package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.dto.PasswordDto;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.PasswordResetToken;
import com.wemakeitwork.allenvooreen.model.VerificationToken;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.repository.event.OnRegistrationSuccessEvent;
import com.wemakeitwork.allenvooreen.repository.event.RegistrationEmailListener;
import com.wemakeitwork.allenvooreen.service.MemberService;
import com.wemakeitwork.allenvooreen.service.MemberServiceInterface;
import com.wemakeitwork.allenvooreen.service.SecurityServiceInterface;
import com.wemakeitwork.allenvooreen.util.GenericResponse;
import com.wemakeitwork.allenvooreen.validator.MemberValidator;
import com.wemakeitwork.allenvooreen.web.error.InvalidOldPasswordException;
import com.wemakeitwork.allenvooreen.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Logger;


@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberServiceInterface memberServiceInterface;

    @Autowired
    private SecurityServiceInterface securityServiceInterface;

    @Autowired
    private MemberValidator memberValidator;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;

    private Logger logger = Logger.getLogger(getClass().getName());

    private Object Locale;

    @GetMapping("member/current")
    protected String showMember(Model model) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentmember", member);
        return "memberProfile";
    }

    @GetMapping("/member/change")
    protected String changeMember(Model model) {
        model.addAttribute("member", new Member());
        return "changeMember";
    }

    @GetMapping("/member/select/{memberId}")
    protected String showMemberData(Model model) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("member", member);
        return "changeMember";
    }

    @GetMapping("/member/new")
    //@Secured("ROLE_ADMIN")
    protected String showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return "newMember";
    }

    @GetMapping("/member/delete")
    public String deleteMember() {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memberRepository.delete(member);
        return "confirmLogout";
    }

    @PostMapping("/member/new")
    protected String newMember(@ModelAttribute("member") @Valid Member member, BindingResult result, WebRequest request, Model model) {
        System.out.println("is er output? " + member.getMemberName());
        memberValidator.validate(member, result);
        if (result.hasErrors()) {
            return "newMember";
        } else {
            System.out.println(member.getMemberName());
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRol("gebruiker");
            memberServiceInterface.save(member);
            try {
                String appUrl = request.getContextPath();
                eventPublisher.publishEvent(new OnRegistrationSuccessEvent(member, appUrl));
            } catch (Exception re) {
                re.printStackTrace();
            }
            return "signup-success";
        }
    }


    @PostMapping("/member/change")
    protected String changeMember(@ModelAttribute("currentmember") Member newNameMember, BindingResult result) {
        if (result.hasErrors()) {
            return "currentMember";
        } else {
            Member originalMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            newNameMember.setPassword(originalMember.getPassword());
            newNameMember.setMemberId(originalMember.getMemberId());
            newNameMember.setRol(originalMember.getRol());
            memberRepository.save(newNameMember);
            return "redirect:/logout";
        }
    }

    @GetMapping("/confirmRegistration")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        VerificationToken verificationToken = memberServiceInterface.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:access-denied";
        }
        Member member = verificationToken.getMember();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", message);
            return "redirect:access-denied";
        }
        member.setEnabled(true);
        memberServiceInterface.enableRegisteredUser(member);
        return null;
    }

    // Reset wachtwoord

    @GetMapping("/forgotPassword")
    protected String showForgotPage() {
        return "forgotPassword";
    }

    @RequestMapping(value= "/member/resetPassword")
    @ResponseBody
    public GenericResponse resetPassword(HttpServletRequest request, @ModelAttribute("email") String email) {
        System.out.println("dag");
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UserNotFoundException();
        }
            String token = UUID.randomUUID().toString();
            memberService.createPasswordResetTokenForMember(member, token);
            mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, member));
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    @GetMapping(value = "/member/changePassword")
    public String showChangePasswordPage(Locale locale, Model model, @RequestParam("id") int id, @RequestParam("token") String token) {
        String result = securityServiceInterface.validatePasswordResetToken(id, token);
        System.out.println("test");
        if (result != null) {
            model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login";
        }
        return "updatePassword";
    }

    @RequestMapping(value= "/member/savePassword")
    @ResponseBody
    public GenericResponse savePassword(Locale locale, @Valid PasswordDto passwordDto) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memberService.changeMemberPassword(member, passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
    }


    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, Member member) {
        String url = contextPath + "/member/changePassword?id=" + member.getMemberId() + "&token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, member);
    }


    private SimpleMailMessage constructEmail(String subject, String body,
                                             Member member) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(member.getEmail());
        email.setFrom(env.getProperty("allenvooreenapplicatie@gmail.com"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}








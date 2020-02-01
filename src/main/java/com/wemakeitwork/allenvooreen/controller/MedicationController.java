package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MedicationServiceInterface;
import com.wemakeitwork.allenvooreen.validator.MedicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Controller
@ControllerAdvice
public class MedicationController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MedicationServiceInterface medicationServiceInterface;

    @Autowired
    MedicationValidator medicationValidator;

    @GetMapping("/medication/new")
    protected String showMedication(Model model) {
        model.addAttribute("medication", new Medication());
        return "newMedication";
    }

    // This method is (amongst others) used to show a user friendly error message when the input of medicationAmount is not an integer.
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException bindException) {
        return "error message is in validation.properties";
    }

    @PostMapping("/medication/new")
    protected String saveOrUpdateMedication(@ModelAttribute("medication")@Valid Medication medication, BindingResult result){
        medicationValidator.validate(medication,result);
        if (result.hasErrors()) {
            return "newMedication";
        } else {
            //System.out.println(medication.getMedicationName());
            Team team = (Team) httpSession.getAttribute("team");
            medication.setTeam(team);
            medicationRepository.save(medication);
            return "redirect:/medication/"+ team.getTeamId();
        }
    }

    @GetMapping("/medication/{teamId}")
    public String showMedication(@PathVariable("teamId") final Integer teamId, Model model, Principal principal) {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);

        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMemberName(principal.getName());
        if (member.isPresent()) {
            teamList = member.get().getAllTeamsOfMemberSet();
        }

        Medication medication = new Medication();
        model.addAttribute("medicationList", team.getMedicationList());

        return "medicationOverview";
    }


    @GetMapping("/medication/delete/{medicationId}")
    public String deleteTeam(@PathVariable("medicationId") final Integer medicationId) {
        medicationRepository.deleteById(medicationId);
        Team team = (Team) httpSession.getAttribute("team");
        return "redirect:/medication/"+ team.getTeamId();
    }
}


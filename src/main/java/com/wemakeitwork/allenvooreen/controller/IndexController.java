package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.TeamMembership;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class IndexController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String calendar(Model model){
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<TeamMembership> teamList = memberRepository.findByMemberName(member.getMemberName()).get().getTeamMemberships();
        /*if (teamList != null) {
            ArrayList<Team> sortedList = (ArrayList<Team>) teamList.stream()
                    .sorted(Comparator.comparing(Team::getTeamName))
                    .collect(Collectors.toList());

            sortedList.forEach(x -> System.out.println(x.getTeamName()));

        }
        model.addAttribute("teamList", sortedList);*/
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/logout";
    }
}
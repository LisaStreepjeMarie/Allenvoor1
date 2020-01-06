package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class NewTeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/team/all")
    protected String showTeams(Model model) {
        List<Team> allTeams = teamRepository.findAll();
        /* for(Team team : allTeams) {
            System.out.println("");
        } */
        model.addAttribute("allTeams", allTeams);
        System.out.println("");
        return "teamOverview";
    }

    @GetMapping("/team/new")
    protected String showTeamForm(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("member", new Member());
        model.addAttribute("teamList", teamRepository.findAll());
        return "teamForm";
    }

    @GetMapping("/team/select/{teamId}")
    protected String showTeamData(@PathVariable("teamId") final Integer teamId, Model model) {
        // extra regel om te testen:
        model.addAttribute("team", new Team());
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        Team team;
        if (teamOpt.isPresent()) {
            team = teamOpt.get();
        } else {
            team = new Team();
        }
        model.addAttribute("team", team);
        return "teamData";
    }

    @PostMapping("/team/new")
    protected String saveOrUpdateTeam(HttpServletRequest request) {
      // System.out.println(team);

        String teamName = request.getParameter("teamName");
        String membername = request.getParameter("membername");
        System.out.println("teamName: " + teamName);
        System.out.println("membername: " + membername);
        /* if (result.hasErrors()) {
            return "teamForm";
        } else { */
        Team team = new Team();
        team.setTeamName(teamName);
        Member member = memberRepository.findByMembername(membername).get();
        member.getTeamName().add(team);
        team.getMembername().add(member);
        teamRepository.save(team);
        memberRepository.save(member);
        return "redirect:/team/all";
        //}
    }
}

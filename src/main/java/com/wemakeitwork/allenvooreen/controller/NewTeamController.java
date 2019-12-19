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

import java.util.List;
import java.util.Optional;

@Controller
public class NewTeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/team/all")
    protected String showTeams(Model model){
        model.addAttribute("allTeams", teamRepository.findAll());
        return "teamOverview";
    }

    @GetMapping("/team/new")
    protected String showTeamForm(Model model){
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
    protected String saveOrUpdateTeam(@ModelAttribute("team") Team team, @ModelAttribute("member") Member member,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "teamForm";
        } else {
            team.getMembername().add(member);
            teamRepository.save(team);
            return "redirect:/team/all";
        }
    }
}

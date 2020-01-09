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
import java.util.List;
import java.util.Optional;

@Controller
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/team/all")
    protected String showTeams(Model model) {
        List<Team> allTeams = teamRepository.findAll();
        model.addAttribute("allTeams", allTeams);
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
        model.addAttribute("team", new Team());

        Optional<Team> teamOpt = teamRepository.findById(teamId);
        Team team;
        team = teamOpt.orElseGet(Team::new);
        model.addAttribute("team", team);
        return "teamData";
    }

    @GetMapping("/team/delete/{teamId}")
    public String deleteTeam(@PathVariable("teamId") final Integer teamId) {
        teamRepository.deleteById(teamId);
        return "redirect:/team/all";
    }

    @PostMapping("/team/new")
    protected String saveOrUpdateTeam(HttpServletRequest request) {
        String teamName = request.getParameter("teamName");
        String membername = request.getParameter("membername");
        Team team = new Team();
        team.setTeamName(teamName);
        Member member = memberRepository.findByMembername(membername).get();
        member.getTeamName().add(team);
        team.getMembername().add(member);
        teamRepository.save(team);
        memberRepository.save(member);
        return "redirect:/team/all";
    }

    @PostMapping("/team/change")
    protected String saveOrUpdateTeam(@ModelAttribute("team") Team team, BindingResult result){
        if (result.hasErrors()) {
            return "teamData";
        } else {
            teamRepository.save(team);
            return "redirect:/team/all";
        }
    }
}

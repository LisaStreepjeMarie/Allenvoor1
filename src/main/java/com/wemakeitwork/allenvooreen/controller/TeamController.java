package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wemakeitwork.allenvooreen.dto.TeamMemberDTO;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/team/all")
    protected String showTeamsPerMember(Model model, Principal principal){
        List<Team> teamList = new ArrayList<>();
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        if(member.isPresent()){
            teamList = teamList(member.get().getMemberId());

        }
        model.addAttribute("teamList", teamList);
        return "teamOverview";
    }

    private List<Team> teamList (Integer memberId){
        List<Team> teamList = new ArrayList<>();
        List<Integer> allMyTeamsById = teamRepository.findTeamsByIdMember(memberId);
        for (Integer integer : allMyTeamsById){
            teamList.add(teamRepository.findTeamById(integer));
        }
        return teamList;
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
        team = teamOpt.orElseGet(Team::new);
        Integer addMembernameToTeam = 0;
        model.addAttribute("addMembernameToTeam", addMembernameToTeam);
        model.addAttribute("team", team);

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setTeamId(teamId);
        model.addAttribute("teamMemberDTO", teamMemberDTO);

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

    @PostMapping("team/addMember")
    protected String addMember(@ModelAttribute("teamMemberDTO")TeamMemberDTO teamMemberDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "teamData";
        } else {
            Optional<Member> member = memberRepository.findByMembername(teamMemberDTO.getTeamMemberName());
            Team team = teamRepository.findTeamById(teamMemberDTO.getTeamId());
            member.ifPresent(team::addTeamMember);
            teamRepository.save(team);
            return "redirect:/team/select/" + teamMemberDTO.getTeamId();
        }
    }
}

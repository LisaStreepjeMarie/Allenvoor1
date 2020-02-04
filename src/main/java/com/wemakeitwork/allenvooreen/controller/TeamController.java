package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.dto.TeamMemberDTO;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.model.TeamMembership;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamMembershipRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamMembershipRepository teamMembershipRepository;


    @GetMapping("/team/all")
    protected String showTeamsPerMember(Model model){
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ArrayList<Team> teamList = memberRepository.findByMemberName(member.getMemberName()).get()
                .getTeamMemberships().stream().map(TeamMembership::getTeam).collect(Collectors.toCollection(ArrayList::new));

        for (Team team: teamList) {
            System.out.print("Current member is admin: " );
            team.getTeamMemberships().stream().filter(x -> x.getMember().getMemberId().equals(member.getMemberId())).map(TeamMembership::isAdmin).forEach(System.out::print);
            System.out.println("of team: " + team.getTeamName());
        }

        model.addAttribute("teamList", teamList);
        return "teamOverview";

    }

    private Set<Team> teamList (Integer memberId){
        Set<Team> teamList = null;
        Member member = memberRepository.getOne(memberId);
        teamList.add(new Team());
        return teamList;
    }

    @GetMapping("/team/new")
    protected String showNewTeam(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("member", new Member());
        model.addAttribute("teamList", teamRepository.findAll());
        return "newTeam";
    }

    @GetMapping("/team/select/{teamId}")
    protected String showChangeTeam(@PathVariable("teamId") final Integer teamId, Model model) {
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

        return "changeTeam";
    }

    @GetMapping("/team/delete/{teamId}")
    public String deleteTeam(@PathVariable("teamId") final Integer teamId) {
        teamRepository.deleteById(teamId);
        return "redirect:/team/all";
    }

    @GetMapping("/team/{teamId}/delete/membership/{membershipId}")
    public String deleteMemberFromTeam(@PathVariable("teamId") final Integer teamId,
                                       @PathVariable("membershipId") final Integer membershipId) {
        TeamMembership tms = new TeamMembership();
        tms.setMembershipId(membershipId);
        teamMembershipRepository.delete(tms);
        return "redirect:/team/select/{teamId}";
    }

    @PostMapping("/team/new")
    protected String newTeam(HttpServletRequest request) {
        String teamName = request.getParameter("teamName");
        Team team = new Team();
        team.setTeamName(teamName);
        teamRepository.save(team);

        TeamMembership tms = new TeamMembership(team,
                (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal(), true);
        teamMembershipRepository.save(tms);
        return "redirect:/team/all";
    }

    @PostMapping("/team/change")
    protected String saveOrUpdateTeam(@ModelAttribute("team") Team team, BindingResult result){
        if (result.hasErrors()) {
            return "changeTeam";
        } else {
            teamRepository.save(team);
            return "redirect:/team/all";
        }
    }

    @PostMapping("team/addMember")
    protected String addMember(@ModelAttribute("teamMemberDTO") TeamMemberDTO teamMemberDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "changeTeam";
        } else {
            Optional<Member> memberOpt = memberRepository.findByMemberName(teamMemberDTO.getTeamMemberName());
            Team team = teamRepository.getOne(teamMemberDTO.getTeamId());

            TeamMembership tms = new TeamMembership();
            tms.setTeam(team);
            tms.setMember(memberOpt.get());
            teamMembershipRepository.save(tms);
            return "redirect:/team/select/" + teamMemberDTO.getTeamId();
        }
    }
}
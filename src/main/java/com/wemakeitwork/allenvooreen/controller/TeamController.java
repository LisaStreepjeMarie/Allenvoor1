package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.dto.TeamMemberDTO;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.model.TeamMembership;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamMembershipRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.InvalidPropertyException;
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
import java.util.List;
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

    int membershipId;

    @GetMapping("/team/all")
    protected String showTeamsPerMember(Model model){
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // List of teams where current user is an admin
        model.addAttribute("adminTeamList", memberRepository
                // Find all teams where current user is a member
                .findByMemberName(member.getMemberName()).get().getTeamMemberships().stream()
                // Filter out the member (and leave out the rest of the teammembers)
                .filter(x -> x.getMember().getMemberId().equals(member.getMemberId()))
                // Filter out teams where user is admin
                .filter(TeamMembership::isAdmin).map(TeamMembership::getTeam)
                // Save resulting teams to adminTeamList
                .collect(Collectors.toCollection(ArrayList::new)));

        // list of teams where current user is NOT an admin
        model.addAttribute("memberTeamList", memberRepository
                .findByMemberName(member.getMemberName()).get().getTeamMemberships().stream()
                .filter(x -> x.getMember().getMemberId().equals(member.getMemberId()))
                .filter(x -> !x.isAdmin()).map(TeamMembership::getTeam)
                .collect(Collectors.toCollection(ArrayList::new)));

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
        Team team = teamRepository.getOne(teamId);
        List<TeamMembership> teamMemberList = team.getTeamMemberships().stream().filter(x -> !x.isAdmin()).collect(Collectors.toList());
        List<TeamMembership> teamAdminList = team.getTeamMemberships().stream().filter(x -> x.isAdmin()).collect(Collectors.toList());

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setTeamId(teamId);

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (teamMembershipRepository.findByTeamAndMember(team, member).isAdmin()) {
            model.addAttribute("teamMemberList", teamMemberList);
            model.addAttribute("teamAdminList", teamAdminList);
            model.addAttribute("teamMemberDTO", teamMemberDTO);
            return "changeTeam";
        } else {
            model.addAttribute("statuscode", "Geen toegang: je bent geen groepsbeheerder");
            return "error";
        }
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


    @GetMapping("/team/quitadmin/{teamId}")
    public String toggleAdmin(@PathVariable("teamId") final Integer teamId, Model model) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Team team = teamRepository.getOne(teamId);

        // Count number of admins in team
        Integer numberOfAdminsInTeam = 0;
        Set<TeamMembership> tmsList = team.getTeamMemberships();
        for (TeamMembership tms : tmsList) {
            if (tms.isAdmin()) {
                numberOfAdminsInTeam++;
            }
        }

        // Prevent admin from giving up admin rights when there are no other admins.
        if (numberOfAdminsInTeam > 1) {
            teamMembershipRepository.findAll().stream()
                    // Find all memberships of member
                    .filter(x -> x.getMember().getMemberId().equals(member.getMemberId()))
                    // filter the team that is passed with the pathvariable (ignore other teams from member)
                    .filter(x -> x.getTeam().getTeamId().equals(teamId))
                    // Toggle the boolean isAdmin in the detached object and save it to the database
                    .map(x -> {
                        x.setAdmin(!x.isAdmin());
                        return x;
                    }).forEach(teamMembershipRepository::save);
            return "redirect:/team/all";
        } else {
            model.addAttribute("statuscode", "Kan rol als groepsbeheerder niet opgeven omdat je de enige groepsbeheerder bent.");
            return "error";
        }
    }

    @GetMapping("/team/grantadmin/{teamId}/{memberId}")
    public String grantAdmin(@PathVariable("teamId") final Integer teamId,
                              @PathVariable("memberId") final Integer memberId) {
        Member member = memberRepository.getOne(memberId);
        Team team = teamRepository.getOne(teamId);
        TeamMembership tms = teamMembershipRepository.findByTeamAndMember(team, member);

        tms.setAdmin(true);
        teamMembershipRepository.save(tms);

        return "redirect:/team/select/" + teamId;
    }

    @GetMapping("/team/quit/{teamId}")
    public String quitTeam(@PathVariable("teamId") final Integer teamId) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new InvalidPropertyException(this.getClass(), "team", "Dit team bestaat niet"));

        teamMembershipRepository.findAll().stream()
                // Find all memberships of member
                .filter(x -> x.getMember().getMemberId().equals(member.getMemberId()))
                // filter the team that is passed with the pathvariable (ignore other teams from member)
                .filter(x -> x.getTeam().getTeamId().equals(teamId))
                // Toggle the boolean isAdmin in the detached object and save it to the database
                .forEach(teamMembershipRepository::delete);

        // Delete team if it has no members anymore. TODO: needs confirmation check
        if (teamMembershipRepository.findByTeam(team).isEmpty()) {
            teamRepository.delete(team);
        }
        return "redirect:/team/all";
    }
}
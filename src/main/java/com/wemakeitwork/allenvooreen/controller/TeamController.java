package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.dto.TeamMemberDTO;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.model.TeamMembership;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamMembershipRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TeamController {
    final int MINIMUM_MEMBERS_IN_TEAM = 1;
    final int MINIMUM_ADMINS_IN_TEAM = 1;

    ObjectMapper mapper = new ObjectMapper();

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
                // Sort the list by teamname
                .sorted(Comparator.comparing(Team::getTeamName))
                // Save resulting teams to adminTeamList
                .collect(Collectors.toCollection(ArrayList::new)));

        // list of teams where current user is NOT an admin
        model.addAttribute("memberTeamList", memberRepository
                .findByMemberName(member.getMemberName()).get().getTeamMemberships().stream()
                .filter(x -> x.getMember().getMemberId().equals(member.getMemberId()))
                .filter(x -> !x.isAdmin()).map(TeamMembership::getTeam)
                .sorted(Comparator.comparing(Team::getTeamName))
                .collect(Collectors.toCollection(ArrayList::new)));

        return "teamOverview";

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
        List<TeamMembership> teamMemberList = team.getTeamMemberships().stream()
                .filter(x -> !x.isAdmin())
                .sorted(Comparator.comparing(x -> x.getMember().getMemberName()))
                .collect(Collectors.toList());
        List<TeamMembership> teamAdminList = team.getTeamMemberships().stream()
                .filter(x -> x.isAdmin())
                .sorted(Comparator.comparing(x -> x.getMember().getMemberName()))
                .collect(Collectors.toList());

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setTeamId(teamId);

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if principal(member) has a teammembership (tms) in team and is admin that team.
        Optional<TeamMembership> tms = teamMembershipRepository.findByTeamAndMember(team, member);
        if (tms.isPresent()) {
            if (tms.get().isAdmin()) {
                model.addAttribute("teamName", team.getTeamName());
                model.addAttribute("teamMemberList", teamMemberList);
                model.addAttribute("teamAdminList", teamAdminList);
                model.addAttribute("teamMemberDTO", teamMemberDTO);
                return "changeTeam";
            } else {
                model.addAttribute("statuscode", "Geen toegang: je bent geen groepsbeheerder");
                return "error";
            }
        }
        model.addAttribute("statuscode", "Error: Missing teammembership");
        return "error";
    }

    @PostMapping("/team/delete")
    public ResponseEntity<Object> deleteTeam(@RequestBody String deleteTeamJson) throws JsonProcessingException {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Team team = mapper.readValue(deleteTeamJson, Team.class);

        // TODO: check if principal is allowed to delete team
        teamRepository.deleteById(team.getTeamId());
        ServiceResponse<String> response = new ServiceResponse<String>("succes", "Team is verwijderd");
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    protected String addMember(@ModelAttribute("teamMemberDTO") TeamMemberDTO teamMemberDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "changeTeam";
        } else {
            Optional<Member> memberOpt = memberRepository.findByMemberName(teamMemberDTO.getTeamMemberName());
            Team team = teamRepository.getOne(teamMemberDTO.getTeamId());

            TeamMembership tms = new TeamMembership();
            tms.setTeam(team);

            if (memberOpt.isPresent()) {
                if (team.getTeamMemberships().stream().anyMatch(x -> x.getMember().getMemberName().matches(memberOpt.get().getMemberName()))) {
                    model.addAttribute("statuscode", "Deze gebruiker zit al in het team");
                    return "error";
                }
                tms.setMember(memberOpt.get());
            } else {
                model.addAttribute("statuscode", "Deze gebruiker bestaat niet");
                return "error";
            }

            teamMembershipRepository.save(tms);
            return "redirect:/team/select/" + teamMemberDTO.getTeamId();
        }
    }

    @PostMapping("/team/quitadmin")
    public ResponseEntity<Object> quitAdmin(@RequestBody String quitAdminJson) throws JsonProcessingException {
        // TODO: Implement guard to check if principal is part of team
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get complete TeamMembership info from database (json only carries partial info like teamId and memberName)
        TeamMembership tms = mapper.readValue(quitAdminJson, TeamMembership.class);
        tms.setMember(memberRepository
                .findByMemberName(tms.getMember().getMemberName())
                .orElseThrow(() -> new InvalidPropertyException(this.getClass(), "member", "Dit teamlid bestaat niet")));
        tms.setTeam(teamRepository
                .findById(tms.getTeam().getTeamId())
                .orElseThrow(() -> new InvalidPropertyException(this.getClass(), "team", "Dit team bestaat niet")));

        // Check if there are more than one teamadmins (otherwise quiting the admin role is not allowed)
        if (tms.getTeam().getTeamMemberships().stream().filter(TeamMembership::isAdmin).count() > MINIMUM_ADMINS_IN_TEAM) {
            // If there are more than one teamadmins, relinquish admin role.
            teamMembershipRepository.findByTeamAndMember(tms.getTeam(), tms.getMember()).stream()
                    .peek(x -> x.setAdmin(false))
                    .forEach(teamMembershipRepository::save);
            ServiceResponse<String> response = new ServiceResponse<String>("succes", "Je bent geen groepsbeheerder meer");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Otherwise, tell user that relinquishing teamadmin role is not possible
            ServiceResponse<String> response = new ServiceResponse<String>("failure",
                    "Je kunt je niet uitschrijven als groepsbeheerder, omdat je de enige groepsbeheerder bent.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/team/grantadmin/{teamId}/{memberId}")
    public String grantAdmin(@PathVariable("teamId") final Integer teamId,
                             @PathVariable("memberId") final Integer memberId, Model model) {

        Optional<TeamMembership> tms = teamMembershipRepository.findByTeamTeamIdAndMemberMemberId(teamId, memberId);
        if (tms.isPresent()) {
            tms.get().setAdmin(true);
            teamMembershipRepository.save(tms.get());
            return "redirect:/team/select/" + teamId;
        }
        // No teammembership was found.
        model.addAttribute("statuscode", "Error: Missing teammembership");
        return "error";
    }

    @PostMapping("/team/quit/{deleteTeamIfEmpty}")
    public ResponseEntity<Object> quitTeam(@RequestBody String quitTeamJson, @PathVariable final boolean deleteTeamIfEmpty) throws JsonProcessingException, InterruptedException {
        // TODO: Implement guard to check if principal is part of team
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TeamMembership tms = mapper.readValue(quitTeamJson, TeamMembership.class);
        tms.setMember(memberRepository
                .findByMemberName(tms.getMember().getMemberName())
                .orElseThrow(() -> new InvalidPropertyException(this.getClass(), "member", "Dit teamlid bestaat niet")));
        tms.setTeam(teamRepository
                .findById(tms.getTeam().getTeamId())
                .orElseThrow(() -> new InvalidPropertyException(this.getClass(), "team", "Dit team bestaat niet")));

        if ((long) tms.getTeam().getTeamMemberships().size() <= MINIMUM_MEMBERS_IN_TEAM) {
            // If logged in user is only member of team, delete team.
            if (deleteTeamIfEmpty) {
                // Delete team because it is empty
                teamRepository.delete(tms.getTeam());
                ServiceResponse<String> response = new ServiceResponse<String>("success",
                        "Het team is verwijderd omdat je het enige groepslid was.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // Before deleting, ask for confirmation first
                ServiceResponse<TeamMembership> response = new ServiceResponse<TeamMembership>("pleaseConfirm", tms);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else if (tms.getTeam().getTeamMemberships().stream().filter(TeamMembership::isAdmin).count() <= MINIMUM_ADMINS_IN_TEAM) {
            // Tell user that quiting team is not possible, because (s)he is the only teamadmin
            ServiceResponse<String> response = new ServiceResponse<String>("failure",
                    "Je kunt je niet uitschrijven, omdat je de enige groepsbeheerder bent.");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } else {
            teamMembershipRepository.delete(tms);
            ServiceResponse<String> response = new ServiceResponse<String>("success",
                    "Je bent geen lid meer van het team");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
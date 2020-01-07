package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.dto.TeamMemberDTO;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.Optional;

@Controller
public class ChangeTeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

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

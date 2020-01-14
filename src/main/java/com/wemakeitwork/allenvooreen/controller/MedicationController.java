package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
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

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Controller
public class MedicationController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/medication/new")
    protected String showMedication(Model model) {
        model.addAttribute("medication", new Medication());
        return "newMedication";
    }

    @GetMapping("/medication/{teamId}")
    public String showMedication(@PathVariable("teamId") final Integer teamId, Model model, Principal principal) {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);

        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        if (member.isPresent()) {
            teamList = member.get().getTeamName();
        }

        Medication medication = new Medication();
        //event.setActivity(new Activity());
        //model.addAttribute("teamList", teamList);
        //model.addAttribute("event", event);

        //Event event = new Event();
        //event.setActivity(new Activity());
        //model.addAttribute("teamList", teamList);
        //model.addAttribute("medication", medication);
        model.addAttribute("medicationList", team.getMedicationList());

        return "medicationOverview";
    }

    @GetMapping("/medication/delete/{medicationId}")
    public String deleteTeam(@PathVariable("medicationId") final Integer medicationId) {
        medicationRepository.deleteById(medicationId);
        return "medicationOverview";
    }

    @PostMapping("/medication/new")
    protected String saveOrUpdateMedication(@ModelAttribute("medication") Medication medication, BindingResult result) {
        if (result.hasErrors()) {
            return "newMedication";
        } else {
            Team team = (Team) httpSession.getAttribute("team");
            medication.setTeam(team);
            medicationRepository.save(medication);
            return "redirect:/medication/new";
        }
    }
}


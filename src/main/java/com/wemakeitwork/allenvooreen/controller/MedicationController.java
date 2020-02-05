package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
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
        Optional<Member> member = memberRepository.findByMemberName(principal.getName());
        if (member.isPresent()) {
            teamList = member.get().getAllTeamsOfMemberSet();
        }

        Medication medication = new Medication();
        model.addAttribute("medicationList", team.getMedicationList());

        return "medicationOverview";
    }

    @GetMapping("/medication/delete/{medicationId}")
    public String deleteTeam(@PathVariable("medicationId") final Integer medicationId) {
        int teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        Team team = teamRepository.getOne(teamId);
        team.getMedicationList().remove(medicationRepository.getOne(medicationId));
        teamRepository.save(team);

        medicationRepository.deleteById(medicationId);
        return "redirect:/medication/"+ teamId;
    }

    @GetMapping("/medication/grocerylist/{medicationId}/{amount}")
    public String addMedicationToGrocerylist(@PathVariable("medicationId") Integer medicationId, @PathVariable ("amount") Integer amount) {

        int teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        Optional<Medication> medication = medicationRepository.findById(medicationId);

        if (medication.isPresent()){

            medication.get().setBought();
            medication.get().setMedicationRefillAmount(amount);
            medication.get().setGroceryList(teamRepository.findById(teamId).get().getGroceryList());
            medicationRepository.save(medication.get());
        }

        return "redirect:/medication/"+ teamId;
    }

    @PostMapping("/medication/new")
    protected String saveOrUpdateMedication(@ModelAttribute("medication") Medication medication, BindingResult result) {
        if (result.hasErrors()) {
            return "newMedication";
        } else {
            Team team = (Team) httpSession.getAttribute("team");
            medication.setTeam(team);
            medicationRepository.save(medication);
            return "redirect:/medication/"+ team.getTeamId();
        }
    }
}


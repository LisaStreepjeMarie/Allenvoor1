package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.model.TeamMembership;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MedicationServiceInterface;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import com.wemakeitwork.allenvooreen.validator.MedicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
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

    @Autowired
    MedicationServiceInterface medicationServiceInterface;

    @Autowired
    MedicationValidator medicationValidator;

    @GetMapping("/medication/new")
    protected String showMedication(Model model) {
        model.addAttribute("medication", new Medication());
        return "newMedication";
    }

    @PostMapping("/medication/new")
    protected String saveOrUpdateMedication(@ModelAttribute("medication")@Valid Medication medication, BindingResult result){
        medicationValidator.validate(medication,result);
        if (result.hasErrors()) {
            return "newMedication";
        } else {
            Team team = (Team) httpSession.getAttribute("team");
            medication.setTeam(team);
            medicationRepository.save(medication);
            return "redirect:/medication/" + team.getTeamId();
        }
    }

    @GetMapping("/medication/{teamId}")
    public String showMedication(@PathVariable("teamId") final Integer teamId, Model model) {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<TeamMembership> teamList = member.getTeamMemberships();

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
            medication.get().setBought(false);
            medication.get().upTheRefillAmount(amount);
            medication.get().setGroceryList(teamRepository.findById(teamId).get().getGroceryList());
            medicationRepository.save(medication.get());
        }

        return "redirect:/medication/"+ teamId;
    }

    @GetMapping("/medication/getList")
    public ResponseEntity<Object> fillMedicationList(){
        int teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        Team team = teamRepository.getOne(teamId);
        List<Medication> medicationList = team.getMedicationList();

        ServiceResponse<List<Medication>> response = new ServiceResponse<>("succes", medicationList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


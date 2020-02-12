package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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

    /* @PostMapping("/medication/new")
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
    } */

    @PostMapping("/medication/new")
    public ResponseEntity<Object> addMedicationItem(@RequestBody String newMedicationItem) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Medication medication = mapper.readValue(newMedicationItem, Medication.class);

        Integer teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        medication.setTeam(teamRepository.getOne(teamId));
        medicationRepository.save(medication);
        ServiceResponse<Medication> result = new ServiceResponse<>("success", medication);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @GetMapping("/medication/{teamId}")
    public String showMedication(@PathVariable("teamId") final Integer teamId, Model model) {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<TeamMembership> teamMembershipList = memberRepository.findByMemberName(member.getMemberName()).get().getTeamMemberships();
        ArrayList<Team> teamList = new ArrayList<>();
        for (TeamMembership tms: teamMembershipList) {
            teamList.add(tms.getTeam());
        }

        ArrayList<Team> sortedList = (ArrayList<Team>) teamList.stream()
                .sorted(Comparator.comparing(Team::getTeamName))
                .collect(Collectors.toList());


        model.addAttribute("teamList", sortedList);
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


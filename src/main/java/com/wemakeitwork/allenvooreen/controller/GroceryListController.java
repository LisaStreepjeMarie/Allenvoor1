package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.GroceryItemRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class GroceryListController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GroceryItemRepository groceryItemRepository;

    @Autowired
    MedicationRepository medicationRepository;


    @GetMapping("/grocerylist/{teamId}")
    protected String showGrocerylist(@PathVariable("teamId") final Integer teamId, Model model) {

        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);
        model.addAttribute("team", team);
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
        return "groceryListWebpage";
    }

    @GetMapping("/grocerylist/getAll")
    public ResponseEntity<Object> fillList(){
        int teamId = ((Team) httpSession.getAttribute("team")).getTeamId();

        Team team = teamRepository.getOne(teamId);
        GroceryList groceryList = team.getGroceryList();

        ServiceResponse<GroceryList> response = new ServiceResponse<>("succes", groceryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add/groceryitem")
    public ResponseEntity<Object> addGroceryItem(@RequestBody String newGroceryItem) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        GroceryItem groceryItem = mapper.readValue(newGroceryItem, GroceryItem.class);

        Integer teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        groceryItem.setGroceryList(teamRepository.findById(teamId).get().getGroceryList());
        groceryItemRepository.save(groceryItem);

        ServiceResponse<GroceryItem> response = new ServiceResponse<GroceryItem>("success", groceryItem);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/delete/groceryItem/{groceryItemId}")
    public ResponseEntity<Object> deleteGroceryItemFromGroceryList(@PathVariable("groceryItemId") final Integer groceryItemid){
        groceryItemRepository.deleteById(groceryItemid);
        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }

    @GetMapping("/delete/allMedications/{medicationId}")
    public  ResponseEntity<Object> deleteMedicationItemFromGroceryList(@PathVariable("medicationId") final Integer medicationId){
        Medication medication = medicationRepository.getOne(medicationId);
        medication.setGroceryList(null);

        if(medication.getBought()){
            medication.upTheMedicationAmount(medication.getMedicationRefillAmount());
            medication.setMedicationRefillAmount(0);
            medication.setBought(true);
        }

        medication.setMedicationRefillAmount(0);
        medicationRepository.save(medication);
        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }

    @GetMapping("/bought/groceryItem/{groceryItemId}")
    public  ResponseEntity<Object> boughtGroceryItem(@PathVariable("groceryItemId") final Integer groceryItemid){
        GroceryItem groceryItem = groceryItemRepository.getOne(groceryItemid);

        if(groceryItem != null) {
            groceryItem.setBought();
            groceryItemRepository.save(groceryItem);
        }

        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }

    @GetMapping("/bought/allMedications/{medicationid}")
    public  ResponseEntity<Object> boughtMedicationItem(@PathVariable("medicationid") final Integer medicationid){
        Medication medication = medicationRepository.getOne(medicationid);

        if(medication != null) {
            medication.toggleBought();
            medicationRepository.save(medication);
        }

        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }
}

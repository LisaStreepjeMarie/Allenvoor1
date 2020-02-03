package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.GroceryItem;
import com.wemakeitwork.allenvooreen.model.GroceryList;
import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.GroceryItemRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GroceryListController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    GroceryItemRepository groceryItemRepository;

    @Autowired
    MedicationRepository medicationRepository;

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/grocerylist")
    protected String showGrocerylist(Model model) {
        Integer teamId = ((Team) httpSession.getAttribute("team")).getTeamId();

        //for some reason it's needed to get the team from repository before getting the grocerylist -LM
        Team team = teamRepository.getOne(teamId);
        GroceryList groceryList = team.getGroceryList();

        List<GroceryItem> allGroceries = groceryList.getAllItemsOnGroceryList();
        team.getGroceryList().setAllMedicationOnGroceryList(team.getMedicationList());

        model.addAttribute("groceryList", allGroceries);
        model.addAttribute("allMedications", team.getMedicationList());
        return "groceryList";
    }

    @PostMapping("/add/groceryitem")
    public ResponseEntity<Object> addGroceryItem(@RequestBody String newGroceryItem) throws JsonProcessingException {
        GroceryItem groceryItem = mapper.readValue(newGroceryItem, GroceryItem.class);

        Integer teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        groceryItem.setGroceryList(teamRepository.findById(teamId).get().getGroceryList());
        groceryItemRepository.save(groceryItem);

        ServiceResponse<GroceryItem> response = new ServiceResponse<GroceryItem>("success", groceryItem);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/delete/groceryItem/{groceryItemId}")
    public  ResponseEntity<Object> deleteGroceryItem(@PathVariable("groceryItemId") final Integer groceryItemid){
        groceryItemRepository.deleteById(groceryItemid);
        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }

    @GetMapping("/delete/allMedications/{medicationId}")
    public  ResponseEntity<Object> deletemedicationItem(@PathVariable("medicationId") final Integer medicationId){
        medicationRepository.deleteById(medicationId);
        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }

    @GetMapping("/bought/groceryItem/{groceryItemId}")
    public  ResponseEntity<Object> boughtGroceryItem(@PathVariable("groceryItemId") final Integer groceryItemid){
        GroceryItem groceryItem = groceryItemRepository.getOne(groceryItemid);
        if(groceryItem != null) {
            groceryItem.setBought();
            System.out.println(groceryItem.getBought());
            groceryItemRepository.save(groceryItem);
        }
        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }

    @GetMapping("/bought/allMedications/{medicationid}")
    public  ResponseEntity<Object> boughtMedicationItem(@PathVariable("medicationid") final Integer medicationid){
        Medication medication = medicationRepository.getOne(medicationid);
        if(medication != null) {
            medication.setBought();
            medicationRepository.save(medication);
        }
        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }
}

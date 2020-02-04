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

    @GetMapping("/grocerylist/{teamId}")
    protected String showGrocerylist(@PathVariable("teamId") final Integer teamId, Model model) {
        httpSession.setAttribute("team", teamRepository.getOne(teamId));
        return "groceryListWebpage";
    }

    @GetMapping("/grocerylist/getAll")
    public ResponseEntity<Object> fillList(){
        int teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        Team team = teamRepository.getOne(teamId);
        GroceryList groceryList = team.getGroceryList();
        for (GroceryItem groceryItem : groceryList.getAllItemsOnGroceryList()){
            System.out.println(groceryItem.getGroceryName());
        }
        ServiceResponse<GroceryList> response = new ServiceResponse<>("succes", groceryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public  ResponseEntity<Object> deletemedicationItemFromGroceryList(@PathVariable("medicationId") final Integer medicationId){
        Medication medication = medicationRepository.getOne(medicationId);
        medication.setGroceryList(null);
        medicationRepository.save(medication);
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

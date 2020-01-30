package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.GroceryItem;
import com.wemakeitwork.allenvooreen.model.GroceryList;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroceryListController {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/GroceryList")
    protected String showGrocerylist(Model model) {
        Team team = teamRepository.getOne(1);

        GroceryList groceryList = team.getGroceryList();
        System.out.println(groceryList.getAllItemsOnGroceryList().size());

        List<GroceryItem> allGroceries = groceryList.getAllItemsOnGroceryList();

        List<GroceryItem> alleItems = new ArrayList<>();
        model.addAttribute("groceryList", allGroceries);
        return "groceryList";
    }

    @GetMapping("/getYourGroceries")
    public ResponseEntity<Object> getMedications(@PathVariable("teamid") final Integer teamId) {
        Team team = (Team) httpSession.getAttribute("team");
        GroceryList groceryList = team.getGroceryList();

        ServiceResponse<GroceryList> response = new ServiceResponse<GroceryList>("success", groceryList);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}

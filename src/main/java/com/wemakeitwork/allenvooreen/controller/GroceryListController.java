package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.GroceryItem;
import com.wemakeitwork.allenvooreen.model.GroceryList;
import com.wemakeitwork.allenvooreen.model.Team;
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

    @GetMapping("/GroceryList")
    protected String showGrocerylist(Model model) {

        GroceryItem een = new GroceryItem();
        GroceryItem twee = new GroceryItem();
        een.setGroceryName("check");
        twee.setGroceryName("check2");

        List<GroceryItem> alleItems = new ArrayList<>();
        alleItems.add(een);
        alleItems.add(twee);

        model.addAttribute("groceryList", alleItems);
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

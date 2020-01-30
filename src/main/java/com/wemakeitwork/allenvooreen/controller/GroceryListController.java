package com.wemakeitwork.allenvooreen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroceryListController {

    @GetMapping("/GroceryList")
    protected String showGrocerylist() {

        return "groceryList";
    }
}

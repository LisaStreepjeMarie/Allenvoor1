package com.wemakeitwork.allenvooreen.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomizedErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        model.addAttribute("statuscode", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
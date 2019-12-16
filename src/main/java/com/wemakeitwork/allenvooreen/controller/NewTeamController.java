package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewTeamController {

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/teams")
    protected String showTeams(Model model){
        model.addAttribute("allTeams", teamRepository.findAll());
        return "teamOverview";
    }

    @GetMapping("/teams/add")
    protected String showTeamForm(Model model){
        model.addAttribute("team", new Team());
        model.addAttribute("teamList", teamRepository.findAll());
        return "teamForm";
    }

    @PostMapping("/teams/add")
    protected String saveOrUpdateTeam(@ModelAttribute("team") Team team, BindingResult result){
        if (result.hasErrors()) {
            return "teamForm";
        } else {
            teamRepository.save(team);
            return "redirect:/teams";
        }
    }

}

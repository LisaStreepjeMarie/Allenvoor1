package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CalendarController.class)
public class CalendarControllerTest {
    @InjectMocks
    CalendarController calendarController;



    @MockBean
    ActivityRepository activityRepository;

    @MockBean
    TeamRepository teamRepository;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    EventRepository eventRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberDetailsService memberDetailsService;

    //mockito doesn't work correctly here yet. Needs to be looked into later - LM
    @Test
    @WithMockUser(roles = "admin")
    public void calendar() throws Exception {
        Set<Team> teamList = new HashSet<>();
        Team team1 = new Team();
        Team team2 = new Team();
        team1.setTeamName("check1");
        team2.setTeamName("check2");
        teamList.add(team1);
        teamList.add(team2);

        Member member = new Member();
        member.setAllTeamsOfMemberSet(teamList);

        ArrayList<Team> sortedList = (ArrayList<Team>) teamList.stream()
                .sorted(Comparator.comparing(Team::getTeamName))
                .collect(Collectors.toList());

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "LisaMarie";
            }
        };


//        Mockito.when(memberRepository.findByMemberName(principal.getName())).thenReturn(java.util.Optional.of(member));
//        Mockito.when(calendarController.calendar(model, principal))

        mockMvc.perform(get("/home")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/home.jsp"))
                .andExpect(model().attribute("teamList", sortedList));
    }

}

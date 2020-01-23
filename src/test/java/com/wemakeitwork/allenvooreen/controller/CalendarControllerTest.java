package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.security.Principal;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CalendarController.class)
public class CalendarControllerTest {

    @MockBean
    ActivityRepository activityRepository;

    @MockBean
    TeamRepository teamRepository;

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberDetailsService memberDetailsService;

    @Test
    @WithMockUser(roles = "admin")
    public void showmyCalender() throws Exception {


        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "TEST_PRINCIPAL";
            }
        };

        Team team  = teamRepository.getOne(1);
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(get("/calendar/{teamId}",1)
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/calendar.jsp"))
                .andExpect(model().attribute("calendarData", mapper.writeValueAsString(team.getEventList())));
    }

}

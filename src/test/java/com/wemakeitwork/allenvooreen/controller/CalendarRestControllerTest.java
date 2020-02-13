package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CalendarRestController.class)
@RunWith(JUnitPlatform.class)
class CalendarRestControllerTest {

    @InjectMocks
    CalendarRestController calendarRestController;

    @MockBean
    TeamRepository teamRepository;

    @MockBean
    MemberDetailsService memberDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEvents() throws Exception {
        // given
        Integer teamId = 0;

        mockMvc.perform(get("/allenvooreen/calendar/get/{teamId}/1580083200000/1583712000000", teamId))
                .andExpect(status().isForbidden());
    }
}
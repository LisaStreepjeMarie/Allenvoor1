package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.repository.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CalendarController.class)
class CalendarControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private HttpSession httpSession;

    @MockBean
    MedicationRepository medicationRepository;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    TeamRepository teamRepository;

    @MockBean
    EventRepository eventRepository;

    @MockBean
    ActivityRepository activityRepository;

    @Autowired
    private MockMvc mockMvc;

    CalendarControllerTest(HttpSession httpSession, MockMvc mockMvc) {
        this.httpSession = httpSession;
        this.mockMvc = mockMvc;
    }
}
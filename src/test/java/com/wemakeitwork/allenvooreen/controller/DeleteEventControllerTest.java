package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ChangeEventController.class)
class DeleteEventControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    EventRepository eventRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ActivityRepository activityRepository;

    @MockBean
    MemberDetailsService memberDetailsService;

    @Test
    @WithMockUser(roles = "admin")
    void deleteEvent() throws Exception {


        mockMvc.perform(get("/event/delete/{eventId}", 9))
                .andExpect(forwardedUrl("/event/all"));

    }

}
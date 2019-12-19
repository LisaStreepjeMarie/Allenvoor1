package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.crypto.Data;

import java.util.Date;

import static org.mockito.ArgumentCaptor.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NewEventController.class)
class NewEventControllerTest {

    @MockBean
    EventRepository eventRepository;

    @MockBean
    ActivityRepository activityRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberDetailsService memberDetailsService;


    @Test
    @WithMockUser(roles = "admin")
    void showEventForm() throws Exception {
        mockMvc.perform(get("/event/new"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/newEvent.jsp"));
    }

    @Test
    @WithMockUser(roles = "admin")
    void saveOrUpdateEvent() throws Exception {
        String eventName = "test2";
        String activitycategory = "test4";
        Date date = new Date(2323223232L);
        String eventComment = "testComment";

        mockMvc.perform(post("/event/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("eventName", eventName)
                .param("activityCategory", activitycategory)
                .param("eventComment", eventComment)
                .flashAttr("event", new Event())
                .with(csrf())
        )
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/event/all"))
                .andExpect(redirectedUrl("/event/all"));

        ArgumentCaptor<Event> formObjectArgument = forClass(Event.class);
        verify(eventRepository, times(1)).save(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(eventRepository);

        Event formObject = formObjectArgument.getValue();

        Assertions.assertThat(formObject.getEventName()).isEqualTo(eventName);
        Assertions.assertThat(formObject.getEventComment()).isEqualTo(eventComment);
    }

}
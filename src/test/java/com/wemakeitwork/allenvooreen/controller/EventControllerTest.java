package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.LeisureActivity;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
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

import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberDetailsService memberDetailsService;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private MedicationRepository medicationRepository;

    @MockBean
    private ActivityRepository activityRepository;

    @MockBean
    private TeamRepository teamRepository;

    @Test
    @WithMockUser(roles = "admin")
    public void shouldReturnNewEventPost() throws Exception {
        Integer eventId = 20;
        String eventName = "testEventNaam";
        Event testEvent = new Event();
        testEvent.setActivity(new LeisureActivity());

        Team testTeam = new Team();
        testTeam.setTeamId(1);

        mockMvc.perform(post("/event/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("eventId", String.valueOf(eventId))
                .param("eventName", eventName)
                .flashAttr("event", testEvent)
                .sessionAttr("team", testTeam)
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/calendar/" + testTeam.getTeamId()))
                .andExpect(redirectedUrl("/calendar/" + testTeam.getTeamId()));

        ArgumentCaptor<Event> formObjectArgument = forClass(Event.class);
        verify(eventRepository, atLeastOnce()).save(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(eventRepository);

        Event formObject = formObjectArgument.getValue();
        Assertions.assertThat(formObject.getEventId()).isEqualTo(eventId);
        Assertions.assertThat(formObject.getEventName()).isEqualTo(eventName);
    }
}
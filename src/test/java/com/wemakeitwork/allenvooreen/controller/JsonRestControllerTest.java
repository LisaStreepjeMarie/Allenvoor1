package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = JsonRestController.class)
class JsonRestControllerTest {

    @MockBean
    ActivityRepository activityRepository;

    @MockBean
    MedicationRepository medicationRepository;

    @MockBean
    TeamRepository teamRepository;

    @InjectMocks
    JsonRestController jsonRestController;

    @MockBean
    EventRepository eventRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberDetailsService memberDetailsService;

    @Test
    void addEvent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Activity testActivity = new Activity();
        testActivity.setActivityId(1);
        testActivity.setActivityCategory("Vrije tijd");
        testActivity.setActivityName("ActivityTest");
        Event testevent1 = new Event();
        testevent1.setEventName("hallo test event");
        testevent1.setActivity(testActivity);
        testevent1.setEventId(1);
        testevent1.setEventComment("hoi");
        Team team = new Team();
        team.setTeamName("check");
        team.setTeamId(1);
        testevent1.setTeam(team);
        String testEvent = mapper.writeValueAsString(testevent1);

        ServiceResponse<Event> result = new ServiceResponse<Event>("Success", testevent1);
        ResponseEntity<Object> expected = new ResponseEntity<Object>(result, HttpStatus.OK);

        when(eventRepository.save(Mockito.any(Event.class))).thenReturn(testevent1);

        ResponseEntity<Object> response = ResponseEntity.ok(testevent1);
        ResponseEntity<Object> responseEntity = jsonRestController.addEvent(testEvent);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testevent1);
        Assertions.assertThat(responseEntity.getBody().getClass()).isEqualTo(result.getClass());

    }

}
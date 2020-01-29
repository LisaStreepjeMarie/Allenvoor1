package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
@WebMvcTest(controllers = TeamController.class)
class TeamControllerTest {

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

    @Test
    @WithMockUser(roles = "admin")
    void showTeamForm() throws Exception {
//        mockMvc.perform(get("/team/new")
//                .contentType("text/plain"))
//                .andExpect(status().isOk())
//                .andExpect(forwardedUrl("/WEB-INF/jsp/teamForm.jsp"));
//                .andExpect(model().attribute("teamList", teamRepository.findAll()));
    }

}
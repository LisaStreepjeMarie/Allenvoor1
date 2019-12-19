package com.wemakeitwork.allenvooreen.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Team;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NewTeamController.class)
class NewTeamControllerTest {

    @MockBean
    TeamRepository teamRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberDetailsService memberDetailsService;

    @Test
    @WithMockUser(roles = "admin")
    public void showTeams() throws Exception {
        mockMvc.perform(get("/team/all"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/teamOverview.jsp"));
    }

    @Test
    @WithMockUser(roles = "admin")
    void showTeamForm() throws Exception {
        mockMvc.perform(get("/team/new"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/teamForm.jsp"));
    }

    @Test
    void showTeamData() {
    }

    @Test
    @WithMockUser(roles = "admin")
    public void saveOrUpdateTeam() throws Exception {
        String teamName = "testName";

        mockMvc.perform(post("/team/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("teamName", teamName)
                .flashAttr("team", new Team())
                .with(csrf())
        )
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/team/all"))
                .andExpect(redirectedUrl("/team/all"));

        ArgumentCaptor<Team> formObjectArgument = forClass(Team.class);
        verify(teamRepository, times(1)).save(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(teamRepository);

        Team formObject = formObjectArgument.getValue();

        Assertions.assertThat(formObject.getTeamName()).isEqualTo(teamName);

    }
}
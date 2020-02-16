package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamMembershipRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TeamController.class)
class TeamControllerTest {

    @MockBean
    TeamRepository teamRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    TeamMembershipRepository teamMembershipRepository;

    @MockBean
    MemberDetailsService memberDetailsService;

    @Test
    @WithMockUser(value = "ROLE_ADMIN")
    void showNewTeam() throws Exception {
        mockMvc.perform(get("/team/new"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/newTeam.jsp"));
    }

    @Test
    public void showTeamsPerMember() throws Exception {
        Member member = mock(Member.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(member);

        mockMvc.perform(get("/team/all")
                        .with(user(member))
        )
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/teamOverview.jsp"));
    }

    @Test
    @WithMockUser(value = "ROLE_ADMIN")
    public void saveOrUpdateTeam() throws Exception {
        String teamName = "testTeamName";

        mockMvc.perform(post("/team/change")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("teamName", teamName)
                .flashAttr("team", new Team())
                .with(csrf())
        )
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/team/all"))
                .andExpect(redirectedUrl("/team/all"));

        ArgumentCaptor<Team> formTeamObjectArgument = forClass(Team.class);
        verify(teamRepository, times(1)).save(formTeamObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(teamRepository);

        Team formTeamObject = formTeamObjectArgument.getValue();

        Assertions.assertThat(formTeamObject.getTeamName()).isEqualTo(teamName);
    }
}
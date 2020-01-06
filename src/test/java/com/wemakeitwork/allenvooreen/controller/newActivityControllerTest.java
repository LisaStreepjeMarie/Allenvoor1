package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
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
import static org.mockito.ArgumentCaptor.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NewActivityController.class)
class newActivityControllerTest {

    @MockBean
    ActivityRepository activityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberDetailsService memberDetailsService;

    @Test
    @WithMockUser(roles = "admin")
    public void shouldReturnNewActivity() throws Exception {
        mockMvc.perform(get("/activity/new"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/newActivity.jsp"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void shouldReturnNewActivityPost() throws Exception {
        String activityname = "test2";
        String activitycategory = "test4";

        mockMvc.perform(post("/activity/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("activityName", activityname)
                .param("activityCategory",activitycategory)
                .flashAttr("activity", new Activity())
                .with(csrf())
        )
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/activity/all"))
                .andExpect(redirectedUrl("/activity/all"));

        ArgumentCaptor<Activity> formObjectArgument = forClass(Activity.class);
        verify(activityRepository, times(1)).save(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(activityRepository);

        Activity formObject = formObjectArgument.getValue();

        Assertions.assertThat(formObject.getActivityName()).isEqualTo(activityname);
        Assertions.assertThat(formObject.getActivityCategory()).isEqualTo(activitycategory);
    }
}
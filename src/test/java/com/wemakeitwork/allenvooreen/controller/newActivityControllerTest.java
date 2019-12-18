package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;


import com.wemakeitwork.allenvooreen.service.MemberDetailsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        String activityname = "test1";
        String activitycategory = "test2";

        Activity testActivity = new Activity();
        testActivity.setActivityName(activityname);
        testActivity.setActivityCategory(activitycategory);
        testActivity.setActivityId(2);

        mockMvc.perform(post("/activity/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("activity", testActivity)
                .with(csrf())
        )
                .andExpect(redirectedUrl("/activity/new"));
    }

}
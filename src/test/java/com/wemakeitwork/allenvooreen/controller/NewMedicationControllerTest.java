package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MedicationServiceInterface;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import com.wemakeitwork.allenvooreen.validator.MedicationValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MedicationController.class)
public class NewMedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MedicationRepository medicationRepository;

    @MockBean
    MemberDetailsService memberDetailsService;

    @MockBean
    MedicationServiceInterface medicationServiceInterface;

    @MockBean
    MedicationValidator medicationValidator;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    TeamRepository teamRepository;

    @MockBean
    ActivityRepository activityRepository;

    @Test
    @WithMockUser(roles = "admin")
    public void shouldReturnNewMedication() throws Exception {
        mockMvc.perform(get("/medication/new"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/newMedication.jsp"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void shouldReturnNewMedicationPost() throws Exception {
        String medicationName = "testmedicijn";
        Integer medicationAmount = 20;
        int medicationRefillAmount = 0;
        String medicationComment = "dagelijks";

        mockMvc.perform(post("/medication/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("medicationName", medicationName)
                .param("medicationAmount", String.valueOf(medicationAmount))
                .param("medicationRefillAmount", String.valueOf(medicationRefillAmount))
                .param("medicationComment", medicationComment)
                .flashAttr("medication", new Medication())
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/medication/\" + team.getTeamId()"))
                .andExpect(redirectedUrl("/medication/\" + team.getTeamId()"));

        ArgumentCaptor<Medication> formObjectArgument = formObjectArgument.forClass(Medication.class);
        /* verify(medicationRepository, times(1)).save(formObjectArgument.capture());
        Mockito.verifyNoMoreInteractions(medicationRepository); */

        Medication formObject = formObjectArgument.getValue();

        Assertions.assertThat(formObject.getMedicationName()).isEqualTo(medicationName);
        Assertions.assertThat(formObject.getMedicationAmount()).isEqualTo(medicationAmount);
        Assertions.assertThat(formObject.getMedicationRefillAmount()).isEqualTo(medicationRefillAmount);
        Assertions.assertThat(formObject.getMedicationComment()).isEqualTo(medicationComment);

    }


}

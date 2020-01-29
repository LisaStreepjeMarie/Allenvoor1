package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MemberDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CalendarController.class)
public class CalendarControllerTest {

    @MockBean
    TeamRepository teamRepository;

    @MockBean
    MemberRepository memberRepository;

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
    public void calendar() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/home.jsp"));
    }

}


//    @Test
//    @WithMockUser(roles = "admin")
//    public void showmyCalender() throws Exception {
//        Member testMember = new Member();
//        testMember.setMemberName("LisaMarie");
//        Set<Team>  teamList= new HashSet<>();
//
//        Principal mockPrincipal = Mockito.mock(Principal.class);
////        Mockito.when(mockPrincipal.getName()).thenReturn("LisaMarie");
////        Mockito.when(memberRepository.findByMemberName(principal.getName())).thenReturn(java.util.Optional.of(testMember));
//
//        mockMvc.perform(get("/calendar/{teamId}", 1))
////                .principal(mockPrincipal))
//                .andExpect(status().isOk())
//                .andExpect(forwardedUrl("/WEB-INF/jsp/calendar.jsp"));
////                .andExpect(model().attribute("teamList", "check"));
////    }
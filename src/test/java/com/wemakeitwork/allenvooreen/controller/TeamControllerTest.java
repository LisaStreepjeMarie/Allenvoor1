package com.wemakeitwork.allenvooreen.controller;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = TeamController.class)
class TeamControllerTest {
//
//    @MockBean
//    TeamRepository teamRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    MemberDetailsService memberDetailsService;
//
//    @Test
//    @WithMockUser(roles = "admin")
//    public void showTeams() throws Exception {
//        mockMvc.perform(get("/team/all"))
//                .andExpect(status().isOk())
//                .andExpect(forwardedUrl("/WEB-INF/jsp/teamOverview.jsp"));
//    }
//
//    @Test
//    @WithMockUser(roles = "admin")
//    void showNewTeam() throws Exception {
//        mockMvc.perform(get("/team/new"))
//                .andExpect(status().isOk())
//                .andExpect(forwardedUrl("/WEB-INF/jsp/newTeam.jsp"));
//    }
//
//    @Test
//    void showChangeTeam() {
//    }
//
//    @Test
//    @WithMockUser(roles = "admin")
//    public void saveOrUpdateTeam() throws Exception {
//        String teamName = "testName";
//
//        mockMvc.perform(post("/team/new")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("teamName", teamName)
//                .flashAttr("team", new Team())
//                .with(csrf())
//        )
//                .andExpect(status().isMovedTemporarily())
//                .andExpect(view().name("redirect:/team/all"))
//                .andExpect(redirectedUrl("/team/all"));
//
//        ArgumentCaptor<Team> formObjectArgument = forClass(Team.class);
//        verify(teamRepository, times(1)).save(formObjectArgument.capture());
//        Mockito.verifyNoMoreInteractions(teamRepository);
//
//        Team formObject = formObjectArgument.getValue();
//
//        Assertions.assertThat(formObject.getTeamName()).isEqualTo(teamName);
//
//    }
}
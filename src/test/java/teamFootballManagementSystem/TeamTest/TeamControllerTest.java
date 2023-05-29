package teamFootballManagementSystem.TeamTest;

import teamFootballManagementSystem.model.Team;
import teamFootballManagementSystem.service.TeamService;
import teamFootballManagementSystem.controller.TeamController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TeamControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TeamService teamService;

    private TeamController teamController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teamController = new TeamController(teamService);
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    void testGetAllTeams() throws Exception {
        List<Team> teams = Arrays.asList(
                new Team(1L, "Team 1", "Coach 1", "Stadium 1"),
                new Team(2L, "Team 2", "Coach 2", "Stadium 2")
        );

        when(teamService.getAllTeams()).thenReturn(teams);

        mockMvc.perform(MockMvcRequestBuilders.get("/teams"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Team 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].coach").value("Coach 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].homeStadium").value("Stadium 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Team 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].coach").value("Coach 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].homeStadium").value("Stadium 2"));

        verify(teamService, times(1)).getAllTeams();
    }

    @Test
    void testGetTeamById() throws Exception {
        Long id = 1L;
        Team team = new Team(id, "Team 1", "Coach 1", "Stadium 1");

        when(teamService.getById(id)).thenReturn(team);

        mockMvc.perform(MockMvcRequestBuilders.get("/teams/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Team 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coach").value("Coach 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.homeStadium").value("Stadium 1"));

        verify(teamService, times(1)).getById(id);
    }

    @Test
    void testAddTeam() throws Exception {
        Team team = new Team(null, "New Team", "New Coach", "New Stadium");
        Team savedTeam = new Team(1L, "New Team", "New Coach", "New Stadium");

        when(teamService.addTeam(any(Team.class))).thenReturn(savedTeam);

        mockMvc.perform(MockMvcRequestBuilders.post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Team\",\"coach\":\"New Coach\",\"homeStadium\":\"New Stadium\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Team"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coach").value("New Coach"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.homeStadium").value("New Stadium"));

        verify(teamService, times(1)).addTeam(any(Team.class));
    }

    @Test
    void testUpdateTeam() throws Exception {
        Long id = 1L;
        Team updatedTeam = new Team(id, "Updated Team", "Updated Coach", "Updated Stadium");
        Team savedTeam = new Team(id, "Updated Team", "Updated Coach", "Updated Stadium");

        when(teamService.updateTeam(eq(id), any(Team.class))).thenReturn(savedTeam);

        mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Team\",\"coach\":\"Updated Coach\",\"homeStadium\":\"Updated Stadium\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Team"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coach").value("Updated Coach"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.homeStadium").value("Updated Stadium"));

        verify(teamService, times(1)).updateTeam(eq(id), any(Team.class));
    }

    @Test
    void testDeleteTeam() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(teamService, times(1)).deleteTeam(id);
    }
}


package teamFootballManagementSystem.TeamTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamFootballManagementSystem.controller.TeamController;

import teamFootballManagementSystem.dto.TeamDTO;
import teamFootballManagementSystem.mapper.PlayerMapper;
import teamFootballManagementSystem.mapper.TeamMapper;
import teamFootballManagementSystem.model.Team;
import teamFootballManagementSystem.service.PlayerService;
import teamFootballManagementSystem.service.TeamService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TeamController.class)
public class TeamControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Mock
    private TeamMapper teamMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTeams() throws Exception {
        TeamDTO teamDTO1 = new TeamDTO(1L, "Roma", "Mourinho", "Roma");
        TeamDTO teamDTO2 = new TeamDTO(2L, "Tirona", "Shehi", "Tirona");
        List<TeamDTO> teamDTOs = Arrays.asList(teamDTO1, teamDTO2);

        when(teamService.getAllTeams()).thenReturn(teamDTOs);

        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Roma"))
                .andExpect(jsonPath("$[0].coach").value("Mourinho"))
                .andExpect(jsonPath("$[0].homeStadium").value("Roma"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Tirona"))
                .andExpect(jsonPath("$[1].coach").value("Shehi"))
                .andExpect(jsonPath("$[1].homeStadium").value("Tirona"));
    }

    @Test
    void getPlayerById() throws Exception {
        Long id = 1L;
        TeamDTO teamDTO = new TeamDTO(id, "Roma", "Mourinho", "Roma");

        when(teamService.getById(id)).thenReturn(teamDTO);

        mockMvc.perform(get("/teams/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Roma"))
                .andExpect(jsonPath("$.coach").value("Mourinho"))
                .andExpect(jsonPath("$.homeStadium").value("Roma"));

        verify(teamService, times(1)).getById(id);
    }

    @Test
    void createPlayer() throws Exception {
        Long id = 1L;
        TeamDTO teamDTO = new TeamDTO(id, "Roma", "Mourinho", "Roma");

        when(teamService.addTeam(any(TeamDTO.class))).thenReturn(teamDTO);
        when(teamMapper.toEntity(any(TeamDTO.class))).thenReturn(new Team(1L, "Roma", "Mourinho", "Roma"));
        when(teamMapper.toDto(any(Team.class))).thenReturn(teamDTO);

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Roma\",\"coach\":\"Mourinho\",\"homeStadium\":\"Roma\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Roma"))
                .andExpect(jsonPath("$.coach").value("Mourinho"))
                .andExpect(jsonPath("$.homeStadium").value("Roma"))
                .andReturn();
    }




    @Test
    void updatePlayer() throws Exception {
        Long id = 1L;
        TeamDTO updatedTeamDTO = new TeamDTO(id, "Tirona", "Shehi", "Tirona");

        when(teamService.updateTeam(eq(id), any(TeamDTO.class))).thenReturn(updatedTeamDTO);
        when(teamMapper.toEntity(any(TeamDTO.class))).thenReturn(new Team(1L, "Tirona", "Shehi", "Tirona"));
        when(teamMapper.toDto(any(Team.class))).thenReturn(updatedTeamDTO);

        mockMvc.perform(put("/teams/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Tirona\",\"coach\":\"Shehi\",\"homeStadium\":\"Tirona\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tirona"))
                .andExpect(jsonPath("$.coach").value("Shehi"))
                .andExpect(jsonPath("$.homeStadium").value("Tirona"));
    }


    @Test
    void deletePlayer() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/teams/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(teamService, times(1)).deleteTeam(id);
    }
}



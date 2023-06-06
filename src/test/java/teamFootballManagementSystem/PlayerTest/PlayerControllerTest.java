package teamFootballManagementSystem.PlayerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import teamFootballManagementSystem.controller.PlayerController;
import teamFootballManagementSystem.dto.PlayerDTO;
import teamFootballManagementSystem.mapper.PlayerMapper;
import teamFootballManagementSystem.service.PlayerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Mock
    private PlayerMapper playerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPlayers() throws Exception {
        PlayerDTO playerDTO1 = new PlayerDTO(1L, "John Doe", "Forward", 10);
        PlayerDTO playerDTO2 = new PlayerDTO(2L, "Jane Smith", "Midfielder", 7);
        List<PlayerDTO> playerDTOs = Arrays.asList(playerDTO1, playerDTO2);

        when(playerService.getAllPlayers()).thenReturn(playerDTOs);

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].position").value("Forward"))
                .andExpect(jsonPath("$[0].jerseyNumber").value(10))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[1].position").value("Midfielder"))
                .andExpect(jsonPath("$[1].jerseyNumber").value(7));
    }

    @Test
    void getPlayerById() throws Exception {
        Long id = 1L;
        PlayerDTO playerDTO = new PlayerDTO(id, "John Doe", "Forward", 10);

        when(playerService.getById(id)).thenReturn(playerDTO);

        mockMvc.perform(get("/players/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.position").value("Forward"))
                .andExpect(jsonPath("$.jerseyNumber").value(10));

        verify(playerService, times(1)).getById(id);
    }

    @Test
    void createPlayer() throws Exception {
        Long id = 1L;
        PlayerDTO playerDTO = new PlayerDTO(id, "John Doe", "Forward", 10);

        when(playerService.addPlayers(playerDTO)).thenReturn(playerDTO);

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1,\"name\":\"John Doe\",\"position\":\"Forward\",\"jerseyNumber\":10}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.position").value("Forward"))
                .andExpect(jsonPath("$.jerseyNumber").value(10))
                .andReturn();
    }



    @Test
    void updatePlayer() throws Exception {
        Long id = 1L;
        PlayerDTO updatedPlayerDTO = new PlayerDTO(id, "Jane Smith", "Midfielder", 7);

        when(playerService.updatePlayers(id, updatedPlayerDTO)).thenReturn(updatedPlayerDTO);

        mockMvc.perform(put("/players/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1,\"name\":\"Jane Smith\",\"position\":\"Midfielder\",\"jerseyNumber\":7}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.position").value("Midfielder"))
                .andExpect(jsonPath("$.jerseyNumber").value(7));
    }

    @Test
    void deletePlayer() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/players/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(playerService, times(1)).deletePlayers(id);
    }
}


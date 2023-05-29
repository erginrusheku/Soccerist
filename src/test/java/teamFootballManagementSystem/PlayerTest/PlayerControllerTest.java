package teamFootballManagementSystem.PlayerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teamFootballManagementSystem.controller.PlayerController;
import teamFootballManagementSystem.model.Player;
import teamFootballManagementSystem.service.PlayerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlayerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PlayerController playerController = new PlayerController(playerService);
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    void getAllPlayers() throws Exception {
        List<Player> players = Arrays.asList(
                new Player(1L, "John Doe", "Forward", 10),
                new Player(2L, "Jane Smith", "Midfielder", 7)
        );
        when(playerService.getAllPlayers()).thenReturn(players);

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

        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    void getPlayerById() throws Exception {
        Long id = 1L;
        Player player = new Player(id, "John Doe", "Forward", 10);
        when(playerService.getById(id)).thenReturn(player);

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
        Player player = new Player(id, "John Doe", "Forward", 10);
        when(playerService.addPlayers(any(Player.class))).thenReturn(player);

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"position\":\"Forward\",\"jerseyNumber\":10}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.position").value("Forward"))
                .andExpect(jsonPath("$.jerseyNumber").value(10));

        verify(playerService, times(1)).addPlayers(any(Player.class));
    }

    @Test
    void updatePlayer() throws Exception {
        Long id = 1L;
        Player updatedPlayer = new Player(id, "Jane Smith", "Midfielder", 7);
        when(playerService.updatePlayers(eq(id), any(Player.class))).thenReturn(updatedPlayer);

        mockMvc.perform(put("/players/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Jane Smith\",\"position\":\"Midfielder\",\"jerseyNumber\":7}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.position").value("Midfielder"))
                .andExpect(jsonPath("$.jerseyNumber").value(7));

        verify(playerService, times(1)).updatePlayers(eq(id), any(Player.class));
    }

    @Test
    void deletePlayer() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/players/{id}", id))
                .andExpect(status().isOk());

        verify(playerService, times(1)).deletePlayers(id);
    }
}

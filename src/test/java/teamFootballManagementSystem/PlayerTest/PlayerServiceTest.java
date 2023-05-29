package teamFootballManagementSystem.PlayerTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import teamFootballManagementSystem.model.Player;
import teamFootballManagementSystem.repository.PlayerRepository;
import teamFootballManagementSystem.service.PlayerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository);
    }

    @Test
    void getAllPlayers() {
        List<Player> players = Arrays.asList(
                new Player(1L, "John Doe", "Forward", 10),
                new Player(2L, "Jane Smith", "Midfielder", 7)
        );
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("John Doe", result.get(0).getName());
        Assertions.assertEquals("Forward", result.get(0).getPosition());
        Assertions.assertEquals(10, result.get(0).getJerseyNumber());
        Assertions.assertEquals("Jane Smith", result.get(1).getName());
        Assertions.assertEquals("Midfielder", result.get(1).getPosition());
        Assertions.assertEquals(7, result.get(1).getJerseyNumber());
    }

    @Test
    void getPlayerById() {
        Long id = 1L;
        Player player = new Player(id, "John Doe", "Forward", 10);
        when(playerRepository.findById(id)).thenReturn(Optional.of(player));

        Player result = playerService.getById(id);

        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("Forward", result.getPosition());
        Assertions.assertEquals(10, result.getJerseyNumber());
    }

    @Test
    void createPlayer() {
        Player player = new Player(null, "John Doe", "Forward", 10);
        Player savedPlayer = new Player(1L, "John Doe", "Forward", 10);
        when(playerRepository.save(any(Player.class))).thenReturn(savedPlayer);

        Player result = playerService.addPlayers(player);

        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("Forward", result.getPosition());
        Assertions.assertEquals(10, result.getJerseyNumber());
    }

    @Test
    void updatePlayer() {
        Long id = 1L;
        Player existingPlayer = new Player(id, "John Doe", "Forward", 10);
        Player updatedPlayer = new Player(id, "John Doe", "Midfielder", 7);
        when(playerRepository.findById(id)).thenReturn(Optional.of(existingPlayer));
        when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

        Player result = playerService.updatePlayers(id, updatedPlayer);

        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("Midfielder", result.getPosition());
        Assertions.assertEquals(7, result.getJerseyNumber());
    }

    @Test
    void deletePlayer() {
        Long id = 1L;

        Assertions.assertDoesNotThrow(() -> playerService.deletePlayers(id));

        verify(playerRepository, times(1)).deleteById(id);
    }
}

package teamFootballManagementSystem.PlayerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import teamFootballManagementSystem.dto.PlayerDTO;
import teamFootballManagementSystem.mapper.PlayerMapper;
import teamFootballManagementSystem.model.Player;
import teamFootballManagementSystem.repository.PlayerRepository;
import teamFootballManagementSystem.service.PlayerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPlayers() {

        Player player1 = new Player(1L, "John Doe", "Forward", 10);
        Player player2 = new Player(2L, "Jane Smith", "Midfielder", 7);
        List<Player> players = Arrays.asList(player1,player2);

        when(playerRepository.findAll()).thenReturn(players);
        when(playerMapper.toDto(player1)).thenReturn(new PlayerDTO(1L, "John Doe", "Forward", 10));
        when(playerMapper.toDto(player2)).thenReturn(new PlayerDTO(2L, "Jane Smith", "Midfielder", 7));

        List<PlayerDTO> result = playerService.getAllPlayers();

        assertEquals(players.size(),result.size());
    }

    @Test
    void getPlayerById() {
        Long id = 1L;
        Player player = new Player(id, "John Doe", "Forward", 10);
        PlayerDTO playerDTO = new PlayerDTO(id, "John Doe", "Forward", 10);

        when(playerRepository.findById(id)).thenReturn(Optional.of(player));
        when(playerMapper.toDto(player)).thenReturn(playerDTO);

        PlayerDTO result = playerService.getById(id);

        assertEquals(playerDTO,result);
    }

    @Test
    void createPlayer() {
        PlayerDTO playerDTO = new PlayerDTO(1L, "Jane Smith", "Midfielder", 7);
        Player player = new Player(1L, "Jane Smith", "Midfielder", 7);

        when(playerMapper.toEntity(playerDTO)).thenReturn(player);
        when(playerMapper.toDto(player)).thenReturn(playerDTO);
        when(playerRepository.save(player)).thenReturn(player);

        PlayerDTO result = playerService.addPlayers(playerDTO);

        assertEquals(playerDTO,result);
    }

    @Test
    void updatePlayer() {
        Long id = 1L;
        PlayerDTO updatedPlayerDTO = new PlayerDTO(id, "Jane Smith", "Midfielder", 7);
        Player updatedPlayer = new Player(id, "Jane Smith", "Midfielder", 7);

        when(playerRepository.findById(id)).thenReturn(Optional.of(new Player()));
        when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);
        when(playerMapper.toDto(updatedPlayer)).thenReturn(updatedPlayerDTO);

        PlayerDTO result = playerService.updatePlayers(id, updatedPlayerDTO);

        assertEquals(updatedPlayerDTO,result);
    }

    @Test
    void deletePlayer() {
        Long id = 1L;

         playerService.deletePlayers(id);

        verify(playerRepository, times(1)).deleteById(id);
    }
}

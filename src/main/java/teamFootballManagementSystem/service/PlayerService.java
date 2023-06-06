package teamFootballManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import teamFootballManagementSystem.dto.PlayerDTO;
import teamFootballManagementSystem.mapper.PlayerMapper;
import teamFootballManagementSystem.model.Player;
import teamFootballManagementSystem.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerDTO> playerDTOS = new ArrayList<>();

        for (Player player : players) {
            PlayerDTO playerDTO = playerMapper.toDto(player);
            playerDTOS.add(playerDTO);
        }

        return playerDTOS;
    }

    public PlayerDTO addPlayers(@RequestBody PlayerDTO playerDTO) {
        Player player = playerMapper.toEntity(playerDTO);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDto(savedPlayer);
    }

    public PlayerDTO getById(@PathVariable Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        return playerMapper.toDto(player);
    }

    public PlayerDTO updatePlayers(@PathVariable Long id, @RequestBody PlayerDTO updatePlayer) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(" Player not found with id: " + id));

            existingPlayer.setName(updatePlayer.getName());
            existingPlayer.setPosition(updatePlayer.getPosition());
            existingPlayer.setJerseyNumber(updatePlayer.getJerseyNumber());

            Player savedPlayer = playerRepository.save(existingPlayer);

            return playerMapper.toDto(savedPlayer);


    }

    public void deletePlayers(@PathVariable Long id) {
        playerRepository.deleteById(id);
    }
}

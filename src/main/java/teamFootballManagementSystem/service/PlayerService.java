package teamFootballManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import teamFootballManagementSystem.model.Player;
import teamFootballManagementSystem.repository.PlayerRepository;
import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayer() {
        return playerRepository.findAll();
    }

    public Player addPlayers(@RequestBody Player player) {
        return playerRepository.save(player);
    }

    public Player getById(@PathVariable Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("\" Player not found with id: \" + id"));
    }

    public Player updatePlayers(@PathVariable Long id, @RequestBody Player updatePlayer) {
        return playerRepository.findById(id).map(player -> {
            player.setName(updatePlayer.getName());
            player.setPosition(updatePlayer.getPosition());
            player.setJerseyNumber(updatePlayer.getJerseyNumber());
            return playerRepository.save(player);
        }).orElseThrow(() -> new IllegalArgumentException("\" Player not found with id: \" + id"));
    }

    public void deletePlayers(@PathVariable Long id) {
        playerRepository.deleteById(id);
    }
}

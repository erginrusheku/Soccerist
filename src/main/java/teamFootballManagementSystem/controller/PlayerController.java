package teamFootballManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamFootballManagementSystem.model.Player;
import teamFootballManagementSystem.service.PlayerService;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayer();
    }

    @PostMapping
    public Player addPlayers(@RequestBody Player player) {
        return playerService.addPlayers(player);
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable Long id) {
        return playerService.getById(id);
    }

    @PutMapping("/{id}")
    public Player updatePlayers(@PathVariable Long id, @RequestBody Player updatePlayer) {
        return playerService.updatePlayers(id, updatePlayer);
    }

    @DeleteMapping("/{id}")
    public void deletePlayers(@PathVariable Long id) {
        playerService.deletePlayers(id);
    }

}

package teamFootballManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamFootballManagementSystem.dto.PlayerDTO;
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
    public List<PlayerDTO> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @PostMapping
    public PlayerDTO addPlayers(@RequestBody PlayerDTO playerDTO) {
        return playerService.addPlayers(playerDTO);
    }

    @GetMapping("/{id}")
    public PlayerDTO getById(@PathVariable Long id) {
        return playerService.getById(id);
    }

    @PutMapping("/{id}")
    public PlayerDTO updatePlayers(@PathVariable Long id, @RequestBody PlayerDTO updatePlayer) {
        return playerService.updatePlayers(id, updatePlayer);
    }

    @DeleteMapping("/{id}")
    public void deletePlayers(@PathVariable Long id) {
        playerService.deletePlayers(id);
    }

}

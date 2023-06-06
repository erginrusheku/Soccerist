package teamFootballManagementSystem.mapper;

import org.springframework.stereotype.Component;
import teamFootballManagementSystem.model.Player;

@Component
public class PlayerMapper {

    public teamFootballManagementSystem.dto.PlayerDTO toDto(Player player){

        teamFootballManagementSystem.dto.PlayerDTO playerDTO = new teamFootballManagementSystem.dto.PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setJerseyNumber(player.getJerseyNumber());

        return playerDTO;
    }

    public Player toEntity(teamFootballManagementSystem.dto.PlayerDTO playerDTO){

        Player player = new Player();
        player.setId(playerDTO.getId());
        player.setName(playerDTO.getName());
        player.setPosition(playerDTO.getPosition());
        player.setJerseyNumber(playerDTO.getJerseyNumber());

        return player;
    }
}

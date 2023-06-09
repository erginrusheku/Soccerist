package teamFootballManagementSystem.mapper;

import org.springframework.stereotype.Component;
import teamFootballManagementSystem.dto.TeamDTO;
import teamFootballManagementSystem.model.Team;

@Component
public class TeamMapper {

    public TeamDTO toDto(Team team){

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setCoach(team.getCoach());
        teamDTO.setHomeStadium(team.getHomeStadium());

        return teamDTO;
    }

    public Team toEntity(TeamDTO teamDTO){

        Team team = new Team();
        team.setId(teamDTO.getId());
        team.setName(teamDTO.getName());
        team.setCoach(teamDTO.getCoach());
        team.setHomeStadium(teamDTO.getHomeStadium());

        return team;
    }
}

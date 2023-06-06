package teamFootballManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamFootballManagementSystem.dto.TeamDTO;
import teamFootballManagementSystem.mapper.TeamMapper;
import teamFootballManagementSystem.model.Team;
import teamFootballManagementSystem.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDTO> teamDTOS = new ArrayList<>();

        for(Team team : teams){
            TeamDTO teamDTO = teamMapper.toDto(team);
            teamDTOS.add(teamDTO);
        }

        return teamDTOS;
    }

    public TeamDTO addTeam(TeamDTO teamDTO) {
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamRepository.save(team);

        return teamMapper.toDto(savedTeam);
    }

    public TeamDTO getById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));

        return teamMapper.toDto(team);
    }

    public TeamDTO updateTeam(Long id, TeamDTO updatedTeam) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));

            existingTeam.setName(updatedTeam.getName());
            existingTeam.setCoach(updatedTeam.getCoach());
            existingTeam.setHomeStadium(updatedTeam.getHomeStadium());

            Team savedTeam = teamRepository.save(existingTeam);

            return teamMapper.toDto(savedTeam);

    }

    public void deleteTeam( Long id) {
        teamRepository.deleteById(id);
    }
}

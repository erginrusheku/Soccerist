package teamFootballManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import teamFootballManagementSystem.model.Team;
import teamFootballManagementSystem.repository.TeamRepository;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team addTeam( Team team) {
        return teamRepository.save(team);
    }

    public Team getById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("\"Team not found with id:\"+ id"));
    }

    public Team updateTeam( Long id, Team updatedTeam) {
        return teamRepository.findById(id).map(team -> {
            team.setName(updatedTeam.getName());
            team.setCoach(updatedTeam.getCoach());
            team.setHomeStadium(updatedTeam.getHomeStadium());
            return teamRepository.save(team);
        }).orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));
    }

    public void deleteTeam( Long id) {
        teamRepository.deleteById(id);
    }
}

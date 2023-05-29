package teamFootballManagementSystem.TeamTest;

import teamFootballManagementSystem.model.Team;
import teamFootballManagementSystem.repository.TeamRepository;
import teamFootballManagementSystem.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TeamServiceTest {
    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teamService = new TeamService(teamRepository);
    }

    @Test
    void testGetAllTeams() {
        List<Team> teams = Arrays.asList(
                new Team(1L, "Team 1", "Coach 1", "Stadium 1"),
                new Team(2L, "Team 2", "Coach 2", "Stadium 2")
        );

        when(teamRepository.findAll()).thenReturn(teams);

        List<Team> result = teamService.getAllTeams();

        assertEquals(2, result.size());
        assertEquals("Team 1", result.get(0).getName());
        assertEquals("Coach 1", result.get(0).getCoach());
        assertEquals("Stadium 1", result.get(0).getHomeStadium());
        assertEquals("Team 2", result.get(1).getName());
        assertEquals("Coach 2", result.get(1).getCoach());
        assertEquals("Stadium 2", result.get(1).getHomeStadium());

        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void testAddTeam() {
        Team team = new Team(null, "New Team", "New Coach", "New Stadium");
        Team savedTeam = new Team(1L, "New Team", "New Coach", "New Stadium");

        when(teamRepository.save(any(Team.class))).thenReturn(savedTeam);

        Team result = teamService.addTeam(team);

        assertEquals(1L, result.getId());
        assertEquals("New Team", result.getName());
        assertEquals("New Coach", result.getCoach());
        assertEquals("New Stadium", result.getHomeStadium());

        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Team team = new Team(id, "Team 1", "Coach 1", "Stadium 1");

        when(teamRepository.findById(id)).thenReturn(Optional.of(team));

        Team result = teamService.getById(id);

        assertEquals(1L, result.getId());
        assertEquals("Team 1", result.getName());
        assertEquals("Coach 1", result.getCoach());
        assertEquals("Stadium 1", result.getHomeStadium());

        verify(teamRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateTeam() {
        Long id = 1L;
        Team updatedTeam = new Team(id, "Updated Team", "Updated Coach", "Updated Stadium");
        Team savedTeam = new Team(id, "Updated Team", "Updated Coach", "Updated Stadium");

        when(teamRepository.findById(id)).thenReturn(Optional.of(savedTeam));
        when(teamRepository.save(any(Team.class))).thenReturn(savedTeam);

        Team result = teamService.updateTeam(id, updatedTeam);

        assertEquals(1L, result.getId());
        assertEquals("Updated Team", result.getName());
        assertEquals("Updated Coach", result.getCoach());
        assertEquals("Updated Stadium", result.getHomeStadium());

        verify(teamRepository, times(1)).findById(id);
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void testDeleteTeam() {
        Long id = 1L;

        teamService.deleteTeam(id);

        verify(teamRepository, times(1)).deleteById(id);
    }
}


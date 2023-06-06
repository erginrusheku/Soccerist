package teamFootballManagementSystem.TeamTest;

import org.mockito.InjectMocks;
import teamFootballManagementSystem.dto.TeamDTO;
import teamFootballManagementSystem.mapper.TeamMapper;
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
    @InjectMocks
    private TeamService teamService;
    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTeams() {
        Team team1 = new Team(1L, "Team 1", "Coach 1", "Stadium 1");
        Team team2 = new Team(2L, "Team 2", "Coach 2", "Stadium 2");
        List<Team> teams = Arrays.asList(team1,team2);

        when(teamRepository.findAll()).thenReturn(teams);
        when(teamMapper.toDto(team1)).thenReturn(new TeamDTO(1L, "Team 1", "Coach 1", "Stadium 1"));
        when(teamMapper.toDto(team2)).thenReturn(new TeamDTO(2L, "Team 2", "Coach 2", "Stadium 2"));

        List<TeamDTO> result = teamService.getAllTeams();

        assertEquals(teams.size(),result.size());
    }

    @Test
    void testAddTeam() {
        TeamDTO teamDTO = new TeamDTO(1L, "New Team", "New Coach", "New Stadium");
        Team team = new Team(1L, "New Team", "New Coach", "New Stadium");

        when(teamMapper.toEntity(teamDTO)).thenReturn(team);
        when(teamMapper.toDto(team)).thenReturn(teamDTO);
        when(teamRepository.save(team)).thenReturn(team);

        TeamDTO result = teamService.addTeam(teamDTO);

        assertEquals(teamDTO,result);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Team team = new Team(id, "Team 1", "Coach 1", "Stadium 1");
        TeamDTO teamDTO= new TeamDTO(id, "Team 1", "Coach 1", "Stadium 1");

        when(teamRepository.findById(id)).thenReturn(Optional.of(team));
        when(teamMapper.toDto(team)).thenReturn(teamDTO);

        TeamDTO result = teamService.getById(id);

        assertEquals(teamDTO,result);
    }

    @Test
    void testUpdateTeam() {
        Long id = 1L;
        TeamDTO updatedTeamDTO = new TeamDTO(id, "Updated Team", "Updated Coach", "Updated Stadium");
        Team updatedTeam = new Team(id, "Updated Team", "Updated Coach", "Updated Stadium");

        when(teamRepository.findById(id)).thenReturn(Optional.of(new Team()));
        when(teamRepository.save(any(Team.class))).thenReturn(updatedTeam);
        when(teamMapper.toDto(updatedTeam)).thenReturn(updatedTeamDTO);

        TeamDTO result = teamService.updateTeam(id, updatedTeamDTO);

       assertEquals(updatedTeamDTO,result);
    }

    @Test
    void testDeleteTeam() {
        Long id = 1L;

        teamService.deleteTeam(id);

        verify(teamRepository, times(1)).deleteById(id);
    }
}


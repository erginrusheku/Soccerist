package teamFootballManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamFootballManagementSystem.model.Team;
import teamFootballManagementSystem.service.TeamService;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private final TeamService teamService;
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    @GetMapping
    public List<Team> getAllTeams(){return teamService.getAllTeams();}
    @PostMapping
    public Team addTeam(@RequestBody Team team){
        return teamService.addTeam(team);
    }
    @GetMapping("/{id}")
    public Team getById(@PathVariable Long id){
        return teamService.getById(id);
    }
    @PutMapping("/{id}")
    public Team updateTeam(@PathVariable Long id, @RequestBody Team updatedTeam){
        return teamService.updateTeam(id,updatedTeam);
    }
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id){
         teamService.deleteTeam(id);
    }


}

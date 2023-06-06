package teamFootballManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamFootballManagementSystem.dto.TeamDTO;
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
    public List<TeamDTO> getAllTeams(){return teamService.getAllTeams();}
    @PostMapping
    public TeamDTO addTeam(@RequestBody TeamDTO teamDTO){
        return teamService.addTeam(teamDTO);
    }
    @GetMapping("/{id}")
    public TeamDTO getById(@PathVariable Long id){
        return teamService.getById(id);
    }
    @PutMapping("/{id}")
    public TeamDTO updateTeam(@PathVariable Long id, @RequestBody TeamDTO updatedTeam){
        return teamService.updateTeam(id,updatedTeam);
    }
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id){
         teamService.deleteTeam(id);
    }


}

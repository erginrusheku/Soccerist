package teamFootballManagementSystem.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String coach;
    private String homeStadium;

    public Team(){}

    public Team(Long id, String name, String coach, String homeStadium) {
        this.id = id;
        this.name = name;
        this.coach = coach;
        this.homeStadium = homeStadium;
    }
}

package teamFootballManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamFootballManagementSystem.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}

package teamFootballManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamFootballManagementSystem.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}

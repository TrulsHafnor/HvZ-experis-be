package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
}

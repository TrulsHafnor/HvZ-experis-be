package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player,Integer> {

    @Query(value = "SELECT * FROM Player p WHERE p.game_id = :gameId and p.player_id = :playerId", nativeQuery = true)
    Player getPlayerInGame(@Param("gameId") Integer gameId, @Param("playerId") Integer playerId);
}

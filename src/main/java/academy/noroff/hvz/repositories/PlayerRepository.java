package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Integer> {

    @Query(value = "SELECT * FROM Player p WHERE p.game_id = :gameId and p.player_id = :playerId", nativeQuery = true)
    Player getPlayerInGame(@Param("gameId") Integer gameId, @Param("playerId") Integer playerId);
    @Query(value = "SELECT COUNT(*) FROM Player p WHERE p.bite_code = :biteCode and p.game_id = :gameId Limit 1", nativeQuery = true)
    Integer checkIfBiteCodeExists(@Param("biteCode") String biteCode, @Param("gameId") int gameId);

    @Query(value = "SELECT * FROM Player p WHERE p.bite_code = :biteCode and p.game_id = :gameId Limit 1", nativeQuery = true)
    Player findPlayerWhitBiteCode(@Param("gameId") int gameId, @Param("biteCode") String biteCode);
}

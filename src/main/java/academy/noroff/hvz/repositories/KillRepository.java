package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface KillRepository extends JpaRepository<Kill, Integer> {
    @Query(value = "SELECT * FROM Kill k WHERE k.game_id = :gameId", nativeQuery = true)
    Collection<Kill> findAllKillsInGame(@Param("gameId") Integer gameId);

    @Query(value = "SELECT * FROM Kill k WHERE k.game_id = :gameId and k.kill_id = :killId Limit 1", nativeQuery = true)
    Optional<Kill> findKillInGameById(@Param("gameId") int gameId, @Param("killId") int killId);
    @Modifying
    @Query(value = "DELETE FROM Kill k WHERE k.game_id = :gameId", nativeQuery = true)
    void deleteAllKillInGame(@Param("gameId") int gameId);
}

package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Integer> {
    @Query(value = "SELECT * FROM Squad s WHERE s.game_id = :gameId and s.squad_id = :squadId", nativeQuery = true)
    Optional<Squad> findSquadInGame(@Param("gameId") Integer gameId, @Param("squadId") Integer squadId);

    @Query(value = "SELECT * FROM Squad s WHERE s.game_id = :gameId", nativeQuery = true)
    Collection<Squad> findAllSquadsInGame(@Param("gameId") Integer gameId);

    @Query(value = "SELECT squad_id FROM Squad s WHERE s.game_id = :gameId and s.player_id = :playerId", nativeQuery = true)
    Integer findSquadIdWhitPlayerIdAndGameId(@Param("gameId") Integer gameId, @Param("playerId") Integer playerId);

}

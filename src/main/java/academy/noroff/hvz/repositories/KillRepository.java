package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Kill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface KillRepository extends JpaRepository<Kill, Integer> {
    @Query(value = "SELECT * FROM Kill k WHERE k.game_id = :gameId", nativeQuery = true)
    Collection<Kill> findAllKillsInGame(@Param("gameId") Integer gameId);
}

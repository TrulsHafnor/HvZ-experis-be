package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.SquadCheckin;
import academy.noroff.hvz.models.SquadMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SquadCheckinRepository extends JpaRepository<SquadCheckin,Integer> {
    @Query(value = "SELECT * FROM Squad_checkin s WHERE s.game_id = :gameId and s.squad_member_id = :playerId", nativeQuery = true)
    Optional<SquadCheckin> getSquadCheckinByGameIdAndPlayerId(@Param("gameId") Integer gameId, @Param("playerId") Integer playerId);

    @Query(value = "SELECT * FROM Squad_checkin s INNER JOIN Squad_member sm ON s.squad_member_id = sm.member_id WHERE s.game_id = :gameId and sm.squad_id = :squadId", nativeQuery = true)
    Collection<SquadCheckin> findAllCheckinsInSquadWhitGameId(@Param("gameId") Integer gameId, @Param("squadId") Integer squadId);
    //SELECT ID FROM Customers INNER
    //JOIN Orders ON Customers.ID = Orders.ID

}

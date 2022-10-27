package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.SquadMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SquadMemberRepository extends JpaRepository<SquadMember, Integer> {

    @Query(value = "SELECT * FROM Squad_member s WHERE s.squad_id = :squadID and s.member_id = :playerId", nativeQuery = true)
    Optional<SquadMember> getSquadMemberInSquadWhitId(@Param("squadID") Integer squadID, @Param("playerId") Integer playerId);

    @Query(value = "SELECT * FROM Squad_member s WHERE s.member_id = :playerId", nativeQuery = true)
    Optional<SquadMember> checkIfPlayerIsInSquad(@Param("playerId") Integer playerId);

    //findSquadMemberWhitPlayerId
    @Query(value = "SELECT * FROM Squad_member s WHERE s.member_id = :playerId", nativeQuery = true)
    Optional<SquadMember> getSquadMemberWhitPlayerID(@Param("playerId") Integer playerId);

    @Query(value = "SELECT * FROM Squad_member s WHERE s.squad_id = :squadId", nativeQuery = true)
    Collection<SquadMember> getAllSquadMembersInSquad(@Param("squadId") Integer squadId);

    @Modifying
    @Query(value = "DELETE FROM Squad_member s WHERE s.squad_id = :squadId", nativeQuery = true)
    void deleteAllSquadMembersInSquad(@Param("squadId") int squadId);

    @Modifying
    @Query(value = "DELETE FROM Squad_member s WHERE s.squad_member_id = :squadMemberID", nativeQuery = true)
    void deleteSquadMemberById(@Param("squadMemberID") int squadMemberID);
}

package academy.noroff.hvz.repositories;

import academy.noroff.hvz.enums.MissionVisibility;
import academy.noroff.hvz.models.Mission;
import com.sun.xml.bind.v2.model.core.EnumLeafInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MissionRepository extends JpaRepository<Mission,Integer> {
    @Query(value = "SELECT * FROM Mission m WHERE m.game_id = :gameId and m.mission_id = :missionId", nativeQuery = true)
    Mission getMissionInGame(@Param("gameId") Integer gameId, @Param("missionId") Integer playerId);

    @Query(value = "SELECT * FROM Mission m where m.game_id = :gameId AND m.mission_visibility = :missionVisibility or m.mission_visibility ='GLOBAL'", nativeQuery = true)
    Set<Mission> getVisibilityOfMission(@Param("gameId") Integer gameId, @Param("missionVisibility") String missionVisibility);

    @Query(value = "SELECT * FROM Mission m where m.game_id = :gameId AND m.mission_visibility = :missionVisibility or m.mission_visibility ='GLOBAL'", nativeQuery = true)
    Mission getVisibilityOfASingularMission(@Param("gameId") Integer gameId, @Param("missionVisibility") String missionVisibility);

}

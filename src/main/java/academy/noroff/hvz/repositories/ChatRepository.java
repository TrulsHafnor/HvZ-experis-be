package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Kill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {

    @Query(value = "SELECT * FROM Chat c WHERE c.game_id = :gameId AND c.human = :cantSee AND c.global = false", nativeQuery = true)
    Collection<Chat> findChatInGameForPlayer(@Param("gameId") Integer gameId, @Param("cantSee") boolean cantSee);

    @Query(value = "SELECT * FROM Chat c WHERE c.game_id = :gameId AND c.global", nativeQuery = true)
    Collection<Chat> findGlobalChats(@Param("gameId") Integer gameId);

    @Query(value = "SELECT * FROM Chat c WHERE c.game_id = :gameId AND c.global = false AND c.squad_id = :squadId", nativeQuery = true)
    Collection<Chat> findSquadChats(@Param("gameId") Integer gameId, @Param("squadId") Integer squadId);
}

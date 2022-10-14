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
    // TODO: 10/13/2022 ikke ta med squad når det kommer 
    @Query(value = "SELECT * FROM Chat c WHERE c.game_id = :gameId AND c.is_human = :cantSee OR  c.game_id = :gameId AND c.is_global=true", nativeQuery = true)
    Collection<Chat> findChatInGameForPlayer(@Param("gameId") Integer gameId, @Param("cantSee") boolean cantSee);
}

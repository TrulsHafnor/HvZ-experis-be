package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {

}

package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission,Integer> {
}

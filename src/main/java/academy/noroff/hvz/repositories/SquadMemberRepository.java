package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.SquadMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadMemberRepository extends JpaRepository<SquadMember, Integer> {

}

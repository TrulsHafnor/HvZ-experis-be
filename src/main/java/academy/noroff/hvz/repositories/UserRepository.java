package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}

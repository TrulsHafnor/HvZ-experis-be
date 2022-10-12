package academy.noroff.hvz.repositories;

import academy.noroff.hvz.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,String> {
}

package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.UserAlreadyExistsException;
import academy.noroff.hvz.exeptions.UserNotFoundException;
import academy.noroff.hvz.models.AppUser;
import academy.noroff.hvz.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser findById(String id) {
        return userRepository.findById(extractId(id)).orElseThrow(() -> new UserNotFoundException(
                "User by id " + id + " was not found"
        ));
    }

    public Collection<AppUser> findAll() {
        return userRepository.findAll();
    }

    AppUser add(AppUser appUser) {
        if (userRepository.existsById(appUser.getId()))
            throw new UserAlreadyExistsException("User by id " + appUser.getId() + " already exist");
        return userRepository.save(appUser);
    }

    public AppUser add(String id) {
        // Prevents internal server error for duplicates
        if (userRepository.existsById(id))
            throw new UserAlreadyExistsException("User by id " + id + " already exist");
        // Create new user
        AppUser appUser = new AppUser();
        appUser.setId(extractId(id));
        return userRepository.save(appUser);
    }

    public void update(AppUser appUser) {
        userRepository.save(appUser);
    }

    public void delete(String id) {
        userRepository.deleteById(extractId(id));
    }

    private String extractId(String input) {
        int offset = input.indexOf("|");
        return input.substring(offset + 1);
    }
}

package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.UserAlreadyExistsException;
import academy.noroff.hvz.exeptions.UserNotFoundException;
import academy.noroff.hvz.models.User;
import academy.noroff.hvz.repositories.GameRepository;
import academy.noroff.hvz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String id) {return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
            "User by id "+ id + " was not found"
    ));}

    public Collection<User> findAll() {
        return userRepository.findAll();
    };

    User add(User user) {
        if(userRepository.existsById(user.getId()))
            throw new UserAlreadyExistsException("User by id "+ user.getId() + " already exist");
        return userRepository.save(user);
    };
}

package academy.noroff.hvz.controllers;

import academy.noroff.hvz.models.User;
import academy.noroff.hvz.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
// TODO: 10/7/2022 fix for later (Sondre sec master)
@CrossOrigin(origins = {
        "https://hvz-fe-noroff.herokuapp.com/",
        "http://localhost:3000"
    }
)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("current")
    public ResponseEntity getCurrentlyLoggedInUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(
                userService.findById(
                        jwt.getClaimAsString("sub")
                )
        );
    }

    @PostMapping("register")
    public ResponseEntity addNewUserFromJwt(@AuthenticationPrincipal Jwt jwt) {
        String id = jwt.getClaimAsString("sub");
        String email = jwt.getClaimAsString("email");
        String nickname = jwt.getClaimAsString("nickname");
        //User user = userService.add(new User(id, email, nickname));
        URI uri = URI.create("api/v1/users/"); // +user.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

}

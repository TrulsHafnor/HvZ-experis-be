package academy.noroff.hvz.controllers;

import academy.noroff.hvz.services.GameService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    public GameController (GameService gameService) {
        this.gameService = gameService;
    }
}

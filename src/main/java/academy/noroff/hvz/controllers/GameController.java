package academy.noroff.hvz.controllers;

import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    public GameController (GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("{id}")
    public ResponseEntity getGameById(@PathVariable("id") int id) {
        Game game = gameService.findGameById(id);
        return new ResponseEntity(game, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllGames() {
        List<Game> games = gameService.finAllGames();
        return new ResponseEntity(games, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addGame (@RequestBody Game game) {
       Game newGame = gameService.addGame(game);
       return new ResponseEntity(newGame, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteGame (@PathVariable("id") int id) {
        gameService.deleteGame(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*@PutMapping("{id}")
    public ResponseEntity updateGame(@RequestBody Game game) {
        if(game.getId() != id) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Game updateGame = gameService.updateGame(game);
        return new ResponseEntity(updateGame, HttpStatus.NO_CONTENT)
    }*/
}

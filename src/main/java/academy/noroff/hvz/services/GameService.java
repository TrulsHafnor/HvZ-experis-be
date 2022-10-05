package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.GameNotFoundException;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService (GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    /**
     * Returns list of all games
     * @return
     */
    public List<Game> finAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Returns game whit id or throws GameNotFoundException
     * @param id
     * @return
     */
    public Game findGameById (int id) {
        return gameRepository.findById(id). orElseThrow(
                () -> new GameNotFoundException("Game by id "+ id + " was not found"));
    }

    /**
     * Add game to database
     * @param game
     * @return
     */
    public Game addGame (Game game) {
        return gameRepository.save(game);
    }

    /**
     * Delete game from database
     * @param id
     */
    // TODO: 10/5/2022 Admin skal kun slette games
    public void deleteGame(int id) {
        // TODO: 10/5/2022 Cascade delete (Drøyer denne til vi har mer fyll i applikasjonen)
        gameRepository.deleteById(id);
    }

    // TODO: 10/5/2022 admin only
    /**
     * Update game
     * @param game
     * @return
     */
    public Game updateGame (Game game) {
        // TODO: 10/5/2022 Sjekk om denne er riktig når vi har aktivt game
        return gameRepository.save(game);
    }

}

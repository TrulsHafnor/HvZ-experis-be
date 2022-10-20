package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.GameNotFoundException;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final KillService killService;
    private final MissionService missionService;
    private final PlayerService playerService;

    @Autowired
    public GameService (GameRepository gameRepository, KillService killService, MissionService missionService, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.killService = killService;
        this.missionService = missionService;
        this.playerService = playerService;
    }


    /**
     * Returns list of all games
     * @return
     */
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Returns game whit id or throws GameNotFoundException
     * @param id
     * @return
     */
    public Game findGameById (int id) {
        System.out.println("DETTE ER GAME ID I service" + id);
        return gameRepository.findById(id).orElseThrow(
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
    @Transactional
    public void deleteGame(int id) {
        killService.deleteAllKillsWhitGameId(id);
        missionService.deleteAllMissionsInGame(id);
        playerService.deleteAllPlayersInGame(id);
        gameRepository.deleteById(id);
    }

    /**
     * Update game
     * @param game
     * @return
     */
    public Game updateGame (Game game) {
        return gameRepository.save(game);
    }

    /**
     * Get all players in game
     * @param id
     * @return
     */
    public Collection<Player> getPlayersInGames(int id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(()->new GameNotFoundException("Game by id "+ id + " was not found"));
        return game.getPlayers();
    }

}

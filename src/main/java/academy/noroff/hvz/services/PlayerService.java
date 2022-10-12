package academy.noroff.hvz.services;


import academy.noroff.hvz.exeptions.PlayerNotFoundException;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService (PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    /**
     * Find all player by id
     * @param id
     * @return
     */
    public Player findPlayerById (int id) {
        return playerRepository.findById(id). orElseThrow(
                () -> new PlayerNotFoundException("Player by id "+ id + " was not found"));
    }

    /**
     * Find player whit bitecode
     * @param gameId
     * @param bitecode
     * @return
     */
    public Player findPlayerWhitBiteCode(int gameId, String bitecode) {
        return playerRepository.findPlayerWhitBiteCode(gameId,bitecode);
    }


    /**
     * find all payers
     * @return
     */
    public Collection<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     * Find player in game
     * @param gameId
     * @param playerId
     * @return
     */
    public Player findPlayerInGame(int gameId, int playerId) {
        return playerRepository.getPlayerInGame(gameId, playerId). orElseThrow(
                () -> new PlayerNotFoundException("Cant find player by id "+ playerId + " ing game by id" + gameId));

    }

    /**
     * Add player
     * @param player
     * @return
     */
    public Player addPlayerToGame (Player player) {
        if (playerRepository.checkIfBiteCodeExists(player.getBiteCode(), player.getGame().getId()) !=0) {
            do {
                player.setBiteCode("holderValue");
            } while (playerRepository.checkIfBiteCodeExists(player.getBiteCode(), player.getGame().getId()) != 0);
        }
        return playerRepository.save(player);
    }


    /**
     * Update user
     * @param player
     * @return
     */
    public Player updatePlayer (Player player) {
        return playerRepository.save(player);
    }


    /**
     * delete player
     * @param id
     */
    public void deletePlayer(int id) {
        // TODO: 10/5/2022 Cascade delete (Drøyer denne til vi har mer fyll i applikasjonen)
        playerRepository.deleteById(id);
    }

    /**
     * Delete all players in game
     * @param gameId
     */
    public void deleteAllPlayersInGame(int gameId) {
        playerRepository.deleteAllPlayersInGame(gameId);
    }
}

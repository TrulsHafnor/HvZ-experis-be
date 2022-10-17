package academy.noroff.hvz.services;


import academy.noroff.hvz.exeptions.PlayerNotFoundException;
import academy.noroff.hvz.exeptions.UserAlreadyHasPlayerException;
import academy.noroff.hvz.models.AppUser;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final UserService userService;

    public PlayerService (PlayerRepository playerRepository, UserService userService) {
        this.playerRepository = playerRepository;
        this.userService = userService;
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
        return playerRepository.findPlayerWhitBiteCode(gameId,bitecode).orElseThrow(
                () -> new PlayerNotFoundException("Cant find player whit bitecode " + bitecode + " in game by id " + gameId));
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
        AppUser appUser = userService.findById(player.getUser().getId());
        if (appUser.getPlayer() != null) {
            throw new UserAlreadyHasPlayerException("User already has a player in game");
        }
        setUniqueBiteCode(player);
        return playerRepository.save(player);
    }

    public Player addNewPlayerToGame(Game game, String userId) {
        AppUser appUser = userService.findById(userId);
        if (appUser.getPlayer() != null) {
            throw new UserAlreadyHasPlayerException("User already has a player in game");
        }
        Player player = new Player();
        player.setGame(game);
        player.setUser(appUser);
        player.setHuman(true);
        player.setPatientZero(false);
        setUniqueBiteCode(player);
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
        // TODO: 17.10.2022 bruk game id 
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

    private void setUniqueBiteCode(Player player) {
        if (playerRepository.checkIfBiteCodeExists(player.getBiteCode(), player.getGame().getId()) !=0) {
            do {
                player.setBiteCode("holderValue");
            } while (playerRepository.checkIfBiteCodeExists(player.getBiteCode(), player.getGame().getId()) != 0);
        }
    }
}

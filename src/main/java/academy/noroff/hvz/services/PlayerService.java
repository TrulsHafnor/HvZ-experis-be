package academy.noroff.hvz.services;


import academy.noroff.hvz.exeptions.PlayerNotFoundException;
import academy.noroff.hvz.exeptions.UserAlreadyHasPlayerException;
import academy.noroff.hvz.models.AppUser;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.Squad;
import academy.noroff.hvz.repositories.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final UserService userService;
    private final SquadService squadService;

    public PlayerService (PlayerRepository playerRepository, UserService userService, SquadService squadService) {
        this.playerRepository = playerRepository;
        this.userService = userService;
        this.squadService=squadService;

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
        player.setBiteCode("Dummy value");
        setUniqueBiteCode(player);
        return playerRepository.save(player);
    }


    /**
     * Update user
     * @param player
     * @return
     */
    public Player updatePlayer (Player player) {
        if (player.isHuman())
            player.setBiteCode("dummy");
        player.setUser(playerRepository.findById(player.getId()).get().getUser());
        return playerRepository.save(player);
    }


    /**
     * delete player
     * @param id
     */
    @Transactional
    public void deletePlayer(int gameId, int playerId) {
        Player player = findPlayerInGame(gameId,playerId);
        if (player.getMembership() != null) {
           /* //checks game id and player id
            // TODO: 10/24/2022 demme retunerer null
            Squad squad = squadService.findSquadInGame(gameId, player.getMembership().getSquad().getId());

            if (squad.getPlayer().getId()== playerId) {
                squadService.updateSquadBeforeDeletingLeader(squad);
            }*/

            squadService.leaveSquad(gameId, playerId);

        }
        playerRepository.deleteById(playerId);
    }

    public void leavePlayer(int gameId, int playerId) {
        Player player = playerRepository.getPlayerInGame(gameId, playerId). orElseThrow(
                () -> new PlayerNotFoundException("Cant find player by id "+ playerId + " ing game by id" + gameId));
        player.setUser(null);
        playerRepository.save(player);
    }

    private void setUniqueBiteCode(Player player) {
        if (playerRepository.checkIfBiteCodeExists(player.getBiteCode(), player.getGame().getId()) !=0) {
            do {
                player.setBiteCode("holderValue");
            } while (playerRepository.checkIfBiteCodeExists(player.getBiteCode(), player.getGame().getId()) != 0);
        }
    }
}

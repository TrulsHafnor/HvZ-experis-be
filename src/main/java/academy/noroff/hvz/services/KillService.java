package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.KillNotFoundException;
import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.repositories.KillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class KillService {
    private final KillRepository killRepository;
    private final PlayerService playerService;


    @Autowired
    public KillService (KillRepository killRepository, PlayerService playerService) {
        this.killRepository = killRepository;
        this.playerService = playerService;
    }


    /**
     * Returns collection og all kills in game
     * @param id
     * @return
     */
    public Collection<Kill> findAllKillsInGame(int id) {
        return killRepository.findAllKillsInGame(id);
    }

    /**
     * Find kill by id
     * @param id
     * @return
     */
    public Kill findKillById(int id) {
        return killRepository.findById(id).orElseThrow(
                () -> new KillNotFoundException("Kill by id "+ id + " was not found"));
    }

    /**
     * Find all kills in game whit supplied id
     * @param gameId
     * @param killId
     * @return
     */
    public Kill findKillInGameById(int gameId, int killId) {
        return killRepository.findKillInGameById(gameId, killId).orElseThrow(
                () -> new KillNotFoundException("Kill by id "+ killId + " was not found"));
    }

    /**
     * Register a kill whit player and set the player tht died to zombie
     * @param kill
     * @param gameId
     * @param bitecode
     * @return
     */
    public boolean createKill(Kill kill, int gameId, String bitecode) {
        Player player = playerService.findPlayerWhitBiteCode(gameId,bitecode);
        if (player == null || !player.isHuman()) {
            return false;
        }
        player.setHuman(false);
        playerService.updatePlayer(player);
        killRepository.save(kill);
        return true;
    }

    /**
     * Delete a kill from the game and set the player back to human
     * @param kill_id
     */
    public void deleteKill(int kill_id) {
        //find player that is dead and updates values
        Player player = setPlayerValuesAfterDelete(findKillById(kill_id).getPlayerDeath());
        //delete kill
        killRepository.deleteById(kill_id);
        //update player to alive
        playerService.updatePlayer(player);
    }

    private Player setPlayerValuesAfterDelete(Player player) {
        //player is now alive
        player.setHuman(true);
        //give new bitecode
        player.setBiteCode("dummy value");
        //player have zero death value
        player.setDeath(new Kill());
        return player;
    }

    /**
     * Updates a kill
     * @param kill
     * @return
     */
    public Kill updateKill (Kill kill) {
        return killRepository.save(kill);
    }

    /**
     * Get kill from game
     * @param game_id
     * @param kill_id
     * @return
     */
    public Kill getKillInGame(int game_id, int kill_id) {
        return killRepository.findKillInGameById(game_id, kill_id).orElseThrow(
                () -> new KillNotFoundException("Cant find mission by id "+ kill_id + " ing game " + game_id));
    }

    /**
     * Delete all kills from a game
     * @param gameId
     */
    public void deleteAllKillsWhitGameId(int gameId) {
        killRepository.deleteAllKillInGame(gameId);
    }

}

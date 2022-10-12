package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.GameNotFoundException;
import academy.noroff.hvz.exeptions.KillNotFoundException;
import academy.noroff.hvz.exeptions.MissionNotFoundException;
import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.Mission;
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


    public Collection<Kill> findAllKillsInGame(int id) {
        return killRepository.findAllKillsInGame(id);
    }

    public Kill findKillById(int id) {
        return killRepository.findById(id).orElseThrow(
                () -> new KillNotFoundException("Kill by id "+ id + " was not found"));
    }

    public Kill findKillInGameById(int gameId, int killId) {
        return killRepository.findKillInGameById(gameId, killId).orElseThrow(
                () -> new KillNotFoundException("Kill by id "+ killId + " was not found"));
    }

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

    public Kill updateKill (Kill kill) {
        return killRepository.save(kill);
    }

    public Kill getKillInGame(int game_id, int kill_id) {
        return killRepository.findKillInGameById(game_id, kill_id).orElseThrow(
                () -> new KillNotFoundException("Cant find mission by id "+ kill_id + " ing game " + game_id));
    }

    public void deleteAllKillsWhitGameId(int gameId) {
        killRepository.deleteAllKillInGame(gameId);
    }

}

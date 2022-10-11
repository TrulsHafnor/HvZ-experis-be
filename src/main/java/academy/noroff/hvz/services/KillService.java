package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.GameNotFoundException;
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

    public void deleteKill(int id) {
        // TODO: 10/5/2022 Cascade delete (Drøyer denne til vi har mer fyll i applikasjonen)
        killRepository.deleteById(id);
    }

    public Kill updateKill (Kill kill) {
        return killRepository.save(kill);
    }

}

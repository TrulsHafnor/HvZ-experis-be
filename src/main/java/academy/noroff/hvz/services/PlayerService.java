package academy.noroff.hvz.services;


import academy.noroff.hvz.exeptions.GameNotFoundException;
import academy.noroff.hvz.exeptions.PlayerNotFoundException;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService (PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player findPlayerById (int id) {
        return playerRepository.findById(id). orElseThrow(
                () -> new PlayerNotFoundException("Player by id "+ id + " was not found"));
    }


}

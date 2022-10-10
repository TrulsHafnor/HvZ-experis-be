package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.GameNotFoundException;
import academy.noroff.hvz.exeptions.KillNotFoundException;
import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.repositories.KillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class KillService {
    private final KillRepository killRepository;

    @Autowired
    public KillService (KillRepository killRepository) {
        this.killRepository = killRepository;
    }


    public Collection<Kill> findAllKillsInGame(int id) {
        return killRepository.findAllKillsInGame(id);
    }

    public Kill findKillById(int id) {
        return killRepository.findById(id).orElseThrow(
                () -> new KillNotFoundException("Kill by id "+ id + " was not found"));
    }

}

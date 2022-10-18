package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.SquadCheckinNotFoundException;
import academy.noroff.hvz.models.SquadCheckin;
import academy.noroff.hvz.repositories.SquadCheckinRepository;
import org.springframework.stereotype.Service;

@Service
public class SquadCheckinService {
    private final SquadCheckinRepository squadCheckinRepository;

    public SquadCheckinService(SquadCheckinRepository squadCheckinRepository) {
        this.squadCheckinRepository = squadCheckinRepository;
    }

    public SquadCheckin findSquadCheckinById(int id) {
        return squadCheckinRepository.findById(id).orElseThrow(
                () -> new SquadCheckinNotFoundException("SquadCheckin by id "+ id + " was not found"));
    }
}

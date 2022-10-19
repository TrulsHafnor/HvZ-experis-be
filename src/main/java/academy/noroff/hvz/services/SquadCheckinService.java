package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.SquadCheckinNotFoundException;
import academy.noroff.hvz.models.Squad;
import academy.noroff.hvz.models.SquadCheckin;
import academy.noroff.hvz.models.SquadMember;
import academy.noroff.hvz.repositories.SquadCheckinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    public SquadCheckin createSquadCheckin (SquadCheckin squadCheckin) {
        Optional<SquadCheckin> squadCheckinToDelete = squadCheckinRepository.getSquadCheckinByGameIdAndPlayerId(squadCheckin.getGame().getId(),squadCheckin.getSquadMember().getId());
        if(squadCheckinToDelete.isPresent()) {
            deleteSquadCheckin(squadCheckinToDelete.get());
        }
        squadCheckin.setStartTime("Dummy value");
        return squadCheckinRepository.save(squadCheckin);
    }

    public void deleteSquadCheckin(SquadCheckin squadCheckin) {
        squadCheckinRepository.delete(squadCheckin);
    }
}

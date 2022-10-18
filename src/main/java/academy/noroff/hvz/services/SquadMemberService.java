package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.SquadMemberNotFoundException;
import academy.noroff.hvz.models.SquadMember;
import academy.noroff.hvz.repositories.SquadMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SquadMemberService {
    private final SquadMemberRepository squadMemberRepository;

    @Autowired
    public SquadMemberService(SquadMemberRepository squadMemberRepository) {
        this.squadMemberRepository = squadMemberRepository;
    }

    public SquadMember findSquadMemberById(int id) {
        return squadMemberRepository.findById(id).orElseThrow(
                () -> new SquadMemberNotFoundException("Squad member by id "+ id + " was not found"));
    }

    public SquadMember addSquadMember(SquadMember squadMember) {
        return squadMemberRepository.save(squadMember);
    }
/*
    public Optional<SquadMember> findSquadMemberInGame(int gameId, int playerID) {
        //needs to return empty Optional if it cant find game
        return squadMemberRepository.findSquadMemberInGame(gameId,playerID);
    }
  */
    public void deleteSquadMember(SquadMember squadMember) {
        // TODO: 10/18/2022 delete more? 
        squadMemberRepository.delete(squadMember);
    }
}

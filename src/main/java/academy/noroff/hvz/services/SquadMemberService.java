package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.SquadMemberNotFoundException;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.SquadMember;
import academy.noroff.hvz.repositories.SquadMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SquadMemberService {
    private final SquadMemberRepository squadMemberRepository;

    @Autowired
    public SquadMemberService(SquadMemberRepository squadMemberRepository) {
        this.squadMemberRepository = squadMemberRepository;
    }

    public SquadMember findSquadMemberByIds(int squadId, int playerId) {
        return squadMemberRepository.getSquadMemberInSquadWhitId(squadId, playerId).orElseThrow(
                () -> new SquadMemberNotFoundException("Squad member by id "+ playerId + " was not found in squad by id " + squadId));
    }

    public SquadMember findSquadMemberWhitPlayerId(int playerId) {
        return squadMemberRepository.getSquadMemberWhitPlayerID(playerId).orElseThrow(
                () -> new SquadMemberNotFoundException("Cant find squad member whit player id " + playerId));
    }

    public SquadMember findSquadMember(int id) {
        return squadMemberRepository.findById(id).orElseThrow(
                () -> new SquadMemberNotFoundException("Squad member by id "+ id + " was not found"));
    }

    public SquadMember addSquadMember(SquadMember squadMember) {
        return squadMemberRepository.save(squadMember);
    }

    public boolean checkIfPlayerIsInSquad(int playerID) {
        return squadMemberRepository.checkIfPlayerIsInSquad(playerID).isEmpty();
    }

    public void deleteSquadMember(SquadMember squadMember) {
        // TODO: 10/18/2022 delete more? 
        squadMemberRepository.delete(squadMember);
    }

    public Collection<SquadMember> getAllSquadMembers(int squadId) {
        return squadMemberRepository.getAllSquadMembersInSquad(squadId);
    }
}

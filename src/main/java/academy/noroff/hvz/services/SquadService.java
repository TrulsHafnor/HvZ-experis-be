package academy.noroff.hvz.services;

import academy.noroff.hvz.enums.GameState;
import academy.noroff.hvz.exeptions.CantJoinSquadException;
import academy.noroff.hvz.exeptions.CantWriteToGameException;
import academy.noroff.hvz.exeptions.SquadMemberNotFoundException;
import academy.noroff.hvz.exeptions.SquadNotFoundException;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.Squad;
import academy.noroff.hvz.models.SquadCheckin;
import academy.noroff.hvz.models.SquadMember;
import academy.noroff.hvz.models.dtos.JoinSquadDto;
import academy.noroff.hvz.repositories.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SquadService {
    private final SquadRepository squadRepository;
    private final PlayerService playerService;
    private final SquadMemberService squadMemberService;
    private final GameService gameService;
    private final SquadCheckinService squadCheckinService;

    @Autowired
    public SquadService(SquadRepository squadRepository, @Lazy PlayerService playerService,
                        SquadMemberService squadMemberService,@Lazy GameService gameService,
                        SquadCheckinService squadCheckinService) {
        this.squadRepository =squadRepository;
        this.playerService=playerService;
        this.squadMemberService=squadMemberService;
        this.gameService=gameService;
        this.squadCheckinService=squadCheckinService;
    }

    public Squad findSquadById (int id) {
        return squadRepository.findById(id).orElseThrow(
                () -> new SquadNotFoundException("Squad by id "+ id + " was not found"));
    }

    @Transactional
    public Squad createSquad (Squad squad) {
        //check for complete game
        checkForCompleteGame(squad.getGame().getId());
        //Check if player is in squad
        checkIfAllreadyInSquad(squad.getPlayer().getId());
        SquadMember squadMember = new SquadMember("Leader",squad,squad.getPlayer());
        squadMemberService.addSquadMember(squadMember);
        return squadRepository.save(squad);
    }

    @Transactional
    public void deleteSquad(int gameId, int squadID) {
        checkForCompleteGame(gameId);
        //check if gameId and squadId is valid
        squadMemberService.deleteAllSquadMembersInSquad(findSquadInGame(gameId,squadID).getId());
        squadRepository.delete(findSquadInGame(gameId,squadID));
    }

    public Squad updateSquad(Squad squad) {
        checkForCompleteGame(squad.getGame().getId());
        return squadRepository.save(squad);
    }

    public void updateSquadBeforeDeletingLeader(Squad squad) {
        List<SquadMember> squadMembers = (List<SquadMember>) getAllPlayersInSquad(squad.getGame().getId(), squad.getId());
            squad.setPlayer(squadMembers.get(0).getMember());
            updateSquad(squad);
    }

    @Transactional
    public void leaveSquad(int gameId, int playerID) {
        //does player exist in that game?
        Player player = playerService.findPlayerInGame(gameId,playerID);
        //check game status
        checkForCompleteGame(gameId);

        Squad squad = squadRepository.findSquadWhitPlayerIdAndGameId(gameId,playerID).orElseThrow(
                () -> new SquadNotFoundException("Squad not found whit player id " + playerID + " and game id " + gameId));

        int squadId = squad.getId();

        SquadMember squadMember = squadMemberService.findSquadMemberByIds(squadId, playerID);
        //first delete squad checkin
        squadMemberService.deleteSquadMember(squadMember);
        if (findSquadById(squadId).getMembers().isEmpty()) {
            deleteSquad(squad.getGame().getId(),squadId);
        }
        else if(squad.getPlayer().getId() == player.getId()) {
            updateSquadBeforeDeletingLeader(squad);
        }
    }

    public Squad findSquadInGame(int gameId, int squadId) {
        return squadRepository.findSquadInGame(gameId,squadId).orElseThrow(
                () -> new SquadNotFoundException("Squad by id "+ squadId + " was not found in game by id " + gameId));
    }

    public Collection<Squad> findAllSquadsInGame(int gameId) {
        return squadRepository.findAllSquadsInGame(gameId);
    }

    public SquadMember joinSquad(int gameId, int squadId, JoinSquadDto joinSquadDto) {
        //Checks if u can write to game
        checkForCompleteGame(gameId);
        Player player = playerService.findPlayerInGame(gameId,joinSquadDto.getPlayerId());
        Squad squad = findSquadInGame(gameId,squadId);
        //throws exeption if not valid squad
        checkForValidSquad(squad, player);
        //Check if player is in squad
        checkIfAllreadyInSquad(player.getId());
        SquadMember squadMember = new SquadMember("Member", squad, player);
        return squadMemberService.addSquadMember(squadMember);
    }

    private void checkForCompleteGame(int gameId) {
        if(gameService.findGameById(gameId).getGameState() == GameState.COMPLETE ) {
            throw new CantWriteToGameException("Cant write to game that is complete");
        }
    }

    private void checkForValidSquad(Squad squad, Player player) {
        if (squad.isHuman() != player.isHuman()) {
            throw new CantJoinSquadException("Wrong faction");
        }
    }

    private void checkIfAllreadyInSquad(int playerID) {
        if(!squadMemberService.checkIfPlayerIsInSquad(playerID))
            throw new CantJoinSquadException("You are all ready in a squad");
    }

    public SquadCheckin creatSquadCheckin(SquadCheckin squadCheckin) {
        checkForCompleteGame(squadCheckin.getGame().getId());
        return squadCheckinService.createSquadCheckin(squadCheckin);
    }

    public Collection<SquadCheckin> findAllSquadsCheckinsInSquadByGameId(int gameId, int squadId) {
        return squadCheckinService.getAllSquadCheckinsWhitSquadAndPlayerId(gameId, squadId);
    }

    public Collection<SquadMember> getAllPlayersInSquad(int gameId, int squadId) {
        //this will throw exeption if squad is not in game
        findSquadInGame(gameId, squadId);
        return squadMemberService.getAllSquadMembers(squadId);
    }
}

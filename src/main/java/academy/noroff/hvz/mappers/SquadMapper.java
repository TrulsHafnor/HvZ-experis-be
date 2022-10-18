package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.*;
import academy.noroff.hvz.models.dtos.CreateSquadDto;
import academy.noroff.hvz.models.dtos.SquadDto;
import academy.noroff.hvz.models.dtos.UpdateSquadDto;
import academy.noroff.hvz.services.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class SquadMapper {
    @Autowired
    protected SquadService squadService;
    @Autowired
    protected GameService gameService;
    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected ChatService chatService;
    @Autowired
    protected SquadMemberService squadMemberService;

    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "player", source = "player.id")
    //@Mapping(target = "chat", source = "chat", qualifiedByName = "chatToIDs")
    @Mapping(target = "members", source = "members", qualifiedByName = "membersToIDs")
    public abstract SquadDto squadToSquadDto(Squad squad);

    public abstract Collection<SquadDto> squadToSquadDto(Collection<Squad> squads);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerToPlayerIds")
    //@Mapping(target = "chat", ignore = true)
    @Mapping(target = "members", ignore = true)
    public abstract Squad squadDtoToSquad(SquadDto squadDto);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerToPlayerIds")
    @Mapping(target = "members", ignore = true)
    public abstract Squad updateSquadDtoToSquadDto(UpdateSquadDto updateSquadDto);

    public Squad createSquadDtoToSquadDto(CreateSquadDto createSquadDto) {
        Squad squad = new Squad();
        squad.setName(createSquadDto.getName());
        squad.setGame(gameService.findGameById(createSquadDto.getGame()));
        squad.setPlayer(playerService.findPlayerInGame(createSquadDto.getGame(), createSquadDto.getPlayer()));
        squad.setHuman(playerService.findPlayerById(squad.getPlayer().getId()).isHuman());
        return squad;
    }

    @Named("gameToGameIds")
    Game mapIdToGame(Integer id) {
        return gameService.findGameById(id);
    }

    @Named("playerToPlayerIds")
    Player mapIdToPlayer(Integer id) {
        return playerService.findPlayerById(id);
    }

    @Named("chatIdsToChat")
    Set<Chat> mapIdsToChat(Set<Integer> id) {
        return id.stream()
                .map( i -> chatService.findChatById(i))
                .collect(Collectors.toSet());
    }

    @Named("chatToIDs")
    Set<Integer> chatToIDs(Set<Chat> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Chat::getId).collect(Collectors.toSet());
    }

    @Named("membersIdsToMembers")
    Set<SquadMember> mapIdsToMembers(Set<Integer> id) {
        return id.stream()
                .map( i -> squadMemberService.findSquadMemberById(i))
                .collect(Collectors.toSet());
    }

    @Named("membersToIDs")
    Set<Integer> membersToIDs(Set<SquadMember> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(SquadMember::getId).collect(Collectors.toSet());
    }


}

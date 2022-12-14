package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.*;
import academy.noroff.hvz.models.dtos.CreateGameDto;
import academy.noroff.hvz.models.dtos.GameDto;
import academy.noroff.hvz.models.dtos.UpdateGameDto;
import academy.noroff.hvz.services.ChatService;
import academy.noroff.hvz.services.KillService;
import academy.noroff.hvz.services.MissionService;
import academy.noroff.hvz.services.PlayerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class GameMapper {
    // ONE TO MANY
    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected MissionService missionService;
    @Autowired
    protected KillService killService;
    @Autowired
    protected ChatService chatService;

    @Mapping(target = "players", source = "players", qualifiedByName = "playersToIDs")
    /*@Mapping(target = "missions", source = "missions", qualifiedByName = "missionsToIDs")
    @Mapping(target = "kills", source = "kills", qualifiedByName = "killsToIDs")
    @Mapping(target = "chats", source = "chats", qualifiedByName = "chatsToIDs")*/
    public abstract GameDto gameToGameDto(Game game);

    public abstract Collection<GameDto> gameToGameDto(Collection<Game> games);

    @Mapping(target = "players", ignore = true)
    /*@Mapping(target = "missions", ignore = true)
    @Mapping(target = "kills", ignore = true)
    @Mapping(target = "chats", ignore = true)*/
    public abstract Game gameDtoToGame(GameDto dto);

    @Named("playersToIDs")
    Set<Integer> playersToIDs(Set<Player> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Player::getId).collect(Collectors.toSet());
    }

    @Named("playerIdsToPlayers")
    Set<Player> mapIdsToPlayers(Set<Integer> id) {
        return id.stream()
                .map( i -> playerService.findPlayerById(i))
                .collect(Collectors.toSet());
    }

    @Named("missionIdsToMissions")
    Set<Mission> mapIdsToMissions(Set<Integer> id) {
        return id.stream()
                .map( i -> missionService.findMissionById(i))
                .collect(Collectors.toSet());
    }

    @Named("missionsToIDs")
    Set<Integer> missionsToIDs(Set<Mission> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Mission::getId).collect(Collectors.toSet());
    }

    @Named("killIdsToKills")
    Set<Kill> mapIdsToKills(Set<Integer> id) {
        return id.stream()
                .map( i -> killService.findKillById(i))
                .collect(Collectors.toSet());
    }

    @Named("killsToIDs")
    Set<Integer> killsToIDs(Set<Kill> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Kill::getId).collect(Collectors.toSet());
    }

    @Named("chatIdsToChats")
    Set<Chat> mapIdsToChats(Set<Integer> id) {
        return id.stream()
                .map( i -> chatService.findChatById(i))
                .collect(Collectors.toSet());
    }

    @Named("chatsToIDs")
    Set<Integer> chatsToIDs(Set<Chat> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Chat::getId).collect(Collectors.toSet());
    }

    public abstract Game createGameDtoTpGame(CreateGameDto createGameDto);

    /*
           return new GameDto(createGameDto.getGameTitle(),
                createGameDto.getGameDescription(),
                createGameDto.getNw_lat(),
                createGameDto.getNw_lng(),
                createGameDto.getSe_lat(),
                createGameDto.getSe_lng());
     */

    public abstract Game updateGameDtoToGameDto(UpdateGameDto updateGameDto);
}

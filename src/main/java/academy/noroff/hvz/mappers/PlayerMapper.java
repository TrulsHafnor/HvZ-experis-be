package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.PlayerDto;
import academy.noroff.hvz.models.dtos.NoPatientZeroPlayerDto;
import academy.noroff.hvz.services.ChatService;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.KillService;
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
public abstract class PlayerMapper {
    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected GameService gameService;
    @Autowired
    protected KillService killService;
    @Autowired
    protected ChatService chatService;

    @Mapping(target = "kills", source = "kills", qualifiedByName = "killsToIDs")
    @Mapping(target = "death", source = "death.id")
    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "messages", source = "messages", qualifiedByName = "messagesToIDs")
    public abstract PlayerDto playerToPlayerDto(Player player);

    public abstract Collection<PlayerDto> playersToPlayerDto(Collection<Player> player);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "kills", ignore = true)
    @Mapping(target = "death", ignore = true)
    @Mapping(target = "messages", ignore = true)
    public abstract Player playerDtoToPlayer(PlayerDto player);

    @Named("gameToGameIds")
    Game mapIdToGame(Integer id) {
        return gameService.findGameById(id);
    }

   @Named("deathToDeathIds")
    Player mapToDeathIds(Integer id) {
        return playerService.findPlayerById(id);
    }
    @Named("killsToIDs")
    Set<Integer> mapKillsToIds(Set<Kill> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Kill::getId).collect(Collectors.toSet());
    }

    @Named("messageIdsToMessages")
    Set<Chat> mapIdsToChats(Set<Integer> id) {
        return id.stream()
                .map( i -> chatService.findChatById(i))
                .collect(Collectors.toSet());
    }

    @Named("messagesToIDs")
    Set<Integer> chatsToIDs(Set<Chat> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Chat::getId).collect(Collectors.toSet());
    }

    @Mapping(target = "kills", source = "kills", qualifiedByName = "killsToIDs")
    @Mapping(target = "death", source = "death.id")
    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "messages", source = "messages", qualifiedByName = "messagesToIDs")
    public abstract NoPatientZeroPlayerDto playerToNoPatientZeroPlayerDto(Player player);

    public abstract Collection<NoPatientZeroPlayerDto> playersToNoPatientZeroPlayerDto(Collection<Player> player);
}

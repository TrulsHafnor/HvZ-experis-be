package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.GameDto;
import academy.noroff.hvz.models.dtos.PlayerDto;
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
    // MANY TO ONE
    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected GameService gameService;
    @Autowired
    protected KillService killService;

    @Mapping(target = "kills", source = "kills", qualifiedByName = "killsToIDs")
    @Mapping(target = "death", source = "death", qualifiedByName = "deathToDeathIds")
    @Mapping(target = "game", source = "game.id")
    public abstract PlayerDto playerToPlayerDto(Player player);

    public abstract Collection<PlayerDto> playerToPlayerDto(Collection<Player> player);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "kills", ignore = true)
    @Mapping(target = "death", source = "death", qualifiedByName = "deathToDeathIds")
    public abstract Player playerDtoToPlayer(PlayerDto player);

    @Named("gameToGameIds")
    Game mapIdToGame(Integer id) {
        return gameService.findGameById(id);
    }

    @Named("deathToDeathIds")
    Kill mapIdToDeath(Integer id) {return killService.findKillById(id);}

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

}

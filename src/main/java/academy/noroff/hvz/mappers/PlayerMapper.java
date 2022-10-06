package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.PlayerDto;
import academy.noroff.hvz.services.GameService;
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
    protected GameService gameService;


    public abstract Collection<PlayerDto> playersToPlayerDtos(Collection<Player> player);


    @Mapping(target = "game", source = "game", qualifiedByName = "gameToIds")
    public abstract PlayerDto playerToPlayerDto(Player player);


    @Mapping(target = "game", source = "game", qualifiedByName = "gameIdsToGames")
    public abstract Player playerDtoToPlayer(PlayerDto dto);


    @Named("gameToIds")
    Set<Integer> map(Set<Game> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Game::getId).collect(Collectors.toSet());
    }


    @Named("gameIdsToGames")
    Set<Game> mapIdsToGames(Set<Integer> id) {
        return id.stream()
                .map( i -> gameService.findGameById(i))
                .collect(Collectors.toSet());
    }



}

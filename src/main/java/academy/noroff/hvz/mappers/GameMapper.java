package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.GameDto;
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
public abstract class GameMapper {

    @Autowired
    protected GameService gameService;
    @Autowired
    protected PlayerService playerService;


    @Mapping(target = "players", source = "players.id")
    public abstract GameDto gameToGameDto(Game game);


    public abstract Collection<GameDto> gameToGameDto(Collection<Game> game);


    @Mapping(target = "players", source = "players", qualifiedByName = "playersToIds")
    public abstract Game gameDtoToGame(GameDto dto);

    @Named("playersToIds")
    Player mapIdToPlayer(int id) {
        return playerService.findPlayerById(id);
    }


}

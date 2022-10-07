package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.GameDto;
import academy.noroff.hvz.services.PlayerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class GameMapper {
    // ONE TO MANY
    @Autowired
    protected PlayerService playerService;


    // TODO: 10/6/2022 Maybe have something like this
    // public abstract Collection<updateGameDto> gameToUpdateGameDto(Collection<Game> games);

    @Mapping(target = "players", source = "players", qualifiedByName = "playersToIDs")
    public abstract GameDto gameToGameDto(Game game);

    public abstract Collection<GameDto> gameToGameDto(Collection<Game> games);

    @Mapping(target = "players", ignore = true)
    public abstract Game gameDtoToGame(GameDto dto);

    // TODO: 10/6/2022 Maybe add something like this
    //public abstract Game gameUpdateDtoToGame(GameUpdateDto dto);

    @Named("playersToIDs")
    List<Integer> playersToIDs(Set<Player> source) {
        if(source == null)
            return null;
        return source.stream()
                .map(Player::getId).collect(Collectors.toList());
    }

    @Named("playerIdsToPlayers")
    Set<Player> mapIdsToMovies(Set<Integer> id) {
        return id.stream()
                .map( i -> playerService.findPlayerById(i))
                .collect(Collectors.toSet());
    }

}

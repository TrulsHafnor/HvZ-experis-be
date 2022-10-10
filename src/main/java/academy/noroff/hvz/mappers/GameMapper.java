package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.GameDto;
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

    @Mapping(target = "players", source = "players", qualifiedByName = "playersToIDs")
    @Mapping(target = "missions", source = "missions", qualifiedByName = "missionsToIDs")
    public abstract GameDto gameToGameDto(Game game);

    public abstract Collection<GameDto> gameToGameDto(Collection<Game> games);

    @Mapping(target = "players", ignore = true)
    @Mapping(target = "missions", ignore = true)
    public abstract Game gameDtoToGame(GameDto dto);

    @Named("playersToIDs")
    Set<Integer> playersToIDs(Set<Player> source) {
        if(source == null)
            return Collections.emptySet();
        return source.stream()
                .map(Player::getId).collect(Collectors.toSet());
    }

    @Named("playerIdsToPlayers")
    Set<Player> mapIdsToMovies(Set<Integer> id) {
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

}

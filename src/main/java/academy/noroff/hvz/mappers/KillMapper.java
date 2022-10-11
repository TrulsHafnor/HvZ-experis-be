package academy.noroff.hvz.mappers;


import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.KillDto;
import academy.noroff.hvz.models.dtos.PlayerDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.KillService;
import academy.noroff.hvz.services.PlayerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;


@Mapper(componentModel = "spring")
public abstract class KillMapper {
    @Autowired
    protected KillService killService;
    @Autowired
    protected GameService gameService;
    @Autowired
    protected PlayerService playerService;

    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "playerKiller", source = "playerKiller.id")
    @Mapping(target = "playerDeath", source = "playerDeath.id")
    public abstract KillDto killToKillDto(Kill kill);

    public abstract Collection<KillDto> killToKillDto(Collection<Kill> kill);

    @Mapping(target = "game", source = "game", qualifiedByName = "killToKillIds")
    @Mapping(target = "playerKiller", source = "playerKiller", qualifiedByName = "playerKillerToPlayerKillerIds")
    @Mapping(target = "playerDeath", source = "playerDeath", qualifiedByName = "deathToDeathIds")
    public abstract Kill killDtoToKill(KillDto kill);

    @Named("killToKillIds")
    Game mapIdToGame(Integer id) {
        return gameService.findGameById(id);
    }

    @Named("playerKillerToPlayerKillerIds")
    Player mapIdToPlayer(Integer id) {
        return playerService.findPlayerById(id);
    }

    @Named("deathToDeathIds")
    Player deathToDeathIds(Integer id) {
        return playerService.findPlayerById(id);
    }
}

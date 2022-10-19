package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.SquadCheckin;
import academy.noroff.hvz.models.SquadMember;
import academy.noroff.hvz.models.dtos.CreateCheckinDto;
import academy.noroff.hvz.models.dtos.SquadCheckinDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.SquadMemberService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class SquadCheckinMapper {
    @Autowired
    protected SquadMemberService squadMemberService;
    @Autowired
    protected GameService gameService;

    @Mapping(target = "squadMember", source = "squadMember.id")
    @Mapping(target = "game", source = "game.id")
    public abstract SquadCheckinDto squadCheckinToSquadCheckinDto(SquadCheckin squadCheckin);

    public abstract Collection<SquadCheckinDto> squadCheckinToSquadCheckinDto(Collection<SquadCheckin> squadCheckins);

    @Mapping(target = "squadMember", source = "squadMember", qualifiedByName = "squadMemberToSquadMemberIds")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    public abstract SquadCheckin squadCheckinDtoToSquadCheckin(SquadCheckinDto squadCheckinDto);

    @Mapping(target = "squadMember", source = "squadMember", qualifiedByName = "squadMemberToSquadMemberIds")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    public abstract SquadCheckin createCheckinDtoToSquadCheckin(CreateCheckinDto createCheckinDto);



    @Named("squadMemberToSquadMemberIds")
    SquadMember mapIdToSquadMember(Integer id){return squadMemberService.findSquadMember(id);}

    @Named("gameToGameIds")
    Game mapIdToGame(Integer id) {
        return gameService.findGameById(id);
    }
}

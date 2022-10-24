package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Squad;
import academy.noroff.hvz.models.SquadMember;
import academy.noroff.hvz.models.dtos.CreateSquadDto;
import academy.noroff.hvz.models.dtos.SquadDto;
import academy.noroff.hvz.models.dtos.SquadMemberDto;
import academy.noroff.hvz.models.dtos.SquadMemberLessDetailsDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.PlayerService;
import academy.noroff.hvz.services.SquadMemberService;
import academy.noroff.hvz.services.SquadService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class SquadMemberMapper {
    @Autowired
    protected SquadMemberService squadMemberService;
    @Autowired
    protected SquadService squadService;
    @Autowired
    protected PlayerService playerService;

    @Mapping(target = "squad", source = "squad.id")
    @Mapping(target = "squadCheckin", source = "squadCheckin.id")
    public abstract SquadMemberDto squadToSquadDto(SquadMember squadMember);

    //public abstract Collection<SquadMemberDto> squadMemberToSquadMemberDto(Collection<SquadMember> squadMembers);

    @Mapping(target = "squad", source = "squad", qualifiedByName = "squadToSquadIds")
    @Mapping(target = "squadCheckin", ignore = true)
    public abstract SquadMember squadMemberDtoToSquadMember(SquadMemberDto squadMemberDto);

    @Mapping(target = "member", source = "member.id")
    public abstract SquadMemberLessDetailsDto squadToSquadMemberLessDetailsDto(SquadMember squadMember);

    public abstract Collection<SquadMemberLessDetailsDto> squadMemberToSquadMemberLessDetailsDtos(Collection<SquadMember> squadMembers);

    @Named("squadToSquadIds")
    Squad mapIdToSquad(Integer id) {
        return squadService.findSquadById(id);
    }

}

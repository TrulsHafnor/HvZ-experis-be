package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.GameDto;
import academy.noroff.hvz.models.dtos.MissionDto;
import academy.noroff.hvz.services.GameService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MissionMapper {
    // Many to one

    @Autowired
    protected GameService gameService;

    // TODO: 10/7/2022 Add these
        // missionToMissionDto
        // missionDtoToMission

    @Mapping(target = "game", source = "game.id")
    public abstract MissionDto missionToMissionDto(Mission mission);

    public abstract Collection<MissionDto> missionToMissionDto(Collection<Mission> missions);

    @Mapping(target = "game", ignore = true)
    public abstract Mission missionDtoToMission(MissionDto dto);


}

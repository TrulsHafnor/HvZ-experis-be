package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.dtos.MissionDto;
import academy.noroff.hvz.services.GameService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class MissionMapper {
    // Many to one

    @Autowired
    protected GameService gameService;

    @Mapping(target = "game", source = "game.id")
    public abstract MissionDto missionToMissionDto(Mission mission);

    public abstract Collection<MissionDto> missionToMissionDto(Collection<Mission> missions);

    @Mapping(target = "game", ignore = true)
    public abstract Mission missionDtoToMission(MissionDto dto);

}

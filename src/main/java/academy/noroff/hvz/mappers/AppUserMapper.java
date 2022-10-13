package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.AppUser;
import academy.noroff.hvz.models.dtos.AppUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AppUserMapper {

    @Mapping(target = "player", source = "player.id")
    public abstract AppUserDto AppUserToAppUserDto(AppUser appUser);
}

package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.dtos.ChatDto;
import academy.noroff.hvz.services.GameService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ChatMapper {
    @Autowired
    protected GameService gameService;

    @Mapping(target = "game", source = "game.id")
    public abstract ChatDto chatToChatDto(Chat chat);

    public abstract Collection<ChatDto> chatToChatDto(Collection<Chat> chat);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    public abstract Chat chatDtoToChat(ChatDto chatDto);

    @Named("gameToGameIds")
    Game mapIdToGame(Integer id) {
        return gameService.findGameById(id);
    }
}

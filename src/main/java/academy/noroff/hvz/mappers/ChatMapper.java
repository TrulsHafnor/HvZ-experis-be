package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.ChatDto;
import academy.noroff.hvz.models.dtos.PostChatDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.PlayerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ChatMapper {
    @Autowired
    protected GameService gameService;
    @Autowired
    protected PlayerService playerService;

    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "player", source = "player.id")
    public abstract ChatDto chatToChatDto(Chat chat);

    public abstract Collection<ChatDto> chatToChatDto(Collection<Chat> chat);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerToPlayerIds")
    public abstract Chat chatDtoToChat(ChatDto chatDto);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerToPlayerIds")
    public abstract Chat postChatDtoToChat(PostChatDto postChatDto);

    @Named("gameToGameIds")
    Game mapIdToGame(Integer id) {
        System.out.println("DETTE ER GAME ID I MAPPER" + id);
        return gameService.findGameById(id);
    }
    @Named("playerToPlayerIds")
    Player mapIdToPlayer(Integer id) {
        return playerService.findPlayerById(id);
    }
}

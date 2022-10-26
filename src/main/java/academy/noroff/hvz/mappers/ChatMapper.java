package academy.noroff.hvz.mappers;

import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.Squad;
import academy.noroff.hvz.models.dtos.ChatDto;
import academy.noroff.hvz.models.dtos.PostChatDto;
import academy.noroff.hvz.models.dtos.PostSquadChatDto;
import academy.noroff.hvz.models.dtos.SquadChatDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.PlayerService;
import academy.noroff.hvz.services.SquadService;
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
    @Autowired
    protected SquadService squadService;

    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "player", source = "player.id")
    //@Mapping(target = "squad", source= "squad.id")
    public abstract ChatDto chatToChatDto(Chat chat);

    public abstract Collection<ChatDto> chatToChatDto(Collection<Chat> chat);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerToPlayerIds")
    //@Mapping(target = "squad", source= "squad", qualifiedByName = "squadToSquadIds")
    public abstract Chat chatDtoToChat(ChatDto chatDto);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerToPlayerIds")
    //@Mapping(target = "squad", source= "squad", qualifiedByName = "squadToSquadIds")
    public abstract Chat postChatDtoToChat(PostChatDto postChatDto);

    @Mapping(target = "game", source = "game", qualifiedByName = "gameToGameIds")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerToPlayerIds")
    @Mapping(target = "squad", source= "squad", qualifiedByName = "squadToSquadIds")
    public abstract Chat postSquadChatDtoToChat(PostSquadChatDto postSquadChatDto);

    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "player", source = "player.id")
    @Mapping(target = "squad", source= "squad.id")
    public abstract PostSquadChatDto chatToPostSquadChatDto(Chat chat);

    public abstract Collection<PostSquadChatDto> chatsToPostSquadChatDtos(Collection<Chat> chat);

    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "player", source = "player.id")
    @Mapping(target = "squad", source= "squad.id")
    public abstract SquadChatDto chatToSquadChatDto(Chat chat);

    public abstract Collection<SquadChatDto> chatsToSquadChatDtos(Collection<Chat> chat);

    @Named("gameToGameIds")
    Game mapIdToGame(Integer id) {
        return gameService.findGameById(id);
    }
    @Named("playerToPlayerIds")
    Player mapIdToPlayer(Integer id) {
        return playerService.findPlayerById(id);
    }

    @Named("squadToSquadIds")
    Squad mapIdToSquad(Integer id) {return squadService.findSquadById(id); }
}

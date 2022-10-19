package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.GameNotFoundException;
import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final PlayerService playerService;

    @Autowired
    public ChatService(ChatRepository chatRepository, PlayerService playerService) {
        this.chatRepository = chatRepository;
        this.playerService = playerService;
    }

    public Chat addChat(Chat chat, int playerId) {
        return chatRepository.save(setValuesOnChat(chat, playerId));
    }

    private Chat setValuesOnChat(Chat chat, int playerId) {
        Player tempPlayer = playerService.findPlayerById(playerId);
        if (tempPlayer.isHuman()) {
            chat.setHuman(true);
        } else {
            chat.setHuman(false);
        }
        return chat;
    }

    public Chat findChatById (int id) {
        return chatRepository.findById(id).orElseThrow(
                () -> new GameNotFoundException("Chat whit id "+ id + " was not found"));
    }

    public Collection<Chat> findAllChatsForPlayer(int gameId, int playerId) {
        boolean cantSeeOtherFaction = playerService.findPlayerById(playerId).isHuman();
        return chatRepository.findChatInGameForPlayer(gameId,cantSeeOtherFaction);
    }

    public List<Chat> findAllChats() {
        return chatRepository.findAll();
    }
}

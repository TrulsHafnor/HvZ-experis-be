package academy.noroff.hvz.controllers;

import academy.noroff.hvz.enums.Status;
import academy.noroff.hvz.mappers.ChatMapper;
import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Message;
import academy.noroff.hvz.models.dtos.ChatDto;
import academy.noroff.hvz.models.dtos.PostChatDto;
import academy.noroff.hvz.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import static java.lang.String.format;

@Controller
@CrossOrigin(origins = {
        "https://hvz-fe-noroff.herokuapp.com/",
        "http://localhost:3000",
})
public class ChatController {

    private final ChatService chatService;
    private final ChatMapper chatMapper;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public ChatController(ChatService chatService, ChatMapper chatMapper) {
        this.chatService = chatService;
        this.chatMapper = chatMapper;
    }

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        //simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }
    
    @MessageMapping("/chat/{gameId}/sendMessage")
    public void sendMessage(@DestinationVariable String gameId, @Payload PostChatDto postChatDto) {
        Chat chat = chatMapper.postChatDtoToChat(postChatDto);
        //System.out.println(chat.getGame().getId() + "DETTE ER GAME!!!");
        //System.out.println(chatDto);
        chatService.addChat(chat, chat.getPlayer().getId());
        ChatDto chatDto = chatMapper.chatToChatDto(chat);
        messagingTemplate.convertAndSend(format("/chatroom/%s", gameId), chatDto);
    }

    @MessageMapping("/chat/{gameId}/human/sendMessage")
    public void sendMessageHuman(@DestinationVariable String gameId, @Payload PostChatDto postChatDto) {
        Chat chat = chatMapper.postChatDtoToChat(postChatDto);
        chatService.addChat(chat, chat.getPlayer().getId());
        ChatDto chatDto = chatMapper.chatToChatDto(chat);
        messagingTemplate.convertAndSend(format("/chatroom/%s/human", gameId), chatDto);
    }

    @MessageMapping("/chat/{gameId}/zombie/sendMessage")
    public void sendMessageZombie(@DestinationVariable String gameId, @Payload PostChatDto postChatDto) {
        Chat chat = chatMapper.postChatDtoToChat(postChatDto);
        chatService.addChat(chat, chat.getPlayer().getId());
        ChatDto chatDto = chatMapper.chatToChatDto(chat);
        messagingTemplate.convertAndSend(format("/chatroom/%s/zombie", gameId), chatDto);
    }

    @MessageMapping("/chat/{gameId}/{squadId}/sendMessage")
    public void sendMessageSquad(@DestinationVariable String gameId, @DestinationVariable String squadId,
                                 @Payload PostChatDto postChatDto) {
        Chat chat = chatMapper.postChatDtoToChat(postChatDto);
        chatService.addChat(chat, chat.getPlayer().getId());
        ChatDto chatDto = chatMapper.chatToChatDto(chat);
        messagingTemplate.convertAndSend(format("/chatroom/%s/%s", gameId, squadId), chatDto);
    }

    @MessageMapping("/chat/{gameId}/addUser")
    public void addUser(@DestinationVariable String gameId, @Payload Message chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("game_id", gameId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setStatus(Status.LEAVE);
            leaveMessage.setSenderName(chatMessage.getSenderName());
            messagingTemplate.convertAndSend(format("/chatroom/%s", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSenderName());
        messagingTemplate.convertAndSend(format("/chatroom/%s", gameId), chatMessage);
    }

    @MessageMapping("/chat/{gameId}/human/addUser")
    public void addUserHuman(@DestinationVariable String gameId, @Payload Message chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("game_id", gameId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setStatus(Status.LEAVE);
            leaveMessage.setSenderName(chatMessage.getSenderName());
            messagingTemplate.convertAndSend(format("/chatroom/%s/human", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSenderName());
        messagingTemplate.convertAndSend(format("/chatroom/%s/human", gameId), chatMessage);
    }

    @MessageMapping("/chat/{gameId}/zombie/addUser")
    public void addUserZombie(@DestinationVariable String gameId, @Payload Message chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("game_id", gameId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setStatus(Status.LEAVE);
            leaveMessage.setSenderName(chatMessage.getSenderName());
            messagingTemplate.convertAndSend(format("/chatroom/%s/zombie", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSenderName());
        messagingTemplate.convertAndSend(format("/chatroom/%s/zombie", gameId), chatMessage);
    }

    @MessageMapping("/chat/{gameId}/{squadId}/addUser")
    public void addUserSquad(@DestinationVariable String gameId, @DestinationVariable String squadId,
                             @Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("game_id", gameId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setStatus(Status.LEAVE);
            leaveMessage.setSenderName(chatMessage.getSenderName());
            messagingTemplate.convertAndSend(format("/chatroom/%s/%s", currentRoomId, squadId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSenderName());
        messagingTemplate.convertAndSend(format("/chatroom/%s/%s", gameId, squadId), chatMessage);
    }
}

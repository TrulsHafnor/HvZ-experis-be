package academy.noroff.hvz.controllers;

import academy.noroff.hvz.enums.Status;
import academy.noroff.hvz.mappers.ChatMapper;
import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Message;
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

    @MessageMapping("/chat/{roomId}/sendMessage/te")
    public void sendMessage(@DestinationVariable String roomId, @Payload Message chatMessage) {
        //chatService.addChat()
        System.out.println(chatMessage);
        messagingTemplate.convertAndSend(format("/chatroom/%s", roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessageDto(@DestinationVariable String roomId, @Payload PostChatDto chatDto) {
        Chat chat = chatMapper.postChatDtoToChat(chatDto);
        System.out.println(chat.getGame().getId() + "DETTE ER GAME!!!");
        System.out.println(chatDto);
        chatService.addChat(chat, chat.getPlayer().getId());
        messagingTemplate.convertAndSend(format("/chatroom/%s", roomId), chatDto);
    }

    @MessageMapping("/chat/{roomId}/human/sendMessage")
    public void sendMessageHuman(@DestinationVariable String roomId, @Payload Message chatMessage) {
        messagingTemplate.convertAndSend(format("/chatroom/%s/human", roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/zombie/sendMessage")
    public void sendMessageZombie(@DestinationVariable String roomId, @Payload Message chatMessage) {
        messagingTemplate.convertAndSend(format("/chatroom/%s/zombie", roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/{squadId}/sendMessage")
    public void sendMessageSquad(@DestinationVariable String roomId, @DestinationVariable String squadId,
                                 @Payload Message chatMessage) {
        messagingTemplate.convertAndSend(format("/chatroom/%s/%s", roomId, squadId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable String roomId, @Payload Message chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setStatus(Status.LEAVE);
            leaveMessage.setSenderName(chatMessage.getSenderName());
            messagingTemplate.convertAndSend(format("/chatroom/%s", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSenderName());
        messagingTemplate.convertAndSend(format("/chatroom/%s", roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/human/addUser")
    public void addUserHuman(@DestinationVariable String roomId, @Payload Message chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setStatus(Status.LEAVE);
            leaveMessage.setSenderName(chatMessage.getSenderName());
            messagingTemplate.convertAndSend(format("/chatroom/%s/human", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSenderName());
        messagingTemplate.convertAndSend(format("/chatroom/%s/human", roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/zombie/addUser")
    public void addUserZombie(@DestinationVariable String roomId, @Payload Message chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setStatus(Status.LEAVE);
            leaveMessage.setSenderName(chatMessage.getSenderName());
            messagingTemplate.convertAndSend(format("/chatroom/%s/zombie", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSenderName());
        messagingTemplate.convertAndSend(format("/chatroom/%s/zombie", roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/{squadId}/addUser")
    public void addUserSquad(@DestinationVariable String roomId, @DestinationVariable String squadId,
                             @Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setStatus(Status.LEAVE);
            leaveMessage.setSenderName(chatMessage.getSenderName());
            messagingTemplate.convertAndSend(format("/chatroom/%s/%s", currentRoomId, squadId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSenderName());
        messagingTemplate.convertAndSend(format("/chatroom/%s/%s", roomId, squadId), chatMessage);
    }
}

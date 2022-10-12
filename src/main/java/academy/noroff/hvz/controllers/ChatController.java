package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.ChatMapper;
import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.dtos.ChatDto;
import academy.noroff.hvz.models.dtos.GameDto;
import academy.noroff.hvz.services.ChatService;
import academy.noroff.hvz.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@Controller
@RequestMapping("/game")
@CrossOrigin(origins = {
        "https://hvz-fe-noroff.herokuapp.com/",
        "http://localhost:3000"}
        )
public class ChatController {

    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final GameService gameService;
    @Autowired
    public  ChatController(ChatService chatService, ChatMapper chatMapper, GameService gameService) {
        this.chatService=chatService;
        this.chatMapper=chatMapper;
        this.gameService=gameService;
    }


    @Operation(summary = "Post new chat message")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Chat message successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
    })
    @PostMapping("/{game_id}/chat/{player_id}")
    public ResponseEntity addGame (@RequestBody ChatDto chatDto, @PathVariable("game_id") int game_id, @PathVariable("player_id") int player_id) {
        if (chatDto.getGame() != gameService.findGameById(chatDto.getGame()).getId()) {
            // && chatDto.getGame() !=game_id
            return ResponseEntity.badRequest().build();
        }
        Chat chat = chatMapper.chatDtoToChat(chatDto);
        chatService.addChat(chat,player_id);
        return new ResponseEntity(chat, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Chats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Chat.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Cant find chat",
                    content = @Content)
    })
    @GetMapping("/getAllChats")
    public ResponseEntity getAllChats() {
        Collection<ChatDto> chats = chatMapper.chatToChatDto(chatService.findAllChats());
        return ResponseEntity.ok(chats);
    }
}

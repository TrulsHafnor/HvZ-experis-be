package academy.noroff.hvz.controllers;

import academy.noroff.hvz.enums.GameState;
import academy.noroff.hvz.mappers.ChatMapper;
import academy.noroff.hvz.mappers.GameMapper;
import academy.noroff.hvz.models.Chat;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.dtos.ChatDto;
import academy.noroff.hvz.models.dtos.CreateGameDto;
import academy.noroff.hvz.models.dtos.GameDto;
import academy.noroff.hvz.models.dtos.UpdateGameDto;
import academy.noroff.hvz.services.ChatService;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.utils.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/games")
@CrossOrigin(origins = {
    "https://hvz-fe-noroff.herokuapp.com/",
    "http://localhost:3000"
    }
)
public class GameController {
    private final GameService gameService;
    private final GameMapper gameMapper;
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    public GameController (GameService gameService, GameMapper gameMapper, ChatService chatService, ChatMapper chatMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
    }

    @Operation(summary = "Get one game whit game id (Open for all)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameDto.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Game does not exist with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{game_id}")
    public ResponseEntity getGameById(@PathVariable("game_id") int game_id) {
        GameDto game = gameMapper.gameToGameDto(gameService.findGameById(game_id));
        return ResponseEntity.ok(game);
    }

    @Operation(summary = "Get all games")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Game.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Cant find games",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity getAllGames() {
        Collection<GameDto> games = gameMapper.gameToGameDto(
                gameService.findAllGames()
        );
        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Create new game (Admin only)")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Game was successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
    })
    @PostMapping
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity addGame (@RequestBody CreateGameDto createGameDto) {
        Game game = gameMapper.createGameDtoTpGame(createGameDto);
        game.setStartState();
        gameService.addGame(game);
        URI location = URI.create("games/" + game.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Delete game whit game id (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GameDto.class))) }),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Game does not exist with supplied ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{game_id}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity deleteGame (@PathVariable("game_id") int game_id) {
        gameService.deleteGame(game_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update game whit game id (Admin only)")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Game was successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Game not found with supplied ID",
                    content = @Content)
    })
    @PutMapping("{game_id}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity updateGame(@RequestBody UpdateGameDto updateGameDto, @PathVariable("game_id") int game_id) {
        // Validates if body is correct
        if(game_id != updateGameDto.getId())
            return ResponseEntity.badRequest().build();
        gameService.updateGame(
                gameMapper.updateGameDtoToGameDto(updateGameDto)
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Post new chat message to game")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Chat message successfully posted",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
    })
    @PostMapping("{game_id}/chat")
    public ResponseEntity addChat (@RequestBody ChatDto chatDto, @PathVariable("game_id") int game_id) {
        if (chatDto.getGame() != game_id
                || chatDto.getGame() != gameService.findGameById(chatDto.getGame()).getId()
                || gameService.findGameById(chatDto.getGame()).getGameState() == GameState.COMPLETE) {
            return ResponseEntity.badRequest().build();
        }
        Chat chat = chatMapper.chatDtoToChat(chatDto);
        chatService.addChat(chat,chatDto.getPlayer());
        URI location = URI.create("games/" + game_id + "chat/" + chat.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get faction chat from game whit game id and player id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Chat.class)) }),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Cant find chat",
                    content = @Content)
    })
    @GetMapping("{game_id}/chat/{player_id}/faction")
    public ResponseEntity getAllChats(@PathVariable("game_id") int game_id, @PathVariable("player_id") int player_id) {
        Collection<ChatDto> chats = chatMapper.chatToChatDto(chatService.findAllChatsForPlayer(game_id, player_id));
        return ResponseEntity.ok(chats);
    }

    @Operation(summary = "Get global chat from game whit game id and player id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Chat.class)) }),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Cant find chat",
                    content = @Content)
    })
    @GetMapping("{game_id}/chat/{player_id}/global")
    public ResponseEntity getAllChatsGlobal(@PathVariable("game_id") int game_id, @PathVariable("player_id") int player_id) {
        Collection<ChatDto> chats = chatMapper.chatToChatDto(chatService.findAllChats(game_id));
        return ResponseEntity.ok(chats);
    }
}

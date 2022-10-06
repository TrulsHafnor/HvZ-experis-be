package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.GameMapper;
import academy.noroff.hvz.mappers.PlayerMapper;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.GameDto;
import academy.noroff.hvz.models.dtos.PlayerDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.utils.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final GameMapper gameMapper;
    private final PlayerMapper playerMapper;

    public GameController (GameService gameService, GameMapper gameMapper, PlayerMapper playerMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
        this.playerMapper = playerMapper;
    }

    @Operation(summary = "Get a game by ID")
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
    @GetMapping("{id}")
    public ResponseEntity getGameById(@PathVariable("id") int id) {
        return ResponseEntity.ok(gameMapper.gameToGameDto(gameService.findGameById(id)));
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
                gameService.finAllGames()
        );
        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Create new game")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Game successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
    })
    @PostMapping
    public ResponseEntity addGame (@RequestBody GameDto gameDto) {
        Game game = gameMapper.gameDtoToGame(gameDto);
        gameService.addGame(game);
        URI location = URI.create("game/" + game.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Delete a game by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GameDto.class))) }),
            @ApiResponse(responseCode = "404",
                    description = "Game does not exist with supplied ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })

    @DeleteMapping("{id}")
    public ResponseEntity deleteGame (@PathVariable("id") int id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Update a game by ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Game successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Game not found with supplied ID",
                    content = @Content)
    })
    @PutMapping("{id}")
    public ResponseEntity updateGame(@RequestBody GameDto gameDto, @PathVariable int id) {
        // Validates if body is correct
        if(id != gameDto.getId())
            return ResponseEntity.badRequest().build();
        gameService.updateGame(
                gameMapper.gameDtoToGame(gameDto)
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all players in game by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Game not found with supplied ID",
                    content = @Content)
    })
    @GetMapping("{id}/players")
    public ResponseEntity getPlayersInGame(@PathVariable int id) {
        Collection<Player> players= gameService.getPlayersInGames(id);
        Collection<PlayerDto> playerDtos = playerMapper.playersToPlayerDtos(players);
        return ResponseEntity.ok(playerDtos);

    }

}

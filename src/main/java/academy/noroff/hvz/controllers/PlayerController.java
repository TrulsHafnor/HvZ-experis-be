package academy.noroff.hvz.controllers;

import academy.noroff.hvz.enums.GameState;
import academy.noroff.hvz.mappers.GameMapper;
import academy.noroff.hvz.mappers.PlayerMapper;
import academy.noroff.hvz.models.AppUser;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.PlayerDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.PlayerService;
import academy.noroff.hvz.services.UserService;
import academy.noroff.hvz.utils.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAuthority;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = {
    "https://hvz-fe-noroff.herokuapp.com/",
    "http://localhost:3000"
    }
)
public class PlayerController {
    private final PlayerService playerService;
    private final GameMapper gameMapper;
    private final GameService gameService;
    private final PlayerMapper playerMapper;


    public PlayerController(PlayerService playerService, GameMapper gameMapper, GameService gameService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.gameMapper = gameMapper;
        this.gameService = gameService;
        this.playerMapper = playerMapper;
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
        Collection<PlayerDto> playerDtos = playerMapper.playerToPlayerDto(players);
        return ResponseEntity.ok(playerDtos);
    }

    @Operation(summary = "Find player in game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Game or Player not found with supplied ID",
                    content = @Content)
    })
    @GetMapping("{gameId}/player/{playerId}")
    public ResponseEntity getPlayerInGame(@PathVariable int gameId,@PathVariable int playerId) {
        // TODO: 10/7/2022 only admin can se if player is patient zero
        Player player = playerService.findPlayerInGame(gameId,playerId);
        PlayerDto playerDto = playerMapper.playerToPlayerDto(player);
        return ResponseEntity.ok(playerDto);
    }

    @Operation(summary = "Add player to game")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Player successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
    })
    @PostMapping("{gameId}/player")
    public ResponseEntity addPlayer (@PathVariable int gameId, @AuthenticationPrincipal Jwt jwt) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read:admin"))) {
            System.out.println("DETTE FUNKER JO!!!");
        }
        Game tempGame = gameService.findGameById(gameId);
        String userId = jwt.getClaimAsString("sub");
        if (tempGame.getGameState() != GameState.REGISTRATION) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerService.addNewPlayerToGame(tempGame, userId);
        URI location = URI.create("game/" + tempGame.getId() + "/player/" + player.getId());
        return ResponseEntity.created(location).build();
    }

    // TODO: 10/7/2022 Hjelpemetode for testing kan endresog slettes ved behov
    @Operation(summary = "Add player to game ADMIN ONLY")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Player successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
    })
    @PostMapping("player")
    public ResponseEntity addPlayerAdmin (@RequestBody PlayerDto playerDto) {

        Game tempGame = gameService.findGameById(playerDto.getGame());
        if (tempGame.getGameState() != GameState.REGISTRATION) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerMapper.playerDtoToPlayer(playerDto);
        playerService.addPlayerToGame(player);
        URI location = URI.create("game/" + tempGame.getId() + "/player/" + player.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update a player by ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Player successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Player not found with supplied ID",
                    content = @Content)
    })
    @PutMapping("{gameId}/player/{playerId}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity updatePlayer(@RequestBody PlayerDto playerDto, @PathVariable int gameId,@PathVariable int playerId) {
        // TODO: 10/7/2022 admin only
        // sjekker om player id som parameter er lik player id i body, og sjekker om game finnes i databasen
        if(playerId != playerDto.getId() || gameId != gameService.findGameById(gameId).getId())
            return ResponseEntity.badRequest().build();
        playerService.updatePlayer(
                playerMapper.playerDtoToPlayer(playerDto)
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a player by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PlayerDto.class))) }),
            @ApiResponse(responseCode = "404",
                    description = "Player does not exist with supplied ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("player/{playerId}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity deletePlayer(@PathVariable int playerId) {
        playerService.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }
}

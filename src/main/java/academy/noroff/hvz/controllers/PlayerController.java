package academy.noroff.hvz.controllers;

import academy.noroff.hvz.enums.GameState;
import academy.noroff.hvz.mappers.PlayerMapper;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.LessDetailsPlayerDto;
import academy.noroff.hvz.models.dtos.PlayerDto;
import academy.noroff.hvz.models.dtos.UpdatePlayerDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.PlayerService;
import academy.noroff.hvz.utils.ApiErrorResponse;
import academy.noroff.hvz.utils.ExtractId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@RestController
@RequestMapping("api/v1/games")
@CrossOrigin(origins = {
        "https://hvz-fe-noroff.herokuapp.com/",
        "http://localhost:3000"
})
public class PlayerController {
    private final PlayerService playerService;
    private final GameService gameService;
    private final PlayerMapper playerMapper;


    public PlayerController(PlayerService playerService,
                            GameService gameService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.playerMapper = playerMapper;
    }

    @Operation(summary = "Get all players in game by game ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Players not found in game whit supplied id",
                    content = @Content)
    })
    @GetMapping("{game_id}/players")
    public ResponseEntity getPlayersInGame(@PathVariable int game_id) {
        Collection<Player> players = gameService.getPlayersInGames(game_id);

        //check for admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read:admin"))) {
            Collection<PlayerDto> playerDtos = playerMapper.playersToPlayerDto(players);
            return ResponseEntity.ok(playerDtos);
        }
        Collection<LessDetailsPlayerDto> lessDetailsPlayerDtos = playerMapper.playersToLessDetailsPlayerDto(players);
        return ResponseEntity.ok(lessDetailsPlayerDtos);
    }

    @Operation(summary = "Find player in game with game and player ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Player not found in game whit supplied ids",
                    content = @Content)
    })
    @GetMapping("{game_id}/players/{player_id}")
    public ResponseEntity getPlayerInGame(@PathVariable int game_id, @PathVariable int player_id, @AuthenticationPrincipal Jwt jwt) {
        Player player = playerService.findPlayerInGame(game_id, player_id);

        //check for admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read:admin"))) {
           PlayerDto playerDto = playerMapper.playerToPlayerDto(player);
            return ResponseEntity.ok(playerDto);
        }

        //check if current user is correct
        String userId = ExtractId.extractId(jwt.getClaimAsString("sub"));
        if (userId.equals(player.getUser().getId())) {
            PlayerDto playerDto = playerMapper.playerToPlayerDto(player);
            return ResponseEntity.ok(playerDto);
        }

        LessDetailsPlayerDto noPatientZeroplayerDto = playerMapper.playerToLessDetailsPlayerDto(player);
        return ResponseEntity.ok(noPatientZeroplayerDto);
    }

    @Operation(summary = "Add player to game with gameId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Player successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
    })
    @PostMapping("{game_id}/players")
    public ResponseEntity addPlayer(@PathVariable int game_id, @AuthenticationPrincipal Jwt jwt) {
        Game tempGame = gameService.findGameById(game_id);
        String userId = jwt.getClaimAsString("sub");
        if (tempGame.getGameState() != GameState.REGISTRATION) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerService.addNewPlayerToGame(tempGame, userId);
        URI location = URI.create("games/" + tempGame.getId() + "/players/" + player.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Add player to game (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Player successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
    })
    @PostMapping("{game_id}/players/register")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity addPlayerAdmin(@PathVariable int game_id, @RequestBody UpdatePlayerDto updatePlayerDto) {

        Game tempGame = gameService.findGameById(game_id);
        if (tempGame.getGameState() == GameState.COMPLETE) {
            return ResponseEntity.badRequest().build();
        }
        Player player = playerMapper.updatePlayerDtoToPlayer(updatePlayerDto);
        playerService.addPlayerToGame(player);
        URI location = URI.create("games/" + game_id + "/players/" + player.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update a player by game and player ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Player successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Player not found with supplied id in game",
                    content = @Content)
    })
    @PutMapping("{game_id}/players/{player_id}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity updatePlayer(@RequestBody UpdatePlayerDto updatePlayerDto, @PathVariable int game_id, @PathVariable int player_id) {
        if (player_id != updatePlayerDto.getId() || game_id != gameService.findGameById(game_id).getId())
            return ResponseEntity.badRequest().build();
        playerService.updatePlayer(
                playerMapper.updatePlayerDtoToPlayer(updatePlayerDto)
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete player with player with game and player id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PlayerDto.class)))}),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Player does not exist with supplied ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @DeleteMapping("{game_id}/players/{player_id}")
    public ResponseEntity deletePlayer(@PathVariable int game_id, @PathVariable int player_id) {
        //check for admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read:admin"))) {
            playerService.deletePlayer(game_id, player_id);
            return ResponseEntity.noContent().build();
        }

        playerService.leavePlayer(game_id, player_id);
        return ResponseEntity.noContent().build();
    }
}

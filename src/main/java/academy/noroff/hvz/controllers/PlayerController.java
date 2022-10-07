package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.GameMapper;
import academy.noroff.hvz.mappers.PlayerMapper;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.dtos.PlayerDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
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
    public ResponseEntity getAllPlayers() {
        Collection<PlayerDto> player = playerMapper.playerToPlayerDto(
                playerService.findAllPlayers()
        );
        return ResponseEntity.ok(player);
    }
}

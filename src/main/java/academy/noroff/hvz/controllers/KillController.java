package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.KillMapper;
import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.models.dtos.KillDto;
import academy.noroff.hvz.models.dtos.MissionDto;
import academy.noroff.hvz.models.dtos.PlayerDto;
import academy.noroff.hvz.services.KillService;
import io.swagger.v3.oas.annotations.Operation;
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
@CrossOrigin(origins = "http://localhost:3000")
public class KillController {
    private final KillService killService;
    private final KillMapper killMapper;

    public KillController(KillService killService, KillMapper killMapper) {
        this.killService = killService;
        this.killMapper = killMapper;
    }


    @Operation(summary = "Get all kills in game by ID")
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
    @GetMapping("/{game_id}/kill")
    public ResponseEntity getPlayersInGame(@PathVariable int game_id) {
        Collection<Kill> kills = killService.findAllKillsInGame(game_id);
        Collection<KillDto> killDtos = killMapper.killToKillDto(kills);
        return ResponseEntity.ok(killDtos);
    }

    @Operation(summary = "register a new kill")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Kill created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
    })
    @PostMapping("{game_id}/kill/{biteCode}")
    public ResponseEntity addKill (@RequestBody KillDto killDto, @PathVariable int game_id, @PathVariable String biteCode) {
        // TODO: 10/10/2022 if admin u can change anyway 
        Kill kill = killMapper.killDtoToKill(killDto);
        if(!killService.createKill(kill,game_id,biteCode)) {
            return ResponseEntity.badRequest().build();
        }
        URI location = URI.create("kill/" + kill.getId());
        return ResponseEntity.created(location).build();
    }
}

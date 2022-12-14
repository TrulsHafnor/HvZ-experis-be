package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.KillMapper;
import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.dtos.KillDto;
import academy.noroff.hvz.models.dtos.RegisterKillDto;
import academy.noroff.hvz.models.dtos.UpdateKillDto;
import academy.noroff.hvz.services.KillService;
import academy.noroff.hvz.utils.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.web.error.ErrorAttributeOptions;
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
public class KillController {
    private final KillService killService;
    private final KillMapper killMapper;

    public KillController(KillService killService, KillMapper killMapper) {
        this.killService = killService;
        this.killMapper = killMapper;
    }


    @Operation(summary = "Get all kills in game whit game id")
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
    @GetMapping("{game_id}/kills")
    public ResponseEntity getKillsInGame(@PathVariable("game_id") int game_id) {
        Collection<Kill> kills = killService.findAllKillsInGame(game_id);
        Collection<KillDto> killDtos = killMapper.killToKillDto(kills);
        return ResponseEntity.ok(killDtos);
    }

    @Operation(summary = "Register a new kill")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Kill created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Cant find player in game whit bitecode",
                    content = @Content)
    })
    @PostMapping("{game_id}/kills")
    public ResponseEntity addKill (@RequestBody RegisterKillDto registerKillDto, @PathVariable("game_id") int game_id) {
        KillDto killDto = killService.registerKillDtoToKillDto(registerKillDto);
        Kill kill = killMapper.killDtoToKill(killDto);
        if(game_id != registerKillDto.getGame() || !killService.createKill(kill,game_id)) {
            return ResponseEntity.badRequest().build();
        }
        URI location = URI.create("kills/" + kill.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get kill object by game id and kill id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = KillDto.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Kill does not exist with supplied ID in game",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{game_id}/kills/{kill_id}")
    public ResponseEntity getGameById(@PathVariable("game_id") int game_id, @PathVariable("kill_id") int kill_id) {
        return ResponseEntity.ok(killMapper.killToKillDto(killService.findKillInGameById(game_id,kill_id)));
    }

    @Operation(summary = "Delete kill whit game and kill id (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = KillDto.class))) }),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Kill does not exist with supplied ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{game_id}/kills/{kill_id}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity deleteKill (@PathVariable("game_id") int game_id,@PathVariable("kill_id") int kill_id) {
        Kill tempKill = killService.getKillInGame(game_id, kill_id);
        if(tempKill == null){
            return ResponseEntity.notFound().build();
        }
        killService.deleteKill(kill_id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Update kill with game and kill id  (Admin only)")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Kill successfully updated",
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
                    description = "Kill not found with supplied ID in game",
                    content = @Content)
    })
    @PutMapping("{game_id}/kills/{kill_id}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity updateKill(@RequestBody UpdateKillDto updateKillDto, @PathVariable("game_id") int game_id , @PathVariable("kill_id") int kill_id) {
        if(kill_id != updateKillDto.getId() || updateKillDto.getGame() != game_id)
            return ResponseEntity.badRequest().build();
        killService.updateKill(
                killMapper.updateKillDtoToKill(updateKillDto)
        );
        return ResponseEntity.noContent().build();
    }
}

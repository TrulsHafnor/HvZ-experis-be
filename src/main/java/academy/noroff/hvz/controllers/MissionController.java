package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.MissionMapper;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.dtos.MissionDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.MissionService;
import academy.noroff.hvz.services.PlayerService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class MissionController {
    protected MissionMapper missionMapper;
    protected MissionService missionService;
    protected GameService gameService;
    protected PlayerService playerService;

    public MissionController (
            MissionMapper missionMapper,
            MissionService missionService,
            GameService gameService,
            PlayerService playerService){
        this.missionMapper = missionMapper;
        this.missionService = missionService;
        this.gameService=gameService;
        this.playerService = playerService;
    }

    @Operation(summary = "Get mission whit game id, mission id and player id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MissionDto.class)) }),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden access.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Mission does not exist with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{game_id}/missions/{mission_id}/players/{player_id}")
    public ResponseEntity getMissionById(@PathVariable int game_id,@PathVariable int mission_id, @PathVariable int player_id) {
        MissionDto missionCheck = missionMapper.missionToMissionDto(missionService.getMissionInGame(game_id, mission_id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read:admin"))) {
            return ResponseEntity.ok(missionCheck);
        }

        if (playerService.findPlayerById(player_id).getGame().getId() != game_id ||
                !missionService.checkMissionType(missionCheck.getMissionVisibility(), player_id, game_id)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(missionCheck);
    }

    @Operation(summary = "Get all missions in game whit game id and player id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MissionDto.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Can't find missions in game",
                    content = @Content)
    })
    @GetMapping("{game_id}/missions/{player_id}")
    public ResponseEntity getAllMissions(@PathVariable int game_id,@PathVariable int player_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read:admin"))) {
            Collection<MissionDto> missions = missionMapper.missionToMissionDto(
                    missionService.findAllMissionsInGameAdmin(game_id)
            );
            return ResponseEntity.ok(missions);
        }
        Collection<MissionDto> missions = missionMapper.missionToMissionDto(
                missionService.findAllMissionsInGame(game_id, player_id)
        );
        return ResponseEntity.ok(missions);
    }

    @Operation(summary = "Create new mission (Admin only)")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Mission created successfully",
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
    @PostMapping("{game_id}/missions")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity addMission (@RequestBody MissionDto missionDto, @PathVariable int game_id) {
        if (missionDto.getGame() != gameService.findGameById(missionDto.getGame()).getId()) {
            return ResponseEntity.badRequest().build();
        }
        Mission mission = missionMapper.missionDtoToMission(missionDto);
        missionService.addMission(mission);
        URI location = URI.create("games/" + game_id + "missions/" + mission.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Delete mission by ID (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MissionDto.class))) }),
            @ApiResponse(responseCode = "403",
                    description = "Access forbidden",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Mission does not exist with supplied ID in game",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{game_id}/missions/{mission_id}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity deleteMission (@PathVariable int game_id, @PathVariable int mission_id) {
        Mission tempMission = missionService.getMissionInGame(game_id, mission_id);
        if(tempMission == null){
            return ResponseEntity.notFound().build();
        }
        missionService.deleteMission(game_id, mission_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update mission by ID (Admin only)")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Mission successfully updated",
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
                    description = "Mission not found with supplied ID",
                    content = @Content)
    })
    @PutMapping("{game_id}/missions/{mission_id}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity updateMission(@RequestBody MissionDto missionDto, @PathVariable int mission_id, @PathVariable int game_id) {
        if(mission_id != missionDto.getId() || missionDto.getGame() != game_id)
            return ResponseEntity.badRequest().build();
        missionService.updateMission(
                missionMapper.missionDtoToMission(missionDto)
        );
        return ResponseEntity.noContent().build();
    }
}

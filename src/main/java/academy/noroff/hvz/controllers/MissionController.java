package academy.noroff.hvz.controllers;

import academy.noroff.hvz.enums.MissionVisibility;
import academy.noroff.hvz.mappers.MissionMapper;
import academy.noroff.hvz.mappers.PlayerMapper;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.dtos.MissionDto;
import academy.noroff.hvz.repositories.MissionRepository;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = {
    "https://hvz-fe-noroff.herokuapp.com/",
    "http://localhost:3000",
    "https://humansandzombiesexperiss.herokuapp.com/"
    }
)
public class MissionController {
    protected MissionMapper missionMapper;
    protected MissionService missionService;
    protected GameService gameService;
    protected MissionRepository missionRepository;
    protected PlayerService playerService;

    public MissionController (
            MissionMapper missionMapper,
            MissionService missionService,
            GameService gameService,
            MissionRepository missionRepository,
            PlayerService playerService){
        this.missionMapper = missionMapper;
        this.missionService = missionService;
        this.gameService=gameService;
        this.missionRepository = missionRepository;
        this.playerService = playerService;
    }

    @Operation(summary = "Get a mission by ID")
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

    @GetMapping("{game_id}/mission/{mission_id}/player/{player_id}")
    public ResponseEntity getMissionById(@PathVariable int mission_id, @PathVariable int game_id, @PathVariable int player_id) {
        Mission missionCheck = missionService.getMissionInGame(game_id, mission_id);
        if(playerService.findPlayerById(player_id).getGame().getId() != game_id || !checkMissionType(missionCheck.getMissionVisibility(), player_id, game_id)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(missionMapper.missionToMissionDto(missionCheck));
    }

    public Boolean checkMissionType(MissionVisibility missionVisibility, int player_id, int game_id) {
        if(missionVisibility == MissionVisibility.GLOBAL){
            return true;
        }
        if(playerService.findPlayerById(player_id).isHuman() && missionVisibility == MissionVisibility.HUMAN) {
            return true;
        }
        if (!playerService.findPlayerById(player_id).isHuman() && missionVisibility == MissionVisibility.ZOMBIE){
            return true;
        }
        return false;
    }

    @Operation(summary = "Get all missions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mission.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Can't find missions",
                    content = @Content)
    })
    @GetMapping("{game_id}/mission/{missionType}")
    public ResponseEntity getAllMissions(@PathVariable int game_id,  @PathVariable MissionVisibility missionType) {
        Collection<MissionDto> missions = missionMapper.missionToMissionDto(
                missionService.findAllMissions(game_id, missionType)
        );
        return ResponseEntity.ok(missions);
    }

    @Operation(summary = "Create a new mission")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Mission created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
    })
    @PostMapping("{game_id}/mission")
    public ResponseEntity addMission (@RequestBody MissionDto missionDto, @PathVariable int game_id) {
        if (missionDto.getGame() != gameService.findGameById(missionDto.getGame()).getId()) {
            return ResponseEntity.badRequest().build();
        }
        Mission mission = missionMapper.missionDtoToMission(missionDto);
        missionService.addMission(mission);
        URI location = URI.create("mission/" + mission.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Delete a mission by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MissionDto.class))) }),
            @ApiResponse(responseCode = "403",
                    description = "Mission does not exist in this game",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Mission does not exist with supplied ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })

    @DeleteMapping("{game_id}/mission/{mission_id}")
    public ResponseEntity deleteMission (@PathVariable int mission_id, @PathVariable int game_id) {
        // TODO: 10/10/2022 Admin only
        Mission tempMission = missionService.getMissionInGame(game_id, mission_id);
        if(tempMission == null){
            return ResponseEntity.badRequest().build();
        }
        missionService.deleteMission(mission_id, game_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a mission by ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204",
                    description = "Mission successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Mission not found with supplied ID",
                    content = @Content)
    })
    @PutMapping("{game_id}/mission/{mission_id}")
    public ResponseEntity updateMission(@RequestBody MissionDto missionDto, @PathVariable int mission_id, @PathVariable int game_id) {
        // Validates if body is correct
        if(mission_id != missionDto.getId())
            return ResponseEntity.badRequest().build();
        missionService.updateMission(
                missionMapper.missionDtoToMission(missionDto)
        );
        return ResponseEntity.noContent().build();
    }

}

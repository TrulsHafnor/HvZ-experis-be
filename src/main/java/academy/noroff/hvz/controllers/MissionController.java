package academy.noroff.hvz.controllers;

import academy.noroff.hvz.enums.MissionVisibility;
import academy.noroff.hvz.mappers.MissionMapper;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.dtos.MissionDto;
import academy.noroff.hvz.services.GameService;
import academy.noroff.hvz.services.MissionService;
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
@CrossOrigin(origins = "http://localhost:3000") // TODO: 10/7/2022 fix for later (Sondre sec master)
public class MissionController {
    protected MissionMapper missionMapper;
    protected MissionService missionService;
    protected GameService gameService;

    public MissionController (MissionMapper missionMapper, MissionService missionService, GameService gameService){
        this.missionMapper = missionMapper;
        this.missionService = missionService;
        this.gameService=gameService;
    }
    /*
    @Operation(summary = "Get a mission by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MissionDto.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Mission does not exist with supplied ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    // TODO: 10/7/2022
    @GetMapping("{game_id}/mission/{mission_id}")
    public ResponseEntity getMissionById(@PathVariable int missionId, int gameId) {
        return ResponseEntity.ok(missionMapper.missionToMissionDto(missionService.findMissionById(missionId, gameId)));
    }
     */

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
    public ResponseEntity addMission (@RequestBody MissionDto missionDto) {
        if (missionDto.getGame() != gameService.findGameById(missionDto.getGame()).getId()) {
            return ResponseEntity.badRequest().build();
        }
        Mission mission = missionMapper.missionDtoToMission(missionDto);
        missionService.addMission(mission);
        URI location = URI.create("mission/" + mission.getId());
        return ResponseEntity.created(location).build();
    }


    /*
    @Operation(summary = "Delete a mission by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MissionDto.class))) }),
            @ApiResponse(responseCode = "404",
                    description = "Mission does not exist with supplied ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    */

    /*
    @DeleteMapping("{game_id}/mission/{mission_id}")
    public ResponseEntity deleteMission (@PathVariable("missionId") int missionId) {
        missionService.deleteMission(missionId);
        return ResponseEntity.noContent().build();
    }
    */


    /*
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
    public ResponseEntity updateMission(@RequestBody MissionDto missionDto, @PathVariable int missionId) {
        // Validates if body is correct
        if(missionId != missionDto.getMissionId())
            return ResponseEntity.badRequest().build();
        missionService.updateMission(
                missionMapper.missionDtoToMission(missionDto)
        );
        return ResponseEntity.noContent().build();
    }
    */

}

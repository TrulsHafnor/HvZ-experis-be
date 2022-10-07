package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.MissionMapper;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.dtos.GameDto;
import academy.noroff.hvz.models.dtos.MissionDto;
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
@RequestMapping("/game/<game_id>/mission")
@CrossOrigin(origins = "https://localhost:3000") // TODO: 10/7/2022 fix for later (Sondre sec master)
public class MissionController {
    protected MissionMapper missionMapper;
    protected MissionService missionService;

    public MissionController (MissionMapper missionMapper, MissionService missionService){
        this.missionMapper = missionMapper;
        this.missionService = missionService;
    }

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
    @GetMapping("{id}")
    public ResponseEntity getMissionById(@PathVariable("id") int id) {
        return ResponseEntity.ok(missionMapper.missionToMissionDto(missionService.findMissionById(id)));
    }

    @Operation(summary = "Get all missions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Game.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Can't find missions",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity getAllMissions() {
        Collection<MissionDto> missions = missionMapper.missionToMissionDto(
                missionService.findAllMissions()
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
    @PostMapping
    public ResponseEntity addMission (@RequestBody MissionDto missionDto) {
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
            @ApiResponse(responseCode = "404",
                    description = "Mission does not exist with supplied ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })

    @DeleteMapping("{id}")
    public ResponseEntity deleteMission (@PathVariable("id") int id) {
        missionService.deleteMission(id);
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
    @PutMapping("{id}")
    public ResponseEntity updateMission(@RequestBody MissionDto missionDto, @PathVariable int id) {
        // Validates if body is correct
        if(id != missionDto.getId())
            return ResponseEntity.badRequest().build();
        missionService.updateMission(
                missionMapper.missionDtoToMission(missionDto)
        );
        return ResponseEntity.noContent().build();
    }



}

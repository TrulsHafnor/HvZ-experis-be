package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.PlayerMapper;
import academy.noroff.hvz.mappers.SquadCheckinMapper;
import academy.noroff.hvz.mappers.SquadMapper;
import academy.noroff.hvz.models.Squad;
import academy.noroff.hvz.models.SquadCheckin;
import academy.noroff.hvz.models.dtos.*;
import academy.noroff.hvz.services.SquadService;
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
public class SquadController {
    private final SquadService squadService;
    private final SquadMapper squadMapper;
    private final SquadCheckinMapper squadCheckinMapper;
    private final PlayerMapper playerMapper;

    @Autowired
    public SquadController(SquadService squadService, SquadMapper squadMapper, SquadCheckinMapper squadCheckinMapper, PlayerMapper playerMapper) {
        this.squadService=squadService;
        this.squadMapper=squadMapper;
        this.squadCheckinMapper=squadCheckinMapper;
        this.playerMapper=playerMapper;
    }

    @Operation(summary = "Create new squad")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Squad was successfully created",
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
                    description = "Squad does not exist with supplied ID in game",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "409",
                    description = "You are already in a squad",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })

    })
    @PostMapping("/{game_id}/squad")
    public ResponseEntity createSquad (@RequestBody CreateSquadDto createSquadDto, @PathVariable("game_id") int game_id) {
        if (createSquadDto.getGame() != game_id) {
            return ResponseEntity.badRequest().build();
        }
        Squad squad = squadMapper.createSquadDtoToSquadDto(createSquadDto);
        squad = squadService.createSquad(squad);
        URI location = URI.create("games/"+game_id+"/squad/" + squad.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Find squad in game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "404",
                    description = "'Squad not found in game whith supplied ID",
                    content = @Content)
    })
    @GetMapping("/{game_id}/squad/{squad_id}")
    public ResponseEntity getSquadInGame(@PathVariable int game_id, @PathVariable int squad_id) {
        Squad squad = squadService.findSquadInGame(game_id, squad_id);
        SquadDto squadDto = squadMapper.squadToSquadDto(squad);
        return ResponseEntity.ok(squadDto);
    }

    @Operation(summary = "Get all squads in game by ID")
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
    @GetMapping("/{game_id}/squads")
    public ResponseEntity getSquadsInGame(@PathVariable int game_id) {
        Collection<Squad> squads = squadService.findAllSquadsInGame(game_id);
        Collection<SquadDto> squadDto = squadMapper.squadToSquadDto(squads);
        return ResponseEntity.ok(squadDto);
    }


    @Operation(summary = "Get all players in squad")
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
    @GetMapping("/{game_id}/squad/{squad_id}/players")
    public ResponseEntity getAllplayersInSquad (@PathVariable int game_id, @PathVariable int squad_id) {
        Collection<LessDetailsPlayerDto> playersDto = playerMapper.playersToLessDetailsPlayerDto(squadService.getAllPlayersInSquad(game_id,squad_id));

        return ResponseEntity.ok(playersDto);
    }

    @Operation(summary = "Join squad")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Joined squad",
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
                    description = "Squad does not exist with supplied ID in game",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "409",
                    description = "You are already in a squad",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) })

    })
    @PostMapping("/{game_id}/squad/{squad_id}/join")
    public ResponseEntity joinSquad (@RequestBody JoinSquadDto joinSquadDto, @PathVariable("game_id") int game_id, @PathVariable("squad_id") int squad_id) {
        squadService.joinSquad(game_id, squad_id, joinSquadDto);
       URI location = URI.create("games/"+game_id+"/squad/"+squad_id+"/join");
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Leave squad with player id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SquadDto.class)))}),
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
    @DeleteMapping("/{game_id}/squad/{player_id}/leave")
    public ResponseEntity leaveSquad(@PathVariable int game_id, @PathVariable int player_id) {
        // TODO: 10/18/2022  er denne sikker nok Sondre?
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read:admin"))) {
            squadService.leaveSquad(game_id, player_id);
            return ResponseEntity.noContent().build();
        }
        squadService.leaveSquad(game_id, player_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a squad by player ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Success",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SquadDto.class)))}),
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
    @DeleteMapping("/{game_id}/squad/{squad_id}")
    public ResponseEntity deleteSquad(@PathVariable int game_id, @PathVariable int squad_id) {
        //check for admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read:admin"))) {
            squadService.deleteSquad(game_id, squad_id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update a squad in game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "squad successfully updated",
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
                    description = "Player not found with supplied ID",
                    content = @Content)
    })
    @PutMapping("/{game_id}/squad/{squad_id}")
    @PreAuthorize("hasAuthority('read:admin')")
    public ResponseEntity updateSquad(@RequestBody UpdateSquadDto updateSquadDto,@PathVariable int game_id, @PathVariable int squad_id) {
        if (game_id != updateSquadDto.getGame())
            return ResponseEntity.badRequest().build();
        squadService.updateSquad(squadMapper.updateSquadDtoToSquadDto(updateSquadDto));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create squad checkin")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201",
                    description = "Squad checkin was successfully created",
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
                    description = "Squad does not exist with supplied ID in game",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
    })
    @PostMapping("/{game_id}/squads/{squad_id}/check-in")
    public ResponseEntity createSquadCheckin (@RequestBody CreateCheckinDto createCheckinDto, @PathVariable("game_id") int game_id, @PathVariable("squad_id") int squad_id) {
        if (createCheckinDto.getGame() != game_id) {
            return ResponseEntity.badRequest().build();
        }
        SquadCheckin tempSquadCheckin = squadCheckinMapper.createCheckinDtoToSquadCheckin(createCheckinDto);
        squadService.creatSquadCheckin(tempSquadCheckin);
        URI location = URI.create("games/"+game_id+"/squads/"+squad_id+"/squadMembers/"+createCheckinDto.getSquadMember()+"/check-in");
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get all squads check-ins in game by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Cant find any squad check ins",
                    content = @Content)
    })
    @GetMapping("/{game_id}/squad/{squad_id}/check-in")
    public ResponseEntity getSquadCheckinsInGame(@PathVariable int game_id, @PathVariable int squad_id) {
        Collection<SquadCheckin> squadCheckins = squadService.findAllSquadsCheckinsInSquadByGameId(game_id, squad_id);
        Collection<SquadCheckinDto> squadCheckinDtos = squadCheckinMapper.squadCheckinToSquadCheckinDto(squadCheckins);
        return ResponseEntity.ok(squadCheckinDtos);
    }



}

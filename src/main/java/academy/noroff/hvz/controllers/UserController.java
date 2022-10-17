package academy.noroff.hvz.controllers;

import academy.noroff.hvz.mappers.AppUserMapper;
import academy.noroff.hvz.models.AppUser;
import academy.noroff.hvz.models.dtos.AppUserDto;
import academy.noroff.hvz.services.UserService;
import academy.noroff.hvz.utils.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = {
        "https://hvz-fe-noroff.herokuapp.com/",
        "http://localhost:3000"
})
public class UserController {
    private final UserService userService;
    private final AppUserMapper appUserMapper;

    public UserController(UserService userService, AppUserMapper appUserMapper) {
        this.userService = userService;
        this.appUserMapper = appUserMapper;
    }

    @Operation(summary = "Get currently logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppUserDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No user logged in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping("current")
    public ResponseEntity getCurrentlyLoggedInUser(@AuthenticationPrincipal Jwt jwt) {
        AppUser appUser = userService.findById(jwt.getClaimAsString("sub"));
        AppUserDto appUserDto = appUserMapper.AppUserToAppUserDto(appUser);
        return ResponseEntity.ok(appUserDto);
    }

    @Operation(summary = "Get user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppUserDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No user logged in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping("principal")
    public ResponseEntity getPrincipal(Principal user) {
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorAttributeOptions.class))}),
    })
    @PostMapping()
    public ResponseEntity addNewUserFromJwt(@AuthenticationPrincipal Jwt jwt) {
        String id = jwt.getClaimAsString("sub");
        AppUser appUser = userService.add(id);
        URI uri = URI.create("users/" + appUser.getId());
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppUserDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Cant find games",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}

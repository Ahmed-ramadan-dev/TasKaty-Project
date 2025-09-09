package com.spring.boot.Controller.Security;

import com.spring.boot.Dto.Security.UserLoginDto;

import com.spring.boot.Service.Security.AuthService;
import com.spring.boot.Vm.TokenResponseVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API", description = "APIs for user authentication and registration")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    @SecurityRequirements
    @Operation(summary = "User Login", description = "Authenticates a user and returns access and refresh tokens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Incorrect password"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<TokenResponseVm> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        TokenResponseVm tokens = authService.login(userLoginDto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refreshAccessToken")
    @SecurityRequirements
    @Operation(summary = "Refresh Access Token", description = "Generates a new access token using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New access token generated"),
            @ApiResponse(responseCode = "401", description = "Refresh token invalid or expired")
    })
    public ResponseEntity<TokenResponseVm> refreshToken(@RequestParam String refreshToken) {
        TokenResponseVm tokens = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    @SecurityRequirements
    @Operation(summary = "User Logout", description = "Logs out the user and clears the refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Refresh token invalid or expired")
    })
    public ResponseEntity<String> logout(@RequestParam String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok("Logout successful. Refresh token cleared.");
    }
}

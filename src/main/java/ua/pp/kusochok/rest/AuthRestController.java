package ua.pp.kusochok.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ua.pp.kusochok.errors.UsernameAlreadyExists;
import ua.pp.kusochok.rest.dto.ErrorResponseDto;
import ua.pp.kusochok.rest.dto.SignInRequestDto;
import ua.pp.kusochok.rest.dto.SignInResponseDto;
import ua.pp.kusochok.rest.dto.SignUpRequestDto;
import ua.pp.kusochok.security.SecurityUser;
import ua.pp.kusochok.services.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthRestController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody SignInRequestDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.username, request.password
            ));

            SignInResponseDto response = authService.authenticate(request);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ErrorResponseDto("Invalid username or password"), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpRequestDto request) {
        try {
            authService.register(request);

            return ResponseEntity.noContent().build();
        } catch (UsernameAlreadyExists e) {
            return ResponseEntity.ok(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        try {
            return ResponseEntity.ok(Map.of("authUsername", ((SecurityUser) authentication.getPrincipal()).getMsg()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

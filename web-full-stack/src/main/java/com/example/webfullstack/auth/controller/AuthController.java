package com.example.webfullstack.auth.controller;

import com.example.webfullstack.auth.domain.entity.RefreshToken;
import com.example.webfullstack.auth.domain.entity.User;
import com.example.webfullstack.auth.service.RefreshTokenService;
import com.example.webfullstack.auth.service.UserService;
import com.example.webfullstack.common.exception.TokenRefreshException;
import com.example.webfullstack.security.jwt.JwtUtils;
import com.example.webfullstack.security.payload.request.LoginRequest;
import com.example.webfullstack.security.payload.request.TokenRefreshRequest;
import com.example.webfullstack.security.payload.response.JwtResponse;
import com.example.webfullstack.security.payload.response.TokenRefreshResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDetails = (User) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        String role = userDetails.getRole().getAuthority();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(
                new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                        userDetails.getUsername(), userDetails.getEmail(), role));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserId)
                .map(userService::loadUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromId(user.getId(), user.getRole());
                    refreshTokenService.deleteByToken(requestRefreshToken);
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(
                            user.getId());
                    return ResponseEntity.ok(
                            new TokenRefreshResponse(token, newRefreshToken.getToken()));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database"));
    }

    @PostMapping("/signout")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void signOut(@RequestBody String refreshToken) {
        SecurityContextHolder.clearContext();
        refreshTokenService.deleteByToken(refreshToken);
    }
}

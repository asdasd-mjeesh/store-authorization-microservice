package com.access_security.api.rest_controller;

import com.access_security.exception.ExecuteActionException;
import com.access_security.model.request.auth.AuthenticationRequest;
import com.access_security.model.response.auth.AuthenticationDataResponse;
import com.access_security.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public AuthenticationControllerV1(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationDataResponse> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        if (userDetails != null) {
            var token = jwtUtils.generateToken(userDetails);
            var authenticationData = new AuthenticationDataResponse(userDetails.getUsername(), token);
            return ResponseEntity.ok(authenticationData);
        }
        throw new ExecuteActionException("Failed to authenticate", HttpStatus.CONFLICT);
    }
}

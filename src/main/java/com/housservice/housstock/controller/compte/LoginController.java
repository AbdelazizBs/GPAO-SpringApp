package com.housservice.housstock.controller.compte;

import com.housservice.housstock.model.LoginRequest;
import com.housservice.housstock.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.LoginException;

public class LoginController {
    private final CompteService loginService;

    public LoginController(CompteService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = loginService.login(loginRequest);
            return ResponseEntity.ok(token);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

package com.housservice.housstock.controller.compte;

import com.housservice.housstock.model.Authrespone;
import com.housservice.housstock.model.LoginRequest;
import com.housservice.housstock.service.CompteService;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.LoginException;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/compte")
@Api(tags = {"Compte Management"})
public class LoginController {
    private final CompteService loginService;

    public LoginController(CompteService loginService) {
        this.loginService = loginService;
    }

    @SneakyThrows
    @PutMapping("/login")
    public ResponseEntity<Authrespone> login(@Valid @RequestBody LoginRequest loginRequest) {
            String token = loginService.login(loginRequest);
            return ResponseEntity.ok(new Authrespone(token));

    }


    @GetMapping("/home")
    public String hello(){
        return "Hello world";
    }
}

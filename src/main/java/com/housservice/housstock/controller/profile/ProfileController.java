package com.housservice.housstock.controller.profile;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Authrespone;
import com.housservice.housstock.model.Compte;
import com.housservice.housstock.model.LoginRequest;
import com.housservice.housstock.model.Personnel;
import com.housservice.housstock.model.dto.CompteDto;
import com.housservice.housstock.service.CompteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/profile")
@Api(tags = {"Compte Management"})
public class ProfileController {
    private final CompteService loginService;
    private final MessageHttpErrorProperties messageHttpErrorProperties;


    public ProfileController(CompteService loginService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.loginService = loginService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }
    @GetMapping("/getCompteid/{id}")
    public Optional<Compte> getCompteid(@ApiParam(name = "id", value = "id", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id) {
        return loginService.getCompteById(id);
    }
    @GetMapping("/getPersonnelid/{username}")
    public Optional<Personnel> getPersonnelid(@ApiParam(name = "username", value = "username", required = true) @PathVariable(value = "username", required = true) @NotEmpty(message = "{http.error.0001}") String username) {
        return loginService.getPersonnelById(username);
    }
}

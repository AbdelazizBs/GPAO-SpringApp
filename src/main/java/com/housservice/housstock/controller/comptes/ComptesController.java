package com.housservice.housstock.controller.comptes;


import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.ComptesDto;

import com.housservice.housstock.service.ComptesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/compte")
@Api(tags = {"comptes Management"})
public class ComptesController {

    final
    MessageHttpErrorProperties messageHttpErrorProperties;
    final
    ComptesService comptesService ;

    public ComptesController(ComptesService comptesService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.comptesService = comptesService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }

//    @PutMapping("/addCompte/{idPersonnel}")
//    public ResponseEntity<String> addCompte(
//            @ApiParam(name = "idPersonnel", value="id of personnel", required = true)
//            @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}") String idPersonnel,
//            final String  email,
//            final String  password,
//            final List<String> roles
//    ) throws ResourceNotFoundException {
//        comptesService.addCompte(idPersonnel,email,password,roles);
//        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
//    }
@PutMapping("/addCompte/{idPersonnel}")
public ResponseEntity <String> addCompte(
        @ApiParam(name = "idPersonnel", value="id of personnel", required = true)
        @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}") String idPersonnel,
        @Valid @RequestBody ComptesDto comptesDto
) throws ResourceNotFoundException {
    comptesService.addCompte(idPersonnel,comptesDto);
    return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
}
    @GetMapping("/getRoles/{email}")
    public List<String> getRoles(
            @PathVariable(value = "email", required = true) @NotEmpty(message = "{http.error.0001}") String email)
            throws ResourceNotFoundException {
        return comptesService.getRoles(email);
    }
}

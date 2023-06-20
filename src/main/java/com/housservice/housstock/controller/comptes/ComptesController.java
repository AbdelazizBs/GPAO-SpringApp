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
import java.util.Map;

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

    @PutMapping("/addCompte/{idPersonnel}")
    public ResponseEntity<String> addCompte(
            @ApiParam(name = "idPersonnel", value="id of personnel", required = true)
            @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}") String idPersonnel,
            @RequestBody ComptesDto comptesDto) throws ResourceNotFoundException {
        comptesService.addCompte(idPersonnel,comptesDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }


    @PutMapping("/updateCompte/{idPersonnel}")
    public ResponseEntity<String> updateCompte(
            @ApiParam(name = "idPersonnel", value="id of personnel", required = true)
            @PathVariable(value = "idPersonnel", required = true) @NotEmpty(message = "{http.error.0001}") String idPersonnel,
            @RequestBody ComptesDto comptesDto) throws ResourceNotFoundException {
        comptesService.updateCompte(idPersonnel,comptesDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @DeleteMapping("/deleteCompte/{idCompte}")
    public ResponseEntity<String> deleteCompte(
            @ApiParam(name = "idCompte", value="id of compte", required = true)
            @PathVariable(value = "idCompte", required = true) @NotEmpty(message = "{http.error.0001}") String idCompte) throws ResourceNotFoundException {
        comptesService.deleteCompte(idCompte);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @PutMapping("/restaurer/{idCompte}")
    public ResponseEntity<String> restaurer(
            @ApiParam(name = "idCompte", value="id of compte", required = true)
            @PathVariable(value = "idCompte", required = true) @NotEmpty(message = "{http.error.0001}") String idCompte) throws ResourceNotFoundException {
        comptesService.restaurer(idCompte);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @PutMapping("/miseEnVeille/{idCompte}")
    public ResponseEntity<String> miseEnVeille(
            @ApiParam(name = "idCompte", value="id of compte", required = true)
            @PathVariable(value = "idCompte", required = true) @NotEmpty(message = "{http.error.0001}") String idCompte) throws ResourceNotFoundException {
        comptesService.miseEnVeille(idCompte);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @GetMapping("/getRoles/{email}")
    public String getRoles(
            @PathVariable(value = "email", required = true) @NotEmpty(message = "{http.error.0001}") String email)
            throws ResourceNotFoundException {
        return comptesService.getRoles(email);
    }

    @GetMapping("/getAllCompte")
    public ResponseEntity<Map<String, Object>> getAllCompte(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size)
            throws ResourceNotFoundException {
        return comptesService.getAllCompte(page,size);
    }
    @GetMapping("/getAllCompteEnVeille")
    public ResponseEntity<Map<String, Object>> getAllCompteEnVeille(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size)
            throws ResourceNotFoundException {
        return comptesService.getAllCompteEnVeille(page,size);
    }
}

package com.housservice.housstock.controller.ligneCommandeClient;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.LigneCommandeClient;
import com.housservice.housstock.model.dto.LigneCommandeClientDto;
import com.housservice.housstock.service.LigneCommandeClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/ligneCommandeClient")
@Api(tags = {"Ligne Commande Client Management"})
public class LigneCommandeClientController {

    private LigneCommandeClientService ligneCommandeClientService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;


    @Autowired
    public LigneCommandeClientController(LigneCommandeClientService ligneCommandeClientService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.ligneCommandeClientService = ligneCommandeClientService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }

    @GetMapping("/getAllLigneCommandeClient")
    public List<LigneCommandeClientDto> getAllLigneCommandeClient() {
        return ligneCommandeClientService.getAllLigneCommandeClient();
    }

    @GetMapping("/getAllLigneCommandeClientFermer")
    public List<LigneCommandeClient> getAllLigneCommandeClientFermer() {

        return ligneCommandeClientService.getAllLigneCommandeClientFermer();

    }

    @GetMapping("/getLignCmdByIdCmd/{idCmd}")
    public List<LigneCommandeClient> getLignCmdByIdCmd(
            @PathVariable(value = "idCmd") final String idCmd) throws ResourceNotFoundException {
        return ligneCommandeClientService.getLignCmdByIdCmd(idCmd);
    }

    @GetMapping("/getLigneCommandeClientById/{id}")
    @ApiOperation(value = "service to get one LigneCommandeClient by Id.")
    public ResponseEntity<LigneCommandeClientDto> getLigneCommandeClientById(
            @ApiParam(name = "id", value = "id of ligneCommandeClient", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String ligneCommandeClientId)
            throws ResourceNotFoundException {
        LigneCommandeClientDto ligneCommandeClient = ligneCommandeClientService.getLigneCommandeClientById(ligneCommandeClientId);
        if (ligneCommandeClient == null) {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok().body(ligneCommandeClient);
    }

    @PutMapping("/createLigneCommandeClient")
    public ResponseEntity<String> createLigneCommandeClient(@RequestBody LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException {

        ligneCommandeClientService.createNewLigneCommandeClient(ligneCommandeClientDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }

    @PutMapping("/updateLigneCommandeClient/{id}")
    public ResponseEntity<String> updateLigneCommandeClient(
            @ApiParam(name = "id", value = "id of ligneCommandeClient", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String ligneCommandeClientId,
            @Valid @RequestBody(required = true) LigneCommandeClientDto ligneCommandeClientDto) throws ResourceNotFoundException {

        ligneCommandeClientService.updateLigneCommandeClient(ligneCommandeClientDto);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }


    @DeleteMapping("/deleteLigneCmd/{ligneCommandeClientId}")
    @ApiOperation(value = "service to delete one LigneCommandeClient by Id.")
    public Map<String, Boolean> deleteLigneCommandeClient(
            @ApiParam(name = "id", value = "id of ligneCommandeClient", required = true)
            @PathVariable(value = "ligneCommandeClientId", required = true) @NotEmpty(message = "{http.error.0001}") String ligneCommandeClientId) throws ResourceNotFoundException {

        ligneCommandeClientService.deleteLigneCommandeClient(ligneCommandeClientId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }

}

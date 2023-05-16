package com.housservice.housstock.controller.planifierMachine;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import com.housservice.housstock.service.PlanifierMachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/planifiermachine")
public class planifierMachineController {
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final PlanifierMachineService planifierMachineService;
@Autowired
    public planifierMachineController(MessageHttpErrorProperties messageHttpErrorProperties, PlanifierMachineService planifierMachineService) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    this.planifierMachineService = planifierMachineService;
}
@GetMapping("/getMachine/{nomConducteur}")
    public List<String> getMachineNamesByConductor(@PathVariable String nomConducteur) {
        return planifierMachineService.getMachineByNomConducteur(nomConducteur);
    }
    @GetMapping("/getListOf")
    public ResponseEntity<Map<String, Object>> getOfByRefMachien(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "3") int size,
                                                                 @RequestParam String refMachine){
    return planifierMachineService.getOfByRefMachine(page,size,refMachine);
    }
    @GetMapping("/search")
    @ApiOperation(value = "service to filter Commande ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return planifierMachineService.search(textToFind, page, size,enVeille);

    }
    @PutMapping("/updateEtapes/{id}")
    public ResponseEntity<String> updatePlan(@ApiParam(name = "id", value = "id", required = true)
                                             @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id,
                                             @Valid @RequestBody PlanEtapesDto planEtapesDto
    ) throws ResourceNotFoundException {
        planifierMachineService.updateEtapes(id,planEtapesDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

    }
}

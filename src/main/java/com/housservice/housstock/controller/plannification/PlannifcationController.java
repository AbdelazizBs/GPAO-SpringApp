package com.housservice.housstock.controller.plannification;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.Plannification;
import com.housservice.housstock.service.MachineService;
import com.housservice.housstock.service.PlannificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/planificationOf")
@Api(tags = {"PlannificationManagement"})
@Validated
public class PlannifcationController {
    private final MachineService machineService;

    private final PlannificationService plannificationService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public PlannifcationController(MachineService machineService, MessageHttpErrorProperties messageHttpErrorProperties,PlannificationService plannificationService) {
        this.machineService = machineService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
        this.plannificationService = plannificationService;

    }


    @GetMapping("/getAllArticle")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllArticle(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size){
        return plannificationService.getAllArticle(page,size);

    }
    @GetMapping("/getAllMachine")
    public List<String> getAllMachine() throws ResourceNotFoundException {
        return machineService.getAllMachineDisponible();}


    @GetMapping("/operationType/{etat}")
    public String operationType(@ApiParam(name = "etat", value = "etat", required = true)
                                    @PathVariable(value = "etat", required = true) @NotEmpty(message = "{http.error.0001}") String etat) throws ResourceNotFoundException {
        return plannificationService.operationType(etat);

    }




}

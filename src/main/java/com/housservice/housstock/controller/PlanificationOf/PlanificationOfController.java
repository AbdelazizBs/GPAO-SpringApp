package com.housservice.housstock.controller.PlanificationOf;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import com.housservice.housstock.service.PlanificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/planificationOf")
@Api(tags = {"Planification Of   Management"})
public class PlanificationOfController {

    final
    PlanificationService planificationService;
    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public PlanificationOfController(PlanificationService planificationService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.planificationService = planificationService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }


    @PutMapping("/updatePlanfication")
    public ResponseEntity<String> updatePlanfication(
            @Valid @RequestBody PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException {
        planificationService.updatePlanfication(planificationOfDTO);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());


    }

    @GetMapping("/getPlanificationEtape/{idLc}")
    public List<PlanificationOf> getPlanificationEtape(
            @ApiParam(name = "idLc", value = "id of ligneCmd", required = true)
            @PathVariable(value = "idLc", required = true) @NotEmpty(message = "{http.error.0001}") String idLc) throws ResourceNotFoundException {
        return planificationService.getPlanificationEtape(idLc);
    }

    @GetMapping("/getPlanificationByIdLigneCmdAndNamEtape/{idLc}/{nameEtape}")
    public PlanificationOf getPlanificationByIdLigneCmdAndNamEtape(
            @ApiParam(name = "idLc", value = "id of ligneCmd", required = true)
            @PathVariable(value = "idLc", required = true) @NotEmpty(message = "{http.error.0001}") String idLc,
            @ApiParam(name = "nameEtape", value = "nomEtape", required = true)
            @PathVariable(value = "nameEtape", required = true) @NotEmpty(message = "{http.error.0001}") String nameEtape) throws ResourceNotFoundException {
        return planificationService.getPlanificationByIdLigneCmdAndNamEtape(idLc, nameEtape);
    }


}


package com.housservice.housstock.controller.PlanificationOf;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.PlanificationOf;
import com.housservice.housstock.model.dto.PlanificationOfDTO;
import com.housservice.housstock.service.PlanificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

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
            @RequestBody PlanificationOfDTO planificationOfDTO) throws ResourceNotFoundException {
        planificationService.updatePlanfication(planificationOfDTO);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());


    }



    @GetMapping("/getPlanificationByIdLigneCmd/{idLc}")
    public List<PlanificationOf> getPlanificationByIdLigneCmd(
            @ApiParam(name = "idLc", value = "id of ligneCmd", required = true)
            @PathVariable(value = "idLc", required = true) @NotEmpty(message = "{http.error.0001}") String idLc) throws ResourceNotFoundException {
        return planificationService.getPlanificationByIdLigneCmd(idLc);
    }

    @GetMapping("/getPlanificationByIdLigneCmdAndIndex/{idLc}/{index}")
    @ApiOperation(value = "service to get planification with index.")
    public ResponseEntity<Map<String, Object>> deleteCommandeClient(
            @ApiParam(name = "idLc", value="id of ligneCmd", required = true)
            @PathVariable(value = "idLc", required = true) @NotEmpty(message = "{http.error.0001}") String idLc,
            @ApiParam(name = "index", value="index of planification", required = true)
            @PathVariable(value = "index", required = true) @NotEmpty(message = "{http.error.0001}") int index) {

        return   planificationService.getPlanificationByIdLigneCmdAndIndex(idLc,index);

    }
}


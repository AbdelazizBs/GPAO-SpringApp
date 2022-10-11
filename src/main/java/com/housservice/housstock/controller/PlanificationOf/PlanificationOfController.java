package com.housservice.housstock.controller.PlanificationOf;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
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

@CrossOrigin
@RestController
@RequestMapping("/api/v1/planificationOf")
@Api(tags = {"Planification Of   Management"})
public class PlanificationOfController {

    final
    PlanificationService planificationService ;
    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public PlanificationOfController(PlanificationService planificationService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.planificationService = planificationService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }

    @GetMapping("/getPlanificationByIdLc/{idLc}")
    @ApiOperation(value = "service to get list of  planification  ligne cmd.")
    public List<PlanificationOfDTO> getPlanificationByIdLc(
            @ApiParam(name = "idLc", value="id of Ligne Cmd", required = true)
            @PathVariable(value = "idLc", required = true) @NotEmpty(message = "{http.error.0001}") String idLc)
            throws ResourceNotFoundException {
        return planificationService.getPlanificationByIdLc(idLc);
    }

    @PutMapping("/updatePlanfication/{id}")
    public ResponseEntity<String> updatePlanfication(
            @ApiParam(name = "id", value="id of planification", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String idPlanification,
            @Valid @RequestBody PlanificationOfDTO  planificationOfDTO) throws ResourceNotFoundException {
        planificationService.updatePlanfication(planificationOfDTO);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());


    }
	  
}


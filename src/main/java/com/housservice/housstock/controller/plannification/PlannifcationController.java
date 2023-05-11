package com.housservice.housstock.controller.plannification;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.CommandeClient;
import com.housservice.housstock.model.PlanEtapes;
import com.housservice.housstock.model.Plannification;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import com.housservice.housstock.service.MachineService;
import com.housservice.housstock.service.PlannificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
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
    @ApiOperation(value = "service to get get All Article")
    public ResponseEntity<Map<String, Object>> getAllArticle(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size){
        return plannificationService.getAllArticle(page,size);

    }

    @GetMapping("/getAllArticleLance")
    @ApiOperation(value = "service to get get All Article")
    public ResponseEntity<Map<String, Object>> getAllArticleLance(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "3") int size){
        return plannificationService.getAllArticleLance(page,size);

    }
    @GetMapping("/search")
    @ApiOperation(value = "service to filter Commande ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return plannificationService.search(textToFind, page, size,enVeille);

    }
    @GetMapping("/getAllMachine")
    public List<String> getAllMachine() throws ResourceNotFoundException {
        return machineService.getAllMachineDisponible();}


    @GetMapping("/operationType/{etat}")
    public String operationType(@ApiParam(name = "etat", value = "etat", required = true)
                                    @PathVariable(value = "etat", required = true) @NotEmpty(message = "{http.error.0001}") String etat) throws ResourceNotFoundException {
        return plannificationService.operationType(etat);

    }

    @GetMapping("/getConducteur/{refMachine}")
    public List<String>  getConducteur(@ApiParam(name = "refMachine", value = "refMachine", required = true)
                                @PathVariable(value = "refMachine", required = true) @NotEmpty(message = "{http.error.0001}") String refMachine ) throws ResourceNotFoundException {
         return plannificationService.getConducteur(refMachine);

    }

    @PutMapping("/updatePlanfication/{id}")
    public ResponseEntity<String> updatePlan(@ApiParam(name = "id", value = "id", required = true)
                                      @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id,
                                      @Valid @RequestBody Plannification plannification
                                      ) throws ResourceNotFoundException {
        plannificationService.updatePlanification(id,plannification);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

    }
    @PutMapping("/updateEtapes/{id}")
    public ResponseEntity<String> updatePlan(@ApiParam(name = "id", value = "id", required = true)
                                             @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id,
                                             @Valid @RequestBody PlanEtapesDto planEtapesDto
    ) throws ResourceNotFoundException {
        plannificationService.updateEtapes(id,planEtapesDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

    }

    @PutMapping("/updateEtape/{id}")
    public ResponseEntity<String> updateEtape(@ApiParam(name = "id", value = "id", required = true)
                                             @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id,
                                             @Valid @RequestBody Plannification plannification
    ) throws ResourceNotFoundException {
        plannificationService.updateEtape(id,plannification);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

    }


    @GetMapping("/indiceEtape/{id}")
    public int indiceEtape(@ApiParam(name = "id", value = "id", required = true)
                              @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id) throws ResourceNotFoundException {
        return plannificationService.indiceEtape(id);

    }
    @GetMapping("/getEtapes/{id}")
    public String[] getEtapes(@ApiParam(name = "id", value = "id", required = true)
                                @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id) throws ResourceNotFoundException {
        return plannificationService.getEtape(id);

    }
    @GetMapping("/getMonitrice")
    public List<String> getMonitrice( ) throws ResourceNotFoundException {
        return plannificationService.getMonitrice();

    }

    @GetMapping("/getEtapesValue/{id}")
    public PlanEtapes getEtapesValue(@ApiParam(name = "id", value = "id", required = true)
                                               @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id,@RequestParam String Nom ) throws ResourceNotFoundException {
        return plannificationService.getEtapesValue(id,Nom);

    }
        @GetMapping("/Ready/{id}")
    public void Terminer(@ApiParam(name = "id", value = "id", required = true)
                                     @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id
    )  {
        plannificationService.Terminer(id);
    }
    @GetMapping("/Suivi/{id}")
    public void Suivi(@ApiParam(name = "id", value = "id", required = true)
                         @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id
    )  {
        plannificationService.Suivi(id);
    }
}

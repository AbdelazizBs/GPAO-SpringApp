package com.housservice.housstock.controller.atelier;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;

import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.PlanEtapesDto;
import com.housservice.housstock.service.AtelierService;
import com.housservice.housstock.service.PlanEtapesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/Atelier")
public class AtelierController {
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    private final PlanEtapesService planEtapesService;

    private final AtelierService atelierService;
@Autowired
    public AtelierController(PlanEtapesService planEtapesService,MessageHttpErrorProperties messageHttpErrorProperties, AtelierService atelierService) {
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    this.atelierService = atelierService;
    this.planEtapesService = planEtapesService;

}

    @GetMapping("/getListOf")
    public ResponseEntity<Map<String, Object>> getOfByRefMachien(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "3") int size){
    return atelierService.getOfByRefMachine(page,size);
    }

    @GetMapping("/getPlantoday")
    public ResponseEntity<Map<String, Object>> getPlantoday(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "3") int size){
        return planEtapesService.getPlantoday(page,size);
    }
    @GetMapping("/search")
    @ApiOperation(value = "service to filter Commande ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return atelierService.search(textToFind, page, size,enVeille);

    }
    @GetMapping("/searchP")
    @ApiOperation(value = "service to filter Commande ")
    public ResponseEntity<Map<String, Object>> searchP(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return planEtapesService.searchP(textToFind, page, size,enVeille);

    }
    @GetMapping("/getMonitrice")
    public List<String> getMonitrice( ) throws ResourceNotFoundException {
        return atelierService.getMonitrice();

    }
    @PutMapping("/updateEtapes/{id}")
    public ResponseEntity<String> updateEtapes(@ApiParam(name = "id", value = "id", required = true)
                                             @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id,
                                             @Valid @RequestBody PlanEtapesDto planEtapesDto
    ) throws ResourceNotFoundException {
        atelierService.updateEtapes(id,planEtapesDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

    }
    @PutMapping("/terminer/{id}")
    public ResponseEntity<String> terminer(@ApiParam(name = "id", value = "id", required = true)
                                           @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id,
                                           @Valid @RequestBody PlanEtapesDto planEtapesDto
    ) throws ResourceNotFoundException {
        atelierService.terminer(id,planEtapesDto);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());

    }

    @GetMapping("/onSortActiveAtelier")
    @ApiOperation(value = "service to get get All active machine   sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> c(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "3") int size,
                                                                   @RequestParam(defaultValue = "field") String field,
                                                                   @RequestParam(defaultValue = "order") String order){
        return atelierService.onSortActiveAtelier(page,size,field,order);

    }

    @GetMapping("/onSortActivePlanEtapes")
    @ApiOperation(value = "service to get get All active machine   sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> sortplanEtapes(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int size,
                                                 @RequestParam(defaultValue = "field") String field,
                                                 @RequestParam(defaultValue = "order") String order){
        return planEtapesService.onSortActivePlanEtapes(page,size,field,order);

    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "service to delete one Machine by Id.")
    public Map < String, Boolean > deletemachine(
            @ApiParam(name = "id", value="id of machine", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        planEtapesService.delete(id);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

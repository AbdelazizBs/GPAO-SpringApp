package com.housservice.housstock.controller.machine;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.message.MessageHttpErrorProperties;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.service.MachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/machine")
@Api(tags = {"MachineManagement"})
@Validated
public class MachineController {
    private final MachineService machineService;

    private final MessageHttpErrorProperties messageHttpErrorProperties;

    public MachineController(MachineService machineService, MessageHttpErrorProperties messageHttpErrorProperties) {
        this.machineService = machineService;
        this.messageHttpErrorProperties = messageHttpErrorProperties;
    }

    @GetMapping("/getType")
    @ApiOperation(value = "service to get one Type")
    public List<String> getType() {
        return machineService.getType();
    }

    @GetMapping("/search")
    @ApiOperation(value = "service to filter machines ")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String textToFind,
                                                      @RequestParam boolean enVeille,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        return machineService.search(textToFind, page, size, enVeille);

    }

    @PutMapping(value = "/addMachine")
    public ResponseEntity<String> createNewMachine(
            @RequestParam("refMachine")
            @NotEmpty
            String refMachine,
            @RequestParam("nomConducteur")
            @NotEmpty
            String nomConducteur,
            @RequestParam("type")
            @NotEmpty
            String type,
            @RequestParam("nbConducteur")
            @NotEmpty
            int nbConducteur,
            @RequestParam("libelle")
            @NotEmpty
            String libelle,
            @RequestParam("dateMaintenance")
            @NotEmpty Date dateMaintenance


    ) throws ResourceNotFoundException {
        machineService.createNewMachine(refMachine,nomConducteur, libelle,  nbConducteur,dateMaintenance,type);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }

    @PutMapping("/updateMachine/{idMachine}")
    public ResponseEntity<String> updateMachine(
            @ApiParam(name = "idMachine", value = "id of machine", required = true)
            @PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}") String idMachine,
            @RequestParam("refMachine")
            @NotEmpty
            String refMachine,
            @RequestParam("nomConducteur")
            @NotEmpty
            String nomConducteur,
            @RequestParam("type")
            @NotEmpty
            String type,
            @RequestParam("nbConducteur")
            @NotEmpty
            int nbConducteur,
            @RequestParam("libelle")
            @NotEmpty
            String libelle,
            @RequestParam("dateMaintenance")
            @NotEmpty Date dateMaintenance


    ) throws ResourceNotFoundException {
        machineService.createNewMachine(refMachine,nomConducteur, libelle,  nbConducteur,dateMaintenance,type);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }
    @GetMapping("/onSortActiveMachine")
    @ApiOperation(value = "service to get get All active machine   sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> onSortActiveMachine(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "3") int size,
                                                                  @RequestParam(defaultValue = "field") String field,
                                                                  @RequestParam(defaultValue = "order") String order){
        return machineService.onSortActiveMachine(page,size,field,order);

    }

    @GetMapping("/onSortMachineNotActive")
    @ApiOperation(value = "service to get get All machine not active sorted  and ordered by  params")
    public ResponseEntity<Map<String, Object>> onSortMachineNotActive(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "3") int size,
                                                                     @RequestParam(defaultValue = "field") String field,
                                                                     @RequestParam(defaultValue = "order") String order){
        return machineService.onSortMachineNotActive(page,size,field,order);

    }


    @PutMapping("/miseEnVeille/{idMachine}")
    public ResponseEntity <String> miseEnVeille(
            @ApiParam(name = "idMachine", value="id of machine", required = true)
            @PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}")  String idMachine,
            @RequestBody(required = true) MachineDto machineDto) throws ResourceNotFoundException {

        machineService.miseEnVeille(idMachine);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }
    @DeleteMapping("/deleteMachine/{id}")
    @ApiOperation(value = "service to delete one Machine by Id.")
    public Map < String, Boolean > deletemachine(
            @ApiParam(name = "id", value="id of machine", required = true)
            @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String machineId)
            throws ResourceNotFoundException {
        Machine machine = machineService.getMachineById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(messageHttpErrorProperties.getError0002(), machineId)));

        machineService.deleteMachine(machine);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}

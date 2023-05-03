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

import javax.validation.Valid;
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
    @GetMapping("/getConducteur")
    @ApiOperation(value = "service to get one Type")
    public List<String> getConducteur() {
        return machineService.getConducteur();
    }


    @PutMapping("/addType/{type}")
    public ResponseEntity <String> addType(
            @ApiParam(name = "type", value="type", required = true)
            @PathVariable(value = "type", required = true) @NotEmpty(message = "{http.error.0001}")  String type) throws ResourceNotFoundException {
        machineService.addType(type);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());

    }
    @GetMapping("/getType")
    @ApiOperation(value = "service to get one Type")
    public List<String> getType() {
        return machineService.getType();
    }

    @GetMapping("/getEtat/{id}")
    @ApiOperation(value = "service to get one Etat")
    public String getEtat(@ApiParam(name = "id", value="id", required = true)
                                    @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String id) {
        return machineService.getEtat(id);
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
            @Valid @RequestBody MachineDto machineDto
    ) throws ResourceNotFoundException {
        machineService.createNewMachine(machineDto);

        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
    }
    @GetMapping("/getAllMachine")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllMachine(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size){
        return machineService.getAllMachine(page,size);

    }
    @GetMapping("/getAllMachineEnVielle")
    @ApiOperation(value = "service to get get All Personnel")
    public ResponseEntity<Map<String, Object>> getAllMachineEnVielle(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "3") int size){
        return machineService.getAllMachineEnVielle(page,size);

    }
    @PutMapping("/updateMachine/{idMachine}")
    public ResponseEntity<String> updateMachine(
            @ApiParam(name = "idMachine", value = "id of machine", required = true)
            @PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}") String idMachine,
            @Valid @RequestBody MachineDto machineDto
    ) throws ResourceNotFoundException {
        machineService.updateMachine(machineDto,idMachine);

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

    @DeleteMapping("/deleteSelectedMachine/{idMachinesSelected}")
    @ApiOperation(value = "service to delete many Personnel by Id.")
    public Map<String, Boolean> deletePersonnelSelected(
            @ApiParam(name = "idMachinesSelected", value = "ids of machine Selected", required = true) @PathVariable(value = "idMachinesSelected", required = true) @NotEmpty(message = "{http.error.0001}") List<String> idMachinesSelected) {
        machineService.deleteMachineSelected(idMachinesSelected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
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
    @PutMapping("/restaurer/{id}")
    public ResponseEntity <String> restaurer(
            @ApiParam(name = "id", value = "id", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id)
            throws ResourceNotFoundException {
        machineService.Restaurer(id);
        return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
    }

    @GetMapping("/reserve/{id}")
    @ApiOperation(value = "service to get one Etat")
    public void reverse(@ApiParam(name = "id", value = "id", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id) {
         machineService.reserve(id);
    }

    @GetMapping("/demarer/{id}")
    @ApiOperation(value = "service to get one Etat")
    public void Demarer(@ApiParam(name = "id", value = "id", required = true) @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String id) {
        machineService.Demarer(id);
    }
}

package com.housservice.housstock.controller.machine;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.service.MachineService;
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
@RequestMapping("/api/v1/machine")
@Api(tags = {"Machines Management"})
public class MachineController {
		
	private MachineService machineService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
		
	
	  @Autowired
	  public MachineController(MachineService machineService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.machineService = machineService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

	  @GetMapping("/getAllMachine")
	  public ResponseEntity<Map<String, Object>> getAllMachine(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
			
		return machineService.getAllMachine(page,size);
			
		}

		 @GetMapping("/getMachineEnVeille")
		 public ResponseEntity<Map<String, Object>> getMachineEnVeille(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
			 return machineService.getMachineEnVeille(page,size);
			 
		 }

	@GetMapping("/getMachineIdByName")
	@ApiOperation(value = "service to get machineId for selected name list.")
	public ResponseEntity<Map<String, Object>> getMachineIdByName(@RequestParam(value = "machinsName", required = true) String machinsName) throws ResourceNotFoundException {
		return machineService.getMachineIdByName(machinsName);
	}
		 @GetMapping("/getIdMachine/{nomEtape}")
		 public String  getIdMachine (
				 @PathVariable(value = "nomEtape", required = true) @NotEmpty(message = "{http.error.0001}") String nomEtape) throws ResourceNotFoundException {
			 return machineService.getIdMachine(nomEtape);

		 }

		 @GetMapping("/getAllMachinesByEtapes/{nomEtape}")
		 public List<Machine>  getAllMachinesByEtapes (
				 @PathVariable(value = "nomEtape", required = true) @NotEmpty(message = "{http.error.0001}") String nomEtape)
				 throws ResourceNotFoundException {
			 return machineService.getAllMachinesByEtapes(nomEtape);
		 }

		  @PutMapping("/addMachine")
		  public ResponseEntity<String> createMachine(@Valid @RequestBody MachineDto machineDto) throws ResourceNotFoundException {
			  machineService.createNewMachine(machineDto);
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
		
		  }
		  
		  @PutMapping("/updateMachine")
		  public ResponseEntity <String> updateMachine(@Valid @RequestBody MachineDto machineDto) throws ResourceNotFoundException {
			  machineService.updateMachine(machineDto);
		      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
		  }
		 
		  
		  @DeleteMapping("/machine/{id}")
		  @ApiOperation(value = "service to delete one Machine by Id.")
		  public ResponseEntity<Void>  deleteMachine(
				  @ApiParam(name = "id", value="id of machine", required = true)
				  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String machineId) {
		      machineService.deleteMachine(machineId);
			  return ResponseEntity.noContent().build();

		  }
		  
	  

	@PutMapping("/setMachineEnVeille/{idMachine}")
	public ResponseEntity <String> setMachineEnVeille(
			@ApiParam(name = "idMachine", value="id of machine", required = true)
			@PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}")  String idMachine) throws ResourceNotFoundException {

		machineService.setMachineEnVeille(idMachine);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}
	@PutMapping("/setEtatEnmarche/{idMachine}")
	public ResponseEntity <String> setEtatEnmarche(
			@ApiParam(name = "idMachine", value="id of machine", required = true)
			@PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}")  String idMachine) throws ResourceNotFoundException {

		machineService.setEtatEnmarche(idMachine);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}

	@PutMapping("/setEtatEnPause/{idMachine}")
	public ResponseEntity <String> setEtatEnPause(
			@ApiParam(name = "idMachine", value="id of machine", required = true)
			@PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}")  String idMachine) throws ResourceNotFoundException {

		machineService.setEtatEnPause(idMachine);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}	@PutMapping("/setEtatEnRepos/{idMachine}")
	public ResponseEntity <String> setEtatEnRepos(
			@ApiParam(name = "idMachine", value="id of machine", required = true)
			@PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}")  String idMachine) throws ResourceNotFoundException {

		machineService.setEtatEnRepos(idMachine);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}	@PutMapping("/setEtatEnMaintenance/{idMachine}")
	public ResponseEntity <String> setEtatEnMaintenance(
			@ApiParam(name = "idMachine", value="id of machine", required = true)
			@PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}")  String idMachine) throws ResourceNotFoundException {

		machineService.setEtatEnMaintenance(idMachine);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}	@PutMapping("/setEtatEnPanne/{idMachine}")
	public ResponseEntity <String> setEtatEnPanne(
			@ApiParam(name = "idMachine", value="id of machine", required = true)
			@PathVariable(value = "idMachine", required = true) @NotEmpty(message = "{http.error.0001}")  String idMachine) throws ResourceNotFoundException {

		machineService.setEtatEnPanne(idMachine);

		return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	}

}

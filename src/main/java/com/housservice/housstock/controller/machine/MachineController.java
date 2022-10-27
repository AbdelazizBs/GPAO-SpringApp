package com.housservice.housstock.controller.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.housservice.housstock.model.Machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.service.MachineService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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



		/*
		 * @GetMapping("/machine")
		 * public ResponseEntity <List<MachineDto>> getAllMachine() {
		 * 
		 * List<MachineDto> result = machineService.findMachineActif();
		 * 
		 * return new ResponseEntity<List<MachineDto>>(result , HttpStatus.OK );
		 * 
		 * }
		 */
	 
		 @GetMapping("/getMachineEnVeille")
		 public ResponseEntity<Map<String, Object>> getMachineEnVeille(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
			 return machineService.getMachineEnVeille(page,size);
			 
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
	
			/*
			 * @GetMapping("/machine/{id}")
			 * 
			 * @ApiOperation(value = "service to get one Machine by Id.") public
			 * ResponseEntity < MachineDto > getMachineById(@ApiParam(name = "id",
			 * value="id of machine", required = true)
			 * 
			 * @PathVariable(value = "id", required = true) @NotEmpty(message =
			 * "{http.error.0001}") String machineId) throws ResourceNotFoundException {
			 * MachineDto machine = machineService.getMachineById(machineId); if (machine ==
			 * null) { ResponseEntity.badRequest().body("not found"); } return
			 * ResponseEntity.ok().body(machine); }
			 */
	
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

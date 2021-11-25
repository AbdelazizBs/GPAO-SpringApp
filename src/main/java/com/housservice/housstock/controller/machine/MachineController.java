package com.housservice.housstock.controller.machine;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.MachineDto;
import com.housservice.housstock.model.Machine;
import com.housservice.housstock.service.MachineService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Machines Management"})
public class MachineController {
		
	private MachineService machineService;
	
	private final MessageHttpErrorProperties messageHttpErrorProperties;
		
	
	  @Autowired
	  public MachineController(MachineService machineService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.machineService = machineService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

	 
	 @GetMapping("/machine")
	 public List< MachineDto > getAllMachine() {
		 		
		 return machineService.findMachineActif();
		 
		 
	 
	 }
	 
	 @GetMapping("/machineEnVeille")
	 public List< MachineDto > getMachineEnVeille() {
		 return machineService.findMachineNotActif();
		 
	 }

	  @GetMapping("/machine/{id}")
	  @ApiOperation(value = "service to get one Machine by Id.")
	  public ResponseEntity < MachineDto > getMachineById(
			  @ApiParam(name = "id", value="id of machine", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String machineId)
	  throws ResourceNotFoundException {
		  MachineDto machine = machineService.getMachineById(machineId);
		  if (machine == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(machine);
	  }

	  @PutMapping("/machine")
	  public ResponseEntity<String> createMachine(@Valid @RequestBody MachineDto machineDto) {
		  
		  machineService.createNewMachine(machineDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
	  
	  
	 
	  @PutMapping("/machine/{id}")
	  public ResponseEntity <String> updateMachine(
			  @ApiParam(name = "id", value="id of machine", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String machineId,
	      @Valid @RequestBody(required = true) MachineDto machineDto) throws ResourceNotFoundException {
		  
		  machineService.updateMachine(machineDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }
	 
	  @DeleteMapping("/machine/{id}")
	  @ApiOperation(value = "service to delete one Machine by Id.")
	  public Map < String, Boolean > deletemachine(
			  @ApiParam(name = "id", value="id of machine", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String machineId) {

	      machineService.deleteMachine(machineId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}

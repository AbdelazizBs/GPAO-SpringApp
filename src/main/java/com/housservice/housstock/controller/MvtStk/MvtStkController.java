package com.housservice.housstock.controller.MvtStk;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.dto.MvtStkDto;
import com.housservice.housstock.service.MvtStkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"MvtStk Management"})
public class MvtStkController {
	
	private MvtStkService mvtStkService;
	  
    private final MessageHttpErrorProperties messageHttpErrorProperties;
    
    
    @Autowired
	  public MvtStkController(MvtStkService mvtStkService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.mvtStkService = mvtStkService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }

    @GetMapping("/mvtStk")
	 public List< MvtStkDto > getAllMvtStk() {
		 		
		 return mvtStkService.getAllMvtStk();
		 	 
	 }

    
    @GetMapping("/mvtStk/{id}")
	  @ApiOperation(value = "service to get one MvtStk by Id.")
	  public ResponseEntity < MvtStkDto > getMvtStkById(
			  @ApiParam(name = "id", value="id of mvtStk", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String mvtStkId)
	  throws ResourceNotFoundException {
    	MvtStkDto mvtStk = mvtStkService.getMvtStkById(mvtStkId);
		  if (mvtStk == null) {
			  ResponseEntity.badRequest();
		  }
	      return ResponseEntity.ok().body(mvtStk);
	  }
    
    @PutMapping("/mvtStk")
	  public ResponseEntity<String> createMvtStk(@Valid @RequestBody MvtStkDto mvtStkDto) {
		  
    	  mvtStkService.createNewMvtStk(mvtStkDto);
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0003());
	  }
    
    @PutMapping("/mvtStk/{id}")
	  public ResponseEntity <String> updateMvtStk(
			  @ApiParam(name = "id", value="id of mvtStk", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}")  String mvtStkId,
	          @Valid @RequestBody(required = true) MvtStkDto mvtStkDto) throws ResourceNotFoundException {
		  
    	  mvtStkService.updateMvtStk(mvtStkDto);
	      
	      return ResponseEntity.ok().body(messageHttpErrorProperties.getError0004());
	  }

    
	  @DeleteMapping("/mvtStk/{id}")
	  @ApiOperation(value = "service to delete one MvtStk by Id.")
	  public Map < String, Boolean > deleteMvtStk(
			  @ApiParam(name = "id", value="id of mvtStk", required = true)
			  @PathVariable(value = "id", required = true) @NotEmpty(message = "{http.error.0001}") String mvtStkId) {

		  mvtStkService.deleteMvtStk(mvtStkId);
	      Map < String, Boolean > response = new HashMap < > ();
	      response.put("deleted", Boolean.TRUE);
	      return response;
	  }

}

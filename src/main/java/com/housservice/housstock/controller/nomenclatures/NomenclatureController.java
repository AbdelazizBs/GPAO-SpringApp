package com.housservice.housstock.controller.nomenclatures;

import com.housservice.housstock.configuration.MessageHttpErrorProperties;
import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.model.Nomenclature;
import com.housservice.housstock.model.dto.NomenclatureDto;
import com.housservice.housstock.service.NomenclatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Nomenclatures Management"})
public class NomenclatureController {
	  
	  private NomenclatureService nomenclatureService;

	  private final MessageHttpErrorProperties messageHttpErrorProperties;
	  
	  @Autowired
	  public NomenclatureController(NomenclatureService nomenclatureService, MessageHttpErrorProperties messageHttpErrorProperties) {
		this.nomenclatureService = nomenclatureService;
		this.messageHttpErrorProperties = messageHttpErrorProperties;
	  }
	  
	  
	

}

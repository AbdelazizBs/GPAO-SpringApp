package com.housservice.housstock.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.housservice.housstock.message.ResponseMessage;
import com.housservice.housstock.model.dto.DocumentDto;
import com.housservice.housstock.service.api.DocumentService;
/**
 * 
 * @author houssem.khadraoui@gmail.com
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class DocumentsApi {

	@Autowired
	DocumentService documentService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("documents") DocumentDto documentDto) {			  

		String message = "";
		
		if (documentDto != null) {
		  try {			  
			  documentService.upLoadFile(documentDto);
		
//		    message = "Uploaded the file successfully: " + file.getOriginalFilename();
		    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		  } catch (Exception e) {
//		    message = "Could not upload the file: " + file.getOriginalFilename() + "!";
		    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		  }
		}
		
		message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
	

	 
}

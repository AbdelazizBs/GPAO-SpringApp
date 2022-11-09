package com.housservice.housstock.controller.excel;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.housservice.housstock.helper.ExcelHelper;
import com.housservice.housstock.message.ResponseMessage;

import com.housservice.housstock.service.ExcelService;


@CrossOrigin
@Controller
@RequestMapping("/api/v1/excel")
public class ExcelController {
	
	  @Autowired
	  ExcelService fileService;

	  @PostMapping("/upload")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		  
	    String message = "";

	    if (ExcelHelper.hasExcelFormat(file)) {
	    
	        fileService.save(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	       // return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	  	  
		  
	    }

	    message = "Please upload an excel file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	  }

}

package com.housservice.housstock.controller.excel;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestMapping;

import com.housservice.housstock.service.ExcelService;


@CrossOrigin
@Controller
@RequestMapping("/api/v1/excel")
public class ExcelController {
	
	  @Autowired
	  ExcelService fileService;

}
	 

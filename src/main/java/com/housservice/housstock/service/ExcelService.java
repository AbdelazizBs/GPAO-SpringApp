package com.housservice.housstock.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.housservice.housstock.helper.ExcelHelper;
import com.housservice.housstock.model.Tutorial;
import com.housservice.housstock.repository.TutorialRepository;

@Service
public class ExcelService {
	
	@Autowired
	  TutorialRepository repository;

	  public void save(MultipartFile file) throws IOException {
	 
	      List<Tutorial> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
	      repository.saveAll(tutorials);
	   
	  }


}

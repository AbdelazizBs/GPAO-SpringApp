package com.housservice.housstock.service.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import com.housservice.housstock.model.dto.DocumentDto;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	public final String CATEGORIE_DOCUMENT_CLIENT = "Client";

	@Override
	public List<String> upLoadFile(DocumentDto documentDto) throws IOException {
		// TODO Auto-generated method stub
		// if categorie Client, ressources, machine ....
		if (documentDto.getCategoroeDocument() == CATEGORIE_DOCUMENT_CLIENT) {
			uploadClientFromFile(documentDto);
		}
		return null;
	}

	private void uploadClientFromFile(DocumentDto documentDto) throws IOException {
		if (documentDto != null) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(documentDto.getDocument());
			 Workbook workbook = new XSSFWorkbook(inputStream);

		      Sheet sheet = workbook.getSheetAt(0);
		}
		
	}
	

}

package com.housservice.housstock.service.api;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.housservice.housstock.model.Client;

import com.housservice.housstock.model.dto.DocumentDto;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	public final String CATEGORIE_DOCUMENT_CLIENT = "Client";
	public final String CATEGORIE_DOCUMENT_PERSONNEL = "Personnel";
	

	@Override
	public List<String> upLoadFile(DocumentDto documentDto) throws IOException {
		
		/*
		 * if (documentDto.getCategorieDocument() == CATEGORIE_DOCUMENT_CLIENT) {
		 * uploadClientFromFile(documentDto); }
		 */
		
		switch (documentDto.getCategorieDocument()) {
		case "CATEGORIE_DOCUMENT_CLIENT" :
			uploadClientFromFile(documentDto);
			break;
			
		case "CATEGORIE_DOCUMENT_PERSONNEL" :
			uploadPersonnelFromFile(documentDto);
			break;
	
		default:
	          break;
		}
		
		return null;
	}

	
	private List<Client> uploadClientFromFile(DocumentDto documentDto) throws IOException {
		if (documentDto != null) {
			
			ByteArrayInputStream inputStream = new ByteArrayInputStream(documentDto.getDocument());
			 Workbook workbook = new XSSFWorkbook(inputStream);

		      Sheet sheet = workbook.getSheetAt(0);
		      
		      // here henda
		      
		      Iterator<Row> rows = sheet.iterator();
		      
		      List<Client> clients = new ArrayList<Client>();
		      
		      for (int i = 12; rows.hasNext(); i++) {
					
				}
		      
		      int rowNumber = 0;
		      while (rows.hasNext()) {
		        Row currentRow = rows.next();

		        // skip header
		        if (rowNumber == 13) {
		          rowNumber++;
		          continue;
		        }
		      
		        Iterator<Cell> cellsInRow = currentRow.iterator();

		        Client client = new Client();

		        int cellIdx = 0;
		        while (cellsInRow.hasNext()) {
		          Cell raisonSocialCell = currentRow.getCell(0);
		          Cell telephoneCell = currentRow.getCell(1);
		      
		          cellIdx++;
		        }

		        clients.add(client);
		      }

		      workbook.close();

		      return clients;
		      
		     		      
		      
		      // here henda
		      	      
		
		}
		return null;
		
	}
	
	
	private void uploadPersonnelFromFile(DocumentDto documentDto) throws IOException {
		if (documentDto != null) {
			
			ByteArrayInputStream inputStream = new ByteArrayInputStream(documentDto.getDocument());
			 Workbook workbook = new XSSFWorkbook(inputStream);

		      Sheet sheet = workbook.getSheetAt(0);
		}
		
	}
	

	


}

package com.housservice.housstock.helper;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Personnel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelHelper {


	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;

	}





	public static List<Personnel> excelToPersonnels(InputStream is) throws IOException
	{

		Workbook workbook = new XSSFWorkbook(is);

		Sheet sheet = workbook.getSheetAt(0);

		List<Personnel> personnels = new ArrayList<>();

		int rowNumber = 0;

		for (int i = 0 ; i <= sheet.getLastRowNum(); i++)
		{

			Row currentRow = sheet.getRow(i);



			if (rowNumber == 0)
			{
				rowNumber++;
				continue;
			}

			Iterator<Cell> cellsInRow = currentRow.iterator();

			Personnel personnel = new Personnel();

			int cellIdx = 0;

			while (cellsInRow.hasNext())
			{

				Cell currentCell =  cellsInRow.next();
//				if (currentCell.getCellType() == CellType.BLANK)
//				{
//					// get this cell value and log it to console
//					System.out.println(currentCell.getStringCellValue());
//					continue;
//				}

				switch (cellIdx)
				{

					case 0:

						personnel.setMatricule(new DataFormatter().formatCellValue(currentCell.getRow().getCell(0)));

						break;

					case 1:
						personnel.setCin(new DataFormatter().formatCellValue(currentCell.getRow().getCell(1)));
						break;

					case 2:
						personnel.setNom(currentCell.getStringCellValue());
						break;

					case 3:
						personnel.setPrenom(currentCell.getStringCellValue());
						break;
					case 4:
						personnel.setAdresse(currentCell.getStringCellValue());
						break;

					case 5:
						personnel.setDateNaissance(currentCell.getDateCellValue());
						break;

					case 6:
						personnel.setSexe(currentCell.getStringCellValue());
						break;

					case 7:
						personnel.setPhone(new DataFormatter().formatCellValue(currentCell.getRow().getCell(7)));
						break;
					case 8:
						personnel.setDateEmbauche(currentCell.getDateCellValue());
						break;
					case 9:
						personnel.setRib(new DataFormatter().formatCellValue(currentCell.getRow().getCell(9)));
						break;

					case 10:
						personnel.setPoste(currentCell.getStringCellValue());
						break;

					case 11:
						personnel.setEchelon(currentCell.getStringCellValue());
						break;
					case 12:
						personnel.setCategorie(currentCell.getStringCellValue());
						break;

					default:
						break;

				}

				cellIdx++;
			}

			personnels.add(personnel);

		}

		workbook.close();
		return personnels;

	}


	public static List<Client> excelToClients(InputStream is) throws IOException
	{

		Workbook workbook = new XSSFWorkbook(is);

		Sheet sheet = workbook.getSheetAt(0);

		List<Client> clients = new ArrayList<>();

		int rowNumber = 0;

		for (int i = 0 ; i <= sheet.getLastRowNum(); i++)
		{

			Row currentRow = sheet.getRow(i);

			// skip header

			if (rowNumber == 0)
			{
				rowNumber++;
				continue;
			}

			Iterator<Cell> cellsInRow = currentRow.iterator();

			Client client = new Client();

			int cellIdx = 0;

			while (cellsInRow.hasNext())
			{
				Cell currentCell =  cellsInRow.next();

				switch (cellIdx)
				{

					case 0:
						client.setRaisonSocial(currentCell.getStringCellValue());
						break;

					case 1:
						client.setRegime(currentCell.getStringCellValue());
						break;

					case 2:
						client.setSecteurActivite(currentCell.getStringCellValue());
						break;

					case 3:
						client.setBrancheActivite(currentCell.getStringCellValue());
						break;
					case 4:
						client.setAdresseFacturation(currentCell.getStringCellValue());
						break;

					case 5:
						client.setAdresseLivraison(currentCell.getStringCellValue());
						break;

					case 6:
						client.setIncoterm(new DataFormatter().formatCellValue(currentCell.getRow().getCell(6)));
						break;

					case 7:
						client.setEcheance(new DataFormatter().formatCellValue(currentCell.getRow().getCell(7)));
						break;
					case 8:
						client.setModePaiement(currentCell.getStringCellValue());
						break;
					case 9:
						client.setNomBanque(currentCell.getStringCellValue());
						break;

					case 10:
						client.setAdresseBanque(currentCell.getStringCellValue());
						break;

					case 11:
						client.setRib(new DataFormatter().formatCellValue(currentCell.getRow().getCell(11)));
						break;
					case 12:
						client.setSwift(new DataFormatter().formatCellValue(currentCell.getRow().getCell(12)));
						break;

					default:
						break;

				}

				cellIdx++;
			}

			clients.add(client);

		}

		workbook.close();
		return clients;

	}
	public static List<Client> excelFormatSageToClient(InputStream is) throws IOException
	{

		Workbook workbook = new XSSFWorkbook(is);

		Sheet sheet = workbook.getSheetAt(0);

		List<Client> clients = new ArrayList<>();

//		int i = sheet.getFirstRowNum()

		for ( int i = 12 ; i <= sheet.getLastRowNum(); i++)
		{

			Row currentRow = sheet.getRow(i);


			Iterator<Cell> cellsInRow = currentRow.iterator();

			Client client = new Client();

			int cellIdx = 0;

			while (cellsInRow.hasNext())
			{

				Cell currentCell =  cellsInRow.next();

				switch (cellIdx)
				{

					case 0:
						client.setRefClientIris(new DataFormatter().formatCellValue(currentCell.getRow().getCell(0)));
						break;


					case 1:
						client.setRaisonSocial(currentCell.getRow().getCell(3).getStringCellValue());
						break;

					case 2:
						client.setPhone(new DataFormatter().formatCellValue(currentCell.getRow().getCell(12)));
						break;

					case 3:
						client.setTelecopie(new DataFormatter().formatCellValue(currentCell.getRow().getCell(15)));
						break;



					default:
						break;

				}

				cellIdx++;
			}

			clients.add(client);

		}

		workbook.close();
		return clients;

	}

}


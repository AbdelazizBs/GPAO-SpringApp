package com.housservice.housstock.helper;

import com.housservice.housstock.model.Client;
import com.housservice.housstock.model.Fournisseur;
import com.housservice.housstock.model.Personnel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelHelper {


	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	public static boolean 	hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;

	}


	 // read excel file and return a list of personnel
	// verify if the table is clean and the cells are string and the format date .


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

						personnel.setMatricule(currentCell.getStringCellValue());
						break;

					case 1:
						personnel.setCin(currentCell.getStringCellValue());

						break;

					case 2:
						personnel.setNom(currentCell.getStringCellValue());

						break;

					case 3:
						personnel.setAdresse(currentCell.getStringCellValue());
						break;

					case 4:
						personnel.setDateNaissance(currentCell.getDateCellValue());
						break;

					case 5:
						personnel.setSexe(currentCell.getStringCellValue());

						break;

					case 6:
						personnel.setPhone(currentCell.getStringCellValue());

						break;
					case 7:
						personnel.setDateEmbauche(currentCell.getDateCellValue());
						break;
					case 8:
						personnel.setRib(currentCell.getStringCellValue());
						break;

					case 9:
						personnel.setPoste(currentCell.getStringCellValue());
						break;

					case 10:
						personnel.setEchelon(currentCell.getStringCellValue());
						break;
					case 11:
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

	
	
	public static List<Personnel> excelFormatSageToPersonnel(InputStream is) throws IOException
	{

		Workbook workbook = new XSSFWorkbook(is);

		Sheet sheet = workbook.getSheetAt(0);

		List<Personnel> personnels = new ArrayList<>();

//		int i = sheet.getFirstRowNum()

		for ( int i = 12 ; i <= sheet.getLastRowNum(); i++)
		{

			Row currentRow = sheet.getRow(i);


			Iterator<Cell> cellsInRow = currentRow.iterator();

			Personnel personnel = new Personnel();

			int cellIdx = 0;

			while (cellsInRow.hasNext())
			{

				Cell currentCell =  cellsInRow.next();

				switch (cellIdx)
				{

					case 0:
						personnel.setMatricule(currentCell.getRow().getCell(0).getStringCellValue());
						break;


					case 1:
						personnel.setNom(currentCell.getRow().getCell(3).getStringCellValue());
						break;

					case 2:
						personnel.setPhone(currentCell.getRow().getCell(12).getStringCellValue());
						break;

					case 3:
						personnel.setPoste(currentCell.getRow().getCell(15).getStringCellValue());
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

		for (int i = 0; i <= sheet.getLastRowNum(); i++)
		{Row currentRow = sheet.getRow(i);

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
						client.setRefClientIris(currentCell.getStringCellValue());
						break;

					case 1:
						client.setRaisonSocial(currentCell.getStringCellValue());
						break;

					case 2:
						client.setAdresse(currentCell.getStringCellValue());
						break;

					case 3:
						client.setCodePostal(currentCell.getStringCellValue());
						break;
					case 4:
						client.setVille(currentCell.getStringCellValue());
						client.setRegion(currentCell.getStringCellValue());
						break;

					case 5:					
						client.setPays(currentCell.getStringCellValue());
						if ((currentCell.getStringCellValue()).equals("Tunisie")||(currentCell.getStringCellValue()).equals("TUNISIE")||(currentCell.getStringCellValue()).equals(""))
						{
							client.setStatut("Résident");
						}
						else
						{
							client.setStatut("Non_résident");
						}

						break;
					case 6:
						client.setPhone(currentCell.getStringCellValue());
						break;
					case 7:
						client.setEmail(currentCell.getStringCellValue());
						
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
						client.setRefClientIris(currentCell.getRow().getCell(0).getStringCellValue());
						break;


					case 1:
						client.setRaisonSocial(currentCell.getRow().getCell(3).getStringCellValue());
						break;

					case 2:
						client.setPhone(currentCell.getRow().getCell(12).getStringCellValue());
						break;

					case 3:
						client.setTelecopie(currentCell.getRow().getCell(15).getStringCellValue());
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

	
	
	public static List<Fournisseur> excelToFournisseurs(InputStream is) throws IOException
	{

		Workbook workbook = new XSSFWorkbook(is);

		Sheet sheet = workbook.getSheetAt(0);

		List<Fournisseur> fournisseurs = new ArrayList<>();

		int rowNumber = 0;

		for (int i = 0; i <= sheet.getLastRowNum(); i++)
			
		{
			Row currentRow = sheet.getRow(i);

			// skip header

			if (rowNumber == 0)
			{
				rowNumber++;
				continue;
			}

			Iterator<Cell> cellsInRow = currentRow.iterator();

			Fournisseur fournisseur = new Fournisseur();

			int cellIdx = 0;

			while (cellsInRow.hasNext())
			{
				Cell currentCell =  cellsInRow.next();

				switch (cellIdx)
				{

					case 0:
						fournisseur.setRefFrsIris(currentCell.getStringCellValue());
						break;

					case 1:
						fournisseur.setIntitule(currentCell.getStringCellValue());
						break;

					case 2:
						fournisseur.setAdresse(currentCell.getStringCellValue());
						break;

					case 3:
						fournisseur.setVille(currentCell.getStringCellValue());
						break;
					case 4:
						fournisseur.setPays(currentCell.getStringCellValue());
						if ((currentCell.getStringCellValue()).equals("Tunisie")||(currentCell.getStringCellValue()).equals("TUNISIE")||(currentCell.getStringCellValue()).equals(""))
						{
							fournisseur.setStatut("Local");
						}
						else
						{
							fournisseur.setStatut("Export");
						}
						break;
					case 5:
						fournisseur.setTelephone(currentCell.getStringCellValue());
						break;

					default:
						break;
						
						

				}

				cellIdx++;
			}

			fournisseurs.add(fournisseur);

		}

		workbook.close();
		return fournisseurs;

	}
	
	
	public static List<Fournisseur> excelFormatSageToFournisseur(InputStream is) throws IOException
	{

		Workbook workbook = new XSSFWorkbook(is);

		Sheet sheet = workbook.getSheetAt(0);

		List<Fournisseur> fournisseurs = new ArrayList<>();



		for ( int i = 12 ; i <= sheet.getLastRowNum(); i++)
		{

			Row currentRow = sheet.getRow(i);

			Iterator<Cell> cellsInRow = currentRow.iterator();

			Fournisseur fournisseur = new Fournisseur();

			int cellIdx = 0;

			while (cellsInRow.hasNext())
			{

				Cell currentCell =  cellsInRow.next();

				switch (cellIdx)
				{

					case 0:
						fournisseur.setRefFrsIris(currentCell.getRow().getCell(0).getStringCellValue());
						break;


					case 1:
						fournisseur.setIntitule(currentCell.getRow().getCell(3).getStringCellValue());
						break;

					case 2:
						fournisseur.setTelephone(currentCell.getRow().getCell(12).getStringCellValue());
						break;

					case 3:
						fournisseur.setTelecopie(currentCell.getRow().getCell(15).getStringCellValue());
						break;


					default:
						break;

				}

				cellIdx++;
			}

			fournisseurs.add(fournisseur);

		}

		workbook.close();
		return fournisseurs;

	}


}


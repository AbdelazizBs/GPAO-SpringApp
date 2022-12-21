package com.housservice.housstock.controller.excel;

import com.housservice.housstock.exception.ResourceNotFoundException;
import com.housservice.housstock.helper.ExcelHelper;
import com.housservice.housstock.message.ResponseMessage;
import com.housservice.housstock.service.ExcelService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@CrossOrigin
@RestController
@RequestMapping("/api/v1/excel")
@Api(tags = {"Excel Management"})
public class ExcelController {

	final
	ExcelService fileService;

	public ExcelController(ExcelService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/uploadPersonnelFile")
	public ResponseEntity<ResponseMessage> uploadPersonnelFile(@RequestParam("file") MultipartFile file) throws IOException, ResourceNotFoundException {
		String message = "";


		if (ExcelHelper.hasExcelFormat(file))
		{

			fileService.savePersonnel(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

		}

		message = "Please upload an excel file!";

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));

	}

	@PostMapping("/uploadPersonnelFileSage")
	public ResponseEntity<ResponseMessage> uploadPersonnelFileSage(@RequestParam("file") MultipartFile file) throws IOException, ResourceNotFoundException {
		String message = "";


		if (ExcelHelper.hasExcelFormat(file))
		{

			fileService.savePersonnelFromSage(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

		}

		message = "Please upload an excel file!";

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));

	}

	@PostMapping("/uploadClientFile")
	public ResponseEntity<ResponseMessage> uploadClientFile(@RequestParam("file") MultipartFile file) throws IOException, ResourceNotFoundException {
		String message = "";

		if (ExcelHelper.hasExcelFormat(file))
		{

			fileService.saveClient(file);


		}
		message = "Uploaded the file successfully: " + file.getOriginalFilename();

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

	}
	@PostMapping("/uploadClientFromSageFile")
	public ResponseEntity<ResponseMessage> excelFormatSageToClient(@RequestParam("file") MultipartFile file) throws IOException, ResourceNotFoundException {
		String message = "";

		if (ExcelHelper.hasExcelFormat(file))
		{

			fileService.excelFormatSageToClient(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

		}

		message = "Please upload an excel file!";

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));

	}

	// get xlsx static file from resources folder

	@GetMapping("/downloadPersonnelStandardFile")
	public ResponseEntity<byte[]> downloadPersonnelStandardFile() throws IOException {
		byte[] data = fileService.getPersonnelFileFromResourceAsStream();
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=PersonnelFormatStandardExp.xlsx")
				.body(data);
	}
	@GetMapping("/downloadClientStandardFile")
	public ResponseEntity<byte[]> downloadClientStandardFile() throws IOException {
		byte[] data = fileService.getClientFileFromResourceAsStream();
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=ClientFormatStandardExp.xlsx")
				.body(data);
	}



}

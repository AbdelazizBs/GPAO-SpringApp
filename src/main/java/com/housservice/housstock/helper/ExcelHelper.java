package com.housservice.housstock.helper;


import org.springframework.web.multipart.MultipartFile;


public class ExcelHelper {
	
	 public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String SHEET = "Tutorials";

	  public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

  
	  
}

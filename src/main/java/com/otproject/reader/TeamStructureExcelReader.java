package com.otproject.reader;

import org.springframework.web.multipart.MultipartFile;

public class TeamStructureExcelReader {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	 static String[] HEADERs = { "No", "Name", "StaffID", "Team","Project","Position" };
	 static String SHEET = "Sheet1";
	
	 public static boolean hasExcelFormat(MultipartFile file) {
		    if (!TYPE.equals(file.getContentType())) {
		      return false;
		    }
		    return true;
		  }
}

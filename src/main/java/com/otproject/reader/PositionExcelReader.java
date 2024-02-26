package com.otproject.reader;

import org.springframework.web.multipart.MultipartFile;

public class PositionExcelReader {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "No", "Name", "StaffID", "Position","Basic Pay" };
	  static String SHEET = "Sheet1";
	  public static boolean hasExcelFormat(MultipartFile file) {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	    return true;
	  }
}

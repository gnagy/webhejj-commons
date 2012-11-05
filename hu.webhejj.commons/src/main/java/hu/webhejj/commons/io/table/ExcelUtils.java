/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Utility methods for working with Excel
 */
public class ExcelUtils {

	public static void ensureSheet(Workbook workbook, int index) {
		for(int i = workbook.getNumberOfSheets(); i <= index; i++) {
			workbook.createSheet();
		}		
	}
	
	public static Workbook openWorkbook(File file) throws FileNotFoundException, IOException {
		Workbook workbook = null;
		if(file == null) {
			workbook = new HSSFWorkbook();
		} else {
			try {
				workbook = new HSSFWorkbook(new FileInputStream(file));
			} catch (OfficeXmlFileException e) {
				workbook = new XSSFWorkbook(new FileInputStream(file));
			}
		}
		return workbook;
	}
}

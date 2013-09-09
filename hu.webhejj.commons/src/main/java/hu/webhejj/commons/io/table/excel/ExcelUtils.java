/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table.excel;

import hu.webhejj.commons.io.RuntimeIOException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

	public static Workbook openWorkbook(File file) {
		try {
			return openWorkbook(new FileInputStream(file));
		} catch (IOException e) {
			throw new RuntimeIOException("Error while reading excel file " + file, e);
		}
	}

	
	public static Workbook openWorkbook(InputStream is) {
		Workbook workbook = null;
		if(is == null) {
			workbook = new HSSFWorkbook();
		} else {
			BufferedInputStream bis = new BufferedInputStream(is);
			// read OLE magic
			bis.mark(8);
			try {
				if(        bis.read() == 0xD0
						&& bis.read() == 0xCF
						&& bis.read() == 0x11
						&& bis.read() == 0xE0
						&& bis.read() == 0xA1
						&& bis.read() == 0xB1
						&& bis.read() == 0x1A
						&& bis.read() == 0xE1) {
					bis.reset();
					workbook = new HSSFWorkbook(bis);
				} else {
					bis.reset();
					workbook = new XSSFWorkbook(bis);
				}
			} catch (IOException e) {
				throw new RuntimeIOException("Error while reading excel input stream", e);
			}
		}
		return workbook;
	}
}

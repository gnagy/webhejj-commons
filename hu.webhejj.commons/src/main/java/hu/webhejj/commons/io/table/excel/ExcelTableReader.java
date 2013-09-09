/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table.excel;

import hu.webhejj.commons.io.table.TableReader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

public class ExcelTableReader implements TableReader {
	
	protected final Workbook workbook;
	protected final ExcelRowValueConverter rowValueConverter;

	public ExcelTableReader(Workbook workbook) {
		this.workbook = workbook;
		rowValueConverter = new ExcelRowValueConverter(workbook.getCreationHelper().createFormulaEvaluator());
	}
	
	public ExcelTableReader(File file) {
		this(ExcelUtils.openWorkbook(file));
	}
	
	public ExcelTableReader(InputStream is) {
		this(ExcelUtils.openWorkbook(is));
	}

	@Override
	public Sheet getSheet(int index) {
		return new ExcelSheet(index, this);
	}

	@Override
	public Sheet getSheet(String name) {
		return getSheet(workbook.getSheetIndex(name));
	}
	
	@Override
	public List<Sheet> getSheets() {
		int sheetCount = workbook.getNumberOfSheets();
		List<Sheet> sheets = new ArrayList<TableReader.Sheet>(sheetCount);
		for(int i = 0; i < sheetCount; i++) {
			sheets.add(getSheet(i));
		}
		return sheets;
	}
}

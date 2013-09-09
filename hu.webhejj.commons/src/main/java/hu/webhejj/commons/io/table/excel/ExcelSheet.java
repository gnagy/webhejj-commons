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
import hu.webhejj.commons.io.table.TableReader.Row;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

public class ExcelSheet implements TableReader.Sheet {

	private final int index;
	private final ExcelTableReader excelTableReader;

	public ExcelSheet(int index, ExcelTableReader excelTableReader) {
		this.index = index;
		this.excelTableReader = excelTableReader;
	}
	
	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getName() {
		return getSheet().getSheetName();
	}
	
	@Override
	public Row getRow(int index) {
		return new ExcelRow(getSheet().getRow(index), excelTableReader.rowValueConverter);
	}

	@Override
	public List<Row> getRows() {
		return new AbstractList<TableReader.Row>() {

			@Override
			public Row get(int index) {
				return getRow(index);
			}

			@Override
			public int size() {
				Sheet sheet = getSheet();
				int i = sheet.getLastRowNum();
				if(i == 0) {
					return sheet.getPhysicalNumberOfRows() == 0 ? 0 : 1;
				}
				return i + 1;
			}
		};
	}
	
	@Override
	public Iterator<Row> iterator() {
		return getRows().iterator();
	}

	private Sheet getSheet() {
		return excelTableReader.workbook.getSheetAt(index);
	}
}
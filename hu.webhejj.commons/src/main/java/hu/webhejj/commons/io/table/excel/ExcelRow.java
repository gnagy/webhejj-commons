/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table.excel;

import hu.webhejj.commons.io.table.AbstractTableRow;

public class ExcelRow extends AbstractTableRow {

	private final org.apache.poi.ss.usermodel.Row row;
	private final ExcelRowValueConverter rowValueConverter;
	

	public ExcelRow(org.apache.poi.ss.usermodel.Row row, ExcelRowValueConverter rowValueConverter) {
		this.row = row;
		this.rowValueConverter = rowValueConverter;
	}

	@Override
	public int size() {
		if(row == null) {
			return 0;
		}
		return row.getLastCellNum() + 1;
	}

	@Override
	public <T> T getValue(int column, Class<T> valueType) {
		return rowValueConverter.getValue(row, column, valueType);
	}
}
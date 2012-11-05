/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table;

import hu.webhejj.commons.CompareUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.Format;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * A TableReader for reading from Excel files
 * 
 * @author Gergely Nagy <greg@webhejj.hu>
 *
 */
public class ExcelTableReader {
	
	/** A row in the table with data accessor methods */
	public class Row {
		
		private org.apache.poi.ss.usermodel.Row row;

		public Row(org.apache.poi.ss.usermodel.Row row) {
			this.row = row;
		}

		public int getColumnCount() {
			if(row == null) {
				return 0;
			}
			return row.getLastCellNum() + 1;
		}
		
		public <T> T getValue(String column, Class<T> valueType) {
			return getValue(toColumnIndex(column), valueType);
		}
		public <T> T getValue(String column, Class<T> valueType, T defaultValue) {
			return getValue(toColumnIndex(column), valueType, defaultValue);
		}
		
		public <T> T getValue(int column, Class<T> valueType) {
			return ExcelTableReader.this.getValue(row, column, valueType);
		}
		public <T> T getValue(int column, Class<T> valueType, T defaultValue) {
			T value = ExcelTableReader.this.getValue(row, column, valueType);
			return CompareUtils.isEmpty(value) ? defaultValue : value;
		}
		
		
		public <T> T[] getValues(Class<T> valueType) {
			int columnCount = Math.max(0, getColumnCount() - 1);
			@SuppressWarnings("unchecked")
			T[] values = (T[]) Array.newInstance(valueType, columnCount);
			for(int i = 0; i < values.length; i++) {
				values[i] = getValue(i, valueType);
			}
			return values;
		}
		
		public boolean matches(Pattern pattern) {
			for(int i = 0; i <= getColumnCount(); i++) {
				String value = getValue(i, String.class);
				if(value != null && pattern.matcher(value).matches()) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		public String toString() {
			return Arrays.asList(getValues(String.class)).toString();
		}
	}

	private Workbook workbook;
	private FormulaEvaluator evaluator;
	private Sheet sheet;
	private DataFormatter formatter;
	
	public ExcelTableReader(File file) throws IOException {
		
		workbook = ExcelUtils.openWorkbook(file);
		selectSheet(0);
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		formatter = new DataFormatter();
	}
	
	public void selectSheet(int index) {
		ExcelUtils.ensureSheet(workbook, index);
		sheet = workbook.getSheetAt(index);
		workbook.setActiveSheet(index);
	}
	

	
	public int getRowCount() {
		int i = sheet.getLastRowNum();
		if(i == 0) {
			return sheet.getPhysicalNumberOfRows() == 0 ? 0 : 1;
		}
		return i + 1;
	}
	
	protected <T> T getValue(org.apache.poi.ss.usermodel.Row r, int column, Class<T> valueType) {

		if(r == null) {
			return null;
		}
		Cell cell = r.getCell(column);
		if(cell == null) {
			return null;
		}
		
		CellValue cellValue = null;
		try {
			cellValue = evaluator.evaluate(cell);
		} catch (RuntimeException e) {
			if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				switch (cell.getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC:
					cellValue = new CellValue(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					cellValue = new CellValue(cell.getStringCellValue());
					break;
				default:
					System.err.format("  Cell[%d,%d] unknown cached formula type %s\n", r.getRowNum(), column, cell.getCachedFormulaResultType());
				}
			}
		}
		if(cellValue == null) {
			return null;
		}
		
		switch(cellValue.getCellType()) {
		case Cell.CELL_TYPE_BLANK: return null;
		case Cell.CELL_TYPE_BOOLEAN:
			if(String.class.isAssignableFrom(valueType)) {
				return (T) Boolean.toString(cellValue.getBooleanValue());
			
			} else if(Integer.class.isAssignableFrom(valueType)) {
				return (T) Integer.valueOf(cellValue.getBooleanValue() ? 1 : 0);
			
			} else if(Long.class.isAssignableFrom(valueType)) {
				return (T) Long.valueOf(cellValue.getBooleanValue() ? 1L : 0L);
			
			} else {
				throw new ClassCastException("Can't convert " + cellValue.getBooleanValue() + " to " + valueType.getName());
			}
		case Cell.CELL_TYPE_STRING:
			String stringValue = cellValue.getStringValue();
			if(CompareUtils.isEmpty(stringValue)) {
				return null;
			}
			if("null".equals(stringValue)) {
				return null;
			}
			if(String.class.isAssignableFrom(valueType)) {
				return (T) stringValue;
			
			} else if(Integer.class.isAssignableFrom(valueType)) {
				return (T) Integer.valueOf(stringValue);
			
			} else if(Long.class.isAssignableFrom(valueType)) {
				return (T) Long.valueOf(stringValue);
			
			} else if(valueType.isEnum()) {
				return (T) Enum.valueOf((Class<? extends Enum>) valueType, stringValue);

			} else if(BigDecimal.class.isAssignableFrom(valueType)) {
				return (T) (CompareUtils.isEmpty(cellValue.getStringValue()) ? null : new BigDecimal(cellValue.getStringValue()));
			
			} else {
				throw new ClassCastException("Can't convert " + cellValue.getStringValue() + " to " + valueType.getName());
			}
		case Cell.CELL_TYPE_NUMERIC:
			if(String.class.isAssignableFrom(valueType)) {
				Format format = formatter.createFormat(cell);
				if(format == null) {
					// TODO: do this without creating a BigDecimal each time
					return (T) new BigDecimal(cellValue.getNumberValue()).toString();
				} else {
					return (T) format.format(cellValue.getNumberValue());
				}
			
			} else if(Integer.class.isAssignableFrom(valueType)) {
				return (T) Integer.valueOf((int) cellValue.getNumberValue());
			
			} else if(Long.class.isAssignableFrom(valueType)) {
				return (T) Long.valueOf((int) cellValue.getNumberValue());
			
			} else {
				throw new ClassCastException("Can't convert " + cellValue.getNumberValue() + " to " + valueType.getName());
			}
		case Cell.CELL_TYPE_ERROR:
			 FormulaError error = FormulaError.forInt(cell.getErrorCellValue());
			 if(FormulaError.NA.equals(error)) {
				 return null;
			 } else {
				 // System.err.format("  Cell[%d,%d] error code %s\n", r.getRowNum(), column, error);
				 return null;
				 // throw new RuntimeException(String.format("Cell[%d,%d] error code %s", r.getRowNum(), column, error));
			 }
		}
		throw new IllegalArgumentException("Don't know how to convert cell of type " + cellValue.getCellType());
	}
	
	public boolean isNumber(int row, int column) {
		org.apache.poi.ss.usermodel.Row r = sheet.getRow(row);
		if(r == null) {
			return false;
		}
		Cell cell = r.getCell(column);
		if(cell == null) {
			return false;
		}
		switch(cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK: return false;
		case Cell.CELL_TYPE_BOOLEAN: return false;
		case Cell.CELL_TYPE_ERROR: return false;
		case Cell.CELL_TYPE_FORMULA: return false; // TODO...
		case Cell.CELL_TYPE_NUMERIC: return true;
		case Cell.CELL_TYPE_STRING: return false; // TODO...
		}
		return false;
	}
	
	public int toColumnIndex(String column) {
		int index = 0;
		int digit = 1;
		for(int i = column.length() - 1; i >= 0; i--) {
			char c = Character.toUpperCase(column.charAt(i));
			if(c < 'A' || c > 'Z') {
				throw new IllegalArgumentException("Illegal character in column " + column + ": " + c);
			} else {
				index += (c - 'A' + 1) * digit;
			}
			digit += 'Z' - 'A';
		}
		if(index < 1) {
			throw new IllegalArgumentException("Opps, column " + column + " turned into negatice " + (index - 1));
		}
		return index - 1;
	}

	public Row getRow(int index) {
		return new Row(sheet.getRow(index));
	}
	
	public Iterable<Row> getRows() {
		return getRows(0, getRowCount());
	}
	
	public Iterable<Row> getRows(final int fromRow, final int toRow) {
				
		return new Iterable<ExcelTableReader.Row>() {
			public Iterator<Row> iterator() {
				
				return new Iterator<ExcelTableReader.Row>() {
					
					private int index = fromRow;
					private Sheet sheet = ExcelTableReader.this.sheet;

					public boolean hasNext() {
						return index <= toRow;
					}

					public Row next() {
						if(!hasNext()) {
							throw new NoSuchElementException();
						}
						return new Row(sheet.getRow(index++));
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}		

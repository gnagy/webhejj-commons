/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table;

import java.util.List;

public interface TableReader {
	
	public interface Sheet extends Iterable<Row> {
		int getIndex();
		String getName();
		Row getRow(int index);
		List<Row> getRows();
	}
	
	public interface Row extends Iterable<String> {
		/** @return number of columns in the row */
		int size();
		<T> T getValue(int column, Class<T> valueType);
		<T> T getValue(int column, Class<T> valueType, T defaultValue);
		<T> T getValue(String column, Class<T> valueType);
		<T> T getValue(String column, Class<T> valueType, T defaultValue);
		List<String> getStringValues();
	}
	
	Sheet getSheet(int index);
	Sheet getSheet(String name);
	List<Sheet> getSheets();
}

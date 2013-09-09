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
import hu.webhejj.commons.io.table.TableReader.Row;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractTableRow implements TableReader.Row {

	@Override
	public <T> T getValue(String column, Class<T> valueType) {
		return getValue(AlphaColumnUtil.toNumeric(column), valueType);
	}
	@Override
	public <T> T getValue(String column, Class<T> valueType, T defaultValue) {
		T value = getValue(column, valueType);
		return CompareUtils.isEmpty(value) ? defaultValue : value;
	}
	
	@Override
	public <T> T getValue(int column, Class<T> valueType, T defaultValue) {
		T value = getValue(column, valueType);
		return CompareUtils.isEmpty(value) ? defaultValue : value;
	}
	
	@Override
	public List<String> getStringValues() {
		return new AbstractList<String>() {

			@Override
			public String get(int index) {
				return getValue(index, String.class);
			}

			@Override
			public int size() {
				return AbstractTableRow.this.size();
			}
		};
	}
	
	@Override
	public Iterator<String> iterator() {
		return getStringValues().iterator();
	}
	
	@Override
	public int hashCode() {
	    int hashCode = 1;
	    int columnCount = Math.max(0, size() - 1);
	    for (int i = 0; i < columnCount; i++) {
	    	String value = getValue(i, String.class);
	    	hashCode = 31*hashCode + (value == null ? 0 : value.hashCode());
	    }
	    return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Row other = (Row) obj;
	    int columnCount = Math.max(size() - 1, other.size() - 1);
	    for (int i = 0; i < columnCount; i++) {
	    	if(!CompareUtils.isEqual(getValue(i, String.class), other.getValue(i, String.class))) {
	    		return false;
	    	}
	    }
		return true;
	}

	@Override
	public String toString() {
		return getStringValues().toString();
	}
}

/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0;
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.io.table;

import junit.framework.Assert;

import org.junit.Test;

public class AlphaColumnUtilTest {

	@Test(expected=IndexOutOfBoundsException.class)
	public void testToAlphaNegative() {
		AlphaColumnUtil.toAlpha(-1);
	}

	@Test
	public void testToAlpha() {
		testToAlpha("A", 0);
		testToAlpha("Z", 25);
		testToAlpha("AA", 26);
		testToAlpha("AZ", 51);
		testToAlpha("BA", 52);
	}

	@Test
	public void testRoundTrip() {
		for(int i = 0; i < 60; i++) {
			// System.out.format("%s, %s, %s\n", i, AlphaColumnUtil.toAlpha(i), AlphaColumnUtil.toNumeric(AlphaColumnUtil.toAlpha(i)));
			Assert.assertEquals(i, AlphaColumnUtil.toNumeric(AlphaColumnUtil.toAlpha(i)));
		}
	}
	
	protected void testToAlpha(String expected, int actual) {
		Assert.assertEquals(expected, AlphaColumnUtil.toAlpha(actual));
	}
}

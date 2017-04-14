package ef1.unit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortCheck;

public class TestSortCheck {

	SortCheck sortCheck;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		sortCheck = new SortCheck();
	}

	/*
	 * Test that isSpecialChar returns true for a valid special char
	 */
	@Test
	public void testIsSpecialChar() {
		char c = '#';
		assertTrue(sortCheck.isSpecialChar(c));
	}

	/*
	 * Test that isSpecialChar returns false for a valid special char
	 */
	@Test
	public void testNotSpecialChar() {
		char c = 'a';
		assertFalse(sortCheck.isSpecialChar(c));
	}

	/*
	 * Test that isFile returns true for a valid file
	 */
	@Test
	public void testIsFile() {
		String fileName = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		assertTrue(sortCheck.isFile(fileName));
	}

	/*
	 * Test that isFile returns false for a invalid file
	 */
	@Test
	public void testNotFile() {
		String fileName = "random.txt";
		assertFalse(sortCheck.isFile(fileName));
	}

	/*
	 * Test that isSortByNumCondition returns true for a valid num condition
	 */
	@Test
	public void testIsSortByNumCondition() {
		assertTrue(sortCheck.isSortByNumCondition("-n"));
	}

	/*
	 * Test that isSortByNumCondition returns false for an invalid num condition
	 */
	@Test
	public void testNotSortByNumCondition() {
		assertFalse(sortCheck.isSortByNumCondition("n"));
	}

	/*
	 * Test that isFirstWordNum returns true if the first word is a number
	 */
	@Test
	public void testIsFirstWordNum() {
		String line = "10 million";
		assertTrue(sortCheck.isFirstWordNum(line));
	}

	/*
	 * Test that isFirstWordNum returns false if the first word is not a number
	 */
	@Test
	public void testNotFirstWordNum() {
		String line = "10million";
		assertFalse(sortCheck.isFirstWordNum(line));
	}

	/*
	 * Throw an exception for invalid num condition statement
	 */
	@Test
	public void testCheckValidCondition() throws SortException {
		thrown.expect(SortException.class);
		thrown.expectMessage("Invalid Condition Statment");
		sortCheck.checkValidCondition("n");
	}

	@After
	public void tearDown() {
		sortCheck = null;
	}

}

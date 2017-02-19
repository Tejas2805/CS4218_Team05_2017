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
	public void setup(){
		sortCheck = new SortCheck();
	}
	
	@Test
	public void testIsSpecialChar() {
		char c = '#';
		assertTrue(sortCheck.isSpecialChar(c));
	}
	
	@Test
	public void testNotSpecialChar() {
		char c = 'a';
		assertFalse(sortCheck.isSpecialChar(c));
	}
	
	@Test
	public void testIsFile() {
		String fileName = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		assertTrue(sortCheck.isFile(fileName));
	}
	
	@Test
	public void testNotFile() {
		String fileName = "random.txt";
		assertFalse(sortCheck.isFile(fileName));
	}
	
	@Test
	public void testIsSortByNumCondition() {
		assertTrue(sortCheck.isSortByNumCondition("-n"));
	}
	
	@Test
	public void testNotSortByNumCondition() {
		assertFalse(sortCheck.isSortByNumCondition("n"));
	}
	
	@Test
	public void testIsFirstWordNum(){
		String line = "10 million";
		assertTrue(sortCheck.isFirstWordNum(line));
	}
	
	@Test
	public void testNotFirstWordNum(){
		String line = "10million";
		assertFalse(sortCheck.isFirstWordNum(line));
	}
	
	
	@Test
	public void testCheckValidCondition() throws SortException{
		thrown.expect(SortException.class);
		thrown.expectMessage("Invalid Condition Statment");
		sortCheck.checkValidCondition("n");
	}
	

	@Test
	public void testCheckValidFile() throws SortException{
		thrown.expect(SortException.class);
		thrown.expectMessage("Sort: error reading file");
		sortCheck.checkValidFile("");
	
	}
	
	@After
	public void tearDown(){
		sortCheck = null;
	}

}

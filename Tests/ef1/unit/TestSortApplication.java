package ef1.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.crypto.SealedObject;
import javax.sound.sampled.Line;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class TestSortApplication {

	SortApplication sortApp;
	ByteArrayInputStream stdin;
	ByteArrayOutputStream stdout;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup(){
		sortApp = new SortApplication();
	}
	
	@Test
	public void testValidInputStreamArgs() throws SortException{
		String[] args = {};
		String testString = "read";
		stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		stdout = new ByteArrayOutputStream();
		sortApp.run(args, stdin, stdout);
		
	}
	
	@Test
	public void testValidFileArgs() throws SortException{
		String filename = "tests" + File.separator + "testSortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + filename;
		
		String resultsSimple = sortApp.sortStringsSimple(toSort);
		
		
		assertEquals("", resultsSimple);
	}
	
	@After
	public void tearDown(){
		sortApp = null;
	}

}

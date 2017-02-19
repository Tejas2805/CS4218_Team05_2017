package ef1.unit;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestSortRead {

	SortRead sortRead;
	ByteArrayInputStream stdin;
	ByteArrayOutputStream stdout;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup(){
		sortRead = new SortRead();
	}
	
	@Test
	public void testReadFromInvalidFile() throws SortException {
		thrown.expect(SortException.class);
		thrown.expectMessage("Invalid File");
		sortRead.readFromFile("");
	}
	
	
	@After
	public void tearDown(){
		
	}

}

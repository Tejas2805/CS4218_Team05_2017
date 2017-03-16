package ef2.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestPipeOperator {

	static ShellImpl shellImpl;
	static ByteArrayOutputStream output;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shellImpl = new ShellImpl();
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");
	}
	
	@Test
	public void testEchoPipeEcho(){
		String input = "echo test|echo test1";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "test1\n", actual;
		
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);		
	}

}

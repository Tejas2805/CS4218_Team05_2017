package bf.unit;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import org.junit.BeforeClass;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestShellBF {

	static ShellImpl shellImpl;
	static ByteArrayOutputStream output;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shellImpl = new ShellImpl();
	}
	
	@Test
	public void echoNoQuotes(){
		output = new ByteArrayOutputStream();
		String input = "echo lala";
		String expected = "lala\n";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actual=output.toString();
        assertEquals(expected, actual);
	}
	
	@Test
	public void echoSingleQuotes(){
		String input = "echo 'lala'";
		output = new ByteArrayOutputStream();
		String expected = "lala\n";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actual=output.toString();
        assertEquals(expected, actual);
	}
	
	@Test
	public void echoDoubleQuotes(){
		String input = "echo \"lala\"";
		output = new ByteArrayOutputStream();
		String expected = "lala\n";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actual=output.toString();
        assertEquals(expected, actual);
	}
	
	@Test
	public void echoBackQuotes(){
		output = new ByteArrayOutputStream();
		String input = "echo `lala`";
		String expected = "shell: lala: Invalid app.";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			actual=e.getMessage();
			assertEquals(expected,actual);
		}
	}
	
	@Test
	public void echoDoubleWithBackQuotes(){
		String input = "echo \"This is space:`echo \" \"`.\"";
		output = new ByteArrayOutputStream();
		String expected = "This is space: .\n";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
		}
		actual=output.toString();
        assertEquals(expected, actual);
	}
	
	@Test
	public void echoSingleWithBackQuotes(){
		String input = "echo 'This is space:`echo \" \"`.'";
		output = new ByteArrayOutputStream();
		String expected = "This is space:`echo \" \"`.\n";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
		}
		actual=output.toString();
        assertEquals(expected, actual);
	}
	
	@Test
	public void echoSemicolon(){
		String input = "echo lala; echo lolo";
		output = new ByteArrayOutputStream();
		String expected = "lala\nlolo\n";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
		}
		actual=output.toString();
        assertEquals(expected, actual);
	}
	
	@Test
	public void echoSemicolonWithException(){
		String input = "eo lala; echo lele";
		output = new ByteArrayOutputStream();
		String expected = "shell: eo: Invalid app.";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			actual=e.getMessage();
			assertEquals(expected,actual);
		}
	}
	
	@Test
	public void echoSemicolonWithQuotes(){
		String input = "echo 'lala'; echo \"lolo `echo lele`\"";
		output = new ByteArrayOutputStream();
		String expected = "lala\nlolo lele\n";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
		}
		actual=output.toString();
        assertEquals(expected, actual);
	}
}

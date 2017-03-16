package ef2.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestPipeOperator {

	static ShellImpl shellImpl;
	static ByteArrayOutputStream output;
	private static final String NEW_LINE = System.lineSeparator();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shellImpl = new ShellImpl();
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");
	}
	
	
	@Test
	public void testEchoPipeEcho(){
		
		String input = "echo test|echo test1";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "test1"+NEW_LINE, actual;
		
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
	
	@Test
	public void testInvalidPipeEcho(){
		
		String input = "ec test|echo test1";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "shell: ec: Invalid app.", actual;
		
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			actual=e.getMessage();
			assertEquals(expected, actual);		
		}	
	}
	
	@Test
	public void testEchoPipeInvalid(){
		
		String input = "echo test|echotg test1";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "shell: echotg: Invalid app.", actual;
		
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			actual=e.getMessage();
			assertEquals(expected, actual);		
		}	
	}
	
	@Test
	public void testCdPipeEcho(){
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");
		String input = "cd Tests\\pipeFiles|echo test1";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "test1"+NEW_LINE, actual;
		
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
	
	@Test
	public void testCatPipeGrep(){
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");
		String input = "cat Tests\\pipeFiles\\pipe*|grep \"Hello World\"";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "Hello World"+NEW_LINE+"Ali Hello World Ali"+NEW_LINE+"Pipe Hello World Pipe"+NEW_LINE, actual;
		
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
	
	@Test
	public void testPipeThreeCommand(){
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");
		String input = "cd Tests\\pipeFiles\\test|cat pipe*.txt|grep \"Hello World\"";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "Hello World Hello World"+NEW_LINE, actual;
		
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
	
	@Test
	public void testPipeFourCommand(){
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");
		String input = "cd Tests\\pipeFiles|cd test|cat pipe*.txt|grep \"Hello World\"";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "Hello World Hello World"+NEW_LINE, actual;
		
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

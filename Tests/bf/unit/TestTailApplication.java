package bf.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;

public class TestTailApplication {
	private final String NEWLINE = System.lineSeparator(); 
	private String testStr = "";
	private final String testString ="test"+NEWLINE+"string";;
	private final static String FILEPATH = "Tests" + File.separator + "tailFiles" + File.separator + "123.txt";
	@Test
	public void testNullInput() {
		Application absApp = new TailApplication();
		String[] args = null;
		InputStream stdin= null;
		OutputStream stdout=null;
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String actual=e.getMessage();
			assertEquals("Tail: args, stdin, stdout are null",actual);
	}
	}
	@Test
	public void testNullInputStream() {
		//outputStream not empty args contain path
		Application absApp = new TailApplication();
		String[] args = new String[] {FILEPATH};
		InputStream stdin= null;
		OutputStream stdout = new ByteArrayOutputStream();
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String actual=e.getMessage();
			assertEquals("Tail: stdin is null",actual);
			
	}
		//outputStream not empty args does contain path
		args = new String[] {"-n","3"};
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String actual=e.getMessage();
			assertEquals("Tail: stdin is null",actual);
		}
	}
	@Test
	public void testNullOutputStream() {
		Application absApp = new TailApplication();
		String[] args = new String[] {FILEPATH};
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = null;
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String actual=e.getMessage();
			assertEquals("Tail: stdout is null",actual);
			
	}
	}
	@Test
	public void testNoArgument(){
		Application absApp = new TailApplication();
		String[] args = {};
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();
		//No args with InputStream and OutputStream
			try {
				absApp.run(args, stdin, stdout);
			} catch (AbstractApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals("test"+NEWLINE+"string"+NEWLINE,stdout.toString());
	}
	
	@Test
	public void testOneArgument(){
		Application absApp = new TailApplication();
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();
		String[] args = new String[] {FILEPATH};
		stdout = new ByteArrayOutputStream();
		try {
			absApp.run(args, stdin, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		testStr = "31423" + NEWLINE + "1" + NEWLINE + "15ew"+ NEWLINE + "afg" + NEWLINE + "gaqwtq345" + NEWLINE +"tqtqt" + NEWLINE + "c592859v" +NEWLINE +"gasgsad" +NEWLINE;
		assertEquals(testStr,stdout.toString());
	}
	@Test
	public void testTwoArgument(){
		Application absApp = new TailApplication();
		testStr = "31423" + NEWLINE + "1" + NEWLINE;
		InputStream stdin = new ByteArrayInputStream(testStr.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();


		String[]args = new String[] {"-n","2"};
		try {
			absApp.run(args, stdin, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(testStr,stdout.toString());
	}
	@Test
	public void testThreeArgument(){
		Application absApp = new TailApplication();
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();
		String[] args = new String[] {FILEPATH};
		testStr = "c592859v" + NEWLINE + "gasgsad" + NEWLINE;
		stdin = new ByteArrayInputStream(testStr.getBytes(StandardCharsets.UTF_8));
		stdout = new ByteArrayOutputStream();
		args = new String[] {"-n","2",FILEPATH};
		try {
			absApp.run(args, stdin, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(testStr,stdout.toString());
	}
}

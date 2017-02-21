package bf.unit;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;

public class TestHeadApplication {

	private final static String NEWLINE = System.lineSeparator();
	private final static String FILEPATH = "123.txt";
	private final static String TESTMESSAGE = "test"+NEWLINE+"string";
	@Test
	public void testAllNullArgument() {
		Application absApp = new HeadApplication();
		String[] args = null;
		InputStream stdin= null;
		OutputStream stdout=null;
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String actual=e.getMessage();
			assertEquals("Head: args, stdin, stdout are null",actual);
			
	}
	}
	@Test
	public void testNullInputStream() {
		//outputStream not empty args contain path
		Application absApp = new HeadApplication();
		String[] args = null;
		InputStream stdin= null;
		OutputStream stdout = new ByteArrayOutputStream();
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String actual=e.getMessage();
			assertEquals("Head: stdin is null",actual);
			
	}
		//outputStream not empty args does contain path
		args = new String[] {"-n","3"};
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String actual=e.getMessage();
			assertEquals("Head: stdin is null",actual);
		}
	}
	@Test
	public void testNullOutputStream() {
		Application absApp = new HeadApplication();
		String[] args = new String[] {FILEPATH};
		String testString = TESTMESSAGE;
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = null;
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String actual=e.getMessage();
			assertEquals("Head: stdout is null",actual);
			
	}
	}
	@Test
	public void testNoArgument(){
		Application absApp = new HeadApplication();
		String[] args = {};
		String testString = TESTMESSAGE;
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();
		//No args with InputStream and OutputStream
			try {
				absApp.run(args, stdin, stdout);
			} catch (AbstractApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(TESTMESSAGE,stdout.toString());
	}
	
	@Test
	public void testOneArgument(){
		Application absApp = new HeadApplication();
		String testString = TESTMESSAGE;
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
		
		String testStr = "31423" + NEWLINE + "1" + NEWLINE + "15ew"+ NEWLINE + "afg" + NEWLINE + "gaqwtq345" + NEWLINE +"tqtqt" + NEWLINE + "c592859v" +NEWLINE +"gasgsad" +NEWLINE;
		assertEquals( testStr,stdout.toString());
	}
	@Test
	public void testTwoArgument(){
		Application absApp = new HeadApplication();
		String testStr = "31423" + NEWLINE + "1" + NEWLINE;
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
		Application absApp = new HeadApplication();
		String testString = TESTMESSAGE;
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();
		String[] args = new String[] {FILEPATH};
		String testStr = "31423" + NEWLINE + "1" + NEWLINE;
		stdin = new ByteArrayInputStream(testStr.getBytes(StandardCharsets.UTF_8));
		stdout = new ByteArrayOutputStream();
		args = new String[] {"-n","2",FILEPATH};
		try {
			absApp.run(args, stdin, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals( testStr,stdout.toString());
	}

	
}

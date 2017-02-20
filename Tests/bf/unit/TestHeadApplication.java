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

	@Test
	public void testAllNullArgument() {
		Application absApp = new HeadApplication();
		String[] args = null;
		InputStream stdin= null;
		OutputStream stdout=null;
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String a=e.getMessage();
			assertEquals("Head: args, stdin, stdout are null",a);
			
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
			String a=e.getMessage();
			assertEquals("Head: stdin is null",a);
			
	}
		//outputStream not empty args does contain path
		args = new String[] {"-n","3"};
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String a=e.getMessage();
			assertEquals("Head: stdin is null",a);
		}
	}
	@Test
	public void testNullOutputStream() {
		String ls = System.lineSeparator();
		Application absApp = new HeadApplication();
		String[] args = new String[] {"123.txt"};
		String testString = "test"+ls+"string";
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = null;
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String a=e.getMessage();
			assertEquals("Head: stdout is null",a);
			
	}
	}
	@Test
	public void testNoArgument(){
		Application absApp = new HeadApplication();
		String[] args = {};
		String ls = System.lineSeparator();
		String testString = "test"+ls+"string";
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();
		//No args with InputStream and OutputStream
			try {
				absApp.run(args, stdin, stdout);
			} catch (AbstractApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals("test"+ls+"string",stdout.toString());
	}
	
	@Test
	public void testOneArgument(){
		Application absApp = new HeadApplication();
		String ls = System.lineSeparator();
		String testString = "test"+ls+"string";
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();
		String[] args = new String[] {"123.txt"};
		stdout = new ByteArrayOutputStream();
		try {
			absApp.run(args, stdin, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String testStr = "31423" + ls + "1" + ls + "15ew"+ ls + "afg" + ls + "gaqwtq345" + ls +"tqtqt" + ls + "c592859v" +ls +"gasgsad" +ls;
		assertEquals( testStr,stdout.toString());
	}
	@Test
	public void testTwoArgument(){
		Application absApp = new HeadApplication();
		String ls = System.lineSeparator();
		String testStr = "31423" + ls + "1" + ls;
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
		String ls = System.lineSeparator();
		String testString = "test"+ls+"string";
		InputStream stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		OutputStream stdout = new ByteArrayOutputStream();
		String[] args = new String[] {"123.txt"};
		String testStr = "31423" + ls + "1" + ls;
		stdin = new ByteArrayInputStream(testStr.getBytes(StandardCharsets.UTF_8));
		stdout = new ByteArrayOutputStream();
		args = new String[] {"-n","2","123.txt"};
		try {
			absApp.run(args, stdin, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals( testStr,stdout.toString());
	}

	
}

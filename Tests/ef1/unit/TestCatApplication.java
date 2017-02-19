package ef1.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

public class TestCatApplication {

	static CatApplication catApplication;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		catApplication = new CatApplication();
	}

	@Test
	public void catWithNonExistantFile(){
		String[] args = {"asdsad.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "\n", actual;
		
		try {
			catApplication.run(args, stdin, output);
		} catch (CatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

		actual = output.toString();
		assertEquals(expected, actual);
			
		
	}
	
	@Test
	public void catWithOneValidFile(){
		String[] args = {"catTestSource1.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "hello world\n";
		String actual;
		try{
			catApplication.run(args, stdin, output);

			actual = output.toString();
			assertEquals(expected, actual);
		}catch(CatException e){

			fail();
		}
	}

	@Test
	public void catWithMultipleValidFiles(){
		String[] args = {"catTestSource1.txt","catTestSource2.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "hello world\nhello world\n";
		String actual;
		try{
			catApplication.run(args, stdin, output);

			actual = output.toString();
			assertEquals(expected, actual);
		}catch(CatException e){

			fail();
		}
	}

	@Test
	public void catWithNoOutputStream() {
		String[] args = {"Dummy", "Args"};
		InputStream stdin = System.in;
		OutputStream output = null;
		String expected = "cat: OutputStream not provided";
		String actual;
		try{
			catApplication.run(args, stdin, output);
			fail();
		}catch(CatException e){
			
			actual = e.getMessage();
			assertEquals(expected, actual);
		}
	}

	@Test
	public void catWithNoArgumentsAndNoInputStream() {
		String[] argsEmpty = {};
		String[] argsNull = null;
		InputStream stdin = null;
		OutputStream output = System.out;
		String expected = "cat: InputStream not provided";
		String actual;
		try{
			catApplication.run(argsEmpty, stdin, output);
			fail();
		}catch(CatException e){
			
			actual = e.getMessage();
			assertEquals(expected, actual);
		}
		
		try{
			catApplication.run(argsNull, stdin, output);
			fail();
		}catch(CatException e){
			
			actual = e.getMessage();
			assertEquals(expected, actual);
		}
	}
	
	public void catWithNoArgumentsButWithInputStream() {
		String[] argsEmpty = {};
		String[] argsNull = null;
		InputStream stdin = new ByteArrayInputStream("Hello\nWorld".getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "Hello\nWorld";
		String actual;
		
		try {
			catApplication.run(argsEmpty, stdin, output);
			actual = output.toString();
			assertEquals(expected, actual);
		} catch (CatException e) {
			fail();
		}
		
			
		try {
			catApplication.run(argsNull, stdin, output);
			actual = output.toString();
			assertEquals(expected, actual);
		} catch (CatException e) {
			fail();
		}
	}

}

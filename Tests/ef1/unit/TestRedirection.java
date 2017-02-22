package ef1.unit;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestRedirection {

	static ShellImpl shellImpl;
	static ByteArrayOutputStream output;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shellImpl = new ShellImpl();
	}
	
	@Test
	public void catInputRedirectionFromOneFile(){
		String input = "cat < Tests\\catFiles\\test.txt";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "this is a file \"test.txt\"\n", actual;
		
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			fail();
		} catch (ShellException e) {
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);		
	}

	@Test
	public void echoOutputRedirection(){
		File f = new File("a.txt");
		
		if(f.exists() && !f.isDirectory()){
			f.delete();
		}
				
		String input = "echo hello > a.txt";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "hello\r\n", actual;
		
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			fail();
		} catch (ShellException e) {
			fail();
		}
		
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new FileReader("a.txt"));
		} catch (FileNotFoundException e) {
			fail();
		}
		try {
		    sb = new StringBuilder();
		    String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				fail();
			}

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        try {
					line = br.readLine();
				} catch (IOException e) {
					fail();
				}
		    }
		    String everything = sb.toString();

			actual = everything;
			
			
			assertEquals(expected, actual);
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				fail();
			}

		}
				
	}
	

	@Test
	public void catInputAndOutputRedirection(){

		File f = new File("a.txt");
		
		if(f.exists() && !f.isDirectory()){
			f.delete();
		}
			
		
		String input = "cat < Tests\\catFiles\\test.txt > a.txt";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "this is a file \"test.txt\"\r\n", actual;
		
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			e.printStackTrace();
			fail();
		} catch (ShellException e) {
			e.printStackTrace();
			fail();
		}
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new FileReader("a.txt"));
		} catch (FileNotFoundException e) {
			fail();
		}
		try {
		    sb = new StringBuilder();
		    String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				fail();
			}

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        try {
					line = br.readLine();
				} catch (IOException e) {
					fail();
				}
		    }
		    String everything = sb.toString();

			actual = everything;
			
			assertEquals(expected, actual);
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				fail();
			}

		}

	}
}

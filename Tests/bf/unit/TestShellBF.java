package bf.unit;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

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
		String actual=output.toString();
		String expected = "";
		try {
			shellImpl.parseAndEvaluate("echo", output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertEquals(expected, actual);
	}
	
	@Test
	public void echoSingleQuotes(){
		output = new ByteArrayOutputStream();
		
		String expected = "lala";
		try {
			shellImpl.parseAndEvaluate("echo lala", output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String actual=output.toString();
        assertEquals(expected, actual);
	}
	@After
	public void tearDown(){
		shellImpl = null;
	}

}

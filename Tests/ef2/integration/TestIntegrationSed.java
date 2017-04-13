package ef2.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationSed {

ShellImpl shellImpl;
	
	@Before
    public void setUp() {
		shellImpl = new ShellImpl();
	}


	@Test
	public void testLocalSedsWithValidFileInput() throws AbstractApplicationException, ShellException {
		String input = "sed s/o/O/ Tests\\sedFiles\\hello.txt";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		String expected ="hellO oreo"+System.lineSeparator()+"wOrld"+System.lineSeparator();
		
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testGlobalSedsWithValidFileInput() throws AbstractApplicationException, ShellException {
		String input = "sed s/o/O/g Tests\\sedFiles\\hello.txt";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		String expected ="hellO OreO"+System.lineSeparator()+"wOrld"+System.lineSeparator();
		
		assertEquals(expected, stdout.toString());
	}
}

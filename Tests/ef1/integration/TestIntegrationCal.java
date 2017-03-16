package ef1.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationCal {

	ShellImpl shellImpl;
	@Before
    public void setUp() {
		shellImpl = new ShellImpl();
	}

	@Test
	public void testArgumentIsValid() throws AbstractApplicationException, ShellException, IOException {
		String input = "cal 2 2017";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		List<String> lines = Files.readAllLines(Paths.get("Tests\\calFiles\\calTestOutput1.txt"));
		String expected = "";
		for(String line : lines)
			expected += line + "\n";
		
		assertEquals(expected+"\n", stdout.toString());
	}


}

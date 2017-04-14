package bf.unit;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class TestGrepApplication {

	static GrepApplication grepApplication;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		grepApplication = new GrepApplication();
	}

	@Test
	public void testRunWithNullOutputStream() {

		String[] args = { "Dummy", "Args" };
		InputStream inputStream = System.in;
		OutputStream outputStream = null;
		String expected = "grep: OutputStream not provided";
		String actual;
		try {
			grepApplication.run(args, inputStream, outputStream);
			fail();
		} catch (AbstractApplicationException e) {
			actual = e.getMessage();
			assertEquals(expected, actual);
		}

	}

	@Test
	public void testRunWithoutArguments() {

		String[] argsEmpty = {};
		String[] argsNull = null;
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		String expected = "";
		String actual;
		try {
			grepApplication.run(argsEmpty, inputStream, outputStream);
			actual = outputStream.toString();
			assertEquals(expected, actual);
			fail();
		} catch (AbstractApplicationException e) {
		}

		try {
			grepApplication.run(argsNull, inputStream, outputStream);
			actual = outputStream.toString();
			assertEquals(expected, actual);
			fail();
		} catch (AbstractApplicationException e) {
		}
	}

	@Test
	public void testRunWithOnlyPatternWithoutInputStream() {

		String[] argsOnlyPattern = { "pattern" };
		InputStream inputStream = null;
		OutputStream outputStream = new ByteArrayOutputStream();
		String expected = "grep: InputStream not provided";
		String actual;
		try {
			grepApplication.run(argsOnlyPattern, inputStream, outputStream);
			fail();
		} catch (AbstractApplicationException e) {
			actual = e.getMessage();
			assertEquals(expected, actual);
		}

	}

	@Test
	public void testRunWithOnlyPatternAndInputStream() {

		String[] argsOnlyPattern = { "pattern" };
		InputStream inputStream = new ByteArrayInputStream("pattern".getBytes());
		OutputStream outputStream = new ByteArrayOutputStream();
		String expected = "pattern\n";
		String actual;
		try {
			grepApplication.run(argsOnlyPattern, inputStream, outputStream);
			actual = outputStream.toString();
			assertEquals(expected, actual);
		} catch (AbstractApplicationException e) {
			fail();
		}

	}

	@Test
	public void testRunWithOnlyOneFile() {

		String[] argsOnlyPattern = { "pattern", "Tests\\grepFiles\\grepTestSource1.txt" };
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		String expected = "pattern\n";
		String actual;
		try {
			grepApplication.run(argsOnlyPattern, inputStream, outputStream);
			actual = outputStream.toString();
			assertEquals(expected, actual);
		} catch (AbstractApplicationException e) {
			fail();
		}

	}

	@Test
	public void testRunWithNonExistantFile() {

		String[] argsOnlyPattern = { "pattern", "aaa.txt" };
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			grepApplication.run(argsOnlyPattern, inputStream, outputStream);
			fail();
		} catch (AbstractApplicationException e) {

		}

	}

	@Test
	public void testRunWithOnlyMultipleFiles() {

		String[] argsOnlyPattern = { "pattern", "Tests\\grepFiles\\grepTestSource1.txt",
				"Tests\\grepFiles\\grepTestSource2.txt" };
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		String expected = "pattern\npattern\n";
		String actual;
		try {
			grepApplication.run(argsOnlyPattern, inputStream, outputStream);
			actual = outputStream.toString();
			assertEquals(expected, actual);
		} catch (AbstractApplicationException e) {
			fail();
		}

	}
}

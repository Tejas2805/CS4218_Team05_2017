package ef2.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class TestSedApplication {
	
	SedApplication sedApp;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before 
	public void setup(){
		sedApp = new SedApplication();
	}
	
	@Test
	public void testSedsWithInvalidFileInput() {
		String[] args = {"s/o/O/g", "Tests\\sedFiles\\helloasdasdasda.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		try {
			sedApp.run(args, stdin, stdout);
			fail();
		} catch (SedException e) {
			return;
		}
	}
	
	@Test
	public void testSedsWithPipe() {
		String[] args = {"s/\\|/O/g"};
		InputStream stdin = new ByteArrayInputStream("h|llo".getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		try {
			sedApp.run(args, stdin, stdout);
			
		} catch (SedException e) {
			fail();
			return;
		}
		String actual = stdout.toString();
		String expected = "hOllo\n";
		
		assertEquals(expected, actual);
	}

	@Test
	public void testSedsWithNoArguments() {
		String[] args = {};
		InputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		try {
			sedApp.run(args, stdin, stdout);
			fail();
		} catch (SedException e) {
			try {
				sedApp.run(args, stdin, stdout);
				fail();
			} catch (SedException ee) {
				return;
			}
		}
	}


	@Test
	public void testLocalSedsWithNoOutputStream() {
		String[] args = {"s/o/O/", "Tests\\sedFiles\\hello.txt"};
		InputStream stdin = null;
		
		try {
			sedApp.run(args, stdin, null);
			fail();
		} catch (SedException e) {
			return;
		}
	}
	
	@Test
	public void testSedsWithOneArgumentAndNoInputStream() {
		String[] args = {"s/o/O/"};
		InputStream stdin = null;
		
		try {
			sedApp.run(args, stdin, null);
			fail();
		} catch (SedException e) {
			return;
		}
	}
	
	@Test
	public void testSedsWithOneInvalidReplacement() {
		String[] args = {"s/o/O", "Tests\\sedFiles\\hello.txt"};
		String[] args2 = {"s/o//", "Tests\\sedFiles\\hello.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		try {
			sedApp.run(args, stdin, stdout);
			fail();
		} catch (SedException e) {
			try {
				sedApp.run(args2, stdin, stdout);
				fail();
			} catch (SedException ee) {
				return;
			}
		}
	}
	

	@Test
	public void testSedsWithOneInvalidRegex() {
		String[] args = {"s///O", "Tests\\sedFiles\\hello.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		try {
			sedApp.run(args, stdin, stdout);
			fail();
		} catch (SedException e) {
			return;
		}
	}
	
	@Test
	public void testLocalSedsWithFileInput() {
		String[] args = {"s/o/O/", "Tests\\sedFiles\\hello.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String expected = "hellO oreo\nwOrld\n";
		String actual;
		
		try {
			sedApp.run(args, stdin, stdout);
			actual = stdout.toString();
			assertEquals(expected, actual);
		} catch (SedException e) {
			fail();
		}
	}
	

	@Test
	public void testGlobalSedsWithFileInput() {
		String[] args = {"s/o/O/g", "Tests\\sedFiles\\hello.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String expected = "hellO OreO\nwOrld\n";
		String actual;
		
		try {
			sedApp.run(args, stdin, stdout);
			actual = stdout.toString();
			assertEquals(expected, actual);
		} catch (SedException e) {
			fail();
		}
	}
	
	@Test
	public void testLocalSedsWithInputStream() {
		String[] args = {"s/t/T/"};
		InputStream stdin = new ByteArrayInputStream("test\ntis".getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String expected = "Test\nTis\n";
		String actual;
		
		try {
			sedApp.run(args, stdin, stdout);
			actual = stdout.toString();
			assertEquals(expected, actual);
		} catch (SedException e) {
			fail();
		}
	}
		
	@Test
	public void testGlobalSedsWithInputStream() {
		String[] args = {"s/t/T/g"};
		InputStream stdin = new ByteArrayInputStream("test\ntis".getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String expected = "TesT\nTis\n";
		String actual;
		
		try {
			sedApp.run(args, stdin, stdout);
			actual = stdout.toString();
			assertEquals(expected, actual);
		} catch (SedException e) {
			fail();
		}
	}
	
	@After
	public void tearDown(){
		sedApp = null;
	}

}

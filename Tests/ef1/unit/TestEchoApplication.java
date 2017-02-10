/**
 * 
 */
package ef1.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.EchoException;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;

/**
 * @author User
 *
 */
public class TestEchoApplication {

	static EchoApplication echoApplication;
	
	@Test
	public void testOutputStreamIsNull() {
		String[] arguments = {"Fake", "Arguments"};
		InputStream inputStream = System.in;
		
		try{
			echoApplication.run(arguments, inputStream, null);
			fail("OutputStream null passed to echoApplication but EchoException not thrown");
		}
		catch(EchoException e){
			String expected = "echo: OutputStream not provided";
			String actual = e.getMessage();
			assertEquals(expected, actual);
		}
		
	}
	
	@Test
	public void testArguentIsNull() {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		
		try{
			echoApplication.run(null, inputStream, outputStream);
			fail("Arguments null passed to echoApplication but EchoException not thrown");
		}
		catch(EchoException e){
			String expected = "echo: Null arguments";
			String actual = e.getMessage();
			assertEquals(expected, actual);
		}
		
	}
	
	@Test
	public void testArgumentIsEmpty() {
		InputStream inputStream = System.in;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String[] arguments = {};
		
		try {
			echoApplication.run(arguments, inputStream, outputStream);
		} catch (EchoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String expected = "\n\n";
		String actual = outputStream.toString();
		assertEquals(expected, actual);
		
		
	}
	
	@Test
	public void testArgumentIsWords() {
		InputStream inputStream = System.in;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String[] arguments = {"Hello", "World"};
		
		try {
			echoApplication.run(arguments, inputStream, outputStream);
		} catch (EchoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String expected = "Hello World\n";
		String actual = outputStream.toString();
		assertEquals(expected, actual);
		
		
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		echoApplication = new EchoApplication();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		echoApplication = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}



}

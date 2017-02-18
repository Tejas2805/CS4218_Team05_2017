package ef1.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.INITIALIZE;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.PwdException;
import sg.edu.nus.comp.cs4218.impl.app.PwdApplication;

public class TestPwdApplication {

	PwdApplication pwdApp;
	ByteArrayOutputStream stdout;
	
	@Before
    public void setUp() {
		pwdApp = new PwdApplication();
		stdout = new ByteArrayOutputStream();
    }
	
	@Test
    public void testPwdWithNoArg() throws PwdException{
		 pwdApp.run(new String[] {}, null, stdout);
		 System.out.print(stdout.toString());
	     assertEquals(Environment.currentDirectory + System.lineSeparator() + System.lineSeparator(), stdout.toString());
    }

    @After
    public void tearDown() {
        pwdApp = null;
    }
}

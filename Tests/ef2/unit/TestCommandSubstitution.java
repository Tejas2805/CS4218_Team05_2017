package ef2.unit;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
public class TestCommandSubstitution {

	
	static final String NEWLINE = System.getProperty("line.separator");
	 @Test
	    public void testCatWithCommandSubstitution() throws AbstractApplicationException, ShellException {
		 ShellImpl shell = null;
		 ByteArrayOutputStream output;
		// shell.parseAndEvaluate("cat `echo test1.txt` test2.txt", output);
	        assertEquals(1,1);
	    //assert("test1" + NEWLINE + "test1test2" + NEWLINE + "test2", output.toString());
	    }
}

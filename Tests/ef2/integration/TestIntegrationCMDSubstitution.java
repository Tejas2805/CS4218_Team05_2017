package ef2.integration;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
public class TestIntegrationCMDSubstitution {

	static final String NEWLINE = System.getProperty("line.separator");
	private static void createFiles() {
        //Create n files for testing
            try {
                PrintWriter writer = new PrintWriter("a.txt");
                writer.println("123452");
                writer.close();
                writer = new PrintWriter("123.txt");
                writer.println("aadfa");
                writer.close();
            } catch (IOException ioe) {
            
        }
    }
	private static void deleteFiles() {
        File file = new File("a.txt");
			file.delete();
			file = new File("123.txt");
			file.delete();
    }
	 @Test
	    public void testCatWithCommandSubstitution() throws AbstractApplicationException, ShellException {
		 ShellImpl shell = new ShellImpl();
		 createFiles();
		 ByteArrayOutputStream output = new ByteArrayOutputStream();
		 try{
		 shell.parseAndEvaluate("cat `echo a.txt` 123.txt", output);
		 }catch (Exception e){
			 String actual = e.getMessage();
			 assertEquals("123452" + NEWLINE +"aadfa" + NEWLINE  , actual);
		 }
	        assertEquals(1,1);
	    
	    }
	    @Test
	    public void testEchoWithValidCommandSubstitutionSingleQuoted() throws AbstractApplicationException, ShellException {
	    	ShellImpl shell = new ShellImpl();
			 ByteArrayOutputStream output = new ByteArrayOutputStream();
	        shell.parseAndEvaluate("echo 'Testing 123 `echo \"nTesting 123\"` and `echo \"2nd Testing 123\"`'", output);
	        assertEquals("Testing 123 `echo \"nTesting 123\"` and `echo \"2nd Testing 123\"`" + NEWLINE, output.toString());
	    }
	    
	    @Test
	    public void testEchoWithValidCommandSubstitutionDoubleQuoted() throws AbstractApplicationException, ShellException {
	    	ShellImpl shell = new ShellImpl();
			 ByteArrayOutputStream output = new ByteArrayOutputStream();
	        shell.parseAndEvaluate("echo \"Double Quote `echo \"nTest\"` and `echo \"2nd space\"`\"", output);
	        assertEquals("Double Quote nTest and 2nd space"+ NEWLINE , output.toString());
	    }
	    
	    @Test
	    public void testEchoWithInvalidCommandSubstitutionBackQuoted() throws AbstractApplicationException, ShellException {
	    	ShellImpl shell = new ShellImpl();
			 ByteArrayOutputStream output = new ByteArrayOutputStream();
			 try
			 {
				 shell.parseAndEvaluate("echo `error_input`", output);
				 fail();
			 }
			 catch(ShellException s)
			 {
				 
				 assertEquals("shell: error_input: Invalid app.", s.getMessage());
			 }
			
	       
	    }
	    
	    @Test
	    public void testEchoWithInvalidCommandSubstitutionDoubleQuoted() throws AbstractApplicationException, ShellException {
	    	ShellImpl shell = new ShellImpl();
			 ByteArrayOutputStream output = new ByteArrayOutputStream();
			 try{
	    	shell.parseAndEvaluate("echo \"testing: `error_input`\"", output);
			 }catch(Exception e){
				 String actual = e.getMessage();
				 assertEquals("shell: error_input: Invalid app.", actual);
			 }
	        
	        deleteFiles();
	    }
}

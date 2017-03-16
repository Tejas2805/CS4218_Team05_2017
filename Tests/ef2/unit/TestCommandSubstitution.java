package ef2.unit;
import static org.junit.Assert.*;
public class TestCommandSubstitution {

	static final String NEWLINE = System.getProperty("line.separator");
	 @Test
	    public void testCatWithCommandSubstitution() throws AbstractApplicationException, ShellException {
		 ShellImpl shell;
		 shell.parseAndEvaluate("cat `echo test1.txt` test2.txt", testOut);
	        assertEquals(1,1);
	    assert("test1" + NEWLINE + "test1test2" + NEWLINE + "test2", testOut.toString());
	    }
}

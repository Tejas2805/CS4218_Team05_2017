package ef2.unit;
import static org.junit.Assert.*;
public class TestCommandSubstitution {

	static final 
	 @Test
	    public void testCatWithCommandSubstitution() throws AbstractApplicationException, ShellException {
	        shell.parseAndEvaluate("cat `echo test1.txt` test2.txt", testOut);
	        assertEquals(1,1);
	    assert("test1" + NL + "test1test2" + NL + "test2", testOut.toString());
	    }
}

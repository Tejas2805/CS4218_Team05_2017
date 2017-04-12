import static org.junit.Assert.*;

import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class DateWcSortBugFix {

	//Wc: throw exception when using option + stdin
	//Wc: return nothing when using more than 1 options + stdin
	//Sort: numbers (-n option) wit stdin returns nothing
	//Date: run correctly and also throw exception on more than 0 input args
	
	ShellImpl shellImpl;
	OutputStream stdout;
	WcApplication wcApp;
	
	@Before
    public void setUp(){
		shellImpl = new ShellImpl();
	}
	@After
	public void tearDown() throws Exception {
		shellImpl = null;
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

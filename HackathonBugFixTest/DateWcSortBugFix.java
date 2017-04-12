import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class DateWcSortBugFix {

	//Wc: throw exception when using option + stdin
	//Wc: return nothing when using more than 1 options + stdin
	//Sort: numbers (-n option) wit stdin returns nothing
	//Date: run correctly and also throw exception on more than 0 input args
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

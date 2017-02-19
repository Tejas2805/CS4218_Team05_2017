package ef1.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestShellEF1 {

	ShellImpl shellImpl;
	
	@Before
	public void setup(){
		shellImpl = new ShellImpl();
	}
	
	@Test
	public void testBF() {
		fail("Not yet implemented");
	}
	

	@After
	public void tearDown(){
		shellImpl = null;
	}

}

package bf.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestShellBF {

	ShellImpl shellImpl;
	
	@Before
	public void setup(){
		shellImpl = new ShellImpl();
	}
	
	@Test
	public void testGlobbing() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testIoRedirection() {
		fail("Not yet implemented");
	}
	
	@After
	public void tearDown(){
		shellImpl = null;
	}

}

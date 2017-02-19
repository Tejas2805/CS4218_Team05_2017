package ef2.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestShellEF2 {
	
	ShellImpl shellImpl;
	
	@Before
	public void setup(){
		shellImpl = new ShellImpl();
	}
	
	@Test
	public void testPipeOperator() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCommandSubstitution() {
		fail("Not yet implemented");
	}
	
	@After
	public void tearDown(){
		shellImpl = null;
	}

}

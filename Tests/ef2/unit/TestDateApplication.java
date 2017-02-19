package ef2.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.DateApplication;

public class TestDateApplication {

	DateApplication dataApp;
	
	@Before
	public void setup(){
		dataApp = new DateApplication();
	}
	
	@Test
	public void testOutput() {
		dataApp.run("", null, null);
	}
	
	@After
	public void tearDown(){
		dataApp = null;
	}

}

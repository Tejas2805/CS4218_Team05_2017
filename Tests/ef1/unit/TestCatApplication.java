package ef1.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

public class TestCatApplication {

	static CatApplication catApplication;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		catApplication = new CatApplication();
	}


	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

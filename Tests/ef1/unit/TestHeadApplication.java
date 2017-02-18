package ef1.unit;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;

public class TestHeadApplication {

	@Test
	public void test() {
		Application absApp = new HeadApplication();
		String[] args = null;
		InputStream stdin= null;
		OutputStream stdout=null;
		try{
			absApp.run(args, stdin, stdout);
		}catch (Exception e){
			String a=e.getMessage();
			assertEquals(a,"Head: args, stdin, stdout are null");
			
	}
		
	}
}

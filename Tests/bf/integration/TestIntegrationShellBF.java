package bf.integration;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import org.junit.BeforeClass;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationShellBF {

	static ShellImpl shellImpl;
	static ByteArrayOutputStream output;
	private static final String NEW_LINE = System.lineSeparator();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shellImpl = new ShellImpl();
	}

	@Test
	public void echoNoQuotes() {
		output = new ByteArrayOutputStream();
		String input = "echo lala";
		String expected = "lala" + NEW_LINE;
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void echoSingleQuotes() {
		String input = "echo 'lala'";
		output = new ByteArrayOutputStream();
		String expected = "lala" + NEW_LINE;
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void echoDoubleQuotes() {
		String input = "echo \"lala\"";
		output = new ByteArrayOutputStream();
		String expected = "lala" + NEW_LINE;
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void echoBackQuotes() {
		output = new ByteArrayOutputStream();
		String input = "echo `lala`";
		String expected = "shell: lala: Invalid app.";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			actual = e.getMessage();
			assertEquals(expected, actual);
		}
	}

	@Test
	public void echoDoubleWithBackQuotes() {
		String input = "echo \"This is space:`echo \" \"`.\"";
		output = new ByteArrayOutputStream();
		String expected = "This is space: ." + NEW_LINE;
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void echoSingleWithBackQuotes() {
		String input = "echo 'This is space:`echo \" \"`.'";
		output = new ByteArrayOutputStream();
		String expected = "This is space:`echo \" \"`." + NEW_LINE;
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void echoSemicolon() {
		String input = "echo lala; echo lolo";
		output = new ByteArrayOutputStream();
		String expected = "lala" + NEW_LINE + "lolo" + NEW_LINE;
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void echoSemicolonWithException() {
		String input = "eo lala; echo lele";
		output = new ByteArrayOutputStream();
		String expected = "shell: eo: Invalid app.";
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			actual = e.getMessage();
			assertEquals(expected, actual);
		}
	}

	@Test
	public void echoSemicolonWithQuotes() {
		String input = "echo 'lala'; echo \"lolo `echo lele`\"";
		output = new ByteArrayOutputStream();
		String expected = "lala" + NEW_LINE + "lolo lele" + NEW_LINE;
		String actual;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void catAppCalled() {
		String input = "cat test.txt";
		output = new ByteArrayOutputStream();
		String expectedAppCalled = "cat";
		String[] expectedArg = { "test.txt" };
		String actualAppCalled;
		String[] actualArg = new String[1];
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actualAppCalled = shellImpl.getAppCalled();
		for (int i = 0; i < actualArg.length; i++) {
			actualArg[i] = shellImpl.getArgument()[i];
		}
		assertEquals(expectedAppCalled, actualAppCalled);
		assertEquals(expectedArg[0], actualArg[0]);
	}

	@Test
	public void cdAppCalled() {
		String input = "cd ..";
		output = new ByteArrayOutputStream();
		String expectedAppCalled = "cd";
		String[] expectedArg = { ".." };
		String actualAppCalled;
		String[] actualArg = new String[1];
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actualAppCalled = shellImpl.getAppCalled();
		for (int i = 0; i < actualArg.length; i++) {
			actualArg[i] = shellImpl.getArgument()[i];
		}
		assertEquals(expectedAppCalled, actualAppCalled);
		assertEquals(expectedArg[0], actualArg[0]);
	}

	@Test
	public void pwdAppCalled() {
		String input = "pwd";
		output = new ByteArrayOutputStream();
		String expectedAppCalled = "pwd";
		String actualAppCalled;
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actualAppCalled = shellImpl.getAppCalled();
		assertEquals(expectedAppCalled, actualAppCalled);
	}

	@Test
	public void echoAppCalled() {
		String input = "echo lala";
		output = new ByteArrayOutputStream();
		String expectedAppCalled = "echo";
		String[] expectedArg = { "lala" };
		String actualAppCalled;
		String[] actualArg = new String[1];
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actualAppCalled = shellImpl.getAppCalled();
		for (int i = 0; i < actualArg.length; i++) {
			actualArg[i] = shellImpl.getArgument()[i];
		}
		assertEquals(expectedAppCalled, actualAppCalled);
		assertEquals(expectedArg[0], actualArg[0]);
	}

	@Test
	public void headAppCalled() {
		String input = "head -n 15 test.txt";
		output = new ByteArrayOutputStream();
		String expectedAppCalled = "head";
		String[] expectedArg = { "-n", "15", "test.txt" };
		String actualAppCalled;
		String[] actualArg = new String[3];
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			actualAppCalled = shellImpl.getAppCalled();
			for (int i = 0; i < actualArg.length; i++) {
				actualArg[i] = shellImpl.getArgument()[i];
			}
			assertEquals(expectedAppCalled, actualAppCalled);
			assertEquals(expectedArg[0], actualArg[0]);
			assertEquals(expectedArg[1], actualArg[1]);
			assertEquals(expectedArg[2], actualArg[2]);
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
	}

	@Test
	public void tailAppCalled() {
		String input = "tail -n 15 test.txt";
		output = new ByteArrayOutputStream();
		String expectedAppCalled = "tail";
		String[] expectedArg = { "-n", "15", "test.txt" };
		String actualAppCalled;
		String[] actualArg = new String[3];
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			actualAppCalled = shellImpl.getAppCalled();
			for (int i = 0; i < actualArg.length; i++) {
				actualArg[i] = shellImpl.getArgument()[i];
			}
			assertEquals(expectedAppCalled, actualAppCalled);
			assertEquals(expectedArg[0], actualArg[0]);
			assertEquals(expectedArg[1], actualArg[1]);
			assertEquals(expectedArg[2], actualArg[2]);
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
}

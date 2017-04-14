package bf.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationCd {

	ShellImpl shellImpl;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		shellImpl = new ShellImpl();
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");

	}

	/*
	 * Test "cd" return the user to the home directory assertEquals is used to
	 * compared the expected directory and the actual current directory
	 */
	@Test
	public void testEmptyArgs() {
		String input = "cd";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String dir = System.getProperty("user.home");

		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}

		assertEquals(dir, Environment.currentDirectory);
	}

	/*
	 * Test "cd /" (for mac or linux) return the user to the root directory Test
	 * "cd \" (for windows) returns the user to the root directory assertEquals
	 * is used to compared the expected directory and the actual current
	 * directory
	 */
	@Test
	public void testRootDirectory() {
		String userDir = System.getProperty("user.home");
		String[] root = userDir.split(Pattern.quote(File.separator));
		String rootDir = root[0];

		String input = "cd " + File.separator;
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}

		assertEquals(rootDir, Environment.currentDirectory);
	}

	/*
	 * Test "cd ~" return the user to the home directory assertEquals is used to
	 * compared the expected directory and the actual current directory
	 */
	@Test
	public void testHomeDirectory() {

		String dir = System.getProperty("user.home");
		String input = "cd ~";
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(dir, Environment.currentDirectory);
	}

	/*
	 * Test "cd ." remain in the current working directory assertEquals is used
	 * to compared the expected directory and the actual current directory
	 */
	@Test
	public void testCurrentDirectory() {
		String dir = Environment.currentDirectory;
		String input = "cd .";
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(dir, Environment.currentDirectory);
	}

	/*
	 * Test "cd .." returns to the parent directory assertEquals is used to
	 * compared the expected directory and the actual current directory
	 */
	@Test
	public void testParentDirectory() {
		String currDir = Environment.currentDirectory;
		String prevDir = currDir;

		int lastSlashIndex = currDir.lastIndexOf(File.separator);
		if (lastSlashIndex != -1) {
			prevDir = currDir.substring(0, lastSlashIndex);
		}

		String input = "cd ..";
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(prevDir, Environment.currentDirectory);
	}

	/*
	 * Test "cd a" where "a" is a invalid directory. An error will be thrown
	 * when the directory is invalid or does not exist
	 */
	@Test(expected = AbstractApplicationException.class)
	public void testCdToInvalidDirectory() throws AbstractApplicationException {
		String input = "cd a";
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			shellImpl.parseAndEvaluate(input, output);

		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}

	}

	/*
	 * Test "cd [valid directory]" This test simulate changing to a valid
	 * directory Let the userDir = C:\Users\Tom prevDir = C:\Users Set the
	 * "Environment.currentDirectory = prevDir (C:\Users) args = {Tom} When
	 * cdApp.run(args, null, null) is called, it will simulate "cd Tom"
	 * assertEquals is used to compared the expected directory and the actual
	 * current directory
	 */
	@Test
	public void testCdToValidDirectory() {

		String userDir = System.getProperty("user.home");
		String[] userDirParts = userDir.split(Pattern.quote(File.separator));

		if (userDirParts.length != 1) {
			String currDir = userDir;
			String prevDir = currDir;

			int lastSlashIndex = currDir.lastIndexOf(File.separator);
			if (lastSlashIndex != -1) {
				prevDir = currDir.substring(0, lastSlashIndex);
			}
			Environment.currentDirectory = prevDir;

			String[] args = { userDirParts[userDirParts.length - 1] };

			String input = "cd " + userDirParts[userDirParts.length - 1];
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			try {
				shellImpl.parseAndEvaluate(input, output);
			} catch (AbstractApplicationException e) {
				// TODO Auto-generated catch block
				fail();
			} catch (ShellException e) {
				// TODO Auto-generated catch block
				fail();
			}

			assertEquals(prevDir + File.separator + args[0], Environment.currentDirectory);

		} else {
			assertFalse(false);
		}

	}

	@After
	public void tearDown() {
		shellImpl = null;
	}

}

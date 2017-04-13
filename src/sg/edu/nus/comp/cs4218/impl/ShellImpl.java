package sg.edu.nus.comp.cs4218.impl;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.app.CalApplication;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;
import sg.edu.nus.comp.cs4218.impl.app.DateApplication;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;
import sg.edu.nus.comp.cs4218.impl.app.PwdApplication;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;
import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;

/**
 * A Shell is a command interpreter and forms the backbone of the entire
 * program. Its responsibility is to interpret commands that the user type and
 * to run programs that the user specify in her command lines.
 * 
 * <p>
 * <b>Command format:</b>
 * <code>&lt;Pipe&gt; | &lt;Sequence&gt; | &lt;Call&gt;</code>
 * </p>
 */

public class ShellImpl implements Shell {

	public static final String EXP_INVALID_APP = "Invalid app.";
	public static final String EXP_SYNTAX = "Invalid syntax encountered.";
	public static final String EXP_REDIR_PIPE = "File output redirection and "
			+ "pipe operator cannot be used side by side.";
	public static final String EXP_SAME_REDIR = "Input redirection file same "
			+ "as output redirection file.";
	public static final String EXP_STDOUT = "Error writing to stdout.";
	public static final String EXP_NOT_SUPPORTED = " not supported yet";
	
	private static ArrayList<String> allFiles = new ArrayList<String>();
	
	private static String appCalled;
	
	private static String[] finalArgsArray;

	private static boolean hasQuotes=false;

	/**
	 * Static method to run the application as specified by the application
	 * command keyword and arguments.
	 * 
	 * @param app
	 *            String containing the keyword that specifies what application
	 *            to run.
	 * @param args
	 *            String array containing the arguments to pass to the
	 *            applications for running.
	 * @param inputStream
	 *            InputputStream for the application to get arguments from, if
	 *            needed.
	 * @param outputStream
	 *            OutputStream for the application to print its output to.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while running any of the
	 *             application(s).
	 * @throws ShellException
	 *             If an unsupported or invalid application command is detected.
	 */
	public static void runApp(String app, String[] argsArray,
			InputStream inputStream, OutputStream outputStream)
			throws AbstractApplicationException, ShellException {
		Application absApp = null;
		//calling application
		if (("cat").equals(app)) {// cat [FILE]...
			absApp = new CatApplication();
		} else if (("echo").equals(app)) {// echo [args]...
			absApp = new EchoApplication();
		} else if (("head").equals(app)) {// head [OPTIONS] [FILE]
			absApp = new HeadApplication();
		} else if (("tail").equals(app)) {// tail [OPTIONS] [FILE]
			absApp = new TailApplication();
		} else if (("cd").equals(app)){// cd PATH
			absApp = new CdApplication();
		} else if (("pwd").equals(app)){// pwd
			absApp = new PwdApplication();
		} else if (("cal").equals(app)){// cal [-m] [[month] year]
			absApp = new CalApplication();
		} else if (("grep").equals(app)){// grep PATTERN [FILE] ...
			absApp = new GrepApplication();
		} else if (("sort").equals(app)){// sort [-n] [FILE]
			absApp = new SortApplication();
		} else if(("wc").equals(app)){
			absApp = new WcApplication();
		} else if(("sed").equals(app)){
			absApp = new SedApplication();
		} else if(("date").equals(app)){
			absApp = new DateApplication();
		} else { // invalid command
			throw new ShellException(app + ": " + EXP_INVALID_APP);
		}
		allFiles.clear();
		appCalled = app;
		//globbing
		Preprocess prepro = new Preprocess();
		allFiles = prepro.preprocessArg(hasQuotes,argsArray);
		finalArgsArray = readAllFile();
		absApp.run(finalArgsArray, inputStream, outputStream);
	}
	
	/**
	 * Static method to read all the file fulfill the regex to process.
	 */
	
	private static String[] readAllFile() {
		String[] finalArgsArray = new String[allFiles.size()];
		for(int i=0;i<allFiles.size();i++){
			finalArgsArray[i]=allFiles.get(i);
		}
		return finalArgsArray;
	}
	public String getAppCalled(){
		return appCalled;
	}
	public String[] getArgument(){
		return finalArgsArray;
	}


	/**
	 * Main method for the Shell Interpreter program.
	 * 
	 * @param args
	 *            List of strings arguments, unused.
	 */

	public static void main(String... args) {
		ShellImpl shell = new ShellImpl();

		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		String readLine = null;
		String currentDir;

		while (true) {
			try {
				currentDir = Environment.currentDirectory;
				System.out.print(currentDir + ">");
				readLine = bReader.readLine();
				if (readLine == null) {
					break;
				}
				if (("").equals(readLine)) {
					continue;
				}
				//System.out.println(readLine);
				shell.parseAndEvaluate(readLine, System.out);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void parseAndEvaluate(String cmdline, OutputStream stdout)
			throws AbstractApplicationException, ShellException {
		// TODO Auto-generated method stub
		//semicolon operator
		ParseAndEvaluation pEva = new ParseAndEvaluation();
		pEva.parseAndEvaluate(cmdline,stdout);
		
		}
	
	@Override
	public String pipeTwoCommands(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pipeMultipleCommands(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pipeWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globNoPaths(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globOneFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globFilesDirectories(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectInput(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectOutput(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectInputWithNoFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectOutputWithNoFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectInputWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectOutputWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String performCommandSubstitution(String args) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String performCommandSubstitutionWithException(String args) {
		// TODO Auto-generated method stub
		String processedData="";
		try {
			CommandSubstitution data = new CommandSubstitution(args);
			processedData = data.process();
		} catch (AbstractApplicationException e) {
			return e.getMessage();
		} catch (ShellException e) {
			return e.getMessage();
		}
		return processedData;
	}

	public static void setHasQuotes(boolean b) {
		// TODO Auto-generated method stub
		hasQuotes=b;
		
	}

}

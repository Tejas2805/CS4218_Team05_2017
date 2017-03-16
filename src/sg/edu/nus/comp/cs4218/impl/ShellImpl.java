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

	/**
	 * Searches for and processes the commands enclosed by back quotes for
	 * command substitution.If no back quotes are found, the argsArray from the
	 * input is returned unchanged. If back quotes are found, the back quotes
	 * and its enclosed commands substituted with the output from processing the
	 * commands enclosed in the back quotes.
	 * 
	 * @param argsArray
	 *            String array of the individual commands.
	 * 
	 * @return String array with the back quotes command processed.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the content in the
	 *             back quotes.
	 * @throws ShellException
	 *             If an exception happens while processing the content in the
	 *             back quotes.
	 */
	public static String[] processBQ(String... argsArray)
			throws AbstractApplicationException, ShellException {
		// echo "this is space `echo "nbsp"`"
		// echo "this is space `echo "nbsp"` and `echo "2nd space"`"
		// Back quoted: any char except \n,`
		String[] resultArr = new String[argsArray.length];
		System.arraycopy(argsArray, 0, resultArr, 0, argsArray.length);
		String patternBQ = "`([^\\n`]*)`";
		Pattern patternBQp = Pattern.compile(patternBQ);

		for (int i = 0; i < argsArray.length; i++) {
			char[] chToken = argsArray[i].toCharArray();
			if(chToken.length  < 2){
				continue;}
			if(chToken[0] == '\'' && chToken[chToken.length-1] == '\''){
				continue;}
			
			Matcher matcherBQ = patternBQp.matcher(argsArray[i]);
			if (matcherBQ.find()) {// found backquoted
				String bqStr = matcherBQ.group(1);
				// cmdVector.add(bqStr.trim());
				// process back quote
				// System.out.println("backquote" + bqStr);
				OutputStream bqOutputStream = new ByteArrayOutputStream();
				ShellImpl shell = new ShellImpl();
				shell.parseAndEvaluate(bqStr, bqOutputStream);

				ByteArrayOutputStream outByte = (ByteArrayOutputStream) bqOutputStream;
				byte[] byteArray = outByte.toByteArray();
				String bqResult = new String(byteArray).replace("\n", "")
						.replace("\r", "");

				// replace substring of back quote with result
				String replacedStr = argsArray[i].replace("`" + bqStr + "`",
						bqResult);
				resultArr[i] = replacedStr;
			}
		}
		return resultArr;
	}

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
		preprocessArg(argsArray);
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
	/**
	 * Static method to preprocess the argument in order to find the file which meet the regex.
	 * 
	 * @param argsArray
	 *             String array containing the arguments to pass to the
	 *            applications for running.
	 */
	private static void preprocessArg(String... argsArray) {
		for(int i=0;i<argsArray.length;i++){
			int numberOfLevel=0;
			if(argsArray[i].contains("*")){
				numberOfLevel = processAsterisk(argsArray, numberOfLevel, i);
			}else{
				allFiles.add(argsArray[i]);
			}
		}
	}

	/**
	 * Static method to find the file when the input contains Asterisk.
	 * 
	 * @param argsArray
	 *            String array containing the arguments to pass to the
	 *            applications for running.
	 * @param numberOfLevel
	 *            the level of the file location.
	 * @param num
	 *            the position of the argsArray.
	 * 
	 */
	private static int processAsterisk(String[] argsArray, int numberOfLevel, int num) {
		String pathUsed="";
		String[] inputTemp = argsArray[num].split("/");
		String input = "";
		for(int j=0;j<inputTemp.length;j++){
			if(inputTemp[j].contains("*")){
				for(int k=0;k<inputTemp.length-j;k++){
					if(k==inputTemp.length-j-1){
						input=input+inputTemp[j+k];
					}else{
						input=input+inputTemp[j+k]+"/";
					}
				}
				break;
			}else{
				pathUsed = pathUsed+inputTemp[j]+"/";
			}
		}
		int output;
		output = preprocessMatchFile(numberOfLevel, pathUsed, input);
		return output;
	}
	/**
	 * Static method to preprocess in order to find the matched file.
	 * 
	 * @param number
	 *            the level of the file location.
	 * @param path
	 *            the path of the file.
	 * @param inputFile
	 *            filename of the file which need to search.
	 * 
	 */
	private static int preprocessMatchFile(int number, String path, String inputFile) {
		String filePathString;
		int numberOfLevel=number;
		Path currentDir = Paths.get(Environment.currentDirectory);
		Path filePath = currentDir.resolve(path);
		filePathString=filePath.toString();
		String inputRegex = "^" +filePathString+"\\"+inputFile.replaceAll("\\*", ".*")+"$";
		String doubleBackSlash="\\\\";
		String finalInputRegex= inputRegex.replace("\\", doubleBackSlash);
		finalInputRegex =finalInputRegex.replace("/", doubleBackSlash);
		
		Pattern pattern = Pattern.compile(finalInputRegex);
		for(int k=0;k<finalInputRegex.split(doubleBackSlash).length;k++){
			if(!finalInputRegex.split(doubleBackSlash)[k].isEmpty()){
				numberOfLevel= numberOfLevel+1;
			}
		}
		final File folder = new File(filePath.toString());
		listFilesForFolder(folder,pattern,numberOfLevel);
		if(allFiles.isEmpty()){
			allFiles.add("");
		}
		return numberOfLevel;
	}
	/**
	 * Static method to find the file which matched the regex.
	 * 
	 * @param folder
	 *            the folder of the corresponding path.
	 * @param pattern
	 *            the pattern which the file need to meet.
	 * @param numOfLevel
	 *            the level of the file location.
	 * 
	 */
	public static void listFilesForFolder(final File folder, Pattern pattern, int numOfLevel) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,pattern,numOfLevel);
	        } else {
	        	Matcher matcher = pattern.matcher(fileEntry.getPath());
	        	while (matcher.find()) {
	        		if(numOfLevel==fileEntry.getPath().split("\\\\").length){
	        			allFiles.add(fileEntry.getPath());
	        		}
	        	}
	        }
	    }
	}
	
	public String getAppCalled(){
		return appCalled;
	}
	
	public String[] getArgument(){
		return finalArgsArray;
	}
	/**
	 * Static method to creates an inputStream based on the file name or file
	 * path.
	 * 
	 * @param inputStreamS
	 *            String of file name or file path
	 * 
	 * @return InputStream of file opened
	 * 
	 * @throws ShellException
	 *             If file is not found.
	 */
	public static InputStream openInputRedir(String inputStreamS)
			throws ShellException {
		File inputFile = new File(inputStreamS);
		FileInputStream fInputStream = null;
		try {
			fInputStream = new FileInputStream(inputFile);
		} catch (FileNotFoundException e) {
			throw (ShellException) new ShellException(e.getMessage()).initCause(e);
		}
		return fInputStream;
	}

	/**
	 * Static method to creates an outputStream based on the file name or file
	 * path.
	 * 
	 * @param onputStreamS
	 *            String of file name or file path.
	 * 
	 * @return OutputStream of file opened.
	 * 
	 * @throws ShellException
	 *             If file destination cannot be opened or inaccessible.
	 */
	public static OutputStream openOutputRedir(String outputStreamS)
			throws ShellException {
		File outputFile = new File(outputStreamS);
		FileOutputStream fOutputStream = null;
		try {
			fOutputStream = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e) {
			throw (ShellException) new ShellException(e.getMessage()).initCause(e);
		}
		return fOutputStream;
	}

	/**
	 * Static method to close an inputStream.
	 * 
	 * @param inputStream
	 *            InputStream to be closed.
	 * 
	 * @throws ShellException
	 *             If inputStream cannot be closed successfully.
	 */
	public static void closeInputStream(InputStream inputStream)
			throws ShellException {
		if (inputStream != System.in) {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw (ShellException) new ShellException(e.getMessage()).initCause(e);
			}
		}
	}

	/**
	 * Static method to close an outputStream. If outputStream provided is
	 * System.out, it will be ignored.
	 * 
	 * @param outputStream
	 *            OutputStream to be closed.
	 * 
	 * @throws ShellException
	 *             If outputStream cannot be closed successfully.
	 */
	public static void closeOutputStream(OutputStream outputStream)
			throws ShellException {
		if (outputStream != System.out) {
			try {
				outputStream.close();
			} catch (IOException e) {
				throw (ShellException) new ShellException(e.getMessage()).initCause(e);
			}
		}
	}

	/**
	 * Static method to write output of an outputStream to another outputStream,
	 * usually System.out.
	 * 
	 * @param outputStream
	 *            Source outputStream to get stream from.
	 * @param stdout
	 *            Destination outputStream to write stream to.
	 * @throws ShellException
	 *             If exception is thrown during writing.
	 */
	public static void writeToStdout(OutputStream outputStream,
			OutputStream stdout) throws ShellException {
		if (outputStream instanceof FileOutputStream) {
			return;
		}
		try {
			stdout.write(((ByteArrayOutputStream) outputStream).toByteArray());
		} catch (IOException e) {
			throw (ShellException) new ShellException(EXP_STDOUT).initCause(e);
		}
	}

	/**
	 * Static method to pipe data from an outputStream to an inputStream, for
	 * the evaluation of the Pipe Commands.
	 * 
	 * @param outputStream
	 *            Source outputStream to get stream from.
	 * 
	 * @return InputStream with data piped from the outputStream.
	 * 
	 * @throws ShellException
	 *             If exception is thrown during piping.
	 */
	public static InputStream outputStreamToInputStream(
			OutputStream outputStream) throws ShellException {
		return new ByteArrayInputStream(
				((ByteArrayOutputStream) outputStream).toByteArray());
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
		String[] cmds = cmdline.split("(;(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)(?=(?:[^\']*\'[^\']*\')*[^\']*$))");
		for(int i=0;i<cmds.length;i++){
			cmds[i] = checkAndPerformCommandSubstitution(cmds[i]);
			//pipe operator
			String[] cmdsPipe = cmds[i].split("(\\|(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)(?=(?:[^\']*\'[^\']*\')*[^\']*$))");
			if(cmdsPipe.length==1){
				CallCommand call = new CallCommand(cmds[i]);
				call.parse();
				call.evaluate(null, stdout);
			}else{
				InputStream inputStream = null;
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				for(int j=0;j<cmdsPipe.length;j++){
					CallCommand call = new CallCommand(cmdsPipe[j]);
					inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					outputStream = new ByteArrayOutputStream();
					call.parse();
					call.evaluate(inputStream, outputStream);
					try {
						if(j==cmdsPipe.length-1){
							stdout.write(outputStream.toString().getBytes());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						throw (ShellException) new ShellException("Unable write to output stream").initCause(e);
					}
				}
			}
			
		}
		
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
	public String checkAndPerformCommandSubstitution(String args){
		CommandSubstitution data = new CommandSubstitution(args);
		ArrayList<String> tokens = data.check(args);
		for (int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			if (!notSingleQuote(token)) {
				tokens.set(i, performCommandSubstitutionWithException(token));
			}
		}
		return String.join("", tokens);
	}
	public boolean notSingleQuote(String args){
		return (args.startsWith("'") && args.endsWith("'"));
	}
	public boolean backQuote(String args){
		return (args.startsWith("`") && args.endsWith("`"));
	}
}

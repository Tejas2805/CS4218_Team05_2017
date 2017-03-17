package sg.edu.nus.comp.cs4218.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;

public class ParseAndEvaluation {

	public ParseAndEvaluation(){
		//Empty
	}
	public void parseAndEvaluate(String cmdline, OutputStream stdout) throws ShellException, AbstractApplicationException{
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
	public String checkAndPerformCommandSubstitution(String args){
		CommandSubstitution data = new CommandSubstitution(args);
		ArrayList<String> tokens = data.check(args);
		for (int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			if (!notSingleQuote(token) && doubleBackQuote(token)) {
				if("``".equals(token)){
					token = "";
					tokens.remove(i);
					tokens.add(i, token);
				}else{
				tokens.set(i, performCommandSubstitutionWithException(token));
				}
			}
		}
		return String.join("", tokens);
	}

	public boolean notSingleQuote(String args){
		return (args.startsWith("'") && args.endsWith("'"));
	}
	public boolean doubleBackQuote(String args){
		return (args.startsWith("`") && args.endsWith("`"));
	}
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
}

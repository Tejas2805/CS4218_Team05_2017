package sg.edu.nus.comp.cs4218.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class CommandSubstitution {
	String args;
	public CommandSubstitution(String args){
		this.args = args;
	}
	
	public ArrayList<String> check(String args){
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern regex = Pattern.compile("('[^\\n']*')|(`[^\\n`]*`)|[^\\n'`]+|[^\\n]+");
		Matcher regexMatcher = regex.matcher(args);
		while (regexMatcher.find()) {
			if (regexMatcher.group(0).length()>0) {
				tokens.add(regexMatcher.group(0));
			} else if (regexMatcher.group(1).length()>0) {
				tokens.add(regexMatcher.group(1));
			} else if (regexMatcher.group(2).length()>0) {
                tokens.add(regexMatcher.group(2));
            } else {
				tokens.add(regexMatcher.group());
			}
		}
		return tokens;
	}
	public String process() throws AbstractApplicationException, ShellException{
		String[] cmd = new String[]{args};
		String[] resultArr = new String[cmd.length];
		System.arraycopy(cmd, 0, resultArr, 0, cmd.length);
		String pattern = "`([^\\n`]*)`";
		Pattern patternSub = Pattern.compile(pattern);
		for (int i = 0; i < cmd.length; i++) {
			Matcher matcherPattern = patternSub.matcher(cmd[i]);
			while (matcherPattern.find()) {
				String subStr = matcherPattern.group(1);
				OutputStream subOutputStream = new ByteArrayOutputStream();
				ShellImpl shell = new ShellImpl();
				shell.parseAndEvaluate(subStr, subOutputStream);

				ByteArrayOutputStream outByte = (ByteArrayOutputStream) subOutputStream;
				byte[] byteArray = outByte.toByteArray();
				String bqResult = new String(byteArray).replace("\n", "").replace("\r", "");
				String replacedStr = cmd[i].replace("`" + subStr + "`", bqResult);
				resultArr[i] = replacedStr;
			}
		}
		return String.join(" ",resultArr);
	}
}


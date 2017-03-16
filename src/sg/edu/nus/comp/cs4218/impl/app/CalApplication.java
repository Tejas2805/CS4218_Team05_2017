package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Locale;

import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.impl.app.cal.CallHelper;

public class CalApplication implements Cal{

	public static final String INVALID_ARG = "Invalid argument";
	public static final String SPACE = " ";
	public static final String THREE_SPACES = SPACE+SPACE+SPACE;
	public static final String FOUR_SPACES = THREE_SPACES+SPACE;
	public CallHelper callHelper = new CallHelper();
	
	
	//-m [[month] year]
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		
		String strArgs = "";
		for(String str:args){
			strArgs += str + "\n";
		}
		String output="";
		
		if(stdout == null)
		{
			throw new CalException("OutputStream not provided");
		}
		
		//Print current Month
		if(args == null || args.length == 0){
			output = printCal(strArgs);
		}
		boolean bStartWithMonday = callHelper.isStartWithMonday(args);
		
		//Print current Month starting With Monday
		if(args.length == 1){
			output = processOneArg(args, strArgs, bStartWithMonday);
		}
		//month and year
		else if(args.length == 2){
			output = processTwoArgs(args, strArgs, bStartWithMonday);
		}
		else if(args.length == 3){
			output = processThreeArgs(args, strArgs, output, bStartWithMonday);
		}
		try {
			stdout.write((output + "\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String processThreeArgs(String[] args, String strArgs, String output, boolean bStartWithMonday)
			throws CalException {
		String temp = output;
		if(bStartWithMonday){
			try{
				Integer.parseInt(args[1]);
				Integer.parseInt(args[2]);
				}catch(NumberFormatException ee){
					throw (CalException) new CalException(INVALID_ARG).initCause(ee);
			}
			temp = printCalForMonthYearMondayFirst(strArgs);
		}
			
		else{
			throw new CalException("Invalid arguments format");
		}
		return temp;
	}

	private String processOneArg(String[] args, String strArgs, boolean bStartWithMonday) throws CalException {
		String output;
		if(bStartWithMonday){
			output = printCalWithMondayFirst(strArgs);
		}
		//Year only
		else{
			
			try{
				Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				throw (CalException) new CalException(INVALID_ARG).initCause(e);
				
			}
			output = printCalForYear(strArgs);
			
		}
		return output;
	}

	private String processTwoArgs(String[] args, String strArgs, boolean bStartWithMonday) throws CalException {
		String output;
		if(bStartWithMonday){
			try{
				Integer.parseInt(args[1]);
			}catch(NumberFormatException ee){
				throw (CalException) new CalException(INVALID_ARG).initCause(ee);
			}

			output = printCalForYearMondayFirst(strArgs);
		}else{

			try{
				Integer.parseInt(args[0]);
				Integer.parseInt(args[1]);
				}catch(NumberFormatException ee){
					throw (CalException) new CalException(INVALID_ARG).initCause(ee);
				}

				output = printCalForMonthYear(strArgs);
		}
		return output;
	}

	
	@Override
	public String printCal(String args) {
		
		String[] output = new String[7];
		int[][] dates = new int[5][7];
		
		//Get now
		LocalDate today = LocalDate.now();
		//Get Month
		String month = callHelper.formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		
		output[0] = FOUR_SPACES + month + SPACE+  year + FOUR_SPACES;
		output[1] = "Su Mo Tu We Th Fr Sa ";
		
		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth()-1);
		
		int dayOfWeek = startOfMonth.getDayOfWeek().getValue();
		dayOfWeek = dayOfWeek == 7 ? 0: dayOfWeek;
		int day = 1;
		int maxDays = startOfMonth.lengthOfMonth();
		//for first row
		for(int col = dayOfWeek; col < 7; col++)
		{
			dates[0][col] = day++;
		}
		day = callHelper.addDays(dates, day, maxDays);
		
		callHelper.loopForMonthYear(dates,output);
		
		String realOutput = "";
		for(String str: output){
			realOutput += str + "\n";
		}
			
		
		return realOutput;
	}

	@Override
	public String printCalWithMondayFirst(String args) {
		String[] output = new String[7];
		int[][] dates = new int[5][7];
		
		//Get now
		LocalDate today = LocalDate.now();
		
		//Get Month
		String month = callHelper.formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		
		output[0] = FOUR_SPACES+month + " " + year + FOUR_SPACES;
		output[1] = "Mo Tu We Th Fr Sa Su ";
		
		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth()-1);
		
		int dayOfWeek = startOfMonth.getDayOfWeek().getValue() - 1;
		int day = 1;
		int maxDays = startOfMonth.lengthOfMonth();
		//for first row
		for(int col = dayOfWeek; col < 7; col++)
		{
			dates[0][col] = day++;
		}
		day = callHelper.addDays(dates, day, maxDays);
		
		callHelper.loopForMonthYear(dates,output);
		
		String realOutput = "";
		for(String str: output){
			realOutput += str + "\n";
		}
			
		
		return realOutput;
	}

	@Override
	public String printCalForMonthYear(String args) {
		String[] strArgs = args.split("\n");
		int imonth = Integer.parseInt(strArgs[0]);
		int iyear = Integer.parseInt(strArgs[1]);
		
		String[] output = new String[7];
		int[][] dates = new int[5][7];
		
		//Get now
		LocalDate today = LocalDate.now();
		int currentMonth = today.getMonthValue();
		int currentYear = today.getYear();
		today = today.minusMonths(currentMonth - imonth).minusYears(currentYear - iyear);
		//Get Month
		String month = callHelper.formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		
		output[0] = FOUR_SPACES+month + " " + year + FOUR_SPACES;
		output[1] = "Su Mo Tu We Th Fr Sa ";
		
		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth()-1);
		
		int dayOfWeek = startOfMonth.getDayOfWeek().getValue();
		dayOfWeek = dayOfWeek == 7 ? 0: dayOfWeek;
		int day = 1;
		int maxDays = startOfMonth.lengthOfMonth();
		//for first row
		for(int col = dayOfWeek; col < 7; col++)
		{
			dates[0][col] = day++;
		}
		day = callHelper.addDays(dates, day, maxDays);
		
		callHelper.loopForMonthYear(dates,output);
		
		String realOutput = "";
		for(String str: output){
			realOutput += str + "\n";
		}
			
		
		return realOutput;
	}

	
	@Override
	public String printCalForYear(String args) {
		String[] strArgs = args.split("\n");
		int iyear = Integer.parseInt(strArgs[0]);
		
		String[] calendar = new String[12];
		for(int i = 0; i < calendar.length; i++){
			calendar[i] = printCalForMonthYear(i+1+"\n" + iyear + "\n");
		}
		
		String[] output = new String[7*4];
		
		for(int row = 0; row < 4; row ++)
		{
			for(int i = row*3; i < 3*row+3; i++){
				String aMonthCalendar = calendar[i];
				String[] arrMthCal = aMonthCalendar.split("\n");
				for(int j = 0; j < arrMthCal.length; j++){
					
					if(output[row*7+j] == null || output[row*7+j].equals("")){
						output[row*7+j] = arrMthCal[j];
					}else{
						output[row*7+j] += THREE_SPACES + arrMthCal[j];
					}
				}
			}
			
		}
		
		String realOutput = "";
		for(String str: output){
			realOutput += str + "\n";
		}
			
		
		return realOutput;
	}

	@Override
	public String printCalForMonthYearMondayFirst(String args) {
		String[] strArgs = args.split("\n");
		int imonth = Integer.parseInt(strArgs[1]);
		int iyear = Integer.parseInt(strArgs[2]);
		
		String[] output = new String[7];
		int[][] dates = new int[5][7];
		
		//Get now
		LocalDate today = LocalDate.now();
		int currentMonth = today.getMonthValue();
		int currentYear = today.getYear();
		today = today.minusMonths(currentMonth - imonth).minusYears(currentYear - iyear);
		//Get Month
		String month = callHelper.formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		output[0] = FOUR_SPACES+month + " " + year + FOUR_SPACES;
		output[1] = "Mo Tu We Th Fr Sa Su ";
		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth()-1);
		int dayOfWeek = startOfMonth.getDayOfWeek().getValue()-1;
		int day = 1;
		int maxDays = startOfMonth.lengthOfMonth();
		for(int col = dayOfWeek; col < 7; col++)
		{
			dates[0][col] = day++;
		}
		day = callHelper.addDays(dates, day, maxDays);
		callHelper.loopForMonthYear(dates,output);
		String realOutput = "";
		for(String str: output){
			realOutput += str + "\n";
		}
		return realOutput;
	}

	
	@Override
	public String printCalForYearMondayFirst(String args) {
		String[] strArgs = args.split("\n");
		int iyear = Integer.parseInt(strArgs[1]);
		
		String[] calendar = new String[12];
		for(int i = 0; i < calendar.length; i++){
			calendar[i] = printCalForMonthYearMondayFirst("-m\n" + (i+1)+"\n" + iyear + "\n");
		}
		
		String[] output = new String[7*4];
		
		for(int row = 0; row < 4; row ++)
		{
			for(int i = row*3; i < 3*row+3; i++){
				String aMonthCalendar = calendar[i];
				String[] arrMthCal = aMonthCalendar.split("\n");
				for(int j = 0; j < arrMthCal.length; j++){
					
					if(output[row*7+j] == null || output[row*7+j].equals("")){
						output[row*7+j] = arrMthCal[j];
					}else{
						output[row*7+j] += THREE_SPACES + arrMthCal[j];
					}
				}
			}
			
		}
		
		String realOutput = "";
		for(String str: output){
			realOutput += str + "\n";
		}
			
		
		return realOutput;
	}

	
}

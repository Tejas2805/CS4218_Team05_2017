package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.exception.CatException;

public class CalApplication implements Cal{


	
	private boolean isStartWithMonday(String[] args)
	{
		for(String str: args)
			if(str.compareTo("-m") == 0)
				return true;
		return false;
	}
	
	//-m [[month] year]
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		
		String strArgs = "";
		for(String str:args)
			strArgs += str + "\n";
		
		String output="";
		
		if(stdout == null)
		{
			throw new CalException("OutputStream not provided");
		}
		
		//Print current Month
		if(args == null || args.length == 0){
			output = printCal(strArgs);
		}
		boolean bStartWithMonday = isStartWithMonday(args);
		
		//Print current Month starting With Monday
		if(args.length == 1){
			if(bStartWithMonday){
				output = printCalWithMondayFirst(strArgs);
			}
			//Year only
			else{
				
				try{
					Integer.parseInt(args[0]);
				}catch(NumberFormatException e){
					throw new CalException("Invalid year");
				}
				output = printCalForYear(strArgs);
				
			}
		}
		//month and year
		else if(args.length == 2){
			
			
			if(bStartWithMonday==false){
				try{
				Integer.parseInt(args[0]);
				Integer.parseInt(args[1]);
				}catch(NumberFormatException ee){
					throw new CalException("Invalid month/year");
					
				}

				output = printCalForMonthYear(strArgs);
			}else{
				try{
					Integer.parseInt(args[1]);
				}catch(NumberFormatException ee){
						throw new CalException("Invalid year");
						
				}

				output = printCalForYearMondayFirst(strArgs);
			}
		}
		else if(args.length == 3){
			if(bStartWithMonday == false)
				throw new CalException("Invalid arguments format");
			else{
				try{
					Integer.parseInt(args[1]);
					Integer.parseInt(args[2]);
					}catch(NumberFormatException ee){
						throw new CalException("Invalid month/year");
						
				}
				output = printCalForMonthYearMondayFirst(strArgs);
			}
		}
				
	
		
		try {
			stdout.write((output + "\n").getBytes());
		} catch (IOException e) {
			throw new CalException("Exception Caught");
		}
	}

	
	@Override
	public String printCal(String args) {
		
		String[] output = new String[7];
		int[][] dates = new int[5][7];
		
		//Get now
		LocalDate today = LocalDate.now();
		//Get Month
		String month = formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		
		output[0] = "    "+month + " " + year + "    ";
		output[1] = "Su Mo Tu We Th Fr Sa";
		
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
		for(int row = 1; row < 5; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				if(day > maxDays)
					break;
				dates[row][col] = day++;
				
			}
		}
		
		for(int row = 0; row < 5; row++)
		{
			String s = "";
			for(int col = 0; col < 7; col++)
			{
				
				if(dates[row][col] == 0)
					s += "   ";
				else{
					if(dates[row][col]> 9)
						s += String.valueOf(dates[row][col]) + " ";
					else
						s += String.valueOf(dates[row][col]) + "  ";
				}
					
				
			}
			output[row+2] = s;
		}
		
		String realOutput = "";
		for(String str: output)
			realOutput += str + "\n";
		
		return realOutput;
	}

	@Override
	public String printCalWithMondayFirst(String args) {
		String[] output = new String[7];
		int[][] dates = new int[5][7];
		
		//Get now
		LocalDate today = LocalDate.now();
		
		//Get Month
		String month = formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		
		output[0] = "    "+month + " " + year + "    ";
		output[1] = "Mo Tu We Th Fr Sa Su";
		
		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth()-1);
		
		int dayOfWeek = startOfMonth.getDayOfWeek().getValue() - 1;
		int day = 1;
		int maxDays = startOfMonth.lengthOfMonth();
		//for first row
		for(int col = dayOfWeek; col < 7; col++)
		{
			dates[0][col] = day++;
		}
		for(int row = 1; row < 5; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				if(day > maxDays)
					break;
				dates[row][col] = day++;
				
			}
		}
		
			for(int row = 0; row < 5; row++)
			{
				String s = "";
				for(int col = 0; col < 7; col++)
				{
					
					if(dates[row][col] == 0){
						s += "   ";
					}
						
					else{
						if(dates[row][col]> 9){
							s += String.valueOf(dates[row][col]) + " ";
						}
							
						else{
							s += String.valueOf(dates[row][col]) + "  ";
						}
							
					}
						
					
				}
				output[row+2] = s;
			}
		
		String realOutput = "";
		for(String str: output)
			realOutput += str + "\n";
		
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
		String month = formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		
		output[0] = "    "+month + " " + year + "    ";
		output[1] = "Su Mo Tu We Th Fr Sa";
		
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
		for(int row = 1; row < 5; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				if(day > maxDays)
					break;
				dates[row][col] = day++;
				
			}
		}
		
		for(int row = 0; row < 5; row++)
		{
			String s = "";
			for(int col = 0; col < 7; col++)
			{
				
				if(dates[row][col] == 0)
					s += "   ";
				else{
					if(dates[row][col]> 9)
						s += String.valueOf(dates[row][col]) + " ";
					else
						s += String.valueOf(dates[row][col]) + "  ";
				}
					
				
			}
			output[row+2] = s;
		}
		
		String realOutput = "";
		for(String str: output)
			realOutput += str + "\n";
		
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
				String[] splitAMonthCalendar = aMonthCalendar.split("\n");
				for(int j = 0; j < splitAMonthCalendar.length; j++){
					
					if(output[row*7+j] == null || output[row*7+j] == ""){
						output[row*7+j] = splitAMonthCalendar[j];
					}else{
						output[row*7+j] += "   " + splitAMonthCalendar[j];
					}
				}
			}
			
		}
		
		String realOutput = "";
		for(String str: output)
			realOutput += str + "\n";
		
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
		String month = formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		
		output[0] = "    "+month + " " + year + "    ";
		output[1] = "Mo Tu We Th Fr Sa Su";
		
		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth()-1);
		
		int dayOfWeek = startOfMonth.getDayOfWeek().getValue()-1;
		int day = 1;
		int maxDays = startOfMonth.lengthOfMonth();
		//for first row
		for(int col = dayOfWeek; col < 7; col++)
		{
			dates[0][col] = day++;
		}
		for(int row = 1; row < 5; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				if(day > maxDays)
					break;
				dates[row][col] = day++;
				
			}
		}
		
		for(int row = 0; row < 5; row++)
		{
			String s = "";
			for(int col = 0; col < 7; col++)
			{
				
				if(dates[row][col] == 0)
					s += "   ";
				else{
					if(dates[row][col]> 9){
						s += String.valueOf(dates[row][col]) + " ";
					}
						
					else{
						s += String.valueOf(dates[row][col]) + "  ";
					}
						
				}
					
				
			}
			output[row+2] = s;
		}
		
		String realOutput = "";
		for(String str: output)
			realOutput += str + "\n";
		
		return realOutput;
	}

	@Override
	public String printCalForYearMondayFirst(String args) {
		String[] strArgs = args.split("\n");
		int iyear = Integer.parseInt(strArgs[1]);
		
		String[] calendar = new String[12];
		for(int i = 0; i < calendar.length; i++){
			calendar[i] = printCalForMonthYearMondayFirst("-m\n" + i+1+"\n" + iyear + "\n");
		}
		
		String[] output = new String[7*4];
		
		for(int row = 0; row < 4; row ++)
		{
			for(int i = row*3; i < 3*row+3; i++){
				String aMonthCalendar = calendar[i];
				String[] splitAMonthCalendar = aMonthCalendar.split("\n");
				for(int j = 0; j < splitAMonthCalendar.length; j++){
					
					if(output[row*7+j] == null || output[row*7+j] == ""){
						output[row*7+j] = splitAMonthCalendar[j];
					}else{
						output[row*7+j] += "   " + splitAMonthCalendar[j];
					}
				}
			}
			
		}
		
		String realOutput = "";
		for(String str: output)
			realOutput += str + "\n";
		
		return realOutput;
	}

	private String formatMonth(String strMonth)
	{
		String formattedMonth = "";
		
		char[] charArrayMonth = strMonth.toCharArray();

		//Change first letter to upper case
		formattedMonth += (charArrayMonth[0] + "").toUpperCase();
		
		for(int i = 1; i < charArrayMonth.length; i++){
			formattedMonth += (charArrayMonth[i] + "").toLowerCase();
		}
		
		return formattedMonth;
	}
}

package sg.edu.nus.comp.cs4218.impl.app.cal;

import java.time.LocalDate;

import sg.edu.nus.comp.cs4218.impl.app.cal.CallHelper;

public class PrintCalendar {

	public CallHelper callHelper = new CallHelper();
	public static final String SPACE = " ";
	public static final String THREE_SPACES = SPACE + SPACE + SPACE;
	public static final String FOUR_SPACES = THREE_SPACES + SPACE;
	// public PrintCalendar(){}

	public String printCalForMonthYearMondayFirst(String args) {
		String[] strArgs = args.split("\n");
		int imonth = Integer.parseInt(strArgs[1]);
		int iyear = Integer.parseInt(strArgs[2]);

		String[] output = new String[7];
		int[][] dates = new int[5][7];

		LocalDate today = LocalDate.now();
		int currentMonth = today.getMonthValue();
		int currentYear = today.getYear();
		today = today.minusMonths(currentMonth - imonth).minusYears(currentYear - iyear);
		// Get Month
		String month = callHelper.formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());
		output[0] = FOUR_SPACES + month + " " + year + FOUR_SPACES;
		output[1] = "Mo Tu We Th Fr Sa Su ";
		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth() - 1);
		int dayOfWeek = startOfMonth.getDayOfWeek().getValue() - 1;
		int day = 1;
		int maxDays = startOfMonth.lengthOfMonth();
		for (int col = dayOfWeek; col < 7; col++) {
			dates[0][col] = day++;
		}
		day = callHelper.addDays(dates, day, maxDays);
		callHelper.loopForMonthYear(dates, output);
		String realOutput = "";
		for (String str : output) {
			realOutput += str + "\n";
		}
		return realOutput;
	}

	public String printCalForYearMondayFirst(String args) {
		String[] strArgs = args.split("\n");
		int iyear = Integer.parseInt(strArgs[1]);

		String[] calendar = new String[12];
		for (int i = 0; i < calendar.length; i++) {
			calendar[i] = printCalForMonthYearMondayFirst("-m\n" + (i + 1) + "\n" + iyear + "\n");
		}

		String[] output = new String[7 * 4];

		for (int row = 0; row < 4; row++) {
			for (int i = row * 3; i < 3 * row + 3; i++) {
				String aMonthCalendar = calendar[i];
				String[] arrMthCal = aMonthCalendar.split("\n");
				for (int j = 0; j < arrMthCal.length; j++) {

					if (output[row * 7 + j] == null || output[row * 7 + j].equals("")) {
						output[row * 7 + j] = arrMthCal[j];
					} else {
						output[row * 7 + j] += THREE_SPACES + arrMthCal[j];
					}
				}
			}

		}

		String realOutput = "";
		for (String str : output) {
			realOutput += str + "\n";
		}

		return realOutput;
	}

	public String printCalForYear(String args) {
		String[] strArgs = args.split("\n");
		int iyear = Integer.parseInt(strArgs[0]);

		String[] calendar = new String[12];
		for (int i = 0; i < calendar.length; i++) {
			calendar[i] = printCalForMonthYear(i + 1 + "\n" + iyear + "\n");
		}

		String[] output = new String[7 * 4];

		for (int row = 0; row < 4; row++) {
			for (int i = row * 3; i < 3 * row + 3; i++) {
				String aMonthCalendar = calendar[i];
				String[] arrMthCal = aMonthCalendar.split("\n");
				for (int j = 0; j < arrMthCal.length; j++) {

					if (output[row * 7 + j] == null || output[row * 7 + j].equals("")) {
						output[row * 7 + j] = arrMthCal[j];
					} else {
						output[row * 7 + j] += THREE_SPACES + arrMthCal[j];
					}
				}
			}

		}

		String realOutput = "";
		for (String str : output) {
			realOutput += str + "\n";
		}

		return realOutput;
	}

	public String printCalForMonthYear(String args) {
		String[] strArgs = args.split("\n");
		int imonth = Integer.parseInt(strArgs[0]);
		int iyear = Integer.parseInt(strArgs[1]);

		String[] output = new String[7];
		int[][] dates = new int[5][7];

		// Get now
		LocalDate today = LocalDate.now();
		int currentMonth = today.getMonthValue();
		int currentYear = today.getYear();
		today = today.minusMonths(currentMonth - imonth).minusYears(currentYear - iyear);
		// Get Month
		String month = callHelper.formatMonth(today.getMonth().toString());
		String year = String.valueOf(today.getYear());

		output[0] = FOUR_SPACES + month + " " + year + FOUR_SPACES;
		output[1] = "Su Mo Tu We Th Fr Sa ";

		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth() - 1);

		int dayOfWeek = startOfMonth.getDayOfWeek().getValue();
		dayOfWeek = dayOfWeek == 7 ? 0 : dayOfWeek;
		int day = 1;
		int maxDays = startOfMonth.lengthOfMonth();
		// for first row
		for (int col = dayOfWeek; col < 7; col++) {
			dates[0][col] = day++;
		}
		day = callHelper.addDays(dates, day, maxDays);

		callHelper.loopForMonthYear(dates, output);

		String realOutput = "";
		for (String str : output) {
			realOutput += str + "\n";
		}

		return realOutput;
	}
}

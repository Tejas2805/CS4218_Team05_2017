package sg.edu.nus.comp.cs4218.impl.app.cal;

import java.util.Locale;

public class CallHelper {

	private static final String THREE_SPACES = "   ";

	public boolean isStartWithMonday(String... args) {
		for (String str : args) {
			if (str.compareTo("-m") == 0) {
				return true;
			}
		}

		return false;
	}

	public int addDays(int[][] dates, int day, int maxDays) {
		int[][] temp = dates;
		int dayTemp = day;
		for (int row = 1; row < 5; row++) {
			for (int col = 0; col < 7; col++) {
				if (dayTemp > maxDays) {
					break;
				}

				temp[row][col] = dayTemp++;

			}
		}
		return dayTemp;
	}

	public void loopForMonthYear(int[][] dates, String... output) {
		for (int row = 0; row < 5; row++) {
			String str = "";
			for (int col = 0; col < 7; col++) {

				if (dates[row][col] == 0) {
					str += THREE_SPACES;
				}

				else {
					if (dates[row][col] > 9) {
						str += String.valueOf(dates[row][col]) + " ";
					}

					else {
						str += String.valueOf(dates[row][col]) + "  ";
					}

				}

			}
			output[row + 2] = str;
		}
	}

	public String formatMonth(String strMonth) {
		String formattedMonth = "";

		char[] charArrayMonth = strMonth.toCharArray();

		// Change first letter to upper case
		formattedMonth += (charArrayMonth[0] + "").toUpperCase(Locale.ENGLISH);

		for (int i = 1; i < charArrayMonth.length; i++) {
			formattedMonth += (charArrayMonth[i] + "").toLowerCase(Locale.ENGLISH);
		}

		return formattedMonth;
	}
}

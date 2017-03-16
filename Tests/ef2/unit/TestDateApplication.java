package ef2.unit;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.DateException;
import sg.edu.nus.comp.cs4218.impl.app.DateApplication;

public class TestDateApplication {

	DateApplication dataApp;

	@Before
	public void setup(){
		dataApp = new DateApplication();
	}

	@Test
	public void testPrintCurrentDate() throws DateException {
		LocalDateTime now = LocalDateTime.now();
		Calendar calendar = Calendar.getInstance();

		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();
		int second = now.getSecond();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		String[] strDays = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		String[] strMonths = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

		TimeZone timeZone = TimeZone.getTimeZone("Asia/Singapore");
		String strTimeZone = timeZone.getDisplayName(false, TimeZone.SHORT);
		
		String expectedResults = strDays[dayOfWeek -1] + " " + strMonths[month-1] + " " + String.format("%02d",day) + 
	    		" " + String.format("%02d",hour) + ":" + String.format("%02d",minute) + ":" + String.format("%02d",second) + 
	    		" " + strTimeZone + " "  + year + System.lineSeparator();
		
		String args = "";
		String actualResults = dataApp.printCurrentDate(args);
		
		assertEquals(expectedResults, actualResults);
	}

	@After
	public void tearDown(){
		dataApp = null;
	}

}

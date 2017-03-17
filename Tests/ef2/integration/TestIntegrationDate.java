package ef2.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DateException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationDate {

	ShellImpl shellImpl;
	OutputStream stdout;
	
	@Before
    public void setUp() {
		shellImpl = new ShellImpl();
		stdout = new ByteArrayOutputStream();
	}
	
	@Test
	public void testPrintCurrentDate() throws AbstractApplicationException, ShellException {
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
			
		String cmdline = "date";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		expectedResults = stdout.toString();
		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test (expected = DateException.class)
	public void testInvalidtArgs() throws AbstractApplicationException, ShellException {
		String cmdline = "date hello.txt";
		shellImpl.parseAndEvaluate(cmdline, stdout);
	}

	
	@After
	public void tearDown() throws Exception {
		shellImpl = null;
	}

	

}

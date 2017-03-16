package ef2.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import sg.edu.nus.comp.cs4218.impl.app.wc.WcOption;

public class TestWcOption {

	WcOption wcOpton;
	String line = "-l";
	String word = "-w";
	String mChar = "-m";
	String lineWord = "-lw";
	String lineMchar = "-lm";
	String wordMchar = "-wm";
	String lineWordMchar = "-lwm";
	
	@Before
	public void setup(){
		wcOpton = new WcOption();
	}
	
	@Test
	public void testProcessArgsOption() {
		
		
		String[] args1 = {line};
		assertEquals(line, wcOpton.processArgsOption(args1));
		String[] args2 = {word};
		assertEquals(word, wcOpton.processArgsOption(args2));
		String[] args3 = {mChar};
		assertEquals(mChar, wcOpton.processArgsOption(args3));
		String[] args4 = {line, word};
		assertEquals(lineWord, wcOpton.processArgsOption(args4));
		String[] args5 = {line, mChar};
		assertEquals(lineMchar, wcOpton.processArgsOption(args5));
		String[] args6 = {word, mChar};
		assertEquals(wordMchar, wcOpton.processArgsOption(args6));
		String[] args7 = {mChar, line};
		assertEquals(lineMchar, wcOpton.processArgsOption(args7));
		String[] args8 = {line, word, mChar};
		assertEquals(lineWordMchar, wcOpton.processArgsOption(args8));
		String[] args9 = {mChar, "-1", word};
		assertEquals(wordMchar, wcOpton.processArgsOption(args9));
		String[] args10 = {"-n", word, "-1", line};
		assertEquals(lineWord, wcOpton.processArgsOption(args10));
		String[] args11 = {"-n", word, "-1", line, "-ml"};
		assertEquals(lineWordMchar, wcOpton.processArgsOption(args11));
		String[] args12 = {"-n", "-1", "-wl", };
		assertEquals(lineWord, wcOpton.processArgsOption(args12));
		String[] args13 = {"-n", word, "-1", line, "-mlw"};
		assertEquals(lineWordMchar, wcOpton.processArgsOption(args13));
		
	}
	
	@Test
	public void testIsValidOption() {
		assertTrue(wcOpton.isValidOption("-l"));
		assertTrue(wcOpton.isValidOption("-w"));
		assertTrue(wcOpton.isValidOption("-m"));
		assertTrue(wcOpton.isValidOption("-lw"));
		assertTrue(wcOpton.isValidOption("-wl"));
		assertTrue(wcOpton.isValidOption("-lm"));
		assertTrue(wcOpton.isValidOption("-ml"));
		assertTrue(wcOpton.isValidOption("-wm"));
		assertTrue(wcOpton.isValidOption("-mw"));
		assertTrue(wcOpton.isValidOption("-lwm"));
		assertTrue(wcOpton.isValidOption("-lmw"));
		assertTrue(wcOpton.isValidOption("-wlm"));
		assertTrue(wcOpton.isValidOption("-wml"));
		assertTrue(wcOpton.isValidOption("-mlw"));
		assertTrue(wcOpton.isValidOption("-mwl"));
		assertFalse(wcOpton.isValidOption("-"));
		assertFalse(wcOpton.isValidOption("l"));
		assertFalse(wcOpton.isValidOption("w"));
		assertFalse(wcOpton.isValidOption("m"));
		assertFalse(wcOpton.isValidOption("- l"));
		assertFalse(wcOpton.isValidOption("-n"));
	}
	
	@After
	public void tearDown(){
		wcOpton = null;
	}

}

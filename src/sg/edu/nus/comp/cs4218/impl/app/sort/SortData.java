package sg.edu.nus.comp.cs4218.impl.app.sort;

import java.io.PrintStream;

public class SortData {

	SortCheck sortCheck = new SortCheck();

	/**
	 * @param data
	 *            contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a special character
	 */
	public String getSpecialCharData(String data) {
		String[] dataAarr = data.split(System.lineSeparator());
		String specialCharData = "";
		for (String line : dataAarr) {
			if (line.isEmpty()) {
				continue;
			}

			char[] charArr = line.toCharArray();
			if (sortCheck.isSpecialChar(charArr[0])) {
				specialCharData += line + System.lineSeparator();
			}

		}
		return specialCharData;
	}

	/**
	 * @param data
	 *            contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a number
	 */
	public String getNumberData(String toSort) {
		String[] args = toSort.split(System.lineSeparator(), 2);
		String sortCondtion = args[0];
		String data = args[1];
		String[] dataAarr = data.split(System.lineSeparator());
		String numberData = "";

		for (String line : dataAarr) {
			if (line.isEmpty()) {
				continue;
			}
			if ("-n".equals(sortCondtion)) {
				if (sortCheck.isFirstWordNum(line)) {
					numberData += line + System.lineSeparator();
				}
			} else {
				char[] charArr = line.toCharArray();
				if (charArr[0] >= 48 && charArr[0] <= 57) {
					numberData += line + System.lineSeparator();
				}
			}

		}
		return numberData;
	}

	/**
	 * @param data
	 *            contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a capital Letter
	 */
	public String getCapitalData(String data) {
		String[] dataAarr = data.split(System.lineSeparator());
		String capitalData = "";

		for (String line : dataAarr) {
			if (line.isEmpty()) {
				continue;
			}
			char[] charArr = line.toCharArray();
			if (charArr[0] >= 65 && charArr[0] <= 90) {
				capitalData += line + System.lineSeparator();
			}
		}
		return capitalData;
	}

	/**
	 * @param data
	 *            contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a simple letter
	 */
	public String getSimpleData(String data) {
		String[] dataAarr = data.split(System.lineSeparator());
		String simpleData = "";

		for (String line : dataAarr) {
			if (line.isEmpty()) {
				continue;
			}
			char[] charArr = line.toCharArray();
			if (charArr[0] >= 97 && charArr[0] <= 122) {
				simpleData += line + System.lineSeparator();
			}
		}
		return simpleData;
	}
}

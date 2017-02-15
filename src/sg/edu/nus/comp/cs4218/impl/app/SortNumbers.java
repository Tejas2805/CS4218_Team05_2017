package sg.edu.nus.comp.cs4218.impl.app;

import java.util.ArrayList;

public class SortNumbers {
	/**
	 * @param dataList ArrayList containing lines to be sorted in numerical order
	 * @return ArrayList<String> data sorted in numerical order
	 */
	
	public ArrayList<String> sortByNum(ArrayList<String> numList){

		String oldLine = "";
		String newLine = "";
		ArrayList<String> numFileList = numList;

		int fileSize = numFileList.size();

		for(int i=0; i < fileSize; i++){
			boolean flag = true;

			for(int j=0; j < (fileSize - i -1 ); j++){

				oldLine = numFileList.get(j);
				newLine = numFileList.get(j+1);

				int oldLineValue = Integer.parseInt(oldLine.split(" ")[0]); 
				int newLineValue = Integer.parseInt(newLine.split(" ")[0]); 

				if(oldLineValue > newLineValue){
					flag = false;
					numFileList.set(j, newLine);
					numFileList.set(j+1, oldLine);
				}

				if(oldLineValue == newLineValue){
					numFileList = sortSameNum(numFileList, oldLine, newLine, j);
				}
			}
			if (flag) {
				break;
			}	
		}
		return numFileList;
	}

	/**
	 * This methods sort the line in ascending ASCII value order if the first word of two lines have the same number
	 * @param numList ArrayList containing lines to be sorted in numerical order
	 * @param firstLine the second first line
	 * @param secondLine the second line
	 * @param index the index to place the line in the arraylist
	 * @return ArrayList<String> data sorted in numerical order follows by ascending ASCII value if the first two words are same number
	 */
	private ArrayList<String> sortSameNum(ArrayList<String> numList, String firstLine, String secondLine, int index){
		ArrayList<String> numFileList = numList;
		int indexOldLine= firstLine.trim().indexOf(" ");
		int indexNewLine = secondLine.trim().indexOf(" ");

		if(indexOldLine == -1){
			numFileList.set(index, firstLine);
			numFileList.set(index+1, secondLine);
		}else if(indexNewLine == -1){
			numFileList.set(index, firstLine);
			numFileList.set(index+1, secondLine);
		}else{
			String noFirstOldLine = firstLine.substring(indexOldLine+1);
			String noFirstNewLine = secondLine.substring(indexNewLine+1);

			char[] asciiOldLine = noFirstOldLine.toCharArray();
			char[] asciiNewLine = noFirstNewLine.toCharArray();

			int shorterLineLen = Math.min(noFirstOldLine.length(), noFirstNewLine.length());

			for(int k=0; k < shorterLineLen; k++){			
				int asciiOldLineValue =  asciiNumAlpha(asciiOldLine[k]);
				int asciiNewLineValue =  asciiNumAlpha(asciiNewLine[k]);

				if(asciiOldLineValue < asciiNewLineValue){
					numFileList.set(index, firstLine);
					numFileList.set(index+1, secondLine);
					break;
				}
				if(asciiOldLineValue > asciiNewLineValue){
					numFileList.set(index+1, firstLine);
					numFileList.set(index, secondLine);
					break;
				}
			}
		}	

		return numFileList;
	}
	
	/**
	 * This methods increase the ASCII value of number (0-9), capital (A-Z) & simple (a-z)
	 * @param char the character to be compared
	 * @return int the increase ACCI value multiply by 10
	 */
	private int asciiNumAlpha(char asciiValue){

		if(asciiValue >= 48 && asciiValue <= 59){
			return asciiValue * 10;
		}
		if(asciiValue >= 65 && asciiValue <= 90){
			return asciiValue * 10;
		}
		if(asciiValue >= 97 && asciiValue <= 122){
			return asciiValue * 10;
		}

		return asciiValue;
	}

}

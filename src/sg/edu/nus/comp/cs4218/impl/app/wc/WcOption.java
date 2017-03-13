package sg.edu.nus.comp.cs4218.impl.app.wc;


public class WcOption {
	/*
	 * Process the determine the valid option present in the args[] array
	 * @param args array of args
	 * return the string option, -l, -w, -m, -lw, -lm, -wm, -lwm
	 */
	public String processArgsOption(String... args){
		String option = "-lwm";
		boolean isLine = false, isWord = false, isMchar = false;
		
		for(int i=0; i<args.length; i++){
			if(args[i].charAt(0) == '-'){
				
				if(args[i].length() == 2 && args[i].charAt(1) == 'l'){ 
					isLine = true;
				}
				if(args[i].length() == 2 && args[i].charAt(1) == 'w'){
					isWord = true;
				}
				if(args[i].length() == 2 && args[i].charAt(1) == 'm'){
					isMchar = true;
				}
				
				
				if((args[i].length() == 3) && ((args[i].charAt(1) == 'l' && args[i].charAt(2) == 'w') || (args[i].charAt(1) == 'w' && args[i].charAt(2) == 'l'))){
					isLine = true;
					isWord = true;
				}
				if((args[i].length() == 3) && ((args[i].charAt(1) == 'l' && args[i].charAt(2) == 'm') || (args[i].charAt(1) == 'm' && args[i].charAt(2) == 'l'))){
					isLine = true;
					isMchar = true;
				}
				if((args[i].length() == 3) && ((args[i].charAt(1) == 'w' && args[i].charAt(2) == 'm') || (args[i].charAt(1) == 'm' && args[i].charAt(2) == 'w'))){
					isWord = true;
					isMchar = true;
				}
				
				if(args[i].length() == 4 && isOptionLWM(args[i])){
					return "-lwm";
				}
			}
		}
		option = getOption(isLine, isWord, isMchar);
		return option;
	}
	
	/*
	 * Check if a option is a valid option
	 * @param option
	 * return true if it is a valid option
	 */
	public boolean isValidOption(String option){
		String optionResults = processArgsOption(option);
		if("-".equals(optionResults)){
			return false;
		}
		if("-lwm".equals(optionResults)){
			return isOptionLWM(option);
		}
		return true;
	}
	
	/*
	 * Return the correct option combination
	 * @param isLine boolean value true/false
	 * @param isWord boolean value true/false
	 * @param isMchar boolean value true/false
	 * return either -l, -w, -m, -lw, -lm, -wm, -lwm
	 */
	private String getOption(boolean isLine, boolean isWord, boolean isMchar){
		String option = "-";
		if(isLine){
			option += "l";
		}
		if(isWord){
			option += "w";
		}
		if(isMchar){
			option += "m";
		}
		return option;
	}
	/*
	 * Check if the option is made up of '-' followed by a combination of 'l' , 'w', 'm'
	 * @param option
	 * return true is the args is made up of '-' followed by a combination of 'l' , 'w', 'm'
	 */
	private boolean isOptionLWM(String option){
		int lineVal = 0, wordVal = 0, mCharVal = 0; 
		if(option.charAt(1) == 'l' ||  option.charAt(2) == 'l' || option.charAt(3) == 'l'){
			lineVal += 1;
		}
		if(option.charAt(1) == 'w' ||  option.charAt(2) == 'w' || option.charAt(3) == 'w'){
			wordVal += 1;
		}
		if(option.charAt(1) == 'm' ||  option.charAt(2) == 'm' || option.charAt(3) == 'm'){
			mCharVal += 1;
		}
		
		return (lineVal == 1 && wordVal == 1 && mCharVal == 1);
		
	}
}

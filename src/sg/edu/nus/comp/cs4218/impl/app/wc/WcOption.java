package sg.edu.nus.comp.cs4218.impl.app.wc;


public class WcOption {
	/*
	 * Process the determine the valid option present in the args[] array
	 * @param args array of args
	 * return the string option, -l, -w, -m, -lw, -lm, -wm, -lwm
	 */
	public String processArgsOption(String... args){
		String option = "-lwm";
		boolean isLine = false, isWord = false, isMchar = false, isIllegal = false, isLegal = false;
		boolean tempIsLine = false, tempIsWord = false, tempIsMchar = false, tempIsIllegal = false;
		
		for(int i=0; i<args.length; i++){
			tempIsIllegal = false;
			if(args[i].charAt(0) == '-'){
				for(int j=1; j<args[i].length(); j++){
					if(args[i].charAt(j) == 'l'){
						tempIsLine = true;
					}else if(args[i].charAt(j) == 'w'){
						tempIsWord = true;
					}else if(args[i].charAt(j) == 'm'){
						tempIsMchar = true;
					}else{
						tempIsIllegal = true;
						isIllegal = true;
					}
				}
				
				if(!tempIsIllegal){
					isLegal = true;
					if(tempIsLine){
						isLine = true;
					}
					if(tempIsWord){
						isWord = true;
					}
					if(tempIsMchar){
						isMchar = true;
					}
				}
			}
		}
		option = getOption(isLine, isWord, isMchar, isIllegal, isLegal);
		return option;
	}
	
	/*
	 * Check if a option is a valid option
	 * @param option
	 * return true if it is a valid option
	 */
	public boolean isValidOption(String option){
			
		if(option.charAt(0) == '-'){
			if(option.length() == 1){
				return false;
			}
			for(int j=1; j<option.length(); j++){
				if(option.charAt(j) != 'l' && option.charAt(j) != 'w' && option.charAt(j) != 'm'){
					return false;
				}
			}
		}
		else{
			return false;
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
	private String getOption(boolean isLine, boolean isWord, boolean isMchar, boolean isIllegal, boolean isLegal){
		String option = "-";
		boolean isAll = isIllegal;
		if(isLegal){
			isAll = false;
		}
		
		if(isLine){
			option += "l";
		}
		if(isWord){
			option += "w";
		}
		if(isMchar){
			option += "m";
		}
		if(isAll){
			option = "-lwm";
		}
		return option;
	}
}

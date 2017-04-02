else if(args.length == 100){
			String option = wcOption.processArgsOption(args);
			String totalData = "";
			fileName = args[0];	
			countFile = 0;
			if(wcOption.isValidOption(fileName)){
				readStdin = getInputStream(stdin, option);
				results = printInputStream(stdin, readStdin, option);		
			}
			else{
				if(wcCheckRead.checkValidFile(fileName)){
					String data = wcCheckRead.readFileStdin(fileName, null);// + System.lineSeparator();
					results = printCountInFileOrStdin("-lwm", data) + " " + fileName + System.lineSeparator();
					totalData += " " + wcCheckRead.readFileStdin(fileName, null);
					countFile += 1;
				}
				if(stdin != null){
					readStdin = getInputStream(stdin, option);	
					if(countFile > 0){
						totalData += " " + readStdin;
						countFile += 1;
					}
					results += printInputStream(stdin, readStdin, option);
				}
				if(countFile > 1){
					numOfBlankSpace = countFile-1;
					results += printCountInFileOrStdin(option, totalData.substring(1)) + " total" + System.lineSeparator();
				}
			}
		}
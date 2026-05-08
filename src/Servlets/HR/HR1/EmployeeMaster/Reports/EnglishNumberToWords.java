package Servlets.HR.HR1.EmployeeMaster.Reports;

import java.text.DecimalFormat;

public class EnglishNumberToWords {
	
	 private static final String[] tensNames = {
		    "",
		    " Ten",
		    " Twenty",
		    " Thirty",
		    " Forty",
		    " Fifty",
		    " Sixty",
		    " Seventy",
		    " Eighty",
		    " Ninety"
		  };

		  private static final String[] numNames = {
		    "",
		    " One",
		    " Two",
		    " Three",
		    " Four",
		    " Five",
		    " Six",
		    " Seven",
		    " Eight",
		    " Nine",
		    " Ten",
		    " Eleven",
		    " Twelve",
		    " Thirteen",
		    " Fourteen",
		    " Fifteen",
		    " Sixteen",
		    " Seventeen",
		    " Eighteen",
		    " Nineteen"
		  };

		  private static String convertLessThanOneThousand(int number) {
		    String soFar;

		    if (number % 100 < 20){
		      soFar = numNames[number % 100];
		      number /= 100;
		    }
		    else {
		      soFar = numNames[number % 10];
		      number /= 10;

		      soFar = tensNames[number % 10] + soFar;
		      number /= 10;
		    }
		    if (number == 0) return soFar;
		    return numNames[number] + " Hundred And" + soFar;
		  }


		  public static String convert(long number) {
		    // 0 to 999 999 999 999
		    if (number == 0) { return "Zero"; }
if(number == 100){ return "One Hundred"; }
		    String snumber = Long.toString(number);

		    // pad with "0"
		    String mask = "000000000000";
		    DecimalFormat df = new DecimalFormat(mask);
		    snumber = df.format(number);

		    // XXXnnnnnnnnn 
		    int billions = Integer.parseInt(snumber.substring(0,3));
		    // nnnXXXnnnnnn
		    int millions  = Integer.parseInt(snumber.substring(3,6)); 
		    // nnnnnnXXXnnn
		    int hundredThousands = Integer.parseInt(snumber.substring(6,9)); 
		    // nnnnnnnnnXXX
		    int thousands = Integer.parseInt(snumber.substring(9,12));    

		    String tradBillions;
		    switch (billions) {
		    case 0:
		      tradBillions = "";
		      break;
		    case 1 :
		      tradBillions = convertLessThanOneThousand(billions) 
		      + " Billion ";
		      break;
		    default :
		      tradBillions = convertLessThanOneThousand(billions) 
		      + " Billion ";
		    }
		    String result =  tradBillions;

		    String tradMillions;
		    switch (millions) {
		    case 0:
		      tradMillions = "";
		      break;
		    case 1 :
		      tradMillions = convertLessThanOneThousand(millions) 
		      + " Million ";
		      break;
		    default :
		      tradMillions = convertLessThanOneThousand(millions) 
		      + " Million ";
		    }
		    result =  result + tradMillions;

		    String tradHundredThousands;
		    switch (hundredThousands) {
		    case 0:
		      tradHundredThousands = "";
		      break;
		    case 1 :
		      tradHundredThousands = "One Thousand ";
		      break;
		    default :
		      tradHundredThousands = convertLessThanOneThousand(hundredThousands) 
		      + " Thousand ";
		    }
		    result =  result + tradHundredThousands;

		    String tradThousand;
		    tradThousand = convertLessThanOneThousand(thousands);
		    result =  result + tradThousand;

		    // remove extra spaces!
		    String fin=result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
		    
		    if(fin.length()>5){
		    //System.out.println("***>>>>>>>>>>>" +fin.substring(fin.length()-4,fin.length()));
		    String finTest=fin.substring(fin.length()-4,fin.length());
		    if(finTest.equals(" And")){
		    	//System.out.println("***><<<<<<<<<>>>>>>.>>" +fin.substring(0,fin.length()-4));
		    	fin=fin.substring(0,fin.length()-4);
		    }
		    }
		    return fin;
		  }

		  /**
		   * testing
		   * @param args
		   */
		  public static String AsciiToString(String str){
		    	String test="",si="";int jk=0,i=0;
		    	 for ( int j = 0; j < str.length(); ++j ) {
		    		 for ( jk = j; jk < str.length(); ++jk ) {
		    		 if(!str.substring(jk,jk+1).equals(",")){
		    			 si+=str.substring(jk,jk+1);
		    		 }else break;
		    		 }
		    		i= Integer.parseInt(si);
		    		 test+= new Character((char) i).toString();
		    		 System.out.println("Test "+si+" "+test);
		    		 si="";j=jk;
		    	 }
		    	return test;
		    }
		  public static String StrToAscii(String str){
		    	 String test = "";
		         for ( int i = 0; i < str.length(); ++i ) {
		           char c = str.charAt( i );
		           int j = (int) c;
		           System.out.println("Test "+j+" "+c);
		           test+=Integer.toString(j)+",";
		           }
		         return test;
		    }
		  public static void main(String[] args) {

			  System.out.println("*** " + EnglishNumberToWords.StrToAscii("str\n"));
		  }

}

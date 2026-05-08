package Servlets.FAS.FAS1.CommonClass;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class FASCommon {
	
	public Date date_convertion(String doj){
		
        String dojj = doj;       
        java.sql.Date formatted_date = null;
        String dateString1 = dojj;
        java.util.Date d1 = null;  
        if(dojj==""){           
            formatted_date=null;           
        }else{
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                d1 = dateFormat1.parse(dojj.trim());
            } catch (ParseException e) {                
                e.printStackTrace();
            }
            dateFormat1.applyPattern("yyyy-MM-dd");
            dateString1 = dateFormat1.format(d1);
            formatted_date = java.sql.Date.valueOf(dateString1);
           
           
        }
        return formatted_date;
    }
	
	public String dateConvertVar(String s){
		int month;
		int day;
		int firstDash;
		int secondDash;
		String date="",mont="";
		Map<Integer,String> monthlist = new HashMap<Integer,String>();
    	monthlist.put(1,"JAN");
    	monthlist.put(2,"FEB");
    	monthlist.put(3,"MAR");
    	monthlist.put(4,"APR");
    	monthlist.put(5,"MAY");
    	monthlist.put(6,"JUN");
    	monthlist.put(7,"JUL");
    	monthlist.put(8,"AUG");
    	monthlist.put(9,"SEP");
    	monthlist.put(10,"OCT");
    	monthlist.put(11,"NOV");
    	monthlist.put(12,"DEC");

		if (s == null) throw new java.lang.IllegalArgumentException();
		firstDash = s.indexOf('/');
		secondDash = s.indexOf('/', firstDash+1);
		
		if ((firstDash > 0) & (secondDash > 0) & (secondDash < s.length()-1)) {
			String[] arr = s.split("/");
			String year = arr[arr.length-1];
			String yr = year.substring(2, year.length());
			month = Integer.parseInt(s.substring(firstDash+1, secondDash));
		    mont = monthlist.get(month);
		    day = Integer.parseInt(s.substring(0, firstDash));		    
		    date = day+"-"+mont+"-"+yr;
		} else {
			throw new java.lang.IllegalArgumentException();
		}
		return date;
    }

}

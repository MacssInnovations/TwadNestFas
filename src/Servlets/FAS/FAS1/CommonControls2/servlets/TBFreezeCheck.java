package Servlets.FAS.FAS1.CommonControls2.servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TBFreezeCheck {
   
   
   
   public static String getTBFreezeStatus(Connection con, int UnitID, int CBYear, int CBMonth ) 
   {
   
       PreparedStatement ps=null;
   
       /**
        *  Check Wheather TB has been Frozen already or not 
        */
       try{        
          
           ps=con.prepareStatement(
           "  select 'X'                  \n" + 
           "  from                        \n" + 
           "    FAS_TRIAL_BALANCE_STATUS  \n" + 
           "  WHERE                       \n" + 
           "     ACCOUNTING_UNIT_ID=?     \n" + 
           "  AND CASHBOOK_YEAR=?      \n" + 
           "  AND CASHBOOK_MONTH=?"
            );
           ps.setInt(1,UnitID);
           ps.setInt(2,CBYear);
           ps.setInt(3,CBMonth);
           ResultSet res=ps.executeQuery();
           if(res.next())                     // if the row doesn't return by the query
           {
               return "FROZE";
           }
           return "NOTFREEZE";
           
           
       }
       catch(Exception e) 
       {
           System.out.println(e);  
           return "ERROR";
       }
       
   }

    
}

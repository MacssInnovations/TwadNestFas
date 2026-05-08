package Servlets.FAS.FAS1.CommonControls2.servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GL_SL_Checking_for_TPA {
   
   
   
   public ResultSet getGLSLData(Connection con, int UnitID, int OffId,int CBMonth, int CBYear ) 
   {
	   System.out.println("Welcome to GL_SL_Checking_for_tpa");
       PreparedStatement ps=null;
       ResultSet res=null;
       String sql=null;
       /**
        *  Check Wheather TB has been Frozen already or not 
        */
       try{        
           sql="select a.*,b.account_head_desc, c.sub_ledger_type_desc from\n" + 
           "(\n" +         	  
           "SELECT \n" + 
           "    accounting_unit_id,\n" + 
           "    accounting_for_office_id, \n" + 
           "    year,\n" + 
           "    month,\n" + 
           "    account_head_code, \n" + 
           "    sub_ledger_type_code, \n" + 
           "    sub_ledger_code,\n" +
           "    month_closing_bal_dr_cr_ind," + 
           "    trim(to_char(month_closing_balance,'99999999999999.99')) as month_closing_balance\n" + 
           "FROM\n" + 
           "    fas_sub_ledger_master\n" + 
           "WHERE \n" + 
           "    accounting_unit_id=? AND\n" + 
           "    accounting_for_office_id=? AND\n" + 
           "    year=? AND\n" + 
           "    month=? \n" + 
           "UNION\n" + 
           "SELECT \n" + 
           "    accounting_unit_id,\n" + 
           "    accounting_for_office_id, \n" + 
           "    year,\n" + 
           "    month,\n" + 
           "    account_head_code, \n" + 
           "    0 AS sub_ledger_type_code, \n" + 
           "    0 AS sub_ledger_code,\n" +
           "    month_closing_bal_dr_cr_ind," + 
           "    trim(to_char(month_closing_balance,'99999999999999.99')) as month_closing_balance\n" + 
           "FROM\n" + 
           "    fas_general_ledger\n" + 
           "WHERE \n" + 
           "    accounting_unit_id=? AND\n" + 
           "    accounting_for_office_id=? AND\n" + 
           "    year=? AND\n" + 
           "    month=? AND\n" + 
           "    account_head_code not in (SELECT account_head_code FROM fas_sub_ledger_master WHERE accounting_unit_id=? AND accounting_for_office_id=? AND year=? AND month=?)\n" + 
           ")a left outer join com_mst_account_heads b on a.account_head_code=b.account_head_code\n" + 
           "   left outer join com_mst_sl_types c on a.sub_ledger_type_code=c.sub_ledger_type_code\n";
           System.out.println("SQL ::: "+sql);
           ps=con.prepareStatement(sql); 
           ps.setInt(1,UnitID);
           ps.setInt(2,OffId);
           ps.setInt(3,CBYear);
           ps.setInt(4,CBMonth);
           ps.setInt(5,UnitID);
           ps.setInt(6,OffId);
           ps.setInt(7,CBYear);
           ps.setInt(8,CBMonth);
           ps.setInt(9,UnitID);
           ps.setInt(10,OffId);
           ps.setInt(11,CBYear);
           ps.setInt(12,CBMonth);
           res=ps.executeQuery();           
           return res;
       }
       catch(Exception e) 
       {
           System.out.println(e);  
           return res;
       }
       
   }

    
}

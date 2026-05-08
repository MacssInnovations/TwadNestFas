package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AA52_Register_OB extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        

    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        try
        {
    	   ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
           String ConnectionString="";

           String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
           String strdsn=rs.getString("Config.DSN");
           String strhostname=rs.getString("Config.HOST_NAME");
           String strportno=rs.getString("Config.PORT_NUMBER");
           String strsid=rs.getString("Config.SID");
           String strdbusername=rs.getString("Config.USER_NAME");
           String strdbpassword=rs.getString("Config.PASSWORD");
              
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

           Class.forName(strDriver.trim());
           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           try
           {
        	   statement=connection.createStatement();
               connection.clearWarnings();
           }
           catch(SQLException e)
           {
               System.out.println("Exception in creating statement:"+e);
           }
        }
        catch(Exception e)
        {
           System.out.println("Exception in openeing connection:"+e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
        int unit_id = 0;
        int office_id = 0;
	   	//String date_transaction = null;
	   	String financial_year = null;
	   	int asset_code = 0;
	   	int txtOB_bal  = 0;
	   	int txtdep_debit  = 0;
                int txtjournal_no  =  0;
               String txtjournal_date  = "";
               String txtjournal_date2  = "";
               String txtsurvey_date="";
               String txtauction_date="";
               String txtcb_date="";
                int txtsurvey_no  = 0;
                int txtauction_amt  = 0;
                int cb_vrno  =  0;
                String appor_date=null;
	   	int txt_profit = 0;
	   	int txtapport_grant = 0;
	   	
	   	int txt_loss = 0;
	   	int txtoff_debit  = 0;
	   	int txtoff_credit  = 0;
	   	int  txtjournal_vno2   =0;
                java.sql.Date cbdate=null;
                java.sql.Date auctiondate=null; 
                java.sql.Date journal_date=null;
                java.sql.Date journal_date2=null;
                java.sql.Date survey_date=null;
	   	String remarks = "",txtperson_name="";
	   	
	   	
        HttpSession session=request.getSession(false);
        try
        {
	        if(session==null)
	        {
	            System.out.println(request.getContextPath()+"/index.jsp");
	            response.sendRedirect(request.getContextPath()+"/index.jsp");
	        }
	        System.out.println(session);
        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
        
        
        String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      
        try
        {
        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
        	System.out.println("accounting_unit_id : " + unit_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
        }        
        
        try
        {
        	office_id = Integer.parseInt(request.getParameter("office_id"));
        	System.out.println("accounting_unit_office_id : " + office_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
        }     
        txtjournal_date = request.getParameter("txtjournal_date");
     
        txtsurvey_date = request.getParameter("txtsurvey_date");
        txtauction_date = request.getParameter("txtauction_date");
        txtcb_date = request.getParameter("txtcb_date");
      
        
        try
        {
        	financial_year = request.getParameter("financial_year");
        	System.out.println("financial_year : " + financial_year);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
        }     
        
        try
        {
        	asset_code = Integer.parseInt(request.getParameter("asset_code"));
        	System.out.println("asset_code : " + asset_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'asset_code' parameter ===> " + e);
        }        
        try
        {
                txtOB_bal = Integer.parseInt(request.getParameter("txtOB_bal"));
                System.out.println("txtOB_bal : " + txtOB_bal);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtOB_bal' parameter ===> " + e);
        }    
       
        
        try
        {
        	txtapport_grant = Integer.parseInt(request.getParameter("txtapport_grant"));
        	System.out.println("txtapport_grant : " + txtapport_grant);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtapport_grant' parameter ===> " + e);
        }        
        try
        {
                txtdep_debit = Integer.parseInt(request.getParameter("txtdep_debit"));
                System.out.println("txtdep_debit : " + txtdep_debit);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtdep_debit' parameter ===> " + e);
        }    
        try
        {
                txtjournal_no = Integer.parseInt(request.getParameter("txtjournal_no"));
                System.out.println("txtjournal_no : " + txtjournal_no);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtjournal_no' parameter ===> " + e);
        } 
        
        try
        {
        	txtsurvey_no = Integer.parseInt(request.getParameter("txtsurvey_no"));
        	System.out.println("txtsurvey_no : " + txtsurvey_no);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtsurvey_no' parameter ===> " + e);
        }   
        try
        {
                txtperson_name = request.getParameter("txtperson_name");
                System.out.println("txtperson_name : " + txtperson_name);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtsurvey_no' parameter ===> " + e);
        }  
        try
        {
                txtauction_amt = Integer.parseInt(request.getParameter("txtauction_amt"));
                System.out.println("txtauction_amt : " + txtauction_amt);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtauction_amt' parameter ===> " + e);
        }
       
        
        try
        {
        	cb_vrno = Integer.parseInt(request.getParameter("cb_vrno"));
        	System.out.println("cb_vrno : " + cb_vrno);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'cb_vrno' parameter ===> " + e);
        }     
	   	
        try
        {
        	txt_profit = Integer.parseInt(request.getParameter("txt_profit"));
        	System.out.println("txt_profit : " + txt_profit);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txt_profit' parameter ===> " + e);
        }     
        
        try
        {
        	txt_loss = Integer.parseInt(request.getParameter("txt_loss"));
        	System.out.println("txt_loss : " + txt_loss);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txt_loss' parameter ===> " + e);
        }     
	
        try
        {
        	txtoff_debit = Integer.parseInt(request.getParameter("txtoff_debit"));
        	System.out.println("txtoff_debit : " + txtoff_debit);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtdepre_recieved' parameter ===> " + e);
        }     
	
        try
        {
        	txtoff_credit = Integer.parseInt(request.getParameter("txtoff_credit"));
        	System.out.println("txtdepre_allowed_yr : " + txtoff_credit);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtdepre_allowed_yr' parameter ===> " + e);
        }     
	
           
    	
        try
        {
        	txtjournal_vno2 = Integer.parseInt(request.getParameter("txtjournal_vno2"));
        	System.out.println("txtjournal_vno2 : " + txtjournal_vno2);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtjournal_vno2' parameter ===> " + e);
        }     
	        
   try{
        if(!txtjournal_date.equalsIgnoreCase(""))
        { System.out.println("inside");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            java.util.Date d1=dateFormat1.parse(txtjournal_date);
            dateFormat1.applyPattern("yyyy-MM-dd");
            txtjournal_date=dateFormat1.format(d1);
        
        }catch(Exception e)
        {
             e.printStackTrace();
        }
        journal_date=Date.valueOf(txtjournal_date);
        System.out.println("From date : "+journal_date);
        }
        if(!txtcb_date.equalsIgnoreCase(""))
        {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            java.util.Date d1=dateFormat1.parse(txtcb_date);
            dateFormat1.applyPattern("yyyy-MM-dd");
            txtcb_date=dateFormat1.format(d1);
        
        }catch(Exception e)
        {
             e.printStackTrace();
        }
        cbdate=Date.valueOf(txtcb_date);
        System.out.println("cbdate : "+cbdate);
        }
        if(!txtauction_date.equalsIgnoreCase(""))
        {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            java.util.Date d1=dateFormat1.parse(txtauction_date);
            dateFormat1.applyPattern("yyyy-MM-dd");
            txtauction_date=dateFormat1.format(d1);
        
        }catch(Exception e)
        {
             e.printStackTrace();
        }
        auctiondate=Date.valueOf(txtauction_date);
        System.out.println("auctiondate : "+auctiondate);
        }
        if(!txtjournal_date2.equalsIgnoreCase(""))
        {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            java.util.Date d1=dateFormat1.parse(txtjournal_date2);
            dateFormat1.applyPattern("yyyy-MM-dd");
            txtjournal_date2=dateFormat1.format(d1);
        
        }catch(Exception e)
        {
             e.printStackTrace();
        }
        journal_date2=Date.valueOf(txtjournal_date2);
        System.out.println("journal_date2 : "+journal_date2);
        }
        if(!txtsurvey_date.equalsIgnoreCase(""))
        {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            java.util.Date d1=dateFormat1.parse(txtsurvey_date);
            dateFormat1.applyPattern("yyyy-MM-dd");
            txtsurvey_date=dateFormat1.format(d1);
        
        }catch(Exception e)
        {
             e.printStackTrace();
        }
        survey_date=Date.valueOf(txtsurvey_date);
        System.out.println("journal_date2 : "+survey_date);
        }
   }   catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'Date' parameter ===> " + e);
        } 
   
        try
        {
        	remarks = request.getParameter("remarks");
        	System.out.println("remarks : " + remarks);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'remarks' parameter ===> " + e);
        }     
      
        
        /*
         * Execute the task adhering to the requested command
         */
        //////////////////////////////// if else - STARTS ///////////////////////////////////////////////
        if(strCommand.equalsIgnoreCase("Delete"))
        {
        	System.out.println("\n*************\nDelete\n**************\n");
            xml="<response><command>Delete</command>";
            try 
            {
             String sqlDelete = "DELETE FROM FAS_AA52REGISTER " +
								"WHERE accounting_unit_id = ? " +
								" AND accounting_unit_office_id = ? " +
								" AND financial_year = ? " +
								" AND asset_code = ? ";
             PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setInt(1, unit_id);
		   	 ps.setInt(2, office_id);
		   	 ps.setString(3, financial_year);
		   	 ps.setInt(4, asset_code);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<accounting_unit_id>" + unit_id + "</accounting_unit_id>";
             xml=xml+"<accounting_unit_office_id>" + office_id + "</accounting_unit_office_id>";
             xml=xml+"<financial_year>" + financial_year + "</financial_year>";
             xml=xml+"<asset_code>" + asset_code + "</asset_code>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Deleting record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
               
        else if(strCommand.equalsIgnoreCase("Update"))
        {
        	System.out.println("\n*************\nUpdate\n**************\n");
            xml="<response><command>Update</command>";
            try 
            {
             String sqlUpdate = "UPDATE FAS_AA52REGISTER SET BOOKVALUE=?,appor_grant=?,dep_debit=?,journal_no=?," + 
				"journal_date=?,survey_no=?,survey_date=?,auction_date=?,person_name=?,auction_amount=?,cb_voucherno=?,cb_voucherdate=?," + 
				"profit=?,loss=?,off_debit=?,off_credit=?,remarks=?," + 
				"UPDATED_BY_USERID=?,UPDATED_DATE=? " + 
			        "WHERE accounting_unit_id = ? AND accounting_unit_office_id = ? " +
				"AND financial_year = ?  AND asset_code = ? ";
		 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
                ps.setInt(1,txtOB_bal);
                ps.setInt(2,txtapport_grant);
                ps.setInt(3,txtdep_debit);
                ps.setInt(4,txtjournal_no);
                ps.setDate(5,journal_date);
                ps.setInt(6,txtsurvey_no);
                ps.setDate(7,survey_date);
                ps.setDate(8,auctiondate);
                ps.setString(9,txtperson_name);
                
                ps.setInt(10,txtauction_amt);
                ps.setInt(11,cb_vrno);
                ps.setDate(12,cbdate);
                ps.setInt(13,txt_profit);
                ps.setInt(14,txt_loss);
                ps.setInt(15,txtoff_debit);
                ps.setInt(16,txtoff_credit);
                ps.setString(17,remarks);
                ps.setString(18,userid);
                ps.setTimestamp(19,ts);
                ps.setInt(20,unit_id);
                ps.setInt(21,office_id);
                ps.setString(22,financial_year);
                ps.setInt(23,asset_code);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
	
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Updating record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
                
     else if(strCommand.equalsIgnoreCase("Add"))
        {
        	System.out.println("\n*************\nAdd\n**************\n");
            xml="<response><command>Add</command>";

            try 
            {
             String sqlAdd = "INSERT INTO FAS_AA52REGISTER" +
                "(BOOKVALUE,appor_grant,dep_debit,journal_no," + 
                "journal_date,survey_no,survey_date,auction_date," +
                "person_name,auction_amount,cb_voucherno,cb_voucherdate," + 
                "profit,loss,off_debit,off_credit,"+
                "remarks," +
                "UPDATED_BY_USERID,UPDATED_DATE,"+
                "accounting_unit_id,accounting_unit_office_id,financial_year,asset_code) "  +
	   	" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	 	 PreparedStatement ps = connection.prepareStatement(sqlAdd);
                ps.setInt(1,txtOB_bal);
                ps.setInt(2,txtapport_grant);
                ps.setInt(3,txtdep_debit);
                ps.setInt(4,txtjournal_no);
                ps.setDate(5,journal_date);
                ps.setInt(6,txtsurvey_no);
                ps.setDate(7,survey_date);
                ps.setDate(8,auctiondate);
                ps.setString(9,txtperson_name);
                ps.setInt(10,txtauction_amt);
                ps.setInt(11,cb_vrno);
                ps.setDate(12,cbdate);
                ps.setInt(13,txt_profit);
                ps.setInt(14,txt_loss);
                ps.setInt(15,txtoff_debit);
                ps.setInt(16,txtoff_credit);
                ps.setString(17,remarks);
                ps.setString(18,userid);
                ps.setTimestamp(19,ts);
                ps.setInt(20,unit_id);
                ps.setInt(21,office_id);
                ps.setString(22,financial_year);
                ps.setInt(23,asset_code);
               
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
                xml=xml+"<txtOB_bal>" + txtOB_bal + "</txtOB_bal>";
                xml=xml+"<txtsurvey_no>" + txtsurvey_no + "</txtsurvey_no>";
                xml=xml+"<txtdep_debit>" + txtdep_debit + "</txtdep_debit>";
                xml=xml+"<txtauction_amt>" + txtauction_amt + "</txtauction_amt>";
                xml=xml+"<txtjournal_no>" + txtjournal_no + "</txtjournal_no>";
                xml=xml+"<txtjournal_date>" + txtjournal_date + "</txtjournal_date>";
                xml=xml+"<txtsurvey_no>" + txtsurvey_no + "</txtsurvey_no>";
                xml=xml+"<txtsurvey_date>" + txtsurvey_date + "</txtsurvey_date>";
                xml=xml+"<txtauction_date>" + txtauction_date + "</txtauction_date>";
                xml=xml+"<txtperson_name>" + txtperson_name + "</txtperson_name>";
                xml=xml+"<txtauction_amt>" + txtauction_amt + "</txtauction_amt>";
                xml=xml+"<cb_vrno>" + cb_vrno + "</cb_vrno>";
                xml=xml+"<txtcb_date>" + txtcb_date + "</txtcb_date>";
                xml=xml+"<txt_profit>" + txt_profit + "</txt_profit>";
                xml=xml+"<txt_loss>" + txt_loss + "</txt_loss>";
                xml=xml+"<txtoff_debit>"+ txtoff_debit + "</txtoff_debit>";
                xml=xml+"<txtoff_credit>" + txtoff_credit+ "</txtoff_credit>";
                xml=xml+"<txtjournal_vno2>" + txtjournal_vno2 + "</txtjournal_vno2>";
                xml=xml+"<txtjournal_date2>" + txtjournal_date2 + "</txtjournal_date2>";
                xml=xml+"<remarks>" + remarks + "</remarks>";
                xml=xml+"<userid>" + userid + "</userid>";
                xml=xml+"<unit_id>" + unit_id + "</unit_id>";
                xml=xml+"<office_id>" + office_id + "</office_id>";
                xml=xml+"<financial_year>" + financial_year + "</financial_year>";
                xml=xml+"<asset_code>" + asset_code + "</asset_code>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Adding record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } 
       else if(strCommand.equals("Get"))
        { 
        	System.out.println("\n*************\nGet\n**************\n");
            xml="<response><command>Get</command>";
            try 
            {
                System.out.println("bef res");
              System.out.println(unit_id);
              System.out.println(office_id);
              System.out.println(financial_year);
              System.out.println(asset_code);
             result = statement.executeQuery("select BOOKVALUE,appor_grant,dep_debit,journal_no," + 
             "to_char(journal_date,'dd/mm/yyyy') as journal_date,survey_no,to_char(survey_date,'dd/mm/yyyy') as survey_date,to_char(auction_date,'dd/mm/yyyy') as auction_date," + 
             "person_name,auction_amount,cb_voucherno,to_char(cb_voucherdate,'dd/mm/yyyy') as cb_voucherdate," + 
             "profit,loss,off_debit,off_credit," + 
             "remarks from FAS_AA52REGISTER " +
   "WHERE accounting_unit_id = "+unit_id +" AND accounting_unit_office_id = "+ office_id +" AND financial_year = '" + financial_year + "'  AND asset_code = " + asset_code+" ");
             
                System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	
                     xml=xml+"<txtOB_bal>" + result.getInt("BOOKVALUE") + "</txtOB_bal>";
                     xml=xml+"<txtapport_grant>" + result.getInt("appor_grant") + "</txtapport_grant>";
                     xml=xml+"<txtdep_debit>" + result.getInt("dep_debit") + "</txtdep_debit>";
                     xml=xml+"<txtauction_amt>" + result.getInt("auction_amount") + "</txtauction_amt>";
                     xml=xml+"<txtjournal_no>" + result.getInt("journal_no") + "</txtjournal_no>";
                     xml=xml+"<txtjournal_date>" + result.getString("journal_date") + "</txtjournal_date>";
                     xml=xml+"<txtsurvey_no>" + result.getInt("survey_no") + "</txtsurvey_no>";
                     xml=xml+"<txtsurvey_date>" + result.getString("survey_date") + "</txtsurvey_date>";
                     xml=xml+"<txtauction_date>" + result.getString("auction_date") + "</txtauction_date>";
                     xml=xml+"<txtperson_name>" + result.getString("person_name") + "</txtperson_name>";
                     xml=xml+"<cb_vrno>" + result.getString("cb_voucherno")+ "</cb_vrno>";
                     xml=xml+"<txtcb_date>" + result.getString("cb_voucherdate") + "</txtcb_date>";
                     xml=xml+"<txt_profit>" + result.getInt("profit") + "</txt_profit>";
                     xml=xml+"<txt_loss>" + result.getInt("loss") + "</txt_loss>";
                     xml=xml+"<txtoff_debit>"+ result.getInt("off_debit") + "</txtoff_debit>";
                     xml=xml+"<txtoff_credit>" + result.getInt("off_credit")+ "</txtoff_credit>";
                    
                	 remarks = result.getString("remarks");
                	 if(remarks == null)
                	 {
                		 remarks = " ";
                	 }
                	 xml += "<remarks>" + remarks + "</remarks>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from DB - GET: " + e);
             }
             
             result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } 
        
        
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
        
    }
}

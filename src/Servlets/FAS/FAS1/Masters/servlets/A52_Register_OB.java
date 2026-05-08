package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class A52_Register_OB extends HttpServlet 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        

    }
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        ResultSet result1=null;
        PreparedStatement pers=null;
        int up=0;
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
        int assetmajor=0;
        int assetminor=0;
	   	//String date_transaction = null;
	   	String financial_year = null;
	   	int asset_code = 0;
	   	int txtQ1  = 0;
	   	int txtQ2  = 0;
                int txtQ3  =  0;
                int txtV1  = 0;
                int txtV2  = 0;
                int txtV3  =  0;
                String appor_date=null;
	   	int txtQ_total = 0;
	   	int txtV_total = 0;
	   	
	   	int txtdepre_prev_yr = 0;
	   	int txtdepre_recieved  = 0;
	   	int txtdepre_allowed_yr  = 0;
	   	int  txttotal_depre   =0;
	        int dep_transfer  = 0;
	   	String txtdepre_date  = "";
	   	int txtnet_depre  = 0;
	   	int txtappor_grant  = 0;
	   	int txtappor_recieved  = 0;
	   	int aoor_a110wed  = 0;
	   	int txttotal_appor  = 0;
	   	int txtapp_transfer  = 0;
       int txtdepre_date2=0,txtnet_depre2=0,txtappor_grant2=0,txtappor_recieved2=0,txtappor_allowed2=0,txttotal_appor2=0;
	   	String remarks = "";
	   	
	   	
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
       try{
         txtdepre_date2=Integer.parseInt(request.getParameter("txtdepre_date"));
        //System.out.println(txtdepre_date2);
         }
        catch (Exception e) {
                    System.out.println("Redirect Error :" + e);
                }
        try{        
        txtnet_depre2=Integer.parseInt(request.getParameter("txtnet_depre"));
       // System.out.println("txtnet_depre2"+txtnet_depre2);
        }
        catch (Exception e) {
                    System.out.println("Redirect Error2 :" + e);
                }
       try{         
         txtappor_grant2=Integer.parseInt(request.getParameter("txtappor_grant"));
       // System.out.println(txtappor_grant2);
        }
        catch (Exception e) {
                    System.out.println("txtappor_grant2 :" + e);
                }
                try{
        txtappor_recieved2=Integer.parseInt(request.getParameter("txtappor_recieved"));
       // System.out.println(txtappor_recieved2);
        }
        catch (Exception e) {
                    System.out.println("txtappor_recieved2 :" + e);
                }
        try{        
        txtappor_allowed2=Integer.parseInt(request.getParameter("txtappor_allowed"));
        //System.out.println(txtappor_allowed2);
        }
        catch (Exception e) {
                    System.out.println("txtappor_allowed2 :" + e);
                }
                try{
        txttotal_appor2=Integer.parseInt(request.getParameter("txttotal_appor"));
      //  System.out.println(txttotal_appor2);
        }
        catch (Exception e) {
                    System.out.println("txttotal_appor2 :" + e);
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
 
        try
        {
             txtdepre_date = request.getParameter("txtdepre_date");
       	
 	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;                    
            d = dateFormat.parse(txtdepre_date.trim());
            dateFormat.applyPattern("dd-MMM-yyyy");
            appor_date = dateFormat.format(d);

            System.out.println("txtdepre_date : " + txtdepre_date);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'appor_date' parameter ===> " + e);
        }        
        
        try
        {
             appor_date = request.getParameter("appor_date");
        
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;                    
            d = dateFormat.parse(appor_date.trim());
            dateFormat.applyPattern("dd-MMM-yyyy");
            appor_date = dateFormat.format(d);

            System.out.println("appor_date : " + appor_date);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'appor_date' parameter ===> " + e);
        }        
        
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
        	txtQ1 = Integer.parseInt(request.getParameter("txtQ1"));
        	System.out.println("txtQ1 : " + txtQ1);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtQ1' parameter ===> " + e);
        }        
        try
        {
                txtQ2 = Integer.parseInt(request.getParameter("txtQ2"));
                System.out.println("txtQ2 : " + txtQ2);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtQ2' parameter ===> " + e);
        }    
        try
        {
                txtQ3 = Integer.parseInt(request.getParameter("txtQ3"));
                System.out.println("asset_type_code : " + txtQ3);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtQ3' parameter ===> " + e);
        } 
        try
        {
        	txtV1 = Integer.parseInt(request.getParameter("txtV1"));
        	System.out.println("txtV1 : " + txtV1);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtV1' parameter ===> " + e);
        }   
        try
        {
                txtV2 = Integer.parseInt(request.getParameter("txtV2"));
                System.out.println("txtV2 : " + txtV2);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtV1' parameter ===> " + e);
        }  
        try
        {
                txtV2 = Integer.parseInt(request.getParameter("txtV2"));
                System.out.println("txtV2 : " + txtV2);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtV2' parameter ===> " + e);
        }
        try
        {
                txtV2 = Integer.parseInt(request.getParameter("txtV2"));
                System.out.println("txtV3 : " + txtV3);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtV1' parameter ===> " + e);
        }
        
        try
        {
        	txtQ_total = Integer.parseInt(request.getParameter("txtQ_total"));
        	System.out.println("txtQ_total : " + txtQ_total);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtQ_total' parameter ===> " + e);
        }     
	   	
        try
        {
        	txtV_total = Integer.parseInt(request.getParameter("txtV_total"));
        	System.out.println("txtV_total : " + txtV_total);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtV_total' parameter ===> " + e);
        }     
        
        try
        {
        	txtdepre_prev_yr = Integer.parseInt(request.getParameter("txtdepre_prev_yr"));
        	System.out.println("txtdepre_prev_yr : " + txtdepre_prev_yr);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtdepre_prev_yr' parameter ===> " + e);
        }     
	
        try
        {
        	txtdepre_recieved = Integer.parseInt(request.getParameter("txtdepre_recieved"));
        	System.out.println("txtdepre_recieved : " + txtdepre_recieved);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtdepre_recieved' parameter ===> " + e);
        }     
	
        try
        {
        	txtdepre_allowed_yr = Integer.parseInt(request.getParameter("txtdepre_allowed_yr"));
        	System.out.println("txtdepre_allowed_yr : " + txtdepre_allowed_yr);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtdepre_allowed_yr' parameter ===> " + e);
        }     
	
           
    	
        try
        {
        	txttotal_depre = Integer.parseInt(request.getParameter("txttotal_depre"));
        	System.out.println("txttotal_depre : " + txttotal_depre);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txttotal_depre' parameter ===> " + e);
        }     
	
        try
        {
        	dep_transfer = Integer.parseInt(request.getParameter("dep_transfer"));
        	System.out.println("dep_transfer : " + dep_transfer);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'dep_transfer' parameter ===> " + e);
        }     
	   
        
        try
        {
                txtapp_transfer = Integer.parseInt(request.getParameter("txttransfer"));
                System.out.println("txtapp_transfer : " + txtapp_transfer);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtapp_transfer' parameter ===> " + e);
        } 
       
		
        try
        {
        	txtdepre_date = request.getParameter("txtdepre_date");
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;                    
            d = dateFormat.parse(txtdepre_date.trim());
            dateFormat.applyPattern("dd-MMM-yyyy");
            txtdepre_date = dateFormat.format(d);
            
        	System.out.println("txtdepre_date : " + txtdepre_date);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtdepre_date' parameter ===> " + e);
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
        try
        {
        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        	System.out.println("assetmajor : " + assetmajor);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'remarks' parameter ===> " + e);
        }     
        try
        {
        	assetminor = Integer.parseInt(request.getParameter("assetminor"));
        	System.out.println("assetmionor : " + assetminor);
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
             String sqlDelete = "DELETE FROM FAS_A52REGISTER " +
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
             String sqlUpdate = "UPDATE FAS_A52_REGISTER " +
				"SET OPEN_BAL_QTY=?,OPENING_BAL_VALUE=?,RECIEPTS_YEAR_QTY=?,RECIEPTS_YR_VALUE=?," + 
				"ISSUES_YEAR_QTY=?,ISSUES_YR_VALUE=?,TOTAL_YEAR_QTY=?,TOTAL_YR_VALUE=?," + 
				"DEP_PREV_YEAR=?,DEPRE_REC_AC=?,DEPRE_ALLOWED_YR=?,TOTAL_DEPRE=?," + 
				"DEPRE_TR_AC=?,DEPRE_UPTO_DATE=?,NET_DEPRE_COST=?,APP_PRE_YR=?," + 
				"APP_GRANT_RECIEVED=?,APPRO_DURING_YR=?,TOT_APP_GRANT=?,APP_GRANT_TR=?," + 
				"APP_GRANT_UPTODATE=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? "+
                                "WHERE accounting_unit_id = ? AND accounting_unit_office_id = ? " +
				" AND financial_year = ?  AND asset_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
		  
                ps.setInt(1,txtQ1);
                System.out.println(txtQ1);
                ps.setInt(2,txtV1);
                System.out.println(txtV1);
                ps.setInt(3,txtQ2);
                System.out.println(txtQ2);
                ps.setInt(4,txtV2);
                System.out.println(txtV2);
                ps.setInt(5,txtQ3);
                System.out.println(txtQ3);
                ps.setInt(6,txtV3);
                System.out.println("txtV3"+txtV3);
                ps.setInt(7,txtQ_total);
                System.out.println(txtQ_total);
                ps.setInt(8,txtV_total);
                System.out.println(txtV_total);
                ps.setInt(9,txtdepre_prev_yr);
                System.out.println(txtdepre_prev_yr);
                ps.setInt(10,txtdepre_recieved);
                System.out.println(txtdepre_recieved);
                ps.setInt(11,txtdepre_allowed_yr);
                System.out.println(txtdepre_allowed_yr);
                ps.setInt(12,txttotal_depre);
                System.out.println(txttotal_depre);
                ps.setInt(13,dep_transfer);
                System.out.println(dep_transfer);
                ps.setString(14,txtdepre_date);
                System.out.println(txtdepre_date);
                ps.setInt(15,txtnet_depre2);
                System.out.println("txtnet_depre2"+txtnet_depre);
                ps.setInt(16,txtappor_grant2);
                System.out.println(txtappor_grant2);
                ps.setInt(17,txtappor_recieved2);
                System.out.println(txtappor_recieved2);
                ps.setInt(18,txtappor_allowed2);
                System.out.println(txtappor_allowed2);
                ps.setInt(19,txttotal_appor2);
                System.out.println(txttotal_appor2);
                ps.setInt(20,txtapp_transfer);
                System.out.println(appor_date);
                ps.setString(21,appor_date);
                System.out.println();
                ps.setString(22,remarks);
                System.out.println(remarks);
                ps.setString(23,userid);
                System.out.println(userid);
                ps.setTimestamp(24,ts);
                System.out.println(ts);
                ps.setInt(25,unit_id);
                System.out.println(unit_id);
                ps.setInt(26,office_id);
                System.out.println(office_id);
                ps.setString(27,financial_year);
                System.out.println(financial_year);
                ps.setInt(28,asset_code);
                System.out.println(asset_code);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
                
                xml=xml+"<txtQ1>" + txtQ1 + "</txtQ1>";
                xml=xml+"<txtV1>" + txtV1 + "</txtV1>";
                xml=xml+"<txtQ2>" + txtQ2 + "</txtQ2>";
                xml=xml+"<txtV2>" + txtV2 + "</txtV2>";
                xml=xml+"<txtQ3>" + txtQ3 + "</txtQ3>";
                xml=xml+"<txtV3>" + txtV3 + "</txtV3>";
                xml=xml+"<txtQ_total>" + txtQ_total + "</txtQ_total>";
                xml=xml+"<txtV_total>" + txtV_total + "</txtV_total>";
                xml=xml+"<txtdepre_prev_yr>" + txtdepre_prev_yr + "</txtdepre_prev_yr>";
                xml=xml+"<txtdepre_recieved>" + txtdepre_recieved + "</txtdepre_recieved>";
                xml=xml+"<txtdepre_allowed_yr>" + txtdepre_allowed_yr + "</txtdepre_allowed_yr>";
                xml=xml+"<txttotal_depre>" + txttotal_depre + "</txttotal_depre>";
                xml=xml+"<dep_transfer>" + dep_transfer + "</dep_transfer>";
                xml=xml+"<txtdepre_date>" + txtdepre_date + "</txtdepre_date>";
                xml=xml+"<txtnet_depre>" + txtnet_depre + "</txtnet_depre>";
                xml=xml+"<txtappor_grant>" + txtappor_grant + "</txtappor_grant>";
                xml=xml+"<txtappor_recieved>" + txtappor_recieved + "</txtappor_recieved>";
                xml=xml+"<txtappor_allowed2>" + txtappor_allowed2 + "</txtappor_allowed2>";
                xml=xml+"<txttotal_appor>" + txttotal_appor + "</txttotal_appor>";
                xml=xml+"<txtapp_transfer>" + txtapp_transfer + "</txtapp_transfer>";
                xml=xml+"<appor_date>" + appor_date + "</appor_date>";
                xml=xml+"<remarks>" + remarks + "</remarks>";
                xml=xml+"<userid>" + userid + "</userid>";
                xml=xml+"<unit_id>" + unit_id + "</unit_id>";
                xml=xml+"<office_id>" + office_id + "</office_id>";
                xml=xml+"<financial_year>" + financial_year + "</financial_year>";
                xml=xml+"<asset_code>" + asset_code + "</asset_code>";
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
             String sqlAdd = "INSERT INTO FAS_A52_REGISTER" +
                "(OPEN_BAL_QTY,OPENING_BAL_VALUE,RECIEPTS_YEAR_QTY,RECIEPTS_YR_VALUE," + 
                "ISSUES_YEAR_QTY,ISSUES_YR_VALUE,TOTAL_YEAR_QTY,TOTAL_YR_VALUE," +
                "DEP_PREV_YEAR,DEPRE_REC_AC,DEPRE_ALLOWED_YR,TOTAL_DEPRE," + 
                "DEPRE_TR_AC,DEPRE_UPTO_DATE,NET_DEPRE_COST,APP_PRE_YR,"+
                "APP_GRANT_RECIEVED,APPRO_DURING_YR,TOT_APP_GRANT,APP_GRANT_TR," +
                "APP_GRANT_UPTODATE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,"+
                "accounting_unit_id,accounting_unit_office_id,financial_year,asset_code) "  +
	   	" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	 	 PreparedStatement ps = connection.prepareStatement(sqlAdd);
                ps.setInt(1,txtQ1);
                System.out.println(txtQ1);
                ps.setInt(2,txtV1);
              System.out.println(txtV1);
                ps.setInt(3,txtQ2);
              System.out.println(txtQ2);
                ps.setInt(4,txtV2);
               System.out.println(txtV2);
                ps.setInt(5,txtQ3);
            System.out.println(txtQ3);
                ps.setInt(6,txtV3);
               System.out.println("txtV3"+txtV3);
                ps.setInt(7,txtQ_total);
              System.out.println(txtQ_total);
                ps.setInt(8,txtV_total);
                System.out.println(txtV_total);
                ps.setInt(9,txtdepre_prev_yr);
                System.out.println(txtdepre_prev_yr);
                ps.setInt(10,txtdepre_recieved);
                System.out.println(txtdepre_recieved);
                ps.setInt(11,txtdepre_allowed_yr);
                System.out.println(txtdepre_allowed_yr);
                ps.setInt(12,txttotal_depre);
                System.out.println(txttotal_depre);
                ps.setInt(13,dep_transfer);
                System.out.println(dep_transfer);
                ps.setString(14,txtdepre_date);
                System.out.println(txtdepre_date);
                ps.setInt(15,txtnet_depre2);
                System.out.println("txtnet_depre2"+txtnet_depre);
                ps.setInt(16,txtappor_grant2);
                System.out.println(txtappor_grant2);
                ps.setInt(17,txtappor_recieved2);
                System.out.println(txtappor_recieved2);
                ps.setInt(18,txtappor_allowed2);
                System.out.println(txtappor_allowed2);
                ps.setInt(19,txttotal_appor2);
                System.out.println(txttotal_appor2);
                ps.setInt(20,txtapp_transfer);
                System.out.println(appor_date);
                ps.setString(21,appor_date);
                System.out.println();
                ps.setString(22,remarks);
                System.out.println(remarks);
                ps.setString(23,userid);
                System.out.println(userid);
                ps.setTimestamp(24,ts);
                System.out.println(ts);
                ps.setInt(25,unit_id);
                System.out.println(unit_id);
                ps.setInt(26,office_id);
                System.out.println(office_id);
                ps.setString(27,financial_year);
                System.out.println(financial_year);
                ps.setInt(28,asset_code);
                System.out.println(asset_code);

                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
                xml=xml+"<txtQ1>" + txtQ1 + "</txtQ1>";
                xml=xml+"<txtV1>" + txtV1 + "</txtV1>";
                xml=xml+"<txtQ2>" + txtQ2 + "</txtQ2>";
                xml=xml+"<txtV2>" + txtV2 + "</txtV2>";
                xml=xml+"<txtQ3>" + txtQ3 + "</txtQ3>";
                xml=xml+"<txtV3>" + txtV3 + "</txtV3>";
                xml=xml+"<txtQ_total>" + txtQ_total + "</txtQ_total>";
                xml=xml+"<txtV_total>" + txtV_total + "</txtV_total>";
                xml=xml+"<txtdepre_prev_yr>" + txtdepre_prev_yr + "</txtdepre_prev_yr>";
                xml=xml+"<txtdepre_recieved>" + txtdepre_recieved + "</txtdepre_recieved>";
                xml=xml+"<txtdepre_allowed_yr>" + txtdepre_allowed_yr + "</txtdepre_allowed_yr>";
                xml=xml+"<txttotal_depre>" + txttotal_depre + "</txttotal_depre>";
                xml=xml+"<dep_transfer>" + dep_transfer + "</dep_transfer>";
                xml=xml+"<txtdepre_date>" + txtdepre_date + "</txtdepre_date>";
                xml=xml+"<txtnet_depre>" + txtnet_depre + "</txtnet_depre>";
                xml=xml+"<txtappor_grant>" + txtappor_grant + "</txtappor_grant>";
                xml=xml+"<txtappor_recieved>" + txtappor_recieved + "</txtappor_recieved>";
                xml=xml+"<txtappor_allowed2>" + txtappor_allowed2 + "</txtappor_allowed2>";
                xml=xml+"<txttotal_appor>" + txttotal_appor + "</txttotal_appor>";
                xml=xml+"<txtapp_transfer>" + txtapp_transfer + "</txtapp_transfer>";
                xml=xml+"<appor_date>" + appor_date + "</appor_date>";
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
             result = statement.executeQuery("select OPEN_BAL_QTY,OPENING_BAL_VALUE,RECIEPTS_YEAR_QTY,RECIEPTS_YR_VALUE,  \n" + 
             "ISSUES_YEAR_QTY,ISSUES_YR_VALUE, \n" + 
             "DEP_PREV_YEAR,DEPRE_REC_AC,DEPRE_ALLOWED_YR,  \n" + 
             "DEPRE_TR_AC,DEPRE_UPTO_DATE,NET_DEPRE_COST,APP_PRE_YR,\n" + 
             "APP_GRANT_RECIEVED,APPRO_DURING_YR,APP_GRANT_TR, \n" + 
             "APP_GRANT_UPTODATE,REMARKS,accounting_unit_id,accounting_unit_office_id,financial_year,asset_code\n" + 
             "from FAS_A52_REGISTER" +
             " WHERE accounting_unit_id = " + unit_id +
	     "AND accounting_unit_office_id = " + office_id +
	     " AND financial_year = '" + financial_year + "'" +
	     " AND asset_code = " + asset_code);
             
                System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<accounting_unit_id>" + result.getInt("accounting_unit_id") + "</accounting_unit_id>";
                	 xml += "<accounting_unit_office_id>" + result.getInt("accounting_unit_office_id") + "</accounting_unit_office_id>";
                	 xml += "<financial_year>" + result.getString("financial_year") + "</financial_year>";
                	 xml += "<asset_code>" + result.getInt("asset_code") + "</asset_code>";
                     xml=xml+"<txtQ1>" + result.getInt("OPEN_BAL_QTY") + "</txtQ1>";
                     xml=xml+"<txtV1>" + result.getInt("OPENING_BAL_VALUE") + "</txtV1>";
                     xml=xml+"<txtQ2>" + result.getInt("RECIEPTS_YEAR_QTY") + "</txtQ2>";
                     xml=xml+"<txtV2>" + result.getInt("RECIEPTS_YR_VALUE") + "</txtV2>";
                     xml=xml+"<txtQ3>" + result.getInt("ISSUES_YEAR_QTY") + "</txtQ3>";
                     xml=xml+"<txtV3>" + result.getInt("ISSUES_YR_VALUE") + "</txtV3>";
                    // xml=xml+"<txtQ_total>" + result.getInt("TOTAL_YEAR_QTY") + "</txtQ_total>";
                    // xml=xml+"<txtV_total>" + result.getInt("TOTAL_YR_VALUE") + "</txtV_total>";
                     xml=xml+"<txtdepre_prev_yr>" + result.getInt("DEP_PREV_YEAR") + "</txtdepre_prev_yr>";
                     xml=xml+"<txtdepre_recieved>" + result.getInt("DEPRE_REC_AC") + "</txtdepre_recieved>";
                     xml=xml+"<txtdepre_allowed_yr>" + result.getInt("DEPRE_ALLOWED_YR") + "</txtdepre_allowed_yr>";
                   //  xml=xml+"<txttotal_depre>" + result.getInt("TOTAL_DEPRE") + "</txttotal_depre>";
                     xml=xml+"<dep_transfer>" + result.getInt("DEPRE_TR_AC") + "</dep_transfer>";
                     xml=xml+"<txtdepre_date>" + result.getString("DEPRE_UPTO_DATE") + "</txtdepre_date>";
                     xml=xml+"<txtnet_depre>" + result.getInt("NET_DEPRE_COST") + "</txtnet_depre>";
                     xml=xml+"<txtappor_grant>" + result.getInt("APP_PRE_YR") + "</txtappor_grant>";
                     xml=xml+"<txtappor_recieved>" + result.getInt("APP_GRANT_RECIEVED") + "</txtappor_recieved>";
                     xml=xml+"<txtappor_allowed2>" + result.getInt("APPRO_DURING_YR") + "</txtappor_allowed2>";
                   //  xml=xml+"<txttotal_appor>" + result.getInt("TOT_APP_GRANT") + "</txttotal_appor>";
                     xml=xml+"<txtapp_transfer>" + result.getInt("APP_GRANT_TR") + "</txtapp_transfer>";
                     xml=xml+"<appor_date>" + result.getString("APP_GRANT_UPTODATE") + "</appor_date>";
                         
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
        } else if(strCommand.equals("GoGet"))
        { 
        	int count=0;
        	System.out.println("\n*************\nGo Get\n**************\n");
            xml="<response><command>GoGet</command>";
            try 
            {
                System.out.println("goget");
             result = statement.executeQuery("select OPEN_BAL_QTY,OPENING_BAL_VALUE,RECIEPTS_YEAR_QTY,RECIEPTS_YR_VALUE,\n"+
            		 "ISSUES_YEAR_QTY,ISSUES_YR_VALUE,\n"+
            		 "DEP_PREV_YEAR,DEPRE_REC_AC,DEPRE_ALLOWED_YR,\n"+
            		 "DEPRE_TR_AC,DEPRE_UPTO_DATE,NET_DEPRE_COST,APP_PRE_YR,APP_GRANT_RECIEVED,APPRO_DURING_YR,\n"+
            		 "APP_GRANT_TR,APP_GRANT_UPTODATE,REMARKS,accounting_unit_id,\n"+
            		 "accounting_unit_office_id,financial_year,asset_code,ASSET_MAJOR_CLASS_CODE,asset_minor_class_code,PARTICULARS,\n" + 
            		 " (SELECT m.ASSET_MINOR_CLASS_DESC "+
            					" FROM FAS_ASSET_MINOR_CLASSIFICATION m "+
            					" WHERE m.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND m.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE "+
            					" )"+ 
            					"AS ASSET_MINOR_CLASS_DESC "+
             "from FAS_A52_REGISTER a" +
             " WHERE accounting_unit_id = " + unit_id +
	     "AND accounting_unit_office_id = " + office_id +
	     " AND financial_year = '" + financial_year + "'" +
	     " AND ASSET_MAJOR_CLASS_CODE="+assetmajor);
       // " AND ASSET_MINOR_CLASS_CODE="+assetminor);
             
           try
             {
        	   System.out.println("inside try");
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<asset_code>" + result.getString("asset_code") + "</asset_code>";
                	 xml=xml+"<ASSET_MINOR_CLASS_CODE>" + result.getInt("ASSET_MINOR_CLASS_CODE") + "</ASSET_MINOR_CLASS_CODE>";
                	 xml += "<PARTICULARS>" + result.getString("PARTICULARS") + "</PARTICULARS>"; 
                     xml=xml+"<OPEN_BAL_QTY>" + result.getInt("OPEN_BAL_QTY") + "</OPEN_BAL_QTY>";
                     xml=xml+"<OPENING_BAL_VALUE>" + result.getInt("OPENING_BAL_VALUE") + "</OPENING_BAL_VALUE>";
                     xml=xml+"<RECIEPTS_YEAR_QTY>" + result.getInt("RECIEPTS_YEAR_QTY") + "</RECIEPTS_YEAR_QTY>";
                     xml=xml+"<RECIEPTS_YR_VALUE>" + result.getInt("RECIEPTS_YR_VALUE") + "</RECIEPTS_YR_VALUE>";
                     xml=xml+"<ISSUES_YEAR_QTY>" + result.getInt("ISSUES_YEAR_QTY") + "</ISSUES_YEAR_QTY>";
                     xml=xml+"<ISSUES_YR_VALUE>" + result.getInt("ISSUES_YR_VALUE") + "</ISSUES_YR_VALUE>";
                     xml=xml+"<DEP_PREV_YEAR>" + result.getInt("DEP_PREV_YEAR") + "</DEP_PREV_YEAR>";
                     xml=xml+"<DEPRE_REC_AC>" + result.getInt("DEPRE_REC_AC") + "</DEPRE_REC_AC>";
                     xml=xml+"<DEPRE_ALLOWED_YR>" + result.getInt("DEPRE_ALLOWED_YR") + "</DEPRE_ALLOWED_YR>";
                     xml=xml+"<DEPRE_TR_AC>" + result.getInt("DEPRE_TR_AC") + "</DEPRE_TR_AC>";
                     xml=xml+"<DEPRE_UPTO_DATE>" + result.getString("DEPRE_UPTO_DATE") + "</DEPRE_UPTO_DATE>";
                     xml=xml+"<NET_DEPRE_COST>" + result.getInt("NET_DEPRE_COST") + "</NET_DEPRE_COST>";
                     xml=xml+"<APP_PRE_YR>" + result.getInt("APP_PRE_YR") + "</APP_PRE_YR>";
                     xml=xml+"<APP_GRANT_RECIEVED>" + result.getInt("APP_GRANT_RECIEVED") + "</APP_GRANT_RECIEVED>";
                     xml=xml+"<APPRO_DURING_YR>" + result.getInt("APPRO_DURING_YR") + "</APPRO_DURING_YR>";
                     xml=xml+"<APP_GRANT_TR>" + result.getInt("APP_GRANT_TR") + "</APP_GRANT_TR>";
                     xml=xml+"<APP_GRANT_UPTODATE>" + result.getString("APP_GRANT_UPTODATE") + "</APP_GRANT_UPTODATE>"; 
                     xml+="<ASSET_MAJOR_CLASS_CODE>"+result.getString("ASSET_MAJOR_CLASS_CODE")+"</ASSET_MAJOR_CLASS_CODE>";
 					xml+="<ASSET_MINOR_CLASS_CODE>"+result.getString("ASSET_MINOR_CLASS_CODE")+"</ASSET_MINOR_CLASS_CODE>";
 					xml+="<ASSET_MINOR_CLASS_DESC>"+result.getString("ASSET_MINOR_CLASS_DESC")+"</ASSET_MINOR_CLASS_DESC>";
                	 remarks = result.getString("remarks");
                	 if(remarks == null)
                	 {
                		 remarks = " ";
                	 }
                	 xml += "<remarks>" + remarks + "</remarks>";
                	 String qry="select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+assetmajor;
                	 //result1 = statement.executeQuery("select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+assetmajor+" order by ASSET_MINOR_CLASS_CODE ");
                	 pers=connection.prepareStatement(qry);
     				//System.out.println(qry);
     				result1=pers.executeQuery();
                	 while(result1.next())
                     { 
                    	 //valExists = "Yes";
                    	 xml+="<MinorCode"+count+">";
                    	 xml += "<ASSET_MINOR_CLASS_CODE>" + result1.getInt("ASSET_MINOR_CLASS_CODE") + "</ASSET_MINOR_CLASS_CODE>";
                    	 xml += "<ASSET_MINOR_CLASS_DESC>" + result1.getString("ASSET_MINOR_CLASS_DESC") + "</ASSET_MINOR_CLASS_DESC>";
                    	 xml+="</MinorCode"+count+">";
                     }
                	 
                	 
                	
                	 count++;
                 }

                 xml += "<exists>"+valExists+"</exists>";
                 xml += "<count>"+count+"</count>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from DB - Go GET: " + e);
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
        else if(strCommand.equals("loadMajor"))
        { 
        	System.out.println("\n*************\nloadMajor\n**************\n");
            xml="<response><command>loadMajor</command>";
            try 
            {
                /*System.out.println("bef res");
              System.out.println(unit_id);
              System.out.println(office_id);
              System.out.println(financial_year);
              System.out.println(asset_code);*/
             result = statement.executeQuery("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE");
             
                //System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_MAJOR_CLASS_CODE>" + result.getInt("ASSET_MAJOR_CLASS_CODE") + "</ASSET_MAJOR_CLASS_CODE>";
                	 xml += "<ASSET_MAJOR_CLASS_DESC>" + result.getString("ASSET_MAJOR_CLASS_DESC") + "</ASSET_MAJOR_CLASS_DESC>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from ASSET_MAJOR_CLASS_CODE: " + e);
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
        else if(strCommand.equals("loadMinor"))
        { 
        	System.out.println("\n*************\nloadMinor\n**************\n");
            xml="<response><command>loadMinor</command>";
           assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
            try 
            {
                
             result = statement.executeQuery("select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+assetmajor+" order by ASSET_MINOR_CLASS_CODE");
             
                //System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_MINOR_CLASS_CODE>" + result.getInt("ASSET_MINOR_CLASS_CODE") + "</ASSET_MINOR_CLASS_CODE>";
                	 xml += "<ASSET_MINOR_CLASS_DESC>" + result.getString("ASSET_MINOR_CLASS_DESC") + "</ASSET_MINOR_CLASS_DESC>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from ASSET_MINOR_CLASS_DESC: " + e);
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
        }  else if(strCommand.equals("loadAssetCode"))
        { 
        	System.out.println("\n*************\n  loadAssetCode \n**************\n");
            xml="<response><command>loadAssetCode</command>";
            assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
           // String unit_id=request.getParameter("unit_id");
            assetminor = Integer.parseInt(request.getParameter("assetminor"));
            
            
            try 
            {
                
             result = statement.executeQuery("select ASSET_CODE from FAS_A52_REGISTER where ACCOUNTING_UNIT_ID="+unit_id+" and ACCOUNTING_UNIT_OFFICE_ID="+office_id+" and ASSET_MAJOR_CLASS_CODE="+assetmajor+" and ASSET_MINOR_CLASS_CODE="+assetminor+" order by ASSET_CODE");
             
                //System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_CODE>" + result.getInt("ASSET_CODE") + "</ASSET_CODE>";
                	// xml += "<ASSET_MINOR_CLASS_DESC>" + result.getString("ASSET_MINOR_CLASS_DESC") + "</ASSET_MINOR_CLASS_DESC>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from ASSET_CODE: " + e);
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
        }  if(strCommand.equalsIgnoreCase("updateTotally")){
     	   
     	   try
            {
            	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            //	System.out.println("accounting_unit_id : " + unit_id);
            }
            catch(Exception e)
            { 
                System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
            }        
            
            try
            {
            	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
            	//System.out.println("accounting_unit_office_id : " + office_id);
            }
            catch(Exception e)
            { 
                System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
            } 
            try
            {
         	   assetmajor = Integer.parseInt(request.getParameter("cmbmajorasset"));
            	//System.out.println("assetmajor : " + assetmajor);
            }
            catch(Exception e)
            { 
                System.out.println("IGNORABLE Exception getting 'major' parameter ===> " + e);
            } 
            /*try
            {
         	   assetminor = Integer.parseInt(request.getParameter("cmbminorasset"));
            //	System.out.println("assetminor : " + assetminor);
            }
            catch(Exception e)
            { 
                System.out.println("IGNORABLE Exception getting 'minor parameter ===> " + e);
            } */
            try
            {
         	   financial_year =request.getParameter("cmbFinancialYear");
            //	System.out.println("financial_year : " + financial_year);
            }
            catch(Exception e)
            { 
                System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
            } 
     	System.out.println("inside update toolllay");
     	String[] assetMinorCode=request.getParameterValues("assMinorCode");
     	String[] assetcode=request.getParameterValues("assetcode");
     	String[] PARTICULARS=request.getParameterValues("PARTICULARS");
     	String[] OPEN_BAL_QTY=request.getParameterValues("OPEN_BAL_QTY");
     	String[] OPENING_BAL_VALUE=request.getParameterValues("OPENING_BAL_VALUE");
     	String[] RECIEPTS_YEAR_QTY=request.getParameterValues("RECIEPTS_YEAR_QTY");
     	
     	String[] RECIEPTS_YR_VALUE=request.getParameterValues("RECIEPTS_YR_VALUE");
     	String[] ISSUES_YEAR_QTY=request.getParameterValues("ISSUES_YEAR_QTY");
     	String[] ISSUES_YR_VALUE=request.getParameterValues("ISSUES_YR_VALUE");
     	String[] DEP_PREV_YEAR=request.getParameterValues("DEP_PREV_YEAR");
     	String[] DEPRE_REC_AC=request.getParameterValues("DEPRE_REC_AC");
     	
      	String[] DEPRE_ALLOWED_YR =request.getParameterValues("DEPRE_ALLOWED_YR");
      	String[] DEPRE_TR_AC=request.getParameterValues("DEPRE_TR_AC");
      	String[] DEPRE_UPTO_DATE =request.getParameterValues("DEPRE_UPTO_DATE");
      	String[] NET_DEPRE_COST=request.getParameterValues("NET_DEPRE_COST");			
      	String[] APP_PRE_YR=request.getParameterValues("APP_PRE_YR");	
      	String[] APP_GRANT_RECIEVED = request.getParameterValues("APP_GRANT_RECIEVED");
      	String[] APPRO_DURING_YR =request.getParameterValues("APPRO_DURING_YR");
      	String[] APP_GRANT_TR = request.getParameterValues("APP_GRANT_TR");
      	String[] APP_GRANT_UPTODATE = request.getParameterValues("APP_GRANT_UPTODATE");
               
      	String[] remark=request.getParameterValues("remark");
     	
     	int ss=assetcode.length;
     	
       System.out.println("assetcode.length =="+ss);
  	 xml="<response><command>updateTotally1</command>";
     	for(int ii=0;ii<assetcode.length;ii++){
     		System.out.println("------------------"+ii+"-----------------");
     		int assetno=Integer.parseInt(assetcode[ii]);
     		System.out.println("PARTICULARS "+PARTICULARS[ii]);
     		System.out.println("OPEN_BAL_QTY "+OPEN_BAL_QTY[ii]);
     		System.out.println("OPENING_BAL_VALUE "+OPENING_BAL_VALUE[ii]);
     		System.out.println("RECIEPTS_YEAR_QTY "+RECIEPTS_YEAR_QTY[ii]);
     		
     		System.out.println("RECIEPTS_YR_VALUE "+RECIEPTS_YR_VALUE[ii]);
     		System.out.println("ISSUES_YEAR_QTY "+ISSUES_YEAR_QTY[ii]);
     		System.out.println("ISSUES_YR_VALUE "+ISSUES_YR_VALUE[ii]);
     		System.out.println("DEP_PREV_YEAR "+DEP_PREV_YEAR[ii]);
     		
     		System.out.println("DEPRE_REC_AC "+DEPRE_REC_AC[ii]);
     		System.out.println((DEPRE_ALLOWED_YR[ii]));
     		System.out.println(DEPRE_TR_AC[ii]);
     		System.out.println(DEPRE_UPTO_DATE[ii]);
     		System.out.println(NET_DEPRE_COST[ii]);
     		System.out.println(APP_PRE_YR[ii]);
     		System.out.println(APP_GRANT_RECIEVED[ii]);
     		System.out.println(APPRO_DURING_YR[ii]);
     		System.out.println(APP_GRANT_TR[ii]);
     		System.out.println(APP_GRANT_UPTODATE[ii]);
     		System.out.println(remark[ii]);
              
     		int assetMin=Integer.parseInt(assetMinorCode[ii]);
     		 String particul=PARTICULARS[ii];
     		  int openqty=Integer.parseInt(OPEN_BAL_QTY[ii]); 
               int openvalu=Integer.parseInt(OPENING_BAL_VALUE[ii]);
               int receyrqt=Integer.parseInt(RECIEPTS_YEAR_QTY[ii]);
               int repyrval=Integer.parseInt(RECIEPTS_YR_VALUE[ii]);
               int issuyrqty=Integer.parseInt(ISSUES_YEAR_QTY[ii]);
               int issval=Integer.parseInt(ISSUES_YR_VALUE[ii]);
               int depyr=Integer.parseInt(DEP_PREV_YEAR[ii]);
              int deprc=Integer.parseInt(DEPRE_REC_AC[ii]); 
              int depallow=Integer.parseInt(DEPRE_ALLOWED_YR[ii]);
             int deptr=Integer.parseInt(DEPRE_TR_AC[ii]);
              String depupdate=DEPRE_UPTO_DATE[ii];
              int net=Integer.parseInt(NET_DEPRE_COST[ii]);
              int appyr=Integer.parseInt(APP_PRE_YR[ii]);
              int appgrant=Integer.parseInt(APP_GRANT_RECIEVED[ii]);
               int appro=Integer.parseInt(APPRO_DURING_YR[ii]);
             int apptr=Integer.parseInt(APP_GRANT_TR[ii]);
              String appup=APP_GRANT_UPTODATE[ii];
              String rem=remark[ii];
     		        	
              try 
              {
               String sqlUpdate = "UPDATE FAS_A52_REGISTER " +
  				"SET OPEN_BAL_QTY=?,OPENING_BAL_VALUE=?,RECIEPTS_YEAR_QTY=?,RECIEPTS_YR_VALUE=?," + 
  				"ISSUES_YEAR_QTY=?,ISSUES_YR_VALUE=?," + 
  				"DEP_PREV_YEAR=?,DEPRE_REC_AC=?,DEPRE_ALLOWED_YR=?," + 
  				"DEPRE_TR_AC=?,DEPRE_UPTO_DATE=?,NET_DEPRE_COST=?,APP_PRE_YR=?," + 
  				"APP_GRANT_RECIEVED=?,APPRO_DURING_YR=?,APP_GRANT_TR=?," + 
  				"APP_GRANT_UPTODATE=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PARTICULARS=?, ASSET_MINOR_CLASS_CODE=? "+
                                  "WHERE accounting_unit_id = ? AND accounting_unit_office_id = ? " +
  				" AND financial_year = ?  AND asset_code = ? and ASSET_MAJOR_CLASS_CODE=?";
               System.out.println(sqlUpdate);
  		   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
  		  
                  ps.setInt(1,openqty); 
                  ps.setInt(2,openvalu);
                  ps.setInt(3,receyrqt);
                  ps.setInt(4,repyrval);
                  ps.setInt(5,issuyrqty);
                  ps.setInt(6,issval);
                  ps.setInt(7,depyr);
                  ps.setInt(8,deprc); 
                  ps.setInt(9,depallow);
                  ps.setInt(10,deptr);
                  ps.setString(11,depupdate);
                  ps.setInt(12,net);
                  ps.setInt(13,appyr);
                  ps.setInt(14,appgrant);
                  ps.setInt(15,appro);
                  ps.setInt(16,apptr);
                  ps.setString(17,appup);
                  ps.setString(18,rem);
                  ps.setString(19,userid);
                  ps.setTimestamp(20,ts);
                  ps.setString(21,particul);
                  ps.setInt(22,assetMin);
                  ps.setInt(23,unit_id);                 
                  ps.setInt(24,office_id);                   
                  ps.setString(25,financial_year);                    
                  ps.setInt(26,assetno);
                  ps.setInt(27,assetmajor);                
                  up=ps.executeUpdate();
                  System.out.println("up ..."+up);
                  if(up>0){
      				//System.out.println("k>0");
      				xml+="<flag>success</flag>";
      			}else{
      				//System.out.println("k<0");
      				xml+="<flag>failure</flag>";
      			}
      			
                  
				} catch (Exception e) {
					System.out.println("exception......in update calll send message "+e);
					
					/*connection.rollback();
                  sendMessage(response,"Failed to update Data","ok");  */
				}
				xml+="</response>";
    /*
				      if(up>0)
						{
							connection.commit();
							sendMessage(response,"Records updated successfully ","ok");
						}
						else
						{
							connection.rollback();	
							sendMessage(response,"Record Not updated  ","ok");
						}*/
      
             }

     	}
        
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
        
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
        int assetmajor=0;
        int assetminor=0;
	   	//String date_transaction = null;
	   	String financial_year = null;
	   	/*int asset_code = 0;
	   	int txtQ1  = 0;
	   	int txtQ2  = 0;
                int txtQ3  =  0;
                int txtV1  = 0;
                int txtV2  = 0;
                int txtV3  =  0;
                String appor_date=null;
	   	int txtQ_total = 0;
	   	int txtV_total = 0;
	   	
	   	int txtdepre_prev_yr = 0;
	   	int txtdepre_recieved  = 0;
	   	int txtdepre_allowed_yr  = 0;
	   	int  txttotal_depre   =0;
	        int dep_transfer  = 0;
	   	String txtdepre_date  = "";
	   	int txtnet_depre  = 0;
	   	int txtappor_grant  = 0;
	   	int txtappor_recieved  = 0;
	   	int aoor_a110wed  = 0;
	   	int txttotal_appor  = 0;
	   	int txtapp_transfer  = 0;
       int txtdepre_date2=0,txtnet_depre2=0,txtappor_grant2=0,txtappor_recieved2=0,txtappor_allowed2=0,txttotal_appor2=0;
	   	String remarks = "";*/
	   	
	   	
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
        int up=0;
        try{
       
           if(strCommand.equalsIgnoreCase("updateTotally")){
        	   
        	   try
               {
               	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
               //	System.out.println("accounting_unit_id : " + unit_id);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
               }        
               
               try
               {
               	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
               	//System.out.println("accounting_unit_office_id : " + office_id);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
               } 
               try
               {
            	   assetmajor = Integer.parseInt(request.getParameter("cmbmajorasset"));
               	//System.out.println("assetmajor : " + assetmajor);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'major' parameter ===> " + e);
               } 
               try
               {
            	   assetminor = Integer.parseInt(request.getParameter("cmbminorasset"));
               //	System.out.println("assetminor : " + assetminor);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'minor parameter ===> " + e);
               } 
               try
               {
            	   financial_year =request.getParameter("cmbFinancialYear");
               //	System.out.println("financial_year : " + financial_year);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
               } 
        	//System.out.println("inside update toolllay");
        	
        	String[] assetcode=request.getParameterValues("assetcode");
        	String[] PARTICULARS=request.getParameterValues("PARTICULARS");
        	String[] OPEN_BAL_QTY=request.getParameterValues("OPEN_BAL_QTY");
        	String[] OPENING_BAL_VALUE=request.getParameterValues("OPENING_BAL_VALUE");
        	String[] RECIEPTS_YEAR_QTY=request.getParameterValues("RECIEPTS_YEAR_QTY");
        	
        	String[] RECIEPTS_YR_VALUE=request.getParameterValues("RECIEPTS_YR_VALUE");
        	String[] ISSUES_YEAR_QTY=request.getParameterValues("ISSUES_YEAR_QTY");
        	String[] ISSUES_YR_VALUE=request.getParameterValues("ISSUES_YR_VALUE");
        	String[] DEP_PREV_YEAR=request.getParameterValues("DEP_PREV_YEAR");
        	String[] DEPRE_REC_AC=request.getParameterValues("DEPRE_REC_AC");
        	
         	String[] DEPRE_ALLOWED_YR =request.getParameterValues("DEPRE_ALLOWED_YR");
         	String[] DEPRE_TR_AC=request.getParameterValues("DEPRE_TR_AC");
         	String[] DEPRE_UPTO_DATE =request.getParameterValues("DEPRE_UPTO_DATE");
         	String[] NET_DEPRE_COST=request.getParameterValues("NET_DEPRE_COST");			
         	String[] APP_PRE_YR=request.getParameterValues("APP_PRE_YR");	
         	String[] APP_GRANT_RECIEVED = request.getParameterValues("APP_GRANT_RECIEVED");
         	String[] APPRO_DURING_YR =request.getParameterValues("APPRO_DURING_YR");
         	String[] APP_GRANT_TR = request.getParameterValues("APP_GRANT_TR");
         	String[] APP_GRANT_UPTODATE = request.getParameterValues("APP_GRANT_UPTODATE");
                  
         	String[] remark=request.getParameterValues("remark");
        	
        	int ss=assetcode.length;
        	
          System.out.println("assetcode.length =="+ss);
     	// xml="<response><command>updateTotally1</command>";
        	for(int ii=0;ii<assetcode.length;ii++){
        		System.out.println("------------------"+ii+"-----------------");
        		int assetno=Integer.parseInt(assetcode[ii]);
        	/*	System.out.println("PARTICULARS "+PARTICULARS[ii]);
        		System.out.println("OPEN_BAL_QTY "+OPEN_BAL_QTY[ii]);
        		System.out.println("OPENING_BAL_VALUE "+OPENING_BAL_VALUE[ii]);
        		System.out.println("RECIEPTS_YEAR_QTY "+RECIEPTS_YEAR_QTY[ii]);
        		
        		System.out.println("RECIEPTS_YR_VALUE "+RECIEPTS_YR_VALUE[ii]);
        		System.out.println("ISSUES_YEAR_QTY "+ISSUES_YEAR_QTY[ii]);
        		System.out.println("ISSUES_YR_VALUE "+ISSUES_YR_VALUE[ii]);
        		System.out.println("DEP_PREV_YEAR "+DEP_PREV_YEAR[ii]);
        		
        		System.out.println("DEPRE_REC_AC "+DEPRE_REC_AC[ii]);
        		System.out.println((DEPRE_ALLOWED_YR[ii]));
        		System.out.println(DEPRE_TR_AC[ii]);
        		System.out.println(DEPRE_UPTO_DATE[ii]);
        		System.out.println(NET_DEPRE_COST[ii]);
        		System.out.println(APP_PRE_YR[ii]);
        		System.out.println(APP_GRANT_RECIEVED[ii]);
        		System.out.println(APPRO_DURING_YR[ii]);
        		System.out.println(APP_GRANT_TR[ii]);
        		System.out.println(APP_GRANT_UPTODATE[ii]);
        		System.out.println(remark[ii]);*/
                 
        		
        		  int openqty=Integer.parseInt(OPEN_BAL_QTY[ii]); 
                  int openvalu=Integer.parseInt(OPENING_BAL_VALUE[ii]);
                  int receyrqt=Integer.parseInt(RECIEPTS_YEAR_QTY[ii]);
                  int repyrval=Integer.parseInt(RECIEPTS_YR_VALUE[ii]);
                  int issuyrqty=Integer.parseInt(ISSUES_YEAR_QTY[ii]);
                  int issval=Integer.parseInt(ISSUES_YR_VALUE[ii]);
                  int depyr=Integer.parseInt(DEP_PREV_YEAR[ii]);
                 int deprc=Integer.parseInt(DEPRE_REC_AC[ii]); 
                 int depallow=Integer.parseInt(DEPRE_ALLOWED_YR[ii]);
                int deptr=Integer.parseInt(DEPRE_TR_AC[ii]);
                 String depupdate=DEPRE_UPTO_DATE[ii];
                 int net=Integer.parseInt(NET_DEPRE_COST[ii]);
                 int appyr=Integer.parseInt(APP_PRE_YR[ii]);
                 int appgrant=Integer.parseInt(APP_GRANT_RECIEVED[ii]);
                  int appro=Integer.parseInt(APPRO_DURING_YR[ii]);
                int apptr=Integer.parseInt(APP_GRANT_TR[ii]);
                 String appup=APP_GRANT_UPTODATE[ii];
                 String rem=remark[ii];
        		        	
                 try 
                 {
                  String sqlUpdate = "UPDATE FAS_A52_REGISTER " +
     				"SET OPEN_BAL_QTY=?,OPENING_BAL_VALUE=?,RECIEPTS_YEAR_QTY=?,RECIEPTS_YR_VALUE=?," + 
     				"ISSUES_YEAR_QTY=?,ISSUES_YR_VALUE=?," + 
     				"DEP_PREV_YEAR=?,DEPRE_REC_AC=?,DEPRE_ALLOWED_YR=?," + 
     				"DEPRE_TR_AC=?,DEPRE_UPTO_DATE=?,NET_DEPRE_COST=?,APP_PRE_YR=?," + 
     				"APP_GRANT_RECIEVED=?,APPRO_DURING_YR=?,APP_GRANT_TR=?," + 
     				"APP_GRANT_UPTODATE=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PARTICULARS=?,ASSET_MINOR_CLASS_CODE=? "+
                                     "WHERE accounting_unit_id = ? AND accounting_unit_office_id = ? " +
     				" AND financial_year = ?  AND asset_code = ? and ASSET_MAJOR_CLASS_CODE=?";
                  System.out.println(sqlUpdate);
     		   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
     		  
                     ps.setInt(1,openqty); 
                     ps.setInt(2,openvalu);
                     ps.setInt(3,receyrqt);
                     ps.setInt(4,repyrval);
                     ps.setInt(5,issuyrqty);
                     ps.setInt(6,issval);
                     ps.setInt(7,depyr);
                     ps.setInt(8,deprc); 
                     ps.setInt(9,depallow);
                     ps.setInt(10,deptr);
                     ps.setString(11,depupdate);
                     ps.setInt(12,net);
                     ps.setInt(13,appyr);
                     ps.setInt(14,appgrant);
                     ps.setInt(15,appro);
                     ps.setInt(16,apptr);
                     ps.setString(17,appup);
                     ps.setString(18,rem);
                     ps.setString(19,userid);
                     ps.setTimestamp(20,ts);
                     ps.setString(21,PARTICULARS[ii]);
                     ps.setInt(22,assetminor);
                     ps.setInt(23,unit_id);                 
                     ps.setInt(24,office_id);                   
                     ps.setString(25,financial_year);                    
                     ps.setInt(26,assetno);
                     ps.setInt(27,assetmajor);
                    
                    
                     up=ps.executeUpdate();
                     System.out.println("up ..."+up);
                     
                     
 				} catch (Exception e) {
 					System.out.println("exception......in update calll send message "+e);
					
					connection.rollback();
                     sendMessage(response,"Failed to update Data","ok");  
				}
       
				      if(up>0)
						{
							connection.commit();
							sendMessage(response,"Records updated successfully ","ok");
						}
						else
						{
							connection.rollback();	
							sendMessage(response,"Record Not updated  ","ok");
						}
         
                }
  
        	}//----if end
        	
        }catch(Exception ss){
        	System.out.println("SQL exception   "+ss);
        	
        }
        }
 
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);
        }
        catch(Exception e)
        {
                System.out.println("error in messenger"+e);
        }
    }
}

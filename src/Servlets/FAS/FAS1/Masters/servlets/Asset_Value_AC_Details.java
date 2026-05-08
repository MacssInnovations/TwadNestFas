package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Asset_Value_AC_Details extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        

    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
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
        int accounting_unit_id = 0;
        int accounting_unit_office_id = 0;
	   	String date_transaction = null;
	   	String financial_year = null;
	   	int asset_code = 0;
	   	int num_ac_office_id = 0;
	   	String asset_type_code = "G";
	   	int asset_major_class_code = 0;
	   	int asset_minor_class_code = 0;
	   	int depreciation_cate_code = 0;
	   	int apportion_grant_cate_code = 0;
	   	int alias_code = 0;
	   	String particulars = null;
	   	int number_of_assets = 0;
	   	String office_id_asset_is_available = null;
	   	String ownership_code = null;
	   	String donating_agency_name = "";
	   	int project_code = 0;
	   	int year_of_purchase = 0;
	   	int month_of_purchase = 0;
	   	int original_cost = 0;
	   	String under_warrenty = null;
	   	String warranty_period_from = "";
	   	String warranty_period_upto = "";
	   	int current_value_after_depre = 0;
	   	int current_value_after_apportion = 0;
	   	String is_under_usable_condition = null;
	   	String is_in_use = null;
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
        	accounting_unit_id = Integer.parseInt(request.getParameter("accounting_unit_id"));
        	System.out.println("accounting_unit_id : " + accounting_unit_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
        }        
        
        try
        {
        	accounting_unit_office_id = Integer.parseInt(request.getParameter("accounting_unit_office_id"));
        	System.out.println("accounting_unit_office_id : " + accounting_unit_office_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
        }     
 
        try
        {
        	date_transaction = request.getParameter("date_transaction");
/*        	
 			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;                    
            d = dateFormat.parse(date_transaction.trim());
            dateFormat.applyPattern("dd-MMM-yyyy");
            date_transaction = dateFormat.format(d);
*/
            System.out.println("date_transaction : " + date_transaction);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'date_transaction' parameter ===> " + e);
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
        	num_ac_office_id = Integer.parseInt(request.getParameter("num_ac_office_id"));
        	System.out.println("num_ac_office_id : " + num_ac_office_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'num_ac_office_id' parameter ===> " + e);
        }     
        
        try
        {
        	asset_type_code = request.getParameter("asset_type_code");
        	System.out.println("asset_type_code : " + asset_type_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'asset_type_code' parameter ===> " + e);
        }        
        
        try
        {
        	asset_major_class_code = Integer.parseInt(request.getParameter("asset_major_class_code"));
        	System.out.println("asset_major_class_code : " + asset_major_class_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'asset_major_class_code' parameter ===> " + e);
        }     
	   	
        try
        {
        	asset_minor_class_code = Integer.parseInt(request.getParameter("asset_minor_class_code"));
        	System.out.println("asset_minor_class_code : " + asset_minor_class_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'asset_minor_class_code' parameter ===> " + e);
        }     
        
        try
        {
        	depreciation_cate_code = Integer.parseInt(request.getParameter("depreciation_cate_code"));
        	System.out.println("depreciation_cate_code : " + depreciation_cate_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'depreciation_cate_code' parameter ===> " + e);
        }     
	
        try
        {
        	apportion_grant_cate_code = Integer.parseInt(request.getParameter("apportion_grant_cate_code"));
        	System.out.println("apportion_grant_cate_code : " + apportion_grant_cate_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'apportion_grant_cate_code' parameter ===> " + e);
        }     
	
        try
        {
        	alias_code = Integer.parseInt(request.getParameter("alias_code"));
        	System.out.println("alias_code : " + alias_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'alias_code' parameter ===> " + e);
        }     
	
        try
        {
        	particulars = request.getParameter("particulars");
        	System.out.println("particulars : " + particulars);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'particulars' parameter ===> " + e);
        }     
    	
        try
        {
        	number_of_assets = Integer.parseInt(request.getParameter("number_of_assets"));
        	System.out.println("number_of_assets : " + number_of_assets);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'number_of_assets' parameter ===> " + e);
        }     
	
        try
        {
        	office_id_asset_is_available = request.getParameter("office_id_asset_is_available");
        	System.out.println("office_id_asset_is_available : " + office_id_asset_is_available);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'office_id_asset_is_available' parameter ===> " + e);
        }     
	    	
        try
        {
        	ownership_code = request.getParameter("ownership_code");
        	System.out.println("ownership_code : " + ownership_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'ownership_code' parameter ===> " + e);
        }     
		
        try
        {
        	donating_agency_name = request.getParameter("donating_agency_name");
        	System.out.println("donating_agency_name : " + donating_agency_name);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'donating_agency_name' parameter ===> " + e);
        }     
       	
        try
        {
        	project_code = Integer.parseInt(request.getParameter("project_code"));
        	System.out.println("project_code : " + project_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'project_code' parameter ===> " + e);
        }     
      	
        try
        {
        	year_of_purchase = Integer.parseInt(request.getParameter("year_of_purchase"));
        	System.out.println("year_of_purchase : " + year_of_purchase);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'year_of_purchase' parameter ===> " + e);
        }     
       	
        try
        {
        	month_of_purchase = Integer.parseInt(request.getParameter("month_of_purchase"));
        	System.out.println("month_of_purchase : " + month_of_purchase);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'month_of_purchase' parameter ===> " + e);
        }     
      	
        try
        {
        	original_cost = Integer.parseInt(request.getParameter("original_cost"));
        	System.out.println("original_cost : " + original_cost);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'original_cost' parameter ===> " + e);
        }     
		
        try
        {
        	under_warrenty = request.getParameter("under_warrenty");
        	System.out.println("under_warrenty : " + under_warrenty);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'under_warrenty' parameter ===> " + e);
        }     
		
        try
        {
        	warranty_period_from = request.getParameter("warranty_period_from");
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;                    
            d = dateFormat.parse(warranty_period_from.trim());
            dateFormat.applyPattern("dd-MMM-yyyy");
            warranty_period_from = dateFormat.format(d);
            
        	System.out.println("warranty_period_from : " + warranty_period_from);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'warranty_period_from' parameter ===> " + e);
        }     
		
        try
        {
        	warranty_period_upto = request.getParameter("warranty_period_upto");
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;                    
            d = dateFormat.parse(warranty_period_upto.trim());
            dateFormat.applyPattern("dd-MMM-yyyy");
            warranty_period_upto = dateFormat.format(d);

        	System.out.println("warranty_period_upto : " + warranty_period_upto);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'warranty_period_upto' parameter ===> " + e);
        }     
       	
        try
        {
        	current_value_after_depre = Integer.parseInt(request.getParameter("current_value_after_depre"));
        	System.out.println("current_value_after_depre : " + current_value_after_depre);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'current_value_after_depre' parameter ===> " + e);
        }     
      	
        try
        {
        	current_value_after_apportion = Integer.parseInt(request.getParameter("current_value_after_apportion"));
        	System.out.println("current_value_after_apportion : " + current_value_after_apportion);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'current_value_after_apportion' parameter ===> " + e);
        }     
		
        try
        {
        	is_under_usable_condition = request.getParameter("is_under_usable_condition");
        	System.out.println("is_under_usable_condition : " + is_under_usable_condition);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'is_under_usable_condition' parameter ===> " + e);
        }     
		
        try
        {
        	is_in_use = request.getParameter("is_in_use");
        	System.out.println("is_in_use : " + is_in_use);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'is_in_use' parameter ===> " + e);
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
             String sqlDelete = "DELETE FROM fas_asset_val_ac_details " +
								"WHERE accounting_unit_id = ? " +
								" AND accounting_unit_office_id = ? " +
								" AND financial_year = ? " +
								" AND asset_code = ? ";
             PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setInt(1, accounting_unit_id);
		   	 ps.setInt(2, accounting_unit_office_id);
		   	 ps.setString(3, financial_year);
		   	 ps.setInt(4, asset_code);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<accounting_unit_id>" + accounting_unit_id + "</accounting_unit_id>";
             xml=xml+"<accounting_unit_office_id>" + accounting_unit_office_id + "</accounting_unit_office_id>";
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
             String sqlUpdate = "UPDATE fas_asset_val_ac_details " +
								"SET date_transaction = to_date(?,'dd/mm/yyyy'), " +
								"  num_ac_office_id = ?, " +
								"  asset_type_code = ?, " +
								"  asset_major_class_code = ?, " +
								"  asset_minor_class_code = ?, " +
								"  depreciation_cate_code = ?, " +
								"  apportion_grant_cate_code = ?, " +
								"  alias_code = ?, " +
								"  particulars = ?, " +
								"  number_of_assets = ?, " +
								"  office_id_asset_is_available = ?, " +
								"  ownership_code = ?, " +
								"  donating_agency_name = ?, " +
								"  project_code = ?, " +
								"  year_of_purchase = ?, " +
								"  month_of_purchase = ?, " +
								"  original_cost = ?, " +
								"  under_warrenty = ?, " +
								"  warranty_period_from = ?, " +
								"  warranty_period_upto = ?, " +
								"  current_value_after_depre = ?, " +
								"  current_value_after_apportion = ?, " +
								"  is_under_usable_condition = ?, " +
								"  is_in_use = ?, " +
								"  remarks = ?, " +
								"  updated_by_userid = ?, " +
								"  updated_date = systimestamp " +
								"WHERE accounting_unit_id = ? " +
								" AND accounting_unit_office_id = ? " +
								" AND financial_year = ? " +
								" AND asset_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
		   	 ps.setString(1,date_transaction);
		   	 ps.setInt(2,num_ac_office_id);
		   	 ps.setString(3,asset_type_code);
		   	 ps.setInt(4,asset_major_class_code);
		   	 ps.setInt(5,asset_minor_class_code);
		   	 ps.setInt(6,depreciation_cate_code);
		   	 ps.setInt(7,apportion_grant_cate_code);
		   	 ps.setInt(8,alias_code);
		   	 ps.setString(9,particulars);
		   	 ps.setInt(10,number_of_assets);
		   	 ps.setString(11,office_id_asset_is_available);
		   	 ps.setString(12,ownership_code);
		   	 ps.setString(13,donating_agency_name);
		   	 ps.setInt(14,project_code);
		   	 ps.setInt(15,year_of_purchase);
		   	 ps.setInt(16,month_of_purchase);
		   	 ps.setInt(17,original_cost);
		   	 ps.setString(18,under_warrenty);
		   	 ps.setString(19,warranty_period_from);
		   	 ps.setString(20,warranty_period_upto);
		   	 ps.setInt(21,current_value_after_depre);
		   	 ps.setInt(22,current_value_after_apportion);
		   	 ps.setString(23,is_under_usable_condition);
		   	 ps.setString(24,is_in_use);
		   	 ps.setString(25,remarks);
		   	 ps.setString(26,userid);
		   	 ps.setInt(27,accounting_unit_id);
		   	 ps.setInt(28,accounting_unit_office_id);
		     ps.setString(29,financial_year);
		   	 ps.setInt(30,asset_code);

		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<date_transaction>" + date_transaction + "</date_transaction>";
             xml=xml+"<num_ac_office_id>" + num_ac_office_id + "</num_ac_office_id>";
             xml=xml+"<asset_type_code>" + asset_type_code + "</asset_type_code>";
             xml=xml+"<asset_major_class_code>" + asset_major_class_code + "</asset_major_class_code>";
             xml=xml+"<asset_minor_class_code>" + asset_minor_class_code + "</asset_minor_class_code>";
             xml=xml+"<depreciation_cate_code>" + depreciation_cate_code + "</depreciation_cate_code>";
             xml=xml+"<apportion_grant_cate_code>" + apportion_grant_cate_code + "</apportion_grant_cate_code>";
             xml=xml+"<particulars>" + particulars + "</particulars>";
             xml=xml+"<number_of_assets>" + number_of_assets + "</number_of_assets>";
             xml=xml+"<office_id_asset_is_available>" + office_id_asset_is_available + "</office_id_asset_is_available>";
             xml=xml+"<ownership_code>" + ownership_code + "</ownership_code>";
             xml=xml+"<donating_agency_name>" + donating_agency_name + "</donating_agency_name>";
             xml=xml+"<project_code>" + project_code + "</project_code>";
             xml=xml+"<year_of_purchase>" + year_of_purchase + "</year_of_purchase>";
             xml=xml+"<month_of_purchase>" + month_of_purchase + "</month_of_purchase>";
             xml=xml+"<original_cost>" + original_cost + "</original_cost>";
             xml=xml+"<under_warrenty>" + under_warrenty + "</under_warrenty>";
             xml=xml+"<warranty_period_from>" + warranty_period_from + "</warranty_period_from>";
             xml=xml+"<warranty_period_upto>" + warranty_period_upto + "</warranty_period_upto>";
             xml=xml+"<current_value_after_depre>" + current_value_after_depre + "</current_value_after_depre>";
             xml=xml+"<current_value_after_apportion>" + current_value_after_apportion + "</current_value_after_apportion>";
             xml=xml+"<is_under_usable_condition>" + is_under_usable_condition + "</is_under_usable_condition>";
             xml=xml+"<is_in_use>" + is_in_use + "</is_in_use>";
             xml=xml+"<remarks>" + remarks + "</remarks>";
             xml=xml+"<userid>" + userid + "</userid>";
             xml=xml+"<accounting_unit_id>" + accounting_unit_id + "</accounting_unit_id>";
             xml=xml+"<accounting_unit_office_id>" + accounting_unit_office_id + "</accounting_unit_office_id>";
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

            
            
            /* Generate Asset Code     --    max(Asset_Code) + 1   */
            //////////////////// START - Generate Asset Code ///////////////////////
            try 
            {
             String sqlMaxAssetCode = "SELECT MAX(asset_code) AS maxasset " +
										"FROM fas_asset_val_ac_details " +
										"WHERE accounting_unit_id = " + accounting_unit_id +
										" AND accounting_unit_office_id = " + accounting_unit_office_id +
										" AND financial_year = '" + financial_year + "'";
             result = statement.executeQuery(sqlMaxAssetCode);
             if(result.next())
             { 
            	 asset_code = result.getInt("maxasset");
            	 System.out.println("asset_code : " + asset_code);
             }
             asset_code++;
             System.out.println("\n--------------------\naccounting_unit_id : " + accounting_unit_id + "\naccounting_unit_office_id : " + accounting_unit_office_id + "\nfinancial_year : " + financial_year + "\nasset_code : " + asset_code + "--------------------\n");
            }catch(Exception e)
            {
           	 System.out.println("Exception ");
            }
            ///////////////////// END - Generate Asset Code ////////////////////////

            
            
            try 
            {
             String sqlAdd = "INSERT INTO fas_asset_val_ac_details" +
			             		" (date_transaction, " +
			             		"  financial_year, " +
			             		"  asset_code, " +
			             		"  num_ac_office_id, " +
			             		"  asset_type_code, " +
			             		"  asset_major_class_code, " +
			             		"  asset_minor_class_code, " +
			             		"  depreciation_cate_code, " +
			             		"  apportion_grant_cate_code, " +
			             		"  alias_code, " +
			             		"  particulars, " +
			             		"  number_of_assets, " +
			             		"  office_id_asset_is_available, " +
			             		"  ownership_code, " +
			             		"  donating_agency_name, " +
			             		"  project_code, " +
			             		"  year_of_purchase, " +
			             		"  month_of_purchase, " +
			             		"  original_cost, " +
			             		"  under_warrenty, " +
			             		"  warranty_period_from, " +
			             		"  warranty_period_upto, " +
			             		"  current_value_after_depre, " +
			             		"  current_value_after_apportion, " +
			             		"  is_under_usable_condition, " +
			             		"  is_in_use, " +
			             		"  remarks, " +
			             		"  updated_by_userid, " +
			             		"  updated_date, " +
			             		"  accounting_unit_id, " +
			             		"  accounting_unit_office_id)" +
			             		" VALUES(to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,systimestamp,?,?)";
		   	 PreparedStatement ps = connection.prepareStatement(sqlAdd);
		   	 ps.setString(1,date_transaction);
		     ps.setString(2,financial_year);
		   	 ps.setInt(3,asset_code);
		   	 ps.setInt(4,num_ac_office_id);
		   	 ps.setString(5,asset_type_code);
		   	 ps.setInt(6,asset_major_class_code);
		   	 ps.setInt(7,asset_minor_class_code);
		   	 ps.setInt(8,depreciation_cate_code);
		   	 ps.setInt(9,apportion_grant_cate_code);
		   	 ps.setInt(10,alias_code);
		   	 ps.setString(11,particulars);
		   	 ps.setInt(12,number_of_assets);
		   	 ps.setString(13,office_id_asset_is_available);
		   	 ps.setString(14,ownership_code);
		   	 ps.setString(15,donating_agency_name);
		   	 ps.setInt(16,project_code);
		   	 ps.setInt(17,year_of_purchase);
		   	 ps.setInt(18,month_of_purchase);
		   	 ps.setInt(19,original_cost);
		   	 ps.setString(20,under_warrenty);
		   	 ps.setString(21,warranty_period_from);
		   	 ps.setString(22,warranty_period_upto);
		   	 ps.setInt(23,current_value_after_depre);
		   	 ps.setInt(24,current_value_after_apportion);
		   	 ps.setString(25,is_under_usable_condition);
		   	 ps.setString(26,is_in_use);
		   	 ps.setString(27,remarks);
		   	 ps.setString(28,userid);
		   	 ps.setInt(29,accounting_unit_id);
		   	 ps.setInt(30,accounting_unit_office_id);
		   	
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<date_transaction>" + date_transaction + "</date_transaction>";
             xml=xml+"<num_ac_office_id>" + num_ac_office_id + "</num_ac_office_id>";
             xml=xml+"<asset_type_code>" + asset_type_code + "</asset_type_code>";
             xml=xml+"<asset_major_class_code>" + asset_major_class_code + "</asset_major_class_code>";
             xml=xml+"<asset_minor_class_code>" + asset_minor_class_code + "</asset_minor_class_code>";
             xml=xml+"<depreciation_cate_code>" + depreciation_cate_code + "</depreciation_cate_code>";
             xml=xml+"<apportion_grant_cate_code>" + apportion_grant_cate_code + "</apportion_grant_cate_code>";
             xml=xml+"<particulars>" + particulars + "</particulars>";
             xml=xml+"<number_of_assets>" + number_of_assets + "</number_of_assets>";
             xml=xml+"<office_id_asset_is_available>" + office_id_asset_is_available + "</office_id_asset_is_available>";
             xml=xml+"<ownership_code>" + ownership_code + "</ownership_code>";
             xml=xml+"<donating_agency_name>" + donating_agency_name + "</donating_agency_name>";
             xml=xml+"<project_code>" + project_code + "</project_code>";
             xml=xml+"<year_of_purchase>" + year_of_purchase + "</year_of_purchase>";
             xml=xml+"<month_of_purchase>" + month_of_purchase + "</month_of_purchase>";
             xml=xml+"<original_cost>" + original_cost + "</original_cost>";
             xml=xml+"<under_warrenty>" + under_warrenty + "</under_warrenty>";
             xml=xml+"<warranty_period_from>" + warranty_period_from + "</warranty_period_from>";
             xml=xml+"<warranty_period_upto>" + warranty_period_upto + "</warranty_period_upto>";
             xml=xml+"<current_value_after_depre>" + current_value_after_depre + "</current_value_after_depre>";
             xml=xml+"<current_value_after_apportion>" + current_value_after_apportion + "</current_value_after_apportion>";
             xml=xml+"<is_under_usable_condition>" + is_under_usable_condition + "</is_under_usable_condition>";
             xml=xml+"<is_in_use>" + is_in_use + "</is_in_use>";
             xml=xml+"<remarks>" + remarks + "</remarks>";
             xml=xml+"<userid>" + userid + "</userid>";
             xml=xml+"<accounting_unit_id>" + accounting_unit_id + "</accounting_unit_id>";
             xml=xml+"<accounting_unit_office_id>" + accounting_unit_office_id + "</accounting_unit_office_id>";
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
             String sqlFetch = "SELECT accounting_unit_id, " +
								 "accounting_unit_office_id, " +
								 "to_char(date_transaction,'dd/MM/yyyy') as date_transaction, " +
								 "financial_year, " +
								 "asset_code, " +
								 "num_ac_office_id, " +
								 "asset_type_code, " +
								 "asset_major_class_code, " +
								 "asset_minor_class_code, " +
								 "depreciation_cate_code, " +
								 "apportion_grant_cate_code, " +
								 "alias_code, " +
								 "particulars, " +
								 "number_of_assets, " +
								 "office_id_asset_is_available, " +
								 "ownership_code, " +
								 "donating_agency_name, " +
								 "project_code, " +
								 "year_of_purchase, " +
								 "month_of_purchase, " +
								 "original_cost, " +
								 "under_warrenty, " +
								 "to_char(warranty_period_from,'dd/MM/yyyy') as warranty_period_from, " +
								 "to_char(warranty_period_upto,'dd/MM/yyyy') as warranty_period_upto, " +
								 "current_value_after_depre, " +
								 "current_value_after_apportion, " +
								 "is_under_usable_condition, " +
								 "is_in_use, " +
								 "remarks " +
								 "FROM fas_asset_val_ac_details " +
								 "WHERE accounting_unit_id = " + accounting_unit_id +
								 " AND accounting_unit_office_id = " + accounting_unit_office_id +
								 " AND financial_year = '" + financial_year + "'" +
								 " AND asset_code = " + asset_code;
              
             result = statement.executeQuery(sqlFetch);
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
/*
                     java.sql.Date DateOfTrans = result.getDate("date_transaction");  
                     if(DateOfTrans==null)
                     {
                    	 date_transaction="Not Specified";
                     }
                     else
                     {
                         try
                         {
                             java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                             date_transaction=sdf.format(DateOfTrans);
                         }
                         catch(Exception e)
                         {
                             System.out.println("Error while formatting date (date_transaction) : " + e);
                             date_transaction="Not Specified";
                         }
                     }                
                          
                     xml += "<date_transaction>" + date_transaction + "</date_transaction>";
*/   
                     
                     xml += "<date_transaction>" + result.getString("date_transaction") + "</date_transaction>";
                	 xml += "<num_ac_office_id>" + result.getInt("num_ac_office_id") + "</num_ac_office_id>";
                	 xml += "<asset_type_code>" + result.getString("asset_type_code") + "</asset_type_code>";
                	 xml += "<asset_major_class_code>" + result.getInt("asset_major_class_code") + "</asset_major_class_code>";
                	 xml += "<asset_minor_class_code>" + result.getInt("asset_minor_class_code") + "</asset_minor_class_code>";
                	 xml += "<depreciation_cate_code>" + result.getInt("depreciation_cate_code") + "</depreciation_cate_code>";
                	 xml += "<apportion_grant_cate_code>" + result.getInt("apportion_grant_cate_code") + "</apportion_grant_cate_code>";
                	 xml += "<alias_code>" + result.getInt("alias_code") + "</alias_code>";
                	 xml += "<particulars>" + result.getString("particulars") + "</particulars>";
                	 xml += "<number_of_assets>" + result.getInt("number_of_assets") + "</number_of_assets>";
                	 xml += "<office_id_asset_is_available>" + result.getInt("office_id_asset_is_available") + "</office_id_asset_is_available>";
                	 xml += "<ownership_code>" + result.getString("ownership_code") + "</ownership_code>";
                	 
                	 
                	 if(donating_agency_name == null)
                	 {
                    	 donating_agency_name = "-";
                         
                	 }
                         else
                             donating_agency_name = result.getString("donating_agency_name");
                	 xml += "<donating_agency_name>" + donating_agency_name + "</donating_agency_name>";

                	 xml += "<project_code>" + result.getInt("project_code") + "</project_code>";
                	 xml += "<year_of_purchase>" + result.getInt("year_of_purchase") + "</year_of_purchase>";
                	 xml += "<month_of_purchase>" + result.getInt("month_of_purchase") + "</month_of_purchase>";
                	 xml += "<original_cost>" + result.getInt("original_cost") + "</original_cost>";
                	 xml += "<under_warrenty>" + result.getString("under_warrenty") + "</under_warrenty>";
                	 xml += "<warranty_period_from>" + result.getString("warranty_period_from") + "</warranty_period_from>";
                	 xml += "<warranty_period_upto>" + result.getString("warranty_period_upto") + "</warranty_period_upto>";
                	 xml += "<current_value_after_depre>" + result.getInt("current_value_after_depre") + "</current_value_after_depre>";
                	 xml += "<current_value_after_apportion>" + result.getInt("current_value_after_apportion") + "</current_value_after_apportion>";
                	 xml += "<is_under_usable_condition>" + result.getString("is_under_usable_condition") + "</is_under_usable_condition>";
                	 xml += "<is_in_use>" + result.getString("is_in_use") + "</is_in_use>";

                	 remarks = result.getString("remarks");
                	 if(remarks == null)
                	 {
                		 remarks = " ";
                	 }
                	 xml += "<remarks>" + remarks + "</remarks>";
                 }
                  System.out.println("valExists"+valExists);
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
        else if(strCommand.equals("ListMajorClass"))
        { 
        	System.out.println("\n*************\nListMajorClass\n**************\n");
            xml="<response><command>ListMajorClass</command>";
            try 
            {
             String sqlMajor = "SELECT asset_major_class_code, " +
								"  asset_major_class_desc " +
								"FROM fas_mst_assets_class " +		// fas_mst_assets_class
								"where asset_type_code = '" + asset_type_code + "' order by asset_major_class_code";
            System.out.println("sqlMajor:::"+sqlMajor);
             result = statement.executeQuery(sqlMajor);
             xml += "<flag>success</flag>";
             try
             {
                 while(result.next())
                 { 
                	 xml += "<asset_major_class_code><![CDATA["+result.getInt("asset_major_class_code")+"]]></asset_major_class_code>";
                	 xml += "<asset_major_class_desc><![CDATA["+result.getString("asset_major_class_desc")+"]]></asset_major_class_desc>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in fetching values from resultset of ListMajorClass: " + e);
             }
             result.close();
             //response.setHeader("cache-control","no-cache");
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in ListMajorClass query execution ==> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }   else if(strCommand.equalsIgnoreCase("LoadMinor")){
        	System.out.println("************LoadMinor*************");
            int major_code= Integer.parseInt(request.getParameter("Major_code"));
            int c=0;
             	xml="<response><command>LoadMinor</command>";
             	try{
             		String qry="SELECT ASSET_MINOR_CLASS_CODE, " +
            		 "ASSET_MINOR_CLASS_DESC " +
         		 "FROM FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+major_code;
             		System.out.println("Query >>> "+qry);
     	               PreparedStatement ps=connection.prepareStatement(qry);
     	           
     	               ResultSet results=ps.executeQuery();
     	              
     	                while(results.next())
     	                {
     	                
     	            xml+="<ASSET_MINOR_CLASS_CODE>"+results.getInt("ASSET_MINOR_CLASS_CODE")+"</ASSET_MINOR_CLASS_CODE>";
     	            xml+="<ASSET_MINOR_CLASS_DESC><![CDATA["+results.getString("ASSET_MINOR_CLASS_DESC")+"]]></ASSET_MINOR_CLASS_DESC>";
     	        	c++;
     	                }
              		}catch(Exception e)
                 	{
              			xml+="<flag>failure</flag>";
              			System.out.println("Exception in Select:"+e);
         	        }
              		if(c>0){
              			xml+="<flag>success</flag>";		
              		}else{
              			xml+="<flag>failure</flag>";
              		}
             	xml+="</response>";
             }
        else if(strCommand.equals("FetchAlias"))
        { 
        	System.out.println("\n*************\nFetchAlias\n**************\n");
            xml="<response><command>FetchAlias</command>";
            try 
            {
             String sqlAlias = "SELECT alias_code " +
								"FROM fas_mst_assets_class " +
								"where asset_major_class_code = " + asset_major_class_code;
             result = statement.executeQuery(sqlAlias);
             xml += "<flag>success</flag>";
             try
             {
                 while(result.next())
                 { 
                	 xml += "<alias_code>" + result.getInt("alias_code") + "</alias_code>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in fetching values from resultset of FetchAlias: " + e);
             }
             result.close();
             //response.setHeader("cache-control","no-cache");
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in FetchAlias query execution ==> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        else if(strCommand.equals("LoadLocationOffice"))
        { 
        	System.out.println("\n*************\nLoadLocationOffice\n**************\n");
            xml="<response><command>LoadLocationOffice</command>";
            try 
            {
             String sqlOffice = "SELECT office_name " +
             					"FROM com_mst_offices " +
             					"WHERE office_id = " + office_id_asset_is_available;
             result = statement.executeQuery(sqlOffice);
             xml += "<flag>success</flag>";
             try
             {
                 while(result.next())
                 { 
                	 xml += "<office_name>" + result.getString("office_name") + "</office_name>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in fetching office_name from resultset of LoadOffice: " + e);
             }
             result.close();
             //response.setHeader("cache-control","no-cache");
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in LoadOffice query execution ==> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        else if(strCommand.equals("LoadNAOffice"))
        { 
        	System.out.println("\n*************\nLoadNAOffice\n**************\n");
            xml="<response><command>LoadNAOffice</command>";
            try 
            {
             String sqlNAOffice = "SELECT a.rendering_unit_office_id, b.office_name " +
             						"FROM fas_asset_num_ac_render_units a " +
             						"JOIN com_mst_offices b " +
             						"ON a.rendering_unit_office_id = b.office_id " +
             						"WHERE ACCT_RENDERING_UNIT_ID = " + accounting_unit_id;
             System.out.println("sqlNAOffice:"+sqlNAOffice);
             result = statement.executeQuery(sqlNAOffice);
             xml += "<flag>success</flag>";
             try
             {
                 while(result.next())
                 {
                	 xml += "<rendering_unit_office_id>" + result.getInt("rendering_unit_office_id") + "</rendering_unit_office_id>";
                	 xml += "<office_name>" + result.getString("office_name") + "</office_name>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in fetching office_name from resultset of LoadNAOffice: " + e);
             }
             result.close();
             //response.setHeader("cache-control","no-cache");
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in LoadNAOffice query execution ==> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }

        //////////////////////////////// if else - ENDS ///////////////////////////////////////////////
        
        
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
        
    }
}

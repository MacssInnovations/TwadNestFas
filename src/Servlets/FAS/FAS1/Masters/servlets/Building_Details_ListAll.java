package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Building_Details_ListAll extends HttpServlet 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
    {
    	System.out.println("\n\n*******************************************************\n");
        Connection connection=null;
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
  
          }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
          String CONTENT_TYPE = "text/xml; charset=windows-1252";
          response.setContentType(CONTENT_TYPE);
 
        PrintWriter out = response.getWriter();
        
        String xml = "<response>";
        String strCommand = ""; 

       
        int accounting_unit_id = 0;
        int accounting_unit_office_id = 0;
        String financial_year = null;
        int asset_code = 0;
        

        
        
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
         System.out.println("session id is:"+userid);
        
        
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");

        
        
        try
        {
        	strCommand = request.getParameter("command");
        	System.out.println("strCommand : " + strCommand);
          
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'accounting_unit_id' parameter ==> "+ e);
        }        

        
        try
        {
        	accounting_unit_id= Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	System.out.println("accounting_unit_id : " + accounting_unit_id);
          
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'accounting_unit_id' parameter ==> "+ e);
        }        

           
        try
        {
        	accounting_unit_office_id= Integer.parseInt(request.getParameter("cmbOffice_code"));
        	System.out.println("accounting_unit_office_id : " + accounting_unit_office_id);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'accounting_unit_office_id' parameter ==> "+ e);
        }        


        try
        {
        	financial_year= request.getParameter("cmbFinyr");
        	System.out.println("financial_year : " + financial_year);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'financial_year' parameter ==> "+ e);
        }        

 
        

        
        
        
        /**************************************************************************************************
         * 									Building Details - Load
         **************************************************************************************************/
        
        if(strCommand.equalsIgnoreCase("LoadBuild"))
        {
        		xml += "<command>LoadBuild</command>";
		        try
		        {
		            PreparedStatement ps1 = connection.prepareStatement("SELECT accounting_unit_id,  " +
																		"  accounting_unit_office_id,  " +
																		"  financial_year,  " +
																		"  asset_code,  " +
																		"  to_char(trans_date,'dd/mm/yyyy') AS trans_date,  " +
																		"  building_name,  " +
																		"  owner_code,  " +
																		"  survey_no,  " +
																		"  door_no,  " +
																		"  village,  " +
																		"  year_of_construction,  " +
																		"  type_of_building,  " +
																		"  no_of_floors,  " +
																		"  type_of_foundation,  " +
																		"  structured_bldg_elements,  " +
																		"  total_civil_cost,  " +
																		"  total_electrical_cost,  " +
																		"  total_external_cost,  " +
																		"  total_additions_cost,  " +
																		"  total_book_value,  " +
																		"  a.account_head_code AS account_head_code,  " +
																		"  b.account_head_desc AS account_head_desc, " +
																		"  a.remarks AS remarks,  " +
																		"  a.updated_by_user_id AS updated_by_user_id,  " +
																		"  a.updated_date AS updated_date " +
																		"FROM fas_building_details a join com_mst_account_heads b " +
																		"ON a.account_head_code = b.account_head_code " +
																		"WHERE accounting_unit_id = ?  " +
																		"  AND accounting_unit_office_id = ? " +
																		"  AND financial_year = ? " +
																		"  AND status = 'Y'");
		            
		            ps1.setInt(1,accounting_unit_id);
		            ps1.setInt(2,accounting_unit_office_id);
		            ps1.setString(3,financial_year);
		            
		            ResultSet result1 = ps1.executeQuery(); 
		            
		            xml += "<flag>success</flag>";
		            
		            while(result1.next())
		            {
			            xml += "<ACCOUNTING_UNIT_ID>" + result1.getString("accounting_unit_id") + "</ACCOUNTING_UNIT_ID>";
			            xml += "<ACCOUNTING_UNIT_OFFICE_ID>" + result1.getString("accounting_unit_office_id") + "</ACCOUNTING_UNIT_OFFICE_ID>";
			            xml += "<FINANCIAL_YEAR>" + result1.getString("financial_year") + "</FINANCIAL_YEAR>";
			            xml += "<ASSET_CODE>" + result1.getString("asset_code") + "</ASSET_CODE>";
			            xml += "<TRANS_DATE>" + result1.getString("trans_date") + "</TRANS_DATE>";
			            xml += "<BUILDING_NAME>" + result1.getString("building_name") + "</BUILDING_NAME>";
			            xml += "<OWNER_CODE>" + result1.getString("owner_code") + "</OWNER_CODE>";
			            xml += "<SURVEY_NO>" + result1.getString("survey_no") + "</SURVEY_NO>";
			            xml += "<DOOR_NO>" + result1.getString("door_no") + "</DOOR_NO>";
			            xml += "<VILLAGE>" + result1.getString("village") + "</VILLAGE>";
			            xml += "<YEAR_OF_CONSTRUCTION>" + result1.getString("year_of_construction") + "</YEAR_OF_CONSTRUCTION>";
			            xml += "<TYPE_OF_BUILDING>" + result1.getString("type_of_building") + "</TYPE_OF_BUILDING>";
			            xml += "<NO_OF_FLOORS>" + result1.getString("no_of_floors") + "</NO_OF_FLOORS>";
			            xml += "<TYPE_OF_FOUNDATION>" + result1.getString("type_of_foundation") + "</TYPE_OF_FOUNDATION>";
			            xml += "<STRUCTURED_BLDG_ELEMENTS>" + result1.getString("structured_bldg_elements") + "</STRUCTURED_BLDG_ELEMENTS>";
			            xml += "<TOTAL_CIVIL_COST>" + result1.getString("total_civil_cost") + "</TOTAL_CIVIL_COST>";
			            xml += "<TOTAL_ELECTRICAL_COST>" + result1.getString("total_electrical_cost") + "</TOTAL_ELECTRICAL_COST>";
			            xml += "<TOTAL_EXTERNAL_COST>" + result1.getString("total_external_cost") + "</TOTAL_EXTERNAL_COST>";
			            xml += "<TOTAL_ADDITIONS_COST>" + result1.getString("total_additions_cost") + "</TOTAL_ADDITIONS_COST>";
			            xml += "<TOTAL_BOOK_VALUE>" + result1.getString("total_book_value") + "</TOTAL_BOOK_VALUE>";
			            xml += "<ACCOUNT_HEAD_CODE>" + result1.getString("account_head_code") + "</ACCOUNT_HEAD_CODE>";
			            xml += "<ACCOUNT_HEAD_DESC>" + result1.getString("account_head_desc") + "</ACCOUNT_HEAD_DESC>";
			            xml += "<REMARKS>" + result1.getString("remarks") + "</REMARKS>";
		            }
		            ps1.close();
		            
		            System.out.println("Building Details fetched Successfully!");
		            
		        }
		        catch(SQLException e) {
		            System.out.println("SQLException executing 'Load Building Query' ==> "+e);
		            xml = "<respone><flag>failure</flag>";
		        }
        }

        
        
        
        
        /**************************************************************************************************
         * 									Floor Details - Load
         **************************************************************************************************/
/*
        if(strCommand.equalsIgnoreCase("LoadFloor"))
        {
        		xml += "<command>LoadFloor</command>";
		        try
		        {
		            PreparedStatement ps2 = connection.prepareStatement("SELECT " +
		            													"  to_char(TRANS_DATE,'dd/mm/yyyy') AS TRANS_DATE, " +  
																		"  FLOOR_NO, " +
																		"  YEAR_OF_CONSTRUCTION, " +
																		"  FLOOR_HEIGHT, " +
																		"  PLINTH_AREA, " +
																		"  CIVIL_COST, " +
																		"  ELECTRICAL_COST, " +
																		"  EXTERNAL_COST, " +
																		"  ADDITIONAL_COST, " +
																		"  BOOK_VALUE, " +
																		"  BP_NO_FOR_CONSTRUCTION, " +
																		"  ACCOUNT_HEAD_CODE, " +
																		"  REMARKS, " +
																		"  UPDATED_BY_USER_ID, " +
																		"  UPDATED_DATE " +
																		"FROM FAS_BUILDING_FLOOR_DETAILS " +
																		"WHERE ACCOUNTING_UNIT_ID = ? " +
																		"  AND ACCOUNTING_UNIT_OFFICE_ID = ? " +
																		"  AND FINANCIAL_YEAR = ? " +
																		"  AND ASSET_CODE = ? " +
																		"  AND status = 'Y'");
						ps2.setInt(1,accounting_unit_id);
			            ps2.setInt(2,accounting_unit_office_id);
			            ps2.setString(3,financial_year);
			            ps2.setInt(4,asset_code);
			        
			            ResultSet result2 = ps2.executeQuery();
		
			            
			            while(result2.next())
			            {
			            	xml += "<FLOOR>";
			            	
			            	xml += "<TRANS_DATE>" + result2.getString("TRANS_DATE") + "</TRANS_DATE>";
				            xml += "<FLOOR_NO>" + result2.getInt("FLOOR_NO") + "</FLOOR_NO>";
				            xml += "<YEAR_OF_CONSTRUCTION>" + result2.getInt("YEAR_OF_CONSTRUCTION") + "</YEAR_OF_CONSTRUCTION>";
				            xml += "<FLOOR_HEIGHT>" + result2.getInt("FLOOR_HEIGHT") + "</FLOOR_HEIGHT>";
				            xml += "<PLINTH_AREA>" + result2.getInt("PLINTH_AREA") + "</PLINTH_AREA>";
				            xml += "<CIVIL_COST>" + result2.getInt("CIVIL_COST") + "</CIVIL_COST>";
				            xml += "<ELECTRICAL_COST>" + result2.getInt("ELECTRICAL_COST") + "</ELECTRICAL_COST>";
				            xml += "<EXTERNAL_COST>" + result2.getInt("EXTERNAL_COST") + "</EXTERNAL_COST>";
				            xml += "<ADDITIONAL_COST>" + result2.getInt("ADDITIONAL_COST") + "</ADDITIONAL_COST>";
				            xml += "<BOOK_VALUE>" + result2.getInt("BOOK_VALUE") + "</BOOK_VALUE>";
				            xml += "<BP_NO_FOR_CONSTRUCTION>" + result2.getInt("BP_NO_FOR_CONSTRUCTION") + "</BP_NO_FOR_CONSTRUCTION>";
				            xml += "<ACCOUNT_HEAD_CODE>" + result2.getInt("ACCOUNT_HEAD_CODE") + "</ACCOUNT_HEAD_CODE>";
				            xml += "<REMARKS>" + result2.getString("REMARKS") + "</REMARKS>";
				            
				            xml += "</FLOOR>";
			            }
			            
		
			            ps2.close();
			            
			            System.out.println("Floor Details fetched Successfully!");
			            
		        }
		        catch(SQLException e) {
		            System.out.println("SQLException executing 'Load floor Query' ==> "+e);
		            xml = "<respone><flag>failure</flag>";
		        }
        }
        
*/        
        
        
        
        
        
        /**************************************************************************************************
         * 									Office Details - Load
         **************************************************************************************************/
/*        
        if(strCommand.equalsIgnoreCase("LoadOffice"))
        {
        		xml += "<command>LoadOffice</command>";
		        try
		        {
		            PreparedStatement ps3 = connection.prepareStatement("SELECT " +
																		"  to_char(TRANS_DATE,'dd/mm/yyyy') AS TRANS_DATE, " +  
																		"  FLOOR_NO, " +
																		"  TYPE_OF_OCCUPYING_OFFICE, " +
																		"  OFFICE_NAME, " +
																		"  REMARKS " +
																		"FROM FAS_FLOOR_OFFICE_DETAILS " +
																		"WHERE ACCOUNTING_UNIT_ID = ? " +
																		"  AND ACCOUNTING_UNIT_OFFICE_ID = ? " +
																		"  AND FINANCIAL_YEAR = ? " +
																		"  AND ASSET_CODE = ? " +
																		"  AND status = 'Y'");
					ps3.setInt(1,accounting_unit_id);
		            ps3.setInt(2,accounting_unit_office_id);
		            ps3.setString(3,financial_year);
		            ps3.setInt(4,asset_code);
		        
		            ResultSet result3 = ps3.executeQuery();
		
		            
		            while(result3.next())
		            {
		            	xml += "<OFFICE>";
		            	
			            xml += "<TRANS_DATE>" + result3.getString("TRANS_DATE") + "</TRANS_DATE>";
			            xml += "<FLOOR_NO>" + result3.getInt("FLOOR_NO") + "</FLOOR_NO>";
			            xml += "<TYPE_OF_OCCUPYING_OFFICE>" + result3.getString("TYPE_OF_OCCUPYING_OFFICE") + "</TYPE_OF_OCCUPYING_OFFICE>";
			            xml += "<OFFICE_NAME>" + result3.getString("OFFICE_NAME") + "</OFFICE_NAME>";
			            xml += "<REMARKS>" + result3.getString("REMARKS") + "</REMARKS>";
			            
			            xml += "</OFFICE>";
		            }
		            
		
		            ps3.close();
		            
		            System.out.println("Office Details fetched Successfully!");
		        }
		        catch(SQLException e) {
		            System.out.println("SQLException executing 'Load Office Query' ==> "+e);
		            xml = "<respone><flag>failure</flag>";
		        }
        }
*/
		 
        
        
        

                
                
                /**************************************************************************************************
                 * 									Office Details - Load
                 **************************************************************************************************/
                
                if(strCommand.equalsIgnoreCase("LoadOffice"))
                {
                		xml += "<command>LoadOffice</command>";
        		        try
        		        {
        		            PreparedStatement ps3 = connection.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC " +
																		        "from COM_MST_ACCOUNT_HEADS " +
																		        "where ACCOUNT_HEAD_CODE = ?");
        					ps3.setInt(1,accounting_unit_id);
        		            ps3.setInt(2,accounting_unit_office_id);
        		            ps3.setString(3,financial_year);
        		            ps3.setInt(4,asset_code);
        		        
        		            ResultSet result3 = ps3.executeQuery();
        		
        		            
        		            while(result3.next())
        		            {
        		            	xml += "<OFFICE>";
        		            	
        			            xml += "<TRANS_DATE>" + result3.getString("TRANS_DATE") + "</TRANS_DATE>";
        			            xml += "<FLOOR_NO>" + result3.getInt("FLOOR_NO") + "</FLOOR_NO>";
        			            xml += "<TYPE_OF_OCCUPYING_OFFICE>" + result3.getString("TYPE_OF_OCCUPYING_OFFICE") + "</TYPE_OF_OCCUPYING_OFFICE>";
        			            xml += "<OFFICE_NAME>" + result3.getString("OFFICE_NAME") + "</OFFICE_NAME>";
        			            xml += "<REMARKS>" + result3.getString("REMARKS") + "</REMARKS>";
        			            
        			            xml += "</OFFICE>";
        		            }
        		            
        		
        		            ps3.close();
        		            
        		            System.out.println("Office Details fetched Successfully!");
        		        }
        		        catch(SQLException e) {
        		            System.out.println("SQLException executing 'Load Office Query' ==> "+e);
        		            xml = "<respone><flag>failure</flag>";
        		        }
                }
		        
		        
        
        
        
        
        xml += "</response>";
        
        System.out.println(xml);
        out.println(xml);
    }

    
 

}

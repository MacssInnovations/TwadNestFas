package Servlets.FAS.FAS1.CommonControls.servlets;

import java.sql.SQLException;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Load_Accounting_Unit_ID extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
                                                           System.out.println("startsssssssssssssssssssssssssss serrrrrrrrrrrrrrrrrr");

        /*
                                                   LoadAccountingUnitID('BOTH_UNITS_AND_OFFICES')
                                                   LoadAccountingUnitID('ONLY_UNITS')

                                                   LoadAccountingUnitID('LIST_ALL_UNITS')
                                                   LoadAccountingUnitID('LIST_ALL_UNITS_ONLY')

                                                   LoadAccountingUnitID('FOR_LIST_0')      // 0 -- Accounting Units Only
                                                   LoadAccountingUnitID('FOR_LIST_1')      // 1 -- Both Units and Offices
                                                   
                                                   LoadAccountingUnitID('FOR_LIST_RD')      // RD -- Both old(Re deployed) office and new offices for viewing reports purpose only

                                               */

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        //---------------------------------------------------------------------------------------------------------------

        /**
               * Variables Declaration
               */
        Connection con = null;

        ResultSet results = null;

        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;


        ResultSet rs6 = null;
        ResultSet rs7 = null;
        ResultSet rs8 = null;

        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps5 = null;
        PreparedStatement ps6 = null;
        PreparedStatement ps7 = null;
        PreparedStatement ps8 = null;

        int office_id = 0,oldOff=0;int office_id1=0;
        String office_level_id = "";
        String office_name = "";
        String COMMAND = "",closedOff="";

        //---------------------------------------------------------------------------------------------------------------

        /**
               * Database Connection
               */
        try {

            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }


        //---------------------------------------------------------------------------------------------------------------


        /**
              * Session Checking
              */
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
                * GET Command Parameter
                */

        COMMAND = request.getParameter("COMMAND");

        System.out.println("FAS_SU-------------->" +
                           session.getAttribute("FAS_SU"));
        System.out.println("FAS_SU_ALL---------->" +
                           session.getAttribute("FAS_SU_ALL"));
        System.out.println("FAS_SU_REGION------->" +
                           session.getAttribute("FAS_SU_REGION"));
        System.out.println("FAS_SU_CIRCLE------->" +
                           session.getAttribute("FAS_SU_CIRCLE"));
        System.out.println("FAS_CAO------->" +
                           session.getAttribute("FAS_CAO"));
        //added on 14MAR2012 for New Role FAS_FEW_LIST_ROLE 
        System.out.println("FAS_FEW_LIST_ROLE------->" +
                session.getAttribute("FAS_FEW_LIST"));
        
        //added on 08May2012 for New Role FAS_REPORTS_VIEW_REDEPOFF 
        System.out.println("FAS_REPORTS_VIEW_REDEPOFF------->" +
                session.getAttribute("FAS_REPORTS_VIEW_REDEPOFF"));
        //added on 11feb2013
        System.out.println("FAS_TDA_HO_ROLE------->" +session.getAttribute("FAS_TDA_HO_ROLE"));
     
        System.out.println("FAS_TPA_AUDIT_ROLE------->" +session.getAttribute("FAS_TPA_AUDIT_ROLE"));
        
        
        System.out.println("COMMAND ------------***********>" + COMMAND);

        String FAS_TDA_HO_ROLE = "";
        if (session.getAttribute("FAS_TDA_HO_ROLE") != null &&
            ((String)session.getAttribute("FAS_TDA_HO_ROLE")).equalsIgnoreCase("YES"))
        	FAS_TDA_HO_ROLE = "YES";
        else
        	FAS_TDA_HO_ROLE = "NO";
        
        String FAS_SU = "";
        if (session.getAttribute("FAS_SU") != null &&
            ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
            FAS_SU = "YES";
        else
            FAS_SU = "NO";

        String FAS_CAO = "";
        if (session.getAttribute("FAS_CAO") != null &&
            ((String)session.getAttribute("FAS_CAO")).equalsIgnoreCase("YES"))
            FAS_CAO = "YES";
        else
            FAS_CAO = "NO";

        String FAS_SU_ALL = "";
        if (session.getAttribute("FAS_SU_ALL") != null &&
            ((String)session.getAttribute("FAS_SU_ALL")).equalsIgnoreCase("YES"))
            FAS_SU_ALL = "YES";
        else
            FAS_SU_ALL = "NO";

        String FAS_SU_REGION = "";
        if (session.getAttribute("FAS_SU_REGION") != null &&
            ((String)session.getAttribute("FAS_SU_REGION")).equalsIgnoreCase("YES"))
            FAS_SU_REGION = "YES";
        else
            FAS_SU_REGION = "NO";

        String FAS_SU_CIRCLE = "";
        if (session.getAttribute("FAS_SU_CIRCLE") != null &&
            ((String)session.getAttribute("FAS_SU_CIRCLE")).equalsIgnoreCase("YES"))
            FAS_SU_CIRCLE = "YES";
        else
            FAS_SU_CIRCLE = "NO";
        
        String FAS_FEW_LIST_ROLE = "";
        if (session.getAttribute("FAS_FEW_LIST") != null &&
            ((String)session.getAttribute("FAS_FEW_LIST")).equalsIgnoreCase("YES"))
        	FAS_FEW_LIST_ROLE = "YES";
        else
        	FAS_FEW_LIST_ROLE = "NO";

        String FAS_REPORTS_VIEW_REDEPOFF = "";
        if (session.getAttribute("FAS_REPORTS_VIEW_REDEPOFF") != null &&
            ((String)session.getAttribute("FAS_REPORTS_VIEW_REDEPOFF")).equalsIgnoreCase("YES"))
        	FAS_REPORTS_VIEW_REDEPOFF = "YES";
        else
        	FAS_REPORTS_VIEW_REDEPOFF = "NO";
        
        String FAS_TPA_AUDIT_ROLE = "";
        if (session.getAttribute("FAS_TPA_AUDIT_ROLE") != null &&
            ((String)session.getAttribute("FAS_TPA_AUDIT_ROLE")).equalsIgnoreCase("YES"))
        	FAS_TPA_AUDIT_ROLE = "YES";
        else
        	FAS_TPA_AUDIT_ROLE = "NO";
        
        

        //---------------------------------------------------------------------------------------------------------------

        /**
                 * Get Employee ID from Session
                 */
        //   HttpSession session=request.getSession(false);
        session =request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("user id::" + empProfile.getEmployeeId());
        int employee_id = empProfile.getEmployeeId();
        long l=System.currentTimeMillis();
		Timestamp ts=new Timestamp(l);                      
		 Date ctdate = new java.sql.Date(ts.getTime());   

        //---------------------------------------------------------------------------------------------------------------
		 try{
		        if(FAS_REPORTS_VIEW_REDEPOFF.equalsIgnoreCase("YES"))
		        {
		        	 ps8 =con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
		             ps8.setInt(1, employee_id);
		             rs8=ps8.executeQuery();
		             if (rs8.next()) {
		                 
		                 office_id = rs8.getInt("OFFICE_ID");
		              
		             }
		             rs8.close();
		             ps8.close();
		        	System.out.println("The office id for the twad8569 testing is ::::: "+office_id);
		        }
		        else
		        {
		        	/** Get Employee Office ID */
		        	  ps=con.prepareStatement("SELECT "+
		              		"  CASE "+
		              		 "   When Old_Office_Id   Is Not Null "+
		              		  "  AND DATE_ALLOWED_UPTO>=? "+
		              		    " THEN OLD_OFFICE_ID "+
		              		    " ELSE Office_Id "+
		              		  " END AS OFFICE_ID "+
		              		" FROM "+
		              		  " (SELECT Office_Id, "+
		              		    " OLD_OFFICE_ID, "+
		              		    " DATE_ALLOWED_UPTO "+
		              		  " From Hrm_Emp_Current_Posting "+
		              		  " Where Employee_Id=? ) as ps" );
		            //ps =con.prepareStatement("select OFFICE_ID   from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
		         //   ps.setInt(1, employee_id);
		        	  ps.setDate(1, ctdate);
		              ps.setInt(2,employee_id);
		            results = ps.executeQuery();
		            if (results.next()) 
		            {
		                             office_id = results.getInt("OFFICE_ID");
		            }
		            results.close();
		            ps.close();
		        }
		   }
		 catch(Exception e)
		 {
			 System.out.println("Exception in getting office id*****"+e);
		 }
//-------------------------------------------------------------------------------------------------------------------------		 
		 /**
         * Find Office ID and Office Name from Employee ID
         */

        try {
//            /** Get Employee Office ID */
//        	  ps=con.prepareStatement(" SELECT "+
//              		"  CASE "+
//              		 "   When Old_Office_Id   Is Not Null "+
//              		  "  AND DATE_ALLOWED_UPTO>=? "+
//              		    " THEN OLD_OFFICE_ID "+
//              		    " ELSE Office_Id "+
//              		  " END AS OFFICE_ID "+
//              		" FROM "+
//              		  " (SELECT Office_Id, "+
//              		    " OLD_OFFICE_ID, "+
//              		    " DATE_ALLOWED_UPTO "+
//              		  " From Hrm_Emp_Current_Posting "+
//              		  " Where Employee_Id=? )" );
//            ps =con.prepareStatement("select OFFICE_ID   from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
//            ps.setInt(1, employee_id);
//        	  ps.setDate(1, ctdate);
//              ps.setInt(2,employee_id);
//            results = ps.executeQuery();
//            if (results.next()) 
//            {
//                             office_id = results.getInt("OFFICE_ID");
//            }
//            results.close();
//            ps.close();
                  
            try
            {
                System.out.println("*****************************************************************BEFORE"+office_id);
            ps2 =
            con.prepareStatement("select OFFICE_LEVEL_ID  from COM_MST_OFFICES  where OFFICE_ID=?");
            ps2.setInt(1, office_id);
            rs2 = ps2.executeQuery();
            if(rs2.next())
            { 
            	office_level_id=rs2.getString("OFFICE_LEVEL_ID");
                System.out.println("*****************************************************************office_level_id"+office_level_id);
                if(office_level_id.equals("AW")) 
                {
                            System.out.println("*****************************************************************Enter"+office_level_id);
			                ps1 =
			                con.prepareStatement("select REGION_OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  where OFFICE_ID=?");
			                ps1.setInt(1, office_id);
			                rs = ps1.executeQuery();
			                if(rs.next()) 
			                {
			                    office_id=rs.getInt("REGION_OFFICE_ID");
			                 }
               
                 }
             }
            }
            catch(Exception e) {
            						System.out.println(e);
    
            					}
            
            
            System.out.println("*****************************************************************"+office_id);
            
            

            /** Get Office Name */
            ps =
  con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
            ps.setInt(1, office_id);
            results = ps.executeQuery();
            if (results.next()) {
                office_name = results.getString("OFFICE_NAME");
            }
            results.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        //---------------------------------------------------------------------------------------------------------------


        /**
                   * Find Accounting Unit ID and its Name
                   */


        String xml = "<response><command>Load_Accounting_Unit_ID</command>";

        /**
                             *    1. Employee has FAS_SUPER_USER Role
                             *    2. List All Accounting Units
                             */

        /**
                             * List All Units
                             */
        if (FAS_SU_ALL.equalsIgnoreCase("YES") &&
            (COMMAND.equalsIgnoreCase("FOR_LIST_0") || COMMAND.equalsIgnoreCase("FOR_LIST_1") || COMMAND.equalsIgnoreCase("LIST_ALL_UNITS")))
        {
            System.out.println("1-----><");
            //String ALL_SQL ="select accounting_unit_id, accounting_unit_name from fas_mst_acct_units order by accounting_unit_name";
            String ALL_SQL ="SELECT accounting_unit_id, "+
				 " trim(accounting_unit_name) AS accounting_unit_name "+
            	" FROM fas_mst_acct_units "+
            //	" WHERE  ACCOUNTING_UNIT_OFFICE_ID NOT IN "+
            //	" (SELECT OFFICE_ID "+
            //	" FROM COM_MST_OFFICES "+
            //	" WHERE OFFICE_STATUS_ID IN('CL','RD','NC') "+
            //	" ) "+
            //	" AND STATUS IS NULL "+
            	" ORDER BY accounting_unit_name";
            
            try {
                ps5 = con.prepareStatement(ALL_SQL);
                rs5 = ps5.executeQuery();
                while (rs5.next()) {
                    xml = xml + "<option>";
                    xml =
 xml + "<accounting_unit_id>" + rs5.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                    xml =
 xml + "<accounting_unit_name>" + rs5.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                    xml = xml + "</option>";
                }
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println("Failed to Fetch Record for Displaying All the Units -->" +
                                   e);
            }
        }
        
        if (FAS_SU_ALL.equalsIgnoreCase("YES") && FAS_SU_REGION.equalsIgnoreCase("YES") && FAS_SU_CIRCLE.equalsIgnoreCase("YES"))             
            {
                System.out.println("1-----><");
                //String ALL_SQL ="select accounting_unit_id, accounting_unit_name from fas_mst_acct_units order by accounting_unit_name";
                String ALL_SQL ="SELECT accounting_unit_id, "+
    				 " trim(accounting_unit_name) AS accounting_unit_name "+
                	" FROM fas_mst_acct_units "+
                //	" WHERE  ACCOUNTING_UNIT_OFFICE_ID NOT IN "+
                //	" (SELECT OFFICE_ID "+
                //	" FROM COM_MST_OFFICES "+
                //	" WHERE OFFICE_STATUS_ID IN('CL','RD','NC') "+
                //	" ) "+
                //	" AND STATUS IS NULL "+
                	" ORDER BY accounting_unit_name";
                
                try {
                    ps5 = con.prepareStatement(ALL_SQL);
                    rs5 = ps5.executeQuery();
                    while (rs5.next()) {
                        xml = xml + "<option>";
                        xml =
     xml + "<accounting_unit_id>" + rs5.getInt("ACCOUNTING_UNIT_ID") +
       "</accounting_unit_id>";
                        xml =
     xml + "<accounting_unit_name>" + rs5.getString("ACCOUNTING_UNIT_NAME") +
       "</accounting_unit_name>";
                        xml = xml + "</option>";
                    }
                    xml = xml + "<flag>success</flag>";

                } catch (Exception e) {
                    System.out.println("Failed to Fetch Record for Displaying All the Units -->" +
                                       e);
                }
            }
        
        //added by sathya on 08May2012
				        /**
				         * To view all Reports for the new office as well as Re deployed office (old office)
				         */
				if (FAS_REPORTS_VIEW_REDEPOFF.equalsIgnoreCase("YES") &&
				(COMMAND.equalsIgnoreCase("FOR_LIST_0") ||
				COMMAND.equalsIgnoreCase("FOR_LIST_1"))) {
				System.out.println("TT---88--><");
				/*String ALL_SQL ="	SELECT redeployed_office_id AS old_office_id,	"+
				  				"	new_office_id,	"+
				  				"	new_office_name,	"+
				  				"	(SELECT office_name	"+
				  				"	FROM com_mst_offices	"+
				  				"	WHERE office_id=	"+
				  				"	(SELECT redeployed_office_id	"+
				  				"	FROM com_office_redeployments	"+
				  				"	WHERE acct_trf_unit_id=	"+
				  				"	(SELECT ACCOUNTING_UNIT_ID	"+
				  				"	FROM FAS_MST_ACCT_UNITS	"+
				  				"	WHERE ACCOUNTING_UNIT_OFFICE_ID=?	"+
				  				"	)"+
				  				"	)"+
				  				"	)AS old_office_name	"+
				  				"	FROM com_office_redeployments	"+
				  				"	WHERE acct_trf_unit_id=	"+
				  				"	(SELECT ACCOUNTING_UNIT_ID	"+
				  				"	FROM FAS_MST_ACCT_UNITS	"+
				  				"	WHERE accounting_unit_office_id=?	"+
				  				"	)";*/
				String ALL_SQL="select accounting_unit_id,accounting_for_office_id,b.office_name as accounting_unit_name "+
								" FROM FAS_MST_ACCT_UNIT_OFFICES_RD,com_mst_offices b "+
								" WHERE accounting_unit_id IN "+
								" (SELECT ACCT_TRF_UNIT_ID "+
								" from com_office_redeployments "+
								" WHERE NEW_OFFICE_ID=? "+
								" ) "+
								" and b.OFFICE_ID=accounting_for_office_id " ;
							
				try {
				ps5 = con.prepareStatement(ALL_SQL);
				ps5.setInt(1, office_id);
				//ps5.setInt(2, office_id);
				rs5 = ps5.executeQuery();
				while (rs5.next()) {
				xml = xml + "<option>";
		/*		xml =
				xml + "<accounting_unit_id1>" + rs5.getInt("old_office_id") +
				"</accounting_unit_id1>";
				xml =
				xml + "<accounting_unit_name1>" + rs5.getString("old_office_name") +
				"</accounting_unit_name1>";
				xml =
					xml + "<accounting_unit_id2>" + rs5.getInt("new_office_id") +
					"</accounting_unit_id2>";
					xml =
					xml + "<accounting_unit_name2>" + rs5.getString("new_office_name") +
					"</accounting_unit_name2>";
	*/
				  xml =
					     xml + "<accounting_unit_id>" + rs5.getInt("ACCOUNTING_UNIT_ID") +
					       "</accounting_unit_id>";
					                        xml =
					     xml + "<accounting_unit_name>" + rs5.getString("ACCOUNTING_UNIT_NAME") +
					       "</accounting_unit_name>";
				xml = xml + "</option>";
				}
				xml = xml + "<flag>success</flag>";
				
				} catch (Exception e) {
				System.out.println("Failed to Fetch Record for Displaying All the Units -->" +
				               e);
				}
				}
        
        /**
         					* FAS FEW LIST Role
         */
        else if (FAS_FEW_LIST_ROLE.equalsIgnoreCase("YES") &&
                (COMMAND.equalsIgnoreCase("FOR_LIST_0") ||
                 COMMAND.equalsIgnoreCase("FOR_LIST_1") ||
                 COMMAND.equalsIgnoreCase("LIST_ALL_UNITS") ||
                 COMMAND.equalsIgnoreCase("LIST_ALL_UNITS_ONLY")
                 )) {
                System.out.println("1111-----sssss><");
                //String ALL_SQL ="select accounting_unit_id, accounting_unit_name from fas_mst_acct_units order by accounting_unit_name";
                String ALL_SQL ="SELECT accounting_unit_id, "+
				 " trim(accounting_unit_name) AS accounting_unit_name "+
           	" FROM fas_mst_acct_units "+
           	" WHERE  ACCOUNTING_UNIT_OFFICE_ID NOT IN "+
           	" (SELECT OFFICE_ID "+
           	" FROM COM_MST_OFFICES "+
           	" WHERE OFFICE_STATUS_ID IN('CL','RD','NC') "+
           	" ) "+
           	" AND STATUS IS NULL "+
           	" ORDER BY accounting_unit_name";
                try {
                    ps5 = con.prepareStatement(ALL_SQL);
                    rs5 = ps5.executeQuery();
                    while (rs5.next()) {
                        xml = xml + "<option>";
                        xml =
     xml + "<accounting_unit_id>" + rs5.getInt("ACCOUNTING_UNIT_ID") +
       "</accounting_unit_id>";
                        xml =
     xml + "<accounting_unit_name>" + rs5.getString("ACCOUNTING_UNIT_NAME") +
       "</accounting_unit_name>";
                        xml = xml + "</option>";
                    }
                    xml = xml + "<flag>success</flag>";

                } catch (Exception e) {
                    System.out.println("Failed to Fetch Record for Displaying All the Units -->" +
                                       e);
                }
            }
        
        
        
        

        /**
                             * FAS CAO Role
                             */
        else if (FAS_CAO.equalsIgnoreCase("YES") &&
                 (COMMAND.equalsIgnoreCase("FOR_LIST_0") ||
                  COMMAND.equalsIgnoreCase("FOR_LIST_1") ||
                  COMMAND.equalsIgnoreCase("BOTH_UNITS_AND_OFFICES") ||
                  COMMAND.equalsIgnoreCase("ONLY_UNITS") ||
                  COMMAND.equalsIgnoreCase("LIST_ALL_UNITS") ||
                  COMMAND.equalsIgnoreCase("LIST_ALL_UNITS_ONLY"))) {
            System.out.println("1-----><");
            //String ALL_SQL ="select accounting_unit_id, accounting_unit_name from fas_mst_acct_units order by accounting_unit_name";
            String ALL_SQL ="SELECT accounting_unit_id, "+
			 " trim(accounting_unit_name) AS accounting_unit_name "+
       	" FROM fas_mst_acct_units "+
       	" WHERE  ACCOUNTING_UNIT_OFFICE_ID NOT IN "+
       	" (SELECT OFFICE_ID "+
       	" FROM COM_MST_OFFICES "+
       	" WHERE OFFICE_STATUS_ID IN('CL','RD','NC') "+
       	" ) "+
       	" AND STATUS IS NULL "+
       	" ORDER BY accounting_unit_name";
            try {
                ps5 = con.prepareStatement(ALL_SQL);
                rs5 = ps5.executeQuery();
                while (rs5.next()) {
                    xml = xml + "<option>";
                    xml =
 xml + "<accounting_unit_id>" + rs5.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                    xml =
 xml + "<accounting_unit_name>" + rs5.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                    xml = xml + "</option>";
                }
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println("Failed to Fetch Record for Displaying All the Units -->" +
                                   e);
            }
        }
        else if (FAS_SU_REGION.equalsIgnoreCase("YES") && FAS_TPA_AUDIT_ROLE.equalsIgnoreCase("YES")
	            && (COMMAND.equalsIgnoreCase("LIST_ALL_UNITS")))
	        {
		
	try
	{
//	int oid=0;
		
//		ps=con.prepareStatement(" SELECT "+
//				"  CASE "+
//				"  When Old_Office_Id   Is Not Null "+
//				"  AND DATE_ALLOWED_UPTO>=? "+
//    		    " THEN OLD_OFFICE_ID "+
//    		    " ELSE Office_Id "+
//    		    " END AS OFFICE_ID "+
//    		    " FROM "+
//    		    " (SELECT Office_Id, "+
//    		    " OLD_OFFICE_ID, "+
//    		    " DATE_ALLOWED_UPTO "+
//    		    " From Hrm_Emp_Current_Posting "+
//				" Where Employee_Id=? )" );
//    System.out.println("empid:::"+employee_id);
//    ps.setDate(1, ctdate);
//    ps.setInt(2,employee_id);
//    
//    results=ps.executeQuery();
//         if(results.next()) 
//         {
//        	 oid=results.getInt("OFFICE_ID");
//         }
         
         ps1 =
	                con.prepareStatement("select REGION_OFFICE_ID  from FAS_TPA_AUDIT_ALL_OFFICES_VIEW  where OFFICE_ID=?");
	                ps1.setInt(1, office_id);
	                rs = ps1.executeQuery();
	                if(rs.next()) 
	                {
	                	office_id=rs.getInt("REGION_OFFICE_ID");
	                }
     String RegionSql=" SELECT ACCOUNTING_UNIT_ID," +
					  " accounting_unit_name " +
					  " FROM " +
					  "(SELECT accounting_unit_id," +
						  " accounting_unit_name " +
					  " FROM fas_mst_acct_units " +
					  " WHERE accounting_unit_office_id IN "+
						  " (SELECT office_id " +
					      " FROM FAS_TPA_AUDIT_ALL_OFFICES_VIEW " +
						  " WHERE region_office_id=" +office_id +
						  " ))as ms ORDER BY accounting_unit_name ";
     
     System.out.println("RegionSql"+RegionSql);
     
     ps6 = con.prepareStatement(RegionSql);
    // ps6.setInt(1, oid);
     rs6 = ps6.executeQuery();
     
     
     
     while(rs6.next())
          {
    	 	xml = xml + "<option>";
    	 	xml = xml + "<accounting_unit_id>" + rs6.getInt("ACCOUNTING_UNIT_ID") + "</accounting_unit_id>";
    	 	xml = xml + "<accounting_unit_name>" + rs6.getString("ACCOUNTING_UNIT_NAME") + "</accounting_unit_name>";
    	 	xml = xml + "</option>";
          }
     		xml = xml + "<flag>success</flag>";
           System.out.println("XML==NEW==>"+xml);         
	       
	   }
		catch(Exception e)
		{
			 System.out.println(e);
		}
	        }
				
        else if (FAS_TPA_AUDIT_ROLE.equalsIgnoreCase("YES")
	            && (COMMAND.equalsIgnoreCase("LIST_ALL_UNITS")))
	        {
		
	try
	{
//	int oid=0;
		
//		ps=con.prepareStatement(" SELECT "+
//				"  CASE "+
//				"  When Old_Office_Id   Is Not Null "+
//				"  AND DATE_ALLOWED_UPTO>=? "+
//    		    " THEN OLD_OFFICE_ID "+
//    		    " ELSE Office_Id "+
//    		    " END AS OFFICE_ID "+
//    		    " FROM "+
//    		    " (SELECT Office_Id, "+
//    		    " OLD_OFFICE_ID, "+
//    		    " DATE_ALLOWED_UPTO "+
//    		    " From Hrm_Emp_Current_Posting "+
//				" Where Employee_Id=? )" );
//    System.out.println("empid:::"+employee_id);
//    ps.setDate(1, ctdate);
//    ps.setInt(2,employee_id);
//    
//    results=ps.executeQuery();
//         if(results.next()) 
//         {
//        	 oid=results.getInt("OFFICE_ID");
//         }
         
         ps1 =
	                con.prepareStatement("select REGION_OFFICE_ID  from FAS_TPA_AUDIT_ALL_OFFICES_VIEW  where OFFICE_ID=?");
	                ps1.setInt(1, office_id);
	                rs = ps1.executeQuery();
	                if(rs.next()) 
	                {
	                	office_id=rs.getInt("REGION_OFFICE_ID");
	                }
     String RegionSql=" SELECT ACCOUNTING_UNIT_ID," +
					  " accounting_unit_name " +
					  " FROM " +
					  "(SELECT accounting_unit_id," +
						  " accounting_unit_name " +
					  " FROM fas_mst_acct_units " +
					  " WHERE accounting_unit_office_id IN "+
						  " (SELECT office_id " +
					      " FROM FAS_TPA_AUDIT_ALL_OFFICES_VIEW " +
						  " WHERE region_office_id=" +office_id +
						  " ))as ps ORDER BY accounting_unit_name ";
     
     System.out.println("RegionSql"+RegionSql);
     
     ps6 = con.prepareStatement(RegionSql);
    // ps6.setInt(1, oid);
     rs6 = ps6.executeQuery();
     
     
     
     while(rs6.next())
          {
    	 	xml = xml + "<option>";
    	 	xml = xml + "<accounting_unit_id>" + rs6.getInt("ACCOUNTING_UNIT_ID") + "</accounting_unit_id>";
    	 	xml = xml + "<accounting_unit_name>" + rs6.getString("ACCOUNTING_UNIT_NAME") + "</accounting_unit_name>";
    	 	xml = xml + "</option>";
          }
     		xml = xml + "<flag>success</flag>";
           System.out.println("XML==NEW==>"+xml);         
	       
	   }
		catch(Exception e)
		{
			 System.out.println(e);
		}
	        }				
				

					
        /**
                             * List Units which come under Particular Region
                             * 
                             */
     //   else if (FAS_SU_REGION.equalsIgnoreCase("YES") && (COMMAND.equalsIgnoreCase("FOR_LIST_0") || COMMAND.equalsIgnoreCase("FOR_LIST_1"))) {
        else if (FAS_SU_REGION.equalsIgnoreCase("YES") && ((COMMAND.equalsIgnoreCase("LIST_ALL_UNITS")) || (COMMAND.equalsIgnoreCase("FOR_LIST_1")) || (COMMAND.equalsIgnoreCase("FOR_LIST_0"))) ) {
            System.out.println("2-----><");
          //  System.out.println("FAS_SU_REGION:::"+FAS_SU_REGION);
            String REGION_SQL =
                "" + "   select                           \n" + "       accounting_unit_id,          \n" +
                "       accounting_unit_name         \n" +
                "   from                             \n" +
                "        fas_mst_acct_units          \n" +
                "   where                            \n" +
                "        accounting_unit_office_id in   \n" +
                "    (                               \n" +
                "       select                       \n" +
                "          office_id                 \n" +
                "       from                         \n" +
                "          com_mst_all_offices_view  \n" +
                "       where region_office_id= ?    \n" +
                "    )                               \n" +
                "   order by accounting_unit_name      \n" +
                "                                    \n";
            //	System.out.println("REGION_SQL:::"+REGION_SQL);
            	System.out.println("office_id::"+office_id);
            try {
                ps6 = con.prepareStatement(REGION_SQL);
                ps6.setInt(1, office_id);
                rs6 = ps6.executeQuery();
                while (rs6.next()) {
                    xml = xml + "<option>";
                    xml =
 xml + "<accounting_unit_id>" + rs6.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                    xml =
 xml + "<accounting_unit_name>" + rs6.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                    xml = xml + "</option>";
                }
                xml = xml + "<flag>success</flag>";
            } catch (SQLException e) {
                System.out.println("Failed to Fetch Record for Displaying the Units Which come under particular Region -->" +
                                   e);
            }

        }
        /**
                             * List Units Which come under Particular Circle
                             */
        else if (FAS_SU_CIRCLE.equalsIgnoreCase("YES") &&
                 (COMMAND.equalsIgnoreCase("FOR_LIST_0") ||
                  COMMAND.equalsIgnoreCase("FOR_LIST_1"))) {
            System.out.println("3-----><");

            String CIRCLE_SQL =
                "" + "   select                           \n" + "       accounting_unit_id,          \n" +
                "       accounting_unit_name         \n" +
                "   from                             \n" +
                "        fas_mst_acct_units          \n" +
                "   where                            \n" +
                "        accounting_unit_office_id in   \n" +
                "    (                               \n" +
                "       select                       \n" +
                "          office_id                 \n" +
                "       from                         \n" +
                "          com_mst_all_offices_view  \n" +
                "       where circle_office_id= ?    \n" +
                "    )                               \n" +
                "   order by accounting_unit_name      \n" +
                "                                    \n";

            try {
                ps7 = con.prepareStatement(CIRCLE_SQL);
                ps7.setInt(1, office_id);
                rs7 = ps7.executeQuery();
                while (rs7.next()) {
                    xml = xml + "<option>";
                    xml =
 xml + "<accounting_unit_id>" + rs7.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                    xml =
 xml + "<accounting_unit_name>" + rs7.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                    xml = xml + "</option>";
                }
                xml = xml + "<flag>success</flag>";
            } catch (SQLException e) {
                System.out.println("Failed to Fetch Record for Displaying the Units Which come under particular Region -->" +
                                   e);
            }

        }
			//	if
		//added on 11feb2013
      else if (FAS_TDA_HO_ROLE.equalsIgnoreCase("YES") && (COMMAND.equalsIgnoreCase("LIST_ALL_UNITS")))
        		{
			    	  try{
			    	  String su =
			              "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS order by ACCOUNTING_UNIT_NAME";
			          ps = con.prepareStatement(su);
			          rs = ps.executeQuery();
			          while (rs.next()) {
			        	  xml = xml + "<flag>success</flag>";
			              xml = xml + "<option>";
			              xml =
					xml + "<accounting_unit_id>" + rs.getInt("ACCOUNTING_UNIT_ID") +
					"</accounting_unit_id>";
					              xml =
					xml + "<accounting_unit_name>" + rs.getString("ACCOUNTING_UNIT_NAME") +
					"</accounting_unit_name>";
			              xml = xml + "</option>";
			          }
			    	  }
			    	  catch(Exception ee)
			    	  {
			    		  System.out.println("Error in loading FAS_TDA_HO_ROLE -->" +
			                      ee);
			    	  }
    	  
    	  
        		}
        /**
                            * The following are the Regular Units displays
                            */
        else if (FAS_SU.equalsIgnoreCase("YES") &&
                 (COMMAND.equalsIgnoreCase("LIST_ALL_UNITS") ||
                  COMMAND.equalsIgnoreCase("LIST_ALL_UNITS_ONLY") ||
                  COMMAND.equalsIgnoreCase("FOR_LIST_0") ||
                  COMMAND.equalsIgnoreCase("FOR_LIST_1"))) {
            System.out.println("4-----><");
            if (office_id == 5000) {

                try {
                    int unitid = 0;
                    String getWing =
                        "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                    ps = con.prepareStatement(getWing);
                    ps.setInt(1, office_id);
                    ps.setInt(2, employee_id);
                    ps.setInt(3, office_id);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        xml = xml + "<option>";
                        xml =
 xml + "<accounting_unit_id>" + rs.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                        xml =
 xml + "<accounting_unit_name>" + rs.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                        xml = xml + "</option>";

                        unitid = rs.getInt("ACCOUNTING_UNIT_ID");

                        if (session.getAttribute("FAS_SU") != null &&
                            ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES")) {
                            String su =
                                "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                            ps = con.prepareStatement(su);
                            ps.setInt(1, unitid);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                xml = xml + "<option>";
                                xml =
 xml + "<accounting_unit_id>" + rs.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                                xml =
 xml + "<accounting_unit_name>" + rs.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                                xml = xml + "</option>";
                            }
                        }
                        xml = xml + "<flag>success</flag>";

                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

            } else {


            }

        }
        
				
      /*  else if(COMMAND.equalsIgnoreCase("FOR_LIST_1"))
        {
        	System.out.println("comes");
        	try{
        	 String su ="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS order by ACCOUNTING_UNIT_NAME";
             ps = con.prepareStatement(su);
           
             rs = ps.executeQuery();
             while (rs.next()) {
            	 xml = xml + "<flag>success</flag>";
                 xml = xml + "<option>";
                 xml =
xml + "<accounting_unit_id>" + rs.getInt("ACCOUNTING_UNIT_ID") +
"</accounting_unit_id>";
                 xml =
xml + "<accounting_unit_name>" + rs.getString("ACCOUNTING_UNIT_NAME") +
"</accounting_unit_name>";
                 xml = xml + "</option>";
             }
        	}
        	catch(Exception ee)
        	{
        		 xml = xml + "<flag>failure</flag>";
        		System.out.println("excep in ::::"+ee);
        	}
        }  */
				
        else {
            System.out.println("5-----><");

            /** Current Posting = Head Office */
            if (office_id == 5000) {
System.out.println("5000");
System.out.println(office_id+" "+employee_id);
                try {

                    /** Fetch Current Posting Details */
                    String getWing =
                        "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                    ps = con.prepareStatement(getWing);
                    ps.setInt(1, office_id);
                    ps.setInt(2, employee_id);
                    ps.setInt(3, office_id);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        System.out.println("1--------------->");
                        xml = xml + "<option>";
                        xml =
 xml + "<accounting_unit_id>" + rs.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                        xml =
 xml + "<accounting_unit_name>" + rs.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                        xml = xml + "</option>";

                    }


                    /** Fetch Additional Posting Details */
                    String getWing_add =
                        "select office_id as off_id  from hrm_emp_addl_charge  where employee_id= ? ";
                    ps1 = con.prepareStatement(getWing_add);
                    ps1.setInt(1, employee_id);
                    rs2 = ps1.executeQuery();
                    if (rs2.next()) {

                        int add_office_id = rs2.getInt("off_id");

                        /** Current Posting = Head Office   and    Additional Charge = Head Office */
                        if (add_office_id == 5000) {


                            String sql =
                                "  select  ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_NAME,  OFFICE_WING_SINO                                                                                                          \n" +
                                "  from  FAS_MST_ACCT_UNITS                                                                                                                                                                \n" +
                                "  where ACCOUNTING_UNIT_OFFICE_ID in (  select office_id from hrm_emp_addl_charge  where employee_id= ? )                                                                                 \n" +
                                "  and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id= ? and office_id in (  select office_id from hrm_emp_addl_charge  where employee_id= ? ))  \n" +
                                "                                                                                                                                                                                          \n";

                            ps2 = con.prepareStatement(sql);
                            ps2.setInt(1, employee_id);
                            ps2.setInt(2, employee_id);
                            ps2.setInt(3, employee_id);

                            rs3 = ps2.executeQuery();

                            while (rs3.next()) {
                                System.out.println("2--------------->");
                                xml = xml + "<option>";
                                xml =
 xml + "<accounting_unit_id>" + rs3.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                                xml =
 xml + "<accounting_unit_name>" + rs3.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                                xml = xml + "</option>";

                            }

                        }

                        /** Current Posting = Head Office   and    Additional Charge = Other Offices */
                        else {
                            System.out.println("J");
                            String sql =
                                "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)";

                            ps3 = con.prepareStatement(sql);

                            ps3.setInt(1, add_office_id);
                            rs4 = ps3.executeQuery();
                            while (rs4.next()) {
                                System.out.println("3--------------->");
                                xml = xml + "<option>";
                                xml =
 xml + "<accounting_unit_id>" + rs4.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                                xml =
 xml + "<accounting_unit_name>" + rs4.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                                xml = xml + "</option>";
                            }

                        }


                    } // End of Result Set


                    xml = xml + "<flag>success</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println(e);
                }

            }


            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            /** Current Posting  =  other Offices  */
            else {
                System.out.println("6-----><");
                //modifications on 28feb2012
                System.out.println("modifications starts********");
            /*    try{
                	
          		  ps=con.prepareStatement("SELECT Office_Id FROM Hrm_Mst_Grant_Closed_Office WHERE User_Id=? and ENABLED='y' and DATE_ALLOWED_UPTO>=?");
          		  closedOff="twad"+employee_id;
          		  System.out.println("closedOff::"+closedOff);
          		  ps.setString(1,closedOff); 
          		  ps.setDate(2, ctdate);
          		  rs=ps.executeQuery();
                    if(rs.next())
                    {
                  	  oldOff=rs.getInt("Office_Id");
                  	  System.out.println("oldOff:::"+oldOff);
                    }
                    else
                    {
                  	  oldOff=0;
                    }
          	  }
          	  catch(Exception ee)
          	  {
          		  System.out.println("exc in closed office"+ee);
          	  }
          	  System.out.println(oldOff);  */
                System.out.println("be4");
                try {
                    ps =
  con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID =(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID =?)");
                   System.out.println("off id:::"+office_id);
                 //   ps.setInt(1, office_id);
                    ps.setInt(1,office_id);
                  //  ps.setInt(2,oldOff);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        System.out.println("4--------------->");
                        xml = xml + "<option>";
                        xml =
 xml + "<accounting_unit_id>" + rs.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                        xml =
 xml + "<accounting_unit_name>" + rs.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                        xml = xml + "</option>";
                    }


                    /** Fetch Additional Posting Details */
                    String getWing_add =
                        "select office_id as off_id from hrm_emp_addl_charge  where employee_id= ? ";
                    ps1 = con.prepareStatement(getWing_add);
                    ps1.setInt(1, employee_id);
                    rs2 = ps1.executeQuery();
                    if (rs2.next()) {
                        int add_office_id = rs2.getInt("off_id");

                        /** Current Posting = Head Office   and    Additional Charge = Head Office */
                        if (add_office_id == 5000) {
                        		System.out.println("5000 comes:::");
                            String sql =
                                "  select  ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_NAME,  OFFICE_WING_SINO                                                                                                          \n" +
                                "  from  FAS_MST_ACCT_UNITS                                                                                                                                                                \n" +
                                "  where ACCOUNTING_UNIT_OFFICE_ID in (  select office_id from hrm_emp_addl_charge  where employee_id= ? )                                                                                 \n" +
                                "  and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id= ? and office_id in (  select office_id from hrm_emp_addl_charge  where employee_id= ? ))  \n" +
                                "                                                                                                                                                                                          \n";

                            ps2 = con.prepareStatement(sql);
                            ps2.setInt(1, employee_id);
                            ps2.setInt(2, employee_id);
                            ps2.setInt(3, employee_id);
                            rs3 = ps2.executeQuery();
                            while (rs3.next()) {
                                System.out.println("5--------------->");
                                xml = xml + "<option>";
                                xml =
 xml + "<accounting_unit_id>" + rs3.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                                xml =
 xml + "<accounting_unit_name>" + rs3.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                                xml = xml + "</option>";

                            }

                        }

                        /** Current Posting = Head Office   and    Additional Charge = Other Offices */
                        else {
                        	System.out.println("else comes:::");
                        System.out.println("add_office_id:::"+add_office_id);
                            String sql =
                                "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)";
                            ps3 = con.prepareStatement(sql);
                            ps3.setInt(1, add_office_id);
                            rs4 = ps3.executeQuery();
                            while (rs4.next()) {
                                System.out.println("6--------------->");
                                xml = xml + "<option>";
                                xml =
 xml + "<accounting_unit_id>" + rs4.getInt("ACCOUNTING_UNIT_ID") +
   "</accounting_unit_id>";
                                xml =
 xml + "<accounting_unit_name>" + rs4.getString("ACCOUNTING_UNIT_NAME") +
   "</accounting_unit_name>";
                                xml = xml + "</option>";
                            }

                        }


                    } // End of Result Set

                    xml = xml + "<flag>success</flag>";
                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println(e);
                }


            }
        }


        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);


        out.close();
    }

    //---------------------------------------------------------------------------------------------------------------

}

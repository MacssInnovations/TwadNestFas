package Servlets.FAS.FAS1.ReceiptSystem.servlets;
/*
         * All this retrival functions for javascript use this servlet
         checkCode
         loadcheckCode_grid
         loademp
         TB_check
         dept
         Load_SL_Code
         Load_SL_Code_grid
         */

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.CommonControls.servlets.Restricted_AccountHead;


import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class CopyOfReceipt_SL1 extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
System.out.println("receipt_sl_javaaaaaaaaaaaaaaaaaaaaaaaaaaas testtttttttt");

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        int employee_id = 0;

        HttpSession session = request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");
        System.out.println("user id::" + empProfile.getEmployeeId());
        employee_id = empProfile.getEmployeeId();
        int old_id=0;

        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null, rsbank = null,rs33=null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, psbank = null,ps33=null;
        //String xml="";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        
            
        if (strCommand.equalsIgnoreCase("checkCode")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";
            int txtAcc_HeadCode = 0,cmbOffice_code=0;
            int h_count=0,offid=0,off_notEqual=0;
            try {

                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));

            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            try {

            	cmbOffice_code =Integer.parseInt(request.getParameter("cmbOffice_code"));

            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }

            Restricted_AccountHead rah = new Restricted_AccountHead();

            if (rah.accountHeadDetails(txtAcc_HeadCode, employee_id) == 0) {
            System.out.println("account head code");

                try {
                    ps = con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS,sl_mandatory from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                    ps.setInt(1, txtAcc_HeadCode);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        xml =xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
					   rs.getString("ACCOUNT_HEAD_DESC") + "</hdesc><BalType>" +
					   rs.getString("BALANCE_TYPE") + "</BalType><SL_YN>" +
					   rs.getString("SUB_LEDGER_TYPE_APPLICABLE")	 + "</SL_YN><rmk>" +
					   rs.getString("REMARKS") + "</rmk><sl_man>" + rs.getString("sl_mandatory") +
					   "</sl_man>";
                        
                        ps33 = con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNTING_UNIT_ID as officeId from Fas_Restricted_Ac_Heads where ACCOUNT_HEAD_CODE=?");
                        ps33.setInt(1, txtAcc_HeadCode);
                        rs33 = ps33.executeQuery();
                        while(rs33.next())
                        {
                        	h_count++;
                        	offid=rs33.getInt("officeId");
                        	System.out.println("comess:"+offid);
                        	//if interface officeid and Fas_Restricted_Ac_Heads officeid equals proceed, else accounthead restricted
                        	if(cmbOffice_code==offid)
                        	{
                        		off_notEqual++;
                        		//System.out.println("cmbOffice_code:"+cmbOffice_code);
		                        		 if (rs.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) {
		                                    int sl_cnt=0;
		                        			 ps =con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'"); //STATUS Field added in 05-07-19
		                                     ps.setInt(1, txtAcc_HeadCode);
		                                     rs = ps.executeQuery();
		                                     while (rs.next()) {
		                                         sl_cnt++;
		                                    	 xml =xml + "<SLCODE>" + rs.getInt("SUB_LEDGER_TYPE_CODE") + "</SLCODE>";
		                                         System.out.println(rs.getInt("SUB_LEDGER_TYPE_CODE") +
		                                                            "code");
		                                         if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
		                                             System.out.println("take SL DESC");
		                                             ps2 = con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
		                                             ps2.setInt(1,rs.getInt("SUB_LEDGER_TYPE_CODE"));
		                                             rs2 = ps2.executeQuery();
		                                             if (rs2.next())
		                                                 xml = xml + "<SLDESC>" + rs2.getString("SUB_LEDGER_TYPE_DESC") + "</SLDESC>";
		                                         }
		                                     }
		                                     if(sl_cnt==0) {
		                                    	 System.out.println("Account Head Not Allowed");
		    		                              xml ="<response><command>" + strCommand + "</command><flag>failure</flag>";
		                                     }
		                                 }	
                        	}
                        	
                        }

                        if(h_count==0)
                        {
                         
 		                        if (rs.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) {
 		                            int sl_cnt=0;
 		                        	ps =con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'"); //STATUS field added in 05-07-19
 		                            ps.setInt(1, txtAcc_HeadCode);
 		                            rs = ps.executeQuery();
 		                            while (rs.next()) {
 		                                sl_cnt++;
 		                            	xml =xml + "<SLCODE>" + rs.getInt("SUB_LEDGER_TYPE_CODE") + "</SLCODE>";
 		                                System.out.println(rs.getInt("SUB_LEDGER_TYPE_CODE") +
 		                                                   "code");
 		                                if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
 		                                    System.out.println("take SL DESC");
 		                                    ps2 = con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
 		                                    ps2.setInt(1,rs.getInt("SUB_LEDGER_TYPE_CODE"));
 		                                    rs2 = ps2.executeQuery();
 		                                    if (rs2.next())
 		                                        xml = xml + "<SLDESC>" + rs2.getString("SUB_LEDGER_TYPE_DESC") + "</SLDESC>";
 		                                }
 		                            }
 		                            if(sl_cnt==0) {
                                   	 System.out.println("Account Head Not Allowed");
   		                              xml ="<response><command>" + strCommand + "</command><flag>failure</flag>";
                                    }
 		                        }
                         }
                        else
                        {
 		                    	 if(off_notEqual==0)
 		                    	 {
 		                    		  System.out.println("Account Head Not Allowed");
 		                              xml ="<response><command>" + strCommand + "</command><flag>failure</flag>";
 		                    	 }
                        }
                    } else {
                        System.out.println("No record found");
                        xml = xml + "<flag>failure</flag>";
                    }


                } catch (Exception e) {
                    System.out.println("catch..HERE.in load head code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else {
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("loadoffice")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";
            int Office_code = 0;
            try {
                Office_code =
                        Integer.parseInt(request.getParameter("Office_code"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            xml = xml + "<offid>" + Office_code + "</offid>";
            try {
                ps =
  con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? ");
                ps.setInt(1, Office_code);
                rs = ps.executeQuery();

                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><offname>" + rs.getString("OFFICE_NAME") +
   "</offname>";
                } else {
                    System.out.println("No record found");
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        else if (strCommand.equalsIgnoreCase("LoadUnitWise_Office")) {
            
            //added by sathya on 11May2012
           
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";

            int cmbAcc_UnitCode = 0;
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            try {
                if( (employee_id==8569) || (employee_id==8696) )
                {
                System.out.println("Testing office id ");
                
                //Lakshmi
               /* ps =
                    con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES_RD a,COM_MST_OFFICES b " +
                    "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=? ");*/
                    ps =
                      con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
                      "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=? ");
                                    ps.setInt(1, cmbAcc_UnitCode);
                }
                else
                {
                    ps =
                        con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
                        "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=? ");
                                      ps.setInt(1, cmbAcc_UnitCode);
                   
                }
              //  ps.setInt(2, cmbAcc_UnitCode);
                rs = ps.executeQuery();
                int cnt = 0;

                while (rs.next()) {
                    xml =
 xml + "<offid>" + rs.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
                    xml =
 xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
                    cnt++;
                }
               
                if (cnt != 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        //commented by sathya
        /*else if (strCommand.equalsIgnoreCase("LoadUnitWise_Office")) {
        	
        	//added by sathya on 11May2012
        	
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";

            int cmbAcc_UnitCode = 0;
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            
            try
            {
	            ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where EMPLOYEE_ID=?");	
	            ps2.setInt(1,employee_id );
	            rs2=ps2.executeQuery();
	            while(rs2.next())
	            	{
	            		old_id=old_id+1;
	            		System.out.println("coming here");
	            	}
	            System.out.println("old_id******"+old_id);
             }
            catch(Exception e)
            {System.out.println("Exception in grtting old_officed_id**"+e);}
            try {
            	if(employee_id==8569)
            	{
                System.out.println("Testing office id ");
            		ps =
					  con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES_RD a,COM_MST_OFFICES b " +
					  "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=? ");
					                ps.setInt(1, cmbAcc_UnitCode);
            	}
            	else if(old_id!=0)
            	{
            			ps =
  					  con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
  					  "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=?");
  					                ps.setInt(1, cmbAcc_UnitCode);
               	}
            	else if(old_id==0)
            	{
            		ps =
        					  con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
        					  "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
        					                ps.setInt(1, cmbAcc_UnitCode);
            	
            	}
              //  ps.setInt(2, cmbAcc_UnitCode);
                rs = ps.executeQuery();
                int cnt = 0;

                while (rs.next()) {
                    xml =
 xml + "<offid>" + rs.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
                    xml =
 xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
                    cnt++;
                }
            	
                if (cnt != 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e1) {
                System.out.println("catch..HERE.in load head code." + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }  */
        //modifications on 27march2012
        else if (strCommand.equalsIgnoreCase("LoadUnitWise_Office_Create")) 
        {
        	String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";

            int cmbAcc_UnitCode = 0;
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            try {
                ps =
  con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
  "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=?  AND B.OFFICE_STATUS_ID NOT IN('RD','NC','CL')");
                ps.setInt(1, cmbAcc_UnitCode);
              //  ps.setInt(2, cmbAcc_UnitCode);
                rs = ps.executeQuery();
                int cnt = 0;

                while (rs.next()) {
                    xml =
 xml + "<offid>" + rs.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
                    xml =
 xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
                    cnt++;
                }
                if (cnt != 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }else if(strCommand.equalsIgnoreCase("LoadREcOffice")){
        	String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";

        	 int cmbAcc_UnitCode = 0;
             try {
                 cmbAcc_UnitCode =
                         Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
             } catch (Exception e) {
                 System.out.println("Exception to catch account head ");
             }
             try {
                 ps =
   con.prepareStatement(" SELECT DISTINCT ACCOUNTING_FOR_OFFICE_ID, " +
"  OFFICE_NAME " +
" FROM " +
"  (SELECT ACCOUNTING_FOR_OFFICE_ID, " +
"    b.OFFICE_NAME " +
"  FROM FAS_MST_ACCT_UNIT_OFFICES a, " +
"    COM_MST_OFFICES b " +
"  WHERE a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID " +
"  AND a.ACCOUNTING_UNIT_ID        =? " +
"  AND B.OFFICE_STATUS_ID NOT     IN('RD','NC','CL') " +
"  UNION ALL " +
"  SELECT b.RENDERING_UNIT_OFFICE_ID, " +
"    c.office_name " +
"  FROM FAS_MST_ACCT_UNIT_OFFICES a, " +
"    FAS_ASSET_VAL_AC_RENDER_UNITS b, " +
"    COM_MST_OFFICES c " +
"  WHERE a.ACCOUNTING_UNIT_ID    =b.ACCT_RENDERING_UNIT_ID " +
"  AND b.RENDERING_UNIT_OFFICE_ID=c.OFFICE_ID " +
"  AND a.ACCOUNTING_UNIT_ID      =? " +
"  AND OFFICE_WING_SINO          =99 " +
"  )a " +
" ORDER BY ACCOUNTING_FOR_OFFICE_ID");
                 ps.setInt(1, cmbAcc_UnitCode);
                 ps.setInt(2, cmbAcc_UnitCode);
               //  ps.setInt(2, cmbAcc_UnitCode);
                 rs = ps.executeQuery();
                 int cnt = 0;

                 while (rs.next()) {
                     xml =
  xml + "<offid>" + rs.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
                     xml =
  xml + "<offname>" + rs.getString("OFFICE_NAME") + "</offname>";
                     cnt++;
                 }
                 if (cnt != 0)
                     xml = xml + "<flag>success</flag>";
                 else
                     xml = xml + "<flag>failure</flag>";
             } catch (Exception e) {
                 System.out.println("catch..HERE.in load head code." + e);
                 xml = xml + "<flag>failure</flag>";
             }
             xml = xml + "</response>";
             System.out.println(xml);
             out.println(xml);
        }
        else if (strCommand.equalsIgnoreCase("loadEmployee")) {
        System.out.println("loadEmployee:::::::::::::::::::::::::");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>" + strCommand + "</command>";
            int Office_code = 0, employeeID = 0;
            try {
                Office_code =
                        Integer.parseInt(request.getParameter("Office_code"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            try {
                employeeID =
                        Integer.parseInt(request.getParameter("employeeID"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            xml = xml + "<empid>" + employeeID + "</empid>";
            try {
                ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME ||'-'||d.DESIGNATION AS empName_and_desig from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.DEPARTMENT_ID='TWAD' and c.OFFICE_ID=? and e.EMPLOYEE_ID=?");
                ps.setInt(1, Office_code);
                ps.setInt(2, employeeID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>success</flag><EMPname>" + rs.getString("empName_and_desig") +
   "</EMPname>";
                } else {
                    System.out.println("No record found");
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        
        
        
        else if (strCommand.equalsIgnoreCase("LoadVendor_circle")) {
            
        	System.out.print("hhhhhhhhhhhhhhhhhhh");
                   int Unit_id=0;
                    String CONTENT_TYPE = "text/xml; charset=windows-1252";
                    response.setContentType(CONTENT_TYPE);
                    String xml = "";
                    xml = "<response><command>" + strCommand + "</command>";
                    try {
                        Unit_id =
                                Integer.parseInt(request.getParameter("unit").split("-")[1]);
                    } catch (Exception e) {
                        System.out.println("Exception to catch account head ");
                    }
                   System.out.println("Unti ???? "+Unit_id);
                    try {
                        
                            ps = con.prepareStatement("SELECT ACCT_RENDERING_UNIT_ID as off_id, " +
                            		"  trim(au.accounting_unit_name) as accounting_unit_name " +
                            		"FROM FAS_ASSET_VAL_AC_RENDER_UNITS u , " +
                            		"  fas_mst_acct_units au " +
                            		"WHERE RENDERING_UNIT_OFFICE_ID=? " +
                            		"AND u.acct_rendering_unit_id  =au.accounting_unit_id");
        System.out.print("hhhhhhhhhhhhhhhhhhh");
                            
                           ps.setInt(1, Unit_id);
                           
                           
                        
                      //  ps.setInt(2, cmbAcc_UnitCode);
                        rs = ps.executeQuery();
                        int cnt = 0;

                        while (rs.next()) {
                            xml =
         xml + "<offid1>" + rs.getString("off_id") + "</offid1>";
                            xml =   xml + "<offid_name>" + rs.getString("accounting_unit_name") + "</offid_name>";                   
                            cnt++;
                        }
                       
                        if (cnt != 0)
                            xml = xml + "<flag>success</flag>";
                        else
                            xml = xml + "<flag>failure</flag>";
                    } catch (Exception e) {
                        System.out.println("catch..HERE.in load head code." + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                    xml = xml + "</response>";
                    System.out.println(xml);
                    out.println(xml);
                }
                 
                
                 
        
        
        
        
        
        ///Veni Added Function for test.jsp
        
        
        
        else if (strCommand.equalsIgnoreCase("LoadUnitWise_circle")) {
                    
        	System.out.print("hhhhhhhhhhhhhhhhhhh");
                   
                    String CONTENT_TYPE = "text/xml; charset=windows-1252";
                    response.setContentType(CONTENT_TYPE);
                    String xml = "";
                    xml = "<response><command>" + strCommand + "</command>";

                   
                    try {
                        
                            ps = con.prepareStatement("SELECT accounting_unit_id " +
                            		"  ||'-' " +
                            		"  ||ACCOUNTING_UNIT_OFFICE_ID AS off_id, " +
                            		"  trim(accounting_unit_name)  AS accounting_unit_name " +
                            		" FROM fas_mst_acct_units " +
                            		" WHERE ACCOUNTING_UNIT_OFFICE_ID NOT IN " +
                            		"  (SELECT OFFICE_ID " +
                            		"  FROM COM_MST_OFFICES " +
                            		"  WHERE OFFICE_STATUS_ID IN('CL','RD','NC') " +
                            		"  ) " +
                            		" AND STATUS IS NULL " +
                            		" ORDER BY accounting_unit_name");
        System.out.print("hhhhhhhhhhhhhhhhhhh");
                            
                       //    ps.setInt(1, cmbAcc_UnitCode);
                           
                        
                      //  ps.setInt(2, cmbAcc_UnitCode);
                        rs = ps.executeQuery();
                        int cnt = 0;

                        while (rs.next()) {
                            xml =
         xml + "<offid1>" + rs.getString("off_id") + "</offid1>";
                            xml =   xml + "<offid_name>" + rs.getString("accounting_unit_name") + "</offid_name>";                   
                            cnt++;
                        }
                       
                        if (cnt != 0)
                            xml = xml + "<flag>success</flag>";
                        else
                            xml = xml + "<flag>failure</flag>";
                    } catch (Exception e) {
                        System.out.println("catch..HERE.in load head code." + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                    xml = xml + "</response>";
                    System.out.println(xml);
                    out.println(xml);
                }
                 
                
                
                
        
        else if (strCommand.equalsIgnoreCase("Load_SL_Code") ||
                   strCommand.equalsIgnoreCase("Load_MasterSL_Code")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
            int cmbSL_type = 0;
            int addtional_field_value = 0;
            int y = 0;
            xml = "<response><command>" + strCommand + "</command>";
           
            try {
                cmbSL_type =
                        Integer.parseInt(request.getParameter("cmbSL_type"));
            } catch (Exception e) {
                System.out.println("error get SL_type");
            }
            
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("error get acc unit code");
            }
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (Exception e) {
                System.out.println("error get office id");
            }
            System.out.println("SL_TYPE " + cmbSL_type + "NN");
            System.out.println(cmbAcc_UnitCode);
            System.out.println(cmbOffice_code);
            xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
            if (cmbSL_type == 1) {
                System.out.println("here in supplier 1");
                try {
                    ps =
  con.prepareStatement("select SUPPLIER_ID,SUPPLIER_NAME from COM_SUPPLIER_SL_MST where STATUS='L' AND ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? order by SUPPLIER_NAME");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("SUPPLIER_ID") + "</cid><cname>" +
   rs.getString("SUPPLIER_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 2) {
                System.out.println("here in Firms 2");
                try {
                    ps =
  con.prepareStatement("select FIRMS_ID, FIRMS_NAME from COM_FIRMS_SL_MST where STATUS='L' AND ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? order by FIRMS_NAME");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("FIRMS_ID") + "</cid><cname>" +
   rs.getString("FIRMS_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load firms" + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 3) {
                System.out.println("here in assets 3");
                try {
                    ps =
  con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION from COM_MST_ASSETS_SL where STATUS='L' AND ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? order by ASSET_DESCRIPTION");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("ASSET_CODE") + "</cid><cname>" +
   rs.getString("ASSET_DESCRIPTION").trim() + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 5) {
                try {

                    try {
                        addtional_field_value =
                                Integer.parseInt(request.getParameter("addtional_field_value"));
                    } catch (Exception e) {
                        System.out.println("error get addtional_field_value");
                    }
                    ps =
  con.prepareStatement("select OFFICE_ID, OFFICE_NAME,OFFICE_STATUS_ID from COM_MST_OFFICES where OFFICE_ID=? ");
                    ps.setInt(1, addtional_field_value);
                    rs = ps.executeQuery();
                    System.out.println("inside offices 5");
                    int cnt = 0;
                    if (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("OFFICE_ID") + "</cid><cname>" +
   rs.getString("OFFICE_NAME") + "</cname>";
                        xml =
 xml + "<state>" + rs.getString("OFFICE_STATUS_ID") + "</state>";
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                   
                    System.out.println(cnt + "count");
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load office code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 6) {

                try {
                    System.out.println("Inside 6 Load Bank Account Number ");
                    String bank_sql =
                        "SELECT BANK_AC_NO , AC_OPERATIONAL_MODE_ID FROM FAS_MST_BANK_BALANCE WHERE STATUS='Y' AND ACCOUNTING_UNIT_ID=? ORDER BY AC_OPERATIONAL_MODE_ID , BANK_AC_NO";
                    ps = con.prepareStatement(bank_sql);
                    ps.setInt(1, cmbAcc_UnitCode);
                    rs = ps.executeQuery();

                    long bank_acc_no;
                    String acc_no_plus_mode = "";
                    String operation_mode = "";
                    int bank_ac_no_alias_code;

                    while (rs.next()) {
                        bank_acc_no = rs.getLong("BANK_AC_NO");
                        operation_mode =
                                rs.getString("AC_OPERATIONAL_MODE_ID");
//                        bank_ac_no_alias_code =
//                                rs.getInt("BANK_AC_NO");

                        acc_no_plus_mode = operation_mode + "-" + bank_acc_no;
                        xml =
 xml + "<cid>" + bank_acc_no + "</cid><cname>" + acc_no_plus_mode +
   "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                    System.out.println("Bank A/C is " + xml);
                } catch (Exception e) {
                    System.out.println("Error in Loading Bank Account Number." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }


            } else if (cmbSL_type == 7) {
                int other_dept_off_alias_id = 0, oid = 0, cmbMas_SL_type = 0;
                String deptid = "";
                System.out.println("inside 7 employeees");
                try {

                    try {
                        addtional_field_value =
                                Integer.parseInt(request.getParameter("addtional_field_value"));
                    } catch (Exception e) {
                        System.out.println("error get addtional_field_value");
                    }
                    try {
                        cmbMas_SL_type =
                                Integer.parseInt(request.getParameter("cmbMas_SL_type"));
                    } catch (Exception e) {
                        System.out.println("getting master combo sl type failed");
                    }
                    try {
                        other_dept_off_alias_id =
                                Integer.parseInt(request.getParameter("other_dept_off_alias_id"));
                    } catch (Exception e) {
                        System.out.println("getting master combo slcode failed");
                    }
                    System.out.println("other_dept_off_alias_id.." +
                                       other_dept_off_alias_id);
                    //deptid=request.getParameter("deptid");
                    System.out.println(oid + " " +
                                       deptid+"  cmbMas_SL_type > "+cmbMas_SL_type+"  addtional_field_value "+addtional_field_value); // OTHER_DEPT_ALIAS_ID
                  
                    if (cmbMas_SL_type == 9) {
                        try {
                        	  //Lakshmi 6Nov13 
                        /*	int montt=  Integer.parseInt(request.getParameter("month1"));
                        	int yearrr=  Integer.parseInt(request.getParameter("year11"));*/
                
                        	
                        //	System.out.println("montt  "+montt+" yearrr "+ yearrr);
                        	String odid="select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ALIAS_ID= "+other_dept_off_alias_id;
                        	System.out.println("inside other employee:::"+odid);
                            ps =con.prepareStatement(odid);
                            //ps.setInt(1, other_dept_off_alias_id);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                deptid = rs.getString("OTHER_DEPT_ID").trim();
                                oid = rs.getInt("OTHER_DEPT_OFFICE_ID");
                            }
                            rs.close();
                            ps.close();
                         
                           String qu=" select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL ||'-'|| d.DESIGNATION as ENAME" +
                       " from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d " +
                       " where c.DESIGNATION_ID=d.DESIGNATION_ID  and c.EMPLOYEE_ID=e.EMPLOYEE_ID and c.EMPLOYEE_STATUS_ID='DPN' " +
                    //   " and c.OFFICE_ID='"+oid+"'
                       // joan change on 30 OCt 2014
                       " and c.DEPARTMENT_ID='"+deptid+"' and e.EMPLOYEE_ID='"+addtional_field_value+"'";
                          System.out.println("qu:::"+qu);
                           ps =con.prepareStatement(qu);
//                            ps.setInt(1, oid);
//                            ps.setString(2, deptid);
//                            ps.setInt(3,addtional_field_value); // later modified
                            rs = ps.executeQuery();

                            if (rs.next()) {
                                xml = xml + "<flag>success</flag>";
                                xml = xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
						   rs.getString("ENAME") + "</cname>";

                            } else {
                            	
                                xml = xml + "<flag>failure</flag>";
                            }
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }

                    }

                    else {
                        try {
                        System.out.println("::::::addtional_field_value:::::"+addtional_field_value);
                            // ps=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and e.OFFICE_ID=? and e.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
                            //ps.setInt(1,cmbOffice_code);
                          
                        String qry="SELECT e.EMPLOYEE_ID,\n" + 
                                "  e.EMPLOYEE_NAME\n" + 
                                "  ||'.'\n" + 
                                "  ||e.EMPLOYEE_INITIAL AS ENAME,\n" + 
                                "  'CR'                 AS EMPLOYEE_STATUS_ID\n" + 
                                " From Hrm_Mst_Employees E\n" + 
                                " Where E.Employee_Id= ?\n" + 
                                " ORDER BY e.EMPLOYEE_NAME ";
                        ps =  con.prepareStatement(qry);
  /*
   ps =  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c," +
   " HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
   */
                            ps.setInt(1, addtional_field_value);
                            rs = ps.executeQuery();

                            if (rs.next()) {
                                xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
   rs.getString("ENAME") + "</cname>";
                                xml =
 xml + "<state>" + rs.getString("EMPLOYEE_STATUS_ID") + "</state>";
                                xml = xml + "<flag>success</flag>";
                            } else
                                xml = xml + "<flag>failure</flag>";

                            ps.close();
                            rs.close();
                        } catch (Exception e) {
                            System.out.println("catch Exception >> " +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } catch (Exception e) {
                    System.out.println("catch..HERE.in getting request value code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }

            } else if (cmbSL_type == 9) {
                System.out.println("inside 9 OTHER Departments");

                try {
                    ps =
  con.prepareStatement("select dep.OTHER_DEPT_NAME || '-' || off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFF_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                       ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ORDER BY dep.OTHER_DEPT_NAME");
                    rs = ps.executeQuery();

                    while (rs.next()) {

                        xml =
 xml + "<cid>" + rs.getInt("OTHER_DEPT_OFFICE_ALIAS_ID") + "</cid><cname>" +
   rs.getString("OTHER_DEPT_OFF_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load department code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 10) {
                try {
                    System.out.println("inside 10 here projects");
                    if(cmbAcc_UnitCode==5)
                    {
                    	 ps = con.prepareStatement("select PROJECT_ID, PROJECT_NAME  from PMS_MST_PROJECTS_VIEW where  STATUS='L' AND OFFICE_ID=? and Wing_Id=2 order by PROJECT_NAME");
                    }
                    else{
                    ps = con.prepareStatement("select PROJECT_ID, PROJECT_NAME  from PMS_MST_PROJECTS_VIEW where  STATUS='L' AND OFFICE_ID=? order by PROJECT_NAME");
                    }
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =	xml + "<cid>" + rs.getInt("PROJECT_ID") + "</cid><cname><![CDATA[" +  rs.getString("PROJECT_NAME") + "]]></cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>NotData</flag>";//Lakshmi 29oct13

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 11) {
                try {
                    System.out.println("inside 11 contractors");
//                    String contra =
//                        " select CONTRACTOR_ID,CONTRACTOR_NAME " + " from PMS_MST_CONTRACTORS_VIEW " +
//                        " where STATUS='L' AND OFFICE_ID=? or OFFICE_ID in " +
//                        "                                (select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where office_id=? " +
//                        "                                 union all " +
//                        "                                 select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where office_id=? ) " +
//                        "order by CONTRACTOR_NAME";
                    String contra ="SELECT CONTRACTOR_ID , "+
					"  CONTRACTOR_NAME    "+
					" From PMS_CONT_REQUEST_REGN"+
					" WHERE OFFICE_ID=? and status='L' order by CONTRACTOR_NAME ";
                    ps = con.prepareStatement(contra);
                    ps.setInt(1, cmbOffice_code);
//                    ps.setInt(2, cmbOffice_code);
//                    ps.setInt(3, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        //xml=xml+"<Remak><![CDATA["+rs.getString("REMARKS")+"]]></Remak>";
    xml =xml + "<cid>" + rs.getString("CONTRACTOR_ID") + "</cid><cname><![CDATA[" + rs.getString("CONTRACTOR_NAME") + "]]></cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load contractor." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            }

            else if (cmbSL_type == 14) // Beneficiaries
            {
                try {
                    ps =
  con.prepareStatement("SELECT BENEFICIARY_SNO AS sl_code,BENEFICIARY_NAME as sl_code_desc From Pms_Dcb_Mst_Beneficiary Where office_id = ? order by BENEFICIARY_NAME");


                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
   rs.getString("sl_code_desc") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }

            }
            else if(cmbSL_type==15)
            { 
               System.out.println("Inside Accounting Unit");
                   try {
                           ps=con.prepareStatement("SELECT accounting_unit_id,trim(accounting_unit_name) as accounting_unit_name FROM fas_mst_acct_units WHERE accounting_unit_id!=? and ACCOUNTING_UNIT_OFFICE_ID NOT IN(SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID IN('CL','RD','NC'))  and STATUS is null order by accounting_unit_name");
                           ps.setInt(1,cmbAcc_UnitCode);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                          
                           while(rs.next())
                           {
                           xml=xml+"<cid>"+rs.getInt("accounting_unit_id")+
                           "</cid><cname>"+rs.getString("accounting_unit_name")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            
            //Miscellaneous
            else if(cmbSL_type==17)
            { 
               
                   try {
                           ps=con.prepareStatement("SELECT TYPE_ID,trim(TYPE_NAME) AS TYPE_NAME From Fas_Sl_Types_User_Defined WHERE ACCOUNTING_UNIT_ID=? ORDER BY TYPE_ID");
                           ps.setInt(1,cmbAcc_UnitCode);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                          
                           while(rs.next())
                           {
                           xml=xml+"<cid>"+rs.getInt("TYPE_ID")+
                           "</cid><cname>"+rs.getString("TYPE_NAME")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            
            
            else if(cmbSL_type==18)
            { 
               System.out.println("Inside pensioner Unit");
                   try {
                           ps=con.prepareStatement("select ppo_no,pensioner_name  FROM hrm_pen_mst_details WHERE process_status='VALIDATE' and payment_office_id=? order by pensioner_name ");
                           ps.setInt(1,cmbOffice_code);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                           String acc_no_plus_mode_one = "",pen_name="";
                           int pp_one=0;
                          
                           while(rs.next())
                           {
                               pp_one=rs.getInt("ppo_no");
                               pen_name=rs.getString("pensioner_name");
                               acc_no_plus_mode_one =  pen_name+ "-" +pp_one ;
                           xml=xml+"<cid>"+rs.getInt("ppo_no")+"</cid><cname>"+acc_no_plus_mode_one+"</cname>";
                           
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            else if(cmbSL_type==19)
            { 
               System.out.println("Inside family pensioner Unit");
                   try {
                           ps=con.prepareStatement("SELECT ppo_no,FPENSIONER_NAME FROM HR_PEN_MST_FAMILY WHERE process_status ='VALIDATE' AND payment_office_id=? ORDER BY FPENSIONER_NAME ");
                           ps.setInt(1,cmbOffice_code);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                           String acc_no_plus_mode_one = "",pen_name="";
                           int pp_one=0;
                          
                           while(rs.next())
                           {
                               pp_one=rs.getInt("ppo_no");
                               pen_name=rs.getString("FPENSIONER_NAME");
                               
                               acc_no_plus_mode_one =  pen_name+ "-" +pp_one ;
                           xml=xml+"<cid>"+rs.getInt("ppo_no")+"</cid><cname>"+acc_no_plus_mode_one+ "</cname>";
                           System.out.println(":::::::::::::xml::::::"+xml);
                           
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            } else if(cmbSL_type==20){
            try {
                ps =
con.prepareStatement("SELECT SUB_LEDGER_TYPE_CODE AS sl_code,SUB_LEDGER_TYPE_DESC as sl_code_desc From COM_MST_IMIS_SL_TYPE Where SUB_LEDGER_CODE = ? order by SUB_LEDGER_TYPE_CODE");


                ps.setInt(1, cmbSL_type);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
rs.getString("sl_code_desc") + "</cname>";
                    y++;
                }
                if (y != 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp code." + e);
                xml = xml + "<flag>failure</flag>";
            }

            }else if(cmbSL_type==21){
                try {
                    ps =
    con.prepareStatement("SELECT SUB_LEDGER_TYPE_CODE AS sl_code,SUB_LEDGER_TYPE_DESC as sl_code_desc From COM_MST_IMIS_SA_MIS_SL Where SUB_LEDGER_CODE = ? order by SUB_LEDGER_TYPE_CODE");


                    ps.setInt(1, cmbSL_type);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml =
    xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
    rs.getString("sl_code_desc") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }

                }else if(cmbSL_type==22){
                    try {
                        ps =
        con.prepareStatement("SELECT SUB_LEDGER_TYPE_CODE AS sl_code,SUB_LEDGER_TYPE_DESC as sl_code_desc From COM_MST_IMIS_SA_GIS_SL Where SUB_LEDGER_CODE = ? order by SUB_LEDGER_TYPE_CODE");


                        ps.setInt(1, cmbSL_type);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            xml =
        xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
        rs.getString("sl_code_desc") + "</cname>";
                            y++;
                        }
                        if (y != 0) {
                            xml = xml + "<flag>success</flag>";
                        } else {
                            xml = xml + "<flag>failure</flag>";
                        }
                        ps.close();
                        rs.close();
                    } catch (Exception e) {
                        System.out.println("catch..HERE.in load emp code." + e);
                        xml = xml + "<flag>failure</flag>";
                    }

                    }else if(cmbSL_type==23){
                        try {
                            ps =
            con.prepareStatement("SELECT SUB_LEDGER_TYPE_CODE AS sl_code,SUB_LEDGER_TYPE_DESC as sl_code_desc From COM_MST_IMIS_SA_RD_SL Where SUB_LEDGER_CODE = ? order by SUB_LEDGER_TYPE_CODE");


                            ps.setInt(1, cmbSL_type);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                xml =
            xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
            rs.getString("sl_code_desc") + "</cname>";
                                y++;
                            }
                            if (y != 0) {
                                xml = xml + "<flag>success</flag>";
                            } else {
                                xml = xml + "<flag>failure</flag>";
                            }
                            ps.close();
                            rs.close();
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." + e);
                            xml = xml + "<flag>failure</flag>";
                        }

                        }else if(cmbSL_type==24){
                            try {
                                ps =
                con.prepareStatement("SELECT SUB_LEDGER_TYPE_CODE AS sl_code,SUB_LEDGER_TYPE_DESC as sl_code_desc From COM_MST_IMIS_SA_MI_SL Where SUB_LEDGER_CODE = ? order by SUB_LEDGER_TYPE_CODE");


                                ps.setInt(1, cmbSL_type);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                    xml =
                xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
                rs.getString("sl_code_desc") + "</cname>";
                                    y++;
                                }
                                if (y != 0) {
                                    xml = xml + "<flag>success</flag>";
                                } else {
                                    xml = xml + "<flag>failure</flag>";
                                }
                                ps.close();
                                rs.close();
                            } catch (Exception e) {
                                System.out.println("catch..HERE.in load emp code." + e);
                                xml = xml + "<flag>failure</flag>";
                            }

                            }
                        else if(cmbSL_type==25){
                        	System.out.println("inside the sub ledger code pf payments");
                            try {
                                ps =
                con.prepareStatement("SELECT SUB_LEDGER_TYPE_CODE AS sl_code,SUB_LEDGER_TYPE_DESC as sl_code_desc From COM_MST_SPECIALPF_PAYMENTS Where SUB_LEDGER_CODE = ? order by SUB_LEDGER_TYPE_CODE");


                                ps.setInt(1, cmbSL_type);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                    xml =
                xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
                rs.getString("sl_code_desc") + "</cname>";
                                    y++;
                                }
                                if (y != 0) {
                                    xml = xml + "<flag>success</flag>";
                                } else {
                                    xml = xml + "<flag>failure</flag>";
                                }
                                ps.close();
                                rs.close();
                            } catch (Exception e) {
                                System.out.println("catch..HERE.in load special pf payments code." + e);
                                xml = xml + "<flag>failure</flag>";
                            }

                            }
            else if(cmbSL_type==84)
            { 
               System.out.println("Inside ooooooo Unit");
                   try {
                           ps=con.prepareStatement("select OFFICE_ID,OFFICE_NAME from com_mst_offices where OFFICE_ID=5000");
                       //    ps.setInt(1,cmbAcc_UnitCode);
                        //   System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                          
                           while(rs.next())
                           {
                           xml=xml+"<cid>"+rs.getInt("OFFICE_ID")+
                           "</cid><cname>"+rs.getString("OFFICE_NAME")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
                
            else if(cmbSL_type==89)
            { 
                try {
                    addtional_field_value =
                            Integer.parseInt(request.getParameter("addtional_field_value"));
                } catch (Exception e) {
                    System.out.println("error get addtional_field_value");
                }
               System.out.println("Inside 899999999 Unit"+addtional_field_value);
                try {
                String tt=" SELECT e.EMPLOYEE_ID,\n" + 
                "  e.EMPLOYEE_NAME\n" + 
                "  ||'.'\n" + 
                "  ||e.EMPLOYEE_INITIAL AS ENAME,\n" + 
                "  'CR'                 AS EMPLOYEE_STATUS_ID\n" + 
                " FROM HRM_MST_EMPLOYEES e\n" + 
                " WHERE e.EMPLOYEE_ID= ?\n" + 
                " ORDER BY e.EMPLOYEE_NAME ";
                System.out.println("tt::::::"+tt);
//                   ps = con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c," +
//                " HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
                    ps = con.prepareStatement(tt);
                    ps.setInt(1, addtional_field_value);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        xml =
                xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
                rs.getString("ENAME") + "</cname>";
                        xml =
                xml + "<state>" + rs.getString("EMPLOYEE_STATUS_ID") + "</state>";
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp cod in else part." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            }   
                
            else if(cmbSL_type==96)
            { 
               System.out.println("Inside Miscellaneous");
                   try {
                           ps=con.prepareStatement("SELECT TYPE_ID,trim(TYPE_NAME) AS TYPE_NAME From Fas_Sl_Types_User_Defined WHERE ACCOUNTING_UNIT_ID=? ORDER BY TYPE_ID");
                           ps.setInt(1,cmbAcc_UnitCode);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                          
                           while(rs.next())
                           {
                           xml=xml+"<cid>"+rs.getInt("TYPE_ID")+
                           "</cid><cname>"+rs.getString("TYPE_NAME")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            
            else {
                System.out.println("no match found");
                xml = xml + "<flag>failure</flag>";
            }
            System.out.println("match found for Sub-Ledger-Type");
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (strCommand.equalsIgnoreCase("Load_SL_Code_NEW"))
        {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
            int cmbSL_type = 0;
            int addtional_field_value = 0;
            int y = 0;
            xml = "<response><command>" + strCommand + "</command>";
           
            try {
                cmbSL_type =
                        Integer.parseInt(request.getParameter("cmbSL_type"));
            } catch (Exception e) {
                System.out.println("error get SL_type");
            }
            
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("error get acc unit code");
            }
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (Exception e) {
                System.out.println("error get office id");
            }
            System.out.println("SL_TYPE " + cmbSL_type + "NN");
            System.out.println(cmbAcc_UnitCode);
            System.out.println(cmbOffice_code);
            xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
            if (cmbSL_type == 7) {
                int other_dept_off_alias_id = 0, oid = 0, cmbMas_SL_type = 0;
                String deptid = "";
                System.out.println("inside 7 employeees");
                try {

                    try {
                        addtional_field_value =
                                Integer.parseInt(request.getParameter("addtional_field_value"));
                    } catch (Exception e) {
                        System.out.println("error get addtional_field_value");
                    }
                    try {
                        cmbMas_SL_type =
                                Integer.parseInt(request.getParameter("cmbMas_SL_type"));
                    } catch (Exception e) {
                        System.out.println("getting master combo sl type failed");
                    }
                    try {
                        other_dept_off_alias_id =
                                Integer.parseInt(request.getParameter("other_dept_off_alias_id"));
                    } catch (Exception e) {
                        System.out.println("getting master combo slcode failed");
                    }
                    System.out.println("other_dept_off_alias_id.." +
                                       other_dept_off_alias_id);
                    //deptid=request.getParameter("deptid");
                    System.out.println(oid + " " +
                                       deptid); // OTHER_DEPT_ALIAS_ID
                    if (cmbMas_SL_type == 9) {
                        try {
                        	String odid="select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ALIAS_ID= "+other_dept_off_alias_id;
                        	System.out.println("inside other employee:::"+odid);
                            ps =con.prepareStatement(odid);
                            //ps.setInt(1, other_dept_off_alias_id);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                deptid = rs.getString("OTHER_DEPT_ID").trim();
                                oid = rs.getInt("OTHER_DEPT_OFFICE_ID");
                            }
                            rs.close();
                            ps.close();
                         
                           String qu=" select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL ||'-'|| d.DESIGNATION as ENAME" +
                       " from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d " +
                       " where c.DESIGNATION_ID=d.DESIGNATION_ID  and c.EMPLOYEE_ID=e.EMPLOYEE_ID and c.EMPLOYEE_STATUS_ID='DPN'" +
                       " and c.DEPARTMENT_ID='" +deptid+"'"+" and c.OFFICE_ID='"+oid+"'"+
                       " and e.EMPLOYEE_ID='"+addtional_field_value+"'";
                          System.out.println("qu:::"+qu);
                           ps =con.prepareStatement(qu);
//                            ps.setInt(1, oid);
//                            ps.setString(2, deptid);
//                            ps.setInt(3,addtional_field_value); // later modified
                            rs = ps.executeQuery();

                            if (rs.next()) {
                                xml = xml + "<flag>success</flag>";
                                xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>"+ rs.getString("ENAME") + "</cname><depid>"+rs.getString("DEPARTMENT_ID");

                            } else {
                                xml = xml + "<flag>failure</flag>";
                            }
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }

                    }

                    else {
                        try {
                        System.out.println("::::::addtional_field_value:::::"+addtional_field_value);
                            // ps=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and e.OFFICE_ID=? and e.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
                            //ps.setInt(1,cmbOffice_code);
                            ps =  con.prepareStatement("SELECT e.EMPLOYEE_ID,\n" + 
                            "  e.EMPLOYEE_NAME\n" + 
                            "  ||'.'\n" + 
                            "  ||e.EMPLOYEE_INITIAL AS ENAME,\n" + 
                            "  'CR'                 AS EMPLOYEE_STATUS_ID\n" + 
                            " From Hrm_Mst_Employees E\n" + 
                            " Where E.Employee_Id= ?\n" + 
                            " ORDER BY e.EMPLOYEE_NAME ");
  /*
   ps =  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c," +
   " HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
   */
                            ps.setInt(1, addtional_field_value);
                            rs = ps.executeQuery();

                            if (rs.next()) {
                                xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
   rs.getString("ENAME") + "</cname>";
                                xml =
 xml + "<state>" + rs.getString("EMPLOYEE_STATUS_ID") + "</state>";
                                xml = xml + "<flag>success</flag>";
                            } else
                                xml = xml + "<flag>failure</flag>";

                            ps.close();
                            rs.close();
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp cod in else part." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } catch (Exception e) {
                    System.out.println("catch..HERE.in getting request value code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }

            } else if (cmbSL_type == 9) {
                System.out.println("inside 9 OTHER Departments");

                try {
                    ps =
  con.prepareStatement("select dep.OTHER_DEPT_NAME || '-' || off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFF_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                       ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ORDER BY dep.OTHER_DEPT_NAME");
                    rs = ps.executeQuery();

                    while (rs.next()) {

                        xml =
 xml + "<cid>" + rs.getInt("OTHER_DEPT_OFFICE_ALIAS_ID") + "</cid><cname>" +
   rs.getString("OTHER_DEPT_OFF_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load department code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } 

            
            
            else {
                System.out.println("no match found");
                xml = xml + "<flag>failure</flag>";
            }
            System.out.println("match found for Sub-Ledger-Type");
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        
        }/// joan Added on 29 jAn 2015
        
        else if(strCommand.equalsIgnoreCase("check_DCB_Frz")){
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            Date txtCrea_date = null;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0;
            System.out.println("check_TB if condi");
            xml = "<response><command>check_TB</command>";

            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            String[] sd =
                request.getParameter("TB_date").split("/"); // *** seee here getting TB_date date not " txtCrea_date " ***
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("TB_date " + txtCrea_date);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date = request.getParameter("TB_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);
            int check_financeyear = Integer.parseInt(ym[2]);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            System.out.println("check financ year is " + check_financeyear);

            try {
                if (check_financeyear == 0) {
                    xml =
 "<response><command>check_TB</command><flag>finyear</flag></response>"; // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date
                    out.println(xml);
                    return;
                }

                System.out.println("checking.." + txtCash_year);
                System.out.println("checking.." + txtCash_Month_hid);
                ps =
  con.prepareStatement("select DCB_FREEZE from PMS_DCB_DATA_FREEZE where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                //ps.setInt(2,cmbOffice_code);
                ps.setInt(2, txtCash_year);
                ps.setInt(3, txtCash_Month_hid);
                rs = ps.executeQuery();
                //System.out.println(rs.next());
                if (rs.next()) {
                    if (rs.getString("TB_STATUS").equalsIgnoreCase("N"))
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println("catch..HERE.in TB_date " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        else if (strCommand.equalsIgnoreCase("check_TB")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            Date txtCrea_date = null;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0;
            System.out.println("check_TB if condi");
            xml = "<response><command>check_TB</command>";

            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            String[] sd =
                request.getParameter("TB_date").split("/"); // *** seee here getting TB_date date not " txtCrea_date " ***
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("TB_date " + txtCrea_date);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date = request.getParameter("TB_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);
            int check_financeyear = Integer.parseInt(ym[2]);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            System.out.println("check financ year is " + check_financeyear);

            try {
                if (check_financeyear == 0) {
                    xml =
 "<response><command>check_TB</command><flag>finyear</flag></response>"; // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date
                    out.println(xml);
                    return;
                }

                System.out.println("checking.." + txtCash_year);
                System.out.println("checking.." + txtCash_Month_hid);
                ps =
  con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                //ps.setInt(2,cmbOffice_code);
                ps.setInt(2, txtCash_year);
                ps.setInt(3, txtCash_Month_hid);
                rs = ps.executeQuery();
                //System.out.println(rs.next());
                if (rs.next()) {
                    if (rs.getString("TB_STATUS").equalsIgnoreCase("N"))
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println("catch..HERE.in TB_date " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    }
}

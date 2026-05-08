package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Calendar;
//import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CivilBills.servlets.BillTypeMasterImpl;


//import Servlets.Security.classes.UserProfile;


public class sanction_proceed_masterNew extends HttpServlet
{
  //private static final String CONTENT_TYPE="text/xml; charset=windows-1252";
    public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
        public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
            {        	
		String CONTENT_TYPE="text/xml;charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                System.out.println("Welcome to Sanction_Proceeed Servlet");
		String cmnd="";String xml="";
                int count=0;
                PrintWriter pw=response.getWriter();
                //PrintWriter out = response.getWriter();
                HttpSession session=request.getSession(false);
                String update_user=(String)session.getAttribute("UserId");
/*********************************************** connection establishment**************************************************************/
                Connection con=null;
                ResultSet rs=null;
                PreparedStatement ps=null;
                      xml="<response>";
                        try
                            {
                                ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                String ConnectionString="";
                                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                String strdsn=rs1.getString("Config.DSN");
                                String strhostname=rs1.getString("Config.HOST_NAME");
                                String strportno=rs1.getString("Config.PORT_NUMBER");
                                String strsid=rs1.getString("Config.SID");
                                String strdbusername=rs1.getString("Config.USER_NAME");
                                String strdbpassword=rs1.getString("Config.PASSWORD");
                                                 
                                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                    Class.forName(strDriver.trim());
                                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                            }
                        catch(Exception e)
                            {
                                System.out.println("Exception in connection...."+e);
                            } 
                        try
                        {
                              session=request.getSession(false);
                              if(session==null)
                                {
                                      System.out.println(request.getContextPath()+"/index.jsp");
                                      response.sendRedirect(request.getContextPath()+"/index.jsp");
                                      return;
                                }
                              System.out.println(session);
                        } 
                        catch(Exception e)
                        {
                                System.out.println("Redirect Error :"+e);
                        }
                        try
                        {
                          cmnd=request.getParameter("command");     
                          System.out.println("Command passed via the button pressed : " +cmnd);
                        }
                        catch(Exception e3)
                        {
                            e3.printStackTrace();
                        }
                    if(cmnd.equalsIgnoreCase("loadMajorType"))
                    {
                           xml=xml+"<command>loadMajorType</command>";
                            try
                            {
                                    ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
                                    rs=ps.executeQuery();
                                    while(rs.next())
                                    {
                                            xml=xml+"<option><id>"+rs.getInt("BILL_MAJOR_TYPE_CODE")+"</id><desc>"+rs.getString("BILL_MAJOR_TYPE_DESC")+"</desc></option>";
                                    }
                                    count++;
                                    if(count>0) 
                                    {
                                            xml=xml+"<flag>success</flag>";    
                                    }
                                    else
                                    {
                                            xml=xml+"<flag>nodata</flag>";
                                    }
                                    rs.close();
                                    ps.close();
                            }
                            catch(Exception e)
                            {
                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                    System.out.println(e);
                            }                    
                    }
                    else if(cmnd.equalsIgnoreCase("loadMinorType"))
                    {
                        xml=xml+"<command>loadMinorType</command>";
                    try
                    {
                        int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                        System.out.println("major code selected:"+strmajor);
                        String sql="select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and status='L' order by BILL_MINOR_TYPE_CODE";
                        ps=con.prepareStatement(sql);
                        ps.setInt(1,strmajor);
                        rs=ps.executeQuery();
                        while(rs.next())
                        {
                            xml=xml+"<option><desc>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</desc><id>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</id></option>";
                            count++;
                        } // while close
                        if(count>0)
                            xml=xml+"<flag>success</flag>";
                        else
                            xml=xml+"<flag>nodata</flag>";
                                               
                            ps.close();
                            rs.close();
                    } //try close
                    catch(Exception e)
                    {
                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                        System.out.println(e);
                    }
                }
                else if(cmnd.equalsIgnoreCase("loadSubType"))
                {
                    xml=xml+"<command>loadSubType</command>";
                    try
                    {
                        int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                        System.out.println("major code selected:"+strmajor);
                        int strminor=Integer.parseInt(request.getParameter("MinorCode1"));
                        System.out.println("minor code selected:"+strminor);
                        
                        String sql="select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES  where BILL_MAJOR_TYPE_CODE=? " +
                        "AND BILL_MINOR_TYPE_CODE=? and status='L' order by BILL_SUB_TYPE_CODE";
                        ps=con.prepareStatement(sql);
                        ps.setInt(1,strmajor);
                        ps.setInt(2,strminor);
                        rs=ps.executeQuery();
                        while(rs.next())
                        {
                            xml=xml+"<option><desc>"+rs.getString("BILL_SUB_TYPE_DESC")+"</desc><id>"+rs.getInt("BILL_SUB_TYPE_CODE")+"</id></option>";
                            count++;
                        } // while close
                        System.out.println("count"+count);
                        if(count>0)
                            xml=xml+"<flag>success</flag>";
                        else if(count==0)
                         {
                                xml=xml+"<flag>nodata</flag>";
                         }
                        ps.close();
                        rs.close();
                        } //try close
                        catch(Exception e)
                        {
                            xml=xml+"<flag>"+e.getMessage()+"</flag>";
                            System.out.println(e);
                        }
                    }
                    else if(cmnd.equalsIgnoreCase("loadpaymenttype"))
                    {
                        xml=xml+"<command>loadpaymenttype</command>";
                        try
                        {
                            int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                            System.out.println("major code selected:"+strmajor);
                            int strminor=Integer.parseInt(request.getParameter("MinorCode1"));
                            System.out.println("minor code selected:"+strminor);
                            
                            String sql="select ADVANCE_APPLICABLE from ADVANCE_APPLICABLE_MASTER where bill_major_type_code=? and bill_minor_type_code=?";
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,strmajor);
                            ps.setInt(2,strminor);
                            rs=ps.executeQuery();
                            if(rs.next())
                            {
                                xml=xml+"<advance_applicable>"+rs.getString("ADVANCE_APPLICABLE")+"</advance_applicable>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                            } //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("loadsanctionauth"))
                    {
                        xml=xml+"<command>loadsanctionauth</command>";
                        try
                        {
                            String sql="select DISTINCT DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS order by DESIGNATION_ID";
                            ps=con.prepareStatement(sql);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<option><desig_id>"+rs.getInt("DESIGNATION_ID")+"</desig_id><desig_name>"+rs.getString("DESIGNATION")+"</desig_name></option>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                            } //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("Loadsanctioned_by"))
                    {
                        xml=xml+"<command>Loadsanctioned_by</command>";
                        try
                        {
                            //int acc_unit_id=Integer.parseInt(request.getParameter("acc_unit_id"));
                            int acc_unit_off_id=Integer.parseInt(request.getParameter("acc_unit_off_id"));
                            int desig_id=Integer.parseInt(request.getParameter("desig_sel_code"));
                            String desig_name=request.getParameter("desig_sel_desc");
                            System.out.println("Office id   : "+acc_unit_off_id);
                            System.out.println("Designation Selected  : "+desig_name);
                            
                            String sql="select EMPLOYEE_ID from HRM_EMP_CURRENT_POSTING where DESIGNATION_ID=? and OFFICE_ID=? and EMPLOYEE_STATUS_ID='WKG'"; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,desig_id);
                            ps.setInt(2,acc_unit_off_id);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<option><employee_id>"+rs.getInt("EMPLOYEE_ID")+"</employee_id></option>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("Loadsanctiondetails"))
                    {
                        xml=xml+"<command>Loadsanctiondetails</command>";
                        try
                        {
                            //int acc_unit_id=Integer.parseInt(request.getParameter("acc_unit_id"));
                            int acc_unit_off_id=Integer.parseInt(request.getParameter("acc_unit_off_id"));
                            int emp_id=Integer.parseInt(request.getParameter("emp_code_sel"));
                            System.out.println("Office id   : "+acc_unit_off_id);
                            System.out.println("Employee id : "+emp_id);
                           
                     
                            String sql="select a.EMPLOYEE_ID,b.employee_name, c.OFFICE_SHORT_NAME,d.DESIGNATION,d.DESIGNATION_ID from " +
                            "   ( " +
                            "   select EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=? and OFFICE_ID=? and EMPLOYEE_STATUS_ID='WKG' " +
                            "   )a left outer join "+
                            "   ( select  EMPLOYEE_ID,EMPLOYEE_INITIAL||' '||EMPLOYEE_NAME as employee_name from HRM_MST_EMPLOYEES "+
                            "    )b on a.EMPLOYEE_ID=b.EMPLOYEE_ID left outer join "+
                            "   (  "+
                            "    select OFFICE_ID,OFFICE_SHORT_NAME from COM_MST_OFFICES "+
                            "    )c on a.OFFICE_ID=c.OFFICE_ID left outer join "+
                            "    ( "+
                            "    select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS "+
                            "   )d on a.DESIGNATION_ID=d.DESIGNATION_ID "; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,emp_id);
                            ps.setInt(2,acc_unit_off_id);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<empl_name>"+rs.getString("employee_name")+"</empl_name>";
                                xml=xml+"<office_name>"+rs.getString("OFFICE_SHORT_NAME")+"</office_name>";
                                xml=xml+"<desig_name>"+rs.getString("DESIGNATION")+"</desig_name>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    else if(cmnd.equalsIgnoreCase("loadInvoiceNumber"))
                    {
                        xml=xml+"<command>loadInvoiceNumber</command>";
                        try
                        {
                          
                            int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                            System.out.println("major code selected:"+strmajor);
                            int strminor=Integer.parseInt(request.getParameter("MinorCode1"));
                            System.out.println("minor code selected:"+strminor);
                            int strsub=Integer.parseInt(request.getParameter("SubCode1"));
                            System.out.println("Sub code selected:"+strsub);
                            
                            String sql="select INVOICE_NO from INVOICE_TMP where BILL_MAJOR_CODE=? and BILL_MINOR_CODE=? and BILL_SUB_CODE=? order by INVOICE_NO "; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,strmajor);
                            ps.setInt(2,strminor);
                            ps.setInt(3,strsub);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<option><invoice_no>"+rs.getInt("INVOICE_NO")+"</invoice_no></option>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }else if(cmnd.equalsIgnoreCase("budgetDetails")){
                        xml=xml+"<command>budgetDetails</command>";
                        try{                          
                            int accUnit=Integer.parseInt(request.getParameter("accUnit"));
                            int accHead=Integer.parseInt(request.getParameter("accHead"));
                            String finaceYear=request.getParameter("finaceYear");
                            
                            String sql="SELECT CURRENT_YEAR_BUDGET_ESTIMATE, " +
                            "  BUDGET_SOFAR_SPENT " +
                            "FROM COM_BUDGET_DETAILS " +
                            "WHERE ACCOUNTING_UNIT_ID=? " +
                            "AND ACCOUNT_HEAD_CODE   =? " +
                            "AND FINANCIAL_YEAR      =?"; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,accUnit);
                            ps.setInt(2,accHead);
                            ps.setString(3,finaceYear);
                            rs=ps.executeQuery();
                            if(rs.next()){
                            	int balance = rs.getInt("CURRENT_YEAR_BUDGET_ESTIMATE")-rs.getInt("BUDGET_SOFAR_SPENT");
                                xml+="<BUDGET_PROVIDE>"+rs.getInt("CURRENT_YEAR_BUDGET_ESTIMATE")+"</BUDGET_PROVIDE>" +
                                	  "<BUDGET_SPENT>"+rs.getInt("BUDGET_SOFAR_SPENT")+"</BUDGET_SPENT>" +
                                	  "<BALANCE>"+balance+"</BALANCE>";
                                xml=xml+"<flag>success</flag>";
                            }else{
                                    xml=xml+"<flag>nodata</flag>";
                            }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }else if(cmnd.equalsIgnoreCase("sanctionList")){
                    	xml += "<command>sanctionList</command>";
                    	String sql = "";
                    	count = 0;
                    	try {
                    		sql = "SELECT SANCTION_PROC_NO, " +
                    		"  TO_CHAR(SANCTION_PROC_DATE,'dd/MM/yyyy') AS SANCTION_PROC_DATE, " +
                    		"  PAYEE_TYPE, " +
                    		"  PAYEE_CODE, " +
                    		"  SANCTION_BY, " +
                    		"  REMARKS, " +
                    		"  ACCOUNT_HEAD_CODE, " +
                    		"  BUDGET_PROVIDED, " +
                    		"  SUDGET_SOFAR_SPENT, " +
                    		"  BALANCE_AMOUNT, " +
                    		"  STATUS " +
                    		"FROM FAS_SANC_PROCEEDING_MST_NEW " +
                    		"ORDER BY SANCTION_PROC_NO";
							ps = con.prepareStatement(sql);
							rs = ps.executeQuery();
							while(rs.next()){
								xml +="<SANCTION_PROC_NO>"+rs.getInt("SANCTION_PROC_NO")+"</SANCTION_PROC_NO>" +
									  "<SANCTION_PROC_DATE>"+rs.getString("SANCTION_PROC_DATE")+"</SANCTION_PROC_DATE>" +
									  "<PAYEE_TYPE>"+rs.getString("PAYEE_TYPE")+"</PAYEE_TYPE>" +
									  "<PAYEE_CODE>"+rs.getInt("PAYEE_CODE")+"</PAYEE_CODE>" +
									  "<SANCTION_BY>"+rs.getInt("SANCTION_BY")+"</SANCTION_BY>" +
									  "<REMARKS>"+rs.getString("REMARKS")+"</REMARKS>" +
									  "<ACCOUNT_HEAD_CODE>"+rs.getDouble("ACCOUNT_HEAD_CODE")+"</ACCOUNT_HEAD_CODE>" +
									  "<BUDGET_PROVIDED>"+rs.getDouble("BUDGET_PROVIDED")+"</BUDGET_PROVIDED>" +
									  "<BUDGET_SOFAR_SPENT>"+rs.getDouble("SUDGET_SOFAR_SPENT")+"</BUDGET_SOFAR_SPENT>" +
									  "<BALANCE_AMOUNT>"+rs.getDouble("BALANCE_AMOUNT")+"</BALANCE_AMOUNT>" +
									  "<STATUSTYPE>"+rs.getString("STATUS")+"</STATUSTYPE>" +
								count++;
							}
							if(count==0){
								xml +="<status>fail</status>";
							}else{
								xml +="<status>success</status>";
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							xml +="<command>sanctionList</command><status>fail</status>";
							e.printStackTrace();
						}

                    }else if(cmnd.equalsIgnoreCase("edit")){   			
               			int sancNo = Integer.parseInt(request.getParameter("sancNo"));
               			String sql = "";
               			try {
               				sql = "SELECT aa.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, " +
               				"  ACCOUNTING_UNIT_OFFICE_ID  AS ACCOUNTING_UNIT_OFFICE_ID, " +
               				"  aa.CASHBOOK_YEAR           AS CASHBOOK_YEAR, " +
               				"  aa.CASHBOOK_MONTH          AS CASHBOOK_MONTH, " +
               				"  aa.SANCTION_PROC_NO        AS SANCTION_PROC_NO, " +
               				"  aa.SANCTION_PROC_DATE      AS SANCTION_PROC_DATE, " +
               				"  aa.BILL_MAJOR_TYPE_CODE    AS BILL_MAJOR_TYPE_CODE, " +
               				"  aa.BILL_MINOR_TYPE_CODE    AS BILL_MINOR_TYPE_CODE, " +
               				"  aa.BILL_SUB_TYPE_CODE      AS BILL_SUB_TYPE_CODE, " +
               				"  aa.PAYEE_TYPE              AS PAYEE_TYPE, " +
               				"  aa.PAYMENT_TYPE            AS PAYMENT_TYPE, " +
               				"  aa.PAYEE_CODE              AS PAYEE_CODE, " +
               				"  aa.SANCTION_AUTHORITY      AS SANCTION_AUTHORITY, " +
               				"  aa.SANCTION_BY             AS SANCTION_BY, " +
               				"  aa.REMARKS                 AS REMARKS, " +
               				"  aa.REF_NO                  AS REF_NO, " +
               				"  aa.REF_DATE                AS REF_DATE, " +
               				"  aa.ACCOUNT_HEAD_CODE       AS ACCOUNT_HEAD_CODE, " +
               				"  aa.BUDGET_PROVIDED         AS BUDGET_PROVIDED, " +
               				"  aa.SUDGET_SOFAR_SPENT      AS SUDGET_SOFAR_SPENT, " +
               				"  aa.BALANCE_AMOUNT          AS BALANCE_AMOUNT, " +
               				"  aa.TRF_ACCOUNTING_UNIT     AS TRF_ACCOUNTING_UNIT, " +
               				"  aa.RECOVERY_FROM           AS RECOVERY_FROM, " +
               				"  aa.TOTAL_INSTALMENTS       AS TOTAL_INSTALMENTS, " +
               				"  aa.EMI                     AS EMI, " +
               				"  aa.EMI_START_MONTH         AS EMI_START_MONTH, " +
               				"  aa.RESIDUAL_AMOUNT         AS RESIDUAL_AMOUNT, " +
               				"  aa.RESIDUAL_NUMBER         AS RESIDUAL_NUMBER, " +
               				"  aa.TOTAL_SANCTIONED_AMOUNT AS TOTAL_SANCTIONED_AMOUNT, " +
               				"  aa.STATUS                  AS STATUS, " +
               				"  bb.BILL_MINOR_TYPE_DESC    AS BILL_MINOR_TYPE_DESC, " +
               				"  cc.BILL_SUB_TYPE_DESC      AS BILL_SUB_TYPE_DESC " +
               				"FROM " +
               				"  (SELECT ACCOUNTING_UNIT_ID, " +
               				"    ACCOUNTING_UNIT_OFFICE_ID, " +
               				"    CASHBOOK_YEAR, " +
               				"    CASHBOOK_MONTH, " +
               				"    SANCTION_PROC_NO, " +
               				"    TO_CHAR(SANCTION_PROC_DATE,'dd/MM/yyyy') AS SANCTION_PROC_DATE, " +
               				"    BILL_MAJOR_TYPE_CODE, " +
               				"    BILL_MINOR_TYPE_CODE, " +
               				"    BILL_SUB_TYPE_CODE, " +
               				"    PAYEE_TYPE, " +
               				"    PAYMENT_TYPE, " +
               				"    PAYEE_CODE, " +
               				"    SANCTION_AUTHORITY, " +
               				"    SANCTION_BY, " +
               				"    REMARKS, " +
               				"    REF_NO, " +
               				"    TO_CHAR(REF_DATE,'dd/MM/yyyy') AS REF_DATE, " +
               				"    ACCOUNT_HEAD_CODE, " +
               				"    BUDGET_PROVIDED, " +
               				"    SUDGET_SOFAR_SPENT, " +
               				"    BALANCE_AMOUNT, " +
               				"    TRF_ACCOUNTING_UNIT, " +
               				"    RECOVERY_FROM, " +
               				"    TOTAL_INSTALMENTS, " +
               				"    EMI, " +
               				"    TO_CHAR(EMI_START_MONTH,'dd/MM/yyyy') AS EMI_START_MONTH, " +
               				"    RESIDUAL_AMOUNT, " +
               				"    RESIDUAL_NUMBER, " +
               				"    TOTAL_SANCTIONED_AMOUNT, " +
               				"    STATUS " +
               				"  FROM FAS_SANC_PROCEEDING_MST_NEW " +
               				"  WHERE SANCTION_PROC_NO=? " +
               				"  )aa " +
               				"LEFT OUTER JOIN " +
               				"  (SELECT BILL_MAJOR_TYPE_CODE, " +
               				"    BILL_MINOR_TYPE_CODE, " +
               				"    BILL_MINOR_TYPE_DESC " +
               				"  FROM FAS_BILL_MINOR_TYPES_MST " +
               				"  )bb " +
               				"ON aa.BILL_MAJOR_TYPE_CODE =bb.BILL_MAJOR_TYPE_CODE " +
               				"AND aa.BILL_MINOR_TYPE_CODE=bb.BILL_MINOR_TYPE_CODE " +
               				"LEFT OUTER JOIN " +
               				"  (SELECT BILL_MAJOR_TYPE_CODE, " +
               				"    BILL_MINOR_TYPE_CODE, " +
               				"    BILL_SUB_TYPE_CODE, " +
               				"    BILL_SUB_TYPE_DESC " +
               				"  FROM FAS_BILL_SUB_TYPES " +
               				"  )cc " +
               				"ON aa.BILL_MAJOR_TYPE_CODE =cc.BILL_MAJOR_TYPE_CODE " +
               				"AND aa.BILL_SUB_TYPE_CODE  =cc.BILL_SUB_TYPE_CODE " +
               				"AND bb.BILL_MINOR_TYPE_CODE=cc.BILL_MINOR_TYPE_CODE";
               				ps = con.prepareStatement(sql);
               				ps.setInt(1, sancNo);
               				rs = ps.executeQuery();
               				if(rs.next()){
               					xml +="<status>success</status>" +
               						  "<ACCOUNTING_UNIT_ID>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>" +
               						  "<ACCOUNTING_UNIT_OFFICE_ID>"+rs.getInt("ACCOUNTING_UNIT_OFFICE_ID")+"</ACCOUNTING_UNIT_OFFICE_ID>" +
               						  "<CASHBOOK_YEAR>"+rs.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>" +
               						  "<CASHBOOK_MONTH>"+rs.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>" +
               						  "<SANCTION_PROC_NO>"+rs.getInt("SANCTION_PROC_NO")+"</SANCTION_PROC_NO>" +
	               					  "<SANCTION_PROC_DATE>"+rs.getString("SANCTION_PROC_DATE")+"</SANCTION_PROC_DATE>" +
	               					  "<BILL_MAJOR_TYPE_CODE>"+rs.getInt("BILL_MAJOR_TYPE_CODE")+"</BILL_MAJOR_TYPE_CODE>" +
	               					  "<BILL_MINOR_TYPE_CODE>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</BILL_MINOR_TYPE_CODE>" +
	               					  "<BILL_SUB_TYPE_CODE>"+rs.getInt("BILL_SUB_TYPE_CODE")+"</BILL_SUB_TYPE_CODE>" +
	               					  "<PAYEE_TYPE>"+rs.getString("PAYEE_TYPE")+"</PAYEE_TYPE>" +
	               					  "<PAYMENT_TYPE>"+rs.getString("PAYMENT_TYPE")+"</PAYMENT_TYPE>" +
	               					  "<PAYEE_CODE>"+rs.getInt("PAYEE_CODE")+"</PAYEE_CODE>" +
	               					  "<SANCTION_AUTHORITY>"+rs.getInt("SANCTION_AUTHORITY")+"</SANCTION_AUTHORITY>" +
	               					  "<SANCTION_BY>"+rs.getInt("SANCTION_BY")+"</SANCTION_BY>" +	               					  
	               					  "<REMARKS>"+rs.getString("REMARKS")+"</REMARKS>" +
	               					  "<REF_NO>"+rs.getInt("REF_NO")+"</REF_NO>" +
	               					  "<REF_DATE>"+rs.getString("REF_DATE")+"</REF_DATE>" +
	               					  "<ACCOUNT_HEAD_CODE>"+rs.getInt("ACCOUNT_HEAD_CODE")+"</ACCOUNT_HEAD_CODE>" +
	               				      "<BUDGET_PROVIDED>"+rs.getInt("BUDGET_PROVIDED")+"</BUDGET_PROVIDED>" +
	               					  "<BUDGET_SOFAR_SPENT>"+rs.getInt("SUDGET_SOFAR_SPENT")+"</BUDGET_SOFAR_SPENT>" +
	               					  "<BALANCE_AMOUNT>"+rs.getInt("BALANCE_AMOUNT")+"</BALANCE_AMOUNT>" +
	               					  "<TRF_ACCOUNTING_UNIT>"+rs.getInt("TRF_ACCOUNTING_UNIT")+"</TRF_ACCOUNTING_UNIT>" +
	               					  "<RECOVERY_FROM>"+rs.getString("RECOVERY_FROM")+"</RECOVERY_FROM>" +
	               					  "<TOTAL_INSTALMENTS>"+rs.getInt("TOTAL_INSTALMENTS")+"</TOTAL_INSTALMENTS>" +
	               					  "<EMI>"+rs.getInt("EMI")+"</EMI>" +
	               					  "<EMI_START_MONTH>"+rs.getString("EMI_START_MONTH")+"</EMI_START_MONTH>" +
	               					  "<RESIDUAL_AMOUNT>"+rs.getInt("RESIDUAL_AMOUNT")+"</RESIDUAL_AMOUNT>" +
	               					  "<RESIDUAL_NUMBER>"+rs.getInt("RESIDUAL_NUMBER")+"</RESIDUAL_NUMBER>" +
	               					  "<TOTAL_SANCTIONED_AMOUNT>"+rs.getInt("TOTAL_SANCTIONED_AMOUNT")+"</TOTAL_SANCTIONED_AMOUNT>" +
	               					  "<STATUS>"+rs.getString("STATUS")+"</STATUS>" +
	               					  "<BILL_MINOR_TYPE_DESC>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</BILL_MINOR_TYPE_DESC>" +
	               					  "<BILL_SUB_TYPE_DESC>"+rs.getString("BILL_SUB_TYPE_DESC")+"</BILL_SUB_TYPE_DESC>";
               				}else{
               					xml +="<status>fail</status>";
               				}
            			} catch (SQLException e) {
            				// TODO Auto-generated catch block
            				xml +="<status>fail</status>";
            				e.printStackTrace();
            			}
               		}
                    else if(cmnd.equalsIgnoreCase("loadInvoiceDetails"))
                    {
                        xml=xml+"<command>loadInvoiceDetails</command>";
                        try
                        {
                            int invoice_no=Integer.parseInt(request.getParameter("invoice_no"));
                            System.out.println("Invoice number selected :"+invoice_no);
                           String sql="    select a.INVOICE_NO,a.INVOICE_DATE,a.INVOICE_AMOUNT,a.PARTICULARS_INVOICE,a.HEAD_ACCOUNT,b.ACCOUNT_HEAD_DESC from    "   +
                                      "    (                                                                                                                    "   +
                                      "         select INVOICE_NO,to_char(INVOICE_DATE,'dd/mm/yyyy')as INVOICE_DATE,                                            "   +
                                      "         INVOICE_AMOUNT,PARTICULARS_INVOICE,HEAD_ACCOUNT from INVOICE_TMP where INVOICE_NO=?                             "   +
                                      "    )a                                                                                                                   "   +
                                      "      left outer join                                                                                                    "   +                                        
                                      "      (                                                                                                                  "   +
                                      "          select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y'                   "   +
                                      "      )b                                                                                                                 "   +
                                      "          on a.head_account=b.ACCOUNT_HEAD_CODE"; 
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,invoice_no);
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                xml=xml+"<invoice_no>"+rs.getInt("INVOICE_NO")+"</invoice_no>";
                                xml=xml+"<invoice_date>"+rs.getString("INVOICE_DATE")+"</invoice_date>";
                                xml=xml+"<invoice_amount>"+rs.getInt("INVOICE_AMOUNT")+"</invoice_amount>";
                                xml=xml+"<invoice_particulars>"+rs.getString("PARTICULARS_INVOICE")+"</invoice_particulars>";
                                xml=xml+"<invoice_headaccount>"+rs.getString("HEAD_ACCOUNT")+"</invoice_headaccount>";
                                xml=xml+"<invoice_head_desc>"+rs.getString("ACCOUNT_HEAD_DESC")+"</invoice_head_desc>";
                                count++;
                            } // while close
                            System.out.println("count"+count);
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else if(count==0)
                             {
                                    xml=xml+"<flag>nodata</flag>";
                             }
                            ps.close();
                            rs.close();
                        }   //try close
                            catch(Exception e)
                            {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }else if(cmnd.equalsIgnoreCase("delete")){
                        xml=xml+"<command>delete</command>";
                        count=0;
                        try{
                           int unitCode=Integer.parseInt(request.getParameter("unitCode"));
                           int officeCode=Integer.parseInt(request.getParameter("officeCode"));
                           int sancNo=Integer.parseInt(request.getParameter("sancNo"));
                           String sql="UPDATE FAS_SANC_PROCEEDING_MST_NEW SET STATUS='C'" +                           
                           "WHERE ACCOUNTING_UNIT_ID      = ? " +
                           "AND ACCOUNTING_UNIT_OFFICE_ID = ? " +
                           "AND SANCTION_PROC_NO          = ?";
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,unitCode);
                            ps.setInt(2, officeCode);
                            ps.setInt(3, sancNo);
                            count = ps.executeUpdate();                            
                            System.out.println("count"+count);
                            if(count>0){
                            	xml=xml+"<flag>success</flag>";
                            }else if(count==0){
                                    xml=xml+"<flag>nodata</flag>";
                            }
                            ps.close();                            
                        }catch(Exception e){
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }else if(cmnd.equalsIgnoreCase("update")){
                        xml=xml+"<command>update</command>";
                        count=0;
                        BillTypeMasterImpl dat = new BillTypeMasterImpl(); 
                        try{
                           int unitCode=Integer.parseInt(request.getParameter("unitCode"));
                           int officeCode=Integer.parseInt(request.getParameter("officeCode"));
                           int sancNo=Integer.parseInt(request.getParameter("sancNo"));
                           int billMajor=Integer.parseInt(request.getParameter("billMajor"));
                           int billMinor=Integer.parseInt(request.getParameter("billMinor"));
                           int billSub=Integer.parseInt(request.getParameter("billSub"));
                           String payment=request.getParameter("payment");
                           String pay=request.getParameter("pay");
                           int payee_code=Integer.parseInt(request.getParameter("payee_code"));
                           int refNo=Integer.parseInt(request.getParameter("refNo"));
                           Date refDate=dat.date_convertion(request.getParameter("refDate"));
                           Date sancpro_date=dat.date_convertion(request.getParameter("sancpro_date"));
                           String sanc_auth=request.getParameter("sanc_auth");
                           String sanc_by=request.getParameter("sanc_by");
                           long acc_HeadCode = Long.parseLong(request.getParameter("acc_HeadCode"));
                           long budget_Provided = Long.parseLong(request.getParameter("budget_Provided"));
                           long budget_sofar_spent = Long.parseLong(request.getParameter("budget_sofar_spent"));
                           long bal_amt = Long.parseLong(request.getParameter("bal_amt"));
                           int txtMade = Integer.parseInt(request.getParameter("txtMade"));
                           String recovery=request.getParameter("recovery");
                           int tot_instalments = Integer.parseInt(request.getParameter("tot_instalments"));
                           int txt_EMI = Integer.parseInt(request.getParameter("txt_EMI"));
                           Date start_month=dat.date_convertion(request.getParameter("start_month"));
                           int resi_amt = Integer.parseInt(request.getParameter("resi_amt"));
                           int deduction_No = Integer.parseInt(request.getParameter("deduction_No"));
                           int tot_sanctionamt = Integer.parseInt(request.getParameter("tot_sanctionamt"));
                           String remarks=request.getParameter("remarks");
                           
                           String sql="UPDATE FAS_SANC_PROCEEDING_MST_NEW " +
                           "SET SANCTION_PROC_DATE        = ?, " +
                           "  BILL_MAJOR_TYPE_CODE        = ?, " +
                           "  BILL_MINOR_TYPE_CODE        = ?, " +
                           "  BILL_SUB_TYPE_CODE          = ?, " +
                           "  PAYEE_TYPE                  = ?, " +
                           "  PAYMENT_TYPE                = ?, " +
                           "  PAYEE_CODE                  = ?, " +
                           "  SANCTION_AUTHORITY          = ?, " +
                           "  SANCTION_BY                 = ?, " +
                           "  REMARKS                     = ?, " +
                           "  UPDATED_BY_USER_ID          = ?, " +
                           "  UPDATED_DATE                = SYSTIMESTAMP, " +
                           "  REF_NO                      = ?, " +
                           "  REF_DATE                    = ?, " +
                           "  ACCOUNT_HEAD_CODE           = ?, " +
                           "  BUDGET_PROVIDED             = ?, " +
                           "  SUDGET_SOFAR_SPENT          = ?, " +
                           "  BALANCE_AMOUNT              = ?, " +
                           "  TRF_ACCOUNTING_UNIT         = ?, " +
                           "  RECOVERY_FROM               = ?, " +
                           "  TOTAL_INSTALMENTS           = ?, " +
                           "  EMI                         = ?, " +
                           "  EMI_START_MONTH             = ?, " +
                           "  RESIDUAL_AMOUNT             = ?, " +
                           "  RESIDUAL_NUMBER             = ?, " +
                           "  TOTAL_SANCTIONED_AMOUNT     = ? " +
                           "WHERE ACCOUNTING_UNIT_ID      = ? " +
                           "AND ACCOUNTING_UNIT_OFFICE_ID = ? " +
                           "AND SANCTION_PROC_NO          = ?";
                            ps=con.prepareStatement(sql);
                            ps.setDate(1,sancpro_date);
                            ps.setInt(2,billMajor);
                            ps.setInt(3,billMinor);
                            ps.setInt(4,billSub);
                            ps.setString(5,pay);
                            ps.setString(6,payment);
                            ps.setInt(7,payee_code);
                            ps.setString(8,sanc_auth);
                            ps.setString(9,sanc_by);
                            ps.setString(10,remarks);
                            ps.setString(11,update_user);
                            ps.setInt(12,refNo);
                            ps.setDate(13,refDate);
                            ps.setLong(14,acc_HeadCode);
                            ps.setLong(15,budget_Provided);
                            ps.setLong(16,budget_sofar_spent);
                            ps.setLong(17,bal_amt);
                            ps.setInt(18,txtMade);
                            ps.setString(19,recovery);
                            ps.setInt(20,tot_instalments);
                            ps.setInt(21,txt_EMI);
                            ps.setDate(22,start_month);
                            ps.setInt(23,resi_amt);
                            ps.setInt(24,deduction_No);
                            ps.setInt(25,tot_sanctionamt);
                            ps.setInt(26,unitCode);
                            ps.setInt(27, officeCode);
                            ps.setInt(28, sancNo);
                            count = ps.executeUpdate();                            
                            System.out.println("count"+count);
                            if(count>0){
                            	xml=xml+"<flag>success</flag>";
                            }else if(count==0){
                                    xml=xml+"<flag>nodata</flag>";
                            }
                            ps.close();                            
                        }catch(Exception e){
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                            }
                    }
                    
                 xml=xml+"</response>";
                 //out.println(xml);
                 pw.write(xml);
                 System.out.println("xml is : " + xml);
                 pw.flush();
                 pw.close();
        }     
/*************************************************************************************************************************************************************************/
 public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException          
 {
                 String CONTENT_TYPE="text/html";
                 response.setContentType(CONTENT_TYPE);
                 response.setHeader("Cache-Control","no-cache");
                
     int sanc_proc_no=0;
                 int Acc_head_code=0;
                 int Bill_majr_code=0;int Bill_minr_code=0;int Bill_sub_code=0;
                 int cashbook_yr=0;int cashbook_mn=0;int acc_unit_id=0;int acc_office_id=0;
                 int payee_code=0,ref_No=0,sanc_auth=0,sanc_by=0,trf_acct_unit=0,tot_instalments=0,residual_no=0;
                 String cmnd="";
                 String Genremarks="";
                 String payee_type="";String payment_type="",recovery_salPen="";
                
                 double budget_provided=0,budget_so_spent=0,balance_amt=0,EMI=0,resi_amt=0,tot_sanc_amt=0;
               
                 Calendar c,c1,c2;
                 String[] sd=null,sd1=null,sd2=null; 
                 Date txtsanc_date=null;Date ref_date=null,EMI_startmonth=null;
                 
                 PrintWriter pw=response.getWriter();
                 System.out.println("Welcome to dopost");
                 HttpSession session=request.getSession(false);
                 String update_user=(String)session.getAttribute("UserId");
                 long l=System.currentTimeMillis();
                 Timestamp ts=new Timestamp(l);
                 System.out.println("Session :"+session);
                 /*********** connection establishment****************/
                 Connection con=null;
                 ResultSet rs=null;
                 PreparedStatement ps=null;
                 try
                 {
                             ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                             String ConnectionString="";
                             String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                             String strdsn=rs1.getString("Config.DSN");
                             String strhostname=rs1.getString("Config.HOST_NAME");
                             String strportno=rs1.getString("Config.PORT_NUMBER");
                             String strsid=rs1.getString("Config.SID");
                             String strdbusername=rs1.getString("Config.USER_NAME");
                             String strdbpassword=rs1.getString("Config.PASSWORD");
                                              
                             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                 Class.forName(strDriver.trim());
                                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                 }
                 catch(Exception e)
                 {
                         System.out.println("Exception in connection...."+e);
                 } 
                     try
                       {
                           session=request.getSession(false);
                           if(session==null)
                           {
                               System.out.println(request.getContextPath()+"/index.jsp");
                               response.sendRedirect(request.getContextPath()+"/index.jsp");
                               return;
                           }
                           System.out.println(session);
                       } 
                     catch(Exception e)
                       {
                             System.out.println("Redirect Error :"+e);
                       }
                       session=request.getSession(false);
                     try
                        {
                               cmnd =  request.getParameter("Command");     
                               System.out.println("Command passed via the button pressed : " + cmnd);
                        }
                     catch(Exception e3)
                        {
                                e3.printStackTrace();
                        }
 /*///////////////////////////////Getting the values from the JSP Page//////////////////////////////////////////////////*/
          if(cmnd.equalsIgnoreCase("Add")) 
         {
                int errcode=0;
                try{acc_unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{acc_office_id=Integer.parseInt(request.getParameter("cmbOffice_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                /********************************Getting the CachBook Year and Cash Month**************************************************************************************/
                 try
                 {
                                 sd=request.getParameter("txt_sancpro_date").split("/");
                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                                 java.util.Date d=c.getTime();
                                 txtsanc_date=new Date(d.getTime());
                 }
                 catch(Exception e)
                 {
                             System.out.println("Exception in date:"+e.getMessage());
                 }
                                 
                 System.out.println("b4 getting month and year");
                 try{cashbook_yr=Integer.parseInt(sd[2]);}
                 catch(Exception e){System.out.println("exception"+e );}
                 System.out.println("txtCash_year "+cashbook_yr);
                 
                 try{cashbook_mn=Integer.parseInt(sd[1]);}
                 catch(Exception e){System.out.println("exception"+e );}
                 System.out.println("txtCash_Month_hid "+cashbook_mn);
                 
/**************************date formats**************************************************************************/                 
                     try
                     {
                                     sd1=request.getParameter("txtRefDate").split("/");
                                     c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                                     java.util.Date d1=c1.getTime();
                                     ref_date=new Date(d1.getTime());
                     }
                     catch(Exception e)
                     {
                                 System.out.println("Exception in date:"+e.getMessage());
                     }
                     try
                     {
                                     sd2=request.getParameter("txt_start_month").split("/");
                                     c2=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
                                     java.util.Date d2=c2.getTime();
                                     EMI_startmonth =new Date(d2.getTime());
                     }
                     catch(Exception e)
                     {
                                 System.out.println("Exception in date:"+e.getMessage());
                     }
                try{payment_type=request.getParameter("rad_payment_type");}catch(Exception e){System.out.println("Exception arised"+e);}
                
                try{Bill_majr_code=Integer.parseInt(request.getParameter("txtbill_majr_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{Bill_minr_code=Integer.parseInt(request.getParameter("txtbill_minr_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{Bill_sub_code=Integer.parseInt(request.getParameter("txtbill_sub_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{payee_type=request.getParameter("rad_payee_type");}catch(Exception e){System.out.println("Exception arised"+e);}
                try{payee_code=Integer.parseInt(request.getParameter("txt_payee_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{ref_No=Integer.parseInt(request.getParameter("txtRefNo"));}catch(Exception e){System.out.println("Exception arised"+e);}
               // try{ref_date=request.getParameter("txtRefDate");}catch(Exception e){System.out.println("Exception arised"+e);}    
                //try{sancpro_date=request.getParameter("txt_sancpro_date");}catch(Exception e){System.out.println("Exception arised"+e);}
                
                                  
                
                try{sanc_auth=Integer.parseInt(request.getParameter("cmb_sanc_auth"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{sanc_by=Integer.parseInt(request.getParameter("txtsanc_by"));}catch(Exception e){System.out.println("Exception arised"+e);}
                try{Acc_head_code=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));}catch(Exception e){System.out.println("Exception arised"+e);}
                     
                     try{budget_provided=Double.parseDouble(request.getParameter("budget_Provided"));}catch(Exception e){System.out.println("Exception arised"+e);}
                     try{budget_so_spent=Double.parseDouble(request.getParameter("budget_sofar_spent"));}catch(Exception e){System.out.println("Exception arised"+e);}
                     try{balance_amt=Double.parseDouble(request.getParameter("balance"));}catch(Exception e){System.out.println("Exception arised"+e);}
                     try{trf_acct_unit=Integer.parseInt(request.getParameter("accunitMade"));}catch(Exception e){System.out.println("Exception arised"+e);}
                     try{recovery_salPen=request.getParameter("rad_recovery");}catch(Exception e){System.out.println("Exception arised"+e);}
                     try{tot_instalments=Integer.parseInt(request.getParameter("txt_tot_instalments"));}catch(Exception e){System.out.println("Exception arised"+e);}  
                     try{EMI=Double.parseDouble(request.getParameter("txt_EMI"));}catch(Exception e){System.out.println("Exception arised"+e);} 
                    // try{EMI_startmonth=request.getParameter("txt_start_month");}catch(Exception e){System.out.println("Exception arised"+e);}
                     try{resi_amt=Double.parseDouble(request.getParameter("txt_resi_amt"));}catch(Exception e){System.out.println("Exception arised"+e);} 
                     try{residual_no=Integer.parseInt(request.getParameter("txt_resi_deduction_No"));}catch(Exception e){System.out.println("Exception arised"+e);} 
                     
                     try{tot_sanc_amt=Double.parseDouble(request.getParameter("txt_tot_sanctionamt"));}catch(Exception e){System.out.println("Exception arised"+e);}
                     try{Genremarks=request.getParameter("txt_GeneralRemarks");}catch(Exception e){System.out.println("Exception arised"+e);}
               
                       /**********************************calculating Max value of sanction proceeding Number************************************/
                       try
                       {
                               String sqlsel="select decode(max(SANCTION_PROC_NO),null,0,max(SANCTION_PROC_NO))as SANC_PRO_NO from FAS_SANC_PROCEEDING_MST_NEW";
                               ps=con.prepareStatement(sqlsel);
                               rs=ps.executeQuery();
                               System.out.println(sqlsel);
                               if(rs.next())
                               {
                                        sanc_proc_no=rs.getInt("SANC_PRO_NO");
                               }
                               sanc_proc_no=sanc_proc_no+1;
                               System.out.println("Maximum value of Sanction Proceeding Number is :"+sanc_proc_no);
                               ps.close();
                               rs.close();
                       }
                       catch(Exception ee) 
                       {
                            System.out.println("Exception in getting maximum value of sanction proceeding number :"+ee);    
                       }
                               
                   try
                   {
                           con.clearWarnings();
                           con.setAutoCommit(false);
                           String sqlins="insert into FAS_SANC_PROCEEDING_MST_NEW(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,\n" + 
                           "CASHBOOK_YEAR,\n" + 
                           "CASHBOOK_MONTH,\n" + 
                           "SANCTION_PROC_NO ,\n" + 
                           "SANCTION_PROC_DATE,BILL_MAJOR_TYPE_CODE,\n" + 
                           "BILL_MINOR_TYPE_CODE,\n" + 
                           "BILL_SUB_TYPE_CODE,\n" + 
                           "PAYEE_TYPE, \n" + 
                           "PAYMENT_TYPE, \n" + 
                           "PAYEE_CODE, \n" + 
                           "SANCTION_AUTHORITY, \n" + 
                           "SANCTION_BY, \n" + 
                           "REMARKS, \n" + 
                           "UPDATED_BY_USER_ID, \n" + 
                           "UPDATED_DATE ,\n" + 
                           "REF_NO ,\n" + 
                           "REF_DATE ,\n" + 
                           "ACCOUNT_HEAD_CODE ,\n" + 
                           "BUDGET_PROVIDED ,\n" + 
                           "SUDGET_SOFAR_SPENT ,\n" + 
                           "BALANCE_AMOUNT ,\n" + 
                           "TRF_ACCOUNTING_UNIT ,\n" + 
                           "RECOVERY_FROM ,\n" + 
                           "TOTAL_INSTALMENTS ,\n" + 
                           "EMI ,\n" + 
                           "EMI_START_MONTH ,\n" + 
                           "RESIDUAL_AMOUNT ,\n" + 
                           "RESIDUAL_NUMBER ,\n" + 
                           "TOTAL_SANCTIONED_AMOUNT ,\n " +
                           "STATUS ) " +
                         "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                           //"values (?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?)";
                        
                     ps=con.prepareStatement(sqlins);
                 //    System.out.println("insertion query :::::::::::::::::"+sqlins);
                     ps.setInt(1,acc_unit_id);
                     ps.setInt(2,acc_office_id);
                     ps.setInt(3,cashbook_yr);
                     ps.setInt(4,cashbook_mn);
                     ps.setInt(5,sanc_proc_no);
                 
                     ps.setDate(6,txtsanc_date);
                     ps.setInt(7,Bill_majr_code);
                     ps.setInt(8,Bill_minr_code);
                     ps.setInt(9,Bill_sub_code);
                     ps.setString(10,payee_type);
                 
                     
                     ps.setString(11,payment_type);
                     ps.setInt(12,payee_code);
                     ps.setInt(13,sanc_auth);
                     ps.setInt(14,sanc_by);
                     ps.setString(15,Genremarks);
                  
                    
                   ps.setString(16,update_user);
                   ps.setTimestamp(17,ts); 
                   ps.setInt(18,ref_No);
                   ps.setDate(19,ref_date);
                   ps.setInt(20,Acc_head_code);
                   
                   
                   ps.setDouble(21,budget_provided);
                   ps.setDouble(22,budget_so_spent);
                   ps.setDouble(23,balance_amt);
                   ps.setInt(24,trf_acct_unit);
                   ps.setString(25,recovery_salPen);
                   
                   ps.setInt(26,tot_instalments);
                   ps.setDouble(27,EMI);
                   ps.setDate(28,EMI_startmonth);
                   ps.setDouble(29,resi_amt);
                   ps.setInt(30,residual_no);
                   ps.setDouble(31,tot_sanc_amt);
                   ps.setString(32, "L");
/**********************************************************************************************************************************/
// ps.setInt(1,3);
// ps.setInt(2,5000);
// ps.setInt(3,2011);
// ps.setInt(4,6);
// ps.setInt(5,1);
// 
// ps.setString(6,"01-jun-2011");
// ps.setInt(7,1);
// ps.setInt(8,1);
// ps.setInt(9,1);
// ps.setString(10,"E");
// 
// 
// ps.setString(11,"A");
// ps.setInt(12,10099);
// ps.setInt(13,2);
// ps.setInt(14,9315);
// ps.setString(15,"test");
// 
// 
// ps.setString(16,"twad10099");
// ps.setTimestamp(17,ts);
// ps.setInt(18,1);
// ps.setString(19,"02-jun-2011");
// ps.setInt(20,100101);
// 
// 
// ps.setDouble(21,1000.0);
// ps.setDouble(22,500.0);
// ps.setDouble(23,500.0);
// ps.setInt(24,111);
// ps.setString(25,"Y");
// 
// ps.setInt(26,10);
// ps.setDouble(27,100.0);
// ps.setString(28,"09-jun-2011");
// ps.setDouble(29,0.0);
// ps.setInt(30,414);
// ps.setDouble(31,1010.0);
                  
                   System.out.println("Error code :"+errcode);
                      errcode=ps.executeUpdate();
                      System.out.println("Error code :"+errcode);
                        ps.close();
                              if(errcode==0)
                              {         
                                        System.out.println("redirect");
                                        sendMessage(response,"The insertion into the Sanction Proceeding master table Failed ","ok");                                 
                              }
                              else
                              {
                                         System.out.println("The records inserted into the Sanction Proceeding master table scuccessfully");
                                        ps.close();
                                        System.out.println("b4 commit");
                                        con.commit();
                                        sendMessage(response,"The General Sanction Proceeding Number "+sanc_proc_no+" is created Successfully \n ","ok");
                                        //sendMessage(response,"The Records are inserted into both table Successfully ","ok");
                              }
               }
               catch(Exception e) 
                      {
                          try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                          sendMessage(response,"The Insertion of records into the table Failed ","ok");
                          System.out.println("Exception occur due to "+e);
                      }
                      finally
                      {
                          System.out.println("done");
                          try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Exception arised :"+sqle);}
                      }
                 }
                 
                           pw.flush();
                           pw.close();
 }//DoPost method close

private void sendMessage(HttpServletResponse response,String msg,String bType)
{
        try
        {
             String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
             response.sendRedirect(url);
        }
        catch(IOException e)
        {
                System.out.println("Exception arised :"+e);
        }
}
}  

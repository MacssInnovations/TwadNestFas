package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import Servlets.Security.classes.UserProfile;


public class HR_Note extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HR_Note() {
        super();
       
    }
    Connection con=null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String CONTENT_TYPE = "text/xml; charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String cmd;System.out.println("welcomeeeeeeeeeeeeeeeeee");
        int major=0,sub=0,minor=0,headcode=0,Sanc_OffID=0;
        String xml="",hrdate="",hramt="",txtRemarks="",sql="";	
        String fin_year = "";
        PreparedStatement ps;
        ResultSet result=null;
        int eid=0;
        int cmbAcc_UnitCode = 0,cmbOffice_code=0;	 
        cmd=request.getParameter("Command");
        
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
                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                  try
                  {
                        con.clearWarnings();
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
          
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
        eid=empProfile.getEmployeeId();
        System.out.println("employee id:"+eid);
        String update_user;
        update_user = (String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
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
 		try
	    {
	         cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));System.out.println(cmbAcc_UnitCode);
	    } 
	    catch (Exception e) 
	    {
	        System.out.println("Exception to catch cmbAcc_UnitCode ");
	    }
	    try
	    {
	        cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));System.out.println(cmbOffice_code);
	    }
	    catch (Exception e) 
	    {
	        System.out.println("Exception to catch cmbOffice_code ");
	    } 
 if(cmd.equalsIgnoreCase("minorType")) 
      {
          xml="<response><command>minor</command>"; 
          try 
                  {
                      major=Integer.parseInt(request.getParameter("major2"));
                      ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major + "and status='L' ");
                      result = ps.executeQuery();
                      xml=xml+"<flag>success</flag>";
                      while (result.next()) 
                          {
                              xml=xml+"<minorcode>"+result.getString("BILL_MINOR_TYPE_CODE")+"</minorcode>";
                              xml=xml+"<minordesc>"+result.getString("BILL_MINOR_TYPE_DESC")+"</minordesc>";
                          }
                  }
            catch(Exception e) 
                  {
                          System.out.println("Exception in minor ===> "+e);   
                          xml=xml+"<flag>failure</flag>";  
                  }
              xml=xml+"</response>";
      }else if(cmd.equalsIgnoreCase("getDetails")) 
      {
    	  xml=xml+"<response><command>getDetails</command>";
    	  try{
    	  cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    	  cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
    	  fin_year=request.getParameter("finyr");
    	  int Note_No=Integer.parseInt(request.getParameter("NoteNo"));
				String Qry = "SELECT BILL_MAJOR_TYPE_CODE, "
						+ "  BILL_MINOR_TYPE_CODE, " + "  BILL_SUB_TYPE_CODE, "
						+ "  to_char(NOTE_DATE,'dd/mm/yyyy') as NOTE_DATE, " + "  NOTE_AMOUNT, "
						+ "  NOTE_PREPARED_BY, " + "  ACCOUNT_HEAD_CODE,financial_year ,b.SANCTION_PROC_OFFICE_ID,(select OFFICE_NAME from COM_MST_OFFICES o where o.OFFICE_ID=b.SANCTION_PROC_OFFICE_ID) as OFFICE_NAME"
						+ " FROM FAS_HR_NOTE_DETAILS b "
						+ " WHERE accounting_unit_id    =? "
						+ "  AND accounting_for_office_id=? "
						+ " AND hr_note_no              =? "
						+ " AND financial_year          =?";
				PreparedStatement ps_Det=con.prepareStatement(Qry);
				ps_Det.setInt(1, cmbAcc_UnitCode);
				ps_Det.setInt(2, cmbOffice_code);
				ps_Det.setString(4, fin_year);
				ps_Det.setInt(3, Note_No);
				
				ResultSet rs_Det=ps_Det.executeQuery();
				int cno=0;
				while(rs_Det.next()){
					  xml=xml+"<BILL_MAJOR_TYPE_CODE>"+rs_Det.getInt(1)+"</BILL_MAJOR_TYPE_CODE>";
					  xml=xml+"<BILL_MINOR_TYPE_CODE>"+rs_Det.getInt(2)+"</BILL_MINOR_TYPE_CODE>";
					  xml=xml+"<BILL_SUB_TYPE_CODE>"+rs_Det.getInt(3)+"</BILL_SUB_TYPE_CODE>";
					
					  xml=xml+"<NOTE_DATE>"+rs_Det.getString(4)+"</NOTE_DATE>";
					  xml=xml+"<NOTE_AMOUNT>"+rs_Det.getBigDecimal(5)+"</NOTE_AMOUNT>";
					  xml=xml+"<NOTE_PREPARED_BY>"+rs_Det.getString(6)+"</NOTE_PREPARED_BY>";
					  xml=xml+"<ACCOUNT_HEAD_CODE>"+rs_Det.getInt(7)+"</ACCOUNT_HEAD_CODE>";
					  xml=xml+"<financial_year>"+rs_Det.getString(8)+"</financial_year>";
					  xml=xml+"<SANCTION_PROC_OFFICE_ID>"+rs_Det.getInt(9)+"</SANCTION_PROC_OFFICE_ID>";
			    	   xml=xml+"<OFFICE_NAME>"+rs_Det.getString(10)+"</OFFICE_NAME>";
					  cno++;
				}if(cno>0){
					  xml=xml+"<flag>success</flag>";  
				}else{
					  xml=xml+"<flag>failure</flag>";  
				}
    	  
    	  }catch (Exception e) {
    		  xml=xml+"<flag>failure</flag>";  
			e.printStackTrace();
		} xml=xml+"</response>";
    	  
      }else if(cmd.equalsIgnoreCase("code"))
      {
	       int code=Integer.parseInt(request.getParameter("code"));
	       System.out.println("code >>> "+code);
	       int count2=0;
          xml=xml+"<response><command>code</command>"; 
           try 
                   {
                           //ps = con.prepareStatement("select PAYEE_NAME,PAYEE_CODE from FAS_HR_SANC_PROC_MST where PAYEE_CODE="+code+"");
                           ps = con.prepareStatement("select a.EMPLOYEE_ID,a.EMPLOYEE_NAME,a.EMPLOYEE_INITIAL,  b.DESIGNATION,a.DATE_OF_BIRTH,a.GPF_NO   from HRM_MST_EMPLOYEES a left outer join  HRM_EMP_CURRENT_POSTING d on a.EMPLOYEE_ID=d.EMPLOYEE_ID  left outer join  HRM_MST_DESIGNATIONS b on d.DESIGNATION_ID=b.DESIGNATION_ID   left outer join  HRM_MST_SERVICE_GROUP c on b.SERVICE_GROUP_ID=c.SERVICE_GROUP_ID   where   a.GPF_NO = "+code+ " order by EMPLOYEE_NAME");
           				result = ps.executeQuery();                                
                           while(result.next()) 
                           {
                               xml=xml+"<code>"+result.getInt("EMPLOYEE_ID")+"</code>";
                               xml=xml+"<desc>"+result.getString("EMPLOYEE_NAME")+"</desc>";
                               xml=xml+"<designation>"+result.getString("DESIGNATION")+"</designation>";
                               count2++;
                           }
                           if(count2>0)
                               xml=xml+"<flag>success</flag>";
                           else
                               xml=xml+"<flag>failure</flag>";
                   }
             catch(Exception e) 
                   {
                           System.out.println("Exception in masterdesc ===> "+e);   
                           xml=xml+"<flag>failure</flag>";  
                   }
               xml=xml+"</response>";
               
       }else if(cmd.equalsIgnoreCase("SanNo_det")){

    	   xml=xml+"<response><command>"+cmd+"</command>";
     	  try{
     	  cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
     	  cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
     	  int txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));
     	  int txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth")); 
     	  int SancNo=Integer.parseInt(request.getParameter("SancNo")); 
     	  String Qry="SELECT SANCTION_PROCEEDING_NO, " +
     			 "  TO_CHAR(SANCTION_PROCEEDING_DATE,'dd/mm/yyyy') AS SANCTION_PROCEEDING_DATE, " +
     			"  BILL_MAJOR_TYPE_CODE, " +
     			"  BILL_MINOR_TYPE_CODE, " +
     			"  BILL_SUB_TYPE_CODE, " +
     			"  PAYEE_TYPE, " +
     			"  PAYEE_CODE, " +
     			"  PAYEE_NAME, " +
     			"  NO_OF_HR, " +
     			"  TO_CHAR(HR_FROM_DATE,'dd/mm/yyyy') AS  HR_FROM_DATE, " +
     			"  TO_CHAR(HR_TO_DATE,'dd/mm/yyyy') AS  HR_TO_DATE, " +
     			"  HR_AMOUNT, " +
     			"  VOU_ATTACHED, " +
     			"  NO_OF_VOU, " +
     			"  REF_NO, " +
     			" TO_CHAR(REF_DATE,'dd/mm/yyyy') AS REF_DATE, " +
     			"  SANCTION_AUTHORITY," +
     			"  SANCTIONED_BY, " +
     			"  TOTAL_SANCTION_AMOUNT, " +
     			"  ACCOUNT_HEAD_CODE, " +
     			"  BUD_PROVIDED, " +
     			"  BUD_SPENT, " +
     			"  BAL_AMT, " +
     			"  PAYMENT_TO_BE_MADE_UNIT_ID, " +
     			"  REMARKS, " +
     			"  HR_NOTE_NO , TO_CHAR(HR_NOTE_DATE,'dd/mm/yyyy') AS HR_NOTE_DATE " +
     			" FROM FAS_HR_SANC_PROC_MST " +
     			" WHERE accounting_unit_id     =?" +
     			" AND accounting_for_office_id = ? " +
     			" AND CASHBOOK_YEAR            =? " +
     			" AND cashbook_month           =? " +
     			" AND SANCTION_PROCEEDING_NO   =?";
     		PreparedStatement ps_San=con.prepareStatement(Qry);
     		ps_San.setInt(1, cmbAcc_UnitCode);
     		ps_San.setInt(2, cmbOffice_code);
     		ps_San.setInt(3, txtCBYear);
			ps_San.setInt(4, txtCBMonth);
			ps_San.setInt(5, SancNo);
			ResultSet rs_San=ps_San.executeQuery();
			int c_no=0;
			while(rs_San.next()){
				  xml=xml+"<SANCTION_PROCEEDING_NO>"+rs_San.getInt(1)+"</SANCTION_PROCEEDING_NO>";
				  xml=xml+"<SANCTION_PROCEEDING_DATE>"+rs_San.getString(2)+"</SANCTION_PROCEEDING_DATE>";
				  xml=xml+"<BILL_MAJOR_TYPE_CODE>"+rs_San.getInt(3)+"</BILL_MAJOR_TYPE_CODE>";
				  xml=xml+"<BILL_MINOR_TYPE_CODE>"+rs_San.getInt(4)+"</BILL_MINOR_TYPE_CODE>";
				  xml=xml+"<BILL_SUB_TYPE_CODE>"+rs_San.getInt(5)+"</BILL_SUB_TYPE_CODE>";
				  xml=xml+"<PAYEE_TYPE>"+rs_San.getString(6)+"</PAYEE_TYPE>";
				  xml=xml+"<PAYEE_CODE>"+rs_San.getInt(7)+"</PAYEE_CODE>";
				  xml=xml+"<PAYEE_NAME>"+rs_San.getString(8)+"</PAYEE_NAME>";
				  xml=xml+"<NO_OF_HR>"+rs_San.getInt(9)+"</NO_OF_HR>";
				  xml=xml+"<HR_FROM_DATE>"+rs_San.getString(10)+"</HR_FROM_DATE>";
				  xml=xml+"<HR_TO_DATE>"+rs_San.getString(11)+"</HR_TO_DATE>";
				  xml=xml+"<HR_AMOUNT>"+rs_San.getBigDecimal(12)+"</HR_AMOUNT>";
				  xml=xml+"<VOU_ATTACHED>"+rs_San.getString(13)+"</VOU_ATTACHED>";
				  xml=xml+"<NO_OF_VOU>"+rs_San.getInt(14)+"</NO_OF_VOU>";
				  xml=xml+"<REF_NO>"+rs_San.getInt(15)+"</REF_NO>";
				  xml=xml+"<REF_DATE>"+rs_San.getString(16)+"</REF_DATE>";
				  xml=xml+"<SANCTION_AUTHORITY>"+rs_San.getInt(17)+"</SANCTION_AUTHORITY>";
				  xml=xml+"<SANCTIONED_BY>"+rs_San.getInt(18)+"</SANCTIONED_BY>";
				  xml=xml+"<TOTAL_SANCTION_AMOUNT>"+rs_San.getBigDecimal(19)+"</TOTAL_SANCTION_AMOUNT>";
				  xml=xml+"<ACCOUNT_HEAD_CODE>"+rs_San.getInt(20)+"</ACCOUNT_HEAD_CODE>";
				  xml=xml+"<BUD_PROVIDED>"+rs_San.getFloat(21)+"</BUD_PROVIDED>";
				  xml=xml+"<BUD_SPENT>"+rs_San.getFloat(22)+"</BUD_SPENT>";
				  xml=xml+"<BAL_AMT>"+rs_San.getFloat(23)+"</BAL_AMT>";
				  xml=xml+"<PAYMENT_TO_BE_MADE_UNIT_ID>"+rs_San.getInt(24)+"</PAYMENT_TO_BE_MADE_UNIT_ID>";
				  xml=xml+"<REMARKS>"+rs_San.getString(25)+"</REMARKS>";
				  xml=xml+"<HR_NOTE_NO>"+rs_San.getInt(26)+"</HR_NOTE_NO>";
				  xml=xml+"<HR_NOTE_DATE>"+rs_San.getString(27)+"</HR_NOTE_DATE>";
				  try 
                  {
                          //ps = con.prepareStatement("select PAYEE_NAME,PAYEE_CODE from FAS_HR_SANC_PROC_MST where PAYEE_CODE="+code+"");
                          ps = con.prepareStatement("select a.EMPLOYEE_ID,a.EMPLOYEE_NAME,a.EMPLOYEE_INITIAL,  b.DESIGNATION,a.DATE_OF_BIRTH,a.GPF_NO   from HRM_MST_EMPLOYEES a left outer join  HRM_EMP_CURRENT_POSTING d on a.EMPLOYEE_ID=d.EMPLOYEE_ID  left outer join  HRM_MST_DESIGNATIONS b on d.DESIGNATION_ID=b.DESIGNATION_ID   left outer join  HRM_MST_SERVICE_GROUP c on b.SERVICE_GROUP_ID=c.SERVICE_GROUP_ID   where   a.GPF_NO =? order by EMPLOYEE_NAME");
          				ps.setInt(1, rs_San.getInt(7));
                          result = ps.executeQuery();                                
                          while(result.next()) 
                          {
                              xml=xml+"<code>"+result.getInt("EMPLOYEE_ID")+"</code>";
                              xml=xml+"<desc>"+result.getString("EMPLOYEE_NAME")+"</desc>";
                              xml=xml+"<designation>"+result.getString("DESIGNATION")+"</designation>";
                             
                          }
                         
                  }
            catch(Exception e) 
                  {
                          System.out.println("Exception in masterdesc ===> "+e);   
                          xml=xml+"<flag>failure</flag>";  
                  }
				  
				  
				  
				  
				  c_no++;
			}if(c_no>0){
				  xml=xml+"<flag>success</flag>";  
			}else{
				  xml=xml+"<flag>failure</flag>";  
			}
     	  }catch (Exception e) {
		     e.printStackTrace();
		     xml=xml+"<flag>failure</flag>";  
		} xml=xml+"</response>";
       
       }else if(cmd.equalsIgnoreCase("getSan_No")){
    	   xml=xml+"<response><command>getSan_No</command>";
     	  try{
     	  cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
     	  cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
     	  int txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));
     	  int txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth")); 
     	  String Qry="SELECT SANCTION_PROCEEDING_NO " +
     	 " FROM FAS_HR_SANC_PROC_MST " +
     	" WHERE accounting_unit_id =? " +
     	" AND accounting_for_office_id        = ? " +
     	" AND CASHBOOK_YEAR             =? " +
     	" AND cashbook_month            =?";
     		PreparedStatement ps_San=con.prepareStatement(Qry);
     		ps_San.setInt(1, cmbAcc_UnitCode);
     		ps_San.setInt(2, cmbOffice_code);
     		ps_San.setInt(3, txtCBYear);
			ps_San.setInt(4, txtCBMonth);
			
			ResultSet rs_San=ps_San.executeQuery();
			int c_no=0;
			while(rs_San.next()){
				  xml=xml+"<San_No>"+rs_San.getInt(1)+"</San_No>";
				  c_no++;
			}if(c_no>0){
				  xml=xml+"<flag>success</flag>";  
			}else{
				  xml=xml+"<flag>failure</flag>";  
			}
     	  }catch (Exception e) {
		     e.printStackTrace();
		     xml=xml+"<flag>failure</flag>";  
		} xml=xml+"</response>";
       }
else if(cmd.equalsIgnoreCase("getDetailssing")) 
      {
    	  xml=xml+"<response><command>getDetailssing</command>";
    	  try{
    	  cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    	  cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
    	  int txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));
    	  int txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth"));
    	  int Note_No=Integer.parseInt(request.getParameter("NoteNo"));
    	
				String Qry = "SELECT BILL_MAJOR_TYPE_CODE, "
						+ "  BILL_MINOR_TYPE_CODE, " + "  BILL_SUB_TYPE_CODE, "
						+ "  to_char(NOTE_DATE,'dd/mm/yyyy') as NOTE_DATE, " + "  NOTE_AMOUNT, "
						+ "  NOTE_PREPARED_BY, " + "  ACCOUNT_HEAD_CODE ,financial_year,b.SANCTION_PROC_OFFICE_ID,(select OFFICE_NAME from COM_MST_OFFICES o where o.OFFICE_ID=b.SANCTION_PROC_OFFICE_ID) as OFFICE_NAME "
						+ " FROM FAS_HR_NOTE_DETAILS b "
						+ " WHERE accounting_unit_id    =? "
						+ "  AND accounting_for_office_id=? "
						+ " AND hr_note_no              =? "
						+ " AND CASHBOOK_YEAR          =? "
						+ " AND CASHBOOK_MONTH          =?";
				PreparedStatement ps_Det=con.prepareStatement(Qry);
				ps_Det.setInt(1, cmbAcc_UnitCode);
				ps_Det.setInt(2, cmbOffice_code);
				ps_Det.setInt(3, Note_No);
				ps_Det.setInt(4, txtCBYear);
				ps_Det.setInt(5, txtCBMonth);
				
				ResultSet rs_Det=ps_Det.executeQuery();
				int cno=0;
				while(rs_Det.next()){
					  xml=xml+"<BILL_MAJOR_TYPE_CODE>"+rs_Det.getInt(1)+"</BILL_MAJOR_TYPE_CODE>";
					  xml=xml+"<BILL_MINOR_TYPE_CODE>"+rs_Det.getInt(2)+"</BILL_MINOR_TYPE_CODE>";
					  xml=xml+"<BILL_SUB_TYPE_CODE>"+rs_Det.getInt(3)+"</BILL_SUB_TYPE_CODE>";
					
					  xml=xml+"<NOTE_DATE>"+rs_Det.getString(4)+"</NOTE_DATE>";
					  xml=xml+"<NOTE_AMOUNT>"+rs_Det.getBigDecimal(5)+"</NOTE_AMOUNT>";
					  xml=xml+"<NOTE_PREPARED_BY>"+rs_Det.getString(6)+"</NOTE_PREPARED_BY>";
					  xml=xml+"<ACCOUNT_HEAD_CODE>"+rs_Det.getInt(7)+"</ACCOUNT_HEAD_CODE>";
					  xml=xml+"<financial_year>"+rs_Det.getString(8)+"</financial_year>";
					  xml=xml+"<SANCTION_PROC_OFFICE_ID>"+rs_Det.getInt(9)+"</SANCTION_PROC_OFFICE_ID>";
			    	   xml=xml+"<OFFICE_NAME>"+rs_Det.getString(10)+"</OFFICE_NAME>";
					  cno++;
				}if(cno>0){
					  xml=xml+"<flag>success</flag>";  
				}else{
					  xml=xml+"<flag>failure</flag>";  
				}
    	  
    	  }catch (Exception e) {
    		  xml=xml+"<flag>failure</flag>";  
			e.printStackTrace();
		} xml=xml+"</response>";
    	  
      }
	else if(cmd.equalsIgnoreCase("subType")) 
    {
	System.out.println(":::::::::::::sub type::::::::::");
        xml="<response><command>subb</command>"; 
        try 
                {
        	 		major=Integer.parseInt(request.getParameter("major2"));
                    sub=Integer.parseInt(request.getParameter("sub2"));	
                    System.out.println("sub"+sub);
                    ps = con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+sub +"and status='L'");
                    System.out.println("ps"+ps);
                    result = ps.executeQuery();
                    xml=xml+"<flag>success</flag>";
                    while (result.next()) 
                        {
                    	    System.out.println("subdesc"+result.getString("BILL_SUB_TYPE_DESC"));
                            xml=xml+"<subcode>"+result.getString("BILL_SUB_TYPE_CODE")+"</subcode>";
                            xml=xml+"<subdesc>"+result.getString("BILL_SUB_TYPE_DESC")+"</subdesc>";
                        }   
                }
          catch(Exception e) 
                {
                        System.out.println("Exception in minor ===> "+e);   
                        xml=xml+"<flag>failure</flag>";  
                }
            xml=xml+"</response>";
            System.out.println("xml"+xml);
    }
	else if(cmd.equalsIgnoreCase("loadAcc")) 
    {
	
        xml="<response><command>loadAcc</command>"; 
        try 
                {
        	 		int majorType=Integer.parseInt(request.getParameter("majorType"));
        	 		int minorType=Integer.parseInt(request.getParameter("minorType"));	
        	 		int billsubtype=Integer.parseInt(request.getParameter("billsubtype"));
        	 		int txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                    
                   String ss = "SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS WHERE USAGE_STATUS   ='Y' "+ 
					"	and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from FAS_BILL_ACCOUNT_HEADS where BILL_MAJOR_TYPE_CODE= "+majorType+
						" and BILL_MINOR_TYPE_CODE="+minorType+" and BILL_SUB_TYPE_CODE="+billsubtype+" and ACCOUNT_HEAD_CODE="+txtAcc_HeadCode+")";
                    System.out.println("ss::"+ss);
                    ps = con.prepareStatement(ss);
                    result = ps.executeQuery();
                    
                    if (result.next()) 
                        {
                    	xml=xml+"<flag>success</flag>";
                    	   
                            xml=xml+"<headcode>"+result.getInt("ACCOUNT_HEAD_CODE")+"</headcode>";
                            xml=xml+"<headdesc>"+result.getString("ACCOUNT_HEAD_DESC")+"</headdesc>";
                        } 
                    else
                    {
                    	 xml=xml+"<flag>failure</flag>";  
                    }
                }
          catch(Exception e) 
                {
                        System.out.println("Exception in minor ===> "+e);   
                        xml=xml+"<flag>failure</flag>";  
                }
            xml=xml+"</response>";
          //  System.out.println("xml"+xml);
    }
	else if(cmd.equalsIgnoreCase("Load_headCode")) 
    {
	
        xml="<response><command>Load_headCode</command>"; 
        try 
                {
        	 		int majorType=Integer.parseInt(request.getParameter("majorType"));
        	 		int minorType=Integer.parseInt(request.getParameter("minorType"));	
        	 		int billsubtype=Integer.parseInt(request.getParameter("billsubtype"));
        	 		String txtfin_yaer=request.getParameter("fin_yr");
                    
                   String ss = "SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS WHERE USAGE_STATUS   ='Y' "+ 
					"	and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from FAS_BILL_ACCOUNT_HEADS where BILL_MAJOR_TYPE_CODE= "+majorType+
						" and BILL_MINOR_TYPE_CODE="+minorType+" and BILL_SUB_TYPE_CODE="+billsubtype+" and financial_year='"+txtfin_yaer+"')";
                    System.out.println("ss::"+ss);
                    ps = con.prepareStatement(ss);
                    result = ps.executeQuery();
                    int c=0;
                    while(result.next()) 
                        {
                   
                    	   
                            xml=xml+"<headcode>"+result.getInt("ACCOUNT_HEAD_CODE")+"</headcode>";
                            xml=xml+"<headdesc>"+result.getString("ACCOUNT_HEAD_DESC")+"</headdesc>";
                            c++;
                        } 
                   if(c==0)
                   {
                	   xml=xml+"<flag>failure</flag>";  
                   }else{
                	 	xml=xml+"<flag>success</flag>";
                   }
                }
          catch(Exception e) 
                {
                        System.out.println("Exception in minor ===> "+e);   
                        xml=xml+"<flag>failure</flag>";  
                }
            xml=xml+"</response>";
          //  System.out.println("xml"+xml);
    }else if(cmd.equalsIgnoreCase("ChkBudget")){
    	   xml=xml+"<response>";
    	   xml=xml+"<Command>"+cmd+"</Command>";
    	cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    	cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));//System.out.println(txtCBYear);
   String finYr=request.getParameter("finYr");//System.out.println(txtCBMonth);
       String   headCode=request.getParameter("headcode");//System.out.println(billmajortype);
          	
    try{
    	String qry_Budget="  SELECT ACCOUNT_HEAD_CODE,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID, " +
						"    CURRENT_YEAR_BUDGET_ALLOTTED , " +
						"    BUDGET_SOFAR_SPENT, " +
						"    REF_NO, " +
						"    TO_CHAR(REF_DATE,'dd/mm/yyyy')AS REF_DATE " +
						"  FROM COM_BUDGET_DETAILS b  where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and " +
						" FINANCIAL_YEAR ='"+finYr+"' and ACCOUNT_HEAD_CODE ="+headCode ;
    	
    PreparedStatement ps_bud=con.prepareStatement(qry_Budget);
  /*  ps_bud.setInt(1,cmbAcc_UnitCode);
    ps_bud.setInt(2,cmbOffice_code);
    ps_bud.setString(3, finYr);
    ps_bud.setString(4,headCode);*/
    System.out.println(qry_Budget);
    ResultSet rs_bud=ps_bud.executeQuery();
    int c1=0;
    while(rs_bud.next())
    {
    	   xml=xml+"<ACCOUNTING_UNIT_ID>"+rs_bud.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";
    	   xml=xml+"<REF_NO>"+rs_bud.getString("REF_NO")+"</REF_NO>";
    	   xml=xml+"<REF_DATE>"+rs_bud.getString("REF_DATE")+"</REF_DATE>";
    	   xml=xml+"<CURRENT_YEAR_BUDGET_ALLOTTED>"+rs_bud.getBigDecimal("CURRENT_YEAR_BUDGET_ALLOTTED")+"</CURRENT_YEAR_BUDGET_ALLOTTED>";
    	   xml=xml+"<BUDGET_SOFAR_SPENT>"+rs_bud.getBigDecimal("BUDGET_SOFAR_SPENT")+"</BUDGET_SOFAR_SPENT>";
    	   xml=xml+"<SANCTION_PROC_OFFICE_ID>"+rs_bud.getInt("SANCTION_PROC_OFFICE_ID")+"</SANCTION_PROC_OFFICE_ID>";
    	   xml=xml+"<OFFICE_NAME>"+rs_bud.getString("OFFICE_NAME")+"</OFFICE_NAME>";
   c1++;
    }
    if(c1==0)
    	 {
      	   xml=xml+"<flag>failure</flag>";  
         }else{
      	 	xml=xml+"<flag>success</flag>";
         }
    }catch (Exception e) {
		e.printStackTrace();
		 xml=xml+"<flag>failure</flag>";  
	}
    xml=xml+"</response>";
    
    }
    	else if(cmd.equalsIgnoreCase("loadNoteNo")){
    
    
    xml=xml+"<response><command>loadNoteNo</command>";
    	cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    	cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
    	 fin_year = request.getParameter("fin_yr");
    	 try{
    		 PreparedStatement ps_Note=con.prepareStatement("select hr_note_no from fas_hr_note_details where accounting_unit_id =? and accounting_for_office_id = ? and FINANCIAL_YEAR=?");
    	 ps_Note.setInt(1, cmbAcc_UnitCode);
    	 ps_Note.setInt(2, cmbOffice_code);
    	 ps_Note.setString(3, fin_year);
    	 ResultSet rs_Note=ps_Note.executeQuery();
    	 int count=0;
    	 while(rs_Note.next())
    	 {
    		 xml=xml+"<hr_note_no>"+rs_Note.getInt("hr_note_no")+"</hr_note_no>";
     count++; 
    	 }if(count==0)
    	 {
        	   xml=xml+"<flag>failure</flag>";  
           }else{
        	 	xml=xml+"<flag>success</flag>";
           }
    	 }catch (Exception e) {
			// TODO: handle exception
    		 e.printStackTrace();
    		 xml=xml+"<flag>failure</flag>";  
		}
    	    xml=xml+"</response>"; 
    }
    else if(cmd.equalsIgnoreCase("Update")){
    	 
         System.out.println("Inside Add"); 
         //int no=Integer.parseInt(request.getParameter("note_no"));
         major=Integer.parseInt(request.getParameter("majorType"));//System.out.println(txtCBYear);
         minor=Integer.parseInt(request.getParameter("minorType"));//System.out.println(txtCBMonth);
         sub=Integer.parseInt(request.getParameter("billsubtype"));//System.out.println(billmajortype);
         Sanc_OffID=Integer.parseInt(request.getParameter("officeId"));
         fin_year = request.getParameter("finyr");
  		hrdate=request.getParameter("hrdate");
  		hramt=request.getParameter("hramt");System.out.println(hramt);
  	int	note_no=Integer.parseInt(request.getParameter("note_no"));
  		txtRemarks=request.getParameter("txtRemarks");
  		headcode=Integer.parseInt(request.getParameter("headcode"));//System.out.println(sancamt);
  		String[] date=request.getParameter("hrdate").split("/");
  		int year=Integer.parseInt(date[2]);
  		int mon=Integer.parseInt(date[1]);
  		int datee=Integer.parseInt(date[0]);
  		int inc=0;
  		String note_date=datee+"-"+mon+"-"+year;
  		System.out.println("HR Note Date :::::::"+note_date);
       
   xml="<response><command>Update</command>"; 
       try 
          {
              sql="UPDATE FAS_HR_NOTE_DETAILS " +
              "  SET CASHBOOK_YEAR           =? ," +
              "  CASHBOOK_MONTH          =?, " +
"  BILL_MAJOR_TYPE_CODE    =?, " +
"  BILL_MINOR_TYPE_CODE      =?, " +
"  NOTE_DATE                 =to_date(?,'dd-mm-yyyy'), " +
"  NOTE_AMOUNT               =?, " +
"  NOTE_PREPARED_BY          =?, " +
"  ACCOUNT_HEAD_CODE         =?, " +
"  UPDATED_BY_USERID         =?, " +
"  UPDATED_DATE              =?, " +
"  BILL_SUB_TYPE_CODE        =? " +
" SANCTION_PROC_OFFICE_ID=? "+
" WHERE ACCOUNTING_UNIT_ID    =? " +
" AND ACCOUNTING_FOR_OFFICE_ID=? " +
" AND HR_NOTE_NO              =? " +
" AND FINANCIAL_YEAR          =?" ;
              ps = con.prepareStatement(sql);
              System.out.println("ps"+ps);
            
              ps.setInt(1,year);
              ps.setInt(2,mon);
            //  
              ps.setInt(3,major);
              ps.setInt(4,minor);
              ps.setString(5,hrdate);
              ps.setString(6,hramt);
              ps.setString(7,txtRemarks);
              ps.setInt(8,headcode);
              ps.setString(9,update_user);
              ps.setTimestamp(10,ts);
              ps.setInt(11,sub);
              ps.setInt(12,Sanc_OffID);
              ps.setInt(13,cmbAcc_UnitCode);     
              ps.setInt(14,cmbOffice_code);
              ps.setInt(15,note_no);
              ps.setString(16,fin_year);
             int k= ps.executeUpdate();    
               
             
          if(k>0){
        	  xml=xml+"<flag>success</flag>";         
          }else{
        	  xml=xml+"<flag>failure</flag>";
          }
          }
      catch(Exception e) 
          {   System.out.println("Error ****"+e.getMessage());  
              xml=xml+"<flag>failure</flag>";
              }
      xml=xml+"</response>";

    }
  	else if(cmd.equalsIgnoreCase("Add"))
    { 
 		                System.out.println("Inside Add"); 
 		                //int no=Integer.parseInt(request.getParameter("note_no"));
 		                major=Integer.parseInt(request.getParameter("majorType"));//System.out.println(txtCBYear);
 		                minor=Integer.parseInt(request.getParameter("minorType"));//System.out.println(txtCBMonth);
 		                sub=Integer.parseInt(request.getParameter("billsubtype"));//System.out.println(billmajortype);
 		                Sanc_OffID=Integer.parseInt(request.getParameter("officeId"));
 		              System.out.println("Sanc_OffID >> "+Sanc_OffID);
 		               fin_year = request.getParameter("finyr");
				  		hrdate=request.getParameter("hrdate");
				  		hramt=request.getParameter("hramt");System.out.println(hramt);
				  		txtRemarks=request.getParameter("txtRemarks");
				  		headcode=Integer.parseInt(request.getParameter("headcode"));//System.out.println(sancamt);
				  		String[] date=request.getParameter("hrdate").split("/");
				  		int year=Integer.parseInt(date[2]);
				  		int mon=Integer.parseInt(date[1]);
				  		int datee=Integer.parseInt(date[0]);
				  		int inc=0;
				  		String note_date=datee+"-"+mon+"-"+year;
				  		System.out.println("HR Note Date :::::::"+note_date);
				        try
				        {
                           ps=con.prepareStatement("select max(HR_NOTE_NO) from FAS_HR_NOTE_DETAILS");
                           ResultSet res=ps.executeQuery();
                           if(res.next())
                           {
                           	inc=res.getInt(1);
                           }
                           inc++;
				        }
				     catch(Exception e){
				         System.out.println("Exception is "+e);
				     }
                   xml="<response><command>Add</command>"; 
                       try 
                          {System.out.println("inside try");
                              {
                              sql="insert into FAS_HR_NOTE_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,HR_NOTE_NO,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,NOTE_DATE,NOTE_AMOUNT,NOTE_PREPARED_BY,ACCOUNT_HEAD_CODE,UPDATED_BY_USERID,UPDATED_DATE,BILL_SUB_TYPE_CODE," +
                              		"FINANCIAL_YEAR,SANCTION_PROC_OFFICE_ID,status) values(?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,'L')";
                              ps = con.prepareStatement(sql);
                              System.out.println("ps"+ps);
                              ps.setInt(1,cmbAcc_UnitCode);     
                              ps.setInt(2,cmbOffice_code);
                              ps.setInt(3,year);
                              ps.setInt(4,mon);
                              ps.setInt(5,inc);
                              ps.setInt(6,major);
                              ps.setInt(7,minor);
                              ps.setString(8,hrdate);
                              ps.setString(9,hramt);
                              ps.setString(10,txtRemarks);
                              ps.setInt(11,headcode);
                              ps.setString(12,update_user);
                              ps.setTimestamp(13,ts);
                              ps.setInt(14,sub);
                              ps.setString(15,fin_year);
                              ps.setInt(16,Sanc_OffID);
                              ps.executeUpdate();    
                              xml=xml+"<flag>success</flag>";          
                             }
                          }
                      catch(Exception e) 
                          {   System.out.println("Error ****"+e.getMessage());  
                              xml=xml+"<flag>failure</flag>";
                              }
                      xml=xml+"</response>";
   }   
      System.out.println("xml ::"+xml);
      out.println(xml);
      out.close();	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	    int cmbAcc_UnitCode = 0,cmbOffice_code=0;	    
	    
		try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try
	    {
	         cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));//System.out.println(cmbAcc_UnitCode);
	    } 
	    catch (Exception e) 
	    {
	        System.out.println("Exception to catch cmbAcc_UnitCode ");
	    }
	    try
	    {
	        cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));//System.out.println(cmbOffice_code);
	    }
	    catch (Exception e) 
	    {
	        System.out.println("Exception to catch cmbOffice_code ");
	    } 
	    String sub ="";
	    String year=request.getParameter("fin_yr");//System.out.println(year);
		int major=Integer.parseInt(request.getParameter("majorType"));//System.out.println("MMMMMMajor"+major);
	    String rtype= "PDF";//System.out.println(rtype);
		int minor=Integer.parseInt(request.getParameter("minorType"));//System.out.println(minor);
		sub=request.getParameter("billsubtype");//System.out.println(sub);
		int subb = 0;
		if (sub == null)
		{
			//System.out.println("coming herrrrrrrrrrrreeeee");
			subb =0;
		}
		else
		{	subb = Integer.parseInt(sub); }
		//System.out.println("coming      "+subb);
		/*String hrdate=request.getParameter("hr_date");//System.out.println("hr_date:::::::::::"+hrdate);
		String[] hr_date = hrdate.split("/");
		int CByear = Integer.parseInt( hr_date[2] );
		int CBmonth = Integer.parseInt( hr_date[1]) ;
		System.out.println("CB Year:::"+CByear+"CBMonth::::"+CBmonth);
		String hramt=request.getParameter("hr_amt");*/
		int headcode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
		String desc=request.getParameter("txtAcc_HeadDesc");
		int note_no=Integer.parseInt(request.getParameter("note_no"));
		File reportFile=null;
		 try {
         
                reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/CivilBills/jasper/HR_Note_Report.jasper"));
           System.out.println("path of servlet context..."+reportFile);                
           
          
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        

      //  System.out.println("opt::" + opt);
     //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
        Map map=new HashMap();
        map.put("accountingunitid",cmbAcc_UnitCode);
        map.put("accountofficeid",cmbOffice_code);
        map.put("Fin_year",year);
        /*map.put("CByear",CByear);
        map.put("CBmonth",CBmonth);     */
        map.put("billmajorcode",major);
        map.put("billminorcode",minor);
        map.put("billsubcode", subb);
        map.put("note_No",note_no);
       //System.out.println("SSSSSSSSSSSSSSSSSSSssss");
     //   System.out.println(""+cmbAcc_UnitCode+""+cmbOffice_code+""+year+""+CByear+""+CBmonth+""+major + "" +minor +""+subb);        
   
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
        if (rtype.equalsIgnoreCase("PDF"))   
        {
        	System.out.println("PDF");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);             
                    response.setHeader ("Content-Disposition", "attachment;filename=\"HRNote.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);System.out.println(buf.length);
                    out.close();
        }
     
    } catch (Exception ex) {
        String connectMsg = 
            "Could not create the report " + ex.getMessage() + " " + 
            ex.getLocalizedMessage();
        System.out.println(connectMsg);
    }
	
	}

}

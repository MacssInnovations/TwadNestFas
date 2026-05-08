package Servlets.FAS.FAS1.TDA.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class TDA_TCA_Acceptance_Create extends HttpServlet
{
    private String CONTENT_TYPE = "text/html; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
    {

  	
             /**
	       * Set Content Type 
	      */
	      PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	      
	      
	      
	      /**
	       * Session Checking 
	      */
	      HttpSession session=request.getSession(false);
	      try
	      {	           
                    if(session==null)
                    {
                             System.out.println(request.getContextPath()+"/index.jsp");
                             response.sendRedirect(request.getContextPath()+"/index.jsp");
                             return;
                    }
                    System.out.println(session);
	               
	      }catch(Exception e)
	      {
                    System.out.println("Redirect Error :"+e);
	      }
	      
	     
	      /**
	       * Variables Declaration 
	      */		        
	      Connection con=null;
	      PreparedStatement ps2=null,ps4=null;        
	      ResultSet rs2=null,rs4=null;
	      String sql="";
	        	int SLCode=0;      
	      /**
	       * Database Connection 
	      */
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
        
	    
	    
            int count=0,cmbAcc_UnitCode=0,cmbOffice_code=0,cashbook_year=0,cashbook_month=0,originated_slno=0,txtUnitId=0,supNo=0;
            String xml=null,cmd="",option="",Journal_type="",type1="",reptype="";          
      
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(Exception e){System.out.println("exception"+e );}
         //   System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(Exception e){System.out.println("exception"+e );}
         //   System.out.println("cmbOffice_code "+cmbOffice_code);
            
            try{cashbook_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
            catch(Exception e){System.out.println("exception"+e );}
         //   System.out.println("cashbook_year "+cashbook_year);
            
            try{Journal_type=request.getParameter("Journal_type");}
            catch(Exception e){System.out.println("exception"+e );}
        //    System.out.println("Journal_type "+Journal_type);
            
            try{cashbook_month=Integer.parseInt(request.getParameter("txtCB_Month"));}
            catch(Exception e){System.out.println("exception"+e );}
          //  System.out.println("cashbook_month "+cashbook_month);
            
            try{originated_slno=Integer.parseInt(request.getParameter("originated_slno"));}
            catch(Exception e){System.out.println("exception"+e );}
         //   System.out.println("originated_slno "+originated_slno);
            
            try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
            catch(Exception e){System.out.println("exception"+e );}
          //  System.out.println("txtUnitId "+txtUnitId);
            
         //   System.out.println("cmd:::"+cmd);
            xml="<response>";
            if(cmd.equalsIgnoreCase("loadVoucher"))
            {     
                try{type1=request.getParameter("type1");}
                catch(Exception e){System.out.println(e);}
                    xml=xml+"<command>loadVoucher</command>";
                    if(Journal_type.equals("TCAO")|| type1.equals("TCACB")){
                    //sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and (TDA_OR_TCA=? or TDA_OR_TCA ='TCAJ1') and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";  
                     sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";  
                    }
                    else if(Journal_type.equals("TDAO")|| type1.equals("TDACB")){
                    //sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA=? and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";  
                     sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";  
                     
                    } 
                    System.out.println("SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                             ps2.setInt(2,cashbook_year);System.out.println("cashbook_year"+cashbook_year);
                             ps2.setInt(3,cashbook_month);System.out.println("cashbook_month"+cashbook_month);
                             ps2.setString(4,type1);System.out.println("type1"+type1);
                              ps2.setString(5,Journal_type);System.out.println("Journal_type"+Journal_type);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<voucher_no>"+ rs2.getInt("VOUCHER_NO") +"</voucher_no>";	
                                    // xml+="<office_id>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</office_id>";
                                   //  xml+="<unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</unit_id>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucher..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }	
            
            if(cmd.equalsIgnoreCase("load_Vno"))
            {     
                try{type1=request.getParameter("type1");}
                catch(Exception e){System.out.println(e);}
                try{  reptype=request.getParameter("reptype");}
                catch(Exception e){System.out.println(e);}
                try{supNo=Integer.parseInt(request.getParameter("supNo"));}
                catch(Exception e){System.out.println("exception"+e );}
                
                    xml=xml+"<command>load_Vno</command>";
                    if(Journal_type.equals("TCAO")|| type1.equals("TCACB")){
                    //sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and (TDA_OR_TCA=? or TDA_OR_TCA ='TCAJ1') and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";  
                    // sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";
                    	
                    	if(reptype.equals("Regular"))
                    	{
                        sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0) AND (SUPPLEMENT_NO IS NULL OR SUPPLEMENT_NO =0) order by VOUCHER_NO";  
                    	}
                    	else if(reptype.equals("InclusiveSJV"))
                    	{
                    		sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' AND SUPPLEMENT_NO =? and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";	
                    	}
                    }
                    else if(Journal_type.equals("TDAO")|| type1.equals("TDACB")){
                    //sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA=? and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";  
                    // sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";
                    	
                    	if(reptype.equals("Regular"))
                    	{
                        sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0) AND (SUPPLEMENT_NO IS NULL OR SUPPLEMENT_NO =0) order by VOUCHER_NO";  
                    	}
                    	else if(reptype.equals("InclusiveSJV"))
                    	{
                    		sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' AND SUPPLEMENT_NO =? and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";	
                    	}
                     
                    } 
                    System.out.println("SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                             ps2.setInt(2,cashbook_year);System.out.println("cashbook_year"+cashbook_year);
                             ps2.setInt(3,cashbook_month);System.out.println("cashbook_month"+cashbook_month);
                             ps2.setString(4,type1);System.out.println("type1"+type1);
                             ps2.setString(5,Journal_type);System.out.println("Journal_type"+Journal_type);
                             
                             if(reptype.equals("InclusiveSJV"))
                             {
                             ps2.setInt(6,supNo);System.out.println("supNo"+supNo);
                             }
                             
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<voucher_no>"+ rs2.getInt("VOUCHER_NO") +"</voucher_no>";	
                                    // xml+="<office_id>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</office_id>";
                                   //  xml+="<unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</unit_id>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucher..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }	
            
            
            if(cmd.equalsIgnoreCase("load_Vno_dft"))
            {     
                try{type1=request.getParameter("type1");}
                catch(Exception e){System.out.println(e);}
                try{  reptype=request.getParameter("reptype");}
                catch(Exception e){System.out.println(e);}
               
                
                    xml=xml+"<command>load_Vno</command>";
                    if(Journal_type.equals("TCAO")|| type1.equals("TCACB")){
                    //sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and (TDA_OR_TCA=? or TDA_OR_TCA ='TCAJ1') and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";  
                     sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";
                    	
//                    	if(reptype.equals("Regular"))
//                    	{
//                        sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0) AND (SUPPLEMENT_NO IS NULL OR SUPPLEMENT_NO =0) order by VOUCHER_NO";  
//                    	}
//                    	else if(reptype.equals("InclusiveSJV"))
//                    	{
//                    		sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' AND SUPPLEMENT_NO =? and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";	
//                    	}
                    }
                    else if(Journal_type.equals("TDAO")|| type1.equals("TDACB")){
                    //sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA=? and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";  
                     sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";
                    	
//                    	if(reptype.equals("Regular"))
//                    	{
//                        sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0) AND (SUPPLEMENT_NO IS NULL OR SUPPLEMENT_NO =0) order by VOUCHER_NO";  
//                    	}
//                    	else if(reptype.equals("InclusiveSJV"))
//                    	{
//                    		sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TDA_OR_TCA in(?,?) and STATUS='L' AND SUPPLEMENT_NO =? and ORGINATING_JVR_NO is not null and (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='N') and (ACCEPTING_SLNO is null or ACCEPTING_SLNO=0)  order by VOUCHER_NO";	
//                    	}
                     
                    } 
                    System.out.println("SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                             ps2.setInt(2,cashbook_year);System.out.println("cashbook_year"+cashbook_year);
                             ps2.setInt(3,cashbook_month);System.out.println("cashbook_month"+cashbook_month);
                             ps2.setString(4,type1);System.out.println("type1"+type1);
                             ps2.setString(5,Journal_type);System.out.println("Journal_type"+Journal_type);
                             
                                                        
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<voucher_no>"+ rs2.getInt("VOUCHER_NO") +"</voucher_no>";	
                                    // xml+="<office_id>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</office_id>";
                                   //  xml+="<unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</unit_id>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucher..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            
            
            //Lakshmi
            else if (cmd.equalsIgnoreCase("check_TB")) {
               // String CONTENT_TYPE = "text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                Calendar c;
              //  String xml = "";
                Date txtCrea_date = null;
                int 
                //cmbAcc_UnitCode = 0, cmbOffice_code = 0,
                txtCash_Month_hid =      0, txtCash_year = 0;
                System.out.println("check_TB if condi");
                xml = "<response><command>check_TB</command>";
int ssupno=0;
               try {
            	   ssupno =
                            Integer.parseInt(request.getParameter("supNo"));
                } catch (NumberFormatException e) {
                    System.out.println("exception" + e);
                }
               // System.out.println("ssupno " + ssupno);

                /* try {
                    cmbOffice_code =
                            Integer.parseInt(request.getParameter("cmbOffice_code"));
                } catch (NumberFormatException e) {
                    System.out.println("exception" + e);
                }
                System.out.println("cmbOffice_code " + cmbOffice_code);
*/
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
                    // ps =  con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
                    //Lakshmi
                    ps2 =  con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS_SJV where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? and SUPPLEMENT_NO=?");
                    ps2.setInt(1, cmbAcc_UnitCode);
                    //ps.setInt(2,cmbOffice_code);
                    ps2.setInt(2, txtCash_year);
                    ps2.setInt(3, txtCash_Month_hid);
                    ps2.setInt(4, ssupno);
                    
                    rs2 = ps2.executeQuery();
                    //System.out.println(rs.next());
                    if (rs2.next()) {
                        if (rs2.getString("TB_STATUS").equalsIgnoreCase("N"))
                            xml = xml + "<flag>success</flag>";
                        else
                            xml = xml + "<flag>failure</flag>";
                    } else
                        xml = xml + "<flag>success</flag>";

                } catch (Exception e) {
                    System.out.println("catch..HERE.in TB_date " + e);
                    xml = xml + "<flag>failure</flag>";
                }
               
            }
            else if(cmd.equalsIgnoreCase("loadVoucherDetails"))
            { 
                
                try{type1=request.getParameter("type1");}
                catch(Exception e){System.out.println(e);}
                if(Journal_type.equals("TDAO") || type1.equals("TDACB")){
                 
                    xml=xml+"<command>loadVoucherDetails</command>";
                    sql="SELECT TO_CHAR(VOUCHER_DATE,'dd/mm/yyyy')AS VOUCHER_DATE,\n" + 
                    "  a.ACCOUNTING_UNIT_ID,\n" + 
                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                    "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
                    "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99'))AS AMOUNT,\n" + 
                    "  a.REASON_FOR_TRANSFER,\n" + 
                    "  b.ACCOUNTING_UNIT_NAME,\n" + 
                    "  (a.ACCOUNT_HEAD_CODE\n" + 
                    "  ||'--'\n" + 
                    "  ||c.ACCOUNT_HEAD_DESC)AS acc_head,\n" + 
                    "  a.SUB_LEDGER_TYPE_CODE,\n" + 
                    "  a.SUB_LEDGER_CODE,\n" + 
                    "  trn.MBOOK_NO,\n" + 
                    "  trn.mbook_pageno,\n" + 
                    "  to_char(trn.mbook_date,'dd/mm/yy') as mbook_date\n" + 
                    "  \n" + 
                    "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                    "  FAS_MST_ACCT_UNITS b,\n" + 
                    "  COM_MST_ACCOUNT_HEADS c\n" + 
                    "WHERE a.ACCOUNTING_UNIT_ID  =b.ACCOUNTING_UNIT_ID\n" + 
                    "AND a.ACCOUNT_HEAD_CODE     =c.ACCOUNT_HEAD_CODE\n" + 
                    "\n" + 
                    "and a.ACCOUNTING_UNIT_ID =trn.ACCOUNTING_UNIT_ID\n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                    "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
                    "a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                    "AND a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                    "AND a.CASHBOOK_YEAR         =?\n" + 
                    "AND a.CASHBOOK_MONTH        =?\n" + 
                    "AND a.VOUCHER_NO            =?\n" + 
                    "AND a.STATUS                ='L'\n" + 
                    "and trn.ACCOUNT_HEAD_CODE=900108";  
                }
                else if(Journal_type.equals("TCAO")|| type1.equals("TCACB")){
                    xml=xml+"<command>loadVoucherDetails</command>";
                    sql="SELECT TO_CHAR(VOUCHER_DATE,'dd/mm/yyyy')AS VOUCHER_DATE,\n" + 
                    "  a.ACCOUNTING_UNIT_ID,\n" + 
                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                    "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
                    "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99'))AS AMOUNT,\n" + 
                    "  a.REASON_FOR_TRANSFER,\n" + 
                    "  b.ACCOUNTING_UNIT_NAME,\n" + 
                    "  (a.ACCOUNT_HEAD_CODE\n" + 
                    "  ||'--'\n" + 
                    "  ||c.ACCOUNT_HEAD_DESC)AS acc_head,\n" + 
                    "  a.SUB_LEDGER_TYPE_CODE,\n" + 
                    "  a.SUB_LEDGER_CODE,\n" + 
                    "  trn.MBOOK_NO,\n" + 
                    "  trn.mbook_pageno,\n" + 
                    "  to_char(trn.mbook_date,'dd/mm/yy') as mbook_date\n" + 
                    "  \n" + 
                    "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                    "  FAS_MST_ACCT_UNITS b,\n" + 
                    "  COM_MST_ACCOUNT_HEADS c\n" + 
                    "WHERE a.ACCOUNTING_UNIT_ID  =b.ACCOUNTING_UNIT_ID\n" + 
                    "AND a.ACCOUNT_HEAD_CODE     =c.ACCOUNT_HEAD_CODE\n" + 
                    "\n" + 
                    "and a.ACCOUNTING_UNIT_ID =trn.ACCOUNTING_UNIT_ID\n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                    "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
                    "a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                    "AND a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                    "AND a.CASHBOOK_YEAR         =?\n" + 
                    "AND a.CASHBOOK_MONTH        =?\n" + 
                    "AND a.VOUCHER_NO            =?\n" + 
                    "AND a.STATUS                ='L'\n" + 
                    "and trn.ACCOUNT_HEAD_CODE=901001"; 
                }
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cashbook_year);
                             ps2.setInt(3,cashbook_month);
                             ps2.setInt(4,originated_slno);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                             System.out.println("inside while");
                            	     xml+="<bookno>"+rs2.getInt("MBOOK_NO")+"</bookno>";
                                     xml+="<bookPageno>"+rs2.getInt("MBOOK_PAGENO")+"</bookPageno>";
                                     xml+="<bookDate>"+rs2.getString("MBOOK_DATE")+"</bookDate>";
                            	     xml+="<voucher_date>"+rs2.getString("VOUCHER_DATE")+"</voucher_date>";	                                     			                		                
                                     xml+="<voucher_total_amount>"+rs2.getString("AMOUNT")+"</voucher_total_amount>";
                                     xml+="<office_id>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</office_id>";
                                     xml+="<unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</unit_id>";
                                     xml+="<unit_name>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</unit_name>";
                                     xml+="<acc_head>"+rs2.getString("acc_head") +"</acc_head>";
                                     xml+="<reason>"+rs2.getString("REASON_FOR_TRANSFER") +"</reason>";
                                     xml+="<sub_type>"+rs2.getString("SUB_LEDGER_TYPE_CODE") +"</sub_type>";
                                     xml+="<sub_type_code>"+rs2.getString("SUB_LEDGER_CODE") +"</sub_type_code>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucherDetails..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            else if(cmd.equalsIgnoreCase("loadVoucher_Details"))
            { 
                
                try{type1=request.getParameter("type1");}
                catch(Exception e){System.out.println(e);}
                
                try{  reptype=request.getParameter("reptype");}
                catch(Exception e){System.out.println(e);}
                try{supNo=Integer.parseInt(request.getParameter("supNo"));}
                catch(Exception e){System.out.println("exception"+e );}
                
                
                if(Journal_type.equals("TDAO") || type1.equals("TDACB")){
                	xml=xml+"<command>loadVoucher_Details</command>";
                	if(reptype.equals("Regular"))
                	{
                    
                    sql="SELECT TO_CHAR(VOUCHER_DATE,'dd/mm/yyyy')AS VOUCHER_DATE,\n" + 
                    "  a.ACCOUNTING_UNIT_ID,\n" + 
                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                    "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
                    "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99'))AS AMOUNT,\n" + 
                    "  a.REASON_FOR_TRANSFER,\n" + 
                    "  b.ACCOUNTING_UNIT_NAME,\n" + 
                    "  (a.ACCOUNT_HEAD_CODE\n" + 
                    "  ||'--'\n" + 
                    "  ||c.ACCOUNT_HEAD_DESC)AS acc_head,\n" + 
                    "  a.SUB_LEDGER_TYPE_CODE,\n" + 
                    "  a.SUB_LEDGER_CODE,\n" + 
                    "  trn.MBOOK_NO,\n" + 
                    "  trn.mbook_pageno,\n" + 
                    "  to_char(trn.mbook_date,'dd/mm/yy') as mbook_date\n" + 
                    "  \n" + 
                    "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                    "  FAS_MST_ACCT_UNITS b,\n" + 
                    "  COM_MST_ACCOUNT_HEADS c\n" + 
                    "WHERE a.ACCOUNTING_UNIT_ID  =b.ACCOUNTING_UNIT_ID\n" + 
                    "AND a.ACCOUNT_HEAD_CODE     =c.ACCOUNT_HEAD_CODE\n" + 
                    "\n" + 
                    "and a.ACCOUNTING_UNIT_ID =trn.ACCOUNTING_UNIT_ID\n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                    "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
                    "a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                    "AND a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                    "AND a.CASHBOOK_YEAR         =?\n" + 
                    "AND a.CASHBOOK_MONTH        =?\n" + 
                    "AND a.VOUCHER_NO            =?\n" + 
                    "AND a.STATUS                ='L'\n" + 
                    "and trn.ACCOUNT_HEAD_CODE=900108";  
                	}
                	else
                	{
                		 sql="SELECT TO_CHAR(VOUCHER_DATE,'dd/mm/yyyy')AS VOUCHER_DATE,\n" + 
                                 "  a.ACCOUNTING_UNIT_ID,\n" + 
                                 "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                                 "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
                                 "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99'))AS AMOUNT,\n" + 
                                 "  a.REASON_FOR_TRANSFER,\n" + 
                                 "  b.ACCOUNTING_UNIT_NAME,\n" + 
                                 "  (a.ACCOUNT_HEAD_CODE\n" + 
                                 "  ||'--'\n" + 
                                 "  ||c.ACCOUNT_HEAD_DESC)AS acc_head,\n" + 
                                 "  a.SUB_LEDGER_TYPE_CODE,\n" + 
                                 "  a.SUB_LEDGER_CODE,\n" + 
                                 "  trn.MBOOK_NO,\n" + 
                                 "  trn.mbook_pageno,\n" + 
                                 "  to_char(trn.mbook_date,'dd/mm/yy') as mbook_date\n" + 
                                 "  \n" + 
                                 "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                                 "FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                                 "  FAS_MST_ACCT_UNITS b,\n" + 
                                 "  COM_MST_ACCOUNT_HEADS c\n" + 
                                 "WHERE a.ACCOUNTING_UNIT_ID  =b.ACCOUNTING_UNIT_ID\n" + 
                                 "AND a.ACCOUNT_HEAD_CODE     =c.ACCOUNT_HEAD_CODE\n" + 
                                 "\n" + 
                                 "and a.ACCOUNTING_UNIT_ID =trn.ACCOUNTING_UNIT_ID\n" + 
                                 "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                                 "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                                 "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
                                 "a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                                 "AND a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                                 "AND a.CASHBOOK_YEAR         =?\n" + 
                                 "AND a.CASHBOOK_MONTH        =?\n" + 
                                 "AND a.VOUCHER_NO            =?\n" + 
                                 "AND a.STATUS                ='L'\n" + 
                                 "AND a.SUPPLEMENT_NO =? " +
                                 "and trn.ACCOUNT_HEAD_CODE=900108";  	
                	}
                }
                else if(Journal_type.equals("TCAO")|| type1.equals("TCACB")){
                    xml=xml+"<command>loadVoucher_Details</command>";
                    if(reptype.equals("Regular"))
                	{
                    sql="SELECT TO_CHAR(VOUCHER_DATE,'dd/mm/yyyy')AS VOUCHER_DATE,\n" + 
                    "  a.ACCOUNTING_UNIT_ID,\n" + 
                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                    "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
                    "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99'))AS AMOUNT,\n" + 
                    "  a.REASON_FOR_TRANSFER,\n" + 
                    "  b.ACCOUNTING_UNIT_NAME,\n" + 
                    "  (a.ACCOUNT_HEAD_CODE\n" + 
                    "  ||'--'\n" + 
                    "  ||c.ACCOUNT_HEAD_DESC)AS acc_head,\n" + 
                    "  a.SUB_LEDGER_TYPE_CODE,\n" + 
                    "  a.SUB_LEDGER_CODE,\n" + 
                    "  trn.MBOOK_NO,\n" + 
                    "  trn.mbook_pageno,\n" + 
                    "  to_char(trn.mbook_date,'dd/mm/yy') as mbook_date\n" + 
                    "  \n" + 
                    "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                    "  FAS_MST_ACCT_UNITS b,\n" + 
                    "  COM_MST_ACCOUNT_HEADS c\n" + 
                    "WHERE a.ACCOUNTING_UNIT_ID  =b.ACCOUNTING_UNIT_ID\n" + 
                    "AND a.ACCOUNT_HEAD_CODE     =c.ACCOUNT_HEAD_CODE\n" + 
                    "\n" + 
                    "and a.ACCOUNTING_UNIT_ID =trn.ACCOUNTING_UNIT_ID\n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                    "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
                    "a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                    "AND a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                    "AND a.CASHBOOK_YEAR         =?\n" + 
                    "AND a.CASHBOOK_MONTH        =?\n" + 
                    "AND a.VOUCHER_NO            =?\n" + 
                    "AND a.STATUS                ='L'\n" + 
                    "and trn.ACCOUNT_HEAD_CODE=901001"; 
                	}
                    else
                    {
                    	sql="SELECT TO_CHAR(VOUCHER_DATE,'dd/mm/yyyy')AS VOUCHER_DATE,\n" + 
                                "  a.ACCOUNTING_UNIT_ID,\n" + 
                                "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                                "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
                                "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99'))AS AMOUNT,\n" + 
                                "  a.REASON_FOR_TRANSFER,\n" + 
                                "  b.ACCOUNTING_UNIT_NAME,\n" + 
                                "  (a.ACCOUNT_HEAD_CODE\n" + 
                                "  ||'--'\n" + 
                                "  ||c.ACCOUNT_HEAD_DESC)AS acc_head,\n" + 
                                "  a.SUB_LEDGER_TYPE_CODE,\n" + 
                                "  a.SUB_LEDGER_CODE,\n" + 
                                "  trn.MBOOK_NO,\n" + 
                                "  trn.mbook_pageno,\n" + 
                                "  to_char(trn.mbook_date,'dd/mm/yy') as mbook_date\n" + 
                                "  \n" + 
                                "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                                "FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                                "  FAS_MST_ACCT_UNITS b,\n" + 
                                "  COM_MST_ACCOUNT_HEADS c\n" + 
                                "WHERE a.ACCOUNTING_UNIT_ID  =b.ACCOUNTING_UNIT_ID\n" + 
                                "AND a.ACCOUNT_HEAD_CODE     =c.ACCOUNT_HEAD_CODE\n" + 
                                "\n" + 
                                "and a.ACCOUNTING_UNIT_ID =trn.ACCOUNTING_UNIT_ID\n" + 
                                "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                                "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                                "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
                                "a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                                "AND a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                                "AND a.CASHBOOK_YEAR         =?\n" + 
                                "AND a.CASHBOOK_MONTH        =?\n" + 
                                "AND a.VOUCHER_NO            =?\n" + 
                                "AND a.STATUS                ='L'\n" +
                                "AND a.SUPPLEMENT_NO =? " +
                                "and trn.ACCOUNT_HEAD_CODE=901001"; 
                    }
                }
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cashbook_year);
                             ps2.setInt(3,cashbook_month);
                             ps2.setInt(4,originated_slno);
                             
                             if(reptype.equals("InclusiveSJV"))
                             {
                             ps2.setInt(5,supNo);System.out.println("supNo"+supNo);
                             }
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                             System.out.println("inside while");
                            	     xml+="<bookno>"+rs2.getInt("MBOOK_NO")+"</bookno>";
                                     xml+="<bookPageno>"+rs2.getInt("MBOOK_PAGENO")+"</bookPageno>";
                                     xml+="<bookDate>"+rs2.getString("MBOOK_DATE")+"</bookDate>";
                            	     xml+="<voucher_date>"+rs2.getString("VOUCHER_DATE")+"</voucher_date>";	                                     			                		                
                                     xml+="<voucher_total_amount>"+rs2.getString("AMOUNT")+"</voucher_total_amount>";
                                     xml+="<office_id>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</office_id>";
                                     xml+="<unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</unit_id>";
                                     xml+="<unit_name>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</unit_name>";
                                     xml+="<acc_head>"+rs2.getString("acc_head") +"</acc_head>";
                                     xml+="<reason>"+rs2.getString("REASON_FOR_TRANSFER") +"</reason>";
                                     xml+="<sub_type>"+rs2.getString("SUB_LEDGER_TYPE_CODE") +"</sub_type>";
                                     xml+="<sub_type_code>"+rs2.getString("SUB_LEDGER_CODE") +"</sub_type_code>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucherDetails..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            else if(cmd.equalsIgnoreCase("loadVoucher_Details_dft"))
            { 
                
                try{type1=request.getParameter("type1");}
                catch(Exception e){System.out.println(e);}
                
                               
                
                if(Journal_type.equals("TDAO") || type1.equals("TDACB")){
                	xml=xml+"<command>loadVoucher_Details</command>";
                	  try
                	  {
                    sql="SELECT TO_CHAR(VOUCHER_DATE,'dd/mm/yyyy')AS VOUCHER_DATE,\n" + 
                    "  a.ACCOUNTING_UNIT_ID,\n" + 
                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                    "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
                    "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99'))AS AMOUNT,\n" + 
                    "  a.REASON_FOR_TRANSFER,\n" + 
                    "  b.ACCOUNTING_UNIT_NAME,\n" + 
                    "  (a.ACCOUNT_HEAD_CODE\n" + 
                    "  ||'--'\n" + 
                    "  ||c.ACCOUNT_HEAD_DESC)AS acc_head,\n" + 
                    "  a.SUB_LEDGER_TYPE_CODE,\n" + 
                    "  a.SUB_LEDGER_CODE,\n" + 
                    "  trn.MBOOK_NO,\n" + 
                    "  trn.mbook_pageno,\n" + 
                    "  to_char(trn.mbook_date,'dd/mm/yy') as mbook_date\n" + 
                    "  \n" + 
                    "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                    "  FAS_MST_ACCT_UNITS b,\n" + 
                    "  COM_MST_ACCOUNT_HEADS c\n" + 
                    "WHERE a.ACCOUNTING_UNIT_ID  =b.ACCOUNTING_UNIT_ID\n" + 
                    "AND a.ACCOUNT_HEAD_CODE     =c.ACCOUNT_HEAD_CODE\n" + 
                    "\n" + 
                    "and a.ACCOUNTING_UNIT_ID =trn.ACCOUNTING_UNIT_ID\n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                    "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
                    "a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                    "AND a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                    "AND a.CASHBOOK_YEAR         =?\n" + 
                    "AND a.CASHBOOK_MONTH        =?\n" + 
                    "AND a.VOUCHER_NO            =?\n" + 
                    "AND a.STATUS                ='L'\n" + 
                    "and trn.ACCOUNT_HEAD_CODE=900108";  
                	  }
                	  catch(Exception e)
                	  {
                		  System.out.println("Exception====>"+e);
                	  }
                	
                }
                else if(Journal_type.equals("TCAO")|| type1.equals("TCACB")){
                    xml=xml+"<command>loadVoucher_Details</command>";
                    try
                    {
                    sql="SELECT TO_CHAR(VOUCHER_DATE,'dd/mm/yyyy')AS VOUCHER_DATE,\n" + 
                    "  a.ACCOUNTING_UNIT_ID,\n" + 
                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                    "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
                    "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99'))AS AMOUNT,\n" + 
                    "  a.REASON_FOR_TRANSFER,\n" + 
                    "  b.ACCOUNTING_UNIT_NAME,\n" + 
                    "  (a.ACCOUNT_HEAD_CODE\n" + 
                    "  ||'--'\n" + 
                    "  ||c.ACCOUNT_HEAD_DESC)AS acc_head,\n" + 
                    "  a.SUB_LEDGER_TYPE_CODE,\n" + 
                    "  a.SUB_LEDGER_CODE,\n" + 
                    "  trn.MBOOK_NO,\n" + 
                    "  trn.mbook_pageno,\n" + 
                    "  to_char(trn.mbook_date,'dd/mm/yy') as mbook_date\n" + 
                    "  \n" + 
                    "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                    "  FAS_MST_ACCT_UNITS b,\n" + 
                    "  COM_MST_ACCOUNT_HEADS c\n" + 
                    "WHERE a.ACCOUNTING_UNIT_ID  =b.ACCOUNTING_UNIT_ID\n" + 
                    "AND a.ACCOUNT_HEAD_CODE     =c.ACCOUNT_HEAD_CODE\n" + 
                    "\n" + 
                    "and a.ACCOUNTING_UNIT_ID =trn.ACCOUNTING_UNIT_ID\n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                    "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
                    "a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                    "AND a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                    "AND a.CASHBOOK_YEAR         =?\n" + 
                    "AND a.CASHBOOK_MONTH        =?\n" + 
                    "AND a.VOUCHER_NO            =?\n" + 
                    "AND a.STATUS                ='L'\n" + 
                    "and trn.ACCOUNT_HEAD_CODE=901001"; 
                    }
                    catch(Exception e)
                    {
                    	System.out.println("Exception is===>"+e);
                    }
                }
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cashbook_year);
                             ps2.setInt(3,cashbook_month);
                             ps2.setInt(4,originated_slno);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                             System.out.println("inside while");
                            	     xml+="<bookno>"+rs2.getInt("MBOOK_NO")+"</bookno>";
                                     xml+="<bookPageno>"+rs2.getInt("MBOOK_PAGENO")+"</bookPageno>";
                                     xml+="<bookDate>"+rs2.getString("MBOOK_DATE")+"</bookDate>";
                            	     xml+="<voucher_date>"+rs2.getString("VOUCHER_DATE")+"</voucher_date>";	                                     			                		                
                                     xml+="<voucher_total_amount>"+rs2.getString("AMOUNT")+"</voucher_total_amount>";
                                     xml+="<office_id>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</office_id>";
                                     xml+="<unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</unit_id>";
                                     xml+="<unit_name>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</unit_name>";
                                     xml+="<acc_head>"+rs2.getString("acc_head") +"</acc_head>";
                                     xml+="<reason>"+rs2.getString("REASON_FOR_TRANSFER") +"</reason>";
                                     xml+="<sub_type>"+rs2.getString("SUB_LEDGER_TYPE_CODE") +"</sub_type>";
                                     xml+="<sub_type_code>"+rs2.getString("SUB_LEDGER_CODE") +"</sub_type_code>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucherDetails..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            
            
            else if(cmd.equalsIgnoreCase("loadSLType"))
            {
            	    xml=xml+"<command>loadSLType</command>";
            	    try	
            	    {			        	 			  	                 		  
            	    		 sql="select trn.ACCOUNTING_UNIT_ID,trn.ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS trn,COM_MST_OFFICES mst where trn.ACCOUNTING_UNIT_OFFICE_ID=mst.OFFICE_ID and trn.ACCOUNTING_UNIT_ID=?";            	    		 
            	    		 ps2=con.prepareStatement(sql);
            	    		 ps2.setInt(1,txtUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<office_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</office_id>";	 
                                     xml+= "<office_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</office_name>";  				                		                 
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
            	    }
            	    catch(Exception e)
            	    {
            		  		 System.out.println("Err in loading loadSLType ::: "+e.getMessage());            		  	
            	    }
            }
            else if(cmd.equalsIgnoreCase("subCode"))
            {
            	try{SLCode=Integer.parseInt(request.getParameter("SLCode"));}
                catch(Exception e){System.out.println("exception"+e );}
              //  System.out.println("SLCode "+SLCode);
            	    xml=xml+"<command>subCode</command>";
            	    try	
            	    {			        	 			  	                 		  
            	    		 sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+SLCode;            	    		 
            	    		 ps2=con.prepareStatement(sql);
            	    		rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<office_id>"+ rs2.getInt("ACCOUNTING_UNIT_ID") +"</office_id>";	 
                                     xml+= "<office_name>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</office_name>";  				                		                 
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
            	    }
            	    catch(Exception e)
            	    {
            		  		 System.out.println("Err in loading loadSLType ::: "+e.getMessage());            		  	
            	    }
            }
            
           
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
            out.close();
    }
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
         
         String strCommand="";
         Connection con=null;        
         PreparedStatement ps=null,ps1=null,ps2=null;
         String xml="";
         Statement st=null;
         ResultSet rs=null;
         int master_sltype=0,master_slcode=0;
 //-----------------------------------------------------------------------------------------------        
 
         HttpSession session=request.getSession(false);
         try
         {
            
              if(session==null)
              {
                      System.out.println(request.getContextPath()+"/index.jsp");
                      response.sendRedirect(request.getContextPath()+"/index.jsp");
                      return;
              }
              System.out.println(session);
                
         }catch(Exception e)
         {
              System.out.println("Redirect Error :"+e);
         } 
        
//-----------------------------------------------------------------------------------------------        
        
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
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              st=con.createStatement();
         }
         catch(Exception e)
         {
             System.out.println("Exception in opening connection :"+e);
             //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

         }
                 
 //-----------------------------------------------------------------------------------------------        
        
        try
        {        
             strCommand=request.getParameter("Command");
             System.out.println("assign..here command..."+strCommand);
           
        }
        
        catch(Exception e) 
        {
             System.out.println("Exception in assigning..."+e);
        }
        
//-----------------------------------------------------------------------------------------------        
       
        if(strCommand.equalsIgnoreCase("Add")) 
        {
             String CONTENT_TYPE = "text/html; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
            
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
             Calendar c;
             int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtDebitHead=0;
             int count=0,originated_year=0,originated_month=0,originated_slno=0,cmbMas_SL_type=0,cmbMas_SL_Code=0,txtReason=0;
             double txtTotalAmt=0;
             Date txtCrea_date=null,originated_date=null;
             String txtRemarks="",Journal_type="",isAccept="",notAccepting_reason="",paid_to="",cr_dr_indicator="",flag="",sql="";
             
                                     // changes here
             String update_user=(String)session.getAttribute("UserId");
             long l=System.currentTimeMillis();
             Timestamp ts=new Timestamp(l);                      
             int errcode=0;
             
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                
             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             
             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             
             try{originated_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
             catch(Exception e){System.out.println("exception"+e );}
             
             try{Journal_type=request.getParameter("Journal_type");}
             catch(Exception e){System.out.println("exception"+e );}
             
             try{originated_month=Integer.parseInt(request.getParameter("txtCB_Month"));}
             catch(Exception e){System.out.println("exception"+e );}
             
             try{originated_slno=Integer.parseInt(request.getParameter("originated_slno"));}
             catch(Exception e){System.out.println("exception"+e );}
           //  System.out.println("originated_slno "+originated_slno);
             
             String[] sd=request.getParameter("originated_date").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
             java.util.Date d=c.getTime();
             originated_date=new Date(d.getTime());
             
             String[] sd1=request.getParameter("txtCrea_date").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
             java.util.Date d1=c.getTime();
             txtCrea_date=new Date(d1.getTime());
     
             try{txtCash_year=Integer.parseInt(sd1[2]);}
             catch(Exception e){System.out.println("exception"+e );}
        //     System.out.println("txtCash_year "+txtCash_year);
             
             try{txtCash_Month_hid=Integer.parseInt(sd1[1]);}
             catch(Exception e){System.out.println("exception"+e );}
        //     System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
             int accepting_SL_No=0;
             int fin_year_from=0,fin_year_to=0;
             
             //////////////////////Financial year calculation/////////////////////////////////
             if(txtCash_Month_hid>3)
             {
            	 	  fin_year_from=txtCash_year;
            	 	  fin_year_to=txtCash_year+1;
             }
             else
             {
            	 	  fin_year_from=txtCash_year-1;
            	 	  fin_year_to=txtCash_year;
             }
             try
             {
            	 	  sql="SELECT VOUCHER_NO FROM FAS_TDA_TCA_RAISED_MST GROUP BY VOUCHER_NO HAVING  "+
            	 	  	  " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TDA_TCA_RAISED_MST where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";	
                      ps=con.prepareStatement(sql);
                      ps.setInt(1,fin_year_from);
               //       System.out.println("fin_year_from============>"+fin_year_from);
                      ps.setInt(2,fin_year_to);
               //       System.out.println("fin_year_to============>"+fin_year_to);
            	 	  rs=ps.executeQuery();
                      if(rs.next()) 
                      {
                    	  accepting_SL_No = rs.getInt(1);                                               
                      }
                      accepting_SL_No=accepting_SL_No+1;
                      rs.close();
             }           
             catch(Exception e){System.out.println("exception"+e );}
          //   System.out.println("Originated_SL_No "+accepting_SL_No);
             
             try{txtReason=Integer.parseInt(request.getParameter("txtReason"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
          //   System.out.println("txtReason "+txtReason);
             
             try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
          //   System.out.println("txtUnitId "+txtUnitId);
             
             try{txtDebitHead=Integer.parseInt(request.getParameter("txtDebitHead"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
          //   System.out.println("txtDebitHead "+txtDebitHead);
          
                                             
             try{txtRemarks=request.getParameter("txtRemarks");}
             catch(NumberFormatException e){System.out.println("exception"+e );}
           //  System.out.println("txtRemarks "+txtRemarks);
             
             try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
             catch(Exception e){System.out.println("exception"+e );}
          //   System.out.println("txtAmount "+txtTotalAmt);
             
             try{Journal_type=request.getParameter("Journal_type");}
             catch(Exception e){System.out.println("Journal_type "+e );}
           //  System.out.println("Journal_type "+Journal_type);
             
             try{isAccept=request.getParameter("isAccept");}
             catch(Exception e){System.out.println("isAccept "+e );}
           //  System.out.println("isAccept "+isAccept);
             
             try{notAccepting_reason=request.getParameter("notAccepting");}
             catch(Exception e){System.out.println("notAccepting "+e );}
           //  System.out.println("notAccepting "+notAccepting_reason);
             
             if(isAccept.equals("Y"))
            	 	  notAccepting_reason="";
             if(Journal_type.equals("TDAA"))
		       	 	  cr_dr_indicator="CR";
		     else
		       	 	  cr_dr_indicator="DR";
             
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                 
             try 
             {   
            	 	  if(isAccept.equals("Y"))
		              {
		                      con.clearWarnings();
		                      con.setAutoCommit(false);
                                      
		                  String Grid_SL_type1[]=request.getParameterValues("SL_type");
		                  String Grid_SL_code1[]=request.getParameterValues("SL_code"); 
                                //  System.out.println("::::1111:::"+Grid_SL_type1[1]+":::00000::"+Grid_SL_type1[0]);
                                      
		                      ps=con.prepareStatement("insert into FAS_TDA_TCA_RAISED_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ADVICE_TYPE,VOUCHER_DATE,CR_DR_INDICATOR,ACCOUNT_HEAD_CODE,PAID_TO,TRF_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_AMOUNT,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,TDA_OR_TCA,ACCEPTANCE_STATUS) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		                      //System.out.println("insert into FAS_ADJUSTMENT_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ADJUSTMENT_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,BEHALF_OF_OFFICE_ID,PARTICULARS,TOTAL_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,VOUCHER_STATUS) values ("+cmbAcc_UnitCode+","+cmbOffice_code+",'"+txtCrea_date+"',"+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+office_id+",'"+particulars+"','"+txtTotalAmt+"','"+update_user+"','"+ts+"','L')");
		                      ps.setInt(1,cmbAcc_UnitCode);
		                      ps.setInt(2,cmbOffice_code);
		                      ps.setInt(3,txtCash_year);
		                      ps.setInt(4,txtCash_Month_hid);
		                      ps.setInt(5,accepting_SL_No);
		                      ps.setString(6,"J");
		                      ps.setDate(7,txtCrea_date);
		                      ps.setString(8,cr_dr_indicator);                      
		                      ps.setInt(9,txtDebitHead);                      
		                      ps.setString(10,paid_to);
		                      ps.setInt(11,txtUnitId);
		                      ps.setInt(12,txtReason);
                                      System.out.println("reason no error");
//		                      ps.setInt(13,cmbMas_SL_type);
//		                      ps.setInt(14,cmbMas_SL_Code);
                                     try{master_sltype=Integer.parseInt(Grid_SL_type1[0]);}
                                     catch(NumberFormatException e){System.out.println("exception"+e );}
                                      ps.setInt(13,master_sltype);
                                      System.out.println("master_sltype***"+master_sltype);
                                      
                                      try{master_slcode=Integer.parseInt(Grid_SL_code1[0]);}
                                      catch(NumberFormatException e){System.out.println("exception"+e );}
                                      ps.setInt(14,master_slcode);
                                      
		                      ps.setDouble(15,txtTotalAmt);
		                      ps.setString(16,txtRemarks);
		                      ps.setString(17,"L");
		                      ps.setString(18,update_user);
		                      ps.setTimestamp(19,ts);
		                      ps.setString(20,Journal_type);
		                      ps.setString(21,isAccept);
		                      errcode=ps.executeUpdate();
		                      if(errcode==0)
		                      {         
			                          System.out.println("redirect");	                         
			                          sendMessage(response,"Accepting Creation Failed ","ok");     
			                          
		                      }
		                      else
		                      {  
			                    	 /* if(isAccept.equals("Y"))
			                    	  {*/
		                    	  	  ps.close();
			                     //     System.out.println("inside 2 nd table");                              
			                          String Grid_H_code[]=request.getParameterValues("H_code");
			                          String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
			                          String Grid_SL_type[]=request.getParameterValues("SL_type");
			                          String Grid_SL_code[]=request.getParameterValues("SL_code");                          
			                          String Grid_sl_amt[]=request.getParameterValues("sl_amt");
			                          String Grid_particular[]=request.getParameterValues("sl_particular");                         
			                          String Trn_Paid_To[]=request.getParameterValues("Paid_To");   
			                          String grid_bookno[]=request.getParameterValues("m_bookno");                          
			                          String grid_bookpageno[]=request.getParameterValues("m_bookpageno");
			                          String grid_bookdate[]=request.getParameterValues("m_bookdate"); 
			                     //     System.out.println("2 nd table insert");
			                          Date sl_ref_date=null,bkDate=null;
			                          int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,bookPageNo=0,bookNo=0;
			                          double txtsub_Amount=0;
			                          try
			                          {
			                                      sql="insert into FAS_TDA_TCA_RAISED_TRN(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH,VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE,PAID_TO, AMOUNT, PARTICULARS, UPDATED_BY_USERID, UPDATED_DATE,MBOOK_NO,MBOOK_PAGENO,MBOOK_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
			                                      ps=con.prepareStatement(sql);
			                                      for(int k=0;k<Grid_H_code.length;k++) 
			                                      {         
			                                    	  		try
			                                    	  		{
					                                    	  		System.out.println("row"+(k+1)+"Starting");
					                                                cmbSL_type=0;cmbSL_Code=0;ref_num=0;
					                                                txtAcc_HeadCode=0;  txtsub_Amount=0; 
					                                                txtsub_Amount=0;
					                                                                                  
					                                                SL_NO++;
					                                                ps.setInt(1,cmbAcc_UnitCode);     
					                                                ps.setInt(2,cmbOffice_code);    
					                                                ps.setInt(3,txtCash_year);
					                                                ps.setInt(4,txtCash_Month_hid); 
					                                                ps.setInt(5,accepting_SL_No);       
					                                                ps.setInt(6,SL_NO);
					                                                
					                                                txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
					                                                ps.setInt(7,txtAcc_HeadCode);
					                                                
					                                                String rad_sub_CR_DR=Grid_CR_DR_type[k];                               
					                                                ps.setString(8,rad_sub_CR_DR);   
					                                                
					                                                try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setInt(9,cmbSL_type); 
					                                                
					                                                try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setInt(10,cmbSL_Code);
					                                                
					                                                ps.setString(11,Trn_Paid_To[k]);
					                                                                                   
					                                                try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setDouble(12,txtsub_Amount);
					                                                
					                                                ps.setString(13,Grid_particular[k]);      
					                                                
					                                                ps.setString(14,update_user);
					                                                ps.setTimestamp(15,ts);
					                                            //    System.out.println("------------------------"+SL_NO);
					                                                try{bookNo=Integer.parseInt(grid_bookno[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setInt(16,bookNo); 
					                                                
					                                                try{bookPageNo=Integer.parseInt(grid_bookpageno[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setInt(17,bookPageNo);
					                                              //  System.out.println("bookPageNo"+bookPageNo);
					                                                System.out.println("grid_bookdate"+grid_bookdate[k]);
					                                                try
									                                 {
//											                             	
					                                                	if(grid_bookdate[k].equalsIgnoreCase("null"))
					                                                	{
					                                                		System.out.println("nullrrrrrrrrr");
					                                                		 ps.setNull(18,java.sql.Types.DATE);
					                                                	}
					                                                	else if(grid_bookdate[k].equalsIgnoreCase(""))
					                                                	{
					                                                		System.out.println("emptyyyyyyyyyy");
					                                                		 ps.setNull(18,java.sql.Types.DATE);
					                                                	}
					                                                	else
											                                 {
											                             		 System.out.println("iffffff");
													                                 sd=grid_bookdate[k].split("/");
													                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
													                                 d=c.getTime();
													                                 bkDate=new Date(d.getTime());
													                                 ps.setDate(18,bkDate);
													                                
											                                 }
										    	                    	 
									                                 }
									                                 catch(Exception e) {
									                                     	 System.out.println(e);
									                                 }
					                                                
					                                                int i=ps.executeUpdate(); 
					                                                if(i>0)
					                                                {
						                                                    count++;
						                                                    System.out.println("inserted successfully row num :::: >>>> "+count);
					                                                }
			                                    	  		}
			                                    	  		catch(Exception ssl)
			                                    	  		{
			                                    	  				System.out.println("Exception while 2 nd table insert ::: "+ssl.getMessage());
			                                    	  		}
			                                               // System.out.println("SQL::::insert into FAS_ADJUSTMENT_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,HO_REF_NUMBER,HO_REF_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values("+cmbAcc_UnitCode+","+cmbOffice_code+","+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+SL_NO+","+txtAcc_HeadCode+",'"+rad_sub_CR_DR+"',"+cmbSL_type+","+cmbSL_Code+","+ref_num+",?,?,?,?)");                                    
		                                          }   
		                                      	  if(count==Grid_H_code.length)
		                                      				flag="success";
		                                      	  else
		                                      				flag="failure";
			                          }                           
			                          catch(Exception e)
			                          {			   
			                        	  System.out
											.println("iinsert fail FAS_TDA_TCA_RAISED_TRN");
			                                      e.getStackTrace();
			                                      //con.rollback();
			                                      sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");     
			                                        
			                          }				                         						              
		             		  }
		                      System.out.println("flag ::: "+flag);
		                      if(flag.equals("success"))
		                      {
		                    	  	  System.out.println("inside update");
			                    	  try
			                		  {
				                				  ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCEPTANCE_STATUS=?,REASON_FOR_NON_ACCEPT=?,ACCEPTING_SLNO=?,ACCEPTING_DATE=? where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
				                				  ps.setString(1,isAccept);
				                				  ps.setString(2,notAccepting_reason);
				                				  ps.setInt(3,accepting_SL_No);System.out.println("accepting_SL_No>>>>>>>>>>>>>1"+accepting_SL_No);
				                				  ps.setDate(4,txtCrea_date);System.out.println("ACCEPTING_DATE>>>>>>>>>>>>>1"+txtCrea_date);
				                				  ps.setInt(5,cmbAcc_UnitCode); System.out.println("cmbAcc_UnitCode>>>>>>>>>>>>2"+cmbAcc_UnitCode);
				                                  ps.setInt(6,originated_year);     
				                                  ps.setInt(7,originated_month);
				                                  ps.setInt(8,originated_slno);  System.out.println("originated_slno>>>>>>>>>>>>3"+originated_slno);
				                				  int kk=ps.executeUpdate();
				                				  if(kk>0)
				                				  {
				                							System.out.println("b4 commit");
				                							con.commit();
				                							sendMessage(response,"Accepting Sl.No '"+accepting_SL_No+"' has been created successfully ","ok");
				                				  }
				                				  else
				                            	  {
				                					  System.out
															.println("update fail FAS_TDA_TCA_RAISED_MST");
					                				  		//con.rollback();
					                				  		sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");           
				                            	  }
			                				
			                		  }
			                		  catch(Exception e)
			                		  {
				                				  System.out.println("Err in updation :: "+e.getMessage());
				                				  //con.rollback();
				                                  sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");     
			                                    
			                		  } 
		                      }
		                      else
		                      {
		                              System.out.println("b4 Rollback");
		                              //con.rollback();
		                              sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");     
		                      }
		              }            	 	 
                	  else
                	  {
	                		  ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCEPTANCE_STATUS=?,REASON_FOR_NON_ACCEPT=? where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
	        				  ps.setString(1,isAccept);
	        				  ps.setString(2,notAccepting_reason);
	        				  ps.setInt(3,cmbAcc_UnitCode);  
	                          ps.setInt(4,originated_year);     
	                          ps.setInt(5,originated_month);
	                          ps.setInt(6,originated_slno);  
	        				  int kk=ps.executeUpdate();
	        				  if(kk>0)
	        				  {
	        						  System.out.println("b4 commit");
	        						  //con.commit();
	        						  sendMessage(response,"Originated Sl.No '"+originated_slno+"' status has been updated successfully ","ok");
	        				  }
                	  }
             }                  
             catch(Exception e) 
             {
//		              try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
		              System.out.println(" last.....");
		              e.getStackTrace();
		              sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");
             }
             finally
             {
		              System.out.println("done");
		              try{con.setAutoCommit(true);con.close(); }catch(SQLException sqle){}
             }
	             
	                  
	                
        }
        else if(strCommand.equalsIgnoreCase("AddSupp")) 
            {
                 String CONTENT_TYPE = "text/html; charset=windows-1252";
                 response.setContentType(CONTENT_TYPE);
                
                 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                 Calendar c;
                 int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtDebitHead=0;
                 int count=0,originated_year=0,originated_month=0,originated_slno=0,cmbMas_SL_type=0,cmbMas_SL_Code=0,txtReason=0,suppno=0;
                 double txtTotalAmt=0;
                 Date txtCrea_date=null,originated_date=null;
                 String txtRemarks="",Journal_type="",isAccept="",notAccepting_reason="",paid_to="",cr_dr_indicator="",flag="",sql="";
                 
                                         // changes here
                 String update_user=(String)session.getAttribute("UserId");
                 long l=System.currentTimeMillis();
                 Timestamp ts=new Timestamp(l);                      
                 int errcode=0;
                 
                 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                    
                 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
                 
                 try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
                 
                 try{originated_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
                 catch(Exception e){System.out.println("exception"+e );}
                 
                 try{Journal_type=request.getParameter("Journal_type");}
                 catch(Exception e){System.out.println("exception"+e );}
                 
                 try{originated_month=Integer.parseInt(request.getParameter("txtCB_Month"));}
                 catch(Exception e){System.out.println("exception"+e );}
                 
                 try{originated_slno=Integer.parseInt(request.getParameter("originated_slno"));}
                 catch(Exception e){System.out.println("exception"+e );}
               //  System.out.println("originated_slno "+originated_slno);
                 
                 try{suppno=Integer.parseInt(request.getParameter("supNo"));}
                 catch(Exception e){System.out.println("exception  supNo "+e );}
                 
                 
                 
                 
                 String[] sd=request.getParameter("originated_date").split("/");
                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                 java.util.Date d=c.getTime();
                 originated_date=new Date(d.getTime());
                 
                 String[] sd1=request.getParameter("txtCrea_date").split("/");
                 c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                 java.util.Date d1=c.getTime();
                 txtCrea_date=new Date(d1.getTime());
         
                 try{txtCash_year=Integer.parseInt(sd1[2]);}
                 catch(Exception e){System.out.println("exception"+e );}
            //     System.out.println("txtCash_year "+txtCash_year);
                 
                 try{txtCash_Month_hid=Integer.parseInt(sd1[1]);}
                 catch(Exception e){System.out.println("exception"+e );}
            //     System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                 int accepting_SL_No=0;
                 int fin_year_from=0,fin_year_to=0;
                 
                 //////////////////////Financial year calculation/////////////////////////////////
                 if(txtCash_Month_hid>3)
                 {
                	 	  fin_year_from=txtCash_year;
                	 	  fin_year_to=txtCash_year+1;
                 }
                 else
                 {
                	 	  fin_year_from=txtCash_year-1;
                	 	  fin_year_to=txtCash_year;
                 }
                 try
                 {
                	 	  sql="SELECT VOUCHER_NO FROM FAS_TDA_TCA_RAISED_MST GROUP BY VOUCHER_NO HAVING  "+
                	 	  	  " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TDA_TCA_RAISED_MST where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";	
                          ps=con.prepareStatement(sql);
                          ps.setInt(1,fin_year_from);
                   //       System.out.println("fin_year_from============>"+fin_year_from);
                          ps.setInt(2,fin_year_to);
                   //       System.out.println("fin_year_to============>"+fin_year_to);
                	 	  rs=ps.executeQuery();
                          if(rs.next()) 
                          {
                        	  accepting_SL_No = rs.getInt(1);                                               
                          }
                          accepting_SL_No=accepting_SL_No+1;
                          rs.close();
                 }           
                 catch(Exception e){System.out.println("exception"+e );}
              //   System.out.println("Originated_SL_No "+accepting_SL_No);
                 
                 try{txtReason=Integer.parseInt(request.getParameter("txtReason"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
              //   System.out.println("txtReason "+txtReason);
                 
                 try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
              //   System.out.println("txtUnitId "+txtUnitId);
                 
                 try{txtDebitHead=Integer.parseInt(request.getParameter("txtDebitHead"));}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
              //   System.out.println("txtDebitHead "+txtDebitHead);
              
                                                 
                 try{txtRemarks=request.getParameter("txtRemarks");}
                 catch(NumberFormatException e){System.out.println("exception"+e );}
               //  System.out.println("txtRemarks "+txtRemarks);
                 
                 try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
                 catch(Exception e){System.out.println("exception"+e );}
              //   System.out.println("txtAmount "+txtTotalAmt);
                 
                 try{Journal_type=request.getParameter("Journal_type");}
                 catch(Exception e){System.out.println("Journal_type "+e );}
               //  System.out.println("Journal_type "+Journal_type);
                 
                 try{isAccept=request.getParameter("isAccept");}
                 catch(Exception e){System.out.println("isAccept "+e );}
               //  System.out.println("isAccept "+isAccept);
                 
                 try{notAccepting_reason=request.getParameter("notAccepting");}
                 catch(Exception e){System.out.println("notAccepting "+e );}
               //  System.out.println("notAccepting "+notAccepting_reason);
                 
                 if(isAccept.equals("Y"))
                	 	  notAccepting_reason="";
                 if(Journal_type.equals("TDAA"))
    		       	 	  cr_dr_indicator="CR";
    		     else
    		       	 	  cr_dr_indicator="DR";
                 
                 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                     
                 try 
                 {   
                	 	  if(isAccept.equals("Y"))
    		              {
    		                      con.clearWarnings();
    		                      con.setAutoCommit(false);
                                          
    		                  String Grid_SL_type1[]=request.getParameterValues("SL_type");
    		                  String Grid_SL_code1[]=request.getParameterValues("SL_code"); 
                                    //  System.out.println("::::1111:::"+Grid_SL_type1[1]+":::00000::"+Grid_SL_type1[0]);
                                          
    		                      ps=con.prepareStatement("insert into FAS_TDA_TCA_RAISED_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ADVICE_TYPE,VOUCHER_DATE,CR_DR_INDICATOR,ACCOUNT_HEAD_CODE,PAID_TO,TRF_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_AMOUNT,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,TDA_OR_TCA,ACCEPTANCE_STATUS,SUPPLEMENT_NO,ACCEPTING_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    		                      //System.out.println("insert into FAS_ADJUSTMENT_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ADJUSTMENT_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,BEHALF_OF_OFFICE_ID,PARTICULARS,TOTAL_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,VOUCHER_STATUS) values ("+cmbAcc_UnitCode+","+cmbOffice_code+",'"+txtCrea_date+"',"+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+office_id+",'"+particulars+"','"+txtTotalAmt+"','"+update_user+"','"+ts+"','L')");
    		                      ps.setInt(1,cmbAcc_UnitCode);
    		                      ps.setInt(2,cmbOffice_code);
    		                      ps.setInt(3,txtCash_year);
    		                      ps.setInt(4,txtCash_Month_hid);
    		                      ps.setInt(5,accepting_SL_No);
    		                      ps.setString(6,"J");
    		                      ps.setDate(7,txtCrea_date);
    		                      ps.setString(8,cr_dr_indicator);                      
    		                      ps.setInt(9,txtDebitHead);                      
    		                      ps.setString(10,paid_to);
    		                      ps.setInt(11,txtUnitId);
    		                      ps.setInt(12,txtReason);
                                          System.out.println("reason no error");
//    		                      ps.setInt(13,cmbMas_SL_type);
//    		                      ps.setInt(14,cmbMas_SL_Code);
                                         try{master_sltype=Integer.parseInt(Grid_SL_type1[0]);}
                                         catch(NumberFormatException e){System.out.println("exception"+e );}
                                          ps.setInt(13,master_sltype);
                                          System.out.println("master_sltype***"+master_sltype);
                                          
                                          try{master_slcode=Integer.parseInt(Grid_SL_code1[0]);}
                                          catch(NumberFormatException e){System.out.println("exception"+e );}
                                          ps.setInt(14,master_slcode);
                                          
    		                      ps.setDouble(15,txtTotalAmt);
    		                      ps.setString(16,txtRemarks);
    		                      ps.setString(17,"L");
    		                      ps.setString(18,update_user);
    		                      ps.setTimestamp(19,ts);
    		                      ps.setString(20,Journal_type);
    		                      ps.setString(21,isAccept);
    		                      ps.setInt(22,suppno);
    		                      ps.setDate(23,txtCrea_date);
    		                      errcode=ps.executeUpdate();
    		                      if(errcode==0)
    		                      {         
    			                          System.out.println("redirect");	                         
    			                          sendMessage(response,"Accepting Creation Failed ","ok");     
    			                          
    		                      }
    		                      else
    		                      {  
    			                    	 /* if(isAccept.equals("Y"))
    			                    	  {*/
    		                    	  	  ps.close();
    			                     //     System.out.println("inside 2 nd table");                              
    			                          String Grid_H_code[]=request.getParameterValues("H_code");
    			                          String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
    			                          String Grid_SL_type[]=request.getParameterValues("SL_type");
    			                          String Grid_SL_code[]=request.getParameterValues("SL_code");                          
    			                          String Grid_sl_amt[]=request.getParameterValues("sl_amt");
    			                          String Grid_particular[]=request.getParameterValues("sl_particular");                         
    			                          String Trn_Paid_To[]=request.getParameterValues("Paid_To");   
    			                          String grid_bookno[]=request.getParameterValues("m_bookno");                          
    			                          String grid_bookpageno[]=request.getParameterValues("m_bookpageno");
    			                          String grid_bookdate[]=request.getParameterValues("m_bookdate"); 
    			                     //     System.out.println("2 nd table insert");
    			                          Date sl_ref_date=null,bkDate=null;
    			                          int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,bookPageNo=0,bookNo=0;
    			                          double txtsub_Amount=0;
    			                          try
    			                          {
    			                                      sql="insert into FAS_TDA_TCA_RAISED_TRN(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH,VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE,PAID_TO, AMOUNT, PARTICULARS, UPDATED_BY_USERID, UPDATED_DATE,MBOOK_NO,MBOOK_PAGENO,MBOOK_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
    			                                      ps=con.prepareStatement(sql);
    			                                      for(int k=0;k<Grid_H_code.length;k++) 
    			                                      {         
    			                                    	  		try
    			                                    	  		{
    					                                    	  		System.out.println("row"+(k+1)+"Starting");
    					                                                cmbSL_type=0;cmbSL_Code=0;ref_num=0;
    					                                                txtAcc_HeadCode=0;  txtsub_Amount=0; 
    					                                                txtsub_Amount=0;
    					                                                                                  
    					                                                SL_NO++;
    					                                                ps.setInt(1,cmbAcc_UnitCode);     
    					                                                ps.setInt(2,cmbOffice_code);    
    					                                                ps.setInt(3,txtCash_year);
    					                                                ps.setInt(4,txtCash_Month_hid); 
    					                                                ps.setInt(5,accepting_SL_No);       
    					                                                ps.setInt(6,SL_NO);
    					                                                
    					                                                txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
    					                                                ps.setInt(7,txtAcc_HeadCode);
    					                                                
    					                                                String rad_sub_CR_DR=Grid_CR_DR_type[k];                               
    					                                                ps.setString(8,rad_sub_CR_DR);   
    					                                                
    					                                                try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
    					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
    					                                                ps.setInt(9,cmbSL_type); 
    					                                                
    					                                                try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
    					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
    					                                                ps.setInt(10,cmbSL_Code);
    					                                                
    					                                                ps.setString(11,Trn_Paid_To[k]);
    					                                                                                   
    					                                                try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
    					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
    					                                                ps.setDouble(12,txtsub_Amount);
    					                                                
    					                                                ps.setString(13,Grid_particular[k]);      
    					                                                
    					                                                ps.setString(14,update_user);
    					                                                ps.setTimestamp(15,ts);
    					                                            //    System.out.println("------------------------"+SL_NO);
    					                                                try{bookNo=Integer.parseInt(grid_bookno[k]);}
    					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
    					                                                ps.setInt(16,bookNo); 
    					                                                
    					                                                try{bookPageNo=Integer.parseInt(grid_bookpageno[k]);}
    					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
    					                                                ps.setInt(17,bookPageNo);
    					                                              //  System.out.println("bookPageNo"+bookPageNo);
    					                                                System.out.println("grid_bookdate"+grid_bookdate[k]);
    					                                                try
    									                                 {
//    											                             	
    					                                                	if(grid_bookdate[k].equalsIgnoreCase("null"))
    					                                                	{
    					                                                		System.out.println("nullrrrrrrrrr");
    					                                                		 ps.setNull(18,java.sql.Types.DATE);
    					                                                	}
    					                                                	else if(grid_bookdate[k].equalsIgnoreCase(""))
    					                                                	{
    					                                                		System.out.println("emptyyyyyyyyyy");
    					                                                		 ps.setNull(18,java.sql.Types.DATE);
    					                                                	}
    					                                                	else
    											                                 {
    											                             		 System.out.println("iffffff");
    													                                 sd=grid_bookdate[k].split("/");
    													                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
    													                                 d=c.getTime();
    													                                 bkDate=new Date(d.getTime());
    													                                 ps.setDate(18,bkDate);
    													                                
    											                                 }
    										    	                    	 
    									                                 }
    									                                 catch(Exception e) {
    									                                     	 System.out.println(e);
    									                                 }
    					                                                
    					                                                int i=ps.executeUpdate(); 
    					                                                if(i>0)
    					                                                {
    						                                                    count++;
    						                                                    System.out.println("inserted successfully row num :::: >>>> "+count);
    					                                                }
    			                                    	  		}
    			                                    	  		catch(Exception ssl)
    			                                    	  		{
    			                                    	  				System.out.println("Exception while 2 nd table insert ::: "+ssl.getMessage());
    			                                    	  		}
    			                                               // System.out.println("SQL::::insert into FAS_ADJUSTMENT_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,HO_REF_NUMBER,HO_REF_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values("+cmbAcc_UnitCode+","+cmbOffice_code+","+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+SL_NO+","+txtAcc_HeadCode+",'"+rad_sub_CR_DR+"',"+cmbSL_type+","+cmbSL_Code+","+ref_num+",?,?,?,?)");                                    
    		                                          }   
    		                                      	  if(count==Grid_H_code.length)
    		                                      				flag="success";
    		                                      	  else
    		                                      				flag="failure";
    			                          }                           
    			                          catch(Exception e)
    			                          {			                        	  		 
    			                                      e.getStackTrace();
    			                                      //con.rollback();
    			                                      sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");     
    			                                        
    			                          }				                         						              
    		             		  }
    		                      System.out.println("flag ::: "+flag);
    		                      if(flag.equals("success"))
    		                      {
    		                    	  	  System.out.println("inside update");
    			                    	  try
    			                		  {
    				                				  ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCEPTANCE_STATUS=?,REASON_FOR_NON_ACCEPT=?,ACCEPTING_SLNO=?,ACCEPTING_DATE=? where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
    				                				  ps.setString(1,isAccept);
    				                				  ps.setString(2,notAccepting_reason);
    				                				  ps.setInt(3,accepting_SL_No);System.out.println("accepting_SL_No>>>>>>>>>>>>>1"+accepting_SL_No);
    				                				  ps.setDate(4,txtCrea_date);System.out.println("ACCEPTING_DATE>>>>>>>>>>>>>1"+txtCrea_date);
    				                				  ps.setInt(5,cmbAcc_UnitCode); System.out.println("cmbAcc_UnitCode>>>>>>>>>>>>2"+cmbAcc_UnitCode);
    				                                  ps.setInt(6,originated_year);     
    				                                  ps.setInt(7,originated_month);
    				                                  ps.setInt(8,originated_slno);  System.out.println("originated_slno>>>>>>>>>>>>3"+originated_slno);
    				                				  int kk=ps.executeUpdate();
    				                				  if(kk>0)
    				                				  {
    				                							System.out.println("b4 commit");
    				                							//con.commit();
    				                							sendMessage(response,"Accepting Sl.No '"+accepting_SL_No+"' has been created successfully ","ok");
    				                				  }
    				                				  else
    				                            	  {
    				                					  
    					                				  		//con.rollback();
    					                				  		sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");           
    				                            	  }
    			                				
    			                		  }
    			                		  catch(Exception e)
    			                		  {
    				                				  System.out.println("Err in updation :: "+e.getMessage());
    				                				  //con.rollback();
    				                                  sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");     
    			                                    
    			                		  } 
    		                      }
    		                      else
    		                      {
    		                              System.out.println("b4 Rollback");
    		                              //con.rollback();
    		                              sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");     
    		                      }
    		              }            	 	 
                    	  else
                    	  {
    	                		  ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCEPTANCE_STATUS=?,REASON_FOR_NON_ACCEPT=?,ACCEPTING_DATE=? where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
    	        				  ps.setString(1,isAccept);
    	        				  ps.setString(2,notAccepting_reason);
    	        				  ps.setDate(3,txtCrea_date);
    	        				  ps.setInt(4,cmbAcc_UnitCode);  
    	                          ps.setInt(5,originated_year);     
    	                          ps.setInt(6,originated_month);
    	                          ps.setInt(7,originated_slno);  
    	        				  int kk=ps.executeUpdate();
    	        				  if(kk>0)
    	        				  {
    	        						  System.out.println("b4 commit");
    	        						  con.commit();
    	        						  sendMessage(response,"Originated Sl.No '"+originated_slno+"' status has been updated successfully ","ok");
    	        				  }
                    	  }
                 }                  
                 catch(Exception e) 
                 {
//    		              try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
    		              e.getStackTrace();
    		              sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");
                 }
                 finally
                 {
    		              System.out.println("done");
    		              try{con.setAutoCommit(true);con.close(); }catch(SQLException sqle){}
                 }
    	             
    	                  
    	                
            }
    }
   
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
                  response.sendRedirect(url);
        }
        catch(IOException e)
        {
        }
    }
}
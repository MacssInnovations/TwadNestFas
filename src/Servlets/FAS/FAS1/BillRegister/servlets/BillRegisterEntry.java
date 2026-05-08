package Servlets.FAS.FAS1.BillRegister.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class BillRegisterEntry extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String cmd,proceedingdate,billregisterdate,nameDesignation,office,remarks,finYear;
        String xml="";
        String sql="";
        int count=0,proceedno=0,unitid=0,offid=0,proceedingno=0,sanctionedamount=0,headcode=0,budgetprovision=0,budgetspent=0,subledgertype=0,subledgercode=0;
        int amountdeducted=0,budgetavailable=0,payeecode=0,billprocessing=0,billregisterno=0,cashbookyear=0,cashbookmonth=0,unitid2=0,officeid2=0,billno=0;
        cmd=request.getParameter("command");
        System.out.println(cmd);
        Connection con=null;
        PreparedStatement ps=null;
        Statement st=null;
        ResultSet result=null;
        int eid,val=0;
        System.out.println("welcome to servlet");
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
                        st=con.createStatement();
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
        try
        {
            if(session==null)
               {
                   System.out.println(request.getContextPath()+"/index.jsp");
                   response.sendRedirect(request.getContextPath()+"/index.jsp");                   
               }
               System.out.println(session);
               
        }
        catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        String userid=(String)session.getAttribute("UserId");
        System.out.println("session id is:"+userid);
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        
        if(cmd.equalsIgnoreCase("proceedingno")) 
        {
        	System.out.println("proceeding no");
            xml="<response><command>proceedingno</command>";
            try 
            {
                ps=con.prepareStatement("select SANCTION_PROC_NO from FAS_SANC_PROCEEDING_MST");
                result=ps.executeQuery();
                while(result.next()) {
                    xml=xml+"<proceedingNo>"+result.getInt("SANCTION_PROC_NO")+"</proceedingNo>";
                    System.out.println("SANCTION_PROC_NO"+result.getInt("SANCTION_PROC_NO"));
                    count++;
                }
                if(count>0) 
                xml=xml+"<flag>success</flag>";
                else
                xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
              System.out.println("Exception in proceeding:"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            
        }
        else if(cmd.equalsIgnoreCase("budgetDet"))
        {
        	System.out.println("budgetdet");
        	xml="<response><command>budgetDet</command>";
        	
        		  try{
                    unitid= Integer.parseInt(request.getParameter("unitid1"));
                    }catch(Exception e1){
                        System.out.println("Err in getting offid "+e1.getMessage());
                    }System.out.println("unitid"+unitid);
                    try
                    {
                      offid= Integer.parseInt(request.getParameter("offid1"));
                    }catch(Exception e2)
                    {
                    System.out.println("Err in getting offid "+e2.getMessage());
                    }System.out.println("offid"+offid);
                    finYear=request.getParameter("finYear");
                    System.out.println("finYear>>>>"+finYear);
                    headcode= Integer.parseInt(request.getParameter("headcode"));
                    try{
                    	sql="select CURRENT_YEAR_BUDGET_ALLOTTED,BUDGET_SOFAR_SPENT from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? AND ACCOUNT_HEAD_CODE=? ";
                    	System.out.println("sql>>>>>>"+sql);
                    	ps=con.prepareStatement(sql);
                    	ps.setInt(1,unitid);
                    	ps.setInt(2,offid);
                    	ps.setString(3,finYear);
                    	ps.setInt(4,headcode);
                    	result=ps.executeQuery();
                    	System.out.println("result"+result);
                    	while(result.next())
                    	{
                    	//	System.out.println(result.getInt("CURRENT_YEAR_BUDGET_ALLOTTED"));
                    		xml=xml+"<budgetalloted>"+result.getInt("CURRENT_YEAR_BUDGET_ALLOTTED")+"</budgetalloted>";
                            xml=xml+"<budgetspent>"+result.getInt("BUDGET_SOFAR_SPENT")+"</budgetspent>";
                            count++;
                    	}
                    	if(count>0) 
                        xml=xml+"<flag>success</flag>";
                        else
                        xml=xml+"<flag>failure</flag>";
                    }
		        	catch(Exception e)
		        	{
		        		System.out.println("exception in budget details"+e.getMessage());
		        		xml=xml+"<flag>failure</flag>";
		        	}
        	xml=xml+"</response>";
        }
        else if(cmd.equalsIgnoreCase("proceedingdate")) 
        {
            xml="<response><command>proceedingdate</command>";
            try 
            {
                proceedno= Integer.parseInt(request.getParameter("proNo"));
                ps=con.prepareStatement("select a.SANCTION_PROC_DATE,a.year,a.month,a.TOTAL_AMOUNT,b.EMPLOYEE_ID,b.EMPLOYEE_NAME from\n" + 
                "(select to_char(SANCTION_PROC_DATE,'dd/mm/yyyy')as SANCTION_PROC_DATE,extract(year from SANCTION_PROC_DATE)as year," +
                "extract(month from SANCTION_PROC_DATE)as month,TOTAL_AMOUNT,PAYEE_CODE from FAS_SANC_PROCEEDING_MST where SANCTION_PROC_NO=?)a\n" + 
                "left outer join\n" + 
                "(select EMPLOYEE_ID,EMPLOYEE_NAME from HRM_MST_EMPLOYEES)b\n" + 
                "on a.PAYEE_CODE=b.EMPLOYEE_ID");
                ps.setInt(1,proceedno);
                result=ps.executeQuery();
                while(result.next()) {
                    xml=xml+"<proceedingDate>"+result.getString("SANCTION_PROC_DATE")+"</proceedingDate>";
                    xml=xml+"<proceedingYear>"+result.getInt("year")+"</proceedingYear>";
                    xml=xml+"<proceedingMonth>"+result.getInt("month")+"</proceedingMonth>";
                    xml=xml+"<totalamount>"+result.getInt("TOTAL_AMOUNT")+"</totalamount>";
                    xml=xml+"<empName>"+result.getString("EMPLOYEE_NAME")+"</empName>";
                    xml=xml+"<empid>"+result.getInt("EMPLOYEE_ID")+"</empid>";
                    count++;
                }
                if(count>0) 
                xml=xml+"<flag>success</flag>";
                else
                xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
              System.out.println("Exception in proceeding:"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        else if(cmd.equalsIgnoreCase("authority")) 
        {
            billprocessing=Integer.parseInt(request.getParameter("billprocessing1"));
            System.out.println("billprocessing"+billprocessing);
            xml="<response><command>authority</command>";
            try 
            {
                    sql="select b.office_id,b.office_name from\n" + 
                    "(select employee_id,office_id from HRM_EMP_CURRENT_POSTING where employee_id=?)a\n" + 
                    "left outer join \n" + 
                    "(select office_id,office_name from COM_MST_OFFICES)b\n" + 
                    "on a.office_id=b.office_id";
                    ps=con.prepareStatement(sql); 
                    ps.setInt(1,billprocessing);
                    result=ps.executeQuery();
                    while(result.next()) 
                    {
                        xml=xml+"<offid>"+result.getInt("office_id")+"</offid>";
                        xml=xml+"<offName>"+result.getString("office_name")+"</offName>";
                        System.out.println("offName"+result.getString("office_name"));
                        count++;
                    }
                    if(count>0) 
                    xml=xml+"<flag>success</flag>";
                    else
                    xml=xml+"<flag>failure</flag>";
             }
            catch(Exception e) 
            {
                System.out.println("Exception in designation"+e);  
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
       else if(cmd.equalsIgnoreCase("gett")) 
        {
            unitid2=Integer.parseInt(request.getParameter("unitid1"));
            officeid2=Integer.parseInt(request.getParameter("officeid1"));
            xml="<response><command>gett</command>";   
            try
            {
               sql="select a.*,b.sub_ledger_type_desc from\n" + 
               "(select SANCTION_PROC_NO,to_char(SANCTION_PROC_DATE,'dd/mm/yyyy')as SANCTION_PROC_DATE,BILL_NO,to_char(BILL_DATE,'dd/mm/yyyy')as BILL_DATE,SUB_LEDGER_TYPE_CODE," +
               "REMARKS from FAS_BILL_REGISTER  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? order by BILL_NO)a\n" + 
               "left outer join\n" + 
               "(select sub_ledger_type_code,sub_ledger_type_desc from COM_MST_SL_TYPES)b\n" + 
               "on a.SUB_LEDGER_TYPE_CODE=b.sub_ledger_type_code";
               ps=con.prepareStatement(sql);  
               System.out.println("ps;;;;;;;;;;;;"+ps);
               ps.setInt(1,unitid2);
               ps.setInt(2,officeid2);
               System.out.println("pes"+ps);
               result=ps.executeQuery();
               System.out.println("result::::::::::::");
               System.out.println(result);
               while(result.next()) 
               {
                   xml=xml+"<proceedingno>" + result.getInt("SANCTION_PROC_NO") + "</proceedingno>";
                   xml=xml+"<proceedingdate>" + result.getString("SANCTION_PROC_DATE") + "</proceedingdate>";
                   xml=xml+"<billRegisterno>" + result.getInt("BILL_NO") + "</billRegisterno>";
                   xml=xml+"<billRegisterdate>" + result.getString("BILL_DATE") + "</billRegisterdate>";
                   xml=xml+"<ledgertype>" + result.getString("sub_ledger_type_desc") + "</ledgertype>";
                   xml=xml+"<remarks>" + result.getString("REMARKS") + "</remarks>";
                   System.out.println("xml"+xml);
                   count++;
               }
                if(count>0)
                    xml = xml+"<flag>success</flag>";
                else
                    xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
                System.out.println("Exception in Getting records ===> "+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        else if(cmd.equalsIgnoreCase("retrieve")) 
        {
        System.out.println("retrieve::::::::::::::::::::::::");
            xml="<response><command>retrieve</command>";   
            try
            {
                   billno=Integer.parseInt(request.getParameter("billno1"));
                   sql="select a.*,b.EMPLOYEE_ID,b.EMPLOYEE_NAME from    \n" + 
                   "   (select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,      \n" + 
                   "   SANCTION_PROC_NO,to_char(SANCTION_PROC_DATE,'dd/mm/yyyy')as SANCTION_PROC_DATE,PAYEE_CODE, \n" + 
                   "   to_char(BILL_DATE,'dd/mm/yyyy')as BILL_DATE,BILL_PROCESSING_DONE_BY,      \n" + 
                   "   ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CURRENT_YEAR_BUDGET_ALLOTED,BUDGET_SOFAR_SPENT,AMOUNT_FOR_THIS_BILL,  \n" + 
                   "   BUDGET_AVAILABLE,REMARKS from FAS_BILL_REGISTER where BILL_NO=?)a    \n" + 
                   "   left outer join    \n" + 
                   "   (select EMPLOYEE_ID,EMPLOYEE_NAME from HRM_MST_EMPLOYEES)b    \n" + 
                   "   on a.PAYEE_CODE=b.EMPLOYEE_ID";
                   ps=con.prepareStatement(sql);   
                   ps.setInt(1,billno);
                   result=ps.executeQuery();
                   while(result.next()) 
                   {
                       xml=xml+"<unitid>" + result.getInt("ACCOUNTING_UNIT_ID") + "</unitid>";
                       xml=xml+"<officeid>" + result.getInt("ACCOUNTING_UNIT_OFFICE_ID") + "</officeid>";
                       xml=xml+"<cashbookyear>" + result.getInt("CASHBOOK_YEAR") + "</cashbookyear>";
                       xml=xml+"<cashbookmonth>" + result.getInt("CASHBOOK_MONTH") + "</cashbookmonth>";
                       xml=xml+"<proceedingno>" + result.getInt("SANCTION_PROC_NO") + "</proceedingno>";
                       xml=xml+"<proceedingdate>" + result.getString("SANCTION_PROC_DATE") + "</proceedingdate>";
                       xml=xml+"<payeecode>" + result.getString("EMPLOYEE_NAME") + "</payeecode>";
                       xml=xml+"<billregisterdate>" + result.getString("BILL_DATE") + "</billregisterdate>";
                       xml=xml+"<billprocessing>" + result.getInt("BILL_PROCESSING_DONE_BY") + "</billprocessing>";
                       xml=xml+"<accountcode>" + result.getInt("ACCOUNT_HEAD_CODE") + "</accountcode>";
                       xml=xml+"<ledgertype>" + result.getString("SUB_LEDGER_TYPE_CODE") + "</ledgertype>";
                       xml=xml+"<ledgercode>" + result.getString("SUB_LEDGER_CODE") + "</ledgercode>";
                       xml=xml+"<budgetprovision>" + result.getInt("CURRENT_YEAR_BUDGET_ALLOTED") + "</budgetprovision>";
                       xml=xml+"<budgetspent>" + result.getInt("BUDGET_SOFAR_SPENT") + "</budgetspent>";
                       xml=xml+"<amountdeducted>" + result.getInt("AMOUNT_FOR_THIS_BILL") + "</amountdeducted>";
                       xml=xml+"<budgetavailable>" + result.getInt("BUDGET_AVAILABLE") + "</budgetavailable>";
                       xml=xml+"<remarks>" + result.getString("REMARKS") + "</remarks>";
                       count++;
                   }
                    if(count>0)
                        xml = xml+"<flag>success</flag>";
                    else
                        xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
                System.out.println("Exception in retrieving records ===> "+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";       
        }
        
        else if(cmd.equalsIgnoreCase("hcodeDet"))
        {
        	xml="<response><command>hcodeDet</command>";
        	headcode= Integer.parseInt(request.getParameter("hcode"));
        	try{
        		ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
        		ps.setInt(1,headcode);
        		result=ps.executeQuery();
        		System.out.println(result);
                while(result.next()) 
                 {
                	 xml=xml+"<accountcode>" + result.getInt("ACCOUNT_HEAD_CODE") + "</accountcode>";
                	 xml=xml+"<headdesc>" + result.getString("ACCOUNT_HEAD_DESC") + "</headdesc>";
                	 count++;
                 }
                if(count>0)
                    xml = xml+"<flag>success</flag>";
                else
                    xml=xml+"<flag>failure</flag>";
        		
        	}
        	catch(Exception e)
        	{
        		System.out.println("Exception in getting head code desc ===> "+e);
                xml=xml+"<flag>failure</flag>";
        	}
        	 xml=xml+"</response>";
        }
        
        else if(cmd.equalsIgnoreCase("ledgerType"))
        {
        	xml="<response><command>ledgerType</command>";
        	val= Integer.parseInt(request.getParameter("val1"));
        	try{
        		
        		ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
        		ps.setInt(1,val);
        		result=ps.executeQuery();
        		System.out.println(result);
                while(result.next()) 
                 {
                	 xml=xml+"<typecode>" + result.getInt("SUB_LEDGER_TYPE_CODE") + "</typecode>";
                	 xml=xml+"<typedesc>" + result.getString("SUB_LEDGER_TYPE_DESC") + "</typedesc>";
                	 count++;
                 }
                if(count>0)
                    xml = xml+"<flag>success</flag>";
                else
                    xml=xml+"<flag>failure</flag>";
        	}
        	catch(Exception e)
        	{
        		System.out.println("Exception in getting ledgerType ===> "+e);
                xml=xml+"<flag>failure</flag>";
        	}
        	 xml=xml+"</response>";
        }
        
        else if(cmd.equalsIgnoreCase("add")) 
        {
            System.out.println("add function starts");
            xml="<response><command>add</command>";
            try{
            unitid= Integer.parseInt(request.getParameter("unitid1"));
            }
            catch(Exception e1){
                System.out.println("Err in getting offid "+e1.getMessage());
            }
            System.out.println("unitid"+unitid);
            try
            {
                offid= Integer.parseInt(request.getParameter("offid1"));
            }
            catch(Exception e2)
            {
            System.out.println("Err in getting offid "+e2.getMessage());
            }
            System.out.println("offid"+offid);
            cashbookyear= Integer.parseInt(request.getParameter("cashbookyear1"));
            cashbookmonth= Integer.parseInt(request.getParameter("cashbookmonth1"));
            proceedingno= Integer.parseInt(request.getParameter("proceedingno1"));
            System.out.println("proceedingno"+proceedingno);
            proceedingdate= request.getParameter("proceedingdate1");
            payeecode= Integer.parseInt(request.getParameter("payeecode1"));
            billregisterdate= request.getParameter("billregisterdate1");
            billprocessing= Integer.parseInt(request.getParameter("billprocessing1"));
            System.out.println("billprocessing"+billprocessing);
            headcode= Integer.parseInt(request.getParameter("headcode1"));
            System.out.println("headcode::;;"+headcode);
            try{  subledgertype=Integer.parseInt(request.getParameter("subledgertype1"));}
            catch(Exception e){ System.out.println("Err in getting subledgertype "+e.getMessage()); }
            System.out.println("subledgertype"+subledgertype);
            try{subledgercode=Integer.parseInt(request.getParameter("subledgercode1"));}
            catch(Exception e){System.out.println("Err in getting subledgercode1 "+e.getMessage());}
            try{ budgetprovision=Integer.parseInt(request.getParameter("budgetprovision1"));}
            catch(Exception e){ System.out.println("Err in getting budgetprovision "+e.getMessage()); }
            try{budgetspent=Integer.parseInt(request.getParameter("budgetspent1"));}
            catch(Exception e){ System.out.println("Err in getting budgetspent "+e.getMessage());}
            try{ amountdeducted=Integer.parseInt(request.getParameter("amountdeducted1"));}
            catch(Exception e){ System.out.println("Err in getting amountdeducted "+e.getMessage());}
            try{ budgetavailable=Integer.parseInt(request.getParameter("budgetavailable1"));}
            catch(Exception e){System.out.println("Err in getting budgetavailable "+e.getMessage());}
            remarks=request.getParameter("remarks1");
            int inc=0;
            try
            {
                 st=con.createStatement();
                 result=st.executeQuery("select max(BILL_NO) from FAS_BILL_REGISTER");
                 if(result.next()) 
                 {
                   inc=result.getInt(1);
                 }
                  inc++;
                  System.out.println("inc::::"+inc);
            }
            catch(Exception e)
            {
                System.out.println("Exception in query "+e);
            }
            try 
                {
                    ps = con.prepareStatement("insert into FAS_BILL_REGISTER(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_NO,SANCTION_PROC_NO,SANCTION_PROC_DATE,PAYEE_CODE,BILL_DATE,BILL_PROCESSING_DONE_BY,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CURRENT_YEAR_BUDGET_ALLOTED,BUDGET_SOFAR_SPENT,AMOUNT_FOR_THIS_BILL,BUDGET_AVAILABLE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?)");
                    System.out.println(ps);
                    ps.setInt(1,unitid);
                    ps.setInt(2,offid);
                    ps.setInt(3,cashbookyear);
                    ps.setInt(4,cashbookmonth);
                    ps.setInt(5,inc);
                    ps.setInt(6,proceedingno);
                    ps.setString(7,proceedingdate);
                    ps.setInt(8,payeecode);
                    System.out.println("payeecode"+payeecode);
                    ps.setString(9,billregisterdate);
                    ps.setInt(10,billprocessing);
                    System.out.println("billprocessing"+billprocessing);
                    ps.setInt(11,headcode);
                    ps.setInt(12,subledgertype);
                    System.out.println("subledgertype"+subledgertype);
                    ps.setInt(13,subledgercode);
                    System.out.println("subledgercode"+subledgercode);
                    ps.setInt(14,budgetprovision);
                    ps.setInt(15, budgetspent);
                    ps.setInt(16, amountdeducted);
                    ps.setInt(17, budgetavailable);
                    ps.setString(18, remarks);
                    ps.setInt(19,eid);
                    System.out.println("eid"+eid);
                    ps.setTimestamp(20,ts);
            
                    ps.executeUpdate(); 
                    count++;
                    if(count>0)
                    	xml=xml+"<flag>success</flag>";
                    else
                    	xml=xml+"<flag>failure</flag>";	
                }
            
            catch(Exception e) 
                {
                    System.out.println("exception in add is "+e);
                    xml=xml+"<flag>failure</flag>";
                }
            xml=xml+"</response>";
           }
        
        else if(cmd.equalsIgnoreCase("updated")) 
        {
        	System.out.println("updated");
            xml="<response><command>updated</command>";
            unitid= Integer.parseInt(request.getParameter("unitid1"));
            offid= Integer.parseInt(request.getParameter("offid1"));
            proceedingno= Integer.parseInt(request.getParameter("proceedingno1"));
            System.out.println("proceedingno"+proceedingno);
            proceedingdate= request.getParameter("proceedingdate1");
            cashbookyear= Integer.parseInt(request.getParameter("cashbookyear1"));
            cashbookmonth= Integer.parseInt(request.getParameter("cashbookmonth1"));
            billregisterno= Integer.parseInt(request.getParameter("billregisterno1"));
            System.out.println("billregisterno"+billregisterno);
            billregisterdate= request.getParameter("billregisterdate1");
            payeecode= Integer.parseInt(request.getParameter("payeecode1"));
            System.out.println("payeecode"+payeecode);
            billprocessing= Integer.parseInt(request.getParameter("billprocessing1"));
            System.out.println("billprocessing"+billprocessing);
            headcode= Integer.parseInt(request.getParameter("headcode1"));
            System.out.println("headcode"+headcode);
            subledgertype= Integer.parseInt(request.getParameter("subledgertype1"));
            System.out.println("subledgertype"+subledgertype);
            subledgercode=Integer.parseInt(request.getParameter("subledgercode1"));
            System.out.println("subledgercode"+subledgercode);
            budgetprovision= Integer.parseInt(request.getParameter("budgetprovision1"));
            budgetspent= Integer.parseInt(request.getParameter("budgetspent1"));
            System.out.println("budgetspent"+budgetspent);
            amountdeducted= Integer.parseInt(request.getParameter("amountdeducted1"));
            budgetavailable= Integer.parseInt(request.getParameter("budgetavailable1"));
            remarks= request.getParameter("remarks1");
            System.out.println("rem");
            try 
                {
                    ps = con.prepareStatement("update FAS_BILL_REGISTER set SANCTION_PROC_NO=?,SANCTION_PROC_DATE=to_date(?,'dd-mm-yyyy'),BILL_DATE=to_date(?,'dd-mm-yyyy'),PAYEE_CODE=?,BILL_PROCESSING_DONE_BY=?,ACCOUNT_HEAD_CODE=?,SUB_LEDGER_TYPE_CODE=?,SUB_LEDGER_CODE=?,CURRENT_YEAR_BUDGET_ALLOTED=?,BUDGET_SOFAR_SPENT=?,AMOUNT_FOR_THIS_BILL=?,BUDGET_AVAILABLE=?,REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=?");    
                    ps.setInt(1, proceedingno);
                    System.out.println("proceedingno"+proceedingno);
                    ps.setString(2, proceedingdate);
                    System.out.println("proceedingdate"+proceedingdate);
                    ps.setString(3, billregisterdate);
                    System.out.println("billregisterdate"+billregisterdate);
                    ps.setInt(4, payeecode);
                    System.out.println("payeecode"+payeecode);
                    ps.setInt(5, billprocessing);
                    System.out.println("billprocessing"+billprocessing);
                    ps.setInt(6, headcode);
                    System.out.println("headcode"+headcode);
                    ps.setInt(7, subledgertype);
                    System.out.println("subledgertype"+subledgertype);
                    ps.setInt(8, subledgercode);
                    System.out.println("subledgercode"+subledgercode);
                    ps.setInt(9, budgetprovision); 
                    System.out.println("budgetprovision"+budgetprovision);
                    ps.setInt(10, budgetspent);
                    System.out.println("budgetspent"+budgetspent);
                    ps.setInt(11, amountdeducted);
                    System.out.println("amountdeducted"+amountdeducted);
                    ps.setInt(12, budgetavailable);
                    System.out.println("budgetavailable"+budgetavailable);
                    ps.setString(13, remarks);
                    System.out.println("remarks"+remarks);
                    ps.setInt(14, unitid);
                    System.out.println("unitid"+unitid);
                    ps.setInt(15, offid);
                    System.out.println("offid"+offid);
                    ps.setInt(16, cashbookyear);
                    System.out.println("cashbookyear"+cashbookyear);
                    ps.setInt(17, cashbookmonth);
                    System.out.println("cashbookmonth"+cashbookmonth);
                    ps.setInt(18, billregisterno);
                    System.out.println("billregisterno"+billregisterno);
                    /*
                     		ps.setInt(16,eid);
                            System.out.println("eid"+eid);
                            ps.setTimestamp(17,ts);
                     */
                    
                    int i=ps.executeUpdate();
                    System.out.println(" int i=ps.executeUpdate();"+i);
                    if(i>0) 
                    {
                            System.out.println("success");
                        xml=xml+"<flag>success</flag>"; 
                    }
                    
                }
            catch(Exception e) 
                {
                    System.out.println("exception in update is "+e.getMessage());
                    xml=xml+"<flag>failure</flag>";
                }
            xml=xml+"</response>";
            
        }
        else if(cmd.equalsIgnoreCase("deleted")) 
        {
        System.out.println("inside deleted");
            xml="<response><command>deleted</command>";
            billregisterno=Integer.parseInt(request.getParameter("billregisterno1"));
            System.out.println("billregisterno"+billregisterno);
            unitid= Integer.parseInt(request.getParameter("unitid1"));
            System.out.println("unitid"+unitid);
            offid= Integer.parseInt(request.getParameter("offid1"));
            System.out.println("offid"+offid);
            cashbookyear= Integer.parseInt(request.getParameter("cashbookyear1"));
            System.out.println("cashbookyear"+cashbookyear);
            cashbookmonth= Integer.parseInt(request.getParameter("cashbookmonth1"));
            System.out.println("cashbookmonth"+cashbookmonth);
            try 
                {
                    ps = con.prepareStatement("delete from FAS_BILL_REGISTER where BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");  
                    ps.setInt(1,billregisterno);
                    System.out.println("billregisterno"+billregisterno);
                    ps.setInt(2, unitid);
                    System.out.println("unitid"+unitid);
                    ps.setInt(3, offid);
                    System.out.println("offid"+offid);
                    ps.setInt(4, cashbookyear);
                    System.out.println("cashbookyear"+cashbookyear);
                    ps.setInt(5, cashbookmonth);
                    System.out.println("cashbookmonth"+cashbookmonth);
                    ps.executeUpdate();
                    xml = xml+"<flag>success</flag>";
                }
            catch(Exception e) 
                {
                    xml=xml+"<flag>failure</flag>";
                }
            xml=xml+"</response>";
        }
        System.out.println(xml);
        out.write(xml);
        out.close();
    }
}

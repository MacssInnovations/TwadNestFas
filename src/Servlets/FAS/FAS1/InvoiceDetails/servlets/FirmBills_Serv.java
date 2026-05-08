package Servlets.FAS.FAS1.InvoiceDetails.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.FAS.FAS1.CommonControls.servlets.Restricted_AccountHead;
import Servlets.Security.classes.UserProfile;

public class FirmBills_Serv extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {
                      response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String cmd,pay,maj,min,subb,idate,irecdate,par,bookdate,agreedate,fname,remarks,billdate;
        int major,sub,iamount,bookno,bookpageno,bud,expincurred,balanvail,workno,invoice,month,year,particularcmb;
        int unitid=0,offid=0,invoiceNo=0,billno=0,headcode=0,agreementno=0;
        String todate="",fromdate="",ida="",finYear="";
        int agreeno=0,count=0;
        String isection="",expen="";
        String xml="",sql="";;
        
        Connection con=null;
        PreparedStatement ps;
        Statement st=null;
        ResultSet result=null;
        int eid=0;
        cmd=request.getParameter("command");
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
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
         String userid=(String)session.getAttribute("UserId");
         System.out.println("session id is:"+userid);
         long l=System.currentTimeMillis();
         Timestamp ts=new Timestamp(l);
         if(cmd.equalsIgnoreCase("loadAgreeNo"))
         {
        	  try{unitid= Integer.parseInt(request.getParameter("unitid"));} 
              catch(Exception e1){System.out.println("Err in getting offid "+e1.getMessage()); }
              System.out.println("unitid"+unitid);
              try
              {offid= Integer.parseInt(request.getParameter("officeid")); }
              catch(Exception e2){ System.out.println("Err in getting offid "+e2.getMessage()); }
              
              
            xml="<response><command>loadAgreeNo</command>"; 
             try 
                     {
            	 String qry="SELECT agreement_no "+
							" FROM fas_civil_agreement "+
							" where accounting_unit_id    =?"+
							" AND accounting_for_office_id=?"+
							" AND verified                ='Y' order by agreement_no";
                             ps = con.prepareStatement(qry);
                             ps.setInt(1,unitid);
                             ps.setInt(2,offid);
                             result = ps.executeQuery();                                
                             while(result.next()) 
                             {
                                 xml=xml+"<agreement_no>"+result.getInt("agreement_no")+"</agreement_no>";
                                // xml=xml+"<masterdesc>"+result.getString("BILL_MAJOR_TYPE_DESC")+"</masterdesc>";
                                 count++;
                             }
                             if(count>0)
                                 xml=xml+"<flag>success</flag>";
                             else
                                 xml=xml+"<flag>failure</flag>";
                     }
               catch(Exception e) 
                     {
                             System.out.println("Exception in loadagree no ===> "+e);   
                             xml=xml+"<flag>failure</flag>";  
                     }
                 xml=xml+"</response>";
         }
         else  if(cmd.equalsIgnoreCase("loadAgreeNoDetail"))
         {
        	 Date ss;
       	  try{unitid= Integer.parseInt(request.getParameter("unitid"));} 
             catch(Exception e1){System.out.println("Err in getting offid "+e1.getMessage()); }
             System.out.println("unitid"+unitid);
             try
             {offid= Integer.parseInt(request.getParameter("officeid")); }
             catch(Exception e2){ System.out.println("Err in getting offid "+e2.getMessage()); }
             agreementno=Integer.parseInt(request.getParameter("agreementno"));
             
           xml="<response><command>loadAgreeNoDetail</command>"; 
            try 
                    {
           	 String qry="SELECT AGREEMENT_NO, "+
  " AGREEMENT_DATE,"+
 " work_or_supply_order_no,"+
  " sub_ledger_code, " +
 " debit_achead "+
 " ,(select account_head_desc "+
 " from com_mst_account_heads c2 where usage_status='Y' "+
 " and c2.ACCOUNT_HEAD_CODE=c1.debit_achead) as accheaddesc, "+
  		" (select c.firms_name from com_firms_sl_mst c where c.firms_id=c1.sub_ledger_code "+
" and  c.accounting_for_office_id=c1.accounting_for_office_id  "+
 " and c.accounting_unit_id=c1.accounting_unit_id)as firmname "+
" from fas_civil_agreement c1 "+
" where accounting_unit_id    =? "+
" AND ACCOUNTING_FOR_OFFICE_ID=? "+
" AND VERIFIED                ='Y' "+
"AND AGREEMENT_NO            =?  ";
                            ps = con.prepareStatement(qry);
                            ps.setInt(1,unitid);
                            ps.setInt(2,offid);
                            ps.setInt(3,agreementno);
                            result = ps.executeQuery();                                
                            while(result.next()) 
                            {
                                xml=xml+"<agreement_no>"+result.getInt("AGREEMENT_NO")+"</agreement_no>";
                                ss=result.getDate("AGREEMENT_DATE");
                                String ss1=result.getString("AGREEMENT_DATE");
                               // System.out.println("ss "+ss);
                               // System.out.println("ss 11 "+ss1);
                                
                                
                                String[] sd= ss1.split("-");
                                String[] ssd=sd[2].split(" ");
                                
                                String dd=ssd[0]+"/"+sd[1]+"/"+sd[0];
                               // System.out.println(" dd "+dd);
                                
                                
                               /* c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                                java.util.Date d=c.getTime();
                                billdate1=new Date(d.getTime());
                                System.out.println("billdate1"+billdate1);
                                */
                                
                                xml=xml+"<AGREEMENT_DATE>"+dd+"</AGREEMENT_DATE>";
                                
                                
                                xml=xml+"<work_or_supply_order_no>"+result.getInt("work_or_supply_order_no")+"</work_or_supply_order_no>";
                                xml=xml+"<sub_ledger_code>"+result.getInt("sub_ledger_code")+"</sub_ledger_code>";
                                xml=xml+"<firmname>"+result.getString("firmname")+"</firmname>";
                                xml=xml+"<accheaddesc>"+result.getString("accheaddesc")+"</accheaddesc>";
                                xml=xml+"<debit_achead>"+result.getString("debit_achead")+"</debit_achead>";
                                count++;
                            }
                            if(count>0)
                                xml=xml+"<flag>success</flag>";
                            else
                                xml=xml+"<flag>failure</flag>";
                    }
              catch(Exception e) 
                    {
                            System.out.println("Exception in loadagree detail no ===> "+e);   
                            xml=xml+"<flag>failure</flag>";  
                    }
                xml=xml+"</response>";
        }
         
         else if(cmd.equalsIgnoreCase("majorType"))
            {
               xml="<response><command>major</command>"; 
                try 
                        {
                                ps = con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
                                result = ps.executeQuery();                                
                                while(result.next()) 
                                {
                                    xml=xml+"<mastercode>"+result.getInt("BILL_MAJOR_TYPE_CODE")+"</mastercode>";
                                    xml=xml+"<masterdesc>"+result.getString("BILL_MAJOR_TYPE_DESC")+"</masterdesc>";
                                    count++;
                                }
                                if(count>0)
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
            }
        else if(cmd.equalsIgnoreCase("minorType")) 
            {
                xml="<response><command>minor</command>"; 
                try 
                        {
                            major=Integer.parseInt(request.getParameter("major2"));
                            ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major+"and status='L'");
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
            }
         
        else if(cmd.equalsIgnoreCase("billNature")) 
        {
            xml="<response><command>nature</command>"; 
            try 
                    {
                        major=Integer.parseInt(request.getParameter("major2"));
                        ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major+"and status='L'");
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
        
        else if(cmd.equalsIgnoreCase("oid")) 
            {
                xml="<response><command>oid</command>"; 
                try {
                        ps = con.prepareStatement("select SECTION_ID,SECTION_NAME from FAS_UNIT_SECTION_MST");
                        result = ps.executeQuery();
                        while(result.next()) 
                        {
                            xml=xml+"<sectionid>"+result.getInt("SECTION_ID")+"</sectionid>";
                            xml=xml+"<sectionname>"+result.getString("SECTION_NAME")+"</sectionname>";
                            count++;
                        }
                        if(count>0)
                            xml=xml+"<flag>success</flag>";
                        else
                            xml=xml+"<flag>failure</flag>";
                    }
                catch(Exception e) 
                    {
                        System.out.println("Exception in minor ===> "+e);   
                        xml=xml+"<flag>failure</flag>";  
                    }
                xml=xml+"</response>";
            }
        else if(cmd.equalsIgnoreCase("checkCode1")) {
      	  
            xml = "<response><command>checkCode1</command>";
            int txtAcc_HeadCode = 0,cmbOffice_code=0;
            try {

                txtAcc_HeadCode = Integer.parseInt(request.getParameter("txtAcc_HeadCode"));

            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            /*try {

            	cmbOffice_code =Integer.parseInt(request.getParameter("cmbOffice_code"));

            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }*/

           // Restricted_AccountHead rah = new Restricted_AccountHead();

         //   if (rah.accountHeadDetails(txtAcc_HeadCode, employee_id) == 0) {
           // System.out.println("account head code");

                try {
                    ps = con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS,sl_mandatory from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                    ps.setInt(1, txtAcc_HeadCode);
                    result = ps.executeQuery();
                    if (result.next()) {
                        xml =xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
					   result.getString("ACCOUNT_HEAD_DESC") + "</hdesc>"; 					  
                        
                    } else {
                        System.out.println("No record found");
                        xml = xml + "<flag>failure</flag>";
                    }


                } catch (Exception e) {
                    System.out.println("catch..HERE.in load head code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            /*} else {
                xml = xml + "<flag>failure</flag>";
            }*/

            xml = xml + "</response>";
            //System.out.println(xml);
            //out.println(xml);
        } 
        else if(cmd.equalsIgnoreCase("retrieve"))
                {
                        xml="<response><command>retrieve</command>";
                        try 
                            {
                        	     billno= Integer.parseInt(request.getParameter("billno"));
                                 billdate= request.getParameter("billdate");
                                 unitid= Integer.parseInt(request.getParameter("unitid"));
                                 offid= Integer.parseInt(request.getParameter("officeid"));
                                 
	 
                                 ps = con.prepareStatement("select INVOICE_NO,ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_ID,to_char(BILL_DATE,'dd/mm/yyyy')as BILL_DATE,PAYMENT_TYPE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,to_char(invoice_date,'dd/mm/yyyy')as invoice_date,to_char(invoice_received_date,'dd/mm/yyyy')as invoice_received_date,INVOICE_AMOUNT,INVOICE_PARTICULARS,to_char(M_BOOK_DATE,'dd/mm/yyyy')as M_BOOK_DATE,M_BOOK_NO,M_BOOK_PAGE_NO,INITIATING_SECTION_ID,ACCOUNT_HEAD_CODE,AGREEMENT_NO,to_char(AGREEMENT_DATE,'dd/mm/yy')as AGREEMENT_DATE,REMARKS,FIRM_NAME from FAS_INVOICE_MASTER where BILL_DATE=to_date(?,'dd-MM-yy') and BILL_NO=?");
                                 ps.setString(1,billdate);  
                                 ps.setInt(2,billno);  
                                 result = ps.executeQuery();
                                 xml=xml+"<flag>success</flag>";
                                 while(result.next())      
                                    {
                                	 xml=xml+"<ACCOUNTING_FOR_OFFICE_ID>" + result.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</ACCOUNTING_FOR_OFFICE_ID>";
                                     xml=xml+"<ACCOUNTING_UNIT_ID>" + result.getString("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";

                                	    xml=xml+"<invoiceno>" + result.getInt("INVOICE_NO") + "</invoiceno>";
                                        xml=xml+"<billdate>" + result.getString("BILL_DATE") + "</billdate>";
                                        xml=xml+"<paymenttype>" + result.getString("PAYMENT_TYPE") + "</paymenttype>";
                                        xml=xml+"<major>" + result.getString("BILL_MAJOR_TYPE_CODE") + "</major>";
                                        xml=xml+"<minor>" + result.getString("BILL_MINOR_TYPE_CODE") + "</minor>";
                                        xml=xml+"<sub>" + result.getString("BILL_SUB_TYPE_CODE") + "</sub>";
                                        xml=xml+"<invoicedate>" + result.getString("INVOICE_DATE") + "</invoicedate>";
                                       // System.out.println("invoicedate"+ result.getString("INVOICE_DATE"));
                                        xml=xml+"<invoicereceiveddate>" + result.getString("INVOICE_RECEIVED_DATE") + "</invoicereceiveddate>";
                                        xml=xml+"<invoiceamount>" + result.getInt("INVOICE_AMOUNT") + "</invoiceamount>";
                                        xml=xml+"<particulars>" + result.getString("INVOICE_PARTICULARS") + "</particulars>";
                                        xml=xml+"<bookdate>" + result.getString("M_BOOK_DATE") + "</bookdate>";
                                        xml=xml+"<bookno>" + result.getInt("M_BOOK_NO") + "</bookno>";
                                        xml=xml+"<bookpageno>" + result.getInt("M_BOOK_PAGE_NO") + "</bookpageno>";
                                        xml=xml+"<isection>" + result.getString("INITIATING_SECTION_ID") + "</isection>";
                                        xml=xml+"<headac>" + result.getString("ACCOUNT_HEAD_CODE") + "</headac>";
                                        xml=xml+"<agreeno>" + result.getInt("AGREEMENT_NO") + "</agreeno>";
                                        xml=xml+"<agreedate>" + result.getString("AGREEMENT_DATE") + "</agreedate>";
                                        xml=xml+"<remarks>" + result.getString("REMARKS") + "</remarks>";
                                        xml=xml+"<firmname>" + result.getString("FIRM_NAME") + "</firmname>";
                                        System.out.println("remarks"+ result.getString("REMARKS"));
                                    }
                            }
                        catch(Exception e1)
                            {
                                System.out.println("Exception in retrieving records ===> "+e1);
                                xml=xml+"<flag>failure</flag>";
                            }
                        xml=xml+"</response>";
                }
        else if(cmd.equalsIgnoreCase("add")) 
            {
                xml="<response><command>add</command>";
                Calendar c;
                int txtCash_Month_hid=0,txtCash_year=0;
                Date idate1=null,txtCheque_date=null,billdate1=null;
                
                try{unitid= Integer.parseInt(request.getParameter("unitid"));} 
                catch(Exception e1){System.out.println("Err in getting offid "+e1.getMessage()); }
              //  System.out.println("unitid"+unitid);
                try
                {offid= Integer.parseInt(request.getParameter("offid")); }
                catch(Exception e2){ System.out.println("Err in getting offid "+e2.getMessage()); }
               // System.out.println("offid"+offid);
                pay=request.getParameter("pay1");
                maj= request.getParameter("maj1");
                min=request.getParameter("min1");              
                subb=request.getParameter("sub1");
                invoiceNo=Integer.parseInt(request.getParameter("invoiceNo"));
                idate=request.getParameter("idate1");
              //  System.out.println("idate>>>"+idate);
                billdate=request.getParameter("billdate1");
              //  System.out.println("billdate>>>"+billdate);
                
                String[] sd=request.getParameter("billdate1").split("/");
                c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                java.util.Date d=c.getTime();
                billdate1=new Date(d.getTime());
                System.out.println("billdate1"+billdate1);
                
                System.out.println("b4 getting month and year");
                try{txtCash_year=Integer.parseInt(sd[2]);}
                catch(Exception e){System.out.println("exception"+e );}
              //  System.out.println("txtCash_year "+txtCash_year);
                
                try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                catch(Exception e){System.out.println("exception"+e );}
              //  System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                int Originated_SL_No=0;
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
                 
                irecdate=request.getParameter("irecdate1");
                iamount=Integer.parseInt(request.getParameter("iamount1"));
                par= request.getParameter("par1").trim();
                bookdate=request.getParameter("bookdate1");
                bookno=Integer.parseInt(request.getParameter("bookno1"));
                bookpageno=Integer.parseInt(request.getParameter("bookpageno1"));
                isection=request.getParameter("isection1");
                expen= request.getParameter("expen1");
//                bud=Integer.parseInt(request.getParameter("bud1"));
//                expincurred=Integer.parseInt(request.getParameter("expincurred1"));
//                balanvail=Integer.parseInt(request.getParameter("balanvail1"));
                agreeno=Integer.parseInt(request.getParameter("agreeno1"));
                agreedate=request.getParameter("agreedate1");
//                workno=Integer.parseInt(request.getParameter("workno1"));
 //               fname=request.getParameter("fname").trim();
                fname=request.getParameter("fname");
                String r1=request.getParameter("remarks1");
                remarks=r1.trim();
                Date agr_date=null;
                Calendar c1;
                //date format in yyyy-mm-dd
                /*String[] sd1=request.getParameter("agreedate1").split("-");
                c1=new GregorianCalendar(Integer.parseInt(sd1[0]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[2]));
                java.util.Date d1=c1.getTime();
                agr_date=new Date(d1.getTime());*/
                //date format indd/mm/yyyy
                String[] sd1=request.getParameter("agreedate1").split("/");
                c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                java.util.Date d1=c1.getTime();
                agr_date=new Date(d1.getTime());
                
              //  System.out.println("remarks"+remarks);
                int inc=0;
                 
                  try
                  {
                          sql="SELECT BILL_NO FROM FAS_INVOICE_MASTER GROUP BY BILL_NO " +
                         		"HAVING BILL_NO =(select max(BILL_NO) from FAS_INVOICE_MASTER " +
                         		"where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') " +
                         		"between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";   
                           ps=con.prepareStatement(sql);
                           ps.setInt(1,fin_year_from);
                           ps.setInt(2,fin_year_to);
                            result=ps.executeQuery();
                           if(result.next())
                           {
                               inc = result.getInt(1);                                              
                           }
                           inc=inc+1;
                           result.close();
                  }                  
                  catch(Exception e){System.out.println("exception"+e );}
                  System.out.println("inc "+inc);   
                
                try 
                    {
                        ps = con.prepareStatement("insert into FAS_INVOICE_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_NO,BILL_DATE,INVOICE_NO,INVOICE_DATE,PAYMENT_TYPE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,INVOICE_RECEIVED_DATE,INVOICE_AMOUNT,INVOICE_PARTICULARS,M_BOOK_NO,M_BOOK_DATE,M_BOOK_PAGE_NO,INITIATING_SECTION_ID,ACCOUNT_HEAD_CODE,AGREEMENT_NO,AGREEMENT_DATE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,FIRM_NAME)values(?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?)");
                        ps.setInt(1,unitid);
                        ps.setInt(2,offid);
                        ps.setInt(3,txtCash_year);
                        ps.setInt(4,txtCash_Month_hid);
                        ps.setInt(5,inc);
                        ps.setString(6, billdate);
                        ps.setInt(7,invoiceNo);
                        ps.setString(8, idate);
                        ps.setString(9, pay);
                        ps.setString(10, maj);
                        ps.setString(11, min);
                        ps.setString(12, subb);
                        ps.setString(13, irecdate);
                        ps.setInt(14, iamount);
                        ps.setString(15, par);
                        ps.setInt(16, bookno);
                        ps.setString(17, bookdate);
                      //  System.out.println("bookdate"+bookdate);
                        ps.setInt(18, bookpageno);
                        ps.setString(19, isection);
                        ps.setString(20, expen);
                        ps.setInt(21, agreeno);
                        ps.setDate(22, agr_date);
                        ps.setString(23, remarks);
                        ps.setInt(24,eid);
                        System.out.println("eid"+eid);
                        ps.setTimestamp(25,ts);
                        ps.setString(26,fname);
                        
                        ps.executeUpdate();    
                        System.out.println("ps is"+ps);
                        xml=xml+"<flag>success</flag>";
                        
                    }
                catch(Exception e) 
                    {
                        System.out.println("exception in add"+e);
                        xml=xml+"<flag>failure</flag>";
                    }
                xml=xml+"</response>"; 
            }
        else if(cmd.equalsIgnoreCase("updated"))
                {
        			System.out.println("updated ");
                    xml="<response><command>updated</command>";
                    Calendar c;
                    int txtCash_Month_hid=0,txtCash_year=0;
                    Date idate1=null,txtCheque_date=null,billdate1;
                    
                    try{ unitid= Integer.parseInt(request.getParameter("unitid")); } 
                    catch(Exception e1){System.out.println("Err in getting offid "+e1.getMessage()); }
                    System.out.println("unitid"+unitid);
                    try { offid= Integer.parseInt(request.getParameter("offid")); } 
                    catch(Exception e2){ System.out.println("Err in getting offid "+e2.getMessage()); }
                    
                    billno=Integer.parseInt(request.getParameter("billno"));
              //      invoice=Integer.parseInt(request.getParameter("inv1"));
                    pay=request.getParameter("pay1");
                    System.out.println("pay"+pay);
                    maj= request.getParameter("maj1");
                    min=request.getParameter("min1");              
                    subb=request.getParameter("sub1");
                  //  idate=request.getParameter("idate1");
                    
                    String[] sd=request.getParameter("billdate1").split("/");
                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                    java.util.Date d=c.getTime();
                    billdate1=new Date(d.getTime());
                   // System.out.println("billdate1 in update"+billdate1);
                    
                   // System.out.println("b4 getting month and year");
                    try{txtCash_year=Integer.parseInt(sd[2]);}
                    catch(Exception e){System.out.println("exception"+e );}
                    System.out.println("txtCash_year "+txtCash_year);
                    
                    try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                    catch(Exception e){System.out.println("exception"+e );}
                   // System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                    
                    irecdate=request.getParameter("irecdate1");
                    iamount=Integer.parseInt(request.getParameter("iamount1"));
                    par= request.getParameter("par1");
                    bookdate=request.getParameter("bookdate1");
                    bookno=Integer.parseInt(request.getParameter("bookno1"));
                    bookpageno=Integer.parseInt(request.getParameter("bookpageno1"));
                    isection=request.getParameter("isection1");
                    expen= request.getParameter("expen1");
                   // bud=Integer.parseInt(request.getParameter("bud1"));
                  //  expincurred=Integer.parseInt(request.getParameter("expincurred1"));
                 //   balanvail=Integer.parseInt(request.getParameter("balanvail1"));
                    agreeno=Integer.parseInt(request.getParameter("agreeno1"));
                    agreedate=request.getParameter("agreedate1");
                //    workno=Integer.parseInt(request.getParameter("workno1"));
                //    fname=request.getParameter("fname1");
                    remarks=request.getParameter("remarks1"); 
                    Date agr_date=null;
                    Calendar c1;
                    
                    String[] sd1=request.getParameter("agreedate1").split("/");
                    c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                    java.util.Date d1=c1.getTime();
                    agr_date=new Date(d1.getTime());
                    try {
                            ps = con.prepareStatement("update FAS_INVOICE_MASTER set PAYMENT_TYPE=?,BILL_MAJOR_TYPE_CODE=?,BILL_MINOR_TYPE_CODE=?,BILL_SUB_TYPE_CODE=?,INVOICE_RECEIVED_DATE=to_date(?,'dd-mm-yyyy'),INVOICE_AMOUNT=?,INVOICE_PARTICULARS=?,M_BOOK_DATE=to_date(?,'dd-mm-yyyy'),M_BOOK_NO=?,M_BOOK_PAGE_NO=?,INITIATING_SECTION_ID=?,ACCOUNT_HEAD_CODE=?,AGREEMENT_NO=?,AGREEMENT_DATE=to_date(?,'dd-mm-yyyy'),REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=?");    
                            ps.setString(1, pay);
                            ps.setString(2, maj);
                            ps.setString(3, min);
                            ps.setString(4, subb);
                            ps.setString(5, irecdate);
                            ps.setInt(6, iamount);
                            ps.setString(7, par);
                            ps.setString(8, bookdate);
                            ps.setInt(9, bookno);
                            ps.setInt(10, bookpageno);
                            ps.setString(11, isection);
                            ps.setString(12, expen);
                            ps.setInt(13, agreeno);
                            ps.setDate(14, agr_date);
                            ps.setString(15, remarks);
                            ps.setInt(16,eid);
                            ps.setTimestamp(17,ts);
                            ps.setInt(18,unitid);
                            ps.setInt(19,offid);
                            ps.setInt(20,txtCash_year);
                            ps.setInt(21,txtCash_Month_hid);
                            ps.setInt(22,billno);  
                            int i=ps.executeUpdate();
                            if(i>0) {
                                xml=xml+"<flag>success</flag>"; 
                            }
                            
                        }
                    catch(Exception e)
                        {
                             System.out.println("exception in update is"+e);
                             xml=xml+"<flag>failure</flag>";
                        }
                    xml=xml+"</response>";
                }
        else if(cmd.equalsIgnoreCase("del"))
                { 
                    xml="<response><command>deleted</command>";
                    Calendar c;
                    int txtCash_Month_hid=0,txtCash_year=0;
                    Date idate1=null,txtCheque_date=null,billdate1=null;
                    billno=Integer.parseInt(request.getParameter("billno"));
                //    invoice=Integer.parseInt(request.getParameter("inv1"));
                    try{
                    unitid= Integer.parseInt(request.getParameter("unitid"));
                    }  catch(Exception e1){
                        System.out.println("Err in getting offid "+e1.getMessage());
                    }
                    System.out.println("unitid"+unitid);
                    try
                    {
                        offid= Integer.parseInt(request.getParameter("offid"));
                    } catch(Exception e2)
                    {
                    System.out.println("Err in getting offid "+e2.getMessage());
                    }
                    String[] sd=request.getParameter("billdate1").split("/");
                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                    java.util.Date d=c.getTime();
                    billdate1=new Date(d.getTime());
                    System.out.println("billdate1"+billdate1);
                    
                    System.out.println("b4 getting month and year");
                    try{txtCash_year=Integer.parseInt(sd[2]);}
                    catch(Exception e){System.out.println("exception"+e );}
                    System.out.println("txtCash_year "+txtCash_year);
                            
                    try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                    catch(Exception e){System.out.println("exception"+e );}
                    System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                                
                                
                    try 
                        {
                            ps = con.prepareStatement("delete from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=?");  
                            ps.setInt(1,unitid);
                            ps.setInt(2,offid);
                            ps.setInt(3,txtCash_year);
                            ps.setInt(4,txtCash_Month_hid);
                            ps.setInt(5,billno);
                            ps.executeUpdate();
                            xml = xml+"<flag>success</flag>";
                        }
                    catch(Exception e) 
                        {
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
        else if(cmd.equalsIgnoreCase("gocmd")) 
            {
        	System.out.println("gocommand");
               xml="<response><command>gobutton1</command>";
                try 
                    {                        
                        year=Integer.parseInt(request.getParameter("yr"));
                        month=Integer.parseInt(request.getParameter("mon"));
                        unitid=Integer.parseInt(request.getParameter("unitid1"));
                        offid=Integer.parseInt(request.getParameter("officeid1"));
                        
                        ps = con.prepareStatement("select a.invoice_no,a.ACCOUNTING_FOR_OFFICE_ID,a.ACCOUNTING_UNIT_ID,a.invoice_date,a.BILL_NO,a.BILL_DATE,a.FIRM_NAME,a.INVOICE_AMOUNT,b.BILL_MAJOR_TYPE_DESC,c.BILL_MINOR_TYPE_DESC,d.BILL_SUB_TYPE_DESC,a.INVOICE_PARTICULARS,a.AGREEMENT_NO,a.AGREEMENT_DATE," +
                        		"a.REMARKS from (select BILL_NO,to_char(BILL_DATE,'dd/mm/yyyy')as BILL_DATE,ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_ID,INVOICE_NO,to_char(INVOICE_DATE,'dd/mm/yyyy')as INVOICE_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,INVOICE_PARTICULARS,AGREEMENT_NO,to_char(AGREEMENT_DATE,'dd/mm/yyyy')as AGREEMENT_DATE,REMARKS,FIRM_NAME,INVOICE_AMOUNT from FAS_INVOICE_MASTER " +
                        		"where extract(year from BILL_DATE)=? and extract(month from BILL_DATE)=?  and ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=?)a left outer join" +
                        		"(select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE)b on a.BILL_MAJOR_TYPE_CODE=b.BILL_MAJOR_TYPE_CODE left outer join (select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC " +
                        		"from FAS_BILL_MINOR_TYPES_MST where status='L')c on a.BILL_MAJOR_TYPE_CODE=c.BILL_MAJOR_TYPE_CODE and  a.BILL_MINOR_TYPE_CODE=c.BILL_MINOR_TYPE_CODE left outer join(select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC " +
                        		"from FAS_BILL_SUB_TYPES where status='L')d on a.BILL_MAJOR_TYPE_CODE=d.BILL_MAJOR_TYPE_CODE and  a.BILL_MINOR_TYPE_CODE=d.BILL_MINOR_TYPE_CODE and a.BILL_SUB_TYPE_CODE=d.BILL_SUB_TYPE_CODE order by a.BILL_NO,a.BILL_DATE");
                        System.out.println("ps "+ps);
                        ps.setInt(1,year);
                        ps.setInt(2,month);
                        ps.setInt(3,unitid);
                        ps.setInt(4,offid);
                        
                        result = ps.executeQuery();
                        System.out.println("result"+result);
                        while(result.next())      
                            {
                        	 xml=xml+"<ACCOUNTING_FOR_OFFICE_ID>" + result.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</ACCOUNTING_FOR_OFFICE_ID>";
                             xml=xml+"<ACCOUNTING_UNIT_ID>" + result.getString("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
                             
                        	    xml=xml+"<billno>" + result.getInt("BILL_NO") + "</billno>";
                                xml=xml+"<billdate>" + result.getString("BILL_DATE") + "</billdate>";
                                xml=xml+"<invoiceno>" + result.getInt("INVOICE_NO") + "</invoiceno>";
                                xml=xml+"<invoicedate>" + result.getString("invoice_date") + "</invoicedate>";
                                xml=xml+"<marterdesc>" + result.getString("BILL_MAJOR_TYPE_DESC") + "</marterdesc>";
                                xml=xml+"<minor>" + result.getString("BILL_MINOR_TYPE_DESC") + "</minor>";
                                xml=xml+"<sub>" + result.getString("BILL_SUB_TYPE_DESC") + "</sub>";
                                xml=xml+"<invoiceparti>" + result.getString("INVOICE_PARTICULARS") + "</invoiceparti>";
                                xml=xml+"<agreeno>" + result.getInt("AGREEMENT_NO") + "</agreeno>";
                                xml=xml+"<agreedate>" + result.getString("AGREEMENT_DATE") + "</agreedate>";
                                xml=xml+"<remarks>" + result.getString("REMARKS") + "</remarks>";
                                xml=xml+"<invoiceAmt>" + result.getInt("INVOICE_AMOUNT") + "</invoiceAmt>";
                                xml=xml+"<firmname>" + result.getString("FIRM_NAME") + "</firmname>";
                                count++;
                            }
                            if(count>0)
                                xml = xml+"<flag>success</flag>";
                            else
                                xml=xml+"<flag>failure</flag>";
                    }
                catch(Exception e) 
                    {
                        System.out.println(" err in retriving records based on date selection ::: "+e.getMessage());
                        xml=xml+"<flag>failure</flag>";
                    }
                xml=xml+"</response>";
            }
          
        else if(cmd.equalsIgnoreCase("callino")) 
            {
               xml="<response><command>callino</command>";
                try 
                    {                        
                        particularcmb=Integer.parseInt(request.getParameter("particularcmb"));
                        unitid=Integer.parseInt(request.getParameter("unitid1"));
                        offid=Integer.parseInt(request.getParameter("officeid1"));
                        
                        ps = con.prepareStatement("select a.invoice_no,a.ACCOUNTING_FOR_OFFICE_ID,a.ACCOUNTING_UNIT_ID,a.invoice_date,a.BILL_NO,a.BILL_DATE,a.FIRM_NAME,a.INVOICE_AMOUNT,b.BILL_MAJOR_TYPE_DESC,c.BILL_MINOR_TYPE_DESC,d.BILL_SUB_TYPE_DESC,a.INVOICE_PARTICULARS,a.AGREEMENT_NO,a.AGREEMENT_DATE,a.REMARKS from(select BILL_NO,ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_ID,to_char(BILL_DATE,'dd/mm/yyyy')as BILL_DATE,invoice_no,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,to_char(invoice_date,'dd/mm/yyyy')as invoice_date,INVOICE_PARTICULARS,AGREEMENT_NO,to_char(AGREEMENT_DATE,'dd/mm/yyyy')as AGREEMENT_DATE,REMARKS,INVOICE_AMOUNT,FIRM_NAME from FAS_INVOICE_MASTER where BILL_NO=?)a left outer join (select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE)b on a.BILL_MAJOR_TYPE_CODE=b.BILL_MAJOR_TYPE_CODE left outer join (select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where status='L')c on a.BILL_MAJOR_TYPE_CODE=c.BILL_MAJOR_TYPE_CODE and  a.BILL_MINOR_TYPE_CODE=c.BILL_MINOR_TYPE_CODE left outer join (select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where status='L')d on a.BILL_MAJOR_TYPE_CODE=d.BILL_MAJOR_TYPE_CODE and  a.BILL_MINOR_TYPE_CODE=d.BILL_MINOR_TYPE_CODE and a.BILL_SUB_TYPE_CODE=d.BILL_SUB_TYPE_CODE order by a.BILL_NO,a.BILL_DATE");
                        System.out.println("ps"+ps);
                        ps.setInt(1,particularcmb);
                        result = ps.executeQuery();                                
                        while(result.next())      
                            {
                        	
                        	 xml=xml+"<ACCOUNTING_FOR_OFFICE_ID>" + result.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</ACCOUNTING_FOR_OFFICE_ID>";
                             xml=xml+"<ACCOUNTING_UNIT_ID>" + result.getString("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
                             
                        	xml=xml+"<billno>" + result.getInt("BILL_NO") + "</billno>";
                            xml=xml+"<billdate>" + result.getString("BILL_DATE") + "</billdate>";
                            xml=xml+"<invoiceno>" + result.getInt("INVOICE_NO") + "</invoiceno>";
                            xml=xml+"<invoicedate>" + result.getString("invoice_date") + "</invoicedate>";
                            xml=xml+"<marterdesc>" + result.getString("BILL_MAJOR_TYPE_DESC") + "</marterdesc>";
                            xml=xml+"<minor>" + result.getString("BILL_MINOR_TYPE_DESC") + "</minor>";
                            xml=xml+"<sub>" + result.getString("BILL_SUB_TYPE_DESC") + "</sub>";
                            xml=xml+"<invoiceparti>" + result.getString("INVOICE_PARTICULARS") + "</invoiceparti>";
                            xml=xml+"<agreeno>" + result.getInt("AGREEMENT_NO") + "</agreeno>";
                            xml=xml+"<agreedate>" + result.getString("AGREEMENT_DATE") + "</agreedate>";
                            xml=xml+"<remarks>" + result.getString("REMARKS") + "</remarks>";
                            xml=xml+"<invoiceAmt>" + result.getInt("INVOICE_AMOUNT") + "</invoiceAmt>";
                            xml=xml+"<firmname>" + result.getString("FIRM_NAME") + "</firmname>";
                            count++;
                            }
                        if(count>0)
                            xml = xml+"<flag>success</flag>";
                        else
                            xml=xml+"<flag>failure</flag>";
                    }
                catch(Exception e) 
                    {
                        System.out.println(" err in retriving records based on date selection ::: "+e.getMessage());
                        xml=xml+"<flag>failure</flag>";
                    }
                xml=xml+"</response>";
            }
              
        
        else if(cmd.equalsIgnoreCase("particularcommand")) 
            {
                xml="<response><command>particularcommand</command>";
                try 
                    {       
                        fromdate=request.getParameter("fromdate");
                        System.out.println("fromdate is"+fromdate);
                        todate=request.getParameter("todate");
                        unitid=Integer.parseInt(request.getParameter("unitid1"));
                        offid=Integer.parseInt(request.getParameter("officeid1"));
                        
                        ps = con.prepareStatement("select BILL_NO from FAS_INVOICE_MASTER  WHERE BILL_DATE BETWEEN to_date(?, 'dd-MM-yyyy') AND to_date(?, 'dd-MM-yyyy') and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? order by BILL_NO");
                        ps.setString(1,fromdate);
                        ps.setString(2,todate);
                        ps.setInt(3,unitid);
                        ps.setInt(4, offid);
                        result = ps.executeQuery();
                        while(result.next())      
                            {
                               System.out.println("result is:::::::::::::::::::");
                                xml=xml+"<billno>" + result.getInt("BILL_NO") + "</billno>";
                                System.out.println("BILL_NO is"+result.getInt("BILL_NO"));
                                count++;
                            }
                            if(count>0)
                                xml = xml+"<flag>success</flag>";
                            else
                                xml=xml+"<flag>failure</flag>";
                    }
                catch(Exception e) 
                    {
                        System.out.println("Exception is"+e.getMessage());
                        xml=xml+"<flag>failure</flag>";
                    }
                xml=xml+"</response>"; 
            }
            
         else if(cmd.equalsIgnoreCase("displaycmd")) 
            {
        	 System.out.println("displaycmd");
                xml="<response><command>gobutton2</command>";
                 try 
                     {                        
                         fromdate=request.getParameter("fromdate");
                         todate=request.getParameter("todate");
                         unitid=Integer.parseInt(request.getParameter("unitid1"));
                         offid=Integer.parseInt(request.getParameter("officeid1"));
                         ps = con.prepareStatement("select a.invoice_no,a.ACCOUNTING_FOR_OFFICE_ID,a.ACCOUNTING_UNIT_ID,a.invoice_date,a.FIRM_NAME,a.INVOICE_AMOUNT,a.BILL_NO,a.BILL_DATE,b.BILL_MAJOR_TYPE_DESC,c.BILL_MINOR_TYPE_DESC,d.BILL_SUB_TYPE_DESC,a.INVOICE_PARTICULARS,a.AGREEMENT_NO,a.AGREEMENT_DATE,a.REMARKS from (select BILL_NO,ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_ID,to_char(BILL_DATE,'dd/mm/yyyy')as BILL_DATE,invoice_no,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,to_char(invoice_date,'dd/mm/yyyy')as invoice_date,INVOICE_PARTICULARS,AGREEMENT_NO,to_char(AGREEMENT_DATE,'dd/mm/yyyy')as AGREEMENT_DATE,REMARKS,INVOICE_AMOUNT,FIRM_NAME from FAS_INVOICE_MASTER WHERE invoice_date BETWEEN to_date(?,'dd-MM-yyyy') AND to_date(?,'dd-MM-yyyy') and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?)a left outer join (select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE)b on a.BILL_MAJOR_TYPE_CODE=b.BILL_MAJOR_TYPE_CODE left outer join (select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where status='L')c on a.BILL_MAJOR_TYPE_CODE=c.BILL_MAJOR_TYPE_CODE and  a.BILL_MINOR_TYPE_CODE=c.BILL_MINOR_TYPE_CODE left outer join (select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where status='L')d on a.BILL_MAJOR_TYPE_CODE=d.BILL_MAJOR_TYPE_CODE and  a.BILL_MINOR_TYPE_CODE=d.BILL_MINOR_TYPE_CODE and a.BILL_SUB_TYPE_CODE=d.BILL_SUB_TYPE_CODE order by a.BILL_NO,a.BILL_DATE ");
						 System.out.println("ps"+ps);
                         ps.setString(1,fromdate);
                         System.out.println("fromdate"+fromdate);
                         ps.setString(2,todate);
                         ps.setInt(3, unitid);
                         ps.setInt(4,offid);
                         
                         System.out.println("todate"+todate);
                         result = ps.executeQuery();
                         System.out.println("res"+result);
                         while(result.next())      
                             {
                        	 xml=xml+"<ACCOUNTING_FOR_OFFICE_ID>" + result.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</ACCOUNTING_FOR_OFFICE_ID>";
                             xml=xml+"<ACCOUNTING_UNIT_ID>" + result.getString("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
                             
                        	 xml=xml+"<billno>" + result.getInt("BILL_NO") + "</billno>";
                             xml=xml+"<billdate>" + result.getString("BILL_DATE") + "</billdate>";
                             xml=xml+"<invoiceno>" + result.getInt("INVOICE_NO") + "</invoiceno>";
                             xml=xml+"<invoicedate>" + result.getString("invoice_date") + "</invoicedate>";
                             xml=xml+"<marterdesc>" + result.getString("BILL_MAJOR_TYPE_DESC") + "</marterdesc>";
                             xml=xml+"<minor>" + result.getString("BILL_MINOR_TYPE_DESC") + "</minor>";
                             xml=xml+"<sub>" + result.getString("BILL_SUB_TYPE_DESC") + "</sub>";
                             xml=xml+"<invoiceparti>" + result.getString("INVOICE_PARTICULARS") + "</invoiceparti>";
                             xml=xml+"<agreeno>" + result.getInt("AGREEMENT_NO") + "</agreeno>";
                             xml=xml+"<agreedate>" + result.getString("AGREEMENT_DATE") + "</agreedate>";
                             xml=xml+"<remarks>" + result.getString("REMARKS") + "</remarks>";
                             xml=xml+"<invoiceAmt>" + result.getInt("INVOICE_AMOUNT") + "</invoiceAmt>";
                             xml=xml+"<firmname>" + result.getString("FIRM_NAME") + "</firmname>";
                             count++;
                             }
                             if(count>0)
                                 xml = xml+"<flag>success</flag>";
                             else
                                 xml=xml+"<flag>failure</flag>";
                     }
                 catch(Exception e) 
                     {
                         System.out.println(" err in retriving records based on date selection ::: "+e.getMessage());
                         xml=xml+"<flag>failure</flag>";
                     }
                 xml=xml+"</response>"; 
            }
            System.out.println("xml ::"+xml);
            out.println(xml);
            out.close();
    }
}

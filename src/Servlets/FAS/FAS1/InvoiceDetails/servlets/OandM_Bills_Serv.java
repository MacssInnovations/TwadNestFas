package Servlets.FAS.FAS1.InvoiceDetails.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class OandM_Bills_Serv
 */
public class OandM_Bills_Serv extends HttpServlet {
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
        ResultSet result=null,rs1=null;
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
         else if(cmd.equalsIgnoreCase("bill_sub_office"))
         {
        	 System.out.println("welcome bill_sub_office ");
             xml="<response><command>bill_sub_office</command>";
             try
             {
            	 offid=Integer.parseInt(request.getParameter("Officeid"));
            	 System.out.println("offid====>"+offid);
            	 ps = con.prepareStatement("SELECT SUBDIVISION_OFFICE_ID,  OFFICE_ID,  OFFICE_NAME FROM COM_MST_ALL_OFFICES_VIEW WHERE DIVISION_OFFICE_ID=? AND OFFICE_LEVEL_ID     ='SD'");
            	 ps.setInt(1,offid);
            	 result = ps.executeQuery();
            	 while(result.next())
            	 {
            		 xml=xml+"<SUBDIVISION_OFFICE_ID>" + result.getInt("SUBDIVISION_OFFICE_ID") + "</SUBDIVISION_OFFICE_ID>"; 
            		 xml=xml+"<SUBDIVISION_OFFICE_NAME>" + result.getString("OFFICE_NAME") + "</SUBDIVISION_OFFICE_NAME>";
            		 count++;
                 }
            	 System.out.println("count===>"+count);
            	 
                 if(count>0)
                     xml = xml+"<flag>success</flag>";
                 else
                     xml=xml+"<flag>failure</flag>";
            	
            	 
             }
             catch(Exception e)
             {
            	 System.out.println("Exception in bill_sub_office======>" +e);
             }
             xml=xml+"</response>"; 
         }
         if (cmd.equalsIgnoreCase("loadSchdebitcode")) {
        	 
      	   ////Joan Changes
         	  response.setContentType(CONTENT_TYPE);
      	   //int count=0;
      	   
      	   System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
  			
      	   String schnoarr=request.getParameter("schno");
      	   System.out.println("schnoarr  "+schnoarr);   			   
      	   int schno = 0;
  			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
  			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
  			System.out.println(schno);
  			String[] sd=request.getParameter("txtCrea_date").split("/");
  			Date txtCrea_date=null;
  			Calendar c;
  			try {
  				
  						
  					
  					c = new GregorianCalendar(
  							Integer.parseInt(sd[2]),
  							Integer.parseInt(sd[1]) - 1,
  							Integer.parseInt(sd[0]));
  					java.util.Date d = c.getTime();
  					txtCrea_date = new Date(d
  							.getTime());
  				}
  			 catch (Exception e) {
  				System.out.println(e);
  			}
  	           System.out.println("txtCrea_date " + txtCrea_date);
  	    	   
  			
  			xml = "<response><command>loadSchdebitcode</command>";
  			
  			try {
  				String su = "SELECT  distinct a.account_head_code, a.sch_sno, " +
  						"  (SELECT PROJECT_NAME " +
  						"  	  FROM PMS_MST_PROJECTS_VIEW v " +
  						"  		  WHERE v.sch_sno= a.sch_sno " +
  						"  		  AND a.office_id=v.office_id " +
  						"  		  AND v.STATUS   ='L' " +
  						"  	  )AS SCH_NAME , " +
  						"  a.office_id, " +
  						"  h.account_head_desc, " +
  						"  a.project_name,DR_CR " +
  						"FROM PMS_FAS_SCH_ACCT_HEAD_MAP_VW a " +
  						"INNER JOIN COM_MST_ACCOUNT_HEADS h " +
  						" ON a. account_head_code=h.account_head_code " +
  						" WHERE a.office_id        = " +cmbOffice_code+
  						" AND a.sch_sno       in ("+schnoarr+") " +
  						" AND ( Usage_Status ='Y'  " +
  						" AND Last_Used_Date  IS NULL ) " +
  						" OR ( USAGE_STATUS    ='B' " + 
  						" AND LAST_USED_DATE   > ?)" +
  						"   order BY  a.sch_sno,a.account_head_code ";
  					System.out.println(su);
  					String su1 =" SELECT h.ACCOUNT_HEAD_CODE, " +
  						"  h.ACCOUNT_HEAD_DESC, " +
  						"  h.BALANCE_TYPE, " +
  						"  h.SUB_LEDGER_TYPE_APPLICABLE, " +
  						"  h.REMARKS, " +
  						"  h.sl_mandatory, " +
  						"  app.SUB_LEDGER_TYPE_CODE, " +
  						"  sl.sub_ledger_type_desc " +
  						"FROM COM_MST_ACCOUNT_HEADS h, " +
  						"  FAS_APPLICABLE_SL_TYPE app, " +
  						"  COM_MST_SL_TYPES sl " +
  						"WHERE h.USAGE_STATUS       ='Y' " +
  						"AND h.ACCOUNT_HEAD_CODE   = ?" +
  						"AND h.ACCOUNT_HEAD_CODE    =app.ACCOUNT_HEAD_CODE " +
  						"AND sl.SUB_LEDGER_TYPE_CODE=app.sub_ledger_type_code " ;
  				ps = con.prepareStatement(su);
  				ps.setDate(1, txtCrea_date);
  				rs1 = ps.executeQuery();
  				System.out.println("su "+su);
  				count=0;
  		while (rs1.next()) {
  			xml = xml + "<cmbAcc_UnitCode>"
  					+ cmbAcc_UnitCode
  					+ "</cmbAcc_UnitCode>";
  			xml = xml + "<cmbOffice_code>"
  					+ cmbOffice_code
  					+ "</cmbOffice_code>";
  					xml = xml + "<ACCOUNT_HEAD_CODE>"
  							+ rs1.getInt("ACCOUNT_HEAD_CODE")
  							+ "</ACCOUNT_HEAD_CODE>";

  					xml = xml + "<ACCOUNT_HEAD_DESC>"
  							+ rs1.getString("account_head_desc")
  							+ "</ACCOUNT_HEAD_DESC>";
  					
  					xml = xml + "<SUB_LEDGER_TYPE>10</SUB_LEDGER_TYPE>";
  					xml = xml + "<sub_ledger_name>Project</sub_ledger_name>";
  				
  					xml = xml + "<SUB_LEDGER_TYPE_CODE>"
  							+ rs1.getInt("sch_sno")
  							+ "</SUB_LEDGER_TYPE_CODE>";
  					xml = xml + "<sub_ledger_type_desc><![CDATA["
  							+ rs1.getString("SCH_NAME")
  							+ "]]></sub_ledger_type_desc>";
  					xml = xml + "<DR_CR>"
  							+ rs1.getString("DR_CR")
  							+ "</DR_CR>";
  					
  				
  				count++;
  				
  			
  		
  		}
  		
  		if(count>0)
  		{
  			xml = xml + "<flag>success</flag>";
  		}
  		else
  		{
  			xml = xml + "<flag>failure1</flag>";
  		}

  		
  				
  				
  			} catch (Exception e) {
  				xml = xml + "<flag>failure</flag>";
  				e.printStackTrace();
  			}
  			xml=xml+"</response>";
//  			System.out.println("xml ::"+xml);
//  		      out.println(xml);
//  		      out.close();	                   
      	   
         }
         
         else if (cmd.equalsIgnoreCase("loadSchEME")) {
	      	  response.setContentType(CONTENT_TYPE);
	   			int  cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	   			int  cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
	   			
	   			System.out.println("cmbOffice_code"+cmbOffice_code);
	   			xml = "<response><command>loadSchEME</command>";
	   			try {
	   			//	String su1 ="select SCH_SNO, SCH_NAME from PMS_SCH_MASTER  where office_id=(select u.accounting_unit_office_id from FAS_MST_ACCT_UNITS u where u.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+"  ) and (SCH_STATUS_ID != 10 and SCH_STATUS_ID !=11) order by SCH_SNO ";
	   			String su1="SELECT a.SCH_SNO, " +
	   					"  a.office_id, " +
	   					"  b.SCH_NAME " +
	   					" FROM " +
	   					"  (SELECT DISTINCT SCH_SNO,office_id FROM PMS_FAS_SCH_ACCT_HEAD_MAP_VW " +
	   					"  )a " +
	   					" INNER JOIN " +
	   					"  (SELECT SCH_SNO , " +
	   					"    PROJECT_NAME AS SCH_NAME, " +
	   					"    office_id " +
	   					"  FROM PMS_MST_PROJECTS_VIEW " +
	   					"  WHERE STATUS='L' " +
	   					"    and (project_id !=0 "+
                        "    and project_id is not null)    "+
	   					"  )b " +
	   					" ON a.SCH_SNO    =b.SCH_SNO " +
	   					" AND a.office_id =b.office_id " +
	   					" AND a.office_id = "+
	   					"  (SELECT u.accounting_unit_office_id " +
	   					"  FROM FAS_MST_ACCT_UNITS u " +
	   					"  WHERE u.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
	   					"  ) " +
	   					"ORDER BY a.SCH_SNO, " +
	   					"  a.office_id";
	   				System.out.println(su1);
	   				ps = con.prepareStatement(su1);
	   				rs1 = ps.executeQuery();
	   				
	   			while(rs1.next()) {
	   					xml = xml + "<sch_sno>"
	   							+ rs1.getInt("sch_sno")
	   							+ "</sch_sno>";
	   			
	   					xml = xml + "<project_name><![CDATA["
	   							+ rs1.getString("SCH_NAME")
	   							+ "]]></project_name>";
	   					
	   				}
	   				
	   				xml = xml + "<flag>success</flag>";
	   			} catch (Exception e) {
	   				xml = xml + "<flag>failure</flag>";
	   				e.printStackTrace();
	   			}
	   			xml=xml+"</response>";
//	   			System.out.println("xml ::"+xml);
//	   		      out.println(xml);
//	   		      out.close();	                       
	   		}
         
         
         
         
            System.out.println("xml ::"+xml);
            out.println(xml);
            out.close();
    }
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException {
    
    	String CONTENT_TYPE ="";
    	  
    	
        
        String strCommand = "";
        Connection con = null;
        ResultSet rs = null,rs_n=null;
        CallableStatement cs = null;
        PreparedStatement ps = null,ps_n=null,ps1=null;
        String xml = "";
        HttpSession session = request.getSession(false);
   String combotype =     request.getParameter("combotype");
 
   
        request.setAttribute("JournalType2",combotype);
        System.out.println(request.getAttribute("JournalType2") + "fgdg");
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
            System.out.println("Exception in opening connection :" + e);
        }

        PrintWriter out=response.getWriter();
        try {

            strCommand = request.getParameter("command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        
        if (strCommand.equalsIgnoreCase("Add")) {
        	
        	System.out.println("THIS IS MINE");           	
            CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCB_Year=0,txtCB_Month=0,txtReceipt_No = 0,sub_office=0;
            int billmajortype=0,billminortype=0,billsubtype=0,billnature=0,ReltxtCB_Year=0,ReltxtCB_Month=0;
            int mbookno=0,mbookpageno=0,cmbMas_SL_Code2=0,cmbMas_SL_type=0,cmbMas_SL_Code=0,paymenttype=0;
            int Agreementno=0,workorderno=0,budgetalloted=0,expenditureincurred=0,DOC_NO=0;
            Calendar c;
            Date txtCrea_date = null,billdate=null,recivdate=null,mbookdate=null;
            Date passdate=null,agreementdate=null,supp_date=null;
            String location="",lsreach="",remarks="";
            double billamount = 0,balanceavailable=0;
            int slNo=0;
            int count=0;
            
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            
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
            
            try {
            	txtCB_Year =
                        Integer.parseInt(request.getParameter("txtCB_Year"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCB_Year " + txtCB_Year);
            try {
            	txtCB_Month =
                        Integer.parseInt(request.getParameter("txtCB_Month"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCB_Month " + txtCB_Month);
            
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
            		new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
            
            try {
                txtReceipt_No =
                        Integer.parseInt(request.getParameter("txtReceipt_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtReceipt_No " + txtReceipt_No);
            
            try {
            	location =request.getParameter("location");
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("location " + location);
            
            try {
            	lsreach =request.getParameter("lsreach");
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("lsreach " + lsreach);
            
            try {
            	sub_office =
                        Integer.parseInt(request.getParameter("sub_office"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("sub_office " + sub_office);
            
            String[] sd1 = request.getParameter("billdate").split("/");
            c =
            		new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
                         Integer.parseInt(sd1[0]));
            java.util.Date d1 = c.getTime();
            billdate = new Date(d1.getTime());
            System.out.println("billdate " + billdate);
            
            String[] sd2 = request.getParameter("recivdate").split("/");
            c =
            		new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,
                         Integer.parseInt(sd2[0]));
            java.util.Date d2 = c.getTime();
            recivdate = new Date(d2.getTime());
            System.out.println("recivdate " + recivdate);
            
            try {
            	billamount =
                        Double.parseDouble(request.getParameter("billamount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("billamount " + billamount);
            
            try {
            	billmajortype =
                        Integer.parseInt(request.getParameter("billmajortype"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("billmajortype " + billmajortype);
            
            try {
            	billminortype =
                        Integer.parseInt(request.getParameter("billminortype"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("billminortype " + billminortype);
            
            try {
            	billsubtype =
                        Integer.parseInt(request.getParameter("billsubtype"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("billsubtype " + billsubtype);
            
            try {
            	billnature =
                        Integer.parseInt(request.getParameter("billnature"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("billnature " + billnature);
            
          
            
            try {
            	ReltxtCB_Year =
                        Integer.parseInt(request.getParameter("ReltxtCB_Year"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("ReltxtCB_Year " + ReltxtCB_Year);
            try {
            	ReltxtCB_Month =
                        Integer.parseInt(request.getParameter("ReltxtCB_Month"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("ReltxtCB_Month " + ReltxtCB_Month);
            
            
            sd2 = request.getParameter("mbookdate").split("/");
            c =
            		new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,
                         Integer.parseInt(sd2[0]));
            d2 = c.getTime();
            mbookdate = new Date(d2.getTime());
            System.out.println("mbookdate " + mbookdate);
            
                        
            try {
            	mbookno =
                        Integer.parseInt(request.getParameter("mbookno"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("mbookno " + mbookno);
            
            try {
            	mbookpageno =
                        Integer.parseInt(request.getParameter("mbookpageno"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("mbookpageno " + mbookpageno);
            
            
            sd2 = request.getParameter("passdate").split("/");
            c =
            		new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,
                         Integer.parseInt(sd2[0]));
            d2 = c.getTime();
            passdate = new Date(d2.getTime());
            System.out.println("passdate " + passdate);
            
            try {
            	cmbMas_SL_Code2 =
                        Integer.parseInt(request.getParameter("cmbMas_SL_Code2"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbMas_SL_Code2 " + cmbMas_SL_Code2);
            
            
            try {
            	cmbMas_SL_type =
                        Integer.parseInt(request.getParameter("cmbMas_SL_type"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbMas_SL_type " + cmbMas_SL_type);
            
            try {
            	cmbMas_SL_Code =
                        Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbMas_SL_Code " + cmbMas_SL_Code);
            
            try {
            	paymenttype =
                        Integer.parseInt(request.getParameter("paymenttype"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("paymenttype " + paymenttype);
            
            
            try {
            	Agreementno =
                        Integer.parseInt(request.getParameter("Agreementno"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("Agreementno " + Agreementno);
            
            sd2 = request.getParameter("agreementdate").split("/");
            c =
            		new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,
                         Integer.parseInt(sd2[0]));
            d2 = c.getTime();
            agreementdate = new Date(d2.getTime());
            System.out.println("agreementdate " + agreementdate);
            
            try {
            	workorderno =
                        Integer.parseInt(request.getParameter("workorderno"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("workorderno " + workorderno);
            
            sd2 = request.getParameter("supp_date").split("/");
            c =
            		new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,
                         Integer.parseInt(sd2[0]));
            d2 = c.getTime();
            supp_date = new Date(d2.getTime());
            System.out.println("supp_date " + supp_date);
            
            
            try {
            	budgetalloted =
                        Integer.parseInt(request.getParameter("budgetalloted"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("budgetalloted " + budgetalloted);
            
            try {
            	expenditureincurred =
                        Integer.parseInt(request.getParameter("expenditureincurred"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("expenditureincurred " + expenditureincurred);
            
            
            try {
            	balanceavailable =
                        Double.parseDouble(request.getParameter("balanceavailable"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("balanceavailable " + balanceavailable);
            
            try {
            	remarks =request.getParameter("remarks");
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("remarks " + remarks);
            
            
            try {
                ps =
                	con.prepareStatement("SELECT DOC_NO FROM FAS_OANDM_BILLS_MASTER GROUP BY DOC_NO HAVING DOC_NO =(SELECT MAX(DOC_NO) FROM FAS_OANDM_BILLS_MASTER WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? )");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                rs = ps.executeQuery();
                if (rs.next()) {
                	DOC_NO = rs.getInt(1);
                    System.out.println("DOC_NO" +
                    		DOC_NO);
                }
                DOC_NO = DOC_NO + 1;
                rs.close();
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("DOC_NO " + DOC_NO);
            
            
            
            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside Master form inserted");
                
                String sql =" insert into FAS_OANDM_BILLS_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID, " + 
                			  " CASHBOOK_YEAR,CASHBOOK_MONTH,DOC_DATE,DOC_NO,LOCATION,LS_REACH,BILL_SUBMIT_OFFICE," + 
                		      " DATE_OF_BILL_SUBMITTED,DATE_OF_RECEIPT,BILL_AMOUNT,MAJOR_TYPE,MINOR_TYPE,SUB_TYPE," +
                			  " BILL_NATURE,RELATED_YEAR,RELATED_MONTH,M_BOOK_DATE,M_BOOK_NO,M_BOOK_PAGE_NO,BILL_PASSED_DATE, " +
                		      " DRAWING_OFFICER,SUB_LEDGER_TYPE,SUB_LEDGER_CODE,PAYMENT_TYPE,AGREEMENT_NO,AGREEMENT_DATE, " +
                			  " WORK_ORDER_NO,WORK_ORDER_DATE,BUDGET_ALLOTED,EXP_INCURRED,BAL_AVL,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                		      " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) " ;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);                
                ps.setInt(4, txtCB_Month);
                ps.setDate(5, txtCrea_date);
                ps.setInt(6, DOC_NO);
                ps.setString(7, location);
                ps.setString(8, lsreach);
                ps.setInt(9, sub_office);
                ps.setDate(10,billdate);
                ps.setDate(11,recivdate);
                ps.setDouble(12,billamount);
                ps.setInt(13,billmajortype);
                ps.setInt(14,billminortype);
                ps.setInt(15,billsubtype);
                ps.setInt(16,billnature);
                ps.setInt(17,ReltxtCB_Year);
                ps.setInt(18,ReltxtCB_Month);
                ps.setDate(19,mbookdate);
                ps.setInt(20,mbookno);
                ps.setInt(21,mbookpageno);
                ps.setDate(22,passdate);
                ps.setInt(23,cmbMas_SL_Code2);
                ps.setInt(24,cmbMas_SL_type);
                ps.setInt(25,cmbMas_SL_Code);
                ps.setInt(26,paymenttype);
                ps.setInt(27,Agreementno);
                ps.setDate(28,agreementdate);
                ps.setInt(29,workorderno);
                ps.setDate(30,supp_date);
                ps.setInt(31,budgetalloted);
                ps.setInt(32,expenditureincurred);
                ps.setDouble(33,balanceavailable);
                ps.setString(34,remarks);
                ps.setString(35, update_user);
                ps.setTimestamp(36, ts);
                int kk = ps.executeUpdate();
                System.out.println("kk executed" + kk);
                if (kk == 0) {
                    System.out.println("redirect");
                    sendMessage(response, "Master Creation Failed ", "ok");
                }
                else
                {
                	String Grid_H_code[] =null;
                	
                	
                	String invoiceNo =
                            request.getParameter("invoiceNo");
                	String invoicedate =
                            request.getParameter("invoicedate");                	
                	String invoiceamount =
                            request.getParameter("invoiceamount");
                	String Particularsinvoice =
                            request.getParameter("Particularsinvoice");
                	Grid_H_code =
                             request.getParameterValues("H_code");
                    String Grid_CR_DR_type[] =
                             request.getParameterValues("CR_DR_type");
                    
                    System.out.println("Grid_CR_DR_type=======inside========>"+Arrays.toString(Grid_CR_DR_type));
                    
                    
                    String Grid_SL_type[] =null;
                    String Grid_SL_code[] =null;
                    String Grid_sl_amt[] =
                                 request.getParameterValues("sl_amt");
                    String Grid_particular[] =
                                 request.getParameterValues("particular");
                    
                    
                   
                    
                    
                    
                    String sql1 =
                            		 " insert into FAS_OANDM_BILLS_TRANS(ACCOUNTING_UNIT_ID, " +
                            		 " ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,DOC_DATE,DOC_NO,SL_NO,INVOICE_NO, "+
                            		 " INVOICE_DATE,INVOICE_AMT,PARTICULARS,ACCOUNT_HEADCODE,SUB_LEDGER_TYPE,SUB_LEDGER_CODE, " +
                            		 " CR_DR_INDICATOR,AMOUNT,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) "+
                            		 " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                             ps1 = con.prepareStatement(sql1);
                             int txtAcc_HeadCode=0,cmbSL_type=0,cmbSL_Code=0,SL_NO = 1;
                             String rad_sub_CR_DR="",invoice_No="",Particulars_invoice="",txtParticular="",txtParticular1="";
                             Date invoicedt=null;
                             double invoice_amount=0,txtsub_Amount=0,txtsub_Amount1=0;
                            
                             System.out.println("Grid_H_code===>"+Grid_H_code.length);
                             
                             
                             if(Grid_H_code.length!=0)
                             {
                             for (int k = 0; k < Grid_H_code.length; k++) {
                            	 
                            	 System.out.println("'Grid_sl_amt[k] "+Grid_sl_amt[k]);
                            	 
                            	 
                           	  if(Grid_sl_amt[k]!="" && Grid_sl_amt[k] != "0"){
                                 try {
                                     txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
                                 } catch (Exception e) {
                                     System.out.println("exception in trans " + e);
                                 }
                                 System.out.println("step1");
                                 rad_sub_CR_DR = Grid_CR_DR_type[k];
                                 System.out.println("rad_sub_CR_DR====>"+rad_sub_CR_DR);
                                 
//                                 try {
//                                     cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
//                                 } catch (Exception e) {
//                                     System.out.println("exception in trans " + e);
//                                 }
                                 
                                 try {
                                     cmbSL_type = Integer.parseInt(request.getParameter("SLtype"+k));
                                 } catch (Exception e) {
                                     System.out.println("exception in trans " + e);
                                 }
                                 
                                 System.out.println("step2");
                                 try {
                                     cmbSL_Code = Integer.parseInt(request.getParameter("SLtypecode"+k));
                                 } catch (Exception e) {
                                     System.out.println("exception in trans " + e);
                                 }
                                 
                                 System.out.println("step2=====>"+cmbSL_type);
                                 System.out.println("step3=====>"+cmbSL_Code);
                                 System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
                                 System.out.println("Grid_CR_DR_type[k] " +
                                                    Grid_CR_DR_type[k]);
                                 
                                 
                                 
                                 
//                                 System.out.println("Grid_SL_type[k]" +
//                                                    Grid_SL_type[k] + "u");
//                                 System.out.println("Grid_SL_code[k]" +
//                                                   "from here" +
//                                                    cmbSL_Code);
                                 
                                 invoice_No=invoiceNo;
                                 
                                 
                                 if (!invoicedate.equalsIgnoreCase("")) {
                                     sd = invoicedate.split("/");
                                     c =
                                    		 new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                  Integer.parseInt(sd[0]));
                                     d = c.getTime();
                                     invoicedt = new Date(d.getTime());
                                 }
                                 
                                 invoice_amount = Double.parseDouble(invoiceamount);
                                 Particulars_invoice = Particularsinvoice;
                                 txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                                 txtParticular = Grid_particular[k];
                                 
                                 
                                 ps1.setInt(1, cmbAcc_UnitCode);
                                 ps1.setInt(2, cmbOffice_code);
                                 ps1.setInt(3, txtCB_Year);
                                 ps1.setInt(4, txtCB_Month);
                                 ps1.setDate(5, txtCrea_date);
                                 ps1.setInt(6, DOC_NO);
                                 ps1.setInt(7, SL_NO);
                                 ps1.setString(8, invoice_No);
                                 ps1.setDate(9, invoicedt);
                                 ps1.setDouble(10, invoice_amount);
                                 ps1.setString(11, Particulars_invoice);
                                 ps1.setInt(12, txtAcc_HeadCode);
                                 ps1.setInt(13, cmbSL_type);
                                 ps1.setInt(14, cmbSL_Code);
                                 ps1.setString(15, rad_sub_CR_DR);
                                 ps1.setDouble(16, txtsub_Amount);
                                 ps1.setString(17, txtParticular);
                                 ps1.setString(18, update_user);
                                 ps1.setTimestamp(19, ts);
                                 SL_NO++;
                                 slNo=SL_NO;
                                 int kk1= ps1.executeUpdate();
                                 
                                 System.out.println(kk1);
                                 if(kk1>0){
                                		count=count+0;
                                	}else{
                                		count=count+1;	
                                	}                              
                                 txtAcc_HeadCode = 0;
                                 rad_sub_CR_DR = "";
                                 cmbSL_type = 0;
                                 cmbSL_Code = 0;
                                 txtAcc_HeadCode = 0;
                                 rad_sub_CR_DR = "";
                                 cmbSL_type = 0;
                                 cmbSL_Code = 0;
                                 
                           	  }
                           	  
                             }
                           	  
                           	  
                             }
                            
                             else
                             {
                            	 count=1;
                             }
                             
                             
//                             int txtAcc_HeadCode1=0,cmbSL_type1=0,cmbSL_Code1=0;
//                             String rad_sub_CR_DR1="";
//                             String Grid_H_code_DR[] =null;
//                             
//                             
//                             Grid_H_code_DR =
//                                     request.getParameterValues("ACCOUNT_HEAD_code");
//                                 String Grid_CR_DR_type_DR[] =
//                                     request.getParameterValues("CR_DR_type1");
//                                 String Grid_SL_type_DR[] =null;
//                                 String Grid_sl_amt_DR[] =
//                                         request.getParameterValues("sl_amt1");
//                                     String Grid_particular_DR[] =
//                                         request.getParameterValues("particular1");
//                             
//                             
//                             
////                             System.out.println("Grid_H_code_DR===>"+Grid_H_code_DR.length);
//System.out.println("For Debit side**************");
//                             
//                             
//                                     
//                                     
//                                     
//                                     if(Grid_H_code_DR.length==0)
//                                     {
//                                     for (int kj = 0; kj < Grid_H_code_DR.length; kj++) {
//                                   	  if(Grid_sl_amt_DR[kj]!="" && Grid_sl_amt_DR[kj] != "0"){
//                                         try {
//                                             txtAcc_HeadCode1 = Integer.parseInt(Grid_H_code_DR[kj]);
//                                         } catch (Exception e) {
//                                             System.out.println("exception in trans " + e);
//                                         }
//                                         rad_sub_CR_DR1 = Grid_CR_DR_type_DR[kj];
//
//                                         try {
//                                             cmbSL_type1 = Integer.parseInt(Grid_SL_type_DR[kj]);
//                                         } catch (Exception e) {
//                                             System.out.println("exception in trans " + e);
//                                         }
//                                         try {
//                                       	  
//                                             cmbSL_Code1 =    Integer.parseInt(request.getParameter("SLtypecode1"+kj));
//                                         } catch (Exception e) {
//                                             System.out.println("exception in trans " + e);
//                                         }
//                                         System.out.println("Grid_H_code[k] " + Grid_H_code_DR[kj]);
//                                         System.out.println("Grid_CR_DR_type[k] " +
//                                                            Grid_CR_DR_type[kj]);
//                                         System.out.println("Grid_SL_type[k]" +
//                                                            Grid_SL_type[kj] + "u");
//                                         System.out.println("Grid_SL_code[k]" +
//                                                          "from here" +
//                                                            cmbSL_Code1);
//                                         
//                                         
//                                         invoice_No=invoiceNo;
//                                         
//                                         
//                                         if (!invoicedate.equalsIgnoreCase("")) {
//                                             sd = invoicedate.split("/");
//                                             c =
//                                            		 new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
//                                          Integer.parseInt(sd[0]));
//                                             d = c.getTime();
//                                             invoicedt = new Date(d.getTime());
//                                         }
//                                         
//                                         invoice_amount = Double.parseDouble(invoiceamount);
//                                         Particulars_invoice = Particularsinvoice;
//                                         txtsub_Amount1 = Double.parseDouble(Grid_sl_amt_DR[kj]);
//                                         txtParticular1 = Grid_particular_DR[kj];
//                                         
//                                         ps1.setInt(1, cmbAcc_UnitCode);
//                                         ps1.setInt(2, cmbOffice_code);
//                                         ps1.setInt(3, txtCB_Year);
//                                         ps1.setInt(4, txtCB_Month);
//                                         ps1.setDate(5, txtCrea_date);
//                                         ps1.setInt(6, DOC_NO);
//                                         ps1.setInt(7, SL_NO);
//                                         ps1.setString(8, invoice_No);
//                                         ps1.setDate(9, invoicedt);
//                                         ps1.setDouble(10, invoice_amount);
//                                         ps1.setString(11, Particulars_invoice);
//                                         ps1.setInt(12, txtAcc_HeadCode1);
//                                         ps1.setInt(13, cmbSL_type1);
//                                         ps1.setInt(14, cmbSL_Code1);
//                                         ps1.setString(15, "DR");
//                                         ps1.setDouble(16, txtsub_Amount1);
//                                         ps1.setString(17, txtParticular1);
//                                         ps1.setString(18, update_user);
//                                         ps1.setTimestamp(19, ts);
//                                         SL_NO++;
//                                         slNo=SL_NO;
//                                         int kk1= ps1.executeUpdate();
//                                         if(kk1>0){
//                                        		count=count+0;
//                                        	}else{
//                                        		count=count+1;	
//                                        	}     
//                                         txtAcc_HeadCode1 = 0;
//                                         rad_sub_CR_DR1 = "";
//                                         cmbSL_type1 = 0;
//                                         cmbSL_Code1 = 0;
//                                         txtsub_Amount1 = 0;
//                                         txtParticular1 = "";
//                                         
//                                   	  }
//                                     
//                                     
//                                     
//                             }
//                                     }
//                                     else
//                                     {
//                                    	 count=0;
//                                     }
                                     
                             
                            
                             
                             
                             if(count==0){   
                                 ps.close();
                                 System.out.println("b4 commit");
                                 con.commit();
                                 sendMessage(response,
                                             "The  Invoice Number '" + DOC_NO +
                                             "' has been Created Successfully ", "ok");
                             }
                              else{
                           	     sendMessage(response,
                                            "Insertion Failed", "ok");
                              }
                	
                }
                
                
            }
            catch(Exception e)
            {
            	System.out.println("Exception in adding master details===>"+e);
            	e.printStackTrace();
            }
            
            
            
            
            
            
        }
        
    	
    	
    }
    
    private void sendMessage(HttpServletResponse response, String msg,
            String bType) {
    	try {
    		System.out.println("sendMessage");
    		String url =
    				"org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
    				"&button=" + bType;
    		response.sendRedirect(url);
    	} catch (IOException e) {
    	}
    }
    
}



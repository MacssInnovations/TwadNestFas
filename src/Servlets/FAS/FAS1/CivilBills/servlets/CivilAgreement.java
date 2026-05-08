package Servlets.FAS.FAS1.CivilBills.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Restricted_AccountHead;
import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.io.*;
import javax.servlet.*;

import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

public class CivilAgreement extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);        
        PrintWriter out = response.getWriter();
        String cmd,agreedate,agreetype,namework,supplydate,tenderdate,to,from,onbrowse,remarks,supplyno,tenderno;
        int count=0,officeid=0,unitid=0,offid=0,agreeno=0,firmContrType=0,firmContrName=0,sectionname=0,concludedcode=0,orgAgreementno=0,param=0,valueofwork=0,authority=0;
        int officeid2,unitid2,cashbookyear=0,cashbookmonth=0,ledcode=0,debitaccode=0;
        String xml="",work="",supply="",worksup="",finYear="",tendetai="";
        String sql="";
        cmd=request.getParameter("command");
        System.out.println(cmd);
        Connection con=null;
        PreparedStatement ps=null;
        Statement st=null;
        ResultSet result=null;
        ServletContext application=null;
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
        int eid=empProfile.getEmployeeId();
        System.out.println("employee id:"+eid);
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        System.out.println("ts"+ts);
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
        
//        try{cashbookyear=Integer.parseInt(request.getParameter("cashbookyear"));}
//        catch(NumberFormatException e){System.out.println("exception"+e );}
//        System.out.println("cashbookyear "+cashbookyear);
//        
//        try{cashbookmonth=Integer.parseInt(request.getParameter("cashbookmonth"));}
//        catch(NumberFormatException e){System.out.println("exception"+e );}
//        System.out.println("cashbookmonth "+cashbookmonth);  
//        
//        try{finYear=request.getParameter("finYear");}
//        catch(NumberFormatException e){System.out.println("exception"+e );}
//        System.out.println("finYear "+finYear);  
        
        int employee_id = 0;

        HttpSession session1 = request.getSession(false);
        UserProfile empProfile1 =
            (UserProfile)session1.getAttribute("UserProfile");
        System.out.println("user id::" + empProfile1.getEmployeeId());
        employee_id = empProfile.getEmployeeId();
        
        if(cmd.equalsIgnoreCase("firmtype")) 
        {
            xml="<response><command>firm</command>";
            try 
            {
               officeid=Integer.parseInt(request.getParameter("officeid1"));
               unitid= Integer.parseInt(request.getParameter("unitid"));
               ps=con.prepareStatement("select FIRMS_ID,FIRMS_NAME from COM_FIRMS_SL_MST where ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNTING_UNIT_ID=?"); 
               ps.setInt(1,officeid);
               ps.setInt(2,unitid);
               result=ps.executeQuery();
               while(result.next()) 
               {
                    xml=xml+"<firmsId>"+result.getInt("FIRMS_ID")+"</firmsId>";    
                    xml=xml+"<firmsName>"+result.getString("FIRMS_NAME")+"</firmsName>";
                    count++;
               }
               if(count>0) 
               xml=xml+"<flag>success</flag>";
               else
               xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
              System.out.println("Exception is"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }//
        
        else if(cmd.equalsIgnoreCase("loadfirm")) 
        {
            xml="<response><command>loadfirm</command>";
            try 
            {
            	
               ledcode=Integer.parseInt(request.getParameter("ledcode"));
               officeid=Integer.parseInt(request.getParameter("officeid1"));
               unitid= Integer.parseInt(request.getParameter("unitid"));
               ps=con.prepareStatement("select FIRMS_ID,FIRMS_NAME from COM_FIRMS_SL_MST where ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNTING_UNIT_ID=? and FIRMS_ID=?"); 
               ps.setInt(1,officeid);
               ps.setInt(2,unitid);
               ps.setInt(3,ledcode);
               result=ps.executeQuery();
               while(result.next()) 
               {
                    xml=xml+"<firmsId>"+result.getInt("FIRMS_ID")+"</firmsId>";    
                    xml=xml+"<firmsName>"+result.getString("FIRMS_NAME")+"</firmsName>";
                    count++;
               }
               if(count>0) 
               xml=xml+"<flag>success</flag>";
               else
               xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
              System.out.println("Exception is"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
         }
        
        else if(cmd.equalsIgnoreCase("contractortype")) 
        {
            xml="<response><command>contractor</command>";
            try 
            {
               officeid=Integer.parseInt(request.getParameter("officeid1"));  
               ps=con.prepareStatement("select CONTRACTOR_ID,CONTRACTOR_NAME from PMS_CONT_REQUEST_REGN where office_id=?"); 
               ps.setInt(1,officeid);
               result=ps.executeQuery();
               while(result.next()) 
               {
                    xml=xml+"<contractorid>"+result.getInt("CONTRACTOR_ID")+"</contractorid>";    
                    xml=xml+"<contractorname>"+result.getString("CONTRACTOR_NAME")+"</contractorname>";
                    count++;
               }
               if(count>0) 
               xml=xml+"<flag>success</flag>";
               else
               xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
              System.out.println("Exception is"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
         }
        else if(cmd.equalsIgnoreCase("work")) 
        {
            xml="<response><command>work</command>";
            try 
            {
             //  officeid=Integer.parseInt(request.getParameter("officeid1"));     
               ps=con.prepareStatement("select work_no from fas_work_order "); 
               result=ps.executeQuery();
               while(result.next()) 
               {
                    xml=xml+"<workno>"+result.getString("work_no")+"</workno>";    
                    count++;
               }
               if(count>0) 
               xml=xml+"<flag>success</flag>";
               else
               xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
              System.out.println("Exception is"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
         }
        else if(cmd.equalsIgnoreCase("supply")) 
        {
            xml="<response><command>supply</command>";
            try 
            {
              ps=con.prepareStatement("select supply_no from fas_supply_order "); 
               result=ps.executeQuery();
               while(result.next()) 
               {
                    xml=xml+"<supplyno>"+result.getString("supply_no")+"</supplyno>";    
                    count++;
               }
               if(count>0) 
               xml=xml+"<flag>success</flag>";
               else
               xml=xml+"<flag>failure</flag>";
            }
            catch(Exception e) 
            {
              System.out.println("Exception is"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
         }
        else if(cmd.equalsIgnoreCase("workdate")) 
        {
            xml="<response><command>workdate</command>";
            try 
            {
            	work=request.getParameter("work");     
               ps=con.prepareStatement("select work_no,to_char(work_date,'dd/mm/yyyy')as work_date from fas_work_order where work_no='"+work+"'"); 
               result=ps.executeQuery();
               while(result.next()) 
               {
                    xml=xml+"<workdate>"+result.getString("work_date")+"</workdate>";    
                    
               }
               xml=xml+"<flag>success</flag>";
               
            }
            catch(Exception e) 
            {
              System.out.println("Exception is"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
         }
        else if(cmd.equalsIgnoreCase("supplydate")) 
        {
            xml="<response><command>supplydate</command>";
            try 
            {
               supply=request.getParameter("supply");     
               ps=con.prepareStatement("select SUPPLY_NO,to_char(SUPPLY_DATE,'dd/mm/yyyy')as SUPPLY_DATE from fas_supply_order where SUPPLY_NO='"+supply+"'"); 
               result=ps.executeQuery();
               while(result.next()) 
               {
                    xml=xml+"<supplydate>"+result.getString("SUPPLY_DATE")+"</supplydate>";    
                    
               }
               xml=xml+"<flag>success</flag>";
               
            }
            catch(Exception e) 
            {
              System.out.println("Exception is"+e);  
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
         }
          else if(cmd.equalsIgnoreCase("address")) 
            {
                xml="<response><command>address</command>";
                officeid=Integer.parseInt(request.getParameter("officeid1"));     
                firmContrType= Integer.parseInt(request.getParameter("firmContrType1"));      
                param=Integer.parseInt(request.getParameter("param1"));
                System.out.println("param"+param);
                try 
                {
                    if(firmContrType==2)
                    {
                        sql="select FIRMS_ID,FIRMS_NAME,FIRMS_ADDRESS as address from COM_FIRMS_SL_MST where ACCOUNTING_FOR_OFFICE_ID=? and FIRMS_ID=?";
                    }
                    else
                    {
                        sql="select CONTRACTOR_ID,CONTRACTOR_NAME,ADDRESS as address  from PMS_CONT_REQUEST_REGN where office_id=? and CONTRACTOR_ID=?";
                    }
                    ps=con.prepareStatement(sql); 
                    ps.setInt(1,officeid);
                    ps.setInt(2,param);
                    result=ps.executeQuery();
                    while(result.next()) 
                    {
                        xml=xml+"<address>"+result.getString("address")+"</address>";
                        count++;
                    }
                    if(count>0) 
                    xml=xml+"<flag>success</flag>";
                    else
                    xml=xml+"<flag>failure</flag>";
                }
                catch(Exception e) 
                {
                    System.out.println("Exception in address"+e);  
                    xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
            } 
        
          else if(cmd.equalsIgnoreCase("authority")) 
            {
        	  officeid=Integer.parseInt(request.getParameter("offid"));
                xml="<response><command>authority</command>";
                try 
                {
                	    sql="select a.DESIGNATION_ID,b.DESIGNATION from\n" + 
                        "(select distinct DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where OFFICE_ID=?)a\n" + 
                        "left outer join \n" + 
                        "(select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS)b\n" + 
                        "on a.DESIGNATION_ID=b.DESIGNATION_ID";
                        
                        ps=con.prepareStatement(sql); 
                        ps.setInt(1,officeid);
                       result=ps.executeQuery();System.out.println("result::::::::"+result);
                        while(result.next()) 
                        {
                            xml=xml+"<designationid>"+result.getString("DESIGNATION_ID")+"</designationid>";
                            xml=xml+"<designation>"+result.getString("DESIGNATION")+"</designation>";
                        }
                        
                        xml=xml+"<flag>success</flag>";
                  }
                catch(Exception e) 
                {
                    System.out.println("Exception in designation"+e);  
                    xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
                System.out.println("response::::::::"+xml);
            }
          else if(cmd.equalsIgnoreCase("checkCode1")) {
        	  
              xml = "<response><command>checkCode1</command>";
              int txtAcc_HeadCode = 0,cmbOffice_code=0;
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
              } else {
                  xml = xml + "<flag>failure</flag>";
              }

              xml = xml + "</response>";
              //System.out.println(xml);
              //out.println(xml);
          } 
        
         else if(cmd.equalsIgnoreCase("add")) 
         {
        	 	xml="<response><command>add</command>";
               // System.out.println("add function starts"); 
                
                try{finYear=request.getParameter("finYear");}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                //System.out.println("finYear "+finYear); 
                
                String[] sd=request.getParameter("agreedate1").split("/");
             //   System.out.println("b4 getting month and year");
                try{cashbookyear=Integer.parseInt(sd[2]);}
                catch(Exception e){System.out.println("exception"+e );}
               // System.out.println("cashbookyear "+cashbookyear);
                
                try{cashbookmonth=Integer.parseInt(sd[1]);}
                catch(Exception e){System.out.println("exception"+e );}
               // System.out.println("cashbookmonth "+cashbookmonth);
                
            
             unitid= Integer.parseInt(request.getParameter("unitid1"));
             offid= Integer.parseInt(request.getParameter("offid1"));
             agreedate= request.getParameter("agreedate1");
             agreetype= request.getParameter("agreetype1");
             try
             {
             orgAgreementno=Integer.parseInt(request.getParameter("orgAgreementno1"));
             }
             catch(Exception e)
             {
             System.out.println("Err in getting orgAgreementno "+e.getMessage());
             orgAgreementno=0;
             }
             namework= request.getParameter("namework1");
             firmContrType= Integer.parseInt(request.getParameter("firmContrType1"));
             try
             {
                 firmContrName= Integer.parseInt(request.getParameter("firmContrName1"));
                 System.out.println("firmContrName"+firmContrName);
             }
             catch(Exception e1)
             {
                 System.out.println("Err in getting firmContrName "+e1.getMessage());  
             }
             valueofwork=Integer.parseInt(request.getParameter("value1"));
             worksup= request.getParameter("worksup");
             supplyno= request.getParameter("supplyno1");
             supplydate= request.getParameter("supplydate1");
             tenderno= request.getParameter("tenderno1");
             tenderdate= request.getParameter("tenderdate1");
             from= request.getParameter("from1");
             to= request.getParameter("to1");
             authority= Integer.parseInt(request.getParameter("authority1"));
             sectionname= Integer.parseInt(request.getParameter("sectionname1"));
             onbrowse= request.getParameter("onbrowse1");
             concludedcode= Integer.parseInt(request.getParameter("concludedcode1"));
             remarks=request.getParameter("remarks1");
             tendetai=request.getParameter("tendetail");
             debitaccode=Integer.parseInt(request.getParameter("debitaccode"));
             
             System.out.println("remarks"+remarks);
             int inc=0;
             try
             {
                  st=con.createStatement();
                  result=st.executeQuery("select max(AGREEMENT_NO) from FAS_CIVIL_AGREEMENT");
                  if(result.next()) 
                  {
                    inc=result.getInt(1);
                  }
                   inc++;
             }
             catch(Exception e)
             {
                 System.out.println("Exception in query "+e);
             }
             try 
                 {
            	 System.out.println("insert");
                  ps=con.prepareStatement("insert into FAS_CIVIL_AGREEMENT(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,AGREEMENT_NO,FINANCIAL_YEAR,CASHBOOK_YEAR,CASHBOOK_MONTH,AGREEMENT_DATE,AGREEMENT_TYPE,SUP_ORG_AGREEMENT_NO,NAME_OF_WORK,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,VALUE_OF_WORK,WORK_OR_SUPPLY,WORK_OR_SUPPLY_ORDER_NO,WORK_OR_SUPPLY_ORDER_DATE,TENDER_NO,TENDER_DATE,AGREEMENT_PERIOD_FROM,AGREEMENT_PERIOD_UPTO,CONCLUDING_AUTHORITY,SECTION_ID,DOCUMENT_PATH,CONCLUDED_BY_CODE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,STATUS,VERIFIED,TENDER_DETAILS,DEBIT_ACHEAD) values(?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),to_date(?,'dd/mm/yyyy'),to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?)");
                  
                     ps.setInt(1, unitid);
                     ps.setInt(2, offid);
                     ps.setInt(3,inc);
                     ps.setString(4, finYear);
                     ps.setInt(5, cashbookyear);
                     ps.setInt(6, cashbookmonth);
                     ps.setString(7, agreedate);
                     ps.setString(8, agreetype);
                     ps.setInt(9,orgAgreementno);
                     ps.setString(10, namework);
                     ps.setInt(11, firmContrType);
                     ps.setInt(12, firmContrName);
                     ps.setInt(13, valueofwork);
                     ps.setString(14, worksup);
                     ps.setString(15, supplyno);
                     ps.setString(16, supplydate);
                     ps.setString(17, tenderno);
                     ps.setString(18, tenderdate);
                     ps.setString(19, from);
                     ps.setString(20, to);
                     ps.setInt(21, authority);
                     ps.setInt(22, sectionname);
                     ps.setString(23, onbrowse);
                     ps.setInt(24, concludedcode);
                     ps.setString(25, remarks);
                     ps.setInt(26,eid);
                     ps.setTimestamp(27,ts);
                     ps.setString(28,"L");
                     ps.setString(29,"N");
                     ps.setString(30,tendetai);
                     ps.setInt(31,debitaccode);
                     
                     
                     System.out.println("last");
            
                     ps.executeUpdate();    
                     xml=xml+"<flag>success</flag>";
                     
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
//        	 try{cashbookyear=Integer.parseInt(request.getParameter("cashbookyear"));}
//             catch(NumberFormatException e){System.out.println("exception"+e );}
//             System.out.println("cashbookyear "+cashbookyear);
//             
//             try{cashbookmonth=Integer.parseInt(request.getParameter("cashbookmonth"));}
//             catch(NumberFormatException e){System.out.println("exception"+e );}
//             System.out.println("cashbookmonth "+cashbookmonth);  
             
        	 String[] sd=request.getParameter("agreedate").split("/");
             System.out.println("b4 getting month and year");
             try{cashbookyear=Integer.parseInt(sd[2]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("cashbookyear "+cashbookyear);
             
             try{cashbookmonth=Integer.parseInt(sd[1]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("cashbookmonth "+cashbookmonth);
        	 
             try{finYear=request.getParameter("finYear");}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("finYear "+finYear); 
             xml="<response><command>updated</command>";
             unitid= Integer.parseInt(request.getParameter("unitid1"));
             offid= Integer.parseInt(request.getParameter("offid1"));
             agreeno= Integer.parseInt(request.getParameter("agreeno1"));
          //   agreedate= request.getParameter("agreedate1");
             agreetype= request.getParameter("agreetype1");
             namework= request.getParameter("namework1");
             firmContrType= Integer.parseInt(request.getParameter("firmContrType1"));
             firmContrName= Integer.parseInt(request.getParameter("firmContrName1"));
             valueofwork=Integer.parseInt(request.getParameter("value1"));
             worksup= request.getParameter("worksup");
             supplyno= request.getParameter("supplyno1");
             supplydate= request.getParameter("supplydate1");
             tenderno= request.getParameter("tenderno1");
             tenderdate= request.getParameter("tenderdate1");
             from= request.getParameter("from1");
             to= request.getParameter("to1");
             authority= Integer.parseInt(request.getParameter("authority1"));
             sectionname= Integer.parseInt(request.getParameter("sectionname1"));
             concludedcode= Integer.parseInt(request.getParameter("concludedcode1"));
             remarks=request.getParameter("remarks1");
             tendetai=request.getParameter("tendetail");
             debitaccode=Integer.parseInt(request.getParameter("debitaccode"));
             
             try 
                 {
                     ps = con.prepareStatement("update FAS_CIVIL_AGREEMENT set AGREEMENT_TYPE=?,NAME_OF_WORK=?,SUB_LEDGER_TYPE_CODE=?,SUB_LEDGER_CODE=?,VALUE_OF_WORK=?,WORK_OR_SUPPLY_ORDER_NO=?,WORK_OR_SUPPLY_ORDER_DATE=to_date(?,'dd-mm-yyyy'),TENDER_NO=?,TENDER_DATE=to_date(?,'dd-mm-yyyy'),AGREEMENT_PERIOD_FROM=to_date(?,'dd-mm-yyyy'),AGREEMENT_PERIOD_UPTO=to_date(?,'dd-mm-yyyy'),CONCLUDING_AUTHORITY=?,SECTION_ID=?,CONCLUDED_BY_CODE=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,WORK_OR_SUPPLY=?,TENDER_DETAILS=?,DEBIT_ACHEAD=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and AGREEMENT_NO=? and FINANCIAL_YEAR=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");     
               //      ps.setString(1, agreedate);
                     ps.setString(1, agreetype);
                     ps.setString(2, namework);
                     ps.setInt(3, firmContrType);
                     ps.setInt(4, firmContrName);
                     ps.setInt(5, valueofwork);
                     ps.setString(6, supplyno);
                     ps.setString(7, supplydate);
                     ps.setString(8, tenderno);
                     ps.setString(9, tenderdate);
                     ps.setString(10, from);
                     ps.setString(11, to);
                     ps.setInt(12, authority);
                     ps.setInt(13, sectionname);
                     ps.setInt(14, concludedcode);
                     ps.setString(15, remarks);
                     ps.setInt(16,eid);
                     ps.setTimestamp(17,ts);
                     ps.setString(18, worksup);
                     ps.setString(19,tendetai);
                     ps.setInt(20,debitaccode);
                     ps.setInt(21, unitid);
                     ps.setInt(22, offid);
                     ps.setInt(23,agreeno);
                     ps.setString(24, finYear);
                     ps.setInt(25, cashbookyear);
                     ps.setInt(26, cashbookmonth);
                     
                     int i=ps.executeUpdate();
                     if(i>0) 
                     {
                         xml=xml+"<flag>success</flag>"; 
                     }
                     
                 }
             catch(Exception e) 
                 {
                     System.out.println("exception in update is "+e);
                     xml=xml+"<flag>failure</flag>";
                 }
             xml=xml+"</response>";
         }
         else if(cmd.equalsIgnoreCase("deleted"))
         {
        	 String[] sd=request.getParameter("agreedate").split("/");
             
             System.out.println("b4 getting month and year");
             try{cashbookyear=Integer.parseInt(sd[2]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("cashbookyear "+cashbookyear);
             
             try{cashbookmonth=Integer.parseInt(sd[1]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("cashbookmonth "+cashbookmonth);
        	 
             unitid= Integer.parseInt(request.getParameter("unitid1"));
             offid= Integer.parseInt(request.getParameter("offid1"));
             
             xml="<response><command>deleted</command>";
//             try{cashbookyear=Integer.parseInt(request.getParameter("cashbookyear"));}
//             catch(NumberFormatException e){System.out.println("exception"+e );}
//             System.out.println("cashbookyear "+cashbookyear);
//             
//             try{cashbookmonth=Integer.parseInt(request.getParameter("cashbookmonth"));}
//             catch(NumberFormatException e){System.out.println("exception"+e );}
//             System.out.println("cashbookmonth "+cashbookmonth);  
             
             try{finYear=request.getParameter("finYear");}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("finYear "+finYear); 
             agreeno=Integer.parseInt(request.getParameter("agreeno1"));
             System.out.println("agreeno"+agreeno);
             try 
                 {
                     ps = con.prepareStatement("delete from FAS_CIVIL_AGREEMENT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and AGREEMENT_NO=? and FINANCIAL_YEAR=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");  
                     ps.setInt(1, unitid);
                     ps.setInt(2, offid);
                     ps.setInt(3,agreeno);
                     ps.setString(4, finYear);
                     ps.setInt(5, cashbookyear);
                     System.out.println("cashbookyear "+cashbookyear);
                     ps.setInt(6, cashbookmonth);
                     System.out.println("cashbookmonth "+cashbookmonth);
                     
                     ps.executeUpdate();
                     xml = xml+"<flag>success</flag>";
                 }
             catch(Exception e) 
                 {
                     xml=xml+"<flag>failure</flag>";
                 }
             xml=xml+"</response>";
         }
         else if(cmd.equalsIgnoreCase("gett"))
         {
        	 try{cashbookyear=Integer.parseInt(request.getParameter("cashbookyear"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("cashbookyear "+cashbookyear);
             
             try{cashbookmonth=Integer.parseInt(request.getParameter("cashbookmonth"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("cashbookmonth "+cashbookmonth);  
             
             try{finYear=request.getParameter("finYear");}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("finYear "+finYear); 
             unitid2=Integer.parseInt(request.getParameter("unitid1"));
             officeid2=Integer.parseInt(request.getParameter("officeid1"));
             xml="<response><command>gett</command>";   
             try
             {
                 sql="select a.*,b.FIRMS_NAME,c.CONTRACTOR_NAME from\n" + 
                "(select ACCOUNTING_FOR_OFFICE_ID,AGREEMENT_NO,to_char(AGREEMENT_DATE,'dd/mm/yyyy')as AGREEMENT_DATE,AGREEMENT_TYPE,SUB_LEDGER_CODE,VALUE_OF_WORK,REMARKS,SUB_LEDGER_TYPE_CODE from FAS_CIVIL_AGREEMENT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? order by AGREEMENT_NO)\n" + 
                "a left outer join\n" + 
                "(select ACCOUNTING_FOR_OFFICE_ID,FIRMS_ID,FIRMS_NAME from COM_FIRMS_SL_MST)b\n" + 
                "on a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID and a.SUB_LEDGER_CODE=b.FIRMS_ID left outer join\n" + 
                "(select OFFICE_ID,CONTRACTOR_ID,CONTRACTOR_NAME from PMS_CONT_REQUEST_REGN)c\n" + 
                " on a.ACCOUNTING_FOR_OFFICE_ID=c.OFFICE_ID and a.SUB_LEDGER_CODE=c.CONTRACTOR_ID";
                ps=con.prepareStatement(sql);   
                ps.setInt(1,unitid2);
                ps.setInt(2,officeid2);
                result=ps.executeQuery();
                while(result.next()) 
                {
                    xml=xml+"<agreeno>" + result.getInt("AGREEMENT_NO") + "</agreeno>";
                    xml=xml+"<agreedate>" + result.getString("AGREEMENT_DATE") + "</agreedate>";
                    xml=xml+"<agreetype>" + result.getString("AGREEMENT_TYPE") + "</agreetype>";
                    xml=xml+"<ledgercode>" + result.getString("SUB_LEDGER_TYPE_CODE") + "</ledgercode>";
                    System.out.println("result.getString(\"SUB_LEDGER_TYPE_CODE\")"+result.getString("SUB_LEDGER_TYPE_CODE"));
                    xml=xml+"<firmname>" + result.getString("FIRMS_NAME") + "</firmname>";
                    System.out.println("result.getString(\"FIRMS_NAME\") "+result.getString("FIRMS_NAME") );
                    xml=xml+"<contractorname>" + result.getString("CONTRACTOR_NAME") + "</contractorname>";
                    System.out.println("result.getString(\"CONTRACTOR_NAME\")"+result.getString("CONTRACTOR_NAME"));
                    xml=xml+"<valueofwork>" + result.getInt("VALUE_OF_WORK") + "</valueofwork>";
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
//        	 try{cashbookyear=Integer.parseInt(request.getParameter("cashbookyear"));}
//             catch(NumberFormatException e){System.out.println("exception"+e );}
//             System.out.println("cashbookyear "+cashbookyear);
//             
//             try{cashbookmonth=Integer.parseInt(request.getParameter("cashbookmonth"));}
//             catch(NumberFormatException e){System.out.println("exception"+e );}
//             System.out.println("cashbookmonth "+cashbookmonth);  
//             
//             try{finYear=request.getParameter("finYear");}
//             catch(NumberFormatException e){System.out.println("exception"+e );}
//             System.out.println("finYear "+finYear); 
             xml="<response><command>retrieve</command>";   
             try
             {
                    agreeno=Integer.parseInt(request.getParameter("agno1"));
                    sql="select a.*,SUP_ORG_AGREEMENT_DATE from(select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,to_char(AGREEMENT_DATE,'dd/mm/yyyy')as AGREEMENT_DATE,AGREEMENT_TYPE,SUP_ORG_AGREEMENT_NO,NAME_OF_WORK,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,VALUE_OF_WORK,WORK_OR_SUPPLY_ORDER_NO,to_char(WORK_OR_SUPPLY_ORDER_DATE,'dd/mm/yyyy')as WORK_OR_SUPPLY_ORDER_DATE,WORK_OR_SUPPLY,TENDER_NO,to_char(TENDER_DATE,'dd/mm/yyyy')as TENDER_DATE,to_char(AGREEMENT_PERIOD_FROM,'dd/mm/yyyy')as AGREEMENT_PERIOD_FROM,to_char(AGREEMENT_PERIOD_UPTO,'dd/mm/yyyy')as AGREEMENT_PERIOD_UPTO,CONCLUDING_AUTHORITY,SECTION_ID,DOCUMENT_PATH,CONCLUDED_BY_CODE,REMARKS,TENDER_DETAILS,DEBIT_ACHEAD from FAS_CIVIL_AGREEMENT where AGREEMENT_NO=?)a left outer join (select AGREEMENT_NO,to_char(AGREEMENT_DATE,'dd/mm/yyyy')as SUP_ORG_AGREEMENT_DATE from FAS_CIVIL_AGREEMENT)b on a.SUP_ORG_AGREEMENT_NO=b.AGREEMENT_NO ";
                    ps=con.prepareStatement(sql);   
                    ps.setInt(1,agreeno);
                    result=ps.executeQuery();
                    System.out.println("result"+result);
                    while(result.next()) 
                    { 
                    	System.out.println("retrieve");
                        xml=xml+"<unitid>" + result.getInt("ACCOUNTING_UNIT_ID") + "</unitid>";
                        xml=xml+"<officeid>" + result.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</officeid>";
                        xml=xml+"<agreedate>" + result.getString("AGREEMENT_DATE") + "</agreedate>";
                        xml=xml+"<agreetype>" + result.getString("AGREEMENT_TYPE") + "</agreetype>";
                        xml=xml+"<supno>" + result.getInt("SUP_ORG_AGREEMENT_NO") + "</supno>";
                        xml=xml+"<namework>" + result.getString("NAME_OF_WORK") + "</namework>";
                        xml=xml+"<ledgertypecode>" + result.getInt("SUB_LEDGER_TYPE_CODE") + "</ledgertypecode>";
                        System.out.println("ledgertypecode"+result.getInt("SUB_LEDGER_TYPE_CODE"));
                        xml=xml+"<ledgercode>" + result.getInt("SUB_LEDGER_CODE") + "</ledgercode>";
                        System.out.println("ledgercode"+result.getInt("SUB_LEDGER_CODE"));
                        xml=xml+"<valuework>" + result.getInt("VALUE_OF_WORK") + "</valuework>";
                        System.out.println("valuework"+result.getInt("VALUE_OF_WORK"));
                        xml=xml+"<worksupp>" + result.getString("WORK_OR_SUPPLY") + "</worksupp>";
                        xml=xml+"<orderno>" + result.getString("WORK_OR_SUPPLY_ORDER_NO") + "</orderno>";
                        xml=xml+"<orderdate>" + result.getString("WORK_OR_SUPPLY_ORDER_DATE") + "</orderdate>";
                        xml=xml+"<tenderno>" + result.getInt("TENDER_NO") + "</tenderno>";
                        xml=xml+"<tenderdate>" + result.getString("TENDER_DATE") + "</tenderdate>";
                        xml=xml+"<periodfrom>" + result.getString("AGREEMENT_PERIOD_FROM") + "</periodfrom>";
                        xml=xml+"<periodupto>" + result.getString("AGREEMENT_PERIOD_UPTO") + "</periodupto>";
                        xml=xml+"<authority>" + result.getString("CONCLUDING_AUTHORITY") + "</authority>";
                        xml=xml+"<sectionid>" + result.getInt("SECTION_ID") + "</sectionid>";
                        System.out.println("sectionid"+result.getInt("SECTION_ID"));
                        xml=xml+"<documentpath>" + result.getString("DOCUMENT_PATH") + "</documentpath>";
                        System.out.println("documentpath::::"+result.getString("DOCUMENT_PATH"));
                        xml=xml+"<concludedcode>" + result.getInt("CONCLUDED_BY_CODE") + "</concludedcode>";
                        System.out.println("concludedcode"+result.getInt("CONCLUDED_BY_CODE"));
                        xml=xml+"<remarks>" + result.getString("REMARKS") + "</remarks>";
                        xml=xml+"<supdate>"+result.getString("SUP_ORG_AGREEMENT_DATE")+"</supdate>";
                        xml=xml+"<TENDER_DETAILS>"+result.getString("TENDER_DETAILS")+"</TENDER_DETAILS>";
                        xml=xml+"<DEBIT_ACHEAD>"+result.getInt("DEBIT_ACHEAD")+"</DEBIT_ACHEAD>";

                        System.out.println("date is:::;"+result.getString("SUP_ORG_AGREEMENT_DATE"));
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
         else if(cmd.equalsIgnoreCase("supplement")) 
         {
             xml="<response><command>supplement</command>";   
             try
             {
                 ps=con.prepareStatement("select AGREEMENT_NO from FAS_CIVIL_AGREEMENT");
                 result=ps.executeQuery();
                 while(result.next()) 
                 {
                 xml=xml+"<agreeno>" + result.getInt("AGREEMENT_NO") + "</agreeno>";   
                 count++;
                 }
                 if(count>0)
                     xml = xml+"<flag>success</flag>";
                 else
                     xml=xml+"<flag>failure</flag>";
             }
             catch(Exception e) 
             {
                 System.out.println("Exception in supplement is "+e);
                 xml=xml+"<flag>failure</flag>";   
             }
             xml=xml+"</response>";   
         }
         else if(cmd.equalsIgnoreCase("suppleDate"))
         {
        	 try{cashbookyear=Integer.parseInt(request.getParameter("cashbookyear"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("cashbookyear "+cashbookyear);
             
             try{cashbookmonth=Integer.parseInt(request.getParameter("cashbookmonth"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("cashbookmonth "+cashbookmonth);  
             
             try{finYear=request.getParameter("finYear");}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("finYear "+finYear); 
           xml="<response><command>suppleDate</command>";   
           agreeno=Integer.parseInt(request.getParameter("OriAgreeNo"));   
           try
           {
                   ps=con.prepareStatement("select to_char(AGREEMENT_DATE,'dd/mm/yyyy')as AGREEMENT_DATE,NAME_OF_WORK,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE from FAS_CIVIL_AGREEMENT where AGREEMENT_NO=?");
                   ps.setInt(1,agreeno);
                   result=ps.executeQuery();
                   while(result.next()) 
                   {
                        xml=xml+"<agreedate>"+result.getString("AGREEMENT_DATE")+"</agreedate>";
                        xml=xml+"<namework>"+result.getString("NAME_OF_WORK")+"</namework>";
                        xml=xml+"<ledgertypecode>"+result.getString("SUB_LEDGER_TYPE_CODE")+"</ledgertypecode>";
                        xml=xml+"<ledgercode>"+result.getString("SUB_LEDGER_CODE")+"</ledgercode>";
                        System.out.println("ledger code:::"+result.getString("SUB_LEDGER_CODE"));
                        count++;
                   }
                   if(count>0)
                       xml = xml+"<flag>success</flag>";
                   else
                       xml=xml+"<flag>failure</flag>";
             }
           catch(Exception e) 
           {
               System.out.println("Exception in supplementDate is "+e);
               xml=xml+"<flag>failure</flag>";   
           }
             xml=xml+"</response>"; 
         }
        System.out.println(xml);
        out.write(xml);
        out.close();
    }
}

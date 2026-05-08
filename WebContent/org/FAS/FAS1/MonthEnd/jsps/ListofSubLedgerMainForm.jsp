<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>List of SubLedger Main Form</title>
    <script type="text/javascript" src="../scripts/ListofSubLedgerMainForm.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   
  </head>
  <body  bgcolor="rgb(255,255,225)">
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    ResultSet rs1=null;
    PreparedStatement ps1=null;
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
  
  <% 
      System.out.println("...............list all jsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
       
           int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0,SL_Type=0,Type_Code=0;
            String financeyear="";
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
             Cashbook_year=Integer.parseInt(request.getParameter("CashbookYear"));
            }catch(Exception e){System.out.println("Exception in CashbookYear:"+e);}
            try{
            Cashbook_month=Integer.parseInt(request.getParameter("CashbookMonth"));
            }catch(Exception e){System.out.println("Exception in CashbookMonth:"+e);}
            try
            {
                SL_Type=Integer.parseInt(request.getParameter("SL_type"));     
            }catch(Exception e){System.out.println("Exception in Sl_Type:"+e);}
            try
            {
                Type_Code=Integer.parseInt(request.getParameter("Type_Code"));     
            }catch(Exception e){System.out.println("Exception in Type_Code:"+e);}
        financeyear= request.getParameter("txtFinanYr");
        System.out.println(financeyear);
   %>
  
  <form name="ChequeBookList" id="ChequeBookList">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
              &nbsp;List of Opening Balance for Sub Ledger Account Heads
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
       <th>
       Select
       </th>
       <th>
      A/c Code
       </th>
       <th>
       A/c Head Desc
       </th>
       <th>
      Curr Year CR Balance
       </th>
       
       <th>
      Curr Year DR Balance
       </th>
       <th>
        Opening Balance
       </th>
       <th>
        Opening Balance Indicator
       </th>
     <!--  <th>
      Current Month Debit
      </th>
      <th>
      Current Month Credit
      </th>
      <th>
        Closing Balance
      </th>
      <th>
        Closing Balance Indicator
      </th>
      <th>
        Last Dr Update Date
      </th>
      <th>
        Last Cr Update Date
      </th>
     <th>
        CashBook Year
      </th>
      <th>
        CashBook Month
      </th>-->
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
         String AcHeadName="";
         String AcHeadCode="";
           try
           {
            ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,UPTO_CREDIT_BALANCE,UPTO_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,CURRENT_YEAR_DEBIT_BALANCE,MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE from FAS_SUB_LEDGER_MASTER where  ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and year=? and month=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? order by ACCOUNT_HEAD_CODE");
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);
            ps.setString(3,financeyear);
            ps.setInt(4,Cashbook_year);
            ps.setInt(5,Cashbook_month);
            ps.setInt(6,SL_Type);
            ps.setInt(7,Type_Code);
            rs=ps.executeQuery();
            int cnt=0;         
            while(rs.next())
            {
                cnt++;  
                AcHeadCode=rs.getString("ACCOUNT_HEAD_CODE");
                System.out.println("*****************"+AcHeadCode);
                try
                {
                    ps1=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps1.setString(1,AcHeadCode);
                    rs1=ps1.executeQuery();
                    if(rs1.next())
                    {
                        AcHeadName=rs1.getString("ACCOUNT_HEAD_DESC");
                    }
                    ps1.close();
                    //rs1.close();
                }
                catch(Exception que)
                {
                System.out.println("exception in fetching the account head name....."+que);
                }
                 String UptoCRBalance=rs.getString("UPTO_CREDIT_BALANCE");
                 System.out.println(UptoCRBalance);
                 String UptoDBBalance=rs.getString("UPTO_DEBIT_BALANCE");
                 System.out.println(UptoDBBalance);
                 String currCR=rs.getString("CURRENT_YEAR_CREDIT_BALANCE");
                 System.out.println(currCR);
                 String currDB=rs.getString("CURRENT_YEAR_DEBIT_BALANCE");
                 System.out.println(currDB);
                 //Change Date 30-Nov-2006 B//
                 String MONTH_OPENING_BALANCE=rs.getString("MONTH_OPENING_BALANCE");
                 String MONTH_OPENING_BAL_DR_CR_IND=rs.getString("MONTH_OPENING_BAL_DR_CR_IND");
                 String CURRENT_MONTH_DEBIT=rs.getString("CURRENT_MONTH_DEBIT");
                 String CURRENT_MONTH_CREDIT=rs.getString("CURRENT_MONTH_CREDIT");
                 String MONTH_CLOSING_BALANCE=rs.getString("MONTH_CLOSING_BALANCE");
                 String MONTH_CLOSING_BAL_DR_CR_IND=rs.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                 String DR_UPDATE_LAST_DATE=rs.getString("DR_UPDATE_LAST_DATE");
                 String CR_UPDATE_LAST_DATE=rs.getString("CR_UPDATE_LAST_DATE");
                 if(MONTH_OPENING_BALANCE=="0")
                 {
                 MONTH_OPENING_BALANCE="";
                 }
                 else
                 {
                 MONTH_OPENING_BALANCE=MONTH_OPENING_BALANCE;
                 }
                 if(CURRENT_MONTH_DEBIT=="0")
                 {
                    CURRENT_MONTH_DEBIT="";
                 }
                 else
                 {
                    CURRENT_MONTH_DEBIT=CURRENT_MONTH_DEBIT;
                 }
                 if(DR_UPDATE_LAST_DATE==null)
                 {
                    DR_UPDATE_LAST_DATE="";
                 }
                 else
                 {
                    DR_UPDATE_LAST_DATE=DR_UPDATE_LAST_DATE;
                 }
                 
                 if(CR_UPDATE_LAST_DATE==null)
                 {
                    CR_UPDATE_LAST_DATE="";
                 }
                 else
                 {
                    CR_UPDATE_LAST_DATE=CR_UPDATE_LAST_DATE;
                 }
                 
                 //End Chage Date 30-Nov-2006 B//
               
                
                
                
                out.println("<tr id='" + AcHeadCode + "'>");   
                out.println("<td><a href=\"javascript:loadTabAll('" + AcHeadCode + "')\">Edit</a></td>");
                
                out.println("<td>"+AcHeadCode+"</td>");
                out.println("<td>"+AcHeadName+"</td>");
                /*out.println("<td>"+UptoCRBalance+"</td>");
                out.println("<td>"+UptoDBBalance+ "</td>");*/
                out.println("<td>"+currCR+"</td>");
                out.println("<td>"+currDB+"</td>");
                out.println("<td>"+MONTH_OPENING_BALANCE+"</td>");
                out.println("<td>"+MONTH_OPENING_BAL_DR_CR_IND+"</td>");
                /*out.println("<td>"+CURRENT_MONTH_DEBIT+"</td>");
                out.println("<td>"+CURRENT_MONTH_CREDIT+ "</td>");
                out.println("<td>"+MONTH_CLOSING_BALANCE+"</td>");
                out.println("<td>"+MONTH_CLOSING_BAL_DR_CR_IND+"</td>");
                out.println("<td>"+DR_UPDATE_LAST_DATE+"</td>");
                out.println("<td>"+CR_UPDATE_LAST_DATE+"</td></tr>");*/
                
               
            }
            
                 rs.close();
                 
             if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td><td></td><td></td><td></td></tr>");
             
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
          %>
          
          </tbody>
       </table>
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>
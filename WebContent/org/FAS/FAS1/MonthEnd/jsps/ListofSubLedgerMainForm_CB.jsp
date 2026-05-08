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
    <script type="text/javascript" src="../scripts/ListofSubLedgerMainForm_CB.js"></script>
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
ResultSet rs2=null;
    PreparedStatement ps2=null;
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
      System.out.println("...............list all LISTjsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
       
           int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0,SL_TYPE=0,SL_CODE=0;
            String financeyear="";
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
             System.out.println("cmbAcc_UnitCode..."+cmbAcc_UnitCode);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            System.out.println("cmbOffice_code..."+cmbOffice_code);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
             Cashbook_year=Integer.parseInt(request.getParameter("CashbookYear"));
             System.out.println("Cashbook_year..."+Cashbook_year);
            }catch(Exception e){System.out.println("Exception in CashbookYear:"+e);}
            try{
            Cashbook_month=Integer.parseInt(request.getParameter("CashbookMonth"));
            System.out.println("Cashbook_month..."+Cashbook_month);
            }catch(Exception e){System.out.println("Exception in CashbookMonth:"+e);}
            try
            {
                SL_TYPE=Integer.parseInt(request.getParameter("SL_TYPE"));     
            }catch(Exception e){System.out.println("Exception in Sl_Type:"+e);}
            try
            {
                SL_CODE=Integer.parseInt(request.getParameter("SL_CODE"));     
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
      SL Type
      </th>
      <th>
        SL Code
      </th>
       <th>
        Closing Balance
       </th>
       <th>
        Closing Balance Indicator
       </th>
     
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
         String AcHeadName="";
         String AcHeadCode="";
         String SLDesc="";
         String SLCodeDesc="";
        // int accheadcode=Integer.parseInt(request.getParameter("accheadcode"));
         //System.out.println("acc head code is..........."+accheadcode);
        // int SL_TYPE=0;
     //    int SL_CODE=0;
           try
           {
            ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND from FAS_SUB_LEDGER_MASTER_CB where  ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and year=? and month=? order by ACCOUNT_HEAD_CODE");
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);
            ps.setString(3,financeyear);
            ps.setInt(4,Cashbook_year);
            ps.setInt(5,Cashbook_month);
            //ps.setInt(6,accheadcode);
           // ps.setInt(6,SL_type);
          //  ps.setInt(7,Type_Code);
            rs=ps.executeQuery();
            int cnt=0;         
            while(rs.next())
            {
                cnt++;  
                AcHeadCode=rs.getString("ACCOUNT_HEAD_CODE");
                System.out.println("AcHeadCode"+AcHeadCode);
                SL_TYPE=rs.getInt("SUB_LEDGER_TYPE_CODE");
                System.out.println("SL_TYPE"+SL_TYPE);
                SL_CODE=rs.getInt("SUB_LEDGER_CODE");
                System.out.println("SL_CODE"+SL_CODE);
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
                
                try
                {
                    ps2=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                    ps2.setInt(1,SL_TYPE);
                    rs2=ps2.executeQuery();
                    if(rs2.next())
                    {
                        SLDesc=rs2.getString("SUB_LEDGER_TYPE_DESC");
                    }
                    ps2.close();
                    //rs1.close();
                }
                catch(Exception que)
                {
                System.out.println("exception in fetching the account subledger type....."+que);
                }
                try
                {
                    ps2=con.prepareStatement("select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=? and SL_CODE=?");
                    ps2.setInt(1,SL_TYPE);
                    ps2.setInt(2,SL_CODE);
                    rs2=ps2.executeQuery();
                    if(rs2.next())
                    {
                        SLCodeDesc=rs2.getString("SL_CODENAME");
                    }
                    ps2.close();
                    //rs1.close();
                }
                catch(Exception que)
                {
                System.out.println("exception in fetching the account subledger type....."+que);
                }
                
                 String MONTH_CLOSING_BALANCE=rs.getString("MONTH_CLOSING_BALANCE");
                 String MONTH_CLOSING_BAL_DR_CR_IND=rs.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                
                 if(MONTH_CLOSING_BALANCE=="0")
                 {
                 MONTH_CLOSING_BALANCE="";
                 }
                 else
                 {
                 MONTH_CLOSING_BALANCE=MONTH_CLOSING_BALANCE;
                 }
                              
                 //End Chage Date 30-Nov-2006 B//
               
                
                
                
                out.println("<tr id='" + SLCodeDesc + "'>");   
                out.println("<td><a href=\"javascript:loadTabAll('" + AcHeadCode + "','"+SL_TYPE+"','"+SL_CODE+"')\">Edit</a></td>");
                
                out.println("<td>"+AcHeadCode+"</td>");
                out.println("<td>"+AcHeadName+"</td>");
                out.println("<td>"+SLDesc+"</td>");
                out.println("<td>"+SLCodeDesc+ "</td>");
             //   out.println("<td>"+currCR+"</td>");
             //   out.println("<td>"+currDB+"</td>");
               // out.println("<td>"+MONTH_OPENING_BALANCE+"</td>");
             //   out.println("<td>"+MONTH_OPENING_BAL_DR_CR_IND+"</td>");
                //out.println("<td>"+CURRENT_MONTH_DEBIT+"</td>");
             //  out.println("<td>"+CURRENT_MONTH_CREDIT+ "</td>");
                out.println("<td>"+MONTH_CLOSING_BALANCE+"</td>");
                out.println("<td>"+MONTH_CLOSING_BAL_DR_CR_IND+"</td>");
              //  out.println("<td>"+DR_UPDATE_LAST_DATE+"</td>");
              //  out.println("<td>"+CR_UPDATE_LAST_DATE+"</td></tr>");*/
                
               
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
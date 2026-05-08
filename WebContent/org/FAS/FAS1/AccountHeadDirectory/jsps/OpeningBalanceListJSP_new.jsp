<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Cheque Book List</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" >
    function loadTabAll(acccode)
    {
         Minimize();
         opener.doParentAcc(acccode);
         return true;
    }
    
    function Minimize() 
    {
         window.close();
         opener.window.focus();
    }

    </script>
   
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
        
       // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
        
        
        int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0;
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
              &nbsp;List of Opening Balance for General Ledger Account Heads
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
        A/c Head
       </th>
       <th>
        Upto Date CR Balance
       </th>
       <th>
        Upto Date DR Balance
       </th>     
      <th>
        Last Dr Update Date
      </th>
      <th>
        Last Cr Update Date
      </th>
    
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
                String AcHeadName="";
                String AcHeadCode="";
                try
                {
                    ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,trim(to_char(UPTO_CREDIT_BALANCE,'99999999999999.99')) as UPTO_CREDIT_BALANCE ,trim(to_char(UPTO_DEBIT_BALANCE,'99999999999999.99')) as UPTO_DEBIT_BALANCE,to_char(DR_UPDATE_LAST_DATE,'dd/mm/yyyy') as DR_UPDATE_LAST_DATE,to_char(CR_UPDATE_LAST_DATE,'dd/mm/yyyy') as CR_UPDATE_LAST_DATE from FAS_GL_UPTO_DATA where  ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and year=? and month=? order by ACCOUNT_HEAD_CODE");
                    System.out.println("cmbAcc_UnitCode ::"+cmbAcc_UnitCode);
                    ps.setInt(1,cmbAcc_UnitCode);
                    System.out.println("cmbOffice_code ::"+cmbOffice_code);
                    ps.setInt(2,cmbOffice_code);
                    System.out.println("financeyear ::"+financeyear);
                    ps.setString(3,financeyear);
                    System.out.println("Cashbook_year ::"+Cashbook_year);
                    ps.setInt(4,Cashbook_year);
                    System.out.println("Cashbook_month ::"+Cashbook_month);
                    ps.setInt(5,Cashbook_month);
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
                            String UptoCRBalance=rs.getString("UPTO_CREDIT_BALANCE").trim();
                            System.out.println(UptoCRBalance);
                            String UptoDBBalance=rs.getString("UPTO_DEBIT_BALANCE").trim();
                            System.out.println(UptoDBBalance);
                            String DR_UPDATE_LAST_DATE=rs.getString("DR_UPDATE_LAST_DATE");
                            String CR_UPDATE_LAST_DATE=rs.getString("CR_UPDATE_LAST_DATE");
                           
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
                            
                            out.println("<tr id='" + AcHeadCode + "'>");   
                            out.println("<td><a href=\"javascript:loadTabAll('" + AcHeadCode + "')\">Edit</a></td>");
                            
                            out.println("<td>"+AcHeadCode+"</td>");
                            out.println("<td>"+AcHeadName+"</td>");
                            out.println("<td align='right' >"+UptoCRBalance+"</td>");
                            out.println("<td align='right'>"+UptoDBBalance+ "</td>");                            
                            out.println("<td align='center'>"+DR_UPDATE_LAST_DATE+"</td>");
                            out.println("<td align='center'>"+CR_UPDATE_LAST_DATE+"</td></tr>");
                    
                    
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>List of Subsidiary Ledger Form</title>
    <script type="text/javascript" src="../scripts/listofsubsidiary_ledger_form.js"></script>
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  
  <% 
      System.out.println("...............list all jsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
       
           int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0,SL_Type=0,Type_Code=0;
           
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
              &nbsp;List of Subsidiary Ledger Account Heads
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
       <th>
       Select
       </th>
       <th>
       Account Head Code
       </th>
       <th>
      Project ID
       </th>
       <th>
       Closing Balance
       </th>
       <th>
      CR/DB
       </th>
       
       <th>
      Last Date
       </th>           
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
         String AcHeadName="";
         String AcHeadCode="";
         String l_date="";
           try
           {
            ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,PROJECT_ID,CLOSING_BALANCE,CLOSING_BALANCE_DR_CR_IND,LAST_DATE_UPDATED from FAS_SELF_BALANCE_MASTER  where  ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? order by ACCOUNT_HEAD_CODE");
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);
            ps.setInt(3,Cashbook_year);
            ps.setInt(4,Cashbook_month);
            ps.setInt(5,SL_Type);
            ps.setInt(6,Type_Code);
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
                String ac_head=rs.getString("ACCOUNT_HEAD_CODE");
                System.out.println(ac_head);
                 String project_id=rs.getString("PROJECT_ID");
                 System.out.println(project_id);
                 String closingbalance=rs.getString("CLOSING_BALANCE");
                 System.out.println(closingbalance);
                 String balance_dc_cr=rs.getString("CLOSING_BALANCE_DR_CR_IND");
                 System.out.println(balance_dc_cr);
                 java.sql.Date last_date=rs.getDate("LAST_DATE_UPDATED");
                 System.out.println(last_date);
                  if(last_date==null)
                    {
                        l_date="Not Specified";
                    }
                    else
                    {
                        try
                        {
                            java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                            l_date=sdf.format(last_date);
                        }
                        catch(Exception e)
                        {
                            System.out.println("error while formatting date : " + e);
                            l_date="Not Specified";
                        }
                    }         
                    
                    System.out.println(l_date);

                 
                out.println("<tr id='" + AcHeadCode + "'>");   
                //out.println("<td><a href=\"javascript:loadTabAll('" + AcHeadCode + "')\" >Edit</a></td>");

               out.println("<td><a href=\"javascript:call_Opener('" + AcHeadCode + "')\" >Edit</a></td>");
                
                out.println("<td>"+ac_head+"</td>");
                out.println("<td>"+project_id+"</td>");
                out.println("<td>"+closingbalance+"</td>");
                
                out.println("<td>"+balance_dc_cr+"</td>");
                out.println("<td>"+l_date+"</td>");
               
                          
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
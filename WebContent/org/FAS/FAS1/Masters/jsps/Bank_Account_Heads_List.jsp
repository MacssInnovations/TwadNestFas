<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Account Heads List</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(accHeadId)
    {
                
                var accHeadCode="",accHeadDesc="",bankid="",operID="";
                 accHeadCode=accHeadId;
                 r=document.getElementById(accHeadCode);
                 rcells=r.cells;
                 //alert(rcells.item(1).firstChild.nodeValue);
                 //alert(rcells.item(2).firstChild.nodeValue);
                 //alert(rcells.item(3).firstChild.nodeValue);
                 //alert(rcells.item(4).firstChild.nodeValue);
                 //alert(rcells.item(5).firstChild.nodeValue);
                 //alert(rcells.item(6).firstChild.nodeValue);
                 accHeadDesc=rcells.item(2).firstChild.nodeValue;
                 bankid=rcells.item(3).firstChild.nodeValue;
                 operID=rcells.item(5).firstChild.nodeValue;
     
        Minimize();
    
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentBankAccHeads(accHeadCode,accHeadDesc,bankid,operID);
        //return true;
   }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>
  </head>
  <body  bgcolor="rgb(255,255,225)">
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
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
  
  
 
        
  <form name="frmBankAccHeadsList" id="frmBankAccHeadsList">
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
              List of Bank Account Heads 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      <th>Select </th>
       <th>Account Head Code </th>
        <th style="display:none"> Account Head Name </th>
       <th style="display:none"> Bank Id </th>
       <th>Bank Name       </th>
       <th style="display:none">Operational Mode Id       </th>
       <th>Operational Mode       </th>
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
           try
           {
            ps=con.prepareStatement("select acc.BANK_ID,trim(acc.AC_OPERATIONAL_MODE_ID) as OPER_MODEID,acc.AC_HEAD_CODE,c.ACCOUNT_HEAD_DESC ,o.AC_OPERATIONAL_MODE,b.BANK_NAME from FAS_MST_BANK_ACCOUNT_HEADS acc,FAS_MST_BANKS b,FAS_MST_AC_OPER_MODES o,COM_MST_ACCOUNT_HEADS c where acc.AC_HEAD_CODE=c.ACCOUNT_HEAD_CODE  and b.BANK_ID=acc.BANK_ID and o.AC_OPERATIONAL_MODE_ID=acc.AC_OPERATIONAL_MODE_ID");
            rs=ps.executeQuery();
           int cnt=0;            
            while(rs.next())
            {
               cnt++;
                int acc_head_code=rs.getInt("AC_HEAD_CODE");
                 System.out.println(acc_head_code);
                out.println("<tr id='" + acc_head_code + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + acc_head_code + "')\">Edit</a></td>");
                out.println("<td>"+rs.getInt("AC_HEAD_CODE")+"-"+rs.getString("ACCOUNT_HEAD_DESC")+"</td>");
                out.println("<td style='display:none'>"+rs.getString("ACCOUNT_HEAD_DESC")+"</td>");
                out.println("<td style='display:none'>"+rs.getInt("BANK_ID")+"</td>");
                out.println("<td>"+rs.getString("BANK_NAME")+"</td>");
                                 System.out.println("dfd");

                out.println("<td style='display:none'>"+rs.getString("OPER_MODEID")+ "</td>");
                 System.out.println(rs.getString("OPER_MODEID"));
                out.println("<td>"+rs.getString("AC_OPERATIONAL_MODE")+"</td></tr>");
                 System.out.println(rs.getString("AC_OPERATIONAL_MODE"));
               
            }
            if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td></tr>");
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
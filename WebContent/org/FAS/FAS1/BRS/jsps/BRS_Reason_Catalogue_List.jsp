<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bill Receipt Register List</title>
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                var reason_code="";
                var reason_short_desc="";
                var reason_desc="";
                
                r=document.getElementById(rowID);
                rcells=r.cells;
      
                reason_code = rcells.item(1).firstChild.nodeValue;
                reason_short_desc  = rcells.item(2).firstChild.nodeValue;
                reason_desc  = rcells.item(3).firstChild.nodeValue;                               
                            
                Minimize();
                opener.doParentBankAccNumbers( reason_code,reason_short_desc, reason_desc );
                self.close();       
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
  <body  >
   <%
     Connection con=null;
     ResultSet rs=null;
     PreparedStatement ps=null;
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
             System.out.println("Connected susscessfully in list file ");
   }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
        
  <form name="frmBillReceiptRegisterList" id="frmBillReceiptRegisterList">
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
              List of Bank Account Number 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Reason Code </th>
       <th>Reason Short Description </th>
       <th>Reason Description </th>       
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
                     
            String sql_que="SELECT reason_code,  reason_short_desc, reason_desc FROM fas_brs_reason_catalogue";
            ps=con.prepareStatement(sql_que);                       
            rs=ps.executeQuery();
            int cnt=0;            
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                
                out.println("<td>"+rs.getInt("reason_code")+"</td>");
                out.println("<td>"+rs.getString("reason_short_desc")+"</td>");
                out.println("<td>"+rs.getString("reason_desc")+"</td></tr>");                                       
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
                     onclick="self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>
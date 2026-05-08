<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>TPA Authorisation List</title>
    
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
                var reason_desc="authunit"+rowID;
               // alert("rowID====>"+rowID);
                //alert("reason_desc==>"+reason_desc);
                r=document.getElementById(rowID);
                rcells=r.cells;
      
                authunit = document.getElementById(reason_desc).value;
                tpatype  = rcells.item(2).firstChild.nodeValue;
                reason  = rcells.item(3).firstChild.nodeValue;
                effectivedate  = rcells.item(4).firstChild.nodeValue;  
                acceptunit=document.getElementById("accept"+rowID).value;      
                particulars  = rcells.item(6).firstChild.nodeValue; 
                     
                Minimize();
                opener.doParentTPA(authunit,tpatype,reason,effectivedate,acceptunit,particulars);
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
  <body  bgcolor="rgb(255,255,225)">
   <%
     Connection con=null;
     ResultSet rs=null,rs1=null;
     PreparedStatement ps=null,ps1=null;
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
             TPA Authorisation List
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Authorised Accounting Unit </th>
        <th>TPA Type</th>
       <th>Reason</th>
      
       <th>Effective Date</th>
       <th>Accepting Unit</th>
       <th>Particulars</th>
             
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
        	   int acUnit=Integer.parseInt(request.getParameter("acc_unit").trim());
        	   int officeId=Integer.parseInt(request.getParameter("office_id").trim());
        	   
        	  // to_char(Date_effective_from,'dd/mm/yyyy') *for date conversion.....
        	  
            String sql_que="SELECT TPA_TYPE, "+
				            	 " AUTHORIZED_ACCOUNTING_UNIT_ID, "+
				            	 " REASON_FOR_TRANSFER, "+
				            	 " TO_CHAR(EFFECTIVE_DATE,'dd/mm/yyyy') AS EFFECTIVE_DATE, "+
				            	"  PARTICULARS, "+
				            	"  ACCEPT_ACCOUNTING_UNIT_ID, "+
				            	"  au.ACCOUNTING_UNIT_NAME "+
				            	" FROM FAS_TPA_AHUTHORIZATION_SYSTEM tas,FAS_MST_ACCT_UNITS au "+
				            	" WHERE tas.ACCOUNTING_UNIT_ID    =? "+
				            	" AND tas.ACCOUNTING_FOR_OFFICE_ID=? "+
				            	" and tas.AUTHORIZED_ACCOUNTING_UNIT_ID=au.ACCOUNTING_UNIT_ID";
            ps=con.prepareStatement(sql_que);  
            ps.setInt(1,acUnit);
            ps.setInt(2,officeId);
            rs=ps.executeQuery();
            int cnt=0;  
            String connectionType="";
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                %>
                
                <input type="hidden" value="<%=rs.getInt("AUTHORIZED_ACCOUNTING_UNIT_ID")%>" id="<%="authunit"+cnt%>" />
                <input type="hidden" value="<%=rs.getInt("ACCEPT_ACCOUNTING_UNIT_ID")%>" id="<%="accept"+cnt%>" />
                <%
                //out.println("<input type=hidden value="+rs.getInt("AUTHORIZED_ACCOUNTING_UNIT_ID")+" id=authunit"+cnt+"/ >");
                out.println("<td>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</td>");
                out.println("<td>"+rs.getString("TPA_TYPE")+"</td>");
                out.println("<td>"+rs.getString("REASON_FOR_TRANSFER")+"</td>");
                out.println("<td>"+rs.getString("EFFECTIVE_DATE")+"</td>");
               // out.println("<input type=hidden value="+rs.getInt("ACCEPT_ACCOUNTING_UNIT_ID")+" id=accept"+cnt+"/ >");
                ps1=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                ps1.setInt(1,rs.getInt("ACCEPT_ACCOUNTING_UNIT_ID"));
               rs1=ps1.executeQuery();
                rs1.next();
                
                
                out.println("<td>"+rs1.getString("ACCOUNTING_UNIT_NAME")+"</td>");
                out.println("<td>"+rs.getString("PARTICULARS")+"</td></tr>");
               rs1.close();
                ps1.close();
            }
             if(cnt==0)
             out.println("<tr><td align=center colspan=10>No data found<td></tr>");     
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
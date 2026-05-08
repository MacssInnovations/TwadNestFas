<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>EB Energy Quota Details List</title>
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                               
                r=document.getElementById(rowID);
                rcells=r.cells;


                var serviceno=rcells.item(1).firstChild.nodeValue;
            	var billno=rcells.item(4).firstChild.nodeValue;
            	var billdate=rcells.item(5).firstChild.nodeValue;
            	var cashmonth=rcells.item(3).firstChild.nodeValue;
            	var cashyear=rcells.item(2).firstChild.nodeValue;
            	var unitstime=rcells.item(6).firstChild.nodeValue;
            	var unitsquotafixed=rcells.item(7).firstChild.nodeValue;
            	var unitsquotaexcess=rcells.item(8).firstChild.nodeValue;
            	var kvatime=rcells.item(9).firstChild.nodeValue;
            	var epdunitsfixed=rcells.item(10).firstChild.nodeValue;
            	var epdunitsexcess=rcells.item(11).firstChild.nodeValue;
            	var remarks=rcells.item(12).firstChild.nodeValue;
                Minimize();
                opener.doParentEBMaster(serviceno,billno,billdate,cashmonth,cashyear,unitstime,unitsquotafixed,unitsquotaexcess,kvatime,epdunitsfixed,epdunitsexcess,remarks);
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
              List of EB Energy Quota Details
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Service No</th>
       <th>Cash Book Year</th>
       <th>Cash Book Month</th>
       <th>Bill No.</th>
       <th>Bill Date</th>
       <th>EPK_Time</th>
       <th>EPK_Quota Fixed</th>
       <th>EPK_Units_Excess</th>
       <th>EPD_KVA_Time</th>
       <th>EPD_Quota Fixed </th>   
        <th>EPD_Units_Excess</th>      
        <th>Remarks</th>          
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
        	   int acUnit=Integer.parseInt(request.getParameter("acc_unit").trim());
        	   int officeId=Integer.parseInt(request.getParameter("office_id").trim());
        	   
        	  // to_char(Date_effective_from,'dd/mm/yyyy') *for date conversion.....
        	  
            String sql_que="SELECT SERVICE_NO,BILL_NO,CASHBOOK_YEAR,CASHBOOK_MONTH,to_char(BILL_DATE,'dd/mm/yyyy') as BILL_DATE,EPK_UNITS_TIME,EPK_UNITS_QUOTA_FIXED,EPK_UNITS_QUOTA_EXCESS,EPD_KVA_TIME,EPD_UNITS_QUOTA_FIXED,EPD_UNITS_QUOTA_EXCESS,REMARKS FROM FAS_EB_ENERGY_QUOTA_MST where  ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?";
            ps=con.prepareStatement(sql_que);  
            ps.setInt(1,acUnit);
            ps.setInt(2,officeId);
            rs=ps.executeQuery();
            int cnt=0;  
            DecimalFormat df=new DecimalFormat("#0.00");
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                out.println("<td>"+rs.getString("SERVICE_NO")+"</td>");
               
                out.println("<td>"+rs.getInt("CASHBOOK_YEAR")+"</td>");
                out.println("<td>"+rs.getInt("CASHBOOK_MONTH")+"</td>");
                out.println("<td>"+rs.getString("BILL_NO")+"</td>");
                
                out.println("<td>"+rs.getString("BILL_DATE")+"</td>");
                out.println("<td>"+rs.getString("EPK_UNITS_TIME")+"</td>");
                                
                out.println("<td>"+df.format(rs.getFloat("EPK_UNITS_QUOTA_FIXED"))+"</td>");
                out.println("<td>"+df.format(rs.getFloat("EPK_UNITS_QUOTA_EXCESS"))+"</td>");
                out.println("<td>"+rs.getString("EPD_KVA_TIME")+"</td>");
                out.println("<td>"+df.format(rs.getFloat("EPD_UNITS_QUOTA_FIXED"))+"</td>");
                out.println("<td>"+df.format(rs.getFloat("EPD_UNITS_QUOTA_EXCESS"))+"</td>");
                               
                out.println("<td>"+rs.getString("REMARKS")+"</td></tr>");     
            }
             if(cnt==0)
             out.println("<tr><td align=center colspan=13>No data found<td></tr>");     
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
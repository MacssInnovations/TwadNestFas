<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>EB Meter Master List</title>
    
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
      
                serviceno = rcells.item(1).firstChild.nodeValue;
                tariff  = rcells.item(2).firstChild.nodeValue;
                permittedmd  = rcells.item(3).firstChild.nodeValue;
                meterno  = rcells.item(4).firstChild.nodeValue;        
                supplydate  = rcells.item(5).firstChild.nodeValue; 
                htlt= rcells.item(6).firstChild.nodeValue;       
                connectiontype  = rcells.item(7).firstChild.nodeValue;        
                effectivefrom  = rcells.item(8).firstChild.nodeValue;          
                effectiveupto  = rcells.item(9).firstChild.nodeValue;                                       
                remark  = rcells.item(10).firstChild.nodeValue;            
                Minimize();
                opener.doParentEBMaster(serviceno,tariff,permittedmd,meterno,supplydate,connectiontype,effectivefrom,effectiveupto,remark,htlt);
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

            // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
              List of EB Meter Master 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Service No</th>
       <th>Tariff App./BLd.</th>
       <th>Permitted MD (in KVA)</th>
       <th>Meter Serial No</th>
       <th>Date of Supply</th>
       <th>HT or  LT</th>
       <th>Type Of Connection</th>
       <th>Effective Date From </th>   
        <th>Effective Date upto</th>      
        <th>Remarks</th>          
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
        	   int acUnit=Integer.parseInt(request.getParameter("acc_unit").trim());
        	   int officeId=Integer.parseInt(request.getParameter("office_id").trim());
        	   
        	  // to_char(Date_effective_from,'dd/mm/yyyy') *for date conversion.....
        	  
            String sql_que="SELECT SERVICE_NO,TARIFF_APP,PERMITTED_MD_IN_KVA,METER_SL_NO,to_char(DATE_OF_SUPPLY,'dd/mm/yyyy') as DATE_OF_SUPPLY,HT_OR_LT,TYPE_OF_CONNECTION,to_char(DATE_EFFECTIVE_FROM,'dd/mm/yyyy') as DATE_EFFECTIVE_FROM,to_char(DATE_EFFECT_UPTO,'dd/mm/yyyy') as DATE_EFFECT_UPTO,REMARKS FROM FAS_EB_METER_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?";
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
                out.println("<td>"+rs.getString("SERVICE_NO")+"</td>");
                out.println("<td>"+rs.getString("TARIFF_APP")+"</td>");
                out.println("<td>"+rs.getInt("PERMITTED_MD_IN_KVA")+"</td>");
                out.println("<td>"+rs.getInt("METER_SL_NO")+"</td>");
                out.println("<td>"+rs.getString("DATE_OF_SUPPLY")+"</td>");
                out.println("<td>"+rs.getString("HT_OR_LT")+"</td>");
                if(rs.getString("TYPE_OF_CONNECTION").equalsIgnoreCase("C"))
                {
                	connectionType="Commercial";
                }else {
                	connectionType="Industrial";
                }
                
                out.println("<td>"+connectionType+"</td>");
                out.println("<td>"+rs.getString("DATE_EFFECTIVE_FROM")+"</td>");
                out.println("<td>"+rs.getString("DATE_EFFECT_UPTO")+"</td>");  
                out.println("<td>"+rs.getString("REMARKS")+"</td></tr>");     
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
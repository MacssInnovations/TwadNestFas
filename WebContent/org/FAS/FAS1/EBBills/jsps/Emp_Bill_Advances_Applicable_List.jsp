<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Employee Bill Advances Applicable List</title>
    
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

				var finyear=rcells.item(1).firstChild.nodeValue;
                
            	var cashmonth=rcells.item(3).firstChild.nodeValue;
            	var cashyear=rcells.item(2).firstChild.nodeValue;
            	var majortype=document.getElementById("a"+rowID).value;	
				var minortype=document.getElementById("b"+rowID).value;	
				var applicable=rcells.item(6).firstChild.nodeValue;
				var dateupto="";
				try{
					dateupto=rcells.item(7).firstChild.nodeValue;
				}catch(e){}
                Minimize();
                opener.doParentEmpadvance(finyear,majortype,minortype,cashmonth,cashyear,applicable,dateupto);
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
     PreparedStatement ps1,ps2,ps3;
     ResultSet rs1,rs2,rs3;
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
             Employee Bill Advances Applicable List
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
        <th>Financial Year</th>
         <th>Cash Book Year</th>
       <th>Cash Book Month</th>
       <th>Bill Major Type</th>
        <th>Bill Minor Type</th>
              
        <th>Advance Applicable</th>
         <th>Applicable Upto</th>    
              
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
        	   String finYear=request.getParameter("finyear").trim();
        	   //int officeId=Integer.parseInt(request.getParameter("office_id").trim());
        	   
        	  // to_char(Date_effective_from,'dd/mm/yyyy') *for date conversion.....
        	  
            String sql_que="SELECT FINANCIAL_YEAR,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,ADVANCE_APPLY,to_char(APPLICABLE_UPTO_DATE,'dd/mm/yyyy') as APPLICABLE_UPTO_DATE FROM FAS_BILL_ADV_APPL_MST where  FINANCIAL_YEAR=?";
            ps=con.prepareStatement(sql_que);  
            ps.setString(1,finYear);
            //ps.setInt(2,officeId);
            rs=ps.executeQuery();
            int cnt=0;  
          
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                
                out.println("<td>"+rs.getString("FINANCIAL_YEAR")+"</td>");
                out.println("<td>"+rs.getInt("CASHBOOK_YEAR")+"</td>");
                out.println("<td>"+rs.getInt("CASHBOOK_MONTH")+"</td>");
                
                
                int majorType=rs.getInt("BILL_MAJOR_TYPE_CODE");
                int minorType=rs.getInt("BILL_MINOR_TYPE_CODE");
               
                ps1=con.prepareStatement("select BILL_MAJOR_TYPE_DESC  from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE=?");  
                ps1.setInt(1,majorType);
                
                rs1=ps1.executeQuery();
                rs1.next();
                out.println("<input type=hidden id=a"+cnt+" value="+majorType+">");
                out.println("<td>"+rs1.getString("BILL_MAJOR_TYPE_DESC")+"</td>");
               
                ps2=con.prepareStatement("select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MINOR_TYPE_CODE=?");  
                ps2.setInt(1,minorType);
                
                rs2=ps2.executeQuery();
                rs2.next();
                out.println("<input type=hidden id=b"+cnt+" value="+minorType+">");
                out.println("<td>"+rs2.getString("BILL_MINOR_TYPE_DESC")+"</td>");
                
              String applicable=rs.getString("ADVANCE_APPLY");
                if(applicable.equalsIgnoreCase("Y"))
                {
                	applicable="Yes";
                }else if(applicable.equalsIgnoreCase("N"))
                {
                	applicable="No";
                }
                out.println("<td>"+applicable+"</td>");
                String date=rs.getString("APPLICABLE_UPTO_DATE");
                if(date==null){
                	date="";
                }
              
                	
                	 out.println("<td>"+date+"</td>");
               
                
            }
             if(cnt==0)
             out.println("<tr><td align=center colspan=13>No data found<td></tr>");     
           }
          catch(Exception e)
          {
            out.println(e);
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
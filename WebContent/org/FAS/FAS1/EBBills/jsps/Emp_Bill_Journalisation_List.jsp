<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Employee Bill Journalisation List</title>
    
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


                
//            	var cashmonth=rcells.item(5).firstChild.nodeValue;
//            	var cashyear=rcells.item(4).firstChild.nodeValue;
            	
            	var remarks=rcells.item(4).firstChild.nodeValue;

				var majortype=document.getElementById("a"+rowID).value;	
				var minortype=document.getElementById("b"+rowID).value;	
				var journaltype=document.getElementById("c"+rowID).value;	

                Minimize();
                opener.doParentEmpbill(majortype,minortype,journaltype,remarks);
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
              List of Employee Bill Journalisation
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Bill Major Type</th>
        <th>Bill Minor Type</th>
       <th>Journal Type</th>
      
     <!-- <th>Cash Book Year</th>
       <th>Cash Book Month</th>  -->
           
        <th>Remarks</th>          
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
        	   int acUnit=Integer.parseInt(request.getParameter("acc_unit").trim());
        	   int officeId=Integer.parseInt(request.getParameter("office_id").trim());
        	   
        	  // to_char(Date_effective_from,'dd/mm/yyyy') *for date conversion.....
        	  
            String sql_que="SELECT BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,JOURNAL_TYPE_CODE,REMARKS FROM FAS_EMP_BILL_LJV where  ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?";
            ps=con.prepareStatement(sql_que);  
            ps.setInt(1,acUnit);
            ps.setInt(2,officeId);
            rs=ps.executeQuery();
            int cnt=0;  
          
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                int majorType=rs.getInt("BILL_MAJOR_TYPE_CODE");
                int minorType=rs.getInt("BILL_MINOR_TYPE_CODE");
                int journalType=rs.getInt("JOURNAL_TYPE_CODE");
                ps1=con.prepareStatement("select BILL_MAJOR_TYPE_DESC  from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE=?");  
                ps1.setInt(1,majorType);
                
                rs1=ps1.executeQuery();
                rs1.next();
                out.println("<input type=hidden id=a"+cnt+" value="+majorType+">");
                out.println("<td>"+rs1.getString("BILL_MAJOR_TYPE_DESC")+"</td>");
               
                ps2=con.prepareStatement("select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MINOR_TYPE_CODE=?");  
                ps2.setInt(1,minorType);
                
                rs2=ps2.executeQuery();
                if(rs2.next()){                	
                	out.println("<td>"+rs2.getString("BILL_MINOR_TYPE_DESC")+"</td>");	                
                }else{
                	out.println("<td>--</td>");
                }
                out.println("<input type=hidden id=b"+cnt+" value="+minorType+">");
                ps3=con.prepareStatement("select JOURNAL_TYPE_DESC   from FAS_MST_JOURNAL_TYPE where CATEGORY='G' and JOURNAL_TYPE_CODE=?");  
                ps3.setInt(1,journalType);
                
                rs3=ps3.executeQuery();
                if(rs3.next()){
	                out.println("<input type=hidden id=c"+cnt+" value="+journalType+">");
	                out.println("<td>"+rs3.getString("JOURNAL_TYPE_DESC")+"</td>");
                }
               
                out.println("<td>"+rs.getString("REMARKS")+"</td>");
                
               
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
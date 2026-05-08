<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
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
                var txtCashbook_Year="", txtCashbook_Month="",   txtStatus_GJV="", txtStatus_SJV="",  txtRemarks="";                
                
                r=document.getElementById(rowID);
                rcells=r.cells;
      
                txtCashbook_Year       = rcells.item(1).firstChild.nodeValue;
                txtCashbook_Month  = rcells.item(2).firstChild.nodeValue;
                txtStatus_GJV              = rcells.item(3).firstChild.nodeValue;           
                 txtStatus_SJV              = rcells.item(4).firstChild.nodeValue; 
                if(rcells.item(5).firstChild.nodeValue!='--')
                txtRemarks = rcells.item(5).firstChild.nodeValue;          
                            
        Minimize();
        opener.doParentBankAccNumbers(txtCashbook_Year, txtCashbook_Month,   txtStatus_GJV, txtStatus_SJV,  txtRemarks); 
        self.close();
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
              Supplement Minus&nbsp;Master Details 
            </div></td>
        </tr>
        
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>
            Cashbookyear
          </th>
       <th>
            CashbookMonth
          </th>       
       <th>GjvMinus </th>
       <th>SjvMinus </th>
       <th>Remarks</th>       
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
                     
            String sql_que="select CASHBOOK_YEAR , CASHBOOK_MONTH , GJV_MINUS, SJV_MINUS , REMARKS from FAS_SUPPLE_MINUS_MST";
            ps=con.prepareStatement(sql_que);                       
            rs=ps.executeQuery();
            int cnt=0;            
          try{
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                
                out.println("<td>"+rs.getInt("CASHBOOK_YEAR")+"</td>");
                out.println("<td>"+rs.getString("CASHBOOK_MONTH")+"</td>");                
                out.println("<td>"+rs.getString("GJV_MINUS")+"</td>");                
                out.println("<td>"+rs.getString("SJV_MINUS")+"</td>");
                
                if(rs.getString("REMARKS")!=null)
                out.println("<td>"+rs.getString("REMARKS")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");             
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
    </form>
    
    
    </body>
</html>

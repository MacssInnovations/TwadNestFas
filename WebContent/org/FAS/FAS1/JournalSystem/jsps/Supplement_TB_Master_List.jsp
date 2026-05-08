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
                var txtSupplement_No="", txtSuppl_Closure_date="",   txtStatus="",   txtRemarks="";                
                
                r=document.getElementById(rowID);
                rcells=r.cells;
      
                txtSupplement_No       = rcells.item(1).firstChild.nodeValue;
                txtSuppl_Closure_date  = rcells.item(3).firstChild.nodeValue;
                txtStatus              = rcells.item(4).firstChild.nodeValue;           
                
                if(rcells.item(5).firstChild.nodeValue!='--')
                txtRemarks = rcells.item(5).firstChild.nodeValue;          
                            
        Minimize();
        opener.doParentBankAccNumbers(txtSupplement_No , txtSuppl_Closure_date , txtStatus, txtRemarks );
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
             //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
              Supplement TB Details 
            </div></td>
        </tr>
        
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Supplement No.</th>
       <th>Date</th>       
       <th>Supplement Closure Date </th>
       <th>Status </th>
       <th>Remarks</th>       
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
            int txtCashbook_Month=0;
            int txtCashbook_Year=0;
            try
            {
              txtCashbook_Month=Integer.parseInt(request.getParameter("txtCashbook_Month"));
              txtCashbook_Year=Integer.parseInt(request.getParameter("txtCashbook_Year"));
            }
            catch(Exception e)
            {
              System.out.println(e);
            }
                     
            String sql_que="select SUPPLEMENT_NO , to_char(SUPPL_DATE,'dd/mm/yyyy') as suppl_date  , CASHBOOK_YEAR , CASHBOOK_MONTH , to_char(SUPPL_CLOSURE_DATE,'dd/mm/yyyy') as suppl_closure_date, STATUS , REMARKS from FAS_SUPPLEMENT_GJV  where cashbook_year= ? and cashbook_month = ? ";
            
            ps=con.prepareStatement(sql_que);    
            
            ps.setInt(1,txtCashbook_Year);
            ps.setInt(2,txtCashbook_Month);            
            
            rs=ps.executeQuery();
            int cnt=0;            
          try{
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>"); 
                
                String status=rs.getString("STATUS");
                
                //Commanded on 25_Jun_2018. Vasanthi mam asked to need a provision for edit, closed status also
                
               // if ( status.equalsIgnoreCase("L"))
               // {
                //  out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
               // }
               // else
               // {
               //   out.println("<td>--</td>");
                //}
                                
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                out.println("<td>"+rs.getInt("SUPPLEMENT_NO")+"</td>");
                out.println("<td>"+rs.getString("SUPPL_DATE")+"</td>");                
                out.println("<td>"+rs.getString("SUPPL_CLOSURE_DATE")+"</td>");                
                
                out.println("<td>"+rs.getString("STATUS")+"</td>");
                
                if(rs.getString("REMARKS")!=null)
                out.println("<td>"+rs.getString("REMARKS")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");             
            }
            if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td><td></td><td></td></tr>");     
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Centage Rates List</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                var txtCategory_Code="", txtCategory_Desc="",   cmbCategoryType="" ;
                
                r=document.getElementById(rowID);
                rcells=r.cells;
      
                txtCategory_Code = rcells.item(1).firstChild.nodeValue;
                txtCentageRates  = rcells.item(3).firstChild.nodeValue;
                txtFinancialYear  = rcells.item(4).firstChild.nodeValue;               
                            
        Minimize();
        opener.doParentBankAccNumbers(txtCategory_Code,txtCentageRates,txtFinancialYear);
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
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
               List of Centage Rates
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Category Code </th>
       <th>Category Desc </th>
       <th>Centage Rates </th>
       <th>Financial Year </th>              
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
           
            int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));  
            int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code")); 
            
                    String sql_que="               select           \n" + 
                            "                  *                 \n" + 
                            "                  from              \n" + 
                            "                  (                 \n" + 
                            "                  select            \n" + 
                            "                    ( DEFAULT_CENTAGE_CATEGORY_TYPE || CATEGORY_CODE ) as category_code,       \n" + 
                            "                     CENTAGE_RATE,        \n" + 
                            "                     FINANCIAL_YEAR       \n" + 
                            "                  from                    \n" + 
                            "                     FAS_CENTAGE_RATES    \n" + 
                            "                  where                   \n" + 
                            "                      ACCOUNTING_UNIT_ID = ?        \n" + 
                            "                  and ACCOUNTING_FOR_OFFICE_ID= ?   \n" + 
                            "                  ) a                              \n" + 
                            "                  \n" + 
                            "                  left outer join                   \n" + 
                            "                  \n" + 
                            "                  (                                 \n" + 
                            "                  select                            \n" + 
                            "                   (CATEGORY_TYPE || CATEGORY_CODE) as cat_code,      \n" + 
                            "                    CATEGORY_DESC as cat_desc       \n" + 
                            "                  from                              \n" + 
                            "                    FAS_CENTAGE_CATEGORY_HO_MASTER  \n" + 
                            "                  where                             \n" + 
                            "                      ACCOUNTING_UNIT_ID = ?        \n" + 
                            "                  and ACCOUNTING_FOR_OFFICE_ID= ?   \n" + 
                            "                  ) b                               \n" + 
                            "                  on                                \n" + 
                            "                   a.category_code = b.cat_code    ";
            
            ps=con.prepareStatement(sql_que); 
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);
            ps.setInt(3,cmbAcc_UnitCode);
            ps.setInt(4,cmbOffice_code);
            rs=ps.executeQuery();
            int cnt=0;            
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");                
                out.println("<td>"+rs.getString("CATEGORY_CODE")+"</td>");
                out.println("<td>"+rs.getString("cat_desc")+"</td>");
                out.println("<td>"+rs.getString("CENTAGE_RATE")+"</td>");                
                out.println("<td>"+rs.getString("FINANCIAL_YEAR")+"</td>");        
            }
            if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td><td></td></tr>");     
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
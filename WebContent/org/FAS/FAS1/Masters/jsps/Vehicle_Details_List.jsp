<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Account Number List</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    
    function EditHead(rowID)
    {
    
                var dateentry="",finyr="",assetcode="",regdetails="",regno="";
                var asset="",tank="",cubic="",regaddr="",regdate="",make="",yrman="";
                var remarks="",road_tax="";
                   r=document.getElementById(rowID);
                 rcells=r.cells;
      
                
                dateentry=rcells.item(1).firstChild.nodeValue;
                finyr=rcells.item(2).firstChild.nodeValue;
                assetcode=rcells.item(3).firstChild.nodeValue;
                regdetails=rcells.item(4).firstChild.nodeValue;
                regaddr=rcells.item(5).firstChild.nodeValue;
                regdate=rcells.item(6).firstChild.nodeValue;
                regno=rcells.item(7).firstChild.nodeValue;
                make=rcells.item(8).firstChild.nodeValue;
                yrman=rcells.item(9).firstChild.nodeValue;
                cubic=rcells.item(10).firstChild.nodeValue;
                tank=rcells.item(11).firstChild.nodeValue;
                road_tax=rcells.item(12).firstChild.nodeValue;
                remarks=rcells.item(13).firstChild.nodeValue;
                
        Minimize();
    
      
        opener.doParentBankAccNumbers(dateentry,finyr,assetcode,regdetails,regaddr,regno,regdate,make,yrman,cubic,tank,road_tax,remarks);
      
   }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>
<script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmFas_holidays_List.txtCB_Year.value=year
        document.frmFas_holidays_List.txtCB_Month.value=month;
        
         }
    </script>
  </head>
  <body  bgcolor="rgb(255,255,225)">
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    PreparedStatement ps1=null;
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
   ResultSet rs1=null; 
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
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
  <% 
      System.out.println("...............list all LISTjsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
     
       
           int  cmbAcc_UnitCode=0,cmbOffice_code=0;
            try
            {
         	   
           
         	   try {
                	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbAcc_UnitCode ");
                }
                try {
                	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbOffice_code ");
                }
        System.out.println(cmbOffice_code);
       // System.out.println(cmbSectionId);
       
        
            
   %>
  
 
        
  <form name="frmBankBranchList" id="frmBankBranchList">
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
              List of Vehicle Additional Details 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      <th>Select </th>
     
       <th> Date </th>
       <th>Financial Year</th>
       <th>Asset Code </th>

       <th>
           Registration Details
          </th>
          <th>
            Registration Office Address
          </th>
     
       <th>
            Registration Number
          </th>
       
       <th>Registration Date
          </th>
          <th>
           Make
          </th>
         <th>
         Year of Manufacture
         </th>
         <th>Cubic Capacity</th>
         <th>Tank Capacity</th>
         <th>Road Tax Reference</th>
         <th>Remarks</th>
         </tr>
         <tbody id="tb" class="table" align="left">
          <%
          String dateentry=null;
          int assetcode=0;
          String finyr="",regdetails="",regno="",regdate="";
          String asset="",tank="",cubic="",regaddr="",make="",yrman="";
          String remarks="",road_tax="";
          Calendar c;
          
        
                
                String sql_que="select to_char(DATE_OF_ENTRY,'DD/MM/YYYY') as transdate,FIN_YEAR,ASSET_CODE,REGN_OFFICE,REGN_OFFICE_ADDRESS,"+
                	"to_char(REGN_DATE,'DD/MM/YYYY') as regtdate,REGN_NO,MAKE,YEAR_OF_MANUFACTURE,CUBIC_CAPACITY,TANK_CAPACITY,ROAD_TAX_DETAILS,REMARKS FROM FAS_VEHICLE_ADDL_MST "+
                	"where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?";
               
            
            ps=con.prepareStatement(sql_que);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, cmbOffice_code);
            rs=ps.executeQuery();
           int cnt=0; 
           String date_entry="";
           
            while(rs.next())
            {
               
                cnt++;
                date_entry=rs.getString("transdate");
                assetcode=rs.getInt("ASSET_CODE");
                System.out.println("asset"+assetcode);
                finyr=rs.getString("FIN_YEAR");
                System.out.println("finyr"+finyr);
                regdetails=rs.getString("REGN_OFFICE");
                System.out.println("regdetails"+regdetails);
                regaddr=rs.getString("REGN_OFFICE_ADDRESS");
                System.out.println("regaddr"+regaddr);
                regdate=rs.getString("regtdate");
                System.out.println("regdate"+regdate);
                regdetails=rs.getString("REGN_OFFICE");
                System.out.println("regdetails"+regdetails);
                regaddr= rs.getString("REGN_OFFICE_ADDRESS");
                System.out.println("regaddr"+regaddr);
               
                regno=rs.getString("REGN_NO"); 
                System.out.println("regno"+regno);
                make=rs.getString("MAKE"); 
                System.out.println("make"+make);
                yrman=rs.getString("YEAR_OF_MANUFACTURE");
                System.out.println("yrman"+yrman);
                cubic=rs.getString("CUBIC_CAPACITY");
                System.out.println("cubic"+cubic);
                tank=rs.getString("TANK_CAPACITY");
                System.out.println("tank"+tank);
                road_tax=rs.getString("ROAD_TAX_DETAILS");
                System.out.println("road_tax"+road_tax);
                remarks=rs.getString("REMARKS");
                System.out.println("remarks"+remarks);
               
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
               
                out.println("<td>"+rs.getString("transdate")+"</td>");
                
                out.println("<td>"+rs.getString("FIN_YEAR")+"</td>");
                out.println("<td>"+assetcode+"</td>");
            
                out.println("<td>"+rs.getString("REGN_OFFICE")+"</td>");
                out.println("<td>"+rs.getString("REGN_OFFICE_ADDRESS")+"</td>");
                out.println("<td>"+rs.getString("regtdate")+"</td>");
                out.println("<td>"+rs.getString("REGN_NO")+"</td>"); 
                out.println("<td >"+rs.getString("MAKE")+"</td>"); 
                out.println("<td>"+rs.getString("YEAR_OF_MANUFACTURE")+"</td>");
                out.println("<td>"+rs.getString("CUBIC_CAPACITY")+"</td>");
                out.println("<td>"+rs.getString("TANK_CAPACITY")+"</td>");
                out.println("<td >"+rs.getString("ROAD_TAX_DETAILS")+"</td>"); 
                out.println("<td>"+rs.getString("REMARKS")+"</td>");
               
                
            
                
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
    </form>
   </body>
</html>


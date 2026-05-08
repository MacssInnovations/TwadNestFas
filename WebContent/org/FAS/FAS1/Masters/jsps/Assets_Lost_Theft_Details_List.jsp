<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>List for AMC Details of Assets</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    
    function EditHead(rowID)
    {
    // alert("inside edithead");
                var dateentry="",assetcode="",compdetails="",location="";
                var remarks="",iscomp="",compoffice="";
                   r=document.getElementById(rowID);
                 rcells=r.cells;
      
                
                dateentry=rcells.item(2).firstChild.nodeValue;
                alert(rcells.item(1).firstChild.nodeValue);
              // majorclass=rcells.item(1).firstChild.nodeValue;
                assetcode=rcells.item(1).firstChild.nodeValue;
                location=rcells.item(3).firstChild.nodeValue;
                iscomp=rcells.item(4).firstChild.nodeValue;
                compdetails=rcells.item(5).firstChild.nodeValue;
                compoffice=rcells.item(6).firstChild.nodeValue;
                remarks=rcells.item(7).firstChild.nodeValue;
                alert("edit assetcode"+assetcode);
               
        Minimize();
    
      
        opener.doParentBankAccNumbers(dateentry,assetcode,location,iscomp,compdetails,compoffice,remarks);
      
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
  
 
        
  <form name="frmassetamclist" id="frmassetamclist">
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
              List of Assets Lost/Theft Details
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      <th>Select </th>
            
           <th>Asset Code </th>
           <th>Date of Lost/Theft</th>
          <th>
            Physical Location of Asset
          </th>
     
       <th>
           Is Complaint Lodged
          </th>
       <th>Complaint No and Details</th>
       <th>Complaint Lodged Office Name
          </th>
         
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
          
        
                
                String sql_que="select a.ASSET_CODE,to_char(a.DATE_OF_LOST,'DD/MM/YYYY') as transdate,a.PHY_LOCATION,a.ISCOMP_LODGED,"+
                	"a.COMP_DETAILS,a.COMP_LODGED_OFFICE,a.REMARKS FROM FAS_ASSET_LOST_DETAILS a "+
                	"where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ";
               
            
            ps=con.prepareStatement(sql_que);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, cmbOffice_code);
            rs=ps.executeQuery();
            
           int cnt=0; 
           //String amcperiod="";
          // amcperiod=rs.
           
            while(rs.next())
            {
               
                cnt++;

               
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
              
                out.println("<td>"+rs.getInt("ASSET_CODE")+"</td>");
                System.out.println(rs.getInt("ASSET_CODE"));
                out.println("<td>"+rs.getString("transdate")+"</td>");
                System.out.println(rs.getString("transdate"));
                out.println("<td>"+rs.getString("PHY_LOCATION")+"</td>");
                System.out.println(rs.getString("PHY_LOCATION"));
                out.println("<td>"+rs.getString("ISCOMP_LODGED")+"</td>");
                System.out.println(rs.getString("ISCOMP_LODGED"));
                out.println("<td>"+rs.getString("COMP_DETAILS")+"</td>");
                System.out.println(rs.getString("COMP_DETAILS"));
                out.println("<td>"+rs.getString("COMP_LODGED_OFFICE")+"</td>");
                System.out.println(rs.getString("COMP_LODGED_OFFICE"));
                
                out.println("<td>"+rs.getString("REMARKS")+"</td>");
                System.out.println(rs.getString("REMARKS"));
               
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


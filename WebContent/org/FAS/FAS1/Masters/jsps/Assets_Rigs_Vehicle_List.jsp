<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title> List for Numerical OB of Assets </title>
    
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
                var VehicleDesc="",RigId="",VehicleId="",RigDesc="";
               
                   r=document.getElementById(rowID);
                 rcells=r.cells;
      
                 
                
                //alert(rcells.item(1).firstChild.nodeValue);
                //alert(rcells.item(2).firstChild.nodeValue);
                //alert(rcells.item(3).firstChild.nodeValue);
                //alert(rcells.item(4).firstChild.nodeValue);
                RigId=rcells.item(1).firstChild.nodeValue;
                RigDesc=rcells.item(2).firstChild.nodeValue;
                VehicleId=rcells.item(3).firstChild.nodeValue;
                
                VehicleDesc=rcells.item(4).firstChild.nodeValue;
               
             //alert("RigId"+RigId);
             //alert("VehicleId"+VehicleId);
             //alert("VehicleDesc"+VehicleDesc);            
             //alert("RigDesc"+RigDesc);
             
               
        Minimize();
    
      
        opener.doParentBankAccNumbers(VehicleDesc,RigId,VehicleId,RigDesc);
      
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
       
        document.frmassetnumOB.txtCB_Year.value=year
        document.frmassetnumOB.txtCB_Month.value=month;
        
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
           String financial_year="";
            try
            {
         	   financial_year=request.getParameter("financial_year");
           
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
        
        System.out.println(financial_year);
        System.out.println(cmbAcc_UnitCode);
       
        
            
   %>
  
 
        
  <form name="frmassetnumOB" id="frmassetnumOB">
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
              List for Numerical OB of Assets 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      		<th>Select </th>    
            <th>Asset Code of Rigs</th>
           <th style="display:none"> Rig Id </th> 
           <th>Asset Code of Vehicles</th>
           <th style="display:none"> Vehicle Id </th>
           <th>Status</th> 
         </tr>
         <tbody id="tb" class="table" align="left">
          <%
          
        
                
         String sql_que="select * from " +
                        "(select ASSET_CODE_OF_RIG,ASSET_CODE_OF_VEHICLE,STATUS  from FAS_ASSETS_RIGS_AND_VEHICLES where ACCOUNTING_UNIT_ID=?" +
                        " and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? ) a " +
                        " left outer join " +
                        " ( select ASSET_CODE,PARTICULARS as assetrigsdesc from FAS_ASSET_VAL_AC_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and ASSET_MAJOR_CLASS_CODE=? and financial_year=? " +
                        " ) b  " +
                        " on  a.ASSET_CODE_OF_RIG=b.ASSET_CODE " +
                        " left outer join" +
                        "(select ASSET_CODE,PARTICULARS as assetvehicledesc from FAS_ASSET_VAL_AC_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and ASSET_MAJOR_CLASS_CODE=? and financial_year=? " +
                        " ) c " +
                       " on  a.ASSET_CODE_OF_VEHICLE=c.ASSET_CODE ";
               
            
            ps=con.prepareStatement(sql_que);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, cmbOffice_code);
            ps.setString(3,financial_year);
            ps.setInt(4, cmbAcc_UnitCode);
            ps.setInt(5, cmbOffice_code);
            ps.setInt(6,6);
            ps.setString(7,financial_year);
            ps.setInt(8, cmbAcc_UnitCode);
            ps.setInt(9, cmbOffice_code);
            ps.setInt(10,7);
            ps.setString(11,financial_year);
            rs=ps.executeQuery();
            
           int cnt=0; 
           String amcperiod="";
          // amcperiod=rs.
           
            while(rs.next())
            {
               
                cnt++;

               
                out.println("<tr id='" + cnt + "'>");
                if(rs.getString("status").equals("L")){
                	out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");	
                }else{
                	out.println("<td>CANCEL</td>");
                }                                
                out.println("<td style='display:none' >"+rs.getInt("ASSET_CODE_OF_RIG")+"</td>");
                out.println("<td>"+rs.getString("assetrigsdesc")+"</td>");
                System.out.println(rs.getString("assetrigsdesc"));
                out.println("<td style='display:none' >"+rs.getInt("ASSET_CODE_OF_VEHICLE")+"</td>");
                out.println("<td>"+rs.getString("assetvehicledesc")+"</td>");
                System.out.println(rs.getString("assetvehicledesc"));
                if(rs.getString("status").equals("L")){
                	out.println("<td>LIVE</td>");	
                }else{
                	out.println("<td>CANCEL</td>");
                }
                  
               
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


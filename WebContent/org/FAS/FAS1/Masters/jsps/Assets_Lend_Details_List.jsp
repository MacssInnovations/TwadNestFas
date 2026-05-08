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
                var dateentry="",majorclass="",assetcode="",transoffice="",location="";
                var reason="",amcamt="",agrno="",agrdate="";
                var remarks="";
                   r=document.getElementById(rowID);
                 rcells=r.cells;
      
                
                dateentry=rcells.item(7).firstChild.nodeValue;
                //alert(rcells.item(1).firstChild.nodeValue);
                majorclass=rcells.item(1).firstChild.nodeValue;
                assetcode=rcells.item(4).firstChild.nodeValue;
               // alert(assetcode);
                location=rcells.item(5).firstChild.nodeValue;
                transoffice=rcells.item(6).firstChild.nodeValue;
                reason=rcells.item(8).firstChild.nodeValue;
                //alert(transoffice);
               
        Minimize();
    
      
        opener.doParentBankAccNumbers(dateentry,majorclass,assetcode,location,transoffice,reason);
      
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
              List of AMC Details of Assets 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      <th>Select </th>
            <th>
           Major Classification of Asset
          </th>
           <th style="display:none"> Major Id </th> 
           <th style="display:none"> Asset Code </th> 
           <th>Asset Code </th>
           
          <th>
            Physical Location of Asset
          </th>
     
       <th>
           Transferred Office name
          </th>
       <th>Date of Transfer</th>
       <th>Reason for Transfer
          </th>
         
         </tr>
         <tbody id="tb" class="table" align="left">
          <%
          String dateentry=null;
          int assetcode=0;
          String finyr="",regdetails="",regno="",regdate="";
          String asset="",tank="",cubic="",regaddr="",make="",yrman="";
          String remarks="",road_tax="";
          Calendar c;
          
        
                
                String sql_que="SELECT TO_CHAR(a.DATE_OF_TRANSFER,'DD/MM/YYYY') AS transdate,a.ASSET_MAJOR_CLASS_CODE,b.ASSET_MAJOR_CLASS_DESC,a.ASSET_CODE,a.PHY_LOCATION,a.trans_office, "+
                	  	" a.REASON_FOR_TRANSFER,c.PARTICULARS as asset_description	from fas_asset_temp_lend_other a,fas_asset_classification b,FAS_ASSET_VAL_AC_DETAILS c where a.asset_major_class_code=b.asset_major_class_code "+
                		" and c.asset_major_class_code = a.asset_major_class_code and a.accounting_unit_id=c.accounting_unit_id and c.asset_code = a.asset_code	and a.accounting_unit_id        =? "+
                		" AND a.accounting_for_office_id  =? ";
               
            
            ps=con.prepareStatement(sql_que);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, cmbOffice_code);
            rs=ps.executeQuery();
            
           int cnt=0; 
           String amcperiod="";
          // amcperiod=rs.
           
            while(rs.next())
            {
               
                cnt++;

               
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
               
                out.println("<td style='display:none' >"+rs.getInt("ASSET_MAJOR_CLASS_CODE")+"</td>");
                out.println("<td>"+rs.getString("ASSET_MAJOR_CLASS_DESC")+"</td>");
                
                System.out.println(rs.getString("ASSET_MAJOR_CLASS_DESC"));
                 
                out.println("<td>"+rs.getString("asset_description")+"</td>");
                out.println("<td style='display:none' >"+rs.getInt("ASSET_CODE")+"</td>");
                System.out.println(rs.getInt("ASSET_CODE"));
                
                out.println("<td>"+rs.getString("PHY_LOCATION")+"</td>");
                System.out.println(rs.getString("PHY_LOCATION"));
                out.println("<td>"+rs.getString("TRANS_OFFICE")+"</td>");
                System.out.println(rs.getString("TRANS_OFFICE"));
                out.println("<td>"+rs.getString("transdate")+"</td>");
                System.out.println(rs.getString("transdate"));
                out.println("<td>"+rs.getString("REASON_FOR_TRANSFER")+"</td>");
                System.out.println(rs.getString("REASON_FOR_TRANSFER"));
               
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


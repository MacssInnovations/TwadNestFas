<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.sql.Date,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Payment System</title>
   
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../scripts/Journal_General_Pending.js"></script>
     <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
      
    <script type="text/javascript" language="javascript">
    function btncancel()
    {
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
  <body class="table"  onload="GetVoucherInfo();" >
  <form name="frmJournal_Bills_pending" method="GET" >
   <%
  Connection con=null;
  ResultSet rs=null,rs2=null,rs3=null;
  PreparedStatement ps=null,ps2=null,ps3=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
   try
  {
  
            ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
   int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,type_MasSL=0,code_MasSL=0;
   Date txtCrea_date=null;
    Calendar c;
    System.out.println("hai");
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));System.out.println(cmbAcc_UnitCode);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));System.out.println(cmbOffice_code);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
           try{
                 yr=Integer.parseInt(request.getParameter("year"));System.out.println(yr);
               }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
                 mon=Integer.parseInt(request.getParameter("month"));System.out.println(mon);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            type_MasSL= Integer.parseInt(request.getParameter("cmbMas_SL_type"));System.out.println("cmbMas_SL_type"+type_MasSL);
                    
            
  %>
   <%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
	%>
     
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>List of Pending Vouchers</strong>
          </div>
        </td>
      </tr>
       
    </table>
     <br>
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
             <th>Select </th>
             <th > Vr.Type  </th>
             <th> Vr.Date </th>
              <th> Vr.No </th>
              <th style="display:none"> SL.No </th>           <!--Transaction Serial Number-->
            <th>A/c Head Code</th>
            <th>CR/DR</th>
            <th >Sub Ledger Type</th>
            <th>Sub Ledger Code</th>
            <th width="80">Amount</th>
           
         
          </tr>
          <tbody id="grid_body" class="table">
          
        
          </tbody>
        </table>
         <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
   
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick="btnsubmit()">
         <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
   
      
      </table>
      </form>
  </body>
</html>
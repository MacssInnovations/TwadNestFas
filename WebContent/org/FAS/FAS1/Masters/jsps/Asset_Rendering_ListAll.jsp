<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>List All Account Rendering Units</title>
    
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <script type="text/javascript" src="../scripts/ListAllAccountingUnit.js"></script>
       <script type="text/javascript" src="../scripts/Asset_Rendering.js"></script>
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
		
		
		   function EditRender(rid)
		   {
		   	var accUnitId="",accUnitName="",accOfficeId="",dtFrm="",active="",dtTo="";
		   	r=document.getElementById(rid);
		   	rcells=r.cells;
		   	//alert(rcells.item(1).firstChild.nodeValue);
		   	//alert(rcells.item(2).firstChild.nodeValue);
		   	//alert(rcells.item(3).firstChild.nodeValue);
		   	//alert(rcells.item(4).firstChild.nodeValue);
		   	//alert(rcells.item(6).firstChild.nodeValue);
		   	//alert(rcells.item(7).firstChild.nodeValue);
		   	//alert(rcells.item(8).firstChild.nodeValue);
		   	
		   	accUnitId=rcells.item(1).firstChild.nodeValue;
		   //	accRendForUnitId=rcells.item(4).firstChild.nodeValue;
		   	dtFrm=rcells.item(4).firstChild.nodeValue;
		   	active=rcells.item(5).firstChild.nodeValue;
		   	dtTo=rcells.item(6).firstChild.nodeValue; 
		   	Minimize();
		   	opener.goBack(accUnitId,dtFrm,active,dtTo);        
		   }
		   
	  </script>
  </head>
  <body  bgcolor="rgb(255,255,225)">
 
 
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
    Connection connection=null;
	ResultSet res=null;
  	ResultSet results=null;
  	ResultSet results1=null;
  	ResultSet results2=null;
    PreparedStatement ps1=null;
    PreparedStatement ps2=null;
    PreparedStatement ps3=null;
    String sql5="";
    String sql3="";
    ResultSet results3=null;
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

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
  <%
  	int accid=0;
  %>  
                                              
                        
                        
 
        
  <form action="" name="frmAccountList">
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
              List of All Asset Rendering Units
            </div></td>
            
        </tr>
       
      </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      	<th>Select</th>
       	<th>Account Rendering Unit Id</th>
       	<th>Account Rendering Unit Name</th>
        <th>Rendering Unit Office</th>
   <!--     <th>Rendered For A/c Unit</th>   -->
        <th>Effective From Date</th>
        <th>Active (Y/N)</th>
        <th>Effective Upto Date</th>
        <th>Status</th>
        <!--th style="display:none"></th-->
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
			try
                {
	                  ps=con.prepareStatement("SELECT a.ACCT_RENDERING_UNIT_ID, " +
					                		  "b.ACCOUNTING_UNIT_NAME AS ACCT_RENDERING_UNIT_NAME, " +
					                		  "a.RENDERING_UNIT_OFFICE_ID, " +
					                		  "d.OFFICE_NAME, " +
					                		 // "a.ACCT_UNIT_ID_RENDERED_FOR, " +
					                		 // "c.ACCOUNTING_UNIT_NAME AS ACCT_UNIT_NAME_RENDERED_FOR, " +
					                		  "to_char(a.DATE_EFFECT_FROM,'DD/MM/YYYY') AS DATE_EFFECT_FROM, " +
					                		  "a.ACTIVE, " +
					                		  "to_char(a.DATE_EFFECT_UPTO,'DD/MM/YYYY') AS DATE_EFFECT_UPTO, " +
					                		  "a.status as status " +
					                		  "FROM FAS_ASSET_NUM_AC_RENDER_UNITS a join FAS_MST_ACCT_UNITS b " +
					                		  "	ON a.ACCT_RENDERING_UNIT_ID = b.ACCOUNTING_UNIT_ID " +
					                		//  "JOIN FAS_MST_ACCT_UNITS c " +
					                		//  "	ON a.ACCT_UNIT_ID_RENDERED_FOR = c.ACCOUNTING_UNIT_ID " +
							                  "JOIN COM_MST_OFFICES d " +
				      						  "	ON a.RENDERING_UNIT_OFFICE_ID = d.office_id " +
					                		  "ORDER BY RENDERING_UNIT_OFFICE_ID ");
	                  results=ps.executeQuery();
	                  int cnt=0;	                  
	                  while(results.next())
	                  {
	                      cnt++;
	                
	                      out.println("<tr id=" + cnt + ">");
	                      if(results.getString("status").equalsIgnoreCase("C")){
	                    	  out.println("<td><font color='green'><b>CANCEL</b></font></td>");
	                      }else{
	                    	  out.println("<td><a href='javascript:EditRender(" +cnt+ ")'>EDIT</a></td>");  
	                      }	                      
	                      out.println("<td>"+results.getInt("ACCT_RENDERING_UNIT_ID")+"</td>");
	                      out.println("<td>"+results.getString("ACCT_RENDERING_UNIT_NAME")+"</td>");
	                      out.println("<td>"+results.getString("OFFICE_NAME")+"</td>"); 
	                    //  out.println("<td style='display:none'>"+results.getInt("ACCT_UNIT_ID_RENDERED_FOR")+"</td>");
	                    //  out.println("<td>"+results.getString("ACCT_UNIT_NAME_RENDERED_FOR")+"</td>");
	                      
	                      String date_from = results.getString("DATE_EFFECT_FROM");
	                      out.println("<td>"+date_from+"</td>");     

	                      out.println("<td>"+results.getString("ACTIVE")+"</td>");
	                      
	                      String date_to = results.getString("DATE_EFFECT_UPTO");
	                      if(date_to == null)
	                    	  date_to = "-";
	                      out.println("<td>"+date_to+"</td>");
	                      if(results.getString("status").equalsIgnoreCase("L")){
	                    	  out.println("<td>LIVE</td>");  
	                      }else{
	                    	  out.println("<td>CANCEL</td>");  
	                      }
	                      out.println("</tr>");
	                  }
	                  if(cnt==0)
	                     {
	                        out.println("<tr><td colspan='7' align='center'>No data found</td></tr>");
	                     } 
         		}catch(Exception e)
            	{
	                System.out.println("Exception in Select:"+e);
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
    </form></body>
</html>
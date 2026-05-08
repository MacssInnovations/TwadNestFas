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
    <title>List of all Assets</title>
    
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <script type="text/javascript" src="../scripts/Asset_Value_AC_Details.js"></script>
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
		
		
		   function Edit(rid)
		   {
		   	var accounting_unit_id=0,accounting_unit_office_id=0,financial_year="",asset_code=0;
		   	r=document.getElementById(rid);
		   	rcells=r.cells;

/*		   	
		   	alert(rcells.item(1).firstChild.nodeValue);
		   	alert(rcells.item(2).firstChild.nodeValue);
		   	alert(rcells.item(3).firstChild.nodeValue);
		   	alert(rcells.item(4).firstChild.nodeValue);
*/
		   	
		   	accounting_unit_id=rcells.item(1).firstChild.nodeValue;
		   	accounting_unit_office_id=rcells.item(2).firstChild.nodeValue;
		   	financial_year=rcells.item(3).firstChild.nodeValue;
		   	asset_code=rcells.item(4).firstChild.nodeValue;
		   	Minimize();
		   	opener.doParent(accounting_unit_id,accounting_unit_office_id,financial_year,asset_code);        
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
  	int accid=0;
  %>  
                                              
                        
                        
 
        
  <form action="" name="frmAList">
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
              List of Assets (<%=request.getParameter("financial_year")%>)
            </div>
          </td>
        </tr>
       
      </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">

      	<th>Select</th>
       	<th>Asset Code</th>
        <th>Asset Type</th>
        <th>Classification</th>
        <th>Alias Code</th>
        <th>Asset Description</th>
        <th>Total Assets</th>
        <th>Asset Location</th>
        <th>Ownership</th>
        <th>Original Cost</th>
        <th>Current Cost</th>

       </tr>
       <tbody id="tb" class="table" align="left">
          <%
			try
                {
				      ps=con.prepareStatement("SELECT asset_code, " +
	                		"  asset_type_desc, " +
	                		"  asset_major_class_desc, " +
	                		"  alias_code, " +
	                		"  particulars, " +
	                		"  number_of_assets, " +
	                		"  office_name, " +
	                		"  ownership_code, " +
	                		"  original_cost, " +
							"    case " +
							"    when depreciation_cate_code = 0 then current_value_after_apportion " +
							"    else current_value_after_depre " +
 						    "  end current_cost " +
	                		"FROM fas_asset_val_ac_details a " + 
	                		"  JOIN com_mst_assets_type b " +
	                		"    ON a.asset_type_code = b.asset_type_code " + 
	                		//"  JOIN fas_asset_classification c " +			// TEMP - fas_mst_assets_class ==> for dummy records
	                     	" join fas_mst_assets_class c "+
	                		"    ON a.asset_major_class_code = c.asset_major_class_code " + 
	                		"  JOIN com_mst_offices d " +
	                		"    ON a.office_id_asset_is_available = d.office_id " +
	                		"WHERE accounting_unit_id = ?" +
	                		" AND accounting_unit_office_id = ?" +
	                		" AND financial_year = ?" +
	                		"ORDER BY asset_code");
	                  
	                  int accounting_unit_id = 0; 
	                  int accounting_unit_office_id = 0;
	                  String financial_year = null;
	                  
	                  try
	                  {
	                  	accounting_unit_id = Integer.parseInt(request.getParameter("accounting_unit_id"));
	                  	System.out.println("\naccounting_unit_id : " + accounting_unit_id);
	                  }catch(Exception e)
	                  {
	                	  System.out.println("Exception getting 'accounting_unit_id' parameter ==> " + e);
	                  }

	                  try
	                  {
	                	  accounting_unit_office_id = Integer.parseInt(request.getParameter("accounting_unit_office_id"));
	                	  System.out.println("\naccounting_unit_office_id : " + accounting_unit_office_id);
	                  }catch(Exception e)
	                  {
	                	  System.out.println("Exception getting 'accounting_unit_office_id' parameter ==> " + e);
	                  }
	                  
	                  try
	                  {
	                	  financial_year = request.getParameter("financial_year");
	                	  System.out.println("\nfinancial_year : " + financial_year);
	                  }catch(Exception e)
	                  {
	                	  System.out.println("Exception getting 'financial_year' parameter ==> " + e);
	                  }
	                  
	                  ps.setInt(1,accounting_unit_id);
	                  ps.setInt(2,accounting_unit_office_id);
	                  ps.setString(3,financial_year);
	                  
	                  results=ps.executeQuery();
	                  
	                  int cnt=0;
	                  while(results.next())
	                  {
	                      cnt++;
	                
	                      out.println("<tr id=" + cnt + ">");
	                      
		                      out.println("<td align='center'><a href='javascript:Edit(" +cnt+ ")'>EDIT</a></td>");
		                      
		                      out.println("<td style='display:none'>"+accounting_unit_id+"</td>");
		                      out.println("<td style='display:none'>"+accounting_unit_office_id+"</td>");
		                      out.println("<td style='display:none'>"+financial_year+"</td>");
		                      
		                      out.println("<td align='center'>"+results.getInt("asset_code")+"</td>");
		                      out.println("<td align='center'>"+results.getString("asset_type_desc")+"</td>"); 
		                      out.println("<td align='center'>"+results.getString("asset_major_class_desc")+"</td>");
		                      out.println("<td align='center'>"+results.getString("alias_code")+"</td>");
		                      out.println("<td align='center'>"+results.getString("particulars")+"</td>");
		                      out.println("<td align='center'>"+results.getInt("number_of_assets")+"</td>");
		                      out.println("<td align='center'>"+results.getString("office_name")+"</td>");
		                      out.println("<td align='center'>"+results.getString("ownership_code")+"</td>");
		                      out.println("<td align='right'>"+results.getInt("original_cost")+"</td>");
		                      out.println("<td align='right'>"+results.getInt("current_cost")+"</td>");
		                      
	                      out.println("</tr>");
	                  }
	                  if(cnt==0)
	                  {
	                	  out.println("<tr><td colspan='10' align='center'>No data found</td></tr>");
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
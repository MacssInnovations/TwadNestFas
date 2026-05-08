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
    <title>List of Floors</title>
    
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <script type="text/javascript" src="../scripts/Building_Details_ListAll.js"></script>
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
		
		
		   function callOffice(rid)
		   {
		   	var accounting_unit_id=0,accounting_unit_office_id=0,financial_year="",asset_code=0;
		   	r=document.getElementById(rid);
		   	rcells=r.cells;

	/*	   	
		   	alert(rcells.item(0).firstChild.nodeValue);
		   	alert(rcells.item(1).firstChild.nodeValue);
		   	alert(rcells.item(2).firstChild.nodeValue);
		   	alert(rcells.item(3).firstChild.nodeValue);
		   	alert(rcells.item(4).firstChild.nodeValue);
	*/
		   	
		   	accounting_unit_id=rcells.item(0).firstChild.nodeValue;
		   	accounting_unit_office_id=rcells.item(1).firstChild.nodeValue;
		   	financial_year=rcells.item(2).firstChild.nodeValue;
		   	asset_code=rcells.item(3).firstChild.nodeValue;
		   	floor_no=rcells.item(4).firstChild.nodeValue;
		   	
		   	Minimize();
		   	opener.ListOffices(accounting_unit_id,accounting_unit_office_id,financial_year,asset_code,floor_no);        
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
              List of Floors
            </div>
          </td>
        </tr>
       
      </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
          <th>Floor No.</th>
          <th>Year</th>
          <th>Height</th>
          <th>Plinth Area</th>
          <th>Civil Cost <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Rs)</th>
          <th>Electrical <br> &nbsp;&nbsp;&nbsp; Cost <br> &nbsp;&nbsp;&nbsp;&nbsp;(Rs)</th>
          <th>External  <br> &nbsp;Services <br> &nbsp;&nbsp;&nbsp;&nbsp;(Rs)</th>
          <th>Additions <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Rs)</th>
          <th>Book <br> Value <br> &nbsp;(Rs)</th>
          <th>B.P No.</th>
          <th>A/c Head</th>
          <th>Office Details</th>
       </tr>

       <tbody id="tblListFloor" class="table" align="left">
          <%
			try
                {
				      ps=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID, " +
												"  ACCOUNTING_UNIT_OFFICE_ID, " +
												"  FINANCIAL_YEAR, " +
												"  ASSET_CODE, " +
	       										"  to_char(TRANS_DATE,'dd/mm/yyyy') AS TRANS_DATE, " +  
												"  FLOOR_NO, " +
												"  YEAR_OF_CONSTRUCTION, " +
												"  FLOOR_HEIGHT, " +
												"  PLINTH_AREA, " +
												"  CIVIL_COST, " +
												"  ELECTRICAL_COST, " +
												"  EXTERNAL_COST, " +
												"  ADDITIONAL_COST, " +
												"  BOOK_VALUE, " +
												"  BP_NO_FOR_CONSTRUCTION, " +
												"  a.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
												"  b.ACCOUNT_HEAD_DESC AS ACCOUNT_HEAD_DESC, " +
												"  a.REMARKS AS REMARKS, " +
												"  a.UPDATED_BY_USER_ID AS UPDATED_BY_USER_ID, " +
												"  a.UPDATED_DATE AS UPDATED_DATE " +
												"FROM FAS_BUILDING_FLOOR_DETAILS a JOIN COM_MST_ACCOUNT_HEADS b " +
												"ON a.ACCOUNT_HEAD_CODE = b.ACCOUNT_HEAD_CODE " +
												"WHERE ACCOUNTING_UNIT_ID = ? " +
												"  AND ACCOUNTING_UNIT_OFFICE_ID = ? " +
												"  AND FINANCIAL_YEAR = ? " +
												"  AND ASSET_CODE = ? ");
	                  
	                  int accounting_unit_id = 0; 
	                  int accounting_unit_office_id = 0;
	                  String financial_year = null;
	                  int asset_code = 0;
	                  
	                  int floor_no;
	                  
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
	                  
	                  try
	                  {
	                	  asset_code = Integer.parseInt(request.getParameter("asset_code"));
	                	  System.out.println("\nasset_code : " + asset_code);
	                  }catch(Exception e)
	                  {
	                	  System.out.println("Exception getting 'asset_code' parameter ==> " + e);
	                  }
	                  
	                  
	                  ps.setInt(1,accounting_unit_id);
	                  ps.setInt(2,accounting_unit_office_id);
	                  ps.setString(3,financial_year);
	                  ps.setInt(4,asset_code);
	                 
	                  results=ps.executeQuery();
	                  
	                  int cnt=0;
	                  while(results.next())
	                  {
	                      cnt++;
	                
	                      out.println("<tr id=" + cnt + ">");
	                      
		                     // out.println("<td align='center'><a href='javascript:Edit(" +cnt+ ")'>EDIT</a></td>");
		                      
		                      out.println("<td style='display:none'>"+accounting_unit_id+"</td>");
		                      out.println("<td style='display:none'>"+accounting_unit_office_id+"</td>");
		                      out.println("<td style='display:none'>"+financial_year+"</td>");
               				  out.println("<td style='display:none'>"+results.getInt("asset_code")+"</td>");
		                      
		                      floor_no = results.getInt("FLOOR_NO");
		                      out.println("<td align='center'>"+floor_no+"</td>"); 
		                      out.println("<td align='center'>"+results.getInt("YEAR_OF_CONSTRUCTION")+"</a></td>");
		                      out.println("<td align='center'>"+results.getInt("FLOOR_HEIGHT")+"</td>");
		                      out.println("<td align='center'>"+results.getInt("PLINTH_AREA")+"</td>");
		                      out.println("<td align='center'>"+results.getInt("CIVIL_COST")+"</td>");
		                      out.println("<td align='center'>"+results.getInt("ELECTRICAL_COST")+"</td>");
		                      out.println("<td align='center'>"+results.getInt("EXTERNAL_COST")+"</td>");
		                      out.println("<td align='center'>"+results.getInt("ADDITIONAL_COST")+"</td>");
		                      out.println("<td align='center'>"+results.getInt("BOOK_VALUE")+"</td>");
		                      out.println("<td align='center'>"+results.getInt("BP_NO_FOR_CONSTRUCTION")+"</td>");
		                      out.println("<td align='center'>"+results.getString("ACCOUNT_HEAD_DESC")+"</td>");
		                      out.println("<td align='center'><a href='javascript:callOffice(" + cnt + ")'>Click here</a></td>");

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
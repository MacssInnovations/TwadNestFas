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
              List of Offices
            </div>
          </td>
        </tr>
       
      </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
          <th>S.No.</th>
          <th>Office Type</th>
          <th>Office Name</th>
       </tr>

       <tbody id="tblListOffice" class="table" align="left">
          <%
			try
                {
				      ps=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID, " +
												"  ACCOUNTING_UNIT_OFFICE_ID, " +
												"  FINANCIAL_YEAR, " +
												"  ASSET_CODE, " +
												"  to_char(TRANS_DATE,'dd/mm/yyyy') AS TRANS_DATE, " +  
												"  FLOOR_NO, " +
												"  TYPE_OF_OCCUPYING_OFFICE, " +
												"  TWAD_OFFICE_ID, " +
												"  NONTWAD_OFFICE_NAME, " +
												"  REMARKS " +
												"FROM FAS_FLOOR_OFFICE_DETAILS " +
												"WHERE ACCOUNTING_UNIT_ID = ? " +
												"  AND ACCOUNTING_UNIT_OFFICE_ID = ? " +
												"  AND FINANCIAL_YEAR = ? " +
												"  AND ASSET_CODE = ? " +
												"  AND FLOOR_NO = ?");
	                  
	                  int accounting_unit_id = 0; 
	                  int accounting_unit_office_id = 0;
	                  String financial_year = null;
	                  int asset_code = 0;
	                  int floor_no = 0;
	                  
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
	                  
	                  
	                  try
	                  {
	                	  floor_no = Integer.parseInt(request.getParameter("floor_no"));
	                	  System.out.println("\nfloor_no : " + floor_no);
	                  }catch(Exception e)
	                  {
	                	  System.out.println("Exception getting 'floor_no' parameter ==> " + e);
	                  }
	                  
	                  
	                  ps.setInt(1,accounting_unit_id);
	                  ps.setInt(2,accounting_unit_office_id);
	                  ps.setString(3,financial_year);
	                  ps.setInt(4,asset_code);
	                  ps.setInt(5,floor_no);
	                  
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
               				  out.println("<td style='display:none'>"+floor_no+"</td>");

		                      out.println("<td align='center'>"+cnt+"</td>");
		                      String OfficeType = results.getString("TYPE_OF_OCCUPYING_OFFICE");
		                      int twadoff = 0;
		                      String office = null;
		                      if(OfficeType.equalsIgnoreCase("T"))
		                      {
		                      	OfficeType="TWAD";
		                      	twadoff = results.getInt("TWAD_OFFICE_ID");
		                      	try
		                      	{
		                      		ps=con.prepareStatement("SELECT OFFICE_NAME FROM COM_MST_OFFICES " +
		                      	 							" WHERE OFFICE_ID = ?");
		                      	 	ps.setInt(1,twadoff);
		                      	 	rs = ps.executeQuery();
		                      	 	if(rs.next())
		                      	 	{
		                      	 		office = rs.getString("OFFICE_NAME");
		                      	 	}
		                      	}
		                      	catch(Exception e)
		                      	{
		                      		System.out.println("Exception fetching TWAD office == " + e);
		                      	}
		                      }
		                      else
		                      {
		                      	OfficeType="Non-TWAD";
		                      	office = results.getString("NONTWAD_OFFICE_NAME");
		                      }
		                      out.println("<td align='center'>"+OfficeType+"</td>");
		                      out.println("<td align='center' style='display:none'>"+ twadoff +"</td>");
		                      out.println("<td align='center'>"+ office +"</td>");

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
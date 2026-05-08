<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    <meta http-equiv="cache-control" content="no-cache">
    <title>List for Vehicle Master Details</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/vehicle_js.js"></script>
    
    <script type="text/javascript" language="javascript">
	  function EditRender(rid)
		   {
		   	var vehno="",fuelqty="",fuelamt="",oilqty="",oilamt="",off="",limit="",rem="";
		   	r=document.getElementById(rid);
		   	rcells=r.cells;
		   	//alert(rcells.item(2).firstChild.nodeValue);		   	
		   	vehno=rcells.item(1).firstChild.nodeValue;
		   	off=rcells.item(2).firstChild.nodeValue;
		   	limit=rcells.item(3).firstChild.nodeValue;
		   	fuelqty=rcells.item(4).firstChild.nodeValue;
		   	fuelamt=rcells.item(5).firstChild.nodeValue;
		   	oilqty=rcells.item(6).firstChild.nodeValue; 
            oilamt=rcells.item(7).firstChild.nodeValue;
		   	rem=rcells.item(8).firstChild.nodeValue;
		    close();
		   	opener.goBack(vehno,off,limit,fuelqty,fuelamt,oilqty,oilamt,rem);        
		   }
		   
    </script>
  </head>
  <body class="table">
   <%
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        Connection connection=null;
        ResultSet results=null;
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
                    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                }
           catch (Exception e) 
                {
                    System.out.println("Exception to catch cmbAcc_UnitCode ");
                }
           try 
               {
                    cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
               }       
           catch (Exception e) 
               {
                    System.out.println("Exception to catch cmbOffice_code ");
               }
          String finyr="";
          finyr=(request.getParameter("finyr"));
   %>
 <form name="frmassetlist" id="frmassetlist">
    <table cellspacing="2" cellpadding="3" border="1" width="100%">
            <tr class="tdH">
              <td>
                <div align="center">
                  TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANCIAL ACCOUNTING SYSTEM
                </div>
              </td>
            </tr>
            
            <tr class="table">
              <td>
                <div align="center">
                  List of Vehicle AM Estimate and Ceiling limits Details
                </div></td>
            </tr>
    </table>
    <table cellspacing="2" cellpadding="3" border="1" width="100%">
           <tr class="tdH">
               <th>Select </th>
               <th>Vehicle No </th>
               <th>Office Use</th>
               <th>Ceiling Limit</th>
               <th>Fuel Ceiling Qty</th>
               <th>Fuel Ceiling Amt</th>
               <th>Oil Ceiling Qty</th>
               <th>Oil Ceiling Amt</th>
               <th>Remarks</th>    
           </tr>
           
           <tbody id="tblList" class="table">
                 <%
                    try
                        {
                                  ps=con.prepareStatement("SELECT * from FAS_VEHICLE_MASTER where FINANCIAL_YEAR=?");
                                  ps.setString(1,finyr);
                                  results=ps.executeQuery();
                                  int cnt=0;
                                  while(results.next())
                                  {
                                      cnt++;
                                
                                      out.println("<tr id=" + cnt + ">");
                                      
                                      out.println("<td><a href='javascript:EditRender(" +cnt+ ")'>EDIT</a></td>");
                                      out.println("<td>"+results.getInt("VEHICLE_CODE")+"</td>");
                                      if(results.getString("OFFICE_IN_USE")!=null)
                                       {
                                    	  out.println("<td>"+results.getString("OFFICE_IN_USE")+"</td>");
                                       }
                                      else
                                      {
                                    	  out.println("<td> </td>");
                                      }  
                                      out.println("<td>"+results.getInt("CEILING_LIMIT")+"</td>");
                                      out.println("<td>"+results.getString("FUEL_CEILING_QTY")+"</td>");
                                      out.println("<td>"+results.getString("FUEL_CEILING_AMT")+"</td>");
                                      out.println("<td>"+results.getString("OIL_CEILING_QTY")+"</td>");
                                      out.println("<td>"+results.getString("OIL_CEILING_AMT")+"</td>");
                                      if(results.getString("REMARKS")!=null)
                                      {
                                   	  out.println("<td>"+results.getString("REMARKS")+"</td>");
                                      }
                                     else
                                     {
                                   	  out.println("<td> </td>");
                                     }  
                                      out.println("</tr>");
                                  }
                                  if(cnt==0)
                                     {
                                        out.println("<tr><td colspan='10' align='center'>No data found</td></tr>");
                                     } 
                        }
                    catch(Exception e)
                        {
                                System.out.println("Exception in Select:"+e);
                        }
                  
                 %>
           </tbody>
    </table>
       
    <table align="center" cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick=" self.close();"/>
            </div>
          </td>
        </tr>
    </table> 
 </form>
</body>
</html>


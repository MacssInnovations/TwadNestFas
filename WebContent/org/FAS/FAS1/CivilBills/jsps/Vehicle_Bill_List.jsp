<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ page import="Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List for Vehicle Bill Details</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Vehicle_Bills.js"></script>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" language="javascript">
	  function EditRender(rid)
		   {
		   	var vehno="",fuelqty="",fuelamt="",oilqty="",oilamt="";
		   	r=document.getElementById(rid);
		   	rcells=r.cells;
		   	//alert(rcells.item(2).firstChild.nodeValue);		   	
		   	vehno=rcells.item(1).firstChild.nodeValue;
                        fuelqty=rcells.item(2).firstChild.nodeValue;
		   	fuelamt=rcells.item(3).firstChild.nodeValue;
		   	oilqty=rcells.item(4).firstChild.nodeValue; 
                        oilamt=rcells.item(5).firstChild.nodeValue;
                        close();
		   	opener.goBack(vehno,fuelqty,fuelamt,oilqty,oilamt);        
		   }
		   
	  </script>
  </head>
  <body class="table">
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
     
       
           int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0;
           String anndate = "";   
                            anndate=request.getParameter("anndate");
                                    
                            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
                            Com_CashBook1 cb=new Com_CashBook1();
                            
                            /** Assign Cashbook Year and Month to year_month Variable */
                            String year_month=cb.cb_date(anndate).toString();
                            
                            /** Split Cash Book Year and Month */
                            String []ym=year_month.split("/");
                            
                            /** Assign Year and Month */
                            Cashbook_year=Integer.parseInt(ym[0]);
                            Cashbook_month=Integer.parseInt(ym[1]);
                            
           
         	   try {
                	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbAcc_UnitCode ");
                }
                try {
                	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        }        catch (Exception e) {
                    System.out.println("Exception to catch cmbOffice_code ");
                }
                      
            
   %>
  <form name="frmbilllist" id="frmbilllist">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANCIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
              List of Vehicle Bill Details
            </div></td>            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">       
       <tr class="tdH">    
           <th>Select </th>
           <th>Vehicle No </th>
           <th>Fuel Used Qty</th>
           <th>Fuel Used Amt</th>
           <th>Oil Used Qty</th>
           <th>Oil Used Amt</th>
           <th>Status</th>  
         </tr>
        <tbody id="tblList" class="table">
         <%
		try
                {
	                  ps=con.prepareStatement("SELECT VEHICLE_CODE,FUEL_USED_QTY,FUEL_USED_AMOUNT,OIL_USED_QTY,OIL_USED_AMOUNT,STATUS from FAS_VEHICLE_BILL_DETAILS where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
	                  ps.setInt(1,Cashbook_year);
                          ps.setInt(2,Cashbook_month);
                          results=ps.executeQuery();
	                  int cnt=0;
	                  while(results.next())
	                  {
	                      cnt++;
	                      out.println("<tr id=" + cnt + ">");
	                      out.println("<td><a href='javascript:EditRender(" +cnt+ ")'>EDIT</a></td>");
	                      out.println("<td>"+results.getInt("VEHICLE_CODE")+"</td>");
	                      out.println("<td>"+results.getString("FUEL_USED_QTY")+"</td>");
	                      out.println("<td>"+results.getString("FUEL_USED_AMOUNT")+"</td>"); 
	                      out.println("<td>"+results.getString("OIL_USED_QTY")+"</td>");
	                      out.println("<td>"+results.getString("OIL_USED_AMOUNT")+"</td>");
	                      out.println("<td>"+results.getString("STATUS")+"</td>");
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
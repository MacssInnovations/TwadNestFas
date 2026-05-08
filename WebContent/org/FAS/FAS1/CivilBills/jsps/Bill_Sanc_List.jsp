<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List for Bill Sanction Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Bill_sanc.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" language="javascript">
	  function EditRender(rid)
		   {
		   	var major="",minor="",empid="",minordesc="",off="",sanc="";
		   	r=document.getElementById(rid);
		   	rcells=r.cells;
		   	//alert(rcells.item(2).firstChild.nodeValue);		   	
		   	major=rcells.item(1).firstChild.nodeValue;
		   	minor=rcells.item(2).firstChild.nodeValue;
            empid=rcells.item(3).firstChild.nodeValue;
            off=rcells.item(4).firstChild.nodeValue;
            sanc=rcells.item(5).firstChild.nodeValue;
		   	close();
		   	opener.goBack(major,minor,empid,off,sanc);        
		   }
		   
	  </script>
  </head>
  <body class="table">         
  <form name="frmbillsanc" id="frmbillsanc">
 <%
  
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
              System.out.println("...............list all LISTjsp started.................");
		      HttpSession session=request.getSession(false);
		      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
		     
		       
		      int  cmbAcc_UnitCode=0,cmbOffice_code=0;
		      String finyr = "";   
		      finyr=request.getParameter("finyr");System.out.println(finyr);     	   
           
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
              List of Bill Sanction Level-Selected Employees Details
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
           <tr class="tdH">
               <th>Select </th>
               <th>Major Type Code </th>
               <th>Minor Type</th>
               <th>Employee Code</th>
               <th>Office Code</th>
               <th>Sanctioning Authority</th>
            </tr>
        <tbody id="tblList" class="table">
       <%
         	try
                       {
         		          String sql="SELECT * from FAS_BILL_SANCTION_LEVEL_EMP where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and FINANCIAL_YEAR='"+finyr+"'";
	                      ps=con.prepareStatement(sql);System.out.println(sql);
	                     // ps.setInt(1,cmbAcc_UnitCode);
	                    //  ps.setInt(2,cmbOffice_code);
		                  //ps.setString(3,finyr);
	                  results=ps.executeQuery();
	                  int cnt=0;
	                  while(results.next())
	                  {     System.out.println("enter 1st while");
		     		        sql="SELECT * from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE='"+results.getInt("BILL_MAJOR_TYPE_CODE")+"' and BILL_MINOR_TYPE_CODE='"+results.getInt("BILL_MINOR_TYPE_CODE")+"'";
		     		        ps1=con.prepareStatement(sql);System.out.println(sql);
	                        rs1=ps1.executeQuery();
                            while(rs1.next())
                            {  
                            	System.out.println("enter 2nd while");
                            	if(results.getInt("SANCTIONING_AUTHORITY")==0)
                            	{
                            		String MajCode=rs1.getString("BILL_MINOR_TYPE_CODE");
  	                              String MajDesc=rs1.getString("BILL_MINOR_TYPE_DESC");
  	                              cnt++;
  	                              out.println("<tr id=" + cnt + ">");
  	                              out.println("<td><a href='javascript:EditRender(" +cnt+ ")'>EDIT</a></td>");
  		                          out.println("<td>"+results.getInt("BILL_MAJOR_TYPE_CODE")+"</td>");
  	                              out.println("<td value="+MajCode+">"+MajCode+"-"+MajDesc+"</td>");  
  		                          out.println("<td>"+results.getInt("EMPLOYEE_ID")+"</td>");
  	                              out.println("<td>"+results.getInt("OFFICE_ID")+"</td>");
                                    out.println("<td value="+0+">"+results.getInt("SANCTIONING_AUTHORITY")+"</td>");
  		                          out.println("</tr>");
                            	}
                            	else
                            	{
                            	sql="select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID='"+results.getInt("SANCTIONING_AUTHORITY")+"'";
    		     		        ps2=con.prepareStatement(sql);System.out.println(sql);
                            	results2=ps2.executeQuery();
                                while(results2.next())
                                {
                                  System.out.println("enter 3rd while");
	                              String MajCode=rs1.getString("BILL_MINOR_TYPE_CODE");
	                              String MajDesc=rs1.getString("BILL_MINOR_TYPE_DESC");
	                              int code=results.getInt("SANCTIONING_AUTHORITY");System.out.println("code.........."+code);
                                  String val=results2.getString("DESIGNATION");System.out.println("val........"+val);
                                  cnt++;
	                              out.println("<tr id=" + cnt + ">");
	                              out.println("<td><a href='javascript:EditRender(" +cnt+ ")'>EDIT</a></td>");
		                          out.println("<td>"+results.getInt("BILL_MAJOR_TYPE_CODE")+"</td>");
	                              out.println("<td value="+MajCode+">"+MajCode+"-"+MajDesc+"</td>");  
		                          out.println("<td>"+results.getInt("EMPLOYEE_ID")+"</td>");
	                              out.println("<td>"+results.getInt("OFFICE_ID")+"</td>");
                                  out.println("<td value="+code+">"+code+"-"+val+"</td>");
		                          out.println("</tr>");
                              }}
                            }
	                  }
	                  if(cnt==0)
	                     {
	                        out.println("<tr><td colspan='7' align='center'>No data found</td></tr>");
	                     } 
         		}
                catch(Exception e)
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

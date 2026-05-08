<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
 <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
   <script type="text/javascript" src="../scripts/Emp_Bill_Advances_Applicable.js"></script>
   <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
       <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
       
<title>Employee Bills-Advance Applicable</title>
</head>
 <body  bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Employee Bills-Advance Applicable</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="emp_bill_advance" id="emp_bill_advance" >
      <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null;
  PreparedStatement ps=null,ps2=null;
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  Calendar cal=Calendar.getInstance();
  int year=cal.get(Calendar.YEAR);
  int month = cal.get(Calendar.MONTH) + 1;
  System.out.println("Current month & Year: "+month + " "+year );
  
  
  
  %>
      <% 
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=9315;
    int  oid=0;
    String oname="";
   
    try
    {
           
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 }
            results.close();
            ps.close();
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                  }
            results.close();
            ps.close();
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   try{
   %>
      
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
             
           <tr class="table">
                <td>
                  <div align="left">
             Financial Year   <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                                         
                     <select name="finyear"  id="finyear" onchange="cashbookyear()">
                   <option value="" > --Select Financial Year--    </option>   
                     <%String fin=(year-1)+"-"+(year); %>
                 <option value="<%=fin %>" > <%=fin%>     </option>
                 
                 <option value="<%=year+"-"+(year+1)%>">   <%=year+"-"+(year+1)%>  </option>
                </select>
                    
                  </div>
                </td>
              </tr>  
              
              <tr class="table">
                <td>
                  <div align="left">
              For the Year & Month   <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                                         
                    <select name="cashyear"  id="cashyear" onchange="cashbookmonth()" >
                     </select>  
                   
                     
                      &nbsp;&nbsp; & &nbsp; &nbsp; 
                      
                     
                      <select name="cashmonth"  id="cashmonth"  >
         
         				 </select> 
                      
                      
                  </div>
                </td>
              </tr>  
              
              
              <tr class="table">
                <td>
                  <div align="left">Bill Major Type   <font color="#ff2121">*</font></div>
                </td>
                <td>
                  <div align="left">
                    <select name="majortype"  id="majortype" onchange="call('get')"  >
                    <option value="">--Select Major Type--</option>
                   <%      ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE, BILL_MAJOR_TYPE_DESC  from FAS_BILL_MAJOR_TYPES");
                       
                        rs=ps.executeQuery();
                       
                        while(rs.next())
                        {
                       
                        out.println("<option value="+rs.getInt("BILL_MAJOR_TYPE_CODE")+">"+rs.getString("BILL_MAJOR_TYPE_DESC")+"</option>");
                        }    %>
                     </select>         
                  </div>
                </td>
              </tr>
              
              
                <tr class="table">
                <td>
                  <div align="left">Bill Minor Type  <font color="#ff2121">*</font></div>
                </td>
                <td>
                  <div align="left">
                    <select name="minortype"  id="minortype" style="display:block" >
                  <option value="">--Select Minor Type--</option>
                     </select>  
                     
                     <input type="text" id="minortypehidden" style="display:none" disabled="disabled">
                       <input type="text" id="minortypehiddenval" style="display:none">      
                  </div>
                </td>
              </tr>
           
              <tr class="table">
                <td>
                  <div align="left">Advance Applicable</div>
                </td>
                <td>
                  <div align="left">
                  <input type="radio" name="applicable" value="Y" onclick="applicableupto()" checked="checked">Yes
                        &nbsp;&nbsp;  &nbsp; &nbsp;      
                     <input type="radio" name="applicable" value="N" onclick="applicableupto()"> No
                            
                  </div>
                
                </td>
              </tr>
              
              <tr class="table">
              <td colspan="2">
              <div id="datediv" style="display:none">
              <table  width="100%"  border="1">
              <tr class="table" >
                <td>
                  <div align="left">Applicable Upto</div>
                 
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="date"  id="date"  size="11" /> 
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.emp_bill_advance.date);"  alt="Show Calendar"></img>
                            
                  </div>
                </td>
              </tr> 
              </table>
              </div>
              </td>
              </tr>
              
             
               <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="call('Add')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="call('Update')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="call('Delete')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListHeads()" tabindex="60"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()" tabindex="70"/>
                     </td>
                 </tr>
              
              
              </table>
              </div>
      </td>
      </tr>
              
              
             <%}catch(Exception e){out.println(e);
             
             System.out.println(e);
             
             } %>    
              
              </table>
              </div>
              </form>
             
              
              

</body>
</html>
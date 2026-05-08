<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Accounthead_Consolidated_Report</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Accounthead_Consolidated_Report.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <!-- to avoid future date the above script used-->
     
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
</script>
<script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body onload="foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Accounthead for Consolidated Report </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmAccount_Head_Consd_Report" id="frmAccount_Head_Consd_Report" >
                  
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
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
          
        System.out.println("user id::"+empProfile.getEmployeeId());
        int empid=empProfile.getEmployeeId();
        //int empid=9315;
        int  oid=0,sid=0;
        String oname="",sname="";
   
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
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   try
     {
     ps=con.prepareStatement("select a.SECTION_ID,b.SECTION_NAME from FAS_SECTION_GROUP_DETAILS a,COM_MST_OFFICE_SECTIONS b,"+
                             "HRM_EMP_CURRENT_POSTING c where a.SECTION_ID=b.SECTION_ID and b.SECTION_ID=c.SECTION_ID " + 
                             "and a.SECTION_ID=c.SECTION_ID and c.employee_id =? and a.office_id=?");
                 ps.setInt(1,empid);
                 ps.setInt(2,oid);
                 rs=ps.executeQuery();
               if(rs.next()) 
                 {
                    sid=rs.getInt("SECTION_ID");
                    sname=rs.getString("SECTION_NAME");
                 }
            rs.close();
            ps.close();  
            }
   catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
              <tr class="table">
                <td>
                  <div align="left">Office&nbsp;Name</div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="txtOfficeId"  id="txtOfficeId" 
                    value="<%=oid%>" size="5" readonly="readonly" class="disab"  />
                    <input type="text" name="txtOfficeName"
                           id="txtOfficeName" value="<%=oname%>"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                  </div>
                </td>
              </tr>
                         <tr>
                <td>Account Head Code:<font color="#ff2121">*</font></td>
                <td>
                    <input type=text name=txtaccountheadcode id=txtaccountheadcode onchange="doFunction('checkCode','null')" onkeypress="return numbersonly(event,this)">
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                 height="20" alt="AccountHeadList"
                 onclick="AccHeadpopup();"></img>
                </td>
        </tr>
        <tr>
                <td>Account Head Name:</td>
                <td><input type=text name="txtaccountheadname" size="55" disabled >
        </tr>
             <tr class="table">
                <td>
                  <div align="left">
                     Section Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                 <!--td>
                  <div align="left">
                   <input type="text" name="txtSectionId"  id="txtSectionId" 
                    value="<%=sid%>" size="5" readonly="readonly" class="disab"  />
                    <input type="text" name="txtSectionName"
                           id="txtSectionName" value="<%=sname%>"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                  </div>
                </td-->
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbSectionId" id="cmbSectionId" tabindex="3" >
                    <option>----Select Section----</option>
                      <%
                    try
                    {
                     ps=con.prepareStatement("select SECTION_ID,SECTION_NAME from FAS_MST_OFFICE_SECTIONS where office_id=?");
                      ps.setInt(1,oid);
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                        out.println("<option value="+rs.getInt("SECTION_ID")+">"+rs.getString("SECTION_NAME")+"</option>");
                     }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in bank combo..."+e);
                    }
                    finally
                    {
                    rs.close();
                    ps.close();
                    }  
                %>
                    </select>
                  </div>
                </td>
              </tr>
           <!--tr>
                <td>Group Name</td>
                <td><input type="text" name="txtGroup" id="txtGroup" size="25"   onkeypress="return key(event,this)">
        </tr-->
               <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')" tabindex="4"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')" tabindex="5"/>
                     </td>
                    <!-- <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" />
                     </td>-->
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="self.close()">
                     </td>
                </tr>
             </table>
                </div></td>
            </tr>       
           
            </table>
    </form></body>
</html>
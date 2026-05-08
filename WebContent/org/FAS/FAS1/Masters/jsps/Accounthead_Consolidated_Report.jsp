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
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/GroupNameLoading.js"></script>
    
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Accounthead_Consolidated_Report.js"></script>    
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     
     
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
     
    
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
        
             <tr class="table">
                <td>
                  <div align="left">
                     Section Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                 
                <td>
                  <div align="left">
                
                    <select size="1" name="txtSectionId" id="txtSectionId" tabindex="3" onchange="LoadGroupName()" >
                    <option>----Select Section----</option>
                      <%
                    try
                    {
                     ps=con.prepareStatement("select SECTION_ID,SECTION_NAME from FAS_MST_SECTIONS ");
                     // ps.setInt(1,oid);
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                        out.println("<option value="+rs.getInt("SECTION_ID")+">"+rs.getString("SECTION_NAME")+"</option>");
                     }
                      System.out.println();  
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
              
              <tr class="table">
                <td>
                  <div align="left">
                     Group Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                 
                <td>
                  <div align="left">
                
                    <select size="1" name="txtGroupId" id="txtGroupId" onchange=Load_Group_Code() tabindex="3" >
                       <option>----Select Group ----</option>                  
                    </select>
                  </div>
                </td>
              </tr>
              
              
         <tr class="table">
                <td>Account Head Code:<font color="#ff2121">*</font></td>
                <td>
                    <input type=text name=txtaccountheadcode id=txtaccountheadcode onchange="doFunction('checkCode','null')" onkeypress="return numbersonly(event,this)">
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                 height="20" alt="AccountHeadList"
                 onclick="AccHeadpopup();"></img>
                </td>
        </tr>
        
        
        <tr class="table">
                <td>Account Head Name:</td>
                <td><input type=text name="txtaccountheadname"  id="txtaccountheadname" size="55" disabled >
        </tr>
             
        
        
        
        
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
                     <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" />
                     </td>
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
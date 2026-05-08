<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_SupplierMasterJSP</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_SupplierMasterJS.js">
    //document.getElementsById("ltype").focus();
    </script>
  </head>
  <body onload="callServer('Get','null')">
  <form action="" name="SupplierForm"> 
  <%
            Connection con=null;
            Statement st=null;
            ResultSet rs=null;
            PreparedStatement ps=null;
            try
              {
                  //Class.forName("oracle.jdbc.OracleDriver");
                  //con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","test","test");
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
                  System.out.println("Connected THRO JSP");
              }
              catch(Exception e)
              {
              System.out.println(e.getMessage());
              }
 
                HttpSession session=request.getSession(false);
                UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                  
                System.out.println("user id::"+empProfile.getEmployeeId());
                int empid=empProfile.getEmployeeId();
                int  oid=0,odidt=0;
                String odt="",lb=""; 
                try
                {
           
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                            oid=rs.getInt("OFFICE_ID");
                        
                    }
                    rs.close();
                    ps.close();
                    ps=con.prepareStatement("select LAB_CODE,LAB_DESC from WQS_MST_LAB where LAB_CODE=?");
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                        odidt=Integer.parseInt(rs.getString("LAB_CODE"));
                        odt=rs.getString("LAB_DESC");
                        lb=odidt+"--"+odt;
                        System.out.println(lb);
                    }
                    rs.close();
                    ps.close();
               
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
  %>
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Supplier Master</b></td>
                   
       </tr> 
       <tr>
            <td class="table" width="50%">Lab<font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="lab" size="50" id="lab" value="<%=lb%>"/>
            </td>
        </tr>  
        <tr id="divwork" style="display:none">
            <td class="table" width="50%">Supplier Code<font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="txtSupId" size="5" id="txtSupId" readonly="readonly">
            </td>
        </tr>       
        <tr>
            <td class="table" width="50%">Supplier Name<font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="txtSupName" size="55" maxlength="50" id="txtSupName" onblur=isChar(this.value)>
            </td>
        </tr>      
              
        <tr>
            <td class="table" width="50%">Address1<font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="txtAddr1" size="40" maxlength="50" id="txtAddr1"/>
          <!--  </td>
        </tr>  
        <tr>
            <td class="table" width="50%">Address2</td>
            <td class="table" width="50%">-->
            <input type="text" name="txtAddr2" size="40" maxlength="50" id="txtAddr2"/>
          <!--  </td>
        </tr>  
        <tr>
            <td class="table" width="50%">Address3</td>
            <td class="table" width="50%">-->
            <input type="text" name="txtAddr3" size="40" maxlength="50" id="txtAddr3"/>
            </td>
        </tr>  
       <tr>
            <td class="table" width="50%">Pin Code<font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="txtPin" size="8" id="txtPin" maxlength="6" onblur=checkPin()>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">District Name<font color="Red">*</font></td>
            <td class="table" width="50%">
            <select name="txtdname" id="txtdname" >
            <option value="">Select District</option>
            </select>
            </td>
        </tr>  
        <tr>
            <td class="table" width="50%">Contact Phone1<font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="txtPhone1" size="20" id="txtPhone1" maxlength="15"/>
            </td>
        </tr>  
        <tr>
            <td class="table" width="50%">Contact Phone2</td>
            <td class="table" width="50%">
            <input type="text" name="txtPhone2" size="20" id="txtPhone2" maxlength="15"/>
            </td>
        </tr>  
        <tr>
            <td class="table" width="50%">Fax</td>
            <td class="table" width="50%">
            <input type="text" name="txtfax" size="25" maxlength="20" id="txtfax" onblur="validmail()"/>
            </td>
        </tr> 
        
         <tr>
            <td class="table" width="50%">E Mail</td>
            <td class="table" width="50%">
            <input type="text" name="txtMail" size="50" maxlength="50" id="txtMail" onblur="validmail()"/>
            </td>
        </tr> 
         <tr>
            <td class="table" width="50%">File Reference</td>
            <td class="table" width="50%">
            <input type="text" name="txtref" size="70" id="txtref" maxlength="20"/>
            </td>
        </tr> 
        <tr>
            <td class="table" width="50%">Current Status<font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="txtstatus" size="1" id="txtstatus" maxlength="1" onblur=checkVal()>&nbsp;<font color="Gray">(A-Active T-Terminate)</font>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Remarks</td>
            <td class="table" width="50%">
            <textarea rows="3" name="txtremarks" id="txtremarks" cols="40" onkeypress="return checklength(event,this)"></textarea>
            </td>
        </tr>
        
        <tr>
          <td colspan="2" class="table" align="center">
            <input type="button" name="CmdAdd" value=" ADD " id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR"
                   id="CmdClear" onclick="clearAll()"/>
          </td>
        </tr>
        
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="exit" value="Exit" id="exit" onclick="close_win()"/>
          </td>
         
        </tr>
    </table>
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
          <td class="table" align="center"><b>Existing Details</b></td>
        </tr>
      </table>
      <table id="Existing" border="1" width="100%"
             align="center">
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Lab
          </th>
          <th>
            Supplier Code
          </th>
         <th>
            Supplier Name
          </th>        
          <th>
            Address1
          </th>
          <th>
            Address2
          </th>
          <th>
            Address3
          </th>
          <th>
            Pin Code
          </th>
          <th>
            District Name
          </th>
          <th>
             Contact Phone1
          </th>
          <th>
             Contact Phone2
          </th>
          <th>
             Fax
          </th>
          <th>
             E_mail
          </th>
          <th>
             File Reference
          </th>
          <th>
             Current status
          </th>
          <th>
             Remarks
          </th>
          </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
    
  </form>
  </body>
</html>
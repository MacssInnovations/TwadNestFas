<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Create Employee Basic Details</title>
     <script type="text/javascript" src="../scripts/OtherUserProfileJS.js"></script> 
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script> 
      
      
      <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../css/try1.css" rel="stylesheet" media="screen"/>
  </head>
  <body class="table" onload="togetFocus()"     id="bodyid">
  <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   
    PreparedStatement ps=null;

  try
  {
         ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
    String ConnectionString="";
   
    String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
    String strdsn=rs.getString("Config.DSN");
    String strhostname=rs.getString("Config.HOST_NAME");
    String strportno=rs.getString("Config.PORT_NUMBER");
    String strsid=rs.getString("Config.SID");
    String strdbusername=rs.getString("Config.USER_NAME");
    String strdbpassword=rs.getString("Config.PASSWORD");
      
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }  
 %>     
  <form name="frmOtherUser" id="frmOtherUser"  class="table">
  <table width="100%">
      
      <tr>
        <td align="center" colspan="2">
         <table border="1px" width="650px">
         
         <tr>
      <td align="center" class="tdH"  colspan="4">
      <b>Other User Profile</b><input type="HIDDEN" name="txtMode" value="insert">
      </td>      
      </tr>
      
              <TR>
                     <TD rowspan="2">
                        Employee&nbsp; Name
                     </TD>
                     <TD>
                        Prefix<label style="color:rgb(255,0,0);"/>
                     </TD>
                     <TD>
                        <center>
                        Initial
                        </center>
                     </TD>
                     <TD>
                         Name<label style="color:rgb(255,0,0);">*</label>
                     </TD>
            </TR>
            <TR>
                     
                     <TD>
                       <select name="Employee_Prefix" tabindex="2">
                       
                         <option value="Mr" selected>Mr</option>
                         <option value="Mrs">Mrs</option>
                         <option value="Thiru">Thiru</option>
                         <option value="Selvi">Selvi</option>
                       </select>
                     </TD>
              <TD>
              <INPUT tabIndex=2 size=10 maxlength="8" name=Employee_Initial onchange="return toCheck1()" style="TEXT-TRANSFORM:UPPERCASE"  onkeypress="return nonanum(event)"></TD>
              <TD>
              <INPUT tabIndex=2 size=30  maxlength="40" name=Employee_Name onchange="return toCheck()"  onkeypress="return nonanum(event)"></TD>
             
        </TR>
        
             
        <TR>
             <TD colspan="2">
                  User Category
                  <font color="#ff2121">
                    *
                  </font>
            </TD>
             <TD colspan="2">
                  <select name="txtusercategory"  id="txtusercategory" >
                         <!--<option value="">Select the Department</option>-->
                         <option value="">Select Category</option>
                         <%
                         try
                         {
                        ps=connection.prepareStatement("select a.USER_CATEGORY_ID,a.USER_CATEGORY_DESC from SEC_MST_USER_CATEGORY a where a.USER_CATEGORY_ID <> 1  order by USER_CATEGORY_DESC");
                        ResultSet rs=ps.executeQuery();
                        String strcode="";
                        String strname="";           
                        while(rs.next())
                        {
                            strcode=rs.getString("USER_CATEGORY_ID");
                            strname=rs.getString("USER_CATEGORY_DESC");
                            
                            out.println("<option value='"+strcode+"'>"+strname+"</option>");
                            
                         }
                      }
                      catch(Exception e)
                      {
                        System.out.println("Exception in grid.."+e);
                      }
                     
                         
                         %>
                         </select>      
             </TD>
   </TR>
   
      <TR>
            <TD colspan="2">
                  Office Id
                  <font color="#ff2121">
                    *
                  </font>
                </TD>
            <TD colspan="2">
                   <input type="text" name="txtoffid" maxlength="4" size="10" onkeypress="return numbersonly1(event,t)">
            </TD>
    </tr>
    
    <TR>
             <TD colspan="2">
                  Wing
                  <!--<font color="#ff2121">
                    *
                  </font>-->
            </TD>
             <TD colspan="2">
                  <select name="txtwing"  id="txtwing" >
                         <!--<option value="">Select the Department</option>-->
                         <option value="0">--Select Wing--</option>
                         <%
                         try
                         {
                        ps=connection.prepareStatement("select OFFICE_WING_SINO,WING_NAME from COM_OFFICE_WINGS order by OFFICE_WING_SINO");
                        ResultSet rs=ps.executeQuery();
                        int wcode=0;
                        String wname="";           
                        while(rs.next())
                        {
                            wcode=rs.getInt("OFFICE_WING_SINO");
                            wname=rs.getString("WING_NAME");
                            
                            out.println("<option value='"+wcode+"'>"+wname+"</option>");
                            
                         }
                      }
                      catch(Exception e)
                      {
                        System.out.println("Exception in grid.."+e);
                      }
                     
                         
                         %>
                         </select>      
             </TD>
   </TR>
   
   <TR>
             <TD colspan="2">
                  Office Name
                  <font color="#ff2121">
                    *
                  </font>
                </TD>
             <TD colspan="2">
                   <input type="text" name="txtofficename" size="20"></input>
            </TD>
    </tr>
    
     <TR>
             <TD colspan="2">
                  Office Address
            </TD>
            <TD colspan="2">
                   <textarea  name="txtofficeaddress" rows="4" cols="40"></textarea>
            </TD>
    </tr>
    <TR>
            <TD colspan="2">
                  Designation
                  <font color="#ff2121">
                    *
                  </font>
                </TD>
             <TD colspan="2">
                   <input type="text" name="txtDesignation" size="40">
            </TD>
    </tr>
    
     <TR>
            <TD colspan="2">
                  E-Mail
            </TD>
            <TD colspan="2">
                   <input type="text" name="txtemail" size="70">
            </TD>
    </tr>
    <tr>
        <td colspan="4" class="tdH">
            User ID/Password
            </td>
    </tr>
    
    <TR>
            <TD colspan="2">
                  User Id
                  <font color="#ff2121">
                    *
                  </font>
                </TD>
            <TD colspan="2">
                   <input type="text" name="txtuserid" size="10">
            </TD>
    </tr>
    
    <TR>
            <TD colspan="2">
                  Preffered password
                  <font color="#ff2121">
                    *
                  </font>
                </TD>
            <TD colspan="2">
                   <input type="password" name="txtpassword" size="10">
            </TD>
    </tr>
    
     <TR>
            <TD colspan="2">
                  Confirm password
                  <font color="#ff2121">
                    *
                  </font>
                </TD>
            <TD colspan="2">
                   <input type="password" name="txtcpassword" size="10">
            </TD>
    </tr>
    
   <TR>
        <TD colspan="4"  align="center" class="tdH">
                 <INPUT  type="Button" value=Submit name=but onclick="callServer('Add','null')">&nbsp;&nbsp;
                 <input type="reset" value=" Clear " name="cmdreset">&nbsp;&nbsp;
                 <input type="Button" value=" Cancel " name="cmdCancel" onclick="self.close();">
        </TD>
  </TR>
      
         
  </table>
  </td>
  </tr>
 
      
     
 
      
    
    
    </table>
  </form>
  </body>
  </html>

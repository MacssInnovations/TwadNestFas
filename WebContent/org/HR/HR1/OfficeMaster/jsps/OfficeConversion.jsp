<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Office Conversion</title>
    <script type="text/javascript" src="../scripts/ConversionValidation.js" >    
    </script>    
    <script type="text/javascript" src="../../../../Library/scripts/selectOffice.js">
    </script>
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript">

            function closeWindow()
            {                
                window.open('','_parent','');                
                window.close(); 
                window.opener.focus();
            }

    </script>
    <link href='../../../../../css/yellow.css' rel='stylesheet' media='screen'/>
  </head>
   <body class="bgbody"> 
 <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   

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
    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

       
       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              System.out.println("Exception in creating statement:"+e);
              return;
       }          
  }
  catch(Exception e)
  {         
         System.out.println("Exception in openeing connection:"+e);
         return;
  }  
 %>
  
  <table  cellspacing="1" cellpadding="3" width="100%">
                    <tr>
                        <td class="bgClass">
                            <center>                           
                            <b>Conversion of an Office from one type to another</b>                            
                            </center>
                        </td>
                    </tr>  
                    
                    <tr><td><br></td></tr>
                    
                    <tr>
                    <td>
        <form name="frmConversion" method="POST" action="../../../../../ServletConvertOffice.con" onsubmit="return nullcheck()" >                
                            <table  cellspacing="3" cellpadding="1"  width="100%"
                                   border="0">
                                  <TR>
                                      <TD vAlign=middle >
                                          <P style="MARGIN-BOTTOM: -16px"><B><FONT face=Tahoma color=#808080 
                                                                        size=2>Office Id
                                                                        <label style="color:rgb(255,0,0);">
                                                                              &nbsp;*
                                                                        </label>
                                                                  </FONT></B></P>
                                      </TD>
                                      <TD vAlign="middle" >
                                           <table>
                                        <tr>
                                            <td></td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>                                            
                                            <td>
                                                <input type="text" name="txtOffice_Id" maxlength="6" size="6"
                                                       onblur="loadOffice(document.frmConversion.txtOffice_Id.value);"/>
                                            </td>
                                            <td>
                                                <SELECT size=1 name=cmbOfficeLevel onchange="getOfficesByLevel(this.form.name)">   
                                                <option value=0>
                                                  ----Select OfficeLevel----
                                                </option>
                                                <%
                                                      try
                                                      {
                                                        results=statement.executeQuery("select * from COM_MST_OFFICE_LEVELS"); 
                                                        while(results.next()) 
                                                        {
                                                            out.print("<option value='" + results.getString("Office_Level_Id") + "'>" + results.getString("Office_Level_Name") + "</option>");                      
                                                        }
                                                        results.close();
                                                      }
                                                      catch(Exception e)
                                                      {                        
                                                      }      
                                                %>
                                              </SELECT>
                                              </td>
                                              <td>
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType(this.form.name)">
                                                    <option value=0>
                                                        ----Select Office
                                                        Type----
                                                    </option>
                                                    <%
                                                          try
                                                          {
                                                            results=statement.executeQuery("select * from COM_MST_WORK_NATURE"); 
                                                            while(results.next()) 
                                                            {
                                                                out.print("<option value='" + results.getString("Work_Nature_Id") + "'>" + results.getString("Work_Nature_Desc") + "</option>");                      
                                                            }
                                                            results.close();
                                                          }
                                                          catch(Exception e)
                                                          {}      
                                                    %>       
                                                </select>
                                            </td>
                                            <td>
                                                <select name="cmbSelectOffice" id="cmbSelectOffice" style="visibility:hidden" 
                                                        onchange="selectOffice(this.form.name,'txtOffice_Id');loadOffice(document.frmConversion.txtOffice_Id.value);">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        </table>                                         
                                      </TD>
                                  </TR>
                                  <tr>
                                      <td class="td">Office Name  </td>
                                      <td style="color:rgb(255,51,102);">
                                        <input type="text" name="txtOfficeName" size="25"
                                               maxlength="60"
                                               disabled="disabled"/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">In case of Division/Sub Division, specify Type</td>
                                      <td>
                                        <input type="text" name="txtOfficeType" maxlength="30"
                                               size="25"
                                               disabled="disabled"/>
                                      </td>
                                  </tr>
                                  <tr>
                                        <td  width="36%">Office Address</td>
                                        <td width="64%">
                                            <textarea name="txtOfficeAddress" cols="25" rows="4" disabled="disabled"></textarea>
                                        </td>
                                    </tr>
                                  <tr>
                                      <td class="td">New Name of the Office <label style="color:rgb(255,0,0);">&nbsp;*</label> </td>
                                      <td style="color:rgb(255,51,102);">
                                        <input type="text"
                                               name="txtNewOfficeName" size="25"  maxlength="60" />
                                      </td>
                                  </tr>
                                  
                                  <tr>
                                      <td class="td">Primary Work&nbsp;Nature  
                                      <label style="color:rgb(255,0,0);">  &nbsp;*</label></td>
                                      <td>                                        
                                        <select name="cmbPrimaryID">                                        
                                            <option value>
                                                                  ----Select
                                                                  Work Nature----
                                                            </option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from COM_MST_WORK_NATURE"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getString("Work_Nature_Id") + "'>" + results.getString("Work_Nature_Desc") + "</option>");                      
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {}      
                                            %>                 
                                        </select>
                                      </td>
                                  </tr>                        
                                  
                                  <tr>
                                      <td class="td">Date Of Conversion
                                                      <label style="color:rgb(255,0,0);">
                                                              &nbsp;*
                                                      </label>
                                                </td>
                                      <td>
                                        <input type="text" name="txtDOC" maxlength="10" size="10"
                                               onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')"/>
                                        (dd/MM/yyyy)
                                        </td>
                                  </tr>
                                  <tr>
                                      <td class="td">
                                        Remarks
                                      </td>
                                      <td>
                                        <textarea name="txtRemarks" cols="25" rows="5" ></textarea>
                                      </td>
                                  </tr>
                                </table>
                                <br>
                                <div id="bghead" class="bgClass">
                                    <center>  
                                        <input type="SUBMIT" value="  Submit  " name="cmdSub"> &nbsp;
                                        <input type="RESET" value=" Clear All " name="cmdClear">&nbsp; 
                                        <input type="BUTTON" value=" Cancel " name="cmdCancel" onclick="closeWindow();"> &nbsp;                                                              
                                    </center>
                                </div>
                            </form>
                        </td>
                    </tr>
                  </table>                
  </body>
</html>
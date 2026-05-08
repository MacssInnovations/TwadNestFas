<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Master Asset Class Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Assets_Minor_Classification_JS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>



  <body onload="callServer('LoadMajorClass','null');callServer('Get','null');" class="table">
  <%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  ResultSet results1=null;
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
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %>
  
  
  <form action="" name="frmAssetMinorClassMaster" id="frmAssetMinorClassMaster">
    <input type='hidden' name='RecordCount' id='RecordCount' value='0' /> 
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="3" class="tdH" align="center">
	            <b>
		            Asset Minor Classification Details
	            </b>
            </td>
       </tr> 
    
    
       <tr>
            <td class="table">
            	Asset Minor Class Code
            </td>
          
          	<td class="table">
    	        <input type="text" name="txtMinorCode" maxlength="2"
                   id="txtMinorCode" readonly size="3"/>
                System Generated
           </td>
        </tr>


        <tr>
            <td class="table">
            	Asset Major Class</td>
            <td class="table">
            	<select name="cmbMajorClass" id="cmbMajorClass" onchange="callServer('Get','null')">
    	        	<option value="0">--Select Major Class--</option>
	            </select>
        	</td>
        </tr>
        
                
        <tr>
            <td class="table">
            	Asset Class Description
            </td>
            <td class="table">
            	<input type="text" name="txtMinorDesc" size="30" maxlength="25"
                	   id="txtMinorDesc"/>
            </td>
        </tr>
        
        <tr style="display:none">
        	<td>
            	<input type="text" name="hidRowId" id="hidRowId"/>
        	</td>
        </tr>
        
        <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="CANCEL"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll()"/>
            <input type="button" name="Exit" value="Exit" onclick="closeWindow()">
          </td>
        </tr>
    </table>



    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
          <td class="table"><b>Existing Details</b></td>
        </tr>
    </table>
    
    
    
    <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Asset Minor Class Code
          </th>
          <th>
            Asset Minor Class Description
          </th>
          <th>
            Asset Major Class
          </th>
          <th>
            Status
          </th>
        </tr>
    
        <tbody id="tbody" class="table">
        </tbody>
        
    </table>
    
     <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            		<tr>
                		<td>
                    		 <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
                     <tr class="tdH">
                  <td>
                    <table align="center" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      <tr>
                        <td width="30%">
                          <div align="left">
                            <div id="divpre" style="display:none"></div>
                          </div>
                        </td>
                        <td width="40%">
                          <div align="center">
                            <table border="0">
                              <tr>
                                <td>
                                  <div id="divcmbpage" style="display:none">
                                    Page&nbsp;&nbsp;<select name="cmbpage"
                                                            id="cmbpage"
                                                            onchange="changepage()"></select>
                                  </div>
                                </td>
                                <td>
                                  <div id="divpage"></div>
                                </td>
                              </tr>
                            </table>
                          </div>
                        </td>
                        <td width="30%">
                          <div align="right">
                            <div id="divnext" style="display:none"></div>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                
              </table>
        				</td>
    				</tr>
      				<tr class="tdH">
      					<td>
          					<div align="center">
          					<input type="submit" id="cmdverify" name="cmdverify" value="VERIFY">
         					<input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="window.close();">
      						</div>
      					</td>
      				</tr>
		      </table>
    
  </form>
  
  </body>
</html>
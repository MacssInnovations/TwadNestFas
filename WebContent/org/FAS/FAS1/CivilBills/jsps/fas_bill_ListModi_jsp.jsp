<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
                <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>  
                <meta http-equiv="cache-control" content="no-cache">
                <title>FAS Bill Transaction List</title>
                <!--
                <link href="css/fas.css" rel="stylesheet"  media="screen"/>
                 -->
                 <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
                 <script language="javascript" type="text/javascript">
                            function closeWindow()
                            {                
                                window.open('','_parent','');                
                                window.close(); 
                                window.opener.focus();
                            }
                </script>
               <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
                <script type="text/javascript" language="javascript" src="../scripts/fas_bill_ListModi_js.js"> </script>
  </head>
  <body onload="ListMajorMinorSub();" class="table"><!-- loadMinorType(),
          --><table cellspacing="1" cellpadding="3" width="100%" >
              <tr class="tdH">
                <td colspan="2">
                  <div align="center">
                    <font size="4">List of Major-Minor-Sub-Type Bills</font>
                  </div>
                </td>
              </tr>
            </table>
    <form name="fas_bill_list_form" id="fas_bill_list_form" >
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
                            %>
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
             <tr class="table">
                <td>
                        <div align="left">
                                Bill Major Type Code
                        </div>
                </td>
                 
                <td>
                        <div align="left">
                            <select size="1" name="bill_majr_code" id="bill_majr_code" tabindex="3"  onchange="ListMajorMinorSub()" >
                            <option value="All">All</option>
                             <%
                                        try
                                            {
                                                ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
                                                rs=ps.executeQuery();
                                                 while(rs.next())
                                                    {
                                                         out.println("<option value="+ rs.getInt("BILL_MAJOR_TYPE_CODE") + ">" + rs.getString("BILL_MAJOR_TYPE_DESC") +"</option>" );
                                                    }
                                            } 
                                             catch(Exception e)
                                                {
                                                    System.out.println("Exception in Bill Type Major combo..."+e);
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
                     Bill Minor Type Code
                  </div>
                </td>
                 
                <td>
                  <div align="left">
                   <input type=text name=bill_minr_code_desc id=bill_minr_code_desc >
                    <input type=text name=bill_minr_code id=bill_minr_code value="All" disabled>
                    <!--
                    <select size="1" name="bill_minr_code" id="bill_minr_code" tabindex="3" onchange="cleardynrow(),ListMajorMinorSub();" >
                    <option value="All">All</option>                     
                    </select>
                  --></div>
                </td>
              </tr>
            <tr class="table">
                 <td>
                    <div align="left">
                         Bill Sub Type Code
                    </div>
                 </td>
                 <td>
                    <input type=text name=txtbillsubcode id=txtbillsubcode disabled>
                  </td>
            </tr>
            <tr class="table">
                    <td>
                            <div align="left">
                                    Bill Sub Type Description
                             </div>
                     </td>
                    <td>
                    <input type=text name=txtbillsubdesc id=txtbillsubdesc  />
                    </td>
            </tr>
            </table>
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
             <tr class="tdH">
             <td colspan="2">
                            <div align="center">
                            <table >
                                    <tr>
                                            <!--
                                            <td>
                                            <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')" tabindex="4"/>
                                             </td>
                                             -->
                                             <td>
                                            <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:inline" onclick="callServer('Update')" tabindex="5"/>
                                             </td>
                                            <td>
                                            <input type="button" name="cmdDelete" value="CANCEL" id="cmdDelete" style="display:inline" onclick="callServer('Delete')" />
                                             </td>
                                             <td>
                                            <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
                                             </td>
                                             <!--<td>
                                            <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListMajorMinorSub()"/>
                                             </td> 
                                               --><td>
                                             <input type="button" id="Exit" name="Exit" value="EXIT" onclick="self.close()">
                                            </td>
                                    </tr>
                            </table>
                            </div>
                            </td>
                            </tr> 
                            </table>
                            <table align="center" cellspacing="3" cellpadding="2" border="1" width="100%">
                                <tr>
                                    <td colspan="2" align="center"><strong>Existing Details</strong></td>
                                </tr>
                            </table>
                            <table  align="center" border="1" cellpadding="2" cellspacing="3" width="100%" id="dyntable">
                                    <tr>
                                        <th class="tdH"> Select</th><!--
                                        
                                        <th class="tdH"> Bill Major Type Code </th>
                                        --><th class="tdH"> Bill Major Type Description</th><!-- 
                                         <th class="tdH"> Bill Minor Type Code </th>
                                        --><th class="tdH"> Bill Minor Type Description</th><!--
                                        <th class="tdH"> Bill Sub Type Code </th>
                                        --><th class="tdH"> Bill Sub Type Code Description</th>
                                        <th class="tdH"> Status</th>
                                    </tr>
                                    <tbody id="dynbody" >
                                    </tbody>
                            </table>
            
    </form>
    </body>
</html>
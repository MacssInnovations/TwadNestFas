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
    <title>fas_bill_trans_jsp</title>
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
                function fillMinorCode(){
                	
               document.getElementById("bill_minr_type_code").value=document.getElementById("bill_minr_desc1").value;
               document.getElementById("bill_minr_desc").value=document.getElementById("bill_minr_desc1").innerHTML;
               }
    </script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
   <script type="text/javascript"  language="javascript" src="../scripts/fas_bill_trans_js.js">
   </script>
   </head>
 
  <body class="table" >

   
           <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                 <tr class="tdH" align="center">
                    <th>
                           TWAD Board-Integrated Online System-Financial Accounting System 
                    </th>
                </tr>
           </table>
   <br>
   
  <table cellspacing="1" cellpadding="3" border="1" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Bill Minor Type Master</font>
          </div>
        </td>
      </tr>
    </table><form name="fas_bill_minr_form" id="fas_bill_minr_form">
  <input type="hidden" id="hid" name="hid" value="Y">
  <table cellpadding="3"  cellspacing="2" border="1"  width="100%" >
  <tr>
                <td>
                  <div align="left" >
                  	  Bill Major Type Code
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select size="1" name="bill_majr_type_code" id="bill_majr_type_code">
                    <option value="0">-----Select Bill Major Type Code-----</option>
  <!--             Getting the Bill Major type code from the Bill_type_mastertable           -->
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
                    <!-- <input type="button" name="go_butn" id="go_butn" value="GO" onclick="Loadmajtypecode()"/> -->
                  </div>
                </td>
   </tr>
          
           <tr>
    <td>
        <div align="left" >New Minor Type Code</div>
        </td>
   <td><div align="left" >
   <input type="radio" id="Typewise" name="Typewise" value="YES" checked="checked"  onchange="chkNeworOld(this.value);" >YES
      <input type="radio" id="Typewise" name="Typewise" value="NO" onchange="chkNeworOld(this.value,'<%= request.getContextPath()%>');" >NO
   </div></td>
           
 <tr>
    <td>
        <div align="left" >
                Bill Minor Type Code
        </div>
     </td>
            <td>
            <div align="left">
                <input type="text" name="bill_minr_type_code" id="bill_minr_type_code"  size="3" readonly/>(Auto generated)
            </div>
            
            </td>
  </tr>
  <tr>
      <td>
            <div align="left">
                Bill Minor Type Description
            </div>
            </td>
        <td>
        <div align="left" id="NewMinor" name="NewMinor" style="display:block;">
               <input type="text" name="bill_minr_desc" id="bill_minr_desc" maxlength="50"/>
        </div> <div align="left" id="OldMinor" name="OldMinor" style="display:none;">
        <select  name="bill_minr_desc1" id="bill_minr_desc1" onchange="fillMinorCode();"></select>
        </div>
        </td>
    </tr>
<!--      ********************************************* -->
            <tr>
                <td>
                  <div align="left">
                            Is Sub Type Available ?
                  </div>
                </td>
                <td>
                  <div align="left">
                   <table border="0">
                    <tr> 
                     <td>
                     <div id="yes_applicable">
                        <input type="radio" name="sub_type_YN" 
                               id="sub_type_YN"
                               onclick="enableSub_type(this.value);" 
                               value="Y"/>YES
                        &nbsp;&nbsp;&nbsp;&nbsp;
                     </div>   
                     </td>
                     <td>
                     <div id="no_applicalbe">
                        <input type="radio" name="sub_type_YN"
                               id="sub_type_YN"  
                               checked="checked"
                               onclick="enableSub_type(this.value);" 
                               value="N"/>NO
                      </div>     
                      </td>
                      </tr>
                    </table>       
                  </div>
                </td>
              </tr>
<!-- ****************************************** -->
<tr>
        <td>
            <div align="left" >
            Is Proceeding Applicable?
            </div>
            </td>
            <td>
            <div align="left" >
            <input  type="radio" name="pro_avai_YN" id="pro_avai_YN" value="Y" />Yes &nbsp;&nbsp;&nbsp;
            <input  type="radio" name="pro_avai_YN" id="pro_avai_YN" value="N" checked/>No 
            </div>
            </td>
</tr>
<!--====================================================================-->
<tr>
        <td>
        <div align="left" >
                Remarks
        </div>
        </td>
        <td>
            <div align="left">
            <textarea cols="40" rows="4"  name="pro_remarks" id="pro_remarks">
            </textarea>
            </div>
        </td>
</tr>
</table>
<!-- Details table will be viewed if sub type available-->
<div id="sub_type_disp" style="display:none">
<h3>Details</h3>
              <table cellpadding="3"  cellspacing="2" border="1"  width="100%" >
                   <tr>
                     <td>
                       <div align="left" >
                  	     Sub Type Code
                          </div>
                         </td>
                          <td>
                                <div align="left">
                                          <input type="text" name="sub_type_code" id="sub_type_code"  maxlength="10" size="10" readonly/>(Auto generated)
                              </div>
                           </td>
                        </tr>
           
                     <tr>
                          <td>
                                <div align="left" >
                                        Sub Type Description
                                 </div>
                            </td>
                        <td>
                                <div align="left" >
                                    <input type="text" name="sub_type_desc" id="sub_type_desc" maxlength="50" size="20"/>
                                </div>
                        </td>
                        </tr>
                        
                                    <div align="center">
                                        <table border="0">
                                        <tr>
                                        <td>
                                                
                                                <input type="button" name="cmdadd" id="cmdadd"
                                                        value="ADD" onclick="add_GRID()" style="display:block"/>
                                               
                                        </td>
                                        <td>
                                            
                                            <input type="button" name="cmdupdate" value="UPDATE"
                                                    id="cmdupdate" onclick="update_GRID()"
                                                    style="display:none"/>
                                             
                                        </td>
                                        <td>
                                         
                                        <input type="button" name="cmddelete" value="DELETE"
                                                            id="cmddelete" onclick="delete_GRID()"
                                                            disabled="disabled"/>
                                      
                                        </td>
                                        <td>
                                         
                                        <input type="button" name="cmdclear" value="CLEAR ALL"
                                                        id="cmdclear" onclick="clearall()"/>
                                        
                                        </td>
                                        </tr>
                                        </table>
                                         </div>
</table>
</div>

                    <div id="grid" style="display:none">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                                    border="1" width="100%">
                      <tr>
                        <th>Select</th>
                        <th>Sub Type Code</th>
                        <th>Sub Type Description</th>
                        </tr>
                       
                                <tbody id="grid_body" class="table" align="left" >
                                </tbody>
                   
                     </table>
                     </div>
                     
               
 <!--     ................................................................................................... -->               
      <br>
                <div align="center">
                    <table cellspacing="1" cellpadding="3" width="100%">
                        <tr class="tdH">
                            <td>
                                <div align="center">
                                    <input type="button" name="butSub" id="butSub" value="Save"  onclick="add_bill_trn();"/>
                                                &nbsp;&nbsp;&nbsp; 
                                    <input type="button" name="butCan" id="butCan" value="CANCEL" onclick="clrForm();"/>
                                            &nbsp;&nbsp;&nbsp; 
                                    <input type="button" name="butList" id="butList" value="LIST" onclick="CallListjsp();"/>
                                            &nbsp;&nbsp;&nbsp; 
                                    <input type="button" name="butCan" id="butCan" value="EXIT" onclick="exitmethod();"/>
                                </div>
                            </td>
                        </tr>
                </table>
                </div>


</form>
</body>
</html>
 
    

    
   
 
      
         
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Assets Lend Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Asset_Lend_Details.js"></script>
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
            <font size="4">Assets Temporarily Lend to Other Offices </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmAMC_Lend_Details" id="frmAMC_Lend_Details" >
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
       Statement statement=null;
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
    
               // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
    					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
     int bankid=0;
     
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
            System.out.println("off id.. emp id"+oid+".."+empid);     
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
                        
              <tr class="table">
                <td>
                  <div align="left">  Major Classisfication of Asset
                   
                   </div>
                </td>
                
              <td colspan="2">
                    <select name="cmbassetclass" id="cmbassetclass" onChange="loadAssetCode()">
                    <option value="">--Select Major Class--</option>
                    <%
                        Statement st=con.createStatement();
                        rs=st.executeQuery("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_ASSET_CLASSIFICATION");
                        while(rs.next())
                        {
                            out.println("<option value="+rs.getInt("ASSET_MAJOR_CLASS_CODE")+">"+rs.getString("ASSET_MAJOR_CLASS_DESC")+"</option>");
                        }
                    %>
                    </select>
              </td>
              </tr>  
          
              <tr class="table">
                <td>
                  <div align="left">
                     Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbasset" id="cmbasset" tabindex="3" >
                    <option value=0>-- Select Asset Code --</option>
                    
                    </select>
                  </div>
                </td>
              </tr>
              
              
              <TR class="table">
                                      <TD >
                                          Physical Location of Asset<font color="#ff2121">*</font>
                                </TD>
                                  <!--     <TD>
                                          <input type="text" name="txtphy_loc" id="txtphy_loc"  >
                                                                                   
                                      </TD> -->
                                      <td>
                                        <SELECT size=1 name=txtphy_loc id=txtphy_loc >   
                                            <option value>----Select Officename----</option>
                                            <%
                                            try
                                            {
                                                
                                             
                                                
                                                    ps=con.prepareStatement("select office_id,OFFICE_NAME from COM_MST_OFFICES order by OFFICE_NAME");
                                                    //ps.setInt(1,unitid);
                                                    rs=ps.executeQuery();
                                                    //out.println("<option value="+oid+">"+oname+"</option>");
                                                    while(rs.next())
                                                    {
                                                    
                                                   // out.println("<option value="+rs.getString("OFFICE_NAME")+"</option>");
                                                        out.println("<option value="+rs.getString("OFFICE_NAME")+">"+rs.getString("OFFICE_NAME")+"</option>");  
                                                }
                                               
                                            } 
                                            catch(Exception e)
                                            {
                                            System.out.println("Exception in Office combo..."+e);
                                            }
                                                
                                            %>
                                          </SELECT>
                                      </td>
                                  </TR>
                                  <tr class="table">
              
                <td>
                  <div align="left">
                   Transferred to which office          <font color="#ff2121">* </font>
                          </div>
                </td>
                <td>
                  
                    <input type="radio" name="txtcheck" id="txtcheck" tabindex="13"
                            value="T" checked onclick="cheque(this.value)" checked="checked">Twad
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtcheck" id="txtcheck" tabindex="14"
                           value="O" onclick="cheque(this.value)">Others &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                
                        
                </tr>
                </table>
          
                <div align="left" id="div_twad" style="display:block" >
               <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <TR class="table">
                                      <TD >
                                       <div align="left">
                                          Transferred Office Name            <font color="#ff2121">*</font>
                                       </div>
                                </TD>
                                <td>
                                <div align="left">
                                        <SELECT size=1 name=txtOffice_Name id=txtOffice_Name >   
                                            <option value>----Select Officename----</option>
                                            <%
                                            try
                                            {
                                                
                                             
                                                
                                                    ps=con.prepareStatement("select office_id,OFFICE_NAME from COM_MST_OFFICES order by OFFICE_NAME");
                                                    //ps.setInt(1,unitid);
                                                    rs=ps.executeQuery();
                                                    //out.println("<option value="+oid+">"+oname+"</option>");
                                                    while(rs.next())
                                                    {
                                                    
                                                   // out.println("<option value="+rs.getString("OFFICE_NAME")+"</option>");
                                                        out.println("<option value="+rs.getString("OFFICE_NAME")+">"+rs.getString("OFFICE_NAME")+"</option>");  
                                                }
                                               
                                            } 
                                            catch(Exception e)
                                            {
                                            System.out.println("Exception in Office combo..."+e);
                                            }
                                                
                                            %>
                                          </SELECT>
                                         </div>
                                      </td>
                                     </TR>
                                     </table>
                                     </div>
                                     <div align="left" id="div_others" style="display:none">
                                     <table cellspacing="1" cellpadding="2" border="1" width="100%">
                                     
                                 <TR class="table">   
                                 <TD >
                                  <div align="left">
                                 Transferred Office Name<font color="#ff2121">*</font>
                                 </div>
                                </TD>  
                                     <td>
                                     <div align="left">
                                          <input type="text" name="txtOffice_Name" id="txtOffice_Name"  >
                                      </div>
                                          
                                      </td>
                                  </TR>
                                  </table>
                                  </div>
                               <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
                        <tr class="table">
                                      <td>
                                       <div align="left">
                                        Date of Transfer  <font color="#ff2121">*</font>
                                        </div>
          		                        </td>
                                      <TD>
                                      <div align="left">
                    <input type="text" name="txt_date" id="txt_date"  tabindex="7"
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAMC_Lend_Details.txt_date,1);"
                         alt="Show Calendar"></img>                     
                  </div>
                              </TD>
                                  </TR>
              
                                  
              <tr class="table">
                <td>
                  <div align="left">Reason for Transfer</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="13" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              </table>
       		<table cellspacing="1" cellpadding="2" border="1" width="100%">
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()" tabindex="60"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()" tabindex="70"/>
                     </td>
                 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>
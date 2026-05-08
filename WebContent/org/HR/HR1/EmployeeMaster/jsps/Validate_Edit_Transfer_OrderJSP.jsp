<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
  
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
      <script type="text/javascript"       src="../scripts/CalendarControl.js"></script>
      <script type="text/javascript" src="../scripts/Validate_Transfer_OrderJS.js"></script>
      <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
      
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <meta http-equiv="cache-control" content="no-cache">
      <title>Validate Transfer Order Details</title>       
          
          <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
             <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
          <link href='../../../../../css/RWS_CSSColour.css' rel='stylesheet' media='screen'/>
          <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
          
          <style type="text/css">
                .divClass{display: none;  }                                
          </style>
    </head>
 <body class="bodyid"> 
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
      
   // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
 <%
          
          PreparedStatement ps=null;
          ResultSet rs1=null;
          
          PreparedStatement ps3=null;
          ResultSet rs3=null;
 
          HttpSession session=request.getSession(false);
          UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
          
          System.out.println("user id::"+empProfile.getEmployeeId());
          int empid=empProfile.getEmployeeId();
          int  oid=0;
          String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="";
          String olname=""; 
          String ownature="";
          
           try
          {
           
            ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            rs1=ps.executeQuery();
                 if(rs1.next()) 
                 {
                    oid=rs1.getInt("OFFICE_ID");
                 
                 }
            rs1.close();
            ps.close();
            ps=connection.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE where OFFICE_ID=?" );
            ps.setInt(1,oid);
            rs1=ps.executeQuery();
                 if(rs1.next()) 
                 {
                    oname=rs1.getString("OFFICE_NAME");
                    oadd1=rs1.getString("OFFICE_ADDRESS1");
                    oadd2=rs1.getString("OFFICE_ADDRESS2");
                    ocity=rs1.getString("CITY_TOWN_NAME");
                    odist=rs1.getString("DISTRICT_NAME");
                    
                  }
            rs1.close();
            ps.close();
       
                 
           }
           catch(Exception e)
           {
             System.out.println(e);
           }
  
  %>
        <table  cellspacing="1" cellpadding="3" width="100%" class="table">
                    <tr>
                        <td class="tdH">
                            <center>                           
                            <h3>Validate Transfer Order Details</h3>                            
                            </center>
                        </td>
                    </tr>                    
              </table>
        <form name="frmTransfer" id="frmTransfer" method="POST" onsubmit="return checkDate()" action="../../../../../Validate_Transfer_OrderServ?Command=Edit">                
                <div class="tab-pane" id="tab-pane-1" >
                    <div class="tab-page">
                        <h2 class="tab">Transfer Details</h2>
                        <div align="center">
                            <table  cellspacing="3" cellpadding="1"  width="100%">
                                  
                                  <tr class="tdTitle">
                                    <td colspan="2">
                                     <div align="left">
                                       <strong>Office Details</strong>
                                     </div>
                                    </td>
                                   </tr>
                                   
                                   <tr class="table">
                                     <td>
                                       <div align="left">
                                             Office ID 
                                        </div>
                                       </td>
                                     <td>
                                      <div align="left">
                                     <input type="text" name="txtOffId" id="txtOffId" maxlength="4" value="<%=oid%>"
                                           size="5" class="disab"  readonly="readonly"/>
                                   </div>
                                   </td>
                                   </tr>
                                  
                                  
                                  <tr class="table">
                                   <td>
                                     <div align="left">Office Name</div>
                                   </td>
                                   <td>
                                 <div align="left">
                                  <input type="text" name="txtOffName" id="txtOffName" value="<%=oname%>"
                        maxlength="60" size="60"
                       readonly="readonly" class="disab"/>
                              </div>
                            </td>
                                </tr>
                                
                                <tr class="table">
            <td>
              <div align="left">Office Address</div>
            </td>
            <td>
              <div align="left">
                <textarea rows="4" cols="40"  name="txtOffAddr" id="txtOffAddr" readonly="readonly"
                class="disab"><%
                String s=null;
                if(oadd1!=null)
                {
                    s=oadd1;
                }
                if(oadd2!=null)
                {
                    s+="\n"+oadd2;
                }
                if(ocity!=null)
                {
                    s+="\n"+ocity;
                }
                if(odist!=null)
                {
                    s+="\n"+odist;
                }
                if(s!=null)
                    out.print(s);   
                                
                %></textarea>
             
              </div>
            </td>
          </tr>
          
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Employee Proceeding Details</strong>
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">Proceeding Id
              <font color="#ff2121">*</font></div>
            </td>
            <td>
              <div align="left">
                <!--<input type="text" name="txtPid" id="txtPid"  onkeypress="return numbersonly1(event,this);"
                        maxlength="6" size="12" onchange="clearDat();doFunction('getDet','null')"/>-->  
                     
              <select name="txtPid" id="txtPid" onchange="clearDat();doFunction('getDet','null')">
                <option value="0">---Select Proceeding Id---</option>
                <%
                   
                 PreparedStatement ps1=null;
                 ResultSet rs2=null;
                 try
                 {
                 ps1=connection.prepareStatement("select distinct transfer_order_id from HRM_TRANSFER_ORDERS where TRANSFER_ISSUE_OFFICE_ID=? and PROCESS_FLOW_STATUS_ID in ('CR','MD') order by transfer_order_id desc");
                 ps1.setInt(1,oid);
                 
                 rs2=ps1.executeQuery();
                 
                 while(rs2.next())
                 {
                    out.println("<option value='"+rs2.getInt("transfer_order_id")+"'>"+rs2.getInt("transfer_order_id")+"</option>");
                 }
                 }
                 catch(Exception e)
                 {
                   System.out.println(e.getMessage());
                 }
                 finally
                 {
                    ps1.close();
                    rs2.close();
                 }
                
                %>
                </select>          
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">Proceeding Reference No.</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtRno" id="txtRno" onkeypress="return noEnter(event)"
                        maxlength="50" size="50" readonly="readonly"/>                       
              </div>
            </td>
          </tr>
          
          <tr class="table">
           <td>
              <div align="left">
                Proceeding Date
                
              </div>
            </td>
          <td>
              <div align="left">
               <input type="text" name="txtPDat" id="txtPDat"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3';  "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);" />
                 
                <img src="../../../../../images/calendr3.gif"
                     onclick="showCalendarControl(document.frmTransfer.txtPDat);"
                     alt="Show Calendar"></img>
                     </div>
             </td>
            </tr>
            
            <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Employee and Transfer Details</strong>
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Employee ID 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmployeeid" id="txtEmployeeid"
                       maxlength="5" size="6"
                       onkeypress="return numbersonly1(event,this);"
                       onchange="doFunction('loademp','null');" readonly="readonly"/>
                 
                <!--<img src="../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img>-->
              </div>
            <input type="hidden" name="birthDate" id="birthDate"></input>
            </td>           
          </tr>
          
           <tr class="table">
            <td>
              <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Employee Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpName" id="txtEmpName"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpDesig" id="txtEmpDesig"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current Posting</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtCurOff" id="txtCurOff"
                       readonly="readonly" class="disab" maxlength="60"
                       size="60"/>
              </div>
            </td>
          </tr>
          
          <!--<tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Transfer Details</strong>
              </div>
            </td>
          </tr>-->
          
          <tr class="table">
            <td>
              <div align="left">
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office
               Id to which posted 
               <font color="#ff2121">*</font>
               </div>
             </td>
             <td>
              <div align="left">
                <input type="text" name="txtT_OffId" id="txtT_OffId"
                 onkeypress="return numbersonly1(event,this);" onchange="doFunction('office',this.value);"
                  maxlength="4" size="5" readonly="readonly"/>
                        
                <!--<img src="../../../../../images/c-lovi.gif" width="20"
                height="20" alt="OfficeList" onclick="jobpopup_trn();"></img>-->
               </div>
             </td>
             </tr>
             
             <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office Name</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtT_OffName" id="txtT_OffName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                 </tr>
                 
                 <tr class="table">
                 <td>
                 <div align="left">
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reason Id
                 <font color="#ff2121">*</font>
                 </div>
                 </td>
                 <td>
                 <div align="left">
                 <!--<select name="cmbreasid" id="cmbreasid" onchange="getreason()">
                 <option value="0">---Select Reason---</option>
                 <option value="Transfer">Transfer</option>
                 <option value="Promotion">Promotion</option>
                 <option value="Redesignation">ReDesignation</option>
                 </select>-->
                 
                 <select name="cmbreasid" id="cmbreasid" onchange="getPost_reas()">
                  <option value="0">---Select Posting Reason---</option>
                  
                  <%
                    ps3=connection.prepareStatement("select posting_reason_id, posting_reason_desc from hrm_mst_posting_reason order by posting_reason_id");
                    
                    rs3=ps3.executeQuery();
                    
                    while(rs3.next())
                    {
                      out.println("<option value="+rs3.getString("posting_reason_id")+">"+rs3.getString("posting_reason_desc")+"</option>");
                    }
                  %>
                 </div>
                 </td>
                 </tr>
                 
                 <tr class="table" id="cmbdesig">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;New
                        Designation Id to be Joined 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    
                    <td>
                      <div align="left">
                    <select name="cmbsgroup" id="cmbsgroup"  onchange="getDesignation()" readonly="readonly">
                <option value="0">Select Service Group</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs.close();
                ps.close();
          
          }    
                
        %>              
               </select> 
                    
                      <select name="cmbDesignation" id="cmbDesignation"
                              onclick="return checkGroup();" readonly="readonly">
                        <option value="0"></option>
                        
                      </select>
                    </div>               
                     
                    </td>
                  </tr>
                  
                  <!--<tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Designation Name</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtP_DesigName" id="txtP_DesigName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                 </tr>-->
                 
                 <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Further
                        Reposting Required 
                        
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="radio" name="radT_Repost" id="radT_Repost"
                               value="Y"/>YES &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input type="radio" name="radT_Repost" id="radT_Repost"
                               value="N" checked="checked"/>NO
                      </div>
                    </td>
                  </tr>
                  
                  <tr class="table">
                  <td>
                  <div align="left">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TA/DA Eligibility&nbsp;?
                  </div>
                  </td>
                  <td>
                  <div align="left">
                  <input type="radio" name="radTransfer" id="radTransfer"
                                value="Y">YES &nbsp;&nbsp;&nbsp;&nbsp;
                   <input type="radio" name="radTransfer"   id="radTransfer"   
                                value="N" checked="checked">NO &nbsp;&nbsp;&nbsp;&nbsp;
                                </div>
                  </td>
                  </tr>
                  
                  

                  
                  
                  <tr class="table">
                  <td colspan="2">
                  <div align="center">
                   <!--<input type="button" name="btadd" id="btadd" value="Add" />
                   <input type="button" name="btupdate" id="btupdate" value="Update" onclick="doFunction('Update','null');"/>
                   <input type="button" name="btdelete" id="btdelete" value="Delete" onclick="doFunction('Delete','null');"/>
                   <input type="button" name="btclear" id="btclear" value="Clear" onclick="clData();"/>-->             
                  </div>
                  </td>
                  </tr>
                  
                   <tr>
                  <td colspan="2" height="56">
                  <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                  <tr class="table">
                        
                        <th>Employee Id</th>
                        <th>Employee Name</th>
                        <th>New Designation</th>
                        <th>Transfer Office Name</th>
                        <th>Reason</th>
                        <th>Further reposting Required</th>
                        <th>TA/DA Eligibility</th>
                       
                        
                      </tr>    
                      <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                  </table>
                  </td>
                              </table>
                        </div>
                        <br>
                    <!--<div id="bghead" class="tdH">
                    <center>  
                        <input type="Submit" name="btsub" id="btsub" value="Validate"/>                   
                       <input type="button" name="btexit" id="btexit" value="Exit" onfocus="javascript:closeWindow();"/>                                                              
                    </center>
                </div>-->
                    </div>
                    
                    <div class="tab-page">
                        <h2 class="tab">Order Details</h2>
                        <div align="center">
                            <table width="100%">
                            
                               <tr class="table">
                         <td>
                           <div align="left">Presiding Officer</div>
                         </td>
                          <td>
                             <div align="left">
                                 Prefix&nbsp;
                                <input type="text" name="txtPref" id="txtPref" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)"
                                   size="10" maxlength="7"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                               <input type="text" name="txtPO" id="txtPO" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)" readonly="readonly"
                                   size="50" maxlength="50"/> 
                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  Suffix&nbsp;(if any)&nbsp;  
                                  <input type="text" name="txtSuf" id="txtSuf" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)"
                                   size="15" maxlength="15" readonly="readonly"/>
                              </div>
                            </td>
                            </tr>
                            
                            <tr class="table">
                         <td>
                           <div align="left">Presiding Officer Designation</div>
                         </td>
                          <td>
                             <div align="left">
                               <input type="text" name="txtPOD" id="txtPOD" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)" readonly="readonly"
                                    size="50" maxlength="50"/>                       
                              </div>
                            </td>
                            </tr>
                            
                            <tr class="table">
                           <td>
                           <div align="left">Subject</div>
                           </td>
                            <td>
                             <div align="left">
                               <textarea cols="50" rows="2" id="txtSub" name="txtSub" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)" readonly="readonly"></textarea>                      
                              </div>
                            </td>
                            </tr>
                            
                             <tr class="table">
                           <td>
                           <div align="left">Reference</div>
                           </td>
                            <td>
                             <div align="left">
                               <textarea cols="50" rows="2" id="txtRef" name="txtRef" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)" readonly="readonly"></textarea>                       
                              </div>
                            </td>
                            </tr>
                            
                            <tr class="table">
                           <td>
                           <div align="left">Additional Para 1</div>
                           </td>
                            <td>
                             <div align="left">
                               <textarea cols="50" rows="4" id="txtAdd1" name="txtAdd1" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)" readonly="readonly"></textarea>                       
                              </div>
                            </td>
                            </tr>
                            
                            <tr class="table">
                           <td>
                           <div align="left">Additional Para 2</div>
                           </td>
                            <td>
                             <div align="left">
                               <textarea cols="50" rows="4" id="txtAdd2" name="txtAdd2" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)" readonly="readonly" ></textarea>                       
                              </div>
                            </td>
                            </tr>
                            
                            <tr class="table">
                           <td>
                           <div align="left">Copy To</div>
                           </td>
                            <td>
                             <div align="left">
                               <textarea cols="40" rows="5" id="txtcopy" name="txtcopy" title="Don't type '&' Character while entering data" onkeypress="return noEnter(event)" readonly="readonly" ></textarea>                       
                              </div>
                            </td>
                            </tr>
                            
                            <!--<tr class="table">
                           <td>
                           <div align="left">Whether Signed By Presiding Officer ?</div>
                           </td>
                            <td>
                             <div align="left">
                               <input type="radio" name="rad_sig" id="rad_sig" value="Y" checked="checked">Yes&nbsp;&nbsp;&nbsp;&nbsp;
                               <input type="radio" name="rad_sig" id="rad_sig" value="N">No&nbsp;&nbsp;&nbsp;&nbsp;
                              </div>
                            </td>
                            </tr>-->
                            
                            <tr class="table">
                  <td>
                  <div align="left">
                    Proceeding to individually addressed
                  </div>
                  </td>
                  <td>
                  <div align="left">
                  <input type="radio" name="rad_indi" id="rad_indi"
                            value="Y" >Yes&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="radio" name="rad_indi"  id="rad_indi" 
                            value="N" checked="checked">No&nbsp;&nbsp;&nbsp;&nbsp;
                            </div>
                  </td>
                  </tr>
                                  
                                </table>
                        </div>
                    </div>
                </div>
                <br>
                <%
                statement.close();
                connection.close();
                %>
               <div id="bghead" class="tdH">
                    <center>  
                        <input type="Submit" name="btsub" id="btsub" value="Validate" disabled="disabled"/>                   
                       <input type="button" name="btexit" id="btexit" value="Exit" onfocus="javascript:closeWindow();"/>                                                              
                    </center>
                </div>                        
                
        </form>
  </body>
</html>


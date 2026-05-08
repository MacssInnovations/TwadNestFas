<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
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
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
   <title> Deputation Report</title>
   <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
 <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript"
            src="../scripts/DeputationReportNewJS.js"></script>
    
          
           <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
   
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
      <!--div.scroll {	
      height: 100px;	
      width: 100%;	
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
     visibility: hidden;
     position: relative;
      }-->
      
    </style>
    
    
  </head>
  <body>
  <form name="frmDeputationRep" method="POST" action="">
  
            <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            <input type="hidden" name="designationlevel">
            <input type="hidden" name="designation">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
            
  
      <div align="center">
        <table border='2' width="100%">
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
                <tr>
                  <td colspan="2" class="tdH" align="center"><strong> Deputation Detailed Report</strong></td>
                </tr>
                <tr>
                  <td colspan="2" class="tdH">Select Office Level</td>
                </tr>
                <tr>
                  <td class="table" align="left">
                                            
                <%
                  HttpSession session=request.getSession(false);
                  UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                      
                    System.out.println("user id::"+empProfile.getEmployeeId());
                    int empid=empProfile.getEmployeeId();
                    int oid=0;
                    String deptid="",offlevel="";
                    
                         try
                        {
                               
                                ps=connection.prepareStatement("select b.OFFICE_LEVEL_ID from hrm_emp_current_posting a "+
                                        " inner join com_mst_offices b on b.office_id=a.office_id "+
                                        " where a.employee_id=?" );
                                ps.setInt(1,empid);
                                results=ps.executeQuery();
                                     if(results.next()) 
                                     {
                                        offlevel=results.getString("OFFICE_LEVEL_ID");
                                        
                                     }
                                results.close();
                                ps.close();
                                System.out.println("office id:"+oid);
                                System.out.println("dept id:"+deptid);
                                
                                }
                                 catch(Exception e)
                        {
                            System.out.println(e);
                        }
                        
                        
   
                    
                    %>
                         
                            <%   
                            String  profile=(String)session.getAttribute("profile");%>
                            <%if(offlevel.equalsIgnoreCase("HO") || profile.equalsIgnoreCase("other")){System.out.println("yes");%>
                     <div id="divallofficelevel" onclick="hideofficelevel()"
                         style="cursor:hand">
                     <input type="radio" name="optofficelevel"
                             checked="checked"></input>All Levels
                    </div>
                    </td>
                  <td class="table" align="left">
                    <div id="divspecificofficelevel" onclick="showofficelevel()"
                         style="cursor:hand">
                    <input type="radio" name="optofficelevel"></input>Specific
                                                                        Levels
                    </div>
                  </td>
                </tr>
                    
                             
                    <%}else{ System.out.println("no");%>
                              <div id="divallofficelevel" >
                               <input type="radio" name="optofficelevel"
                              onclick="document.frmVacantRep.optofficelevel[1].checked=true;"></input>All Levels
                           </div>  
                           </td>
                  <td class="table" align="left">
                    <div id="divspecificofficelevel" onclick="showofficelevel()"
                         style="cursor:hand">
                    <input checked type="radio" name="optofficelevel"></input>Specific
                                                                        Levels
                    </div>
                    <script language="javascript">
                           document.frmVacantRep.optofficelevel[1].checked=true;
                           showofficelevel();
                           </script>
                  </td>
                </tr>
                           
                           
        <%}%>
                             
                    
                  
                
                  <tr id="trofficelevel" style="display:none">
                  <td class="table">
                    <div align="left">Select The Office Level Upto</div>
                  </td>
                  <td class="table">
                    <div align="left">
                    
               
                    
                      <select name="cmbolevel" id="cmbolevel"
                              onchange="getLevel()">
                              
                         <%      profile=(String)session.getAttribute("profile");%>
                             <%if(offlevel.equalsIgnoreCase("HO") || profile.equalsIgnoreCase("other")){%>
                       
                        <option value="">Select Office Level</option>
                        <option value='HO'>Head Office</option>
                        <option value='RN'>Region</option>
                        <option value='CL'>Circle</option>
                        <option value='DN'>Division</option>
                        <%}
                        else if(offlevel.equalsIgnoreCase("RN")){%>
                         <option value="">Select Office Level</option>
                        <option value='RN'>Region</option>
                        <option value='CL'>Circle</option>
                        <option value='DN'>Division</option>
                        <%}
                        else if(offlevel.equalsIgnoreCase("CL")){%>
                       <option value="">Select Office Level</option>
                       <option value='CL'>Circle</option>
                        <option value='DN'>Division</option>
                        <%}
                        else if(offlevel.equalsIgnoreCase("DN")){%>
                         <option value="">Select Office Level</option>
                       <option value='DN'>Division</option>
                       <%}%>
                        
                        
                      </select>
                    </div>
                    
                     <div id="aggreate" style="display:none">
                    <input type=checkbox name="aggid" id=aggid value="Y" checked>Aggregate Subdivision
                    </div>
                  </td>
                </tr>
                 <tr id="troffice" style="display:none">
                  <td class="table" align="left">
                    <div id="divalloffice" onclick="hideoffice()"
                         style="cursor:hand">
                      <input type="radio" name="optoffice" checked="checked"></input>All
                                                                                     Offices
                    </div>
                  </td>
                  <td class="table" align="left">
                    <div id="divspecificoffice" onclick="showoffice()"
                         style="cursor:hand">
                      <input type="radio" name="optoffice"></input>Specific Offices
                                                                   
                    </div>
                  </td>
                </tr>
                <tr class="table" id="trofficeselection" style="display:none">
                  <td colspan="2">
                    <div align="left">
                      <table border="0" width="100%">
                        <tr>
                          <td>
                            <div id="divregion" style="display:none">Region</div>
                          </td>
                          <td>
                            <div id="divcircle" style="display:none">Circle</div>
                          </td>
                          <td>
                            <div id="divdivision" style="display:none">Division</div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbregion" id="cmbregion"
                                          style="display:none;width:100%"
                                          onclick="getRegion()">
                                    <option>Select the Regions</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframeregion">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbcircle" id="cmbcircle"
                                          style="display:none;width:100%"
                                          onclick="getCircle('null')">
                                    <option>Select the Circle</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframecircle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbdivision" id="cmbdivision"
                                          style="display:none;width:100%"
                                          onclick="getDivision('null')">
                                    <option>Select the Division</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframedivision">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          
          <tr class="table">
          <td>
            <div align="left">
              Deputation Date From<font color="#e70000">&nbsp;</font>(dd/mm/yyyy) 
              <font color="#e70000">*</font>
            </div>
          </td>
          <td>
            <div align="left">
             <input type="text" name="txtDateFrom" id="txtDateFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../../images/calendr3.gif"
                           onclick=" showCalendarControl(document.frmDeputationRep.txtDateFrom);"
                           alt="Show Calendar"></img>
                    
            </div>
          </td>
        </tr>  
        
         <tr class="table">
          <td>
            <div align="left">
              Deputation Date To<font color="#e70000">&nbsp;</font>(dd/mm/yyyy) 
              <font color="#e70000">*</font>
            </div>
          </td>
          <td>
            <div align="left">
             <input type="text" name="txtDateTo" id="txtDateTo"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="toimg" src="../../../../../../images/calendr3.gif"
                           onclick="showCalendarControl(document.frmDeputationRep.txtDateTo);"
                           alt="Show Calendar"></img>
            </div>
          </td>
        </tr>  
          
          
          <tr>
           
                    
             <td colspan="2" align="left" valign="top" class="table">
              <div align="center">
                <table border="0" width="100%">
                  <tr>
                    <td colspan="2" class="tdH">Report Output Format</td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[0].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype" checked></input>PDF
                                                                         Format
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[1].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype"></input>EXCEL
                                                                         Format
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      <div onclick="document.frmEmployeeDetailRep.optoutputtype[2].checked=true;"
                           style="cursor:hand">
                        <input type="radio" name="optoutputtype"></input>HTML
                                                                         Format
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdH" align="center"><input type="button" value="Submit" onclick="frmsubmit()"><input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
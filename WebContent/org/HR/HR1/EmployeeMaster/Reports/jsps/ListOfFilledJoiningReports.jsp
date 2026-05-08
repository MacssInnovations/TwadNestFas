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
    <title> List of Relieval Reports</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
 <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="../scripts/ListOfFilledJoiningReports.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
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
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body><form name="frmValidationSummaryRep" method="POST" 
                       action="../../../../../../ListOfFilledJoiningReports">
  
         
            
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
                  <td colspan='2' class="tdH" align="center"><strong> List Of Filled Joining Reports</strong></td>
                </tr>
            <tr>
                  <td colspan="2" class="tdH">Selection of Period</td>
                </tr>
             <tr>
                <td  width="auto" >From&nbsp;Date:
               <!-- <td  width="auto">-->
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/><img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmValidationSummaryRep.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"/>
                <!--</td>--></td>
                <td  width="auto">To&nbsp;Date:
                <!--<td  width="auto">-->
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/><img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmValidationSummaryRep.txttodate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
                </tr>
            
                <tr>
                  <td colspan="2" class="tdH">Selection of Office Jurisdiction</td>
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
                     <div id="divallofficelevel" 
                         style="cursor:hand">
                     
                             
                             
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                             
                    </div>
                    </td>
                    
                    
                  
                </tr>
                    
                             
                    <%}else{ System.out.println("no");%>
                           
                           
        <%}%>
                  
               
                <tr>
                  <td colspan="2" class="tdH">Selection of Office</td>
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
                            <div id="divofftype" style="display:none">Office Type</div>
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
                                  <select name="cmbofftype" id="cmbofftype"
                                          style="display:none;width:100%"
                                          onclick="getofftype('null')">
                                    <option>Select the Office Type</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframeofftype">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
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
          <tr class=table id="divallhier" style="display:block">
            <td colspan=2 align=left><input type="checkbox" name="allhier" id="allhier" value="include">Include all Offices down the Hierarchy</input></td>
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
            <td colspan="2" class="tdH" align="center"><input type="button" value="Submit" onclick="frmsubmit()">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
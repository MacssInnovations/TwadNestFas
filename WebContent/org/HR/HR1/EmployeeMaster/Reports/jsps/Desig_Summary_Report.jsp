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
           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
    <title> Designation Wise Summary Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Desig_Summary_ReportJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
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
  
  <body><form name="frmValidationSummaryRep" method="POST" 
                       action="../../../../../../Desig_Summary_ReportServ">
  
         
            
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
                  <td colspan='2' class="tdH" align="center"><strong> Designation Wise Summary Report</strong></td>
                </tr>
                
                
           
       
       <tr>
                  <td colspan="2" class="tdH">Selection of Designation</td>
                </tr>
         <tr>
            <td colspan="3" ><input type="radio" name="desType" value="All" id="desType" onclick="HideOfficeType()" checked="checked" >All &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="radio" name="desType" id="desType" value="Specific" onclick="VisibleOfficeType()">Specific	            
        </tr>
        
         <tr class="table" id="divdest" style="display:block;">
                  <td>
                    <div align="left" style="display:none" id="SpecificDes">
                    <select name="cmbsgroup" id="cmbsgroup" onchange="getDesignation()">
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
                System.out.println("service group code:"+strcode);
                System.out.println("service gorup name:"+strname);
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
               </div>
                  </td>
                  
           </tr>
               
                   
                    
                
                
                
        
          <!--<tr class="table" id="divrank" style="display:none">
                  <td colspan='2'>
                    <div align="left"  id='divrankwhole'>
                    <select name="cmbsgroup1" id="cmbsgroup1" onchange="getDesignation1()">
                <option value="0">Select Service Group</option>
                        <%
            rs=null;
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
                   
                    </div>
                  </td>
                </tr>             
                
                
                
                <tr class="table" id="divcadre" style="display:none;">
                  <td colspan='2'>
                    <div align="left">
                    <select name="cmbsgroup2" id="cmbsgroup2" onchange="getDesignation2()">
                <option value="0">Select Service Group</option>
                <%
            rs=null;
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
                    
                    </div>
                  </td>
                </tr>-->
                
                
              <tr class="table" id="desigblock" style="display:none">
               <td>
               <div class="scroll" align='left'  id="divdesignation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               </td>
               </tr>  
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                <!--<tr>
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
                               
                                ps=connection.prepareStatement("select b.OFFICE_LEVEL_ID,b.office_id from hrm_emp_current_posting a "+
                                        " inner join com_mst_offices b on b.office_id=a.office_id "+
                                        " where a.employee_id=?" );
                                ps.setInt(1,empid);
                                results=ps.executeQuery();
                                     if(results.next()) 
                                     {
                                        offlevel=results.getString("OFFICE_LEVEL_ID");
                                        oid=results.getInt("office_id");
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
                          
                      <%  if(offlevel.equalsIgnoreCase("HO") || profile.equalsIgnoreCase("other"))
                       {%>
                     
                     <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                             
                    </div>
                    
                    <% }
                    
                      else if(offlevel.equalsIgnoreCase("RN")) { %>
                      
                      <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                             
                    </div>
                    
                    <%}
                       else if(offlevel.equalsIgnoreCase("CL")) {%>
                       
                      <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                      </div>
   
                    <%}
                        else if(offlevel.equalsIgnoreCase("DN")) {%>
                        
                        <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()" disabled="disabled"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                      </div>
                        
                        <%}
                        
                           else
                           {
                             System.out.println("No Office id");
                           }%>

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
          
          </tr>-->
          <!--<tr class=table id="divallhier" style="display:block">
            <td colspan=2 align=left><input type="checkbox" name="allhier" id="allhier" value="include">Include all Offices down the Hierarchy</input></td>
          </tr>-->
         <tr class="table">
   <td  colspan="2" class="tdH">Employee Status   
   </td>
   </tr>
   <tr>
   <td align="left"><table border="0">
   <tr><td align="left"><input type="checkbox" name="select1" value="WKG" id="worselect">Working </td>
   <td align="left"><input type="checkbox" name="select1" value="TRT" id="traselect">Transit</td>
   <td align="left"><input type="checkbox" name="select1" value="DPN" id="depselect">Deputation
   </tr>
   <tr><td align="left">  <input type="checkbox" name="select1" value="STT" id="worselect">Studies Transit </td>
   <td align="left"><input type="checkbox" name="select1" value="DPT" id="traselect">Deputation Transit </td>
   <td align="left"><input type="checkbox" name="select1" value="SAN" id="depselect">Superannuation</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="SUS" id="worselect">Supspension </td>
   <td align="left"><input type="checkbox" name="select1" value="LLV" id="traselect">Leave</td>
   <td align="left"><input type="checkbox" name="select1" value="STU" id="depselect">Studies</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="UAL" id="worselect">Unauthorised Absent (Absconded)</td>
   <td align="left"><input type="checkbox" name="select1" value="TCL" id="traselect">Transit Cum Leave</td>
   <td align="left"><input type="checkbox" name="select1" value="LOP" id="depselect">Loss Of Pay</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="CMW" id="worselect">Compulsory Wait </td>
   <td align="left"><input type="checkbox" name="select1" value="TRA" id="worselect">Training</td>
   <td align="left"><input type="checkbox" name="select1" value="VRS" id="traselect">Voluntary Retirement</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="DIS" id="depselect">Dismissed</td>
   <td align="left"><input type="checkbox" name="select1" value="DTH" id="worselect">Death </td>
   <td align="left"><input type="checkbox" name="select1" value="ABR" id="traselect">Abroad</td>
   </tr>
   <tr><td align="left"><input type="checkbox" name="select1" value="RES" id="depselect">Resigned</td>
   <td></td>
   <td></td>
   </tr>
   </table>

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
            <td colspan="2" class="tdH" align="center"><input type="button" value="Submit" onclick="frmsubmit()">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
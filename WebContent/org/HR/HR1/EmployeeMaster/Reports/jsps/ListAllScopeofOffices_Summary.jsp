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
    <title> Employee Vacancy Position Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/ListAllScopeofOffices_Summary.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
    .navigation {
  height:100px; width:283px; 
  border-style:1px solid #666;
  border-width:1px; 
  background-color: #fff;
  float:left; 
  overflow: auto;
}

      
       <!-- div.scroll {	
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
  <body ><form   name="frmValidationSummaryRep" method="POST" 
                       action="../../../../../../ListAllScopeofOffices_Summary">
  
         
            
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
                  <td colspan='2' class="tdH" align="center"><strong> Employee Vacancy Position Report</strong></td>
                </tr>
                
                
          <!--tr class="tdH">
          <th colspan="2" align="left">Select Designation / Rank</th>
        </tr-->
        <tr class="table">    
        <!--td >       
              <input type="radio" value="RadAl" name="optselectgrp" id="chkone" checked="checked" onclick="hidedesig()">
               All Designation
         
               <input type="radio" value="radsel" onclick="sellectall()" name="optselectgrp">
               
               Select Specific Designation / Cadre / Rank     -->          
       <!--/td-->   
       <td class="tdH" colspan="2" >
       Selection of Specific Rank
       </td>
       
       </tr>       
       
       <tr class="table" id="divselect" > 
                
                <td> <input type="radio" value="allRank"  onclick="hideSpecRank()"
                             name="optsel" id="optselect1" checked></input>
                      All Rank             
                </td>
                <td> <!--input type="radio" value="Dest"  onclick="selectoption1()"
                             name="optselect" id="optselect1" ></input-->
                      <!-- Designation-->
                      
                      <input type="radio" value="Specific Rank" name="optselect"  onclick="selectoption1()"
                             id="optselect2" ></input>
                       Rank
                       <!--input type="radio" value="Cadr" name="optselect"  onclick="selectoption1()"
                             id="optselect3" ></input-->
                      <!-- Cadre-->
                </td>
               
        </tr>
        
         <tr class="table" id="divdest" style="display:none;">
                  <td>
                    <div align="left">
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
            
                
        %>
               
               </select> 
               </div>
                  </td>
                  
           </tr>                                              
                              
                
        
          <tr class="table" id="divrank" style="display:none">
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
           
                
        %>
               
               </select> 
                    
                    </div>
                  </td>
                </tr>
              
                
              <tr class="table" id="desigblock" style="display:none">
               <td>
               <div class="navigation" align='left' id="divdesignation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               <!--p class="scroll" align='left' id="divdesignation">&nbsp;</p-->
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
                    System.out.println("Employee_ id is "+empid);
                    int oid=0;
                    String deptid="",offlevel="";
                    
                         try
                        {
                         System.out.println("first");      
                                ps=connection.prepareStatement("select b.OFFICE_LEVEL_ID,b.office_id from hrm_emp_current_posting a "+
                                                               " inner join com_mst_offices b on b.office_id=a.office_id "+
                                                               " where a.employee_id=?" );
                                ps.setInt(1,empid);
                        System.out.println("seccond");                                      
                                results=ps.executeQuery();
                                     if(results.next()) 
                                     {
                                        offlevel=results.getString("OFFICE_LEVEL_ID");
                                        oid=results.getInt("office_id");
                                        System.out.println("office id:"+oid);
                                System.out.println("level id:"+offlevel);
                                     }
                                     //updated on 30 August
                                     else
                                     {
                                     offlevel="HO";
                                      oid=5000;
                                     }
                         System.out.println("third");                  
                                results.close();
                                ps.close();
                                
                                
                                }
                                 catch(Exception e)
                        {
                            System.out.println(e);
                        }
                        
                        
   
                    
                    %>
                         
                            <%-- 
                            String  profile=(String)session.getAttribute("profile");--%>
                            <%--if(offlevel.equalsIgnoreCase("HO") || profile.equalsIgnoreCase("other")){System.out.println("yes");--%>
                     
                   
                   <%
                   if(offlevel.equalsIgnoreCase("HO"))
                    {
                   %>
                   
                     <div id="divallofficelevel" onclick="hideofficelevel()"
                         style="cursor:hand">
                             
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()">HO</input>
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()">Region</input>
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()">Circle</input>
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()">Division</input>
                             
                    </div>
                    <%}
                    else if(offlevel.equalsIgnoreCase("RN"))
                    {
                    System.out.println("inside ho");
                    %>
                    <div id="divallofficelevel" onclick="hideofficelevel()"
                         style="cursor:hand">
                             
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()"  disabled="disabled">HO</input>
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()">Region</input>
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()">Circle</input>
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()">Division</input>
                             
                    </div>
                    <%}
                    else if(offlevel.equalsIgnoreCase("CL"))
                    {
                    %>
                    <div id="divallofficelevel" onclick="hideofficelevel()"
                         style="cursor:hand">
                             
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()"  disabled="disabled">HO</input>
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled">Region</input>
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()">Circle</input>
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()">Division</input>
                             
                    </div>
                    <%}
                    else if(offlevel.equalsIgnoreCase("DN"))
                    {
                    %>
                    <div id="divallofficelevel" onclick="hideofficelevel()"
                         style="cursor:hand">
                             
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()"  disabled="disabled">HO</input>
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled">Region</input>
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()" disabled="disabled">Circle</input>
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()">Division</input>
                             
                    </div>
                    <%}
                    else
                      {
                      System.out.println("No office id");
                      }
                    %>
                    
                    </td>
                    
                    
                 
                </tr>
                    
                             
                    <%--}else{ System.out.println("no");--%>
                              <!--div id="divallofficelevel" >
                               <input type="radio" name="optofficelevel"
                              onclick="document.frmVacantRep.optofficelevel[1].checked=true;"></input>All Levels
                           </div>  
                           </td>
                  <td class="table" align="left" >
                    <div id="divspecificofficelevel" onclick="showofficelevel()"
                         style="cursor:hand">
                    <input checked type="radio" name="optofficelevel" ></input>Specific
                                                                        Levels
                    </div>
                    <script language="javascript">
                           document.frmVacantRep.optofficelevel[1].checked=true;
                           showofficelevel();
                           </script>
                  </td>
                </tr-->
                           
                           
        <%--}--%>
                             
                    
                  
                
                  
                
                <tr class="table" id="selectofficediv" style="display:none">
                        <td class="tdH" colspan="4">
                            Selection of Offices
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
          
          </tr>
          <!--tr class=table >
            <td colspan=2 align=left><input type="checkbox" name="includeho" id="includeho" value="checked">Include HO</input></td>
          </tr-->
          <!--tr class='table' id="divincludeho">
            <td colspan=2 align=left><input type="checkbox" name="includeho" id="includeho" value="checked">Include HO</input></td>
          </tr-->
          <tr class='table' id="divallhier" style="display:none">
            <td colspan=2 align=left><input type="checkbox" name="allhier" id="allhier" value="checked">Include Hierarchy</input></td>
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
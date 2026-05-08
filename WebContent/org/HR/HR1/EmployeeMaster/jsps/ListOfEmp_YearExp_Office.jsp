<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>List of Employees Years Completion(Seniority Based)</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/ListOfEmp_YearExp_Office.js"></script>
    
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
    
    
  </head>
  <%
  
  Connection connection=null;
  PreparedStatement ps=null;
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
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
  
  <body>
  <form name="frmlist_service" id="frmlist_service" action="../../../../../../ListOfEmp_YearExp_Office" method="post">
      <input type="hidden" name="officeselected" id="officeselected">
      
        <input type="hidden" name="optselect1" id="optselect1">
          <input type="hidden" name="cmbCadre1" id="cmbCadre1">
             <input type="hidden" name="cmbDesignation1" id="cmbDesignation1">
      
      
  <!--action="ListofEmp_YearExp_Serv" method="post"-->
  <table cellpadding="3" cellspacing="2" border="1" width="100%"> 
  
  <tr class="tdH">
  <th colspan="3" align="center">List of Employees Years Completion(Seniority Based)-Office Wise</th>
  </tr>
  
  
   <tr style="display:none">
            <td colspan="3" ><input type="radio" name="OffType" id="OffType" onclick="VisibleOfficeType() " >All Office &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="radio" name="OffType" id="OffType" onclick="VisibleOfficeType()"  checked="checked">Specific Office	

            
        </tr>
        
      <tr>
            <td colspan="3">
                <div id="SpecificOffType" >
                        <table border='1' width="100%">
                            <tr>
                                      <td  colspan="3" class="tdH">Selection of Office Jurisdiction</td>
                                    </tr>
                                    <tr>
                                      <td  colspan="3" class="table" align="left">
                                                                
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
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                    </div>
                                        
                                        <% }
                                        
                                          else if(offlevel.equalsIgnoreCase("RN")) { %>
                                          
                                          <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                    </div>
                                        
                                        <%}
                                           else if(offlevel.equalsIgnoreCase("CL")) {%>
                                           
                                          <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                      </div>
                       
                                        <%}
                                            else if(offlevel.equalsIgnoreCase("DN")) {%>
                                            
                                            <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()" disabled="disabled"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                      </div>
                                            
                                            <%}
                                            
                                               else
                                               {
                                                 System.out.println("No Office id");
                                               }%>                    </td>
                                    </tr>
                                        
                                    
                                     
                                    <tr>
                                      <td colspan="4" class="tdH">Selection of Office</td>
                                    </tr>
                                    <tr class="table" id="trofficeselection" style="display:none">
                                      <td colspan="4">
                                        <div align="left">
                                          <table border="0" width="100%">
                                            <tr>
                                              <td>
                                                <div id="divregion" style="display:none">Region</div>                          </td>
                                              <td>
                                                <div id="divcircle" style="display:none">Circle</div>                          </td>
                                              <td>
                                                <div id="divofftype" style="display:none">Office Type</div>                          </td>
                                              <td>
                                                <div id="divdivision" style="display:none">Division</div>                          </td>
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
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframeregion">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              <td>
                                                <table border="0" cellpadding="0" cellspacing="0"
                                                       width="100%">
                                                  <tr>
                                                    <td>
                                                      <select name="cmbcircle" id="cmbcircle"
                                                              style="display:none;width:100%"
                                                              onclick="getCircle('null')">
                                                        <option>Select the Circle</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframecircle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              
                                                <td>
                                                <table border="0" cellpadding="0" cellspacing="0"
                                                       width="100%">
                                                  <tr>
                                                    <td>
                                                      <select name="cmbofftype" id="cmbofftype"
                                                              style="display:none;width:100%"
                                                              onclick="getofftype('null')">
                                                        <option>Select the Office Type</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframeofftype">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              
                                              <td>
                                                <table border="0" cellpadding="0" cellspacing="0"
                                                       width="100%">
                                                  <tr>
                                                    <td>
                                                      <select name="cmbdivision" id="cmbdivision"
                                                              style="display:none;width:100%"
                                                              onclick="getDivision('null')">
                                                        <option>Select the Division</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframedivision">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                            </tr>
                                          </table>
                                        </div>                  </td>
                                    </tr>
                                </table>
                         </div>                        
                    </td>
                </tr>
           
           
  
  
  
  
  
  
  
  <tr class="table">
   <td colspan="2" align="left">
     <input type="radio" name="optselect" value="Desig" id="optselect1" onclick="selectoption1()" checked="checked">Designation
     <input type="radio" name="optselect" value="Cadre" id="optselect2" onclick="selectoption1()">Cadre
   </td>
   </tr>
   
   <tr class="table" id="divdest">
   <td colspan="2">
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
       
       out.println("<option value='"+strcode+"'>"+strname+"</option>");
     }
     }
     catch(Exception e)
     {
       System.out.println(e.getMessage());
     }
     finally
     {
       rs.close();
       ps.close();
     }
   %>
   </select>
   
   <select name="cmbDesignation" id="cmbDesignation" onclick="return checkgroup1();" onchange="frmrolesubmit()">
   <option value="0"></option>
   </select>
   </div>
   </td>
   </tr>
   
   
   <tr class="table" id="divcadre" style="display:none">
   <td colspan="2">
   <div align="left">
   <select name="cmbsgroup1" id="cmbsgroup1" onclick="getCadre()">
   <option value="0">Select Service Group</option>
   <%
      ResultSet rs1=null;
      
       try
       {
          ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
          rs1=ps.executeQuery();
          
          while(rs1.next())
          {
             int strcode=0;
             String strname="";
             
             strcode=rs1.getInt("SERVICE_GROUP_ID");
             strname=rs1.getString("SERVICE_GROUP_NAME");
             
             out.println("<option value='"+strcode+"'>"+strname+"</option>");
          }
       }
       catch(Exception e)
       {
         System.out.println(e.getMessage());
       }
       finally
       {
         rs1.close();
         ps.close();
       }
   
   %>
   </select>
   
   <select name="cmbCadre" id="cmbCadre" onclick="return checkgroup2();" onchange="frmrolesubmit()">
   <option value="0"></option>
   </select>
   
   </div>
   </td>
   </tr>
   
   <tr class="table">
   <td align="left">Report&nbsp;Type   
   </td>
   <td>
   <select name="cmbReportType">
   <option value="PDF" selected="selected">PDF</option>
   <option value="EXCEL">EXCEL</option>
   <option value="HTML">HTML</option>
   </select>
   </tr>
   
   <tr class="tdH">
   <td colspan="2">
     <div align="center">
       <input type="submit" name="submit" value="Submit"  />
       <input type="reset" value="Clear"/>
       <input type="button" value="Exit" onclick="self.close()"/>       
     </div>
   </td>
   </tr>
  
  </table>
  </form>
  </body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Survey Report</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <!--script type="text/javascript" src="../scripts/Asset_Rendering.js"></script-->
    <script type="text/javascript"     src="../scripts/AddAdditionalCharge_New.js">     </script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    
    <script type="text/javascript" src="../scripts/Asset_Survey_Report.js"></script>
    
 
    <!--script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script-->
    <!--script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script-->
            
    <!--script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script-->
    
    
    <script language="javascript" type="text/javascript">
				function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body class="table">
  <form action="" name="frmsurvey_report" id='frmsurvey_report' method="get" ><input
	type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='filter' id='filter' value='no' />

  <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="3" class="tdH" align="center">Survey Report</td>
                   
       </tr> 
         <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null,rsbank=null;
  PreparedStatement ps=null,ps2=null,psbank=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
    //int empid=10099;
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
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
      
            <!--table cellspacing="1" cellpadding="2" border="1" width="100%"-->
             
               <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                    
                          <%
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
                             
                            String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                            ps=con.prepareStatement(getWing);
                            ps.setInt(1,oid);
                            ps.setInt(2,empid);
                            ps.setInt(3,oid);
                            rs=ps.executeQuery();
                          
                              if(rs.next())
                              {
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                              unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                              
                              System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                              System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                              System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                              
                              }
                          System.out.println(oid+" "+oname);
                          ps.close();
                          rs.close();
                          }
                              else
                              {
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                ps.setInt(1,oid);
                                rs=ps.executeQuery();
                                  if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  }
                                  ps.close();
                                  rs.close();
                              }
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
                      </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >
                      
                      <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
                try
                {
                   if(oid==5000)
                    {
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"</option>");
                    }
                    else
                    {
                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
                        ps.setInt(1,unitid);
                        rs=ps.executeQuery();
                       
                        
                        int countoffice=0;      
                        while(rs.next())
                        {
                            ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                            ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                            rs2=ps2.executeQuery();
                            if(rs2.next())
                            {
                              out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");                           
                            }
                        }
                    }
                    
                } 
                catch(Exception e)
                {
                System.out.println("Exception in Office combo..."+e);
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
      <tr align="left">
              <td class="table">
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" onchange="callGridItems()" >
                    <option value="">--Select Year--</option>
                    <%
                        Statement st=con.createStatement();
                        rs=st.executeQuery("select financial_year from cash_book_control");
                        while(rs.next())
                        {
                            out.println("<option value='"+rs.getString("financial_year").substring(0,4)+"-"+rs.getString("financial_year").substring(7,9)+"'>"+rs.getString("financial_year")+"</option>");
                        }
                    %>
                    </select>
              </td>
              </tr>
        
         <tr class="table">
            <td>
              <div align="left">
                Survey&nbsp;Report&nbsp;No 
                <font color="#ff2121">*</font>
              </div>
            </td>
                      
          <td class="table">
            <input type="text" name="txtsurveyno" maxlength="2"
                   id="txtsurveyno" readonly size="3"/>System Generated
           </td>
           
       
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">
                Survey&nbsp;Report&nbsp;Date 
                <font color="#ff2121">*</font>
              </div>
            </td>
             <td class="table">
            <input type="text" name="txtsurveydate" id="txtsurveydate" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10 size="10">
            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.getElementById('txtsurveydate'),1);" alt="Show Calendar"
                                 height="24" width="19"></img>
            </td>
            
          </tr>
           <tr class="table">
                  <td>
                    <div align="left">
              Survey Done By
            </div>
                  </td>
                  <td>
                    
                      <input type="text" name="txtEmployeeid" id="txtEmployeeid"
                             maxlength="5" size="5"
                             onchange="doFunction('loademp','null');"
                             onkeypress="return numbersonly1(event,this);"/>
                             <%
                            // HttpSession session=request.getSession(false);
                             if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                             {
                             %>
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">
                             <%}else{%>
                             
                              <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopupSR();">
                             <%}%>
                             <!--<img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">-->
                   
                    
              
                <input type="text" name="txtEmployee" id="txtEmployee"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
              
            
                  </td>
                   
                
                </tr>
  
      
     <tr class="table">
            <td>
              <div align="left">Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="cmbDesignation" id="cmbDesignation"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
                       <input type="text" name="Desig_Id" id="Desig_Id" style="Visibility:hidden"/>
              </div>
              
            </td>
          </tr>
        
                       
                        <tr class="table">
                          <td>
                            <font>Present location of Posting</font>
                          </td>
                          <td>
                           <input type="text" name="txtOffice_Id" id="txtOffice_Id"
                       readonly="readonly"  maxlength="6"
                        size="10"/>
                         <input type="text" name="txtOffice_Name" id="txtOffice_Name"
                       readonly="readonly"  maxlength="40"
                        size="40"/>
              
                            
                          </td>
                        </tr>
                       <tr class="table">
                            <td >
                  <div align="left">
                    Approval Date
                    <font color="#ff2121"> * </font>
                   </div>
                </td>
                      <td>     
                    <input type="text" name="txtappdate" id="txtappdate" 
                           maxlength="10" size="11"    
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="Check_Date(this.value);check(this);return checkdt(this)"
                           />
                    </td> 
              
              </tr>       
                        
             <tr class="table">
                  <td>
                    <div align="left">
              Approved By
            </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtEmployeeid2" id="txtEmployeeid2"
                             maxlength="5" size="5"
                             onchange="doFunction('loadempview','null');"
                             onkeypress="return numbersonly1(event,this);"/>
                             <%
                            // HttpSession session=request.getSession(false);
                             if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                             {
                             %>
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">
                             <%}else{%>
                             
                              <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopupSR();">
                             <%}%>
                             <!--<img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">-->
                           
              
                <input type="text" name="txtEmployee2" id="txtEmployee2"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
          
      
                    </div>
                  </td>
                </tr>
  
       
     <tr class="table">
            <td>
              <div align="left">Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="cmbDesignation2" id="cmbDesignation2"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
				<input type="text" name="Desig_Id2" id="Desig_Id2" style="Visibility:hidden" />
             </div>
            </td>
          </tr>
                        <tr class="table">
                          <td>
                            <font>Present location of Posting</font>
                          </td>
                          <td>
                           <input type="text" name="txtOffice_Id2"                                          
                                         id="txtOffice_Id2" 
                                       
                                        
                                         maxlength="4"></input>
                        
                         
                            <input type="text" name="txtOffice_Name2"
                                   id="txtOffice_Name2"
                                   style="background-color: #FFFFFF"
                                    size="30"></input>
                          </td>
                        </tr>
                      
                        
          <tr class="table">
                 
                <td >
                  <div align="left">
                    Reference No 
                    <font color="#ff2121"> * </font>
                   </div>
                </td>
                <td width="74%">
                    <div align="left">
                    <input type="text" name="txtrefno" maxlength="10"  onkeypress="return numbersonly(event)"   
                           id="txtrefno" />
                           </div>
                           </td>
                           </tr>
                           <tr class="table">
                            <td >
                  <div align="left">
                    Reference Date
                    <font color="#ff2121"> * </font>
                   </div>
                </td>
                      <td>     
                    <input type="text" name="txtrefdate" id="txtrefdate" 
                           maxlength="10" size="11"    
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="Check_Date(this.value);check(this);return checkdt(this)"
                           />
                    </td> 
              
              </tr>   
      
    </table>
    <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th style="font-size: 85%;">
            Select
            </th>
            <th style="font-size: 85%;">
              Asset Code
            </th>
            <th style="font-size: 85%;">
              Qty 
            </th>
            <th style="font-size: 85%;">Book Value</th>
            <th style="font-size: 85%;">Assessed Value</th>
           <th style="font-size: 85%;">Assesment Date</th>
            <th style="font-size: 85%;">
            Remarks by the Officer
            </th>
            <th style="font-size: 85%;">
            Remarks by the Divisional Officer
            </th>
            <th style="font-size: 85%;">
            Remarks by SE
            </th>
            <th style="font-size: 85%;">
            BP No. 
            </th>
            <th style="font-size: 85%;">
            BP DATE
            </th>
            <th style="font-size: 85%;">
            Proceeding Order No
            </th>
            <th style="font-size: 85%;">
            Proceeding Order Date
            </th>
             <th style="font-size: 85%;">
           Remarks
            </th>
          
          </tr>
          <tbody id="grid_body" class="table">
          </tbody>
        </table>    
      <table cellspacing="2" cellpadding="3" border="0" align="center">
       <tr>
			<td class="tdH">
				<input type="button" name="CmdAdd" value="SUBMIT" id="CmdAdd" onclick="addBtn();">
			</td>
	        
	        <td class="tdH">
	            <input type="button" name="CmdClose" value="CANCEL" id="CmdList" onclick="closeWindow()">
	        </td>
       </tr>
    </table>
   
    
  </form>
  </body>
</html>
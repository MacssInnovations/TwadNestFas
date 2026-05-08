<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>AA52 Register for Closing Balance Report</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
     <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
</script>
<script type="text/javascript" src="../scripts/AA52_Register_OB_Report.js"></script>
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadUnitsDelete();">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">AA52 Register for Closing Balance Report</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmAA52_Register" id="frmAA52_Register" method="GET" action="../../../../../FAS_AA52_Report_Servlet" onsubmit="return checkNull()">
                  
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
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="common_LoadOffice(this.value);checkOffice();"></select>
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
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
             <tr class="table">
		             <td class="table">
		                <div align="left">
		                        Options<font color="#ff2121">*</font>
		              </div>
		              </td>
		              <td colspan="2">
		               <div align="left">
		              <input type=radio name="allid" id=allid value="All" checked="checked" onclick="loadUnitsDelete();">ALL
                      <input type=radio name=allid id=allid value="P" onclick="loadUnits();">Office Wise
                      </div>
		              </td>
              </tr>
         	 <tr class="table">
              <td class="table">
                <div align="left">
                        Accounting Unit Rendered For<font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
               <div align="left">
                    <select name="unit_rendered" id="unit_rendered" >
                    <option value="">--All Units--</option>
                 
                   </select>
                    </div>
              </td>
              </tr>  
           <tr class="table">
              <td class="table">
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
               <div align="left">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" tabindex="3"  >
                    <option value="">--Select Year--</option>
                    <option value="2011-12">2011-12</option>
                    <option value="2012-13">2012-13</option>
                    </select>
                    </div>
              </td>
              </tr>
             <tr class="table">
		             <td class="table">
		                <div align="left">
		                        Options<font color="#ff2121">*</font>
		              </div>
		              </td>
		              <td colspan="2">
		               <div align="left">
		              <input type=radio name="allasset" id="allasset" value="All" onclick="blockHead()">ALL
                      <input type=radio name="allasset" id="allasset" value="P" onclick="blockHead();" checked="checked">Asset Code Wise
                      </div>
		              </td>
              </tr>
              <tr class="table">
                <td>
                   <div id="head_div1" name="head_div1" >
                     Asset Major Class
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div id="head_div2" name="head_div2" >
                
                    <select size="1" name="cmbasset" id="cmbasset" tabindex="4"  >
                      <%
                      int asset=0;
                    try
                    {
                     ps=con.prepareStatement("select Asset_Major_Class_Code,Asset_Major_Class_Desc from Fas_Mst_Assets_Class order by Asset_Major_Class_Code");
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                    	
                      out.println("<option value='"+rs.getInt("Asset_Major_Class_Code")+"'>"+rs.getString("Asset_Major_Class_Desc")+"</option>");
                     }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in asset combo..."+e);
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
              </tr><!--
              
              
              <TR class="table">
                                      <TD >
            Book value(Opening Balance in A52 Register)
                                </TD>
                                      <TD>
                                        <input type="text" name="txtOB_bal" id="txtOB_bal" tabindex="5" onkeypress="return numFloatInt(event,this);"> 
                                          
                                      </TD>
                                  </TR>
              <TR class="table">
                                      <TD>
                                        Accumulated Depreciation/
                                        Apportionment of grant(upto date depreciation/
                                        Apportionment of grant in A52 Register)  
                             </td><td>
                                      <input type="text" name="txtapport_grant" id="txtapport_grant" tabindex="6" size="30" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
              <tr class="table">
                                    <td>Depreciated value debited to discarded asset A/c 
                                    (Depreciated cost in A52)</td>
                                    <TD>
                                    <input type="text" name="txtdep_debit" id="txtdep_debit" tabindex="7" size="30" onkeypress="return numFloatInt(event,this);" >
                                        
                                      </TD>
                                                                  
                                  </tr>
             
                                   <tr class="table">
                <td>
                  <div align="left"> Ref. journal no and Date of office in which value a/c is maintained  
                   
                   </div>
                </td>
                <TD>
                                     <input type="text" name="txtjournal_no" id="txtjournal_no"  tabindex="8" size="30"  onkeypress="return numFloatInt(event,this);">
                                      Date<input type="text" name="txtjournal_date" id="txtjournal_date"  tabindex="9" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtjournal_date,1);"
                         alt="Show Calendar"></img> 
                                      </TD>
              </tr>     
                                  <TR class="table">
                                      <TD>
                   Condemenation Approved Details
            
                                </TD>
                                      <TD>
                                        Survey Report No<input type="text" name="txtsurvey_no" id="txtsurvey_no" tabindex="10" onkeypress="return numFloatInt(event,this);">
                                           Date<input type="text" name="txtsurvey_date" id="txtsurvey_date"  tabindex="11" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtsurvey_date,1);"
                         alt="Show Calendar"></img> 
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                        Date of Auction
                                </TD>
                                      <TD>
                                         <input type="text" name="txtauction_date" id="txtauction_date"  tabindex="12" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtauction_date,1);"
                         alt="Show Calendar"></img> 
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Name of the person to whom asset is handed over
                                </TD>
                                      <TD>
                                          <input type="text" name="txtperson_name" id="txtperson_name" tabindex="13" onchange="return toCheck1()"  >
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Amount recieved on Auction
                                </TD>
                                      <TD>
                                          <input type="text" name="txtauction_amt" id="txtauction_amt" tabindex="14" onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                  </TR>
                                  <TR class="table">
                                      <TD>
                                         Ref. CB Vr No. & Date of office in which value a/c is maintained
                                </TD>
                                      <TD>
                                          <input type="text" name="cb_vrno" id="cb_vrno" tabindex="15" onkeypress="return numFloatInt(event,this);" >
                                           Date<input type="text" name="txtcb_date" id="txtcb_date"  tabindex="16" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtcb_date,1);"
                         alt="Show Calendar"></img>
                                      </TD>
                                  </TR>
                                  
              <tr class="table">
                <td>
                  <div align="left">Profit</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="txt_profit" id="txt_profit"  tabindex="17" onkeypress="return numFloatInt(event,this);"></input>
                            
                         
                  </div>
                </td>
              </tr>
               <TR class="table">
                                      <TD>
                                         Loss
                                </TD>
                                      <TD>
                                          <input type="text" name="txt_loss" id="txt_loss" tabindex="18"  onkeypress="return numFloatInt(event,this);">
                                          
                                      </TD>
                                  </TR>                     
              <tr class="table">
                <td>
                  <div align="left">Debit to discarded asset a/c of office</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtoff_debit" id="txtoff_debit" tabindex="19" onkeypress="return numFloatInt(event,this);" >
                  </div>
                </td>
              </tr>
                 <TR class="table">
                                      <TD>
                          Credit to discarded asset a/c of office
           
                                </TD>
                                      <TD>
                                          <input type="text" name="txtoff_credit" id="txtoff_credit" tabindex="20" onkeypress="return numFloatInt(event,this);" >
                                          
                                      </TD>
                                  </TR>
                                  TR class="table">
                                      <TD>
                                        Ref.journal No.& Date of office in which value a/c is maintained
                                </TD>
                                      <TD>
                                          <input type="text" name="txtjournal_vno2" id="txtjournal_vno2" tabindex="21" onkeypress="return numFloatInt(event,this);" >
                                            Date<input type="text" name="txtjournal_date2" id="txtjournal_date2"  tabindex="18" 
                            
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAA52_Register.txtjournal_date2,1);"
                         alt="Show Calendar"></img>
                                      </TD>
                                  </TR
                                  
                                   <TR class="table">
                                      <TD>
                                         Remarks 
                                </TD>
                                      <TD>
                                          <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="21" 
                              rows="4"></textarea>
                                          
                                      </TD>
                                  </TR>
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete')" disabled/>
            input type="button" name="CmdList" value="LIST"
                   id="CmdList" onclick="callServer('Get')"/
            <input type="reset" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll();"/>
            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
          </td>
        </tr>
                -->
           
                <tr class="table">
        <td>
         <div align="left">
                  Report Option: 
                  </div>   
        </td>
        <td colspan="3" align="left">
        <div align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
          HTML
          </div>
        </td>
      </tr>
       
         </table>
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
             
    </form></body>
</html>
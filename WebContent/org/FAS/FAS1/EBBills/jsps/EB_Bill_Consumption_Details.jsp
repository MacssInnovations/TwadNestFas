<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>EB Bill & Consumption Details</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
       
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript" src="../scripts/EB_Bill_Consumption_Details.js"></script>
    
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>      
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
     
  
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
         function loadDate()
         {
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         
         if(day<=9 && day>=1)
         day="0"+day;
         if(month<=9 && month>=1)
         month="0"+month;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         var monthArray =new Array("January", "February", "March", 
                   "April", "May", "June", "July", "August",
                   "September", "October", "November", "December");
        document.frmJournal_General.txtCrea_date.value=day+"/"+month+"/"+year;
        
         }
        
   
</script>
  </head>
  <body  bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">EB Bill & Consumption Details</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="eb_bill_consumption" id="eb_bill_consumption">
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
  %>
      <% 
      try{
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
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
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Office Details</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Office&nbsp;Name</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_unitName"
                           id="txtAcc_unitName" value="<%=oname%>"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                  </div>
                </td>
              </tr>
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
                  </div>
                </td>
              </tr>
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
                     <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
                             //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                            //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                      <%
                 
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
                        //out.println("<option value="+oid+">"+oname+"</option>");
                        while(rs.next())
                        {
                        ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                        rs2=ps2.executeQuery();
                        if(rs2.next())
                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");
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
              <tr class="table">
                <td>
                  <div align="left">
                   Service No.
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <%
                   ps=con.prepareStatement("select SERVICE_NO from FAS_EB_METER_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
                        ps.setInt(1,unitid);
                        ps.setInt(2,oid);
                        rs=ps.executeQuery();%>
                       
                        <select  name="seviceno" id="seviceno" tabindex="2">
                  <%      while(rs.next())
                        {
                       
                        out.println("<option value="+rs.getString("SERVICE_NO")+">"+rs.getString("SERVICE_NO")+"</option>");
                        }
                           
                    %>
                    </select>
                  </div>
                </td>
              </tr>
              <!--<tr class="table" style="display:none">
                <td>
                  <div align="left">
                    Cash Book Month & Year
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                  
                    <input type="hidden" name="txtCash_Month_hid"
                           id="txtCash_Month_hid" size="2" maxlength="2"/>
                    <input type="text" name="txtCash_Month" id="txtCash_Month"
                           style="background-color: #ececec"  readonly="readonly"  maxlength="10" size="11"/>
                    <input type="text" name="txtCash_year" id="txtCash_year"
                           style="background-color: #ececec"  readonly="readonly"   maxlength="4" size="5"/>
                  </div>
                </td>
              </tr>-->
              
              <tr class="table">
                <td>
                  <div align="left">
                   Bill No.
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="billno"  id="billno"  />
                  </div>
                </td>
              </tr>
           <tr class="table">
                <td width="30%">
                  <div align="left">
                   Bill Date
                  
                  </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="billdate"  id="billdate"  size="11" /> 
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.eb_bill_consumption.billdate);"  alt="Show Calendar"></img>
                   
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
              For the Month & Year
                  </div>
                </td>
                <td>
                  <div align="left">
                                         
                     <select name="cashmonth"  id="cashmonth"  >
          <option value="1">January</option>
          <option value="2">February</option>
          <option value="3">March</option>
          <option value="4">April</option>
          <option value="5">May</option>
          <option value="6">June</option>
          <option value="7">July</option>
          <option value="8">August</option>
          <option value="9">September</option>
          <option value="10">October</option>
          <option value="11">November</option>
          <option value="12">December</option>
          </select>
           
                      &nbsp;&nbsp; & &nbsp;&nbsp;
                      
                       <input type="text" name="cashyear"  id="cashyear" size="4" maxlength="4"  />
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td >
                  <div align="left">
                 Due Date
                   </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="duedate"  id="duedate" size="11" /> 
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.eb_bill_consumption.duedate);"  alt="Show Calendar"></img>
                   
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td >
                  <div align="left">
                Bill Received On
                   </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="billreceived"  id="billreceived" size="11" /> 
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.eb_bill_consumption.billreceived);"  alt="Show Calendar"></img>
                   
                  </div>
                </td>
              </tr>
              
               
              <tr class="table">
                <td>
                  <div align="left">
                  Tranformer Loss
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="tranloss"  id="tranloss" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                  Tranformer Capacity in KVA
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="transcapacity"  id="transcapacity" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                 Total Energy Charges (A)
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="energycharges"  id="energycharges" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">
                Demand Tariff (X)
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="demandtariff"  id="demandtariff" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                Demand Units in KVA (Y)
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="demandunit"  id="demandunit" onblur="valuecalculated()" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
               Total Demand Charges (B=X*Y)
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="totaldemand"  id="totaldemand" disabled="disabled" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                Add Others (C)
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="addother"  id="addother" onkeypress="return filter_real(event,this,5,2)"  />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                 Less Others (D)
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="lessother"  id="lessother" onblur="netvalues()" onkeypress="return filter_real(event,this,5,2)"  />
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                   Net Value (A+B+C-D)
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="netvalue"  id="netvalue" disabled="disabled" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td >
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="7" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              
              </table>
             
              
             
          </div>
        </div>
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" >Energy Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong>Energy Details</strong>
                          </div>
                        </td>
                      </tr>
                
                      <tr class="table">
                <td>
                  <div align="left">
                   Energy Type
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="energytype"    id="energytype" size="11"/>  
                  </div>
                </td>
              </tr>
                                            
              
               <tr class="table">
                <td >
                  <div align="left">Energy Tariff (ET)</div>
                </td>
                <td>
                  <div align="left">
                                       
                      <input type="text" name="energytariff" maxlength="8" onkeypress="return filter_real(event,this,5,2)"  id="energytariff" size="11"/>
                    
                  </div>
                </td>
              </tr>
              
                         <tr class="table">
                        <td>
                          <div align="left">
                           Energy Unit (EU)
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="energyunit" maxlength="8" onkeypress="return filter_real(event,this,5,2)"  id="energyunit" size="11" onblur="energychargecalulate()" />
                          </div>
                        </td>
                      </tr>
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                           Energy Charge (EC=ET*EU)
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="energycharge"  onkeypress="return filter_real(event,this,5,2)" id="energycharge" disabled="disabled" size="11"/>
                          </div>
                        </td>
                      </tr>
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                          Rebate (RB)
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="rebate" maxlength="8"  id="rebate" onkeypress="return filter_real(event,this,5,2)" size="11" onblur="afterrebatecalulate()" />
                          </div>
                        </td>
                      </tr>
                      
                       <tr class="table">
                        <td>
                          <div align="left">
                          Energy charges after Rebate (EC-RB)
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="afterrebate"  id="afterrebate" size="11" disabled="disabled"/>
                          </div>
                        </td>
                      </tr>
                                                                                  
             
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd" id="cmdadd"
                                 value="ADD" onclick="ADD_GRID()" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick="update_GRID()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete" value="DELETE"
                                 id="cmddelete" onclick="delete_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear" value="CLEAR ALL"
                                 id="cmdclear" onclick="clearall()"/></td>
                        </tr>
                        </table>
                        </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                      <tr class="table">
                        <th >Select</th>
                        <th >Energy Type</th>
                        <th >Energy Tariff</th>
                         <th>Energy Unit</th>
                        <th>Energy Charge</th>
                        <th >Rebate</th>
                         <th >Energy charges after Rebate</th>
                        
                      </tr>
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
        
        
        
        
        
        
            <div class="tab-page" id="gd" >
          <h2 class="tab" >Others</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong>Others</strong>
                          </div>
                        </td>
                      </tr>
                
                      <tr class="table">
                <td>
                  <div align="left">
                  Add/Less
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="addorless" id="addorless"  checked="checked" value="A"/>Add
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="addorless" value="L"/>Less &nbsp;&nbsp;&nbsp;&nbsp; 
                  </div>
                </td>
              </tr>
             
             <tr class="table">
                <td >
                  <div align="left">Description</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="description" id="description" cols="30" onkeypress="return check_leng(this.value);" rows="2"></textarea>
                  </div>
                </td>
              </tr>
              
                     <tr class="table">
                <td>
                  <div align="left">
                  Amount
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="amount"  id="amount" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
                                                                                                           
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdaddother" id="cmdaddother"
                                 value="ADD" onclick="ADD_GRID_OTHER()" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdateother" value="UPDATE"
                                 id="cmdupdateother" onclick="update_GRID_OTHER()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddeleteother" value="DELETE"
                                 id="cmddeleteother" onclick="delete_GRID_OTHER()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclearother" value="CLEAR ALL"
                                 id="cmdclearother" onclick="clearother()"/></td>
                        </tr>
                        </table>
                        </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable_other" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                      <tr class="table">
                        <th >Select</th>
                        <th >ADD/LESS</th>
                        <th >Description</th>
                        <th>Amount</th>
                                              
                      </tr>
                       <tbody id="grid_body_other" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
 
      </div>
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="addvalues()"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="exit();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form>
    
    <%}catch(Exception e){out.println(e);} %>
    
    </body>
</html>
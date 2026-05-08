<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>EB Bill Work Sheet Annexure Details</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
       
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript" src="../scripts/EB_Bill_WS_Annexure.js"></script>
    
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
            <font size="4">EB Bill Work Sheet Annexure Details</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="eb_bill_annexure" id="eb_bill_annexure">
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
                       
                        <select  name="seviceno" id="seviceno" >
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
                      
                       <input type="text" name="cashyear"  id="cashyear" size="4" maxlength="4" onblur="detailsbillno()" />
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td>
                  <div align="left">
                   Bill No.
                  </div>
                </td>
                <td>
                  <div align="left">
                  
                    <select size="1" name="billno" id="billno" onchange="detailsbilldate()">
                                        
                                  <option value="">--Select Code--</option>
                                </select>
                                                                  
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
                   <input type="text" name="billdate"  id="billdate"  size="11" disabled="disabled" /> 
                                   
                  </div>
                </td>
              </tr>
              
             <tr class="table">
                <td >
                  <div align="left">
               Circle Name
                   </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="circlename"  id="circlename" /> 
                                      
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td >
                  <div align="left">
                Meter No.
                   </div>
                </td>
                <td>
                  <div align="left">
                  
                   <select size="1" name="meterno" id="meterno">
                                        
                                  <option value="">--Select Code--</option>
                                </select>                   
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td >
                  <div align="left">
               Reading Date
                   </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="readingdate"  id="readingdate" /> 
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.eb_bill_annexure.readingdate);"  alt="Show Calendar"></img>
                   
                  </div>
                </td>
              </tr>
              
               
              <tr class="table">
                <td>
                  <div align="left">
                 Read Type Code
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="readtypecode"  id="readtypecode"  />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                  Meter UOM Type
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="metertype" id="metertype"  >
                  <option value="KWHR">KWHR</option>
                   <option value="KVAHR">KVAHR</option>
                    <option value="RKVAHR">RKVAHR</option>
                     <option value="KVAMD">KVAMD</option>
                        </select>
                  </div>
                </td>
              </tr>
              
                           
              
               <tr class="table">
                <td>
                  <div align="left">
                Final Reading
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="finalreading"  id="finalreading" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
               Initial Reading
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="initialreading"  id="initialreading" onblur="diffreading()" onkeypress="return filter_real(event,this,5,2)"  />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
               Difference in Reading
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="differencereading"  id="differencereading" disabled="disabled" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                MF
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="mf"  id="mf" onkeypress="return filter_real(event,this,5,2)"  />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                Consumption
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="comsumption"  id="comsumption" onkeypress="return filter_real(event,this,5,2)"  />
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                 Comp_Con
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="comp_con"  id="comp_con"  onkeypress="return filter_real(event,this,5,2)"/>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                 Other_Cons
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="other_cons"  id="other_cons" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                 AVG_Cons
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="avg_cons"  id="avg_cons" onkeypress="return filter_real(event,this,5,2)" />
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td >
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50"  onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              
              </table>
             
              
             
          </div>
        </div>
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" >Concession Adjustments</h2>
           
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
                            <strong>Concession Adjustments</strong>
                          </div>
                        </td>
                      </tr>
                
                  <tr class="table">
                <td>
                  <div align="left">
                  Adjustment Code
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="adjcode" id="adjcode"  >
                     <option value="">--Select code--</option>
                  <option value="test">test</option>
                   <option value="test1">test1</option>
                    
                        </select>
                  </div>
                </td>
              </tr>
                
                
                
                
                
                     <tr class="table">
                <td>
                  <div align="left">
                 PF Incentive
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                 <input type="text" name="pfincentive"  onkeypress="return filter_real(event,this,8,2)" id="pfincentive" />     
                  </div>
                </td>
              </tr>
             
             
              <tr class="table">
                <td>
                  <div align="left">
                Adjustment Affected
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                 <input type="text" name="affected"  onkeypress="return filter_real(event,this,8,2)" id="affected" />     
                  </div>
                </td>
              </tr>
             
              <tr class="table">
                <td>
                  <div align="left">
                Adjustment Not Affected
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                 <input type="text" name="notaffected"  onkeypress="return filter_real(event,this,8,2)" id="notaffected"  onblur="totaladjust()" />     
                  </div>
                </td>
              </tr>
                  
                  
                  <tr class="table">
                <td>
                  <div align="left">
                Total Adjustment 
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                 <input type="text" name="totaladj" disabled="disabled" id="totaladj" />     
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
                        <th>Adjustment Code</th>
                        <th >PF Incentive</th>
                        <th>Adjustment Affected</th>
						<th>Adjustment Not Affected</th>   
                         <th>Total Adjustment</th>                          
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
          <h2 class="tab" >TAX</h2>
           
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
                            <strong>TAX</strong>
                          </div>
                        </td>
                      </tr>
                
                     
              
              
                         <tr class="table">
                <td>
                  <div align="left">
                   Realized Energy Charges (Total Energy Charges in Table2) 
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="realizedenergycharge"    id="realizedenergycharge"  disabled="disabled" />  
                  </div>
                </td>
              </tr>
                                            
              
               <tr class="table">
                <td >
                  <div align="left">
                  
                  Recorded Demand Charges (Total Demand Charges in Table 2)
                  
                  </div>
                </td>
                <td>
                  <div align="left">
                                       
                      <input type="text" name="regdemandcharge" maxlength="8"  id="regdemandcharge" disabled="disabled" />
                    
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td >
                  <div align="left">
                  
                  Total Adjustments
                  
                  </div>
                </td>
                <td>
                  <div align="left">
                                       
                      <input type="text" name="totaladjustment"  id="totaladjustment" disabled="disabled" />
                    
                  </div>
                </td>
              </tr>
              
                         <tr class="table">
                        <td>
                          <div align="left">
                          Taxable Amount (C)
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="taxableamount" disabled="disabled" id="taxableamount"  />
                          </div>
                        </td>
                      </tr>
                      
                        <tr class="table">
                        <td>
                          <div align="left">
                           Tax Amount 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="taxamount"  id="taxamount" disabled="disabled" />
                          </div>
                        </td>
                      </tr>
                      
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                          Old Tax Amount
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="oldtaxamount"  onkeypress="return filter_real(event,this,5,2)" id="oldtaxamount" onblur="taxvalue()" />
                          </div>
                        </td>
                      </tr>
                    
                      
                      
                       <tr class="table">
                        <td>
                          <div align="left">
                          Total Tax Amount 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="totaltaxamount" id="totaltaxamount" disabled="disabled" />
                          </div>
                        </td>
                      </tr>
                      
                      
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.sql.*,java.util.*,java.text.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<title>Sanction Proceedings</title>
  <script type="text/javascript" src="../scripts/Sanction_Proceedings.js"></script>
  
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
       
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
  
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>      
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>

</head>
 <body  bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Sanction Proceedings (Single Payee)</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="sanction_proceedings" id="sanction_proceedings">
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
   
    
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
   java.util.Date date = new java.util.Date();
     
    
    
   %>
   
         
           
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
                  <div align="left">Payment </div>
                </td>
                <td>
                  <div align="left">
                  <input type="radio" name="payment" value="Regular"  checked="checked">Regular
                        &nbsp;&nbsp;  &nbsp; &nbsp;      
                     <input type="radio" name="payment" value="Advance" > Advance  
                                             
                       
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">Bill Major Type   </div>
                </td>
                <td>
                  <div align="left">
                    <select name="majortype"  id="majortype" onchange="call('get')"  >
                    <option value="">--Select Major Type--</option>
                   <%      ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE, BILL_MAJOR_TYPE_DESC  from FAS_BILL_MAJOR_TYPES");
                       
                        rs=ps.executeQuery();
                       
                        while(rs.next())
                        {
                       
                        out.println("<option value="+rs.getInt("BILL_MAJOR_TYPE_CODE")+">"+rs.getString("BILL_MAJOR_TYPE_DESC")+"</option>");
                        }    %>
                     </select>         
                  </div>
                </td>
              </tr>
              
              
                <tr class="table">
                <td>
                  <div align="left">Bill Minor Type </div>
                </td>
                <td>
                  <div align="left">
                    <select name="minortype"  id="minortype"  onchange="call('getsub')"  >
                  <option value="">--Select Minor Type--</option>
                     </select>  
                     
                        
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Bill Sub Type  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="subtype"  id="subtype"  >
                  <option value="">--Select Sub Type--</option>
                     </select>  
                     
                       
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">Payee Type </div>
                </td>
                <td>
                  <div align="left">
                  <input type="radio" name="payeetype" value="Employee"  checked="checked">Employee
                        &nbsp;&nbsp;  &nbsp; &nbsp;      
                     <input type="radio" name="payeetype" value="PrivilegedUser" > Privileged User  &nbsp;&nbsp;  &nbsp; &nbsp;    
                  <input type="radio" name="payeetype" value="Pensioner" >Pensioner                               
                       
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Payee Code</div>
                </td>
                <td>
                  <div align="left">
                <input type="text" name="txtEmpId" id="txtEmpId" size="5" maxlength=5 onkeypress="return filter_real(event,this,5,0)" onchange="call('load')"></input>
                                                     
                <img src="../../../../../images/c-lovi.gif" width="20"  height="20" alt="empList"  onclick="servicepopup();"></img>
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">Payee Name</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="empname" disabled="disabled" > &nbsp;&nbsp;&nbsp;&nbsp; Designation  &nbsp;&nbsp; <input type="text" id="designation" disabled="disabled" >                     
                       
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">Ref.No </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="refno" >                
                       
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Ref.Date </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="refdate" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings.refdate);"  alt="Show Calendar"></img>             
                       
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">Sanction Proceeding Date</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="prodate" value="<%=dateFormat.format(date)%>" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings.prodate);"   alt="Show Calendar"></img>             
                       
                  </div>
                </td>
              </tr>
              
              
             <tr class="table">
                <td>
                  <div align="left">Sanction Authority </div>
                </td>
                <td>
                  <div align="left">
                    <select name="sanctionauthority"  id="sanctionauthority" >
                    <option value="">--Select Sanction Authority--</option>
                   <%      ps=con.prepareStatement("select DESIGNATION_ID, DESIGNATION from HRM_MST_DESIGNATIONS order by DESIGNATION");
                       
                        rs=ps.executeQuery();
                       
                        while(rs.next())
                        {
                       
                        out.println("<option value="+rs.getInt("DESIGNATION_ID")+">"+rs.getString("DESIGNATION")+"</option>");
                        }    %>
                     </select>         
                  </div>
                </td>
              </tr> 
              
              <tr class="table">
                <td>
                  <div align="left">Sanctioned By (Select Emp.Code)</div>
                </td>
                <td>
                  <div align="left">
                <input  type="text" name="sanctionedby" id="sanctionedby" maxlength=5 onkeypress="return filter_real(event,this,5,0)" size="5" onchange="call('checkemp')"></input>
                                                     
                <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList"  onclick="servicepopup1();"></img>
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td>
                  <div align="left">Sanctioned By (Emp.Name)</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="sanname" disabled="disabled" > &nbsp;&nbsp;&nbsp;&nbsp; Designation  &nbsp;&nbsp; <input type="text" id="sandesignation" disabled="disabled" >                     
                       
                  </div>
                </td>
              </tr>
              
             
              
              
              
              
              
               <tr class="table">
          <td>Account Head Code 
                
          
          </td>
          <td>
           <input type="text" name="cmbAcHeadCode" id="cmbAcHeadCode" maxlength="8"  onchange="call('headcode');" onkeypress="return filter_real(event,this,8,0)"   size="9"/>
          <img src="../../../../../images/c-lovi.gif" width="20"  height="20" alt="AccountHeadList" onclick="AccHeadpopup();"></img>
          </td>
        </tr>
            
            
            <tr class="table">
          <td>Account Head Code Desc 
                           
          </td>
          <td>
           <input type="text" name="headdesc" id="headdesc" disabled="disabled" size="40" />
         
          </td>
        </tr>
            
            
             <tr class="table">
                <td>
                  <div align="left">Budget Provided</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="budgetprovided" disabled="disabled" >                
                       
                  </div>
                </td>
              </tr>
            
            <tr class="table">
                <td>
                  <div align="left">Budget so far spent</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="budgetspent" disabled="disabled" >                
                       
                  </div>
                </td>
              </tr> 
            
            
            
                 <tr class="table">
                <td>
                  <div align="left">Accounting Unit in which the payment to be made </div>
                </td>
                <td>
                  <div align="left">
                    <select name="paymentunit"  id="paymentunit" >
                    <option value="">--Select Accounting Unit--</option>
                   <%      ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS order by ACCOUNTING_UNIT_NAME");
                       
                        rs=ps.executeQuery();
                       
                        while(rs.next())
                        {
                       if(unitid==rs.getInt("ACCOUNTING_UNIT_ID")){
                        out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" selected=selected >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                       }else{
                    	   out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>"); 
                       }
                       
                        }    %>
                     </select>         
                  </div>
                </td>
              </tr> 
              
              
              <tr class="table">
                <td>
                  <div align="left">Recovery From Salary/Pension?</div>
                </td>
                <td>
                  <div align="left">
                  <input type="radio" name="recovery" value="Y"  checked="checked">Yes
                        &nbsp;&nbsp;  &nbsp; &nbsp;      
                     <input type="radio" name="recovery" value="N" > No
                    
                  </div>
                </td>
              </tr>
              
                <tr class="table">
                <td>
                  <div align="left">Total Installments</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="totalinstallment" onkeypress="return filter_real(event,this,2,0)" >                
                       
                  </div>
                </td>
              </tr> 
              
               <tr class="table">
                <td>
                  <div align="left">EMI</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="emi" onchange="totalcal()" onkeypress="return filter_real(event,this,7,2)" >                
                       
                  </div>
                </td>
              </tr> 
              
               <tr class="table">
                <td>
                  <div align="left">Recovery Start Month</div>
                </td>
                <td>
                  <div align="left">
                            
                  
                  
                   <select name="recoverymonth"  id="recoverymonth"  >
                <option value="">--Select Month--</option>     
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
                  
                  
                       
                  </div>
                </td>
              </tr> 
              
               <tr class="table">
                <td>
                  <div align="left">Residual Amount</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="residualamount" value="0" onkeypress="return filter_real(event,this,7,2)" onchange="totalcal()">                
                       
                  </div>
                </td>
              </tr> 
              
               <tr class="table">
                <td>
                  <div align="left">Residual Amount Deduction Instl.No</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="installment" onkeypress="return filter_real(event,this,2,0)">                
                       
                  </div>
                </td>
              </tr> 
              
              
              <tr class="table">
                <td>
                  <div align="left">Total Sanction Amount</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="totalamount" disabled="disabled"   >                
                       
                  </div>
                </td>
              </tr> 
              
             <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                  <textarea rows="4" cols="25" id="remarks"></textarea>            
                    <input type="hidden" id="sanctionno" disabled="disabled"   >        
                  </div>
                </td>
              </tr> 
               
                          
              
              
              
              
              
               <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="call('Add')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="call('Update')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="call('Delete')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListHeads()" tabindex="60"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="javascript:window.close()" tabindex="70"/>
                     </td>
                 </tr>
              
              
              </table>
              </div>
      </td>
      </tr>
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              </table>
              </div>
          
              </form>
                <%}catch(Exception e){out.println(e);} %>
              </body>
              </html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>General Sanction Proceedings (Single Payee-Firm)</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
       
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript" src="../scripts/Sanction_Proceedings_Firm.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
       
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>      
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
     
  
   
  </head>
  <body  bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">General Sanction Proceedings (Single Payee-Firm)</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="sanction_proceedings_firm" id="sanction_proceedings_firm" method="post" action="../../../../../Sanction_Proceedings_Firm?command=Add" onsubmit="return nullcheck()">
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
  
  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
  java.util.Date date = new java.util.Date();
  
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
                 <select name="payeetype" id="payeetype">
                 <option value="">--Select--</option>
                  <option value="Type1">Type1</option>
                   <option value="Type2">Type2</option>
                  <option value="Type3">Type3</option> 
                 </select>                    
                       
                  </div>
                </td>
              </tr>
              
            <tr class="table">
                <td>
                  <div align="left">Ref.No </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="refno" name="refno" >                
                       
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Ref.Date </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="refdate" name="refdate" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings_firm.refdate);"  alt="Show Calendar"></img>             
                       
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">Sanction Proceeding Date</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="prodate" value="<%=dateFormat.format(date)%>" name="prodate" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings_firm.prodate);"   alt="Show Calendar"></img>             
                       
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
                <input  type="text" name="sanctionedby" id="sanctionedby" maxlength=5 onkeypress="return filter_real(event,this,5,0)" size="5" onchange="call('load')"></input>
                                                     
                <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList"  onclick="servicepopup();"></img>
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
                <td>
                  <div align="left">Estimate Sanction Number</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="estimatenumber" name="estimatenumber" >                
                       
                  </div>
                </td>
              </tr> 
              
            <tr class="table">
                <td>
                  <div align="left">Estimate Sanction Date</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="estimatedate" name="estimatedate" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings_firm.estimatedate);"  alt="Show Calendar"></img>             
                       
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
                  <div align="left">Total Sanctioned Amount (in Rs.)</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="totalamount"  name="totalamount" onchange="budgetcheck()" >                
                       
                  </div>
                </td>
              </tr> 
                
              
              <tr class="table">
                <td >
                  <div align="left">particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="particulars" id=""particulars"" cols="50" tabindex="7" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              
              </table>
             
              
             
          </div>
        </div>
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" >Details</h2>
           
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
                            <strong>Details</strong>
                          </div>
                        </td>
                      </tr>
                
                      <tr class="table">
                <td>
                  <div align="left">
                 Invoice No.
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" id="invoiceno" > 
                  </div>
                </td>
              </tr>
                                          
                   <tr class="table">
                <td>
                  <div align="left">Invoice Date </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="invoicedate" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings_firm.invoicedate);"  alt="Show Calendar"></img>             
                       
                  </div>
                </td>
              </tr>          
              
              <tr class="table">
                <td >
                  <div align="left">particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="detailsparticular" id="detailsparticular" cols="50" tabindex="7" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              
              
              
                      <tr class="table">
                        <td>
                          <div align="left">
                          M-Book Ref.No
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="mrefno"  onkeypress="return filter_real(event,this,5,2)" id="mrefno"  />
                          </div>
                        </td>
                      </tr>
                      
                      
                       <tr class="table">
                <td>
                  <div align="left">M-Book Ref.Date </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" id="mrefdate" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings_firm.mrefdate);"  alt="Show Calendar"></img>             
                       
                  </div>
                </td>
              </tr>       
                      
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                        Agreement No. & Date
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="agreementno" maxlength="8"  id="agreementno" onkeypress="return filter_real(event,this,5,2)" size="11"  />
                         &nbsp; &nbsp; &nbsp;
                          <input type="text" id="agreementdate" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings_firm.agreementdate);"  alt="Show Calendar"></img> 
                         
                          </div>
                        </td>
                      </tr>
                      
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                        supplement Agreement No. & Date
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="suplementno" maxlength="8"  id="suplementno" onkeypress="return filter_real(event,this,5,2)" size="11"  />
                         &nbsp; &nbsp; &nbsp;
                          <input type="text" id="supplementdate" >   
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.sanction_proceedings_firm.supplementdate);"  alt="Show Calendar"></img> 
                         
                          </div>
                        </td>
                      </tr>
                      
                      
                      
                      
                       <tr class="table">
                        <td>
                          <div align="left">
                        Invoice Amount
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="invoiceamount"  id="invoiceamount"  />
                          </div>
                        </td>
                      </tr>
                      
                      <tr class="table">
                        <td>
                          <div align="left">
                        Sanc.Amount
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="sancamount"  id="sancamount"  />
                          </div>
                        </td>
                      </tr>
                      
                       <tr class="table">
                <td >
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="remarks" id="remarks" cols="50" tabindex="7" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
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
                                 id="cmdclear" onclick="clear()"/></td>
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
                        <th >Invoice No</th>
                        <th >Invoice Date</th>
                         <th>Particulars</th>
                        <th>M-Book Ref No</th>
                        <th >M-Book Ref Date</th>
                         <th >Agreement No</th>
                         <th >Agreement Date</th>
                          <th >Supplement Agreement No</th>
                           <th >Supplement Agreement Date</th>
                             <th >Invoice Amount</th>
                               <th >Sanc.Amount</th>
                                <th >Remarks</th>
                               
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
      
 
      </div>
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT" />
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Authorization System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/PaymentSystem/scripts/Common_PaymentType.js"></script>
    <script type="text/javascript" src="../scripts/Authorization_for_Journal.js" ></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/SJV_TB_Check.js"></script>   
    
     <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>           
    
    
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
          //  document.frmAuthorization_JAO_Create.txtCrea_date.value=day+"/"+month+"/"+year;
        }
            
   </script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body  onload="call_clr();LoadAccountingUnitID_Create('LIST_ALL_UNITS');loadDate();foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Authorization Details </font>
          </div>
        </td>
      </tr>
    </table>
    <form name="frmAuthorization_JAO_Create" id="frmAuthorization_JAO_Create" method="POST"
                  action="../../../../../Authorization_for_Journal.kv?Command=Add"
                  onsubmit="return checkNull()">
        
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table">
                <td width="40%">
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange= "common_LoadOffice(this.value), doFunction('load_Voucher_No','null');" >
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
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?");
                                ps.setInt(1,oid);
                                rs=ps.executeQuery();
                                 if(rs.next())
                                    unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                ps.close();
                                rs.close();
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                                ps.setInt(1,unitid);
                                rs=ps.executeQuery();
                                  if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  unitname=rs.getString("ACCOUNTING_UNIT_NAME");
                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
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
                <td width="40%">
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onchange=" doFunction('load_Voucher_No','null');" >
                      
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
                        //out.println("<option value="+oid+">"+oname+"</option>");
                        int countoffice=0;      // used to load the bank details for the first office of combo box
                        while(rs.next())
                        {
                        	int old_offid=0;

                       	 		ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=?");
                       	                             ps2.setInt(1,empid);
                       	                             rs2=ps2.executeQuery();
                       	                             while(rs2.next())
                       	                             {
                       	                            	 old_offid=old_offid+1;
                       	                             }
                       	                        	if(old_offid !=0)
                       	                        	{
                       	                        		ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? ");
                       	                        	}
                       	                        	else if(old_offid==0)
                       	                        	{
                       	                             ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('CL','NC','RD')");
                       	                        	}
                        	//ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
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
             <tr class="table">
                <td width="40%">
                  <div align="left">
                    Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                  
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="4" 
                           maxlength="10" size="11"  readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>    
                           
                     <img src="../../../../../images/calendr3.gif" id="Suppl_Date_Hide"
                         onclick="showCalendarControl(document.frmAuthorization_JAO_Create.txtCrea_date,1);"
                         alt="Show Calendar"></img>    
                         
                         
                  </div>
                </td>
            </tr>
            <tr class="table">
                <td >
                  <div align="left">
                    Sub-System type 
                <font color="#ff2121">
                  *
                </font>
              </div>
                </td>
                <td >
                  <div align="left">
                    <select name="cmbSubSystemType" id="cmbSubSystemType" onchange="Supplement_Date();">
                     <option value="">--Select Sub-System Type--</option>
                                         
                     <option value="GJV">General Journal</option>                     
                     <option value="LJV">Liability Journal</option>                     
                     <option value="SJV">Supplement Journal</option>
                     <!-- Imprest -->
                     <option value="IJV">Imprest Journal</option>
                     <option value="TJV">Temporary / Adv</option>
                     <!-- Rectification journal added on 22 Mar 2011 -->
                     <option value="RJV">Rectification Journal</option>
                     <option value="TDCP">TDA/TCA,TPA Cut off</option>   
                      <!-- added on  23/04/2012 BY Dhana-->   
                      
                      <!-- added on 28/05/2018 -->
                                          
                       <option value="TDAOS">TDA Originating(Supplement)</option>
                     <option value="TCAOS">TCA Originating(Supplement)</option>
                     <option value="TDAAS">TDA Accepting(Supplement)</option>
                     <option value="TCAAS">TCA Accepting(Supplement)</option>
                     <option value="TCAABS">TCA Accepting Before JVR(Supplement)</option>
                     <option value="TDAABS">TDA Accepting Before JVR(Supplement)</option>
                     <option value="TDARS">TDA Suspense Head Clearence(Supplement)</option>
                     <option value="TCARS">TCA Suspense Head Clearence(Supplement)</option> 
                                 
                    </select> 
                  </div>
                 <div align="left" id="supplement" style="display:none">
                  <select name="supNo" id="supNo" >
                     <option value="">Select Supplement No</option>
                     </select>
                    
                  </div><input type="button" id="goBtn" name="goBtn" value="Go" onclick="doFunction('load_Voucher_No','null');">
                </td>
               
              </tr>

            
          <tr class="table">
                <td width="40%">
                  <div align="left">
                    Voucher Number 
                <font color="#ff2121">
                  *
                </font>
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <select size="1" name="txtVoucher_No" id="txtVoucher_No" tabindex="5" onchange=" doFunction('load_Voucher_details','null');">
                      <option value="">--Select Voucher Number--</option>
                    </select>
                  </div>
                </td>
              </tr>
            <tr class="table"  >
                <td width="40%">
                  <div align="left">
                   Received /Paid /journal type/office name
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="com_value"  style="background-color: #ececec" readonly="readonly"
                           id="com_value" size="75"/>
                  </div>
                </td>
              </tr>
                    <tr class="table" >
                <td width="40%">
                  <div align="left">
                 Total Amount 
                    
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    
                    <input type="text" name="amt"  style="background-color: #ececec" readonly="readonly"
                           id="amt" size="16"/>
                           
                  </div>
                </td>
              </tr>
            <tr class="table">
                <td width="40%">
                  <div align="left">
                    Authorized To
                <font color="#ff2121"> *</font>
              </div>
                </td>
                <td width="60%">
                  <div align="left" id="modifyauthID">
                  
                   <span id="H_Modify" >
                      <input type="radio" id="radAuth_MC" name="radAuth_MC" value="M" checked="checked" onclick="call_remarks(this);"/> Modify
                   </span>
                   </div>
                    <div align="left" id="cancelauthID">
                   <span id="H_Cancel">
                      <input type="radio" id="radAuth_MC" name="radAuth_MC" value="C" onclick="call_remarks(this);"/> Cancel
                   </span>
                   </div>
                   <div align="left" id="remarksauthID"  style="display:none;">
                   <span id="H_Remarks">
                   <font color="red">	  Cancel due to Want of Fund in IMIS</font>
                      <input type="radio" id="radAuth_Y" name="radAuth_YN" value="Y" checked ="unchecked" onclick = "remarks_disp(this);"/> Yes
                      <input type="radio" id="radAuth_N" name="radAuth_YN" value="N" checked="checked" onclick = "remarks_disp(this);"/> No
                   </span>
                   </div>
                </td>
              </tr>    

              <tr class="table">
                <td width="40%">
                  <div align="left">
                    Reference Number 
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtReferNO_edit"
                           id="txtReferNO_edit" maxlength="15" size="16"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td width="40%">
                  <div align="left">
                    Reference Date 
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtReferDate_edit"
                           id="txtReferDate_edit" maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAuthorization_JAO_Create.txtReferDate_edit);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td width="40%">
                  <div align="left" >
                    Remarks 
                <font color="#ff2121">
                  *
                </font>
              </div>
                </td>
                <td width="60%">
                  <div align="left" >
                    <textarea name="txtRemak_edit" id="txtRemak_edit" cols="60"  onkeypress="return check_leng(this.value);"
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
            </table>
          </div>
      
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
               <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
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
      
     
     <input type="hidden" name="hidden_date" id="hidden_date"/>
     <input type="hidden" name="hidden_voctype" id="hidden_voctype"/>
      
    </form>   
   </body>
</html>
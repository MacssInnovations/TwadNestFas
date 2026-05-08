<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <title>firmbills_jsp</title>
        <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
        <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
        <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
        <script type="text/javascript" src="../../Reports/ReceiptSystem/scripts/CalendarControl.js"></script>
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
        <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
        <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
        <script type="text/javascript" src="../scripts/FirmBills_js.js"></script>
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
         <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
        <script type="text/javascript" language="javascript">
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
                 document.billform.billdate.value=day+"/"+month+"/"+year;    
                 
        }
       
</script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');callmajorType();callidval();loadDate();">
          <form name="billform" action="Get">
            <%
                            Connection con=null;
                            Statement stmt=null;
                             ResultSet rs=null,rs2=null;
                            ResultSet results=null;
                            PreparedStatement ps=null,ps2=null;
                            String xml=null;
                            int oid=0;
                            
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
                                       //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                       Class.forName(strDriver.trim());
                                       con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                                       try
                                       {
                                            stmt=con.createStatement();
                                            con.clearWarnings();
                                       }
                                       catch(SQLException e)
                                       {
                                            System.out.println("Exception in creating statement:"+e);
                                       }
                                        stmt=con.createStatement();
                           }
                           catch(Exception e)
                           {
                                System.out.println("Exception in opening connection:"+e);
                           } 
                            HttpSession session=request.getSession(false);
                            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                              
                            System.out.println("user id::"+empProfile.getEmployeeId());
                            int empid=empProfile.getEmployeeId();
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
                            }
                            catch(Exception e)
                            {
                                System.out.println(e);
                            }
                %>
                 <table cellspacing="1" cellpadding="3" width="100%" >
                     <tr class="tdH"> 
                                <td colspan="2">
                                     <div align="center">
                                         <font size="4"> Invoice Details for Firm Bills </font>
                                     </div>
                                </td>
                     </tr>
                </table>
                
                <div class="tab-pane" id="tab-pane-1">
                <div class="tab-page">
                <h2 class="tab" >Part-1 </h2>
                    <div align="center">
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                        <tr class="table">
                            <td> 
                                <div align="left">
                                    Accounting Unit Code 
                                    <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td>
                            <div align="left">
                            <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                                <%
                                  int unitid=0;
                                  String unitname="";
                                  try
                                  {
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
                                          out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                          unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                          }
                                          ps.close();
                                          rs.close();
                                      }
                                    }
                                  catch(Exception e)
                                    {
                                    System.out.println("here");
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
                        <tr>
                                    <td class="table" width="40%" align="left">Payment Type</td>
                                    <td class="table" align="left"> 
                                            <select name="paymenttype" id="paymenttype"> 
                                                <option value="">select</option>
                                                 <option value="R">Regular</option>
                                                 <option value="A">Advance</option>
                                            </select>
                                    </td>               
                        </tr>
                         <tr>
                                    <td class="table" width="40%" align="left">Bill Major Type</td>
                                    <td class="table" align="left"> 
                                            <select name="billmajortype" id="billmajortype" onchange="callminor();"> 
                                                <option value='0'>select</option>
                                            </select>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Minor Type</td>
                                    <td class="table" align="left"> 
                                            <select name="billminortype"  id="billminortype" onchange="callsub(this.value);"> 
                                                <option value='0'>select</option>
                                            </select>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Sub Type</td>
                                    <td class="table" align="left"> 
                                            <select name="billsubtype"  id="billsubtype" > 
                                                <option value='0'>select</option>
                                            </select>
                                            <input type="hidden" name="invoiceno" id="invoiceno" disabled="disabled"/>
                                             <input type="hidden" name="billno" id="billno" disabled="disabled"/>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Bill Date</td>
                                    <td class="table" align="left"> 
                                         
                                          <input type="text" name="billdate" id="billdate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);" />
						                           
						                   <img src="../../../../../images/calendr3.gif"
                                                 onclick="showCalendarControl(document.forms[0].billdate, 1);"
                                                 alt="Show Calendar"></img>
                                    </td>               
                        </tr>
                        <tr>
                        			<td class="table" width="40%" align="left">Invoice No</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="invoiceNo" id="invoiceNo" onkeypress="return numbersonly(event)"/>
                                    </td>  
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Invoice Date</td>
                                    <td class="table" align="left"> 
                                             <input type="text" name="invoicedate" id="invoicedate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                          onkeypress="return calins(event,this);" />  
						                          
						                     <img src="../../../../../images/calendr3.gif" 
                                                 onclick="showCalendarControl(document.forms[0].invoicedate, 1);setTimeout('checkBillDate()',2000);" 
                                                 alt="Show Calendar" ></img>
                                            
                                    </td>               
                        </tr>
                        <tr>
                                     <td class="table" width="40%" align="left">Invoice Received on Date</td>
                                    <td class="table" align="left"> 
                                            
                                           <input type="text" name="invoicereceivedondate" id="invoicereceivedondate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);"/>
                                           <img src="../../../../../images/calendr3.gif"
                                                 onclick="showCalendarControl(document.forms[0].invoicereceivedondate, 1);setTimeout('checkRecDate()',2000);"
                                                 alt="Show Calendar"></img>
                                           
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Invoice Amount</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="invoiceamount" id="invoiceamount"  onkeypress="return numbersonly(event);callAmount();"/>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Particulars of Invoice</td>
                                    <td class="table" align="left"> 
                                       <textarea name="Particularsinvoice" id="Particularsinvoice" cols="50"
                                         tabindex="6" rows="4"></textarea>
                                    </td>               
                        </tr>
                         <tr>
                                    <td class="table" width="40%" align="left">M-Book Date</td>
                                    <td class="table" align="left"> 
                                             <input type="text" name="mbookdate" id="mbookdate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);"/>
                                           <img src="../../../../../images/calendr3.gif"
                                                 onClick="showCalendarControl(document.forms[0].mbookdate, 1);setTimeout('checkBillDate()',2000);"
                                                 alt="Show Calendar"></img>
                                           

                                    </td>               
                        </tr>
                         <tr>
                                    <td class="table" width="40%" align="left">M-Book No</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="mbookno" id="mbookno"  onkeypress="return numbersonly(event)"/>
                                    </td>               
                        </tr>
                          <tr>
                                 <td class="table" align="left">M-Book Page No</td>
                                 <td class="table" align="left"> 
                                        <input type="text" name="mbookpageno" id="mbookpageno" onkeypress="return numbersonly(event)" onblur="loadAgreeNo()"/>
                                 </td>               
                        </tr> 
                    </table>
                    </div>
                    </div>
                    <div class="tab-page" id="gd" >
                    <h2 class="tab" > Part-2 </h2>
                    <div align="center">
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                    
                    
                    
                        <tr>
                            <td class="table" width="40%" align="left">Initiating Section(in case of HO only)</td>
                                    <td class="table" align="left"> 
                                            <select name="initiatingsection" id="initiatingsection" > 
                                                <option value="0">select</option>    
                                            </select>
                            		</td>               
                        </tr>
                      
                    <tr>
                                    <td class="table" width="40%" align="left">Agreement No</td>
                                    <td class="table" align="left"> 
                                           <select name="agreementno" id="agreementno" onblur="loadAgreeNoDetail()"  onkeypress="return numbersonly(event)" onchange="loadAgreeNoDetail()"> 
                                                <option value="">select</option>                                             
                                            </select>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Agreement Date</td>
                                    <td class="table" align="left"> 
                                            <input type="text" name="agreementdate" id="agreementdate" tabindex="3" readonly="readonly"
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);"/>
                                            <img src="../../../../../images/calendr3.gif"
                                             onClick="showCalendarControl(document.forms[0].agreementdate, 1);"
                                             alt="Show Calendar"></img>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Work Order No</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="workorderno" id="workorderno"  onkeypress="return numbersonly(event)" readonly="readonly"/>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Firm Name</td>
                                    <td class="table" align="left"> 
                                       <textarea name="firmname" id="firmname" cols="50" readonly="readonly"
                                         tabindex="6" rows="4"></textarea>
                                    </td>               
                        </tr>
                        <tr class="table">
                                <td>
                                      <div align="left">
                                        Head of Account for Expenditure
                                      </div>
                                </td>
                                <td>
                                      <div align="left">
                                        <input type="text" name="txtAcc_HeadCode" readonly="readonly"
                                               id="txtAcc_HeadCode" maxlength="6"
                                               onkeypress="return numbersonly(event)"
                                                onchange="sixdigit();" onblur="financialDate();"
                                               size="7"/>                                         
                                        <input type="text" name="txtAcc_HeadDesc"
                                               readonly="readonly" id="txtAcc_HeadDesc"
                                               style="background-color: #ececec"
                                               maxlength="250" size="70" />
                                        <div style="display:none">
                                        <input type="hidden" name="cmbSL_type" id="cmbSL_type" />
                                        <select name="cmbSL_Code" id="cmbSL_Code"></select>
                                        </div>
                                      </div>
                                </td>
                        </tr>
                    <!--
                        <tr>
                                 <td class="table" align="left">M-Book Page No</td>
                                 <td class="table" align="left"> 
                                        <input type="text" name="mbookpageno"  id="mbookpageno"/>
                                 </td>               
                        </tr>
                    	<%
                        if(oid==5000)
                        {
                        %>
                        <tr>
                            <td class="table" width="40%" align="left">Initiating Section(in case of HO only)</td>
                                    <td class="table" align="left"> 
                                            <select name="initiatingsection" id="initiatingsection" > 
                                                <option value="">select</option>    
                                            </select>
                            		</td>               
                        </tr>
                         <%
                         }
                         %>
                    
                        <tr class="table">
                                <td>
                                      <div align="left">
                                        Head of Account for Expenditure
                                        <font color="#ff2121">*</font>
                                      </div>
                                </td>
                                <td>
                                      <div align="left">
                                        <input type="text" name="txtAcc_HeadCode"
                                               id="txtAcc_HeadCode" maxlength="6"
                                               onkeypress="return numbersonly(event)"
                                                onchange="sixdigit();" 
                                                onblur="doFunction('checkCode','null');financialDate();" 
                                               size="9"/>
                                         
                                        <img src="../../../../../images/c-lovi.gif"
                                             width="20" height="20" alt="AccountHeadList"
                                             onclick="AccHeadpopup();"></img>
                                         
                                        <input type="text" name="txtAcc_HeadDesc"
                                               readonly="readonly" id="txtAcc_HeadDesc"
                                               style="background-color: #ececec"
                                               maxlength="250" size="90" />
                                        <div style="display:none">
                                        <input type="hidden" name="cmbSL_type" id="cmbSL_type" />
                                        <select name="cmbSL_Code" id="cmbSL_Code"></select>
                                        </div>
                                      </div>
                                </td>
                        </tr>
                         --><tr >
                                    <td class="table" width="40%" align="left" ><font color="#E60000">Budget Alloted</font></td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="budgetalloted" id="budgetalloted" readonly="readonly"/>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left"><font color="#E60000">Expenditure incurred so far</font></td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="expenditureincurred" id="expenditureincurred" readonly="readonly" />
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left"><font color="#E60000">Balance available</font></td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="balanceavailable" id="balanceavailable" readonly="readonly" />
                                    </td>               
                        </tr><!--
                        <tr>
                                    <td class="table" width="40%" align="left">Agreement No</td>
                                    <td class="table" align="left"> 
                                           <select name="agreementno" id="agreementno"> 
                                                <option value="">select</option>
                                            </select>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Agreement Date</td>
                                    <td class="table" align="left"> 
                                            <input type="text" name="agreementdate" id="agreementdate" tabindex="3" 
						                           maxlength="10" size="11"  
						                           onfocus="javascript:vDateType='3';"
						                           onkeypress="return calins(event,this);"/>
                                            <img src="../../../../../images/calendr3.gif"
                                             onClick="showCalendarControl(document.forms[0].agreementdate, 1);"
                                             alt="Show Calendar"></img>
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Work Order No</td>
                                    <td class="table" align="left"> 
                                           <input type="text" name="workorderno" id="workorderno" />
                                    </td>               
                        </tr>
                        <tr>
                                    <td class="table" width="40%" align="left">Firm Name</td>
                                    <td class="table" align="left"> 
                                       <textarea name="firmname" id="firmname" cols="50"
                                         tabindex="6" rows="4"></textarea>
                                    </td>               
                        </tr>
                        --><tr>
                                    <td class="table" width="40%" align="left">Remarks</td>
                                    <td class="table" align="left"> 
                                       <textarea name="remarks" id="remarks" cols="50"
                                         tabindex="6" rows="4"></textarea>
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
                            <td  colspan="2">
                                <div align="center"> 
                                        <input type="button" name="onadd" value="Add" id="onadd" onclick="add();" /> 
                                        <input type="button" name="onedit" value="Update" id="onedit" onclick="update();" disabled="disabled"/> 
                                        <input type="button" name="ondelete" value="Delete" id="ondelete" onclick="deleted();" disabled="disabled"/> 
                                        <input type="button" name="onlist" value="List" id="onlist" onclick="listpopup();" />  
                                 <!--       <input type="button" name="oncancel" value="Cancel" id="oncancel" onclick="cancel();" /> -->
                                        
                                        
                                         <input type="button" name="onclear" value="ClearAll" id="onclear" onclick="clearAll();" /> 
                       					 <input type="button" name="oncancel" value="Cancel" id="oncancel" onclick=" self.close();" /> 
                                </div> 
                           </td> 
                       </tr>
                     </table>
               </div>
            </form>
  </body>
</html>
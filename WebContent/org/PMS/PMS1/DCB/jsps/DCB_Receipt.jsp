<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>DCB Receipt System</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
   
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
  
    <script type="text/javascript" src="../scripts/dcb_check.js"></script>
    <script type="text/javascript" src="../scripts/DCB_Receipt.js"></script>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
     <!-- <script type="text/javascript" src="../scripts/jquery-1.3.2.min.js"></script> -->
     <script type="text/javascript" src="../scripts/jquery-3.6.0.min.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>     
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>     
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>    
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
    
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    
    <script type="text/javascript">
   		function count_check(a)
       {
       	var Cheque=new String(document.getElementById(a).value);
       	if (Cheque=="")
       	{
       		alert("Enter the Cheque No ");
       	}else if (Cheque.length < 6  )
       	{
       		   alert("Enter the Valid Cheque No ");
       		   document.getElementById(a).value="";  
       		   document.getElementById(a).focus();
       		   
       	}
       
       }
   </script>
    <!-- to avoid future date the above script used-->
       <script type="text/javascript" language="javascript">
       $( function()
				{
					$("#txtDraw_BR").blur(
						function () {
									   var beneficerytypeid=document.getElementById("dcb_ben_type").value;
	       							   var officeid=document.getElementById("cmbOffice_code").value;
	       							   var cno=document.getElementById("txtCheque_DD_NO").value;
	       							   var cdate=document.getElementById("txtCheque_DD_date").value;
	       							   var txtCrea_date=document.getElementById("txtCrea_date").value;
									   var xmlobj=createObject();
									   var url="../../../../../DCB_Receipt?command=prvddamt&officeid="+officeid+"&cno="+cno+"&cdate="+cdate+"&txtCrea_date="+txtCrea_date;
									 
								 		xmlobj.open("GET", url, true); 
										xmlobj.onreadystatechange = function() {
										ddamt(xmlobj );  
										}

										xmlobj.send(null);
			
							}
							);
					
				}

		 );

      function ddamt(xmlobj)
      {
     		if(xmlobj.readyState==4)
   				{ 
      			 if(xmlobj.status==200)
       	 			{  
           
           				var baseResponse=xmlobj.responseXML.getElementsByTagName("response")[0];
           				var tagcommand=baseResponse.getElementsByTagName("totamt")[0];
           				var totamt=tagcommand.firstChild.nodeValue;
           				var tablecmd=baseResponse.getElementsByTagName("tablecmd")[0];
           				var table=tablecmd.firstChild.nodeValue;
           				var rowc =baseResponse.getElementsByTagName("rowc")[0].firstChild.nodeValue;
           				if (parseInt(rowc) > 0) {
           				alert(table)  
           				   
           				 }
           				  
            		}
          	 }	
           
      }
      
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
                document.DCB_Receipt_Form.txtCrea_date.value=day+"/"+month+"/"+year;
                call_date(document.DCB_Receipt_Form.txtCrea_date);
     }
</script>
  </head>
  <body onload="call_clr();LoadAccountingUnitID_Create('LIST_ALL_UNITS');setTimeout('loadDate()', 300);foc();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="3">
          <div align="center">
            <font size="4">Creation Of DCB Bank Receipt </font>
          </div>
        </td>
        <td width="10%" align="right"><font size=1>Date Modified : 12-12-12</font></td>
      </tr>
    </table>
    
    <form name="DCB_Receipt_Form" id="DCB_Receipt_Form" method="POST" action="../../../../../DCB_Receipt?Command=Add"   onsubmit="return checkNull_DCB()">
      
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onChange="common_LoadOffice_New(this.value);">
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2"  >
                      
                      <%
               int old_offid=0;
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
                            
                            ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=? and old_office_id is not null and Date_Allowed_Upto>=?");
                             ps2.setInt(1,empid);
                             ps2.setDate(2, ctdate);
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
                            
                             ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                                             
                        rs2=ps2.executeQuery();
                        if(rs2.next())
                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("office_name")+"</option>");
                            
                            
                           /* ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
                            ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                            rs2=ps2.executeQuery();
                            if(rs2.next())
                            {
                              out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");                           
                            }*/
                            
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
                //System.out.println("bank details..."+bankID+" "+branchID+ " "+ bankAccNo+" "+bk_br_city);
                %>
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Receipt Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"  readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"    onblur="call_date(this); dateCheck(this);"/>
                    <!--        onblur="call_DCB_fze(this),call_date(this);"/> -->
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.DCB_Receipt_Form.txtCrea_date,1);"
                         alt="Show Calendar"></img> 
                    
                  </div>
                </td>
              </tr>
              
             <!-- <tr class="table" style="display:none">
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
              </tr>
              -->
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Receipt Number
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="hidden" name="txtReceipt_No" id="txtReceipt_No"  
                    style="background-color: #ececec"  readonly="readonly" size="6" maxlength="5"/>(System Generated)
                  </div>
                </td>
              </tr>
              <%
              String mod_id="MF004",CR_DR="DR";
                    String sql_bank="select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce (br.CITY_TOWN_NAME,'') as bk_br_city "+
                    " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? "+
                    " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.STATUS='Y' and curr.ac_operational_mode_id='COL'";
                    // here SL_NO=1 means that DEFAULT account number for that unit ..
                     System.out.println(sql_bank);
                     psbank=con.prepareStatement(sql_bank);
                     
                     psbank.setInt(1,unitid);
                     psbank.setString(2,mod_id);
                     psbank.setString(3,CR_DR);
                     rsbank=psbank.executeQuery();
                    if(rsbank.next())
                    {
                    System.out.println("inside if");
                     bankID=rsbank.getInt("BANK_ID");
                     branchID=rsbank.getInt("BRANCH_ID");
                     bankAccNo=rsbank.getLong("BANK_AC_NO");
                     AC_HEAD_CODE=rsbank.getInt("AC_HEAD_CODE");
                     bk_br_city=rsbank.getString("bk_br_city");
                        System.out.println("bank details..."+bankID+" "+branchID+ " "+ bankAccNo+" "+bk_br_city+" "+AC_HEAD_CODE);
                    }
                    psbank.close();
                    rsbank.close();
                    
              %>
              <tr class="table">
                <td>
                  <div align="left">Collection A/c Code</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCash_Acc_code" id="txtCash_Acc_code" value="<%=AC_HEAD_CODE%>"
                          style="background-color: #ececec"  readonly="readonly" maxlength="8" size="8"/>
                     <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountNumberList"
                             onclick="MainAccNopopup();"></img>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                   Bank Account Number
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  
                    <input type="text" name="txtBankAccountNo"  onkeypress="return numbersonly(event)" value="<%=bankAccNo%>"
                           id="txtBankAccountNo" maxlength="15"  size="15"  style="background-color: #ececec"  readonly="readonly" />
                   
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Bank Name
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankName" id="txtBankName" value="<%=bk_br_city%>"
                    style="background-color: #ececec"  readonly="readonly" size="50" maxlength="49"/>
                   <input type="hidden" name="txtBankId" value="<%=bankID%>"
                           id="txtBankId" size="5" maxlength="5"/>
                   <input type="hidden" name="txtBranchId"  value="<%=branchID%>"
                           id="txtBranchId" size="5" maxlength="5"/>
                  </div>                         
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left"> Reference Number & Date</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtRef_no" id="txtRef_no"  tabindex="4" 
                           maxlength="50" size="55"/>
                     
                    <input type="text" name="txtRef_date" id="txtRef_date"  tabindex="5"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="foc_new();return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.DCB_Receipt_Form.txtRef_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                    CR/DR Indicator
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="hidden" name="txtCR_DB" 
                           id="txtCR_DB" value="DR" size="2"/>
                    <input type="text" name="txtCR_DB_desc"
                          style="background-color: #ececec"  readonly="readonly" id="txtCR_DB_desc" value="DEBIT" size="6"/>
                  </div>
                </td>
              </tr>
                <tr class="table">
                <td width="40%">
               
                  <div align="left">Sub-Ledger Type</div>
                </td>
                <td >
                 <table border="0"><tr><td  >
                  <div align="left">
                    <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="6"
                            onchange="doFunction('Load_MasterSL_Code',this.value);">
                      <option value="14">DCB Beneficiary</option>
                      
                    </select>
                 </div> 
              </td><td  >  
                    <select size="1" name="dcb_ben_type" id="dcb_ben_type" tabindex="7"  onchange="doFunction('Load_SL_Code',this.value);" onblur="focus_from_type()" > 
                      <option value="">--Select Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("BEN_TYPE_ID")+">"+rs.getString("BEN_TYPE_DESC")+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in Reason combo..."+e);
                        }
                        finally
                        {
                        rs.close();
                        ps.close();
                        }   
                      %>
                    </select>&nbsp;&nbsp;&nbsp;Select Union
                	   	<select id="Block_sno" onchange="block_wise_ben_list(this.value);  " tabindex="8"  >
                			<option value="0">Select Union Name</option>
                		</select>   
                 </td>
                 </tr></table>
                </td>
              </tr>
              <tr class="table">   
                <td width="40%">
                  <div align="left">Sub-Ledger Code</div>
                </td>
                <td width="60%">
                    <table align="left">
                     <tr align="left">  
                     <td>
                          <div align="left">
                                <!-- onchange="loadName_Mas(this);"  -->
                                <select size="1" name="cmbMas_SL_Code"  id="cmbMas_SL_Code" tabindex="9"  onchange="loadName_Mas(this);" >
                                  <option value="">--Select Code--</option>
                                </select>
                          </div>
                      </td>
                      <td>
                          <div align="left" id="offlist_div_master"  style="display:none">
                         	   <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                          	  <input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"  onblur="mas_office(this.value);" />
                          </div>
                         <div align="left" id="emplist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_master();"></img>
                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="5" size="5"  onblur="mas_employee(this.value);" />
                          </div>
                       </td>
                    </tr>
                   </table>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="10"  onkeypress="return check_leng(this.value,'remarks');"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">
                    Received From
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtRecei_from" tabindex="11" onkeypress="return check_leng(this.value,'received_from');"
                           id="txtRecei_from"  size="50"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Total Amount (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount"  onkeypress="return limit_amt(this,event);" onblur="tab_move('rem_current_month')" tabindex="10" onblur="valid_amt(this);"
                           id="txtAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left" >
                    Remittance in Current Month ? 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="rem_current_month" id="rem_current_month" value="Y" checked="checked"/>YES&nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="rem_current_month" id="rem_current_month" value="N" />NO &nbsp;&nbsp;&nbsp;&nbsp; 
                  </div>
                </td>
              </tr>  
              <tr class="table">
                <td>
                  <div align="left" >
                    <b>Do you want to reclassify this voucher after remitting into bank ? </b>
               	     <font color="#ff2121" face="Arial Black">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="rad_ReClass" id="rad_ReClass" value="Y"  />YES&nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="rad_ReClass" id="rad_ReClass" value="N" checked="checked" />NO &nbsp;&nbsp;&nbsp;&nbsp; 
                  </div>                   
                </td>  
              </tr>
            </table>
          </div>
        </div>
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                    <table cellspacing="1" cellpadding="2" border="1" width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong> Details</strong>
                          </div>
                        </td>
                      </tr>                     
                      <tr class="table">
                		<td>
                  			<div align="left">
                    			Account Head Code 
                    		<font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                   <select size="1" name="acheadcode" id="acheadcode" >
                                        
                                  <option value="">--Select Code--</option>
                                </select>
                     
                  </div>
                </td>
              </tr>
             
                    <tr class="table">
                <td>
                  <div align="left">
                    CR/DR
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR" 
                           checked="checked" value="CR"/>Credit
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR" 
                           value="DR"/>Debit &nbsp;&nbsp;&nbsp;&nbsp; 
                    
                  </div>
                </td>
              </tr>
                      <tr class="table">
                        <td width="40%">
                          <div align="left">Sub-Ledger Type <font color="#ff2121">*</font>  </div>
                        </td>
                        <td width="60%">
                          <div align="left">
                           <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="detailssubled()">
                              <option value="">--Select Type--</option>
                             <option value="10">Project</option>
                           
                            </select>
                          </div>
                        </td>
                      </tr>
                      
                      <tr class="table">
                        <td width="40%">
                          <div align="left">Sub-Ledger Code <font color="#ff2121">*</font>  </div>
                        </td>
                        <td width="60%">
                          <table align="left">
                                 <tr align="left">
                                 <td>
                                      <div align="left">
                                            <select size="1" name="cmbSL_Code" id="cmbSL_Code" onchange="projectmapping()" >
                                                    
                                              <option value="">--Select Code--</option>
                                            </select>
                                      </div>
                                  </td>
                                  <td>
                                      <div align="left" id="offlist_div_trans"  style="display:none">
                                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList"  onclick="jobpopup_trans();"></img>
                                        <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"  onblur="trs_office(this.value);" />
                                      </div>
                                      <div align="left" id="emplist_div_trans"  style="display:none">
                                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
                                        <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="trs_employee(this.value);" />
                                     </div>
                                     <input type="hidden" name="projectid" id="projectid" />
                                   </td>
                                 
                                </tr>
                          </table>
                          
                        </td>
                      </tr>
                      
               <tr class="table">
                <td>
                  <div align="left">
                    Cheque/DD
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD" value="C"/>Cheque &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD"  checked="checked" value="D" />DD &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD" value="E"/>ECS                   
                    
                  </div>
                </td>
              </tr>        
               <tr class="table">
                <td>
                  <div align="left" id="ecs_hide" >
                    Cheque/DD Number
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left" id="ecs_hide" >
                    <input type="text" name="txtCheque_DD_NO" maxlength="10" onkeypress="return numbersonly(event)" onblur="count_check('txtCheque_DD_NO');"
                           id="txtCheque_DD_NO" size="11"/>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left" id="ecs_hide" >
                    Cheque/DD Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left" id="ecs_hide" >
                     <input type="text" name="txtCheque_DD_date" id="txtCheque_DD_date"  
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="Check_Date(this.value);return checkdt(this);"/>
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.DCB_Receipt_Form.txtCheque_DD_date,0,2);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              
             <tr class="table">
                <td>
                  <div align="left">
                   Bank Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBank_Name"
                           id="txtBank_Name" size="27" maxlength="25"/>
                  </div>
                </td>
              </tr>
              
                 <tr class="table">
                <td>
                  <div align="left">
                    Drawee Branch
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtDraw_BR"
                           id="txtDraw_BR" size="27" maxlength="25"/>
                  </div>
                </td>
              </tr>
                 <tr class="table">
                <td>
                  <div align="left">
                    Bank MICR code

                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBank_M_Code" onkeypress="return numbersonly(event)"
                           id="txtBank_M_Code" size="20" maxlength="15"/>
                  </div>
                </td>
              </tr>
                 <!--<tr class="table">
                <td>
                  <div align="left">
                    Received From
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtsub_Recei_from"
                           id="txtsub_Recei_from" size="50"/>
                  </div>
                </td>
              </tr>-->
              <tr class="table">
                <td>
                  <div align="left">
                     Amount 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtsub_Amount" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                           id="txtsub_Amount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtParticular" id="txtParticular" cols="70"  onkeypress="return check_leng(this.value,'particulars');"
                              rows="3"></textarea>
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
                        <th >A/c Head Code</th>
                        <th >CR/DR</th>
                        <th>Sub Ledger Type</th>
                        <th >Sub Ledger Code</th>
                           <!-- <th style="display:none" >Cheque/DD</th>    -->
                        <th >Cheque/DD No.</th>
                         <th >Cheque/DD Date</th>
                       <!--  <th style="display:none" >Bank Name</th>
                         <th style="display:none"> Drawee Branch</th>
                         <th style="display:none"> Bank MICR code</th>
                        <th style="display:none" >Received From</th>-->
                        <th >Amount</th>
                        <!--<th style="display:none">Particulars</th>-->
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
    </form></body>
</html>
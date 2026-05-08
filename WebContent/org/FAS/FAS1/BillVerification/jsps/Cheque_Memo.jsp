<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Cheque Memo</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
   
    <script type="text/javascript" src="../scripts/Cheque_Memo.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>


    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
 
    <script type="text/javascript" src="../scripts/Common_ReceiptType.js"></script>
   
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>     
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>     
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
  
   <script type="text/javascript" language="javascript">
         
         function loadDate()
         {
      // alert("jjj");
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
        document.cheque_memo.check_memo_Year.value=year;  
        document.cheque_memo.vochardate.value=day+"/"+month+"/"+year;
        document.cheque_memo.txtCheque_DD_date.value=day+"/"+month+"/"+year;
        
        
         } 
        
   function particue()
   {
	   if(document.getElementById("memotype").value==2){
		   document.getElementById("particulars").value="Paid to Bank by Yourself Cheque No. " +document.getElementById("txtCheque_DD_NO").value+" , Dated "+document.getElementById("txtCheque_DD_date").value;
	   }else if(document.getElementById("memotype").value==7)
	   {
		  //alert(document.getElementById("txtEmpId").options[document.getElementById("txtEmpId").selectedIndex].text); 
		   document.getElementById("particulars").value="Paid to "+document.getElementById("txtEmpId").options[document.getElementById("txtEmpId").selectedIndex].text+" Cheque No. " +document.getElementById("txtCheque_DD_NO").value+" , Dated "+document.getElementById("txtCheque_DD_date").value;
	   }
   }
</script>
   </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Cheque Memo</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="cheque_memo" id="cheque_memo" action="../../../../../Cheque_Memo?command=Add"  method="post" onsubmit="return nullcheck();">
      
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
             <tr class="table">
		            <td>
		                <div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div>
		            </td>
		            <td><div align="left">
		                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onChange="common_LoadOffice(this.value);">
		                
		                </select>
		                </div>
		            </td>
		        </tr>
				<tr class="table">
					<td>
			                    <div align="left">Accounting For Office Code <font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left">
			                    <select size="1" name="cmbOffice_code"
			                            id="cmbOffice_code" onchange="setTimeout('dateCheck(document.cheque_memo.vochardate);',300)">
			                    </select>
			                    </div>		
			                </td>
				</tr>
            
            
            
            <!--
              
               <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>
                    
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                            tabindex="1">
                       <option value="0"> Select Account Unit </option>
                          <%
                          String mod_id="MF005",CR_DR="CR";
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2"  >
                      
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
                           // ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
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
                //System.out.println("bank details..."+bankID+" "+branchID+ " "+ bankAccNo+" "+bankName);
                %>
                    </select>
                  </div>
                </td>
              </tr>
                        
                --><tr class="table">
                <td>
                  <div align="left">Check Memo Type</div>
                </td>
                <td>
                  <div align="left">
                   <select size="1" name="memotype" id="memotype" onchange="call('getcode')">   
                   <option value="">--Select Memo Type--</option>  
                                                        
                   <%    ps=con.prepareStatement("select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST where STATUS='L'");
                   		rs=ps.executeQuery();
                   		while(rs.next())
                   		{
                   		 out.println("<option value="+rs.getInt("CHEQUE_MEMO_TYPE_CODE")+">"+rs.getString("CHEQUE_MEMO_DESC")+"</option>");	
                   		}
                   
                		   %>
                		   </select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
		<td>
		<div align="left">Cheque Memo Year<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="check_memo_Year" size="10" onchange="new_year();"
			id="check_memo_Year" onKeyPress="return numbersonly(event,this)"></div>
			<input type="hidden" id="majortype" ></input>
			<input type="hidden" id="minortype" ></input>
			<input type="hidden" id="subtype"></input>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Cheque Memo Month<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="check_memo_Month" id="check_memo_Month" onchange="call('get')">
			<option value="s">---Select---</option>
			<option value="01">January</option>
			<option value="02">February</option>
			<option value="03">March</option>
			<option value="04">April</option>
			<option value="05">May</option>
			<option value="06">June</option>
			<option value="07">July</option>
			<option value="08">August</option>
			<option value="09">September</option>
			<option value="10">October</option>
			<option value="11">November</option>
			<option value="12">December</option>
		</select></div>
		</td>
	</tr>
               
              <tr class="table">
                <td>
                  <div align="left">Payee Type<font color="#ff2121">*</font></div>
                </td>
                <td>
                  <div align="left">
                  <select name="payeetype" id="payeetype" >
                 <option value="">--Select--</option>
                                                    
                  
                 </select>                      
                       
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Payee Code  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <select name="txtEmpId" id="txtEmpId" onchange="call('getdet')" >
                 <option value="">--Select--</option>
                </select> 
                  </div>
                </td>
              </tr>
              
                <tr class="table">
                <td>
                  <div align="left">Voucher Date </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" id="vochardate" name="vochardate" 
                       onfocus="javascript:vDateType='3';"  onkeypress="return calins(event,this);"  
                       onblur="testfutureDate();dateCheck(this);"
            		 maxlength="10" size="11">   
                         
                  </div>
                </td>
              </tr>
              
             
              <%
                    
                    String sql_bank="select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || br.CITY_TOWN_NAME as bk_br_city "+
                    " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? "+
                    " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID";
                    // here SL_NO=1 means that DEFAULT account number for that unit ..
                     System.out.println(sql_bank);
                    psbank=con.prepareStatement(sql_bank);
                    System.out.println("unitid..."+unitid+" "+mod_id+ " "+ CR_DR);            
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
			          <div align="left">
			              Operation A/c Code  <font color="#ff2121">*</font>
			            
			          </div>
			      </td>
			      <td>
			          <div align="left">			              
			              <input type="text" name="txtCash_Acc_code" class="light" id="txtCash_Acc_code" value="<%=AC_HEAD_CODE%>" style="background-color: #ececec"  readonly="readonly" maxlength="8" size="9" />
                          <img src="../../../../../images/c-lovi.gif" width="20" 
				                             height="20" alt="AccountNumberList"
				                             onclick="MainAccNopopup();"></img>
			          </div>
			      </td>
			   </tr>               
			  
			  
			   <tr class="table">
                <td>
                  <div align="left">Bank Account Number  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  	  <input type="text" name="txtBankAccountNo" class="light" value="<%=bankAccNo%>"  id="txtBankAccountNo" size="15" style="background-color: #ececec"  readonly="readonly" />                                                  
                  </div>
                </td>
              </tr>
			 
			 
			  <tr class="table">
                <td>
                  <div align="left">Bank Name</div>
                </td>
                <td>
                  <div align="left">                     
                    <input type="text" class="light" name="txtBankName"  id="txtBankName" value="<%=bk_br_city%>" readonly="readonly" size=50/>
                    <input type="hidden" name="txtBankID" id="txtBankID" value="<%=bankID%>"></input>
                    <input type="hidden" id="txtBranchID" name="txtBranchID" value="<%=branchID%>"></input>
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
                  <input type="hidden" class="light" name="txtCR_DB" id="txtCR_DB" value="CR" size="2"/>
                    <input type="text" name="txtCR_DB_desc" style="background-color: #ececec"  readonly="readonly" id="txtCR_DB_desc" value="CREDIT" size="6"/>
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">
                    Cheque Number 
                            <font color="#ff2121">
                              *
                            </font>
                          </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCheque_DD_NO" maxlength="10" onkeypress="return numbersonly(event)" 
                    onblur="check_dd_cheque_deletecp(),chequeRange();"
                           id="txtCheque_DD_NO" size="11" onchange="particue();"/>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Cheque Date 
                            <font color="#ff2121">
                              *
                            </font>
                          </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="txtCheque_DD_date" class="light" id="txtCheque_DD_date"  readonly="readonly"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                   
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td >
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="particulars" readonly="readonly" class="light" id="particulars" cols="50" tabindex="7" onkeypress="return check_leng('particulars',this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Total Cheque Amount (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount" class="light" readonly="readonly"  tabindex="8" onkeypress="return limit_amt(this,event);"  onblur="valid_amt(this);"
                           id="txtAmount" maxlength="17" size="18"/>
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
                    
                    </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                      <tr class="table">
                    <th>  <div id="selDiv" style="display: block;"><a>Select</a></div>
                      <div id="selDiv1" style="display: none;"><a href="javascript:unselect();">UnSelect</a></div></th>
                    
                    <!--  <th >Select</th>   -->
                     <th >Bill No</th>
                        <th >Bill Date</th>
                        <th >PassOrder Amt</th>
                        <th >A/c Head Code</th>
                         <th >SL Type</th>
                         <th >SL Code</th>
                         <th >CR/DR</th>
                         <th >DR Amount</th>
                         <th >Remarks</th>
                         <th >MTC Date/Approval Date</th>
                        
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
              <div align="center" id="firid" style="display:block">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="exit();"/>
              </div>
              <div align="center" id="secid" style="display:none">
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
    
    </body>
</html>
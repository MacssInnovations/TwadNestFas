<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Imprest/Temp.Advance A/c Payment System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script> 
    <script type="text/javascript"
    		src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script> 
    <script type="text/javascript"
    		src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Operation_AC_Heads.js"></script>    
    		   		 				    		
    <script type="text/javascript" src="../scripts/Imprest_Account_Edit.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   
     <!-- to avoid future date the above script used-->
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
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
             document.imprest_account.txtCrea_date.value=day+"/"+month+"/"+year;
          //   doFunction_voucher('load_Voucher_No');
          //   alert(document.imprest_account.txtCrea_date.value); 
             //document.imprest_account.txtCB_Year.value=year;            
             //document.imprest_account.txtCB_Month.value=monthArray[today.getMonth()];
             //setTimeout('doFunction_voucher("load_Voucher_No")',10000);
        }

        function checkNull()
        {
                //alert("tbody");
        		var tbody=document.getElementById("grid_body");
                if(document.getElementById("cmbAcc_UnitCode").value=="")
                {
        	            alert("Select the Account Unit code");
        	            return false;    
                }
                if(document.getElementById("cmbOffice_code").value=="")
                {
        	            alert("Select the Office Code");           
        	            return false;
                }
                if(document.getElementById("txtCrea_date").value.length==0)
                {
        	            alert("Enter the Date of Creation");           
        	            return false;    
                }
                if(document.getElementById("cmbPayment_type").value=="")
	    	    {
    	                alert("Select Journal Type");           
    	                return false;    
	    	    }    
                if(document.getElementById("txtVoucher_No").value.length==0)
                {
        	            alert("select Voucher Number");           
        	            return false;    
                }        
                if(document.getElementById("txtCash_Acc_code").value.length==0)
                {
        	            alert("Enter the Operation A/c Code");           
        	            return false;    
                }    
                if(document.getElementById("txtBankAccountNo").value=="")
                {
        	            alert("Select Bank Account Number");           
        	            return false;    
                }    
                if(document.getElementById("txtOfficeId").value.length==0)
                {
        	            alert("Select Office Id");           
        	            return false;    
                }            
                if(document.getElementById("txtTotalAmt").value.length==0)
                {
        	            alert("Enter the Total Amount in General");          
        	            return false;    
                }
                if(tbody.rows.length==0)
                {
        	            alert("Enter the Details Part");         
        	            return false; 
                }
                if(tbody.rows.length>0)
                {
                        var check_amt=0;
                        rows=tbody.getElementsByTagName("tr");
                        for(i=0;i<rows.length;i++)
                        {
							 
	                          var cells=rows[i].cells;	                         
                              check_amt=parseFloat(check_amt) + parseFloat(cells.item(4).firstChild.value);	                	                
                          
                        }                            
                        if(parseFloat(document.getElementById("txtTotalAmt").value)!=parseFloat(check_amt))
                        {
        	                  alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtTotalAmt").value)-parseFloat(check_amt)))
        	                  return false; 
                        }
                }
               
                document.getElementById("num_rows").value=tbody.rows.length;

                //setTimeout("LoadOprAccountHead('CR','MF031','OPR')",1200);
                //return true;
                
        }
       
</script>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
   <%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      Statement st=null;
		      String ac_head_name="",ac_head="";
		      int ac_head_code=820103;
		      PreparedStatement ps=null;
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
		      try
		      {
		      		
		      		st=con.createStatement();
		      		rs=st.executeQuery("select account_head_code,account_head_desc from com_mst_account_heads where account_head_code="+ac_head_code);
		      		if(rs.next())
		      		{
		      			ac_head=rs.getString("account_head_code")+" - "+rs.getString("account_head_desc");
		      			ac_head_name=rs.getString("account_head_desc");
		      		}
		      		rs.close();
		      			
		      }
		      catch(Exception e)
		      {
		    	    System.out.println("Err in selecting acc head code:::"+e.getMessage());
		      }
		      
		      
      		 HttpSession session=request.getSession(false);
      		 UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
             int empid=empProfile.getEmployeeId();
             System.out.println("employee id:"+empid);
             int oid=0;
             try
    	     {
    	           
    	            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
    	            ps.setInt(1,empid);
    	            rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                        	oid=rs.getInt("OFFICE_ID");
                    }
    	            rs.close();
    	              	                 
    	     }
    	     catch(Exception e)
    	     {
    	        	System.out.println(e);
    	     }
             out.println("<input type='hidden' name='LoginOffice' id='LoginOffice' value="+oid+">");
             
      
		      
  %>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');clrForm('load');loadDate();doFunction('checkCode','null');loaddrawlPurpose();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Edit Imprest/Temp.Advance A/c Payment System</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="imprest_account" id="imprest_account" method="POST"
                  action="../../../../../Imprest_Account_Edit?Command=Add" onsubmit="return checkNull()">
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" > General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="table">
                <td>
                  <div align="left" >
                  	  Accounting Unit Code  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);LoadBankAccountNumber();">
    
                     </select>
                  </div>
                </td>
              </tr>


              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                    </select>
                  </div>
                </td>
              </tr>
              
               <tr class="table">
	                <td>
	                  <div align="left">
	                    Date
	                    <font color="#ff2121">*</font>
	                  </div>
	                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return call_date(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.imprest_account.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                       
                  
                  </div> 
                </td>
              </tr>
              
              <tr class="table">
	                <td width="30%">
	                  <div align="left">
	                    Advance Type
	                    <font color="#ff2121">*</font>
	                  </div>
	                </td>
	                <td>
	                  <div align="left">	                  	
	                    <select name="cmbPayment_type" id="cmbPayment_type" tabindex="4" onchange="loadAccountHead();loaddrawlPurpose();doFunction_voucher('load_Voucher_No');hideRecoup();">
                                <option value="" selected>select</option>
                                <option value="I">Imprest </option>
                                <option value="T">Temporary Advance</option>
                                
                       </select>                     
                  </div>
                </td>
              </tr>
			  
			   
			   <tr class="table">
		          <td>
			          <div align="left">
			              Voucher Number
			          </div>
			      </td>
			      <td>
			          <div align="left">
			              <select name="txtVoucher_No" id="txtVoucher_No" tabindex="5" onchange="doFunction_voucher('load_Voucher_Details')">
			              		  <option value="">Select Voucher Number</option>
			              </select>                                                                           
			          </div>
			      </td>
			   </tr>       
			   
			   
			    <tr class="table">
		          <td>
			          <div align="left">
			              Operation A/c Code
			          </div>
			      </td>
			      <td>
			          <div align="left">			              
			              <input type="text" name="txtCash_Acc_code" id="txtCash_Acc_code"  style="background-color: #ececec"  readonly="readonly" maxlength="8" size="9"/>
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
                  	  <input type="text" name="txtBankAccountNo"  id="txtBankAccountNo" size="15" style="background-color: #ececec"  readonly="readonly" />                                                  
                  </div>
                </td>
              </tr>
			 
			 
			  <tr class="table">
                <td>
                  <div align="left">Bank Name</div>
                </td>
                <td>
                  <div align="left">                     
                    <input type="text" name="txtBankName" tabindex="7" id="txtBankName" size=50/>
                    <input type="hidden" name="txtBankID" id="txtBankID"></input>
                    <input type="hidden" id="txtBranchID" name="txtBranchID"></input>
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">CR/DR Indicator</div>
                </td>
                <td>
                  <div align="left">                     
                    <input type="text" name="cr_dr_indicator" id="cr_dr_indicator" tabindex="8" value="CREDIT" readonly="readonly"/>
                  </div>
                </td>
              </tr>
                                   
              <tr class="table">
                <td>
                  <div align="left">Office Id</div>
                </td>
                <td>
                  <div align="left">
                   	 <input type="text" name="txtOfficeId" maxlength="5" size="7" id="txtOfficeId" onchange="loadOfficeDetails()"  onkeypress="return numbersonly(event,this)" tabindex="9"/>
                     <img src="../../../../../images/c-lovi.gif"
                                 width="20" height="20" alt="Office List"
                                 onclick="servicepopup_mst();"></img>
                   
                  </div>                  
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left"> Office Name</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" size=40 id="txtOfficeDet" name="txtOfficeDet" tabindex=10 readonly="readonly"/>
                  </div>
                </td>
              </tr>   
              
              
              <tr class="table">
	                <td>
	                  <div align="left">Remarks</div>
	                </td>
	                <td>
	                  <div align="left">
	                    <textarea name="txtRemarks" id="txtRemarks" cols="70" onkeypress="return check_leng(this.value);"
	                              rows="3" tabindex="13"></textarea>
	                  </div>
	                </td>
		      </tr> 
                    
                    
              <tr class="table">
                <td>
                  <div align="left">
                     Total Amount  <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtTotalAmt" id="txtTotalAmt" tabindex="14"  maxlength="16" onkeypress="return filter_real(event,this,10,2)" ></input>
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
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
                      
			             <tr class="table">
			                <td>
			                  <div align="left">
			                    Account Head Code 
			                    <font color="#ff2121">*</font>
			                  </div>
			                </td>
			                <td>
			                  <div align="left">
			                    <input type="text" name="txtAcc_HeadCode" tabindex="15" readonly="readonly"
			                           id="txtAcc_HeadCode" maxlength="6" value="<%=ac_head_code%>"
			                           size="9"/>
			                    
			                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" value="<%=ac_head_name%>"
			                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
			                    <input type="hidden" name="num_rows" id="num_rows"></input>
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
			                  	<input type="text" name="rad_sub_CR_DR" id="rad_sub_CR_DR" tabindex="16" value="DEBIT" readonly="readonly"/>			                    			                   
			                  </div>
			                </td>
			              </tr>


			              <tr class="table">
			                 <td width="40%">
			                  	<div align="left">Sub-Ledger Type  <font color="#ff2121">*</font></div>
			                 </td>
			                <td width="60%">
			                  <div align="left">
			                   <select size="1" name="cmbSL_type" id="cmbSL_type" tabindex="17" onchange="doFunction('Load_SL_Code',this.value);">
			                      <option value="">--Select Type--</option>			                     
			                    </select>
			                   </div>
				              <div align="left" style="display:none">
			                   <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type" >
			                      <option value="">--Select Type--</option>			                     
			                    </select>
			                   </div>
			                </td>
			             </tr>
              
			             <tr class="table">
				              <td width="40%">
				                  <div align="left">Sub-Ledger Code <font color="#ff2121">*</font></div>
				              </td>
				              <td width="60%">
						            <table align="left">
						             <tr align="left">
						             <td>
						                  <div align="left">
						                        <select size="1" name="cmbSL_Code" id="cmbSL_Code" tabindex="18">						                                
						                          <option value="">--Select Code--</option>						                          
						                        </select>
						                  </div>						                 
						                  <div align="left" style="display:none">
							                    <select  name="cmbMas_SL_Code" id="cmbMas_SL_Code" >
							                      		<option value="">--Select Code--</option>
							                     </select>
							              </div>
						              </td>
						              <td>
						                  <div align="left" id="offlist_div_trans"  style="display:none">
						                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_trans();"></img>
						                    <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"    onblur="trs_office(this.value);" />
						                  </div>
						                  <div align="left" id="emplist_div_trans"  style="display:none">
						                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="emp_servicepopup();"></img>
						                            <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="load_employee(this.value);" />
						                  </div>
						               </td>
						             
						            </tr>
						           </table>
				        	  </td>
		              </tr>
		              
		              
		              <tr class="table">
			                <td>
			                  <div align="left">
			                     Paid To 
			                    <font color="#ff2121">*</font>
			                  </div>
			                </td>
			                <td>
			                  <div align="left">
			                    <input type="text" name="txtPaidTo" id="txtPaidTo" disabled="disabled" tabindex=19 maxlength="25" size="30"/>
			                  </div>
			                </td>
		              </tr>
		              
		              
		              <tr class="table">
			                <td>
			                  <div align="left">Ref.Number</div>
			                </td>
			                <td>
			                  <div align="left">
			                    	<input type="text" name="ho_ref_no" id="ho_ref_no"  maxlength="8" size="18" onkeypress="return numbersonly(event,this)"/>		                             
			                  </div>
			                </td>
		              </tr>
		              
		              <tr class="table">
			                <td>
			                  <div align="left">Ref.Date</div>
			                </td>
			                <td>
			                  <div align="left">
			                    	<input type="text" name="ho_ref_date" id="ho_ref_date" maxlength="10" size="11"  
			                        	   onfocus="javascript:vDateType='3';"
				                           onkeypress="return calins(event,this);"
				                           onblur="return checkdt(this);"/>
	                           		<img src="../../../../../images/calendr3.gif"
					                       onclick="showCalendarControl(document.imprest_account.ho_ref_date,0);"
					                       alt="Show Calendar"></img>        	                            
						       </div>
			                </td>
		              </tr>
		              
           
		              <tr class="table">
			                <td>
			                  <div align="left">
			                     Amount 
			                    <font color="#ff2121">*</font>
			                  </div>
			                </td>
			                <td>
			                  <div align="left">
			                    <input type="text" name="txtsub_Amount" onkeypress="return filter_real(event,this,10,2)" onblur="valid_amt(this);account_head_code()"
			                           id="txtsub_Amount" maxlength="17" size="18"/>
			                  </div>
			                </td>
		              </tr>
		              
		              <tr class="table">
			                <td>
			                  <div align="left">Cheque/DD</div>
			                </td>
			                <td>
			                  <div align="left">
			                     <input type="radio" id="cd" name="cd" checked="checked"> Cheque
			                     <input type="radio" id="cd" name="cd"> DD
			                  </div>
			                </td>
		              </tr>
		              
		              <tr class="table">
			                <td>
			                  <div align="left">
			                    Cheque/DD Number 
			                    <font color="#ff2121">*</font>
			                  </div>
			                </td>
			                <td>
			                  <div align="left">                   
			                    <input type="text" name="txtCheque_DD_NO" id="txtCheque_DD_NO" maxlength="17" size="18" onkeypress="return numbersonly(event,this)" onblur="chequeRange()" />                   
			                  </div>
			                </td>
		              </tr>
		              
		              
		              <tr class="table">
			                <td>
			                  <div align="left">
			                   Cheque/DD Date
			                    <font color="#ff2121">*</font>
			                  </div>
			                </td>
			                <td>
			                  <div align="left">
			                    <input type="text" name="txtDDDate" id="txtDDDate" tabindex="3" 
			                           maxlength="10" size="11"  
			                           onfocus="javascript:vDateType='3';"
			                           onkeypress="return calins(event,this);"  />                           
			                        <!--    onblur="call_date(this);"  -->                           
			                          
			                     <img src="../../../../../images/calendr3.gif"
			                         onclick="showCalendarControl(document.imprest_account.txtDDDate,0);"
			                         alt="Show Calendar"></img> 
			                  
			                  </div>
			                </td>
			          </tr>
			          
			           <tr class="table">
			                <td>
			                  <div align="left"> Purpose of drawl</div>
			                </td>
			                <td>
			                  <div align="left">
			                     <select id="drawl_purpose" name="drawl_purpose" tabindex="25">
			                     	<option value="">-Select--</option>			                     	
			                     </select>
			                  </div>
			                </td>
			           </tr>   
              
		               <tr class="table">
			                <td>
			                  <div align="left">Payment Type</div>
			                </td>
			                <td>
			                   <div id="newId" align="left" style="display:block">
			                     <input type="radio" id="payment_type" name="payment_type" checked="checked" onclick="checkRecoup()"> New
			                    </div>
			                    <div id="recoupId" align="left" style="display:block">
			                     <input type="radio" id="payment_type" name="payment_type" onclick="checkRecoup()"> Recoup
			                    </div>
			                </td>
		              </tr>		              		               
	              
		              <tr class="table">
			                <td>
			                  <div align="left"> Particulars</div>
			                </td>
			                <td>
			                  <div align="left">
			                     <textarea rows="4"  tabindex="8" cols="50" id="particulars" name="particulars"></textarea>
			                  </div>
			                </td>
			           </tr>   
			           
			           <tr>
			           		<td colspan="2">
			           			 <div id="recoupDiv" style="display:none">
			                  			<table id="recoupTable" cellspacing="3" cellpadding="2"  border="1" width="100%">
			                  			
			                  				  <tr>
								           		    <td colspan="6" class="tdH">
								           			 		Recoup Details 
								           		    </td>
								              </tr>	
								              		                  	                      
						                      <tr class="table">
							                        <th >Select</th>                                               
							                        <th>Voucher No.</th>
							                        <th>Sl.No.</th>
							                        <th>Voucher Date</th>  
							                        <th>Purpose of Drawal</th>     					                        	                      
							                        <th>Amount</th>
						                      </tr>
						                      
						                      <tbody id="recoup_body" class="table" align="left" >
						                      </tbody>
						                    
			                    		</table>
			                  </div>
			                </td>
			           </tr>        
			              
		              
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd" id="cmdadd"
                                 value="ADD" onclick="load_grid('ADD_GRID')" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick="load_grid('update_GRID')"
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
                        <th>Sub Ledger Type</th>
                        <th >Sub Ledger Code</th>     
                        <th >Ref.No</th>                       
                        <th >Amount</th>
                        <th >Cheque/DD</th>
                        <th >Cheque/DD Number</th>
                        <th >Cheque/DD Date</th>
                        <th >Payment Type</th>
                        <th >Purpose of drawl</th>                        
                        <th >Recouped Voucher No. </th>
                        <th >Recouped Voucher Date </th>
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
                       onclick="clrForm('cancel');"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
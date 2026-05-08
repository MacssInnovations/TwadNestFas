<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Post TCA System</title>
    
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script>		   		 				    		
    <script type="text/javascript" src="../scripts/SingleEmployee_Many_units.js"></script>
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
              //   document.TCA_POST.txtCrea_date.value=day+"/"+month+"/"+year; 
                 document.TCA_POST.txtPayment_date.value=day+"/"+month+"/"+year;   
                 setTimeout('doFunction_voucher("load_voucher_no")',900);
        }
        function checkNull()
        {     

           // alert("enter into Check null");          
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
                if(document.getElementById("txtVoucher_No").value=="")
                {
        	            alert("Select Payment Voucher No.");           
        	            return false;    
                }      
                if(document.getElementById("txtCrea_date").value.length==0)
                {
        	            alert("Enter the Date of Creation");           
        	            return false;    
                }                    
                               
                if(document.getElementById("txtTotalAmt").value.length==0)
                {
        	            alert("Enter TDA Total Debit Amount");          
        	            return false;    
                }
                if(tbody.rows.length==0)
                {
        	            alert("Enter the Details Part");         
        	            return false; 
                }
               
               
              
                else
                {
                  //  alert("Enter into details part");
                	var credit_amount=0;
                	var debit_amount=0;
                	var tot_amount=0;
                	 rows=tbody.getElementsByTagName("TR");       
                	 //alert(rows);
                     for(i=0;i<rows.length;i++)
                     {
                    	// alert(rows.length);
                                 var cells=rows[i].cells;      
                                 tot_amount=tot_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                                 if(cells.item(2).lastChild.nodeValue=='CR')
                                 {
                                	  credit_amount=credit_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                                 }
                                 else
                                 {
                                	 debit_amount=debit_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                                 }
                }
                    
                     
                     if(credit_amount!=debit_amount)
                     {
                    	 alert("Debit Amount and Credit Amount Should Be Equal");
                    	 return false; 
                     }

                     if(credit_amount!=document.getElementById("txtTotalAmt").value)
                     {
                    	 alert("Master Total Amount and Credit Amount Should Be Equal");
                    	 return false; 
                     }
                   
                     
                     
                }
                       










                             
                document.getElementById("txtDebitHead").disabled=false;
                document.getElementById("cmbMas_SL_type").disabled=false;
                document.getElementById("cmbMas_SL_Code").disabled=false;
                document.getElementById("txtTotalAmt").disabled=false;


                document.getElementById("txtParticular").disabled=false;
                
        }
       
</script>
  </head>
   <%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      Statement st=null;
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
		     
		      
  %>
  <body onload="clrForm('load');LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Post TCA System</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="TCA_POST" id="TCA_POST" method="POST"
                  action="../../../../../SingleEmployee_Many_units?Command=Add" onsubmit="return checkNull()">
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
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);loadTransferUnit();clrForm('load');doFunction_voucher('load_voucher_no');">        
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
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
                        </select>
                      </div>
                    </td>
              </tr>
              
              
              
               <tr class="table">
                    <td>
                      <div align="left">
                         Journal Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtPayment_date" id="txtPayment_date" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="doFunction_voucher('load_voucher_no');checkDated();"/>
                         
                                                
                      </div> 
                    </td>
              </tr>
            
              
        
              <tr class="table">
		          <td>
			          <div align="left">
			              Journal Number
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
                        TDA Create Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtCrea_date" id="txtCrea_date" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                              />
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.TCA_POST.txtCrea_date,0);"
                             alt="Show Calendar"></img>                        
                      </div> 
                    </td>
              </tr>
		
             <tr class="table">
                    <td>
                      <div align="left">
                          Reason For TDA
                      </div>
                    </td>
                    <td>
                      <div align="left">			              
                          <select name="cmbReason" id="cmbReason" >
                            <option value="">--Select Reason--</option>
                            <%
                            
                                     try
                                     {
                                            ps=con.prepareStatement("select REASON_CODE,REASON_DESC from FAS_MST_TDA_TCA_REASON order by REASON_DESC");
                                            rs=ps.executeQuery();
                                            while(rs.next())
                                            {
                                                out.println("<option value="+rs.getInt("REASON_CODE")+" selected>"+rs.getString("REASON_DESC")+"</option>");
                                            }
                                        
                                     } 
                                     catch(Exception e)
                                     {
                                            System.out.println("Exception in Journal combo..."+e);
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
                      <div align="left">Debit Head</div>
                    </td>
                    <td>
                      <div align="left">
                         <input type="text" size=7 id="txtDebitHead" name="txtDebitHead" readonly="readonly"/>
                      </div>
                    </td>
              </tr>   
              
              
               <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Type</div>
                  
                </td>
                <td width="60%">
                  <div align="left">
                    <select  name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="6"
                            onchange="doFunction('Load_MasterSL_Code',this.value);">
                      <option value="">--Select Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=7order by SUB_LEDGER_TYPE_DESC");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getString("SUB_LEDGER_TYPE_CODE")+">"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</option>");
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
                    </select>
                  </div>
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
                        <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code"  onchange="loadName_Mas(this);"   >
                                
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
                      <div align="left">
                         Total Debit Amount  
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtTotalAmt" id="txtTotalAmt" maxlength="16" onkeypress="return filter_real(event,this,10,2);" onchange="moveAmount(this.value);" ></input>
                      </div>
                    </td>
              </tr>       
              
               <tr class="table">
	                <td>
	                  <div align="left"> Particulars</div>
	                </td>
	                <td>
	                  <div align="left">
	                     <textarea rows="4"  cols="50" id="txtParticular" name="txtParticular"></textarea>
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
			                    <input type="text" name="txtAcc_HeadCode" 
			                           id="txtAcc_HeadCode" maxlength="6" value="901001"  onkeypress="return numbersonly(event)"
				                         onchange="sixdigit();" 
			                            onblur="doFunction('checkCode','null');"  size="9"/>
			                    <img src="../../../../../images/c-lovi.gif" width="20" 
			                             height="20" alt="AccountHeadList"
			                             onclick="AccHeadpopup();"></img>
			                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" value="TRANSFER FORM BOARD  -RECEIPT ADJUSTED"
			                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
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
                  <div align="left">Sub-Ledger Type <font color="#ff2121">*</font> </div>
                </td>
                <td width="60%">
                  <div align="left">
                   <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="doFunction('Load_SL_Code',this.value);">
                      <option value="">--Select Type--</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Code <font color="#ff2121">*</font> </div>
                </td>
              <td width="60%">
            <table align="left">
             <tr align="left">
             <td>
                  <div align="left">
                        <select size="1" name="cmbSL_Code" id="cmbSL_Code" >
                                
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
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
                            <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="trs_employee(this.value);" />
                 </div>
               </td>
             
            </tr>
           </table>
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
		                    <input type="text" name="txtsub_Amount"  id="txtsub_Amount"  size="18"/>
		                  </div>
		                </td>
		              </tr>
		                <tr class="table">
                <td>
                  <div align="left"> M Book NO.</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="letterNo" id="letterNo" tabindex="5" ></input>
                  </div>
                </td>
              </tr>   
              
                  <tr class="table">
                <td>
                  <div align="left"> M Book Page NO.</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="letterpageNo" id="letterpageNo" tabindex="6" ></input>
                  </div>
                </td>
              </tr>   
              
              
              
              
              
               <tr class="table">
                <td>
                  <div align="left"> M Book Date</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="letterDate" id="letterDate" 
                           maxlength="10" size="11" tabindex="6"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.forms[0].letterDate);"
                         alt="Show Calendar"></img>
                  
                  </div>
                </td>
              </tr>  
		              <tr class="table">
		                <td>
		                  <div align="left">Remarks</div>
		                </td>
		                <td>
		                  <div align="left">
		                    <textarea name="txtParticular1" id="txtParticular1" cols="70" rows="3"></textarea>
		                  </div>
		                </td>
		              </tr>
		            
		             
               <tr class="tdTitle">
                    <td colspan="2" height="23">
                     <div align="center">
                        <table border="0">
                          		<tr>
                          				  <td>
	                                       <input type="button" name="cmdadd" id="cmdadd"
	                                              value="ADD" onclick="load_grid('ADD_GRID')" style="display:block" /></td>
	                                       <td>
	                                       <input type="button" name="cmdupdate" value="UPDATE"
	                                              id="cmdupdate" onclick="up();"
	                                              style="display:none" /></td>
	                                              <!-- <td>
	                                       <input type="button" name="cmdupdate" value="UPDATE"
	                                              id="cmdupdate" onclick="load_grid('update_GRID')"
	                                              style="display:none" /></td> -->
	                                       <td><input type="button" name="cmddelete" value="DELETE"
	                                              id="cmddelete" onclick="delete_GRID()"
	                                              disabled="disabled" /></td>
	                                       <td><input type="button" name="cmdclear" value="CLEAR ALL"
	                                              id="cmdclear" onclick="clearall()" /></td>
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
                        <th >A/c Head Code-Desc</th>
                        <th >CR/DR</th>
                        <th >Sub Ledger Type</th>
                        <th >Sub Ledger Code</th>   
                        <th >Amount</th>
                        <th >M Book No</th>
                        <th >M Book Page No</th>
                        <th >M Book Date</th>
                         <th >Particulars</th>
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
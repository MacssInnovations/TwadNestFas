<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Adjustment Memo System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    <script type="text/javascript" src="../js/LoadMemoDetails.js"></script>
    <script type="text/javascript" src="../js/Adjustment_Memo_Accept.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   
 
    <script type="text/javascript" language="javascript">
    function clrallsl(){
    	
    	 document.Adjustment_Memo_Form1.cmbSL_type.disabled=false;
    	 document.Adjustment_Memo_Form1.cmbSL_Code.disabled=false;
    }    
    function hid(){
    	if( document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled==true)
    	   document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=false;
    	 if( document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled==true)
    	   document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=false;
    }
    function loadDate()
        {
            //alert("enter");
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
             //document.Adjustment_Memo_Form1.txtDate.value=day+"/"+month+"/"+year;
             document.Adjustment_Memo_Form1.txtCB_Year.value=year;
         }
         
         function disp(dvalue){
         
         if(dvalue=="CR"){         
         document.getElementById("txtCB_Month").value="";
         document.getElementById("txtAcc_HeadCode").value="610102";
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true; 
          document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=false;
           document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=true;
         }else
         {
         document.getElementById("txtCB_Month").value="";
         document.getElementById("txtAcc_HeadCode").value="900202";
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].checked=true;         
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[0].disabled=false;
         document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].disabled=true;
         }
         
         }
	</script>
  </head>
  
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  
  <body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS');">
  
       <%
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;            
             Connection connection=null;        
             ResultSet results=null,rs2=null;
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
                       System.out.println("Exception in opening connection :"+e);
             }  
	       %>
	       <% 
	               session=request.getSession(false);
	       UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                int empid=empProfile.getEmployeeId();
                int  oid=0;             // Office id
                String oname="";        // office name
           
            try
            {
                   
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oid=results.getInt("OFFICE_ID");
                            System.out.println("Office id is:"+oid);
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
            <font size="4">Adjustment Memo from Board(Accept)</font>
          </div>
        </td>
      </tr>
    </table>
     
  <form name="Adjustment_Memo_Form1" id="Adjustment_Memo_Form1" method="POST" 
  action="../../../../../Adjustment_Memo_Accept?command=Add" onsubmit="return checkNull()">
                  
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
                     <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
               
                              
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
                    Advice Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="advice_type" id="advice_type" 
                           checked="checked" value="CR" onclick="disp(this.value);"/>Credit Advice
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="advice_type" id="advice_type" 
                           value="DR" onclick="disp(this.value);"/>Debit Advice &nbsp;&nbsp;&nbsp;&nbsp; 
                    
                  </div>
                </td>
              </tr>
              
              <tr class="table">
					          <td>
					              <div align="left">Orginated Cash Book Year &amp; Month <font color="#ff2121">*</font></div>
					          </td>
					          <td>
					              <div align="left">
					                <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3" maxlength="4" size="5"
					                       onkeypress="return numbersonly(event)" ></input>
					                 
					                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" >
					                  <option value="">Select Month</option>
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
					                </select>
					              </div>
					          </td>
					    </tr>
			  			 
			  <tr class="table">
                <td>
                  <div align="left">
                   Acceptance Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                                    
                   <input type="text" name="txtDate" id="txtDate"  tabindex="5" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);" 
                               onblur="return call_date(this);"/>
                               
                               
                         
                       <!--   <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.Adjustment_Memo_Form1.txtDate);"
                             alt="Show Calendar"></img>   -->
                  </div>
                </td>
              </tr>
           <tr class="table">
		<td>
		<div id="head_div1" name="head_div1" style="display: none">
		Supplement Voucher Number <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div id="head_div2" name="head_div2" style="display: none"><select
			name="supNo" id="supNo" onchange="checkLiveSub();">
			<option value="">Select Supplement No</option>
		</select></div>
		</td>

	</tr>
              <tr class="table">
                <td>
                  <div align="left">Memo Advice No. <font color="#ff2121">*</font></div>
                </td>
                <td>
                  <div align="left">
                      <select id="cmbAdviceNO" name="cmbAdviceNO" tabindex="6" onchange="loadMemoDetails(),loadValues();">
                         <option value="s">--Select No--</option>
                        
                      </select>
                      <input type="hidden" name="adjustmentDate" id="adjustmentDate" ></input>
                  </div>                  
                </td>
              </tr>
                  
              <tr class="table">
                <td>
                  <div align="left"> Authority Name</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="3"  tabindex="7" cols="35" id="txtAuthority" name="txtAuthority" ></textarea>
                  </div>
                </td>
              </tr>   
               <tr class="table">
                <td>
                  <div align="left"> Authority Address</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="3"  tabindex="8" cols="35" id="txtAuthorityaddress" name="txtAuthorityaddress" ></textarea>
                  </div>
                </td>
              </tr>   
              
              
               <tr class="table">
                <td>
                  <div align="left"> Letter No.</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="txtLetterNO" id="txtLetterNO" tabindex="9"></input>
                  </div>
                </td>
              </tr>   
              
               <tr class="table">
                <td>
                  <div align="left"> Letter Date</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtLetterDate" id="txtLetterDate" 
                           maxlength="10" size="11" tabindex="10"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>                              
                  </div>
                </td>
              </tr>   
             
              <tr class="table">
                <td>
                  <div align="left"> Particulars</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="4"  tabindex="11" cols="50" id="txtRemarks1" name="txtRemarks1"></textarea>
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
                    <input type="text" name="txtAmount" id="txtAmount"  maxlength="16" onkeypress="return filter_real(event,this,10,2)" tabindex="12"></input>
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
			                  <!-- disabled="disabled" -->
			                    <input type="text" name="txtAcc_HeadCode" value="610102"
			                           id="txtAcc_HeadCode" maxlength="6"   disabled="disabled"
			                            onkeypress="return numbersonly(event)"
			                            onchange="sixdigit();" 
			                            onblur="clrallsl();doFunction('checkCode','null');cr_dr()"  size="9"/>
			                    <img src="../../../../../images/c-lovi.gif" width="20" 
			                             height="20" alt="AccountHeadList"
			                             onclick="hid();AccHeadpopup();"></img>
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
			                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR"  onclick="cr_dr();"
			                            value="CR" disabled/>Credit
			                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
			                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR"  onclick="cr_dr();"
			                           checked="checked" value="DR"/>Debit &nbsp;&nbsp;&nbsp;&nbsp; 
			                    
			                  </div>
			                </td>
			              </tr>

			              <tr class="table">
			                 <td width="40%">
			                  	<div align="left">Sub-Ledger Type <font color="#ff2121">*</font></div>
			                 </td>
			                <td width="60%">
			                  <div align="left">
			                   <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="doFunction('Load_SL_Code',this.value);">
			                      <option value="">--Select Type--</option>			                     
			                    </select>
			                   </div>
				              <div align="left" style="display:none">
			                   <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type">
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
						                        <select size="1" name="cmbSL_Code" id="cmbSL_Code" >						                                
						                          <option value="">--Select Code--</option>
						                        </select>
						                  </div>						                 
						                  <div align="left" style="display:none">
							                    <select  name="cmbMas_SL_Code" id="cmbMas_SL_Code" >
							                      		<option value="">--Select Type--</option>
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
		                    <input type="text" name="txtsub_Amount"  id="txtsub_Amount" size="18" onkeypress="return filter_real(event,this,10,2);"/>
		                  </div>
		                </td>
		              </tr>
		              
		              <tr class="table">
		                <td>
		                  <div align="left">Remarks</div>
		                </td>
		                <td>
		                  <div align="left">
		                    <textarea name="txtParticular" id="txtParticular" cols="70" onkeypress="return check_leng(this.value);"
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
                        <th >Amount</th>
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
                <input type="submit" name="butSub" id="butSub" value="SUBMIT" onclick="return checkNull()"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form>
    </body>
</html>
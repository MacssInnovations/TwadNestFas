<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>HR Sanction Proceedings Multiple Payee (Employee/Pensioner)  Edit</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
     <script type="text/javascript" src="../scripts/Sanc_Proc_Single.js"></script>
    <script type="text/javascript" src="../scripts/HR_Sanc_Proc_Mul.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" language="javascript">
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
             document.HR_SancProcMul.sanc_date.value=day+"/"+month+"/"+year;
             document.HR_SancProcMul.txtCB_Year.value=year;
             document.HR_SancProcMul.txtCB_Month.value=today.getMonth()+1;
         }
	</script>
  </head>
<!--  <body onload="loadDate(),setTimeout('loadMemoNO()',3000),dispvalue();">-->
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();">
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
             }   UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
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
            <font size="4">HR Sanction Proceedings Multiple Payee (Employee/Pensioner) Edit</font>
          </div>
        </td>
      </tr>
    </table>
     
  <form name="HR_SancProcMul" id="HR_SancProcMul" method="POST" onsubmit="chk();" action="../../../../../HR_Sanc_Proc_Mul?command=Add">
                  
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
                <%
                      int unitid=0;
                      String unitname="";
                      try{
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
                    Accounting For Office Code <font color="#ff2121">*</font>
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
			  		 
          <tr align="left">
		          <td class="table">
		          	<div >
		              	Cash Book Year &amp; Month&nbsp;&nbsp;
		              	</div>
		          </td>
		          <td>
		                  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
		         
				          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
				          <input type="button" id="goBtn" name="goBtn" onclick="loadNote_noSanccp();" value="GO">			          
				   </td>       
		           
		        </tr> 	
		         <tr class="table">
	            <td>
	              <div align="left">
	                Sanction Proceeding No. 
	                <font color="#ff2121">*</font>
	              </div>
	            </td>
	            <td>
	              <select name="sanc_no" 
			                           id="sanc_no"  onchange="loadDetailsSanc(this.value);" >	
			                           <option value="">--Select--</option>
			                           	</select>	 
	               <input type="text" name="sanc_no" value="" id="sanc_no" size="10" readonly /> 
	            </td>
              </tr>          
          
		        
		         <tr>
            <td class="table">HR Note No</td>
            <td class="table">
		                
			                  <div align="left">
			                    <select name="note_no" 
			                           id="note_no" >	
			                           <option value="">--Select--</option>
			                           	</select>	     
			                           	<input type="text" id="ho_date" name="ho_date" value="" disabled="disabled">                
			                  </div>		              
            </td>
          </tr> 
			  <tr class="table">
                <td width="40%">
                  <div align="left">
                       Bill Major Type 
                  </div>
                  
                </td>
                <td width="60%">
                  <div align="left">
                    <select  name="majorType" id="majorType" tabindex="6"
                            onchange="callminor()">
                      <option value="0">--Select Major Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("BILL_MAJOR_TYPE_CODE")+">"+rs.getString("BILL_MAJOR_TYPE_DESC")+"</option>");
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
	              <div align="left">
	                   Bill Minor Type 
	              </div>
	            </td>
	            <td width="60%">
	                <table align="left">
	                 <tr align="left">
	                     <td>
	                         <div align="left">
	                                <select name="minorType" id="minorType" onchange="callsub(this.value);" tabindex="6">                                            
	                                  <option value="0">--Select Minor Type--</option>
	                                </select>
	                         </div>
	                      </td>
	                 </tr>
	               </table>
	            </td>
              </tr>
          
              <tr class="table">
                <td width="40%">
                  <div align="left">
                       Sanctioning Authority
                  </div>
                  
                </td>
                <td width="60%">
                  <div align="left">
                    <select  name="sanc" id="sanc" tabindex="6">
                      <option value="0">--Select--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS order by DESIGNATION");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("DESIGNATION_ID")+">"+rs.getString("DESIGNATION")+"</option>");
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
        	  
	          <tr>
	            <td class="table">Sanctioned By(Select Emp.Code)</td>
	                            <td class="table">
	                               <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" size="6" onkeypress="return  numbersonly(event,this);" readonly/>
	                               <input type="button" value="Select" name="txtEmpVal" id="txtEmpVal" onclick="employee_popup_master();"/>
	                          </td>
	          </tr>
	          
	         
              <tr class="table">
	            <td>
	              <div align="left">
	                Sanction Proceeding Date
	                <font color="#ff2121">*</font>
	              </div>
	            </td>
	            <td class="table">
			                
				                  <div align="left">
				                    <input type="text" name="sanc_date" value=""
				                           id="sanc_date" maxlength="6" 
				                            onkeypress="return numbersonly(event)"
				                            size="9"/>			                     
				                    <img src="../../../../../images/calendr3.gif"
				                         onclick="showCalendarControl(document.HR_SancProcMul.sanc_date,0);"
				                         alt="Show Calendar"></img>
				                  </div>
				              
	            </td>
              </tr>
		      <tr>
		            <td class="table">Total Sanction Amount(in Rs.)</td>
		            <td class="table">
				                
					                  <div align="left">
					                    <input type="text" name="tot_amt" value=""
					                           id="tot_amt" 
					                            onkeypress="return filter_real(event,this,6,5)"/>			                     
					                  </div>					              
		            </td>
		      </tr>      
                      
      <!--        <tr class="table">
                <td>
                  <div align="left">
                    Account Head Code 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" id="txtAcc_HeadCode" maxlength="6" 
                            onkeypress="return numbersonly(event)"
                            onchange="sixdigit();" 
                            onblur="doFunction('checkCode','null')"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>
                </td>
             </tr> -->
              <tr class="table">
			                <td>
			                  <div align="left">
			                    Account Head Code & Desc.
			                  </div>
			                </td>
	    	                <td width="60%">
			                  <div align="left">
			                    <select  name="txtAcc_HeadCode" id="txtAcc_HeadCode" tabindex="6" onchange="acc_desc()">
			                      <option value="">--Select--</option>
			                      <%
			                        try
			                        {
			                        ps=con.prepareStatement("select distinct ACCOUNT_HEAD_CODE from FAS_BILL_ACCOUNT_HEADS order by ACCOUNT_HEAD_CODE");
			                        rs=ps.executeQuery();
			                            while(rs.next())
			                            {
			                            out.println("<option value="+rs.getInt("ACCOUNT_HEAD_CODE")+">"+rs.getInt("ACCOUNT_HEAD_CODE")+"</option>");
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
			                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
			                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
			                  </div>
			                </td>
	     </tr>
	     
	          <tr class="table">
		          <td>Budget Provided</td>
		          <td>
		              <input type="text" name="bud_pro" id="bud_pro" onkeypress="return filter_real(event,this,6,5)"/>
		          </td>     
              </tr>
		      <tr class="table">
		          <td>Budget so far spent</td>
		               <td>
		                        <input type="text" name="bud_spent" id="bud_spent" onkeypress="return filter_real(event,this,6,5)"/>               
		                </td>
	          </tr>	 
	          <tr class="table">
	            <td>Amount deduced for this bill</td>
		            <td>
		              <input type="text" name="amt" id="amt" onkeypress="return filter_real(event,this,6,5)"/>
		            </td>     
		       </tr>
		       <tr class="table">
	              <td>Balance Amount for this bill</td>
		            <td>
		              <input type="text" name="bal_amt" id="bal_amt" onkeypress="return filter_real(event,this,6,5)"/>
		            </td>     
		       </tr>  
			   <tr class="table">
		          <td>Accounting Unit in which the payment to be made</td>
		               <td>
		                        <input type="text" name="ac_unit" id="ac_unit" onkeypress="return  numbersonly(event,this);"/>               
		                </td>
		       </tr>
		       <tr class="table">
		         <td>Remarks</td>
		          <td>
		            <textarea name="txtRemarks" id="txtRemarks" cols="60" rows="3"></textarea>
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
                        <tr>
                              <td class="table" width="40%" align="left">Bill Sub Type</td>
                              <td class="table" align="left"> 
                                      <select name="billsubtype" id="billsubtype"> 
                                          <option value="0">select</option>
                                      </select>
                              </td>               
             			</tr>
 			            <tr>
			               <td class="table">Payee Type</td>
				            <td class="table">
					            <input type="radio" name="radActive" id="radActive" value="E" checked>Employee &nbsp;
					            <input type="radio" name="radActive" id="radActive" value="U">Priviledged User
					            <input type="radio" name="radActive" id="radActive" value="P">Pensioner
				            </td>
				       </tr>
				        <tr>
	            				<td class="table">Payee Code</td>
	                            <td class="table">
	                               <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" size="6" onkeypress="return  numbersonly(event,this);"  readonly/>
	                               <input type="button" value="Select" name="txtEmpVal" id="txtEmpVal" onclick="employee_popup_trans();paydisp();"/>
	                          	</td>
	         			</tr>
				      
				       <tr>
				            <td class="table">Payee Name & Designation</td>
				            <td class="table">
					            <input type="text" name="pay_name" value="" id="pay_name"  size="50"  maxlength="125" onfocus="paydisp();" readonly/>
					         </td>
				       </tr>
				       <tr>
				            <td class="table">No.of HRs</td>
				            <td class="table">						                
							                  <div align="left">
							                    <input type="text" name="hr" value=""
							                           id="hr" maxlength="6" 
							                            onkeypress="return numbersonly(event)"
							                            onchange="sixdigit();" 
							                            size="9"/>
							                  </div>
							              
				            </td>
				       </tr>
				       <tr>
				            <td class="table">HR Period From Date</td>
				            <td class="table">
						                
							                  <div align="left">
							                    <input type="text" name="frm_date" value=""
							                 size="10"  maxlength="10"        id="frm_date"/>			                     
							                    <img src="../../../../../images/calendr3.gif"
							                         onclick="showCalendarControl(document.HR_SancProcMul.frm_date,0);"
							                         alt="Show Calendar"></img>
							                  </div>
							              
				            </td>
				       </tr>
				       <tr>
				            <td class="table">HR Period To Date</td>
				            <td class="table">						                
							                  <div align="left">
							                    <input type="text" name="to_date" value=""
							                      size="10" maxlength="10"     id="to_date"/>			                     
							                    <img src="../../../../../images/calendr3.gif"
							                         onclick="showCalendarControl(document.HR_SancProcMul.to_date,0);"
							                         alt="Show Calendar"></img>
							                  </div>							              
				            </td>
				       </tr>
				       <tr>
				            <td class="table">HR Amount</td>
				            <td class="table">						                
							                  <div align="left">
							                    <input type="text" name="hr_amt" value=""
							                           id="hr_amt" 
							                            onkeypress="return filter_real(event,this,6,5)"/>			                     
							                  </div>							              
				            </td>
				       </tr>
				       
				       <tr class="table">
							                <td>
							                  <div align="left">
							                    Sub Vouchers Attached?
							                  </div>
							                </td>
							                <td class="table">
									            <input type="radio" name="radActive1" id="radActive1" value="N" onclick="document.getElementById('trDtTo1').style.display='none';document.getElementById('trDtTo2').style.display='none';">No &nbsp;
									            <input type="radio" name="radActive1" id="radActive1" value="Y" onclick="document.getElementById('trDtTo1').style.display='block';document.getElementById('trDtTo2').style.display='block';" checked>Yes
				            				</td>
						</tr>
						
						<tr id="trDtTo" style="display='inline'" class="table">
				            <td class="table"><div id="trDtTo1">If Yes,No.of Sub-Vouchers</div></td>
				            <td class="table">
				            <div id="trDtTo2">
				                            <input type="text" name="sub_vou" value=""
							                           id="sub_vou" maxlength="6" 
							                            onkeypress="return numbersonly(event)"
							                            size="9"/>	
				             </div>
				            </td>
				       </tr>
				       
				       <tr>
				            <td class="table">Sanction Amount</td>
				            <td class="table">						                
							                  <div align="left">
							                    <input type="text" name="sanc_amt" value=""
							                           id="sanc_amt" 
							                            onkeypress="return filter_real(event,this,6,5)"/>			                     
							                  </div>							              
				            </td>
				       </tr>
					   <tr>
				            <td class="table">Ref.No</td>
				            <td class="table">						                
							                  <div align="left">
							                    <input type="text" name="ref_no" value=""
							                           id="ref_no" maxlength="6" 
							                            onkeypress="return numbersonly(event)"
							                            size="9"/>			                     
							                  </div>							              
				            </td>
				       </tr>
				       <tr>
				            <td class="table">Ref Date</td>
				            <td class="table">						                
							                  <div align="left">
							                    <input type="text" name="ref_date" value=""
							                           id="ref_date" maxlength="6" 
							                            onkeypress="return numbersonly(event)" size="9" />			                     
							                    <img src="../../../../../images/calendr3.gif"
							                         onclick="showCalendarControl(document.HR_SancProcMul.ref_date,0);"
							                         alt="Show Calendar"></img>
							                  </div> 							              
				            </td>
				       </tr>
				       
      	 		       <tr class="table">
				         <td>Particulars</td>
					          <td>
					            <textarea name="parti" id="parti" cols="60" rows="3"></textarea>
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
                        <th>Select</th>
                        <th>Bill Sub Type</th>
                        <th>Payee Type</th>
                        <th>Payee Code</th>
                        <th>HR Amount</th>
                        <th>Particulars</th>
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
               <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="Lists();" tabindex="60"/>
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
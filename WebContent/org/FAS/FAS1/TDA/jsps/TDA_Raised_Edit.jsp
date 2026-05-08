<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>TDA/TCA System</title>
    
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
    <script type="text/javascript" src="../scripts/TDA_Raised_Edit.js"></script>
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
                 document.TDA_TCA_one.txtCrea_date.value=day+"/"+month+"/"+year;                
        }
        function checkNull()
        {               
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
                if(document.getElementById("txtUnitId").value=="")
                {
        	            alert("Select Tranfer Unit");           
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
                if(tbody.rows.length>0)
                {
                        var dr_check_amt=0;var cr_check_amt=0;var count=0;
                        rows=tbody.getElementsByTagName("TR");                           
                        for(i=0;i<rows.length;i++)
                        {
                                    var cells=rows[i].cells;                                              
                                    if(cells.item(2).lastChild.nodeValue=='DR')
                                    {
                                            dr_check_amt=parseFloat(dr_check_amt) + parseFloat(cells.item(5).lastChild.nodeValue);
                                            if(document.TDA_TCA_one.Journal_type_one[0].checked==true)
	                   	                	{
	                   	                		  if(cells.item(1).firstChild.value==900108)
	                   	                			  count++;
	                   	                	}
                   	                	
                                    }  
                                    else
                                    {
                                            cr_check_amt=parseFloat(cr_check_amt) + parseFloat(cells.item(5).lastChild.nodeValue);       
                                            if(document.TDA_TCA_one.Journal_type_one[1].checked==true)
	                   	                	{
	                   	                		  if(cells.item(1).firstChild.value==901001)
	                   	                			  count++;
	                   	                	}                             
                                    }
                                   
                                     if(cells.item(1).firstChild.value==900108||cells.item(1).firstChild.value==901001)
                                     {
                                    //alert(">>1>>"+document.getElementById("txtUnitId").value);
                                    //alert(">>2>>"+cells.item(4).lastChild.nodeValue);
                                    var cellslCode=cells.item(4).lastChild.nodeValue;
                                    var slCode=(cellslCode.split("-"));
                                    //alert(">>3>>"+slCode[1]);
                         		//	if(document.getElementById("txtUnitId").value!=slCode[1])
                          		//	{
                          		//	  //	alert("The subledger code in details & subledger code in general part should be equal..... ");
                          		//	return false;
                         		//	}	    
                         			} 
                             
                              
                        }       
                        if(dr_check_amt!=cr_check_amt)
                        {
                                    alert("Total Amount of DR & CR should be equal");
                                    return false; 
                        }
                        else if(cr_check_amt!=document.getElementById("txtTotalAmt").value)
                        {
                                    alert("Total Amount of CR & TDA Total Debit Amount should be equal");
                                    return false;
                        }
                        else
                        {
                                    if(count==0)
                                    {
	                                     	if(document.TDA_TCA_one.Journal_type_one[0].checked==true)
	             	            				alert("Detail should have at least one DR Account Head 900108");
	             	            			else
	             	            				alert("Detail should have at least one CR Account Head 901001");
	             	            			return false;
                                            
                                    }
                        }
                            
                }               
                document.getElementById("txtDebitHead").disabled=false;
                document.getElementById("cmbMas_SL_type").disabled=false;
                document.getElementById("cmbMas_SL_Code").disabled=false;
                document.getElementById("txtTotalAmt").disabled=false;
                document.getElementById("num_rows").value=tbody.rows.length;
                document.getElementById("txtMas_PaidTo").value=document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text; 
                		
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
  <body onload="clrForm('load');LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();setTimeout('loadTransferUnit()',500);" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer of Debit/Credit Advice (TDA/TCA) System</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="TDA_TCA_one" id="TDA_TCA_one" method="POST"
                  action="../../../../../TDA_Raised_Edit?Command=Edit" onsubmit="return checkNull()">
                      <input type="hidden" name="txtMas_PaidTo" id="txtMas_PaidTo" />
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
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);loadTransferUnit()">        
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
                        Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtCrea_date" id="txtCrea_date" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);" />
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.TDA_TCA_one.txtCrea_date,1);"
                             alt="Show Calendar"></img>                        
                      </div> 
                    </td>
              </tr>
		
			  <tr class="table">
                    <td>
                      <div align="left">
                        Journal Type <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="radio" name=Journal_type_one id=Journal_type_one value="TDAO" onclick="doFunction_TDA('load_Voucher_No')"></input> TDA Originating
                        <input type="radio" name=Journal_type_one id=Journal_type_one value="TCAO" onclick="doFunction_TDA('load_Voucher_No')"></input> TCA Originating
                      </div>
                    </td>
              </tr>
              
               <tr class="table">
		          <td>
			          <div align="left">
			              Originated Sl.No.
			          </div>
			      </td>
			      <td>
			          <div align="left">
			              <select name="originated_slno" id="originated_slno" tabindex="5" onchange="doFunction_TDA('load_Voucher_Details')">
			              		  <option value="">Select Voucher Number</option>
			              </select>                                                                           
			          </div>
			      </td>
			   </tr>       
			 
             <tr class="table">
                    <td>
                      <div align="left">
                          Reason For TDA/TCA
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
                      <div align="left">Transferee Unit</div>
                    </td>
                    <td>
                      <div align="left">
                         <select name="txtUnitId" id="txtUnitId"   onchange="loadSLTypeNEW(this.value);">
                            <option value="">Select Unit</option>
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
                            <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="6"
                                    onchange="moveSubType(this.value);loadSLType('null',this.value);"  >
                               <option value="15">Accounting Units</option> 
                            </select>
                      </div>
                    </td>
              </tr>
            <tr class="table">
                    <td width="40%">
                      <div align="left">Sub-Ledger Code</div>
                    </td>
                    
                    <td>
                         <div align="left">
                               <!-- onchange="loadName_Mas(this);"  -->
                               <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code"  tabindex="7" >
                                 <option value="">--Select Code--</option>
                                  <%
                            
                                     try
                                     {
                                            ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME " +
                                            		" from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID not in(3) AND " +
                                            		" ACCOUNTING_UNIT_OFFICE_ID not in(select OFFICE_ID from COM_MST_OFFICES " +
                                            		" where OFFICE_STATUS_ID in('CL','RD')) " +
                                            		" order by ACCOUNTING_UNIT_NAME");
                                            rs=ps.executeQuery();
                                            while(rs.next())
                                            {
                                                out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" selected>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
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
                      <div align="left">
                         TDA/TCA Total Debit Amount  <font color="#ff2121">*</font> 
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
                      <div align="left">Remarks</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="txtRemarks" id="txtRemarks" cols="70" onkeypress="return check_leng(this.value);"
                                  rows="3" ></textarea>
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
                                    <input type="text" name="txtAcc_HeadCode" onchange="checkAccHead();"
                                           id="txtAcc_HeadCode" maxlength="6"  
                                           size="9"/>
                                    <img src="../../../../../images/c-lovi.gif"
			                                       width="20" height="20" alt="AccountHeadList"
			                                       onclick="AccHeadpopup();"></img>
                                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
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
                                        <div align="left">Sub-Ledger Type  <font color="#ff2121">*</font></div>
                                     </td>
                                     <td width="60%">
                                       <div align="left">
                                            <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="fordcb(this.value);">
                                              <option value="">--Select Type--</option>			                     
                                            </select>
                                       </div>
                                       <div id="benifici" style="display:none">
								                   <select size="1" name="dcb_ben_type" id="dcb_ben_type"   onchange="call('get','null')"  >
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
								             </td>
								             <td>
								                  <div align="left" id="offlist_div_trans"  style="display:none">
	                                                     <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_trans();"></img>
	                                                     <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"    onblur="trs_office(this.value);" />
								                  </div>
								                  <div align="left" id="emplist_div_trans"  style="display:none">
								                         <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
								                         <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onchange="trs_employee(this.value);" />                                                                         
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
			                    <input type="text" name="txtsub_Amount" onkeypress="return filter_real(event,this,10,2)" onblur="valid_amt(this);account_head_code();"
			                           id="txtsub_Amount" maxlength="17" size="18" />
			                  </div>
			                </td>
                        </tr>
		                <tr class="table">
			                <td>
			                  <div align="left">
			                     M-Book No
			                    <font color="#ff2121">*</font>
			                  </div>
			                </td>
			                <td>
			                  <div align="left">
			                    <input type="text" name="bookNo" id="bookNo" onkeypress="return filter_real(event,this,10,2)" maxlength="17" size="18" />
			                  </div>
			                </td>
                        </tr>
                        <tr class="table">
			                <td>
			                  <div align="left">
			                     M-Book Page No
			                    <font color="#ff2121">*</font>
			                  </div>
			                </td>
			                <td>
			                  <div align="left">
			                    <input type="text" name="bookPageNo" id="bookPageNo" onkeypress="return filter_real(event,this,10,2)" maxlength="17" size="18" />
			                  </div>
			                </td>
                        </tr>
		              <tr class="table">
                    <td>
                      <div align="left">
                        M-Book Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                   
                    <td>
                  <div align="left">
                    <input type="text" name="book_date" id="book_date" tabindex="3" 
                           maxlength="10" size="11"
                          onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);" />
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.TDA_TCA_one.book_date,1);"
                         alt="Show Calendar"></img>
                  
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
			          		              
                       <tr class="tdH">
                             <td colspan="2" height="23">
                                  <div align="center">
                                     <table border="0">
                                       <tr><td>
                                           <input type="button" name="cmdadd" id="cmdadd"
                                                  value="ADD" onclick="load_grid('ADD_GRID')" style="display:block" /></td>
                                           <td>
                                           <input type="button" name="cmdupdate" value="UPDATE"
                                                  id="cmdupdate" onclick="load_grid('update_GRID')"
                                                  style="display:none" /></td>
                                           <td><input type="button" name="cmddelete" value="DELETE"
                                                  id="cmddelete" onclick="delete_GRID()"
                                                  disabled="disabled" /></td>
                                           <td><input type="button" name="cmdclear" value="CLEAR ALL"
                                                  id="cmdclear" onclick="clearall()" disabled="disabled"/></td>
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
                      
                          <tr class="tdH">
                            <th >Select</th> 
                            <th>Account Head</th>
                            <th >CR/DR</th>     
                            <th>Sub Ledger Type</th>
                            <th >Sub Ledger Code</th>                                                 
                            <th >Amount</th>                                                    
                            <th >Particulars </th>
                            <th >M Book No</th>                                                 
                            <th >M Book PageNo</th>                                                    
                            <th >M Book Date</th>
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
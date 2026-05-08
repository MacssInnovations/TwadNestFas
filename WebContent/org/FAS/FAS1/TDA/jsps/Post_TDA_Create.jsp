<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Post TDA System</title>
    
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
    <script type="text/javascript" src="../scripts/Post_TDA_Create.js"></script>
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
              //   document.TDA_POST.txtCrea_date.value=day+"/"+month+"/"+year;  
                 document.TDA_POST.txtPayment_date.value=day+"/"+month+"/"+year; 
                 setTimeout('doFunction_voucher("load_voucher_no")',900);
                 
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
                if(document.getElementById("cmbReason").value=="")
                {
        	            alert("Select Reason");           
        	            return false;    
                }     
                if(document.getElementById("txtUnitId").value=="")
                {
        	            alert("Select Tranferee Unit");           
        	            return false;    
                }                   
                if(document.getElementById("txtTotalAmt").value.length==0)
                {
        	            alert("Enter TDA Total Debit Amount");          
        	            return false;    
                }
                if(tbody.rows.length==0)
                { 
        	            alert("Detail Part should have atleast one entry");         
        	            return false; 
                }
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
  <body onload="clrForm('load');LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();setTimeout('loadTransferUnit()',500);" bgcolor="rgb(255,255,225)"> 

  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Post TDA System</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="TDA_POST" id="TDA_POST" method="POST"
                  action="../../../../../Post_TDA_Create?Command=Add" onsubmit="return checkNull()">
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
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);loadTransferUnit();clrForm('load');">        
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
                        Payment Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtPayment_date" id="txtPayment_date" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return checkDated();"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.TDA_POST.txtPayment_date,1);setTimeout('checkDated();',1200)"
                             alt="Show Calendar"></img>                        
                      </div> 
                    </td>
              </tr>
              
              <tr class="table">
		          <td>
			          <div align="left">
			              Payment Voucher Number <font color="#ff2121">*</font>
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
                               onblur="return call_date(this,0);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.TDA_POST.txtCrea_date,0);"
                             alt="Show Calendar"></img>                        
                      </div> 
                    </td>
              </tr>
		
             <tr class="table">
                    <td>
                      <div align="left">
                          Reason For TDA <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">			              
                          <select name="cmbReason" id="cmbReason" >
                            <option  value="">--Select Reason--</option>
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
                      <div align="left">Transferee Unit <font color="#ff2121">*</font></div>
                    </td>
                    <td>
                      <div align="left" >
                      <input type="hidden" size=7 id="txtUnitId" name="txtUnitId" />
                      <input type="text" size=30 id="txtUnitId1" name="txtUnitId1" readonly="readonly"/>
                                                 
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
                      <input type="hidden" size=20 id="cmbMas_SL_type" name="cmbMas_SL_type" />
                      <input type="text" size=25 id="cmbMas_SL_type1" name="cmbMas_SL_type1" readonly="readonly"/>
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
                                            <input type="hidden" size=7 id="cmbMas_SL_Code" name="cmbMas_SL_Code" />
                   							<input type="text" size=30 id="cmbMas_SL_Code1" name="cmbMas_SL_Code1" readonly="readonly"/>
                                            
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
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtPaidTo" id="txtPaidTo" maxlength="16"  ></input>
                      </div>
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
	                        <input type="text" name="book_date" id="book_date" 
	                               maxlength="10" size="11"
	                               onfocus="javascript:vDateType='3';"
	                               onkeypress="return calins(event,this);"
	                               onblur="return call_date(this);"/>
	                         
	                        <img src="../../../../../images/calendr3.gif"
	                             onclick="showCalendarControl(document.TDA_POST.book_date,1);"
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
              
            </table>
          </div>
        </div>        
        <div class="tab-page" id="gd" >
         <h2 class="tab" > Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
              <tr>
                <td colspan="2">
                
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                          <tr class="tdH">
                            <th>Account Head</th>
                            <th >Bank A/c No.</th>     
                            <th>Bank Name</th>
                            <th >CR/DR</th>                                                 
                            <th >Sub-Ledger Type</th>                                                    
                            <th >Sub-Ledger Code </th>
                            <th >Cheque/DD </th>
                            <th >Cheque/DD No.</th>
                            <th >Cheque/DD Date </th>
                            <th >Amount </th>
                            <th >Particulars </th>
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
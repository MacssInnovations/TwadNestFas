<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>PassOrderPreparation</title>
    
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
    <script type="text/javascript" src="../scripts/PassOrderPreparation_Edit.js"></script>
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
               //  document.passorPreparation.txtCrea_date.value=day+"/"+month+"/"+year;   
                 document.passorPreparation.cbmonth.value=month;
                 document.passorPreparation.cbyear.value=year;      
               //  setTimeout('setEmp()',900);
        }
        
       
</script>
  </head>
  <%String s=request.getContextPath(); %>
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
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();" >
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">PassOrderPreparation</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="passorPreparation" id="passorPreparation" method="POST"  action="../../../../../PassOrderPreparation_Edit?command=EditPassorder" 
                 onsubmit="return checkNull()">
     
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
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);setTimeout('callGridValues()',900);">        
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
                      <div align="left">Cash Book Year</div>
                    </td>
                    <td>
                      <div align="left">
                         <input type="text" size=4 maxlength=4 id="cbyear" name="cbyear" onkeypress="return numbersonly(event)"/>
                      </div>
                    </td>
              </tr>  
              
            <tr align="left" class="table">
		          <td class="table">
		              Cash Book Month
	              </td>
	             <td class="table">
			          <select name="cbmonth"  id="cbmonth" tabindex="4" onchange="loadpassno();">
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
		          
		          </td>
        </tr>
              <tr>
                    <td class="table" width="40%" align="left">Pass Order No</td>
                    <td class="table" align="left">
                    	 <select name="passOrderNo" id="passOrderNo"  onchange="callGridValues()">
                         	<option value="">select</option>
                         </select>
                          <input type="hidden" name="passyear" id="passyear" ></input>
                          <input type="hidden" name="bill_num" id="bill_num" ></input>
                    </td>
                </tr>
               <tr class="table">
                    <td>
                      <div align="left">
                        Pass Order Prepared On
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtCrea_date" id="txtCrea_date"  STYLE="background-color:#E4F470" class="light" readonly="readonly"
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                                   onchange="callBDate();"
                               onblur="return call_date(this);"/>
                         <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.passorPreparation.txtCrea_date);"
		                         alt="Show Calendar"></img>
                                    
                      </div> 
                    </td>
              </tr>
               <tr align="left" class="table">
                        <td >
                            <div align="left">
                               Pass Order Prepared By
                               <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td >
                            <div align="left">
                                 <input type="text" class="light" name="txtPass_order_preparedBy"  id="txtPass_order_preparedBy"  maxlength="30" size="30" readonly="readonly"  />&nbsp;&nbsp; 
                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();"/>&nbsp;&nbsp;  
                        <input type="text" name="txtPass_order_preparedByEmpcode"  id="txtPass_order_preparedByEmpcode"  maxlength="6" size="6" onchange="Load_emp_details();" onblur="callemp('<%=s %>');" onkeypress="return numbersonly(event)"/>
                            </div>
                        </td>
                    </tr> 
                  
              <tr class="table">
                    <td>
                      <div align="left">
                         Pass Order Amount  <font color="#ff2121">*</font> 
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtTotalAmt" onblur="dispDetails();" id="txtTotalAmt" maxlength="12" size="12" onkeypress="return numbersonly(event);return filter_real(event,this,10,2);" ></input>
                      </div>
                    </td>
              </tr>       
              
              <tr class="table">
                    <td>
                      <div align="left">Pass Order Remarks</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="txtRemarks" id="txtRemarks" cols="70" rows="3" onblur="dispDetails();" ></textarea>
                      </div>
                    </td>
              </tr> 
              
              <tr class="table">
                    <td>
                      <div align="left">Pass Order Seal</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="passSeal" class="light" id="passSeal" cols="70" onkeypress="return check_leng(this.value);"
                                  rows="3" onfocus="dispDetails();"></textarea>
                      </div>
                    </td>
              </tr>
              
            </table>
          </div>
          
        <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
             Select
                <a href="javascript:selectAll('ALL');">All</a>
                 <a href="javascript:selectAll('UNSelect');">Unselect</a> 
            </th>
            <th>
             Bill No
            </th><!--
            <th>
             Bill Major Type
            </th>
            <th>
            Bill Minor Type
            </th>
            <th>
            Bill Sub Type
            </th>
            --><th>
            Bill Date
            </th>
            <th>
            Bill Amount
            </th>
          
            <th>
            Payable To
            </th><!--
            <th>
            Scrutiny Date
            </th>
            <th>
            Particulars
            </th>
             <th>
            Show Details ?
            </th>
           
          --></tr>
          <tbody id="tbody" class="table">          
          </tbody>
        </table>
        
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
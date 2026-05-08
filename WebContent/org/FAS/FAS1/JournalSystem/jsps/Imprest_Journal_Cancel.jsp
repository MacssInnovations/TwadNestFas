<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="java.sql.Date" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Imprest/Temp.Advance A/c Journal System</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/Imprest_Journal_Cancel.js"></script>
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">
          </script>
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
                 
		         document.frmJournal_Imprest_Cancel.txtCrea_date.value=day+"/"+month+"/"+year;
		         setTimeout('doFunction("load_Voucher_No","null")',900);
		      //   doFunction('load_Voucher_No','null');
		 }
        
    	/* function load_date_onfocus()
     	 {
		        
		         if(document.frmJournal_Imprest_Cancel.txtCrea_date.value.length!=0)
		         {
			            var DOC=document.frmJournal_Imprest_Cancel.txtCrea_date.value;			       
			            var dd=DOC.split('/');
			            var d1=dd[0];
			            var m1=dd[1];
			            var y1=dd[2];
			            var monthArray =new Array("January", "February", "March", 
			               "April", "May", "June", "July", "August",
			               "September", "October", "November", "December");
			            if(dd[1].substring(0,1)==0)
			               mon_arr=dd[1].substring(1,2);
			            else
			           	   mon_arr=dd[1];
			         
		         }
		         else if(document.frmJournal_Imprest_Cancel.txtCrea_date.value.length==0)
		            	document.frmJournal_Imprest_Cancel.txtCrea_date.focus();
          
     	 }  */
</script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');call_clr();loadDate();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Cancel Imprest/Temp.Advance A/c Journal System </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmJournal_Imprest_Cancel" id="frmJournal_Imprest_Cancel" method="POST"
                  action="../../../../../Imprest_Journal_Cancel?Command=Cancel"
                  onsubmit="return checkNull_cancel()">
    <%
  
				 Connection con=null;
				 ResultSet rs=null,rs2=null;
				 PreparedStatement ps=null,ps2=null;
				 ResultSet results=null;
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
				            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
				            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				
				            Class.forName(strDriver.trim());
				            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
				 }
				 catch(Exception e)
				 {
				   			System.out.println("Exception in connection...."+e);
				 }
				 long l=System.currentTimeMillis();
					Timestamp ts=new Timestamp(l);                      
					 Date ctdate = new java.sql.Date(ts.getTime()); 
    %>
    <% 
    	    	 HttpSession session=request.getSession(false);
        		 UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
			     System.out.println("user id::"+empProfile.getEmployeeId());
			     int empid=empProfile.getEmployeeId();
			     //int empid=9315;
			     int  oid=0;
			     String oname="";
			   
			     try
			     {
			           
			          //  ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
			          //  ps.setInt(1,empid);
			           ps=con.prepareStatement(" SELECT "+
            		"  CASE "+
            		 "   When Old_Office_Id   Is Not Null "+
            		  "  AND DATE_ALLOWED_UPTO>=? "+
            		    " THEN OLD_OFFICE_ID "+
            		    " ELSE Office_Id "+
            		  " END AS OFFICE_ID "+
            		" FROM "+
            		  " (SELECT Office_Id, "+
            		    " OLD_OFFICE_ID, "+
            		    " DATE_ALLOWED_UPTO "+
            		  " From Hrm_Emp_Current_Posting "+
            		  " Where Employee_Id=? )" );
            ps.setDate(1, ctdate);
            ps.setInt(2,empid);
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
                   
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" >
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onchange="byUnitAndOfficeChange();">
                  
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
                           onchange="doFunction('load_Voucher_No','null');"
                           onblur="return call_mainJSP_script(this,1);dateCheck(this);call_date(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_Imprest_Cancel.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              
     
              <tr class="table">
                <td>
                  <div align="left">
                    Journal Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="4" onchange="doFunction('load_Voucher_No','null');" >
		                <option value="68">Imprest Journal </option>
		                <option value="69-BPF">Temporary Adv.  Journal</option>
		                </select>
                     
                  </div>
                 </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Imprest/Temporary Advance &nbsp;Voucher Number
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                  <select size="1" name="txtJournalVou_No" id="txtJournalVou_No"
                            tabindex="4" onchange="doFunction('load_Voucher_Details','null');">
                      <option value="">--Select Voucher Number--</option>
                    </select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td width="30%">
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="8"  onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>              
            </table>
            
            <div style="display:none" id="CHD" >
              <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table" >
                <td width="30%">
                  <div align="left">
                    Cheque Number and Date
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCheque_NO" maxlength="10" tabindex="6" onkeypress="return numbersonly(event)"
                           id="txtCheque_NO" size="11"/>
                     <input type="text" name="txtCheque_date" id="txtCheque_date"  tabindex="7" 
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_Imprest_Cancel.txtCheque_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              </table>
            </div>
              
           
          </div>
        </div>
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                    <!-- The SL details input taken out -->
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                      <tr class="table">                        
                        <th >A/c Head Code</th>
                        <th >CR/DR</th>
                        <th>Sub Ledger Type</th>
                        <th >Sub Ledger Code</th>
                          <th>Bill No.</th>
                        <th>Bill Date</th>
                        <th>Amount</th>
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
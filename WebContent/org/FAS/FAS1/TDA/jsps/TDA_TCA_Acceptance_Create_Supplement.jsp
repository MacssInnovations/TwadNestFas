<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>TDA/TCA Accepting System(Supplement)</title>
    
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
    <script type="text/javascript" src="../scripts/TDA_TCA_Acceptance_Create_Supplement.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
     <script type="text/javascript"
            src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script>  
   
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
                document.TDA_TCA.txtCrea_date.value="31"+"/"+"03"+"/"+year;  
              
                 document.TDA_TCA.txtCB_Year.value=year;                
        }
        
        function callSJVload()
     {
  	   
  	   var month_chosen=document.getElementById("txtCB_Month").value;
  	 
  	   var dispsjv=document.getElementById("dispSJV");
  	 var regyr=document.getElementById("regyr");
  	var regdate=document.getElementById("regdate");
  	 
  	   if(month_chosen==3)
  		   {

  		 document.getElementById("labeliddd").style.display="block";
  		document.getElementById("reguDis").style.display="block";
  		   		dispsjv.style.display="block";
  		   		//regyr.style.display="block";
  		  		//regdate.style.display="block";
  		  		
  		   }
  	   else
  		   {
  		 document.TDA_TCA.reptype[0].checked=true;
  		 document.getElementById("labeliddd").style.display="none";
   		document.getElementById("reguDis").style.display="none";
  				dispsjv.style.display="none";
  				  document.getElementById("dispsupno1").style.display="none";
  		         document.getElementById("dispsupno2").style.display="none";
  				//regyr.style.display="block";
  		  	//	regdate.style.display="block";
  		   }
  	
     }
        function ChooseReptype(id)
     {
        
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");
    	 var regyr=document.getElementById("regyr");
    	  	var regdate=document.getElementById("regdate");
         if(id=="Regular")
         {
                  
                 dispsupnochosen1.style.display="none";
                 dispsupnochosen2.style.display="none";
				//fromdate
             //    regyr.style.display="block";
   		  	//	regdate.style.display="block";
         }
         else
         {

        	// regyr.style.display="none";
		  //	regdate.style.display="none";
                
                 dispsupnochosen1.style.display="block";
                 dispsupnochosen2.style.display="block";
                // alert("Select the Supplement Number");
         }
     }
</script>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
   <%
  
 
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
  <body onload="clrForm('load');LoadAccountingUnitID('LIST_ALL_UNITS');doFunction('checkCode','null');checkSuppTB();Check_Supplement_No();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer of Debit/Credit Advice (TDA/TCA) Accepting System(Supplement)</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="TDA_TCA" id="TDA_TCA" method="POST"
                  action="../../../../../TDA_TCA_Acceptance_Create?Command=AddSupp" onsubmit="return checkNull()">
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
                      <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);call_clr();">        
                          <%
                      int unitid=0;
                      String unitname="";
                      try{
                    	  //siva changed on 18-04-2016
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
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          <option value="">hhh</option>
                        </select>
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
                        <input type="radio" name=Journal_type id=Journal_type value="TDAA" checked onclick=checkAccountHead();></input> TDA Accepting
                        <input type="radio" name=Journal_type id=Journal_type value="TCAA" onclick=checkAccountHead()></input> TCA Accepting
                      </div>
                    </td>
              </tr>
              
              <tr class="table">
	            <td>
	              <div align="left" id="regyr" style="display:none">Originated Year &amp; Month <font color="#ff2121">*</font></div>
	            </td>
	            <td>
	              <div align="left">
	                <input type="text" name="txtCB_Year" id="txtCB_Year"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly(event)" onchange="resetMonth();" onblur="checkSuppTB();"></input>	    
	                       
	                       <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="callSJVload();loadVoucher();">
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
	                       
	                       
	                                    
	                
	              </div>
	            </td>
	          </tr>
	          
	          <tr align="left" class="table">
          <td>
            <div align="left" id="labeliddd" style="display:none">Select</div>
          </td>
          <td>
            <div align="left" id="reguDis" style="display:none">                
                            
                            <input type=radio id="reptype" name="reptype" value="Regular" checked onclick="ChooseReptype(this.value),loadVoucher();"> Regular
                            </div>
                            <div id="dispSJV" name="dispSJV" style="display:none">
                            <input type=radio id="reptype" name="reptype" value="InclusiveSJV" onclick="ChooseReptype(this.value);loadVoucher();Check_Supplement_No1();" >SJV
                           
            </div>
            </td>
         </tr>
	          
	          
	          
	          <tr align="left" class="table">
            <td class="table">
            <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">Supplement Number<font color="#ff2121">*</font>
            </div>
            </td>
            <td>
               <div id="dispsupno2" name="dispsupno2" style="display:none">
                <select name="supNo1" id="supNo1" onblur="loadVoucher();">
                   <!-- <option value="" >-- Select Suppl No. -- </option>  -->
                </select>
                
                </div>
            </td>
          </tr> 
	          <tr class="table">
                <td>
                  <div align="left">
                    Originated Sl.No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="originated_slno" id="originated_slno" onchange="changeLink();loadVoucherDetails();">
                    	<option value="">--Select Voucher--</option>
                    	</select> <a id="linkId" href="javascript:checkVoucherNo();" style="visibility:hidden">Advice Details</a>
					<textarea size="3" style="color:red">Submit Button Will Be Visible on Selection of Voucher No only</textarea>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Originated Date 
                       <font color="#ff2121">*</font>
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="originated_date" id="originated_date" 
                              maxlength="10" size="11"
                              onfocus="javascript:vDateType='3';"  readonly="readonly"/>
                                             
                     </div> 
                   </td>
              </tr>   
              
              <tr class="table">
                    <td>
                      <div align="left">Originated Unit</div>
                    </td>
                    <td>
                      <div align="left">
                      	 <input type="text"name="txtUnitName" id="txtUnitName" size=50 readonly="readonly"/>
                         <input type="hidden"name="txtUnitId" id="txtUnitId" size=5 readonly="readonly"/>
                         <input type="hidden"name="txtOffId" id="txtOffId" size=5 readonly="readonly"/>
                         <input type="hidden"name="txtReason" id="txtReason" size=5 readonly="readonly"/>
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
                        <input type="text" name="txtTotalAmt" id="txtTotalAmt" size="16" readonly="readonly"></input>
                      </div>
                    </td>
             </tr> 
             
             <tr class="table">
                    <td>
                      <div align="left">Account Head Code</div>
                    </td>
                    <td>
                      <div align="left">
                         <input type="text" size=6 id="txtDebitHead" name="txtDebitHead" readonly="readonly"/>
                      </div>
                    </td>
              </tr>    
              
             <tr class="table">
                    <td>
                      <div align="left">
                        Accepting Date  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    
                    <% 
                  String cash_year="";
                  try{              
// String unit=request.getParameter("cmbAcc_UnitCode");
 //int unitid=Integer.parseInt(unit);
 //out.println(unitid);
 
 PreparedStatement ps_dte=con.prepareStatement("SELECT " +
 "  CASE " +
 "    WHEN coalesce(b.year1 ,0) = a.year " +
 "    THEN '31/03/' " +
 "		|| coalesce(a.year ,extract(YEAR FROM now()))" +
// "      || extract(YEAR FROM now()) " + modified by sathya on 18/02/2016
 "    ELSE '31/03/' " +
 "      || coalesce(a.year ,extract(YEAR FROM now())) " +
 "  END AS CASHBOOK_YEAR " +
 " FROM " +
 "  (SELECT row_number() over() AS id, " +
 "    YEAR " +
 "  FROM " +
 "    (SELECT MAX(SUP.CASHBOOK_YEAR) AS YEAR " +
 "    FROM FAS_SUPPLEMENT_GJV SUP " +
 "    WHERE STATUS ='L' " +
 "    )as sm " +
 "  )a " +
 " LEFT OUTER JOIN " +
 "  (SELECT row_number() over() AS id, " +
 "    year1 " +
 "  FROM " +
 "    (SELECT MAX(CASHBOOK_YEAR) AS year1 " +
 "    FROM FAS_TRIAL_BALANCE_STATUS_SJV " +
 "    WHERE ACCOUNTING_UNIT_ID = " +unitid+
 "    GROUP BY ACCOUNTING_UNIT_ID " +
 "    )as sm " +
 "  )b " +
 " ON a.id=b.id");



                  ResultSet rs_dte=ps_dte.executeQuery();
                  while(rs_dte.next()){
                	 cash_year=rs_dte.getString("CASHBOOK_YEAR");
                  }
                  }catch (Exception e){
                	  e.printStackTrace();
                  }
                  
                  %>
                    
                    
                    <td>
                      <div align="left">
                       <!--  <input type="text" name="txtCrea_date" id="txtCrea_date" 
                               maxlength="10" size="11"  readonly
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="accDate();call_date(this);" />-->
                               
                               <input type="text" name="txtCrea_date" id="txtCrea_date" value="<%=cash_year %>" readonly
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);"/>
                               
                     
                	<select name="supNo" id="supNo" >
                   	<!--  <option value="" >-- Select Suppl No. -- </option> -->
                	</select>
                
                </div>
                                        
                      
                    </td>
                    
              </tr>     
              
              <tr class="table">
                    <td>
                      <div align="left">Particulars</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="txtRemarks" id="txtRemarks" cols="70" onkeypress="return check_leng(this.value);"
                                  rows="3" ></textarea>
                      </div>
                    </td>
              </tr> 
              
              <tr class="table">
                    <td>
                      <div align="left">Accept the TDA/TCA Raised <font color="#ff2121">*</font> </div>
                    </td>
                    <td>
                      <div align="left">
                       		<input type="radio" name="isAccept" id="isAccept" value="Y" onclick="checkStatus()"/> Yes <input type="radio" name="isAccept" id="isAccept" value="N" onclick="checkStatus()"/> No
                      </div>
                    </td>
              </tr>
              
              <tr class="table">
                    <td>
                      <div align="left">If not accepting state reason <font color="#ff2121">*</font> </div>
                    </td>
                    <td>
                      <div align="left">
                      <select id="notAccepting" name="notAccepting" >
                      <option value="1">Not Related To This Office</option>
                      <option value="2">Will be Accepted Later on Receipt Of Original Supporting Documents</option>
                      </select>
                      <!--  <textarea name="notAccepting" id="notAccepting" cols="70" onkeypress="return check_leng(this.value);"
                                  rows="3" ></textarea>  -->
                      </div>
                    </td>
              </tr> 
              
            </table>
          </div>
        </div>        
        <div class="tab-page" id="gd" >
         <h2 class="tab" > Details</h2>
           
          <div align="center" >
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
                                       <input type="text" name="txtAcc_HeadCode" onblur="doFunction('checkCode','null');" onchange="checkAccHead()"
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
                                          <select  name="cmbSL_type" id="cmbSL_type" onchange="fordcb(this.value);">
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
                 					 <div style="display:none">
                                     	  <select name="cmbMas_SL_type" id="cmbMas_SL_type" />
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
							                 <div style="display:none">
		                                     	  <select name="cmbMas_SL_Code" id="cmbMas_SL_Code" />
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
			                    <input type="text" name="txtsub_Amount" id="txtsub_Amount" maxlength="17" size="18" />
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
		                             onclick="showCalendarControl(document.TDA_TCA.book_date,1);"
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
              <div align="center" style="display:none" id="submitdiv">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm('cancel');"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
              <div id="canDiv" style="display:block" align="center">
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
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
    <title>TDA/TCA Suspense Head Clearence System(Supplement)</title>
    
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
    <script type="text/javascript" src="../scripts/TDA_TCA_Responding_Create_supplement.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript"  src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script>  
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
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');clrForm('load');checkSuppTB();Check_Supplement_No();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer of Debit/Credit Advice (TDA/TCA) Suspense Head Clearence System(Supplement)</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="TDA_TCA" id="TDA_TCA" method="POST"
                  action="../../../../../TDA_TCA_Responding_Create_supplement?Command=Add" onsubmit="return checkNull()">
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
                        <input type="radio" name=Journal_type id=Journal_type value="TDAR" checked onclick=checkAccountHead()></input>TDA Suspense Head Clearance
                        <input type="radio" name=Journal_type id=Journal_type value="TCAR" onclick=checkAccountHead()></input>TCA Suspense Head Clearance
                        
                      </div>
                    </td>
              </tr>
              
              <tr class="table">
	            <td>
	              <div align="left">Originated Year &amp; Month <font color="#ff2121">*</font></div>
	            </td>
	            <td>
	              <div align="left">
	                <input type="text" name="txtCB_Year" id="txtCB_Year"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly(event)" onchange="resetMonth();"></input>	                 
	                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="callSJVload();loadVoucher();">
	                  <option value="">Select Month</option>
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
                            
                            <input type=radio id="reptype" name="reptype" value="Regular" checked onclick="ChooseReptype(this.value);loadVoucher();" > Regular
                            </div>
                            <div id="dispSJV" name="dispSJV" style="display:none">
                            <input type=radio id="reptype" name="reptype" value="InclusiveSJV" onclick="ChooseReptype(this.value);loadVoucher();Check_Supplement_No1();">SJV
                           
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
                <select name="supNo1" id="supNo1" >
                   <!--  <option value="" >-- Select Suppl No. -- </option> -->
                </select>
                
                </div>
            </td>
          </tr>  
	          
	          <tr class="table">
                <td>
                  <div align="left">
                    Originated Advise No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="originated_slno" id="originated_slno" onchange="changeLink();loadVoucherDetails();">
                    	<option value="">--Select Voucher--</option>
                    	</select> <a id="linkId" href="javascript:ShowDetails('originated_slno');" style="visibility:hidden">Advise Details</a>
                    </select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Originated Advice Date                       
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="originated_sldate" id="originated_sldate" 
                              maxlength="10" readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>   
              
              <tr class="table">
                <td>
                  <div align="left">
                    Originated Journal No.
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type=text name="originated_jvr_no" id="originated_jvr_no" size=5 readonly="readonly"><a id="linkId1" href="javascript:ShowDetails('originated_jvr');" style="visibility:hidden">Voucher Details</a>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Originated Journal Date 
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="originated_jvr_date" id="originated_jvr_date" size=10 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
                  
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Accepted Advice No. 
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="accepted_slno" id="accepted_slno" size=5 readonly="readonly"/><a id="linkId2" href="javascript:ShowDetails('accepted_slno');" style="visibility:hidden">Advise Details</a>                   
                     </div> 
                   </td>
              </tr>  
              
               <tr class="table">
                   <td>
                     <div align="left">
                       Accepted Advice Date 
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="accepted_sldate" id="accepted_sldate" size=10 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
              
               <tr class="table">
                <td>
                  <div align="left">
                    Accepted Journal No.
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type=text name="accepted_jvr_no" id="accepted_jvr_no" size=5 readonly="readonly"><a id="linkId3" href="javascript:ShowDetails('accepted_jvr');" style="visibility:hidden">Voucher Details</a>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Accepted Journal Date 
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="accepted_jvr_date" readonly="readonly" id="accepted_jvr_date" size=10 />                   
                     </div> 
                   </td>
              </tr>  
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Accepted Unit
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="accepted_unit_name" id="accepted_unit_name" size=30 readonly="readonly"/>
                       <input type="hidden" name="accepted_unit_id" id="accepted_unit_id" size=30 readonly="readonly"/> 
                       <input type="hidden" name="accepted_office_id" id="accepted_office_id" size=30 readonly="readonly"/>                  
                     </div> 
                   </td>
              </tr>  
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Total Amount
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="txtTotalAmt" id="txtTotalAmt" size=6 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
              
               <tr class="table">
                   <td>
                     <div align="left">
                       Credit Account Head Code
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="cr_accHead_code" id="cr_accHead_code" size=6 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
                            
              <tr class="table">
                   <td>
                     <div align="left">
                       Debit Account Head Code
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="dr_accHead_code" id="dr_accHead_code" size=6 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
                           
             <tr class="table">
                    <td>
                      <div align="left">
                        Suspense Head Clearance Date  <font color="#ff2121">*</font>
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
 "    WHEN COALESCE(b.year1 ,0) = a.year " +
 "    THEN '31/03/' " +
 "		|| COALESCE(a.year ,extract(YEAR FROM now()))" +
// "      || extract(YEAR FROM now()) " + modified by sathya on 18/02/2016
 "    ELSE '31/03/' " +
 "      || COALESCE(a.year ,extract(YEAR FROM now())) " +
 "  END AS CASHBOOK_YEAR " +
 " FROM " +
 "  (SELECT row_number() OVER ()  AS id, " +
 "    YEAR " +
 "  FROM " +
 "    (SELECT MAX(SUP.CASHBOOK_YEAR) AS YEAR " +
 "    FROM FAS_SUPPLEMENT_GJV SUP " +
 "    WHERE STATUS ='L' " +
 "    ) as opt2 " +
 "  )a " +
 " LEFT OUTER JOIN " +
 "  (SELECT row_number() OVER ()  AS id, " +
 "    year1 " +
 "  FROM " +
 "    (SELECT MAX(CASHBOOK_YEAR) AS year1 " +
 "    FROM FAS_TRIAL_BALANCE_STATUS_SJV " +
 "    WHERE ACCOUNTING_UNIT_ID = " +unitid+
 "    GROUP BY ACCOUNTING_UNIT_ID " +
 "    )as opt1 " +
 "  )b " +
 " ON a.id=b.id");

System.out.println("SELECT " +
		 "  CASE " +
		 "    WHEN NVL(b.year1 ,0) = a.year " +
		 "    THEN '31/03/' " +
		 "		|| NVL(a.year ,extract(YEAR FROM now()))" +
		// "      || extract(YEAR FROM now()) " + modified by sathya on 18/02/2016
		 "    ELSE '31/03/' " +
		 "      || NVL(a.year ,extract(YEAR FROM now())) " +
		 "  END AS CASHBOOK_YEAR " +
		 " FROM " +
		 "  (SELECT row_number() OVER ()  AS id, " +
		 "    YEAR " +
		 "  FROM " +
		 "    (SELECT MAX(SUP.CASHBOOK_YEAR) AS YEAR " +
		 "    FROM FAS_SUPPLEMENT_GJV SUP " +
		 "    WHERE STATUS ='L' " +
		 "    ) " +
		 "  )a " +
		 " LEFT OUTER JOIN " +
		 "  (SELECT row_number() OVER ()  AS id, " +
		 "    year1 " +
		 "  FROM " +
		 "    (SELECT MAX(CASHBOOK_YEAR) AS year1 " +
		 "    FROM FAS_TRIAL_BALANCE_STATUS_SJV " +
		 "    WHERE ACCOUNTING_UNIT_ID = " +unitid+
		 "    GROUP BY ACCOUNTING_UNIT_ID " +
		 "    ) " +
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
                        <input type="text" name="txtCrea_date" id="txtCrea_date" value="<%=cash_year %>" readonly
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);"/>
                         
                                             
                      </div> 
                      <div align="left">
                  <select name="supNo" id="supNo" onchange="checkLiveSub();">
                       <!--  <option value="">Select Supplement No</option> -->
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
              
            </table>
          </div>      
 
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" disabled value="SUBMIT"/>
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
    </form>
    </body>
</html>
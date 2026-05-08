<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>  
  <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Twad Outstanding</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
   <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
   
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript" src="../scripts/TwadScheme_outstanding.js"></script>
    
    
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }

        
          function loadDate()
         {
             // alert('tezt')
        	  //call_bankUpdate();
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
            //document.frmMIS_Twad.date_val.value=day+"/"+month+"/"+year;
            document.getElementById("date_val").value=day+"/"+month+"/"+year;
       
        
         }
</script>
  </head>
  <%
  int val=1;
	String s = request.getContextPath();
  response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
  int unitid=0;
  String unitname="";
	System.out.println(s);
%>
  <body onload="loadDate(),check_User('<%=s%>'),LoadAccountingUnitID('LIST_ALL_UNITS'),frzzDet('<%=s%>'),LoadProgram('<%=val%>'),LoadProgram1('<%=val%>'),Load_grid('gen'),Load_grid('sch'),Load_grid('brd');LcBody('<%=val%>'),LcBody1('<%=val%>');"><table cellspacing="1" cellpadding="3" width="100%">
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Details of Liability in respect of the Schemes taken up before 31.3.2011</font>
          </div>
        </td>
      </tr>
    </table>
  <form name="frmMIS_Twad" id="frmMIS_Twad" action="">
   <input type="hidden" id="sno_val" name="sno_val" value=""/>
      <input type="hidden" id="Type_desc" name="Type_desc" value=""/>
       <input type="hidden" id="empid_val" value="<%=empid %>"/>
    <input type="hidden" id="date_val" value=""/>
    <input type="hidden" id="Dist_val" value=""/>
     <input type="hidden" id="date_val" value=""/>
    <input type="hidden" id="f_year" name="f_year" value="2012-13"/>
      <div class="tab-pane" id="tab-pane-1">
       <table cellspacing="1" cellpadding="2" border="0" width="100%">
      <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                     
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                            tabindex="1" onchange="frzzDet('<%=s%>'),LoadProgram('<%=val%>'),LoadProgram1('<%=val%>'),Load_grid('gen'),Load_grid('sch'),Load_grid('brd');">
                      <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                       unitid=0;
                      unitname="";
                      try{
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
                  <div id="pdf_btn" onclick="pdf_All('<%=s%>');" style="display:none;"></div>
                </td>
              </tr></table>
        <div class="tab-page">
          <h2 class="tab" >General Liability </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>
	
	<tr class="table">
		<td>
		<div align="left">Financial Year</div>
		</td>
		<td>
		<!--<select id="fin_year" name="fin_year">
			<option value="2006-2007">2006-07</option>
			<option value="2007-2008">2007-08</option>
			<option value="2008-2009">2008-09</option>
			<option value="2009-2010">2009-10</option>
			<option value="2010-2011">2010-11</option>
			<option value="2011-2012">2011-12</option>
			<option value="2012-2013" selected="selected">2012-13</option>
		</select>
		-->
		<label id="fin_year" name="fin_year" >2012-13</label>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Contractor Payments due(Liability)</div>
		</td>
		<td><input type="text" id="cont_amt" name="cont_amt"" >
		<input type="hidden" id="cont_id" name="cont_id" value="101" ></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">Supplier  Payments due(Liability)</div>
		</td>
		<td><input type="text" id="sup_amt" name="sup_amt" >
		<input type="hidden" id="sup_id" name="sup_id" value="102"></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">EMD Received before 31Mar2011 and still due</div>
		</td>
		<td><input type="text" id="EMD_amt" name="EMD_amt" >
		<input type="hidden" id="EMD_id" name="EMD_id" value="103"></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">SD Received before 31Mar2011 and still due</div>
		</td>
		<td><input type="text" id="SD_rec_amt" name="SD_rec_amt" >
		<input type="hidden" id="SD_rec_id" name="SD_rec_id" value="104"></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">Deposit received for Water Connection</div>
		</td>
		<td><input type="text" id="Deposit_amt" name="Deposit_amt">
		<input type="hidden" id="Deposit_id" name="Deposit" value="105"></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">Advance Received for Water Charges</div>
		</td>
		<td><input type="text" id="Adv_amt" name="Adv_amt" >
		<input type="hidden" id="Adv_id" name="Adv_id" value="106"></td>
		</tr>
	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>

	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="ge_onsubmit1" value="SUBMIT" id="ge_onsubmit1" onClick="generalFunc('<%=s%>');"/>
			 <input type="button" name="onUpdate_ge" value="Update" id="onUpdate_ge"  onClick="Update_gen('<%=s%>');" style="display: none" />
			 <input type="button" name="onsubmit1" value="PDF" id="onsubmit1"  onClick="printPDF('<%=s%>','G');" />
			<input type="button" name="butCan2" id="butCan2" value="Cancel" onclick="window.location.reload();" />
			
			</div>
		</td>
	</tr>
</table>

 
          </div>
        </div>
         
        <div class="tab-page" id="gd">
          <h2 class="tab">Liability to be paid by the Board</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%" id="lia_row">
	<tr class="tdTitle">
		<td colspan="9">
		<div align="left"><strong>General Liability Details</strong></div>
		</td>
	</tr>
	

		<tr class="table">
		<th>
		<div align="left">Program</div>
		</th>
		<th>
		<div align="left">Scheme</div>
		</th>
		<th>
		<div align="left">Fund Tie up</div>
		</th>
		<th>
		<div align="left">Received Amt</div>
		</th>
		
	<th>
		<div align="left">Expdr incured upto 31.3.2011</div>
		</th>
		<th>
		<div align="left">Expdr incured upto 31.3.2013</div>
		</th>
		<th>
		<div align="left">Bills Pending as on 31.3.2013</div>
		</th>
		<!--<th>
		<div align="left">Balance</div>
		</th>
		-->
		<th>
		<div align="left">Local Body</div>
		</th>
		<th>
		</th>
		</tr>
		
	<tr class="table">
		<td>
		<div align="left">
		<select id="pro_id1" name="pro_id1" style="width: 50mm">
		<option value="">--Select--</option>
	</select></div>
		</td>
		<td>
		<div align="left"><input type="text" size="20" id="sch_id1"/></div>
		</td>
		<td>
		<div align="left"><input type="text" size="10" id="tie_id1" /></div>
		</td>
		<!--<td>
		<div align="left"><input type="text" size="10" id="AmtRec_id1" onchange="balanceVal('<%=val%>');"/></div>
		</td>
		-->
		<td>
		<div align="left"><input type="text" size="10" id="AmtRec_id1"/></div>
		</td>
		<td>
		<div align="left"><input type="text" id="ExpBook_id1" size="10"/></div>
		</td>
		<td>
		<div align="left"><input type="text" id="incur_id1"  size="10" /></div>
		</td>
		<td>
		<div align="left"><input type="text" id="ExpAct_id1" size="10"/></div>
		</td>
		<!--<td>
		<div align="left"><input type="text" id="Bal_id1" disabled="disabled" size="10" /></div>
		</td>
		-->
		<td>
		<div align="left" id="local_div1">
		<select id="Local1" name="Local1" onchange="LoadlcData('s','<%=val%>');" >
		<option value="">--Select--</option>
	</select></div>
	<div align="left" style="display: none;" id="fin_div1">
		<select id="fin1" name="fin1" onchange="Loadlhid(this.value);" >
		<option value="">--Select--</option>
	</select>
	<input type="button" id="add_oth1" name="add_oth1" value="ADD" style="display: none;" onclick="fun_oth('F','<%=val%>');"/>
	
	<a id="backbt1" href="javascript:backAnc('<%=val%>');">Back</a>
	</div>

<div align="left" style="display: none;" id="fin_oth1">
	<input type="text" id="other_id1" name="other_id1" />
	<input type="button" id="insert_oth1" name="insert_oth1" value="Insert" style="display: none;"  onclick="other_Details('F','<%=val%>');"/><!--
	<input type="button" id="add_oth1" name="add_oth1" value="Add" style="display: none;"/>


--></div>
	
		</td>
		<td>
		<div align="left"><input type="hidden" id="hid1" name="hid1" value="<%=val%>"/>
		<input type="button" id="save_id1" value="Add_Row" onclick="Row_Adding('<%=val%>','save');"/>
		<!--
		<input type="button" id="save_id1" value="Add" onclick="removeRow();"/>
		-->
		</div>
		</td>
		</tr>
	
	</table>
	<table cellspacing="1" cellpadding="2" border="0" width="100%" id="lia_row1">
	<tr class="tdH">
		<td colspan="7">
		<div align="center"><input type="button" name="onsubmit1_sch" value="SUBMIT" id="onsubmit1_sch" onClick="Sch_Liability('<%=s%>');" />
			
			 <input type="button" name="onsubmit1" value="PDF" id="onsubmit1"  onClick="printPDF1('<%=s%>','F');" />
			<input type="button" name="butCan1" id="butCan1" value="Cancel" onclick="window.location.reload();" />
			<input type="button" name="butCan" id="butCan" value="EXIT" onclick="window.close();" />
			</div>
		</td>
	</tr>
</table> 
<div id="unFrz_sch">
<table cellspacing="2" cellpadding="3" border="1" width="100%" >

	<tr class="tdH">
		<th>Select</th>
		<th>Program</th>
		<th>Scheme</th>
			<th>Local Body</th>
		<th>Tie Up Amount</th>
		<th>Received Amount</th>
			<th>Expdr incured upto 31.3.2011</th>
			<th>Expdr incured upto 31.3.2013</th>
		<th>Bills Pending as on 31.3.2013</th>
	
			<th>Delete</th>
		
	</tr>
	
	<tbody id="tblList_gen" class="table" align="left"></tbody>
</table>
</div>
<div id="frz_sch" style="display: none;">
<table cellspacing="2" cellpadding="3" border="1" width="100%" >

	<tr class="tdH">
		<th>Program</th>
		<th>Scheme</th>
			<th>Local Body</th>
		<th>Tie Up Amount</th>
		<th>Received Amount</th>
			<th>Expdr incured upto 31.3.2011</th>
			<th>Expdr incured upto 31.3.2013</th>
		<th>Bills Pending as on 31.3.2013</th>
	</tr>
	
	<tbody id="tblList_gen1" class="table" align="left"></tbody>
</table>
</div>
          </div>
        </div>
        <div class="tab-page" id="gd1">
          <h2 class="tab">Outstanding to be received by the Board</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%" id="Alia_row1">
	<tr class="tdTitle">
		<td colspan="9">
		<div align="left"><strong>General Liability Details</strong></div>
		</td>
	</tr>
	

	<tr class="table">
		<th>
		<div align="left">Program</div>
		</th>
		<th>
		<div align="left">Scheme</div>
		</th>
		<th>
		<div align="left">Fund Tie up</div>
		</th>
		<th>
		<div align="left">Received Amt</div>
		</th>
	<th>
		<div align="left">Expdr incured upto 31.3.2011</div>
		</th>
		<th>
		<div align="left">Expdr incured upto 31.3.2013</div>
		</th>
		<th>
		<div align="left">Bills Pending as on 31.3.2013</div>
		</th>
		<!--<th>
		<div align="left">Balance</div>
		</th>
		-->
		<th>
		<div align="left">Local Body</div>
		</th>
		<th>
		</th>
		</tr>
		
	<tr class="table">
		<td>
		<div align="left">
		<select id="Apro_id1" name="Apro_id1" style="width: 50mm">
		<option value="">--Select--</option>
	</select></div> 
		</td>
		<td>
		<div align="left"><input type="text" size="20" id="Asch_id1" /></div>
		</td>
			<td>
		<div align="left"><input type="text" size="10" id="Atie_id1"/></div>
		</td>
		<!--<td>
		<div align="left"><input type="text" size="10" id="AAmtRec_id1" onchange="balanceVal1('<%=val%>');"/></div>
		</td>
		-->
		<td>
		<div align="left"><input type="text" size="10" id="AAmtRec_id1" /></div>
		</td>
		<td>
		<div align="left"><input type="text" size="10" id="AExpBook_id1"/></div>
		</td>
			<td>
		<div align="left"><input type="text" size="10" id="Aincur_id1"/></div>
		</td>
		<td>
		<div align="left"><input type="text" size="10" id="AExpAct_id1"/></div>
		</td><!--
		<td>
		<div align="left"><input type="text" size="10" id="ABal_id1" disabled="disabled"/></div>
		</td>
		-->
<td>
		<div align="left" id="Alocal_div1">
		<select id="ALocal1" name="ALocal1" onchange="LoadlcData('T','<%=val%>');" >
		<option value="">--Select--</option>
	</select></div>
	<div align="left" style="display: none;" id="Afin_div1">
		<select id="Afin1" name="Afin1" onchange="Loadlhid(this.value);" >
		<option value="">--Select--</option>
	</select>
	<input type="button" id="Aadd_oth1" name="Aadd_oth1" value="ADD" style="display: none;"  onclick="fun_oth('T','<%=val%>');"/>
		
	<a id="Abackbt1" href="javascript:AbackAnc('<%=val%>');">Back</a>
	</div>
	

<div align="left" style="display: none;" id="Afin_oth1">
	<input type="text" id="Aother_id1" name="Aother_id1" />
	<input type="button" id="Ainsert_oth1" name="Ainsert_oth1" value="Insert" style="display: none;"  onclick="other_Details('T','<%=val%>');"/>
	<!--
	<input type="button" id="Aadd_oth1" name="Aadd_oth1" value="Add" style="display: none;"/>


--></div>
	
		</td>
		<td>
		<div align="left"><input type="hidden" id="Ahid1" name="Ahid1" value="<%=val%>"/>
		<input type="button" id="Asave_id1" value="Add_Row" onclick="Row_Adding1('<%=val%>','save');" />
		</div>
		</td>
		</tr>
	
	</table>
	<table cellspacing="1" cellpadding="2" border="0" width="100%" id="Alia_row1">
	<tr class="tdH">
		<td colspan="7">
		<div align="center"><input type="button" name="brd_onsubmit1" value="SUBMIT" id="brd_onsubmit1" onClick="Board_Liability('<%=s%>');" />
			
			 <input type="button" name="onsubmit1" value="PDF" id="onsubmit1"  onClick="printPDF2('<%=s%>','T');" />
			
			<input type="button" name="butCan" id="butCan" value="Cancel" onclick="window.location.reload();" />
			<input type="button" name="butCan" id="butCan" value="EXIT" onclick="window.close();" />
			</div>
		</td>
	</tr>
</table> 
<div id="unfrz_brd">
<table cellspacing="2" cellpadding="3" border="1" width="100%">

	<tr class="tdH">
	<th>Select</th>
		<th>Program</th>
		<th>Scheme</th>
		<th>Local Body</th>
		<th>Tie Up Amount</th>
		<th>Received Amount</th>
			<th>Expdr incured upto 31.3.2011</th>
			<th>Expdr incured upto 31.3.2013</th>
		<th>Bills Pending as on 31.3.2013</th>
		<th>Delete</th>
	</tr>
	
	<tbody id="tblList_lia" class="table" align="left"></tbody>
</table>
</div>
<div  id="frz_brd" style="display: none">
<table cellspacing="2" cellpadding="3" border="1" width="100%" >

	<tr class="tdH">
	
		<th>Program</th>
		<th>Scheme</th>
		<th>Local Body</th>
		<th>Tie Up Amount</th>
		<th>Received Amount</th>
			<th>Expdr incured upto 31.3.2011</th>
			<th>Expdr incured upto 31.3.2013</th>
		<th>Bills Pending as on 31.3.2013</th>
		
	</tr>
	
	<tbody id="tblList_lia1" class="table" align="left"></tbody>
</table>
</div>
          </div>
        </div>
          <div class="tab-page" id="gd1">
          <h2 class="tab">Freeze</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%" id="Alia_row1">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>Freeze Details</strong></div>
		</td>
	</tr>
	<tr class="table">
	<td> <table cellspacing="1" cellpadding="2" border="0" align="center" width="50%" id="Alia_row_freeze">
	<tr class="table">
	<td style="font-size: 20px;color:Green ">
	<div id="div_frz" style="display:block;">
<a id="fz_href" href="javascript:frzz('<%=s%>');">Freeze</a>
</div>
<div id="div_frz_text" style="display:none;">
<font style="color: purple;"><blink>Freezed</blink></font>
</div>
<div id="div_Unfrz" style="display:none;">
<a id="fz_href" href="javascript:Unfrzz('<%=s%>');">UnFreeze</a>
</div>
<div id="div_Unfrz_text" style="display:none;">
<font style="color: purple;"><blink>UnFreeze Pending</blink></font>
</div>

	</td>
	
	</tr></table></td></tr></table>
	
	</div></div>
		 <div class="tab-page">
          <h2 class="tab">Report-Head</h2>
           <table cellspacing="1" cellpadding="2" border="0" width="100%">
           	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>Reports</strong></div>
		</td></tr>
		 <tr  style="height: 10mm">
		<td colspan="2"></td></tr>
	<tr height="10">
		<td colspan="2">
		<div align="center">
		<center>
	 <select style="display: none;width: 60mm" id="rep_head" onchange="chk_sub_div();"  >
		<option value="">--Select--</option>
		<option value="Liability">Liability</option>
		<option value="Summary">Summary</option>
		<option value="Details">All_Detail</option>
		<option value="REG_Detail">Regionwise_Detail</option>
			
		</select>
	
		
		<div id="sub_rdio_div" style="display: none;">
		<br><br><br><br>
		Regions  <select  id="rep_head_rdo" onchange="printReport();"  >
		<option value="">--Select--</option>
		<option value="6868-Northern Region, Vellore">Northern Region, Vellore</option>
		<option value="6869-Western Region, Coimbatore">Western Region, Coimbatore</option>
		<option value="6870-Southern Region, Madurai">Southern Region, Madurai</option>
		<option value="6871-Eastern Region, Thanjavur">Eastern Region, Thanjavur</option>
			
		<option value="6777-Hogenakkal WS and  FM Project">Hogenakkal WS and  FM Project</option>
			
		</select>
		</div>
		</center>
		</div></td></tr>
	 <tr  style="height: 10mm">
		<td colspan="2"></td></tr>
	 <tr class="tdTitle" style="height: 5mm">
		<td colspan="2"></td></tr>
        
	
	</table>
          </div>
	
	 <div class="tab-page">
          <h2 class="tab">Download</h2>
           <table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>Download</strong></div>
		</td>
	</tr>
		<tr style="height: 5mm"><td colspan="2"></td></tr>
			<tr style="height: 5mm"><td colspan="2"></td></tr>
         	<tr>
         
		<td colspan="2"> <a href="../../../../../Guidelines to Liability Module.doc" />Guidelines to Liability Module</a>
         </td></tr>
         	<tr style="height: 5mm"><td colspan="2"></td></tr>
         		<tr style="height: 5mm"><td colspan="2"></td></tr>
         		
         <tr class="tdTitle" style="height: 5mm">
		<td colspan="2"></td></tr>
         </table>
          </div>

      </div>
      <br>
    </form></body>
</html>

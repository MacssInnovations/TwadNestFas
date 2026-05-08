 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page session="false" contentType="text/html;charset=windows-1252"%>
 <%@page import="java.util.Calendar" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 
 <html>
 <head>
 <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>

 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
 <title></title>
 <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
 <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
    <script type="text/javascript" src="../scripts/Bill_Demand_Report.js"></script>
    <script language=javascript src="../scripts/RIW.js"></script>
 
 <script type="text/javascript" src="../scripts/cellcreate.js"></script>
 
 <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
 
 <script type="text/javascript">
 function view()
 {
	 var maxsno=document.getElementById("maxsno").value;
	 document.location.href="Bill_Demand_Report.jsp?maxsno="+maxsno
 }
 </script>
 </head>
     <%
     
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String userid="0",Office_id="",Office_Name="";
			String Date_dis=day+"/"+month+"/"+year;
			    Controller obj=new Controller();
				Connection con;
				try
				{
				con=obj.con();
				obj.createStatement(con);
				 
				 HttpSession session=request.getSession(false);
				  userid=(String)session.getAttribute("UserId");
 				if(userid==null)
				{
				  	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				String OFFICE_ID=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				if (Office_id.equals("")) Office_id="0";
		 		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+OFFICE_ID+ " and OFFICE_LEVEL_ID='DN'");
				obj.conClose(con);
				}catch(Exception e) {
					
					userid="0";
				 
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				 
				}
			 
				String Bill_month=obj.setValue("month_value", request);
				String Ben_type=obj.setValue("ben_type", request);
				String Bill_year=obj.setValue("year_value", request);

%>
 
 <body  onload="data_show('show',2,1)">
  <form name="billdemand" >
   <table  class="table"  id="data_table" width=95% align=center border=0  cellspacing="0" cellpadding="3">
    <tr><td align="center" class="tdH" colspan=3 ><b>Bill Demand</b></td></tr>
    <rr><td width=35%  align="left" >Date &nbsp;:<%=Date_dis%></td><td   align="right" colspan="2" >Division &nbsp;:<%=Office_Name%></td></rr>
    <tr><td class="td"><div align="left"><font color="Black" size="2">&nbsp;Bill No</font></div> </td> 
    	<td colspan="2" > <input class="tb5" type=text id="Billno" name="Billno" readonly="readonly"></input>&nbsp;&nbsp;&nbsp;<label id="msg"></label></font></td>
   </tr>
   
   
   <tr>
        <td class="td" width="30%"><div align="left"><font color="Black" size="2">&nbsp;Bill Date</font></div> </td>
        <td colspan="2" align="left"><input class="tb5" type="text" name="date" id="date" maxlength="10" size="10" onFocus="javascript:vDateType='3'"  value="<%=Date_dis%>" onkeypress="return  calins(event,this)" onblur="return checkdt(this);"></input>
        <img src="../../../../../images/calendr3.gif" 
        onclick=" showCalendarControl(document.billdemand.date);"
        alt="Show Calendar" height="24" width="19"></img>             
        </td>
    </tr>
    <tr><td><div align="left"><font color="Black" size="2">&nbsp;Bill Period</font></div></td> 
        <td  align="left" colspan=2>
        <input type="text" class="tb5" name="datefrom" id="datefrom" maxlength="10" size="10" onFocus="javascript:vDateType='3'"  onblur="month_select(this)"  onblur="return checkdt(this);"/>
        <img src="../../../../../images/calendr3.gif"  onclick=" showCalendarControl(document.billdemand.datefrom);"  alt="Show Calendar"></img>
         To   <input type="text" class="tb5" name="dateto" id="dateto" width=10 maxlength="10" size="10" onFocus="return date_val('datefrom',this);" onChange="return date_val('datefrom',this);"/>
        <img src="../../../../../images/calendr3.gif" onclick=" showCalendarControl(document.billdemand.dateto);" alt="Show Calendar"></img>             
        </td>    
     </tr>
        
    <tr>
       <td><div align="left"><font color="Black" size="2">&nbsp;Beneficiary Name</font></div></td>
       <td><label id="ben_name"></label></td><td width=30%><div id='dist-div'></div></td>
    </tr>
     <tr>
       <td width=35%> <div align="left"><font color="Black" size="2">&nbsp;Beneficiary Type</font></div></td>
       <td width=35%><label id="ben_type"></label></td><td><div id='block-div'></div></td>
    </tr>
  </table>
   <input type="hidden" id="rows" name="rows"></input> 
 <table  class="table" id="data_table" width=95% align=center border="0" cellspacing="0" cellpadding="0">
  <tr class="tdH">
    <td align="center"><font color="Black" size="2">Sl.No</font></td>
     <td align=left valign=top width=2%>&nbsp;&nbsp;&nbsp;<font color="Black" size="2"><img src="../../../../../images/ic_checkall.gif" onclick="javascript:selectAll()"></img></font></td>
     
        
     
     <td align="center"> <font color="Black" size="2">Meter<br>Location</font></td>
     <td align="center"><font color="Black" size="2">Final<br>Reading</font></td>
     <td align="center"><font color="Black" size="2">Initial<br>Reading</font></td>
     <td align="center"><font color="Black" size="2">Consumption(KL)</font></td>
     <% if (Integer.parseInt(Ben_type)>6) {%>
     <td align="center"><font color="Black" size="2">Multiply<Br>Factor</font></td>
         <td align="center"><font color="Black" size="2">Total <Br>Consumption<br>(KL)</font></td>
      <%} %>
     <td align="right"><font color="Black" size="2">Tariff Rate<br>(Rs.)</font></td>
     <td align="center"><font color="Black" size="2">Amount<br>(Rs.)</font></td>
     <% if (Integer.parseInt(Ben_type)>6)  { %>
     <td align="center"><font color="Black" size="2">Excess<br>Consumption<br>(KL)</font></td>
     <td align="center"><font color="Black" size="2">Excess <Br> Rate<br>(Rs.)</font></td>
     <td align="center"><font color="Black" size="2">Excess<br>Amount<br>(Rs.)</font> </td>
     <%} %>
     <td align="center"><font color="Black" size="2">Total<br>Amount<br>(Rs.)</font></td>
     
  </tr>
  <tbody id="data_tbody"  ></tbody>
  
  <tr class="tdH" height=3ëm><td colspan=11></td></tr>
  </table>
  
  
  <table class="table"  id="data_table" width=95% align=center border=0  cellspacing="0" cellpadding="0">
  <tr>
    <td><div align="left"><font color="Black" size="2">&nbsp;Total Consumption Amount Rs.</font></div></td>
    <td align="left"> <input type=text class="tb5" style="text-align: right;" id="netconsumption" name="netconsumption" size="10" readonly></input> </td>
  </tr>
  <tr>
      <td align=center  colspan="4"> 
      <span align=center>
      	  
	  <div style="visibility: hidden;" id='othercharge'>
	  <div id='scroll_clipper' style='position:absolute; width:400px; border-height:3px; height: 400px; overflow:auto;white-space:nowrap;'>
	  <div id='scroll_text' align=center >
      <table bgcolor="#c0c0c0" id="charge_data"  border="1" width=95% align=right cellpadding=0 cellspacing=0>
        <tr class="tdH">
      	
       	<td align=center><font color="Black" size="2">Other Charges</td>
      	<td align=center width=5%><font color="Black" size="2">Add</td>
      	<td align=center width=5%><font color="Black" size="2">Subtract</td>
      	<td align=center width=2%>&nbsp;</td>
      </tr>
       <tbody id="charge_body" align="left"   ></tbody>
      </table>
      </div>
      </div>
      
      </div>
      </span>
      </td>
      
      </tr>
  <tr>
   <td  width="10%"><div align="left"><font color="Black" size="2">&nbsp;Total Excess Consumption Rs.</font></div></td>
   <td><input type=text style="text-align: right;" class="tb5" id="netexcessconsumption" name="netexcessconsumption" size="10" onKeyUp="isInteger(this,9,event)"  readonly></input></td>
  </tr>
  
  
  <tr> 
  	<td><div align="left"><font color="Black" size="2">&nbsp;Water Charges Rs.</font></div></td>
    <td><div align="left"><input type=text class="tb5" style="text-align: right;" id="netwcamt" name="netwcamt" size="10" onKeyUp="isInteger(this,9,event)" readonly></input></div></td>        
  </tr>
  
  <tr>
   <td  width=30%><div align="left"><font color="Black" size="2">Due as per Last Bill  Rs.</font></div></td>
   <td><div align="left"><input type=text class="tb5" style="text-align: right;" id="cbtotal" name="cbtotal" size="10" onKeyUp="isInteger(this,9,event)"></input></div></td>
  </tr>
      
  <tr> 
	<td>
			&nbsp;Receipt 
	</td>
</tr>

<tr>

    <td ><font color="Black" size="2">&nbsp;&nbsp;&nbsp;&nbsp;Water Charges  Rs.</font></td>
    <td><input type=text id="waterreceipt" class="tb5"  name="waterreceipt" size="10"   style="text-align: right;"  value=""  onKeyUp="isInteger(this,9,event)"></input>            </td>
</tr>
<tr>

       <td ><font color="Black" size="2">&nbsp;&nbsp;&nbsp;&nbsp;Interest  Rs. </font></td>
       <td><input type=text id="interestreceipt" class="tb5"  name="interestreceipt" size="10" style="text-align: right;"   value="0" onKeyUp="isInteger(this,9,event)"></input>            </td>
                
 </tr>
  <tr>

       <td ><font color="Black" size="2">&nbsp;&nbsp;&nbsp;&nbsp;Yester Year Rs.  </font></td>
       <td><input type=text id="yesteryearreceipt" class="tb5"  name="yesteryearreceipt" size="10"  style="text-align: right;"   value="0" onKeyUp="isInteger(this,9,event)"></input>            </td>
                
 </tr>
  <tr>

       <td ><font color="Black" size="2">&nbsp;&nbsp;&nbsp;&nbsp;Maintenance  Rs.  </font></td>
       <td><input type=text id="MAINT_COLN"  class="tb5" name="MAINT_COLN" size="10"  style="text-align: right;"   value="0" onblur="calculate()" onKeyUp="isInteger(this,9,event)"></input>            </td>
                
 </tr>
      <tr>
       <td >
                  <div align="left">
				<font color="Black" size="2">&nbsp;Balance  (Total Dues - Receipt)  Rs.
                  </div>
                </td>
                <td>
                 <div align="left">
                     <input type=text class="tb5"  style="text-align: right;"  id="balance" name="balance" size="10"   readonly></input>                  </div>
                </td>
                
      </tr>
        <tr>
       <td >
                  <div align="left">
				<font color="Black" size="2">&nbsp;Penalty @</font>   <input type=text class="tb0"  id="penaltyint" name="penaltyint" size="3" value="1" readonly>%
                   Rs.</div>
                </td>
                <td>
                 <div align="left"><input style="text-align: right;" class="tb5"  type=text id="penalty" name="penalty" size="10"  readonly> </input>                  
                </div>
                  
                </td> 
        <!--  <td width=50%> <font size=3>Other Charegs &nbsp; <a href="javascript:show_div()">Show</a>&nbsp; <a href="javascript:hide_div()">Hide</a></font></td> --> 
                             
      </tr>
      
      <tr> <td>
       <div align="left">
					<font color="Black" size="2">&nbsp;Total Other Charge Rs.</font>
                  </div>
           </td>
      <td><input type=text style="text-align: right;" class="tb5" id="othercharges" name="othercharges" size="10"  onblur="calculate()" onKeyUp="isInteger(this,9,event)"></td></tr>
      
      
      <tr>
      
       <td >
                  <div align="left">
				<font color="Black" size="2">&nbsp;Net Total Water Charges  Rs.</font> 
                  </div>
                </td>
                <td>
                 <div align="left">
                     <input type=text id="nettotal" class="tb5" style="text-align: right;" name="nettotal" size="10" onclick="net_calculation()" onKeyUp="isInteger(this,9,event)"></input>                  </div>
                </td>
                
      </tr>
      <td >
                  <div align="left">
				<font color="Black" size="2">&nbsp;Remarks :</font> 
                  </div>
                </td>
                <td colspan="2">
                 <div align="left">
                     <textarea id="Remarks" class="textarea" name="Remarks" size="10" cols=50></textarea>                  </div>
                </td>
                
      </tr>
       </table>
         <input type="hidden" value="" id="fyear" name="fyear" />
         <input type="hidden" value="" id="command" name="command"/>
         <input type="hidden" value="" id="fmonth" name="fmonth" />
        <table cellspacing="0" cellpadding="0" border="0" width="95%" align="center">
        	<tr>                            
          		<td class="tdH">
          			<div align="center">
          			   
          				<input class='fb2'   type="button" name="add" value="SUBMIT"
                   				id="add" onclick="data_show('add',7,0)" align="middle"/>
                   				
                   		<input class='fb2'  type="button" name="add" value="View" onclick="view()"></input>		
          		 		<input class='fb2'   type="reset" name="clear" value="CLEAR"
                   				id="clear" align="middle"/>
          				<input class='fb2'   type="button" name="exit" value="EXIT"
                   				id="exit" onclick="self.close()" align="middle"/>
          			</div>                   
          		</td>
          	</tr>
      </table>
      
           		<input type="hidden" id="selected_ben" name="selected_ben" value="0"></input>
 	
         <input type="hidden" id="total_charge_row" name="total_charge_row" ></input>    
 	     <input type="hidden" id="t1" name="t1" ></input>
 	  	<input type="hidden" id="net_qty" name="net_qty" ></input>
 	  	<input type="hidden" id="net_eqty" name="net_eqty" ></input>
 	  	<input type=hidden id="ADf_ANY" value=0></input>
 	  	<input type=hidden id="WC_CB_TOTAL" value=1254512></input>
 	  	<input type=hidden id="MAINT_CB_TOTAL" value=0></input>
 	  	<input type=hidden id="YESTERYR_CB" value=0></input>
  	  	<input type=hidden id="BENEFICIARY_SNO" value=0></input>
  	  	<input type=hidden id="maxsno" value=0></input>
  	  	
  	  	<input type=hidden id="billmonth" value=<%=Bill_month%>></input>
  	  	<input type=hidden id="billyear" value=<%=Bill_year%>></input>
  	 	 
   <input type=hidden id="pr_status" name="pr_status" value="0"> 


 	  </form>
 	  		  </body>
 	  		 
 	  		  </html>
      
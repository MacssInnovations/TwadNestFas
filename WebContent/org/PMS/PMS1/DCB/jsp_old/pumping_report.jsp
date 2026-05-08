 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page session="false" contentType="text/html;charset=windows-1252"%>
 <html>
  <head>
     
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pumping Return Report  </title>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
       <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
   
    </head>
   <%
   String month_text=request.getParameter("mv");
   String Year_text=request.getParameter("yv");
   String Ben_type=request.getParameter("ben_type");
   int col=0;
   %>
    <body  onload="data_show('show',4,1),flash()">
     <form name="billdemand" >
      <table  class="table"  id="data_table" width=100% align=center border=1  cellspacing="0" cellpadding="0">
        <tr >
          <td align="center" class="tdH" colspan="3">
             
              <b>Pumping Return Report </b>   
             
          </td>
        </tr>
        
       <tr>
       <td width=25%><font color="Black" size="2"><b>Beneficiary Name</b></font></td>
       <td colspan="2">
       <label id="ben_name"></label>
       </td>
       </tr>
        <tr>
       <td width=25%><font color="Black" size="2"><b>Beneficiary Type</b></font></td>
       <td colspan="2">
       <label id="bentypevalue"></label>
       </td>
       </tr>
        <tr>
       <td><font color="Black" size="2"><b>Month&Year</b></font></td>
       <td>
       <%=month_text%> /<%=Year_text%>   <font size="2" color="red">&nbsp;<label  id="msg"></label></font>
       </td>
       </tr>
       <tr>
      
      </table>
      
      <table  class="table" id="data_table" width=100% align=center border=1  cellspacing="0" cellpadding="3">
      		<tr >
      		    <td align="center"><font color="Black" size="2"><b>Sl.No</b></font></td>
       			<td align="center"> <font color="Black" size="2"><b>Meter Location</b></font></td>
     			<td align="center"><font color="Black" size="2"><b>Final Reading</b></font></td>
     			<td align="center"><font color="Black" size="2"><b>Initial Reading</b></font></td>
      			<td align="center"><font color="Black" size="2"><b>Difference(KL)</b></font></td>
      			
      			<% if (Integer.parseInt(Ben_type)>6) {col=14;			
      			%>      			
      			<td align="center"><font color="Black" size="2"><b>Multi Factor</b></font></td>
      			<%} else{
      					col=7; 
      					}
      			 %>
      			<td align="center"><font color="Black" size="2"><b>Total Consumption(KL)</b></font></td>
      			<% if (Integer.parseInt(Ben_type)>6) {%>
      			<td align="center"><font color="Black" size="2"><b>Min Bill <br>Qty(KL)</b></font></td>
      			<%} %>
      			<td align="center"><font color="Black" size="2"><b>Tariff Rate(Rs.)</b></font></td>
      			<% if (Integer.parseInt(Ben_type)>6)  { %>
      			<td align="center"><font color="Black" size="2"><b>Amount(Rs.)</b></font></td>
      			
      			<td align="center"><font color="Black" size="2"><b>Alloted Qty(KL)</b></font></td>
      			<%} %>
      			 <% if (Integer.parseInt(Ben_type)>6)  { %>
      			<td align="center"><font color="Black" size="2"><b>Excess Consumption(KL)</b></font></td>
      			<td align="center"><font color="Black" size="2"><b>Excess Rate(Rs.)</b></font></td>
      			<td align="center"><font color="Black" size="2"><b>Excess Amount(Rs.)</b></font> </td>
      			<%} %>
      			<td align="center"><font color="Black" size="2"><b>Total Amount(Rs.)</b></font> </td>
      		</tr>
      	 	<tbody id="data_tbody"  ></tbody>
    		  <tr>
      			 <td colspan="<%=col%>"><font color="Black" size="2"><b>Total Amount</b></font></td>
       			<td align=right>
      			 <label id="net_amount"></label>
    		   </td>
      			 </tr>
       <tr>
       <td colspan=15 align=center><input type=button value="Print"></input><input type=button value="Exit" onclick="window.close()"></input></td>
       	</tr>
      </table>
        
       	  <input type="hidden" id="fyear" name="fyear" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" ></input> 
      
    <input type=hidden id="pr_status" name="pr_status" value="0"> 
           
 	  <input type="hidden" id="t1" name="t1" ></input> 
 	  </form>
 	  		  </body>
 	  		 
 	  		  </html>
      
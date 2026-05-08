<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>BRS List</title>
     <link href="../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
     <script type="text/javascript" src="../scripts/BRS_ListAll.js"></script>   
  </head>
  <body onload="loadfun()">
  <form name="list">
  <%
 
            String type=request.getParameter("OB_Type"); 
  			System.out.println("type"+type);
		  if(type.equals("ob_list"))
		  {
			  
				int unit_id=Integer.parseInt(request.getParameter("acc_unit_id"));
	  			int office_id=Integer.parseInt(request.getParameter("office_code"));
	  			int cashbook_yr_from=Integer.parseInt(request.getParameter("cashbook_yr_from"));
	  			
	  			int cashbook_mn_from=Integer.parseInt(request.getParameter("cashbook_mn_from"));
	  			
	  			int cashbook_yr_to=Integer.parseInt(request.getParameter("cashbook_yr_to"));
	  			
	  			int cashbook_mn_to=Integer.parseInt(request.getParameter("cashbook_mn_to"));
	  		//	System.out.println("BankAccNo:::"+request.getParameter("cmbBankAccNo"));
	  			long BankAccNo = Long.parseLong(request.getParameter("BankAccNo"));
	  			//System.out.println("BankAccNo"+BankAccNo);
	  			//out.println("<input type='hidden' name='ob_type' id='ob_type' value='"+type+"'>");
	  			out.println("<input type='hidden' name='acc_unit_id' id='acc_unit_id' value="+unit_id+">");
	  			out.println("<input type='hidden' name='office_code' id='office_code' value="+office_id+">");
	  			out.println("<input type='hidden' name='cash_book_yr_from' id='cash_book_yr_from' value="+cashbook_yr_from+">");
	  			out.println("<input type='hidden' name='cash_book_mn_from' id='cash_book_mn_from' value="+cashbook_mn_from+">");
	  			out.println("<input type='hidden' name='cash_book_yr_to' id='cash_book_yr_to' value="+cashbook_yr_to+">");
	  			out.println("<input type='hidden' name='cash_book_mn_to' id='cash_book_mn_to' value="+cashbook_mn_to+">");
	  			out.println("<input type='hidden' name='BankAccNo' id='BankAccNo' value="+BankAccNo+">");
	  			 System.out.println("oblist");
		  }
		  else{
  			int unit_id=Integer.parseInt(request.getParameter("acc_unit_id"));
  			int office_id=Integer.parseInt(request.getParameter("office_code"));
  			int cashbook_yr_from=Integer.parseInt(request.getParameter("cashbook_yr"));
  			int cashbook_mn_from=Integer.parseInt(request.getParameter("cashbook_mn"));
  			//int cashbook_yr_to=Integer.parseInt(request.getParameter("cashbook_yr_to"));
  			//int cashbook_mn_to=Integer.parseInt(request.getParameter("cashbook_mn_to"));
  			long BankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
  			out.println("<input type='hidden' name='ob_type' id='ob_type' value='"+type+"'>");
  			out.println("<input type='hidden' name='acc_unit_id' id='acc_unit_id' value="+unit_id+">");
  			out.println("<input type='hidden' name='office_code' id='office_code' value="+office_id+">");
  			out.println("<input type='hidden' name='cash_book_yr_from' id='cash_book_yr_from' value="+cashbook_yr_from+">");
  			out.println("<input type='hidden' name='cash_book_mn_from' id='cash_book_mn_from' value="+cashbook_mn_from+">");
  		//	out.println("<input type='hidden' name='cash_book_yr_to' id='cash_book_yr_to' value="+cashbook_yr_to+">");
  		//	out.println("<input type='hidden' name='cash_book_mn_to' id='cash_book_mn_to' value="+cashbook_mn_to+">");
  			out.println("<input type='hidden' name='BankAccNo' id='BankAccNo' value="+BankAccNo+">");
  			 System.out.println("hhhhh");
		  }
  %>  
  <table id="Exsisting" border="1" width="100%"
             align="center">
        <tr class="tdH">
                 <th width="5%">
                   Select
                </th>              
                <th width="15%">
                    Bank Account Number
                </th>
                <th width="5%">
                    Sl No
                </th>
                <th width="5%">
                   CashBookYear
                </th>
                <th width="5%">
                    CashBookMonth
                </th>
                 <th width="10%">
                    PassBook Date
                </th>
                <th width="20%">
                    Reason
                </th>
                <th width="10%">
					Cheque No
                </th>
                 <th width="20%">
					Cheque Details
                </th>
                 <th width="10%">
					CR Amount
                </th>
                <th width="10%">
					DR Amount
                </th>
                <th width="5%">
					FollowUp Action Required
                </th>
        </tr>
        <tbody id="tb" class="table">
        </tbody>
        </table>  
        <table border="1" width="100%"
             align="center">
        <tr class="tdH" >
        <td align="center">
                <input type="button" name="cancel" id="cancel" value="Cancel" onclick="javascript:window.close()">
        </td>
        </tr>
      </table>
      </form>
      </body>
</html>

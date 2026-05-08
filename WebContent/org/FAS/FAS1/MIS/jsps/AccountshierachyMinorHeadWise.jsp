<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<%
	
	String Command="";
	String finYear = "";
	String majorheadSortOrder = ""; 
	String majorHead = ""; 
	String[] finYears = new String[10];
	try {
		Command =request.getParameter("Command");
		finYear = request.getParameter("finYear");
		majorheadSortOrder = request.getParameter("majorheadSortOrder");
		majorHead = request.getParameter("majorHead");
		finYears = finYear.split("-");

	} catch (Exception e) {
		e.printStackTrace();
	}
	/*  session.setAttribute("finYear", finYear); */ 
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style>
a {
	color: blue;
}

a:hover {
	text-decoration: underline;
	cursor: pointer;
}

a:active {
	text-decoration: underline;
	cursor: pointer;
}

@media print {
	#finYear {
		border: none;
	}
	#showCol {
		display: none;
	}
	#navi {
		display: none;
	}
	.td1 {
		width: 20%;
	}
	.td2 {
		width: 80%;
	}
}
</style>
<title>Financial Accounting System - MinorHeads</title>
<script type="text/javascript" charset=UTF-8
	src="../scripts/Accountshierachy_minorHead.js"></script>
<META HTTP-EQUIV="CACHE-CONTROL"
	CONTENT=" no-store, no-cache, must-revalidate">
<META HTTP-EQUIV="CACHE-CONTROL"
	CONTENT=" pre-check=0, post-check=0, max-age=0">
</head>
<body onload="load();">
	<form name="frmAccountshierachyMinorHeadWise" method="POST">
		<table>
			<tr id="parameters">
				<td>
				<input type="text" id="Command" style="display: none;" value="<%=Command%> " /> 
				<input type="text"
					id="finYear" style="display: none;" value="<%=finYear%> " /> <input
					type="text" id="majorsortOrder" style="display: none;"
					value="<%=majorheadSortOrder%> " /> <input type="text" id="finyearStart"
					style="display: none;" value="<%=finYears[0]%> " /> <input
					type="text" id="finyearEnd" style="display: none;"
					value="<%=finYears[1]%> " />
					<input type="text" id="majorHead" style="display: none;" value="<%=majorHead%> " />
					<input type="text" id="res" style="display: none;" /> </td>
				
			</tr>
		</table>

		<table class="tableForm">
			<thead>
				<tr>
					<th
						style="width: 20%; background: none repeat scroll 0 0 transparent; color: #000000; font-family: inherit; font-size: 140%; font-weight: bold; padding: 4px; text-align: center;">
						<img src="../../../../../images/twademblem.gif" alt="twad emblem"
						style="width: 71px; height: 68px;">
					</th>
					<th
						style="width: 80%; background: none repeat scroll 0 0 transparent; color: #000000; font-family: inherit; font-size: 140%; font-weight: bold; padding: 4px; text-align: center;">
						<h2>TAMILNADU WATER SUPPLY AND DRAINAGE BOARD</h2>
					</th>
				</tr>
				<tr>
					<th colspan="3"><%=majorHead %> For the Financial Year <%=finYear%></th>
				</tr>

			</thead>
		</table>
		<table cellspacing="0" cellpadding="6" class="printOpt">
			<tr id="navi">
				<td colspan="3"><img
					src="../../../../../images/right-arrowD.gif" /> <a
					href="Accountshierachy.jsp" id="load_Major_Heads" onclick="migration(this.id)"><b>Major Heads</b> </a></td>

			</tr>

		</table>



		<table id="mytable" align="center" cellspacing="3" cellpadding="2"
			border="1" width="100%">
			<tr class="tdH">
				<th>Minor Head</th>
				<th>Debit</th>
				<th>Credit</th>
				<th>Net</th>

			</tr>
			<tbody id="tbody" class="table">


			</tbody>
		</table>

	<div align="center" style="background-color: #F5F5F5;">
			<!-- <input type="button" value="EXIT" name="cancel" id="Exit"
				onClick="window.close()" class="button"> -->
				 <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
        
            <tr>
                <td>
                    <table align="center" cellspacing="3" cellpadding="2" border="1"  width="100%">
                     <tr class="tdH">
                        <td>
                            <table align="center" cellspacing="3" cellpadding="2"  border="0" width="100%">
                                <tr>
                                    <td width="30%">
                                        <div align="left">
                                            <div id="divpre" style="display:none"></div>
                                        </div>
                                    </td>
                                    <td width="40%">
                                        <div align="center">
                                            <table border="0">
                                                <tr>
                                                    <td>
                                                        <div id="divcmbpage" style="display:none">
                                                        Page&nbsp;&nbsp;<select name="cmbpage"  id="cmbpage" onchange="changepage()"></select>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div id="divpage"></div>
                                                    </td>
                                                  </tr>
                                                </table>
                                            </div>
                                    </td>
                                    <td width="30%">
                                        <div align="right">
                                                <div id="divnext" style="display:none"></div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                 </table>
        </td>
    </tr>
      <tr class="tdH">
      <td>
          <div align="center">
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
				
		</div>

	</form>
</body>
</html>
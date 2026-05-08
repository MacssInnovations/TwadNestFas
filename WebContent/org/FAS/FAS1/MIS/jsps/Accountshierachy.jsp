<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Financial Accounting System - Dash Board</title>

<style type="text/css">
#main {
	width: 980px;
	margin: auto;
	border-left: 1px solid #2C5A8B;
	border-right: 1px solid #2C5A8B;
}

#header {
	border-top: 5px solid #2C5A8B;
	height: 100%;
	margin: auto;
	overflow: hidden;
	/*width: 980px;*/
}

#header #content {
	padding: 8px;
	background-color: #EAEEF2;
	border: 0 solid black;
	height: 100%;
	margin: 0 auto;
	overflow: hidden;
	/*width: 980px;*/
}

#header #content #left {
	background: inherit;
	float: left;
}

#header #content #right {
	margin: auto;
	width: 90%;
	text-align: right;
}

#header #content #right span {
	/*text-shadow: 0px 0px 6px rgba(7, 2, 2, 0.75);*/
	/*line-height: 1em;
	text-shadow:0px 0px 0 rgb(196,196,196),1px 1px 0 rgb(152,152,152),2px 2px 0 rgb(109,109,109), 3px 3px 0 rgb(66,66,66),4px 4px 3px rgba(0,0,0,0.25),4px 4px 1px rgba(0,0,0,0.5),0px 0px 3px rgba(0,0,0,.2);
	text-shadow:0px 0px 0 rgb(188,188,188),1px 1px 0 rgb(137,137,137),2px 2px 0 rgb(86,86,86),3px 3px 0 rgb(35,35,35),4px 4px 0 rgb(-16,-16,-16), 5px 5px 0 rgb(-67,-67,-67),6px 6px 5px rgba(0,0,0,1),6px 6px 1px rgba(0,0,0,0.5),0px 0px 5px rgba(0,0,0,.2);
	text-shadow:0px 0px 0 rgb(188,188,188),-1px 0px 0 rgb(137,137,137),-2px 0px 0 rgb(86,86,86),-3px 0px 0 rgb(35,35,35),-4px 0px 0 rgb(-16,-16,-16), -5px 0px 0 rgb(-67,-67,-67),-6px 0px 5px rgba(0,0,0,1),-6px 0px 1px rgba(0,0,0,0.5),0px 0px 5px rgba(0,0,0,.2);*/
	font-size: 25px;
	color: rgba(128, 128, 0, 255);
	text-shadow: 2px 8px 4px rgba(0, 0, 0, 0.5), 0px -15px 35px
		rgba(255, 255, 255, 0.3);
}

#header #content #center span {
	/*text-shadow: 0px 0px 6px rgba(7, 2, 2, 0.75);*/
	line-height: 1em;
	/*text-shadow:0px 0px 0 rgb(196,196,196),1px 1px 0 rgb(152,152,152),2px 2px 0 rgb(109,109,109), 3px 3px 0 rgb(66,66,66),4px 4px 3px rgba(0,0,0,0.25),4px 4px 1px rgba(0,0,0,0.5),0px 0px 3px rgba(0,0,0,.2);
	text-shadow:0px 0px 0 rgb(188,188,188),1px 1px 0 rgb(137,137,137),2px 2px 0 rgb(86,86,86),3px 3px 0 rgb(35,35,35),4px 4px 0 rgb(-16,-16,-16), 5px 5px 0 rgb(-67,-67,-67),6px 6px 5px rgba(0,0,0,1),6px 6px 1px rgba(0,0,0,0.5),0px 0px 5px rgba(0,0,0,.2);*/
	text-shadow: 0px 0px 0 rgb(188, 188, 188), -1px 0px 0 rgb(137, 137, 137),
		-2px 0px 0 rgb(86, 86, 86), -3px 0px 0 rgb(35, 35, 35), -4px 0px 0
		rgb(-16, -16, -16), -5px 0px 0 rgb(-67, -67, -67), -6px 0px 5px
		rgba(0, 0, 0, 1), -6px 0px 1px rgba(0, 0, 0, 0.5), 0px 0px 5px
		rgba(0, 0, 0, .2);
	color: #FFFFFF;
	font-size: 28px;
}

#header #menu {
	padding: 6px;
	background-color: #2C5A8B;
	border: 0 solid black;
	height: 100%;
	margin: 0 auto;
	overflow: hidden;
	/*width: 980px;border-bottom: 30px solid #5F5F5F;*/
}

#header #menu a:link {
	color: #ffffff;
} /* unvisited link */
#header #menu a:visited {
	color: #ffffff;
} /* visited link */
#header #menu a:hover {
	color: #ffffff;
} /* mouse over link */
#header #menu a:active {
	color: #ffffff;
} /* selected link */
#body {
	/*width: 980px;*/
	height: 100%;
	margin: 0 auto;
	min-height: 300px;
	background-color: #F5F5F5;
	padding: 5px;
}

#body .box {
	height: 200px;
	display: block;
	padding: 5px;
	border: 4px solid #4C5A65;
	border-radius: 5px;
	color: #333;
	transition: all 0.3s ease-out;
	min-width: 298px;
	width: 97%;
	margin: 5px;
	background-color: #F5F5F5;
	overflow-y: auto;
	overflow-x: hidden;
}

#body .box .heading {
	border-bottom: 1px solid #D7DEE4;
	text-align: left;
	min-width: 298px;
	font-weight: bold;
}

#body #left_panel {
	float: left;
	background-color: #FFFFFF;
	width: 46%;
}

#body #middle_panel {
	display: inline-block;
	background-color: #FFFFFF;
}

#body #right_panel {
	float: right;
	background-color: #FFFFFF;
	min-width: 300px;
	width: 46%;
}

#footer {
	background-color: #2C5A8B;
	height: 40px;
	/*width: 980px;*/
	margin: 0 auto;
}

#footer #bottom_panel {
	color: #FFFFFF;
	font-size: 85%;
	height: 30px;
	line-height: 30px;
	text-align: center;
	line-height: 20px;
}

#footer #content {
	height: 100%;
	margin: 0 auto;
	padding-top: 4px;
	border-bottom: 5px solid #357ABF;
}

#footer #content a {
	color: #D1AC6B;
}

#roundedCorner {
	border-radius: 10px 10px 10px 10px;
	background-color: #10365D;
	width: 285px;
}

ul {
	list-style: none outside none;
	padding-left: 20px;
}

ul li:before {
	content: url(../../../../images1/right-arrowD.gif);
}

/* a:link {color: #5F5F5F;}  */ /* unvisited link */
a:visited {
	color: #5F5F5F;
} /* visited link */
a:active {
	color: #000000;
} /* selected link */
a:hover {
	color: #000000;
	TEXT-DECORATION: none;
	font-weight: none;
	background-color: silver;
	font-size: medium;
} /* mouse over link */

/*  Button Style  Start*/
input[type="button"], input[type="submit"], input[type="reset"] {
	font-family: inherit;
	font-size: 12.5px;
	text-transform: uppercase;
	cursor: pointer;
	box-shadow: inset 0 2px 3px #808080;
	font-weight: bold !important;
	letter-spacing: normal;
	padding: 1px 8px;
}

input[type="button"]:HOVER, input[type="submit"]:HOVER, input[type="reset"]:HOVER
	{
	background-image: -moz-linear-gradient(top, #57AA43, #78eb61);
	background-image: linear-gradient(to bottom, #57AA43, #78eb61);
}
/*  Button Style  End*/
</style>



<script type="text/javascript" src="../scripts/Accountshierachy.js"></script>


</head>
<body style="background-color: #E2E2E2;" onload="load();">
<form name="frmAccounts_hierachy_dashboard" method="POST">
	<br />
	<%
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		String hidfinYear="";

		try {
			ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs.getString("Config.DSN");
			String strhostname = rs.getString("Config.HOST_NAME");
			String strportno = rs.getString("Config.PORT_NUMBER");
			String strsid = rs.getString("Config.SID");
			String strdbusername = rs.getString("Config.USER_NAME");
			String strdbpassword = rs.getString("Config.PASSWORD");

		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":"
			//		+ strsid.trim();
ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());

			try {
				statement = connection.createStatement();
			} catch (SQLException e) {
			}
		} catch (Exception e) {
		}
		
		
	 /* hidfinYear = (String)session.getAttribute("finYear"); */  
	 
	%>
	<div id="main">
		<div id="header">
			<div id="content">
				 
				<div id="left">
					<div id="roundedCorner">
						<img alt="TWAD Board logo"
							src="../../../../../images1/twad_nest.png"
							style="width: 290px; height: 90px;">

					</div>
				</div>
				  
				<div id="right">
					    <span>Welcome to Financial Accounting System</span>
				</div>
				<div id="right" style="width: 600px;">
					    <span>Dash Board</span>
				</div>
			</div>
			<div id="menu">
				<!-- <a href="../../../../index.jsp">Home</a> -->
			</div>
		</div>

		<div id="body">
			<table cellspacing="1" cellpadding="2" border="1" width="100%">
				<tr align="left">
					<td class="table">
						<div align="left">
							Financial Year <font color="#ff2121">*</font>
						</div>
					</td>

					<td>
						<div>
						<%-- <input type="hidden" id=hidfinYear value="<%=hidfinYear %>"/> --%>
							<select name="txtfin_year" id="txtfin_year" tabindex="4"
								onchange="doFunction('load_Major_Heads',this.value);">
								<option value="">--select--</option>
								<%
								String finyearStart="";
								String finyearEnd="";
									statement = connection.createStatement();
									results = statement.executeQuery(
											"select DISTINCT(AG.FINYEARSTART||'-'||AG.FINYEAREND) AS FINANCIAL_YEAR,AG.FINYEARSTART,AG.FINYEAREND from FAS_HO_ANNUALGROUPING AG  order by financial_year");
									while (results.next()) {
										finyearStart=results.getString("FINYEARSTART");
									    finyearEnd=results.getString("FINYEAREND");
										out.println("<option value='" + results.getString("financial_year") + "'>"
												+ results.getString("financial_year") + "</option>");
									}
									/*  results.close();
									 statement.close(); */
								%>

							</select>
						</div>
					</td>
				</tr>
			</table>
		
			  <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
            <th>
              Major Head
            </th>
            <th>
             Debit
            </th>
            <th>
            Credit
            </th>
            <th>
            Net
            </th>
          </tr>
          <tbody id="tbody" class="table">
          
         
          </tbody>
        </table>


		</div>
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
		<div style="background-color: #F5F5F5;">
			<br />
		</div>
		<div id="header"></div>
	</div>
	</form>
</body>
</html>
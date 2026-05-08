<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Calculate Depreciation</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/FAS/FAS1/Masters/scripts/Calc_Depreciation.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
           
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
     <!-- to avoid future date the above script used-->        
    <script language="javascript" type="text/javascript">
				function closeWindow(){                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
				
					function loadyear_month()
			         {    	  
			    	  var today = new Date();
			  		var day = today.getDate();
			  		var month = today.getMonth();
			  		month = month + 1;
			  		var year = today.getYear();
			  		var year1 = 0;
			  		var financialyear = 0;
			  		var financialyear1 = 0;
			  		if (year < 1900)
			  			year += 1900;
			  		if (month < 4) {
			  			year1 = year - 1;
			  		} else {
			  			year1 = year + 1;
			  		}

			  		if (month < 4) {
			  			
			  	
			  			financialyear = year1 + "-" + year;
			  			financialyear1 = (year1-1) + "-" + (year-1);
			  			financialyear2 = (year1-2) + "-" + (year-2);
			  		} else {
			  			financialyear = year + "-" + year1;
			  			financialyear1 = (year-1) + "-" + (year1-1);
			  			financialyear2 = (year-2) + "-" + (year1-2);
			  		}
			  		for(var k=0;k<3;k++)
			  		{
			  			if(k==0)
			  			{
			  				var se = document.getElementById("cmbFinancialYear");
			  		  		var op = document.createElement("OPTION");
			  		  		op.value = financialyear2;
			  		  		var txt = document.createTextNode(financialyear2);
			  		  		op.appendChild(txt);
			  		  		se.appendChild(op);
			  			}else if(k==1)
			  			{
			  				var se = document.getElementById("cmbFinancialYear");
			  		  		var op = document.createElement("OPTION");
			  		  		op.value = financialyear1;
			  		  		var txt = document.createTextNode(financialyear1);
			  		  		op.appendChild(txt);
			  		  		se.appendChild(op);
			  		  		
			  			}
			  			else if(k==2)
			  			{
			  				var se = document.getElementById("cmbFinancialYear");
			  		  		var op = document.createElement("OPTION");
			  		  		op.value = financialyear;
			  		  		var txt = document.createTextNode(financialyear);
			  		  		op.appendChild(txt);
			  		  		se.appendChild(op);
			  		  		
			  			}  
			  		}    
			  		document.getElementById("cmbFinancialYear").value=financialyear;          
			         }
				
    </script>
  </head>
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');callServer('loadMajor');">
  <form name="fromDEpCalc" id="fromDEpCalc" method="post">
  <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="3" class="tdH" align="center"><b>Calculate - Depreciation Value </b></td>
       </tr>
       
        <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null,rsbank=null;
  PreparedStatement ps=null,ps2=null,psbank=null;
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
       
         <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                 
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="common_LoadOffice(this.value);">
                
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
                <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">                
                </select>
              </div>
            </td>
          </tr>      
       	  <tr>
          <td class="table">
            <div align="left">
               Financial Year
                <font color="#ff2121">*</font>
              </div></td>
            <td class="table">
         <select size="1" name="cmbFinancialYear" id="cmbFinancialYear" tabindex="3">
                    <option value=0>-- Select --</option>
                     <%
                        Statement st=con.createStatement();
                        rs=st.executeQuery("select financial_year from cash_book_control");
                        while(rs.next())
                        {
                            out.println("<option value='"+rs.getString("financial_year").substring(0,4)+"-"+rs.getString("financial_year").substring(7,9)+"'>"+rs.getString("financial_year")+"</option>");
                        }
                    %>
                    </select></td>
       	</tr>
       	<tr class="table">
                <td>
                  <div align="left">
                     Major Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbmajorasset" id="cmbmajorasset" tabindex="3" onchange="callDepRate('loadDepRate')">
                    <option value=0>-- Select Major Asset Code --</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
              <tr >
              <td class="table">  <div align="left">
                     Depreciation Rates
                    <font color="#ff2121">*</font>
                  </div></td>
             
              <td class="table"> 
              <input type="hidden" name="cmbdepRateCode" id="cmbdepRateCode" maxlength="9">
                <input type="hidden" name="cmbdepRateVAl" id="cmbdepRateVAl" maxlength="9">
                
                <label id="cmbdepRate" ></label>
                <input type="button" id="goBtn" name="goBtn" onclick="listData();" value="GO" />
                </td>
                     </tr>
       	</table>
       	 <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                 <tr class="tdH">
		<th>Asset Code</th>
		<th>Asset Description</th>
		<th>Opening Balance Value (in Rs.)</th>
		<th>CY Depreciation Amount</th>
		<th>After Depreciated Cost</th>
	</tr>
       	<tbody id="tblList" align="center" class="table">
             </tbody>
            </table>
            <table align="center" cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <td>
                    <div align="center">
                             <input type="button" id="SubTotally" name="SubTotally" value="Submit" onclick="SubmitValues();"></input>
                            <input type="button" name="Cmdclaer" value="CLEAR" onclick="window.location.reload();">
                            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
                    </div>
                  </td>
                </tr>
      </table> 
       	</form>
       

</body>
</html>
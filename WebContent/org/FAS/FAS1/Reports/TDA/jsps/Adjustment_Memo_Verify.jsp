<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Verification Of Adjustment_Memo</title>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/AdjustmentMemo_verification.js"></script>
            
   	<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
            
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
            <script type="text/javascript" language="javascript">
     function loadyear_month()
     {
   
		    var today= new Date(); 
		     var day=today.getDate();
		     var month=today.getMonth();
		     month=month+1;
		     var year=today.getYear();
		     if(year < 1900) year += 1900;
		   
		     document.tda_tca_grid.txtCB_Year.value=year;
             document.tda_tca_grid.txtCB_Month.value=month;
     }
     function closeWindow()
     {                
	         window.open('','_parent','');                
	         window.close(); 
	         window.opener.focus();
     }
    </script>
</head>
<%
	String s1 = request.getContextPath();
%>
<%
  
	    Connection con=null;
	    ResultSet rs=null,rs2=null;
	    Statement st=null;
	    PreparedStatement ps=null;
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
 
<body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')">
<form name="tda_tca_grid" id="tda_tca_grid" method="post" >
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Verification Of Adjustment Memo</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
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
                        tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
          <tr align="left">
            <td class="table">
              <div align="left">Year </div>
            </td>
            <td>
              <div align="left">
                 <select name="txtCB_Year" id="txtCB_Year" >
                  <!-- <option value="2012">2012</option>
                  <option value="2013">2013</option>
                   <option value="2014">2014</option>
                    <option value="2015">2015</option>
                    <option value="2016">2016</option>
                    <option value="2017">2017</option>
                    <option value="2018">2018</option> -->
                    
                    <%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year,CB_FROM_DATE_FOR_APRIL, CB_TO_DATE_FOR_APRIL," +
					                        		  " CB_FROM_DATE_FOR_MARCH," +
					                        		  " CB_TO_DATE_FOR_MARCH from cash_book_control order by financial_year");
					                        while(rs.next())
					                        {
					                            String year1=rs.getString("financial_year");
					                            String[] cbyear=year1.split("-");
					                        	System.out.println("cbyear==>"+cbyear[0]);
					                        	String from_apr=rs.getString("CB_FROM_DATE_FOR_APRIL");
					                        	String[] fr_apr=from_apr.split("-");
					                        	String to_apr1=rs.getString("CB_TO_DATE_FOR_APRIL");
					                        	String[] to_apr=to_apr1.split("-");
					                        	if((fr_apr[0]=="01" && fr_apr[1]=="03") && (fr_apr[0]=="31" && fr_apr[1]=="03"))
					                        	{
					                        		//out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                            out.println("<option value='"+cbyear[0]+"'>"+cbyear[0]+"</option>");
					                        	}
					                        	else
					                        	{
					                        		out.println("<option value='"+cbyear[1]+"'>"+cbyear[1]+"</option>");
					                        	}
											    
											   
					                        }
                       %>
                 </select>
                 <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
                <input type="button" name="gobtn" id="gobtn" value="Go" onclick="callGrid();"></input>
              </div>
            </td>
            
          </tr>
          
         
          
          <tr align="left">
            <td class="table">
              <div align="left">Particulars
             <font color="#ff2121">*</font></div>
            </td>
            <td>
             <textarea name="txtParticular" id="txtParticular" cols="70"  rows="3"></textarea>
            </td>
          </tr>
         
        </table>
      </div>
      
         
      <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
             <div id="grid_one" style="display:block">
            <table id="mytable1" cellspacing="3" cellpadding="2"
                   border="0" width="100%">
              <tr class="table">
              	<th>Month</th>
                <th>Head Of Account</th>
                <th>Transaction DR Amount</th>
                <th>Adj_Memo DR Amount</th>
                <th>Transaction CR Amount</th>
                <th>Adj_Memo CR Amount</th>
                <th>Transaction NET Amount</th>
                <th>Adj_Memo NET Amount</th>
                <th>Difference</th>
                                      
              </tr>
              <tbody id="grid_body1" class="table" align="left" >
              </tbody>
            </table>
          </div>
            
          </tr>
         
        </table>
      
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center" style="display:none" id="one_id">
              <input type="button" value="Verify" id="btnSubmit" onclick="verify_btn();"></input>
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
             <div align="center" style="display:block" id="two_id">
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>  
            <div align="center" style="display:none" id="three_id">
             <input type="button" value="Verified" id="btnverified" disabled="disabled"></input>
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div> 
          </td>
        </tr>
      </table>
    </form>
    </body>
</html>
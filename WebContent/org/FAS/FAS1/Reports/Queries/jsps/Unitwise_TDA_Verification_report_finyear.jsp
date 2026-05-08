<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Verification Of TDA/TCA Report (Single Unit)</title>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
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
<body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS')">
<form name="tda_tca_grid_fin" id="tda_tca_grid_fin" method="post" action="../../../../../../tda_tca_verify_report?command=singleUnit" >
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Verification Of TDA/TCA Report (Single Unit)</strong>
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
              <div align="left">Cash Book Year </div>
            </td>
            <td>
              <div align="left">
                 <select name="txtCB_Year" id="txtCB_Year" >
                 <!-- <option value="2018">2018</option>
                   <option value="2017">2017</option>
                   <option value="2016">2016</option>
                    <option value="2015">2015</option>
                   <option value="2014">2014</option>
                  <option value="2013">2013</option>
                  <option value="2012">2012</option>
                  <option value="2011">2011</option> -->
                  
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
               </div>
            </td>
            
          </tr>
          
         
         
        </table>
      </div>
      
         
      
          </div>
            
          </tr>
         
        </table>
      
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center"  id="one_id">
              <input type="submit" value="Submit" id="btnSubmit" ></input>
              
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
             
          </td>
        </tr>
      </table>
    </form>
    </body>
</html>
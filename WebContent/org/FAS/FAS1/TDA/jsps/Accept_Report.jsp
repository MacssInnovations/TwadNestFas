<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>TDA/TCA(Accepting) Report</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
  
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
    
    
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
  
    
      <script language="javascript" type="text/javascript" src="../scripts/Accept_Report.js"></script>
    <script type="text/javascript" language="javascript">
    function loadyear_month()
    {       
        var today= new Date(); 
        var day=today.getDate();
        var month=today.getMonth();
        month=month+1;
        var year=today.getYear();
        if(year < 1900) year += 1900;
       
        document.TDA_Accept_Report.txtCB_Year.value=year;
        document.TDA_Accept_Report.txtCB_Month.value=month;        
    }
    function ChooseReptype(id)
    {
        var dispsupnochosen1=document.getElementById("dispsupno1");
        var dispsupnochosen2=document.getElementById("dispsupno2");

        if(document.TDA_Accept_Report.reporttype[0].checked==true)
        {
       	     dispsupnochosen1.style.display="none";
                dispsupnochosen2.style.display="none";
        }
        else if(document.TDA_Accept_Report.reporttype[1].checked==true)
        {
       	 dispsupnochosen1.style.display="block";
            dispsupnochosen2.style.display="block";
            alert("Enter the Supplement Number");
        }
    }
    function dispsuppno()
    {
   	    var dispsupnochosen3=document.getElementById("dispsupno3");
        var dispsupnochosen4=document.getElementById("dispsupno4");
        var dispsupnochosen1=document.getElementById("dispsupno1");
        var dispsupnochosen2=document.getElementById("dispsupno2");

        if(document.getElementById("txtCB_Month").value==3)
        {
       	 dispsupnochosen3.style.display="block";
       	 dispsupnochosen4.style.display="block";
        }
        else 
        {
       	 dispsupnochosen3.style.display="block";
       	document.TDA_Accept_Report.reporttype[0].checked=true;
       	 dispsupnochosen4.style.display="none";
       	dispsupnochosen1.style.display="none";
       	dispsupnochosen2.style.display="none";
        }
    }
    </script>
  </head>
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');" >
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">TDA/TCA(Accepting) Report</font>
          </div>
        </td>
      </tr>
</table>
<%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      PreparedStatement ps=null,ps2=null,ps3=null;
		      ResultSet results=null;
		      ResultSet results1=null,rs3=null;
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
  <% 
           HttpSession session=request.getSession(false);
            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
              
            System.out.println("user id::"+empProfile.getEmployeeId());
            int empid=empProfile.getEmployeeId();
            int  oid=0;
            String oname="";
   
            try
            {
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oid=results.getInt("OFFICE_ID");
                         }
                    results.close();
                    ps.close();
                    ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OFFICE_NAME");
                          }
                    results.close();
                    ps.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
   
         %>
<form name="TDA_Accept_Report" method="POST" action="../../../../../TDA_Accept_Report" onsubmit="return nullcheck()">
<div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
		                         <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);">        
                         	<option value="0">select</option>
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
                          <option value="0">select</option>
                        </select>
                      </div>
                    </td>
             </tr>
                   <tr align="left">
		              <td class="table">
			          	    <div align="left">
			              	     Cash Book Year <font color="#ff2121">*</font>
			              	</div>
		               </td>
		              <td>
			              	<div align="left">
			                  <input type="text" name="txtCB_Year" id="txtCB_Year" value="2010"  maxlength="4" size="5" onkeypress="return numbersonly(event)"/>
			                </div>
		               </td>
		           </tr>
		        <tr align="left">
			          <td class="table">
			          	<div align="left">
			              	Cash Book Month
			          	</div>
			          </td>
			          <td>
				       	<div align="left">
				             <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="dispsuppno()">
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
                 <tr class="table">
	                <td width="30%">
	                  <div align="left">
	                     Type
	                    <font color="#ff2121">*</font> 
	                  </div>
	                </td>
	                <td>
	                  <div align="left">
	                    <select name="cmbJournal_type" id="cmbJournal_type">
	                      <option value="">Select Type</option>
                          <option value=1>TDA</option>
                          <option value=2>TCA</option>
	                       </select>                                           
	                  </div>
	                </td>
	              </tr>    			          
		        
		        <!--<tr class="table">
                <td>
                  <div align="left">
                    Memo Advice No.
                  </div>
                </td>
                <td>
                  <div align="left" >
                      <select name="txtAdvice_No" id="txtAdvice_No" name="txtAdvice_No">
                        <option value="0">-- Select Number -- </option>
                           
                      </select>
                  </div>
                </td>
              </tr>              
		        
            -->
             <tr align="left">
          <td class="table">
            <div align="left">Report Type </div>
          </td>
          <td>
           <div align="left" id="dispsupno3" style="display:block">
                <input type="radio" name="reporttype" id="reporttype" value="1" checked onclick="ChooseReptype(this.value);"/>Regular &nbsp;
                </div>
                <div align="left" id="dispsupno4" style="display:none">
                <input type="radio" name="reporttype" id="reporttype" value="3" onclick="ChooseReptype(this.value);"/> Supplement 
            </div>
          </td>
        </tr> 
        <tr align="left">
            <td class="table">
              <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">
                Supplement Number 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div id="dispsupno2" name="dispsupno2" style="display:none">
                <input type="text" name="supno" id="supno" size=2 value= 0>     
              </div>
              
            </td>
          </tr>  
            </table>
      </div>
       <br>
       
     <br> 
      
          
        <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
		
	      <tr class="tdH">
		      <td>
		          <div align="center">
		             <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                        &nbsp;&nbsp;&nbsp; 
		         	<input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="self.close()"/>
		     	  </div>
		      </td>
	      </tr>      
      	</table>
</form>
</body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>BRS OB Report</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>         
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>   
    <script type="text/javascript" src="../scripts/BRS_NonTwadReport.js"></script>   
    <script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmBRSreportNonTwad.txtCB_Year.value=year;
            document.frmBRSreportNonTwad.txtCB_Month.value=month;            
          //  document.frmBRSreportNonTwad.txtPassBook_date.value=day+"/"+month+"/"+year;
      }  
       function numbersonly1(e, t) {
    		var unicode = e.charCode ? e.charCode : e.keyCode;
    		if (unicode == 13) {
    			try {
    				t.blur();
    			} catch (e) {
    			}
    			return true;

    		}
    		if (unicode != 8 && unicode != 9) {
    			if (unicode < 48 || unicode > 57)
    				return false
    		}
    	}  
    </script>    
  </head>
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
		    
		               // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		    					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		                Class.forName(strDriver.trim());
		                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		      }
		      catch(Exception e)
		      {
		        System.out.println("Exception in connection...."+e);
		      }
		     
		      
  %>
  <body onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');" >
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">BRS OB Report</font>
          </div>
        </td>
      </tr>
    </table>
    
    
    <form name="frmBRSreportNonTwad" id="frmBRSreportNonTwad" method="post" action="/BRS_NonTWADReport.jsp">
       
		<table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="table">
                <td>
                  <div align="left" >
                  	  Accounting Unit Code  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);LoadBankAccountNumber();">
    
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                    </select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
	            <td>
	              <div align="left">Cash Book Year &amp; Month <font color="#ff2121">*</font></div>
	            </td>
	            <td>
	              <div align="left">
	                <input type="text" name="txtCB_Year" id="txtCB_Year"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly1(event,this)"></input>	                 
	                <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
	                  <option value="">Select Month</option>
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
	           <tr align="left" class="table">
          <td >
          <div align="left">
              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong></strong>
              </td>
              <td>
                                   <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_Bill_ListAll.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_Bill_ListAll.txtTo_date);"
                         alt="Show Calendar"></img>
            </div>
          </td>          
        </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                     Bank Name 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankName" id="txtBankName"  tabindex="6"  
                          style="background-color: #ececec"  readonly="readonly"  size="30"/>
                   
                    <input type="hidden" name="txtBankID" id="txtBankID"    
                          style="background-color: #ececec"  readonly="readonly"  size="30"/>
                                             
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                     Branch Name  
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBranchName"  id="txtBranchName" 
                     size="35"  style="background-color: #ececec" readonly="readonly" />
                     
                    <input type="hidden" name="txtBranchID"  id="txtBranchID" 
                     size="35"  style="background-color: #ececec" readonly="readonly" />
                     
                  </div>
                </td>
              </tr>
              
              
             
             <tr class="table">
                <td>
                  <div align="left">Reason</div>
                </td>
                <td>
                  <div align="left">
                  <!--  <textarea name="txtParticular" id="txtParticular" tabindex="8" cols="70"  onkeypress="return check_leng('particulars',this.value);"
                              rows="3"></textarea>  -->
                    <select name="txtParticular" id="txtParticular" >
                    <option value="">--Select Reason--</option>
                    <%
                     try
                                     {
                                            ps=con.prepareStatement("select TRANS_CODE,TRANS_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_TYPE='NT' ");
                                            rs=ps.executeQuery();
                                            while(rs.next())
                                            {
                                               // out.println("<option value="+rs.getInt("TRANS_CODE")+" selected>"+rs.getString("TRANS_DESC")+"</option>");
                                               out.println("<option value="+rs.getString("TRANS_CODE")+">"+rs.getString("TRANS_DESC")+"</option>");
                                            }
                                        
                                     } 
                                     catch(Exception e)
                                     {
                                            System.out.println("Exception in Journal combo..."+e);
                                     }
                                     finally
                                     {
                                            rs.close();
                                            ps.close();
                                     }  
                    %>
                    </select>
                  </div>
                </td>
              </tr>
              
              
        </table>
        <div align="center">
         <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="table">
        <td align="left">Report Option:</td>
        <td colspan="3" align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
          HTML
        </td>
      </tr>
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
        </table>
      </div>
    </form>
  </body>
</html>
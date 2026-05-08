<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>TPA Acceptance List</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/TPA_Acceptance_List.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
    <script type="text/javascript" language="javascript">
    function loadyear_month()
    {
       
	        var today= new Date(); 
	        var day=today.getDate();
	        var month=today.getMonth();
	        month=month+1;
	        var year=today.getYear();
	        if(year < 1900) year += 1900;
	       
	        document.tpa_acceptance_list.txtCB_Year.value=year;
	        document.tpa_acceptance_list.txtCB_Month.value=month;
        
    }
    </script>
  </head>
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');" >
  <form name="tpa_acceptance_list" method="POST">
  <%
		    Connection con=null;
		 	ResultSet rs=null,rs2=null;
		 	PreparedStatement ps=null,ps2=null;
		  	ResultSet results=null;
		  	ResultSet results1=null;
		  	ResultSet results2=null;
		  	 DecimalFormat df=new DecimalFormat("#0.00");
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
  
  <% try{
	        HttpSession session=request.getSession(false);
	        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
		    System.out.println("user id::"+empProfile.getEmployeeId());
		    int empid=empProfile.getEmployeeId();
		    int  oid=0;
		    String oname="",FAS_SU="";
		    if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
		         FAS_SU="YES";
		    else
		         FAS_SU="NO";
	   
	   
		  
   
   %>
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>List of TPA Acceptance Raised</strong>
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                   
                    <% 
                   /* String office_level_id = "";
                    int unitid=0;
                    int office_id = 0;
                    ResultSet rs6 = null;
	       			PreparedStatement ps6 = null,ps1=null;	
                        	
                       
                    
             try
            {
                System.out.println("*********************************inside jsp********************************BEFORE"+oid);
                
              
                System.out.println("*******************************inside jsp**********************************office_level_id"+office_level_id);
               
                            System.out.println("****************************inside jsp*************************************Enter"+office_level_id);
			                ps1 =
			                con.prepareStatement("select REGION_OFFICE_ID  from FAS_TPA_AUDIT_ALL_OFFICES_VIEW  where OFFICE_ID=?");
			                ps1.setInt(1, oid);
			                rs = ps1.executeQuery();
			                if(rs.next()) 
			                {
			                    oid=rs.getInt("REGION_OFFICE_ID");
			                 }
                String RegionSql=   " SELECT ACCOUNTING_UNIT_ID," +
       								" accounting_unit_name " +
									" FROM " +
  
  									"(SELECT accounting_unit_id," +
    								" accounting_unit_name " +
  									" FROM fas_mst_acct_units " +
  									" WHERE accounting_unit_office_id IN "+
    								" (SELECT office_id " +
    								" FROM FAS_TPA_AUDIT_ALL_OFFICES_VIEW " +
    								" WHERE region_office_id=?"+
    								" ))ORDER BY accounting_unit_name ";
                
                System.out.println("RegionSql"+RegionSql);
                try {
                ps6 = con.prepareStatement(RegionSql);
              	ps6.setInt(1, oid);
                rs6 = ps6.executeQuery();
                
                
                
                while(rs6.next())
                               {
                
                 
                
                    out.println("<option value="+rs6.getInt("accounting_unit_id")+" >"+rs6.getString("accounting_unit_name")+"("+rs6.getInt("accounting_unit_id")+")"+"</option>");

                   
                  
                    System.out.println(".."+rs6.getInt("accounting_unit_id"));
                              System.out.println(".."+rs6.getString("accounting_unit_name"));          
                   
                    }
                    }
                   catch(Exception e)
                        {
                            System.out.println("Exception is"+e);
                        }
                
                //}
            }
             catch(Exception e) {
             						System.out.println(e);
     
             					}
                    */
                     %>
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
              <tr class="table">
                <td width="30%">
                  <div align="left">
                   Transfer Proforma Type
                    <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="proformatype" id="proformatype" onchange="deleteGrid();">
                      <option value=""> Transfer Proforma Type</option>
                     <option value="TPAAC1">TPA Pending For Acceptance(Credit)</option>
                      <option value="TPAAD1">TPA Pending For Acceptance(Debit)</option>
                      
                       <option value="TPAAC2">TPA Accepted Against a Month(Credit)</option>
                      <option value="TPAAD2">TPA Accepted Against a Month(Debit)</option>
                      
                       <option value="TPAAC3">TPA Accepted During The Month(Credit)</option>
                      <option value="TPAAD3">TPA Accepted During The Month(Debit)</option>
                       </select>                                           
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Voucher Status
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbStatus" id="cmbStatus" >
                      <option value="L">Alive</option>
                      <option value="C">Cancelled</option> 
                    </select>
                  </div>
                </td>
              </tr>                           
        </table>
          </div> 
    <br>
       <table cellspacing="1" cellpadding="1" border="0" width="100%">
       <tr align="left" class="tdH"> <th>Search By Month or Date</th></tr>
        <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month&nbsp;&nbsp;<strong>:</strong>
	          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
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
          
           <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="call('get')"/>
           </div>
          </td>
        </tr>
        <tr align="left">
          <td class="table">
          <div align="left">
              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong>
                    <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.tpa_acceptance_list.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.tpa_acceptance_list.txtTo_date);"
                         alt="Show Calendar"></img>
            <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  onclick="call('getbydate')"/>
            </div>
          </td>          
        </tr>
     </table>
     <br>
     <div style="display:block" id="firstDiv">
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
              Orginating Unit Name
            </th>
            <th>
              Org Voucher Number
            </th>
            <th>
              Org Voucher Date
            </th>
             <th>
            Particulars
            </th>
             <th>
            Reason
            </th>
            <th>
            Total Amount
            </th>
           <th>
            Show Details ?
            </th>
           
          </tr>
          <tbody id="grid_body" class="table">          
          </tbody>
        </table>
        </div>
        
        <div style="display:none" id="secDiv">
     <table id="mytable_sec" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
              Orginating Unit Name
            </th>
            <th>
              Org Voucher Number
            </th>
            <th>
              Org Voucher Date
            </th>
            <th>
             Accepting Sl No
            </th>
            <th>
              Accepting Sl Date
            </th>
             <th>
            Particulars
            </th>
             <th>
            Reason
            </th>
            <th>
            Total Amount
            </th>
           <th>
            Show Details ?
            </th>
           
          </tr>
          <tbody id="grid_body_sec" class="table">          
          </tbody>
        </table>
        </div>
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
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="javascript:window.close()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
      <%}catch(Exception e){out.println(e);} %>
  </body>
</html>
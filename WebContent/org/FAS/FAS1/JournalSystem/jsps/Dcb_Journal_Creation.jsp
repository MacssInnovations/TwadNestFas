<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
	<title>DCB Journal Creation  | TWAD Nest - Phase II </title>
	<link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../scripts/Dcb_Journal_Creation_js.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
     	
 	<script type="text/javascript" language="javascript">
	    function loadDate()
        {
               // alert("asdf");
        	 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear(); 
                 if(year < 1900) year += 1900;                
                 document.Dcb_Journal.txtCrea_date.value=day+"/"+month+"/"+year;    
                 document.Dcb_Journal.cashbookyear.value=year;
                 
                  var month1;
                  if(month==01){month1="Jan"}
                  else if(month==02){month1="Feb"}
                  else if(month==03){month1="Mar"}
                  else if(month==04){month1="Apr"}
                  else if(month==05){month1="May"}
                  else if(month==06){month1="Jun"}
                  else if(month==07){month1="Jul"}
                  else if(month==08){month1="Aug"}
                  else if(month==09){month1="Sep"}
                  else if(month==10){month1="Oct"}
                  else if(month==11){month1="Nov"}
                  else if(month==12){month1="Dec"}
                  document.Dcb_Journal.cashbookmonth.value=month1;
                 call_date(document.Dcb_Journal.txtCrea_date); 
                 
                 
                 
                 
        }
       
</script>
</head>
<%
  
	   //Connection con=null;
	  //  ResultSet rs=null,rs2=null;
	    Statement st=null;
	   // PreparedStatement ps=null;
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
	             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	            // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
	    
	             Class.forName(strDriver.trim());
	             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	    }
	    catch(Exception e)
	    {
	        	 System.out.println("Exception in connection...."+e);
	    }
		      
  %>
<body onload="LoadAccountingUnitID_Create('LIST_ALL_UNITS');setTimeout('loadDate()', 300);" bgcolor="rgb(255,255,225)"> 
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">DCB Journal</font>
            <div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; 
		left: 378px; width: 212px; height: 6px;" left=100 top=100>
		<input type="image" name="img1" id="img1" src="../../../../../images/Loading.gif" 
		height="200"></div>
          </div>
        </td>
      </tr>
    </table>
 <form name="Dcb_Journal" id="Dcb_Journal" method="post" action="../../../../../Dcb_Journal_Creation?Command=Add" onsubmit="return checkRadio();" >
 <!-- <form name="Dcb_Journal" id="Dcb_Journal" method="post" action="../../../../../Dcb_Journal_Creation?Command=Add" onsubmit="return checkRadio();" >  -->
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
                        <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                            tabindex="1" onChange="common_LoadOffice_New(this.value);">
                      <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
                             //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                            //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                            String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                            ps=con.prepareStatement(getWing);
                            ps.setInt(1,oid);
                            ps.setInt(2,empid);
                            ps.setInt(3,oid);
                            rs=ps.executeQuery();
                          
                              if(rs.next())
                              {
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                              unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                              
                              System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                              System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                              System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                              
                              }
                          System.out.println(oid+" "+oname);
                          ps.close();
                          rs.close();
                          }
                              else
                              {
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                ps.setInt(1,oid);
                                rs=ps.executeQuery();
                                  if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  }
                                  ps.close();
                                  rs.close();
                              }
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
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
                        
                        
                        <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
                try
                {
                   if(oid==5000)
                    {
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"</option>");
                    }
                    else
                    {
                        
                    	ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
                        ps.setInt(1,unitid);
                        rs=ps.executeQuery();
                        //out.println("<option value="+oid+">"+oname+"</option>");
                        while(rs.next())
                        {
                        	int old_offid=0;

                        	 ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=?");
                        	                             ps2.setInt(1,empid);
                        	                             rs2=ps2.executeQuery();
                        	                             while(rs2.next())
                        	                             {
                        	                            	 old_offid=old_offid+1;
                        	                             }
                        	                        	if(old_offid !=0)
                        	                        	{
                        	                        		ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? ");
                        	                        	}
                        	                        	else if(old_offid==0)
                        	                        	{
                        	                             ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('CL','NC','RD')");
                        	                        	}
                        	//ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
                        ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                        rs2=ps2.executeQuery();
                        if(rs2.next())
                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");
                        }
                    }
                    
                } 
                catch(Exception e)
                {
                System.out.println("Exception in Office combo..."+e);
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
              
               <tr class="table">
                    <td>
                      <div align="left">
                        Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                      
                      
                       <input type="text" name="txtCrea_date" id="txtCrea_date"  readonly="readonly"
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);loadDate();dateCheck(this);"
                               onchange="loadDate();"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.Dcb_Journal.txtCrea_date,1);"
                             alt="Show Calendar"></img>  
                                            
                      </div> 
                    </td>
              </tr>
              <tr class="table">
	              	<td>
	                      <div align="left">
	                        Cash Book Year
	                        <font color="#ff2121">*</font>
	                      </div>
	                </td>
	                <td align="left">
	               		 <input type="text" name="cashbookyear" id="cashbookyear" readonly="readonly"/>
	                </td>
              </tr>
              <tr class="table">
	              	<td>
	                      <div align="left">
	                        Cash Book Month
	                        <font color="#ff2121">*</font>
	                      </div>
	                </td>
                        <td align="left">
                          <input type="text" name="cashbookmonth" id="cashbookmonth" readonly="readonly"/>
                        </td>
                  </tr>
            </table>
    </div>
    <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">   
                <input type="button" name="butGo" id="butGo" value="GO" onclick="callGo();"/>                     
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    <div id="grid" style="display:block">
            <table id="mytable" cellspacing="3" cellpadding="2"
                   border="0" width="100%">
              <tr class="table" >
                   
                    <th align="left">Scheme Type</th>
                    <th align="left">Account Head Code</th>
                    <th align="left">SL Type</th>
                    <th align="left">SL Code</th>
                    <th align="left">Total CR Amount</th>  
                    <th align="left">Particulars</th>
                    <th align="left">Show Details?</th>                       
              </tr>
              <tbody id="grid_body" class="table" align="left" >
              </tbody>
            </table>
    </div>
     <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">   
                    <input type="submit" name="butGo1" id="butGo1" value="Submit" disabled/>                     
              </div>
            </td>
          </tr>
        </table>
      </div>
  </form>

</body>
</html>
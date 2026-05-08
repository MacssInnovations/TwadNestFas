<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>RJV_for_tda_tca_rejected(Supplement)</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script language="javascript" type="text/javascript" src="../scripts/RJV_for_tda_tca_rejected_supplement.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>   
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>  
   <!-- <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  -->
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" language="javascript">
    function loadDate()
    {
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
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
           //  document.RJVRejected.txtCrea_date.value="31/03/"+year;                
    }
    </script>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
   <%
  
		     
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
<body class="table" onload="loadDate();Check_Supplement_No();checkSuppTB();" >
  <form name="RJVRejected" id="RJVRejected" method="POST" 
  action="../../../../../TDA_TCA_Verify_List_supplement?Command=verify_list" onsubmit="return checkNull_verify()">
 
  
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>RJV_for_tda_tca_rejected(Supplement)</strong>
          </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
         <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);call_clr();">   
                         
                         <%
                      int unitid=0;
                      String unitname="";
                      try{
                    	  //siva changed on 18-04-2016
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
                        Journal Type <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="jrnltype" id="jrnltype" >
                          <option value="75">Rectification Journal</option>
                        </select>
                     
                       <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth" tabindex="8" onclick="doFunction('searchByDate','null')"/>
                        </div>
                    </td>
             </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Voucher Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <% 
                  String cash_year="";
                  try{              
// String unit=request.getParameter("cmbAcc_UnitCode");
 //int unitid=Integer.parseInt(unit);
 //out.println(unitid);
 
 PreparedStatement ps_dte=con.prepareStatement("SELECT " +
 "  CASE " +
 "    WHEN NVL(b.year1 ,0) = a.year " +
 "    THEN '31/03/' " +
 "		|| NVL(a.year ,extract(YEAR FROM now()))" +
// "      || extract(YEAR FROM now()) " + modified by sathya on 18/02/2016
 "    ELSE '31/03/' " +
 "      || NVL(a.year ,extract(YEAR FROM now())) " +
 "  END AS CASHBOOK_YEAR " +
 " FROM " +
 "  (SELECT rownum AS id, " +
 "    YEAR " +
 "  FROM " +
 "    (SELECT MAX(SUP.CASHBOOK_YEAR) AS YEAR " +
 "    FROM FAS_SUPPLEMENT_GJV SUP " +
 "    WHERE STATUS ='L' " +
 "    ) " +
 "  )a " +
 " LEFT OUTER JOIN " +
 "  (SELECT rownum AS id, " +
 "    year1 " +
 "  FROM " +
 "    (SELECT MAX(CASHBOOK_YEAR) AS year1 " +
 "    FROM FAS_TRIAL_BALANCE_STATUS_SJV " +
 "    WHERE ACCOUNTING_UNIT_ID = " +unitid+
 "    GROUP BY ACCOUNTING_UNIT_ID " +
 "    ) " +
 "  )b " +
 " ON a.id=b.id");



                  ResultSet rs_dte=ps_dte.executeQuery();
                  while(rs_dte.next()){
                	 cash_year=rs_dte.getString("CASHBOOK_YEAR");
                  }
                  }catch (Exception e){
                	  e.printStackTrace();
                  }
                  
                  %>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" value="<%=cash_year %>" tabindex="3" 
                           maxlength="10" size="11"  readonly
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                   
                  
                  </div>
                  <div align="left">
                  <select name="supNo" id="supNo" >
                       <!--  <option value="">Select Supplement No</option> -->
                       </select>
                  </div>
                </td>
              </tr>
              
        </table>
          </div> 
    
     <br>
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            
            <th>Select
                     <a href="javascript:selectAll('ALL');">All</a>
                 <a href="javascript:selectAll('UNSelect');">Unselect</a> 
              </th>
            <th>
              Voucher No
            </th>
            <th>
              Voucher Date
            </th>
            <th>
              Accepting UnitName
            </th>
            <th>
              Type
            </th>
            <th>
            Remarks
            </th>
            <th>
            Total Amount
            </th>
           <th>
            Reason
            </th>
            <th>
            Rejected Date
            </th>
            <th>Show Details ?
            </th>    
          </tr>
          <tbody id="tbody" class="table">          
          </tbody>
        </table>
         <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
           
      <tr class="tdH">
      <td>
          <div align="center">
          <input type="submit" id="submitid" name="submitid" value="Submit" >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>
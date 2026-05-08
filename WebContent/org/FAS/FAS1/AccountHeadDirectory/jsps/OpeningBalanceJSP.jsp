<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.Date"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Opening Balance General Ledger Account</title>
    <script type="text/javascript" src="../../../../Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/OpeningBalanceScript.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript"
            src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
    
     <link href="../../../../../css/Sample3.css" media="screen" rel="stylesheet"/>
     
     <script type="text/javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.OpeningBalForm.txtCB_Year.value=year
        document.OpeningBalForm.txtCB_Month.value=month;
        
         }
     </script>
  </head>
  <body onload="loadDate();loadyear_month()"  bgcolor="rgb(255,255,225)">
  <form name="OpeningBalForm" id="OpeningBalForm" >
  
  <%
  
    Connection con=null;
    ResultSet rs=null,rs2=null,rs1=null;;
    PreparedStatement ps=null,ps1=null,ps2=null;;
    
    
    Connection connection=null;

    ResultSet results=null;
    ResultSet results1=null;
    ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

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
    //int empid=5556;
    int  oid=0,AccUnit_office_id=0;
    String oname="",AccUnit_office_id_name="",getWing="";        // office name,empname
    boolean isSuperUser=false;
    String empNAME="";
   
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
            String findAcc_office="select u.ACCOUNTING_UNIT_OFFICE_ID,off.OFFICE_NAME from FAS_MST_ACCT_UNITS u,FAS_MST_ACCT_UNIT_OFFICES o,COM_MST_OFFICES off"
                + " where u.ACCOUNTING_UNIT_ID=o.ACCOUNTING_UNIT_ID and off.OFFICE_ID=u.ACCOUNTING_UNIT_OFFICE_ID and o.ACCOUNTING_FOR_OFFICE_ID=?  order by o.ACCOUNTING_FOR_OFFICE_ID desc";
			ps=con.prepareStatement(findAcc_office);
			ps.setInt(1,oid);
			results=ps.executeQuery();
			if(results.next())
			{
			AccUnit_office_id=results.getInt("ACCOUNTING_UNIT_OFFICE_ID");
			AccUnit_office_id_name=results.getString("OFFICE_NAME");
			System.out.println(oid+".."+AccUnit_office_id);
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
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                  }
            results.close();
            ps.close();
        
        //*****************  get user name
                ps=con.prepareStatement("select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?");
                ps.setInt(1,empid);
                rs=ps.executeQuery();
              
                if(rs.next())
                    empNAME=rs.getString("EMPLOYEE_NAME");
                ps.close();
                rs.close();
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
  
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              Opening Balance For General Ledger A/c Heads System
            </div></td>
        </tr>
        <tr class="table">
          <td>Accounting Unit Code 
            <font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  *
                </font>
              </font>
            </font>
          </td>
          <td>
             <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="loadAccountOffice()">
             <%
                      int unitid=0;
                      String unitname="";
                      try{
                    	  String sql="SELECT aa.ROLE_ID AS ROLE_ID, " +
                          "  bb.role_name    AS role_name " +
                          "FROM " +
                          "  ( SELECT ROLE_ID FROM sec_mst_login_roles1 WHERE employee_id=? " +
                          "  )aa " +
                          "LEFT OUTER JOIN " +
                          "  ( SELECT role_id,role_name FROM sec_mst_roles " +
                          "  )bb " +
                          "ON aa.role_id=bb.role_id"; 
                      	ps=con.prepareStatement(sql);
                      	ps.setInt(1,empid);
                      	rs=ps.executeQuery();
                          while(rs.next()){                          	
                          	if(rs.getString("ROLE_ID")!=null && rs.getInt("ROLE_ID")==7 && rs.getString("role_name").equalsIgnoreCase("FAS_SUPER_USER")){
                          		isSuperUser=true;
                          		break;
                          	}
                          }
                        if(oid==5000)
                        {
                        	//out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                            //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");                        	
                            if(isSuperUser){
                            	out.println("<option value=select>Select</option>");
                            	getWing="SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO FROM FAS_MST_ACCT_UNITS";
                            	ps=con.prepareStatement(getWing);
                            }else{
                            	getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                            	ps=con.prepareStatement(getWing);
                                ps.setInt(1,oid);
                                ps.setInt(2,empid);
                                ps.setInt(3,oid);
                            }                      
                            rs=ps.executeQuery();
                          
                              while(rs.next())
                              {
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                              unitid=rs.getInt("ACCOUNTING_UNIT_ID");                          
                              }                      
                          ps.close();
                          rs.close();
                          }else{
                            	if(isSuperUser){
                            		out.println("<option value=select>Select</option>");
                            		ps=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO FROM FAS_MST_ACCT_UNITS");
                            	}else{
                            		ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                    ps.setInt(1,oid);
                            	}                                
                                rs=ps.executeQuery();
                                  while(rs.next()){
	                                  //System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
	                                  //System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
	                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
	                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
	                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  }
                                  ps.close();
                                  rs.close();
                              }
                          }catch(Exception e){
                            System.out.println(e);
                        }
                      %> 
             </select>
          </td>
        </tr>
        <tr class="table">
          <td>Accounting Unit for Office Code 
            <font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  *
                </font>
              </font>
            </font>
          </td>
          <td>
             <select size="1" name="comOffCode" id="comOffCode" >
              	<option value="<%=AccUnit_office_id%>"><%=AccUnit_office_id_name%></option>
        	 </select>
          </td>
        </tr>
        <tr class="table">
          <td>Financial Year 
                <font color="#ff2121">
                  *
                </font>
           
          </td>
          <td>
           <!-- <input type="text" name="txtFinanYr" maxlength="9" size="9"
                   id="txtFinanYr"/>-->
            <select name="txtFinanYr" id="txtFinanYr" size="1" onchange="clearAccountHead()">
            <option value="" >--Select--</option>
            <option value="2001-2002">2001-2002</option>
            <option value="2002-2003">2002-2003</option>
            <option value="2003-2004">2003-2004</option>
            <option value="2004-2005">2004-2005</option>
            <option value="2005-2006">2005-2006</option>
            <option value="2006-2007">2006-2007</option>
            <option value="2007-2008">2007-2008</option>
            <option value="2008-2009">2008-2009</option>
            <option value="2009-2010">2009-2010</option>
            </select>
          </td>
        </tr>
        <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <!--<option value="">select the Month</option>-->
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
          <td>Account Head Code 
                 <font color="#ff2121">
                  *
                </font>
          
          </td>
          <td>
           <input type="text" name="cmbAcHeadCode"
                           id="cmbAcHeadCode" maxlength="8"
                           onkeypress="return numbersonly1(event)"
                            onchange="sixdigit();" 
                           onblur="fetchingValues();" size="9"/>
          <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
          </td>
        </tr>
        
        <tr class="table">
          <td>Major Group</td>
          <td>
            <!--<select size="1" name="cmbMajorGroup" id="cmbMajorGroup"/>-->
            <input type="text" name="cmbMajorGroup" maxlength="14" size="14"
                   id="cmbMajorGroup" readonly class="disab"/>
          </td>
        </tr>
        <tr class="table">
          <td>Minor Group</td>
          <td>
            <!--<select size="1" name="cmbMinorGroup" id="cmbMinorGroup"/>-->
            <input type="text" name="cmbMinorGroup" maxlength="14" size="14"
                   id="cmbMinorGroup" readonly class="disab"/>
          </td>
        </tr>
        <tr class="table">
          <td>Sub-Group1</td>
          <td>
            <!--<select size="1" name="cmbSubGroup1" id="cmbSubGroup1"/>-->
            <input type="text" name="cmbSubGroup1" maxlength="14" size="14"
                   id="cmbSubGroup1" readonly class="disab"/>
          </td>
        </tr>
        <tr class="table">
          <td>Sub-Group-2</td>
          <td>
           <!-- <select size="1" name="cmbSubGroup2" id="cmbSubGroup2"/>-->
           <input type="text" name="cmbSubGroup2" maxlength="16" size="16"
                   id="cmbSubGroup2" readonly class="disab"/>
          </td>
        </tr>
        <tr>
            <td >Opening Balance:<font color="#ff2121">*</font>
            </td>
            <td>
                <input type="text" name="txtOpenBal" maxlength="16" size="16"
                   id="txtOpenBal" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
                   <input type=radio name="radOpenBalCrDrInd" id="radOpenBalCrDrInd" checked value="CR">CR
                <input type=radio name="radOpenBalCrDrInd" id="radOpenBalCrDrInd" value="DR">DR
            </td>
        </tr>
        <tr class="table">
          <td>Upto Date Debit Balance 
            <font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  *
                </font>
              </font>
            </font>
          </td>
          <td>
            <input type="text" name="txtDebit" maxlength="16" size="16"
                   id="txtDebit" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
                   </td>
        </tr>
        <tr class="table">
          <td>Upto Date Credit Balance 
            <font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  *
                </font>
              </font>
            </font>
          </td>
          <td>
            <input type="text" name="txtCredit" maxlength="16" size="16"   onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                   id="txtCredit" />
                   
                 
          </td>
        </tr>
        <tr class="table">
          <td>Current Year Debit Balance 
            <font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  *
                </font>
              </font>
            </font>
          </td>
          <td>
            <input type="text" name="txtYrDebit" maxlength="16" size="16"
                   id="txtYrDebit" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
          </td>
        </tr>
        <tr class="table">
          <td>Current Year Credit Balance 
            <font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  *
                </font>
              </font>
            </font>
          </td>
          <td>
            <input type="text" name="txtYrCredit" maxlength="16" size="16"
                   id="txtYrCredit" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
          </td>
        </tr>
        <tr>
            <td>Current Month Debit:<font color="#ff2121">*</font></td>
            <td>
                <input type="text" name="txtCurrMonDebit" maxlength="16" size="16"
                   id="txtCurrMonDebit" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
            </td>
        </tr>
        <tr>
            <td>Current Month Credit:<font color="#ff2121">*</font></td>
            <td>
                <input type="text" name="txtCurrMonCredit" maxlength="16" size="16"
                   id="txtCurrMonCredit" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
            </td>
        </tr>
        
        
        
        
        
        
        
        <!--<tr>
            <td>Opening Balance DR CR Indicator:<font color="#ff2121">*</font></td>
            <td>
                <input type=radio name="radOpenBalCrDrInd" id="radOpenBalCrDrInd" checked>CR
                <input type=radio name="radOpenBalCrDrInd" id="radOpenBalCrDrInd">DR
            </td>
        </tr>-->
        
        
        <tr>
            <td >Closing Balance:<font color="#ff2121">*</font>
            </td>
            <td>
                <input type="text" name="txtCloseBal" maxlength="16" size="16"
                   id="txtCloseBal" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
                   <input type=radio name="radCloseBalCrDrInd" id="radCloseBalCrDrInd" checked value="CR">CR
                <input type=radio name="radCloseBalCrDrInd" id="radCloseBalCrDrInd" value="DR">DR
            </td>
        </tr>
        <!--<tr>
            <td>Closing Balance DR CR Indicator:<font color="#ff2121">*</font></td>
            <td>
                <input type=radio name="radCloseBalCrDrInd" id="radCloseBalCrDrInd" checked>CR
                <input type=radio name="radCloseBalCrDrInd" id="radCloseBalCrDrInd">DR
            </td>
        </tr>-->
        <tr>
            <td>Last Update Date:<font color="#ff2121">*</font></td>
            <td>
                DR<input type="text" name="txtDrLUpdate" maxlength="10" size="10"
                   id="txtDrLUpdate" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onblur="return checkdt1(this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.OpeningBalForm.txtDrLUpdate);"
                         alt="Show Calendar"></img>
                 CR<input type="text" name="txtCrLUpdate" maxlength="10" size="10"
                   id="txtCrLUpdate" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onblur="return checkdt1(this);"/>  
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.OpeningBalForm.txtCrLUpdate);"
                         alt="Show Calendar"></img>
            </td>
        </tr>
        <!--<tr>
            <td>CR Last Update Date:<font color="#ff2121">*</font></td>
            <td>
                <input type="text" name="txtCrLUpdate" maxlength="10" size="10"
                   id="txtCrLUpdate" />
            </td>
        </tr>-->
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
            <table>
            <tr><td>
              <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')"/>
              </td><td>
              <input type="button" name="cmdUpdate" value="UPDATE" style="display:none" id="cmdUpdate" onclick="doFunction('Update','null')"/>
               </td><td>
              <input type="button" name="cmdDelete" value="DELETE" style="display:none" id="cmdDelete" onclick="doFunction('Delete','null')"/>
               </td><td>
              <input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="Exit()"/>
               </td><td>
              <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/>
               </td><td>
               <input type="button" name="cmdClear" value="CLEARALL" id="cmdClear" onclick="clearall()"/>
               </td></tr>
               </table>
            </div></td>
        </tr>
      </table>
    </form></body>
</html>
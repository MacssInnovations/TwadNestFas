<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  %>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link href="../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>
    
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
     
     
     
      
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    
      <script type="text/javascript" src="../js/Fix_Reject_Adjustment_Memo.js"></script>
      
         <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
   
      
      
<title>Fix Adjustment Memo</title>
 <script type="text/javascript" language="javascript">
        function loadDate()
        {
        	 var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
           
            document.Fix_Reject_Adjustment_Memo.cashyear.value=year;
            document.Fix_Reject_Adjustment_Memo.cashmonth.value=month;    
                
        }
	</script>
</head>
<body onload="loadDate()">
<form action="../../../../../Fix_Reject_Adjustment_Memo" name="Fix_Reject_Adjustment_Memo" id="Fix_Reject_Adjustment_Memo" method="post" onsubmit="return checknull()">
  <%
  try{
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
      <% 
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=10099;
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
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
     
      
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
              <tr class="tdH">
                <td colspan="2">
                  <div align="center">
                    <strong>Verify Rejected Adjustment Memo</strong>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td> 
                <td>
                  <div align="left">
                   
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" >
                   
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
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onchange="byUnitAndOfficeChange();">
                      
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
                        while(rs.next())
                        {
                        ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
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
              For the Month & Year
                  </div>
                </td>
                <td>
                  <div align="left">
                <input type="text" name="cashyear"  id="cashyear" size="4" maxlength="4"  />  
                 &nbsp;&nbsp; & &nbsp; &nbsp;                        
                     <select name="cashmonth"  id="cashmonth" onchange="call('loadvoucher')" >
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
                <td>
                  <div align="left">
              For the Accounting Unit Id
                  </div>
                </td>
                <td>
                  <div align="left">
                <select name="forunitid" id="forunitid" onchange="call('loadamountreasondetails')">
                <option value="">Select Accounting Unit</option>
               </select>              
                  </div>
                <input type="hidden" name="adviceno"  id="adviceno"  />     
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
              Voucher No
                  </div>
                </td>
                <td>
                  <div align="left">
               <select name="voucherno" id="voucherno" onchange="call('loaddetails')">
                <option value="">Select Voucher No</option>
               </select>
                              
                  </div>
                </td>
              </tr>
              
               
              <tr class="table">
                <td>
                  <div align="left">
           Amount
                  </div>
                </td>
                <td>
                  <div align="left">
                <input type="text" name="amount"  id="amount" readonly="readonly" />  
                              
                  </div>
                </td>
              </tr> 
              
               <tr class="table">
                <td>
                  <div align="left">
          Reason
                  </div>
                </td>
                <td>
                  <div align="left">
               
               <textarea rows="5" cols="40"  name="reason"  id="reason" readonly="readonly"></textarea>               
                  </div>
                </td>
              </tr> 
             
              <tr class="table">
                <td>
                  <div align="left">
          Option 
                  </div>
                </td>
                <td>
                  <div align="left">
                <input type="radio"  value="E" name="extend_close" onclick="displaydate('E')">Extend 
                 <input type="radio"  value="C" name="extend_close" checked="checked" onclick="displaydate('C')">Close 
                  <table id="mytable" style="display:none"> 
                <tr>
                <td>Accept By</td>
                <td>
                 <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"  readonly="readonly" 
                           onfocus="javascript:vDateType='3';" />
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.Fix_Reject_Adjustment_Memo.txtCrea_date,1);"
                         alt="Show Calendar"></img>  
                </td>
                
                </tr>
                </table>
                 </div>
                 <br></br>
               
                              
                 
                </td>
              </tr>  
              
              </table>
              </div>
             
           <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT" />
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="cls();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>    
              
   <%}catch(Exception e){out.println(e);} %>           
 </form>             
</body>
</html>

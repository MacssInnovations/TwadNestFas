<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  
contentType="text/html;charset=windows-1252"%>
<%@ page 
import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile
"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Revert Cheque Cancellation</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" src="../scripts/Common_JournalType.js"></script>
    <script type="text/javascript" src="../scripts/Revert_Cheque_Dishonour_Status.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    <script type="text/javascript" src="../../../../Security/scripts/tabpane.js">
          </script>
    <script type="text/javascript" language="javascript">
     
     function loadyear_month()
         {
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmRevert_Cheque_Dishonour.txtCB_Year.value=year
        document.frmRevert_Cheque_Dishonour.txtCB_Month.value=month;
        
         }   
        function btncancel()
    {
     self.close();
    }
   
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="loadyear_month()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td >
          <div align="center">
            <font size="4">
              Revert Cheque  Dishonour
            </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmRevert_Cheque_Dishonour" id="frmRevert_Cheque_Dishonour" method="POST" 
                action="../../../../../Revert_Cheque_Dishonour_Status" > 
                  
       
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" 
width="100%">
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Office Details</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Office&nbsp;Name</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_unitName"
                           id="txtAcc_unitName" value="<%=oname%>"
                           maxlength="60" size="60" 
readonly="readonly"
                           class="disab"/>
                  </div>
                </td>
              </tr>
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >
                      
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
              </table>
          </div>
    <br>
       <table cellspacing="1" cellpadding="2" border="1" width="100%">
       <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Search By Month or Date</strong>
                  </div>
                </td>
              </tr>
     
           <tr class="table">
           <td>
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year"  
onclick="loadCheque()" tabindex="3"  maxlength="4" size="5" 
onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4"  >
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
          <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="loadCheque()"/>
           </div>
          </td>
              </tr>
              <tr class="table" >
          <td >
          <div align="left">
              From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong></div></td>
              <td>
              <div align="left">
                                   <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmRevert_Cheque_Dishonour.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmRevert_Cheque_Dishonour.txtTo_date);"
                         alt="Show Calendar"></img>
            <input type="BUTTON" value="GO" name="ByDate" id="ByDate" tabindex="8" onclick="searchByDate()"/>
            </div>
          </td>          
        </tr>
              </table>
            
     <br>
     
           
          <div align="center">
            <table cellspacing="1" cellpadding="3" border="1" width="100%%">
          
            
              <tr>
                <td>
                
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%" >
                      
                      <tr class="tdH">
                         <th>Revert </th>
                        <th>
             SlNo
             </th>
              <th>
            Doc No
            </th>
             <th>
            Doc Date
          </th>
            <th>
             Doc Type
            </th>
                     
          <th>
          Cheque or DD
          </th>
            <th>
            Cheq No
            </th>
            <th>
             Cheq Date
            </th>
            <th>
             Cheq Amt
            </th>
                  <th>
             Receipt Yr
            </th>
            <th>
             Receipt Mn
            </th> 
                      </tr>
                       <tbody id="grid_body" align="left" class="table">
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
              </table>
          </div>
       
      
      
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="SUBMIT" name="butSub" id="butSub" value="SUBMIT" />
                 &nbsp;&nbsp;&nbsp; 
               
                  <!--input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/-->
               
               <input type="button" name="butCan" id="butCan" 
value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" 
value="EXIT"
                       onclick="self.close()"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
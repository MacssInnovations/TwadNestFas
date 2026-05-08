<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <link href="../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../Reports/ReceiptSystem/scripts/CalendarControl.js"></script>
   <script type="text/javascript" src="../scripts/BudgetMasterScript.js"></script>
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    <title>Budget Details</title>
  </head>
  <%
	String s = request.getContextPath();
%>
  <body class="table" onload="getStatementName()">
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%><!--
  loadfyr(),
  
  --><form action="../../../../../BudgetMasterServlet.con?Command=Add" name="frmBudget" id="frmBudget" >
  
  <table width="100%" >
        <tr>
            <td class="tdH"><center><b>Budget Details</b></center></td>
        </tr>
          <tr>
            <td>
                <table border="1" cellspacing="0" cellpadding="0" width="100%">
  
                    <tr class="table">
                            <td>
                              <div align="left">
                                Accounting Unit Code 
                                <font color="#ff2121">*</font>
                              </div>
                            </td>
                            <td colspan="4">
                              <div align="left">
                                <!--<input type="text" name="txtAcc_UnitCode"
                                       id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                                 <option value="0">--Select Account Unit--</option>
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
                            <td colspan="4">
                              <div align="left">
                                <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                                <option value="0">--Select Accounting Office Code--</option>
                                  
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
                          <tr>
                            <td>Financial Year:<font color="#ff2121">*</font></td>
                            <td>
                                <select name="cmbFinancialYear" id="cmbFinancialYear">
                                <option value="0">--Select Financial Year--</option>
                                <option value="2011-12">2011-12</option>
                                <option value="2012-13">2012-13</option>
                                </select>
                            </td>
                          
                          </tr> 
                          <tr>
      <td>Statement Name<font color="#ff2121">*</font></td>
	  <td><label>
        <select name="cmbStatementName" id="cmbStatementName" onChange="chooseGroup(),checkFreeze()">
          <option value="0">---Select---</option>
        </select>
	  </label></td>
	  </tr>
                          <tr class="table1">
      <td>Statement Group<font color="#ff2121">*</font></td>
	  <td>
        <select name="statementGp" id="statementGp" onchange="callStatement(),callHead();">
          <option value="0">---Select---</option>
        </select>
        
	  </td>
	  </tr>
	  <tr class="table1">
      <td>Type of Allocation<font color="#ff2121">*</font></td>
	  <td>
        <input type="radio" name="groupId" id="groupId" value="H" onclick="blockHead(),checkFreeze();">HeadWise
        <input type="radio" name="groupId" id="groupId" value="G" onclick="blockHead(),checkFreeze(),getAmount();">GroupWise
        
	  </td>
	  </tr>
	  
	  <tr class="table1">
      <td>
      <div id="head_div1" name="head_div1" >
      Account Head Code:<font color="#ff2121">*</font>
      </div></td>
	  <td width="80%">
	  <div id="head_div2" name="head_div2" >
	   <!--<input type=text name=txtaccountheadcode size=8 maxlength=8 onchange="getAmount(),doFunction('checkCode','null')" onkeypress="return numbersonly1(event,this)">
                                <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
	  <input type=text name="txtaccountheadname" size="45" disabled >
	  -->
	  <select name="txtaccountheadcode" id="txtaccountheadcode" onchange="reallocation_fn(),getAmount();">
          <option value="">---Choose A/c---</option>
        </select>
       <input type="text" size="5" readonly="readonly" name="groupType" id="groupType"></input>
       <input type="text" size="25" readonly="readonly" name="groupDesc" id="groupDesc"></input>
	  
      <!--<select name="head_code" id="head_code" onchange="reallocation_fn('<%=s %>');">
          <option value="">---Choose A/c---</option>
        </select>
       <input type="text" size="5" readonly="readonly" name="groupType" id="groupType"></input>
       <input type="text" size="25" readonly="readonly" name="groupDesc" id="groupDesc"></input>
       --></div>
	 </td>
	  </tr>
	  
	  
	  <!--
                          <tr>
                            <td>Account Head Code:<font color="#ff2121">*</font></td>
                            <td>
                                <input type=text name=txtaccountheadcode size=8 maxlength=8 onchange="doFunction('checkCode','null')" onkeypress="return numbersonly1(event,this)">
                                <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                            </td>
                          </tr>
                          <tr>
                            <td>Account Head Name:</td>
                            <td><input type=text name="txtaccountheadname" size="45" disabled >
                          </tr>
                          --><tr>
                            <td>Previous Year Expenditure:</td>
                            <td>
                                <input type=text name="txtpreviousyear" size=16 maxlength="17" onkeypress="return limit_amt(this,event);" onchange="valid_amt(this);">
                            </td>
                          </tr>
                          <tr>
                            <td>Current Year Budget Estimate:</td>
                            <td>
                                <input type=text name="txtcurrentyearbudget" size=16 maxlength="17" onkeypress="return limit_amt(this,event);" onchange="valid_amt(this);">
                            </td>
                          </tr>
                          <tr>
                            <td>Current Year Revised Estimate:</td>
                            <td>
                                <input type=text name="txtcurrentyearrevised" size=16 maxlength="17" onkeypress="return limit_amt(this,event);" onchange="valid_amt(this);">
                            </td>
                          </tr>
                           <tr>
                                <td>Current Year Budget Alloted:</td>
                                <td>
                                    <input type=text name="txtbudget_alloted" id="txtbudget_alloted" size=20 maxlength="17" onkeypress="return limit_amt(this,event);" onchange="valid_amt(this);">
                                    <font color="#ff2121">in Lakhs</font>
                                </td>
                            </tr>
                            <tr>
                                <td>Budget so far spent</td>
                                <td>
                                    <input type=text name="txtbudget_spent" id ="txtbudget_spent" size=20 maxlength="17" onkeypress="return limit_amt(this,event);" onchange="valid_amt(this);">
                                </td>
                            </tr>
                            <tr>
                                <td>Next Year Budget Estimate:</td>
                                <td>
                                    <input type=text name="txtnextyearestimate" size=20 maxlength="17" onkeypress="return limit_amt(this,event);" onchange="valid_amt(this);">
                                </td>
                            </tr>
                            <tr>
                                <td>Reference No. & Date:</td>
                                <td><input type="text" name="txtreferno" size="30" maxlength="50" onkeypress="return numbersonly1(event,this)">&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="text" name="txtreferdate" size="10" maxlength="10" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'">
                                <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmBudget.txtreferdate);" alt="Show Calendar"
                                 height="24" width="19"></img>
                                </td>
                            </tr>
                            <tr>
                                <td>Remarks:</td>
                                 <td> 
                                    <textarea cols="25" rows="5" name="txtRemarks" id="txtRemarks"></textarea>
                                </td>
                            </tr>
                            
                            <tr>
                                <td colspan="2" align="center">
                                <table>
                                <tr>
                                <td>
                                <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')"/>
                                </td>
                                <td>
                                <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')"/>
                                </td>
                                <td>
                                <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" />
                                </td>
                                <td>
                                <input type="reset" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
                                </td>
                                <td>
                                <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAllBudget()"/>
                                </td>
                                <td>
                                <input type="button" id="Exit" name="Exit" value="EXIT" onclick="closeWindow()">
                                </td>
                                </tr>
                                </table>
                                
                                
                                 </td>
                            </tr>
                                                   
                                <!--<tr>
                                    <td class="tdH" colspan="2"><b>Existing Details</b></td>
                                </tr>-->
                          </table>
                          <!--<table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%">
                          <tr class="tdH">
                          <th>
                            Select
                          </th>
                          <th>
                            Accounting Unit Name
                          </th>
                          <th>
                            Accounting Office Code
                          </th>
                          <th>
                            Financial Year
                          </th>
                          <th>
                          Account Head Description
                          </th>
                          <th>
                            Previous Year Expenditure
                          </th>
                          <th>
                            Current Year Budget Estimate
                          </th>
                          <th>
                            Current Year Revised Estimate
                          </th>
                          <th>
                           Next Year Estimate
                          </th>
                          <th>
                            Reference No & Date:
                          </th>
                          <th>
                            Remarks
                          </th>
                          </tr>
                          </table>-->
                    </td>
              </tr>
        </table>
  </form>
  </body>
</html>
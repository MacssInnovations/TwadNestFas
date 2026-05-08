<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.Date"%>

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <meta http-equiv="cache-control" content="no-store,no-cache,ust-revalidate"></meta>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" />
    <title>Cheque Book Master</title>
    <script type="text/javascript" src="../scripts/ChequeBookMstScript.js"></script>
     <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script> 
     <link href="../../../../../css/Sample3.css" media="screen" rel="stylesheet"/>
     <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
     <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
       
 </head>
  <body  bgcolor="rgb(255,255,225)"><form name="chequeForm" id="chequeForm" onload="showicons(this.value);" >
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  
  <%
  
    Connection con=null;
    ResultSet rs=null,results=null;
    PreparedStatement ps=null,ps2=null;
    Connection connection=null;
    ResultSet results1=null;
    ResultSet rs1=null,rs2=null;
    
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  long l=System.currentTimeMillis();
	Timestamp ts=new Timestamp(l);                      
	 Date ctdate = new java.sql.Date(ts.getTime()); 

  %>
   <% 
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=5556;
    int  EmployeeId=0,oid=0,AccUnit_office_id=0;             // Office id
    String oname="",AccUnit_office_id_name="",Empname="";        // office name,empname
    
    try
    {
           
           // ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
          //  ps.setInt(1,empid);
           ps=con.prepareStatement(" SELECT "+
            		"  CASE "+
            		 "   When Old_Office_Id   Is Not Null "+
            		  "  AND DATE_ALLOWED_UPTO>=? "+
            		    " THEN OLD_OFFICE_ID "+
            		    " ELSE Office_Id "+
            		  " END AS OFFICE_ID "+
            		" FROM "+
            		  " (SELECT Office_Id, "+
            		    " OLD_OFFICE_ID, "+
            		    " DATE_ALLOWED_UPTO "+
            		  " From Hrm_Emp_Current_Posting "+
            		  " Where Employee_Id=? )as s" );
            ps.setDate(1, ctdate);
            ps.setInt(2,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 }
            results.close();
            ps.close();
            System.out.println(oid+".."+AccUnit_office_id);
            String findAcc_office="select u.ACCOUNTING_UNIT_OFFICE_ID,off.OFFICE_NAME from FAS_MST_ACCT_UNITS u,FAS_MST_ACCT_UNIT_OFFICES o,COM_MST_OFFICES off"
                                    + " where u.ACCOUNTING_UNIT_ID=o.ACCOUNTING_UNIT_ID and off.OFFICE_ID=u.ACCOUNTING_UNIT_OFFICE_ID and o.ACCOUNTING_FOR_OFFICE_ID=? order by o.ACCOUNTING_FOR_OFFICE_ID desc";
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
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    
   %>
     <%   try{
     ps=con.prepareStatement("select EMPLOYEE_ID,EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    EmployeeId=results.getInt("EMPLOYEE_ID");
                    Empname=results.getString("EMPLOYEE_NAME");
                 }
            results.close();
            ps.close();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   %>
  
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong><font size="4">
                  Cheque Books Sub-Ledger Master
                </font></strong>
            </div></td>
        </tr>
        <tr class="table">
          <td width="55%">Accounting Unit Code    <font color="#ff2121">*</font></td>
            <td width="45%">
           <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
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
            </td>
        </tr>
       
 <tr class="table">
          <td width="55%">Accounting For Office Code    <font color="#ff2121">*</font></td>
          <td width="45%">
            <select size="1" name="comOffCode" id="comOffCode" >
            <option value="<%=AccUnit_office_id%>"><%=AccUnit_office_id_name%></option>
            </select>
          </td>
        </tr> 
        <tr class="table">
          <td width="55%">Bank Account Number    <font color="#ff2121">*</font></td>
          <td width="45%">
         <input type="text" name="txtBankAc" id="txtBankAc" maxlength="15" class="disab" readonly
                   size="15"/>
            <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountNumberList"
                             onclick="AccNopopup();"></img>
          </td>
     
         
        </tr>  
        
        
        
        <tr class="table">
          <td width="55%">Name Of the Bank  </td>
          <td width="45%">
            <input type="text" name="txtBankName" size="40" id="txtBankName" class="disab" readonly >
            <input type="hidden" name="BankName_ID" size="10" id="BankName_ID" readonly/>
            <input type="hidden" name="Bank_ID" size="10" id="Bank_ID" readonly/>
            <input type="hidden" name="Br_ID" size="10" id="Br_ID" readonly/>
          </td>
           
        </tr>
        <tr class="table">
          <td width="55%">Address Of the Bank  </td>
          <td width="45%">
            <textarea name="txtBankAddr" cols="30" rows="2" id="txtBankAddr" class="disab" readonly></textarea>
            <input type="text" name="Branch_ID" id="Branch_ID" size="10" readonly/>
          </td>
        </tr>
        <tr class="table">
          <td width="55%">MICR Code  </td>
          <td width="45%">
            <input type="text" name="txtMICRCode" id="txtMICRCode"
                   maxlength="10" size="10" class="disab" readonly >
          </td>
        </tr>
      
        <tr class="table">
          <td width="55%">Cheque Book Code   <font color="#ff2121"> *</font></td>
          <td width="45%">
            <input type="text" name="txtChequeCode" maxlength="10" size="25" id="txtChequeCode" onblur="checkCode();"/>
          </td>
        </tr>
        <tr class="table">
          <td width="55%">Number Of Leaves 
            <font color="#ff2121">
                  *
            </font>
          </td>
          <td width="45%">
            <input type="text" name="txtNoLeaves" maxlength="4" size="5" 
                   id="txtNoLeaves" onkeypress="return numbersonly1(event,this)" onblur="return CheckNoofLeaves()" />
          </td>
        </tr>
        <tr class="table">
          <td width="55%">Number Of Leaves Phisically Verified? </td>
          <td width="45%">
            <input type="radio" name="radCheck_NoOfLeaf" id="radCheck_NoOfLeaf" value="Y" onclick="showicons(this.value);"
                  checked="checked" />
            Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" name="radCheck_NoOfLeaf" id="radCheck_NoOfLeaf" value="N" onclick="showicons(this.value);"  />
            No
          </td>
        </tr>
        <tr class="table">
          <td width="55%">Physically Verified by 
          </td>
          <td width="45%">
           <table align="left">
           <tr align="left">
     
             <td>
              
                <div>
                    <input type="text" name="txtverifyBy" id="txtverifyBy" value="<%=EmployeeId%>" maxlength="5" size="6" onchange="doFunction('loademp','null');" 
                     onkeypress="return numbersonly1(event,this)" />
                     <input type="text" id="txtverifyByName" style="background-color: #ececec"  readonly="readonly"  size="30" value="<%=Empname%>" />
                </div>
              </td>
             <td>
                    <div id="showemplist"  > 
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="empList" onclick="servicepopup();"></img>
                    </div>
                           
             </td>
            </tr>
            </table>
        </tr>
        <tr class="table">
          <td width="55%">Physical Verification done on 
           
          </td>
          <td width="45%">
           <table align="left">
           <tr align="left">
           <td>
                      <div >
                        <input type="text" name="txtPhyVerDate" id="txtPhyVerDate" 
                               maxlength="10" size="10" onFocus="javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                    </div>
          </td>
          <td>
              <div id="showdate" >
              <img src="../../../../../images/calendr3.gif" 
              onclick="showCalendarControl(document.chequeForm.txtPhyVerDate);" alt="Show Calendar" ></img>
                </div>
          </td>
      
          </tr>
            </table>           
          </td>
        </tr>
        <tr class="table">
          <td width="55%">Starting Leaves Number &nbsp;
            <font color="#ff2121">
                  *
            </font>
          </td>
          <td width="45%">
           <!--  <input type="text" name="txtStartLNO" id="txtStartLNO" onchange="fillendleaf()" maxlength="10" size="10" onkeypress="return numbersonly1(event,this)"/>  -->
           <input type="text" name="txtStartLNO" id="txtStartLNO" onchange="TestLeafNos()" maxlength="10" size="10" onkeypress="return numbersonly1(event,this)"/>
          </td>
        </tr>
        <tr class="table">
          <td width="55%">Ending Leaves Number 
            <font color="#ff2121">
                  *
           </font>
          </td>
          <td width="45%">
            <input type="text" name="txtEndLNO" id="txtEndLNO" maxlength="10"   size="10" onkeypress="return numbersonly1(event,this)" onblur="checkLeaf()" readonly="readonly"/>
            <!-- <input type="text" name="txtEndLNO" id="txtEndLNO" maxlength="10"   size="10" onkeypress="return numbersonly1(event,this)" onblur="checkLeaf()"/>  -->
          </td>
        </tr>
        <tr class="table">
          <td width="55%">Date Of Destruction of Office copy of the Cheque Book   </td>
          <td width="45%">
            <input type="text" name="txtDateDest" maxlength="10" size="10"
                   id="txtDateDest" onFocus="javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                   <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.chequeForm.txtDateDest);" alt="Show Calendar" ></img>
                    
          </td>
        </tr>
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
            <table>
            <tr>
            <td>
              <input type="button" name="cmdAdd" value="ADDNEW" id="cmdAdd" onclick="doFunction('Add','null')"/>
              </td><td>
              <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" onclick="doFunction('Update','null')" style="display:none"/>
              </td><td>
              <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" onclick="doFunction('Delete','null')" style="display:none"/>
              </td><td>
              <input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="Exit()"/>
              </td><td>
              <input type="button" name="cmdClear" value="CLEARALL" id="cmdClear" onclick="clearall()"/>
              </td><td>
              <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/>
              </td><td>
              </tr>
              </table>
            </div></td>
        </tr>
      </table>
    </form></body>
</html>
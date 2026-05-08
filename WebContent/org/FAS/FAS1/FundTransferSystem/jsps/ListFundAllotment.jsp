<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  
contentType="text/html;charset=windows-1252"%>
<%@ page 
import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile
"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Journal System</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" src="../scripts/Common_JournalType.js"></script>
    <script type="text/javascript" src="../scripts/ListFundAllotment.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    <!--script type="text/javascript" src="../scripts/CalendarControlFund.js"></script-->  
    <script type="text/javascript" src="../../../../Security/scripts/tabpane.js">
          </script>
    <script type="text/javascript" language="javascript">
     
     function loadyear_month()
         {
         //alert("verify");
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmFund_Allotment_Details.txtCB_Year.value=year
        document.frmFund_Allotment_Details.txtCB_Month.value=month;
        
         }   
        function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                var  cmbAcc_UnitCode="",cmbOffice_code="",Cashbook_year="",Cashbook_month="",slno="",letter_gen="";
                var transAmt=0,fundreq=0;
            var LetterDate="",OffLetterDate="",remarks="",tranOffice="", tranOfficeName="",fundtype="",fundtypeid="",LetterNo="",OffLetterNo="",reason="";
            
            
                 r=document.getElementById(rowID);
                 rcells=r.cells;
      
                 slno=rcells.item(1).firstChild.nodeValue;
                 tranOffice=rcells.item(2).firstChild.nodeValue;
                 tranOfficeName=rcells.item(3).firstChild.nodeValue;
                 OffLetterNo=rcells.item(4).firstChild.nodeValue;
                 OffLetterDate=rcells.item(5).firstChild.nodeValue
                 LetterNo=rcells.item(6).firstChild.nodeValue;
                 LetterDate=rcells.item(7).firstChild.nodeValue;
                 //LetterDate=rcells.item(6).firstChild.nodeValue;
                 fundtypeid=rcells.item(8).firstChild.nodeValue;
                 fundtype=rcells.item(9).firstChild.nodeValue;
                 
                 
                 fundreq=rcells.item(10).firstChild.nodeValue;
                 transAmt=rcells.item(11).firstChild.nodeValue;
                 reason=rcells.item(12).firstChild.nodeValue;
                 CheqorDD=rcells.item(13).firstChild.nodeValue;
                 CheqNo=rcells.item(14).firstChild.nodeValue;
                 CheqDate=rcells.item(15).firstChild.nodeValue;
                 remarks=rcells.item(16).firstChild.nodeValue;
                 letter_gen=rcells.item(17).firstChild.nodeValue;
        Minimize();
    
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentFundAllotment(slno,transAmt,fundreq,LetterDate,OffLetterDate,tranOffice,tranOfficeName,fundtype,fundtypeid,LetterNo,OffLetterNo,reason,CheqorDD,CheqNo,CheqDate,remarks,letter_gen)
       // return true;
   }
   
</script>
  </head>
  <body onload="loadyear_month()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td >
          <div align="center">
            <font size="4">Fund&nbsp;Allotment&nbsp;System </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmFund_Allotment_Details" id="frmFund_Allotment_Details" >
       <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>           
      <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null,rs3=null;
  PreparedStatement ps=null,ps2=null,ps1=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
   try
  {
  
             ResourceBundle 
rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String 
strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");

          //  ConnectionString = strdsn.trim() + "@" + 
//strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             
con=DriverManager.getConnection(ConnectionString,strdbusername.trim(
),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  %>
      <% 
        HttpSession session=request.getSession(false);
         UserProfile 
empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=9315;
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
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
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
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" 
size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" >
                     <!-- <option value="0"> Select Account Unit 
</option>-->
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
              <tr class="table">
            
                <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" 
onchange="loadVoucher()" tabindex="3"  maxlength="4" size="5" 
onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" 
onchange="loadVoucher()">
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
              
                <td>
                <div align="left">
                   Voucher No
                </div>   
                </td>
                
                <td>
                   <div align="left">
                  <select size="1" name="txtVoucherNo" id="txtVoucherNo" onchange="loadDivision()"  tabindex="4" >
                      <option value="">--Select Voucher Number--</option>
                  </select>  
                  </div>
                </td>
              </tr>
              
              
        
              
              
           <tr class="table">
                <td>
                  <div align="left">
                    HO Reference No
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtLetNo" tabindex="5"  style="background-color: #ececec"
                                id="txtLetNo"  size="15"/>
                  </div>
                 </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    HO Reference Date<font color="#ff2121">*</font>
                  </div>
                  
                </td>
                <td>
              
                     <div align="left">
                    <input type="text" name="txtLetterDate" tabindex="5"  onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"
                    id="txtLetterDate"  size="15"/><img src="../../../../../images/calendr3.gif"
                    onclick="showCalendarControl(document.frmFund_Allotment_Details.txtLetterDate);"
                    alt="Show Calendar"/>
                     
                  </div>           
                 </td>
              </tr>
               <tr class="table">
                <td width="30%">
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtRemarks" id="txtRemarks" size="50" tabindex="8"></input>  
                            
                  </div>
                </td>
              </tr>
              </table>
              
          </div>
        </div>
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%%">
          
            
              <tr>
                <td>
                
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="1" cellpadding="2"
                           border="1" width="692" height="97">
                      
                      <tr class="tdH">
                        
                        <th >
                          Sl No
                        </th>
                        <th>
                          Transferred Office
                        </th>
                        <th >
                          Office Letter No 
                        </th>
                        <th >
                          Office Letter Date 
                        </th>
                        <th >
                          HO Letter No 
                        </th>
                        <th >
                          HO Letter Date 
                        </th>
                        <th>
                          Work Type
                        </th>
                        <th>
                          Amount Requested
                        </th>
                        <th >
                          Amount Alloted
                        </th>
                       <th >
                         Reason
                        </th>
                        
                      </tr>
                       <tbody id="grid_body" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
              </table>
          </div>
        </div>
      </div>
      <br>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
                        <th>
                            Select
                        </th>
                         <th>
                            Sl.No
                        </th>
                        <th>
                          Transferred Office
                        </th>
                        <th >
                          Office Letter No 
                        </th>
                        <th >
                          Office Letter Date 
                        </th>
                        <th >
                          HO Letter No 
                        </th>
                        <th >
                          HO Letter Date 
                        </th>
                        <th>
                          Work Type
                        </th>
                        <th>
                          Amount Requested
                        </th>
                        <th >
                          Amount Alloted
                        </th>
                       <th >
                         Reason for withholding the requested amount
                        </th>
                    
       </tr>
       <tbody id="tb" class="table" align="left">
         
          
          </tbody>
       </table>
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="SUBMIT" name="butSub" id="butSub" value="SUBMIT" onclick="Addfund()"/>
                 &nbsp;&nbsp;&nbsp; 
               
                  <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAll()"/>
               
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
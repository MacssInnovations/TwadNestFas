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
    <meta http-equiv="cache-control" content="no-cache">
    <title>Journal System</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" src="../scripts/Common_JournalType.js"></script>
    <script type="text/javascript" src="../scripts/FundAllotmentReportJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
    <!--script type="text/javascript" src="../scripts/CalendarControlFund.js"></script-->  
    <!--script type="text/javascript" src="../../../../Security/scripts/tabpane.js">
          </script--->
    <script type="text/javascript" language="javascript">
     /*    function foc()
         {
         }
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
        
document.frmJournal_General_Edit.txtCrea_date.value=day+"/"+month+"/
"+year;
         doFunction('load_Voucher_No','null');
       // document.frmJournal_General_Edit.txtCash_year.value=year;
        
//document.frmJournal_General_Edit.txtCash_Month_hid.value=month;
        
//document.frmJournal_General_Edit.txtCash_Month.value=monthArray[to
day.getMonth()];
         }*/
     function loadyear_month()
         {
         //alert("verify");
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmJournal_General_Edit.txtCB_Year.value=year
        document.frmJournal_General_Edit.txtCB_Month.value=month;
        
         }   
        
    /* function load_date_onfocus()
     {
        //alert("from here");
        
if(document.frmJournal_General_Edit.txtCrea_date.value.length!=0)
        {
           var 
DOC=document.frmJournal_General_Edit.txtCrea_date.value
       
           var dd=DOC.split('/');
           var d1=dd[0];
           var m1=dd[1];
           var y1=dd[2];
           var monthArray =new Array("January", "February", "March", 
               "April", "May", "June", "July", "August",
               "September", "October", "November", "December");
            if(dd[1].substring(0,1)==0)
            mon_arr=dd[1].substring(1,2);
            else
            mon_arr=dd[1];
            
//document.frmJournal_General_Edit.txtCash_year.value=dd[2];
           // 
document.frmJournal_General_Edit.txtCash_Month_hid.value=dd[1];
            
//document.frmJournal_General_Edit.txtCash_Month.value=monthArray[mo
n_arr-1];
        }
        else 
if(document.frmJournal_General_Edit.txtCrea_date.value.length==0)
            document.frmJournal_General_Edit.txtCrea_date.focus();
          
     }*/
</script>
  </head>
  <body onload="loadyear_month()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Fund&nbsp;Allotment&nbsp;System </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmJournal_General_Edit" id="frmJournal_General_Edit"  method="POST" 
                action="../../../../../FundAllotmentServ">
        <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>          
      
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
           
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
                <!--td>
                  <div align="left">
                    Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" 
id="txtCrea_date" tabindex="3"
                           maxlength="10" size="11" 
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         
onclick="showCalendarControl(document.frmJournal_General_Edit.txtCre
a_date,1);"
                         alt="Show Calendar"></img>
                    
                           
                    
                  </div>
                </td-->
                <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" 
onchange="loadDivision()" tabindex="3"  maxlength="4" size="5" 
onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" 
onchange="loadDivision()">
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
              <!--<tr class="table" style="display:none">
                <td>
                  <div align="left">
                    Cash Book Month & Year
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                  
                    <input type="hidden" name="txtCash_Month_hid"
                           id="txtCash_Month_hid" size="2" 
maxlength="2"/>
                    <input type="text" name="txtCash_Month" 
id="txtCash_Month"
                           style="background-color: #ececec"  
readonly="readonly"  maxlength="10" size="11"/>
                    <input type="text" name="txtCash_year" 
id="txtCash_year"
                           style="background-color: #ececec"  
readonly="readonly"   maxlength="4" size="5"/>accunit
                  </div>
                </td>
              </tr>-->
              
              <tr class="table">
                <td>
                  <div align="left">
                    Division Name
                  </div>
                </td>
                <td>
                  <div align="left">
                  <select size="1" name="txtDivname" id="txtDivname"  tabindex="4" >
                      <option value="">--Select Division Name--</option>
                    
                        <!---%
                        java.sql.Date to_day= new java.sql.Date(System.currentTimeMillis()); 
                        
System.out.println("today-----------"+to_day);
                         int day=to_day.getDate();
                         int month=to_day.getMonth();
                         month=month+1;
                         int year=to_day.getYear();
                         System.out.println("Month:"+month);
                         
                         if(year < 1900) year += 1900;
                         System.out.println("Year"+year);
                                       
                       try
                        {
                            ps1=con.prepareStatement("select TRANSFER_TO_OFFICE_ID from FAS_FUND_TRF_FROM_HO_TRN where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?"); 
                            ps1.setInt(1,year);                      
      
                            ps1.setInt(2,month);
                            rs3=ps1.executeQuery();
                            System.out.println("here is ok");
                            while(rs3.next())
                            {
                           //<option value='ACCOUNTING_UNIT_ID'>rs3.getInt("TRANSFER_TO_OFFICE_ID")+"</option>
                            out.println("<option value="+rs3.getInt("TRANSFER_TO_OFFICE_ID")+">"+rs3.getInt("TRANSFER_TO_OFFICE_ID")+"</option>");  
                            }
                        }
                        catch(Exception e)
                        {
                        System.out.println("err");
                        }
                     %!---->
                    </select>
                  </div>
                </td>
              </tr>
              <tr class=table>
              <td align="left">
                 Work Type
              </td>
              <td align="left">
                 <select id="worktype" name="worktype" onchange="loadLetNoNew()" >
                 <option value="">--Select Work Type--</option>
                 <option value="C">Civil</option>
                 <option value="W">Works</option>
                 </select>
              </td>
              </tr>
           <tr class="table">
                <td>
                  <div align="left">
                    Letter No
                  </div>
                </td>
                <td>
                  <div align="left">
                    <!--input type="text" name="txtLetNo" tabindex="5"  style="background-color: #ececec"  readonly="readonly"  
                                id="txtLetNo"  size="50"/-->
                    <select id="cmbLetNo" name="cmbLetNo">
                     <%--
                     ps=con.prepareStatement("select ref_no from fas_fund_allotment_transaction where accounting_unit_id=5 and"+
                        " office_id=? and cashbook_year=2007 and cashbook_month=6");
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                          out.println("<option value='' ></option>");
                                                                
                      }
                    --%>
                     
                    </select>
                    
                  </div>
                 </td>
              </tr>
              <tr class=table>
                 <td colspan=2>
                    <center>
                    <input type="Submit" value="Submit"></input>
                    <input type="button" value="Exit" onclick="self.close()"></input>
                    </center>
                 </td>
              </tr>
              </table>
          </div>
        </div>
      </div>
    </form></body>
</html>
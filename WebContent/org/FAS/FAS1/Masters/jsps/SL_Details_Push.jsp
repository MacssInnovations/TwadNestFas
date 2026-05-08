<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Bank Receipt System</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
   
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/SL_Details.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>     
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>     
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>    
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
   
   
    <!-- to avoid future date the above script used-->
       <script type="text/javascript" language="javascript">
         function foc()
         {
         }
         function loadDate()
         {
        	// call_bankUpdate();
        //	setTimeout('call_a52()',900);
        	
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
                document.Bank_Receipt_Form.txtCrea_date.value=day+"/"+month+"/"+year; 
                 var ctdate=day+"-"+monthArray1[today.getMonth()]+"-"+year;  
             
     }
</script>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<%
	String s = request.getContextPath();
%>
  <body onload="foc();" bgcolor="rgb(255,255,225)">
  
    
    <form name="Bank_Receipt_Form" id="Bank_Receipt_Form" method="POST" >
    
     
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
              
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="center">
                    <strong>SL Details Transfer from closed office to another office</strong>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    From Office
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbOffice_code1" id="cmbOffice_code1" tabindex="1">
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
                        ps=con.prepareStatement("select distinct ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES  order by ACCOUNTING_FOR_OFFICE_ID");
                       
                        rs=ps.executeQuery();
                       
                        int countoffice=0;   
                        while(rs.next())
                        {
                        	//int old_offid=0;

                        	
                        	                             ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID in ('CL','NC','RD')");
                        	                        	
                            //ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
                            ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                            rs2=ps2.executeQuery();
                            if(rs2.next())
                            {
                              out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"("+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+")"+"</option>");                           
                            }
                            
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
                   To Office 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code2" id="cmbOffice_code2" tabindex="2"  >
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
                        ps=con.prepareStatement("select distinct ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES  order by ACCOUNTING_FOR_OFFICE_ID");
                       
                        rs=ps.executeQuery();
                       
                        int countoffice=0;   
                        while(rs.next())
                        {
                        	int old_offid=0;

                        	// ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=?");
          					ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=? and old_office_id is not null and Date_Allowed_Upto>=?");
                        	 
                        	                             ps2.setInt(1,empid);
                        	                             ps2.setDate(2, ctdate);
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
                            {
                              out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"("+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+")"+"</option>");                           
                            }
                            
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
                <td width="40%">
                  <div align="left">Sub-Ledger Type</div>
                </td>
                <td width="60%">
                  <div align="left">
                    <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type">
                      <option value="">--Select Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE in (1,2,11) order by SUB_LEDGER_TYPE_DESC");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getString("SUB_LEDGER_TYPE_CODE")+">"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in Reason combo..."+e);
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
        </div>
      </div>
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center" style="disply:block" id="newDiv" name="newDiv">
                <input type="button" name="butSub" id="butSub" value="SUBMIT"  onClick="doo('<%=s%>');"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CLEAR"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
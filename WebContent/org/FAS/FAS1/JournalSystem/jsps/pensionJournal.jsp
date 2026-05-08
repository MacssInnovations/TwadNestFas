<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Pension Journal System</title>
    
     
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/JournalSystem/scripts/pensionJournal.js"></script>
      <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
       <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
   
   <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

    <script type="text/javascript"
           src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
     
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmpensionJournal.txtCB_Year.value=year;
        document.frmpensionJournal.txtCB_Month.value=month;
         }
    </script>
  </head>
  <body onload="loadyear_month(),listtype(),setTimeout('listgroup()',100),LoadAccountingUnitID_Create('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Pension Journal System</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmpensionJournal" id="frmpensionJournal" method="POST"  action="../../../../../PensionJournal?command=Add" onsubmit="return checkNull()">
      
    <input type="hidden" id="hid_chklist" name="hid_chklist" value="">
    <input type="hidden" id="hid_Grplist" name="hid_Grplist" value="">
  
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
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
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice_New(this.value),setTimeout('listgroup()',100);">
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
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
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
                       // ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
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
                    Cashbook Month & Year
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
		          <select name="txtCB_Month" id="txtCB_Month" tabindex="4" >          
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
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Journal Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3"
                           maxlength="10" size="11" readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_mainJSP_script(this,1),check_withinCB(); dateCheck(this);"/>
                      
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmpensionJournal.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                    
                           
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
              		<td>
              		<div align="left">
              		Check List Type<font color="#ff2121">*</font></div> </td>
              		<td><select name="listtype" id="listtype" onblur="listgroup()">
                    <option value="">--select--</option>
                     </select>
              		</td>          		
              </tr>
              <tr class="table">
              <td><div align="left">
              		Check List Group<font color="#ff2121" >*</font></div> </td>
              <td>
              
              <select  name="grouptype" id="grouptype" onchange="att();">
                    <option value="">--select--</option>
              </select>
              </td>
              </tr>
              <tr class="table">
              <td><div align="left">
              		Pensioner/Family<font color="#ff2121">*</font></div>
              </td>
              <td>
               <select  name="penfamily" id="penfamily">
                    <option value="">--select--</option>
                    <option value="P">P</option>
                    <option value="F">F</option>
              </select>
              </td></tr>
            </table>
            <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
              <input type="button" name="go" id="go" value="GO" onclick="loadpension();"/>
              <input type="button" name="butCan" id="butCan" value="EXIT" onclick="self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
      <div>
      <table id="Existing"  cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
          <th>S.No</th> 
          <th align="center">Description</th>
          <th>Account Head</th>
          <th align="left">SL Type</th>
          <th align="left">SL Code</th>
          <th align="right">CR Amount</th> 
          <th align="right">DR Amount</th>
          
       <!--     <th>select/deselect</th> -->   
          
          </tr>
           <tbody id="tblList" align="center" class="table">
	   </tbody>
	
	    <!--    <tbody id="totalvalue" align="center" class="table"> </tbody>   -->
	   <tr class="tdH">
	    <td colspan="3">
                  <div align="center" >
                    Total                   
                  </div>
                </td>
	   <td align="right">
	    <div>
                    <input type="text" name="crtotal" id="crtotal" tabindex="3"
                            size="11" readonly="readonly" align="right"/></div></td>
            <td align="right">
	    <div>
                    <input type="text" name="drtotal" id="drtotal" tabindex="3"
                           size="11" readonly="readonly"  align="right"/></div></td>                
                           
                           </tr>
                           
        </table>
      </div>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
          
            <td>
              <div align="center" id="submitdiv">   
                    <input type="submit" name="butGo" id="butGo" value="Submit" />  
                    <input type="button" name="butCan" id="butCan" value="EXIT" onclick="javascript:self.close();"/>                   
              </div>
            </td>
          </tr>
        </table>
      </div>
         
      
     
    </form></body>
</html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Adjustment Memo System Edit</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    
    <script type="text/javascript" src="../js/Adjustment_Memo_singleunit_multiplereceipt_report.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   
     <!-- to avoid future date the above script used-->
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
             document.Adjustment_Memo_Form.txtCrea_date.value=day+"/"+month+"/"+year;
           //  document.Adjustment_Memo_Form.txtCB_Year.value=year;
             //document.Adjustment_Memo_Form.txtCB_Month.value=month;
             
        }
        function clr_fun()
        {
        document.getElementById("office_id").value="";
        document.getElementById("cmbvocharNo").value="";
        document.getElementById("txtTotalAmt").value="";
        if(document.Adjustment_Memo_Form.advice_type[0].checked==true)
		   {
        	document.forms[0].butSub1.disabled=false;
        	document.forms[0].butSub2.disabled=true;
           }
           else
           {
           document.forms[0].butSub1.disabled=true;
           document.forms[0].butSub2.disabled=false;
           }
        
        }
        function ad_type()
        {
        if(document.Adjustment_Memo_Form.advice_type[0].checked==true)
		   {
        	document.forms[0].butSub1.disabled=false;
        	document.forms[0].butSub2.disabled=true;
           }
           else
           {
           document.forms[0].butSub1.disabled=true;
           document.forms[0].butSub2.disabled=false;
           }
        }
        
        
	</script>
  </head>
 <body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS'),ad_type()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Generated advice for Adjustment memo</font>
          </div>
        </td>
      </tr>
    </table>
    <%
  
		      Connection con=null;
		      ResultSet rs=null,rs2=null;
		      PreparedStatement ps=null,ps2=null,ps3=null;
		      ResultSet results=null,rs3=null;
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
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
   
         %>
  <form name="Adjustment_Memo_Form" id="Adjustment_Memo_Form" method="POST">
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" > General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="table">
                       <td>
                          <div align="left">
                            Accounting Unit Id 
                            <font color="#ff2121">*</font>
                          </div>
                       </td>
                       <td>
                        <div align="left">
                          <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
            
                        <%
                          int unitid=0;
                          String unitname="";
                          try
                          {
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
                                  
                                  System.out.println("..ACCOUNTING_UNIT_ID"+rs.getInt("ACCOUNTING_UNIT_ID"));
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
                                      out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                      unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                      }
                                      ps.close();
                                      rs.close();
                                  }
                              }
                              catch(Exception e)
                                {
                                System.out.println("here");
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
                    Accounting For Office Id
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
                    Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);" />
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.Adjustment_Memo_Form.txtCrea_date);"
                         alt="Show Calendar"></img>
                  
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                    Advice Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="advice_type" id="advice_type" 
                           checked="checked" value="CR" onclick="clr_fun();"/>Credit
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="advice_type" id="advice_type" 
                           value="DR" onclick="clr_fun();"/>Debit &nbsp;&nbsp;&nbsp;&nbsp; 
                    
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">On Behalf of which office <font color="#ff2121">*</font></div>
                </td>
                <td>
                  <div align="left">
                    <select name="office_id" id="office_id" tabindex="4"  onchange="doFunction_voucher('load_Receipt_No',null);">
                    	<option value="">--Select--</option>
                    	<%
		                    			ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS order by ACCOUNTING_UNIT_NAME");
		                    			rs=ps.executeQuery();
		                    			while(rs.next())
		                    			{
		                    					out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
		                    			}
		                %>
                    </select>
                  </div>                  
                </td>
              </tr>
               <tr class="table">
                       <td>
                          <div align="left">
                     VOUCHER_NO
                            <font color="#ff2121">*</font>
                          </div>
                       </td>
                       <td>
                        <div align="left">
                          <select size="1" name="cmbvocharNo" id="cmbvocharNo" onchange="doFunction_voucher('load_Receipt_Details','null');">
                          <option value="0">---Select Option----</option>
            </select>
            </div>
             </td>
             </tr>
            
           <!--    
              
               <tr class="table">
                <td>
                  <div align="left"> Letter No.</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="letterNo" id="letterNo" tabindex="5"  ></input>
                  </div>
                </td>
              </tr>   
              
               <tr class="table">
                <td>
                  <div align="left"> Letter Date</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="letterDate" id="letterDate"  
                           maxlength="10" size="11" tabindex="6"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.Adjustment_Memo_Form.letterDate);"
                         alt="Show Calendar"></img>
                  
                  </div>
                </td>
              </tr>   
              
              <tr class="table">
                <td>
                  <div align="left"> Authority Name </div>
                </td>
                <td>
                   <div align="left">
                     <textarea rows="3"  tabindex="7" cols="35" id="authority" name="authority" ></textarea>
                  </div>
                </td>
              </tr>   
               <tr class="table">
                <td>
                  <div align="left"> Authority Address </div>
                </td>
                <td>
                   <div align="left">
                     <textarea rows="3"  tabindex="7" cols="35" id="authorityaddress" name="authorityaddress" ></textarea>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left"> Particulars</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="3"  tabindex="9" cols="35" id="particulars" name="particulars" ></textarea>
                  </div>
                </td>
              </tr>    -->
              
              <tr class="table">
                <td>
                  <div align="left">
                     Total Amount  <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtTotalAmt" id="txtTotalAmt" readonly="readonly"  maxlength="16" onkeypress="return filter_real(event,this,10,2)" tabindex="9" ></input>
                  </div>
                </td>
              </tr>   
               <!--   <tr class="table">
                <td>
                  <div align="left"> Office Address</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="3"  tabindex="9" cols="35" id="txtOfficeAddress" name="txtOfficeAddress" ></textarea>
                  </div>
                </td>
              </tr>   -->
                            
            </table>
          </div>
        </div>        
        <div class="tab-page" id="gd" >
         <h2 class="tab" > </h2>
           
        
                  
                
             
          </div>
        </div>
 
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                
                <input type="button" name="butSub1" id="butSub1" value="CREDIT ADVICE REPORT" onclick="report();"/>
                <input type="button" name="butSub2" id="butSub2" value="DEBIT ADVICE REPORT" onclick="report1();"/>
               
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
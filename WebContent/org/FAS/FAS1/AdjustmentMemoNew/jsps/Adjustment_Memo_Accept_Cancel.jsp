<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Adjustment Memo System</title>
    
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
    
    <script type="text/javascript" src="../js/Adjustment_Memo_Accept_Cancel.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   
    
    <script type="text/javascript" language="javascript">
        function loadDate()
        {
            //alert("enter");
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
            // document.Adjustment_Memo_Form1.txtDate.value=day+"/"+month+"/"+year;
         }
	</script>
  </head>
  <body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS');">
  
       <%
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null,ps2=null;            
             Connection connection=null;        
             ResultSet results=null,rs2=null;
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
                       System.out.println("Exception in opening connection :"+e);
             }   UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                int empid=empProfile.getEmployeeId();
                int  oid=0;             // Office id
                String oname="";        // office name
           
            try
            {
                   
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oid=results.getInt("OFFICE_ID");
                            System.out.println("Office id is:"+oid);
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
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Adjustment Memo from Board(Cancel)</font>
          </div>
        </td>
      </tr>
    </table>
     
  <form name="Adjustment_Memo_Form1" id="Adjustment_Memo_Form1" method="POST" action="../../../../../Adjustment_Memo_Accept_Cancel?command=Add">
                  
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" > General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
              <tr class="table">
                <td>
                  <div align="left" >
                  	  Accounting Unit Code  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
               
                              
                     </select>
                  </div>
                </td>
              </tr>

              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                        
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
                    <input type="text" name="txtDate" id="txtDate" tabindex="4" 
                           maxlength="10" size="11" readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"
                           />   <!-- onblur="loadMemoNO1();" -->
                               <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.Adjustment_Memo_Form1.txtDate,1);"
                         alt="Show Calendar"></img>        
                  </div>
                </td>
              </tr>
            
              <tr class="table">
                <td>
                  <div align="left">Memo Advice No. <font color="#ff2121">*</font></div>
                </td>
                <td>
                  <div align="left">
                      <select id="cmbAdviceNO" name="cmbAdviceNO" onchange="loadMemoDetails();">
                         <option value="s">--Select No--</option>
                      </select>
                  </div>                  
                </td>
              </tr>
                  
              <tr class="table">
                <td>
                  <div align="left"> Authority Name</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="3"  tabindex="5" cols="35" id="txtAuthority" name="txtAuthority" ></textarea>
                  </div>
                </td>
              </tr>   
               <tr class="table">
                <td>
                  <div align="left"> Authority Address</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="3"  tabindex="5" cols="35" id="txtAuthorityaddress" name="txtAuthorityaddress" ></textarea>
                  </div>
                </td>
              </tr>   
              
              
               <tr class="table">
                <td>
                  <div align="left"> Letter No.</div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="txtLetterNO" id="txtLetterNO" tabindex="5"></input>
                  </div>
                </td>
              </tr>   
              
               <tr class="table">
                <td>
                  <div align="left"> Letter Date</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtLetterDate" id="txtLetterDate" 
                           maxlength="10" size="11" tabindex="6"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>                              
                  </div>
                </td>
              </tr>   
             
              <tr class="table">
                <td>
                  <div align="left"> Particulars</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="4"  tabindex="8" cols="50" id="txtRemarks1" name="txtRemarks1"></textarea>
                  </div>
                </td>
              </tr>   
              
              <tr class="table">
                <td>
                  <div align="left">
                     Total Amount  <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount" id="txtAmount"  maxlength="16" onkeypress="return filter_real(event,this,10,2)" tabindex="9"></input>
                  </div>
                </td>
              </tr>    
                           
            </table>
          </div>
        </div>        
        
        <div class="tab-page" id="gd" >
         <h2 class="tab" > Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
              <tr>
                <td colspan="2">
                  
                  
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                      <tr class="table">
                       
                        <th >A/c Head Code</th>
                        <th >CR/DR</th>
                        <th>Sub Ledger Type</th>
                        <th >Sub Ledger Code</th>   
                        <th >Amount</th>
                        <th >Particulars</th>
                      </tr>
                       <tbody id="grid_body" class="table" align="left" >
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
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT" onclick="return checkNull()"/>
                 &nbsp;&nbsp;&nbsp; 
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
    </form>
    </body>
</html>
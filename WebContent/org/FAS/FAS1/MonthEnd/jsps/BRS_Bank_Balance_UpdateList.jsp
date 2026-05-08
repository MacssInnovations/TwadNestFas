<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Bank Balance Non-Update List</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript"
             src="../../../../Library/scripts/checkDate.js"></script>
       <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script language="javascript" type="text/javascript" src="../scripts/BRS_Bank_Balance_Update.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"
            src="../../../../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
            <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
           
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
    <script type="text/javascript" language="javascript">
     
      </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
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
                    //     document.TDA_TCA.txtCrea_date.value=day+"/"+month+"/"+year;  
                         document.frmBRS_Bank_Balance_Update.txtCB_Year.value=year;                
                }
                function loadDate1()
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
                    //     document.TDA_TCA.txtCrea_date.value=day+"/"+month+"/"+year;  
                         document.frmBRS_Bank_Balance_Update.txtCB_Year1.value=year;                
                }
                
                function disableyear()
                {
					//alert("dasfasdf");
                	document.getElementById("monthdis").style.display='block';
                	document.getElementById("yeardis").style.display='none';
				var tbody=document.getElementById("tblList");
				    
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }
                }

                function enableyear()
                {
                	//alert("fffffff");
                	document.getElementById("monthdis").style.display='none';
                	document.getElementById("yeardis").style.display='block';


                	var tbody=document.getElementById("tblList");
				    
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }
                }
                function get_radio_value() {
                    var inputs = document.getElementsByName("txtoption");
                    for (var i = 0; i < inputs.length; i++) {
                      if (inputs[i].checked) {
                        return inputs[i].value;
                      }
                    }
                  }
                
    </script>
    
    
    
    
  </head>
  <body onload="loadDate();disableyear();LoadAccountingUnitID('LIST_ALL_UNITS');setTimeout('loadBankDetails()',500);">
   <%
   String s=request.getContextPath();
   %>
  <form name="frmBRS_Bank_Balance_Update" method="POST" action="../../../../../BRS_Bank_Balance_UpdateList" >
   <%
  Connection con=null;
  ResultSet rs=null,rs2=null;
  PreparedStatement ps=null,ps2=null;
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

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  cmbAcc_UnitCode=0,cmbOffice_code=0;

            
  %>
  
 
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="3">
          <div align="center">
              <strong>BRS Bank Balance Not Update List </strong>
            </div>
        </td>
      </tr>
    </table>
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
             <tr class="table">
                    <td class="table">
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);loadBankDetails();">        
                         </select>
                      </div>
                    </td>
              </tr>
             <tr class="table">
              <td class="table">
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code">
                          
                        </select>
                      </div>
                    </td>
              </tr>
              
              
              
              
              <tr class="table">
              <td colspan="2" align="center"> <input type="radio" name="txtoption" id="txtoption" value="Monthwise"
                 checked="checked" onclick="disableyear();"></input>
          Month wise
          <input type="radio" name="txtoption" id="txtoption" value="Yearwise" onclick="enableyear();loadDate1();"></input>
          Year wise</td>
              
              </tr>
              <tr >
              
              <td class="table" colspan="2">
              
              <div id="monthdis" >
              <table>
              <tr class="table">
          <td class="table">
          <div>
              Cash Book Year &amp; Month  <font color="#ff2121">*</font>
              </div>
            </td>
            <td >
             
                     <div >
                        <input type="text" name="txtCB_Year" id="txtCB_Year" 
                         tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)" ></input>
                         
                        <select name="txtCB_Month" id="txtCB_Month" onchange="passSheetChangeNew();"   tabindex="4"  >
		                      <option value="s">Select</option>
		                      <option value="01">January</option>
			                  <option value="02">February</option>
			                  <option value="03">March</option>
			                  <option value="04">April</option>
			                  <option value="05">May</option>
			                  <option value="06">June</option>
			                  <option value="07">July</option>
			                  <option value="08">August</option>
			                  <option value="09">September</option>
			                  <option value="10">October</option>
			                  <option value="11">November</option>
			                  <option value="12">December</option>
                        </select>
                     </div>
                  
          </td>
        </tr>
       
        <tr class="table">
          <td class="table" >
          <div align="left">
              Pass Sheet Year &amp; Month
              </div>
            </td>
            <td >
             <div align="left">
          <input type="text" name="txtPS_Year" id="txtPS_Year" tabindex="3" maxlength="4" size="5" onkeypress="return numbersonly(event)" onChange="checkMonthYear();">
       <select name="txtPS_Month" id="txtPS_Month" 
                                tabindex="4" onChange="checkMonth();" >
                           <option value="s">Select</option>
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
              
              </table>
              
              
            </div>
            
            
            <div id="yeardis">
            <table>
              <tr class="table">
          <td class="table">
          <div align="left">
              Cash Book Year   <font color="#ff2121">*</font>
              </div>
            </td>
            <td colspan="2" >
             
                     <div align="left">
                        <input type="text" name="txtCB_Year1" id="txtCB_Year1" 
                         tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)"></input>
                         
                        
                     </div>
                  
          </td>
        </tr>
       
        
              
              </table>
            </div>
              
              </td>
              
              </tr>
             
          
    
        
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="0"  >
  
                <tr>
                <td>
                  <!-- 	<input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')"/>
                  </td><td>
                  	<input type="button" name="cmdUpdate" disabled value="UPDATE" id="cmdUpdate" onclick="doFunction('Update','null')"/>
                  </td><td>
                  	<input type="button" name="cmdDelete" disabled value="DELETE" id="cmdDelete" onclick="doFunction('Delete','null')"/>
                  </td> -->
                 
                  <!--<td>
                  	<input type="button" name="cmdClear" value="CLEARALL" id="cmdClear" onclick="clearall()"/>
               	 </td>
               	  <td>
                  	<input type="button" name="cmdSubmit" value="SUBMIT" id="cmdSubmit" onclick="submitt()"/>
               	 </td>-->
                <td>
                  	<input type="button" name="cmdList" value="LIST" id="cmdList" onclick="callListcp();"/>
                  </td>
                   <td>
                  	<input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="Exit()"/>                  
                  </td>
               	 </tr>
      
      </table>

       <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
          <td class="table"><b> Details</b></td>
        </tr>
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
             
        <tr class="tdH">
        <th>
           Sl.No
          </th>
          <th>
            Bank & Branch Name
          </th>
          <th>
            Type of Account
          </th>
           <th>
            Mode Of Operation
          </th>
          <th>
            Account Number
          </th>
            <th>
          CashBook Year
          </th>
    <th>
        CashBook Month          </th>

          <th>
           Initial Deposit Amount
          </th>
          
          <th>
           PS Date
          </th>
          
           <th>
         Opening Date
          </th>
          <th>
         Account No Updated (Y/N)
          </th>
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
        
       
      </form>
  
  </body>
</html>
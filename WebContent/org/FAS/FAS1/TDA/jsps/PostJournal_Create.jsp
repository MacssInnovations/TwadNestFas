<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Post Journal System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script>		   		 				    		
    <script type="text/javascript" src="../scripts/PostJournal_Create.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
 	<script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
     <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">

	  /*  function loadyear_month()
	    {
		         var currentTime = new Date();
		         var finday=1;
		         var finmonth=4;
		         var month = currentTime.getMonth() + 1;
		         var day = currentTime.getDate();
		         var year = currentTime.getFullYear();		
		         fin_year_from="",fin_year_to="";
		         var itemcombo=document.getElementById("financial_year");
                          document.Post_Journal.txtCB_Year.value=year;
                          
		         if(month<4)
		            	year=year-1;
		         i=0;
		         while(i<2)
		         {
			            fin_year_from=year;fin_year_to=year+1;
			           
			            var option=document.createElement("option");
			            var text=document.createTextNode(fin_year_from+"-"+fin_year_to);
			            option.setAttribute("value",fin_year_from+"-"+fin_year_to);
			            if(i==0)
				            option.setAttribute("selected","true");
			            option.appendChild(text);
			            itemcombo.appendChild(option);
			            year=year-1;i++;
		        }
		         document.getElementById("txtFrom_date").value=finday+"/"+finmonth+"/"+fin_year_to;
	    } */

       function loadDate()
        {
	       
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 document.Post_Journal.txtCB_Month.value=month; 
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;                
             //    document.Post_Journal.txtCrea_date.value=day+"/"+month+"/"+year;
               
                 document.getElementById("txtFrom_date").value=day+"/"+month+"/"+year;
                 document.Post_Journal.txtTo_date.value=day+"/"+month+"/"+year;  
                 document.Post_Journal.txtCB_Year.value= year;
        }
        function loadfirstdayofmonth()
        {
                  
                  var month_chosen=document.getElementById("txtCB_Month").value;
                  var year_current=document.getElementById("txtCB_Year").value;
                 
                  if(month_chosen<=9 && month_chosen>=1)
                        document.getElementById("txtFrom_date").value="01/0"+month_chosen+"/"+year_current;
                  else if(month_chosen>=10 && month_chosen<=12)
                        document.getElementById("txtFrom_date").value="01/"+month_chosen+"/"+year_current;
        }
    </script>
  </head>
   <%
  
	    Connection con=null;
	    ResultSet rs=null,rs2=null;
	    Statement st=null;
	    PreparedStatement ps=null;
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
	        	 System.out.println("Exception in connection...."+e);
	    }
		     
		      
  %>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();call_clr();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Post Journal</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="Post_Journal" id="Post_Journal" method="post" action="../../../../../PostJournal_Create?Command=Add" onsubmit="return checkNull();">
    <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
              
               <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);">        
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
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >                          
                        </select>
                      </div>
                    </td>
              </tr>
              
              <tr class="table">
                    <td>
                      <div align="left">
                        Voucher Date
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtCrea_date" id="txtCrea_date" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);dateCheck(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.Post_Journal.txtCrea_date,1);"
                             alt="Show Calendar"></img>                        
                      </div> 
                    </td>
              </tr>
              
              
                           
              <tr class="table">
                <td width="30%">
                  <div align="left">
                    Journal Type
                    <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
              <select name="cmbJournal_type" id="cmbJournal_type" onchange="pendingJour();"> 
                  <!--    <select name="cmbJournal_type" id="cmbJournal_type" >  -->
                      <option value="">Select Journal Type</option>
                      <%
			                     try
			                     {
			                            ps=con.prepareStatement("select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where CATEGORY='G' and JOURNAL_TYPE_CODE in(62,63,65,66)  order by JOURNAL_TYPE_CODE");
			                            rs=ps.executeQuery();
			                            while(rs.next())
			                            {
			                            		out.println("<option value="+rs.getInt("JOURNAL_TYPE_CODE")+">"+rs.getString("JOURNAL_TYPE_DESC")+"</option>");
			                            }
			                        
			                     } 
			                     catch(Exception e)
			                     {
			                    	System.out.println("Exception in Journal combo..."+e);
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
              <br>
               <table cellspacing="1" cellpadding="1" border="0" width="100%">
               
                 <tr align="left" class="tdH"> <th>Search By Month or Date</th></tr>
                 
                 <tr align="left">
                  <td class="table">
                  <div align="left">
                      Cash Book Year &amp; Month&nbsp;&nbsp;<strong>:</strong>
	          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
	          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="loadfirstdayofmonth();">
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
          
           <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="loadVoucher('searchByMonth')"/>
           </div>
          </td>
        </tr>
                 
              <tr align="left">
		          <td class="table">
			          <div align="left">
			            From Date &amp; To Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong>   
		                    <input type="text" name="txtFrom_date" id="txtFrom_date"  
		                           maxlength="10" size="11"
		                           onfocus="javascript:vDateType='3';"
		                           onkeypress="return calins(event,this);"
		                           onblur="return checkdt(this);"/>
		                    <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.Post_Journal.txtFrom_date,0);"
		                         alt="Show Calendar"></img>
		           
		                    <input type="text" name="txtTo_date" id="txtTo_date"  
		                           maxlength="10" size="11"
		                           onfocus="javascript:vDateType='3';"
		                           onkeypress="return calins(event,this);"
		                           onblur="return checkdt(this);"/>
		                     
		                     <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.Post_Journal.txtTo_date,0);"
		                         alt="Show Calendar"></img>		  
		                    
		                     <input type="button" name="butSub" id="butSub" value="Go" onclick="loadVoucher('searchByDate')"/>  
		                      <input type="button" name="btnClear" id="btnClear" value="Clear" onclick="call_clr();"/>        	
		             </div>
		          </td>          
		      </tr>		    
			 </table>
              <br>
  </div>
  <div id="grid" style="display:block">
            <table id="mytable" cellspacing="3" cellpadding="2"
                   border="0" width="100%">
              <tr class="table">
                <th>Select</th>
                <th>Slno</th>
                <th>Originated Date</th>
                <th>Transfer Unit</th>
                <th>Reason for transfer</th>
                <th>Sub Ledger Type</th>
                <th>Sub Ledger Code</th>
                <th>Amount</th>           
                <th>Particulars</th>
                <th>Show Details ?</th>                         
              </tr>
              <tbody id="grid_body" class="table" align="left" >
              </tbody>
            </table>
          </div>
   <div id="grid_two" style="display:none">
            <table id="mytable2" cellspacing="3" cellpadding="2"
                   border="0" width="100%">
              <tr class="table">
                <th>Select</th>
                <th>Accepted V.No</th>
                <th>Accepted V.Date</th>
                <th>Accepting Unit</th>
                <th>Originated V.No</th>
                <th>Originated V.Date</th>
                <th>Originating Unit</th>
                <th>Reason for transfer</th>
                <th>Amount</th>           
               <th>Show Details ?</th>                         
              </tr>
              <tbody id="grid_body2" class="table" align="left" >
              </tbody>
            </table>
          </div>
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">   
                <input type="submit" name="butSub_go" id="butSub_go" value="SUBMIT" disabled/>                     
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
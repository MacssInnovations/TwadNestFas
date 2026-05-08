<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>HR Note Preparation</title>
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
       
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
         <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
         <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
         <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case2.js"></script>
                  
         <script type="text/javascript" src="../scripts/HR_Note_Report1.js"></script>
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
	                 document.HR_Note.hr_date.value=day+"/"+month+"/"+year;                   
	        }  
	          function loadyear_month()
	            {
	                 var currentTime = new Date();
	                 var month = currentTime.getMonth() + 1;  
	                 var day = currentTime.getDate();
	                 var year = currentTime.getFullYear();
	                 
	                 var year_to = currentTime.getYear()-100;
	                // alert(year_to);
	                 fin_year_from="",fin_year_to="";
	                 var itemcombo=document.getElementById("fin_yr");
	                 if(month<4)
	                        year=year-1;
	                 year_to = year_to-1;
	                 i=0;
	                 while(i<2)
	                 {
		            
	                          //  fin_year_from=year;fin_year_to=year_to+1;
	                            fin_year_from=year;fin_year_to=year_to+2;
	                           // alert(fin_year_from+"-"+fin_year_to);
	                            var option=document.createElement("option");
	                            var text=document.createTextNode(fin_year_from+"-"+fin_year_to);
	                            option.setAttribute("value",fin_year_from+"-"+fin_year_to);
	                            //alert("from year ::::"+fin_year_from+"fin_year_to::::::::"+fin_year_to);
	                            if(i==0)
	                                    option.setAttribute("selected","true");
	                            option.appendChild(text);
	                            itemcombo.appendChild(option);
	                            year=year-1;
	                            year_to=year_to-1;
	                            i++;
	                }
	            }    
       </script>
   
  </head>
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();loadDate();">
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
                   //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
        
  <form name="HR_Note" id="HR_Note" method="POST" action="../../../../../HR_Note" onsubmit="return nullcheck()">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
              <td>
                <div align="center">
                  TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANCIAL ACCOUNTING SYSTEM
                </div>
              </td>
        </tr>
        
        <tr class="table">
          <td>
            <div align="center">
              <b>HR Note Preparation Report</b>
            </div>
          </td>
        </tr>
        </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
          
          
          
          
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
         <!--  <tr>
	            <td class="table">cashbookmonth &year<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
	            <td class="table">
			                 <select name="fin_yr" id="fin_yr" onchange="loadHoNote(this.value);"></select>
	            </td>
          </tr> -->
          
          
   <tr>
					<td class="table">
			                    CashbookYear & Month
			                           <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
			                   	
			             
					<td>
			                    
							  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
					          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4">
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
					          <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="doFunction1('searchByMonth','null')"/>
			                    	
			                </td>
				</tr>
				
         
       
                </table>
                    
            
        

       
      
       <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th><font size="2">
           NOTE NO </font></th>
          
              <th><font size="2">
           NOTE DATE </font></th>
           
                       
            <th><font size="2">
           BILL_MAJOR_TYPE </font></th>
            <th><font size="2">
           BILL_MINOR_TYPE 
           </font> </th>
            <th> <font size="2">
          BILL_SUB_TYPE
            </font></th>
            <th><font size="2">
       AMOUNT 
            </font></th>
            <th><font size="2">
        NOTE_PREPARED_BY 
           </font> </th>
            <th><font size="2">
        ACCOUNT_HEAD_CODE 
           </font> </th>
            
          </tr>
          <tbody id="tbody" class="table">          
          </tbody>
          
        </table>
        <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            
      <tr class="tdH">
      <td>
          <div align="center">
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel1()">
      </div>
      </td>
      </tr>
      
      </table>
    </form>
</html>

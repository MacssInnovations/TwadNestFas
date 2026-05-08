<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    

    <script type="text/javascript" src="../../../../../../org/FAS/FAS1/CommonControls/scripts/Common_Bank_Name_Loading.js"></script> 
   <script language="javascript" type="text/javascript" src="../scripts/GeneralLedgerReport_dft.js"></script>
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
   
   <script type="text/javascript" src="../scripts/BillstatusReport.js"></script>
        
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                  function loadyear_month()
                 {
               
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
               
                document.frmReport.txtCB_Year.value=year
                document.frmReport.txtCB_Month.value=month;
                
                 }
                  function blockHead()
                  {
                     
                  	if(document.frmReport.DisMode[0].checked==true)
                  	{
                  		
                  		document.getElementById("head_div1").style.display="none";
                  		document.getElementById("head_div2").style.display="none";
                  	}
                  	else
                  	{
                  		
                  		document.getElementById("head_div1").style.display="block";
                  		document.getElementById("head_div2").style.display="block";
                  		
                  	}
                  }
    </script>
     <script type="text/javascript" language="javascript">
   
    </script>
    <title>BIll Status Report</title>
  </head>
  <%String s=request.getContextPath(); %>
<%System.out.println(s); %>
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();">
  <%  
  Connection con=null;
  ResultSet rs=null,rs2=null,rs3=null;
  PreparedStatement ps=null,ps2=null,ps3=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  ResultSet results3=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  %>
   
   
   
  
  
  <form action="../../../../../../BillStatusReport1" name=frmReport id="frmReport" method="get" onsubmit="return nullcheck();"> 
   <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Bill Status report </font>
          </div>
        </td>
      </tr>
    </table>
   
    <table width="100%" >
        
        
		    <!--    <tr>
          <td>
            <center>
              <b>Bill Status Report</b>
            </center>
          </td>
        </tr> -->
		  
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
         
          
          
   <!-- <tr>
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
				
          
                    <tr>
                        <td>
                            From Date:
                        </td>
                        <td>
                            <input type=text name=txtfromdate id=txtfromdate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmReport.txtfromdate);" alt="Show Calendar"
                                 height="24" width="19"></img>
                                 
                            &nbsp;&nbsp;&nbsp;
                                 
                            To Date:
                            <input type=text name=txttodate id=txttodate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmReport.txttodate);" alt="Show Calendar" ></img>
                         </td>
                    </tr>
     -->                                   
                    
                       
                    <tr align="left">
           <td class="table">
              <div align="left">
                 Cash Book Year &amp; Month <font color="#ff2121">*</font></td>
              </div>
           </td>
          <td>
         
          <input type="radio" name="month_year" id="month_year" value="particular_cb" onclick="cb_month_year(this.value)" >one Month 
          <input type="radio" name="month_year" id="month_year" value="more_cb" onclick="cb_month_year(this.value)"> More than one Month 
          
          <br><br>       
          
          <div id="particular" name="particular" style="display:none">
            
          Year 
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          Month 
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
          
          <div id="more" name="more" style="display:none">
          
          From   
          <input type=text name=txtfromdate id=txtfromdate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmReport.txtfromdate);" alt="Show Calendar"
                                 height="24" width="19"></img>
         
          
          To  
          <input type=text name=txttodate id=txttodate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmReport.txttodate);" alt="Show Calendar" ></img>
          
          </div>      
           
          </td>
        </tr>                     
                    
                  <tr class="table">
                    <td>
                      <div align="left" >
                              BIll Major Type  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select name="Major_Grp" id="Major_Grp"
                          onchange="loadingMinor('loadMinor','<%=s %>')">
                  <option value="select">--select--</option>
                         <%
                        try
                            {
                                ps=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES");
                                rs=ps.executeQuery();
                                while(rs.next())
                                {
                                    out.println("<option value="+rs.getString("BILL_MAJOR_TYPE_CODE")+">"+rs.getString("BILL_MAJOR_TYPE_DESC")+"</option>");
                                }
                            } 
                        catch(Exception e)
                        {
                            System.out.println("Exception in Major combo..."+e);
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
                      <div align="left" >
                              Bill Minor Type  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name=Minor_Grp id="Minor_Grp" onchange="loadingSub('loadSub')"> 
                           <option value="select">--select--</option>      
                         </select>
                      </div>
                    </td>
              </tr>
                <tr class="table">
                    <td>
                      <div align="left" >
                              BIll Sub Type  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="sub_type" id="sub_type" onchange=""> 
                          <option value="select">--select--</option>       
                         </select>
                      </div>
                    </td>
              </tr>
                    
                    
                   <tr>
                        <td colspan=4 class="tdH" align="center">
                        <input type=submit value=Submit >
                        <input type=reset value=Clear>
                        <input type=button value=Exit onclick="closeWindow()">
                        </td>
                    </tr>
                     
                    
                    
                
                </table>
            
  
  
  </form>
  </body>
</html>
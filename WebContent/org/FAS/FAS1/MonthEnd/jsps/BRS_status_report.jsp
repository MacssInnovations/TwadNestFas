<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>BRS TB Freeze Status</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
       
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
  
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
       <!-- <script language="javascript" type="text/javascript" src="../scripts/Auto_Fund_Receipt_Create_atHOjs.js"></script>
    -->   <script type="text/javascript"  src="../scripts/BRS_status.js"></script>
  
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
         function loadDate()
         {
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         document.BRS_statusform.BRStxtCB_Month.value=month;
        // document.BRS_statusform.TBtxtCB_Month.value=month;
         if(day<=9 && day>=1)
         day="0"+day;
         if(month<=9 && month>=1)
         month="0"+month;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         var monthArray =new Array("January", "February", "March", 
                   "April", "May", "June", "July", "August",
                   "September", "October", "November", "December");
        
        document.BRS_statusform.BRStxtCB_Year.value=year;
     
         }
         function cb_month_year(id)
         {
             
            var Regions=document.getElementById("Regions");
            var Banks=document.getElementById("Banks");
               
               if(id=="RW")
               {
                  Regions.style.display="block";
                  Banks.style.display="none";
               }
               if(id=="BW")
               {
                  
                 Banks.style.display="block";
                  Regions.style.display="none";
                 
               }
               load_bank();
       
         }

        
   
</script>
  </head>
  <body onload="loadDate();load_REgion();">
 
 
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">BRS Status</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="BRS_statusform" id="BRS_statusform" method="POST" action="../../../../../BRS_status_report?command=PDF">
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
  %>
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
             
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
                  </div>
                </td>
              </tr>
               
              
              
               <tr class="table">
                <td>
                  <div align="left">BRS completed for the Cash book Year and Month (Manual)<font color="#ff2121">*</font></div>
                </td>
                <td>
                   <div align="left">
                        <input type="text" name="BRStxtCB_Year" id="BRStxtCB_Year"
                               tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)"></input>
                         
                        <select name="BRStxtCB_Month" id="BRStxtCB_Month"
                                tabindex="4">
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
               <td>
                 <div align="left">
              		Displying Order <font color="#ff2121"> * </font>
                 </div>
               </td>
               <td>
                <input type="radio" name="displayingOrder" id="displayingOrder" value="ALL" onclick="cb_month_year(this.value);" checked="checked"></input>ALL &nbsp;&nbsp; 
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="RW" onclick="cb_month_year(this.value);"></input>Region Wise &nbsp;&nbsp; 
                   <input type="radio" name="displayingOrder" id="displayingOrder" value="BW" onclick="cb_month_year(this.value);" ></input>Bank Wise          
                  <br><br>
                  
                   <div align="left" id="Regions" name="Regions" style="display:block">                	
                   
                    <select size="1" name="txtRegionId" id="txtRegionId" tabindex="1">
                     <option value="101">All Region</option>
                     
                      <%
                    
                      try{
                                ps=con.prepareStatement("select OFFICE_NAME,OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID in ('RN','HO') and office_status_id='CR'");
                                rs=ps.executeQuery();
                                while(rs.next())
                                {
                                    out.println("<option value="+rs.getInt("OFFICE_ID")+">"+rs.getString("OFFICE_NAME")+"</option>");
                                }
                                
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
                    </select>        
                  </div>   
                
                
                       
               
                 <div align="left" id="Banks" name="Banks" style="display:none">
                         	
                    <select name="txtBankName" id="txtBankName" >
                    <option value="">-- Select Bank Account No--</option>
                   
                    </select>         
                    
                </div>
                
                
               </td>               
              </tr>           
               
              
              
               <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
                 </tr>
               </table>
              </div>
                </td>
                </tr>
                </table>
                </div>
              </form>
              </body>
              </html>
             
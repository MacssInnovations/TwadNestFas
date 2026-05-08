<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html> 
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Transfer Proforma Accounting System (Credit / Debit) Authorization System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    
    <!--   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    

    
    <script type="text/javascript" src="../scripts/TPA_Authorization_System.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>

      
    <!-- to avoid future date the above script used-->
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
             
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
            document.frm_TPA_Raised_Create.effectivedate.value=day+"/"+month+"/"+year;
          //  document.frm_TPA_Raised_Create.txtCB_Year.value=year;
          //  document.frm_TPA_Raised_Create.txtCB_Month.value=month;
            
         }
</script>


 </head>
 <body onload="setTimeout('loadTransferUnit()', 500);loadDate();" bgcolor="rgb(255,255,225)">
  <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null,rsbank=null;
  PreparedStatement ps=null,ps2=null,psbank=null;
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
   
   <form id="frm_TPA_Raised_Create" name="frm_TPA_Raised_Create">  
   
   
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer Proforma Accounting System (Credit / Debit) Authorisation System (By Compilation Section)</font>
          </div>
        </td>
      </tr>
    </table>
    
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
           
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="loadTransferUnit();">
                  <option value="999">Compilation Section , Head Office</option>
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
                    <option value="5000">Head Office, Chennai</option>
                    </select>
                  </div>
                </td>
              </tr>
              
      
              <tr class="table">
                <td>
                  <div align="left">Transfer Proforma Type <font color="#ff2121">*</font> </div>
                </td>
                <td>
                  <div align="left">                  
                    <input id="Org_CR_DR" type="radio" value="CR"  name="Org_CR_DR"  checked="checked"/> Credit     
                    <input id="Org_CR_DR" type="radio" value="DR" name="Org_CR_DR" /> Debit  
                     <input id="Org_CR_DR" type="radio" value="ALL" name="Org_CR_DR" /> ALL  
                    <input type="hidden" name="indicrdr" id="indicrdr">                               
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">Accounting Unit which is to be Authorised for TPA <font color="#ff2121">*</font> </div>
                </td>
                <td>
                  <div align="left">
                     <select name="TransferedID" id="TransferedID" onchange="loadacceptunit()" >
                      <option value="" >-- Select Accounting Unit ID --  </option>                    
                    </select>                   
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">Accounting Unit which is to Accept TPA  <font color="#ff2121">*</font> </div>
                </td>
                <td>
                  <div align="left">
                     <select name="acceptunitid" id="acceptunitid" >
                      <option value="" >-- Select Accounting Unit ID --  </option>                    
                    </select>                   
                  </div>
                </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">Reason for transfer<font color="#ff2121">*</font> </div>
                </td>
                <td>
                   <div align="left">                  
                    <input  type="radio" value="Closure"  name="unitauthoriz" checked="checked" /> Closure     
                    <input  type="radio" value="Shift" name="unitauthoriz" /> Shift  
                   <input  type="radio" value="Re-style" name="unitauthoriz" /> Re-Style  
                    <input  type="radio" value="Redeployment" name="unitauthoriz" /> Redeployment                          
                  </div>
                </td>
             
                
         </tr>
              <tr class="table">
                <td>
                  <div align="left">
                     Date Effective from
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="effectivedate" id="effectivedate" 
                           maxlength="10" size="11" onblur="dateCheck(this);" />
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_TPA_Raised_Create.effectivedate,1);"
                         alt="Show Calendar"></img>                   
                  </div>
                  <input type="hidden" name="pre_tpa_type" id="pre_tpa_type"  />
                   <input type="hidden" name="pre_authorizedunit" id="pre_authorizedunit"  />
                    <input type="hidden" name="pre_reason" id="pre_reason"  />
                     <input type="hidden" name="pre_effectivedate" id="pre_effectivedate"  />
                  
                </td>
              </tr>
            
            
              
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="GenParticulars" id="GenParticulars" cols="50"  rows="4"></textarea>
                  </div>
                </td>
              </tr>
        
           <tr class="tdH">
            <td colspan="2" align="center" >
                   <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="storeit()"/>
                     </td>
                     <td>
                    <input type="button" name="butupdate" id="butupdate" value="Update"  onclick="updateall()" style="display:none"/>
                     </td>
                    <td>
                   <input type="button" name="butdelete" id="butdelete" value="Delete"  onclick="deleteall()" style="display:none"/>
                     </td>
                     <td>
                     <input type="button" name="butList" id="butList" value="LIST" onclick="ListAll()"/>
                     </td>
                     <td>
                     <input type="button" name="butCan" id="butCan" value="EXIT"  onclick="javascript:self.close();"/>
                     </td>
                       
                 </tr>
              
              
              </table>
              </div>
  
            </td>
           
          </tr>
          
          
            </table>
     
      <div align="center">
        <table  width="100%" align="center" >
         
        </table>
      </div>
       
  </form>       

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.sql.*,java.util.*,java.sql.*,Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Scheme Mapping</title>
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
    <script type="text/javascript" src="../scripts/Scheme_Mapping.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   
     <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
        function loadDate()
        {
        	 	/* var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 document.TPA.txtCB_Month.value=month;
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear();
                 if(year < 1900) year += 1900; 
                 document.TPA.txtCB_Year.value=year;                  
                 */
        }
	</script>
  </head>
   
  <body onload="clrForm('load'); loadDate();LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  
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
      try{
       
    
   
   %>
  
  
  
  
  
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Scheme Mapping</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="schememapping" id="schememapping" method="POST"  action="../../../../../Scheme_Mapping?command=Add" onsubmit="return checkNull()">
    
        
           
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
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);projectload();">        
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
                        Scheme Status<font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                       <select size="1" name="schemestatus" id="schemestatus" onchange="call('get')" >  
                       <option value="">--Select Scheme Status--</option>
                      <%
                      ps=con.prepareStatement("select SCH_STATUS_ID,SCH_STATUS_DESC from  PMS_SCH_LKP_STATUS" );
                      rs=ps.executeQuery();
                      while(rs.next())
                      {
                    	  out.println( "<option value="+rs.getInt("SCH_STATUS_ID")+">"+rs.getString("SCH_STATUS_DESC")+" </option>");
                      %>
                          <%} %>   
                         </select>
                          
                       
                      </div>
                    </td>
             </tr>    
             
            <tr class="table">
                    <td>
                      <div align="left">
                       Project Level / Component Level<font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <input id="level" type="radio" value="Project" checked="checked" name="level" onclick="changelevel()"/> Project    
                    <input id="level" type="radio" value="Component" name="level" onclick="changelevel()" />Component            
                    
                      </div>
                    </td>
             </tr>
              
              
               <tr class="table">
                    <td>
                      <div align="left">
                 Scheme  as per PMS
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="schemepms" id="schemepms" onchange="call('getschemetypeid')" >
                          
                        </select>
                      </div>
                    </td>
             </tr>
                          
           <tr class="table">
                    <td>
                      <div align="left">
                      Scheme Type ID
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <input id="schemetypedesc" type="text"  name="schemetypedesc" readonly="readonly"/>   
                          <input id="schemetypeid" type="hidden"  name="schemetypeid"/>       
                      </div>
                    </td>
            </tr> 
             <tr class="table">
                    <td>
                      <div align="left">
                 Scheme Component as per PMS
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="schemecomponent" id="schemecomponent" disabled="disabled" >
                        </select>
                      </div>
                    </td>
             </tr>
             
             
           <tr class="table">
                    <td>
                      <div align="left">
                 Existing Scheme Name as per Project Master
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="existscheme" id="existscheme" >
                          
                        </select>
                      </div>
                    </td>
             </tr>
             
              <tr class="table">
                    <td>
                      <div align="left">
                 Existing Component Name as per Project Master
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="existcomponent" id="existcomponent" >
                          
                        </select>
                      </div>
                    </td>
             </tr>
            
            
            
             <tr class="table">
                    <td>
                      <div align="left">
                      Scheme Name as per PMS
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <input id="" type="text"  name=""/>   
                             
                    
                      </div>
                    </td>
             </tr>   
            
            <tr class="table">
                    <td>
                      <div align="left">
                     Component Name as per PMS
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <input id="" type="text"  name=""/>   
                             
                    
                      </div>
                    </td>
             </tr>   
             
             
              <tr class="table">
                    <td>
                      <div align="left">
                    Scheme No
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <input id="" type="text"  name=""/>   
                             
                    
                      </div>
                    </td>
             </tr>   
             
             <tr class="table">
                    <td>
                      <div align="left">
                    Scheme Type Id
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <input id="" type="text"  name=""/>   
                             
                    
                      </div>
                    </td>
             </tr>   
             
             
             <tr class="table">
                    <td>
                      <div align="left">
                    Component No.
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <input id="" type="text"  name=""/>   
                             
                    
                      </div>
                    </td>
             </tr>   
             
             </table>
             </div>
             <br>
             <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
                             
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
             </form>
             





<%}catch(Exception e){out.println(e);} %>


</body>
</html>
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
    <script type="text/javascript" src="../scripts/Scheme_Mapping_List.js"></script>
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
   
  <body onload="clrForm('load'); loadDate();LoadAccountingUnitID('LIST_ALL_UNITS'); ">
  
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
   
  <form name="schememapping" id="schememapping" method="POST"  action="../../../../../Scheme_Mapping_List?command=Add" onsubmit="return checknull()" >
    
        
           
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
                          
                        </select>  <input type="button" onclick="call('loadproject');">  
                      </div>
                    </td>
             </tr>
             </table>
              <div align="center" >
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%">
                      
                          <tr class="tdH">
                            <th>SI.No</th>    
                            <th>Project Name</th>
                            <th>Comp.Name</th>  
                            <th>Project/Component</th>                                                
                            <th>View</th>                                                    
                            <th>Office/State </th>
                            <th>Sch.Status </th>
                            <th>Sch.Type </th>
                            <th>Sch.No </th>
                            <th>Sch.Name </th>
                            <th>Como.No </th>
                            <th>Compo.Name </th>
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
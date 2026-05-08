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
   
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

  <script type="text/javascript" src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
  <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script> 

<script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script><!--
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
--><script type="text/javascript" src="../scripts/Bank_Account_Details.js"></script>
    <script language="javascript" type="text/javascript">

          function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                 
               function chkMod(val){
            	  // alert(val)
            	  document.getElementById("hid").value="";
            	   document.getElementById("hid").value=val;
            	   if(val=="module"){
            		   document.getElementById("divUnit").style.display="none";
            		 //  document.frmBank_Account_Details.txtUnit[0].checked=true;
            		 //   document.frmBank_Account_Details.txtUnit[1].checked=false;
            		   document.getElementById("div1").style.display="block";
            		   document.getElementById("div11").style.display="block";
            		   document.getElementById("div111").style.display="block";
            		   document.getElementById("div1112").style.display="block";
            		 //  document.getElementById("div1111").style.display="none";
            		  // document.getElementById("div11112").style.display="none";
            		   document.getElementById("div10111").style.display="block";
             		  document.getElementById("div101112").style.display="block";
            	   }
            	   if(val=="office"){
            		   document.getElementById("divUnit").style.display="none";
            		  // document.frmBank_Account_Details.txtUnit[0].checked=true;
            		 //   document.frmBank_Account_Details.txtUnit[1].checked=false;
            		   document.getElementById("div1").style.display="none";
            		   document.getElementById("div11").style.display="none";
            		   document.getElementById("div111").style.display="block";
            		   document.getElementById("div1112").style.display="block";
            		   //document.getElementById("div1111").style.display="none";
            		   //document.getElementById("div11112").style.display="none";
            		   document.getElementById("div10111").style.display="block";
            		  document.getElementById("div101112").style.display="block";
            	   }
               } 
               
             function ChkUnit(value){
            	  
             if(value=="Y"){
            	// document.frmReport.txtUnit[0].checked=true;
                 document.getElementById("divUnit").style.display="none";
             }  else if(value=="N"){
            	// document.frmReport.txtUnit[0].checked=true;
                     document.getElementById("divUnit").style.display="block";
                 }else{
                     alert('Error');
                 }   
             }
             function chkUnitrad(){
                // alert('et');
                 if(document.frmReport.txtUnit[1].checked==true)
            	 document.frmReport.txtUnit[0].checked=true;
             }
    </script>
     <script type="text/javascript" language="javascript">
   
    </script>
    <title>Bank Account Maintained by Units List</title>
  </head>
  
  <body class="table" onload="LoadAccountingUnitID('LIST_ALL_UNITS');">
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
             } 
          
      %>
  
  <form action="../../../../../Bank_Account_Details.view?Command=PDF" name=frmReport id="frmReport" method=POST > 
  <input type="hidden" id="hid" name="hid" value="office">
    <input type="hidden" id="hid_unit" name="hid_unit" value="Y">
    <table width="100%" >
        
        
		        <tr>
		            <td class="tdH"><center><b>Bank Account Maintained by Units List</b></center></td>
		        </tr>
		        
        
        
		          <tr>
		            <td>
		                <table border="0" cellspacing="3" cellpadding="3" width="100%">
		                  <tr>
                        <td>
                            Report Option:
                        </td>
                        <td colspan="3">
                            <input type=radio name=txtoption id=txtoption value="office"  onclick="chkMod(this.value);" onchange="chkUnitrad();" checked="checked">Office wise
                            <input type=radio name=txtoption id=txtoption value="module"  onclick="chkMod(this.value)" onchange="chkUnitrad();">Mode of Operation wise
                          
                        </td>                        
                    </tr>
                       <tr class="table">
					<td>
                 <div align="left" id="div1" style="display: none;">Operational Mode<font
			                            color="#ff2121">*</font></div></td>
			                            	<td>
                  <div align="left" id="div11" style="display: none;"><!--
			                    <select size="1" name="cmbOprMode"
			                            id="cmbOprMode" onchange="AccNoload(this.value,'<%=request.getContextPath()%>')">
			                           -->
			                            <select size="1" name="cmbOprMode"
			                            id="cmbOprMode">
			                           <option value="">--Select--</option>
			                            <% 
			                            try{
			                            String sql="Select AC_OPERATIONAL_MODE_ID,AC_OPERATIONAL_MODE from FAS_MST_AC_OPER_MODES order by AC_OPERATIONAL_MODE_ID,AC_OPERATIONAL_MODE ";
			                            ps=con.prepareStatement(sql);
			                            rs=ps.executeQuery();
			                            while(rs.next()){
			                            	out.println("<option value="+rs.getString("AC_OPERATIONAL_MODE_ID")+">"+rs.getString("AC_OPERATIONAL_MODE")+"</option>");
			                            }
			                            }catch(Exception e){
			                            	e.printStackTrace();
			                            }
			                            %>
			                    </select>
			                    </div>	</td>
			                            </tr>
                   
		                       <tr class="table">
		                <td>
		                   <div align="left" id="div111" style="display: block;">
		                    Accounting Unit Code 
		                    <font color="#ff2121">*</font>
		                  </div>
		                </td>
		                <td>
		                  <div align="left" id="div1112" style="display: block;">
		                  <input type="radio" id="txtUnit" name="txtUnit" value="Y" checked="checked" onchange="ChkUnit(this.value);">All
		                   <input type="radio" id="txtUnit" name="txtUnit" value="N" onchange="ChkUnit(this.value);">Single Unit
		                  <div align="left" id="divUnit" style="display: none;">
		                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
		     
		                    </select>
		                  </div>
		                  </div>
		                </td>
		              </tr><!--
		              
              <tr class="table">
					<td>
			                    <div align="left" id="div1111" style="display: block;">Accounting For Office Code <font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left" id="div11112" style="display: block;">
			                    <select size="1" name="cmbOffice_code"
			                            id="cmbOffice_code" >
			                    </select>
			                    </div>		
			                </td>
				</tr>
				
				  --><tr class="table">
					<td>
			                    <div align="left" id="div10111" style="display: block;">Status <font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left" id="div101112" style="display: block;">
			                <input type="radio" id="txtStatus" name="txtStatus" value="Y" checked="checked">Live
			                 <input type="radio" id="txtStatus" name="txtStatus" value="N">Cancel
			                  <input type="radio" id="txtStatus" name="txtStatus" value="All">All
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
            </td>
           </tr>
        </table>

  
  </form>
  </body>
</html>
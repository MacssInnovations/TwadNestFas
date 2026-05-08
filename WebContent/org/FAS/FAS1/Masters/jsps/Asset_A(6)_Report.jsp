<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Asset.6</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
  
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <!-- to avoid future date the above script used-->
    
<script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
	<script type="text/javascript" src="../scripts/Asset_6_Report.js" ></script>
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function loadYear()
                {
                	
                	   var  year=0;
                	var today=new Date();
                	  var day=today.getDate();
                	     var month=today.getMonth();
                	     month=month+1;
                	     var year=today.getYear();
                	     if(year < 1900) year += 1900;
                	  var finan_year=0,finan_year1=0,finan_year2=0;
                	   if(month<4)
                	   {
                		   year1=year-1;
                	   }
                	   else{
                		   year1=year+1;
                	   }
                
                	   if (month < 4) {
                		   finan_year = year1 + "-" + year;
                		   finan_year1 = (year1-1) + "-" + (year-1);
                		   finan_year2 = (year1-2) + "-" + (year-2);
                 		} else {
                 			finan_year = year + "-" + year1;
                 			finan_year1 = (year-1) + "-" + (year1-1);
                 			finan_year2 = (year-2) + "-" + (year1-2);
                 		}
                	     for(var i=0;i<3;i++)
                	    	 {
                	    	 if(i==0)
                	  			{
                	  				var se = document.getElementById("cmbFinancial_Year");
                	  		  		var op = document.createElement("OPTION");
                	  		  		op.value = finan_year2;
                	  		  		var txt = document.createTextNode(finan_year2);
                	  		  		op.appendChild(txt);
                	  		  		se.appendChild(op);
                	  			}else if(i==1)
                	  			{
                	  				var se = document.getElementById("cmbFinancial_Year");
                	  		  		var op = document.createElement("OPTION");
                	  		  		op.value = finan_year1;
                	  		  		var txt = document.createTextNode(finan_year1);
                	  		  		op.appendChild(txt);
                	  		  		se.appendChild(op);
                	  		  		
                	  			} else if(i==2)
                	  			{
                	  				var se = document.getElementById("cmbFinancial_Year");
                	  		  		var op = document.createElement("OPTION");
                	  		  		op.value = finan_year;
                	  		  		var txt = document.createTextNode(finan_year);
                	  		  		op.appendChild(txt);
                	  		  		se.appendChild(op);
                	  		  		
                	  			}  
                     	     }
                	     document.getElementById("cmbFinancial_Year").value=finan_year;
                	 //    alert(document.getElementById("cmbFinancial_Year").value)
                	    	 }
                </script>
                 <script language="javascript" type="text/javascript">
                function rad_Abstract()
                {
                	
                	if(document.frmAsset6.All.checked == true){
                		 
                		document.frmAsset6.AllMajorType.checked=false;
                		document.frmAsset6.OneMajorType.checked=false;
                			document.getElementById("tr_row").style.display="none";
                			document.getElementById("tr_row1").style.display="none";
                		
                			document.getElementById("rep_type").value="All";
                			document.getElementById("major_cmb").value="";
                			document.getElementById("hid").value=1;
                			 // alert(document.getElementById("rep_type").value);
                	}
                }
                function rad_AllMajor()
                {
                	if(document.frmAsset6.AllMajorType.checked == true){
            		document.frmAsset6.All.checked=false;
            		document.frmAsset6.OneMajorType.checked=false;
            			document.getElementById("tr_row").style.display="none";
            			document.getElementById("tr_row1").style.display="none";
            		
            			document.getElementById("rep_type").value="AllMajorType";
            			document.getElementById("major_cmb").value="";
            			document.getElementById("hid").value=2;
            			 // alert(document.getElementById("rep_type").value);
                }
                }
                function rad_OneMajor(){
                //window.location.reload();	
                if(document.frmAsset6.OneMajorType.checked == true){
                		document.frmAsset6.All.checked=false;
                		document.frmAsset6.AllMajorType.checked =false;
                		document.getElementById("tr_row").style.display="block";
                		document.getElementById("tr_row1").style.display="block";
                		document.getElementById("rep_type").value="OneMajorType";
                		document.getElementById("hid").value="";
                //alert(document.getElementById("rep_type").value);
                }
                }
    </script>
  </head>
  <body onload="loadYear();LoadAccountingUnitID('LIST_ALL_UNITS');">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">A6 Twad Form</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmAsset6" id="frmAsset6" action="">
       <input type="hidden" id="hid" name="hid" value="">           
  <% 
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
       Statement statement=null;
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
       HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
     int bankid=0;
     
    //int empid=9315;
    int  oid=0;
    String oname="";
    try
    {
           
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
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
     /* */      
            System.out.println("off id.. emp id"+oid+".."+empid);     
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
   
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
                        <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="checkOffice();">
                          <%
                      int unitid=0;
                      String unitname="";
                     String FAS_SU="";
   
    if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
                      try{
                     
                     
                            if(oid==5000)
                            {
                                 //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                                String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                                ps=con.prepareStatement(getWing);
                                ps.setInt(1,oid);
                                ps.setInt(2,empid);
                                ps.setInt(3,oid);
                                rs=ps.executeQuery();
                              
                                  if(rs.next())
                                  {
                                      out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                      unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                      
                                      System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                                      System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                                      System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                                      ps.close();
                                      rs.close();
                                          if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                          { 
                                             String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                             ps=con.prepareStatement(su);
                                             ps.setInt(1,unitid);
                                             rs=ps.executeQuery();
                                              while(rs.next())
                                              {
                                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          }
                                  }
                              ps.close();
                              rs.close();
                              }
                                  else
                                  {
                                    ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                    ps.setInt(1,oid);
                                    rs=ps.executeQuery();
                                      if(rs.next())
                                      {
                                          System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                          System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                          //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                          out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                          unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                          ps.close();
                                          rs.close();
                                          if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                          { 
                                             String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                             ps=con.prepareStatement(su);
                                             ps.setInt(1,unitid);
                                             rs=ps.executeQuery();
                                              while(rs.next())
                                              {
                                                 out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          }
                                      }
                                      ps.close();
                                      rs.close();
                                  }
                    
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                      %>
                      </select>
                  </div>
                </td>
              </tr>
        
           <tr class="table">
              <td class="table">
                <div align="left">
                         Financial Year <font color="#ff2121">*</font>
              </div>
              </td>
              <td colspan="2">
               <div align="left">
                    <select name="cmbFinancial_Year" id="cmbFinancial_Year" >
                    <option value="">--Select Year--</option>
                    </select>
                    </div>
              </td>
              </tr>
               <tr>
              <td class="table">
                <div align="left">
                         Report Type <font color="#ff2121">*</font>
              </div></td>
             <td class="table">
             <input type="radio" id="rep_type" name="All" value="All" onclick="rad_Abstract();" />Abstract
              <input type="radio" id="rep_type" name="AllMajorType"  value="AllMajorType"onclick="rad_AllMajor();" />All Major Type
               <input type="radio" id="rep_type" name="OneMajorType"  checked="checked"  value="OneMajorType"onclick="rad_OneMajor();" />One Major Type
             </td>
              </tr>
              
                <tr >
                <td class="table" colspan="">
                 <table cellspacing="1" cellpadding="2"  width="100%" id="tr_row" style="display: block;" >
                 <tr>
              <td class="table">
                <div align="left">
                         Asset Major Class <font color="#ff2121">*</font>
              </div>
              </td></tr></table>
                  </td>
                   <td class="table">
               <table cellspacing="1" cellpadding="2" width="100%" id="tr_row1" style="display: block;" >
               <tr>
              <td class="table"  >
            <div align="left">
              <select id="major_cmb" name="major_cmb" ><option value="">--Select--</option>
              <%
              PreparedStatement pre_sts=con.prepareStatement("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from  FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE");
              ResultSet r_set=pre_sts.executeQuery();
              while(r_set.next()){
              out.println("<option value="+r_set.getInt("ASSET_MAJOR_CLASS_CODE")+">"+r_set.getString("ASSET_MAJOR_CLASS_DESC")+"</option>");
                 }          
               %>
              
              </select></div></td></tr></table>
              </td>
              </tr>
          
          
               <tr class="table">
                        <td>
                            Report Option:
                        </td>
                        <td colspan="3">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>                        
                    </tr>
            <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
				          <td colspan="3" class="table">
				           <input type="button" name="cmdsubmit" value="SUBMIT" onclick="report_Sub();"
				                   id="cmdsubmit" />
				            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow();" />
				          </td>
				 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>
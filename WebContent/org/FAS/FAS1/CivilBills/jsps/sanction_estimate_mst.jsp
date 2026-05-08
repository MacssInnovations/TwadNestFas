<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Sanction Estimate System</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
     
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>  
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript"   src="../scripts/sanction_estimate_mst.js"></script>
    <script type="text/javascript"   src="../scripts/AccHeadCode.js"></script>
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
                 document.formsanc_estimate.txtSanction_Estimate_PreparedOn.value=day+"/"+month+"/"+year;                
        }
    
    </script>
    
  </head>
      
      <body onload="clen();LoadAccountingUnitID('LIST_ALL_UNITS');loadDate();foc();" bgcolor="rgb(255,255,225)">
  <!-- //////////////////////Sanction Master ///////////////////////////////////-->
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
        <table cellspacing="1" cellpadding="3" width="100%" >
            <tr class="tdH">
                <td colspan="2">
                    <div align="center">
                        <font size="4">General Sanction Estimate</font>
                    </div>
                </td>
            </tr>
        </table>
    
  <form name="formsanc_estimate" id="formsanc_estimate_General" method="POST"
                  action="../../../../../Sanction_estimate_mst?Command=Add" onSubmit="return checkfields();clen();">
    <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
            <h2 class="tab" >General </h2>
            
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
                            <td width="27%">
                                <div align="left">
                                    Accounting Unit Code 
                                        <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td width="73%">
                                <div align="left">
                                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr class="table">
                            <td width="27%">
                                <div align="left">
                                    Accounting For Office Code
                                        <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td width="73%">
                                <div align="left">
                                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >
                  
                                    </select>   
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                    <table cellspacing="1" cellpadding="3" border="1" width="100%">
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Sanction Estimate Applicable Financial Year <font color="#ff2121">*</font>
                            </div>
                        </td>
                       <td width="73%">
                            <div align="left">
                                <select size="1" name="cmbSanction_Estimate_FY" id="cmbSanction_Estimate_FY"  onchange="loadassetcode();">
                                    <option value="0">Select</option>
                                    <!-- <option value="2008-2009">2008-2009</option>
                                    <option value="2009-2010">2009-2010</option>
                                    <option value="2010-2011" selected="true">2010-2011</option>  -->
                                    <%
					                        st=con.createStatement();
					                        rs=st.executeQuery("select financial_year from cash_book_control");
					                        while(rs.next())
					                        {
					                            out.println("<option value='"+rs.getString("financial_year")+"'>"+rs.getString("financial_year")+"</option>");
					                        }
                    				%>
                                </select>
                            </div>
                        </td> 
              <!--  <td colspan="2">
                    <select name="cmbFinancialYear" id="cmbFinancialYear" >
                    <option value="">Select Year</option>
                    
                    </select>
                </td>
              -->
                    </tr>
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Sanction Estimate prepared on <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtSanction_Estimate_PreparedOn" 
                                id="txtSanction_Estimate_PreparedOn" 
                                maxlength="10" size="10"                
                     onFocus="javascript:vDateType='3'"
                     onkeypress="return  calins(event,this)"
                     onblur="return checkdt(this);" />&nbsp;&nbsp;
                                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].txtSanction_Estimate_PreparedOn,0);" alt="Show Calendar" ></img>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left" style="display:none">
                                Sanction Estimate Number
                            </div>
                        </td>
                 
                        <td width="73%">
                            <div align="left" style="display:none">
                                <input type="hidden" name="txtsanc_esti_no"  id="txtsanc_esti_no"  maxlength="20" size="25" />&nbsp;&nbsp;  
                                    System Generated
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Total Sanction Estimate Amount (Rs.P)
                                <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtTotSanction_Estimate_Amount"  id="txtTotSanction_Estimate_Amount"  maxlength="16" size="12" onkeypress="return filter_real(event,this,10,2);" />&nbsp;&nbsp;                   
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                 Debit Account Head
                                 <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6"
                           onkeypress="return numbersonly(event)"
                            onchange="sixdigit(this.value);" 
                            onblur="doFunction('checkCode','null');"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                               Sanction Estimate prepared By
                               <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                 <input type="text" name="txtSanction_Estimate_PreparedBy"  id="txtSanction_Estimate_PreparedBy"  maxlength="40" size="40" readonly="readonly"/>&nbsp;&nbsp;  
                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="emp_popup_sanction_preparedby();"/>&nbsp;&nbsp; 
                        <input type="text" name="txtSanction_Estimate_Empcode1"  id="txtSanction_Estimate_Empcode1"  maxlength="6" size="6" onchange="emp_sanction_preparedby();" onkeypress="return numbersonly(event)"/>  
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                               Sanction Estimate approved By
                               <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                 <input type="text" name="txtSanction_Estimate_ApprovedBy"  id="txtSanction_Estimate_ApprovedBy"  maxlength="40" size="40" readonly="readonly"  />&nbsp;&nbsp; 
                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="emp_popup_sanction_approvedby();"/>&nbsp;&nbsp;  
                        <input type="text" name="txtSanction_Estimate_Empcode2"  id="txtSanction_Estimate_Empcode2"  maxlength="6" size="6" onchange="emp_sanction_approvedby();" onkeypress="return numbersonly(event)"/>
                            </div>
                        </td>
                    </tr> 
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Remarks
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="7" onkeypress="return check_leng('remarks',this.value);"
                    rows="4"></textarea>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>    
    
            <div class="tab-page" id="gd" >
                <h2 class="tab" > Details</h2>
                    <div align="center">
           
                        <table cellspacing="1" cellpadding="2" border="1" width="100%">
                            <tr>
                                <td colspan="2">
                                    <div id="sanc_det_disp" >
                        
                                        <table cellspacing="1" cellpadding="2" border="1"
                                            width="100%">
                                            <tr class="tdTitle">
                                                <td colspan="2">
                                                    <div align="left">
                                                        <strong>Asset Details</strong>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                        
                                       <table cellspacing="1" cellpadding="2" border="1"
                                            width="100%">
                                           
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Asset Code <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                         <select size="1" name="cmbasset_code" id="cmbasset_code" tabindex="4"  onchange="loadassetdesc(this.value);loadassetmajclass();">
                                                             <option value="0">---Select Asset Code--- </option>
                                                        </select>
                                                    </div>
                                                </td>
                                            </tr>
                                            
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Asset Description
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txtAsset_Description"  id="txtAsset_Description"  maxlength="20" size="21" readonly/>&nbsp;&nbsp;                   
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Asset Major Classification
                                                        
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                            <input type="text" name="txtAssetMajClassDesc"  id="txtAssetMajClassDesc"  maxlength="20" size="21" readonly/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Sanction Estimate Amount <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txtSanction_Estimate_Amount"  id="txtSanction_Estimate_Amount"  maxlength="16" size="12" onkeypress="return filter_real(event,this,10,2);"/>&nbsp;&nbsp;                   
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Particulars
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <textarea name="txtParticulars" id="txtParticulars" cols="50" tabindex="7" onkeypress="return check_leng('particulars',this.value);"
                                                                rows="4"></textarea>
                                                    </div>
                                                </td>
                                            </tr>
                                            
                                            <tr align="center" class="tdTitle">
                                                <td colspan="2" height="23">
                                                    <div align="center">
                                                        <table border="0">
                                                            <tr>
                                                                <td>
                                                                    <input type="button" name="cmdadd" id="cmdadd"
                                                                            value="ADD" onclick="ADD_GRID()" style="display:block" />
                                                                </td>
                                                                <td>
                                                                    <input type="button" name="cmdupdate" value="UPDATE"
                                                                        id="cmdupdate" onclick="update_GRID()"
                                                                        style="display:none"/>
                                                                </td>
                                                                <td>
                                                                    <input type="button" name="cmddelete" value="DELETE"
                                                                        id="cmddelete" onclick="delete_GRID()"
                                                                        disabled="disabled"/>
                                                                </td>
                                                                <td>
                                                                    <input type="button" name="cmdclear" value="CLEARALL"
                                                                        id="cmdclear" onclick="clear_main_fields();"/>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </td>
                                              </tr>
                                        </table> 
                                        
                                            <div id="grid" style="display:block">
                                                <table id="mytable" cellspacing="3" cellpadding="2"
                                                        border="1" width="100%">
                                                    <tr class="table">
                                                        <th>Select</th>
                                                        <th>Asset Major Classification</th>
                                                        <th>Asset Code</th>
                                                        <th>Asset Description</th>
                                                        <th>Sanction Estimate Amount</th>
                                                        <th>Particulars</th>
                                                    </tr>
                                                        <tbody id="grid_body" class="table" align="left" >
                                                        </tbody>
                                            </table>
                                        </div>           
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    
            </div>
                <div align="center">
                    <table cellspacing="1" cellpadding="3" width="100%">
                        <tr class="tdH">
                            <td>
                                <div align="center">
                                    <input type="submit" name="butSub" id="butSub" value="SUBMIT" />
                                            &nbsp;&nbsp;&nbsp;
                                    <input type="button" name="butCancel" id="butCancel" value="CANCEL" 
                                            onclick="clrForm();"/>
                                            &nbsp;&nbsp;&nbsp; 
                                    <input type="button" name="butList" id="butList" value="EXIT"
                                            onclick="exitmethod();"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
    </div>
   </form>
 </body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Phone Master System</title>
      
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
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
   <script type="text/javascript" language="javascript" src="../scripts/phone_master_list_js.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  <!-- ////////////////////// phone master heading ///////////////////////////////////-->
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">List of Phone Details </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="formPhone_MasterList" id="formPhone_General" method=""
                  action="">
   
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onchange="delexistingrows();" >
                  
                    </select>
                  </div>
                </td>
              </tr>
            </table>
             <br>
       <table cellspacing="1" cellpadding="3" border="0" width="100%">
            <tr align="center" class="tdH"> 
               <td colspan="2">
                    <b>Search By Custodian Type</b>
                 </td>
             </tr>
                <tr align="left">
                    <td class="table">
                        <div align="left">Custodian Type</div>
	            </td>
	            <td>
	              <div align="left">
	                 <input type="radio" name="rad_cust_type" id="rad_cust_type"
		                           value="T" checked="checked" />TWAD
		                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
		                    <input type="radio" name="rad_cust_type" id="rad_cust_type"
		                           value="P" />Privileged Users &nbsp;&nbsp;&nbsp;&nbsp; 
	              </div>
	            </td>
	          </tr>
                 </table> 
        <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
              <tr class="tdH">
                <td>
                  <div align="center">
                    <input type="button" name="butGo" id="butGo" value="SUBMIT" onclick="callGridList();"/>
                     &nbsp;&nbsp;&nbsp; 
                     <input type="button" name="butList" id="butList" value="CANCEL"
                           onclick="exitmethod();"/>
                    </div>
                    </td>
                    </tr>
            </table>
            </div>
                
                  <div id="grid" style="display:block">
                  <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                              <tr class="tdH">
                                    <th>Accounting Unit Office Id</th>
                                    <th>Accounting  Unit Id</th>
                                    <th>Employee Id</th>
                                    <th>Custodian Type</th>
                                    <th>Show Details?</th>
                              </tr>
                                <tbody id="grid_body" class="table" align="left" >
                                </tbody>
                  </table>
                  
                  </div>

<!-- //////////////////////////////////////////Records view by Pagination-PageNo-Prev-Next////////////////////////////////////////////////////////////////-->
    <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
        <tr>
            <td>
                <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
                    <tr class="tdH">
                         <td>
                                <table align="center" cellspacing="3" cellpadding="2"
                                        border="0" width="100%">
                                     <tr>
                                         <td width="30%">
                                              <div align="left">
                                                <div id="divpre" style="display:none"></div>
                                              </div>
                                           </td>
                                            <td width="40%">
                                                <div align="center">
                                                    <table border="0">
                                                        <tr>
                                                            <td>
                                                            <div id="divcmbpage" style="display:none">
                                                                    Page&nbsp;&nbsp;<select name="cmbpage"
                                                                                id="cmbpage"
                                                                                onchange="changepage()"></select>
                                                            </div>
                                                        </td>
                                                        <td>
                                                          <div id="divpage">
                                                          </div>
                                                        </td>
                                                      </tr>
                                                    </table>
                                              </div>
                                            </td>
                                            <td width="30%">
                                              <div align="right">
                                                <div id="divnext" style="display:none"></div>
                                              </div>
                                            </td>
                                    </tr>
                                </table>
                    
                              </td>
                            </tr>
                         </table>
                    </td>
                </tr>
                  <tr class="tdH">
                    <td>
                      <div align="center">
                             <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="btncancel()">
                      </div>
                    </td>
                 </tr>
          </table>

        </div>     
   </form>
 </body>
</html>
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
    <title>Auction Asset Master</title>
      
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
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/Masters/scripts/auctionAssetsDone.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
  </head>
  <%String accId=request.getParameter("accId");
  	String offId=request.getParameter("offId");
  %>
  <body onload="getAuctionDetails('all'),surveyReportNum();">
  <!-- ////////////////////// phone master heading ///////////////////////////////////-->
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">List of Asset Auction Details </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="assetAuctionList" id="assetAuctionList">   
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table">
                <td width="27%">
                  <div align="left">
                    Survey No 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="73%">
                  <div align="left">
                    <select name="surveyReportNo" id="surveyReportNo" onchange="callServer('assetCode')">
                    	<option value="select">Select</option>
                    </select>
                  </div>
                  <input type="hidden" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" value="<%=accId%>">
                  <input type="hidden" name="cmbOffice_code" id="cmbOffice_code" value="<%=offId%>">
                </td>
              </tr>
              
              <tr class="table">
                <td width="27%">
                  <div align="left">
                    Asset Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="73%">
                  <div align="left">
                    <select name="assetAuctioin" id="assetAuctioin" onchange="getAuctionDetails('asset')">
                    	<option value="select">Select</option>
                    </select>
                  </div>
                </td>
              </tr>
            </table>
            <br>        
            <div id="grid" style="display:block">
                  <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%">
			                  <tr class="tdH">
								<td colspan="6">Existing Details</td>
								<td colspan="2">
			            			Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" id="cmbpagination" onchange="changepagesize()">
			                  		<option value="5" >5</option>
			                  		<option value="10" selected="selected">10</option>
			                  		<option value="15">15</option>
			                  		<option value="20">20</option>
			                	</select>
			          			</td>
							</tr>
                            <tr class="tdH">                    
                                    <th>Survey No</th>
                                    <th>Auction No</th>
                                    <th>Asset Code</th>
                                    <th>Reference No</th>
                                    <th>Reference Date</th>
                                    <th>Auctioneer</th>
                                    <th>Auction Amount</th>                                    
                                    <th>Remarks</th>
                            </tr>
                            <tbody id="tblList" class="table" align="left" >
                            </tbody>
                            <tr>
         						<td colspan="8" class="tdH">
         						<center>Pages          
          							<select name="cmbpage" id="cmbpage" onchange="changepage()" ></select></center>
        						</td>
     						</tr>
                  	</table>
                  </div>

<!-- //////////////////////////////////////////Records view by Pagination-PageNo-Prev-Next////////////////////////////////////////////////////////////////-->
    <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >        
                  <tr class="tdH">
                    <td>
                      <div align="center">
                             <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="self.close()">
                      </div>
                    </td>
                 </tr>
          </table>

        </div>     
   </form>
 </body>
</html>
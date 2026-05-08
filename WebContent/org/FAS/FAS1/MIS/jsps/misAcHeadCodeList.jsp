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
    <title>Account Head Code</title>
      
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
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/MIS/scripts/misAcHeadCode.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
  </head>
  <%String mainCatId=request.getParameter("mainCatId");
  	String subCatId=request.getParameter("subCatId");
  	String fundType=request.getParameter("fundType");
  %>
  <body onload="callServer('loadAcc')">
  <!-- ////////////////////// phone master heading ///////////////////////////////////-->
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">List of Account Head Code </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="assetAuctionList" id="assetAuctionList">   
          <div align="center">
          			<input type="hidden" name="categoryCode" id="categoryCode" value="<%=mainCatId%>">
                  	<input type="hidden" name="subCategoryCode" id="subCategoryCode" value="<%=subCatId%>">
                  	<input type="hidden" name="fundExpend" id="fundExpend" value="<%=fundType%>">                    
            <div id="grid" style="display:block">
                  <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%">
			                  <tr class="tdH">
								<td colspan="3">Existing Details</td>
								<td colspan="1">
			            			Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination1" id="cmbpagination1" onchange="changepagesize1()">
			                  		<option value="5" >5</option>
			                  		<option value="10" selected="selected">10</option>
			                  		<option value="15">15</option>
			                  		<option value="20">20</option>
			                	</select>
			          			</td>
							</tr>
                            <tr class="tdH">                    
                                    <th>Select</th>
                                    <th>Account Head Code</th>
                                    <th>Account Head Description</th>
                                    <th>Status</th>                                    
                            </tr>
                            <tbody id="tblList1" class="table" align="left" >
                            </tbody>
                            <tr>
         						<td colspan="4" class="tdH">
         						<center>Pages          
          							<select name="cmbpage1" id="cmbpage1" onchange="changepage1()" ></select></center>
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
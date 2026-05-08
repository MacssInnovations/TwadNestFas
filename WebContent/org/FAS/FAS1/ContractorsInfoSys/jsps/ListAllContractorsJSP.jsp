<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Contractors List</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript" src="../scripts/AllContractorsListJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">

    function btncancel()
    {
     self.close();
    }

    function EditHead(accHeadId)
    {
                
                 var accHeadCode="",accHeadDesc="",bankid="",operID="";
                 accHeadCode=accHeadId;
                 r=document.getElementById(accHeadCode);
                 rcells=r.cells;
                 //alert(rcells.item(1).firstChild.nodeValue);
                 //alert(rcells.item(2).firstChild.nodeValue);
                 //alert(rcells.item(3).firstChild.nodeValue);
                 //alert(rcells.item(4).firstChild.nodeValue);
                 //alert(rcells.item(5).firstChild.nodeValue);
                 //alert(rcells.item(6).firstChild.nodeValue);
                 accHeadDesc=rcells.item(2).firstChild.nodeValue;
                 bankid=rcells.item(3).firstChild.nodeValue;
                 operID=rcells.item(5).firstChild.nodeValue;
                
        Minimize();
    
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentBankAccHeads(accHeadCode,accHeadDesc,bankid,operID);
        //return true;
   }
   

</script>
  </head>
    <%
  		int offid=Integer.parseInt(request.getParameter("param"));
        out.println("<input type=hidden name='off_id' id='off_id' value="+offid+">");        
        if(offid==5000)
        {
        	int cmbWing1=Integer.parseInt(request.getParameter("cmbWing"));
        out.println("<input type=hidden name='cmbWing' id='cmbWing' value="+cmbWing1+">");
        }
  %>
  <body onload="loadGrid()">
 

        
  <form action="" name="frmAccountList">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM - FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
              List of All Contractors
            </div></td>            
        </tr>
       
      </table>      
     
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
             
         <tr>
            <td colspan="2">
                <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%">
                		<tr class="tdH">
					      <th>Select </th>
					       <th>Contractor&nbsp;Name  </th>
					       <th>Contractor Address   </th>     
					      <th>Regn. Sl. No.</th>   
					       <th>Regn. Date </th>
					       <th>Class</th>
                                               <th>Status</th>
					      </tr>
                          <tr class="tdH">   
                                <tbody id="tblList" name="tblList" class="table">
                                </tbody>
                         </tr>                         
                 </table>            
            </td>            
        </tr> 
        <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
         <tr>
		    <td colspan="17">
		      <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" class="tdH" >
		                    <tr >
		                        <td width="30%">
		                             <div align="left"> <div id="divpre" style="display:none"></div> </div>
		                        </td>
		                        <td width="40%">
		                             <div align="center"><table border="0"><tr><td> <div id="divcmbpage" style="display:none" ><font color="Black" size="2"><strong>
		                             Page&nbsp;&nbsp;<select name="cmbpage" id="cmbpage" onchange="changepage()"></select></strong></font></div></td><td>
		                             <div id="divpage"></div></td></tr></table> </div>
		                        </td>
		                        <td width="30%">
		                             <div align="right"> <div id="divnext" style="display:none"></div> </div>
		                        </td>
		                    </tr>
		      </table>
		    </td>
		    </tr>
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> </table>
    </form></body>
</html>
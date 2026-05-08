<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
    	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
    	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
	    <title>Account Head Verified List</title>
	    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     	<script type="text/javascript" src="../scripts/AccountHead_Verification.js"></script>
  </head>
  <body onload="initialload();">
          <form name="AccHeadListForm" action="Get">
          <% 
          System.out.println("yesssssssss");
          String fin_year=request.getParameter("fin_year");
          System.out.println("fin_year"+fin_year);
          String maj_grp=request.getParameter("MajorGrp");
          System.out.println("maj_grp"+maj_grp);
          String min_grp=request.getParameter("MinorGrp");
          System.out.println("min_grp:"+min_grp);
          %>
          <input type="hidden" name="fin_year" id="fin_year" value=<%=fin_year%> ></input>
          <input type="hidden" name="Maj_grp" id="Maj_grp" value=<%=maj_grp%> ></input>
            <input type="hidden" name="Min_grp" id="Min_grp" value=<%=min_grp%> ></input>
               <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                 <tr class="tdH" align="center">
                    <th>
                            List of Verified Account Head during the Financial Year
                    </th>
                </tr>
           </table>
           
           <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" >
                <tr class="tdH">
                		<th>
             				Financial Year
            			</th>
            			<th>
             				Major Group
            			</th>
            			<th>
             				Minor Group
            			</th>
            			<th>
             				Verified By
            			</th>
            			<th>
             				Verified On
            			</th>
            			<th>
                     		Status
                     	</th>
                   		<th>
             				Account Head Code
            			</th>
            			<th>
             				Account Head Description
            			</th>
                </tr>
             <tbody id="tblList" align="center" class="table">
             </tbody>
            </table>
            <table align="center" cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <td>
                    <div align="center">
                             <input type="button" id="exit" name="exit" value="Exit" onclick=" self.close();"></input>
                    </div>
                  </td>
                </tr>
            </table> 
          </form>
  </body>
</html>
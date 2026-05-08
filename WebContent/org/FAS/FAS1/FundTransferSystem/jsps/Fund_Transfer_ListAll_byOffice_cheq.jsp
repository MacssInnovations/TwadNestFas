<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Fund Transfer System</title>
     
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/Fund_Transfer_ListAll_byOffice.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script> 
    <script type="text/javascript" language="javascript">
     function loadyear_month()
     {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
       // document.frmFund_Transfer_ListAll_byOffice.txtCB_Year.value=year
     //   document.frmFund_Transfer_ListAll_byOffice.txtCB_Month.value=month;
        
    }
    </script>
      <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
        var cheqno="";
        var cheqdate="";
        var cheqamount="";
         
                 r=document.getElementById(rowID);
                 rcells=r.cells;
                 alert(rcells.item(3).firstChild.nodeValue);
    alert(rcells.item(7).firstChild.nodeValue);
    alert(rcells.item(8).firstChild.nodeValue);
      cheqno=rcells.item(7).firstChild.nodeValue;
      cheqdate=rcells.item(8).firstChild.nodeValue;
      cheqamount=rcells.item(3).firstChild.nodeValue;
           
          
        Minimize();
    
      
        opener.doParentBankAccNumbers(cheqno,cheqdate,cheqamount);
      
   }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>    
      </head>
        <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table" onload="loadyear_month()" >
  <form name="frmFund_Transfer_ListAll_byOffice" method="POST">
   <%
  Connection con=null;
  ResultSet rs=null,rs2=null,rs3=null;
  PreparedStatement ps=null,ps2=null,ps3=null;
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
    
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    
    //int empid=1663;
    int  oid=0;
    int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,recNo=0;

            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
                 yr=Integer.parseInt(request.getParameter("yr"));
                 System.out.println(yr);
                 }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
                 mon=Integer.parseInt(request.getParameter("mon"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          
   
    
   %>
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>List of Fund Transfers from Region/Circle/Division
 </strong>
          </div>
        </td>
      </tr>
    </table>
     
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
           <th>Select</th> 
           <th>Vr. No</th>
          <th>Vr. Date</th>
         
          <th>
            <div>Total</div>
            <div>Amount</div>
          </th>
          <th>Particulars</th>
          <th>Office Ref.No</th>
          <th>Office Ref.Date</th>
          <th>Cheque No</th>
          <th>Cheque Date</th>
          

          </tr>
         <tbody id="tbody" class="table">
          <%
         
           try
           {
           String sql="select m.VOUCHER_NO as vno,to_char(m.DATE_OF_TRANSFER,'DD/MM/YYYY') as rec_date,trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT," +
                      "m.PARTICULARS as remarks,m.OFF_REF_NO as refno,to_char(m.OFF_REF_DATE,'DD/MM/YYYY') as ref_date," +
                      "m.CHEQUE_DD_NO as cheqno,to_char(m.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqdate,m.CHEQUE_OR_DD as cheqtype, " +
                      "bk.BANK_NAME ||'-'|| br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BK_BR_CITY " +
                      "from FAS_FUND_TRF_FROM_OFFICE m,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br " +
                      "where m.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and m.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and " +
                      "m.CASHBOOK_YEAR="+yr+" and m.CASHBOOK_MONTH="+mon+" and m.TRANSFER_STATUS='L' and m.CHEQUE_OR_DD='C' " +
                      "and m.HO_BANK_ID=br.BANK_ID and m.HO_BRANCH_ID=br.BRANCH_ID and m.HO_BANK_ID=bk.BANK_ID " ;
                      
                      System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
             int cnt=0; 
            while(rs2.next())
            {
             cnt++;
                out.println("<tr id='" + cnt + "'>");   
                        
                 out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Select</a></td>");
           
           
                out.println("<td align='left'>"+rs2.getInt("vno")+"</td>");
          
                out.println("<td align='left'>"+rs2.getString("rec_date")+"</td>");
               
               // out.println("<td align='left'>"+rs2.getString("REMITTANCE_TYPE")+"</td>");
               
                
                if (rs2.getString("TOTAL_AMOUNT") != null)
                {
                 out.println("<td align='left'>"+rs2.getString("TOTAL_AMOUNT").trim()+"</td>");
                }
                else
                {
                   out.println("<td align='left'>"+"---"+"</td>");                
                }
                if (rs2.getString("remarks") != null){
                
                out.println("<td align='left'>"+rs2.getString("remarks")+"</td>");
                }
                else{    
                out.println("<td align='left'>"+"---"+"</td>");
                }
                
                 if (rs2.getString("refno")!=null){
                out.println("<td align='left'>"+rs2.getString("refno")+"</td>");
                }
                else
                out.println("<td align='left'>"+"---"+"</td>");
                          
                if(rs2.getString("ref_date")!=null)
                out.println("<td align='left'>"+rs2.getString("ref_date")+"</td>");
                else
                out.println("<td align='left'>"+"   --  "+"</td>");
                 if (rs2.getString("cheqno") != null)
                {
                 out.println("<td align='left'>"+rs2.getString("cheqno")+"</td>");
                }
                else
                {
                   out.println("<td align='left'>"+"---"+"</td>");                
                }
                              
                if (rs2.getString("cheqdate") !=null )
                {
                 out.println("<td align='left'>"+rs2.getString("cheqdate")+"</td>");
                 System.out.println(rs2.getString("cheqdate"));
                }
                else
                {
                 out.println("<td align='left'>"+"---"+"</td></tr>");
                }
                 
//                if(rs2.getString("cheqtype")!=null)
//                out.println("<td align='left'>"+rs2.getString("cheqtype")+"</td>");
//                else
//                out.println("<td align='left'>"+"   --  "+"</td>");
               
          
            }
              if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td></tr>");
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
         %>
        </tbody>
      
        </table>
       
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            <tr>
                <td>
                    <table align="center" cellspacing="3" cellpadding="2" border="1"  width="100%">
                     <tr class="tdH">
                        <td>
                            <table align="center" cellspacing="3" cellpadding="2"  border="0" width="100%">
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
                                                        Page&nbsp;&nbsp;<select name="cmbpage"  id="cmbpage" onchange="changepage()"></select>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div id="divpage"></div>
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
      </form>
  </body>
</html>
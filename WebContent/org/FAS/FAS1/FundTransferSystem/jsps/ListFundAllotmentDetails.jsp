<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>List of Fund Allotment Details Form</title>
    <!--script type="text/javascript" src="../scripts/ListofSubLedgerMainForm_CB.js"></script-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                var  cmbAcc_UnitCode="",cmbOffice_code="",Cashbook_year="",Cashbook_month="",slno="",letgen="";
                var transAmt=0,fundreq=0;
            var LetterDate="",OffLetterDate="",remarks="",tranOffice="", tranOfficeName="",fundtype="",fundtypeid="",LetterNo="",OffLetterNo="",reason="",HOREFDATE="",HOREFNO="";
            
            
                 r=document.getElementById(rowID);
                 rcells=r.cells;
      
                 slno=rcells.item(1).firstChild.nodeValue;
                 voucherno=rcells.item(2).firstChild.nodeValue;
                 tranOffice=rcells.item(3).firstChild.nodeValue;
                 tranOfficeName=rcells.item(4).firstChild.nodeValue;
                 OffLetterNo=rcells.item(5).firstChild.nodeValue;
                 OffLetterDate=rcells.item(6).firstChild.nodeValue
                 LetterNo=rcells.item(7).firstChild.nodeValue;
                 LetterDate=rcells.item(8).firstChild.nodeValue;
                 //LetterDate=rcells.item(6).firstChild.nodeValue;
                 fundtypeid=rcells.item(9).firstChild.nodeValue;
                 fundtype=rcells.item(10).firstChild.nodeValue;
                 
                 
                 fundreq=rcells.item(11).firstChild.nodeValue;
                 transAmt=rcells.item(12).firstChild.nodeValue;
                 reason=rcells.item(13).firstChild.nodeValue;
                 CheqorDD=rcells.item(14).firstChild.nodeValue;
                 CheqNo=rcells.item(15).firstChild.nodeValue;
                 CheqDate=rcells.item(16).firstChild.nodeValue;
                 remarks=rcells.item(17).firstChild.nodeValue;
                 letgen=rcells.item(18).firstChild.nodeValue;
                 HOREFNO=rcells.item(19).firstChild.nodeValue;
                 HOREFDATE=rcells.item(20).firstChild.nodeValue;
        Minimize();
    
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentFundAllotment(slno,voucherno,transAmt,fundreq,LetterDate,OffLetterDate,tranOffice,tranOfficeName,fundtype,fundtypeid,LetterNo,OffLetterNo,reason,CheqorDD,CheqNo,CheqDate,remarks,letgen,HOREFNO,HOREFDATE)
       // return true;
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
  <body  bgcolor="rgb(255,255,225)">
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    ResultSet rs1=null;
    PreparedStatement ps1=null;
     Connection connection=null;
ResultSet rs2=null;
    PreparedStatement ps2=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

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
      System.out.println("...............list all LISTjsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
       
           int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0,SL_TYPE=0,SL_CODE=0;
            int voucherno=0;
            String financeyear="";
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
             System.out.println("cmbAcc_UnitCode..."+cmbAcc_UnitCode);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            System.out.println("cmbOffice_code..."+cmbOffice_code);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
           try{
             Cashbook_year=Integer.parseInt(request.getParameter("CashbookYear"));
            }catch(Exception e){System.out.println("Exception in CashbookYear:"+e);}
            try{
            Cashbook_month=Integer.parseInt(request.getParameter("CashbookMonth"));
            }catch(Exception e){System.out.println("Exception in CashbookMonth:"+e);}
         financeyear= request.getParameter("txtFinanYr");
        System.out.println(cmbAcc_UnitCode);
        System.out.println(cmbOffice_code);
        System.out.println(Cashbook_year);
        System.out.println(Cashbook_month);
         /*   try
            {        
        
        voucherno=Integer.parseInt(request.getParameter("txtVoucherNo"));
        System.out.println("voucher no is :"+voucherno);
              
            }catch(Exception e){System.out.println("Exception in Sl_Type:"+e);}*/
            
   %>
  
  <form name="ChequeBookList" id="ChequeBookList">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
              List of Fund Allotment Details
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
                        <th>
                            Select
                        </th>
                         <th>
                            Sl.No
                        </th>
                         <th>
                            V.No
                        </th>
                        <th>
                          Transferred Office
                        </th>
                        <th >
                          Office Letter No 
                        </th>
                        <th >
                          Office Letter Date 
                        </th>
                        <th >
                          HO Letter No 
                        </th>
                        <th >
                          HO Letter Date 
                        </th>
                        <th>
                          Work Type
                        </th>
                        <th>
                          Amount Requested
                        </th>
                        <th >
                          Amount Alloted
                        </th>
                       <th >
                         Reason for withholding the requested amount
                        </th>
                    
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
         
         String fundtype="";
         int cnt=0,CheqNo=0;
         
         double transAmt=0,fundreq=0;
            String CheqorDD="",letgen="", CheqDate="",LetterDate="",OffLetterDate="",tranOffice="", tranOfficeName="",fundtypeid="",LetterNo="",remarks="",OffLetterNo="",reason="",HOREFNO="",HOREFDATE="";
      
           try
           {
                      ps=con.prepareStatement("select a.SL_NO,a.ACCOUNTING_UNIT_ID,a.OFFICE_ID,a.CASHBOOK_YEAR,a.cashbook_month,a.FUND_TYPE, " + 
                     "a.REF_NO,a.REF_DATE,a.AMOUNT,a.PARTICULARS,a.FUND_REQUESTED,a.voucher_no,a.updated_by_user_id, " + 
                     "a.updated_date,a.CHEQUE_OR_DD,a.CHEQUE_DD_NO, " + 
                     "a.CHEQUE_DD_DATE,a.HO_REF_NO,a.HO_REF_DATE,a.REASON,a.LETTER_GEN,b.office_name,c.REF_NO as HOREFNO,c.REF_DATE as HOREFDATE from FUND_ALLOTMENT_TRANSACTION a,com_mst_offices b,FUND_ALLOTMENT_MASTER c " + 
                     " where a.OFFICE_ID=b.OFFICE_ID and a.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and a.CASHBOOK_YEAR=c.CASHBOOK_YEAR and a.CASHBOOK_MONTH=c.CASHBOOK_MONTH " +
                     " and a.VOUCHER_NO=c.VOUCHER_NO and a.ACCOUNTING_UNIT_ID=? and a.CASHBOOK_YEAR=? and a.cashbook_month=? ");
                     ps.setInt(1,cmbAcc_UnitCode);
                     ps.setInt(2,Cashbook_year);
                     ps.setInt(3,Cashbook_month);
                    // ps.setInt(3,voucherno);
                     
                     rs=ps.executeQuery();
                     
                    
            while(rs.next())
            {
              cnt++; 
              
             voucherno=rs.getInt("voucher_no");
             System.out.println(voucherno);
              
               if(rs.getString("PARTICULARS")==null)
                      remarks="--";
                      else{
                      remarks=rs.getString("PARTICULARS");
                      System.out.println("listremarks"+remarks);
                      }
          
                 try
                    {
                    
                    if(rs.getDate("HOREFDATE")==null)
                          HOREFDATE="";
                    else
                    {
                    java.sql.Date dd=rs.getDate("HOREFDATE");
                        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                         HOREFDATE=sdf.format(dd);
                        System.out.println("HOREFDATE is"+HOREFDATE);
                    }
                
                 if(HOREFNO.equals(null)) {
                              HOREFNO="-";
                          }
                          else
                          {
                          HOREFNO=rs.getString("HOREFNO");
                          System.out.println("HOREFNO"+HOREFNO);
                          }
                          
                    if(rs.getDate("REF_DATE")==null)
                          OffLetterDate="";
                    else
                    {
                    java.sql.Date dd=rs.getDate("REF_DATE");
                        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                         OffLetterDate=sdf.format(dd);
                        System.out.println("date1 is"+OffLetterDate);
                    }
                if(rs.getDate("HO_REF_DATE")==null)
                          LetterDate="";
                    else
                    {
                    java.sql.Date dd=rs.getDate("HO_REF_DATE");
                        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                         LetterDate=sdf.format(dd);
                        System.out.println("date1 is"+LetterDate);
                    }
                  if(rs.getDate("CHEQUE_DD_DATE")==null)
                          CheqDate="";
                    else
                    {
                    java.sql.Date dd=rs.getDate("CHEQUE_DD_DATE");
                        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                         CheqDate=sdf.format(dd);
                        System.out.println("cheqdate2 is"+CheqDate);
                    }  
                    
                 fundtype=rs.getString("FUND_TYPE");
                          if (fundtype.equalsIgnoreCase("C")){
                              fundtype="Civil";
                          }
                          else{
                              fundtype="Work";
                          }
                          
                          System.out.println("the letter number is"+LetterNo);
                          if(LetterNo.equals(null)) {
                              LetterNo="-";
                          }
                          else
                          {
                          LetterNo=rs.getString("HO_REF_NO");
                          System.out.println("LetterNo"+LetterNo);
                          }
                          
                          
                          System.out.println("the letter number is"+OffLetterNo);
                          if(OffLetterNo.equals(null)) {
                              OffLetterNo="-";
                          }
                          else
                          {
                          OffLetterNo=rs.getString("REF_NO");
                          System.out.println("OffLetterNo"+OffLetterNo);
                          }
                          
                        
                      if(reason.equals(null)) {
                              reason=" ";
                          }
                          else{
                          reason=rs.getString("REASON"); 
                          System.out.println("reason"+reason);
                          }
                      if(CheqorDD.equals(null)) {
                              CheqorDD=" ";
                          }
                          else{
                          CheqorDD=rs.getString("CHEQUE_OR_DD");  
                           System.out.println("CheqorDD"+CheqorDD);
                          }
                          CheqNo=rs.getInt("CHEQUE_DD_NO");
                        tranOffice=rs.getString("OFFICE_ID");
                        System.out.println("transofficeid"+tranOffice);
                        tranOfficeName=rs.getString("office_name");
                        transAmt=rs.getDouble("AMOUNT");
                        fundreq=rs.getDouble("FUND_REQUESTED");
                        if(fundtypeid.equals(null)){
                        fundtypeid="";
                        }
                        else{
                        fundtypeid=rs.getString("FUND_TYPE");
                        System.out.println("fundtypeid"+fundtypeid);
                        }
                      
                   if(letgen.equals(null)){
                        letgen="";
                        }
                        else{
                        letgen=rs.getString("LETTER_GEN");
                        System.out.println("letgen"+letgen);
                        }       
           
                   
                    }    
                    catch(Exception e) {
                        System.out.println("Error in getting date values"+e);
                        
                    }      
              
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                out.println("<td>"+rs.getInt("SL_NO")+"</td>");
                out.println("<td>"+voucherno+"</td>");
                out.println("<td style='display:none'>"+tranOffice+"</td>");
                out.println("<td>"+tranOfficeName+"</td>");
                out.println("<td>"+OffLetterNo+"</td>");
                out.println("<td>"+OffLetterDate+"</td>");
                out.println("<td>"+LetterNo+"</td>");
                out.println("<td>"+LetterDate+"</td>");
                out.println("<td style='display:none'>"+fundtypeid+"</td>");
                out.println("<td>"+fundtype+"</td>");
                
                
                //out.println("<td style='display:none'>"+rs.getInt("SUB_LEDGER_CODE")+"</td>");
                out.println("<td>"+fundreq+"</td>");
               
                out.println("<td>"+transAmt+"</td>");
                out.println("<td>"+reason+"</td>");
                out.println("<td style='display:none'>"+CheqorDD+"</td>");
                out.println("<td style='display:none'>"+CheqNo+"</td>");
                out.println("<td style='display:none'>"+CheqDate+"</td>");
                out.println("<td style='display:none'>"+remarks+"</td>");
                out.println("<td style='display:none'>"+letgen+"</td>");
                out.println("<td style='display:none'>"+HOREFNO+"</td>");
                out.println("<td style='display:none'>"+HOREFDATE+"</td>");
                
            }
            
            
            
                 rs.close();
                 
             if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td><td></td><td></td><td></td></tr>");
             
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
          %>
          
          </tbody>
       </table>
        <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="/org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" > -->
<title>Payment/Receipt Detailssss</title>
 <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
</head>
<body>
  <%
  Connection con=null;
 // ResultSet rs=null,rs2=null;
  PreparedStatement ps4=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,recNo=0;

  try{
   cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
  }catch(Exception e){System.out.println("Exception in getting req:"+e);}
//  try{
//  cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
//  }catch(Exception e){System.out.println("Exception in getting req:"+e);}
  try{
       yr=Integer.parseInt(request.getParameter("yr"));
     }catch(Exception e){System.out.println("Exception in getting req:"+e);}
  try{
       mon=Integer.parseInt(request.getParameter("mon"));
  }catch(Exception e){System.out.println("Exception in getting req:"+e);}
  try{
       recNo=Integer.parseInt(request.getParameter("recNo"));
  }catch(Exception e){System.out.println("Exception in getting req:"+e);}
  
  String type=request.getParameter("type");
  System.out.println("type::::::;"+type);
  String sql="";
  String head="";
  if(type.equalsIgnoreCase("P")){
	  sql="select VOUCHER_NO,to_char(PAYMENT_DATE,'DD/MM/YYYY') as rec_date," +
	  "trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,CREATED_BY_MODULE,PAID_TO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  " +
	  " CASHBOOK_YEAR="+yr+" and CASHBOOK_MONTH="+mon+" and VOUCHER_NO="+recNo+"";
	  
	  head="Payment";
  }
  else if(type.equalsIgnoreCase("R"))
  {
  sql="select RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date," +
  "trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,CREATED_BY_MODULE,RECEIVED_FROM as PAID_TO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  " +
  " CASHBOOK_YEAR="+yr+" and CASHBOOK_MONTH="+mon+" and RECEIPT_NO="+recNo+"";
  
  head="Receipt";
  }
  else if(type.equalsIgnoreCase("J"))
  {
  sql="SELECT VOUCHER_NO, "+
   " TO_CHAR(VOUCHER_DATE,'DD/MM/YYYY')                    AS rec_date, "+
 " CREATED_BY_MODULE "+
 " FROM FAS_JOURNAL_MASTER WHERE ACCOUNTING_UNIT_ID          ="+cmbAcc_UnitCode+" AND CASHBOOK_YEAR                 ="+yr+" AND CASHBOOK_MONTH                ="+mon+" AND VOUCHER_NO                    ="+recNo;
  
  head="Journal";
  }
  
  
  String paydate="",module="",amt="",paidTo="";
  if(type.equalsIgnoreCase("J"))
  {
      ps4=con.prepareStatement(sql);
      results=ps4.executeQuery();
      if(results.next())
      {
              paydate= results.getString("rec_date"); 
              module=results.getString("CREATED_BY_MODULE"); 
             
      }
  }
  else{
      ps4=con.prepareStatement(sql);
      results=ps4.executeQuery();
      if(results.next())
      {
              paydate= results.getString("rec_date"); 
              module=results.getString("CREATED_BY_MODULE"); 
              amt=results.getString("TOTAL_AMOUNT"); 
              paidTo=results.getString("PAID_TO"); 
      }
  }
 
/* sql="select SL_NO, ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,"+
 "SUB_LEDGER_TYPE_CODE ,(select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=SUB_LEDGER_TYPE_CODE and SL_CODE=SUB_LEDGER_CODE) as SLNAME  ,AGREEMENT_NO,PAID_TO, AMOUNT,CHEQUE_DD_NO ,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as CHE_DD_DATE "+
 ",BANK_ID,BRANCH_ID from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+
 cmbOffice_code+"and CASHBOOK_YEAR="+yr+"and CASHBOOK_MONTH="+mon+"and  VOUCHER_NO="+recNo;
 */
 
 
 
 
  %>
 <%--   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%> --%>
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong> <%=head%> Details</strong>
          </div>
        </td>
      </tr>
    </table>
  
  <table align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
              <tr class="tdH">
          <th>Voucher No</th>
          <th>Date</th>
          <th>Module</th>
          <th>Total Amount</th>
          <th>Paid To/Received From</th>
         
        </tr>
         <tbody id="tbody1" class="table">
  <tr  ><td><%=recNo%></td><td><%=paydate%></td><td><%=module%></td><td><%=amt%></td><td><%=paidTo%></td>
  </tr>
  </tbody>
  </table>
  
  
   <table cellspacing="3" cellpadding="2"  width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Voucher Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th> Serial Number</th>
          <th>Account Head Code</th>
          <th>Credit/Debit</th>
          <th>Sub-Ledger Type</th>
          <th>Sub-Ledger Code</th>
       
          <th>Cheque Number</th>
          <th>Cheque Date</th>
           <th>Amount</th>
          <th>Bank Name</th>
          <!--<th>Paid To</th>-->
         
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs=null, rs2=null,rs3=null;
          PreparedStatement ps=null, ps2=null,ps3=null;
           try
           {
        	   if(type.equalsIgnoreCase("P")){
        	   sql="select SL_NO, ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,"+
        	   "SUB_LEDGER_TYPE_CODE ,(select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=SUB_LEDGER_TYPE_CODE and SL_CODE=SUB_LEDGER_CODE) as SLNAME  , AMOUNT,CHEQUE_DD_NO ,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as CHE_DD_DATE "+
        	   ",BANK_ID,BRANCH_ID from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+yr+"and CASHBOOK_MONTH="+mon+"and  VOUCHER_NO="+recNo;
        	   }
        	   else if(type.equalsIgnoreCase("R"))
        	   {
        		   sql="select SL_NO, ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,"+
            	   "SUB_LEDGER_TYPE_CODE ,(select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=SUB_LEDGER_TYPE_CODE and SL_CODE=SUB_LEDGER_CODE) as SLNAME  , AMOUNT,CHEQUE_DD_NO ,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as CHE_DD_DATE "+
            	   ",BANK_NAME from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+"and CASHBOOK_YEAR="+yr+"and CASHBOOK_MONTH="+mon+"and  RECEIPT_NO="+recNo;  
        	   } 
                   else if(type.equalsIgnoreCase("J"))
                  {
                  System.out.println("cmbAcc_UnitCode::"+cmbAcc_UnitCode+"::::mon:::"+mon+"::::recNo:::"+recNo);
                  sql="SELECT SL_NO,ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE , "+
                    " (SELECT SL_CODENAME "+
                    "   FROM SL_TYPE_CODE_NAME_VIEW "+
                    "   WHERE SL_TYPE=SUB_LEDGER_TYPE_CODE  "+
                    "   AND SL_CODE  =SUB_LEDGER_CODE  "+
                     "  ) AS SLNAME , "+
                   "    AMOUNT,CHEQUE_DD_NO ,TO_CHAR(CHEQUE_DD_DATE,'DD/MM/YYYY') AS CHE_DD_DATE "+
                   "  FROM FAS_JOURNAL_TRANSACTION WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND CASHBOOK_YEAR ="+yr+" AND CASHBOOK_MONTH ="+mon+" AND VOUCHER_NO ="+recNo;
                 
                  }
           // System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
             //  System.out.println("whileeeeeeee");
                out.println("<tr>");   
                out.println("<td align='left'>"+rs2.getInt("SL_NO")+"</td>");
                ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
                rs3=ps3.executeQuery();
                if(rs3.next())
                out.println("<td align='left'>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"-"+rs3.getString("ACCOUNT_HEAD_DESC")+"</td>");
                
                out.println("<td align='left'>"+rs2.getString("CR_DR_INDICATOR")+"</td>");
                   
                   if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
                    {
                    System.out.println("take SL DESC");
                    ps=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                    ps.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
                    rs=ps.executeQuery();
                    if(rs.next())
                    out.println("<td align='left'>"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</td>");
                    }
                    else
                     out.println("<td align='left'>"+"   --  "+"</td>");
                 
                
                    if(rs2.getString("SLNAME")!=null)
                        out.println("<td align='left'>"+rs2.getString("SLNAME")+"</td>");
                    else
                        out.println("<td align='left'>"+"--"+"</td>");
                   
                
               
                
                if (rs2.getString("CHEQUE_DD_NO") != null)
                {
                 out.println("<td align='left'>"+rs2.getString("CHEQUE_DD_NO")+"</td>");
                }
                else
                {
                   out.println("<td align='left'>"+"--"+"</td>");                
                }
               
               
                if (rs2.getString("CHE_DD_DATE") !=null )
                {
                 out.println("<td align='left'>"+rs2.getString("CHE_DD_DATE")+"</td>");
                }
                else
                {
                 out.println("<td align='left'>"+"--"+"</td>");
                }
                
                out.println("<td align='left'>"+rs2.getString("AMOUNT").trim()+"</td>");
                rs3.close();
                ps3.close();
                if(type.equalsIgnoreCase("P")){
                ps3=con.prepareStatement("select BANK_NAME from FAS_MST_BANKS where BANK_ID=?");
                ps3.setInt(1,rs2.getInt("BANK_ID"));
                rs3=ps3.executeQuery();
                if(rs3.next())
                out.println("<td align='left'>"+rs3.getString("BANK_NAME")+"</td>");
                }
                else if(type.equalsIgnoreCase("R"))
                {
                	out.println("<td align='left'>"+rs2.getString("BANK_NAME")+"</td></tr>");
                }
                else
                {
                        out.println("<td align='left'>"+"--"+"</td></tr>");
                }
                
                
               // if(rs2.getString("PAID_TO")!=null)
               // out.println("<td align='left'>"+rs2.getString("PAID_TO")+"</td>");
               // else
               // out.println("<td align='left'>"+"   --  "+"</td>");
                
                
            }
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
                     onclick="javascript:window.close()"></input>
            </div>
          </td>
        </tr>
      </table>
  
  
  
</body>
</html>
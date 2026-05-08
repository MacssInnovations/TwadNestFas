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
    <title>Cheque Memo SubList</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
  <body class="table"><form name="frmpassOrder_sublist" method="POST">
      <%
  Connection con=null;
  ResultSet rs=null;
  PreparedStatement ps=null;
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
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,checkno=0;
  System.out.println("jsp :");
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
                 yr=Integer.parseInt(request.getParameter("yr"));
               }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
                 mon=Integer.parseInt(request.getParameter("mon"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            	checkno=Integer.parseInt(request.getParameter("checkno"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
           
            
  %>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
      <table cellspacing="3" cellpadding="2"  width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Cheque Memo Sub List Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th> Serial Number</th>
            <th>Bill No</th>
          <th>Bill Date</th>
          <th>Total Voucher Amount</th>
          <th>A/C Head Code</th>
            <th>Sub Ledger Type</th>
          <th>Sub Ledger Code</th>
           <th>CR/DR</th>
            <th>DR Amount</th>
             <th>Cheque No</th>
              <th>Cheque Date</th>
          <th>Remarks</th>
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null,rs4=null;
          PreparedStatement ps2=null,ps3=null,ps4=null;
           try
           {
           
          String sql="select sl_no,"+
        	  "  bill_no,"+
        	  " to_char(bill_date,'dd/mm/yyyy')bill_date,"+
        	  " PASS_ORDER_AMOUNT,"+
        	  " voucher_no,"+
        	 "  to_char(voucher_date,'dd/mm/yyyy')voucher_date,"+
        	 "  account_head_code,"+
        	  " (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS b where b.ACCOUNT_HEAD_CODE=t.account_head_code ) as headDesc,"+
        	 "  sub_ledger_type_code,"+
        	 " (select sub_ledger_type_desc from com_mst_sl_types a where a.sub_ledger_type_code=t.sub_ledger_type_code)as typedesc,"+
        	 " sub_ledger_code,"+
        	 " (select v.sl_codename from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=sub_ledger_type_code and SL_CODE=t.sub_ledger_code)as paydesc,"+
        	 " amount,"+
        	 " particulars,"+
        	"  CR_DR_INDICATOR from FAS_CHEQUE_MEMO_TRN t "+ 
        	" WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
        	" AND CASHBOOK_YEAR       = "+yr+
        	" AND CASHBOOK_MONTH      = "+mon+
        	" AND CHEQUE_MEMO_NO             ="+checkno +" order by bill_no";
            System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
               //System.out.println("while");
                out.println("<tr>");   
                out.println("<td align='left'>"+rs2.getInt("sl_no")+"</td>");
                out.println("<td align='left'>"+rs2.getString("bill_no")+"</td>");
                out.println("<td align='left'>"+rs2.getString("bill_date")+"</td>");
                out.println("<td align='left'>"+rs2.getString("PASS_ORDER_AMOUNT")+"</td>");
                out.println("<td align='left'>"+rs2.getString("account_head_code")+"-"+rs2.getString("headDesc")+"</td>");
                out.println("<td align='left'>"+rs2.getString("typedesc")+"</td>");
               out.println("<td align='left'>"+rs2.getString("paydesc")+"</td>");
               
               out.println("<td align='left'>"+rs2.getString("CR_DR_INDICATOR")+"</td>");
               out.println("<td align='right'>"+rs2.getString("amount")+"</td>");
               out.println("<td align='left'>"+rs2.getString("voucher_no")+"</td>");
               out.println("<td align='left'>"+rs2.getString("voucher_date")+"</td>");
               
               
               out.println("<td align='left'>"+rs2.getString("particulars").trim()+"</td></tr>");  
              
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
                     onclick="exit()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
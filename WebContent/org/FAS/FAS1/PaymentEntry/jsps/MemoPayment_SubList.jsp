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
    <title>Memo Payment SubList</title>
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
  <body class="table"><form name="frmMemoPayment_sublist" method="POST">
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
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,bilno=0;
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
            	bilno=Integer.parseInt(request.getParameter("bilno"));
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
              <strong>Memo Payment Sub List Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th> Serial Number</th>
          <th>A/C head Code</th>
          <th>Sub Ledger Type</th>
          <th>Sub Ledger Code</th>
          <th>Invoice No</th>
          <th>Invoice Date</th>
          <th>Invoice Amount</th>
          <th>Amount</th>
          <th>First Party</th>
           <th>Remarks</th>
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null,rs4=null;
          PreparedStatement ps2=null,ps3=null,ps4=null;
           try
           {
           
          String sql="select sl_no,"+
        	  "account_head_code,"+
        	 " (select h.ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS h where h.ACCOUNT_HEAD_CODE=t.account_head_code)headDesc,"+
        	 " cr_dr_indicator,"+
        	 " INVOICE_NO,"+
        	 " INVOICE_DATE,"+
        	  "INVOICE_AMOUNT,"+
        	 " agreement_no,"+
        	 " agreement_date,"+
        	 " agreement_amount,"+
        	 " sub_ledger_type_code,  CASE     WHEN T.PAYEE_TYPE_CODE=1     THEN 'Drawing Office'     ELSE (select sub_ledger_type_desc from com_mst_sl_types a where a.sub_ledger_type_code=DECODE(T.PAYEE_TYPE_CODE::numeric,9,1,T.PAYEE_TYPE_CODE::numeric)) end as typedesc,"+
        	  "SUB_LEDGER_CODE,"+
        	  "amount,"+
        	 " FIRST_PARTY,"+
        	 " PARTICULARS,"+
        	  "jvr_no,"+
        	 " JVR_DATE,"+
        	 " pvr_no,"+
        	 " pvr_date,"+
        	"  (select v.sl_codename from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=DECODE(T.PAYEE_TYPE_CODE::numeric,1,7,9,1,T.PAYEE_TYPE_CODE::numeric) and SL_CODE=t.PAYEE_CODE)as paydesc"+
        	  	
        	"  from FAS_MEMO_OF_PAYMENT_TRN t"+
        	" WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
        	" AND CASHBOOK_YEAR       = "+yr+
        	" AND CASHBOOK_MONTH      = "+mon+
        	" AND BILL_NO             ="+bilno;
            System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
               //System.out.println("while");
                out.println("<tr>");   
                out.println("<td align='left'>"+rs2.getInt("sl_no")+"</td>");
                out.println("<td align='left'>"+rs2.getString("account_head_code")+"-"+rs2.getString("headDesc")+"</td>");
                out.println("<td align='left'>"+rs2.getString("typedesc")+"</td>");
                out.println("<td align='left'>"+rs2.getString("paydesc")+"</td>");
                
                out.println("<td align='left'>"+rs2.getString("INVOICE_NO")+"</td>");
                
                String invodate="-";
                if((rs2.getString("INVOICE_DATE")=="0")||(rs2.getString("INVOICE_DATE")==null)){
                	invodate="-";
                }else{
                	invodate=rs2.getString("INVOICE_DATE");
                }
                out.println("<td align='left'>"+invodate+"</td>");
                
              //  out.println("<td align='left'>"+rs2.getString("INVOICE_DATE")+"</td>");
               out.println("<td align='right'>"+rs2.getString("INVOICE_AMOUNT")+"</td>");
               out.println("<td align='right'>"+rs2.getString("amount")+"</td>");
               out.println("<td align='left'>"+rs2.getString("FIRST_PARTY")+"</td>");
               
               String part="-";
               System.out.println(" well --- "+rs2.getString("PARTICULARS").trim());
               if((rs2.getString("PARTICULARS").trim()==" ")||(rs2.getString("PARTICULARS").trim()=="")||(rs2.getString("PARTICULARS").trim().equalsIgnoreCase(null))||(rs2.getString("PARTICULARS").trim().equalsIgnoreCase("null"))){
            	   part="-";
               }else{
            	   part=rs2.getString("PARTICULARS").trim();
               }
               
               
               out.println("<td align='left'>"+part+"</td></tr>");  
              
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
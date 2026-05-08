<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<%@page import="java.math.BigDecimal"%><html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Imprest/Temporary Advance Payment System</title>
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
  <body class="table"><form name="frmBankPay_FinalBill" method="POST">
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
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
  		  int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,recNo=0;

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
                 recNo=Integer.parseInt(request.getParameter("recNo"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
            
  %>
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
          <th>Agreement Number</th>
          <th>Cheque Number</th>
          <th>Cheque Date</th>
          <th>Bank Name</th>
          <!--<th>Paid To</th>-->
          <th>Amount</th>
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null;
          PreparedStatement ps2=null,ps3=null;
          try
          {
           
           
           //changed on 01_jul_17 for desc from scheme master table
 	         /*String sql="select SL_NO, ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,"+
			            "SUB_LEDGER_TYPE_CODE ,(select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=SUB_LEDGER_TYPE_CODE and SL_CODE=SUB_LEDGER_CODE) as SLNAME  ,AGREEMENT_NO,PAID_TO, AMOUNT,CHEQUE_DD_NO ,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as CHE_DD_DATE "+
			            ",BANK_ID,BRANCH_ID from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+
            cmbOffice_code+"and CASHBOOK_YEAR="+yr+"and CASHBOOK_MONTH="+mon+"and  VOUCHER_NO="+recNo;*/
            
             String sql="select SL_NO, ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,"+
			            "SUB_LEDGER_TYPE_CODE ,(select SL_CODENAME from SL_TYPE_CODE_NAME_VIEW_M where SL_TYPE=SUB_LEDGER_TYPE_CODE and SL_CODE=SUB_LEDGER_CODE) as SLNAME  ,AGREEMENT_NO,PAID_TO, AMOUNT,CHEQUE_DD_NO ,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as CHE_DD_DATE "+
			            ",BANK_ID,BRANCH_ID from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+
            cmbOffice_code+"and CASHBOOK_YEAR="+yr+"and CASHBOOK_MONTH="+mon+"and  VOUCHER_NO="+recNo;
            
            
           // System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
                System.out.println("while");
                out.println("<tr>");   
                out.println("<td align='right'>"+rs2.getInt("SL_NO")+"</td>");
                ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
                rs3=ps3.executeQuery();
                if(rs3.next())
                out.println("<td align='left'>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"-"+rs3.getString("ACCOUNT_HEAD_DESC")+"</td>");
                
                out.println("<td align='center'>"+rs2.getString("CR_DR_INDICATOR")+"</td>");
                   
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
                   
                
                if(rs2.getString("AGREEMENT_NO")!=null)
               		 out.println("<td align='left'>"+rs2.getString("AGREEMENT_NO")+"</td>");
                else
                	 out.println("<td align='left'>"+"   --  "+"</td>");
                
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
                 	 out.println("<td align='center'>"+rs2.getString("CHE_DD_DATE")+"</td>");
                }
                else
                {
                 	 out.println("<td align='left'>"+"--"+"</td>");
                }
                
                
                rs3.close();
                ps3.close();
                ps3=con.prepareStatement("select BANK_NAME from FAS_MST_BANKS where BANK_ID=?");
                ps3.setInt(1,rs2.getInt("BANK_ID"));
                rs3=ps3.executeQuery();
                if(rs3.next())
                out.println("<td align='left'>"+rs3.getString("BANK_NAME")+"</td>");
      
                
                out.println("<td align='right'>"+new BigDecimal(Double.parseDouble(rs2.getString("AMOUNT"))).setScale(2,BigDecimal.ROUND_CEILING)+"</td></tr>");
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
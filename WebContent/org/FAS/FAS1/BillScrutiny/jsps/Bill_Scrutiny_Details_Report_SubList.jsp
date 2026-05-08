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
    <title>Bill Scrutiny SubList</title>
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
  <body class="table"><form name="frmBill_scrutiny_sublist" method="POST">
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,bilno=0,sancno=0;
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
            try{
            	sancno=Integer.parseInt(request.getParameter("sancno"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            System.out.println("sancno:::"+sancno);
            String sub_q = "",sub_main="";
			if( ((yr==2014) && (mon>3) ) || (yr>2014)) 
				{
					sub_q = " Fas_Bill_Register_Transactionw "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";
				
				}
			else{
				sub_q = " Fas_Bill_Register_Transaction "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}
			System.out.println("Table from**"+sub_q);
            
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
              <strong>Bill Scrutiny Sub List Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th> Serial Number</th>
          <th>Account Head Code</th>
          <th>Amount</th>
          <th>Particulars</th>
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null,rs4=null;
          PreparedStatement ps2=null,ps3=null,ps4=null;
           try
           {
           
          String sql="SELECT t.ACCOUNT_HEAD_CODE, "+
        	  " t.BILL_MAJOR_TYPE,t.sl_no, case when t.PARTICULARS is null then '-' else t.PARTICULARS end as PARTICULARS,t.AMOUNT,"+
        	//  " (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES m WHERE status='L' and m.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE)as majordesc, "+
        	 // " t.BILL_MINOR_TYPE_CODE, "+
        	 // " (select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST n where n.BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE "+
        	 // " and n.bill_minor_type_code=t.BILL_MINOR_TYPE_CODE and status='L')as minordesc, "+
        	// "  t.BILL_SUB_TYPE_CODE, "+
        	 "  (select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES o "+
        	 "  where BILL_MAJOR_TYPE_CODE=t.BILL_MAJOR_TYPE and BILL_MINOR_TYPE_CODE=t.BILL_MINOR_TYPE_CODE "+ 
        	 "  and t.BILL_SUB_TYPE_CODE=o.bill_sub_type_code and status='L')as subdesc, "+
        	 "  BUDGET_PROVISION, "+
        	 "  BUDGET_SO_FAR_SPENT,h.ACCOUNT_HEAD_DESC, "+
        	 "  DEDUCTED_AMOUNT "+
        	" FROM "+sub_q+" t,COM_MST_ACCOUNT_HEADS h "+
        	" WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
        	" AND CASHBOOK_YEAR       = "+yr+
        	" AND CASHBOOK_MONTH      = "+mon+
        	" AND BILL_NO             ="+bilno+
        	" and t.ACCOUNT_HEAD_CODE=h.ACCOUNT_HEAD_CODE";
            System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
               //System.out.println("while");
                out.println("<tr>");   
                out.println("<td align='left'>"+rs2.getInt("SL_NO")+"</td>");
           /*      ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
                rs3=ps3.executeQuery();
                if(rs3.next())
                {
                out.println("<td align='left'>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"-"+rs3.getString("ACCOUNT_HEAD_DESC")+"</td>");
                } */
                out.println("<td align='left'>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"-"+rs2.getString("ACCOUNT_HEAD_DESC")+"</td>");
               out.println("<td align='right'>"+rs2.getString("AMOUNT")+"</td>");
               out.println("<td align='left'>"+rs2.getString("PARTICULARS").trim()+"</td></tr>");    
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
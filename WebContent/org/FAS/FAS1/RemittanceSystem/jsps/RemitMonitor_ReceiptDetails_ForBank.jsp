<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.sql.Date,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Remittance System</title>
    <script type="text/javascript"    src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"         media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
  <body class="table"><form name="RemitMonitor_ReceiptDetails" method="POST">
     <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
      <%
  Connection con=null;
  ResultSet rs=null;
  PreparedStatement ps=null;
  
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
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,challanNo=0;
  Date challanDate=null;
  Calendar c;

            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
                 challanNo=Integer.parseInt(request.getParameter("challanNo"));
               }catch(Exception e){System.out.println("Exception in getting req challanNo:"+e);}
          try
          {
             String[] sd=request.getParameter("challanDate").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
             java.util.Date d=c.getTime();
             challanDate=new Date(d.getTime());
               }
             catch(Exception e){System.out.println("Exception in getting req challan date:"+e);}
             System.out.println("challanDate.. "+challanDate);
            
            
  %>
      <table cellspacing="2" cellpadding="3" width="100%">
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
          <th>Bank Receipt No</th>
          <th>Bank Receipt Date</th>
          <th>From whom Received</th>
          <th >Cheque No.</th>
          <th >Cheque Date</th>
          <th>Amount</th>
        </tr>
        <tbody id="tbody" class="table">
          <%
       
           try 
            {
                    String sqlretrive= "select mas.ACCOUNT_HEAD_CODE,mas.RECEIPT_NO,to_char(mas.RECEIPT_DATE,'DD/MM/YYYY') as rec_date,"+
                   " mas.RECEIVED_FROM,mas.TOTAL_AMOUNT,trs.CHEQUE_DD_NO,to_char(trs.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqDD_date,trim(to_char(trs.AMOUNT ,'99999999999999.99'))  as amt from "+
                   "FAS_RECEIPT_MASTER mas,FAS_RECEIPT_TRANSACTION trs where mas.ACCOUNTING_UNIT_ID=trs.ACCOUNTING_UNIT_ID and "+
                   "mas.ACCOUNTING_FOR_OFFICE_ID=trs.ACCOUNTING_FOR_OFFICE_ID and mas.CASHBOOK_YEAR=trs.CASHBOOK_YEAR and  "+
                   "mas.CASHBOOK_MONTH=trs.CASHBOOK_MONTH and  mas.RECEIPT_NO=trs.RECEIPT_NO and  mas.RECEIPT_TYPE='B' and mas.CREATED_BY_MODULE='BR' "+
                   " and mas.ACCOUNTING_UNIT_ID=? and  mas.ACCOUNTING_FOR_OFFICE_ID=?  and  mas.CHALLAN_NO=? and mas.CHALLAN_DATE=?";
                    //ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date,RECEIVED_FROM,TOTAL_AMOUNT from FAS_RECEIPT_MASTER where RECEIPT_TYPE='C' and REMITTANCE_STATUS='Y' " +
                      //                      "and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=?  and  CHALLAN_NO=? and CHALLAN_DATE=?");
                    ps=con.prepareStatement(sqlretrive);
                    ps.setInt(1,cmbAcc_UnitCode);
                    ps.setInt(2,cmbOffice_code);
                    ps.setInt(3,challanNo);
                    ps.setDate(4,challanDate);
                    rs=ps.executeQuery();
                    int cnt=0;
                    double amt=0.0;
                 
                    while(rs.next()) 
                    {
                        cnt++;
                      out.println("<tr>");   
                      out.println("<td align='left'>"+cnt+"</td>");
                      out.println("<td align='left'>"+rs.getInt("RECEIPT_NO")+"</td>");
                      out.println("<td align='left'>"+rs.getString("rec_date")+"</td>");
                      out.println("<td align='left'>"+rs.getString("RECEIVED_FROM")+"</td>");
                      
                      if (rs.getString("CHEQUE_DD_NO")==null)
                      {
                        out.println("<td align='left'>"+"--"+"</td>");
                      }
                      else
                      {
                        out.println("<td align='left'>"+rs.getString("CHEQUE_DD_NO")+"</td>");
                      }
                      if (rs.getString("cheqDD_date")==null)
                      {                      
                       out.println("<td align='left'>"+"--"+"</td>");
                      }
                      else
                      {
                       out.println("<td align='left'>"+rs.getString("cheqDD_date")+"</td>");
                      }
                      
                      out.println("<td align='left'>"+rs.getString("amt")+"</td></tr>");
                      
                        
                    } 
                    if(cnt==0)
                   out.println("<tr><td align='left'>"+"No data found"+"</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
                    System.out.println("count"+cnt);
                 }
            catch(Exception e)
            {
            System.out.println("catch..HERE.in failure to retrieve."+e);
              
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
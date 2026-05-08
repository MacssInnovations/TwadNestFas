<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>  
    <meta http-equiv="cache-control" content="no-cache">
    <title>Self-Cheque System</title>
    <script language="javascript" type="text/javascript" src="../scripts/SelfCheque_ListAll.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table"><form name="frmVoucher_list_SL" method="POST">
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
  String cheq_no="",drawled_date=null;

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
                 cheq_no=request.getParameter("cheq_no");
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
            recNo=Integer.parseInt(request.getParameter("recNo"));
            System.out.println("while");
            System.out.println(recNo);
  %>
      <table cellspacing="3" cellpadding="2" width="100%">
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
          <th> Sl. No</th>
          <th>Vr. Number</th>
          <th>Amount</th>
          <th>No.of Acq. Rolls</th>
          <th>Remarks</th>
          <th>Show Vr. Details </th>
          <th>Print Vr ?</th>
          <th>Show Acq. Details</th>
          
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null;
          PreparedStatement ps2=null,ps3=null;
           try
           {
          
          String sql="select VOUCHER_NO, (to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT  ,NO_OF_ACQ_ROLLS ,REMARKS "+
            " from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? "+
            " and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and  SELFCHEQUE_RECEIPT_NO=? order by VOUCHER_NO";
            
            ps2=con.prepareStatement(sql);
            ps2.setInt(1,cmbAcc_UnitCode);
            ps2.setInt(2,cmbOffice_code);
            ps2.setInt(3,yr);
            ps2.setInt(4,mon);
            ps2.setInt(5,recNo);
            //ps2.setString(6,drawled_date);
            
            rs2=ps2.executeQuery();
            
            int serial=1;
            while(rs2.next())
            {
               System.out.println("while");
                out.println("<tr>");   
                out.println("<td align='left'>"+serial+"</td>");
                out.println("<td align='left'>"+rs2.getInt("VOUCHER_NO")+"</td>");
                out.println("<td align='left'>"+rs2.getString("TOTAL_AMOUNT")+"</td>");
                out.println("<td align='left'>"+rs2.getInt("NO_OF_ACQ_ROLLS")+"</td>");
                if(rs2.getString("REMARKS")==null)
                    out.println("<td align='left'>--</td>");
                else
                    out.println("<td align='left'>"+rs2.getString("REMARKS")+"</td>");
                out.println("<td><a href=\"javascript:loadVoucher_Details(" + cmbAcc_UnitCode + ","+cmbOffice_code+","+yr+","+mon+","+rs2.getInt("VOUCHER_NO")+")\">Vr. Deatils</a></td>");
                
                out.println("<td><a href=\"javascript:printingPayment(" + cmbAcc_UnitCode + ","+cmbOffice_code+","+yr+","+mon+","+rs2.getInt("VOUCHER_NO")+")\">PRINT</a></td>");
                
                out.println("<td><a href=\"javascript:loadAcq_Details(" + cmbAcc_UnitCode + ","+cmbOffice_code+","+yr+","+mon+","+rs2.getInt("VOUCHER_NO")+","+rs2.getInt("NO_OF_ACQ_ROLLS")+")\">Acq. Deatils</a></td>");
                serial++;
            }
            rs2.close();
            ps2.close();
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
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <title>Bank Payment System</title>
    
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
  <body class="table"><form name="frmBankPay_NilPayment_ListAll_SL" method="POST">
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
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,recNo=0,yr=0,mon=0;
  Calendar c;

            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            	String[] sd=request.getParameter("yr").split("/");
                c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            	 yr=Integer.parseInt(sd[2]);System.out.println("year"+yr);
            	 mon=Integer.parseInt(sd[1]);System.out.println("month"+mon);
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
          <th>Office Name</th>
          <th>Amount Transfered</th>
          <th><div> Cheque/DD No. </div><div> (C-cheq,D-DD,E-ECS)</div></th>
          <th>Cheque/DD/ECS Date</th>
          <th>Particulars</th>
          
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null;
          PreparedStatement ps2=null,ps3=null;
           try
           {
           
            ps3=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
            
          String sql="select t.SL_NO,t.TRANSFER_TO_OFFICE_ID ,t.TRANSFERED_TO_HO_UNIT_ID,o.OFFICE_NAME ,t.ACCOUNT_HEAD_CODE, t.AMOUNT,  t.CHEQUE_OR_DD ||'-'|| t.CHEQUE_DD_NO as cheDD,"+
          "to_char(t.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheDDdate,t.PARTICULARS  from  FAS_FUND_TRF_FROM_HO_TRN t,COM_MST_OFFICES o  where t.ACCOUNTING_UNIT_ID=? "+
          " and t.TRANSFER_TO_OFFICE_ID=o.OFFICE_ID and t.ACCOUNTING_FOR_OFFICE_ID=? and t.CASHBOOK_YEAR=? and t.CASHBOOK_MONTH=? "+
          "and t.voucher_no=? ";
           System.out.println(sql);
            ps2=con.prepareStatement(sql);
            ps2.setInt(1,cmbAcc_UnitCode);
            ps2.setInt(2,cmbOffice_code);
            ps2.setInt(3,yr);
            ps2.setInt(4,mon);
            ps2.setInt(5,recNo);
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
               
                out.println("<tr>");   
                out.println("<td align='left'>"+rs2.getInt("SL_NO")+"</td>");
                String officeName=rs2.getString("OFFICE_NAME");
                if(rs2.getInt("TRANSFERED_TO_HO_UNIT_ID")!=0)
                {
                    ps3.setInt(1,rs2.getInt("TRANSFERED_TO_HO_UNIT_ID"));
                    rs3=ps3.executeQuery();
                    if(rs3.next())
                    {
                        officeName=officeName+"-"+rs3.getString("ACCOUNTING_UNIT_NAME");
                    }
                }
                out.println("<td align='left'>"+officeName+"</td>");
                out.println("<td align='left'>"+rs2.getString("AMOUNT")+"</td>");
                
                
                
                if (rs2.getString("cheDD") !=null)
                {
                   out.println("<td align='left'>"+rs2.getString("cheDD")+"</td>");
                }
                else
                {
                  out.println("<td align='left'>"+"--"+"</td>");                  
                }
                
                
                if (rs2.getString("cheDDdate")!=null)
                {
                  out.println("<td align='left'>"+rs2.getString("cheDDdate")+"</td>");
                }
                else
                {
                  out.println("<td align='left'>"+"--"+"</td>"); 
                }
                
                
                if(rs2.getString("PARTICULARS")!=null)
                out.println("<td align='left'>"+rs2.getString("PARTICULARS")+"</td></tr>");
                else
                out.println("<td align='left'>"+"--"+"</td></tr>");
               
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
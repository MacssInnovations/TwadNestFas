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
    <title>Pass Order SubList</title>
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,passno=0;
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
            	passno=Integer.parseInt(request.getParameter("passno"));
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
              <strong>Pass Order Sub List Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
        
          <th>check List No</th>
          <th>check List Desc</th>
            <th>Bill No</th>
          <th>Bill Date</th>
          <th>Bill Amount</th>
       
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null,rs4=null;
          PreparedStatement ps2=null,ps3=null,ps4=null;
           try
           {
           
          String sql="SELECT CHECK_LIST_CODE, " +
        		  "  (SELECT nvl(CHECK_LIST_DESC,'-') as CHECK_LIST_DESC " +
        		  "  FROM fas_pre_audit_chklst_mst p " +
        		  "  WHERE p.accounting_unit_id= " +cmbAcc_UnitCode+
        		  "  AND p.cashbook_year       = " +yr+
        		  "  AND p.CASHBOOK_MONTH      = " +mon+
        		  "  AND p.STATUS              ='L' " +
        		  "  AND p.CHECK_LIST_CODE     =f.CHECK_LIST_CODE " +
        		  "  ) AS checkDesc, " +
        		  "  TO_CHAR(PRE_AUDIT_DATE,'dd/mm/yyyy')PRE_AUDIT_DATE, " +
        		  "  BILLNO, " +
        		  "  TO_CHAR(BILL_DATE,'dd/mm/yyyy')BILL_DATE, " +
        		  "  BILL_AMOUNT , " +
        		  "  BILL_MAJOR_TYPE_CODE, " +
        		  "  BILL_MINOR_TYPE_CODE, " +
        		  "  BILL_SUB_TYPE_CODE, " +
        		  "  PRE_AUDIT_BY, " +
        		  "  (SELECT BILL_MAJOR_TYPE_DESC " +
        		  "  FROM FAS_BILL_MAJOR_TYPES b1 " +
        		  "  WHERE b1.BILL_MAJOR_TYPE_CODE=f.BILL_MAJOR_TYPE_CODE " +
        		  "  )AS MajorDesc , " +
        		  "  (SELECT bill_minor_type_desc " +
        		  "  FROM fas_bill_minor_types_mst b2 " +
        		  "  WHERE b2.bill_minor_type_code=f.BILL_MINOR_TYPE_CODE " +
        		  "  AND b2.bill_major_type_code  =f.BILL_MAJOR_TYPE_CODE " +
        		  "  ) AS MinorDesc, " +
        		  "  (SELECT bill_sub_type_desc " +
        		  "  FROM FAS_BILL_SUB_TYPES b3 " +
        		  "  WHERE b3.BILL_MINOR_TYPE_CODE=f.BILL_MINOR_TYPE_CODE " +
        		  "  AND b3.BILL_MAJOR_TYPE_CODE  =f.BILL_MAJOR_TYPE_CODE " +
        		  "  AND b3.BILL_SUB_TYPE_CODE    =f.BILL_SUB_TYPE_CODE " +
        		  "  ) AS SubDesc, " +
        		  "  (SELECT EMPLOYEE_NAME " +
        		  "  FROM HRM_MST_EMPLOYEES " +
        		  "  WHERE EMPLOYEE_ID=f.PRE_AUDIT_BY " +
        		  "  )AS EntryDesc " +
        		  " FROM FAS_PRE_AUDIT_CHECK_NEW f " +
        		  " WHERE ACCOUNTING_UNIT_ID=" +cmbAcc_UnitCode+
        		  " AND CASHBOOK_YEAR       =" +yr+
        		  " AND CASHBOOK_MONTH      =" +mon+
        		  " AND f.STATUS            ='L' " +
        		  " and f.BILLNO= "+passno+
        		  " ORDER BY BILLNO";
            System.out.println(sql);
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
               //System.out.println("while");
                out.println("<tr>");   
            
                out.println("<td align='left'>"+rs2.getString("CHECK_LIST_CODE")+"</td>");
                out.println("<td align='left'>"+rs2.getString("checkDesc")+"</td>");
                out.println("<td align='left'>"+rs2.getString("BILLNO")+"</td>");
                out.println("<td align='left'>"+rs2.getString("BILL_DATE")+"</td>");
                out.println("<td align='right'>"+rs2.getString("BILL_AMOUNT")+"</td>");
          
              
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
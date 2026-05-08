<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Self-Cheque System</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
  <body class="table"><form name="frmVoucher_ListAll_SL" method="POST">
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
              <strong>Acquittance Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th> Acq. RollNo.</th>
          <th>Division/Sub-Division</th>
          <th>Employee Code</th>
          <th>Employee Name & Designation</th>
          <th>Amount</th>
          
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null;
          PreparedStatement ps2=null,ps3=null;
           try
           {
           System.out.println("now");
          
          String sql="select a.ACQ_ROLL_NO,a.EMPLOYEE_CODE, b.OFFICE_NAME,e.EMPLOYEE_NAME ||'-' || d.DESIGNATION as emp_desig"+
            ",trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT"+
            " from FAS_ACQ_ROLL_TRANSACTION a,COM_MST_OFFICES b,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d ,"+
            " HRM_MST_EMPLOYEES e where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and "+
            " c.EMPLOYEE_ID=a.EMPLOYEE_CODE and a.DISBURSING_OFFICE_CODE=b.OFFICE_ID and a.EMPLOYEE_CODE=c.EMPLOYEE_ID and  "+
            " a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and "+
             " a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=? and  a.VOUCHER_NO=? order by ACQ_ROLL_NO";
            //System.out.println(sql);
            ps2=con.prepareStatement(sql);
            ps2.setInt(1,cmbAcc_UnitCode);
            ps2.setInt(2,cmbOffice_code);
            ps2.setInt(3,yr);
            ps2.setInt(4,mon);
            ps2.setInt(5,recNo);
            
            rs2=ps2.executeQuery();
            //System.out.println("hi");
            while(rs2.next())
            {
               
                out.println("<tr>");   
                out.println("<td align='center'>"+rs2.getInt("ACQ_ROLL_NO")+"</td>");
                out.println("<td align='left'>"+rs2.getString("OFFICE_NAME")+"</td>");
                out.println("<td align='left'>"+rs2.getInt("EMPLOYEE_CODE")+"</td>");
                out.println("<td align='left'>"+rs2.getString("emp_desig")+"</td>");
                out.println("<td align='left'>"+rs2.getString("TOTAL_AMOUNT")+"</td></tr>");
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
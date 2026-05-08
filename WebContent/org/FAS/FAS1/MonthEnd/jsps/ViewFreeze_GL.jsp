<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Cash Receipt System</title>
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
  <body class="table"><form name="frmviewfreeze" method="POST">
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

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0,recNo=0;

    
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
             System.out.println("accounting unit code..."+cmbAcc_UnitCode);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
           
           
            try{
                 Cashbook_year=Integer.parseInt(request.getParameter("CashbookYear"));
                 System.out.println("Cashbook_year...."+Cashbook_year);
               }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
                 Cashbook_month=Integer.parseInt(request.getParameter("CashbookMonth"));
                 System.out.println("Cashbook_month...."+Cashbook_month);
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
           
           /*
            Cashbook_year=2008;
            Cashbook_month=3;
            */
            
  %>
      <table cellspacing="3" cellpadding="2"  width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong> Closing Balance Details </strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th>
            Account Head and Description
          </th>
          
          <th>
            GL Cl Balance
          </th>
          <th>
            GL Ind
          </th>
          <th>SL Cl Balance</th>
          <th>
            SL Ind
          </th>
          <th>Differnce</th>
         
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null;
          PreparedStatement ps2=null,ps3=null;
           try
           {
           System.out.println("accunit"+cmbAcc_UnitCode);
           System.out.println("cashbookyear"+Cashbook_year);
           System.out.println("cashbookmonth"+Cashbook_month);
           
            
            String sql="select accounting_unit_id,account_head_code,total,month_closing_bal_dr_cr_ind,year,month, " +
                       " sl_tot,sl_mon_cl_cr_dr, diff from " +
                       " ( " +
                       " select a.accounting_unit_id,a.account_head_code,a.total,a.month_closing_bal_dr_cr_ind,a.year,a.month, " +
                       " b.sl_tot,b.sl_mon_cl_cr_dr,(total-sl_tot) as diff from " +
                       " ( " +
                       " select accounting_unit_id,account_head_code,month_closing_balance as total, " +
                       " month_closing_bal_dr_cr_ind,year,month from FAS_GENERAL_LEDGER_CB " +
                       " ) a " +
                       " inner join " +
                       " ( " +
                       " select accounting_unit_id as acct_unit_id, year as yr, month as mont, account_head_code as acct_head_code, " +
                       " month_closing_bal_dr_cr_ind  as sl_mon_cl_cr_dr, " +
                       " sum(month_closing_balance) as sl_tot from FAS_SUB_LEDGER_MASTER_CB " +
                       " group by accounting_unit_id,year, month,account_head_code,month_closing_bal_dr_cr_ind " +
                        " ) b " +
                        " on a.accounting_unit_id=b.acct_unit_id and a.year=b.yr and a.month=b.mont and " +
                         " a.account_head_code=b.acct_head_code and a.month_closing_bal_dr_cr_ind=b.sl_mon_cl_cr_dr " +
                         " where a.accounting_unit_id="+cmbAcc_UnitCode+" and a.year="+Cashbook_year+" and a.month="+Cashbook_month+
                         " ) " +
                         " where diff != 0 ";
                         
                        ps2=con.prepareStatement(sql); 
                        
                       
            
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
               System.out.println("while");
                out.println("<tr>");   
               
                ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
                rs3=ps3.executeQuery();
                if(rs3.next())
                out.println("<td align='left'>"+rs2.getInt("account_head_code")+"-"+rs3.getString("ACCOUNT_HEAD_DESC")+"</td>");
                               
                out.println("<td align='left'>"+rs2.getInt("total")+"</td>");
                
                out.println("<td align='left'>"+rs2.getString("month_closing_bal_dr_cr_ind")+"</td>");                 
                           
                out.println("<td align='left'>"+rs2.getInt("sl_tot")+"</td>");
                          
                out.println("<td align='left'>"+rs2.getString("sl_mon_cl_cr_dr")+"</td>");
              
                out.println("<td align='left'>"+rs2.getInt("diff")+"</td>");
                

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
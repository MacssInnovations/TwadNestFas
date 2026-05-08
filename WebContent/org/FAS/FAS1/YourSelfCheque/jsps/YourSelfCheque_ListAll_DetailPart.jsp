<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>  
    <meta http-equiv="cache-control" content="no-cache">
    <title>YourSelf Cheque System</title>
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
  <body class="table"><form name="Bank_Rec_ListAll_SL_Form" method="POST">
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
  			int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,vocNo=0;

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
            	vocNo=Integer.parseInt(request.getParameter("vocNo"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            
            
  %>
      <table cellspacing="3" cellpadding="2" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>YourSelf Cheque System Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="0" width="100%">
        <tr class="tdH">
          <th>Voucher Number</th>
          <th>SL No.</th>
          <th>DD Number</th>
          <th>DD Date</th>
          <th>DD Amount</th>
          <th>DD in Favour of </th>
          <th>Commission Charge</th>
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null;
          PreparedStatement ps2=null,ps3=null;
           try
           {
           
          String sql=" SELECT											"+ 
					 "    voucher_no,									"+
 					 "    slno,											"+
  					 "    dd_number,  									"+
   					 "    to_char(dd_date,'DD/MM/YYYY') AS che_dd_date,	"+
    				 "    dd_amount,									"+
     				 "    dd_in_favour_of,								"+
     				 "    commission_charges							"+
      				 " FROM fas_yourself_cheque_trans 					"+
       				 " where 											"+
        			 "	  ACCOUNTING_UNIT_ID = ? and 					"+
        			 "	  ACCOUNTING_FOR_OFFICE_ID = ? and 				"+
        			 "	  CASHBOOK_YEAR = ? and 		     			"+
        			 "	  CASHBOOK_MONTH = ? and   						"+
        			 "	  VOUCHER_NO = ?  								";


            ps2=con.prepareStatement(sql);
            ps2.setInt(1,cmbAcc_UnitCode);
            ps2.setInt(2,cmbOffice_code);
            ps2.setInt(3,yr);
            ps2.setInt(4,mon);
            ps2.setInt(5,vocNo);
          
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
    	        out.println("<tr>");   
                out.println("<td align='Right'>"+rs2.getInt("voucher_no")+"</td>");
                out.println("<td align='Right'>"+rs2.getString("slno")+"</td>");
                out.println("<td align='Right'>"+rs2.getString("dd_number")+"</td>");                
                out.println("<td align='Center'>"+rs2.getString("che_dd_date")+"</td>");
                out.println("<td align='Right'>"+rs2.getString("dd_amount")+"</td>");                
                out.println("<td align='left'>"+rs2.getString("dd_in_favour_of")+"</td>");                
                out.println("<td align='Right'>"+rs2.getString("commission_charges")+"</td></tr>");
            }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
         %>
        </tbody>
      </table>
      <table align="center" cellspacing="3" cellpadding="2" border="0"
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
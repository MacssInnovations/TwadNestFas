<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Details Part of Adjustment Memo</title>
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
<body class="table"><form name="adj_List_DetailaPart" method="POST">
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

          //  ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  			int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,vocNo=0;

            try
            {
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
              <strong>Adjustment Memo ListDetails</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th>Sl No</th>
          <th>A/c Head Code</th>
          <th>CR/DR</th>
          <th>Sub Ledger Type</th>
          <th>Sub Ledger Code </th>
          <th>Amount</th>
          <th>Particulars</th>
          <th>Letter Number</th>
          <th>Letter Date</th>
        </tr>
        <tbody id="tbody" class="table">
        <%
          ResultSet rs2=null,rs3=null;
          PreparedStatement ps2=null,ps3=null;
          String sql="",particulars="",ho_ref_date="";
          int ho_ref_no=0,sl_code=0,sl_type=0;
          try
          {
           
	        	   sql ="select a.sl_no,a.ACCOUNT_HEAD_CODE,a.CR_DR_TYPE,a.SUB_LEDGER_TYPE_CODE,a.SUB_LEDGER_CODE,(select u.accounting_unit_name from fas_mst_acct_units u where u.accounting_unit_id=a.SUB_LEDGER_CODE)as sub_desc, "+
	        	   " a.LETTER_NO,to_char(a.LETTER_DATE,'DD/MM/YYYY') as LETTER_DATE ,trim(to_char(a.AMOUNT,'99999999999999.99'))as AMOUNT,a.REMARKS, "+
	        	   " b.account_head_desc,c.sub_ledger_type_desc from FAS_ADJUST_MEMO_TRN a left outer join com_mst_account_heads b on a.account_head_code=b.account_head_code left outer join com_mst_sl_types c on  "+
	        	   " a.SUB_LEDGER_TYPE_CODE=c.sub_ledger_type_code where accounting_unit_id=? and accounting_for_office_id=? and cashbook_year=? and cashbook_month =? and voucher_no =? order by a.sl_no";
	        	   System.out.println("  sql:"+sql);
		
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
		    	        
		    	        out.println("<td align='Center'>"+rs2.getInt("sl_no")+"</td>");
		                out.println("<td align='left'>"+rs2.getInt("account_head_code")+"-"+rs2.getString("account_head_desc")+"</td>");
		                out.println("<td align='Center'>"+rs2.getString("CR_DR_TYPE")+"</td>");
		                sl_type=rs2.getInt("SUB_LEDGER_TYPE_CODE");
		                sl_code=rs2.getInt("SUB_LEDGER_CODE");
		                
		                if(sl_type==0)
		                    out.println("<td align='center'>"+"-"+"</td>");    
		                else
		                	out.println("<td align='Left'>"+rs2.getString("sub_ledger_type_desc")+"</td>");
		                
							out.println("<td align='center'>"+rs2.getString("sub_desc")+"</td>");
		                  		                		                
		                out.println("<td align='left'>"+rs2.getString("AMOUNT")+"</td>");
		                
		                particulars=rs2.getString("REMARKS");
		                if(particulars==null)
		                	//particulars="";
		                	out.println("<td align='center'>"+"-"+"</td>");  
		                else
		                out.println("<td align='Left'>"+particulars+"</td>");
		                
		                
		                ho_ref_no=rs2.getInt("LETTER_NO");
		                if(ho_ref_no==0)
		                	out.println("<td align='center'>"+"-"+"</td>");  
		                else
		                	out.println("<td align='Right'>"+ho_ref_no+"</td>");
		                
		                
		                ho_ref_date=rs2.getString("LETTER_DATE");
		                if(ho_ref_date==null)
		                	out.println("<td align='center'>"+"-"+"</td>"); 
		                //	ho_ref_date="";
		                else
		                out.println("<td align='left'>"+ho_ref_date+"</td></tr>");  
		                
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
</form>
</body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Cheque Memo Details</title>
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
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table"><form name="raised_List" method="POST">
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
  		  int  cmbAcc_UnitCode=0,cmbOffice_code=0,yr=0,mon=0,vou_no=0;

          try{
          	 cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
                 yr=Integer.parseInt(request.getParameter("cashbook_yr"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
        	  mon=Integer.parseInt(request.getParameter("cboCashBook_Month"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            	vou_no=Integer.parseInt(request.getParameter("cboBillNo"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
         
            
          System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode+" cmbOffice_code====>"+cmbOffice_code+" yr====>"+yr);
  %>
      <table cellspacing="3" cellpadding="2"  width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Cheque Memo Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th>SL No</th>
          <th>Sanc/proc No</th>
          <th>Sanc/proc Date</th>
          <th> Bill No</th>
          <th>Sub-Ledger Type</th>
          <th>Sub-Ledger Code</th>
          <th>Amount</th>
          <th>Sanc Amount</th>
          <th>Particulars</th>
        </tr>
        <tbody id="tbody" class="table">
          <%
          		  ResultSet rs2=null,rs3=null;
		          PreparedStatement ps2=null,ps3=null;
		          int sl_no=0;
		          try
		          {
		           	    System.out.println("inside try");
			 	        String sql="Select Bill_No,Sanction_Proceeding_No,to_char(Sanction_Proceeding_Date,'dd/mm/yyyy')as Sanction_Proceeding_Date, "+
			 	        	" M.Sub_Ledger_Type_Code,"+
			 	        	" (Select S.Sub_Ledger_Type_Desc From Com_Mst_Sl_Types S Where "+ 
			 	        	" S.Sub_Ledger_Type_Code=m.Sub_Ledger_Type_Code)As Type_Desc,"+
			 	        	" Sub_Ledger_Code,"+
			 	        	" (Select V.Sl_Codename From Sl_Type_Code_Name_View V "+
			 	        	" Where V.Sl_Type=M.Sub_Ledger_Type_Code And V.Sl_Code=M.Sub_Ledger_Code)As Code_Desc,"+
			 	        	" Amount,Sanctioned_Amount,REMARKS From Fas_Memo_Of_Payment_Mst m"+
			 	        	" where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and Accounting_For_Office_Id="+cmbOffice_code+" and CASHBOOK_YEAR="+yr+" and CASHBOOK_MONTH="+mon+" and BILL_NO="+vou_no;
			 	        System.out.println("SQL ::: "+sql);
			            ps2=con.prepareStatement(sql);
			            rs2=ps2.executeQuery();
			            while(rs2.next())
			            {
			                System.out.println("while");
			                sl_no++;
			                out.println("<tr>");   
			                out.println("<td align='left'>"+sl_no+"</td>");
			                out.println("<td align='left'>"+rs2.getInt("Sanction_Proceeding_No")+"</td>");
			                out.println("<td align='left'>"+rs2.getString("Sanction_Proceeding_Date")+"</td>");
			                out.println("<td align='left'>"+rs2.getInt("Bill_No")+"</td>");
			                out.println("<td align='left'>"+rs2.getString("Type_Desc")+"</td>");
			                out.println("<td align='left'>"+rs2.getString("Code_Desc")+"</td>");
			                out.println("<td align='left'>"+rs2.getString("AMOUNT").trim()+"</td>");
			                out.println("<td align='left'>"+rs2.getString("Sanctioned_Amount").trim()+"</td>");
			                out.println("<td align='left'>"+rs2.getString("REMARKS")+"</td></tr>");
			            }
		          }
		          catch(Exception e)
		          {
		            	System.out.println("Exception in grid.."+e.getMessage());
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
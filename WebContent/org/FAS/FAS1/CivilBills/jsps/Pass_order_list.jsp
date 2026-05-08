<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.Date"%>

<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Pass Order List</title>
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
		  String billDate="";
		Date PassOrderDate=null;
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
  		  int  cmbAcc_UnitCode=0,cmbOffice_code=0,billMajorType=0,billNo=0,billSubType=0,PassOrderNo=0;

          try{
          	 cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
        	  PassOrderNo=Integer.parseInt(request.getParameter("passNo"));
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          try{
        	

        		Calendar c3;
        		  String[] sd=request.getParameter("billNo").split("/");
                  c3=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                  java.util.Date d=c3.getTime();
                  PassOrderDate=new Date(d.getTime());
        /* 	Calendar c3 = new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),
        				Integer.parseInt(sd3[1]) - 1, Integer.parseInt(sd3[0]));
        		java.util.Date d3 = c3.getTime();
        		PassOrderDate = new Date(d3.getTime()); */
          }catch(Exception e){System.out.println("Exception in getting req:"+e);}
          
          System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode+" cmbOffice_code====>"+cmbOffice_code+" PassOrderDate====>"+PassOrderDate);
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
          <th> Bill No</th>
          <th>Bill Date</th>
           <th>Bill Type</th>
          <th>Sanc/proc No</th>
          <th>Head Code</th><!--
          <th>Bill Major Type</th>
          <th>Bill Minor Type</th>
          <th>Sub Type</th>
           <th>Bill Amount</th>
          --><th>Sanc Amount</th>
         
          <th>Deducted Amount</th>
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
			 	        String sql="SELECT AAA.* from (SELECT " +
			 	   /*      	"	m.bill_major_type, " +
			 	       "  m.bill_minor_type_code, " +
			 	      "  m.bill_sub_type_code, " + */
			 	      "  M.BILL_NO, " +
			 	      "  M.SANCTION_PROC_NO, " +
			 	      "  CASE  " +
			 	      "    WHEN m.bill_type <> 'WOSP' and m.bill_major_type    =2 " +
			 	      "    AND m.bill_minor_type_code=2 " +
			 	      "    AND m.bill_sub_type_code  =1 " +
			 	      "    THEN " +
			 	      "      (SELECT SANCTION_PROC_NO " +
			 	      "      FROM sls_sanctions_bills_link_mst1 " +
			 	      "      WHERE HRMS_SANCTION_ID=M.SANCTION_PROC_NO ::numeric " +
			 	      "      ) when  m.bill_type = 'WOSP' then M.SANCTION_PROC_NO  " +
			 	      "    ELSE " +
			 	      "      (SELECT SANCTION_PROC_NO " +
			 	      "      FROM HRM_SANCTIONS_BILLS_LINK_MST " +
			 	      "      WHERE HRMS_SANCTION_ID=M.SANCTION_PROC_NO ::numeric  " +
			 	      "      ) " +
			 	      "  END                              AS SANC_ID, " +
			 	      "  TO_CHAR(M.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, " +
			 	      "  T.ACCOUNT_HEAD_CODE, " +
			 	      "  (SELECT S.ACCOUNT_HEAD_DESC " +
			 	      "  FROM COM_MST_ACCOUNT_HEADS S " +
			 	      "  WHERE S.ACCOUNT_HEAD_CODE=t.ACCOUNT_HEAD_CODE " +
			 	      "  )AS TYPE_DESC, " +
			 	      "  M.BILL_MAJOR_TYPE, " +
			 	      "  (SELECT K.BILL_MAJOR_TYPE_DESC " +
			 	      "  FROM FAS_BILL_MAJOR_TYPES K " +
			 	      "  WHERE K.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE " +
			 	      "  )AS MAJOR_DESC, " +
			 	      "  M.BILL_MINOR_TYPE_CODE, " +
			 	      "  (SELECT K.BILL_MINOR_TYPE_DESC " +
			 	      "  FROM FAS_BILL_MINOR_TYPES_MST K " +
			 	      "  WHERE K.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE " +
			 	      "  AND k.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE " +
			 	      "  )AS MINOR_DESC, " +
			 	      "  M.BILL_SUB_TYPE_CODE, " +
			 	      "  (SELECT KK.BILL_SUB_TYPE_DESC " +
			 	      "  FROM FAS_BILL_SUB_TYPES KK " +
			 	      "  WHERE KK.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE " +
			 	      "  AND kK.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE " +
			 	      "  AND kK.BILL_SUB_TYPE_CODE    =m.BILL_SUB_TYPE_CODE " +
			 	      "  )AS SUB_DESC, " +
			 	      "  m.TOTAL_SANCTIONED_AMOUNT, " +
			 	      "  M.TOTAL_BILL_AMOUNT, " +
			 	      "  M.DEDUCTED_AMOUNT, " +
			 	      "  m.REMARKS " +
			 	      "FROM FAS_BILL_REGISTER_MASTER M, " +
			 	      "  FAS_BILL_REGISTER_TRANSACTION T " +
			 	      "WHERE M.ACCOUNTING_UNIT_ID     =T.ACCOUNTING_UNIT_ID " +
			 	      "AND M.ACCOUNTING_UNIT_OFFICE_ID=T.ACCOUNTING_UNIT_OFFICE_ID " +
			 	      "AND M.CASHBOOK_YEAR            =T.CASHBOOK_YEAR " +
			 	      "AND M.CASHBOOK_MONTH           =T.CASHBOOK_MONTH " +
			 	      "AND M.BILL_NO                  =T.BILL_NO " +
			 	      "AND M.ACCOUNTING_UNIT_ID       = ? " +
			 	      "AND m.ACCOUNTING_UNIT_OFFICE_ID=? " +
			 	      "AND M.PASS_ORDER_DATE                  = ? " +
			 	      " and m.bill_date < '01-Apr-15' AND M.STATUS                   ='L'"+
			 	     " union all "+
			 	  "  SELECT " +
				 	   /*      	"	m.bill_major_type, " +
				 	       "  m.bill_minor_type_code, " +
				 	      "  m.bill_sub_type_code, " + */
				 	      "  M.BILL_NO, " +
				 	      "  M.SANCTION_PROC_NO, " +
				 	      "  CASE  " +
				 	      "    WHEN m.bill_type <> 'WOSP' and m.bill_major_type    =2 " +
				 	      "    AND m.bill_minor_type_code=2 " +
				 	      "    AND m.bill_sub_type_code  =1 " +
				 	      "    THEN " +
				 	      "      (SELECT SANCTION_PROC_NO " +
				 	      "      FROM sls_sanctions_bills_link_mst1 " +
				 	      "      WHERE HRMS_SANCTION_ID=M.SANCTION_PROC_NO::numeric " +
				 	      "      ) when  m.bill_type = 'WOSP' then M.SANCTION_PROC_NO  " +
				 	      "    ELSE " +
				 	      "      (SELECT SANCTION_PROC_NO " +
				 	      "      FROM HRM_SANCTIONS_BILLS_LINK_MST " +
				 	      "      WHERE HRMS_SANCTION_ID=M.SANCTION_PROC_NO::numeric " +
				 	      "      ) " +
				 	      "  END                              AS SANC_ID, " +
				 	      "  TO_CHAR(M.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, " +
				 	      "  T.ACCOUNT_HEAD_CODE, " +
				 	      "  (SELECT S.ACCOUNT_HEAD_DESC " +
				 	      "  FROM COM_MST_ACCOUNT_HEADS S " +
				 	      "  WHERE S.ACCOUNT_HEAD_CODE=t.ACCOUNT_HEAD_CODE " +
				 	      "  )AS TYPE_DESC, " +
				 	      "  M.BILL_MAJOR_TYPE, " +
				 	      "  (SELECT K.BILL_MAJOR_TYPE_DESC " +
				 	      "  FROM FAS_BILL_MAJOR_TYPES K " +
				 	      "  WHERE K.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE " +
				 	      "  )AS MAJOR_DESC, " +
				 	      "  M.BILL_MINOR_TYPE_CODE, " +
				 	      "  (SELECT K.BILL_MINOR_TYPE_DESC " +
				 	      "  FROM FAS_BILL_MINOR_TYPES_MST K " +
				 	      "  WHERE K.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE " +
				 	      "  AND k.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE " +
				 	      "  )AS MINOR_DESC, " +
				 	      "  M.BILL_SUB_TYPE_CODE, " +
				 	      "  (SELECT KK.BILL_SUB_TYPE_DESC " +
				 	      "  FROM FAS_BILL_SUB_TYPES KK " +
				 	      "  WHERE KK.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE " +
				 	      "  AND kK.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE " +
				 	      "  AND kK.BILL_SUB_TYPE_CODE    =m.BILL_SUB_TYPE_CODE " +
				 	      "  )AS SUB_DESC, " +
				 	      "  m.TOTAL_SANCTIONED_AMOUNT, " +
				 	      "  M.TOTAL_BILL_AMOUNT, " +
				 	      "  M.DEDUCTED_AMOUNT, " +
				 	      "  m.REMARKS " +
				 	      "FROM FAS_BILL_REGISTER_MASTERNEW M, " +
				 	      "  FAS_BILL_REGISTER_TRANSACTIONW T " +
				 	      "WHERE M.ACCOUNTING_UNIT_ID     =T.ACCOUNTING_UNIT_ID " +
				 	      "AND M.ACCOUNTING_UNIT_OFFICE_ID=T.ACCOUNTING_UNIT_OFFICE_ID " +
				 	      "AND M.CASHBOOK_YEAR            =T.CASHBOOK_YEAR " +
				 	      "AND M.CASHBOOK_MONTH           =T.CASHBOOK_MONTH " +
				 	      "AND M.BILL_NO                  =T.BILL_NO " +
				 	      "AND M.ACCOUNTING_UNIT_ID       = ? " +
				 	      "AND m.ACCOUNTING_UNIT_OFFICE_ID=? " +
				 	      "AND M.PASS_ORDER_DATE                  = ? " +
				 	      "AND M.STATUS                   ='L'"+
				 	     " union all "+
			 	     " SELECT "+
			 	   /*   " m.bill_major_type, "+
			 	      "  m.bill_minor_type_code, "+
			 	      "  m.bill_sub_type_code, "+ */
			 	       " M.BILL_NO, "+
			 	       " M.SANCTION_PROC_NO, "+
			 	       " '' as SANC_ID, "+
			 	       " TO_CHAR(M.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE, "+
			 	      "  M.ACCOUNT_HEAD_CODE, "+
			 	       " (SELECT S.ACCOUNT_HEAD_DESC "+
			 	       " FROM COM_MST_ACCOUNT_HEADS S "+
			 	      "  WHERE S.ACCOUNT_HEAD_CODE=M.ACCOUNT_HEAD_CODE "+
			 	      "  )AS TYPE_DESC, "+
			 	      "  M.BILL_MAJOR_TYPE, "+
			 	      "  (SELECT K.BILL_MAJOR_TYPE_DESC "+
			 	      "  FROM FAS_BILL_MAJOR_TYPES K "+
			 	      "  WHERE K.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE "+
			 	      "  )AS MAJOR_DESC, "+
			 	      "  M.BILL_MINOR_TYPE_CODE, "+
			 	      "  (SELECT K.BILL_MINOR_TYPE_DESC "+
			 	       " FROM FAS_BILL_MINOR_TYPES_MST K "+
			 	       " WHERE K.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE "+
			 	      "  AND k.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE "+
			 	      "  )AS MINOR_DESC, "+
			 	      "  M.BILL_SUB_TYPE_CODE, "+
			 	      "  (SELECT KK.BILL_SUB_TYPE_DESC "+
			 	      "  FROM FAS_BILL_SUB_TYPES KK "+
			 	      "  WHERE KK.BILL_MAJOR_TYPE_CODE=M.BILL_MAJOR_TYPE "+
			 	      "  AND kK.BILL_MINOR_TYPE_CODE  =m.BILL_MINOR_TYPE_CODE "+
			 	      "  AND kK.BILL_SUB_TYPE_CODE    =m.BILL_SUB_TYPE_CODE "+
			 	      "  )AS SUB_DESC, "+
			 	      "  m.TOTAL_SANCTIONED_AMOUNT, "+
			 	      "  M.TOTAL_BILL_AMOUNT, "+
			 	      "  M.DEDUCTED_AMOUNT, "+
			 	      "  m.REMARKS "+
			 	    "  FROM FAS_BILL_REGISTERNEW M "+
			 	    "  WHERE M.ACCOUNTING_UNIT_ID       =? "+
			 	     " AND m.ACCOUNTING_UNIT_OFFICE_ID=? "+
			 	     " AND M.PASS_ORDER_DATE                  = ?  "+
			 	    "  AND M.STATUS                   ='L'  "+
			 	  "  )AAa "+
			 	 " INNER JOIN "+
			 	 " (SELECT  "+ 
			 			 "   t.BILL_NO "+
			 			 "  FROM FAS_PASS_ORDER_MST M, "+
			 					 "    FAS_PASS_ORDER_TRN T "+
			 					 "  WHERE M.ACCOUNTING_UNIT_ID =T.ACCOUNTING_UNIT_ID AND "+
			 							 "  m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID and "+
			 									 "   M.CASHBOOK_YEAR=T.CASHBOOK_YEAR AND "+
		  "   M.CASHBOOK_MONTH=T.CASHBOOK_MONTH AND "+
				  "   M.PASS_ORDER_NO=T.PASS_ORDER_NO  "+
 "   AND M.ACCOUNTING_UNIT_ID   =? "+
		 "   AND M.PASS_ORDER_DATE = ? "+
				 "  AND M.PASS_ORDER_NO   =? "+
				 "  )BBb "+
				 " ON AAA.bill_no=bbb.bill_no ";
			 	    
			 	    
			 	        System.out.println("SQL ::: "+sql);
			            ps2=con.prepareStatement(sql);
			            ps2.setInt(1,cmbAcc_UnitCode);
			            ps2.setInt(2,cmbOffice_code);
			            ps2.setDate(3,PassOrderDate);
			            ps2.setInt(4,cmbAcc_UnitCode);
			            ps2.setInt(5,cmbOffice_code);
			            ps2.setDate(6,PassOrderDate);
			            ps2.setInt(7,cmbAcc_UnitCode);
			            ps2.setInt(8,cmbOffice_code);
			            ps2.setDate(9,PassOrderDate);
			            ps2.setInt(10,cmbAcc_UnitCode);
			            ps2.setInt(12,PassOrderNo);
			            ps2.setDate(11,PassOrderDate);
			            
			            rs2=ps2.executeQuery();
			            while(rs2.next())
			            {
			               // System.out.println("while");
			                sl_no++;
			                out.println("<tr>");   
			                out.println("<td align='left'>"+rs2.getInt("BILL_NO")+"</td>");
			                out.println("<td align='left'>"+rs2.getString("BILL_DATE")+"</td>");
			                out.println("<td align='left'>"+rs2.getString("MAJOR_DESC")+"-"+rs2.getString("MINOR_DESC")+"-"+rs2.getString("SUB_DESC")+"</td>");
			                out.println("<td align='left'>"+rs2.getString("SANC_ID")+"</td>");
			                out.println("<td align='left'>"+"("+rs2.getInt("ACCOUNT_HEAD_CODE")+")"+rs2.getString("Type_Desc")+"</td>"); 
			                out.println("<td align='right'>"+rs2.getString("TOTAL_SANCTIONED_AMOUNT").trim()+"</td>");
			                out.println("<td align='right'>"+rs2.getString("DEDUCTED_AMOUNT").trim()+"</td>");
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
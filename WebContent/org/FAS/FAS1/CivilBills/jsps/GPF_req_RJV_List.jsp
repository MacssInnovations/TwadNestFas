<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.Date"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>GPF Number List</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body bgcolor="rgb(255,255,225)">
  
  <form name="frm_AccNo_Popup_Form1" id="frm_AccNo_Popup_Form1">
      <p>
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
  
  
  %>
      </p>
      <p>
        &nbsp;
      </p>
     
      <table  border="0" width="80%">
      <tr><td>
       <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="100%">
         <tr class="tdH" >
        <th align="center" colspan="2">
              GPF Request RJV LIST
                </th>
           </tr>
     </table>
      
         
        
         <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
            REQUEST_NO
            </th>
            <th>
             ACCOUNT_HEAD_CODE
            </th>
             <th>
                AMOUNT
            </th>
            <th>
                CR_DR_TYPE
            </th>
              <th>
                Sl Type Code
            </th>
            <th>
                Sl Code
            </th>
            <th>
                SL_NO
            </th>
            <th>
              ADJ Against Year
            </th>
             <th>
               ADJ Against Month
            </th>
             <th>
            ADJ Doc Type
            </th>
             <th>
             ADJ Doc No
            </th>
            
            
          </tr>
          <tbody id="tb" class="table">

          <%
          	ResultSet rs2=null,rs3=null;
                     PreparedStatement ps2=null,ps3=null;
                 try
                 {
                     int cmbAcc_UnitCode=0,request_no=0,y=0,Office_code=0,txtSubBankId=0,txtSub_Office_code=0;
                     String Amount="",VOUCHER_DATE="",unspent_OR_col="";
                     java.sql.Date txtVrDate = null;
                      try{
                    	  cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                      }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
                      try{
                    	  request_no=Integer.parseInt(request.getParameter("request_no"));
                         }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
                        
                      try{
                    	  VOUCHER_DATE=request.getParameter("VOUCHER_DATE");
                         }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
                        
                      try{
                    	  Amount=request.getParameter("Amount");
                         }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
                        
                      try{
                	      String[] sd=request.getParameter("VOUCHER_DATE").split("/");
                	   
                	     Calendar c=new java.util.GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                	      java.util.Date d=c.getTime();
                	
                	   txtVrDate=new Date(d.getTime());
                	      System.out.println("txtVrDate >> "+txtVrDate);
                	        }catch(Exception e){
                	        	e.printStackTrace();
                	        }
                      String sql= "SELECT t.request_no, " +
                    		  "  m.ACCOUNTING_UNIT_ID, " +
                    		  "  t.CASHBOOK_YEAR, " +
                    		  "  t.CASHBOOK_MONTH, " +
                    		  "  trim(u.accounting_unit_name) unit_name, " +
                    		  "  M.APPROVED, " +
                    		  "  m.ACCOUNTING_FOR_OFFICE_ID, " +
                    		  "  TO_CHAR(m.VOUCHER_DATE,'dd/mm/yyyy') AS VOUCHER_DATE , " +
                    		  "  m.JOURNAL_AMOUNT, " +
                    		  "  ACCOUNT_HEAD_CODE, " +
                    		  "  AMOUNT, " +
                    		  "  CR_DR_TYPE, " +
                    		  "  SL_NO,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,  sl.sub_ledger_type_desc ,st.sl_codename,ADJ_AGAINST_YEAR,"+
                    				  "  ADJ_AGAINST_MONTH," +
 "  nvl(ADJ_DOC_TYPE,' ') as ADJ_DOC_TYPE ," +
 "  ADJ_DOC_NO" +
                    		  " FROM FAS_GPF_RJV_REQ_MST m " +
                    		  " INNER JOIN FAS_GPF_RJV_REQ_TRN t " +
                    		  " ON m.accounting_unit_id       =t.accounting_unit_id " +
                    		  " AND m.accounting_for_office_id=t.accounting_for_office_id " +
                    		  " AND m.cashbook_month          = t.cashbook_month " +
                    		  " AND m.cashbook_year           =t.cashbook_year " +
                    		  " AND m.request_no              =t.request_no " +
                    		  " AND m.VOUCHER_DATE            =?"+
                    		  " AND m.request_no              = " +request_no+
                    		  " AND  m.accounting_unit_id               = " +cmbAcc_UnitCode+
                    		  " AND  JOURNAL_AMOUNT               = " +Amount+
                    		  " AND m.status                  ='L' " +
                    	/* 	  " AND ( M.APPROVED             IS NULL " +
                    		  " OR M.APPROVED                <> 'Y') " + */
                    		  " INNER JOIN FAS_MST_ACCT_UNITS u " +
                    		  " ON m.accounting_unit_id =u.accounting_unit_id " +
                    		  "		  inner join COM_MST_SL_TYPES sl                           on t.SUB_LEDGER_TYPE_CODE=sl.sub_ledger_type_code "+
                    		" inner join SL_TYPE_CODE_NAME_VIEW st on  t.SUB_LEDGER_TYPE_CODE=st.sl_type and t.SUB_LEDGER_CODE=st.sl_code "+
                    				  " ORDER BY m.request_no,SL_NO";
                    
                      ps=con.prepareStatement(sql);
                      ps.setDate(1,txtVrDate);
                   
          		rs2 = ps.executeQuery();

          		int rowid = 0;
          		while (rs2.next()) {
          			rowid++;
          			out.println("<tr id=" + rowid + ">");
          			

          			out.println("<td align='left'>"
          					+ rs2.getInt("request_no") + "</td>");
          			out.println("<td align='left'>" +  rs2.getInt("ACCOUNT_HEAD_CODE")
          					+ "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("AMOUNT") + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("CR_DR_TYPE") + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("sub_ledger_type_desc") + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("sl_codename") + "</td>");
          			out.println("<td align='left'>"
          					+  rs2.getInt("SL_NO")+ "</td>");
          			out.println("<td align='left'>"
          					+  rs2.getInt("ADJ_AGAINST_YEAR")+ "</td>");
          			out.println("<td align='left'>"
          					+  rs2.getInt("ADJ_AGAINST_MONTH")+ "</td>");
          			out.println("<td align='left'>"
          					+  rs2.getString("ADJ_DOC_TYPE")+ "</td>");
          			out.println("<td align='left'>"
          					+  rs2.getInt("ADJ_DOC_NO")+ "</td>");
          		
          		}
          		
          	} catch (Exception e) {
          		System.out.println("Exception in grid.." + e);
          	}
          %>
         </tbody>
        </table>
     
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
   
      </div>
      </td>
      </tr>
      
      </table>
       </div>
    </td></tr></table>
    </form></body>
</html>
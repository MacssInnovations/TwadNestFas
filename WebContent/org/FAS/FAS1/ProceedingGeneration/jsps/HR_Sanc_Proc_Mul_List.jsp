<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List for HR Sanction Proceedings Multiple Payee (Employee/Pensioner)</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Bill_Account_Heads.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" language="javascript">
    var Voucher_list_SL;

    function show(unitcode,offid,yr,mon,vou_no,major,minor)
    {
    	     if (Voucher_list_SL && Voucher_list_SL.open && !Voucher_list_SL.closed) 
    	     {
    		       Voucher_list_SL.resizeTo(500,500);
    		       Voucher_list_SL.moveTo(250,250); 
    		       Voucher_list_SL.focus();
    	     }
    	     else
    	     {
    	    	   Voucher_list_SL=null
    	     }
    	     
    	     Voucher_list_SL= window.open("../jsps/HR_Sanc_Proc_Mul__List_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+vou_no+"&majorcode="+major+"&minorcode="+minor,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
            	     
    	     Voucher_list_SL.moveTo(250,250);  
    	     Voucher_list_SL.focus();
         
    }
	  </script>
  </head>
  <body class="table">         
  <form>
 <%
  
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        Connection connection=null;
        ResultSet results=null;
        ResultSet rs1=null; 
        ResultSet rs2=null; 
   try
  {
  
            ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";
            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
       System.out.println("...............list all LISTjsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
     
           String sancdate = "";
           int txtCash_year=0,txtCash_Month_hid=0;   
           sancdate=request.getParameter("sancdate");System.out.println(sancdate);
           
           String[] sd=request.getParameter("sancdate").split("/");

           System.out.println("b4 getting month and year");
           try{txtCash_year=Integer.parseInt(sd[2]);}
           catch(Exception e){System.out.println("exception"+e );}
           System.out.println("txtCash_year "+txtCash_year);
           
           try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
           catch(Exception e){System.out.println("exception"+e );}
           System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
           int  cmbAcc_UnitCode=0,cmbOffice_code=0;
           int major_code=0,minor_code=0;
           try 
                {
                    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                }
           catch (Exception e) 
                {
                    System.out.println("Exception to catch cmbAcc_UnitCode ");
                }
           try 
               {
                    cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
               }       
           catch (Exception e) 
               {
                    System.out.println("Exception to catch cmbOffice_code ");
               }
           //added to get the minor type code depends on the major and minor---------30/01/2014
           try 
           {
        	   major_code = Integer.parseInt(request.getParameter("major_code"));
           }
      catch (Exception e) 
           {
               System.out.println("Exception to catch Major Code ");
           }
      try 
          {
    	  minor_code = Integer.parseInt(request.getParameter("minor_code"));
          }       
      catch (Exception e) 
          {
               System.out.println("Exception to catch Minor code ");
          }
          %>
  
 
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANCIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
              List of HR Sanction Proceedings Multiple Payee (Employee/Pensioner)
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
           <tr class="tdH">
               <th>Proceeding No </th>
               <th>Proceeding Date </th>
               <th>Account Head Code</th>
               <th>Balance Amount</th>
               <th>Remarks</th>
               <th>Show Details ?</th>
            </tr>
        <tbody id="tblList" class="table">
          <%
		try
                {
	                  ps=con.prepareStatement("SELECT SANCTION_PROCEEDING_NO,to_char(SANCTION_PROCEEDING_DATE,'dd/mm/yyyy') AS SANCTION_PROCEEDING_DATE,ACCOUNT_HEAD_CODE,TOTAL_SANCTION_AMOUNT,PARTICULARS FROM FAS_HR_SANC_PROC_MULTI_MST WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=?");
                      
                      ps.setInt(1,cmbAcc_UnitCode);
                      ps.setInt(2,cmbOffice_code);
                      ps.setInt(3,txtCash_year);
                      ps.setInt(4,txtCash_Month_hid);
                      results=ps.executeQuery();
	                  int cnt=0;
	                  while(results.next())
	                  {
	                	  
                                	  //String MajCode=results.getString("BILL_MINOR_TYPE_CODE");System.out.println(MajCode);
                                      //String MajDesc=results.getString("descr");System.out.println(MajDesc);
                                	  //String MinCode=results.getString("BILL_SUB_TYPE_CODE");System.out.println(MinCode);
                                      //String MinDesc=results.getString("BILL_SUB_TYPE_DESC");System.out.println(MinDesc);
	                                  cnt++;
	                                  out.println("<tr id=" + cnt + ">");
	                                  out.println("<td>"+results.getInt("SANCTION_PROCEEDING_NO")+"</td>");
	                                  out.println("<td>"+results.getString("SANCTION_PROCEEDING_DATE")+"</td>"); 
	                                  out.println("<td>"+results.getInt("ACCOUNT_HEAD_CODE")+"</td>");
	                                  out.println("<td>"+results.getString("TOTAL_SANCTION_AMOUNT")+"</td>"); 
	                                  out.println("<td>"+results.getString("PARTICULARS")+"</td>"); 
	                                  out.println("<td><a href='javascript:show("+cmbAcc_UnitCode+","+cmbOffice_code+","+txtCash_year+","+txtCash_Month_hid+"," +cnt+ ","+major_code+","+minor_code+")'>DETAILS</a></td>");
	                                  out.println("</tr>");	                                  
                        }
	                  if(cnt==0)
	                     {
	                        out.println("<tr><td colspan='8' align='center'>No data found</td></tr>");
	                     } 
         		}
                catch(Exception e)
            	{
	                System.out.println("Exception in Select:"+e);
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
                         onclick=" self.close();"></input>
                </div>
              </td>
            </tr>
      </table> 
    </form>
  </body>
</html>

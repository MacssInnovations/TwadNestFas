<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>List for HR Sanction Proceedings Single Payee</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Bill_Account_Heads.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" language="javascript">
	  function EditRender(rid)
		   {
		   	var major="",minor="",date="",pay="",hr="",sub="",num="",amt="",acchead="",rem="",sanc_no="";
		   	r=document.getElementById(rid);
		   	rcells=r.cells;		   		   	
		   	num=rid;
		   	sanc_no=document.getElementById("a"+rid).value;
		   
		   	//alert("selected sanction no::::::"+sanc_no);
		   	date=rcells.item(1).firstChild.nodeValue;
		   	major=rcells.item(2).firstChild.nodeValue;
		   	minor=rcells.item(3).firstChild.nodeValue;
		   	sub=rcells.item(4).firstChild.nodeValue;
		   	pay=rcells.item(5).firstChild.nodeValue;
		   	hr=rcells.item(6).firstChild.nodeValue;
		   	amt=rcells.item(7).firstChild.nodeValue;
		   	acchead=rcells.item(8).firstChild.nodeValue;
		   	rem=rcells.item(9).firstChild.nodeValue;
		   	alert(num+"    "+date+"    "+major+"    "+minor+"    "+sub+"    "+pay+"    "+hr+"    "+amt+"    "+acchead+"    "+rem+"    "+sanc_no);
		   //	close();
		   	opener.history(-1);
		//   	opener.goBack(num,date,major,minor,sub,pay,hr,amt,acchead,rem,sanc_no);        
		
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

           int cmbOffice_code=0,cmbAcc_UnitCode=0,txtCBYear=0,txtCBMonth=0;   
           cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
           cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
           txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));  
           txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth"));  
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
              List for HR Sanction Proceedings Single Payee
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
           <tr class="tdH">
               <th>Select </th>
               <th>Date</th>
               <th>Major Type Code </th>
               <th>Minor Type</th>
               <th>Sub Type</th>
               <th>Payee Type & Code</th>
               <th>No.of HR & Amt</th>
               <th>Sanc Amt</th>
               <th>Account Head Code & Desc</th>
               <th>Remarks</th>
            </tr>
        <tbody id="tblList" class="table">
          <%
		try
                {
			          String sql="SELECT SANCTION_PROCEEDING_NO,PAYEE_NAME,major,minor,sub,to_char(SANCTION_PROCEEDING_DATE,'dd/mm/yyyy') AS SANCTION_PROCEEDING_DATE,"+
            		  " (SELECT BILL_SUB_TYPE_DESC FROM FAS_BILL_SUB_TYPES WHERE BILL_SUB_TYPE_CODE=sub and BILL_MAJOR_TYPE_CODE=major AND BILL_MINOR_TYPE_CODE=minor)AS sub_desc,"+
            		  " (SELECT BILL_MINOR_TYPE_DESC FROM FAS_BILL_MINOR_TYPES_MST WHERE BILL_MAJOR_TYPE_CODE=major AND BILL_MINOR_TYPE_CODE  =minor) AS min_desc,"+
            		  "  PAYEE_TYPE,PAYEE_CODE,"+
            		  "  NO_OF_HR,HR_AMOUNT,TOTAL_SANCTION_AMOUNT,code,REMARKS,"+
            		  " (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=code)descr"+
            		  " FROM"+
            		  " (SELECT SANCTION_PROCEEDING_NO,PAYEE_NAME,SANCTION_PROCEEDING_DATE,"+
            		  "  BILL_MAJOR_TYPE_CODE                         AS major,"+
            		  "  BILL_MINOR_TYPE_CODE                         AS minor,"+
            		  "  BILL_SUB_TYPE_CODE                           AS sub,"+
            		  "  PAYEE_TYPE,PAYEE_CODE,NO_OF_HR,HR_AMOUNT,TOTAL_SANCTION_AMOUNT,ACCOUNT_HEAD_CODE as code,REMARKS"+
            		  "  FROM FAS_HR_SANC_PROC_MST where ACCOUNTING_UNIT_ID=? and accounting_for_office_id=? and cashbook_year=? and cashbook_month=?"+
            		  "  order by SANCTION_PROCEEDING_NO)";
	                  ps=con.prepareStatement(sql);System.out.println(sql);
                      ps.setInt(1,cmbAcc_UnitCode);
                      ps.setInt(2,cmbOffice_code);
                      ps.setInt(3,txtCBYear);
                      ps.setInt(4,txtCBMonth);
                      results=ps.executeQuery();
	                  int cnt=0;
	                  while(results.next())
	                  {

                                	  int MajCode=results.getInt("SANCTION_PROCEEDING_NO");
                                	  String payname=results.getString("PAYEE_NAME");
          							System.out.print(results.getString("PAYEE_NAME"));
	                                  cnt++;
	                                  out.println("<tr id=" + cnt + ">");
	                                  out.println("<td><a href='javascript:EditRender(" +cnt+ ")'>EDIT</a>");
	                                  out.println("<input type=hidden name=sancno id=a"+cnt+" value='"+MajCode+"'></input></td>");
	                                  
	                                  out.println("<td>"+results.getString("SANCTION_PROCEEDING_DATE")+"</td>");
	                                  out.println("<td>"+results.getInt("major")+"</td>");
	                                  if(results.getString("min_desc")==null)
	                                  {
	                                	  out.println("<td>"+results.getInt("minor")+"</td>");
	                                  }
	                                  else
	                                  {
	                                      out.println("<td>"+results.getInt("minor")+"-"+results.getString("min_desc")+"</td>");
	                                  }
	                                  if(results.getString("min_desc")==null)
	                                  {
	                                	  out.println("<td>"+results.getInt("sub")+"</td>");
	                                  }
	                                  else
	                                  {
	                                	  out.println("<td>"+results.getInt("sub")+"-"+results.getString("sub_desc")+"</td>");;
	                                  }
	                                  
	                                  out.println("<td>"+results.getString("PAYEE_TYPE")+"-"+results.getInt("PAYEE_CODE")+"-"+results.getString("PAYEE_NAME")+"</td>");
	                                  out.println("<td>"+results.getInt("NO_OF_HR")+"-"+results.getString("HR_AMOUNT")+"</td>");
	                                  
	                                  out.println("<td>"+results.getString("TOTAL_SANCTION_AMOUNT")+"</td>");
	                                  out.println("<td>"+results.getInt("code")+"-"+results.getString("descr")+"</td>");
	                                  out.println("<td>"+results.getString("REMARKS")+"</td>");
	                                  
	                                  out.println("</tr>");
                        }
	                  if(cnt==0)
	                     {
	                        out.println("<tr><td colspan='10' align='center'>No data found</td></tr>");
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

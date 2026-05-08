<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Sanction Proceedings (Single Payee) List</title>
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                               
                r=document.getElementById(rowID);
                rcells=r.cells;

                       
            	
            	//var cashyear=rcells.item(1).firstChild.nodeValue;
            	//var cashmonth=rcells.item(2).firstChild.nodeValue;
            	var majortype=document.getElementById("a"+rowID).value;	
				var minortype=document.getElementById("b"+rowID).value;	
				var subtype=document.getElementById("c"+rowID).value;	
				var payeetype=document.getElementById("payeetype"+rowID).value;	

				var pay=rcells.item(4).firstChild.nodeValue.split("-");
				
				//var payeecode=rcells.item(4).firstChild.nodeValue;
				var payeecode=pay[0];

				var refno=document.getElementById("refno"+rowID).value;
				var refdate=document.getElementById("refdate"+rowID).value;
				var sanctionno=rcells.item(5).firstChild.nodeValue;
				var prodate=rcells.item(6).firstChild.nodeValue;
				var sanctionauthority=document.getElementById("sanauthority"+rowID).value;
				var sanctionedby=document.getElementById("sanctionby"+rowID).value;	
				var head=rcells.item(7).firstChild.nodeValue.split("-");
				//	var headcode=rcells.item(7).firstChild.nodeValue;
				var headcode=head[0];
					var totalinstall=rcells.item(8).firstChild.nodeValue;
				
				var paymentunit=document.getElementById("paymentunit"+rowID).value;	
				var recovery=document.getElementById("recovery"+rowID).value;	
				var startmonth=document.getElementById("startmont"+rowID).value;	
				var residualamount=document.getElementById("residualamount"+rowID).value;
				var installno=document.getElementById("installno"+rowID).value;
				var emi=rcells.item(9).firstChild.nodeValue;
				var totalamount=rcells.item(10).firstChild.nodeValue;
				var remarks=document.getElementById("remarks"+rowID).value;
				var payment=document.getElementById("payment"+rowID).value;
                Minimize();
                opener.doParentsanction(majortype,minortype,subtype,payeetype,payeecode,refno,refdate,sanctionno,prodate,sanctionauthority,sanctionedby,headcode,totalinstall,paymentunit,recovery,startmonth,residualamount,installno,emi,totalamount,remarks,payment);
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
  </head>
  <body  bgcolor="rgb(255,255,225)">
   <%
     Connection con=null;
     ResultSet rs=null;
     PreparedStatement ps=null;
     PreparedStatement ps1,ps2,ps3;
     ResultSet rs1,rs2,rs3;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             System.out.println("Connected susscessfully in list file ");
   }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
        
  <form name="frmBillReceiptRegisterList" id="frmBillReceiptRegisterList">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
        <tr class="table">
          <td>
            <div align="center">
            Sanction Proceedings(Single Payee)  List
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
        
       
       <th>Bill Major Type</th>
        <th>Bill Minor Type</th>
            <th>Bill Sub Type</th>  
        <th>Payee Code</th>
         <th>Sanction Proceeding No</th>    
         <th>Sanction Proceeding Date</th>    
         
              <th>Account Head Code </th>    
               <th>Total Installment </th>
                <th>EMI </th>  
         <th>Total Sanction Amount </th>   
         <th>Remarks</th>         
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
        	   int unitId=Integer.parseInt(request.getParameter("unitid").trim());
        	   int officeId=Integer.parseInt(request.getParameter("officeid").trim());
        	   DecimalFormat df=new DecimalFormat("#0.00"); 
        	  // to_char(Date_effective_from,'dd/mm/yyyy') *for date conversion.....
        	  
            String sql_que="SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_NO,to_char(SANCTION_PROCEEDING_DATE,'dd/mm/yyyy') as SANCTION_PROCEEDING_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,REF_NO,to_char(REF_DATE,'dd/mm/yyyy') as REF_DATE,SANCTION_AUTHORITY,SANCTIONED_BY,ACCOUNT_HEAD_CODE,TOTAL_SANCTION_AMOUNT,PAYMENT_TO_BE_MADE_UNIT_ID,RECOVERY,RECOVERY_START_MONTH,RESIDUAL_AMOUNT,RESIDUAL_INSTL_NO,TOTAL_INSTALLMENT,EMI,REMARKS,PAYMENT  FROM FAS_SANC_PROC_MST1 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?";
            ps=con.prepareStatement(sql_que);  
            ps.setInt(1,unitId);
            ps.setInt(2,officeId);
            rs=ps.executeQuery();
            int cnt=0;  
          
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
               
                int majorType=rs.getInt("BILL_MAJOR_TYPE_CODE");
                int minorType=rs.getInt("BILL_MINOR_TYPE_CODE");
               int subType=rs.getInt("BILL_SUB_TYPE_CODE");
               
                ps1=con.prepareStatement("select BILL_MAJOR_TYPE_DESC  from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE=?");  
                ps1.setInt(1,majorType);
                
                rs1=ps1.executeQuery();
                rs1.next();
                out.println("<input type=hidden id=a"+cnt+" value="+majorType+">");
                out.println("<td>"+rs1.getString("BILL_MAJOR_TYPE_DESC")+"</td>");
               
                ps2=con.prepareStatement("select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MINOR_TYPE_CODE=?");  
                ps2.setInt(1,minorType);
                
                rs2=ps2.executeQuery();
                rs2.next();
                out.println("<input type=hidden id=b"+cnt+" value="+minorType+">");
                out.println("<td>"+rs2.getString("BILL_MINOR_TYPE_DESC")+"</td>");
                
                ps3=con.prepareStatement("select BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?");  
                ps3.setInt(1,majorType);
                ps3.setInt(2,minorType);
                ps3.setInt(3,subType);
                rs3=ps3.executeQuery();
                rs3.next();
                out.println("<input type=hidden id=c"+cnt+" value="+subType+">");
                out.println("<td>"+rs3.getString("BILL_SUB_TYPE_DESC")+"</td>");
                
                
                
                out.println("<input type=hidden id=payeetype"+cnt+" value="+rs.getString("PAYEE_TYPE")+">");
                 // out.println("<td>"+rs.getInt("PAYEE_CODE")+"</td>");
                  int EmpId=rs.getInt("PAYEE_CODE");
                  ps3=con.prepareStatement("select  A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME , b.designation_id from hrm_mst_employees a,hrm_emp_current_posting b where b.employee_id = a.employee_id and a.employee_id=?");  
                  ps3.setInt(1,EmpId);
                  rs3=ps3.executeQuery();
                  rs3.next();
                  out.println("<td>"+EmpId+"-"+rs3.getString("EMPLOYEE_NAME")+"</td>");  
                                  
                  
                  out.println("<input type=hidden id=refno"+cnt+" value="+rs.getInt("REF_NO")+">");
                  out.println("<input type=hidden id=refdate"+cnt+" value="+rs.getString("REF_DATE")+">");
                
                  out.println("<td align=center>"+rs.getInt("SANCTION_PROCEEDING_NO")+"</td>"); 
                  out.println("<td align=center>"+rs.getString("SANCTION_PROCEEDING_DATE")+"</td>"); 
                          
                  out.println("<input type=hidden id=sanauthority"+cnt+" value="+rs.getInt("SANCTION_AUTHORITY")+">");
                  out.println("<input type=hidden id=sanctionby"+cnt+" value="+rs.getInt("SANCTIONED_BY")+">");
                 // out.println("<td>"+rs.getInt("ACCOUNT_HEAD_CODE")+"</td>"); 
                  int headCode=rs.getInt("ACCOUNT_HEAD_CODE");
                  ps2=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");  
                  ps2.setInt(1,headCode);
                  
                  rs2=ps2.executeQuery();
                  rs2.next();               
                  out.println("<td>"+headCode+"-"+rs2.getString("ACCOUNT_HEAD_DESC")+"</td>"); 
                  
                  out.println("<td align=center>"+rs.getInt("TOTAL_INSTALLMENT")+"</td>"); 
                  
                  out.println("<input type=hidden id=paymentunit"+cnt+" value="+rs.getInt("PAYMENT_TO_BE_MADE_UNIT_ID")+">"); 
                 
                  out.println("<input type=hidden id=recovery"+cnt+" value="+rs.getString("RECOVERY")+">"); 
                  out.println("<input type=hidden id=startmont"+cnt+" value="+rs.getInt("RECOVERY_START_MONTH")+">"); 
                  out.println("<input type=hidden id=residualamount"+cnt+" value="+rs.getString("RESIDUAL_AMOUNT")+">"); 
                  out.println("<input type=hidden id=installno"+cnt+" value="+rs.getString("RESIDUAL_INSTL_NO")+">"); 
                  
                  out.println("<td align=right>"+df.format(rs.getInt("EMI"))+"</td>"); 
                 
                  out.println("<td align=right>"+df.format(rs.getInt("TOTAL_SANCTION_AMOUNT"))+"</td>"); 
                  String remarks=rs.getString("REMARKS");
                  out.println("<input type=hidden id=remarks"+cnt+" value="+remarks+">"); 
                  out.println("<input type=hidden id=payment"+cnt+" value="+rs.getString("PAYMENT")+">"); 
                  
                  out.println("<td>"+remarks+"</td>");
                
            }
             if(cnt==0)
             out.println("<tr><td align=center colspan=13>No data found<td></tr>");     
           }
          catch(Exception e)
          {
            out.println(e);
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
                     onclick="self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>
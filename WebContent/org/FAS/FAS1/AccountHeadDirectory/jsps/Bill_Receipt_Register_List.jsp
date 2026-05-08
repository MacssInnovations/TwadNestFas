<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Bill Receipt Register List</title>
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                var cmbAcc_UnitCode="", cmbOffice_code="",   cmbMas_SL_type="",   cmbMas_SL_Code="";
                var txtreceipt_date="", txtcash_book_year="",  txtcash_book_month="",  txtbill_no="" ;
                var txtbill_date="", txtbill_type="", txtbill_description="", txtbill_Amount="", txtreference_book="", txtRemarks="";
                
                r=document.getElementById(rowID);
                rcells=r.cells;
      
                cmbAcc_UnitCode = rcells.item(1).firstChild.nodeValue;
                cmbOffice_code  = rcells.item(2).firstChild.nodeValue;
                cmbMas_SL_type  = rcells.item(3).firstChild.nodeValue;
                cmbMas_SL_Code  = rcells.item(4).firstChild.nodeValue;
                
              if(rcells.item(5).firstChild.nodeValue!='--')
                txtreceipt_date = rcells.item(5).firstChild.nodeValue;
                
           /*   if(rcells.item(6).firstChild.nodeValue!='--')
                txtcash_book_year  = rcells.item(6).firstChild.nodeValue;
              if(rcells.item(7).firstChild.nodeValue!='--')
                txtcash_book_month = rcells.item(7).firstChild.nodeValue;
            */    
                
              txtbill_no      = rcells.item(6).firstChild.nodeValue;
                
              if(rcells.item(7).firstChild.nodeValue!='--')
                txtbill_date    = rcells.item(7).firstChild.nodeValue;
              if(rcells.item(8).firstChild.nodeValue!='--')
                txtbill_type    = rcells.item(8).firstChild.nodeValue;
              if(rcells.item(9).firstChild.nodeValue!='--')
                txtbill_description = rcells.item(9).firstChild.nodeValue;
              if(rcells.item(10).firstChild.nodeValue!='--')
                txtbill_Amount  = rcells.item(10).firstChild.nodeValue;
              if(rcells.item(11).firstChild.nodeValue!='--')
                txtreference_book   = rcells.item(11).firstChild.nodeValue;
              if(rcells.item(12).firstChild.nodeValue!='--')
                txtRemarks      = rcells.item(12).firstChild.nodeValue;
                
                            
        Minimize();
        opener.doParentBankAccNumbers(cmbAcc_UnitCode , cmbOffice_code , cmbMas_SL_type, cmbMas_SL_Code, txtreceipt_date,txtbill_no, txtbill_date, txtbill_type, txtbill_description, txtbill_Amount, txtreference_book, txtRemarks );
        self.close();
        //return true;
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
    
     Connection connection=null;

     ResultSet results=null;
     ResultSet results1=null;
     ResultSet results2=null;
    
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
              List of Bank Account Number 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Accounting Unit Code </th>
       <th>Accounting For Office Code </th>
       <th>Sub-Ledger Type </th>
       <th>Sub-Ledger Code </th>
       <th>Date of Receipt </th>
       <th>Bill No </th>
       <th>Bill Date</th>
       <th>Bill Type</th>
       <th>Bill Description</th>
       <th>Bill Amount</th>
       <th>M-Book Reference</th>       
       <th>Remarks</th>
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
           int cmbAcc_UnitCode=0, cmbOffice_code=0, cmbMas_SL_type=0, cmbMas_SL_Code=0;
           try{
           cmbAcc_UnitCode  = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
           cmbOffice_code   = Integer.parseInt(request.getParameter("cmbOffice_code"));
           cmbMas_SL_type   = Integer.parseInt(request.getParameter("cmbMas_SL_type"));
           cmbMas_SL_Code   = Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
           }catch(Exception e)
           {
                System.out.println("Exception in cmbAcc_UnitCode.."+cmbAcc_UnitCode);
           }
                     
            String sql_que="select ACCOUNTING_UNIT_ID , ACCOUNTING_FOR_OFFICE_ID , SUB_LEDGER_TYPE_CODE , SUB_LEDGER_CODE , BILL_NO , CASHBOOK_YEAR , CASHBOOK_MONTH , BILL_TYPE , to_char(BILL_DATE,'DD/MM/YYYY') as billdate , to_char(RECEIPT_DATE,'DD/MM/YYYY') as receiptdate   , BILL_DESCRIPTION , BILL_AMOUNT , MBOOK_REFERENCE , REMARKS from FAS_BILL_MOVEMENT_REGISTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?";
            ps=con.prepareStatement(sql_que);
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);
            ps.setInt(3,cmbMas_SL_type);
            ps.setInt(4,cmbMas_SL_Code);
                       
            rs=ps.executeQuery();
            int cnt=0;            
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                
                out.println("<td>"+rs.getLong("ACCOUNTING_UNIT_ID")+"</td>");
                out.println("<td>"+rs.getString("ACCOUNTING_FOR_OFFICE_ID")+"</td>");
                out.println("<td>"+rs.getInt("SUB_LEDGER_TYPE_CODE")+"</td>");
                out.println("<td>"+rs.getString("SUB_LEDGER_CODE")+"</td>");
               
                if(rs.getString("receiptdate")!=null)
                out.println("<td>"+rs.getString("receiptdate")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
                                 
                out.println("<td>"+rs.getString("BILL_NO")+"</td>");
                
                if(rs.getString("billdate")!=null)
                out.println("<td>"+rs.getString("billdate")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
                
                if(rs.getString("BILL_TYPE")!=null)
                out.println("<td>"+rs.getString("BILL_TYPE")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
                 
               if(rs.getString("BILL_DESCRIPTION")!=null)
                out.println("<td>"+rs.getString("BILL_DESCRIPTION")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
               
               
                if(!rs.getString("BILL_AMOUNT").equalsIgnoreCase("0"))
                out.println("<td>"+rs.getString("BILL_AMOUNT")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
               
               
                if(rs.getString("MBOOK_REFERENCE")!=null)
                out.println("<td>"+rs.getString("MBOOK_REFERENCE")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
               
                if(rs.getString("REMARKS")!=null)
                    out.println("<td>"+rs.getString("REMARKS")+"</td></tr>");
                else
                   out.println("<td>"+"--"+"</td></tr>");
                
            }
            if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td></tr>");     
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
                     onclick="self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>
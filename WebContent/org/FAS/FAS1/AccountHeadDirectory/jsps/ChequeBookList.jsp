<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Cheque Book List</title>
    <script type="text/javascript" src="../scripts/ChequeBookListScript.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  
   <script type="text/javascript" language="javascript">
    function call_OpenerCheque(id)
   {
   opener.ParentCheque(id);
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
  
  
  <% 
      System.out.println("...............list all jsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
        System.out.println("user id::"+empProfile.getEmployeeId());
      int empid=empProfile.getEmployeeId();
       //int empid=9315;
    System.out.println("empid"+empid);
   
    
        
   %>
   <%
          int  cmbAcc_UnitCode=0,cmbOffice_code=0;
   			long txtBankAc=0;

            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            	txtBankAc=Long.parseLong(request.getParameter("txtBankAc"));
                }catch(Exception e){System.out.println("Exception in getting req:"+e);}
                System.out.println(txtBankAc);
          %>
        
  <form name="ChequeBookList" id="ChequeBookList">
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
              &nbsp;List of ChequeBook 
                <input type="hidden" id="cmbAcc_UnitCode" name="cmbAcc_UnitCode" value="<%=cmbAcc_UnitCode%>" >
            <input type="hidden" id="cmbOffice_code" name="cmbOffice_code" value="<%=cmbOffice_code%>" >
            <input type="hidden" id="txtBankAc" name="txtBankAc" value="<%=txtBankAc%>" >
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       
       <th>
       Select
       </th>
       <th>
       Bank Name
       </th>
       <th>
       Account No
       </th>
       <th>
       Cheque Book Code
       </th>
       <th>
       No Of Leaves
       </th>
      
       <th>
       	Starting Leaf NO.
       </th>
       <th>
      	Ending Leaf NO.
       </th>
       <th>
      	Status
       </th>
      
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
         
           try
           {
          if(txtBankAc==0){
            ps=con.prepareStatement("SELECT cb.BANK_ID,(select bk.BANK_NAME from FAS_MST_BANKS bk where bk.BANK_ID=cb.BANK_ID)as bankName,cb.BRANCH_ID,cb.ACCOUNT_NO,cb.CHEQUE_BOOK_CODE,cb.NO_OF_LEAVES,cb.START_LEAF_NO,cb.END_LEAF_NO,cb.VERIFIED_BY,cb.STATUS FROM COM_MST_CHEQUE_BOOKS_SL cb WHERE cb.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND cb.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" ORDER BY cb.CHEQUE_BOOK_CODE ");
          }
          else
          {
        	  ps=con.prepareStatement("SELECT cb.BANK_ID,(select bk.BANK_NAME from FAS_MST_BANKS bk where bk.BANK_ID=cb.BANK_ID)as bankName,cb.BRANCH_ID,cb.ACCOUNT_NO,cb.CHEQUE_BOOK_CODE,cb.NO_OF_LEAVES,cb.START_LEAF_NO,cb.END_LEAF_NO,cb.VERIFIED_BY,cb.STATUS FROM COM_MST_CHEQUE_BOOKS_SL cb WHERE cb.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND cb.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and account_no="+txtBankAc+" ORDER BY cb.CHEQUE_BOOK_CODE ");
          }
            rs=ps.executeQuery();
           int cnt=0;            
            while(rs.next())
            {
               cnt++;
                int bankId=rs.getInt("BANK_ID");
             //   System.out.println("*****************"+bankId);
                String bankName=rs.getString("bankName");
             //   System.out.println("bankName::::"+bankName);
                 String ACCOUNT_NO=rs.getString("ACCOUNT_NO");
                 //int ACCOUNT_NO=rs.getInt("ACCOUNT_NO");
             //   System.out.println("ACCOUNT_NO*******"+ACCOUNT_NO);
                String Cheque_book_code=rs.getString("CHEQUE_BOOK_CODE");
              //   System.out.println(Cheque_book_code);
                int NoLeaves=rs.getInt("NO_OF_LEAVES");
              //   System.out.println(NoLeaves);
                 int StartLeaf=rs.getInt("START_LEAF_NO");
              //   System.out.println(StartLeaf);
                 int EndLeaf=rs.getInt("END_LEAF_NO");
               //  System.out.println(EndLeaf);
               
               // int userid=rs.getInt("VERIFIED_BY ");
                
                
                out.println("<tr id='" + Cheque_book_code + "'>");                
                if(rs.getString("STATUS").equalsIgnoreCase("C")){
                	  out.println("<td>CANCEL</td>");
                  }else{
                	  out.println("<td><a href=\"javascript:call_OpenerCheque('" + Cheque_book_code + "')\">Edit</a></td>");  
                  }
                out.println("<td>"+bankName+"</td>");
                out.println("<td>"+ACCOUNT_NO+"</td>");
                out.println("<td>"+Cheque_book_code+"</td>");
                out.println("<td>"+NoLeaves+ "</td>");
                out.println("<td>"+StartLeaf+"</td>");
                out.println("<td>"+EndLeaf+"</td>");
                if(rs.getString("STATUS").equalsIgnoreCase("L")){
                	  out.println("<td>LIVE</td>");  
                  }else{
                	  out.println("<td>CANCEL</td>");  
                  }
                out.println("</tr>");
               
            }
            if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td><td></td><td></td></tr>");
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
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>
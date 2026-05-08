<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>List for Insurance Details</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    
    function EditHead(rowID)
    {
    
                var dateentry="",finyr="",assetcode="",compdetails="",instype="";
                var favour="",pno="",cmncedate="",expdate="",insamount="",preamount="",othercharges="";
                var remarks="",renewaldate="",issuingoff="";
                   r=document.getElementById(rowID);
                 rcells=r.cells;
      
                 finyr=rcells.item(2).firstChild.nodeValue;
                // alert(rcells.item(1).firstChild.nodeValue);
                dateentry=rcells.item(1).firstChild.nodeValue;
                //alert(rcells.item(2).firstChild.nodeValue);
                
                assetcode=rcells.item(3).firstChild.nodeValue;
                compdetails=rcells.item(4).firstChild.nodeValue;
                instype=rcells.item(5).firstChild.nodeValue;
                favour=rcells.item(6).firstChild.nodeValue;
                pno=rcells.item(7).firstChild.nodeValue;
                cmncedate=rcells.item(8).firstChild.nodeValue;
                expdate=rcells.item(9).firstChild.nodeValue;
                insamount=rcells.item(10).firstChild.nodeValue;
                preamount=rcells.item(11).firstChild.nodeValue;
                //alert(rcells.item(11).firstChild.nodeValue);
                othercharges=rcells.item(12).firstChild.nodeValue;
                renewaldate=rcells.item(13).firstChild.nodeValue;
                issuingoff=rcells.item(14).firstChild.nodeValue;
                remarks=rcells.item(15).firstChild.nodeValue;
               
        Minimize();
    
      
        opener.doParentBankAccNumbers(dateentry,finyr,assetcode,compdetails,instype,favour,pno,cmncedate,expdate,insamount,preamount,othercharges,renewaldate,issuingoff,remarks);
      
   }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>
<script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmFas_holidays_List.txtCB_Year.value=year
        document.frmFas_holidays_List.txtCB_Month.value=month;
        
         }
    </script>
  </head>
  <body  bgcolor="rgb(255,255,225)">
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    PreparedStatement ps1=null;
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
   ResultSet rs1=null; 
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
      System.out.println("...............list all LISTjsp started.................");
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
     
       
           int  cmbAcc_UnitCode=0,cmbOffice_code=0;
            try
            {
         	   
           
         	   try {
                	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbAcc_UnitCode ");
                }
                try {
                	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbOffice_code ");
                }
              String  cmbFinancialYear = request.getParameter("cmbFinancialYear");
        System.out.println(cmbOffice_code);
        System.out.println(cmbFinancialYear);
       
        
            
   %>
  
 
        
  <form name="frmassetamclist" id="frmassetamclist">
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
              List of Insurance Details
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      <th>Select </th>
     
       <th> Date </th>
       <th>Financial Year</th>
       <th>Asset Code </th>
       <th>Insurance Company Name</th>
       <th>
           Type of Insurance
          </th>
          <th>
           Insurance in favour of
          </th>
     
       <th>
           Policy Number
          </th>
       <th>Date of Commencement</th>
       <th>Expiry Date</th>
       <th>Insurance Amount 
          </th>
          <th>
          Premium Amount
          </th>
         
         <th>Other Charges</th>
         <th>Renewal Date</th>
         <th>Issuing Office Address</th>
         <th>Remarks</th>
         </tr>
         <tbody id="tb" class="table" align="left">
          <%
          String dateentry=null;
          int assetcode=0;
          String finyr="",regdetails="",regno="",regdate="";
          String asset="",tank="",cubic="",regaddr="",make="",yrman="";
          String remarks="",road_tax="";
          Calendar c;
          
        
                
                String sql_que="select to_char(ENTER_DATE,'DD/MM/YYYY') as transdate,FINANCIAL_YEAR,ASSET_CODE,INS_COMP_NAME,INS_TYPE,INS_FAVOUR_OF,POLICY_NO,to_char(DATE_COMNCE,'DD/MM/YYYY') as comncedate,"+
                	           "to_char(EXPIRY_DATE,'DD/MM/YYYY') as expdate,INS_AMOUNT,PREMIUM_AMOUNT,OTHER_CHARGES,to_char(RENEWAL_DATE,'DD/MM/YYYY') as rendate,ISS_OFF_ADDRESS,"+
                	           "REMARKS FROM FAS_INSURANCE_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";
               
            System.out.println("sql_que::"+sql_que);
            ps=con.prepareStatement(sql_que);
            ps.setInt(1,cmbAcc_UnitCode);
            System.out.println("cmbAcc_UnitCode::"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code::"+cmbOffice_code);
            ps.setInt(2,cmbOffice_code);
            ps.setString(3,cmbFinancialYear);
            rs=ps.executeQuery();
            
           int cnt=0; 
          
            while(rs.next())
            {
               
                cnt++;
                System.out.println("inside");
                
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
               
                out.println("<td>"+rs.getString("transdate")+"</td>");
                System.out.println(rs.getString("transdate"));
                out.println("<td>"+rs.getString("FINANCIAL_YEAR")+"</td>");
                System.out.println(rs.getString("FINANCIAL_YEAR"));
                out.println("<td>"+rs.getInt("ASSET_CODE")+"</td>");
                System.out.println(rs.getString("ASSET_CODE"));
                out.println("<td>"+rs.getString("INS_COMP_NAME")+"</td>");
                System.out.println(rs.getString("INS_COMP_NAME"));
                out.println("<td>"+rs.getString("INS_TYPE")+"</td>");
                System.out.println(rs.getString("INS_TYPE"));
                out.println("<td>"+rs.getString("INS_FAVOUR_OF")+"</td>");
                System.out.println(rs.getString("INS_FAVOUR_OF"));
                out.println("<td>"+rs.getInt("POLICY_NO")+"</td>");
                System.out.println(rs.getInt("POLICY_NO"));
                out.println("<td >"+rs.getString("comncedate")+"</td>");
                System.out.println(rs.getString("comncedate"));
                out.println("<td>"+rs.getString("expdate")+"</td>");
                System.out.println(rs.getString("expdate"));
                out.println("<td>"+rs.getDouble("INS_AMOUNT")+"</td>");
                System.out.println(rs.getDouble("INS_AMOUNT"));
                out.println("<td>"+rs.getDouble("PREMIUM_AMOUNT")+"</td>");
                System.out.println(rs.getDouble("PREMIUM_AMOUNT"));
                out.println("<td>"+rs.getDouble("OTHER_CHARGES")+"</td>");
                System.out.println(rs.getDouble("OTHER_CHARGES"));
                out.println("<td>"+rs.getString("rendate")+"</td>");
                out.println("<td>"+rs.getString("ISS_OFF_ADDRESS")+"</td>");
                System.out.println(rs.getString("ISS_OFF_ADDRESS"));
                out.println("<td>"+rs.getString("REMARKS")+"</td>");
                System.out.println(rs.getString("REMARKS"));
                
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
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form>
   </body>
</html>


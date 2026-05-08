<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>General Ledger CB List</title>
    <script type="text/javascript" src="../scripts/ListofGeneralLedgerMainForm_OC.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   
  </head>
  <body  bgcolor="rgb(255,255,225)">
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    ResultSet rs1=null;
    PreparedStatement ps1=null;
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

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
  
  <% 
            HttpSession session=request.getSession(false);
            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
            int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0,SL_Type=0,Type_Code=0,acchead=0;
            String financeyear="";
            try{
             cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
             Cashbook_year=Integer.parseInt(request.getParameter("CashbookYear"));
            }catch(Exception e){System.out.println("Exception in CashbookYear:"+e);}
            try{
            Cashbook_month=Integer.parseInt(request.getParameter("CashbookMonth"));
            acchead=Integer.parseInt(request.getParameter("acchead"));
            }catch(Exception e){System.out.println("Exception in CashbookMonth:"+e);}
           
            financeyear= request.getParameter("txtFinanYr");            
            String queryStr ="";
            if(acchead!=0){
            	queryStr = "and ACCOUNT_HEAD_CODE='"+acchead+"'";
            }
        
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
              &nbsp;List of Closing Balance for General Ledger Account Heads
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
       <th>
       Select
       </th>
       <th>
      A/c Code
       </th>
       <th>
       A/c Head Desc
       </th>
      
       <th>
        Closing Balance
       </th>
       <th>
        Closing Balance Indicator
       </th>
    
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
         String AcHeadName="";
         String AcHeadCode="";
           try
           {
        	   
        	String sql=	"                                       "+
           			    "select              					"+ 
     		   		    " acc_head, 							"+
    		  		    " (SELECT account_head_desc				"+
    		  		    "  FROM com_mst_account_heads			"+
    		  		    "  WHERE account_head_code = acc_head	"+
    		  		    " ) as acc_head_name,					"+
    		  			" month_closing_balance,				"+
    		  			" month_closing_bal_dr_cr_ind,   		"+
    		  			" freeze_status 					   	"+
    					"from 									"+
    					"(										"+
    					"    select  							"+ 
   						"  *									"+
   						"from 									"+
   						"(										"+
   						" SELECT								"+ 
   						"     account_head_code as acc_head,	"+
   						"     month_closing_balance,			"+
   						"     month_closing_bal_dr_cr_ind		"+
   						" FROM fas_general_ledger_cb			"+
   						" WHERE accounting_unit_id = ?			"+
   						" AND accounting_for_office_id = ?		"+
   					//	" and FINANCIAL_YEAR='"+financeyear+"'"+
   						" AND YEAR=? 							"+
   						" AND MONTH = ?							"+
   							queryStr +
   						" ),(									"+
   						"select count(gl_status) as freeze_status from fas_gl_cb_status		"+
   						" where accounting_unit_id=? 			"+
   						" and cashbook_month=?					"+
   						" and cashbook_year=?					"+
   						" and gl_status='Y'						"+
   						") 										"+
    					")										"+
						"ORDER BY acc_head						";
            ps=con.prepareStatement(sql);
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);            
            ps.setInt(3,Cashbook_year);
            ps.setInt(4,Cashbook_month);
            ps.setInt(5,cmbAcc_UnitCode);            
            ps.setInt(6,Cashbook_month);
            ps.setInt(7,Cashbook_year);
            
            rs=ps.executeQuery();
            int cnt=0;         
            while(rs.next())
            {
                cnt++;  
                AcHeadCode=rs.getString("acc_head");                
                String MONTH_CLOSING_BALANCE=rs.getString("MONTH_CLOSING_BALANCE");
                String MONTH_CLOSING_BAL_DR_CR_IND=rs.getString("MONTH_CLOSING_BAL_DR_CR_IND");
                AcHeadName =rs.getString("acc_head_name");
                if(MONTH_CLOSING_BALANCE=="0")
                {
                  MONTH_CLOSING_BALANCE="";
                }
                else
                {
                  MONTH_CLOSING_BALANCE=MONTH_CLOSING_BALANCE;
                }
                              
                out.println("<tr id='" + AcHeadCode + "'>");   
                
               // System.out.println("----------->>>"+rs.getInt("freeze_status"));
                
                if ( rs.getInt("freeze_status")==1 ) 
                {
                	System.out.println("froze");
                	out.println("<td><font color='#ff2121'>Frozen</font></td>");
                }
                else
                {  
                	//System.out.println("not freeze");
                	out.println("<td><a href=\"javascript:loadTabAll('" + AcHeadCode + "')\">Edit</a></td>");
                }
                
                out.println("<td>"+AcHeadCode+"</td>");
                out.println("<td>"+AcHeadName+"</td>");               
                out.println("<td>"+MONTH_CLOSING_BALANCE+"</td>");
                out.println("<td>"+MONTH_CLOSING_BAL_DR_CR_IND+"</td>");
              
               
            }
            
                 rs.close();
                 
             if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td><td></td><td></td><td></td></tr>");
             
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
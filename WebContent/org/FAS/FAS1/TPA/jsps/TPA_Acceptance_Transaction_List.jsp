<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*,java.util.*,java.text.*,Servlets.Security.classes.UserProfile,Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>TPA Acceptance List</title>
    
    
   
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     
</head>
<body>
 <%
		    Connection con=null;
		 	ResultSet rs=null,rs2=null;
		 	PreparedStatement ps=null,ps2=null;
		  	ResultSet results=null;
		  	ResultSet results1=null;
		  	ResultSet results2=null;
		  	 DecimalFormat df=new DecimalFormat("#0.00");
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
 
		  	
		  int unitId=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		  int officeId=Integer.parseInt(request.getParameter("cmbOffice_code"));
		  int cashYear=Integer.parseInt(request.getParameter("cashyear"));
		  int cashMonth=Integer.parseInt(request.getParameter("cashmonth"));
		  int voucharNo=Integer.parseInt(request.getParameter("vno"));
		  	
	try{	  	
		  	
  %>
  

<table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <strong>List of TPA Acceptance</strong>
          </div>
        </td>
      </tr>
    </table>
    
    
   
    
<table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="left">
            <strong>SUB LEDGER</strong>
          </div>
        </td>
      </tr>
    </table>
    
     <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
           <tr class="table">
           <th>S.No</th>
           <th>Head Code</th>
           <th>SubLedger type</th>
          <th>SubLedger Code</th>
           <th>Amount</th>
           <th>Particulars</th>
           </tr>
            
            
      <%  ps=con.prepareStatement(" select a.*,b.account_head_desc, c.sub_ledger_type_desc from \n"+       
                   " ( select SL_NO,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS from FAS_TPA_TRANSACTION  where \n"+   
                   " accounting_unit_id=? AND  \n"+      
                   " accounting_for_office_id=? AND \n"+       
                   " CASHBOOK_YEAR=? AND    \n"+    
                   /* " CASHBOOK_MONTH=? AND  VOUCHER_NO=? and SL_NO!=1 and SUB_LEDGER_TYPE_CODE!=0)a \n"+ */
                   " CASHBOOK_MONTH=? AND  VOUCHER_NO=? and SUB_LEDGER_TYPE_CODE!=0)a \n"+ /* SNO condition is removed on 25/06/2019 by nanda */
                   " left outer join com_mst_account_heads b on a.account_head_code=b.account_head_code \n"+       
                   " left outer join com_mst_sl_types c on a.sub_ledger_type_code=c.sub_ledger_type_code order by a.SL_NO");
		  ps.setInt(1,unitId);
		  System.out.println("unitId==>"+unitId);
		  ps.setInt(2,officeId);
		  System.out.println("officeId==>"+officeId);
		  ps.setInt(3,cashYear);
		  System.out.println("cashYear==>"+cashYear);
		  ps.setInt(4,cashMonth);
		  System.out.println("cashMonth==>"+cashMonth);
		  ps.setInt(5,voucharNo);
		  
		  rs=ps.executeQuery();   
		  
          
		  while(rs.next()){
		  %> 
            <tr class="table"><td><%=rs.getInt("SL_NO") %></td>
            <td><%=rs.getInt("ACCOUNT_HEAD_CODE")+"-"+ rs.getString("account_head_desc")%></td>
         
         
         
         <%if(rs.getString("sub_ledger_type_desc")!=null) 
        out.print("<td>"+rs.getString("sub_ledger_type_desc")+"</td>");
         else
        	 out.print("<td>--</td>");
            if(rs.getInt("sub_ledger_type_code")!=0 && rs.getInt("sub_ledger_code")!=0)
                                 {
		                                SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
		                                ResultSet rs_get=obj_gen.getResult_General(unitId,officeId,rs.getInt("sub_ledger_type_code"),rs.getInt("sub_ledger_code"),0);
		                                String slcheck="";
		                                /*if(rs_get!=null)
		                                {
                                            rs_get.next();
                                            	
                                            	out.print("<td>"+rs_get.getString("cname") +"</td>");  				                                              
                                            
                                            rs_get.close();
		                                }
		                                else
		                                {
	                                        	out.print("<td>--</td>"); 
		                                }*/
		                                
		                                while(rs_get.next())
                                        {	if(rs_get.getInt("cid")==rs.getInt("sub_ledger_code") )
                                            {
                                        	slcheck=rs_get.getString("cname");
                                        	out.print("<td>"+slcheck +"</td>");
                                            }
                                        
                                        }
                                        rs_get.close();
                                        if(slcheck=="")
                                        {
                                        	out.print("<td>--</td>"); 
                                        }
		                                
                                 }
                                 else
                                	 		
                                	 	out.print("<td>--</td>");
            
            %>
            
            <td align="right"><%=df.format(rs.getBigDecimal("AMOUNT")) %></td>
            
             <%if(rs.getString("PARTICULARS")!=null) 
        out.print("<td>"+rs.getString("PARTICULARS")+"</td>");
         else
        	 out.print("<td></td>");
            %>
            
            </tr>
            
           <%} %> 
        
            </table>
            </div>
                        
		  
<table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="left">
            <strong>GENERAL LEDGER</strong>
          </div>
        </td>
      </tr>
    </table>	
			 <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
           <tr class="table">
           <th>S.No</th>
           <th>Head Code</th>
           <th>SubLedger type</th>
          <th>SubLedger Code</th>
           <th>Amount</th>
           <th>Particulars</th>
           </tr>
            
            
      <%  ps=con.prepareStatement(" select a.*,b.account_head_desc, c.sub_ledger_type_desc from \n"+       
                   " ( select SL_NO,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS from FAS_TPA_TRANSACTION  where \n"+   
                   " accounting_unit_id=? AND  \n"+      
                   " accounting_for_office_id=? AND \n"+       
                   " CASHBOOK_YEAR=? AND    \n"+    
                   /* " CASHBOOK_MONTH=? AND  VOUCHER_NO=? and SL_NO!=1 and SUB_LEDGER_TYPE_CODE=0)a \n"+ */   
                   " CASHBOOK_MONTH=? AND  VOUCHER_NO=?  and SUB_LEDGER_TYPE_CODE=0)a \n"+ /* SNO condition is removed on 25/06/2019 by nanda */
                   " left outer join com_mst_account_heads b on a.account_head_code=b.account_head_code \n"+       
                   " left outer join com_mst_sl_types c on a.sub_ledger_type_code=c.sub_ledger_type_code order by a.SL_NO");
		  ps.setInt(1,unitId);
		  ps.setInt(2,officeId);
		  ps.setInt(3,cashYear);
		  ps.setInt(4,cashMonth);
		  ps.setInt(5,voucharNo);
		  rs=ps.executeQuery();   
		  
          
		  while(rs.next()){
		  %> 
            <tr class="table"><td><%=rs.getInt("SL_NO") %></td>
            <td><%=rs.getInt("ACCOUNT_HEAD_CODE")+"-"+ rs.getString("account_head_desc")%></td>
         
         
         
         <%if(rs.getString("sub_ledger_type_desc")!=null) 
        out.print("<td>"+rs.getString("sub_ledger_type_desc")+"</td>");
         else
        	 out.print("<td>--</td>");
            if(rs.getInt("sub_ledger_type_code")!=0 && rs.getInt("sub_ledger_code")!=0)
                                 {
		                                SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
		                                ResultSet rs_get=obj_gen.getResult_General(unitId,officeId,rs.getInt("sub_ledger_type_code"),rs.getInt("sub_ledger_code"),0);
		                                if(rs_get!=null)
		                                {
                                            while(rs_get.next())
                                            {	
                                            	out.print("<td>"+rs_get.getString("cname") +"</td>");  				                                              
                                            }
                                            rs_get.close();
		                                }
		                                else
		                                {
	                                        	out.print("<td>--</td>"); 
		                                }
                                 }
                                 else
                                	 		
                                	 	out.print("<td>--</td>");
            
            %>
            
            <td align="right"><%=df.format(rs.getBigDecimal("AMOUNT")) %></td>
            
             <%if(rs.getString("PARTICULARS")!=null) 
        out.print("<td>"+rs.getString("PARTICULARS")+"</td>");
         else
        	 out.print("<td></td>");
            %>
            
            </tr>
            
           <%} %> 
            
            
            
            
            </table>
            </div>


<%}catch(Exception e){out.println(e);} %>







</body>
</html>
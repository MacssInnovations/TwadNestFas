<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>List of Subledger Closing Balance</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                var  cmbAcc_UnitCode="",cmbOffice_code="",Cashbook_year="",Cashbook_month="",accntcode="",accntdesc="",SL_TYPE="",SL_CODE="";
            var financeyear="",closebal="",closebalind="";
              //  var bank_AccNo="",branch_id="",bankid="",bank_acc_type="",operID="";
            //    var open_date="",init_dep_amt="",bal_date="",bal_amt="",remark="";
                 r=document.getElementById(rowID);
                 rcells=r.cells;
      
               
                 accntcode=rcells.item(1).firstChild.nodeValue;
                 accntdesc=rcells.item(2).firstChild.nodeValue;
                SL_TYPE=rcells.item(3).firstChild.nodeValue;
                 SL_CODE=rcells.item(5).firstChild.nodeValue;
                 closebal=rcells.item(7).firstChild.nodeValue;
                 closebalind=rcells.item(8).firstChild.nodeValue;
                // alert("test******"+closebalind);
        Minimize();
    
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentClosingSubLedger(accntcode,accntdesc,SL_TYPE,SL_CODE,closebal,closebalind);
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

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
  
 
        
  <form name="frmBankAccHeadsList" id="frmBankAccHeadsList">
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
              List of Sub Ledger Closing Balance
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
      SL Type
      </th>
       <th style="display:none"> SLType Id </th>
      <th>
        SL Code
      </th>
       <th style="display:none"> SLCode Id </th>
       <th>
        Closing Balance
       </th>
       <th>
        Closing Balance Indicator
       </th>
     
       </tr>
     
      
       <tbody id="tb" class="table" align="left">
          <%
           int  cmbAcc_UnitCode=0,cmbOffice_code=0,Cashbook_year=0,Cashbook_month=0,SL_TYPE=0,SL_CODE=0,slType=0,slCode=0,accHead=0;
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
            accHead=Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            slType=Integer.parseInt(request.getParameter("cmbMas_SL_type"));
            slCode=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
            }catch(Exception e){System.out.println("Exception in CashbookMonth:"+e);}
         financeyear= request.getParameter("txtFinanYr");
        String queryStr="";
        if(accHead!=0){
        	queryStr = "and a.ACCOUNT_HEAD_CODE='"+accHead+"'";
        }
        if((accHead!=0)&&(slType!=0)){
        	queryStr = "and a.ACCOUNT_HEAD_CODE='"+accHead+"' and a.SUB_LEDGER_TYPE_CODE='"+slType+"'";
        }
        if((accHead!=0)&&(slType!=0)&&(slCode!=0)){
        	queryStr = "and a.ACCOUNT_HEAD_CODE='"+accHead+"' and a.SUB_LEDGER_TYPE_CODE='"+slType+"' and a.SUB_LEDGER_CODE='"+slCode+"'";
        }
        try{
           
            String sql_que= "  							   "+
            " select 									   "+		
            "	  acc_head, 							   "+
            "	  SUB_LEDGER_TYPE_CODE,					   "+
            "	  SUB_LEDGER_CODE,						   "+
            "	  MONTH_CLOSING_BALANCE,				   "+
            "	  MONTH_CLOSING_BAL_DR_CR_IND,             "+
            "	  ACCOUNT_HEAD_DESC,					   "+
            "	  SUB_LEDGER_TYPE_DESC,					   "+
            "	  SL_CODENAME,							   "+
            "	  freeze_status							   "+
            "	from									 "+
            "	(											"+
            "	select										"+ 
            "	   a.ACCOUNT_HEAD_CODE as acc_head,			"+
            "	   a.SUB_LEDGER_TYPE_CODE,					"+
            "	   a.SUB_LEDGER_CODE,						"+
            "	   a.MONTH_CLOSING_BALANCE,					"+
            "	   a.MONTH_CLOSING_BAL_DR_CR_IND,			"+
            "	   b.ACCOUNT_HEAD_DESC,						"+
            "	   c.SUB_LEDGER_TYPE_DESC,					"+
            "	   d.SL_CODENAME 							"+
            "	from 										"+
            "	   FAS_SUB_LEDGER_MASTER_CB a,				"+
            "      COM_MST_ACCOUNT_HEADS b,					"+
            "	   COM_MST_SL_TYPES c,						"+
            "	   SL_TYPE_CODE_NAME_VIEW d					"+ 
            "	where 										"+
            "	      a.ACCOUNT_HEAD_CODE=b.ACCOUNT_HEAD_CODE			"+ 
            "	  and a.SUB_LEDGER_TYPE_CODE=c.SUB_LEDGER_TYPE_CODE		"+ 
            "	  and a.SUB_LEDGER_CODE=d.SL_CODE 						"+
            "	  and c.SUB_LEDGER_TYPE_CODE=d.SL_TYPE					"+             	 
            "	  and ACCOUNTING_UNIT_ID=? 								"+
            "	  and YEAR=?											"+
            "	  and month=? 											"+
            		queryStr +
            "	),														"+
            "	(														"+
            "	    select count(sl_status) as freeze_status from fas_sl_cb_status			"+
            "	    where accounting_unit_id=? 												"+
            "	    and cashbook_month=?													"+ 
            "	    and cashbook_year=? 													"+
            "	    and sl_status='Y'														"+ 
            "	)   																		";
            System.out.println("sql "+sql_que);
            ps=con.prepareStatement(sql_que);
            
            ps.setInt(1,cmbAcc_UnitCode);           
            ps.setInt(2,Cashbook_year);
            ps.setInt(3,Cashbook_month);
            ps.setInt(4,cmbAcc_UnitCode); 
            ps.setInt(5,Cashbook_month);
            ps.setInt(6,Cashbook_year);        
            rs=ps.executeQuery();
           int cnt=0;            
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");
                
                if ( rs.getInt("freeze_status")==1 ) 
                {                
                	out.println("<td><font color='#ff2121'>Frozen</font></td>");                
                }
                else
                {
                	out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                }           
                
                out.println("<td>"+rs.getInt("acc_head")+"</td>");
                out.println("<td>"+rs.getString("ACCOUNT_HEAD_DESC")+"</td>");
                out.println("<td style='display:none'>"+rs.getInt("SUB_LEDGER_TYPE_CODE")+"</td>");
                out.println("<td>"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</td>");
                out.println("<td style='display:none'>"+rs.getInt("SUB_LEDGER_CODE")+"</td>");
                out.println("<td>"+rs.getString("SL_CODENAME")+"</td>");               
                out.println("<td>"+rs.getInt("MONTH_CLOSING_BALANCE")+"</td>");
                out.println("<td>"+rs.getString("MONTH_CLOSING_BAL_DR_CR_IND")+"</td>");
            }
            if(cnt==0)
             out.println("<tr><td>No data found<td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
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

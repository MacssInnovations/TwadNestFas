<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>BRS update Master List</title>
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../scripts/BRS_status.js"></script>
     
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
                
                recno = rowID;
                
                acc_unitid  = rcells.item(1).firstChild.nodeValue;
               
                off_id  = rcells.item(2).firstChild.nodeValue;
               
                BRS_ym  = rcells.item(3).firstChild.nodeValue;        
               
                TB_ym  = rcells.item(4).firstChild.nodeValue; 
              
                TB_freezedate = rcells.item(5).firstChild.nodeValue;       
               
                TB_status  = rcells.item(6).firstChild.nodeValue;        
               
                ACCOUNT_NO = rcells.item(7).firstChild.nodeValue;  
                
                
                Minimize();
                opener.doParentBRSMaster(recno,acc_unitid,off_id,BRS_ym,TB_ym,TB_freezedate,TB_status,ACCOUNT_NO);
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
  <body>
   <%
     Connection con=null;
     ResultSet rs=null;
     PreparedStatement ps=null;
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
             System.out.println("Connected susscessfully in list file ");
   }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
        
  <form name="frmBRSupdateList" id="frmBRSupdateList" method="" action="">
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
              List of BRS Update Master
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       <th>Select </th>
       <th>Accounting unit id</th>
       <th>Office id</th>
       <th>BRS completed for the yesr/month</th>
       <th>TB to be freezed upto year/month</th>
       <th>TB Freeze Date</th>
       <th>TB Freeze Status</th>
       <th>ACCOUNT_NO</th>
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
        	   int acUnit=Integer.parseInt(request.getParameter("acc_unit").trim());
        	   int officeId=Integer.parseInt(request.getParameter("office_id").trim());
        	   String acc_unitname=request.getParameter("acc_unitname");
                   String office_name=request.getParameter("office_name");
        	  // to_char(Date_effective_from,'dd/mm/yyyy') *for date conversion.....
        	  
         //   String sql_que="SELECT TB_YEAR,TB_MONTH,to_char(TB_FREEZE_DATE,'dd/mm/yyyy') as TB_FREEZE_DATE,"+
           // "TB_FREEZE,BRS_YEAR,BRS_MONTH,ACCOUNT_NO from BRS_STATUS_UPDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?";
                    String sql_que="select * from(SELECT TB_YEAR,TB_MONTH, TO_CHAR(TB_FREEZE_DATE,'dd/mm/yyyy') AS TB_FREEZE_DATE," +
                    		  " TB_FREEZE, BRS_YEAR, BRS_MONTH, ACCOUNT_NO  FROM BRS_STATUS_UPDATE WHERE ACCOUNTING_UNIT_ID    =? " +
                    		  " AND ACCOUNTING_FOR_OFFICE_ID=?)a LEFT OUTER JOIN (SELECT bank_id,  BRANCH_ID, bank_ac_no," +
                    		  " AC_OPERATIONAL_MODE_ID,trim(bank_ac_no::varchar)||'-'||trim(AC_OPERATIONAL_MODE_ID) AS acc_no " +
                    		  " FROM fas_mst_bank_balance WHERE ACCOUNTING_UNIT_ID = ? AND status               ='Y')b " +
                    		  " on a.ACCOUNT_NO = b.bank_ac_no::varchar ";
        			  
            ps=con.prepareStatement(sql_que);  
            ps.setInt(1,acUnit);
            ps.setInt(2,officeId);
            ps.setInt(3,acUnit);
            rs=ps.executeQuery();
            int cnt=0;  
            String connectionType="";
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                if(rs.getString("TB_FREEZE").equalsIgnoreCase("Y"))
                {
                    out.println("<td>----</td>");
                }
                else if(rs.getString("TB_FREEZE").equalsIgnoreCase("N"))
                {
                    out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                }
                out.println("<td>"+acUnit+"</td>");
                out.println("<td>"+officeId+"</td>");
                if(rs.getInt("BRS_MONTH")==1 || rs.getInt("BRS_MONTH")==2 || rs.getInt("BRS_MONTH")==3 || rs.getInt("BRS_MONTH")==4 || rs.getInt("BRS_MONTH")==5 || rs.getInt("BRS_MONTH")==6 || rs.getInt("BRS_MONTH")==7 || rs.getInt("BRS_MONTH")==8 || rs.getInt("BRS_MONTH")==9)
                {
                    out.println("<td>"+"0"+rs.getInt("BRS_MONTH")+"/"+rs.getInt("BRS_YEAR")+"</td>");
                }
                else if (rs.getInt("BRS_MONTH")==10 || rs.getInt("BRS_MONTH")==11 || rs.getInt("BRS_MONTH")==12)
                {
                    out.println("<td>"+rs.getInt("BRS_MONTH")+"/"+rs.getInt("BRS_YEAR")+"</td>");
                }
                if(rs.getInt("TB_MONTH")==1 || rs.getInt("TB_MONTH")==2 || rs.getInt("TB_MONTH")==3 || rs.getInt("TB_MONTH")==4 || rs.getInt("TB_MONTH")==5 || rs.getInt("TB_MONTH")==6 || rs.getInt("TB_MONTH")==7 || rs.getInt("TB_MONTH")==8 || rs.getInt("TB_MONTH")==9)
                {
                    out.println("<td>"+"0"+rs.getInt("TB_MONTH")+"/"+rs.getInt("TB_YEAR")+"</td>");
                }
                else if (rs.getInt("TB_MONTH")==10 || rs.getInt("TB_MONTH")==11 || rs.getInt("TB_MONTH")==12)
                {
                    out.println("<td>"+rs.getInt("TB_MONTH")+"/"+rs.getInt("TB_YEAR")+"</td>");
                }
                out.println("<td>"+rs.getString("TB_FREEZE_DATE")+"</td>");
                out.println("<td>"+rs.getString("TB_FREEZE")+"</td>"); 
                out.println("<td>"+rs.getString("acc_no")+"</td>");    
            }
             if(cnt==0)
             out.println("<tr><td align=center colspan=10>No data found<td></tr>");     
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
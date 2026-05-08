<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>Scheme Independent Centage Account Head List</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                var txtWorkExpACHeadCode ="";
                var txtWorkExpACHeadCode_Desc = "";
                var txtDftCentageCtgy = "";
                var txtDftCentageCtgy_Desc = "";
                var txtCrACHeadCode = "";
                var txtCrACHeadCode_Desc ="";
                var txtDrACHeadCode = "";
                var txtDrACHeadCode_Desc ="";
                var txtRemarks ="";
                
                r=document.getElementById(rowID);
                rcells=r.cells;
      
                txtWorkExpACHeadCode = rcells.item(1).firstChild.nodeValue;
                txtWorkExpACHeadCode_Desc = rcells.item(2).firstChild.nodeValue;                
                txtDftCentageCtgy  = rcells.item(3).firstChild.nodeValue;
                txtDftCentageCtgy_Desc  = rcells.item(4).firstChild.nodeValue;                
                txtCrACHeadCode  = rcells.item(5).firstChild.nodeValue;
                txtCrACHeadCode_Desc  = rcells.item(6).firstChild.nodeValue;                
                txtDrACHeadCode  = rcells.item(7).firstChild.nodeValue;
                txtDrACHeadCode_Desc  = rcells.item(8).firstChild.nodeValue;                
                txtRemarks  = rcells.item(9).firstChild.nodeValue;
                            
        Minimize();
        opener.doParentBankAccNumbers(txtWorkExpACHeadCode, txtWorkExpACHeadCode_Desc, txtDftCentageCtgy, txtDftCentageCtgy_Desc, txtCrACHeadCode, txtCrACHeadCode_Desc, txtDrACHeadCode, txtDrACHeadCode_Desc, txtRemarks );
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
              Scheme Independent Centage Account Head List
            </div></td>
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
       
       <th>Select </th>
       <th>Work Expediture A/C Code </th>
       <th>Default Centage Category </th>
       <th>Credit A/C Code </th>
       <th>Debit A/C Code </th>
       <th>Remarks </th>
       
       
       </tr>
       <tbody id="tb" class="table" align="left">
          <%    
           try
           {
           
            int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
             
          
String sql_que="select \n" + 
"* \n" + 
"from \n" + 
"(\n" + 
"SELECT WEXP_ACCOUNT_HEAD_CODE,\n" + 
"  (SELECT account_head_desc\n" + 
"  FROM COM_MST_ACCOUNT_HEADS\n" + 
"  WHERE account_head_code = WEXP_ACCOUNT_HEAD_CODE\n" + 
"  )AS wexp_desc,\n" + 
"  DEFAULT_CENTAGE_CATEGORY,\n" + 
"  DEFAULT_CENTAGE_CATEGORY_type,\n" + 
"  CR_ACCOUNT_HEAD_CODE,\n" + 
"  (SELECT account_head_desc\n" + 
"  FROM COM_MST_ACCOUNT_HEADS\n" + 
"  WHERE account_head_code = CR_ACCOUNT_HEAD_CODE\n" + 
"  )AS dr_desc,\n" + 
"  DR_ACCOUNT_HEAD_CODE,\n" + 
"  (SELECT account_head_desc\n" + 
"  FROM COM_MST_ACCOUNT_HEADS\n" + 
"  WHERE account_head_code = DR_ACCOUNT_HEAD_CODE\n" + 
"  )AS cr_desc,\n" + 
"  REMARKS\n" + 
"FROM FAS_INDE_CENTAGE_AC_HEADS\n" + 
"WHERE ACCOUNTING_UNIT_ID     = ?\n" + 
"AND ACCOUNTING_FOR_OFFICE_ID = ?\n" + 
")x \n" + 
"\n" + 
"left outer join \n" + 
"\n" + 
"\n" + 
"(\n" + 
"   \n" + 
"SELECT \n" + 
"      CATEGORY_CODE, \n" + 
"      CATEGORY_DESC, \n" + 
"      CATEGORY_TYPE, ( category_type || category_code ) as cat_code_type      \n" + 
"  FROM FAS_CENTAGE_CATEGORY_HO_MASTER\n" + 
"  WHERE \n" + 
"      ACCOUNTING_UNIT_ID       = ?\n" + 
"  AND ACCOUNTING_FOR_OFFICE_ID = ?\n" + 
"  and CATEGORY_TYPE='S' \n" + 
"\n" + 
"  union all \n" + 
"  \n" + 
"  SELECT \n" + 
"      CATEGORY_CODE, \n" + 
"      CATEGORY_DESC, \n" + 
"      CATEGORY_TYPE,(category_type || category_code ) as cat_code_type      \n" + 
"  FROM FAS_CENTAGE_CATEGORY_HO_MASTER\n" + 
"  WHERE \n" + 
"      CATEGORY_TYPE='C'        \n" + 
") y \n" + 
"on x.DEFAULT_CENTAGE_CATEGORY= y.CATEGORY_CODE  and \n" + 
"   x.DEFAULT_CENTAGE_CATEGORY_type =y.CATEGORY_TYPE  \n" + 
"  ";
            
            ps=con.prepareStatement(sql_que);   
            
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);
            
            ps.setInt(3,cmbAcc_UnitCode);
            ps.setInt(4,cmbOffice_code);
           
            rs=ps.executeQuery();
            int cnt=0;            
            while(rs.next())
            {
                cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");                
                
                
                out.println("<td style='display:none' >"+rs.getString("WEXP_ACCOUNT_HEAD_CODE")+"</td>");                
                out.println("<td style='display:none' >"+rs.getString("wexp_desc")+"</td>"); 
                
                out.println("<td style='display:none' >"+rs.getString("cat_code_type")+"</td>");                
                out.println("<td style='display:none' >"+rs.getString("CATEGORY_DESC")+"</td>");                
                
                out.println("<td style='display:none' >"+rs.getString("CR_ACCOUNT_HEAD_CODE")+"</td>");                                             
                out.println("<td style='display:none' >"+rs.getString("cr_desc")+"</td>");                                             
                
                out.println("<td style='display:none' >"+rs.getString("DR_ACCOUNT_HEAD_CODE")+"</td>");                  
                out.println("<td style='display:none' >"+rs.getString("dr_desc")+"</td>");                  
                
                out.println("<td style='display:none' >"+rs.getString("REMARKS")+"</td>");                      
                
                
                out.println("<td>"+rs.getInt("WEXP_ACCOUNT_HEAD_CODE")+" - "+rs.getString("wexp_desc")+"</td>");
                out.println("<td>"+rs.getString("cat_code_type")+" - " +rs.getString("CATEGORY_DESC")+"</td>");                
                out.println("<td>"+rs.getInt("CR_ACCOUNT_HEAD_CODE")+" - "+rs.getString("cr_desc")+"</td>");                                             
                out.println("<td>"+rs.getInt("DR_ACCOUNT_HEAD_CODE")+" - "+rs.getString("dr_desc")+"</td>");
                out.println("<td>"+rs.getString("REMARKS")+"</td>");                                             
                
                
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
                     onclick="self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>
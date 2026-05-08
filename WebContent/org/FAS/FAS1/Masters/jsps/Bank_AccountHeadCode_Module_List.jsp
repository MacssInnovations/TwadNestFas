<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Account Number with Account Head Modules</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                
                var bank_AccNo="",branch_id="",bankid="",bank_acc_type="",operID="";
                var acchead="",acchead_desc="",moduleID="",CRDR_ind="",remark="";
                var bankid_name="",branch_id_name="",bank_acc_type_name="",operID_name="",defaultvalue="";
                 r=document.getElementById(rowID);
                 rcells=r.cells;
      
               /*  alert(rcells.item(1).firstChild.nodeValue);
                 alert(rcells.item(2).firstChild.nodeValue);
                 alert("bankid.."+rcells.item(3).firstChild.nodeValue);
                 alert(rcells.item(4).firstChild.nodeValue);
                 alert("brit.."+rcells.item(5).firstChild.nodeValue);
                 alert(rcells.item(6).firstChild.nodeValue);
                 alert("aty.."+rcells.item(7).firstChild.nodeValue);
                 alert(rcells.item(8).firstChild.nodeValue);
                 alert("op.."+rcells.item(9).firstChild.nodeValue);
                 alert(rcells.item(10).firstChild.nodeValue);
                 alert(rcells.item(11).firstChild.nodeValue);
                 alert(rcells.item(12).firstChild.nodeValue);
                 alert(rcells.item(13).firstChild.nodeValue);
                 alert(rcells.item(14).firstChild.nodeValue);
                 */
                 bank_AccNo=rcells.item(1).firstChild.nodeValue;
                 bankid_name=rcells.item(2).firstChild.nodeValue;
                 bankid=rcells.item(3).firstChild.nodeValue;
                 branch_id_name=rcells.item(4).firstChild.nodeValue;
                 branch_id=rcells.item(5).firstChild.nodeValue;
                 bank_acc_type_name=rcells.item(6).firstChild.nodeValue;
                 bank_acc_type=rcells.item(7).firstChild.nodeValue;
                 operID_name=rcells.item(8).firstChild.nodeValue;
                 operID=rcells.item(9).firstChild.nodeValue;
                 //if(rcells.item(10).firstChild.nodeValue!='--')
                     acchead_desc=rcells.item(10).firstChild.nodeValue;
                 //if(rcells.item(11).firstChild.nodeValue!='--')
                    acchead=rcells.item(11).firstChild.nodeValue;
                 //if(rcells.item(12).firstChild.nodeValue!='--')
                    //bal_date=rcells.item(12).firstChild.nodeValue;
                 //if(rcells.item(13).firstChild.nodeValue!='--')
                    moduleID=rcells.item(13).firstChild.nodeValue;
                 
                   CRDR_ind=rcells.item(14).firstChild.nodeValue;
                if(rcells.item(15).firstChild.nodeValue!='--')
                   remark=rcells.item(15).firstChild.nodeValue;
                defaultvalue=rcells.item(17).firstChild.nodeValue;
        Minimize();
        
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentBankAccNo_Head_module(bank_AccNo,branch_id,bankid,bank_acc_type,operID,acchead,moduleID,CRDR_ind,remark,bankid_name,branch_id_name,bank_acc_type_name,operID_name,acchead_desc,defaultvalue);
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
              List of Bank Account Number with Account Head Modules
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      <th>Select </th>
       <th>Bank Account Number </th>
       <th>Bank Name </th>
        <th style="display:none"> Bank Id </th>
       <th>Branch Name </th>
        <th style="display:none"> Branch Id </th>
       <th>Account type </th>
       <th style="display:none"> Account type Id </th>
       <th>Operational mode </th>
       <th style="display:none"> Operational Id </th>
       <th>Account Head description </th>
       <th style="display:none"> Account Head Code Id </th>
       <th>Module type </th>
       <th style="display:none"> Module type Id </th>
       <th>CR/DR indicator</th>
       <th>Remarks </th>
       <th> Default </th>
        <th style="display:none" > Default value</th>
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
           try
           {
           int cmbAcc_UnitCode=0;
           try{
           cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
           System.out.println("cmbAcc_UnitCode.."+cmbAcc_UnitCode);
           }catch(Exception e)
           {
                System.out.println("Exception in cmbAcc_UnitCode.."+cmbAcc_UnitCode);
           }
           
           String sql_que= "select bb.BANK_ID,bb.BRANCH_ID,bk.BANK_NAME,br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BRANCH_CITY,"
            +" trim(bb.BANK_AC_NO) as BankAccNumber,trim(bb.BANK_AC_TYPE_ID) as accTypeId,trim(bb.AC_OPERATIONAL_MODE_ID) as oper_ID,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE ," 
            +" bb.AC_HEAD_CODE,h.ACCOUNT_HEAD_DESC,bb.MODULE_ID,bb.CR_DR_TYPE,"
            +" decode(bb.MODULE_ID,'MF004','Receipt system','MF005','Payment system','MF006','Remittance system','MF015','Fund Transfer system','MF008','Self-Cheque system','MF009','Fund Receipt system','MF010','Inter-Bank Transfer system','module not found') as module_type, "
            +" bb.REMARKS,SL_NO from FAS_OFFICE_BANK_AC_CURRENT bb,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t,FAS_MST_AC_OPER_MODES m,COM_MST_ACCOUNT_HEADS h "
            +" where  bb.STATUS='Y' and ACCOUNTING_UNIT_ID=? and t.ACCOUNT_TYPE_ID=bb.BANK_AC_TYPE_ID and bb.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID"
            +" and bb.BANK_ID=br.BANK_ID and bb.BRANCH_ID=br.BRANCH_ID and bb.BANK_ID=bk.BANK_ID and bb.AC_HEAD_CODE=h.ACCOUNT_HEAD_CODE order by module_type,bb.CR_DR_TYPE";
            ps=con.prepareStatement(sql_que);
            ps.setInt(1,cmbAcc_UnitCode);
            rs=ps.executeQuery();
           int cnt=0;            
            while(rs.next())
            {
               cnt++;
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                out.println("<td>"+rs.getLong("BankAccNumber")+"</td>");
                out.println("<td>"+rs.getString("BANK_NAME")+"</td>");
                out.println("<td style='display:none'>"+rs.getInt("BANK_ID")+"</td>");
                out.println("<td>"+rs.getString("BRANCH_CITY")+"</td>");
                out.println("<td style='display:none'>"+rs.getInt("BRANCH_ID")+"</td>");
                out.println("<td>"+rs.getString("ACCOUNT_TYPE")+"</td>");
                out.println("<td style='display:none'>"+rs.getString("accTypeId")+"</td>");
                out.println("<td>"+rs.getString("AC_OPERATIONAL_MODE")+"</td>");
                out.println("<td style='display:none'>"+rs.getString("oper_ID")+"</td>");
                out.println("<td>"+rs.getInt("AC_HEAD_CODE")+"--"+rs.getString("ACCOUNT_HEAD_DESC")+"</td>");
                out.println("<td style='display:none'>"+rs.getInt("AC_HEAD_CODE")+"</td>");
                out.println("<td>"+rs.getString("module_type")+"</td>");
                out.println("<td style='display:none'>"+rs.getString("MODULE_ID")+"</td>");
                out.println("<td>"+rs.getString("CR_DR_TYPE")+"</td>");
            
                if(rs.getString("REMARKS")!=null)
                    out.println("<td>"+rs.getString("REMARKS")+"</td>");
                else
                   out.println("<td>"+"--"+"</td>");
                if(rs.getInt("SL_NO")==1)
                  {
                    out.println("<td>YES</td>");
                    out.println("<td style='display:none'>1</td>");
                 }
                else
                 {
                    out.println("<td>NO</td>");
                    out.println("<td style='display:none'>0</td>");
                  }  
                  out.println("</tr>");
            }
            System.out.println("count.."+cnt);
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
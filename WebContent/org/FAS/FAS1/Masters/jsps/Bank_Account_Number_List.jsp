<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Account Number List</title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function EditHead(rowID)
    {
                //alert("rowID***"+rowID);
                var bank_AccNo="",branch_id="",bankid="",bank_acc_type="",operID="";
                var open_date="",init_dep_amt="",bal_date="",bal_amt="",remark="",status="";
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
                 bankid=rcells.item(3).firstChild.nodeValue;
                 branch_id=rcells.item(5).firstChild.nodeValue;
                 //alert("branch_id>>>"+branch_id);
                 bank_acc_type=rcells.item(7).firstChild.nodeValue;
                 operID=rcells.item(9).firstChild.nodeValue;
                 if(rcells.item(10).firstChild.nodeValue!='--')
                     open_date=rcells.item(10).firstChild.nodeValue;
                 if(rcells.item(11).firstChild.nodeValue!='--')
                    init_dep_amt=rcells.item(11).firstChild.nodeValue;
                 if(rcells.item(12).firstChild.nodeValue!='--')
                    bal_date=rcells.item(12).firstChild.nodeValue;
                 if(rcells.item(13).firstChild.nodeValue!='--')
                    bal_amt=rcells.item(13).firstChild.nodeValue;
                 if(rcells.item(14).firstChild.nodeValue!='--')
                   remark=rcells.item(14).firstChild.nodeValue;
                   
                   status=rcells.item(15).firstChild.nodeValue;
                   
     //   Minimize();
    
        //alert(accHeadCode,accHeadDesc,bankid,operID)
        opener.doParentBankAccNumbers(bank_AccNo,branch_id,bankid,bank_acc_type,operID,open_date,init_dep_amt,bal_date,bal_amt,remark,status);
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
              List of Bank Account Number 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" >
       
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
       <th>Opening date </th>
       <th>Initial deposit </th>
       <th>Balance as on date </th>
       <th>Balance amount </th>
       <th>Remarks </th>
       <th > Status </th>
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
           try
           {
          //  ps=con.prepareStatement("
           int cmbAcc_UnitCode=0,cmbBankId=0,cmbBranchId=0;
           String sql_que=null,cmbBankAcc_type=null;
           try{
           cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
         //  System.out.println("cmbAcc_UnitCode..from parent js to child"+cmbAcc_UnitCode);
           }catch(Exception e)
           {
                System.out.println("Exception in cmbAcc_UnitCode.."+cmbAcc_UnitCode);
           }
           try{
           cmbBankId=Integer.parseInt(request.getParameter("cmbBankId"));
       //    System.out.println("cmbBankId..from parent js to child"+cmbBankId);
           }catch(Exception e)
           {
                System.out.println("Exception in cmbBankId.."+cmbBankId);
           }
           try{
           cmbBranchId=Integer.parseInt(request.getParameter("cmbBranchId"));
          System.out.println("cmbBranchId..from parent js to child"+cmbBranchId);
           }catch(Exception e)
           {
                System.out.println("Exception in cmbBranchId.."+cmbBranchId);
           }
           try{
           cmbBankAcc_type=request.getParameter("cmbBankAcc_type");
           
         //  System.out.println("cmbBankAcc_type..here is>>>>>>"+cmbBankAcc_type);
           }catch(Exception e)
           {
                System.out.println("Exception in cmbBankAcc_type.."+cmbBankAcc_type);
           }
           
              
           if(cmbBankId==0 && cmbBranchId==0){
             sql_que=
                "select                                                                              \n" + 
               "       bb.BANK_ID,                                                                   \n" + 
               "       bb.BRANCH_ID,\n" + 
               "       bk.BANK_NAME,\n" + 
               "       br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BRANCH_CITY,\n" + 
               "       trim(bb.BANK_AC_NO::varchar) as BankAccNumber,\n" + 
               "       trim(bb.BANK_AC_TYPE_ID::varchar) as accTypeId,\n" + 
               "       trim(bb.AC_OPERATIONAL_MODE_ID::varchar) as oper_ID,t.ACCOUNT_TYPE,bb.AC_OPERATIONAL_MODE_ID as AC_OPERATIONAL_MODE,"+ 
               "       to_char(bb.AC_OPENING_DATE,'DD/MM/YYYY') as opening_date,\n" + 
               "       trim(bb.INITIAL_DEPOSIT_AMT::varchar) as init_dep_amt,\n" + 
               "       to_char(bb.BALANCE_DATE,'DD/MM/YYYY') as bal_date,\n" + 
               "       trim(bb.OPENING_BALANCE::varchar) as open_bal,bb.CLOSING_BALANCE,\n" + 
               "       bb.REMARKS, \n" + 
               "       bb.status as stat  \n" + 
               "from \n" + 
               "      FAS_MST_BANK_BALANCE bb,\n" + 
               "      FAS_MST_BANKS bk,\n" + 
               "      FAS_MST_BANK_BRANCHES br,\n" + 
               "      FAS_MST_BANK_AC_TYPES t\n" + 
              // "      FAS_MST_AC_OPER_MODES m \n" + 
               "where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+"  and t.ACCOUNT_TYPE_ID=bb.BANK_AC_TYPE_ID \n" + 
               "             and bb.BANK_ID=br.BANK_ID and bb.BRANCH_ID=br.BRANCH_ID and bb.BANK_ID=bk.BANK_ID\n";
          }
          else if(cmbBranchId==0)
          {
                     sql_que=
                "select                                                                              \n" + 
               "       bb.BANK_ID,                                                                   \n" + 
               "       bb.BRANCH_ID,\n" + 
               "       bk.BANK_NAME,\n" + 
               "       br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BRANCH_CITY,\n" + 
               "       trim(bb.BANK_AC_NO::varchar) as BankAccNumber,\n" + 
               "       trim(bb.BANK_AC_TYPE_ID::varchar) as accTypeId,\n" + 
               "       trim(bb.AC_OPERATIONAL_MODE_ID::varchar) as oper_ID,t.ACCOUNT_TYPE,bb.AC_OPERATIONAL_MODE_ID as AC_OPERATIONAL_MODE,"+ 
               "       to_char(bb.AC_OPENING_DATE,'DD/MM/YYYY') as opening_date,\n" + 
               "       trim(bb.INITIAL_DEPOSIT_AMT::varchar) as init_dep_amt,\n" + 
               "       to_char(bb.BALANCE_DATE,'DD/MM/YYYY') as bal_date,\n" + 
               "       trim(bb.OPENING_BALANCE::varchar) as open_bal,bb.CLOSING_BALANCE,\n" + 
               "       bb.REMARKS, \n" + 
               "       bb.status as stat  \n" + 
               "from \n" + 
               "      FAS_MST_BANK_BALANCE bb,\n" + 
               "      FAS_MST_BANKS bk,\n" + 
               "      FAS_MST_BANK_BRANCHES br,\n" + 
               "      FAS_MST_BANK_AC_TYPES t\n" + 
              
               "       WHERE ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+" \n" + 
               " and bk.BANK_ID="+cmbBankId+"  \n" +
                " AND t.ACCOUNT_TYPE_ID        =bb.BANK_AC_TYPE_ID AND bb.BANK_ID               =br.BANK_ID AND bb.BRANCH_ID             =br.BRANCH_ID  AND bb.BANK_ID               =bk.BANK_ID";
          }
         // else if(cmbBankAcc_type.equalsIgnoreCase("null"))
          else if(cmbBankId!=0 && cmbBranchId!=0 && cmbBankAcc_type==null)
          {  
          //System.out.println("33333333333333 loop");
           sql_que=
                "select                                                                              \n" + 
               "       bb.BANK_ID,                                                                   \n" + 
               "       bb.BRANCH_ID,\n" + 
               "       bk.BANK_NAME,\n" + 
               "       br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BRANCH_CITY,\n" + 
               "       trim(bb.BANK_AC_NO::varchar) as BankAccNumber,\n" + 
               "       trim(bb.BANK_AC_TYPE_ID::varchar) as accTypeId,\n" + 
               "       trim(bb.AC_OPERATIONAL_MODE_ID::varchar) as oper_ID,t.ACCOUNT_TYPE,bb.AC_OPERATIONAL_MODE_ID as AC_OPERATIONAL_MODE,"+
               //" m.AC_OPERATIONAL_MODE , \n" + 
               "       to_char(bb.AC_OPENING_DATE,'DD/MM/YYYY') as opening_date,\n" + 
               "       trim(bb.INITIAL_DEPOSIT_AMT::varchar) as init_dep_amt,\n" + 
               "       to_char(bb.BALANCE_DATE,'DD/MM/YYYY') as bal_date,\n" + 
               "       trim(bb.OPENING_BALANCE::varchar) as open_bal,bb.CLOSING_BALANCE,\n" + 
               "       bb.REMARKS, \n" + 
               "       bb.status as stat  \n" + 
               "from \n" + 
               "      FAS_MST_BANK_BALANCE bb,\n" + 
               "      FAS_MST_BANKS bk,\n" + 
               "      FAS_MST_BANK_BRANCHES br,\n" + 
               "      FAS_MST_BANK_AC_TYPES t\n" + 
             //  "      FAS_MST_AC_OPER_MODES m \n" + 
               "       WHERE ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+" \n" + 
               " and bk.BANK_ID="+cmbBankId+" and br.BRANCH_ID="+cmbBranchId+" \n" + 
               " AND t.ACCOUNT_TYPE_ID        =bb.BANK_AC_TYPE_ID AND  "+
              // " bb.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID AND 
               " bb.BANK_ID               =br.BANK_ID AND bb.BRANCH_ID             =br.BRANCH_ID  AND bb.BANK_ID               =bk.BANK_ID";
          }
          else 
          {
         //  System.out.println("444444444 loop");
           sql_que=
                "select                                                                              \n" + 
               "       bb.BANK_ID,                                                                   \n" + 
               "       bb.BRANCH_ID,\n" + 
               "       bk.BANK_NAME,\n" + 
               "       br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BRANCH_CITY,\n" + 
               "       trim(bb.BANK_AC_NO::varchar) as BankAccNumber,\n" + 
               "       trim(bb.BANK_AC_TYPE_ID::varchar) as accTypeId,\n" + 
               "       trim(bb.AC_OPERATIONAL_MODE_ID::varchar) as oper_ID,t.ACCOUNT_TYPE,bb.AC_OPERATIONAL_MODE_ID as AC_OPERATIONAL_MODE,"+ 
               "       to_char(bb.AC_OPENING_DATE,'DD/MM/YYYY') as opening_date,\n" + 
               "       trim(bb.INITIAL_DEPOSIT_AMT::varchar) as init_dep_amt,\n" + 
               "       to_char(bb.BALANCE_DATE,'DD/MM/YYYY') as bal_date,\n" + 
               "       trim(bb.OPENING_BALANCE::varchar) as open_bal,bb.CLOSING_BALANCE,\n" + 
               "       bb.REMARKS, \n" + 
               "       bb.status as stat  \n" + 
               "from \n" + 
               "      FAS_MST_BANK_BALANCE bb,\n" + 
               "      FAS_MST_BANKS bk,\n" + 
               "      FAS_MST_BANK_BRANCHES br,\n" + 
               "      FAS_MST_BANK_AC_TYPES t\n" + 
              
               "       WHERE ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+" \n" + 
               " and bk.BANK_ID="+cmbBankId+" and br.BRANCH_ID="+cmbBranchId+" and t.ACCOUNT_TYPE_ID='"+cmbBankAcc_type+"' \n"+
               " AND t.ACCOUNT_TYPE_ID        =bb.BANK_AC_TYPE_ID AND bb.BANK_ID               =br.BANK_ID AND bb.BRANCH_ID             =br.BRANCH_ID  AND bb.BANK_ID               =bk.BANK_ID";
          }
           System.out.println(sql_que);
            ps=con.prepareStatement(sql_que);
          //  ps.setInt(1,cmbAcc_UnitCode);
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
              
                if(rs.getString("opening_date")!=null)
                out.println("<td>"+rs.getString("opening_date")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
                
            
                if(rs.getString("init_dep_amt")!=null)
                out.println("<td>"+rs.getString("init_dep_amt")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
               
                if(rs.getString("bal_date")!=null)
                out.println("<td>"+rs.getString("bal_date")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
               
               
                if(!rs.getString("open_bal").equalsIgnoreCase("0"))
                out.println("<td>"+rs.getString("open_bal")+"</td>");
                else
                out.println("<td>"+"--"+"</td>");
               
                if(rs.getString("REMARKS")!=null)
                    out.println("<td>"+rs.getString("REMARKS")+"</td>");
                else
                   out.println("<td>"+"--"+"</td>");              
               
               out.println("<td>"+rs.getString("stat")+"</td></tr>");
                
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
    </form></body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title> List for Numerical OB of Assets </title>
    
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    
    function EditHead(rowID)
    {
    // alert("inside edithead");
                var issueddate="",majorclass="",assetcode="",assetdesc="",issuedoffice="",quantity="",jvno="",jvdate="",value1="";
                var status="",aliascode="";
                var remarks="";
                   r=document.getElementById(rowID);
                 rcells=r.cells;
      
                //alert(rcells.item(1).firstChild.nodeValue);
                majorclass=rcells.item(2).firstChild.nodeValue;
               // alert("majorclass:"+majorclass);
                var majorclasscode=rcells.item(1).firstChild.nodeValue;
                //alert("majorclasscode:"+majorclasscode);
                assetcode=rcells.item(3).firstChild.nodeValue;
               // alert("assetcode==="+assetcode);
                assetdesc=rcells.item(4).firstChild.nodeValue;
                //cashbookyr=rcells.item(5).firstChild.nodeValue;
                //cashbookmon=rcells.item(6).firstChild.nodeValue;
                var levelid = rcells.item(6).firstChild.nodeValue;
                //alert(levelid);
                issueddate=rcells.item(5).firstChild.nodeValue;
                issuedoffice=rcells.item(7).firstChild.nodeValue;
                quantity=rcells.item(8).firstChild.nodeValue;
                value1=rcells.item(9).firstChild.nodeValue;
                jvno=rcells.item(10).firstChild.nodeValue;
                jvdate=rcells.item(11).firstChild.nodeValue;
                remarks=rcells.item(12).firstChild.nodeValue;
               
                var officecode=rcells.item(13).firstChild.nodeValue;
             //alert("majorclass"+majorclass);
             //alert("assetcode"+assetcode);
             //alert("assetdesc"+assetdesc);
             //alert("issueddate"+issueddate);
             //alert("quantity"+quantity);
             //alert("jvno"+jvno);
             //alert("jvdate"+jvdate);
             //alert("remarks"+remarks);
             //alert("issuedoffice"+issuedoffice);
               
        Minimize();
    
      
        opener.doParentBankAccNumbers(majorclasscode,majorclass,assetcode,assetdesc,issueddate,levelid,issuedoffice,quantity,jvno,jvdate,remarks,officecode,value1);
      
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
       
        document.frmassetnumOB.txtCB_Year.value=year
        document.frmassetnumOB.txtCB_Month.value=month;
        
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
     
       
           int  cmbAcc_UnitCode=0,cmbOffice_code=0,txtCB_Year=0,txtCB_Month=0;
           String cmbFinancialYear="";
            try
            {
            	cmbFinancialYear=request.getParameter("cmbFinancialYear");
           
         	   try {
                	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbAcc_UnitCode ");
                }
                try {
                	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                } catch (Exception e) {
                    System.out.println("Exception to catch cmbOffice_code");
                }
                try{
                	txtCB_Year= Integer.parseInt(request.getParameter("txtCB_Year"));
                }catch(Exception e){
                	e.printStackTrace();
                }
                try{
                	txtCB_Month= Integer.parseInt(request.getParameter("txtCB_Month"));
                }catch(Exception e){
                	e.printStackTrace();
                }
   //    System.out.println(cmbOffice_code);
   //    System.out.println(cmbFinancialYear);
   //     System.out.println(cmbAcc_UnitCode);
   //     System.out.println(txtCB_Year);
   //     System.out.println(txtCB_Month);    
   %>
  
 
        
  <form name="frmassetnumOB" id="frmassetnumOB">
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
              List for Issue Numerical Ac Assets 
            </div></td>
            
        </tr>
       
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      <th>Select </th>
      
          
            <th>
           Major Classification of Asset
          </th>
           <th style="display:none"> Major Id </th> 
           <th>Asset Code</th>
           <th>Asset Desc</th>
           <th>
           Issued Date
          </th>
          <th>
            Asset is sent to office
          </th>
                    <th>
            Quantity Issued 
          </th>
            <th>
            Value Issued 
          </th>
       <th>
            TDA Ori Journal NO
          </th>
       <th>
            TDA Ori Journal Date
          </th>
         <th>
            Remarks
          </th>
         </tr>
         <tbody id="tb" class="table" align="left">
          <%
          String dateentry=null;
          int assetcode=0;
          String finyr="",regdetails="",regno="",regdate="";
          String asset="",tank="",cubic="",regaddr="",make="",yrman="";
          String remarks="",road_tax="";
          Calendar c;
          
        
                
                // String sql_que="select a.ASSET_CODE,a.ASSET_DESC,to_char(a.ISSUE_DATE,'DD/MM/YYYY') as entrydate,a.ASSET_MAJOR_CLASS_CODE," +
                //               " a.QTY_ISSUED,a.REMARKS, "+
                 //              " case "+
                //			   " when a.tda_originating_jvr_date is null "+ 
                //			   " then '--' "+ 
            	//			   " else "+ 
                //			   " to_char(a.tda_originating_jvr_date,'DD/MM/YYYY') "+ 
            	//			   " end as jvdate,	"+
            	//			   " a.ISSUED_TO_LEVEL_ID,"+
                 //              " a.TDA_ORIGINATING_JVR_NO as jvno,c.OFFICE_NAME as issuedoffice,b.ASSET_MAJOR_CLASS_DESC  " + 
                 //              " FROM FAS_ISSUEOF_ASSETS a,FAS_ASSET_CLASSIFICATION b,COM_MST_OFFICES c where a.ASSET_MAJOR_CLASS_CODE=b.ASSET_MAJOR_CLASS_CODE " +
                  //             " and a.ISSUED_TO_OFFICE=c.OFFICE_ID  and a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and a.FINANCIAL_YEAR=? and a.CASHBOOK_YEAR=? and a.CASHBOOK_MONTH=? ";
            				   
            String sql_que=" SELECT a.ASSET_CODE, "+
            "  a.ASSET_DESC,  "+
            "   TO_CHAR(a.ISSUE_DATE,'DD/MM/YYYY') AS entrydate, "+
            " a.ASSET_MAJOR_CLASS_CODE, "+
            "  a.QTY_ISSUED, a.VALUE_ISSUED,"+
            " a.REMARKS, "+
            " CASE "+
            "   WHEN a.tda_originating_jvr_date IS NULL "+
              "   THEN '--' "+
              "   ELSE TO_CHAR(a.tda_originating_jvr_date,'DD/MM/YYYY') "+
              "   END AS jvdate, "+
            "  a.issued_to_level_id, "+
            "  a.tda_originating_jvr_no as jvno, "+
            "   a.issued_to_office, "+
            "  c.particulars, "+
           " d.office_name            as issuedoffice, "+
           " b.ASSET_MAJOR_CLASS_DESC "+
           "  from fas_issueof_assets a inner join "+
          "  FAS_MST_ASSETS_CLASS b  "+
          "  on  b.ASSET_MAJOR_CLASS_CODE=a.asset_major_class_code "+
            "   inner join fas_a52_register c "+
            " on a.asset_code=c.asset_code "+
          " and a.accounting_for_office_id=c.accounting_unit_office_id "+
        //  " and a.financial_year=c.financial_year "+
          " and a.ASSET_MAJOR_CLASS_CODE=c.ASSET_MAJOR_CLASS_CODE "+
          " inner join com_mst_offices d "+
          " on a.issued_to_office        =d.office_id  "+
          " and a.accounting_unit_id      =? "+
        		  " and a.accounting_for_office_id=? "+
        		  " and a.financial_year          =? "+
        		  "  AND a.CASHBOOK_YEAR           =? "+
          " AND a.CASHBOOK_MONTH          =?";
            
            ps=con.prepareStatement(sql_que);
            ps.setInt(1, cmbAcc_UnitCode);
            ps.setInt(2, cmbOffice_code);
            ps.setString(3,cmbFinancialYear);
            ps.setInt(4,txtCB_Year);
            ps.setInt(5,txtCB_Month);
            rs=ps.executeQuery();
            
           int cnt=0; 
           String amcperiod="";
          // amcperiod=rs.
           
            while(rs.next())
            {
               
                cnt++;

               
                out.println("<tr id='" + cnt + "'>");   
                out.println("<td><a href=\"javascript:EditHead('" + cnt + "')\">Edit</a></td>");
                out.println("<td style='display:none'>"+rs.getInt("ASSET_MAJOR_CLASS_CODE")+"</td>");
              //  System.out.println("clas:::"+rs.getInt("ASSET_MAJOR_CLASS_CODE"));
                out.println("<td>"+rs.getString("ASSET_MAJOR_CLASS_DESC")+"</td>");
                //System.out.println(rs.getString("ASSET_MAJOR_CLASS_DESC"));
                out.println("<td>"+rs.getInt("ASSET_CODE")+"</td>");
                //System.out.println(rs.getString("ASSET_CODE"));
                out.println("<td>"+rs.getString("ASSET_DESC")+"</td>");
                out.println("<td>"+rs.getString("entrydate")+"</td>");
                out.println("<td style='display:none'>"+rs.getString("ISSUED_TO_LEVEL_ID")+"</td>"); 
                System.out.println(" level id  "+rs.getString("ISSUED_TO_LEVEL_ID"));
                out.println("<td>"+rs.getString("issuedoffice")+"</td>");
                out.println("<td>"+rs.getString("QTY_ISSUED")+"</td>");
                out.println("<td>"+rs.getString("VALUE_ISSUED")+"</td>");
               // System.out.println(rs.getString("QTY_ISSUED"));
                out.println("<td>"+rs.getInt("jvno")+"</td>");
                out.println("<td>"+rs.getString("jvdate")+"</td>");
                //System.out.println(rs.getString("jvdate"));
                out.println("<td>"+rs.getString("REMARKS")+"</td>");
                //System.out.println(rs.getString("REMARKS"));
                out.println("<td style='display:none' >"+rs.getString("ISSUED_TO_OFFICE")+"</td>");
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


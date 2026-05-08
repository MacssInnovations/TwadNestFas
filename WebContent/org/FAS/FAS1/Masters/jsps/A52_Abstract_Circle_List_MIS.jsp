<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>  
    <meta http-equiv="cache-control" content="no-cache">
    <title>A52 Abstract MIS Circle</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          var Office_list_SL;
          function Show(unitid,assetcode,finyr)
          {
              if (Office_list_SL && Office_list_SL.open && !Office_list_SL.closed) 
              {
                 Office_list_SL.resizeTo(500,500);
                 Office_list_SL.moveTo(250,250); 
                 Office_list_SL.focus();
              }
              else
              {
                  Office_list_SL=null;
              }
              Office_list_SL= window.open("A52_Abstract_Office_List_MIS.jsp?unitid="+unitid+"&assetcode="+assetcode+"&finyr="+finyr,"A52_Abstract_Office","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
              Office_list_SL.moveTo(250,250);  
              Office_list_SL.focus();
              
          }

          window.onunload=function()
          {
          if (Office_list_SL && Office_list_SL.open && !Office_list_SL.closed) Office_list_SL.close();
          }
          </script>
  </head>
  <body class="table"><form name="A52_Abstract_Circle_List_Form" method="POST">
      <%
  Connection con=null;
  ResultSet rs=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  int  assetcode=0;String assetdesc="",finyr="";
            try{
            	assetcode=Integer.parseInt(request.getParameter("assetcode"));
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            	assetdesc=request.getParameter("assetdesc");
            }catch(Exception e){System.out.println("Exception in getting req:"+e);}
            try{
            	finyr=request.getParameter("finyr");
               }catch(Exception e){System.out.println("Exception in getting req:"+e);}
           
            
  %>
      <table cellspacing="3" cellpadding="2" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>A52 Abstract Details</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th>Asset Desc</th>
          <th>Circle Name</th>
          <th>Closing Qty</th>
          <th>Closing Value</th>
          <th>Book Value</th>  
          <th>View</th>             
        </tr>
        <tbody id="tbody" class="table">
          <%
          ResultSet rs2=null,rs3=null;
          PreparedStatement ps2=null,ps3=null,ps4=null;
           try
           {
           
          String sql=" select asset_major_class_desc, asset_major_class_code,unitname,accounting_unit_id, "+
          "  closing_qty,closing_value, "+
        	  "  bookvalue,depre_allowed_yr,(closing_value-depre_allowed_yr)as balance "+
        	  "  from  "+

        	  "  (select unitname,asset_major_class_desc, asset_major_class_code,accounting_unit_id,  "+
        			  "  (n_open_bal_qty+n_reciepts_year_qty-n_issues_year_qty)as closing_qty,  "+
        	  "  (n_opening_bal_value+n_reciepts_yr_value-n_issues_yr_value)as closing_value, "+ 
        	    "  net_depre_cost as bookvalue,depre_allowed_yr  "+ 
        	   "  from  "+
        	   " (select asset_major_class_code,accounting_unit_id, "+
        			   "  (SELECT cc.Accounting_Unit_Name "+
        			  "   FROM fas_mst_acct_units cc "+
        	    "   WHERE CC.ACCOUNTING_UNIT_ID=A.ACCOUNTING_UNIT_ID "+
        	    "   )AS unitname, "+
        	    "  (SELECT bb.ASSET_MAJOR_CLASS_DESC  "+
        			   "   FROM FAS_MST_ASSETS_CLASS bb  "+
        	    "   WHERE BB.ASSET_MAJOR_CLASS_CODE=A.ASSET_MAJOR_CLASS_CODE  "+
        	    "   )AS asset_MAJOR_CLASS_DESC,  "+
        	    "  decode(SUM(OPEN_BAL_QTY),null,0,SUM(OPEN_BAL_QTY))AS OPEN_BAL_QTY,  "+
        	   "   decode(SUM(OPENING_BAL_VALUE),null,0,SUM(OPENING_BAL_VALUE))AS OPENING_BAL_VALUE, "+ 
        	   " decode(SUM(RECIEPTS_YEAR_QTY),null,0,SUM(RECIEPTS_YEAR_QTY))AS RECIEPTS_YEAR_QTY,  "+
        	      "    decode(SUM(RECIEPTS_YR_VALUE),null,0,SUM(RECIEPTS_YR_VALUE))AS RECIEPTS_YR_VALUE,  "+
        	   "   decode(SUM(ISSUES_YEAR_QTY),null,0,SUM(ISSUES_YEAR_QTY))AS ISSUES_YEAR_QTY,  "+
        	   "  decode(sum(issues_yr_value),null,0,sum(issues_yr_value)) as issues_yr_value,  "+
        	   "  decode(sum(n_open_bal_qty),null,0,sum(n_open_bal_qty))as n_open_bal_qty,  "+
        	   "  decode(sum(n_opening_bal_value),null,0,sum(n_opening_bal_value))as n_opening_bal_value,  "+
        	   "  decode(sum(n_reciepts_year_qty),null,0,sum(n_reciepts_year_qty))as n_reciepts_year_qty,  "+
        	   "  decode(sum(n_reciepts_yr_value),null,0,sum(n_reciepts_yr_value))as n_reciepts_yr_value,  "+
        	   "  decode(sum(n_issues_year_qty),null,0,sum(n_issues_year_qty))as n_issues_year_qty,  "+
        	   "  decode(SUM(N_ISSUES_YR_VALUE),null,0,SUM(N_ISSUES_YR_VALUE)) AS N_ISSUES_YR_VALUE,  "+
        	   "  decode(SUM(DEP_PREV_YEAR),null,0,SUM(DEP_PREV_YEAR))AS DEP_PREV_YEAR,  "+
        	   "  decode(SUM(DEPRE_REC_AC),null,0,SUM(DEPRE_REC_AC))AS DEPRE_REC_AC,  "+
        	   "  decode(SUM(DEPRE_ALLOWED_YR),null,0,SUM(DEPRE_ALLOWED_YR))AS DEPRE_ALLOWED_YR, "+
        	   "  decode(SUM(DEPRE_TR_AC),null,0,SUM(DEPRE_TR_AC))AS DEPRE_TR_AC, "+
        	   "  decode(SUM(DEPRE_UPTO_DATE),null,0,SUM(DEPRE_UPTO_DATE))AS DEPRE_UPTO_DATE,  "+
        	   "  DECODE(SUM(NET_DEPRE_COST),NULL,0,SUM(NET_DEPRE_COST))AS NET_DEPRE_COST,  "+
        	   "  DECODE(SUM(APP_PRE_YR),NULL,0,SUM(APP_PRE_YR))AS APP_PRE_YR,  "+
        	   "  DECODE(SUM(APP_GRANT_RECIEVED),NULL,0,SUM(APP_GRANT_RECIEVED))AS APP_GRANT_RECIEVED,  "+
        	   "  DECODE(SUM(APPRO_DURING_YR),NULL,0,SUM(APPRO_DURING_YR))AS APPRO_DURING_YR,  "+
        	   "  DECODE(SUM(APP_GRANT_TR),NULL,0,SUM(APP_GRANT_TR))AS APP_GRANT_TR,  "+
        	   "  decode(SUM(APP_GRANT_UPTODATE),null,0,SUM(APP_GRANT_UPTODATE))AS APP_GRANT_UPTODATE  "+
        	   "  from fas_a52_register a  "+
        	   "  where financial_year = '"+finyr+"'" +
        		   "  and asset_major_class_code="+assetcode +
        		   " group by asset_major_class_code, accounting_unit_id "+
        	   " order by ASSET_MAJOR_CLASS_CODE,accounting_unit_id))";
            ps2=con.prepareStatement(sql);
            rs2=ps2.executeQuery();
            while(rs2.next())
            {
               //System.out.println("while");
                out.println("<tr>");   
              
                out.println("<td align='left'>"+rs2.getString("asset_major_class_desc")+"</td>");
                
                out.println("<td align='left'>"+rs2.getString("unitname").trim()+"</td>");
                
                out.println("<td align='right'>"+rs2.getInt("closing_qty")+"</td>");
    
                out.println("<td align='right'>"+rs2.getInt("closing_value")+"</td>");
                
                out.println("<td align='right'>"+rs2.getInt("bookvalue")+"</td>");
                out.println("<td align='center'><a href= javascript:Show('"+rs2.getInt("accounting_unit_id")+"','"+rs2.getInt("asset_major_class_code")+"','"+finyr+"')>Office Wise BreakUp</a></td>");
               
               // out.println("<td align='center'><a href='A52_Abstract_Circle_List_MIS.jsp?unitid="+rs2.getInt("accounting_unit_id")+"&assetcode="+rs2.getInt("asset_major_class_code")+"&finyr="+finyr'>Office Wise BreakUp</a></td></tr>");
                out.println("</tr>");   
            }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+ e);
            e.printStackTrace();
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
                     onclick="exit()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>List All Account Rendering Units</title>
    
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <script type="text/javascript" src="../scripts/ListAllAccountingUnit.js"></script>
       <script type="text/javascript" src="../scripts/Asset_Rendering.js"></script>
	   <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
	   <script type="text/javascript" language="javascript">
	
		   function btncancel()
		   {
		   	self.close();
		   }
		
		
		   function Minimize() 
		   {
		   	window.resizeTo(0,0);
		   	window.screenX = screen.width;
		   	window.screenY = screen.height;
		   	opener.window.focus();
		   }
		
		
		   function EditRender(rid)
		   {
		   	var renderingUnitId="",renderedForUnitId="",dtFrm="",active="",dtTo="",renderAccunit="";
		   	r=document.getElementById(rid);
		   	rcells=r.cells;
		   	//alert(rcells.item(1).firstChild.nodeValue);
		   //alert(rcells.item(2).firstChild.nodeValue);
		   	//alert(rcells.item(3).firstChild.nodeValue);
		   //	alert(rcells.item(4).firstChild.nodeValue);
		   	//alert(rcells.item(5).firstChild.nodeValue);
		   	//alert(rcells.item(6).firstChild.nodeValue);
		   	//alert(rcells.item(7).firstChild.nodeValue);
		   	
		   	//renderAccunit = rcells.item(1).firstChild.nodeValue;
		   	renderingUnitId=rcells.item(3).firstChild.nodeValue;
		   	renderedForUnitId=rcells.item(4).firstChild.nodeValue;
		   	dtFrm=rcells.item(5).firstChild.nodeValue;
		   	active=rcells.item(6).firstChild.nodeValue;
		   	dtTo=rcells.item(7).firstChild.nodeValue; 
		   	Minimize();
		   	opener.goBack(renderingUnitId,renderedForUnitId,dtFrm,active,dtTo);        
		   }
		   
	  </script>
  </head>
  <body  bgcolor="rgb(255,255,225)">
 
 
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
    Connection connection=null;
	ResultSet res=null;
  	ResultSet results=null;
  	ResultSet results1=null;
  	ResultSet results2=null;
    PreparedStatement ps1=null;
    PreparedStatement ps2=null;
    PreparedStatement ps3=null;
    String sql5="";
    String sql3="";
    ResultSet results3=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
  <%
  	int accid=0;
  
  int  cmbRenderAccUnit=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
  String condn="";
  try{
	  cmbRenderAccUnit=Integer.parseInt(request.getParameter("cmbRenderAccUnit"));
  }catch(Exception e){System.out.println("Exception in getting render:"+e);}
  try{
	  cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
  }catch(Exception e){System.out.println("Exception in getting unit id:"+e);}
  
  try{
	  cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
  }catch(Exception e){System.out.println("Exception in getting officeid:"+e);}
 // System.out.println("cmbOffice_code=="+cmbRenderAccUnit +" "+cmbAcc_UnitCode+" "+cmbOffice_code);
  if((cmbRenderAccUnit>0)&&(cmbAcc_UnitCode>0))
	{
		condn+="  and x.rid ="+cmbRenderAccUnit;
	}	
  else if((cmbRenderAccUnit>0) && (cmbAcc_UnitCode>0)&&(cmbOffice_code>0))
	{
		condn+="  and x.rid ="+cmbRenderAccUnit+" and y.aid ="+cmbAcc_UnitCode;
	}
  else {
	  condn=" ";
  }

  %>  
  
        
  <form action="" name="frmAccountList">
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
              List of All Value Account Rendering Units
            </div></td>
            
        </tr>
       
      </table>
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
       
       <tr class="tdH">
      	<th>Select</th>
       	<th>Account Rendering Unit</th>
        <th>Account Rendered for the Unit</th>
        <th>Effective From Date</th>
        <th>Active (Y/N)</th>
        <th>Effective Upto Date</th>
        <th>Status</th>        
       </tr>
       <tbody id="tb" class="table" align="left">
          <%
			try
                {
	                  //ps=con.prepareStatement("SELECT x.RID AS RID, y.AID AS AID, AR_UNIT, RENDERED_FOR, to_char(FRM,'DD/MM/YYYY') AS FRM, ACTV, to_char(UPTO,'DD/MM/YYYY') AS UPTO FROM ((SELECT a.ACCT_RENDERING_UNIT_ID AS RID, b.ACCOUNTING_UNIT_NAME AS AR_UNIT, a.DATE_EFFECT_FROM AS FRM, a.ACTIVE AS ACTV, a.DATE_EFFECT_UPTO AS UPTO FROM FAS_ASSET_VAL_AC_RENDER_UNITS a JOIN FAS_MST_ACCT_UNITS b ON a.ACCT_RENDERING_UNIT_ID = b.ACCOUNTING_UNIT_ID)x JOIN (SELECT c.ACCT_RENDERING_UNIT_ID AS RID, d.ACCOUNTING_UNIT_ID AS AID, d.ACCOUNTING_UNIT_NAME AS RENDERED_FOR FROM FAS_ASSET_VAL_AC_RENDER_UNITS c JOIN FAS_MST_ACCT_UNITS d ON c.ACCT_UNIT_ID_RENDERED_FOR = d.ACCOUNTING_UNIT_ID)y ON x.RID = y.RID) ORDER BY AR_UNIT");
	                  ps=con.prepareStatement("SELECT distinct x.RID AS RID, " +	                		  
	                		  "  y.AID      AS AID, " +
	                		  "  AR_UNIT, " +
	                		  "  RENDERED_FOR, " +
	                		  "  TO_CHAR(FRM,'DD/MM/YYYY') AS FRM, " +
	                		  "  ACTV, " +
	                		  "  TO_CHAR(UPTO,'DD/MM/YYYY') AS UPTO, " +
	                		  "  x.STATUS   AS STATUS " +
	                		  "FROM ( " +
	                		  "  (SELECT a.ACCT_RENDERING_UNIT_ID AS RID, " +
	                		  "    b.ACCOUNTING_UNIT_NAME         AS AR_UNIT, " +
	                		  "    a.DATE_EFFECT_FROM             AS FRM, " +
	                		  "    a.ACTIVE                       AS ACTV, " +
	                		  "    a.DATE_EFFECT_UPTO             AS UPTO, " +
	                		  "    a.STATUS                       AS STATUS " +
	                		  "  FROM FAS_ASSET_VAL_AC_RENDER_UNITS a " +
	                		  "  JOIN FAS_MST_ACCT_UNITS b " +
	                		  "  ON a.ACCT_RENDERING_UNIT_ID = b.ACCOUNTING_UNIT_ID " +
	                		  "  )x " +
	                		  "JOIN " +
	                		  "  (SELECT c.ACCT_RENDERING_UNIT_ID AS RID, " +
	                		  "    d.ACCOUNTING_UNIT_ID           AS AID, " +
	                		  "    d.ACCOUNTING_UNIT_NAME         AS RENDERED_FOR " +
	                		  "  FROM FAS_ASSET_VAL_AC_RENDER_UNITS c " +
	                		  "  JOIN FAS_MST_ACCT_UNITS d " +
	                		  "  ON c.ACCT_UNIT_ID_RENDERED_FOR = d.ACCOUNTING_UNIT_ID " +
	                		  "  )y ON x.RID                    = y.RID "+condn+
	                		  " ) " +
	                		  "ORDER BY AR_UNIT");
	                  	                  
	                  
	               /*   ps=con.prepareStatement("SELECT a.ACCT_UNIT_ID_RENDERED_FOR, "+

	                		  " b.accounting_unit_name"+

	                		" FROM FAS_ASSET_VAL_AC_RENDER_UNITS a,"+

	                		  " FAS_MST_ACCT_UNITS b"+

	                		" WHERE a.ACCT_UNIT_ID_RENDERED_FOR=b.accounting_unit_id"+

	                		" AND a.acct_rendering_unit_id     =57"+

	                		" ORDER BY a.acct_unit_id_rendered_for");*/
	                  results=ps.executeQuery();
	                  int cnt=0;	                  
	                  while(results.next())
	                  {
	                      cnt++;
	                
	                      out.println("<tr id=" + cnt + ">");	                      
	                      if(results.getString("STATUS").equalsIgnoreCase("C")){
	                    	  out.println("<td>CANCEL</td>");
	                      }else{
	                    	  out.println("<td><a href='javascript:EditRender(" +cnt+ ")'>EDIT</a></td>");  
	                      }	                      
	                      out.println("<td>"+results.getString("AR_UNIT")+"</td>");
	                      out.println("<td>"+results.getString("RENDERED_FOR")+"</td>"); 
	                      
	                      out.println("<td style='display:none'>"+results.getInt("RID")+"</td>");
	                      out.println("<td style='display:none'>"+results.getInt("AID")+"</td>");
	                      String date_from = results.getString("FRM");
	                      out.println("<td>"+date_from+"</td>");     
	                      out.println("<td>"+results.getString("ACTV")+"</td>");
	                      String date_to = results.getString("UPTO");
	                      if(date_to == null)
	                    	  date_to = "-";
	                      out.println("<td>"+date_to+"</td>");
	                      if(results.getString("STATUS").equalsIgnoreCase("L")){
	                    	  out.println("<td>LIVE</td>");  
	                      }else{
	                    	  out.println("<td>CANCEL</td>");  
	                      }
						  out.println("</tr>");
	                  }
	                  if(cnt==0)
	                     {
	                        out.println("<tr><td colspan='7' align='center'>No data found</td></tr>");
	                     } 
         		}catch(Exception e)
            	{
	                System.out.println("Exception in Select:"+e);
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
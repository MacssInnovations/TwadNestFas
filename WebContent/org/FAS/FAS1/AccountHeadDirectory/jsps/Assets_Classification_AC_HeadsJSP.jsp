<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.Format,java.text.SimpleDateFormat,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Assets Account Heads</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" src="../scripts/Assets_Classification_AC_HeadsJS.js" type="text/javascript"></script>   
  </head>
  <body class="table" onload="loadRecord()">
  <%
        Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String xml=null;
        String odt="",lb="",did="",dspec="",dist="",bcode="",bdesc="";   
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
                   try
                   {
                        stmt=con.createStatement();
                        con.clearWarnings();
                   }
                   catch(SQLException e)
                   {
                        System.out.println("Exception in creating statement:"+e);
                   }
                    stmt=con.createStatement();
           }
           catch(Exception e)
           {
                System.out.println("Exception in opening connection:"+e);
           }            
          
  %>
 <form name="Asset_classifications">
 <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Assets Account Heads</b></td>                   
       </tr>       
         <tr>
            <td class="table" width="40%">
                Major Asset Class Code
            </td>
            <td class="table">                                 
                 <select name=class_code id=class_code onchange=loadRecord()>                       
                  <%
                        try
                        {
                               String sql="select asset_major_class_code,asset_major_class_desc from fas_mst_assets_class where STATUS='L' ";
                               rs=stmt.executeQuery(sql);
                               try
                               {
                                    while(rs.next())
                                    {
                                          out.println("<option value='"+rs.getString("asset_major_class_code")+"'>"+rs.getString("asset_major_class_desc")+"</option>");
                                    }
                               }
                               catch(SQLException e)
                               {
                                    System.out.println("Exception in resultset:"+e);
                               }
                               finally
                               {
                                    rs.close();
                               }
                        }
                        catch(SQLException e)
                        { 
                          System.out.println("Exception :"+e);
                        }
                  %>
                  </select>             
            </td>
        </tr>        
        <tr>
            <td class="table" width="40%">Account Head Code</td>
            <td class="table">                
                  <select name=ac_head_code id=ac_head_code >
		                  <option value="">--- Select ---</option>                    
		                  <%
		                        try
		                        {
		                        	   rs.close();
		                        	   String sql="select account_head_code,account_head_desc from com_mst_account_heads";
		                               rs=stmt.executeQuery(sql);
		                               try
		                               {
		                                    while(rs.next())
		                                    {
		                                          out.println("<option value='"+rs.getString("account_head_code")+"'>"+rs.getString("account_head_desc")+"</option>");
		                                    }
		                               }
		                               catch(SQLException e)
		                               {
		                                    System.out.println("Exception in resultset:"+e);
		                               }
		                               finally
		                               {
		                                    rs.close();
		                               }
		                        }
		                        catch(SQLException e)
		                        { 
		                          System.out.println("Exception :"+e);
		                        }
		                  %>
                  </select>  
            </td>
        </tr>        
		<tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="add" value="Add" id="add" onclick="callServer('Add')" >
            <input type="button" name="del" value="Delete" id="del" onclick="callServer('Delete')" disabled="disabled"/>           
            <input type="button" name="clear" value="Clear" id="clear" onclick="clearAll()"/>
            <!--input type="button" name="list" value="List" id="list" onclick="ListAll()"/-->
          </td>
    	</tr>
 </table>
 <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
    <tr>
        <td align="left">
        Number Of Account Heads/Page.&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="cmbpagination" onchange="changepagesize()">
                <option value="5" selected="selected">
                5
                </option>
                <option value="10">
                10
                </option>
                <option value="15">
                15
                </option>
                <option value="20">
                20
                </option>
            </select>
        </td>
        <td align="right">
            <B>Total Number Of Account Heads</b> &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="total" id="total" size="3" readonly></input>
        </td>
    </tr>
</table>  
<table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" class="table">
    <tr class="tdTitle"><td colspan="17">Existing Details</td></tr>
    <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Major Asset Class Description
          </th>
          <th style="display:none;">
            Account Head code
          </th>
          <th>
            Account Head Description
          </th>        
        </tr>
        <tbody id="tblList" class="table">
    	</tbody>
    <tr>
    <td colspan="3">
      <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
                    <tr >
                        <td width="30%">
                             <div align="left"> <div id="divpre" style="display:none"></div> </div>
                        </td>
                        <td width="40%">
                             <div align="center"><table border="0"><tr><td> <div id="divcmbpage" style="display:none" ><font color="Black" size="2"><strong>
                             Page&nbsp;&nbsp;<select name="cmbpage" id="cmbpage" onchange="changepage()"></select></strong></font></div></td><td>
                             <div id="divpage"></div></td></tr></table> </div>
                        </td>
                        <td width="30%">
                             <div align="right"> <div id="divnext" style="display:none"></div> </div>
                        </td>
                    </tr>
      </table>
    </td>
    </tr>
    <tr class="tdH">
          <td colspan="3" align="center">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
          </td>
    </tr>
	 </table>
  </form>
 </body>
</html>
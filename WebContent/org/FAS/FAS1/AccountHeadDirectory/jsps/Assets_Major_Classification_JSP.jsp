<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.Format,java.text.SimpleDateFormat,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Assets Major Classifications</title>    
    <script language="javascript" src="../scripts/Assets_Major_Classification_JS.js" type="text/javascript"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>     
  </head>
  <body class="table" onload="LoadRecord" >
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
           HttpSession session=request.getSession(false);
           UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
             
           System.out.println("user id::"+empProfile.getEmployeeId());
           int empid=empProfile.getEmployeeId();
           int  oid=0,odidt=0;           
           try
           {      
               ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
               ps.setInt(1,empid);
               rs=ps.executeQuery();
               if(rs.next()) 
               {
                       oid=rs.getInt("OFFICE_ID");
                   
               }
               rs.close();
               ps.close();
               ps=con.prepareStatement("select LAB_CODE,LAB_DESC from WQS_MST_LAB where LAB_CODE=?");
               ps.setInt(1,oid);
               rs=ps.executeQuery();
               if(rs.next()) 
               {
                   odidt=Integer.parseInt(rs.getString("LAB_CODE"));
                   odt=rs.getString("LAB_DESC");
                   lb=odidt+"--"+odt;
                   System.out.println(lb);
               }
               rs.close();
               ps.close();
           }
           catch(Exception e)
           {
               System.out.println(e);
           }
  %>
 <form name="Asset_classifications">
 <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Assets Major Classifications</b></td>                   
       </tr>       
         <tr>
            <td class="table" width="40%">
                Major Asset Class Code
            </td>
            <td class="table">                
                 <input type="text" name="ass_code" id="ass_code" disabled="disabled" style="background-color:rgb(214,214,214)">             
            </td>
        </tr>        
        <tr>
            <td class="table" width="40%">Major Asset Class Description</td>
            <td class="table">
                 <input type="text" name="ass_desc" id="ass_desc">
            </td>
        </tr>
        <tr>
            <td class="table" width="40%">Asset Type</td>
            <td class="table">
                    <select name=ass_type id=ass_type>                       
                        <%
                              try
                              {
                                     String sql="select asset_type_code,asset_type_desc from com_mst_assets_type";
                                     rs=stmt.executeQuery(sql);
                                     try
                                     {
                                          while(rs.next())
                                          {
                                                out.println("<option value='"+rs.getString("asset_type_code")+"'>"+rs.getString("asset_type_desc")+"</option>");
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
            <td class="table" width="40%">Alias Code (last 4 digits of the a/c head)</td>
            <td class="table">
                    <input type="text" name=alios_code id=alios_code >
            </td>
        </tr>       
        <tr>
            <td class="table" width="40%">Major Classification of asset as given in the a/c Manual</td>
            <td class="table">
                <input type="radio" name="major_class" id="major_class" checked="checked">Yes<input type="radio" name="major_class" id="major_class">No                
          </td>
        </tr>
        <tr>
           <td class="table" width="40%">Whether Individual Folio to be maintained</td>
           <td class="table">
                <input type="radio" name="folio_maintained" id="folio_maintained" checked="checked">Yes<input type="radio" name="folio_maintained" id="folio_maintained">No
            </td>
        </tr>       
        <tr>
            <td class="table" width="40%">Minor Classification Applicable</td>
            <td class="table">	           
	           <input type="radio" name="mc_applicable" id="mc_applicable" checked="checked">Yes<input type="radio" name="mc_applicable" id="mc_applicable">No
            </td>
        </tr>
        <tr>
            <td class="table" width="40%">Asset is Depreciable</td>
            <td class="table">	           
	           <input type="radio" name="ass_depreciable" id="ass_depreciable" checked="checked">Yes<input type="radio" name="ass_depreciable" id="ass_depreciable">No
            </td>
        </tr>
		<tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="add" value="Add" id="add" onclick="callServer('Add')" >
            <input type="button" name="del" value="Cancel" id="del" onclick="callServer('Delete')" disabled="disabled"/>
            <input type="button" name="update" value="Update" id="update" onclick="callServer('Update')" disabled="disabled"/>
            <input type="button" name="clear" value="Clear All" id="clear" onclick="clearAll()"/>
            <input type="button" name="list" value="List" id="list" onclick="ListAll()"/>
            <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()" tabindex="70"/>
                     
          </td>
    	</tr>
	 </table>
  </form>
 </body>
</html>
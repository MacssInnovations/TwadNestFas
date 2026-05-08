<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"
	import="java.sql.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<title>NewsPage</title>
</head>
<body bgcolor="#B1C9DE">
<p>
<%
 // String caption=request.getParameter("cap");
 // String descrip=request.getParameter("desc");
 // String link=request.getParameter("link");
  String capid=request.getParameter("capId");
 // String attslno=request.getParameter("attslno");
  System.out.println(capid);
        Connection connection=null;
        ResultSet rsnew=null;
        Statement st=null;
        boolean flag=false;
        String statid="";
                   
     try
  {
                    System.out.println("view birthday");
            
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
                         connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
 String descrip="";
 int  slno=0;
 String filename="";
 String caption="";
  st=connection.createStatement();
  String query = "SELECT CAPTION,BRIEF_DESC FROM COM_CAPTION_DETAILS WHERE CAPTION_ID='"+capid+ "'";
        System.out.println(query);
        rsnew = st.executeQuery(query);
        if(rsnew.next())
        {
        caption=rsnew.getString("CAPTION");
        descrip=rsnew.getString("BRIEF_DESC");
       // slno=rsnew.getString("ATTACH_SLNO")+",";
       // filename=rsnew.getString("FILE_TITLE");
       // }
       
       %>
</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<form>
<center>
<table border='1' width="75%">
	<tr align="center">
		<td colspan="2"><font>
		<h2>News Page</h2>
		</font></td>
	</tr>

	<tr align="left">

		<td width="20%">
		<h3>Title:</h3>
		</h3>
		<td>
		<h3><%=caption%></h3>
		</td>
		</td>
	</tr>
	<tr align="left">
		<td width="20%" align="left">
		<h4>Description:&nbsp;</h4>
		</td>
		<td>
		<h4><%=descrip%></h4>
		</td>
	</tr>
	<%
         try
         {
       Statement st1=connection.createStatement();
       ResultSet rs1=st1.executeQuery("select attach_slno,file_name,CAPTION_ID as capid from COM_CAPTION_ATTACH "+  
                                    " where file_content is not null and process_flow_status_id!='DL' and CAPTION_ID="+capid);
                    while(rs1.next())
                    {
                    slno=rs1.getInt("ATTACH_SLNO");
                    System.out.println("slno is ::"+slno );
                    filename=rs1.getString("FILE_NAME");
                    System.out.println("file name is :"+filename);
                    %>
	<tr align="left">
		<td colspan="2"><a
			style="text-decoration: underline; cursor: pointer; color: blue"
			target="_blank"
			href="showPDF.jsp?captionid=<%=capid%>&attachslno=<%=slno%>"><%=filename%></a></td>
	</tr>
	<%
                    }
                    }
                    catch(Exception e)
                    {
                    System.out.println("ERRoR");
                    }
                    %>
	<%}%>


</table>
</center>


<br>

</form>
</body>
</html>
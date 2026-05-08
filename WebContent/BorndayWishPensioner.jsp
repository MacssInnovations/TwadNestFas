<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"
	isErrorPage="false"%>
<%@ page
	import="java.sql.*,java.text.*,java.util.ResourceBundle,java.util.*,java.lang.String,javax.servlet.*,javax.servlet.http.*,java.io.*,java.util.Date"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<title>View Birthday</title>
<script type="text/javascript"	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

<link href="css/birthdaywish.css" rel="stylesheet" media="screen" />
<%
     	Connection connection = null;
     	ResultSet rsnew = null;
     	Statement st = null;
     	Statement stmt = null;
     	boolean flag = false;
     	String statid = "";
     	String newpath = "";
     	String birthdayimagepath = "";
     %>

</head>
<body class="table">
<form name="frm1" id="frm1">
<center>
<%
   	try {

   		ResourceBundle rs1 = ResourceBundle
   				.getBundle("Servlets.Security.servlets.Config");
   		String ConnectionString = "";
   		String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
   		String strdsn = rs1.getString("Config.DSN");
   		String strhostname = rs1.getString("Config.HOST_NAME");
   		String strportno = rs1.getString("Config.PORT_NUMBER");
   		String strsid = rs1.getString("Config.SID");
   		String strdbusername = rs1.getString("Config.USER_NAME");
   		String strdbpassword = rs1.getString("Config.PASSWORD");
   		//ConnectionString = strdsn.trim() + "@" + strhostname.trim()
   			//	+ ":" + strportno.trim() + ":" + strsid.trim();
   		ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
   		Class.forName(strDriver.trim());
   		connection = DriverManager.getConnection(ConnectionString,
   				strdbusername.trim(), strdbpassword.trim());

   	} catch (Exception e) {
   		System.out.println("Exception in connection...." + e);
   	}

   	try {
   		st = connection.createStatement();
   		stmt = connection.createStatement();
   		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
   		String strdate = sd
   				.format(new Date(System.currentTimeMillis()));
   		//String sql="SELECT hrm.PPO_NO,hrm.PENSIONER_INITIAL,hrm.PENSIONER_NAME,hrm.DATE_OF_BIRTH,hrm.PAYMENT_OFFICE_ID,img.FAMILY_PHOTO FROM HRM_PEN_MST_DETAILS hrm full join HRM_PEN_ATTACH_PHOTO_MST img on hrm.ppo_no=img.ppo_no WHERE substr(hrm.date_of_birth,0,6) = substr(now(),0,6)";
   		String sql = "SELECT hrm.ppo_no,(hrm.pensioner_initial||'.'||hrm.pensioner_name) as name,hrm.date_of_birth,hrm.payment_office_id,img.family_photo,offi.office_name,desi.DESIGNATION FROM hrm_pen_mst_details hrm FULL JOIN hrm_pen_attach_photo_mst img ON hrm.ppo_no = img.ppo_no left outer join com_mst_offices offi on offi.office_id=hrm.payment_office_id left outer join HRM_MST_DESIGNATIONS desi on desi.DESIGNATION_ID=hrm.DESIGNATION_ID WHERE SUBSTR(hrm.date_of_birth,   0,   6) = SUBSTR(now(),   0,   6) ";
   		rsnew = st.executeQuery(sql);
   		String contextPath = request.getRealPath("/");
   		String delfloder=contextPath + "PensionerPhotos/";
			File f = new File(delfloder);
			File f2;
			boolean success = false;
			if(f.exists())
			{
			String[] list=f.list();
			//System.out.println("The list length is"+list.length);
			for (int i = 0; i <list.length; i++)
			{
		       f2 = new File(f+"\\"+list[i]);
		       success = f2.delete();
		      // System.out.print(f2);
		       //System.out.println( "  deleted " + success);
		       }
			}
   		
   %>
<h1 class='a1'>TWAD Board Wishes the following Pensioners</h1>
<h1 class='a1'><i>Many More Happy Returns of the Day</i></h1>
<h2 class='a1'><%=strdate%></h2>
<table border='0' cellspacing='0' height="100%" width="50%">
	<tr align="center">
	<%
	birthdayimagepath=contextPath+"Birthdayimages/";
	 //System.out.println("The birthday images path is===>"+birthdayimagepath);
	String[] daysplit=strdate.split("/");
	File f3=new File(birthdayimagepath);
	if(f3.exists())
	{
		String imagename=daysplit[0]+".gif";
		 //f4 = new File(f3+"\\"+birthdaylist[i]);
		 //System.out.println("The birthday images path is===>"+birthdaylist[i]);
		 System.out.println("The birthday images path is===>"+imagename);
		 %>
		 <td width="300px" height="200px" ><img src="Birthdayimages/<%=imagename%>" width="300px" height="200px"></td>
		 <%
		
		
	}
	
	%>
		
</tr>
</table>
</center>


<%
                             	        	while (rsnew.next()) {
                             	        			String filename = rsnew.getString("PPO_NO") + ".jpg";
                             	        			
                             	        			newpath = contextPath + "PensionerPhotos/" +filename;
                             	        			
                             	        			
                             	        			if (flag != true) {

                             	        				String sqlnew = "select FAMILY_PHOTO from HRM_PEN_ATTACH_PHOTO_MST where ppo_no="
                             	        						+ rsnew.getString("PPO_NO");
                             	        				//System.out.println(sqlnew);

                             	        				ResultSet rs = stmt.executeQuery(sqlnew);

                             	        				while (rs.next()) {

                             	        					Blob ablob = rs.getBlob("FAMILY_PHOTO");
                             	        					//response.setContentType("image/jpg");

                             	        					try {

                             	        						//System.out.println("The realpath is"+newpath);
                             	        						//FileInputStream fis =new FileInputStream(request.getRealPath(""));

                             	        						if (ablob != null)
                             	        						{
                             	        							FileOutputStream fos = new FileOutputStream(
                             	        									newpath);
                             	        							long bloblen = ablob.length();
                             	        							int buffersize = (int) bloblen;
                             	        							int bytesRead = 0;
                             	        							byte b[] = new byte[buffersize];
                             	        							InputStream is = ablob.getBinaryStream();
                             	        							while ((bytesRead = is.read(b)) != -1) {
                             	        								fos.write(b, 0, bytesRead);

                             	        							}
                             	        							is.close();
                             	        						}

                             	        					} catch (Exception e) {
                             	        						e.printStackTrace();
                             	        					}
                             	        				}
                             	        				rs.close();

                             	        			}

                             	        			else {
                             	        %> <script type="text/javascript">
						alert("No photos Available!");
					</script> <%
						}

								String src = rsnew.getString("PPO_NO").toString() + ".jpg";
					%>
<table border='0' cellspacing='0' class='border1' height="100%" width="50%" align="center">
	<tr class='a'>
		<td width="200px" height="150px">
		<img src="PensionerPhotos/<%=src%>" width="200px" height="150px" />
		
		</td>
		<td >PPONO:<%=rsnew.getString("PPO_NO")%>
		<br />
		<br />
		Pensioner Name :<%=rsnew.getString("name")%> <br />
		<br />
		Designation at the time of Retirement:<%=rsnew.getString("DESIGNATION")%>
		<br />
		<br />
		Pension Payment Office:<%=rsnew.getString("office_name")%>
		</td>
	</tr>
</table>

<%
	}
		rsnew.close();

                    	}

                    	catch (Exception e) {
                    		System.out.println("Err:" + e.getMessage());
                    		System.out.println(e);
                    	}
                    %>

</form>
</body>
</html>

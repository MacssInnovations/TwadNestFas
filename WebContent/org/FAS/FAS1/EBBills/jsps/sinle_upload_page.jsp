<%@ page import="java.io.*,java.sql.*,java.util.ResourceBundle" %>
<%
	//to get the content type information from JSP Request Header
	String contentType = request.getContentType();
	//here we are checking the content type is not equal to Null and as well as the passed data from mulitpart/form-data is greater than or equal to 0
	if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
		try{
 		DataInputStream in = new DataInputStream(request.getInputStream());
		//we are taking the length of Content type data
		int formDataLength = request.getContentLength();
		byte dataBytes[] = new byte[formDataLength];
		int byteRead = 0;
		int totalBytesRead = 0;
		//this loop converting the uploaded file into byte code
		while (totalBytesRead < formDataLength) {
			byteRead = in.read(dataBytes, totalBytesRead,formDataLength);
			totalBytesRead += byteRead;
			}

		String file = new String(dataBytes);
		//for saving the file name
		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1,contentType.length());
		int pos;
		//extracting the index of file 
		pos = file.indexOf("filename=\"");
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		int boundaryLocation = file.indexOf(boundary, pos) - 4;
		int startPos = ((file.substring(0, pos)).getBytes()).length;
		int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
	//out.println("location"+saveFile);
	String fileName=saveFile;
	saveFile="/root/Sample/twadfas/WebContent/org/FAS/FAS1/EBBills/"+saveFile;
	

		// creating a new file with the same name and writing the content in new file	
		FileOutputStream fileOut = new FileOutputStream(saveFile);
		fileOut.write(dataBytes, startPos, (endPos - startPos));
		fileOut.flush();
		fileOut.close();

		
		
		Connection con = null;

		try {

			ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");

			//ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
				//	+ strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in connection...." + e);
		}
		ResultSet rs = null, rs1 = null, rs2 = null, rs4 = null;
		CallableStatement cs = null;
		PreparedStatement ps = null, ps1 = null, ps2 = null;
		String xml = "";
		
		
		ps=con.prepareStatement("insert into FAS_TEST_UPLOAD(FILE_NO,FILE_NAME) values(?,?)");
		ps.setInt(1,1);
		ps.setString(2,fileName);
		ps.executeUpdate();
		
		%><Br><table border="2"><tr><td><b>You have successfully
 upload the file by the name of:</b>
		<% out.println(saveFile); %></td></tr></table> <%
		}catch(Exception e){out.println(e);}
		}
	
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Upload File Response</title>
</head>
<body>
	<%-- Using JSP EL to get message attribute value from request scope --%>
	<%--  <h2 >${requestScope.message}</h2> --%>
	<form id="fileUploadingReaponse" name="fileUploadingReaponse">

		<table bgcolor=" #3498db" width="100%" border="1" align="center">
			<tr class="tdH">
				<td colspan="2">
					<div align="center">
						<strong>File Status</strong>
					</div>
				</td>
			</tr>
		</table>

		<table cellspacing="1" cellpadding="2" border="1" width="100%">

			<tr align="center">
				<td class="table">
					<div align="center">
						<font color="#ff2121">${requestScope.message}</font>
					</div>
				</td>

			</tr>
			<tr align="center">
				<td bgcolor=" #3498db">
					<div align="center">
						<input type="button" id="btn" value="ok" onclick="self.close()" />
					</div>
				</td>

			</tr>


		</table>
	</form>

</body>
</html>
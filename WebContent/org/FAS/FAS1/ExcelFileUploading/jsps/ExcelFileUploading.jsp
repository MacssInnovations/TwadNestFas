<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/ExcelFileUploading/scripts/ExcelFileUploading.js"></script>
<title>File Uploading</title>
</head>



<body>

	<form id="fileUploading" name="fileUploading" method="POST"
		action="../../../../../File_upload?Command=Upload"
		enctype="multipart/form-data" onsubmit="return Validate(this);">
		<table width="100%" border="1" align="center">
			<tr class="tdH">
				<td colspan="2">
					<div align="center">
						<strong>Choose File to Upload in Server</strong>
					</div>
				</td>
			</tr>
		</table>

		<table cellspacing="1" cellpadding="2" border="1" width="100%">
			<tr align="left">
				<td class="table">
					<div align="left">
						ChooseFile <font color="#ff2121">*</font>
					</div>
				</td>
				<td class="table">
					<div id="more">
						<input type="file" name="file" id="file" />
					</div>
					<div align="left">
						<a href="../../../../../File_upload?Command=Download">Download the Sample Excel 97-2003 Workbook file Format</a>
					</div>

				</td>
				
			</tr>
			<tr align="left">
				<td colspan="2" class="table" align="center">
					<div align="center">
						<input type="submit" value="upload" />
					</div>
				</td>
				
				

			</tr>
		</table>
		<table width="100%" border="1" align="center">
			<tr class="tdH">
				<td>
					<div align="center">
						<input type="button" id="cmdcancel" name="cancel" value="EXIT"
							onclick="btncancel()">
					</div>
				</td>
			</tr>
		</table>


	</form>

</body>


</html>
package Servlets.FAS.FAS1.AnnualAccounts.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Servlet implementation class File_upload
 * 
 * 
 * Developed By Nanda Kumar
 * 
 * 
 */
public class File_upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public File_upload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/* Session checking */
		/* processRequest(request, response); */
		/* Session checking */
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String filepath, fileName, strType;
		filepath = fileName = strType = "";
		try {
			strType = request.getParameter("Command");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Command " + strType);
		if (strType.equalsIgnoreCase("Download"))

		{

			filepath = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/downloadFile/Annual_Grouping.xls");
			fileName = "Annual_Grouping.xls";
			File fileToDownload = new File(filepath);
			if (!fileToDownload.exists()) {
				throw new ServletException("File doesn't exists on server.");
			}
			System.out.println("File location on server::" + fileToDownload.getAbsolutePath());
			ServletContext ctx = getServletContext();
			InputStream fis = new FileInputStream(fileToDownload);
			String mimeType = ctx.getMimeType(fileToDownload.getAbsolutePath());
			response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
			response.setContentLength((int) fileToDownload.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			ServletOutputStream os = response.getOutputStream();
			byte[] bufferData = new byte[1024];
			int read = 0;
			while ((read = fis.read(bufferData)) != -1) {
				os.write(bufferData, 0, read);
			}
			os.flush();
			os.close();
			fis.close();
		}

	}

	/*
	 * String name; String StoreLocation; String size; String isFormField;
	 * String FieldName;
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		/* Session checking */
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		/* Establishing Connection */

		Connection con = null;
		ResultSet rs = null, rs2 = null;
		/*
		 * ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
		 * PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;
		 */
		PreparedStatement ps = null, ps2 = null;
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
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in openeing connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to
			// the database server.. due to "+e,"ok");

		}

		String path, actPath, val, majHead, minHead, subHead, xml, strType, filename, msg, parsmsg;
		path = actPath = val = majHead = minHead = subHead = strType = filename = msg = parsmsg = "";
		int finStart, finEnd, headAcc, majOrder, minOrder, subOrder, cnt;
		finStart = finEnd = headAcc = majOrder = minOrder = subOrder = cnt = 0;

		String[] path_split = new String[20];
		// xml = "<response>";
		try {
			strType = request.getParameter("Command");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Command " + strType);

		if (strType.equalsIgnoreCase("Upload"))

		{
			// String CONTENT_TYPE = "text/xml; charset=windows-1252";
			String CONTENT_TYPE = "text/html";
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			// xml = "<response><command>load_Major_Heads</command>";

			try {
				boolean ismultipart = ServletFileUpload.isMultipartContent(request);
				if (!ismultipart) {

				} else {
					FileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);

					List items = null;

					try {

						items = upload.parseRequest(request);
					} catch (Exception e) {
					}

					for (int i = 0; i < items.size(); i++) {
						System.out.println(items.get(i));
						Object obj = items.get(i);

						path = obj.toString();

					}

					path_split = path.split(",");

					actPath = path_split[1].substring(15);

					System.out.println(actPath);
					Iterator itr = items.iterator();

					while (itr.hasNext()) {
						FileItem item = (FileItem) itr.next();
						// InputStream ip = item.getInputStream();
						// System.out.println(ip);
						if (item.isFormField()) {

						} else {
							String itemname = item.getName();

							if ((itemname == null || itemname.equals(""))) {
								continue;
							}
							filename = FilenameUtils.getName(itemname);
							String filePath = request.getSession().getServletContext().getRealPath("/uploadFiles/");
							File fileToCreate = new File(filePath, filename);
							File actPathFile = new File(actPath);
							FileUtils.copyFile(actPathFile, fileToCreate);
							String filePathName = filePath + "/" + filename;
							FileInputStream inputStream = new FileInputStream(new File(filePathName));
							HSSFWorkbook workbook = getWorkbook(inputStream, filePathName);

							HSSFSheet firstSheet = workbook.getSheetAt(0);

							@SuppressWarnings("unchecked")
							Iterator<HSSFRow> rowIter = firstSheet.rowIterator();
							while (rowIter.hasNext()) {
								HSSFRow row = rowIter.next();

								if (row.getRowNum() == 0) {
									System.out
											.println("ROW ONE->" + workbook.getSheetAt(0).getRow(0).getCell((short) 0));
								}
								if (row.getRowNum() > 0) {

									int r = row.getRowNum();

									HSSFCell cellZero = null;
									HSSFCell cellOne = null;
									HSSFCell cellTwo = null;
									HSSFCell cellThree = null;
									HSSFCell cellFour = null;
									HSSFCell cellFive = null;
									HSSFCell cellSix = null;
									HSSFCell cellSeven = null;
									HSSFCell cellEight = null;
									cellZero = workbook.getSheetAt(0).getRow(r).getCell((short) 0);
									cellOne = workbook.getSheetAt(0).getRow(r).getCell((short) 1);
									cellTwo = workbook.getSheetAt(0).getRow(r).getCell((short) 2);
									cellThree = workbook.getSheetAt(0).getRow(r).getCell((short) 3);
									cellFour = workbook.getSheetAt(0).getRow(r).getCell((short) 4);
									cellFive = workbook.getSheetAt(0).getRow(r).getCell((short) 5);
									cellSix = workbook.getSheetAt(0).getRow(r).getCell((short) 6);
									cellSeven = workbook.getSheetAt(0).getRow(r).getCell((short) 7);
									cellEight = workbook.getSheetAt(0).getRow(r).getCell((short) 8);

									@SuppressWarnings("unchecked")
									Iterator<HSSFCell> cellIterator = row.cellIterator();
									while (cellIterator.hasNext()) {
										HSSFCell cell = cellIterator.next();
										int columnIndex = cell.getCellNum();

										switch (columnIndex) {
										case 0:

											if (!cellZero.toString().equals(val)) {
												finStart = (Integer) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}
										case 1:

											if (!cellOne.toString().equals(val)) {
												finEnd = (Integer) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}
										case 2:

											if (!cellTwo.toString().equals(val)) {
												headAcc = (Integer) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}
										case 3:

											if (!cellThree.toString().equals(val)) {
												majHead = (String) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}
										case 4:

											if (!cellFour.toString().equals(val)) {
												majOrder = (Integer) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}
										case 5:

											if (!cellFive.toString().equals(val)) {
												minHead = (String) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}
										case 6:

											if (!cellSix.toString().equals(val)) {
												minOrder = (Integer) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}
										case 7:

											if (!cellSeven.toString().equals(val)) {
												subHead = (String) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}
										case 8:

											if (!cellEight.toString().equals(val)) {
												subOrder = (Integer) getCellValue(cell);
												cnt = cnt + 1;
												break;
											}

										}

									} // while end cell

									try {
										ps = con.prepareStatement(
												"Insert into FAS_HO_ANNUALGROUPING (FINYEARSTART,FINYEAREND,HOA,MAJORHEAD,MAJORHEADSORTORDER,MINORHEAD,MINORHEADSORTORDER,SUBHEAD,SUBHEADSORTORDER) "
														+ "values (?,?,?,?,?,?,?,?,?)");
										ps.setInt(1, finStart);
										ps.setInt(2, finEnd);
										ps.setInt(3, headAcc);
										ps.setString(4, majHead.trim());
										ps.setInt(5, majOrder);
										ps.setString(6, minHead.trim());
										ps.setInt(7, minOrder);
										ps.setString(8, subHead.trim());
										ps.setInt(9, subOrder);
										ps.executeUpdate();
										//ps.close();

									} catch (Exception e) {
										System.out.print(e);
										msg = msg + "," + Integer.toString(headAcc);
										// msg="Data already exist for the
										// following headcode"+msg;

									}
									if(ps!=null)
			         				{
			         					ps.close();
			         				}
								}
							}

						}

					}
				}
			}

			catch (Exception e) {
				System.out.println("eXCP :" + e);
				parsmsg = parsmsg + " File Not Suitable data format";
			} finally {
				// out.close();
			}

			if (cnt >= 9 && msg.isEmpty() && parsmsg.isEmpty())

			{
				// out.print("file uploaded succesfully");
				request.setAttribute("message", filename + "  File uploaded successfully!");
				RequestDispatcher rd = request.getRequestDispatcher("/response.jsp");
				rd.include(request, response);
			}
			if (cnt >= 9 && msg.length() > 0) {

				// out.print("Please import correct data format!");
				request.setAttribute("message",
						filename + "  File Data already exist for the following headcode" + msg);
				RequestDispatcher rd = request.getRequestDispatcher("/response.jsp");
				rd.include(request, response);
			}
			if (cnt < 9 && msg.isEmpty() && parsmsg.length() > 0) {

				// out.print("Please import correct data format!");
				request.setAttribute("message", filename + parsmsg);
				RequestDispatcher rd = request.getRequestDispatcher("/response.jsp");
				rd.include(request, response);
			}

		}

	}

	private Object getCellValue(HSSFCell cell) {
		// try{
		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			double doubValue = cell.getNumericCellValue();
			int l = (int) Math.round(doubValue);
			return l;

		case Cell.CELL_TYPE_BLANK:
			return cell.getNumericCellValue();
		}

		return null;
	}

	private HSSFWorkbook getWorkbook(FileInputStream inputStream, String filePathName) throws IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = null;

		if (filePathName.endsWith("xls")) {
			System.out.println("Nanda xls");

			workbook = new HSSFWorkbook(inputStream);

		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		return workbook;
	}

}
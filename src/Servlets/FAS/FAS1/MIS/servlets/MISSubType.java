package Servlets.FAS.FAS1.MIS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MIS_Major_Grouping
 */
public class MISSubType extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MISSubType() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();


		String strCommand = "";
		String xml = "";

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try {
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("command");
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strCommand.equalsIgnoreCase("add")) {
			xml = "";
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int mainTypeCode = Integer.parseInt(request.getParameter("mainTypeCode"));
   			String subTypeDesc = request.getParameter("subTypeDesc");
   			try {   				
   				MISCategoryImpl add = new MISCategoryImpl();				
   				xml = add.addMisSubType(mainCategoryId,mainTypeCode,subTypeDesc,userid);   				
			} catch (SQLException e) {
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("getGrid")) {			
			xml = "";
			String categoryCode = request.getParameter("cateCode");
			String mainTypeCode = request.getParameter("mainTypeCode");
			try{
				MISCategoryImpl mismasterservice = new MISCategoryImpl();
				xml = mismasterservice.subTypeList(categoryCode,mainTypeCode);
			}catch (Exception e) {
				xml="<response><status>failure</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int mainTypeCode = Integer.parseInt(request.getParameter("mainTypeCode"));
			int subTypeCode = Integer.parseInt(request.getParameter("subTypeCode"));
   			String subTypeDesc = request.getParameter("subTypeDesc");
			xml = "";
   			try {
   				MISCategoryImpl update = new MISCategoryImpl();				
				xml = update.updateSubTypes(mainCategoryId, mainTypeCode, subTypeCode, subTypeDesc, userid, "n");
			} catch (SQLException e) {
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("deleted")) {
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int mainTypeCode = Integer.parseInt(request.getParameter("mainTypeCode"));
			int subTypeCode = Integer.parseInt(request.getParameter("subTypeCode"));
   			String subTypeDesc = request.getParameter("subTypeDesc");
			xml = "";
   			try {
   				MISCategoryImpl update = new MISCategoryImpl();				
   				xml = update.updateSubTypes(mainCategoryId, mainTypeCode, subTypeCode, subTypeDesc, userid, "y");
			} catch (SQLException e) {
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("edit")) {
			xml = "";
			int mainCategoryId = Integer.parseInt(request.getParameter("majorCode"));
			int mainTypeCode = Integer.parseInt(request.getParameter("mainTypeCode"));
			int subTypeCode = Integer.parseInt(request.getParameter("subType"));
			try {   				
   				MISCategoryImpl add = new MISCategoryImpl();				
   				xml = add.editSubType(mainCategoryId,mainTypeCode,subTypeCode);   				
			} catch (SQLException e) {
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		}	else if (strCommand.equalsIgnoreCase("maincombo")) {
			xml = "";
			try {   				
   				MISCategoryImpl list = new MISCategoryImpl();				
   				xml = list.loadMainCombo();   				
			} catch (SQLException e) {
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		}else if (strCommand.equalsIgnoreCase("mainType")) {
			xml = "";
			int majorCode = Integer.parseInt(request.getParameter("majorCode"));
			try {   				
   				MISCategoryImpl list = new MISCategoryImpl();				
   				xml = list.loadMainType(majorCode);   				
			} catch (SQLException e) {
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		}	
		out.write(xml);
		out.flush();
		out.close();
	}

}

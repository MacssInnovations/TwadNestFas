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
public class MISMainType extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MISMainType() {
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
   			String mainTypeDesc = request.getParameter("mainTypeDesc");
   			try {   				
   				MISCategoryImpl add = new MISCategoryImpl();				
   				//xml = add.addMisMainType(mainCategoryId,mainTypeDesc,userid);   				
			} catch (Exception e) {
				
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("getGrid")) {			
			xml = "";			 
			try{
				MISCategoryImpl mismasterservice = new MISCategoryImpl();
				//xml = mismasterservice.mainTypeList();
			}catch (Exception e) {
				
				xml="<response><status>failure</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int mainTypeCode = Integer.parseInt(request.getParameter("mainTypeCode"));
			String mainTypeDesc = request.getParameter("mainTypeDesc");
			xml = "";
   			try {
   				MISCategoryImpl update = new MISCategoryImpl();				
				//xml = update.updateMainType(mainCategoryId, mainTypeCode, mainTypeDesc, userid, "n");
			} catch (Exception e) {
				
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("deleted")) {
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int mainTypeCode = Integer.parseInt(request.getParameter("mainTypeCode"));
			String mainTypeDesc = request.getParameter("mainTypeDesc");
			xml = "";
   			try {
   				MISCategoryImpl update = new MISCategoryImpl();				
   				//xml = update.updateMainType(mainCategoryId, mainTypeCode, mainTypeDesc, userid, "y");				
			} catch (Exception e) {
				
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("edit")) {
			xml = "";
			int mainCategoryId = Integer.parseInt(request.getParameter("majorCode"));
			int subCode = Integer.parseInt(request.getParameter("subCode"));
			try {   				
   				MISCategoryImpl add = new MISCategoryImpl();				
   				//xml = add.editMainType(mainCategoryId,subCode);   				
			} catch (Exception e) {
				
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

		}	
		out.write(xml);
		out.flush();
		out.close();
	}

}

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
public class MISFundProgress extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MISFundProgress() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			String fundType = request.getParameter("fundType");
   			String fundDesc = request.getParameter("fundDesc");
   			try {   				
   				MISCategoryImpl add = new MISCategoryImpl();				
   				xml = add.addFundProgress(fundType,fundDesc,userid);   				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("getGrid")) {			
			xml = "";			 
			try{
				MISCategoryImpl mismasterservice = new MISCategoryImpl();
				xml = mismasterservice.financialProgressList();
			}catch (Exception e) {
				// TODO: handle exception
				xml="<response><status>failure</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {			
			String fundType = request.getParameter("fundType");
			int fundCode = Integer.parseInt(request.getParameter("fundCode"));
   			String fundDesc = request.getParameter("fundDesc");
			xml = "";
   			try {
   				MISCategoryImpl update = new MISCategoryImpl();				
				xml = update.updateFinancialProgress(fundType, fundCode, fundDesc, userid, "n");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("deleted")) {
			String fundType = request.getParameter("fundType");
			int fundCode = Integer.parseInt(request.getParameter("fundCode"));
   			String fundDesc = request.getParameter("fundDesc");
			xml = "";
   			try {
   				MISCategoryImpl update = new MISCategoryImpl();				
				xml = update.updateFinancialProgress(fundType, fundCode, fundDesc, userid, "y");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("edit")) {
			xml = "";
			int fundCode = Integer.parseInt(request.getParameter("fundCode"));
			try {   				
   				MISCategoryImpl add = new MISCategoryImpl();				
   				xml = add.editFundProgress(fundCode);   				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		}	else if (strCommand.equalsIgnoreCase("maincombo")) {
			xml = "";
			try {   				
   				MISCategoryImpl list = new MISCategoryImpl();				
   				xml = list.loadMainCombo();   				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} 
		out.write(xml);
		out.flush();
		out.close();
	}

}

package Servlets.FAS.FAS1.MIS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MIS_Major_Grouping
 */
public class MISAcHeadCode extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MISAcHeadCode() {
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
			int mstInsert = 0;
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int subCategoryId = Integer.parseInt(request.getParameter("subCategoryId"));
			String fundExpend = request.getParameter("fundExpend");
			String accHeads = request.getParameter("accHead");
			String[] accHead = accHeads.split(",");
			List<String> list = new ArrayList<String>();
   			try {   				
   				MISCategoryImpl add = new MISCategoryImpl();				
   				mstInsert = add.addAcHeadCodeMst(mainCategoryId,subCategoryId,userid);   				
   				if(mstInsert==0){
   					xml="<response><status>success</status><value>Notadded</value></response>";
   				}else{
   					list = add.addMisAcHeadCodeTrn(mainCategoryId,subCategoryId,fundExpend,accHead,userid);
   					xml="<response><status>success</status><value>added</value>";
   					for(String l: list){
   						xml+="<code>"+l+"</code>";
   					}
   					xml+="</response>";
   				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("getGrid")) {			
			xml = "";	
			String fundExpend = request.getParameter("fundExpend");
			try{
				MISCategoryImpl mismasterservice = new MISCategoryImpl();
				//xml = mismasterservice.acCategoryList();
				xml = mismasterservice.acHeadCodeList(fundExpend);
			}catch (Exception e) {
				// TODO: handle exception
				xml="<response><status>failure</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int subCategoryId = Integer.parseInt(request.getParameter("subCategoryId"));
			long accCode = Long.parseLong(request.getParameter("accountHeadCode"));
			String fundType =request.getParameter("fundExpend");
			long preAccCode = Long.parseLong(request.getParameter("preAcchead"));
			xml = "";
   			try {
   				MISCategoryImpl update = new MISCategoryImpl();				
				xml = update.updateAcHeadCode(mainCategoryId, subCategoryId, fundType, accCode, userid, "n", preAccCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("deleted")) {
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int subCategoryId = Integer.parseInt(request.getParameter("subCategoryId"));
			long accCode = Long.parseLong(request.getParameter("accountHeadCode"));
			String fundType =request.getParameter("fundExpend");
			long preAccCode = Long.parseLong(request.getParameter("preAcchead"));
			xml = "";
   			try {
   				MISCategoryImpl update = new MISCategoryImpl();				
				xml = update.updateAcHeadCode(mainCategoryId, subCategoryId, fundType, accCode, userid, "y", preAccCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("edit")) {
			xml = "";
			int mainCategoryId = Integer.parseInt(request.getParameter("majorCode"));
			int subCode = Integer.parseInt(request.getParameter("subCode"));
			long accCode = Long.parseLong(request.getParameter("accCode"));
			String fundType = request.getParameter("fundtype");
			try {   				
   				MISCategoryImpl edit = new MISCategoryImpl();				
   				xml = edit.editAcCode(mainCategoryId, subCode, accCode, fundType);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		}	else if (strCommand.equalsIgnoreCase("subcat")) {
			xml = "";
			int majorCode = Integer.parseInt(request.getParameter("majorCode"));
			try {   				
   				MISCategoryImpl list = new MISCategoryImpl();				
   				xml = list.loadSubCombo(majorCode);   				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("loadExist")) {
			xml = "";
			int mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
			int subCategoryId = Integer.parseInt(request.getParameter("subCategoryId"));
			String fundType = request.getParameter("fundExpend");
			try {   				
   				MISCategoryImpl list = new MISCategoryImpl();				
   				xml = list.acHeadCodeExistList(mainCategoryId,subCategoryId,fundType);   				
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

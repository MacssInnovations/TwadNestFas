package Servlets.FAS.FAS1.MonthEnd.servlets;

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
public class TBUnFreezedSuspReqRaised extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TBUnFreezedSuspReqRaised() {
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
		TBunfreezedReqRaisedImpl tImpl = null;

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
			int accountingUnit = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cashbookYear = Integer.parseInt(request.getParameter("txtCB_Year"));
			int cashbookMonth = Integer.parseInt(request.getParameter("txtCB_Month"));
			int supplementNo = Integer.parseInt(request.getParameter("txtsupplement_no"));
			tImpl = new TBunfreezedReqRaisedImpl();
			int status = 0;
			String msg = "";
			
			List<Integer> list = new ArrayList<Integer>();
			try {
				list = tImpl.addUnfreezeSusRequestRaised(accountingUnit, supplementNo, cashbookYear, cashbookMonth, userid);
				status = list.get(1);
				System.out.println("list.get(0)------>"+list.get(0));
				System.out.println("status------>"+status);
				
				
				if(list.get(0)>=0){
					if(status>0){
						msg="TB Unfreeze Request Raised";
						msg = msg + "<br><br>";
					}else{
						//msg="TB Already Unfreeze for this month";
						msg="Already TB Unfreeze Request Raised for this month";
						
						msg = msg + "<br><br>";
					}
				}else{
					//msg="TB Not freeze for this month";
					
					msg="TB Not freeze for this month";
					msg = msg + "<br><br>";
				}								
	            sendMessage(response, msg, "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				msg="TB Unfreeze Raise Request Has failed to Add";
	            msg=msg+"<br><br>";
	            sendMessage(response,msg,"ok");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg="TB Unfreeze Raise Request Has failed to Add";
	            msg=msg+"<br><br>";
	            sendMessage(response,msg,"ok");
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("loadaccount")) {			
			xml = "";
			int cashbookYear = Integer.parseInt(request.getParameter("cashbookYear"));
			int cashbookMonth = Integer.parseInt(request.getParameter("cashbookMonth"));
			TBunfreezedReqRaisedImpl tbunfreezeImpl = new TBunfreezedReqRaisedImpl();
			try {
				xml = tbunfreezeImpl.loadAccountUnit(cashbookYear, cashbookMonth);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml = "<response><command>loadaccount</command><status>fail</status></response>";
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				xml = "<response><command>loadaccount</command><status>fail</status></response>";
				e.printStackTrace();
			}
		} 
		else if (strCommand.equalsIgnoreCase("check_TB")) {			
			xml = "";
			int cashbookYear = Integer.parseInt(request.getParameter("TB_Year"));
			int cashbookMonth = Integer.parseInt(request.getParameter("TB_Month"));
			int accunitid = Integer.parseInt(request.getParameter("Accunitid")); 
					
			TBunfreezedReqRaisedImpl tbunfreezeImpl = new TBunfreezedReqRaisedImpl();
			try {
				xml = tbunfreezeImpl.loadstatus(cashbookYear,cashbookMonth,accunitid );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml = "<response><command>loadstat</command><status>fail</status></response>";
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				xml = "<response><command>loadstat</command><status>fail</status></response>";
				e.printStackTrace();
			}
		} 
		else if (strCommand.equalsIgnoreCase("check_TBSJV")) {			
			xml = "";
			String msg = "";
			int cashbookYear = Integer.parseInt(request.getParameter("TB_Year"));
			int cashbookMonth = Integer.parseInt(request.getParameter("TB_Month"));
			int accunitid = Integer.parseInt(request.getParameter("Accunitid")); 
			int sup_No = Integer.parseInt(request.getParameter("sup_No"));	
			System.out.println("testingggggggggg::::"+sup_No);
			TBunfreezedReqRaisedImpl tbunfreezeImpl = new TBunfreezedReqRaisedImpl();
			try {
				xml = tbunfreezeImpl.loadstatusSJV(cashbookYear,cashbookMonth,accunitid,sup_No );
				//msg = "Raise the Request for subsequent supplement Number first";
				//msg = tbunfreezeImpl.loadstatusSJV(cashbookYear,cashbookMonth,accunitid,sup_No );
				//System.out.println(msg);
				//sendMessage(response, msg, "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml = "<response><command>loadstat</command><status>fail</status></response>";
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				xml = "<response><command>loadstat</command><status>fail</status></response>";
				e.printStackTrace();
			}
		} 
		System.out.println("returning the xml file***** "+xml);
		out.write(xml);
		out.flush();
		out.close();
	}
	private void sendMessage(HttpServletResponse response,String msg,String bType) {
        try
        {
            String url="org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);          
        }
        catch(IOException e)
        {
        System.out.println("ERROR");
        }
    }

}


package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CivilBills.servlets.BillTypeMasterImpl;

/**
 * Servlet implementation class PassOrderApproval
 */
public class AuctionAssetVoucher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuctionAssetVoucher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();
        String strCommand = "";         
        String xml="";
        int accountUnitId = 0;
		int accountOfficeId = 0;   			
		int assetCode = -10;
        response.setHeader("Cache-Control","no-cache");
        HttpSession session=request.getSession(false);
        String userid=(String)session.getAttribute("UserId");
        try{            
            if(session==null){
                //System.out.println(request.getContextPath()+"/index.jsp?message=sessionout");
                response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
               return;
            }
            //System.out.println(session);                
        }catch(Exception e){
           System.out.println("Redirect Error :"+e);
        }
        try{
          strCommand = request.getParameter("command");
          //System.out.println("command "+strCommand);
        }catch(Exception e){
          e.printStackTrace();
        }
        try{
   		}catch (Exception e){
   			if(strCommand.equalsIgnoreCase("loadleavereqid")){
   				xml="Database Service not Available";
   			}else{
   				xml="<response><status>success</status><value>databaseError</value></response>";
   			}   			
   			System.out.println("databse connection error");
   			return;
		}
   		
   		if(strCommand.equalsIgnoreCase("getOffice")){   			
   			xml = "";
   			AuctionAssetsService office = new AuctionAssetImpl();
   			try {
				xml = office.getAuctionOffice();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("surveyNo")){
   			xml = "";   			
   			int unitCode = Integer.parseInt(request.getParameter("unitCode"));
   			int officeCode = Integer.parseInt(request.getParameter("officeCode"));
   			AuctionAssetsService survey = new AuctionAssetImpl();
   			Set<AuctionAssetBean> set = new HashSet<AuctionAssetBean>();   			
   			try {				
   				set = survey.surveyReportNo(unitCode, officeCode, "");
   				xml = "<response><command>surveyNo</command>";
   				for(AuctionAssetBean s: set){
   	   				//System.out.println("set test "+s.getSurveyNo());
   	   				xml +="<SURVEY_NO>"+s.getSurveyNo()+"</SURVEY_NO>";
   	   			}
   				if(set.isEmpty()){
   					xml +="<status>nodata</status>";
   				}else{
   					xml +="<status>success</status>";
   				}
   				xml +="</response>";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("auctionDetail")){
   			xml = "";
   			int unitCode = Integer.parseInt(request.getParameter("unitCode"));
   			int officeCode = Integer.parseInt(request.getParameter("officeCode"));
   			int surveyNo = Integer.parseInt(request.getParameter("surveyNo"));
   			int assetcode = Integer.parseInt(request.getParameter("assetcode"));
   			AuctionAssetsService details = new AuctionAssetImpl();
   			try {
				xml = details.getAuctionDetails(unitCode,officeCode,surveyNo,assetcode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("cashReceipt")){
   			xml = "";
   			int unitCode = Integer.parseInt(request.getParameter("unitCode"));
   			int officeCode = Integer.parseInt(request.getParameter("officeCode"));
   			int year = Integer.parseInt(request.getParameter("year"));
   			int month = Integer.parseInt(request.getParameter("month"));
   			String check = request.getParameter("check");
   			AuctionAssetsService details = new AuctionAssetImpl();
   			try {
				xml = details.getCashReceipt(unitCode,officeCode,year,month,check);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("add")){   			
   			xml = "";
   			BillTypeMasterImpl dat = new BillTypeMasterImpl();
   			accountUnitId = Integer.parseInt(request.getParameter("accountId"));
   			accountOfficeId = Integer.parseInt(request.getParameter("officeId"));
   			String financialYear = request.getParameter("financialYear");
   			int surveyNo = Integer.parseInt(request.getParameter("surveyNo"));
   			int assetAuction = Integer.parseInt(request.getParameter("assetAuction"));
   			Date auctionDate = dat.date_convertion(request.getParameter("auctionDate"));
   			int auctionNo= Integer.parseInt(request.getParameter("auctionNo"));
   			int auctionOffice = Integer.parseInt(request.getParameter("auctionedOffice"));
   			int auctionAmount = Integer.parseInt(request.getParameter("auctionAmount"));
   			int recoverYear = Integer.parseInt(request.getParameter("recoverYear"));
   			int month = Integer.parseInt(request.getParameter("month"));
   			String cashbook = request.getParameter("cashbook");
   			String[] receipt = cashbook.split(":"); 
   			int receiptNo = Integer.parseInt(receipt[0]);
   			String receiptdat = receipt[1];
   			Date receiptDate = dat.date_convertion(receiptdat);
   			System.out.println("no "+receiptNo+" receiptDate "+receiptDate);
   			int jvrYear = Integer.parseInt(request.getParameter("jvrYear"));
   			int jvrmonth = Integer.parseInt(request.getParameter("jvrmonth"));
   			String finalAdjust = request.getParameter("finalAdjust");
   			String[] journal = finalAdjust.split(":");
   			int journalNo = Integer.parseInt(journal[0]);
   			String journaldat = journal[1];
   			Date journalDate = dat.date_convertion(journaldat);
   			System.out.println("no "+journalNo+" journalDate "+journalDate);
   			
   			AuctionAssetsService add = new AuctionAssetImpl();   			   			
   			try {
				xml = add.addAssetAuctionVoucher(accountUnitId,accountOfficeId,financialYear,surveyNo,assetAuction,auctionDate,auctionNo,auctionOffice,auctionAmount,recoverYear,month,receiptNo,receiptDate,jvrYear,jvrmonth,journalNo,journalDate,userid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}  		
   		if(strCommand.equalsIgnoreCase("assetcode")){   			
   			xml = "";
   			accountUnitId = Integer.parseInt(request.getParameter("unitCode"));
   			accountOfficeId = Integer.parseInt(request.getParameter("officeCode"));
   			int surveyNo = Integer.parseInt(request.getParameter("surveyNo"));   			
   			AuctionAssetsService asset = new AuctionAssetImpl();    			
   			try {
   				xml = asset.assetCode(accountUnitId, accountOfficeId, surveyNo, "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("Get")){   			
   			xml = "";
   			accountUnitId = Integer.parseInt(request.getParameter("unitCode"));
   			accountOfficeId = Integer.parseInt(request.getParameter("officeCode"));   			
   			AuctionAssetsService view = new AuctionAssetImpl();    			
   			try {
				xml=view.viewDetails(accountUnitId,accountOfficeId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		
   		pw.write(xml);
		pw.flush();
		pw.close();	
	}

}

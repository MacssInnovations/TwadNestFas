package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CivilBills.servlets.BillTypeMasterImpl;

/**
 * Servlet implementation class PassOrderApproval
 */
public class AuctionAssetDone extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuctionAssetDone() {
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
   			try {
				xml = survey.surveyReportNo(unitCode, officeCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		
   		if(strCommand.equalsIgnoreCase("add")){  			
   			Map<String,Integer> map = new HashMap<String,Integer>();
   			BillTypeMasterImpl dat = new BillTypeMasterImpl();
   			accountUnitId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			accountOfficeId = Integer.parseInt(request.getParameter("cmbOffice_code"));
   			int surveyReportNo = Integer.parseInt(request.getParameter("surveyReportNo"));   			
   			Date auctionDate = dat.date_convertion(request.getParameter("auctionDate"));
   			int auctionOffice = Integer.parseInt(request.getParameter("auctionedOffice"));   			
   			String[] assetAuction = request.getParameterValues("h_assetcode");
   			String[] referenceNo = request.getParameterValues("h_referenceNo");
   			String[] referenceDate = request.getParameterValues("h_referenceDate");
   			String[] auctioneer = request.getParameterValues("h_auctioner");
   			String[] auctionAmount = request.getParameterValues("h_auctioAmt");
   			String[] remarks = request.getParameterValues("h_remarks");
   			AuctionAssetsService add = new AuctionAssetImpl();   			   			
   			try {   				
   				map = add.addAuctionDoneMaster(accountUnitId,accountOfficeId,surveyReportNo,auctionDate,auctionOffice,assetAuction,referenceNo,referenceDate,auctioneer,auctionAmount,remarks,userid);
   				if(map.get("first")!=0){
   					add.sendMessage(response,"The insertion into the Auction Master table Failed ","ok");
   				}else if(map.get("first")==0 && map.get("firsts")==0){
   					add.sendMessage(response,"The insertion into the Auction Transaction table Failed ","ok");
   				} else if(map.get("first")==0 && map.get("second")!=0){
   					add.sendMessage(response,"The Records are inserted into both table Successfully ","ok");   					
   				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block				
				e.printStackTrace();
			}
   		}   		
   		if(strCommand.equalsIgnoreCase("edit")){
   			xml = "";
   			assetCode = Integer.parseInt(request.getParameter("assetCode"));
   			AssetAnnaulImpl edit = new AssetAnnaulImpl();
   			try {
				xml = edit.editAssetAnnualValue(assetCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				xml="<response><status>fail</status></response>";
			}
   		}
   		
   		if(strCommand.equalsIgnoreCase("Update")){   			
   			xml = "";
   			accountUnitId = Integer.parseInt(request.getParameter("accUnitId"));
   			accountOfficeId = Integer.parseInt(request.getParameter("accOfficeId"));
   			assetCode = Integer.parseInt(request.getParameter("assetCode"));
   			String financialYear = request.getParameter("financialYear");
   			int year = Integer.parseInt(request.getParameter("year"));
   			int month = Integer.parseInt(request.getParameter("month"));
   			int day = Integer.parseInt(request.getParameter("day"));
   			int fairMarket = Integer.parseInt(request.getParameter("fairMarket"));
   			String remarks = request.getParameter("remarks");
   			AssetAnnaulImpl update = new AssetAnnaulImpl();
   			try {
				xml = update.updateAnnualAssetValue(accountUnitId,accountOfficeId,assetCode,financialYear,year,month,day,fairMarket,remarks,userid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				xml="<response><status>fail</status></response>";
			}
   		}
   		
   		if(strCommand.equalsIgnoreCase("assetcode")){   			
   			xml = "";
   			accountUnitId = Integer.parseInt(request.getParameter("unitCode"));
   			accountOfficeId = Integer.parseInt(request.getParameter("officeCode"));
   			int surveyNo = Integer.parseInt(request.getParameter("surveyNo"));   			
   			AuctionAssetsService asset = new AuctionAssetImpl();    			
   			try {
				xml = asset.assetCode(accountUnitId, accountOfficeId, surveyNo);
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
   			String surveyNo = request.getParameter("surveyNo");
   			String assetAuctioin = request.getParameter("assetAuctioin");
   			String category = request.getParameter("command");
   			AuctionAssetsService view = new AuctionAssetImpl();    			
   			try {
				xml=view.viewDetails(accountUnitId,accountOfficeId,surveyNo,assetAuctioin,category);
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

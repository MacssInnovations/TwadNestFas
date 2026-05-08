package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PassOrderApproval
 */
public class AssetAnnual extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssetAnnual() {
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
   		
   		if(strCommand.equalsIgnoreCase("get")){   			
   			xml = "";  			
   			if(!request.getParameter("accUnitId").equalsIgnoreCase("")){
   				accountUnitId = Integer.parseInt(request.getParameter("accUnitId"));
   			}
   			if(!request.getParameter("accOfficeId").equalsIgnoreCase("")){
   				accountOfficeId = Integer.parseInt(request.getParameter("accOfficeId"));
   			}
   			if(!request.getParameter("assetCode").equalsIgnoreCase("select")){
   				assetCode = Integer.parseInt(request.getParameter("assetCode"));
   			}
   			AssetAnnaulImpl detail = new AssetAnnaulImpl();
   			try {
				xml = detail.viewExistingDetails(accountUnitId,accountOfficeId,assetCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("assetCode")){
   			xml = "";
   			if(!request.getParameter("accUnitId").equalsIgnoreCase("")){
   				accountUnitId = Integer.parseInt(request.getParameter("accUnitId"));
   			}
   			if(!request.getParameter("accOfficeId").equalsIgnoreCase("")){
   				accountOfficeId = Integer.parseInt(request.getParameter("accOfficeId"));
   			}
   			AssetAnnaulImpl asset = new AssetAnnaulImpl();
   			try {
				xml = asset.loadAssetCode(accountUnitId,accountOfficeId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		
   		if(strCommand.equalsIgnoreCase("Add")){   			
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
   			String status = "L";
   			AssetAnnaulImpl add = new AssetAnnaulImpl();   			   			
   			try {
				xml = add.addAssetAnnualValue(accountUnitId,accountOfficeId,assetCode,financialYear,year,month,day,fairMarket,remarks,userid,status);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
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
   		
   		if(strCommand.equalsIgnoreCase("Delete")){   			
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
   			AssetAnnaulImpl delete = new AssetAnnaulImpl(); 
   			String status = "C";
   			try {
				xml = delete.deleteAnnualAssetValue(accountUnitId,accountOfficeId,assetCode,financialYear,year,month,day,fairMarket,remarks,userid,status);
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

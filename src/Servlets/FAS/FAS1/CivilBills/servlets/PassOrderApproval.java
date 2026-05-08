package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PassOrderApproval
 */
public class PassOrderApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassOrderApproval() {
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
   			//System.out.println("getttt");
   			xml = "";
   			BillTypeMasterImpl majorType = new BillMajorTypeImpl();
   			try {
				xml = majorType.viewExistingDetails();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("loadBillNo")){
   			xml = "";
   			//int billMajorType = Integer.parseInt(request.getParameter("billMajor"));
   			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			
   			BillMajorTypeImpl majorType = new BillMajorTypeImpl();
   			try {
				xml = majorType.loadBillNo(cmbAcc_UnitCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}if(strCommand.equalsIgnoreCase("loadbillDetails"))
   		{
   			xml="";
   			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
   			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
   			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
   			BillMajorTypeImpl majorType = new BillMajorTypeImpl();
   			try {
				xml = majorType.loadbillDetailsMTC(cmbOffice_code,cmbAcc_UnitCode,txtCB_Year,txtCB_Month);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e1.printStackTrace();
			}
   		}if(strCommand.equalsIgnoreCase("loadbillMTC"))
   		{
   			xml="";
   			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
   			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
   			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
   			int Bill_No = Integer.parseInt(request.getParameter("BillNo"));
   			BillMajorTypeImpl majorType = new BillMajorTypeImpl();
   			try {
				xml = majorType.loadbillMTC(cmbOffice_code,cmbAcc_UnitCode,txtCB_Year,txtCB_Month,Bill_No);
			System.out.println("xml"+xml);
   			} catch (Exception e1) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e1.printStackTrace();
			}
   		}if(strCommand.equalsIgnoreCase("savechangeOff"))
   		{
   			xml="";
   			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
   			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
   			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
   			int Bill_No = Integer.parseInt(request.getParameter("BillNo"));
   			int chan_Unit = Integer.parseInt(request.getParameter("chan_Unit"));
   			int chan_Office = Integer.parseInt(request.getParameter("chan_Office"));
   			
   			BillMajorTypeImpl majorType = new BillMajorTypeImpl();
   			try {
				xml = majorType.savechangeOff(cmbOffice_code,cmbAcc_UnitCode,txtCB_Year,txtCB_Month,Bill_No,chan_Unit,chan_Office);
			System.out.println("xml"+xml);
   			} catch (Exception e1) {
				// TODO Auto-generated catch block
				xml="<response><status>Failure</status></response>";
				e1.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("loadpassno")){
   			//System.out.println("loadpassno.....");
   			xml = "";
   			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
   			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			
   			BillMajorTypeImpl majorType = new BillMajorTypeImpl();
   			try {
				xml = majorType.loadpassNo(cmbOffice_code,cmbAcc_UnitCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("passOrderDetails")){   			
   			xml = "";
   		
   			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			int passNo = Integer.parseInt(request.getParameter("pasSplit"));
   			int pass_year = Integer.parseInt(request.getParameter("pass_amt"));
   			int pass_Month = Integer.parseInt(request.getParameter("Bill_no"));
   			
   			BillMajorTypeImpl majorType = new BillMajorTypeImpl();
   			try {
				xml = majorType.getPassOrderDetails(cmbAcc_UnitCode,passNo,pass_year,pass_Month);
				//System.out.println("xml "+xml);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		
   		if(strCommand.equalsIgnoreCase("update")){   			
   			//Date PassOrderDate=null;
   			xml = "";
   			BillMajorTypeImpl update = new BillMajorTypeImpl();
   			//int billMajorType = Integer.parseInt(request.getParameter("billMajor"));
   			//int billNo = Integer.parseInt(request.getParameter("billNo"));
   			//String micEntry = request.getParameter("micEntry");
   			int drawOffice = Integer.parseInt(request.getParameter("drawOff"));
   			String approve = request.getParameter("isApprove");   			
   			java.sql.Date approveDate = update.date_convertion(request.getParameter("approveDate"));
   			String rejectReason = request.getParameter("rejectReason");
   			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
   			int sancno = Integer.parseInt(request.getParameter("sancno"));
   			int year = Integer.parseInt(request.getParameter("year"));
   			int month = Integer.parseInt(request.getParameter("month"));
   			int passno = Integer.parseInt(request.getParameter("passno"));
   			 String passOrderDate=request.getParameter("passOrderDate");
   			 System.out.println("Passorder Date from Form is ::::::"+passOrderDate);
   			//Calendar c3;
  		/*  String[] sd=request.getParameter("passOrderDate").split("/");
            c3=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c3.getTime();
            PassOrderDate=new Date(d.getTime());*/
   			
   			//System.out.println("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);billNo,micEntry
   			try {
				xml = update.updatePassApproval(cmbOffice_code,drawOffice,approve,approveDate,rejectReason,userid,cmbAcc_UnitCode,sancno,year,month,passno,passOrderDate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		
   		if(strCommand.equalsIgnoreCase("delete")){   			
   			xml = "";
   			BillMajorTypeImpl update = new BillMajorTypeImpl();
   			//int billMajorType = Integer.parseInt(request.getParameter("billMajor"));
   			//int billNo = Integer.parseInt(request.getParameter("billNo"));
   			int drawOffice = Integer.parseInt(request.getParameter("drawOff"));
   			String approve = request.getParameter("isApprove");   			
   			java.sql.Date approveDate = update.date_convertion(request.getParameter("approveDate"));
   			String rejectReason = request.getParameter("rejectReason");
   			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
   			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
   			int sancno = Integer.parseInt(request.getParameter("sancno"));
   			//System.out.println("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
   			try {
				xml = update.deletePassApproval(cmbOffice_code,drawOffice,approve,approveDate,rejectReason,userid,cmbAcc_UnitCode,sancno);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		
   		pw.write(xml);
		pw.flush();
		pw.close();	
	}

}

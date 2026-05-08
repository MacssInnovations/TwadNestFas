package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Bill_Major_Type_Mst
 */
public class Bill_Major_Type_Mst extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bill_Major_Type_Mst() {
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
   			/*pw.write(xml);
   			pw.flush();
   			pw.close();	*/
   			System.out.println("databse connection error");
   			return;
		}   		
   		if(strCommand.equalsIgnoreCase("Get")){   			
   			xml="";   			
   			try {
   				BillTypeMasterImpl load = new BillTypeMasterImpl();
				xml = load.viewExistingDetails();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}			
   		}
   		if(strCommand.equalsIgnoreCase("Add")){   			
   			xml = "";
   			String billDesc = request.getParameter("billDesc");
   			String remarks = request.getParameter("remarks");   			
   			try {   				
   				BillTypeMasterImpl add = new BillTypeMasterImpl();
   				xml = add.addBillType(billDesc,remarks,userid);   				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("edit")){   			
   			int billCode = Integer.parseInt(request.getParameter("billCode"));
   			xml = "";   			
   			try {
   				BillTypeMasterImpl edit = new BillTypeMasterImpl();
				xml = edit.editBillType(billCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("Update")){   			
   			xml = "";
   			int billCode = Integer.parseInt(request.getParameter("billCode"));
   			String billDesc = request.getParameter("billDesc");
   			String remarks = request.getParameter("remarks");
   			//String check = request.getParameter("check");
   			try {
   				BillTypeMasterImpl bill = new BillTypeMasterImpl();
				xml = bill.updateBillType(billCode, billDesc, remarks, userid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><status>fail</status></response>";
				e.printStackTrace();
			}
   		}
   		if(strCommand.equalsIgnoreCase("delete")){
   			xml = "";
   			int billCode = Integer.parseInt(request.getParameter("billCode"));
   			String billDesc = request.getParameter("billDesc");
   			String remarks = request.getParameter("remarks");
   			try {
   				BillTypeMasterImpl bill = new BillTypeMasterImpl();
				xml = bill.deleteBillType(billCode,billDesc,remarks,userid);
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

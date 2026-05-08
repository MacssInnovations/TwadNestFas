package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CivilBills.servlets.BillMajorTypeImpl;

/**
 * Servlet implementation class sanction_proceed_approval
 */
public class sanction_proceed_approval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sanction_proceed_approval() {
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
        
        
	}

}

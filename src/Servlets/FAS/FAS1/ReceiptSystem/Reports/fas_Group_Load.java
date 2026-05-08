package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class fas_Group_Load
 */
public class fas_Group_Load extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE =
	        "text/html; charset=windows-1252";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fas_Group_Load() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test Group Servlt");
		response.setContentType(CONTENT_TYPE);
	        
	        /**
	         * Set Content Type 
	         */
	        PrintWriter out = response.getWriter();
	        String CONTENT_TYPE = "text/xml; charset=windows-1252";
	        response.setContentType(CONTENT_TYPE);
	        
	        
	        /**
	         * Session Checking 
	         */
	        HttpSession session=request.getSession(false);
	        try
	         {
	             
	             if(session==null)
	             {
	                 System.out.println(request.getContextPath()+"/index.jsp");
	                 response.sendRedirect(request.getContextPath()+"/index.jsp");
	                 return;
	             }
	             System.out.println(session);
	                 
	         }catch(Exception e)
	         {
	         System.out.println("Redirect Error :"+e);
	         }
	        
	        /** Get User ID */
	        String userid=(String)session.getAttribute("UserId");
	        String empid = userid.substring(4,userid.length());
	        
	        System.out.println("Empid -------------->"+empid);
	        
	        /**
	         * Variables Declaration 
	         */        
	        Connection con=null;
	        
	        /** Combo Loading */
	        PreparedStatement ps=null;        
	        ResultSet rs=null;  
	        
	       /**
	        * Database Connection 
	        */
	        try{
	                           ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                           String ConnectionString="";
	                           String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                           String strdsn=rs1.getString("Config.DSN");
	                           String strhostname=rs1.getString("Config.HOST_NAME");
	                           String strportno=rs1.getString("Config.PORT_NUMBER");
	                           String strsid=rs1.getString("Config.SID");
	                           String strdbusername=rs1.getString("Config.USER_NAME");
	                           String strdbpassword=rs1.getString("Config.PASSWORD");
	                           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                           Class.forName(strDriver.trim());
	                           con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	          }
	          catch(Exception e)
	          {
	              System.out.println("Exception in opening connection :"+e);
	          }
	          
	          /** xml */
	          String xml="";
	         String Command=request.getParameter("Command");
	System.out.println("cmd .....  "+Command);
 if (Command.equalsIgnoreCase("loadGroup")){
    	 xml="<response><command>loadGroup</command>";
         int sec_id=Integer.parseInt(request.getParameter("sec_id"));
           
          String sql_all="SELECT GROUP_ID,  GROUP_NAME FROM fas_mst_groups WHERE section_id=19 ORDER BY group_id"+sec_id; 
          int cnt=0;
    
    
     
         try
         {
             ps=con.prepareStatement(sql_all);
             rs=ps.executeQuery();
             while (rs.next()) 
             {
                        
                 xml=xml+"<GROUP_ID>"+rs.getInt("GROUP_ID")+"</GROUP_ID>";  
                 xml=xml+"<GROUP_NAME>"+rs.getString("GROUP_NAME")+"</GROUP_NAME>";                      
             
                cnt++;
             }
      
      
            if(cnt==0) {
                xml=xml+"<flag>NoData</flag>";   
            }
            else{
                xml=xml+"<flag>Success</flag>";   
            }
        }
        catch(Exception e) {
            xml=xml+"<flag>Failure</flag>";    
            System.out.println(e);
	}
 xml=xml+"</response>";               
 out.println(xml);
 System.out.println(xml);      
	   }
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

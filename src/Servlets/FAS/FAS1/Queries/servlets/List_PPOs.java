package Servlets.FAS.FAS1.Queries.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class List_PPOs
 */
public class List_PPOs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252"; 
	
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Inside method doget");
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;        
   	    PreparedStatement ps=null;
     
        try
        {
    	   ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
           String ConnectionString="";

           String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
           String strdsn=rs.getString("Config.DSN");
           String strhostname=rs.getString("Config.HOST_NAME");
           String strportno=rs.getString("Config.PORT_NUMBER");
           String strsid=rs.getString("Config.SID");
           String strdbusername=rs.getString("Config.USER_NAME");
           String strdbpassword=rs.getString("Config.PASSWORD");
              
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
           Class.forName(strDriver.trim());
           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           try
           {
        	   statement=connection.createStatement();
               connection.clearWarnings();
           }
           catch(SQLException e)
           {
               System.out.println("Exception in creating statement:"+e);
           }
        }
        catch(Exception e)
        {
           System.out.println("Exception in openeing connection:"+e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
  	
        HttpSession session=request.getSession(false);
        try
        {
	        if(session==null)
	        {
	            System.out.println(request.getContextPath()+"/index.jsp");
	            response.sendRedirect(request.getContextPath()+"/index.jsp");
	        }
	        System.out.println(session);
        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
        
        
        String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        //Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
    
        if(strCommand.equals("GetList"))
        { 
        	int count=0;
        	System.out.println("\n*************\nGetList\n**************\n");
            xml="<response><command>GetList</command>";
            try 
            {
                System.out.println("inside GetList");
                
                /*String selectQuery="SELECT b.OFFICE_ID,b.OFFICE_NAME, "+
				     " (SELECT OFFICE_LEVEL_NAME "+
				  " FROM COM_MST_OFFICE_LEVELS a "+
				  "WHERE a.OFFICE_LEVEL_ID=b.OFFICE_LEVEL_ID "+
				  " ) AS LevelName "+
				" FROM COM_MST_OFFICES b "+
				" WHERE PENSION_PAYMENT_OFFICE='Y' ";   */  
                
                String selectQuery="SELECT b.OFFICE_ID,b.OFFICE_NAME "+
			" FROM COM_MST_OFFICES b "+
			" WHERE b.PENSION_PAYMENT_OFFICE='Y' ";   
         	ps=connection.prepareStatement(selectQuery);
			System.out.println(selectQuery);
			result=ps.executeQuery();	
           try
             {
        	   System.out.println("inside try");
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<OFFICE_ID>" + result.getString("OFFICE_ID") + "</OFFICE_ID>"; 
                	 xml += "<OFFICE_NAME>" + result.getString("OFFICE_NAME") + "</OFFICE_NAME>"; 
                	 count++;
                 }

                 xml =xml+ "<exists>"+valExists+"</exists>";
                 xml =xml+ "<count>"+count+"</count>";
             }
           catch(Exception e)
             {
            	 System.out.println("Exception in getting values from DB -GET List : " + e);
             }
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } 
       
        
       System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

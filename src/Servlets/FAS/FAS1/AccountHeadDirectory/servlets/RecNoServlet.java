/*package view;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;

public class RecNoServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    response.setContentType(CONTENT_TYPE);
    System.out.println("RecServlet called");
    PrintWriter out = response.getWriter();
    String strdate="";
    int month=0;
    
    try
            {
            strdate=request.getParameter("crd");
            System.out.println(strdate);
                      
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
            
             try
                {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con=DriverManager.getConnection("jdbc:odbc:fas");
                String query="select max(RECEIPT_NO)  from RECEIPT_MASTER where 
                PreparedStatement ps=con.prepareStatement(query);
                ResultSet rs=null;
                ps.setString(1,strdate);
                rs=ps.executeQuery();
                }
                catch(Exception e)
                {}
    out.close();
  }
}*/
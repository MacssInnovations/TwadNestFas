package Servlets.FAS.FAS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class ServletListClass extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        System.out.println("servlet called");
        String strCommand = "";
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "";
        //String sql2="";

        sql = "select * from PMS_MST_CONTRACTOR_CLASS Order By Regn_Class_Id";


        try {
            Connection connection = null;

            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

            Statement statement = null;
            try {
                response.setContentType(CONTENT_TYPE);
                PrintWriter out = response.getWriter();
                int serialNo = 0;
                statement = connection.createStatement();
                System.out.println("executing : " + sql);
                ResultSet results = statement.executeQuery(sql);

                out.println("<html>");
                out.println("<head><title>List Records</title></head>");
                //out.println("<script language=\"javascript\" src=\"ServletUpdates.js\"></script>");
                out.println("<body bgcolor=rgb(184,224,204)>");
                out.println("<table border=\"1\" width=\"100%\">");
                out.println("<th>Sl.No</th><th>Class Id.</th><th>Regn.Class.Desc</th><th>Last Updated Date</th><tbody>");

                while (results.next()) {
                    //String regno=results.getString("value");
                    out.println("<tr><td>" + (++serialNo) + "</td>");
                    out.println("<td>" + results.getInt("Regn_Class_Id") +
                                "</td>");
                    out.println("<td>" + results.getString("Regn_Class_Desc") +
                                "</td>");
                    out.println("<td>" + results.getDate("Last_Updated_Date") +
                                "</td>");

                    //out.println("<td><input type=\"checkbox\" onclick=\"showSubLedgerTypes('" +  + "')\"></td>");
                    //out.println("<td><input type=\"checkbox\" onclick=\"deleteAccountHeadCode('" +  + "')\"></td>");
                    //out.println("<td><input type=\"checkbox\" onclick=\"EditAccountHeadCode('" +  + "')\"></td></tr>");
                }
                out.println("</tbody>");
                if (serialNo == 0) {
                    // no records found
                    out.println("<center><b>Result : No Records Found</b></center>");
                }
                out.println("</body></html>");
                out.close();
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
            }

        } catch (Exception e) {
            System.out.println("error while opening connection " + e);
        }


    }
}

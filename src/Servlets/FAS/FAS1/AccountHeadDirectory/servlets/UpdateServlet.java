package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

public class UpdateServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("servlet called");
        String strType = "";
        try {
            strType = request.getParameter("Type");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "";

        if (strType.equals("ShowSLTypes")) {
            int acctheadcode = Integer.parseInt(request.getParameter("AHC"));
            sql =
 "select * from FAS_SUB_LEDGER_TYPE where SUB_LEDGER_TYPE_CODE  in (select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from FAS_ACCOUNT_HEAD_MASTER where ACCOUNT_HEAD_CODE='" +
   acctheadcode + "' and SUB_LEDGER_TYPE_APPLY='Y'))";
        }

        else if (strType.equals("DeleteAHC")) {
            int acctheadcode = Integer.parseInt(request.getParameter("AHC"));
            sql =
 "delete from FAS_ACCOUNT_HEAD_MASTER where  ACCOUNT_HEAD_CODE='" +
   acctheadcode + "'";


        }


        try {
            Connection connection = null;
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connection = DriverManager.getConnection("jdbc:odbc:fas");
            Statement statement = null;
            try {
                response.setContentType(CONTENT_TYPE);
                PrintWriter out = response.getWriter();
                int serialNo = 0;
                statement = connection.createStatement();
                System.out.println("executing : " + sql);
                ResultSet results = statement.executeQuery(sql);
                out.println("<html>");
                out.println("<head><title>SLTypes</title></head>");
                out.println("<script language=\"javascript\" src=\"UpdateRecords.js\"></script>");
                out.println("<body>");
                out.println("<table border=\"1\" width=\"100%\">");
                out.println("<th>SI.No.</th><th>SubLedgerCode</th><th>SubLedgerDescription</th><tbody>");
                while (results.next()) {
                    System.out.println("yes");
                    // String ahc=results.getString("Account_Head_Code");
                    String subledgercode =
                        results.getString("SUB_LEDGER_TYPE_CODE");
                    System.out.println("subledgercode");
                    String subledgerdesc =
                        results.getString("SUB_LEDGER_TYPE_DESC");
                    System.out.println("subledgerdesc");
                    out.println("<tr><td>" + (++serialNo) + "</td>");
                    out.println("<td>" + subledgercode + "</td>");
                    out.println("<td>" + subledgerdesc + "</td>");

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

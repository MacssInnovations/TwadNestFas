package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class report
 */
public class report extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public report() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        // TODO Auto-generated method stub
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        Controller obj = new Controller();
        String command =
            request.getParameter("command"); //Command ->view,insert etc
        if (command == null || command.equals(""))
            command = "no command";

        String input_value = request.getParameter("input_value"); //input value
        if (input_value == null || input_value.equals(""))
            input_value = "0";

        String process_code =
            request.getParameter("process_code"); // process code 1-> Ben Select ,2->Meter Select
        if (process_code == null || process_code.equals(""))
            process_code = "0";


        String xml = " ";
        try {
            con = obj.con();

            stmt = con.createStatement();
            obj.createStatement(con);
            HttpSession session = request.getSession(false);
            String userid = "0", Office_id = "0";
            try {
                userid = (String)session.getAttribute("UserId");
            } catch (Exception e) {
                userid = "0";
            }

            if (userid == null) {
                userid = "0";
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

            Office_id =
                    (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
                                             "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" +
                                             userid + "')"), 1));

            if (Office_id.equals("0"))
                Office_id = "5082";
            /**************************************************************************************************/

            if (process_code.equals("1"))
                xml =
 obj.combo_lkup("DISTRICT_CODE", "DISTRICT_NAME", "COM_MST_DISTRICTS",
                "WHERE DISTRICT_CODE IN (SELECT DISTRICT_CODE FROM PMS_DCB_DIV_DIST_MAP WHERE OFFICE_ID=" +
                Office_id + ") order by DISTRICT_NAME", 2, "--Select--");

            if (process_code.equals("2"))
                xml =
 obj.combo_lkup("block_sno", "block_name", "com_mst_blocks",
                "where district_code =" + input_value + " order by block_name",
                2, "--Select--");

            PrintWriter pr = response.getWriter();
            pr.write(xml);
            pr.flush();
            pr.close();
            obj.conClose(con);
        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();


        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        // TODO Auto-generated method stub
    }

}

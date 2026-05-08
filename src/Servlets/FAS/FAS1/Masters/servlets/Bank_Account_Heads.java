package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Bank_Account_Heads extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        Connection con = null;
        ResultSet rs = null, rs2 = null;
        PreparedStatement ps = null, ps2 = null;
        //String xml="";

        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";

        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        //String update_user="x";
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        System.out.println(update_user + "..." + ts);
        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        if (strCommand.equalsIgnoreCase("checkCode")) {

            String xml = "";
            xml = "<response><command>checkCode</command>";
            int txtAcc_HeadCode = 0;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            try {

                ps2 =
 con.prepareStatement("select AC_HEAD_CODE from FAS_MST_BANK_ACCOUNT_HEADS where AC_HEAD_CODE=?");
                ps2.setInt(1, txtAcc_HeadCode);
                rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    System.out.println("duplicate exist");
                    xml = xml + "<flag>exist</flag>";
                } else {
                    System.out.println("duplicate not exist");
                    ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                    ps.setInt(1, txtAcc_HeadCode);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        xml =
 xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
   rs.getString("ACCOUNT_HEAD_DESC") + "</hdesc>";
                    } else {
                        System.out.println("No record found");
                        xml = xml + "<flag>failure</flag>";
                    }

                    ps.close();
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        if (strCommand.equalsIgnoreCase("Add")) {
            int txtAcc_HeadCode = 0;
            int cmbBankId = 0;
            String cmbOperation_mode = "";
            String xml = "";
            xml = "<response><command>Add</command>";
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                System.out.println("(txtAcc_HeadCode);.." + txtAcc_HeadCode);
                cmbBankId =
                        Integer.parseInt(request.getParameter("cmbBankId"));
                System.out.println("(cmbBankId);.." + cmbBankId);
                cmbOperation_mode = request.getParameter("cmbOperation_mode");
                System.out.println("(cmbOperation_mode);.." +
                                   cmbOperation_mode);
                String sql_Insert =
                    "insert into FAS_MST_BANK_ACCOUNT_HEADS(BANK_ID,AC_OPERATIONAL_MODE_ID,AC_HEAD_CODE,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?)";
                ps = con.prepareStatement(sql_Insert);
                ps.setInt(1, cmbBankId);
                ps.setString(2, cmbOperation_mode);
                ps.setInt(3, txtAcc_HeadCode);
                ps.setString(4, update_user);
                ps.setTimestamp(5, ts);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception in insert..." + e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Update")) {
            int txtAcc_HeadCode = 0;
            int cmbBankId = 0;
            String cmbOperation_mode = "";
            String xml = "";
            xml = "<response><command>Update</command>";
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                System.out.println("(txtAcc_HeadCode);.." + txtAcc_HeadCode);
                cmbBankId =
                        Integer.parseInt(request.getParameter("cmbBankId"));
                System.out.println("(cmbBankId);.." + cmbBankId);
                cmbOperation_mode = request.getParameter("cmbOperation_mode");
                System.out.println("(cmbOperation_mode);.." +
                                   cmbOperation_mode);
                String sql_Insert =
                    "update FAS_MST_BANK_ACCOUNT_HEADS set BANK_ID=?,AC_OPERATIONAL_MODE_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where AC_HEAD_CODE=?";
                ps = con.prepareStatement(sql_Insert);
                ps.setInt(1, cmbBankId);
                ps.setString(2, cmbOperation_mode);
                ps.setString(3, update_user);
                ps.setTimestamp(4, ts);
                ps.setInt(5, txtAcc_HeadCode);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception in insert..." + e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            int txtAcc_HeadCode = 0;
            int cmbBankId = 0;
            String cmbOperation_mode = "";
            String xml = "";
            xml = "<response><command>Delete</command>";
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                System.out.println("(txtAcc_HeadCode);.." + txtAcc_HeadCode);

                String sql_Insert =
                    "delete from FAS_MST_BANK_ACCOUNT_HEADS where AC_HEAD_CODE=?";
                ps = con.prepareStatement(sql_Insert);
                ps.setInt(1, txtAcc_HeadCode);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception in insert..." + e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

    }
}

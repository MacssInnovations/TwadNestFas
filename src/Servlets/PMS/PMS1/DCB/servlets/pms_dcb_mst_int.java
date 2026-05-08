package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class pms_dcb_mst_int extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        System.out.println("asasasas");
        String command_var = "";
        String xmlvariable = "";
        PreparedStatement ps, ps_max_int, ps_ben_desc, ps_ins_check;
        Connection connection = null;
        ResultSet res, res_max_int, rs_ben_desc, res_ins_check;
        res = null;
        String INT_WEF = "";
        String ACTIVE_STATUS = "";
        String ben_desc_val = "";
        int BENEFICIARY_TYPE = 0;
        int INT_RATE = 0;
        int INT_ID = 0;
        int ben_desc_id = 0;
        int Interest_Id = 0;
        int countbentype = 0;
        int countinscheck = 0;
        try {
            command_var = request.getParameter("command");
            System.out.println(command_var);
        } catch (Exception e) {
            System.out.println("Error in reterival the command");
        }
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strdsn = rs.getString("Config.DSN"); //jdbc:oracle:thin:
            String strhostname =
                rs.getString("Config.HOST_NAME"); //10.163.0.58
            String strportno = rs.getString("Config.PORT_NUMBER"); //1521
            String strsid = rs.getString("Config.SID"); //twadnest
            String strDriver =
                rs.getString("Config.DATA_BASE_DRIVER"); //oracle.jdbc.OracleDriver
            String strdbusername = rs.getString("Config.USER_NAME"); //twadpms
            String strdbpassword =
                rs.getString("Config.PASSWORD"); //twadpms123
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

            try {
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        try {
            Interest_Id =
                    Integer.parseInt(request.getParameter("Interest_Id"));

        } catch (Exception e) {
            System.out.println("Error in reterving the Interest_Id");
        }
        try {
            BENEFICIARY_TYPE =
                    Integer.parseInt(request.getParameter("Beneficiary_Type"));

        } catch (Exception e) {
            System.out.println("Error in reterving the BENEFICIARY_TYPE");
        }
        try {
            INT_RATE = Integer.parseInt(request.getParameter("Interest_Rate"));

        } catch (Exception e) {
            System.out.println("Error in reterving the INT_RATE");
        }
        try {
            INT_WEF = request.getParameter("Interest_wef");

        } catch (Exception e) {
            System.out.println("Error in reterving the INT_WEF");
        }

        try {
            ACTIVE_STATUS = request.getParameter("status");

        } catch (Exception e) {
            System.out.println("Error in reterving the ACTIVE_STATUS");
        }

        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        String userid = (String)session.getAttribute("UserId");
        System.out.println("Session id is:" + userid);
        if (command_var.equalsIgnoreCase("loadbeneficiary")) {

            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiary</command>";
            try {
                ps =
  connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");
                res = ps.executeQuery();
                System.out.println("loadbeneficiary");
                while (res.next()) {
                    System.out.println("loadbeneficiary");
                    System.out.println(res.getString("BEN_TYPE_DESC"));
                    System.out.println(res.getInt("BEN_TYPE_ID"));

                    xmlvariable +=
                            "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID") +
                            "</BEN_TYPE_ID>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_DESC>" + res.getString("BEN_TYPE_DESC") +
                            "</BENEFICIARY_TYPE_DESC>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("add")) {
            xmlvariable = "<response>";
            xmlvariable += "<command>add</command>";
            System.out.println(BENEFICIARY_TYPE);
            System.out.println(INT_RATE);
            System.out.println(INT_WEF);
            System.out.println(ACTIVE_STATUS);
            xmlvariable = "<response>";
            xmlvariable += "<command>add</command>";
            try {

                ps_ins_check =
                        connection.prepareStatement("select count(*) as countinscheck from  PMS_DCB_MST_INT where BENEFICIARY_TYPE=? and ACTIVE_STATUS='A'");
                ps_ins_check.setInt(1, BENEFICIARY_TYPE);
                res_ins_check = ps_ins_check.executeQuery();
                if (res_ins_check.next()) {
                    System.out.println(res_ins_check.getInt("countinscheck"));
                    countinscheck = res_ins_check.getInt("countinscheck");

                }

            } catch (Exception e) {

                System.out.println("Error in reteriving the count");
            }
            if (countinscheck == 0) {
                try {


                    ps =
  connection.prepareStatement("insert into PMS_DCB_MST_INT(BENEFICIARY_TYPE,INT_RATE,INT_WEF,UPDATED_BY_USER_ID,UPDATED_TIME,ACTIVE_STATUS) values(?,?,to_date(?,'DD/MM/YYYY'),?,SYSTIMESTAMP,?)");
                    ps.setInt(1, BENEFICIARY_TYPE);
                    ps.setInt(2, INT_RATE);
                    ps.setString(3, INT_WEF);
                    ps.setString(4, userid);
                    ps.setString(5, ACTIVE_STATUS);
                    ps.executeUpdate();
                    System.out.println("Inserted");

                    ps_max_int =
                            connection.prepareStatement("select max(INT_ID) as INT_ID from PMS_DCB_MST_INT");
                    res_max_int = ps_max_int.executeQuery();
                    if (res_max_int.next()) {
                        System.out.println(res_max_int.getInt("INT_ID"));
                        INT_ID = res_max_int.getInt("INT_ID");
                    }

                    ps_ben_desc =
                            connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE where BEN_TYPE_ID= ?");
                    ps_ben_desc.setInt(1, BENEFICIARY_TYPE);
                    rs_ben_desc = ps_ben_desc.executeQuery();
                    if (rs_ben_desc.next()) {
                        System.out.println(rs_ben_desc.getString("BEN_TYPE_DESC"));
                        ben_desc_id = rs_ben_desc.getInt("BEN_TYPE_ID");
                        ben_desc_val = rs_ben_desc.getString("BEN_TYPE_DESC");
                    }
                    xmlvariable += "<INT_ID>" + INT_ID + "</INT_ID>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + ben_desc_id + "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_DESC>" + ben_desc_val + "</BENEFICIARY_TYPE_DESC>";
                    xmlvariable += "<INT_RATE>" + INT_RATE + "</INT_RATE>";
                    xmlvariable += "<INT_WEF>" + INT_WEF + "</INT_WEF>";
                    xmlvariable +=
                            "<ACTIVE_STATUS>" + ACTIVE_STATUS + "</ACTIVE_STATUS>";
                    xmlvariable += "<countinscheck>0</countinscheck>";
                    xmlvariable += "<flag>success</flag>";
                } catch (Exception e) {
                    System.out.println("Error in addition");
                    xmlvariable += "<flag>failure</flag>";
                    xmlvariable += "<countinscheck>0</countinscheck>";
                    System.out.println(e + "not inserted!");
                }
            } else {
                xmlvariable += "<countinscheck>1</countinscheck>";
                xmlvariable += "<flag>success</flag>";

            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("get")) {
            System.out.println("getting value");
            xmlvariable = "<response>";
            xmlvariable += "<command>get</command>";

            try {
                ps =
  connection.prepareStatement("select INT_ID,\n" + "PMS_DCB_BEN_TYPE.BEN_TYPE_DESC as BENEFICIARY_TYPE_DESC,\n" +
                              "PMS_DCB_BEN_TYPE.BEN_TYPE_ID as BENEFICIARY_TYPE_ID,\n" +
                              "ACTIVE_STATUS,\n" + "INT_RATE,\n" +
                              "INT_WEF \n" + "from \n" + "PMS_DCB_MST_INT\n" +
                              "join\n" + "PMS_DCB_BEN_TYPE\n" + "on \n" +
                              "PMS_DCB_BEN_TYPE.BEN_TYPE_ID=PMS_DCB_MST_INT.BENEFICIARY_TYPE\n" +
                              "where\n" + "ACTIVE_STATUS='A'");
                res = ps.executeQuery();
                while (res.next()) {
                    xmlvariable +=
                            "<INT_ID>" + res.getInt("INT_ID") + "</INT_ID>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + res.getInt("BENEFICIARY_TYPE_ID") +
                            "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_DESC>" + res.getString("BENEFICIARY_TYPE_DESC") +
                            "</BENEFICIARY_TYPE_DESC>";
                    xmlvariable +=
                            "<ACTIVE_STATUS>" + res.getString("ACTIVE_STATUS") +
                            "</ACTIVE_STATUS>";
                    xmlvariable +=
                            "<INT_RATE>" + res.getInt("INT_RATE") + "</INT_RATE>";
                    xmlvariable +=
                            "<INT_WEF>" + res.getDate("INT_WEF") + "</INT_WEF>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }

            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("update")) {
            System.out.println("Update values");
            xmlvariable = "<response>";
            xmlvariable += "<command>update</command>";
            System.out.println(BENEFICIARY_TYPE);
            System.out.println(INT_RATE);
            System.out.println(INT_WEF);
            System.out.println(ACTIVE_STATUS);
            System.out.println(Interest_Id);

            try {

                ps =
  connection.prepareStatement("update PMS_DCB_MST_INT set BENEFICIARY_TYPE=?,INT_RATE=?,INT_WEF=to_date(?,'DD/MM/YYYY'),ACTIVE_STATUS=?,UPDATED_TIME=SYSTIMESTAMP,UPDATED_BY_USER_ID=? where INT_ID =?");
                ps.setInt(1, BENEFICIARY_TYPE);
                ps.setInt(2, INT_RATE);
                ps.setString(3, INT_WEF);
                ps.setString(4, ACTIVE_STATUS);
                ps.setString(5, userid);
                ps.setInt(6, Interest_Id);
                ps.executeUpdate();

                ps_ben_desc =
                        connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE where BEN_TYPE_ID= ?");
                ps_ben_desc.setInt(1, BENEFICIARY_TYPE);
                rs_ben_desc = ps_ben_desc.executeQuery();
                if (rs_ben_desc.next()) {
                    System.out.println(rs_ben_desc.getString("BEN_TYPE_DESC"));
                    ben_desc_id = rs_ben_desc.getInt("BEN_TYPE_ID");
                    ben_desc_val = rs_ben_desc.getString("BEN_TYPE_DESC");
                }
                xmlvariable += "<INT_ID>" + Interest_Id + "</INT_ID>";
                xmlvariable +=
                        "<BENEFICIARY_TYPE_ID>" + ben_desc_id + "</BENEFICIARY_TYPE_ID>";
                xmlvariable +=
                        "<BENEFICIARY_TYPE_DESC>" + ben_desc_val + "</BENEFICIARY_TYPE_DESC>";
                xmlvariable +=
                        "<ACTIVE_STATUS>" + ACTIVE_STATUS + "</ACTIVE_STATUS>";
                xmlvariable += "<INT_RATE>" + INT_RATE + "</INT_RATE>";
                xmlvariable += "<INT_WEF>" + INT_WEF + "</INT_WEF>";
                xmlvariable += "<flag>success</flag>";

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not updated!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("delete")) {
            xmlvariable = "<response>";
            xmlvariable += "<command>delete</command>";
            try {

                ps =
  connection.prepareStatement("delete from PMS_DCB_MST_INT where INT_ID=?");
                ps.setInt(1, Interest_Id);
                ps.executeUpdate();
                System.out.println("deleted");
                xmlvariable += "<INT_ID>" + Interest_Id + "</INT_ID>";

                xmlvariable += "<flag>success</flag>";
            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not deleted!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("bentypecheck")) {
            System.out.println("bentypecheck");
            xmlvariable = "<response>";
            xmlvariable += "<command>bentypecheck</command>";
            System.out.println("Beneficiary_Type" + BENEFICIARY_TYPE);

            try {
                ps =
  connection.prepareStatement("select count(*) as countbentype from  PMS_DCB_MST_INT where BENEFICIARY_TYPE=? and ACTIVE_STATUS='A'");
                ps.setInt(1, BENEFICIARY_TYPE);
                res = ps.executeQuery();

                if (res.next()) {
                    System.out.println(res.getInt("countbentype"));
                    countbentype = res.getInt("countbentype");

                }
                if (countbentype > 0) {
                    xmlvariable += "<countbentype>1</countbentype>";
                    xmlvariable += "<flag>success</flag>";
                } else {
                    xmlvariable += "<countbentype>0</countbentype>";
                    xmlvariable += "<flag>success</flag>";
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
        System.out.println(xmlvariable);
        out.println(xmlvariable);
    }
}

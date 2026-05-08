package Servlets.PMS.PMS1.DCB.servlets;

import Servlets.Security.classes.UserProfile;

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

public class Pms_Dcb_Beneficiary_Metre_Report extends HttpServlet {
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
        String command_var = "";
        String xmlvariable = "";
        Connection connection = null;
        PreparedStatement ps_oid, ps, ps_div;
        ResultSet result_new, res, res_div;
        String officename = "";
        String tempquery = "";
        int empid = 0;
        int oid = 0;
        int flagvariable;
        int Beneficiary_type = 0;
        int flagname = 0;
        int Beneficiary_sno = 0;
        int SubDivision = 0;
        int countbenname = 0;
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
            System.out.println("Paid by master");
            try {
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        try {
            command_var = request.getParameter("command");
            System.out.println(command_var);
        } catch (Exception e) {
            System.out.println("Error in reteriving the command");

        }
        try {
            Beneficiary_type =
                    Integer.parseInt(request.getParameter("Beneficiary_type"));
        } catch (Exception e) {
            System.out.println("Error in reteriving the Beneficiary_type");

        }
        try {
            SubDivision =
                    Integer.parseInt(request.getParameter("SubDivision"));
        } catch (Exception e) {
            System.out.println("Error in reteriving the SubDivision");

        }
        try {
            Beneficiary_sno =
                    Integer.parseInt(request.getParameter("Beneficiary_Name"));
        } catch (Exception e) {
            System.out.println("Error in reteriving the Beneficiary_sno");

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

        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("emp id::" + empProfile.getEmployeeId());

        empid = empProfile.getEmployeeId();
        //  empid=2513;


        System.out.println("Empid" + empid);
        try {
            System.out.println("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=" +
                               empid);
            ps_oid =
                    connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
            ps_oid.setInt(1, empid);
            result_new = ps_oid.executeQuery();
            System.out.println("Testing value");
            if (result_new.next()) {
                System.out.println("inside condition");
                oid = result_new.getInt("OFFICE_ID");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (command_var.equalsIgnoreCase("divisionname")) {
            xmlvariable = "<response>";
            xmlvariable += "<command>divisionname</command>";
            try {


                ps_oid =
                        connection.prepareStatement("select OFFICE_NAME from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?");
                ps_oid.setInt(1, oid);
                result_new = ps_oid.executeQuery();
                if (result_new.next()) {
                    officename = result_new.getString("OFFICE_NAME");
                    xmlvariable +=
                            "<officename>" + officename + "</officename>";
                    xmlvariable += "<flag>success</flag>";
                    System.out.println(officename);
                } else {
                    xmlvariable += "<officename>0</officename>";
                    xmlvariable += "<flag>success</flag>";

                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadbeneficiarytype")) {
            System.out.println("loadbeneficiary");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiarytype</command>";
            try {
                ps =
  connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");

                res = ps.executeQuery();
                System.out.println("loadbeneficiarytype");
                while (res.next()) {
                    System.out.println("loadbeneficiarytype");
                    System.out.println(res.getInt("BEN_TYPE_ID"));
                    System.out.println(res.getString("BEN_TYPE_DESC"));
                    System.out.println(res.getString("BEN_TYPE_SDESC"));

                    xmlvariable +=
                            "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID") +
                            "</BEN_TYPE_ID>";
                    xmlvariable +=
                            "<BEN_TYPE_DESC>" + res.getString("BEN_TYPE_DESC") +
                            "</BEN_TYPE_DESC>";
                    xmlvariable +=
                            "<BEN_TYPE_SDESC>" + res.getString("BEN_TYPE_SDESC") +
                            "</BEN_TYPE_SDESC>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");

            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadsubdivision")) {
            System.out.println("loadsubdivision");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadsubdivision</command>";

            try {
                ps_div =
                        connection.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID AS EMPLOYEE_ID ,\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW.SUBDIVISION_OFFICE_ID AS SUBDIVISION_OFFICE_ID ,\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID AS OFFICE_ID,\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID AS DIVISION_OFFICE_ID,\n" +
                                                    "OFFICE_LEVEL_ID AS OFFICE_LEVEL_ID ,\n" +
                                                    "OFFICE_NAME AS OFFICE_NAME\n" +
                                                    "from HRM_EMP_CURRENT_POSTING\n" +
                                                    "JOIN\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW\n" +
                                                    "ON\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID=HRM_EMP_CURRENT_POSTING.OFFICE_ID\n" +
                                                    "AND\n" +
                                                    "OFFICE_LEVEL_ID='SD'\n" +
                                                    "WHERE\n" +
                                                    "HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=?");
                //empid=2513
                ps_div.setInt(1, empid);
                res_div = ps_div.executeQuery();
                flagvariable = 0;
                while (res_div.next()) {
                    flagvariable = 1;
                    xmlvariable +=
                            "<SUBDIVISION_OFFICE_ID>" + res_div.getInt("SUBDIVISION_OFFICE_ID") +
                            "</SUBDIVISION_OFFICE_ID>";
                    xmlvariable +=
                            "<OFFICE_NAME>" + res_div.getString("OFFICE_NAME") +
                            "</OFFICE_NAME>";
                    xmlvariable += "<flag>success</flag>";


                }
                if (flagvariable == 0) {
                    xmlvariable +=
                            "<SUBDIVISION_OFFICE_ID>-1</SUBDIVISION_OFFICE_ID>";
                    xmlvariable += "<OFFICE_NAME>No data</OFFICE_NAME>";
                    xmlvariable += "<flag>success</flag>";


                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadbeneficiaryname")) {
            System.out.println("loadbeneficiaryname");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiaryname</command>";

            System.out.println("oid" + oid);
            try {
                ps =
  connection.prepareStatement("select BENEFICIARY_SNO,\n" + "BENEFICIARY_TYPE_ID,\n" +
                              "BENEFICIARY_NAME,\n" + "OTHERS_PRIVATE_SNO,\n" +
                              "VILLAGE_PANCHAYAT_SNO,\n" + "URBANLB_SNO,\n" +
                              "PMS_DCB_MST_BENEFICIARY.OFFICE_ID\n" +
                              "from PMS_DCB_MST_BENEFICIARY\n" + "where\n" +
                              "BENEFICIARY_TYPE_ID=?\n" + "and\n" +
                              "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?");
                //officeid=5340
                ps.setInt(1, Beneficiary_type);
                ps.setInt(2, oid);


                res = ps.executeQuery();
                flagname = 0;
                while (res.next()) {
                    flagname = 1;
                    System.out.println(res.getInt("BENEFICIARY_SNO"));
                    System.out.println(res.getString("BENEFICIARY_NAME"));

                    xmlvariable +=
                            "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") +
                            "</BENEFICIARY_SNO>";
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") +
                            "</BENEFICIARY_NAME>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + res.getInt("BENEFICIARY_TYPE_ID") +
                            "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<OTHERS_PRIVATE_SNO>" + res.getInt("OTHERS_PRIVATE_SNO") +
                            "</OTHERS_PRIVATE_SNO>";
                    xmlvariable +=
                            "<VILLAGE_PANCHAYAT_SNO>" + res.getInt("VILLAGE_PANCHAYAT_SNO") +
                            "</VILLAGE_PANCHAYAT_SNO>";
                    xmlvariable +=
                            "<URBANLB_SNO>" + res.getInt("URBANLB_SNO") +
                            "</URBANLB_SNO>";

                    xmlvariable += "<flag>success</flag>";

                }
                if (flagname == 0) {
                    xmlvariable +=
                            "<BENEFICIARY_SNO>" + -1 + "</BENEFICIARY_SNO>";
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + "No Data" + "</BENEFICIARY_NAME>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + -1 + "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<OTHERS_PRIVATE_SNO>" + -1 + "</OTHERS_PRIVATE_SNO>";
                    xmlvariable +=
                            "<VILLAGE_PANCHAYAT_SNO>" + -1 + "</VILLAGE_PANCHAYAT_SNO>";
                    xmlvariable += "<URBANLB_SNO>" + -1 + "</URBANLB_SNO>";


                    xmlvariable += "<flag>success</flag>";

                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("benreport")) {

            xmlvariable = "<response>";
            xmlvariable += "<command>benreport</command>";
            try {
                ps =
  connection.prepareStatement("select\n" + "count(*)as COUNTBENNAMES\n" +
                              "from\n" + "PMS_DCB_MST_BENEFICIARY_METRE\n" +
                              "JOIN\n" + "PMS_SCH_MASTER\n" + "ON\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO\n" +
                              "JOIN\n" + "COM_MST_ALL_OFFICES_VIEW\n" +
                              "ON\n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID\n" +
                              "where\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=?\n" +
                              "and\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID=?\n" +
                              "and\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=?\n" +
                              "and\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.OFFICE_ID=?\n");
                ps.setInt(1, Beneficiary_type);
                ps.setInt(2, SubDivision);
                ps.setInt(3, Beneficiary_sno);
                ps.setInt(4, oid);
                res = ps.executeQuery();
                if (res.next()) {
                    countbenname = res.getInt("COUNTBENNAMES");
                    System.out.println("countbenname" + countbenname);
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
            if (countbenname > 0) {
                try {
                    ps =
  connection.prepareStatement("select\n" + "PMS_DCB_MST_BENEFICIARY_METRE.METRE_SNO AS METRE_SNO,\n" +
                              "METRE_LOCATION,\n" + "TARIFF_RATE,\n" +
                              "SUB_DIV_ID,\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO,\n" +
                              "PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,\n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_NAME AS OFFICE_NAME,\n" +
                              "METRE_FIXED,\n" + "METRE_WORKING\n" + "from\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE\n" + "JOIN\n" +
                              "PMS_SCH_MASTER\n" + "ON\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO\n" +
                              "JOIN\n" + "COM_MST_ALL_OFFICES_VIEW\n" +
                              "ON\n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID\n" +
                              "where\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=?\n" +
                              "and\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID=?\n" +
                              "and\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=?\n" +
                              "and\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.OFFICE_ID=?\n");

                    ps.setInt(1, Beneficiary_type);
                    ps.setInt(2, SubDivision);
                    ps.setInt(3, Beneficiary_sno);
                    ps.setInt(4, oid);


                    res = ps.executeQuery();
                    flagname = 0;
                    while (res.next()) {
                        flagname = 1;
                        xmlvariable +=
                                "<METRE_SNO>" + res.getInt("METRE_SNO") +
                                "</METRE_SNO>";
                        xmlvariable +=
                                "<METRE_LOCATION>" + res.getString("METRE_LOCATION") +
                                "</METRE_LOCATION>";
                        xmlvariable +=
                                "<OFFICE_NAME>" + res.getString("OFFICE_NAME") +
                                "</OFFICE_NAME>";
                        xmlvariable +=
                                "<SCH_NAME>" + res.getString("SCH_NAME") +
                                "</SCH_NAME>";
                        xmlvariable +=
                                "<TARIFF_RATE>" + res.getDouble("TARIFF_RATE") +
                                "</TARIFF_RATE>";
                        xmlvariable +=
                                "<METRE_FIXED>" + res.getString("METRE_FIXED") +
                                "</METRE_FIXED>";
                        xmlvariable +=
                                "<METRE_WORKING>" + res.getString("METRE_WORKING") +
                                "</METRE_WORKING>";
                        xmlvariable +=
                                "<countbenname>" + 1 + "</countbenname>";

                        xmlvariable += "<flag>success</flag>";

                    }


                } catch (Exception e) {
                    xmlvariable += "<flag>failure</flag>";
                    System.out.println(e + "not reterived!");
                }

            } else {
                xmlvariable += "<countbenname>" + 0 + "</countbenname>";
                xmlvariable += "<flag>success</flag>";
            }
            xmlvariable += "</response>";


        } else if (command_var.equalsIgnoreCase("subreport")) {
            System.out.println("subreport");
            System.out.println("benreport");
            System.out.println(Beneficiary_type);
            System.out.println(SubDivision);
            System.out.println(Beneficiary_sno);
            System.out.println(oid);
            xmlvariable = "<response>";
            xmlvariable += "<command>subreport</command>";

            if ((Beneficiary_type == 0) && (SubDivision != 0) &&
                (Beneficiary_sno == 0)) {

                tempquery +=
                        "where PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID=? AND PMS_DCB_MST_BENEFICIARY_METRE.OFFICE_ID=?";
                System.out.println(tempquery);

            }
            if ((Beneficiary_type != 0) && (SubDivision != 0) &&
                (Beneficiary_sno == 0)) {
                tempquery +=
                        "where PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=? and PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID=? AND PMS_DCB_MST_BENEFICIARY_METRE.OFFICE_ID=?";
                System.out.println(tempquery);

            }
            try {
                ps =
  connection.prepareStatement("select  count(*) AS COUNTBENNAMES from\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE  \n" +
                              "JOIN  \n" + "PMS_SCH_MASTER  \n" + "ON  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO  \n" +
                              "JOIN  \n" + "COM_MST_ALL_OFFICES_VIEW  \n" +
                              "ON  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID  \n" +
                              tempquery);
                if ((Beneficiary_type == 0) && (SubDivision != 0) &&
                    (Beneficiary_sno == 0)) {

                    ps.setInt(1, SubDivision);
                    ps.setInt(2, oid);
                }
                if ((Beneficiary_type != 0) && (SubDivision != 0) &&
                    (Beneficiary_sno == 0)) {

                    ps.setInt(1, Beneficiary_type);
                    ps.setInt(2, SubDivision);
                    ps.setInt(3, oid);

                }

                res = ps.executeQuery();
                if (res.next()) {
                    countbenname = res.getInt("COUNTBENNAMES");
                    System.out.println("countbenname" + countbenname);
                }
            } catch (Exception e) {
                System.out.println("Error");
            }


            if (countbenname > 0) {

                try {
                    ps =
  connection.prepareStatement("select  \n" + "PMS_DCB_MST_BENEFICIARY_METRE.METRE_SNO AS METRE_SNO,  \n" +
                              "METRE_LOCATION,  \n" + "TARIFF_RATE,  \n" +
                              "SUB_DIV_ID,  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO,  \n" +
                              "PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_NAME AS OFFICE_NAME,  \n" +
                              "METRE_FIXED,  \n" + "METRE_WORKING  \n" +
                              "from  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE  \n" +
                              "JOIN  \n" + "PMS_SCH_MASTER  \n" + "ON  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO  \n" +
                              "JOIN  \n" + "COM_MST_ALL_OFFICES_VIEW  \n" +
                              "ON  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID  \n" +
                              tempquery);

                    if ((Beneficiary_type == 0) && (SubDivision != 0) &&
                        (Beneficiary_sno == 0)) {

                        System.out.println("hai");
                        ps.setInt(1, SubDivision);
                        ps.setInt(2, oid);
                    }
                    if ((Beneficiary_type != 0) && (SubDivision != 0) &&
                        (Beneficiary_sno == 0)) {
                        System.out.println("hai1");
                        ps.setInt(1, Beneficiary_type);
                        ps.setInt(2, SubDivision);
                        ps.setInt(3, oid);

                    }


                    res = ps.executeQuery();
                    flagname = 0;
                    while (res.next()) {
                        flagname = 1;
                        xmlvariable +=
                                "<METRE_SNO>" + res.getInt("METRE_SNO") +
                                "</METRE_SNO>";
                        xmlvariable +=
                                "<METRE_LOCATION>" + res.getString("METRE_LOCATION") +
                                "</METRE_LOCATION>";
                        xmlvariable +=
                                "<OFFICE_NAME>" + res.getString("OFFICE_NAME") +
                                "</OFFICE_NAME>";
                        xmlvariable +=
                                "<SCH_NAME>" + res.getString("SCH_NAME") +
                                "</SCH_NAME>";
                        xmlvariable +=
                                "<TARIFF_RATE>" + res.getDouble("TARIFF_RATE") +
                                "</TARIFF_RATE>";
                        xmlvariable +=
                                "<METRE_FIXED>" + res.getString("METRE_FIXED") +
                                "</METRE_FIXED>";
                        xmlvariable +=
                                "<METRE_WORKING>" + res.getString("METRE_WORKING") +
                                "</METRE_WORKING>";
                        xmlvariable +=
                                "<countbenname>" + 1 + "</countbenname>";

                        xmlvariable += "<flag>success</flag>";

                    }


                } catch (Exception e) {
                    xmlvariable += "<flag>failure</flag>";
                    System.out.println(e + "not reterived!");
                }

            } else {
                xmlvariable += "<countbenname>" + 0 + "</countbenname>";
                xmlvariable += "<flag>success</flag>";
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("bentype")) {

            xmlvariable = "<response>";
            xmlvariable += "<command>bentype</command>";
            try {
                ps =
  connection.prepareStatement("select  \n" + "count(*) AS COUNTBENNAMES\n" +
                              "from  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE  \n" +
                              "JOIN  \n" + "PMS_SCH_MASTER  \n" + "ON  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO  \n" +
                              "JOIN  \n" + "COM_MST_ALL_OFFICES_VIEW  \n" +
                              "ON  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID  \n" +
                              "where  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=?\n" +
                              "and  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.OFFICE_ID=?");
                ps.setInt(1, Beneficiary_type);
                ps.setInt(2, oid);
                res = ps.executeQuery();
                if (res.next()) {
                    countbenname = res.getInt("COUNTBENNAMES");
                    System.out.println("countbenname" + countbenname);
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
            if (countbenname > 0) {
                try {
                    ps =
  connection.prepareStatement("select  \n" + "PMS_DCB_MST_BENEFICIARY_METRE.METRE_SNO AS METRE_SNO,  \n" +
                              "METRE_LOCATION,  \n" + "TARIFF_RATE,  \n" +
                              "SUB_DIV_ID,  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO,  \n" +
                              "PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_NAME AS OFFICE_NAME,  \n" +
                              "METRE_FIXED,  \n" + "METRE_WORKING  \n" +
                              "from  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE  \n" +
                              "JOIN  \n" + "PMS_SCH_MASTER  \n" + "ON  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO  \n" +
                              "JOIN  \n" + "COM_MST_ALL_OFFICES_VIEW  \n" +
                              "ON  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID  \n" +
                              "where  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=?\n" +
                              "and  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.OFFICE_ID=?");

                    ps.setInt(1, Beneficiary_type);
                    ps.setInt(2, oid);


                    res = ps.executeQuery();
                    flagname = 0;
                    while (res.next()) {
                        flagname = 1;
                        xmlvariable +=
                                "<METRE_SNO>" + res.getInt("METRE_SNO") +
                                "</METRE_SNO>";
                        xmlvariable +=
                                "<METRE_LOCATION>" + res.getString("METRE_LOCATION") +
                                "</METRE_LOCATION>";
                        xmlvariable +=
                                "<OFFICE_NAME>" + res.getString("OFFICE_NAME") +
                                "</OFFICE_NAME>";
                        xmlvariable +=
                                "<SCH_NAME>" + res.getString("SCH_NAME") +
                                "</SCH_NAME>";
                        xmlvariable +=
                                "<TARIFF_RATE>" + res.getDouble("TARIFF_RATE") +
                                "</TARIFF_RATE>";
                        xmlvariable +=
                                "<METRE_FIXED>" + res.getString("METRE_FIXED") +
                                "</METRE_FIXED>";
                        xmlvariable +=
                                "<METRE_WORKING>" + res.getString("METRE_WORKING") +
                                "</METRE_WORKING>";
                        xmlvariable +=
                                "<countbenname>" + 1 + "</countbenname>";

                        xmlvariable += "<flag>success</flag>";

                    }


                } catch (Exception e) {
                    xmlvariable += "<flag>failure</flag>";
                    System.out.println(e + "not reterived!");
                }

            } else {
                xmlvariable += "<countbenname>" + 0 + "</countbenname>";
                xmlvariable += "<flag>success</flag>";
            }
            xmlvariable += "</response>";


        } else if (command_var.equalsIgnoreCase("benname")) {

            xmlvariable = "<response>";
            xmlvariable += "<command>benname</command>";
            System.out.println("benname");
            try {
                ps =
  connection.prepareStatement("select  \n" + "count(*) AS COUNTBENNAMES\n" +
                              "from  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE  \n" +
                              "JOIN  \n" + "PMS_SCH_MASTER  \n" + "ON  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO  \n" +
                              "JOIN  \n" + "COM_MST_ALL_OFFICES_VIEW  \n" +
                              "ON  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID  \n" +
                              "where  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=?\n" +
                              "and  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=?\n" +
                              "and  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.OFFICE_ID=?");
                ps.setInt(1, Beneficiary_type);
                ps.setInt(2, Beneficiary_sno);
                ps.setInt(3, oid);
                res = ps.executeQuery();
                if (res.next()) {
                    countbenname = res.getInt("COUNTBENNAMES");
                    System.out.println("countbenname" + countbenname);
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
            if (countbenname > 0) {
                try {
                    ps =
  connection.prepareStatement("select  \n" + "PMS_DCB_MST_BENEFICIARY_METRE.METRE_SNO AS METRE_SNO,  \n" +
                              "METRE_LOCATION,  \n" + "TARIFF_RATE,  \n" +
                              "SUB_DIV_ID,  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO,  \n" +
                              "PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_NAME AS OFFICE_NAME,  \n" +
                              "METRE_FIXED,  \n" + "METRE_WORKING  \n" +
                              "from  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE  \n" +
                              "JOIN  \n" + "PMS_SCH_MASTER  \n" + "ON  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO  \n" +
                              "JOIN  \n" + "COM_MST_ALL_OFFICES_VIEW  \n" +
                              "ON  \n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID  \n" +
                              "where  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=?\n" +
                              "and  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=?\n" +
                              "and  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.OFFICE_ID=?");

                    ps.setInt(1, Beneficiary_type);
                    ps.setInt(2, Beneficiary_sno);
                    ps.setInt(3, oid);


                    res = ps.executeQuery();
                    flagname = 0;
                    while (res.next()) {
                        flagname = 1;
                        xmlvariable +=
                                "<METRE_SNO>" + res.getInt("METRE_SNO") +
                                "</METRE_SNO>";
                        xmlvariable +=
                                "<METRE_LOCATION>" + res.getString("METRE_LOCATION") +
                                "</METRE_LOCATION>";
                        xmlvariable +=
                                "<OFFICE_NAME>" + res.getString("OFFICE_NAME") +
                                "</OFFICE_NAME>";
                        xmlvariable +=
                                "<SCH_NAME>" + res.getString("SCH_NAME") +
                                "</SCH_NAME>";
                        xmlvariable +=
                                "<TARIFF_RATE>" + res.getDouble("TARIFF_RATE") +
                                "</TARIFF_RATE>";
                        xmlvariable +=
                                "<METRE_FIXED>" + res.getString("METRE_FIXED") +
                                "</METRE_FIXED>";
                        xmlvariable +=
                                "<METRE_WORKING>" + res.getString("METRE_WORKING") +
                                "</METRE_WORKING>";
                        xmlvariable +=
                                "<countbenname>" + 1 + "</countbenname>";

                        xmlvariable += "<flag>success</flag>";

                    }


                } catch (Exception e) {
                    xmlvariable += "<flag>failure</flag>";
                    System.out.println(e + "not reterived!");
                }

            } else {
                xmlvariable += "<countbenname>" + 0 + "</countbenname>";
                xmlvariable += "<flag>success</flag>";
            }
            xmlvariable += "</response>";


        }
        out.println(xmlvariable);
        System.out.println(xmlvariable);
    }
}

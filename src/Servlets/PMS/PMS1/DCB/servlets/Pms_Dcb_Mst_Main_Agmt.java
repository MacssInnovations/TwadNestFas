package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Types;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Pms_Dcb_Mst_Main_Agmt extends HttpServlet {
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
        response.setHeader("Cache-Control", "no-cache");
        System.out.println("sdsdsd");
        String command_var = "";
        String xmlvariable = "";
        int Beneficiary_Sno = 0;
        String Agreement_Code = "";
        int Agreement_Qty = 0;
        int Agreement_Sno = 0;
        int Agreement_No = 0;
        int ben_desc_id = 0;
        String Agreement_DT_Wef = "";
        String Agreement_DT_from = "";
        String Agreement_DT_to = "";
        String Remarks = "";
        String ben_desc_val = "";
        Connection connection = null;
        PreparedStatement ps, ps1, ps3;
        ResultSet res = null, res3 = null;
        ps = null;
        ps1 = null;
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
            Beneficiary_Sno =
                    Integer.parseInt(request.getParameter("Beneficiary_Sno"));
            Agreement_Code = request.getParameter("Agreement_Code");
            Agreement_Qty =
                    Integer.parseInt(request.getParameter("Agreement_Qty"));
            Agreement_DT_Wef = request.getParameter("Agreement_DT_Wef");
            Agreement_DT_from = request.getParameter("Agreement_DT_from");
            Agreement_DT_to = request.getParameter("Agreement_DT_to");
            Remarks = request.getParameter("Remarks");


        } catch (Exception e) {
            System.out.println("Error in reterival:" + e);
        }
        if (command_var.equalsIgnoreCase("add")) {
            xmlvariable = "<response>";
            xmlvariable += "<command>add</command>";
            System.out.println("The remarks value =" + Remarks);
            try {
                ps =
  connection.prepareStatement("insert into PMS_DCB_MST_MAINT_AGMT(BENEFICIARY_SNO,AGREEMENT_CODE,AGREEMENT_QTY,AGREEMENT_DT_FROM,AGREEMENT_DT_TO,AGREEMENT_DT,REMARKS,UPDATED_BY_USER_ID,UPDATED_TIME) values(?,?,?,to_date(?,'DD/MM/YYYY'),to_date(?,'DD/MM/YYYY'),to_date(?,'DD/MM/YYYY'),?,'user',SYSTIMESTAMP)");
                ps.setInt(1, Beneficiary_Sno);
                ps.setString(2, Agreement_Code);
                ps.setInt(3, Agreement_Qty);
                ps.setString(4, Agreement_DT_Wef);
                ps.setString(5, Agreement_DT_from);
                ps.setString(6, Agreement_DT_to);
                ps.setString(7, Remarks);

                ps.executeUpdate();
                System.out.println("Inserted");

                System.out.println("hai");
                ps1 =
 connection.prepareStatement("select max(AGREEMENT_SNO) as AGREEMENT_SNO from PMS_DCB_MST_MAINT_AGMT");
                System.out.println(ps1);
                res = ps1.executeQuery();
                if (res.next()) {
                    System.out.println(res.getInt("AGREEMENT_SNO"));
                    Agreement_Sno = res.getInt("AGREEMENT_SNO");
                }
                ps3 =
 connection.prepareStatement("select BENEFICIARY_SNO,BENEFICIARY_NAME FROM PMS_DCB_MST_BENEFICIARY where BENEFICIARY_SNO= ?");
                ps3.setInt(1, Beneficiary_Sno);
                ///System.out.println(ps2);
                res3 = ps3.executeQuery();
                if (res3.next()) {

                    ben_desc_id = res3.getInt("BENEFICIARY_SNO");
                    ben_desc_val = res3.getString("BENEFICIARY_NAME");
                }
                xmlvariable +=
                        "<Agreement_Sno>" + Agreement_Sno + "</Agreement_Sno>";
                xmlvariable +=
                        "<Beneficiary_Sno>" + ben_desc_id + "</Beneficiary_Sno>";
                xmlvariable +=
                        "<Beneficiary_name>" + ben_desc_val + "</Beneficiary_name>";
                xmlvariable +=
                        "<Agreement_Code>" + Agreement_Code + "</Agreement_Code>";
                xmlvariable +=
                        "<Agreement_Qty>" + Agreement_Qty + "</Agreement_Qty>";
                xmlvariable +=
                        "<Agreement_DT_Wef>" + Agreement_DT_Wef + "</Agreement_DT_Wef>";
                xmlvariable +=
                        "<Agreement_DT_from>" + Agreement_DT_from + "</Agreement_DT_from>";
                xmlvariable +=
                        "<Agreement_DT_to>" + Agreement_DT_to + "</Agreement_DT_to>";
                if (Remarks.equals("")) {

                    xmlvariable += "<Remarks>-</Remarks>";

                } else {
                    xmlvariable += "<Remarks>" + Remarks + "</Remarks>";
                }
                xmlvariable += "<flag>success</flag>";
            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not inserted!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("get")) {
            System.out.println("getting value");

            xmlvariable = "<response>";
            xmlvariable += "<command>get</command>";
            try {
                ps =
  connection.prepareStatement("select AGREEMENT_SNO,PMS_DCB_MST_MAINT_AGMT.BENEFICIARY_SNO AS BENEFICIARY_SNO ,PMS_DCB_MST_BENEFICIARY.BENEFICIARY_NAME AS BENEFICIARY_NAME,AGREEMENT_CODE,AGREEMENT_QTY,AGREEMENT_DT_FROM,AGREEMENT_DT_TO,AGREEMENT_DT,REMARKS\n" +
                              " from PMS_DCB_MST_MAINT_AGMT\n" + "join\n" +
                              "PMS_DCB_MST_BENEFICIARY\n" + "on \n" +
                              "PMS_DCB_MST_MAINT_AGMT.BENEFICIARY_SNO=PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO\n" +
                              "ORDER BY AGREEMENT_SNO");
                res = ps.executeQuery();

                while (res.next()) {

                    System.out.println(res.getInt("AGREEMENT_SNO"));
                    System.out.println(res.getInt("BENEFICIARY_SNO"));
                    System.out.println(res.getString("AGREEMENT_CODE"));
                    System.out.println(res.getInt("AGREEMENT_QTY"));
                    System.out.println(res.getDate("AGREEMENT_DT_FROM"));
                    System.out.println(res.getDate("AGREEMENT_DT_TO"));
                    System.out.println(res.getDate("AGREEMENT_DT"));
                    System.out.println(res.getString("REMARKS"));
                    xmlvariable +=
                            "<Agreement_Sno>" + res.getInt("AGREEMENT_SNO") +
                            "</Agreement_Sno>";
                    xmlvariable +=
                            "<Beneficiary_Sno>" + res.getInt("BENEFICIARY_SNO") +
                            "</Beneficiary_Sno>";
                    // xmlvariable += "<Beneficiary_name>" + ben_desc_val + "</Beneficiary_name>";
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") +
                            "</BENEFICIARY_NAME>";
                    xmlvariable +=
                            "<Agreement_Code>" + res.getString("AGREEMENT_CODE") +
                            "</Agreement_Code>";
                    xmlvariable +=
                            "<Agreement_Qty>" + res.getInt("AGREEMENT_QTY") +
                            "</Agreement_Qty>";
                    xmlvariable +=
                            "<Agreement_DT_Wef>" + res.getDate("AGREEMENT_DT_FROM") +
                            "</Agreement_DT_Wef>";
                    xmlvariable +=
                            "<Agreement_DT_from>" + res.getDate("AGREEMENT_DT_TO") +
                            "</Agreement_DT_from>";
                    xmlvariable +=
                            "<Agreement_DT_to>" + res.getDate("AGREEMENT_DT") +
                            "</Agreement_DT_to>";
                    String remarkss1 = res.getString("REMARKS");
                    if (remarkss1 == null) {

                        xmlvariable += "<Remarks>-</Remarks>";

                    } else {
                        xmlvariable += "<Remarks>" + remarkss1 + "</Remarks>";
                    }
                    //  xmlvariable += "<Remarks>" + res.getString("REMARKS") + "</Remarks>";
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
            try {
                Agreement_No =
                        Integer.parseInt(request.getParameter("Agreement_No"));
                System.out.println(Agreement_No);
                ps =
  connection.prepareStatement("update PMS_DCB_MST_MAINT_AGMT set BENEFICIARY_SNO=?,AGREEMENT_CODE=?,AGREEMENT_QTY=?,AGREEMENT_DT_FROM=to_date(?,'DD/MM/YYYY'),AGREEMENT_DT_TO=to_date(?,'DD/MM/YYYY'),AGREEMENT_DT=to_date(?,'DD/MM/YYYY'),REMARKS=? where AGREEMENT_SNO =?");
                ps.setInt(1, Beneficiary_Sno);
                ps.setString(2, Agreement_Code);
                ps.setInt(3, Agreement_Qty);
                ps.setString(4, Agreement_DT_Wef);
                ps.setString(5, Agreement_DT_from);
                ps.setString(6, Agreement_DT_to);
                ps.setString(7, Remarks);
                ps.setInt(8, Agreement_No);

                ps.executeUpdate();

                System.out.println("updated");
                ps3 =
 connection.prepareStatement("select BENEFICIARY_SNO,BENEFICIARY_NAME FROM PMS_DCB_MST_BENEFICIARY where BENEFICIARY_SNO= ?");
                ps3.setInt(1, Beneficiary_Sno);

                res3 = ps3.executeQuery();
                if (res3.next()) {

                    ben_desc_id = res3.getInt("BENEFICIARY_SNO");
                    ben_desc_val = res3.getString("BENEFICIARY_NAME");
                }
                xmlvariable +=
                        "<Agreement_No>" + Agreement_No + "</Agreement_No>";
                xmlvariable +=
                        "<Beneficiary_Sno>" + ben_desc_id + "</Beneficiary_Sno>";
                xmlvariable +=
                        "<BENEFICIARY_NAME>" + ben_desc_val + "</BENEFICIARY_NAME>";
                xmlvariable +=
                        "<Agreement_Code>" + Agreement_Code + "</Agreement_Code>";
                xmlvariable +=
                        "<Agreement_Qty>" + Agreement_Qty + "</Agreement_Qty>";
                xmlvariable +=
                        "<Agreement_DT_Wef>" + Agreement_DT_Wef + "</Agreement_DT_Wef>";
                xmlvariable +=
                        "<Agreement_DT_from>" + Agreement_DT_from + "</Agreement_DT_from>";
                xmlvariable +=
                        "<Agreement_DT_to>" + Agreement_DT_to + "</Agreement_DT_to>";
                if (Remarks.equals("")) {

                    xmlvariable += "<Remarks>-</Remarks>";

                } else {
                    xmlvariable += "<Remarks>" + Remarks + "</Remarks>";
                }

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
                Agreement_No =
                        Integer.parseInt(request.getParameter("Agreement_No"));
                ps =
  connection.prepareStatement("delete from PMS_DCB_MST_MAINT_AGMT where AGREEMENT_SNO=?");
                ps.setInt(1, Agreement_No);
                ps.executeUpdate();
                System.out.println("deleted");
                xmlvariable +=
                        "<Agreement_No>" + Agreement_No + "</Agreement_No>";
                xmlvariable +=
                        "<Beneficiary_Sno>" + Beneficiary_Sno + "</Beneficiary_Sno>";
                xmlvariable +=
                        "<Agreement_Code>" + Agreement_Code + "</Agreement_Code>";
                xmlvariable +=
                        "<Agreement_Qty>" + Agreement_Qty + "</Agreement_Qty>";
                xmlvariable +=
                        "<Agreement_DT_Wef>" + Agreement_DT_Wef + "</Agreement_DT_Wef>";
                xmlvariable +=
                        "<Agreement_DT_from>" + Agreement_DT_from + "</Agreement_DT_from>";
                xmlvariable +=
                        "<Agreement_DT_to>" + Agreement_DT_to + "</Agreement_DT_to>";
                xmlvariable += "<Remarks>" + Remarks + "</Remarks>";

                xmlvariable += "<flag>success</flag>";
            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not deleted!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadbeneficiarytype")) {
            System.out.println("loadbeneficiary");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiarytype</command>";
            try {
                ps =
  connection.prepareStatement("select BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY order by BENEFICIARY_SNO");

                res = ps.executeQuery();
                System.out.println("loadbeneficiarytype");
                while (res.next()) {
                    System.out.println("loadbeneficiarytype");
                    System.out.println(res.getInt("BENEFICIARY_SNO"));
                    System.out.println(res.getString("BENEFICIARY_NAME"));


                    xmlvariable +=
                            "<BEN_TYPE_ID>" + res.getInt("BENEFICIARY_SNO") +
                            "</BEN_TYPE_ID>";
                    xmlvariable +=
                            "<BEN_TYPE_DESC>" + res.getString("BENEFICIARY_NAME") +
                            "</BEN_TYPE_DESC>";

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

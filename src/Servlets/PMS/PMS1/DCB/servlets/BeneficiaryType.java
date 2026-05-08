package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.Security.classes.UserProfile;

public class BeneficiaryType extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        try {
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";

        int sno = 0;
        String sdesc = null;
        String desc = null;
        String adr = null;
        String prvlb = null;


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
        int empid = empProfile.getEmployeeId();
        int oid = 0;
        try {
            PreparedStatement ps =
                connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
            ps.setInt(1, empid);
            result = ps.executeQuery();
            if (result.next()) {
                oid = result.getInt("OFFICE_ID");
            }
            result.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");


        /////////////////////// Command Parameters //////////////////////////
        try {
            strCommand = request.getParameter("command");
            System.out.println("strCommand : " + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////////////////////////////////////


        /////////////////////// Other Parameters //////////////////////////
        try {
            sno = Integer.parseInt(request.getParameter("sno"));
            System.out.println("sno : " + sno);
        } catch (Exception e) {
            System.out.println("Exception fetching sno ===> " + e);
        }


        try {
            sdesc = request.getParameter("sdesc");
            System.out.println("sdesc : " + sdesc);
        } catch (Exception e) {
            System.out.println("Exception fetching sdesc ===> " + e);
        }


        try {
            desc = request.getParameter("desc");
            System.out.println("desc : " + desc);
        } catch (Exception e) {
            System.out.println("Exception fetching desc ===> " + e);
        }


        try {
            prvlb = request.getParameter("prvlb");
            System.out.println("prvlb : " + prvlb);
        } catch (Exception e) {
            System.out.println("Exception fetching prvlb ===> " + e);
        }


        try {
            adr = request.getParameter("adr");
            System.out.println("adr : " + adr);
        } catch (Exception e) {
            System.out.println("Exception fetching adr ===> " + e);
        }

        ///////////////////////////////////////////////////////////////////////


        /*******************************************************
         * 						DELETE
         *******************************************************/
        if (strCommand.equalsIgnoreCase("Delete")) {
            System.out.println("\n*************\nDelete\n**************\n");
            xml = "<response><command>Delete</command>";
            try {

                String sqlDelPriv =
                    "DELETE FROM PMS_DCB_MST_PRIVCAT " + "WHERE PCAT_SNO= ?";
                PreparedStatement psPrv =
                    connection.prepareStatement(sqlDelPriv);
                psPrv.setInt(1, sno);
                psPrv.executeUpdate();


                String sqlDelTrf =
                    "DELETE FROM PMS_DCB_MST_TARIFF " + "WHERE BENEFICIARY_TYPE= ?";
                PreparedStatement psTrf =
                    connection.prepareStatement(sqlDelTrf);
                psTrf.setInt(1, sno);
                psTrf.executeUpdate();


                String sqlDelInt =
                    "DELETE FROM PMS_DCB_MST_INT " + "WHERE BENEFICIARY_TYPE= ?";
                PreparedStatement psInt =
                    connection.prepareStatement(sqlDelInt);
                psInt.setInt(1, sno);
                psInt.executeUpdate();


                String sqlDelete =
                    "DELETE FROM PMS_DCB_BEN_TYPE " + "WHERE BEN_TYPE_ID= ?";
                PreparedStatement ps = connection.prepareStatement(sqlDelete);
                ps.setInt(1, sno);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<sno>" + sno + "</sno>";
            } catch (Exception e1) {
                System.out.println("Exception in Deleting record ===> " + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        /*******************************************************
         * 						UPDATE
         *******************************************************/

        else if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("\n*************\nUpdate\n**************\n");
            xml = "<response><command>Update</command>";


            int dup = 0;
            try {
                result =
                        statement.executeQuery("SELECT COUNT(*) AS dup " + "FROM PMS_DCB_BEN_TYPE " +
                                               "WHERE (LOWER(BEN_TYPE_SDESC) = '" +
                                               sdesc.toLowerCase() + "' " +
                                               "OR LOWER(BEN_TYPE_DESC) = '" +
                                               desc.toLowerCase() + "') " +
                                               "AND BEN_TYPE_ID != " + sno);
                try {
                    if (result.next()) {
                        dup = result.getInt("dup");
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values of dup: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception is in dup" + e1);
            }
            System.out.println("Duplicate - " + dup);


            if (dup == 0) {
                try {
                    if ("P".equalsIgnoreCase(prvlb)) {

                        String sqlUpdPrv =
                            "UPDATE PMS_DCB_MST_PRIVCAT " + "SET PCAT_DESC=? " +
                            "WHERE PCAT_SNO= ?";
                        PreparedStatement psPrv =
                            connection.prepareStatement(sqlUpdPrv);

                        psPrv.setString(1, desc);
                        psPrv.setInt(2, sno);

                        psPrv.executeUpdate();
                    }


                    String sqlUpdate =
                        "UPDATE PMS_DCB_BEN_TYPE " + "SET " + "  BEN_TYPE_SDESC=?, " +
                        "  BEN_TYPE_DESC=?, " + "  ADDRESS_TO=?, " +
                        "  BEN_TYPE_CATEGORY=?, " +
                        "  UPDATED_BY_USER_ID=?, " +
                        "  UPDATED_DATE=SYSTIMESTAMP " +
                        "WHERE BEN_TYPE_ID= ?";
                    PreparedStatement ps =
                        connection.prepareStatement(sqlUpdate);

                    ps.setString(1, sdesc);
                    ps.setString(2, desc);
                    ps.setString(3, adr);
                    ps.setString(4, prvlb);
                    ps.setString(5, userid);
                    ps.setInt(6, sno);

                    ps.executeUpdate();

                    xml = xml + "<flag>success</flag>";

                    xml += "<sno>" + sno + "</sno>";
                    xml += "<desc>" + desc + "</desc>";
                    xml += "<sdesc>" + sdesc + "</sdesc>";
                    xml += "<adr>" + adr + "</adr>";
                    xml += "<prvlb>" + prvlb + "</prvlb>";

                } catch (Exception e1) {
                    System.out.println("Exception in Updating record ===> " +
                                       e1);
                    xml = xml + "<flag>failure</flag>";
                }
                xml = xml + "</response>";
            } else {
                xml = xml + "<flag>duplicate</flag></response>";
                System.out.println("Duplicate Beneficiary Found - Failed to Update");
            }
        }


        /*******************************************************
         * 						ADD
         *******************************************************/

        else if (strCommand.equalsIgnoreCase("Add")) {
            System.out.println("\n*************\nAdd\n**************\n");
            xml = "<response><command>Add</command>";


            ///////////////////////// DUPLICATION CHECK ///////////////////////////////////////////////////////
            int dup = 0;
            try {
                result =
                        statement.executeQuery("SELECT COUNT(*) AS dup " + "FROM PMS_DCB_BEN_TYPE " +
                                               "WHERE LOWER(BEN_TYPE_SDESC) = '" +
                                               sdesc.toLowerCase() + "' " +
                                               "OR LOWER(BEN_TYPE_DESC) = '" +
                                               desc.toLowerCase() + "' ");
                try {
                    if (result.next()) {
                        dup = result.getInt("dup");
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values of dup: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception is in dup" + e1);
            }
            System.out.println("Duplicate - " + dup);
            ///////////////////////////////////////////////////////////////////////////////////////////////////


            if (dup == 0) {


                try {
                    result =
                            statement.executeQuery("SELECT " + "(CASE WHEN MAX(BEN_TYPE_ID) IS NULL THEN 1 ELSE MAX(BEN_TYPE_ID)+1 END) AS maxsno " +
                                                   "FROM PMS_DCB_BEN_TYPE");
                    try {
                        if (result.next()) {
                            sno = result.getInt("maxsno");
                        }
                    } catch (Exception e) {
                        System.out.println("Exception in the getting values of maxsno: " +
                                           e);
                    }
                    result.close();
                } catch (Exception e1) {
                    System.out.println("Exception is in maxsno" + e1);
                }
                System.out.println("maxsno - " + sno);


                try {
                    if ("P".equalsIgnoreCase(prvlb)) {
                        String sqlAddPrv =
                            "INSERT INTO PMS_DCB_MST_PRIVCAT " + "( " +
                            "  PCAT_SNO, " + "  PCAT_DESC " + ") " +
                            "VALUES " + "(?,?)";

                        PreparedStatement psPrv =
                            connection.prepareStatement(sqlAddPrv);
                        psPrv.setInt(1, sno);
                        psPrv.setString(2, desc);
                        psPrv.executeUpdate();


                        int maxtariff = 0;
                        try {
                            result =
                                    statement.executeQuery("SELECT " + "(CASE WHEN MAX(TARIFF_ID) IS NULL THEN 1 ELSE MAX(TARIFF_ID)+1 END) AS maxtariff " +
                                                           "FROM PMS_DCB_MST_TARIFF");
                            try {
                                if (result.next()) {
                                    maxtariff = result.getInt("maxtariff");
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in the getting values of maxtariff: " +
                                                   e);
                            }
                            result.close();
                        } catch (Exception e1) {
                            System.out.println("Exception is in maxtariff" +
                                               e1);
                        }
                        System.out.println("maxtariff - " + maxtariff);

                        String sqlAddTrf =
                            "INSERT INTO PMS_DCB_MST_TARIFF " + "( " +
                            "  TARIFF_ID, " + "  BENEFICIARY_TYPE, " +
                            "  ACTIVE_STATUS, " + "  UPDATED_BY_USER_ID, " +
                            "  UPDATED_TIME " + ") " + "VALUES " +
                            "(?,?,'A',?,systimestamp)";
                        PreparedStatement psTrf =
                            connection.prepareStatement(sqlAddTrf);
                        psTrf.setInt(1, maxtariff);
                        psTrf.setInt(2, sno);
                        psTrf.setString(3, userid);

                        psTrf.executeUpdate();


                        int maxint = 0;
                        try {
                            result =
                                    statement.executeQuery("SELECT " + "(CASE WHEN MAX(INT_ID) IS NULL THEN 1 ELSE MAX(INT_ID)+1 END) AS maxint " +
                                                           "FROM PMS_DCB_MST_INT");
                            try {
                                if (result.next()) {
                                    maxint = result.getInt("maxint");
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in the getting values of maxint: " +
                                                   e);
                            }
                            result.close();
                        } catch (Exception e1) {
                            System.out.println("Exception is in maxint" + e1);
                        }
                        System.out.println("maxint - " + maxint);

                        String sqlAddInt =
                            "INSERT INTO PMS_DCB_MST_INT " + "( " +
                            "  INT_ID, " + "  BENEFICIARY_TYPE, " +
                            "  ACTIVE_STATUS, " + "  UPDATED_BY_USER_ID, " +
                            "  UPDATED_TIME " + ") " + "VALUES " +
                            "(?,?,'A',?,systimestamp)";
                        PreparedStatement psInt =
                            connection.prepareStatement(sqlAddInt);
                        psInt.setInt(1, maxint);
                        psInt.setInt(2, sno);
                        psInt.setString(3, userid);

                        psInt.executeUpdate();

                    }


                    String sqlAdd =
                        "INSERT INTO PMS_DCB_BEN_TYPE " + "( " + "  BEN_TYPE_ID, " +
                        "  BEN_TYPE_SDESC, " + "  BEN_TYPE_DESC, " +
                        "  ADDRESS_TO, " + "  BEN_TYPE_CATEGORY, " +
                        "  UPDATED_BY_USER_ID, " + "  UPDATED_DATE " + ") " +
                        "VALUES(?,?,?,?,?,?,SYSTIMESTAMP)";

                    PreparedStatement ps = connection.prepareStatement(sqlAdd);
                    ps.setInt(1, sno);
                    ps.setString(2, sdesc);
                    ps.setString(3, desc);
                    ps.setString(4, adr);
                    ps.setString(5, prvlb);
                    ps.setString(6, userid);

                    ps.executeUpdate();


                    xml = xml + "<flag>success</flag>";


                    xml += "<sno>" + sno + "</sno>";
                    xml += "<sdesc>" + sdesc + "</sdesc>";
                    xml += "<desc>" + desc + "</desc>";
                    xml += "<adr>" + adr + "</adr>";
                    xml += "<prvlb>" + prvlb + "</prvlb>";

                } catch (Exception e1) {
                    System.out.println("Exception in Adding record ===> " +
                                       e1);
                    xml = xml + "<flag>failure</flag>";
                }
                xml = xml + "</response>";

            } else {
                xml = xml + "<flag>duplicate</flag></response>";
                System.out.println("Duplicate Entry Found");
            }
        } else if (strCommand.equals("Get")) {
            System.out.println("\n*************\nGet\n**************\n");
            xml = "<response><command>Get</command>";


            try {
                result =
                        statement.executeQuery("SELECT BEN_TYPE_ID, " + "  BEN_TYPE_SDESC, " +
                                               "  BEN_TYPE_DESC, " +
                                               "  ADDRESS_TO, " +
                                               "  BEN_TYPE_CATEGORY " +
                                               "FROM PMS_DCB_BEN_TYPE " +
                                               "ORDER BY BEN_TYPE_DESC");
                try {
                    xml = xml + "<flag>success</flag>";


                    while (result.next()) {
                        xml += "<row>";

                        xml +=
"<sno>" + result.getInt("BEN_TYPE_ID") + "</sno>";
                        xml +=
"<sdesc>" + result.getString("BEN_TYPE_SDESC") + "</sdesc>";
                        xml +=
"<desc>" + result.getString("BEN_TYPE_DESC") + "</desc>";
                        xml +=
"<adr>" + result.getString("ADDRESS_TO") + "</adr>";
                        xml +=
"<prvlb>" + result.getString("BEN_TYPE_CATEGORY") + "</prvlb>";

                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception in fetching values from Resultset - GET: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        System.out.println("\nBENEFICIARY TYPE XML RESPONSE:");
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }

}


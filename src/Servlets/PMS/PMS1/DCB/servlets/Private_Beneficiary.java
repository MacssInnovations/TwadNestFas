package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.Security.classes.UserProfile;

public class Private_Beneficiary extends HttpServlet {
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
        String desc = null;
        int type = 0;
        int dis = 0;
        String adr = null;


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
            desc = request.getParameter("desc");
            System.out.println("desc : " + desc);
        } catch (Exception e) {
            System.out.println("Exception fetching desc ===> " + e);
        }


        try {
            type = Integer.parseInt(request.getParameter("type"));
            System.out.println("type : " + type);
        } catch (Exception e) {
            System.out.println("Exception fetching blk ===> " + e);
        }


        try {
            dis = Integer.parseInt(request.getParameter("dis"));
            System.out.println("dis : " + dis);
        } catch (Exception e) {
            System.out.println("Exception fetching dis ===> " + e);
        }


        try {
            adr = request.getParameter("adr");
            System.out.println("adr : " + adr);
        } catch (Exception e) {
            System.out.println("Exception fetching adr ===> " + e);
        }
        ///////////////////////////////////////////////////////////////////////


        /*
         * Execute the task adhered to the requested command
         */

        if (strCommand.equalsIgnoreCase("Delete")) {
            System.out.println("\n*************\nDelete\n**************\n");
            xml = "<response><command>Delete</command>";
            int fk = 0;

            try {
                String sqlfk =
                    "SELECT COUNT(*) AS FK FROM PMS_DCB_MST_BENEFICIARY " +
                    "WHERE OTHERS_PRIVATE_SNO = ?";
                PreparedStatement psfk = connection.prepareStatement(sqlfk);
                psfk.setInt(1, sno);
                result = psfk.executeQuery();
                if (result.next()) {
                    fk = result.getInt("FK");
                }
                if (fk == 0) {
                    try {
                        String sqlDelete =
                            "DELETE FROM COM_MST_PRIVATE " + "WHERE PRIVATE_SNO = ?";
                        PreparedStatement ps =
                            connection.prepareStatement(sqlDelete);
                        ps.setInt(1, sno);
                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                        xml = xml + "<sno>" + sno + "</sno>";
                    } catch (Exception e1) {
                        System.out.println("Exception in Deleting record ===> " +
                                           e1);
                        xml = xml + "<flag>failure</flag>";
                    }
                } else {
                    System.out.println("This Private LB referenced by Beneficiary Master");
                    xml = xml + "<flag>reference</flag>";
                }
            } catch (Exception e1) {
                System.out.println("Exception in checking foreign key constraint (PRIVATE_SNO with OTHERS_PRIVATE_SNO in Beneficiary Master) before Deleting Private LB ===> " +
                                   e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("\n*************\nUpdate\n**************\n");
            xml = "<response><command>Update</command>";


            int dup = 0;
            try {
                result =
                        statement.executeQuery("SELECT COUNT(*) AS dup " + "FROM COM_MST_PRIVATE " +
                                               "WHERE LOWER(PRIVATE_DESC) = '" +
                                               desc.toLowerCase() + "' " +
                                               "AND PRIVATE_SNO != " + sno);
                try {
                    if (result.next()) {
                        dup = result.getInt("dup");
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values OF dup: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception is in dup" + e1);
            }
            System.out.println("Duplicate - " + dup);


            if (dup == 0) {
                try {

                    String sqlUpdate =
                        "UPDATE COM_MST_PRIVATE " + "SET " + "  PRIVATE_DESC=?, " +
                        "  UPDATED_BY_USER_ID=?, " +
                        "  UPDATED_TIME=systimestamp, " + "  PCAT_SNO=?, " +
                        "  DISTRICT_CODE=?, " + "  ADDRESS_TO=? " +
                        "WHERE PRIVATE_SNO=?";
                    PreparedStatement ps =
                        connection.prepareStatement(sqlUpdate);

                    ps.setString(1, desc);
                    ps.setString(2, userid);
                    ps.setInt(3, type);
                    ps.setInt(4, dis);
                    ps.setString(5, adr);
                    ps.setInt(6, sno);

                    ps.executeUpdate();

                    try {
                        String sqlUpdBen =
                            "UPDATE PMS_DCB_MST_BENEFICIARY " + "SET 	BENEFICIARY_NAME    = ? " +
                            "WHERE 	OTHERS_PRIVATE_SNO  = ? ";
                        PreparedStatement psBen =
                            connection.prepareStatement(sqlUpdBen);

                        psBen.setString(1, desc);
                        psBen.setInt(2, sno);

                        psBen.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                    } catch (Exception e1) {
                        System.out.println("Exception in Updating Private Beneficiary name in Beneficiary master ===> " +
                                           e1);
                        xml = xml + "<flag>failure</flag>";

                    }

                    xml += "<sno>" + sno + "</sno>";
                    xml += "<desc>" + desc + "</desc>";
                    xml += "<userid>" + userid + "</userid>";
                    xml += "<type>" + type + "</type>";

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

        else if (strCommand.equalsIgnoreCase("Add")) {
            System.out.println("\n*************\nAdd\n**************\n");
            xml = "<response><command>Add</command>";


            int dup = 0;
            try {
                String sqlDup =
                    "SELECT COUNT(*) AS dup " + "FROM COM_MST_PRIVATE " +
                    "WHERE LOWER(PRIVATE_DESC) = '" + desc.toLowerCase() + "'";
                System.out.println(sqlDup);

                PreparedStatement ps = connection.prepareStatement(sqlDup);
                //ps.setString(1,desc.toLowerCase());

                result = ps.executeQuery(sqlDup);

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


            if (dup == 0) {

                try {
                    result =
                            statement.executeQuery("SELECT " + "(CASE WHEN MAX(PRIVATE_SNO) IS NULL THEN 1 ELSE MAX(PRIVATE_SNO)+1 END) AS MAXSNO " +
                                                   "FROM COM_MST_PRIVATE ");
                    try {
                        if (result.next()) {
                            sno = result.getInt("MAXSNO");
                        }
                    } catch (Exception e) {
                        System.out.println("Exception in the getting values OF sno: " +
                                           e);
                    }
                    result.close();
                    //response.setHeader("cache-control","no-cache");
                } catch (Exception e1) {
                    System.out.println("Exception is in MAX sno" + e1);
                }


                try {
                    String sqlAdd =
                        "INSERT INTO COM_MST_PRIVATE " + "( " + "  PRIVATE_SNO, " +
                        "  PRIVATE_DESC, " + "  UPDATED_BY_USER_ID, " +
                        "  UPDATED_TIME, " + "  PCAT_SNO, " +
                        "  DISTRICT_CODE, " + "  ADDRESS_TO " + ") " +
                        "VALUES " + "(?,?,?,systimestamp,?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(sqlAdd);
                    ps.setInt(1, sno);
                    ps.setString(2, desc);
                    ps.setString(3, userid);
                    ps.setInt(4, type);
                    ps.setInt(5, dis);
                    ps.setString(6, adr);

                    ps.executeUpdate();

                    xml = xml + "<flag>success</flag>";


                    xml += "<sno>" + sno + "</sno>";
                    xml += "<desc>" + desc + "</desc>";
                    xml += "<type>" + type + "</type>";


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
                        statement.executeQuery("SELECT " + "  PRV.PRIVATE_SNO, " +
                                               "  PRV.PRIVATE_DESC, " +
                                               "  PRV.PCAT_SNO, " +
                                               "  BTYP.BEN_TYPE_DESC, " +
                                               "  PRV.DISTRICT_CODE, " +
                                               "  DIS.DISTRICT_NAME, " +
                                               "  PRV.ADDRESS_TO " + "FROM " +
                                               "  ( " + "    ( " +
                                               "    SELECT " +
                                               "      PRIVATE_SNO, " +
                                               "      PRIVATE_DESC, " +
                                               "      PCAT_SNO, " +
                                               "      DISTRICT_CODE, " +
                                               "      ADDRESS_TO " +
                                               "    FROM COM_MST_PRIVATE " +
                                               "    )PRV " + "    JOIN " +
                                               "    ( " + "      SELECT " +
                                               "        DISTRICT_CODE, " +
                                               "        DISTRICT_NAME " +
                                               "      FROM COM_MST_DISTRICTS " +
                                               "    )DIS " +
                                               "    ON PRV.DISTRICT_CODE = DIS.DISTRICT_CODE " +
                                               "    JOIN " + "    ( " +
                                               "      SELECT " +
                                               "        BEN_TYPE_ID, " +
                                               "        BEN_TYPE_DESC " +
                                               "      FROM PMS_DCB_BEN_TYPE " +
                                               "    )BTYP " +
                                               "    ON PRV.PCAT_SNO = BTYP.BEN_TYPE_ID " +
                                               "  )");
                try {
                    xml = xml + "<flag>success</flag>";


                    while (result.next()) {
                        xml += "<row>";

                        xml +=
"<sno>" + result.getInt("PRIVATE_SNO") + "</sno>";
                        xml +=
"<desc>" + result.getString("PRIVATE_DESC") + "</desc>";
                        xml += "<typ>" + result.getInt("PCAT_SNO") + "</typ>";
                        xml +=
"<type>" + result.getString("BEN_TYPE_DESC") + "</type>";
                        xml +=
"<dis>" + result.getInt("DISTRICT_CODE") + "</dis>";
                        xml +=
"<district>" + result.getString("DISTRICT_NAME") + "</district>";
                        xml +=
"<adr>" + result.getString("ADDRESS_TO") + "</adr>";

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


        else if (strCommand.equals("Type")) {
            System.out.println("\n*************\nType\n**************\n");
            xml = "<response><command>Type</command>";
            try {
                result =
                        statement.executeQuery("SELECT " + "  BEN_TYPE_ID, " + "  BEN_TYPE_DESC " +
                                               "FROM PMS_DCB_BEN_TYPE " +
                                               "WHERE BEN_TYPE_CATEGORY = 'P'" +
                                               "ORDER BY BEN_TYPE_ID");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (result.next()) {
                        xml += "<row>";
                        xml +=
"<typ>" + result.getString("BEN_TYPE_ID") + "</typ>";
                        xml +=
"<type>" + result.getString("BEN_TYPE_DESC") + "</type>";
                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values OF Type: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception is in Type" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equals("District")) {
            System.out.println("\n*************\nDistrict\n**************\n");
            xml = "<response><command>District</command>";


            try {
                result =
                        statement.executeQuery("SELECT DISTRICT_CODE,DISTRICT_NAME " +
                                               "FROM COM_MST_DISTRICTS  " +
                                               "WHERE DISTRICT_CODE " +
                                               "      IN  " + "      ( " +
                                               "        SELECT DISTRICT_CODE " +
                                               "        FROM HRM_EMP_CURRENT_POSTING a JOIN PMS_DCB_DIV_DIST_MAP b " +
                                               "        ON a.OFFICE_ID=b.OFFICE_ID " +
                                               "        WHERE EMPLOYEE_ID= " +
                                               empid + "      )");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (result.next()) {
                        xml += "<row>";

                        xml +=
"<dis>" + result.getInt("DISTRICT_CODE") + "</dis>";
                        xml +=
"<district>" + result.getString("DISTRICT_NAME") + "</district>";

                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values from District query: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception in District query" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        System.out.println("\nPRIVATE BENEFICIARY XML RESPONSE:");
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }

}


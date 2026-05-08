package Servlets.FAS.FAS1.CommonControls.servlets;

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

public class SGH_UserRoleCreation extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        /**
         * Set Content Type
         */
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
         */
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


        /**
         * Variables Declaration
         */
        Connection con = null;


        /** Combo Loading */
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement ps_list = null;
        ResultSet rs_list = null;


        /** Record Add */
        PreparedStatement ps2 = null;

        /** Check Existing Record */
        PreparedStatement ps3 = null;
        ResultSet rs3 = null;

        /** Record Delete */
        PreparedStatement ps4 = null;

        /** Load Employee ID and Name */
        PreparedStatement ps5 = null;
        ResultSet rs5 = null;

        /**
         * Database Connection
         */
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
        }


        /**
                * Get Command Parameter
                */
        String Command = null;

        try {
            Command = request.getParameter("command");
        } catch (Exception e) {
            System.out.println("Error in get Command Parameter " + Command);
        }

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Updated Time */
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /**
        * Section Combo Loading -- Load Section ID and Name
        */
        if (Command.equalsIgnoreCase("combo")) {

            /** xml */
            String xml = "";

            xml = "<response><command>Load_Section</command>";

            /** Query */
            String sql_combo =
                " select section_id, section_name from fas_mst_sections order by section_name ";
            int cnt = 0;

            /** Fetch Section ID and Name from DB */
            try {
                ps = con.prepareStatement(sql_combo);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<Section_Pair>";
                    xml =
 xml + "<section_id>" + rs.getInt("section_id") + "</section_id>";
                    xml =
 xml + "<section_name>" + rs.getString("section_name") + "</section_name>";
                    xml = xml + "</Section_Pair>";
                    cnt++;
                }


                if (cnt == 0) {
                    xml = xml + "<flag>NoData</flag>";
                } else {
                    xml = xml + "<flag>Success</flag>";
                }


            } catch (Exception e) {
                xml = xml + "<flag>Failure</flag>";
                System.out.println(e);
            }

            xml = xml + "</response>";
            out.println(xml);
            System.out.println(xml);
        }


        /**
         *  Addtion -- Grant
         */
        else if (Command.equalsIgnoreCase("Add")) {

            /** Get Employee ID */
            int txtEmpID = 0;
            try {
                txtEmpID = Integer.parseInt(request.getParameter("txtEmpID"));
            } catch (Exception e) {
                System.out.println(e);
            }

            /** Get Section ID */
            int txtSection = 0;
            try {
                txtSection =
                        Integer.parseInt(request.getParameter("txtSection"));
            } catch (Exception e) {
                System.out.println(e);
            }


            /** xml */
            String xml = "";
            xml = "<response><command>Add</command>";


            /** Insertion Query */
            String sql_ins =
                "insert into FAS_SECTION_ROLES (USERID,SECTIONID,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?)";

            /** Check Existing Record */
            int cnt = 0;
            String Sql_Exi =
                "select sectionid from FAS_SECTION_ROLES where USERID= ? and SECTIONID=?";
            try {
                ps3 = con.prepareStatement(Sql_Exi);
                ps3.setInt(1, txtEmpID);
                ps3.setInt(2, txtSection);
                rs3 = ps3.executeQuery();

                /** Existing Record Check */
                while (rs3.next()) {
                    cnt++;
                }

                if (cnt == 0) {
                    /** Insertion Part */
                    ps2 = con.prepareStatement(sql_ins);
                    ps2.setInt(1, txtEmpID);
                    ps2.setInt(2, txtSection);
                    ps2.setString(3, userid);
                    ps2.setTimestamp(4, ts);
                    ps2.executeUpdate();
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>AlreadyExist</flag>";
                }

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>Failure</flag>";
            }


            xml = xml + "</response>";
            out.println(xml);
            System.out.println(xml);

        }


        /**
         *  Deletion --- Revoke
         */
        else if (Command.equalsIgnoreCase("Delete")) {

            /** Get Employee ID */
            int txtEmpID = 0;
            try {
                txtEmpID = Integer.parseInt(request.getParameter("txtEmpID"));
            } catch (Exception e) {
                System.out.println(e);
            }

            /** Get Section ID */
            int txtSection = 0;
            try {
                txtSection =
                        Integer.parseInt(request.getParameter("txtSection"));
            } catch (Exception e) {
                System.out.println(e);
            }

            /** xml */
            String xml = "";
            xml = "<response><command>Delete</command>";

            /** Record Deletion Query */
            String sql_del =
                "delete from FAS_SECTION_ROLES where USERID= ? and SECTIONID=? ";

            try {
                /** Record Deletion Part */
                ps4 = con.prepareStatement(sql_del);
                ps4.setInt(1, txtEmpID);
                ps4.setInt(2, txtSection);
                ps4.executeUpdate();
                xml = xml + "<flag>Success</flag>";
                xml = xml + "<section_id>" + txtSection + "</section_id>";

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>Failure</flag>";
            }

            xml = xml + "</response>";
            out.println(xml);
            System.out.println(xml);

        }


        /**
         * List All the Records in Grid Table
         */
        else if (Command.equalsIgnoreCase("ListAll")) {
            /** Get Employee ID */
            int txtEmpID = 0;
            try {
                txtEmpID = Integer.parseInt(request.getParameter("txtEmpID"));
            } catch (Exception e) {
                System.out.println(e);
            }

            /** Get Section ID */
            int txtSection = 0;
            try {
                txtSection =
                        Integer.parseInt(request.getParameter("txtSection"));
            } catch (Exception e) {
                System.out.println(e);
            }

            /** xml */
            String xml = "";
            xml = "<response><command>ListAll</command>";

            try {
                int cnt = 0;

                /** List all the Sections corresponding to given User id */
                String sql_all =
                    "select sectionid, (select section_name from fas_mst_sections where section_id= sectionid) as sectionname from fas_section_roles where userid = " +
                    txtEmpID;

                ps_list = con.prepareStatement(sql_all);
                rs_list = ps_list.executeQuery();

                while (rs_list.next()) {
                    xml = xml + "<List_Section_Pair>";
                    xml =
 xml + "<list_section_id>" + rs_list.getInt("sectionid") +
   "</list_section_id>";
                    xml =
 xml + "<list_section_name>" + rs_list.getString("sectionname") +
   "</list_section_name>";
                    xml = xml + "</List_Section_Pair>";
                    cnt++;
                }

                if (cnt == 0) {
                    xml = xml + "<flag>NoData</flag>";
                } else {
                    xml = xml + "<flag>Success</flag>";
                }

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>Failure</flag>";
            }

            xml = xml + "</response>";
            out.println(xml);
            System.out.println(xml);

        }

        /**
         * Load Employee ID and Name
         */
        else if (Command.equalsIgnoreCase("LoadEmpName")) {
            /** Get Employee ID */
            String txtUserID = null;
            try {
                txtUserID = request.getParameter("txtUserID");
            } catch (Exception e) {
                System.out.println(e);
            }


        }


    }
}

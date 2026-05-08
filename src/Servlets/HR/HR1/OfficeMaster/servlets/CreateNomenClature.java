package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class CreateNomenClature extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet res = null;
    private PreparedStatement ps1 = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
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

        //Connection String coding
        int txtnewOfficeId = 0;
        int txtnew = 0;
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);

        java.sql.Date dateOfNomen = null;
        java.sql.Date dateOfRelieval = null;
        java.sql.Date dateOfJoining = null;

        String officeid = request.getParameter("txtOffice_Id");
        String officename = request.getParameter("txtNewOfficeName");
        String officeshortname = request.getParameter("txtNewShortName");
        String primaryid = request.getParameter("cmbSecondaryID");
        String dateofnomen = request.getParameter("txtDateOfNomen");

        String dateofrelieval = request.getParameter("txtDateOfRelieval");
        String dateofjoining = request.getParameter("txtDateOfJoining");
        String rad_Relieval = request.getParameter("rad_Relieval");
        String rad_Joining = request.getParameter("rad_Joining");

        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        int office_id = 0;
        if (officeid != "null") {
            office_id = Integer.parseInt(officeid);
        }
        try {

            System.out.println("before converting date");

            String dateString = dateofnomen;
            String dateString2 = dateofrelieval;
            String dateString3 = dateofjoining;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");

            java.util.Date d;
            java.util.Date d2;
            java.util.Date d3;

            d = dateFormat.parse(dateString.trim());
            d2 = dateFormat2.parse(dateString2.trim());
            d3 = dateFormat3.parse(dateString3.trim());

            dateFormat.applyPattern("yyyy-MM-dd");
            dateFormat2.applyPattern("yyyy-MM-dd");
            dateFormat3.applyPattern("yyyy-MM-dd");

            dateString = dateFormat.format(d);
            dateString2 = dateFormat2.format(d2);
            dateString3 = dateFormat3.format(d3);

            dateOfNomen = java.sql.Date.valueOf(dateString);
            dateOfRelieval = java.sql.Date.valueOf(dateString2);
            dateOfJoining = java.sql.Date.valueOf(dateString3);

            System.out.println("Date Of Nomen: " + dateOfNomen);
            System.out.println("Date Of Relieval: " + dateOfRelieval);
            System.out.println("Date Of Joining: " + dateOfJoining);
            System.out.println("Relieval Session: " + rad_Relieval);
            System.out.println("Joining Session: " + rad_Joining);

        } catch (Exception e) {
            System.out.println("Exception in Date:" + e);
        }

        /*
        try
        {
        String sql4="select max(office_id) as maxofficeid from com_mst_offices";

                    PreparedStatement ps=connection.prepareStatement(sql4);
                    ResultSet result=ps.executeQuery();
                    if(result.next()){
                        txtnewOfficeId=result.getInt("maxofficeid");
                        txtnew=txtnewOfficeId+1;
                        System.out.println("new Office Id is:"+txtnew);
                    }
        } catch(Exception e) {
                        System.out.println("excep in creating new office id"+e);
                    }*/

        try {
            //String sql="insert into COM_OFFICE_NOMENCLATURE(office_id,new_office_name,NEW_SHORT_NAME,NEW_PRIMARY_WORK_ID,DATE_OF_NOMENCLATURE_CHANGE,UPDATED_BY_USER_ID,PROCESS_FLOW_STATUS_ID,new_office_id) values(?,?,?,?,?,?,?,?)";

            String sql =
                "insert into COM_OFFICE_NOMENCLATURE(office_id,new_office_name,NEW_SHORT_NAME,NEW_PRIMARY_WORK_ID,DATE_OF_NOMENCLATURE_CHANGE,UPDATED_BY_USER_ID,PROCESS_FLOW_STATUS_ID,UPDATED_DATE,EMP_RELIEVAL_DATE,EMP_JOIN_DATE,EMP_RELIEVAL_SESSION,EMP_JOIN_SESSION) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, office_id);
            ps.setString(2, officename);
            ps.setString(3, officeshortname);
            ps.setString(4, primaryid);
            ps.setDate(5, dateOfNomen);
            ps.setString(6, userid);
            ps.setString(7, "CR");
            ps.setTimestamp(8, ts);
            //ps.setInt(8,txtnew);
            ps.setDate(9, dateOfRelieval);
            ps.setDate(10, dateOfJoining);
            ps.setString(11, rad_Relieval);
            ps.setString(12, rad_Joining);

            int ii = ps.executeUpdate();
            String msg = "NomenClature has been Created Successfully";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

        } catch (Exception e) {
            System.out.println("Exception in Insert:" + e);
        }


        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}

package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class newRegnServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;

    private PreparedStatement ps = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

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
            } catch (SQLException e) {
                System.out.println("Error while opening connection:" + e);
            }
        } catch (Exception e) {
            System.out.println("Error :" + e);
        }

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        System.out.println("servlet called");

        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();


        String strcmd = "";
        strcmd = request.getParameter("command");
        String xml = "";
        String officelevel = "";
        String sql = "";

        //Code to Get The Class Level based On Office Id
        if (strcmd.equals("Class")) {
            try {
                int offid = 0;
                offid = Integer.parseInt(request.getParameter("offid"));
                System.out.println("inside try b4 query");
                ps =
  connection.prepareStatement("select Office_Level_Id from COM_MST_OFFICES where Office_Id=?");
                ps.setInt(1, offid);
                System.out.println("ps is:" + offid);
                results = ps.executeQuery();
                System.out.println(results);

                if (results.next()) {
                    xml =
 xml + "<response><offlevel>" + results.getString("Office_Level_Id") +
   "</offlevel></response>";

                } else {
                    System.out.println("sorry error");
                }
                results.close();

            } catch (Exception e) {
            }
        } else if (strcmd.equals("Fees")) {
            //System.out.println("yes AAyiye");

            try {
                int classid = 0;
                classid = Integer.parseInt(request.getParameter("classid"));
                System.out.println(classid);
                String state = "";
                state = request.getParameter("state");
                System.out.println(state);
                ps =
  connection.prepareStatement("select Regn_Fees from PMS_MST_CON_REGN_FEES where Regn_Class_Id=? and Entire_State=?");
                ps.setInt(1, classid);
                System.out.println("ps is:" + classid);
                ps.setString(2, state);
                System.out.println("ps is:" + state);
                results = ps.executeQuery();
                System.out.println(results);

                if (results.next()) {
                    xml =
 xml + "<response><fees>" + results.getInt("Regn_Fees") + "</fees></response>";

                } else {
                    System.out.println("sorry error");
                }
                results.close();

            } catch (Exception e) {
            }
        }

        //code to Verify the Contractor Id

        if (strcmd.equals("verify")) {
            xml = "<response><command>verify</command>";
            String strCid = request.getParameter("id");
            System.out.println(strCid);
            int strContractorId = 0;
            try {
                strContractorId = Integer.parseInt(strCid);
                System.out.println(strCid);
            } catch (NumberFormatException ne) {
                // error in casting
            }
            try {

                sql = "select * from PMS_MST_CONTR where Contractor_Id=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strContractorId);
                System.out.println(strContractorId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {


                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value>";
                    xml = xml + "<ContId>" + strContractorId + "</ContId>";
                    xml = xml + "</value>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();
            } catch (Exception e) {
                System.out.println("Exception is" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        if (strcmd.equals("verifyRNO")) {
            xml = "<response><command>verifyRNO</command>";
            String strRNo = request.getParameter("RNO");
            System.out.println(strRNo);
            int strRegNo = 0;
            try {
                strRegNo = Integer.parseInt(strRNo);
                System.out.println(strRegNo);
            } catch (NumberFormatException ne) {
                // error in casting
            }
            try {

                sql = "select * from PMS_MST_CONT_REGN where Regn_SlNo=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strRegNo);
                System.out.println(strRegNo);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {


                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value>";
                    xml = xml + "<RNO>" + strRegNo + "</RNO>";
                    xml = xml + "</value>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();
            } catch (Exception e) {
                System.out.println("Exception is" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        out.write(xml);
        out.flush();
        out.close();
        System.out.println("xml is:" + xml);


    }

}

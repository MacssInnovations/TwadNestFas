package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

public class Interest extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";
    private static final String DOC_TYPE = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        System.out.println("welcome servlet");
        String strCommand = "";
        String xml = "";
        int tariff_id = 0;
        int tariff_id2 = 0;
        String tariff_desc = "";
        int tariff_rate = 0;
        int Excesstariff_rate = 0;
        int Int_rate = 0;
        String tariff_w = "";
        String Uom = "";
        String tariff_code = "";
        String updated_time = "";
        String updated_by = "";
        String userid = (String)session.getAttribute("UserId");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con =
 DriverManager.getConnection("jdbc:oracle:thin:@10.163.0.58:1521:twadnest",
                             "twadpms", "twadpms123");
        } catch (ClassNotFoundException e) {
            System.out.println(" Connection error" + e);
        } catch (SQLException e) {
            System.out.println(" sql error" + e);
        }
        //************************fetching emp_id value ***************************
        try {
            strCommand = request.getParameter("command");
            System.out.println("strCommand");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tariff_id = Integer.parseInt(request.getParameter("tariff_id"));
            System.out.println("tariff_id " + tariff_id);
        } catch (Exception e) {
            System.out.println("Exception tariff_id  data===> " + e);
        }
        try {
            tariff_desc = request.getParameter("tariff_desc");
            System.out.println("tariff_desc" + tariff_desc);
        } catch (Exception e) {
            System.out.println("Exception tariff_desc data===> " + e);
        }
        try {
            tariff_rate =
                    Integer.parseInt(request.getParameter("tariff_rate"));
            System.out.println("tariff_rate" + tariff_rate);
        } catch (Exception e) {
            System.out.println("Exception tariff_rate data===> " + e);
        }
        try {
            Excesstariff_rate =
                    Integer.parseInt(request.getParameter("Excesstariff_rate"));
            System.out.println("Excesstariff_rate is : " + Excesstariff_rate);
        } catch (Exception e) {
            System.out.println("Exception Excesstariff_rate  data===> " + e);
        }
        try {
            Int_rate = Integer.parseInt(request.getParameter("Int_rate"));
            System.out.println("date is : " + Int_rate);
        } catch (Exception e) {
            System.out.println("Exception int_rate  data===> " + e);
        }
        try {
            tariff_w = request.getParameter("tariff_w");
            System.out.println("tariff_w is : " + tariff_w);
        } catch (Exception e) {
            System.out.println("Exception tariff_w  data===> " + e);
        }
        try {
            Uom = request.getParameter("Uom");
            System.out.println("Uom is : " + Uom);
        } catch (Exception e) {
            System.out.println("Exception Uom data===> " + e);
        }
        try {
            tariff_code = request.getParameter("tariff_code");
            System.out.println("date is : " + tariff_code);
        } catch (Exception e) {
            System.out.println("Exception tariff_idalias  data===> " + e);
        }
        try {
            updated_by = request.getParameter("updated_time");
            System.out.println("updated_time is : " + updated_by);
        } catch (Exception e) {
            System.out.println("Exception tariff_idalias  data===> " + e);
        }
        try {
            updated_time = request.getParameter("updated_time");
            System.out.println("updated_time is : " + updated_time);
        } catch (Exception e) {
            System.out.println("Exception updated_time  data===> " + e);
        }


        if (strCommand.equalsIgnoreCase("Add")) {
            System.out.println("------------->add---------------->");
            xml = "<response><command>Add</command>";
            String tariff_id1 =
                "SELECT MAX(TARIFF_ID) AS TARIFF_ID FROM PMS_DCB_MST_TARIFF";
            try {
                ps = con.prepareStatement(tariff_id1);
                result = ps.executeQuery();
                while (result.next()) {
                    tariff_id2 = result.getInt("TARIFF_ID") + 1;
                }
            } catch (Exception e) {
                System.out.println("Exception finding max tariff_id ===> " +
                                   e);
            }

            try {
                String stradd_qry =
                    "insert into PMS_DCB_MST_TARIFF(TARIFF_ID,TARIFF_DESC,TARIFF_RATE,EXCESS_TARIFF_RATE,INTEREST_RATE,TARIFF_WEF,UOM,TARIFF_ID_ALIAS,updated_by,updated_time) values(?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,systimestamp)";
                ps = con.prepareStatement(stradd_qry);

                ps.setInt(1, tariff_id2);

                ps.setString(2, tariff_desc);
                ps.setInt(3, tariff_rate);
                ps.setInt(4, Excesstariff_rate);
                ps.setInt(5, Int_rate);
                ps.setString(6, tariff_w);
                ps.setString(7, Uom);
                ps.setString(8, tariff_code);
                ps.setString(9, userid);
                ps.setString(10, updated_time);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<tariff_id>" + tariff_id2 + "</tariff_id>";
                xml = xml + "<tariff_desc>" + tariff_desc + "</tariff_desc>";
                xml = xml + "<tariff_rate>" + tariff_rate + "</tariff_rate>";
                xml =
 xml + "<Excesstariff_rate>" + Excesstariff_rate + "</Excesstariff_rate>";
                xml = xml + "<Int_rate>" + Int_rate + "</Int_rate>";
                xml = xml + "<tariff_w>" + tariff_w + "</tariff_w>";
                xml = xml + "<Uom>" + Int_rate + "</Uom>";
                xml = xml + "<tariff_code>" + tariff_code + "</tariff_code>";
            } catch (Exception e1) {
                System.out.println("exeception in adding   :" + e1);
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("------------->Update---------------->");
            xml = "<response><command>Update</command>";
            try {
                String stradd_qry =
                    "Update  PMS_DCB_MST_TARIFF set TARIFF_DESC=?,TARIFF_RATE=?,EXCESS_TARIFF_RATE=?,INTEREST_RATE=?,TARIFF_WEF=to_date(?,'dd/mm/yyyy'),UOM=?,TARIFF_ID_ALIAS=? where  TARIFF_ID=?";
                ps = con.prepareStatement(stradd_qry);

                ps.setString(1, tariff_desc);
                ps.setInt(2, tariff_rate);
                ps.setInt(3, Excesstariff_rate);
                ps.setInt(4, Int_rate);
                ps.setString(5, tariff_w);
                ps.setString(6, Uom);
                ps.setString(7, tariff_code);
                ps.setInt(8, tariff_id);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<tariff_id>" + tariff_id + "</tariff_id>";
                xml = xml + "<tariff_desc>" + tariff_desc + "</tariff_desc>";
                xml = xml + "<tariff_rate>" + tariff_rate + "</tariff_rate>";
                xml =
 xml + "<Excesstariff_rate>" + Excesstariff_rate + "</Excesstariff_rate>";
                xml = xml + "<Int_rate>" + Int_rate + "</Int_rate>";
                xml = xml + "<tariff_w>" + tariff_w + "</tariff_w>";
                xml = xml + "<Uom>" + Int_rate + "</Uom>";
                xml = xml + "<tariff_code>" + tariff_code + "</tariff_code>";
            } catch (Exception e1) {
                System.out.println("exeception in update   :" + e1);
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            System.out.println("------------->Delete---------------->");
            xml = "<response><command>Delete</command>";
            try {
                String stradd_qry =
                    "Delete  from PMS_DCB_MST_TARIFF  where  TARIFF_ID=?";
                ps = con.prepareStatement(stradd_qry);
                ps.setInt(1, tariff_id);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<tariff_id>" + tariff_id + "</tariff_id>";
                xml = xml + "<tariff_desc>" + tariff_desc + "</tariff_desc>";
                xml = xml + "<tariff_rate>" + tariff_rate + "</tariff_rate>";
                xml =
 xml + "<Excesstariff_rate>" + Excesstariff_rate + "</Excesstariff_rate>";
                xml = xml + "<Int_rate>" + Int_rate + "</Int_rate>";
                xml = xml + "<tariff_w>" + tariff_w + "</tariff_w>";
                xml = xml + "<Uom>" + Int_rate + "</Uom>";
                xml = xml + "<tariff_code>" + tariff_code + "</tariff_code>";
            } catch (Exception e1) {
                System.out.println("exeception in update   :" + e1);
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        }
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
    }


}

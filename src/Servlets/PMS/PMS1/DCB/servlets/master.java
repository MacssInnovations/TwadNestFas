package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class master extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        ResultSet result = null;
        // PreparedStatement=null;

        System.out.println("welcome to servlet");
        String strCommand = "";
        String xml = "";
        int mas_no = 0;
        String mas_desc = "";
        int UOM_SNO = 0;
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con =
 DriverManager.getConnection("jdbc:oracle:thin:@10.163.0.58:1521:twadnest",
                             "pmstwad", "pmstwad123");
        }

        catch (ClassNotFoundException e) {
            System.out.println("Connection err");
        }

        catch (SQLException e) {
            System.out.println("Sql err");
        }


        try {
            strCommand = request.getParameter("command");
            System.out.println("strCommand : " + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mas_no = Integer.parseInt(request.getParameter("mas_no"));
            System.out.println("mas_no is : " + mas_no);
        } catch (Exception e) {
            System.out.println("Exception fetching mas_no ===> " + e);
        }


        try {
            mas_desc = request.getParameter("mas_desc");
            System.out.println("mas_desc is  : " + mas_desc);
        } catch (Exception e) {
            System.out.println("Exception fetching mas_desc===> " + e);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            System.out.println("\n*************\nAdd\n**************\n");
            xml = "<response><command>Add</command>";
            try {

                String sqlAdd =
                    "insert into PMS_DCB_MST_UOM(UOM_SNO,UOM_DESC) values(?,?)";
                PreparedStatement ps = con.prepareStatement(sqlAdd);
                ps.setInt(1, UOM_SNO);
                ps.setString(2, mas_desc);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<mas_no>" + UOM_SNO + "</mas_no>";
                xml = xml + "<mas_desc>" + mas_desc + "</mas_desc>";
            } catch (Exception e1) {
                System.out.println("Exception in Adding record ===> " + e1);
                xml = xml + "<flag>failure</flag>";
            }

        }


        else if (strCommand.equalsIgnoreCase("Delete")) {
            System.out.println("\n*************\nDelete\n**************\n");
            xml = "<response><command>Delete</command>";
            try {
                String sqlDelete =
                    "DELETE FROM PMS_DCB_MST_UOM WHERE UOM_DESC=?";
                PreparedStatement ps = con.prepareStatement(sqlDelete);
                ps.setString(1, mas_desc);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<mas_no>" + mas_no + "</mas_no>";
                xml = xml + "<mas_desc>" + mas_desc + "</mas_desc>";
            } catch (Exception e1) {
                System.out.println("Exception in Deleting record ===> " + e1);
                xml = xml + "<flag>failure</flag>";
            }

        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("\n*************\nUpdate\n**************\n");
            xml = "<response><command>Update</command>";
            try {
                String sqlUpdate =
                    "UPDATE PMS_DCB_MST_UOM SET UOM_DESC = ?  WHERE UOM_SNO=?";
                PreparedStatement ps = con.prepareStatement(sqlUpdate);
                ps.setString(1, mas_desc);
                ps.setInt(2, mas_no);
                ps.executeUpdate();

                xml = xml + "<mas_no>" + mas_no + "</mas_no>";
                xml = xml + "<mas_desc>" + mas_desc + "</mas_desc>";
                xml = xml + "<flag>success</flag>";

            } catch (Exception e1) {
                System.out.println("Exception in Updating record ===> " + e1);
                xml = xml + "<flag>failure</flag>";
            }

        }

        else if (strCommand.equalsIgnoreCase("Get")) {
            xml = "<response><command>Get</command>";
            System.out.println("\n*************\nGet\n**************\n");

            //result = null;
            try {
                String sqlGet =
                    "select UOM_SNO,UOM_DESC from PMS_DCB_MST_UOM order by UOM_SNO";
                PreparedStatement ps = con.prepareStatement(sqlGet);
                result = ps.executeQuery();
                xml = xml + "<flag>success</flag>";

                while (result.next()) {
                    xml =
 xml + "<mas_no>" + result.getInt("UOM_SNO") + "</mas_no>";
                    xml =
 xml + "<mas_desc>" + result.getString("UOM_DESC") + "</mas_desc>";
                }
            } catch (Exception e1) {
                System.out.println("Exception in Getting records ===> " + e1);
                xml = xml + "<flag>failure</flag>";
            }
        }


        xml = xml + "</response>";
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }
}

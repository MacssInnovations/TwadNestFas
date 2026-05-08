package Servlets.FAS.FAS1.CommonControls.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.*;
import javax.servlet.http.*;

public class Tree_Menu_Generation extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        /** Set Content Type */
        response.setContentType(CONTENT_TYPE);


        /**
           * Variables Declaration
           */
        Connection con = null;
        String xml = null;

        PreparedStatement ps_ho = null;
        PreparedStatement ps_region = null;
        PreparedStatement ps_circle = null;
        PreparedStatement ps_unit = null;

        ResultSet rs_ho = null;
        ResultSet rs_region = null;
        ResultSet rs_circle = null;
        ResultSet rs_unit = null;

        String sql_ho = null;
        String sql_region = null;
        String sql_circle = null;
        String sql_unit = null;


        PrintWriter out = response.getWriter();

        /**
          * Database Connection
          */
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con =
 DriverManager.getConnection("jdbc:oracle:thin:@10.163.0.14:1521:twaddb",
                             "twadfas", "twadfas123");
            System.out.println("connection establised");
        } catch (Exception e) {
            System.out.println("conection failure");
        }


        /** XML Declaration */
        xml = "<response><command>ListAllUnits</command>";


        /**
            * Load HO Only
            */

        sql_ho =
                "select accounting_unit_id , accounting_unit_name from fas_mst_acct_units where accounting_unit_office_id=5000 order by accounting_unit_id";

        try {
            ps_ho = con.prepareStatement(sql_ho);
            rs_ho = ps_ho.executeQuery();
            while (rs_ho.next()) {
                xml = xml + "<count_HO>";
                xml =
 xml + "<HO_unit>" + rs_ho.getInt("accounting_unit_id") + "</HO_unit>";
                xml =
 xml + "<HO_unit_name>" + rs_ho.getString("accounting_unit_name") +
   "</HO_unit_name>";
                xml = xml + "</count_HO>";
            }

        } catch (Exception e) {
            System.out.println("Units Under Head Office is not Loading ");
        }


        /**
           * Load All Units for Units Drill Down
           */


        sql_region =
                "                                                                                                      " +
                " select accounting_unit_id , accounting_unit_name, accounting_unit_office_id from fas_mst_acct_units where accounting_unit_office_id in     \n" +
                "  ( select office_id from com_mst_all_offices_view                                                               \n" +
                "    where office_level_id='RN'                                                                                   \n" +
                "  ) order by accounting_unit_id                                                                                  \n" +
                "                                                                                                                 \n";


        sql_circle =
                "                                                                                                        " +
                "select accounting_unit_id , accounting_unit_name, accounting_unit_office_id from fas_mst_acct_units where accounting_unit_office_id in      \n" +
                "(                                                                                                                \n" +
                "  select office_id  from com_mst_all_offices_view                                                                \n" +
                "  where office_level_id='CL'                                                                                     \n" +
                "  and region_office_id= ?                                                                                        \n" +
                ") order by accounting_unit_id                                                                                    \n" +
                "";


        sql_unit =
                "                                                                                                        " +
                "select accounting_unit_id , accounting_unit_name from fas_mst_acct_units where accounting_unit_office_id in      \n" +
                "(                                                                                                                \n" +
                "  select office_id  from com_mst_all_offices_view                                                                \n" +
                "  where office_level_id='DN'                                                                                     \n" +
                "  and circle_office_id = ?                                                                                       \n" +
                ") order by accounting_unit_id                                                                                    \n" +
                "\n";


        try {

            ps_region = con.prepareStatement(sql_region);
            ps_circle = con.prepareStatement(sql_circle);
            ps_unit = con.prepareStatement(sql_unit);

            try {
                rs_region = ps_region.executeQuery();
            } catch (Exception e) {
                System.out.println("TWAD Error in Executing Region " + e);
            }


            /** Region Load */
            while (rs_region.next()) {
                xml = xml + "<count_region>";
                xml =
 xml + "<Region_unit>" + rs_region.getInt("accounting_unit_id") +
   "</Region_unit>";
                xml =
 xml + "<Region_unit_name>" + rs_region.getString("accounting_unit_name") +
   "</Region_unit_name>";

                /** Circle Load */
                ps_circle.setInt(1,
                                 rs_region.getInt("accounting_unit_office_id"));
                rs_circle = ps_circle.executeQuery();
                while (rs_circle.next()) {
                    xml = xml + "<count_circle>";
                    xml =
 xml + "<Circle_unit>" + rs_circle.getInt("accounting_unit_id") +
   "</Circle_unit>";
                    xml =
 xml + "<Circle_unit_name>" + rs_circle.getString("accounting_unit_name") +
   "</Circle_unit_name>";

                    /** Units Load */
                    ps_unit.setInt(1,
                                   rs_circle.getInt("accounting_unit_office_id"));
                    rs_unit = ps_unit.executeQuery();
                    while (rs_unit.next()) {
                        xml = xml + "<count_unit>";
                        xml =
 xml + "<Unit_unit>" + rs_unit.getInt("accounting_unit_id") + "</Unit_unit>";
                        xml =
 xml + "<Unit_unit_name>" + rs_unit.getString("accounting_unit_name") +
   "</Unit_unit_name>";

                        xml = xml + "</count_unit>";
                    }
                    rs_unit.close();
                    xml = xml + "</count_circle>";
                }
                rs_circle.close();
                xml = xml + "</count_region>";
            }

            rs_region.close();
            xml = xml + "<flag>Success</flag>";
        } catch (Exception e) {
            System.out.println("Err in getting values:" + e.getMessage());
            xml = xml + "<flag>Failure</flag>";
        }


        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();


    }


}

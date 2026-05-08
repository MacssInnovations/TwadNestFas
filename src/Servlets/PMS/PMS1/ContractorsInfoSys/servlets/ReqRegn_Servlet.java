package Servlets.PMS.PMS1.ContractorsInfoSys.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;


public class ReqRegn_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        PreparedStatement ps = null;

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
                //System.out.println("Exception in creating statement:"+e);
            }
        } catch (Exception e) {
            //System.out.println("Exception in openeing connection:"+e);
        }

        String strCommand = "";
        String xml = "";
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);
        String userid = (String)(session.getAttribute("userid"));
        System.out.println("servlet called");

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (strCommand.equalsIgnoreCase("Load")) {


            xml = "<response><command>Load</command>";
            try {

                int strOfficeid = 0;
                strOfficeid = Integer.parseInt(request.getParameter("offid"));
                System.out.println(strOfficeid);

                String sql =
                    "SELECT COM_MST_OFFICES.OFFICE_NAME,COM_MST_OFFICES.OFFICE_LEVEL_ID,COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_NAME,COM_MST_OFFICES.OFFICE_ADDRESS1,COM_MST_OFFICES.OFFICE_ADDRESS2,COM_MST_OFFICES.CITY_TOWN_NAME";
                sql = sql + " from COM_MST_OFFICES,COM_MST_OFFICE_LEVELS";
                sql =
 sql + " where COM_MST_OFFICE_LEVELS.OFFICE_LEVEL_ID=COM_MST_OFFICES.OFFICE_LEVEL_ID And COM_MST_OFFICES.OFFICE_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strOfficeid);

                results = ps.executeQuery();
                // while(results.next()) //... changes
                if (results.next()) {
                    xml =
 xml + "<flag>success</flag><offname>" + results.getString("OFFICE_NAME") +
   "</offname><levelid>" + results.getString("OFFICE_LEVEL_ID") + "</levelid>";
                    xml =
 xml + "<leveldesc>" + results.getString("OFFICE_LEVEL_NAME") + "</leveldesc>";
                    xml =
 xml + "<address>" + ((results.getString("OFFICE_ADDRESS1") == null) ? "" :
                      results.getString("OFFICE_ADDRESS1")) + "\n" +
   ((results.getString("OFFICE_ADDRESS2") == null) ? "" :
    results.getString("OFFICE_ADDRESS2")) + "\n" +
   ((results.getString("CITY_TOWN_NAME") == null) ? "" :
    results.getString("CITY_TOWN_NAME")) + "</address>";
                } else
                    xml = xml + "<flag>failure</flag>";

                results.close();
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Exception is******" + e);
            }
            xml = xml + "</response>";


        } else if (strCommand.equalsIgnoreCase("Class")) {


            try {
                String strOffice = request.getParameter("offlevel");
                System.out.println(strOffice);
                String sql =
                    "SELECT PMS_MST_CON_CLASS.REGN_CLASS_ID, PMS_MST_CON_CLASS.REGN_CLASS_DESC, PMS_OFFICE_REGN_CLASS.OFFICE_LEVEL_ID";
                sql = sql + " FROM PMS_MST_CON_CLASS, PMS_OFFICE_REGN_CLASS";
                sql =
 sql + " WHERE PMS_MST_CON_CLASS.REGN_CLASS_ID=PMS_OFFICE_REGN_CLASS.REGN_CLASS_ID AND PMS_OFFICE_REGN_CLASS.OFFICE_LEVEL_ID=? ORDER BY PMS_MST_CON_CLASS.REGN_CLASS_ID";
                ps = connection.prepareStatement(sql);
                ps.setString(1, strOffice);

                results = ps.executeQuery();
                while (results.next()) {
                    xml =
 xml + "<option><id>" + results.getInt("REGN_CLASS_ID") + "</id><desc>" +
   results.getString("Regn_Class_Desc") + "</desc></option>";
                }

                results.close();
            } catch (Exception e) {
                System.out.println("Exception is%%%" + e);
                xml = xml + "<flag>failure</flag>";
            }

            xml = "<select>" + xml + "</select>";

        } else if (strCommand.equalsIgnoreCase("Fees")) {
            try {
                int classid = 0;
                classid = Integer.parseInt(request.getParameter("classid"));
                System.out.println(classid);
                String state = "";
                state = request.getParameter("state");
                System.out.println(state);
                ps =
  connection.prepareStatement("select REGN_FEES from PMS_MST_CON_REGN_FEES where REGN_CLASS_ID=? and REGN_STATE_COVERAGE=?");
                ps.setInt(1, classid);
                System.out.println("ps is:" + classid);
                ps.setString(2, state);
                System.out.println("ps is:" + state);
                results = ps.executeQuery();
                System.out.println(results);
                xml = xml + "<response>";
                if (results.next()) {

                    xml =
 xml + "<flag>success</flag><fees>" + results.getString("REGN_FEES") +
   "</fees>";

                } else {
                    System.out.println("sorry error");
                    xml = xml + "<flag>failure</flag>";
                }
                results.close();
                xml = xml + "</response>";
            } catch (Exception e) {
                System.out.println("Exception ***" + e);
            }


        } else if (strCommand.equalsIgnoreCase("verify")) {
            xml = "<response><command>verify</command>";
            String strCid = request.getParameter("id");
            System.out.println(strCid);
            int txtOffID = 0, txtResNo = 0, year = 0;
            try {
                txtOffID = Integer.parseInt(request.getParameter("txtOffID"));
                System.out.println("txtOffID:" + txtOffID);

                txtResNo = Integer.parseInt(request.getParameter("txtResNo"));
                System.out.println("txtResNo:" + txtResNo);

                String regdate = request.getParameter("txtDate");
                String[] str = regdate.split("/");
                year = Integer.parseInt(str[2]);
                System.out.println("Year :" + year);
            } catch (NumberFormatException ne) {
                // error in casting
            }
            try {

                String sql =
                    "select OFFICE_ID from  PMS_CONT_REQUEST_REGN where OFFICE_ID=? and REGN_YEAR =? and REGN_SLNO=? ";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, txtOffID);
                ps.setInt(2, year);
                ps.setInt(3, txtResNo);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {


                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value>";
                    xml = xml + "<ContId>" + txtResNo + "</ContId>";
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
        } else if (strCommand.equals("Display")) {
            xml = "<response><command>Display</command>";
            try {
                //System.out.println("inside Display");
                String sql =
                    "SELECT REQUEST_SEQ_NO FROM PMS_CONT_REQUEST_REGN GROUP BY REQUEST_SEQ_NO HAVING REQUEST_SEQ_NO=(SELECT max(REQUEST_SEQ_NO) from PMS_CONT_REQUEST_REGN)";
                results = statement.executeQuery(sql);
                try {

                    while (results.next()) {
                        int sno = results.getInt("REQUEST_SEQ_NO");
                        sno = sno + 1;
                        xml = xml + "<flag>success</flag>";
                        xml = xml + "<SNO>" + sno + "</SNO>";
                    }
                }

                catch (SQLException e) {
                    System.out.println("Exception in resultset:" + e);
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e1) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception is$$$$:" + e1);
            }
            xml = xml + "</response>";
        }
        // changes .....
        else if (strCommand.equalsIgnoreCase("Load_Edit_verifyRegID")) {
            xml = "<response><command>Load_Edit_verifyRegID</command>";
            String strCid = request.getParameter("id");
            System.out.println(strCid);
            int txtOffID = 0, txtResNo = 0, year = 0;
            System.out.println();
            try {
                txtOffID = Integer.parseInt(request.getParameter("txtOffID"));
                System.out.println("txtOffID:" + txtOffID);

                txtResNo = Integer.parseInt(request.getParameter("txtResNo"));
                System.out.println("txtResNo:" + txtResNo);

                String regdate = request.getParameter("txtDate");
                String[] str = regdate.split("/");
                year = Integer.parseInt(str[2]);
                System.out.println("Year :" + year);
            } catch (NumberFormatException ne) {
                // error in casting
            }
            try {
                System.out.println("edit");
                String sql =
                    "select CONTRACTOR_ID,CONTRACTOR_NAME,ADDRESS,trim(PHONE) as phone_no,EMAIL,REGN_CLASS_ID," +
                    "REGN_STATE_COVERAGE," +
                    "to_char(REGN_VALID_UPTO,'DD/MM/YYYY') AS REGN_VALID_UPTO_VALUE,REGN_ALIAS_CODE,JURISDICTION from  " +
                    "PMS_CONT_REQUEST_REGN where OFFICE_ID=? and REGN_YEAR =? and REGN_SLNO=? ";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, txtOffID);
                ps.setInt(2, year);
                ps.setInt(3, txtResNo);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {


                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value>";
                    xml =
 xml + "<CONTRACTOR_ID>" + rs.getInt("CONTRACTOR_ID") + "</CONTRACTOR_ID>";
                    xml =
 xml + "<CONTRACTOR_NAME>" + rs.getString("CONTRACTOR_NAME") +
   "</CONTRACTOR_NAME>";
                    xml =
 xml + "<ADDRESS>" + ((rs.getString("ADDRESS") == null) ? "" :
                      rs.getString("ADDRESS")) + "</ADDRESS>";
                    xml =
 xml + "<PHONE>" + ((rs.getString("phone_no") == null) ? "" :
                    rs.getString("phone_no")) + "</PHONE>";
                    xml =
 xml + "<EMAIL>" + ((rs.getString("EMAIL") == null) ? "" :
                    rs.getString("EMAIL")) + "</EMAIL>";
                    xml =
 xml + "<REGN_CLASS_ID>" + rs.getInt("REGN_CLASS_ID") + "</REGN_CLASS_ID>";
                    xml =
 xml + "<REGN_STATE_COVERAGE>" + rs.getString("REGN_STATE_COVERAGE") +
   "</REGN_STATE_COVERAGE>";
                    xml =
 xml + "<REGN_VALID_UPTO_VALUE>" + rs.getString("REGN_VALID_UPTO_VALUE") +
   "</REGN_VALID_UPTO_VALUE>";
                    xml =
 xml + "<REGN_ALIAS_CODE>" + rs.getString("REGN_ALIAS_CODE") +
   "</REGN_ALIAS_CODE>";
                    xml =
 xml + "<JURISDICTION>" + rs.getString("JURISDICTION") + "</JURISDICTION>";
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
        if (strCommand.equalsIgnoreCase("List")) {
            System.out.println("List");
            String accountunitid = request.getParameter("AccountUnitId");
            int aunitid = 0;
            if (accountunitid != null) {
                aunitid = Integer.parseInt(accountunitid);
                System.out.println("Unit is:" + aunitid);
            }
            try {
                String sql5 =
                    "select a.accounting_unit_id,a.accounting_unit_name,a.accounting_unit_office_id,b.accounting_for_office_id,c.office_name from fas_mst_acct_units a,fas_mst_acct_unit_offices b,com_mst_offices c where a.accounting_unit_id=b.accounting_unit_id and b.accounting_for_office_id=c.office_id and a.accounting_unit_id=?";
                ps = connection.prepareStatement(sql5);
                ps.setInt(1, aunitid);
                results = ps.executeQuery();
                // String xml="";
                xml = "<response><flag>success</flag>";
                while (results.next()) {

                    response.setContentType("text/xml");

                    int accountid = results.getInt("accounting_unit_id");
                    String unitname =
                        results.getString("accounting_unit_name").trim();
                    int accofficeid =
                        results.getInt("accounting_unit_office_id");
                    int accforofficeid =
                        results.getInt("accounting_for_office_id");
                    String officename = results.getString("Office_name");

                    xml =
 xml + "<leng><accountid>" + accountid + "</accountid><unitname>" + unitname +
   "</unitname><accofficeid>" + accofficeid +
   "</accofficeid><accforofficeid>" + accforofficeid +
   "</accforofficeid><officename>" + officename + "</officename></leng>";

                }
                xml = xml + "</response>";
                //    out.write(xml);
                System.out.println("xml is:" + xml);
            } catch (Exception e) {
                System.out.println("Exception in Accounting Unit:" + e);
                xml = "<response><flag>failure</flag></response>";
            }


        }
        // changes...

        System.out.println("xml is" + xml);
        pw.write(xml);

        pw.flush();
        pw.close();


    }
}

package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletOfficeAccountingUnit extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        String strCriteria = "";
        int Office_Id = 0;


        int found = 0;
        String mode = request.getParameter("command");
        System.out.println(mode);


        String xml = "";
        //System.out.println("Hai");
        Connection connection = null;
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
            if (mode.equals("Load")) {
                try {
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));
                    //String accountunit=request.getParameter("account_unit");
                    ResultSet rs4 = null;
                    String sql4 =
                        "select * from com_office_acct_units where office_id=?";
                    PreparedStatement ps = connection.prepareStatement(sql4);
                    ps.setInt(1, Office_Id);
                    rs4 = ps.executeQuery();
                    if (!rs4.next()) {
                        String sql =
                            "select OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO,ACCOUNTING_UNIT from com_mst_offices where OFFICE_ID=?";
                        //String sql="select com_mst_offices.OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO,ACCOUNTING_UNIT,com_office_acct_units.ACCT_RENDERING_OFFICE_ID from com_mst_offices,com_office_acct_units where com_mst_offices.office_id=?";
                        PreparedStatement statement =
                            connection.prepareStatement(sql);
                        statement.setInt(1, Office_Id);
                        connection.clearWarnings();
                        try {
                            ResultSet results = statement.executeQuery();
                            System.out.println();
                            try {
                                xml = "<response><flag>success</flag>";
                                if (results.next()) {
                                    //xml=xml+"<options><id>"+results.getInt("OFFICE_ID") + "</id><name>" + results.getString("OFFICE_NAME") + "</name><officeAddress1>"+results.getString("OFFICE_ADDRESS1")+ "</officeAddress1><officeAddress2>"+results.getString("OFFICE_ADDRESS2")+"</officeAddress2><officeAddress3>"+results.getString("CITY_TOWN_NAME")+"</officeAddress3><District>"+results.getInt("DISTRICT_CODE")+"</District><Phone>"+results.getString("OFFICE_PHONE_NO")+"</Phone><Fax>"+results.getString("OFFICE_FAX_NO")+"</Fax><AccUnit>"+results.getString("ACCOUNTING_UNIT")+"</AccUnit><AttachOfficeId>"+results.getInt("ACCT_RENDERING_OFFICE_ID")+"</AttachOfficeId></options>";
                                    xml =
 xml + "<options><id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><District>" +
   results.getInt("DISTRICT_CODE") + "</District><Phone>" +
   results.getString("OFFICE_PHONE_NO") + "</Phone><Fax>" +
   results.getString("OFFICE_FAX_NO") + "</Fax><AccUnit>" +
   results.getString("ACCOUNTING_UNIT") + "</AccUnit></options>";
                                    found++;
                                }
                                if (found == 0)
                                    xml = "<response><flag>failure</flag>";

                                xml = xml + "</response>";
                            } catch (SQLException e) {
                                System.out.println("Exception in Query" + e);
                            } finally {
                                results.close();
                            }
                        } catch (SQLException e) {
                            System.out.println("Exception in statement:" + e);
                        } finally {
                            statement.close();
                        }
                    } else {
                        //String sql="select OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO,ACCOUNTING_UNIT from com_mst_offices where OFFICE_ID=?";
                        String sql =
                            "select com_mst_offices.OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO,ACCOUNTING_UNIT,com_office_acct_units.ACCT_RENDERING_OFFICE_ID from com_mst_offices,com_office_acct_units where com_mst_offices.office_id=com_office_acct_units.office_id and com_mst_offices.office_id=?";
                        PreparedStatement statement =
                            connection.prepareStatement(sql);
                        statement.setInt(1, Office_Id);
                        connection.clearWarnings();
                        try {
                            ResultSet results = statement.executeQuery();
                            System.out.println();
                            try {
                                xml = "<response><flag>success</flag>";
                                if (results.next()) {
                                    xml =
 xml + "<options><id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><District>" +
   results.getInt("DISTRICT_CODE") + "</District><Phone>" +
   results.getString("OFFICE_PHONE_NO") + "</Phone><Fax>" +
   results.getString("OFFICE_FAX_NO") + "</Fax><AccUnit>" +
   results.getString("ACCOUNTING_UNIT") + "</AccUnit><AttachOfficeId>" +
   results.getInt("ACCT_RENDERING_OFFICE_ID") + "</AttachOfficeId></options>";
                                    // xml=xml+"<options><id>"+results.getInt("OFFICE_ID") + "</id><name>" + results.getString("OFFICE_NAME") + "</name><officeAddress1>"+results.getString("OFFICE_ADDRESS1")+ "</officeAddress1><officeAddress2>"+results.getString("OFFICE_ADDRESS2")+"</officeAddress2><officeAddress3>"+results.getString("CITY_TOWN_NAME")+"</officeAddress3><District>"+results.getInt("DISTRICT_CODE")+"</District><Phone>"+results.getString("OFFICE_PHONE_NO")+"</Phone><Fax>"+results.getString("OFFICE_FAX_NO")+"</Fax><AccUnit>"+results.getString("ACCOUNTING_UNIT")+"</AccUnit></options>";
                                    found++;
                                }
                                if (found == 0)
                                    xml = "<response><flag>failure</flag>";

                                xml = xml + "</response>";
                            } catch (SQLException e) {
                                System.out.println("Exception in Query" + e);
                            } finally {
                                results.close();
                            }
                        } catch (SQLException e) {
                            System.out.println("Exception in statement:" + e);
                        } finally {
                            statement.close();
                        }

                    } //end else*/

                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                } finally {
                    //connection.close();
                }

            } else if (mode.equals("Attach")) {
                int txtAttach =
                    Integer.parseInt(request.getParameter("txtOfficeId"));
                int Office_Id1 =
                    Integer.parseInt(request.getParameter("OfficeId"));
                String sql4 =
                    "select * from com_office_acct_units where ACCT_RENDERING_OFFICE_ID=? and office_id=?";
                PreparedStatement ps = connection.prepareStatement(sql4);
                ResultSet rs4 = null;
                ps.setInt(1, txtAttach);
                ps.setInt(2, Office_Id1);
                //System.out.println("b4 Attach");
                rs4 = ps.executeQuery();
                boolean b = rs4.next();
                //System.out.println(" boolean : " + b);
                if (!b) {
                    System.out.println("Not In Attach");
                    String sql =
                        "select OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO,Accounting_unit from com_mst_offices where OFFICE_ID=?";
                    //String sql="select com_mst_offices.OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO,ACCOUNTING_UNIT,com_office_acct_units.ACCT_RENDERING_OFFICE_ID from com_mst_offices,com_office_acct_units where com_mst_offices.office_id=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, txtAttach);
                    connection.clearWarnings();
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            if (results.next()) {
                                //xml=xml+"<options><id>"+results.getInt("OFFICE_ID") + "</id><name>" + results.getString("OFFICE_NAME") + "</name><officeAddress1>"+results.getString("OFFICE_ADDRESS1")+ "</officeAddress1><officeAddress2>"+results.getString("OFFICE_ADDRESS2")+"</officeAddress2><officeAddress3>"+results.getString("CITY_TOWN_NAME")+"</officeAddress3><District>"+results.getInt("DISTRICT_CODE")+"</District><Phone>"+results.getString("OFFICE_PHONE_NO")+"</Phone><Fax>"+results.getString("OFFICE_FAX_NO")+"</Fax><AccUnit>"+results.getString("ACCOUNTING_UNIT")+"</AccUnit><AttachOfficeId>"+results.getInt("ACCT_RENDERING_OFFICE_ID")+"</AttachOfficeId></options>";
                                xml =
 xml + "<options><id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><District>" +
   results.getInt("DISTRICT_CODE") + "</District><Phone>" +
   results.getString("OFFICE_PHONE_NO") + "</Phone><Fax>" +
   results.getString("OFFICE_FAX_NO") + "</Fax><AccUnit>" +
   results.getString("ACCOUNTING_UNIT") + "</AccUnit></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in Query" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                        connection.commit();
                    }
                } else {
                    System.out.println("inside else Attach");
                    //String sql="select OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO,ACCOUNTING_UNIT from com_mst_offices where OFFICE_ID=?";

                    try {
                        String sql3 =
                            "select com_mst_offices.OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,accounting_unit,OFFICE_FAX_NO,com_office_acct_units.Date_Effective_From,com_office_acct_units.Remarks from com_mst_offices,com_office_acct_units where com_mst_offices.office_id=com_office_acct_units.office_id and com_office_acct_units.ACCT_RENDERING_OFFICE_ID=? and  com_office_acct_units.office_id=?";

                        PreparedStatement statement1 =
                            connection.prepareStatement(sql3);
                        statement1.setInt(1, txtAttach);
                        statement1.setInt(2, Office_Id1);
                        ResultSet results1 = statement1.executeQuery();

                        try {
                            xml = "<response><flag>success</flag>";
                            boolean f = results1.next();

                            if (f) {
                                java.sql.Date DateOfFormation =
                                    results1.getDate("Date_Effective_From");
                                String DateToBeDisplayed = "";
                                if (DateOfFormation == null) {
                                    DateToBeDisplayed = "Not Specified";
                                } else {
                                    try {
                                        java.text.SimpleDateFormat sdf =
                                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                                        DateToBeDisplayed =
                                                sdf.format(DateOfFormation);
                                    } catch (Exception e) {
                                        System.out.println("error while formatting date : " +
                                                           e);
                                        DateToBeDisplayed = "Not Specified";
                                    }
                                }
                                //System.out.println("date : " + DateToBeDisplayed);
                                //xml=xml+"<options><id>"+results.getInt("OFFICE_ID") + "</id><name>" + results.getString("OFFICE_NAME") + "</name><officeAddress1>"+results.getString("OFFICE_ADDRESS1")+ "</officeAddress1><officeAddress2>"+results.getString("OFFICE_ADDRESS2")+"</officeAddress2><officeAddress3>"+results.getString("CITY_TOWN_NAME")+"</officeAddress3><District>"+results.getInt("DISTRICT_CODE")+"</District><Phone>"+results.getString("OFFICE_PHONE_NO")+"</Phone><Fax>"+results.getString("OFFICE_FAX_NO")+"</Fax><AccUnit>"+results.getString("ACCOUNTING_UNIT")+"</AccUnit><AttachOfficeId>"+results.getInt("ACCT_RENDERING_OFFICE_ID")+"</AttachOfficeId></options>";
                                xml =
 xml + "<options><id>" + results1.getInt("OFFICE_ID") + "</id><name>" +
   results1.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results1.getString("OFFICE_ADDRESS1") +
   "</officeAddress1><officeAddress2>" +
   results1.getString("OFFICE_ADDRESS2") +
   "</officeAddress2><officeAddress3>" + results1.getString("CITY_TOWN_NAME") +
   "</officeAddress3><District>" + results1.getInt("DISTRICT_CODE") +
   "</District><Phone>" + results1.getString("OFFICE_PHONE_NO") +
   "</Phone><Fax>" + results1.getString("OFFICE_FAX_NO") + "</Fax><Date>" +
   DateToBeDisplayed + "</Date><Remarks>" + results1.getString("Remarks") +
   "</Remarks><AccUnit>" + results1.getString("accounting_unit") +
   "</AccUnit></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in Query" + e);
                        } finally {
                            results1.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        //statement.close();
                    }
                }
            }

            else if (mode.equals("Add")) {
                //System.out.println("Add Mode");
                int officeid =
                    Integer.parseInt(request.getParameter("txtOffice_Id"));
                String account_unit = request.getParameter("account_unit");
                System.out.println("account_unit is" + account_unit);
                if (account_unit.equals("Y")) {
                    try {
                        System.out.println("Add");
                        String sql =
                            "Update com_mst_offices set ACCOUNTING_UNIT=? where Office_Id=?";
                        PreparedStatement statement =
                            connection.prepareStatement(sql);
                        connection.clearWarnings();
                        statement.setString(1, account_unit);
                        statement.setInt(2, officeid);
                        statement.executeUpdate();
                        //connection.setAutoCommit(false);
                        /*for(int j=0;j<slno.length;j++)
                           {
                                 statement.setInt(1,officeid);
                                 statement.setInt(2,slno1[j]);
                                 statement.setString(3,wingname[j]);
                                 statement.setInt(4,cadreid[j]);
                                 statement.setString(5,phoneno[j]);
                                 statement.setString(6,faxno[j]);
                                 statement.setString(7,naturework[j]);
                                 statement.setString(8,email[j]);
                                 statement.setDate(9,date1[j]);
                                 statement.setString(10,"unknown");
                                 statement.executeUpdate();

                           }*/
                        //connection.commit();


                    } catch (SQLException e) {
                        System.out.println("Exception in connection:" + e);
                        //connection.rollback();
                    }

                    finally {
                        //connection.close();
                    }
                } else {
                    System.out.println("in else");
                    officeid =
                            Integer.parseInt(request.getParameter("txtOffice_Id"));
                    account_unit = request.getParameter("account_unit");
                    int txtAttachedOfficeID =
                        Integer.parseInt(request.getParameter("txtAttachedOfficeID"));
                    String Remarks = request.getParameter("Remarks").trim();
                    String DateCreated =
                        request.getParameter("txtDateCreated");
                    System.out.println("officeid is" + officeid);
                    System.out.println("account_unit" + account_unit);
                    SimpleDateFormat dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d;
                    java.sql.Date date;
                    try {
                        //System.out.println(Dob);
                        d = dateFormat.parse(DateCreated);
                        dateFormat.applyPattern("yyyy-MM-dd");
                        DateCreated = dateFormat.format(d);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    date = java.sql.Date.valueOf(DateCreated);

                    try {
                        System.out.println("Add");
                        ResultSet rs1 = null;
                        String sql =
                            "Update com_mst_offices set ACCOUNTING_UNIT=? where Office_Id=?";
                        PreparedStatement statement =
                            connection.prepareStatement(sql);
                        connection.clearWarnings();
                        System.out.println("Office_id" + officeid);
                        //connection.setAutoCommit(false);
                        statement.setString(1, account_unit);
                        statement.setInt(2, officeid);
                        statement.executeUpdate();
                        statement.close();

                        String sql2 =
                            "select * from com_office_acct_units where office_id=?";
                        statement = connection.prepareStatement(sql2);
                        statement.setInt(1, officeid);
                        rs1 = statement.executeQuery();
                        if (rs1.next()) {
                            System.out.println("inside select");
                            System.out.println("txtAttachOffice" +
                                               txtAttachedOfficeID);

                            String sql3 =
                                "update com_office_acct_units set DATE_EFFECTIVE_FROM=?,REMARKS=?,UPDATED_BY_USER_ID=?,ACCT_RENDERING_OFFICE_ID=? where office_id=?";
                            PreparedStatement statement3 =
                                connection.prepareStatement(sql3);

                            statement3.setDate(1, date);
                            statement3.setString(2, Remarks);
                            statement3.setString(3, "unknown");
                            statement3.setInt(5, officeid);
                            statement3.setInt(4, txtAttachedOfficeID);
                            System.out.println("b4 update");
                            statement3.executeUpdate();
                            System.out.println("after update");
                            connection.commit();

                        } else {
                            System.out.println("txtAttachOfficeID" +
                                               txtAttachedOfficeID);
                            String sql1 =
                                "insert into COM_OFFICE_ACCT_UNITS(OFFICE_ID,ACCT_RENDERING_OFFICE_ID,DATE_EFFECTIVE_FROM,REMARKS,UPDATED_BY_USER_ID) values(?,?,?,?,?)";
                            PreparedStatement statement1 =
                                connection.prepareStatement(sql1);
                            statement1.setInt(1, officeid);
                            statement1.setInt(2, txtAttachedOfficeID);
                            statement1.setDate(3, date);
                            statement1.setString(4, Remarks);
                            statement1.setString(5, "unknown");
                            statement1.executeUpdate();
                        }
                        connection.commit();

                    } catch (SQLException e) {
                        System.out.println("Exception in connection:" + e);
                        connection.rollback();
                    }

                    finally {
                        //connection.close();
                    }

                }
                String msg =
                    "Upating Accounting Details Has been Successfully Inserted.<br>Office ID  is  : " +
                    officeid;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");
            }

        } catch (Exception e) {
            System.out.println("Exception in Base try" + e);
        }
        System.out.println("Xml is : " + xml);
        out.println(xml);
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

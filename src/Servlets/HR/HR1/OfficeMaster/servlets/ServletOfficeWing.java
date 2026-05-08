package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletOfficeWing extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        String strCriteria = "";
        int Office_Id = 0;


        int found = 0;
        String mode = request.getParameter("command");
        System.out.println(mode);


        String xml = "";
        System.out.println("Hai");
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
                    System.out.println("hai1");
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));
                    String sql =
                        "select OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO,DATE_OF_FORMATION from com_mst_offices where OFFICE_ID=?";
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

                                java.sql.Date DateOfFormation =
                                    results.getDate("DATE_OF_FORMATION");
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
                                xml =
 xml + "<options><id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><District>" +
   results.getInt("DISTRICT_CODE") + "</District><Phone>" +
   results.getString("OFFICE_PHONE_NO") + "</Phone><Fax>" +
   results.getString("OFFICE_FAX_NO") + "</Fax><date>" + DateToBeDisplayed +
   "</date></options>";
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
                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                } finally {
                    //connection.close();
                }
            }

            else if (mode.equals("Add")) {
                System.out.println("Add Mode");
                int officeid =
                    Integer.parseInt(request.getParameter("txtOffice_Id"));
                System.out.println("Office id" + officeid);
                String slno[] = request.getParameterValues("sno");
                //System.out.println(slno);
                int slno1[] = new int[slno.length];


                String wingname[] = request.getParameterValues("wingname");
                String headwing[] = request.getParameterValues("headwing");
                String datecreated[] =
                    request.getParameterValues("datecreated");
                String phoneno[] = request.getParameterValues("phoneno");
                String faxno[] = request.getParameterValues("faxno");
                String naturework[] = request.getParameterValues("naturework");
                String email[] = request.getParameterValues("email");
                String old[] = request.getParameterValues("old");
                //String
                int cadreid[] = new int[slno.length];
                for (int k = 0; k < slno.length; k++) {
                    slno1[k] = Integer.parseInt(slno[k]);
                    cadreid[k] = Integer.parseInt(headwing[k]);
                    System.out.println("slno is" + slno[k]);
                    System.out.println("cadre is" + cadreid[k]);
                    System.out.println("wingname is" + wingname[k]);
                    System.out.println("phoneno is" + phoneno[k]);
                    System.out.println("faxno is" + faxno[k]);
                    System.out.println("naturework is" + naturework[k]);
                    System.out.println("email is" + email[k]);
                }

                /*System.out.println("wingname"+wingname);
                          System.out.println("headwing"+headwing);
                          System.out.println("datecreated"+datecreated);
                          System.out.println("phoneno"+phoneno);
                          System.out.println("faxno"+faxno);
                          System.out.println("naturework"+naturework);
                          System.out.println("eamil"+email);*/


                //Convert into Sql Date
                java.sql.Date date1[] = new java.sql.Date[slno.length];
                java.util.Date d1 = null;
                //System.out.println("datecreated length"+datecreated.length);

                for (int i = 0; i < slno.length; i++) {
                    SimpleDateFormat dateFormat1 =
                        new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        //System.out.println("inside date");
                        d1 = dateFormat1.parse(datecreated[i]);
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        //System.out.println("inside date1");
                        datecreated[i] = dateFormat1.format(d1);
                        System.out.println("inside date2:" + datecreated[i]);
                        date1[i] = Date.valueOf(datecreated[i]);
                        System.out.println("date is" + date1[i]);

                    } catch (Exception e) {
                        System.out.println("Exception in Date Converted" + e);
                    }
                }

                try {
                    System.out.println("Add");
                    connection.clearWarnings();
                    connection.setAutoCommit(false);
                    String sql1 =
                        "delete from com_office_wings_tmp where office_id=?";
                    PreparedStatement statement1 =
                        connection.prepareStatement(sql1);
                    statement1.setInt(1, officeid);
                    statement1.executeUpdate();

                    String sql =
                        "insert into COM_OFFICE_WINGS_TMP(OFFICE_ID,OFFICE_WING_SINO,WING_NAME,WING_HEAD_CADRE_ID,WING_PHONE_NO,WING_FAX_NO,WING_WORK_NATURE,WING_EMAIL_ID,DATE_CREATED,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID) values(?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);

                    for (int j = 0; j < slno.length; j++) {
                        statement.setInt(1, officeid);
                        statement.setInt(2, slno1[j]);
                        statement.setString(3, wingname[j]);
                        statement.setInt(4, cadreid[j]);
                        statement.setString(5, phoneno[j]);
                        statement.setString(6, faxno[j]);
                        statement.setString(7, naturework[j]);
                        statement.setString(8, email[j]);
                        statement.setDate(9, date1[j]);
                        statement.setString(10, "unknown");
                        long l = System.currentTimeMillis();
                        Timestamp ts = new Timestamp(l);
                        statement.setTimestamp(11, ts);
                        statement.setString(12, "CR");
                        statement.executeUpdate();

                    }


                    connection.commit();


                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                    connection.rollback();
                }

                finally {
                    //connection.close();
                }
                String msg =
                    "Wing Details Has been Successfully Inserted.<br>Office ID  is  : " +
                    officeid;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");


            } else if (mode.equals("SlNo")) {

                try {
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));
                    String sql =
                        "select MAX(OFFICE_WING_SINO) from com_office_wings_tmp where office_id=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Office_Id);
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            if (results.next()) {
                                xml =
 xml + "<options><id>" + results.getInt(1) + "</id></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in QuerySlNo" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }


                } catch (Exception e) {
                    System.out.println("Exception in SlNO" + e);
                }
            }

            else if (mode.equals("TableView")) {

                try {
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));
                    String sql =
                        "select OFFICE_WING_SINO,WING_NAME,WING_HEAD_CADRE_ID,WING_PHONE_NO,WING_FAX_NO,WING_WORK_NATURE,WING_EMAIL_ID,DATE_CREATED from com_office_wings_tmp where office_id=?";
                    String sql2 =
                        "select a.OFFICE_WING_SINO,a.WING_NAME,a.WING_HEAD_CADRE_ID,a.WING_PHONE_NO,a.WING_FAX_NO,a.WING_WORK_NATURE,a.WING_EMAIL_ID,a.DATE_CREATED,b.designation from com_office_wings_tmp a,HRM_MST_WING_HEAD_DESIG b where  a.WING_HEAD_CADRE_ID=b.designation_id and office_id=? order by a.OFFICE_WING_SINO";
                    PreparedStatement statement =
                        connection.prepareStatement(sql2);
                    statement.setInt(1, Office_Id);
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            //xml=xml+"<options>";
                            while (results.next()) {
                                java.sql.Date DateOfFormation =
                                    results.getDate("DATE_CREATED");
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
                                xml =
 xml + "<SlNo>" + results.getInt(1) + "</SlNo><wingname>" +
   results.getString("WING_NAME") + "</wingname><winghead>" +
   results.getInt("WING_HEAD_CADRE_ID") + "</winghead><PhoneNo>" +
   results.getString("WING_PHONE_NO") + "</PhoneNo><FaxNo>" +
   results.getString("WING_FAX_NO") + "</FaxNo><wingnature>" +
   results.getString("WING_WORK_NATURE") + "</wingnature><Email>" +
   results.getString("WING_EMAIL_ID") + "</Email><Date>" + DateToBeDisplayed +
   "</Date><cadrename>" + results.getString("designation") + "</cadrename>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";
                            // xml=xml+"</options>";
                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in QuerySlNo" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }


                } catch (Exception e) {
                    System.out.println("Exception in SlNO" + e);
                }
            }

        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }

        System.out.println("Xml is : " + xml);
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();

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

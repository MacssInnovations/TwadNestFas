package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletMovOldRec extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("******servlet called****");
        int strExistingId = 0;
        String ShiftingDate = "", strRemarks = "";

        int To_Which_Id = 0;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
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


            try {

                strExistingId =
                        Integer.parseInt(request.getParameter("txtOffice_Id"));
                System.out.println("************  first ID :" + strExistingId);
                To_Which_Id =
                        Integer.parseInt(request.getParameter("txtOffice_Id_two"));
                System.out.println("************ To Which Id  :" +
                                   To_Which_Id);
                ShiftingDate = request.getParameter("txt_DateShifting");
                System.out.println("shifting date   :" + ShiftingDate);
                /*
                   strDateOfHandover = request.getParameter("txt_DateHandover");
                   strRemarks = request.getParameter("txt_Remark");
                   System.out.println("************  :"+strExistingId);
                    java.sql.Date dateOfConversion=null;
                    System.out.println("before converting date");
                    String dateString = strDateOfHandover;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d;
                    d = dateFormat.parse(dateString.trim());
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString = dateFormat.format(d);
                    dateOfConversion = java.sql.Date.valueOf(dateString);

                    System.out.println("converted date "+dateOfConversion);

                   strRemarks=request.getParameter("txtRemarks");
                   */
                String chk[] = request.getParameterValues("checkName");
                System.out.println("chks........." + chk.length);
                for (int i = 0; i < chk.length; i++) {
                    System.out.println("vals........." + chk[i]);
                }

                String ckdvals[] = request.getParameterValues("chk_vals");


                String slno[] = request.getParameterValues("sno");
                //System.out.println(slno);
                int slno1[] = new int[slno.length];
                System.out.println("^^^" + slno1);


                String ClosedId[] = request.getParameterValues("id");
                String ClosureDate[] = request.getParameterValues("date");
                System.out.println("shifting date   :" + ShiftingDate);


                int ClosedOfficeid[] = new int[slno.length];
                for (int k = 0; k < slno.length; k++) {
                    slno1[k] = Integer.parseInt(slno[k]);
                    ClosedOfficeid[k] = Integer.parseInt(ClosedId[k]);
                    System.out.println("slno is" + slno[k]);
                    System.out.println("second id is  :" + ClosedOfficeid[k]);

                }


                //Convert into Sql Date
                java.sql.Date date1[] = new java.sql.Date[slno.length];
                java.util.Date d1 = null;
                //System.out.println("datecreated length"+datecreated.length);

                for (int i = 0; i < slno.length; i++) {
                    SimpleDateFormat dateFormat1 =
                        new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        /* //System.out.println("inside date");
                                                         d1=dateFormat1.parse(ShiftingDate[i]);
                                                          dateFormat1.applyPattern("yyyy-MM-dd");
                                                          //System.out.println("inside date1");
                                                        ShiftingDate[i]=dateFormat1.format(d1);
                                                          System.out.println("inside date2:"+ShiftingDate[i]);*/
                        date1[i] = Date.valueOf(ClosureDate[i]);
                        System.out.println("date is   :" + date1[i]);

                    } catch (Exception e) {
                        System.out.println("Exception in Date Converted" + e);
                    }
                }

                for (int i = 0; i < chk.length; i++) {
                    System.out.println("vals........." + ckdvals[i]);
                    if (ckdvals[i].equals("yes")) {
                        System.out.println("close of id selected :     " +
                                           ClosedOfficeid[i]);

                    }

                }


                try {
                    System.out.println("Add");


                    String sql =
                        "select * from com_office_closed_records where office_id=? and old_rec_office_id=?";
                    //String sql="insert into COM_OFFICE_CLOSED_RECORDS(OFFICE_ID,OLD_REC_OFFICE_ID,DATE_EFFECTIVE_FROM,REMARKS) values(?,?,?,?)";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);

                    connection.clearWarnings();
                    connection.setAutoCommit(false);


                    for (int j = 0; j < chk.length; j++) {
                        if (ckdvals[j].equals("yes")) {
                            System.out.println("comming");
                            statement.setInt(1, strExistingId);
                            statement.setInt(2, ClosedOfficeid[j]);
                            rs1 = statement.executeQuery();
                            String sql_toInsert =
                                "insert into COM_OFFICE_RECORD_MOVE_LOG(CLOSED_OFFICE_ID,OLD_REC_MAINTAIN_OFFICE_ID,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID) values(?,?,?,?)";
                            PreparedStatement statement1 =
                                connection.prepareStatement(sql_toInsert);
                            String sql2 =
                                "update com_office_closed_records set office_id=? where old_rec_office_id=? and office_id=?";
                            PreparedStatement statement2 =
                                connection.prepareStatement(sql2);
                            while (rs1.next()) {
                                int first = rs1.getInt("OLD_REC_OFFICE_ID");
                                int second = rs1.getInt("OFFICE_ID");
                                System.out.println("first  :" + first);
                                System.out.println("second  :" + second);
                                statement1.setInt(1, first);
                                statement1.setInt(2, second);
                                statement1.setDate(3,
                                                   rs1.getDate("DATE_EFFECTIVE_FROM"));
                                statement1.setString(4, "DATE_EFFECTIVE_FROM");
                                statement1.executeUpdate();
                                System.out.println("to which :" + To_Which_Id);
                                //rs1.updateInt("OFFICE_ID",To_Which_Id);
                                //rs1.updateRow();

                                statement2.setInt(1, To_Which_Id);
                                statement2.setInt(2, first);
                                statement2.setInt(3, second);
                                statement2.executeUpdate();

                            }
                            System.out.println("333");
                        }
                    }
                    connection.commit();


                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                    String msg =
                        "Record With This Given ID Already Exist <br>";
                    msg = "<br><br><p><b>" + msg + "</b></p>";
                    sendMessage(response, msg, "back");


                    connection.rollback();
                } finally {
                    //connection.close();
                }
                String msg =
                    "Records Has been Moved Successfully To This Office ID.<br>Office ID  is  : " +
                    strExistingId;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "back");


            } catch (Exception e) {
                System.out.println("Exception in retriving values :" + e);
                sendMessage(response,
                            "Exception in retriving values may be due to " + e,
                            "back");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            sendMessage(response,
                        "probably Failed to Establish connection to the database server.. due to " +
                        e, "back");
        }

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

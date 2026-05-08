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

public class ServletEditReqForNewOfficeCreation extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("*REQ*EDit****servlet called****");

        String Str_Prop_Office_Name = "", Str_Prop_Office_Short_Name =
            "", cmb_Level_Id = "", cmb_Primary_ID = "", cmb_Secondary_ID =
            "", Str_Remark = "", Str_Remark_Second = "";
        String Date_of_request = "";
        int NewOffice_Req_Id = 0, Requesting_OfficeId = 0;

        Connection connection = null;
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());


            try {


                System.out.println("B4");
                NewOffice_Req_Id =
                        Integer.parseInt(request.getParameter("txt_Request_Id1"));
                System.out.println("************ new office req ID :" +
                                   NewOffice_Req_Id);
                System.out.println("aftr");
                Requesting_OfficeId =
                        Integer.parseInt(request.getParameter("txtOffice_Id1"));
                System.out.println("************  requesting ID :" +
                                   Requesting_OfficeId);


                Date_of_request = request.getParameter("txt_Date");
                System.out.println("***********Date of Request:" +
                                   Date_of_request);

                Str_Prop_Office_Name =
                        request.getParameter("txt_Prop_Office_Name");
                System.out.println("************Propesed Office Name:" +
                                   Str_Prop_Office_Name);

                Str_Prop_Office_Short_Name =
                        request.getParameter("txt_Prop_Short_Name");
                System.out.println("************Propesed Office Name:" +
                                   Str_Prop_Office_Short_Name);

                cmb_Level_Id = request.getParameter("cmbLevelId");
                System.out.println("************Level ID :" + cmb_Level_Id);


                cmb_Primary_ID = request.getParameter("cmbPrimaryID");
                System.out.println("************Primary ID :" +
                                   cmb_Primary_ID);

                Str_Remark = request.getParameter("txt_Remark");
                System.out.println("************Remark:" + Str_Remark);

                // cmb_Secondary_ID=request.getParameter("cmbSecondaryID");
                // System.out.println("************ Secondary ID :"+cmb_Secondary_ID);

                // Str_Remark_Second=request.getParameter("txt_Remark_Second");
                // System.out.println("************Remark:"+Str_Remark_Second);


                String slno[] = request.getParameterValues("sno");


                //System.out.println(slno);
                int slno1[] = new int[slno.length];
                System.out.println("^^^" + slno1);

                String Addl_Nature[] =
                    request.getParameterValues("Secondary_Work");
                //  String ClosedName[]=request.getParameterValues("Office_Name");
                //String ClosedDate[]=request.getParameterValues("Closure_Date");
                // String HandOverDate[]=request.getParameterValues("Handover_Date");
                String Remark[] = request.getParameterValues("Remark");
                String rowChk[] = request.getParameterValues("Old");
                // String naturework[]=request.getParameterValues("naturework");
                //String email[]=request.getParameterValues("email");

                int officeid[] = new int[slno.length];
                for (int k = 0; k < slno.length; k++) {
                    slno1[k] = Integer.parseInt(slno[k]);

                    System.out.println("slno is" + slno[k]);
                    System.out.println("second nature  :" + Addl_Nature[k]);
                    System.out.println("------------Remark  :" + Remark[k]);

                }


                //Convert into Sql Date
                java.sql.Date date1 = new java.sql.Date(0);
                java.util.Date d1 = null;
                //System.out.println("datecreated length"+datecreated.length);


                SimpleDateFormat dateFormat1 =
                    new SimpleDateFormat("dd/MM/yyyy");
                try {
                    System.out.println("inside date");
                    d1 = dateFormat1.parse(Date_of_request);
                    dateFormat1.applyPattern("yyyy-MM-dd");
                    System.out.println("inside date1");
                    Date_of_request = dateFormat1.format(d1);
                    System.out.println("inside date2:" + Date_of_request);
                    date1 = Date.valueOf(Date_of_request);
                    System.out.println("date is   :" + date1);

                } catch (Exception e) {
                    System.out.println("Exception in Date Converted" + e);
                }

                System.out.println("after date-----------------------------");


                try {

                    connection.clearWarnings();
                    connection.setAutoCommit(false);

                    String sql_to_del1 =
                        "delete from com_office_new_requests where NEW_OFFICE_REQUEST_ID=?";
                    PreparedStatement st100 =
                        connection.prepareStatement(sql_to_del1);
                    st100.setInt(1, NewOffice_Req_Id);
                    st100.executeUpdate();
                    System.out.println("values Deleted---first--");


                    String sql_to_First =
                        "insert into com_office_new_requests (NEW_OFFICE_REQUEST_ID,REQUESTING_OFFICE_ID,DATE_OF_REQUEST,NEW_OFFICE_NAME,NEW_OFFICE_SHORT_NAME,NEW_OFFICE_LEVEL_ID,PRIMARY_WORK_NATURE_ID,REMARKS) values(?,?,?,?,?,?,?,?)";
                    PreparedStatement st0 =
                        connection.prepareStatement(sql_to_First);

                    //connection.clearWarnings();
                    //connection.setAutoCommit(false);

                    System.out.println("B4 insert");

                    st0.setInt(1, NewOffice_Req_Id);
                    st0.setInt(2, Requesting_OfficeId);
                    st0.setDate(3, date1);
                    st0.setString(4, Str_Prop_Office_Name);
                    st0.setString(5, Str_Prop_Office_Short_Name);
                    st0.setString(6, cmb_Level_Id);
                    st0.setString(7, cmb_Primary_ID);
                    st0.setString(8, Str_Remark);
                    st0.executeUpdate();
                    System.out.println("-----------------inserted into the first Table----------------");


                    String sql_to_del =
                        "delete from com_office_new_addl_works where NEW_OFFICE_REQUEST_ID=?";
                    PreparedStatement st1 =
                        connection.prepareStatement(sql_to_del);
                    st1.setInt(1, NewOffice_Req_Id);
                    st1.executeUpdate();
                    System.out.println("values Deleted-----");

                    System.out.println("INSERT----------------ROWCHECK--:");
                    String sql =
                        "insert into com_office_new_addl_works(NEW_OFFICE_REQUEST_ID,SECONDARY_WORK_NATURE_ID,REMARKS,UPDATED_BY_USER_ID) values(?,?,?,?)";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, NewOffice_Req_Id);
                    //connection.clearWarnings();
                    //connection.setAutoCommit(false);
                    for (int j = 0; j < slno.length; j++) {

                        statement.setString(2, Addl_Nature[j]);
                        statement.setString(3, Remark[j]);
                        statement.setString(4, "user id");
                        statement.executeUpdate();

                    }


                    connection.commit();
                    System.out.println("in insert");


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

                String msg = "Request Has been Posted Successfully.<br>";
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "back");


            } catch (Exception e) {
                System.out.println("Exception in retriving values :" + e);
                //  sendMessage(response,"Exception in retriving values may be due to " + e,"back");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
            // sendMessage(response,"probably Failed to Establish connection to the database server.. due to " + e,"back");
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

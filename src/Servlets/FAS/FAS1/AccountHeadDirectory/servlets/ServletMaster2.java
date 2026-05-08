package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.lang.IllegalArgumentException;

import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.lang.IllegalArgumentException.*;


public class ServletMaster2 extends HttpServlet {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


        // opening connection to the database
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connection = DriverManager.getConnection("Jdbc:Odbc:fas");
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(true);


        String command = "";
        System.out.println("servlet calledddddddddddddd");

        //String  strHead_Office_Code="";
        String strAcct_Head_Code = "";
        String strAcct_Head_Des = "";
        Date strDate_Creation = null;
        String strMajor_grp_code = "";
        String strMinor_grp_code = "";
        String strSub_grp1 = "";
        String strSub_grp2 = "";
        String strBal_type = "";
        String strStatus = "";
        Date strLast_date_used = null;
        String strFile_Ref_No = "";
        Date strFile_Ref_Date = null;
        String strRes_access = "";
        String strAccessBy = "";
        String strSub_ledg = "";
        String strRemarks = "";

        try {

            System.out.println("good");
            //strHead_Office_Code=request.getParameter("Head_off_code");
            // System.out.println(strHead_Office_Code);

            strAcct_Head_Code = request.getParameter("acct_head_code");
            System.out.println(strAcct_Head_Code);
            strAcct_Head_Des = request.getParameter("acct_head_des");
            System.out.println(strAcct_Head_Des);


            strMajor_grp_code = request.getParameter("major_grp_code");
            System.out.println(strMajor_grp_code);

            strMinor_grp_code = request.getParameter("minor_grp_code");
            System.out.println(strMinor_grp_code);
            strSub_grp1 = request.getParameter("sub_grp1");
            System.out.println(strSub_grp1);
            strSub_grp2 = request.getParameter("sub_grp2");
            System.out.println(strSub_grp2);
            strBal_type = request.getParameter("bal_type1");
            System.out.println(strBal_type);
            strStatus = request.getParameter("status1");
            System.out.println(strStatus);
            // String st2=request.getParameter("Last_date");
            //strLast_date_used = Date.valueOf("st2");
            //System.out.println(strLast_date_used);
            strFile_Ref_No = request.getParameter("File_Ref_No");
            System.out.println(strFile_Ref_No);
            //String st3=request.getParameter("File_R_D");
            //strFile_Ref_Date= Date.valueOf("st3");
            //System.out.println(strFile_Ref_Date);
            strRes_access = request.getParameter("res_access1");
            System.out.println(strRes_access);
            if (strRes_access.equals("Y")) {
                strAccessBy = "no";
                System.out.println(strAccessBy);
            }

            strAccessBy = request.getParameter("HAccBy");

            System.out.println(strAccessBy);
            strSub_ledg = request.getParameter("sub_ledg1");
            System.out.println(strSub_ledg);
            strRemarks = request.getParameter("txtRemarks");
            System.out.println(strRemarks);
        } catch (Exception e) {
            System.out.println("exce **** " + e);
        }


        String sql =
            "update  FAS_ACCOUNT_HEAD_MASTER set MAJOR_HEAD_CODE=?,MINOR_HEAD_CODE=?,SUB_HEAD1_CODE=?,SUB_HEAD2_CODE=?,ACCOUNT_HEAD_DESC=?,DATE_OF_CREATION=?,BALANCE_TYPE=?,STATUS=?,ACTIVE_UPTO_DATE=?,FILE_REF_NO=?,FILE_REF_DATE=?,ACCESS_RESTRICTED=?,ACCESSIBLE_BY_CODE=?,SUB_LEDGER_TYPE_APPLY=?,REMARKS=?,ENTRY_DATE=?,USER_ID=? where ACCOUNT_HEAD_CODE='strAcct_Head_Code'";
        try {
            //for date functionality
            //first date field for Date_creation field in the form

            String dateString1 = request.getParameter("date_creation");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1;
            try {
                d1 = dateFormat.parse(dateString1);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat.format(d1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
            System.out.println(date1);

            //for second date field ie.Last date field in the form

            String dateString2 = request.getParameter("Last_date_used");
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d2;
            try {
                d2 = dateFormat.parse(dateString2);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString2 = dateFormat.format(d2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            java.sql.Date date2 = java.sql.Date.valueOf(dateString2);
            System.out.println(date2);

            //for third date field ie. File Reference Date field in the form

            String dateString3 = request.getParameter("File_Ref_Date");
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d3;
            try {
                try {
                    d3 = dateFormat.parse(dateString3);
                    dateFormat.applyPattern("yyyy-MM-dd");
                    dateString3 = dateFormat.format(d3);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                java.sql.Date date3 = java.sql.Date.valueOf(dateString3);
                System.out.println(date3);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);


                ps = connection.prepareStatement(sql);
                //ps.setString(1,strHead_Office_Code);
                ps.setString(1, strMajor_grp_code);
                ps.setString(2, strMinor_grp_code);
                ps.setString(3, strSub_grp1);
                ps.setString(4, strSub_grp2);
                ps.setString(5, strAcct_Head_Code);
                ps.setString(6, strAcct_Head_Des);
                ps.setDate(7, date1);
                ps.setString(8, strBal_type);
                ps.setString(9, strStatus);
                ps.setDate(10, date2);
                ps.setString(11, strFile_Ref_No);
                ps.setDate(12, date3);
                ps.setString(13, strRes_access);
                ps.setString(14, strAccessBy);
                ps.setString(15, strSub_ledg);
                ps.setString(16, strRemarks);
                ps.setTimestamp(17, ts);
                ps.setInt(18, 0);
                System.out.println("before update");
                ps.executeUpdate();
                System.out.println("afetre update");
                ps.close();

                if (strSub_ledg.equals("Y")) {
                    String subledger[] = request.getParameterValues("HSLCode");
                    PreparedStatement ps1 =
                        connection.prepareStatement("update  FAS_APPLICABLE_SL_TYPE set ACCOUNT_HEAD_CODE=?, SUB_LEDGER_TYPE_CODE=? where ACCOUNT_HEAD_CODE='strAcct_Head_Code' ");
                    System.out.println("length :" + subledger.length);
                    for (int i = 0; i < subledger.length; i++) {
                        System.out.println("value " + subledger[i]);
                    }

                    for (int i = 0; i < subledger.length; i++) {
                        System.out.println("inside for");
                        ps1.setString(1, strAcct_Head_Code);
                        ps1.setString(2, subledger[i]);
                        ps1.setTimestamp(3, ts);
                        ps1.setInt(4, 0);
                        ps1.executeUpdate();
                    }
                    ps1.close();

                }
                System.out.println("success");
                pw.write("<html>");
                pw.write("<body>");
                pw.write("Record inserted successfully</body></html>");
            } catch (IllegalArgumentException s) {
                System.out.println("IAE:" + s);

                pw.write("<html>");
                pw.write("<body>");
                pw.write("Sorry Record Not Inserted</body></html>");

            }
        }


        catch (Exception e) {
            System.out.println("exce ****2 " + e);
            pw.write("<html>");
            pw.write("<body>");
            pw.write("Sorry Record Not Inserted</body></html>");


        }


    }
}

package Servlets.FAS.FAS1.PaymentSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ListCheque_Dishonour extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        Connection con = null;
        ResultSet rs = null;

        PreparedStatement ps = null;
        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in openeing connection :" + e);
            //               sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        System.out.println("servlet called");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strType = "", xml = "<response>";
        try {
            strType = request.getParameter("Command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0;
        Date txtFrom_date = null, txtTo_date = null;
        Calendar c;
        String sql = "", newcheq_status = "";
        String newcheq_date = "", newcheqamt = "", remarks =
            "", recieved_date = "";
        int new_cheqno = 0;


        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);
        System.out.println("strtype  " + strType);
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);

        if (strType.equalsIgnoreCase("searchByMonth")) {
            System.out.println("inside searchbymonth");
            xml = "<response><command>searchByMonth</command>";


            sql =
 "select DOCUMENT_TYPE,DOC_NO,to_char(DOCUMENT_DATE,'DD/MM/YYYY') as doc_date,CHEQUE_OR_DD,CHEQUE_DD_NO,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as oldcheq_date, " +
   " trim(to_char(AMOUNT,'99999999999999.99')) as oldcheqamt,REMARKS,to_char(RECIEVED_DATE,'DD/MM/YYYY') as recieved_date,RECEIVED_CHEQUE,RECEIVED_CHEQUE_NO," +
   " to_char(RECEIVED_CHEQUE_DATE,'DD/MM/YYYY') as newcheq_date," +
   " trim(to_char(RECEIVED_CHEQUE_AMOUNT,'99999999999999.99')) as newcheqamt from FAS_CHEQUE_DISHONOUR " +
   " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and revert_cheqdishonour_status is null";
            try {
                System.out.println("1:"+sql);
                System.out.println(cmbAcc_UnitCode);
                System.out.println(cmbOffice_code);
                System.out.println(txtCB_Year);
                System.out.println(txtCB_Month);
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);

                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                System.out.println("xml......");
                rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println("2");

                    try {


                        if (rs.getString("REMARKS") == null) {
                            remarks = "-";
                        } else {
                            remarks = rs.getString("REMARKS");
                            System.out.println("REMARKS" + remarks);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting REMARKS values" +
                                           e);

                    }
                    try {


                        if (rs.getString("RECEIVED_CHEQUE") == null) {
                            newcheq_status = "N";
                        } else {
                            newcheq_status = rs.getString("RECEIVED_CHEQUE");
                            System.out.println("newcheq_status" +
                                               newcheq_status);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting REMARKS values" +
                                           e);

                    }
                    try {


                        if (rs.getString("recieved_date") == null) {
                            recieved_date = "-";
                        } else {
                            recieved_date = rs.getString("recieved_date");
                            System.out.println("recieved_date" +
                                               recieved_date);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting recieved_date values" +
                                           e);

                    }

                    try {


                        if (rs.getString("newcheq_date") == null) {
                            newcheq_date = "-";
                        } else {
                            newcheq_date = rs.getString("newcheq_date");
                            System.out.println("newcheq_date" + newcheq_date);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting newcheq_date values" +
                                           e);

                    }
                    try {


                        if (rs.getInt("RECEIVED_CHEQUE_NO") == 0) {
                            new_cheqno = 0;
                        } else {
                            new_cheqno = rs.getInt("RECEIVED_CHEQUE_NO");
                            System.out.println("new_cheqno" + new_cheqno);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting new_cheqno values" +
                                           e);

                    }
                    try {


                        if (rs.getString("newcheqamt") == null) {
                            newcheqamt = "-";
                        } else {
                            newcheqamt = rs.getString("newcheqamt");
                            System.out.println("newcheqamt" + newcheqamt);
                        }
                    } catch (Exception e) {
                        System.out.println("Error in getting newcheqamt values");

                    }
                    xml = xml + "<leng>";
                    xml =
 xml + "<doc_type>" + rs.getString("DOCUMENT_TYPE") + "</doc_type>";
                    // System.out.println(rs.getString("DOCUMENT_TYPE"));
                    xml =
 xml + "<doc_no>" + rs.getString("DOC_NO") + "</doc_no>";
                    //  System.out.println(rs.getString("DOCUMENT_NO"));
                    xml =
 xml + "<doc_date>" + rs.getString("doc_date") + "</doc_date>";
                    // System.out.println(rs.getString("doc_date"));
                    xml =
 xml + "<oldcheq_type>" + rs.getString("CHEQUE_OR_DD") + "</oldcheq_type>";
                    //  System.out.println(rs.getString("CHEQUE_OR_DD"));
                    xml =
 xml + "<old_cheqno>" + rs.getInt("CHEQUE_DD_NO") + "</old_cheqno>";
                    //   System.out.println(rs.getInt("CHEQUE_DD_NO"));
                    xml =
 xml + "<old_cheqdate>" + rs.getString("oldcheq_date") + "</old_cheqdate>";
                    //   System.out.println(rs.getString("oldcheq_date"));
                    xml =
 xml + "<old_cheqamt>" + rs.getString("oldcheqamt") + "</old_cheqamt>";
                    //     System.out.println(rs.getString("oldcheqamt"));

                    xml = xml + "<remarks>" + remarks + "</remarks>";
                    xml =
 xml + "<recieved_date>" + recieved_date + "</recieved_date>";
                    System.out.println(recieved_date);
                    xml =
 xml + "<newcheq_status>" + newcheq_status + "</newcheq_status>";
                    //   System.out.println(rs.getString("ISSUE_CHEQUE"));
                    xml = xml + "<new_cheqno>" + new_cheqno + "</new_cheqno>";
                    //    System.out.println(new_cheqno);
                    xml =
 xml + "<new_cheqdate>" + newcheq_date + "</new_cheqdate>";
                    //   System.out.println(newcheq_date);
                    xml =
 xml + "<new_cheqamt>" + rs.getString("newcheqamt") + "</new_cheqamt>";
                    //   System.out.println(rs.getString("newcheqamt"));

                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
            }

        }

        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
    }
}

package Servlets.FAS.FAS1.FundTransferSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;


public class ListFundAllotmentSubmitServ2 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        System.out.println("inside fundsubmit");
        String strCommand = "";
        Connection con = null;
        ResultSet res1 = null;
        ResultSet rs3 = null;
        ResultSet rs2 = null;
        ResultSet rs4 = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps2 = null;
        String xml = "";
        Calendar c;
        Date LetterDate1 = null;
        int tot_amt = 0, ho_refno = 0;
        java.sql.Date ho_refdate = null;
        HttpSession session = request.getSession(false);
        String updatedby = (String)session.getAttribute("UserId");
        System.out.println("updated by user id is:" + updatedby);
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        java.sql.Timestamp sqldt = new java.sql.Timestamp(dt.getTime());
        System.out.println("Time Stamp:" + sqldt);
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
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {

            System.out.println("................Start in the servlet...........");
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);


        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        if (strCommand.equalsIgnoreCase("update")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>update</command>";

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            int slno = 0, accunitid = 0, officeid = 0, cashbookyear =
                0, cashbookmonth = 0, count = 0;
            slno = Integer.parseInt(request.getParameter("slno"));
            System.out.println("slno" + slno);
            String CheqType = "";
            accunitid =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            System.out.println("unit id" + accunitid);
            officeid =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
            System.out.println("office_id" + officeid);
            cashbookyear =
                    Integer.parseInt(request.getParameter("txtCB_Year"));
            System.out.println("year" + cashbookyear);
            cashbookmonth =
                    Integer.parseInt(request.getParameter("txtCB_Month"));
            System.out.println("month" + cashbookmonth);
            Double transAmt =
                Double.parseDouble(request.getParameter("transAmt"));
            System.out.println("alot_amt_hid" + transAmt);
            Double fundreq =
                Double.parseDouble(request.getParameter("fundreq"));
            System.out.println("fundreq" + fundreq);
            // Date  LetterDate=request.getParameter("cmbOffice_code");
            // OffLetterDate,
            int tranOffice =
                Integer.parseInt(request.getParameter("tranOffice"));
            System.out.println("tranOffice" + tranOffice);
            // String tranOfficeName=request.getParameter("cmbOffice_code");
            // String fundtype=request.getParameter("cmbOffice_code");
            String fundtypeid = request.getParameter("fundtypeid");
            System.out.println("fundtypeid" + fundtypeid);
            String LetterNo = request.getParameter("LetterNo");
            System.out.println("LetterNo" + LetterNo);
            String OffLetterNo = request.getParameter("OffLetterNo");
            System.out.println("OffLetterNo" + OffLetterNo);
            String reasonold = request.getParameter("reason");
            System.out.println("reasonold" + reasonold);

            String LetterDate = null;
            LetterDate = request.getParameter("LetterDate");
            System.out.println("LetterDate:" + LetterDate);
            String sdTd[] = request.getParameter("LetterDate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sdTd[2]), Integer.parseInt(sdTd[1]) -
                         1, Integer.parseInt(sdTd[0]));
            java.util.Date d = c.getTime();
            LetterDate1 = new Date(d.getTime());

            String OffLetterDate = null;
            Date OffLetterDate1 = null;
            OffLetterDate = request.getParameter("OffLetterDate");
            System.out.println("OffLetterDate:" + OffLetterDate);
            String sdTd1[] = request.getParameter("OffLetterDate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sdTd1[2]), Integer.parseInt(sdTd1[1]) -
                         1, Integer.parseInt(sdTd1[0]));
            d = c.getTime();
            OffLetterDate1 = new Date(d.getTime());

            String remarks = request.getParameter("remarks");
            System.out.println("txtRemarks" + remarks);
            int voucherno = 0;
            voucherno = Integer.parseInt(request.getParameter("txtVoucherNo"));
            System.out.println("voucher no is :" + voucherno);
            int CheqNum = 0;
            CheqNum = Integer.parseInt(request.getParameter("CheqNo"));
            System.out.println("CheqNum" + CheqNum);

            String CheqDate = null;
            Date CheqDate1 = null;
            CheqDate = (request.getParameter("CheqDate"));
            System.out.println(CheqDate);
            String sdTd2[] = request.getParameter("CheqDate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sdTd2[2]), Integer.parseInt(sdTd2[1]) -
                         1, Integer.parseInt(sdTd2[0]));
            d = c.getTime();
            CheqDate1 = new Date(d.getTime());

            CheqType = request.getParameter("CheqType");
            System.out.println("CheqType" + CheqType);
            String letter_gen = "";
            letter_gen = request.getParameter("letter_gen");
            System.out.println("letter_gen" + letter_gen);

            // if(letter_gen!=Y)

            try {

                try {
                    System.out.println("inner try b4");
                    try {


                        ps1 =
 con.prepareStatement("  select *                 " +
                      "  from                        " +
                      "    FUND_ALLOTMENT_TRANSACTION " +
                      "  where ACCOUNTING_UNIT_ID=? and office_id=? and CASHBOOK_YEAR=? and cashbook_month=? " +
                      " and SL_NO=? and VOUCHER_NO=? and LETTER_GEN='Y' ");
                        ps1.setInt(1, accunitid);
                        ps1.setInt(2, tranOffice);
                        ps1.setInt(3, cashbookyear);
                        ps1.setInt(4, cashbookmonth);
                        ps1.setInt(5, slno);
                        ps1.setInt(6, voucherno);

                        System.out.println("1");
                        res1 = ps1.executeQuery();
                        System.out.println("2");
                        System.out.println("leeter generated");
                        while (res1.next()) // if the row doesn't return by the query
                        {
                            count++;
                            System.out.println("3");
                        }
                        if (count > 0) {
                            System.out.println("4");
                            sendMessage(response, "Letter Generated already",
                                        "ok");
                            return;
                        }
                        res1.close();
                        ps1.close();


                    } catch (Exception e) {
                        System.out.println("---------------->>>err" + e);

                    }


                    //slno+=1;

                    System.out.println("comes inside flag else part");
                    ps2 =
 con.prepareStatement("update FUND_ALLOTMENT_TRANSACTION set SL_NO=?,REF_NO=?,REF_DATE=?,FUND_TYPE=?,AMOUNT=?,PARTICULARS=?,FUND_REQUESTED=?,voucher_no=?,updated_by_user_id=?,updated_date=?,CHEQUE_OR_DD=?,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=?,HO_REF_NO=?,HO_REF_DATE=?,REASON =? where ACCOUNTING_UNIT_ID=? and office_id=? and CASHBOOK_YEAR=? and cashbook_month=? and LETTER_GEN='N' ");
                    System.out.println("update FUND_ALLOTMENT_TRANSACTION set SL_NO=" +
                                       slno + ",REF_NO='" + LetterNo +
                                       "',REF_DATE='" + LetterDate1 +
                                       "',FUND_TYPE='" + fundtypeid +
                                       "',AMOUNT=" + transAmt +
                                       ",PARTICULARS='" + remarks +
                                       "',FUND_REQUESTED=" + fundreq +
                                       ",voucher_no=" + voucherno +
                                       ",updated_by_user_id='" + updatedby +
                                       "',updated_date='" + ts +
                                       "',CHEQUE_OR_DD='" + CheqType +
                                       "',CHEQUE_DD_NO=" + CheqNum +
                                       ",CHEQUE_DD_DATE='" + CheqDate +
                                       "',HO_REF_NO='" + OffLetterNo +
                                       "',HO_REF_DATE='" + OffLetterDate +
                                       "',REASON ='" + reasonold +
                                       "' where ACCOUNTING_UNIT_ID=" +
                                       accunitid + " and office_id=" +
                                       tranOffice + " and CASHBOOK_YEAR=" +
                                       cashbookyear + " and cashbook_month=" +
                                       cashbookmonth + " and LETTER_GEN='N' ");
                    ps2.setInt(1, slno);
                    ps2.setString(2, LetterNo);
                    ps2.setDate(3, LetterDate1);
                    System.out.println(LetterDate1);
                    ps2.setString(4, fundtypeid);
                    ps2.setDouble(5, transAmt);
                    ps2.setString(6, remarks);
                    ps2.setDouble(7, fundreq);
                    ps2.setInt(8, voucherno);
                    ps2.setString(9, updatedby);
                    ps2.setTimestamp(10, ts);
                    ps2.setString(11, CheqType);
                    ps2.setInt(12, CheqNum);
                    ps2.setDate(13, CheqDate1);
                    ps2.setString(14, OffLetterNo);
                    ps2.setDate(15, OffLetterDate1);
                    System.out.println(OffLetterDate1);
                    ps2.setString(16, reasonold);
                    ps2.setInt(17, accunitid);
                    ps2.setInt(18, tranOffice);
                    ps2.setInt(19, cashbookyear);
                    ps2.setInt(20, cashbookmonth);

                    ps2.executeUpdate();
                    System.out.println("end.....");
                    xml = xml + "<flag>success</flag>";
                    //  System.out.println("x is++++----"+x);
                    //ps2.close();

                }

                catch (Exception e) {
                    System.out.println("Error in updating data from the trn table..." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception ex4) {
                System.out.println("Exception in update......" + ex4);
                xml = xml + "<flag>failure</flag>";
            }


        }

        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        //sendMessage(response,"The data has been updated Successfully ","ok");
        // return;
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            System.out.println("Inside.....................");
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("error in messenger" + e);
        }
    }


}

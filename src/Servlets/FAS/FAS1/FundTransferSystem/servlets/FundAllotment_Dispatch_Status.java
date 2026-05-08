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


public class FundAllotment_Dispatch_Status extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
        //CallableStatement cs=null,cs1=null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;

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
            System.out.println("connected");
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
        }
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign.. command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        String cmbAcc_UnitCodestr = "", cmbOffice_codestr = "";
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;
        try {
            //cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            cmbAcc_UnitCodestr = (request.getParameter("accunit"));
            cmbAcc_UnitCode = Integer.parseInt(cmbAcc_UnitCodestr);
            System.out.println("cmbAcc_UnitCode========" + cmbAcc_UnitCodestr);
        } catch (NumberFormatException e) {
            System.out.println("exception  here" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        try {
            cmbOffice_codestr = request.getParameter("diviname");
            // cmbOffice_code=Integer.parseInt(cmbOffice_codestr);
            System.out.println(" cmbOffice_code========" + cmbOffice_codestr);
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);


        if (strCommand.equalsIgnoreCase("loadDivision")) {
            int count = 0;
            String remarks = "", fundtype = "", fundtype1 =
                "", tranOfficeName = "", fundtypeid = "";
            double transAmt = 0;
            int tranOffice = 0;
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            String xml = "";
            xml = "<response><command>loadDivision</command>";

            String stryear = request.getParameter("txtyear");
            String strmonth = request.getParameter("txtmonth");
            int year = Integer.parseInt(stryear);
            int month = Integer.parseInt(strmonth);
            System.out.println("year month:" + year + "  " + month);
            int accunit = Integer.parseInt(request.getParameter("accunit"));
            System.out.println("accounting unit is :" + accunit);
            //   int divname=Integer.parseInt(request.getParameter("divname"));
            int offid = Integer.parseInt(request.getParameter("officeId"));
            System.out.println("office id is :" + offid);
            /*   int vno=Integer.parseInt(request.getParameter("voucherno"));
                 System.out.println("Voucher no is :"+vno);*/
            System.out.println("txtmonth " + month);
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            int voucherno = 0, slno = 0, accunitid = 0, officeid =
                0, cashbookyear = 0, cashbookmonth = 0;

            String CheqType = "";


            int cnt = 0, CheqNo = 0;

            double fundreq = 0;
            String CheqorDD = "", letgen = "", CheqDate = "", LetterDate =
                "", OffLetterDate = "", LetterNo = "", OffLetterNo =
                "", reason = "", HOREFNO = "", HOREFDATE = "";

            try {
                ps =
  con.prepareStatement("select a.SL_NO,a.ACCOUNTING_UNIT_ID,a.OFFICE_ID,a.CASHBOOK_YEAR,a.cashbook_month,a.FUND_TYPE, " +
                       "a.REF_NO,a.REF_DATE,a.AMOUNT,a.PARTICULARS,a.FUND_REQUESTED,a.voucher_no,a.updated_by_user_id, " +
                       "a.updated_date,a.CHEQUE_OR_DD,a.CHEQUE_DD_NO, " +
                       "a.CHEQUE_DD_DATE,a.HO_REF_NO,a.HO_REF_DATE,a.REASON,a.LETTER_GEN,b.office_name,c.REF_NO as HOREFNO,c.REF_DATE as HOREFDATE from FUND_ALLOTMENT_TRANSACTION a,com_mst_offices b,FUND_ALLOTMENT_MASTER c " +
                       " where a.OFFICE_ID=b.OFFICE_ID and a.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and a.CASHBOOK_YEAR=c.CASHBOOK_YEAR and a.CASHBOOK_MONTH=c.CASHBOOK_MONTH " +
                       " and a.LETTER_GEN ='N' and a.VOUCHER_NO=c.VOUCHER_NO and a.ACCOUNTING_UNIT_ID=? and a.CASHBOOK_YEAR=? and a.cashbook_month=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, year);
                ps.setInt(3, month);
                // ps.setInt(3,voucherno);

                rs = ps.executeQuery();


                while (rs.next()) {
                    cnt++;
                    slno = rs.getInt("SL_NO");
                    System.out.println(slno);

                    voucherno = rs.getInt("voucher_no");
                    System.out.println(voucherno);

                    if (rs.getString("PARTICULARS") == null)
                        remarks = "--";
                    else {
                        remarks = rs.getString("PARTICULARS");
                        System.out.println("listremarks" + remarks);
                    }

                    try {

                        if (rs.getDate("HOREFDATE") == null)
                            HOREFDATE = "";
                        else {
                            java.sql.Date dd = rs.getDate("HOREFDATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            HOREFDATE = sdf.format(dd);
                            System.out.println("HOREFDATE is" + HOREFDATE);
                        }

                        if (HOREFNO.equals(null)) {
                            HOREFNO = "-";
                        } else {
                            HOREFNO = rs.getString("HOREFNO");
                            System.out.println("HOREFNO" + HOREFNO);
                        }

                        if (rs.getDate("REF_DATE") == null)
                            OffLetterDate = "";
                        else {
                            java.sql.Date dd = rs.getDate("REF_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            OffLetterDate = sdf.format(dd);
                            System.out.println("date1 is" + OffLetterDate);
                        }
                        if (rs.getDate("HO_REF_DATE") == null)
                            LetterDate = "";
                        else {
                            java.sql.Date dd = rs.getDate("HO_REF_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            LetterDate = sdf.format(dd);
                            System.out.println("date1 is" + LetterDate);
                        }
                        if (rs.getDate("CHEQUE_DD_DATE") == null)
                            CheqDate = "";
                        else {
                            java.sql.Date dd = rs.getDate("CHEQUE_DD_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            CheqDate = sdf.format(dd);
                            System.out.println("cheqdate2 is" + CheqDate);
                        }

                        fundtype = rs.getString("FUND_TYPE");
                        if (fundtype.equalsIgnoreCase("C")) {
                            fundtype = "Civil";
                        } else {
                            fundtype = "Work";
                        }

                        System.out.println("the letter number is" + LetterNo);
                        if (LetterNo.equals(null)) {
                            LetterNo = "-";
                        } else {
                            LetterNo = rs.getString("HO_REF_NO");
                            System.out.println("LetterNo" + LetterNo);
                        }


                        // System.out.println("the letter number is"+OffLetterNo);
                        if (OffLetterNo.equals(null)) {
                            OffLetterNo = "-";
                        } else {
                            OffLetterNo = rs.getString("REF_NO");
                            System.out.println("OffLetterNo" + OffLetterNo);
                        }


                        if (reason.equals(null)) {
                            reason = " ";
                        } else {
                            reason = rs.getString("REASON");
                            System.out.println("reason" + reason);
                        }
                        if (CheqorDD.equals(null)) {
                            CheqorDD = " ";
                        } else {
                            CheqorDD = rs.getString("CHEQUE_OR_DD");
                            System.out.println("CheqorDD" + CheqorDD);
                        }
                        CheqNo = rs.getInt("CHEQUE_DD_NO");
                        tranOffice = rs.getInt("OFFICE_ID");
                        System.out.println("transofficeid" + tranOffice);
                        tranOfficeName = rs.getString("office_name");
                        transAmt = rs.getDouble("AMOUNT");
                        fundreq = rs.getDouble("FUND_REQUESTED");
                        if (fundtypeid.equals(null)) {
                            fundtypeid = "";
                        } else {
                            fundtypeid = rs.getString("FUND_TYPE");
                            System.out.println("fundtypeid" + fundtypeid);
                        }

                        if (letgen.equals(null)) {
                            letgen = "";
                        } else {
                            letgen = rs.getString("LETTER_GEN");
                            System.out.println("letgen" + letgen);
                        }


                    } catch (Exception e) {
                        System.out.println("Error in getting date values" + e);

                    }

                    xml = xml + "<slno>" + slno + "</slno>";
                    xml = xml + "<voucherno>" + voucherno + "</voucherno>";
                    xml = xml + "<tranOffice>" + tranOffice + "</tranOffice>";
                    xml =
 xml + "<tranOfficeName>" + tranOfficeName + "</tranOfficeName>";
                    xml = xml + "<transAmt>" + transAmt + "</transAmt>";
                    xml = xml + "<fundtype>" + fundtype + "</fundtype>";
                    xml = xml + "<fundtypeid>" + fundtypeid + "</fundtypeid>";
                    xml = xml + "<remarks>" + remarks + "</remarks>";
                    xml = xml + "<HOREFDATE>" + HOREFDATE + "</HOREFDATE>";
                    xml = xml + "<HOREFNO>" + HOREFNO + "</HOREFNO>";
                    xml =
 xml + "<OffLetterDate>" + OffLetterDate + "</OffLetterDate>";
                    xml = xml + "<LetterDate>" + LetterDate + "</LetterDate>";

                    xml = xml + "<LetterNo>" + LetterNo + "</LetterNo>";
                    xml =
 xml + "<OffLetterNo>" + OffLetterNo + "</OffLetterNo>";
                    xml = xml + "<reason>" + reason + "</reason>";
                    xml = xml + "<CheqorDD>" + CheqorDD + "</CheqorDD>";

                    xml = xml + "<fundreq>" + fundreq + "</fundreq>";
                    //  cnt++;
                    System.out.println("OffLetterNo" + OffLetterNo);
                    if (cnt > 0)
                        xml = xml + "<flag>success</flag>";


                }

                xml = xml + "<flag>failure</flag>";
                System.out.println("count  " + cnt);
            } catch (Exception e) {
                System.out.println("Exception in grid.." + e);
            }

            xml = xml + "</response>";
            out.println(xml);
        }

    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        System.out.println("inside fundsubmit");
        String strCommand = "";
        Connection con = null;
        ResultSet rs = null;
        ResultSet res1 = null;
        ResultSet rs3 = null;
        ResultSet rs2 = null;
        ResultSet rs4 = null;
        PreparedStatement ps = null;
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
            sendMessage(response,
                        "probably Failed to Establish connection to the database server.. due to " +
                        e, "ok");

        }

        out.println("<html>");
        out.println("<head><title>FundAllotmentSubmitServ</title></head>");
        out.println("<body>");
        String accunitid = request.getParameter("cmbAcc_UnitCode");

        String officeid = request.getParameter("cmbOffice_code");

        String cashbookyear = request.getParameter("txtCB_Year");

        String cashbookmonth = request.getParameter("txtCB_Month");


        int year = Integer.parseInt(cashbookyear);
        int month = Integer.parseInt(cashbookmonth);
        System.out.println("Month:========" + month);
        int accunit = Integer.parseInt(accunitid);
        System.out.println(accunit);
        int office_id = Integer.parseInt(officeid);
        System.out.println(office_id);
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        int voucherno = 0;

        try {

            int selrow = 0;
            String sel[] = request.getParameterValues("sel");
            int selnumber[] = new int[sel.length];
            int vno = 0, sno = 0, offno = 0;

            for (int i = 0; i < sel.length; i++) {
                selnumber[i] = Integer.parseInt(sel[i]);
                System.out.println("selected rows is-------" + selnumber[i]);
                int x = selnumber[i];
                System.out.println("vno_hid" + x);
                String vnos[] = request.getParameterValues("vno_hid" + x);
                int vonumber[] = new int[vnos.length];
                System.out.println(vnos.length);
                vonumber[0] = Integer.parseInt(vnos[0]);
                System.out.println("voucher new is-------" + vonumber[0]);
                vno = vonumber[0];
                //----------------
                System.out.println("sl_no_hid" + x);
                String snos[] = request.getParameterValues("sl_no_hid" + x);
                int sonumber[] = new int[snos.length];
                System.out.println(snos.length);
                sonumber[0] = Integer.parseInt(snos[0]);
                System.out.println("sl new is-------" + sonumber[0]);
                sno = sonumber[0];
                //------------
                System.out.println("trans_off_hid" + x);
                String offnos[] =
                    request.getParameterValues("trans_off_hid" + x);
                int offnumber[] = new int[offnos.length];
                System.out.println(offnos.length);
                offnumber[0] = Integer.parseInt(offnos[0]);
                System.out.println("tranfered office new is-------" +
                                   offnumber[0]);
                offno = offnumber[0];


                System.out.println("comes inside flag else part");

                String ltrstatus = "";
                int dname1 = 0;
                int vnumber = 0;
                int snumber = 0;
                //update FUND_ALLOTMENT_TRANSACTION set SL_NO=?,REF_NO=?,REF_DATE=?,FUND_TYPE=?,AMOUNT=?,PARTICULARS=?,FUND_REQUESTED=?,voucher_no=?,updated_by_user_id=?,updated_date=?,CHEQUE_OR_DD=?,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=?,HO_REF_NO=?,HO_REF_DATE=?,REASON =? where ACCOUNTING_UNIT_ID=? and office_id=? and CASHBOOK_YEAR=? and cashbook_month=? and LETTER_GEN='N' ");
                String sql_update =
                    "update FUND_ALLOTMENT_TRANSACTION set LETTER_GEN=?,UPDATED_DATE=?, UPDATED_BY_USER_ID=? where LETTER_GEN='N' and ACCOUNTING_UNIT_ID=? and OFFICE_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and VOUCHER_NO=? and SL_NO=? ";


                ps2 = con.prepareStatement(sql_update);
                ps2.setString(1, "Y");
                ps2.setTimestamp(2, ts);
                ps2.setString(3, update_user);
                ps2.setInt(4, accunit);
                ps2.setInt(5, offno);
                ps2.setInt(6, month);
                ps2.setInt(7, year);
                ps2.setInt(8, vno);
                ps2.setInt(9, sno);
                ps2.executeUpdate();


                System.out.println("update FUND_ALLOTMENT_TRANSACTION set LETTER_GEN='Y' where ACCOUNTING_UNIT_ID=" +
                                   accunit + " and OFFICE_ID=" + offno +
                                   " and CASHBOOK_MONTH=" + month +
                                   " and CASHBOOK_YEAR=" + year +
                                   " and VOUCHER_NO=" + vno + " and SL_NO=" +
                                   sno + " ");
                sno = 0;
                vno = 0;
                offno = 0;


                ps2.close();
                System.out.println("last");

                con.commit();
                sendMessage(response, "The fundallotment updated successfully",
                            "ok");
            }
        }


        catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException sqle) {
                System.out.println("Excep" + sqle);
            }

            System.out.println("Exception occur due to " + e);
        }

        finally {
            System.out.println("done here");
            try {
                con.setAutoCommit(true);
            } catch (SQLException sqle) {
                System.out.println("Excep" + sqle);
            }
        }
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

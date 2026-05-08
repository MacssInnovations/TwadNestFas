package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.ResourceBundle;

public class OpeningBalServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement pss1 = null;
        PreparedStatement pss = null;

        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        String userid = (String)session.getAttribute("UserId");


        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        int AccId = 0;
        int OffCode = 0;
        String FinanYr = "";
        int cmbAcHeadCode = 0;
        double Credit = 0;
        double Debit = 0;
        String OpenBalInd = "";
        double CloseBal = 0;
        java.sql.Date DrLastUpdateDate = null;
        java.sql.Date CrLastUpdateDate = null;
        int CashbookYear = 0;
        int CashbookMonth = 0;

        try {
            System.out.println("................Start in the servlet...........");
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }
            System.out.println("cmbAcHeadCode :: " + cmbAcHeadCode);
        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        try {
            cmbAcHeadCode =
                    Integer.parseInt(request.getParameter("cmbAcHeadCode"));
        } catch (Exception e) {
            System.out.println("Exception in checkcode");
        }

        int cnt = 0;
        try {
            AccId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception que) {
            System.out.println("Exception in assigning values in add command...." +
                               que);
        }
        System.out.println(AccId);

        try {
            OffCode = Integer.parseInt(request.getParameter("comOffCode"));
            System.out.println(OffCode);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in add command...." +
                               que);
        }

        try {
            CashbookYear =
                    Integer.parseInt(request.getParameter("CashbookYear"));
            System.out.println(CashbookYear);

        } catch (Exception que) {
            System.out.println("Exception in assigning values in CashbookYear...." +
                               que);
        }
        try {
            CashbookMonth =
                    Integer.parseInt(request.getParameter("CashbookMonth"));
            System.out.println(CashbookMonth);

        } catch (Exception que) {
            System.out.println("Exception in assigning values in CashbookMonth...." +
                               que);
        }


        FinanYr = request.getParameter("txtFinanYr");
        System.out.println(FinanYr);

        if (strCommand.equalsIgnoreCase("fetchingValues")) {
            xml = "<response><command>fetchingValues</command>";
            try {
                ps =
  con.prepareStatement("select account_head_desc from com_mst_account_heads where account_head_code=?");
                ps.setInt(1, cmbAcHeadCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<acc_desc>" + rs.getString("account_head_desc") + "</acc_desc>";
                    xml = xml + "<flag>success</flag>";
                    ps1 =
 con.prepareStatement("select trim(to_char(UPTO_DEBIT_BALANCE,'99999999999999.99')) as UPTO_DEBIT_BALANCE,trim(to_char(UPTO_CREDIT_BALANCE,'99999999999999.99')) as UPTO_CREDIT_BALANCE,to_char(DR_UPDATE_LAST_DATE,'dd-mm-yyyy') as DR_UPDATE_LAST_DATE,to_char(CR_UPDATE_LAST_DATE,'dd/mm/yyyy') as CR_UPDATE_LAST_DATE from FAS_GL_UPTO_DATA where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and year=? and month=?");
                    ps1.setInt(1, cmbAcHeadCode);
                    ps1.setInt(2, AccId);
                    ps1.setInt(3, OffCode);
                    ps1.setString(4, FinanYr);
                    ps1.setInt(5, CashbookYear);
                    ps1.setInt(6, CashbookMonth);
                    rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        xml =
 xml + "<UPTO_DEBIT_BALANCE>" + rs1.getString("UPTO_DEBIT_BALANCE") +
   "</UPTO_DEBIT_BALANCE>";
                        xml =
 xml + "<UPTO_CREDIT_BALANCE>" + rs1.getString("UPTO_CREDIT_BALANCE") +
   "</UPTO_CREDIT_BALANCE>";
                        if (rs1.getString("DR_UPDATE_LAST_DATE") == null)
                            xml =
 xml + "<DR_UPDATE_LAST_DATE>-</DR_UPDATE_LAST_DATE>";
                        else
                            xml =
 xml + "<DR_UPDATE_LAST_DATE>" + rs1.getString("DR_UPDATE_LAST_DATE") +
   "</DR_UPDATE_LAST_DATE>";

                        if (rs1.getString("CR_UPDATE_LAST_DATE") == null)
                            xml =
 xml + "<CR_UPDATE_LAST_DATE>-</CR_UPDATE_LAST_DATE>";
                        else
                            xml =
 xml + "<CR_UPDATE_LAST_DATE>" + rs1.getString("CR_UPDATE_LAST_DATE") +
   "</CR_UPDATE_LAST_DATE>";
                        xml = xml + "<exist>yes</exist>";

                    } else
                        xml = xml + "<exist>no</exist>";
                } else
                    xml = xml + "<flag>failure</acc_desc>";
            } catch (Exception e) {
                System.out.println("Err in fetchingValues :: " +
                                   e.getMessage());
            }
            xml = xml + "</response>";
            System.out.println("xml is:" + xml);
            out.write(xml);
            out.flush();
            out.close();
        }

        else if (strCommand.equalsIgnoreCase("Add")) {

            xml = "<response><command>Add</command>";

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            OpenBalInd = request.getParameter("OpenBalInd");
            System.out.println("OpenBalInd is:" + OpenBalInd);


            try {
                Credit = Double.parseDouble(request.getParameter("txtCredit"));
                System.out.println(Credit);

            } catch (Exception ex) {
                System.out.println("exception in assigning credit details...in add.." +
                                   ex);
            }


            try {
                Debit = Double.parseDouble(request.getParameter("txtDebit"));
                System.out.println(Debit);
            } catch (Exception ex1) {
                System.out.println("exception in assigning debit details..in add.." +
                                   ex1);
            }


            try {
                //Date Conversion for Date
                String fromdate = request.getParameter("DrLastUpdateDate");
                System.out.println("before converting date");
                String dateString = fromdate;
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d;
                d = dateFormat.parse(fromdate.trim());
                System.out.println("util date is:" + d);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                DrLastUpdateDate = java.sql.Date.valueOf(dateString);


                System.out.println("before converting date");
                String todate = request.getParameter("CrLastUpdateDate");
                String dateString1 = todate;
                SimpleDateFormat dateFormat1 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d1;
                d1 = dateFormat1.parse(todate.trim());
                dateFormat1.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat1.format(d1);
                CrLastUpdateDate = java.sql.Date.valueOf(dateString1);
            } catch (Exception e) {
                System.out.println("Exception in Date Conversion:" + e);
            }
            System.out.println("fromdate" + DrLastUpdateDate);
            System.out.println("todate" + CrLastUpdateDate);

            //End Change Date 29-nov-2006
            try {
                pss1 =
con.prepareStatement("insert into FAS_GL_UPTO_DATA(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH,ACCOUNT_HEAD_CODE,UPTO_CREDIT_BALANCE,UPTO_DEBIT_BALANCE,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?)");
                pss1.setInt(1, AccId);
                pss1.setInt(2, OffCode);
                pss1.setString(3, FinanYr);
                pss1.setInt(4, CashbookYear);
                pss1.setInt(5, CashbookMonth);
                pss1.setInt(6, cmbAcHeadCode);
                pss1.setDouble(7, Credit);
                pss1.setDouble(8, Debit);
                pss1.setDate(9, DrLastUpdateDate);
                pss1.setDate(10, CrLastUpdateDate);
                pss1.setString(11, userid);
                pss1.setTimestamp(12, ts);
                pss1.executeUpdate();

                xml = xml + "<flag>success</flag>";
            } catch (Exception ex4) {
                System.out.println("Exception in add......" + ex4);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {


            xml = "<response><command>Update</command>";

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            OpenBalInd = request.getParameter("OpenBalInd");
            System.out.println("OpenBalInd is:" + OpenBalInd);


            try {
                Credit = Double.parseDouble(request.getParameter("txtCredit"));
                System.out.println(Credit);
            } catch (Exception ex) {
                System.out.println("exception in assigning credit details...in add.." +
                                   ex);
            }

            try {
                Debit = Double.parseDouble(request.getParameter("txtDebit"));
                System.out.println(Debit);
            } catch (Exception ex1) {
                System.out.println("exception in assigning debit details..in add.." +
                                   ex1);
            }


            try {
                //Date Conversion for Date
                String fromdate = request.getParameter("DrLastUpdateDate");
                System.out.println("before converting date");
                String dateString = fromdate;
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d;
                d = dateFormat.parse(fromdate.trim());
                System.out.println("util date is:" + d);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                DrLastUpdateDate = java.sql.Date.valueOf(dateString);


                System.out.println("before converting date");
                String todate = request.getParameter("CrLastUpdateDate");
                String dateString1 = todate;
                SimpleDateFormat dateFormat1 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d1;
                d1 = dateFormat1.parse(todate.trim());
                dateFormat1.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat1.format(d1);
                CrLastUpdateDate = java.sql.Date.valueOf(dateString1);
            } catch (Exception e) {
                System.out.println("Exception in Date Conversion:" + e);
            }
            System.out.println("fromdate" + DrLastUpdateDate);
            System.out.println("todate" + CrLastUpdateDate);

            //End Change Date 30-nov-2006 B


            try {
                pss1 =
con.prepareStatement("update FAS_GL_UPTO_DATA set UPTO_CREDIT_BALANCE=?,UPTO_DEBIT_BALANCE=?,DR_UPDATE_LAST_DATE=?,CR_UPDATE_LAST_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and YEAR=? and MONTH=? and ACCOUNT_HEAD_CODE=?");
                System.out.println("Credit :: " + Credit);
                pss1.setDouble(1, Credit);
                System.out.println("Credit :: " + Credit);
                pss1.setDouble(2, Debit);
                System.out.println("Credit :: " + Credit);
                pss1.setDate(3, DrLastUpdateDate);
                System.out.println("Credit :: " + Credit);
                pss1.setDate(4, CrLastUpdateDate);
                System.out.println("Credit :: " + Credit);
                pss1.setString(5, userid);
                System.out.println("Credit :: " + Credit);
                pss1.setTimestamp(6, ts);
                System.out.println("Credit :: " + Credit);
                pss1.setInt(7, AccId);
                System.out.println("Credit :: " + Credit);
                pss1.setInt(8, OffCode);
                System.out.println("Credit :: " + Credit);
                pss1.setString(9, FinanYr);
                System.out.println("Credit :: " + Credit);
                pss1.setInt(10, CashbookYear);
                System.out.println("Credit :: " + Credit);
                pss1.setInt(11, CashbookMonth);
                System.out.println("Credit :: " + Credit);
                pss1.setInt(12, cmbAcHeadCode);
                System.out.println("Credit :: " + Credit);
                pss1.executeUpdate();

                xml = xml + "<flag>success</flag>";
                pss1.close();
            }

            catch (Exception ex4) {
                System.out.println("Exception in update......" + ex4);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Delete")) {

            xml = "<response><command>Delete</command>";


            try {
                pss =
 con.prepareStatement("delete from FAS_GL_UPTO_DATA where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and YEAR=? and MONTH=? and ACCOUNT_HEAD_CODE=?");

                pss.setInt(1, AccId);
                System.out.println("AccId :: " + AccId);
                pss.setInt(2, OffCode);
                System.out.println("OffCode :: " + OffCode);
                pss.setString(3, FinanYr);
                System.out.println("FinanYr :: " + FinanYr);
                pss.setInt(4, CashbookYear);
                System.out.println("CashbookYear :: " + CashbookYear);
                pss.setInt(5, CashbookMonth);
                System.out.println("CashbookMonth :: " + CashbookMonth);
                pss.setInt(6, cmbAcHeadCode);
                System.out.println("cmbAcHeadCode :: " + cmbAcHeadCode);
                pss.executeUpdate();
                xml = xml + "<flag>success</flag>";
                pss.close();
            } catch (Exception ex2) {
                System.out.println("exception in add" + ex2);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}


package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class OpeningBalListServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection con = null;


        ResultSet rss = null;
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


        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;


        int acOffId = 0;
        int AccId = 0;
        int OffCode = 0;
        String FinanYr = "";
        String finanYR = "";
        String UptoCR = "";
        String UptoDB = "";
        String currCR = "";
        String currDB = "";
        String MONTH_OPENING_BALANCE = "";
        String MONTH_OPENING_BAL_DR_CR_IND = "";
        String CURRENT_MONTH_DEBIT = "";
        String CURRENT_MONTH_CREDIT = "";
        String MONTH_CLOSING_BALANCE = "";
        String MONTH_CLOSING_BAL_DR_CR_IND = "";
        String DateToBeDisplayed = "";
        String DateToBeDisplayed1 = "";


        int Major_id = 0;

        int Minor_id = 0;
        int sub1_id = 0;
        int sub2_id = 0;
        int CashbookYear = 0;
        int CashbookMonth = 0;
        int straccountHeadCode = 0;
        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


        try {
            System.out.println("..........................servlet opening balnacelist started.............");
            strCommand = request.getParameter("command");
            System.out.println("assign....." + strCommand);

        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        try {
            try {
                straccountHeadCode =
                        Integer.parseInt(request.getParameter("accountHeadcode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }
        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        try {
            AccId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception que) {
            System.out.println("Exception in assigning values in add command...." +
                               que);
        }


        try {
            OffCode = Integer.parseInt(request.getParameter("comOffCode"));
            System.out.println(OffCode);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in add command...." +
                               que);
        }

        //Change Date 30-Nov-2006 B//
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

        //End Change Date 30-Nov-2006 B//


        FinanYr = request.getParameter("txtFinanYr");
        System.out.println("values list unit ,office, year");
        System.out.println(AccId);
        System.out.println(OffCode);
        System.out.println(FinanYr);

        if (strCommand.equalsIgnoreCase("fetch")) {
            xml = "<response><command>fetch</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            System.out.println("inside fetch command");
            try {
                pss =
 con.prepareStatement("select ACCOUNTING_UNIT_ID ,ACCOUNTING_FOR_OFFICE_ID ,FINANCIAL_YEAR ,ACCOUNT_HEAD_CODE,UPTO_CREDIT_BALANCE,UPTO_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,CURRENT_YEAR_DEBIT_BALANCE,MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE  from fas_general_ledger where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and year=? and month=? ");
                pss.setInt(1, straccountHeadCode);
                pss.setInt(2, AccId);
                pss.setInt(3, OffCode);
                pss.setString(4, FinanYr);
                pss.setInt(5, CashbookYear);
                pss.setInt(6, CashbookMonth);
                rss = pss.executeQuery();
                if (rss.next()) {
                    acOffId = rss.getInt("ACCOUNTING_FOR_OFFICE_ID");
                    System.out.println("Asset type code is*****************" +
                                       acOffId);

                    straccountHeadCode = rss.getInt("ACCOUNT_HEAD_CODE");

                    finanYR = rss.getString("FINANCIAL_YEAR");
                    System.out.println("Asset type code is*****************" +
                                       finanYR);

                    UptoCR = rss.getString("UPTO_CREDIT_BALANCE").trim();
                    System.out.println("Asset type code is*****************" +
                                       UptoCR);

                    UptoDB = rss.getString("UPTO_DEBIT_BALANCE").trim();
                    System.out.println("Asset type code is*****************" +
                                       UptoDB);

                    currCR =
                            rss.getString("CURRENT_YEAR_CREDIT_BALANCE").trim();
                    System.out.println("Asset type code is*****************" +
                                       currCR);

                    currDB =
                            rss.getString("CURRENT_YEAR_DEBIT_BALANCE").trim();
                    System.out.println("Asset type code is*****************" +
                                       currDB);

                    //Change Date 30-Nov-2006 B//
                    MONTH_OPENING_BALANCE =
                            rss.getString("MONTH_OPENING_BALANCE").trim();
                    System.out.println("OpenBal:" + MONTH_OPENING_BALANCE);
                    MONTH_OPENING_BAL_DR_CR_IND =
                            rss.getString("MONTH_OPENING_BAL_DR_CR_IND").trim();
                    System.out.println("OpenBalInd:" +
                                       MONTH_OPENING_BAL_DR_CR_IND);
                    CURRENT_MONTH_DEBIT =
                            rss.getString("CURRENT_MONTH_DEBIT").trim();
                    System.out.println("CurrDebit:" + CURRENT_MONTH_DEBIT);
                    CURRENT_MONTH_CREDIT =
                            rss.getString("CURRENT_MONTH_CREDIT").trim();
                    System.out.println("CurrCredit:" + CURRENT_MONTH_CREDIT);
                    MONTH_CLOSING_BALANCE =
                            rss.getString("MONTH_CLOSING_BALANCE").trim();
                    System.out.println("CloseBal:" + MONTH_CLOSING_BALANCE);
                    MONTH_CLOSING_BAL_DR_CR_IND =
                            rss.getString("MONTH_CLOSING_BAL_DR_CR_IND").trim();
                    System.out.println("CloseBalInd:" +
                                       MONTH_CLOSING_BAL_DR_CR_IND);
                    //DR_UPDATE_LAST_DATE=rs.getString("DR_UPDATE_LAST_DATE").trim();
                    //CR_UPDATE_LAST_DATE=rs.getString("CR_UPDATE_LAST_DATE").trim();


                    java.sql.Date DR_UPDATE_LAST_DATE =
                        rss.getDate("DR_UPDATE_LAST_DATE");

                    if (DR_UPDATE_LAST_DATE == null) {
                        DateToBeDisplayed = "Not Specified";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            DateToBeDisplayed =
                                    sdf.format(DR_UPDATE_LAST_DATE);
                        } catch (Exception e) {
                            System.out.println("error while formatting date : " +
                                               e);
                            DateToBeDisplayed = "Not Specified";
                        }
                    }

                    java.sql.Date CR_UPDATE_LAST_DATE =
                        rss.getDate("DR_UPDATE_LAST_DATE");

                    if (CR_UPDATE_LAST_DATE == null) {
                        DateToBeDisplayed1 = "Not Specified";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            DateToBeDisplayed1 =
                                    sdf.format(CR_UPDATE_LAST_DATE);
                        } catch (Exception e) {
                            System.out.println("error while formatting date : " +
                                               e);
                            DateToBeDisplayed1 = "Not Specified";
                        }
                    }

                    //End Chage Date 30-Nov-2006 B//

                }
                xml =
 xml + "<flag>success</flag><straccountHeadCode>" + straccountHeadCode +
   "</straccountHeadCode><finanYR>" + finanYR + "</finanYR><UptoCR>" + UptoCR +
   "</UptoCR><UptoDB>" + UptoDB + "</UptoDB><currCR>" + currCR +
   "</currCR><currDB>" + currDB + "</currDB><acOffId>" + acOffId +
   "</acOffId><OpenBal>" + MONTH_OPENING_BALANCE + "</OpenBal><OpenBalInd>" +
   MONTH_OPENING_BAL_DR_CR_IND + "</OpenBalInd><CurrDebit>" +
   CURRENT_MONTH_DEBIT + "</CurrDebit><CurrCredit>" + CURRENT_MONTH_CREDIT +
   "</CurrCredit><CloseBal>" + MONTH_CLOSING_BALANCE +
   "</CloseBal><CloseBalInd>" + MONTH_CLOSING_BAL_DR_CR_IND +
   "</CloseBalInd><DrUpdateDate>" + DateToBeDisplayed1 +
   "</DrUpdateDate><CrUpdateDate>" + DateToBeDisplayed + "</CrUpdateDate>";

                String majorcode = "";
                String majorDesc = "";
                String minorcode = "";
                String minordesc = "";
                String subcode1 = "";
                String subdesc1 = "";
                String subcode2 = "";
                String subdesc2 = "";

                ps =
  con.prepareStatement("select MAJOR_HEAD_CODE,MINOR_HEAD_CODE,SUB_HEAD1_CODE,SUB_HEAD2_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, straccountHeadCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    majorcode = rs.getString("MAJOR_HEAD_CODE");
                    minorcode = rs.getString("MINOR_HEAD_CODE");
                    subcode1 = rs.getString("SUB_HEAD1_CODE");
                    subcode2 = rs.getString("SUB_HEAD2_CODE");

                    ps1 =
 con.prepareStatement("select * from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
                    ps1.setString(1, majorcode);
                    rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        majorDesc = rs1.getString("MAJOR_HEAD_DESC");
                        majorcode = rs1.getString("MAJOR_HEAD_CODE");
                        xml =
 xml + "<majorcode>" + majorcode + "</majorcode><majorDesc>" + majorDesc +
   "</majorDesc>";
                        rs1.close();
                        ps1.close();
                    }

                    ps1 =
 con.prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MINOR_HEAD_CODE=?");
                    ps1.setString(1, minorcode);
                    rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        minorcode = rs1.getString("MINOR_HEAD_CODE");
                        minordesc = rs1.getString("MINOR_HEAD_DESC");
                        xml =
 xml + "<flag>success</flag><minorcode>" + minorcode +
   "</minorcode><minordesc>" + minordesc + "</minordesc>";
                        rs1.close();
                        ps1.close();
                    }

                    ps1 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                    ps1.setString(1, subcode1);
                    rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        subcode1 = rs1.getString("SUB_HEAD_CODE");
                        subdesc1 = rs1.getString("SUB_HEAD_DESC");
                        xml =
 xml + "<subcode1>" + subcode1 + "</subcode1><subdesc1>" + subdesc1 +
   "</subdesc1>";
                        rs1.close();
                        ps1.close();
                    } else
                        xml =
 xml + "<subcode1>" + null + "</subcode1><subdesc1>" + null + "</subdesc1>";


                    ps1 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                    ps1.setString(1, subcode2);
                    rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        subcode2 = rs1.getString("SUB_HEAD_CODE");
                        subdesc2 = rs1.getString("SUB_HEAD_DESC");
                        xml =
 xml + "<subcode2>" + subcode2 + "</subcode2><subdesc2>" + subdesc2 +
   "</subdesc2>";
                    } else
                        xml =
 xml + "<subcode2>" + null + "</subcode2><subdesc2>" + null + "</subdesc2>";


                    rs.close();
                    ps.close();
                }


            } catch (Exception que) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception in fetching fas_opening balance details");
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}


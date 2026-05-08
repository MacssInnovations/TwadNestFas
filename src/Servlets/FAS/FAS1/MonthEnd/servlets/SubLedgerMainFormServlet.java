package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class SubLedgerMainFormServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }


        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        String userid = (String)session.getAttribute("UserId");
        System.out.println(userid);
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement pss1 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet res = null;
        ResultSet res1 = null;
        ResultSet res2 = null;

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


        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String Office_Code = request.getParameter("comOffCode");
        String CashBook_Year = request.getParameter("CashbookYear");
        String CashBook_Month = request.getParameter("CashbookMonth");
        String Mas_SL_type = request.getParameter("cmbSL_type");
        String cmbMas_SL_Code = request.getParameter("cmbMas_SL_Code");

        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("Office_Code is:" + Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);
        System.out.println("Mas_SL_type is:" + Mas_SL_type);
        System.out.println("cmbMas_SL_Code is:" + cmbMas_SL_Code);
        String strCommand = "";
        String sxml = "";
        String xml = "";
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int cmbMas_SL_type = 0;
        int Mas_SL_Code = 0;
        int cmbAcHeadCode = 0;
        double Credit = 0;
        double Debit = 0;
        double YrCredit = 0;
        double YrDebit = 0;
        double OpenBal = 0;
        String OpenBalInd = "";
        double CurrDebit = 0;
        double CurrCredit = 0;
        double CloseBal = 0;
        String CloseBalInd = "";
        java.sql.Date DrLastUpdateDate = null;
        java.sql.Date CrLastUpdateDate = null;
        int CashbookYear = 0;
        int CashbookMonth = 0;
        int AccId = 0;
        int OffCode = 0;
        String FinanYr = "";
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
            cmbMas_SL_type = Integer.parseInt(Mas_SL_type);

            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + CashBookYear);
            System.out.println("CashBook Month After is:" + CashBookMonth);
            System.out.println("cmbMas_SL_type is:" + cmbMas_SL_type);
            ;

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }
        try {
            Mas_SL_Code = Integer.parseInt(cmbMas_SL_Code);

        } catch (Exception e) {
            System.out.println("Exception in SlCode:" + e);
        }
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

        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Add</command>";

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            OpenBalInd = request.getParameter("OpenBalInd");
            System.out.println("OpenBalInd is:" + OpenBalInd);
            CloseBalInd = request.getParameter("CloseBalInd");
            System.out.println("CloseBalInd is:" + CloseBalInd);
            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
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

            FinanYr = request.getParameter("txtFinanYr");
            System.out.println(FinanYr);

            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }

            //                 credit / debit details
            try {
                Credit = Double.parseDouble(request.getParameter("txtCredit"));
                System.out.println(Credit);

            } catch (Exception ex) {
                System.out.println("exception in assigning credit details...in add.." +
                                   ex);
            }


            try {

                YrCredit =
                        Double.parseDouble(request.getParameter("txtYrCredit"));
                System.out.println(YrCredit);
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
                YrDebit =
                        Double.parseDouble(request.getParameter("txtYrDebit"));
                System.out.println(YrDebit);
            } catch (Exception ex1) {
                System.out.println("exception in assigning debit details..in add.." +
                                   ex1);
            }

            try {

                OpenBal = Double.parseDouble(request.getParameter("OpenBal"));
                System.out.println("OpenBal is:" + OpenBal);

            } catch (Exception ex1) {
                System.out.println("Exception in Open Bal:" + ex1);
            }
            try {

                CurrDebit =
                        Double.parseDouble(request.getParameter("CurMonDebit"));
                System.out.println("CurMonDebit is:" + CurrDebit);

            } catch (Exception ex1) {
                System.out.println("Exception in CurrDebit:" + ex1);
            }
            try {

                CurrCredit =
                        Double.parseDouble(request.getParameter("CurMonCredit"));
                System.out.println("CurMonCredit is:" + CurrCredit);

            } catch (Exception ex1) {
                System.out.println("Exception in CurMonCredit:" + ex1);
            }
            try {

                CloseBal =
                        Double.parseDouble(request.getParameter("CloseBal"));
                System.out.println("CloseBal is:" + CloseBal);

            } catch (Exception ex1) {
                System.out.println("Exception in CloseBal:" + ex1);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
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


            try {
                System.out.println("inner try b4");

                ps1 =
 con.prepareStatement("select account_head_code from FAS_SUB_LEDGER_MASTER where accounting_unit_id=? " +
                      " and account_head_code=? and year=? and month=? and accounting_for_office_id=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?");
                ps1.setInt(1, AccId);
                ps1.setInt(2, cmbAcHeadCode);
                ps1.setInt(3, CashbookYear);
                ps1.setInt(4, CashbookMonth);
                ps1.setInt(5, OffCode);
                ps1.setInt(6, cmbMas_SL_type);
                ps1.setInt(7, Mas_SL_Code);
                res = ps1.executeQuery();
                if (!res.next()) {
                    pss1 =
con.prepareStatement("insert into FAS_SUB_LEDGER_MASTER(ACCOUNT_HEAD_CODE,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,UPDATED_BY_USER_ID,CURRENT_YEAR_CREDIT_BALANCE,CURRENT_YEAR_DEBIT_BALANCE,MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND,UPDATED_DATE,year,month,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    pss1.setInt(1, cmbAcHeadCode);
                    pss1.setInt(2, AccId);
                    pss1.setInt(3, OffCode);
                    pss1.setString(4, FinanYr);
                    pss1.setString(5, userid);
                    pss1.setDouble(6, YrCredit);
                    pss1.setDouble(7, YrDebit);
                    pss1.setDouble(8, OpenBal);
                    pss1.setString(9, OpenBalInd);
                    pss1.setTimestamp(10, ts);
                    /*pss1.setDouble(13,CurrDebit);
                        pss1.setDouble(14,CurrCredit);
                        pss1.setDouble(15,CloseBal);
                        pss1.setString(16,CloseBalInd);
                        pss1.setDate(17,DrLastUpdateDate);
                        pss1.setDate(18,CrLastUpdateDate);*/
                    pss1.setInt(11, CashbookYear);
                    pss1.setInt(12, CashbookMonth);
                    pss1.setInt(13, cmbMas_SL_type);
                    pss1.setInt(14, Mas_SL_Code);
                    System.out.println("inner try b41");
                    pss1.executeUpdate();
                    System.out.println("inner try b42");

                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>AlreadyExist</flag>";
                }
            } catch (Exception ex4) {
                System.out.println("Exception in add......" + ex4);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Update")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Update</command>";

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            OpenBalInd = request.getParameter("OpenBalInd");
            System.out.println("OpenBalInd is:" + OpenBalInd);
            CloseBalInd = request.getParameter("CloseBalInd");
            System.out.println("CloseBalInd is:" + CloseBalInd);
            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
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

            FinanYr = request.getParameter("txtFinanYr");
            System.out.println(FinanYr);

            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }

            //                 credit / debit details
            try {
                Credit = Double.parseDouble(request.getParameter("txtCredit"));
                System.out.println(Credit);

            } catch (Exception ex) {
                System.out.println("exception in assigning credit details...in add.." +
                                   ex);
            }


            try {

                YrCredit =
                        Double.parseDouble(request.getParameter("txtYrCredit"));
                System.out.println(YrCredit);
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
                YrDebit =
                        Double.parseDouble(request.getParameter("txtYrDebit"));
                System.out.println(YrDebit);
            } catch (Exception ex1) {
                System.out.println("exception in assigning debit details..in add.." +
                                   ex1);
            }

            //Change Date 30-nov-2006 B
            try {

                OpenBal = Double.parseDouble(request.getParameter("OpenBal"));
                System.out.println("OpenBal is:" + OpenBal);

            } catch (Exception ex1) {
                System.out.println("Exception in Open Bal:" + ex1);
            }
            try {

                CurrDebit =
                        Double.parseDouble(request.getParameter("CurMonDebit"));
                System.out.println("CurMonDebit is:" + CurrDebit);

            } catch (Exception ex1) {
                System.out.println("Exception in CurrDebit:" + ex1);
            }
            try {

                CurrCredit =
                        Double.parseDouble(request.getParameter("CurMonCredit"));
                System.out.println("CurMonCredit is:" + CurrCredit);

            } catch (Exception ex1) {
                System.out.println("Exception in CurMonCredit:" + ex1);
            }
            try {

                CloseBal =
                        Double.parseDouble(request.getParameter("CloseBal"));
                System.out.println("CloseBal is:" + CloseBal);

            } catch (Exception ex1) {
                System.out.println("Exception in CloseBal:" + ex1);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
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
con.prepareStatement("update FAS_SUB_LEDGER_MASTER set UPDATED_BY_USER_ID=?,CURRENT_YEAR_CREDIT_BALANCE=?,CURRENT_YEAR_DEBIT_BALANCE=?,UPDATED_DATE=?,YEAR=?,MONTH=?,MONTH_OPENING_BALANCE=?,MONTH_OPENING_BAL_DR_CR_IND=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and ACCOUNT_HEAD_CODE=? and year=? and month=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?");

                pss1.setString(1, userid);
                pss1.setDouble(2, YrCredit);
                pss1.setDouble(3, YrDebit);
                pss1.setTimestamp(4, ts);
                pss1.setInt(5, CashbookYear);
                pss1.setInt(6, CashbookMonth);
                pss1.setDouble(7, OpenBal);
                pss1.setString(8, OpenBalInd);
                pss1.setInt(9, AccId);
                pss1.setInt(10, OffCode);
                pss1.setString(11, FinanYr);
                pss1.setInt(12, cmbAcHeadCode);
                pss1.setInt(13, CashbookYear);
                pss1.setInt(14, CashbookMonth);
                pss1.setInt(15, cmbMas_SL_type);
                pss1.setInt(16, Mas_SL_Code);
                pss1.executeUpdate();

                xml = xml + "<flag>success</flag>";
                pss1.close();
            }

            catch (Exception ex4) {
                System.out.println("Exception in update......" + ex4);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("Delete")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Delete</command>";

            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
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
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }
            FinanYr = request.getParameter("txtFinanYr");
            System.out.println(FinanYr);

            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }


            try {
                ps1 =
 con.prepareStatement("delete from FAS_SUB_LEDGER_MASTER where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and year=? and month=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?");
                ps1.setInt(1, cmbAcHeadCode);
                ps1.setInt(2, AccId);
                ps1.setInt(3, OffCode);
                ps1.setString(4, FinanYr);
                ps1.setInt(5, CashbookYear);
                ps1.setInt(6, CashbookMonth);
                ps1.setInt(7, cmbMas_SL_type);
                ps1.setInt(8, Mas_SL_Code);
                ps1.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps1.close();
            } catch (Exception ex2) {
                System.out.println("exception in add" + ex2);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        if (strCommand.equalsIgnoreCase("HeadCode")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String headcode = request.getParameter("txtAcc_Head_code");
            int headcodeno = Integer.parseInt(headcode);
            xml = "<response><command>HeadCode</command>";
            /*try
            {
                ps=con.prepareStatement("select account_head_desc from com_mst_account_heads where account_head_code=?");
                ps.setInt(1,headcodeno);
                res=ps.executeQuery();
                if(res.next())
                {
                    xml=xml+"<flag>success</flag><headcode>"+res.getString("account_head_desc")+"</headcode>";
                }
                else
                {
                    xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
            }catch(Exception e)
            {
                System.out.println("Exception in HeadCode:"+e);
            }*/

            try {
                ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, headcodeno);
                res = ps.executeQuery();
                if (res.next()) {
                    xml =
 xml + "<flag>success</flag><hid>" + headcodeno + "</hid><hdesc>" +
   res.getString("ACCOUNT_HEAD_DESC") + "</hdesc><BalType>" +
   res.getString("BALANCE_TYPE") + "</BalType><SL_YN>" +
   res.getString("SUB_LEDGER_TYPE_APPLICABLE") + "</SL_YN><rmk>" +
   res.getString("REMARKS") + "</rmk>";

                    if (res.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) {
                       int sl_cnt=0;
                    	ps =
  con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'"); // STATUS field added 05-07-19
                        ps.setInt(1, headcodeno);
                        res = ps.executeQuery();
                        while (res.next()) {
                           sl_cnt++;
                        	xml =
 xml + "<SLCODE>" + res.getInt("SUB_LEDGER_TYPE_CODE") + "</SLCODE>";
                            System.out.println(res.getInt("SUB_LEDGER_TYPE_CODE") +
                                               "code");
                            if (res.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                                System.out.println("take SL DESC");
                                ps2 =
 con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                                ps2.setInt(1,
                                           res.getInt("SUB_LEDGER_TYPE_CODE"));
                                res2 = ps2.executeQuery();
                                if (res2.next())
                                    xml =
 xml + "<SLDESC>" + res2.getString("SUB_LEDGER_TYPE_DESC") + "</SLDESC>";
                            }
                        }
                        if(sl_cnt==0) {
                            System.out.println("STATUS is 'N'");
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } else {
                    System.out.println("No record found");
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        out.write(xml);
        System.out.println("xml is:" + xml);
        out.close();
    }
}

package Servlets.FAS.FAS1.FundTransferSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
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

public class Fund_Transfer_Cancel_byHO extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

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
        ResultSet rs = null, rs2 = null, rs3 = null;
        //CallableStatement cs=null,cs1=null;
        PreparedStatement ps = null, ps2 = null, ps3 = null;
        String xml = "";
        String strCommand = "";
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

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;
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

        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            xml = "<response><command>load_Voucher_No</command>";

            try {
                String[] sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);

                //ps=con.prepareStatement("select VOUCHER_NO from FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'");
                ps =
  con.prepareStatement("select i.VOUCHER_NO from FAS_FUND_TRF_FROM_HO_MASTER i,FAS_CROSS_REFERENCE c where " +
                       " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.DATE_OF_TRANSFER=? and i.TRANSFER_STATUS!='C' " +
                       " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                       " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.VOUCHER_NO=c.VOUCHER_NO " +
                       " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE='FTH'");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                rs = ps.executeQuery();

                int count = 0;
                while (rs.next()) {

                    xml =
 xml + "<Rec_No>" + rs.getInt("VOUCHER_NO") + "</Rec_No>";
                    count++;
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (strCommand.equalsIgnoreCase("load_Voucher_Details")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            xml = "<response><command>load_Voucher_Details</command>";
            int txtVoucher_No = 0;
            // Date txtCrea_date;

            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);


            try {
                String[] sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);

                ps =
  con.prepareStatement("select CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE,HO_BANK_ID," +
                       "HO_BRANCH_ID,HO_ACCOUNT_NO,trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,TOTAL_TRN_RECORDS,REMARKS,HO_REF_NO,to_char(HO_REF_DATE,'DD/MM/YYYY') as referDate  " +
                       "from  FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and DATE_OF_TRANSFER=? and VOUCHER_NO=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtVoucher_No);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<MasHeadCode>" + rs.getString("ACCOUNT_HEAD_CODE").trim() +
   "</MasHeadCode>";
                    xml =
 xml + "<accNo>" + rs.getString("HO_ACCOUNT_NO").trim() + "</accNo>";

                    ps3 =
 con.prepareStatement("select bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||COALESCE(br.CITY_TOWN_NAME,'') as bankNAME" +
                      " from FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where br.BANK_ID=? and br.BRANCH_ID=? and br.BANK_ID=bk.BANK_ID");
                    ps3.setInt(1, rs.getInt("HO_BANK_ID"));
                    ps3.setInt(2, rs.getInt("HO_BRANCH_ID"));

                    rs3 = ps3.executeQuery();
                    if (rs3.next()) {
                        xml =
 xml + "<bk_name>" + rs3.getString("bankNAME") + "</bk_name>";
                        rs3.close();
                        ps3.close();
                    }


                    xml =
 xml + "<bk_id>" + rs.getInt("HO_BANK_ID") + "</bk_id><br_id>" +
   rs.getInt("HO_BRANCH_ID") + "</br_id><Total_amt>" +
   rs.getString("TOTAL_AMOUNT") + "</Total_amt><No_TRN_Rec>" +
   rs.getInt("TOTAL_TRN_RECORDS") + "</No_TRN_Rec><Remak>" +
   rs.getString("REMARKS") + "</Remak><REF_NO>" + rs.getString("HO_REF_NO") +
   "</REF_NO><referDate>" + rs.getString("referDate") + "</referDate>";
                }


                System.out.println("in b/w here exection");

                ps2 =
 con.prepareStatement("select trs.TRANSFER_TO_OFFICE_ID,off.OFFICE_NAME,trs.TRANSFERED_TO_HO_UNIT_ID," +
                      "bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||COALESCE(br.CITY_TOWN_NAME,'') as bankNAME_trs," +
                      "trs.ACCOUNT_HEAD_CODE,trs.OFFICE_BANK_ID,trs.OFFICE_BRANCH_ID,trs.OFFICE_ACCOUNT_NO," +
                      "trs.CHEQUE_OR_DD ,trs.CHEQUE_DD_NO ,to_char(trs.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date ," +
                      "trim(to_char(trs.AMOUNT,'99999999999999.99')) as  AMOUNT,trs.PARTICULARS  from FAS_FUND_TRF_FROM_HO_TRN trs,COM_MST_OFFICES Off,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where " +
                      "  trs.TRANSFER_TO_OFFICE_ID=off.OFFICE_ID and bk.BANK_ID=trs.OFFICE_BANK_ID and " +
                      " br.BRANCH_ID=trs.OFFICE_BRANCH_ID and br.BANK_ID=trs.OFFICE_BANK_ID and " +
                      " trs.ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and  trs.CASHBOOK_YEAR=?::numeric and trs.CASHBOOK_MONTH=?::numeric and trs.VOUCHER_NO=?");
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                ps2.setString(3, rs.getString("CASHBOOK_YEAR"));
                ps2.setInt(4, rs.getInt("CASHBOOK_MONTH"));
                ps2.setInt(5, txtVoucher_No);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    xml =
 xml + "<Office_id>" + rs2.getInt("TRANSFER_TO_OFFICE_ID") +
   "</Office_id><Office_name>" + rs2.getString("OFFICE_NAME") +
   "</Office_name><AHcode>" + rs2.getInt("ACCOUNT_HEAD_CODE") + "</AHcode>";
                    ps3 =
 con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
 xml + "<AHdesc>" + rs3.getString("ACCOUNT_HEAD_DESC") + "</AHdesc>";
                    ps3.close();
                    rs3.close();

                    xml =
 xml + "<HO_acc_unitid>" + rs2.getInt("TRANSFERED_TO_HO_UNIT_ID") +
   "</HO_acc_unitid>";
                    ps3 =
 con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                    ps3.setInt(1, rs2.getInt("TRANSFERED_TO_HO_UNIT_ID"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
 xml + "<HO_acc_unitName>" + rs3.getString("ACCOUNTING_UNIT_NAME") +
   "</HO_acc_unitName>";
                    else
                        xml =
 xml + "<HO_acc_unitName>" + null + "</HO_acc_unitName>";
                    ps3.close();
                    rs3.close();

                    xml =
 xml + "<off_bank_id>" + rs2.getInt("OFFICE_BANK_ID") +
   "</off_bank_id><off_branch_id>" + rs2.getInt("OFFICE_BRANCH_ID") +
   "</off_branch_id>";
                    xml =
 xml + "<off_bank_name>" + rs2.getString("bankNAME_trs") + "</off_bank_name>";
                    xml =
 xml + "<che_or_DD>" + rs2.getString("CHEQUE_OR_DD") + "</che_or_DD>" +
   "<che_DD_no>" + rs2.getString("CHEQUE_DD_NO") + "</che_DD_no>" +
   "<che_DD_date>" + rs2.getString("cheq_dd_date") + "</che_DD_date>" +
   "<off_bankAccNo>" + rs2.getString("OFFICE_ACCOUNT_NO").trim() +
   "</off_bankAccNo>" + "<sub_amount>" + rs2.getString("AMOUNT") +
   "</sub_amount><sub_part>" + rs2.getString("PARTICULARS") + "</sub_part>";
                }

            } catch (Exception e) {
                System.out.println("catch..HERE.in failure to retrieve." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        HttpSession session = request.getSession(false);
        try {

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
        CallableStatement cs = null, cs1 = null;
        PreparedStatement ps = null;
        String xml = "";
        String strCommand = "";
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

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Cancel")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            Date txtCrea_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
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

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /*
            System.out.println("b4 getting month and year");
            try{txtCash_year=Integer.parseInt(sd[2]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_year "+txtCash_year);

                        try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_Month_hid "+txtCash_Month_hid);

            String[] sp=request.getParameter("txtCrea_date").split("/");
            System.out.println(sp[0]+" "+sp[1]+" "+sp[2]);
            int check_year=Integer.parseInt(sp[2]);                 // to check in while loop
            int check_day=Integer.parseInt(sp[0]);                    // to check in while loop
                        System.out.println(Integer.parseInt(sp[2]));
                        System.out.println("here"+check_year);

             String check_date=request.getParameter("txtCrea_date");
             sp=request.getParameter("txtCrea_date").split("/");
             check_date=sp[2]+"/"+sp[1]+"/"+sp[0];

             System.out.println(check_date); // to check in while loop with d/b date it converted to yyyy/mm/dd form
             try
             {
                 String sql1="select FINANCIAL_YEAR," +
                 "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ," +
                 "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
                 "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  " +
                 "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";

              // date is taken as string from database in above format for checking with Payment date variable ( check_date is string type)
              // checking of dates performed in form of String checking
              ps=con.prepareStatement(sql1);
              rs=ps.executeQuery();
              int Begin_yr,End_yr;
             while(rs.next())
             {
                 String[] yr=rs.getString("FINANCIAL_YEAR").split("-");
                  Begin_yr=Integer.parseInt(yr[0]);
                  End_yr=Integer.parseInt(yr[1]);
                                  System.out.println("while");
                                  System.out.println(Begin_yr+ " "+End_yr);
                                  System.out.println(rs.getString("mar_beg")+" "+rs.getString("mar_end"));

                 if(check_year==Begin_yr)          //   to check which financial year it belongs
                 {
                     if(txtCash_Month_hid>=4 && txtCash_Month_hid<=12)
                     {
                              System.out.println("if 4");
                              if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                              {
                                  txtCash_Month_hid=03;
                              System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                              }
                              else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                              {
                                  txtCash_Month_hid=04;
                              System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                              }
                              else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                              {
                                  txtCash_Month_hid=txtCash_Month_hid+1;
                                  if(txtCash_Month_hid>12)
                                      {
                                      txtCash_Month_hid=1;
                                      txtCash_year=txtCash_year+1;
                                      System.out.println("hello"+txtCash_year);
                                      }
                                  System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                              }
                              else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                              {
                                 //txtCash_Month_hid=txtCash_Month_hid;
                                 System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                              }
                     }

                 }
                 else  if(check_year==End_yr)
                 {
                     if(txtCash_Month_hid>=1 && txtCash_Month_hid<=3)
                     {
                         txtCash_year=End_yr;System.out.println("if 3");
                         if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                          {
                              txtCash_Month_hid=03;
                          System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                          }
                          else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                          {
                              txtCash_Month_hid=04;
                          System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                          }
                          else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                          {
                              txtCash_Month_hid=txtCash_Month_hid+1;
                              if(txtCash_Month_hid>12)            // No chance for this condition
                              {
                              txtCash_Month_hid=1;
                              txtCash_year=txtCash_year+1;
                              System.out.println("hello"+txtCash_year);
                              }
                              System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                          }
                         else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                         {
                             //txtCash_Month_hid=txtCash_Month_hid;
                             System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                         }
                     }
                 }
             }
             ps.close();
             rs.close();
             }
             catch(Exception e)
             {
             sendMessage(response,"The Fund Transfer Transaction Failed finding Bank book month","ok");
             System.out.println("exception"+e);
             }

           */


            String sql_del =
                "update FAS_FUND_TRF_FROM_HO_MASTER set TRANSFER_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                " and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and VOUCHER_NO=?  ";
            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                ps = con.prepareStatement(sql_del);
                ps.setString(1, update_user);
                ps.setTimestamp(2, ts);
                ps.setInt(3, cmbAcc_UnitCode);
                ps.setInt(4, cmbOffice_code);
                ps.setInt(5, txtCash_year);
                ps.setInt(6, txtCash_Month_hid);
                ps.setInt(7, txtVoucher_No);
                ps.executeUpdate();
                String txtReferNO_edit = "", txtRemak_edit =
                    ""; // for cross reference
                Date txtReferDate_edit = null;
                String radAuth_MC = "";
                int txtAuth_By = 0;


                cs1 =
 con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
                cs1.setInt(1, cmbAcc_UnitCode);
                cs1.setInt(2, txtCash_year);
                cs1.setInt(3, txtCash_Month_hid);
                cs1.setInt(4, txtVoucher_No);
                cs1.setInt(5, cmbOffice_code);
                cs1.setDate(6, txtCrea_date);
                cs1.setString(7, "FTH");
                cs1.setString(8, txtReferNO_edit);
                cs1.setDate(9, txtReferDate_edit);
                cs1.setString(10, txtRemak_edit);
                cs1.setInt(11, txtAuth_By);
                cs1.setString(12, "insert");
                cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
                cs1.setNull(13,java.sql.Types.NUMERIC);
                cs1.setString(14, update_user);
                cs1.setTimestamp(15, ts);
                cs1.setString(16, radAuth_MC);
                cs1.execute(); // insertion into cross reference table
                //int errcode = cs1.getInt(13);
                int errcode = cs1.getBigDecimal(13).intValue();
                System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                System.out.println("cmbOffice_code " + cmbOffice_code);
                System.out.println("txtCrea_date " + txtCrea_date);
                System.out.println("txtCash_year " + txtCash_year);
                System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    con.rollback();
                    sendMessage(response,
                                "The Fund Transfer  Cancellation Failed ",
                                "ok");
                    xml = xml + "<flag>failure</flag>";
                }
                con.commit();
                sendMessage(response,
                            "The Fund Transfer Transaction Voucher Number '" +
                            txtVoucher_No +
                            "' has been Cancelled Successfully ", "ok");
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
                sendMessage(response, "The Fund Transfer Cancellation Failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
            }

        }

    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("error in send message");
        }
    }
}

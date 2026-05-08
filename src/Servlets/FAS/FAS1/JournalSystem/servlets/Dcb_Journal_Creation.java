package Servlets.FAS.FAS1.JournalSystem.servlets;



import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Dcb_Journal_Creation extends HttpServlet {
    private String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        System.out.println("inside servlet");
        /**
	       * Set Content Type
	      */
        PrintWriter out = response.getWriter();
        response.setHeader("cache-control", "no-cache");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);


        /**
	       * Session Checking
	      */
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


        /**
	       * Variables Declaration
	      */
        Connection con = null;
        PreparedStatement ps2 = null,ps3=null,ps4=null;
        ResultSet rs2 = null,rs3=null,rs4=null;
        String sql = "";
        String txtFrom_date = null, txtTo_date = null;
        /**
	       * Database Connection
	      */
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
        }
        int count = 0, cmbAcc_UnitCode = 0, cmbOffice_code = 0, cashbookyear =
            0, cashbookmonth = 0;
        String xml = null, cmd = "", type = "", dcbdate = "";

        try {
            cmd = request.getParameter("Command");
            System.out.println("command" + cmd);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            cmbAcc_UnitCode = Integer.parseInt(request.getParameter("unitid"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("unitid " + cmbAcc_UnitCode);

        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("officeid"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("officeid " + cmbOffice_code);

        try {
            cashbookyear =
                    Integer.parseInt(request.getParameter("cashbookyear"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cashbookyear " + cashbookyear);

        try {
            cashbookmonth =
                    Integer.parseInt(request.getParameter("cashbookmonth"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cashbookmonth " + cashbookmonth);

        xml = "<response>";
        if (cmd.equalsIgnoreCase("load")) {
            System.out.println("load in servlet");

            xml = xml + "<command>loadgrid</command>";

            /*	sql="select a.*,f.SCH_TYPE_DESC,f.SCH_TYPE_ID,b.SUB_LEDGER_CODE,e.SCH_SHORT_DESC,c.SUB_LEDGER_TYPE_DESC,b.SUB_LEDGER_TYPE_CODE,d.ACCOUNT_HEAD_CODE,d.ACCOUNT_HEAD_DESC,b.AMOUNT,b.REMARKS from     \n" +
            	"  (select ACCOUNTING_UNIT_ID,OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,WC_FREEZED from PMS_DCB_FREEZE_STATUS where ACCOUNTING_UNIT_ID=? and OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and WC_FREEZED='Y')a    \n" +
            	"  left outer join    \n" +
            	"  (select ACCOUNTING_UNIT_ID,OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE,SCHEME_TYPE_ID,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,trim(to_char(amount,'99999999999999.99')) as amount,REMARKS from PMS_DCB_JOURNAL_DETAILS where CR_DR_INDICATOR='CR')b    \n" +
            	"   on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and a.OFFICE_ID=b.OFFICE_ID and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH  left outer join    \n" +
            	"    (select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES)c    \n" +
            	"   on b.SUB_LEDGER_TYPE_CODE=c.SUB_LEDGER_TYPE_CODE left outer join    \n" +
            	"   (select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS)d    \n" +
            	"   on b.ACCOUNT_HEAD_CODE=d.ACCOUNT_HEAD_CODE left outer join   \n" +
            	"   (SELECT OFFICE_ID,SCH_SNO,SCH_SHORT_DESC FROM PMS_SCH_MASTER )e  \n" +
            	"   on a.OFFICE_ID=e.OFFICE_ID and b.SUB_LEDGER_CODE=e.SCH_SNO left outer join \n" +
            	"   (select SCH_TYPE_ID,SCH_TYPE_DESC from PMS_SCH_LKP_TYPE)f \n" +
            	"   on b.SCHEME_TYPE_ID=f.SCH_TYPE_ID";
            	*/
double cramt=0.0,netcr=0.0;

            sql =
 "select a.*,f.SCH_TYPE_DESC,f.SCH_TYPE_ID,b.SUB_LEDGER_CODE,e.SCH_SHORT_DESC,c.SUB_LEDGER_TYPE_DESC,b.SUB_LEDGER_TYPE_CODE,d.ACCOUNT_HEAD_CODE,d.ACCOUNT_HEAD_DESC,b.AMOUNT,b.REMARKS from  \n" +
   " (select ACCOUNTING_UNIT_ID,OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,WC_FREEZED from PMS_DCB_FREEZE_STATUS where ACCOUNTING_UNIT_ID=? and OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and WC_FREEZED='Y')a    \n" +
   " join  \n" +
   " (select ACCOUNTING_UNIT_ID,OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE,SCHEME_TYPE_ID,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,trim(to_char(amount,'99999999999999.99')) as amount,REMARKS from PMS_DCB_JOURNAL_DETAILS where CR_DR_INDICATOR='CR' and VOUCHER_NO is null or VOUCHER_NO=0 and VOUCHER_DATE is null)b  \n" +
   "  on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and a.OFFICE_ID=b.OFFICE_ID and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH  left outer join    \n" +
   " (select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES)c    \n" +
   " on b.SUB_LEDGER_TYPE_CODE=c.SUB_LEDGER_TYPE_CODE left outer join    \n" +
   " (select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS)d    \n" +
   " on b.ACCOUNT_HEAD_CODE=d.ACCOUNT_HEAD_CODE left outer join   \n" +
   " (SELECT OFFICE_ID,SCH_SNO,SCH_SHORT_DESC FROM PMS_SCH_MASTER )e  \n" +
   " on a.OFFICE_ID=e.OFFICE_ID and b.SUB_LEDGER_CODE=e.SCH_SNO left outer join  \n" +
   " (select SCH_TYPE_ID,SCH_TYPE_DESC from PMS_SCH_LKP_TYPE)f  \n" +
   " on b.SCHEME_TYPE_ID=f.SCH_TYPE_ID";


           System.out.println(" SQL :: " + sql);
            try {
                ps2 = con.prepareStatement(sql);
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                ps2.setInt(3, cashbookyear);
                ps2.setInt(4, cashbookmonth);

                rs2 = ps2.executeQuery();
                System.out.println("rs2" + rs2);
                while (rs2.next()) {
                	   cramt= rs2.getDouble("AMOUNT");
                    xml +=
"<unitid>" + rs2.getInt("ACCOUNTING_UNIT_ID") + "</unitid>";
                    xml +=
"<officeid>" + rs2.getInt("OFFICE_ID") + "</officeid>";
                    xml +=
"<cashyear>" + rs2.getInt("CASHBOOK_YEAR") + "</cashyear>";
                    xml +=
"<cashmonth>" + rs2.getInt("CASHBOOK_MONTH") + "</cashmonth>";
                    xml += "<schId>" + rs2.getInt("SCH_TYPE_ID") + "</schId>";
                    xml +=
"<schDesc><![CDATA[" + rs2.getString("SCH_TYPE_DESC") + "]]></schDesc>";
                    xml +=
"<headCode>" + rs2.getString("ACCOUNT_HEAD_CODE") + "</headCode>";
                    xml +=
"<headCodedesc>" + rs2.getString("ACCOUNT_HEAD_DESC") + "</headCodedesc>";
                    xml +=
"<slTypecode>" + rs2.getString("SUB_LEDGER_TYPE_CODE") + "</slTypecode>";
                    xml +=
"<slTypedesc>" + rs2.getString("SUB_LEDGER_TYPE_DESC") + "</slTypedesc>";
                    xml +=
"<slCode>" + rs2.getString("SUB_LEDGER_CODE") + "</slCode>";
                    xml +=
"<slCodedesc>" + rs2.getString("SCH_SHORT_DESC") + "</slCodedesc>";
                    xml +=
"<crAmount>" + cramt + "</crAmount>";
                    xml +=
"<Remarks><![CDATA[" + rs2.getString("REMARKS") + "]]></Remarks>";
          netcr=netcr+cramt;
                    count++;
                }
                if (count == 0)
                    xml += "<flag>NoData</flag>";
                else
                {
                	try{
                	double dramt=0.0;
                	int sltype=0,slcode=0;
                	
                	String sql1=" SELECT SUM(amount) AS dramt,    cashbook_month,  " +
                			"  cashbook_year,    office_id "+ 
								 "  FROM pms_dcb_journal_details"+
                		"   WHERE cr_dr_indicator = 'DR'"+
                		" 	   AND office_id = "+cmbOffice_code+
                		" 	   AND cashbook_month = "+cashbookmonth+
                		" 	   AND cashbook_year = "+cashbookyear+
                		" 	   GROUP BY office_id,       cashbook_month,    cashbook_year";
                	System.out.println("sec query:::"+sql1);
                	 ps3 = con.prepareStatement(sql1);
                	 rs3=ps3.executeQuery();
                	 while(rs3.next())
                	 {
                		 dramt=rs3.getDouble("dramt");
                	 }
                	 if(dramt==netcr)
                	 {
                		 xml += "<flagCheck>enabled</flagCheck>";
                	 }
                	 else
                	 {
                		 xml += "<flagCheck>disabled</flagCheck>";
                	 }
                	 
                	 String subqry="SELECT SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,trim(TO_CHAR(amount,'99999999999999.99')) AS amount " + 
                	 		" FROM pms_dcb_journal_details" + 
                	 		" WHERE CR_DR_INDICATOR = 'DR'" + 
                	 		" AND office_id = " + cmbOffice_code +
                	 		" AND cashbook_month =" + cashbookmonth +
                	 		" AND cashbook_year ="+cashbookyear;
                	 System.out.println("subqry:::"+subqry);
                	 ps4 = con.prepareStatement(subqry);
                	 rs4=ps4.executeQuery();
                	 while(rs4.next())
                	 {
                		 sltype=rs4.getInt("SUB_LEDGER_TYPE_CODE");
                		 slcode=rs4.getInt("SUB_LEDGER_CODE");
                		 double amt=rs4.getDouble("amount");
                		 
                		 System.out.println("sltype>>>"+sltype+"slcode>>>"+slcode);
                    	 if(sltype==10 && slcode==0||sltype==10 && slcode==0 && amt==0)
                		 {
                			 xml += "<slCheck>SlcodeZero</slCheck>";
                		 }
                		 else
                		 {
                			 xml += "<slCheck>Slcodevalue</slCheck>";
                		 }
                	 }
                	 System.out.println("sltype>>>"+sltype+"slcode>>>"+slcode);
                	 if(sltype==10 && slcode==0)
            		 {
            			 xml += "<slCheck>SlcodeZero</slCheck>";
            		 }
            		 else
            		 {
            			 xml += "<slCheck>Slcodevalue</slCheck>";
            		 }
                	 
                	 
                	}
                	catch(Exception ee)
                	{
                		System.out.println("exception in fetching dr amount::"+ee);
                	}
                	 xml += "<flag>success</flag>";
                	 
                }
                System.out.println("xml in flag" + xml);
            } catch (Exception e) {
                System.out.println("Exception in loadTransferUnit..." +
                                   e.getMessage());
                xml += "<flag>" + e.getMessage() + "</flag>";
            }
        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String strCommand = "";
        Connection con = null;
        PreparedStatement ps = null, ps1 = null, ps2 = null, ps4 = null;
        Statement st = null;
        ResultSet rs = null, result = null, rs2 = null;
        //-----------------------------------------------------------------------------------------------

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
        //-----------------------------------------------------------------------------------------------

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
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        //-----------------------------------------------------------------------------------------------
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        //-----------------------------------------------------------------------------------------------
        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0;
            Date voucher_date = null;
            String cr_dr_indicator = "", sql = "", flag = "", particulars =
                "", remarks = "", voucher_display = "";
            String txtCrea_date = null, financial_year[] = null;
            int account_head_code = 0, sub_ledger_code = 0, trn_records =
                0, count = 0, trn_count = 0, grid_count =
                0, depriciation_rate = 0, cashbook_year = 0, cashbook_month =
                0, cb_ref_no = 0;

            String paid_to = "";
            int sub_ledger_type = 0;
            double amount = 0;

            // changes here
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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


            txtCrea_date = request.getParameter("txtCrea_date");
            System.out.println("txtCrea_date " + txtCrea_date);

            System.out.println("b4 getting month and year");
            try {
                txtCash_year = Integer.parseInt(sd[2]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_year " + txtCash_year);

            try {
                txtCash_Month_hid = Integer.parseInt(sd[1]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

            String[] schemeTypeId = request.getParameterValues("schemeTypeId");
            int schId = 0;
            int schemes = 0;
            /*   for(int i=0;i<schemeTypeId.length;i++){
                   schId=Integer.parseInt(schemeTypeId[i]);
                   System.out.println("schId"+schId);
               }
                */
            //String checkNo[]=request.getParameterValues("checkNo");
            // int schemes=Integer.parseInt(request.getParameter("checkNo"));
            // String schemesarr[]=request.getParameterValues("checkNo");
            System.out.println("schemes" + schemes);
            System.out.println("Scheme.length" + schemeTypeId.length);

            int Originated_SL_No = 0;

            for (int ss = 0; ss < schemeTypeId.length; ss++) {
                schId = Integer.parseInt(schemeTypeId[ss]);
                // schemes=Integer.parseInt(schemesarr[i]);
                schemes = schId;

                System.out.println("schid WWWWW" + schId);
                System.out.println("count WWWWW" + count);
                count = 0;
                try {
                    ps =
  con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?)");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setInt(3, txtCash_year);
                    ps.setInt(4, txtCash_Month_hid);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        Originated_SL_No = rs.getInt(1);
                        System.out.println("Originated_SL_No" +
                                           Originated_SL_No);
                    }
                    Originated_SL_No = Originated_SL_No + 1;
                    rs.close();
                } catch (Exception e) {
                    System.out.println("exception" + e);
                }
                System.out.println("Originated_SL_No " + Originated_SL_No);

                int trnrecords = 0;
                try {
                    ps2 = con.prepareStatement("select count(SCHEME_TYPE_ID) from PMS_DCB_JOURNAL_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SCHEME_TYPE_ID=?");
                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, cmbOffice_code);
                    ps2.setInt(3, txtCash_year);
                    ps2.setInt(4, txtCash_Month_hid);
                    ps2.setInt(5, schId);
                    result = ps2.executeQuery();
                    System.out.println("result" + result);
                    while (result.next()) {
                        trnrecords = result.getInt(1);
                        System.out.println("trnrecords" + trnrecords);
                    }
                    System.out.println("trnrecords after" + trnrecords);


                } catch (Exception e) {
                    System.out.println("error in trRecoeds" + e.getMessage());
                }

                try {
                    ps2.close();
                    con.setAutoCommit(false);


                    ps1 = con.prepareStatement("insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,JOURNAL_TYPE_CODE,TOTAL_TRN_RECORDS,MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,SUB_LEDGER_CODE,DEPRECIATION_RATE)values(?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps1.setInt(1, cmbAcc_UnitCode);
                    ps1.setInt(2, cmbOffice_code);
                    ps1.setString(3, txtCrea_date);
                    ps1.setInt(4, txtCash_year);
                    ps1.setInt(5, txtCash_Month_hid);
                    ps1.setInt(6, Originated_SL_No);
                    ps1.setInt(7, 73);
                    ps1.setInt(8, trnrecords);
                    ps1.setString(9, "D");
                    ps1.setString(10, "GJV");
                    ps1.setString(11, "L");
                    ps1.setString(12, update_user);
                    ps1.setTimestamp(13, ts);
                    ps1.setInt(14, sub_ledger_code);
                    ps1.setInt(15, depriciation_rate);
                    System.out.println("ps1 ::::::::;;" + ps1);
                    int kk = ps1.executeUpdate();
                    System.out.println("kk executed" + kk);
                    if (kk == 0) {
                        System.out.println("redirect");
                        sendMessage(response, "Master Creation Failed ", "ok");
                    } else

                    {
                        trn_count = 0;
                        ps1.close();
                        System.out.println("inside success");
                        //                         String checkNo[]=request.getParameterValues("checkNo");
                        //                         int schemes=Integer.parseInt(request.getParameter("schemes"));
                        //                         System.out.println("schemes"+schemes);
                        //                         System.out.println("checkNo.length"+checkNo.length);


                        //                         for(int i=0;i<checkNo.length;i++)
                        //                         {
                        sql = "select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,REMARKS from PMS_DCB_JOURNAL_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SCHEME_TYPE_ID=?";
                        ps2 = con.prepareStatement(sql);
                        System.out.println("ps2" + ps2);
                        ps2.setInt(1, cmbAcc_UnitCode);
                        ps2.setInt(2, cmbOffice_code);
                        ps2.setInt(3, txtCash_year);
                        ps2.setInt(4, txtCash_Month_hid);
                        ps2.setInt(5, schemes);
                        rs2 = ps2.executeQuery();
                        System.out.println("rs2 be4 trans" + rs2);
                        String indicator;
                        int slno = 1;
                        while (rs2.next()) {
                            indicator = rs2.getString("CR_DR_INDICATOR");
                          /*  if (indicator.equals("CR")) {
                                slno = 1;
                            } else
                            {
                                slno++;
                            }  */
                            System.out.println("slno for trans:" + slno);
                            account_head_code =
                                    rs2.getInt("ACCOUNT_HEAD_CODE");
                            cr_dr_indicator = rs2.getString("CR_DR_INDICATOR");
                            sub_ledger_type =
                                    rs2.getInt("SUB_LEDGER_TYPE_CODE");
                            sub_ledger_code = rs2.getInt("SUB_LEDGER_CODE");
                            amount = rs2.getDouble("AMOUNT");
                            particulars = rs2.getString("REMARKS");
                            System.out.println("particulars" + particulars);

                            ps =
  con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            ps.setInt(1, cmbAcc_UnitCode);
                            ps.setInt(2, cmbOffice_code);
                            ps.setInt(3, txtCash_year);
                            ps.setInt(4, txtCash_Month_hid);
                            ps.setInt(5, Originated_SL_No);
                            ps.setInt(6, slno);
                            ps.setInt(7, account_head_code);
                            ps.setString(8, cr_dr_indicator);
                            ps.setInt(9, sub_ledger_type);
                            ps.setInt(10, sub_ledger_code);
                            ps.setDouble(11, amount);
                            ps.setString(12, particulars);
                            ps.setString(13, update_user);
                            ps.setTimestamp(14, ts);
                            ps.setInt(15, cb_ref_no);
                            slno++;
                            int kkkk = ps.executeUpdate();
                            if (kkkk > 0)
                                count++;
                            System.out.println("ps in transssssssss" + ps);
                        }
                        //   }

                        System.out.println("count in check" + count);

                        if (count ==
                            trnrecords) //total tr records=4 so if count matches trnrecords then flag is success
                            flag = "success";
                        else
                            flag = "failure";
                        System.out.println("rs2" + rs2);

                    }
                } catch (Exception e) {
                    System.out.println("exception" + e.getMessage());
                    flag = "failure";

                }

                try {
                    if (flag.equals("success")) {
                        ps.close();
                        con.commit();
                    //    System.out.println("inside update");

                        System.out.println("txtCrea_date" + txtCrea_date);
                        System.out.println("schemes" + schemes);

                        ps =  con.prepareStatement("update PMS_DCB_JOURNAL_DETAILS set VOUCHER_NO=?,VOUCHER_DATE=to_date(?,'dd-mm-yy') where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SCHEME_TYPE_ID=?");
                        ps.setInt(1, Originated_SL_No);
                       
                        ps.setString(2, txtCrea_date);
                     
                        ps.setInt(3, cmbAcc_UnitCode);
                        
                        ps.setInt(4, cmbOffice_code);
                       
                        ps.setInt(5, txtCash_year);
                       
                        ps.setInt(6, txtCash_Month_hid);
                        
                        ps.setInt(7, schemes);
                       // System.out.println("schemes >>>>> " + schemes);
                        int kk = ps.executeUpdate();
                        System.out.println("updatedddddddd" + kk);
                        /*if(kk>0)
                        {

                        sendMessage(response,"The DCB Journal Number "+Originated_SL_No+" has been Created Successfully ","ok");
                        con.commit();
                        }*/
                    }
                } catch (Exception e) {
                    System.out.println("Err in insertion 434::: " +
                                       e.getMessage());
                }
            }

            try {
                ps4 = con.prepareStatement("update PMS_DCB_FREEZE_STATUS set JOURNAL_POSTED='Y',JOURNAL_POSTED_DATE=to_date(?,'dd-mm-yy'),FAS_UPDATED_DATE=?,FAS_UPDATED_BY_USER_ID=? where ACCOUNTING_UNIT_ID=? and OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                ps4.setString(1, txtCrea_date);
                System.out.println("txtCrea_date" + txtCrea_date);
                ps4.setTimestamp(2, ts);
                System.out.println("ts" + ts);
                ps4.setString(3, update_user);
                System.out.println("update_user" + update_user);
                ps4.setInt(4, cmbAcc_UnitCode);
                System.out.println("cmbAcc_UnitCode >>>>> " + cmbAcc_UnitCode);
                ps4.setInt(5, cmbOffice_code);
                System.out.println("cmbOffice_code >>>>> " + cmbOffice_code);
                ps4.setInt(6, txtCash_year);
                System.out.println("txtCash_year >>>>> " + txtCash_year);
                ps4.setInt(7, txtCash_Month_hid);
                System.out.println("txtCash_Month_hid >>>>> " +
                                   txtCash_Month_hid);
                //  ps4.setInt(8,schemes);
                System.out.println("schemes >>>>> " + schemes);
                int up = ps4.executeUpdate();
                System.out.println("updatedddddddd" + up);
                if (up == 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                " DCB freeze status updation Failed ", "ok");
                } else {
                    sendMessage(response,
                                "The DCB Journal Number " + Originated_SL_No +
                                " has been Created Successfully ", "ok");
                    con.commit();
                }


                /*  else {
                        System.out.println("b4 Rollback");
                        con.rollback();
                        sendMessage(response,"The DCB Journal Number Creation Failed ","ok");
                    }*/
            } catch (Exception e) {
                System.out.println("Err in insertion ::: " + e.getMessage());
            } finally {

                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                }
            }


        }
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            System.out.println("sendMessage");
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}



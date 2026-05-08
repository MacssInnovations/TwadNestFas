package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import Servlets.Security.classes.UserProfile;

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

public class Fund_Receipt_Create_byHO extends HttpServlet {
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
        ResultSet rs = null, rsbank = null;
        CallableStatement cs = null;
        PreparedStatement ps = null, psbank = null;
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


        if (strCommand.equalsIgnoreCase("office_with_bank_betails")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            xml = "<response><command>office_with_bank_betails</command>";

            try {
                int offid = 0;
                int unit_ID = 0;
                int cmb_HO_acc_unitid = 0;
                offid = Integer.parseInt(request.getParameter("oid"));
                try {
                    cmb_HO_acc_unitid =
                            Integer.parseInt(request.getParameter("cmb_HO_acc_unitid"));
                } catch (Exception e) {
                    System.out.println("A/c unit id not send");
                }
                String txtModule_Type = request.getParameter("txtModule_Type");
                String cr_dr_indi = request.getParameter("cr_dr_indi");
                String unspent_OR_col = request.getParameter("unspent_OR_col");
                String sql_bank = "";
                System.out.println("txtModule_Type.." + txtModule_Type);
                System.out.println("cr_dr_indi.." + cr_dr_indi);
                System.out.println("unspent_OR_col.." + unspent_OR_col);

                xml = xml + "<oid>" + offid + "</oid>";

                ps =
  con.prepareStatement("select u.ACCOUNTING_UNIT_ID,o.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES u,COM_MST_OFFICES o  where u.ACCOUNTING_FOR_OFFICE_ID=o.OFFICE_ID and u.ACCOUNTING_FOR_OFFICE_ID=?");
                ps.setInt(1, offid);
                rs = ps.executeQuery();
                if (rs.next()) {


                    if (offid == 5000) {
                        unit_ID = cmb_HO_acc_unitid;
                    } else {
                        unit_ID = rs.getInt("ACCOUNTING_UNIT_ID");
                    }


                    xml =
 xml + "<oname>" + rs.getString("OFFICE_NAME") + "</oname>";


                    if (unspent_OR_col.equalsIgnoreCase("COL")) {

                        if (offid == 5000 && unit_ID == 5) {
                            unit_ID = 5;
                            sql_bank =
                                    "SELECT curr.BANK_ID,\n" + "  curr.BRANCH_ID,\n" +
                                    "  curr.BANK_AC_NO,\n" +
                                    "  curr.AC_HEAD_CODE,\n" +
                                    "  bk.BANK_NAME\n" + "  || '-'\n" +
                                    "  || br.BRANCH_NAME\n" + "  ||'-'\n" +
                                    "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city\n" +
                                    "FROM FAS_OFFICE_BANK_AC_CURRENT curr,\n" +
                                    "  FAS_MST_BANK_BRANCHES br ,\n" +
                                    "  FAS_MST_BANKS bk\n" +
                                    "WHERE curr.STATUS              ='Y'\n" +
                                    "AND curr.ACCOUNTING_UNIT_ID    =?\n" +
                                    "AND curr.MODULE_ID             =?\n" +
                                    "AND curr.CR_DR_TYPE            =?\n" +
                                    "AND curr.SL_NO                 =1\n" +
                                    "AND curr.BANK_ID               =br.BANK_ID\n" +
                                    "AND curr.BRANCH_ID             =br.BRANCH_ID\n" +
                                    "AND br.BANK_ID                 =bk.BANK_ID\n" +
                                    "and ac_head_code like '%2'\n";
                        } else {
                            sql_bank =
                                    "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                                    " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and  curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                                    " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='COL'";
                        }

                    }


                    else if (unspent_OR_col.equalsIgnoreCase("OPR")) {
                        if (offid == 5000 && unit_ID == 5) {
                        	System.out.println("opr");
                            unit_ID = 5;
                            sql_bank =
                                    "SELECT curr.BANK_ID,\n" + "  curr.BRANCH_ID,\n" +
                                    "  curr.BANK_AC_NO,\n" +
                                    "  curr.AC_HEAD_CODE,\n" +
                                    "  bk.BANK_NAME\n" + "  || '-'\n" +
                                    "  || br.BRANCH_NAME\n" + "  ||'-'\n" +
                                    "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city\n" +
                                    "FROM FAS_OFFICE_BANK_AC_CURRENT curr,\n" +
                                    "  FAS_MST_BANK_BRANCHES br ,\n" +
                                    "  FAS_MST_BANKS bk\n" +
                                    "WHERE curr.STATUS              ='Y'\n" +
                                    "AND curr.ACCOUNTING_UNIT_ID    =?\n" +
                                    "AND curr.MODULE_ID             =?\n" +
                                    "AND curr.CR_DR_TYPE            =?\n" +
                                    "AND curr.SL_NO                 =1\n" +
                                    "AND curr.BANK_ID               =br.BANK_ID\n" +
                                    "AND curr.BRANCH_ID             =br.BRANCH_ID\n" +
                                    "AND br.BANK_ID                 =bk.BANK_ID\n" +
                                    "and curr.ac_head_code like '%3'";
                        } else {
                        	System.out.println("hh");
                            sql_bank =
                                    "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' ||coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                                    " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                                    " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='OPR'";
                        }

                    }
                    // here SL_NO=1 means that DEFAULT account number for that unit ..
                    System.out.println("quer:::"+sql_bank);
                    psbank = con.prepareStatement(sql_bank);

                    psbank.setInt(1, unit_ID);
                    psbank.setString(2, txtModule_Type);
                    psbank.setString(3, cr_dr_indi);
                    rsbank = psbank.executeQuery();

                    int count = 0;
                    while (rsbank.next()) {
                        xml = xml + "<flag>success</flag>";
                        System.out.println("inside if rsbank");

                        xml =
 xml + "<AC_HEAD_CODE>" + rsbank.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
                        xml =
 xml + "<BANK_ID>" + rsbank.getInt("BANK_ID") + "</BANK_ID>";
                        xml =
 xml + "<BRANCH_ID>" + rsbank.getInt("BRANCH_ID") + "</BRANCH_ID>";
                        xml =
 xml + "<BANK_AC_NO>" + rsbank.getLong("BANK_AC_NO") + "</BANK_AC_NO>";
                        xml =
 xml + "<bk_br_city>" + rsbank.getString("bk_br_city") + "</bk_br_city>";
                        count++;
                    }

                    if (count == 0) {
                        xml = xml + "<flag>failure_bank</flag>";
                    }

                    ps.close();
                    rs.close();
                    psbank.close();
                    rsbank.close();

                } else
                    xml = xml + "<flag>failure_office</flag>";
            } catch (Exception e1) {
                xml = xml + "<flag>failure_office</flag>";
                System.out.println("Exception handling.." + e1);
            }
            xml = xml + "</response>";
            System.out.println("xml.." + xml);
            out.println(xml);
        }


        else if (strCommand.equalsIgnoreCase("unspent_OR_col_based_bank")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            xml = "<response><command>unspent_OR_col_based_bank</command>";

            try {
                int unitID = 0;
                unitID = Integer.parseInt(request.getParameter("unitID"));
                String txtModule_Type = request.getParameter("txtModule_Type");
                String cr_dr_indi = request.getParameter("cr_dr_indi");
                String unspent_OR_col = request.getParameter("unspent_OR_col");
                String sql_bank = "";

                System.out.println("txtModule_Type.." + txtModule_Type);
                System.out.println("cr_dr_indi.." + cr_dr_indi);
                System.out.println("unspent_OR_col.." + unspent_OR_col);
                System.out.println("here xml" + xml);
                System.out.println("b4 bank fetch");

                if (unspent_OR_col.equalsIgnoreCase("COL")) {

                    if (unitID == 5) {
                        sql_bank =
                                "SELECT curr.BANK_ID,\n" + "  curr.BRANCH_ID,\n" +
                                "  curr.BANK_AC_NO,\n" +
                                "  curr.AC_HEAD_CODE,\n" + "  bk.BANK_NAME\n" +
                                "  || '-'\n" + "  || br.BRANCH_NAME\n" +
                                "  ||'-'\n" +
                                "  ||coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city\n" +
                                "FROM FAS_OFFICE_BANK_AC_CURRENT curr,\n" +
                                "  FAS_MST_BANK_BRANCHES br ,\n" +
                                "  FAS_MST_BANKS bk\n" +
                                "WHERE curr.STATUS              ='Y'\n" +
                                "AND curr.ACCOUNTING_UNIT_ID    =?\n" +
                                "AND curr.MODULE_ID             =?\n" +
                                "AND curr.CR_DR_TYPE            =?\n" +
                                "AND curr.SL_NO                 =1\n" +
                                "AND curr.BANK_ID               =br.BANK_ID\n" +
                                "AND curr.BRANCH_ID             =br.BRANCH_ID\n" +
                                "AND br.BANK_ID                 =bk.BANK_ID\n" +
                                "AND curr.AC_OPERATIONAL_MODE_ID='COL'\n" +
                                "and curr.ac_head_code like '%3'";
                    } else {
                        sql_bank =
                                "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                                " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                                " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='COL'";
                    }


                } else if (unspent_OR_col.equalsIgnoreCase("OPR")) {
                    if (unitID == 5) {
                        sql_bank =
                                "SELECT curr.BANK_ID,\n" + "  curr.BRANCH_ID,\n" +
                                "  curr.BANK_AC_NO,\n" +
                                "  curr.AC_HEAD_CODE,\n" + "  bk.BANK_NAME\n" +
                                "  || '-'\n" + "  || br.BRANCH_NAME\n" +
                                "  ||'-'\n" +
                                "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city\n" +
                                "FROM FAS_OFFICE_BANK_AC_CURRENT curr,\n" +
                                "  FAS_MST_BANK_BRANCHES br ,\n" +
                                "  FAS_MST_BANKS bk\n" +
                                "WHERE curr.STATUS              ='Y'\n" +
                                "AND curr.ACCOUNTING_UNIT_ID    =?\n" +
                                "AND curr.MODULE_ID             =?\n" +
                                "AND curr.CR_DR_TYPE            =?\n" +
                                "AND curr.SL_NO                 =1\n" +
                                "AND curr.BANK_ID               =br.BANK_ID\n" +
                                "AND curr.BRANCH_ID             =br.BRANCH_ID\n" +
                                "AND br.BANK_ID                 =bk.BANK_ID\n" +
                                "AND curr.AC_OPERATIONAL_MODE_ID='OPR'\n" +
                                "and curr.ac_head_code like '%2'";
                    } else {
                        sql_bank =
                                "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                                " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                                " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='OPR'";
                    }

                }
                // here SL_NO=1 means that DEFAULT account number for that unit ..
                System.out.println(sql_bank);
                psbank = con.prepareStatement(sql_bank);

                psbank.setInt(1, unitID);
                psbank.setString(2, txtModule_Type);
                psbank.setString(3, cr_dr_indi);
                rsbank = psbank.executeQuery();

                if (rsbank.next()) {
                    xml = xml + "<flag>success</flag>";
                    System.out.println("inside if rsbank");

                    xml =
 xml + "<AC_HEAD_CODE>" + rsbank.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
                    xml =
 xml + "<BANK_ID>" + rsbank.getInt("BANK_ID") + "</BANK_ID>";
                    xml =
 xml + "<BRANCH_ID>" + rsbank.getInt("BRANCH_ID") + "</BRANCH_ID>";
                    xml =
 xml + "<BANK_AC_NO>" + rsbank.getLong("BANK_AC_NO") + "</BANK_AC_NO>";
                    xml =
 xml + "<bk_br_city>" + rsbank.getString("bk_br_city") + "</bk_br_city>";
                } else
                    xml = xml + "<flag>failure_bank</flag>";


                psbank.close();
                rsbank.close();

            } catch (Exception e1) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Exception handling.." + e1);
            }
            xml = xml + "</response>";
            System.out.println("xml.." + xml);
            out.println(xml);
        }

    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


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
         * Variables Declarations
         */
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        CallableStatement cs1 = null;
        PreparedStatement ps = null;
        String xml = "";
        String strCommand = "";
        String Remittance_Type = "";


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


        /**
         * Get Command Parameter
         */
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /**
         * If Command is ADD
         */
        if (strCommand.equalsIgnoreCase("Add")) {

            /**
            * Set Content Type
            */
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";

            Calendar c;
            int cmb_HO_acc_unitid = 0;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId = 0;
            long txtBankAccountNo = 0;
            double txtAmount = 0;
            String txtRemarks = "";
            Date txtCrea_date = null, txtReferenceDate = null;
            String update_user = (String)session.getAttribute("UserId");
            String txtReferenceNo = "", radRecType = "";
            int txtCreditAccCode = 0, txtSubBankId = 0, txtSubBranchId =
                0, txtSub_Office_code = 0;
            long txtSubBankAccountNo = 0;
            String txtCheque_DD = "", txtCheque_DD_NO = "";
            Date txtCheque_DD_date = null;


            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            try {
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);

            try {
                txtBankId =
                        Integer.parseInt(request.getParameter("txtBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankId " + txtBankId);

            try {
                txtBranchId =
                        Integer.parseInt(request.getParameter("txtBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBranchId " + txtBranchId);

            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);

            try {
                txtSub_Office_code =
                        Integer.parseInt(request.getParameter("txtSub_Office_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSub_Office_code " + txtSub_Office_code);

            try {
                cmb_HO_acc_unitid =
                        Integer.parseInt(request.getParameter("cmb_HO_acc_unitid"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmb_HO_acc_unitid... " + cmb_HO_acc_unitid);


            try {
                txtCreditAccCode =
                        Integer.parseInt(request.getParameter("txtCreditAccCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCreditAccCode " + txtCreditAccCode);

            try {
                txtSubBankId =
                        Integer.parseInt(request.getParameter("txtSubBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBankId " + txtSubBankId);

            try {
                txtSubBranchId =
                        Integer.parseInt(request.getParameter("txtSubBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBranchId " + txtSubBranchId);

            try {
                txtSubBankAccountNo =
                        Long.parseLong(request.getParameter("txtSubBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBankAccountNo " + txtSubBankAccountNo);


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

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);


            radRecType = request.getParameter("radRecType");
            System.out.println("radRecType " + radRecType);


            txtReferenceNo = request.getParameter("txtReferenceNo");
            System.out.println("txtReferenceNo " + txtReferenceNo);

            if (!request.getParameter("txtReferenceDate").equalsIgnoreCase("")) {
                sd = request.getParameter("txtReferenceDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtReferenceDate = new Date(d.getTime());
            }
            System.out.println("txtReferenceDate " + txtReferenceDate);


            try {
                txtCheque_DD = request.getParameter("txtCheque_DD");
                Remittance_Type = txtCheque_DD;
            } catch (Exception e) {
                System.out.println("Failed to get option such as cheque or dd or ecs ");
            }


            try {
                txtCheque_DD_NO = request.getParameter("txtCheque_DD_NO");
            } catch (Exception e) {
                System.out.println("Failed to get Cheque or DD Number");
            }


            try {
                sd = request.getParameter("txtCheque_DD_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtCheque_DD_date = new Date(d.getTime());
                System.out.println("txtCheque_DD_date " + txtCheque_DD_date);
            } catch (Exception e) {
                System.out.println("Failed to Get Cheque or DD Date ");
            }


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


            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");

                cs =
  con.prepareCall("call FAS_FUND_REC_BYHO_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?::numeric,?,?,?::numeric,?::numeric)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setDate(3, txtCrea_date);
                cs.setInt(4, txtCash_year);
                cs.setInt(5, txtCash_Month_hid);
                cs.setInt(6, txtVoucher_No);
                cs.setString(7, radRecType);
                cs.setInt(8, txtCash_Acc_code);
                cs.setInt(9, txtBankId);
                cs.setInt(10, txtBranchId);
                cs.setLong(11, txtBankAccountNo);
                cs.setString(12, txtRemarks);
                cs.setDouble(13, txtAmount);
                cs.setString(14, txtReferenceNo);
                cs.setDate(15, txtReferenceDate);
                cs.setInt(16, txtCreditAccCode);
                cs.setInt(17, txtSubBankId);
                cs.setInt(18, txtSubBranchId);
                cs.setLong(19, txtSubBankAccountNo);
                cs.setString(20, txtCheque_DD);
                cs.setString(21, txtCheque_DD_NO);
                cs.setDate(22, txtCheque_DD_date);
                cs.setString(23, "insert");
                cs.registerOutParameter(24, java.sql.Types.NUMERIC);
                cs.registerOutParameter(6, java.sql.Types.NUMERIC);
                cs.setNull(24, java.sql.Types.NUMERIC);
                cs.setNull(6, java.sql.Types.NUMERIC);
                cs.setString(25, update_user);
                cs.setTimestamp(26, ts);
                cs.setInt(27, txtSub_Office_code);
                cs.setInt(28, cmb_HO_acc_unitid);
                cs.execute();
//                txtVoucher_No = cs.getInt(6);
//                int errcode = cs.getInt(24);
                txtVoucher_No = cs.getBigDecimal(6).intValue();
                int errcode = cs.getBigDecimal(24).intValue();
                
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The Fund Receipt Transaction failed ", "ok");
                    return;
                } else {


                    /**
                        * Auto Generation of Bank Remittance for ECS Transaction
                        */

                    System.out.println("Remittance_Type----><--" +
                                       Remittance_Type);
                    int Verified_Authority = 0;


                    if (Remittance_Type.equalsIgnoreCase("E")) {

                        UserProfile empProfile =
                            (UserProfile)session.getAttribute("UserProfile");
                        Verified_Authority = empProfile.getEmployeeId();
                        System.out.println("Verified_Authority::" +
                                           Verified_Authority);

                        System.out.println("inside E ");
                        cs1 =
                        		con.prepareCall("call FAS_ECS_REMITTANCE_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?)");
                        cs1.setInt(1, cmbAcc_UnitCode);
                        cs1.setInt(2, cmbOffice_code);
                        cs1.setInt(3, txtCash_year);
                        cs1.setInt(4, txtCash_Month_hid);
                        cs1.setDate(5, txtCrea_date);
                        cs1.setString(6, "B");
                        cs1.setDouble(7, txtAmount);
                        cs1.setInt(8, Verified_Authority);
                        cs1.setString(9, update_user);
                        cs1.registerOutParameter(10, java.sql.Types.VARCHAR);
                        cs1.setNull(10, java.sql.Types.VARCHAR);
                        cs1.execute();
                        //int err_code = cs1.getInt(10);
                        String err_code = cs1.getString(10);
                        if (err_code.equals("0")) {
                            //con.commit();
                            sendMessage(response,
                                        "The Fund Receipt Transaction Voucher Number '" +
                                        txtVoucher_No +
                                        "' has been Created Successfully ",
                                        "ok");
                        } else {
                            con.rollback();
                            sendMessage(response,
                                        "The Fund Receipt Creation Failed ",
                                        "ok");
                        }

                    } else {
                        con.commit();
                        sendMessage(response,
                                    "The Fund Receipt Transaction Voucher Number '" +
                                    txtVoucher_No +
                                    "' has been Created Successfully ", "ok");
                    }
                }
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("excepton in rollback");
                }

                sendMessage(response, "The Fund Receipt Transaction Failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("exception in autocommit");
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

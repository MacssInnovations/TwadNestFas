package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

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

public class Fund_Receipt_ListAll_byHO extends HttpServlet {
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
        String sql = "", txtCreat_By_Module = "", cmbStatus = "";

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
        txtCreat_By_Module = request.getParameter("txtCreat_By_Module");
        System.out.println("testing.................");

        cmbStatus = request.getParameter("cmbStatus");

        System.out.println("cmbStatus.." + cmbStatus);
        
        //added as new option All/Auto/Manual on 16/1/2013
        String txtTypes = "";
        String sql_types = "";

        txtTypes = request.getParameter("txtTypes");
System.out.println("Testing *****"+txtTypes);
        if (txtTypes.equalsIgnoreCase("AUTO")) {
            sql_types = "and AUTO_STATUS='Y' ";
        } else if (txtTypes.equalsIgnoreCase("MAN")) {
            sql_types = "and AUTO_STATUS is null ";
        } else if (txtTypes.equalsIgnoreCase("ALL")) {
            sql_types = " ";
        }


        if (strType.equalsIgnoreCase("searchByMonth")) {
            xml = "<response><command>searchByMonth</command>";

            sql =
 "select m.RECEIPT_NO,to_char(m.RECEIPT_DATE,'DD/MM/YYYY') as rec_date,m.RECEIPT_TYPE ,m.PARTICULARS,m.ho_account_no,m.office_account_no," +
   "trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT ,m.REF_NO,to_char(m.REF_DATE,'DD/MM/YYYY') as ref_date," +
   //"bk.BANK_NAME ||'-'|| br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BK_BR_CITY,  " +
   " bk.bank_short_name AS BK_BR_CITY,  " +
   "  m.received_from_office_id, " +
   "  m.RECEIVED_FROM_HO_UNIT_ID , " +
   "  CASE " +
   "    WHEN m.received_from_office_id=5000 " +
   "    THEN " +
   "      (SELECT trim(ACCOUNTING_UNIT_NAME) " +
   "      FROM FAS_MST_ACCT_UNITS u " +
   "      WHERE u.ACCOUNTING_UNIT_ID   =m.RECEIVED_FROM_HO_UNIT_ID " +
   "      AND ACCOUNTING_UNIT_OFFICE_ID=m.received_from_office_id " +
   "      ) " +
   "    ELSE " +
   "      (SELECT trim(ACCOUNTING_UNIT_NAME) " +
   "      FROM FAS_MST_ACCT_UNITS u " +
   "      WHERE ACCOUNTING_UNIT_OFFICE_ID=m.received_from_office_id " +
   "      ) " +
   "  END rec_name, " +
   " m.CHEQUE_OR_DD,m.CHEQUE_DD_NO,to_char(m.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date,m.DR_ACCOUNT_HEAD_CODE,m.CR_ACCOUNT_HEAD_CODE   " +
   "from FAS_FUND_RECEIPT_BY_HO m,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br" +
   " where m.ACCOUNTING_UNIT_ID=? and " +
   "m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=?  and m.RECEIPT_STATUS=? " +
   " and m.HO_BANK_ID=br.BANK_ID and m.HO_BRANCH_ID=br.BRANCH_ID and m.HO_BANK_ID=bk.BANK_ID " +   sql_types +
   " order by m.RECEIPT_NO,rec_date ";
            System.out.println(sql);
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, cmbStatus);
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml =xml + "<Rec_no>" + rs.getInt("RECEIPT_NO") + "</Rec_no>";
                    xml =
 xml + "<Rec_Date>" + rs.getString("rec_date") + "</Rec_Date>";
                    xml =
 xml + "<R_type>" + rs.getString("RECEIPT_TYPE") + "</R_type>";
                    xml =
 xml + "<che_or_DD>" + rs.getString("CHEQUE_OR_DD") + "</che_or_DD>" +
   "<che_DD_no>" + rs.getString("CHEQUE_DD_NO") + "</che_DD_no>" +
   "<che_DD_date>" + rs.getString("cheq_dd_date") + "</che_DD_date>";
                    xml =
 xml + "<Remak>" + rs.getString("PARTICULARS") + "</Remak>";
                    xml =
 xml + "<Tot_Amt>" + rs.getString("TOTAL_AMOUNT") + "</Tot_Amt>";
                    xml =
 xml + "<BK_BR_CITY>" + rs.getString("BK_BR_CITY").trim() + "</BK_BR_CITY>";
                    xml =
 xml + "<REF_NO>" + rs.getString("REF_NO") + "</REF_NO>";
                    xml=xml+"<DR_ACCOUNT_HEAD_CODE>"+rs.getInt("DR_ACCOUNT_HEAD_CODE")+"</DR_ACCOUNT_HEAD_CODE>";
                    xml=xml+"<CR_ACCOUNT_HEAD_CODE>"+rs.getInt("CR_ACCOUNT_HEAD_CODE")+"</CR_ACCOUNT_HEAD_CODE>";
                    xml=xml+"<Rec_office>"+rs.getString("rec_name")+"</Rec_office>";
                    xml =
 xml + "<ref_date>" + rs.getString("ref_date") + "</ref_date>";
                    xml =xml + "<ho_ac_no>" + rs.getLong("ho_account_no") + "</ho_ac_no>";
                    xml =xml + "<office_ac_no>" + rs.getLong("office_account_no") + "</office_ac_no>";
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
        System.out.println("here " + strType.equalsIgnoreCase("searchByDate"));
        if (strType.equalsIgnoreCase("searchByDate")) {
            xml = "<response><command>searchByDate</command>";
            System.out.println("here " +
                               strType.equalsIgnoreCase("searchByDate"));

            String[] sd = request.getParameter("txtFrom_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtFrom_date = new Date(d.getTime());
            System.out.println("from_date " + txtFrom_date);
            
            sd = request.getParameter("txtTo_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            txtTo_date = new Date(d.getTime());
            System.out.println("txtTo_date " + txtTo_date);

            sql =
 "select m.RECEIPT_NO,to_char(m.RECEIPT_DATE,'DD/MM/YYYY') as rec_date,m.RECEIPT_TYPE ,m.PARTICULARS,m.ho_account_no,m.office_account_no," +
   "trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT ,m.REF_NO,to_char(m.REF_DATE,'DD/MM/YYYY') as ref_date," +
  // "bk.BANK_NAME ||'-'|| br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BK_BR_CITY,  " +
   " bk.bank_short_name AS BK_BR_CITY,  " +
   "  m.received_from_office_id, " +
   "  m.RECEIVED_FROM_HO_UNIT_ID , " +
   "  CASE " +
   "    WHEN m.received_from_office_id=5000 " +
   "    THEN " +
   "      (SELECT trim(ACCOUNTING_UNIT_NAME) " +
   "      FROM FAS_MST_ACCT_UNITS u " +
   "      WHERE u.ACCOUNTING_UNIT_ID   =m.RECEIVED_FROM_HO_UNIT_ID " +
   "      AND ACCOUNTING_UNIT_OFFICE_ID=m.received_from_office_id " +
   "      ) " +
   "    ELSE " +
   "      (SELECT trim(ACCOUNTING_UNIT_NAME) " +
   "      FROM FAS_MST_ACCT_UNITS u " +
   "      WHERE ACCOUNTING_UNIT_OFFICE_ID=m.received_from_office_id " +
   "      ) " +
   "  END rec_name ," +
   " m.CHEQUE_OR_DD,m.CHEQUE_DD_NO,to_char(m.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date,m.DR_ACCOUNT_HEAD_CODE,m.CR_ACCOUNT_HEAD_CODE   " +
   "from FAS_FUND_RECEIPT_BY_HO m,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br" +
   "  where m.ACCOUNTING_UNIT_ID=? and  " +
   "m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=? and m.RECEIPT_DATE>=? " +
   "and m.RECEIPT_DATE<=? and m.RECEIPT_STATUS=? " +
   "and m.HO_BANK_ID=br.BANK_ID and m.HO_BRANCH_ID=br.BRANCH_ID and m.HO_BANK_ID=bk.BANK_ID" + sql_types +
   " order by m.RECEIPT_NO,rec_date ";
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setDate(5, txtFrom_date);
                ps.setDate(6, txtTo_date);
                ps.setString(7, cmbStatus);
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml =
 xml + "<Rec_no>" + rs.getInt("RECEIPT_NO") + "</Rec_no>";
                    xml =
 xml + "<Rec_Date>" + rs.getString("rec_date") + "</Rec_Date>";
                    xml =
 xml + "<R_type>" + rs.getString("RECEIPT_TYPE") + "</R_type>";
                    xml =
 xml + "<che_or_DD>" + rs.getString("CHEQUE_OR_DD") + "</che_or_DD>" +
   "<che_DD_no>" + rs.getString("CHEQUE_DD_NO") + "</che_DD_no>" +
   "<che_DD_date>" + rs.getString("cheq_dd_date") + "</che_DD_date>";
                    xml =
 xml + "<Remak>" + rs.getString("PARTICULARS") + "</Remak>";
                    xml =
 xml + "<Tot_Amt>" + rs.getString("TOTAL_AMOUNT") + "</Tot_Amt>";
                    xml =
 xml + "<BK_BR_CITY>" + rs.getString("BK_BR_CITY").trim() + "</BK_BR_CITY>";
                    xml =
 xml + "<REF_NO>" + rs.getString("REF_NO") + "</REF_NO>";
                    xml=xml+"<DR_ACCOUNT_HEAD_CODE>"+rs.getInt("DR_ACCOUNT_HEAD_CODE")+"</DR_ACCOUNT_HEAD_CODE>";
                    xml=xml+"<Rec_office>"+rs.getString("rec_name")+"</Rec_office>";
                    xml=xml+"<CR_ACCOUNT_HEAD_CODE>"+rs.getInt("CR_ACCOUNT_HEAD_CODE")+"</CR_ACCOUNT_HEAD_CODE>";
                    xml =
                    	xml + "<ref_date>" + rs.getString("ref_date") + "</ref_date>";
                    xml =xml + "<ho_ac_no>" + rs.getLong("ho_account_no") + "</ho_ac_no>";
                    xml =xml + "<office_ac_no>" + rs.getLong("office_account_no") + "</office_ac_no>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
            }
        }
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
    }
}

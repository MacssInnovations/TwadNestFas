package Servlets.FAS.FAS1.RemittanceSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ListAllBank_Remittance extends HttpServlet {
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
        Statement stmt = null;
        PreparedStatement ps = null, ps2 = null;
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
        String sql = "", txtRemittance_type = "";


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
        txtRemittance_type = request.getParameter("txtRemittance_type");
        String cmbStatus = request.getParameter("cmbStatus");

        if (strType.equalsIgnoreCase("searchByMonth")) {
            xml = "<response><command>searchByMonth</command>";
            /*sql="select * from"+
                " ("+
                " select challan_no,to_char( challan_date,'DD/MM/YYYY') as chal_date,amount_remitted,"+
                " remittance_type,accounting_unit_id from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and "+
                " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REMITTANCE_TYPE=? and "+
                " STATUS is null"+
                " )a"+
                " left join "+
                " ("+
                " select accounting_unit_id,to_char( RECEIPT_DATE,'DD/MM/YYYY') as rec_date,receipt_no,"+
                " receipt_type, challan_no from fas_receipt_master where"+
                " ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and "+
                " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_TYPE=? and "+
                " REMITTANCE_STATUS='Y' and RECEIPT_STATUS!='C'"+
                " )b"+
                " on a.challan_no=b.challan_no and a.chal_date=b.rec_date and a.accounting_unit_id=b.accounting_unit_id"+
                " and a.remittance_type=b.receipt_type";*/
            if(cmbStatus.equalsIgnoreCase("Y"))
            {
            sql =
 "select challan_no,to_char( challan_date,'DD/MM/YYYY') as chal_date,amount_remitted," +
   "  remittance_type,accounting_unit_id from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and " +
   "  CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REMITTANCE_TYPE=? and " +
   "  (STATUS='Y' or STATUS is null)";
            }
            else
            {
            sql =
            		 "select challan_no,to_char( challan_date,'DD/MM/YYYY') as chal_date,amount_remitted," +
            		   "  remittance_type,accounting_unit_id from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and " +
            		   "  CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and REMITTANCE_TYPE=? and " +
            		   "  STATUS='N'";
            }
            /*sql="select rec.RECEIPT_NO,to_char(rec.RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,rem.CHALLAN_NO,to_char(rem.CHALLAN_DATE,'DD/MM/YYYY') as challan_date ,rem.AMOUNT_REMITTED," +
            "rem.VERIFIED_AUTHORITY,emp.EMPLOYEE_NAME from FAS_REMITTANCE rem,HRM_MST_EMPLOYEES emp," +
            "FAS_RECEIPT_MASTER rec  where rem.ACCOUNTING_UNIT_ID=? and rem.ACCOUNTING_FOR_OFFICE_ID=? and rem.CASHBOOK_YEAR=? " +
            "and rem.CASHBOOK_MONTH=? and rem.REMITTANCE_TYPE=? and rem.VERIFIED='Y' and rem.VERIFIED_AUTHORITY=emp.EMPLOYEE_ID " +
            "and rec.RECEIPT_TYPE=rem.REMITTANCE_TYPE and rec.ACCOUNTING_FOR_OFFICE_ID=rem.ACCOUNTING_FOR_OFFICE_ID and " +
            "rem.ACCOUNTING_UNIT_ID=rec.ACCOUNTING_UNIT_ID and rem.CHALLAN_NO=rec.CHALLAN_NO and rem.CHALLAN_DATE=rec.CHALLAN_DATE";*/
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, txtRemittance_type);
//                ps.setString(6, cmbStatus);
                /* ps.setInt(6,cmbAcc_UnitCode);
               ps.setInt(7,cmbOffice_code);
               ps.setInt(8,txtCB_Year);
               ps.setInt(9,txtCB_Month);
               ps.setString(10,txtRemittance_type);*/

                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    // xml=xml+"<pay_no>"+rs.getInt("receipt_no")+"</pay_no>" +
                    //"<pay_date>"+rs.getString("rec_date")+"</pay_date>" +
                    xml =
 xml + "<Challan_no>" + rs.getInt("challan_no") + "</Challan_no>" +
   "<challan_date>" + rs.getString("chal_date") + "</challan_date>" +
   "<Amount>" + rs.getString("AMOUNT_REMITTED") + "</Amount>";
                    // "<userName>"+rs.getString("EMPLOYEE_NAME")+"</userName>";
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
            /* sql="select * from"+
                " ("+
                " select challan_no,to_char( challan_date,'DD/MM/YYYY') as chal_date,amount_remitted,"+
                " remittance_type,accounting_unit_id from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and "+
                " REMITTANCE_DATE>=? and REMITTANCE_DATE<=? and REMITTANCE_TYPE=? and "+
                " STATUS is null"+
                " )a"+
                " left join "+
                " ("+
                " select accounting_unit_id,to_char( RECEIPT_DATE,'DD/MM/YYYY') as rec_date,receipt_no,"+
                " receipt_type, challan_no from fas_receipt_master where"+
                " ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and "+
                " RECEIPT_DATE>=? and RECEIPT_DATE<=? and RECEIPT_TYPE=? and"+
                " REMITTANCE_STATUS='Y' and RECEIPT_STATUS!='C'"+
                " )b"+
                " on a.challan_no=b.challan_no and a.chal_date=b.rec_date and a.accounting_unit_id=b.accounting_unit_id"+
                " and a.remittance_type=b.receipt_type";*/
            sql =
 "select challan_no,to_char( challan_date,'DD/MM/YYYY') as chal_date,amount_remitted," +
   " remittance_type,accounting_unit_id from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and " +
   " REMITTANCE_DATE>=? and REMITTANCE_DATE<=? and REMITTANCE_TYPE=? and " +
   " STATUS=?";


            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                //ps.setInt(3,txtCB_Year);
                //ps.setInt(4,txtCB_Month);
                ps.setDate(3, txtFrom_date);
                ps.setDate(4, txtTo_date);
                ps.setString(5, txtRemittance_type);
                ps.setString(6, cmbStatus);
                /*  ps.setInt(6,cmbAcc_UnitCode);
                   ps.setInt(7,cmbOffice_code);
                   ps.setDate(8,txtFrom_date);
                   ps.setDate(9,txtTo_date);
                   ps.setString(10,txtRemittance_type);*/
                System.out.println("sql++++" + sql);
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    // xml=xml+"<pay_no>"+rs.getInt("receipt_no")+"</pay_no>" +
                    // "<pay_date>"+rs.getString("rec_date")+"</pay_date>" +
                    xml =
 xml + "<Challan_no>" + rs.getInt("challan_no") + "</Challan_no>" +
   "<challan_date>" + rs.getString("chal_date") + "</challan_date>" +
   "<Amount>" + rs.getString("AMOUNT_REMITTED") + "</Amount>";
                    //"<userName>"+rs.getString("EMPLOYEE_NAME")+"</userName>";
                    xml = xml + "</leng>";
                    count++;
                }
                // System.out.println("sql++++2222222222"+sql);
                if (count == 0) {
                    xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
            }
            //System.out.println("sql++++11111111"+sql);
        }
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
    }
}

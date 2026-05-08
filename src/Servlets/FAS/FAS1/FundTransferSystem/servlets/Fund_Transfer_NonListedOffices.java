package Servlets.FAS.FAS1.FundTransferSystem.servlets;

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

public class Fund_Transfer_NonListedOffices extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
System.out.println("hhhhhhhhhhhhhhhhhh");
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


      /*  try {
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
        System.out.println("cmbOffice_code " + cmbOffice_code);*/
        System.out.println("strtype  " + strType);
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);
       
       
        if (strType.equalsIgnoreCase("searchByMonth")) {
        
            xml = "<response><command>searchByMonth</command>";

//            sql = "SELECT ACCOUNTING_UNIT_ID,\n" + 
//            "  ACCOUNTING_UNIT_NAME\n" + 
//            " FROM fas_mst_acct_units\n" + 
//            " WHERE ACCOUNTING_UNIT_ID NOT IN\n" + 
//            "  (SELECT ACCOUNTING_UNIT_ID\n" + 
//            "  FROM FAS_FUND_TRF_FROM_OFFICE\n" + 
//            "  WHERE CASHBOOK_YEAR=?\n" + 
//            "  AND CASHBOOK_MONTH =?\n" + 
//            "  )\n" + 
//            " AND ACCOUNTING_UNIT_ID !=5\n" + 
//            " ORDER BY ACCOUNTING_UNIT_ID ";

          /*  sql="SELECT unit1.ACCOUNTING_UNIT_ID,\n" + 
            "  (SELECT COUNT(*)\n" + 
            "  FROM fas_receipt_master re\n" + 
            "  WHERE receipt_type   IN ('CR','BR')\n" + 
            "  AND receipt_status    ='L'\n" + 
            "  AND remittance_status ='Y'\n" + 
            "  AND re.accounting_unit_id=unit1.ACCOUNTING_UNIT_ID\n" + 
            "  AND cashbook_year     =?\n" + 
            "  AND cashbook_month    =?\n" + 
            "  ) AS count1,\n" + 
            "  unit1.ACCOUNTING_UNIT_NAME\n" + 
            " FROM fas_mst_acct_units unit1\n" + 
            " WHERE unit1.ACCOUNTING_UNIT_ID NOT IN\n" + 
            "  (SELECT ACCOUNTING_UNIT_ID\n" + 
            "  FROM FAS_FUND_TRF_FROM_OFFICE\n" + 
            "  WHERE CASHBOOK_YEAR=?\n" + 
            "  AND CASHBOOK_MONTH =?\n" + 
            "  )\n" + 
            " AND unit1.ACCOUNTING_UNIT_ID !=5\n" + 
            " ORDER BY unit1.ACCOUNTING_UNIT_ID";*/
       //    joan Change on 12 Dec 2014\
            sql="SELECT ACCOUNTING_UNIT_ID, " +
            		"  ACCOUNTING_UNIT_NAME " +
            		"FROM fas_mst_acct_units " +
            		"WHERE ACCOUNTING_UNIT_ID NOT IN " +
            		"  (SELECT ACCOUNTING_UNIT_ID " +
            		"  FROM FAS_FUND_TRF_FROM_OFFICE " +
            		"  WHERE CASHBOOK_YEAR=? " +
            		"  AND CASHBOOK_MONTH =? " +
            		"  ) " +
            		" AND ACCOUNTING_UNIT_ID !=5 " +
            		" AND  STATUS           IS NULL " +
            		" AND DATE_OF_CLOSURE    IS NULL  " +
            		" ORDER BY ACCOUNTING_UNIT_ID";
            try {
                int count = 0;
                System.out.println("sql :: "+sql);
                ps = con.prepareStatement(sql);
              
                ps.setInt(1, txtCB_Year);
                ps.setInt(2, txtCB_Month);
           /*     ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);*/
               rs = ps.executeQuery();
             
                while (rs.next()) {
               
                    xml = xml + "<leng>";
                    xml = xml + "<unit_idone>" + rs.getInt("ACCOUNTING_UNIT_ID") + "</unit_idone>";
                    xml = xml + "<unit_name>" + rs.getString("ACCOUNTING_UNIT_NAME") + "</unit_name>";
               //    xml = xml + "<countList>" + rs.getInt("count1") + "</countList>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
                }
                else {
                    xml =xml+"<flag>success</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
            }

        }
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

            sql = "SELECT ACCOUNTING_UNIT_ID,\n" + 
            "  ACCOUNTING_UNIT_NAME\n" + 
            " FROM fas_mst_acct_units\n" + 
            " WHERE ACCOUNTING_UNIT_ID NOT IN\n" + 
            "  (SELECT ACCOUNTING_UNIT_ID\n" + 
            "  FROM FAS_FUND_TRF_FROM_OFFICE\n" + 
            "  WHERE DATE_OF_TRANSFER between ? and ? \n" + 
            "  )\n" + 
            " AND ACCOUNTING_UNIT_ID !=5 " +
    		" AND  STATUS           IS NULL " +
    		" AND DATE_OF_CLOSURE    IS NULL  " +
    		" ORDER BY ACCOUNTING_UNIT_ID";
            System.out.println(sql);
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setDate(1, txtFrom_date);
                ps.setDate(2, txtTo_date);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml =  xml + "<unit_idone>" + rs.getInt("ACCOUNTING_UNIT_ID") + "</unit_idone>";
                    xml =  xml + "<unit_name>" + rs.getString("ACCOUNTING_UNIT_NAME") + "</unit_name>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
                }
                else {
                    xml =
                    "<response><command>searchByMonth</command><flag>success</flag>";
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


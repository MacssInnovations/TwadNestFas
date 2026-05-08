package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class BankBalanceMonitorOperNew extends HttpServlet {
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
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        //String xml="";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
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

        int txtBankId = 0, cmbAcc_UnitCode = 0, txtBranchId =
            0, txtAcc_HeadCode = 0, radDefault = 0;
        String txtOperation_mode = "", txtBankAcc_type = "", txtRemarks =
            "", cmbModule = "", rad_CR_DR = "";
        long txtBankAccountNo = 0;

        //String update_user="x";
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        try {
            txtBankId = Integer.parseInt(request.getParameter("txtBankId"));
        } catch (Exception e) {
            System.out.println("Exception to catch bank id ");
        }

        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbAcc_UnitCode ");
        }

        try {
            txtBranchId =
                    Integer.parseInt(request.getParameter("txtBranchId"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtBranchId ");
        }

        txtOperation_mode = request.getParameter("txtOperation_mode");
        txtBankAcc_type = request.getParameter("txtBankAcc_type");
        try {
            txtBankAccountNo =
                    Long.parseLong(request.getParameter("txtBankAccountNo"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtBankAccountNo ");
        }

        /*  try{
        txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
        }catch(Exception e){System.out.println("Exception to catch txtAcc_HeadCode ");}


        cmbModule=request.getParameter("cmbModule");

        rad_CR_DR=request.getParameter("rad_CR_DR");
        txtRemarks=request.getParameter("txtRemarks");
        try
        {
        radDefault=Integer.parseInt(request.getParameter("radDefault"));
        }catch(NumberFormatException e)
        {
        System.out.println("Exception radDefault.."+e);
        }
       */


        System.out.println("after getting parameters");

        if (strCommand.equalsIgnoreCase("loadbankdeatils")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>loadbankdeatils</command>";
            int bani_id_temp = 0;
            String operation_mode_temp = "";

            try {
                ps =
  con.prepareStatement("select distinct bb.BANK_ID,bb.BRANCH_ID,bk.BANK_NAME,br.BRANCH_NAME ||'-'||coalesce (br.CITY_TOWN_NAME,'')" +
                       " AS BRANCH_CITY,bb.BANK_AC_TYPE_ID,bb.AC_OPERATIONAL_MODE_ID,bb.REMARKS,bb.AC_OPENING_DATE," +
                       " bb.INITIAL_DEPOSIT_AMT,bb.OPENING_BALANCE,bb.BALANCE_DATE,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE," +
                       " N.AC_HEAD_CODE" + " from " +
                       " FAS_MST_BANK_BALANCE bb,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t," +
                       " FAS_MST_AC_OPER_MODES m,fas_office_bank_ac_current N " +
                       " where bb.ACCOUNTING_UNIT_ID=? and bb.BANK_AC_NO=? and " +
                       " t.ACCOUNT_TYPE_ID=bb.BANK_AC_TYPE_ID and bb.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID " +
                       " and bb.BANK_ID=br.BANK_ID and bb.BRANCH_ID=br.BRANCH_ID" +
                       " and bb.BANK_ID=bk.BANK_ID AND N.ACCOUNTING_UNIT_ID=? AND bb.bank_id=N.bank_id AND" +
                       " bb.branch_id=N.branch_id AND N.AC_OPERATIONAL_MODE_ID='OPR' AND " +
                       " bb.BANK_AC_TYPE_ID= N.BANK_AC_TYPE_ID and bb.BANK_AC_NO=? and bk.bank_id=N.bank_id");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setLong(2, txtBankAccountNo);
                ps.setInt(3, cmbAcc_UnitCode);
                ps.setLong(4, txtBankAccountNo);
                rs = ps.executeQuery();

                if (rs.next()) {
                    java.sql.Date dd = rs.getDate("AC_OPENING_DATE");
                    java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("dd/MM/yyyy");
                    String dispdate1 = sdf.format(dd);
                    java.sql.Date dd1 = rs.getDate("BALANCE_DATE");
                    java.text.SimpleDateFormat sdf1 =
                        new java.text.SimpleDateFormat("dd/MM/yyyy");
                    String dispdate2 = sdf1.format(dd1);

                    // System.out.println("Date is ________"+d.format(rs.getString("AC_OPENING_DATE")));

                    xml = xml + "<flag>success</flag>";
                    bani_id_temp = rs.getInt("BANK_ID");
                    operation_mode_temp =
                            rs.getString("AC_OPERATIONAL_MODE_ID");
                    xml =
 xml + "<BANK_ID>" + rs.getInt("BANK_ID") + "</BANK_ID>";
                    xml =
 xml + "<BANK_NAME>" + rs.getString("BANK_NAME") + "</BANK_NAME>";
                    xml =
 xml + "<BRANCH_ID>" + rs.getInt("BRANCH_ID") + "</BRANCH_ID>";
                    xml =
 xml + "<BRANCH_CITY>" + rs.getString("BRANCH_CITY") + "</BRANCH_CITY>";
                    xml =
 xml + "<BANK_AC_TYPE_ID>" + rs.getString("BANK_AC_TYPE_ID") +
   "</BANK_AC_TYPE_ID>";
                    xml =
 xml + "<ACCOUNT_TYPE>" + rs.getString("ACCOUNT_TYPE") + "</ACCOUNT_TYPE>";
                    xml =
 xml + "<AC_OPERATIONAL_MODE_ID>" + rs.getString("AC_OPERATIONAL_MODE_ID") +
   "</AC_OPERATIONAL_MODE_ID>";
                    xml =
 xml + "<AC_OPERATIONAL_MODE>" + rs.getString("AC_OPERATIONAL_MODE") +
   "</AC_OPERATIONAL_MODE>";

                    xml = xml + "<OPEN_DATE>" + dispdate1 + "</OPEN_DATE>";
                    xml =
 xml + "<INIT_AMT>" + rs.getInt("INITIAL_DEPOSIT_AMT") + "</INIT_AMT>";
                    xml = xml + "<BAL_DATE>" + dispdate2 + "</BAL_DATE>";
                    xml =
 xml + "<OPEN_BAL>" + rs.getInt("OPENING_BALANCE") + "</OPEN_BAL>";
                    xml =
 xml + "<ACCHEAD>" + rs.getInt("AC_HEAD_CODE") + "</ACCHEAD>";
                    xml =
 xml + "<REMARK>" + rs.getString("REMARKS") + "</REMARK>";
                } else
                    xml = xml + "<flag>failure</flag>";
                ps.close();
                rs.close();


            } catch (Exception e) {
                System.out.println("Finding Branch failed due to exception" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }


    }
}

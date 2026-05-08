package Servlets.FAS.FAS1.MonthEnd.servlets;

import Servlets.FAS.FAS1.CommonClass.ConvertDate;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class openingBalance_sl_servlet
 */
public class openingBalance_sl_servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private static final long serialVersionUID = 1L;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null,rs3=null;
    PreparedStatement ps1 = null,ps3=null,ps4=null;
    ResultSet rs1 = null,rs4=null;
    PreparedStatement ps2 = null;
    ResultSet rs2 = null;

    public openingBalance_sl_servlet() {
        super();
      
    }


    public void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
     
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) 
    throws ServletException, IOException {
        response.setHeader("cache-control","no-cache");
         String CONTENT_TYPE = "text/xml; charset=windows-1252";
         response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String cmd = request.getParameter("command");

        ConvertDate cc = new ConvertDate();

        System.out.println(cmd);

        int sl_type_code=0,sl_code=0;
        String xml = "";
        try {
            ResourceBundle rsb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rsb.getString("Config.DSN");
            String strhostname = rsb.getString("Config.HOST_NAME");
            String strportno = rsb.getString("Config.PORT_NUMBER");
            String strsid = rsb.getString("Config.SID");
            String strdbusername = rsb.getString("Config.USER_NAME");
            String strdbpassword = rsb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        if (cmd.equalsIgnoreCase("add")) {
            java.sql.Date debitdate1 = null;
            String txtDrLUpdate1 = request.getParameter("txtDrLUpdate");

            debitdate1 = cc.convert(txtDrLUpdate1);
            System.out.println("date****************************************" +
                               debitdate1);

            xml = xml + "<response><command>add</command>";
            String accno = request.getParameter("cmbAcc_UnitCode");
            System.out.println(accno);
            int accno1 = Integer.parseInt(accno);
            System.out.println(accno1);

            String officeid = request.getParameter("cmbOffice_code");
            System.out.println(officeid);
            int officeid1 = Integer.parseInt(officeid);

            String eid = request.getParameter("txtFinanYr");
            System.out.println(eid);

            String cyear = request.getParameter("txtCB_Year");
            System.out.println(cyear);
            int cash_year = Integer.parseInt(cyear);
            System.out.println(cash_year);

            String cmonth = request.getParameter("txtCB_Month");
            int cash_month = Integer.parseInt(cmonth);
            System.out.println(cash_month);

            String debit = request.getParameter("txtDebit");
            double debit1 = Double.parseDouble(debit);
            System.out.println(debit1);


            String credit = request.getParameter("txtCredit");
            double credit1 = Double.parseDouble(credit);
            System.out.println(credit1);


            String headcode = request.getParameter("txtAcc_HeadCode");
            int code = Integer.parseInt(headcode);
            System.out.println(code);


            String profile = (String)session.getAttribute("UserId");
            //int employeeid=profile.getEmployeeId();
            System.out.println("values..............................." + profile);
                       try{        
             sl_type_code = Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
            catch(Exception e){
                System.out.println("Exception in sl_type"+e);
            }
            
            try{
             sl_code = Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
            }
            catch(Exception e1){
                System.out.println("Exception in sl_code"+e1);
            }
            java.sql.Date debitdate = null;
            java.sql.Date creaditdate = null;
            String txtDrLUpdate = request.getParameter("txtDrLUpdate");
            String txtCrLUpdate = request.getParameter("txtCrLUpdate");
            debitdate = cc.convert(txtDrLUpdate);
            creaditdate = cc.convert(txtCrLUpdate);

                                  
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            System.out.println("values..............................." + ts);


            try {
System.out.println("inside try");
                ps1 = con.prepareStatement("select * from FAS_SL_UPTO_DATA where ACCOUNTING_UNIT_ID='" +accno1 + "'and ACCOUNTING_FOR_OFFICE_ID='" + officeid1 +"' and YEAR='" + cash_year + "' and MONTH='" +cash_month + "' and ACCOUNT_HEAD_CODE='" + code + "' ");
                rs = ps1.executeQuery();
                System.out.println("result"+rs);
                if (rs.next()) {
                    xml = xml + "<flag>exists</flag>";
                }

                else {
                System.out.println("elseeeeeeeeee");
                    ps = con.prepareStatement("insert into FAS_SL_UPTO_DATA(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,YEAR,MONTH,ACCOUNT_HEAD_CODE,UPTO_DEBIT_BALANCE,UPTO_CREDIT_BALANCE,DR_UPDATE_LAST_DATE,CR_UPDATE_LAST_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, accno1);
                    ps.setInt(2, officeid1);
                    ps.setString(3, eid);
                    ps.setInt(4, cash_year);
                    ps.setInt(5, cash_month);
                    ps.setInt(6, code);
                    ps.setDouble(7, debit1);
                    ps.setDouble(8, credit1);
                    ps.setDate(9, debitdate);
                    ps.setDate(10, creaditdate);
                    ps.setString(11, profile);
                    ps.setTimestamp(12, ts);
                    ps.setInt(13, sl_type_code);
                    ps.setInt(14, sl_code);
                    ps.executeUpdate();
                    xml = xml + "<flag>success</flag>";

                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception in add::::::::"+e);
            }
            xml = xml + "</response>";
            System.out.println(xml);

        } else if (cmd.equalsIgnoreCase("verify")) {

            xml = xml + "<response><command>verify</command>";
            String accno = request.getParameter("cmbAcc_UnitCode");
            System.out.println(accno);
            int accno1 = Integer.parseInt(accno);
            System.out.println(accno1);

            String officeid = request.getParameter("cmbOffice_code");
            System.out.println(officeid);
            int officeid1 = Integer.parseInt(officeid);


            String cyear = request.getParameter("txtCB_Year");
            System.out.println(cyear);
            int cash_year = Integer.parseInt(cyear);
            System.out.println(cash_year);

            String cmonth = request.getParameter("txtCB_Month");
            int cash_month = Integer.parseInt(cmonth);
            System.out.println(cash_month);


            String headcode = request.getParameter("txtAcc_HeadCode");
            int code = Integer.parseInt(headcode);
            System.out.println(code);
            try {

                ps1 =
    con.prepareStatement("select * from FAS_SL_UPTO_DATA where ACCOUNTING_UNIT_ID='" +
                      accno1 + "'and ACCOUNTING_FOR_OFFICE_ID='" + officeid1 +
                      "' and YEAR='" + cash_year + "' and MONTH='" +
                      cash_month + "' and ACCOUNT_HEAD_CODE='" + code + "' ");
                rs = ps1.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>exists</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
                }

            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e);
            }
            xml = xml + "</response>";
            System.out.println(xml);


        } else if (cmd.equalsIgnoreCase("update")) {
            xml = xml + "<response><command>update</command>";
            String accno = request.getParameter("cmbAcc_UnitCode");
            System.out.println(accno);
            int accno1 = Integer.parseInt(accno);
            System.out.println(accno1);

            String officeid = request.getParameter("cmbOffice_code");
            System.out.println(officeid);
            int officeid1 = Integer.parseInt(officeid);

            String eid = request.getParameter("txtFinanYr");
            System.out.println(eid);

            String cyear = request.getParameter("txtCB_Year");
            System.out.println(cyear);
            int cash_year = Integer.parseInt(cyear);
            System.out.println(cash_year);

            String cmonth = request.getParameter("txtCB_Month");
            int cash_month = Integer.parseInt(cmonth);
            System.out.println(cash_month);

            String debit = request.getParameter("txtDebit");
            double debit1 = Double.parseDouble(debit);
            System.out.println(debit1);


            String credit = request.getParameter("txtCredit");
            double credit1 = Double.parseDouble(credit);
            System.out.println(credit1);

            String headcode = request.getParameter("txtAcc_HeadCode");
            int code = Integer.parseInt(headcode);
            System.out.println(code);

            try{        
            sl_type_code = Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
            catch(Exception e){
            System.out.println("Exception in sl_type"+e);
            }
            
            try{
            sl_code = Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
            }
            catch(Exception e1){
            System.out.println("Exception in sl_code"+e1);
            }

            String profile = (String)session.getAttribute("UserId");
            //int employeeid=profile.getEmployeeId();
            System.out.println("values..............................." +
                               profile);


            java.sql.Date debitdate = null;
            java.sql.Date creaditdate = null;
            String txtDrLUpdate = request.getParameter("txtDrLUpdate");
            String txtCrLUpdate = request.getParameter("txtCrLUpdate");
            debitdate = cc.convert(txtDrLUpdate);
            creaditdate = cc.convert(txtCrLUpdate);

           
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            System.out.println("values..............................." + ts);
            try {

                ps =
    con.prepareStatement("update FAS_SL_UPTO_DATA set UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=?,DR_UPDATE_LAST_DATE=?,CR_UPDATE_LAST_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,FINANCIAL_YEAR=?,SUB_LEDGER_TYPE_CODE=?,SUB_LEDGER_CODE=? where ACCOUNTING_UNIT_ID='" +
                       accno1 + "'and ACCOUNTING_FOR_OFFICE_ID='" + officeid1 +
                       "' and YEAR='" + cash_year + "' and MONTH='" +
                       cash_month + "' and ACCOUNT_HEAD_CODE='" + code + "'");

                System.out.println("update FAS_SL_UPTO_DATA set UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=?,DR_UPDATE_LAST_DATE=?,CR_UPDATE_LAST_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,FINANCIAL_YEAR=? where=ACCOUNTING_UNIT_ID='" +
                                   accno1 + "'and ACCOUNTING_FOR_OFFICE_ID='" +
                                   officeid1 + "' and YEAR='" + cash_year +
                                   "' and MONTH='" + cash_month +
                                   "' and ACCOUNT_HEAD_CODE='" + code + "' ");
                ps.setDouble(1, debit1);
                System.out.println(debit1);
                ps.setDouble(2, credit1);
                System.out.println(credit1);
                ps.setDate(3, debitdate);
                System.out.println(debitdate);
                ps.setDate(4, creaditdate);
                System.out.println(creaditdate);
                ps.setString(5, profile);
                ps.setTimestamp(6, ts);
                ps.setString(7, eid);
                ps.setInt(8, sl_type_code);System.out.println("sl_type_code"+sl_type_code);
                ps.setInt(9, sl_code);System.out.println("sl_code"+sl_code);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";


            }


            catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
        } else if (cmd.equalsIgnoreCase("deleted")) {

            xml = xml + "<response><command>deleted</command>";
            String accno = request.getParameter("cmbAcc_UnitCode");
            System.out.println(accno);
            int accno1 = Integer.parseInt(accno);
            System.out.println(accno1);

            String officeid = request.getParameter("cmbOffice_code");
            System.out.println(officeid);
            int officeid1 = Integer.parseInt(officeid);


            String cyear = request.getParameter("txtCB_Year");
            System.out.println(cyear);
            int cash_year = Integer.parseInt(cyear);
            System.out.println(cash_year);

            String cmonth = request.getParameter("txtCB_Month");
            int cash_month = Integer.parseInt(cmonth);
            System.out.println(cash_month);

            String headcode = request.getParameter("txtAcc_HeadCode");
            int code = Integer.parseInt(headcode);
            System.out.println(code);


            try {
                ps =
    con.prepareStatement("delete from FAS_SL_UPTO_DATA where ACCOUNTING_UNIT_ID='" +
                       accno1 + "'and ACCOUNTING_FOR_OFFICE_ID='" + officeid1 +
                       "' and YEAR='" + cash_year + "' and MONTH='" +
                       cash_month + "' and ACCOUNT_HEAD_CODE='" + code + "'");

                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e);
            }
            xml = xml + "</response>";
            System.out.println(xml);
        } else if (cmd.equalsIgnoreCase("gridvalue")) {

            xml = xml + "<response><command>gridvalue</command>";
            String accno = request.getParameter("accountUnitId");
            int accno1 = Integer.parseInt(accno);
            System.out.println(accno1);
            String officeid = request.getParameter("accountOfficeId");
            System.out.println(officeid);
            int officeid1 = Integer.parseInt(officeid);

            try {

                ps =
    con.prepareStatement("select * from  FAS_SL_UPTO_DATA where ACCOUNTING_UNIT_ID='" +
                       accno1 + "'and ACCOUNTING_FOR_OFFICE_ID='" + officeid1 +
                       "' order By ACCOUNT_HEAD_CODE ASC ");
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    //System.out.println("id"+rs.getInt(5));
                    xml =
    xml + "<code>" + rs.getInt("ACCOUNT_HEAD_CODE") + "</code>";

                    ps1 =
    con.prepareStatement("select * from  COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE='" +
                      rs.getInt("ACCOUNT_HEAD_CODE") + "'");
                    rs1 = ps1.executeQuery();

                    if (rs1.next()) {
                        xml =
    xml + "<accname>" + rs1.getString("ACCOUNT_HEAD_DESC") + "</accname>";
                    }
                    double s1 = rs.getDouble("UPTO_DEBIT_BALANCE");
                    double s2 = rs.getDouble("UPTO_CREDIT_BALANCE");
                    // trim(to_char(amount,"99999999999999.99")) as amount
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    System.out.println(formatter.format(s1));
                    xml = xml + "<debit>" + formatter.format(s1) + "</debit>";
                    xml =
    xml + "<credit>" + formatter.format(s2) + "</credit>";
                    try {
                        if (rs.getDate("DR_UPDATE_LAST_DATE").equals(null)) {
                            xml = xml + "<flag>exception</flag>";
                        } else {
                            String ddate[] =
                                rs.getDate("DR_UPDATE_LAST_DATE").toString().split("-");
                            String ddate1 =
                                ddate[2] + "/" + ddate[1] + "/" + ddate[0];
                            xml =xml + "<debitdate>" + ddate1 + "</debitdate>";
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    try {
                        if (rs.getDate("CR_UPDATE_LAST_DATE").equals(null)) {
                            xml = xml + "<flag>exception</flag>";
                        } else {
                            String cdate[] =
                                rs.getDate("CR_UPDATE_LAST_DATE").toString().split("-");
                            String cdate1 =
                                cdate[2] + "/" + cdate[1] + "/" + cdate[0];
                            xml =
    xml + "<creditdate>" + cdate1 + "</creditdate>";
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                    try {
                        if (rs.getInt("SUB_LEDGER_TYPE_CODE")==0) {
                            xml = xml + "<flag>exception</flag>";
                        } else {
                        ps3=con.prepareStatement("select SUB_LEDGER_TYPE_DESC,SUB_LEDGER_TYPE_CODE from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE="+rs.getInt("SUB_LEDGER_TYPE_CODE"));
                            rs3=ps3.executeQuery();
                            while(rs3.next()) {
                                xml =xml + "<type_desc>" + rs3.getString("SUB_LEDGER_TYPE_DESC") + "</type_desc>";
                                xml =xml + "<type_code>" + rs3.getInt("SUB_LEDGER_TYPE_CODE") + "</type_code>";
                            }
                        //    xml =xml + "<creditdate>" + cdate1 + "</creditdate>";
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    try {
                        if (rs.getInt("SUB_LEDGER_CODE")==0) {
                            xml = xml + "<flag>exception</flag>";
                        } else {
                        ps4=con.prepareStatement("select sl_codename,sl_code from SL_TYPE_CODE_NAME_VIEW where sl_type="+rs.getInt("SUB_LEDGER_TYPE_CODE")+" and sl_code="+rs.getInt("SUB_LEDGER_CODE"));
                            rs4=ps4.executeQuery();
                            while(rs4.next()) 
                            {
                                xml =xml + "<code_desc>" + rs4.getString("sl_codename") + "</code_desc>";
                                xml =xml + "<sl_code>" + rs4.getInt("sl_code") + "</sl_code>";
                            }
                        
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }


            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e);
            }
            xml = xml + "</response>";
            System.out.println(xml);


        }


        out.write(xml);
    }


}

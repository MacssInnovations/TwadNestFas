package Servlets.FAS.FAS1.MonthEnd.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
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

import Servlets.Security.classes.UserProfile;

import Servlets.FAS.FAS1.CommonClass.ConvertDate;

/**
 * Servlet implementation class DrawingOfficerDetails
 */
public class OpeningBalance extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private static final long serialVersionUID = 1L;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    PreparedStatement ps1 = null;
    ResultSet rs1 = null;
    PreparedStatement ps2 = null;
    ResultSet rs2 = null;

    public OpeningBalance() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        // TODO Auto-generated method stub
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String cmd = request.getParameter("command");

        ConvertDate cc = new ConvertDate();

        System.out.println(cmd);


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
            System.out.println("values..............................." +
                               profile);


            java.sql.Date debitdate = null;
            java.sql.Date creaditdate = null;
            String txtDrLUpdate = request.getParameter("txtDrLUpdate");
            String txtCrLUpdate = request.getParameter("txtCrLUpdate");
            debitdate = cc.convert(txtDrLUpdate);
            creaditdate = cc.convert(txtCrLUpdate);

            //				    java.util.GregorianCalendar c2;
            //				
            //				    String txtDrLUpdate=request.getParameter("txtDrLUpdate");
            //				    String[] deb_Date= txtDrLUpdate.split("/");
            //					c2 = new java.util.GregorianCalendar(Integer.parseInt(deb_Date[2]),
            //				    Integer.parseInt(deb_Date[1]) - 1, Integer.parseInt(deb_Date[0]));
            //					java.util.Date ddd = c2.getTime();
            //					debitdate = new Date(ddd.getTime());
            //					System.out.println("values..............................."+debitdate);
            //					System.out.println("date****************************************"+debitdate);
            //				
            //					 String txtCrLUpdate=request.getParameter("txtCrLUpdate");
            //					    String[] cre_Date= txtCrLUpdate.split("/");
            //						c2 = new java.util.GregorianCalendar(Integer.parseInt(cre_Date[2]),
            //					    Integer.parseInt(cre_Date[1]) - 1, Integer.parseInt(cre_Date[0]));
            //						java.util.Date ddd1 = c2.getTime();
            //						creaditdate = new Date(ddd1.getTime());
            //
            //						System.out.println("values..............................."+creaditdate);
            //				
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            System.out.println("values..............................." + ts);


            try {

                ps1 =
 con.prepareStatement("select * from FAS_GL_UPTO_DATA where ACCOUNTING_UNIT_ID='" +
                      accno1 + "'and ACCOUNTING_FOR_OFFICE_ID='" + officeid1 +
                      "' and YEAR='" + cash_year + "' and MONTH='" +
                      cash_month + "' and ACCOUNT_HEAD_CODE='" + code + "' ");
                rs = ps1.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>exists</flag>";
                }

                else {
                    ps =
  con.prepareStatement("insert into FAS_GL_UPTO_DATA values(?,?,?,?,?,?,?,?,?,?,?,?)");
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

                    ps.executeUpdate();
                    xml = xml + "<flag>success</flag>";

                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e);
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
 con.prepareStatement("select * from FAS_GL_UPTO_DATA where ACCOUNTING_UNIT_ID='" +
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

            //				    java.util.GregorianCalendar c2;
            //				
            //				    String txtDrLUpdate=request.getParameter("txtDrLUpdate");
            //				    String[] deb_Date= txtDrLUpdate.split("/");
            //				    System.out.println(deb_Date.length);
            //					c2 = new java.util.GregorianCalendar(Integer.parseInt(deb_Date[2]),
            //				    Integer.parseInt(deb_Date[1]) - 1, Integer.parseInt(deb_Date[0]));
            //					java.util.Date ddd = c2.getTime();
            //					debitdate = new Date(ddd.getTime());
            //					System.out.println("values..............................."+debitdate);
            //				
            //				
            //					 String txtCrLUpdate=request.getParameter("txtCrLUpdate");
            //					    String[] cre_Date= txtCrLUpdate.split("/");
            //						c2 = new java.util.GregorianCalendar(Integer.parseInt(cre_Date[2]),
            //					    Integer.parseInt(cre_Date[1]) - 1, Integer.parseInt(cre_Date[0]));
            //						java.util.Date ddd1 = c2.getTime();
            //						creaditdate = new Date(ddd1.getTime());
            //
            //						System.out.println("values..............................."+creaditdate);
            //				
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            System.out.println("values..............................." + ts);
            try {

                //ps=con.prepareStatement("update FAS_GL_UPTO_DATA set UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=?,DR_UPDATE_LAST_DATE=?,CR_UPDATE_LAST_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,FINANCIAL_YEAR=? where=ACCOUNTING_UNIT_ID='"+accno1+"' and ACCOUNTING_FOR_OFFICE_ID='"+officeid1+"' and YEAR='"+cash_year+"' and MONTH='"+cash_month+"' and ACCOUNT_HEAD_CODE='"+code+"' ");
                ps =
  con.prepareStatement("update FAS_GL_UPTO_DATA set UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=?,DR_UPDATE_LAST_DATE=?,CR_UPDATE_LAST_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,FINANCIAL_YEAR=? where ACCOUNTING_UNIT_ID='" +
                       accno1 + "'and ACCOUNTING_FOR_OFFICE_ID='" + officeid1 +
                       "' and YEAR='" + cash_year + "' and MONTH='" +
                       cash_month + "' and ACCOUNT_HEAD_CODE='" + code + "'");

                System.out.println("update FAS_GL_UPTO_DATA set UPTO_DEBIT_BALANCE=?,UPTO_CREDIT_BALANCE=?,DR_UPDATE_LAST_DATE=?,CR_UPDATE_LAST_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,FINANCIAL_YEAR=? where=ACCOUNTING_UNIT_ID='" +
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
  con.prepareStatement("delete from FAS_GL_UPTO_DATA where ACCOUNTING_UNIT_ID='" +
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
  con.prepareStatement("select * from  FAS_GL_UPTO_DATA where ACCOUNTING_UNIT_ID='" +
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
                            xml =
 xml + "<debitdate>" + ddate1 + "</debitdate>";
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

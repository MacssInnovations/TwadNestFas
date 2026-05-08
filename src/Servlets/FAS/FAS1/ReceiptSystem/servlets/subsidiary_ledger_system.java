package Servlets.FAS.FAS1.ReceiptSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class subsidiary_ledger_system extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        response.setContentType(CONTENT_TYPE);
        String strCommand = "";

        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        ResultSet rs = null;

        String xml = "";
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
            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
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
            //con.setAutoCommit(false);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        Calendar c;
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtyear_update =
            0, txtAcc_HeadCode = 0, txtmon_update = 0, cmbSL_type =
            0, cmbSL_Code = 0, txtprj_id = 0, txtEmpID_trs = 0, txtvoc_no = 0;
        String txtAcc_HeadDesc = "", txtV_Type = "", txtOfficeID_trs =
            "", rad_sub_CR_DB = "", rad_CR_DB = "", cmbvoc_type =
            "", txtRemarks = "", rad_sub_CR_DR = "";
        double txtsub_Amount = 0, balance = 0.0, txtcl_bal = 0.0;
        Date txtRef_date = null, txtvoc_date = null;
        int voc_no = 0;


        // String update_user="twad10099";


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

        try {
            txtyear_update =
                    Integer.parseInt(request.getParameter("txtyear_update"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtyear_update " + txtyear_update);

        try {
            txtmon_update =
                    Integer.parseInt(request.getParameter("txtmon_update"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtmon_update " + txtmon_update);

        try {
            txtAcc_HeadCode =
                    Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtAcc_HeadCode " + txtAcc_HeadCode);

        try {
            txtAcc_HeadDesc = request.getParameter("txtAcc_HeadDesc");
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtAcc_HeadDesc " + txtAcc_HeadDesc);

        try {
            cmbSL_type = Integer.parseInt(request.getParameter("cmbSL_type"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbSL_type " + cmbSL_type);

        try {
            cmbSL_Code = Integer.parseInt(request.getParameter("cmbSL_Code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbSL_Code " + cmbSL_Code);

        /*try{txtOfficeID_trs=request.getParameter("txtOfficeID_trs");}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtOfficeID_trs "+txtOfficeID_trs);

            try{txtEmpID_trs=Integer.parseInt(request.getParameter("txtEmpID_trs"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtEmpID_trs "+txtEmpID_trs);*/

        try {
            txtprj_id = Integer.parseInt(request.getParameter("txtprj_id"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtprj_id " + txtprj_id);

        try {
            txtcl_bal = Double.parseDouble(request.getParameter("txtcl_bal"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtcl_bal " + txtcl_bal);

        try {
            rad_CR_DB = request.getParameter("rad_CR_DB");
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("rad_CR_DB " + rad_CR_DB);


        String[] sd = request.getParameter("txtRef_date").split("/");
        String Ref_date = request.getParameter("txtRef_date");
        System.out.println("txtRef_date " + txtRef_date);

        if (!Ref_date.equals("")) // if not a empty string, convert to SQL date
        {
            sd = request.getParameter("txtRef_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtRef_date = new Date(d.getTime());
        }
        System.out.println("after txtRef_date " + txtRef_date);

        int l1 = 0;

        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";


            try {

                ps =
  con.prepareStatement("Insert into FAS_SELF_BALANCE_MASTER(accounting_unit_id,accounting_for_office_id,cashbook_year, " +
                       " cashbook_month,account_head_code,sub_ledger_type_code,sub_ledger_code,project_id,closing_balance, " +
                       " closing_balance_dr_cr_ind,last_date_updated,updated_by_user_id,updated_date) " +
                       " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode); //account unit code
                ps.setInt(2, cmbOffice_code); //account for office code
                ps.setInt(3, txtyear_update); //year
                ps.setInt(4, txtmon_update); //month
                ps.setInt(5, txtAcc_HeadCode); //account head code
                ps.setInt(6, cmbSL_type); //sub ledger type
                ps.setInt(7, cmbSL_Code); //sub ledger code
                ps.setInt(8, txtprj_id); //project id
                ps.setDouble(9, txtcl_bal); //closing balance
                ps.setString(10, rad_CR_DB); //cr/db
                ps.setDate(11, txtRef_date);
                ps.setString(12, update_user);
                ps.setTimestamp(13, ts);

                l1 = ps.executeUpdate();
                System.out.println(l1);


            }

            catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            String Grid_V_type[] = request.getParameterValues("V_type");
            String Grid_CR_DR_type[] =
                request.getParameterValues("CR_DR_type");
            String Grid_V_no[] = request.getParameterValues("V_no");
            String Grid_V_date[] = request.getParameterValues("V_date");
            String Grid_sl_amt[] = request.getParameterValues("sl_amt");
            String Grid_remarks[] = request.getParameterValues("remarks");

            System.out.println("Grid_remarks" + txtRemarks);

            System.out.println("out " + Grid_V_type);

            System.out.println("Grid_V_type.length " + Grid_V_type.length);


            try {

                ps =
  con.prepareStatement("Insert into FAS_SUB_LEDGER_TRN(accounting_unit_id,accounting_for_office_id,cashbook_year,cashbook_month, " +
                       " account_head_code,sub_ledger_type_code,sub_ledger_code,voucher_type,voucher_no,cr_dr_indicator,voucher_date, " +
                       " amount,updated_by_user_id,updated_date,remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


                for (int k = 0; k < Grid_V_type.length; k++) {
                    System.out.println("Grid_V_type.length " +
                                       Grid_V_type.length);

                    try {
                        cmbvoc_type = Grid_V_type[k];
                    } catch (Exception e) {
                        System.out.println("exception in trans " + e);
                    }
                    rad_sub_CR_DR = Grid_CR_DR_type[k];
                    System.out.println("rad_sub_CR_DR :" + rad_sub_CR_DR);


                    try {
                        voc_no = Integer.parseInt(Grid_V_no[k]);
                    } catch (Exception e) {
                        System.out.println("exception in trans " + e);
                    }
                    try {
                        balance = Double.parseDouble(Grid_sl_amt[k]);
                    } catch (Exception e) {
                        System.out.println("exception in trans " + e);
                    }
                    cmbvoc_type = Grid_V_type[k];

                    System.out.println("Grid_V_type[k] " + Grid_V_type[k]);
                    System.out.println("Grid_CR_DR_type[k] " +
                                       Grid_CR_DR_type[k]);
                    System.out.println("Grid_V_no[k] " + voc_no);
                    System.out.println("Grid_V_date[k]" + Grid_V_date[k]);
                    System.out.println("Grid_sl_amt[k]" + balance);
                    System.out.println("Grid_remarks[k]" + Grid_remarks[k]);


                    txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                    txtRemarks = Grid_remarks[k];

                    System.out.println(txtsub_Amount);
                    System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);
                    System.out.println("Grid_remarks[k] " + Grid_remarks[k]);


                    if (!Grid_V_date[k].equalsIgnoreCase("")) {
                        sd = Grid_V_date[k].split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                        java.util.Date d = c.getTime();
                        txtvoc_date = new Date(d.getTime());
                    }


                    ps.setInt(1, cmbAcc_UnitCode); //account unit code
                    ps.setInt(2, cmbOffice_code); //account for office code
                    ps.setInt(3, txtyear_update); //year
                    ps.setInt(4, txtmon_update); //month
                    ps.setInt(5, txtAcc_HeadCode); //account head code
                    ps.setInt(6, cmbSL_type); //sub ledger type
                    ps.setInt(7, cmbSL_Code); //sub ledger code
                    ps.setString(8, cmbvoc_type); //project id
                    ps.setInt(9, voc_no); //closing balance
                    ps.setString(10, rad_sub_CR_DR); //cr/db
                    ps.setDate(11, txtvoc_date);
                    ps.setDouble(12, txtsub_Amount);
                    ps.setString(13, update_user);
                    ps.setTimestamp(14, ts);
                    ps.setString(15, txtRemarks);

                    int l2 = ps.executeUpdate();
                    System.out.println(l2);
                }

                sendMessage(response, "The data is inserted Successfully",
                            "ok");
                //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");


            }

            catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        else if (strCommand.equalsIgnoreCase("Update")) {
            //String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            //xml="<response><command>Detail</command>";
            System.out.println("inside update");
            int a = 0;


            try {
                ps1 =
 con.prepareStatement("update FAS_SELF_BALANCE_MASTER set PROJECT_ID=?,CLOSING_BALANCE=?," +
                      " CLOSING_BALANCE_DR_CR_IND=?,LAST_DATE_UPDATED=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?" +
                      " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?" +
                      " and SUB_LEDGER_TYPE_CODE=? and" +
                      " SUB_LEDGER_CODE=? and ACCOUNT_HEAD_CODE=?");

                System.out.println("master update");
                ps1.setInt(1, txtprj_id);
                ps1.setDouble(2, txtcl_bal);
                ps1.setString(3, rad_CR_DB);
                ps1.setDate(4, txtRef_date);
                ps1.setString(5, update_user);
                ps1.setTimestamp(6, ts);
                ps1.setInt(7, cmbAcc_UnitCode);
                ps1.setInt(8, cmbOffice_code);
                ps1.setInt(9, txtyear_update);
                ps1.setInt(10, txtmon_update);
                ps1.setInt(11, cmbSL_type);
                ps1.setInt(12, cmbSL_Code);
                ps1.setInt(13, txtAcc_HeadCode);

                a = ps1.executeUpdate();

                System.out.println(a);

                if (a > 0) {
                    int b = 0;
                    try {
                        ps2 =
 con.prepareStatement("delete from FAS_SUB_LEDGER_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and" +
                      " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? and ACCOUNT_HEAD_CODE=?");

                        ps2.setInt(1, cmbAcc_UnitCode);
                        ps2.setInt(2, cmbOffice_code);
                        ps2.setInt(3, txtyear_update);
                        ps2.setInt(4, txtmon_update);
                        ps2.setInt(5, cmbSL_type);
                        ps2.setInt(6, cmbSL_Code);
                        ps2.setInt(7, txtAcc_HeadCode);

                        b = ps2.executeUpdate();

                        System.out.println(b);


                        String Grid_V_type[] =
                            request.getParameterValues("type_vou");
                        String Grid_CR_DR_type[] =
                            request.getParameterValues("rad_CR_DR");
                        String Grid_V_no[] =
                            request.getParameterValues("no_vou");
                        String Grid_V_date[] =
                            request.getParameterValues("date_vou");
                        String Grid_sl_amt[] =
                            request.getParameterValues("amount");
                        String Grid_remarks[] =
                            request.getParameterValues("remar");

                        System.out.println("Grid_remarks" + txtRemarks);

                        System.out.println("out " + Grid_V_type);

                        System.out.println("Grid_V_type.length " +
                                           Grid_V_type.length);

                        if (b > 0) {
                            System.out.println("inside detail insert");

                            try {

                                ps =
  con.prepareStatement("Insert into FAS_SUB_LEDGER_TRN(accounting_unit_id,accounting_for_office_id,cashbook_year,cashbook_month, " +
                       " account_head_code,sub_ledger_type_code,sub_ledger_code,voucher_type,voucher_no,cr_dr_indicator,voucher_date, " +
                       " amount,updated_by_user_id,updated_date,remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                                System.out.println("after inserting");

                                for (int k = 0; k < Grid_V_type.length; k++) {
                                    System.out.println("Grid_V_type.length " +
                                                       Grid_V_type.length);

                                    try {
                                        cmbvoc_type = Grid_V_type[k];
                                    } catch (Exception e) {
                                        System.out.println("exception in trans " +
                                                           e);
                                    }
                                    rad_sub_CR_DR = Grid_CR_DR_type[k];
                                    System.out.println("rad_sub_CR_DR :" +
                                                       rad_sub_CR_DR);


                                    try {
                                        voc_no =
                                                Integer.parseInt(Grid_V_no[k]);
                                    } catch (Exception e) {
                                        System.out.println("exception in trans " +
                                                           e);
                                    }
                                    try {
                                        balance =
                                                Double.parseDouble(Grid_sl_amt[k]);
                                    } catch (Exception e) {
                                        System.out.println("exception in trans " +
                                                           e);
                                    }
                                    cmbvoc_type = Grid_V_type[k];

                                    System.out.println("Grid_V_type[k] " +
                                                       Grid_V_type[k]);
                                    System.out.println("Grid_CR_DR_type[k] " +
                                                       Grid_CR_DR_type[k]);
                                    System.out.println("Grid_V_no[k] " +
                                                       voc_no);
                                    System.out.println("Grid_V_date[k]" +
                                                       Grid_V_date[k]);
                                    System.out.println("Grid_sl_amt[k]" +
                                                       balance);
                                    System.out.println("Grid_remarks[k]" +
                                                       Grid_remarks[k]);


                                    txtsub_Amount =
                                            Double.parseDouble(Grid_sl_amt[k]);
                                    txtRemarks = Grid_remarks[k];

                                    System.out.println(txtsub_Amount);
                                    System.out.println("Grid_sl_amt[k] " +
                                                       Grid_sl_amt[k]);
                                    System.out.println("Grid_remarks[k] " +
                                                       Grid_remarks[k]);


                                    if (!Grid_V_date[k].equalsIgnoreCase("")) {
                                        sd = Grid_V_date[k].split("/");
                                        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                                        java.util.Date d = c.getTime();
                                        txtvoc_date = new Date(d.getTime());
                                    }


                                    ps.setInt(1,
                                              cmbAcc_UnitCode); //account unit code
                                    ps.setInt(2,
                                              cmbOffice_code); //account for office code
                                    ps.setInt(3, txtyear_update); //year
                                    ps.setInt(4, txtmon_update); //month
                                    ps.setInt(5,
                                              txtAcc_HeadCode); //account head code
                                    ps.setInt(6, cmbSL_type); //sub ledger type
                                    ps.setInt(7, cmbSL_Code); //sub ledger code
                                    ps.setString(8, cmbvoc_type); //project id
                                    ps.setInt(9, voc_no); //closing balance
                                    ps.setString(10, rad_sub_CR_DR); //cr/db
                                    ps.setDate(11, txtvoc_date);
                                    ps.setDouble(12, txtsub_Amount);
                                    ps.setString(13, update_user);
                                    ps.setTimestamp(14, ts);
                                    ps.setString(15, txtRemarks);

                                    int l2 = ps.executeUpdate();
                                    System.out.println(l2);
                                }
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }

                }
                sendMessage(response, "The data is Updated Successfully",
                            "ok");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        else if (strCommand.equalsIgnoreCase("Delete")) {
            response.setContentType(CONTENT_TYPE);
            int d = 0;
            System.out.println("inside delete part");

            try {
                ps3 =
 con.prepareStatement("delete from FAS_SELF_BALANCE_MASTER where ACCOUNTING_UNIT_ID=? and " +
                      " ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUB_LEDGER_TYPE_CODE=? and" +
                      " SUB_LEDGER_CODE=? and ACCOUNT_HEAD_CODE=?");

                ps3.setInt(1, cmbAcc_UnitCode);
                ps3.setInt(2, cmbOffice_code);
                ps3.setInt(3, txtyear_update);
                ps3.setInt(4, txtmon_update);
                ps3.setInt(5, cmbSL_type);
                ps3.setInt(6, cmbSL_Code);
                ps3.setInt(7, txtAcc_HeadCode);

                d = ps3.executeUpdate();

                System.out.println(d);

                if (d > 0) {

                    try {
                        ps4 =
 con.prepareStatement("delete from FAS_SUB_LEDGER_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and" +
                      " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? and ACCOUNT_HEAD_CODE=?");

                        ps4.setInt(1, cmbAcc_UnitCode);
                        ps4.setInt(2, cmbOffice_code);
                        ps4.setInt(3, txtyear_update);
                        ps4.setInt(4, txtmon_update);
                        ps4.setInt(5, cmbSL_type);
                        ps4.setInt(6, cmbSL_Code);
                        ps4.setInt(7, txtAcc_HeadCode);

                        int e = ps4.executeUpdate();

                        System.out.println(e);

                    } catch (SQLException e1) {
                        System.out.println(e1.getMessage());
                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }
                }
                sendMessage(response, "The data is Deleted Successfully",
                            "ok");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
            System.out.println("Excep" + e);
        }
    }
}



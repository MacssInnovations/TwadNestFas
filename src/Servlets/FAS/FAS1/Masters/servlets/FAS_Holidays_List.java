package Servlets.FAS.FAS1.Masters.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class FAS_Holidays_List extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ResultSet rss = null;
        ResultSet res = null;
        PreparedStatement pss = null;
        ResultSet rs1 = null;
        PreparedStatement ps1 = null;
        Statement statement = null;


        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        String userid = (String)session.getAttribute("UserId");
        System.out.println("User Id is:" + userid);
        response.setContentType(CONTENT_TYPE);
        String xml = "";
        String strCommand = "";
        String txtaccountheadcode = "";
        String txtaccountheadname = "";
        String txtSectionId = "";
        String officeid = "";


        int accountheadcode = 0, offid = 0, sectid = 0;
        txtaccountheadcode = request.getParameter("txtaccountheadcode");
        txtSectionId = request.getParameter("txtSectionId");
        officeid = request.getParameter("officeid");

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        int CashbookYear = 0;
        int CashbookMonth = 0;
        int AccId = 0;
        int OffCode = 0;
        String txtRemarks = "";
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        String update_user = (String)session.getAttribute("UserId");
        int ret_code = 0;
        /* String CashBook_Year_Cur = request.getParameter("txtCash_year");
        String CashBook_Month_Cur = request.getParameter("txtCash_Month");
        int CashBookYear_Cur = 0;
        int CashBookMonth_Cur = 0;
        try {
            CashBookYear_Cur = Integer.parseInt(CashBook_Year_Cur);
            System.out.println("Current CashBook_Year :" + CashBookYear_Cur);
        } catch (Exception e) {
            System.out.println("Exception in Current CashBook_Year:" + e);
            CashBookYear_Cur = 0;
        }

        try {
            CashBookMonth_Cur = Integer.parseInt(CashBook_Month_Cur);
            System.out.println("Current CashBook Month :" + CashBookMonth_Cur);
        } catch (Exception e) {
            System.out.println("Exception in Current CashBook Month:" + e);
            CashBookMonth_Cur = 0;
        }
        */
        if (strCommand.equalsIgnoreCase("Add")) 
        {
            xml = "<response><command>Add</command>";
            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }
            try {
                txtRemarks = request.getParameter("txtRemarks");
                System.out.println("txtRemarks" + txtRemarks);
            } catch (Exception e) {
                System.out.println("excepion in txtremarks :" + e);
            }
            Date txtCrea_date = null;
            Calendar c;
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
            try {
                ps1 =
 con.prepareStatement("select HOLIDAY_DATE,REASON from FAS_HOLIDAYS_LIST where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and HOLIDAY_DATE=?");
                ps1.setInt(1, AccId);
                ps1.setInt(2, OffCode);
                ps1.setInt(3, CashbookYear);
                ps1.setInt(4, CashbookMonth);
                ps1.setDate(5, txtCrea_date);
                rs1 = ps1.executeQuery();
                if (!rs1.next()) {
                    System.out.println("this i sinside the if loop");


                    try {
                        ps =
  con.prepareStatement("insert into FAS_HOLIDAYS_LIST (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,HOLIDAY_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,REASON,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?)");
                        // System.out.println(ps);
                        ps.setInt(1, AccId);
                        ps.setInt(2, OffCode);
                        ps.setDate(3, txtCrea_date);
                        ps.setInt(4, CashbookYear);
                        ps.setInt(5, CashbookMonth);
                        ps.setString(6, txtRemarks);
                        ps.setString(7, update_user);
                        ps.setTimestamp(8, ts);
                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";


                    } catch (Exception e) {

                        System.out.println("catch. in  adding...." + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                } else {
                    System.out.println("This is Else Loop");
                    xml = xml + "<flag>AlreadyExist</flag>";
                }

                xml = xml + "</response>";
            } catch (Exception e) {
                System.out.println("Exception in select:" + e);
            }
        } 
        //added on 27/01/2012
        else if(strCommand.equalsIgnoreCase("CommonList"))
        {
            xml = "<response><command>CommonList</command>";
            System.out.println("inside the common holidays addition option****");
            try 
            {
                
                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }
            try {
                txtRemarks = request.getParameter("remarks");
                System.out.println("txtRemarks" + txtRemarks);
            } catch (Exception e) {
                System.out.println("excepion in txtremarks :" + e);
            }
            Date txtCrea_date1 = null;
            Calendar c;
            String[] sd = request.getParameter("txtCrea_date1").split("/");
            c =
            new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date1 = new Date(d.getTime());
            System.out.println("txtCrea_date11111 " + txtCrea_date1);
            try {
                ps1 =
            con.prepareStatement("select HOLIDAY_DATE,REASON from FAS_COMMON_HOLIDAYS_LIST where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and HOLIDAY_DATE=?");
                ps1.setInt(1, CashbookYear);
                ps1.setInt(2, CashbookMonth);
                ps1.setDate(3, txtCrea_date1);
                rs1 = ps1.executeQuery();
                if (!rs1.next()) 
                {
                    System.out.println("this is inside the if loop");
                    try 
                    {
                        ps =con.prepareStatement("insert into FAS_COMMON_HOLIDAYS_LIST (CASHBOOK_YEAR,CASHBOOK_MONTH,HOLIDAY_DATE,REASON,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)");
                        // System.out.println(ps);
                        ps.setInt(1, CashbookYear);
                        ps.setInt(2, CashbookMonth);
                        ps.setDate(3, txtCrea_date1);
                        ps.setString(4, txtRemarks);
                        ps.setString(5, update_user);
                        ps.setTimestamp(6, ts);
                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                    } catch (Exception e) {

                        System.out.println("catch. in  adding...." + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                } 
                else 
                {
                    System.out.println("This is Else Loop");
                    xml = xml + "<flag>AlreadyExist</flag>";
                }

                xml = xml + "</response>";
            } catch (Exception e) {
                System.out.println("Exception in select:" + e);
            }

        
        }
        
        
        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";

            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }
            try {
                txtRemarks = request.getParameter("remarks");
                System.out.println("txtRemarks" + txtRemarks);
            } catch (Exception e) {
                System.out.println("excepion in txtremarks :" + e);
            }
            Date txtCrea_date = null;
            Calendar c;
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
            System.out.println("userid" + userid);
            System.out.println(ts);
            try {
                String sql =
                    ("Update FAS_HOLIDAYS_LIST set REASON=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and HOLIDAY_DATE=?");
                System.out.println("Update FAS_HOLIDAYS_LIST set REASON='" +
                                   txtRemarks + "',UPDATED_BY_USER_ID='" +
                                   userid + "',UPDATED_DATE='" + ts +
                                   "' where ACCOUNTING_UNIT_ID=" + AccId +
                                   " AND ACCOUNTING_FOR_OFFICE_ID=" + OffCode +
                                   " and CASHBOOK_YEAR=" + CashbookYear +
                                   " and CASHBOOK_MONTH=" + CashbookMonth +
                                   " and HOLIDAY_DATE='" + txtCrea_date +
                                   "' ");
                ps = con.prepareStatement(sql);

                ps.setString(1, txtRemarks);
                ps.setString(2, userid);
                ps.setTimestamp(3, ts);
                ps.setInt(4, AccId);
                ps.setInt(5, OffCode);
                ps.setInt(6, CashbookYear);
                ps.setInt(7, CashbookMonth);
                ps.setDate(8, txtCrea_date);
                int i = ps.executeUpdate();
                System.out.println("value of i" + i);
                if (i > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();


            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } 
        else if (strCommand.equalsIgnoreCase("UpdateCommon")) {
            xml = "<response><command>UpdateCommon</command>";

            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }
            try {
                txtRemarks = request.getParameter("remarks");
                System.out.println("txtRemarks" + txtRemarks);
            } catch (Exception e) {
                System.out.println("excepion in txtremarks :" + e);
            }
            Date txtCrea_date = null;
            Calendar c;
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
        new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
            System.out.println("userid" + userid);
            System.out.println(ts);
            try {
                String sql =
                    ("Update FAS_COMMON_HOLIDAYS_LIST set REASON=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and HOLIDAY_DATE=?");
                System.out.println("Update FAS_HOLIDAYS_LIST set REASON='" +
                                   txtRemarks + "',UPDATED_BY_USER_ID='" +
                                   userid + "',UPDATED_DATE='" + ts +
                                   "' where "+
                                   "  CASHBOOK_YEAR=" + CashbookYear +
                                   " and CASHBOOK_MONTH=" + CashbookMonth +
                                   " and HOLIDAY_DATE='" + txtCrea_date +
                                   "' ");
                ps = con.prepareStatement(sql);

                ps.setString(1, txtRemarks);
                ps.setString(2, userid);
                ps.setTimestamp(3, ts);
                
                ps.setInt(4, CashbookYear);
                ps.setInt(5, CashbookMonth);
                ps.setDate(6, txtCrea_date);
                int i = ps.executeUpdate();
                System.out.println("value of i" + i);
                if (i > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();


            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        
        else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }
            try {
                txtRemarks = request.getParameter("remarks");
                System.out.println("txtRemarks" + txtRemarks);
            } catch (Exception e) {
                System.out.println("excepion in txtremarks :" + e);
            }
            Date txtCrea_date = null;
            Calendar c;
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
            System.out.println("userid" + userid);
            System.out.println(ts);
            try {
                ps1 =
 con.prepareStatement("delete from FAS_HOLIDAYS_LIST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and HOLIDAY_DATE=? ");
                ps1.setInt(1, AccId);
                ps1.setInt(2, OffCode);
                ps1.setInt(3, CashbookYear);
                ps1.setInt(4, CashbookMonth);
                ps1.setDate(5, txtCrea_date);


                ps1.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps1.close();
            } catch (Exception ex2) {
                System.out.println("exception in add" + ex2);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        else if (strCommand.equalsIgnoreCase("DeleteCommon")) {
                    xml = "<response><command>DeleteCommon</command>";
                    try {

                        CashbookYear =
                                Integer.parseInt(request.getParameter("CashbookYear"));
                        System.out.println("CashbookYear is:" + CashbookYear);

                    } catch (Exception ex1) {
                        System.out.println("Exception in CashbookYear:" + ex1);
                    }
                    try {

                        CashbookMonth =
                                Integer.parseInt(request.getParameter("CashbookMonth"));
                        System.out.println("CashbookMonth is:" + CashbookMonth);

                    } catch (Exception ex1) {
                        System.out.println("Exception in CashbookMonth:" + ex1);
                    }
                    try {
                        txtRemarks = request.getParameter("remarks");
                        System.out.println("txtRemarks" + txtRemarks);
                    } catch (Exception e) {
                        System.out.println("excepion in txtremarks :" + e);
                    }
                    Date txtCrea_date = null;
                    Calendar c;
                    String[] sd = request.getParameter("txtCrea_date").split("/");
                    c =
           new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                 Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    txtCrea_date = new Date(d.getTime());
                    System.out.println("txtCrea_date " + txtCrea_date);
                    System.out.println("userid" + userid);
                    System.out.println(ts);
                    try {
                        ps1 =
         con.prepareStatement("delete from FAS_COMMON_HOLIDAYS_LIST where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and HOLIDAY_DATE=? ");
                        ps1.setInt(1, CashbookYear);
                        ps1.setInt(2, CashbookMonth);
                        ps1.setDate(3, txtCrea_date);


                        ps1.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                        ps1.close();
                    } catch (Exception ex2) {
                        System.out.println("exception in add" + ex2);
                        xml = xml + "<flag>failure</flag>";
                    }
                    xml = xml + "</response>";
                }
        /* try {

                Statement st=con.createStatement();
                String sql="delete from FAS_HOLIDAYS_LIST where ACCOUNTING_UNIT_ID="+AccId+" and ACCOUNTING_FOR_OFFICE_ID="+OffCode+" and CASHBOOK_YEAR="+CashbookYear+" and CASHBOOK_MONTH="+CashbookMonth+" and HOLIDAY_DATE='"+txtCrea_date+"' ";
                System.out.println(sql);
                int ij=st.executeUpdate(sql);

                if(ij>0)
                {
                xml=xml+"<flag>success</flag>";
                }
                else {
                    xml=xml+"<flag>failure</flag>";
                }
            }
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } */
        //---------
        else if (strCommand.equals("Get")) {
            String dispdate1 = "", remarks = "", oname = "", unitname = "";
            ;
            int txtCash_year = 0, txtCash_Month = 0, empid = 0, officeid_get =
                0;
            xml = "<response><command>Get</command>";

            try {
                System.out.println("bef res");

                String SQL =
                    ("select to_char(now(),'yyyy') as curr_year,to_char(now(),'mm') as curr_month ");
                ps = con.prepareStatement(SQL);
                rs = ps.executeQuery();

                while (rs.next()) {
                    txtCash_year = rs.getInt("curr_year");
                    txtCash_Month = rs.getInt("curr_month");
                    System.out.println("year" + txtCash_year);
                    System.out.println("month" + txtCash_Month);

                }
                String SQL2 =
                    ("select a.EMPLOYEE_ID,b.office_id from SEC_MST_USERS a,HRM_EMP_CURRENT_POSTING b where a.USER_ID=? and a.EMPLOYEE_ID=b.EMPLOYEE_ID");
                ps = con.prepareStatement(SQL2);
                ps.setString(1, userid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    empid = rs.getInt("EMPLOYEE_ID");
                    officeid_get = rs.getInt("office_id");
                }
                int unitid = 0;

                try {
                    if (officeid_get == 5000) {
                        //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                        //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                        String getWing =
                            "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                        ps = con.prepareStatement(getWing);
                        ps.setInt(1, officeid_get);
                        ps.setInt(2, empid);
                        ps.setInt(3, officeid_get);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            unitid = rs.getInt("ACCOUNTING_UNIT_ID");
                            unitname = rs.getString("ACCOUNTING_UNIT_NAME");


                            System.out.println("HOunitid" + unitid);
                            System.out.println("HOunitname" + unitname);
                            System.out.println(".." +
                                               rs.getInt("OFFICE_WING_SINO"));

                        }

                        ps.close();
                        rs.close();
                    } else {
                        ps =
  con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                        ps.setInt(1, officeid_get);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            unitid = rs.getInt("ACCOUNTING_UNIT_ID");
                            unitname = (rs.getString("ACCOUNTING_UNIT_NAME"));

                        }
                        ps.close();
                        rs.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                // res = statement.executeQuery("select HOLIDAY_DATE,REASON from FAS_HOLIDAYS_LIST where  CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month+"");
                System.out.println("officeid_get" + officeid_get);
                System.out.println("empid" + empid);
                System.out.println("unitid" + unitid);

                try

                {
                    String sqlget =
                        ("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,HOLIDAY_DATE,REASON from FAS_HOLIDAYS_LIST where ACCOUNTING_UNIT_ID=" +
                         unitid + " and ACCOUNTING_FOR_OFFICE_ID=" +
                         officeid_get + " and CASHBOOK_YEAR=" + txtCash_year +
                         " and CASHBOOK_MONTH=" + txtCash_Month + "");
                    ps = con.prepareStatement(sqlget);
                    res = ps.executeQuery();
                    System.out.println("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,HOLIDAY_DATE,REASON from FAS_HOLIDAYS_LIST where ACCOUNTING_UNIT_ID=" +
                                       unitid +
                                       " and ACCOUNTING_FOR_OFFICE_ID=" +
                                       officeid_get + " and CASHBOOK_YEAR=" +
                                       txtCash_year + " and CASHBOOK_MONTH=" +
                                       txtCash_Month + "");
                    System.out.println("aft res");

                    xml = xml + "<flag>success</flag>";
                    while (res.next()) 
                    {
                        unitid = res.getInt("ACCOUNTING_UNIT_ID");
//                        unitname = res.getString("ACCOUNTING_UNIT_NAME");
//
                        offid = res.getInt("ACCOUNTING_FOR_OFFICE_ID");

                        System.out.println("sathya*********"+res.getDate("HOLIDAY_DATE"));
                        if (res.getDate("HOLIDAY_DATE") == null)
                            dispdate1 = "";
                        else 
                        {
                          System.out.println("The else part of Holiday_date");
                            java.sql.Date dd = res.getDate("HOLIDAY_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            dispdate1 = sdf.format(dd);
                            System.out.println("date1 is" + dispdate1);
                        }

                        remarks = res.getString("REASON");

                        xml =
                        xml + "<officeid>" + offid + "</officeid><unitid>" + unitid +
                        "</unitid><holi_date>" + dispdate1 + "</holi_date><reason>" + remarks +
                        "</reason>";
                        //<PayName>" + PayName + "</PayName>;


                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF GET: " +
                                       aee);
                }
               
                res.close();
//                response.setHeader("cache-control", "no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equals("Get1")) 
        {
            String dispdate1 = "", remarks = "", oname = "", unitname = "";
            
            int txtCash_year = 0, txtCash_Month = 0, empid = 0, officeid_get = 0;
            xml = "<response><command>Get1</command>";

                try
                {
//                txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));
//                System.out.println("cb year test*********"+txtCash_year);
//                txtCash_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
//                System.out.println("cb month test*********"+txtCash_Month);
                 String SQL =
                     ("select to_char(now(),'yyyy') as curr_year,to_char(now(),'mm') as curr_month ");
                 ps = con.prepareStatement(SQL);
                 rs = ps.executeQuery();
                
                 while (rs.next()) {
                     txtCash_year = rs.getInt("curr_year");
                     txtCash_Month = rs.getInt("curr_month");
                     System.out.println("year" + txtCash_year);
                     System.out.println("month" + txtCash_Month);
                 }
                try

                {
                    String sqlget =
                        "select HOLIDAY_DATE,REASON from FAS_COMMON_HOLIDAYS_LIST where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
                           ps = con.prepareStatement(sqlget);
                           ps.setInt(1,txtCash_year);
                           ps.setInt(2,txtCash_Month);
                           
                    res = ps.executeQuery();
                    xml = xml + "<flag>success</flag>";
                    while (res.next()) 
                    {
                         System.out.println("Test*********"+res.getDate("HOLIDAY_DATE"));
                        if (res.getDate("HOLIDAY_DATE") == null)
                            dispdate1 = "";
                        else 
                        {
                          System.out.println("The else part of Holiday_date");
                            java.sql.Date dd = res.getDate("HOLIDAY_DATE");
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            dispdate1 = sdf.format(dd);
                            System.out.println("date1 is" + dispdate1);
                        }

                        remarks = res.getString("REASON");

                        xml =
                        xml + "<holi_date>" + dispdate1 + "</holi_date><reason>" + remarks +
                        "</reason>";
                      
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF GET: " +
                                       aee);
                }
               
               //                response.setHeader("cache-control", "no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }





        else if (strCommand.equals("Load")) {
            String dispdate1 = "", remarks = "";
            xml = "<response><command>Load</command>";
            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }

            try {
                System.out.println("bef load");

                //  res = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                String sqlLoad =
                    ("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,HOLIDAY_DATE,REASON from FAS_HOLIDAYS_LIST where ACCOUNTING_UNIT_ID=" +
                     AccId + " and ACCOUNTING_FOR_OFFICE_ID=" + OffCode +
                     " and CASHBOOK_YEAR=" + CashbookYear +
                     " and CASHBOOK_MONTH=" + CashbookMonth + " ");
                ps = con.prepareStatement(sqlLoad);
                res = ps.executeQuery();
                System.out.println(sqlLoad);
                xml = xml + "<flag>success</flag>";

                while (res.next()) {
                    offid = res.getInt("ACCOUNTING_FOR_OFFICE_ID");

                    if (res.getDate("HOLIDAY_DATE") == null)
                        dispdate1 = "";
                    else {
                        java.sql.Date dd = res.getDate("HOLIDAY_DATE");
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        dispdate1 = sdf.format(dd);
                        System.out.println("date1 is" + dispdate1);
                    }

                    remarks = res.getString("REASON");


                    //<PayName>" + PayName + "</PayName>;
                    xml =
 xml + "<officeid>" + offid + "</officeid><unitid>" + AccId +
   "</unitid><holi_date>" + dispdate1 + "</holi_date><reason>" + remarks +
   "</reason>";

                }

                res.close();
                response.setHeader("cache-control", "no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        
        
        else if (strCommand.equals("Load1")) {
            String dispdate1 = "", remarks = "";
            xml = "<response><command>Load1</command>";
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }

            try {
                System.out.println("bef load");

                //  res = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                String sqlLoad =
                    "select HOLIDAY_DATE,REASON from FAS_COMMON_HOLIDAYS_LIST where CASHBOOK_YEAR=" + CashbookYear+ 
                    " and CASHBOOK_MONTH=" + CashbookMonth;
                ps = con.prepareStatement(sqlLoad);
                res = ps.executeQuery();
                System.out.println(sqlLoad);
                xml = xml + "<flag>success</flag>";

                while (res.next()) {
                      if (res.getDate("HOLIDAY_DATE") == null)
                        dispdate1 = "";
                    else {
                        java.sql.Date dd = res.getDate("HOLIDAY_DATE");
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        dispdate1 = sdf.format(dd);
                        System.out.println("date1 is" + dispdate1);
                    }

                    remarks = res.getString("REASON");


//                   System.out.println("Xml *****"+xml);
                    //<PayName>" + PayName + "</PayName>;
                    
        xml =xml+"<holi_date>" + dispdate1 + "</holi_date><reason>" + remarks +
        "</reason>";

                }

                res.close();
                response.setHeader("cache-control", "no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        //----------
        System.out.println("xml at last is:" + xml);
        out.write(xml);
        out.flush();
        out.close();
    }
}

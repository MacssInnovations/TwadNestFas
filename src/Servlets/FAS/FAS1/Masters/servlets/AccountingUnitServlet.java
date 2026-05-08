package Servlets.FAS.FAS1.Masters.servlets;

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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class AccountingUnitServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Variables Declaration
         */
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        ResultSet results2 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;


        /**
         * Database Connection
         */
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }


        /**
         *  Set Content Type
         */
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        /**
     * Session Checking
     */
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


        /** Get Office ID -- Ist Tab */

        String LOffice = null;Calendar c;
        
        try {
            LOffice = request.getParameter("txtLOffice");
        } catch (Exception e) {
            System.out.println(e);
        }
        /** Getting Date of Opening accounting unit id */
  
        String DOpen_accunit="";Date DOpen_accunitid=null;
        try {
        	DOpen_accunit = request.getParameter("date_open");
        	System.out.println("accounting unit cration date String ***"+DOpen_accunit);
       
        String[] sd=request.getParameter("date_open").split("/");
        c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
        java.util.Date d=c.getTime();
        DOpen_accunitid=new Date(d.getTime());
        System.out.println("date_open "+DOpen_accunitid);
        
        } catch (Exception e) {
            System.out.println(e);
        }
        /** Get Office Name -- Ist Tab */
        String accountname = null;
        try {
            accountname = request.getParameter("txtaccountname");
        } catch (Exception e) {
            System.out.println(e);
        }


        /** Get Office ID -- Second Tab */
        String office[] = null;
        try {
            office = request.getParameterValues("officeid");
        } catch (Exception e) {
            System.out.println("Error of Getting Office ID's");
        }


        String command = null;

        /** Get Command Parameter */
        try {
            command = request.getParameter("command");
        } catch (Exception e) {
            System.out.println("Error of Getting Command Parameter ");
        }


        int LOfficeId = 0;
        int officeid[] = null;
        String HAccount = null;

        try {
            HAccount = request.getParameter("txtHAccountid");
            System.out.println("HAccount --------------------------<><><>" +
                               HAccount);
        } catch (Exception e) {
            System.out.println(e);
        }

        int HAccountUnit = 0;


        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");


        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /**
          * Accounting Unit Id List
          */
        if (command.equalsIgnoreCase("List")) {

            String accountunitid = request.getParameter("AccountUnitId");

            int aunitid = 0;

            if (accountunitid != null) {
                aunitid = Integer.parseInt(accountunitid);
                System.out.println("Unit is:" + aunitid);
            }


            try {
                String sql5 =
                    "select a.accounting_unit_id,a.accounting_unit_name,a.accounting_unit_office_id,b.accounting_for_office_id,c.office_name,a.DATE_OF_OPENING from fas_mst_acct_units a,fas_mst_acct_unit_offices b,com_mst_offices c where a.accounting_unit_id=b.accounting_unit_id and b.accounting_for_office_id=c.office_id and a.accounting_unit_id=?";
                ps = connection.prepareStatement(sql5);
                ps.setInt(1, aunitid);
                results = ps.executeQuery();
                String xml = "";
                xml = "<response><flag>success</flag>";
                while (results.next()) {

                    response.setContentType("text/xml");
                    int accountid = results.getInt("accounting_unit_id");
                    String unitname =
                        results.getString("accounting_unit_name").trim();
                    int accofficeid =
                        results.getInt("accounting_unit_office_id");
                    int accforofficeid =
                        results.getInt("accounting_for_office_id");
                    String officename = results.getString("Office_name");
                    String DOpening = results.getString("DATE_OF_OPENING");
                    xml =
 xml + "<leng><accountid>" + accountid + "</accountid><unitname>" + unitname +
   "</unitname><accofficeid>" + accofficeid +
   "</accofficeid><accforofficeid>" + accforofficeid +
   "</accforofficeid><officename>" + officename + "</officename><dateofopening>"+ DOpening+"</dateofopening></leng>";

                }
                xml = xml + "</response>";
                out.write(xml);
                System.out.println("xml is:" + xml);
            } catch (Exception e) {
                System.out.println("Exception in Accounting Unit:" + e);
                String xml = "<response><flag>failure</flag></response>";
            }


        }


        /**
          *  Loading Accounting Office ID Name
          */

        if (command.equalsIgnoreCase("List_Office_Name")) {

            String cID = request.getParameter("cID");

            int aunitid = 0;

            if (cID != null) {
                aunitid = Integer.parseInt(cID);
                System.out.println("Unit is:" + aunitid);
            }


            try {
                String sql5 =
                    "" + "  select office_id, office_name from com_mst_offices        \n" +
                    "  where office_id = ?                                    \n" +
                    "  and OFFICE_STATUS_ID not in ( 'RD', 'CL')                 \n";

                String xml = "";
                xml = "<response>";

                ps = connection.prepareStatement(sql5);
                ps.setInt(1, aunitid);
                results = ps.executeQuery();

                int cnt = 0;


                while (results.next()) {
                    response.setContentType("text/xml");
                    int accountid = results.getInt("office_id");
                    String unitname = results.getString("office_name").trim();
                    xml =
 xml + "<accountid>" + accountid + "</accountid><unitname>" + unitname +
   "</unitname>";
                    cnt++;
                }

                if (cnt > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>NotFound</flag>";
                    // xml=xml+"<flag>norecords</flag>";
                }

                xml = xml + "</response>";

                out.write(xml);
                System.out.println("xml is:" + xml);
            } catch (Exception e) {
                System.out.println("Exception in Accounting Unit:" + e);
                String xml = "<flag>failure</flag></response>";
            }


        }


        /**
     * Not Used Anywhere
     */
        if (command.equalsIgnoreCase("AccountUnit")) {
            System.out.println("AccountUnit");
            String accountunitid = request.getParameter("AccountUnitId");
            int aunitid = 0;
            if (accountunitid != null) {
                aunitid = Integer.parseInt(accountunitid);
                System.out.println("Unit is:" + aunitid);
            }
            try {

                String sql6 =
                    "select a.ACCOUNTING_UNIT_ID,a.ACCOUNTING_UNIT_NAME,a.ACCOUNTING_UNIT_OFFICE_ID,b.office_name,to_char(a.DATE_OF_OPENING,'DD/MM/YYYY')as DATE_OF_OPENING from fas_mst_acct_units a,com_mst_offices b " +
                    " where a.accounting_unit_id=? and a.ACCOUNTING_UNIT_OFFICE_ID=b.office_id ";
                ps = connection.prepareStatement(sql6);
                ps.setInt(1, aunitid);


                results = ps.executeQuery();
                String xml = "";
                xml = "<response><flag>success</flag>";

                if (results.next()) {

                    response.setContentType("text/xml");
                    int accountid = results.getInt("accounting_unit_id");
                    String unitname =
                        results.getString("accounting_unit_name").trim();
                    int accofficeid =
                        results.getInt("accounting_unit_office_id");
                    String officenameL = results.getString("office_name");
                    String DOOpening = results.getString("DATE_OF_OPENING");
                    xml =
 xml + "<accountid>" + accountid + "</accountid><unitname>" + unitname +
   "</unitname><accofficeid>" + accofficeid + "</accofficeid><officenameL>" +
   officenameL + "</officenameL><dopening>" + DOOpening + "</dopening>";

                }

                String sql7 =
                    "select a.ACCOUNTING_UNIT_ID,a.ACCOUNTING_FOR_OFFICE_ID,b.office_name from FAS_MST_ACCT_UNIT_OFFICES a,com_mst_offices b " +
                    " where a.ACCOUNTING_FOR_OFFICE_ID=b.office_id and " +
                    " a.ACCOUNTING_UNIT_ID=?";
                ps1 = connection.prepareStatement(sql7);
                ps1.setInt(1, aunitid);
                results2 = ps1.executeQuery();
                while (results2.next()) {
                    xml =
 xml + "<leng><accountforofficeid>" + results2.getInt("ACCOUNTING_FOR_OFFICE_ID") +
   "</accountforofficeid><officename>" + results2.getString("office_name") +
   "</officename></leng>";
                }
                xml = xml + "</response>";
                out.write(xml);
                System.out.println("xml is:" + xml);
            } catch (Exception e) {
                System.out.println("Exception in Accounting Unit:" + e);
                String xml = "<response><flag>failure</flag></response>";
            }


        }


        /** Record Addition */
        else if (command.equalsIgnoreCase("Add")) {

            try {
                if (LOffice != null) {
                    LOfficeId = Integer.parseInt(LOffice);
                }


                System.out.println("Array Length ----------------->>>>>>>" +
                                   office.length);
                officeid = new int[office.length];


                System.out.println("Office is:" + officeid);
                System.out.println("Officelength:" + office.length);


                if (office != null) {
                    for (int i = 0; i < office.length; i++) {
                        officeid[i] = Integer.parseInt(office[i]);
                        System.out.println("Office After Integer is:" +
                                           officeid[i]);

                    }
                }
            } catch (Exception e) {
                System.out.println("Exception in Office:" + e);
            }


            try {
                HAccountUnit = Integer.parseInt(HAccount);
            } catch (Exception e) {
                System.out.println("Exception in Hidden AccountUnit:" + e);
            }


            try {
                if (HAccount.equalsIgnoreCase("")) {
                    String sql =
                        "select max(accounting_unit_id) as accountid from fas_mst_acct_units where accounting_unit_id not in(888,999)";
                    ps = connection.prepareStatement(sql);
                    results = ps.executeQuery();
                    int id = 0;
                    if (results.next()) {
                        id = results.getInt("accountid");
                        id = id + 1;
                        System.out.println("Account id is:" + id);
                    }

                    ps.close();
                    String sql2 =
                        "insert into fas_mst_acct_units(accounting_unit_id,accounting_unit_name,accounting_unit_office_id ,DATE_OF_OPENING,UPDATED_BY_USER_ID, UPDATED_DATE  ) values(?,?,?,?,?,?)";
                    ps = connection.prepareStatement(sql2);
                    ps.setInt(1, id);
                    ps.setString(2, accountname);
                    ps.setInt(3, LOfficeId);
                    ps.setDate(4,DOpen_accunitid);
                    ps.setString(5, userid);
                    ps.setTimestamp(6, ts);

                    ps.executeUpdate();
                    ps.close();

                    String sql3 =
                        "insert into fas_mst_acct_unit_offices(accounting_for_office_id,accounting_unit_id,UPDATED_BY_USER_ID, UPDATED_DATE) values(?,?,?,?)";
                    ps = connection.prepareStatement(sql3);

                    System.out.println("1----------------->");

                    for (int j = 0; j < officeid.length; j++) {

                        ps.setInt(1, officeid[j]);
                        ps.setInt(2, id);
                        ps.setString(3, userid);
                        ps.setTimestamp(4, ts);
                        ps.executeUpdate();
                        System.out.println("2-------------------->");

                    }
                    ps.close();

                    String msg =
                        "AccountingUnit Details has been Successfully Inserted.New AccountingUnit id is:" +
                        id;
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");


                } else {


                    System.out.println("Inner Else" + HAccountUnit);
                    connection.setAutoCommit(false);
                  //  System.out.println("accountname:::"+accountname.trim()+":::");
                    String sql1 =
                        "update fas_mst_acct_units set accounting_unit_name=?,accounting_unit_office_id=? ,UPDATED_BY_USER_ID = ? , UPDATED_DATE = ? where accounting_unit_id=?";
                    ps = connection.prepareStatement(sql1);
                    ps.setString(1, accountname.trim());
                    ps.setInt(2, LOfficeId);
                    ps.setString(3, userid);
                    ps.setTimestamp(4, ts);
                    ps.setInt(5, HAccountUnit);

                    try {
                        ps.executeUpdate();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    ps.close();

                    String sql4 =
                        "delete from fas_mst_acct_unit_offices where accounting_unit_id=?";
                    ps = connection.prepareStatement(sql4);
                    ps.setInt(1, HAccountUnit);

                    try {
                        ps.executeUpdate();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    ps.close();

                    String sql3 =
                        "insert into fas_mst_acct_unit_offices(accounting_for_office_id,accounting_unit_id, UPDATED_BY_USER_ID, UPDATED_DATE ) values(?,?,?,?)";
                    ps = connection.prepareStatement(sql3);
                    for (int j = 0; j < office.length; j++) {
                        System.out.println(officeid[j]);
                        System.out.println(HAccountUnit);
                        System.out.println(userid);
                        System.out.println(ts);

                        ps.setInt(1, officeid[j]);
                        ps.setInt(2, HAccountUnit);
                        ps.setString(3, userid);
                        ps.setTimestamp(4, ts);
                        try {
                            ps.executeUpdate();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    ps.close();
                    connection.commit();
                    connection.setAutoCommit(true);
                    String msg =
                        "AccountingUnit Details has been Updated Sucessfully";
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");

                }
            } catch (Exception e) {
                System.out.println("Exception in Query:" + e);
                try {
                    connection.rollback();
                } catch (Exception e1) {
                    System.out.println("Exception in Catch" + e1);
                }
                String msg = "<br><br>" + e + "<br>";
                sendMessage(response, msg, "ok");
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
            System.out.println(e);
        }
    }


}

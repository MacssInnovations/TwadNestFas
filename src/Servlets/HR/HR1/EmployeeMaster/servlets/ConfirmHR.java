package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ConfirmHR extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        //response.setContentType(CONTENT_TYPE);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        Connection con = null;
        try {

            ResourceBundle rsb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            System.out.println("coming---------------------");
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
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement st = null;

        int year = 0, month = 0, offid = 0;
        String allupdate = "";
        String xml = "";
        String strCommand = "", sql = "";
        String proc_flow_status = "";

        PrintWriter out = response.getWriter();
        /*   HttpSession session=request.getSession(false);
        try
        {

        if(session==null)
        {
        System.out.println(request.getContextPath()+"/index.jsp");
        response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
        System.out.println(session);

        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }*/
        response.setContentType(CONTENT_TYPE);
        //  PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            //  strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
            // System.out.println("assign..... Code::"+strcode);


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        offid = Integer.parseInt(request.getParameter("txtOffId"));
        try {
            if (strCommand.equalsIgnoreCase("Add")) {
                xml = "<response><command>Add</command>";

                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                System.out.println("inside----------");

                year = Integer.parseInt(request.getParameter("year"));
                month = Integer.parseInt(request.getParameter("month"));
                System.out.println("heeeeeeeeeeeeeeeee" + offid + year +
                                   month);
                allupdate = request.getParameter("allupdate");
                System.out.println("inside1-------------");
                //sql="insert into hrm_trans_verify (office_id,year,month,JOINING_RELIEVAL,OFFICE_ADDRESS,GRADE_CHANGES,ADDL_DETAILS,SANCTION_STRENGTH,VACANCY_POSITION) values(?,?,?,?,?,?,?,?,?)";
                sql =
 "insert into hrm_trans_verify (office_id,year,month,allupdate,PROCESS_FLOW_STATUS_ID,UPDATED_DATE,UPDATED_BY_USER_ID) values(?,?,?,?,?,?,?)";

                ps = con.prepareStatement(sql);
                ps.setInt(1, offid);
                ps.setInt(2, year);
                ps.setInt(3, month);
                ps.setString(4, allupdate);

                ps.setString(5, "CR");
                ps.setTimestamp(6, ts);
                ps.setString(7, updatedby);
                ps.executeUpdate();
                System.out.println("success");
                xml = xml + "<flag>success</flag>";

            } else if (strCommand.equalsIgnoreCase("load")) {
                xml = "<response><command>Load</command>";
                sql = "select * from hrm_trans_verify where office_id=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, offid);
                System.out.println("office id" + offid);
                rs = ps.executeQuery();
                System.out.println("success");
                while (rs.next()) {
                    System.out.println("result set");
                    xml = xml + "<year>" + rs.getInt("year") + "</year>";
                    xml = xml + "<month>" + rs.getInt("month") + "</month>";
                    xml =
 xml + "<allupdate>" + rs.getString("allupdate") + "</allupdate>";
                    xml =
 xml + "<processid>" + rs.getString("process_flow_status_id") + "</processid>";
                    System.out.println(rs.getString("process_flow_status_id") +
                                       "..........................");
                }
                xml = xml + "<flag>success</flag>";
            } else if (strCommand.equalsIgnoreCase("Update")) {
                xml = "<response><command>Update</command>";
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                System.out.println("inside----------");
                proc_flow_status = request.getParameter("proc_flow_status");
                System.out.println("proc_flow_status" + proc_flow_status);
                year = Integer.parseInt(request.getParameter("year"));
                month = Integer.parseInt(request.getParameter("month"));
                System.out.println("heeeeeeeeeeeeeeeee" + offid + year +
                                   month);
                allupdate = request.getParameter("allupdate");
                System.out.println("inside update1-------------");
                //sql="insert into hrm_trans_verify (office_id,year,month,JOINING_RELIEVAL,OFFICE_ADDRESS,GRADE_CHANGES,ADDL_DETAILS,SANCTION_STRENGTH,VACANCY_POSITION) values(?,?,?,?,?,?,?,?,?)";
                sql =
 "update hrm_trans_verify set allupdate=?,PROCESS_FLOW_STATUS_ID=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where office_id=? and year=? and month=?";

                ps = con.prepareStatement(sql);
                ps.setString(1, allupdate);
                ps.setString(2, proc_flow_status);
                ps.setTimestamp(3, ts);
                ps.setString(4, updatedby);
                ps.setInt(5, offid);
                ps.setInt(6, year);
                ps.setInt(7, month);

                ps.executeUpdate();
                System.out.println("success");
                xml = xml + "<flag>success</flag>";

            } else if (strCommand.equalsIgnoreCase("Valid")) {
                xml = "<response><command>Valid</command>";

                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                System.out.println("inside----------");

                year = Integer.parseInt(request.getParameter("year"));
                month = Integer.parseInt(request.getParameter("month"));

                System.out.println("heeeeeeeeeeeeeeeee" + offid + year +
                                   month);

                System.out.println("inside update1-------------");
                //sql="insert into hrm_trans_verify (office_id,year,month,JOINING_RELIEVAL,OFFICE_ADDRESS,GRADE_CHANGES,ADDL_DETAILS,SANCTION_STRENGTH,VACANCY_POSITION) values(?,?,?,?,?,?,?,?,?)";
                sql =
 "update hrm_trans_verify set PROCESS_FLOW_STATUS_ID=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where office_id=? and year=? and month=?";

                ps = con.prepareStatement(sql);
                ps.setString(1, "FR");
                ps.setTimestamp(2, ts);
                ps.setString(3, updatedby);
                ps.setInt(4, offid);
                ps.setInt(5, year);
                ps.setInt(6, month);

                ps.executeUpdate();
                System.out.println("success");
                xml = xml + "<flag>success</flag>";

            }

            else if (strCommand.equalsIgnoreCase("Delete")) {
                xml = "<response><command>Delete</command>";

                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                System.out.println("inside----------");

                year = Integer.parseInt(request.getParameter("year"));
                month = Integer.parseInt(request.getParameter("month"));
                System.out.println("heeeeeeeeeeeeeeeee" + offid + year +
                                   month);
                //sql="insert into hrm_trans_verify (office_id,year,month,JOINING_RELIEVAL,OFFICE_ADDRESS,GRADE_CHANGES,ADDL_DETAILS,SANCTION_STRENGTH,VACANCY_POSITION) values(?,?,?,?,?,?,?,?,?)";
                sql =
 "delete from hrm_trans_verify where office_id=? and year=? and month=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, offid);
                ps.setInt(2, year);
                ps.setInt(3, month);


                ps.executeUpdate();
                System.out.println("success");
                xml = xml + "<flag>success</flag>";

            }


        } catch (Exception e) {
            System.out.println("hello");
            xml = xml + "<flag>fail</flag>";
            System.out.println("Exception " + e);
        }
        xml = xml + "</response>";

        System.out.println("xml is : " + xml);
        out.write(xml);
        out.flush();
        out.close();
    }
}

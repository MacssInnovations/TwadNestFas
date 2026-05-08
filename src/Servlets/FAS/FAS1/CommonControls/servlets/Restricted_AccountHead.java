package Servlets.FAS.FAS1.CommonControls.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Restricted_AccountHead extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    int employee_id = 0;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        /**
         * Session Checking
         */

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


        /**
         * Get Employee ID from Session
         */
        HttpSession session = request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");
        System.out.println("user id::" + empProfile.getEmployeeId());
        employee_id = empProfile.getEmployeeId();

        System.out.println("Employee ID in get method --->" + employee_id);

        out.close();


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        out.close();
    }


    public int accountHeadDetails(int txtAcc_HeadCode, int employee_id) {


        /**
        * Variables Declarations
        */

        int account_head_code = 0;
        String account_head_desc = null;
        String usage_status = null;
        String access_restricted = null;
        int accessible_by_office_code = 0;
        int accessible_office_wing_sino = 0;
        int employee_office_id = 0;
        int OFFICE_WING_SINO = 0;

        Connection con = null;

        ResultSet rs2 = null,res=null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;

        Statement st2 = null;
        Statement st3 = null;
        PreparedStatement ps = null;

        int count = 0;

        /**
        * Database Connection
        */


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
        }

        System.out.println("Employee ID is ------>" + employee_id);

        System.out.println("here accounthead is ---->" + txtAcc_HeadCode);

        /** Sql Query for fetching data from 'com_mst_accounts_head' table */
        String sql =
            "select ACCOUNT_HEAD_CODE, ACCOUNT_HEAD_DESC, USAGE_STATUS, ACCESS_RESTRICTED, ACCESSIBLE_BY_OFFICE_CODE, ACCESSIBLE_OFFICE_WING_SINO from  COM_MST_ACCOUNT_HEADS  where ACCOUNT_HEAD_CODE=" +
            txtAcc_HeadCode;


        try {

            System.out.println("1");

            /** Create Statement */
            st2 = con.createStatement();

            System.out.println("2");
            /** Execute Query */
            rs2 = st2.executeQuery(sql);
            System.out.println("3");
            /** Resultset */
            while (rs2.next()) {

                System.out.println("4");

                /** Get Account Head Code */
                account_head_code = rs2.getInt("ACCOUNT_HEAD_CODE");
                /** Get Account Head Description */
                account_head_desc = rs2.getString("ACCOUNT_HEAD_DESC");
                /** Account Head Usage Status */
                usage_status = rs2.getString("USAGE_STATUS");
                /** Acc Head Access Restricted */
                access_restricted = rs2.getString("ACCESS_RESTRICTED");
                /** Accessible office code */
                accessible_by_office_code =
                        rs2.getInt("ACCESSIBLE_BY_OFFICE_CODE");
                /** office wing number */
                accessible_office_wing_sino =
                        rs2.getInt("ACCESSIBLE_OFFICE_WING_SINO");

                count++;

            }
            System.out.println("count-->" + count);
            if (count == 0) {
                return 1;
            }

        } catch (Exception e) {
            System.out.println(e);
        }


        /**
      * Get Employee Office ID
      */

        sql =
 "select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=" +
   employee_id;

        try {
            st3 = con.createStatement();
            rs3 = st3.executeQuery(sql);
            while (rs3.next()) {
                employee_office_id = rs3.getInt("OFFICE_ID");
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        /** Check If ACC Head access restriced status is 'N', No need to check anything */
        if (access_restricted.equals("N")) {
            System.out.println("access restricted     --- N ");
            return 0;

        }
        /** If access_restricted status is 'Y', check office id and office wing if exits */
        else if (access_restricted.equals("Y")) {
            System.out.println("access restricted  --- Y ");
            /** If employee office id is 5000 */
            if (employee_office_id == 5000) {
                System.out.println("this is head office ??????????????????????????????????");
                /** Get head Office wing number */
                sql =
 " select                           \n" + "       ACCOUNTING_UNIT_ID,            \n" +
   "       OFFICE_WING_SINO,              \n" +
   "       accounting_unit_office_id      \n" +
   "    from                              \n" +
   "       FAS_MST_ACCT_UNITS             \n" +
   "                                      \n" +
   "    where ACCOUNTING_UNIT_OFFICE_ID=(select OFFICE_ID from hrm_emp_current_posting where employee_id=? )  and       \n" +
   "    OFFICE_WING_SINO=( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id= ?  and                  \n" +
   "                                office_id= (select OFFICE_ID from hrm_emp_current_posting where employee_id=? ))    \n" +
   "  \n ";

                try {

                    ps = con.prepareStatement(sql);
                    ps.setInt(1, employee_id);
                    ps.setInt(2, employee_id);
                    ps.setInt(3, employee_id);
                    rs4 = ps.executeQuery();
                    /** get head office wing number */
                    while (rs4.next()) {

                        OFFICE_WING_SINO = rs4.getInt("OFFICE_WING_SINO");

                    }

                } catch (Exception e) {
                    System.out.println(e);
                    return 1;
                }

                /** head Office wing number */
                if (OFFICE_WING_SINO == accessible_office_wing_sino) {
                    System.out.println("this is head office and office wing number is ????????? " +
                                       OFFICE_WING_SINO);
                    System.out.println("accessbile office wing number is ????????? " +
                                       accessible_office_wing_sino);
                    return 0;
                } else if (accessible_office_wing_sino == 0) {
                    System.out.println("this is head office but wing is 0 ???????? " +
                                       accessible_office_wing_sino);
                    return 0;
                }


            }
            /** If office id is other than 5000 */
            else {
            	int counted=0;
            	System.out.println("employee_office_id::"+employee_office_id);
                /** Compare office id only */
            	try{
            	 ps = con.prepareStatement("select ACCOUNT_HEAD_CODE from FAS_RESTRICTED_AC_HEADS where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=?");
                 ps.setInt(1, txtAcc_HeadCode);
                 ps.setInt(2, employee_office_id);
                 res=ps.executeQuery();
                 while(res.next())
                 {
                	 counted++;
                 }
                 if(counted>0)
                 {
                	 return 0; 
                 }
            	}
            	catch(Exception e)
            	{
            		System.out.println("exception::::"+e);
            	}
//                if (accessible_by_office_code == employee_office_id) {
//                    System.out.println("this is not head office but acc head is restricted ");
//                    return 0;
//                }

            }


        }

        return 1;


    }

}



package Servlets.Security.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class UserValidation_old extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


        System.out.println("::::::::::::USER LOGIN CONNECTION INITIALING");
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);


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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
    		
            System.out.println(" connection string : " + ConnectionString);

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Connection e" + e);
            //RequestDispatcher rd=config.getServletContext().getNamedDispatcher("/index.jsp?message=yes");
            response.sendRedirect("index.jsp?message=dbnill");

            return;
        }

        boolean loginflag = false;
        ResultSet results = null;
        PreparedStatement statement = null;
        HttpSession session = null;
        int empid = 0;
        String strID = "";
        String strPassword = "";
        String status = "";
        String Remote_host = "";
        System.out.println("user validateion");
        try {
            strID = request.getParameter("txtID");
            strPassword = request.getParameter("txtPassword");
            // System.out.println("user Name:"+strID);
            // System.out.println("user password:"+strPassword);
            //Getting  Remote host IP
            Remote_host = request.getRemoteHost();
            System.out.println("Remote Host is :" + Remote_host);


            session = request.getSession(false);
            System.out.println("::Session:: " + session);
            if (session == null) {
                System.out.println("Session parameter is null ");
                /*//sk This code is added to change to actual password into MD5 password */
                if (strID != null && strPassword != null) {
                    byte[] b = strPassword.getBytes();
                    try {
                        MessageDigest algorithm =
                            MessageDigest.getInstance("MD5");
                        algorithm.reset();
                        algorithm.update(b);
                        byte messageDigest[] = algorithm.digest();
                        System.out.println("actual encrypt::" + messageDigest);
                        StringBuffer hexString = new StringBuffer();
                        for (int i = 0; i < messageDigest.length; i++) {
                            hexString.append(Integer.toHexString(0xFF &
                                                                 messageDigest[i]));
                        }
                        strPassword = new String(hexString);
                        //System.out.println(strPassword);
                    } catch (NoSuchAlgorithmException nsae) {
                        System.out.println(nsae);
                    }

                    try {


                        String sql =
                            "select * from SEC_MST_USERS where USER_ID=? and USER_PASSWORD=?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, strID);
                        statement.setString(2, strPassword);
                        //connection.clearWarnings();

                        results = statement.executeQuery();

                        if (results.next()) // login was successful
                        {
                            System.out.println("login success");


                            String active = results.getString("LOGIN_ENABLED");
                            if (active == null ||
                                active.equalsIgnoreCase("0")) {
                                response.sendRedirect("index.jsp?message=logindisabled");
                                return;
                            }

                            loginflag = true;
                            empid = results.getInt("Employee_Id");
                            status = results.getString("CHANGE_PASSWORD");
                            //System.out.println("status of pass change:"+status);
                            int categoryid = 1;
                            try {
                                categoryid =
                                        results.getInt("USER_CATEGORY_ID");
                            } catch (Exception e) {
                                System.out.println("Error in category id:" +
                                                   e);
                            }

                            results.close();
                            statement.close();

                            System.out.println("Login part is ok");


                            UserProfile up = null;
                            ResultSet profile = null;

                            if (categoryid == 1) {
                                sql =
 "select * from VIEW_EMPLOYEE1 where Employee_Id=?";
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, empid);
                                System.out.print("employee id : " + empid);

                                try {

                                    profile = statement.executeQuery();
                                    // creating user profile object
                                    if (!profile.next()) {
                                        response.sendRedirect("index.jsp?message=noprofile");
                                        return;
                                    }


                                    //System.out.println("empid:"+profile.getInt("Employee_Id"));
                                    up =
  new UserProfile(profile.getInt("Employee_Id"),
                  (profile.getString("Employee_Initial") == null) ? "" :
                  profile.getString("Employee_Initial"),
                  profile.getString("Employee_Name"),
                  profile.getString("Employee_Prefix"),
                  profile.getString("Designation"),
                  profile.getString("Office_Level"),
                  profile.getString("Office_Short_Name"),
                  profile.getString("Office_Address"));
                                    System.out.println("Employee sucessfully loaded");
                                    //System.out.println("Employee Name : " + up.getEmployeeName());

                                    profile.close();
                                    statement.close();

                                    session = request.getSession(true);
                                    // session.setMaxInactiveInterval(5);
                                    // System.out.println("New session id:"+session.getId());

                                    sql =
 "select max(ACCESS_SEQ_NUM)  acc_seq from SEC_MST_USERS_LOGIN_HISTORY";
                                    PreparedStatement ps =
                                        connection.prepareStatement(sql);
                                    ResultSet rset = ps.executeQuery();
                                    rset.next();
                                    int accno = rset.getInt("acc_seq");
                                    if (accno > 0) {
                                        accno += 1;
                                    } else {
                                        accno = 1;
                                    }

                                    System.out.println("Acc No:" + accno);
                                    sql =
 "insert into sec_mst_users_login_history(USER_ID,LOGGED_IN_TIME,IP_ADDRESS,SESSION_ID,ACCESS_SEQ_NUM) values(?,?,?,?,?)";
                                    ps = connection.prepareStatement(sql);
                                    ps.setString(1, strID);
                                    java.sql.Date dt =
                                        new java.sql.Date(System.currentTimeMillis());
                                    Timestamp tms =
                                        new Timestamp(System.currentTimeMillis());
                                    ps.setTimestamp(2, tms);
                                    ps.setString(3, Remote_host);
                                    ps.setString(4, session.getId());
                                    ps.setInt(5, accno);
                                    ps.executeUpdate();

                                    session.setAttribute("accno",
                                                         String.valueOf(accno));

                                    session.setAttribute("UserProfile", up);
                                    session.setAttribute("UserId", strID);

                                    System.out.println("profile from VIEW TWAD");
                                    session.setAttribute("profile", "twad");

                                } catch (Exception e) {
                                    System.out.println(e);
                                    response.sendRedirect("index.jsp?message=noprofile");
                                    return;
                                }
                            } else {

                                // sql="select SEC_MST_OTHER_USERS_PROFILE.* from SEC_MST_OTHER_USERS_PROFILE, where USER_ID=?";
                                sql =
 "select SEC_MST_OTHER_USERS_PROFILE.*,SEC_MST_USER_CATEGORY.USER_CATEGORY_DESC " +
   " from SEC_MST_OTHER_USERS_PROFILE,SEC_MST_USER_CATEGORY " +
   " where SEC_MST_OTHER_USERS_PROFILE.USER_ID=? " +
   " and SEC_MST_USER_CATEGORY.USER_CATEGORY_ID =?";
                                statement = connection.prepareStatement(sql);
                                statement.setString(1, strID);
                                statement.setInt(2, categoryid);
                                System.out.print("employee id : " + empid);

                                try {

                                    profile = statement.executeQuery();
                                    // creating user profile object
                                    if (!profile.next()) {
                                        response.sendRedirect("index.jsp?message=noprofile");
                                        return;
                                    }


                                    //System.out.println("empid:"+profile.getInt("Employee_Id"));
                                    up =
  new UserProfile(0, (profile.getString("USER_INITIAL") == null) ? "" :
                     profile.getString("USER_INITIAL"),
                  profile.getString("USER_NAME"),
                  profile.getString("USER_PREFIX"),
                  profile.getString("DESIGNATION"),
                  profile.getString("USER_CATEGORY_DESC"),
                  profile.getString("OFFICE_NAME"),
                  profile.getString("OFFICE_ADDRESS"));
                                    System.out.println("Employee sucessfully loaded");
                                    //System.out.println("Employee Name : " + up.getEmployeeName());

                                    profile.close();
                                    statement.close();

                                    session = request.getSession(true);
                                    // session.setMaxInactiveInterval(5);
                                    // System.out.println("New session id:"+session.getId());

                                    sql =
 "select max(ACCESS_SEQ_NUM)  acc_seq from SEC_MST_USERS_LOGIN_HISTORY";
                                    PreparedStatement ps =
                                        connection.prepareStatement(sql);
                                    ResultSet rset = ps.executeQuery();
                                    rset.next();
                                    int accno = rset.getInt("acc_seq");
                                    if (accno > 0) {
                                        accno += 1;
                                    } else {
                                        accno = 1;
                                    }

                                    System.out.println("Acc No:" + accno);
                                    sql =
 "insert into sec_mst_users_login_history(USER_ID,LOGGED_IN_TIME,IP_ADDRESS,SESSION_ID,ACCESS_SEQ_NUM) values(?,?,?,?,?)";
                                    ps = connection.prepareStatement(sql);
                                    ps.setString(1, strID);
                                    java.sql.Date dt =
                                        new java.sql.Date(System.currentTimeMillis());
                                    Timestamp tms =
                                        new Timestamp(System.currentTimeMillis());
                                    ps.setTimestamp(2, tms);
                                    ps.setString(3, Remote_host);
                                    ps.setString(4, session.getId());
                                    ps.setInt(5, accno);
                                    ps.executeUpdate();

                                    session.setAttribute("accno",
                                                         String.valueOf(accno));

                                    session.setAttribute("UserProfile", up);
                                    session.setAttribute("UserId", strID);

                                    System.out.println("profile from SEC_MST_OTHER_USERS_PROFILE");
                                    session.setAttribute("profile", "other");

                                } catch (Exception e) {
                                    System.out.println(e);
                                    response.sendRedirect("index.jsp?message=noprofile");
                                    return;
                                }


                            }

                            String path =
                                "/ServletLogin.con?session=" + session.getId() +
                                "&empid=" + empid + "&status=" + status;
                            path = response.encodeRedirectURL(path);
                            //response.sendRedirect(request.getContextPath()+"/"+path);
                            try {
                                System.out.println("path:" + path);
                                response.sendRedirect(request.getContextPath() +
                                                      path);
                                //return;
                                System.out.println("After redirect");
                                // response.sendRedirect(request.getContextPath()+"/index.jsp?message=yes");
                            } catch (Exception e) {
                                System.out.println("Redirect Error:" + e);
                            }
                            return;


                        } else {
                            System.out.println("Login failure");
                            response.sendRedirect(request.getContextPath() +
                                                  "/index.jsp?message=yes");
                            //System.out.println("ok");
                            System.out.println("After Dispatching");
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println("authentication error:" + e);
                        response.sendRedirect(request.getContextPath() +
                                              "/index.jsp?message=dbnill");
                        return;
                    }
                } else {
                    response.sendRedirect(request.getContextPath() +
                                          "/index.jsp?message=yes");
                    return;

                }
            } else {
                session.invalidate();
                System.out.println("session is invalidated in uservalidation form");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
    }
}

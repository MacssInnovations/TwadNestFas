package Servlets.SysAdmin.servlets;

import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.*;


public class ServletUpdatingRoles_FAS extends HttpServlet {


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        /// opening connection to the database

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        PreparedStatement ps = null;
        try {
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //connection=DriverManager.getConnection("Jdbc:Odbc:twad");
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

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());


            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                //System.out.println("Exception in creating statement:"+e);
            }
        } catch (Exception e) {
            //System.out.println("Exception in openeing connection:"+e);
        }
        //System.out.println("servlet entered");
        String strCommand = "";
        String xml = "";
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);
        String userid = (String)(session.getAttribute("userid"));
        System.out.println("updating roles");

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(strCommand);
        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            int strSysId = 0;
            int strName = 0;
            String strDesc = "";
            String strSubSysShortDesc = "";
            String strValidation = "";
            boolean sound = false;
            int strOrderseq = 0;
            String LoginId = "";
            try {
                strName = Integer.parseInt(request.getParameter("txtName"));
                strOrderseq =
                        Integer.parseInt(request.getParameter("Orderseq"));
                strDesc = request.getParameter("txtDesc");
                System.out.println("StrName" + strName);
                System.out.println("StrDesc" + strDesc);
                //strSubSysShortDesc = request.getParameter("txtSubSysShortDesc");

                LoginId = request.getParameter("txtUserId");
                System.out.println("loginid:" + LoginId);
                strSysId = Integer.parseInt(request.getParameter("txtSysId"));

            } catch (Exception e) {
                //System.out.println("exce **** "+ e);
            }


            // insert values into the table
            int profile = 0;
            try {
                ps =
  connection.prepareStatement("select USER_CATEGORY_ID from SEC_MST_USERS where USER_ID=?");
                ps.setString(1, LoginId);
                results = ps.executeQuery();
                if (results.next()) {

                    profile = results.getInt("USER_CATEGORY_ID");
                }

                System.out.println("Profile:" + profile);
                if (profile == 1) {

                    String sql =
                        "insert into SEC_MST_USER_ROLES(Role_Id,Employee_Id,LIST_SEQ_NO,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?)";
                    //String sql2="select Role_Id from SEC_MST_ROLES where Role_Id=?";
                    //String sql1="insert into SEC_MST_ROLES(Role_Id,Role_Name)  values(?,?)";

                    long k = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(k);
                    sound = false;
                    String name = "";
                    java.sql.Date today =
                        new java.sql.Date(System.currentTimeMillis());

                    session = request.getSession(false);
                    String updatedby = (String)session.getAttribute("UserId");


                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strName);
                    ps.setInt(2, strSysId);
                    ps.setInt(3, strOrderseq);

                    ps.setString(4, updatedby);
                    ps.setTimestamp(5, ts);

                    ps.executeUpdate();
                    ps.close();
                    xml =
 xml + "<flag>success</flag><RoleId>" + strName + "</RoleId><RoleName>" +
   strDesc + "</RoleName><Orderseq>" + strOrderseq + "</Orderseq>";
                    //ps.close();


                } else {

                    String sql =
                        "insert into SEC_MST_OTHER_USER_ROLES(Role_Id,USER_ID,LIST_SEQ_NO,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?)";
                    //String sql2="select Role_Id from SEC_MST_ROLES where Role_Id=?";
                    //String sql1="insert into SEC_MST_ROLES(Role_Id,Role_Name)  values(?,?)";

                    long k = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(k);
                    sound = false;
                    String name = "";
                    java.sql.Date today =
                        new java.sql.Date(System.currentTimeMillis());

                    session = request.getSession(false);
                    String updatedby = (String)session.getAttribute("UserId");


                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strName);
                    ps.setString(2, LoginId);
                    ps.setInt(3, strOrderseq);

                    ps.setString(4, updatedby);
                    ps.setTimestamp(5, ts);

                    ps.executeUpdate();
                    ps.close();
                    xml =
 xml + "<flag>success</flag><RoleId>" + strName + "</RoleId><RoleName>" +
   strDesc + "</RoleName><Orderseq>" + strOrderseq + "</Orderseq>";
                    //ps.close();


                }
            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            /* try {

                      ps=connection.prepareStatement(sql2);
                      ps.setInt(1,strName);
                      results=ps.executeQuery();
                      while(!results.next()) {
                          ps=connection.prepareStatement(sql1);
                          ps.setInt(1,strName);
                          ps.setString(2,strDesc);
                          ps.executeUpdate();
                          System.out.println("Inner While");
                      }
                     // xml=xml+"<flag>success</flag><RoleId>"+strName+"</RoleId><RoleName>"+strDesc+"</strDesc>";
      }catch(Exception e) {
          //xml=xml+"<flag>failure</flag>";
      }*/
            // on sucess
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("Delete")) {
            // read the parameter id

            xml = "<response><command>Delete</command>";
            int strSysId = 0;
            String strName = "";
            String LoginId = "";
            try {
                strName = request.getParameter("txtName");
                System.out.println("StrName" + strName);
                LoginId = request.getParameter("txtUserId");
                System.out.println("loginid:" + LoginId);
                strSysId = Integer.parseInt(request.getParameter("txtSysId"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            // delete the row from table
            int profile = 0;
            try {
                ps =
  connection.prepareStatement("select USER_CATEGORY_ID from SEC_MST_USERS where USER_ID=?");
                ps.setString(1, LoginId);
                results = ps.executeQuery();
                if (results.next()) {

                    profile = results.getInt("USER_CATEGORY_ID");
                }

                System.out.println("Profile:" + profile);
                if (profile == 1) {
                    //String sql="delete from SECURITY_MST_ROLES where Role_Id=?";
                    String sql1 =
                        "delete from SEC_MST_USER_ROLES where Role_Id=? and Employee_Id=?";
                    //ps=connection.prepareStatement(sql);
                    //ps.setString(1,strSysId);
                    //ps.setString(1,strName);
                    //int i=ps.executeUpdate();
                    //ps.close();i>=1 &&
                    ps = connection.prepareStatement(sql1);
                    ps.setString(1, strName);
                    ps.setInt(2, strSysId);
                    int j = ps.executeUpdate();
                    // on sucess
                    // build and return the xml with id
                    if (j >= 1) {
                        xml =
 xml + "<flag>success</flag><RoleId>" + strName + "</RoleId>";
                        connection.commit();
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                } else {
                    //String sql="delete from SECURITY_MST_ROLES where Role_Id=?";
                    String sql1 =
                        "delete from SEC_MST_OTHER_USER_ROLES where Role_Id=? and USER_ID=?";
                    //ps=connection.prepareStatement(sql);
                    //ps.setString(1,strSysId);
                    //ps.setString(1,strName);
                    //int i=ps.executeUpdate();
                    //ps.close();i>=1 &&
                    ps = connection.prepareStatement(sql1);
                    ps.setString(1, strName);
                    ps.setString(2, LoginId);
                    int j = ps.executeUpdate();
                    // on sucess
                    // build and return the xml with id
                    if (j >= 1) {
                        xml =
 xml + "<flag>success</flag><RoleId>" + strName + "</RoleId>";
                        connection.commit();
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (strCommand.equals("combo")) {
            System.out.println("servlet called");
            try {
                xml = "<response><command>combo</command>";
                ps =
  connection.prepareStatement("select a.Employee_Id,a.Employee_Name||decode(a.employee_initial,null,' ','.'||a.employee_initial) Employee_Name from HRM_MST_EMPLOYEES a " +
                              " inner join HRM_EMP_CURRENT_POSTING b on b.EMPLOYEE_ID=a.EMPLOYEE_ID " +
                              " where b.OFFICE_ID is not null and b.DESIGNATION_ID is not null order by Employee_Name");
                results = ps.executeQuery();
                while (results.next()) {
                    xml =
 xml + "<value>" + results.getString("Employee_Id") + "</value><EmpName>" +
   results.getString("Employee_Name") + "(" +
   results.getString("Employee_Id") + ")" + "</EmpName>";
                }
                ps.close();
                results.close();


            } catch (Exception e) {
                e.printStackTrace();
                xml = xml + "<value>Failure</value>";

            }

            xml = xml + "</response>";


            //System.out.println("Combo");
        } else if (strCommand.equals("Login")) {
            System.out.println("servlet Login");
            try {
                xml = "<response><command>Login</command>";
                String LoginId = request.getParameter("txtEmpId");
                System.out.println("Empid id" + LoginId);
                //ps=connection.prepareStatement("select Role_Id,Role_Name from SEC_MST_ROLES where Role_Id in(select Role_Id from SEC_MST_USER_ROLES where Employee_Id=?)");
                //ps=connection.prepareStatement("select a.list_seq_no,a.role_id,b.role_name,c.Employee_Name from sec_mst_user_roles a left outer join sec_mst_roles b on a.role_id=b.role_id left outer join hrm_mst_employees c on c.Employee_Id=a.Employee_Id where b.role_id in a.role_id and a.employee_id=? order by a.role_id");
                ps =
  connection.prepareStatement("select a.list_seq_no,a.role_id,c.Employee_Name||decode(c.employee_initial,null,' ','.'||c.employee_initial) Employee_Name,b.role_name from " +
                              "hrm_mst_employees c left outer join sec_mst_user_roles a on c.employee_id=a.employee_id left outer join sec_mst_roles b on " +
                              "b.role_id=a.role_id " +
                              "where c.employee_id=? order by a.role_id");
                ps.setString(1, LoginId);
                System.out.println("Hai");
                results = ps.executeQuery();
                while (results.next()) {
                    System.out.println("Hai");
                    xml =
 xml + "<RoleId>" + results.getString("Role_Id") + "</RoleId><RoleName>" +
   results.getString("Role_Name") + "</RoleName><OrderSeqNO>" +
   results.getInt("list_seq_no") + "</OrderSeqNO><EmpName>" +
   results.getString("Employee_Name") + "</EmpName>";
                }


                /*PreparedStatement ps1=connection.prepareStatement("SELECT [Designation] FROM HR_MST_DESIGNATIONS WHERE Designation_Id=(select Designation_Id from HR_MST_EMPLOYEES where Employee_Id=?)");
            ps1.setString(1,LoginId);
            ResultSet results1=ps1.executeQuery();
            if(results1.next()) {
                xml=xml+"<Designation>"+results1.getString("Designation")+"</Designation>";
            }
            PreparedStatement ps2=connection.prepareStatement("SELECT [Role_Id], [Role_Name] FROM SECURITY_MST_ROLES WHERE Role_Id in(select Role_Id from SECURITY_MST_LOGIN_ROLES where Employee_Id=?)");
            ps2.setString(1,LoginId);
            ResultSet results2=ps2.executeQuery();
            while(results2.next()) {
                  xml=xml+"<RoleId>"+results2.getString("Role_Id")+"</RoleId>";
            }*/

                //select the role id from EmployeeId without Assigning him
                ps =
  connection.prepareStatement("select Role_Id,Role_Name from SEC_MST_ROLES_RESTRICT where Role_Id not in(select Role_Id from SEC_MST_USER_ROLES where Employee_Id=?)");
                ps.setString(1, LoginId);
                results = ps.executeQuery();
                while (results.next()) {
                    xml =
 xml + "<RoleValue>" + results.getString("Role_Id") + "</RoleValue><RoleName1>" +
   results.getString("Role_Name") + "</RoleName1>";
                }

            } catch (Exception e) {
                System.out.println("Exception in Login" + e);

            }

            xml = xml + "</response>";

        } else if (strCommand.equals("LoginUid")) {
            System.out.println("servlet Login Userid");
            try {
                xml = "<response><command>LoginUid</command>";
                String LoginId = request.getParameter("txtUserId");
                System.out.println("txtUserId " + LoginId);
                //ps=connection.prepareStatement("select Role_Id,Role_Name from SEC_MST_ROLES where Role_Id in(select Role_Id from SEC_MST_USER_ROLES where Employee_Id=?)");
                //ps=connection.prepareStatement("select a.list_seq_no,a.role_id,b.role_name,c.Employee_Name from sec_mst_user_roles a left outer join sec_mst_roles b on a.role_id=b.role_id left outer join hrm_mst_employees c on c.Employee_Id=a.Employee_Id where b.role_id in a.role_id and a.employee_id=? order by a.role_id");
                int profile = 0;
                ps =
  connection.prepareStatement("select USER_CATEGORY_ID from SEC_MST_USERS where USER_ID=?");
                ps.setString(1, LoginId);
                results = ps.executeQuery();
                if (results.next()) {

                    profile = results.getInt("USER_CATEGORY_ID");
                }

                System.out.println("Profile:" + profile);
                if (profile == 1) {
                    ps =
  connection.prepareStatement("select EMPLOYEE_ID from SEC_MST_USERS where USER_ID=?");
                    ps.setString(1, LoginId);
                    results = ps.executeQuery();
                    int empid = 0;
                    if (results.next()) {

                        empid = results.getInt("EMPLOYEE_ID");
                        xml = xml + "<empid>" + empid + "</empid>";

                        ps =
  connection.prepareStatement("select Employee_Name||decode(employee_initial,null,' ','.'||employee_initial) Employee_Name from hrm_mst_employees where employee_id=?");
                        ps.setInt(1, empid);
                        results = ps.executeQuery();
                        if (results.next()) {
                            xml =
 xml + "<EmpName>" + results.getString("Employee_Name") + "</EmpName>";
                        }

                        ps =
  connection.prepareStatement("select a.list_seq_no,a.role_id,b.role_name from " +
                              " sec_mst_user_roles a  inner join sec_mst_roles b on " +
                              "b.role_id=a.role_id " +
                              "where a.employee_id=? order by a.role_id");
                        ps.setInt(1, empid);
                        results = ps.executeQuery();
                        while (results.next()) {

                            xml =
 xml + "<RoleId>" + results.getString("Role_Id") + "</RoleId><RoleName>" +
   results.getString("Role_Name") + "</RoleName><OrderSeqNO>" +
   results.getInt("list_seq_no") + "</OrderSeqNO>";
                        }

                        //select the role id from EmployeeId without Assigning him
                        ps =
  connection.prepareStatement("select Role_Id,Role_Name from SEC_MST_ROLES_RESTRICT where Role_Id not in(select Role_Id from SEC_MST_USER_ROLES where Employee_Id=?)");
                        ps.setInt(1, empid);
                        results = ps.executeQuery();
                        while (results.next()) {
                            xml =
 xml + "<RoleValue>" + results.getString("Role_Id") +
   "</RoleValue><RoleName1>" + results.getString("Role_Name") + "</RoleName1>";
                        }

                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml =
 "<response><command>LoginUid</command><flag>NoRecord</flag>";
                    }
                } else {


                    xml = xml + "<empid>" + 0 + "</empid>";
                    System.out.println("test1");
                    ps =
  connection.prepareStatement("select USER_NAME||decode(USER_INITIAL,null,' ','.'||USER_INITIAL) Employee_Name from SEC_MST_OTHER_USERS_PROFILE where USER_ID=?");
                    ps.setString(1, LoginId);
                    results = ps.executeQuery();
                    if (results.next()) {
                        xml =
 xml + "<EmpName>" + results.getString("Employee_Name") + "</EmpName>";
                        System.out.println(results.getString("Employee_Name"));
                    }

                    ps =
  connection.prepareStatement("select a.list_seq_no,a.role_id,b.role_name from " +
                              " SEC_MST_OTHER_USER_ROLES a  inner join sec_mst_roles b on " +
                              "b.role_id=a.role_id " +
                              "where a.USER_ID=? order by a.role_id");
                    ps.setString(1, LoginId);
                    results = ps.executeQuery();
                    while (results.next()) {

                        xml =
 xml + "<RoleId>" + results.getString("Role_Id") + "</RoleId><RoleName>" +
   results.getString("Role_Name") + "</RoleName><OrderSeqNO>" +
   results.getInt("list_seq_no") + "</OrderSeqNO>";
                    }

                    //select the role id from EmployeeId without Assigning him
                    ps =
  connection.prepareStatement("select Role_Id,Role_Name from SEC_MST_ROLES_RESTRICT where Role_Id not in(select Role_Id from SEC_MST_OTHER_USER_ROLES where USER_ID=?)");
                    ps.setString(1, LoginId);
                    results = ps.executeQuery();
                    while (results.next()) {
                        xml =
 xml + "<RoleValue>" + results.getString("Role_Id") +
   "</RoleValue><RoleName1>" + results.getString("Role_Name") + "</RoleName1>";
                    }

                    xml = xml + "<flag>success</flag>";

                }

                /*PreparedStatement ps1=connection.prepareStatement("SELECT [Designation] FROM HR_MST_DESIGNATIONS WHERE Designation_Id=(select Designation_Id from HR_MST_EMPLOYEES where Employee_Id=?)");
              ps1.setString(1,LoginId);
              ResultSet results1=ps1.executeQuery();
              if(results1.next()) {
                  xml=xml+"<Designation>"+results1.getString("Designation")+"</Designation>";
              }
              PreparedStatement ps2=connection.prepareStatement("SELECT [Role_Id], [Role_Name] FROM SECURITY_MST_ROLES WHERE Role_Id in(select Role_Id from SECURITY_MST_LOGIN_ROLES where Employee_Id=?)");
              ps2.setString(1,LoginId);
              ResultSet results2=ps2.executeQuery();
              while(results2.next()) {
                    xml=xml+"<RoleId>"+results2.getString("Role_Id")+"</RoleId>";
              }*/


            } catch (Exception e) {
                System.out.println("Exception in Login" + e);
                xml =
 "<response><command>LoginUid</command><flag>failure</flag>";
            }

            xml = xml + "</response>";

        }
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    } //doget


}// class


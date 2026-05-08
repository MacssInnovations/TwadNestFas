package Servlets.SysAdmin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Bulk_Userid_Create
 */
public class Bulk_Userid_Create extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bulk_Userid_Create() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        PrintWriter out = response.getWriter();
        Connection connection = null;
        //select * from hrm_emp_current_posting where to_char(employee_id) not in(select wm_concat(employee_id) from sec_mst_users)
        //select * from hrm_emp_current_posting where to_char(employee_id) not in(select wm_concat(employee_id) from sec_mst_users) and
        // designation_id=7 and employee_status_id not in('VRS','RES','DIS','SAN','DTH')
        ResultSet rs1 = null, rs2 = null, rs3 = null;
        CallableStatement cs = null;
        PreparedStatement ps = null, ps1 = null, ps2 = null;
        response.setContentType(CONTENT_TYPE);

        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
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

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        String employeeIdCon = "";
        String xml = "";
        String command;
        command = request.getParameter("command");
        session = request.getSession(false);
        String updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);
        if (command.equalsIgnoreCase("Get")) {
            try {

                //int officeId=Integer.parseInt(request.getParameter("officeid"));
                int designation =
                    Integer.parseInt(request.getParameter("desig").trim());

                /*  ps=connection.prepareStatement("select decode(employee_id,null,0,employee_id) from sec_mst_users");
		        	    rs1=ps.executeQuery();
		        	   	        	
		        	    while(rs1.next())
		        	    {
		        	    		if(employeeIdCon==""){
		        	    			employeeIdCon=new Integer(rs1.getInt(1)).toString();
		        	    		}else{
		        	    			employeeIdCon+=","+new Integer(rs1.getInt(1)).toString();
		        	    		}
		        	    }	
		        	
        	  		        	    		        	    		        	
		        	   rs1.close();
		        	   ps.close();*/
                ps =
  connection.prepareStatement("select employee_id,decode(office_id,null,0,office_id) as office_id,designation_id from hrm_emp_current_posting where to_char(employee_id) not in (select decode(employee_id,null,0,employee_id) from sec_mst_users) and designation_id=? and employee_status_id not in('VRS','RES','DIS','SAN','DTH')");
                ps.setInt(1, designation);

                rs1 = ps.executeQuery();
                xml = "<response><command>get</command>";
                xml = xml + "<flag>success</flag>";

                while (rs1.next()) {
                    int employeeId = rs1.getInt("employee_id");
                    int officeId = rs1.getInt("office_id");
                    xml = xml + "<emp_id>" + employeeId + " </emp_id>";
                    xml =
 xml + "<desig_id>" + rs1.getString("designation_id") + " </desig_id>";
                    ps1 =
 connection.prepareStatement("Select employee_name from hrm_mst_employees where employee_id=?");
                    ps1.setInt(1, employeeId);
                    rs2 = ps1.executeQuery();
                    if (rs2.next()) {
                        xml =
 xml + "<emp_name>" + rs2.getString("employee_name") + " </emp_name>";
                    } else {
                        xml = xml + "<emp_name>Not Mentioned</emp_name>";
                    }
                    ps1.close();
                    rs2.close();
                    if (officeId != 0) {
                        ps2 =
 connection.prepareStatement("Select distinct office_name from com_mst_offices where office_id=?");
                        ps2.setInt(1, officeId);
                        rs3 = ps2.executeQuery();
                        if (rs3.next()) {
                            xml =
 xml + "<office_name>" + rs3.getString("office_name") + " </office_name>";
                        } else {
                            xml =
 xml + "<office_name>Not Mentioned </office_name>";
                        }
                        ps2.close();
                        rs3.close();
                    } else {
                        xml =
 xml + "<office_name>Not Mentioned </office_name>";
                    }

                }

                rs1.close();
                ps.close();


            }

            catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                e.printStackTrace();
            }
            xml = xml + "</response>";

            System.out.println(xml);
            out.println(xml);
        } else if (command.equalsIgnoreCase("create")) {
            try {
                xml = "";
                int designation =
                    Integer.parseInt(request.getParameter("desig").trim());

                System.out.println("Designation in create" + designation);

                xml = "<response><command>create</command>";


                /* ps=connection.prepareStatement("select decode(employee_id,null,0,employee_id) from sec_mst_users");
	        	    rs1=ps.executeQuery();
	        	   	        	
	        	    while(rs1.next())
	        	    {
	        	    		if(employeeIdCon==""){
	        	    			employeeIdCon=new Integer(rs1.getInt(1)).toString();
	        	    		}else{
	        	    			employeeIdCon+=","+new Integer(rs1.getInt(1)).toString();
	        	    		}
	        	    }	
	        	 */
                String sqlString =
                    "select employee_id,decode(office_id,null,0,office_id) as office_id,designation_id from hrm_emp_current_posting where to_char(employee_id) not in (select decode(employee_id,null,0,employee_id) from sec_mst_users) and designation_id=" +
                    designation +
                    " and employee_status_id not in('VRS','RES','DIS','SAN','DTH')";
                // ps=connection.prepareStatement("select employee_id from hrm_emp_current_posting where to_char(employee_id) not in ("+employeeIdCon+") and designation_id=? and employee_status_id not in('VRS','RES','DIS','SAN','DTH')");
                //ps.setInt(1,designation);
                Statement stmt =
                    connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                               ResultSet.CONCUR_READ_ONLY);
                rs1 = stmt.executeQuery(sqlString);
                //rs1=ps.executeQuery();

                rs1.last();
                int count = rs1.getRow();
                rs1.beforeFirst();
                System.out.println("Couunt " + count);
                int empl[] = new int[count];
                int i = 0;
                while (rs1.next()) {
                    empl[i] = rs1.getInt("employee_id");
                    i++;

                }
                rs1.close();
                stmt.close();
                String LoginId = "";
                String txtConfirmPassword = "";
                for (int j = 0; j < empl.length; j++) {
                    LoginId = "twad" + empl[j];


                    txtConfirmPassword = LoginId;

                    byte[] b = txtConfirmPassword.getBytes();
                    try {
                        MessageDigest algorithm =
                            MessageDigest.getInstance("MD5");
                        algorithm.reset();
                        algorithm.update(b);
                        byte messageDigest[] = algorithm.digest();
                        System.out.println("actual encrypt::" + messageDigest);
                        StringBuffer hexString = new StringBuffer();
                        for (i = 0; i < messageDigest.length; i++) {
                            hexString.append(Integer.toHexString(0xFF &
                                                                 messageDigest[i]));
                        }
                        txtConfirmPassword = new String(hexString);
                    } catch (NoSuchAlgorithmException nsae) {
                        System.out.println(nsae);
                    }
                    System.out.println("Login ID" + LoginId);
                    System.out.println("Password" + txtConfirmPassword);

                    ps =
  connection.prepareStatement("insert into SEC_MST_USERS(USER_ID,USER_PASSWORD,EMPLOYEE_ID,LOGIN_ENABLED,UPDATED_BY_USER_ID,UPDATED_DATE,USER_CATEGORY_ID) values(?,?,?,'1',?,?,1)");
                    ps.setString(1, LoginId);
                    ps.setString(2, txtConfirmPassword);
                    ps.setInt(3, empl[j]);

                    ps.setString(4, updatedby);
                    ps.setTimestamp(5, ts);

                    ps.executeUpdate();
                    ps.close();


                    ps =
  connection.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                    ps.setInt(1, empl[j]);
                    ps.setInt(2, 22);
                    ps.setString(3, updatedby);
                    ps.setTimestamp(4, ts);
                    ps.setInt(5, 999);
                    ps.executeUpdate();
                    ps.close();
                    System.out.println("help role is added");

                }

                xml = xml + "<flag>success</flag>";


            } catch (Exception e) {
                System.out.println("Error" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }


    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        // TODO Auto-generated method stub
    }

}

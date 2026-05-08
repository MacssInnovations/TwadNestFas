package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class charge_type_details extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        //System.out.println("sdsdsd");
        String command_var = "";
        String xmlvariable = "";
        int txtAcc_HeadCode = 0;
        int txtAcc_HeadCode_dr = 0;
        int charge_type_Id = 0;
        int flagvar = 0;
        String charge_type_desc = "";
        String validheaddesc = "";
        Connection connection = null;
        PreparedStatement ps, ps1;
        ResultSet res = null;
        ps = null;
        ps1 = null;
        try {
            command_var = request.getParameter("command");
            System.out.println(command_var);
            txtAcc_HeadCode =
                    Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            txtAcc_HeadCode_dr =
                    Integer.parseInt(request.getParameter("txtAcc_HeadCode_dr"));
            charge_type_desc = request.getParameter("charge_type_desc");
            System.out.println(txtAcc_HeadCode_dr);

        } catch (Exception e) {
            System.out.println("Error in reterival:" + e);
        }
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strdsn = rs.getString("Config.DSN"); //jdbc:oracle:thin:
            String strhostname =
                rs.getString("Config.HOST_NAME"); //10.163.0.58
            String strportno = rs.getString("Config.PORT_NUMBER"); //1521
            String strsid = rs.getString("Config.SID"); //twadnest
            String strDriver =
                rs.getString("Config.DATA_BASE_DRIVER"); //oracle.jdbc.OracleDriver
            String strdbusername = rs.getString("Config.USER_NAME"); //twadpms
            String strdbpassword =
                rs.getString("Config.PASSWORD"); //twadpms123
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            System.out.println("Paid by master");
            try {
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        /*HttpSession session=request.getSession(false);
        try
        {
              if(session==null)
              {
                     System.out.println(request.getContextPath()+"/index.jsp");
                     response.sendRedirect(request.getContextPath()+"/index.jsp");
               }
                System.out.println(session);

        }
        catch(Exception e)
        {
           System.out.println("Redirect Error :"+e);
        }
        String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:"+userid);*/

        /*HttpSession session=request.getSession(false);
         try
         {
             if(session==null)
             {
                 System.out.println("session out");
                 return;
             }
             System.out.println(session);
         }
         catch(Exception e)
         {
                 System.out.println("Redirect Error :"+e);
         }
         String updatedby=(String)session.getAttribute("UserId");
        */
        if (command_var.equalsIgnoreCase("reterive")) {
            System.out.println("getting value");
            xmlvariable = "<response>";
            xmlvariable += "<command>reterive</command>";
            try {
                ps =
  connection.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS WHERE ACCOUNT_HEAD_CODE = ? ");
                ps.setInt(1, txtAcc_HeadCode);
                res = ps.executeQuery();


                while (res.next()) {

                    System.out.println(res.getInt("ACCOUNT_HEAD_CODE"));
                    System.out.println(res.getString("ACCOUNT_HEAD_DESC"));
                    xmlvariable +=
                            "<txtAcc_HeadCode>" + res.getInt("ACCOUNT_HEAD_CODE") +
                            "</txtAcc_HeadCode>";
                    xmlvariable +=
                            "<txtAcc_HeadDesc>" + res.getString("ACCOUNT_HEAD_DESC") +
                            "</txtAcc_HeadDesc>";
                    xmlvariable += "<flag>success</flag>";
                    flagvar = 1;
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            if (flagvar == 0) {
                xmlvariable += "<flag>failure</flag>";
                xmlvariable += "<flagvar>Dataerror</flagvar>";
            }


            xmlvariable += "</response>";
            System.out.println(flagvar);
        } else if (command_var.equalsIgnoreCase("Reterivevalue2")) {

            System.out.println("getting value");
            txtAcc_HeadCode_dr =
                    Integer.parseInt(request.getParameter("txtAcc_HeadCode_dr"));
            System.out.println("Inside  val" + txtAcc_HeadCode_dr);
            xmlvariable = "<response>";
            xmlvariable += "<command>Reterivevalue2</command>";
            try {
                ps =
  connection.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS WHERE ACCOUNT_HEAD_CODE = ? ");
                ps.setInt(1, txtAcc_HeadCode_dr);
                res = ps.executeQuery();


                while (res.next()) {

                    System.out.println(res.getInt("ACCOUNT_HEAD_CODE"));
                    System.out.println(res.getString("ACCOUNT_HEAD_DESC"));
                    xmlvariable +=
                            "<txtAcc_HeadCode>" + res.getInt("ACCOUNT_HEAD_CODE") +
                            "</txtAcc_HeadCode>";
                    xmlvariable +=
                            "<txtAcc_HeadDesc>" + res.getString("ACCOUNT_HEAD_DESC") +
                            "</txtAcc_HeadDesc>";
                    xmlvariable += "<flag>success</flag>";
                    flagvar = 1;
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            if (flagvar == 0) {
                xmlvariable += "<flag>failure</flag>";
                xmlvariable += "<flagvar>Dataerror</flagvar>";
            }


            xmlvariable += "</response>";
            System.out.println(flagvar);
        } else if (command_var.equalsIgnoreCase("add")) {
            xmlvariable = "<response>";
            xmlvariable += "<command>add</command>";
            System.out.println(txtAcc_HeadCode);

            System.out.println(txtAcc_HeadCode_dr);
            try {
                ps =
  connection.prepareStatement("insert into PMS_DCB_MST_CHARGES_TYPE(CHARGE_TYPE_DESC,CHARGE_AC_HEAD_ID_CR,UPDATED_BY_USER_ID,UPDATED_TIME,CHARGE_AC_HEAD_ID_DR ) values(?,?,'user',SYSTIMESTAMP,?)");
                ps.setString(1, charge_type_desc);
                ps.setInt(2, txtAcc_HeadCode);
                // ps.setString(3, userid);
                // ps.setInt(5, txtAcc_HeadCode_dr);
                ps.setInt(3, txtAcc_HeadCode_dr);

                ps.executeUpdate();
                System.out.println("Inserted");

                System.out.println("hai");
                ps1 =
 connection.prepareStatement("select max(CHARGE_TYPE_ID) as CHARGE_TYPE_ID from PMS_DCB_MST_CHARGES_TYPE");
                System.out.println(ps1);
                res = ps1.executeQuery();
                charge_type_Id = 0;
                if (res.next()) {
                    System.out.println(res.getInt("CHARGE_TYPE_ID"));
                    charge_type_Id = res.getInt("CHARGE_TYPE_ID");
                }
                xmlvariable +=
                        "<charge_type_Id>" + charge_type_Id + "</charge_type_Id>";
                xmlvariable +=
                        "<charge_type_desc>" + charge_type_desc + "</charge_type_desc>";
                xmlvariable +=
                        "<txtAcc_HeadCode>" + txtAcc_HeadCode + "</txtAcc_HeadCode>";
                xmlvariable +=
                        "<txtAcc_HeadCode_dr>" + txtAcc_HeadCode_dr + "</txtAcc_HeadCode_dr>";
                xmlvariable += "<flag>success</flag>";
            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not inserted!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("get")) {
            System.out.println("getting value");

            xmlvariable = "<response>";
            xmlvariable += "<command>get</command>";
            try {
                ps =
  connection.prepareStatement("select CHARGE_TYPE_ID,CHARGE_TYPE_DESC,CHARGE_AC_HEAD_ID_CR,CHARGE_AC_HEAD_ID_DR from PMS_DCB_MST_CHARGES_TYPE ORDER BY CHARGE_TYPE_ID");
                res = ps.executeQuery();

                while (res.next()) {

                    System.out.println(res.getInt("CHARGE_TYPE_ID"));
                    System.out.println(res.getString("CHARGE_TYPE_DESC"));
                    System.out.println(res.getInt("CHARGE_AC_HEAD_ID_CR"));
                    xmlvariable +=
                            "<charge_by_id>" + res.getInt("CHARGE_TYPE_ID") +
                            "</charge_by_id>";
                    xmlvariable +=
                            "<charge_type_desc>" + res.getString("CHARGE_TYPE_DESC") +
                            "</charge_type_desc>";
                    xmlvariable +=
                            "<txtAcc_HeadCode>" + res.getInt("CHARGE_AC_HEAD_ID_CR") +
                            "</txtAcc_HeadCode>";
                    xmlvariable +=
                            "<txtAcc_HeadCode_dr>" + res.getInt("CHARGE_AC_HEAD_ID_DR") +
                            "</txtAcc_HeadCode_dr>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("update")) {
            System.out.println("Update values");
            xmlvariable = "<response>";
            xmlvariable += "<command>update</command>";
            try {
                charge_type_Id =
                        Integer.parseInt(request.getParameter("charge_type_Id"));
                System.out.println(charge_type_Id);
                System.out.println(charge_type_desc);
                System.out.println(txtAcc_HeadCode);
                System.out.println(txtAcc_HeadCode_dr);

                ps =
  connection.prepareStatement("update PMS_DCB_MST_CHARGES_TYPE set CHARGE_TYPE_DESC=?,CHARGE_AC_HEAD_ID_CR=?,CHARGE_AC_HEAD_ID_DR=? where CHARGE_TYPE_ID =?");
                ps.setString(1, charge_type_desc);
                ps.setInt(2, txtAcc_HeadCode);
                ps.setInt(3, txtAcc_HeadCode_dr);
                ps.setInt(4, charge_type_Id);


                ps.executeUpdate();
                System.out.println("updated");
                xmlvariable +=
                        "<charge_type_Id>" + charge_type_Id + "</charge_type_Id>";
                xmlvariable +=
                        "<charge_type_desc>" + charge_type_desc + "</charge_type_desc>";
                xmlvariable +=
                        "<txtAcc_HeadCode>" + txtAcc_HeadCode + "</txtAcc_HeadCode>";
                xmlvariable +=
                        "<txtAcc_HeadCode_dr>" + txtAcc_HeadCode_dr + "</txtAcc_HeadCode_dr>";

                xmlvariable += "<flag>success</flag>";
            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not updated!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("delete")) {
            xmlvariable = "<response>";
            xmlvariable += "<command>delete</command>";
            try {
                charge_type_Id =
                        Integer.parseInt(request.getParameter("charge_type_Id"));
                ps =
  connection.prepareStatement("delete from PMS_DCB_MST_CHARGES_TYPE where CHARGE_TYPE_ID=?");
                ps.setInt(1, charge_type_Id);
                ps.executeUpdate();
                System.out.println("deleted");
                xmlvariable +=
                        "<charge_by_id>" + charge_type_Id + "</charge_by_id>";
                xmlvariable +=
                        "<charge_type_desc>" + charge_type_desc + "</charge_type_desc>";
                xmlvariable +=
                        "<txtAcc_HeadCode>" + txtAcc_HeadCode + "</txtAcc_HeadCode>";
                xmlvariable +=
                        "<txtAcc_HeadCode_dr>" + txtAcc_HeadCode_dr + "</txtAcc_HeadCode_dr>";
                xmlvariable += "<flag>success</flag>";
            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not deleted!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("comboload")) {
            System.out.println("comboload");
            xmlvariable = "<response>";
            xmlvariable += "<command>comboload</command>";
            try {
                ps =
  connection.prepareStatement("select CHARGE_DESC from PMS_DCB_CHARGE_TYPE_DESC");
                res = ps.executeQuery();
                System.out.println("comboload1");
                while (res.next()) {
                    System.out.println("comboload3");
                    System.out.println(res.getString("CHARGE_DESC"));

                    xmlvariable +=
                            "<CHARGE_TYPE_DESC_TABLE>" + res.getString("CHARGE_DESC") +
                            "</CHARGE_TYPE_DESC_TABLE>>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
        System.out.println(xmlvariable);
        out.println(xmlvariable);
        out.close();
    }
}

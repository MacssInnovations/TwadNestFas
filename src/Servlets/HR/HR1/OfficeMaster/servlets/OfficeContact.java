package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class OfficeContact extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                /* response.setContentType("text/xml");
                response.setHeader("Cache-Control","no-cache");
               String xml="<response><command>session</command><flag>failure</flag><flag>Session already closed.</flag></response>";
               System.out.println(xml);
                out.println(xml);
                out.close();
                return;*/
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String Office_Address1 = "", Office_Address2 = "", Office_Address3 =
            "", Office_EmailId = "", Office_Add_EmailId = "";
        int Office_Id = 0, Office_District_Code = 0, Office_Pin_Code = 0;
        String Office_Phone_No = "", Office_Add_Phone_No = "", Office_Fax_No =
            "", Office_Add_Fax_No = "", OfficeSTDCode = "", Office_STDCode =
            "", Office_PinCode = "";
        try {
            Office_Address1 = request.getParameter("txtOffice_Address1");
            Office_Address2 = request.getParameter("txtOffice_Address2");
            Office_Address3 = request.getParameter("txtOffice_City");
            Office_EmailId = request.getParameter("txtEmailId");
            Office_Add_EmailId = request.getParameter("txtAdd_EmailId");
            Office_PinCode = request.getParameter("txtPin_Code");
            System.out.println("values retrived sucessfully");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(response,
                        "Please Fill in all the required fields. " + e,
                        "back");
        }

        try {
            Office_Id = Integer.parseInt(request.getParameter("txtOffice_Id"));
            System.out.println("Office_Id" + Office_Id);
            Office_District_Code =
                    Integer.parseInt(request.getParameter("cmbDistrict"));
            System.out.println("Office_Code" + Office_District_Code);

            String Office_Phone = request.getParameter("txtPhone_No");
            if (Office_Phone != null && !Office_Phone.equals("")) {
                Office_Phone_No = Office_Phone;
            }
            System.out.println("Office_Phone" + Office_Phone_No);

            String Office_Add = request.getParameter("txtAdd_Phone_No");
            if (Office_Add != null && !Office_Add.equals("")) {
                Office_Add_Phone_No = Office_Add;
            }
            System.out.println("Office_Add" + Office_Add_Phone_No);

            String Fax = request.getParameter("txtFax_No");
            if (Fax != null && !Fax.equals("")) {
                Office_Fax_No = Fax;
            }
            System.out.println("Office_Fax" + Office_Fax_No);

            String Office_Fax = request.getParameter("txtAdd_Fax_No");
            if (Office_Fax != null && !Office_Fax.equals("")) {
                Office_Add_Fax_No = Office_Fax;
            }
            System.out.println("Office_Add_Fax" + Office_Add_Fax_No);
            OfficeSTDCode = request.getParameter("txtStd_Code");
            if (OfficeSTDCode != null && !OfficeSTDCode.equals("")) {
                Office_STDCode = OfficeSTDCode;
            }
            System.out.println("Office Std Code" + Office_STDCode);

            if (Office_PinCode != null && !Office_PinCode.equals("")) {
                Office_Pin_Code = Integer.parseInt(Office_PinCode);
            }
            System.out.println("Office_PinCode" + Office_Pin_Code);
        } catch (NumberFormatException num) {
            System.out.println("Number Format Exeception:" + num);
        }


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
                String sql =
                    "update com_mst_offices set OFFICE_ADDRESS1=?,OFFICE_ADDRESS2=?,CITY_TOWN_NAME=?,DISTRICT_CODE=?,OFFICE_PHONE_NO=?,ADDL_PHONE_NOS=?,OFFICE_EMAIL_ID=?,ADDL_EMAIL_IDS=?,OFFICE_FAX_NO=?,ADDL_FAX_NOS=?,OFFICE_STD_CODE=?,OFFICE_PIN_CODE=? where OFFICE_ID=?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, Office_Address1);
                statement.setString(2, Office_Address2);
                statement.setString(3, Office_Address3);
                statement.setInt(4, Office_District_Code);
                statement.setString(5, Office_Phone_No);
                statement.setString(6, Office_Add_Phone_No);
                statement.setString(7, Office_EmailId);
                statement.setString(8, Office_Add_EmailId);
                statement.setString(9, Office_Fax_No);
                statement.setString(10, Office_Add_Fax_No);
                statement.setString(11, Office_STDCode);
                statement.setInt(12, Office_Pin_Code);
                statement.setInt(13, Office_Id);
                connection.clearWarnings();
                statement.executeUpdate();
                String msg = "Office Details updation was successful.<br>";
                msg = "<br><br><b><p>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Hai" + e);
        }
    }
}

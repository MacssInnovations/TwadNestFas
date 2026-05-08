package Servlets.FAS.FAS1.ContractorsInfoSys.servlets;

import java.lang.IllegalArgumentException;

import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.lang.IllegalArgumentException.*;

import java.util.ResourceBundle;

// Servlet For NewReg.jsp
public class ServletReg extends HttpServlet {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


        // opening connection to the database
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

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);

        System.out.println("servlet calledddddddddddddd");

        String strrad = request.getParameter("radio1");
        System.out.println(strrad);
        if (strrad.equals("New")) {

            //Declaring the Variables
            int strContractorId = 0;
            String strContractorName = "";
            String strCompanyName = "";
            String strAdd1 = "";
            String strAdd2 = "";
            String strAdd3 = "";
            int strpincode = 0;
            String strdistrictcode = "";
            int strphone = 0;
            int strcellno = 0;
            String stremailid = "";
            int dcode = 0;

            int strOfficeId = 0;
            int strYear = 0;
            int strSerialNo = 0;
            Date strDatereg = null;
            int strRefNo = 0;
            String strEntireState = "";
            int classid = 0;
            int strRegFees = 0;
            //String strAddit="";

            try {

                System.out.println("good");
                strContractorId =
                        Integer.parseInt(request.getParameter("txtContId"));
                System.out.println(strContractorId);
                strContractorName = request.getParameter("txtContName");
                System.out.println(strContractorName);
                strCompanyName = request.getParameter("txtCompName");
                System.out.println(strCompanyName);
                strAdd1 = request.getParameter("txtadd1");
                System.out.println(strAdd1);
                strAdd2 = request.getParameter("txtadd2");
                System.out.println(strAdd2);
                strAdd3 = request.getParameter("txtadd3");
                System.out.println(strAdd3);
                strpincode =
                        Integer.parseInt(request.getParameter("txtPincode"));
                System.out.println(strpincode);
                strdistrictcode = request.getParameter("txtCmbDistrict");
                System.out.println(strdistrictcode);
                dcode = Integer.parseInt(strdistrictcode);
                strphone = Integer.parseInt(request.getParameter("txtPhone"));
                System.out.println(strphone);
                String temp = request.getParameter("txtCellNo");
                if (!temp.equals("")) {
                    strcellno = Integer.parseInt(temp);
                }
                System.out.println(strcellno);

                String temp1 = request.getParameter("txtEmail");
                if (!temp1.equals("")) {
                    stremailid = temp1;
                }
                System.out.println(stremailid);

                strOfficeId =
                        Integer.parseInt(request.getParameter("txtOffId"));
                System.out.println(strOfficeId);
                strYear = Integer.parseInt(request.getParameter("txtYear"));
                System.out.println(strYear);
                strSerialNo =
                        Integer.parseInt(request.getParameter("txtRegnNo"));
                System.out.println(strSerialNo);
                //strDatereg=request.getParameter("DateOfRegn");
                strRefNo =
                        Integer.parseInt(request.getParameter("txtRef_FileNo"));
                System.out.println(strRefNo);
                strEntireState = request.getParameter("State");
                System.out.println(strEntireState);
                classid = Integer.parseInt(request.getParameter("txtClass"));
                System.out.println(classid);
                strRegFees =
                        Integer.parseInt(request.getParameter("txtRegn_Fees"));
                System.out.println(strRegFees);

            } catch (Exception e) {
                System.out.println("exce **** " + e);
            }

            //for date functionality
            //first date field for Date of Registration field in the form

            String dateString1 = request.getParameter("DateOfRegn");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1;
            try {
                d1 = dateFormat.parse(dateString1);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat.format(d1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
            System.out.println(date1);
            //java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("dd/MM/yyyy");
            //String DateTo= sdf.format(date1);

            try {

                String sql1 =
                    "insert into PMS_MST_CONTR(Contractor_Id,Contractor_Name,Company_Name,Address1,Address2,Address3,Pin_Code,Phone_No,";
                sql1 =
sql1 + "Cell_No,Email_Id,District_Code,Last_Updated_Date,Last_Updated_By)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

                String sql2 =
                    "insert into PMS_MST_CONT_REGN(Office_Id,Regn_Year,Regn_SlNo,Contractor_Id,Regn_Class_Id,Entire_State,Date_of_Regn,Regn_Fees_Receipt_No,";
                sql2 =
sql2 + "Regn_Fees,Last_Updated_Date,Last_Updated_By)values(?,?,?,?,?,?,?,?,?,?,?)";
                System.out.println(sql2);

                try {
                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);

                    ps = connection.prepareStatement(sql1);
                    System.out.println(ps);
                    ps.setInt(1, strContractorId);
                    System.out.println(strContractorId);
                    ps.setString(2, strContractorName);
                    System.out.println(strContractorName);
                    ps.setString(3, strCompanyName);
                    System.out.println(strCompanyName);
                    ps.setString(4, strAdd1);
                    System.out.println(strAdd1);
                    ps.setString(5, strAdd2);
                    System.out.println(strAdd2);
                    ps.setString(6, strAdd3);
                    System.out.println(strAdd3);
                    ps.setInt(7, strpincode);
                    System.out.println(strpincode);
                    ps.setInt(8, strphone);
                    System.out.println(strphone);
                    ps.setInt(9, strcellno);
                    System.out.println(strcellno);
                    ps.setString(10, stremailid);
                    System.out.println(stremailid);
                    ps.setInt(11, dcode);
                    System.out.println("ps is:" + dcode);
                    ps.setTimestamp(12, ts);
                    System.out.println(ts);
                    ps.setString(13, "test");
                    ps.executeUpdate();


                    ps1 = connection.prepareStatement(sql2);
                    System.out.println(ps1);
                    ps1.setInt(1, strOfficeId);
                    System.out.println(strOfficeId);
                    ps1.setInt(2, strYear);
                    System.out.println(strYear);
                    ps1.setInt(3, strSerialNo);
                    System.out.println(strSerialNo);
                    ps1.setInt(4, strContractorId);
                    System.out.println(strContractorId);
                    ps1.setInt(5, classid);
                    System.out.println(classid);
                    ps1.setString(6, strEntireState);
                    System.out.println(strEntireState);
                    ps1.setDate(7, date1);
                    System.out.println(date1);
                    ps1.setInt(8, strRefNo);
                    System.out.println(strRefNo);
                    ps1.setInt(9, strRegFees);
                    System.out.println(strRegFees);
                    ps1.setTimestamp(10, ts);
                    System.out.println(ts);
                    ps1.setString(11, "test");

                    ps1.executeUpdate();

                    System.out.println("success");

                    pw.write("<html>");
                    pw.write("<body>");
                    pw.write("Record inserted successfully<br>");
                    pw.write(" Registration No will be generated on creation of Receipt Form</body></html>");
                }


                catch (Exception s) {
                    System.out.println("Exception:" + s);

                    pw.write("<html>");
                    pw.write("<body>");
                    pw.write("Sorry Record Not Inserted</body></html>");

                }
            } catch (Exception e3) {
                System.out.println("exce ****2 " + e3);
                pw.write("<html>");
                pw.write("<body>");
                pw.write("Sorry Record Not Inserted</body></html>");

            }
        }

        //Code to Update The Record
        else if (strrad.equals("Existing")) {

            //Declaring the Variables
            int intContractorId1 = 0;
            String strContractorName = "";
            String strCompanyName = "";
            String strAdd1 = "";
            String strAdd2 = "";
            String strAdd3 = "";
            int strpincode = 0;
            String strdistrictcode = "";
            int strphone = 0;
            int strcellno = 0;
            String stremailid = "";
            int dcode = 0;

            int strOfficeId = 0;
            int strYear = 0;
            int strSerialNo = 0;
            String strDatereg = null;
            int strRefNo = 0;
            String strEntireState = "";
            int classid = 0;
            int strRegFees = 0;

            try {

                System.out.println("super");

                intContractorId1 =
                        Integer.parseInt(request.getParameter("txtContId1"));
                System.out.println(intContractorId1);
                strContractorName = request.getParameter("txtContName");
                System.out.println(strContractorName);
                strCompanyName = request.getParameter("txtCompName");
                System.out.println(strCompanyName);
                strAdd1 = request.getParameter("txtadd1");
                System.out.println(strAdd1);
                strAdd2 = request.getParameter("txtadd2");
                System.out.println(strAdd2);
                strAdd3 = request.getParameter("txtadd3");
                System.out.println(strAdd3);
                strpincode =
                        Integer.parseInt(request.getParameter("txtPincode"));
                System.out.println(strpincode);
                strdistrictcode = request.getParameter("txtCmbDistrict");
                System.out.println(strdistrictcode);
                dcode = Integer.parseInt(strdistrictcode);
                strphone = Integer.parseInt(request.getParameter("txtPhone"));
                System.out.println(strphone);
                String temp = request.getParameter("txtCellNo");
                if (!temp.equals("")) {
                    strcellno = Integer.parseInt(temp);
                }
                System.out.println(strcellno);

                String temp1 = request.getParameter("txtEmail");
                if (!temp1.equals("")) {
                    stremailid = temp1;
                }
                System.out.println(stremailid);

                strOfficeId =
                        Integer.parseInt(request.getParameter("txtOffId1"));
                System.out.println(strOfficeId);
                strYear = Integer.parseInt(request.getParameter("txtYear"));
                System.out.println(strYear);
                strSerialNo =
                        Integer.parseInt(request.getParameter("txtRegnNo1"));
                System.out.println(strSerialNo);
                strDatereg = request.getParameter("DateOfRegn1");
                strRefNo =
                        Integer.parseInt(request.getParameter("txtRef_FileNo1"));
                System.out.println(strRefNo);
                strEntireState = request.getParameter("State");
                System.out.println(strEntireState);
                classid = Integer.parseInt(request.getParameter("txtClass"));
                System.out.println(classid);
                strRegFees =
                        Integer.parseInt(request.getParameter("txtRegn_Fees"));
                System.out.println(strRegFees);
            } catch (NumberFormatException nfe) {
                System.out.println("exce **** " + nfe);
            }


            try {
                System.out.println("inside try catch");
                String sql1 =
                    "update PMS_MST_CONTR set Contractor_Name=?,Company_Name=?,Address1=?,";
                sql1 =
sql1 + "Address2=?,Address3=?,Pin_Code=?,Phone_No=?,Cell_No=?,Email_Id=?,District_Code=?,Last_Updated_Date=?,Last_Updated_By=? where Contractor_Id=?";

                String sql2 =
                    "update PMS_MST_CONT_REGN set Regn_Class_Id=?,Entire_State=?,Regn_Fees=?,Last_Updated_Date=?,Last_Updated_By=? where Contractor_Id=?";

                //first date field for Date_creation field in the form

                /*SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
          java.util.Date d1;
        try {
          d1 = dateFormat.parse(dateString1);
          dateFormat.applyPattern("yyyy-MM-dd");
          dateString1 = dateFormat.format(d1);
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
		
          java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
          System.out.println(date1);

          //java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("dd/MM/yyyy");
          ///String DateTo= sdf.format(date1);*/

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                System.out.println(ts);

                ps = connection.prepareStatement(sql1);
                System.out.println("Executing sql" + sql1);
                ps.setString(1, strContractorName);
                System.out.println(strContractorName);
                ps.setString(2, strCompanyName);
                System.out.println(strCompanyName);
                ps.setString(3, strAdd1);
                System.out.println(strAdd1);
                ps.setString(4, strAdd2);
                System.out.println(strAdd2);
                ps.setString(5, strAdd3);
                System.out.println(strAdd3);
                ps.setInt(6, strpincode);
                System.out.println(strpincode);
                ps.setInt(7, strphone);
                System.out.println(strphone);
                ps.setInt(8, strcellno);
                System.out.println(strcellno);
                ps.setString(9, stremailid);
                System.out.println(stremailid);
                ps.setInt(10, dcode);
                System.out.println("ps is:" + dcode);
                ps.setTimestamp(11, ts);
                System.out.println(ts);
                ps.setString(12, "test");
                ps.setInt(13, intContractorId1);
                ps.executeUpdate();

                ps1 = connection.prepareStatement(sql2);
                System.out.println(ps1);
                ps1.setInt(1, classid);
                System.out.println("ps is:" + classid);
                ps1.setString(2, strEntireState);
                System.out.println("ps is:" + strEntireState);
                ps1.setInt(3, strRegFees);
                System.out.println("ps is:" + strRegFees);
                ps1.setTimestamp(4, ts);
                System.out.println(ts);
                ps1.setString(5, "test");
                ps1.setInt(6, intContractorId1);
                ps1.executeUpdate();
                System.out.println("success");

                pw.write("<html>");
                pw.write("<body>");
                pw.write("Record Updated successfully</body></html>");

            } catch (Exception e) {
                System.out.println("Exception **" + e);
                pw.write("<html>");
                pw.write("<body>");
                pw.write("Sorry Record Not Updated</body></html>");
            }
        }


    }


}

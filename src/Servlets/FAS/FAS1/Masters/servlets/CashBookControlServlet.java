package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class CashBookControlServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        ResultSet results2 = null;
        PreparedStatement ps1 = null;
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
        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";
        int ret_code = 0;
        int AssetClassCode = 0;
        String AssetClassDesc = "";
        String AssetType = "";
        String Financial_Year = "";
        double Depreciation_Rate = 0;
        HttpSession session = request.getSession(false);

        String DateForMarch = "";
        String DateToMarch = "";
        String DateForApril = "";
        String DateToApril = "";
        String NoOfOther = "";
        String NoToOther = "";
        String FileNo = "";
        String FileRefDate = "";
        String Remarks = "";

        int NoOfOther1 = 0;
        int NoToOther1 = 0;
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
        System.out.println("session id is:" + userid);


        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            AssetClassDesc = request.getParameter("AssetClassDesc");
            AssetType = request.getParameter("AssetType");

            Financial_Year = request.getParameter("FinancialYear");
            DateForMarch = request.getParameter("DateForMarch");
            DateToMarch = request.getParameter("DateToMarch");
            DateForApril = request.getParameter("DateForApril");
            DateToApril = request.getParameter("DateToApril");
            FileNo = request.getParameter("FileNo");
            FileRefDate = request.getParameter("FileRefDate");
            Remarks = request.getParameter("Remarks");
            System.out.println("Class desc:" + AssetClassDesc);
            System.out.println("AssetType is:" + AssetType);
            System.out.println("DateForMarch:" + DateForMarch);
            System.out.println("DateToMarch:" + DateToMarch);
            System.out.println("DateForApril:" + DateForApril);
            System.out.println("DateToApril:" + DateToApril);
            System.out.println("FileNo:" + FileNo);
            System.out.println("FileRefDate:" + FileRefDate);
        }

        catch (Exception e) {
            System.out.println("in getting values in all other values **** " +
                               e);
        }

        try {

            NoOfOther1 = Integer.parseInt(request.getParameter("NoOfOther"));
            NoToOther1 = Integer.parseInt(request.getParameter("NoToOther"));
            System.out.println("NoOfOther:" + NoOfOther1);
            System.out.println("NoToOther:" + NoToOther1);
            //AssetClassCode= Integer.parseInt(request.getParameter("AssetClassCode"));
            //Depreciation_Rate=Double.parseDouble(request.getParameter("DepreciationRate"));
        } catch (Exception e) {
            System.out.println("in getting values in cadre id**** " + e);
        }
        java.sql.Date DateForMarchFormation = null;
        java.sql.Date DateToMarchFormation = null;
        java.sql.Date DateForAprilFormation = null;
        java.sql.Date DateToAprilFormation = null;
        java.sql.Date FileRefDateFormation = null;

        //Date Conversion for Date
        try {
            DateForMarchFormation = null;
            if (DateForMarch != "") {
                System.out.println("before converting date");
                String dateString = DateForMarch;
                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d;
                d = dateFormat.parse(DateForMarch.trim());
                System.out.println("util date is:" + d);
                dateFormat.applyPattern("yyyy-MM-dd");
                dateString = dateFormat.format(d);
                DateForMarchFormation = java.sql.Date.valueOf(dateString);
                System.out.println("DateForMarch:" + DateForMarchFormation);
            }

            DateToMarchFormation = null;
            if (DateToMarch != "") {
                System.out.println("before converting date");
                String dateString1 = DateToMarch;
                SimpleDateFormat dateFormat1 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d1;
                d1 = dateFormat1.parse(DateToMarch.trim());
                dateFormat1.applyPattern("yyyy-MM-dd");
                dateString1 = dateFormat1.format(d1);
                DateToMarchFormation = java.sql.Date.valueOf(dateString1);
                System.out.println("DateToMarch:" + DateToMarchFormation);
            }

            DateForAprilFormation = null;
            System.out.println("before converting date");
            if (DateForApril != "") {
                String dateString2 = DateForApril;
                SimpleDateFormat dateFormat2 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d2;
                d2 = dateFormat2.parse(DateForApril.trim());
                dateFormat2.applyPattern("yyyy-MM-dd");
                dateString2 = dateFormat2.format(d2);
                DateForAprilFormation = java.sql.Date.valueOf(dateString2);
                System.out.println("DateForApril:" + DateForAprilFormation);
            }
            DateToAprilFormation = null;
            System.out.println("before converting date");
            if (DateToApril != "") {
                String dateString3 = DateToApril;
                SimpleDateFormat dateFormat3 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d3;
                d3 = dateFormat3.parse(DateToApril.trim());
                dateFormat3.applyPattern("yyyy-MM-dd");
                dateString3 = dateFormat3.format(d3);
                DateToAprilFormation = java.sql.Date.valueOf(dateString3);
                System.out.println("DateToApril:" + DateToAprilFormation);
            }
            FileRefDateFormation = null;
            System.out.println("before converting date");
            if (FileRefDate != "") {
                String dateString4 = FileRefDate;
                SimpleDateFormat dateFormat4 =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d4;
                d4 = dateFormat4.parse(FileRefDate.trim());
                dateFormat4.applyPattern("yyyy-MM-dd");
                dateString4 = dateFormat4.format(d4);
                FileRefDateFormation = java.sql.Date.valueOf(dateString4);
                System.out.println("File Ref:" + FileRefDateFormation);
            }

        } catch (Exception e) {
            System.out.println("Exception in Date");
        }

        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                System.out.println("asst inside delete" + AssetClassCode);
                System.out.println("finyear delete" + Financial_Year);
                PreparedStatement pstmt =
                    connection.prepareStatement("delete from cash_book_control where financial_year=?");
                System.out.println(pstmt);

                pstmt.setString(1, Financial_Year);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            try {
                PreparedStatement pstmt =
                    connection.prepareStatement("update cash_book_control set CB_FROM_DATE_FOR_MARCH=?,CB_TO_DATE_FOR_MARCH=?,CB_FROM_DATE_FOR_APRIL=?,CB_TO_DATE_FOR_APRIL=?,CB_FROM_DATE_FOR_OTH=?,CB_TO_DATE_FOR_OTH=?,FILE_REF_NO=?,FILE_REF_DATE=?,REMARKS=? where financial_year=?");
                System.out.println(pstmt);

                pstmt.setDate(1, DateForMarchFormation);
                pstmt.setDate(2, DateToMarchFormation);
                pstmt.setDate(3, DateForAprilFormation);
                pstmt.setDate(4, DateToAprilFormation);
                pstmt.setInt(5, NoOfOther1);
                pstmt.setInt(6, NoToOther1);
                pstmt.setString(7, FileNo);
                pstmt.setDate(8, FileRefDateFormation);
                pstmt.setString(9, Remarks);
                pstmt.setString(10, Financial_Year);
                int ii = pstmt.executeUpdate();

                if (ii >= 1) {
                    xml = xml + "<flag>success</flag>";
                } else
                    xml = xml + "<flag>failure</flag>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
                PreparedStatement ps =
                    connection.prepareStatement("select * from cash_book_control where financial_year=?");

                ps.setString(1, Financial_Year);
                ResultSet res = ps.executeQuery();
                if (!res.next()) {
                    PreparedStatement pstmt =
                        connection.prepareStatement("insert into cash_book_control(FINANCIAL_YEAR,CB_FROM_DATE_FOR_MARCH,CB_TO_DATE_FOR_MARCH,CB_FROM_DATE_FOR_APRIL,CB_TO_DATE_FOR_APRIL,CB_FROM_DATE_FOR_OTH,CB_TO_DATE_FOR_OTH,FILE_REF_NO,FILE_REF_DATE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?)");


                    pstmt.setString(1, Financial_Year);
                    pstmt.setDate(2, DateForMarchFormation);
                    pstmt.setDate(3, DateToMarchFormation);
                    pstmt.setDate(4, DateForAprilFormation);
                    pstmt.setDate(5, DateToAprilFormation);
                    pstmt.setInt(6, NoOfOther1);
                    pstmt.setInt(7, NoToOther1);
                    pstmt.setString(8, FileNo);
                    pstmt.setDate(9, FileRefDateFormation);
                    pstmt.setString(10, Remarks);
                    pstmt.setString(11, userid);
                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);
                    pstmt.setTimestamp(12, ts);
                    int ii = pstmt.executeUpdate();

                    if (ii >= 1) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";


                    xml =
 xml + "<FinancialYear>" + Financial_Year + "</FinancialYear>";
                    pstmt.close();
                } else {
                    xml = xml + "<flag>AlreadyExist</flag>";
                }
            } catch (SQLException e) {
                System.out.println("error is" + e);
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
        }

        else if (strCommand.equals("Get")) {
            xml = "<response><command>Get</command>";
            try {
                System.out.println("bef res" + Financial_Year);

                //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                String sql =
                    "select FINANCIAL_YEAR,CB_FROM_DATE_FOR_MARCH,CB_TO_DATE_FOR_MARCH,CB_FROM_DATE_FOR_APRIL,CB_TO_DATE_FOR_APRIL,CB_FROM_DATE_FOR_OTH,CB_TO_DATE_FOR_OTH,FILE_REF_NO,FILE_REF_DATE,REMARKS from cash_book_control where FINANCIAL_YEAR='" +
                    Financial_Year + "'";
                System.out.println("sql is:" + sql);
                results2 = statement.executeQuery(sql);


                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        System.out.println("inner while");
                        //Date Conversion From Datebase Values
                        //*****************************************************************************************************//
                        java.sql.Date DateOfFormation =
                            results2.getDate("CB_FROM_DATE_FOR_MARCH");
                        String DateToBeDisplayed = "";
                        if (DateOfFormation == null) {
                            DateToBeDisplayed = "Not Specified";
                        } else {
                            try {
                                java.text.SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                                DateToBeDisplayed =
                                        sdf.format(DateOfFormation);
                            } catch (Exception e) {
                                System.out.println("error while formatting date : " +
                                                   e);
                                DateToBeDisplayed = "Not Specified";
                            }
                        }

                        java.sql.Date DateOfFormation1 =
                            results2.getDate("CB_TO_DATE_FOR_MARCH");
                        String DateToBeDisplayed1 = "";
                        if (DateOfFormation1 == null) {
                            DateToBeDisplayed1 = "Not Specified";
                        } else {
                            try {
                                java.text.SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                                DateToBeDisplayed1 =
                                        sdf.format(DateOfFormation1);
                            } catch (Exception e) {
                                System.out.println("error while formatting date : " +
                                                   e);
                                DateToBeDisplayed1 = "Not Specified";
                            }
                        }

                        java.sql.Date DateOfFormation2 =
                            results2.getDate("CB_FROM_DATE_FOR_APRIL");
                        String DateToBeDisplayed2 = "";
                        if (DateOfFormation2 == null) {
                            DateToBeDisplayed2 = "Not Specified";
                        } else {
                            try {
                                java.text.SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                                DateToBeDisplayed2 =
                                        sdf.format(DateOfFormation2);
                            } catch (Exception e) {
                                System.out.println("error while formatting date : " +
                                                   e);
                                DateToBeDisplayed2 = "Not Specified";
                            }
                        }

                        java.sql.Date DateOfFormation3 =
                            results2.getDate("CB_TO_DATE_FOR_APRIL");
                        String DateToBeDisplayed3 = "";
                        if (DateOfFormation3 == null) {
                            DateToBeDisplayed3 = "Not Specified";
                        } else {
                            try {
                                java.text.SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                                DateToBeDisplayed3 =
                                        sdf.format(DateOfFormation3);
                            } catch (Exception e) {
                                System.out.println("error while formatting date : " +
                                                   e);
                                DateToBeDisplayed3 = "Not Specified";
                            }
                        }

                        java.sql.Date DateOfFormation4 =
                            results2.getDate("FILE_REF_DATE");
                        String DateToBeDisplayed4 = "";
                        if (DateOfFormation4 == null) {
                            DateToBeDisplayed4 = "Not Specified";
                        } else {
                            try {
                                java.text.SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd/MM/yyyy");
                                DateToBeDisplayed4 =
                                        sdf.format(DateOfFormation4);
                            } catch (Exception e) {
                                System.out.println("error while formatting date : " +
                                                   e);
                                DateToBeDisplayed4 = "Not Specified";
                            }
                        }

                        //******************************************************************************************************************************************************************//


                        System.out.println("Date 1 is:" + DateToBeDisplayed);
                        System.out.println("Date 2 is:" + DateToBeDisplayed1);
                        System.out.println("Date 3 is:" + DateToBeDisplayed2);
                        System.out.println("Date 4 is:" + DateToBeDisplayed3);
                        System.out.println("Date 5 is:" + DateToBeDisplayed4);
                        //int AssetClassCode1=results2.getInt("ASSET_CLASS_CODE");
                        //String AssetClassDesc1=results2.getString("ASSET_CLASS_DESC");
                        //String AssetType1=results2.getString("ASSET_TYPE_CODE");
                        String FinancialYear =
                            results2.getString("Financial_Year");
                        String FileNo1 = results2.getString("FILE_REF_NO");
                        String Remarks1 = results2.getString("Remarks");
                        int FNoOther = results2.getInt("CB_FROM_DATE_FOR_OTH");
                        int TNoOther = results2.getInt("CB_TO_DATE_FOR_OTH");
                        System.out.println("Remarks is:" + Remarks1);
                        System.out.println("Fileno is:" + FileNo1);
                        System.out.println("FNoOther:" + FNoOther);
                        System.out.println("TNoOther:" + TNoOther);


                        //<PayName>" + PayName + "</PayName>;
                        xml =
 xml + "<DateForMarch>" + DateToBeDisplayed + "</DateForMarch><DateToMarch>" +
   DateToBeDisplayed1 + "</DateToMarch><DateForApril>" + DateToBeDisplayed2 +
   "</DateForApril><DateToApril>" + DateToBeDisplayed3 +
   "</DateToApril><FinancialYear>" + FinancialYear +
   "</FinancialYear><NoOfOther>" + FNoOther + "</NoOfOther><NoToOther>" +
   TNoOther + "</NoToOther><FileNo>" + FileNo1 + "</FileNo><FileRefDate>" +
   DateToBeDisplayed4 + "</FileRefDate><Remarks>" + Remarks1 + "</Remarks>";
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF GET: " +
                                       aee);
                }
                results2.close();
                //response.setHeader("cache-control","no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equals("Asset")) {
            xml = "<response><command>Asset</command>";
            try {
                System.out.println("bef res");

                //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                results2 =
                        statement.executeQuery("select asset_class_code,ASSET_class_DESC from com_mst_assets_class order by ASSET_class_CODE");
                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        int AssetClassCode1 =
                            results2.getInt("ASSET_class_CODE");
                        String AssetClassDesc1 =
                            results2.getString("ASSET_class_DESC");


                        //<PayName>" + PayName + "</PayName>;
                        xml =
 xml + "<options><AssetClassCode>" + AssetClassCode1 +
   "</AssetClassCode><AssetClassDesc>" + AssetClassDesc1 +
   "</AssetClassDesc></options>";
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF Asset: " +
                                       aee);
                }
                results2.close();
                //response.setHeader("cache-control","no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }
}

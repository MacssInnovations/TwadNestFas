package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.Security.classes.UserProfile;

public class Pms_Dcb_Beneficiary_metre extends HttpServlet {
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
        System.out.println("hai");
        String command_var = "";
        String xmlvariable = "";
        int Beneficiary_Sno, Metre_init_reading, Allotted_Qty, Min_bill_Qty, metre_sno;
        double Tariffrate;
        int flagvar = 0;
        int msno = 0;
        int flagvariable = 0;
        int Schemesid = 0;
        int flagname = 0;
        int countsno = 0;
        int vilage_panchayat_sno = 0;
        metre_sno = 0;
        int tempvar = 0;
        int delvalue = 0;
        int Beneficiary_Name_traffic = 0;
        int Beneficiary_Name = 0;
        Beneficiary_Sno = 0;
        int BEN_CONS_CATEGORY = 0;
        int subdivisionid = 0;
        Metre_init_reading = 0;
        Allotted_Qty = 1;
        Allotted_Qty = 1;
        Min_bill_Qty = 1;
        double Multiply_factor;
        Multiply_factor = 1;
        Tariffrate = 0;
        String scheme_name = "";

        String Metre_Location = "";
        String meterfixed = "";
        String meterworking = "";
        String Init_Reading_Record_date = "";
        int Beneficiary_type = 0;
        String Service_Connection_date = "";
        int Habitation_Name = 0;
        int Consumption_Category = 0;
        int Multi_WS_Category = 0;
        int Metre_Type = 0;
        double Excess_Tariff_Rate = 1;
        String Service_Connection = "";
        String officename = "";
        int Schemes = 0;
        int SubDivision = 0;
        int district_Name = 0;
        Connection connection = null;
        PreparedStatement ps, ps1, ps2, ps_new, ps_new4, ps_new8, pssno, ps_oid, ps_off, ps_ben_name, ps_vill_pan, psdel, ps_check, ps_insert;
        ResultSet res, res1, res_new, res_new4, res_new8, ressno, result_new, res_off, res_ben_name, rs_vill_pan, resdel, rs_check, res_insert;
        res1 = null;
        res_off = null;
        res = null;
        ps = null;
        ps1 = null;
        resdel = null;
        rs_check = null;
        ps2 = null;
        res_insert = null;
        int countinsert = 0;
        int empid;
        int oid = 0;
        int hablist = 0;
        int tariff_id = 0;
        int BENEFICIARY_TYPE_ID = 0;
        int SCH_TYPE_ID = 0;
        int OTHERS_PRIVATE_SNO = 0;
        int VILLAGE_PANCHAYAT_SNO = 0;
        int URBANLB_SNO = 0;
        int block_Name = 0;
        String Applicableval = "";
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
        String userid = (String)session.getAttribute("UserId");
        System.out.println("Session id is:" + userid);

        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("emp id::" + empProfile.getEmployeeId());

        empid = empProfile.getEmployeeId();
        //int empid=1758;

        String oname = "";
        System.out.println("Empid" + empid);
        try {

            System.out.println("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=" +
                               empid);
            ps_oid =
                    connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
            ps_oid.setInt(1, empid);
            result_new = ps_oid.executeQuery();
            System.out.println("Testing value");
            if (result_new.next()) {
                System.out.println("inside condition");
                oid = result_new.getInt("OFFICE_ID");
            }

        } catch (Exception e) {
            System.out.println(e);
        }


        try {
            command_var = request.getParameter("command");
            System.out.println(command_var);
            //Metre_Code=request.getParameter("Metre_Code");
            try {
                hablist = Integer.parseInt(request.getParameter("hablist"));
            } catch (Exception e) {
                System.out.println("hablist" + e);
            }
            try {
                Beneficiary_Sno =
                        Integer.parseInt(request.getParameter("Beneficiary_type"));
            } catch (Exception e) {
                System.out.println("Beneficiary_Sno" + e);
            }
            try {
                Habitation_Name =
                        Integer.parseInt(request.getParameter("Habitation_Name"));
            } catch (Exception e) {
                System.out.println("Habitation_Name" + e);
            }

            try {
                Beneficiary_Name =
                        Integer.parseInt(request.getParameter("Beneficiary_Name"));
            } catch (Exception e) {
                System.out.println("Beneficiary_Name" + e);
            }
            try {
                Consumption_Category =
                        Integer.parseInt(request.getParameter("Consumption_Category"));
            } catch (Exception e) {
                System.out.println("Consumption_Category" + e);
            }
            try {
                Multi_WS_Category =
                        Integer.parseInt(request.getParameter("Multi_WS_Category"));
            } catch (Exception e) {
                System.out.println("Multi_WS_Category" + e);
            }
            try {
                Tariffrate =
                        Double.parseDouble(request.getParameter("Tariff_Id"));
            } catch (Exception e) {
                System.out.println("Tariffrate" + e);
            }
            try {
                tariff_id =
                        Integer.parseInt(request.getParameter("Tariff_Id_val"));
            } catch (Exception e) {
                System.out.println("Tariffrate" + e);
            }

            try {
                SubDivision =
                        Integer.parseInt(request.getParameter("SubDivision"));
            } catch (Exception e) {
                System.out.println("SubDivision" + e);
            }
            try {
                Schemes = Integer.parseInt(request.getParameter("Schemes"));
            } catch (Exception e) {
                System.out.println("Schemes" + e);
            }
            try {
                Metre_Location = request.getParameter("Metre_Location");
            } catch (Exception e) {
                System.out.println("Metre_Location" + e);
            }
            try {
                meterfixed = request.getParameter("meterfixed");
            } catch (Exception e) {
                System.out.println("meterfixed" + e);
            }
            try {
                meterworking = request.getParameter("meterworking");
            } catch (Exception e) {
                System.out.println("meterworking" + e);
            }
            try {
                Metre_Type =
                        Integer.parseInt(request.getParameter("Metre_Type"));
            } catch (Exception e) {
                System.out.println("Metre_Type" + e);
            }
            try {
                Metre_init_reading =
                        Integer.parseInt(request.getParameter("Metre_init_reading"));
            } catch (Exception e) {
                System.out.println("Metre_init_reading" + e);
            }
            try {
                Multiply_factor =
                        Double.parseDouble(request.getParameter("Multiply_factor"));
            } catch (Exception e) {
                System.out.println("Multiply_factor" + e);
            }
            try {
                Init_Reading_Record_date =
                        request.getParameter("Init_Reading_Record_date");
            } catch (Exception e) {
                System.out.println("Init_Reading_Record_date" + e);
            }
            try {
                Allotted_Qty =
                        Integer.parseInt(request.getParameter("Allotted_Qty"));
            } catch (Exception e) {
                System.out.println("Allotted_Qty" + e);
            }
            try {
                Min_bill_Qty =
                        Integer.parseInt(request.getParameter("Min_bill_Qty"));
            } catch (Exception e) {
                System.out.println("Min_bill_Qty" + e);
            }
            try {
                Excess_Tariff_Rate =
                        Double.parseDouble(request.getParameter("Excess_Tariff_Rate"));
            } catch (Exception e) {
                System.out.println("Excess_Tariff_Rate" + e);
            }
            try {
                Service_Connection =
                        request.getParameter("Service_Connection");
            } catch (Exception e) {
                System.out.println("serviceno" + e);
            }
            try {
                Service_Connection_date =
                        request.getParameter("Service_Connection_date");
            } catch (Exception e) {
                System.out.println("Connectiondate" + e);
            }
            try {
                BENEFICIARY_TYPE_ID =
                        Integer.parseInt(request.getParameter("BENEFICIARY_TYPE_ID"));
            } catch (Exception e) {
                System.out.println("BENEFICIARY_TYPE_ID" + e);
            }
            try {
                SCH_TYPE_ID =
                        Integer.parseInt(request.getParameter("SCH_TYPE_ID"));
            } catch (Exception e) {
                System.out.println("SCH_TYPE_ID" + e);
            }
            try {
                OTHERS_PRIVATE_SNO =
                        Integer.parseInt(request.getParameter("OTHERS_PRIVATE_SNO"));
            } catch (Exception e) {
                System.out.println("OTHERS_PRIVATE_SNO" + e);
            }
            try {
                VILLAGE_PANCHAYAT_SNO =
                        Integer.parseInt(request.getParameter("VILLAGE_PANCHAYAT_SNO"));
            } catch (Exception e) {
                System.out.println("VILLAGE_PANCHAYAT_SNO" + e);
            }
            try {
                URBANLB_SNO =
                        Integer.parseInt(request.getParameter("URBANLB_SNO"));
            } catch (Exception e) {
                System.out.println("URBANLB_SNO" + e);
            }
            try {
                district_Name =
                        Integer.parseInt(request.getParameter("district_Name"));
            } catch (Exception e) {
                System.out.println("district_Name" + e);
            }
            try {
                block_Name =
                        Integer.parseInt(request.getParameter("block_Name"));
            } catch (Exception e) {
                System.out.println("block_Name" + e);
            }
            try {
                block_Name =
                        Integer.parseInt(request.getParameter("block_Name"));
            } catch (Exception e) {
                System.out.println("block_Name" + e);
            }
            try {
                Applicableval = request.getParameter("Applicableval");
            } catch (Exception e) {
                System.out.println("Applicableval" + e);
            }

            //System.out.println(Beneficiary_Sno);
            System.out.println(Habitation_Name);
            System.out.println(Beneficiary_Name);
            System.out.println(Consumption_Category);
            System.out.println(Tariffrate);
            System.out.println(Multi_WS_Category);

            System.out.println(SubDivision);
            System.out.println(Schemes);
            System.out.println(Metre_Location);
            System.out.println(meterfixed);
            System.out.println(meterworking);
            System.out.println(Metre_Type);
            System.out.println(Metre_init_reading);
            System.out.println(Multiply_factor);
            System.out.println(Init_Reading_Record_date);
            System.out.println(Allotted_Qty);
            System.out.println(Min_bill_Qty);
            System.out.println(Excess_Tariff_Rate);
            System.out.println(Service_Connection);
            System.out.println(Service_Connection_date);
            System.out.println("Applicableval" + Applicableval);

        } catch (Exception e) {
            System.out.println("Error in reterival:" + e);
        }

        if (command_var.equalsIgnoreCase("add")) {

            xmlvariable = "<response>";
            xmlvariable += "<command>add</command>";
            try {
                System.out.println("Habitation_Name" + Habitation_Name);
                System.out.println("Beneficiary_Name" + Beneficiary_Name);
                System.out.println("Consumption_Category" +
                                   Consumption_Category);
                System.out.println("Tariffrate is " + Tariffrate);

                System.out.println("SubDivision" + SubDivision);
                System.out.println("Schemes" + Schemes);
                System.out.println("Metre_Location" + Metre_Location);

                System.out.println("tariff_id" + tariff_id);

                System.out.println("meterworking " + meterworking);
                System.out.println("meterfixed" + meterfixed);
                System.out.println("Metre_Type" + Metre_Type);
                System.out.println("Metre_init_reading" + Metre_init_reading);
                System.out.println("Multiply_factor" + Multiply_factor);
                System.out.println("Init_Reading_Record_date" +
                                   Init_Reading_Record_date);
                System.out.println("Allotted_Qty" + Allotted_Qty);
                System.out.println("Min_bill_Qty" + Min_bill_Qty);
                System.out.println("Excess_Tariff_Rate" + Excess_Tariff_Rate);
                System.out.println("Service_Connection" + Service_Connection);
                System.out.println("Service_Connection_date" +
                                   Service_Connection_date);
                //20
                if (BENEFICIARY_TYPE_ID == 6) {
                    if ((Consumption_Category == 0) ||
                        (Consumption_Category == 1)) {
                        ps_insert =
                                connection.prepareStatement("select count(*)as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE  \n" +
                                                            "where   \n" +
                                                            "BENEFICIARY_SNO=?  \n" +
                                                            "and  \n" +
                                                            "SUB_DIV_ID=?  \n" +
                                                            "and  \n" +
                                                            "scheme_sno=?  \n" +
                                                            "and  \n" +
                                                            "HABITATION_SNO=?");
                        ps_insert.setInt(1, Beneficiary_Name);
                        ps_insert.setInt(2, SubDivision);
                        ps_insert.setInt(3, Schemes);
                        ps_insert.setInt(4, Habitation_Name);
                        res_insert = ps_insert.executeQuery();
                        if (res_insert.next()) {
                            countinsert = res_insert.getInt("countinsert");
                        }
                    }
                }
                if ((BENEFICIARY_TYPE_ID == 1) || (BENEFICIARY_TYPE_ID == 2) ||
                    (BENEFICIARY_TYPE_ID == 3) || (BENEFICIARY_TYPE_ID == 4) ||
                    (BENEFICIARY_TYPE_ID == 5)) {
                    if (Consumption_Category == 0) {
                        ps_insert =
                                connection.prepareStatement("select count(*) as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE\n" +
                                                            "where \n" +
                                                            "BENEFICIARY_SNO=?\n" +
                                                            "and\n" +
                                                            "SUB_DIV_ID=?\n" +
                                                            "and\n" +
                                                            "SCHEME_SNO=?");
                        ps_insert.setInt(1, Beneficiary_Name);
                        ps_insert.setInt(2, SubDivision);
                        ps_insert.setInt(3, Schemes);
                        res_insert = ps_insert.executeQuery();
                        if (res_insert.next()) {
                            countinsert = res_insert.getInt("countinsert");
                        }

                    }
                }


                if ((Consumption_Category == 1) || (BENEFICIARY_TYPE_ID > 6)) {
                    ps_insert =
                            connection.prepareStatement("select count(*)as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE\n" +
                                                        "where \n" +
                                                        "BENEFICIARY_SNO=?\n" +
                                                        "and\n" +
                                                        "SUB_DIV_ID=?\n" +
                                                        "and\n" +
                                                        "scheme_sno=?\n" +
                                                        "and\n" +
                                                        "METRE_LOCATION=?");
                    ps_insert.setInt(1, Beneficiary_Name);
                    ps_insert.setInt(2, SubDivision);
                    ps_insert.setInt(3, Schemes);
                    ps_insert.setString(4, Metre_Location);
                    res_insert = ps_insert.executeQuery();
                    if (res_insert.next()) {

                        countinsert = res_insert.getInt("countinsert");
                    }
                }
                System.out.println("countinsert" + countinsert);
                if (countinsert == 0) {
                    /*ps_max = connection.prepareStatement("select max(METRE_SNO) from PMS_DCB_MST_BENEFICIARY_METRE");
                        	res_max = ps_max.executeQuery();
                            if(res_max.next())
                            {
                             if METRE_SNO>0 then
                             METRE_SNO=METRE_SNO+1;
                             else
                            	 METRE_SNO=1;*/
                    ps =
  connection.prepareStatement("insert into PMS_DCB_MST_BENEFICIARY_METRE(BENEFICIARY_SNO,\n" +
                              "METRE_LOCATION,\n" + "METRE_FIXED,\n" +
                              "ALLOTED_QTY,\n" + "MIN_BILL_QTY,\n" +
                              "METRE_INIT_READING,\n" +
                              "INIT_READING_RECORD_DT,\n" +
                              "MULTIPLY_FACTOR,\n" + "SCHEME_SNO,\n" +
                              "HABITATION_SNO,\n" + "METRE_WORKING,\n" +
                              "UPDATED_BY_USER_ID,\n" + "UPDATED_DATE,\n" +
                              "TARIFF_RATE,\n" + "METRE_TYPE,\n" +
                              "SERVICE_CON_NO,\n" + "SEVICE_CONN_DATE,\n" +
                              "EXCESS_TARIFF_RATE,\n" + "TARIFF_ID,\n" +
                              "OFFICE_ID,\n" + "BENEFICIARY_TYPE_ID,\n" +
                              "SCH_TYPE_ID,\n" + "OTHERS_PRIVATE_SNO,\n" +
                              "VILLAGE_PANCHAYAT_SNO,\n" + "URBANLB_SNO,\n" +
                              "BULKWS_CATEGORY,\n" + "SUB_DIV_ID,\n" +
                              "ALLOTED_FLG) values(?,?,?,?,?,?,to_date(?,'DD/MM/YYYY'),?,?,?,?,?,SYSTIMESTAMP,?,?,?,to_date(?,'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,?)");


                    ps.setInt(1, Beneficiary_Name);
                    ps.setString(2, Metre_Location);
                    ps.setString(3, meterfixed);
                    ps.setInt(4, Allotted_Qty);
                    ps.setInt(5, Min_bill_Qty);
                    ps.setInt(6, Metre_init_reading);
                    if (Init_Reading_Record_date.equals("")) {
                        ps.setNull(7, Types.DATE);
                        System.out.println("The value is null");
                    } else {
                        ps.setString(7, Init_Reading_Record_date);
                    }
                    //  ps.setString(7, Init_Reading_Record_date);
                    ps.setDouble(8, Multiply_factor);
                    ps.setInt(9, Schemes);
                    ps.setInt(10, Habitation_Name);
                    ps.setString(11, meterworking);
                    ps.setString(12, userid);
                    ps.setDouble(13, Tariffrate);
                    ps.setInt(14, Metre_Type);
                    ps.setString(15, Service_Connection);
                    if (Service_Connection_date.equals("")) {
                        ps.setNull(16, Types.DATE);
                        System.out.println("The value is null");
                    } else {
                        ps.setString(16, Service_Connection_date);
                    }

                    ps.setDouble(17, Excess_Tariff_Rate);
                    ps.setInt(18, tariff_id);
                    ps.setInt(19, oid);
                    ps.setInt(20, BENEFICIARY_TYPE_ID);
                    ps.setInt(21, SCH_TYPE_ID);
                    ps.setInt(22, OTHERS_PRIVATE_SNO);
                    ps.setInt(23, VILLAGE_PANCHAYAT_SNO);
                    ps.setInt(24, URBANLB_SNO);
                    ps.setInt(25, Consumption_Category);
                    ps.setInt(26, SubDivision);
                    ps.setString(27, Applicableval);


                    ps.executeUpdate();
                    System.out.println("Inserted");
                    //  System.out.println("meterfixed"+meterfixed);
                    ps1 =
 connection.prepareStatement("select max(METRE_SNO) as METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE");
                    System.out.println(ps1);
                    res = ps1.executeQuery();
                    if (res.next()) {
                        System.out.println(res.getInt("METRE_SNO"));
                        metre_sno = res.getInt("METRE_SNO");
                        xmlvariable +=
                                "<metre_sno>" + res.getInt("METRE_SNO") +
                                "</metre_sno>";
                    }

                    ps_off =
                            connection.prepareStatement("select office_name from  COM_MST_ALL_OFFICES_VIEW  where COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=?");
                    System.out.println(SubDivision);
                    ps_off.setInt(1, SubDivision);
                    res_off = ps_off.executeQuery();
                    if (res_off.next()) {
                        System.out.println(res_off.getString("office_name"));
                        xmlvariable +=
                                "<office_name>" + res_off.getString("office_name") +
                                "</office_name>";

                    }
                    ps_off =
                            connection.prepareStatement("select SCH_NAME from PMS_SCH_MASTER where SCH_SNO=?");
                    ps_off.setInt(1, Schemes);
                    res_off = ps_off.executeQuery();
                    if (res_off.next()) {
                        System.out.println(res_off.getString("SCH_NAME"));
                        xmlvariable +=
                                "<SCH_NAME>" + res_off.getString("SCH_NAME") +
                                "</SCH_NAME>";

                    }
                    ps_off =
                            connection.prepareStatement("select BEN_TYPE_DESC  from PMS_DCB_BEN_TYPE where BEN_TYPE_ID=?");
                    ps_off.setInt(1, BENEFICIARY_TYPE_ID);
                    res_off = ps_off.executeQuery();
                    if (res_off.next()) {
                        System.out.println(res_off.getString("BEN_TYPE_DESC"));
                        xmlvariable +=
                                "<BEN_TYPE_DESC>" + res_off.getString("BEN_TYPE_DESC") +
                                "</BEN_TYPE_DESC>";

                    }
                    ps_off =
                            connection.prepareStatement("select BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_SNO=?");
                    ps_off.setInt(1, Beneficiary_Name);
                    res_off = ps_off.executeQuery();
                    if (res_off.next()) {
                        System.out.println(res_off.getString("BENEFICIARY_NAME"));
                        xmlvariable +=
                                "<BENEFICIARY_NAME>" + res_off.getString("BENEFICIARY_NAME") +
                                "</BENEFICIARY_NAME>";

                    }

                    xmlvariable +=
                            "<Metre_Location>" + Metre_Location + "</Metre_Location>";
                    xmlvariable += "<Tariff_Id>" + Tariffrate + "</Tariff_Id>";
                    xmlvariable +=
                            "<meterfixed>" + meterfixed + "</meterfixed>";
                    xmlvariable +=
                            "<meterworking>" + meterworking + "</meterworking>";
                    xmlvariable +=
                            "<Consumption_Category>" + Consumption_Category +
                            "</Consumption_Category>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + BENEFICIARY_TYPE_ID +
                            "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<Applicableval>" + Applicableval + "</Applicableval>";
                    xmlvariable += "<countinsert>" + 0 + "</countinsert>";


                    /*    xmlvariable += "<METRE_WORKING>" + METRE_WORKING + "</METRE_WORKING>";
                        xmlvariable += "<Metre_Location>" + Metre_Location + "</Metre_Location>";
                        xmlvariable += "<Tariff_Id>" + Tariff_Id + "</Tariff_Id>";
                        xmlvariable += "<meterfixed>" + meterfixed + "</meterfixed>";
                        xmlvariable += "<Allotted_Qty>" + Allotted_Qty + "</Allotted_Qty>";
                        xmlvariable += "<Min_bill_Qty>" + Min_bill_Qty + "</Min_bill_Qty>";
                        xmlvariable += "<Metre_init_reading>" + Metre_init_reading + "</Metre_init_reading>";
                        if(Init_Reading_Record_date.equals(""))
                            xmlvariable += "<Init_Reading_Record_date>-</Init_Reading_Record_date>";
                        else
                            xmlvariable += "<Init_Reading_Record_date>" + Init_Reading_Record_date + "</Init_Reading_Record_date>";
                        xmlvariable += "<Multiply_factor>" + Multiply_factor + "</Multiply_factor>";
                        xmlvariable += "<scheme_name>" + scheme_name + "</scheme_name>";
                        xmlvariable += "<Habitation_name>" + Habitation_name + "</Habitation_name>";
                        xmlvariable += "<meterworking>" + meterworking + "</meterworking>";
                        //xmlvariable += "<Metre_Code>" + Metre_Code + "</Metre_Code>";*/


                    xmlvariable += "<flag>success</flag>";
                } else {
                    xmlvariable += "<countinsert>" + 1 + "</countinsert>";
                    xmlvariable += "<flag>success</flag>";
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not inserted!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("get")) {
            System.out.println("getting value");
            System.out.println("oid" + oid);
            System.out.println("BENEFICIARY_TYPE_ID" + Beneficiary_Sno);
            System.out.println("Beneficiary_Name" + Beneficiary_Name);
            xmlvariable = "<response>";
            xmlvariable += "<command>get</command>";

            try {
                ps_check =
                        connection.prepareStatement("select count(*) As countsno from PMS_DCB_MST_BENEFICIARY_METRE where BENEFICIARY_SNO =?");
                ps_check.setInt(1, Beneficiary_Name);
                rs_check = ps_check.executeQuery();
                if (rs_check.next()) {
                    countsno = rs_check.getInt("countsno");
                    System.out.println("countsno" + countsno);
                }
            } catch (Exception e) {
                System.out.println("Error reterival");

            }
            if (countsno > 0) {
                try {


                    //  ps = connection.prepareStatement("select METRE_SNO,BENEFICIARY_SNO,METRE_LOCATION,TARIFF_ID,METRE_FIXED,ALLOTED_QTY,MIN_BILL_QTY,METRE_INIT_READING,INIT_READING_RECORD_DT,MULTIPLY_FACTOR,SCHEME_SNO,HABITATION_SNO,METRE_WORKING,METRE_CODE,UPDATED_BY_USER_ID,UPDATED_DATE from PMS_DCB_MST_BENEFICIARY_METRE ORDER BY METRE_SNO");
                    /*  ps = connection.prepareStatement("select METRE_SNO,PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,PMS_DCB_MST_BENEFICIARY.BENEFICIARY_NAME AS BENEFICIARY_NAME,METRE_LOCATION,METRE_FIXED,ALLOTED_QTY,MIN_BILL_QTY,METRE_INIT_READING,INIT_READING_RECORD_DT,MULTIPLY_FACTOR,SCHEME_SNO,PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,HABITATION_SNO,METRE_WORKING,BULKWS_CATEGORY,TARIFF_RATE,METRE_TYPE,SERVICE_CON_NO,SEVICE_CONN_DATE,EXCESS_TARIFF_RATE,SUB_DIV_ID,COM_MST_ALL_OFFICES_VIEW.OFFICE_NAME As officename from PMS_DCB_MST_BENEFICIARY_METRE \n" +
               "left outer join \n" +
               "PMS_DCB_MST_BENEFICIARY\n" +
               "on\n" +
               "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO\n" +
               "join\n" +
               "PMS_SCH_MASTER\n" +
               "on\n" +
               "PMS_SCH_MASTER.SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO\n" +
               "join\n" +
               "COM_MST_ALL_OFFICES_VIEW\n" +
               "on\n" +
               "PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID= COM_MST_ALL_OFFICES_VIEW.OFFICE_ID\n" +
               "ORDER BY METRE_SNO");*/
                    /* ps = connection.prepareStatement("select METRE_SNO,\n" +
              "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,\n" +
              "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_NAME AS BENEFICIARY_NAME,\n" +
              "METRE_LOCATION,\n" +
              "METRE_FIXED,\n" +
              "ALLOTED_QTY,\n" +
              "MIN_BILL_QTY,\n" +
              "METRE_INIT_READING,\n" +
              "INIT_READING_RECORD_DT,\n" +
              "MULTIPLY_FACTOR,\n" +
              "SCHEME_SNO,\n" +
              "PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,\n" +
              "HABITATION_SNO,\n" +
              "METRE_WORKING,\n" +
              "BULKWS_CATEGORY,\n" +
              "TARIFF_RATE,\n" +
              "METRE_TYPE,\n" +
              "SERVICE_CON_NO,\n" +
              "SEVICE_CONN_DATE,\n" +
              "EXCESS_TARIFF_RATE,\n" +
              "SUB_DIV_ID,\n" +
              "COM_MST_ALL_OFFICES_VIEW.OFFICE_NAME As officename,\n" +
              "PMS_DCB_BEN_TYPE.BEN_TYPE_DESC from PMS_DCB_MST_BENEFICIARY_METRE \n" +
              "join\n" +
              "PMS_DCB_MST_BENEFICIARY\n" +
              "on\n" +
              "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO\n" +
              "join\n" +
              "PMS_SCH_MASTER\n" +
              "on\n" +
              "PMS_SCH_MASTER.SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO \n" +
              "join \n" +
              "COM_MST_ALL_OFFICES_VIEW\n" +
              "on\n" +
              "PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID= COM_MST_ALL_OFFICES_VIEW.OFFICE_ID\n" +
              "join\n" +
              "PMS_DCB_BEN_TYPE\n" +
              "on\n" +
              "PMS_DCB_BEN_TYPE.BEN_TYPE_ID=PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID\n" +
              "where\n" +
              "PMS_DCB_MST_BENEFICIARY_METRE.office_id=?\n" +
              "ORDER BY PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID");*/
                    ps =
  connection.prepareStatement("select \n" + "PMS_DCB_MST_BENEFICIARY_METRE.METRE_SNO AS METRE_SNO,\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO AS Beneficiary_Sno,\n" +
                              "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_NAME AS BENEFICIARY_NAME,\n" +
                              "COM_MST_ALL_OFFICES_VIEW.OFFICE_NAME As officename,\n" +
                              "PMS_SCH_MASTER.SCH_NAME as SCH_NAME,\n" +
                              "PMS_DCB_BEN_TYPE.BEN_TYPE_ID AS BEN_TYPE_ID,\n" +
                              "PMS_DCB_BEN_TYPE.BEN_TYPE_DESC,\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.METRE_LOCATION AS METRE_LOCATION,\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.METRE_WORKING AS METRE_WORKING,\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BULKWS_CATEGORY AS BULKWS_CATEGORY,\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.METRE_WORKING AS METRE_WORKING,\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.METRE_FIXED AS METRE_FIXED,\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.TARIFF_RATE AS TARIFF_RATE\n" +
                              "from PMS_DCB_MST_BENEFICIARY_METRE\n" + "\n" +
                              "join\n" + "PMS_DCB_MST_BENEFICIARY\n" + "on\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO\n" +
                              "join\n" + "COM_MST_ALL_OFFICES_VIEW\n" +
                              "on\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID=COM_MST_ALL_OFFICES_VIEW.OFFICE_ID\n" +
                              "join\n" + "PMS_SCH_MASTER\n" + "on\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO\n" +
                              "JOIN\n" + "PMS_DCB_BEN_TYPE\n" + "ON\n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=PMS_DCB_BEN_TYPE.BEN_TYPE_ID\n" +
                              "where  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.office_id=? \n" +
                              "and  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=?  \n" +
                              "and  \n" +
                              "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=?  \n" +
                              "ORDER BY PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID");

                    ps.setInt(1, oid);
                    ps.setInt(2, Beneficiary_Sno);
                    ps.setInt(3, Beneficiary_Name);
                    res = ps.executeQuery();
                    while (res.next()) {

                        /*   System.out.println(res.getInt("METRE_SNO"));
                    System.out.println(res.getString("BENEFICIARY_NAME"));
                    System.out.println(res.getString("METRE_LOCATION"));
                    System.out.println(res.getString("METRE_FIXED"));
                    System.out.println(res.getInt("ALLOTED_QTY"));
                    System.out.println(res.getInt("MIN_BILL_QTY"));
                    System.out.println(res.getInt("METRE_INIT_READING"));
                    System.out.println(res.getDate("INIT_READING_RECORD_DT"));
                    System.out.println(res.getInt("MULTIPLY_FACTOR"));
                    System.out.println(res.getString("SCHEME_SNO"));
                    System.out.println(res.getString("HABITATION_SNO"));

                    System.out.println(res.getString("METRE_WORKING"));
                    System.out.println(res.getInt("BULKWS_CATEGORY"));
                    System.out.println(res.getInt("TARIFF_RATE"));
                    System.out.println(res.getInt("METRE_TYPE"));
                    System.out.println(res.getInt("SERVICE_CON_NO"));
                    System.out.println(res.getDate("SEVICE_CONN_DATE"));
                    System.out.println(res.getInt("EXCESS_TARIFF_RATE"));
                    System.out.println(res.getInt("SUB_DIV_ID"));
                    System.out.println(res.getString("BEN_TYPE_DESC"));

                    */

                        xmlvariable += "<recordfound> 1 </recordfound>";
                        xmlvariable +=
                                "<METRE_SNO>" + res.getInt("METRE_SNO") +
                                "</METRE_SNO>";
                        xmlvariable +=
                                "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") +
                                "</BENEFICIARY_NAME>";
                        xmlvariable +=
                                "<METRE_LOCATION>" + res.getString("METRE_LOCATION") +
                                "</METRE_LOCATION>";
                        xmlvariable +=
                                "<METRE_FIXED>" + res.getString("METRE_FIXED") +
                                "</METRE_FIXED>";
                        xmlvariable +=
                                "<BULKWS_CATEGORY>" + res.getInt("BULKWS_CATEGORY") +
                                "</BULKWS_CATEGORY>";

                        xmlvariable +=
                                "<meterworking>" + res.getString("METRE_WORKING") +
                                "</meterworking>";
                        //   xmlvariable += "<ALLOTED_QTY>" +res.getInt("ALLOTED_QTY") + "</ALLOTED_QTY>";
                        //    xmlvariable += "<MIN_BILL_QTY>" +res.getInt("MIN_BILL_QTY")+ "</MIN_BILL_QTY>";
                        //    xmlvariable += "<METRE_INIT_READING>" + res.getInt("METRE_INIT_READING")+ "</METRE_INIT_READING>";
                        //    xmlvariable += "<INIT_READING_RECORD_DT>" + res.getDate("INIT_READING_RECORD_DT") + "</INIT_READING_RECORD_DT>";
                        /*   if(res.getDate("INIT_READING_RECORD_DT")==null)
                        xmlvariable += "<INIT_READING_RECORD_DT>-</INIT_READING_RECORD_DT>";
                    else*/
                        //    xmlvariable += "<INIT_READING_RECORD_DT>" + res.getDate("INIT_READING_RECORD_DT") + "</INIT_READING_RECORD_DT>";
                        //    xmlvariable += "<MULTIPLY_FACTOR>" + res.getInt("MULTIPLY_FACTOR") + "</MULTIPLY_FACTOR>";
                        //   xmlvariable += "<SCHEME_SNO>" + res.getString("SCHEME_SNO") + "</SCHEME_SNO>";
                        xmlvariable +=
                                "<SCH_NAME>" + res.getString("SCH_NAME") +
                                "</SCH_NAME>";
                        //     xmlvariable += "<HABITATION_SNO>" + res.getString("HABITATION_SNO") + "</HABITATION_SNO>";
                        //    xmlvariable += "<METRE_WORKING>" + res.getString("METRE_WORKING") + "</METRE_WORKING>";

                        //    xmlvariable += "<BULKWS_CATEGORY>" + res.getInt("BULKWS_CATEGORY") + "</BULKWS_CATEGORY>";
                        xmlvariable +=
                                "<TARIFF_RATE>" + res.getDouble("TARIFF_RATE") +
                                "</TARIFF_RATE>";
                        //     xmlvariable += "<METRE_TYPE>" + res.getInt("METRE_TYPE") + "</METRE_TYPE>";
                        //     xmlvariable += "<SERVICE_CON_NO>" + res.getInt("SERVICE_CON_NO") + "</SERVICE_CON_NO>";
                        //   xmlvariable += "<SEVICE_CONN_DATE>" +res.getDate("SEVICE_CONN_DATE") + "</SEVICE_CONN_DATE>";
                        // xmlvariable += "<EXCESS_TARIFF_RATE>" + res.getInt("EXCESS_TARIFF_RATE") + "</EXCESS_TARIFF_RATE>";
                        // xmlvariable += "<SUB_DIV_ID>" + res.getInt("SUB_DIV_ID") + "</SUB_DIV_ID>";

                        xmlvariable +=
                                "<BEN_TYPE_DESC>" + res.getString("BEN_TYPE_DESC") +
                                "</BEN_TYPE_DESC>";
                        xmlvariable +=
                                "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID") +
                                "</BEN_TYPE_ID>";
                        xmlvariable +=
                                "<officename>" + res.getString("officename") +
                                "</officename>";
                        xmlvariable += "<flag>success</flag>";

                    }


                } catch (Exception e) {
                    xmlvariable += "<flag>failure</flag>";
                    System.out.println(e + "not reterived!");
                }

            } else {
                xmlvariable += "<flag>success</flag>";
                xmlvariable += "<recordfound> 0 </recordfound>";
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("update")) {

            System.out.println("Update values");
            xmlvariable = "<response>";
            xmlvariable += "<command>update</command>";
            /*   try  for duplicate records
           { ///
            if(Consumption_Category==0)
            {
              ps_insert = connection.prepareStatement("select count(*) as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE\n" +
              "where \n" +
              "BENEFICIARY_SNO=?\n" +
              "and\n" +
              "SUB_DIV_ID=?\n" +
              "and\n" +
              "SCHEME_SNO=?");
            ps_insert.setInt(1, Beneficiary_Name);
            ps_insert.setInt(2, SubDivision);
            ps_insert.setString(3, Schemes);

            res_insert=ps_insert.executeQuery();
            if(res_insert.next())
            {
               countinsert=res_insert.getInt("countinsert");
            }
            }
            else if (Consumption_Category==1)
            {
               ps_insert = connection.prepareStatement("select count(*)as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE\n" +
               "where \n" +
               "BENEFICIARY_SNO=?\n" +
               "and\n" +
               "SUB_DIV_ID=?\n" +
               "and\n" +
               "scheme_sno=?\n" +
               "and\n" +
               "METRE_LOCATION=?");
               ps_insert.setInt(1, Beneficiary_Name);
               ps_insert.setInt(2, SubDivision);
               ps_insert.setString(3, Schemes);
               ps_insert.setString(4, Metre_Location);
               res_insert=ps_insert.executeQuery();
               if(res_insert.next())
               {

                        countinsert=res_insert.getInt("countinsert");
               }
            }
            }
            catch (Exception e)
            {
                System.out.println("Error in update");

            } */
            //
            try {
                // if (countinsert==0)
                //   { for duplicate check
                metre_sno =
                        Integer.parseInt(request.getParameter("Meter_Sno"));
                System.out.println(metre_sno);
                //    ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set BENEFICIARY_SNO=?,METRE_LOCATION=?,TARIFF_ID=?,METRE_FIXED=?,ALLOTED_QTY=?,MIN_BILL_QTY=?,METRE_INIT_READING=?,INIT_READING_RECORD_DT=to_date(?,'DD/MM/YYYY'),MULTIPLY_FACTOR=?,SCHEME_SNO=?,HABITATION_SNO=?,METRE_WORKING=?,METRE_CODE=? where METRE_SNO =?");
                ps =
  connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set " +
                              "SCH_TYPE_ID=?," + "BENEFICIARY_SNO=?," +
                              "METRE_LOCATION=?," + "TARIFF_ID=?," +
                              "METRE_FIXED=?," + "ALLOTED_QTY=?," +
                              "MIN_BILL_QTY=?," + "METRE_INIT_READING=?," +
                              "INIT_READING_RECORD_DT=to_date(?,'DD/MM/YYYY')," +
                              "MULTIPLY_FACTOR=?," + "OFFICE_ID=?," +
                              "SCHEME_SNO=?," + "HABITATION_SNO=?," +
                              "METRE_WORKING=?," + "UPDATED_BY_USER_ID=?," +
                              "UPDATED_DATE=SYSTIMESTAMP," +
                              "BULKWS_CATEGORY=?," + "TARIFF_RATE=?," +
                              "METRE_TYPE=?," + "SERVICE_CON_NO=?," +
                              "SEVICE_CONN_DATE=to_date(?,'DD/MM/YYYY')," +
                              "EXCESS_TARIFF_RATE=?," + "SUB_DIV_ID=?," +
                              "OTHERS_PRIVATE_SNO=?," +
                              "VILLAGE_PANCHAYAT_SNO=?," + "URBANLB_SNO=?," +
                              "BENEFICIARY_TYPE_ID=?, " + "ALLOTED_FLG=? " +
                              "where METRE_SNO =?");


                ps.setInt(1, SCH_TYPE_ID);
                ps.setInt(2, Beneficiary_Name);
                ps.setString(3, Metre_Location);
                ps.setInt(4, tariff_id);
                ps.setString(5, meterfixed);
                ps.setInt(6, Allotted_Qty);
                ps.setInt(7, Min_bill_Qty);
                ps.setInt(8, Metre_init_reading);
                ps.setString(9, Init_Reading_Record_date);
                ps.setDouble(10, Multiply_factor);
                ps.setInt(11, oid);
                ps.setInt(12, Schemes);
                ps.setInt(13, Habitation_Name);
                ps.setString(14, meterworking);
                ps.setString(15, userid);
                ps.setInt(16, Consumption_Category);
                ps.setDouble(17, Tariffrate);
                ps.setInt(18, Metre_Type);
                ps.setString(19, Service_Connection);
                ps.setString(20, Service_Connection_date);
                ps.setDouble(21, Excess_Tariff_Rate);
                ps.setInt(22, SubDivision);
                ps.setInt(23, OTHERS_PRIVATE_SNO);
                ps.setInt(24, VILLAGE_PANCHAYAT_SNO);
                ps.setInt(25, URBANLB_SNO);
                ps.setInt(26, BENEFICIARY_TYPE_ID);
                ps.setString(27, Applicableval);
                ps.setInt(28, metre_sno);

                ps.executeUpdate();
                System.out.println("updated");
                ps_off =
                        connection.prepareStatement("select office_name from  COM_MST_ALL_OFFICES_VIEW  where COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=?");
                System.out.println(SubDivision);
                ps_off.setInt(1, SubDivision);
                res_off = ps_off.executeQuery();
                if (res_off.next()) {
                    System.out.println(res_off.getString("office_name"));
                    xmlvariable +=
                            "<office_name>" + res_off.getString("office_name") +
                            "</office_name>";

                }
                ps_off =
                        connection.prepareStatement("select SCH_NAME from PMS_SCH_MASTER where SCH_SNO=?");
                ps_off.setInt(1, Schemes);
                res_off = ps_off.executeQuery();
                if (res_off.next()) {
                    System.out.println(res_off.getString("SCH_NAME"));
                    xmlvariable +=
                            "<SCH_NAME>" + res_off.getString("SCH_NAME") +
                            "</SCH_NAME>";

                }
                ps_off =
                        connection.prepareStatement("select BEN_TYPE_DESC  from PMS_DCB_BEN_TYPE where BEN_TYPE_ID=?");
                ps_off.setInt(1, BENEFICIARY_TYPE_ID);
                res_off = ps_off.executeQuery();
                if (res_off.next()) {
                    System.out.println(res_off.getString("BEN_TYPE_DESC"));
                    xmlvariable +=
                            "<BEN_TYPE_DESC>" + res_off.getString("BEN_TYPE_DESC") +
                            "</BEN_TYPE_DESC>";

                }
                ps_off =
                        connection.prepareStatement("select BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_SNO=?");
                ps_off.setInt(1, Beneficiary_Name);
                res_off = ps_off.executeQuery();
                if (res_off.next()) {
                    System.out.println(res_off.getString("BENEFICIARY_NAME"));
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + res_off.getString("BENEFICIARY_NAME") +
                            "</BENEFICIARY_NAME>";

                }

                xmlvariable += "<metre_sno>" + metre_sno + "</metre_sno>";
                xmlvariable +=
                        "<Metre_Location>" + Metre_Location + "</Metre_Location>";
                xmlvariable += "<Tariff_Id>" + Tariffrate + "</Tariff_Id>";
                xmlvariable += "<meterfixed>" + meterfixed + "</meterfixed>";
                xmlvariable +=
                        "<meterworking>" + meterworking + "</meterworking>";
                xmlvariable +=
                        "<Consumption_Category>" + Consumption_Category +
                        "</Consumption_Category>";
                xmlvariable +=
                        "<BENEFICIARY_TYPE_ID>" + BENEFICIARY_TYPE_ID + "</BENEFICIARY_TYPE_ID>";
                //    xmlvariable += "<countinsert>" + 0 + "</countinsert>";

                /*   xmlvariable += "<metre_sno>" + metre_sno + "</metre_sno>";
                 xmlvariable += "<Beneficiary_Sno>" + Beneficiary_Sno + "</Beneficiary_Sno>";
                 xmlvariable += "<Metre_Location>" + Metre_Location + "</Metre_Location>";
                 xmlvariable += "<Tariffrate>" + Tariffrate + "</Tariffrate>";
                 xmlvariable += "<meterfixed>" + meterfixed + "</meterfixed>";
                 xmlvariable += "<Allotted_Qty>" + Allotted_Qty + "</Allotted_Qty>";
                 xmlvariable += "<Min_bill_Qty>" + Min_bill_Qty + "</Min_bill_Qty>";
                 xmlvariable += "<Metre_init_reading>" + Metre_init_reading + "</Metre_init_reading>";
                 xmlvariable += "<Init_Reading_Record_date>" + Init_Reading_Record_date + "</Init_Reading_Record_date>";
                 xmlvariable += "<Multiply_factor>" + Multiply_factor + "</Multiply_factor>";
                 xmlvariable += "<scheme_name>" + scheme_name + "</scheme_name>";
                 xmlvariable += "<Habitation_name>" + Habitation_Name + "</Habitation_name>";
                 xmlvariable += "<meterworking>" + meterworking + "</meterworking>";
                 xmlvariable += "<Metre_Code>" + Metre_Code + "</Metre_Code>";*/
                xmlvariable += "<flag>success</flag>";
                //     } for duplicate check
                /* for duplicate check  else
                {
                     xmlvariable += "<countinsert>" + 1 + "</countinsert>";
                     xmlvariable += "<flag>success</flag>";
                }*/
            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not updated!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("delete")) {
            xmlvariable = "<response>";
            xmlvariable += "<command>delete</command>";
            try {
                metre_sno =
                        Integer.parseInt(request.getParameter("Meter_Sno"));
                System.out.println("Delete value is=" + metre_sno);
                psdel =
connection.prepareStatement("select count(*) as countvalue from  PMS_DCB_TRN_MONTHLY_PR\n" +
                            "join\n" + "PMS_DCB_MST_BENEFICIARY_METRE\n" +
                            "on\n" + "PMS_DCB_TRN_MONTHLY_PR.METRE_SNO=?");
                psdel.setInt(1, metre_sno);
                resdel = psdel.executeQuery();
                if (resdel.next()) {
                    delvalue = resdel.getInt("countvalue");
                }
                System.out.println("countvalue" + delvalue);
                if (delvalue < 1) {
                    ps =
  connection.prepareStatement("delete from PMS_DCB_MST_BENEFICIARY_METRE where METRE_SNO=?");
                    ps.setInt(1, metre_sno);
                    ps.executeUpdate();
                    System.out.println("deleted");
                    xmlvariable += "<checkcons>0</checkcons>";
                    xmlvariable += "<metre_sno>" + metre_sno + "</metre_sno>";
                    /* xmlvariable += "<Beneficiary_Sno>" + Beneficiary_Sno + "</Beneficiary_Sno>";
                        xmlvariable += "<Metre_Location>" + Metre_Location + "</Metre_Location>";
                        xmlvariable += "<Tariffrate>" + Tariffrate + "</Tariffrate>";
                        xmlvariable += "<meterfixed>" + meterfixed + "</meterfixed>";
                        xmlvariable += "<Allotted_Qty>" + Allotted_Qty + "</Allotted_Qty>";
                        xmlvariable += "<Min_bill_Qty>" + Min_bill_Qty + "</Min_bill_Qty>";
                        xmlvariable += "<Metre_init_reading>" + Metre_init_reading + "</Metre_init_reading>";
                        xmlvariable += "<Init_Reading_Record_date>" + Init_Reading_Record_date + "</Init_Reading_Record_date>";
                        xmlvariable += "<Multiply_factor>" + Multiply_factor + "</Multiply_factor>";
                        xmlvariable += "<scheme_name>" + scheme_name + "</scheme_name>";
                        xmlvariable += "<Habitation_name>" + Habitation_Name + "</Habitation_name>";
                        xmlvariable += "<meterworking>" + meterworking + "</meterworking>";
                      //  xmlvariable += "<Metre_Code>" + Metre_Code + "</Metre_Code>";*/
                    xmlvariable += "<flag>success</flag>";
                } else {
                    xmlvariable += "<metre_sno>" + metre_sno + "</metre_sno>";
                    xmlvariable += "<checkcons>1</checkcons>";
                    xmlvariable += "<flag>success</flag>";
                }
            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not deleted!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadbeneficiarytype")) {
            System.out.println("loadbeneficiary");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiarytype</command>";
            try {
                ps =
  connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");

                res = ps.executeQuery();
                System.out.println("loadbeneficiarytype");
                while (res.next()) {
                    System.out.println("loadbeneficiarytype");
                    System.out.println(res.getInt("BEN_TYPE_ID"));
                    System.out.println(res.getString("BEN_TYPE_DESC"));
                    System.out.println(res.getString("BEN_TYPE_SDESC"));

                    xmlvariable +=
                            "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID") +
                            "</BEN_TYPE_ID>";
                    xmlvariable +=
                            "<BEN_TYPE_DESC>" + res.getString("BEN_TYPE_DESC") +
                            "</BEN_TYPE_DESC>";
                    xmlvariable +=
                            "<BEN_TYPE_SDESC>" + res.getString("BEN_TYPE_SDESC") +
                            "</BEN_TYPE_SDESC>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadhabitations")) {
            System.out.println("loadhabitations");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadhabitations</command>";
            Beneficiary_type =
                    Integer.parseInt(request.getParameter("Beneficiary_type"));

            try {
                ps =
  connection.prepareStatement("SELECT HAB_SNO,\n" + "HAB_NAME FROM PMS_DCB_MST_BENEFICIARY\n" +
                              "JOIN\n" + "COM_MST_HABITATIONS\n" + "ON\n" +
                              "COM_MST_HABITATIONS.PANCH_SNO=PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO\n" +
                              "WHERE\n" +
                              "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_TYPE_ID=?\n" +
                              "AND\n" + "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?");
                //5000
                ps.setInt(1, Beneficiary_type);
                ps.setInt(2, oid);
                res = ps.executeQuery();

                while (res.next()) {

                    xmlvariable +=
                            "<HAB_CODE>" + res.getInt("HAB_SNO") + "</HAB_CODE>";
                    xmlvariable +=
                            "<HNAME>" + res.getString("HAB_NAME") + "</HNAME>";
                    xmlvariable += "<flag>success</flag>";


                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadbeneficiaryname")) {
            System.out.println("loadbeneficiaryname");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiaryname</command>";
            Beneficiary_type =
                    Integer.parseInt(request.getParameter("Beneficiary_type"));
            System.out.println("oid" + oid);
            try {
                ps =
  connection.prepareStatement("select BENEFICIARY_SNO,\n" + "BENEFICIARY_TYPE_ID,\n" +
                              "BENEFICIARY_NAME,\n" + "OTHERS_PRIVATE_SNO,\n" +
                              "VILLAGE_PANCHAYAT_SNO,\n" + "URBANLB_SNO,\n" +
                              "PMS_DCB_MST_BENEFICIARY.OFFICE_ID\n" +
                              "from PMS_DCB_MST_BENEFICIARY\n" + "where\n" +
                              "BENEFICIARY_TYPE_ID=?\n" + "and\n" +
                              "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?");
                //officeid=5340
                ps.setInt(1, Beneficiary_type);
                ps.setInt(2, oid);


                res = ps.executeQuery();
                flagname = 0;
                while (res.next()) {
                    flagname = 1;
                    System.out.println(res.getInt("BENEFICIARY_SNO"));
                    System.out.println(res.getString("BENEFICIARY_NAME"));

                    xmlvariable +=
                            "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") +
                            "</BENEFICIARY_SNO>";
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") +
                            "</BENEFICIARY_NAME>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + res.getInt("BENEFICIARY_TYPE_ID") +
                            "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<OTHERS_PRIVATE_SNO>" + res.getInt("OTHERS_PRIVATE_SNO") +
                            "</OTHERS_PRIVATE_SNO>";
                    xmlvariable +=
                            "<VILLAGE_PANCHAYAT_SNO>" + res.getInt("VILLAGE_PANCHAYAT_SNO") +
                            "</VILLAGE_PANCHAYAT_SNO>";
                    xmlvariable +=
                            "<URBANLB_SNO>" + res.getInt("URBANLB_SNO") +
                            "</URBANLB_SNO>";

                    xmlvariable += "<flag>success</flag>";

                }
                if (flagname == 0) {
                    xmlvariable +=
                            "<BENEFICIARY_SNO>" + -1 + "</BENEFICIARY_SNO>";
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + "No Data" + "</BENEFICIARY_NAME>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + -1 + "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<OTHERS_PRIVATE_SNO>" + -1 + "</OTHERS_PRIVATE_SNO>";
                    xmlvariable +=
                            "<VILLAGE_PANCHAYAT_SNO>" + -1 + "</VILLAGE_PANCHAYAT_SNO>";
                    xmlvariable += "<URBANLB_SNO>" + -1 + "</URBANLB_SNO>";


                    xmlvariable += "<flag>success</flag>";

                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }

        else if (command_var.equalsIgnoreCase("loadcategory")) {
            System.out.println("loadtraffic");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadcategory</command>";
            Beneficiary_Name =
                    Integer.parseInt(request.getParameter("Beneficiary_Name"));
            try {
                ps =
  connection.prepareStatement("select BENEFICIARY_SNO,BENEFICIARY_NAME,BEN_CONS_CATEGORY,BENEFICIARY_TYPE_ID,OTHERS_PRIVATE_SNO,\n" +
                              "VILLAGE_PANCHAYAT_SNO,\n" +
                              "URBANLB_SNO from PMS_DCB_MST_BENEFICIARY\n" +
                              "WHERE\n" + "BENEFICIARY_SNO=?");
                ps.setInt(1, Beneficiary_Name);

                res = ps.executeQuery();

                if (res.next()) {

                    System.out.println("loadtraffic");
                    tempvar = res.getInt("BENEFICIARY_TYPE_ID");
                    BEN_CONS_CATEGORY = res.getInt("BEN_CONS_CATEGORY");
                    xmlvariable +=
                            "<BEN_CONS_CATEGORY>" + res.getInt("BEN_CONS_CATEGORY") +
                            "</BEN_CONS_CATEGORY>";
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") +
                            "</BENEFICIARY_NAME>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + res.getInt("BENEFICIARY_TYPE_ID") +
                            "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<OTHERS_PRIVATE_SNO>" + res.getInt("OTHERS_PRIVATE_SNO") +
                            "</OTHERS_PRIVATE_SNO>";
                    xmlvariable +=
                            "<VILLAGE_PANCHAYAT_SNO>" + res.getInt("VILLAGE_PANCHAYAT_SNO") +
                            "</VILLAGE_PANCHAYAT_SNO>";
                    xmlvariable +=
                            "<URBANLB_SNO>" + res.getInt("URBANLB_SNO") +
                            "</URBANLB_SNO>";

                    System.out.println("tempvar=" + tempvar);
                    flagvariable = 0;
                    ps_new =
                            connection.prepareStatement("select TARIFF_ID,  \n" +
                                                        "BENEFICIARY_TYPE,  \n" +
                                                        "TARIFF_RATE  \n" +
                                                        "from  \n" +
                                                        "PMS_DCB_MST_TARIFF  \n" +
                                                        "where BENEFICIARY_TYPE=?\n" +
                                                        "and\n" +
                                                        "ACTIVE_STATUS='A'");
                    System.out.println("tempvar1=" + tempvar);
                    ps_new.setInt(1, tempvar);
                    res_new = ps_new.executeQuery();

                    if (res_new.next()) {
                        flagvariable = 1;
                        System.out.println(res_new.getInt("TARIFF_RATE"));
                    }
                    System.out.println("flagvariable" + flagvariable);
                    if (flagvariable == 1) {

                        xmlvariable +=
                                "<TARIFF_ID>" + res_new.getInt("TARIFF_ID") +
                                "</TARIFF_ID>";
                        xmlvariable +=
                                "<TARIFF_RATE>" + res_new.getDouble("TARIFF_RATE") +
                                "</TARIFF_RATE>";
                        xmlvariable +=
                                "<BENEFICIARY_TYPE>" + res_new.getInt("BENEFICIARY_TYPE") +
                                "</BENEFICIARY_TYPE>";
                        xmlvariable += "<flag>success</flag>";
                    } else {
                        xmlvariable += "<TARIFF_ID>  -1  </TARIFF_ID>";
                        xmlvariable += "<TARIFF_RATE>tariffrate</TARIFF_RATE>";
                        xmlvariable +=
                                "<BENEFICIARY_TYPE>" + 0 + "</BENEFICIARY_TYPE>";
                        xmlvariable += "<flag>success</flag>";
                    }


                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadsubdivision")) {
            System.out.println("loadsubdivision");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadsubdivision</command>";

            try {
                ps_new8 =
                        connection.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID AS EMPLOYEE_ID ,\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW.SUBDIVISION_OFFICE_ID AS SUBDIVISION_OFFICE_ID ,\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID AS OFFICE_ID,\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID AS DIVISION_OFFICE_ID,\n" +
                                                    "OFFICE_LEVEL_ID AS OFFICE_LEVEL_ID ,\n" +
                                                    "OFFICE_NAME AS OFFICE_NAME\n" +
                                                    "from HRM_EMP_CURRENT_POSTING\n" +
                                                    "JOIN\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW\n" +
                                                    "ON\n" +
                                                    "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID=HRM_EMP_CURRENT_POSTING.OFFICE_ID\n" +
                                                    "AND\n" +
                                                    "OFFICE_LEVEL_ID='SD'\n" +
                                                    "WHERE\n" +
                                                    "HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=?");
                //empid=2513
                ps_new8.setInt(1, empid);
                res_new8 = ps_new8.executeQuery();
                flagvariable = 0;
                while (res_new8.next()) {
                    flagvariable = 1;
                    xmlvariable +=
                            "<SUBDIVISION_OFFICE_ID>" + res_new8.getInt("SUBDIVISION_OFFICE_ID") +
                            "</SUBDIVISION_OFFICE_ID>";
                    xmlvariable +=
                            "<OFFICE_NAME>" + res_new8.getString("OFFICE_NAME") +
                            "</OFFICE_NAME>";
                    xmlvariable += "<flag>success</flag>";


                }
                if (flagvariable == 0) {
                    xmlvariable +=
                            "<SUBDIVISION_OFFICE_ID>-1</SUBDIVISION_OFFICE_ID>";
                    xmlvariable += "<OFFICE_NAME>No data</OFFICE_NAME>";
                    xmlvariable += "<flag>success</flag>";


                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadschemes")) {
            System.out.println("loadschemes");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadschemes</command>";

            try {
                ps =
  connection.prepareStatement("select SCH_SNO,SCH_NAME,OFFICE_ID,SCH_TYPE_ID\n" +
                              "              from PMS_SCH_MASTER\n" +
                              "                where office_id=? and SCH_CATEGORY_ID=2 and SCH_STATUS_ID=4 and MAINTAINED_BY_TWAD='Y'");
                //5110
                ps.setInt(1, oid);

                res = ps.executeQuery();
                flagvariable = 0;
                while (res.next()) {
                    flagvariable = 1;
                    System.out.println(res.getInt("SCH_SNO"));
                    System.out.println(res.getString("SCH_NAME"));
                    xmlvariable +=
                            "<SCHEME_ID>" + res.getInt("SCH_SNO") + "</SCHEME_ID>";
                    xmlvariable +=
                            "<SCHEME_NAME>" + res.getString("SCH_NAME") +
                            "</SCHEME_NAME>";
                    xmlvariable +=
                            "<SCH_TYPE_ID>" + res.getInt("SCH_TYPE_ID") +
                            "</SCH_TYPE_ID>";
                    xmlvariable += "<flag>success</flag>";


                }
                if (flagvariable == 0) {
                    xmlvariable += "<SCHEME_ID>-1</SCHEME_ID>";
                    xmlvariable += "<SCHEME_NAME>No data</SCHEME_NAME>";
                    xmlvariable += "<flag>success</flag>";
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("subdivisiondis")) {
            System.out.println("subdivisiondis");
            xmlvariable = "<response>";
            xmlvariable += "<command>subdivisiondis</command>";
            subdivisionid =
                    Integer.parseInt(request.getParameter("subdivisionid"));

            try {
                ps_new8 =
                        connection.prepareStatement("select OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where office_id=?");
                ps_new8.setInt(1, subdivisionid);
                res_new8 = ps_new8.executeQuery();
                flagvariable = 0;
                if (res_new8.next()) {
                    xmlvariable +=
                            "<OFFICE_ID>" + res_new8.getInt("OFFICE_ID") +
                            "</OFFICE_ID>";
                    xmlvariable +=
                            "<OFFICE_NAME>" + res_new8.getString("OFFICE_NAME") +
                            "</OFFICE_NAME>";

                    xmlvariable += "<flag>success</flag>";


                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("Schemesval")) {
            System.out.println("subdivisiondis");
            xmlvariable = "<response>";
            xmlvariable += "<command>Schemesval</command>";
            Schemesid = Integer.parseInt(request.getParameter("Schemesid"));

            try {
                ps_new8 =
                        connection.prepareStatement("select SCH_SNO,SCH_NAME,OFFICE_ID,SCH_TYPE_ID from PMS_SCH_MASTER where\n" +
                                                    "SCH_SNO=?");
                ps_new8.setInt(1, Schemesid);
                res_new8 = ps_new8.executeQuery();
                flagvariable = 0;
                if (res_new8.next()) {
                    xmlvariable +=
                            "<SCH_SNO>" + res_new8.getInt("SCH_SNO") + "</SCH_SNO>";
                    xmlvariable +=
                            "<SCH_NAME>" + res_new8.getString("SCH_NAME") +
                            "</SCH_NAME>";
                    xmlvariable +=
                            "<SCH_TYPE_ID>" + res_new8.getInt("SCH_TYPE_ID") +
                            "</SCH_TYPE_ID>";
                    xmlvariable += "<flag>success</flag>";


                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadhabitationlist")) {

            xmlvariable = "<response>";
            xmlvariable += "<command>loadhabitationlist</command>";
            hablist = Integer.parseInt(request.getParameter("hablist"));

            try {
                ps_new8 =
                        connection.prepareStatement("select PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,\n" +
                                                    "PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO,\n" +
                                                    "COM_MST_HABITATIONS.HAB_SNO As HAB_SNO ,\n" +
                                                    "COM_MST_HABITATIONS.HAB_NAME As HAB_NAME\n" +
                                                    "from PMS_DCB_MST_BENEFICIARY\n" +
                                                    "join\n" +
                                                    "COM_MST_HABITATIONS\n" +
                                                    "on\n" +
                                                    "PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO=COM_MST_HABITATIONS.PANCH_SNO\n" +
                                                    "where\n" +
                                                    "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=?");
                ps_new8.setInt(1, hablist);
                res_new8 = ps_new8.executeQuery();
                flagvariable = 0;
                while (res_new8.next()) {
                    flagvariable = 1;
                    xmlvariable +=
                            "<HAB_SNO>" + res_new8.getInt("HAB_SNO") + "</HAB_SNO>";
                    xmlvariable +=
                            "<HAB_NAME>" + res_new8.getString("HAB_NAME") +
                            "</HAB_NAME>";

                    xmlvariable += "<flag>success</flag>";
                }
                if (flagvariable == 0) {
                    xmlvariable += "<HAB_SNO>-1</HAB_SNO>";
                    xmlvariable += "<HAB_NAME>No data</HAB_NAME>";

                    xmlvariable += "<flag>success</flag>";
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("getgrid")) {
            System.out.println("getting value");
            xmlvariable = "<response>";
            xmlvariable += "<command>getgrid</command>";
            msno = Integer.parseInt(request.getParameter("msno"));
            System.out.println("The value of getgrid" + msno);
            try {


                //  ps = connection.prepareStatement("select METRE_SNO,BENEFICIARY_SNO,METRE_LOCATION,TARIFF_ID,METRE_FIXED,ALLOTED_QTY,MIN_BILL_QTY,METRE_INIT_READING,INIT_READING_RECORD_DT,MULTIPLY_FACTOR,SCHEME_SNO,HABITATION_SNO,METRE_WORKING,METRE_CODE,UPDATED_BY_USER_ID,UPDATED_DATE from PMS_DCB_MST_BENEFICIARY_METRE ORDER BY METRE_SNO");
                ps =
  connection.prepareStatement("select SCH_TYPE_ID,BENEFICIARY_SNO,METRE_LOCATION,TARIFF_ID,METRE_FIXED,ALLOTED_QTY,MIN_BILL_QTY,METRE_INIT_READING,INIT_READING_RECORD_DT,MULTIPLY_FACTOR,OFFICE_ID,SCHEME_SNO,HABITATION_SNO,METRE_WORKING,METRE_CODE,UPDATED_BY_USER_ID,UPDATED_DATE,BULKWS_CATEGORY,TARIFF_RATE,METRE_TYPE,SERVICE_CON_NO,SEVICE_CONN_DATE,EXCESS_TARIFF_RATE,SUB_DIV_ID,METRE_W_WEF,METRE_NW_WEF,OTHERS_PRIVATE_SNO,VILLAGE_PANCHAYAT_SNO,URBANLB_SNO,BENEFICIARY_TYPE_ID,METRE_SNO,ALLOTED_FLG from PMS_DCB_MST_BENEFICIARY_METRE where METRE_SNO=? ");
                ps.setInt(1, msno);
                res = ps.executeQuery();
                while (res.next()) {

                    System.out.println("SCH_TYPE_ID" +
                                       res.getInt("SCH_TYPE_ID"));
                    System.out.println("BENEFICIARY_SNO" +
                                       res.getInt("BENEFICIARY_SNO"));
                    System.out.println("METRE_LOCATION" +
                                       res.getString("METRE_LOCATION"));
                    System.out.println("TARIFF_ID" + res.getInt("TARIFF_ID"));

                    System.out.println("METRE_FIXED" +
                                       res.getString("METRE_FIXED"));
                    System.out.println("ALLOTED_QTY:" +
                                       res.getInt("ALLOTED_QTY"));
                    System.out.println("MIN_BILL_QTY" +
                                       res.getInt("MIN_BILL_QTY"));
                    System.out.println("METRE_INIT_READING" +
                                       res.getInt("METRE_INIT_READING"));
                    System.out.println("INIT_READING_RECORD_DT" +
                                       res.getDate("INIT_READING_RECORD_DT"));
                    System.out.println("MULTIPLY_FACTOR" +
                                       res.getDouble("MULTIPLY_FACTOR"));
                    System.out.println("OFFICE_ID" + res.getInt("OFFICE_ID"));

                    System.out.println("SCHEME_SNO" +
                                       res.getString("SCHEME_SNO"));
                    System.out.println("HABITATION_SNO" +
                                       res.getString("HABITATION_SNO"));

                    System.out.println("METRE_WORKING" +
                                       res.getString("METRE_WORKING"));
                    System.out.println("BULKWS_CATEGORY" +
                                       res.getInt("BULKWS_CATEGORY"));
                    System.out.println("TARIFF_RATE" +
                                       res.getDouble("TARIFF_RATE"));
                    System.out.println("METRE_TYPE" +
                                       res.getInt("METRE_TYPE"));
                    System.out.println("SERVICE_CON_NO" +
                                       res.getString("SERVICE_CON_NO"));
                    System.out.println("SEVICE_CONN_DATE" +
                                       res.getDate("SEVICE_CONN_DATE"));
                    System.out.println("EXCESS_TARIFF_RATE" +
                                       res.getDouble("EXCESS_TARIFF_RATE"));
                    System.out.println("SUB_DIV_ID" +
                                       res.getInt("SUB_DIV_ID"));


                    System.out.println("OTHERS_PRIVATE_SNO" +
                                       res.getInt("OTHERS_PRIVATE_SNO"));
                    System.out.println("VILLAGE_PANCHAYAT_SNO" +
                                       res.getInt("VILLAGE_PANCHAYAT_SNO"));
                    System.out.println("URBANLB_SNO" +
                                       res.getInt("URBANLB_SNO"));
                    System.out.println("BENEFICIARY_TYPE_ID" +
                                       res.getInt("BENEFICIARY_TYPE_ID"));
                    System.out.println("METRE_SNO" + res.getInt("METRE_SNO"));

                    int temptypeid = res.getInt("BENEFICIARY_TYPE_ID");
                    System.out.println(temptypeid);
                    System.out.println(oid);
                    ps_ben_name =
                            connection.prepareStatement("select BENEFICIARY_SNO,\n" +
                                                        "BENEFICIARY_TYPE_ID, \n" +
                                                        "BENEFICIARY_NAME,\n" +
                                                        "OTHERS_PRIVATE_SNO,\n" +
                                                        "VILLAGE_PANCHAYAT_SNO,\n" +
                                                        "URBANLB_SNO,\n" +
                                                        "PMS_DCB_MST_BENEFICIARY.OFFICE_ID\n" +
                                                        "from PMS_DCB_MST_BENEFICIARY\n" +
                                                        "where\n" +
                                                        "BENEFICIARY_TYPE_ID=?\n" +
                                                        "and\n" +
                                                        "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?");
                    ps_ben_name.setInt(1, temptypeid);
                    ps_ben_name.setInt(2, oid);
                    res_ben_name = ps_ben_name.executeQuery();
                    while (res_ben_name.next()) {


                        xmlvariable +=
                                "<BENEFICIARY_SNO>" + res_ben_name.getInt("BENEFICIARY_SNO") +
                                "</BENEFICIARY_SNO>";
                        xmlvariable +=
                                "<BENEFICIARY_NAME>" + res_ben_name.getString("BENEFICIARY_NAME") +
                                "</BENEFICIARY_NAME>";
                    }
                    if (temptypeid == 6) {
                        int tempsno = res.getInt("BENEFICIARY_SNO");
                        System.out.println("msno" + msno);
                        xmlvariable += "<HAB_var>1 </HAB_var>";
                        ps_vill_pan =
                                connection.prepareStatement("select PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,\n" +
                                                            "PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO,\n" +
                                                            "COM_MST_HABITATIONS.HAB_SNO As HAB_SNO, \n" +
                                                            "COM_MST_HABITATIONS.HAB_NAME As HAB_NAME\n" +
                                                            "from PMS_DCB_MST_BENEFICIARY\n" +
                                                            "join\n" +
                                                            "COM_MST_HABITATIONS\n" +
                                                            "on\n" +
                                                            "PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO=COM_MST_HABITATIONS.PANCH_SNO\n" +
                                                            "where\n" +
                                                            "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=?");
                        ps_vill_pan.setInt(1, tempsno);

                        rs_vill_pan = ps_vill_pan.executeQuery();
                        while (rs_vill_pan.next()) {

                            System.out.println("asasasas");
                            xmlvariable +=
                                    "<HAB_SNO>" + rs_vill_pan.getInt("HAB_SNO") +
                                    "</HAB_SNO>";
                            xmlvariable +=
                                    "<HAB_NAME>" + rs_vill_pan.getString("HAB_NAME") +
                                    "</HAB_NAME>";
                        }

                    }

                    /*  pssno = connection.prepareStatement("select DISTINCT PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,\n" +
                    "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_NAME \n" +
                    "from \n" +
                    "PMS_DCB_MST_BENEFICIARY \n" +
                    "JOIN\n" +
                    "PMS_DCB_MST_BENEFICIARY_METRE\n" +
                    "ON\n" +
                    "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO\n" +
                    "WHERE \n" +
                    "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=1");
                    pssno.setInt(1,msno);
                    ressno = pssno.executeQuery();
                    if (ressno.next())
                    {
                        xmlvariable += "<BENEFICIARY_SNO>" +res.getString("BENEFICIARY_NAME") + "</BENEFICIARY_SNO>";
                    }*/
                    xmlvariable += "<HAB_var>0 </HAB_var>";
                    xmlvariable +=
                            "<BEN_SNO>" + res.getInt("BENEFICIARY_SNO") +
                            "</BEN_SNO>";
                    xmlvariable +=
                            "<METRE_SNO>" + res.getInt("METRE_SNO") + "</METRE_SNO>";
                    xmlvariable +=
                            "<SCH_TYPE_ID>" + res.getInt("SCH_TYPE_ID") +
                            "</SCH_TYPE_ID>";

                    xmlvariable +=
                            "<METRE_LOCATION>" + res.getString("METRE_LOCATION") +
                            "</METRE_LOCATION>";
                    xmlvariable +=
                            "<TARIFF_ID>" + res.getInt("TARIFF_ID") + "</TARIFF_ID>";
                    xmlvariable +=
                            "<METRE_FIXED>" + res.getString("METRE_FIXED") +
                            "</METRE_FIXED>";
                    xmlvariable +=
                            "<ALLOTED_QTY>" + res.getInt("ALLOTED_QTY") +
                            "</ALLOTED_QTY>";
                    xmlvariable +=
                            "<MIN_BILL_QTY>" + res.getInt("MIN_BILL_QTY") +
                            "</MIN_BILL_QTY>";
                    xmlvariable +=
                            "<METRE_INIT_READING>" + res.getInt("METRE_INIT_READING") +
                            "</METRE_INIT_READING>";
                    /*   xmlvariable += "<INIT_READING_RECORD_DT>" + res.getDate("INIT_READING_RECORD_DT") + "</INIT_READING_RECORD_DT>";*/
                    if (res.getDate("INIT_READING_RECORD_DT") == null)
                        xmlvariable +=
                                "<INIT_READING_RECORD_DT>-</INIT_READING_RECORD_DT>";
                    else
                        xmlvariable +=
                                "<INIT_READING_RECORD_DT>" + res.getDate("INIT_READING_RECORD_DT") +
                                "</INIT_READING_RECORD_DT>";
                    xmlvariable +=
                            "<MULTIPLY_FACTOR>" + res.getDouble("MULTIPLY_FACTOR") +
                            "</MULTIPLY_FACTOR>";
                    xmlvariable +=
                            "<OFFICE_ID>" + res.getInt("OFFICE_ID") + "</OFFICE_ID>";

                    xmlvariable +=
                            "<SCHEME_SNO>" + res.getString("SCHEME_SNO") +
                            "</SCHEME_SNO>";
                    xmlvariable +=
                            "<HABITATION_SNO>" + res.getString("HABITATION_SNO") +
                            "</HABITATION_SNO>";
                    xmlvariable +=
                            "<METRE_WORKING>" + res.getString("METRE_WORKING") +
                            "</METRE_WORKING>";

                    xmlvariable +=
                            "<BULKWS_CATEGORY>" + res.getInt("BULKWS_CATEGORY") +
                            "</BULKWS_CATEGORY>";
                    xmlvariable +=
                            "<TARIFF_RATE>" + res.getDouble("TARIFF_RATE") +
                            "</TARIFF_RATE>";
                    xmlvariable +=
                            "<METRE_TYPE>" + res.getInt("METRE_TYPE") + "</METRE_TYPE>";
                    xmlvariable +=
                            "<ALLOTED_FLG>" + res.getString("ALLOTED_FLG") +
                            "</ALLOTED_FLG>";

                    if (res.getString("SERVICE_CON_NO") == null)
                        xmlvariable += "<SERVICE_CON_NO>-</SERVICE_CON_NO>";
                    else
                        xmlvariable +=
                                "<SERVICE_CON_NO>" + res.getString("SERVICE_CON_NO") +
                                "</SERVICE_CON_NO>";
                    //    xmlvariable += "<SERVICE_CON_NO>" + res.getString("SERVICE_CON_NO") + "</SERVICE_CON_NO>";
                    if (res.getDate("SEVICE_CONN_DATE") == null)
                        xmlvariable +=
                                "<SEVICE_CONN_DATE>-</SEVICE_CONN_DATE>";
                    else
                        xmlvariable +=
                                "<SEVICE_CONN_DATE>" + res.getDate("SEVICE_CONN_DATE") +
                                "</SEVICE_CONN_DATE>";
                    //   xmlvariable += "<SEVICE_CONN_DATE>" +res.getDate("SEVICE_CONN_DATE") + "</SEVICE_CONN_DATE>";
                    xmlvariable +=
                            "<EXCESS_TARIFF_RATE>" + res.getDouble("EXCESS_TARIFF_RATE") +
                            "</EXCESS_TARIFF_RATE>";
                    xmlvariable +=
                            "<SUB_DIV_ID>" + res.getInt("SUB_DIV_ID") + "</SUB_DIV_ID>";


                    xmlvariable +=
                            "<OTHERS_PRIVATE_SNO>" + res.getInt("OTHERS_PRIVATE_SNO") +
                            "</OTHERS_PRIVATE_SNO>";
                    xmlvariable +=
                            "<VILLAGE_PANCHAYAT_SNO>" + res.getInt("VILLAGE_PANCHAYAT_SNO") +
                            "</VILLAGE_PANCHAYAT_SNO>";
                    xmlvariable +=
                            "<URBANLB_SNO>" + res.getInt("URBANLB_SNO") +
                            "</URBANLB_SNO>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_ID>" + res.getInt("BENEFICIARY_TYPE_ID") +
                            "</BENEFICIARY_TYPE_ID>";
                    xmlvariable +=
                            "<METRE_SNO>" + res.getInt("METRE_SNO") + "</METRE_SNO>";


                    xmlvariable += "<flag>success</flag>";

                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }

            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadhabname")) {
            System.out.println("loadhabname");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadhabname</command>";
            int bentypeval =
                Integer.parseInt(request.getParameter("bentypeval"));

            try {
                ps =
  connection.prepareStatement("select HAB_SNO,HAB_NAME from COM_MST_HABITATIONS where HAB_SNO=?");
                //5110
                ps.setInt(1, bentypeval);

                res = ps.executeQuery();
                flagvariable = 0;
                while (res.next()) {
                    flagvariable = 1;

                    xmlvariable +=
                            "<HAB_SNO>" + res.getInt("HAB_SNO") + "</HAB_SNO>";
                    xmlvariable +=
                            "<HAB_NAME>" + res.getString("HAB_NAME") + "</HAB_NAME>";
                    xmlvariable += "<flag>success</flag>";


                }
                if (flagvariable == 0) {
                    xmlvariable += "<HAB_SNO>-1</HAB_SNO>";
                    xmlvariable += "<HAB_NAME>No data</HAB_NAME>";
                    xmlvariable += "<flag>success</flag>";
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadschname")) {
            System.out.println("loadschname");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadschname</command>";
            int schvalue = Integer.parseInt(request.getParameter("schvalue"));

            try {
                ps =
  connection.prepareStatement("select SCH_SNO,SCH_NAME,SCH_TYPE_ID from PMS_SCH_MASTER where SCH_SNO=?");
                //5110
                ps.setInt(1, schvalue);

                res = ps.executeQuery();
                flagvariable = 0;
                while (res.next()) {
                    flagvariable = 1;

                    xmlvariable +=
                            "<SCH_SNO>" + res.getInt("SCH_SNO") + "</SCH_SNO>";
                    xmlvariable +=
                            "<SCH_NAME>" + res.getString("SCH_NAME") + "</SCH_NAME>";
                    xmlvariable +=
                            "<SCH_TYPE_ID>" + res.getInt("SCH_TYPE_ID") +
                            "</SCH_TYPE_ID>";
                    xmlvariable += "<flag>success</flag>";


                }
                if (flagvariable == 0) {
                    xmlvariable += "<SCH_SNO>-1</SCH_SNO>";
                    xmlvariable += "<SCH_NAME>No data</SCH_NAME>";
                    xmlvariable += "<flag>success</flag>";
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("divisionname")) {
            xmlvariable = "<response>";
            xmlvariable += "<command>divisionname</command>";
            try {


                ps_oid =
                        connection.prepareStatement("select OFFICE_NAME from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?");
                ps_oid.setInt(1, oid);
                result_new = ps_oid.executeQuery();
                if (result_new.next()) {
                    officename = result_new.getString("OFFICE_NAME");
                    xmlvariable +=
                            "<officename>" + officename + "</officename>";
                    xmlvariable += "<flag>success</flag>";
                    System.out.println(officename);
                } else {
                    xmlvariable += "<officename>0</officename>";
                    xmlvariable += "<flag>success</flag>";

                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("habcheckdup")) {
            System.out.println("habcheckdup");
            System.out.println(Beneficiary_Name);
            System.out.println(SubDivision);
            System.out.println(Schemes);
            System.out.println(Habitation_Name);
            xmlvariable = "<response>";
            xmlvariable += "<command>habcheckdup</command>";
            try {
                ps =
  connection.prepareStatement("select count(*)as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE    \n" +
                              "where     \n" + "BENEFICIARY_SNO=?    \n" +
                              "and    \n" + "SUB_DIV_ID=?    \n" +
                              "and    \n" + "scheme_sno=?    \n" +
                              "and    \n" + "HABITATION_SNO=?");
                ps.setInt(1, Beneficiary_Name);
                ps.setInt(2, SubDivision);
                ps.setInt(3, Schemes);
                ps.setInt(4, Habitation_Name);
                res = ps.executeQuery();

                if (res.next()) {

                    System.out.println(res.getInt("countinsert"));
                    countinsert = res.getInt("countinsert");
                    xmlvariable +=
                            "<countinsert>" + countinsert + "</countinsert>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("schemecheck")) {
            System.out.println("schemecheck");
            System.out.println(Beneficiary_Name);
            System.out.println(SubDivision);
            System.out.println(Schemes);

            xmlvariable = "<response>";
            xmlvariable += "<command>schemecheck</command>";
            try {
                ps =
  connection.prepareStatement("select count(*) as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE  \n" +
                              "where   \n" + "BENEFICIARY_SNO=?  \n" +
                              "and  \n" + "SUB_DIV_ID=?  \n" + "and  \n" +
                              "SCHEME_SNO=?");
                ps.setInt(1, Beneficiary_Name);
                ps.setInt(2, SubDivision);
                ps.setInt(3, Schemes);

                res = ps.executeQuery();

                if (res.next()) {

                    System.out.println(res.getInt("countinsert"));
                    countinsert = res.getInt("countinsert");
                    xmlvariable +=
                            "<countinsert>" + countinsert + "</countinsert>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("metercheck")) {
            System.out.println("schemecheck");
            System.out.println(Beneficiary_Name);
            System.out.println(SubDivision);
            System.out.println(Schemes);

            xmlvariable = "<response>";
            xmlvariable += "<command>metercheck</command>";
            try {
                ps =
  connection.prepareStatement("select count(*)as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE    \n" +
                              "where     \n" + "BENEFICIARY_SNO=?    \n" +
                              "and    \n" + "SUB_DIV_ID=?    \n" +
                              "and    \n" + "scheme_sno=?    \n" +
                              "and    \n" + "METRE_LOCATION=?");
                ps.setInt(1, Beneficiary_Name);
                ps.setInt(2, SubDivision);
                ps.setInt(3, Schemes);
                ps.setString(4, Metre_Location);

                res = ps.executeQuery();

                if (res.next()) {

                    System.out.println(res.getInt("countinsert"));
                    countinsert = res.getInt("countinsert");
                    xmlvariable +=
                            "<countinsert>" + countinsert + "</countinsert>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loaddistrict")) {
            System.out.println("loaddistrict");


            xmlvariable = "<response>";
            xmlvariable += "<command>loaddistrict</command>";
            try {
                ps =
  connection.prepareStatement("SELECT COM_MST_DISTRICTS.DISTRICT_CODE DISTRICT_CODE,\n" +
                              "COM_MST_DISTRICTS.DISTRICT_NAME As DISTRICT_NAME\n" +
                              "FROM\n" + "PMS_DCB_DIV_DIST_MAP\n" + "JOIN\n" +
                              "COM_MST_DISTRICTS\n" + "ON\n" +
                              "COM_MST_DISTRICTS.DISTRICT_CODE=PMS_DCB_DIV_DIST_MAP.DISTRICT_CODE\n" +
                              "WHERE\n" +
                              "OFFICE_ID=? order by COM_MST_DISTRICTS.DISTRICT_NAME");
                ps.setInt(1, oid);


                res = ps.executeQuery();

                while (res.next()) {


                    xmlvariable +=
                            "<DISTRICT_CODE>" + res.getInt("DISTRICT_CODE") +
                            "</DISTRICT_CODE>";
                    xmlvariable +=
                            "<DISTRICT_NAME>" + res.getString("DISTRICT_NAME") +
                            "</DISTRICT_NAME>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadblocks")) {
            System.out.println("loadblocks");


            xmlvariable = "<response>";
            xmlvariable += "<command>loadblocks</command>";
            try {
                ps =
  connection.prepareStatement("SELECT BLOCK_SNO,\n" + "BLOCK_NAME FROM\n" +
                              "COM_MST_BLOCKS\n" + "WHERE\n" +
                              "DISTRICT_CODE=?" + "order by BLOCK_NAME");
                ps.setInt(1, district_Name);


                res = ps.executeQuery();

                while (res.next()) {


                    xmlvariable +=
                            "<BLOCK_SNO>" + res.getInt("BLOCK_SNO") + "</BLOCK_SNO>";
                    xmlvariable +=
                            "<BLOCK_NAME>" + res.getString("BLOCK_NAME") +
                            "</BLOCK_NAME>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadbenname")) {
            System.out.println("loadbenname");


            xmlvariable = "<response>";
            xmlvariable += "<command>loadbenname</command>";
            flagname = 0;
            System.out.println("block_Name is" + block_Name);
            try {
                ps =
  connection.prepareStatement("select BENEFICIARY_NAME,\n" + "BENEFICIARY_SNO\n" +
                              "FROM\n" + "PMS_DCB_MST_BENEFICIARY\n" +
                              "WHERE\n" + "BLOCK_SNO=?" +
                              "order by BENEFICIARY_NAME");
                ps.setInt(1, block_Name);

                res = ps.executeQuery();

                while (res.next()) {

                    flagname = 1;
                    xmlvariable +=
                            "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") +
                            "</BENEFICIARY_SNO>";
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") +
                            "</BENEFICIARY_NAME>";
                    xmlvariable += "<flag>success</flag>";
                }
                if (flagname == 0) {
                    xmlvariable += "<BENEFICIARY_SNO>-1</BENEFICIARY_SNO>";
                    xmlvariable +=
                            "<BENEFICIARY_NAME>" + "No Data" + "</BENEFICIARY_NAME>";
                    xmlvariable += "<flag>success</flag>";
                }

            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
        out.println(xmlvariable);
        System.out.println(xmlvariable);

    }
}

/*
select PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,
PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO,
COM_MST_HABITATIONS.HAB_SNO,
COM_MST_HABITATIONS.HAB_NAME
from PMS_DCB_MST_BENEFICIARY
join
COM_MST_HABITATIONS
on
PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO=COM_MST_HABITATIONS.PANCH_SNO
where
PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=62
*/

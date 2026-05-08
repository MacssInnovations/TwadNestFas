package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Date;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Pms_Dcb_Mst_Tariff extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        System.out.println("hai");
        String command_var = "";
        String xmlvariable = "";
        int charge_type_Id, tariff_Id;
        double tariff_Rate, excess_tariff_Rate;
        int flagvar = 0;
        int countbentype = 0;
        charge_type_Id = 0;
        tariff_Id = 0;
        tariff_Rate = 0;
        excess_tariff_Rate = 0;
        int countvar = 0;
        int rows = 0;
        int charge_type_id_val = 0;
        int ben_desc_id = 0;
        int uom_id = 0;
        int Beneficiary_Type = 0;
        int Uom = 0;
        int countinscheck = 0;
        //  String Tariff_code="";
        String status = "";
        String Tariff_wef = "";
        String tariff_Desc = "";
        String charge_type_desc_val = "";
        String ben_desc_val = "";
        String uom_desc_val = "";
        Connection connection = null;
        PreparedStatement ps, ps1, ps2, ps3, ps4, ps_ins_check;
        ResultSet res, res1, res2, res3, res4, res_ins_check;
        res1 = null;
        res = null;
        ps = null;
        ps1 = null;
        ps2 = null;
        ps3 = null;
        res2 = null;
        res3 = null;
        res_ins_check = null;
        try {
            command_var = request.getParameter("command");
        } catch (Exception e) {
            System.out.println("Error in reterival the command type");

        }
        try {
            Beneficiary_Type =
                    Integer.parseInt(request.getParameter("Beneficiary_Type"));

        } catch (Exception e) {
            System.out.println("Error in reterival the ben type");

        }
        try {
            tariff_Rate =
                    Double.parseDouble(request.getParameter("tariff_Rate"));
        } catch (Exception e) {
            System.out.println("Error in reterival the tariff_Rate");

        }
        try {
            Tariff_wef = request.getParameter("Tariff_wef");
        } catch (Exception e) {
            System.out.println("Error in reterival the Tariff_wef");

        }
        try {
            Uom = Integer.parseInt(request.getParameter("UOM"));
        } catch (Exception e) {
            System.out.println("Error in reterival the Uom");

        }
        try {

            //charge_type_Id=Integer.parseInt(request.getParameter("charge_type_Id"));
            // tariff_Desc=request.getParameter("tariff_Desc");

            // excess_tariff_Rate=Double.parseDouble(request.getParameter("excess_tariff_Rate"));


            //   Tariff_code=request.getParameter("Tariff_Code");

            status = request.getParameter("status");
            //  System.out.println(charge_type_Id);
            //   System.out.println(tariff_Desc);
            System.out.println(tariff_Id);
            System.out.println(tariff_Rate);
            //  System.out.println(excess_tariff_Rate);
            System.out.println(Tariff_wef);
            System.out.println(Uom);
            //     System.out.println(Tariff_code);
            System.out.println(status);


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
        if (command_var.equalsIgnoreCase("add")) {
            //System.out.println("Outside"+charge_type_Id);
            xmlvariable = "<response>";
            xmlvariable += "<command>add</command>";
            try {

                ps_ins_check =
                        connection.prepareStatement("select count(*) as countinscheck from  PMS_DCB_MST_TARIFF where BENEFICIARY_TYPE=? and ACTIVE_STATUS='A'");
                ps_ins_check.setInt(1, Beneficiary_Type);
                res_ins_check = ps_ins_check.executeQuery();
                if (res_ins_check.next()) {
                    System.out.println(res_ins_check.getInt("countinscheck"));
                    countinscheck = res_ins_check.getInt("countinscheck");

                }

            }


            catch (Exception e) {

                System.out.println("Error in reteriving the count");
            }

            if (countinscheck == 0) {
                try {
                    // ps = connection.prepareStatement("insert into PMS_DCB_MST_TARIFF(CHARGE_TYPE_ID,TARIFF_RATE,EXCESS_TARIFF_RATE,TARIFF_WEF,UOM,TARIFF_CODE,UPDATED_BY_USER_ID,UPDATED_TIME,BENEFICIARY_TYPE) values(?,?,?,to_date(?,'DD/MM/YYYY'),?,?,'twad',SYSTIMESTAMP,?)");
                    ps =
  connection.prepareStatement("insert into PMS_DCB_MST_TARIFF(TARIFF_RATE,TARIFF_WEF,UOM,UPDATED_BY_USER_ID,UPDATED_TIME,BENEFICIARY_TYPE,ACTIVE_STATUS) values(?,to_date(?,'DD/MM/YYYY'),?,'twad',SYSTIMESTAMP,?,?)");
                    //  ps.setInt(1, charge_type_Id);
                    //ps.setString(2, tariff_Desc);
                    ps.setDouble(1, tariff_Rate);
                    // ps.setDouble(2, excess_tariff_Rate);
                    ps.setString(2, Tariff_wef);
                    ps.setInt(3, Uom);
                    //  ps.setString(6, Tariff_code);
                    //     ps.setString(7, userid);
                    ps.setInt(4, Beneficiary_Type);
                    ps.setString(5, status);
                    ps.executeUpdate();
                    System.out.println("Inserted");

                    ps1 =
 connection.prepareStatement("select max(tariff_Id) as tariff_Id from PMS_DCB_MST_TARIFF");
                    System.out.println(ps1);
                    res = ps1.executeQuery();
                    if (res.next()) {
                        System.out.println(res.getInt("tariff_Id"));
                        tariff_Id = res.getInt("tariff_Id");
                    }

                    /*   ps2 = connection.prepareStatement("select CHARGE_TYPE_ID,CHARGE_TYPE_DESC FROM PMS_DCB_MST_CHARGES_TYPE where CHARGE_TYPE_ID = ?");
                        ps2.setInt(1, charge_type_Id);
                        System.out.println(ps2);
                        res2 = ps2.executeQuery();
                        if(res2.next())
                        {
                            System.out.println(res2.getString("CHARGE_TYPE_DESC"));
                            charge_type_id_val= res2.getInt("CHARGE_TYPE_ID");
                            charge_type_desc_val= res2.getString("CHARGE_TYPE_DESC");
                        }
                        */
                    ps3 =
 connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE where BEN_TYPE_ID= ?");
                    ps3.setInt(1, Beneficiary_Type);
                    System.out.println(ps2);
                    res3 = ps3.executeQuery();
                    if (res3.next()) {
                        System.out.println(res3.getString("BEN_TYPE_DESC"));
                        ben_desc_id = res3.getInt("BEN_TYPE_ID");
                        ben_desc_val = res3.getString("BEN_TYPE_DESC");
                    }

                    ps4 =
 connection.prepareStatement("select UOM_SNO,UOM_DESC FROM PMS_DCB_MST_UOM where UOM_SNO= ?");
                    ps4.setInt(1, Uom);
                    System.out.println(ps2);
                    res4 = ps4.executeQuery();
                    if (res4.next()) {
                        System.out.println(res4.getString("UOM_DESC"));
                        uom_id = res4.getInt("UOM_SNO");
                        uom_desc_val = res4.getString("UOM_DESC");
                    }
                    //   System.out.println("Inside"+charge_type_Id);
                    System.out.println("Inside" + tariff_Rate);
                    // System.out.println("Inside"+excess_tariff_Rate);
                    System.out.println("Inside" + Tariff_wef);
                    System.out.println("Inside" + Uom);
                    //  System.out.println("Inside"+Tariff_code);
                    xmlvariable += "<tariff_Id>" + tariff_Id + "</tariff_Id>";
                    // xmlvariable += "<charge_type_Id>" + charge_type_desc_val + "</charge_type_Id>";
                    //   xmlvariable += "<tariff_Desc>" + tariff_Desc + "</tariff_Desc>";
                    xmlvariable += "<data>";
                    // xmlvariable += "<charge_type_Id>"+charge_type_id_val + "</charge_type_Id>";
                    // xmlvariable += "<charge_type_Id_desc>" +charge_type_desc_val +  "</charge_type_Id_desc>";
                    xmlvariable += "</data>";
                    xmlvariable +=
                            "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                    //   xmlvariable += "<excess_tariff_Rate>" + excess_tariff_Rate + "</excess_tariff_Rate>";
                    xmlvariable +=
                            "<Tariff_wef>" + Tariff_wef + "</Tariff_wef>";
                    // xmlvariable += "<Uom>" + uom_desc_val + "</Uom>";
                    xmlvariable += "<datatwo>";
                    xmlvariable += "<Uom_id>" + uom_id + "</Uom_id>";
                    xmlvariable +=
                            "<uom_val_desc>" + uom_desc_val + "</uom_val_desc>";
                    xmlvariable += "</datatwo>";
                    //   xmlvariable += "<Tariff_code>" + Tariff_code + "</Tariff_code>";
                    // xmlvariable += "<Beneficiary_Type>" + ben_desc_val + "</Beneficiary_Type>";
                    xmlvariable += "<dataone>";
                    xmlvariable +=
                            "<Beneficiary_Type_id>" + ben_desc_id + "</Beneficiary_Type_id>";
                    xmlvariable +=
                            "<Beneficiary_Type_desc>" + ben_desc_val + "</Beneficiary_Type_desc>";
                    xmlvariable += "</dataone>";
                    xmlvariable +=
                            "<activestatus>" + status + "</activestatus>";
                    xmlvariable += "<countinscheck>0</countinscheck>";
                    xmlvariable += "<flag>success</flag>";


                } catch (Exception e) {
                    xmlvariable += "<flag>failure</flag>";
                    xmlvariable += "<countinscheck>0</countinscheck>";
                    System.out.println(e + "not inserted!");
                }

            } else {
                xmlvariable += "<countinscheck>1</countinscheck>";
                xmlvariable += "<flag>success</flag>";

            }
            xmlvariable += "</response>";
        }

        else if (command_var.equalsIgnoreCase("get")) {
            System.out.println("getting value");
            xmlvariable = "<response>";
            xmlvariable += "<command>get</command>";
            /* try
            {
            ps2 = connection.prepareStatement("select count(*) from PMS_DCB_MST_TARIFF");
            res1 = ps2.executeQuery();
            System.out.println("Hai");
            while (res1.next())
            {
                        rows =res1.getInt(1);

            }

                System.out.println("countval"+rows);

            }
            catch (Exception e)
            {
                System.out.println("Exception caught"+e);
            }*/
            try {


                //ps = connection.prepareStatement("select TARIFF_ID,CHARGE_TYPE_ID,TARIFF_RATE,EXCESS_TARIFF_RATE,TARIFF_WEF,UOM,TARIFF_CODE,BENEFICIARY_TYPE from PMS_DCB_MST_TARIFF ORDER BY TARIFF_ID");
                ps =
  connection.prepareStatement("select PMS_DCB_MST_TARIFF.TARIFF_ID AS TARIFF_ID,\n" +
                              "PMS_DCB_MST_TARIFF.TARIFF_RATE AS TARIFF_RATE,\n" +
                              "PMS_DCB_BEN_TYPE.BEN_TYPE_DESC AS BEN_TYPE_DESC,\n" +
                              "PMS_DCB_MST_TARIFF.TARIFF_WEF AS TARIFF_WEF,\n" +
                              "PMS_DCB_MST_UOM.UOM_DESC AS UOM_DESC,\n" +
                              "PMS_DCB_MST_TARIFF.ACTIVE_STATUS AS ACTIVE_STATUS,\n" +
                              "PMS_DCB_MST_TARIFF.BENEFICIARY_TYPE AS BENEFICIARY_TYPE,\n" +
                              "PMS_DCB_MST_TARIFF.UOM AS UOM\n" + "FROM\n" +
                              "PMS_DCB_MST_TARIFF\n" + "JOIN\n" +
                              "PMS_DCB_BEN_TYPE\n" + "ON      \n" +
                              "PMS_DCB_MST_TARIFF.BENEFICIARY_TYPE=PMS_DCB_BEN_TYPE.BEN_TYPE_ID \n" +
                              "JOIN\n" + "PMS_DCB_MST_UOM\n" + "ON\n" +
                              "PMS_DCB_MST_TARIFF.UOM=PMS_DCB_MST_UOM.UOM_SNO\n" +
                              "WHERE PMS_DCB_MST_TARIFF.TARIFF_ID between 1 and 6\n" +
                              "and\n" +
                              "PMS_DCB_MST_TARIFF.ACTIVE_STATUS='A'\n" +
                              "ORDER BY\n" + "PMS_DCB_MST_TARIFF.TARIFF_ID");

                res = ps.executeQuery();
                //int numRows = res.getRow();
                // System.out.println("Number of rows"+numRows);
                //  System.out.println("countvalinsede - "+rows);
                // if(rows>=1)
                // {
                while (res.next()) {


                    /*  try
                    {
                    System.out.println(res.getInt("TARIFF_CODE"));
                    }
                        catch(Exception e)
                        {
                            System.out.println("eRROESATHIYA"+e);
                        }
                        */
                    try {
                        System.out.println(res.getString("CHARGE_TYPE_DESC"));
                    } catch (Exception e) {
                        System.out.println("eRROESATHIYA" + e);
                    }
                    try {
                        System.out.println(res.getDouble("TARIFF_RATE"));
                    } catch (Exception e) {
                        System.out.println("eRROESATHIYA" + e);
                    }
                    /*  try {
                        System.out.println(res.getDouble("EXCESS_TARIFF_RATE"));
                    }
                    catch (Exception e) {
                        System.out.println("eRROESATHIYA"+e);
                    }*/
                    try {
                        System.out.println(res.getDate("TARIFF_WEF"));
                    } catch (Exception e) {
                        System.out.println("eRROESATHIYA" + e);
                    }
                    try {
                        System.out.println(res.getString("UOM_DESC"));
                    } catch (Exception e) {
                        System.out.println("eRROESATHIYA" + e);
                    }
                    /*
                   try {
                        System.out.println(res.getString("TARIFF_CODE"));
                    }
                    catch (Exception e) {
                        System.out.println("eRROESATHIYA"+e);
                    }*/
                    try {
                        System.out.println(res.getString("BEN_TYPE_DESC"));
                    } catch (Exception e) {
                        System.out.println("eRROESATHIYA" + e);
                    }

                    xmlvariable +=
                            "<tariff_Id>" + res.getInt("TARIFF_ID") + "</tariff_Id>";
                    xmlvariable += "<data>";
                    //   xmlvariable += "<charge_type_Id>"+res.getInt("CHARGE_TYPE_ID") + "</charge_type_Id>";
                    // xmlvariable += "<charge_type_Id_desc>" + res.getString("CHARGE_TYPE_DESC") +  "</charge_type_Id_desc>";
                    xmlvariable += "</data>";
                    xmlvariable +=
                            "<tariff_Rate>" + res.getDouble("TARIFF_RATE") +
                            "</tariff_Rate>";
                    //   xmlvariable += "<excess_tariff_Rate>" +res.getDouble("EXCESS_TARIFF_RATE") + "</excess_tariff_Rate>";
                    xmlvariable +=
                            "<Tariff_wef>" + res.getDate("TARIFF_WEF") + "</Tariff_wef>";
                    //  xmlvariable += "<Uom>" + res.getString("UOM_DESC") + "</Uom>";
                    //  xmlvariable += "<Tariff_code>" + res.getString("TARIFF_CODE") + "</Tariff_code>";
                    xmlvariable += "<dataone>";
                    xmlvariable +=
                            "<Beneficiary_Type_id>" + res.getInt("BENEFICIARY_TYPE") +
                            "</Beneficiary_Type_id>";
                    xmlvariable +=
                            "<Beneficiary_Type_desc>" + res.getString("BEN_TYPE_DESC") +
                            "</Beneficiary_Type_desc>";
                    xmlvariable += "</dataone>";

                    //xmlvariable += "<charge_type_id_val>"+res.getInt("CHARGE_TYPE_ID") + "</charge_type_id_val>";
                    // xmlvariable += "<beneficiary_val>"+res.getInt("BENEFICIARY_TYPE") + "</beneficiary_val>";

                    xmlvariable += "<datatwo>";
                    xmlvariable +=
                            "<Uom_id>" + res.getInt("UOM") + "</Uom_id>";
                    xmlvariable +=
                            "<uom_val_desc>" + res.getString("UOM_DESC") +
                            "</uom_val_desc>";
                    xmlvariable += "</datatwo>";
                    xmlvariable +=
                            "<activestatus>" + res.getString("ACTIVE_STATUS") +
                            "</activestatus>";
                    xmlvariable += "<flag>success</flag>";

                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            /* if(countvar==0)
            {
                xmlvariable+= "<flag>failure</flag>";
                xmlvariable+="<recorddes>record</recorddes>";
                System.out.println("countervarzero");S
            }*/
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("update")) {
            System.out.println("Update values");
            xmlvariable = "<response>";
            xmlvariable += "<command>update</command>";
            try {
                tariff_Id =
                        Integer.parseInt(request.getParameter("tariff_Id"));
                System.out.println(tariff_Id);
                System.out.println(Beneficiary_Type);
                System.out.println(status);
                // ps = connection.prepareStatement("update PMS_DCB_MST_TARIFF set CHARGE_TYPE_ID=?,TARIFF_RATE=?,EXCESS_TARIFF_RATE=?,TARIFF_WEF=to_date(?,'DD/MM/YYYY'),UOM=?,TARIFF_CODE=?,BENEFICIARY_TYPE=? where TARIFF_ID =?");
                ps =
  connection.prepareStatement("update PMS_DCB_MST_TARIFF set TARIFF_RATE=?,TARIFF_WEF=to_date(?,'DD/MM/YYYY'),UOM=?,BENEFICIARY_TYPE=?,ACTIVE_STATUS=? where TARIFF_ID =?");
                //  ps.setInt(1, charge_type_Id);
                //ps.setString(2, tariff_Desc);
                ps.setDouble(1, tariff_Rate);
                //  ps.setDouble(2, excess_tariff_Rate);
                ps.setString(2, Tariff_wef);
                ps.setInt(3, Uom);
                //   ps.setString(6, Tariff_code);
                ps.setInt(4, Beneficiary_Type);
                ps.setString(5, status);
                ps.setInt(6, tariff_Id);
                ps.executeUpdate();

                /*    ps2 = connection.prepareStatement("select CHARGE_TYPE_ID,CHARGE_TYPE_DESC FROM PMS_DCB_MST_CHARGES_TYPE where CHARGE_TYPE_ID = ?");
                 ps2.setInt(1, charge_type_Id);

                 res2 = ps2.executeQuery();
                 if(res2.next())
                 {
                    // System.out.println(res2.getString("CHARGE_TYPE_DESC"));
                     charge_type_id_val= res2.getInt("CHARGE_TYPE_ID");
                     charge_type_desc_val= res2.getString("CHARGE_TYPE_DESC");
                 }
               */
                ps3 =
 connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE where BEN_TYPE_ID= ?");
                ps3.setInt(1, Beneficiary_Type);

                res3 = ps3.executeQuery();
                if (res3.next()) {
                    System.out.println(res3.getString("BEN_TYPE_DESC"));
                    ben_desc_id = res3.getInt("BEN_TYPE_ID");
                    ben_desc_val = res3.getString("BEN_TYPE_DESC");
                }

                ps4 =
 connection.prepareStatement("select UOM_SNO,UOM_DESC FROM PMS_DCB_MST_UOM where UOM_SNO= ?");
                ps4.setInt(1, Uom);

                res4 = ps4.executeQuery();
                if (res4.next()) {
                    System.out.println(res4.getString("UOM_DESC"));
                    uom_id = res4.getInt("UOM_SNO");
                    uom_desc_val = res4.getString("UOM_DESC");
                }

                System.out.println("updated");
                xmlvariable += "<tariff_Id>" + tariff_Id + "</tariff_Id>";
                xmlvariable += "<data>";
                // xmlvariable += "<charge_type_Id>"+charge_type_id_val + "</charge_type_Id>";
                // xmlvariable += "<charge_type_Id_desc>" +charge_type_desc_val +  "</charge_type_Id_desc>";
                xmlvariable += "</data>";


                //   xmlvariable += "<tariff_Desc>" + tariff_Desc + "</tariff_Desc>";
                xmlvariable +=
                        "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                //  xmlvariable += "<excess_tariff_Rate>" + excess_tariff_Rate + "</excess_tariff_Rate>";
                xmlvariable +=
                        "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                xmlvariable += "<Tariff_wef>" + Tariff_wef + "</Tariff_wef>";
                xmlvariable += "<datatwo>";
                xmlvariable += "<Uom_id>" + uom_id + "</Uom_id>";
                xmlvariable +=
                        "<uom_val_desc>" + uom_desc_val + "</uom_val_desc>";
                xmlvariable += "</datatwo>";
                //  xmlvariable += "<Uom>" + uom_desc_val + "</Uom>";
                //xmlvariable += "<Tariff_code>" + Tariff_code + "</Tariff_code>";
                //  xmlvariable += "<Beneficiary_Type>" + ben_desc_val + "</Beneficiary_Type>";
                xmlvariable += "<dataone>";
                xmlvariable +=
                        "<Beneficiary_Type_id>" + ben_desc_id + "</Beneficiary_Type_id>";
                xmlvariable +=
                        "<Beneficiary_Type_desc>" + ben_desc_val + "</Beneficiary_Type_desc>";
                xmlvariable += "</dataone>";
                xmlvariable += "<activestatus>" + status + "</activestatus>";
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
                tariff_Id =
                        Integer.parseInt(request.getParameter("tariff_Id"));
                ps =
  connection.prepareStatement("delete from PMS_DCB_MST_TARIFF where TARIFF_ID=?");
                ps.setInt(1, tariff_Id);
                ps.executeUpdate();
                System.out.println("deleted");
                xmlvariable += "<tariff_Id>" + tariff_Id + "</tariff_Id>";
                // xmlvariable += "<charge_type_Id>" + charge_type_Id + "</charge_type_Id>";
                //    xmlvariable += "<tariff_Desc>" + tariff_Desc + "</tariff_Desc>";
                xmlvariable +=
                        "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                // xmlvariable += "<excess_tariff_Rate>" + excess_tariff_Rate + "</excess_tariff_Rate>";
                xmlvariable +=
                        "<tariff_Rate>" + tariff_Rate + "</tariff_Rate>";
                xmlvariable += "<Tariff_wef>" + Tariff_wef + "</Tariff_wef>";
                xmlvariable += "<Uom>" + Uom + "</Uom>";
                //  xmlvariable += "<Tariff_code>" + Tariff_code + "</Tariff_code>";
                xmlvariable +=
                        "<Beneficiary_Type>" + Beneficiary_Type + "</Beneficiary_Type>";
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
  connection.prepareStatement("select CHARGE_TYPE_ID,CHARGE_TYPE_DESC from PMS_DCB_MST_CHARGES_TYPE");
                res = ps.executeQuery();
                System.out.println("comboload1");
                while (res.next()) {
                    System.out.println("comboload3");
                    System.out.println(res.getInt("CHARGE_TYPE_ID"));
                    System.out.println(res.getString("CHARGE_TYPE_DESC"));

                    xmlvariable +=
                            "<CHARGE_TYPE_ID>" + res.getInt("CHARGE_TYPE_ID") +
                            "</CHARGE_TYPE_ID>";
                    xmlvariable +=
                            "<CHARGE_TYPE_DESC_TABLE>" + res.getString("CHARGE_TYPE_DESC") +
                            "</CHARGE_TYPE_DESC_TABLE>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadbeneficiary")) {
            System.out.println("loadbeneficiary");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiary</command>";
            try {
                //ps = connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE from PMS_DCB_BEN_TYPE\n" +
                //"where BEN_TYPE_ID between 1 and 6 ORDER BY BEN_TYPE_ID");
                ps =
  connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE from PMS_DCB_BEN_TYPE\n" +
                              "where BEN_TYPE_CATEGORY='L'ORDER BY BEN_TYPE_ID");
                //  ps = connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE from PMS_DCB_BEN_TYPE");
                res = ps.executeQuery();
                System.out.println("loadbeneficiary");
                while (res.next()) {
                    System.out.println("loadbeneficiary");
                    System.out.println(res.getString("BEN_TYPE_DESC"));
                    System.out.println(res.getInt("BEN_TYPE_ID"));

                    xmlvariable +=
                            "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID") +
                            "</BEN_TYPE_ID>";
                    xmlvariable +=
                            "<BENEFICIARY_TYPE_DESC>" + res.getString("BEN_TYPE_DESC") +
                            "</BENEFICIARY_TYPE_DESC>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("loadUOM")) {
            System.out.println("loadUOM");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadUOM</command>";
            try {
                ps =
  connection.prepareStatement("select UOM_SNO,UOM_DESC from PMS_DCB_MST_UOM ORDER BY UOM_SNO");
                res = ps.executeQuery();
                System.out.println("loadUOM");
                while (res.next()) {
                    System.out.println("loadUOM");
                    System.out.println(res.getString("UOM_DESC"));
                    System.out.println(res.getInt("UOM_SNO"));

                    xmlvariable +=
                            "<UOM_SNO>" + res.getInt("UOM_SNO") + "</UOM_SNO>";
                    xmlvariable +=
                            "<UOM_DESC>" + res.getString("UOM_DESC") + "</UOM_DESC>";
                    xmlvariable += "<flag>success</flag>";
                }


            } catch (Exception e) {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        } else if (command_var.equalsIgnoreCase("bentypecheck")) {
            System.out.println("bentypecheck");
            xmlvariable = "<response>";
            xmlvariable += "<command>bentypecheck</command>";
            System.out.println("Beneficiary_Type" + Beneficiary_Type);

            try {
                ps =
  connection.prepareStatement("select count(*) as countbentype from  PMS_DCB_MST_TARIFF where BENEFICIARY_TYPE=? and ACTIVE_STATUS='A'");
                ps.setInt(1, Beneficiary_Type);
                res = ps.executeQuery();

                if (res.next()) {
                    System.out.println(res.getInt("countbentype"));
                    countbentype = res.getInt("countbentype");

                }
                if (countbentype > 0) {
                    xmlvariable += "<countbentype>1</countbentype>";
                    xmlvariable += "<flag>success</flag>";
                } else {
                    xmlvariable += "<countbentype>0</countbentype>";
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
    }
}


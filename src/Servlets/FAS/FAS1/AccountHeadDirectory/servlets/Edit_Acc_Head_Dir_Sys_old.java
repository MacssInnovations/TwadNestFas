package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Edit_Acc_Head_Dir_Sys_old extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
        ResultSet rs = null, rs2 = null;
        CallableStatement cs = null;
        PreparedStatement ps = null, ps2 = null;
        String xml = "", xml_office = "";
        String sl_mandatory;
        //_______________________________________________________________________________________________________________//
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        //_______________________________________________________________________________________________________________//
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        //_______________________________________________________________________________________________________________//
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        String major_id = "";
        //String dept_id="";
        Calendar c;
        //int deptOff_id=0,pin=0;
        //String deptOff_Name="", deptOff_SName="",addr1="", addr2="";
        //String city="", phone="", phone1="", add_phone="", fax="",  email="", HoOffice="";
        //_______________________________________________________________________________________________________________//
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
            System.out.println("assign..ended...");
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        //_______________________________________________________________________________________________________________//
        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            int id = 0;
            int txtAcc_HeadCode = 0, txtApp_offid = 0, txtApp_wingId = 0;
            String txtAcc_HeadDesc = "", txtMajor_id = "";
            int txtMinor_id = 0, txtProg_id = 0, txtProg_sub_id = 0;
            Date txtCrea_date = null, txtlast_date = null, txtRef_date = null;
            String txtBal_type = "", txtRef_no = "", txtApp_for_workid =
                "", txtRemarks = "";
            String txtUse_status = "Y", txtTB_mandatory = "", txtaccess =
                "", txtsub_ledger_YN = "", txtNature = "";
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
                System.out.println("exception...1");
            }
            System.out.println(txtAcc_HeadCode);
            txtAcc_HeadDesc = request.getParameter("txtAcc_HeadDesc");
            System.out.println(txtAcc_HeadDesc);
            txtMajor_id = request.getParameter("txtMajor_id");
            System.out.println(txtMajor_id);
            try {
                txtMinor_id =
                        Integer.parseInt(request.getParameter("txtMinor_id"));
            } catch (Exception e) {
                System.out.println("exception...2");
            }
            System.out.println(txtMinor_id);
            try {
                txtProg_id =
                        Integer.parseInt(request.getParameter("txtProg_id"));
            } catch (Exception e) {
                System.out.println("exception...3");
            }
            System.out.println(txtProg_id);
            try {
                txtProg_sub_id =
                        Integer.parseInt(request.getParameter("txtProg_sub_id"));
            } catch (Exception e) {
                System.out.println("exception...4");
            }
            // String Crea_date=request.getParameter("txtCrea_date");
            System.out.println(txtProg_sub_id);
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("creation" + txtCrea_date);
            txtBal_type = request.getParameter("txtBal_type");
            System.out.println(txtBal_type);
            txtNature = request.getParameter("txtNature");
            System.out.println("NATURE" + txtNature);
            //txtUse_status=request.getParameter("txtUse_status");
            System.out.println("UUus" + txtUse_status);
            txtTB_mandatory = request.getParameter("txtTB_mandatory");
            String access = request.getParameter("txtaccess");
            txtaccess = access;
            System.out.println("ACC" + access);
            txtsub_ledger_YN = request.getParameter("txtsub_ledger_YN");
            System.out.println("SL" + txtsub_ledger_YN);
            txtRemarks = request.getParameter("txtRemarks");
            if (txtUse_status.equalsIgnoreCase("N")) {
                System.out.println("inside usage");
                txtRef_no = request.getParameter("txtRef_no");
                String last_date = request.getParameter("txtlast_date");
                String Ref_date = request.getParameter("txtRef_date");
                System.out.println("date" + last_date + "  " + Ref_date);
                if (!last_date.equals("")) {
                    sd = request.getParameter("txtlast_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtlast_date = new Date(d.getTime());
                }
                if (!Ref_date.equals("")) {
                    sd = request.getParameter("txtRef_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtRef_date = new Date(d.getTime());
                }
                System.out.println("us" + txtRef_no + " " + txtlast_date);
                System.out.println(txtRef_date);
            }
            System.out.println("after usage");
            System.out.println(txtaccess);
            if (txtaccess.equalsIgnoreCase("Y")) {
                try {
                    txtApp_for_workid =
                            request.getParameter("txtApp_for_workid");
                } catch (Exception e) {
                    System.out.println("exception...5");
                }
                try {
                    txtApp_offid =
                            Integer.parseInt(request.getParameter("txtApp_offid"));
                } catch (Exception e) {
                    System.out.println("exception...6");
                }
                try {
                    txtApp_wingId =
                            Integer.parseInt(request.getParameter("txtApp_wingId"));
                } catch (Exception e) {
                    System.out.println("exception...7");
                }
                System.out.println("acc" + txtApp_for_workid);
                System.out.println(txtApp_offid);
                System.out.println(txtApp_wingId);
            }
            sl_mandatory = request.getParameter("txtsub_ledger_man_YN");
            System.out.println("check null");
            System.out.println("HERE");
            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                cs =
//  con.prepareCall("{call FAS_ACCOUNT_HEAD_DIR_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                cs.setInt(1, txtAcc_HeadCode);
//                cs.setString(2, txtAcc_HeadDesc);
//                cs.setString(3, txtMajor_id);
//                cs.setInt(4, txtMinor_id);
//                cs.setInt(5, txtProg_id);
//                cs.setInt(6, txtProg_sub_id);
//                cs.setDate(7, txtCrea_date);
//                cs.setString(8, txtBal_type);
//                cs.setString(9, txtNature);
//
//                cs.setString(10, txtUse_status);
//                cs.setDate(11, txtlast_date);
//                cs.setString(12, txtRef_no);
//                cs.setDate(13, txtRef_date);
//                cs.setString(14, txtTB_mandatory);
//                cs.setString(15, txtaccess);
//                cs.setString(16, txtApp_for_workid);
//                cs.setInt(17, txtApp_offid);
//                cs.setInt(18, txtApp_wingId);
//                cs.setString(19, txtsub_ledger_YN);
//                cs.setString(20, txtRemarks);
//                cs.setString(22, "update");
//
//                // cs.registerOutParameter(2,java.sql.Types.NUMERIC);
//                cs.registerOutParameter(21, java.sql.Types.NUMERIC);
//                cs.setString(23, update_user);
//                cs.setTimestamp(24, ts);
//                cs.setString(25, sl_mandatory);
//                cs.execute();
//
//                int errcode = cs.getInt(21);
  con.prepareCall("call FAS_ACCOUNT_HEAD_DIR_PROC(?::NUMERIC,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?,?,?,?,?,?,?,?::NUMERIC,?::NUMERIC,?,?,?::NUMERIC,?,?,?,?)");
                cs.setInt(1, txtAcc_HeadCode);
                cs.setString(2, txtAcc_HeadDesc);
                cs.setString(3, txtMajor_id);
                cs.setInt(4, txtMinor_id);
                cs.setInt(5, txtProg_id);
                cs.setInt(6, txtProg_sub_id);
                cs.setDate(7, txtCrea_date);
                cs.setString(8, txtBal_type);
                cs.setString(9, txtNature);

                cs.setString(10, txtUse_status);
                cs.setDate(11, txtlast_date);
                cs.setString(12, txtRef_no);
                cs.setDate(13, txtRef_date);
                cs.setString(14, txtTB_mandatory);
                cs.setString(15, txtaccess);
                cs.setString(16, txtApp_for_workid);
                cs.setInt(17, txtApp_offid);
                cs.setInt(18, txtApp_wingId);
                cs.setString(19, txtsub_ledger_YN);
                cs.setString(20, txtRemarks);
                cs.setString(22, "update");

                // cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                cs.registerOutParameter(21, java.sql.Types.NUMERIC);
                cs.setNull(21,java.sql.Types.NUMERIC);
                cs.setString(23, update_user);
                cs.setTimestamp(24, ts);
                cs.setString(25, sl_mandatory);
                cs.execute();

                //int errcode = cs.getInt(21);
                int errcode = cs.getBigDecimal(21).intValue();                		
                // id=cs.getInt(2);
                //System.out.println(id);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    sendMessage(response,
                                "The Account Head Code Updation Failed ",
                                "ok");
                    xml = xml + "<flag>failure</flag>";
                } else {
                    xml = xml + "<num>" + id + "</num><flag>success</flag>";
                    if (txtsub_ledger_YN.equalsIgnoreCase("Y")) {
                        String SL_code[] =
                            request.getParameterValues("HSL_code");
                        String SL_Desc[] =
                            request.getParameterValues("HSL_type");
                        System.out.println("sub ledge");
                        String s =
                            "delete from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=?";
                        ps = con.prepareStatement(s);
                        ps.setInt(1, txtAcc_HeadCode);
                        ps.executeUpdate();
                        String sql =
                            "insert into FAS_APPLICABLE_SL_TYPE(ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?)";

                        ps = con.prepareStatement(sql);
                        int SL_code_value = 0;
                        for (int k = 0; k < SL_code.length; k++) {
                            try {
                                SL_code_value = Integer.parseInt(SL_code[k]);
                            } catch (Exception e) {
                                System.out.println("exception...8");
                            }
                            System.out.println(SL_code[k]);
                            ps.setInt(1, txtAcc_HeadCode);
                            ps.setInt(2, SL_code_value);
                            ps.setString(3, update_user);
                            ps.setTimestamp(4, ts);
                            ps.executeUpdate();
                        }
                        ps.close();
                    } else if (txtsub_ledger_YN.equalsIgnoreCase("N")) {
                        String s =
                            "delete from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=?";
                        ps = con.prepareStatement(s);
                        ps.setInt(1, txtAcc_HeadCode);
                        ps.executeUpdate();
                    }
                    con.commit();
                    sendMessage(response,
                                " The Account Head Code  " + txtAcc_HeadCode +
                                "  has been Modified successfully.", "ok");
                }
            } catch (Exception e) {
                System.out.println("insert exception  :" + e);
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("exception...10");
                }
                sendMessage(response, "Exception in Updation due to." + e,
                            "ok");
                xml = xml + "<flag>failure</flag>";
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("exception...11");
                }
            }
            xml = xml + "</response>";
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        else if (strCommand.equalsIgnoreCase("checkCode")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>checkCode</command>";
            int txtAcc_HeadCode = 0;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }
            try {
                ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE, ACCOUNT_HEAD_DESC, MAJOR_HEAD_CODE ," +
                       "MINOR_HEAD_CODE ,SUB_HEAD1_CODE ,SUB_HEAD2_CODE ,DATE_OF_CREATION ,BALANCE_TYPE ,NATURE_TYPE" +
                       ",USAGE_STATUS ,LAST_USED_DATE ,FILE_REF_NO ,FILE_REF_DATE ,TB_MANDATORY ,ACCESS_RESTRICTED ," +
                       "WORK_NATURE_ID ,ACCESSIBLE_BY_OFFICE_CODE ,ACCESSIBLE_OFFICE_WING_SINO ,SUB_LEDGER_TYPE_APPLICABLE " +
                       ",REMARKS , SL_MANDATORY  from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, txtAcc_HeadCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("inside if cond");
                    xml =
 xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
   rs.getString("ACCOUNT_HEAD_DESC") + "</hdesc><mjHC>" +
   rs.getString("MAJOR_HEAD_CODE") + "</mjHC><miHC>" +
   rs.getInt("MINOR_HEAD_CODE") + "</miHC><SG1>" +
   rs.getInt("SUB_HEAD1_CODE") + "</SG1><SG2>" + rs.getInt("SUB_HEAD2_CODE") +
   "</SG2><DOC>" + rs.getDate("DATE_OF_CREATION") + "</DOC><BalType>" +
   rs.getString("BALANCE_TYPE") + "</BalType><Nature>" +
   rs.getString("NATURE_TYPE") + "</Nature><inUse>" +
   rs.getString("USAGE_STATUS") + "</inUse><LastUse>" +
   rs.getDate("LAST_USED_DATE") + "</LastUse><FRN>" +
   rs.getString("FILE_REF_NO") + "</FRN><FRD>" + rs.getDate("FILE_REF_DATE") +
   "</FRD><TB>" + rs.getString("TB_MANDATORY") + "</TB><AccRes>" +
   rs.getString("ACCESS_RESTRICTED") + "</AccRes><WNature_id>" +
   rs.getString("WORK_NATURE_ID") + "</WNature_id><oid>" +
   rs.getInt("ACCESSIBLE_BY_OFFICE_CODE") + "</oid><wingid>" +
   rs.getInt("ACCESSIBLE_OFFICE_WING_SINO") + "</wingid><SL_Man_YN>" +
   rs.getString("SL_MANDATORY") + "</SL_Man_YN><SL_YN>" +
   rs.getString("SUB_LEDGER_TYPE_APPLICABLE") + "</SL_YN><rmk>" +
   rs.getString("REMARKS") + "</rmk>";
                    if (rs.getString("WORK_NATURE_ID") != null) {
                        System.out.println("prim nature");
                        ps2 =
 con.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?");
                        ps2.setString(1, rs.getString("WORK_NATURE_ID"));
                        rs2 = ps2.executeQuery();
                        if (rs2.next())
                            xml =
 xml + "<WNature_Desc>" + rs2.getString("WORK_NATURE_DESC") +
   "</WNature_Desc>";
                    } else if (rs.getString("WORK_NATURE_ID") == null) {
                        System.out.println("here");
                        xml =
 xml + "<WNature_Desc>" + null + "</WNature_Desc>";
                    }

                    if (rs.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) {
                        ps =
  con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=?");
                        ps.setInt(1, txtAcc_HeadCode);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            xml =
 xml + "<SLCODE>" + rs.getInt("SUB_LEDGER_TYPE_CODE") + "</SLCODE>";
                            if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                                System.out.println("take SL DESC");
                                ps2 =
 con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                                ps2.setInt(1,
                                           rs.getInt("SUB_LEDGER_TYPE_CODE"));
                                rs2 = ps2.executeQuery();
                                if (rs2.next())
                                    xml =
 xml + "<SLDESC>" + rs2.getString("SUB_LEDGER_TYPE_DESC") + "</SLDESC>";
                            }
                        }
                    }
                } else {
                    System.out.println("NO value for check code");
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
            System.out.println("Load" + xml);
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        else if (strCommand.equalsIgnoreCase("office")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>office</command>";
            try {
                int oid = 0;
                //String oname="";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println("exception...12");
                }
                ps2 =
 con.prepareStatement("select OFFICE_NAME,WINGS_APPLICABLE,PRIMARY_WORK_ID from COM_MST_OFFICES where OFFICE_ID=?");
                ps2.setInt(1, oid);
                rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    xml =
 xml + "<flag>success</flag><oid>" + oid + "</oid><oname>" +
   rs2.getString("OFFICE_NAME") + "</oname><wing>" +
   rs2.getString("WINGS_APPLICABLE") + "</wing><WNature_id>" +
   rs2.getString("PRIMARY_WORK_ID") + "</WNature_id>";
                    System.out.println(rs2.getString("PRIMARY_WORK_ID"));
                    if (rs2.getString("PRIMARY_WORK_ID") != null) {
                        System.out.println("prim nature");
                        ps =
  con.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?");
                        ps.setString(1, rs2.getString("PRIMARY_WORK_ID"));
                        rs = ps.executeQuery();
                        if (rs.next())
                            xml =
 xml + "<WNature_Desc>" + rs.getString("WORK_NATURE_DESC") + "</WNature_Desc>";
                    } else if (rs2.getString("PRIMARY_WORK_ID") == null) {
                        System.out.println("here");
                        xml =
 xml + "<WNature_Desc>" + null + "</WNature_Desc>";
                    }
                    //  System.out.println(rs2.getString("WINGS_APPLICABLE"));
                    if (rs2.getString("WINGS_APPLICABLE") != null) {
                        if (rs2.getString("WINGS_APPLICABLE").equalsIgnoreCase("Y")) {
                            System.out.println("wing");
                            ps =
  con.prepareStatement("select OFFICE_WING_SINO,WING_NAME from COM_OFFICE_WINGS where OFFICE_ID=?");
                            ps.setInt(1, oid);
                            rs = ps.executeQuery();
                            while (rs.next())
                                xml =
 xml + "<wid>" + rs.getInt("OFFICE_WING_SINO") + "</wid><wname>" +
   rs.getString("WING_NAME") + "</wname>";
                        }
                    }
                } else
                    xml = xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                ps2.close();
                rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        else if (strCommand.equalsIgnoreCase("office_Function")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml_office = "<response>";
            System.out.println("from office_Function");
            try {
                int oid = 0;
                //String oname="";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println("exception...13");
                }
                ps2 =
 con.prepareStatement("select OFFICE_NAME,WINGS_APPLICABLE,PRIMARY_WORK_ID from COM_MST_OFFICES where OFFICE_ID=?");
                ps2.setInt(1, oid);
                rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    xml_office =
                            xml_office + "<flag>success</flag><oid>" + oid +
                            "</oid><oname>" + rs2.getString("OFFICE_NAME") +
                            "</oname><wing>" +
                            rs2.getString("WINGS_APPLICABLE") +
                            "</wing><WNature_id>" +
                            rs2.getString("PRIMARY_WORK_ID") + "</WNature_id>";
                    System.out.println(rs2.getString("PRIMARY_WORK_ID"));
                    if (rs2.getString("PRIMARY_WORK_ID") != null) {
                        System.out.println("prim nature");
                        ps =
  con.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?");
                        ps.setString(1, rs2.getString("PRIMARY_WORK_ID"));
                        rs = ps.executeQuery();
                        if (rs.next())
                            xml_office =
                                    xml_office + "<WNature_Desc>" + rs.getString("WORK_NATURE_DESC") +
                                    "</WNature_Desc>";
                    } else if (rs2.getString("PRIMARY_WORK_ID") == null) {
                        System.out.println("here");
                        xml_office =
                                xml_office + "<WNature_Desc>" + null + "</WNature_Desc>";
                    }
                    //  System.out.println(rs2.getString("WINGS_APPLICABLE"));
                    if (rs2.getString("WINGS_APPLICABLE") != null) {
                        if (rs2.getString("WINGS_APPLICABLE").equalsIgnoreCase("Y")) {
                            System.out.println("wing");
                            ps =
  con.prepareStatement("select OFFICE_WING_SINO,WING_NAME from COM_OFFICE_WINGS where OFFICE_ID=?");
                            ps.setInt(1, oid);
                            rs = ps.executeQuery();
                            while (rs.next())
                                xml_office =
                                        xml_office + "<wid>" + rs.getInt("OFFICE_WING_SINO") +
                                        "</wid><wname>" +
                                        rs.getString("WING_NAME") + "</wname>";
                        }
                    }
                } else
                    xml_office =
                            xml_office + "<flag>failure</flag><oid>" + oid +
                            "</oid>";
                ps2.close();
                rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml_office = xml_office + "<flag>failure</flag>";
            }
            xml_office = xml_office + "</response>";
            out.println(xml_office);
            System.out.println(xml_office + "from office _function");
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        else if (strCommand.equalsIgnoreCase("loadMinor")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            System.out.println("inside minorload....");
            major_id = request.getParameter("txtMajor_id");
            xml = "<response><command>loadMinor</command>";
            try {
                System.out.println("inside try....");
                ps =
  con.prepareStatement("select MAJOR_HEAD_CODE,MINOR_HEAD_CODE,MINOR_HEAD_DESC from  COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=?");
                ps.setString(1, major_id);
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    xml =
 xml + "<Maj_id>" + rs.getString("MAJOR_HEAD_CODE") + "</Maj_id>";
                    xml =
 xml + "<Min_id>" + rs.getInt("MINOR_HEAD_CODE") + "</Min_id>";
                    xml =
 xml + "<Min_desc>" + rs.getString("MINOR_HEAD_DESC") + "</Min_desc>";
                }
            } catch (Exception e) {
                System.out.println("catch..in..loadMinor::" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

            out.println(xml);
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        else if (strCommand.equalsIgnoreCase("subgroup")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            System.out.println("inside subgroup....");
            int txtProg_id = 0, txtProg_sub_id = 0;
            try {
                txtProg_id =
                        Integer.parseInt(request.getParameter("txtProg_id"));
            } catch (Exception e) {
                System.out.println("exception...14");
            }
            try {
                txtProg_sub_id =
                        Integer.parseInt(request.getParameter("txtProg_sub_id"));
            } catch (Exception e) {
                System.out.println("exception...15");
            }
            xml = "<response><command>subgroup</command>";
            try {
                System.out.println("inside try....");
                xml =
 xml + "<flag>success</flag><prog_subid>" + txtProg_sub_id + "</prog_subid>";
                if (txtProg_id != 0) {
                    ps =
  con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE!=? order by SUB_HEAD_DESC");
                    ps.setInt(1, txtProg_id);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<sub_id>" + rs.getInt("SUB_HEAD_CODE") + "</sub_id>";
                        xml =
 xml + "<sub_desc>" + rs.getString("SUB_HEAD_DESC") + "</sub_desc>";
                    }
                }
            } catch (Exception e) {
                System.out.println("catch..in..sub group::" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }
        System.out.println(xml);
        //out.println(xml);

    }


    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}

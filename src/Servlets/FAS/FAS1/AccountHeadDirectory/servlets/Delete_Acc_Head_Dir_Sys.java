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

public class Delete_Acc_Head_Dir_Sys extends HttpServlet {

    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null;
        //CallableStatement cs=null;
        PreparedStatement ps = null, ps2 = null, ps3 = null;
        String xml = "";
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

        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        //String major_id="",prog_id="";
        //String dept_id="";
        Calendar c;
        //int deptOff_id=0,pin=0;
        //String deptOff_Name="", deptOff_SName="",addr1="", addr2="";
        //String city="", phone="", phone1="", add_phone="", fax="",  email="", HoOffice="";
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);


            System.out.println("assign..ended...");
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Delete")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            //int id=0;
            int txtAcc_HeadCode = 0;
            String txtUse_status = "N", txtRef_no = "";
            Date txtlast_date = null, txtRef_date = null;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
            }

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                txtRef_no = request.getParameter("txtRef_no");
                String last_date = request.getParameter("txtlast_date");
                String Ref_date = request.getParameter("txtRef_date");
                System.out.println("date" + last_date + "  " + Ref_date);
                if (!last_date.equals("")) {
                    String[] sd =
                        request.getParameter("txtlast_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    txtlast_date = new Date(d.getTime());
                }
                if (!Ref_date.equals("")) {
                    String[] sd =
                        request.getParameter("txtRef_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    txtRef_date = new Date(d.getTime());
                }
                System.out.println("us" + txtRef_no + " " + txtlast_date);
                System.out.println(txtRef_date);
                String s =
                    "update COM_MST_ACCOUNT_HEADS set LAST_USED_DATE=?,FILE_REF_NO=?,FILE_REF_DATE=?,USAGE_STATUS='N',UPDATED_DATE=?,UPDATED_BY_USER_ID=? where ACCOUNT_HEAD_CODE=?";
                ps = con.prepareStatement(s);
                ps.setDate(1, txtlast_date);
                ps.setString(2, txtRef_no);
                ps.setDate(3, txtRef_date);
                ps.setTimestamp(4, ts);
                ps.setString(5, update_user);
                ps.setInt(6, txtAcc_HeadCode);
                ps.executeUpdate();
                con.commit();
                sendMessage(response,
                            " The Account Head Code  " + txtAcc_HeadCode +
                            "  has been modified successfully.", "ok");
            } catch (Exception e) {
                System.out.println("Deletion exception  :" + e);
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                }
                sendMessage(response, "Exception in Deletion due to." + e,
                            "ok");
                xml = xml + "<flag>failure</flag>";
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                }
            }
            xml = xml + "</response>";
        }       
        /*   added to revert the usage status              */
        if (strCommand.equalsIgnoreCase("Revert")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            //int id=0;
            int txtAcc_HeadCode = 0;
            String txtUse_status = "N", txtRef_no = "";
            Date txtlast_date = null, txtRef_date = null;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
            }

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                txtRef_no = request.getParameter("txtRef_no");
                String last_date = request.getParameter("txtlast_date");
                String Ref_date = request.getParameter("txtRef_date");
                System.out.println("date" + last_date + "  " + Ref_date);
                if (!last_date.equals("")) {
                    String[] sd =
                        request.getParameter("txtlast_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    txtlast_date = new Date(d.getTime());
                }
                if (!Ref_date.equals("")) {
                    String[] sd =
                        request.getParameter("txtRef_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    txtRef_date = new Date(d.getTime());
                }
                System.out.println("us" + txtRef_no + " " + txtlast_date);
                System.out.println(txtRef_date);
                String s =
                    "update COM_MST_ACCOUNT_HEADS set LAST_USED_DATE=?,FILE_REF_NO=?,FILE_REF_DATE=?,USAGE_STATUS='Y',UPDATED_DATE=?,UPDATED_BY_USER_ID=? where ACCOUNT_HEAD_CODE=?";
                ps = con.prepareStatement(s);
                ps.setDate(1, txtlast_date);
                ps.setString(2, txtRef_no);
                ps.setDate(3, txtRef_date);
                ps.setTimestamp(4, ts);
                ps.setString(5, update_user);
                ps.setInt(6, txtAcc_HeadCode);
                ps.executeUpdate();
                con.commit();
                sendMessage(response,
                            " The Account Head Code  " + txtAcc_HeadCode +
                            "  has been Modified successfully.", "ok");
            } catch (Exception e) {
                System.out.println("Deletion exception  :" + e);
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                }
                sendMessage(response, "Exception in Deletion due to." + e,
                            "ok");
                xml = xml + "<flag>failure</flag>";
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                }
            }
            xml = xml + "</response>";
        } 
        
        else if (strCommand.equalsIgnoreCase("checkCode")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>checkCode</command>";
            int txtAcc_HeadCode = 0;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
            }
            try {
                ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE, ACCOUNT_HEAD_DESC, MAJOR_HEAD_CODE ," +
                       "MINOR_HEAD_CODE ,SUB_HEAD1_CODE ,SUB_HEAD2_CODE ,DATE_OF_CREATION ,BALANCE_TYPE ,NATURE_TYPE" +
                       ",USAGE_STATUS ,LAST_USED_DATE ,FILE_REF_NO ,FILE_REF_DATE ,TB_MANDATORY ,ACCESS_RESTRICTED ," +
                       "WORK_NATURE_ID ,ACCESSIBLE_BY_OFFICE_CODE ,ACCESSIBLE_OFFICE_WING_SINO ,SUB_LEDGER_TYPE_APPLICABLE " +
                       ",REMARKS  from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, txtAcc_HeadCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid>";
                    xml =
 xml + "<hdesc>" + rs.getString("ACCOUNT_HEAD_DESC") + "</hdesc>";
                    ps3 =
 con.prepareStatement("select MAJOR_HEAD_DESC from  COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
                    ps3.setString(1, rs.getString("MAJOR_HEAD_CODE"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
 xml + "<mjHC>" + rs3.getString("MAJOR_HEAD_DESC") + "</mjHC>";
                    ps3.close();
                    rs3.close();

                    ps3 =
 con.prepareStatement("select MINOR_HEAD_CODE, MINOR_HEAD_DESC  from  COM_MST_MINOR_HEADS where MINOR_HEAD_CODE=?");
                    ps3.setInt(1, rs.getInt("MINOR_HEAD_CODE"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
 xml + "<miHC>" + rs3.getString("MINOR_HEAD_DESC") + "</miHC>";
                    ps3.close();
                    rs3.close();
                    System.out.println(rs.getInt("SUB_HEAD1_CODE") +
                                       "SUB_HEAD1_CODE");
                    if (rs.getInt("SUB_HEAD1_CODE") != 0) {
                        ps3 =
 con.prepareStatement("select SUB_HEAD_DESC from  COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                        ps3.setInt(1, rs.getInt("SUB_HEAD1_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
 xml + "<SG1>" + rs3.getString("SUB_HEAD_DESC") + "</SG1>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
 xml + "<SG1>" + rs.getInt("SUB_HEAD1_CODE") + "</SG1>";

                    if (rs.getInt("SUB_HEAD2_CODE") != 0) {
                        ps3 =
 con.prepareStatement("select SUB_HEAD_DESC from  COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                        ps3.setInt(1, rs.getInt("SUB_HEAD2_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
 xml + "<SG2>" + rs3.getString("SUB_HEAD_DESC") + "</SG2>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
 xml + "<SG2>" + rs.getInt("SUB_HEAD2_CODE") + "</SG2>";


                    xml =
 xml + "<DOC>" + rs.getDate("DATE_OF_CREATION") + "</DOC>" + "<BalType>" +
   rs.getString("BALANCE_TYPE") + "</BalType><Nature>" +
   rs.getString("NATURE_TYPE") + "</Nature><inUse>" +
   rs.getString("USAGE_STATUS") + "</inUse><LastUse>" +
   rs.getDate("LAST_USED_DATE") + "</LastUse><FRN>" +
   rs.getString("FILE_REF_NO") + "</FRN><FRD>" + rs.getDate("FILE_REF_DATE") +
   "</FRD><TB>" + rs.getString("TB_MANDATORY") + "</TB><AccRes>" +
   rs.getString("ACCESS_RESTRICTED") + "</AccRes>";
                    System.out.println(rs.getString("WORK_NATURE_ID"));
                    if (rs.getString("WORK_NATURE_ID") != null) {
                        System.out.println("ghg" +
                                           rs.getString("WORK_NATURE_ID"));
                        ps3 =
 con.prepareStatement("select WORK_NATURE_DESC from  COM_MST_WORK_NATURE where WORK_NATURE_ID=?");
                        ps3.setString(1, rs.getString("WORK_NATURE_ID"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
 xml + "<WN_id>" + rs3.getString("WORK_NATURE_DESC") + "</WN_id>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
 xml + "<WN_id>" + rs.getString("WORK_NATURE_ID") + "</WN_id>";
                    if (rs.getInt("ACCESSIBLE_BY_OFFICE_CODE") != 0) {
                        ps3 =
 con.prepareStatement("select OFFICE_ID ,OFFICE_NAME ,WINGS_APPLICABLE,PRIMARY_WORK_ID from  COM_MST_OFFICES where OFFICE_ID=?");
                        ps3.setInt(1, rs.getInt("ACCESSIBLE_BY_OFFICE_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
 xml + "<oid>" + rs3.getString("OFFICE_NAME") + "</oid>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
 xml + "<oid>" + rs.getInt("ACCESSIBLE_BY_OFFICE_CODE") + "</oid>";

                    if (rs.getInt("ACCESSIBLE_OFFICE_WING_SINO") != 0) {
                        ps3 =
 con.prepareStatement("select OFFICE_WING_SINO,WING_NAME from  COM_OFFICE_WINGS where OFFICE_WING_SINO=? and OFFICE_ID=?");
                        ps3.setInt(1,
                                   rs.getInt("ACCESSIBLE_OFFICE_WING_SINO"));
                        ps3.setInt(2, rs.getInt("ACCESSIBLE_BY_OFFICE_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
 xml + "<wingid>" + rs3.getString("WING_NAME") + "</wingid>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
 xml + "<wingid>" + rs.getInt("ACCESSIBLE_OFFICE_WING_SINO") + "</wingid>";

                    xml =
 xml + "<SL_YN>" + rs.getString("SUB_LEDGER_TYPE_APPLICABLE") +
   "</SL_YN><rmk>" + rs.getString("REMARKS") + "</rmk>";

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
                    System.out.println("no record found in table");
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }
        else if (strCommand.equalsIgnoreCase("checkCode1")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>checkCode1</command>";
            int txtAcc_HeadCode = 0;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
            }
            try {
                ps =
        con.prepareStatement("select ACCOUNT_HEAD_CODE, ACCOUNT_HEAD_DESC, MAJOR_HEAD_CODE ," +
                       "MINOR_HEAD_CODE ,SUB_HEAD1_CODE ,SUB_HEAD2_CODE ,DATE_OF_CREATION ,BALANCE_TYPE ,NATURE_TYPE" +
                       ",USAGE_STATUS ,LAST_USED_DATE ,FILE_REF_NO ,FILE_REF_DATE ,TB_MANDATORY ,ACCESS_RESTRICTED ," +
                       "WORK_NATURE_ID ,ACCESSIBLE_BY_OFFICE_CODE ,ACCESSIBLE_OFFICE_WING_SINO ,SUB_LEDGER_TYPE_APPLICABLE " +
                       ",REMARKS  from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='N' and ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, txtAcc_HeadCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
        xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid>";
                    xml =
        xml + "<hdesc>" + rs.getString("ACCOUNT_HEAD_DESC") + "</hdesc>";
                    ps3 =
        con.prepareStatement("select MAJOR_HEAD_DESC from  COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
                    ps3.setString(1, rs.getString("MAJOR_HEAD_CODE"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
        xml + "<mjHC>" + rs3.getString("MAJOR_HEAD_DESC") + "</mjHC>";
                    ps3.close();
                    rs3.close();

                    ps3 =
        con.prepareStatement("select MINOR_HEAD_CODE, MINOR_HEAD_DESC  from  COM_MST_MINOR_HEADS where MINOR_HEAD_CODE=?");
                    ps3.setInt(1, rs.getInt("MINOR_HEAD_CODE"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
        xml + "<miHC>" + rs3.getString("MINOR_HEAD_DESC") + "</miHC>";
                    ps3.close();
                    rs3.close();
                    System.out.println(rs.getInt("SUB_HEAD1_CODE") +
                                       "SUB_HEAD1_CODE");
                    if (rs.getInt("SUB_HEAD1_CODE") != 0) {
                        ps3 =
        con.prepareStatement("select SUB_HEAD_DESC from  COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                        ps3.setInt(1, rs.getInt("SUB_HEAD1_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
        xml + "<SG1>" + rs3.getString("SUB_HEAD_DESC") + "</SG1>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
        xml + "<SG1>" + rs.getInt("SUB_HEAD1_CODE") + "</SG1>";

                    if (rs.getInt("SUB_HEAD2_CODE") != 0) {
                        ps3 =
        con.prepareStatement("select SUB_HEAD_DESC from  COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                        ps3.setInt(1, rs.getInt("SUB_HEAD2_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
        xml + "<SG2>" + rs3.getString("SUB_HEAD_DESC") + "</SG2>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
        xml + "<SG2>" + rs.getInt("SUB_HEAD2_CODE") + "</SG2>";


                    xml =
        xml + "<DOC>" + rs.getDate("DATE_OF_CREATION") + "</DOC>" + "<BalType>" +
        rs.getString("BALANCE_TYPE") + "</BalType><Nature>" +
        rs.getString("NATURE_TYPE") + "</Nature><inUse>" +
        rs.getString("USAGE_STATUS") + "</inUse><LastUse>" +
        rs.getDate("LAST_USED_DATE") + "</LastUse><FRN>" +
        rs.getString("FILE_REF_NO") + "</FRN><FRD>" + rs.getDate("FILE_REF_DATE") +
        "</FRD><TB>" + rs.getString("TB_MANDATORY") + "</TB><AccRes>" +
        rs.getString("ACCESS_RESTRICTED") + "</AccRes>";
                    System.out.println(rs.getString("WORK_NATURE_ID"));
                    if (rs.getString("WORK_NATURE_ID") != null) {
                        System.out.println("ghg" +
                                           rs.getString("WORK_NATURE_ID"));
                        ps3 =
        con.prepareStatement("select WORK_NATURE_DESC from  COM_MST_WORK_NATURE where WORK_NATURE_ID=?");
                        ps3.setString(1, rs.getString("WORK_NATURE_ID"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
        xml + "<WN_id>" + rs3.getString("WORK_NATURE_DESC") + "</WN_id>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
        xml + "<WN_id>" + rs.getString("WORK_NATURE_ID") + "</WN_id>";
                    if (rs.getInt("ACCESSIBLE_BY_OFFICE_CODE") != 0) {
                        ps3 =
        con.prepareStatement("select OFFICE_ID ,OFFICE_NAME ,WINGS_APPLICABLE,PRIMARY_WORK_ID from  COM_MST_OFFICES where OFFICE_ID=?");
                        ps3.setInt(1, rs.getInt("ACCESSIBLE_BY_OFFICE_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
        xml + "<oid>" + rs3.getString("OFFICE_NAME") + "</oid>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
        xml + "<oid>" + rs.getInt("ACCESSIBLE_BY_OFFICE_CODE") + "</oid>";

                    if (rs.getInt("ACCESSIBLE_OFFICE_WING_SINO") != 0) {
                        ps3 =
        con.prepareStatement("select OFFICE_WING_SINO,WING_NAME from  COM_OFFICE_WINGS where OFFICE_WING_SINO=? and OFFICE_ID=?");
                        ps3.setInt(1,
                                   rs.getInt("ACCESSIBLE_OFFICE_WING_SINO"));
                        ps3.setInt(2, rs.getInt("ACCESSIBLE_BY_OFFICE_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
        xml + "<wingid>" + rs3.getString("WING_NAME") + "</wingid>";
                        ps3.close();
                        rs3.close();
                    } else
                        xml =
        xml + "<wingid>" + rs.getInt("ACCESSIBLE_OFFICE_WING_SINO") + "</wingid>";

                    xml =
        xml + "<SL_YN>" + rs.getString("SUB_LEDGER_TYPE_APPLICABLE") +
        "</SL_YN><rmk>" + rs.getString("REMARKS") + "</rmk>";

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
                    System.out.println("no record found in table");
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
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

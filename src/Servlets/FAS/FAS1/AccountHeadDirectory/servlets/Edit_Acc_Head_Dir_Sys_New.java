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

public class Edit_Acc_Head_Dir_Sys_New extends HttpServlet 
{
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException 
{
        System.out.println("welcome to new modify ************");
        Connection con = null;
        ResultSet rs = null, rs2 = null,rs3=null,rs6=null,rs9=null,resultRs3=null;
        CallableStatement cs = null;
        PreparedStatement ps = null, ps2 = null,ps3=null,ps6=null,ps9=null,ps_select=null;
        String xml = "", xml_office = "";
        String sl_mandatory="";int off_wing=0;
        int acc_unitid=0;
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
        if (strCommand.equalsIgnoreCase("Add")) 
        {
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
                "", txtsub_ledger_YN = "", txtNature = "", txtstatus = "Y";
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
                System.out.println("exception...1");
            }
            //System.out.println(txtAcc_HeadCode);
            txtAcc_HeadDesc = request.getParameter("txtAcc_HeadDesc");
            //System.out.println(txtAcc_HeadDesc);
            txtMajor_id = request.getParameter("txtMajor_id");
            //System.out.println(txtMajor_id);
            try {
                txtMinor_id =
                        Integer.parseInt(request.getParameter("txtMinor_id"));
            } catch (Exception e) {
                System.out.println("exception...2");
            }
//            System.out.println(txtMinor_id);
            try {
                txtProg_id =
                        Integer.parseInt(request.getParameter("txtProg_id"));
            } catch (Exception e) {
                System.out.println("exception...3");
            }
//            System.out.println(txtProg_id);
            try {
                txtProg_sub_id =
                        Integer.parseInt(request.getParameter("txtProg_sub_id"));
            } catch (Exception e) {
                System.out.println("exception...4");
            }
            // String Crea_date=request.getParameter("txtCrea_date");
//            System.out.println(txtProg_sub_id);
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
//            System.out.println("creation" + txtCrea_date);
            txtBal_type = request.getParameter("txtBal_type");
//            System.out.println(txtBal_type);
            txtNature = request.getParameter("txtNature");
//            System.out.println("NATURE" + txtNature);
            txtUse_status = request.getParameter("txtUse_status");
            txtstatus = request.getParameter("txtstatus");
//            System.out.println("status" + txtstatus);
            txtTB_mandatory = request.getParameter("txtTB_mandatory");
            String access = request.getParameter("txtaccess");
            txtaccess = access;
//            System.out.println("ACC" + access);
            txtsub_ledger_YN = request.getParameter("txtsub_ledger_YN");
//            System.out.println("SL" + txtsub_ledger_YN);
            txtRemarks = request.getParameter("txtRemarks");
            if (txtUse_status.equalsIgnoreCase("N")) {
//                System.out.println("inside usage");
                txtRef_no = request.getParameter("txtRef_no");
                String last_date = request.getParameter("txtlast_date");
                String Ref_date = request.getParameter("txtRef_date");
//                System.out.println("date" + last_date + "  " + Ref_date);
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
//                System.out.println("us" + txtRef_no + " " + txtlast_date);
//                System.out.println(txtRef_date);
            }
//            System.out.println("after usage");
            System.out.println(txtaccess);
            /*if (txtaccess.equalsIgnoreCase("Y")) {
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
            }*/
            if(txtaccess.equalsIgnoreCase("Y")) 
            {
                //txtApp_for_workid=request.getParameter("txtApp_for_workid");
                //try{txtApp_offid=Integer.parseInt(request.getParameter("txtApp_offid")); }catch(Exception e){System.out.println("exception...5");}
                //try{ txtApp_wingId=Integer.parseInt(request.getParameter("txtApp_wingId"));  }catch(Exception e){System.out.println("exception...6");}
                txtApp_for_workid=null;
                txtApp_offid=0;
                txtApp_wingId=0;
//                System.out.println("acc"+txtApp_for_workid);
//                System.out.println(txtApp_offid);
//                System.out.println(txtApp_wingId);
            }
            sl_mandatory = request.getParameter("txtsub_ledger_man_YN");
//            System.out.println("check null");
//            System.out.println("HERE");
            try {
            
                String s2 = "delete from FAS_RESTRICTED_AC_HEADS where ACCOUNT_HEAD_CODE=?";
                  ps = con.prepareStatement(s2);
                  ps.setInt(1, txtAcc_HeadCode);
                  ps.executeUpdate();  
            
                con.clearWarnings();
                con.setAutoCommit(false);
            //    cs =
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
//                
//                int errcode = cs.getInt(21);
//con.prepareCall("call FAS_ACCOUNT_HEAD_DIR_PROC(?::NUMERIC,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?,?,?,?,?,?,?,?::NUMERIC,?::NUMERIC,?,?,?::NUMERIC,?,?,?,?)");
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
//                cs.setNull(21,java.sql.Types.NUMERIC);
//                cs.setString(23, update_user);
//                cs.setTimestamp(24, ts);
//                cs.setString(25, sl_mandatory);
//                cs.execute();
//
//                //int errcode = cs.getInt(21);
//                int errcode = cs.getBigDecimal(21).intValue();        
                // id=cs.getInt(2);
                //System.out.println(id);
                		
               ps=con.prepareStatement(" update COM_MST_ACCOUNT_HEADS set ACCOUNT_HEAD_DESC=?,MAJOR_HEAD_CODE=?,MINOR_HEAD_CODE=?,SUB_HEAD1_CODE=?,SUB_HEAD2_CODE=?,DATE_OF_CREATION=?,"+
                        " BALANCE_TYPE=?,NATURE_TYPE=?,USAGE_STATUS=?,LAST_USED_DATE=?,FILE_REF_NO=?,FILE_REF_DATE=?,TB_MANDATORY=?,ACCESS_RESTRICTED=?,WORK_NATURE_ID=?,ACCESSIBLE_BY_OFFICE_CODE=?,"+
                         "ACCESSIBLE_OFFICE_WING_SINO=?,SUB_LEDGER_TYPE_APPLICABLE=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?, SL_MANDATORY=? where ACCOUNT_HEAD_CODE=?");
               
             ps.setString(1, txtAcc_HeadDesc);
             ps.setString(2, txtMajor_id);
             ps.setInt(3, txtMinor_id);
             ps.setInt(4, txtProg_id);
             ps.setInt(5, txtProg_sub_id);
             ps.setDate(6, txtCrea_date);
             ps.setString(7, txtBal_type);
             ps.setString(8, txtNature);

             ps.setString(9, txtUse_status);
             ps.setDate(10, txtlast_date);
             ps.setString(11, txtRef_no);
             ps.setDate(12, txtRef_date);
             ps.setString(13, txtTB_mandatory);
             ps.setString(14, txtaccess);
             ps.setString(15, txtApp_for_workid);
             ps.setInt(16, txtApp_offid);
             ps.setInt(17, txtApp_wingId);
             ps.setString(18, txtsub_ledger_YN);
             ps.setString(19, txtRemarks);
            // ps.setString(22, "update");

             // ps.registerOutParameter(2,java.sql.Types.NUMERIC);
            // ps.registerOutParameter(21, java.sql.Types.NUMERIC);
            // ps.setNull(21,java.sql.Types.NUMERIC);
             ps.setString(20, update_user);
             ps.setTimestamp(21, ts);
             ps.setString(22, sl_mandatory);
             ps.setInt(23, txtAcc_HeadCode);
             int errcode=ps.executeUpdate();
                System.out.println("SQLCODE:::" + errcode);
                if (errcode == 0) {
                    sendMessage(response,
                                "The Account Head Code Updation Failed ",
                                "ok");
                    xml = xml + "<flag>failure</flag>";
                } else 
                {
                    xml = xml + "<num>" + id + "</num><flag>success</flag>";
                    if (txtsub_ledger_YN.equalsIgnoreCase("Y")) 
                    {
                        String SL_code[] =
                            request.getParameterValues("HSL_code");
                        String SL_Desc[] =
                            request.getParameterValues("HSL_type");
                        String SL_status[] =
                            request.getParameterValues("HSL_status");
                       System.out.println("sub ledge modified by sathya on 16/04/2015");
                    String s =
                            "delete from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=?";
                        ps = con.prepareStatement(s);
                        ps.setInt(1, txtAcc_HeadCode);
                        ps.executeUpdate(); 
                        String sql ="insert into FAS_APPLICABLE_SL_TYPE(ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS) values(?,?,?,?,?)";
                        System.out.println("sql::::"+sql);
                        ps = con.prepareStatement(sql);
                        int SL_code_value = 0;
                        String SL_status_value = "";
                       
                       System.out.println("length of SL type"+SL_code.length);
                        for (int k = 0; k < SL_code.length; k++) {
                        	 int insertCount=0;
                            try {
                                SL_code_value = Integer.parseInt(SL_code[k]);
                                System.out.println("SL_code_value*****SSSS"+SL_code_value);
                                } catch (Exception e) {
                                System.out.println("exception...8");
                            }
                            try {
                                SL_status_value = (SL_status[k]);
                            } catch (Exception e) {
                                System.out.println("exception...8");
                            }
                            
                            String sql_select ="select ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE="+txtAcc_HeadCode+" and SUB_LEDGER_TYPE_CODE="+SL_code_value;
                            System.out.println("sql_select::::"+sql_select);
                            ps_select = con.prepareStatement(sql_select);
                            resultRs3=ps_select.executeQuery();
                            while(resultRs3.next())
                            {
                            	System.out.println("while:::");
                            	insertCount++;
                          
                            }
                            if(insertCount==0)
                            {
                            	System.out.println("insert:::");
                            	//insert
                            	ps.setInt(1, txtAcc_HeadCode);
                                ps.setInt(2, SL_code_value);
                                System.out.println("SL_code_value:::"+SL_code_value);
                                ps.setString(3, update_user);
                                ps.setTimestamp(4, ts);
                                ps.setString(5, SL_status_value);
                                ps.executeUpdate();
                            }
                            
                        }
                        ps.close();
                    } 
                    else if (txtsub_ledger_YN.equalsIgnoreCase("N")) {
                        String s =
                            "delete from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=?";
                        ps = con.prepareStatement(s);
                        ps.setInt(1, txtAcc_HeadCode);
                        ps.executeUpdate();
                    }
                    System.out.println("access*********************::"+access);
                    if(access.equalsIgnoreCase("Y")) 
                    {
                    System.out.println("id&&&&&&&&&&&::"+id);
                       xml=xml+"<num1>"+id+"</num1><flag>success</flag>";
                       String Off_id[]=request.getParameterValues("HOFF_id");
                       //String Off_name[]=request.getParameterValues("HOFF_name");
                       String Wing_name[]=request.getParameterValues("HWing_name");
                       System.out.println("more than one Office units ");
                       
                   /*     String s =
                           // "delete from FAS_RESTRICTED_AC_HEADS where ACCOUNT_HEAD_CODE=?";
                           "update FAS_RESTRICTED_AC_HEADS set status='N' where ACCOUNT_HEAD_CODE=?";
                        ps = con.prepareStatement(s);
                        System.out.println("while changing the units delete the old one and enter a new one............"+s);
                        ps.setInt(1, txtAcc_HeadCode);
                        ps.executeUpdate();  */
                        String sql="insert into FAS_RESTRICTED_AC_HEADS(ACCOUNT_HEAD_CODE,ACCOUNTING_UNIT_ID,OFFICE_WING_SLNO,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)";
                           ps=con.prepareStatement(sql);
                           int off_res_value=0;int Wing_slno=0;
                            for(int k=0;k<Off_id.length;k++) 
                            {
                                try{off_res_value=Integer.parseInt(Off_id[k]); }catch(Exception e){}
                                try{Wing_slno=Integer.parseInt(Wing_name[k]); }catch(Exception e){}
                                ps.setInt(1,txtAcc_HeadCode);
                                ps.setInt(2,off_res_value);
                                ps.setInt(3,Wing_slno);
                                ps.setString(4,access);
                                ps.setString(5,update_user);
                                ps.setTimestamp(6,ts);
                                ps.executeUpdate();
                            }
                            ps.close();
                    }
                   else if (access.equalsIgnoreCase("N")) 
                    {
                    System.out.println("NOOOOOOOOOOO");
                       /* String s =
                            "delete from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=?";
                        ps = con.prepareStatement(s);
                        ps.setInt(1, txtAcc_HeadCode);
                        ps.executeUpdate(); */
                        String s ="update FAS_RESTRICTED_AC_HEADS set status='N' where ACCOUNT_HEAD_CODE=?";
                        ps = con.prepareStatement(s);
                        System.out.println("while changing the units delete the old one and enter a new one............"+s);
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
        else if (strCommand.equalsIgnoreCase("checkCode")) 
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>checkCode</command>";
            int txtAcc_HeadCode = 0;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                        System.out.println("Account head code in checkcode*********"+txtAcc_HeadCode);
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }
            try {
                ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE, ACCOUNT_HEAD_DESC, MAJOR_HEAD_CODE ," +
                       "MINOR_HEAD_CODE ,SUB_HEAD1_CODE ,SUB_HEAD2_CODE ,DATE_OF_CREATION ,BALANCE_TYPE ,NATURE_TYPE" +
                       ",USAGE_STATUS ,LAST_USED_DATE ,FILE_REF_NO ,FILE_REF_DATE ,TB_MANDATORY ,ACCESS_RESTRICTED ," +
                       "WORK_NATURE_ID ,ACCESSIBLE_BY_OFFICE_CODE ,ACCESSIBLE_OFFICE_WING_SINO ,SUB_LEDGER_TYPE_APPLICABLE " +
                       ",REMARKS , SL_MANDATORY  from COM_MST_ACCOUNT_HEADS where  ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, txtAcc_HeadCode);
                rs = ps.executeQuery();
                if (rs.next()) 
                {
                    
                    xml =xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
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
                       //System.out.println("data from first query***"+xml);
               //------------------------------------------------------------------------------------
                    if ((rs.getString("ACCESS_RESTRICTED").equalsIgnoreCase("Y")) && (rs.getInt("ACCESSIBLE_BY_OFFICE_CODE")==0) && (rs.getInt("ACCESSIBLE_OFFICE_WING_SINO")==0))
                    {
                        //data taken from the fas_restricted_ac_heads for office more units 
                         System.out.println("here for Y  0 0 ");
                         ps9 =con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNTING_UNIT_ID,OFFICE_WING_SLNO,STATUS from FAS_RESTRICTED_AC_HEADS where account_head_code=? and status ='Y'");
                         ps9.setInt(1, txtAcc_HeadCode);
                         rs9 = ps9.executeQuery();
                                                 
                         int countrecord=0;
                         while(rs9.next())
                         {
                             acc_unitid=rs9.getInt("ACCOUNTING_UNIT_ID");
                             off_wing=rs9.getInt("OFFICE_WING_SLNO");
                             ps2 =con.prepareStatement("select OFFICE_NAME,WINGS_APPLICABLE,PRIMARY_WORK_ID from COM_MST_OFFICES where OFFICE_ID=?");
                             ps2.setInt(1, acc_unitid);
                             rs2 = ps2.executeQuery();
                             if(rs2.next())
                             {
                                
                                 xml =xml + "<Office_id>" +acc_unitid+"</Office_id>"+"<wingId>"+off_wing+"</wingId>"
                                    + "<Office_name>" + rs2.getString("OFFICE_NAME") +"</Office_name>";
                                    
                                    
                                 if(rs2.getString("WINGS_APPLICABLE") !=null)
                                 {
                                 
                                    if(rs2.getString("WINGS_APPLICABLE").equalsIgnoreCase("Y"))
                                    {
                                         ps3 =con.prepareStatement("select WING_NAME from COM_OFFICE_WINGS where OFFICE_ID=? and OFFICE_WING_SINO=?");
                                         ps3.setInt(1, acc_unitid);
                                         ps3.setInt(2, off_wing);
                                         rs3 = ps3.executeQuery();
                                         if (rs3.next())
                                         {
                                            xml =xml + "<Wing_Name>" + rs3.getString("WING_NAME") +"</Wing_Name>";
                                         }
                                        else 
                                          {
                                              System.out.println("here in  no wing");
                                              xml = xml + "<Wing_Name>" + null + "</Wing_Name>";
                                          }
                                    }
                                 }
                                 else if(rs2.getString("WINGS_APPLICABLE") ==null)
                                 {
                                 
                                     xml =xml + "<Wing_Name>"+ null +"</Wing_Name>";
                                 
                                 }
                                         if (rs2.getString("PRIMARY_WORK_ID") != null) 
                                         {
                                              System.out.println("prim nature");
                                                    ps6 =  con.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?");
                                                    ps6.setString(1, rs2.getString("PRIMARY_WORK_ID"));
                                                    rs6 = ps6.executeQuery();
                                                    if (rs6.next())
                                                    xml = xml + "<WNature_Desc>" + rs6.getString("WORK_NATURE_DESC") + "</WNature_Desc>";
                                         } 
                                          else 
                                            {
                                                System.out.println("here");
                                                xml = xml + "<WNature_Desc>" + null + "</WNature_Desc>";
                                            }
                                countrecord++;
                             }
                         }
                         System.out.println("number of records::::::::::"+countrecord);
                         //ps2.close();ps3.close();ps6.close();rs2.close();rs3.close();rs6.close();
                    }
                   if(rs.getString("ACCESS_RESTRICTED").equalsIgnoreCase("Y"))
                    {
                        int officeid=rs.getInt("ACCESSIBLE_BY_OFFICE_CODE");
                        int wingid=rs.getInt("ACCESSIBLE_OFFICE_WING_SINO");
                        ps2 =con.prepareStatement("select OFFICE_NAME,WINGS_APPLICABLE,PRIMARY_WORK_ID from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1, officeid);
                        rs2 = ps2.executeQuery();
                        if(rs2.next())
                        {
                           
                            xml =xml + "<Office_id>" +officeid+"</Office_id>"+"<wingId>"+wingid+"</wingId>"
                               + "<Office_name>" + rs2.getString("OFFICE_NAME") +"</Office_name>";
                            if(rs2.getString("WINGS_APPLICABLE") !=null)
                            {
                               if(rs2.getString("WINGS_APPLICABLE").equalsIgnoreCase("Y"))
                               {
                                    ps3 =con.prepareStatement("select WING_NAME from COM_OFFICE_WINGS where OFFICE_ID=? and OFFICE_WING_SINO=?");
                                    ps3.setInt(1, officeid);
                                    ps3.setInt(2, wingid);
                                    rs3 = ps3.executeQuery();
                                    if (rs3.next())
                                    {
                                       xml =xml + "<Wing_Name>" + rs3.getString("WING_NAME") +"</Wing_Name>";
                                    }
                                   else 
                                     {
                                         System.out.println("here in  no wing");
                                         xml = xml + "<Wing_Name>" + null + "</Wing_Name>";
                                     }
                               }
                            }
                            else 
                            {
                                xml = xml + "<Wing_Name>" + null + "</Wing_Name>";
                            }
                                    if (rs2.getString("PRIMARY_WORK_ID") != null) 
                                    {
                                         System.out.println("prim nature");
                                               ps6 =  con.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?");
                                               ps6.setString(1, rs2.getString("PRIMARY_WORK_ID"));
                                               rs6 = ps6.executeQuery();
                                               if (rs6.next())
                                               xml = xml + "<WNature_Desc>" + rs6.getString("WORK_NATURE_DESC") + "</WNature_Desc>";
                                    } 
                                     else 
                                       {
                                           System.out.println("here");
                                           xml = xml + "<WNature_Desc>" + null + "</WNature_Desc>";
                                       }
                        }
                    }
                    if (rs.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) 
                    {
                       
                    	int sl_cnt=0;
                    	System.out.println("Getting SL Type Details>>>>>");
                    	ps =con.prepareStatement("select SUB_LEDGER_TYPE_CODE,STATUS from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'");// Status Field added 05-07-19 onwards
                        ps.setInt(1, txtAcc_HeadCode);
                        rs = ps.executeQuery();
                        while (rs.next()) 
                        {
                          sl_cnt++;
                        	String STATUS_SL = rs.getString("STATUS");
//                            System.out.println("status" + STATUS_SL);
                            int slcode = rs.getInt("SUB_LEDGER_TYPE_CODE");
//                            System.out.println("slcode" + slcode);
                            xml =xml + "<SLCODE>" + rs.getInt("SUB_LEDGER_TYPE_CODE") +"</SLCODE><STATUS_SL>" + STATUS_SL + "</STATUS_SL>";
                            if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) 
                            {
                                System.out.println("take SL DESC");
                                ps2 =con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                                ps2.setInt(1,
                                           rs.getInt("SUB_LEDGER_TYPE_CODE"));
                                           rs2 = ps2.executeQuery();
                                if (rs2.next())
                                    xml =xml + "<SLDESC>" + rs2.getString("SUB_LEDGER_TYPE_DESC") + "</SLDESC>";
                            }
                        }
                        if(sl_cnt==0)
                        {
                        	System.out.println("Status is 'N'");
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } 
                else 
                {
                    System.out.println("NO value for check code");
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
            //System.out.println("Load" + xml);
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
        } catch (IOException e) 
        {
                System.out.println("exception*******"+e);
        }
    }
}

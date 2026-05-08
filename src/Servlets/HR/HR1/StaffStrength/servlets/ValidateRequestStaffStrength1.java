package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ValidateRequestStaffStrength1 extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        try {

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

        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);
        int Office_Id = Integer.parseInt(request.getParameter("txtOffice_Id"));
        System.out.println("OfficeId:" + Office_Id);
        String FinancialYear = request.getParameter("cmbFinancialYear");
        System.out.println("Financ:" + FinancialYear);
        //String requestdate=request.getParameter("txtRequest_Date");
        //System.out.println("requestdate:"+requestdate);
        String txtRemarks = request.getParameter("txtRemarks");
        System.out.println("remarks" + txtRemarks);
        String ServiceGroup[] = request.getParameterValues("servicegroupid");
        String PostRank[] = request.getParameterValues("postrankid");
        String PostCategory[] =
            request.getParameterValues("employmentstatusid");
        String NoPost[] = request.getParameterValues("noofpost");
        String Remarks[] = request.getParameterValues("remarks");

        //String slno[]=request.getParameterValues("sno");
        // String ServiceGroupTo[]=request.getParameterValues("servicegroup");
        //String PostRankTo[]=request.getParameterValues("postrank");
        // String PostCategoryTo[]=request.getParameterValues("employmentstatus");
        String NoOfPostTo[] = request.getParameterValues("noofpostto");
        //String RemarksTo[]=request.getParameterValues("remarksto");
        String NoOfPostFrom[] = request.getParameterValues("noofpostfrom");
        String TotalStrength[] = request.getParameterValues("totalstrength");
        String RemarksFrom[] = request.getParameterValues("remarks");

        //Conversion for Date
        /*java.sql.Date dateOfFormation=null;
         System.out.println("before inserting date");
         if(requestdate!=null )
         {
             String dateString = requestdate;
             SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
             java.util.Date d;
             try {
               d = dateFormat.parse(dateString.trim());
               dateFormat.applyPattern("yyyy-MM-dd");
               dateString = dateFormat.format(d);
               dateOfFormation = java.sql.Date.valueOf(dateString);
             } catch (Exception e) {
               e.printStackTrace();
               //sendMessage(response,"Date of formation is not valid.<br>","back");
             }
         }
         System.out.println("After Date is:"+dateOfFormation);*/

        //Creation for Integer Array

        int servicegroup1[] = new int[ServiceGroup.length];
        int postrank1[] = new int[ServiceGroup.length];
        int nopost1[] = new int[ServiceGroup.length];
        for (int i = 0; i < ServiceGroup.length; i++) {

            servicegroup1[i] = Integer.parseInt(ServiceGroup[i]);
            postrank1[i] = Integer.parseInt(PostRank[i]);
            nopost1[i] = Integer.parseInt(NoPost[i]);


        }

        //int slno1[]=new int[slno.length];
        // int servicegroupto[]=new int[ServiceGroup.length];
        // int postrankto[]=new int[ServiceGroup.length];
        //int postcategoryto[]=new int[ServiceGroup.length];
        int noofpostto[] = new int[ServiceGroup.length];
        int noofpostfrom[] = new int[ServiceGroup.length];
        int totalstrength[] = new int[ServiceGroup.length];
        // try
        // {
        for (int i = 0; i < ServiceGroup.length; i++) {
            //slno1[i]=Integer.parseInt(slno[i]);
            // servicegroupto[i]=Integer.parseInt(ServiceGroupTo[i]);
            // postrankto[i]=Integer.parseInt(PostRankTo[i]);
            try {
                noofpostto[i] = Integer.parseInt(NoOfPostTo[i]);
            } catch (Exception e) {
                noofpostto[i] = 0;
            }
            try {
                noofpostfrom[i] = Integer.parseInt(NoOfPostFrom[i]);
            } catch (Exception e) {
                noofpostfrom[i] = 0;
            }
            try {
                totalstrength[i] = Integer.parseInt(TotalStrength[i]);
            } catch (Exception e) {
                totalstrength[i] = 0;
            }
        }
        //}catch(Exception e)
        //{
        //    System.out.println("Exception in Convertin Integer:"+e);
        // }

        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet res = null;
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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());


            try {
                System.out.println("Add");

                connection.clearWarnings();
                connection.setAutoCommit(false);

                /*String sql4="delete from HRM_SS_GENERAL where office_id=?";
                        ps=connection.prepareStatement(sql4);
                        ps.setInt(1,Office_Id);
                        ps.executeUpdate();
                        ps.close();*/

                String sql5 =
                    "delete from HRM_SS_SANCTION_tmp where office_id=? and fin_year=?";

                ps = connection.prepareStatement(sql5);
                ps.setInt(1, Office_Id);
                ps.setString(2, FinancialYear);
                ps.executeUpdate();
                ps.close();

                String sql6 =
                    "delete from HRM_SS_sanction_details_tmp where office_id=? and fin_year=?";
                ps = connection.prepareStatement(sql6);
                ps.setInt(1, Office_Id);
                ps.setString(2, FinancialYear);
                ps.executeUpdate();
                ps.close();

                String sql =
                    "insert into hrm_ss_sanction_tmp values(?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql);
                System.out.println("inner sql");
                ps.setInt(1, Office_Id);
                ps.setString(2, FinancialYear);
                ps.setString(3, txtRemarks);
                ps.setString(4, userid);
                long l1 = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l1);
                ps.setTimestamp(5, ts);

                ps.setString(6, "FR");
                ps.executeUpdate();
                System.out.println("after execute");
                ps.close();

                String sql1 =
                    "insert into hrm_ss_sanction_details_tmp(OFFICE_ID,FIN_YEAR,POST_SLNO,POST_RANK_ID,SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,TOTALSTRENGTH,PROCESS_FLOW_STATUS_ID) values (?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql1);
                for (int l = 0; l < ServiceGroup.length; l++) {
                    ps.setInt(1, Office_Id);
                    ps.setString(2, FinancialYear);
                    ps.setInt(3, l);
                    ps.setInt(4, postrank1[l]);
                    //ps.setString(5,PostCategory[l]);
                    ps.setInt(5, nopost1[l]);
                    ps.setInt(6, noofpostto[l]);
                    ps.setInt(7, noofpostfrom[l]);
                    ps.setString(8, Remarks[l]);
                    ps.setString(9, userid);
                    ps.setTimestamp(10, ts);
                    ;
                    ps.setInt(11, totalstrength[l]);
                    ps.setString(12, "FR");
                    ps.executeUpdate();


                }
                //connection.commit();

                try {
                    //connection.setAutoCommit(false);
                    String sql4 =
                        "insert into hrm_ss_sanction(OFFICE_ID,FIN_YEAR,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) (select OFFICE_ID,FIN_YEAR,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE from hrm_ss_sanction_tmp where office_id=? and fin_year=?)";
                    System.out.println(sql4);
                    ps = connection.prepareStatement(sql4);
                    ps.setInt(1, Office_Id);
                    ps.setString(2, FinancialYear);
                    ps.executeUpdate();
                    ps.close();

                    System.out.println("after first insert");


                    String sql9 =
                        "select office_level_id from com_mst_offices where office_id=?";
                    ps = connection.prepareStatement(sql9);
                    ps.setInt(1, Office_Id);
                    res = ps.executeQuery();
                    String officelevel = "";
                    if (res.next()) {
                        System.out.println("Office Level id is:" +
                                           res.getString("office_level_id"));
                        officelevel = res.getString("office_level_id");
                    }
                    if (officelevel.equalsIgnoreCase("DN")) {
                        String sql10 =
                            "insert into hrm_ss_sanction_details(OFFICE_ID,FIN_YEAR,POST_SLNO,POST_RANK_ID,SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,TOTALSTRENGTH,PROCESS_FLOW_STATUS_ID,AGGREGATE_NO_OF_POSTS) (select OFFICE_ID,FIN_YEAR,POST_SLNO,POST_RANK_ID,SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,TOTALSTRENGTH,PROCESS_FLOW_STATUS_ID,SANCTIONED_NO_OF_POSTS from hrm_ss_sanction_details_tmp where office_id=? and fin_year=?)";
                        ps1 = connection.prepareStatement(sql10);
                        ps1.setInt(1, Office_Id);
                        ps1.setString(2, FinancialYear);
                        ps1.executeUpdate();
                    } else {
                        System.out.println("else part of officelevel");
                        String sql8 =
                            "insert into hrm_ss_sanction_details(OFFICE_ID,FIN_YEAR,POST_SLNO,POST_RANK_ID,SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,TOTALSTRENGTH,PROCESS_FLOW_STATUS_ID) (select OFFICE_ID,FIN_YEAR,POST_SLNO,POST_RANK_ID,SANCTIONED_NO_OF_POSTS,DIVERSION_TO_OTHER,DIVERSION_FROM_OTHER,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,TOTALSTRENGTH,PROCESS_FLOW_STATUS_ID from hrm_ss_sanction_details_tmp where office_id=? and fin_year=?)";
                        System.out.println(sql8);
                        ps = connection.prepareStatement(sql8);
                        ps.setInt(1, Office_Id);
                        ps.setString(2, FinancialYear);
                        ps.executeUpdate();
                        System.out.println("after second insert");
                        ps.close();
                    }
                    connection.commit();
                } catch (Exception e) {
                    System.out.println("Exception in select to insert" + e);
                    connection.rollback();
                    String msg = "";
                    msg = "<br><br><p><b>" + e + "</b></p>";
                    sendMessage(response, msg, "ok");
                    return;
                }

                /*String sql1="insert into HRM_SS_GENERAL(OFFICE_ID,FIN_YEAR,UPDATED_DATE,REMARKS) values(?,?,?,?)";
                        ps=connection.prepareStatement(sql1);
                        ps.setInt(1,Office_Id);
                        ps.setString(2,FinancialYear);
                          long l1=System.currentTimeMillis();
                          Timestamp ts=new Timestamp(l1);
                          ps.setTimestamp(3,ts);
                        ps.setString(4,txtRemarks);
                        ps.executeUpdate();
                        ps.close();

                        String sql2="insert into HRM_SS_SANCTIONED(OFFICE_ID,POST_SLNO,POST_RANK_ID,EMPLOYMENT_STATUS_ID,SANCTIONED_NO_OF_POSTS,REMARKS,FIN_YEAR) values(?,?,?,?,?,?,?)";
                        ps=connection.prepareStatement(sql2);
                        for(int l=0;l<ServiceGroup.length;l++)
                        {
                            ps.setInt(1,Office_Id);
                            ps.setInt(2,l);
                            ps.setInt(3,postrank1[l]);
                            ps.setString(4,PostCategory[l]);
                            ps.setInt(5,nopost1[l]);
                            ps.setString(6,Remarks[l]);
                            ps.setString(7,FinancialYear);
                            ps.executeUpdate();
                        }

                        ps.close();

                        String sql3="insert into HRM_SS_DIVERSIONS(OFFICE_ID,POST_SLNO,POST_RANK_ID,EMPLOYMENT_STATUS_ID,POSTS_DIVERSION_TO,REMARKS_DIVERSION_TO,POSTS_DIVERSION_FROM,REMARKS_DIVERSION_FROM,FIN_YEAR) values(?,?,?,?,?,?,?,?,?)";
                        PreparedStatement statement=connection.prepareStatement(sql3);

                        for(int j=0;j<ServiceGroupTo.length;j++)
                        {
                              statement.setInt(1,Office_Id);
                              statement.setInt(2,j);
                              statement.setInt(3,postrankto[j]);
                              statement.setString(4,PostCategoryTo[j]);
                              statement.setInt(5,noofpostto[j]);
                              statement.setString(6,RemarksTo[j]);
                              statement.setInt(7,noofpostfrom[j]);
                              statement.setString(8,RemarksFrom[j]);
                              statement.setString(9,FinancialYear);
                              statement.executeUpdate();

                        }*/


            } catch (Exception e) {
                System.out.println("Exception in connection:" + e);
                connection.rollback();
            }

            finally {
                //connection.close();
            }
            String msg =
                "Request Staff Strength Details Has been Successfully Validated.<br>Office Id  is  : " +
                Office_Id;
            msg = "<br><br><p><b>" + msg + "</b></p>";
            sendMessage(response, msg, "ok");


        } catch (Exception e) {
            System.out.println("Exception" + e);
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
        } catch (IOException e) {
        }
    }

}

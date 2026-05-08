package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class NewAttachmentServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    static int i = 0;
    static int j = 0;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet results = null;
        ResultSet results1 = null;
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

        int OfficeId = 0;
        int ii = 0;
        int maxslno = 0;
        int maxslno1 = 0;
        String txtDateOfAttachment = "";
        String processflow = "";
        int cmbLevelId = 0;
        int cmbSecondaryID = 0;
        OfficeId = Integer.parseInt(request.getParameter("txtOffice_Id"));
        System.out.println(OfficeId);
        int AttachedOfficeid =
            Integer.parseInt(request.getParameter("txtAttachedOfficeID"));
        System.out.println("AttacheId" + AttachedOfficeid);
        txtDateOfAttachment = request.getParameter("txtDOC");
        System.out.println("Date is" + txtDateOfAttachment);
        String cmbPrimaryId = request.getParameter("cmbPrimaryID");
        System.out.println("WorkNature" + cmbPrimaryId);
        System.out.println("hai");

        //cmbLevelId=Integer.parseInt(request.getParameter("cmbLevelId"));
        //cmbSecondaryID=Integer.parseInt(request.getParameter("cmbSecondaryID"));
        //String NewOfficeName=request.getParameter("txtNewOfficeName");
        //String NewOfficeAddress=request.getParameter("txtNewOfficeAddress");


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
            String od = txtDateOfAttachment;
            // System.out.println("");
            String[] s = od.split("/");
            System.out.println("s is" + s[0]);
            System.out.println("s is" + s[1]);
            System.out.println("s is" + s[2]);

            java.util.Calendar g =
                new java.util.GregorianCalendar(Integer.parseInt(s[2]),
                                                Integer.parseInt(s[1]) - 1,
                                                Integer.parseInt(s[0]) - 1);
            java.util.Date d1 = g.getTime();
            java.sql.Date sd = new java.sql.Date(d1.getTime());


            System.out.println(sd);

            //Date Conversion for Date
            java.sql.Date dateOfAttachment = null;
            System.out.println("before converting date");
            String dateString = txtDateOfAttachment;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            d = dateFormat.parse(txtDateOfAttachment.trim());
            dateFormat.applyPattern("yyyy-MM-dd");
            dateString = dateFormat.format(d);
            dateOfAttachment = java.sql.Date.valueOf(dateString);
            //dateOfAttachment=


            System.out.println(dateOfAttachment);


            try {
                connection.setAutoCommit(false);

                String sql5 =
                    "select max(ATTACHEMENT_SL_NO) from COM_OFFICE_ATTACHMENTS ";
                ps1 = connection.prepareStatement(sql5);
                // ps1.setInt(1,OfficeId);
                results = ps1.executeQuery();

                if (results.next()) {
                    maxslno = results.getInt(1);
                    maxslno = maxslno + 1;

                }

                /*String sql="select PROCESS_FLOW_STATUS_ID from com_office_attachments where attached_office_id=?";
                       ps=connection.prepareStatement(sql);
                       ps.setInt(1,AttachedOfficeid);
                       //ps.setDate(2,dateOfAttachment);
                       //ps.setDate(3,sd);
                       //ps.setInt(3,OfficeId);
                       results1=ps.executeQuery();
                       while(results1.next())
                       {
                        processflow=results1.getString("PROCESS_FLOW_STATUS_ID");

                       }
                       if(processflow.equalsIgnoreCase("FR") || processflow.equalsIgnoreCase("VR"))
                       {

                       }
                       else
                       {

                       }
                       ps.close();*/

                String sqlinsert =
                    "insert into COM_OFFICE_ATTACHMENTS(ATTACHED_OFFICE_ID,ATTACHEMENT_SL_NO,CONTROLLING_OFFICE_ID,DATE_OF_ATTACHMENT,PROCESS_FLOW_STATUS_ID,PRIME_WORK_NATURE_ID) values(?,?,?,?,?,?)";
                ps = connection.prepareStatement(sqlinsert);
                ps.setInt(1, OfficeId);
                ps.setInt(2, maxslno);
                ps.setInt(3, AttachedOfficeid);
                ps.setDate(4, dateOfAttachment);
                ps.setString(5, "CR");
                ps.setString(6, cmbPrimaryId);
                ii = ps.executeUpdate();
                if (ii >= 1) {
                    connection.commit();
                    //connection.setAutoCommit(true);
                    String msg =
                        " Attachment Office has been successfully Inserted";
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");
                }


            } catch (SQLException e) {
                System.out.println("Exception in " + e);
                //connection.setAutoCommit(true);
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.commit();
            }

        } catch (Exception e) {
            System.out.println("Exception in Base Try:" + e);
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

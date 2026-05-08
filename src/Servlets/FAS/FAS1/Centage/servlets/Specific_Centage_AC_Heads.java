package Servlets.FAS.FAS1.Centage.servlets;

import java.io.*;

import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Specific_Centage_AC_Heads extends HttpServlet {

    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                System.out.println((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                response.sendRedirect((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                return;
            }
            System.out.println(session);
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Redirect Error :").append(e).toString());
        }
        Connection con = null;
        PreparedStatement ps = null;
        response.setContentType("text/xml; charset=windows-1252");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        ResultSet rs = null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Exception in opening connection :").append(e).toString());
        }
        try {
            strCommand = request.getParameter("Command");
            System.out.println((new StringBuilder()).append("assign..here command...").append(strCommand).toString());
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Exception in assigning...").append(e).toString());
        }
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;
        int txtWorkExpACHeadCode = 0;
        int txtProjectSLCode = 0;
        int txtDftCentageCtgy = 0;
        String txtDftCentageCtgyType="";
        int txtCrACHeadCode = 0;
        int txtDrACHeadCode = 0;
        String txtRemarks = null;
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            System.out.println("Exception to get Accounting Unit Code ");
        }
        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (Exception e) {
            System.out.println("Exception to get Accounting Office Code");
        }
        try {
            txtWorkExpACHeadCode =
                    Integer.parseInt(request.getParameter("txtWorkExpACHeadCode"));
        } catch (Exception e) {
            System.out.println("Exception to get Work Expenditure Account Head Code");
        }
        try {
            txtProjectSLCode =
                    Integer.parseInt(request.getParameter("txtProjectSLCode"));
        } catch (Exception e) {
            System.out.println("Exception to get Work Expenditure Account Head Code");
        }
        
        
        try
        {
            String s = new String();
            s=request.getParameter("txtDftCentageCtgy");            
              System.out.println("venkat------->>>"+s);            
            txtDftCentageCtgyType =s.charAt(0)+"";             
              System.out.println("venkat------->>>"+txtDftCentageCtgyType);            
            txtDftCentageCtgy = Integer.parseInt(s.substring(1));            
              System.out.println("venkat------->>>"+txtDftCentageCtgy);            
        }
        catch(Exception e)
        {
            System.out.println("Exception to Default Centage Category");
        }
        
        
        
        
     /*  try {
            txtDftCentageCtgy =
                    Integer.parseInt(request.getParameter("txtDftCentageCtgy"));
        } catch (Exception e) {
            System.out.println("Exception to Default Centage Category");
        }
     */   
        
        
        
        try {
            txtCrACHeadCode =
                    Integer.parseInt(request.getParameter("txtCrACHeadCode"));
        } catch (Exception e) {
            System.out.println("Exception to get Credit Account Head Code");
        }
        try {
            txtDrACHeadCode =
                    Integer.parseInt(request.getParameter("txtDrACHeadCode"));
        } catch (Exception e) {
            System.out.println("Exception to get Debit Account Head Code");
        }
        try {
            txtRemarks = request.getParameter("txtRemarks");
        } catch (Exception e) {
            System.out.println("Exception to Remarks");
        }


        if (strCommand.equalsIgnoreCase("ProjectSL_Code")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>ProjectSL_Code</command>";
            try {
                ps = con.prepareStatement("select PROJECT_ID, PROJECT_NAME  from PMS_MST_PROJECTS_VIEW where  OFFICE_ID=? order by PROJECT_NAME ");
                ps.setInt(1, cmbOffice_code);
                rs = ps.executeQuery();
                int i;
                for (i = 0; rs.next(); i++) 
                {
                     xml = (new StringBuilder()).append(xml).append("<cat_pair>").toString();
                     xml = (new StringBuilder()).append(xml).append("<cat_code>").append(rs.getInt("PROJECT_ID")).append("</cat_code>").toString();
                     xml = (new StringBuilder()).append(xml).append("<cat_desc>").append(rs.getString("PROJECT_NAME")).append("</cat_desc>").toString();
                     xml = (new StringBuilder()).append(xml).append("</cat_pair>").toString();
                    }

                if (i > 0) {
                    xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                } else {
                    xml = (new StringBuilder()).append(xml).append("<flag>NotAvailable</flag>").toString();
                }
            } catch (Exception e) {
                xml = (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            xml = (new StringBuilder()).append(xml).append("</response>").toString();
            
            System.out.println(xml);
            out.println(xml);
            
        }


        else if (strCommand.equalsIgnoreCase("Load_Category_Code")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Load_Category_Code</command>";
            try {

                ps = con.prepareStatement("SELECT \n" + 
                "  ( CATEGORY_TYPE || category_code )  as category_code,\n" + 
                "  category_desc\n" + 
                "FROM fas_centage_category_ho_master\n" + 
                "WHERE ACCOUNTING_UNIT_ID   = ?   \n" + 
                "AND ACCOUNTING_FOR_OFFICE_ID = ? \n" + 
                "\n" + 
                "\n" + 
                "UNION ALL\n" + 
                "\n" + 
                "SELECT (CATEGORY_TYPE || category_code ) as category_code,\n" + 
                "  category_desc\n" + 
                "FROM fas_centage_category_ho_master\n" + 
                "WHERE category_type='C'");

                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                rs = ps.executeQuery();
                int i;
                for (i = 0; rs.next(); i++) 
                {
                    xml = (new StringBuilder()).append(xml).append("<cat_pair>").toString();
                    xml = (new StringBuilder()).append(xml).append("<cat_code>").append(rs.getString("category_code")).append("</cat_code>").toString();
                    xml = (new StringBuilder()).append(xml).append("<cat_desc>").append(rs.getString("category_desc")).append("</cat_desc>").toString();
                    xml = (new StringBuilder()).append(xml).append("</cat_pair>").toString();
                }

                if (i > 0) {
                    xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                } else {
                    xml = (new StringBuilder()).append(xml).append("<flag>NotAvailable</flag>").toString();
                }
            }
            catch (Exception e) 
            {
                xml =  (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            
                xml = (new StringBuilder()).append(xml).append("</response>").toString();
            
            System.out.println(xml);
            out.println(xml);
            
        }


        else if (strCommand.equalsIgnoreCase("Add")) {
        	System.out.println("inside add");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            try {
                con.setAutoCommit(false);
                ps =
                	con.prepareStatement("delete from FAS_SPECI_CENTAGE_AC_HEADS where ACCOUNTING_UNIT_ID = ? and ACCOUNTI" +
                       "NG_FOR_OFFICE_ID = ? and WEXP_ACCOUNT_HEAD_CODE = ? and PROJECT_SL_CODE=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtWorkExpACHeadCode);
                ps.setInt(4, txtProjectSLCode);
                ps.executeUpdate();
                ps = con.prepareStatement("insert into FAS_SPECI_CENTAGE_AC_HEADS ( ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFI" +
                       "CE_ID, WEXP_ACCOUNT_HEAD_CODE,PROJECT_SL_CODE, DEFAULT_CENTAGE_CATEGORY,CR_ACCOU" +
                       "NT_HEAD_CODE, DR_ACCOUNT_HEAD_CODE, REMARKS, UPDATED_BY_USER_ID, UPDATED_DATE, DEFAULT_CENTAGE_CATEGORY_TYPE) v" +
                       "alues(?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCodedd"+cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);System.out.println(cmbOffice_code);
                ps.setInt(3, txtWorkExpACHeadCode);System.out.println(txtWorkExpACHeadCode);
                ps.setInt(4, txtProjectSLCode);System.out.println(txtProjectSLCode);
                ps.setInt(5, txtDftCentageCtgy);System.out.println(txtDftCentageCtgy);
                ps.setInt(6, txtCrACHeadCode);System.out.println(txtCrACHeadCode);
                ps.setInt(7, txtDrACHeadCode);System.out.println(txtDrACHeadCode);
                ps.setString(8, txtRemarks);System.out.println(txtRemarks);
                ps.setString(9, update_user);System.out.println(update_user);
                ps.setTimestamp(10, ts);System.out.println(ts);
                ps.setString(11, txtDftCentageCtgyType);System.out.println(txtDftCentageCtgyType);
                
                ps.executeUpdate();
                con.commit();
                con.setAutoCommit(true);
                xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
            } catch (Exception e) {
                xml =
 (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            xml =
 (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
        }


        else if (strCommand.equalsIgnoreCase("Update")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Update</command>";
            try {
                ps =
  con.prepareStatement("update FAS_SPECI_CENTAGE_AC_HEADS set DEFAULT_CENTAGE_CATEGORY=? , CR_ACCOUNT_HE" +
                       "AD_CODE=?, DR_ACCOUNT_HEAD_CODE=? , REMARKS=?, UPDATED_BY_USER_ID=? , UPDATED_DA" +
                       "TE=?  where ACCOUNTING_UNIT_ID = ? and ACCOUNTING_FOR_OFFICE_ID = ? and WEXP_ACC" +
                       "OUNT_HEAD_CODE = ? and PROJECT_SL_CODE=? ");
                ps.setInt(1, txtDftCentageCtgy);
                ps.setInt(2, txtCrACHeadCode);
                ps.setInt(3, txtDrACHeadCode);
                ps.setString(4, txtRemarks);
                ps.setString(5, update_user);
                ps.setTimestamp(6, ts);
                ps.setInt(7, cmbAcc_UnitCode);
                ps.setInt(8, cmbOffice_code);
                ps.setInt(9, txtWorkExpACHeadCode);
                ps.setInt(10, txtProjectSLCode);
                ps.executeUpdate();
                xml =
 (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
            } catch (Exception e) {
                xml =
 (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            xml =
 (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
        }


        else if (strCommand.equalsIgnoreCase("Delete")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("delete from  FAS_SPECI_CENTAGE_AC_HEADS where ACCOUNTING_UNIT_ID = ? and ACCOUNT" +
                       "ING_FOR_OFFICE_ID = ? and WEXP_ACCOUNT_HEAD_CODE = ? and PROJECT_SL_CODE=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtWorkExpACHeadCode);
                ps.setInt(4, txtProjectSLCode);
                ps.executeUpdate();
                xml =
 (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
            } catch (Exception e) {
                xml =
 (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            xml =
 (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
        }


    }

    public Specific_Centage_AC_Heads() {
    }
}

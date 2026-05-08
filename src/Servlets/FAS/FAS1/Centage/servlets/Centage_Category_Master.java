package Servlets.FAS.FAS1.Centage.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Centage_Category_Master extends HttpServlet
{

    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	
    	System.out.println("This is venkat --------------->>>.");
    	
        HttpSession session = request.getSession(false);
        try
        {
            if(session == null)
            {
                System.out.println((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                response.sendRedirect((new StringBuilder()).append(request.getContextPath()).append("/index.jsp").toString());
                return;
            }
            System.out.println(session);
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Redirect Error :").append(e).toString());
        }
        response.setContentType("text/xml; charset=windows-1252");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        String strCommand = "";
        try
        {
            ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in opening connection :").append(e).toString());
        }
        try
        {
            strCommand = request.getParameter("Command");
            System.out.println((new StringBuilder()).append("assign..here command...").append(strCommand).toString());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in assigning...").append(e).toString());
        }
        int txtCategory_Code = 0;
        String txtCategory_Desc = null;
        String cmbCategoryType = null;
        int cmbOffice_code = 0;
        int cmbAcc_UnitCode = 0;
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        try
        {
            cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Accounting Unit Code ");
        }
        try
        {
            cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
            System.out.println("cmbOffice_code"+cmbOffice_code);
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Accounting Office Code ");
        }
        try
        {
            txtCategory_Code = Integer.parseInt(request.getParameter("txtCategory_Code"));
            System.out.println("txtCategory_Code"+txtCategory_Code);
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Category Code ");
        }
        try
        {
            txtCategory_Desc = request.getParameter("txtCategory_Desc");
            System.out.println("txtCategory_Code"+txtCategory_Code);
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Category Description");
        }
        try
        {
            cmbCategoryType = request.getParameter("cmbCategoryType");
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Category Type");
        }
        if(strCommand.equalsIgnoreCase("Add"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            try
            {
                int max_code = 0;
                ps2 = con.prepareStatement(" select decode( max(CATEGORY_CODE),null, 0 ,max(CATEGORY_CODE)) as max_code from" +
" FAS_CENTAGE_CATEGORY_HO_MASTER  \n where ACCOUNTING_UNIT_ID =? and ACCOUNTING_F" +
"OR_OFFICE_ID = ?"
);
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                for(rs2 = ps2.executeQuery(); rs2.next();)
                {
                    max_code = rs2.getInt("max_code");
                }
                
                max_code++;
                ps = con.prepareStatement("insert into FAS_CENTAGE_CATEGORY_HO_MASTER (ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_O" +
"FFICE_ID, CATEGORY_CODE, CATEGORY_DESC, CATEGORY_TYPE, UPDATED_BY_USER_ID, UPDAT" +
"ED_DATE) values(?,?,?,?,?,?,?)"
);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, max_code);
                ps.setString(4, txtCategory_Desc);
                ps.setString(5, cmbCategoryType);
                ps.setString(6, update_user);
                ps.setTimestamp(7, ts);
                ps.executeUpdate();
                xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
            }
            catch(Exception e)
            {
                xml = (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            xml = (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
        } else
        if(strCommand.equalsIgnoreCase("Update"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Update</command>";
            try
            {
                ps = con.prepareStatement(" update FAS_CENTAGE_CATEGORY_HO_MASTER set CATEGORY_DESC=? , CATEGORY_TYPE=? whe" +
"re CATEGORY_CODE=?  and ACCOUNTING_UNIT_ID =? and ACCOUNTING_FOR_OFFICE_ID= ? "
);
                ps.setString(1, txtCategory_Desc);
                ps.setString(2, cmbCategoryType);
                ps.setInt(3, txtCategory_Code);
                ps.setInt(4, cmbAcc_UnitCode);
                ps.setInt(5, cmbOffice_code);
                ps.executeUpdate();
                xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
            }
            catch(Exception e)
            {
                xml = (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            xml = (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
        } else
        if(strCommand.equalsIgnoreCase("Delete"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Delete</command>";
            try
            {
                ps = con.prepareStatement("delete from  FAS_CENTAGE_CATEGORY_HO_MASTER where CATEGORY_CODE=? and ACCOUNTING" +
"_UNIT_ID =? and ACCOUNTING_FOR_OFFICE_ID= ? "
);
                ps.setInt(1, txtCategory_Code);
                ps.setInt(2, cmbAcc_UnitCode);
                ps.setInt(3, cmbOffice_code);
                ps.executeUpdate();
                xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
            }
            catch(Exception e)
            {
                xml = (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            xml = (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
        }
    }

    public Centage_Category_Master()
    {
    }
}



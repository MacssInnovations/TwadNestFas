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


public class Centage_Rates extends HttpServlet
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
   
   
        /** Session Checking */
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
        
        
        /** Variables Declaration */
        Connection con = null;
        PreparedStatement ps = null;
        response.setContentType("text/xml; charset=windows-1252");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        ResultSet rs = null;
        
        
        /** Database Connection */
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
        
        
        /** Get Command Paramter */
        try
        {
            strCommand = request.getParameter("Command");
            System.out.println((new StringBuilder()).append("assign..here command...").append(strCommand).toString());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in assigning...").append(e).toString());
        }
        
        
        /** Variables Declaration */
        int txtCategory_Code = 0;
        double txtCentageRates = 0.0D;
        String txtFinancialYear = null;
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        int cmbOffice_code = 0;
        int cmbAcc_UnitCode = 0;
        String txtDftCentageCtgyType="";
        
        /** Get Accounting Unit Id */
        try
        {
            cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Accounting Unit Code ");
        }
        
        /** Get Office ID */
        try
        {
            cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Accounting Office Code ");
        }
        
        /** Get Catagory Code */
        try
        {
           // txtCategory_Code = Integer.parseInt(request.getParameter("txtCategory_Code"));
           
            String s = new String();
            s=request.getParameter("txtCategory_Code");            
            System.out.println("venkat------->>>"+s);            
            txtDftCentageCtgyType =s.charAt(0)+"";             
            System.out.println("venkat------->>>"+txtDftCentageCtgyType);            
            txtCategory_Code = Integer.parseInt(s.substring(1));            
            System.out.println("venkat------->>>"+txtCategory_Code);                        
            
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Category Code ");
        }
        
        /** Get Centage Rates */
        try
        {
            txtCentageRates = Double.parseDouble(request.getParameter("txtCentageRates"));
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Centage Rate");
        }
        
        
        /** Get Financial Year */
        try
        {
            txtFinancialYear = request.getParameter("txtFinancialYear");
        }
        catch(Exception e)
        {
            System.out.println("Exception to catch Financial Year");
        }
        
        
        /** Load Category Code */
        if(strCommand.equalsIgnoreCase("Load_Category_Code"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Load_Category_Code</command>";
            try
            {
                ps = con.prepareStatement("SELECT category_code, category_desc, CATEGORY_TYPE FROM fas_centage_category_ho_master where  ACCOUNTING_UNIT_ID = ? and ACCOUNTING_FOR_OFFICE_ID = ? order by category_desc ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                rs = ps.executeQuery();
                int i;
                for(i = 0; rs.next(); i++)
                {
                  /*  xml = (new StringBuilder()).append(xml).append("<cat_pair>").toString();
                    xml = (new StringBuilder()).append(xml).append("<cat_code>").append(rs.getInt("category_code")).append("</cat_code>").toString();
                    xml = (new StringBuilder()).append(xml).append("<cat_desc>").append(rs.getString("category_desc")).append("</cat_desc>").toString();
                    xml = (new StringBuilder()).append(xml).append("</cat_pair>").toString();
                   */
                   
                   xml=xml+"<cat_pair>";
                   xml=xml+"<cat_code>"+rs.getString("CATEGORY_TYPE")+rs.getInt("category_code")+"</cat_code>";
                   xml=xml+"<cat_desc>"+rs.getString("category_desc")+"</cat_desc>";
                   xml=xml+"</cat_pair>";
                   
                }

                if(i > 0)
                {
                    xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                } else
                {
                    xml = (new StringBuilder()).append(xml).append("<flag>NotAvailable</flag>").toString();
                }
            }
            catch(Exception e)
            {
                xml = (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            }
            xml = (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
        }
        
        
        /** Addition */
        else if(strCommand.equalsIgnoreCase("Add"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            try
            {
                con.setAutoCommit(false);
                
                ps = con.prepareStatement("delete from  FAS_CENTAGE_RATES  where CATEGORY_CODE=? and ACCOUNTING_UNIT_ID = ? and ACCOUNTING_FOR_OFFICE_ID= ? and DEFAULT_CENTAGE_CATEGORY_TYPE = ? ");
                ps.setInt(1, txtCategory_Code);
                ps.setInt(2, cmbAcc_UnitCode);
                ps.setInt(3, cmbOffice_code);
                ps.setString(4,txtDftCentageCtgyType);
                
                ps.executeUpdate();
                
                ps = con.prepareStatement("insert into FAS_CENTAGE_RATES ( ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, " +
                                          "CATEGORY_CODE, CENTAGE_RATE, FINANCIAL_YEAR, UPDATED_BY_USER_ID, UPDATED_DATE, DEFAULT_CENTAGE_CATEGORY_TYPE) values(?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCategory_Code);
                ps.setDouble(4, txtCentageRates);
                ps.setString(5, txtFinancialYear);
                ps.setString(6, update_user);
                ps.setTimestamp(7, ts);
                ps.setString(8,txtDftCentageCtgyType);
                
                ps.executeUpdate();
                con.commit();
                
                
                con.setAutoCommit(true);
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
        
        
        /** Updation */
        else if(strCommand.equalsIgnoreCase("Update"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Update</command>";
            try
            {
                ps = con.prepareStatement(" update FAS_CENTAGE_RATES set CENTAGE_RATE=? , FINANCIAL_YEAR=? where CATEGORY_CODE=? and ACCOUNTING_UNIT_ID = ? and ACCOUNTING_FOR_OFFICE_ID= ? and DEFAULT_CENTAGE_CATEGORY_TYPE = ? ");
                ps.setDouble(1, txtCentageRates);
                ps.setString(2, txtFinancialYear);
                ps.setInt(3, txtCategory_Code);
                ps.setInt(4, cmbAcc_UnitCode);
                ps.setInt(5, cmbOffice_code);
                ps.setString(6,txtDftCentageCtgyType);
                
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
        
        
        /** Deletion */
        else if(strCommand.equalsIgnoreCase("Delete"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Delete</command>";
            try
            {
                ps = con.prepareStatement("delete from  FAS_CENTAGE_RATES  where CATEGORY_CODE=? and DEFAULT_CENTAGE_CATEGORY_TYPE = ? and ACCOUNTING_UNIT_ID = ? and ACCOUNTING_FOR_OFFICE_ID= ? ");
                ps.setInt(1, txtCategory_Code);
                ps.setString(2,txtDftCentageCtgyType);
                ps.setInt(3, cmbAcc_UnitCode);
                ps.setInt(4, cmbOffice_code);
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

    public Centage_Rates()
    {
    }
}

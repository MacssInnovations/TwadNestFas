package Servlets.FAS.FAS1.Centage.servlets;

import java.io.*;
import java.sql.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class View_Centage_AfterPosting extends HttpServlet
{

    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
     
     
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        
        /** Variables Declaration */ 
        
        String strCommand = "";
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;
        int txtCB_Month = 0;
        int txtCB_Year = 0;
        String xml = null;
        String sql = null;
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        
        /** Seesion Checking */
        
        javax.servlet.http.HttpSession session = request.getSession(false);
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
//            ConnectionString = (new StringBuilder()).append(strdsn.trim()).append("@").append(strhostname.trim()).append(":").append(strportno.trim()).append(":").append(strsid.trim()).toString();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in opening connection :").append(e).toString());
        }
        
        
        /** Get Commnad Parameter */
        try
        {
            strCommand = request.getParameter("Command");
            System.out.println((new StringBuilder()).append("assign..here command...").append(strCommand).toString());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception in assigning...").append(e).toString());
        }
        
        
        /** Accounting Unit ID */
        try
        {
            cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
               
        /** Accounting for Office Code */
        try
        {
            cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }        
        
        
        /** Cashbook Year */
        try
        {
            txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
      
        /** Cashbook Month */
        try
        {
            txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        }
        catch(NumberFormatException e)
        {
            System.out.println((new StringBuilder()).append("exception").append(e).toString());
        }
       
        
        if(strCommand.equalsIgnoreCase("PendingJournals"))
        {
            xml = "<response><command>PendingJournals</command>";
            try
            {
                sql = " SELECT\n" + 
                "  accounting_unit_id ,\n" + 
                "  accounting_for_office_id,\n" + 
                "  cashbook_year,\n" + 
                "  cashbook_month,\n" + 
                "  voucher_no,\n" + 
                "  TO_CHAR(voucher_date,'DD/MM/YYYY') AS voucher_date,\n" + 
                "  cr_account_head_code,\n" + 
                "  (SELECT account_head_desc\n" + 
                "  FROM com_mst_account_heads\n" + 
                "  WHERE account_head_code = cr_account_head_code\n" + 
                "  ) AS cr_account_head_code_desc,\n" + 
                "  dr_account_head_code,\n" + 
                "  (SELECT account_head_desc\n" + 
                "  FROM com_mst_account_heads\n" + 
                "  WHERE account_head_code = dr_account_head_code\n" + 
                "  ) AS dr_account_head_code_desc,\n" + 
                "  trim(TO_CHAR(amount,'99999999999999.99')) as amount\n" + 
                "FROM\n" + 
                "  (\n" + 
                "    SELECT\n" + 
                "      *\n" + 
                "    FROM\n" + 
                "    (\n" + 
                "         SELECT accounting_unit_id,\n" + 
                "          accounting_for_office_id,\n" + 
                "          cashbook_year,\n" + 
                "          cashbook_month,\n" + 
                "          voucher_no,\n" + 
                "          voucher_date\n" + 
                "        FROM FAS_JOURNAL_MASTER\n" + 
                "        where ACCOUNTING_UNIT_ID      = ?     \n" + 
                "        AND ACCOUNTING_FOR_OFFICE_ID  = ?     \n" + 
                "        AND CASHBOOK_YEAR             = ?     \n" + 
                "        AND CASHBOOK_MONTH            = ?     \n" + 
                "        AND created_by_module         ='GJV' AND JOURNAL_STATUS='L'  \n" + 
                "        AND JOURNAL_TYPE_CODE           =58    \n" + 
                "    )a   \n" + 
                "    \n" + 
                "    left OUTER JOIN\n" + 
                "    \n" + 
                "    (\n" + 
                "     SELECT\n" + 
                "      accounting_unit_id AS unit_id,\n" + 
                "      accounting_for_office_id AS office_id,\n" + 
                "      cashbook_year            AS cb_year,\n" + 
                "      cashbook_month cb_month,\n" + 
                "      voucher_no                AS vou_no,\n" + 
                "      SUM(cr_account_head_code) AS cr_account_head_code,\n" + 
                "      SUM(dr_account_head_code) AS dr_account_head_code,\n" + 
                "      amount\n" + 
                "    FROM\n" + 
                "     (\n" + 
                "        select \n" + 
                "          accounting_unit_id, \n" + 
                "          accounting_for_office_id, \n" + 
                "          cashbook_year, \n" + 
                "          cashbook_month, \n" + 
                "          voucher_no, \n" + 
                "          account_head_code, \n" + 
                "          cr_dr_indicator,\n" + 
                "          CASE  WHEN cr_dr_indicator='CR' THEN \n" + 
                "             account_head_code\n" + 
                "          END AS cr_account_head_code,\n" + 
                "          CASE WHEN cr_dr_indicator='DR' THEN \n" + 
                "            account_head_code\n" + 
                "          END AS dr_account_head_code,  \n" + 
                "        amount\n" + 
                "        FROM FAS_JOURNAL_TRANSACTION\n" + 
                "        WHERE ACCOUNTING_UNIT_ID      = ?     \n" + 
                "        AND ACCOUNTING_FOR_OFFICE_ID  = ?     \n" + 
                "        AND CASHBOOK_YEAR             = ?     \n" + 
                "        AND CASHBOOK_MONTH            = ?     \n" + 
             
                "     )\n" + 
                "    GROUP BY ACCOUNTING_UNIT_ID,\n" + 
                "      ACCOUNTING_FOR_OFFICE_ID,\n" + 
                "      CASHBOOK_YEAR,\n" + 
                "      CASHBOOK_MONTH,\n" + 
                "      voucher_no,\n" + 
                "      amount\n" + 
                "    ) b \n" + 
                "  on a.accounting_unit_id   =b.unit_id\n" + 
                "  AND a.ACCOUNTING_FOR_OFFICE_ID = b.OFFICE_ID\n" + 
                "  AND a.CASHBOOK_YEAR            = b.cb_year\n" + 
                "  AND a.CASHBOOK_MONTH           =b.cb_month\n" + 
                "  AND a.voucher_no               =b.vou_no\n" + 
                "  \n" + 
                "  \n" + 
                "  \n" + 
                "  )\n" + 
                "ORDER BY \n" + 
                "  accounting_unit_id ,\n" + 
                "  accounting_for_office_id,\n" + 
                "  cashbook_year,\n" + 
                "  cashbook_month,\n" + 
                "  voucher_no,\n" + 
                "  voucher_date  ";
            
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setInt(5, cmbAcc_UnitCode);
                ps.setInt(6, cmbOffice_code);
                ps.setInt(7, txtCB_Year);
                ps.setInt(8, txtCB_Month);
                
                rs = ps.executeQuery();
                int cnt = 0;
                xml = (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                while(rs.next()) 
                {
                	//System.out.println(""+);
                    xml = (new StringBuilder()).append(xml).append("<option>").append("<voucher_no>").append(rs.getInt("voucher_no")).append("</voucher_no>").append("<voucher_date>").append(rs.getString("voucher_date")).append("</voucher_date>").append("<cr_account_head_code>").append(rs.getInt("cr_account_head_code")).append("</cr_account_head_code>").append("<cr_account_head_code_desc>").append(rs.getString("cr_account_head_code_desc")).append("</cr_account_head_code_desc>").append("<dr_account_head_code>").append(rs.getInt("dr_account_head_code")).append("</dr_account_head_code>").append("<dr_account_head_code_desc>").append(rs.getString("dr_account_head_code_desc")).append("</dr_account_head_code_desc>").append("<amount>").append(rs.getString("amount")).append("</amount></option>").toString();
                    cnt++;
                }
                if(cnt == 0)
                {
                    xml = "<response><command>PendingJournals</command><flag>failure</flag>";
                }
            }
            catch(Exception e)
            {
                System.out.println((new StringBuilder()).append("Error ").append(e).toString());
                xml = "<response><command>PendingJournals</command><flag>failure</flag>";
            }
            xml = (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println(xml);
            out.println(xml);
            
            
            
       }
       else if(strCommand.equalsIgnoreCase("RevokeJournals") ) 
       {
          
          xml = "<response><command>RevokeJournals</command>";
          
          int CJVno=0;
          String CJVdate=null;
          Calendar c=null;
          
          try 
          {
          
              /** CJV Number */
              try
              {
                  CJVno = Integer.parseInt(request.getParameter("CJVno"));
              }
              catch(NumberFormatException e)
              {
                  System.out.println((new StringBuilder()).append("exception").append(e).toString());
              }
            //  System.out.println((new StringBuilder()).append("CJVno ").append(CJVno).toString());
              
              
          
              /** CJV Date */
              try
              {
                  CJVdate =  request.getParameter("CJVdate");
               //   System.out.println("CJVdate "+CJVdate);
              }
              catch(NumberFormatException e)
              {
                  System.out.println((new StringBuilder()).append("exception").append(e).toString());
              }
           //   System.out.println((new StringBuilder()).append("CJVdate ").append(CJVdate).toString());
              
       
//             String sql2="                     " +
//             "update fas_journal_master         \n" + 
//             "set centage_avail='N'              \n" +              
//             "where accounting_unit_id=?  and accounting_for_office_id =?    \n" + 
//             "and cjv_no = ? and to_date(cjv_date,'dd-mm-yy')= to_date(?,'dd-mm-yy') \n";
//       System.out.println("sql2 :::;;"+sql2);
//             con.setAutoCommit(false);
//       
//             ps1=con.prepareStatement(sql2);
//             ps1.setInt(1,cmbAcc_UnitCode); 
//             ps1.setInt(2,cmbOffice_code); 
//             ps1.setInt(3,CJVno); 
//             ps1.setString(4,CJVdate); 
//             ps1.executeUpdate();
             
             String sql3="" +
             "update fas_journal_master             \n" + 
             "set journal_status='C'                \n" + 
             "where accounting_unit_id= ?           \n" + 
             "and accounting_for_office_id= ?       \n" + 
             "and cashbook_month=?                  \n" + 
             "and cashbook_year= ?                  \n" + 
             "and voucher_no=? and to_date(voucher_date,'dd-mm-yy')= to_date(?,'dd-mm-yy')      \n";
             
             ps2=con.prepareStatement(sql3);
             ps2.setInt(1,cmbAcc_UnitCode); 
             ps2.setInt(2,cmbOffice_code); 
             ps2.setInt(3,txtCB_Month); 
             ps2.setInt(4,txtCB_Year); 
             ps2.setInt(5,CJVno); 
             ps2.setString(6,CJVdate); 
             
             ps2.executeUpdate();
             
             con.commit();
             con.setAutoCommit(true);
             
             xml=xml+"<flag>success</flag>";   
             
          }
          catch(Exception e) 
          {
              System.out.println(e);
              xml=xml+"<flag>failure</flag>";
          }
          
          xml=xml+"</response>";    
          System.out.println(xml);
          out.println(xml);
           
       }
       
        
    }

    public View_Centage_AfterPosting()
    {
    }
}

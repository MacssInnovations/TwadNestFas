package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Journal_ListAll extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        /**
     * Session Checking
     */
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
        * Variables Declaration
        */
        Connection con = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        PreparedStatement ps4 = null;


        String strType = "";
        String xml = "<response>";

        /**
        * Database Connection
        */
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
            System.out.println("Exception in opening connection :" + e);

        }


        /**
         * Set Content Type
         */
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        /**
         * Get Command Parameter
         */
        try {
            strType = request.getParameter("Command");
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * Variables Declaration
         */
        int txtCB_Year = 0;
        int txtCB_Month = 0;
        int cmbAcc_UnitCode = 0;
        int cmbOffice_code = 0;
        int txtsupplement_no = 0;
        String cmbJrnltype="";

        Date txtFrom_date = null;
        Date txtTo_date = null;
        Calendar c;
        String sql = "";
        String txtCreat_By_Module = "";
        String cmbStatus = "";

        /** Get Acounting Unit ID */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /** Get Office Code */
        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }


        /** Cashbook Year */
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

        /** Cashbook Month */
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));

        /** Created By Module Either GJV or LJV */
        txtCreat_By_Module = request.getParameter("txtCreat_By_Module");

        /** Get Voucher Status either Alive or Cancel */
        cmbStatus = request.getParameter("cmbStatus");
        
        
        if(txtCreat_By_Module.equalsIgnoreCase("GJJV"))
        {
        cmbJrnltype = request.getParameter("cmbJrnltype");
        System.out.println("Journal type selected :::::"+cmbJrnltype);
        }


        /** Get Supplement Voucher Number */

        try {
            txtsupplement_no =
                    Integer.parseInt(request.getParameter("txtsupplement_no"));
        } catch (Exception e) {
            System.out.println("Error getting Supplement Number..");
        }


        if (strType.equalsIgnoreCase("searchByMonth")) {
        	int type=Integer.parseInt(request.getParameter("type"));
        	System.out.println("Type for Converstion 1 for convert "+type);
            String sql_del =
                " delete from FAS_CONVERT_GJV_TO_LJV_TMP where       \n" +
                " ACCOUNTING_UNIT_ID= ? and                                        \n" +
                " ACCOUNTING_FOR_OFFICE_ID = ? and                                 \n" +
                " CASHBOOK_YEAR = ? and                                            \n" +
                " CASHBOOK_MONTH = ?                      \n" + "  ";

            try {
                ps = con.prepareStatement(sql_del);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.execute();

            } catch (Exception e) {
                System.out.println("Cant Delete ---------------------------------------->>>" +
                                   e);
            }


            xml = "<response><command>searchByMonth</command>";

            sql =
 "SELECT VOUCHER_NO,\n" + "  TO_CHAR(VOUCHER_DATE,'DD/MM/YYYY') AS rec_date,\n" +
   "  JOURNAL_TYPE_CODE,\n" + "  REMARKS\n" + "FROM FAS_JOURNAL_MASTER\n" +
   "WHERE ACCOUNTING_UNIT_ID    =?\n" + "AND ACCOUNTING_FOR_OFFICE_ID=?\n" +
   "AND CASHBOOK_YEAR           =?\n" + "AND CASHBOOK_MONTH          =?\n" +
   "AND CREATED_BY_MODULE       =?\n" + "AND JOURNAL_STATUS          =?\n";

// added 27/09/2011 for the journal type - cutoff
            if (cmbJrnltype.equalsIgnoreCase("COJ")) {
                sql = sql + "and CB_REF_TYPE = ?\n";
            }

            
            if (txtCreat_By_Module.equalsIgnoreCase("SJV")) {
                sql = sql + "and supplement_no           =?\n";
            }

          /*
           * 
           *   Joan Restrict on 07 OCt 2014
           *   
           *   sql =
 sql + "AND MODE_OF_CREATION      !='I'\n" + "ORDER BY VOUCHER_NO,\n" +
   "  VOUCHER_DATE\n" + "  ";*/
            if(type==1){
            sql =
            		 sql + " AND JOURNAL_TYPE_CODE=53 AND MODE_OF_CREATION   not in ('A','I') \n" + "ORDER BY VOUCHER_NO,\n" +
            		   "  VOUCHER_DATE\n" + "  ";
            }else  if(type==2){
            	sql =
            			 sql + " AND MODE_OF_CREATION      !='I'\n" + "ORDER BY VOUCHER_NO,\n" +
            			   "  VOUCHER_DATE\n" + "  ";
            }

            /*System.out.println(cmbAcc_UnitCode);
            System.out.println(cmbOffice_code);
            System.out.println(txtCB_Year);
            System.out.println(txtCB_Month);
            System.out.println(txtCreat_By_Module);
            System.out.println(cmbStatus);
            System.out.println("sql::::"+sql);*/
            System.out.println("sql::::"+sql);
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, txtCreat_By_Module);
                ps.setString(6, cmbStatus);
              //  System.out.println("cmbStatus"+cmbStatus+":::cmbJrnltype:::"+cmbJrnltype);
                if (cmbJrnltype.equalsIgnoreCase("COJ")) {
              // System.out.println("cmbJrnltype"+cmbJrnltype);
                    ps.setString(7, cmbJrnltype);
                }
                
                if (txtCreat_By_Module.equalsIgnoreCase("SJV")) {
                   // ps.setInt(8, txtsupplement_no);
                    ps.setInt(7, txtsupplement_no);
                }
                //System.out.println("be4 xmlllll");
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";

               // System.out.println("3");

                rs = ps.executeQuery();

              //  System.out.println("4");

                while (rs.next()) {
              //      System.out.println("5");
                    xml = xml + "<leng>";
                    xml = xml + "<no>" + rs.getInt("VOUCHER_NO") + "</no>";
//                    System.out.println("6");
                    int journal_type_code = rs.getInt("JOURNAL_TYPE_CODE");

                    xml =xml + "<typecode>" + journal_type_code + "</typecode>";
                        if (journal_type_code != 0) {
                    ps2 =con.prepareStatement("select JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where JOURNAL_TYPE_CODE=?");
                    ps2.setInt(1, journal_type_code);

                    rs2 = ps2.executeQuery();
//                    System.out.println("chk 2"+rs2);
                    
                            if (rs2.next())
                            {
                         
                               xml =xml + "<typeof>" + rs2.getString("JOURNAL_TYPE_DESC") + "</typeof>";
                            }
                            rs2.close();
                            ps2.close();
                        }
                        else {
                            xml =xml + "<typeof>" + "--"+ "</typeof>";
                        }
                    
                       // System.out.println("xml in type of>>."+xml);
                    
//                    System.out.println("chk 3");
//                    System.out.println(rs.getInt("VOUCHER_NO"));
                    String sub_Qry="";
                    
                    if (txtCreat_By_Module.equalsIgnoreCase("LJV")) {
                    	  xml = xml + "<Mode>"+txtCreat_By_Module+"</Mode>";
                    	 //System.out.println("chk CB REf NO");
                    	sub_Qry="SELECT CB_REF_NO,to_char(CB_REF_DATE,'dd/mm/yyyy') as CB_REF_DATE FROM FAS_JOURNAL_TRANSACTION  WHERE ACCOUNTING_UNIT_ID    =?"+
                                " AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR           =? AND CASHBOOK_MONTH          =?"+
                                " AND VOUCHER_NO              =? AND CR_DR_INDICATOR         ='CR' and (CB_REF_NO <> 0 and CB_REF_NO is not null)";
                   
                    PreparedStatement ps_Ref=con.prepareStatement(sub_Qry);
                    ps_Ref.setInt(1, cmbAcc_UnitCode);
                    ps_Ref.setInt(2, cmbOffice_code);
                    ps_Ref.setInt(3, txtCB_Year);
                    ps_Ref.setInt(4, txtCB_Month);
                    ps_Ref.setInt(5, rs.getInt("VOUCHER_NO"));
                    ResultSet rs_Ref=ps_Ref.executeQuery();
                    if(rs_Ref.next()){
                    	   xml = xml + "<CB_REF_NO>" +rs_Ref.getInt("CB_REF_NO")+"</CB_REF_NO>";
                    	   xml = xml + "<CB_REF_DATE>" +rs_Ref.getString("CB_REF_DATE")+"</CB_REF_DATE>";
                    }else{
                    	   xml = xml + "<CB_REF_NO>null</CB_REF_NO>";
                    	   xml = xml + "<CB_REF_DATE>null</CB_REF_DATE>";
                    }
                    }else {
                    	   xml = xml + "<CB_REF_NO>null</CB_REF_NO>";
                    	   xml = xml + "<CB_REF_DATE>null</CB_REF_DATE>";
                    	  xml = xml + "<Mode>"+txtCreat_By_Module+"</Mode>";
                    }
                    ps2 =con.prepareStatement("select  COALESCE(to_char(sum(AMOUNT),'99999999999999.99'),'0.00') as  AMOUNT   from FAS_JOURNAL_TRANSACTION  where ACCOUNTING_UNIT_ID=? and  " +
                      "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and CR_DR_INDICATOR='DR'");

                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, cmbOffice_code);
                    ps2.setInt(3, txtCB_Year);
                    ps2.setInt(4, txtCB_Month);
                    ps2.setInt(5, rs.getInt("VOUCHER_NO"));
                    rs2 = ps2.executeQuery();
                    if (rs2.next()) 
                    {
//                        System.out.println("amoutn...AMOUNT." +
//                                           rs2.getString("AMOUNT"));
                       if (rs2.getString("AMOUNT") != null) {
                            xml = xml + "<Tot_Amt>" + rs2.getString("AMOUNT") + "</Tot_Amt>";
                            //System.out.println("trim(nvl((sum(AMOUNT),'999999999amt99999.99'),'0.00'))");
                        } else {
                            xml = xml + "<Tot_Amt>0.00</Tot_Amt>";
                           // System.out.println("amoutn" +
                             //                  rs2.getString("AMOUNT"));
                        }
                    }

                    xml =
 xml + "<Dateof>" + rs.getString("rec_date") + "</Dateof>";
                    xml =
 xml + "<Remak><![CDATA[" + rs.getString("REMARKS") + "]]></Remak>";

                    xml = xml + "</leng>";
                    count++;

                }
                if (count == 0) {
                    xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
            }

        }
/// Modify on 
        
        
        
        if(strType.equalsIgnoreCase("SLSsearchByMonth"))
        {

            String sql_del =
                "delete from FAS_CONVERT_GJV_TO_LJV_TMP where       \n" +
                " ACCOUNTING_UNIT_ID= ? and                                        \n" +
                " ACCOUNTING_FOR_OFFICE_ID = ? and                                 \n" +
                " CASHBOOK_YEAR = ? and                                            \n" +
                " CASHBOOK_MONTH = ?                      \n" + "  ";

            try {
                ps = con.prepareStatement(sql_del);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.execute();

            } catch (Exception e) {
                System.out.println("Cant Delete ---------------------------------------->>>" +
                                   e);
            }


            xml = "<response><command>searchByMonth</command>";

            sql =
 "SELECT VOUCHER_NO,\n" + "  TO_CHAR(VOUCHER_DATE,'DD/MM/YYYY') AS rec_date,\n" +
   "  JOURNAL_TYPE_CODE,\n" + "  REMARKS\n" + " FROM FAS_JOURNAL_MASTER_SLS" +
   " WHERE ACCOUNTING_UNIT_ID    =?\n" + " AND ACCOUNTING_FOR_OFFICE_ID=?\n" +
   " AND CASHBOOK_YEAR           =?\n" + " AND CASHBOOK_MONTH          =?\n" +
   "  AND CREATED_BY_MODULE       =?\n" + " AND JOURNAL_STATUS          =?\n"+ " and supplement_no           =?\n"
   + " and JOURNAL_TYPE_CODE           =?\n";
            


          
            sql=sql + " AND MODE_OF_CREATION      ='A'" + " ORDER BY VOUCHER_NO," +
   "  VOUCHER_DATE\n" + "  ";

         
System.out.println("sql::::"+sql);
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setString(5, "GJV");
                ps.setString(6, "L");
                ps.setInt(7, 0);
                ps.setInt(8, 108);
System.out.println("be4 xmlllll");
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";


                rs = ps.executeQuery();

              

                while (rs.next()) {
                  
                    xml = xml + "<leng>";
                    xml = xml + "<no>" + rs.getInt("VOUCHER_NO") + "</no>";
//                    System.out.println("6");
                    int journal_type_code = rs.getInt("JOURNAL_TYPE_CODE");

                    xml =xml + "<typecode>" + journal_type_code + "</typecode>";
                        if (journal_type_code != 0) {
                    ps2 =con.prepareStatement("select JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where JOURNAL_TYPE_CODE=?");
                    ps2.setInt(1, journal_type_code);

                    rs2 = ps2.executeQuery();
                   // System.out.println("chk 2"+rs2);
                    
                            if (rs2.next())
                            {
                         
                               xml =xml + "<typeof>" + rs2.getString("JOURNAL_TYPE_DESC") + "</typeof>";
                            }
                            rs2.close();
                            ps2.close();
                        }
                        else {
                            xml =xml + "<typeof>" + "--"+ "</typeof>";
                        }
                    
                       // System.out.println("xml in type of>>."+xml);
                    
//                    System.out.println("chk 3");
//                    System.out.println(rs.getInt("VOUCHER_NO"));
                    ps2 =con.prepareStatement("select  COALESCE(to_char(sum(AMOUNT),'99999999999999.99'),'0.00') as  AMOUNT   from FAS_JOURNAL_TRANSACTION_sls  where ACCOUNTING_UNIT_ID=? and  " +
                      "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and CR_DR_INDICATOR='DR'");

                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, cmbOffice_code);
                    ps2.setInt(3, txtCB_Year);
                    ps2.setInt(4, txtCB_Month);
                    ps2.setInt(5, rs.getInt("VOUCHER_NO"));
                    rs2 = ps2.executeQuery();
                    if (rs2.next()) 
                    {
                        System.out.println("amoutn...AMOUNT." +
                                           rs2.getString("AMOUNT"));
                        if (rs2.getString("AMOUNT") != null) {
                            xml = xml + "<Tot_Amt>" + rs2.getString("AMOUNT") + "</Tot_Amt>";
                            System.out.println("trim(nvl((sum(AMOUNT),'999999999amt99999.99'),'0.00'))");
                        } else {
                            xml = xml + "<Tot_Amt>0.00</Tot_Amt>";
                            System.out.println("amoutn" +
                                               rs2.getString("AMOUNT"));
                        }
                    }

                    xml =
 xml + "<Dateof>" + rs.getString("rec_date") + "</Dateof>";
                    xml =
 xml + "<Remak><![CDATA[" + rs.getString("REMARKS") + "]]></Remak>";

                    xml = xml + "</leng>";
                    count++;

                }
                if (count == 0) {
                    xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
            }

        
        }
/// Statert searchByDate SSL
        if(strType.equalsIgnoreCase("SSLsearchByDate")){

            xml = "<response><command>searchByDate</command>";
            System.out.println("here " +
                               strType.equalsIgnoreCase("searchByDate"));

            String[] sd = request.getParameter("txtFrom_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtFrom_date = new Date(d.getTime());
            System.out.println("from_date " + txtFrom_date);

            sd = request.getParameter("txtTo_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            txtTo_date = new Date(d.getTime());
            System.out.println("txtTo_date " + txtTo_date);

            sql =
 "\n" + "SELECT VOUCHER_NO,\n" + "  TO_CHAR(VOUCHER_DATE,'DD/MM/YYYY') AS rec_date,\n" +
   "  JOURNAL_TYPE_CODE,\n" + "  REMARKS\n" + " FROM FAS_JOURNAL_MASTER_sls \n" +
   " WHERE ACCOUNTING_UNIT_ID    =?\n" + " AND ACCOUNTING_FOR_OFFICE_ID=?\n" +
   " AND CASHBOOK_YEAR           =?\n" + " AND CASHBOOK_MONTH          =?\n" +
   " AND VOUCHER_DATE           >=?\n" + " AND VOUCHER_DATE           <=?\n" +
   " AND CREATED_BY_MODULE       =?\n" + " AND JOURNAL_STATUS          =?\n"+" and supplement_no           =?   \n"
   +" AND JOURNAL_TYPE_CODE           =?   \n";


         

            sql =
 sql + "AND MODE_OF_CREATION       ='A'   \n" + "ORDER BY VOUCHER_NO,\n" +
   "  VOUCHER_DATE";


            try {

                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                ps.setDate(5, txtFrom_date);
                ps.setDate(6, txtTo_date);
                ps.setString(7, "GJV");
                ps.setString(8, "L");
                ps.setInt(9, 0);
                ps.setInt(10, 108);


                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml = xml + "<no>" + rs.getInt("VOUCHER_NO") + "</no>";
                    ps2 =
 con.prepareStatement("select JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where JOURNAL_TYPE_CODE=?");
                    ps2.setInt(1, rs.getInt("JOURNAL_TYPE_CODE"));
                    rs2 = ps2.executeQuery();
                    if (rs2.next())
                        xml =
 xml + "<typeof>" + rs2.getString("JOURNAL_TYPE_DESC") + "</typeof>";
                    rs2.close();
                    ps2.close();
                    ps2 =
 con.prepareStatement("select COALESCE(to_char(sum(AMOUNT),'99999999999999.99'),'0.00') as  AMOUNT   from FAS_JOURNAL_TRANSACTION_sls  where ACCOUNTING_UNIT_ID=? and  " +
                      "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and CR_DR_INDICATOR='DR'");

                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, cmbOffice_code);
                    ps2.setInt(3, txtCB_Year);
                    ps2.setInt(4, txtCB_Month);
                    ps2.setInt(5, rs.getInt("VOUCHER_NO"));
                    rs2 = ps2.executeQuery();

                    xml =
 xml + "<Dateof>" + rs.getString("rec_date") + "</Dateof>";
                    xml =
 xml + "<Remak><![CDATA[" + rs.getString("REMARKS") + "]]></Remak>";
                    if (rs2.next())
                        xml =
 xml + "<Tot_Amt>" + rs2.getString("AMOUNT") + "</Tot_Amt>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
                }

            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
            }
        
        }
        
        
        ////
        System.out.println("here " + strType.equalsIgnoreCase("searchByDate"));
        if (strType.equalsIgnoreCase("searchByDate")) {
            xml = "<response><command>searchByDate</command>";
            System.out.println("here " +
                               strType.equalsIgnoreCase("searchByDate"));

            String[] sd = request.getParameter("txtFrom_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtFrom_date = new Date(d.getTime());
            System.out.println("from_date " + txtFrom_date);

            sd = request.getParameter("txtTo_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            txtTo_date = new Date(d.getTime());
            System.out.println("txtTo_date " + txtTo_date);

            sql =
 "\n" + "SELECT VOUCHER_NO,\n" + "  TO_CHAR(VOUCHER_DATE,'DD/MM/YYYY') AS rec_date,\n" +
   "  JOURNAL_TYPE_CODE,\n" + "  REMARKS\n" + "FROM FAS_JOURNAL_MASTER\n" +
   "WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID=" +cmbOffice_code+" AND" +
 //  	" CASHBOOK_YEAR           ="+txtCB_Year+ "AND CASHBOOK_MONTH          =" +txtCB_Month+
   " VOUCHER_DATE           >=? AND VOUCHER_DATE           <=? AND CREATED_BY_MODULE       ='"+txtCreat_By_Module + "' AND JOURNAL_STATUS          ='"+cmbStatus+"'";

// added 27/09/2011 for the journal type - cutoff
            if (cmbJrnltype.equalsIgnoreCase("COJ")) {
                sql = sql + "and CB_REF_TYPE = '"+cmbJrnltype+"'";
            }
            if (txtCreat_By_Module.equalsIgnoreCase("SJV")) {
                sql = sql + "and supplement_no           ="+txtsupplement_no;
            }

            sql =
 sql + "AND MODE_OF_CREATION!       ='I'   \n" + "ORDER BY VOUCHER_NO,\n" +
   "  VOUCHER_DATE";

            System.out.println(sql);
            try {

                int count = 0;
                ps = con.prepareStatement(sql);
//                ps.setInt(1, cmbAcc_UnitCode);
//                ps.setInt(2, cmbOffice_code);
//                ps.setInt(3, txtCB_Year);
//                ps.setInt(4, txtCB_Month);
                ps.setDate(1, txtFrom_date);
                ps.setDate(2, txtTo_date);
            //    ps.setString(1, txtCreat_By_Module);
           //     ps.setString(8, cmbStatus);

              /*  if (cmbJrnltype.equalsIgnoreCase("COJ")) {
                    ps.setString(2, cmbJrnltype);
                }
                if (txtCreat_By_Module.equalsIgnoreCase("SJV")) {
                    ps.setInt(3, txtsupplement_no);
                } */


                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<leng>";
                    xml = xml + "<no>" + rs.getInt("VOUCHER_NO") + "</no>";
                    xml =xml + "<typecode>" + rs.getInt("JOURNAL_TYPE_CODE") + "</typecode>";
                    ps2 =
 con.prepareStatement("select JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where JOURNAL_TYPE_CODE=?");
                    ps2.setInt(1, rs.getInt("JOURNAL_TYPE_CODE"));
                    rs2 = ps2.executeQuery();
                    if (rs2.next())
                        xml =
 xml + "<typeof>" + rs2.getString("JOURNAL_TYPE_DESC") + "</typeof>";
                    rs2.close();
                    ps2.close();
                    String 	sub_Qry="";
                    if (txtCreat_By_Module.equalsIgnoreCase("LJV")) {
                    	   xml = xml + "<Mode>" +txtCreat_By_Module+"</Mode>";
                   	// System.out.println("chk CB REf NO   ....    "+txtCreat_By_Module);
                   	sub_Qry="SELECT CB_REF_NO,to_char(CB_REF_DATE,'dd/Mon/yyyy') as CB_REF_DATE FROM FAS_JOURNAL_TRANSACTION  WHERE ACCOUNTING_UNIT_ID    =?"+
                               " AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR           =? AND CASHBOOK_MONTH          =?"+
                               " AND VOUCHER_NO              =? AND CR_DR_INDICATOR         ='CR' and (CB_REF_NO <> 0 and CB_REF_NO is not null)";
                  
                   PreparedStatement ps_Ref=con.prepareStatement(sub_Qry);
                   ps_Ref.setInt(1, cmbAcc_UnitCode);
                   ps_Ref.setInt(2, cmbOffice_code);
                   ps_Ref.setInt(3, txtCB_Year);
                   ps_Ref.setInt(4, txtCB_Month);
                   ps_Ref.setInt(5, rs.getInt("VOUCHER_NO"));
                   ResultSet rs_Ref=ps_Ref.executeQuery();
                   if(rs_Ref.next()){
                   	  
                	   xml = xml + "<CB_REF_NO>" +rs_Ref.getInt("CB_REF_NO")+"</CB_REF_NO>";
                   	   xml = xml + "<CB_REF_DATE>" +rs_Ref.getString("CB_REF_DATE")+"</CB_REF_DATE>";
                   }
                   }else {
                	   xml = xml + "<Mode>" +txtCreat_By_Module+"</Mode>";
                   }
                    ps2 =
 con.prepareStatement("select COALESCE(to_char(sum(AMOUNT),'99999999999999.99'),'0.00') as  AMOUNT   from FAS_JOURNAL_TRANSACTION  where ACCOUNTING_UNIT_ID=? and  " +
                      "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and CR_DR_INDICATOR='DR'");

                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, cmbOffice_code);
                    ps2.setInt(3, txtCB_Year);
                    ps2.setInt(4, txtCB_Month);
                    ps2.setInt(5, rs.getInt("VOUCHER_NO"));
                    rs2 = ps2.executeQuery();

                    xml =
 xml + "<Dateof>" + rs.getString("rec_date") + "</Dateof>";
                    xml =
 xml + "<Remak><![CDATA[" + rs.getString("REMARKS") + "]]></Remak>";
                    if (rs2.next())
                        xml =
 xml + "<Tot_Amt>" + rs2.getString("AMOUNT") + "</Tot_Amt>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
                }

            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByDate</command><flag>failure</flag>";
            }
        }


        sql =
 "select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where category='L'  order by CATEGORY,JOURNAL_TYPE_DESC";
        try {
            ps3 = con.prepareStatement(sql);
            rs3 = ps3.executeQuery();
            while (rs3.next()) {
                xml = xml + "<leng_2>";
                xml =
 xml + "<journal_type_code>" + rs3.getString("JOURNAL_TYPE_CODE") +
   "</journal_type_code>";
                xml =
 xml + "<journal_type_desc>" + rs3.getString("JOURNAL_TYPE_DESC") +
   "</journal_type_desc>";
                xml = xml + "</leng_2>";
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);

    }
}

package Servlets.FAS.FAS1.FundTransferSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

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

public class Fund_Transfer_Create_byOffice extends HttpServlet {
    private static  String CONTENT_TYPE =
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
        ResultSet rsoffice = null, rsbank = null;
        PreparedStatement psbank = null;
        String xml = "";
        PrintWriter out = response.getWriter();
        String strCommand = "";


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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
        }

        /**
        * Get Command Parameter
        */
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /**
         * Load HO Bank Details
         */
        if (strCommand.equalsIgnoreCase("unspent_OR_col_based_bank")) {
             CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            response.setHeader("Cache-Control", "no-cache");

            xml = "<response><command>unspent_OR_col_based_bank</command>";

            try {
                int unitID = 0;
                unitID = Integer.parseInt(request.getParameter("unitID"));
                String txtModule_Type = request.getParameter("txtModule_Type");
                String cr_dr_indi = request.getParameter("cr_dr_indi");
                String unspent_OR_col = request.getParameter("unspent_OR_col");
                int main_AHCODE =Integer.parseInt(request.getParameter("main_AHCODE"));
                String sql_bank = "";

                System.out.println("txtModule_Type.." + txtModule_Type);
                System.out.println("cr_dr_indi.." + cr_dr_indi);
                System.out.println("unspent_OR_col.." + unspent_OR_col);
                System.out.println("here xml" + xml);
                System.out.println("b4 bank fetch");

                if (unspent_OR_col.equalsIgnoreCase("COL")||unspent_OR_col.equalsIgnoreCase("FDW")) {
                    System.out.println("collection");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='COL'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("WATCHARGEREV_Hogenakkal"))
                {
                	sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='WATCHARGEREV_Hogenakkal'";
                	
                }
                else if(unspent_OR_col.equalsIgnoreCase("WATCHARGEREV"))
                {
                	System.out.println("WATCHARGEREV");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='WATCHARGEREV'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("NONWATCHARGEREV"))
                {
                	System.out.println("NONWATCHARGEREV");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='NONWATCHARGEREV'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("LB100PCNTCONTRIB"))
                {
                	System.out.println("LB100PCNTCONTRIB");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='LB100PCNTCONTRIB'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("UIDDSMT"))
                {
                	System.out.println("UIDDSMT");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='UIDDSMT'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("JICA"))
                {
                	System.out.println("JICA");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='JICA'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("KFW"))
                {
                	System.out.println("KFW");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='KFW'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("FieldKit"))
                {
                	System.out.println("FieldKit");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE like::varchar '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='FieldKit'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("FDW from Collection"))
                {
                	System.out.println("FDW from Collection");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='FDW from Collection'";
                }
                else if(unspent_OR_col.equalsIgnoreCase("Security Deposit"))
                {
                	System.out.println("Security Deposit");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='Security Deposit'";
                }
                
                
                
                else if (unspent_OR_col.equalsIgnoreCase("OPR")) {
                    System.out.println("Operation");
                    sql_bank =
                            "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                            " and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='OPR'";
                }else if(unspent_OR_col.equalsIgnoreCase("NRDWP_Main")) {
                	System.out.println(unspent_OR_col);
                	 sql_bank ="SELECT curr.BANK_ID, " +
                	 "  curr.AC_OPERATIONAL_MODE_ID, " +
                	 "  curr.BRANCH_ID, " +
                	 "  curr.BANK_AC_NO, " +
                	 "  curr.AC_HEAD_CODE, " +
                	 "  bk.BANK_NAME " +
                	 "  || '-' " +
                	 "  || br.BRANCH_NAME " +
                	 "  ||'-' " +
                	 "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city " +
                	 " FROM FAS_OFFICE_BANK_AC_CURRENT curr, " +
                	 "  FAS_MST_BANK_BRANCHES br , " +
                	 "  FAS_MST_BANKS bk " +
                	 " WHERE curr.STATUS          ='Y' " +
                	 " AND curr.ACCOUNTING_UNIT_ID=? " +
                	 " AND curr.MODULE_ID         =? " +
                	 " AND curr.CR_DR_TYPE        =? " +
                	 " AND curr.SL_NO=2 " +// changed as 3 from 2 on 13/04/2016
                	// " AND curr.AC_HEAD_CODE LIKE '%152' " +
                	// "  --AND curr.SL_NO                 =1 " +
                	 " AND curr.BANK_ID   =br.BANK_ID " +
                	 " AND curr.BRANCH_ID =br.BRANCH_ID " +
                	 " AND br.BANK_ID     =bk.BANK_ID " +
                	 " AND curr.AC_OPERATIONAL_MODE_ID LIKE 'OPR-NRDWP-Main'";
                	
                }else if(unspent_OR_col.equalsIgnoreCase("NRDWP_Support")) {
                	System.out.println(unspent_OR_col);
               	 sql_bank ="SELECT curr.BANK_ID, " +
               	 "  curr.AC_OPERATIONAL_MODE_ID, " +
               	 "  curr.BRANCH_ID, " +
               	 "  curr.BANK_AC_NO, " +
               	 "  curr.AC_HEAD_CODE, " +
               	 "  bk.BANK_NAME " +
               	 "  || '-' " +
               	 "  || br.BRANCH_NAME " +
               	 "  ||'-' " +
               	 "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city " +
               	 " FROM FAS_OFFICE_BANK_AC_CURRENT curr, " +
               	 "  FAS_MST_BANK_BRANCHES br , " +
               	 "  FAS_MST_BANKS bk " +
               	 " WHERE curr.STATUS          ='Y' " +
               	 " AND curr.ACCOUNTING_UNIT_ID=? " +
               	 " AND curr.MODULE_ID         =? " +
               	 " AND curr.CR_DR_TYPE        =? " +
               	 " AND curr.SL_NO=2 "+
               	// " AND curr.AC_HEAD_CODE LIKE '%152' " +
               	// "  --AND curr.SL_NO                 =1 " +
               	 " AND curr.BANK_ID   =br.BANK_ID " +
               	 " AND curr.BRANCH_ID =br.BRANCH_ID " +
               	 " AND br.BANK_ID     =bk.BANK_ID " +
               	 " AND curr.AC_OPERATIONAL_MODE_ID LIKE 'OPR-NRDWP-Support'";
               	
               }else if(unspent_OR_col.equalsIgnoreCase("NRDWP-WQM-SP")){
            		System.out.println(unspent_OR_col);
                  	 sql_bank ="SELECT curr.BANK_ID, " +
                  	 "  curr.AC_OPERATIONAL_MODE_ID, " +
                  	 "  curr.BRANCH_ID, " +
                  	 "  curr.BANK_AC_NO, " +
                  	 "  curr.AC_HEAD_CODE, " +
                  	 "  bk.BANK_NAME " +
                  	 "  || '-' " +
                  	 "  || br.BRANCH_NAME " +
                  	 "  ||'-' " +
                  	 "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city " +
                  	 " FROM FAS_OFFICE_BANK_AC_CURRENT curr, " +
                  	 "  FAS_MST_BANK_BRANCHES br , " +
                  	 "  FAS_MST_BANKS bk " +
                  	 " WHERE curr.STATUS          ='Y' " +
                  	 " AND curr.ACCOUNTING_UNIT_ID=? " +
                  	 " AND curr.MODULE_ID         =? " +
                  	 " AND curr.CR_DR_TYPE        =? " +
                  	 " AND curr.SL_NO=1 "+
                  	// " AND curr.AC_HEAD_CODE LIKE '%152' " +
                  	// "  --AND curr.SL_NO                 =1 " +
                  	 " AND curr.BANK_ID   =br.BANK_ID " +
                  	 " AND curr.BRANCH_ID =br.BRANCH_ID " +
                  	 " AND br.BANK_ID     =bk.BANK_ID " +
                  	 " AND curr.AC_OPERATIONAL_MODE_ID LIKE 'NRDWP-WQM-SP'";
               }else if(unspent_OR_col.equalsIgnoreCase("NRDWP_Main1")) {
               	System.out.println(unspent_OR_col);
           	 sql_bank ="SELECT curr.BANK_ID, " +
           	 "  curr.AC_OPERATIONAL_MODE_ID, " +
           	 "  curr.BRANCH_ID, " +
           	 "  curr.BANK_AC_NO, " +
           	 "  curr.AC_HEAD_CODE, " +
           	 "  bk.BANK_NAME " +
           	 "  || '-' " +
           	 "  || br.BRANCH_NAME " +
           	 "  ||'-' " +
           	 "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city " +
           	 " FROM FAS_OFFICE_BANK_AC_CURRENT curr, " +
           	 "  FAS_MST_BANK_BRANCHES br , " +
           	 "  FAS_MST_BANKS bk " +
           	 " WHERE curr.STATUS          ='Y' " +
           	 " AND curr.ACCOUNTING_UNIT_ID=? " +
           	 " AND curr.MODULE_ID         =? " +
           	 " AND curr.CR_DR_TYPE        =? " +
             " AND curr.SL_NO=1 "+ //changed as 2 on 13/04/2016
           	// " AND curr.SL_NO=3 "+
           	// " AND curr.AC_HEAD_CODE LIKE '%152' " +
           	// "  --AND curr.SL_NO                 =1 " +
           	 " AND curr.BANK_ID   =br.BANK_ID " +
           	 " AND curr.BRANCH_ID =br.BRANCH_ID " +
           	 " AND br.BANK_ID     =bk.BANK_ID " +
           	 " AND curr.AC_OPERATIONAL_MODE_ID LIKE 'OPR-NRDWP-Main'";
           	
           }else if(unspent_OR_col.equalsIgnoreCase("NRDWP_Support1")) {
           	System.out.println(unspent_OR_col);
          	 sql_bank ="SELECT curr.BANK_ID, " +
          	 "  curr.AC_OPERATIONAL_MODE_ID, " +
          	 "  curr.BRANCH_ID, " +
          	 "  curr.BANK_AC_NO, " +
          	 "  curr.AC_HEAD_CODE, " +
          	 "  bk.BANK_NAME " +
          	 "  || '-' " +
          	 "  || br.BRANCH_NAME " +
          	 "  ||'-' " +
          	 "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city " +
          	 " FROM FAS_OFFICE_BANK_AC_CURRENT curr, " +
          	 "  FAS_MST_BANK_BRANCHES br , " +
          	 "  FAS_MST_BANKS bk " +
          	 " WHERE curr.STATUS          ='Y' " +
          	 " AND curr.ACCOUNTING_UNIT_ID=? " +
          	 " AND curr.MODULE_ID         =? " +
          	 " AND curr.CR_DR_TYPE        =? " +
          	// " AND curr.SL_NO=3 "+
          	// " AND curr.AC_HEAD_CODE LIKE '%152' " +
          	 "  AND curr.SL_NO                 =1 " +
          	 " AND curr.BANK_ID   =br.BANK_ID " +
          	 " AND curr.BRANCH_ID =br.BRANCH_ID " +
          	 " AND br.BANK_ID     =bk.BANK_ID " +
          	 " AND curr.AC_OPERATIONAL_MODE_ID LIKE 'OPR-NRDWP-Support'";
          	
          }else if(unspent_OR_col.equalsIgnoreCase("NRDWP_Calamity")) {
          	System.out.println(unspent_OR_col);
          	 sql_bank ="SELECT curr.BANK_ID, " +
          	 "  curr.AC_OPERATIONAL_MODE_ID, " +
          	 "  curr.BRANCH_ID, " +
          	 "  curr.BANK_AC_NO, " +
          	 "  curr.AC_HEAD_CODE, " +
          	 "  bk.BANK_NAME " +
          	 "  || '-' " +
          	 "  || br.BRANCH_NAME " +
          	 "  ||'-' " +
          	 "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city " +
          	 " FROM FAS_OFFICE_BANK_AC_CURRENT curr, " +
          	 "  FAS_MST_BANK_BRANCHES br , " +
          	 "  FAS_MST_BANKS bk " +
          	 " WHERE curr.STATUS          ='Y' " +
          	 " AND curr.ACCOUNTING_UNIT_ID=? " +
          	 " AND curr.MODULE_ID         =? " +
          	 " AND curr.CR_DR_TYPE        =? " +
          	" AND curr.SL_NO=1 "+
          	 " AND curr.BANK_ID   =br.BANK_ID " +
          	 " AND curr.BRANCH_ID =br.BRANCH_ID " +
          	 " AND br.BANK_ID     =bk.BANK_ID " +
          	 " AND curr.AC_OPERATIONAL_MODE_ID LIKE 'OPR-NRDWP-Calamity'";
          	
          }else if(unspent_OR_col.equalsIgnoreCase("NRDWP_AEJE")) {
            	System.out.println(unspent_OR_col);
             	 sql_bank ="SELECT curr.BANK_ID, " +
             	 "  curr.AC_OPERATIONAL_MODE_ID, " +
             	 "  curr.BRANCH_ID, " +
             	 "  curr.BANK_AC_NO, " +
             	 "  curr.AC_HEAD_CODE, " +
             	 "  bk.BANK_NAME " +
             	 "  || '-' " +
             	 "  || br.BRANCH_NAME " +
             	 "  ||'-' " +
             	 "  || coalesce(br.CITY_TOWN_NAME,'') AS bk_br_city " +
             	 " FROM FAS_OFFICE_BANK_AC_CURRENT curr, " +
             	 "  FAS_MST_BANK_BRANCHES br , " +
             	 "  FAS_MST_BANKS bk " +
             	 " WHERE curr.STATUS          ='Y' " +
             	 " AND curr.ACCOUNTING_UNIT_ID=? " +
             	 " AND curr.MODULE_ID         =? " +
             	 " AND curr.CR_DR_TYPE        =? " +
             	" AND curr.SL_NO=1 "+
             	 " AND curr.BANK_ID   =br.BANK_ID " +
             	 " AND curr.BRANCH_ID =br.BRANCH_ID " +
             	 " AND br.BANK_ID     =bk.BANK_ID " +
             	 " AND curr.AC_OPERATIONAL_MODE_ID LIKE 'OPR-NRDWP-AEJE'";
             	
             }
                // here SL_NO=1 means that DEFAULT account number for that unit ..
                System.out.println("Query for bank DR***"+sql_bank+" "+unitID);
                
                System.out.println("select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                            " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID="+unitID+" and curr.MODULE_ID='"+txtModule_Type+"' and curr.CR_DR_TYPE= '"+cr_dr_indi +
                            "' and curr.AC_HEAD_CODE::varchar like '" + main_AHCODE +
                            "%' " +
                            " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='LB100PCNTCONTRIB'");
                
                
                psbank = con.prepareStatement(sql_bank);

                psbank.setInt(1, unitID);
                psbank.setString(2, txtModule_Type);
                psbank.setString(3, cr_dr_indi);
                rsbank = psbank.executeQuery();

                if (rsbank.next()) {
                    xml = xml + "<flag>success</flag>";
                    System.out.println("inside if rsbank");
//System.out.println("welcome -----");
                    xml =
 xml + "<AC_HEAD_CODE>" + rsbank.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
                    xml =
 xml + "<BANK_ID>" + rsbank.getInt("BANK_ID") + "</BANK_ID>";
                    xml =
 xml + "<BRANCH_ID>" + rsbank.getInt("BRANCH_ID") + "</BRANCH_ID>";
                    xml =
 xml + "<BANK_AC_NO>" + rsbank.getLong("BANK_AC_NO") + "</BANK_AC_NO>";
                    xml =
 xml + "<bk_br_city>" + rsbank.getString("bk_br_city") + "</bk_br_city>";
                } else{
                    xml = xml + "<flag>failure_bank</flag>";
                }

            } catch (Exception e1) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Exception handling.." + e1);
            }
            xml = xml + "</response>";
            System.out.println("xml..bank unspent-- " + xml);
            out.println(xml);
        }


        /**
         * Load Office Bank Details
         */

        else if (strCommand.equalsIgnoreCase("Load_Office_Bank_Details")) {
             CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            response.setHeader("Cache-Control", "no-cache");

            xml = "<response><command>unspent_OR_col_based_office</command>";

            try {
                int accounting_unit_id =
                    Integer.parseInt(request.getParameter("unitID"));
                String unspent_OR_col = request.getParameter("unspent_OR_col");
                xml=xml+"<type>"+unspent_OR_col+"</type>";
                String sql_office = "";
                int c=0;
               // System.out.println("unspent_OR_col "+unspent_OR_col);
                System.out.println("accounting_unit_id============>"+accounting_unit_id);
                System.out.println("unspent_OR_col============>"+unspent_OR_col);
               
                
                if (unspent_OR_col.equalsIgnoreCase("COL")||unspent_OR_col.equalsIgnoreCase("WATCHARGEREV")||unspent_OR_col.equalsIgnoreCase("WATCHARGEREV_Hogenakkal")
                		||unspent_OR_col.equalsIgnoreCase("NONWATCHARGEREV")||unspent_OR_col.equalsIgnoreCase("LB100PCNTCONTRIB")||
                		unspent_OR_col.equalsIgnoreCase("UIDDSMT")||unspent_OR_col.equalsIgnoreCase("JICA")||unspent_OR_col.equalsIgnoreCase("KFW")
                		||unspent_OR_col.equalsIgnoreCase("FieldKit")||unspent_OR_col.equalsIgnoreCase("FDW from Collection")||unspent_OR_col.equalsIgnoreCase("Security Deposit")) 
                {
                   // System.out.println("collection");

                    sql_office =
                            "select                      \n" + "       curr.BANK_ID,                   \n" +
                            "       curr.BRANCH_ID,                 \n" +
                            "       curr.BANK_AC_NO,                \n" +
                            "       curr.AC_HEAD_CODE,              \n" +
                            "       curr.AC_OPERATIONAL_MODE_ID,    \n" +
                            "       substring(curr.AC_HEAD_CODE::varchar,1,4) as main_AHCODE,                                       \n" +
                            "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city      \n" +
                            "                                            \n" +
                            "from                                        \n" +
                            "       FAS_OFFICE_BANK_AC_CURRENT curr,     \n" +
                            "       FAS_MST_BANK_BRANCHES br ,           \n" +
                            "       FAS_MST_BANKS bk                     \n" +
                            "                                            \n" +
                            "where                                       \n" +
                            "       curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and        \n" +
                            "       curr.MODULE_ID='MF015' and           \n" +
                            "       curr.CR_DR_TYPE='CR' and             \n" +
                            "       curr.SL_NO=1 and                     \n" +
                            "       curr.BANK_ID=br.BANK_ID and          \n" +
                            "       curr.BRANCH_ID=br.BRANCH_ID and      \n" +
                            "       br.BANK_ID=bk.BANK_ID                \n" +
                            "       and curr.AC_OPERATIONAL_MODE_ID='COL'   \n" +
                            "       ";
                    

                } else if (unspent_OR_col.equalsIgnoreCase("OPR")) {
                 //   System.out.println("Operation");

                    sql_office =
                            "select                          \n" + "       curr.BANK_ID,                       \n" +
                            "       curr.BRANCH_ID,                     \n" +
                            "       curr.BANK_AC_NO,                    \n" +
                            "       curr.AC_HEAD_CODE,                  \n" +
                            "       curr.AC_OPERATIONAL_MODE_ID,        \n" +
                            "       substring(curr.AC_HEAD_CODE:varchar,1,4) as main_AHCODE,                                       \n" +
                            "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city      \n" +
                            "                                           \n" +
                            "from                                       \n" +
                            "       FAS_OFFICE_BANK_AC_CURRENT curr,    \n" +
                            "       FAS_MST_BANK_BRANCHES br ,          \n" +
                            "       FAS_MST_BANKS bk                    \n" +
                            "                                           \n" +
                            "where                                      \n" +
                            "       curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and       \n" +
                            "       curr.MODULE_ID='MF015' and          \n" +
                            "       curr.CR_DR_TYPE='CR' and            \n" +
                            "       curr.SL_NO=1 and                    \n" +
                            "       curr.BANK_ID=br.BANK_ID and         \n" +
                            "       curr.BRANCH_ID=br.BRANCH_ID and     \n" +
                            "       br.BANK_ID=bk.BANK_ID               \n" +
                            "       and curr.AC_OPERATIONAL_MODE_ID='OPR'   \n" +
                            "       ";

                }else if(unspent_OR_col.equalsIgnoreCase("NRDWP-Main"))
                {
                	System.out.println("inside NRDWP-Main");
                	  sql_office =
                          "select                          \n" + "       curr.BANK_ID,                       \n" +
                          "       curr.BRANCH_ID,                     \n" +
                          "       curr.BANK_AC_NO,                    \n" +
                          "       curr.AC_HEAD_CODE,                  \n" +
                          "       curr.AC_OPERATIONAL_MODE_ID,        \n" +
                          "       substring(curr.AC_HEAD_CODE::varchar,1,4) as main_AHCODE,                                       \n" +
                          "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city      \n" +
                          "                                           \n" +
                          "from                                       \n" +
                          "       FAS_OFFICE_BANK_AC_CURRENT curr,    \n" +
                          "       FAS_MST_BANK_BRANCHES br ,          \n" +
                          "       FAS_MST_BANKS bk                    \n" +
                          "                                           \n" +
                          "where                                      \n" +
                          "       curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and       \n" +
                          "       curr.MODULE_ID='MF015' and          \n" +
                          "       curr.CR_DR_TYPE='CR' and            \n" +
                          "       curr.SL_NO=1 and                    \n" +
                          "       curr.BANK_ID=br.BANK_ID and         \n" +
                          "       curr.BRANCH_ID=br.BRANCH_ID and     \n" +
                          "       br.BANK_ID=bk.BANK_ID               \n" +
                          "       and curr.AC_OPERATIONAL_MODE_ID='OPR-NRDWP-Main'   \n" +
                          "       ";
                }else if(unspent_OR_col.equalsIgnoreCase("NRDWP-Support")){
                	 sql_office =
                         "select                          \n" + "       curr.BANK_ID,                       \n" +
                         "       curr.BRANCH_ID,                     \n" +
                         "       curr.BANK_AC_NO,                    \n" +
                         "       curr.AC_HEAD_CODE,                  \n" +
                         "       curr.AC_OPERATIONAL_MODE_ID,        \n" +
                         "       substring(curr.AC_HEAD_CODE::varchar,1,4) as main_AHCODE,                                       \n" +
                         "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city      \n" +
                         "                                           \n" +
                         "from                                       \n" +
                         "       FAS_OFFICE_BANK_AC_CURRENT curr,    \n" +
                         "       FAS_MST_BANK_BRANCHES br ,          \n" +
                         "       FAS_MST_BANKS bk                    \n" +
                         "                                           \n" +
                         "where                                      \n" +
                         "       curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and       \n" +
                         "       curr.MODULE_ID='MF015' and          \n" +
                         "       curr.CR_DR_TYPE='CR' and            \n" +
                         "       curr.SL_NO=1 and                    \n" +
                         "       curr.BANK_ID=br.BANK_ID and         \n" +
                         "       curr.BRANCH_ID=br.BRANCH_ID and     \n" +
                         "       br.BANK_ID=bk.BANK_ID               \n" +
                         "       and curr.AC_OPERATIONAL_MODE_ID='OPR-NRDWP-Support'   \n" ;
                }else if(unspent_OR_col.equalsIgnoreCase("NRDWP-WQM-SP")){
                	 sql_office =
                         "select                          \n" + "       curr.BANK_ID,                       \n" +
                         "       curr.BRANCH_ID,                     \n" +
                         "       curr.BANK_AC_NO,                    \n" +
                         "       curr.AC_HEAD_CODE,                  \n" +
                         "       curr.AC_OPERATIONAL_MODE_ID,        \n" +
                         "       substring(curr.AC_HEAD_CODE::varchar,1,4) as main_AHCODE,                                       \n" +
                         "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city      \n" +
                         "                                           \n" +
                         "from                                       \n" +
                         "       FAS_OFFICE_BANK_AC_CURRENT curr,    \n" +
                         "       FAS_MST_BANK_BRANCHES br ,          \n" +
                         "       FAS_MST_BANKS bk                    \n" +
                         "                                           \n" +
                         "where                                      \n" +
                         "       curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and       \n" +
                         "       curr.MODULE_ID='MF015' and          \n" +
                         "       curr.CR_DR_TYPE='CR' and            \n" +
                         "       curr.SL_NO=1 and                    \n" +
                         "       curr.BANK_ID=br.BANK_ID and         \n" +
                         "       curr.BRANCH_ID=br.BRANCH_ID and     \n" +
                         "       br.BANK_ID=bk.BANK_ID               \n" +
                         "       and curr.AC_OPERATIONAL_MODE_ID='OPR-NRDWP-Support'   \n" ;
                }else if(unspent_OR_col.equalsIgnoreCase("FDW")){
                	 sql_office =
                         "select                          \n" + "       curr.BANK_ID,                       \n" +
                         "       curr.BRANCH_ID,                     \n" +
                         "       curr.BANK_AC_NO,                    \n" +
                         "       curr.AC_HEAD_CODE,                  \n" +
                         "       curr.AC_OPERATIONAL_MODE_ID,        \n" +
                         "       substring(curr.AC_HEAD_CODE::varchar,1,4) as main_AHCODE,                                       \n" +
                         "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city      \n" +
                         "                                           \n" +
                         "from                                       \n" +
                         "       FAS_OFFICE_BANK_AC_CURRENT curr,    \n" +
                         "       FAS_MST_BANK_BRANCHES br ,          \n" +
                         "       FAS_MST_BANKS bk                    \n" +
                         "                                           \n" +
                         "where                                      \n" +
                         "       curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and       \n" +
                         "       curr.MODULE_ID='MF015' and          \n" +
                         "       curr.CR_DR_TYPE='CR' and            \n" +
                         "       curr.SL_NO=1 and                    \n" +
                         "       curr.BANK_ID=br.BANK_ID and         \n" +
                         "       curr.BRANCH_ID=br.BRANCH_ID and     \n" +
                         "       br.BANK_ID=bk.BANK_ID               \n" +
                         "       and curr.AC_OPERATIONAL_MODE_ID='FDW'   \n" ;
                }
                
                else if(unspent_OR_col.equalsIgnoreCase("NRDWP-Calamity")){
                	//System.out.println("inside calamity");
                	sql_office =
                        "select                          \n" + "       curr.BANK_ID,                       \n" +
                        "       curr.BRANCH_ID,                     \n" +
                        "       curr.BANK_AC_NO,                    \n" +
                        "       curr.AC_HEAD_CODE,                  \n" +
                        "       curr.AC_OPERATIONAL_MODE_ID,        \n" +
                        "       substring(curr.AC_HEAD_CODE::varchar,1,4) as main_AHCODE,                                       \n" +
                        "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city      \n" +
                        "                                           \n" +
                        "from                                       \n" +
                        "       FAS_OFFICE_BANK_AC_CURRENT curr,    \n" +
                        "       FAS_MST_BANK_BRANCHES br ,          \n" +
                        "       FAS_MST_BANKS bk                    \n" +
                        "                                           \n" +
                        "where                                      \n" +
                        "       curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and       \n" +
                        "       curr.MODULE_ID='MF015' and          \n" +
                        "       curr.CR_DR_TYPE='CR' and            \n" +
                        "       curr.SL_NO=1 and                    \n" +
                        "       curr.BANK_ID=br.BANK_ID and         \n" +
                        "       curr.BRANCH_ID=br.BRANCH_ID and     \n" +
                        "       br.BANK_ID=bk.BANK_ID               \n" +
                        "       and curr.AC_OPERATIONAL_MODE_ID='OPR-NRDWP-Main'   \n" +
                        "       ";
                }
                	
                	 else if(unspent_OR_col.equalsIgnoreCase("NRDWP-AEJE")){
                     	//System.out.println("inside calamity");
                     	sql_office =
                             "select                          \n" + "       curr.BANK_ID,                       \n" +
                             "       curr.BRANCH_ID,                     \n" +
                             "       curr.BANK_AC_NO,                    \n" +
                             "       curr.AC_HEAD_CODE,                  \n" +
                             "       curr.AC_OPERATIONAL_MODE_ID,        \n" +
                             "       substring(curr.AC_HEAD_CODE::varchar,1,4) as main_AHCODE,                                       \n" +
                             "       bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city      \n" +
                             "                                           \n" +
                             "from                                       \n" +
                             "       FAS_OFFICE_BANK_AC_CURRENT curr,    \n" +
                             "       FAS_MST_BANK_BRANCHES br ,          \n" +
                             "       FAS_MST_BANKS bk                    \n" +
                             "                                           \n" +
                             "where                                      \n" +
                             "       curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and       \n" +
                             "       curr.MODULE_ID='MF015' and          \n" +
                             "       curr.CR_DR_TYPE='CR' and            \n" +
                             "       curr.SL_NO=1 and                    \n" +
                             "       curr.BANK_ID=br.BANK_ID and         \n" +
                             "       curr.BRANCH_ID=br.BRANCH_ID and     \n" +
                             "       br.BANK_ID=bk.BANK_ID               \n" +
                             "       and curr.AC_OPERATIONAL_MODE_ID='OPR-NRDWP-Main'   \n" +
                             "       ";
               }
                System.out.println(sql_office);

                psbank = con.prepareStatement(sql_office);

                psbank.setInt(1, accounting_unit_id);
System.out.println("'accounting_unit_id "+accounting_unit_id+" unspent_OR_col "+unspent_OR_col);
                rsoffice = psbank.executeQuery();

                System.out.println("rsbank==" + rsbank);
                if(unspent_OR_col.equalsIgnoreCase("FDW")){
                	 while (rsoffice.next()) {
                     
                         System.out.println("inside if rsoffcie-------");

                         xml =
      xml + "<AC_HEAD_CODE>" + rsoffice.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
                         xml =
      xml + "<BANK_ID>" + rsoffice.getInt("BANK_ID") + "</BANK_ID>";
                         xml =
      xml + "<BRANCH_ID>" + rsoffice.getInt("BRANCH_ID") + "</BRANCH_ID>";
                         xml =
      xml + "<BANK_AC_NO>" + rsoffice.getLong("BANK_AC_NO") + "</BANK_AC_NO>";
                         xml =
      xml + "<bk_br_city>" + rsoffice.getString("bk_br_city") + "</bk_br_city>";
                         c++;
                     }
                	 if(c==0){
                		 xml = xml + "<flag>failure_office</flag>";
                         System.out.println("else part error by kkv ");

                	 }else{
                		    xml = xml + "<flag>success</flag>";
                	 }
                }else{
                if (rsoffice.next()) {
                    xml = xml + "<flag>success</flag>";
                    System.out.println("inside if rsoffcie------- loading");

                    xml =
 xml + "<AC_HEAD_CODE>" + rsoffice.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
                    xml =
 xml + "<BANK_ID>" + rsoffice.getInt("BANK_ID") + "</BANK_ID>";
                    xml =
 xml + "<BRANCH_ID>" + rsoffice.getInt("BRANCH_ID") + "</BRANCH_ID>";
                    xml =
 xml + "<BANK_AC_NO>" + rsoffice.getLong("BANK_AC_NO") + "</BANK_AC_NO>";
                    xml =
 xml + "<bk_br_city>" + rsoffice.getString("bk_br_city") + "</bk_br_city>";
                } else {
                    xml = xml + "<flag>failure_office</flag>";
                    System.out.println("else part error by kkv ");

                }
                } 

                //    psbank.close();
                //    rsbank.close();


            } catch (Exception e1) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Exception handling.." + e1);
            }
            xml = xml + "</response>";
            System.out.println("xml.." + xml);
            out.println(xml);

        }


    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

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
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        PreparedStatement ps = null;
        String xml = "";
        String strCommand = "";
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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
             CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
          System.out.println("Fund_Transfer_Create_byOffice    Add  >>> "+xml);
            Calendar c;
            //  Office details
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId = 0;
            long txtBankAccountNo = 0;

            double txtAmount = 0;
            String txtRemarks = "";
            Date txtCrea_date = null, txtReferenceDate = null;
            String txtReferenceNo = "", radRemitType = "";
            String update_user = (String)session.getAttribute("UserId");
            //Head Office details
            int txtDebitAccCode = 0, txtSubBankId = 0, txtSubBranchId = 0;
            //int txtSubOffice_code=0;
            long txtSubBankAccountNo = 0;
            String txtCheque_DD = "", txtCheque_DD_NO = "";
            Date txtCheque_DD_date = null;


            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            try {
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);

            try {
                txtBankId =
                        Integer.parseInt(request.getParameter("txtBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankId " + txtBankId);

            try {
                txtBranchId =
                        Integer.parseInt(request.getParameter("txtBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBranchId " + txtBranchId);

            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);

            try {
                txtDebitAccCode =
                        Integer.parseInt(request.getParameter("txtDebitAccCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtDebitAccCode " + txtDebitAccCode);

            try {
                txtSubBankId =
                        Integer.parseInt(request.getParameter("txtSubBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBankId " + txtSubBankId);

            try {
                txtSubBranchId =
                        Integer.parseInt(request.getParameter("txtSubBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBranchId " + txtSubBranchId);

            try {
                txtSubBankAccountNo =
                        Long.parseLong(request.getParameter("txtSubBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtSubBankAccountNo " + txtSubBankAccountNo);


            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

           
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);


            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);


            radRemitType = request.getParameter("radRemitType").trim();
            System.out.println("radRemitType " + radRemitType);


            txtReferenceNo = request.getParameter("txtReferenceNo");
            System.out.println("txtReferenceNo " + txtReferenceNo);

            if (!request.getParameter("txtReferenceDate").equalsIgnoreCase("")) {
                sd = request.getParameter("txtReferenceDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtReferenceDate = new Date(d.getTime());
            }
            System.out.println("txtReferenceDate " + txtReferenceDate);


            /* txtCheque_DD=request.getParameter("txtCheque_DD");

            txtCheque_DD_NO=request.getParameter("txtCheque_DD_NO");

            sd=request.getParameter("txtCheque_DD_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtCheque_DD_date=new Date(d.getTime());
            System.out.println("txtCheque_DD_date "+txtCheque_DD_date);
        */


            // DD and Cheque Option
            try {
                txtCheque_DD = request.getParameter("txtCheque_DD");
            } catch (Exception e) {
                System.out.println("Failed to get option such as cheque or dd or ecs ");
            }

            // Get DD / Cheque Number
            try {
                txtCheque_DD_NO = request.getParameter("txtCheque_DD_NO");
            } catch (Exception e) {
                System.out.println("Failed to get Cheque or DD Number");
            }

            // Get DD / Cheque Date
            try {
                sd = request.getParameter("txtCheque_DD_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtCheque_DD_date = new Date(d.getTime());
                System.out.println("txtCheque_DD_date " + txtCheque_DD_date);
            } catch (Exception e) {
                System.out.println("Failed to Get Cheque or DD Date ");
            }


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");

                cs =
  con.prepareCall("call FAS_FUND_TRF_FRM_OFFICE_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?::numeric,?,?)");
             
          
           
                
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setDate(3, txtCrea_date);
                cs.setInt(4, txtCash_year);
                cs.setInt(5, txtCash_Month_hid);
                cs.setInt(6, txtVoucher_No);
                cs.setString(7, radRemitType);
                cs.setInt(8, txtCash_Acc_code);
                cs.setInt(9, txtBankId);
                cs.setInt(10, txtBranchId);
                cs.setLong(11, txtBankAccountNo);
                cs.setString(12, txtRemarks);
                cs.setDouble(13, txtAmount);
                cs.setString(14, txtReferenceNo);
                cs.setDate(15, txtReferenceDate);
                cs.setInt(16, txtDebitAccCode);
                cs.setInt(17, txtSubBankId);
                cs.setInt(18, txtSubBranchId);
                cs.setLong(19, txtSubBankAccountNo);
                cs.setString(20, txtCheque_DD);
                cs.setString(21, txtCheque_DD_NO);
                cs.setDate(22, txtCheque_DD_date);
                cs.setString(23, "insert");
                cs.registerOutParameter(24, java.sql.Types.NUMERIC);
                cs.registerOutParameter(6, java.sql.Types.NUMERIC);
                cs.setNull(24,java.sql.Types.NUMERIC);
                cs.setNull(6,java.sql.Types.NUMERIC);
                cs.setString(25, update_user);
                cs.setTimestamp(26, ts);
                System.out.println("txtCheque_DD_date >> "+cmbOffice_code+" "+txtCheque_DD_date+"  txtVoucher_No "+txtVoucher_No);
                cs.execute();

                //txtVoucher_No = cs.getInt(6);
                txtVoucher_No = cs.getBigDecimal(6).intValue();
                System.out.println("After txtVoucher_No "+txtVoucher_No);
                //int errcode = cs.getInt(24);
                int errcode= cs.getBigDecimal(24).intValue();
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                   sendMessage(response,
                                "The Fund Transfer Transaction failed ", "ok");
                    return;
                } else{
                    System.out.println("b4 commit");
            //    con.commit();
                sendMessage(response,
                            "The Fund Transfer Transaction Voucher Number '" +
                            txtVoucher_No + "' has been Created Successfully ",
                            "ok");
                }
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("excepton in rollback");
                }

                sendMessage(response, "The Fund Transfer Transaction Failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("exception in autocommit");
                }
            }
        }
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("error in send message");
        }
    }
}

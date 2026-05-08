package Servlets.FAS.FAS1.MonthEnd.servlets;

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

public class SubLedgerMainFormServlet_CB extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("Servlet Started --------------------------<><><><>");

        /**
         * Session Checking
         */
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }


        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Get User ID
         */
        String userid = (String)session.getAttribute("UserId");
        System.out.println(userid);


        /**
         * Set Content Type
         */
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        /**
         * Variable Declaration
         */
        Connection con = null;
        PreparedStatement pss1 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet res = null;
        ResultSet res2 = null;


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
               * Get Parameters
               */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String Office_Code = request.getParameter("comOffCode");
        String CashBook_Year = request.getParameter("CashbookYear");
        String CashBook_Month = request.getParameter("CashbookMonth");
        String Mas_SL_type = request.getParameter("cmbSL_type");
        String cmbMas_SL_Code = request.getParameter("cmbMas_SL_Code");


        /**
         * Variables Declarations
         */
        String strCommand = "";
        String xml = "";
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int cmbMas_SL_type = 0;
        int Mas_SL_Code = 0;
        int cmbAcHeadCode = 0;
        double CloseBal = 0;
        String CloseBalInd = "";
        int CashbookYear = 0;
        int CashbookMonth = 0;
        int AccId = 0;
        int OffCode = 0;
        String FinanYr = "";


        /**
         * Convert String Data into corresponding Data
         */
        try {        	
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
            cmbMas_SL_type = Integer.parseInt(Mas_SL_type);
            Mas_SL_Code = Integer.parseInt(cmbMas_SL_Code);
        } catch (Exception e) {
        	//e.printStackTrace();
            System.out.println("Exception in Converting Integer:" + e);
        }


        /**
         * Get Command Parameter and Account Head Code
         */
        try {
            strCommand = request.getParameter("Command");
            cmbAcHeadCode =
                    Integer.parseInt(request.getParameter("cmbAcHeadCode"));
        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }


        /**
         * ADD Part
         */
        if (strCommand.equalsIgnoreCase("Add")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Add</command>";

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            CloseBalInd = request.getParameter("CloseBalInd");
            System.out.println("CloseBalInd is:" + CloseBalInd);

            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }

            FinanYr = request.getParameter("txtFinanYr");
            System.out.println(FinanYr);

            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }


            try {

                CloseBal =
                	Double.parseDouble(request.getParameter("CloseBal"));
                System.out.println("CloseBal is:" + CloseBal);

            } catch (Exception ex1) {
                System.out.println("Exception in CloseBal:" + ex1);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }


            try {
                System.out.println("inner try b4");
                ps1 =
 con.prepareStatement("select account_head_code from FAS_SUB_LEDGER_MASTER_CB where accounting_unit_id=? " +
                      " and account_head_code=? and year=? and month=? and accounting_for_office_id=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?");
                ps1.setInt(1, AccId);
                ps1.setInt(2, cmbAcHeadCode);
                ps1.setInt(3, CashbookYear);
                ps1.setInt(4, CashbookMonth);
                ps1.setInt(5, OffCode);
                ps1.setInt(6, cmbMas_SL_type);
                ps1.setInt(7, Mas_SL_Code);
                res = ps1.executeQuery();
                if (!res.next()) {
                    pss1 =
con.prepareStatement("insert into FAS_SUB_LEDGER_MASTER_CB(ACCOUNT_HEAD_CODE,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,UPDATED_BY_USER_ID,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND,YEAR,MONTH,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?)");
                    pss1.setInt(1, cmbAcHeadCode);
                    pss1.setInt(2, AccId);
                    pss1.setInt(3, OffCode);
                    pss1.setString(4, FinanYr);
                    pss1.setString(5, userid);
                    pss1.setDouble(6, CloseBal);
                    pss1.setString(7, CloseBalInd);
                    pss1.setInt(8, CashbookYear);
                    pss1.setInt(9, CashbookMonth);
                    pss1.setInt(10, cmbMas_SL_type);
                    pss1.setInt(11, Mas_SL_Code);
                    pss1.setTimestamp(12, ts);
                    System.out.println("inner try b41");
                    pss1.executeUpdate();
                    System.out.println("inner try b42");
                    con.commit();
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>AlreadyExist</flag>";
                }
            } catch (Exception ex4) {
                System.out.println("Exception in add......" + ex4);
                xml = xml + "<flag>failure</flag>";
            }
            finally
            {
		              System.out.println("done");
		              try{
		            	  con.setAutoCommit(true);
		            	  con.close(); 
		              }catch(SQLException sqle){}
            }
            xml = xml + "</response>";
            out.write(xml);
            System.out.println("xml is:" + xml);
        }


        /**
         * Update Part
         */
        else if (strCommand.equalsIgnoreCase("Update")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");

            xml = "<response><command>Update</command>";

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            CloseBalInd = request.getParameter("CloseBalInd");
            System.out.println("CloseBalInd is:" + CloseBalInd);
            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }

            FinanYr = request.getParameter("txtFinanYr");
            System.out.println(FinanYr);

            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }
            System.out.println("cmbAcHeadCode::"+cmbAcHeadCode);
            //                 credit / debit details

            try {

                CloseBal =Double.parseDouble(request.getParameter("CloseBal"));
                System.out.println("CloseBal is:" + CloseBal);

            } catch (Exception ex1) {
                System.out.println("Exception in CloseBal:" + ex1);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }
            System.out.println("cmbMas_SL_type::"+cmbMas_SL_type);
            System.out.println("Mas_SL_Code::"+Mas_SL_Code);
            try {
            	String sql="update FAS_SUB_LEDGER_MASTER_CB set MONTH_CLOSING_BALANCE="+CloseBal+"," +
            			" MONTH_CLOSING_BAL_DR_CR_IND='"+CloseBalInd+"',UPDATED_BY_USER_ID='"+userid+"',UPDATED_DATE=? " +
            			" where ACCOUNTING_UNIT_ID="+AccId+" and ACCOUNTING_FOR_OFFICE_ID="+OffCode+" and FINANCIAL_YEAR='" +FinanYr+"'"+
            			" and ACCOUNT_HEAD_CODE="+cmbAcHeadCode+" and year="+CashbookYear+" and month="+CashbookMonth+" and SUB_LEDGER_TYPE_CODE="+cmbMas_SL_type+" " +
            			" and SUB_LEDGER_CODE="+Mas_SL_Code;
                System.out.println("sql::::"+sql);
            	pss1 =con.prepareStatement(sql);
//                pss1.setInt(1, CashbookYear);
//                pss1.setInt(2, CashbookMonth);

                pss1.setTimestamp(1, ts);

                int k=pss1.executeUpdate();
                System.out.println(k);
                if(k>0){
                	 xml = xml + "<flag>success</flag>";
                     pss1.close();
                con.commit();
                }
                else
                {
                	xml = xml + "<flag>failure</flag>";
                }
               
            } catch (Exception ex4) {
                System.out.println("Exception in update......" + ex4);
                xml = xml + "<flag>failure</flag>";
            }
            finally
            {
		              System.out.println("done");
		              try{
		            	  con.setAutoCommit(true);
		            	  con.close(); 
		              }catch(SQLException sqle){}
            }
            xml = xml + "</response>";
            out.write(xml);
            System.out.println("xml is:" + xml);

        }


        /**
         * Delete Part
         */
        else if (strCommand.equalsIgnoreCase("Delete")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Delete</command>";

            try {
                AccId =Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }
            FinanYr = request.getParameter("txtFinanYr");
            System.out.println(FinanYr);

            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }


            try {

                // String sql="delete from FAS_SUB_LEDGER_MASTER_CB where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and year=? and month=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?";

                String sql =
                    "DELETE\n" + "FROM FAS_SUB_LEDGER_MASTER_CB                       \n" +
                    "WHERE ACCOUNT_HEAD_CODE     =" + cmbAcHeadCode +
                    "      \n" + "AND ACCOUNTING_UNIT_ID      =" + AccId +
                    "              \n" + "AND ACCOUNTING_FOR_OFFICE_ID=" +
                    OffCode + "            \n" +
                    "AND FINANCIAL_YEAR          ='" + FinanYr +
                    "'          \n" + "AND YEAR                    =" +
                    CashbookYear + "       \n" +
                    "AND MONTH                   =" + CashbookMonth +
                    "      \n" + "AND SUB_LEDGER_TYPE_CODE    =" +
                    cmbMas_SL_type + "     \n" +
                    "AND SUB_LEDGER_CODE         =" + Mas_SL_Code +
                    "          ";

                ps1 = con.prepareStatement(sql);

                System.out.println("delete sql-->" + sql);

                System.out.println("UnitId-->" + AccId);
                System.out.println("OficeID -->" + OffCode);
                System.out.println("cbmon-->" + CashbookMonth);
                System.out.println("cbyear-->" + CashbookYear);
                System.out.println("sltype-->" + cmbMas_SL_type);
                System.out.println("slcode -->" + Mas_SL_Code);
                System.out.println("FinYr-->" + FinanYr);
                System.out.println("AccCode-->" + cmbAcHeadCode);

                /*
            ps1.setInt(1,cmbAcHeadCode);
            ps1.setInt(2,AccId);
            ps1.setInt(3,OffCode);
            ps1.setString(4,FinanYr);
            ps1.setInt(5,CashbookYear);
            ps1.setInt(6,CashbookMonth);
            ps1.setInt(7,cmbMas_SL_type);
            ps1.setInt(8,Mas_SL_Code);
            */

                ps1.executeUpdate();

                		//sankari
                con.commit();
                xml = xml + "<flag>success</flag>";
                ps1.close();
            } catch (Exception ex2) {
                System.out.println("exception in add" + ex2);
                xml = xml + "<flag>failure</flag>";
            }
            finally
            {
		              System.out.println("done");
		              try{
		            	  con.setAutoCommit(true);
		            	  con.close(); 
		              }catch(SQLException sqle){}
            }
            xml = xml + "</response>";
            out.write(xml);
            System.out.println("xml is:" + xml);
        }


        /**
         * Load Sub Ledger Code Part
         */
        if (strCommand.equalsIgnoreCase("loadsubledgercode")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>loadsubledgercode</command>";
            int y = 0;
            System.out.println("insideloadcode");
            int subtype = 0;
            int subcode = 0;

            try {
                subtype = Integer.parseInt(request.getParameter("cmbSL_type"));
                System.out.println("subtype:" + subtype);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in load command subtype...." +
                                   que);
            }


            try {
                subcode =
                        Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
                System.out.println("subcode:" + subcode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command subcode...." +
                                   que);
            }


            try {
                String subname = "";
                ps =
  con.prepareStatement("select SL_CODE,SL_CODENAME from SL_TYPE_CODE_NAME_VIEW where SL_TYPE=? and SL_CODE=?");
                ps.setInt(1, subtype);
                ps.setInt(2, subcode);
                res = ps.executeQuery();
                if (res.next()) {
                    xml =
 xml + "<cid>" + res.getInt("SL_CODE") + "</cid><cname>" +
   res.getString("SL_CODENAME") + "</cname>";
                    //y++;

                    xml = xml + "<flag>success</flag>";
                } else
                    xml = xml + "<flag>failure</flag>";

                ps.close();
                res.close();
            }


            catch (Exception e) {
                System.out.println("Finding subledgercode failed due to exception" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
            return;
        }


        /**
         * Load Account Head Code Part
         */
        if (strCommand.equalsIgnoreCase("HeadCode")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String headcode = request.getParameter("txtAcc_Head_code");
            int headcodeno = Integer.parseInt(headcode);
            xml = "<response><command>HeadCode</command>";

            try {
                ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, headcodeno);
                res = ps.executeQuery();
                if (res.next()) {
                    xml =
 xml + "<flag>success</flag><hid>" + headcodeno + "</hid><hdesc>" +
   res.getString("ACCOUNT_HEAD_DESC") + "</hdesc><BalType>" +
   res.getString("BALANCE_TYPE") + "</BalType><SL_YN>" +
   res.getString("SUB_LEDGER_TYPE_APPLICABLE") + "</SL_YN><rmk>" +
   res.getString("REMARKS") + "</rmk>";

                    if (res.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) {
                       int sl_cnt=0;
                    	ps =
  con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'"); // STATUS field added 05-07-19
                        ps.setInt(1, headcodeno);
                        res = ps.executeQuery();
                        while (res.next()) {
                            sl_cnt++;
                        	xml =
 xml + "<SLCODE>" + res.getInt("SUB_LEDGER_TYPE_CODE") + "</SLCODE>";
                            System.out.println(res.getInt("SUB_LEDGER_TYPE_CODE") +
                                               "code");
                            if (res.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                                System.out.println("take SL DESC");
                                ps2 =
 con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                                ps2.setInt(1,
                                           res.getInt("SUB_LEDGER_TYPE_CODE"));
                                res2 = ps2.executeQuery();
                                if (res2.next())
                                    xml =
 xml + "<SLDESC>" + res2.getString("SUB_LEDGER_TYPE_DESC") + "</SLDESC>";
                            }
                        }
                        if(sl_cnt==0) {
                            System.out.println("STATUS is 'N'");
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } else {
                    System.out.println("No record found");
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
            	e.printStackTrace();
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.write(xml);
            System.out.println("xml is:" + xml);
        }


        /**
       * Freeze Check
       */
        if (strCommand.equalsIgnoreCase("Freeze_Check")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Freeze_Check</command>";

            try {
                ps =
  con.prepareStatement("  select 'X'                  \n" + "  from                        \n" +
                       "    FAS_SL_CB_STATUS  \n" +
                       "  WHERE                       \n" +
                       "     ACCOUNTING_UNIT_ID=?     \n" +
                       "  AND CASHBOOK_YEAR=?      \n" +
                       "  AND CASHBOOK_MONTH=?");
                ps.setInt(1, AccountUnitCode);
                ps.setInt(2, CashBookYear);
                ps.setInt(3, CashBookMonth);
                res = ps.executeQuery();
                while (res.next()) // if the row doesn't return by the query
                {
                    xml = xml + "<flag>success</flag>";
                }
                res.close();
                xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.write(xml);
            System.out.println("xml is:" + xml);
        }


        /**
         * Finally Close the Print Writer
         */
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
            System.out.println("ERROR");
        }
    }


}



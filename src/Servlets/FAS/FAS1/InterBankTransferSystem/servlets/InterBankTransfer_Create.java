package Servlets.FAS.FAS1.InterBankTransferSystem.servlets;

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
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class InterBankTransfer_Create extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
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
       
        PreparedStatement ps = null;
        //String xml="";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
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

        if (strCommand.equalsIgnoreCase("load_bank_deatils")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>load_bank_deatils</command>";
            int cmbAcc_UnitCode = 0;
            long bank_acc_no = 0;
            String cr_dr_indicator = "";
            int branch_id=0;
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                bank_acc_no =
                        Long.parseLong(request.getParameter("bank_acc_no"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("bank_acc_no " + bank_acc_no);

            cr_dr_indicator = request.getParameter("cr_dr_indicator");
            System.out.println("cr_dr_indicator " + cr_dr_indicator);

            try {
                branch_id =
                		Integer.parseInt(request.getParameter("branch"));
                System.out.println("branch_id>>>>>"+branch_id);
                
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            } 
            String sql_bank =
                "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce(br.CITY_TOWN_NAME,'') as bk_br_city " +
                " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.BANK_AC_NO=? and curr.BRANCH_ID=?  and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                " and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID";
            try {
                ps = con.prepareStatement(sql_bank);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setLong(2, bank_acc_no);
                ps.setInt(3, branch_id);
                ps.setString(4, "MF010");
                ps.setString(5, cr_dr_indicator);
                rs = ps.executeQuery();
                
                System.out.println("sql_bank>>>>"+sql_bank);

                xml =
 xml + "<cr_dr_indicator>" + cr_dr_indicator + "</cr_dr_indicator>";

                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<BANK_ID>" + rs.getInt("BANK_ID") + "</BANK_ID>";
                    xml =
 xml + "<bk_br_city>" + rs.getString("bk_br_city") + "</bk_br_city>";
                    xml =
 xml + "<BRANCH_ID>" + rs.getInt("BRANCH_ID") + "</BRANCH_ID>";
                    xml =
 xml + "<AC_HEAD_CODE>" + rs.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
                } else
                    xml = xml + "<flag>failure</flag>";
                ps.close();
                rs.close();

            } catch (Exception e) {
                System.out.println("Finding Branch failed due to exception" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
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
        ResultSet rs = null,rs2=null;
        Statement st=null;
        CallableStatement cs = null;
        PreparedStatement ps = null,ps1=null;
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
            st=con.createStatement();
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
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            Calendar c;
            //  HO details
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId = 0;
            long txtBankAccountNo = 0;

            double txtAmount = 0;
            String txtRemarks = "";
            Date txtCrea_date = null, txtReferenceDate = null;
            String txtReferenceNo = "";
            String update_user = (String)session.getAttribute("UserId");
            //String radRemitType="";

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


            txtReferenceNo = request.getParameter("txtReferenceNo");
            System.out.println("txtReferenceNo ...." + txtReferenceNo);

            if (!request.getParameter("txtReferenceDate").equalsIgnoreCase("")) {
                sd = request.getParameter("txtReferenceDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtReferenceDate = new Date(d.getTime());
            }
            System.out.println("txtReferenceDate " + txtReferenceDate);

            txtCheque_DD = request.getParameter("txtCheque_DD");

            txtCheque_DD_NO = request.getParameter("txtCheque_DD_NO");

            if (!request.getParameter("txtCheque_DD_date").equalsIgnoreCase("")) {
                sd = request.getParameter("txtCheque_DD_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtCheque_DD_date = new Date(d.getTime());
            }
            System.out.println("txtCheque_DD_date " + txtCheque_DD_date);


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

            /*
            System.out.println("b4 getting month and year");
            try{txtCash_year=Integer.parseInt(sd[2]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_year "+txtCash_year);

                        try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_Month_hid "+txtCash_Month_hid);

           String[] sp=request.getParameter("txtCrea_date").split("/");
           System.out.println(sp[0]+" "+sp[1]+" "+sp[2]);
           int check_year=Integer.parseInt(sp[2]);                 // to check in while loop
           int check_day=Integer.parseInt(sp[0]);                    // to check in while loop
                      System.out.println(Integer.parseInt(sp[2]));
                      System.out.println("here"+check_year);

           String check_date=request.getParameter("txtCrea_date");
           sp=request.getParameter("txtCrea_date").split("/");
           check_date=sp[2]+"/"+sp[1]+"/"+sp[0];

           System.out.println(check_date); // to check in while loop with d/b date it converted to yyyy/mm/dd form

            try
            {
                String sql1="select FINANCIAL_YEAR," +
                "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ," +
                "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
                "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  " +
                "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";

             // date is taken as string from database in above format for checking with receipt date variable ( check_date is string type)
             // checking of dates performed in form of String checking
             ps=con.prepareStatement(sql1);
             rs=ps.executeQuery();
             int Begin_yr,End_yr;
            while(rs.next())
            {
                String[] yr=rs.getString("FINANCIAL_YEAR").split("-");
                 Begin_yr=Integer.parseInt(yr[0]);
                 End_yr=Integer.parseInt(yr[1]);
                                 System.out.println("while");
                                 System.out.println(Begin_yr+ " "+End_yr);
                                 System.out.println(rs.getString("mar_beg")+" "+rs.getString("mar_end"));

                if(check_year==Begin_yr)          //   to check which financial year it belongs
                {
                    if(txtCash_Month_hid>=4 && txtCash_Month_hid<=12)
                    {
                             System.out.println("if 4");
                             if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                             {
                                 txtCash_Month_hid=03;
                             System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                             }
                             else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                             {
                                 txtCash_Month_hid=04;
                             System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                             }
                             else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                             {
                                 txtCash_Month_hid=txtCash_Month_hid+1;
                                 if(txtCash_Month_hid>12)
                                     {
                                     txtCash_Month_hid=1;
                                     txtCash_year=txtCash_year+1;
                                     System.out.println("hello"+txtCash_year);
                                     }
                                 System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                             }
                             else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                             {
                                //txtCash_Month_hid=txtCash_Month_hid;
                                System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                             }
                    }

                }
                else  if(check_year==End_yr)
                {
                    if(txtCash_Month_hid>=1 && txtCash_Month_hid<=3)
                    {
                        txtCash_year=End_yr;System.out.println("if 3");
                        if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                         {
                             txtCash_Month_hid=03;
                         System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                         }
                         else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                         {
                             txtCash_Month_hid=04;
                         System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                         }
                         else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                         {
                             txtCash_Month_hid=txtCash_Month_hid+1;
                             if(txtCash_Month_hid>12)
                             {
                             txtCash_Month_hid=1;
                             txtCash_year=txtCash_year+1;
                             System.out.println("hello"+txtCash_year);
                             }
                             System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                         }
                        else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                        {
                            //txtCash_Month_hid=txtCash_Month_hid;
                            System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                        }
                    }
                }
            }
            ps.close();
            rs.close();
            }
            catch(Exception e)
            {
            sendMessage(response,"The Cash Book Period Not Found ","ok");
            System.out.println("exception"+e);
            }

          */
          int vou1=0,vou2=0,count=0;
          String sql="",sql1="";
          System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
             try
             {
             
                 sql=" select max(VOUCHER_NO)as vno from FAS_INTER_BANK_TRF_AT_HO where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and" +
                 " ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and " +
                 " CASHBOOK_YEAR="+txtCash_year;
                 System.out.println("sql:::"+sql);
                 ps=con.prepareStatement(sql);
                 rs=ps.executeQuery();
                 while(rs.next()) 
                 {
                       vou1 = rs.getInt("vno");  
                      txtVoucher_No=vou1;
                     count++;
                     
                 }
                 
                 txtVoucher_No=txtVoucher_No+1;
                 System.out.println("last final txtVoucher_No:::"+txtVoucher_No);
             }
             catch(Exception e){System.out.println("exception in finding max voucher_no"+e );}
             

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");

                cs =
 con.prepareCall("call FAS_INTERBANK_TRANSFER_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?::numeric,?,?)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setDate(3, txtCrea_date);
                cs.setInt(4, txtCash_year);
                cs.setInt(5, txtCash_Month_hid);
                System.out.println("txtVoucher_No::"+txtVoucher_No);
                cs.setInt(6, txtVoucher_No);
                //cs.setString(7,radRemitType);
                cs.setInt(7, txtCash_Acc_code);
                cs.setInt(8, txtBankId);
                cs.setInt(9, txtBranchId);
                cs.setLong(10, txtBankAccountNo);
                cs.setString(11, txtRemarks);
                cs.setDouble(12, txtAmount);
                cs.setString(13, txtReferenceNo);
                cs.setDate(14, txtReferenceDate);
                cs.setInt(15, txtDebitAccCode);
                cs.setInt(16, txtSubBankId);
                cs.setInt(17, txtSubBranchId);
                cs.setLong(18, txtSubBankAccountNo);
                cs.setString(19, txtCheque_DD);
                cs.setString(20, txtCheque_DD_NO);
                cs.setDate(21, txtCheque_DD_date);
                cs.setString(22, "insert");
                cs.registerOutParameter(23, java.sql.Types.NUMERIC);
                cs.registerOutParameter(6, java.sql.Types.NUMERIC);
                cs.setNull(23, java.sql.Types.NUMERIC);
                //cs.setNull(6, java.sql.Types.NUMERIC);
                
                cs.setString(24, update_user);
                cs.setTimestamp(25, ts);
                cs.execute();

//                txtVoucher_No = cs.getInt(6);
//                int errcode = cs.getInt(23);
                txtVoucher_No = cs.getBigDecimal(6).intValue();
                int errcode = cs.getBigDecimal(23).intValue();
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The InterBank Transfer Transaction failed ",
                                "ok");
                    return;
                } else
                    System.out.println("b4 commit");
                con.commit();
                sendMessage(response,
                            "The InterBank Transfer Transaction Voucher Number '" +
                            txtVoucher_No + "' has been Created Successfully ",
                            "ok");

            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("excepton in rollback");
                }

                sendMessage(response,
                            "The InterBank Transfer TransactionFailed ", "ok");
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

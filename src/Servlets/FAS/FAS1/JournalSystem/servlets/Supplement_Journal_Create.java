package Servlets.FAS.FAS1.JournalSystem.servlets;

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

public class Supplement_Journal_Create extends HttpServlet {

    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);

        String strCommand = "";
        Connection con = null;
        PrintWriter out = response.getWriter();

        CallableStatement cs = null;
        PreparedStatement ps = null;
        String xml = "";
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

        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        Calendar c;
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
            0, txtCash_year = 0, txtJournalVou_No = 0;
        int Total_TRN_Rec = 0;
        String txtCheque_NO = "", txtCB_REF_TYPE = "";

        Date txtCrea_date = null, txtCheque_date = null;
        String txtRemarks = "";

        int cmbMas_SL_type = 0, cmbMas_SL_Code = 0;
        String txtMode_of_creat = "M", txtCreat_By_Module = "SJV";
        double dep_rate = 0; // changes here
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

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

        String[] sd = null;
        java.util.Date d = null;

        try {
            sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("b4 getting month and year");

        try {
            txtCash_year = Integer.parseInt(sd[2]);
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtCash_year " + txtCash_year);

        try {
            txtCash_Month_hid = Integer.parseInt(sd[1]);
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtCash_Month_hid " + txtCash_Month_hid);


        try {
            txtJournalVou_No =
                    Integer.parseInt(request.getParameter("txtJournalVou_No"));
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
        System.out.println("txtJournalVou_No " + txtJournalVou_No);

        try {
            txtCheque_NO = request.getParameter("txtCheque_NO");
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("txtCheque_NO " + txtCheque_NO);

        try {
            if (!request.getParameter("txtCheque_date").equalsIgnoreCase("")) {
                sd = request.getParameter("txtCheque_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtCheque_date = new Date(d.getTime());
            }
            System.out.println("txtCheque_date " + txtCheque_date);
        } catch (Exception e) {
            System.out.println(e);
        }


        try {
            cmbMas_SL_type =
                    Integer.parseInt(request.getParameter("cmbMas_SL_type"));
        } catch (Exception e) {
            System.out.println("exception" + e);
        }

        try {
            cmbMas_SL_Code =
                    Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
        } catch (Exception e) {
            System.out.println("exception" + e);
        }


        System.out.println("cmbMas_SL and office " + cmbMas_SL_type + " " +
                           cmbMas_SL_Code);


        try {
            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);
        } catch (Exception e) {
            System.out.println(e);
        }


        if (strCommand.equalsIgnoreCase("Add")) {

            xml = "<response><command>Add</command>";

            /**
             * Get Live Supplement Number
             */

            Statement st = null;
            ResultSet rs = null;
            String sql_suppl = null;
            int supplement_no = 0;

//            sql_suppl =
//                    "select SUPPLEMENT_NO from FAS_SUPPLEMENT_GJV where status='L' and cashbook_year = " +
//                    txtCash_year + " and cashbook_month =" + txtCash_Month_hid ;
//
//            try {
//                st = con.createStatement();
//                rs = st.executeQuery(sql_suppl);
//                while (rs.next()) {
//                    supplement_no = rs.getInt("SUPPLEMENT_NO");
//                }
//
//            } catch (Exception e) {
//                System.out.println("Unable to load Supplement Number " + e);
//            }

         try {
             supplement_no =Integer.parseInt(request.getParameter("supNo"));
             System.out.println("supplement_no>>>>>>"+supplement_no);
         } catch (Exception e) {
             System.out.println("exception in supplement_no" + e);
         }


            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");
                String No_TRN_Rec[] = request.getParameterValues("H_code");
                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                Total_TRN_Rec =
                        No_TRN_Rec.length; //Integer.parseInt(No_TRN_Rec);
                System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
                cs =
  con.prepareCall("call FAS_JOURNAL_MASTER_PROC_SJV(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?,?,?::numeric,?,?,?::numeric)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setInt(3, txtCash_year);
                cs.setInt(4, txtCash_Month_hid);
                cs.setInt(5, txtJournalVou_No);
                cs.setDate(6, txtCrea_date);
                // cs.setString(7,txtReceipt_type);
                //  cs.setInt(8,txtCash_Acc_code);
                cs.setInt(7, cmbMas_SL_type);
                cs.setInt(8, cmbMas_SL_Code);
                cs.setDouble(9, dep_rate);
                cs.setString(10, txtCheque_NO);
                cs.setDate(11, txtCheque_date);
                cs.setString(12, txtCB_REF_TYPE);
                //cs.setInt(13,txtCB_REF_NO);
                //cs.setDate(14,txtCB_REF_DATE);
                // cs.setDouble(19,txtAmount);
                cs.setInt(13, Total_TRN_Rec);
                cs.setString(14, txtRemarks);
                cs.setString(15, txtMode_of_creat);
                cs.setString(16, txtCreat_By_Module);
                cs.setString(17, "insert");
                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
                cs.setInt(18, 0);
                cs.setString(19, update_user);
                cs.setTimestamp(20, ts);
                cs.setInt(21, supplement_no);

                System.out.println("b4 exe ");
                cs.execute();
                txtJournalVou_No = cs.getBigDecimal(5).intValue();
                int errcode = cs.getBigDecimal(18).intValue();
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The Supplement Voucher Number Creation Failed ",
                                "ok");
                    xml = xml + "<flag>failure</flag>";
                } else {
                    String Grid_H_code[] =
                        request.getParameterValues("H_code");
                    String Grid_CR_DR_type[] =
                        request.getParameterValues("CR_DR_type");
                    String Grid_SL_type[] =
                        request.getParameterValues("SL_type");
                    String Grid_SL_code[] =
                        request.getParameterValues("SL_code");
                    // String Grid_rec_from[]=request.getParameterValues("rec_from");
                    String Grid_Bill_No[] =
                        request.getParameterValues("Bill_NO");
                    String Grid_Bill_date[] =
                        request.getParameterValues("Bill_date");
                    String Grid_Bill_type[] =
                        request.getParameterValues("Bill_type");

                    String Grid_Agree_No[] =
                        request.getParameterValues("Agree_No");
                    String Grid_Agree_date[] =
                        request.getParameterValues("Agree_date");

                    String Grid_sl_amt[] =
                        request.getParameterValues("sl_amt");
                    String Grid_particular[] =
                        request.getParameterValues("particular");

                    String sql =
                        "insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                        "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                        "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                        "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
                        "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                    int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code =
                        0, cmbSL_type = 0, txtCB_REF_NO = 0;
                    Date txtBill_Date = null, txtAgree_Date =
                        null, txtCheque_DD_date = null, txtCB_REF_DATE = null;
                    double txtsub_Amount = 0;
                    String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
                        "", txtAgree_No = "", txtParticular = "";
                    String txtCheque_DD = "", txtCheque_DD_NO = "";

                    ps = con.prepareStatement(sql);
                    for (int k = 0; k < Grid_H_code.length; k++) {
                        try {
                            txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        rad_sub_CR_DR = Grid_CR_DR_type[k];

                        try {
                            cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        try {
                            cmbSL_Code = Integer.parseInt(Grid_SL_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
                        System.out.println("Grid_CR_DR_type[k] " +
                                           Grid_CR_DR_type[k]);
                        System.out.println("Grid_SL_type[k]" +
                                           Grid_SL_type[k] + "u");
                        System.out.println("Grid_SL_code[k]" +
                                           Grid_SL_code[k] + "from here" +
                                           cmbSL_Code);
                        //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                        //txtsub_Recei_from=Grid_rec_from[k];


                        txtBill_no = Grid_Bill_No[k];

                        txtBill_Type = Grid_Bill_type[k];

                        if (!Grid_Bill_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Bill_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtBill_Date = new Date(d.getTime());
                        }

                        txtAgree_No = Grid_Agree_No[k];
                        if (!Grid_Agree_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Agree_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtAgree_Date = new Date(d.getTime());
                        }

                        System.out.println("txtBill_no..." + txtBill_no);
                        System.out.println("txtBill_Type..." + txtBill_Type);
                        System.out.println("txtBill_Date..." + txtBill_Date);
                        System.out.println("txtAgree_No..." + txtAgree_No);
                        System.out.println("txtAgree_Date..." + txtAgree_Date);

                        System.out.println("amount");
                        txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                        txtParticular = Grid_particular[k];
                        System.out.println("amount");
                        System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);
                        // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                        System.out.println("Grid_particular[k] " +
                                           Grid_particular[k]);

                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setInt(2, cmbOffice_code);
                        ps.setInt(3, txtCash_year);
                        ps.setInt(4, txtCash_Month_hid);
                        ps.setInt(5, txtJournalVou_No);
                        ps.setInt(6, SL_NO);
                        ps.setInt(7, txtAcc_HeadCode);
                        ps.setString(8, rad_sub_CR_DR);
                        ps.setInt(9, cmbSL_type);
                        ps.setInt(10, cmbSL_Code);
                        ps.setString(11, txtBill_no);
                        ps.setString(12, txtBill_Type);
                        ps.setString(13, txtAgree_No);
                        ps.setDate(14, txtAgree_Date);
                        ps.setDate(15, txtBill_Date);

                        ps.setString(16, txtCheque_DD);
                        ps.setString(17, txtCheque_DD_NO);
                        ps.setDate(18, txtCheque_DD_date);

                        ps.setDouble(19, txtsub_Amount);
                        ps.setString(20, txtParticular);
                        ps.setInt(21, txtCB_REF_NO);
                        ps.setDate(22, txtCB_REF_DATE);
                        ps.setString(23, update_user);
                        ps.setTimestamp(24, ts);
                        SL_NO++;
                        ps.executeUpdate();

                        txtAcc_HeadCode = 0;
                        rad_sub_CR_DR = "";
                        cmbSL_type = 0;
                        cmbSL_Code = 0;
                        txtCheque_DD = "";
                        txtCheque_DD_NO = "";
                        txtCheque_DD_date = null;
                        txtAgree_No = "";
                        txtAgree_Date = null;
                        txtsub_Amount = 0;
                        txtParticular = "";
                    }
                    ps.close();
                    System.out.println("b4 commit");
                    con.commit();
                    sendMessage(response,
                                "The Supplement Voucher Number '" + txtJournalVou_No +
                                "' has been Created Successfully ", "ok");
                }

            }

            catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("exception in rollback " + sqle);
                }
                sendMessage(response,
                            "The Supplement Voucher Number Creation Failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                }
            }

        }

        else if (strCommand.equalsIgnoreCase("Check_Supplement_No")) {

            Statement st = null;
            ResultSet rs = null;
            String sql = null;
            int supplement_no = 0;

            String xml2 = "<response><command>Check_Supplement_No</command>";

            sql =
 "select SUPPLEMENT_NO from FAS_SUPPLEMENT_GJV where status='L' and cashbook_year = " +
   txtCash_year + " and cashbook_month =" + txtCash_Month_hid+" order by SUPPLEMENT_NO";

            System.out.println(sql);

            try {
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                
                    xml2 = xml2 + "<supno>"+rs.getInt("SUPPLEMENT_NO")+"</supno>";
                    supplement_no++;
                }
                if (supplement_no>0) {
                    xml2 = xml2 + "<flag>success</flag>";
                    
                } 
                else if (supplement_no <= 0) {
                    xml2 = xml2 + "<flag>failure</flag>";
                    xml2 =xml2 + "<suppl_error>No Live Supplement Number</suppl_error>";
                }
                // else if (supplement_no > 1) {
                //     xml2=xml2+"<flag>failure</flag>";
                //     xml2=xml2+"<suppl_error>More than one Live Supplement Number</suppl_error>";
                // }

                xml2 = xml2 + "</response>";
                out.println(xml2);
                System.out.println(xml2);

            } catch (Exception e) {
                System.out.println("Unable to load Supplement Number " + e);
            }


        }
        
        
        else if (strCommand.equalsIgnoreCase("Check_Supplement_No_New")) {
System.out.println("Check_Supplement_No_New");
            Statement st = null;
            ResultSet rs = null;
            String sql = null;
            int supplement_no = 0;

            String xml2 = "<response><command>Check_Supplement_No</command>";

            sql =
 "select SUPPLEMENT_NO,CASHBOOK_YEAR from FAS_SUPPLEMENT_GJV where status='L' and cashbook_year = " +
   txtCash_year + " and cashbook_month =" + txtCash_Month_hid+" order by SUPPLEMENT_NO";

            System.out.println(sql);

            try {
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                
                    xml2 = xml2 + "<supno>"+rs.getInt("SUPPLEMENT_NO")+"</supno>";
                    xml2 = xml2 + "<year>"+rs.getInt("CASHBOOK_YEAR")+"</year>";
                    supplement_no++;
                }
                if (supplement_no>0) {
                    xml2 = xml2 + "<flag>success</flag>";
                    
                } 
                else if (supplement_no <= 0) {
//                    xml2 = xml2 + "<flag>failure</flag>";
//                    xml2 =xml2 + "<suppl_error>No Live Supplement Number</suppl_error>";
                	
                	String sql1 =
                			 "select SUPPLEMENT_NO,CASHBOOK_YEAR from FAS_SUPPLEMENT_GJV where status='L'order by SUPPLEMENT_NO";
                	Statement st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery(sql1);
                	while (rs1.next()) {
                        
                        xml2 = xml2 + "<supno>"+rs1.getInt("SUPPLEMENT_NO")+"</supno>";
                        xml2 = xml2 + "<year>"+rs1.getInt("CASHBOOK_YEAR")+"</year>";
                        supplement_no++;
                    }
                    if (supplement_no>0) {
                        xml2 = xml2 + "<flag>success</flag>";                        
                    } 
                    else if (supplement_no <= 0)
                    {
                    	xml2 = xml2 + "<flag>failure</flag>";
                    	xml2 =xml2 + "<suppl_error>No Live Supplement Number</suppl_error>";
                    }
                	
                	
                }
                // else if (supplement_no > 1) {
                //     xml2=xml2+"<flag>failure</flag>";
                //     xml2=xml2+"<suppl_error>More than one Live Supplement Number</suppl_error>";
                // }

                xml2 = xml2 + "</response>";
                out.println(xml2);
                System.out.println(xml2);

            } catch (Exception e) {
                System.out.println("Unable to load Supplement Number " + e);
            }


        }
        
        
        
        else if (strCommand.equalsIgnoreCase("Check_Supplement_No1")) {

        	
        	try {
        		txtCash_year =
                        Integer.parseInt(request.getParameter("txtCash_year"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_year " + txtCash_year);

            try {
            	txtCash_Month_hid =
                        Integer.parseInt(request.getParameter("txtCash_Month_hid"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

        	
            Statement st = null;
            ResultSet rs = null;
            String sql = null;
            int supplement_no = 0;

            String xml2 = "<response><command>Check_Supplement_No</command>";

            sql =
 "select SUPPLEMENT_NO from FAS_SUPPLEMENT_GJV where status='L' and cashbook_year = " +
   txtCash_year + " and cashbook_month =" + txtCash_Month_hid+" order by SUPPLEMENT_NO";

            System.out.println(sql);

            try {
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                
                    xml2 = xml2 + "<supno>"+rs.getInt("SUPPLEMENT_NO")+"</supno>";
                    supplement_no++;
                }
                if (supplement_no>0) {
                    xml2 = xml2 + "<flag>success</flag>";
                    
                } 
                else if (supplement_no <= 0) {
                    xml2 = xml2 + "<flag>failure</flag>";
                    xml2 =xml2 + "<suppl_error>No Live Supplement Number</suppl_error>";
                }
                // else if (supplement_no > 1) {
                //     xml2=xml2+"<flag>failure</flag>";
                //     xml2=xml2+"<suppl_error>More than one Live Supplement Number</suppl_error>";
                // }

                xml2 = xml2 + "</response>";
                out.println(xml2);
                System.out.println(xml2);

            } catch (Exception e) {
                System.out.println("Unable to load Supplement Number " + e);
            }


        }
        
        else if (strCommand.equalsIgnoreCase("solveSupNo")) {
System.out.println("solveSupNo");
            Statement st1 = null,st2 = null;
            ResultSet rs1 = null,rs2 = null;
            String sql1 = null,sql2 = null;
            int ssno=0;
            try {
                ssno =Integer.parseInt(request.getParameter("ssno"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            
            String xml2 = "<response><command>solveSupNo</command>";

//            sql1 ="select SUPPLEMENT_NO from FAS_SUPPLEMENT_GJV where status='L' and cashbook_year = " +
//        txtCash_year + " and cashbook_month =" + txtCash_Month_hid+" and SUPPLEMENT_NO!="+ssno+" order by SUPPLEMENT_NO";

            sql1="SELECT supplement_no FROM fas_trial_balance_status_sjv WHERE accounting_unit_id ="+cmbAcc_UnitCode+" AND cashbook_month  ="+txtCash_Month_hid+" AND cashbook_year= "+txtCash_year+" AND tb_status ='Y' AND supplement_no ="+ssno;

            System.out.println(sql1);

            try {
                st1 = con.createStatement();
                rs1 = st1.executeQuery(sql1);
             
                
              if(rs1.next()) {
                  xml2 = xml2 + "<flag>TBfailure</flag>";
                  xml2 = xml2 + "<supnumber>"+"TB status closed for this Supplement Number:"+ssno+"</supnumber>";
              }
//              else {
//                  
//                  sql2 ="select SUPPLEMENT_NO from FAS_SUPPLEMENT_GJV where status='L' and cashbook_year = " +
//                          txtCash_year + " and cashbook_month =" + txtCash_Month_hid+" and " +
//                          "SUPPLEMENT_NO!="+ssno+" order by SUPPLEMENT_NO";
//                          try{
//                              st2 = con.createStatement();
//                              rs2 = st1.executeQuery(sql2);
//                                 if(rs2.next()) 
//                                 {
//                                        
//                                         int s1=rs2.getInt("SUPPLEMENT_NO");
//                                         System.out.println("s1>>>>>>>>>>>>>"+s1);
//                                             if(s1<ssno){
//                                                            xml2 = xml2 + "<flag>supsuccess</flag>";
//                                                            xml2 = xml2 + "<supnumber>"+"Choose Supplement Number:"+s1+"</supnumber>";
//                                                          
//                                                        }
//                                                        else
//                                                        {
//                                                            xml2 = xml2 + "<flag>supfailure</flag>";
//                                                            xml2 = xml2 + "<supnumber>"+ssno+"</supnumber>";
//                                                        }
//                                     
//                                 }  
//                              
//                          }
//                          catch(Exception e1) {
//                              System.out.println("Exception in 2nd query"+e1);
//                          }
//                  
//              }
                else{
                    sql2="SELECT SUPPLEMENT_NO FROM FAS_SUPPLEMENT_GJV WHERE status ='L' " +
                    " AND cashbook_year     ="+txtCash_year+" AND " +
                    " cashbook_month  ="+txtCash_Month_hid+" AND SUPPLEMENT_NO not in (SELECT supplement_no " +
                    " FROM fas_trial_balance_status_sjv WHERE accounting_unit_id ="+cmbAcc_UnitCode+" AND " +
                    " cashbook_month  ="+txtCash_Month_hid+" AND cashbook_year=  "+txtCash_year+" AND tb_status ='Y') order by SUPPLEMENT_NO  ";
                    try{
                        st2 = con.createStatement();
                        rs2 = st1.executeQuery(sql2);
                        while(rs2.next()) 
                        {
                            xml2 = xml2 + "<flag>supsuccess</flag>";
                            xml2 = xml2 + "<supnumber>"+rs2.getInt("SUPPLEMENT_NO")+"</supnumber>";
                                                                                       
                        }
                      
                    }
                    catch(Exception e1) {
                        System.out.println("Exception in 2nd query"+e1);
                    }
                }
                xml2 = xml2 + "</response>";
                out.println(xml2);
                System.out.println(xml2);

            } catch (Exception e) {
                System.out.println("Unable to load Supplement Number " + e);
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
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        doPost(request, response);
    }


}

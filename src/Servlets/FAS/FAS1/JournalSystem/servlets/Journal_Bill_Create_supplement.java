package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Journal_Bill_Create_supplement
 */
public class Journal_Bill_Create_supplement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Journal_Bill_Create_supplement() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

        String strCommand = "";
        Connection con = null;
        ResultSet rs = null;
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtJournalVou_No = 0;
            //int txtCash_Acc_code=0;
            int Total_TRN_Rec = 0;
            //double txtAmount=0;
            String txtCheque_NO = "", txtCB_REF_TYPE = "";

            Date txtCrea_date = null, txtCheque_date = null;
            String txtRemarks = "";

            int cmbMas_SL_type = 0, cmbMas_SL_Code = 0;
            String txtMode_of_creat = "M", txtCreat_By_Module = "LJV";
            double dep_rate = 0; // changes here
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            //Timestamp ts = new Timestamp(l);

            //For Banking Purpose


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

            /*  try{txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Acc_code "+txtCash_Acc_code);*/

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

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

            /*  try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+txtAmount);*/

            //txtCheque_NO=request.getParameter("txtCheque_NO");                //no need in bill type*****************
            System.out.println("txtCheque_NO " + txtCheque_NO);

            /*  if(!request.getParameter("txtCheque_date").equalsIgnoreCase(""))  // no need in bill type*****************
            {
            sd=request.getParameter("txtCheque_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtCheque_date=new Date(d.getTime());
            }*/
            System.out.println("txtCheque_date " + txtCheque_date);
            // changes here
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


            System.out.println("cmbMas_SL and SL_CODE " + cmbMas_SL_type +
                               " " + cmbMas_SL_Code); //+" "+cmbMas_offid);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");
              //  int supplement_no=0;
                String No_TRN_Rec[] = request.getParameterValues("H_code");
                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");  
    			SimpleDateFormat objTs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
    			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                        Integer.parseInt(sd[0]));
           d = c.getTime();
           txtCrea_date = new Date(d.getTime());

           	Timestamp ts=new Timestamp(l); 

                Total_TRN_Rec =
                        No_TRN_Rec.length; //Integer.parseInt(No_TRN_Rec);
                System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
                cs =
  con.prepareCall("call FAS_JOURNAL_MASTER_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?,?,?::int,?,?)");

  //con.prepareCall("call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//                cs.setInt(1, cmbAcc_UnitCode);
//                cs.setInt(2, cmbOffice_code);
//                cs.setInt(3, txtCash_year);
//                cs.setInt(4, txtCash_Month_hid);
//                cs.setInt(5, txtJournalVou_No);
//                cs.setDate(6, txtCrea_date);
//                // cs.setString(7,txtReceipt_type);
//                //  cs.setInt(8,txtCash_Acc_code);
//                cs.setInt(7, cmbMas_SL_type);
//                cs.setInt(8, cmbMas_SL_Code);
//                cs.setDouble(9, dep_rate);
//                cs.setString(10, txtCheque_NO);
//                cs.setDate(11, txtCheque_date);
//                cs.setString(12, txtCB_REF_TYPE);
//                // cs.setInt(13,txtCB_REF_NO);
//                // cs.setDate(14,txtCB_REF_DATE);
//                // cs.setDouble(19,txtAmount);
//                cs.setInt(13, Total_TRN_Rec);
//                cs.setString(14, txtRemarks);
//                cs.setString(15, txtMode_of_creat);
//                cs.setString(16, txtCreat_By_Module);
//                cs.setString(17, "insert");
//                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
//                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
//                cs.setString(19, update_user);
//                cs.setTimestamp(20, ts);
//              //  cs.setInt(21,supplement_no);
//                System.out.println("b4 exe ");
//                cs.execute();
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
                // cs.setInt(13,txtCB_REF_NO);
                // cs.setDate(14,txtCB_REF_DATE);
                // cs.setDouble(19,txtAmount);
                cs.setInt(13, Total_TRN_Rec);
                cs.setString(14, txtRemarks);
                cs.setString(15, txtMode_of_creat);
                cs.setString(16, txtCreat_By_Module);
                cs.setString(17, "update");
                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
                cs.setNull(5, java.sql.Types.NUMERIC);
                cs.setNull(18, java.sql.Types.NUMERIC);
                cs.setString(19, update_user);
                cs.setTimestamp(20, ts);
                System.out.println("b4 exe ");
                cs.execute();
                txtJournalVou_No = cs.getInt(5);
                int errcode = cs.getInt(18);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The  Voucher Number Creation Failed ", "ok");
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
                        txtBill_no = "";
                        txtBill_Type = "";
                        txtBill_Date = null;
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
                                "The  Voucher Number '" + txtJournalVou_No +
                                "' has been Created Successfully ", "ok");
                }

            }

            catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("exception in rollback " + sqle);
                }
                sendMessage(response, "The  Voucher Number Creation Failed ",
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
    
		
	}
	private void sendMessage(HttpServletResponse response, String msg,
	        String bType) {
	try {
	String url =
	"org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
	"&button=" + bType;
	response.sendRedirect(url);
	} catch (Exception e) {
	System.out.println("error in messenger" + e);
	}
	}
}


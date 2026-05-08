package Servlets.FAS.FAS1.JournalSystem.servlets;

import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Journal_Bill_Edit extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String strCommand = "";
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null, cs1 = null;
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
                String No_TRN_Rec[] = request.getParameterValues("H_code");
                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");  
    			SimpleDateFormat objTs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
                //java.sql.Date txtCrea_date = java.sql.Date.valueOf( "txtCrea_date" );
                //long epoch = objTs.parse("ts").getTime();    
               
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
                cs.registerOutParameter(18, java.sql.Types.INTEGER);
                //cs.setNull(5, java.sql.Types.NUMERIC);
                cs.setNull(18, java.sql.Types.NUMERIC);
                cs.setString(19, update_user);
                cs.setTimestamp(20, ts);
                System.out.println("b4 exe ");
                cs.execute();
                txtJournalVou_No = cs.getBigDecimal(5).intValue();
                int errcode = cs.getInt(18);
                //int errcode = cs.getBigDecimal(18).intValue();
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The  Voucher Number Creation Failed ", "ok");
                    xml = xml + "<flag>failure</flag>";
                } else {
                    String sql_del =
                        "delete from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" +
                        "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and VOUCHER_NO=? ";
                    ps = con.prepareStatement(sql_del);
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setInt(3, txtCash_year);
                    ps.setInt(4, txtCash_Month_hid);
                    ps.setInt(5, txtJournalVou_No);
                    ps.executeUpdate(); // deletion from transaction table
                    ps.close();

                    String Grid_H_code[] =
                        request.getParameterValues("H_code");
                    
                    System.out.println("H_code>>>>>>"+request.getParameterValues("H_code"));
                    String Grid_CR_DR_type[] =
                        request.getParameterValues("CR_DR_type");
                    String Grid_SL_type[] =
                        request.getParameterValues("SL_type");
                    String Grid_SL_code[] =
                        request.getParameterValues("SL_code");
                    // String Grid_rec_from[]=request.getParameterValues("rec_from");
                    
                    
//                    
                    
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
                    
                    
                    
                    System.out.println("Amount>>>>>>"+request.getParameterValues("sl_amt"));
                    
                    String CB_REF_NO[] =
                            request.getParameterValues("CB_REF_NO");
                        String CB_REF_DATE[] =
                            request.getParameterValues("CB_REF_DATE");
                        
                        String MULTIPLE_PVRS[] =
                                request.getParameterValues("MULTIPLE_PVRS");                        
                        String MULTIPLE_PVR_DETAILS[] =
                                request.getParameterValues("MULTIPLE_PVR_DETAILS");                        
                        String CB_TDCA_REF_NO[] =
                                request.getParameterValues("CB_TDCA_REF_NO");                        
                        String CB_TDCA_REF_DATE[] =
                                request.getParameterValues("CB_TDCA_REF_DATE");                        
                        String CB_TPA_REF_NO[] =
                                request.getParameterValues("CB_TPA_REF_NO");                        
                        String CB_TPA_REF_DATE[] =
                                request.getParameterValues("CB_TPA_REF_DATE");
                    
                    
                    String sql =
                            "insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                            "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                            "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                            "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
                            "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,MULTIPLE_PVRS,MULTIPLE_PVR_DETAILS,CB_TDCA_REF_NO," +
                            "CB_TDCA_REF_DATE,CB_TPA_REF_NO,CB_TPA_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?::numeric,?,?::numeric,?,?,?)";
                    
                    
                   
                    int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code =
                        0, cmbSL_type = 0,txtCB_REF_NO = 0;
                    Date txtBill_Date = null, txtAgree_Date =
                        null, txtCheque_DD_date = null, txtCB_REF_DATE = null,txtCB_TDCA_REF_DATE=null,txtCB_TPA_REF_DATE=null;
                    double txtsub_Amount = 0;
                    String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
                        "", txtAgree_No = "", txtParticular = "";
                    String txtCheque_DD = "", txtCheque_DD_NO = "";
                    String txtMULTIPLE_PVRS="",txtMULTIPLE_PVR_DETAILS="",txtCB_TDCA_REF_NO="",txtCB_TPA_REF_NO="";

                    ps = con.prepareStatement(sql);
                    System.out.println("length of TRN" + Grid_H_code.length);
                    for (int k = 0; k < Grid_H_code.length; k++) {
                        try {
                            txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        System.out.println("txtAcc_HeadCode>>>>>>>"+txtAcc_HeadCode);
                        
                        rad_sub_CR_DR = Grid_CR_DR_type[k];
                        System.out.println("rad_sub_CR_DR>>>>>>>"+rad_sub_CR_DR);

                        try {
                            cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        System.out.println("cmbSL_type>>>>>>>"+cmbSL_type);
                        try {
                            cmbSL_Code = Integer.parseInt(Grid_SL_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        
                        System.out.println("cmbSL_Code>>>>>>>"+cmbSL_Code);
                        
//                        System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
//                        System.out.println("Grid_CR_DR_type[k] " +
//                                           Grid_CR_DR_type[k]);
//                        System.out.println("Grid_SL_type[k]" +
//                                           Grid_SL_type[k] + "u");
//                        System.out.println("Grid_SL_code[k]" +
//                                           Grid_SL_code[k] + "from here" +
//                                           cmbSL_Code);
                        
                        
                        
                        //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                        //txtsub_Recei_from=Grid_rec_from[k];
                        
                            System.out.println("&&&&&&&&&&&&&"+request.getParameterValues("CB_REF_NO"));
                            System.out.println("&&&&&&&&&&&&&"+request.getParameterValues("CB_REF_DATE"));
                            try
                            {
                            txtCB_REF_NO = Integer.parseInt(CB_REF_NO[k]); 
                            }
                            catch(Exception e)
                            {
                        	System.out.println("Exception in Cb_Ref_No>>>>>>"+txtCB_REF_NO);
                            }
                       // System.out.println("date----outside------CB_REF_DATE[k]----------------"+CB_REF_DATE[k]);
                        try {
                        	//if ((!CB_REF_DATE[k].equalsIgnoreCase(null)) && (!CB_REF_DATE[k].equalsIgnoreCase("")) ){
                        	if (!CB_REF_DATE[k].equalsIgnoreCase("")) {
                        		System.out.println("date----inside------CB_REF_DATE[k]------is not null----------"+CB_REF_DATE[k]);
                        		
                        		sd = CB_REF_DATE[k].split("/");
                                c =
       new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                             Integer.parseInt(sd[0]));
                                d = c.getTime();
                                txtCB_REF_DATE = new Date(d.getTime());
                            }
                        	else
                        	{
                        		txtCB_REF_DATE=null;
                        	}
                        	
                        	
                        } catch (Exception e) {
                            System.out.println(
                            		"Exception in txtCB_REF_DATE"+ e);
                        }
                        
                        try{
                        	txtMULTIPLE_PVRS=MULTIPLE_PVRS[k];
                        	System.out.println("txtMULTIPLE_PVRS>>>>"+txtMULTIPLE_PVRS);
                        	txtMULTIPLE_PVR_DETAILS=MULTIPLE_PVR_DETAILS[k];
                        	System.out.println("txtMULTIPLE_PVR_DETAILS>>>>"+txtMULTIPLE_PVR_DETAILS);
                        	txtCB_TDCA_REF_NO=CB_TDCA_REF_NO[k];
                        	System.out.println("txtCB_TDCA_REF_NO>>>>"+txtCB_TDCA_REF_NO);
                        }catch(Exception e){
                        	System.out.println("Exception>>"+e);
                        }
                        
                        try{
                        	if (!CB_TDCA_REF_DATE[k].equalsIgnoreCase("")) {
                        		System.out.println("date----inside------CB_TDCA_REF_DATE[k]------is not null----------"+CB_TDCA_REF_DATE[k]);
                        		
                        		sd = CB_TDCA_REF_DATE[k].split("/");
                                c =
       new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                             Integer.parseInt(sd[0]));
                                d = c.getTime();
                                txtCB_TDCA_REF_DATE = new Date(d.getTime());
                            }
                        	else
                        	{
                        		txtCB_TDCA_REF_DATE=null;
                        	}
                        }catch(Exception e){
                        	System.out.println("Exception in "+e);
                        }
                        try{
                        	txtCB_TPA_REF_NO=CB_TPA_REF_NO[k];
                        	if (!CB_TPA_REF_DATE[k].equalsIgnoreCase("")) {
                        		System.out.println("date----inside------CB_TPA_REF_DATE[k]------is not null----------"+CB_TPA_REF_DATE[k]);
                        		
                        		sd = CB_TPA_REF_DATE[k].split("/");
                                c =
       new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                             Integer.parseInt(sd[0]));
                                d = c.getTime();
                                txtCB_TPA_REF_DATE = new Date(d.getTime());
                            }
                        	else{
                        		txtCB_TPA_REF_DATE=null;
                        	}
                        	
                        }catch(Exception e){
                        	System.out.println("Exception in>>>"+e);
                        }
                        
//                        System.out.println("Outside Ref Date******&&&&&&&******>"+CB_REF_DATE[k]);
//                        if ((!CB_REF_DATE[k].equalsIgnoreCase(""))&& (!CB_REF_DATE[k].equalsIgnoreCase(null)))
//                        {
//                         System.out.println("Inside Ref Date *****###NULL###*******>"+CB_REF_DATE[k]);
//                        	
//                        	sd = CB_REF_DATE[k].split("/");
//                            c =
//                            		new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
//                         Integer.parseInt(sd[0]));
//                            d = c.getTime();
//                            txtCB_REF_DATE = new Date(d.getTime());
//                        	
//                        	
//                        }
//                        else
//                        {
//                        	System.out.println("Inside Ref Date ELSE PART*****###NULL###*******>"+CB_REF_DATE[k]);
//                        }
//                        System.out.println("CB_REF_DATE[k]" +
//                        		CB_REF_DATE[k] + "from here" +
//                        		txtCB_REF_DATE); 
                        
                        
                        txtBill_no = Grid_Bill_No[k];

                        System.out.println("txtBill_no>>>>>>>"+txtBill_no);
                        try
                        {
//                        System.out.println("inside try block");
                        	txtBill_Type = Grid_Bill_type[k];
//                        	System.out.println("Grid_Bill_type[k]>>>>"+Grid_Bill_type[k]);
                        }
                        catch(Exception e)
                        {
                        	System.out.println("Exeption billtype>>>>"+e);
                        }
                        System.out.println("txtBill_Type>>>>>>>"+txtBill_Type);
                        
                        if (!Grid_Bill_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Bill_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtBill_Date = new Date(d.getTime());
                        }
                        
                        System.out.println("txtBill_Date>>>>>>>"+txtBill_Date);
//System.out.println("Grid_Bill_date===>"+Grid_Bill_date);
                        try{
                        txtAgree_No = Grid_Agree_No[k];
                        
                        System.out.println("txtAgree_No>>>>>>>"+txtAgree_No);
                        
                        if (!Grid_Agree_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Agree_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtAgree_Date = new Date(d.getTime());
                        }
                        }catch(Exception e)
                        {
                          System.out.println("Exception in Agree_No,Date>>>>"+e);
                        }
                        System.out.println("txtAgree_Date>>>>>>>"+txtAgree_Date);
                        
                        System.out.println("txtCB_REF_NO..." + txtCB_REF_NO);
                        System.out.println("txtCB_REF_DATE..." + txtCB_REF_DATE);
                        
                        System.out.println("txtBill_no..." + txtBill_no);
                        System.out.println("txtBill_Type..." + txtBill_Type);
                        System.out.println("txtBill_Date..." + txtBill_Date);
                        System.out.println("txtAgree_No..." + txtAgree_No);
                        System.out.println("txtAgree_Date..." + txtAgree_Date);

                        try{
                        txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                        }catch(Exception e){
                        	System.out.println("Exception in amount>>>>"+txtsub_Amount);
                        }
                        try{
                        txtParticular = Grid_particular[k];
                        }catch(Exception e){
                        	System.out.println("Exception in Particular>>>"+txtParticular);
                        }
                        System.out.println("amount");
//                        System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);
                        // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
//                        System.out.println("Grid_particular[k] " +
//                                           Grid_particular[k]);
                      
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
                        ps.setString(23, txtMULTIPLE_PVRS);
                        ps.setString(24, txtMULTIPLE_PVR_DETAILS);
                        if(!txtCB_TDCA_REF_NO.equals(""))
                        	ps.setInt(25, Integer.parseInt(txtCB_TDCA_REF_NO));
                        else
                        	ps.setInt(25, 0);
                        
                        ps.setDate(26, txtCB_TDCA_REF_DATE);
                        if(!txtCB_TPA_REF_NO.equals(""))
                        	ps.setInt(27, Integer.parseInt(txtCB_TPA_REF_NO));
                        else
                        	ps.setInt(27, 0);
                        	
                       // ps.setInt(27, Integer.parseInt(txtCB_TPA_REF_NO));
                        ps.setDate(28, txtCB_TPA_REF_DATE);
                                                
                        ps.setString(29, update_user);
                        ps.setTimestamp(30, ts);
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
                    String txtReferNO_edit = "", txtRemak_edit =
                        "", txtRefdate = ""; // for cross reference
                    Date txtReferDate_edit = null;
                    String radAuth_MC = "";
                    int txtAuth_By = 0;

                    System.out.println("txtReferDate_edit " +
                                       txtReferDate_edit);
                    cs1 =
 con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
                    cs1.setInt(1, cmbAcc_UnitCode);
                    cs1.setInt(2, txtCash_year);
                    cs1.setInt(3, txtCash_Month_hid);
                    cs1.setInt(4, txtJournalVou_No);
                    cs1.setInt(5, cmbOffice_code);
                    cs1.setDate(6, txtCrea_date);
                    cs1.setString(7, "LJV");
                    cs1.setString(8, txtReferNO_edit);
                    cs1.setDate(9, txtReferDate_edit);
                    cs1.setString(10, txtRemak_edit);
                    cs1.setInt(11, txtAuth_By);
                    cs1.setString(12, "insert");
                    cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
                    cs1.setNull(13,java.sql.Types.NUMERIC);
                    cs1.setString(14, update_user);
                    cs1.setTimestamp(15, ts);
                    cs1.setString(16, radAuth_MC);
                    cs1.execute(); // insertion into cross reference table
                    //errcode = cs1.getInt(13);
                     errcode = cs1.getBigDecimal(13).intValue();
                    System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                    System.out.println("cmbOffice_code " + cmbOffice_code);
                    System.out.println("txtCrea_date " + txtCrea_date);
                    System.out.println("txtCash_year " + txtCash_year);
                    System.out.println("txtCash_Month_hid " +
                                       txtCash_Month_hid);
                    System.out.println("SQLCODE:::" + errcode);
                    if (errcode != 0) {
                        con.rollback();
                        sendMessage(response,
                                    "The Voucher Number Modification Failed ",
                                    "ok");
                        xml = xml + "<flag>failure</flag>";
                    }

                    System.out.println("b4 commit");
                    con.commit();
                    sendMessage(response,
                                "The  Voucher Number '" + txtJournalVou_No +
                                "' has been Modified Successfully ", "ok");
                }

            }

            catch (Exception e) {
            e.printStackTrace();
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("exception in rollback " + sqle);
                }
                sendMessage(response,
                            "The  Voucher Number Modification Failed ", "ok");
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

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
        //CallableStatement cs=null,cs1=null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;

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
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign.. command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;
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

        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {
        	System.out.println("***************load_Voucher_No*******************");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            xml = "<response><command>load_Voucher_No</command>";
            System.out.println("date" + request.getParameter("txtCrea_date"));
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            try {
                // ps=con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='LJV' and JOURNAL_STATUS!='C'");
                ps =
  con.prepareStatement("select i.VOUCHER_NO from FAS_JOURNAL_MASTER i,FAS_CROSS_REFERENCE c where " +
                       " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.VOUCHER_DATE=? and i.JOURNAL_STATUS!='C'  and CREATED_BY_MODULE='LJV' " +
                       " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                       " and i.VOUCHER_DATE=c.ORIGINAL_DATE and i.VOUCHER_NO=c.VOUCHER_NO " +
                       " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='M' and DOC_TYPE='LJV'");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                rs = ps.executeQuery();
                int count = 0;
                while (rs.next()) {

                    xml =
 xml + "<Rec_No>" + rs.getInt("VOUCHER_NO") + "</Rec_No>";
                    count++;
                }
                if (count == 0)
                    xml = xml + "<flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";
                System.out.println("count  " + count);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (strCommand.equalsIgnoreCase("load_Voucher_Details")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            xml = "<response><command>load_Voucher_Details</command>";
            int txtJournalVou_No = 0;
            // Date txtCrea_date;

            try {
                txtJournalVou_No =
                        Integer.parseInt(request.getParameter("txtJournalVou_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtJournalVou_No " + txtJournalVou_No);

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            try {
                ps =
  con.prepareStatement("select CASHBOOK_YEAR,CASHBOOK_MONTH,CHEQUE_NO,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE ,to_char(CHEQUE_DATE,'DD/MM/YYYY')as CHEQ_date,REMARKS,TOTAL_TRN_RECORDS from  FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtJournalVou_No);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<cheq_No>" + rs.getString("CHEQUE_NO") + "</cheq_No><cheq_Date>" +
   rs.getString("CHEQ_date") + "</cheq_Date><No_TRN_Rec>" +
   rs.getInt("TOTAL_TRN_RECORDS") + "</No_TRN_Rec><Remak><![CDATA[" +
   rs.getString("REMARKS") + "]]></Remak><Mas_SL_type>" +
   rs.getInt("JOURNAL_TYPE_CODE") + "</Mas_SL_type><Mas_SL_code>" +
   rs.getInt("SUB_LEDGER_CODE") + "</Mas_SL_code>";
                }
//Loading master SL code and SL Type loading in Edit Journal----
                // Start of fetching sub-Ledger   , here u r passing parameters to the function getResult_General which is inside the class SL_TYPE_CODE_NAME_GENERAL
                if (rs.getInt("JOURNAL_TYPE_CODE") != 0) {
                    SL_TYPE_CODE_NAME_GENERAL obj_gen =new SL_TYPE_CODE_NAME_GENERAL();
                    ResultSet rs_get =obj_gen.getResult_General(cmbAcc_UnitCode,
                                                  cmbOffice_code,
                                                  rs.getInt("JOURNAL_TYPE_CODE"),
                                                  rs.getInt("SUB_LEDGER_CODE"),
                                                  0);
                    if (rs_get != null) {
                        while (rs_get.next()) {
                          //  System.out.println("cid my "+rs_get.getString("cid"));
                          //  System.out.println("cname My "+rs_get.getString("cname"));
                            xml = xml + "<cid>" + rs_get.getInt("cid") + "</cid><cname><![CDATA[" +rs_get.getString("cname") + "]]></cname>";
                        }
                        rs_get.close();
                    } else
                        System.out.println("null result set");
                }
                // End of fetching sub-Ledger

                //System.out.println("in b/w here"+rs.getInt("CASHBOOK_MONTH"));
                System.out.println("in b/w here for loading details");

                ps2 = con.prepareStatement("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ," +
                      "SUB_LEDGER_CODE,BILL_NO, BILL_TYPE, AGREEMENT_NO, to_char(AGREEMENT_DATE,'DD/MM/YYYY') as agree_date," +
                      "to_char(BILL_DATE,'DD/MM/YYYY') as b_date  " +
                      ",trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS,VERIFIED," + 
                      " to_char(VERIFIED_DATE,'DD/MM/YYYY') as VERIFIED_DATE,VERIFIED_AUTHORITY,CB_REF_NO,to_char(CB_REF_DATE,'DD/MM/YYYY') as CB_REF_DATE,COALESCE(NULLIF(MULTIPLE_PVRS, ''), NULL) as MULTIPLE_PVRS,COALESCE(NULLIF(MULTIPLE_PVR_DETAILS, ''), NULL) as MULTIPLE_PVR_DETAILS,CB_TDCA_REF_NO,TO_CHAR(CB_TDCA_REF_DATE,'DD/MM/YYYY') as CB_TDCA_REF_DATE,CB_TPA_REF_NO," +
                      " TO_CHAR(CB_TPA_REF_DATE,'DD/MM/YYYY') AS CB_TPA_REF_DATE from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and " +
                      " ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
                
                System.out.println("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ," +
                        "SUB_LEDGER_CODE,BILL_NO, BILL_TYPE, AGREEMENT_NO, to_char(AGREEMENT_DATE,'DD/MM/YYYY') as agree_date," +
                        "to_char(BILL_DATE,'DD/MM/YYYY') as b_date  " +
                        ",trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS,VERIFIED," + 
                        " to_char(VERIFIED_DATE,'DD/MM/YYYY') as VERIFIED_DATE,VERIFIED_AUTHORITY,CB_REF_NO,to_char(CB_REF_DATE,'DD/MM/YYYY') as CB_REF_DATE,MULTIPLE_PVRS,MULTIPLE_PVR_DETAILS,CB_TDCA_REF_NO,TO_CHAR(CB_TDCA_REF_DATE,'DD/MM/YYYY') as CB_TDCA_REF_DATE,CB_TPA_REF_NO," +
                        " TO_CHAR(CB_TPA_REF_DATE,'DD/MM/YYYY') AS CB_TPA_REF_DATE from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
                      "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+rs.getInt("CASHBOOK_YEAR")+" and CASHBOOK_MONTH="+rs.getInt("CASHBOOK_MONTH")+" and VOUCHER_NO="+txtJournalVou_No);
                
                
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                ps2.setInt(3, rs.getInt("CASHBOOK_YEAR"));
                ps2.setInt(4, rs.getInt("CASHBOOK_MONTH"));
                ps2.setInt(5, txtJournalVou_No);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    xml = xml + "<AHcode>" + rs2.getInt("ACCOUNT_HEAD_CODE") + "</AHcode>";
                    ps3 = con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml = xml + "<AHdesc>" + rs3.getString("ACCOUNT_HEAD_DESC") + "</AHdesc>";
                    ps3.close();
                    rs3.close();
                    xml = xml + "<CR_DR_ind>" + rs2.getString("CR_DR_INDICATOR") +  "</CR_DR_ind><SL_Type>" + rs2.getInt("SUB_LEDGER_TYPE_CODE") + "</SL_Type>";

                    if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                        System.out.println("take SL DESC");
                        ps3 = con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                        ps3.setInt(1, rs2.getInt("SUB_LEDGER_TYPE_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml = xml + "<SL_Desc><![CDATA[" + rs3.getString("SUB_LEDGER_TYPE_DESC") + "]]></SL_Desc>";
                    } else {
                        xml = xml + "<SL_Desc>" + null + "</SL_Desc>"; // it also return null value
                        System.out.println("else part  23");
                    }

                    rs3.close();
                    ps3.close();

                    xml = xml + "<SL_Code>" + rs2.getInt("SUB_LEDGER_CODE") + "</SL_Code>";

                    if (rs2.getInt("SUB_LEDGER_CODE") != 0) {
                        SL_TYPE_CODE_NAME_DETAILS obj_det =
                            new SL_TYPE_CODE_NAME_DETAILS();
                        ResultSet rs_det =
                            obj_det.getResult_Details(cmbAcc_UnitCode,
                                                      cmbOffice_code,
                                                      rs2.getInt("SUB_LEDGER_TYPE_CODE"),
                                                      rs2.getString("SUB_LEDGER_CODE"),
                                                      0);
                        if (rs_det != null) {
                            if (rs_det.next()) {
                                System.out.println(rs_det.getString("cid"));
                                System.out.println(rs_det.getString("cname"));
                                xml = xml + "<desc_type><![CDATA[" + rs_det.getString("cname") + "]]></desc_type>";
                            }
                            rs_det.close();
                        } else
                        {
                            System.out.println("null result set");
                        xml = xml + "<desc_type>" + null + "</desc_type>";
                        }

                    } else {
                        xml = xml + "<desc_type>" + null + "</desc_type>";
                    }


                    xml = xml + "<Bill_NO>" + rs2.getString("BILL_NO") + "</Bill_NO>" + "<Bill_date>" +
   rs2.getString("b_date") + "</Bill_date>" + "<Bill_type>" +
   rs2.getString("BILL_TYPE") + "</Bill_type>" + "<Agree_No>" +
   rs2.getString("AGREEMENT_NO") + "</Agree_No>" + "<Agree_date>" +
   rs2.getString("agree_date") + "</Agree_date>" + "<sub_amount>" +
   rs2.getString("AMOUNT") + "</sub_amount><sub_part><![CDATA[" +
   rs2.getString("PARTICULARS") + "]]></sub_part><CB_REF_NO>" + rs2.getInt("CB_REF_NO") + "</CB_REF_NO><CB_REF_DATE>" + 
   rs2.getString("CB_REF_DATE") + "</CB_REF_DATE><MULTIPLE_PVRS>"+rs2.getString("MULTIPLE_PVRS")+"</MULTIPLE_PVRS><MULTIPLE_PVR_DETAILS>" +
   rs2.getString("MULTIPLE_PVR_DETAILS") + "</MULTIPLE_PVR_DETAILS><CB_TDCA_REF_NO>"+rs2.getString("CB_TDCA_REF_NO")+"</CB_TDCA_REF_NO><CB_TDCA_REF_DATE>" +rs2.getString("CB_TDCA_REF_DATE") + "</CB_TDCA_REF_DATE><CB_TPA_REF_NO>" +
   rs2.getString("CB_TPA_REF_NO") + "</CB_TPA_REF_NO><CB_TPA_REF_DATE>" + rs2.getString("CB_TPA_REF_DATE") +"</CB_TPA_REF_DATE> <VERIFIED>"+rs2.getString("VERIFIED")+"</VERIFIED><VERIFIED_DATE>"+rs2.getString("VERIFIED_DATE") +"</VERIFIED_DATE><VERIFIED_AUTHORITY>" + 
   rs2.getInt("VERIFIED_AUTHORITY") + "</VERIFIED_AUTHORITY>";
                }

            } catch (Exception e) {
                System.out.println("catch..HERE.in failure to retrieve." + e);
                xml = "<response><command>load_Voucher_Details</command><flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
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

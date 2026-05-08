package Servlets.FAS.FAS1.Imprest.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.CommonControls.servlets.EmployeeDetails;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

import Servlets.Security.classes.UserProfile;

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

public class Imprest_Refund_Receipt_Edit extends HttpServlet
{
   
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    	
    	/**
         * Variables Declaration
         */
        String strCommand="";
        Connection con=null;
        CallableStatement cs=null,cs1=null;
        PreparedStatement ps=null;
        String xml="";
        
        
        /**
         * Session Checking 
         */
        HttpSession session=request.getSession(false);
        try
        {
           
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
            System.out.println("Redirect Error :"+e);
        } 
        
        
        /**
         * Database Connection 
         */
        try {
                               ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                               String ConnectionString="";

                               String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                               String strdsn=rs1.getString("Config.DSN");
                               String strhostname=rs1.getString("Config.HOST_NAME");
                               String strportno=rs1.getString("Config.PORT_NUMBER");
                               String strsid=rs1.getString("Config.SID");
                               String strdbusername=rs1.getString("Config.USER_NAME");
                               String strdbpassword=rs1.getString("Config.PASSWORD");
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
              {
                  System.out.println("Exception in opening connection :"+e);                             
              }

              
        /**
         *  Get Command Parameter 
         */
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }                
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        
        
        /**
         *  Main Command Parameter for Updating Records 
         */
        if(strCommand.equalsIgnoreCase("Add")) 
        {
        
            /**
             * Set Content Type 
             */
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml="<response><command>Add</command>";
            
            
            /**
             * Variables Declaration 
             */
            Calendar c;
            int txtAcc_HeadCode=0;
            int cmbAcc_UnitCode=0;
            int cmbOffice_code=0;
            int txtCash_Month_hid=0;
            int txtCash_year=0,txtReceipt_No=0;
            int txtCash_Acc_code=0,txtCash_Acc_code_cash=0;
            int Total_TRN_Rec=0;
            double txtAmount=0;
            String txtReceipt_type="";
            String txtCR_DB="";
            String txtRecei_from="";
            Date txtCrea_date=null;
            Date txtRef_date=null;
            String txtRef_no="";
            String txtRemarks="";
            int cmbMas_SL_type=0;
            int cmbMas_SL_Code=0;
            
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);
            
            String txtRec_Vou_type="";
            String txtMode_of_creat="",doc_type="";
            String txtCreat_By_Module="";
            int txtJournal_code=0;
            Date txtCha_Date=null;
            Date txtRec_Vou_date=null;
            int txtBankId=0;
            int txtBranchId=0;
            int txtNo_of_pay_voucher=0;
            int txtCha_No=0;
            int txtRec_Vou_No=0;
            long txtBankAccountNo=0;
             
            String rad_ReClass="";
            rad_ReClass="N";
            
                
            String rem_current_month="";
            rem_current_month="Y";
                
            
            /* Mode of Receipt */       
            String txtAmtType="";
            try{txtAmtType=request.getParameter("txtAmtType");}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmtType "+txtAmtType);
            
            if ( txtAmtType.equalsIgnoreCase("CR"))
            {
            	txtReceipt_type="C";
            	txtCreat_By_Module="CR";
            }
            else if ( txtAmtType.equalsIgnoreCase("BR"))
            {
            	txtReceipt_type="B";
            	txtCreat_By_Module="BR";
            }
            
            
            
            /* Bank ID */
            try{txtBankId=Integer.parseInt(request.getParameter("txtBankID"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBankId "+txtBankId);
            
            
            /* Branch ID */
            try{txtBranchId=Integer.parseInt(request.getParameter("txtBranchID"));} 
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBranchId "+txtBranchId);
            
            try{txtBankAccountNo=Long.parseLong(request.getParameter("txtBankAccountNo"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBankAccountNo "+txtBankAccountNo);
            
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            try{txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Acc_code "+txtCash_Acc_code);
          
            
            try{txtCash_Acc_code_cash=Integer.parseInt(request.getParameter("txtCash_Acc_code_cash"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Acc_code_cash =====>> "+txtCash_Acc_code_cash);
            
            
            String[] sd=request.getParameter("txtCrea_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            System.out.println("txtCrea_date "+txtCrea_date);
            
            System.out.println("b4 getting month and year");
            try{txtCash_year=Integer.parseInt(sd[2]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_year "+txtCash_year);
                        
            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
            
            
            try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtReceipt_No "+txtReceipt_No);
            
            try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+txtAmount);
            
            txtCR_DB=request.getParameter("txtCR_DB");
            System.out.println("txtCR_DB "+txtCR_DB);
            
            try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
            catch(Exception e){System.out.println("exception"+e );}
            
            try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cmbMas_SL and office "+cmbMas_SL_type+" "+cmbMas_SL_Code);            
            
            txtRecei_from=request.getParameter("txtRecei_from");
            System.out.println("txtRecei_from "+txtRecei_from);            
            
            /* Payment Voucher Number txtRef_no = cmbPayVocNo */
            try{txtRec_Vou_No=Integer.parseInt(request.getParameter("cmbPayVocNo"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtRec_Vou_No = cmbPayVocNo  "+txtRec_Vou_No);
            
            
            
            /* Payment Voucher Date Ref_date = txtPaymentVoc_Date */
            String Rec_date=request.getParameter("txtPaymentVoc_Date");
            System.out.println("txtRef_date "+txtRef_date);
           
            if(!Rec_date.equals(""))                                
            {
	            sd=request.getParameter("txtPaymentVoc_Date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            d=c.getTime();
	            txtRec_Vou_date=new Date(d.getTime());
	            }
            System.out.println("after txtPaymentVoc_Date--> "+txtRec_Vou_date);
        
            try{txtMode_of_creat=request.getParameter("cmbAdvance_type");}
            catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
            
//            if(txtMode_of_creat.equals("I"))
//                doc_type="IMPR";
//            else
//                doc_type="TMPR"; 
            
            if(txtMode_of_creat.equals("I"))
            {
            	txtMode_of_creat="I";
            	doc_type="IMPR";
             
            }
            
            else if(txtMode_of_creat.equals("T"))
            {
            	txtMode_of_creat="T";
            	doc_type="TMPR";
            }
            
            
            txtRemarks=request.getParameter("txtRemarks");
            System.out.println("txtRemarks "+txtRemarks);
            
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                    
                      /** Get Receipt Creation Date */          
                        String Receipt_Creation_Date=request.getParameter("txtCrea_date");
                  
                      /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */    
                        Com_CashBook1 cb=new Com_CashBook1();
                      
                      /** Assign Cashbook Year and Month to year_month Variable */
                        String year_month=cb.cb_date(Receipt_Creation_Date).toString();  
                      
                      /** Split Cash Book Year and Month */
                        String []ym=year_month.split("/");
                      
                      /** Assign Year and Month */
                        txtCash_year=Integer.parseInt(ym[0]);
                        txtCash_Month_hid=Integer.parseInt(ym[1]);
                                 
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~                               
                        /* Get Employee Name for Received From field */ 
                        String Received_From_Txt="";
                        
                        try {
                               Received_From_Txt = EmployeeDetails.getEmployeeDetails(con,Integer.parseInt(txtRecei_from));                 
                        }
                        catch(Exception e) {
                            System.out.println(e);
                        }
                        
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                             
            /**
             * Actual Operaiton Starts Here
             */
             try 
                 {   
                     /** Make AutoCommit as fasle */ 
                     con.clearWarnings();
                     con.setAutoCommit(false);
                     
                     
                     Total_TRN_Rec=1;
                     System.out.println(Total_TRN_Rec+" Total_TRN_Rec");
                  
                     
                     /** Call fas_receipt_master_proc */
                     //cs=con.prepareCall("call FAS_RECEIPT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)") ; 
                     cs=
							 //con.prepareCall("call FAS_RECEIPT_MASTER_PROC(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?,?,?,?,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?)");              
							 con.prepareCall("call FAS_RECEIPT_MASTER_PROC(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?,?,?,?,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?)");              

                     cs.setInt(1,cmbAcc_UnitCode);
                     cs.setInt(2,txtCash_year);
                     cs.setInt(3,txtCash_Month_hid);
                     cs.setInt(4,txtReceipt_No);
                     cs.setInt(5,cmbOffice_code);
                     cs.setDate(6,txtCrea_date);
                     cs.setString(7,txtReceipt_type);
                     if(txtReceipt_type.equals("C"))
                    	 cs.setInt(8,txtCash_Acc_code_cash);
                     else
                    	 cs.setInt(8,txtCash_Acc_code);
                    	 
                     cs.setString(9,txtCR_DB);
                     cs.setInt(10,txtBankId);
                     cs.setInt(11,txtBranchId);
                     cs.setLong(12,txtBankAccountNo);
                     cs.setString(13,Received_From_Txt);
                     cs.setString(14,txtRef_no);
                     cs.setDate(15,txtRef_date);
                     cs.setInt(16,txtNo_of_pay_voucher);
                     cs.setInt(17,txtCha_No);
                     cs.setDate(18,txtCha_Date);
                     cs.setDouble(19,txtAmount);
                     cs.setInt(20,Total_TRN_Rec);
                     cs.setString(21,txtRec_Vou_type);
                     cs.setInt(22,txtRec_Vou_No);
                     cs.setDate(23,txtRec_Vou_date);
                     cs.setInt(24,txtJournal_code);
                     cs.setString(25,txtRemarks);
                     cs.setString(26,txtMode_of_creat);
                     cs.setString(27,txtCreat_By_Module);
                     cs.setString(28,"update");                     
                     cs.registerOutParameter(4,java.sql.Types.NUMERIC);
                     cs.registerOutParameter(29,java.sql.Types.NUMERIC); 
                     cs.setNull(4, java.sql.Types.NUMERIC);
                     cs.setNull(29, java.sql.Types.NUMERIC);
                     cs.setInt(30,cmbMas_SL_type);
                     cs.setInt(31,cmbMas_SL_Code);
                     cs.setString(32,update_user);
                     cs.setTimestamp(33,ts);
                     cs.setString(34,rem_current_month);
                     System.out.println("b4 exe ");
                     cs.execute();
//                     txtReceipt_No=cs.getInt(4);
//                     int errcode=cs.getInt(29);
                     txtReceipt_No = cs.getBigDecimal(4).intValue();
                    int errcode = cs.getBigDecimal(29).intValue();	
                     
                     
                     System.out.println("SQLCODE:::"+errcode);
                     
                     /** If Error Code not equal to 0 Display Error Message */
                     if(errcode!=0)
                     {
                        System.out.println("redirect");
                        sendMessage(response,"The Receipt Modification Failed ","ok");
                        xml=xml+"<flag>failure</flag>";
                     }
                     else
                     {
                       
                         /** Delete Transaction Entries */
                         String sql_del="delete from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? " +
                         "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and RECEIPT_NO=?  ";
                         ps=con.prepareStatement(sql_del);
                         ps.setInt(1,cmbAcc_UnitCode);
                     //    ps.setInt(2,cmbOffice_code);
                         ps.setInt(2,txtCash_year);
                         ps.setInt(3,txtCash_Month_hid);
                         ps.setInt(4,txtReceipt_No);
                         ps.executeUpdate();                       
                         ps.close();
                         
                         String Grid_H_code[]=request.getParameterValues("txtAcc_HeadCode");
                         String Grid_CR_DR_type[]=request.getParameterValues("rad_sub_CR_DR");
                         String Grid_SL_type[]=request.getParameterValues("cmbSL_type");
                         String Grid_SL_code[]=request.getParameterValues("cmbSL_Code");
                         String Grid_Cheque_DD[]=request.getParameterValues("txtCheque_DD");
                         String Grid_Cheque_DD_NO[]=request.getParameterValues("txtCheque_DD_NO");
                         String Grid_Cheque_DD_date[]=request.getParameterValues("txtCheque_DD_date");
                         String Grid_Bank_Name[]=request.getParameterValues("txtBank_Name");
                         String Grid_Draw_BR[]=request.getParameterValues("txtDraw_BR");
                         String Grid_Bank_M_Code[]=request.getParameterValues("txtBank_M_Code");
                         String Grid_sl_amt[]=request.getParameterValues("txtsub_Amount");
                         String Grid_particular[]=request.getParameterValues("txtParticular");
                       
                       
                       /**
                        *  Insert into transaction table 
                        */
                       String sql="insert into FAS_RECEIPT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                       "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, RECEIPT_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                       "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, RECEIVED_FROM," +
                       "CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, BANK_NAME, DRAWEE_BRANCH, " +
                       "BANK_MICR_CODE, AMOUNT, PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE, CB_REF_NO, CB_REF_DATE ) "+
                       "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                       
                               int SL_NO=1,cmbSL_type=0,cmbSL_Code=0;
                               String  rad_sub_CR_DR="",txtParticular="",txtCheque_DD="",txtCheque_DD_NO="",txtBank_Name="",txtDraw_BR="",txtBank_M_Code="",txtsub_Recei_from="";
                               Date txtCheque_DD_date=null;
                               String Remittance_Type="";
                               double txtsub_Amount=0;
                           
                               ps=con.prepareStatement(sql);
                               System.out.println("length of TRN"+Grid_H_code.length);
                               for(int k=0;k<Grid_H_code.length;k++) 
                               {

                                   try{txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   rad_sub_CR_DR=Grid_CR_DR_type[k];
                                   
                                   try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   
                                   try{ txtCheque_DD=Grid_Cheque_DD[k];
                                       Remittance_Type = txtCheque_DD;
                                   }catch(Exception e){System.out.println(e);}
                                   
                                   
                                   
                                try
                                {
                                   txtCheque_DD_NO=Grid_Cheque_DD_NO[k];
                                }
                                catch (Exception e) {
                                     System.out.println(e);
                                }
                                   
                                   
                                   
                                try
                                {
                                   if(!Grid_Cheque_DD_date[k].equalsIgnoreCase(""))
                                   {
                                   sd=Grid_Cheque_DD_date[k].split("/");
                                   c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                                   d=c.getTime();
                                   txtCheque_DD_date=new Date(d.getTime());
                                   }
                                }
                                catch(Exception e) {
                                    System.out.println(e);
                                }
                                   
                                   txtBank_Name=Grid_Bank_Name[k];
                                   txtDraw_BR=Grid_Draw_BR[k];
                                   txtBank_M_Code=Grid_Bank_M_Code[k];
                                   
                                   
                                   
                                   System.out.println("Grid_H_code[k] "+Grid_H_code[k]);
                                   System.out.println("Grid_CR_DR_type[k] "+Grid_CR_DR_type[k]);
                                   System.out.println("Grid_SL_type[k]"+Grid_SL_type[k]+"u");
                                   System.out.println("Grid_SL_code[k]"+Grid_SL_code[k]+"from here"+cmbSL_Code);
                                   System.out.println("Grid_Cheque_DD[k]"+Grid_Cheque_DD[k]);
                                   System.out.println("Grid_Cheque_DD_NO[k]"+Grid_Cheque_DD_NO[k]);
                                   System.out.println("txtCheque_DD_date"+txtCheque_DD_date+"date"+Grid_Cheque_DD_date[k]);
                                   System.out.println("Grid_Bank_Name[k]"+Grid_Bank_Name[k]);
                                   System.out.println("Grid_Draw_BR[k]"+Grid_Draw_BR[k]);
                                   System.out.println("Grid_Bank_M_Code[k]"+Grid_Bank_M_Code[k]);

                                   txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);
                                   txtParticular=Grid_particular[k];
                                   System.out.println("Grid_sl_amt[k] "+Grid_sl_amt[k]);
                                   System.out.println("Grid_particular[k] "+Grid_particular[k]);
                                   
                                   ps.setInt(1,cmbAcc_UnitCode);
                                   ps.setInt(2,cmbOffice_code);
                                   ps.setInt(3,txtCash_year);
                                   ps.setInt(4,txtCash_Month_hid);
                                   ps.setInt(5,txtReceipt_No);
                                   ps.setInt(6,SL_NO);
                                   ps.setInt(7,txtAcc_HeadCode);
                                   ps.setString(8,rad_sub_CR_DR);
                                   ps.setInt(9,cmbSL_type);
                                   ps.setInt(10,cmbSL_Code);
                                   ps.setString(11,txtsub_Recei_from);
                                   ps.setString(12,txtCheque_DD);
                                   ps.setString(13,txtCheque_DD_NO);
                                   ps.setDate(14,txtCheque_DD_date);
                                   ps.setString(15,txtBank_Name);
                                   ps.setString(16,txtDraw_BR);
                                   ps.setString(17,txtBank_M_Code);
                                   ps.setDouble(18,txtsub_Amount);
                                   ps.setString(19,txtParticular);
                                   ps.setString(20,update_user);
                                   ps.setTimestamp(21,ts);
                                   
                                   // CB Ref Number and Date  
                                  // ps.setInt(22,Integer.parseInt(txtRef_no));
                                   ps.setInt(22,0);
                                   ps.setDate(23,txtRef_date);
                                   
                                   SL_NO++;
                                   
                                   ps.executeUpdate();  
                                   txtAcc_HeadCode=0;
                                   rad_sub_CR_DR="";
                                   cmbSL_type=0;
                                   cmbSL_Code=0;
                                   txtsub_Recei_from="";
                                   txtCheque_DD="";
                                   txtCheque_DD_NO="";
                                   txtCheque_DD_date=null;
                                   txtBank_Name="";
                                   txtDraw_BR="";
                                   txtBank_M_Code="";
                                   txtsub_Amount=0;
                                   txtParticular="";
                               }
                               ps.close();
                          
                         String txtReferNO_edit="",txtRemak_edit="", txtRefdate="";         // for cross reference
                         Date txtReferDate_edit=null; 
                         String radAuth_MC="";
                         int txtAuth_By=0;
                         
                         System.out.println("txtReferDate_edit "+txtReferDate_edit);
                         cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
                         cs1.setInt(1,cmbAcc_UnitCode);
                         cs1.setInt(2,txtCash_year);
                         cs1.setInt(3,txtCash_Month_hid);
                         cs1.setInt(4,txtReceipt_No);
                         cs1.setInt(5,cmbOffice_code);
                         cs1.setDate(6,txtCrea_date);
                         cs1.setString(7,doc_type);
                         cs1.setString(8,txtReferNO_edit);
                         cs1.setDate(9,txtReferDate_edit);
                         cs1.setString(10,txtRemak_edit);
                         cs1.setInt(11,txtAuth_By);                                                      
                         cs1.setString(12,"insert");
                         cs1.registerOutParameter(13,java.sql.Types.NUMERIC);
                         cs1.setNull(13,java.sql.Types.NUMERIC);
                         cs1.setString(14,update_user);
                         cs1.setTimestamp(15,ts);       
                         cs1.setString(16,radAuth_MC);
                         cs1.execute();                                            // insertion into cross reference table
                         errcode=cs1.getInt(13);
                         System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                         System.out.println("cmbOffice_code "+cmbOffice_code);
                         System.out.println("txtCrea_date "+txtCrea_date);
                         System.out.println("txtCash_year "+txtCash_year);
                         System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                         System.out.println("SQLCODE:::"+errcode);
                         if(errcode!=0)
                         {   
                           con.rollback();
                           sendMessage(response,"The Receipt Modification Failed ","ok");
                           xml=xml+"<flag>failure</flag>";                          
                         }
                         
                      
                       /**
                        * Auto Generation of Bank Remittance for ECS Transaction 
                        */
                       
                       System.out.println("Remittance_Type----><--"+Remittance_Type);
                       int  Verified_Authority=0;
                       
                       
                       if (Remittance_Type.equalsIgnoreCase("E"))      
                       {
                       
                         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                         Verified_Authority= empProfile.getEmployeeId();                 
                         System.out.println("Verified_Authority::"+Verified_Authority);
                                       
                        
                         System.out.println("inside E ");
                         cs1=
                         //=con.prepareCall("{call FAS_ECS_REMITTANCE_PROC(?,?,?,?,?,?,?,?,?,?)}") ;                          
//                         cs1.setInt(1,cmbAcc_UnitCode);
//                         cs1.setInt(2,cmbOffice_code);
//                         cs1.setInt(3,txtCash_year);
//                         cs1.setInt(4,txtCash_Month_hid);
//                         cs1.setDate(5,txtCrea_date);
//                         cs1.setString(6,"B");
//                         cs1.setDouble(7,txtAmount);
//                         cs1.setInt(8,Verified_Authority);
//                         cs1.setString(9,update_user);                         
//                         cs1.registerOutParameter(10,java.sql.Types.NUMERIC); 
//                         cs1.execute();
//                         int err_code=cs1.getInt(10);     
                        		 con.prepareCall("call FAS_ECS_REMITTANCE_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?)");
                         cs1.setInt(1, cmbAcc_UnitCode);
                         cs1.setInt(2, cmbOffice_code);
                         cs1.setInt(3, txtCash_year);
                         cs1.setInt(4, txtCash_Month_hid);
                         cs1.setDate(5, txtCrea_date);
                         cs1.setString(6, "B");
                         cs1.setDouble(7, txtAmount);
                         cs1.setInt(8, Verified_Authority);
                         cs1.setString(9, update_user);
                         cs1.registerOutParameter(10, java.sql.Types.VARCHAR);
                         cs1.setNull(10, java.sql.Types.VARCHAR);
                         cs1.execute();
                         //int err_code = cs1.getInt(10);
                         String err_code = cs1.getString(10);
                         if (err_code.equals("0")) 
                         {
                           con.commit();
                           sendMessage(response,"The Receipt Number '"+txtReceipt_No+"' has been Modified Successfully ","ok");
                         }
                         else {
                           sendMessage(response,"The Receipt Modification Failed ","ok"); 
                         }
                       }   
                       else 
                       {
                           con.commit(); 
                           sendMessage(response,"The Receipt Number '"+txtReceipt_No+"' has been Modified Successfully ","ok");
                       }
                       
                     
                   } /** Else part End */
                   
                 } /** Try End */
                 catch(Exception e) 
                 {
                     try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
                     sendMessage(response,"The Receipt Modification Failed ","ok");
                     System.out.println("Exception occur due to "+e);
                 }
                 finally
                 {
                     System.out.println("done");
                     try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
                 }
                 
        }
    }
    
    
    
    
    
    
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
    	
    	/** Get Session */
        try
        {
            HttpSession session=request.getSession(false);
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        
        
         Connection con=null;
         PreparedStatement ps2=null;
         ResultSet rs2=null;         
         PreparedStatement ps3=null;
         ResultSet rs3=null;
         
         PreparedStatement ps4=null;
         ResultSet rs4=null;
        
        
        /** Database Connection */
        try {
                               ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                               String ConnectionString="";

                               String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                               String strdsn=rs1.getString("Config.DSN");
                               String strhostname=rs1.getString("Config.HOST_NAME");
                               String strportno=rs1.getString("Config.PORT_NUMBER");
                               String strsid=rs1.getString("Config.SID");
                               String strdbusername=rs1.getString("Config.USER_NAME");
                               String strdbpassword=rs1.getString("Config.PASSWORD");
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
              {
                  System.out.println("Exception in opening connection :"+e);
              }
        
              
        /** Set Content Type */      
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        String strCommand="";
        
        
        /** Get Command Parameter */
        try 
        {
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        /**
         * Variables Declaration 
         */
        int cmbAcc_UnitCode=0;        
        int cmbOffice_code=0;
        int txtCB_Year=0;
        int txtCB_Month=0;
        PreparedStatement ps =null;
        ResultSet rs=null;
        String txtMode_of_creat="",doc_type="",payment_type1="";
        /** Get Accounting Unit ID */    
        try{
        	cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        }catch(Exception e){System.out.println("Exception to get Accounting Unit ID ");}
        
        
        /** Get Accouting for office id */
        try{
        	cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
        }catch(Exception e){System.out.println("Exception to get Office ID ");}
        
        
        
        /** Get Cashbook Year */
      /*  try{
        	txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        }catch(Exception e){System.out.println("Exception to Read Cashbook Year ");}
      */      
  
        
        /** Get Cashbook Month */
      /*  try{
        	txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
        }catch(Exception e){System.out.println("Exception to Read Cashbook Month ");}
       */
        
       try{txtMode_of_creat=request.getParameter("txtMode_of_creat");}
       catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
       
       if(txtMode_of_creat.equals("I"))
       {
       	txtMode_of_creat="I";
        doc_type="IMPR";
       }
       
       else if(txtMode_of_creat.equals("T"))
       {
       	txtMode_of_creat="T";
        doc_type="TMPR";
       }
      
       
        if(strCommand.equalsIgnoreCase("LoadPayEmpMaxAmt")) 
        {
        
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             String xml="";
             int count=0;
             
            int txtReceipt_No=0;
            int cmbPayVocNo=0;
            
            int txtCash_year_Pay=0;
            int txtCash_Month_Pay=0;
            
            int cmbSL_type=0;
            int cmbSL_Code=0;
            
            Date txtCrea_date=null;
            Date txtPay_date=null;
            
            Calendar c;
             
            /** Get Receipt Number */
            try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtReceipt_No "+txtReceipt_No);            
            
            /** Get Receipt Date */
            String[] sd=request.getParameter("txtCrea_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            System.out.println("txtCrea_date "+txtCrea_date);            
            
            
            /** Get Payment Voucher Number */                  
            try{cmbPayVocNo=Integer.parseInt(request.getParameter("cmbPayVocNo"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbPayVocNo "+cmbPayVocNo);             
                
            /** Get Payment Date */
            String[] sd1=request.getParameter("txtPaymentVoc_Date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
            java.util.Date d1=c.getTime();
            txtPay_date=new Date(d1.getTime());
            System.out.println("txtPay_date "+txtPay_date);
            
            
             /** Calculate Payment Cashbook Month and Year */
             try{txtCash_year_Pay=Integer.parseInt(sd1[2]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_year_Pay "+txtCash_year_Pay);
             
             try{txtCash_Month_Pay=Integer.parseInt(sd1[1]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_Month_Pay "+txtCash_Month_Pay);
             
                        
            /** Get SL Type Code */             
            try{cmbSL_type=Integer.parseInt(request.getParameter("cmbSL_type"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbSL_type "+cmbSL_type);            
            
            /** Get SL Code */ 
            try{cmbSL_Code=Integer.parseInt(request.getParameter("cmbSL_Code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbSL_Code "+cmbSL_Code);                         
             
            xml="<response><command>LoadPayEmpMaxAmt</command>";
             
            String sql=" select * from \n" + 
            "              (\n" + 
            "               select   \n" + 
            "                voucher_no,  \n" + 
            "                payment_date,  \n" + 
            "                sl_type_code,  \n" + 
            "                sl_code,  \n" + 
            "                (pay_amt - jour_amt - rec_amt)  as bal \n" + 
            "               from   \n" + 
            "               (  \n" + 
            "                  \n" + 
            "                select   \n" + 
            "                 voucher_no,   \n" + 
            "                 payment_date,   \n" + 
            "                 sl_type_code,   \n" + 
            "                 sl_code,   \n" + 
            "                 pay_amt as pay_amt,   \n" + 
            "                 sum(jour_amt) as jour_amt ,   \n" + 
            "                 sum(rec_amt) as rec_amt   \n" + 
            "               from   \n" + 
            "               (  \n" + 
            "                     select   \n" + 
            "                      voucher_no,   \n" + 
            "                      payment_date,   \n" + 
            "                      sl_type_code,  \n" + 
            "                      sl_code,   \n" + 
            "                      pay_amt,   \n" + 
            "                      jour_amt,  \n" + 
            "                      0 as rec_amt  \n" + 
            "                     from   \n" + 
            "                     (                                                                                                          \n" + 
            "                          select * from    \n" + 
            "                          (   \n" + 
            "                           select                                                                                    \n" + 
            "                               m.voucher_no,    \n" + 
            "                               to_char(m.PAYMENT_DATE,'DD/MM/YY')as payment_date,                                                                   \n" + 
            "                               t.SUB_LEDGER_TYPE_CODE as sl_type_code,                                           \n" + 
            "                               t.SUB_LEDGER_CODE as sl_code,                                                     \n" + 
            "                               t.AMOUNT   as pay_amt                                                                 \n" + 
            "                            from                                                                             \n" + 
            "                               fas_payment_master       m,                           \n" + 
            "                               fas_payment_transaction t      \n" + 
            "                           where                                                                                             \n" + 
            "                               m.accounting_unit_id = ?                                              \n" + 
       //     "                           and m.accounting_for_office_id = ? \n" + 
            "                           and m.cashbook_month = ?                                  \n" + 
            "                           and m.cashbook_year= ?                                    \n" + 
            "                           and m.VOUCHER_NO =  ?                                \n" + 
            "                           and m.mode_of_creation=?                           \n" +
            //"                           and payment_type = '"+payment_type1+"'                            \n" +
            "                           and m.payment_status='L'                                  \n" + 
            "                           and m.accounting_unit_id= t.accounting_unit_id       \n" + 
            "                           and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID        \n" + 
            "                           and m.cashbook_month=t.cashbook_month                \n" + 
            "                           and m.cashbook_year=t.cashbook_year                  \n" + 
            "                           and m.voucher_no=t.voucher_no           \n" + 
            "                           )p    \n" + 
            "                              \n" + 
            "                           left outer join   \n" + 
            "                              \n" + 
            "                           (    \n" + 
            "                               select    \n" + 
            "                                   t.SUB_LEDGER_TYPE_CODE as j_sl_type_code,   \n" + 
            "                                   t.SUB_LEDGER_CODE as j_sl_code,    \n" + 
            "                                   t.cb_ref_no,                   \n" + 
            "                                   to_char(t.cb_ref_date,'DD/MM/YY') as cb_ref_date,    \n" + 
            "                                   amount as jour_amt   \n" + 
            "                               from    \n" + 
            "                                  fas_journal_master m,    \n" + 
            "                                  fas_journal_transaction  t                                                                                  \n" + 
            "                               where   \n" + 
            "                                  m.accounting_unit_id=t.accounting_unit_id and    \n" + 
            "                                  m.accounting_for_office_id = t.accounting_for_office_id and    \n" + 
            "                                  m.cashbook_month=t.cashbook_month and    \n" + 
            "                                  m.cashbook_year=t.cashbook_year and    \n" + 
            "                                  m.voucher_no = t.voucher_no and    \n" + 
            "                                  m.MODE_OF_CREATION=? and    \n" + 
            "                                  t.cr_dr_indicator='CR'  and    \n" + 
            "                                  m.accounting_unit_id=?    \n" + 
            "                              \n" + 
            "                           )j                                                             \n" + 
            "                           on    \n" + 
            "                                p.voucher_no = j.CB_REF_NO and    \n" + 
            "                                p.payment_date = j.CB_REF_DATE and    \n" + 
            "                                p.sl_type_code = j_sl_type_code and    \n" + 
            "                                p.sl_code = j_sl_code    \n" + 
            "                              \n" + 
            "                         )   \n" + 
            "                           \n" + 
            "                           \n" + 
            "                           \n" + 
            "                         union all   \n" + 
            "                           \n" + 
            "                           \n" + 
            "                select   \n" + 
            "                  voucher_no,   \n" + 
            "                  payment_date,  \n" + 
            "                  sl_type_code,   \n" + 
            "                  sl_code,   \n" + 
            "                  pay_amt,   \n" + 
            "                  0 as jour_amt,   \n" + 
            "                  rec_amt  \n" + 
            "                  from   \n" + 
            "                  (  \n" + 
            "                          select * from    \n" + 
            "                          (   \n" + 
            "                           select                                                                                    \n" + 
            "                               m.voucher_no,    \n" + 
            "                               to_char(m.PAYMENT_DATE,'DD/MM/YY')as payment_date,                                                                   \n" + 
            "                               t.SUB_LEDGER_TYPE_CODE as sl_type_code,                                           \n" + 
            "                               t.SUB_LEDGER_CODE as sl_code,                                                     \n" + 
            "                               t.AMOUNT   as pay_amt                                                                 \n" + 
            "                            from                                                                             \n" + 
            "                              fas_payment_master        m,                           \n" + 
            "                               fas_payment_transaction t      \n" + 
            "                           where                                                                                             \n" + 
            "                               m.accounting_unit_id = ?                                              \n" + 
           // "                           and m.accounting_for_office_id = ? \n" + 
            "                           and m.cashbook_month = ?                                  \n" + 
            "                           and m.cashbook_year= ? \n" + 
            "                            and m.VOUCHER_NO =  ?                                \n" + 
            "                            and m.mode_of_creation=?                           \n" +
            //"                           and payment_type = '"+payment_type1+"'                            \n" +
            "                           and m.payment_status='L'                                  \n" + 
            "                            and m.accounting_unit_id= t.accounting_unit_id       \n" + 
            "                            and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID        \n" + 
            "                            and m.cashbook_month=t.cashbook_month                \n" + 
            "                            and m.cashbook_year=t.cashbook_year                  \n" + 
            "                            and m.voucher_no=t.voucher_no           \n" + 
            "                           )p    \n" + 
            "                              \n" + 
            "                           left outer join   \n" + 
            "                              \n" + 
            "                           (    \n" + 
            "                               select    \n" + 
            "                                   t.SUB_LEDGER_TYPE_CODE as r_sl_type_code,   \n" + 
            "                                   t.SUB_LEDGER_CODE as r_sl_code,                                                                                                                          \n" + 
            "                                   m.RECEIVABLE_VOUCHER_NO,                                                                                                                        \n" + 
            "                                   to_char(m.RECEIVABLE_VOUCHER_DATE,'DD/MM/YY') as RECEIVABLE_VOUCHER_DATE,                                                                                                                                                                                                                                                \n" + 
            "                                   t.AMOUNT as rec_amt   \n" + 
            "                               from    \n" + 
            "                                  fas_receipt_master m,    \n" + 
            "                                  fas_receipt_transaction  t                                                                                  \n" + 
            "                               where   \n" + 
            "                                  m.accounting_unit_id=t.accounting_unit_id and    \n" + 
            "                                  m.accounting_for_office_id = t.accounting_for_office_id and    \n" + 
            "                                  m.cashbook_month=t.cashbook_month and    \n" + 
            "                                  m.cashbook_year=t.cashbook_year and    \n" + 
            "                                  m.receipt_no = t.receipt_no and    \n" + 
            "                                  m.MODE_OF_CREATION=? and                                                                                                                         \n" + 
            "                                  m.accounting_unit_id=? and   \n" + 
            "                                  m.RECEIPT_STATUS='L'  and \n" + 
            "                                  m.receipt_no != ? and \n" + 
            "                                  m.receipt_date != ? \n" + 
            "                              \n" + 
            "                           )r                                                             \n" + 
            "                           on    \n" + 
            "                                p.voucher_no = r.RECEIVABLE_VOUCHER_NO and    \n" + 
            "                                p.payment_date = r.RECEIVABLE_VOUCHER_DATE and    \n" + 
            "                                p.sl_type_code = r.r_sl_type_code and    \n" + 
            "                                p.sl_code = r.r_sl_code    \n" + 
            "                               \n" + 
            "                      )       \n" + 
            "                           \n" + 
            "                ) group by voucher_no, payment_date, sl_type_code, sl_code , pay_amt           \n" + 
            "                order by payment_date, voucher_no, sl_type_code, sl_code                   \n" + 
            "               )               \n" + 
            "              )   \n" + 
            "                 \n" + 
            "          where bal > 0  and sl_type_code= ? and sl_code = ?\n" + 
            "         \n" + 
            "        ";
            
            try
            {
                    ps4=con.prepareStatement(sql);
                    
                    ps4.setInt(1,cmbAcc_UnitCode);  // ok 
                    System.out.println("1--> "+cmbAcc_UnitCode);
//                    ps4.setInt(2,cmbOffice_code);  // ok 
//                    System.out.println("2--> "+cmbOffice_code);
                    ps4.setInt(2,txtCash_Month_Pay);
                    ps4.setInt(3,txtCash_year_Pay);  
                    ps4.setInt(4,cmbPayVocNo);
                    ps4.setString(5,txtMode_of_creat);
                    ps4.setString(6,txtMode_of_creat);
                    ps4.setInt(7,cmbAcc_UnitCode);  // ok 
                    
                    
                    ps4.setInt(8,cmbAcc_UnitCode);  // ok 
                    System.out.println("7--> "+cmbAcc_UnitCode);
//                    ps4.setInt(10,cmbOffice_code);   // ok 
//                    System.out.println("8--> "+cmbOffice_code);
                    ps4.setInt(9,txtCash_Month_Pay);
                    System.out.println("9--> "+txtCash_Month_Pay);
                    ps4.setInt(10,txtCash_year_Pay);                       
                    System.out.println("10--> "+txtCash_year_Pay);
                    ps4.setInt(11,cmbPayVocNo);
                    System.out.println("11--> "+cmbPayVocNo);
                    
                    ps4.setString(12,txtMode_of_creat);
                    ps4.setString(13,txtMode_of_creat);
                    ps4.setInt(14,cmbAcc_UnitCode);  // ok 
                    System.out.println("12--> "+cmbAcc_UnitCode);
                    ps4.setInt(15,txtReceipt_No); // ok 
                    System.out.println("13--> "+txtReceipt_No);
                    ps4.setDate(16,txtCrea_date); // ok 
                    System.out.println("14--> "+txtCrea_date);
                    
                    
                    ps4.setInt(19,7);  // ok 
                    System.out.println("15--> "+7);
                    ps4.setInt(20,cmbSL_Code); // ok 
                    System.out.println("16--> "+cmbSL_Code);
                    
                    rs4=ps4.executeQuery();
                    int Max_Amount=0;
                    while(rs4.next()) 
                    {
                       Max_Amount = rs4.getInt("BAL");                          
                    }
                    
                    xml=xml+"<Max_Amount>"+Max_Amount+"</Max_Amount>";
                    xml=xml+"<flag>success</flag>";
                    xml=xml+"</response>";
                    System.out.println(xml);		
                    out.println(xml);                
                      
            }
            catch(Exception e) {
                xml=xml+"<flag>failure</flag>";
                xml=xml+"</response>";
                System.out.println(e);
            }
             
        }
        else if(strCommand.equalsIgnoreCase("load_Receipt_No")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             Calendar c;
             String xml="";
             Date txtCrea_date=null;
             String txtAmtType="";
             String receipt_type="";
             
             xml="<response><command>load_Receipt_No</command>";
              
             String[] sd=request.getParameter("txtCrea_date").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
             java.util.Date d=c.getTime();
             txtCrea_date=new Date(d.getTime());
             System.out.println("txtCrea_date "+txtCrea_date);
             
             /* Mode of Receipt */            
             try{
             	txtAmtType=request.getParameter("txtAmtType");
             	if (txtAmtType.equalsIgnoreCase("CR"))
             		receipt_type="C";
             	else if (txtAmtType.equalsIgnoreCase("BR"))
             		receipt_type="B";             	
             }
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtAmtType "+txtAmtType);
 	                               
                    try {                    	
                    	    ps=con.prepareStatement("" +
                            "SELECT i.RECEIPT_NO                                \n" + 
                    	    "FROM FAS_RECEIPT_MASTER i,                         \n" + 
                    	    "  FAS_CROSS_REFERENCE c                            \n" + 
                    	    "WHERE i.ACCOUNTING_UNIT_ID    =?                   \n" + 
                    	  //  "AND i.ACCOUNTING_FOR_OFFICE_ID=?                   \n" + 
                    	    "AND i.RECEIPT_DATE            =?                   \n" + 
                    	    "AND i.RECEIPT_STATUS!         ='C'                 \n" + 
                    	    "AND i.RECEIPT_TYPE            =?                   \n" + 
                    	    "AND i.CREATED_BY_MODULE       =?                   \n" + 
                    	    "AND i.ACCOUNTING_UNIT_ID      =c.ACCOUNTING_UNIT_ID                \n" + 
                    	    "AND i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID          \n" + 
                    	    "AND i.CASHBOOK_YEAR           =c.CASHBOOK_YEAR                     \n" + 
                    	    "AND i.CASHBOOK_MONTH          =c.CASHBOOK_MONTH                    \n" + 
                    	    "AND i.RECEIPT_NO              =c.VOUCHER_NO                        \n" + 
                    	    "AND c.CHANGE_NO               =0                                   \n" + 
                    	    "AND c.AUTHORIZED_TO           ='M'                                 \n" + 
                    	    "AND c.DOC_TYPE                =?              ");
                       
                    	    System.out.println("cmbAcc_UnitCode-->"+cmbAcc_UnitCode);
                    	    System.out.println("cmbOffice_code-->"+cmbOffice_code);
                    	    System.out.println("txtCrea_date-->"+txtCrea_date);
                    	    System.out.println("receipt_type-->"+receipt_type);
                    	    System.out.println("txtAmtType-->"+txtAmtType);
                    	    
                    	    ps.setInt(1,cmbAcc_UnitCode);
                          //  ps.setInt(2,cmbOffice_code);
                            ps.setDate(2,txtCrea_date);                            
                            ps.setString(3,receipt_type);
                            ps.setString(4,txtAmtType); // Created By Moudle   
                          
                            ps.setString(5,doc_type);System.out.println("doc_type"+doc_type);
                            rs=ps.executeQuery();
                            
                            int count=0;
                            while(rs.next())
                            {
                            
                            xml=xml+"<Rec_No>"+rs.getInt("RECEIPT_NO")+
                            "</Rec_No>";
                                count++;
                            }
                            if(count==0)
                                xml=xml+"<flag>failure</flag>";
                            else 
                                xml=xml+"<flag>success</flag>";
                        System.out.println("count  "+count);
                        ps.close();
                        rs.close();
                        }
                        catch(Exception e)
                        {
                        System.out.println("catch..HERE.in load head code."+e);
                            xml=xml+"<flag>failure</flag>";
                        }
                        xml=xml+"</response>";
                        System.out.println(xml);
                        out.println(xml);                        
         }         
         
         
         
         
        else if(strCommand.equalsIgnoreCase("load_Receipt_Details")) 
        {
        	
        	 System.out.println("indside load_Receipt_Details --->");
          
	         String CONTENT_TYPE = "text/xml; charset=windows-1252";
	         response.setContentType(CONTENT_TYPE);
                 
                 /** Variables Declaration */
	         Calendar c;
	         String xml="";
	         String txtAmtType="";
	         String receipt_type="";
                 int txtReceipt_No=0;
                 int cmbPayVocNo=0;
                 
                 int txtCash_year_Pay=0;
                 int txtCash_Month_Pay=0;
                 
    		 Date txtCrea_date=null;
                 Date txtPay_date=null;
    		     
	         xml="<response><command>load_Receipt_Details</command>";
	         
                 
                 
                 
                /** Receipt Number */
	        try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
	        catch(NumberFormatException e){System.out.println("exception"+e );}
	        System.out.println("txtReceipt_No "+txtReceipt_No);
	        
                /** Receipt Date */
	        String[] sd=request.getParameter("txtCrea_date").split("/");
	        c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	        java.util.Date d=c.getTime();
	        txtCrea_date=new Date(d.getTime());
	        System.out.println("txtCrea_date "+txtCrea_date);
            
                
	       /** Mode of Receipt */            
               try{
            	   txtAmtType=request.getParameter("txtAmtType");
            	   if (txtAmtType.equalsIgnoreCase("CR"))
            		 receipt_type="C";
            	   else if (txtAmtType.equalsIgnoreCase("BR"))
            		 receipt_type="B";
            	
                }
                catch(Exception e){System.out.println("exception"+e );}
                System.out.println("txtAmtType "+txtAmtType);
                  
                  
                    
                try {
                
                     /**
                      *  PART I
                      */
                     //--------------------------------------------------------------------------------------//
                
            	       /**
                        * Load General Tab from fas_receipt_master table 
                        */
                       
                       ps=con.prepareStatement("" +
                       "SELECT                                          \n" + 
                       "  REMITTANCE_IN_CURR_MONTH,                     \n" + 
                       "  MODE_OF_CREATION,                             \n" + 
                       "  ACCOUNT_HEAD_CODE,                            \n" + 
                       "  CASHBOOK_YEAR,                                \n" + 
                       "  CASHBOOK_MONTH,                               \n" + 
                       "  BANK_ID ,                                     \n" + 
                       "  BRANCH_ID ,                                   \n" + 
                       "  ACCOUNT_NO ,                                  \n" + 
                       "  REF_NO,                                       \n" + 
                       "  TO_CHAR(REF_DATE,'DD/MM/YYYY') AS ref_date,                               \n" + 
                       "  trim(TO_CHAR(TOTAL_AMOUNT,'99999999999999.99')) AS TOTAL_AMOUNT,          \n" + 
                       "  TOTAL_TRN_RECORDS,                                                        \n" + 
                       "  RECEIVED_FROM,                                \n" + 
                       "  REMARKS,                                      \n" + 
                       "  SUB_LEDGER_TYPE_CODE,                         \n" + 
                       "  SUB_LEDGER_CODE,                              \n" + 
                       "  RECEIVABLE_VOUCHER_NO,                        \n" + 
                       "  TO_CHAR(RECEIVABLE_VOUCHER_DATE,'DD/MM/YYYY') AS RECEIVABLE_VOUCHER_DATE   \n" + 
                       "FROM FAS_RECEIPT_MASTER                         \n" + 
                       "WHERE ACCOUNTING_UNIT_ID    =?\n" + 
                       //"AND ACCOUNTING_FOR_OFFICE_ID=?\n" + 
                       "AND RECEIPT_DATE            =?\n" + 
                       "AND RECEIPT_TYPE            =?\n" + 
                       "AND CREATED_BY_MODULE       =?\n" + 
                       "AND RECEIPT_NO              =?");
                       
                       ps.setInt(1,cmbAcc_UnitCode);
                     //  ps.setInt(2,cmbOffice_code);
                       ps.setDate(2,txtCrea_date);
                       ps.setString(3,receipt_type);
                       ps.setString(4,txtAmtType);
                       ps.setInt(5,txtReceipt_No);
                       
                       rs=ps.executeQuery();                       
                       
                       if(rs.next())
		       {
		                     
		                       
		                       /* Get Account Head Code from Master */
                    	       System.out.println("Acc Head :::  --------------->"+rs.getString("ACCOUNT_HEAD_CODE"));
		                       xml=xml+"<MasHeadCode>"+rs.getString("ACCOUNT_HEAD_CODE").trim()+"</MasHeadCode>";
		
		                       /* Get Bank Name , Branch Name  */
		                       ps3=con.prepareStatement("" +
                                       "SELECT bk.BANK_NAME         \n" + 
		                       "  ||'-'                     \n" + 
		                       "  ||br.BRANCH_NAME          \n" + 
		                       "  || '-'                    \n" + 
		                       "  ||coalesce (br.CITY_TOWN_NAME,'') AS bankNAME       \n" + 
		                       "FROM FAS_MST_BANKS bk,                  \n" + 
		                       "  FAS_MST_BANK_BRANCHES br              \n" + 
		                       "WHERE br.BANK_ID=?                      \n" + 
		                       "AND br.BRANCH_ID=?                      \n" + 
		                       "AND br.BANK_ID  =bk.BANK_ID");
                                       
		                       ps3.setInt(1,rs.getInt("BANK_ID"));
		                       ps3.setInt(2,rs.getInt("BRANCH_ID"));
		                       rs3=ps3.executeQuery();
		                       if(rs3.next())
		                       {   
			                   xml=xml+"<bk_name>"+rs3.getString("bankNAME")+"</bk_name>";
		                       }
		                       else 
		                       {
		                    	   xml=xml+"<bk_name>--</bk_name>";
		                       }
		                       
			               rs3.close();
			               ps3.close();
			                       
		                       /* Get Reference Number and Date = Payment voucher no and Date */
		                       xml=xml+"<Ref_No>"+rs.getString("RECEIVABLE_VOUCHER_NO")+"</Ref_No>" +
                                               "<Ref_Date>"+rs.getString("RECEIVABLE_VOUCHER_DATE")+"</Ref_Date>" +
                                               "<accNo>"+rs.getString("ACCOUNT_NO")+"</accNo>";
                                               
		                       xml=xml+"<bk_id>"+rs.getInt("BANK_ID")+
		                       "</bk_id><br_id>"+rs.getInt("BRANCH_ID")+
		                       "</br_id><Total_amt>"+rs.getString("TOTAL_AMOUNT")+
		                       "</Total_amt><No_TRN_Rec>"+rs.getInt("TOTAL_TRN_RECORDS")+
		                       "</No_TRN_Rec><Rec_From>"+rs.getString("RECEIVED_FROM")+
		                       "</Rec_From><Remak>"+rs.getString("REMARKS")+
		                       "</Remak><Mas_SL_type>"+rs.getInt("SUB_LEDGER_TYPE_CODE")+
		                       "</Mas_SL_type><Mas_SL_code>"+rs.getInt("SUB_LEDGER_CODE")+ "</Mas_SL_code>";
		                       xml=xml+"<MO_creation>"+rs.getString("MODE_OF_CREATION")+"</MO_creation>";
		                       xml=xml+"<rem_in_curr_month>"+rs.getString("REMITTANCE_IN_CURR_MONTH")+"</rem_in_curr_month>";
		       }
                       
                       
                       System.out.println("xml--1-->"+xml);
                       
                       
                       /**
                        *  PART II
                        */                       
                       //--------------------------------------------------------------------------------------//
                       
                       
                       // Start of fetching sub-Ledger , here u r passing parameters to the function getResult_General which is inside the class SL_TYPE_CODE_NAME_GENERAL
                       
                         if(rs.getInt("SUB_LEDGER_TYPE_CODE")!=0)
                         {
                           
                             SL_TYPE_CODE_NAME_GENERAL obj_gen = new SL_TYPE_CODE_NAME_GENERAL();
                           
                             ResultSet rs_get=obj_gen.getResult_General(cmbAcc_UnitCode,cmbOffice_code,rs.getInt("SUB_LEDGER_TYPE_CODE"),rs.getInt("SUB_LEDGER_CODE"),0);
                           
                             if(rs_get!=null)
                             {
                                 while(rs_get.next())
                                 {
                           
                                     xml=xml+"<cid>"+rs_get.getInt("cid")+"</cid>";
                                     xml=xml+"<cname>"+rs_get.getString("cname")+"</cname>";
                                 }
                           
                                 rs_get.close();
                             }
                             else
                             {
                                 System.out.println("null result set");                             
                             }    
                         } 
                            
                            
                       System.out.println("xml--2-->"+xml);    
                       
                       
                       
                       /**
                        * PART III
                        */
                       //--------------------------------------------------------------------------------------//    
                            
                       int SL_TYPE_CODE_TMP=0;
                       int SL_CODE_TMP = 0;
                       int CB_MONTH=0;
                       int CB_YEAR=0;
                       
                         CB_YEAR=rs.getInt("CASHBOOK_YEAR");
                         CB_MONTH =rs.getInt("CASHBOOK_MONTH");
                       
                       /*  Load Detail Tab from fas_receipt_transaction table */
                       ps2=con.prepareStatement("" +
                       "SELECT\n" + 
                       "  ACCOUNT_HEAD_CODE ,\n" + 
                       "  CR_DR_INDICATOR ,\n" + 
                       "  SUB_LEDGER_TYPE_CODE ,\n" + 
                       "  SUB_LEDGER_CODE ,\n" + 
                       "  CHEQUE_OR_DD ,\n" + 
                       "  CHEQUE_DD_NO ,\n" + 
                       "  TO_CHAR(CHEQUE_DD_DATE,'DD/MM/YYYY') AS cheq_dd_date ,\n" + 
                       "  BANK_NAME ,\n" + 
                       "  DRAWEE_BRANCH ,\n" + 
                       "  BANK_MICR_CODE,\n" + 
                       "  RECEIVED_FROM ,\n" + 
                       "  trim(TO_CHAR(AMOUNT,'99999999999999.99')) AS AMOUNT,\n" + 
                       "  PARTICULARS\n" + 
                       "FROM FAS_RECEIPT_TRANSACTION\n" + 
                       "WHERE ACCOUNTING_UNIT_ID    =?\n" + 
                      // "AND ACCOUNTING_FOR_OFFICE_ID=?\n" + 
                       "AND CASHBOOK_YEAR           =?\n" + 
                       "AND CASHBOOK_MONTH          =?\n" + 
                       "AND RECEIPT_NO              =?");
                       
                      
                       
                       ps2.setInt(1,cmbAcc_UnitCode);
                  //     ps2.setInt(2,cmbOffice_code);
                       ps2.setInt(2,CB_YEAR);
                       ps2.setInt(3,CB_MONTH);
                       ps2.setInt(4,txtReceipt_No);
                       rs2=ps2.executeQuery();
                       while(rs2.next()) 
                       {
                                               xml=xml+"<AHcode>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"</AHcode>";
                                                  
                                               ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                                               ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
                                               rs3=ps3.executeQuery();
                                               if(rs3.next())
                                               {
                                                 xml=xml+"<AHdesc>"+rs3.getString("ACCOUNT_HEAD_DESC")+"</AHdesc>";
                                               }
                                               ps3.close();
                                               rs3.close();
			                       
			                       xml=xml+"<CR_DR_ind>"+rs2.getString("CR_DR_INDICATOR")+"</CR_DR_ind>";
                                               
                                               
                                               SL_TYPE_CODE_TMP=rs2.getInt("SUB_LEDGER_TYPE_CODE");                            
			                       xml=xml+"<SL_Type>"+SL_TYPE_CODE_TMP+"</SL_Type>";
			                       
                                               
                                               
			                       if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
			                       {
			                           ps3=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
				                       ps3.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
				                       rs3=ps3.executeQuery();
				                       if(rs3.next())
				                       {	  
				                         xml=xml+"<SL_Desc>"+rs3.getString("SUB_LEDGER_TYPE_DESC")+"</SL_Desc>";
				                       } 
			                       }
			                       else
			                       {
			                          xml=xml+"<SL_Desc>"+null+"</SL_Desc>";  			                    
			                       }
			                       
			                       rs3.close();                            
			                       ps3.close();
			                       
                                               SL_CODE_TMP =  rs2.getInt("SUB_LEDGER_CODE");                                               
			                       xml=xml+"<SL_Code>"+SL_CODE_TMP+"</SL_Code>";
			                           
			                               if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
			                               {
			                                   SL_TYPE_CODE_NAME_DETAILS obj_det=new SL_TYPE_CODE_NAME_DETAILS();
			                                   ResultSet rs_det=obj_det.getResult_Details(cmbAcc_UnitCode,cmbOffice_code,rs2.getInt("SUB_LEDGER_TYPE_CODE"),rs2.getString("SUB_LEDGER_CODE"),0);
			                                   if(rs_det!=null)
			                                   {
			                                       if(rs_det.next())
			                                       {
			                                           System.out.println(rs_det.getString("cname"));
			                                           xml=xml+"<desc_type>"+rs_det.getString("cname")+"</desc_type>";
			                                       }
			                                       rs_det.close();
			                                   }
			                                   else
			                                       System.out.println("null result set");
			                               }
			                               else
			                               {
			                                   xml=xml+"<desc_type>"+null+"</desc_type>";  
			                               }
			                               
			                           xml=xml+"<che_or_DD>"+rs2.getString("CHEQUE_OR_DD")+ "</che_or_DD>" +
			                           "<che_DD_no>"+rs2.getString("CHEQUE_DD_NO")+"</che_DD_no>" +
			                           "<che_DD_date>"+rs2.getString("cheq_dd_date")+"</che_DD_date>" +
			                           "<bank_na>"+rs2.getString("BANK_NAME")+"</bank_na>" +
			                           "<drawee>"+rs2.getString("DRAWEE_BRANCH")+"</drawee>" +
			                           "<micr>"+rs2.getString("BANK_MICR_CODE")+"</micr>"+
			                           "<sub_rec_frm>"+rs2.getString("RECEIVED_FROM")+
			                           "</sub_rec_frm><sub_amount>"+rs2.getString("AMOUNT")+
			                           "</sub_amount><sub_part>"+rs2.getString("PARTICULARS")+"</sub_part>";
			
                        }
                        System.out.println("xml--3-->"+xml);
                        
                       
                       /**
                        * PART IV
                        */
                       //-------------------------------------------------------------------------------------//
                       
                      
                       xml=xml+"<flag>success</flag>";                       
                       
                   }
                   catch(Exception e)
                   {
                       xml=xml+"<flag>failure</flag>";
                   }
                   xml=xml+"</response>";
                   System.out.println(xml);
                   out.println(xml);
    		}
        	
        
        
    }
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);
        }
        catch(IOException e)
        {}
    }
}

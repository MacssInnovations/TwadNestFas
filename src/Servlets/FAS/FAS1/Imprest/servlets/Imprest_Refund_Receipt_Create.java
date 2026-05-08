package Servlets.FAS.FAS1.Imprest.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import Servlets.FAS.FAS1.CommonControls.servlets.EmployeeDetails;

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

public class Imprest_Refund_Receipt_Create extends HttpServlet
{
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
       
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {        
    	
    	/** Variables Declaration */
    	String strCommand="";
        Connection con=null;
        ResultSet rs=null,rs2=null;
        CallableStatement cs=null;
        CallableStatement cs1=null;
        PreparedStatement ps=null,ps2=null,ps3=null;       
        String xml="";        
        HttpSession session=request.getSession(false);
        
    
        /** Session Checking */
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
        
        
        
        /** Database Connection */
        try
        {
                                ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                String ConnectionString="";

                                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                String strdsn=rs1.getString("Config.DSN");
                                String strhostname=rs1.getString("Config.HOST_NAME");
                                String strportno=rs1.getString("Config.PORT_NUMBER");
                                String strsid=rs1.getString("Config.SID");
                                String strdbusername=rs1.getString("Config.USER_NAME");
                                String strdbpassword=rs1.getString("Config.PASSWORD");
                                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
         }
         catch(Exception e)
         {
             System.out.println("Exception in opening connection :"+e);                    
         }

               
               
        /** Get Command Parameter */        
        try {
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
       
        
        if(strCommand.equalsIgnoreCase("Add")) 
        {
        	
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml="<response><command>Add</command>";
            Calendar c;
            
            
            int txtAcc_HeadCode=0;
            int cmbAcc_UnitCode=0;
            int cmbOffice_code=0;
            int txtCash_Month_hid=0;
            int txtCash_year=0;
            int txtReceipt_No=0;
            int txtCash_Acc_code=0,txtCash_Acc_code_cash=0;
            int Total_TRN_Rec=1;
            double txtAmount=0;
            String txtReceipt_type="B";
            String txtCR_DB="";
            int txtRecei_from=0;
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
            String txtMode_of_creat="";
            String txtCreat_By_Module="BR";
            
            int txtJournal_code=0;
            Date txtCha_Date=null;
            Date txtRec_Vou_date=null;
            int txtBankId=0;
            int txtBranchId=0;
            int txtNo_of_pay_voucher=0;
            int txtCha_No=0;
            int txtRec_Vou_No=0;
            long txtBankAccountNo=0;
            String txtAmtType="";
            
            String rad_ReClass="";
            String rem_current_month="",journal_type="";
            
            
            
            /* Receipt Reclassification */
            rad_ReClass="N";
            
            /* Remittance in Current Month */
            rem_current_month="Y";
            
            /* Mode of Creation */
            txtMode_of_creat="";
            
            
            /* Accounting Unit ID */
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            
            /* Accounting for Office ID */
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            
            /* Payment Voucher Number txtRec_Vou_No = cmbPayVocNo */
            try{
               txtRec_Vou_No=Integer.parseInt(request.getParameter("cmbPayVocNo"));
            }
            catch(NumberFormatException e)
            {
               System.out.println("exception"+e );
            }
            System.out.println("txtRec_Vou_No = cmbPayVocNo  "+txtRec_Vou_No);
            
            
            /* Receipt Number */
            try{txtReceipt_No=Integer.parseInt(request.getParameter("txtReceipt_No"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtReceipt_No "+txtReceipt_No);
            
            /* Receipt Date */
            String[] sd=request.getParameter("txtCrea_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            System.out.println("txtCrea_date "+txtCrea_date);
            
            
            /* Mode of Receipt */            
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
            
            /* Collection Account Code */
            try{txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Acc_code =====>> "+txtCash_Acc_code);
            
            try{txtCash_Acc_code_cash=Integer.parseInt(request.getParameter("txtCash_Acc_code_cash"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Acc_code_cash =====>> "+txtCash_Acc_code_cash);
            
            /* Bank Account Number */
            try{txtBankAccountNo=Long.parseLong(request.getParameter("txtBankAccountNo"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBankAccountNo "+txtBankAccountNo);
            
            /* Bank ID */
            try{txtBankId=Integer.parseInt(request.getParameter("txtBankID"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBankId "+txtBankId);
            
            
            /* Branch ID */
            try{txtBranchId=Integer.parseInt(request.getParameter("txtBranchID"));} 
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtBranchId "+txtBranchId);
            
            
            /* CR DR Indicator */
            txtCR_DB=request.getParameter("txtCR_DB");
            System.out.println("txtCR_DB "+txtCR_DB);
            
            
            /* Master Sub Ledger Type Code */
            try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
            catch(Exception e){System.out.println("exception"+e );}
            
            /* Master Sub Ledger Code */
            try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
            catch(Exception e){System.out.println("exception"+e );}
            
            
            /* Remarks */
            txtRemarks=request.getParameter("txtRemarks");
            System.out.println("txtRemarks "+txtRemarks);
          
           
            /* Received From */
            txtRecei_from=Integer.parseInt(request.getParameter("txtRecei_from"));
            System.out.println("txtRecei_from---> "+txtRecei_from);
            
            
            /* Total Amount Returned */
            try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+txtAmount);
            
           
            /* Payment Voucher Date txtRec_Vou_date = txtPaymentVoc_Date */
            String Rec_date=request.getParameter("txtPaymentVoc_Date");

           if(Rec_date.equals("") || Rec_date.equals(null))
           {
        	   
           }
           else    
            {
	            sd=request.getParameter("txtPaymentVoc_Date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            d=c.getTime();
	            txtRec_Vou_date=new Date(d.getTime());
            }
            System.out.println("after txtPaymentVoc_Date--> "+txtRec_Vou_date);
        
            try{txtMode_of_creat=request.getParameter("cmbAdvance_type");}
            catch(Exception e)
            {
                System.out.println("Exception in txtMode_of_creat ::: "+e.getMessage());
            }
            
            
            if(txtMode_of_creat.equals("I1"))
            {
            	txtMode_of_creat="I";
            	
            }
            else if(txtMode_of_creat.equals("I2"))
            {
            	txtMode_of_creat="I";
            	
            }
            else if(txtMode_of_creat.equals("T1"))
            {
            	txtMode_of_creat="T";
           		
            }
            else if(txtMode_of_creat.equals("T2"))
            {
            	txtMode_of_creat="T";
            	
            }
            
            
           /* Calculate Cashbook Month and year from Receipt Date */
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
                  Received_From_Txt = EmployeeDetails.getEmployeeDetails(con,txtRecei_from);                 
           }
           catch(Exception e) {
               System.out.println(e);
           }
           
           
           
            
             try 
                 {   
                     con.clearWarnings();
                     con.setAutoCommit(false);
                   
                     
					  cs= con.prepareCall("call FAS_RECEIPT_MASTER_PROC(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?,?,?,?,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?)");
                         cs.setInt(1,cmbAcc_UnitCode);
                         cs.setInt(2,txtCash_year);
                         cs.setInt(3,txtCash_Month_hid);
                         cs.setInt(4,txtReceipt_No);
                         cs.setInt(5,cmbOffice_code);
                         cs.setDate(6,txtCrea_date);
                         cs.setString(7,txtReceipt_type);
                         System.out.println("txtCash_Acc_code::::"+txtCash_Acc_code);
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
                         cs.setString(28,"insert");                     
                         cs.registerOutParameter(4,java.sql.Types.NUMERIC);
                         cs.registerOutParameter(29,java.sql.Types.NUMERIC); 
                         cs.setNull(4, java.sql.Types.NUMERIC);
		                 cs.setNull(29, java.sql.Types.NUMERIC);
                         cs.setInt(30,cmbMas_SL_type);
                         cs.setInt(31,cmbMas_SL_Code);
                         cs.setString(32,update_user);
                         cs.setTimestamp(33,ts);
                         cs.setString(34,rem_current_month);
                       
                     cs.execute();
                     
//                     txtReceipt_No=cs.getInt(4);
//                     int errcode=cs.getInt(29);
                     txtReceipt_No = cs.getBigDecimal(4).intValue();
	                 int errcode = cs.getBigDecimal(29).intValue();
                     
                     System.out.println("Receipt Master Table Error code ---> "+errcode);
                     
                     if(errcode!=0)
                     {         
                       System.out.println("The Receipt Creation Failed to INSERT values");
                       sendMessage(response,"The Bank Receipt Creation Failed ","ok");
                       xml=xml+"<flag>failure</flag>";                          
                     }
                     else
                     {  
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
                               for(int k=0;k<Grid_H_code.length;k++) 
                               {
                                   try{txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   rad_sub_CR_DR=Grid_CR_DR_type[k];
                                   
                                   try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   
                                   
                                   try{ txtCheque_DD=Grid_Cheque_DD[k];
                                       Remittance_Type = txtCheque_DD;
                                   }catch(Exception e){System.out.println(e);}
                                   try{ txtCheque_DD_NO=Grid_Cheque_DD_NO[k];}catch(Exception e){System.out.println(e);}
                                   
                                   
                                   
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
                                   
                                   System.out.println("Grid_H_code[k]---> "+Grid_H_code[k]);
                                   System.out.println("Grid_CR_DR_type[k] ---> "+Grid_CR_DR_type[k]);
                                   System.out.println("Grid_SL_type[k]--->"+Grid_SL_type[k]);
                                   System.out.println("Grid_SL_code[k]--->"+Grid_SL_code[k]+"from here"+cmbSL_Code);
                                   System.out.println("Grid_Cheque_DD[k]--->"+Grid_Cheque_DD[k]);
                                   System.out.println("Grid_Cheque_DD_NO[k]--->"+Grid_Cheque_DD_NO[k]);
                                   System.out.println("txtCheque_DD_date--->"+txtCheque_DD_date+"date"+Grid_Cheque_DD_date[k]);
                                   System.out.println("Grid_Bank_Name[k]--->"+Grid_Bank_Name[k]);
                                   System.out.println("Grid_Draw_BR[k]-->"+Grid_Draw_BR[k]);
                                   System.out.println("Grid_Bank_M_Code[k]--->"+Grid_Bank_M_Code[k]);

                                   
                                 
                                   System.out.println("amount");
                                   
                                   txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);
                                   txtParticular=Grid_particular[k];
                                   
                                   System.out.println("amount");
                                   System.out.println("Grid_sl_amt[k] "+Grid_sl_amt[k]);                                 
                                   System.out.println("Grid_particular[k] "+Grid_particular[k]);
                                   
                                   ps.setInt(1,cmbAcc_UnitCode);
                                   ps.setInt(2,cmbOffice_code);
                                   ps.setInt(3,txtCash_year);
                                   ps.setInt(4,txtCash_Month_hid);
                                   ps.setInt(5,txtReceipt_No);
                                   ps.setInt(6,SL_NO);
                                   
                                   System.out.println("unit id -->"+cmbAcc_UnitCode);
                                   System.out.println("Office -->"+cmbOffice_code);
                                   System.out.println("yr--->"+txtCash_year);
                                   System.out.println("mon-->"+txtCash_Month_hid);
                                   System.out.println("rece_no-->"+txtReceipt_No);
                                   System.out.println("sl_no-->"+SL_NO);
                                   
                                   System.out.println("11");
                                   
                                   ps.setInt(7,txtAcc_HeadCode);
                                 //  System.out.println("22");
                                   ps.setString(8,rad_sub_CR_DR);
                                //   System.out.println("33");
                                   ps.setInt(9,cmbSL_type);
                                //   System.out.println("44");
                                   ps.setInt(10,cmbSL_Code);
                               //    System.out.println("55");
                                   ps.setString(11,txtsub_Recei_from);
                                //   System.out.println("66");
                                   ps.setString(12,txtCheque_DD);
                                //   System.out.println("77");
                                   ps.setString(13,txtCheque_DD_NO);
                              //     System.out.println("88");
                                   ps.setDate(14,txtCheque_DD_date);
                                //   System.out.println("99");
                                   ps.setString(15,txtBank_Name);
                                //   System.out.println("aa");
                                   ps.setString(16,txtDraw_BR);
                                //   System.out.println("bb");
                                   ps.setString(17,txtBank_M_Code);
                                 //  System.out.println("cc");
                                   ps.setDouble(18,txtsub_Amount);
                                 //  System.out.println("dd");
                                   ps.setString(19,txtParticular);
                                 //  System.out.println("ee");
                                   ps.setString(20,update_user);
                                //   System.out.println("ff");
                                   ps.setTimestamp(21,ts);
                                 //  System.out.println("gg");
                                  
                                  // CB Ref Number and Date  
                                  
                                  // ps.setInt(22,0);     
                                   ps.setInt(22,txtRec_Vou_No);
                                   System.out.println("txtRec_Vou_No:"+txtRec_Vou_No);
                                   System.out.println("hh");
                                   ps.setDate(23,txtRef_date);
                                   System.out.println("ii");
                                   
                                   SL_NO++;
                                   
                                  try
                                  { 
                                	  int j1=ps.executeUpdate(); 
                                	 if(j1>0)
                                      {
                                   	   System.out.println("j1"+j1);                                   	   
                                   	String sql2="select a.AMT_SANCTIONED,b.AMT_SPENT,b.CB_REF_NO,c.receipt_amt,a.AMT_SANCTIONED-(decode(b.AMT_SPENT,null,0,b.AMT_SPENT)+decode(c.receipt_amt,null,0,c.receipt_amt)) as BALANCE from (select sum(AMOUNT)AS AMT_SANCTIONED,VOUCHER_NO from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" AND SUB_LEDGER_CODE="+cmbSL_Code+" group by VOUCHER_NO)a left outer join (select sum(AMOUNT)AS AMT_SPENT,CB_REF_NO from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" AND SUB_LEDGER_CODE="+cmbSL_Code+" and CR_DR_INDICATOR='DR' group by CB_REF_NO)b on a.VOUCHER_NO=b.CB_REF_NO left outer join (select sum(AMOUNT)AS receipt_amt,CB_REF_NO from FAS_RECEIPT_TRANSACTION  where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" AND SUB_LEDGER_CODE="+cmbSL_Code+"  group by CB_REF_NO)c ON a.VOUCHER_NO=c.CB_REF_NO ";
                                   	  // 	String sql2="select a.AMT_SANCTIONED,b.AMT_SPENT,b.CB_REF_NO,c.receipt_amt,a.AMT_SANCTIONED-(decode(b.AMT_SPENT,null,0,b.AMT_SPENT)+decode(c.receipt_amt,null,0,c.receipt_amt)) as BALANCE from (select sum(AMOUNT)AS AMT_SANCTIONED,VOUCHER_NO from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" AND SUB_LEDGER_CODE="+cmbSL_Code+" group by VOUCHER_NO)a left outer join (select sum(AMOUNT)AS AMT_SPENT,CB_REF_NO from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" AND SUB_LEDGER_CODE="+cmbSL_Code+" and CR_DR_INDICATOR='DR' group by CB_REF_NO)b on a.VOUCHER_NO=b.CB_REF_NO left outer join (select sum(AMOUNT)AS receipt_amt,CB_REF_NO from FAS_RECEIPT_TRANSACTION  where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" AND SUB_LEDGER_CODE="+cmbSL_Code+"  group by CB_REF_NO)c ON a.VOUCHER_NO=c.CB_REF_NO ";
                                   	   System.out.println("sql2 for up::::"+sql2);
                                   	   ps2=con.prepareStatement(sql2);
                                   	   rs2=ps2.executeQuery();
                                   	   System.out.println("rs2"+rs2);
                                   	   while(rs2.next())
                                   	   {
                                   		   System.out.println("inside while");
                                   		   int balanAmt=rs2.getInt("BALANCE");
                                   		   int cbNo=rs2.getInt("CB_REF_NO");
                                   		   System.out.println("cbNo::"+cbNo);
                                   		   System.out.println("balanAmt"+balanAmt);
                                   		   if(balanAmt==0)
                                   		   {
                                   			  System.out.println("blance zerooooooooooooooooo");
                                   			  ps3=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set AMOUNT_FULLY_SPENT='F' where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and VOUCHER_NO="+cbNo+" AND SUB_LEDGER_CODE="+cmbSL_Code);
                                   			  ps3.executeUpdate();
   	                             	 		  
                                   		   }
                                   		   else
                                   		   {
                                   			   System.out.println("balance not equal");
                                    			  ps3=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set AMOUNT_FULLY_SPENT='N' where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and VOUCHER_NO="+cbNo+" AND SUB_LEDGER_CODE="+cmbSL_Code);
                                    			  ps3.executeUpdate();
                                   		   }
                                   	   }
                                      }  
                                  }
                                  catch(Exception e) {
                                      System.out.println("Trans Insertion Error===>"+e);
                                  }
                                  

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
                              System.out.println("b4 commit");
                             
                             
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
//                        		 con.prepareCall("{call FAS_ECS_REMITTANCE_PROC(?,?,?,?,?,?,?,?,?,?)}") ;                          
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
                         if (err_code.equals("0")) {
                           con.commit();
                           sendMessage(response,"The Bank Receipt Number '"+txtReceipt_No+"' has been Created Successfully ","ok");
                         }
                         else {
                           sendMessage(response,"The Bank Receipt Creation Failed ","ok"); 
                         }
                         
                       }   
                       else {
                           con.commit(); 
                           sendMessage(response,"The Bank Receipt Number '"+txtReceipt_No+"' has been Created Successfully ","ok");
                       }
                      
                     }
                    
                 }
                 
                 catch(Exception e) 
                 {
                     try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                     sendMessage(response,"The Bank Receipt Creation Failed ","ok");
                     System.out.println("Exception occur due to insert in receipt transaction"+e);
                 }
                 finally
                 {
                     System.out.println("done");
                     try{con.setAutoCommit(true);  }catch(SQLException sqle){}
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
        String strCommand="",payment_type1="";
        
        
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
        String createdByModule="";
        int cmbAcc_UnitCode=0;        
        int cmbOffice_code=0;
        int txtCB_Year=0;
        int txtCB_Month=0;
        String txtMode_of_creat="",textMode="";
        /** Get Accounting Unit ID */    
        try{
        	cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        }catch(Exception e){System.out.println("Exception to Read Reason Code ");}
        
        
        /** Get Accouting for office id */
        try{
        	cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
        }catch(Exception e){System.out.println("Exception to Read Reason short Description");}
        
        
        /** Get Cashbook Year */
        try{
        	txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        }catch(Exception e){System.out.println("Exception to Read Cashbook Year ");}
        

        
        /** Get Cashbook Month */
        try{
        	txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
        }catch(Exception e){System.out.println("Exception to Read Cashbook Month ");}
        
        try{txtMode_of_creat=request.getParameter("txtMode_of_creat");}
        catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
        
        if(txtMode_of_creat.equals("I1"))
        {
        	txtMode_of_creat="I";
        	textMode="0";
        	payment_type1="B";
        	createdByModule="BPF";
        	
        }
        else if(txtMode_of_creat.equals("I2"))
        {
        	txtMode_of_creat="I";
        	textMode="IT";
        	payment_type1="C";
        	createdByModule="SC";
        }
        else if(txtMode_of_creat.equals("T1"))
        {
        	txtMode_of_creat="T";
	       	textMode="0";
	       	payment_type1="B";
	       	createdByModule="BPF";
        	
        }
        else if(txtMode_of_creat.equals("T2"))
        {
        	txtMode_of_creat="T";
        	textMode="IT";
        	payment_type1="C";
        	createdByModule="SC";
        }
        
        if(strCommand.equalsIgnoreCase("LoadPayVoucherNo")) 
        {
        	 int finyear1=Integer.parseInt(request.getParameter("fyear1"));
             int fmonth=Integer.parseInt(request.getParameter("fmonth"));
        	 String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             String xml="";
             int count=0;
             xml="<response><command>LoadPayVoucherNo</command>";
             
                           
                                  System.out.println("be4 sql");
                                  String sql="SELECT *\n" + 
                    	    	  "FROM\n" + 
                    	    	  "  (SELECT voucher_no,\n" + 
                    	    	  "    payment_date,\n" + 
                    	    	  "    sl_type_code,\n" + 
                    	    	  "    sl_code,\n" + 
                    	    	  "    (pay_amt - jour_amt - rec_amt) AS bal\n" + 
                    	    	  "  FROM\n" + 
                    	    	  "    (SELECT voucher_no,\n" + 
                    	    	  "      payment_date,\n" + 
                    	    	  "      sl_type_code,\n" + 
                    	    	  "      sl_code,\n" + 
                    	    	  "      pay_amt,\n" + 
                    	    	  "      DECODE(SUM(jour_amt),NULL,0,SUM(jour_amt)) AS jour_amt ,\n" + 
                    	    	  "      DECODE(SUM(rec_amt),NULL,0,SUM(rec_amt))   AS rec_amt\n" + 
                    	    	  "    FROM\n" + 
                    	    	  "      (SELECT voucher_no,\n" + 
                    	    	  "        payment_date,\n" + 
                    	    	  "        sl_type_code,\n" + 
                    	    	  "        sl_code,\n" + 
                    	    	  "        pay_amt,\n" + 
                    	    	  "        jour_amt,\n" + 
                    	    	  "        0 AS rec_amt\n" + 
                    	    	  "      FROM\n" + 
                    	    	  "        (SELECT *\n" + 
                    	    	  "        FROM\n" + 
                    	    	  "          (SELECT m.voucher_no,\n" + 
                    	    	  "            TO_CHAR(m.PAYMENT_DATE,'DD/MM/YY')AS payment_date,\n" + 
                    	    	  "            t.SUB_LEDGER_TYPE_CODE            AS sl_type_code,\n" + 
                    	    	  "            t.SUB_LEDGER_CODE                 AS sl_code,\n" + 
                    	    	  "            t.AMOUNT                          AS pay_amt\n" + 
                    	    	  "          FROM fas_payment_master m,\n" + 
                    	    	  "            fas_payment_transaction t\n" + 
                    	    	  "          WHERE m.accounting_unit_id     = "+cmbAcc_UnitCode+"\n" + 
//                    	    	  "           AND to_date(m.cashbook_month\n" + 
//                    	    	  "          ||'-'\n" + 
//                    	    	  "          || + m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )\n" + 
//                    	    	  "          || '-'\n" + 
//                    	    	  "          || ("+finyear1+") , 'mm-yyyy')\n" + 
//                    	    	  "        AND to_date( ( "+txtCB_Month+" )\n" + 
//                    	    	  "          ||'-'\n" + 
//                    	    	  "          || ( "+txtCB_Year+" ) , 'mm-yyyy')\n" + 

 								  "	AND M.CASHBOOK_MONTH = " + txtCB_Month +"\n" +
 								  "	AND m.cashbook_year= " + txtCB_Year +"\n"+
                    	    	  "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                    	    	  "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                    	    	  "          AND m.payment_status           ='L'\n" + 
                    	    	  "          And m.payment_type ='"+payment_type1+"'\n" + 
                    	    	  "          AND CREATED_BY_MODULE='"+createdByModule+"'\n" + 
                    	    	  "           AND (t.AMOUNT_FULLY_SPENT !='F' or t.AMOUNT_FULLY_SPENT is null)\n" + 
                    	    	  "          AND m.accounting_unit_id       = t.accounting_unit_id\n" + 
                    	    	  "          AND m.ACCOUNTING_FOR_OFFICE_ID =t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    	    	  "          AND m.cashbook_month           =t.cashbook_month\n" + 
                    	    	  "          AND m.cashbook_year            =t.cashbook_year\n" + 
                    	    	  "          AND m.voucher_no               =t.voucher_no\n" + 
                    	    	  "          )p\n" + 
                    	    	  "        LEFT OUTER JOIN\n" + 
                    	    	  "          (SELECT t.SUB_LEDGER_TYPE_CODE AS j_sl_type_code,\n" + 
                    	    	  "            t.SUB_LEDGER_CODE            AS j_sl_code,\n" + 
                    	    	  "            t.cb_ref_no,\n" + 
                    	    	  "            TO_CHAR(t.cb_ref_date,'DD/MM/YY') AS cb_ref_date,\n" + 
                    	    	  "            amount                            AS jour_amt\n" + 
                    	    	  "          FROM fas_journal_master m,\n" + 
                    	    	  "            fas_journal_transaction t\n" + 
                    	    	  "          WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                    	    	  "          AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                    	    	  "          AND m.cashbook_month           =t.cashbook_month\n" + 
                    	    	  "          AND m.cashbook_year            =t.cashbook_year\n" + 
                    	    	  "          AND m.voucher_no               = t.voucher_no\n" + 
                    	    	  "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                    	    	  "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                    	    	  "          AND m.JOURNAL_STATUS           ='L'\n" + 
                    	    	  "          AND t.cr_dr_indicator          ='CR'\n" + 
                    	    	  "          AND m.accounting_unit_id       ="+cmbAcc_UnitCode+"\n" + 
                    	    	  "          )j\n" + 
                    	    	  "        ON p.voucher_no    = j.CB_REF_NO\n" + 
                    	    	  "        AND p.payment_date = j.CB_REF_DATE\n" + 
                    	    	  "        AND p.sl_type_code = j_sl_type_code\n" + 
                    	    	  "        AND p.sl_code      = j_sl_code\n" + 
                    	    	  "          --   where voucher_no is not null\n" + 
                    	    	  "        )\n" + 
                    	    	  "      UNION ALL\n" + 
                    	    	  "      SELECT voucher_no,\n" + 
                    	    	  "        payment_date,\n" + 
                    	    	  "        sl_type_code,\n" + 
                    	    	  "        sl_code,\n" + 
                    	    	  "        pay_amt,\n" + 
                    	    	  "        0 AS jour_amt,\n" + 
                    	    	  "        rec_amt\n" + 
                    	    	  "      FROM\n" + 
                    	    	  "        (SELECT *\n" + 
                    	    	  "        FROM\n" + 
                    	    	  "          (SELECT m.voucher_no,\n" + 
                    	    	  "            TO_CHAR(m.PAYMENT_DATE,'DD/MM/YY')AS payment_date,\n" + 
                    	    	  "            t.SUB_LEDGER_TYPE_CODE            AS sl_type_code,\n" + 
                    	    	  "            t.SUB_LEDGER_CODE                 AS sl_code,\n" + 
                    	    	  "            t.AMOUNT                          AS pay_amt\n" + 
                    	    	  "          FROM fas_payment_master m,\n" + 
                    	    	  "            fas_payment_transaction t\n" + 
                    	    	  "          WHERE m.accounting_unit_id     = "+cmbAcc_UnitCode+"\n" + 
//                    	    	  "         AND to_date(m.cashbook_month\n" + 
//                    	    	  "          ||'-'\n" + 
//                    	    	  "          || + m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )\n" + 
//                    	    	  "          || '-'\n" + 
//                    	    	  "          || ("+finyear1+") , 'mm-yyyy')\n" + 
//                    	    	  "        AND to_date( ( "+txtCB_Month+" )\n" + 
//                    	    	  "          ||'-'\n" + 
//                    	    	  "          || ( "+txtCB_Year+" ) , 'mm-yyyy')\n" + 
								  "	         AND M.CASHBOOK_MONTH = " + txtCB_Month +"\n" +
								  "	         AND m.cashbook_year= " + txtCB_Year +"\n"+
                    	    	  "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                    	    	  "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                    	    	  "          AND m.payment_status           ='L'\n" + 
                    	    	  "          and m.payment_type ='"+payment_type1+"'\n" + 
                    	    	  "          AND CREATED_BY_MODULE='"+createdByModule+"'\n" + 
                    	    	  "          AND (t.AMOUNT_FULLY_SPENT !='F' or t.AMOUNT_FULLY_SPENT is null)\n" + 
                    	    	  "          AND m.accounting_unit_id       = t.accounting_unit_id\n" + 
                    	    	  "          AND m.ACCOUNTING_FOR_OFFICE_ID =t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    	    	  "          AND m.cashbook_month           =t.cashbook_month\n" + 
                    	    	  "          AND m.cashbook_year            =t.cashbook_year\n" + 
                    	    	  "          AND m.voucher_no               =t.voucher_no\n" + 
                    	    	  "          )p\n" + 
                    	    	  "        RIGHT OUTER JOIN\n" + 
                    	    	  "          (SELECT t.SUB_LEDGER_TYPE_CODE AS r_sl_type_code,\n" + 
                    	    	  "            t.SUB_LEDGER_CODE            AS r_sl_code,\n" + 
                    	    	  "            m.RECEIVABLE_VOUCHER_NO,\n" + 
                    	    	  "            TO_CHAR(m.RECEIVABLE_VOUCHER_DATE,'DD/MM/YY') AS RECEIVABLE_VOUCHER_DATE,\n" + 
                    	    	  "            t.AMOUNT    AS rec_amt\n" + 
                    	    	  "          FROM fas_receipt_master m,\n" + 
                    	    	  "            fas_receipt_transaction t\n" + 
                    	    	  "          WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                    	    	  "          AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                    	    	  "          AND m.cashbook_month           =t.cashbook_month\n" + 
                    	    	  "          AND m.cashbook_year            =t.cashbook_year\n" + 
                    	    	  "          AND m.receipt_no               = t.receipt_no\n" + 
                    	    	  "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                    	    	  "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                    	    	  "          AND m.accounting_unit_id       ="+cmbAcc_UnitCode+"\n" + 
                    	    	  "          AND m.RECEIPT_STATUS           ='L'\n" + 
                    	    	  "          )r\n" + 
                    	    	  "        ON p.voucher_no    = r.RECEIVABLE_VOUCHER_NO\n" + 
                    	    	  "        AND p.payment_date = r.RECEIVABLE_VOUCHER_DATE\n" + 
                    	    	  "        AND p.sl_type_code = r.r_sl_type_code\n" + 
                    	    	  "        AND p.sl_code      = r.r_sl_code\n" + 
                    	    	  "        )\n" + 
                    	    	  "      )\n" + 
                    	    	  "    GROUP BY voucher_no,\n" + 
                    	    	  "      payment_date,\n" + 
                    	    	  "      sl_type_code,\n" + 
                    	    	  "      sl_code ,\n" + 
                    	    	  "      pay_amt\n" + 
                    	    	  "    ORDER BY voucher_no,\n" + 
                    	    	  "      payment_date,\n" + 
                    	    	  "      sl_type_code,\n" + 
                    	    	  "      sl_code\n" + 
                    	    	  "    )\n" + 
                    	    	  "  ) x\n" + 
                    	    	  "LEFT OUTER JOIN\n" + 
                    	    	  "  (SELECT e.EMPLOYEE_ID,\n" + 
                    	    	  "    e.EMPLOYEE_NAME\n" + 
                    	    	  "    ||'.'\n" + 
                    	    	  "    ||e.EMPLOYEE_INITIAL\n" + 
                    	    	  "    ||'-'\n" + 
                    	    	  "    || d.DESIGNATION AS ENAME\n" + 
                    	    	  "  FROM HRM_MST_EMPLOYEES e,\n" + 
                    	    	  "    HRM_EMP_CURRENT_POSTING c,\n" + 
                    	    	  "    HRM_MST_DESIGNATIONS d\n" + 
                    	    	  "  WHERE c.DESIGNATION_ID=d.DESIGNATION_ID\n" + 
                    	    	  "  AND e.EMPLOYEE_ID     =c.EMPLOYEE_ID\n" + 
                    	    	  "  ) y\n" + 
                    	    	  "ON x.sl_code = y.employee_id\n" + 
                    	    	  "WHERE bal    >0\n";   
                             
                                  
                                  
                  try {
                    System.out.println("sql"+sql);		
                     ps2=con.prepareStatement(sql);
                                                   
				 rs2= ps2.executeQuery();
				 while (rs2.next())
				 {
				   xml=xml+"<voucher_no>"+rs2.getString("voucher_no")+"</voucher_no>";
				   xml+= "<sl_code>"+rs2.getInt("sl_code")+"</sl_code>";
				   count++;
				 }
				 if( count ==0)
				 {
					xml=xml+"<flag>NoRecords</flag>"; 
				 }else
				 {
					 xml=xml+"<flag>Success</flag>";
				 }
				 
				 
			     } catch (SQLException e)
                             {
                             	xml=xml+"<flag>Failure</flag>";
				e.printStackTrace();				
			    } 
			    xml=xml+"</response>";
           	 System.out.println(xml);		
             out.println(xml);
             
         }
      
        else if (strCommand.equalsIgnoreCase("LoadPayVoucherDetails"))
        {

        	 int finyear1=Integer.parseInt(request.getParameter("fyear1"));
             int fmonth=Integer.parseInt(request.getParameter("fmonth"));
        	 /** Get Voucher Number */
        	
        	 
        	 String vno_one=request.getParameter("cmbPayVocNo");
             String vno_split[]=vno_one.split("-");
             String vno=vno_split[0];
             String slCode=vno_split[1];
        	
                String CONTENT_TYPE = "text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                String xml="";
                int count=0;
                xml="<response><command>LoadPayVoucherDetails</command>";
             
                                     String sql="select	 "+
						"  to_char(PAYMENT_DATE,'DD/MM/YYYY')as payment_date,       "+
               			                "  TOTAL_AMOUNT,     "+
               			                "  PAID_TO  		   "+		
						" from		"+
						"  fas_payment_master	    "+
						" where 		 "+
						" (mode_of_creation='"+txtMode_of_creat+"'or mode_of_creation='"+textMode+"')"+
						" and accounting_unit_id= "+cmbAcc_UnitCode+
//					 "         AND to_date(cashbook_month\n" + 
//           	    	  "          ||'-'\n" + 
//           	    	  "          ||  cashbook_year, 'mm-yyyy') BETWEEN to_date( "+fmonth+" \n" + 
//           	    	  "          || '-'\n" + 
//           	    	  "          || "+finyear1+" , 'mm-yyyy')\n" + 
//           	    	  "        AND to_date(  "+txtCB_Month+" \n" + 
//           	    	  "          ||'-'\n" + 
//           	    	  "          ||  "+txtCB_Year+" , 'mm-yyyy')\n" + 
						"         AND cashbook_month = " + txtCB_Month +"\n" +
				        "	      AND cashbook_year= " + txtCB_Year +"\n"+
						
						" and payment_type = '"+payment_type1+"'" +
						" and payment_status='L'	       "+
						" and VOUCHER_NO ="+vno+
					//	" and CASHBOOK_YEAR ="+txtCB_Year+
					//	" and CASHBOOK_MONTH="+txtCB_Month+		
					    " order by voucher_no ";
             
                                try {
				System.out.println("details:::"+sql);
                                 ps2=con.prepareStatement(sql);		
          	 
				 rs2= ps2.executeQuery();
                                 
				 while (rs2.next())
				 {
				   xml=xml+"<payment>";	 
				   xml=xml+"<pay_date>"+rs2.getString("payment_date")+"</pay_date>";
				   xml=xml+"<tot_amt>"+rs2.getString("TOTAL_AMOUNT")+"</tot_amt>";
				   xml=xml+"<paid_to>"+rs2.getString("PAID_TO")+"</paid_to>";
				   xml=xml+"</payment>";
				   count++;
				 }
				 if( count == 0)
				 {
					xml=xml+"<flag>NoRecords</flag>"; 
				 }
				 else
				 {
                     String sql3="select kk.employee_id,\n" + 
                     "   sum(kk.bal) as ttlBalance,kk.ename,kk.sl_type_code,kk.sl_code \n" + 
                     " from (SELECT *\n" + 
                     "FROM\n" + 
                     "  (SELECT voucher_no,\n" + 
                     "    payment_date,\n" + 
                     "    sl_type_code,\n" + 
                     "    sl_code,\n" + 
                     "    (pay_amt - jour_amt - rec_amt) AS bal\n" + 
                     "  FROM\n" + 
                     "    (SELECT voucher_no,\n" + 
                     "      payment_date,\n" + 
                     "      sl_type_code,\n" + 
                     "      sl_code,\n" + 
                     "      pay_amt       AS pay_amt,\n" + 
                     "      SUM(jour_amt) AS jour_amt ,\n" + 
                     "      SUM(rec_amt)  AS rec_amt\n" + 
                     "    FROM\n" + 
                     "      (SELECT voucher_no,\n" + 
                     "        payment_date,\n" + 
                     "        sl_type_code,\n" + 
                     "        sl_code,\n" + 
                     "        pay_amt,\n" + 
                     "        jour_amt,\n" + 
                     "        0 AS rec_amt\n" + 
                     "      FROM\n" + 
                     "        (SELECT *\n" + 
                     "        FROM\n" + 
                     "          (SELECT m.voucher_no,\n" + 
                     "            TO_CHAR(m.PAYMENT_DATE,'DD/MM/YY')AS payment_date,\n" + 
                     "            t.SUB_LEDGER_TYPE_CODE            AS sl_type_code,\n" + 
                     "            t.SUB_LEDGER_CODE                 AS sl_code,\n" + 
                     "            t.AMOUNT                          AS pay_amt\n" + 
                     "          FROM fas_payment_master m,\n" + 
                     "            fas_payment_transaction t\n" + 
                     "          WHERE m.accounting_unit_id     = "+cmbAcc_UnitCode+"\n" + 
//                   "  and To_Date((m.Cashbook_Month  "+
//                    "		  ||'-'"+
//                    "		  || m.Cashbook_Year),'mm-yyyy') BETWEEN To_Date("+fmonth+
//                    "		  ||'-'"+
//                    "		  ||"+finyear1+",'mm-yyyy')"+
//                    "		AND to_date("+txtCB_Month+
//                    "		  ||'-'"+
//                    "		  ||"+txtCB_Year+",'mm-yyyy')"+
				      "         AND M.CASHBOOK_MONTH = " + txtCB_Month +"\n" +
				      "	        AND m.cashbook_year= " + txtCB_Year +"\n"+

                     "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                     "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                     "          AND m.payment_status           ='L'\n" + 
                     "          AND payment_type               = '"+payment_type1+"'\n" + 
                     "          AND m.accounting_unit_id       = t.accounting_unit_id\n" + 
                     "          AND m.ACCOUNTING_FOR_OFFICE_ID =t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                     "          AND m.cashbook_month           =t.cashbook_month\n" + 
                     "          AND m.cashbook_year            =t.cashbook_year\n" + 
                     "          AND m.voucher_no               =t.voucher_no\n" + 
                     "          )p\n" + 
                     "        LEFT OUTER JOIN\n" + 
                     "          (SELECT t.SUB_LEDGER_TYPE_CODE AS j_sl_type_code,\n" + 
                     "            t.SUB_LEDGER_CODE            AS j_sl_code,\n" + 
                     "            t.cb_ref_no,\n" + 
                     "            TO_CHAR(t.cb_ref_date,'DD/MM/YY') AS cb_ref_date,\n" + 
                     "            amount                            AS jour_amt\n" + 
                     "          FROM fas_journal_master m,\n" + 
                     "            fas_journal_transaction t\n" + 
                     "          WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                     "          AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                     "          AND m.cashbook_month           =t.cashbook_month\n" + 
                     "          AND m.cashbook_year            =t.cashbook_year\n" + 
                     "          AND m.voucher_no               = t.voucher_no\n" + 
                     "          AND m.MODE_OF_CREATION         ='"+txtMode_of_creat+"'\n" + 
                     "          AND m.JOURNAL_STATUS           ='L'\n" + 
                     "          AND t.cr_dr_indicator          ='CR'\n" + 
                     "          AND m.accounting_unit_id       ="+cmbAcc_UnitCode+"\n" + 
                     "          )j\n" + 
                     "        ON p.voucher_no    = j.CB_REF_NO\n" + 
                     "        AND p.payment_date = j.CB_REF_DATE\n" + 
                     "        AND p.sl_type_code = j_sl_type_code\n" + 
                     "        AND p.sl_code      = j_sl_code\n" + 
                     "        )\n" + 
                     "      UNION ALL\n" + 
                     "      SELECT voucher_no,\n" + 
                     "        payment_date,\n" + 
                     "        sl_type_code,\n" + 
                     "        sl_code,\n" + 
                     "        pay_amt,\n" + 
                     "        0 AS jour_amt,\n" + 
                     "        rec_amt\n" + 
                     "      FROM\n" + 
                     "        (SELECT *\n" + 
                     "        FROM\n" + 
                     "          (SELECT m.voucher_no,\n" + 
                     "            TO_CHAR(m.PAYMENT_DATE,'DD/MM/YY')AS payment_date,\n" + 
                     "            t.SUB_LEDGER_TYPE_CODE            AS sl_type_code,\n" + 
                     "            t.SUB_LEDGER_CODE                 AS sl_code,\n" + 
                     "            t.AMOUNT                          AS pay_amt\n" + 
                     "          FROM fas_payment_master m,\n" + 
                     "            fas_payment_transaction t\n" + 
                     "          WHERE m.accounting_unit_id     = "+cmbAcc_UnitCode+"\n" + 
//                 " and To_Date((m.Cashbook_Month  "+
//                        "		  ||'-'"+
//                        "		  || m.Cashbook_Year),'mm-yyyy') BETWEEN To_Date("+fmonth+
//                        "		  ||'-'"+
//                        "		  ||"+finyear1+",'mm-yyyy')"+
//                        "		AND to_date("+txtCB_Month+
//                        "		  ||'-'"+
//                        "		  ||"+txtCB_Year+",'mm-yyyy')"+

					 "         AND M.CASHBOOK_MONTH = " + txtCB_Month +"\n" +
					 "	        AND m.cashbook_year= " + txtCB_Year +"\n"+
                     "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                     "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                     "          AND m.payment_status           ='L'\n" + 
                     "          AND payment_type               = '"+payment_type1+"'\n" + 
                     "          AND m.accounting_unit_id       = t.accounting_unit_id\n" + 
                     "          AND m.ACCOUNTING_FOR_OFFICE_ID =t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                     "          AND m.cashbook_month           =t.cashbook_month\n" + 
                     "          AND m.cashbook_year            =t.cashbook_year\n" + 
                     "          AND m.voucher_no               =t.voucher_no\n" + 
                     "          )p\n" + 
                     "        LEFT OUTER JOIN\n" + 
                     "          (SELECT t.SUB_LEDGER_TYPE_CODE AS r_sl_type_code,\n" + 
                     "            t.SUB_LEDGER_CODE            AS r_sl_code,\n" + 
                     "            m.RECEIVABLE_VOUCHER_NO,\n" + 
                     "            TO_CHAR(m.RECEIVABLE_VOUCHER_DATE,'DD/MM/YY') AS RECEIVABLE_VOUCHER_DATE,\n" + 
                     "            t.AMOUNT                                      AS rec_amt\n" + 
                     "          FROM fas_receipt_master m,\n" + 
                     "            fas_receipt_transaction t\n" + 
                     "          WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                     "          AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                     "          AND m.cashbook_month           =t.cashbook_month\n" + 
                     "          AND m.cashbook_year            =t.cashbook_year\n" + 
                     "          AND m.receipt_no               = t.receipt_no\n" + 
                     "          AND m.MODE_OF_CREATION         ='"+txtMode_of_creat+"'\n" + 
                     "          AND m.accounting_unit_id       ="+cmbAcc_UnitCode+"\n" + 
                     "          AND m.RECEIPT_STATUS           ='L'\n" + 
                     "          )r\n" + 
                     "        ON p.voucher_no    = r.RECEIVABLE_VOUCHER_NO\n" + 
                     "        AND p.payment_date = r.RECEIVABLE_VOUCHER_DATE\n" + 
                     "        AND p.sl_type_code = r.r_sl_type_code\n" + 
                     "        AND p.sl_code      = r.r_sl_code\n" + 
                     "        )\n" + 
                     "      )\n" + 
                     "    GROUP BY voucher_no,\n" + 
                     "      payment_date,\n" + 
                     "      sl_type_code,\n" + 
                     "      sl_code ,\n" + 
                     "      pay_amt\n" + 
                     "    ORDER BY payment_date,\n" + 
                     "      voucher_no,\n" + 
                     "      sl_type_code,\n" + 
                     "      sl_code\n" + 
                     "    )\n" + 
                     "  ) x\n" + 
                     " LEFT OUTER JOIN\n" + 
                     "  (SELECT e.EMPLOYEE_ID,\n" + 
                     "    e.EMPLOYEE_NAME\n" + 
                     "    ||'.'\n" + 
                     "    ||e.EMPLOYEE_INITIAL\n" + 
                     "    ||'-'\n" + 
                     "    || d.DESIGNATION AS ENAME\n" + 
                     "  FROM HRM_MST_EMPLOYEES e,\n" + 
                     "    HRM_EMP_CURRENT_POSTING c,\n" + 
                     "    HRM_MST_DESIGNATIONS d\n" + 
                     "  WHERE c.DESIGNATION_ID=d.DESIGNATION_ID\n" + 
                     "  AND e.EMPLOYEE_ID     =c.EMPLOYEE_ID\n" + 
                     "  ) y\n" + 
                     " ON x.sl_code = y.employee_id\n" + 
                   " WHERE bal    !=0 )kk \n" +
                     " where kk.employee_id="+slCode+"\n" + 
                     " GROUP BY Kk.Employee_Id, kk.ename, kk.sl_type_code, kk.sl_code  \n";
                     System.out.println("sql3:::"+sql3);
					  								ps3=con.prepareStatement(sql3);
                                                      rs3= ps3.executeQuery();
                                                    
                                                     while (rs3.next())
                                                     {
                                                             
                                                       xml=xml+"<payment_details>";
                                                       
                                                       System.out.println("during pay details-->"+xml);
                                                       
                                                               xml=xml+"<sl_type_code>"+rs3.getInt("sl_type_code")+"</sl_type_code>";
                                                               xml=xml+"<sl_code>"+rs3.getInt("sl_code")+"</sl_code>";
                                                               xml=xml+"<detail_amt>"+rs3.getString("ttlBalance")+"</detail_amt>";
                                                               xml=xml+"<ename>"+rs3.getString("ename")+"</ename>";
                                                       xml=xml+"</payment_details>";					   
                                                     }					 
                                                     
                                                    /** Fetch Journal Amount */
                                                
                                                     xml=xml+"<flag>Success</flag>";
                     
				 } // else end
				 
			}catch (SQLException e)
			{				
				xml=xml+"<flag>Failure</flag>";
				e.printStackTrace();				
			} 
			 xml=xml+"</response>";
           	 System.out.println(xml);		
             out.println(xml);
             
        }
        else if (strCommand.equalsIgnoreCase("finalPay"))
        {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml="";
            int count=0;
            xml="<response><command>finalPay</command>";
            int slcode1= Integer.parseInt(request.getParameter("cmbSL_Code"));
        //    int slcc= Integer.parseInt(request.getParameter("txtEmpID_mas"));
            System.out.println("slcode1"+slcode1);
                             String sql="select kk.employee_id,\n" + 
                             "   sum(kk.bal) as ttl\n" + 
                             " from (SELECT *\n" + 
                             "FROM\n" + 
                             "  (SELECT voucher_no,\n" + 
                             "    payment_date,\n" + 
                             "    sl_type_code,\n" + 
                             "    sl_code,\n" + 
                             "    (pay_amt - jour_amt - rec_amt) AS bal\n" + 
                             "  FROM\n" + 
                             "    (SELECT voucher_no,\n" + 
                             "      payment_date,\n" + 
                             "      sl_type_code,\n" + 
                             "      sl_code,\n" + 
                             "      pay_amt       AS pay_amt,\n" + 
                             "      SUM(jour_amt) AS jour_amt ,\n" + 
                             "      SUM(rec_amt)  AS rec_amt\n" + 
                             "    FROM\n" + 
                             "      (SELECT voucher_no,\n" + 
                             "        payment_date,\n" + 
                             "        sl_type_code,\n" + 
                             "        sl_code,\n" + 
                             "        pay_amt,\n" + 
                             "        jour_amt,\n" + 
                             "        0 AS rec_amt\n" + 
                             "      FROM\n" + 
                             "        (SELECT *\n" + 
                             "        FROM\n" + 
                             "          (SELECT m.voucher_no,\n" + 
                             "            TO_CHAR(m.PAYMENT_DATE,'DD/MM/YY')AS payment_date,\n" + 
                             "            t.SUB_LEDGER_TYPE_CODE            AS sl_type_code,\n" + 
                             "            t.SUB_LEDGER_CODE                 AS sl_code,\n" + 
                             "            t.AMOUNT                          AS pay_amt\n" + 
                             "          FROM fas_payment_master m,\n" + 
                             "            fas_payment_transaction t\n" + 
                             "          WHERE m.accounting_unit_id     = "+cmbAcc_UnitCode+"\n" + 
                       //      "          AND m.accounting_for_office_id = "+cmbOffice_code+"\n" + 
                             "          AND m.cashbook_year            = "+txtCB_Year+"\n" + 
                             "          AND m.cashbook_month           <= "+txtCB_Month+" \n"+
                             "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                             "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                             "          AND m.payment_status           ='L'\n" + 
                             "          AND payment_type               = '"+payment_type1+"'\n" + 
                             "          AND m.accounting_unit_id       = t.accounting_unit_id\n" + 
                             "          AND m.ACCOUNTING_FOR_OFFICE_ID =t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                             "          AND m.cashbook_month           =t.cashbook_month\n" + 
                             "          AND m.cashbook_year            =t.cashbook_year\n" + 
                             "          AND m.voucher_no               =t.voucher_no\n" + 
                             "          )p\n" + 
                             "        LEFT OUTER JOIN\n" + 
                             "          (SELECT t.SUB_LEDGER_TYPE_CODE AS j_sl_type_code,\n" + 
                             "            t.SUB_LEDGER_CODE            AS j_sl_code,\n" + 
                             "            t.cb_ref_no,\n" + 
                             "            TO_CHAR(t.cb_ref_date,'DD/MM/YY') AS cb_ref_date,\n" + 
                             "            amount                            AS jour_amt\n" + 
                             "          FROM fas_journal_master m,\n" + 
                             "            fas_journal_transaction t\n" + 
                             "          WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                             "          AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                             "          AND m.cashbook_month           =t.cashbook_month\n" + 
                             "          AND m.cashbook_year            =t.cashbook_year\n" + 
                             "          AND m.voucher_no               = t.voucher_no\n" + 
                             "          AND m.MODE_OF_CREATION         ='"+txtMode_of_creat+"'\n" + 
                             "          AND m.JOURNAL_STATUS           ='L'\n" + 
                             "          AND t.cr_dr_indicator          ='CR'\n" + 
                             "          AND m.accounting_unit_id       ="+cmbAcc_UnitCode+"\n" + 
                             "          )j\n" + 
                             "        ON p.voucher_no    = j.CB_REF_NO\n" + 
                             "        AND p.payment_date = j.CB_REF_DATE\n" + 
                             "        AND p.sl_type_code = j_sl_type_code\n" + 
                             "        AND p.sl_code      = j_sl_code\n" + 
                             "        )\n" + 
                             "      UNION ALL\n" + 
                             "      SELECT voucher_no,\n" + 
                             "        payment_date,\n" + 
                             "        sl_type_code,\n" + 
                             "        sl_code,\n" + 
                             "        pay_amt,\n" + 
                             "        0 AS jour_amt,\n" + 
                             "        rec_amt\n" + 
                             "      FROM\n" + 
                             "        (SELECT *\n" + 
                             "        FROM\n" + 
                             "          (SELECT m.voucher_no,\n" + 
                             "            TO_CHAR(m.PAYMENT_DATE,'DD/MM/YY')AS payment_date,\n" + 
                             "            t.SUB_LEDGER_TYPE_CODE            AS sl_type_code,\n" + 
                             "            t.SUB_LEDGER_CODE                 AS sl_code,\n" + 
                             "            t.AMOUNT                          AS pay_amt\n" + 
                             "          FROM fas_payment_master m,\n" + 
                             "            fas_payment_transaction t\n" + 
                             "          WHERE m.accounting_unit_id     = "+cmbAcc_UnitCode+"\n" + 
                        //     "          AND m.accounting_for_office_id = "+cmbOffice_code+"\n" + 
                             "          AND m.cashbook_year            = "+txtCB_Year+"\n" + 
                             "          AND m.cashbook_month           <= "+txtCB_Month+" \n"+
                             "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                             "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                             "          AND m.payment_status           ='L'\n" + 
                             "          AND payment_type               = '"+payment_type1+"'\n" + 
                             "          AND m.accounting_unit_id       = t.accounting_unit_id\n" + 
                             "          AND m.ACCOUNTING_FOR_OFFICE_ID =t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                             "          AND m.cashbook_month           =t.cashbook_month\n" + 
                             "          AND m.cashbook_year            =t.cashbook_year\n" + 
                             "          AND m.voucher_no               =t.voucher_no\n" + 
                             "          )p\n" + 
                             "        LEFT OUTER JOIN\n" + 
                             "          (SELECT t.SUB_LEDGER_TYPE_CODE AS r_sl_type_code,\n" + 
                             "            t.SUB_LEDGER_CODE            AS r_sl_code,\n" + 
                             "            m.RECEIVABLE_VOUCHER_NO,\n" + 
                             "            TO_CHAR(m.RECEIVABLE_VOUCHER_DATE,'DD/MM/YY') AS RECEIVABLE_VOUCHER_DATE,\n" + 
                             "            t.AMOUNT                                      AS rec_amt\n" + 
                             "          FROM fas_receipt_master m,\n" + 
                             "            fas_receipt_transaction t\n" + 
                             "          WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                             "          AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                             "          AND m.cashbook_month           =t.cashbook_month\n" + 
                             "          AND m.cashbook_year            =t.cashbook_year\n" + 
                             "          AND m.receipt_no               = t.receipt_no\n" + 
                             "          AND m.MODE_OF_CREATION         ='"+txtMode_of_creat+"'\n" + 
                             "          AND m.accounting_unit_id       ="+cmbAcc_UnitCode+"\n" + 
                             "          AND m.RECEIPT_STATUS           ='L'\n" + 
                             "          )r\n" + 
                             "        ON p.voucher_no    = r.RECEIVABLE_VOUCHER_NO\n" + 
                             "        AND p.payment_date = r.RECEIVABLE_VOUCHER_DATE\n" + 
                             "        AND p.sl_type_code = r.r_sl_type_code\n" + 
                             "        AND p.sl_code      = r.r_sl_code\n" + 
                             "        )\n" + 
                             "      )\n" + 
                             "    GROUP BY voucher_no,\n" + 
                             "      payment_date,\n" + 
                             "      sl_type_code,\n" + 
                             "      sl_code ,\n" + 
                             "      pay_amt\n" + 
                             "    ORDER BY payment_date,\n" + 
                             "      voucher_no,\n" + 
                             "      sl_type_code,\n" + 
                             "      sl_code\n" + 
                             "    )\n" + 
                             "  ) x\n" + 
                             "LEFT OUTER JOIN\n" + 
                             "  (SELECT e.EMPLOYEE_ID,\n" + 
                             "    e.EMPLOYEE_NAME\n" + 
                             "    ||'.'\n" + 
                             "    ||e.EMPLOYEE_INITIAL\n" + 
                             "    ||'-'\n" + 
                             "    || d.DESIGNATION AS ENAME\n" + 
                             "  FROM HRM_MST_EMPLOYEES e,\n" + 
                             "    HRM_EMP_CURRENT_POSTING c,\n" + 
                             "    HRM_MST_DESIGNATIONS d\n" + 
                             "  WHERE c.DESIGNATION_ID=d.DESIGNATION_ID\n" + 
                             "  AND e.EMPLOYEE_ID     =c.EMPLOYEE_ID\n" + 
                             "  ) y\n" + 
                             "ON x.sl_code = y.employee_id\n" + 
                             "WHERE bal    > 0 )kk \n" + 
                             "where kk.employee_id="+slcode1+"\n" + 
                             "group by kk.employee_id \n";
                             
             try {
                   System.out.println("sql:::::"+sql);        
                ps2=con.prepareStatement(sql);
             
                            rs2= ps2.executeQuery();
                            while (rs2.next())
                            {
                              xml=xml+"<ttl>"+rs2.getString("ttl")+"</ttl>";
                              count++;
                            }
                            if( count ==0)
                            {
                                   xml=xml+"<flag>NoRecords</flag>"; 
                            }else
                            {
                                    xml=xml+"<flag>Success</flag>";
                            }
                            
                            
                        } catch (SQLException e)
                        {
                           xml=xml+"<flag>Failure</flag>";
                           e.printStackTrace();                            
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

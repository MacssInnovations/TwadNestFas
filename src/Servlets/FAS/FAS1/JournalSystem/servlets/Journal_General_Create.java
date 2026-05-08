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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Journal_General_Create extends HttpServlet 
{

    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    	 response.setContentType(CONTENT_TYPE);
    	 PrintWriter out = response.getWriter();
         String strCommand="";
         Connection con=null;
         ResultSet rs=null;
         CallableStatement cs=null;
         PreparedStatement ps=null,ps3=null;
         String xml="";
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
//                              ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
             catch(Exception e)
                 {
                    System.out.println("Exception in opening connection :"+e);
                    //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

                 }
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command :..."+strCommand);
           
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
            Calendar c,c1;
            int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtJournalVou_No=0;
            //int txtCash_Acc_code=0;
            int Total_TRN_Rec=0,num=0;
            //double txtAmount=0;
            String  txtCheque_NO="",txtCB_REF_TYPE="";
           
            Date txtCrea_date=null,txtCheque_date=null,cb_date=null;
            String txtRemarks="";
          
            int cmbMas_SL_type=0,cmbMas_SL_Code=0;
            String txtMode_of_creat="M",txtCreat_By_Module="GJV";
            double dep_rate=0;                           // changes here
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            //Timestamp ts=new Timestamp(l);

            //For Banking Purpose
           
            
                        
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
           
            
           
          
            String[] sd=request.getParameter("txtCrea_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            System.out.println("txtCrea_date "+txtCrea_date);
            
            System.out.println("length:::"+sd[2].length());
            if(sd[2].length()>3){
            
            System.out.println("b4 getting month and year");
            try{txtCash_year=Integer.parseInt(sd[2]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_year "+txtCash_year);
            
            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                        
                        
           System.out.println("txtCash_year::"+txtCash_year);
            
            /*try{txtCash_year=Integer.parseInt(request.getParameter("txtCash_year"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_year "+txtCash_year);
            
            try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCash_Month_hid"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);*/
            
            try{txtJournalVou_No=Integer.parseInt(request.getParameter("txtJournalVou_No"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtJournalVou_No "+txtJournalVou_No);
            
          /*  try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+txtAmount);*/
            
            txtCheque_NO=request.getParameter("txtCheque_NO");
            System.out.println("txtCheque_NO "+txtCheque_NO);
            
            if(!request.getParameter("txtCheque_date").equalsIgnoreCase(""))
            {
            sd=request.getParameter("txtCheque_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtCheque_date=new Date(d.getTime());
            }
            System.out.println("txtCheque_date "+txtCheque_date);
            // changes here
            try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
            catch(Exception e){System.out.println("exception"+e );}
            
            
            try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
            catch(Exception e){System.out.println("exception"+e );}
            
          
            System.out.println("cmbMas_SL and office "+cmbMas_SL_type+" "+cmbMas_SL_Code);//+" "+cmbMas_offid);
            
          
           
         
            txtRemarks=request.getParameter("txtRemarks");
            System.out.println("txtRemarks "+txtRemarks);
          
             try 
                 {   
//                     con.clearWarnings();
//                     con.setAutoCommit(false);
//                     System.out.println("inside proc");
//                     String No_TRN_Rec[]=request.getParameterValues("H_code");
//                   //  int supplement_no=0;
//                     //int NTR=No_TRN_Rec.length;
//                      //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
//                     Total_TRN_Rec=No_TRN_Rec.length;//Integer.parseInt(No_TRN_Rec);
//                     System.out.println(Total_TRN_Rec+" Total_TRN_Rec");
//            	        cs=con.prepareCall("{call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
//                         cs.setInt(1,cmbAcc_UnitCode);
//                         cs.setInt(2,cmbOffice_code);
//                         cs.setInt(3,txtCash_year);
//                         cs.setInt(4,txtCash_Month_hid);
//                         System.out.println("txtJournalVou_No:::"+txtJournalVou_No);
//                         cs.setInt(5,txtJournalVou_No);
//                         cs.setDate(6,txtCrea_date);
//                                         // cs.setString(7,txtReceipt_type);
//                                                                                    //  cs.setInt(8,txtCash_Acc_code);
//                         cs.setInt(7,cmbMas_SL_type);
//                         cs.setInt(8,cmbMas_SL_Code);
//                         cs.setDouble(9,dep_rate);
//                         cs.setString(10,txtCheque_NO);
//                         cs.setDate(11,txtCheque_date);
//                         cs.setString(12,txtCB_REF_TYPE);
//                         //cs.setInt(13,txtCB_REF_NO);
//                         //cs.setDate(14,txtCB_REF_DATE);
//                                                              // cs.setDouble(19,txtAmount);
//                          cs.setInt(13,Total_TRN_Rec);
//                          cs.setString(14,txtRemarks);
//                          cs.setString(15,txtMode_of_creat);
//                          cs.setString(16,txtCreat_By_Module);
//                          cs.setString(17,"insert");                     
//                          cs.registerOutParameter(5,java.sql.Types.NUMERIC);
//                          cs.registerOutParameter(18,java.sql.Types.NUMERIC);  
//                          cs.setString(19,update_user);
//                          cs.setTimestamp(20,ts);
//                        //  cs.setInt(21,supplement_no);
//                         System.out.println("b4 exe ");
//                     cs.execute();
//                     txtJournalVou_No=cs.getInt(5);
//                     int errcode=cs.getInt(18);
            	 con.clearWarnings();
                 con.setAutoCommit(false);
                 System.out.println("inside proc");
                 String No_TRN_Rec[] = request.getParameterValues("H_code");
                 SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");  
     			SimpleDateFormat objTs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
                 //java.sql.Date txtCrea_date = java.sql.Date.valueOf( "txtCrea_date" );
                 //long epoch = objTs.parse("ts").getTime();    
                
     			   c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
     					                         Integer.parseInt(sd[0]));
     					            d = c.getTime();
     					            txtCrea_date = new Date(d.getTime());
     			
     			Timestamp ts=new Timestamp(l); 
     			
     			//long	epoch = objTs.parse("ts").getTime();
     			 java.sql.Date txtCrea_date_NEW =new  java.sql.Date(d.getTime());
     			
                 //int NTR=No_TRN_Rec.length;
                 //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                 Total_TRN_Rec =
                         No_TRN_Rec.length; //Integer.parseInt(No_TRN_Rec);
                 System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
//                 cs =con.prepareCall("call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//                 cs.setObject(1,cmbAcc_UnitCode , java.sql.Types.NUMERIC);
//                 cs.setObject(2,cmbOffice_code , java.sql.Types.NUMERIC);
//                 cs.setObject(3,txtCash_year , java.sql.Types.NUMERIC);
//                 cs.setObject(4,txtCash_Month_hid , java.sql.Types.NUMERIC);
//                 cs.setObject(5, txtJournalVou_No , java.sql.Types.NUMERIC);
//                 System.out.println("txtJournalVou_No:::"+txtJournalVou_No);
//                
//                // cs.setNull(6, java.sql.Types.DATE);
//                 cs.setDate(6, txtCrea_date);
//                 
//                 cs.setObject(7,cmbMas_SL_type , java.sql.Types.NUMERIC);
//                 cs.setObject(8,cmbMas_SL_Code , java.sql.Types.NUMERIC);
//                 cs.setObject(9,dep_rate , java.sql.Types.NUMERIC);
//                 
//                 cs.setString(10,txtCheque_NO);
//                 cs.setObject(11,txtCheque_date , java.sql.Types.DATE);
//                 //cs.setDate(11,txtCheque_date);
//                 cs.setString(12,txtCB_REF_TYPE);
//                 cs.setObject(13,Total_TRN_Rec, java.sql.Types.NUMERIC);
//                 cs.setString(14,txtRemarks);
//                 cs.setString(15,txtMode_of_creat);
//                 cs.setString(16,txtCreat_By_Module);
//                 cs.setString(17,"insert");    
//                 cs.setObject(18,0 ,java.sql.Types.NUMERIC);
//                
//                 cs.registerOutParameter(5,java.sql.Types.NUMERIC);
//                 cs.registerOutParameter(18,java.sql.Types.NUMERIC);  
//                 cs.setString(19,update_user);
//                
//                 System.out.println("b4 exe ");
//                 cs.execute();
                 cs=
//                		 con.prepareCall("call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//                 cs.setInt(1, cmbAcc_UnitCode);
//                 cs.setInt(2, cmbOffice_code);
//                 cs.setInt(3, txtCash_year);
//                 cs.setInt(4, txtCash_Month_hid);
//                 cs.setInt(5, txtJournalVou_No);
//                 cs.setDate(6, txtCrea_date);
//                 // cs.setString(7,txtReceipt_type);
//                 //  cs.setInt(8,txtCash_Acc_code);
//                 cs.setInt(7, cmbMas_SL_type);
//                 cs.setInt(8, cmbMas_SL_Code);
//                 cs.setDouble(9, dep_rate);
//                 cs.setString(10, txtCheque_NO);
//                 cs.setDate(11, txtCheque_date);
//                 cs.setString(12, txtCB_REF_TYPE);
//                 // cs.setInt(13,txtCB_REF_NO);
//                 // cs.setDate(14,txtCB_REF_DATE);
//                 // cs.setDouble(19,txtAmount);
//                 cs.setInt(13, Total_TRN_Rec);
//                 cs.setString(14, txtRemarks);
//                 cs.setString(15, txtMode_of_creat);
//                 cs.setString(16, txtCreat_By_Module);
//                 cs.setString(17, "update");
//                 cs.registerOutParameter(5, java.sql.Types.NUMERIC);
//                 cs.registerOutParameter(18, java.sql.Types.NUMERIC);
//                 cs.setNull(5, java.sql.Types.NUMERIC);
//                 cs.setNull(18, java.sql.Types.NUMERIC);
//                 cs.setString(19, update_user);
//                 cs.setTimestamp(20, ts);
//                 System.out.println("b4 exe ");
//                 cs.execute();
//                // txtJournalVou_No =cs.getObject(5).toString();
//
//                 int errcode =0;
							con.prepareCall(
									"call FAS_JOURNAL_MASTER_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?,?,?::int,?,?)");
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
                 cs.setString(17, "insert");
                 cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                 cs.registerOutParameter(18, java.sql.Types.INTEGER);
                 cs.setNull(5, java.sql.Types.NUMERIC);
                 cs.setNull(18, java.sql.Types.NUMERIC);
                 cs.setString(19, update_user);
                 cs.setTimestamp(20, ts);
                 System.out.println("b4 exe ");
                 cs.execute();
                 txtJournalVou_No = cs.getBigDecimal(5).intValue();
                 int errcode = cs.getInt(18);
                     System.out.println("SQLCODE:::"+errcode);
                     if(errcode!=0)
                     {         
                       System.out.println("redirect");
                       sendMessage(response,"The General Voucher Number Creation Failed ","ok");
                       xml=xml+"<flag>failure</flag>";                          
                     }
                     else
                     {  
                         String Grid_H_code[]=request.getParameterValues("H_code");
                         String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
                         String Grid_SL_type[]=request.getParameterValues("SL_type");
                         String Grid_SL_code[]=request.getParameterValues("SL_code");
                        // String Grid_rec_from[]=request.getParameterValues("rec_from");
                         String Grid_Bill_No[]=request.getParameterValues("Bill_NO");
                         String Grid_Bill_date[]=request.getParameterValues("Bill_date");
                         String Grid_Bill_type[]=request.getParameterValues("Bill_type");
                         
                         String Grid_Agree_No[]=request.getParameterValues("Agree_No");
                         String Grid_Agree_date[]=request.getParameterValues("Agree_date");
                         
                         String Grid_sl_amt[]=request.getParameterValues("sl_amt");
                         String Grid_particular[]=request.getParameterValues("particular");
                         
                         String Grid_adjYear[]=request.getParameterValues("sl_adjyear");
                         String Grid_adjMonth[]=request.getParameterValues("sl_adjmonth");
                         
                         
                         String sql="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                         "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                         "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                         "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
                         "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH ) "+
                         "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                         
                         
                         
                         int SL_NO=1,txtAcc_HeadCode=0,cmbSL_type=0,txtCB_REF_NO=0,adjYear=0,adjMonth=0,comCbNo=0,last=0;
                         Date txtBill_Date=null,txtAgree_Date=null,txtCheque_DD_date=null,txtCB_REF_DATE=null,comCbDate=null;
                         double txtsub_Amount=0;                                  
                         String rad_sub_CR_DR="",txtBill_no="",txtBill_Type="",txtAgree_No="",txtParticular="";
                         String txtCheque_DD="",txtCheque_DD_NO="";  
                         long cmbSL_Code=0;

                               ps=con.prepareStatement(sql);
                               for(int k=0;k<Grid_H_code.length;k++) 
                               {
                                   try{txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   rad_sub_CR_DR=Grid_CR_DR_type[k];
                                   
                                   try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   try{cmbSL_Code=Long.parseLong(Grid_SL_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   System.out.println("Grid_H_code[k] "+Grid_H_code[k]);
                                   System.out.println("Grid_CR_DR_type[k] "+Grid_CR_DR_type[k]);
                                   System.out.println("Grid_SL_type[k]"+Grid_SL_type[k]+"u");
                                   System.out.println("Grid_SL_code[k]"+Grid_SL_code[k]+"from here"+cmbSL_Code);
                                   //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                                   //txtsub_Recei_from=Grid_rec_from[k];
                                   
                                   
                                    txtBill_no=Grid_Bill_No[k];
                                    
                                    txtBill_Type=Grid_Bill_type[k];
                                    
                                    if(!Grid_Bill_date[k].equalsIgnoreCase(""))
                                    {
                                    sd=Grid_Bill_date[k].split("/");
                                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                                    d=c.getTime();
                                    txtBill_Date=new Date(d.getTime());
                                    }
                                    
                                    txtAgree_No=Grid_Agree_No[k];
                                    if(!Grid_Agree_date[k].equalsIgnoreCase(""))
                                    {
                                    sd=Grid_Agree_date[k].split("/");
                                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                                    d=c.getTime();
                                    txtAgree_Date=new Date(d.getTime());
                                    }
                                    
                                    
                                   System.out.println("amount");
                                   txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);
                                   txtParticular=Grid_particular[k];
                                   System.out.println("amount");
                                   System.out.println("Grid_sl_amt[k] "+Grid_sl_amt[k]);
                                  // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                                   System.out.println("Grid_particular[k] "+Grid_particular[k]);
                                   
                                  
                                   try{
                                   adjYear=Integer.parseInt(Grid_adjYear[k]);
                                   }catch(Exception e){System.out.println("Adjyear"+e);}
                                   
                                   try{
                                       adjMonth=Integer.parseInt(Grid_adjMonth[k]);
                                       }catch(Exception e){System.out.println("Adjmonth"+e);}
                                       
                                   if(cmbMas_SL_type==61){
                                   System.out.println("comes in 61>>>>>>>>>>>>>>>>>> journal type");
                                   try{num=Integer.parseInt(request.getParameter("num"));
                                   }
                                   catch(NumberFormatException e){System.out.println("exception"+e );}
                               //    System.out.println("num>>>>>>> "+num);
                                   
                                   if(request.getParameter("date1").equalsIgnoreCase("null")) {
                                       cb_date=null;
                                   }
                                   else{
                                   String[] sdd=request.getParameter("date1").split("/");
                                   c1=new GregorianCalendar(Integer.parseInt(sdd[2]),Integer.parseInt(sdd[1])-1,Integer.parseInt(sdd[0]));
                                   java.util.Date d1=c1.getTime();
                                   cb_date=new Date(d1.getTime());
                                //   System.out.println("cb_date "+cb_date);  
                                   }
                                       if(rad_sub_CR_DR.equals("CR")) {
                                     //  System.out.println("comes in credit");
                                           comCbNo=num;
                                           comCbDate=cb_date;
                                       }
                                       else {
                                       //System.out.println("comes in cbrefno");
                                       comCbNo=txtCB_REF_NO;
                                       comCbDate=txtCB_REF_DATE;
                                       }
                                   }
                                   else {
                                       System.out.println("else comes >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
                                       comCbNo=txtCB_REF_NO;
                                       comCbDate=txtCB_REF_DATE;
                                   }
                                       
                                   ps.setInt(1,cmbAcc_UnitCode);
                                   ps.setInt(2,cmbOffice_code);
                                   ps.setInt(3,txtCash_year);
                                   ps.setInt(4,txtCash_Month_hid);
                                   ps.setInt(5,txtJournalVou_No);
                                   ps.setInt(6,SL_NO);
                                   ps.setInt(7,txtAcc_HeadCode);
                                   ps.setString(8,rad_sub_CR_DR);
                                   ps.setInt(9,cmbSL_type);
                                   ps.setLong(10,cmbSL_Code);
                                   ps.setString(11,txtBill_no);
                                   ps.setString(12,txtBill_Type);
                                   ps.setString(13,txtAgree_No);
                                   ps.setDate(14,txtAgree_Date);
                                   ps.setDate(15,txtBill_Date);
                                   
                                   ps.setString(16,txtCheque_DD);
                                   ps.setString(17,txtCheque_DD_NO); 
                                   ps.setDate(18,txtCheque_DD_date);
                                  
                                   ps.setDouble(19,txtsub_Amount);
                                   ps.setString(20,txtParticular);
                                   ps.setInt(21,comCbNo);
                                   //System.out.println("comCbNo222222>>>>>>"+comCbNo);
                                   ps.setDate(22,comCbDate);
                                   //System.out.println("comCbDate222222>>>>>>"+comCbDate);
                                   ps.setString(23,update_user);
                                   ps.setTimestamp(24,ts);
                                   ps.setInt(25,adjYear);
                                   ps.setInt(26,adjMonth);
                                   SL_NO++;
                                 last=  ps.executeUpdate(); 
                                   
                                   txtAcc_HeadCode=0;
                                   rad_sub_CR_DR="";
                                   cmbSL_type=0;
                                   cmbSL_Code=0;
                                   txtCheque_DD="";
                                   txtCheque_DD_NO="";
                                   txtCheque_DD_date=null;
                                   txtAgree_No="";
                                   txtAgree_Date=null;
                                  txtsub_Amount=0;
                                   txtParticular="";
                                   adjYear=0;
                                   adjMonth=0;
                                   
                               }
                                      if(last>0){
                                     if(cmbMas_SL_type==61)
                                     {
                                  //   System.out.println("comes in 61>>>>>>>>>>>>>>>>>>update journal type");
                                     try{num=Integer.parseInt(request.getParameter("num"));}
                                     catch(NumberFormatException e){System.out.println("exception"+e );}
                                     
                                     String[] sdd=request.getParameter("date1").split("/");
                                     c1=new GregorianCalendar(Integer.parseInt(sdd[2]),Integer.parseInt(sdd[1])-1,Integer.parseInt(sdd[0]));
                                     java.util.Date d1=c1.getTime();
                                     cb_date=new Date(d1.getTime());
                                       //  if(rad_sub_CR_DR.equals("CR")) {
                                         try{
                                                       
                                                  String sql2="update FAS_JOURNAL_MASTER set CB_REF_TYPE='A' where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and VOUCHER_DATE=? and VOUCHER_NO="+num;
                                                  System.out.println("sql2 for up::::"+sql2);
                                                  ps3=con.prepareStatement(sql2);
                                                  ps3.setDate(1,cb_date);
                                                   ps3.executeUpdate();
                                               }
                                               catch(Exception e1)
                                               {
                                               System.out.println("excep in update:"+e1);      
                                               }
                                     //    }
                                        
                                     }
                     }
                               ps.close();
                         System.out.println("b4 commit");
                         con.commit();
                         sendMessage(response,"The General Voucher Number '"+txtJournalVou_No+"' has been Created Successfully ","ok");
                     }
                    
                 }
                 
                 catch(Exception e) 
                 {
                     try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                     sendMessage(response,"The General Voucher Number Creation Failed ","ok");
                     System.out.println("Exception occur due to "+e);
                 }
                 finally
                 {
                     System.out.println("done");
                     try{con.setAutoCommit(true);  }catch(SQLException sqle){}
                 }
             }
            else
            {
            	//cashbook year is 201 so error
            	System.out.println("error due to year 201:");
            	 try{con.rollback();}
            	 catch(Exception sqle1)
            	 {System.out.println("exception in rollback "+sqle1);}
                 sendMessage(response,"Please Check Year in Date ","ok");
                 
            }
                 
        } 
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
   	 Connection con=null;
        ResultSet rs=null;
        //CallableStatement cs=null,cs1=null;
        PreparedStatement ps=null;
       PreparedStatement ps2=null;
       ResultSet rs2=null;
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
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
             catch(Exception e)
                 {
                    System.out.println("Exception in opening connection :"+e);
                    //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

                 }
        
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        String strCommand="";
        
        try 
        {
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        
        
        if(strCommand.equalsIgnoreCase("get")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             int cmbOffice_code=0;
             String xml="";
              xml="<response><command>get</command>";
              int count=0;
              try{
           	   int benTypeId=Integer.parseInt(request.getParameter("bentypeid"));
           	   cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
           	   
           	   System.out.println("benTypeId"+benTypeId) ;
           	System.out.println("cmbOffice_code"+cmbOffice_code) ;
              ps=con.prepareStatement("SELECT BENEFICIARY_SNO,BENEFICIARY_NAME FROM PMS_DCB_MST_BENEFICIARY	WHERE BENEFICIARY_TYPE_ID=?	AND OFFICE_ID=? and status='L' order by BENEFICIARY_NAME");     
              ps.setInt(1, benTypeId);
              ps.setInt(2, cmbOffice_code);
             rs=ps.executeQuery(); 
             while(rs.next())
             {
             xml=xml+"<beneficiarysno>"+rs.getInt("BENEFICIARY_SNO")+"</beneficiarysno>";
             xml=xml+"<beneficiaryname>"+rs.getString("BENEFICIARY_NAME")+"</beneficiaryname>";
             count++;
             }
             if(count>0){
             xml=xml+"<flag>success</flag>";
             }else
            	 xml=xml+"<flag>failure</flag>";	
             
              }catch(Exception e){
           	   System.out.println(e);
           	xml=xml+"<flag>failure</flag>";	
              }
              xml=xml+"</response>";
              System.out.println(xml);
              out.println(xml);
        }
        if(strCommand.equalsIgnoreCase("checkLiability")) {
      
            String hCode="";
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int cmbOffice_code=0;
            String xml="";
             xml="<response><command>checkLiability</command>";
             int count=0;
             try{
                  int txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
             ps=con.prepareStatement("select MAJOR_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE= ?");     
             ps.setInt(1, txtAcc_HeadCode);
        
            rs=ps.executeQuery(); 
            while(rs.next())
            {
              hCode=rs.getString("MAJOR_HEAD_CODE");
              System.out.println("hCode"+hCode);
              if(hCode.equals("L")) {
                  xml=xml+"<flag>success</flag>";
              }
              else {
              
                  xml=xml+"<flag>failure</flag>";
              }
            
           
            }
                
            
             }catch(Exception e){
                 e.printStackTrace();
               xml=xml+"<flag>failure</flag>"; 
             }
             xml=xml+"</response>";
             System.out.println(xml);
             out.println(xml);
        }
        // check Liability for Journal_Bill
      /* if(strCommand.equalsIgnoreCase("accCheck")) {
       System.out.println("serrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
       int num=0;
           String hCode="",sql="";
           String CONTENT_TYPE = "text/xml; charset=windows-1252";
           response.setContentType(CONTENT_TYPE);
           int cmbOffice_code=0;
           String xml="";
            xml="<response><command>accCheck</command>";
            int count=0;
            try{
                 hCode=request.getParameter("hCodes");
                 System.out.println("hCodeeeeeeee"+hCode);
                 sql="select MAJOR_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE in("+hCode+") and MAJOR_HEAD_CODE='L'";
           System.out.println("sql>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
            ps=con.prepareStatement(sql);     
           // ps.setString(1, hCode);
       
           rs=ps.executeQuery(); 
           System.out.println("rs"+rs);
           while(rs.next())
           {
           System.out.println("inside ");
                num++;
              
           }
           if(num>0) {
               xml=xml+"<flag>success</flag>"; 
           }
           else {
               xml=xml+"<flag>failure</flag>"; 
           }
           
            }
            catch(Exception e){
                 System.out.println(e);
              xml=xml+"<flag>failure</flag>"; 
            }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
       }
       */
        
        if(strCommand.equalsIgnoreCase("benifi")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             int cmbOffice_code=0;
             String xml="";
              xml="<response><command>benifi</command>";
              int count=0;
              try{
           	   int benSNO=Integer.parseInt(request.getParameter("benficierysno"));
           	   cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
           	   
           	   System.out.println("benSNO"+benSNO) ;
           	System.out.println("cmbOffice_code"+cmbOffice_code) ;
              ps=con.prepareStatement("SELECT BENEFICIARY_TYPE_ID FROM PMS_DCB_MST_BENEFICIARY	WHERE BENEFICIARY_SNO=?	AND OFFICE_ID=? and status='L' order by BLOCK_SNO");     
              ps.setInt(1, benSNO);
              ps.setInt(2, cmbOffice_code);
             rs=ps.executeQuery(); 
             while(rs.next())
             {
             xml=xml+"<bentypeid>"+rs.getInt("BENEFICIARY_TYPE_ID")+"</bentypeid>";
            
             count++;
             }
             if(count>0){
             xml=xml+"<flag>success</flag>";
             }else
            	 xml=xml+"<flag>failure</flag>";	
             
              }catch(Exception e){
           	   System.out.println(e);
           	xml=xml+"<flag>failure</flag>";	
              }
              xml=xml+"</response>";
              System.out.println(xml);
              out.println(xml);
        }if(strCommand.equalsIgnoreCase("HeadCodeValidation1"))
        {
        	String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);            
            String xml="";
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~ RK   ~~~~~~~~~~~~~~~~~~~~~~~~~");
        	xml="<response><command>HeadCodeValidation1</command>";
        	int txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
        	try {
        		int sl_cnt=0;
        		
        		ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'");//STATUS field added in 05-07-19.
        		ps.setInt(1, txtAcc_HeadCode);
        		rs=ps.executeQuery();
        		while(rs.next())
        		{
        			sl_cnt++;
        			xml=xml+"<subLedgerType>" + rs.getInt("SUB_LEDGER_TYPE_CODE") + "</subLedgerType>";     
        		}
        		if(sl_cnt==0)
                {
                	System.out.println("Status is 'N'");
                    xml = xml + "<flag>failure</flag>";
                }
        		else
        		{
        		xml=xml+"<flag>success</flag>";     
        		}
			} catch (Exception e) {
				e.printStackTrace();
				xml=xml+"<flag>failure</flag>";     
			}
			xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
        }     
        
       if(strCommand.equalsIgnoreCase("fixAccHdCode"))
               {
                       String CONTENT_TYPE = "text/xml; charset=windows-1252";
                   response.setContentType(CONTENT_TYPE);            
                   String xml="";
                       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~ RKsbg   ~~~~~~~~~~~~~~~~~~~~~~~~~");
                       xml="<response><command>fixAccHdCode</command>";
                       int txtAcc_UnitCode=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
                       try {
                               ps=con.prepareStatement("select AC_HEAD_CODE from FAS_OFFICE_BANK_AC_CURRENT where accounting_unit_id=? and module_id='MF005' and ac_operational_mode_id='OPR'");
                               ps.setInt(1, txtAcc_UnitCode);
                               rs=ps.executeQuery();
                               while(rs.next())
                               {
                                       xml=xml+"<acc_Hd_Code>" + rs.getInt("AC_HEAD_CODE") + "</acc_Hd_Code>";     
                               }
                               xml=xml+"<flag>success</flag>";     
                               } catch (Exception e) {
                                       e.printStackTrace();
                                       xml=xml+"<flag>failure</flag>";     
                               }
                               xml=xml+"</response>";
                   System.out.println(xml);
                   out.println(xml);
               }     
       if(strCommand.equalsIgnoreCase("fixAccHdCode_col"))
       {
               String CONTENT_TYPE = "text/xml; charset=windows-1252";
           response.setContentType(CONTENT_TYPE);            
           String xml="";
               System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~ RKsbg   ~~~~~~~~~~~~~~~~~~~~~~~~~");
               xml="<response><command>fixAccHdCode_col</command>";
               int txtAcc_UnitCode=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
               try {
                       ps=con.prepareStatement("select AC_HEAD_CODE from FAS_OFFICE_BANK_AC_CURRENT where accounting_unit_id=? and module_id='MF004' and ac_operational_mode_id='COL'");
                       ps.setInt(1, txtAcc_UnitCode);
                       rs=ps.executeQuery();
                       while(rs.next())
                       {
                               xml=xml+"<acc_Hd_Code>" + rs.getInt("AC_HEAD_CODE") + "</acc_Hd_Code>";     
                       }
                       xml=xml+"<flag>success</flag>";     
                       } catch (Exception e) {
                               e.printStackTrace();
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
        {
        }
    }
}

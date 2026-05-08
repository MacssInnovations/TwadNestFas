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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;

/**
 * Servlet implementation class Rectificational_Journal_Cancel_Deft
 */
public class Rectificational_Journal_Cancel_Deft extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rectificational_Journal_Cancel_Deft() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con=null;
        ResultSet rs=null,rs2=null,rs3=null,rs4=null;
        //CallableStatement cs=null,cs1=null;
        PreparedStatement ps=null,ps2=null,ps3=null,ps4=null;
        
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
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
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
            System.out.println("assign.. command..."+strCommand);
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        int cmbAcc_UnitCode=0,cmbOffice_code=0;
           Date txtCrea_date=null;
           try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
           catch(NumberFormatException e){System.out.println("exception"+e );}
           System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
           
           try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
           catch(NumberFormatException e){System.out.println("exception"+e );}
           System.out.println("cmbOffice_code "+cmbOffice_code);
        
        if(strCommand.equalsIgnoreCase("load_Voucher_No")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             Calendar c;
             String xml="";
              xml="<response><command>load_Voucher_No</command>";
             
             String[] sd=request.getParameter("txtCrea_date").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
             java.util.Date d=c.getTime();
             txtCrea_date=new Date(d.getTime());
             System.out.println("txtCrea_date "+txtCrea_date);
             
                    try {
                            //ps=con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='GJV' and JOURNAL_STATUS!='C'");
                             ps=con.prepareStatement("select i.VOUCHER_NO from FAS_JOURNAL_MASTER i,FAS_CROSS_REFERENCE c where " +
                             " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.VOUCHER_DATE=? and i.JOURNAL_STATUS!='C'  and CREATED_BY_MODULE='GJV' " +
                             " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                             " and i.VOUCHER_DATE=c.ORIGINAL_DATE and i.VOUCHER_NO=c.VOUCHER_NO " +
                             " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE='RJV' and JOURNAL_TYPE_CODE=75");
                            ps.setInt(1,cmbAcc_UnitCode);
                            ps.setInt(2,cmbOffice_code);
                            ps.setDate(3,txtCrea_date);
                            rs=ps.executeQuery();
                            
                            int count=0;
                            while(rs.next())
                            {
                            
                            xml=xml+"<Rec_No>"+rs.getInt("VOUCHER_NO")+
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
         
         else if(strCommand.equalsIgnoreCase("load_Voucher_Details")) 
           {
                String CONTENT_TYPE = "text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                Calendar c;
                String xml="";
                 xml="<response><command>load_Voucher_Details</command>";
                 int txtJournalVou_No=0;
                // Date txtCrea_date;
                 
                try{txtJournalVou_No=Integer.parseInt(request.getParameter("txtJournalVou_No"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("txtJournalVou_No "+txtJournalVou_No);
                
                String[] sd=request.getParameter("txtCrea_date").split("/");
                c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                java.util.Date d=c.getTime();
                txtCrea_date=new Date(d.getTime());
                System.out.println("txtCrea_date "+txtCrea_date);
                
                       try {
                               ps=con.prepareStatement("select CASHBOOK_YEAR,CASHBOOK_MONTH,CHEQUE_NO,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE ,to_char(CHEQUE_DATE,'DD/MM/YYYY')as CHEQ_date,REMARKS,TOTAL_TRN_RECORDS from  FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=?");
                               ps.setInt(1,cmbAcc_UnitCode);
                               ps.setInt(2,cmbOffice_code);
                               ps.setDate(3,txtCrea_date);
                               ps.setInt(4,txtJournalVou_No);
                               rs=ps.executeQuery();
                               if(rs.next())
                               {
                               xml=xml+"<flag>success</flag>";
                               xml=xml+"<cheq_No>"+rs.getString("CHEQUE_NO")+
                               "</cheq_No><cheq_Date>"+rs.getString("CHEQ_date")+
                               "</cheq_Date><No_TRN_Rec>"+rs.getInt("TOTAL_TRN_RECORDS")+
                               "</No_TRN_Rec><Remak>"+rs.getString("REMARKS")+
                               "</Remak><Mas_SL_type>"+rs.getInt("JOURNAL_TYPE_CODE")+
                               "</Mas_SL_type><Mas_SL_code>"+rs.getInt("SUB_LEDGER_CODE")+
                               "</Mas_SL_code>";
                               }
                                
                               //System.out.println("in b/w here"+rs.getInt("CASHBOOK_MONTH"));
                               System.out.println("in b/w here");
                               
                               ps2=con.prepareStatement("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ," +
                               "SUB_LEDGER_CODE,BILL_NO, BILL_TYPE, AGREEMENT_NO, to_char(AGREEMENT_DATE,'DD/MM/YYYY') as agree_date," +
                               "to_char(BILL_DATE,'DD/MM/YYYY') as b_date  " +
                               ",trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS,ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH,ADJ_DOC_TYPE,ADJ_DOC_NO from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and " +
                               "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
                               ps2.setInt(1,cmbAcc_UnitCode);
                               ps2.setInt(2,cmbOffice_code);
                               ps2.setString(3,rs.getString("CASHBOOK_YEAR"));
                               ps2.setInt(4,rs.getInt("CASHBOOK_MONTH"));
                               ps2.setInt(5,txtJournalVou_No);
                               rs2=ps2.executeQuery();
                               while(rs2.next()) 
                               {
                               xml=xml+"<AHcode>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"</AHcode>";
                               ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                               ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
                               rs3=ps3.executeQuery();
                               if(rs3.next())
                               xml=xml+"<AHdesc>"+rs3.getString("ACCOUNT_HEAD_DESC")+"</AHdesc>";
                               ps3.close();
                               rs3.close();
                               xml=xml+"<CR_DR_ind>"+rs2.getString("CR_DR_INDICATOR")+
                               "</CR_DR_ind><SL_Type>"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+
                               "</SL_Type>";
                               
                               if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
                               {
                               System.out.println("take SL DESC");
                               ps3=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                               ps3.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
                               rs3=ps3.executeQuery();
                               if(rs3.next())
                               xml=xml+"<SL_Desc>"+rs3.getString("SUB_LEDGER_TYPE_DESC")+"</SL_Desc>";
                               }
                               else
                                  {
                                  xml=xml+"<SL_Desc>"+null+"</SL_Desc>";   // it also return null value
                                   System.out.println("else part  23");
                                  }
                              
                               rs3.close();                            
                               ps3.close();
                               
                               xml=xml+"<SL_Code>"+rs2.getInt("SUB_LEDGER_CODE")+"</SL_Code>";
                                   
                               if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
                               {
                                   SL_TYPE_CODE_NAME_DETAILS obj_det=new SL_TYPE_CODE_NAME_DETAILS();
                                   ResultSet rs_det=obj_det.getResult_Details(cmbAcc_UnitCode,cmbOffice_code,rs2.getInt("SUB_LEDGER_TYPE_CODE"),rs2.getString("SUB_LEDGER_CODE"),0);
                                   if(rs_det!=null)
                                   {
                                       if(rs_det.next())
                                       {
                                           //System.out.println(rs_det.getString("cid"));
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
                                       
                                   xml=xml+"<Bill_NO>"+rs2.getString("BILL_NO")+"</Bill_NO>"+"<Bill_date>"+rs2.getString("b_date")+"</Bill_date>"+"<Bill_type>"
                                   +rs2.getString("BILL_TYPE")+"</Bill_type>"+"<Agree_No>"+rs2.getString("AGREEMENT_NO")+"</Agree_No>"+
                                   "<Agree_date>"+rs2.getString("agree_date")+"</Agree_date>"+
                                   "<sub_amount>"+rs2.getString("AMOUNT") +
                                   "</sub_amount><sub_part>"+rs2.getString("PARTICULARS")+"</sub_part>";
                                   
                                   xml=xml+"<adjyear>"+rs2.getInt("ADJ_AGAINST_YEAR")+"</adjyear>";
                                   xml=xml+"<adjmonth>"+rs2.getInt("ADJ_AGAINST_MONTH")+"</adjmonth>";
                                   xml=xml+"<doctype>"+rs2.getString("ADJ_DOC_TYPE")+"</doctype>";
                                   xml=xml+"<docno>"+rs2.getInt("ADJ_DOC_NO")+"</docno>";
                               }
                               
                               
                               
                               
                           }
                           catch(Exception e)
                           {
                           System.out.println("catch..HERE.in failure to retrieve."+e);
                               xml="<response><command>load_Voucher_Details</command><flag>failure</flag>";
                           }
                           xml=xml+"</response>";
                           System.out.println(xml);
                           out.println(xml);
            }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		  String strCommand="";
	         Connection con=null;
	         ResultSet rs=null,rs2=null,rs3=null;
	      CallableStatement cs1=null;
	         PreparedStatement ps=null;
	         PreparedStatement ps1=null,ps2=null,ps3=null;
	        
	        
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
	                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                              Class.forName(strDriver.trim());
	                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	             }
	             catch(Exception e)
	                 {
	                    System.out.println("Exception in opening connection :"+e);
	                    //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	                 }
	        
	        try {
	        
	            strCommand=request.getParameter("Command");
	            System.out.println("assign..here command..."+strCommand);
	           
	        }
	        
	        catch(Exception e) 
	        {
	            System.out.println("Exception in assigning..."+e);
	        }
	        if(strCommand.equalsIgnoreCase("Cancel")) 
	        {
	             String CONTENT_TYPE = "text/html; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	            Calendar c;
	            int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtJournalVou_No=0;
	            Date txtCrea_date=null;
	            String update_user=(String)session.getAttribute("UserId");
	            long l=System.currentTimeMillis();
	            Timestamp ts=new Timestamp(l);
	            
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
	          
	            System.out.println("b4 getting month and year");
	            try{txtCash_year=Integer.parseInt(sd[2]);}
	                        catch(Exception e){System.out.println("exception"+e );}
	                        System.out.println("txtCash_year "+txtCash_year);
	                        
	                        try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	                        catch(Exception e){System.out.println("exception"+e );}
	                        System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	            
	          /*  try{txtCash_year=Integer.parseInt(request.getParameter("txtCash_year"));}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtCash_year "+txtCash_year);
	            
	            try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCash_Month_hid"));}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);*/
	            
	           try{txtJournalVou_No=Integer.parseInt(request.getParameter("txtJournalVou_No"));}
	           catch(Exception e){System.out.println("exception"+e );}
	           System.out.println("txtJournalVou_No "+txtJournalVou_No);
	            
	            
	            String sql="" +
	            "update fas_brs_transaction \n" + 
	            "set\n" + 
	            " doc_date=null, \n" + 
	            " doc_no=0\n" + 
	            "where \n" + 
	            "  accounting_unit_id= ? \n" + 
	            "  and accounting_for_office_id= ? \n" + 
	            "  and cashbook_month=? \n" + 
	            "  and cashbook_year=? \n" + 
	            "  and twad_or_non_twad='NT' \n" + 
	            "  and doc_no = ? \n" + 
	            "  ";
	            
	             
	            
	            String sql_del="update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" +
	            "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and VOUCHER_NO=?  ";
	            try
	            {
	            con.clearWarnings();
	            con.setAutoCommit(false);
	            
	            
	            
	            
	            String sql_cancel="Select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID, ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH,ADJ_DOC_TYPE,ADJ_DOC_NO from FAS_JOURNAL_TRANSACTION where  ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" +
	            "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and VOUCHER_NO=? and ADJ_DOC_TYPE is not null";
	            ps2=con.prepareStatement(sql_cancel);
	            ps2.setInt(1,cmbAcc_UnitCode);
              ps2.setInt(2,cmbOffice_code);
             
              ps2.setInt(3,txtCash_year);    
              ps2.setInt(4,txtCash_Month_hid);
              ps2.setInt(5,txtJournalVou_No);
	            rs2=ps2.executeQuery();
	            while(rs2.next())
	            {
	            	
	            	String updateReference="";
	            String	docType=rs2.getString("ADJ_DOC_TYPE").trim();
	           int cmbAcc_UnitCode_ref=rs2.getInt("ACCOUNTING_UNIT_ID");
	        	 int  cmbOffice_code_ref=rs2.getInt("ACCOUNTING_FOR_OFFICE_ID");
	        	 int  adjYear=rs2.getInt("ADJ_AGAINST_YEAR");	
	        	 int  adjMonth=	rs2.getInt("ADJ_AGAINST_MONTH");
	        	 int docNo=rs2.getInt("ADJ_DOC_NO");
	        	   
	        	   
	            	 if(docType.equalsIgnoreCase("J"))  
              		 updateReference="update FAS_JOURNAL_MASTER set CB_REF_TYPE=null where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode_ref+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code_ref+" and CASHBOOK_YEAR="+adjYear+" and CASHBOOK_MONTH="+adjMonth+" and VOUCHER_NO="+docNo+"";
              	 else if(docType.equalsIgnoreCase("P"))   
              		 updateReference="update FAS_PAYMENT_MASTER set PAYABLE_VOUCHER_TYPE=null where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode_ref+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code_ref+" and CASHBOOK_YEAR="+adjYear+" and CASHBOOK_MONTH="+adjMonth+" and VOUCHER_NO="+docNo+"";
              	 else if(docType.equalsIgnoreCase("R"))   
              		 updateReference="update FAS_RECEIPT_MASTER set RECEIVABLE_VOUCHER_TYPE=null where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode_ref+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code_ref+" and CASHBOOK_YEAR="+adjYear+" and CASHBOOK_MONTH="+adjMonth+" and RECEIPT_NO="+docNo+"";
              	 else if(docType.equalsIgnoreCase("FT"))
              	 {
              		 if(cmbOffice_code==5000)
              		 updateReference="update FAS_FUND_TRF_FROM_HO_MASTER set RJV_CREATED=null where  CASHBOOK_YEAR="+adjYear+" and CASHBOOK_MONTH="+adjMonth+" and VOUCHER_NO="+docNo+"";
              		 else
              			 updateReference="update FAS_FUND_TRF_FROM_OFFICE set RJV_CREATED=null where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode_ref+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code_ref+" and CASHBOOK_YEAR="+adjYear+" and CASHBOOK_MONTH="+adjMonth+" and VOUCHER_NO="+docNo+"";
              	}
              	 else if(docType.equalsIgnoreCase("FR"))
              	 {
              		 if(cmbOffice_code==5000)
              		 updateReference="update FAS_FUND_RECEIPT_BY_HO set RJV_CREATED=null where  CASHBOOK_YEAR="+adjYear+" and CASHBOOK_MONTH="+adjMonth+" and RECEIPT_NO="+docNo+"";
              		 else
              			 updateReference="update FAS_FUND_RECEIPT_BY_OFFICE set RJV_CREATED=null where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode_ref+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code_ref+" and CASHBOOK_YEAR="+adjYear+" and CASHBOOK_MONTH="+adjMonth+" and RECEIPT_NO="+docNo+"";
              	 }
              	 else if(docType.equalsIgnoreCase("IBT"))   
              		 updateReference="update FAS_INTER_BANK_TRF_AT_HO set RJV_CREATED=null where   CASHBOOK_YEAR="+adjYear+" and CASHBOOK_MONTH="+adjMonth+" and VOUCHER_NO="+docNo+"";
	          System.out.println("updateReference ****"+updateReference);  	 
	           ps3= con.prepareStatement(updateReference);	 
	           ps3.executeUpdate(); 	 
	            ps3.close();	 
              	
	            }
	            
	            
	            
	            
	            
	                ps1=con.prepareStatement(sql);
	                ps1.setInt(1,cmbAcc_UnitCode);
	                ps1.setInt(2,cmbOffice_code);
	                ps1.setInt(3,txtCash_Month_hid);
	                ps1.setInt(4,txtCash_year);                
	                ps1.setInt(5,txtJournalVou_No);
	                
	                ps1.executeUpdate();
	                
	                ps=con.prepareStatement(sql_del);
	                ps.setString(1,update_user);
	                ps.setTimestamp(2,ts);
	                ps.setInt(3,cmbAcc_UnitCode);
	                ps.setInt(4,cmbOffice_code);
	                ps.setInt(5,txtCash_year);
	                ps.setInt(6,txtCash_Month_hid);
	                ps.setInt(7,txtJournalVou_No);
	                ps.executeUpdate();
	                String txtReferNO_edit="",txtRemak_edit="", txtRefdate="";         // for cross reference
	                Date txtReferDate_edit=null; 
	                String radAuth_MC="";
	                int txtAuth_By=0;
	                
	                System.out.println("txtAuth_By  "+txtAuth_By);
	                cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
	                cs1.setInt(1,cmbAcc_UnitCode);
	                cs1.setInt(2,txtCash_year);
	                cs1.setInt(3,txtCash_Month_hid);
	                cs1.setInt(4,txtJournalVou_No);
	                cs1.setInt(5,cmbOffice_code);
	                cs1.setDate(6,txtCrea_date);
	                cs1.setString(7,"RJV");
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
	                
	                int errcode=cs1.getInt(13);
	                System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	                System.out.println("cmbOffice_code "+cmbOffice_code);
	                System.out.println("txtCrea_date "+txtCrea_date);
	                System.out.println("txtCash_year "+txtCash_year);
	                System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	                System.out.println("SQLCODE:::"+errcode);
	                if(errcode!=0)
	                {   
	                  con.rollback();
	                  sendMessage(response,"Rectification Voucher Number Cancellation Failed ","ok");
	                  xml=xml+"<flag>failure</flag>";                          
	                }
	            con.commit();
	            sendMessage(response,"Rectification Voucher Number '"+txtJournalVou_No+"' has been cancelled Successfully ","ok");
	            }
	            catch(Exception e) 
	            {
	                try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
	                sendMessage(response,"Rectification Voucher Number Cancellation Failed ","ok");
	                System.out.println("Exception occur due to "+e);
	            }
	            finally
	            {
	                System.out.println("done");
	                try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
	            }
	            
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

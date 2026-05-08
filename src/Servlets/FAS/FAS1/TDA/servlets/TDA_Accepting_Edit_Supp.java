package Servlets.FAS.FAS1.TDA.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class TDA_Accepting_Edit_Supp extends HttpServlet
{
    private String CONTENT_TYPE = "text/html; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
    {

  	
             /**
	       * Set Content Type 
	      */
	      PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	      
	      
	      
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
	       * Variables Declaration 
	      */		        
	      Connection con=null;
	      PreparedStatement ps2=null;        
	      ResultSet rs2=null;
	      String sql="";
	        	      
	      /**
	       * Database Connection 
	      */
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
        
	    
	    
            int count=0,cmbAcc_UnitCode=0,cmbOffice_code=0,accepted_slno=0,txtUnitId=0,supNo=0;
            String xml=null,cmd="",option="",Journal_type="";          
            Date txtCrea_date=null;
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            System.out.println("Command ::: "+cmd);
            
            try{option=request.getParameter("Option");}
            catch(Exception e){System.out.println(e);}
            
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            try{
            String[] sd=request.getParameter("txtCrea_date").split("/");
            Calendar c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            }
            catch(Exception e){System.out.println("Exp in create Date :: "+e.getMessage());}
            System.out.println("txtCrea_date "+txtCrea_date);
            
            try{accepted_slno=Integer.parseInt(request.getParameter("accepted_slno"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("accepted_slno "+accepted_slno);
            
            try{Journal_type=request.getParameter("Journal_type");}
            catch(Exception e){System.out.println("Journal_type "+e );}
            System.out.println("Journal_type "+Journal_type);
            
            try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtUnitId "+txtUnitId);
            
            try{supNo=Integer.parseInt(request.getParameter("supNo"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            System.out.println("cmd:::"+cmd);
            xml="<response>";
            if(cmd.equalsIgnoreCase("loadVoucher"))
            {     
                 
                    xml=xml+"<command>loadVoucher</command>";
                    sql="select VOUCHER_NO from\n" + 
                    "(\n" + 
                    "select    \n" + 
                    "    mst.ACCOUNTING_UNIT_ID,   \n" + 
                    "    mst.ACCOUNTING_FOR_OFFICE_ID,   \n" + 
                    "    mst.VOUCHER_NO,   \n" + 
                    "    TRF_ACCOUNTING_UNIT_ID,   \n" + 
                    "    ACCEPTING_SLNO,   \n" + 
                    "    ACCEPTING_DATE,\n" + 
                    "    RESPONDING_JVR_NO,\n" + 
                    "    RESPONDING_JVR_DATE,\n" + 
                    "    c.AUTHORIZED_TO   \n" + 
                    "from   \n" + 
                    "    FAS_TDA_TCA_RAISED_MST mst,  \n" + 
                    "    FAS_CROSS_REFERENCE c   \n" + 
                    "where    \n" + 
                    "    mst.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and	  \n" + 
                    "    mst.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID and	  \n" + 
                    "    mst.CASHBOOK_YEAR=c.CASHBOOK_YEAR and	  \n" + 
                    "    mst.CASHBOOK_MONTH=c.CASHBOOK_MONTH and   \n" + 
                    "    mst.VOUCHER_NO=c.VOUCHER_NO and	        \n" + 
                    "    mst.ACCOUNTING_UNIT_ID=? and   \n" + 
                    "    mst.ACCOUNTING_FOR_OFFICE_ID=? and   \n" + 
                    "    mst.VOUCHER_DATE=? and   \n" + 
                    "    TDA_OR_TCA=? and   \n" +
                    "    mst.STATUS='L' and " + 
                    "    c.CHANGE_NO=0  and   \n" + 
                    "    (c.DOC_TYPE=?  OR c.DOC_TYPE=?)\n" + 
                    ")aa\n" + 
                    "where  RESPONDING_JVR_NO not in \n" + 
                    "(\n" + 
                    "    select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and VOUCHER_DATE=aa.RESPONDING_JVR_DATE and VOUCHER_NO=aa.RESPONDING_JVR_NO and JOURNAL_STATUS='L' \n" + 
                    ")";
                    System.out.println("option ::: "+option);
                    if(option.equals("Edit"))
       	        	 	     sql=sql+" and AUTHORIZED_TO='M'";
                    else
       	        	 	     sql=sql+" and AUTHORIZED_TO='C'";
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cmbOffice_code);
                             ps2.setDate(3,txtCrea_date);
                             ps2.setString(4,Journal_type);
                             ps2.setString(5,Journal_type);
                             if(Journal_type.equals("TCAA")) {
                                 ps2.setString(6,"TCAAB");
                             }
                             else {
                                 ps2.setString(6,"TDAAB"); 
                             }
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<voucher_no>"+ rs2.getInt("VOUCHER_NO") +"</voucher_no>";	                                     			                		                
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucher..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }	
            else if(cmd.equalsIgnoreCase("loadVr_sup"))
            {     
                 
            	String TDA_TCA="",Ref_TDA_TCA="";
                if(Journal_type.equalsIgnoreCase("TCAAS")){
              		 TDA_TCA="TCACB','TCAA";
              		 Ref_TDA_TCA="TCAA";
                   }else if(Journal_type.equalsIgnoreCase("TDAAS")){
              		 TDA_TCA="TDACB','TDAA";
              		Ref_TDA_TCA="TDAA";
                   }
            	System.out.println("Ref_TDA_TCA--->"+Ref_TDA_TCA);
            	System.out.println("supNo--->"+supNo);
            	
            	
            	xml=xml+"<command>loadVr_sup</command>";
                    sql="select VOUCHER_NO from\n" + 
                    "(\n" + 
                    "select    \n" + 
                    "    mst.ACCOUNTING_UNIT_ID,   \n" + 
                    "    mst.ACCOUNTING_FOR_OFFICE_ID,   \n" + 
                    "    mst.VOUCHER_NO,   \n" + 
                    "    TRF_ACCOUNTING_UNIT_ID,   \n" + 
                    "    ACCEPTING_SLNO,   \n" + 
                    "    ACCEPTING_DATE,\n" + 
                    "    RESPONDING_JVR_NO,\n" + 
                    "    RESPONDING_JVR_DATE,\n" + 
                    "    c.AUTHORIZED_TO   \n" + 
                    "from   \n" + 
                    "    FAS_TDA_TCA_RAISED_MST mst,  \n" + 
                    "    FAS_CROSS_REFERENCE c   \n" + 
                    "where    \n" + 
                    "    mst.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and	  \n" + 
                    "    mst.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID and	  \n" + 
                    "    mst.CASHBOOK_YEAR=c.CASHBOOK_YEAR and	  \n" + 
                    "    mst.CASHBOOK_MONTH=c.CASHBOOK_MONTH and   \n" + 
                    "    mst.VOUCHER_NO=c.VOUCHER_NO and	        \n" + 
                    "    mst.ACCOUNTING_UNIT_ID=? and   \n" + 
                    "    mst.ACCOUNTING_FOR_OFFICE_ID=? and   \n" + 
                    "    mst.VOUCHER_DATE=? and   \n" + 
                   // "    TDA_OR_TCA=? and   \n" +
                   "     mst.TDA_OR_TCA in ('"+TDA_TCA+"') and \n" +
                    "    mst.STATUS='L' and " + 
                    "    c.CHANGE_NO=0  and   \n" + 
                    //"    (c.DOC_TYPE=?  OR c.DOC_TYPE=?) and \n" + 
                    "	 c.DOC_TYPE=? and \n" +
                    "    SUPPLEMENT_NO=?\n" +
                    ")aa\n" + 
                    "where  RESPONDING_JVR_NO not in \n" + 
                    "(\n" + 
                    "    select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and VOUCHER_DATE=aa.RESPONDING_JVR_DATE and VOUCHER_NO=aa.RESPONDING_JVR_NO and JOURNAL_STATUS='L' \n" + 
                    ")";
                    System.out.println("option ::: "+option);
                    if(option.equals("Edit"))
       	        	 	     sql=sql+" and AUTHORIZED_TO='M'";
                    else
       	        	 	     sql=sql+" and AUTHORIZED_TO='C'";
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cmbOffice_code);
                             ps2.setDate(3,txtCrea_date);
                             ps2.setString(4,Ref_TDA_TCA);
                             //ps2.setString(5,Journal_type);
//                             if(Journal_type.equals("TCAA")) {
//                                 ps2.setString(6,"TCAAB");
//                             }
//                             else {
//                                 ps2.setString(6,"TDAAB"); 
//                             }
                             ps2.setInt(5,supNo);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<voucher_no>"+ rs2.getInt("VOUCHER_NO") +"</voucher_no>";	                                     			                		                
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucher..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            
            else if(cmd.equalsIgnoreCase("loadVoucherDetails"))
            {     
                 
                    xml=xml+"<command>loadVoucherDetails</command>";
                    sql="select * from(select \n" + 
                    "    b.VOUCHER_NO,\n" + 
                    "    to_char(b.VOUCHER_DATE,'dd/mm/yyyy')as VOUCHER_DATE,\n" + 
                    "    b.ACCOUNTING_UNIT_ID," +
                    "    b.REASON_FOR_TRANSFER,\n" + 
                    "    c.ACCOUNTING_UNIT_NAME,\n" + 
                    "    trim(to_char(b.TOTAL_AMOUNT,'99999999999999.99'))as TOTAL_AMOUNT,\n" + 
                    "    a.ACCOUNT_HEAD_CODE as acc_head,\n" + 
                    "    a.PARTICULARS,\n" + 
                    "    a.TDA_OR_TCA,\n" + 
                    "    a.ACCEPTANCE_STATUS,\n" + 
                    "    a.REASON_FOR_NON_ACCEPT,\n" + 
                    "    trn.SL_NO,\n" + 
                    "    trn.ACCOUNT_HEAD_CODE as trn_acc_head,\n" + 
                    "    acc_mst.ACCOUNT_HEAD_DESC,\n" + 
                    "    trn.CR_DR_INDICATOR,\n" + 
                    "    trn.SUB_LEDGER_TYPE_CODE as trn_sub_type_code,\n" + 
                    "    trn.SUB_LEDGER_CODE as trn_sub_code,\n" + 
                    "    trn.PAID_TO,\n" + 
                    "    trim(to_char(trn.AMOUNT,'99999999999999.99')) as AMOUNT,\n" + 
                    "    trn.PARTICULARS as trn_particulars,\n" + 
                    "    trn.MBOOK_NO ,	\n" + 
                    "    trn.MBOOK_PAGENO, \n" + 
       	         	"    to_char(trn.MBOOK_DATE,'dd/mm/yyyy') as bkDate   \n" +
                    "from \n" + 
                    "  FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "  FAS_TDA_TCA_RAISED_MST b,\n" + 
                    "  FAS_MST_ACCT_UNITS c,\n" + 
                    "  FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                    "  COM_MST_ACCOUNT_HEADS acc_mst\n" + 
                    "where a.ACCOUNTING_UNIT_ID=b.TRF_ACCOUNTING_UNIT_ID\n" + 
                    "and a.VOUCHER_NO=b.ACCEPTING_SLNO \n" + 
                    "and a.VOUCHER_DATE=b.ACCEPTING_DATE\n" + 
                    "and a.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID \n" + 
                    "and a.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID \n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                    "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH\n" + 
                    "and a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                    "and trn.ACCOUNT_HEAD_CODE=acc_mst.ACCOUNT_HEAD_CODE\n" + 
                    "and a.ACCOUNTING_UNIT_ID=?  \n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=? \n" + 
                    "and a.VOUCHER_DATE=?  \n" + 
                    "and a.VOUCHER_NO=? \n" +
                    "and a.TDA_OR_TCA=? " + 
                    "and a.STATUS='L' " +
                    ")aa left outer join \n " +
                    "(\n" + 
	       	        "    	select SUB_LEDGER_TYPE_DESC,SUB_LEDGER_TYPE_CODE as sub_code from COM_MST_SL_TYPES\n" + 
	       	        ")bb on aa.trn_sub_type_code=bb.sub_code order by SL_NO";
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cmbOffice_code);
                             ps2.setDate(3,txtCrea_date);
                             ps2.setInt(4,accepted_slno);
                             ps2.setString(5,Journal_type);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                            	 	 xml+="<voucher_no>"+rs2.getInt("VOUCHER_NO")+"</voucher_no>";	         
                                     xml+="<voucher_date>"+rs2.getString("VOUCHER_DATE")+"</voucher_date>";	                                     			                		                
                                     xml+="<voucher_total_amount>"+rs2.getString("TOTAL_AMOUNT")+"</voucher_total_amount>";
                                     xml+="<unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</unit_id>";
                                     xml+="<unit_name>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</unit_name>";
                                     xml+="<acc_head>"+rs2.getString("acc_head") +"</acc_head>";
                                     xml+="<reason_for_transfer>"+rs2.getString("REASON_FOR_TRANSFER") +"</reason_for_transfer>";
                                     if(rs2.getString("PARTICULARS")==null)
                                    	 xml+="<particulars>--</particulars>";
                                     else
                                    	 xml+="<particulars>"+rs2.getString("PARTICULARS") +"</particulars>";
                                     xml+="<acceptance>"+rs2.getString("ACCEPTANCE_STATUS") +"</acceptance>";
                                     if(rs2.getString("REASON_FOR_NON_ACCEPT")==null)
                                    	 xml+="<reason>--</reason>";
                                     else
                                    	 xml+="<reason>"+rs2.getString("REASON_FOR_NON_ACCEPT") +"</reason>";
                                     xml+="<sl_no>"+rs2.getInt("SL_NO") +"</sl_no>";
                                     xml+="<trn_acc_head>"+rs2.getInt("trn_acc_head") +"</trn_acc_head>";
                                     xml+="<head_desc>"+rs2.getString("ACCOUNT_HEAD_DESC") +"</head_desc>";
                                     xml+="<cr_dr_indicator>"+rs2.getString("CR_DR_INDICATOR") +"</cr_dr_indicator>";
                                     xml+="<trn_sub_type_code>"+rs2.getInt("trn_sub_type_code") +"</trn_sub_type_code>";
                                     xml+= "<trn_sub_type_desc>"+ rs2.getString("SUB_LEDGER_TYPE_DESC") +"</trn_sub_type_desc>";
                                     xml+="<trn_sub_code>"+rs2.getInt("trn_sub_code") +"</trn_sub_code>";
                                     xml+="<trn_sub_desc>"+rs2.getString("PAID_TO") +"</trn_sub_desc>";
                                     xml+="<amount>"+rs2.getString("AMOUNT") +"</amount>";
                                     xml+="<trn_particulars>"+rs2.getString("trn_particulars") +"</trn_particulars>";
                                     xml+= "<trn_bookNo>"+ rs2.getInt("MBOOK_NO") +"</trn_bookNo>";
                                     xml+= "<trn_bookPageno>"+ rs2.getInt("MBOOK_PAGENO") +"</trn_bookPageno>";
                                     xml+= "<trn_bookDate>"+ rs2.getString("bkDate") +"</trn_bookDate>";
                                  
                                     
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucherDetails..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            else if(cmd.equalsIgnoreCase("loadVrDetails_sup"))
            {     
                 
                    
            	String TDA_TCA="";
                if(Journal_type.equalsIgnoreCase("TCAAS")){
              		 TDA_TCA="TCACB','TCAA";
                   }else if(Journal_type.equalsIgnoreCase("TDAAS")){
              		 TDA_TCA="TDACB','TDAA";
                   }
            	
            	xml=xml+"<command>loadVrDetails_sup</command>";
                    sql="select * from(select \n" + 
                    "    b.VOUCHER_NO,\n" + 
                    "    to_char(b.VOUCHER_DATE,'dd/mm/yyyy')as VOUCHER_DATE,\n" + 
                    "    b.ACCOUNTING_UNIT_ID," +
                    "    b.REASON_FOR_TRANSFER,\n" + 
                    "    c.ACCOUNTING_UNIT_NAME,\n" + 
                    "    trim(to_char(b.TOTAL_AMOUNT,'99999999999999.99'))as TOTAL_AMOUNT,\n" + 
                    "    a.ACCOUNT_HEAD_CODE as acc_head,\n" + 
                    "    a.PARTICULARS,\n" + 
                    "    a.TDA_OR_TCA,\n" + 
                    "    a.ACCEPTANCE_STATUS,\n" + 
                    "    a.REASON_FOR_NON_ACCEPT,\n" + 
                    "    trn.SL_NO,\n" + 
                    "    trn.ACCOUNT_HEAD_CODE as trn_acc_head,\n" + 
                    "    acc_mst.ACCOUNT_HEAD_DESC,\n" + 
                    "    trn.CR_DR_INDICATOR,\n" + 
                    "    trn.SUB_LEDGER_TYPE_CODE as trn_sub_type_code,\n" + 
                    "    trn.SUB_LEDGER_CODE as trn_sub_code,\n" + 
                    "    trn.PAID_TO,\n" + 
                    "    trim(to_char(trn.AMOUNT,'99999999999999.99')) as AMOUNT,\n" + 
                    "    trn.PARTICULARS as trn_particulars,\n" + 
                    "    trn.MBOOK_NO ,	\n" + 
                    "    trn.MBOOK_PAGENO, \n" + 
       	         	"    to_char(trn.MBOOK_DATE,'dd/mm/yyyy') as bkDate   \n" +
                    "from \n" + 
                    "  FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "  FAS_TDA_TCA_RAISED_MST b,\n" + 
                    "  FAS_MST_ACCT_UNITS c,\n" + 
                    "  FAS_TDA_TCA_RAISED_TRN trn,\n" + 
                    "  COM_MST_ACCOUNT_HEADS acc_mst\n" + 
                    "where a.ACCOUNTING_UNIT_ID=b.TRF_ACCOUNTING_UNIT_ID\n" + 
                    "and a.VOUCHER_NO=b.ACCEPTING_SLNO \n" + 
                    "and a.VOUCHER_DATE=b.ACCEPTING_DATE\n" + 
                    "and a.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID \n" + 
                    "and a.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID \n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
                    "and a.CASHBOOK_YEAR=trn.CASHBOOK_YEAR\n" + 
                    "and a.CASHBOOK_MONTH=trn.CASHBOOK_MONTH\n" + 
                    "and a.VOUCHER_NO=trn.VOUCHER_NO\n" + 
                    "and trn.ACCOUNT_HEAD_CODE=acc_mst.ACCOUNT_HEAD_CODE\n" + 
                    "and a.ACCOUNTING_UNIT_ID=?  \n" + 
                    "and a.ACCOUNTING_FOR_OFFICE_ID=? \n" + 
                    "and a.VOUCHER_DATE=?  \n" + 
                    "and a.VOUCHER_NO=? \n" +
                    //"and a.TDA_OR_TCA=? " + 
                    "and a.TDA_OR_TCA in ('"+TDA_TCA+"') \n" +
                    "and a.STATUS='L' " +
                    "and a.SUPPLEMENT_NO=?\n" +
                    ")aa left outer join \n " +
                    "(\n" + 
	       	        "    	select SUB_LEDGER_TYPE_DESC,SUB_LEDGER_TYPE_CODE as sub_code from COM_MST_SL_TYPES\n" + 
	       	        ")bb on aa.trn_sub_type_code=bb.sub_code order by SL_NO";
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cmbOffice_code);
                             ps2.setDate(3,txtCrea_date);
                             ps2.setInt(4,accepted_slno);
                            // ps2.setString(5,Journal_type);
                             ps2.setInt(5,supNo);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                            	 	 xml+="<voucher_no>"+rs2.getInt("VOUCHER_NO")+"</voucher_no>";	         
                                     xml+="<voucher_date>"+rs2.getString("VOUCHER_DATE")+"</voucher_date>";	                                     			                		                
                                     xml+="<voucher_total_amount>"+rs2.getString("TOTAL_AMOUNT")+"</voucher_total_amount>";
                                     xml+="<unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</unit_id>";
                                     xml+="<unit_name>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</unit_name>";
                                     xml+="<acc_head>"+rs2.getString("acc_head") +"</acc_head>";
                                     xml+="<reason_for_transfer>"+rs2.getString("REASON_FOR_TRANSFER") +"</reason_for_transfer>";
                                     if(rs2.getString("PARTICULARS")==null)
                                    	 xml+="<particulars>--</particulars>";
                                     else
                                    	 xml+="<particulars>"+rs2.getString("PARTICULARS") +"</particulars>";
                                     xml+="<acceptance>"+rs2.getString("ACCEPTANCE_STATUS") +"</acceptance>";
                                     if(rs2.getString("REASON_FOR_NON_ACCEPT")==null)
                                    	 xml+="<reason>--</reason>";
                                     else
                                    	 xml+="<reason>"+rs2.getString("REASON_FOR_NON_ACCEPT") +"</reason>";
                                     xml+="<sl_no>"+rs2.getInt("SL_NO") +"</sl_no>";
                                     xml+="<trn_acc_head>"+rs2.getInt("trn_acc_head") +"</trn_acc_head>";
                                     xml+="<head_desc>"+rs2.getString("ACCOUNT_HEAD_DESC") +"</head_desc>";
                                     xml+="<cr_dr_indicator>"+rs2.getString("CR_DR_INDICATOR") +"</cr_dr_indicator>";
                                     xml+="<trn_sub_type_code>"+rs2.getInt("trn_sub_type_code") +"</trn_sub_type_code>";
                                     xml+= "<trn_sub_type_desc>"+ rs2.getString("SUB_LEDGER_TYPE_DESC") +"</trn_sub_type_desc>";
                                     xml+="<trn_sub_code>"+rs2.getInt("trn_sub_code") +"</trn_sub_code>";
                                     xml+="<trn_sub_desc>"+rs2.getString("PAID_TO") +"</trn_sub_desc>";
                                     xml+="<amount>"+rs2.getString("AMOUNT") +"</amount>";
                                     xml+="<trn_particulars>"+rs2.getString("trn_particulars") +"</trn_particulars>";
                                     xml+= "<trn_bookNo>"+ rs2.getInt("MBOOK_NO") +"</trn_bookNo>";
                                     xml+= "<trn_bookPageno>"+ rs2.getInt("MBOOK_PAGENO") +"</trn_bookPageno>";
                                     xml+= "<trn_bookDate>"+ rs2.getString("bkDate") +"</trn_bookDate>";
                                  
                                     
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucherDetails..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            
            else if(cmd.equalsIgnoreCase("loadSLType"))
            {
            	    xml=xml+"<command>loadSLType</command>";
            	    try	
            	    {			        	 			  	                 		  
            	    		 sql="select trn.ACCOUNTING_UNIT_OFFICE_ID,mst.OFFICE_SHORT_NAME from FAS_MST_ACCT_UNITS trn,COM_MST_OFFICES mst where trn.ACCOUNTING_UNIT_OFFICE_ID=mst.OFFICE_ID and trn.ACCOUNTING_UNIT_ID=?";            	    		 
            	    		 ps2=con.prepareStatement(sql);
            	    		 ps2.setInt(1,txtUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<office_id>"+ rs2.getInt("ACCOUNTING_UNIT_OFFICE_ID") +"</office_id>";	 
                                     xml+= "<office_name>"+ rs2.getString("OFFICE_SHORT_NAME") +"</office_name>";  				                		                 
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
            	    }
            	    catch(Exception e)
            	    {
            		  		 System.out.println("Err in loading loadSLType ::: "+e.getMessage());            		  	
            	    }
            }
           
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
            out.close();
    }
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
         
         String strCommand="";
         Connection con=null;        
         PreparedStatement ps=null,ps1=null,ps2=null;
         String xml="";
         Statement st=null;
         ResultSet rs=null;
         Boolean flag=true;
 //-----------------------------------------------------------------------------------------------        
 
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
        
//-----------------------------------------------------------------------------------------------        
        
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
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              st=con.createStatement();
         }
         catch(Exception e)
         {
             System.out.println("Exception in opening connection :"+e);
             //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

         }
                 
 //-----------------------------------------------------------------------------------------------        
        
        try
        {        
             strCommand=request.getParameter("Command");
             System.out.println("assign..here command..."+strCommand);
           
        }
        
        catch(Exception e) 
        {
             System.out.println("Exception in assigning..."+e);
        }
        
//-----------------------------------------------------------------------------------------------        
       
        if(strCommand.equalsIgnoreCase("Edit")) 
        {
             String CONTENT_TYPE = "text/html; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
            
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
             Calendar c;
             int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtDebitHead=0,supNo=0;
             int count=0,cmbMas_SL_type=0,cmbMas_SL_Code=0,txtReason=0,responded_jvr_no=0;
             double txtTotalAmt=0;
             Date txtCrea_date=null;
             String txtRemarks="",Journal_type="",paid_to="",cr_dr_indicator="",sql="",responded_jvr_dt=null;
             
                                     // changes here
             String update_user=(String)session.getAttribute("UserId");
             long l=System.currentTimeMillis();
             Timestamp ts=new Timestamp(l);                      
             int errcode=0;
             CallableStatement cs1=null;
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                
             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             
             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             
             try{Journal_type=request.getParameter("Journal_type");}
             catch(Exception e){System.out.println("exception"+e );}
      
             String[] sd1=request.getParameter("txtCrea_date").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
             java.util.Date d1=c.getTime();
             txtCrea_date=new Date(d1.getTime());
     
             try{txtCash_year=Integer.parseInt(sd1[2]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_year "+txtCash_year);
             
             try{txtCash_Month_hid=Integer.parseInt(sd1[1]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
             int accepting_SL_No=0;
             
            
             try{accepting_SL_No=Integer.parseInt(request.getParameter("accepted_slno"));}	           
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("accepting_SL_No "+accepting_SL_No);
             
             try{txtReason=Integer.parseInt(request.getParameter("txtReason"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtReason "+txtReason);
             
             try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtUnitId "+txtUnitId);
             
             try{txtDebitHead=Integer.parseInt(request.getParameter("txtDebitHead"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtDebitHead "+txtDebitHead);
          
                                             
             try{txtRemarks=request.getParameter("txtRemarks");}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtRemarks "+txtRemarks);
             
             try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtAmount "+txtTotalAmt);
             
             try{Journal_type=request.getParameter("Journal_type");}
             catch(Exception e){System.out.println("Journal_type "+e );}
             System.out.println("Journal_type "+Journal_type);
             
             try{supNo=Integer.parseInt(request.getParameter("supNo"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("supNo "+supNo);
             
             if(Journal_type.equals("TDAA"))
		       	 	  cr_dr_indicator="CR";
		     else
		       	 	  cr_dr_indicator="DR";
             
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                 
             try 
             {   
                      con.clearWarnings();
                      con.setAutoCommit(false);
                      ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCOUNT_HEAD_CODE=?,PAID_TO=?,TRF_ACCOUNTING_UNIT_ID=?,REASON_FOR_TRANSFER=?,SUB_LEDGER_TYPE_CODE=?,SUB_LEDGER_CODE=?,TOTAL_AMOUNT=?,PARTICULARS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,TDA_OR_TCA=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SUPPLEMENT_NO=?");
                      //System.out.println("insert into FAS_ADJUSTMENT_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ADJUSTMENT_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,BEHALF_OF_OFFICE_ID,PARTICULARS,TOTAL_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,VOUCHER_STATUS) values ("+cmbAcc_UnitCode+","+cmbOffice_code+",'"+txtCrea_date+"',"+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+office_id+",'"+particulars+"','"+txtTotalAmt+"','"+update_user+"','"+ts+"','L')");
                            
                      ps.setInt(1,txtDebitHead);                      
                      ps.setString(2,paid_to);
                      ps.setInt(3,txtUnitId);
                      ps.setInt(4,txtReason);
                      ps.setInt(5,cmbMas_SL_type);
                      ps.setInt(6,cmbMas_SL_Code);
                      ps.setDouble(7,txtTotalAmt);
                      ps.setString(8,txtRemarks);
                      ps.setString(9,"L");
                      ps.setString(10,update_user);
                      ps.setTimestamp(11,ts);
                      ps.setString(12,Journal_type);
                      ps.setInt(13,cmbAcc_UnitCode);
                      ps.setInt(14,cmbOffice_code);
                      ps.setInt(15,txtCash_year);
                      ps.setInt(16,txtCash_Month_hid);
                      ps.setInt(17,accepting_SL_No);
                      ps.setInt(18,supNo);
                      errcode=ps.executeUpdate();
                      if(errcode==0)
                      {         
	                          System.out.println("Err in first table insertion");	                         
	                          flag=false;
	                          
                      }
                      else
                      {		                    	  	  
	                    	  ps.close();
	                    	  errcode=0;
	                    	  String sql_del=" delete from FAS_TDA_TCA_RAISED_TRN  "+ 
                 			  " where ACCOUNTING_UNIT_ID=? 					"+
                 			  " and ACCOUNTING_FOR_OFFICE_ID=?				"+
                 			  " and CASHBOOK_YEAR=? 							"+
                 			  " and CASHBOOK_MONTH=?							"+
                 			  " and voucher_no=? 						    "  +
                 			  " and SUPPLEMENT_NO=?           ";	                 			
         	 
				         	  ps=con.prepareStatement(sql_del);
				              ps.setInt(1,cmbAcc_UnitCode);
				              ps.setInt(2,cmbOffice_code);
				              ps.setInt(3,txtCash_year);
				              ps.setInt(4,txtCash_Month_hid);
				              ps.setInt(5,accepting_SL_No);
				              ps.setInt(6,supNo);
				              errcode=ps.executeUpdate();    
				              if(errcode==0)
		                      {         
			                       	  System.out.println("Can not delete old records for this voucher in transaction table");	
			                       	  flag=false;
		                      }
		                      else
		                      {  
			                    	 /* if(isAccept.equals("Y"))
			                    	  {*/
		                    	  	  ps.close();
			                          System.out.println("inside 2 nd table");                              
			                          String Grid_H_code[]=request.getParameterValues("H_code");
			                          String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
			                          String Grid_SL_type[]=request.getParameterValues("SL_type");
			                          String Grid_SL_code[]=request.getParameterValues("SL_code");                          
			                          String Grid_sl_amt[]=request.getParameterValues("sl_amt");
			                          String Grid_particular[]=request.getParameterValues("sl_particular");                         
			                          String Trn_Paid_To[]=request.getParameterValues("Paid_To");  
			                          String grid_bookno[]=request.getParameterValues("m_bkNo");   
			                          String grid_bookpageno[]=request.getParameterValues("m_bkPageno");
			                          String grid_bookdate[]=request.getParameterValues("m_bookDate"); 
			                          System.out.println("2 nd table insert");
			                          Date sl_ref_date=null,bkDate=null;
			                          int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,ref_num=0,bookPageNo=0,bookNo=0;
			                          double txtsub_Amount=0;
			                          try
			                          {
			                                      sql="insert into FAS_TDA_TCA_RAISED_TRN(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH,VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE,PAID_TO, AMOUNT, PARTICULARS, UPDATED_BY_USERID, UPDATED_DATE,MBOOK_NO,MBOOK_PAGENO,MBOOK_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
			                                      ps=con.prepareStatement(sql);
			                                      for(int k=0;k<Grid_H_code.length;k++) 
			                                      {         
			                                    	  		try
			                                    	  		{
					                                    	  		System.out.println("row"+(k+1)+"Starting");
					                                                cmbSL_type=0;cmbSL_Code=0;ref_num=0;
					                                                txtAcc_HeadCode=0;  txtsub_Amount=0; 
					                                                txtsub_Amount=0;
					                                                                                  
					                                                SL_NO++;
					                                                ps.setInt(1,cmbAcc_UnitCode);     
					                                                ps.setInt(2,cmbOffice_code);    
					                                                ps.setInt(3,txtCash_year);
					                                                ps.setInt(4,txtCash_Month_hid); 
					                                                ps.setInt(5,accepting_SL_No);       
					                                                ps.setInt(6,SL_NO);
					                                                
					                                                txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
					                                                ps.setInt(7,txtAcc_HeadCode);
					                                                
					                                                String rad_sub_CR_DR=Grid_CR_DR_type[k];                               
					                                                ps.setString(8,rad_sub_CR_DR);   
					                                                
					                                                try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setInt(9,cmbSL_type); 
					                                                
					                                                try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setInt(10,cmbSL_Code);
					                                                
					                                                ps.setString(11,Trn_Paid_To[k]);
					                                                                                   
					                                                try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setDouble(12,txtsub_Amount);
					                                                
					                                                ps.setString(13,Grid_particular[k]);      
					                                                
					                                                ps.setString(14,update_user);
					                                                ps.setTimestamp(15,ts);
					                                                try{bookNo=Integer.parseInt(grid_bookno[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setInt(16,bookNo); 
					                                               
					                                                try{bookPageNo=Integer.parseInt(grid_bookpageno[k]);}
					                                                catch(NumberFormatException e){System.out.println("exception"+e );}
					                                                ps.setInt(17,bookPageNo);
					                                               
					                                                try
									                                 {
					                                                	if(grid_bookdate[k].equalsIgnoreCase("null"))
						                                                	{
						                                                		System.out.println("nullrrrrrrrrr");
						                                                		 ps.setNull(18,java.sql.Types.DATE);
						                                                	}
					                                                	else if(grid_bookdate[k].equalsIgnoreCase(""))
						                                                	{
						                                                		System.out.println("emptyyyyyyyyyy");
						                                                		 ps.setNull(18,java.sql.Types.DATE);
						                                                	}
					                                                	else
											                                 {
											                             		 System.out.println("iffffff");
											                             		String[] sd=grid_bookdate[k].split("/");
													                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
													                                 java.util.Date d=c.getTime();
													                                 bkDate=new Date(d.getTime());
													                                 ps.setDate(18,bkDate);
													                                
											                                 }
										    	                    	 
									                                 }
									                                 catch(Exception e) {
									                                     	 System.out.println(e);
									                                 } 
					                                                
					                                                int i=ps.executeUpdate(); 
					                                                if(i>0)
					                                                {
						                                                    count++;
						                                                    System.out.println("inserted successfully row num :::: >>>> "+count);
					                                                }
			                                    	  		}
			                                    	  		catch(Exception ssl)
			                                    	  		{
			                                    	  				System.out.println("Exception while 2 nd table insert ::: "+ssl.getMessage());
			                                    	  				flag=false;
			                                    	  		}
			                                                                                   
		                                          }   
			                                      System.out.println("count :: "+count+" Grid Length :: "+Grid_H_code.length);
		                                      	  if(count==Grid_H_code.length)
		                                      	  {
				                                      		flag=true;		
		                                      	  }
		                                      	  else
		                                      				flag=false;
			                          }                           
			                          catch(Exception e)
			                          {			                        	  		 
			                                      e.getStackTrace();
			                                      System.out.println("exception while inserting 2 nd table "+e.getMessage());
			                                      flag=false;
			                                        
			                          }				                         						              
		             		  }
                      }
                      /*******************************update the records in journal table **************************************************************/
                      if(flag)
                      {		  
	                    	  try
	                    	  { 
                    	  		      ps.close();
		                    	  	  System.out.println("Inside journal update");
				            	 	  ps=con.prepareStatement("select ACCEPTING_JVR_NO,to_char(ACCEPTING_JVR_DATE,'dd-mm-yyyy')as ACCEPTING_JVR_DATE from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SUPPLEMENT_NO=?");
				            	 	  ps.setInt(1,cmbAcc_UnitCode);
				            	 	  ps.setInt(2,cmbOffice_code);
				            	 	  ps.setInt(3,txtCash_year);
				            	 	  ps.setInt(4,txtCash_Month_hid);
				            	 	  ps.setInt(5,accepting_SL_No);
				            	 	  ps.setInt(6,supNo);
				            	 	  rs=ps.executeQuery();
				            	 	  if(rs.next())
				            	 	  {
					             				  System.out.println("Originating JVR NO :: "+rs.getInt("ACCEPTING_JVR_NO"));
					             				  responded_jvr_no=rs.getInt("ACCEPTING_JVR_NO");
					             				  responded_jvr_dt=rs.getString("ACCEPTING_JVR_DATE");
					             				  if(rs.getInt("ACCEPTING_JVR_NO")!=0)
					             				  {
						             						ps.close();
						             						ps=con.prepareStatement("update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=? and SUPPLEMENT_NO=? ");              						
						             						ps.setInt(1,cmbAcc_UnitCode);
						             						ps.setInt(2,cmbOffice_code);
						             						ps.setString(3,responded_jvr_dt);
						             						ps.setInt(4,responded_jvr_no);
						             						ps.setInt(5,supNo);
						             						int upd=ps.executeUpdate();
						             						if(upd>0) {
                                                                            System.out.println("journal_master flag updated successfully"); 
                                                                            ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCEPTING_JVR_NO=0,ACCEPTING_JVR_DATE=null where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and SUPPLEMENT_NO=? ");                                                          
                                                                            ps.setInt(1,cmbAcc_UnitCode);
                                                                            ps.setInt(2,cmbOffice_code);
                                                                            ps.setInt(3,txtCash_year);
                                                                            ps.setInt(4,txtCash_Month_hid);
                                                                            ps.setInt(5,accepting_SL_No);
                                                                            ps.setInt(6,supNo);
                                                                            int upd_again=ps.executeUpdate();
                                                                            if(upd_again>0) {
                                                                                System.out.println("ACCEPTING_JVR_NO is cancelled succesfully:::");
                                                                            }
                                                                            else {
                                                                                flag=false;
                                                                            }
                                                                        }
						                             				
						             						else
						                             				flag=false;
                                                                                    }
					             				  else
					             							flag=true; 
				             		  }                		
				            	 	  else
				            	 	  {
					             				  System.out.println("Record not available");		
					             				  flag=false;
				            	 	  }
                    	  	  		  
                    	  	  }
                    	  	  catch(Exception e)
                    	  	  {
                    	  		  	  System.out.println("Err in updation :: "+e.getMessage());
                    	  		  	  flag=false;
                    	  	  }
		            	 	  
		            	 	  /*********************************************Update Cross Reference Table *****************************************************************/
		            	 	  if(flag)
                          	  {
		            	 		      System.out.println("inside cross reference ");
                                      String txtReferNO_edit="",txtRemak_edit="";         // for cross reference
						              Date txtReferDate_edit=null; 
						              String radAuth_MC="";
						              int txtAuth_By=0;						    						                
						              cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
						              cs1.setInt(1,cmbAcc_UnitCode);
						              cs1.setInt(2,txtCash_year);
						              cs1.setInt(3,txtCash_Month_hid);
						              cs1.setInt(4,accepting_SL_No);
						              cs1.setInt(5,cmbOffice_code);
						              cs1.setDate(6,txtCrea_date);
						              cs1.setString(7,Journal_type);
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
						                	flag=false;                      
						              }
						              else 
						                	flag=true;             
                         	  }
		            	 	  else
                         			  flag=false;
                      }
                      
                      ps.close();
                      System.out.println("flag :: "+flag);
                      if(flag)
                      {
                              System.out.println("b4 commit");
                              con.commit();
                              if(Journal_type.equals("TDAO"))
                              		  sendMessage(response,"The TDA Accepted Sl.No '"+accepting_SL_No+"' has been Updated Successfully ","ok");
                              else
                              		  sendMessage(response,"The TCA Accepted Sl.No '"+accepting_SL_No+"' has been Updated Successfully ","ok");
                      }
                      else
                      {
                              System.out.println("b4 Rollback");
                              con.rollback();
                              if(Journal_type.equals("TDAO"))
  	                    	  		  sendMessage(response,"The TDA Updation Failed ","ok");
  	                          else
  	                  	  			  sendMessage(response,"The TCA Updation Failed ","ok");     
                      }
             }                  
             catch(Exception e) 
             {
		              try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
		              e.getStackTrace();
		              sendMessage(response,"Accepting Sl.No. Updation Failed ","ok");
             }
             finally
             {
		              System.out.println("done");
		              try{con.setAutoCommit(true);con.close(); }catch(SQLException sqle){}
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

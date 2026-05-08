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

public class TDA_Raised_Edit extends HttpServlet
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
        
	    
            int count=0,AccUnitId=0,OfficeId=0,originated_slno=0,supNo=0;
            String xml=null,cmd="",option="",Journal_type="";         
            Date txtCrea_date=null;
      
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            
            try{option=request.getParameter("Option");}
            catch(Exception e){System.out.println(e);}
            
            try{AccUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
            catch(Exception e){System.out.println(e);}
            
            try{OfficeId=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(Exception e){System.out.println(e);}
            
            String[] sd=request.getParameter("txtCrea_date").split("/");
            Calendar c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            System.out.println("txtCrea_date "+request.getParameter("supNo"));
            
            try{supNo=Integer.parseInt(request.getParameter("supNo"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            try{originated_slno=Integer.parseInt(request.getParameter("originated_slno"));}
            catch(Exception e){System.out.println(e);}
                        
            try{Journal_type=request.getParameter("Journal_type");}
            catch(Exception e){System.out.println(e);}
            
            System.out.println("cmd:::"+cmd);
            System.out.println("Journal_type:::"+Journal_type);
            String TDA_TCA="";
            if(Journal_type.equalsIgnoreCase("TCAO")){
       		 TDA_TCA="TCACB','TCAO";
            }else if(Journal_type.equalsIgnoreCase("TDAO")){
       		 TDA_TCA="TDACB','TDAO";
            }
            xml="<response>";
            
            if(cmd.equalsIgnoreCase("load_Voucher_No"))
            {
            	
            		xml=xml+"<command>load_Voucher_No</command>";
            		try
            		{
	            	         sql="select aa.voucher_no from (SELECT\r\n"
	            	         		+ "	mst.ACCOUNTING_UNIT_ID,\r\n"
	            	         		+ "	mst.ACCOUNTING_FOR_OFFICE_ID,\r\n"
	            	         		+ "	mst.VOUCHER_NO,\r\n"
	            	         		+ "	TRF_ACCOUNTING_UNIT_ID,\r\n"
	            	         		+ "CASE\r\n"
	            	         		+ "		\r\n"
	            	         		+ "		WHEN ACCEPTING_SLNO IS NOT NULL THEN\r\n"
	            	         		+ "		ACCEPTING_SLNO ELSE 0 \r\n"
	            	         		+ "	END AS accepting_slno,\r\n"
	            	         		+ "	accepting_date,\r\n"
	            	         		+ "	C.AUTHORIZED_TO,\r\n"
	            	         		+ "CASE\r\n"
	            	         		+ "		\r\n"
	            	         		+ "		WHEN accepting_date IS NOT NULL THEN\r\n"
	            	         		+ "		EXTRACT ( YEAR FROM accepting_date ) ELSE 0 \r\n"
	            	         		+ "	END AS m_year,\r\n"
	            	         		+ "CASE\r\n"
	            	         		+ "		\r\n"
	            	         		+ "		WHEN accepting_date IS NOT NULL THEN\r\n"
	            	         		+ "		EXTRACT ( MONTH FROM accepting_date ) ELSE 0 \r\n"
	            	         		+ "	END AS m_month \r\n"
	            	         		+ "FROM\r\n"
	            	         		+ "	fas_tda_tca_raised_mst mst\r\n"
	            	         		+ "	LEFT JOIN fas_cross_reference C ON mst.ACCOUNTING_UNIT_ID = C.ACCOUNTING_UNIT_ID \r\n"
	            	         		+ "	AND mst.ACCOUNTING_FOR_OFFICE_ID = C.ACCOUNTING_FOR_OFFICE_ID \r\n"
	            	         		+ "	AND mst.CASHBOOK_YEAR = C.CASHBOOK_YEAR \r\n"
	            	         		+ "	AND mst.CASHBOOK_MONTH = C.CASHBOOK_MONTH \r\n"
	            	         		+ "	AND mst.VOUCHER_NO = C.VOUCHER_NO \r\n"
	            	         		+ "WHERE\r\n"
	            	         		+ "	mst.ACCOUNTING_UNIT_ID = ?\r\n"
	            	         		+ "	AND mst.ACCOUNTING_FOR_OFFICE_ID = ? \r\n"
	            	         		+ "	AND mst.VOUCHER_DATE = ? \r\n"
	            	         		+ "	AND mst.STATUS = 'L' \r\n"
	            	         		+ "	AND TDA_OR_TCA in ('"+TDA_TCA+"')"
	            	         		+ "	AND C.CHANGE_NO = 0 \r\n"
	            	         		+ "	AND ( C.DOC_TYPE = ? OR C.DOC_TYPE = ? ) \r\n"
	            	         		+ "	AND ( ACCEPTANCE_STATUS IS NULL OR ACCEPTANCE_STATUS = 'Y' ))aa left join (select * from fas_tda_tca_raised_mst where STATUS = 'L')  mst2 on mst2.accounting_unit_id=aa.TRF_ACCOUNTING_UNIT_ID and mst2.cashbook_year=aa.m_year and mst2.cashbook_month=aa.m_month and  mst2.VOUCHER_NO = aa.ACCEPTING_SLNO AND VOUCHER_DATE = aa.ACCEPTING_DATE ";
	            	         System.out.println("option ::: "+option);
	            	         if(option.equals("Edit"))
	            	        	 sql=sql+" and AUTHORIZED_TO='M'";
	            	         else
	            	        	 sql=sql+" and AUTHORIZED_TO='C'";
	            	         System.out.println("SQL ::: "+sql);
	            	         ps2=con.prepareStatement(sql);
            	    		 ps2.setInt(1,AccUnitId);
            	    		 ps2.setInt(2,OfficeId);
            	    		 ps2.setDate(3,txtCrea_date);
            	    		 ps2.setString(4,Journal_type);
            	    		 if(Journal_type.equals("TCAO")) {
                                 ps2.setString(5,"TCAOB");
                             }
                             else {
                                 ps2.setString(5,"TDAOB"); 
                             }
            	    	//	 ps2.setString(5,Journal_type);
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
            				 System.out.println("");
            		}
            }
            else if(cmd.equalsIgnoreCase("load_Voucher_Details"))
            {
            		xml=xml+"<command>load_Voucher_Details</command>";
            		try
            		{
	            	        sql="select * from\n" + 
	            	         "(\n" + 
	            	         "    select \n" + 
	            	         "        mst.ACCOUNT_HEAD_CODE,\n" + 
	            	         "        mst.TRF_ACCOUNTING_UNIT_ID,\n" +
	            	         "        mst.REASON_FOR_TRANSFER,\n" + 
	            	         "        mst.SUB_LEDGER_TYPE_CODE as mst_sub_type_code,\n" + 
	            	         "        mst.SUB_LEDGER_CODE as mst_sub_code,\n" + 
	            	         "        trim(to_char(mst.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,\n" + 
	            	         "        mst.PARTICULARS,\n" + 
	            	         "        trn.SL_NO,\n" + 
	            	         "        trn.ACCOUNT_HEAD_CODE as trn_acc_head,\n" + 
	            	         "        acc_mst.ACCOUNT_HEAD_DESC," + 
	            	         "        trn.CR_DR_INDICATOR,\n" + 
	            	         "        trn.SUB_LEDGER_TYPE_CODE as trn_sub_type_code,\n" + 
	            	         "        trn.SUB_LEDGER_CODE as trn_sub_code,\n" +
	            	         "		  trn.PAID_TO," + 
	            	         "        trim(to_char(trn.AMOUNT,'99999999999999.99')) as AMOUNT,\n" + 
	            	         "        trn.PARTICULARS as trn_particulars,\n" + 
	            	         "        trn.MBOOK_NO ,	\n" + 
	            	         "        trn.MBOOK_PAGENO, \n" + 
	            	         "         to_char(trn.MBOOK_DATE,'dd/mm/yyyy') as bkDate        \n" +
	            	         "    from\n" + 
	            	         "        FAS_TDA_TCA_RAISED_MST mst,\n" + 
	            	         "        FAS_TDA_TCA_RAISED_TRN trn,\n" +
	            	         "        COM_MST_ACCOUNT_HEADS acc_mst" +
	            	         "    where\n" + 
	            	         "        mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and\n" + 
	            	         "        mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and\n" + 
	            	         "        mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and \n" + 
	            	         "        mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
	            	         "        mst.VOUCHER_NO=trn.VOUCHER_NO and\n" +
	            	         "        trn.ACCOUNT_HEAD_CODE=acc_mst.ACCOUNT_HEAD_CODE and " +	            	                                              
	            	         "        mst.ACCOUNTING_UNIT_ID=? and\n" + 
	            	         "        mst.ACCOUNTING_FOR_OFFICE_ID=? and\n" + 
	            	         "        mst.VOUCHER_NO=? and\n" + 
	            	         "        mst.VOUCHER_DATE=? and \n" +
	            	         "        mst.TDA_OR_TCA in ('"+TDA_TCA+"')  " +	            	        		     
	            	         ")aa left outer join\n" + 
	            	         "(\n" + 
	            	         "    select SUB_LEDGER_TYPE_DESC,SUB_LEDGER_TYPE_CODE as sub_code from COM_MST_SL_TYPES\n" + 
	            	         ")bb on aa.trn_sub_type_code=bb.sub_code order by SL_NO";
	            	        
	            	        System.out.println("Sql_details-----<>"+sql);
	            	        
	            	         ps2=con.prepareStatement(sql);
	            	         ps2.setInt(1,AccUnitId);
	            	         ps2.setInt(2,OfficeId);
	            	         ps2.setInt(3,originated_slno);
	            	         ps2.setDate(4,txtCrea_date);
	            	      //   ps2.setString(5,Journal_type);
                             rs2=ps2.executeQuery();   
                             while(rs2.next()) 
                             {
                                     xml+= "<head_code>"+ rs2.getInt("ACCOUNT_HEAD_CODE")+"</head_code>";	                                     
                                     xml+= "<unit_code>"+ rs2.getInt("TRF_ACCOUNTING_UNIT_ID") +"</unit_code>";
                                     xml+= "<reason_for_transfer>"+ rs2.getString("REASON_FOR_TRANSFER") +"</reason_for_transfer>";
                                     xml+= "<mst_sub_type_code>"+ rs2.getInt("mst_sub_type_code") +"</mst_sub_type_code>";
                                     xml+= "<mst_sub_code>"+ rs2.getInt("mst_sub_code") +"</mst_sub_code>";
                                     xml+= "<total_amount>"+ rs2.getString("TOTAL_AMOUNT") +"</total_amount>";
                                     xml+= "<particulars>"+ rs2.getString("PARTICULARS") +"</particulars>";
                                     xml+= "<trn_acc_head>"+ rs2.getInt("trn_acc_head") +"</trn_acc_head>";
                                     xml+= "<head_desc>"+rs2.getString("ACCOUNT_HEAD_DESC")+"</head_desc>";	
                                     xml+= "<cr_dr_indicator>"+ rs2.getString("CR_DR_INDICATOR") +"</cr_dr_indicator>";
                                     xml+= "<trn_sub_type_code>"+ rs2.getInt("trn_sub_type_code") +"</trn_sub_type_code>";
                                     
                                    //  xml+= "<trn_sub_type_desc> <![CDATA["+ rs2.getString("SUB_LEDGER_TYPE_DESC") +"]]></trn_sub_type_desc>";
                                     xml+= "<trn_sub_type_desc> "+ rs2.getString("SUB_LEDGER_TYPE_DESC") +"</trn_sub_type_desc>";
                                     xml+= "<trn_sub_code>"+ rs2.getInt("trn_sub_code") +"</trn_sub_code>";
                                     xml+= "<trn_bookNo>"+ rs2.getInt("MBOOK_NO") +"</trn_bookNo>";
                                     xml+= "<trn_bookPageno>"+ rs2.getInt("MBOOK_PAGENO") +"</trn_bookPageno>";
                                     xml+= "<trn_bookDate>"+ rs2.getString("bkDate") +"</trn_bookDate>";
                                  
                                     if(rs2.getInt("trn_sub_code")==0)
                                    	 	xml+= "<trn_sub_desc>--</trn_sub_desc>";
                                     else
                                    	 	xml+= "<trn_sub_desc><![CDATA["+ rs2.getString("PAID_TO") +"]]></trn_sub_desc>";
                                     xml+= "<amount>"+ rs2.getString("AMOUNT") +"</amount>";
                                     xml+= "<trn_particulars>"+ rs2.getString("trn_particulars") +"</trn_particulars>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
            		}
            		catch(Exception e)
            		{
            				System.out.println("Err in load_Voucher_Details :: "+e.getMessage());
            		}
            }
            
            if(cmd.equalsIgnoreCase("load_Vr_No"))
            {
            	
            		xml=xml+"<command>load_Vr_No</command>";
            		try
            		{
	            	         sql="select aa.voucher_no from (SELECT\r\n"
	            	         		+ "	mst.ACCOUNTING_UNIT_ID,\r\n"
	            	         		+ "	mst.ACCOUNTING_FOR_OFFICE_ID,\r\n"
	            	         		+ "	mst.VOUCHER_NO,\r\n"
	            	         		+ "	TRF_ACCOUNTING_UNIT_ID,\r\n"
	            	         		+ "CASE\r\n"
	            	         		+ "		\r\n"
	            	         		+ "		WHEN ACCEPTING_SLNO IS NOT NULL THEN\r\n"
	            	         		+ "		ACCEPTING_SLNO ELSE 0 \r\n"
	            	         		+ "	END AS accepting_slno,\r\n"
	            	         		+ "	accepting_date,\r\n"
	            	         		+ "	C.AUTHORIZED_TO,\r\n"
	            	         		+ "CASE\r\n"
	            	         		+ "		\r\n"
	            	         		+ "		WHEN accepting_date IS NOT NULL THEN\r\n"
	            	         		+ "		EXTRACT ( YEAR FROM accepting_date ) ELSE 0 \r\n"
	            	         		+ "	END AS m_year,\r\n"
	            	         		+ "CASE\r\n"
	            	         		+ "		\r\n"
	            	         		+ "		WHEN accepting_date IS NOT NULL THEN\r\n"
	            	         		+ "		EXTRACT ( MONTH FROM accepting_date ) ELSE 0 \r\n"
	            	         		+ "	END AS m_month \r\n"
	            	         		+ "FROM\r\n"
	            	         		+ "	fas_tda_tca_raised_mst mst\r\n"
	            	         		+ "	LEFT JOIN fas_cross_reference C ON mst.ACCOUNTING_UNIT_ID = C.ACCOUNTING_UNIT_ID \r\n"
	            	         		+ "	AND mst.ACCOUNTING_FOR_OFFICE_ID = C.ACCOUNTING_FOR_OFFICE_ID \r\n"
	            	         		+ "	AND mst.CASHBOOK_YEAR = C.CASHBOOK_YEAR \r\n"
	            	         		+ "	AND mst.CASHBOOK_MONTH = C.CASHBOOK_MONTH \r\n"
	            	         		+ "	AND mst.VOUCHER_NO = C.VOUCHER_NO \r\n"
	            	         		+ "WHERE\r\n"
	            	         		+ "	mst.ACCOUNTING_UNIT_ID = ?\r\n"
	            	         		+ "	AND mst.ACCOUNTING_FOR_OFFICE_ID = ? \r\n"
	            	         		+ "	AND mst.VOUCHER_DATE = ? \r\n"
	            	         		+ "	AND mst.STATUS = 'L' \r\n"
	            	         		+ "	AND TDA_OR_TCA in ('"+TDA_TCA+"')\r\n"
	            	         		+ "	AND C.CHANGE_NO = 0 \r\n"
	            	         		+ "	AND ( C.DOC_TYPE = ? OR C.DOC_TYPE = ?::varchar ) \r\n"
	            	         		+ "	AND ( ACCEPTANCE_STATUS IS NULL OR ACCEPTANCE_STATUS = 'Y' ))aa left join (select * from fas_tda_tca_raised_mst where STATUS = 'L')  mst2 on mst2.accounting_unit_id=aa.TRF_ACCOUNTING_UNIT_ID and mst2.cashbook_year=aa.m_year and mst2.cashbook_month=aa.m_month and  mst2.VOUCHER_NO = aa.ACCEPTING_SLNO AND VOUCHER_DATE = aa.ACCEPTING_DATE  ";
	            	         System.out.println("option ::: "+option);
	            	         if(option.equals("Edit"))
	            	        	 sql=sql+" and AUTHORIZED_TO='M'";
	            	         else
	            	        	 sql=sql+" and AUTHORIZED_TO='C'";
	            	         System.out.println("SQL ::: "+sql);
	            	         ps2=con.prepareStatement(sql);
            	    		 ps2.setInt(1,AccUnitId);
            	    		 ps2.setInt(2,OfficeId);
            	    		 ps2.setDate(3,txtCrea_date);
            	    		 ps2.setString(4,Journal_type);
            	    		 ps2.setInt(5,supNo);
            	    	//	 ps2.setString(5,Journal_type);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<voucher_no>"+ rs2.getInt("VOUCHER_NO") +"</voucher_no>";	 			                		                 
                                     count++;
                             }					              
                             if(count==0)
                             {
                                     xml+="<flag>NoData</flag>";		
                             }
                             else     
                             {
                                     xml+="<flag>success</flag>";
                             }
            		}
            		catch(Exception e)
            		{
            				 System.out.println("");
            		}
            }
            else if(cmd.equalsIgnoreCase("load_Vr_Details"))
            {
            		xml=xml+"<command>load_Vr_Details</command>";
            		try
            		{
	            	        sql="select * from\n" + 
	            	         "(\n" + 
	            	         "    select \n" + 
	            	         "        mst.ACCOUNT_HEAD_CODE,\n" + 
	            	         "        mst.TRF_ACCOUNTING_UNIT_ID,\n" +
	            	         "        mst.REASON_FOR_TRANSFER,\n" + 
	            	         "        mst.SUB_LEDGER_TYPE_CODE as mst_sub_type_code,\n" + 
	            	         "        mst.SUB_LEDGER_CODE as mst_sub_code,\n" + 
	            	         "        trim(to_char(mst.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,\n" + 
	            	         "        mst.PARTICULARS,\n" + 
	            	         "        trn.SL_NO,\n" + 
	            	         "        trn.ACCOUNT_HEAD_CODE as trn_acc_head,\n" + 
	            	         "        acc_mst.ACCOUNT_HEAD_DESC," + 
	            	         "        trn.CR_DR_INDICATOR,\n" + 
	            	         "        trn.SUB_LEDGER_TYPE_CODE as trn_sub_type_code,\n" + 
	            	         "        trn.SUB_LEDGER_CODE as trn_sub_code,\n" +
	            	         "		  trn.PAID_TO," + 
	            	         "        trim(to_char(trn.AMOUNT,'99999999999999.99')) as AMOUNT,\n" + 
	            	         "        trn.PARTICULARS as trn_particulars,\n" + 
	            	         "        trn.MBOOK_NO ,	\n" + 
	            	         "        trn.MBOOK_PAGENO, \n" + 
	            	         "         to_char(trn.MBOOK_DATE,'dd/mm/yyyy') as bkDate        \n" +
	            	         "    from\n" + 
	            	         "        FAS_TDA_TCA_RAISED_MST mst,\n" + 
	            	         "        FAS_TDA_TCA_RAISED_TRN trn,\n" +
	            	         "        COM_MST_ACCOUNT_HEADS acc_mst" +
	            	         "    where\n" + 
	            	         "        mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and\n" + 
	            	         "        mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and\n" + 
	            	         "        mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and \n" + 
	            	         "        mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
	            	         "        mst.VOUCHER_NO=trn.VOUCHER_NO and\n" +
	            	         "        trn.ACCOUNT_HEAD_CODE=acc_mst.ACCOUNT_HEAD_CODE and " +	            	                                              
	            	         "        mst.ACCOUNTING_UNIT_ID=? and\n" + 
	            	         "        mst.ACCOUNTING_FOR_OFFICE_ID=? and\n" + 
	            	         "        mst.VOUCHER_NO=? and\n" + 
	            	         "        mst.VOUCHER_DATE=? and \n" +
	            	         "	      MST.SUPPLEMENT_NO           =? and\n" +
	            	         "        mst.TDA_OR_TCA in ('"+TDA_TCA+"')  " +	            	        		     
	            	         ")aa left outer join\n" + 
	            	         "(\n" + 
	            	         "    select SUB_LEDGER_TYPE_DESC,SUB_LEDGER_TYPE_CODE as sub_code from COM_MST_SL_TYPES\n" + 
	            	         ")bb on aa.trn_sub_type_code=bb.sub_code order by SL_NO";
	            	         ps2=con.prepareStatement(sql);
	            	         ps2.setInt(1,AccUnitId);
	            	         ps2.setInt(2,OfficeId);
	            	         ps2.setInt(3,originated_slno);
	            	         ps2.setDate(4,txtCrea_date);
	            	         ps2.setInt(5,supNo);
	            	      //   ps2.setString(5,Journal_type);
                             rs2=ps2.executeQuery();   
                             while(rs2.next()) 
                             {
                                     xml+= "<head_code>"+ rs2.getInt("ACCOUNT_HEAD_CODE")+"</head_code>";	                                     
                                     xml+= "<unit_code>"+ rs2.getInt("TRF_ACCOUNTING_UNIT_ID") +"</unit_code>";
                                     xml+= "<reason_for_transfer>"+ rs2.getString("REASON_FOR_TRANSFER") +"</reason_for_transfer>";
                                     xml+= "<mst_sub_type_code>"+ rs2.getInt("mst_sub_type_code") +"</mst_sub_type_code>";
                                     xml+= "<mst_sub_code>"+ rs2.getInt("mst_sub_code") +"</mst_sub_code>";
                                     xml+= "<total_amount>"+ rs2.getString("TOTAL_AMOUNT") +"</total_amount>";
                                     xml+= "<particulars>"+ rs2.getString("PARTICULARS") +"</particulars>";
                                     xml+= "<trn_acc_head>"+ rs2.getInt("trn_acc_head") +"</trn_acc_head>";
                                     xml+= "<head_desc>"+rs2.getString("ACCOUNT_HEAD_DESC")+"</head_desc>";	
                                     xml+= "<cr_dr_indicator>"+ rs2.getString("CR_DR_INDICATOR") +"</cr_dr_indicator>";
                                     xml+= "<trn_sub_type_code>"+ rs2.getInt("trn_sub_type_code") +"</trn_sub_type_code>";
                                     
                                              //  xml+= "<trn_sub_type_desc> <![CDATA["+ rs2.getString("SUB_LEDGER_TYPE_DESC") +"]]></trn_sub_type_desc>";
                                     xml+= "<trn_sub_type_desc> "+ rs2.getString("SUB_LEDGER_TYPE_DESC") +"</trn_sub_type_desc>";
                                     xml+= "<trn_sub_code>"+ rs2.getInt("trn_sub_code") +"</trn_sub_code>";
                                     xml+= "<trn_bookNo>"+ rs2.getInt("MBOOK_NO") +"</trn_bookNo>";
                                     xml+= "<trn_bookPageno>"+ rs2.getInt("MBOOK_PAGENO") +"</trn_bookPageno>";
                                     xml+= "<trn_bookDate>"+ rs2.getString("bkDate") +"</trn_bookDate>";
                                  
                                     if(rs2.getInt("trn_sub_code")==0)
                                    	 	xml+= "<trn_sub_desc>--</trn_sub_desc>";
                                     else
                                    	 	xml+= "<trn_sub_desc><![CDATA["+ rs2.getString("PAID_TO") +"]]></trn_sub_desc>";
                                     xml+= "<amount>"+ rs2.getString("AMOUNT") +"</amount>";
                                     xml+= "<trn_particulars>"+ rs2.getString("trn_particulars") +"</trn_particulars>";
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
            		}
            		catch(Exception e)
            		{
            				System.out.println("Err in load_Voucher_Details :: "+e.getMessage());
            		}
            }
            
            
            
            else if(cmd.equalsIgnoreCase("loadSLType"))
            {
            	    xml=xml+"<command>loadSLType</command>";
            	    try	
            	    {			        	 			  	                 		  
            	    		 sql="select trn.ACCOUNTING_UNIT_OFFICE_ID,mst.OFFICE_SHORT_NAME from FAS_MST_ACCT_UNITS trn,COM_MST_OFFICES mst where trn.ACCOUNTING_UNIT_OFFICE_ID=mst.OFFICE_ID and trn.ACCOUNTING_UNIT_ID=?";            	    		 
            	    		 ps2=con.prepareStatement(sql);
            	    		 ps2.setInt(1,AccUnitId);
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
	         PreparedStatement ps=null,ps1=null,ps2=null,ps4=null;
	         String xml="";
	         Statement st=null;
	         ResultSet rs=null;
	         Date bkDate=null;
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
	              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	              Class.forName(strDriver.trim());
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
	             int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtDebitHead=0;
	             int count=0,cmbMas_SL_type=0,cmbMas_SL_Code=0,cmbReason=0,originated_jvr_no=0;
	             double txtTotalAmt=0;
	             Date txtCrea_date=null;
	             String txtRemarks="",paid_to="",Journal_type="",cr_dr_indicator="",sql="",oridinated_dt=null;
	             Boolean flag=true;
	                                     // changes here
	             String update_user=(String)session.getAttribute("UserId");
	             long l=System.currentTimeMillis();
	             Timestamp ts=new Timestamp(l);                      
	             int errcode=0;
	             int Originated_SL_No=0;
	             CallableStatement cs1=null;
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                                
	             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	          //   System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	             
	             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	        //    System.out.println("cmbOffice_code "+cmbOffice_code);
	             
	             String[] sd=request.getParameter("txtCrea_date").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	             java.util.Date d=c.getTime();
	             txtCrea_date=new Date(d.getTime());
	     
	             try{txtCash_year=Integer.parseInt(sd[2]);}
	             catch(Exception e){System.out.println("exception"+e );}
	        //    System.out.println("txtCash_year "+txtCash_year);
	             
	             try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	             catch(Exception e){System.out.println("exception"+e );}
	         //   System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	          
	             try{Originated_SL_No=Integer.parseInt(request.getParameter("originated_slno"));}
	 	         catch(NumberFormatException e){System.out.println("exception"+e );}
	 	  //    System.out.println("Originated_SL_No "+Originated_SL_No);
	           
	             try{cmbReason=Integer.parseInt(request.getParameter("cmbReason"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	        //    System.out.println("cmbReason "+cmbReason);
	             
	             try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	       //     System.out.println("txtUnitId "+txtUnitId);
	             
	             try{txtDebitHead=Integer.parseInt(request.getParameter("txtDebitHead"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	          //  System.out.println("txtDebitHead "+txtDebitHead);
	            
	             try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	     //       System.out.println("cmbMas_SL_type "+cmbMas_SL_type);
	            
	             try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	       //     System.out.println("cmbMas_SL_Code "+cmbMas_SL_Code);
	             
	             try{paid_to=request.getParameter("txtMas_PaidTo");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	       //     System.out.println("paid_to "+paid_to);
	            
	                                     
	             try{txtRemarks=request.getParameter("txtRemarks");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	      //       System.out.println("txtRemarks "+txtRemarks);
	             
	             try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
	             catch(Exception e){System.out.println("exception"+e );}
	         //    System.out.println("txtAmount "+txtTotalAmt);
	             
	             try{Journal_type=request.getParameter("Journal_type_one");}
	             catch(Exception e){System.out.println("Journal_type "+e );}
	         //    System.out.println("Journal_type "+Journal_type);
	             if(Journal_type.equals("TDAO"))
	            	 	  cr_dr_indicator="DR";
	             else
	            	 	  cr_dr_indicator="CR";
	             
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                 
	             try 
	             {   
	                      con.clearWarnings();
	                      con.setAutoCommit(false);
	                      ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCOUNT_HEAD_CODE=?,PAID_TO=?,TRF_ACCOUNTING_UNIT_ID=?,REASON_FOR_TRANSFER=?,SUB_LEDGER_TYPE_CODE=?,SUB_LEDGER_CODE=?,TOTAL_AMOUNT=?,PARTICULARS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and TDA_OR_TCA=?");
	                                           
	                      ps.setInt(1,txtDebitHead);                      
	                      ps.setString(2,paid_to);
	                      ps.setInt(3,txtUnitId);
	                      ps.setInt(4,cmbReason);
	                      ps.setInt(5,cmbMas_SL_type);
	                      ps.setInt(6,cmbMas_SL_Code);
	                      ps.setDouble(7,txtTotalAmt);
	                      ps.setString(8,txtRemarks);
	                      ps.setString(9,"L");
	                      ps.setString(10,update_user);
	                      ps.setTimestamp(11,ts);
	                      ps.setInt(12,cmbAcc_UnitCode);
	                      ps.setInt(13,cmbOffice_code);
	                      ps.setInt(14,txtCash_year);
	                      ps.setInt(15,txtCash_Month_hid);
	                      ps.setInt(16,Originated_SL_No);
	                      ps.setString(17,Journal_type);
	                      errcode=ps.executeUpdate();
	                      if(errcode==0)
	                      {         
	                          System.out.println("redirect");
	                          sendMessage(response,"The TDA/TCA Updation Failed ","ok");                                      
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
                 			  " and voucher_no=? 						    ";	                 			
         	 
				         	  ps=con.prepareStatement(sql_del);
				              ps.setInt(1,cmbAcc_UnitCode);
				              ps.setInt(2,cmbOffice_code);
				              ps.setInt(3,txtCash_year);
				              ps.setInt(4,txtCash_Month_hid);
				              ps.setInt(5,Originated_SL_No);
				              errcode=ps.executeUpdate();    
				              if(errcode==0)
		                      {         
			                       	  System.out.println("Can not delete old records for this voucher in transaction table");			                       
		                      }
				              else
				              {				            
				            	  	  ps.close();                
			                                            
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
			                          int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,bookPageNo=0,bookNo=0;
			                          double txtsub_Amount=0;
			                          try
			                          {
			                        	 
			                                      sql="insert into FAS_TDA_TCA_RAISED_TRN(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE,PAID_TO, AMOUNT, PARTICULARS, UPDATED_BY_USERID, UPDATED_DATE,MBOOK_NO,MBOOK_PAGENO,MBOOK_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
			                                      ps=con.prepareStatement(sql);
			                                      for(int k=0;k<Grid_H_code.length;k++) 
			                                      {                                                                                                        
			                                                cmbSL_type=0;cmbSL_Code=0;
			                                                txtAcc_HeadCode=0;  txtsub_Amount=0; 
			                                                txtsub_Amount=0;
			                                                                                  
			                                                SL_NO++;
			                                                ps.setInt(1,cmbAcc_UnitCode);     
			                                                ps.setInt(2,cmbOffice_code);    
			                                                ps.setInt(3,txtCash_year);
			                                                ps.setInt(4,txtCash_Month_hid);
			                                                ps.setInt(5,Originated_SL_No);       
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
			                                               if(txtAcc_HeadCode==900108)
			                                               { ps.setInt(10,cmbMas_SL_Code);
			                                               ps.setString(11,paid_to);}
			                                               else
			                                                {ps.setInt(10,cmbSL_Code);
			                                                ps.setString(11,Trn_Paid_To[k]);}
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
			                                               System.out.println("dateeeeee"+grid_bookdate[k]);
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
											                                 sd=grid_bookdate[k].split("/");
											                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
											                                 d=c.getTime();
											                                 bkDate=new Date(d.getTime());
											                                 ps.setDate(18,bkDate);
											                                
									                                 }

								    	                    	 
							                                 }
							                                 catch(Exception e) {
							                                     	 System.out.println("eeeeee"+e);
							                                 } 
							                               
			                                                int i=ps.executeUpdate(); 
			                                                if(i>0)
			                                                    count++;
			                                               // System.out.println("SQL::::insert into FAS_ADJUSTMENT_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,HO_REF_NUMBER,HO_REF_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values("+cmbAcc_UnitCode+","+cmbOffice_code+","+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+SL_NO+","+txtAcc_HeadCode+",'"+rad_sub_CR_DR+"',"+cmbSL_type+","+cmbSL_Code+","+ref_num+",?,?,?,?)");                                    
			                                        }
			                                     //   System.out.println("Length:  "+count+" "+Grid_H_code.length);
			                                        if(count==Grid_H_code.length)
			                                        {			                                        		
				                                        	if(Journal_type.equalsIgnoreCase("TDAO")||Journal_type.equalsIgnoreCase("TCAO"))
				                                        	{
				                                        			ps.close();
					                                        		ps=con.prepareStatement("select ORGINATING_JVR_NO,to_char(ORGINATING_JVR_DATE,'dd-mm-yyyy')as ORGINATING_JVR_DATE from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
					                                        		ps.setInt(1,cmbAcc_UnitCode);
					                                        		ps.setInt(2,cmbOffice_code);
					                                        		ps.setInt(3,txtCash_year);
					                                        		ps.setInt(4,txtCash_Month_hid);
					                                        		ps.setInt(5,Originated_SL_No);
					                                        		rs=ps.executeQuery();
					                                        		if(rs.next())
					                                        		{
					                                        			//	System.out.println("Originating JVR NO :: "+rs.getInt("ORGINATING_JVR_NO"));
					                                        				originated_jvr_no=rs.getInt("ORGINATING_JVR_NO");
					                                        				oridinated_dt=rs.getString("ORGINATING_JVR_DATE");
					                                        				if(rs.getInt("ORGINATING_JVR_NO")!=0)
					                                        				{
					                                        						ps.close();
					                                        						ps=con.prepareStatement("update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=to_date(?,'dd-mm-yy') and VOUCHER_NO=?");
					                                        					//	System.out.println(cmbAcc_UnitCode);                						
					                                        						ps.setInt(1,cmbAcc_UnitCode);
					                                        					//	System.out.println(cmbOffice_code);
					                                                        		ps.setInt(2,cmbOffice_code);
					                                                        	//	System.out.println(oridinated_dt);
					                                                        		ps.setString(3,oridinated_dt);
					                                                        	//	System.out.println(originated_jvr_no);
					                                                        		ps.setInt(4,originated_jvr_no);
					                                                        		int upd=ps.executeUpdate();
					                                                        		if(upd>0)
					                                                        		{
					                                                        				System.out.println("journal_master flag updated successfully");
					                                                        				  ps4=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ORGINATING_JVR_NO=0,ORGINATING_JVR_DATE=null where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and TDA_OR_TCA=?");
					                                                        				  ps4.setInt(1,cmbAcc_UnitCode);
					                                                        				  ps4.setInt(2,cmbOffice_code);
					                                                        				  ps4.setInt(3,txtCash_year);
					                                                        				  ps4.setInt(4,txtCash_Month_hid);
					                                                        				  ps4.setInt(5,Originated_SL_No);
					                                                        				  ps4.setString(6,Journal_type);
					                                              	                      int tda_up=ps4.executeUpdate();
					                                              	                      if(tda_up==0)
					                                              	                      {
					                                              	                    	flag=false;
					                                              	                      }
					                                              	                      else
					                                              	                      {
					                                              	                    	flag=true; 
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
					                                        		}
				                                        	}
			                                        		
				                                        	if(flag)
				                                        	{
						                                        	String txtReferNO_edit="",txtRemak_edit="";         // for cross reference
						    						                Date txtReferDate_edit=null; 
						    						                String radAuth_MC="";
						    						                int txtAuth_By=0;						    						                
						    						                cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
						    						                cs1.setInt(1,cmbAcc_UnitCode);
						    						                cs1.setInt(2,txtCash_year);
						    						                cs1.setInt(3,txtCash_Month_hid);
						    						                cs1.setInt(4,Originated_SL_No);
						    						                cs1.setInt(5,cmbOffice_code);
						    						                cs1.setDate(6,txtCrea_date);
						    						                cs1.setString(7,Journal_type);
						    						                cs1.setString(8,txtReferNO_edit);
						    						                cs1.setDate(9,txtReferDate_edit);
						    						                cs1.setString(10,txtRemak_edit);
						    						                cs1.setInt(11,txtAuth_By);                                                      
						    						                cs1.setString(12,"insert");
						    						                cs1.registerOutParameter(13,java.sql.Types.NUMERIC);  
						    						                cs1.setInt(13,0);
						    						                cs1.setString(14,update_user);
						    						                cs1.setTimestamp(15,ts);     
						    						                cs1.setString(16,radAuth_MC);
						    						                cs1.execute();                                            // insertion into cross reference table
						    						                //errcode=cs1.getInt(13);	
						    						                 errcode = cs1.getBigDecimal(13).intValue();   
						    						                
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
			                                        else
			                                        		flag=false;
			                            }
			                            catch(Exception e)
			                            {
			                                        e.getStackTrace();
			                                        System.out.println("Err in value setting for insertion:::"+e.getMessage());
			                                        con.rollback();
			                                        flag=false;
			                            }
			                            ps.close();
			                            //System.out.println("flag :: "+flag);
			                            if(flag)
			                            {
			                                    //    System.out.println("b4 commit");
			                                        con.commit();
			                                        if(Journal_type.equals("TDAO"))
			                                        		sendMessage(response,"The TDA Originated Sl.No '"+Originated_SL_No+"' has been Updated Successfully ","ok");
			                                        else
			                                        		sendMessage(response,"The TCA Originated Sl.No '"+Originated_SL_No+"' has been Updated Successfully ","ok");
			                            }
			                            else
			                            {
			                                      //  System.out.println("b4 Rollback");
			                                        con.rollback();
			                                        if(Journal_type.equals("TDAO"))
			            	                    	  		sendMessage(response,"The TDA Updation Failed ","ok");
			            	                        else
			            	                  	  			sendMessage(response,"The TCA Updation Failed ","ok");     
			                            }
			                            
			                  }
	                      }
	                     
	                  }
	                  catch(Exception e) 
	                  {
	                      try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                      e.getStackTrace();
	                      if(Journal_type.equals("TDAO"))
	                    	  sendMessage(response,"The TDA Updation Failed ","ok");
	                      else
	                  	  	  sendMessage(response,"The TCA Updation Failed ","ok");                    	  	
	                      System.out.println("Exception occur due to "+e);
	                      
	                  }
	                  finally
	                  {
	                  //    System.out.println("done");
	                      try{con.setAutoCommit(true);  }catch(SQLException sqle){}
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

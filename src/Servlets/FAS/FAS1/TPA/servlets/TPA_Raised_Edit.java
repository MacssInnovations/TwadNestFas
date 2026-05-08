package Servlets.FAS.FAS1.TPA.servlets;

import Servlets.FAS.FAS1.CommonControls2.servlets.GL_SL_Checking_for_TPA;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.sql.CallableStatement;
import java.text.DecimalFormat;

import javax.servlet.*;
import javax.servlet.http.*;

public class TPA_Raised_Edit extends HttpServlet
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
	      PreparedStatement ps2=null,ps3=null;        
	      ResultSet rs2=null,rs3=null;
	      String sql=null;
	        	      
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
        
            DecimalFormat df=new DecimalFormat("#0.00");
            int count=0,AccUnitId=0,officeCode=0,cashBookMonth=0,cashBookYear=0,OfficeId=0,originated_slno=0,txtCash_year=0,txtCash_Month_hid=0;
            String xml=null,cmd=null,option=null,tpa_type=null;          
            Date txtCrea_date=null;
            String[] sd=null;
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            System.out.println("cmd :: "+cmd);
            
            try{option=request.getParameter("Option");}
            catch(Exception e){System.out.println("Exception in getting option :"+option);}
            System.out.println("option :: "+option);
            
            try{AccUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
            catch(Exception e){System.out.println(e);}
            System.out.println("AccUnitId :: "+AccUnitId);
            
            try{OfficeId=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(Exception e){System.out.println(e);}
            System.out.println("OfficeId :: "+OfficeId);
            
            try
            {
	            sd=request.getParameter("txtCrea_date").split("/");
	            Calendar c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtCrea_date=new Date(d.getTime());
	            System.out.println("txtCrea_date "+txtCrea_date);
            }
            catch(Exception e)
            {System.out.println("Err in date selection :: "+e.getMessage());}
            
            try{txtCash_year=Integer.parseInt(sd[2]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_year "+txtCash_year);
            
            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
            
            try{originated_slno=Integer.parseInt(request.getParameter("adviceNumber"));}
            catch(Exception e){System.out.println(e);}
            System.out.println("originated_slno "+originated_slno);
                        
            try{tpa_type=request.getParameter("tpa_type");}
            catch(Exception e){System.out.println(e);}
            System.out.println("tpa_type "+tpa_type);
            
            System.out.println("cmd:::"+cmd);
            xml="<response>";
            if(cmd.equalsIgnoreCase("load_Voucher_No"))
            {     
                    xml=xml+"<command>load_Voucher_No</command>";
            		try
            		{
	                        sql=" select \n" + 
	                        "     mst.VOUCHER_NO\n" + 
	                        " from\n" + 
	                        "     FAS_TPA_MASTER mst,\n" + 
	                        "     FAS_CROSS_REFERENCE c \n" + 
	                        " where \n" + 
	                        "     mst.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and	\n" + 
	                        "     mst.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID and	\n" + 
	                        "     mst.CASHBOOK_YEAR=c.CASHBOOK_YEAR and	\n" + 
	                        "     mst.CASHBOOK_MONTH=c.CASHBOOK_MONTH and \n" + 
	                        "     mst.VOUCHER_NO=c.VOUCHER_NO and	      \n" + 
	                        "     mst.ACCOUNTING_UNIT_ID=? and\n" + 
	                        "     mst.ACCOUNTING_FOR_OFFICE_ID=? and\n" + 
	                        "     mst.VOUCHER_DATE=? and\n" + 
	                        "     mst.STATUS='L' and  \n" + 
	                        "     TPA_TYPE=? and \n" + 
	                        "     c.CHANGE_NO=0  and  +\n" + 
	                        "     c.DOC_TYPE=? and ACCEPTANCE_STATUS is null ";
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
	        	    		 ps2.setString(4,tpa_type);
	        	    		 ps2.setString(5,tpa_type);
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
                             System.out.println("Exception in load_Voucher_No..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            else if(cmd.equalsIgnoreCase("load_Voucher_Details"))
            {
	            	xml=xml+"<command>load_Voucher_Details</command>";
	        		try
	        		{
		                    sql="select \n" + 
		                    "   mst.TRF_ACCOUNTING_UNIT_ID,\n" + 
		                    "   mst.REASON_FOR_TRANSFER,\n" + 
		                    "   mst.PARTICULARS,\n" + 
		                    "   mst.CASHBOOK_YEAR,\n" + 
		                    "   mst.CASHBOOK_Month,\n" +
		                    "   trn.ACCOUNT_HEAD_CODE,\n" + 
		                    "   acc_mst.account_head_desc,\n" + 
		                    "   trn.CR_DR_INDICATOR,\n" + 
		                    "   trn.SL_NO,\n" + 
		                    "   trn.SUB_LEDGER_TYPE_CODE, \n" + 
		                    "   trn.SUB_LEDGER_CODE,\n" + 
		                    "   trn.AMOUNT,\n" + 
		                    "   trn.PARTICULARS as trn_particulars,\n" + 
		                    "   SUB_LEDGER_TYPE_DESC\n" + 
		                    "from \n" + 
		                    "  FAS_TPA_MASTER mst inner join FAS_TPA_TRANSACTION trn on mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and mst.VOUCHER_NO=trn.VOUCHER_NO\n" + 
		                    "  left outer join COM_MST_SL_TYPES sl_mst on trn.SUB_LEDGER_TYPE_CODE=sl_mst.SUB_LEDGER_TYPE_CODE\n" + 
		                    "  left outer join com_mst_account_heads acc_mst on trn.account_head_code=acc_mst.account_head_code\n" + 
		                    "where\n" + 
		                    "  mst.ACCOUNTING_UNIT_ID=? and\n" + 
		                    "  mst.ACCOUNTING_FOR_OFFICE_ID=? and\n" + 
		                    "  mst.CASHBOOK_MONTH=? and\n" + 
		                    "  mst.CASHBOOK_YEAR=? and\n" + 
		                    "  mst.VOUCHER_NO=? \n" + 
		                    "order by SL_NO";
	            	         System.out.println("SQL ::: "+sql);
	            	         ps2=con.prepareStatement(sql);
	        	    		 ps2.setInt(1,AccUnitId);
	        	    		 
	        	    		 ps2.setInt(2,OfficeId);
	        	    		 ps2.setInt(3,txtCash_Month_hid);
	        	    		 ps2.setInt(4,txtCash_year);
	        	    		 ps2.setInt(5,originated_slno);
	                         rs2=ps2.executeQuery();                                 
	                         while(rs2.next()) 
	                         {
	                        	 
	                        	 if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0){
		                        	 xml+= "<acct_unit>"+ rs2.getInt("TRF_ACCOUNTING_UNIT_ID") +"</acct_unit>";
		                        	 xml+= "<reason>"+ rs2.getString("REASON_FOR_TRANSFER") +"</reason>";
		                        	 
		                        	 xml+= "<cashyear>"+ rs2.getInt("CASHBOOK_YEAR") +"</cashyear>";
		                        	 xml+= "<cashmonth>"+ rs2.getInt("CASHBOOK_Month") +"</cashmonth>";
		                        	 
		                        	 xml+= "<particulars>"+ rs2.getString("PARTICULARS") +"</particulars>";
	                        	     xml+= "<account_head_code>"+ rs2.getInt("ACCOUNT_HEAD_CODE") +"</account_head_code>";
	                                 xml+= "<account_head_desc>"+ rs2.getString("account_head_desc") +"</account_head_desc>";
	                                 xml+= "<sub_ledger_type_code>"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+"</sub_ledger_type_code>";
	                                 xml+= "<sub_ledger_type_desc>"+rs2.getString("SUB_LEDGER_TYPE_DESC")+"</sub_ledger_type_desc>";
	                                 xml+= "<sub_ledger_code>"+ rs2.getInt("SUB_LEDGER_CODE") +"</sub_ledger_code>";	     
	                                 xml+= "<cr_dr>"+ rs2.getString("CR_DR_INDICATOR") +"</cr_dr>";	   
	                                 xml+= "<slno>"+ rs2.getString("SL_NO") +"</slno>";	   
	                                 xml+= "<amount>"+ df.format(rs2.getBigDecimal("AMOUNT")) +"</amount>"; 
	                                 xml+= "<trn_particulars>"+ rs2.getString("trn_particulars") +"</trn_particulars>";
	                                 
	                           
	                                 
	                            if(rs2.getString("REASON_FOR_TRANSFER").equalsIgnoreCase("Others") && (rs2.getInt("ACCOUNT_HEAD_CODE")!=620101 || rs2.getInt("ACCOUNT_HEAD_CODE")!=900301 ) )
	                            {
	                           ps3=con.prepareStatement("select MINOR_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?  ");
	                           ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
	                           rs3=ps3.executeQuery();
	                           if(rs3.next())
	                           xml+= "<minorheadcode>"+ rs3.getInt("MINOR_HEAD_CODE") +"</minorheadcode>";
	                           else
	                        	   xml+= "<minorheadcode>nominorhead</minorheadcode>";
	                            }else{
	                            	xml+= "<minorheadcode>nominorhead</minorheadcode>";
	                            }    
	                                 
	                                 
	                                 if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0 && rs2.getInt("SUB_LEDGER_CODE")!=0)
	                                 {
			                                SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
			                                ResultSet rs_get=obj_gen.getResult_General(AccUnitId,OfficeId,rs2.getInt("SUB_LEDGER_TYPE_CODE"),rs2.getInt("SUB_LEDGER_CODE"),0);
			                                String slcheck="";
			                                if(rs_get!=null)
			                                {
			                                	
	                                            while(rs_get.next())
	                                            {	
	                                            	System.out.println("I am in outside subledger"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+"----"+rs2.getInt("SUB_LEDGER_CODE"));
	                                            	if(rs_get.getInt("cid")==rs2.getInt("SUB_LEDGER_CODE") )
	                                            	{
	                                            		
	                                            		slcheck=rs_get.getString("cname");
	                                            	xml+= "<sub_ledger_desc>"+ slcheck +"</sub_ledger_desc>"; 
	                                            	System.out.println("I am in subledger");
	                                            	}
	                                            }
	                                            rs_get.close();
	                                            if(slcheck=="")
	                                            {
	                                            	xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  
	                                            }
			                                }
			                                else
			                                {
		                                      	System.out.println("null result set");
		                                      	xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  
			                                }
	                                 }
	                                 else
	                                	 	xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  
	                        	 }else{
	                        		 xml+= "<g_acct_unit>"+ rs2.getInt("TRF_ACCOUNTING_UNIT_ID") +"</g_acct_unit>";
		                        	 xml+= "<g_reason>"+ rs2.getString("REASON_FOR_TRANSFER") +"</g_reason>";
		                        	 
		                        	 xml+= "<g_cashyear>"+ rs2.getInt("CASHBOOK_YEAR") +"</g_cashyear>";
		                        	 xml+= "<g_cashmonth>"+ rs2.getInt("CASHBOOK_Month") +"</g_cashmonth>";
		                        	 
		                        	 xml+= "<g_particulars>"+ rs2.getString("PARTICULARS") +"</g_particulars>";
	                        	     xml+= "<g_account_head_code>"+ rs2.getInt("ACCOUNT_HEAD_CODE") +"</g_account_head_code>";
	                                 xml+= "<g_account_head_desc>"+ rs2.getString("account_head_desc") +"</g_account_head_desc>";
	                                 xml+= "<g_sub_ledger_type_code>"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+"</g_sub_ledger_type_code>";
	                                 xml+= "<g_sub_ledger_type_desc>"+rs2.getString("SUB_LEDGER_TYPE_DESC")+"</g_sub_ledger_type_desc>";
	                                 xml+= "<g_sub_ledger_code>"+ rs2.getInt("SUB_LEDGER_CODE") +"</g_sub_ledger_code>";	     
	                                 xml+= "<g_cr_dr>"+ rs2.getString("CR_DR_INDICATOR") +"</g_cr_dr>";	
	                                 xml+= "<g_slno>"+ rs2.getString("SL_NO") +"</g_slno>";	
	                                 xml+= "<g_amount>"+  df.format(rs2.getBigDecimal("AMOUNT")) +"</g_amount>"; 
	                                 xml+= "<g_trn_particulars>"+ rs2.getString("trn_particulars") +"</g_trn_particulars>";
	                                 
	                                 
	                                 
	                                 if(rs2.getString("REASON_FOR_TRANSFER").equalsIgnoreCase("Others") && (rs2.getInt("ACCOUNT_HEAD_CODE")!=620101 || rs2.getInt("ACCOUNT_HEAD_CODE")!=900301 ) )
	 	                            {
	 	                           ps3=con.prepareStatement("select MINOR_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?  ");
	 	                           ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
	 	                           rs3=ps3.executeQuery();
	 	                           
	 	                          if(rs3.next())
	 		                           xml+= "<g_minorheadcode>"+ rs3.getInt("MINOR_HEAD_CODE") +"</g_minorheadcode>";
	 		                           else
	 		                        	   xml+= "<g_minorheadcode>nominorhead</g_minorheadcode>";

	 	                            }else{
	 	                            	xml+= "<g_minorheadcode>nominorhead</g_minorheadcode>";
	 	                            }    
	                                 
	                                 
	                                 
	                                 if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0 && rs2.getInt("SUB_LEDGER_CODE")!=0)
	                                 {
			                                SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
			                                ResultSet rs_get=obj_gen.getResult_General(AccUnitId,OfficeId,rs2.getInt("SUB_LEDGER_TYPE_CODE"),rs2.getInt("SUB_LEDGER_CODE"),0);
			                                if(rs_get!=null)
			                                {
			                                	 String slcheck="";
	                                            while(rs_get.next())
	                                            {	
	                                            	if(rs_get.getInt("cid")==rs2.getInt("sub_ledger_code") )
	                                            	{
	                                            		
	                                            		slcheck=rs_get.getString("cname");
	                                            		xml+= "<g_sub_ledger_desc>"+ slcheck +"</g_sub_ledger_desc>"; 
	                                            	}
	                                            }
	                                            rs_get.close();
	                                            if(slcheck=="")
	                                            {
	                                            	xml+= "<g_sub_ledger_desc>--</g_sub_ledger_desc>";  
	                                            }
			                                }
			                                else
			                                {
		                                      	System.out.println("null result set");
		                                      	xml+= "<g_sub_ledger_desc>--</g_sub_ledger_desc>";  
			                                }
	                                 }
	                                 else
	                                	 	xml+= "<g_sub_ledger_desc>--</g_sub_ledger_desc>";  
	                        	 }
	                                 
	                                 
	                                 
	                                 count++;
	                         }					              
	                         if(count==0)
	                                 xml+="<flag>NoData</flag>";					           
	                         else               
	                                 xml+="<flag>success</flag>";
	                                     
	                }
	                catch(Exception e) 
	                {
	                         System.out.println("Exception in load_Voucher_No..."+e);
	                         xml+="<flag>"+e.getMessage()+"</flag>";
	                }                      
            }
           
            xml=xml+"</response>";
            System.out.println("xml :: "+xml);
            out.println(xml);
            out.close();
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
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
		      PreparedStatement ps2=null,ps=null;        
		      ResultSet rs2=null,rs=null;
		      String sql=null;      
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
	                  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                  Class.forName(strDriver.trim());
	                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	          }
	          catch(Exception e)
	          {
	              	  System.out.println("Exception in opening connection :"+e);
	          }
	          Calendar c;
	          int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,count=0;
	          String cr_dr=null,reason4Trf=null,particulars=null;
	          Date txtCrea_date=null,txtVoucher_date=null;
	          String update_user=(String)session.getAttribute("UserId");
	          long l=System.currentTimeMillis();
	          Timestamp ts=new Timestamp(l);                      
	          int errcode=0;
	          
	          //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
              
	          try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
             
              try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              System.out.println("cmbOffice_code "+cmbOffice_code);
             
              String[] sd=request.getParameter("Voucher_Date").split("/");
              c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
              java.util.Date d=c.getTime();
              txtCrea_date=new Date(d.getTime());
     
              try{txtCash_year=Integer.parseInt(sd[2]);}
              catch(Exception e){System.out.println("exception"+e );}
              System.out.println("txtCash_year "+txtCash_year);
             
              try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
              catch(Exception e){System.out.println("exception"+e );}
              System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
              int Originated_SL_No=0;
	             
	             try{Originated_SL_No=Integer.parseInt(request.getParameter("adviceNumber"));}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("Originated_SL_No "+Originated_SL_No);
	             
	             String[] sd1=request.getParameter("Voucher_Date").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
	             java.util.Date d1=c.getTime();
	             txtVoucher_date=new Date(d1.getTime());
	             
	             try{cr_dr=request.getParameter("Org_CR_DR");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("Org_CR_DR "+cr_dr);
	             
	             if(cr_dr.equals("CR"))
	            	 cr_dr="TPAOC";
	             else
	            	 cr_dr="TPAOD";
	             
	             try{txtUnitId=Integer.parseInt(request.getParameter("TransferedID"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("txtUnitId "+txtUnitId);
	             	                                     
	             try{reason4Trf=request.getParameter("Reason4Trf");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("txtRemarks "+reason4Trf);
	             
	             try{particulars=request.getParameter("GenParticulars");}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtAmount "+particulars);
	             Boolean flag=true;
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	             try
	             {
		             try
		       	 	 {
		                     con.clearWarnings();
		                     con.setAutoCommit(false);
		                     ps=con.prepareStatement("update FAS_TPA_MASTER set TRF_ACCOUNTING_UNIT_ID=?,REASON_FOR_TRANSFER=?,PARTICULARS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and TPA_TYPE=?");	                     
		                     ps.setInt(1,txtUnitId);                      
		                     ps.setString(2,reason4Trf);               
		                     ps.setString(3,particulars);
		                     ps.setString(4,"L");
		                     ps.setString(5,update_user);
		                     ps.setTimestamp(6,ts);
		                     ps.setInt(7,cmbAcc_UnitCode);
		                     ps.setInt(8,cmbOffice_code);
		                     ps.setInt(9,txtCash_year);
		                     ps.setInt(10,txtCash_Month_hid);
		                     ps.setInt(11,Originated_SL_No);
		                     ps.setString(12,cr_dr);
		                     errcode=ps.executeUpdate();
		       	 	 }catch(Exception e){System.out.println("Err in first table create :: "+e.getMessage());}
	                 if(errcode==0)
	                 {         
		                     System.out.println("redirect");
		                     flag=false;                                   
	                 }
	                 else
	                 {
		                	 ps.close();
	                   	  	 errcode=0;
	                   	  	 String sql_del=" delete from FAS_TPA_TRANSACTION  "+ 
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
			                       	 flag=false; 
		                     }
				             else
				             {			
			                	 	 int acc_head_code=0,cmbSL_type=0,cmbSL_Code=0,SL_NO=0;
			                	 	 double amount=0,txtsub_Amount=0;
			                	 	 String cr_dr_indicator=null,det_particulars=null;
			                	 	 System.out.println("First tableinserted successfully");
			                	 	 try{acc_head_code=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
			                	 	 }catch(Exception e){System.out.println("Exp in acc_head_code ::: ");}
			                	 	 try{cmbSL_type=Integer.parseInt(request.getParameter("cmbSL_type"));
			                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
			                	 	 try{cmbSL_Code=Integer.parseInt(request.getParameter("cmbSL_Code"));
			                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
			                	 	 try{amount=Double.parseDouble(request.getParameter("Amount"));
			                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
			                	 	 try{cr_dr_indicator=request.getParameter("Indi_CR_DR");
			                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
			                	 	 try{det_particulars=request.getParameter("DetParticular");
			                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
			                	 	 
			                	 	 String Grid_H_code[]=request.getParameterValues("H_code");
			                	 	 String Grid_SL_type[]=request.getParameterValues("SL_type");
			                         String Grid_SL_code[]=request.getParameterValues("SL_code"); 
			                         String Grid_CR_DR[]=request.getParameterValues("cr_dr");
			                         String Grid_Amt[]=request.getParameterValues("amount");
			                         String Grid_particular[]=request.getParameterValues("particular");
			                         System.out.println("2 nd table insert");
			                         try
			                         {	                        	 
			                        	 sql="insert into FAS_TPA_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
		                                 ps=con.prepareStatement(sql);
		                                 int k=-1;
		                                 for(;k<Grid_H_code.length;k++) 
		                                 {                                                                                                        
		                                          // cmbSL_type=0;cmbSL_Code=0;
		                                           txtAcc_HeadCode=0;txtsub_Amount=0;                                             
		                                           SL_NO++;
		                                           ps.setInt(1,cmbAcc_UnitCode);     
		                                           ps.setInt(2,cmbOffice_code);    
		                                           ps.setInt(3,txtCash_year);
		                                           System.out.println("txtCash_Month_hid :: "+txtCash_Month_hid);
		                                           ps.setInt(4,txtCash_Month_hid);
		                                           ps.setInt(5,Originated_SL_No);       
		                                           ps.setInt(6,SL_NO);
		                                           if(k<0)
		                                           {
		                                        	   ps.setInt(7,acc_head_code);
		                                        	   ps.setString(8,cr_dr_indicator);
		                                        	   ps.setInt(9,cmbSL_type);
		                                        	   ps.setInt(10,cmbSL_Code);
		                                        	   ps.setDouble(11,amount);
		                                        	   ps.setString(12,det_particulars);
		                                           }
		                                           else
		                                           {
		                                        	   txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
			                                           ps.setInt(7,txtAcc_HeadCode);
			                                           
			                                           String rad_sub_CR_DR=Grid_CR_DR[k];                               
			                                           ps.setString(8,rad_sub_CR_DR);   
			                                           
			                                           try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
			                                           catch(NumberFormatException e){System.out.println("exception"+e );}
			                                           ps.setInt(9,cmbSL_type); 
			                                           
			                                           try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
			                                           catch(NumberFormatException e){System.out.println("exception"+e );}
			                                           ps.setInt(10,cmbSL_Code);
			                                                                          
			                                           try{txtsub_Amount=Double.parseDouble(Grid_Amt[k]);}
			                                           catch(NumberFormatException e){System.out.println("exception"+e );}
			                                           ps.setDouble(11,txtsub_Amount);
			                                           
			                                           ps.setString(12,Grid_particular[k]);      
		                                           }
		                                           ps.setString(13,update_user);
		                                           ps.setTimestamp(14,ts);
		                                           int i=ps.executeUpdate(); 
		                                           if(i>0)
		                                           {
		                                               count++;
		                                               System.out.println("------------------------"+SL_NO+" inserted successfully");
		                                           }
		                                          // System.out.println("SQL::::insert into FAS_ADJUSTMENT_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,HO_REF_NUMBER,HO_REF_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values("+cmbAcc_UnitCode+","+cmbOffice_code+","+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+SL_NO+","+txtAcc_HeadCode+",'"+rad_sub_CR_DR+"',"+cmbSL_type+","+cmbSL_Code+","+ref_num+",?,?,?,?)");                                    
		                                   }   
		                                   if(count==Grid_H_code.length+1)
		                                   {
			                                	   	String txtReferNO_edit="",txtRemak_edit="";         // for cross reference
		    						                Date txtReferDate_edit=null; 
		    						                String radAuth_MC="";
		    						                int txtAuth_By=0;		
		    						                CallableStatement cs1=null;
		    						                cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
		    						                cs1.setInt(1,cmbAcc_UnitCode);
		    						                cs1.setInt(2,txtCash_year);
		    						                cs1.setInt(3,txtCash_Month_hid);
		    						                cs1.setInt(4,Originated_SL_No);
		    						                cs1.setInt(5,cmbOffice_code);
		    						                cs1.setDate(6,txtCrea_date);
		    						                cs1.setString(7,cr_dr);
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
			                         catch(Exception e)
			                         {
			                        	   System.out.println("Exp in 2 nd table ::: "+e.getMessage());	        
			                        	   flag=false;       
			                         }
				             }
	                 }
	             }
	             catch(Exception e)
	             {
            	 	 System.out.println("Err in insertion :: "+e.getMessage());
            	 	 flag=false;  
	             }
	             try
	             {	
	            	 if(flag==true)
		             {
		            	 sendMessage(response,"The TPA Sl.No '"+Originated_SL_No+"' has been updated successfully ","ok");
	                     con.commit();
		             }
		             else
	                 {
			             System.out.println("b4 Rollback");
			             sendMessage(response,"The TPA Sl.No updation failed ","ok");
			             con.rollback(); 
	                 }
	             }
	             catch(Exception e)
	             {
	            	 System.out.println("b4 Rollback");
		             sendMessage(response,"The TPA Sl.No updation failed  ","ok");
		             //con.rollback();
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

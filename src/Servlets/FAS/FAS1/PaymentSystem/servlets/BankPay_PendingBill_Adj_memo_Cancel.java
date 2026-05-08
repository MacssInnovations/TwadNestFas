package Servlets.FAS.FAS1.PaymentSystem.servlets;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

public class BankPay_PendingBill_Adj_memo_Cancel extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    /**
     *
     * @param config
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * do Post Function
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
        *  SESSION CHECKING
        */

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


        /**
         *  VARIABLES DECLARATION
         */
       /* Connection conMvr = null;*/
        Connection con = null;
        ResultSet rs = null,rs11=null;
        CallableStatement cs = null, cs1 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        
        String xml = "";
        String strCommand = "";

        /**
         *  VARIABLES DECLARATION by Nanda Kumar For Payment Cancellation
         *  
         *  
         *  
         */
        int totalnoofRecords=0;
        String mpvr="",crefd="",crefvr="",mpvrs="",mpvpd="",neededMvrdetails="",presentMvrdetails="";
	   
        /**
         *  VARIABLES DECLARATION by Nanda Kumar For Payment Cancellation
         *  
         *  
         *  
         */
        
        
        
        /**
         *  DATABASE CONNECTION
         */

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

        }


        /**
         *  GET COMMAND
         */

        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /**
         *  ACTUAL ACTION FOR CANCELLING BANK PAYMNETS STARTS HERE
         */

        if (strCommand.equalsIgnoreCase("Cancel")) {

            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";


            /** Variables Declaration */
            Calendar c;
            int cmbAcc_UnitCode = 0;
            int cmbOffice_code = 0;
            int txtCash_Month_hid = 0;
            int txtCash_year = 0;
            int txtVoucher_No = 0;
            Date txtCrea_date = null;
            Date cb_ref_date = null;
            Date rec_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            int receipt_no=0,voucher_no=0,mpvrlen=0;
 		   String receipt_dt="",voucher_dt="",cbrfno="",cbrfdate="",cbmpvrstatus="",cbmpvrdetails="";
 		  
            /** GET ACCOUNTING UNIT ID */
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            /** GET ACCOUNTING OFFICE ID */
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            
            String txtCrea_date1=request.getParameter("txtCrea_date");
            
            /** GET DATE */
            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
            String txtReferNO_edit = "";
            String txtRemak_edit = "";
            Date txtReferDate_edit = null;
            String radAuth_MC = "";
            int txtAuth_By = 0;int jj=0,count=0;
            /** GET VOUCHER NUMBER */
            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);


            /** FIND CASH BOOK MONTH AND YEAR */
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();
            int ss= 0;int kk=0,kk1=0,kk2=0,kk3=0;
            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");
            
            System.out.println("printing the year_month*****"+ym);

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);
            System.out.println(request.getParameter("Count_hid"));
            int len=Integer.parseInt(request.getParameter("Count_hid"));
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

System.out.println("len >> "+len);
            /** SQL QUERY FOR UPDATING 'FAS_PAYMENT_MASTER */

            String sql_del =
                "update FAS_PAYMENT_MASTER set PAYMENT_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
                "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and VOUCHER_NO=?  ";

            
            try{     ps = con.prepareStatement(sql_del);

            /** PARAMETER PASSING */
            System.out.println(txtCash_year+" > "+txtCash_Month_hid+" > "+txtVoucher_No+" > ");
            ps.setString(1, update_user);
            System.out.println("update_user---->"+update_user);
            ps.setTimestamp(2, ts);
            System.out.println("ts---->"+ts);
            ps.setInt(3, cmbAcc_UnitCode);
            System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
            ps.setInt(4, cmbOffice_code);
            System.out.println("cmbOffice_code---->"+cmbOffice_code);
            ps.setInt(5, txtCash_year);
            System.out.println("txtCash_year---->"+txtCash_year);
            ps.setInt(6, txtCash_Month_hid);
            System.out.println("txtCash_Month_hid---->"+txtCash_Month_hid);
            ps.setInt(7, txtVoucher_No);
            System.out.println("txtVoucher_No---->"+txtVoucher_No);
            ss=ps.executeUpdate();
            
            /** QUERY EXECUTION FOR UPDATION IN 'FAS_JOURNAL_TRANSACTION' */
          System.out.println("ss >>> "+ss);
            }catch(Exception ex){
    	   System.out.println("Excep 111111");
    	  ex.printStackTrace(); 
            }
            
        
            try {
                con.clearWarnings();
                con.setAutoCommit(false);

                /** QUERY EXECUTION FOR UPDATION IN 'FAS_PAYMENT_MASTER */
           
           String qry="";
           kk=0;
           String sqlQuery="",type="",type2="";
           int cnt=0,chk=0;
           
           System.out.println("ss >*****************> "+ss);
           
           
           if(ss > 0){
        	   String sel_JRl=
               		" SELECT COUNT(*) cno, " +
               		"  'J' AS type " +
               		" FROM FAS_JOURNAL_TRANSACTION " +
               		" WHERE ACCOUNTING_UNIT_ID    = " +cmbAcc_UnitCode+
               		" AND ACCOUNTING_FOR_OFFICE_ID= " +cmbOffice_code+
               		" AND CB_ADJ_REF_NO               = " +txtVoucher_No+
               		" AND CB_ADJ_REF_DATE             =? ";
               
            try{  
            	System.out.println("sel_JRl---->"+sel_JRl);
           	PreparedStatement pre_c=con.prepareStatement(sel_JRl);
           
            pre_c.setDate(1, txtCrea_date);
         //   pre_c.setDate(2, txtCrea_date);
           ResultSet rs_c=pre_c.executeQuery();
           System.out.println("rs_c---->"+rs_c.toString());
           while(rs_c.next()){
        	    chk++;
        	    if(chk==2) {
        	    	type2=type;
        	    	type=rs_c.getString("type");
        	    }
        	    else
        	    {
        	   cnt=rs_c.getInt("cno");
        	   type=rs_c.getString("type");
        	    }
           }
        	System.out.println("COUNT==>"+cnt+ "TYPE===>"+type);  
        	
          
        	
        	
        	/*@NK Include on 03072019 for multiplePvrs updation*/   
            String status="";
           // String[] mvrdetails = " ",replacedate = " " ;
            String[]mpvrdetails = new String[20];
            String[]cbrefdate = new String[20];
            String[]cbvrno = new String[20];
            String[]mpvrstatus = new String[20];
            String[]mpvrpdetails = new String[20];
			
            if(cnt>0 && type.equalsIgnoreCase("J"))
     	   {
     		   System.out.println(cnt+"     "+ type.equalsIgnoreCase("J"));
     		   
     		  
               String txtVoucherNo = request.getParameter("txtVoucher_No");
                String txtCreadate=request.getParameter("txtCrea_date");
                String cmbVochernoDate= "'"+" - "+txtVoucherNo+"/"+txtCreadate+"'";
                System.out.println("combinedVocherDate"+cmbVochernoDate);
     		   
//     		   String multiplepvrstatus =""
//     				  + "SELECT replaced.multiple_pvr_details, "
//     				 + "  replaced.replaced1, replaced.multiple_pvrs, "
//     				 + "  SUBSTR(SUBSTR(replaced.replaced1,instr_post(replaced.replaced1,'-',-1)+2), instr_post(SUBSTR(replaced.replaced1,instr_post(replaced.replaced1,'-',-1)+2),'/',1)+1)  AS paydt, "
//     				 + "  SUBSTR(SUBSTR(replaced.replaced1,instr_post(replaced.replaced1,'-',-1)+2),1,instr_post(SUBSTR(replaced.replaced1,instr_post(replaced.replaced1,'-',-1)+2),'/',1)-1) AS payvr "
//     				 + "FROM "
//     				 + "  (SELECT accounting_unit_id, "
//     				 + "    voucher_no, "
//     				 + "    CASHBOOK_YEAR, "
//     				 + "    CASHBOOK_MONTH, "
//     				 + "    multiple_pvr_details,jt.multiple_pvrs, "
//     				 + "    instr_post(multiple_pvr_details,"
//     				 + cmbVochernoDate
//     				 + ",1) AS STARTPOS, "
//     				 + "    LENGTH("
//     				 + cmbVochernoDate
//     				 + ")                     AS LEN1, "
//     				 + "    SUBSTR(multiple_pvr_details,2,instr_post(multiple_pvr_details,"
//     				 + cmbVochernoDate
//     				 + ",1)-1) "
//     				 + "    ||SUBSTR(multiple_pvr_details,instr_post(multiple_pvr_details,"
//     				 + cmbVochernoDate
//     				 + ",1)+LENGTH("
//     				 + cmbVochernoDate
//     				 + ")) AS REPLACED1 "
//     				 + "  FROM fas_journal_transaction jt "
//     				 + "  WHERE accounting_unit_id    = ? "
//     				 + "  AND ( multiple_pvr_details <> ' ' "
//     				 + "  OR multiple_pvr_details    IS NOT NULL ) "
//     				 + " AND jt.cb_ref_no            =? "
//     				 + "AND jt.cb_ref_date          =? "
//     				 + "  ) replaced "; 
     				   
     		   
     		   //sheron new query
     		   
     		  String multiplepvrstatus = "select multiple_pvr_details, multiple_pvrs "
     		  		+ " FROM fas_journal_transaction jt "
     		  		+ " WHERE accounting_unit_id    = ? "
     		  		+ " AND ( multiple_pvr_details <> '' "
     		  		+ " OR multiple_pvr_details    IS NOT NULL )"
     		  		+ " AND jt.cb_adj_ref_no            =? "
     		  		+ " AND jt.cb_adj_ref_date          =? ";
     		   
     		   
     		   //
           
     				   
     		   
     		   PreparedStatement pre_mpvr =null;
       			ResultSet resultmpvr=null;
       		 Connection  conMvr=null;
     		   try
     		   {
     			   
     			
     			   /*CREATING NEW CONNECTION*/
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
                         conMvr =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
                       conMvr.setAutoCommit(true);
                      
     			   
     			System.out.println("sel_mpvr---->"+multiplepvrstatus);
     			
     			try{
            pre_mpvr=conMvr.prepareStatement(multiplepvrstatus);
             	pre_mpvr.setInt(1, cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
             	pre_mpvr.setInt(2, txtVoucher_No);System.out.println("txtVoucher_No"+txtVoucher_No);
             	pre_mpvr.setDate(3, txtCrea_date);System.out.println("txtCrea_date"+txtCrea_date);
             	    resultmpvr=pre_mpvr.executeQuery();
             	 
                    while(resultmpvr.next()){
                    	
                   
//                 	String   replace_date=resultmpvr.getString("replaced1");   
//                 	String  cbref_date=resultmpvr.getString("paydt");
//                 	String  cbref_vrno=resultmpvr.getString("payvr");
//                 	 String   mpvr_details=resultmpvr.getString("multiple_pvr_details");
//                 	String  mpvr_status=resultmpvr.getString("multiple_pvrs");
                 	
                 	
                 	//new code// chris
//                 	String mul_pvr=" - 39/01/06/2023";
//            		String[] pvr=mul_pvr.trim().split("-");
//            		
//            		
//            		String pay_vr=pvr[pvr.length-2].toString().split("/")[0];
//            		String final_mpvr=mul_pvr.replace(" -"+ pvr[pvr.length-1],"");
//            		String final_date=pvr[pvr.length-2].toString().replace(pay_vr+"/", "");
            		
            		//new code// sheron
            		
              
                  String   mpvr_details=resultmpvr.getString("multiple_pvr_details");
                  String [] pvr = mpvr_details.trim().split("-");
                  String cbref_vrno =pvr[pvr.length-2].toString().split("/")[0];
                  String cbref_date =pvr[pvr.length-2].toString().replace(cbref_vrno+"/", "");
                  String replace_date=mpvr_details.replace(" -"+ pvr[pvr.length-1],"");
                  String  mpvr_status=resultmpvr.getString("multiple_pvrs");

                  cbref_date = cbref_date.isEmpty() ? null : cbref_date;
                  cbref_vrno = cbref_vrno.isEmpty() ? "0" : cbref_vrno;
            		
            		
                  //
                  	if(cbref_date == null && cbref_vrno.equalsIgnoreCase("0")) {
                  		replace_date = null;
                  	}
                  		
            		///
                 	
                 	mpvrdetails[totalnoofRecords]=replace_date;
                 	cbrefdate[totalnoofRecords]=cbref_date;
                 	cbvrno[totalnoofRecords]=cbref_vrno;
                 	mpvrpdetails[totalnoofRecords]=mpvr_details;
                 	mpvrstatus[totalnoofRecords]=mpvr_status;
                 	 totalnoofRecords++;
                 	
                    }
                	
     			}
     			catch(Exception e)
     			{
     				e.printStackTrace();
     			}
     			finally
     			{
     				if(pre_mpvr!=null)
     				{
     					pre_mpvr.close();
     				}
     				if(resultmpvr!=null)
     				{
     					resultmpvr.close();
     				}
     				
     			}
     			for(int i=0;i<totalnoofRecords;i++ )
     			{
     				
     				 mpvr=mpvrdetails[i];
     				 crefd=cbrefdate[i];
     				 crefvr=cbvrno[i];
     				mpvrs=mpvrstatus[i];
     				mpvpd=mpvrpdetails[i];
     				
     				 if(crefd!=null)
     				 {
     				String[] rd = crefd.split("/");
     	            c =
     	   new GregorianCalendar(Integer.parseInt(rd[2].trim()), Integer.parseInt(rd[1].trim()) - 1,
     	                         Integer.parseInt(rd[0].trim()));
     	            java.util.Date rdt = c.getTime();
     	           cb_ref_date = new Date(rdt.getTime());
     	            System.out.println("cb_ref_date " + cb_ref_date);
     				 }
     				
     				
     				 //Start Changed by sheron 12_07_2023 
     				if(cnt>0 && mpvr==null)
                	{
//     					presentMvrdetails="'"+mpvpd+"'";
     					presentMvrdetails=mpvpd;
     					try{  
                                	  
     						cs1 = con.prepareCall("call srn_journal_payment_cancel2(?,?,?,?,?,?,?)");		
     						cs1.setString(1,update_user );
     						cs1.setInt(2,cmbAcc_UnitCode );
     						cs1.setInt(3,cmbOffice_code );
     						cs1.setInt(4,txtVoucher_No );
     						cs1.setDate(5,txtCrea_date);
     						cs1.setString(6, presentMvrdetails);
     						cs1.setInt(7, 0);
     		                cs1.registerOutParameter(7, java.sql.Types.NUMERIC);
     		                
     		                cs1.execute();
     		               int errcode = cs1.getBigDecimal(7).intValue(); 
                      
     		               int rkk = errcode;
                      
                      if (rkk>0)
                      {
                   	   kk=rkk;
                      }
                     
                      
                      /** QUERY EXECUTION FOR UPDATION IN 'FAS_JOURNAL_TRANSACTION' */
                   
                      }catch(Exception ex){
              	   System.out.println("Excep 111111");
              	  ex.printStackTrace(); 
                      }
                		
                	}
     				
     				// End Changed by sheron 12_07_2023 
     			
//     		      	if(cnt>0 && mpvr!=null && mpvrs.equalsIgnoreCase("Y"))
//                	{
//     		      		neededMvrdetails="'"+mpvr+"'";
//     		      		presentMvrdetails="'"+mpvpd+"'";
//                		String sql_mvrDate_JT =
//                             "update FAS_JOURNAL_TRANSACTION set MULTIPLE_PVR_DETAILS="+ neededMvrdetails
//                             + " ,CHEQUE_OR_DD= (select CHEQUE_OR_DD from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                             + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                             + " and CASHBOOK_YEAR="+txtCash_year
//                             + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                             + " and VOUCHER_NO="+txtVoucher_No
//                             + " and SL_NO=1 )"
//                             + " ,CHEQUE_DD_NO= (select CHEQUE_DD_NO from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                             + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                             + " and CASHBOOK_YEAR="+txtCash_year
//                             + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                             + " and VOUCHER_NO="+txtVoucher_No
//                             + " and SL_NO=1 )"
//                              + " ,CHEQUE_DD_DATE= (select CHEQUE_DD_DATE from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                             + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                             + " and CASHBOOK_YEAR="+txtCash_year
//                             + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                             + " and VOUCHER_NO="+txtVoucher_No
//                             + " and SL_NO=1 )"
//                             + " ,cb_ref_no="+crefvr
//                             + " ,cb_ref_date=?"
//                             + " ,UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and cb_ref_no=? AND cb_ref_date=? "
//                             + "  and MULTIPLE_PVR_DETAILS="+presentMvrdetails;
//                   	 
//                		System.out.println("qry  "+sql_mvrDate_JT);
//                         try{  
//                         	
//          
//                         	PreparedStatement   psMVRD = conMvr.prepareStatement(sql_mvrDate_JT);
//                         	
//                         /** PARAMETER PASSING */
//                         System.out.println(txtCash_year+" > "+txtCash_Month_hid+" > "+txtVoucher_No+" > ");
//                      
//                         psMVRD.setDate(1, cb_ref_date);
//                         psMVRD.setString(2, update_user);
//                         System.out.println("update_user---->"+update_user);
//                         psMVRD.setTimestamp(3, ts);
//                         System.out.println("ts---->"+ts);
//                         psMVRD.setInt(4, cmbAcc_UnitCode);
//                         System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
//                         psMVRD.setInt(5, cmbOffice_code);
//                         System.out.println("cmbOffice_code---->"+cmbOffice_code);
//                     
//                         psMVRD.setInt(6, txtVoucher_No);
//                         System.out.println("txtVoucher_No---->"+txtVoucher_No);
//                         psMVRD.setDate(7, txtCrea_date);
//                         System.out.println("txtCrea_date"+txtCrea_date);
//                        
//                         
//                          int rkk= psMVRD.executeUpdate();
//                          if (rkk>0)
//                          {
//                       	   kk=rkk;
//                          }
//                         
//                       
//                         }
//                         
//                         catch(SQLException e){
//                 	   System.out.println("Excep 111111");
//                 	  e.printStackTrace(); 
//                         }
//                	}
               
     		      	
     		     	if(cnt>0 && mpvr!=null && mpvrs.equalsIgnoreCase("N"))
                	{
     		     		neededMvrdetails="'"+mpvr+"'";
     		     		presentMvrdetails="'"+mpvpd+"'";
                		String sql_mvrDate_JT =
                             "update FAS_JOURNAL_TRANSACTION set MULTIPLE_PVR_DETAILS="+ neededMvrdetails
                             + " ,CHEQUE_OR_DD= (select CHEQUE_OR_DD from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
                             + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
                             + " and CASHBOOK_YEAR="+txtCash_year
                             + " and CASHBOOK_MONTH="+txtCash_Month_hid
                             + " and VOUCHER_NO="+txtVoucher_No
                             + " and SL_NO=1 )"
                             + " ,CHEQUE_DD_NO= (select CHEQUE_DD_NO from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
                             + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
                             + " and CASHBOOK_YEAR="+txtCash_year
                             + " and CASHBOOK_MONTH="+txtCash_Month_hid
                             + " and VOUCHER_NO="+txtVoucher_No
                             + " and SL_NO=1 )"
                              + " ,CHEQUE_DD_DATE= (select CHEQUE_DD_DATE from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
                             + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
                             + " and CASHBOOK_YEAR="+txtCash_year
                             + " and CASHBOOK_MONTH="+txtCash_Month_hid
                             + " and VOUCHER_NO="+txtVoucher_No
                             + " and SL_NO=1 )"
                             + " ,cb_adj_ref_no="+crefvr
                             + " ,cb_adj_ref_date=?"
                             + " ,MULTIPLE_PVRS='Y', UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and cb_ref_no=? AND cb_ref_date=? "
                             + " and MULTIPLE_PVR_DETAILS="+presentMvrdetails;
                   	 
                		System.out.println("qry  "+sql_mvrDate_JT);
                         try{  
                         	
          
                         	PreparedStatement   psMVRD = conMvr.prepareStatement(sql_mvrDate_JT);
                         	
                         /** PARAMETER PASSING */
                         System.out.println(txtCash_year+" > "+txtCash_Month_hid+" > "+txtVoucher_No+" > ");
                     
                         
                         psMVRD.setDate(1, cb_ref_date);
                         psMVRD.setString(2, update_user);
                         System.out.println("update_user---->"+update_user);
                         psMVRD.setTimestamp(3, ts);
                         System.out.println("ts---->"+ts);
                         psMVRD.setInt(4, cmbAcc_UnitCode);
                         System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
                         psMVRD.setInt(5, cmbOffice_code);
                         System.out.println("cmbOffice_code---->"+cmbOffice_code);
                        
                         psMVRD.setInt(6, txtVoucher_No);
                         System.out.println("txtVoucher_No---->"+txtVoucher_No);
                         psMVRD.setDate(7, txtCrea_date);
                         System.out.println("txtCrea_date"+txtCrea_date);
                         
                       
                         int rkk= psMVRD.executeUpdate();
                         if (rkk>0)
                         {
                      	   kk=rkk;
                         }
                        
                       
                         }
                         
                         catch(SQLException e){
                 	   System.out.println("Excep 111111");
                 	  e.printStackTrace(); 
                         }
                	}
     				
     				
     		     	
     		     	/* @NK on 27-Sep-2019 For cbrefType updation	*/
      		     	
         		 	 Integer[]voucherno = new Integer[20];
                    String[]voucherdate = new String[20];
                    String[]cbrefno = new String[20];
                    String[]cbrefda = new String[20];
         	      // if((kk1>0)&&(mpvrlen==15 || mpvrlen==16 ||mpvrlen==17))
                    if((kk>0)&&(mpvr==null))
                  {
         	    	 String combPaynodate="'"+txtVoucherNo+txtCreadate+"'";
         	    	
//         	    	 SimpleDateFormat originalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//         	    	 
//         	    	try {
//         	            // Parse the original date string to obtain a Date object
//         	            java.util.Date originalDate = originalDateFormat.parse(txtCreadate);
//
//         	            // Create a SimpleDateFormat object for formatting the new date
//         	            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//         	            // Format the Date object to obtain the new date string
//         	           txtCreadate = newDateFormat.format(originalDate);
//
//         	            // Print the new date string
//         	           
//         	        } catch (ParseException e) {
//         	            e.printStackTrace();
//         	        }
         	    	 
//         	      combPaynodate="'"+txtVoucherNo+txtCreadate+"'";
         	   	 
         	    	
/*@NK
 * Details about sqlCbreftype
 * 
 * First Query returns number of records going to null during payment cancellation
 * Second Query returns number of records which has 0 or null value in cbreftype and cbrefdate for already cancelled payment
 * Finally total records is matched with No.oftransation records with related master table
 * 
 * Scenario is same for receipt payment cancellation
 * 
 * */
         	    	 
         	    	String sqlCbreftype = ""
         	    			+ "SELECT mm.VOUCHER_NO, "
         	    			+ "  mm.cashbook_year, "
         	    			+ "  MM.CASHBOOK_MONTH, "
         	    			+ "  mm.VOUCHER_DATE, "
         	    			+ "  mm.total_trn_records, "
         	    			+ "  CT.TOTALSL "
         	    			+ "FROM FAS_JOURNAL_MASTER mm , "
         	    			+ "  (SELECT VOUCHER_NO, "
         	    			+ "    cashbook_year, "
         	    			+ "    cashbook_month, "
         	    			+ "    COUNT(*) AS totalsl "
         	    			+ "  FROM "
         	    			+ "    (SELECT m.VOUCHER_NO, "
         	    			+ "      m.cashbook_year, "
         	    			+ "      M.CASHBOOK_MONTH "
         	    			+ "    FROM FAS_JOURNAL_MASTER m, "
         	    			+ "      (SELECT DISTINCT JT.ACCOUNTING_UNIT_ID, "
         	    			+ "        JT.accounting_for_office_id, "
         	    			+ "        JT.VOUCHER_NO, "
         	    			+ "        JT.SL_NO, "
         	    			+ "        JM.VOUCHER_DATE, "
         	    			+ "        JT.CASHBOOK_YEAR, "
         	    			+ "        JT.CASHBOOK_MONTH, "
         	    			+ "        JT.CB_REF_NO, "
         	    			+ "        JT.CB_REF_DATE, "
         	    			+ "        COUNT(*) AS totalsl "
         	    			+ "      FROM FAS_JOURNAL_TRANSACTION JT, "
         	    			+ "        FAS_JOURNAL_MASTER JM "
         	    			+ "      WHERE JT.ACCOUNTING_UNIT_ID                                             =? "
         	    			+ "      AND JT.ACCOUNTING_FOR_OFFICE_ID                                         = ? "
         	    			+ "      AND JT.VOUCHER_NO                                                       =JM.VOUCHER_NO "
         	    			+ "      AND JT.CASHBOOK_YEAR                                                    = JM.CASHBOOK_YEAR "
         	    			+ "      AND JT.CASHBOOK_MONTH                                                   =JM.CASHBOOK_MONTH "
//         	    			+ "      AND (CONCAT(TO_CHAR(JT.CB_REF_NO),TO_CHAR(JT.CB_REF_DATE,'dd/mm/yyyy')) = "+combPaynodate
//         	    			+ "    ) "
							+ "      AND JT.cb_ref_no::varchar||to_char(JT.cb_ref_date::date,'DD/MM/YYYY') = "+combPaynodate           
         	    			+ "      AND LENGTH(JT.MULTIPLE_PVR_DETAILS)                                    <=17 "
         	    			+ "      GROUP BY JT.ACCOUNTING_UNIT_ID, "
         	    			+ "        JT.accounting_for_office_id, "
         	    			+ "        JT.VOUCHER_NO, "
         	    			+ "        JT.SL_NO, "
         	    			+ "        JM.VOUCHER_DATE, "
         	    			+ "        JT.CASHBOOK_YEAR, "
         	    			+ "        JT.CASHBOOK_MONTH, "
         	    			+ "        JT.CB_REF_NO, "
         	    			+ "        JT.CB_REF_DATE "
         	    			+ "      ) x "
         	    			+ "    WHERE m.accounting_unit_id    = x.accounting_unit_id "
         	    			+ "    AND M.ACCOUNTING_FOR_OFFICE_ID= X.ACCOUNTING_FOR_OFFICE_ID "
         	    			+ "    AND m.VOUCHER_NO              = x.VOUCHER_NO "
         	    			+ "    AND m.CASHBOOK_YEAR           = x.CASHBOOK_YEAR "
         	    			+ "    AND m.CASHBOOK_MONTH          =x.CASHBOOK_MONTH "
         	    			+ "    UNION ALL "
         	    			+ "    SELECT VOUCHER_NO, "
         	    			+ "      cashbook_year, "
         	    			+ "      cashbook_month "
         	    			+ "    FROM "
         	    			+ "      (SELECT DISTINCT T.VOUCHER_NO , "
         	    			+ "        M.VOUCHER_DATE, "
         	    			+ "        t.sl_no, "
         	    			+ "        t.cashbook_year, "
         	    			+ "        t.cashbook_month, "
         	    			+ "        t.cb_ref_no, "
         	    			+ "        t.cb_ref_date "
         	    			+ "      FROM FAS_JOURNAL_TRANSACTION T, "
         	    			+ "        FAS_JOURNAL_MASTER M, "
         	    			+ "        (SELECT JT.ACCOUNTING_UNIT_ID, "
         	    			+ "          JT.VOUCHER_NO, "
         	    			+ "          JT.CASHBOOK_YEAR, "
         	    			+ "          JT.CASHBOOK_MONTH, "
         	    			+ "          JT.CB_REF_NO, "
         	    			+ "          JT.CB_REF_DATE "
         	    			+ "        FROM FAS_JOURNAL_TRANSACTION JT "
         	    			+ "        WHERE JT.ACCOUNTING_UNIT_ID                                             =? "
         	    			+ "        AND JT.ACCOUNTING_FOR_OFFICE_ID                                         = ?"
         	    			//+ "        AND (concat(TO_CHAR(JT.cb_ref_no),TO_CHAR(JT.cb_ref_date,'dd/mm/yyyy')) ="+combPaynodate
         	    			+ "        AND JT.cb_ref_no::varchar||to_char(JT.cb_ref_date::date,'DD/MM/YYYY') ="+combPaynodate
//         	    			+ "   ) "
         	    			+ "        ) x "
         	    			+ "      WHERE t.accounting_unit_id     = x.accounting_unit_id "
         	    			+ "      AND t.cashbook_year            = x.cashbook_year "
         	    			+ "      AND T.CASHBOOK_MONTH           =X.CASHBOOK_MONTH "
         	    			+ "      AND T.VOUCHER_NO               = X.VOUCHER_NO "
         	    			+ "      AND M.VOUCHER_NO               =X.VOUCHER_NO "
         	    			+ "      AND M.CASHBOOK_YEAR            = X.CASHBOOK_YEAR "
         	    			+ "      AND M.CASHBOOK_MONTH           =X.CASHBOOK_MONTH "
         	    			+ "      AND M.ACCOUNTING_UNIT_ID       = T.ACCOUNTING_UNIT_ID "
         	    			+ "      AND M.ACCOUNTING_FOR_OFFICE_ID = T.ACCOUNTING_FOR_OFFICE_ID "
         	    			+ "      AND ((T.CB_REF_NO             IS NULL) "
         	    			+ "      OR(T.CB_REF_NO                 =0)) "
         	    			+ "      ) Y "
         	    			+ "    ) newm "
         	    			+ "  GROUP BY VOUCHER_NO, "
         	    			+ "    cashbook_year, "
         	    			+ "    cashbook_month "
         	    			+ "  ) CT "
         	    			+ "WHERE MM.ACCOUNTING_UNIT_ID     = ? "
         	    			+ "AND mm.accounting_for_office_id = ? "
         	    			+ "AND mm.VOUCHER_NO               = ct.VOUCHER_NO "
         	    			+ "AND mm.CASHBOOK_YEAR            = ct.CASHBOOK_YEAR "
         	    			+ "AND MM.CASHBOOK_MONTH           = CT.CASHBOOK_MONTH "
         	    		    + "AND mm.total_trn_records        = totalsl";



         	    	try{
         	    		 System.out.println("sqlCbreftype---->"+sqlCbreftype);
         	    	 ps=con.prepareStatement(sqlCbreftype);
         	    	 
         	    	ps.setInt(1, cmbAcc_UnitCode);
                   System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
         	    	 
                   ps.setInt(2, cmbOffice_code);
                   System.out.println("cmbOffice_code---->"+cmbOffice_code);
                   
                   ps.setInt(3, cmbAcc_UnitCode);
                   System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
         	    	 
                   ps.setInt(4, cmbOffice_code);
                   System.out.println("cmbOffice_code---->"+cmbOffice_code);
                   
                   ps.setInt(5, cmbAcc_UnitCode);
                   System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
         	    	 
                   ps.setInt(6, cmbOffice_code);
                   System.out.println("cmbOffice_code---->"+cmbOffice_code);
                   
                   
                    rs11=ps.executeQuery();  
         	    	   
                      	while(rs11.next())
                      	{
                      		System.out.println("inside while loop....");
                      		voucher_no=rs11.getInt("VOUCHER_NO")	;
                      		voucher_dt=rs11.getString("VOUCHER_DATE");
                      		voucherno[count]=voucher_no;
                      		voucherdate[count]=voucher_dt;
                         	count++;
                      	
                      	}
                 	    	
                      	}
            			catch(Exception e)
            			{
            				e.printStackTrace();
            			}
            			finally
            			{
            				if(ps!=null)
            				{
            					ps.close();
            				}
            				if(rs11!=null)
            				{
            					rs11.close();
            				}
                      		
                      	}
         	    		
                      		for(int j=0;j<count;j++)
                      		{
                      		 int vouno=voucherno[j];
            				 String voud=voucherdate[j];
            				
            				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            				java.util.Date date = inputFormat.parse(voud);

            				// Format date into output format
            				DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            				String voucher_date = outputFormat.format(date);
                      	System.out.println("count for total number of records for cbreftype upation-->"+count);
                      	
                    	   String sql_recMaster =
                             "update FAS_JOURNAL_MASTER mas set mas.CB_REF_TYPE=null,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where" +
                             "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.VOUCHER_NO=? and mas.VOUCHER_DATE=?";
                    	   ps3 = con.prepareStatement(sql_recMaster);   
                    	   ps3.setString(1, update_user);System.out.println("update_user"+update_user);
                    	   ps3.setTimestamp(2, ts);System.out.println("ts"+ts);
                    	   ps3.setInt(3, cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                    	   ps3.setInt(4, cmbOffice_code);System.out.println("cmbOffice_code"+cmbOffice_code);
                    	   ps3.setInt(5, vouno);System.out.println("voucher_no"+vouno);
                    	   ps3.setString(6, voucher_date);System.out.println("voucher_dt"+voucher_date);
                           kk3=ps3.executeUpdate();
                          
                           System.out.println("Result.....kk3....."+kk3+"journal CB_REF_TYPE updated for voucher_no= "+vouno);
                       }//end for loop
                      	
                      		if(count==0&&kk3==0)
                           {
                      			kk3++;
                        	   System.out.println("Result.....kk3...WithoutAny.CB_REF_TYPE updation."+kk3);
                           }
                      	
                      	
                      		} //end if
                      		
                    else if((kk>0)&&(mpvr!=null)&&(kk3==0))
           	       {
                    	kk3++;
                	   System.out.println("Result.....kk3...WithoutAny.CB_REF_TYPE updation."+kk3);
           	       }         	
         		     	
                   /* @NK on 27-Sep-2019 For cbrefType updation*/
     		     	
     				 
     			} //end of for loop
     			
                
          
             	
     		   }
     		   catch (Exception e)
     		   {
     			   e.printStackTrace(); 
     		   }
    		   finally
    	        { 
    			   conMvr.setAutoCommit(true);
      			  conMvr.close();
    	        } 
    		   
 	   
     	   }
            
            
            
//             if(cnt>0 && (type.equalsIgnoreCase("R") || type2.equalsIgnoreCase("R")))
//      	   {
//      		   System.out.println(cnt+"     "+ type.equalsIgnoreCase("R"));
//      		   
//      		  
//                String txtVoucherNo = request.getParameter("txtVoucher_No");
//                 String txtCreadate=request.getParameter("txtCrea_date");
//                 String cmbVochernoDate= "'"+" - "+txtVoucherNo+"/"+txtCreadate+"'";
//               
//                 System.out.println("combinedVocherDate"+cmbVochernoDate);
//      		   
//      		   String multiplepvrstatus = " SELECT 	multiple_pvrs, multiple_pvr_details "
//      									+ " FROM FAS_Receipt_TRANSACTION rt "
//      									+ " WHERE "
//      									+ " accounting_unit_id = ? "
//      									+ " AND ( multiple_pvr_details <> '' OR multiple_pvr_details IS NOT NULL ) "
//      									+ " AND rt.cb_ref_no = ? "
//      									+ " AND rt.cb_ref_date = ? ";
//      					
//   
//      				   
//      		   
//      		   PreparedStatement pre_mpvr =null;
//        			ResultSet resultmpvr=null;
//        		 Connection  conMvr=null;
//      		   try
//      		   {
//      			   
//      			
//      			   /*CREATING NEW CONNECTION*/
//      			   ResourceBundle rs1 =
//                             ResourceBundle.getBundle("Servlets.Security.servlets.Config");
//                         String ConnectionString = "";
//
//                         String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
//                         String strdsn = rs1.getString("Config.DSN");
//                         String strhostname = rs1.getString("Config.HOST_NAME");
//                         String strportno = rs1.getString("Config.PORT_NUMBER");
//                         String strsid = rs1.getString("Config.SID");
//                         String strdbusername = rs1.getString("Config.USER_NAME");
//                         String strdbpassword = rs1.getString("Config.PASSWORD");
//                         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
//                         Class.forName(strDriver.trim());
//                          conMvr =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
//                        conMvr.setAutoCommit(false);
//                         //conMvr.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); 
//      			   
//      			   
//      			System.out.println("sel_mpvr---->"+multiplepvrstatus);
//      			
//      			try{
//             pre_mpvr=conMvr.prepareStatement(multiplepvrstatus);
//              	pre_mpvr.setInt(1, cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
//              	pre_mpvr.setInt(2, txtVoucher_No);System.out.println("txtVoucher_No"+txtVoucher_No);
//              	pre_mpvr.setDate(3, txtCrea_date);System.out.println("txtCrea_date"+txtCrea_date);
//              	    resultmpvr=pre_mpvr.executeQuery();
//              	 
//                     while(resultmpvr.next()){
//                     	
//                    
//                  	
//                  	String   mpvr_details=resultmpvr.getString("multiple_pvr_details");
//                  	String  mpvr_status=resultmpvr.getString("multiple_pvrs");
//                  	
//                  	String pvr[] = mpvr_details.trim().split("-");
//                  	String cbref_vrno =pvr[pvr.length-2].toString().split("/")[0];
//                    String cbref_date =pvr[pvr.length-2].toString().replace(cbref_vrno+"/", "");
//                    String replace_date=mpvr_details.replace(" -"+ pvr[pvr.length-1],"");
//
//                  	
//                    cbref_date = cbref_date.isEmpty() ? null : cbref_date;
//                    cbref_vrno = cbref_vrno.isEmpty() ? "0" : cbref_vrno;
//                    
//                  	if(cbref_date == null && cbref_vrno.equalsIgnoreCase("0")) {
//                  		replace_date = null;
//                  	} 
//                  	
//                  	
//                  	
//                  	mpvrdetails[totalnoofRecords]=replace_date;
//                  	cbrefdate[totalnoofRecords]=cbref_date;
//                  	cbvrno[totalnoofRecords]=cbref_vrno;
//                  	mpvrpdetails[totalnoofRecords]=mpvr_details;
//                  	mpvrstatus[totalnoofRecords]=mpvr_status;
//                  	 totalnoofRecords++;
//                  	
//                     }
//                 	
//      			}
//      			catch(Exception e)
//      			{
//      				e.printStackTrace();
//      			}
//      			finally
//      			{
//      				if(pre_mpvr!=null)
//      				{
//      					pre_mpvr.close();
//      				}
//      				if(resultmpvr!=null)
//      				{
//      					resultmpvr.close();
//      				}
//      				
//      			}
//      			for(int i=0;i<totalnoofRecords;i++ )
//      			{
//      				
//      				 mpvr=mpvrdetails[i];
//      				 crefd=cbrefdate[i];
//      				 crefvr=cbvrno[i];
//      				mpvrs=mpvrstatus[i];
//      				mpvpd=mpvrpdetails[i];
//      				 if(crefd!=null)
//      				 {
//      				String[] rd = crefd.split("/");
//      	            c =
//      	   new GregorianCalendar(Integer.parseInt(rd[2].trim()), Integer.parseInt(rd[1].trim()) - 1,
//      	                         Integer.parseInt(rd[0].trim()));
//      	            java.util.Date rdt = c.getTime();
//      	           cb_ref_date = new Date(rdt.getTime());
//      	            System.out.println("cb_ref_date " + cb_ref_date);
//      				 }
//      				
//      				 
//      				 /*
//      				  * if(cnt>0 && mpvr==null)
//                	{
////     					presentMvrdetails="'"+mpvpd+"'";
//     					presentMvrdetails=mpvpd;
//     					try{  
//                                	  
//     						cs1 = con.prepareCall("call srn_journal_payment_cancel(?,?,?,?,?,?,?)");		
//     						cs1.setString(1,update_user );
//     						cs1.setInt(2,cmbAcc_UnitCode );
//     						cs1.setInt(3,cmbOffice_code );
//     						cs1.setInt(4,txtVoucher_No );
//     						cs1.setDate(5,txtCrea_date);
//     						cs1.setString(6, presentMvrdetails);
//     						cs1.setInt(7, 0);
//     		                cs1.registerOutParameter(7, java.sql.Types.NUMERIC);
//     		                
//     		                cs1.execute();
//     		               int errcode = cs1.getBigDecimal(7).intValue(); 
//                      
//     		               int rkk = errcode;
//                      
//                      if (rkk>0)
//                      {
//                   	   kk=rkk;
//                      }
//      				  * 
//      				  * 
//      				  * 
//      				  * 
//      				  * 
//      				  */
//      				 
//      				 
//      				 
//      				 
//      				
//      				if(cnt>0 && mpvr==null)
//                 	{
////      					presentMvrdetails="'"+mpvpd+"'";
//     					presentMvrdetails=mpvpd;
//
//      					
//      					try {
//      						cs1 = con.prepareCall("call srn_receipt_payment_cancel(?,?,?,?,?,?,?)");		
//      						
//      						cs1.setString(1,update_user );
//     						cs1.setInt(2,cmbAcc_UnitCode );
//     						cs1.setInt(3,cmbOffice_code );
//     						cs1.setInt(4,txtVoucher_No );
//     						cs1.setDate(5,txtCrea_date);
//     						cs1.setString(6, presentMvrdetails);
//     						cs1.setInt(7, 0);
//     		                cs1.registerOutParameter(7, java.sql.Types.NUMERIC);
//     		                
//     		                cs1.execute();
//     		               int errcode = cs1.getBigDecimal(7).intValue(); 
//                      
//     		               int rkk1 = errcode;
//      					
//     		              if (rkk1>0)
//                          {
//                       	   kk1=rkk1;
//                          }
//      
//      					}
//                 	catch(Exception ex){
//               	   System.out.println("Excep 111111");
//               	  ex.printStackTrace(); 
//                       }
//                 		
//                 	}
//      				
//      			
//      		      	if(cnt>0 && mpvr!=null && (mpvrs.equalsIgnoreCase("Y")||mpvrs.contains("Y")) )
//                 	{
//      		      		neededMvrdetails="'"+mpvr+"'";
//      		      		presentMvrdetails="'"+mpvpd+"'";
//                 		String sql_mvrDate_JT =
//                              "update FAS_Receipt_TRANSACTION set MULTIPLE_PVR_DETAILS="+ neededMvrdetails
//                              + " ,CHEQUE_OR_DD= (select CHEQUE_OR_DD from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                              + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                              + " and CASHBOOK_YEAR="+txtCash_year
//                              + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                              + " and VOUCHER_NO="+txtVoucher_No
//                              + " and SL_NO=1 )"
//                              + " ,CHEQUE_DD_NO= (select CHEQUE_DD_NO from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                              + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                              + " and CASHBOOK_YEAR="+txtCash_year
//                              + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                              + " and VOUCHER_NO="+txtVoucher_No
//                              + " and SL_NO=1 )"
//                               + " ,CHEQUE_DD_DATE= (select CHEQUE_DD_DATE from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                              + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                              + " and CASHBOOK_YEAR="+txtCash_year
//                              + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                              + " and VOUCHER_NO="+txtVoucher_No
//                              + " and SL_NO=1 )"
//                              + " ,cb_ref_no="+crefvr
//                              + " ,cb_ref_date=?"
//                              + " ,UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and cb_ref_no=? AND cb_ref_date=? "
//                              + "  and MULTIPLE_PVR_DETAILS="+presentMvrdetails;
//                    	 
//                 		System.out.println("qry  "+sql_mvrDate_JT);
//                          try{  
//                          	
//           
//                          	PreparedStatement   psMVRD = conMvr.prepareStatement(sql_mvrDate_JT);
//                          
//
//                          /** PARAMETER PASSING */
//                          System.out.println(txtCash_year+" > "+txtCash_Month_hid+" > "+txtVoucher_No+" > ");
//                       
//                          psMVRD.setDate(1, cb_ref_date);
//                          psMVRD.setString(2, update_user);
//                          System.out.println("update_user---->"+update_user);
//                          psMVRD.setTimestamp(3, ts);
//                          System.out.println("ts---->"+ts);
//                          psMVRD.setInt(4, cmbAcc_UnitCode);
//                          System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
//                          psMVRD.setInt(5, cmbOffice_code);
//                          System.out.println("cmbOffice_code---->"+cmbOffice_code);
//                       
//                          psMVRD.setInt(6, txtVoucher_No);
//                          System.out.println("txtVoucher_No---->"+txtVoucher_No);
//                          psMVRD.setDate(7, txtCrea_date);
//                          System.out.println("txtCrea_date"+txtCrea_date);
//                          
//                          int rkk1= psMVRD.executeUpdate();
//                          if (rkk1>0)
//                          {
//                       	   kk1=rkk1;
//                          }
//                          
//                          }
//                          
//                          catch(SQLException e){
//                  	   System.out.println("Excep 111111");
//                  	  e.printStackTrace(); 
//                          }
//                 	}
//                
//      		      	
//      		     	if(cnt>0 && mpvr!=null && (mpvrs.equalsIgnoreCase("N")||mpvrs.contains("N")))
//                 	{
//      		     		neededMvrdetails="'"+mpvr+"'";
//      		     		presentMvrdetails="'"+mpvpd+"'";
//                 		String sql_mvrDate_JT =
//                              "update FAS_Receipt_TRANSACTION set MULTIPLE_PVR_DETAILS="+ neededMvrdetails
//                              + " ,CHEQUE_OR_DD= (select CHEQUE_OR_DD from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                              + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                              + " and CASHBOOK_YEAR="+txtCash_year
//                              + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                              + " and VOUCHER_NO="+txtVoucher_No
//                              + " and SL_NO=1 )"
//                              + " ,CHEQUE_DD_NO= (select CHEQUE_DD_NO from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                              + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                              + " and CASHBOOK_YEAR="+txtCash_year
//                              + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                              + " and VOUCHER_NO="+txtVoucher_No
//                              + " and SL_NO=1 )"
//                               + " ,CHEQUE_DD_DATE= (select CHEQUE_DD_DATE from fas_payment_transaction where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode
//                              + " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
//                              + " and CASHBOOK_YEAR="+txtCash_year
//                              + " and CASHBOOK_MONTH="+txtCash_Month_hid
//                              + " and VOUCHER_NO="+txtVoucher_No
//                              + " and SL_NO=1 )"
//                              + " ,cb_ref_no="+crefvr
//                              + " ,cb_ref_date=?"
//                              + " ,MULTIPLE_PVRS='Y', UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and cb_ref_no=? AND cb_ref_date=? "
//                              + " and MULTIPLE_PVR_DETAILS="+presentMvrdetails;
//                    	 
//                 		System.out.println("qry  "+sql_mvrDate_JT);
//                          try{  
//                          	
//           
//                          	PreparedStatement   psMVRD = conMvr.prepareStatement(sql_mvrDate_JT);
//                          
//
//                          /** PARAMETER PASSING */
//                          System.out.println(txtCash_year+" > "+txtCash_Month_hid+" > "+txtVoucher_No+" > ");
//                       
//                          
//                          psMVRD.setDate(1, cb_ref_date);
//                          psMVRD.setString(2, update_user);
//                          System.out.println("update_user---->"+update_user);
//                          psMVRD.setTimestamp(3, ts);
//                          System.out.println("ts---->"+ts);
//                          psMVRD.setInt(4, cmbAcc_UnitCode);
//                          System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
//                          psMVRD.setInt(5, cmbOffice_code);
//                          System.out.println("cmbOffice_code---->"+cmbOffice_code);
//                     
//                          psMVRD.setInt(6, txtVoucher_No);
//                          System.out.println("txtVoucher_No---->"+txtVoucher_No);
//                          psMVRD.setDate(7, txtCrea_date);
//                          System.out.println("txtCrea_date"+txtCrea_date);
//                        
//                          
//                          int rkk1= psMVRD.executeUpdate();
//                          if (rkk1>0)
//                          {
//                       	   kk1=rkk1;
//                          }
//                          
//                          }
//                          
//                          catch(SQLException e){
//                  	   System.out.println("Excep 111111");
//                  	  e.printStackTrace(); 
//                          }
//                 	}
//      		     	
//      		      System.out.println("kk1===>"+kk1);
//      		      /*@NK on 21-Sep-2019 For cbrefType updation*/	
//      		     	
//      		 	 Integer[]receiptno = new Integer[20];
//                 String[]receiptdate = new String[20];
//               
//                 if((kk1>0)&&(mpvr==null))
//               {
//                	 String originalDateString =  txtCreadate;
//                	 
//                	 SimpleDateFormat originalDateFormat = new SimpleDateFormat("dd/mm/yyyy");
//             		
//           		  java.util.Date originalDate = null;
//           		try {
//           			originalDate =  originalDateFormat.parse(originalDateString);
//           		} catch (Exception e) {
//           			// TODO Auto-generated catch block
//           			e.printStackTrace();
//           		}
//
//                     // Define the desired date format
//                     SimpleDateFormat desiredDateFormat = new SimpleDateFormat("yyyy-mm-dd");
//
//                     // Format the Date object using the desired format
//                     String desiredDateString = desiredDateFormat.format(originalDate); 
//                	 
//                	 
//      	    	 String combPaynodate="'"+txtVoucherNo+desiredDateString+"'";
//      	    	 
//      	    	 
//      	    	 
//      	    	 
//      	    	 
//      	    	 
//      	   	 
//      	    	String sqlCbreftype = ""
//      	    			+ "select  mm.receipt_no,mm.cashbook_year,mm.cashbook_month,mm.receipt_date,mm.total_trn_records,ct.totalsl from fas_receipt_master mm , "
//      	    			+ "( "
//      	    			+ " "
//      	    			+ " "
//      	    			+ "select  receipt_no,cashbook_year,cashbook_month,count(*) as totalsl from "
//      	    			+ "( "
//      	    			+ " "
//      	    			+ " "
//      	    			+ " "
//      	    			+ "select m.receipt_no,  m.cashbook_year,m.cashbook_month from fas_receipt_master m, "
//      	    			+ "(SELECT DISTINCT RT.ACCOUNTING_UNIT_ID, "
//      	    			+ "      rt.accounting_for_office_id, "
//      	    			+ "      RT.RECEIPT_NO, "
//      	    			+ "      RT.SL_NO, "
//      	    			+ "      RM.RECEIPT_DATE, "
//      	    			+ "      RT.CASHBOOK_YEAR, "
//      	    			+ "      RT.CASHBOOK_MONTH, "
//      	    			+ "      RT.CB_REF_NO, "
//      	    			+ "      RT.CB_REF_DATE, "
//      	    			+ "      count(*) as totalsl "
//      	    			+ "    FROM FAS_RECEIPT_TRANSACTION RT, "
//      	    			+ "      FAS_RECEIPT_MASTER RM "
//      	    			+ "    WHERE RT.ACCOUNTING_UNIT_ID                                             =? "
//      	    			+ "    AND RT.ACCOUNTING_FOR_OFFICE_ID                                         = ? "
//      	    			+ "    AND RT.RECEIPT_NO                                                       = RM.RECEIPT_NO "
//      	    			+ "    AND RT.RECEIPT_NO                                                       =RM.RECEIPT_NO "
//      	    			+ "    AND RT.CASHBOOK_YEAR                                                    = RM.CASHBOOK_YEAR "
//      	    			+ "    AND RT.CASHBOOK_MONTH                                                   =RM.CASHBOOK_MONTH "
//      	    			+ "    AND (CONCAT((RT.CB_REF_NO),(RT.CB_REF_DATE)::varchar) ="+combPaynodate
//      	    			+ " ) "
//      	    			+ "    AND LENGTH(RT.MULTIPLE_PVR_DETAILS)                                    <=17 "
//      	    			+ "    group by "
//      	    			+ "      RT.ACCOUNTING_UNIT_ID, "
//      	    			+ "      rt.accounting_for_office_id, "
//      	    			+ "      RT.RECEIPT_NO, "
//      	    			+ "       RT.SL_NO, "
//      	    			+ "      RM.RECEIPT_DATE, "
//      	    			+ "      RT.CASHBOOK_YEAR, "
//      	    			+ "      RT.CASHBOOK_MONTH, "
//      	    			+ "      RT.CB_REF_NO, "
//      	    			+ "      RT.CB_REF_DATE) x "
//      	    			+ "      where m.accounting_unit_id = x.accounting_unit_id AND m.ACCOUNTING_FOR_OFFICE_ID= x.accounting_for_office_id "
//      	    			+ "    AND m.RECEIPT_NO                                                       = x.RECEIPT_NO "
//      	    			+ "    AND m.CASHBOOK_YEAR                                                    = x.CASHBOOK_YEAR "
//      	    			+ "    AND m.CASHBOOK_MONTH                                                   =x.CASHBOOK_MONTH "
//      	    			+ "     "
//      	    			+ "     "
//      	    			+ "     "
//      	    			+ "    union all "
//      	    			+ "    "
//      	    			+ "    "
//      	    			+ "    SELECT receipt_no,cashbook_year,cashbook_month "
//      	    			+ "FROM "
//      	    			+ "  (SELECT DISTINCT T.RECEIPT_NO , "
//      	    			+ "    M.RECEIPT_DATE, "
//      	    			+ "    t.sl_no, "
//      	    			+ "    t.cashbook_year, "
//      	    			+ "    t.cashbook_month, "
//      	    			+ "    t.cb_ref_no, "
//      	    			+ "    t.cb_ref_date "
//      	    			+ "  FROM FAS_RECEIPT_TRANSACTION T, "
//      	    			+ "    FAS_RECEIPT_MASTER M, "
//      	    			+ "    (SELECT RT.ACCOUNTING_UNIT_ID, "
//      	    			+ "      RT.RECEIPT_NO, "
//      	    			+ "      RT.CASHBOOK_YEAR, "
//      	    			+ "      RT.CASHBOOK_MONTH, "
//      	    			+ "      RT.CB_REF_NO, "
//      	    			+ "      RT.CB_REF_DATE "
//      	    			+ "    FROM FAS_RECEIPT_TRANSACTION RT "
//      	    			+ "    WHERE RT.ACCOUNTING_UNIT_ID                                             =? "
//      	    			+ "    AND RT.ACCOUNTING_FOR_OFFICE_ID                                         =? "
//      	    			+ "    AND (concat((RT.cb_ref_no),(RT.cb_ref_date)::varchar) ="+combPaynodate
//      	    			+ " ) "
//      	    			+ "    ) x "
//      	    			+ "  WHERE t.accounting_unit_id                                              = x.accounting_unit_id "
//      	    			+ "  AND t.cashbook_year                                                     = x.cashbook_year "
//      	    			+ "  AND t.cashbook_month                                                    =x.cashbook_month "
//      	    			+ "  AND T.RECEIPT_NO                                                        = X.RECEIPT_NO "
//      	    			+ "  AND M.RECEIPT_NO                                                        =X.RECEIPT_NO "
//      	    			+ "  AND M.CASHBOOK_YEAR                                                     = X.CASHBOOK_YEAR "
//      	    			+ "  AND M.CASHBOOK_MONTH                                                    =X.CASHBOOK_MONTH "
//      	    			+ "  AND M.ACCOUNTING_UNIT_ID                                                = T.ACCOUNTING_UNIT_ID "
//      	    			+ "  AND M.ACCOUNTING_FOR_OFFICE_ID                                          = T.ACCOUNTING_FOR_OFFICE_ID "
//      	    			+ "    AND ((T.CB_REF_NO                                                      IS NULL) "
//      	    			+ "      OR(T.CB_REF_NO                                                          =0))"
//      	    			+ "  ) Y "
//      	    			+ "   "
//      	    			+ "   "
//      	    			+ "  ) newm "
//      	    			+ "  group by receipt_no,cashbook_year,cashbook_month) "
//      	    			+ "   "
//      	    			+ "   "
//      	    			+ "   "
//      	    			+ "  ct "
//      	    			+ "   where mm.accounting_unit_id = ? AND mm.accounting_for_office_id =? "
//      	    			+ "    AND mm.RECEIPT_NO                                                       = ct.RECEIPT_NO "
//      	    			+ "    AND mm.CASHBOOK_YEAR                                                    = ct.CASHBOOK_YEAR "
//      	    			+ "    AND MM.CASHBOOK_MONTH                                                   =CT.CASHBOOK_MONTH "
//      	    			+ "    and mm.total_trn_records = totalsl";
//
//
//
//      	    	try{
//      	    		 System.out.println("sqlCbreftype---->"+sqlCbreftype);
//      	    	 ps=con.prepareStatement(sqlCbreftype);
//      	    	 
//      	    	ps.setInt(1, cmbAcc_UnitCode);
//                System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
//      	    	 
//                ps.setInt(2, cmbOffice_code);
//                System.out.println("cmbOffice_code---->"+cmbOffice_code);
//                
//                ps.setInt(3, cmbAcc_UnitCode);
//                System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
//      	    	 
//                ps.setInt(4, cmbOffice_code);
//                System.out.println("cmbOffice_code---->"+cmbOffice_code);
//                
//                ps.setInt(5, cmbAcc_UnitCode);
//                System.out.println("cmbAcc_UnitCode---->"+cmbAcc_UnitCode);
//      	    	 
//                ps.setInt(6, cmbOffice_code);
//                System.out.println("cmbOffice_code---->"+cmbOffice_code);
//                
//                
//                 rs11=ps.executeQuery();  
//      	    	   
//                   	while(rs11.next())
//                   	{
//                   		System.out.println("inside while loop....");
//                   		receipt_no=rs11.getInt("RECEIPT_NO")	;
//                   		receipt_dt=rs11.getString("RECEIPT_DATE");
//                   		receiptno[count]=receipt_no;
//                   		receiptdate[count]=receipt_dt;
//                      	count++;
//                   	
//                   	}
//              	    	
//                   	}
//         			catch(Exception e)
//         			{
//         				e.printStackTrace();
//         			}
//         			finally
//         			{
//         				if(ps!=null)
//         				{
//         					ps.close();
//         				}
//         				if(rs11!=null)
//         				{
//         					rs11.close();
//         				}
//                   		
//                   	}
//      	    	
//                   		for(int j=0;j<count;j++)
//                   		{
//                   		 int recno=receiptno[j];
//         				 String recd=receiptdate[j];
//         				
//         				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//         				java.util.Date date = inputFormat.parse(recd);
//
//         				// Format date into output format
//         				DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
//         				String receipt_date = outputFormat.format(date);
//                   	System.out.println("count for total number of records for cbreftype upation-->"+count);
//                   	
//                 	   String sql_recMaster =
//                          "update FAS_RECEIPT_MASTER mas set mas.CB_REF_TYPE=null,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where" +
//                          "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.RECEIPT_NO=? and mas.RECEIPT_DATE=?";
//                 	   ps3 = con.prepareStatement(sql_recMaster);   
//                 	   ps3.setString(1, update_user);System.out.println("update_user"+update_user);
//                 	   ps3.setTimestamp(2, ts);System.out.println("ts"+ts);
//                 	   ps3.setInt(3, cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
//                 	   ps3.setInt(4, cmbOffice_code);System.out.println("cmbOffice_code"+cmbOffice_code);
//                 	   ps3.setInt(5, recno);System.out.println("receipt_no"+recno);
//                 	   ps3.setString(6, receipt_date);System.out.println("receipt_dt"+receipt_date);
//                        kk2=ps3.executeUpdate();
//                       
//                        System.out.println("Result.....kk2....."+kk2+"CB_REF_TYPE updated for RECEIPT_NO= "+recno);
//                    }//end for loop
//                   	
//                   		if(count==0&&kk2==0)
//                        {
//                     	   kk2++;
//                     	   System.out.println("Result.....kk2...WithoutAny.CB_REF_TYPE updation."+kk2);
//                        }
//                   	
//                   	
//                   		} //end if
//                   		
//                 else if((kk1>0)&&(mpvr!=null)&&(kk2==0))
//        	       {
//        	    	 kk2++;
//             	   System.out.println("Result.....kk2...WithoutAny.CB_REF_TYPE updation."+kk2);
//        	       }         	
//      		     	
//                 /*@NK on 21-Sep-2019 For cbrefType updation*/
//      		     	
//      				 
//      				 
//      			}//for loop end
//      			
//      			 System.out.println("kk1===>"+kk1);
//                 
//      		
//      		   }
//      		   catch (Exception e)
//      		   {
//      			   e.printStackTrace(); 
//      		   }
//     		   finally
//     	        { 
//     			   conMvr.setAutoCommit(true);
//     			  conMvr.close();
//     	        } 
//     		   
//  	   
//      	   }
            
          
            /*@NK Include on 03072019 for multiplePvrs updation*/ 
            
        	   
            if(kk > 0){
            	jj=jj+kk;
            }
            if(kk2>0)
            {
            	jj=jj+kk2;
            }

              System.out.println("jjk >> "+jj);

    
           }catch(Exception e){
           	 e.printStackTrace();
            }
if(jj > 0){
                cs1 =
 con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
                cs1.setInt(1, cmbAcc_UnitCode);
                cs1.setInt(2, txtCash_year);
                cs1.setInt(3, txtCash_Month_hid);
                cs1.setInt(4, txtVoucher_No);
                cs1.setInt(5, cmbOffice_code);
                cs1.setDate(6, txtCrea_date);
                cs1.setString(7, "BPP");
                cs1.setString(8, txtReferNO_edit);
                cs1.setDate(9, txtReferDate_edit);
                cs1.setString(10, txtRemak_edit);
                cs1.setInt(11, txtAuth_By);
                cs1.setString(12, "insert");
                cs1.setInt(13, 0);
                cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
                 cs1.setString(14, update_user);
                cs1.setTimestamp(15, ts);
                cs1.setString(16, radAuth_MC);

                /** INSERTION INTO CROSS REFERENCE TABLE */
                cs1.execute();

                /** GET ERROR CODE */
//                int errcode = cs1.getInt(13);
                int errcode = cs1.getBigDecimal(13).intValue(); 

                System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                System.out.println("cmbOffice_code " + cmbOffice_code);
                System.out.println("txtCrea_date " + txtCrea_date);
                System.out.println("txtCash_year " + txtCash_year);
                System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
                System.out.println("SQLCODE:::" + errcode);


                if (errcode != 0) {
                    con.rollback();
                    sendMessage(response,
                                "The Bank Payment Cancellation Failed ", "ok");
                    
                    xml = xml + "<flag>failure</flag>";
                    return;
                }

                /** FINAL SAVINGS */
                con.commit();
                sendMessage(response,
                            "The Bank Payment Voucher Number '" + txtVoucher_No +
                            "' has been Cancelled Successfully ", "ok");
                return;
}
           }
} catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
                sendMessage(response, "The Bank Payment Cancellation Failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
                return;
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
            }
        
        }

    }

    /**
     * do Get Function
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public

    void doGet(HttpServletRequest request,
               HttpServletResponse response) throws ServletException,
                                                    IOException {


        /**
       * Session Checking
       */

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


        /**
        * Varialbes Declaration
        */

        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;
        String xml = "";
        String strCommand = "";
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;


        /**
        *  Database Connection
        */

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


        /**
         * Content Type Setting
         */

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();


        /**
      *  Get Command
      */

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /** Get Accouinting Unit Id */

        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


        /** Get Accounting Office Id */

        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);


        /**
       *  Load Voucher Number and Corresponding Details
       */

        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            xml = "<response><command>load_Voucher_No</command>";

            try {
                String[] sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);
                ps =
  con.prepareStatement("select i.VOUCHER_NO from FAS_PAYMENT_MASTER i,FAS_CROSS_REFERENCE c where " +
                       " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.PAYMENT_DATE=? and PAYMENT_TYPE='B' and i.PAYMENT_STATUS!='C'  and CREATED_BY_MODULE='BPP' " +
                       " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                       " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.VOUCHER_NO=c.VOUCHER_NO " +
                       " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE='BPP'");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                rs = ps.executeQuery();

                System.out.println("select i.VOUCHER_NO from FAS_PAYMENT_MASTER i,FAS_CROSS_REFERENCE c where " + 
                		"                       i.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+"  and i.ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and i.PAYMENT_DATE="+txtCrea_date+" and PAYMENT_TYPE='B' and i.PAYMENT_STATUS!='C'  and CREATED_BY_MODULE='BPP'" + 
                		"                       and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID" + 
                		"                        and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.VOUCHER_NO=c.VOUCHER_NO" + 
                		"                       and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE='BPP'");
                
                
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
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (strCommand.equalsIgnoreCase("load_Voucher_Details")) {
System.out.println("load voucher details ... ");
            /** VARAILBES DECLARATION */
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            xml = "<response><command>load_Voucher_Details</command>";
            int txtVoucher_No = 0;

            /** GET VOUCHER NUMBER */
            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);


            try {
                /** GET DATE */
                String[] sd = request.getParameter("txtCrea_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtCrea_date = new Date(d.getTime());
                System.out.println("txtCrea_date " + txtCrea_date);

                /** SQL QUERY */
                String sql_stmt =
                    "" + " select                                \n" +
                    "          ACCOUNT_HEAD_CODE,           \n" +
                    "          CASHBOOK_YEAR,               \n" +
                    "          CASHBOOK_MONTH,              \n" +
                    "          BANK_ID,                     \n" +
                    "          BRANCH_ID,                   \n" +
                    "          ACCOUNT_NO,                  \n" +
                    "          trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,     \n" +
                    "          TOTAL_TRN_RECORDS,           \n" +
                    "          SUB_LEDGER_TYPE_CODE,        \n" +
                    "          SUB_LEDGER_CODE,             \n" +
                    "          PAID_TO,JOURNAL_TYPE_CODE ,REMARKS , \n" +
                    "          PART_PAYMENT,                \n" +
                    "          trim(to_char(PART_AMOUNT,'99999999999999.99')) as PART_AMOUNT    \n" +
                    "                                                                           \n" +
                    " from                                                                      \n" +
                    "          FAS_PAYMENT_MASTER           \n" +
                    "                                       \n" +
                    " where                                 \n" +
                    "         ACCOUNTING_UNIT_ID=?          \n" +
                    "     and ACCOUNTING_FOR_OFFICE_ID=?    \n" +
                    "     and PAYMENT_DATE=?                \n" +
                    "     and VOUCHER_NO=?                  \n ";

                /** PARAMETER PASSING */
                System.out.println("sql_stmt::"+sql_stmt);
                ps = con.prepareStatement(sql_stmt);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtVoucher_No);

                /** QUERY EXECUTION */
                rs = ps.executeQuery();
                
             /* @NK nanda restriction for subsequent payment cancellation is added*/  
           
                String paymentcancel_stmt= ""
                		+ "SELECT "
                		+ "op2.PAYABLE_VOUCHER_TYPE as type, "
                		+ "op2.NO_OF_TRANSACTION_PT, "
                		+ "( op2.rec_count + op2.jr_count ) AS TRANSACTION_AGAINSTPAYMENT " 
                		+ "	FROM "
                		+ "	( "
                		+ "SELECT "
                		+ "	op1.PAYABLE_VOUCHER_TYPE AS PAYABLE_VOUCHER_TYPE,"
                		+ "	op1.NO_OF_TRANSACTION_PT AS NO_OF_TRANSACTION_PT, "
                		+ "	CASE "
                		+ "		WHEN op1.r_count IS NULL THEN "
                		+ "	0 ELSE op1.r_count "
                		+ "END AS rec_count,"
                		+ "CASE "
                		+ "	WHEN op1.j_count IS NULL THEN "
                		+ "0 ELSE op1.j_count "
                		+ "	END AS jr_count "
                		+ "FROM "
                		+ "( "
                		+ "( "
                		+ "SELECT "
                		+ "	PM.PAYABLE_VOUCHER_TYPE, "
                		+ "	PM.voucher_no, "
                		+ "	COUNT ( PT.VOUCHER_NO ) AS NO_OF_TRANSACTION_PT "
                		+ "FROM "
                				+ "	FAS_PAYMENT_MASTER PM, "
                				+ "	FAS_PAYMENT_TRANSACTION PT "
                				+ "WHERE "
                				+ "	PM.ACCOUNTING_UNIT_ID = PT.ACCOUNTING_UNIT_ID "
                				+ "	AND PM.CASHBOOK_YEAR = PT.CASHBOOK_YEAR "
                				+ "	AND PM.CASHBOOK_MONTH = PT.CASHBOOK_MONTH "
                				+ "	AND PM.VOUCHER_NO = PT.VOUCHER_NO "
                				+ "	AND PM.ACCOUNTING_UNIT_ID = ? "
                				+ "	AND PM.CASHBOOK_YEAR = ? "
                				+ "	AND PM.CASHBOOK_MONTH = ? "
                				+ "	AND PM.VOUCHER_NO = ? "
                				+ "	AND PM.PAYMENT_STATUS = 'L' "
                				+ "GROUP BY "
                					+ "PM.PAYABLE_VOUCHER_TYPE, "
                				+ "	PM.voucher_no "
                				+ ") MASTER "
                				+ "LEFT JOIN ( "
                				+ "SELECT COUNT "
                				+ "	( RT.CB_REF_NO ) AS r_count, "
                				+ "	RT.CB_REF_NO "
                				+ "FROM "
                				+ "	FAS_RECEIPT_TRANSACTION RT "
                				+ "WHERE "
                				+ "	RT.ACCOUNTING_UNIT_ID = ? "
                				+ "	AND RT.CB_REF_DATE = ? "
                				+ "	AND RT.CB_REF_NO = ? "
                				+ "GROUP BY "
                				+ "	RT.CB_REF_NO " 
                				+ ") opt1 ON Master.voucher_no = opt1.cb_ref_no "
                				+ "LEFT JOIN ( "
                				+ "SELECT COUNT"
                					+ "( JT.CB_ADJ_REF_NO ) AS j_count,"
                					+ "JT.CB_ADJ_REF_NO "
                			+ "	FROM "
                				+ "	FAS_JOURNAL_TRANSACTION JT "
                				+ "WHERE "
                					+ "JT.ACCOUNTING_UNIT_ID = ? "
                					+ "AND JT.CB_ADJ_REF_DATE = ? "
                					+ "AND JT.CB_ADJ_REF_NO = ? "
                					+ "OR ( JT.CB_ADJ_REF_DATE IS NOT NULL AND JT.CB_ADJ_REF_NO IS NOT NULL AND JT.CB_TDCA_REF_DATE = ? AND JT.CB_TDCA_REF_NO = ? ) "
                					+ "OR ( JT.CB_ADJ_REF_DATE IS NOT NULL AND JT.CB_ADJ_REF_NO IS NOT NULL AND JT.CB_TPA_REF_DATE = ? AND JT.CB_TPA_REF_NO = ? ) "
                			+ "	GROUP BY "
                				+ "	JT.CB_ADJ_REF_NO "
                			+ "	) opt2 ON opt2.cb_adj_ref_no = Master.voucher_no "
                			+ ") op1 "
                	+ "	) op2 ";
                System.out.println("sql_stmt::"+paymentcancel_stmt);
                ps4 = con.prepareStatement(paymentcancel_stmt);
                
                String Receipt_Creation_Date =
                        request.getParameter("txtCrea_date");

                    /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
                    Com_CashBook1 cb = new Com_CashBook1();

                    /** Assign Cashbook Year and Month to year_month Variable */
                    String year_month = cb.cb_date(Receipt_Creation_Date).toString();
                  //  int ss= 0;int kk=0,kk1=0,kk2=0;
                    /** Split Cash Book Year and Month */
                    String[] ym = year_month.split("/");
                    
                    System.out.println("printing the year_month*****"+ym);

                    /** Assign Year and Month */
                  int  txtCash_year = Integer.parseInt(ym[0]);
                   int  txtCash_Month_hid = Integer.parseInt(ym[1]);
                
                ps4.setInt(1, cmbAcc_UnitCode);
                ps4.setInt(2, txtCash_year); //txtCash_year
                ps4.setInt(3, txtCash_Month_hid);//txtCash_Month_hid
                
                ps4.setInt(4, txtVoucher_No);//txtVoucher_No
                ps4.setInt(5, cmbAcc_UnitCode);//cmbAcc_UnitCode
                ps4.setDate(6, txtCrea_date);//txtCrea_date
                
                ps4.setInt(7, txtVoucher_No);//txtVoucher_No
                ps4.setInt(8, cmbAcc_UnitCode);//cmbAcc_UnitCode
                
                ps4.setDate(9, txtCrea_date);//txtCrea_date
                ps4.setInt(10, txtVoucher_No);//txtVoucher_No
                
                ps4.setDate(11, txtCrea_date);//txtCrea_date
                ps4.setInt(12, txtVoucher_No);//txtVoucher_No
                
                ps4.setDate(13, txtCrea_date);//txtCrea_date
                ps4.setInt(14, txtVoucher_No);//txtVoucher_No
               
                rs4=ps4.executeQuery();
           int nooftrans=0,nooftranspay=0;
           String type=null;
                if (rs4 != null) {
                    while (rs4.next()) {
                    	type=  rs4.getString("type");
                 	nooftrans= rs4.getInt("No_of_transaction_pt");
                 	nooftranspay=rs4.getInt("transaction_againstpayment");
                       
                    }
                    System.out.println("type"+type+"No_of_transaction_pt"+nooftrans+"transactionagainstpayment"+nooftranspay);
                    rs4.close();
                }
                /*end of @NK nanda restriction for subsequent payment cancellation is added*/ 
if(nooftrans==nooftranspay){
                /** RESULTSET HANDLING */
                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<MasHeadCode>" + rs.getString("ACCOUNT_HEAD_CODE").trim() +
   "</MasHeadCode>";

                    xml = xml + "<accNo>" + rs.getString("ACCOUNT_NO").trim() + "</accNo>";
                    /** SQL QUERY */
                    ps3 =
 con.prepareStatement("   " + "select                                                                              \n" +
                      "      bk.BANK_NAME ||'-' ||br.BRANCH_NAME || '-' ||coalesce(br.CITY_TOWN_NAME,'') as bankNAME    \n" +
                      "from                                                                                \n" +
                      "      FAS_MST_BANKS bk,                                                             \n" +
                      "      FAS_MST_BANK_BRANCHES br                                                      \n" +
                      "where                                                                               \n" +
                      "        br.BANK_ID=?                                                                \n" +
                      "    and br.BRANCH_ID=?                                                              \n" +
                      "    and br.BANK_ID=bk.BANK_ID");


                    /** PARAMETER PASSING */
                    ps3.setInt(1, rs.getInt("BANK_ID"));
                    ps3.setInt(2, rs.getInt("BRANCH_ID"));

                    /** QUERY EXECUTION */
                    rs3 = ps3.executeQuery();

                    System.out.println("FAS_PAYMENT_MASTER");
                    if (rs3.next())
                    {
                        xml = xml + "<bk_name>" + rs3.getString("bankNAME") + "</bk_name>";
                    }
                    else
                    {
                    	xml = xml + "<bk_name>" + "-"+ "</bk_name>";
                    }
                    rs3.close();
                    ps3.close();

                    xml =
 xml + "<bk_id>" + rs.getInt("BANK_ID") + "</bk_id><br_id>" +
   rs.getInt("BRANCH_ID") + "</br_id><Total_amt>" +
   rs.getString("TOTAL_AMOUNT") + "</Total_amt><No_TRN_Rec>" +
   rs.getInt("TOTAL_TRN_RECORDS") + "</No_TRN_Rec><Mas_paid><![CDATA[" +
   rs.getString("PAID_TO") + "]]></Mas_paid><Remak><![CDATA[" + rs.getString("REMARKS").replace("", "-") +
   "]]></Remak><Mas_SL_type>" + rs.getInt("SUB_LEDGER_TYPE_CODE") +
   "</Mas_SL_type><Mas_SL_code>" + rs.getInt("SUB_LEDGER_CODE") +
   "</Mas_SL_code>" + "<partPay>" + rs.getString("PART_PAYMENT") +
   "</partPay>" + "<partAmt>" + rs.getString("PART_AMOUNT") + "</partAmt>";

                } // Resultset End

                // Start of fetching sub-Ledger   , here u r passing parameters to the function getResult_General which is inside the class SL_TYPE_CODE_NAME_GENERAL
                if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                    SL_TYPE_CODE_NAME_GENERAL obj_gen =
                        new SL_TYPE_CODE_NAME_GENERAL();
                    ResultSet rs_get =
                        obj_gen.getResult_General(cmbAcc_UnitCode,
                                                  cmbOffice_code,
                                                  rs.getInt("SUB_LEDGER_TYPE_CODE"),
                                                  rs.getInt("SUB_LEDGER_CODE"),
                                                  0);
                    if (rs_get != null) {
                        while (rs_get.next()) {
                            System.out.println(rs_get.getString("cid"));
                            System.out.println(rs_get.getString("cname"));
                            xml =
 xml + "<cid>" + rs_get.getInt("cid") + "</cid><cname><![CDATA[" +
   rs_get.getString("cname") + "]]></cname>";
                        }
                        rs_get.close();
                    } else
                        System.out.println("null result set");
                }
                // End of fetching sub-Ledger

                System.out.println("in b/w here");
System.out.println("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ,SUB_LEDGER_CODE " +
                      ",CHEQUE_OR_DD ,CHEQUE_DD_NO ,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date ,AGREEMENT_NO," +
                      "to_char(AGREEMENT_DATE,'DD/MM/YYYY') as Agree_date ,BILL_NO, BILL_TYPE, to_char(BILL_DATE,'DD/MM/YYYY') as Bill_date, " +
                      "PAID_TO,trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS,PAYABLE_VOUCHER_NO,to_char(PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') as pay_voudate  from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
                      "ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+rs.getInt("CASHBOOK_YEAR")+" and CASHBOOK_MONTH="+rs.getInt("CASHBOOK_MONTH")+" and VOUCHER_NO="+txtVoucher_No);
                ps2 =
 con.prepareStatement("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ,SUB_LEDGER_CODE " +
                      ",CHEQUE_OR_DD ,CHEQUE_DD_NO ,to_char(CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date ,AGREEMENT_NO," +
                      "to_char(AGREEMENT_DATE,'DD/MM/YYYY') as Agree_date ,BILL_NO, BILL_TYPE, to_char(BILL_DATE,'DD/MM/YYYY') as Bill_date, " +
                      "PAID_TO,trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS,PAYABLE_VOUCHER_NO,to_char(PAYABLE_VOUCHER_DATE,'DD/MM/YYYY') as pay_voudate  from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID=? and " +
                      "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?::numeric and CASHBOOK_MONTH=? and VOUCHER_NO=?");
            System.out.println("ps2 >> "+ps2);
                ps2.setInt(1, cmbAcc_UnitCode);
                ps2.setInt(2, cmbOffice_code);
                ps2.setString(3, rs.getString("CASHBOOK_YEAR"));
                ps2.setInt(4, rs.getInt("CASHBOOK_MONTH"));
                ps2.setInt(5, txtVoucher_No);
                rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    xml =
 xml + "<AHcode>" + rs2.getInt("ACCOUNT_HEAD_CODE") + "</AHcode>";
                    ps3 =
 con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
                    rs3 = ps3.executeQuery();
                    if (rs3.next())
                        xml =
 xml + "<AHdesc>" + rs3.getString("ACCOUNT_HEAD_DESC") + "</AHdesc>";
                    ps3.close();
                    rs3.close();
                    xml =
 xml + "<CR_DR_ind>" + rs2.getString("CR_DR_INDICATOR") +
   "</CR_DR_ind><SL_Type>" + rs2.getInt("SUB_LEDGER_TYPE_CODE") + "</SL_Type>";
                    if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                        System.out.println("take SL DESC");
                        ps3 =
 con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                        ps3.setInt(1, rs2.getInt("SUB_LEDGER_TYPE_CODE"));
                        rs3 = ps3.executeQuery();
                        if (rs3.next())
                            xml =
 xml + "<SL_Desc>" + rs3.getString("SUB_LEDGER_TYPE_DESC") + "</SL_Desc>";
                    } else {
                        xml =
 xml + "<SL_Desc>" + null + "</SL_Desc>"; // it also return null value
                        System.out.println("else part  23");
                    }
                    try {
                        rs3.close();
                        ps3.close();
                    } catch (Exception e) {
                        System.out.println("closing recordset error");
                    }

                    xml =
 xml + "<SL_Code>" + rs2.getInt("SUB_LEDGER_CODE") + "</SL_Code>";

                    if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
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
                                System.out.println(rs_det.getString("cname"));
                                xml =
 xml + "<desc_type><![CDATA[" + rs_det.getString("cname") + "]]></desc_type>";
                            }
                            rs_det.close();
                        } else
                            System.out.println("null result set");
                    } else {
                        xml = xml + "<desc_type>" + null + "</desc_type>";
                    }


                    // *** it's from master table
                        xml =
                            xml + "<che_or_DD>" + rs2.getString("CHEQUE_OR_DD") +
                            "</che_or_DD>" + "<che_DD_no>" +
                            rs2.getString("CHEQUE_DD_NO") + "</che_DD_no>" +
                            "<che_DD_date>" + rs2.getString("cheq_dd_date") +
                            "</che_DD_date>" + "<billno>" +
                            rs2.getString("BILL_NO").replace("", "-") + "</billno>" +
//                            "<billdate>" + rs2.getString("Bill_date").replace("", "-") +
							"<billdate>" + rs2.getString("Bill_date") +
                            "</billdate>" + "<billtype>" +
                            rs2.getString("BILL_TYPE") + "</billtype>" +
                            "<Agree_no>" + rs2.getString("AGREEMENT_NO") +
                            "</Agree_no>" + "<Agree_date>" +
                            rs2.getString("Agree_date") + "</Agree_date>" +
                            "<sub_paidto>" + rs2.getString("PAID_TO") +
                            "</sub_paidto>" + "<sub_amount>" +
                            rs2.getString("AMOUNT") + "</sub_amount>" +
                            "<pay_vou_no>" + rs2.getInt("PAYABLE_VOUCHER_NO") +
                            "</pay_vou_no>" + "<pay_vou_date>" +
                            rs2.getString("pay_voudate") + "</pay_vou_date>" +
                            "<jour_code>" + rs.getString("JOURNAL_TYPE_CODE") +
                            "</jour_code>" + "<sub_part><![CDATA[" +
                            rs2.getString("PARTICULARS").replace("", "-") + "]]></sub_part>";
                }
}
else{
	xml = xml + "<flag>failure</flag>";
}

            } catch (Exception e) {
                System.out.println("catch..HERE.in failure to retrieve." + e);
                xml = xml + "<flag>failure</flag>";
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
            return;
        } catch (IOException e) {
        }
    }
}
package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

import Servlets.FAS.FAS1.CommonControls2.servlets.TBFreezeCheck;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Auto_Fund_Receipt_Create_atHOServ extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";


   public void doGet(HttpServletRequest request, 
                     HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
     
       
        /** Session Checking */               
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
        
      
        
        /** Variables Declaration */
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        
        ResultSet rs2=null,rs3=null;
        PreparedStatement ps2=null,ps3=null;
        
      
      
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
                                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                Class.forName(strDriver.trim());
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
        
        
        
        /** Variables Declaration */
        int cmbAcc_UnitCode=0;
        int cmbOffice_code=0;   
        String remit_type="";
        String col_unspent="";
        String xml="";
        String strCommand="";           
        String displayingOrder="";
        int txtRegionId=0;
        String txtBankAccountNo="";
        
        
        
        /** Get Command Parameter */
        try 
        {
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        
        
        /** Get Accounting Unit ID */
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
           
           
           
        /** Get Accounting for Office ID */
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbOffice_code "+cmbOffice_code);
        
        
           
        /** Account Type */
        try{remit_type=request.getParameter("remit_type");}
        catch(Exception e){System.out.println("exception"+e );}
        System.out.println("remit_type "+remit_type);
        
        
        
        if ( remit_type.equalsIgnoreCase("C,U,NM,NS,NC,UNM,UNS,UNC,FDW,WATCHARGEREV_Hogenakkal,WATCHARGEREV,NONWATCHARGEREV,FieldKit,FDW from Collection,LB100PCNTCONTRIB,JICA,Security Deposit,KFW,UIDDSMT"))
        {
        	remit_type="C','U','NM','NS','NC','UNM','UNS','UNC','NRDWP-WQM-SP','FDW','WATCHARGEREV_Hogenakkal','WATCHARGEREV','NONWATCHARGEREV','FieldKit','FDW from Collection','LB100PCNTCONTRIB','JICA','Security Deposit','KFW','UIDDSMT";
        	System.out.println("*****************>>>"+remit_type);
        	
        	//remit_type="C','U','NM','NS','NC','UNM','UNS','UNC','NRDWP-WQM-SP','FDW";
                col_unspent="COL','OPR','OPR-NRDWP-Main','OPR-NRDWP-Support','NRDWP-WQM-SP','OPR-NRDWP-Calamity','FDW','WATCHARGEREV_Hogenakkal','WATCHARGEREV','NONWATCHARGEREV','FieldKit','FDW from Collection','LB100PCNTCONTRIB','JICA','Security Deposit','KFW','UIDDSMT";
        
        }
        else if ( remit_type.equalsIgnoreCase("U"))
        {
            col_unspent="OPR";            
        }
        else if ( remit_type.equalsIgnoreCase("C"))
        {
            col_unspent="COL";
        }
        else if ( remit_type.equalsIgnoreCase("NM") ||remit_type.equalsIgnoreCase("UNM")  )
        {
            col_unspent="OPR-NRDWP-Main";
        }
        else if ( remit_type.equalsIgnoreCase("NS")||remit_type.equalsIgnoreCase("UNS"))
        {
            col_unspent="OPR-NRDWP-Support";
        } else if ( remit_type.equalsIgnoreCase("NC")||remit_type.equalsIgnoreCase("UNC"))
        {
            col_unspent="OPR-NRDWP-Calamity";
        }else if(remit_type.equalsIgnoreCase("NRDWP-WQM-SP")){
        	col_unspent="NRDWP-WQM-SP";
        }else if(remit_type.equalsIgnoreCase("FDW")){
        	col_unspent="FDW";
        }else if(remit_type.equalsIgnoreCase("WATCHARGEREV_Hogenakkal")){
        	col_unspent="WATCHARGEREV_Hogenakkal";
        }
        else if(remit_type.equalsIgnoreCase("WATCHARGEREV")){
        	col_unspent="WATCHARGEREV";
        }
        else if(remit_type.equalsIgnoreCase("NONWATCHARGEREV")){
        	col_unspent="NONWATCHARGEREV";
        }
        else if(remit_type.equalsIgnoreCase("FieldKit")){
        	col_unspent="FieldKit";
        }
        else if(remit_type.equalsIgnoreCase("FDW from Collection")){
        	col_unspent="FDW from Collection";
        }
        else if(remit_type.equalsIgnoreCase("LB100PCNTCONTRIB")){
        	col_unspent="LB100PCNTCONTRIB";
        }
        else if(remit_type.equalsIgnoreCase("JICA")){
        	col_unspent="JICA";
        }
        else if(remit_type.equalsIgnoreCase("Security Deposit")){
        	col_unspent="Security Deposit";
        }
        else if(remit_type.equalsIgnoreCase("KFW")){
        	col_unspent="KFW";
        }
        else if(remit_type.equalsIgnoreCase("UIDDSMT")){
        	col_unspent="UIDDSMT";
        }
        
        
        System.out.println("remit_type---->"+remit_type);
        
        
        
        /** Get Displaying Order */
        try{displayingOrder=request.getParameter("displayingOrder");}
        catch(Exception e){System.out.println("exception"+e );}
        System.out.println("displayingOrder "+displayingOrder);
        
        /** Get Region Office ID */
        try{txtRegionId=Integer.parseInt(request.getParameter("txtRegionId"));}
        catch(Exception e){System.out.println("exception"+e );}
        System.out.println("txtRegionId "+txtRegionId);
        
        /** Get Bank Account Number */
        try{txtBankAccountNo=request.getParameter("txtBankAccountNo");}
        catch(Exception e){System.out.println("exception"+e );}
        System.out.println("txtBankAccountNo "+txtBankAccountNo);
        
       
        
        
        
        if(strCommand.equalsIgnoreCase("searchByMonth") || strCommand.equalsIgnoreCase("searchByDate")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             Calendar c; Date txtCrea_date=null;
              xml="<response><command>searchByMonth</command>";
             
                    try {
                    	     /* Get Cashbook Year */
                             int year=Integer.parseInt(request.getParameter("txtCB_Year"));
                             System.out.println("the year is"+year);
                             
                             /* Get Cashbook Month */
                             int month=Integer.parseInt(request.getParameter("txtCB_Month"));
                             System.out.println("the month is"+month);
                             
                             // Receipt DAte
                             try{ 
                             String[] sd1=request.getParameter("txtCrea_date").split("/");
                             c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                             System.out.println("sd1 >> "+sd1);
                             java.util.Date d1=c.getTime();
                             txtCrea_date=new Date(d1.getTime());
                             System.out.println("txtCrea_date "+txtCrea_date);
                             }  catch(Exception e )
                             {
                          	   System.out.println("Could not get date the Record --> "+e);
                             }
                             
                             /** Date Declaration */
                             Date txtFrom_date=null;
                             Date txtTo_date=null;
                            
                             
                             /** Get From and To Date */
                            
                           try
                           {
                             
                             String[] sd=request.getParameter("txtFrom_date").split("/");
                             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                             java.util.Date d=c.getTime();
                             txtFrom_date=new Date(d.getTime());
                             System.out.println("from_date "+txtFrom_date);
                             
                             sd=request.getParameter("txtTo_date").split("/");
                             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                             d=c.getTime();
                             txtTo_date=new Date(d.getTime());
                             System.out.println("txtTo_date "+txtTo_date);
                             
                             
                          
                           }
                           catch(Exception e )
                           {
                        	   System.out.println("Could not get the Record txtto date a--> "+e);
                           }
                           
                           String sql="                                  select  \n" + 
                           "                                   *  \n" + 
                           "                                 from  \n" + 
                           "                                 ( \n" + 
                           "                                             SELECT *   \n" + 
                           "                                             FROM   \n" + 
                           "                                             (  \n" + 
                           "                                             \n" + 
                           "\n" + 
                           "                                                     \n" + 
                           "                                                   select\n" + 
                           "                                                   accounting_unit_id,   \n" + 
                           "                                                   accounting_for_office_id,   \n" + 
                           "                                                   trim(remittance_type) as remittance_type,   \n" + 
                           "                                                   date_of_transfer,date_of_transfer1,   \n" + 
                           "                                                   voucher_no, \n" + 
                           "                                                   cheque_or_dd,   \n" + 
                           "                                                   cashbook_month,   \n" + 
                           "                                                   cashbook_year,   \n" + 
                           "                                                   \n" + 
                           "                                                   cr_account_head_code,           \n" + 
                           "                                                   ( \n" + 
                           "                                                    select account_head_desc from com_mst_account_heads\n" + 
                           "                                                    where account_head_code= cr_account_head_code\n" + 
                           "                                                    )as cr_account_head_code_desc, \n" + 
                           "                                                    \n" + 
                           "                                                   office_account_no,   \n" + 
                           "                                                   office_bank_id,   \n" + 
                           "                                                   office_branch_id,    \n" + 
                           "                                                   \n" + 
                           "                                                   dr_account_head_code,\n" + 
                           "                                                   \n" + 
                           "                                                   ( \n" + 
                           "                                                    select account_head_desc from com_mst_account_heads\n" + 
                           "                                                    where account_head_code= dr_account_head_code\n" + 
                           "                                                   )as dr_account_head_code_desc, \n" + 
                           "                                                    \n" + 
                           "                                                   \n" + 
                           "                                                   ho_account_no,                                                                      \n" + 
                           "                                                   ho_account_no_desc,       \n" + 
                           "                                                   total_amount,HO_BANK_ID           \n" + 
                           "                                                    \n" + 
                           "                                                   from \n" + 
                           "                                                   (\n" + 
                           "                                                        select \n" + 
                           "                                                        * \n" + 
                           "                                                        from \n" + 
                           "                                                        (               \n" + 
                           "                                                           SELECT    \n" + 
                           "                                                             accounting_unit_id,   \n" + 
                           "                                                             accounting_for_office_id,   \n" + 
                           "                                                             remittance_type, date_of_transfer,  \n" + 
                           "                                                             to_char(date_of_transfer,'dd/mm/yyyy') as date_of_transfer1 ,   \n" + 
                           "                                                             voucher_no, \n" + 
                           "                                                             cheque_or_dd,   \n" + 
                           "                                                             cashbook_month,   \n" + 
                           "                                                             cashbook_year,                    \n" + 
                           "                                                       \n" + 
                           "                                                             office_account_no,   \n" + 
                           "                                                             office_bank_id,   \n" + 
                           "                                                             office_branch_id, HO_BANK_ID,                 \n" + 
                           "                                                             \n" + 
                           "                                                           -- HO Side --    \n" + 
                           "                                                           \n" + 
                      //     "                                                            (  SELECT     \n" + 
                        //   "                                                                curr.AC_HEAD_CODE   \n" + 
                      //     "                                                              FROM FAS_OFFICE_BANK_AC_CURRENT curr                       \n" + 
                       //    "                                                              WHERE curr.STATUS              ='Y'                        \n" + 
                      //     "                                                              AND curr.ACCOUNTING_UNIT_ID    =5                          \n" + 
                      //     "                                                              AND curr.MODULE_ID             ='MF009'                    \n" + 
                      //     "                                                              AND curr.CR_DR_TYPE            ='DR'                       \n" + 
                     //      "                                                              AND curr.SL_NO                 =1                          \n" + 
                     //      "                                                              AND curr.AC_OPERATIONAL_MODE_ID in ( '"+col_unspent+"' )               \n" + 
                       //    "                                                              and curr.BANK_AC_NO = ho_account_no                        \n" + 
                      //     "                                                              and curr.BANK_ID = ho_bank_id                              \n" + 
                           "                                                            dr_account_head_code,  cr_account_head_code,                                \n" + 
                           "                                                            \n" + 
                           "                                                         \n" + 
                           "                                                             ho_account_no,                                                                      \n" + 
                           "                                                             ho_account_no ||'/'|| ho_bank_id ||'/' || ho_branch_id as ho_account_no_desc,       \n" + 
                           "                                                             trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount                    \n" + 
                           "                                                              \n" + 
                           "                                                           FROM fas_fund_trf_from_office                        \n" + 
                           "                                                           WHERE transfer_status = 'L'                          \n" + 
                           "                                                           AND remittance_type  IN ('"+remit_type+"')                        \n" + 
                           "                                                           AND auto_status      IS NULL  and VERIFY='Y'                       \n" + 
                           "                                                                                                                \n" + 
                           "                                                           \n" + 
                           "                                                           \n" + 
                           "                                                        ) a \n" + 
                           "                                                        \n" + 
                          /* "                                                        left outer join             \n" + 
                           "                                                        \n" + 
                           "                                                         (  \n" + 
                           "                                                          SELECT   \n" + 
                           "                                                            AC_HEAD_CODE as cr_account_head_code ,  \n" + 
                           "                                                            ACCOUNTING_UNIT_ID as unit_id, \n" + 
                           "                                                            BANK_AC_NO,\n" + 
                           "                                                            BANK_ID\n" + 
                           "                                                          FROM FAS_OFFICE_BANK_AC_CURRENT                            \n" + 
                           "                                                          WHERE STATUS              ='Y'                             \n" + 
                           "                                                          AND MODULE_ID             ='MF009'                         \n" + 
                           "                                                          AND CR_DR_TYPE            ='CR'                            \n" + 
                           "                                                          AND SL_NO                 =1                               \n" + 
                           "                                                          AND AC_OPERATIONAL_MODE_ID in ( '"+col_unspent+"' )        \n" + 
                           "                                                      \n" + 
                           "                                                         ) b \n" + 
                           "                                                         on \n" + 
                           "                                                           a.accounting_unit_id = b.unit_id \n" + 
                           "                                                           and a.office_account_no = b.BANK_AC_NO\n" + 
                           "                                                           and a.office_bank_id = b.BANK_ID\n" + */
                           "                                                   )  as ps    \n" + 
                           "            \n" + 
                           "          \n" + 
                           "                                           )a   \n" + 
                           "                                            \n" + 
                           "                                           LEFT OUTER JOIN    \n" + 
                           "                                           (   \n" + 
                           "                                             SELECT    \n" + 
                           "                                               accounting_unit_id as u_id,                                           \n" + 
                           "                                               accounting_unit_name   \n" + 
                           "                                             FROM fas_mst_acct_units   \n" + 
                           "                                           )b   \n" + 
                           "                                           on    \n" + 
                           "                                           a.accounting_unit_id = b.u_id    \n" + 
                           "                                )as ps ";
                            
                           System.out.println("SQL***************>"+sql);
                            System.out.println("remit_type====>"+remit_type);
                           /** Search By Date */
                            if(strCommand.equalsIgnoreCase("searchByDate")) 
                            {
                               String search_by_date =" where date_of_transfer between ? and ?  ";
                               sql = sql + search_by_date ;
                            }
                            else {
                               String search_by_month =" where cashbook_month= "+month+" and cashbook_year= "+year+" ";
                               sql = sql + search_by_month ;
                               System.out.println("searchByMonth.............="+sql);
                            }
                            
                           
                           /** SQL for displaying Region wise */
                           String sql_Region_Wise=" ";
                                                   
                           if ( txtRegionId == 5000)
                           {
                               sql_Region_Wise=" and accounting_for_office_id  in (5000) ";                               
                           }
                           else
                           {
                               sql_Region_Wise="					                  "+
                               " and accounting_for_office_id  in		                	\n"+ 
                               "   (							         	\n"+
                               "     select office_id						        \n"+ 
                               "      from COM_MST_ALL_OFFICES_VIEW			                \n"+ 
                               "      where region_office_id = "+ txtRegionId +  ")                     \n"+
                               "									\n";
                           } 


                           /** SQL for displaying Bank wise */
                           String sql_Bank_Wise = "and ho_account_no = "+txtBankAccountNo;
                           
                           
                           if ( displayingOrder.equalsIgnoreCase("RW"))
                           { 
                        	  if ( txtRegionId == 101 )
                        	  {
                                         /** Display All Units */ 
                        	         sql=sql;	  
                        	  }
                        	  else
                        	  {
                        		  /** Display Particular Region only */
                        		  sql=sql  +  sql_Region_Wise;
                        	  }	  
                           }
                          


                           
                           /** Displaying in Bank wise */
                           if ( displayingOrder.equalsIgnoreCase("BW"))
                           {
                        	  sql=sql  +  sql_Bank_Wise;
                           }
                           
                           
                           /** Display Order */                           
                       //Change on 20 Aug 2014//    sql=sql+ " and  date_of_transfer=?  order by accounting_unit_name , date_of_transfer, voucher_no    "; 
                           sql=sql+ "  order by accounting_unit_name , date_of_transfer, voucher_no    "; 
                           System.out.println("end of sql::::"+sql);
                           
                           
                           ps=con.prepareStatement(sql);
                           
                           /** Search by Date */
                           if(strCommand.equalsIgnoreCase("searchByDate")) 
                           {
                        	   ps.setDate(1,txtFrom_date);
                        	   ps.setDate(2,txtTo_date);
                        	  // ps.setDate(3,txtCrea_date);
                           } else 
                           {
                           //ps.setDate(1,txtCrea_date);
                           }
                           /** Query Execution */
                           rs=ps.executeQuery();
                            
                           System.out.println("sql-->"+sql); 
                            
                            
                           /** Get All Banking Section Account Numbers */
                           String bank_acc_num="" +
                           " SELECT\n" + 
                           "  b.BANK_AC_NO || '/' || b.bank_id || '/' || b.branch_id as ho_bank_ac_no,  \n" + 
                           "  c.BANK_SHORT_NAME\n" + 
                           "  || '-'\n" + 
                           "  || b.BANK_AC_TYPE_ID\n" + 
                           "  ||'-'\n" + 
                           "  || b.BANK_AC_NO AS bankShow\n" + 
                           " FROM FAS_OFFICE_BANK_AC_CURRENT b,\n" + 
                           "  FAS_MST_BANKS c\n" + 
                           " WHERE b.BANK_ID         =c.BANK_ID\n" + 
                           " AND b.ACCOUNTING_UNIT_ID=5 \n" + 
                           " AND MODULE_ID           ='MF009'\n" + 
                           " AND CR_DR_TYPE          ='DR' \n" + 
                           " AND b.STATUS            ='Y'";
                            
                            
                           int count=0;
                           while(rs.next())
                           {
                                xml=xml+"<details>";      
                              //      System.out.println("1");
                                    xml=xml+"<accounting_unit_id>"+rs.getString("accounting_unit_id")+"</accounting_unit_id>";
                            //   System.out.println("2");
                                    xml=xml+"<accounting_for_office_id>"+rs.getString("accounting_for_office_id")+"</accounting_for_office_id>";
                          //     System.out.println("3");
                                    xml=xml+"<remittance_type>"+rs.getString("remittance_type")+"</remittance_type>";
                           //    System.out.println("4");
                                    xml=xml+"<date_of_transfer>"+rs.getString("date_of_transfer1")+"</date_of_transfer>";
                           //    System.out.println("5");
                                    xml=xml+"<voucher_no>"+rs.getString("voucher_no")+"</voucher_no>";
                             //  System.out.println("6");
                                    
                                    xml=xml+"<cheque_or_dd>"+rs.getString("cheque_or_dd")+"</cheque_or_dd>";
                             //  System.out.println("7");
                                    
                                    xml=xml+"<cashbook_month>"+rs.getString("cashbook_month")+"</cashbook_month>";
                              // System.out.println("8");
                                    xml=xml+"<cashbook_year>"+rs.getString("cashbook_year")+"</cashbook_year>";
                             //  System.out.println("9");
                             /*        xml=xml+"<cr_account_head_code>"+rs.getString("cr_account_head_code")+"</cr_account_head_code>";
                               System.out.println("10");
                               */
                              // bank_acc_num=bank_acc_num+" and b.BANK_ID="+rs.getString("office_bank_id");
                               
                               String testsqlsec="SELECT AC_HEAD_CODE as  cr_account_head_code , \n"+  
                               "  ACCOUNTING_UNIT_ID as unit_id, \n"+ 
                               "  BANK_AC_NO,\n"+ 
                               "  BANK_ID \n"+ 
                              " FROM FAS_OFFICE_BANK_AC_CURRENT  \n"+                            
                              " WHERE STATUS              ='Y' \n"+                             
                              " AND MODULE_ID             ='MF009' \n"+                          
                              " AND CR_DR_TYPE            ='CR'   \n"+                          
//                              " AND SL_NO                 =1    \n"+                            
                              " AND AC_OPERATIONAL_MODE_ID in ( '"+col_unspent+"' ) and ACCOUNTING_UNIT_ID="+rs.getString("accounting_unit_id")+" and BANK_ID="+rs.getString("office_bank_id")+" " ;
                              //" AND AC_OPERATIONAL_MODE_ID in ( '"+col_unspent+"' ) and ACCOUNTING_UNIT_ID="+rs.getString("accounting_unit_id")+" and BANK_AC_NO="+rs.getString("office_account_no")+" and BANK_ID="+rs.getString("office_bank_id")+" " ;
					  
                               if(rs.getString("remittance_type").equalsIgnoreCase("WATCHARGEREV"))
                               {
                            	   testsqlsec="SELECT AC_HEAD_CODE as  cr_account_head_code , \n"+  
                                           "  ACCOUNTING_UNIT_ID as unit_id, \n"+ 
                                           "  BANK_AC_NO,\n"+ 
                                           "  BANK_ID \n"+ 
                                          " FROM FAS_OFFICE_BANK_AC_CURRENT  \n"+                            
                                          " WHERE STATUS              ='Y' \n"+                             
                                          " AND MODULE_ID             ='MF009' \n"+                          
                                          " AND CR_DR_TYPE            ='CR'   \n"+                          
                                          " AND SL_NO                 =1    \n"+                            
                                          " AND AC_OPERATIONAL_MODE_ID in ( 'COL' ) and ACCOUNTING_UNIT_ID="+rs.getString("accounting_unit_id")+" and BANK_ID="+rs.getString("office_bank_id")+" and AC_HEAD_CODE::varchar like '%6'\n  " ; 
                               }
                               else if(rs.getString("remittance_type").equalsIgnoreCase("WATCHARGEREV_Hogenakkal"))
                               {
                            	   testsqlsec="SELECT AC_HEAD_CODE as  cr_account_head_code , \n"+  
                                           "  ACCOUNTING_UNIT_ID as unit_id, \n"+ 
                                           "  BANK_AC_NO,\n"+ 
                                           "  BANK_ID \n"+ 
                                          " FROM FAS_OFFICE_BANK_AC_CURRENT  \n"+                            
                                          " WHERE STATUS              ='Y' \n"+                             
                                          " AND MODULE_ID             ='MF009' \n"+                          
                                          " AND CR_DR_TYPE            ='CR'   \n"+                          
                                          " AND SL_NO                 =1    \n"+                            
                                          " AND AC_OPERATIONAL_MODE_ID in ( 'COL' ) and ACCOUNTING_UNIT_ID="+rs.getString("accounting_unit_id")+" and BANK_ID="+rs.getString("office_bank_id")+" and AC_HEAD_CODE::varchar like '820706'\n  " ; 
                               }
                               
                               else if(rs.getString("remittance_type").equalsIgnoreCase("FDW") && rs.getString("ho_account_no").equalsIgnoreCase("443372678"))
                               {
                            	   testsqlsec="SELECT AC_HEAD_CODE as  cr_account_head_code , \n"+  
                                           "  ACCOUNTING_UNIT_ID as unit_id, \n"+ 
                                           "  BANK_AC_NO,\n"+ 
                                           "  BANK_ID \n"+ 
                                          " FROM FAS_OFFICE_BANK_AC_CURRENT  \n"+                            
                                          " WHERE STATUS              ='Y' \n"+                             
                                          " AND MODULE_ID             ='MF009' \n"+                          
                                          " AND CR_DR_TYPE            ='CR'   \n"+                          
                                          " AND SL_NO                 =1    \n"+                            
                                          " AND AC_OPERATIONAL_MODE_ID in ( 'COL' ) and ACCOUNTING_UNIT_ID="+rs.getString("accounting_unit_id")+" and BANK_ID="+rs.getString("office_bank_id")+" and AC_HEAD_CODE::varchar like '%6'\n  " ;
                               }
                               
                               if(rs.getString("remittance_type").equalsIgnoreCase("C")
                            		   ||rs.getString("remittance_type").equalsIgnoreCase("NONWATCHARGEREV")||rs.getString("remittance_type").equalsIgnoreCase("FieldKit")
                            		   ||rs.getString("remittance_type").equalsIgnoreCase("FDW from Collection")||
                            		   rs.getString("remittance_type").equalsIgnoreCase("JICA")||rs.getString("remittance_type").equalsIgnoreCase("Security Deposit")
                            		   ||rs.getString("remittance_type").equalsIgnoreCase("KFW")||rs.getString("remittance_type").equalsIgnoreCase("UIDDSMT"))
                               {
                                testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '%6'\n";
                               }
                                if (rs.getString("remittance_type").equalsIgnoreCase("LB100PCNTCONTRIB"))
                                {
                                	testsqlsec="SELECT AC_HEAD_CODE as  cr_account_head_code , \n"+  
                                            "  ACCOUNTING_UNIT_ID as unit_id, \n"+ 
                                            "  BANK_AC_NO,\n"+ 
                                            "  BANK_ID \n"+ 
                                           " FROM FAS_OFFICE_BANK_AC_CURRENT  \n"+                            
                                           " WHERE STATUS              ='Y' \n"+                             
                                           " AND MODULE_ID             ='MF009' \n"+                          
                                           " AND CR_DR_TYPE            ='CR'   \n"+                          
                                           " AND SL_NO                 =1    \n"+                            
                                           " AND AC_OPERATIONAL_MODE_ID in ( 'COL' ) and ACCOUNTING_UNIT_ID="+rs.getString("accounting_unit_id")+" and BANK_ID="+rs.getString("office_bank_id")+" and AC_HEAD_CODE::varchar like '%6'\n  " ;
                                }
//                               else if(rs.getString("remittance_type").equalsIgnoreCase("WATCHARGEREV"))
//                               {
//                            	   testsqlsec=testsqlsec+ " and AC_HEAD_CODE like '%6'\n";
//                               }
                               
                               else if(rs.getString("remittance_type").equalsIgnoreCase("U"))
                               {
                                testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '%5'\n";
                               }else if(rs.getString("remittance_type").equalsIgnoreCase("NM"))
                               {
                                   testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '822151'\n";
                                  }else if(rs.getString("remittance_type").equalsIgnoreCase("NS"))
                                  {
                                      testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '820661'\n";
                                     }else if(rs.getString("remittance_type").equalsIgnoreCase("NC"))
                                     {
                                         testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '822151'\n";
                                        }else if(rs.getString("remittance_type").equalsIgnoreCase("UNM"))
                                        {
                                            testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '822152'\n";
                                           }
//                                        else if(rs.getString("remittance_type").equalsIgnoreCase("UNS") && !rs.getString("ho_account_no").equalsIgnoreCase("332502050000017"))
                                        else if(rs.getString("remittance_type").equalsIgnoreCase("UNS"))
                                           {
                                               testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '820662'\n";
//                                        	   testsqlsec=testsqlsec+ " and AC_HEAD_CODE like '820661'\n";
                                        	   
                                              }else if(rs.getString("remittance_type").equalsIgnoreCase("UNC") && !rs.getString("ho_account_no").equalsIgnoreCase("800710210000012"))
                                              {
                                                  testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '822152'\n";
                                                 }else if(rs.getString("remittance_type").equalsIgnoreCase("NRDWP-WQM-SP") || rs.getString("ho_account_no").equalsIgnoreCase("332502050000017"))
                                                 {
                                                    // testsqlsec=testsqlsec+ " and AC_HEAD_CODE like '820616'\n";
                                                	 testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '820664'\n";
                                                	 
                                                     
                                                    }
                                                 else if(rs.getString("remittance_type").equalsIgnoreCase("FDW") && !rs.getString("ho_account_no").equalsIgnoreCase("443372678") )
                                                    {
                                                        testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '%7'\n";
                                                    }
                               
                               
                                                 else if(rs.getString("remittance_type").equalsIgnoreCase("UNC") && rs.getString("ho_account_no").equalsIgnoreCase("800710210000012"))
                                                 {
                                                     testsqlsec=testsqlsec+ " and AC_HEAD_CODE::varchar like '822151'\n";
                                                 }
                               
//                                                 else if(rs.getString("remittance_type").equalsIgnoreCase("FDW")||rs.getString("ho_account_no").equalsIgnoreCase("953101042776"))
//                                                 {
//                                                    System.out.println("Remittance Type......FWD To Collection......");
//                                                	 
//                                                	 testsqlsec=testsqlsec+ " and AC_HEAD_CODE like '%6'\n";
//                                                 }
                                           
                            System.out.println("testsqlsec******"+testsqlsec);                                              
                               ps3=con.prepareStatement(testsqlsec);   
					           rs3=ps3.executeQuery();
					           if(rs3.next())
					           {
					        	    if(rs.getString("remittance_type").equalsIgnoreCase("UNC") && !rs.getString("ho_account_no").equalsIgnoreCase("800710210000012"))
                                   {
					        		   xml=xml+"<cr_account_head_code>822152</cr_account_head_code>";
                                   }else{
					        	   xml=xml+"<cr_account_head_code>"+rs3.getString("cr_account_head_code")+"</cr_account_head_code>";
					        	  // System.out.println("Success 123"+rs3.getString("cr_account_head_code"));
                                   }
                               }
					           else
					           {
					        	   xml=xml+"<cr_account_head_code>--</cr_account_head_code>";
					        	   
					           }
                              
                               
                                    xml=xml+"<cr_account_head_code_desc>"+rs.getString("cr_account_head_code_desc")+"</cr_account_head_code_desc>";
                              // System.out.println("12");
                                    xml=xml+"<ho_account_no>"+rs.getString("ho_account_no")+"</ho_account_no>";
                            //   System.out.println("13");
                                    xml=xml+"<ho_account_no_desc>"+rs.getString("ho_account_no_desc")+"</ho_account_no_desc>";
                            //   System.out.println("14");
                                    xml=xml+"<total_amount>"+rs.getString("total_amount")+"</total_amount>";
                             //  System.out.println("15");
                               
                               String testsql="SELECT curr.AC_HEAD_CODE as dr_ACCOUNT_HEAD_CODE ,BANK_AC_NO,BANK_ID,BRANCH_ID \n"+
                               " FROM FAS_OFFICE_BANK_AC_CURRENT curr  \n"+                     
                               " WHERE curr.STATUS              ='Y'  \n"+                      
                                " AND curr.ACCOUNTING_UNIT_ID    =5   \n"+                       
                               " AND curr.MODULE_ID             ='MF009' \n"+                    
                               "  AND curr.CR_DR_TYPE            ='DR'  \n"+                     
                            //   " AND curr.SL_NO                 =1     \n"+                     
                              "  AND curr.AC_OPERATIONAL_MODE_ID in ( '"+col_unspent+"'  ) and curr.BANK_AC_NO="+rs.getString("ho_account_no")+" and BANK_ID="+rs.getInt("ho_bank_id")+" " ;
                               System.out.println(" hhhh ?>???  "+rs.getString("remittance_type"));
                               
                             /*  if(rs.getString("remittance_type").equalsIgnoreCase("C"))
                               {
                               	testsql=testsql+ " and curr.ac_head_code like '%3'\n";
                               } else if(rs.getString("remittance_type").equalsIgnoreCase("NM") || rs.getString("remittance_type").equalsIgnoreCase("NS"))
                               {
                            	   testsql=testsql+ " ";  
                               }
                            	   else 
                               {
                               	testsql=testsql+ " and curr.ac_head_code like '%2'\n";
                               }*/
                               
                               System.out.println(rs.getString("remittance_type")+"----Remittance Type ");
                               System.out.println(rs.getString("ho_account_no")+"----ho_account_no  ");
                               
                               if(rs.getString("remittance_type").equalsIgnoreCase("C"))
                               {
                            	 	testsql=testsql+ " and curr.ac_head_code::varchar like '%3'\n";
                               }else if(rs.getString("remittance_type").equalsIgnoreCase("U"))
                               {
                            	 	testsql=testsql+ " and curr.ac_head_code::varchar like '%2'\n";
                               }else if(rs.getString("remittance_type").equalsIgnoreCase("NM"))
                               {
                            	 	testsql=testsql+ " and curr.ac_head_code::varchar like '822111'\n";
                                  }else if(rs.getString("remittance_type").equalsIgnoreCase("NS"))
                                  {
                                	 	testsql=testsql+ " and curr.ac_head_code::varchar like '820611'\n";
                                     }else if(rs.getString("remittance_type").equalsIgnoreCase("NC"))
                                     {
                                    	 	testsql=testsql+ " and curr.ac_head_code::varchar like '822113'\n";
                                        }else if(rs.getString("remittance_type").equalsIgnoreCase("UNM"))
                                        {
                                         	testsql=testsql+ " and curr.ac_head_code::varchar like '822111'\n";
                                           }else if(rs.getString("remittance_type").equalsIgnoreCase("UNS") && !rs.getString("ho_account_no").equalsIgnoreCase("332502050000017"))
                                           {
                                        	 	testsql=testsql+ " and curr.ac_head_code::varchar like '820611'\n";
                                              }else if(rs.getString("remittance_type").equalsIgnoreCase("UNC"))
                                              {
                                            	 	testsql=testsql+ " and curr.ac_head_code::varchar like '822113'\n";
                                                 }
                                              
                                              else if(rs.getString("remittance_type").equalsIgnoreCase("NRDWP-WQM-SP") || rs.getString("ho_account_no").equalsIgnoreCase("332502050000017"))
                                                 {
                                                		//testsql=testsql+ " and curr.ac_head_code like '820663'\n";
                                                		testsql=testsql+ " and curr.ac_head_code::varchar like '820616'\n";
                                                    }
                                                 else if(rs.getString("remittance_type").equalsIgnoreCase("FDW") && rs.getString("ho_account_no").equalsIgnoreCase("443372678"))
                                                 {
                                                	  testsql="SELECT curr.AC_HEAD_CODE as dr_ACCOUNT_HEAD_CODE ,BANK_AC_NO,BANK_ID,BRANCH_ID \n"+
                                                             " FROM FAS_OFFICE_BANK_AC_CURRENT curr  \n"+                     
                                                             " WHERE curr.STATUS              ='Y'  \n"+                      
                                                              " AND curr.ACCOUNTING_UNIT_ID    =5   \n"+                       
                                                             " AND curr.MODULE_ID             ='MF009' \n"+                    
                                                             "  AND curr.CR_DR_TYPE            ='DR'  \n"+                     
                                                          //   " AND curr.SL_NO                 =1     \n"+                     
                                                            "  AND curr.AC_OPERATIONAL_MODE_ID in ( 'COL'  ) and curr.BANK_AC_NO="+rs.getString("ho_account_no")+" and BANK_ID="+rs.getInt("ho_bank_id")+" " ;
                                                	 
                                                	 
                                                	  testsql=testsql+ " and AC_HEAD_CODE::varchar like '%3'\n";
                                                 }
                                                 
                                                 
//                                                 	else if(rs.getString("remittance_type").equalsIgnoreCase("C") && rs.getString("ho_account_no").equalsIgnoreCase("800710210000006"))
//                                                 	{
//                                                  	System.out.println(rs.getString("remittance_type")+"----Remittance Type ");
//                                                 		
//                                                 		testsql=testsql+ " and curr.ac_head_code like '822111'\n";
//                                                    }
                             System.out.println("testsql::::::::::::::::::::::"+testsql);
					           ps2=con.prepareStatement(testsql);   
					           rs2=ps2.executeQuery();
					           if(rs2.next())
					           {
					        	
					        	   xml=xml+"<dr_account_head_code>"+rs2.getString("dr_ACCOUNT_HEAD_CODE")+"</dr_account_head_code>";
					               System.out.println("Success  ........."+rs2.getString("dr_ACCOUNT_HEAD_CODE"));
					           }
					           else{
					           	xml=xml+"<dr_account_head_code>--</dr_account_head_code>";
					           System.out.println(" ..........and curr.BANK_AC_NO="+rs.getString("ho_account_no")+" and BANK_ID="+rs.getInt("ho_bank_id"));
					           }
                               
                               /*
                                    xml=xml+"<dr_account_head_code>"+rs.getString("dr_account_head_code")+"</dr_account_head_code>";
                               System.out.println("16");  */
                                    xml=xml+"<dr_account_head_code_desc>"+rs.getString("dr_account_head_code_desc")+"</dr_account_head_code_desc>";
                            //   System.out.println("17");
                                    xml=xml+"<office_account_no>"+rs.getString("office_account_no")+"</office_account_no>";
                             //  System.out.println("18");
                                    xml=xml+"<accounting_unit_name>"+rs.getString("accounting_unit_name")+"</accounting_unit_name>";                               
                              // System.out.println("19");
                                    xml=xml+"</details>";
                                    bank_acc_num="" +
                                    "SELECT\n" + 
                                    " distinct  b.BANK_AC_NO || '/' || b.bank_id || '/' || b.branch_id as ho_bank_ac_no,  \n" + 
                                    "  c.BANK_SHORT_NAME\n" + 
                                    "  || '-'\n" + 
                                    "  || b.BANK_AC_TYPE_ID\n" + 
                                    "  ||'-'\n" + 
                                    "  || b.BANK_AC_NO AS bankShow\n" + 
                                    "FROM FAS_OFFICE_BANK_AC_CURRENT b,\n" + 
                                    "  FAS_MST_BANKS c\n" + 
                                    " WHERE b.BANK_ID         =c.BANK_ID\n" + 
                                    " AND b.ACCOUNTING_UNIT_ID=5 \n" + 
                                    " AND MODULE_ID           ='MF009'\n" + 
                                    " AND CR_DR_TYPE          ='DR' \n" + 
                                    " AND b.STATUS            ='Y' \n";
                                    if(rs.getString("remittance_type").equalsIgnoreCase("C"))
                                    {
                                    	bank_acc_num+="";
                                    }else if(rs.getString("remittance_type").equalsIgnoreCase("U"))
                                    {
                                    	bank_acc_num+="";
                                    }else  if(rs.getString("remittance_type").equalsIgnoreCase("NM"))
                                    {
                                    bank_acc_num+=" and b.BANK_AC_NO=800710210000006  ";	
                                    }else  if(rs.getString("remittance_type").equalsIgnoreCase("NS"))
                                    {
                                        bank_acc_num+=" and b.BANK_AC_NO=332502010091046  ";	
                                        }else  if(rs.getString("remittance_type").equalsIgnoreCase("NC"))
                                        {
                                            bank_acc_num+=" and b.BANK_AC_NO=800710210000012  ";	
                                            }else  if(rs.getString("remittance_type").equalsIgnoreCase("UNM"))
                                            {
                                                bank_acc_num+=" and b.BANK_AC_NO=800710210000006   ";	
                                                }else  if(rs.getString("remittance_type").equalsIgnoreCase("UNS") && !rs.getString("ho_account_no").equalsIgnoreCase("332502050000017") )
                                                {
                                                    bank_acc_num+=" and b.BANK_AC_NO=332502010091046  ";	
                                                    }else  if(rs.getString("remittance_type").equalsIgnoreCase("UNC"))
                                                    {
                                                        bank_acc_num+=" and b.BANK_AC_NO=800710210000012  ";	
                                                        }else if(rs.getString("remittance_type").equalsIgnoreCase("NRDWP-WQM-SP") || rs.getString("ho_account_no").equalsIgnoreCase("332502050000017"))
                                                        {
                                                        	 bank_acc_num+=" and b.BANK_AC_NO=332502050000017  ";	
                                                           }
                              //      " AND b.BANK_ID ="+rs.getString("office_bank_id");
                                //    "   AND (b.BANK_ID ="+rs.getString("office_bank_id")+"  or  b.BANK_AC_NO="+txtBankAccountNo+") ";
                                    
                                  /*  
                                   if(rs.getString("remittance_type").equalsIgnoreCase("NS")||rs.getString("remittance_type").equalsIgnoreCase("NM"))
                                   {
                                	   bank_acc_num=bank_acc_num+" ";
                                   }
                                   else{
                                       bank_acc_num=bank_acc_num + " AND b.BANK_ID ="+rs.getString("office_bank_id");
                                   }*/
                                 //  System.out.println(bank_acc_num) ;
                                    ps2=con.prepareStatement(bank_acc_num);
                                    System.out.println("bank_acc_num >> "+bank_acc_num);
                                    rs2=ps2.executeQuery();
                                    while (rs2.next()) 
                                    {
                                        xml=xml+"<bank_details"+count+">";                                    
                                            xml=xml+"<ho_bank_ac_no>"+rs2.getString("ho_bank_ac_no")+"</ho_bank_ac_no>";
                                            xml=xml+"<bankShow>"+rs2.getString("bankShow")+"</bankShow>";
                                        xml=xml+"</bank_details"+count+">"; 
                                        
                                    }
                               
                                count++;
                                
                                
                           }
                           
                        /*   ps2=con.prepareStatement(bank_acc_num);
                           rs2=ps2.executeQuery();
                           while (rs2.next()) 
                           {
                               xml=xml+"<bank_details>";                                    
                                   xml=xml+"<ho_bank_ac_no>"+rs2.getString("ho_bank_ac_no")+"</ho_bank_ac_no>";
                                   xml=xml+"<bankShow>"+rs2.getString("bankShow")+"</bankShow>";
                               xml=xml+"</bank_details>"; 
                               
                           }
                            */
                           
                           if(count==0)
                               xml=xml+"<flag>NoRecords</flag>";
                           else                            
                               xml=xml+"<flag>success</flag>";
                               
                          ps.close();
                          rs.close();
                        
                        }
                        catch(Exception e)
                        {
                            xml=xml+"<flag>failure</flag>";
                            System.out.println("Error-->"+e);
                        }
    }
    
    xml=xml+"</response>";
    System.out.println(xml);
    out.println(xml);
}


   public void doPost(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, IOException 
   {
        
        response.setContentType(CONTENT_TYPE);
        
        
        /* Variables Declaration */ 
        Connection con = null;      
        CallableStatement cs= null;
        
      
        /* Session Checking */
        HttpSession session = request.getSession(false);       
              
      
        /* Database Connection */
        try {
            ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        
        
        /* Variables Declaration */ 
        
        
        String HO_accunitid=null;
        String HO_officeid=null;
        int HO_cashbookyear=0;
        int HO_cashbookmonth=0;
        
        Date txtCrea_date=null;
        String comRemarks =null;
        
        int TotalRecords=0;
        
        
        String chckparameter_Voucher_no[]=null;
        String Office_acctid[]=null;
        String Office_acctofficeid[] =null;
        String Transfer_Type[]=null;        
        String cmbHOAccountNum[]=null; // ( Head Office Account Number plus its corresponding Bank and Branch ID //
        String Off_cb_month[]=null;
        String Off_cb_year[]=null;
        String Off_Voucher_no[]=null;
        String Off_Ref_no[]=null;
        String Off_Ref_Date[]=null;
        String TotAmt[]=null;
        String crHOA[]=null;
        String drHOA[]=null;
        
        Calendar c;
        
        int err_code=0;
        
        
        /* Get HO Accounting Unit ID */
         HO_accunitid=request.getParameter("cmbAcc_UnitCode");
         System.out.println(HO_accunitid);
        
      
        /* Get HO Office ID */
         HO_officeid=request.getParameter("cmbOffice_code");
         System.out.println(HO_officeid);
         
        
        /* Get Receipt Date */
        String[] sd=request.getParameter("txtCrea_date").split("/");
        c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
        java.util.Date d=c.getTime();
        txtCrea_date=new Date(d.getTime());
        System.out.println("txtCrea_date "+txtCrea_date);
        
        /* Get Cashbook Year from Receipt date */ 
        HO_cashbookyear=Integer.parseInt(sd[2]);
        
        /* Get Cashbook Month from Receipt date */ 
        HO_cashbookmonth=Integer.parseInt(sd[1]);
           
    
        /* Get Remarks */
        try {
            comRemarks=request.getParameter("comRemarks");
        }
        catch(Exception e) {
            System.out.println("Error getting Remarks "+e);
        }
        
       
         /* Get User ID */ 
         String update_user=(String)session.getAttribute("UserId");
       
        
         /* Get Total Number of Displayed Records */
         try{
             TotalRecords=Integer.parseInt(request.getParameter("TotalRecords"));
             System.out.println("TotalRecords-->"+TotalRecords);
         }
         catch(Exception e) {
             System.out.println("Error Could not get Total Number of Records "+e);             
         }
         
         
        //----------------------------------------------------------------------------------------------//
         /* Get voucher Number from Selected Check Box */         
         try
         {
    	   chckparameter_Voucher_no = request.getParameterValues("chckparameter");  
           for(int i=0;i<chckparameter_Voucher_no.length;i++)
           {
              System.out.println("chckparameter_Voucher_no -->"+chckparameter_Voucher_no[i]);
           }
         }
         catch(Exception e)
         {
    	   System.out.println("Error 1 ---->"+e);
         }
         
         /* Get Office Accounting Unit ID */          
          try {
              Office_acctid = request.getParameterValues("Off_UnitID");  
          }
          catch (Exception e) {
              System.out.println("Error getting Accounting Unit ID "+e);
          }
         
         /* Get Office Accounting for Office ID */         
          try {
              Office_acctofficeid = request.getParameterValues("Off_OfficeCode");  
          }
          catch (Exception e) {
              System.out.println("Error getting Office ID "+e);
          }
         
         /* Get Cashbook Month */
          try {
              Off_cb_month =  request.getParameterValues("Off_cb_Month");  
          }
          catch (Exception e) {
              System.out.println("Error getting CB Month "+e);
          }
         
         /* Get Cashbook Year */
          try {
              Off_cb_year = request.getParameterValues("Off_cb_Year");  
          }
          catch (Exception e) {
              System.out.println("Error getting CB Year "+e);
          }
         
         /* Get Voucher Number */         
          try {
              Off_Voucher_no = request.getParameterValues("Vou_No");  
          }
          catch (Exception e) {
              System.out.println("Error getting Voucher Number "+e);
          }
         
         /* Get Head Office Account Number */
          try {
              cmbHOAccountNum = request.getParameterValues("cmbHOAccountNum");  
          }
          catch (Exception e) {
              System.out.println("Error getting HO Account NUmber "+e);
          }
         
         /* Get Ref Number */
          try {
              Off_Ref_no = request.getParameterValues("RefNo");  
          }
          catch (Exception e) {
              System.out.println("Error getting Refno "+e);
          }
         
         /* Get Ref Date */
          try {
              Off_Ref_Date =  request.getParameterValues("RefDate");  
          }
          catch (Exception e) {
              System.out.println("Error getting RefDate "+e);
          }
          
         
         /* Get Transfer Type */
          try {
              Transfer_Type =  request.getParameterValues("FundType");  
          }
          catch (Exception e){
              System.out.println("Error getting RefDate "+e);
          }
        
         /* Get Total Amount */
          try {
              TotAmt =  request.getParameterValues("TotAmt");  
          }
          catch (Exception e){
              System.out.println("Error getting TotAmt "+e);
          }
        
        int trf_year=0,trf_month=0;
          try {
        	  trf_year =Integer.parseInt(request.getParameter("txtCB_Year"));
          }
          catch (Exception e){
              System.out.println("Error getting trf_year "+e);
          }
          
          try {
        	  trf_month =Integer.parseInt(request.getParameter("txtCB_Month"));
          }
          catch (Exception e){
              System.out.println("Error getting trf_month "+e);
          }
        try
         {
           con.clearWarnings();
           con.setAutoCommit(false);
           
           
           /** Main For Loop. It will iterate all records */
           for ( int i=0;i<TotalRecords;i++)
     	   {
             System.out.println("TotalRecords::::"+TotalRecords);
              /** Initially set the status as false */
     	      boolean flag =false;
     	      int k=0;
              Date Refdate=null;
              
              /** Iterate selected Records and make status true if it matches */
     	      for(k=0;k<chckparameter_Voucher_no.length;k++)
     	      {
     	    	  System.out.println("firrrrrrr"+Integer.parseInt(chckparameter_Voucher_no[k]));
     	        if (i == Integer.parseInt(chckparameter_Voucher_no[k]) ) 
     	        {   
     	        	System.out.println(Integer.parseInt(chckparameter_Voucher_no[k]));
     	           flag=true; 
     	        }
     	      }
     	      
              
              /** Only true condition */
              if(flag)
     	      {  
                   System.out.println("----------------------------Starts----------------------------------");              
                   
                   System.out.println("The value of i -->"+i); 
                   
                   System.out.println("Office_acctid--->"+Office_acctid[i]);   // done 
                   System.out.println("Office_acctofficeid-->"+Office_acctofficeid[i]);  // done 
                   System.out.println("Off_cb_month-->"+Off_cb_month[i]);  // done 
                   System.out.println("Off_cb_year-->"+Off_cb_year[i]);  // done 
                   System.out.println("Off_Voucher_no-->"+Off_Voucher_no[i]); // done
                   
                   System.out.println("cmbHOAccountNum-->"+cmbHOAccountNum[i]);
                   String HOAcc[] = cmbHOAccountNum[i].split("/");
                   System.out.println("HO Account Number -->"+HOAcc[0]);
                   System.out.println("HO Bank id  -->"+HOAcc[1]);
                   System.out.println("HO Branch ID -->"+HOAcc[2]);
                    
                   System.out.println("Off_Ref_no-->"+Off_Ref_no[i]);
                   System.out.println("Off_Ref_Date-->"+Off_Ref_Date[i]);
                   System.out.println("Transfer_Type-->"+Transfer_Type[i]);  
                   System.out.println("TotAmt-->"+TotAmt[i]);  
                    
                  
               
                   
                   
                 //changes on 8th aug2012
                   
                   try {
                 	  drHOA = request.getParameterValues("drHOA"+i);  
                   }
                   catch (Exception e) {
                       System.out.println("Error getting drHOA "+e);
                   }
                   System.out.println("drHOA:ssssss::::"+drHOA[0]);
                //   System.out.println("drHOA::::"+drHOA[i]);
                   try {
                 	  crHOA = request.getParameterValues("crHOA"+i);  
                   }
                   catch (Exception e) {
                       System.out.println("Error getting crHOA: "+e);
                   }
                   System.out.println("crHOA222::::"+crHOA[0]);
               //    System.out.println("crHOA::::"+crHOA[i]);
                   
                   /* Ref Date Conversion */
                   String sd2[]=null;
                   System.out.println("Off_Ref_Date[i]  "+Off_Ref_Date[i]);
                   try {
                       sd2=Off_Ref_Date[i].split("/");
                       c=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
                       java.util.Date d2=c.getTime();
                       Refdate=new Date(d2.getTime());                       
                   }
                   catch (Exception e) {
                       System.out.println("Error getting Ref Date "+e);
                   }
                   
                   System.out.println("Refdate-->"+Refdate); 
                    
                   
                   System.out.println(HO_accunitid);
                   System.out.println(HO_officeid);
                   System.out.println(txtCrea_date);
                   System.out.println(HO_cashbookyear);
                   System.out.println(HO_cashbookmonth);
                   System.out.println(comRemarks);
                   System.out.println(trf_year);
                   System.out.println(trf_month);
                   System.out.println(drHOA[0]);
                   System.out.println(crHOA[0]);
                  // System.out.println(HO_accunitid);
                 
         		    
                    
                   System.out.println("-----------------------------Ends---------------------------------");              
                  
                
               //    cs=con.prepareCall("{call FAS_AUTO_FUND_RECEIPT_ATHO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ;
                   cs=con.prepareCall("call FAS_AUTO_FUND_RECEIPT_ATHO_O(?::numeric,?::numeric,?,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric)") ;
                    
                   /* Static Fields */ 
     		   cs.setInt(1, Integer.parseInt(HO_accunitid)); // Head Office Unit ID 
     		   cs.setInt(2, Integer.parseInt(HO_officeid));  // Head Office's Office Code    		   
     		   cs.setDate(3,txtCrea_date); // Receipt Date     
                   cs.setInt(4,HO_cashbookyear); // HO cb year 
                   cs.setInt(5,HO_cashbookmonth);  // HO cb month 
                   
     		   cs.setString(6,comRemarks); // Remarks 
     		   
     		   cs.setInt(7, Integer.parseInt(Office_acctid[i]));   // Transfering Unit ID 
    		   cs.setInt(8, Integer.parseInt(Office_acctofficeid[i])); // Transfering Office Code 
    		   cs.setInt(9, Integer.parseInt(Off_cb_month[i])); // CB Month 
     		   cs.setInt(10, Integer.parseInt(Off_cb_year[i]));  // CB year
               cs.setInt(11, Integer.parseInt(Off_Voucher_no[i])); // Receipt Number    
               cs.setString(12,Off_Ref_no[i]); // Ref Number 
               cs.setDate(13, Refdate); // Ref Date 
               cs.setDouble(14, Double.parseDouble(TotAmt[i])); // Total Amount 
               
               cs.setDouble(15,Double.parseDouble(HOAcc[0]));  //. HO Account Number 
               cs.setInt(16,Integer.parseInt(HOAcc[1]));  //  HO Bank ID   
               cs.setInt(17,Integer.parseInt(HOAcc[2]));  //  HO Brach ID 
            
               cs.setInt(18,trf_year);
               cs.setInt(19,trf_month);
                                      
     		   cs.setString(20,update_user);     
     		  cs.setInt(21,Integer.parseInt(drHOA[0]));
     		 cs.setInt(22,Integer.parseInt(crHOA[0]));
     		   
     		   cs.registerOutParameter(23,java.sql.Types.NUMERIC);
     		  cs.setNull(23,java.sql.Types.NUMERIC);
                   
                   /** Procedure Execution */
                   
                   try {
                      // cs.execute();
                       cs.executeUpdate();
                       
                   }
                   catch (Exception e) {
                       System.out.println("Error while executing procedure -->"+e);
                   }
     		   //err_code=cs.getInt(23);
                   err_code = cs.getBigDecimal(23).intValue();

     		   
     		   
                 
     		   System.out.println("Error_Code ------------>>>"+err_code);     		   
     		   
     		   if ( err_code != 0 )
     		   {
     			  con.rollback();
                          System.out.println("Auto Fund Receipt Creation Failed");
                          sendMessage(response," Auto Fund Receipt Creation Failed ","ok");
                          return;                     
     		   }
                   
               
                 // Auto Generation of Bank Remittance for ECS Transaction        
                    
                    
                    System.out.println("1--->");
                    System.out.println("venkat ecs type check --->"+Transfer_Type[i]);
                    System.out.println("2-->");
                    
                    int  Verified_Authority=0;                    
     	            CallableStatement cs1=null;
                    
                    if (Transfer_Type[i].equalsIgnoreCase("E"))      
                    {
                   
                        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                        Verified_Authority= empProfile.getEmployeeId();                 
                        System.out.println("Verified_Authority::"+Verified_Authority);
                    
                        try {
                    
                            System.out.println("inside E ");
                            
                            cs1=con.prepareCall("call FAS_ECS_REMITTANCE_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?)") ;       
                            
                            cs1.setInt(1,Integer.parseInt(HO_accunitid));
                            cs1.setInt(2,Integer.parseInt(HO_officeid));
                            cs1.setInt(3, HO_cashbookyear);
                            cs1.setInt(4,HO_cashbookmonth);
                            cs1.setDate(5,txtCrea_date);
                            cs1.setString(6,"F");
                            cs1.setDouble(7,Double.parseDouble(TotAmt[i]));
                            cs1.setInt(8,Verified_Authority);
                            cs1.setString(9,update_user);    
                            cs1.registerOutParameter(10,java.sql.Types.VARCHAR); 
                            cs1.setString(10, "0");
                            cs1.execute();
                            
                            //int err_code11=cs1.getInt(10);  
                            String err_code11 = cs1.getString(10);
                            System.out.println("ECS Error Code---->"+err_code11);
                            
                            //if (err_code11 != 0)
                            if (!err_code11.equals("0"))
                            {
                                con.rollback();
                                System.out.println("Auto Fund Receipt Creation Failed");
                                sendMessage(response," Auto Fund Receipt Creation Failed ","ok");
                                return;     
                            }     
                            
                            
                        }catch(Exception e) 
                        {
                            System.out.println(e);
                        }
                        
                    }
                    
                    
                    
                    
                    
                    
                }
                
                
     	  } 	
          if ( err_code == 0)
 	  {
                        try {
                            con.commit();
                            
                           /* for ( int i=0;i<TotalRecords;i++)
                      	   {
                              
                               *//** Initially set the status as false *//*
                      	      boolean flag =false;
                      	      int k=0;
                               Date Refdate=null;
                               
                               *//** Iterate selected Records and make status true if it matches *//*
                      	      for(k=0;k<chckparameter_Voucher_no.length;k++)
                      	      {
                      	        if (i == Integer.parseInt(chckparameter_Voucher_no[k]) ) 
                      	        {   
                      	           flag=true; 
                      	        }
                      	      }
                      	      
                               
                               *//** Only true condition *//*
                               if(flag)
                      	      {  
                            
                            	   
                      	      }
                      	   }*/
                            
                            System.out.println("Transaction Commited Successfully....");
                        }
                        catch (Exception e) {
                            System.out.println("Transaction Not Comitted ");
                        }
                        sendMessage(response,"Auto Fund Receipt Created Sucessfully ","ok");                                     
          } 
             
         }
         catch(Exception e) 
         {
             System.out.println("the exception in update is"+e.getMessage());    
             try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}           
         }         
         finally
         {
           try{con.setAutoCommit(true);}
           catch(SQLException sqle)
           {System.out.println("Excep"+sqle);}
         }    
         
         
        }
        
        private void sendMessage(HttpServletResponse response,String msg,String bType)
        {
            try
            {
                String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
                response.sendRedirect(url);
            }
            catch(Exception e)
            {
                    System.out.println("error in messenger"+e);
            }
        }
        
        
        }

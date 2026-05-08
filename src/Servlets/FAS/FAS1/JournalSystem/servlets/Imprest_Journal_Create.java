package Servlets.FAS.FAS1.JournalSystem.servlets;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Imprest_Journal_Create extends HttpServlet 
{

    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    
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
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        System.out.println("it comes heree");
    	
    	String strCommand="";
        Connection con=null;
        PreparedStatement ps=null,ps2=null;
        ResultSet rs=null,result1=null,rs2=null;
        String xml="",ac_head_name="",txtMode_of_creat="",createdByModule="",textMode="",payment_type1="";
		Date txtCrea_date=null;
        HttpSession session=request.getSession(false);
        Calendar c;
        int hCode=0;
        int txtCash_Month1=0,txtCash_year1=0;
        
        int txtCash_year=0,txtCash_Month=0,AccUnitId=0,Office_Code=0,count=0,txtVoucher_no=0,ac_head_code=0,cmbSL_Code=0;
        String[] sd=null;
        try
        {
	           
             if(session==null)
             {
	               System.out.println(request.getContextPath()+"/index.jsp");
	               response.sendRedirect(request.getContextPath()+"/index.jsp");
	               return;
             }
             System.out.println(session);
           
               
        }catch(Exception e){
    	     System.out.println("Redirect Error :"+e);
        } 
        
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
             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
             System.out.println("Exception in opening connection :"+e);
             //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
        }
       
    
       
        strCommand=request.getParameter("Command");
        System.out.println("assign..here command..."+strCommand);
       
        try
        {AccUnitId = Integer.parseInt(request.getParameter("UnitCode"));
        }catch(Exception e){System.out.println(e);
        }
        
        try
        {Office_Code = Integer.parseInt(request.getParameter("Office_Code"));
        }catch(Exception e){System.out.println(e);
        }
        
        try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));
        }catch(Exception e){System.out.println("Exception to Read Cashbook Year ");}
       
        try{txtCash_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("txtCash_Month"+txtCash_Month);
        }catch(Exception e){System.out.println("Exception to Read Cashbook Month ");}
      
        try{txtVoucher_no=Integer.parseInt(request.getParameter("txtVou_no"));
        }catch(Exception e){System.out.println("Exception in txtVoucher_no ");}
        
        try{ac_head_code=Integer.parseInt(request.getParameter("ac_head_code"));
        }catch(Exception e){System.out.println("Exception in ac_head_name ");}
   
        try{cmbSL_Code=Integer.parseInt(request.getParameter("cmbSL_Code"));
        }catch(Exception e){System.out.println("Exception in cmbSL_Code ");}
        
        try{txtMode_of_creat=request.getParameter("txtMode_of_creat");
       
        System.out.println("txtMode_of_creat::::"+txtMode_of_creat);
			        if(txtMode_of_creat.equals("68-BPF"))
			        {
			        	txtMode_of_creat="I";
			        	textMode="0";
			        	createdByModule="BPF";
			        	payment_type1="B";
			        }
			        else if(txtMode_of_creat.equals("68-SC"))
			        {
			        	txtMode_of_creat="I";
			        	textMode="IT";
			        	createdByModule="SC";
			        	payment_type1="C";
			        }
			        else if(txtMode_of_creat.equals("69-BPF"))
			        {
			        	txtMode_of_creat="T";
			        	textMode="0";
			        	createdByModule="BPF";
			        	payment_type1="B";
			        }
			        else if(txtMode_of_creat.equals("69-SC"))
			        {
			        	txtMode_of_creat="T";
			        	textMode="IT";
			        	createdByModule="SC";
			        	payment_type1="C";
			        }
        
        }
        catch(Exception e){System.out.println("Err in Journal Type ::: "+e.getMessage());}
       
        xml="<response>";
        if(strCommand.equalsIgnoreCase("loadVoucher")) 
        {
	    	  xml=xml+"<command>loadVoucher</command>";      
            int finyear1=Integer.parseInt(request.getParameter("fyear1"));
            int fmonth=Integer.parseInt(request.getParameter("fmonth"));
           // int finyear2=Integer.parseInt(request.getParameter("fyear2"));
            
            try{txtCash_year1=Integer.parseInt(request.getParameter("txtCB_Year"));
            }catch(Exception e){System.out.println("Exception to Read Cashbook Year ");}
            
            try{txtCash_Month1=Integer.parseInt(request.getParameter("txtCB_Month"));
            System.out.println("txtCash_Month1"+txtCash_Month1);
            }catch(Exception e){System.out.println("Exception to Read Cashbook Month ");}
            
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
	    	  "      coalesce(SUM(jour_amt),NULL,0,SUM(jour_amt)) AS jour_amt ,\n" + 
	    	  "      coalesce(SUM(rec_amt),NULL,0,SUM(rec_amt))   AS rec_amt\n" + 
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
	    	  "          WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
	    	 // "          AND m.accounting_for_office_id = "+Office_Code+"\n" + 
	    	  "           AND to_date(m.cashbook_month\n" + 
	    	  "          ||'-'\n" + 
	    	  "          || + m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )\n" + 
	    	  "          || '-'\n" + 
	    	  "          || ("+finyear1+") , 'mm-yyyy')\n" + 
	    	  "        AND to_date( ( "+txtCash_Month1+" )\n" + 
	    	  "          ||'-'\n" + 
	    	  "          || ( "+txtCash_year1+" ) , 'mm-yyyy')\n" + 
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
	    	  "          AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
	    	  "          )j\n" + 
	    	  "        ON p.voucher_no    = j.CB_REF_NO\n" + 
	    	  "        AND p.payment_date = j.CB_REF_DATE\n" + 
	    	  "        AND p.sl_type_code = j_sl_type_code\n" + 
	    	  "        AND p.sl_code      = j_sl_code\n" + 
	    	  "          --   where voucher_no is not null\n" + 
	    	  "        )as opt1 " + 
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
	    	  "          WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
	    	//  "          AND m.accounting_for_office_id = "+Office_Code+"\n" + 
	    	  "         AND to_date(m.cashbook_month\n" + 
	    	  "          ||'-'\n" + 
	    	  "          || + m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )\n" + 
	    	  "          || '-'\n" + 
	    	  "          || ("+finyear1+") , 'mm-yyyy')\n" + 
	    	  "        AND to_date( ( "+txtCash_Month1+" )\n" + 
	    	  "          ||'-'\n" + 
	    	  "          || ( "+txtCash_year1+" ) , 'mm-yyyy')\n" + 
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
	    	  "          AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
	    	  "          AND m.RECEIPT_STATUS           ='L'\n" + 
	    	  "          )r\n" + 
	    	  "        ON p.voucher_no    = r.RECEIVABLE_VOUCHER_NO\n" + 
	    	  "        AND p.payment_date = r.RECEIVABLE_VOUCHER_DATE\n" + 
	    	  "        AND p.sl_type_code = r.r_sl_type_code\n" + 
	    	  "        AND p.sl_code      = r.r_sl_code\n" + 
	    	  "        ) as opt2 " + 
	    	  "      ) as opt3 " + 
	    	  "    GROUP BY voucher_no,\n" + 
	    	  "      payment_date,\n" + 
	    	  "      sl_type_code,\n" + 
	    	  "      sl_code ,\n" + 
	    	  "      pay_amt\n" + 
	    	  "    ORDER BY voucher_no,\n" + 
	    	  "      payment_date,\n" + 
	    	  "      sl_type_code,\n" + 
	    	  "      sl_code\n" + 
	    	  "    ) as opt4 " + 
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
		    	 
	              System.out.println("SQL:::"+sql);

		      try
		      {
			    	  ps=con.prepareStatement(sql); 
			          result1=ps.executeQuery();
	           	
	            	  while(result1.next()) 
		              {
	            		  	
	            		//  System.out.println("Inside while");
	            			 xml+= "<count>";
		            	  	 xml+= "<voucher_no>"+result1.getString("voucher_no")+"</voucher_no>";
                                         xml+= "<payment_date>"+result1.getString("payment_date")+"</payment_date>";
                                         xml+= "<sl_code>"+result1.getInt("sl_code")+"</sl_code>";
                                      
			                 xml+= "</count>"; 
			                 count++;
			          }	
	            	  
		              if(count==0) 
		                  	 xml+="<flag>NoData</flag>";	            
		              else
		                  	 xml+="<flag>success</flag>";
		      }   
		      catch(Exception e) 
		      {
			          System.out.println("Exception in assigning..."+e);
			          xml+="<flag>"+e.getMessage()+"</flag>";
		      }
    	   
        }
	    else if (strCommand.equalsIgnoreCase("loadVoucher_details"))
	    {
                xml=xml+"<command>loadVoucherdetails</command>";   
              //  int vno=Integer.parseInt(request.getParameter("voucher_no"));
               String vno_one=request.getParameter("voucher_no");
               String vno_split[]=vno_one.split("-");
               String vno=vno_split[0];
               String slCode=vno_split[1];
               
                int fyear1=Integer.parseInt(request.getParameter("fyear1"));
                int fmonth=Integer.parseInt(request.getParameter("fmonth"));
               // int fyear2=Integer.parseInt(request.getParameter("fyear2"));
                
                try{txtCash_year1=Integer.parseInt(request.getParameter("txtCB_Year"));
                }catch(Exception e){System.out.println("Exception to Read Cashbook Year ");}
                
                try{txtCash_Month1=Integer.parseInt(request.getParameter("txtCB_Month"));
                System.out.println("txtCash_Month1"+txtCash_Month1);
                }catch(Exception e){System.out.println("Exception to Read Cashbook Month ");}
                
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
                "          WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
            //    "          AND m.accounting_for_office_id = "+Office_Code+"\n" + 
                "           AND to_date(m.cashbook_month\n" + 
                "          ||'-'\n" + 
                "          || + m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )\n" + 
                "          || '-'\n" + 
                "          || ("+fyear1+") , 'mm-yyyy')\n" + 
                "        AND to_date( ( "+txtCash_Month1+" )\n" + 
                "          ||'-'\n" + 
                "          || ( "+txtCash_year1+" ) , 'mm-yyyy')\n" + 
                "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                "          AND m.payment_status           ='L'\n" + 
                "          And m.payment_type ='"+payment_type1+"'\n" + 
                "          AND m.CREATED_BY_MODULE='"+createdByModule+"'\n" + 
                "          and m.VOUCHER_NO="+vno+"and t.SUB_LEDGER_CODE="+slCode+ 
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
                "          AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
                "          )j\n" + 
                "        ON p.voucher_no    = j.CB_REF_NO\n" + 
                "        AND p.payment_date = j.CB_REF_DATE\n" + 
                "        AND p.sl_type_code = j_sl_type_code\n" + 
                "        AND p.sl_code      = j_sl_code\n" + 
                "         )\n" + 
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
                "          WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
          //      "          AND m.accounting_for_office_id = "+Office_Code+"\n" + 
                "         AND to_date(m.cashbook_month\n" + 
                "          ||'-'\n" + 
                "          || + m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )\n" + 
                "          || '-'\n" + 
                "          || ("+fyear1+") , 'mm-yyyy')\n" + 
                "        AND to_date( ( "+txtCash_Month1+" )\n" + 
                "          ||'-'\n" + 
                "          || ( "+txtCash_year1+" ) , 'mm-yyyy')\n" + 
                "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                "          AND m.payment_status           ='L'\n" + 
                "          and m.payment_type ='"+payment_type1+"'\n" + 
                "          AND CREATED_BY_MODULE='"+createdByModule+"'\n" + 
                "          and m.VOUCHER_NO="+vno+"and t.SUB_LEDGER_CODE="+slCode+  
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
                "          AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
                "          AND m.RECEIPT_STATUS           ='L'\n" + 
                "          )r\n" + 
                "        ON p.voucher_no    = r.RECEIVABLE_VOUCHER_NO\n" + 
                "        AND p.payment_date = r.RECEIVABLE_VOUCHER_DATE\n" + 
                "        AND p.sl_type_code = r.r_sl_type_code\n" + 
                "        AND p.sl_code      = r.r_sl_code\n" + 
                "       )\n" + 
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
                "WHERE bal    >0";   
                       
                    System.out.println("SQL:::"+sql);

                    try
                    {
                                ps=con.prepareStatement(sql); 
                                result1=ps.executeQuery();
                     
                        while(result1.next()) 
                            {
                                      
                            //    System.out.println("Inside while");
                                       xml+= "<count>";
                                    xml+= "<payment_date>"+result1.getString("payment_date")+"</payment_date>";
                                       xml+= "</count>"; 
                                       count++;
                                }     
                        
                            if(count==0) 
                                       xml+="<flag>NoData</flag>";                
                            else
                                       xml+="<flag>success</flag>";
                    }   
                    catch(Exception e) 
                    {
                                System.out.println("Exception in assigning..."+e);
                                xml+="<flag>"+e.getMessage()+"</flag>";
                    }
            }
        
	    else if (strCommand.equalsIgnoreCase("finalPay"))
	    {
	    	int fromMonth=0,fromyear=0,toMonth=0,toyear=0;
	    	//modifications on 21mar2012
	      if(txtCash_Month<=3)
	      {
	    	  fromMonth=4;
	    	  fromyear=txtCash_year-1;
	    	  toMonth=txtCash_Month;
	    	  toyear=txtCash_year;
	      }
	      else if(txtCash_Month>3)
	      {
	    	  fromMonth=4;
	    	  fromyear=txtCash_year;
	    	  toMonth=txtCash_Month;
	    	  toyear=txtCash_year;
	      }
	     //   int count=0;
	        xml+="<command>finalPay</command>";
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
	                         "          WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
	                     //    "          AND m.accounting_for_office_id = "+Office_Code+"\n" + 
	                       //  "          AND m.cashbook_year            = "+txtCash_year+"\n" + 
	                       //  "          AND m.cashbook_month           <= "+txtCash_Month+" \n"+
	                        " and To_Date((m.Cashbook_Month  "+
	                        "		  ||'-'"+
	                        "		  || m.Cashbook_Year),'mm-yyyy') BETWEEN To_Date("+fromMonth+
	                        "		  ||'-'"+
	                        "		  ||"+fromyear+",'mm-yyyy')"+
	                        "		AND to_date("+toMonth+
	                        "		  ||'-'"+
	                        "		  ||"+toyear+",'mm-yyyy')"+
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
	                         "          AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
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
	                         "          WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
	                     //    "          AND m.accounting_for_office_id = "+Office_Code+"\n" + 
	                      //   "          AND m.cashbook_year            = "+txtCash_year+"\n" + 
	                         //"          AND m.cashbook_month           <= "+txtCash_Month+" \n"+
	                         " and To_Date((m.Cashbook_Month  "+
		                        "		  ||'-'"+
		                        "		  || m.Cashbook_Year),'mm-yyyy') BETWEEN To_Date("+fromMonth+
		                        "		  ||'-'"+
		                        "		  ||"+fromyear+",'mm-yyyy')"+
		                        "		AND to_date("+toMonth+
		                        "		  ||'-'"+
		                        "		  ||"+toyear+",'mm-yyyy')"+
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
	                         "          AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
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
	                       //  "WHERE bal    > 0 )kk \n" + 
	                         "WHERE bal    !=0 )kk \n" +
	                         "where kk.employee_id="+slcode1+"\n" + 
	                         "group by kk.employee_id \n";
	                         
	         try {
	               System.out.println("sql::final_pay:::"+sql);        
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
//	                   xml=xml+"</response>";
//	        System.out.println(xml);               
//	        out.println(xml);


	    }
        
        else if(strCommand.equalsIgnoreCase("hCodeChecking"))
        {
        	//System.out.println("1111111111111111");
        	 hCode=Integer.parseInt(request.getParameter("hCode"));
        	System.out.println("hCodeChecking"+hCode);
        	 xml=xml+"<command>hCodeChecking</command>";
        	try{
        		if(hCode==820103 || hCode==820102)
        		{
        			ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE," +
                                "SUB_LEDGER_TYPE_APPLICABLE,REMARKS,sl_mandatory from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y'" +
                                "and ACCOUNT_HEAD_CODE="+hCode);
        		}
        		else{
        	
        			ps=con.prepareStatement("select ACCOUNT_HEAD_CODE," +
                                "ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS,sl_mandatory " +
                                "from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE="+hCode);
                             //   "and MAJOR_HEAD_CODE in('E','L') ");
        		}
        		rs=ps.executeQuery();
        		  if(rs.next())
                  {
                  xml=xml+"<flag>success</flag><hid>"+hCode+
                  "</hid><hdesc>"+rs.getString("ACCOUNT_HEAD_DESC")+
                  "</hdesc><BalType>"+rs.getString("BALANCE_TYPE")+
                  "</BalType><SL_YN>"+rs.getString("SUB_LEDGER_TYPE_APPLICABLE")+
                      "</SL_YN><rmk>"+rs.getString("REMARKS")+"</rmk><sl_man>"+rs.getString("sl_mandatory")+"</sl_man>";
                        
                      if(rs.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) 
                      {
                         int sl_cnt=0;
                    	  ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'"); //Status Field added in 05-07-19 onwards
                          ps.setInt(1,hCode);
                          rs=ps.executeQuery();
                          while(rs.next())
                          {
                              sl_cnt++;
                        	  xml=xml+"<SLCODE>"+rs.getInt("SUB_LEDGER_TYPE_CODE")+"</SLCODE>";
                              System.out.println(rs.getInt("SUB_LEDGER_TYPE_CODE")+"code");
                              if(rs.getInt("SUB_LEDGER_TYPE_CODE")!=0)
                              {
                              System.out.println("take SL DESC");
                              ps2=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                              ps2.setInt(1,rs.getInt("SUB_LEDGER_TYPE_CODE"));
                              rs2=ps2.executeQuery();
                              if(rs2.next())
                              xml=xml+"<SLDESC>"+rs2.getString("SUB_LEDGER_TYPE_DESC")+"</SLDESC>";
                              }
                          }
                          if(sl_cnt==0)
                          {
                          	System.out.println("Status is 'N'");
                              xml = xml + "<flag>failure</flag>";
                          }
                      }                            
                  }
                  else
                   {
                     System.out.println("No record found");
                     xml=xml+"<flag>failure</flag>";
                    }
        	}
        	catch(Exception e2)
        	{
        		System.out.println("exception in hcode:"+e2);
        		 xml +="<flag>failure</flag>";
        	}
            
            
        }
        else if(strCommand.equalsIgnoreCase("loadSLType"))
        {
            int fyear1=Integer.parseInt(request.getParameter("fyear1"));
            int fmonth=Integer.parseInt(request.getParameter("fmonth"));
            try{txtCash_year1=Integer.parseInt(request.getParameter("txtCB_Year"));
            }catch(Exception e){System.out.println("Exception to Read Cashbook Year ");}
            
            try{txtCash_Month1=Integer.parseInt(request.getParameter("txtCB_Month"));
            System.out.println("txtCash_Month1"+txtCash_Month1);
            }catch(Exception e){System.out.println("Exception to Read Cashbook Month ");}
            
        	  xml=xml+"<command>loadSLType</command>";
        	  try	
        	  {			        	 			  	     
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
                   	  "          WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
                   	//  "          AND m.accounting_for_office_id = "+Office_Code+"\n" + 
                   	  "          AND to_date(m.cashbook_month  \n" + 
                   	  "            ||'-'  \n" + 
                   	  "            ||  m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )  \n" + 
                   	  "            || '-'  \n" + 
                   	  "            || ("+fyear1+") , 'mm-yyyy')  \n" + 
                   	  "          AND to_date( ( "+txtCash_Month1+" )  \n" + 
                   	  "            ||'-'  \n" + 
                   	  "            || ( "+txtCash_year1+" ) , 'mm-yyyy')  \n" + 
                   	  "          AND m.VOUCHER_NO               = "+txtVoucher_no+"\n" + 
                   	  "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                   	  "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                   	  "          AND m.payment_status           ='L'\n" + 
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
                   	  "          AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
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
                   	  "          WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
                   //	  "          AND m.accounting_for_office_id = "+Office_Code+"\n" + 
                   	  "         AND to_date(m.cashbook_month  \n" + 
                   	  "            ||'-'  \n" + 
                   	  "            ||  m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+")  \n" + 
                   	  "            || '-'  \n" + 
                   	  "            || ("+fyear1+") , 'mm-yyyy')  \n" + 
                   	  "          AND to_date( ( "+txtCash_Month1+" )  \n" + 
                   	  "            ||'-'  \n" + 
                   	  "            || ( "+txtCash_year1+" ) , 'mm-yyyy')\n" + 
                   	  "          AND m.VOUCHER_NO               = "+txtVoucher_no+"\n" + 
                   	  "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                   	  "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                   	  "          AND m.payment_status           ='L'\n" + 
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
                   	  "            t.AMOUNT                                      AS rec_amt\n" + 
                   	  "          FROM fas_receipt_master m,\n" + 
                   	  "            fas_receipt_transaction t\n" + 
                   	  "          WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                   	  "          AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                   	  "          AND m.cashbook_month           =t.cashbook_month\n" + 
                   	  "          AND m.cashbook_year            =t.cashbook_year\n" + 
                   	  "          AND m.receipt_no               = t.receipt_no\n" + 
                   	  "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                   	  "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                   	  "          AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
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
                   	  "WHERE bal    >0";
                                    
		        	  System.out.println(" SQL in imp journal::::"+sql);
		        	  ps=con.prepareStatement(sql);                                                                                   
                         	  rs=ps.executeQuery();
                                  
		        	  while(rs.next())
		        	  {
        		  		//  	 System.out.println("inside while");
	        		   		 xml +="<SUB_LEDGER_CODE>"+rs.getString("sl_code")+"</SUB_LEDGER_CODE>";
	        		   		 xml +="<ENAME>"+rs.getString("ENAME")+"</ENAME>";			        		   		 
	        		   		 count++;
		        	  }
		        	  if(count>0)
	        		  	     xml +="<flag>success</flag>";
		        	  else
	        		  		 xml +="<flag>failure</flag>";
        	  }
        	  catch(Exception e)
        	  {
    		  		  System.out.println("Err in loadSLType:   "+e.getMessage());
    		  		  xml +="<flag>failure</flag>";
        	  }
        }
        else if(strCommand.equalsIgnoreCase("loadPaymentTotal"))
        {
            int financialyear1=Integer.parseInt(request.getParameter("fyear1"));
            int fmonth=Integer.parseInt(request.getParameter("fmonth"));
            int txtCB_Month_ttl=Integer.parseInt(request.getParameter("txtCB_Month"));
              xml=xml+"<command>loadPaymentTotal</command>";
              try   
              {
                          String sql= "SELECT *\n" + 
                          "FROM\n" + 
                          "  (SELECT voucher_no,\n" + 
                        //  "    payment_date,\n" + 
                          "    sl_type_code,\n" + 
                          "    sl_code,\n" + 
                       //   "    pay_amt,\n" + 
                          "    DECODE(SUM(jour_amt),NULL,0,SUM(jour_amt)) AS jour_amt ,\n" + 
                          "    DECODE(SUM(pay_amt),NULL,0,SUM(pay_amt))   AS pay_amt, DECODE(SUM(rec_amt),NULL,0,SUM(rec_amt))   AS rec_amt\n" + 
                          "  FROM\n" + 
                          "    (SELECT voucher_no,\n" + 
                          "      payment_date,\n" + 
                          "      sl_type_code,\n" + 
                          "      sl_code,\n" + 
                          "      pay_amt,\n" + 
                          "      jour_amt,\n" + 
                          "      0 AS rec_amt\n" + 
                          "    FROM\n" + 
                          "      (SELECT *\n" + 
                          "      FROM\n" + 
                          "        (SELECT m.voucher_no,\n" + 
                          "          TO_CHAR(m.PAYMENT_DATE,'DD/MM/YY')AS payment_date,\n" + 
                          "          t.SUB_LEDGER_TYPE_CODE            AS sl_type_code,\n" + 
                          "          t.SUB_LEDGER_CODE                 AS sl_code,\n" + 
                          "          t.AMOUNT                          AS pay_amt\n" + 
                          "        FROM fas_payment_master m,\n" + 
                          "          fas_payment_transaction t\n" + 
                          "        WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
                        //  "        AND m.accounting_for_office_id = "+Office_Code+"\n" + 
                          "       AND to_date(m.cashbook_month  \n" + 
                          "            ||'-'  \n" + 
                          "            ||  m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )  \n" + 
                          "            || '-'  \n" + 
                          "            || ("+financialyear1+") , 'mm-yyyy')  \n" + 
                          "          AND to_date( ( "+txtCB_Month_ttl+" )  \n" + 
                          "            ||'-'  \n" + 
                          "            || ( "+txtCash_year+" ) , 'mm-yyyy')\n" + 
                          "        AND m.VOUCHER_NO               = "+txtVoucher_no+"\n" + 
                          "        AND t.SUB_LEDGER_CODE          ="+cmbSL_Code+"\n" + 
                          "        AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                          "        OR m.mode_of_creation          ='"+textMode+"')\n" + 
                          "        AND m.payment_status          ='L'\n" + 
                          "        AND m.accounting_unit_id      = t.accounting_unit_id\n" + 
                          "        AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                          "        AND m.cashbook_month          =t.cashbook_month\n" + 
                          "        AND m.cashbook_year           =t.cashbook_year\n" + 
                          "        AND m.voucher_no              =t.voucher_no\n" + 
                          "        )p\n" + 
                          "      LEFT OUTER JOIN\n" + 
                          "        (SELECT t.SUB_LEDGER_TYPE_CODE AS j_sl_type_code,\n" + 
                          "          t.SUB_LEDGER_CODE            AS j_sl_code,\n" + 
                          "          t.cb_ref_no,\n" + 
                          "          TO_CHAR(t.cb_ref_date,'DD/MM/YY') AS cb_ref_date,\n" + 
                          "          amount                            AS jour_amt\n" + 
                          "        FROM fas_journal_master m,\n" + 
                          "          fas_journal_transaction t\n" + 
                          "        WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                          "        AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                          "        AND m.cashbook_month           =t.cashbook_month\n" + 
                          "        AND m.cashbook_year            =t.cashbook_year\n" + 
                          "        AND m.voucher_no               = t.voucher_no\n" + 
                          "        AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                          "        OR m.mode_of_creation          ='"+textMode+"')\n" + 
                          "        AND m.JOURNAL_STATUS    ='L'\n" + 
                          "        AND t.cr_dr_indicator   ='CR'\n" + 
                          "        AND m.accounting_unit_id="+AccUnitId+"\n" + 
                          "        )j\n" + 
                          "      ON p.voucher_no    = j.CB_REF_NO\n" + 
                          "      AND p.payment_date = j.CB_REF_DATE\n" + 
                          "      AND p.sl_type_code = j_sl_type_code\n" + 
                          "      AND p.sl_code      = j_sl_code\n" + 
                          "      WHERE voucher_no  IS NOT NULL\n" + 
                          "      )\n" + 
                          "    UNION ALL\n" + 
                          "    SELECT voucher_no,\n" + 
                          "      payment_date,\n" + 
                          "      sl_type_code,\n" + 
                          "      sl_code,\n" + 
                          "      pay_amt,\n" + 
                          "      0 AS jour_amt,\n" + 
                          "      rec_amt\n" + 
                          "    FROM\n" + 
                          "      (SELECT *\n" + 
                          "      FROM\n" + 
                          "        (SELECT m.voucher_no,\n" + 
                          "          TO_CHAR(m.PAYMENT_DATE,'DD/MM/YY')AS payment_date,\n" + 
                          "          t.SUB_LEDGER_TYPE_CODE            AS sl_type_code,\n" + 
                          "          t.SUB_LEDGER_CODE                 AS sl_code,\n" + 
                          "          t.AMOUNT                          AS pay_amt\n" + 
                          "        FROM fas_payment_master m,\n" + 
                          "          fas_payment_transaction t\n" + 
                          "        WHERE m.accounting_unit_id     = "+AccUnitId+"\n" + 
                         // "        AND m.accounting_for_office_id = "+Office_Code+"\n" + 
                          "       AND to_date(m.cashbook_month  \n" + 
                          "            ||'-'  \n" + 
                          "            ||  m.cashbook_year, 'mm-yyyy') BETWEEN to_date( ("+fmonth+" )  \n" + 
                          "            || '-'  \n" + 
                          "            || ("+financialyear1+") , 'mm-yyyy')  \n" + 
                          "          AND to_date( ( "+txtCB_Month_ttl+" )  \n" + 
                          "            ||'-'  \n" + 
                          "            || ( "+txtCash_year+" ) , 'mm-yyyy')\n" + 
                          "        AND m.VOUCHER_NO               = "+txtVoucher_no+"\n" + 
                          "        AND t.SUB_LEDGER_CODE          ="+cmbSL_Code+"\n" + 
                          "        AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                          "        OR m.mode_of_creation          ='"+textMode+"')\n" + 
                          "        AND m.payment_status          ='L'\n" + 
                          "        AND m.accounting_unit_id      = t.accounting_unit_id\n" + 
                          "        AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                          "        AND m.cashbook_month          =t.cashbook_month\n" + 
                          "        AND m.cashbook_year           =t.cashbook_year\n" + 
                          "        AND m.voucher_no              =t.voucher_no\n" + 
                          "        )p\n" + 
                          "      RIGHT OUTER JOIN\n" + 
                          "        (SELECT t.SUB_LEDGER_TYPE_CODE AS r_sl_type_code,\n" + 
                          "          t.SUB_LEDGER_CODE            AS r_sl_code,\n" + 
                          "          m.RECEIVABLE_VOUCHER_NO,\n" + 
                          "          TO_CHAR(m.RECEIVABLE_VOUCHER_DATE,'DD/MM/YY') AS RECEIVABLE_VOUCHER_DATE,\n" + 
                          "          t.AMOUNT                                      AS rec_amt\n" + 
                          "        FROM fas_receipt_master m,\n" + 
                          "          fas_receipt_transaction t\n" + 
                          "        WHERE m.accounting_unit_id     =t.accounting_unit_id\n" + 
                          "        AND m.accounting_for_office_id = t.accounting_for_office_id\n" + 
                          "        AND m.cashbook_month           =t.cashbook_month\n" + 
                          "        AND m.cashbook_year            =t.cashbook_year\n" + 
                          "        AND m.receipt_no               = t.receipt_no\n" + 
                          "        AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                          "        OR m.mode_of_creation          ='"+textMode+"')\n" + 
                          "        AND m.accounting_unit_id       ="+AccUnitId+"\n" + 
                          "        AND m.RECEIPT_STATUS           ='L'\n" + 
                          "        )r\n" + 
                          "      ON p.voucher_no    = r.RECEIVABLE_VOUCHER_NO\n" + 
                          "      AND p.payment_date = r.RECEIVABLE_VOUCHER_DATE\n" + 
                          "      AND p.sl_type_code = r.r_sl_type_code\n" + 
                          "      AND p.sl_code      = r.r_sl_code\n" + 
                          "      WHERE voucher_no  IS NOT NULL\n" + 
                          "      )\n" + 
                          "    )\n" + 
                          "  GROUP BY voucher_no,\n" + 
                       //   "    payment_date,\n" + 
                          "    sl_type_code,\n" + 
                          "    sl_code \n" + 
                        //  "    pay_amt\n" + 
                          "  ORDER BY "+
                     //     + "payment_date,\n" + 
                          "    voucher_no,\n" + 
                          "    sl_type_code,\n" + 
                          "    sl_code\n" + 
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
                          "  )y\n" + 
                          "ON x.sl_code = y.employee_id  ";          
	                  
                      System.out.println(" SQL :::"+sql);
                      ps=con.prepareStatement(sql);
                 
                      rs=ps.executeQuery();                                   
                      if(rs.next())
                      {
		                     System.out.println("pay_amt :"+rs.getDouble("pay_amt"));        
		                     System.out.println("jour_amt :"+rs.getDouble("jour_amt"));        
		                     System.out.println("rec_amt :"+rs.getDouble("rec_amt")); 
		                     xml=xml+"<pay_amt>"+rs.getDouble("pay_amt")+"</pay_amt>";
		                     xml=xml+"<jour_amt>"+rs.getDouble("jour_amt")+"</jour_amt>";
		                     xml=xml+"<rec_amt>"+rs.getDouble("rec_amt")+"</rec_amt>";
		                     count++;
                      }
                      if(count>0)
                             xml +="<flag>success</flag>";
                      else
                             xml +="<flag>failure</flag>";
              }
              catch(Exception e)
              {
                             System.out.println("Err in loadPaymentTotal:   "+e.getMessage());
                             xml +="<flag>failure</flag>";
              }
                       
        }
        xml+="</response>"; 
        System.out.println("XML :::::"+xml);
        out.println(xml);
	
	}
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        String strCommand="";
        Connection con=null;
        ResultSet rs=null,rs2=null,rs4=null;
        CallableStatement cs=null;
        PreparedStatement ps=null,ps2=null,ps3=null,ps4=null;
        int paymonth=0;
        String xml="";
        int count=0;
        String year1=null;
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
	          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	          Class.forName(strDriver.trim());
	          con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	    }
	    catch(Exception e)
	    {
	          System.out.println("Exception in opening connection :"+e);
	          //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
	
	    }
        
        try 
        {        
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
             Calendar c,c1;
             int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtJournalVou_No=0,txtPayVou_No=0;
             int Total_TRN_Rec=0,finalCount=0,sl_code_one=0;
             String  txtCheque_NO="",txtCB_REF_TYPE="",textMode="",payment_type1="";
           
             Date txtCrea_date=null,txtCheque_date=null,txtPayVou_date=null,payment_dates=null;
			 String txtRemarks="";
			  
		     int cmbMas_SL_type=0,cmbMas_SL_Code=0;
		     String cmbMas_SL_type1=null;
		     String txtMode_of_creat="",txtCreat_By_Module="GJV";
			 double dep_rate=0;                           // changes here
			 String update_user=(String)session.getAttribute("UserId");
			 long l=System.currentTimeMillis();
			 Timestamp ts=new Timestamp(l);

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
            
             System.out.println("b4 getting month and year");
             try{txtCash_year=Integer.parseInt(sd[2]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_year "+txtCash_year);
                        
             try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                      
             try{txtPayVou_No=Integer.parseInt(request.getParameter("txtJournalVou_No"));}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtPayVou_No "+txtPayVou_No);
            
            
             // changes here
             try{
            	 cmbMas_SL_type1=request.getParameter("cmbMas_SL_type");
            	 System.out.println("cmbMas_SL_type1 "+cmbMas_SL_type1);
            	 }
            
             catch(Exception e){System.out.println("exception in cmbMas_SL_type1:"+e );}
           
            
             if(cmbMas_SL_type1.equals("68-BPF"))
                        {
		        	txtMode_of_creat="I";
		        	cmbMas_SL_type=68;
                                textMode="0";
                                payment_type1="B";
		        }
              else if(cmbMas_SL_type1.equals("68-SC"))
		        {
		        	txtMode_of_creat="I";
		        	cmbMas_SL_type=68;
                                textMode="IT";
                                payment_type1="C";
		        }
              else if(cmbMas_SL_type1.equals("69-BPF"))
		        {
		        	txtMode_of_creat="T";
		        	cmbMas_SL_type=69;
                                textMode="0";
                                payment_type1="B";
		        }
              else if(cmbMas_SL_type1.equals("69-SC"))
		        {System.out.println("inside 69");
		        	txtMode_of_creat="T";
		        	cmbMas_SL_type=69;
                                textMode="IT";
                                payment_type1="C";
		        }
//             if(cmbMas_SL_type==68)
//            	 txtMode_of_creat="I";
//             else
//            	 txtMode_of_creat="T";
            
  	  		 try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
          	 catch(Exception e){System.out.println("exception"+e );}                  
         // 	 System.out.println("cmbMas_SL and office "+cmbMas_SL_type+" "+cmbMas_SL_Code);//+" "+cmbMas_offid);
            
         
             txtRemarks=request.getParameter("txtRemarks");
         //    System.out.println("txtRemarks "+txtRemarks);
             try 
             {   
                    con.clearWarnings();
                    con.setAutoCommit(false);
                 //   System.out.println("inside proc");
                    String No_TRN_Rec[]=request.getParameterValues("H_code");
                    Total_TRN_Rec=No_TRN_Rec.length;//Integer.parseInt(No_TRN_Rec);
                    System.out.println(Total_TRN_Rec+" Total_TRN_Rec");
                    cs=con.prepareCall("{call FAS_IMP_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ;                     
                    cs.setInt(1,cmbAcc_UnitCode);
                    cs.setInt(2,cmbOffice_code);
                    cs.setInt(3,txtCash_year);
                    cs.setInt(4,txtCash_Month_hid);
                    cs.setInt(5,txtJournalVou_No);
                    cs.setDate(6,txtCrea_date);                                      
                    cs.setInt(7,cmbMas_SL_type);
	                cs.setInt(8,cmbMas_SL_Code);
	                cs.setDouble(9,dep_rate);
	                cs.setString(10,txtCheque_NO);
	                cs.setDate(11,txtCheque_date);
	                cs.setString(12,txtCB_REF_TYPE);
	                cs.setInt(13,Total_TRN_Rec);
	                cs.setString(14,txtRemarks);
	                cs.setString(15,txtMode_of_creat);
	                cs.setString(16,txtCreat_By_Module);
	                cs.setString(17,"insert");                     
	                cs.registerOutParameter(5,java.sql.Types.NUMERIC);
	                cs.registerOutParameter(18,java.sql.Types.NUMERIC);  
	                cs.setString(19,update_user);
	                cs.setTimestamp(20,ts);
                    System.out.println("b4 exe ");
                    cs.execute();
                    txtJournalVou_No=cs.getInt(5);
                    int errcode=cs.getInt(18);
                    System.out.println("txtJournalVou_No ::: "+txtJournalVou_No);
                    System.out.println("SQLCODE:::"+errcode);
                    if(errcode!=0)
                    {         
	                       System.out.println("redirect");
	                       sendMessage(response,"The Imprest Journal Creation Failed ","ok");
	                       xml=xml+"<flag>failure</flag>";                          
                    }
                    else
                    {  
	                       String Grid_H_code[]=request.getParameterValues("H_code");
	                       String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
	                       String Grid_SL_type[]=request.getParameterValues("SL_type");
	                       String Grid_SL_code[]=request.getParameterValues("SL_code");
	                       String Grid_Bill_No[]=request.getParameterValues("Bill_NO");
	                       String Grid_Bill_date[]=request.getParameterValues("Bill_date");
	                       String Grid_Bill_type[]=request.getParameterValues("Bill_type");
	                        
	                       String Grid_Agree_No[]=request.getParameterValues("Agree_No");
	                       String Grid_Agree_date[]=request.getParameterValues("Agree_date");
	                         
	                       String Grid_sl_amt[]=request.getParameterValues("sl_amt");
	                       String Grid_particular[]=request.getParameterValues("particular");
	                       
	                       int chb_payment_year=Integer.parseInt(request.getParameter("txtCB_Year"));
	                       int chb_payment_month=Integer.parseInt(request.getParameter("txtCB_Month"));
	                       
	                       String sql1 ="select to_date(PAYMENT_DATE,'dd/mm/yyyy')as PAYMENT_DATE from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and MODE_OF_CREATION='"+txtMode_of_creat+"' and PAYMENT_STATUS!='C' ";
	                       ps=con.prepareStatement(sql1);
	                       ps.setInt(1,cmbAcc_UnitCode);
                           ps.setInt(2,cmbOffice_code);
                           ps.setInt(3,chb_payment_year);
                           ps.setInt(4,chb_payment_month);
                           ps.setInt(5,txtPayVou_No);
                        //   ps.setString(6,txtMode_of_creat);
                           rs=ps.executeQuery();
                           if(rs.next())
                           {
                        	   		txtPayVou_date=rs.getDate("PAYMENT_DATE");
                           }
	                       ps.close();
	                       
	                        
	                       String sql="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
				                    "   ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
				                    "   CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
				                    "   BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
				                    "   AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) "+
				                    "   values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
			                         
	                         
	                         
	                       int SL_NO=1,txtAcc_HeadCode=0,cmbSL_Code=0,cmbSL_type=0,cmbSL_Code_update=0;
	                       Date txtBill_Date=null,txtAgree_Date=null,txtCheque_DD_date=null;
	                       double txtsub_Amount=0;                                  
	                       String rad_sub_CR_DR="",txtBill_no="",txtBill_Type="",txtAgree_No="",txtParticular="";
	                       String txtCheque_DD="",txtCheque_DD_NO="";  

                           ps=con.prepareStatement(sql);
                           for(int k=0;k<Grid_H_code.length;k++) 
                           {
                           try{
                                   try{txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   rad_sub_CR_DR=Grid_CR_DR_type[k];                                   
                                   try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                   try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
                                 //  System.out.println("Grid_H_code[k] "+Grid_H_code[k]);
                                       if(Grid_CR_DR_type[k].equals("CR")) {
                                      
                                           cmbSL_Code_update=Integer.parseInt(Grid_SL_code[k]);
                                           System.out.println("cmbSL_Code_update:::"+cmbSL_Code_update);
                                       }
                                   
                                   System.out.println("Grid_CR_DR_type[k] "+Grid_CR_DR_type[k]);
                                   System.out.println("Grid_SL_type[k]"+Grid_SL_type[k]+"u");
                              //     System.out.println("Grid_SL_code[k]"+Grid_SL_code[k]+"from here"+cmbSL_Code);                                   
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
                                    
                                 /*  System.out.println("txtBill_no..."+txtBill_no);
                                   System.out.println("txtBill_Type..."+txtBill_Type);
                                   System.out.println("txtBill_Date..."+txtBill_Date);
                                   System.out.println("txtAgree_No..."+txtAgree_No);
                                   System.out.println("txtAgree_Date..."+txtAgree_Date);  */
                                    
                                   System.out.println("amount");
                                   txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);
                                   txtParticular=Grid_particular[k];
                                   System.out.println("amount");
                                   System.out.println("Grid_sl_amt[k] "+Grid_sl_amt[k]);
                                  // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                                   System.out.println("Grid_particular[k] "+Grid_particular[k]);
                                   
                                   ps.setInt(1,cmbAcc_UnitCode);
                                   ps.setInt(2,cmbOffice_code);
                                   ps.setInt(3,txtCash_year);
                                   ps.setInt(4,txtCash_Month_hid);
                                   ps.setInt(5,txtJournalVou_No);
                                   ps.setInt(6,SL_NO);
                                   ps.setInt(7,txtAcc_HeadCode);
                                   ps.setString(8,rad_sub_CR_DR);
                                   ps.setInt(9,cmbSL_type);
                                   ps.setInt(10,cmbSL_Code);
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
                                   ps.setInt(21,txtPayVou_No);
                                  
                                   
                                   //from jsp page payment date
                                   String[] pay=request.getParameter("pay_date").split("/");
                                   year1="20".concat(pay[2]);
                                   c1=new GregorianCalendar(Integer.parseInt(year1),Integer.parseInt(pay[1])-1,Integer.parseInt(pay[0]));
                                   java.util.Date d1=c1.getTime();
                                   payment_dates=new Date(d1.getTime());
                                   System.out.println("payment_date "+payment_dates);
                                   
                                   ps.setDate(22,payment_dates);
                                   // ps.setDate(22,txtPayVou_date);
                                   ps.setString(23,update_user);
                                   ps.setTimestamp(24,ts);
                                   SL_NO++;
                                 int j1=  ps.executeUpdate(); 
                                     if(j1>0) {
                                         count++;
                                         System.out.println("inserted successfully row num :::: >>>> "+count);
                                     }
                                     
                               }
                               catch(Exception ssl)
                               {
                                        System.out.println("Exception while 2 nd table insert ::: "+ssl.getMessage());
                                
                                   con.rollback();
                                   sendMessage(response,"Journal Creation Failed ","ok");   
                               }
                                   

                           } //end of for loop
                         
                                   if(count==Grid_H_code.length)
                                   {
                                	   System.out.println("j1?****************************"+count);
                                     
                                     
                                      String GridHcode[]=request.getParameterValues("H_code");
                                      String Gridfinalpayment[]=request.getParameterValues("final_payment");
                                       String GridSLcode[]=request.getParameterValues("SL_code");
                                       
                                       
                                       
                                       for(int k=0;k<GridHcode.length;k++) 
                                       {
                                           if(Gridfinalpayment[k].equals("Y")) {
                                           finalCount++;
                                           }
                                       }
                                       try{sl_code_one=Integer.parseInt(GridSLcode[0]);}catch(Exception e){System.out.println("exception in GridSLcode "+e);}
                                     if(finalCount>0) 
                                     { 
                                     
                                         paymonth=Integer.parseInt(request.getParameter("pay_month"));
                                         System.out.println("paymonth::::"+paymonth);
                                         
                                         String[] pay=request.getParameter("pay_date").split("/");
                                         c1=new GregorianCalendar(Integer.parseInt(pay[2]),Integer.parseInt(pay[1])-1,Integer.parseInt(pay[0]));
                                         java.util.Date d1=c1.getTime();
                                         payment_dates=new Date(d1.getTime());
                                         System.out.println("payment_date "+payment_dates);
                                         
                                         if(pay[2].length()<=2)
                                        		 {
                                        	 		year1="20".concat(pay[2]);
                                        		 }
                                        System.out.println("chb_payment_year:::"+chb_payment_year);
                                        //payment date in fas_payment_voucher_no
                                         System.out.println("year1 :::"+year1);
                                       /*    ps3=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set AMOUNT_FULLY_SPENT='F',PAYABLE_VOUCHER_NO= " +txtJournalVou_No+",PAYABLE_VOUCHER_DATE=? " +
                                           "where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+chb_payment_year+" and CASHBOOK_MONTH="+paymonth+" and VOUCHER_NO="+txtPayVou_No+" AND SUB_LEDGER_CODE="+cmbSL_Code_update);
                                           ps3.setDate(1,txtCrea_date);
                                          int sSuccess= ps3.executeUpdate();  */
                                          if(finalCount>0){
                                    // if more than one voucher nos (5-9505,6-9505,36-9505) then updated their PAYABLE_VOUCHER_NO with newly created journal no in payment Transaction
                                    int prevpayyear=(chb_payment_year-1);
                                         String sql2="select kk.employee_id,\n" + 
                                         "   sum(kk.bal) as ttl,kk.voucher_no,kk.payment_date from (SELECT * FROM\n" + 
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
                                      //   "          AND m.accounting_for_office_id = "+cmbOffice_code+"\n" + 
                                      //   "          AND m.cashbook_year            = "+chb_payment_year+"\n" + 
                                      //   "          AND m.cashbook_month           <= "+chb_payment_month+" \n"+
                                         " AND To_Date((m.Cashbook_Month "+
                                         "         ||'-' "+
                                         "       || m.Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 "+
                                         "       ||'-' "+
                                         "       ||"+prevpayyear+",'mm-yyyy') "+
                                         "     AND to_date("+chb_payment_month+" "+
                                         "       ||'-' "+
                                         "       ||"+chb_payment_year+",'mm-yyyy') "+
                                         "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                                         "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                                         "          AND m.payment_status           ='L'   and (t.AMOUNT_FULLY_SPENT       !='F' or t.AMOUNT_FULLY_SPENT is null) \n" + 
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
                                       //  "          AND m.accounting_for_office_id = "+cmbOffice_code+"\n" + 
                                      //   "          AND m.cashbook_year            = "+chb_payment_year+"\n" + 
                                        // "          AND m.cashbook_month           <= "+chb_payment_month+" \n"+
                                         " AND To_Date((m.Cashbook_Month "+
                                         "         ||'-' "+
                                         "       || m.Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 "+
                                         "       ||'-' "+
                                         "       ||"+prevpayyear+",'mm-yyyy') "+
                                         "     AND to_date("+chb_payment_month+""+
                                         "       ||'-' "+
                                         "       ||"+chb_payment_year+",'mm-yyyy') "+
                                         "          AND (m.mode_of_creation        ='"+txtMode_of_creat+"'\n" + 
                                         "          OR m.mode_of_creation          ='"+textMode+"')\n" + 
                                         "          AND m.payment_status           ='L'   and (t.AMOUNT_FULLY_SPENT       !='F' or t.AMOUNT_FULLY_SPENT is null)\n" + 
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
                                         "WHERE bal    = 0 )kk \n" + 
                                         "where kk.employee_id="+sl_code_one+"\n" + 
                                         "group by kk.employee_id, kk.voucher_no, kk.payment_date \n"; 
                                         
                                         System.out.println("sql2 for updating payment trans:::(oly final payment is yes):::::"+sql2);
                                         ps2=con.prepareStatement(sql2);
                                         rs2=ps2.executeQuery();
                                             while(rs2.next())
                                             {
                                                     System.out.println("blance zerooooooooooooooooo");
                                                      int vno=rs2.getInt("voucher_no");
                                                     String pdate=rs2.getString("payment_date");
                                                     System.out.println("pdate::::::"+pdate);
                                                     int emplid=rs2.getInt("employee_id");
                                                     
                                                     ps4=con.prepareStatement("select CASHBOOK_YEAR,CASHBOOK_MONTH from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and PAYMENT_DATE=? and PAYMENT_STATUS='L' and VOUCHER_NO="+vno);
                                                     //ps4=con.prepareStatement("select CASHBOOK_YEAR,CASHBOOK_MONTH from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and PAYMENT_DATE=? and PAYMENT_STATUS='L' and VOUCHER_NO="+vno);
                                                     ps4.setString(1,pdate);
                                                     rs4=ps4.executeQuery();
                                                     if(rs4.next()) 
                                                     {
                                                     System.out.println("update in payment table");
                                                        int mont=rs4.getInt("CASHBOOK_MONTH");
                                                         int yr=rs4.getInt("CASHBOOK_YEAR");
                                                        
                                                         ps3=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set AMOUNT_FULLY_SPENT='F'," +
                                                         "PAYABLE_VOUCHER_NO= " +txtJournalVou_No+",PAYABLE_VOUCHER_DATE=? " +
                                                         " where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+yr+" and" +
                                                         " CASHBOOK_MONTH="+mont+" and VOUCHER_NO="+vno+" AND SUB_LEDGER_CODE="+emplid);
                                                         ps3.setDate(1,txtCrea_date);
                                                         ps3.executeUpdate();
                                                     }
                                                     
                                             } //end of while
                                         }
                                         else {
                                             try{con.rollback();}
                                             catch(SQLException sqle)
                                             {
                                             System.out.println("exception in rollback "+sqle);
                                             }
                                             System.out.println("error in Payable Vno:");
                                             sendMessage(response,"Failed to update Payable Voucher Nos","ok");
                                             
                                         }
                                     }
                                     else {
                                         // pradha
                                             String sql2= " SELECT a.AMT_SANCTIONED, "+
                                            "  b.AMT_SPENT,"+
                                            "  b.CB_REF_NO,"+
                                           "   a.AMT_SANCTIONED-(DECODE(b.AMT_SPENT,NULL,0,b.AMT_SPENT)) AS BALANCE "+
                                           " FROM  "+
                                           "   (SELECT SUM(AMOUNT)AS AMT_SANCTIONED, "+
                                           "     VOUCHER_NO  "+
                                          "    FROM FAS_PAYMENT_TRANSACTION  "+
                                             " WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
                                         //  "   AND CASHBOOK_YEAR           ="+chb_payment_year+"  "+
                                             "   AND CASHBOOK_YEAR           ="+year1+"  "+
                                          "    AND CASHBOOK_MONTH          ="+paymonth+"  "+
                                          "    AND SUB_LEDGER_CODE         ="+cmbSL_Code_update+"   and VOUCHER_NO="+txtPayVou_No+
                                           "   GROUP BY VOUCHER_NO  "+
                                         "     )a  "+
                                          "  LEFT OUTER JOIN  "+
                                          "      (SELECT  "+
                                           "   SUM(trn.AMOUNT)AS AMT_SPENT,"+
                                         "       trn.CB_REF_NO  "+
                                          "    FROM FAS_JOURNAL_TRANSACTION trn,fas_journal_master mas  "+
                                         "     WHERE  mas.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID  "+
                                         "     AND mas.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID  "+
                                         "     AND mas.CASHBOOK_YEAR           =trn.CASHBOOK_YEAR  "+
                                         "     AND mas.CASHBOOK_MONTH          =trn.CASHBOOK_MONTH  "+
                                         "      and mas.VOUCHER_NO=trn.VOUCHER_NO  "+
                                         "      and mas.JOURNAL_STATUS='L'  "+
                                         "      and trn.ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
                                        // "     AND trn.CASHBOOK_YEAR           ="+txtCash_year+" "+
                                        // "      AND trn.CASHBOOK_MONTH          ="+txtCash_Month_hid+"  "+
                                         "       AND trn.SUB_LEDGER_CODE         ="+cmbSL_Code_update+"  "+
                                         "     and trn.CR_DR_INDICATOR='CR'  "+
                                         "        GROUP BY trn.CB_REF_NO  "+
                                         "     )b  "+
                                         "   ON a.VOUCHER_NO=b.CB_REF_NO ";
                                                     
                                                
                                                System.out.println("sql2 for up::::"+sql2);
                                                ps2=con.prepareStatement(sql2);
                                                rs2=ps2.executeQuery();
                                             //   System.out.println("rs2"+rs2.next());
                                                while(rs2.next())
                                                {
                                                      //  System.out.println("inside while****************************");
                                                        int balanAmt=rs2.getInt("BALANCE");
                                                        int cbNo=rs2.getInt("CB_REF_NO");
                                                     //   System.out.println("cbNo::"+cbNo);
                                                      //  System.out.println("balanAmt"+balanAmt);
                                                        if(balanAmt==0)
                                                        {
                                                               System.out.println("blance zerooooooooooooooooo");
                                                               ps3=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set AMOUNT_FULLY_SPENT='F',PAYABLE_VOUCHER_NO= " +txtJournalVou_No+",PAYABLE_VOUCHER_DATE=? " +
                                                               "where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+year1+" and CASHBOOK_MONTH="+paymonth+" and VOUCHER_NO="+txtPayVou_No+" AND SUB_LEDGER_CODE="+cmbSL_Code_update);
                                                            ps3.setDate(1,txtCrea_date);
                                                               ps3.executeUpdate();
                                                               
                                                        }
                                                        else
                                                        {
                                                                System.out.println("balance not equal");
                                                               ps3=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set AMOUNT_FULLY_SPENT='N' where " +
                                                               " ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
                                                               " CASHBOOK_YEAR="+year1+" and CASHBOOK_MONTH="+paymonth+" and VOUCHER_NO="+txtPayVou_No+" " +
                                                               " AND SUB_LEDGER_CODE="+cmbSL_Code_update);
                                                               ps3.executeUpdate();
                                                        }
                                                }
                                         
                                     }
                                 
                                          
                                   }
                                   
                              else{
                                  try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                                System.out.println("error in update");
                                  sendMessage(response,"Failed to update Payment Transaction","ok");
                              }
                      
                           con.commit();
                           if(txtMode_of_creat.equals("I"))
                        	   		sendMessage(response,"The Imprest Voucher Number '"+txtJournalVou_No+"' has been Created Successfully ","ok");
                           else
                        	   		sendMessage(response,"The Temporary Advance Voucher Number '"+txtJournalVou_No+"' has been Created Successfully ","ok");
                     }
                    
             }
             
             catch(Exception e) 
             {
	                 try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                 if(txtMode_of_creat.equals("I"))
	                	   sendMessage(response,"The Imprest Voucher Number Creation Failed ","ok");
	                 else
	                	   sendMessage(response,"The Temporary Advance Voucher Number Creation Failed ","ok");
	                 System.out.println("Exception occur due to "+e);
             }
             finally
             {
                 System.out.println("done");
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

package Servlets.FAS.FAS1.TDA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PostJournal_Create_Supplement
 */
public class PostJournal_Create_Supplement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private String CONTENT_TYPE = "text/html; charset=windows-1252"; 
   
    public PostJournal_Create_Supplement() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	  	
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
	      String txtFrom_date=null,txtTo_date=null;
	      Date txtCrea_date=null;
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
                  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                  Class.forName(strDriver.trim());
                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
          }
          catch(Exception e)
          {
              	System.out.println("Exception in opening connection :"+e);
          }
      
	    
	    
          int count=0,cmbAcc_UnitCode=0,cmbOffice_code=0,cmbJournal_type=0,txtCB_Year=0,txtCB_Month=0;
          String xml=null,cmd="",type="";     
          String financial_year[]=null;
          int supp=0;
          /** Get Employee ID */
          try{cmd=request.getParameter("command");}
          catch(Exception e){System.out.println(e);}
          
          try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
          System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
          
          try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
          System.out.println("cmbOffice_code "+cmbOffice_code);
          
//          try{financial_year=request.getParameter("financial_year").split("-");}
//          catch(NumberFormatException e){System.out.println("exception"+e );}
//          System.out.println("financial_year "+financial_year);
          
          try{cmbJournal_type=Integer.parseInt(request.getParameter("cmbJournal_type"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
          System.out.println("cmbJournal_type "+cmbJournal_type);
          
          try{supp=Integer.parseInt(request.getParameter("supp"));}
          catch(NumberFormatException e){System.out.println("exception"+e );}
          System.out.println("supp "+supp);
          
          if(cmbJournal_type==62)
          	type="TDAO";
          else if(cmbJournal_type==65)
          	type="TCAO";
          else if(cmbJournal_type==63)
          	type="TDAA";
          else if(cmbJournal_type==66)
          	type="TCAA";
          txtFrom_date=request.getParameter("txtFrom_date");
          txtTo_date=request.getParameter("txtTo_date");
         
          System.out.println("cmd:::"+cmd);
          System.out.println("type :: "+type);
          xml="<response>";
          if(cmd.equalsIgnoreCase("loadVoucherByDate"))
          {     
               
                  xml=xml+"<command>loadVoucher</command>";
                  sql="select a.CASHBOOK_MONTH,a.CASHBOOK_YEAR,a.slno,to_char(a.originated_date,'dd-mm-yyyy')as originated_date,a.TRF_ACCOUNTING_UNIT_ID,b.ACCOUNTING_UNIT_NAME,a.REASON_FOR_TRANSFER,c.REASON_DESC,a.SUB_LEDGER_TYPE_CODE,d.SUB_LEDGER_TYPE_DESC,a.SUB_LEDGER_CODE,a.PAID_TO as sub_ledger_desc,trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as AMOUNT,a.PARTICULARS  from\n" + 
                  "(\n" + 
                  "  select\n" + 
                  "    ACCOUNTING_UNIT_ID,    \n" + 
                  "    ACCOUNTING_FOR_OFFICE_ID,	\n" + 
                  "    CASHBOOK_MONTH,    \n" + 
                  "    CASHBOOK_YEAR,    \n" + 
                  "    VOUCHER_NO as slno,\n" + 
                  "    VOUCHER_DATE as originated_date,\n" + 
                  "    ORGINATING_JVR_NO,\n" + 
                  "    ORGINATING_JVR_DATE,\n" + 
                  "    ACCEPTING_JVR_NO,\n" +
                  "    ACCEPTING_JVR_DATE,\n" +
                  "    TRF_ACCOUNTING_UNIT_ID,      \n" + 
                  "    REASON_FOR_TRANSFER,    \n" + 
                  "    SUB_LEDGER_TYPE_CODE,    \n" + 
                  "    SUB_LEDGER_CODE,\n" + 
                  "    PAID_TO,\n" + 
                  "    TOTAL_AMOUNT,\n" + 
                  "    PARTICULARS\n" + 
                  "  from\n" + 
                  "    FAS_TDA_TCA_RAISED_MST\n" + 
                  "  where\n" + 
                  "    ACCOUNTING_UNIT_ID=? and\n" + 
                  "    ACCOUNTING_FOR_OFFICE_ID=? and\n" + 
                  "  (ORGINATING_JVR_NO is null or ORGINATING_JVR_NO=0) and ORGINATING_JVR_DATE is null and"+
                  "    TDA_OR_TCA=? and\n" + 
                  "    STATUS='L' and    VOUCHER_DATE between ? and ? AND SUPPLEMENT_NO="+supp+ "\n " +
                  ")a left outer join\n" + 
                  "   FAS_MST_ACCT_UNITS b on a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID left outer join\n" + 
                  "   FAS_MST_TDA_TCA_REASON c on a.REASON_FOR_TRANSFER::numeric=c.REASON_CODE left outer join\n" + 
                  "   COM_MST_SL_TYPES d on a.SUB_LEDGER_TYPE_CODE=d.SUB_LEDGER_TYPE_CODE \n" + 
                  "where \n" + 
                  "   a.ACCEPTING_JVR_NO not in(select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID and VOUCHER_NO=a.ACCEPTING_JVR_NO and VOUCHER_DATE=a.ACCEPTING_JVR_DATE and JOURNAL_STATUS='L') \n" + 
                  "   order by a.slno";
                  System.out.println(" SQL :: "+sql);
                  try
                  {
                           ps2=con.prepareStatement(sql);
                           ps2.setInt(1,cmbAcc_UnitCode);
                           System.out.println(cmbAcc_UnitCode);
                           ps2.setInt(2,cmbOffice_code);
                           System.out.println(cmbOffice_code);
//                           ps2.setInt(3,Integer.parseInt(financial_year[0]));
//                           System.out.println(financial_year[0]);
//                           ps2.setInt(4,Integer.parseInt(financial_year[1]));
//                           System.out.println(financial_year[1]);
                           ps2.setString(3,type);
                           System.out.println(type);
                           ps2.setString(4,txtFrom_date);
                           System.out.println(txtFrom_date);
                           ps2.setString(5,txtTo_date);
                           System.out.println(txtTo_date);
                           rs2=ps2.executeQuery();                                 
                           while(rs2.next()) 
                           {
                                   xml+= "<slno>"+ rs2.getInt("slno") +"</slno>";
                                   xml+= "<CASHBOOK_MONTH>"+rs2.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>";
                                   xml+= "<CASHBOOK_YEAR>"+rs2.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>";
                                   xml+= "<originated_date>"+ rs2.getString("originated_date") +"</originated_date>";  
                                   xml+= "<TRANSFER_UNIT_NAME>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</TRANSFER_UNIT_NAME>"; 
                                   if(rs2.getString("REASON_DESC")==null)
                                  	   xml+= "<REASON_FOR_TRANSFER>-</REASON_FOR_TRANSFER>";	
                                   else
                                  	   xml+= "<REASON_FOR_TRANSFER>"+ rs2.getString("REASON_DESC") +"</REASON_FOR_TRANSFER>";
                                   if(rs2.getString("SUB_LEDGER_TYPE_DESC")==null)
                                	   	   xml+= "<SUB_LEDGER_TYPE>-</SUB_LEDGER_TYPE>";
                                   else
                                  	   xml+= "<SUB_LEDGER_TYPE>"+ rs2.getString("SUB_LEDGER_TYPE_DESC") +"</SUB_LEDGER_TYPE>";
                                   if(rs2.getString("sub_ledger_desc")==null)
                              	   	   xml+= "<SUB_LEDGER_CODE>-</SUB_LEDGER_CODE>";
                                   else
                                  	   xml+= "<SUB_LEDGER_CODE>"+ rs2.getString("sub_ledger_desc") +"</SUB_LEDGER_CODE>";  
                                   xml+= "<TOTAL_AMOUNT>"+ rs2.getString("AMOUNT") +"</TOTAL_AMOUNT>";                                      
                                   //xml+= "<PARTICULARS>"+ rs2.getString("PARTICULARS") +"</PARTICULARS>";                                      
                                    xml+= "<PARTICULARS><![CDATA["+rs2.getString("PARTICULARS")+"]]></PARTICULARS>"; 
                                    
                                   count++;
                           }					              
                           if(count==0)
                                   xml+="<flag>NoData</flag>";					           
                           else               
                                   xml+="<flag>success</flag>";
                                       
                  }
                  catch(Exception e) 
                  {
                           System.out.println("Exception in loadTransferUnit..."+e);
                           xml+="<flag>"+e.getMessage()+"</flag>";
                  }                      
          }	
          else if(cmd.equalsIgnoreCase("loadVoucherByDate_accepting"))
          {     
               
                  xml=xml+"<command>loadVoucherAccepting</command>";
              sql="SELECT \n" + 
              "  a.acceptedVouNo,\n" + 
              "  TO_CHAR(a.acceptedDate,'dd-mm-yyyy')AS acceptedVouDate,\n" + 
              "  a.acceptedUnitname,\n" + 
              "  a.CASHBOOK_MONTH,\n" + 
              "  a.CASHBOOK_YEAR,\n" + 
              "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
              "  b.ACCOUNTING_UNIT_NAME as ogrUnitName,\n" + 
              "  a.orgVoucherNo,\n" + 
              "  TO_CHAR(a.orgVoucherDate,'dd-mm-yyyy')AS orgDate,\n" + 
              "  a.REASON_FOR_TRANSFER,\n" + 
              "  c.REASON_DESC,\n" + 
              "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99')) AS AMOUNT\n" + 
              "\n" + 
              "FROM\n" + 
              "  (SELECT acc1.ACCOUNTING_UNIT_ID as acceptedUnitid,\n" + 
              "  (select uni.ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS uni where uni.ACCOUNTING_UNIT_ID=acc1.ACCOUNTING_UNIT_ID)as acceptedUnitname,\n" + 
              "    acc1.ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "    acc1.CASHBOOK_MONTH,\n" + 
              "    acc1.CASHBOOK_YEAR,\n" + 
              "    acc1.VOUCHER_NO   AS acceptedVouNo,\n" + 
              "    acc1.VOUCHER_DATE AS acceptedDate,\n" + 
              "    acc1.ORGINATING_JVR_NO,\n" + 
              "    acc1.ORGINATING_JVR_DATE,\n" + 
              "    acc1.ACCEPTING_JVR_NO,\n" + 
              "    acc1.ACCEPTING_JVR_DATE,\n" + 
              "    acc1.TRF_ACCOUNTING_UNIT_ID,\n" + 
              "    acc1.REASON_FOR_TRANSFER,\n" + 
              "    acc1.TOTAL_AMOUNT,\n" + 
              "    org1.VOUCHER_NO as orgVoucherNo,\n" + 
              "    org1.VOUCHER_DATE as orgVoucherDate,\n" + 
              "    org1.ACCOUNTING_UNIT_ID\n" + 
              "  FROM FAS_TDA_TCA_RAISED_MST acc1,FAS_TDA_TCA_RAISED_MST org1\n" + 
              "  WHERE  acc1.ACCOUNTING_UNIT_ID=org1.TRF_ACCOUNTING_UNIT_ID\n" + 
              "  and acc1.VOUCHER_NO=org1.ACCEPTING_SLNO\n" + 
              "  and acc1.ACCOUNTING_UNIT_ID    =? AND org1.acceptance_status='Y'" + 
              "  AND acc1.ACCOUNTING_FOR_OFFICE_ID=?\n" + 
              " AND org1.ORGINATING_JVR_NO      IS NOT NULL \n" + 
              " AND org1.ORGINATING_JVR_DATE    IS NOT NULL \n" + 
              " AND (acc1.ACCEPTING_JVR_NO      IS NULL OR acc1.ACCEPTING_JVR_NO=0) \n" + 
              " AND acc1.ACCEPTING_JVR_DATE    IS NULL \n" + 
              "  AND acc1.TDA_OR_TCA              =?\n" + 
              "  AND acc1.STATUS                  ='L'\n" + 
              "  AND acc1.VOUCHER_DATE between ? and ? AND acc1.SUPPLEMENT_NO="+supp+ "\n " +
              "  )a\n" + 
              "LEFT OUTER JOIN FAS_MST_ACCT_UNITS b\n" + 
              "ON a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
              "--and a.acceptedUnit=b.ACCOUNTING_UNIT_ID\n" + 
              "LEFT OUTER JOIN FAS_MST_TDA_TCA_REASON c\n" + 
              "ON a.REASON_FOR_TRANSFER::numeric=c.REASON_CODE\n" + 
              "WHERE a.ACCEPTING_JVR_NO NOT IN\n" + 
              "  (SELECT VOUCHER_NO\n" + 
              "  FROM FAS_JOURNAL_MASTER\n" + 
              "  WHERE ACCOUNTING_UNIT_ID            =a.ACCOUNTING_UNIT_ID\n" + 
              "  AND ACCOUNTING_FOR_OFFICE_ID        =a.ACCOUNTING_FOR_OFFICE_ID\n" + 
              "  AND VOUCHER_NO                      =a.ACCEPTING_JVR_NO\n" + 
              "  AND VOUCHER_DATE=a.ACCEPTING_JVR_DATE\n" + 
              "  AND JOURNAL_STATUS                  ='L'\n" + 
              "  )\n" + 
              "ORDER BY a.orgVoucherNo";
                  System.out.println(" SQL :: "+sql);
                  try
                  {
                           ps2=con.prepareStatement(sql);
                           ps2.setInt(1,cmbAcc_UnitCode);
                           System.out.println(cmbAcc_UnitCode);
                           ps2.setInt(2,cmbOffice_code);
                           System.out.println(cmbOffice_code);
                           ps2.setString(3,type);
                           System.out.println(type);
                           ps2.setString(4,txtFrom_date);
                           System.out.println(txtFrom_date);
                           ps2.setString(5,txtTo_date);
                           System.out.println(txtTo_date);
                           rs2=ps2.executeQuery();                                 
                           while(rs2.next()) 
                           {
                               xml+= "<acceptedVouNo>"+ rs2.getInt("acceptedVouNo") +"</acceptedVouNo>";
                                xml+= "<acceptedVouDate>"+ rs2.getString("acceptedVouDate") +"</acceptedVouDate>";  
                                xml+= "<acceptedUnitname>"+ rs2.getString("acceptedUnitname") +"</acceptedUnitname>";  
                                xml+= "<CASHBOOK_MONTH>"+rs2.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>";
                               xml+= "<CASHBOOK_YEAR>"+rs2.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>";
                                xml+= "<orgVoucherNo>"+rs2.getInt("orgVoucherNo")+"</orgVoucherNo>";
                                xml+= "<orgDate>"+ rs2.getString("orgDate") +"</orgDate>";  
                                xml+= "<ogrUnitName>"+ rs2.getString("ogrUnitName") +"</ogrUnitName>"; 
                                if(rs2.getString("REASON_DESC")==null)
                                      xml+= "<REASON_FOR_TRANSFER>-</REASON_FOR_TRANSFER>";        
                                else
                                      xml+= "<REASON_FOR_TRANSFER>"+ rs2.getString("REASON_DESC") +"</REASON_FOR_TRANSFER>";
                                xml+= "<TOTAL_AMOUNT>"+ rs2.getString("AMOUNT") +"</TOTAL_AMOUNT>";                                      
                                count++;
                           }					              
                           if(count==0)
                                   xml+="<flag>NoData</flag>";					           
                           else               
                                   xml+="<flag>success</flag>";
                                       
                  }
                  catch(Exception e) 
                  {
                           System.out.println("Exception in loadTransferUnit..."+e);
                           xml+="<flag>"+e.getMessage()+"</flag>";
                  }                      
          }	
          
          else if(cmd.equalsIgnoreCase("loadVoucherByMonth_accepting"))
          { 
              txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
              txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
              String[] sd=request.getParameter("txtCrea_date").split("/");
              Calendar c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
              java.util.Date d=c.getTime();
              txtCrea_date=new Date(d.getTime());
              System.out.println("txtCrea_date "+txtCrea_date);
              xml=xml+"<command>loadVoucherAccepting</command>";
              sql="SELECT \n" + 
              "  a.acceptedVouNo,\n" + 
              "  TO_CHAR(a.acceptedDate,'dd-mm-yyyy')AS acceptedVouDate,\n" + 
              "  a.acceptedUnitname,\n" + 
              "  a.CASHBOOK_MONTH,\n" + 
              "  a.CASHBOOK_YEAR,\n" + 
              "  a.TRF_ACCOUNTING_UNIT_ID,\n" + 
              "  b.ACCOUNTING_UNIT_NAME as ogrUnitName,\n" + 
              "  a.orgVoucherNo,\n" + 
              "  TO_CHAR(a.orgVoucherDate,'dd-mm-yyyy')AS orgDate,\n" + 
              "  a.REASON_FOR_TRANSFER,\n" + 
              "  c.REASON_DESC,\n" + 
              "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99')) AS AMOUNT\n" + 
              "\n" + 
              "FROM\n" + 
              "  (SELECT acc1.ACCOUNTING_UNIT_ID as acceptedUnitid,\n" + 
              "  (select uni.ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS uni where uni.ACCOUNTING_UNIT_ID=acc1.ACCOUNTING_UNIT_ID)as acceptedUnitname,\n" + 
              "    acc1.ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "    acc1.CASHBOOK_MONTH,\n" + 
              "    acc1.CASHBOOK_YEAR,\n" + 
              "    acc1.VOUCHER_NO   AS acceptedVouNo,\n" + 
              "    acc1.VOUCHER_DATE AS acceptedDate,\n" + 
              "    acc1.ORGINATING_JVR_NO,\n" + 
              "    acc1.ORGINATING_JVR_DATE,\n" + 
              "    acc1.ACCEPTING_JVR_NO,\n" + 
              "    acc1.ACCEPTING_JVR_DATE,\n" + 
              "    acc1.TRF_ACCOUNTING_UNIT_ID,\n" + 
              "    acc1.REASON_FOR_TRANSFER,\n" + 
              "    acc1.TOTAL_AMOUNT,\n" + 
              "    org1.VOUCHER_NO as orgVoucherNo,\n" + 
              "    org1.VOUCHER_DATE as orgVoucherDate,\n" + 
              "    org1.ACCOUNTING_UNIT_ID\n" + 
              "  FROM FAS_TDA_TCA_RAISED_MST acc1,FAS_TDA_TCA_RAISED_MST org1\n" + 
              "  WHERE  acc1.ACCOUNTING_UNIT_ID=org1.TRF_ACCOUNTING_UNIT_ID\n" + 
              "  and acc1.VOUCHER_NO=org1.ACCEPTING_SLNO\n" + 
              "  and acc1.ACCOUNTING_UNIT_ID    =? " + 
              "  AND acc1.ACCOUNTING_FOR_OFFICE_ID=?\n" + 
              "  AND (acc1.ACCEPTING_JVR_NO      IS NULL or acc1.ACCEPTING_JVR_NO=0) \n" + 
              "  AND acc1.ACCEPTING_JVR_DATE    IS NULL\n" + 
              "  AND acc1.TDA_OR_TCA              =?  and acc1.VOUCHER_DATE<=?\n" + 
              "  AND acc1.STATUS                  ='L'\n" + 
              "  AND acc1.CASHBOOK_YEAR           ="+txtCB_Year+"\n" + 
              "  AND acc1.CASHBOOK_MONTH          ="+txtCB_Month+"\n" + 
              "  AND acc1.SUPPLEMENT_NO="+supp+ "\n " +
              "  )a\n" + 
              "LEFT OUTER JOIN FAS_MST_ACCT_UNITS b\n" + 
              "ON a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
              "--and a.acceptedUnit=b.ACCOUNTING_UNIT_ID\n" + 
              "LEFT OUTER JOIN FAS_MST_TDA_TCA_REASON c\n" + 
              "ON a.REASON_FOR_TRANSFER::numeric=c.REASON_CODE\n" + 
              "WHERE a.ACCEPTING_JVR_NO NOT IN\n" + 
              "  (SELECT VOUCHER_NO\n" + 
              "  FROM FAS_JOURNAL_MASTER\n" + 
              "  WHERE ACCOUNTING_UNIT_ID            =a.ACCOUNTING_UNIT_ID\n" + 
              "  AND ACCOUNTING_FOR_OFFICE_ID        =a.ACCOUNTING_FOR_OFFICE_ID\n" + 
              "  AND VOUCHER_NO                      =a.ACCEPTING_JVR_NO\n" + 
              "  AND VOUCHER_DATE=a.ACCEPTING_JVR_DATE\n" + 
              "  AND JOURNAL_STATUS                  ='L'\n" + 
              "  )\n" + 
              "ORDER BY a.orgVoucherNo";
              System.out.println(" SQL ::accepting ::"+sql);
              try
              {
                       ps2=con.prepareStatement(sql);
                       ps2.setInt(1,cmbAcc_UnitCode);
                       ps2.setInt(2,cmbOffice_code);
                       ps2.setString(3,type);
                       ps2.setDate(4,txtCrea_date);
                       rs2=ps2.executeQuery();                                 
                       while(rs2.next()) 
                       {
                              xml+= "<acceptedVouNo>"+ rs2.getInt("acceptedVouNo") +"</acceptedVouNo>";
                               xml+= "<acceptedVouDate>"+ rs2.getString("acceptedVouDate") +"</acceptedVouDate>";  
                               xml+= "<acceptedUnitname>"+ rs2.getString("acceptedUnitname") +"</acceptedUnitname>";  
                               xml+= "<CASHBOOK_MONTH>"+rs2.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>";
                              xml+= "<CASHBOOK_YEAR>"+rs2.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>";
                               xml+= "<orgVoucherNo>"+rs2.getInt("orgVoucherNo")+"</orgVoucherNo>";
                               xml+= "<orgDate>"+ rs2.getString("orgDate") +"</orgDate>";  
                               xml+= "<ogrUnitName>"+ rs2.getString("ogrUnitName") +"</ogrUnitName>"; 
                               if(rs2.getString("REASON_DESC")==null)
                                     xml+= "<REASON_FOR_TRANSFER>-</REASON_FOR_TRANSFER>";        
                               else
                                     xml+= "<REASON_FOR_TRANSFER>"+ rs2.getString("REASON_DESC") +"</REASON_FOR_TRANSFER>";
                               xml+= "<TOTAL_AMOUNT>"+ rs2.getString("AMOUNT") +"</TOTAL_AMOUNT>";                                      
                               count++;
                       }                                                
                       if(count==0)
                               xml+="<flag>NoData</flag>";                                                   
                       else               
                               xml+="<flag>success</flag>";
                                   
              }
              catch(Exception e) 
              {
                       System.out.println("Exception in loadTransferUnit..."+e);
                       xml+="<flag>"+e.getMessage()+"</flag>";
              }  
          
          }
      else if(cmd.equalsIgnoreCase("loadVoucherByMonth"))
                  { 
                      txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
                      txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
                     
                      String[] sd=request.getParameter("txtCrea_date").split("/");
                      Calendar c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                      java.util.Date d=c.getTime();
                      txtCrea_date=new Date(d.getTime());
                      System.out.println("txtCrea_date "+txtCrea_date);
                      xml=xml+"<command>loadVoucher</command>";
                      sql="select a.CASHBOOK_MONTH,a.CASHBOOK_YEAR,a.slno,to_char(a.originated_date,'dd-mm-yyyy')as originated_date,a.TRF_ACCOUNTING_UNIT_ID,b.ACCOUNTING_UNIT_NAME,a.REASON_FOR_TRANSFER,c.REASON_DESC,a.SUB_LEDGER_TYPE_CODE,d.SUB_LEDGER_TYPE_DESC,a.SUB_LEDGER_CODE,a.PAID_TO as sub_ledger_desc,trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as AMOUNT,a.PARTICULARS  from\n" + 
                      "(\n" + 
                      "  select\n" + 
                      "    ACCOUNTING_UNIT_ID,    \n" + 
                      "    ACCOUNTING_FOR_OFFICE_ID,      \n" + 
                      "    CASHBOOK_MONTH,    \n" + 
                      "    CASHBOOK_YEAR,    \n" + 
                      "    VOUCHER_NO as slno,\n" + 
                      "    VOUCHER_DATE as originated_date,\n" + 
                      "    ORGINATING_JVR_NO,\n" + 
                      "    ORGINATING_JVR_DATE,\n" + 
                      "    ACCEPTING_JVR_NO,\n" +
                      "    ACCEPTING_JVR_DATE,\n" +
                      "    TRF_ACCOUNTING_UNIT_ID,      \n" + 
                      "    REASON_FOR_TRANSFER,    \n" + 
                      "    SUB_LEDGER_TYPE_CODE,    \n" + 
                      "    SUB_LEDGER_CODE,\n" + 
                      "    PAID_TO,\n" + 
                      "    TOTAL_AMOUNT,\n" + 
                      "    PARTICULARS\n" + 
                      "  from\n" + 
                      "    FAS_TDA_TCA_RAISED_MST\n" + 
                      "  where\n" + 
                      "    ACCOUNTING_UNIT_ID=? and\n" + 
                      "    ACCOUNTING_FOR_OFFICE_ID=? and\n" + 
                      "  (ORGINATING_JVR_NO is null or ORGINATING_JVR_NO=0) and ORGINATING_JVR_DATE is null "+
                 //     "    to_date(CASHBOOK_MONTH ||'-'|| + CASHBOOK_YEAR, 'mm-yyyy') BETWEEN to_date( 4 || '-' || ? ,   'mm-yyyy')  AND to_date( 3 ||'-' || ?, 'mm-yyyy') and\n" + 
                      "   and TDA_OR_TCA=? and\n" + 
                      "    STATUS='L' and   VOUCHER_DATE<=? and " +
                      "    CASHBOOK_YEAR="+txtCB_Year+" AND CASHBOOK_MONTH="+txtCB_Month+" AND SUPPLEMENT_NO="+supp+")a left outer join\n" + 
                      "   FAS_MST_ACCT_UNITS b on a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID left outer join\n" + 
                      "   FAS_MST_TDA_TCA_REASON c on a.REASON_FOR_TRANSFER::numeric=c.REASON_CODE left outer join\n" + 
                      "   COM_MST_SL_TYPES d on a.SUB_LEDGER_TYPE_CODE=d.SUB_LEDGER_TYPE_CODE \n" + 
                      "where \n" + 
                      "   a.ACCEPTING_JVR_NO not in(select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID and VOUCHER_NO=a.ACCEPTING_JVR_NO and VOUCHER_DATE=a.ACCEPTING_JVR_DATE and JOURNAL_STATUS='L') \n" + 
                      "   order by a.slno";
                      System.out.println(" SQL :: "+sql);
                      try
                      {
                               ps2=con.prepareStatement(sql);
                               ps2.setInt(1,cmbAcc_UnitCode);
                               ps2.setInt(2,cmbOffice_code);
                               ps2.setString(3,type);
                               ps2.setDate(4,txtCrea_date);
                               
                               rs2=ps2.executeQuery();                                 
                               while(rs2.next()) 
                               {
                                       xml+= "<slno>"+ rs2.getInt("slno") +"</slno>";
                                       xml+= "<CASHBOOK_MONTH>"+rs2.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>";
                                       xml+= "<CASHBOOK_YEAR>"+rs2.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>";
                                       xml+= "<originated_date>"+ rs2.getString("originated_date") +"</originated_date>";  
                                       xml+= "<TRANSFER_UNIT_NAME>"+ rs2.getString("ACCOUNTING_UNIT_NAME") +"</TRANSFER_UNIT_NAME>"; 
                                       if(rs2.getString("REASON_DESC")==null)
                                             xml+= "<REASON_FOR_TRANSFER>-</REASON_FOR_TRANSFER>";        
                                       else
                                             xml+= "<REASON_FOR_TRANSFER>"+ rs2.getString("REASON_DESC") +"</REASON_FOR_TRANSFER>";
                                       if(rs2.getString("SUB_LEDGER_TYPE_DESC")==null)
                                                     xml+= "<SUB_LEDGER_TYPE>-</SUB_LEDGER_TYPE>";
                                       else
                                             xml+= "<SUB_LEDGER_TYPE>"+ rs2.getString("SUB_LEDGER_TYPE_DESC") +"</SUB_LEDGER_TYPE>";
                                       if(rs2.getString("sub_ledger_desc")==null)
                                                     xml+= "<SUB_LEDGER_CODE>-</SUB_LEDGER_CODE>";
                                       else
                                             xml+= "<SUB_LEDGER_CODE>"+ rs2.getString("sub_ledger_desc") +"</SUB_LEDGER_CODE>";  
                                       xml+= "<TOTAL_AMOUNT>"+ rs2.getString("AMOUNT") +"</TOTAL_AMOUNT>";                                      
                                       xml+= "<PARTICULARS><![CDATA["+rs2.getString("PARTICULARS")+"]]></PARTICULARS>";                                      
                                       count++;
                               }                                                
                               if(count==0)
                                       xml+="<flag>NoData</flag>";                                                   
                               else               
                                       xml+="<flag>success</flag>";
                                           
                      }
                      catch(Exception e) 
                      {
                               System.out.println("Exception in loadTransferUnit..."+e);
                               xml+="<flag>"+e.getMessage()+"</flag>";
                      }  
                  
                  }
          
          xml=xml+"</response>";
          System.out.println(xml);
          out.println(xml);
          out.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
        String strCommand="";
        Connection con=null;        
        PreparedStatement ps=null,ps1=null,ps2=null;     
        Statement st=null;
        ResultSet rs=null,rs2=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
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
      
       if(strCommand.equalsIgnoreCase("Add")) 
       {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
           
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            Calendar c;
            int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,cmbJournal_type=0;            
            Date txtCrea_date=null,voucher_date=null;
            String journal_type_desc="",cr_dr_indicator="",sql="",flag="",particulars="",voucher_display="";
            String financial_year[]=null;
            int account_head_code=0,sub_ledger_code=0,trn_records=0,trn_count=0,grid_count=0,depriciation_rate=0,cashbook_year=0,cashbook_month=0;
            int supplement_no=0;
            String paid_to=""; 
    		 int sub_ledger_type=0;
    		 double amount=0;
          
                                    // changes here
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                               
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            String[] Voucher_no=request.getParameterValues("slno");
            System.out.println("Voucher_no::"+Voucher_no);
            
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
            
            try{cmbJournal_type=Integer.parseInt(request.getParameter("cmbJournal_type"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("cmbJournal_type "+cmbJournal_type);
            
            try {
                supplement_no =Integer.parseInt(request.getParameter("supNo"));
                System.out.println("supplement_no>>>>>>"+supplement_no);
            } catch (Exception e) {
                System.out.println("exception in supplement_no" + e);
            }
            
//            try{financial_year=request.getParameter("financial_year").split("-");}
//            catch(NumberFormatException e){System.out.println("exception"+e );}
//            System.out.println("financial_year "+financial_year);
            
            if(cmbJournal_type==62)
            {
           	 	  journal_type_desc="TDAO";
           	 	  cr_dr_indicator="DR";
           	 	  account_head_code=900108;
            }
            else if(cmbJournal_type==65)
            {
           	 	  journal_type_desc="TCAO";
           	 	  cr_dr_indicator="CR";account_head_code=901001;            	 
            }
            else if(cmbJournal_type==63)
            {
           	 	  journal_type_desc="TDAA";
           	 	  cr_dr_indicator="CR";account_head_code=900109;
            }
            else if(cmbJournal_type==66)
            {
      	 	  		  journal_type_desc="TCAA";
      	 	  		  cr_dr_indicator="DR";account_head_code=900102;
            }
            int Originated_SL_No=0;
            try
            {
                     ps=con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?)");
                     ps.setInt(1,cmbAcc_UnitCode);
                     ps.setInt(2,cmbOffice_code);
                     ps.setInt(3,txtCash_year);
                     ps.setInt(4,txtCash_Month_hid);                      
           	 	  rs=ps.executeQuery();
                     if(rs.next()) 
                     {
                               Originated_SL_No = rs.getInt(1);                                               
                     }
                     Originated_SL_No=Originated_SL_No+1;
                     rs.close();
            }           
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("Originated_SL_No "+Originated_SL_No);
                        
            try 
            {
           	 	  ps.close();
           	 	  con.setAutoCommit(false);
                     for(int i=0;i<Voucher_no.length;i++)
                     {
                   	  		System.out.println("=============Selected Voucher "+Voucher_no[i]+" ==========================");
                   	  		voucher_date=null;
                   	  		sub_ledger_code=0;
                   	  		particulars="";
                   	  		trn_records=0;
	                            sql="select \n" + 
	                            "    distinct\n" +
	                            "    mst.CASHBOOK_YEAR," +
	                            "    mst.CASHBOOK_MONTH," + 
	                          	"    mst.VOUCHER_DATE, " + 
	                          	"    mst.SUB_LEDGER_CODE,\n" + 
	                          	"    mst.PARTICULARS,\n" + 
	                          	"    count(trn.VOUCHER_NO) as trn_rec\n" + 
	                          	"from \n" + 
	                          	"    FAS_TDA_TCA_RAISED_MST mst,\n" + 
	                          	"    FAS_TDA_TCA_RAISED_TRN trn\n" + 
	                          	"where \n" + 
	                          	"    mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and\n" + 
	                          	"    mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and\n" + 
	                          	"    mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and \n" + 
	                          	"    mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
	                          	"    mst.VOUCHER_NO=trn.VOUCHER_NO and\n" + 
	                          	"    mst.ACCOUNTING_UNIT_ID=? and\n" + 
	                          	"    mst.ACCOUNTING_FOR_OFFICE_ID=? and\n" + 
	                          	"    mst.VOUCHER_NO=? and \n " +
	                          	" mst.CASHBOOK_YEAR=? AND  "+
	                            " mst.CASHBOOK_MONTH=? and  "+
	                          //	"	 to_date((mst.cashbook_month||'-'||mst.cashbook_year),'mm-yyyy') between to_date((4||'-'||?),'mm-yyyy') and to_date((3||'-'||?),'mm-yyyy') and " +
	                          	"    TDA_OR_TCA=? " + 
	                          	"group by\n" +
	                          	"    mst.CASHBOOK_YEAR," +
	                          	"    mst.CASHBOOK_MONTH," + 
	                          	"    mst.VOUCHER_DATE,\n" + 
	                          	"    mst.SUB_LEDGER_CODE,\n" + 
	                          	"    mst.PARTICULARS ";
                   	  		ps=con.prepareStatement(sql);
                   	  		ps.setInt(1,cmbAcc_UnitCode);
                   	  		ps.setInt(2,cmbOffice_code);
                   	  		ps.setInt(3,Integer.parseInt(Voucher_no[i]));
                   	  		ps.setInt(4,txtCash_year);
                               ps.setInt(5,txtCash_Month_hid);
                               ps.setString(6,journal_type_desc);
                               rs=ps.executeQuery();
                               if(rs.next())
                               {
                               		System.out.println("Inside first select");
                               		cashbook_month=rs.getInt("CASHBOOK_MONTH");
                               		cashbook_year=rs.getInt("CASHBOOK_YEAR");
                               		voucher_date=rs.getDate("VOUCHER_DATE");
                               		System.out.println(" voucher_date :: "+voucher_date);
                               		sub_ledger_code=rs.getInt("SUB_LEDGER_CODE");
                               		particulars=rs.getString("PARTICULARS");
                               		trn_records=rs.getInt("trn_rec");
                               		System.out.println("trn_records :: "+trn_records);
                               		ps1=con.prepareStatement("insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_TRN_RECORDS,REMARKS,MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,DEPRECIATION_RATE,SUPPLEMENT_NO)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                               		ps1.setInt(1,cmbAcc_UnitCode);
                               		ps1.setInt(2,cmbOffice_code);
                               		ps1.setDate(3,txtCrea_date);
                               		ps1.setInt(4,txtCash_year);
                                       ps1.setInt(5,txtCash_Month_hid);  
                                       ps1.setInt(6,Originated_SL_No);
                                       ps1.setInt(7,cmbJournal_type);
                                       ps1.setInt(8,sub_ledger_code);
                                       ps1.setInt(9,trn_records);
                                       ps1.setString(10,particulars);
                                       ps1.setString(11,"A");
                                       ps1.setString(12,"SJV");
                                       ps1.setString(13,"L");
                                       ps1.setString(14,update_user);
                                       ps1.setTimestamp(15,ts);
                                       ps1.setInt(16,depriciation_rate);
                                       ps1.setInt(17,supplement_no);
                                       int kk=ps1.executeUpdate();
                                       
                                       if(kk>0)
                                       {
                                       		flag="success";
                                       		System.out.println("Flag ::: "+flag);
                                       }
                                       else
                                       		flag="failure";
                               }                                
                               System.out.println("Flag ------>"+flag);
                               
                               if(flag.equals("success"))
                               {
                               		trn_count=0;
                               		int count=0;
                               		ps.close();
                               		sql="SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,PAID_TO,AMOUNT,PARTICULARS FROM FAS_TDA_TCA_RAISED_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? order by SL_NO";
                               		ps2=con.prepareStatement(sql);
                               		ps2.setInt(1,cmbAcc_UnitCode);
                           	  		ps2.setInt(2,cmbOffice_code);
                           	  		ps2.setInt(3,Integer.parseInt(Voucher_no[i]));
                           	  		ps2.setInt(4,txtCash_year);
                                       ps2.setInt(5,txtCash_Month_hid);
                                       rs2=ps2.executeQuery();
                                       while(rs2.next())
                                       {
                                       		System.out.println("inside second while");
                                       		count++;
                                       		account_head_code=rs2.getInt("ACCOUNT_HEAD_CODE");
                                       		cr_dr_indicator=rs2.getString("CR_DR_INDICATOR");
                                       		sub_ledger_type=rs2.getInt("SUB_LEDGER_TYPE_CODE");
                                       		sub_ledger_code=rs2.getInt("SUB_LEDGER_CODE");
                                       		paid_to=rs2.getString("PAID_TO");
                                       		amount=rs2.getDouble("AMOUNT");
                                       		particulars=rs2.getString("PARTICULARS");
//commanded on 30-05-2018             		ps=con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                       		ps=con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_TDCA_REF_NO,CB_TDCA_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                       		ps.setInt(1,cmbAcc_UnitCode);
                                       		ps.setInt(2,cmbOffice_code);
                                       		ps.setInt(3,txtCash_year);
                                               ps.setInt(4,txtCash_Month_hid);
                                               ps.setInt(5,Originated_SL_No);
                                               ps.setInt(6,count);
                                               ps.setInt(7,account_head_code);
                                               ps.setString(8,cr_dr_indicator);
                                               ps.setInt(9,sub_ledger_type);
                                               ps.setInt(10,sub_ledger_code);
                                               ps.setDouble(11,amount);
                                               ps.setString(12,particulars);
                                               ps.setInt(13,Integer.parseInt(Voucher_no[i]));
                                               ps.setDate(14,voucher_date);
                                               ps.setString(15,update_user);
                                               ps.setTimestamp(16,ts);
                                               ps.setInt(17,0);
                                              int kk=ps.executeUpdate();
                                               
                                               if(kk>0)
                                               	trn_count++;                                                
                                       }
                                       if(trn_records==trn_count)
                                       {
                                       		ps.close();
                                       		System.out.println("inside update");
                                       	  if(cmbJournal_type==62 || cmbJournal_type==65){
                                       		//if(journal_type_desc.equals("TDAO")||journal_type_desc.equals("TCAO")){
                                       			System.out.println("111111");
                                       			ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ORGINATING_JVR_NO=?,ORGINATING_JVR_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?  and (ORGINATING_JVR_NO is null or ORGINATING_JVR_NO=0) and ORGINATING_JVR_DATE is null");
                                       		}
                                       	
                                       	  else if(cmbJournal_type==63 || cmbJournal_type==66){
                                       	//	else if(journal_type_desc.equals("TDAA")||journal_type_desc.equals("TCAA")){
                                       			System.out.println("22222222");
                                       			ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set ACCEPTING_JVR_NO=?,ACCEPTING_JVR_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? AND (ACCEPTING_JVR_NO      IS NULL or ACCEPTING_JVR_NO=0) and ACCEPTING_JVR_DATE    IS NULL");
                                       		}
                                       		ps.setInt(1,Originated_SL_No);System.out.println("Originated_SL_No"+Originated_SL_No);
                                       		ps.setDate(2,txtCrea_date);System.out.println("txtCrea_date"+txtCrea_date);
                                       		ps.setInt(3,cmbAcc_UnitCode);
                                       		ps.setInt(4,cmbOffice_code);
                                       		ps.setInt(5,cashbook_year);System.out.println("cashbook_year"+cashbook_year);
                                       		ps.setInt(6,cashbook_month);System.out.println("cashbook_month"+cashbook_month);
                                       		ps.setInt(7,Integer.parseInt(Voucher_no[i]));System.out.println("vouchno"+Integer.parseInt(Voucher_no[i]));
                                       		int kk=ps.executeUpdate();
                                       		
                                       		if(kk>0)
                                       		{
	                                        		grid_count++;
	                                        		voucher_display=voucher_display+","+Originated_SL_No;
                                       		}
                                       }
                               }
                               Originated_SL_No++;
                               System.out.println("===================================================================================");
                     }
                     voucher_display=voucher_display.substring(1,voucher_display.length());
                     System.out.println("voucher_display :: "+voucher_display);
                     System.out.println("grid_count:::"+grid_count+"  Voucher_no length :::  "+Voucher_no.length);
                     if(grid_count==Voucher_no.length)
                     {
		                    	System.out.println("b4 commit");
		                        con.commit();
		                        sendMessage(response,"The Post Journal Voucher Number "+voucher_display+" has been Created Successfully ","ok");		                        
                     }
                     else
                     {
                               System.out.println("b4 Rollback");
                               con.rollback();
                               sendMessage(response,"The Post Journal Voucher Creation Failed ","ok");        
                     }
                    
            }
             
            catch(Exception e) 
            {
	                  try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                  e.getStackTrace();	                                 	  	
	                  System.out.println("Exception occur due to "+e);
	                  sendMessage(response,"The Post Journal Voucher Creation Failed ","ok");
                 
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

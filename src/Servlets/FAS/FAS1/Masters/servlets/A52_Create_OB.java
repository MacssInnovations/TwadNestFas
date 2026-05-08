package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.sql.Date;

import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class A52_Create_OB
 */
public class A52_Create_OB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public A52_Create_OB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        ResultSet result1=null,rs1=null,rss1=null,rss2=null;
        PreparedStatement pers=null;
   	    PreparedStatement ps=null,ps1=null;
       // int up=0;
        try
        {
    	   ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
           String ConnectionString="";

           String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
           String strdsn=rs.getString("Config.DSN");
           String strhostname=rs.getString("Config.HOST_NAME");
           String strportno=rs.getString("Config.PORT_NUMBER");
           String strsid=rs.getString("Config.SID");
           String strdbusername=rs.getString("Config.USER_NAME");
           String strdbpassword=rs.getString("Config.PASSWORD");
              
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

           Class.forName(strDriver.trim());
           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           try
           {
        	   statement=connection.createStatement();
               connection.clearWarnings();
           }
           catch(SQLException e)
           {
               System.out.println("Exception in creating statement:"+e);
           }
        }
        catch(Exception e)
        {
           System.out.println("Exception in openeing connection:"+e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
        int unit_id = 0;
        int office_id = 0;
        int assetmajor=0;
        int assetminor=0;
	   	String financial_year = null;
	   	int asset_code = 0; 
	   	String PARTICULARS = "";
	   	
	   	
        HttpSession session=request.getSession(false);
        try
        {
	        if(session==null)
	        {
	            System.out.println(request.getContextPath()+"/index.jsp");
	            response.sendRedirect(request.getContextPath()+"/index.jsp");
	        }
	        System.out.println(session);
        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
        
        
        String userid=(String)session.getAttribute("UserId");
       // System.out.println("Session id is    a52 push...:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	//System.out.println("strCommand : a52 value edit " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        try
        {
        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
        	//System.out.println("accounting_unit_id : " + unit_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
        }        
        
        try
        {
        	office_id = Integer.parseInt(request.getParameter("office_id"));
        	//System.out.println("accounting_unit_office_id : " + office_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
        }            
        try
        {
        	financial_year = request.getParameter("financial_year");
        	//System.out.println("financial_year : " + financial_year);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
        }     
        
    
        try
        {
        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        	
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'assetmajor' parameter ===> " + e);
        }     
      
     
        if(strCommand.equals("GoGet"))
        { 
        	int count=0;
        	System.out.println("\n*************\nGo Get\n**************\n");
            xml="<response><command>GoGet</command>";
            try 
            {
            	 String[] divyear=financial_year.split("-");
             	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
             System.out.println("goget");
             
             String qry="select (decode(open_bal_qty,null,0,open_bal_qty) +decode(reciepts_year_qty,null,0,reciepts_year_qty)-decode(issues_year_qty,null,0,issues_year_qty)) as aval_qty,"+
   "(decode(opening_bal_value,null,0,opening_bal_value)+decode(reciepts_yr_value,null,0,reciepts_yr_value)-decode(issues_yr_value,null,0,issues_yr_value)) as aval_value,"+
   "(decode(N_OPEN_BAL_QTY,null,0,N_OPEN_BAL_QTY) +decode(N_RECIEPTS_YEAR_QTY,null,0,N_RECIEPTS_YEAR_QTY)-decode(N_ISSUES_YEAR_QTY,null,0,N_ISSUES_YEAR_QTY)) as crt_aval_qty,"+
   "(decode(N_OPENING_BAL_VALUE,null,0,N_OPENING_BAL_VALUE)+decode(N_RECIEPTS_YR_VALUE,null,0,N_RECIEPTS_YR_VALUE)-decode(N_ISSUES_YR_VALUE,null,0,N_ISSUES_YR_VALUE)) as crt_aval_value,"+
            		" PARTICULARS,REMARKS,accounting_unit_id,"+
            		 " accounting_unit_office_id,asset_code,ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE,"+
            		  " (SELECT m.ASSET_MINOR_CLASS_DESC "+
            					 " FROM FAS_ASSET_MINOR_CLASSIFICATION m "+
            					 " WHERE m.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND m.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE "+
            					" ) "+
            					" AS ASSET_MINOR_CLASS_DESC, "+
            					"(select OFFICE_NAME from com_mst_offices where OFFICE_ID=a.accounting_unit_office_id)as offName  "+
              "  from FAS_A52_REGISTER a " +
             		
             " WHERE " +
             "a.accounting_unit_id = "+ unit_id +
	    // " AND " +
	    // "a.accounting_unit_office_id = " + office_id +
	     " AND a.financial_year = '"+ financial_year +"'" +
	     " AND a.ASSET_MAJOR_CLASS_CODE="+assetmajor ;
             System.out.println(qry);
             result = statement.executeQuery(qry);
             
           try
             {  
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<asset_code>" + result.getInt("asset_code") + "</asset_code>";
                	 xml=xml+"<accounting_unit_office_id>" + result.getInt("accounting_unit_office_id") + "</accounting_unit_office_id>";
                	 xml=xml+"<offName>" + result.getString("offName").trim() + "</offName>";
                     xml=xml+"<aval_qty>" + result.getInt("aval_qty") + "</aval_qty>";
                     xml=xml+"<aval_value>" + result.getInt("aval_value") + "</aval_value>";
                     xml=xml+"<crt_aval_qty>" + result.getInt("crt_aval_qty") + "</crt_aval_qty>";
                     xml=xml+"<crt_aval_value>" + result.getInt("crt_aval_value") +"</crt_aval_value>";
                     xml=xml+"<ASSET_MAJOR_CLASS_CODE>"+ result.getInt("ASSET_MAJOR_CLASS_CODE") +"</ASSET_MAJOR_CLASS_CODE>";
 					xml=xml+"<ASSETMINORCLASSCODE1>"+ result.getInt("ASSET_MINOR_CLASS_CODE") +"</ASSETMINORCLASSCODE1>";
 					xml=xml+"<ASSET_MINOR_CLASS_DESC>"+ result.getString("ASSET_MINOR_CLASS_DESC") +"</ASSET_MINOR_CLASS_DESC>";
 					PARTICULARS = result.getString("PARTICULARS").trim();
                	// System.out.println("asss  minor code  "+result.getString("ASSET_MINOR_CLASS_CODE"));
                	 if(PARTICULARS == null)
                	 {
                		 PARTICULARS = " ";
                	 }
                	 xml =xml+ "<PARTICULARS><![CDATA[" + PARTICULARS + "]]></PARTICULARS>";

                	 count++;
                 }

                 xml =xml+ "<exists>"+valExists+"</exists>";
                 xml =xml+ "<count>"+count+"</count>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from DB - Go GET: " + e);
             }
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get ---> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  else  if(strCommand.equals("GoInsertOB"))
        { 
        	int count=0;
        	int accid=0,accoffice=0,assmajo=0,assminor=0,asscode=0,opnBalqty=0,opnBalval=0,dep_Pre_yr=0,app_Pre_yr=0,pre_yr_BookValue=0,dep_allowyr=0,app_allowyr=0;
        	String part="";
        	int officewing=0,dep_upto=0,app_upto=0;
        	double dep_cost=0;
        	
    	System.out.println("\n*************\n GoInsertOB \n**************\n");
        xml="<response><command>GoInsertOB</command>";
        try 
        {
         System.out.println("GoInsertOB");
         String[] divyear=financial_year.split("-");
      	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
      	
      	String qry="select ACCOUNTING_UNIT_ID, "+
" ACCOUNTING_UNIT_OFFICE_ID, "+
" ASSET_MAJOR_CLASS_CODE, "+
" ASSET_MINOR_CLASS_CODE, "+
" ASSET_CODE,openingBal_Qty,openingBal_Value,dep_Pre_yr,app_Pre_yr,pre_yr_BookValue,dep_allowyr,app_allowyr,dep_upto,app_upto, "+
//" (decode(dep_Pre_yr,null,0,dep_Pre_yr)+decode(dep_allowyr,null,0,dep_allowyr) )as dep_upto, "+
//" (decode(app_Pre_yr,null,0,app_Pre_yr)+decode(app_allowyr,null,0,app_allowyr) )as app_upto, "+
" (openingBal_Value-(decode(dep_upto,null,DECODE(app_upto,NULL,0,app_upto),dep_upto)))as dep_cost, "+
" PARTICULARS,OFFICE_WING_SINO "+
" from (select ACCOUNTING_UNIT_ID, "+
" ACCOUNTING_UNIT_OFFICE_ID, "+
" ASSET_MAJOR_CLASS_CODE, "+
" ASSET_MINOR_CLASS_CODE, "+
" ASSET_CODE,openingBal_Qty,openingBal_Value,dep_Pre_yr,app_Pre_yr,pre_yr_BookValue,dep_allowyr,app_allowyr, "+
" (decode(dep_Pre_yr,null,0,dep_Pre_yr)+decode(dep_allowyr,null,0,dep_allowyr) )as dep_upto, "+
" (decode(app_Pre_yr,null,0,app_Pre_yr)+decode(app_allowyr,null,0,app_allowyr) )as app_upto, "+
//" --(openingBal_Value-(decode(dep_upto,null,0,decode(app_upto,null,0,app_upto))))as dep_cost, "+
" PARTICULARS,OFFICE_WING_SINO "+
" from  "+
" (select ACCOUNTING_UNIT_ID, "+
" ACCOUNTING_UNIT_OFFICE_ID, "+
" ASSET_MAJOR_CLASS_CODE, "+
" ASSET_MINOR_CLASS_CODE, "+
" ASSET_CODE, "+
" (decode(OPEN_BAL_QTY,null,0,OPEN_BAL_QTY)+decode(RECIEPTS_YEAR_QTY,null,0,RECIEPTS_YEAR_QTY)-decode(ISSUES_YEAR_QTY,null,0,ISSUES_YEAR_QTY)) as openingBal_Qty, "+
" (decode(OPENING_BAL_VALUE,null,0,OPENING_BAL_VALUE)+decode(RECIEPTS_YR_VALUE,null,0,RECIEPTS_YR_VALUE)-decode(ISSUES_YR_VALUE,null,0,ISSUES_YR_VALUE)) as openingBal_Value, "+
//" --RECIEPTS_YEAR_QTY,ISSUES_YEAR_QTY,RECIEPTS_YR_VALUE,ISSUES_YR_VALUE is zero "+
" (decode(DEPRE_UPTO_DATE,null,0,DEPRE_UPTO_DATE))as dep_Pre_yr, "+
" (decode(APP_GRANT_UPTODATE,null,0,APP_GRANT_UPTODATE))as app_Pre_yr, "+
//" --DEPRE_REC_AC ,APP_GRANT_RECIEVED is zero "+
" NET_DEPRE_COST as pre_yr_BookValue, "+
" round(NET_DEPRE_COST*((decode((select DEPRECIATION_RATE from  FAS_DEPRE_RATES where FINANCIAL_YEAR='"+ newyear +"'"+" and DEPRECIATION_CATE_CODE="+assetmajor+"),null,0,(select DEPRECIATION_RATE from  FAS_DEPRE_RATES where FINANCIAL_YEAR='"+ newyear +"'"+" and DEPRECIATION_CATE_CODE="+assetmajor+")))/100) ) as dep_allowyr, "+
" round(NET_DEPRE_COST*((decode((select APPORTIONMENT_RATE from  FAS_APPORTIONMENT_RATES where FINANCIAL_YEAR='"+ newyear +"'"+" and APPORTION_GRANT_CATE_CODE="+assetmajor+"),null,0,(select APPORTIONMENT_RATE from  FAS_APPORTIONMENT_RATES where FINANCIAL_YEAR='"+ newyear +"'"+" and APPORTION_GRANT_CATE_CODE="+assetmajor+"))) /100)) as app_allowyr, "+
//" --DEPRE_TR_AC , APP_GRANT_TR, zero "+
//" --decode(dep_prev_year,null,decode(app_pre_yr,null,0,app_pre_yr),0,decode(app_pre_yr,null,0,app_pre_yr),dep_prev_year)as dep_prev_year, "+
//" --DEPRE_ALLOWED_YR, "+
" NET_DEPRE_COST, "+
" APPRO_DURING_YR, "+
" REMARKS, "+
" PARTICULARS,OFFICE_WING_SINO from fas_a52_register where ACCOUNTING_UNIT_ID="+ unit_id +
		//" and  "+
		//" ACCOUNTING_UNIT_OFFICE_ID= " + office_id +
		" and  "+
		" FINANCIAL_YEAR='"+ newyear +"'"+" and  "+
		" ASSET_MAJOR_CLASS_CODE ="+assetmajor+" ))";
        
         System.out.println("qry"+qry);
         result = statement.executeQuery(qry);
         int up=0;
         int countA52=0;
       try
         {  
    	   int val=0;
        	 xml=xml+"<flag>success</flag>";
        	 String valExists = "No";
        	/* if(result.next())
             { 
            	 PreparedStatement pss1=connection.prepareStatement(qry);
            	 ResultSet rrss1= pss1.executeQuery();
            	 while (rrss1.next()) {
            		 valExists = "Yes";
					
            		accid=rrss1.getInt("ACCOUNTING_UNIT_ID");
            		accoffice=rrss1.getInt("ACCOUNTING_UNIT_OFFICE_ID");
            		assmajo=rrss1.getInt("ASSET_MAJOR_CLASS_CODE");
            		assminor=rrss1.getInt("ASSET_MINOR_CLASS_CODE");
            		asscode=rrss1.getInt("ASSET_CODE");
            		opnBalqty=rrss1.getInt("openingBal_Qty");
            		opnBalval=rrss1.getInt("openingBal_Value");
            		dep_Pre_yr=rrss1.getInt("dep_Pre_yr");
            		app_Pre_yr=rrss1.getInt("app_Pre_yr");
            		pre_yr_BookValue=rrss1.getInt("pre_yr_BookValue");
            		dep_allowyr=rrss1.getInt("dep_allowyr");
            		app_allowyr=rrss1.getInt("app_allowyr");
                 	part=rrss1.getString("PARTICULARS").trim();
                 	officewing=rrss1.getInt("OFFICE_WING_SINO");
                 	dep_upto=rrss1.getInt("dep_upto");
                 	app_upto=rrss1.getInt("app_upto");
                 	dep_cost=rrss1.getDouble("dep_cost");
                 	
                 	
                 	String checkqry="select 'X' from FAS_A52_REGISTER_OB where ACCOUNTING_UNIT_ID="+accid +
		" and  "+
		" ACCOUNTING_UNIT_OFFICE_ID= " +accoffice +" and  "+
		" FINANCIAL_YEAR='"+financial_year+"'"+" and  "+
		" ASSET_MAJOR_CLASS_CODE ="+assmajo+" and ASSET_MINOR_CLASS_CODE= "+assminor+" and ASSET_CODE="+asscode;
                 	System.out.println(" checkqry--> "+checkqry);
                 	PreparedStatement pss11=connection.prepareStatement(checkqry);
               	 ResultSet rrss11= pss11.executeQuery();
               	 if (rrss11.next()) {
               		 val=1;
               		
               	 }else{
               		String insertOB="insert into FAS_A52_REGISTER_OB(ACCOUNTING_UNIT_ID, "+
               		" ACCOUNTING_UNIT_OFFICE_ID,  "+
               		" FINANCIAL_YEAR, "+
               		" ASSET_MAJOR_CLASS_CODE, "+
               		" ASSET_MINOR_CLASS_CODE, "+
               		" ASSET_CODE, "+
               		" OPEN_BAL_QTY, "+
               		" OPENING_BAL_VALUE, "+
               		" RECIEPTS_YEAR_QTY, "+
               		" RECIEPTS_YR_VALUE, "+
               		" ISSUES_YEAR_QTY, "+
               		" ISSUES_YR_VALUE, "+
               		" DEP_PREV_YEAR, "+
               		" DEPRE_REC_AC, "+
               		" DEPRE_ALLOWED_YR, "+
               		" DEPRE_TR_AC, "+
               		" DEPRE_UPTO_DATE, "+
               		" NET_DEPRE_COST, "+
               		" APP_PRE_YR, "+
               		" APP_GRANT_RECIEVED, "+
               		" APPRO_DURING_YR, "+
               		" APP_GRANT_TR, "+
               		" APP_GRANT_UPTODATE, "+
               		//" REMARKS, "+
               		" UPDATED_BY_USERID, "+
               		" UPDATED_DATE, "+
               		" PARTICULARS, "+
               		" OFFICE_WING_SINO, "+
               		" PRV_YEAR_BOOKVALUE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
               		                 	PreparedStatement pss2=connection.prepareStatement(insertOB);
               		                 	
               		                 	pss2.setInt(1,accid); 
               		                 	pss2.setInt(2,accoffice);  
               		                 	pss2.setString(3,financial_year);
               							pss2.setInt(4,assmajo);
               							pss2.setInt(5,assminor);
               							pss2.setInt(6,asscode); 
               							pss2.setInt(7,opnBalqty);
               							pss2.setInt(8,opnBalval);
               							pss2.setInt(9,0);
               							pss2.setInt(10,0);
               							pss2.setInt(11,0);
               							pss2.setInt(12,0);
               							pss2.setInt(13,dep_Pre_yr);
               							pss2.setInt(14,0); 
               							pss2.setInt(15,dep_allowyr);
               							pss2.setInt(16,0);
               							pss2.setInt(17,dep_upto);
               							pss2.setDouble(18,dep_cost);
               							pss2.setInt(19,app_Pre_yr);
               							pss2.setInt(20,0);
               							pss2.setInt(21,app_allowyr);
               							pss2.setInt(22,0);
               							pss2.setInt(23,app_upto);				
               							pss2.setString(24,userid);
               							pss2.setTimestamp(25,ts);
               							pss2.setString(26,part);
               							pss2.setInt(27,officewing);
               							pss2.setInt(28,pre_yr_BookValue);
               							up=pss2.executeUpdate();
               		                 	

               		            	 count++;
               		            	// xml=xml+"<flagValue>AlreadyExist</flagValue>";
               	 }
               	countA52++;
            	 }
            	xml =xml+ "<exists>"+valExists+"</exists>"; 
            	System.out.println("countA52==count "+countA52+"  "+count);
            	
            	if(val==1){
            		 xml=xml+"<flagValue>AlreadyExist</flagValue>";
            	}else{
            		 xml=xml+"<flagValue>NoExist</flagValue>";
            	if(countA52==count){
            		 xml =xml+ "<insertRes>success</insertRes>"; 
            	}else{
            		 xml =xml+ "<insertRes>notinsert</insertRes>"; 
            	}
            	}
             }else{	 
            	 xml =xml+ "<exists>"+valExists+"</exists>";  
             }*/

        	 
        	 
        	 if(result.next())
             { 
            	 PreparedStatement pss1=connection.prepareStatement(qry);
            	 ResultSet rrss1= pss1.executeQuery();
            	 while (rrss1.next()) {
            		 valExists = "Yes";
					
            		accid=rrss1.getInt("ACCOUNTING_UNIT_ID");
            		accoffice=rrss1.getInt("ACCOUNTING_UNIT_OFFICE_ID");
            		assmajo=rrss1.getInt("ASSET_MAJOR_CLASS_CODE");
            		assminor=rrss1.getInt("ASSET_MINOR_CLASS_CODE");
            		asscode=rrss1.getInt("ASSET_CODE");
            		opnBalqty=rrss1.getInt("openingBal_Qty");
            		opnBalval=rrss1.getInt("openingBal_Value");
            		dep_Pre_yr=rrss1.getInt("dep_Pre_yr");
            		app_Pre_yr=rrss1.getInt("app_Pre_yr");
            		pre_yr_BookValue=rrss1.getInt("pre_yr_BookValue");
            		dep_allowyr=rrss1.getInt("dep_allowyr");
            		app_allowyr=rrss1.getInt("app_allowyr");
                 	part=rrss1.getString("PARTICULARS").trim();
                 	officewing=rrss1.getInt("OFFICE_WING_SINO");
                 	dep_upto=rrss1.getInt("dep_upto");
                 	app_upto=rrss1.getInt("app_upto");
                 	dep_cost=rrss1.getDouble("dep_cost");
                 	
                 	
                 	String checkqry="select 'X' from FAS_A52_REGISTER_EDIT where ACCOUNTING_UNIT_ID="+accid +
		" and  "+
		" ACCOUNTING_UNIT_OFFICE_ID= " +accoffice +" and  "+
		" FINANCIAL_YEAR='"+financial_year+"'"+" and  "+
		" ASSET_MAJOR_CLASS_CODE ="+assmajo+" and ASSET_MINOR_CLASS_CODE= "+assminor+" and ASSET_CODE="+asscode;
                 	System.out.println(" checkqry--> "+checkqry);
                 	PreparedStatement pss11=connection.prepareStatement(checkqry);
               	 ResultSet rrss11= pss11.executeQuery();
               	 if (rrss11.next()) {
               		 val=1;
               		
               	 }else{
               		String insertOB="insert into FAS_A52_REGISTER_EDIT(ACCOUNTING_UNIT_ID, "+
               		" ACCOUNTING_UNIT_OFFICE_ID,  "+
               		" FINANCIAL_YEAR, "+
               		" ASSET_MAJOR_CLASS_CODE, "+
               		" ASSET_MINOR_CLASS_CODE, "+
               		" ASSET_CODE, "+
               		" OPEN_BAL_QTY, "+
               		" OPENING_BAL_VALUE, "+
               		" RECIEPTS_YEAR_DR_QTY, "+
               		" RECIEPTS_YR_DR_VALUE, "+
               		" ISSUES_YEAR_CR_QTY, "+
               		" ISSUES_YR_CR_VALUE, "+
               		" DEP_PREV_YEAR, "+
               		" DEPRE_REC_AC, "+
               		" DEPRE_ALLOWED_YR_CR, "+
               		" DEPRE_TR_AC, "+
               		" DEPRE_UPTO_DATE, "+
               		" NET_DEPRE_COST, "+
               		" APP_PRE_YR, "+
               		" APP_REC_AC, "+
               		" APPRO_ALLOWED_YR_CR, "+
               		" APPRO_TR_AC, "+
               		" APPRO_UPTO_DATE, "+
               		//" REMARKS, "+
               		" UPDATED_BY_USERID, "+
               		" UPDATED_DATE, "+
               		" REMARKS, "+
               		" OFFICE_WING_SINO "+
               		//" PRV_YEAR_BOOKVALUE" +
               		") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
               		                 	PreparedStatement pss2=connection.prepareStatement(insertOB);
               		                 	
               		                 	pss2.setInt(1,accid); 
               		                 	pss2.setInt(2,accoffice);  
               		                 	pss2.setString(3,financial_year);
               							pss2.setInt(4,assmajo);
               							pss2.setInt(5,assminor);
               							pss2.setInt(6,asscode); 
               							pss2.setInt(7,opnBalqty);
               							pss2.setInt(8,opnBalval);
               							pss2.setInt(9,0);
               							pss2.setInt(10,0);
               							pss2.setInt(11,0);
               							pss2.setInt(12,0);
               							pss2.setInt(13,dep_Pre_yr);
               							pss2.setInt(14,0); 
               							pss2.setInt(15,dep_allowyr);
               							pss2.setInt(16,0);
               							pss2.setInt(17,dep_upto);
               							pss2.setDouble(18,dep_cost);
               							pss2.setInt(19,app_Pre_yr);
               							pss2.setInt(20,0);
               							pss2.setInt(21,app_allowyr);
               							pss2.setInt(22,0);
               							pss2.setInt(23,app_upto);				
               							pss2.setString(24,userid);
               							pss2.setTimestamp(25,ts);
               							pss2.setString(26,part);
               							pss2.setInt(27,officewing);
               							//pss2.setInt(28,pre_yr_BookValue);
               							up=pss2.executeUpdate();
               		                 	

               		            	 count++;
               		            	// xml=xml+"<flagValue>AlreadyExist</flagValue>";
               	 }
               	countA52++;
            	 }
            	xml =xml+ "<exists>"+valExists+"</exists>"; 
            	System.out.println("countA52==count "+countA52+"  "+count);
            	
            	if(val==1){
            		 xml=xml+"<flagValue>AlreadyExist</flagValue>";
            	}else{
            		 xml=xml+"<flagValue>NoExist</flagValue>";
            	if(countA52==count){
            		 xml =xml+ "<insertRes>success</insertRes>"; 
            	}else{
            		 xml =xml+ "<insertRes>notinsert</insertRes>"; 
            	}
            	}
             }else{	 
            	 xml =xml+ "<exists>"+valExists+"</exists>";  
             }
        	 
        
         
         
         }catch(Exception e)
         {
        	 System.out.println("Exception in getting values from DB - Go insert: " + e);
         }
        }
        catch(Exception e1)
        {
        	System.out.println("Exception is in initial ---> "+e1);
        	xml=xml+"<flag>failure</flag>";
        }
        xml=xml+"</response>";
    } 
       /* else  if(strCommand.equals("getValue"))
        { 
        	int count=0;
    	System.out.println("\n*************\ngetValue \n**************\n");
        xml="<response><command>getValue</command>";
        try 
        {
         System.out.println("getValue");
         String[] divyear=financial_year.split("-");
      	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
         String qry="select OPEN_BAL_QTY, "+
         " OPENING_BAL_VALUE, "+
" reciepts_year_qty,"+
" reciepts_yr_value,"+
" ISSUES_YEAR_QTY,"+
" issues_yr_value,"+
" dep_prev_year,"+
" depre_rec_ac,"+
" depre_allowed_yr,"+
" DEPRE_TR_AC,"+
" depre_upto_date,"+
" net_depre_cost,"+
" APP_PRE_YR,"+
" app_grant_recieved,"+
" appro_during_yr,"+
" APP_GRANT_TR,"+
" app_grant_uptodate,"+
" REMARKS,"+
" particulars,"+
" TEST_DEP_COST,accounting_unit_id,"+
        		 " accounting_unit_office_id,asset_code,ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE,"+
        		  " (SELECT m.ASSET_MINOR_CLASS_DESC "+
        					 " FROM FAS_ASSET_MINOR_CLASSIFICATION m "+
        					 " WHERE m.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND m.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE "+
        					" ) "+
        					" AS ASSET_MINOR_CLASS_DESC, " +
        					"  (select m1.ASSET_MAJOR_CLASS_DESC From FAS_MST_ASSETS_CLASS m1 where m1.ASSET_MAJOR_CLASS_CODE=a.ASSET_MAJOR_CLASS_CODE)as asset_major_desc, "+
        					"(select OFFICE_NAME from com_mst_offices where OFFICE_ID=a.accounting_unit_office_id)as offName  "+
          "  from FAS_A52_REGISTER a " +
         		
         " WHERE " +
        // "a.accounting_unit_id = "+ unit_id +
    // " AND " +
     "a.accounting_unit_office_id = " + office_id +
     " AND a.financial_year ='"+ newyear +"'" +
     " AND a.ASSET_MAJOR_CLASS_CODE="+assetmajor ;
         System.out.println(qry);
         result = statement.executeQuery(qry);
         
       try
         {  
        	 xml=xml+"<flag>success</flag>";
        	 String valExists = "No";
             while(result.next())
             { 
            	 
           
            	 
            	 valExists = "Yes";
            	 xml += "<asset_code>" + result.getInt("asset_code") + "</asset_code>";
            	 xml=xml+"<accounting_unit_office_id>" + result.getInt("accounting_unit_office_id") + "</accounting_unit_office_id>";
            	 xml=xml+"<offName>" + result.getString("offName").trim() + "</offName>";
                 xml=xml+"<OPEN_BAL_QTY>" + result.getInt("OPEN_BAL_QTY") + "</OPEN_BAL_QTY>";
                 xml=xml+"<OPENING_BAL_VALUE>" + result.getInt("OPENING_BAL_VALUE") + "</OPENING_BAL_VALUE>";
                 xml=xml+"<reciepts_year_qty>" + result.getInt("reciepts_year_qty") + "</reciepts_year_qty>";
                 xml=xml+"<reciepts_yr_value>" + result.getInt("reciepts_yr_value") +"</reciepts_yr_value>";
                 int tot_qty=result.getInt("OPEN_BAL_QTY")+result.getInt("reciepts_year_qty") ;
                 int tot_value=result.getInt("OPENING_BAL_VALUE")+result.getInt("reciepts_yr_value");

          		 xml=xml+"<tot_qty>" + tot_qty + "</tot_qty>";
                 xml=xml+"<tot_value>" + tot_value +"</tot_value>";

                 xml=xml+"<ISSUES_YEAR_QTY>" + result.getInt("ISSUES_YEAR_QTY") + "</ISSUES_YEAR_QTY>";
                 xml=xml+"<issues_yr_value>" + result.getInt("issues_yr_value") + "</issues_yr_value>";
                 
                
          		int cb_qty=tot_qty- result.getInt("ISSUES_YEAR_QTY");
          		int cb_value=tot_value-result.getInt("issues_yr_value");
          		 xml=xml+"<cb_qty>" + cb_qty + "</cb_qty>";
                 xml=xml+"<cb_value>" + cb_value +"</cb_value>";

                 xml=xml+"<dep_prev_year>" + result.getInt("dep_prev_year") + "</dep_prev_year>";
                 xml=xml+"<depre_rec_ac>" + result.getInt("depre_rec_ac") +"</depre_rec_ac>";
                 xml=xml+"<depre_allowed_yr>" + result.getInt("depre_allowed_yr") + "</depre_allowed_yr>";
                 xml=xml+"<DEPRE_TR_AC>" + result.getInt("DEPRE_TR_AC") + "</DEPRE_TR_AC>";
                 xml=xml+"<depre_upto_date>" + result.getInt("depre_upto_date") + "</depre_upto_date>";
               
                 xml=xml+"<net_depre_cost>" + result.getInt("net_depre_cost") + "</net_depre_cost>";
                 xml=xml+"<APP_PRE_YR>" + result.getInt("APP_PRE_YR") + "</APP_PRE_YR>";
                 xml=xml+"<app_grant_recieved>" + result.getInt("app_grant_recieved") + "</app_grant_recieved>";
                 xml=xml+"<appro_during_yr>" + result.getInt("appro_during_yr") + "</appro_during_yr>";
                 xml=xml+"<APP_GRANT_TR>" + result.getInt("APP_GRANT_TR") +"</APP_GRANT_TR>";
             
                 Upto Previous year Depreciation + 
                 Received through proforma account + 
                 allowed during the year credit –
                 allowed during the year debit
                 
                 
                 int tot_dep=result.getInt("dep_prev_year")+result.getInt("depre_rec_ac")+result.getInt("depre_allowed_yr");
                 int tot_app= result.getInt("APP_PRE_YR")+ result.getInt("app_grant_recieved")+result.getInt("appro_during_yr");
                 xml=xml+"<tot_dep>" + tot_dep + "</tot_dep>";
                 xml=xml+"<tot_app>" + tot_app + "</tot_app>";
                 //11a – 12a 
                 int upto_dep=tot_dep-result.getInt("DEPRE_TR_AC")  ;
                 int upto_app=tot_app-result.getInt("APP_GRANT_TR");
                 xml=xml+"<upto_dep>" + upto_dep +"</upto_dep>";
                 xml=xml+"<upto_app>" + upto_app +"</upto_app>";
                 
                 xml=xml+"<ASSET_MAJOR_CLASS_CODE>"+ result.getInt("ASSET_MAJOR_CLASS_CODE") +"</ASSET_MAJOR_CLASS_CODE>";
					xml=xml+"<ASSETMINORCLASSCODE>"+ result.getInt("ASSET_MINOR_CLASS_CODE") +"</ASSETMINORCLASSCODE>";
					xml=xml+"<ASSET_MINOR_CLASS_DESC>"+ result.getString("ASSET_MINOR_CLASS_DESC") +"</ASSET_MINOR_CLASS_DESC>";
					xml=xml+"<asset_major_desc>"+ result.getString("asset_major_desc") +"</asset_major_desc>";
					PARTICULARS = result.getString("PARTICULARS").trim();
            	// System.out.println("asss  minor code  "+result.getString("ASSET_MINOR_CLASS_CODE"));
            	 if(PARTICULARS == null)
            	 {
            		 PARTICULARS = " ";
            	 }
            	 xml =xml+ "<PARTICULARS><![CDATA[" + PARTICULARS + "]]></PARTICULARS>";

            	 count++;
             }

             xml =xml+ "<exists>"+valExists+"</exists>";
             xml =xml+ "<count>"+count+"</count>";
         }catch(Exception e)
         {
        	 System.out.println("Exception in getting values from DB - Go GET: " + e);
         }
        }
        catch(Exception e1)
        {
        	System.out.println("Exception is in Get ---> "+e1);
        	xml=xml+"<flag>failure</flag>";
        }
        xml=xml+"</response>";
    } */
        else if(strCommand.equals("loadMajor"))
        { 
        	System.out.println("\n*************\nloadMajor\n**************\n");
            xml="<response><command>loadMajor</command>";
            try 
            {
             result = statement.executeQuery("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_MAJOR_CLASS_CODE>" + result.getInt("ASSET_MAJOR_CLASS_CODE") + "</ASSET_MAJOR_CLASS_CODE>";
                	 xml += "<ASSET_MAJOR_CLASS_DESC><![CDATA[" + result.getString("ASSET_MAJOR_CLASS_DESC") + "]]></ASSET_MAJOR_CLASS_DESC>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from ASSET_MAJOR_CLASS_CODE: " + e);
             }
             
             result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
        else if(strCommand.equals("officeload"))
        { 
        	System.out.println("\n*************\nofficeload\n**************\n");
            xml="<response><command>officeload</command>";
             int cc=0;     
            try 
            {
			String queryFind="select a.RENDERING_UNIT_OFFICE_ID,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=a.rendering_unit_office_id)as officeName from fas_asset_val_ac_render_units a where a.acct_rendering_unit_id="+unit_id+" order by a.RENDERING_UNIT_OFFICE_ID";
			ps1=connection.prepareStatement(queryFind);
			rs1=ps1.executeQuery();					
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(rs1.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<officeID>" + rs1.getInt("RENDERING_UNIT_OFFICE_ID") + "</officeID>";
                	 xml += "<officeName>" + rs1.getString("officeName") + "</officeName>";
                	 cc++;
                 }

                 xml += "<exists>"+valExists+"</exists>";
                 xml += "<count>"+cc+"</count>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from office desc: " + e);
             }
             
             rs1.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in office"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            
            
        } 
        else if (strCommand.equalsIgnoreCase("checkFreeze")) {
        	int c1=0,c2=0,c3=0;
            xml = "<response><command>checkFreeze</command>";
            System.out.println("checkFreeze in a52 verified ");
            try {
            	
            	String[] divyear=financial_year.split("-");
            	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
            	System.out.println("new year  "+newyear);
            	/*int cricleID11=0;
                int circleoff11=0;
                String officelevel11="";
                String selectQry11="select office_level_id from com_mst_all_offices_view  where office_id="+office_id;
		           PreparedStatement ps211=connection.prepareStatement(selectQry11);
		           ResultSet rs11=ps211.executeQuery();
		           while(rs11.next()){
		        	   officelevel11=rs11.getString("office_level_id");
		           }
		           System.out.println(" officelevel "+officelevel11);
		           if(officelevel11.equalsIgnoreCase("RN")){
		        	   cricleID11=unit_id;
		        	   circleoff11=office_id;
		        	   //select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?
		           } else  if(officelevel11.equalsIgnoreCase("CL")){
		        	   cricleID11=unit_id;
		        	   circleoff11=office_id;
		           
		           }
		           else if(officelevel11.equalsIgnoreCase("HO")){
		        	   cricleID11=unit_id;
		        	   circleoff11=office_id;
		        	   //select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?
		           }
		           else {
		        	   String selectCircleID11="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
		            	  PreparedStatement ps111 =  connection.prepareStatement(selectCircleID11);
		                  ps111.setInt(1, office_id);
		                   ResultSet rss111=ps111.executeQuery();
		                  if(rss111.next()){
		                	  circleoff11=rss111.getInt("CIRCLE_OFFICE_ID");
		                	  String selectCircleID1="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?";
			                  PreparedStatement ps311 =  connection.prepareStatement(selectCircleID1);
			                  ps311.setInt(1, circleoff11);
			                  ResultSet rss311=ps311.executeQuery();
			                  if(rss311.next()){
			                	  cricleID11=rss311.getInt("ACCOUNTING_UNIT_ID");	  
			                  }
		                  } else{
		                	  
		                	  
		                  }
		                  
		                 
		           } */
            	
            /*	int cricleID=0;
            	String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
            	  ps =  connection.prepareStatement(selectCircleID);
                  ps.setInt(1, office_id);
                  rss1=ps.executeQuery();
                  while(rss1.next()){
                	  cricleID=rss1.getInt("CIRCLE_OFFICE_ID");	  
                  }*/
            	int cricleID11=0;
            	int circleoff11=0;
            	String selectCircleuID="select ACCT_RENDERING_UNIT_ID from FAS_ASSET_VAL_AC_RENDER_UNITS where RENDERING_UNIT_OFFICE_ID=?";
            	  ps =  connection.prepareStatement(selectCircleuID);
                  ps.setInt(1, office_id);
                  rss1=ps.executeQuery();
                  if(rss1.next()){
                	  cricleID11=rss1.getInt("ACCT_RENDERING_UNIT_ID");	  
                  }
                  int cricleID=0;
              	String selectCircleID="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?";
              	  PreparedStatement ps22 =  connection.prepareStatement(selectCircleID);
                    ps22.setInt(1, cricleID11);
                    ResultSet rss122=ps22.executeQuery();
                    while(rss122.next()){
                  	  cricleID=rss122.getInt("ACCOUNTING_UNIT_OFFICE_ID");	  
                    }
                 // System.out.println("circle office Id "+circleoff11);
                  System.out.println("cricleID11 office Id "+cricleID+" orignal  "+office_id);
                
                  
                  String searchquery="select 'X' from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR='"+newyear+"' ";//and finanical_year='"+cmbFinancialYear+"' 
                 PreparedStatement ps11 =  connection.prepareStatement(searchquery);
                  ps11.setInt(1, office_id);
                  ResultSet rss21=ps11.executeQuery();
                 // System.out.println(" searchquery  "+searchquery);
                  if(rss21.next()){
      			
      				String searchquery1="select A52_STATUS_VAL from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR='"+newyear+"' and A52_STATUS_VAL='Y' ";//and finanical_year='"+cmbFinancialYear+"' 
                    ps1 =  connection.prepareStatement(searchquery1);
                    ps1.setInt(1, cricleID);
                   // ps1.setInt(2, cricleID);
                   // ps1.setString(2, newyear);
                    rss2=ps1.executeQuery();
                  //  System.out.println(" searchquery  "+searchquery);
                    if(rss2.next()){
        				xml = xml + "<flag>freezeCricle</flag>";
        				System.out.println("freezeCricle   ");
        				
                    }else{
                    	xml = xml + "<flag>notfreezeCricle</flag>";
                    } 
                  }else{
                  	//xml = xml + "<flag>notfreezeCricle</flag>";
                		xml = xml + "<flag>freezeUnit</flag>";
          				System.out.println("freezeCricle   ");
            	
                  }
                
            } catch (SQLException e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
            xml=xml+"</response>";
           
        }
        
        /*else if(strCommand.equals("checkVerifyA52"))
        { 
        	//System.out.println("\n*************\n checkVerifyA52 \n**************\n");
            xml="<response><command>checkVerifyA52</command>";
            try 
            {  
             result = statement.executeQuery("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,A52_STATUS_VAL from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID="+unit_id+" and FINANCIAL_YEAR='"+financial_year+"'");
                //System.out.println("aft res");and ACCOUNTING_FOR_OFFICE_ID="+office_id+" 
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ACCOUNTING_UNIT_ID>" + result.getInt("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
                	 xml += "<A52_STATUS_VAL>" + result.getString("A52_STATUS_VAL") + "</A52_STATUS_VAL>";
                	 //xml += "<A52_STATUS_QTY>" + result.getString("A52_STATUS_QTY") + "</A52_STATUS_QTY>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from fas a52 verify: " + e);
             }
             
             result.close();
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  */
 
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		
		Connection connection=null;
        Statement statement=null;
        ResultSet result=null,rss1=null,rss3=null;
        PreparedStatement ps1=null,ps3=null;
        try
        {
    	   ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
           String ConnectionString="";

           String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
           String strdsn=rs.getString("Config.DSN");
           String strhostname=rs.getString("Config.HOST_NAME");
           String strportno=rs.getString("Config.PORT_NUMBER");
           String strsid=rs.getString("Config.SID");
           String strdbusername=rs.getString("Config.USER_NAME");
           String strdbpassword=rs.getString("Config.PASSWORD");
              
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

           Class.forName(strDriver.trim());
           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           try
           {
        	   statement=connection.createStatement();
               connection.clearWarnings();
           }
           catch(SQLException e)
           {
               System.out.println("Exception in creating statement:"+e);
           }
        }
        catch(Exception e)
        {
           System.out.println("Exception in openeing connection:"+e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        int unit_id = 0;
        int office_id = 0;
        int assetmajor=0;
	   	String financial_year = null;
int circle_ID=0;
int cmb_Vendor=0;
        HttpSession session=request.getSession(false);
        try
        {
	        if(session==null)
	        {
	            System.out.println(request.getContextPath()+"/index.jsp");
	            response.sendRedirect(request.getContextPath()+"/index.jsp");
	        }
	        System.out.println(session);
        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
               
        String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:"+userid);
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : post " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        int up=0;
        
        int asset_code1=0;
        int OB_Qty1=0,OB_Value1=0,Receipts_Dr_Qty1=0,Receipts_Cr_Qty1=0,Receipts_Dr_Value1=0,Receipts_Cr_Value1=0;
        int Total_Qty1=0,Total_Value1=0,Issues_Dr_Qty1=0,Issues_Cr_Qty1=0,Issues_Dr_Value1=0,Issues_Cr_Value1=0,CB_Qty1=0,CB_Value1=0,Upto_Pre_Depr1=0;
        int Upto_Pre_Appr1=0,Rec_Thr_dep1=0,Rec_Thr_app1=0,allow_dur_dep_dr1=0,allow_dur_dep_cr1=0,allow_dur_app_dr1=0,allow_dur_app_cr1=0;
        int tot_Dep1=0,tot_App1=0,trasf_Thr_dep1=0,trasf_Thr_app1=0,Upto_date_Dep1=0,Upto_date_App1=0,dep_Cost1=0;
        String desc1="",depreciat="",apport="",headac="";
        try{
       
           if(strCommand.equalsIgnoreCase("updateEdit")){
        	 
        	   connection.setAutoCommit(false);
        	   try
               {
               	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
               }        
               
               try
               {
               	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
               } 
               try
               {
            	   assetmajor = Integer.parseInt(request.getParameter("cmbmajorasset"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'major' parameter ===> " + e);
               } 
               int majorCoode=0;
               try
               {
            	   financial_year =request.getParameter("cmbFinancialYear");
             	  majorCoode =Integer.parseInt(request.getParameter("cmbmajorasset"));
            	   
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
               } 
              
               try
               {
            	  headac =request.getParameter("cmbheadac");
            	  asset_code1=Integer.parseInt(request.getParameter("asset_code"));
            	 
            	  depreciat =request.getParameter("cmbdepreciat");
            	    apport =request.getParameter("cmbapport");
            	    desc1=request.getParameter("desc");
            	  
            	  OB_Qty1 =Integer.parseInt(request.getParameter("OB_Qty"));
            	 
            	  OB_Value1 =Integer.parseInt(request.getParameter("OB_Value"));
            	  
            	  Receipts_Dr_Qty1 =Integer.parseInt(request.getParameter("Receipts_Dr_Qty"));
            	  
            	  Receipts_Cr_Qty1 =Integer.parseInt(request.getParameter("Receipts_Cr_Qty"));
            	  
            	  Receipts_Dr_Value1 =Integer.parseInt(request.getParameter("Receipts_Dr_Value"));
            	  
            	  Receipts_Cr_Value1 =Integer.parseInt(request.getParameter("Receipts_Cr_Value"));
            	  
            	  Total_Qty1 =Integer.parseInt(request.getParameter("Total_Qty"));
            	 
            	  Total_Value1 =Integer.parseInt(request.getParameter("Total_Value"));
            	 
            	  Issues_Dr_Qty1 =Integer.parseInt(request.getParameter("Issues_Dr_Qty"));
            	 
            	  Issues_Cr_Qty1 =Integer.parseInt(request.getParameter("Issues_Cr_Qty"));
            	  
            	  Issues_Dr_Value1 =Integer.parseInt(request.getParameter("Issues_Dr_Value"));
            	  
            	 
            	  Issues_Cr_Value1 =Integer.parseInt(request.getParameter("Issues_Cr_Value"));
            	  
            	  CB_Qty1 =Integer.parseInt(request.getParameter("CB_Qty"));
            	  
            	  
            	  CB_Value1 =Integer.parseInt(request.getParameter("CB_Value"));
            	  
            	Upto_Pre_Depr1 =Integer.parseInt(request.getParameter("Upto_Pre_Depr"));
            			  
            	  Upto_Pre_Appr1 =Integer.parseInt(request.getParameter("Upto_Pre_Appr"));
            	  
            	  Rec_Thr_dep1 =Integer.parseInt(request.getParameter("Rec_Thr_dep"));
            	 
            	  Rec_Thr_app1 =Integer.parseInt(request.getParameter("Rec_Thr_app"));
            	  
            	  allow_dur_dep_dr1 =Integer.parseInt(request.getParameter("allow_dur_dep_dr"));
            	  
            	  allow_dur_dep_cr1 =Integer.parseInt(request.getParameter("allow_dur_dep_cr"));
            	 
            	  allow_dur_app_dr1=Integer.parseInt(request.getParameter("allow_dur_app_dr"));
            	  
            	  allow_dur_app_cr1 =Integer.parseInt(request.getParameter("allow_dur_app_cr"));
            	  
            	  tot_Dep1 =Integer.parseInt(request.getParameter("tot_Dep"));
            	  
            	  tot_App1 =Integer.parseInt(request.getParameter("tot_App"));
            	  
            	  trasf_Thr_dep1 =Integer.parseInt(request.getParameter("trasf_Thr_dep"));
            	  
            	  trasf_Thr_app1 =Integer.parseInt(request.getParameter("trasf_Thr_app"));
            	  
            	  Upto_date_Dep1 =Integer.parseInt(request.getParameter("Upto_date_Dep"));
            	  
            	  Upto_date_App1=Integer.parseInt(request.getParameter("Upto_date_App"));
            	 
            	  dep_Cost1=Integer.parseInt(request.getParameter("dep_Cost"));
 
            	  
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting parameter ===> " + e);
               }
             /* System.out.println("headac "+headac +" asset_code1 "+asset_code1+" 33 "+majorCoode+" majorCoode "+depreciat+"cmbdepreciat"+apport +"cmbapport"+desc1+"desc"+OB_Qty1+"OB_Qty"+ OB_Value1 +"OB_Value"+ Receipts_Dr_Qty1 +"Receipts_Dr_Qty"+ Receipts_Cr_Qty1+"Receipts_Cr_Qty"+Receipts_Dr_Value1+"Receipts_Dr_Value"+  Receipts_Cr_Value1 +"Receipts_Cr_Value"+
                 	  Total_Qty1 +"Total_Qty"+Total_Value1+"Total_Value"+
                 	  Issues_Dr_Qty1 +"Issues_Dr_Qty"+
                 	  Issues_Cr_Qty1 +"Issues_Cr_Qty"+
                 	  Issues_Dr_Value1 +"Issues_Dr_Value"+
                 	  Issues_Cr_Value1 +"Issues_Cr_Value"+
                 	  CB_Qty1 +"CB_Qty"+
                 	  CB_Value1 +"CB_Value"+
                 	  Upto_Pre_Depr1 +"Upto_Pre_Depr"+
                 	  Upto_Pre_Appr1 +"Upto_Pre_Appr"+
                 	  Rec_Thr_dep1 +"Rec_Thr_dep"+
                 	  Rec_Thr_app1 +"Rec_Thr_app"+
                 	  allow_dur_dep_dr1 +"allow_dur_dep_dr"+
                 	  allow_dur_dep_cr1 +"allow_dur_dep_cr"+
                 	  allow_dur_app_dr1+"allow_dur_app_dr"+
                 	  allow_dur_app_cr1 +"allow_dur_app_cr"+
                 	  tot_Dep1 +"tot_Dep"+
                 	  tot_App1 +"tot_App"+
                 	  trasf_Thr_dep1 +"trasf_Thr_dep"+
                 	  trasf_Thr_app1 +"trasf_Thr_app"+
                 	  Upto_date_Dep1 +"Upto_date_Dep"+
                 	  Upto_date_App1+"Upto_date_App"+
                 	  dep_Cost1);*/
           /*     
               
        	String[] sno=request.getParameterValues("sno");
        	String[] assetcode=request.getParameterValues("assetcode");
        	String[] minorcode=request.getParameterValues("minorcode");
        	String[] offcode=request.getParameterValues("offcode");
        	String[] offName=request.getParameterValues("offName");
        	String[] aval_qty=request.getParameterValues("aval_qty");
        	String[] crt_aval_qty=request.getParameterValues("crt_aval_qty");
        	String[] PARTICULARS1=request.getParameterValues("PARTICULARS");
        	String[] finyr=financial_year.split("-");
        	int finyr1=(Integer.parseInt(finyr[0])+1);
        	int finyr2=(Integer.parseInt(finyr[1])+1);
        	String finyrfull=finyr1+"-"+finyr2;
        	
        	System.out.println("finyrfull  "+finyrfull);
        	String[] N_OPENING_BAL_VALUE=request.getParameterValues("N_OPENING_BAL_VALUE");
        	String[] N_RECIEPTS_YR_VALUE=request.getParameterValues("N_RECIEPTS_YR_VALUE");
        	String[] N_ISSUES_YR_VALUE=request.getParameterValues("N_ISSUES_YR_VALUE");
        	String[] OPEN_BAL_QTY=request.getParameterValues("OPEN_BAL_QTY");
        	String[] RECIEPTS_YEAR_QTY=request.getParameterValues("RECIEPTS_YEAR_QTY");
        	String[] ISSUES_YEAR_QTY=request.getParameterValues("ISSUES_YEAR_QTY");
        	//String[] officeN=request.getParameterValues("offName");
        	
        	java.sql.Date DATE_OF_ENTRY1=new java.sql.Date(ts.getTime());
			System.out.println("DATE_OF_ENTRY --->  "+DATE_OF_ENTRY1);
        	
        	int ss=assetcode.length;
        	int cc=0;
            System.out.println("assetcode.length =="+ss);
            
            try{
            	
            	int cricleID11=0;
                int circleoff11=0;
                String officelevel11="";
                String selectQry11="select office_level_id from com_mst_all_offices_view  where office_id="+office_id;
		           PreparedStatement ps211=connection.prepareStatement(selectQry11);
		           ResultSet rs11=ps211.executeQuery();
		           while(rs11.next()){
		        	   officelevel11=rs11.getString("office_level_id");
		           }
		           System.out.println(" officelevel "+officelevel11);
		           if(officelevel11.equalsIgnoreCase("RN")){
		        	   cricleID11=unit_id;
		        	   //select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?
		           } else  if(officelevel11.equalsIgnoreCase("CL")){
		        	   cricleID11=unit_id;
		           
		           }
		           else if(officelevel11.equalsIgnoreCase("HO")){
		        	   cricleID11=unit_id;
		        	   //select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?
		           }
		           else {
		        	   String selectCircleID11="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
		            	  PreparedStatement ps111 =  connection.prepareStatement(selectCircleID11);
		                  ps111.setInt(1, office_id);
		                   ResultSet rss111=ps111.executeQuery();
		                  while(rss111.next()){
		                	  circleoff11=rss111.getInt("CIRCLE_OFFICE_ID");	  
		                  } 
		                  String selectCircleID1="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?";
		                  PreparedStatement ps311 =  connection.prepareStatement(selectCircleID1);
		                  ps311.setInt(1, circleoff11);
		                  ResultSet rss311=ps311.executeQuery();
		                  while(rss311.next()){
		                	  cricleID11=rss311.getInt("ACCOUNTING_UNIT_ID");	  
		                  }
		                 
		           } 
            	
            	   String selectcmd="select 'X' from FAS_ASSETS_NUM_OB where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=? AND ASSET_MAJOR_CLASS_CODE=?";
            	   PreparedStatement ps30 =  connection.prepareStatement(selectcmd);
                  ps30.setInt(1, cricleID11);                   
                  ps30.setInt(2, office_id);
                  ps30.setString(3, finyrfull);
                  ps30.setInt(4,assetmajor);
                  ResultSet rss30=ps30.executeQuery();
                  if(rss30.next()){
               	   	  connection.rollback();	
      				  sendMessage(response,"Record Already Moved","ok"); 
                  }else{
                	  for(int ii=0;ii<ss;ii++){
                  		//System.out.println("------------------"+ii+"-----------------");
                  		
                  		String officelevel="";
                      	String units="";
                        //  System.out.println("goget");
                         
                          int cricleID=0;
                          int circleoff=0;
                          String selectQry="select office_level_id from com_mst_all_offices_view  where office_id="+office_id;
          		           PreparedStatement ps2=connection.prepareStatement(selectQry);
          		           ResultSet rs=ps2.executeQuery();
          		           while(rs.next()){
          		        	   officelevel=rs.getString("office_level_id");
          		           }
          		           System.out.println(" officelevel "+officelevel);
          		           if(officelevel.equalsIgnoreCase("RN")){
          		        	   cricleID=unit_id;
          		        	   //select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?
          		           } else  if(officelevel.equalsIgnoreCase("CL")){
          		        	   cricleID=unit_id;
          		           
          		           }
          		           else if(officelevel.equalsIgnoreCase("HO")){
          		        	   cricleID=unit_id;
          		        	   //select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?
          		           }
          		           else {
          		        	   String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
          		            	  ps1 =  connection.prepareStatement(selectCircleID);
          		                  ps1.setInt(1, office_id);
          		                  rss1=ps1.executeQuery();
          		                  while(rss1.next()){
          		                	  circleoff=rss1.getInt("CIRCLE_OFFICE_ID");	  
          		                  } 
          		                  String selectCircleID1="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?";
          		            	  ps3 =  connection.prepareStatement(selectCircleID1);
          		                  ps3.setInt(1, circleoff);
          		                  rss3=ps3.executeQuery();
          		                  while(rss3.next()){
          		                	  cricleID=rss3.getInt("ACCOUNTING_UNIT_ID");	  
          		                  }
          		        	   
          		        	 String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
     		            	  ps1 =  connection.prepareStatement(selectCircleID);
     		                  ps1.setInt(1, office_id);
     		                  rss1=ps1.executeQuery();
     		                  while(rss1.next()){
     		                	  circleoff=rss1.getInt("CIRCLE_OFFICE_ID");	  
     		                  } 
     		                  String selectCircleID1="select ACCT_RENDERING_UNIT_ID from FAS_ASSET_VAL_AC_RENDER_UNITS where RENDERING_UNIT_OFFICE_ID=?";
     		            	  ps3 =  connection.prepareStatement(selectCircleID1);
     		                  ps3.setInt(1, office_id);
     		                  rss3=ps3.executeQuery();
     		                  while(rss3.next()){
     		                	  cricleID=rss3.getInt("ACCT_RENDERING_UNIT_ID");	  
     		                  }
          		                 
          		           } 
                      	
                            System.out.println("cricleID  "+cricleID);
                         
                  		  int assetno=Integer.parseInt(assetcode[ii]);
                            int aval_qty1=Integer.parseInt(aval_qty[ii]);
                            int crt_aval_qty1=Integer.parseInt(crt_aval_qty[ii]);
                            String PARTICULARS2=PARTICULARS1[ii];
                            int minorcode1=Integer.parseInt(minorcode[ii]);
                            int offco=Integer.parseInt(offcode[ii]);
                            String ooffNa=offName[ii];   
                          		   try 
                  		                       {        		            			   
                  		   String sqlinsert="insert into FAS_ASSETS_NUM_OB (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,DATE_OF_ENTRY,ASSET_MAJOR_CLASS_CODE,ASSET_CODE,QTY_AVL_ASON_DATE,PHYSICAL_LOCATION_CODE,STATUS,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,FINANCIAL_YEAR,QTY_AVAILABLE,CORRECTQTY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";        		            			   
              		   	   PreparedStatement ps = connection.prepareStatement(sqlinsert); 
                               ps.setInt(1,cricleID);
                               ps.setInt(2,offco);
                               ps.setDate(3,DATE_OF_ENTRY1);
                               ps.setInt(4,assetmajor);
                               ps.setInt(5,assetno);
                               ps.setInt(6,aval_qty1);
                               ps.setString(7,ooffNa);
                               ps.setString(8,"Y");
                               ps.setString(9,PARTICULARS2);
                               ps.setString(10,userid);
                               ps.setTimestamp(11,ts);
                               ps.setString(12,finyrfull);  
                               ps.setInt(13,aval_qty1);
                               ps.setInt(14,crt_aval_qty1);
                               up=ps.executeUpdate();
                               
                               System.out.println("up ..."+up);
                               cc++;
                               
           				} catch (Exception e) {
           					System.out.println("exception......in update calll send message "+e);
          					
          					connection.rollback();
                               sendMessage(response,"Failed to update Data","ok");  
          				}
           				//System.out.println("count of records updated  "+cc);
           				
                     }
             
                	  System.out.println(" cc "+cc+"  ssss "+ss);
             	       
                      // }
               	if(cc==ss)
       			{
       				connection.commit();
       				sendMessage(response,"Records inserted successfully ","ok");
       			}
       			else
       			{
       				//System.out.println(" inside    else  ");
       				connection.rollback();	
       				sendMessage(response,"Record Not Insert  ","ok");
       			} */
                	  
               try 
                 {  
            	   
            	  // String sqlselect="select 'X' from FAS_A52_REGISTER_EDIT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and FINANCIAL_YEAR=? and ASSET_MAJOR_CLASS_CODE=? and ASSET_CODE=? and HEAD_OF_ACCOUNT=?";
            	   String sqlselect="select 'X' from FAS_A52_REGISTER_EDIT where ACCOUNTING_UNIT_ID=? and FINANCIAL_YEAR=? and ASSET_MAJOR_CLASS_CODE=? and ASSET_CODE=? and HEAD_OF_ACCOUNT=?";
					PreparedStatement ps11 = connection.prepareStatement(sqlselect); 
					ps11.setInt(1,unit_id); 
					//ps11.setInt(2,office_id);  
					ps11.setString(2,financial_year);
					ps11.setInt(3,majorCoode);
					ps11.setInt(4,asset_code1);
					ps11.setString(5,headac); 
					
            	   ResultSet rs11=ps11.executeQuery();
            	   if(rs11.next()){
            		   System.out.println("inside if");
            		   connection.rollback();	
						sendMessage(response,"Record already Exists  ","ok");
            		   
            	   }else{
            		   System.out.println("inside else ");
            	   try{
            		   
            	  
					String sqlinsert="insert into FAS_A52_REGISTER_EDIT(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,FINANCIAL_YEAR,ASSET_MAJOR_CLASS_CODE,ASSET_CODE,HEAD_OF_ACCOUNT,DEPRECIATION,APPORTIONMENT,OPEN_BAL_QTY,OPENING_BAL_VALUE,RECIEPTS_YEAR_DR_QTY,RECIEPTS_YEAR_CR_QTY,RECIEPTS_YR_DR_VALUE,RECIEPTS_YR_CR_VALUE,ISSUES_YEAR_DR_QTY,ISSUES_YEAR_CR_QTY,ISSUES_YR_DR_VALUE,ISSUES_YR_CR_VALUE,DEP_PREV_YEAR,APP_PRE_YR,DEPRE_REC_AC,APP_REC_AC,DEPRE_ALLOWED_YR_DR,DEPRE_ALLOWED_YR_CR,APPRO_ALLOWED_YR_DR,APPRO_ALLOWED_YR_CR,DEPRE_TR_AC,APPRO_TR_AC,DEPRE_UPTO_DATE,APPRO_UPTO_DATE,NET_DEPRE_COST,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";        		            			   
					PreparedStatement ps = connection.prepareStatement(sqlinsert);
					
					ps.setInt(1,unit_id); 
					ps.setInt(2,office_id);  
					ps.setString(3,financial_year);
					ps.setInt(4,majorCoode);
					ps.setInt(5,asset_code1);
					ps.setString(6,headac); 
					
					ps.setString(7,depreciat);
					ps.setString(8,apport);
					ps.setInt(9,OB_Qty1);
					ps.setInt(10,OB_Value1);
					ps.setInt(11,Receipts_Dr_Qty1);
					ps.setInt(12,Receipts_Cr_Qty1);
					ps.setInt(13,Receipts_Dr_Value1);
					ps.setInt(14,Receipts_Cr_Value1);
					//ps.setInt(11,Total_Qty1);
					//ps.setInt(12,Total_Value1);  
					ps.setInt(15,Issues_Dr_Qty1);
					ps.setInt(16,Issues_Cr_Qty1);
					ps.setInt(17,Issues_Dr_Value1);
					ps.setInt(18,Issues_Cr_Value1);
					//ps.setInt(17,CB_Qty1);
					//ps.setInt(18,CB_Value1);
					ps.setInt(19,Upto_Pre_Depr1);
					ps.setInt(20,Upto_Pre_Appr1);
					ps.setInt(21,Rec_Thr_dep1);
					ps.setInt(22,Rec_Thr_app1);
					ps.setInt(23,allow_dur_dep_dr1);
					ps.setInt(24,allow_dur_dep_cr1);
					ps.setInt(25,allow_dur_app_dr1);
					ps.setInt(26,allow_dur_app_cr1);
					//ps.setInt(28,tot_Dep1);
					//ps.setInt(29,tot_App1);
					ps.setInt(27,trasf_Thr_dep1);
					ps.setInt(28,trasf_Thr_app1);
					ps.setInt(29,Upto_date_Dep1);
					ps.setInt(30,Upto_date_App1);
					ps.setInt(31,dep_Cost1);
					ps.setString(32,desc1); 
					ps.setString(33,userid);
					ps.setTimestamp(34,ts);
					up=ps.executeUpdate();
					System.out.println("up ..."+up);
            	   }catch (Exception e) {
   					System.out.println("Excep in insert "+e);
   				}
					   
            	   }
            	   if(up>0)
					{
						connection.commit();
						sendMessage(response,"Records Updated successfully ","ok");
					}
					else
					{
						//System.out.println(" inside    else  ");
						connection.rollback();	
						sendMessage(response,"Record Not Insert  ","ok");
					}    
            	   
					} catch (Exception e) {
					System.out.println("exception......in update calll send message "+e);
					
					connection.rollback();
					sendMessage(response,"Failed to update Data","ok");  
					}
					System.out.println("records updated  "+up);            	  
				   	  
					                	  
                  }else if(strCommand.equalsIgnoreCase("loadGrid")){
                	 
                	  int no=0;
                	  String xml="<response><command>"+strCommand+"</command>";
                	  try
                      {
                      	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
                      }        
                      
                      try
                      {
                      	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
                      } 
                      try
                      {
                   	   assetmajor = Integer.parseInt(request.getParameter("cmbmajorasset"));
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'major' parameter ===> " + e);
                      } 
                      try
                      {
                   	   financial_year =request.getParameter("cmbFinancialYear");
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
                      } 
                      try{
                     ps1=connection.prepareStatement("  SELECT ACCOUNTING_UNIT_ID , " +
    "   ACCOUNTING_UNIT_OFFICE_ID, " +
"      FINANCIAL_YEAR, " +
"      ASSET_MAJOR_CLASS_CODE,  ASSET_CODE    , " +
"      HEAD_OF_ACCOUNT, " +
"      DEPRECIATION, " +
"      APPORTIONMENT,OPEN_BAL_QTY, " +
"      OPENING_BAL_VALUE, " +
"      RECIEPTS_YEAR_DR_QTY, " +
"      RECIEPTS_YEAR_CR_QTY, " +
"      RECIEPTS_YR_DR_VALUE, " +
"      RECIEPTS_YR_CR_VALUE, " +
"      ISSUES_YEAR_DR_QTY, " +
"      ISSUES_YEAR_CR_QTY, " +
"      ISSUES_YR_DR_VALUE, " +
"      ISSUES_YR_CR_VALUE, " +
"      DEP_PREV_YEAR, " +
"      APP_PRE_YR, " +
"      DEPRE_REC_AC, " +
"      APP_REC_AC, " +
"      DEPRE_ALLOWED_YR_DR, " +
"      DEPRE_ALLOWED_YR_CR, " +
"      APPRO_ALLOWED_YR_DR, " +
"      APPRO_ALLOWED_YR_CR, " +
"      DEPRE_TR_AC, " +
"      APPRO_TR_AC, " +
"      DEPRE_UPTO_DATE, " +
"      APPRO_UPTO_DATE, " +
"      NET_DEPRE_COST, " +
"      REMARKS, " +
"      ASSET_MINOR_CLASS_CODE, " +
"      OFFICE_WING_SINO, " +
"      MOVED_TO_AA52, "+
"      nvl(OPEN_BAL_QTY,0)+(nvl(RECIEPTS_YEAR_CR_QTY,0)-nvl(RECIEPTS_YEAR_DR_QTY,0))-(nvl(ISSUES_YEAR_CR_QTY,0)-nvl(ISSUES_YEAR_DR_QTY,0)) as qty, " +
"      nvl(OPENING_BAL_VALUE,0)+(nvl(RECIEPTS_YR_CR_VALUE,0)-nvl(RECIEPTS_YR_DR_VALUE,0))-(nvl(ISSUES_YR_CR_VALUE,0)-nvl(ISSUES_YR_DR_VALUE,0)) as vaalue, " +
"      nvl(DEPRE_ALLOWED_YR_CR,0)-nvl(DEPRE_ALLOWED_YR_DR,0) as depre, "+
"      nvl(APPRO_ALLOWED_YR_CR,0)-nvl(APPRO_ALLOWED_YR_DR,0) as appro "+
"    FROM fas_A52_Register_edit " +
"    WHERE ACCOUNTING_UNIT_ID     =? " +
"    AND ACCOUNTING_UNIT_OFFICE_ID=? " +
"    AND FINANCIAL_YEAR           =? " +
"    AND ASSET_MAJOR_CLASS_CODE   =? "  );
               	  ps1.setInt(1, unit_id);
            	  ps1.setInt(2, office_id);
            	  ps1.setString(3, financial_year);
            	  ps1.setInt(4, assetmajor);      
                     rss1=ps1.executeQuery();
                     while(rss1.next())
                     {
                    	 xml+="<ACCOUNTING_UNIT_ID>"+rss1.getInt(1)+"</ACCOUNTING_UNIT_ID>";
                    	 xml+="<ACCOUNTING_UNIT_OFFICE_ID>"+rss1.getInt(2)+"</ACCOUNTING_UNIT_OFFICE_ID>";
                    	 xml+="<FINANCIAL_YEAR>"+rss1.getString(3)+"</FINANCIAL_YEAR>";
                    	 xml+="<ASSET_MAJOR_CLASS_CODE>"+rss1.getInt(4)+"</ASSET_MAJOR_CLASS_CODE>";
                    	 xml+="<ASSET_CODE>"+rss1.getInt(5)+"</ASSET_CODE>";
                    	 xml+="<HEAD_OF_ACCOUNT>"+rss1.getInt(6)+"</HEAD_OF_ACCOUNT>";
                    	 xml+="<DEPRECIATION>"+rss1.getBigDecimal(7)+"</DEPRECIATION>";
                    	 xml+="<APPORTIONMENT>"+rss1.getBigDecimal(8)+"</APPORTIONMENT>";
                    	 xml+="<OPEN_BAL_QTY>"+rss1.getBigDecimal(9)+"</OPEN_BAL_QTY>";
                    	 xml+="<OPENING_BAL_VALUE>"+rss1.getBigDecimal(10)+"</OPENING_BAL_VALUE>";
                    	 xml+="<RECIEPTS_YEAR_DR_QTY>"+rss1.getBigDecimal(11)+"</RECIEPTS_YEAR_DR_QTY>";
                    	 xml+="<RECIEPTS_YEAR_CR_QTY>"+rss1.getBigDecimal(12)+"</RECIEPTS_YEAR_CR_QTY>";
                    	 xml+="<RECIEPTS_YR_DR_VALUE>"+rss1.getBigDecimal(13)+"</RECIEPTS_YR_DR_VALUE>";
                    	 xml+="<RECIEPTS_YR_CR_VALUE>"+rss1.getBigDecimal(14)+"</RECIEPTS_YR_CR_VALUE>";
                    	 xml+="<ISSUES_YEAR_DR_QTY>"+rss1.getBigDecimal(15)+"</ISSUES_YEAR_DR_QTY>";
                    	 xml+="<ISSUES_YEAR_CR_QTY>"+rss1.getBigDecimal(16)+"</ISSUES_YEAR_CR_QTY>";
                    	 xml+="<ISSUES_YR_DR_VALUE>"+rss1.getBigDecimal(17)+"</ISSUES_YR_DR_VALUE>";
                    	 xml+="<ISSUES_YR_CR_VALUE>"+rss1.getBigDecimal(18)+"</ISSUES_YR_CR_VALUE>";
                    	 xml+="<DEP_PREV_YEAR>"+rss1.getBigDecimal(19)+"</DEP_PREV_YEAR>";
                    	 xml+="<APP_PRE_YR>"+rss1.getBigDecimal(20)+"</APP_PRE_YR>";
                    	 xml+="<DEPRE_REC_AC>"+rss1.getBigDecimal(21)+"</DEPRE_REC_AC>";
                    	 xml+="<APP_REC_AC>"+rss1.getBigDecimal(22)+"</APP_REC_AC>";
                    	 xml+="<DEPRE_ALLOWED_YR_DR>"+rss1.getBigDecimal(23)+"</DEPRE_ALLOWED_YR_DR>";
                    	 xml+="<DEPRE_ALLOWED_YR_CR>"+rss1.getBigDecimal(24)+"</DEPRE_ALLOWED_YR_CR>";
                    	 xml+="<APPRO_ALLOWED_YR_DR>"+rss1.getBigDecimal(25)+"</APPRO_ALLOWED_YR_DR>";
                    	 xml+="<APPRO_ALLOWED_YR_CR>"+rss1.getBigDecimal(26)+"</APPRO_ALLOWED_YR_CR>";
                    	 xml+="<DEPRE_TR_AC>"+rss1.getBigDecimal(27)+"</DEPRE_TR_AC>";
                    	 xml+="<APPRO_TR_AC>"+rss1.getBigDecimal(28)+"</APPRO_TR_AC>";
                    	 xml+="<DEPRE_UPTO_DATE>"+rss1.getBigDecimal(29)+"</DEPRE_UPTO_DATE>";
                    	 xml+="<APPRO_UPTO_DATE>"+rss1.getBigDecimal(30)+"</APPRO_UPTO_DATE>";
                    	 xml+="<NET_DEPRE_COST>"+rss1.getBigDecimal(31)+"</NET_DEPRE_COST>";
                    	 xml+="<REMARKS>"+rss1.getString(32)+"</REMARKS>";
                    	 xml+="<ASSET_MINOR_CLASS_CODE>"+rss1.getInt(33)+"</ASSET_MINOR_CLASS_CODE>";
                    	 xml+="<OFFICE_WING_SINO>"+rss1.getInt(34)+"</OFFICE_WING_SINO>";
                    	 xml+="<MOVED_TO_AA52>"+rss1.getString(35)+"</MOVED_TO_AA52>";
                    	 xml+="<qty>"+rss1.getBigDecimal(36)+"</qty>";
                    	 xml+="<vaalue>"+rss1.getBigDecimal(37)+"</vaalue>";
                    	 xml+="<depre>"+rss1.getBigDecimal(38)+"</depre>";
                    	 xml+="<appro>"+rss1.getBigDecimal(39)+"</appro>";
                    no++;
                     }if(no==0){
                    	 xml+="<flag>failure</flag>";
                     }else
                     {
                    	 xml+="<flag>success</flag>";
                     }
                      }catch(Exception e)
                      {
                    	  xml+="<flag>failure</flag>";
                    	  e.printStackTrace();
                      }
                      xml+="</response>";
                      pw.write(xml);
                      System.out.println(":::: "+xml);
                      pw.close();
                  }
           
           
           else if(strCommand.equalsIgnoreCase("AddEdit")){
                		Date tranferDte=null;
    					Date closureDte=null;
    				java.util.Date cDte=null;
    				java.util.Date tDte=null;
    					        Calendar c;     
                	  try
                      {
                      	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
                      }        
                      
                      try
                      {
                      	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
                      }  try
                      {
                    	  circle_ID = Integer.parseInt(request.getParameter("circle_ID").split("-")[1]);
                        }
                        catch(Exception e)
                        { 
                            System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
                        } 
                        try
                        {
                        	cmb_Vendor = Integer.parseInt(request.getParameter("cmb_Vendor"));
                          }
                          catch(Exception e)
                          { 
                              System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
                          } 
                      try
                      {
                   	   assetmajor = Integer.parseInt(request.getParameter("cmbmajorasset"));
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'major' parameter ===> " + e);
                      } 
                      try
                      {
                   	   financial_year =request.getParameter("cmbFinancialYear");
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
                      } 
                      try
                      {
                 String d[]=request.getParameter("closureDte").split("/");
                c=new GregorianCalendar(Integer.parseInt(d[2]),Integer.parseInt(d[1]), Integer.parseInt(d[0]));
                   	cDte=c.getTime();
                   	closureDte=new Date(cDte.getTime());
                //financial_year =request.getParameter("closureDte");
                    System.out.println("closureDte"+closureDte);
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'closureDte' parameter ===> " + e);
                      } 
                      try
                      {
                   	  String sd1[]=request.getParameter("tranferDte").split("/");
                   	  c=new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]), Integer.parseInt(sd1[0]));
                      tDte=c.getTime();
                   	  tranferDte=new Date(tDte.getTime());
                   	  System.out.println("tranferDte"+tranferDte);
                      }
                      catch(Exception e)
                      { 
                          System.out.println("IGNORABLE Exception getting 'tranferDte' parameter ===> " + e);
                      } 
                      int asset_Code=0;
                      int no=0;
                      
                      try{
                    	  System.out.println("**********************");
                    	  PreparedStatement ps2=connection.prepareStatement("select ASSET_CODE  FROM fas_A52_Register_edit " +
                    			  "    WHERE ACCOUNTING_UNIT_ID     =? " +
                    			  "    AND ACCOUNTING_UNIT_OFFICE_ID=? " +
                    			  "    AND FINANCIAL_YEAR           =? " +
                    			  "    AND ASSET_MAJOR_CLASS_CODE   =? ");
                    	  ps2.setInt(1, unit_id);
                    	  ps2.setInt(2, office_id);
                    	  ps2.setString(3, financial_year);
                    	  ps2.setInt(4, assetmajor);
                    			                      	  ResultSet rs2=ps2.executeQuery();
                    			                      	  while (rs2.next()) {
                    			                      		 System.out.println(rs2.getInt(1));
                    			  					
                    	  
                      try{
                    	 
                    	  PreparedStatement p=connection.prepareStatement("select max(ASSET_CODE) cno  FROM fas_A52_Register_edit " +
"    WHERE ACCOUNTING_UNIT_ID     =? " +
"    AND ACCOUNTING_UNIT_OFFICE_ID=? " +
"    AND FINANCIAL_YEAR           =? " +
"    AND ASSET_MAJOR_CLASS_CODE   =? ");
                    	  p.setInt(1, cmb_Vendor);
                    	  p.setInt(2, circle_ID);
                    	  p.setString(3, financial_year);
                    	  p.setInt(4, assetmajor);
                    	  ResultSet r=p.executeQuery();
                    	  if (r.next()) {
                    		  System.out.println(r.getInt(1));
						asset_Code=r.getInt(1)+1;
							
						}else{
							asset_Code=1;
						}
                    	  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. "+asset_Code);
                      }catch(Exception e){
                    	  e.printStackTrace();
                      }
                     
                      try{
                    	  ps1=connection.prepareStatement("INSERT " +
"INTO fas_A52_Register_edit " +
"  ( " +
"    ACCOUNTING_UNIT_ID, " +
"    ACCOUNTING_UNIT_OFFICE_ID, " +
"    FINANCIAL_YEAR, " +
"    ASSET_MAJOR_CLASS_CODE, " +
"    ASSET_CODE, " +
"    HEAD_OF_ACCOUNT, " +
"    DEPRECIATION, " +
"    APPORTIONMENT, " +
"    OPEN_BAL_QTY, " +
"    OPENING_BAL_VALUE, " +
"    RECIEPTS_YEAR_DR_QTY, " +
"    RECIEPTS_YEAR_CR_QTY, " +
"    RECIEPTS_YR_DR_VALUE, " +
"    RECIEPTS_YR_CR_VALUE, " +
"    ISSUES_YEAR_DR_QTY, " +
"    ISSUES_YEAR_CR_QTY, " +
"    ISSUES_YR_DR_VALUE, " +
"    ISSUES_YR_CR_VALUE, " +
"    DEP_PREV_YEAR, " +
"    APP_PRE_YR, " +
"    DEPRE_REC_AC, " +
"    APP_REC_AC, " +
"    DEPRE_ALLOWED_YR_DR, " +
"    DEPRE_ALLOWED_YR_CR, " +
"    APPRO_ALLOWED_YR_DR, " +
"    APPRO_ALLOWED_YR_CR, " +
"    DEPRE_TR_AC, " +
"    APPRO_TR_AC, " +
"    DEPRE_UPTO_DATE, " +
"    APPRO_UPTO_DATE, " +
"    NET_DEPRE_COST, " +
"    REMARKS, " +
"    UPDATED_BY_USERID, " +
"    UPDATED_DATE, " +
"    ASSET_MINOR_CLASS_CODE, " +
"    OFFICE_WING_SINO, " +
"    MOVED_TO_AA52,DATE_OF_TRANSFER,DATE_OF_CLOSURE,TRANSFER_REMARKS " +
"  ) " +
"  (SELECT "+cmb_Vendor+" AS ACCOUNTING_UNIT_ID , " +
     circle_ID+"  AS ACCOUNTING_UNIT_OFFICE_ID, " +
"      FINANCIAL_YEAR, " +
"      ASSET_MAJOR_CLASS_CODE, " +asset_Code+
"    , " +
"      HEAD_OF_ACCOUNT, " +
"      DEPRECIATION, " +
"      APPORTIONMENT, " +
"      OPEN_BAL_QTY, " +
"      OPENING_BAL_VALUE, " +
"      RECIEPTS_YEAR_DR_QTY, " +
"      RECIEPTS_YEAR_CR_QTY, " +
"      RECIEPTS_YR_DR_VALUE, " +
"      RECIEPTS_YR_CR_VALUE, " +
"      ISSUES_YEAR_DR_QTY, " +
"      ISSUES_YEAR_CR_QTY, " +
"      ISSUES_YR_DR_VALUE, " +
"      ISSUES_YR_CR_VALUE, " +
"      DEP_PREV_YEAR, " +
"      APP_PRE_YR, " +
"      DEPRE_REC_AC, " +
"      APP_REC_AC, " +
"      DEPRE_ALLOWED_YR_DR, " +
"      DEPRE_ALLOWED_YR_CR, " +
"      APPRO_ALLOWED_YR_DR, " +
"      APPRO_ALLOWED_YR_CR, " +
"      DEPRE_TR_AC, " +
"      APPRO_TR_AC, " +
"      DEPRE_UPTO_DATE, " +
"      APPRO_UPTO_DATE, " +
"      NET_DEPRE_COST, " +
"      REMARKS, " +
"    ?, " +
"    ?, " +
"      ASSET_MINOR_CLASS_CODE, " +
"      OFFICE_WING_SINO, " +
"      MOVED_TO_AA52 ,?,?,?" +
"    FROM fas_A52_Register_edit " +
"    WHERE ACCOUNTING_UNIT_ID     =? " +
"    AND ACCOUNTING_UNIT_OFFICE_ID=? " +
"    AND FINANCIAL_YEAR           =? " +
"    AND ASSET_MAJOR_CLASS_CODE   =?  and ASSET_CODE=? " +
"  )"
);
					ps1.setString(1, userid);
					ps1.setTimestamp(2, ts);
					ps1.setDate(3,tranferDte);
					ps1.setDate(4,closureDte);
					ps1.setString(5,"Transfer from "+unit_id +" to "+cmb_Vendor);
					ps1.setInt(6, unit_id);
					ps1.setInt(7, office_id);
					ps1.setString(8, financial_year);
					ps1.setInt(9, assetmajor);System.out.println("rs2.getInt(1) >> "+rs2.getInt(1));
					ps1.setInt(10, rs2.getInt(1));
				
                    	  int kk=ps1.executeUpdate();
                    	  System.out.println("kk ********** "+kk);
                    	  if(kk==0)
                    	  {  connection.rollback();
                    		  sendMessage(response, "Insertion Failed...", "OK");}
                    	  else{
                    			connection.commit();
                    		  sendMessage(response, "Successfully Inserted...", "OK");}
                      }catch(Exception e)
                      {	connection.rollback();
                      e.printStackTrace();
                      }
                      no++;
                      }
                    			                      	 if(no==0)
                    			                           	  sendMessage(response, "No Data Found .. ", "OK");
                    			                            // System.out.println("asset_Code >> "+asset_Code);
                    
                      }catch(Exception e){
                    	  e.printStackTrace();  
                      }
                      
                  }
            }catch (Exception e) {
				System.out.println("error in checking");
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

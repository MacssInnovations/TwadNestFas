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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class A52_Register_OB_list
 */
public class A52_Register_OB_list extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public A52_Register_OB_list() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        ResultSet result1=null;
        PreparedStatement pers=null;
   	 PreparedStatement ps=null;
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
	   	//String date_transaction = null;
	   	String financial_year = null;
	   	int asset_code = 0;
	   /*	int txtQ1  = 0;
	   	int txtQ2  = 0;
                int txtQ3  =  0;
                int txtV1  = 0;
                int txtV2  = 0;
                int txtV3  =  0;
                String appor_date=null;
	   	int txtQ_total = 0;
	   	int txtV_total = 0;
	   	
	   	int txtdepre_prev_yr = 0;
	   	int txtdepre_recieved  = 0;
	   	int txtdepre_allowed_yr  = 0;
	   	int  txttotal_depre   =0;
	        int dep_transfer  = 0;
	   	String txtdepre_date  = "";
	   	int txtnet_depre  = 0;
	   	int txtappor_grant  = 0;
	   	int txtappor_recieved  = 0;
	   	int aoor_a110wed  = 0;
	   	int txttotal_appor  = 0;
	   	int txtapp_transfer  = 0;
       int txtdepre_date2=0,txtnet_depre2=0,txtappor_grant2=0,txtappor_recieved2=0,txtappor_allowed2=0,txttotal_appor2=0;*/
	   	String remarks = "";
	   	
	   	
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
        int employee_id = 0;

      
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");
        System.out.println("user id::" + empProfile.getEmployeeId());
        employee_id = empProfile.getEmployeeId();
        
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
        	System.out.println("strCommand : " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
     
        try
        {
        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
        	System.out.println("accounting_unit_id : " + unit_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
        }        
        
        try
        {
        	office_id = Integer.parseInt(request.getParameter("office_id"));
        	System.out.println("accounting_unit_office_id : " + office_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
        }     
        try
        {
        	financial_year = request.getParameter("financial_year");
        	System.out.println("financial_year : " + financial_year);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
        }     
        
        try
        {
        	asset_code = Integer.parseInt(request.getParameter("asset_code"));
        	System.out.println("asset_code : " + asset_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'asset_code' parameter ===> " + e);
        }        
        
    
        try
        {
        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        	System.out.println("assetmajor : " + assetmajor);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'remarks' parameter ===> " + e);
        }     
      
     
        if(strCommand.equals("GoGet"))
        { 
        	PreparedStatement ps11=null;
        	int count=0;
        	System.out.println("\n*************\nGo Get\n**************\n");
            xml="<response><command>GoGet</command>";
            try 
            {
            	String officelevel="";
            	String units="";
            	int offid=0;
                System.out.println("goget");
               String ss="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+unit_id;
               PreparedStatement ps2=connection.prepareStatement(ss);
	           ResultSet rs1=ps2.executeQuery();
	           while(rs1.next()){
	        	   offid=rs1.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	           }
	           String ssqql="";
               int oldofficeid=0;
	           //find reployement or not....
	          /* String ssq="select REDEPLOYED_OFFICE_ID from COM_OFFICE_REDEPLOYMENTS where NEW_OFFICE_ID="+offid;
	           PreparedStatement ps20=connection.prepareStatement(ssq);
	           ResultSet rs11=ps20.executeQuery();
	           if(rs11.next()){
	        	   ResultSet rs112=ps20.executeQuery();
	        	   while(rs112.next()){
	        		   
	        	   }
	        	   
	           }else{
	        	   
	        	   
	           }*/ 
               
                String selectQry="select office_level_id from com_mst_all_offices_view  where office_id="+offid;
		           PreparedStatement ps1=connection.prepareStatement(selectQry);
		           ResultSet rs=ps1.executeQuery();
		           while(rs.next()){
		        	   officelevel=rs.getString("office_level_id");
		           }
		           System.out.println(" officelevel "+officelevel);
		           if(officelevel.equalsIgnoreCase("DN")){
		        	  System.out.println("if ");
		        	   //units=" and a.accounting_unit_office_id in (select office_id from com_mst_all_offices_view  where division_office_id="+office_id+")"  ;
		        	  ssqql="SELECT DECODE(OPEN_BAL_QTY,NULL,0,OPEN_BAL_QTY)     AS OPEN_BAL_QTY , " +
	               		 "  DECODE(OPENING_BAL_VALUE,NULL,0,OPENING_BAL_VALUE)AS OPENING_BAL_VALUE , " +
	               		 "  DECODE(RECIEPTS_YEAR_QTY,NULL,0,RECIEPTS_YEAR_QTY)AS RECIEPTS_YEAR_QTY , " +
	               		 "  DECODE(RECIEPTS_YR_VALUE,NULL,0,RECIEPTS_YR_VALUE)AS RECIEPTS_YR_VALUE , " +
	               		 "  DECODE(ISSUES_YEAR_QTY,NULL,0,ISSUES_YEAR_QTY)    AS ISSUES_YEAR_QTY , " +
	               		 "  DECODE(ISSUES_YR_VALUE,NULL,0,ISSUES_YR_VALUE)    AS ISSUES_YR_VALUE ,\n"+
	               		 "  DECODE(N_OPEN_BAL_QTY,NULL,0,N_OPEN_BAL_QTY)     AS N_OPEN_BAL_QTY , " +
	               		 "  DECODE(N_OPENING_BAL_VALUE,NULL,0,N_OPENING_BAL_VALUE)AS N_OPENING_BAL_VALUE , " +
	               		 "  DECODE(N_RECIEPTS_YEAR_QTY,NULL,0,N_RECIEPTS_YEAR_QTY)AS N_RECIEPTS_YEAR_QTY , " +
	               		 "  DECODE(N_RECIEPTS_YR_VALUE,NULL,0,N_RECIEPTS_YR_VALUE)AS N_RECIEPTS_YR_VALUE , " +
	               		 "  DECODE(N_ISSUES_YEAR_QTY,NULL,0,N_ISSUES_YEAR_QTY)    AS N_ISSUES_YEAR_QTY , " +
	               		 "  DECODE(N_ISSUES_YR_VALUE,NULL,0,N_ISSUES_YR_VALUE)    AS N_ISSUES_YR_VALUE ,\n"+
	               		 "DEP_PREV_YEAR,DEPRE_REC_AC,SYSTEM_REMARKS,DEPRE_ALLOWED_YR,\n"+
	               		 "DEPRE_TR_AC,DEPRE_UPTO_DATE,accounting_unit_id,NET_DEPRE_COST,APP_PRE_YR,APP_GRANT_RECIEVED,APPRO_DURING_YR,\n"+
	               		 "APP_GRANT_TR,APP_GRANT_UPTODATE,REMARKS,accounting_unit_id,\n"+
	               		 "accounting_unit_office_id,financial_year,asset_code,ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE,PARTICULARS,\n" + 
	               		 " (SELECT m.ASSET_MINOR_CLASS_DESC "+
	               					" FROM FAS_ASSET_MINOR_CLASSIFICATION m "+
	               					" WHERE m.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND m.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE "+
	               					" )"+ 
	               					"AS ASSET_MINOR_CLASS_DESC, "+
	               					" (select OFFICE_NAME from com_mst_offices where OFFICE_ID=a.accounting_unit_office_id)as offName"+
	                " from FAS_A52_REGISTER a" +
	                " WHERE  " + //accounting_unit_id = " + unit_id +" and  "+
	              //  "( a.accounting_unit_office_id in (select redeployed_office_id from com_office_redeployments where new_office_id="+offid+" ) "+
		            //   " or a.accounting_unit_office_id in (select office_id from com_mst_all_offices_view  where division_office_id="+offid+" ))"+
		            //   "( a.accounting_unit_office_id in (select redeployed_office_id from com_office_redeployments where new_office_id="+offid+" ) "+
		            //   " or a.accounting_unit_office_id in (select office_id from com_mst_all_offices_view  where division_office_id="+offid+" ))"+
	              // " a.accounting_unit_office_id in (select office_id from com_mst_all_offices_view  where division_office_id="+offid+" ) "+
	               "( a.accounting_unit_office_id in (select office_id from com_mst_all_offices_view  where division_office_id="+offid+" ) or a.accounting_unit_office_id="+office_id+" ) "+
	   	     " AND financial_year = '"+financial_year+"'" +
	   	     " AND ASSET_MAJOR_CLASS_CODE="+assetmajor;
		        		        	   
		           }else{
		        	   System.out.println("else  ");
		        	   
		        	  ssqql="SELECT DECODE(OPEN_BAL_QTY,NULL,0,OPEN_BAL_QTY)     AS OPEN_BAL_QTY , " +
	               		 "  DECODE(OPENING_BAL_VALUE,NULL,0,OPENING_BAL_VALUE)AS OPENING_BAL_VALUE , " +
	               		 "  DECODE(RECIEPTS_YEAR_QTY,NULL,0,RECIEPTS_YEAR_QTY)AS RECIEPTS_YEAR_QTY , " +
	               		 "  DECODE(RECIEPTS_YR_VALUE,NULL,0,RECIEPTS_YR_VALUE)AS RECIEPTS_YR_VALUE , " +
	               		 "  DECODE(ISSUES_YEAR_QTY,NULL,0,ISSUES_YEAR_QTY)    AS ISSUES_YEAR_QTY , " +
	               		 "  DECODE(ISSUES_YR_VALUE,NULL,0,ISSUES_YR_VALUE)    AS ISSUES_YR_VALUE ,\n"+
	               		 "  DECODE(N_OPEN_BAL_QTY,NULL,0,N_OPEN_BAL_QTY)     AS N_OPEN_BAL_QTY , " +
	               		 "  DECODE(N_OPENING_BAL_VALUE,NULL,0,N_OPENING_BAL_VALUE)AS N_OPENING_BAL_VALUE , " +
	               		 "  DECODE(N_RECIEPTS_YEAR_QTY,NULL,0,N_RECIEPTS_YEAR_QTY)AS N_RECIEPTS_YEAR_QTY , " +
	               		 "  DECODE(N_RECIEPTS_YR_VALUE,NULL,0,N_RECIEPTS_YR_VALUE)AS N_RECIEPTS_YR_VALUE , " +
	               		 "  DECODE(N_ISSUES_YEAR_QTY,NULL,0,N_ISSUES_YEAR_QTY)    AS N_ISSUES_YEAR_QTY , " +
	               		 "  DECODE(N_ISSUES_YR_VALUE,NULL,0,N_ISSUES_YR_VALUE)    AS N_ISSUES_YR_VALUE ,\n"+
	               		 "DEP_PREV_YEAR,DEPRE_REC_AC,SYSTEM_REMARKS,DEPRE_ALLOWED_YR,\n"+
	               		 "DEPRE_TR_AC,DEPRE_UPTO_DATE,accounting_unit_id,NET_DEPRE_COST,APP_PRE_YR,APP_GRANT_RECIEVED,APPRO_DURING_YR,\n"+
	               		 "APP_GRANT_TR,APP_GRANT_UPTODATE,REMARKS,accounting_unit_id,\n"+
	               		 "accounting_unit_office_id,financial_year,asset_code,ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE,PARTICULARS,\n" + 
	               		 " (SELECT m.ASSET_MINOR_CLASS_DESC "+
	               					" FROM FAS_ASSET_MINOR_CLASSIFICATION m "+
	               					" WHERE m.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND m.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE "+
	               					" )"+ 
	               					"AS ASSET_MINOR_CLASS_DESC ,"+
	               					"(select OFFICE_NAME from com_mst_offices where OFFICE_ID=a.accounting_unit_office_id)as offName"+
	                " from FAS_A52_REGISTER a" +
	                " WHERE  "+//accounting_unit_id = " + unit_id +" AND "+
	               // "( a.accounting_unit_office_id in (select redeployed_office_id from com_office_redeployments where new_office_id="+offid+" ) "+
	              // " or a.accounting_unit_office_id ="+offid+" )"+
	   	     "   accounting_unit_office_id = "+ office_id +
	   	     " AND financial_year = '"+financial_year+"'" +
	   	     " AND ASSET_MAJOR_CLASS_CODE="+assetmajor;
		        	   //units=" and a.accounting_unit_office_id="+office_id;
		        	    //result = statement.executeQuery();
		        	  
		        	  
		        	   
		           }
		           System.out.println(ssqql);
           try
             {
        	   ps11=connection.prepareStatement(ssqql); 
        	   result=ps11.executeQuery();
        	 //  result = ps11.executeQuery();
        	   System.out.println("inside try");
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<asset_code>" + result.getString("asset_code") + "</asset_code>";
                	 xml=xml+"<accounting_unit_office_id>" + result.getInt("accounting_unit_office_id") + "</accounting_unit_office_id>";
                	 xml=xml+"<offName>" + result.getString("offName").trim() + "</offName>";
                	 xml += "<PARTICULARS><![CDATA[" + result.getString("PARTICULARS") + "]]></PARTICULARS>"; 
                     xml=xml+"<OPEN_BAL_QTY>" + result.getInt("OPEN_BAL_QTY") + "</OPEN_BAL_QTY>";
                     xml=xml+"<OPENING_BAL_VALUE>" + result.getInt("OPENING_BAL_VALUE") +"</OPENING_BAL_VALUE>";
                     xml=xml+"<RECIEPTS_YEAR_QTY>" + result.getInt("RECIEPTS_YEAR_QTY") + "</RECIEPTS_YEAR_QTY>";
                     xml=xml+"<RECIEPTS_YR_VALUE>" + result.getInt("RECIEPTS_YR_VALUE") + "</RECIEPTS_YR_VALUE>";
                     xml=xml+"<ISSUES_YEAR_QTY>" + result.getInt("ISSUES_YEAR_QTY") + "</ISSUES_YEAR_QTY>";
                     xml=xml+"<ISSUES_YR_VALUE>" + result.getInt("ISSUES_YR_VALUE") + "</ISSUES_YR_VALUE>";
                     
                     xml=xml+"<N_OPEN_BAL_QTY>" + result.getInt("N_OPEN_BAL_QTY") + "</N_OPEN_BAL_QTY>";
                     xml=xml+"<N_OPENING_BAL_VALUE>" + result.getInt("N_OPENING_BAL_VALUE") +"</N_OPENING_BAL_VALUE>";
                     xml=xml+"<N_RECIEPTS_YEAR_QTY>" + result.getInt("N_RECIEPTS_YEAR_QTY") + "</N_RECIEPTS_YEAR_QTY>";
                     xml=xml+"<N_RECIEPTS_YR_VALUE>" + result.getInt("N_RECIEPTS_YR_VALUE") +"</N_RECIEPTS_YR_VALUE>";
                     xml=xml+"<N_ISSUES_YEAR_QTY>" + result.getInt("N_ISSUES_YEAR_QTY") + "</N_ISSUES_YEAR_QTY>";
                     xml=xml+"<N_ISSUES_YR_VALUE>" + result.getInt("N_ISSUES_YR_VALUE") + "</N_ISSUES_YR_VALUE>";
                     xml=xml+"<DEP_PREV_YEAR>" + result.getInt("DEP_PREV_YEAR") + "</DEP_PREV_YEAR>";
                     xml=xml+"<DEPRE_REC_AC>" + result.getInt("DEPRE_REC_AC") + "</DEPRE_REC_AC>";
                     xml=xml+"<DEPRE_ALLOWED_YR>" + result.getInt("DEPRE_ALLOWED_YR") + "</DEPRE_ALLOWED_YR>";
                     xml=xml+"<DEPRE_TR_AC>" + result.getInt("DEPRE_TR_AC") + "</DEPRE_TR_AC>";
                     xml=xml+"<DEPRE_UPTO_DATE>" + result.getInt("DEPRE_UPTO_DATE") + "</DEPRE_UPTO_DATE>";
                     xml=xml+"<NET_DEPRE_COST>" + result.getInt("NET_DEPRE_COST") +"</NET_DEPRE_COST>";
                     xml=xml+"<APP_PRE_YR>" + result.getString("APP_PRE_YR") + "</APP_PRE_YR>";
                     xml=xml+"<APP_GRANT_RECIEVED>" + result.getInt("APP_GRANT_RECIEVED") + "</APP_GRANT_RECIEVED>";
                     xml=xml+"<APPRO_DURING_YR>" + result.getInt("APPRO_DURING_YR") + "</APPRO_DURING_YR>";
                     xml=xml+"<APP_GRANT_TR>" + result.getInt("APP_GRANT_TR") + "</APP_GRANT_TR>";
                     xml=xml+"<APP_GRANT_UPTODATE>" + result.getInt("APP_GRANT_UPTODATE") + "</APP_GRANT_UPTODATE>"; 
                     xml=xml+"<ASSET_MAJOR_CLASS_CODE>"+ result.getInt("ASSET_MAJOR_CLASS_CODE") +"</ASSET_MAJOR_CLASS_CODE>";
 					xml=xml+"<ASSETMINORCLASSCODE1>"+ result.getInt("ASSET_MINOR_CLASS_CODE") +"</ASSETMINORCLASSCODE1>";
 					xml=xml+"<ASSET_MINOR_CLASS_DESC>"+ result.getString("ASSET_MINOR_CLASS_DESC") +"</ASSET_MINOR_CLASS_DESC>";
 					xml=xml+"<accounting_unit_id>"+ result.getString("accounting_unit_id") +"</accounting_unit_id>";
 					
 					remarks = result.getString("SYSTEM_REMARKS");
                	// System.out.println("asss  minor code  "+result.getString("ASSET_MINOR_CLASS_CODE"));
                	 if(remarks == null)
                	 {
                		 remarks = " ";
                	 }
                	 xml =xml+ "<remarks><![CDATA[" + remarks + "]]></remarks>";
                	/* String qry="select ASSET_MINOR_CLASS_CODE as code,ASSET_MINOR_CLASS_DESC as des from FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+assetmajor;
                	 //result1 = statement.executeQuery("select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+assetmajor+" order by ASSET_MINOR_CLASS_CODE ");
                	 pers=connection.prepareStatement(qry);
     				System.out.println(qry);
     				result1=pers.executeQuery();
                	 while(result1.next())
                     { 
                    	 //valExists = "Yes";
                    	 xml=xml+"<MinorCode"+count+">";
                    	 xml=xml+ "<MINOR_CLASS_CODE>" + result1.getInt("code") + "</MINOR_CLASS_CODE>";
                    	 xml=xml+ "<MINOR_CLASS_DESC><![CDATA[" + result1.getString("des") + "]]></MINOR_CLASS_DESC>";
                    	 xml=xml+"</MinorCode"+count+">";
                    	// System.out.println("code minor "+result1.getInt("code"));
                     }
                	 */
                	 
                	
                	 count++;
                 }

                 xml =xml+ "<exists>"+valExists+"</exists>";
                 xml =xml+ "<count>"+count+"</count>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from DB - Go GET: " + e);
             }
             
            // result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();
        } 
       
        else if(strCommand.equals("loadMajor"))
        { 
        	System.out.println("\n*************\nloadMajor\n**************\n");
            xml="<response><command>loadMajor</command>";
            try 
            {
                /*System.out.println("bef res");
              System.out.println(unit_id);
              System.out.println(office_id);
              System.out.println(financial_year);
              System.out.println(asset_code);*/
             result = statement.executeQuery("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE");
             
                //System.out.println("aft res");
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
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();
        }  
       /* else if(strCommand.equals("loadMinor"))
        { 
        	System.out.println("\n*************\nloadMinor\n**************\n");
            xml="<response><command>loadMinor</command>";
           assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
            try 
            {
                
             result = statement.executeQuery("select ASSET_MINOR_CLASS_CODE,ASSET_MINOR_CLASS_DESC from FAS_ASSET_MINOR_CLASSIFICATION where ASSET_MAJOR_CLASS_CODE="+assetmajor+" order by ASSET_MINOR_CLASS_CODE");
             
                //System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_MINOR_CLASS_CODE>" + result.getInt("ASSET_MINOR_CLASS_CODE") + "</ASSET_MINOR_CLASS_CODE>";
                	 xml += "<ASSET_MINOR_CLASS_DESC><![CDATA[" + result.getString("ASSET_MINOR_CLASS_DESC") + "]]></ASSET_MINOR_CLASS_DESC>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from ASSET_MINOR_CLASS_DESC: " + e);
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
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();
        } */
        else if(strCommand.equals("checkVerifyA52"))
        { 
        	System.out.println("\n*************\n checkVerifyA52 \n**************\n");
            xml="<response><command>checkVerifyA52</command>";
            try 
            {
                /*System.out.println("bef res");
              System.out.println(unit_id);
              System.out.println(office_id);
              System.out.println(financial_year);
              System.out.println(asset_code);*/
             result = statement.executeQuery("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,A52_STATUS from FAS_A52_VERIFY_STATUS where ACCOUNTING_UNIT_ID="+unit_id+" and ACCOUNTING_FOR_OFFICE_ID="+office_id+"");
             
                //System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ACCOUNTING_UNIT_ID>" + result.getInt("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
                	 xml += "<A52_STATUS>" + result.getString("A52_STATUS") + "</A52_STATUS>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from fas a52 verify: " + e);
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
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();
        }  
        else if(strCommand.equals("checkVerifyA52Status"))
        { 
        	System.out.println("\n*************\n checkVerifyA52 \n**************\n");
            xml="<response><command>checkVerifyA52</command>";
            try 
            {
                /*System.out.println("bef res");
              System.out.println(unit_id);
              System.out.println(office_id);//ACCOUNTING_UNIT_ID="+unit_id+" and 
              System.out.println(financial_year);
              System.out.println(asset_code);*/
             result = statement.executeQuery("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,A52_STATUS_QTY from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID="+office_id+" and FINANCIAL_YEAR='"+financial_year+"'");
             
                //System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ACCOUNTING_UNIT_ID>" + result.getInt("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
                	 xml += "<A52_STATUS>" + result.getString("A52_STATUS_QTY") + "</A52_STATUS>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from fas a52 verify: " + e);
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
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();
        } 
 else if (strCommand.equalsIgnoreCase("LoadUnitWise_OfficeRedeploy")) {
            xml = "<response><command>" + strCommand + "</command>";

           
            try {
            	//unit_id
            	int offid1=0;
            	 int cnt = 0;
              
               String ss="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+unit_id;
             
               PreparedStatement ps2=connection.prepareStatement(ss);
	           ResultSet rs1=ps2.executeQuery();
	           if(rs1.next()){
	        	   offid1=rs1.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	           }         
	           
            	 //find reployement or not....
  	          String ssq="select REDEPLOYED_OFFICE_ID from COM_OFFICE_REDEPLOYMENTS where NEW_OFFICE_ID="+offid1;
  	       // System.out.println(ssq);
  	           PreparedStatement ps20=connection.prepareStatement(ssq);
  	           ResultSet rs11=ps20.executeQuery();
  	           if(rs11.next()){
  	        	   String ssq2="select offid, "+
					" (SELECT accounting_unit_id FROM FAS_MST_ACCT_UNITS WHERE accounting_unit_office_id=offid )as unit_id,"+
					" (SELECT accounting_unit_name FROM FAS_MST_ACCT_UNITS WHERE accounting_unit_office_id=offid )as unit_name"+
					" from "+
					" (SELECT REDEPLOYED_OFFICE_ID as offid"+
					" FROM com_office_redeployments"+
					" WHERE ACCT_TRF_UNIT_ID="+unit_id+
					" union all"+
					" SELECT ACCOUNTING_UNIT_OFFICE_ID as offid"+
					" FROM FAS_MST_ACCT_UNITS "+
					" WHERE ACCOUNTING_UNIT_ID="+unit_id+") ";
  	        	 System.out.println(ssq2);
  	        	 PreparedStatement ps211=connection.prepareStatement(ssq2);
  	        	   ResultSet rs112=ps211.executeQuery();
  	        	   while(rs112.next()){
  	        		 
  	        		   xml =
  	          			 xml + "<offid>" + rs112.getInt("offid") + "</offid>";
  	          			                    xml =
  	          			 xml + "<offname>" + rs112.getString("unit_name") + "</offname>";
  	          			                    cnt++;   
  	        		   
  	        	   }
  	        	   
  	           }else{

  	                String ss1="select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
  	                      "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID= "+unit_id;
  	                System.out.println(ss1);
  	                    ps =connection.prepareStatement(ss1);
  	               ResultSet rs10 = ps.executeQuery();
  	               

  	                while (rs10.next()) {
  			                    xml = xml + "<offid>" + rs10.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
  			                    xml = xml + "<offname>" + rs10.getString("OFFICE_NAME") + "</offname>";
  			                    cnt++;
  	                }
  	               
  	        	   
  	           } 
            	
                if (cnt != 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();

        }
        else if (strCommand.equalsIgnoreCase("loadUnitRendering")) {
			//System.out.println("inside loadUnitRendering...");
			int count=0;
			xml = "<response><command>unitLoad</command>";
			
			try {
				//ps = connection.prepareStatement("select distinct ACCT_UNIT_ID_RENDERED_FOR,(select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=ACCT_UNIT_ID_RENDERED_FOR)as unit_name from FAS_ASSET_VAL_AC_RENDER_UNITS where ACCT_RENDERING_UNIT_ID='"+unitid+"'");
				ps = connection.prepareStatement("SELECT ACCT_UNIT_ID_RENDERED_FOR,RENDERING_UNIT_OFFICE_ID,(SELECT OFFICE_NAME FROM COM_MST_OFFICES WHERE OFFICE_ID=RENDERING_UNIT_OFFICE_ID)AS unit_name from fas_asset_val_ac_render_units WHERE ACCT_RENDERING_UNIT_ID="+unit_id);
				result = ps.executeQuery();
				while (result.next()) {
					xml = xml + "<unit_No>"+ result.getString("RENDERING_UNIT_OFFICE_ID")+ "</unit_No>";
					xml=xml+"<desc>"+result.getString("unit_name").trim()+"("+result.getString("RENDERING_UNIT_OFFICE_ID")+")"+"</desc>";
					count++;
				
				}
				xml = xml + "<count>"+count+"</count>";
				if(count==0){
					xml = xml + "<flag>failure</flag>";
				}else{
					xml = xml + "<flag>success</flag>";
					System.out.println("count"+count);

					
				}
								
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			 xml=xml+"</response>";
	            System.out.println("xml is : " + xml);
	            pw.write(xml);
	            pw.flush();
	            pw.close();
		}
        /*else if(strCommand.equals("loadAssetCode"))
        { 
        	System.out.println("\n*************\n  loadAssetCode \n**************\n");
            xml="<response><command>loadAssetCode</command>";
            assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
           // String unit_id=request.getParameter("unit_id");
            assetminor = Integer.parseInt(request.getParameter("assetminor"));
            
            
            try 
            {
                
             result = statement.executeQuery("select ASSET_CODE from FAS_A52_REGISTER where ACCOUNTING_UNIT_ID="+unit_id+" and ACCOUNTING_UNIT_OFFICE_ID="+office_id+" and ASSET_MAJOR_CLASS_CODE="+assetmajor+" and ASSET_MINOR_CLASS_CODE="+assetminor+" order by ASSET_CODE");
             
                //System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_CODE>" + result.getInt("ASSET_CODE") + "</ASSET_CODE>";
                	// xml += "<ASSET_MINOR_CLASS_DESC>" + result.getString("ASSET_MINOR_CLASS_DESC") + "</ASSET_MINOR_CLASS_DESC>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from ASSET_CODE: " + e);
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
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();
        }  */
       /* else if(strCommand.equalsIgnoreCase("updateTotally")){
        	try{
        	int up=0;
        
     	System.out.println("update totally");
     	String[] assetMinorCode=request.getParameterValues("assMinorCode");
     	String[] assetcode=request.getParameterValues("assetcode");
     	String[] PARTICULARS=request.getParameterValues("PARTICULARS");
     	String[] OPEN_BAL_QTY=request.getParameterValues("OPEN_BAL_QTY");
     	String[] OPENING_BAL_VALUE=request.getParameterValues("OPENING_BAL_VALUE");
     	String[] RECIEPTS_YEAR_QTY=request.getParameterValues("RECIEPTS_YEAR_QTY");
     	
     	String[] RECIEPTS_YR_VALUE=request.getParameterValues("RECIEPTS_YR_VALUE");
     	String[] ISSUES_YEAR_QTY=request.getParameterValues("ISSUES_YEAR_QTY");
     	String[] ISSUES_YR_VALUE=request.getParameterValues("ISSUES_YR_VALUE");
   
               
     	String[] remark=request.getParameterValues("remark");
      	String[] offcode=request.getParameterValues("offcode");
      	
      	 xml="<response><command>updateTotally1</command>";
     	int ss=assetcode.length;
     
      // System.out.println("assetcode.length =="+ss);
  	
     	for(int ii=0;ii<ss;ii++){
     		
     	
     		int assetno=Integer.parseInt(assetcode[ii]);
     		int assetMin=Integer.parseInt(assetMinorCode[ii]);
     		 //String particul=PARTICULARS[ii];
     		  int openqty=Integer.parseInt(OPEN_BAL_QTY[ii]); 
               int openvalu=Integer.parseInt(OPENING_BAL_VALUE[ii]);
               int receyrqt=Integer.parseInt(RECIEPTS_YEAR_QTY[ii]);
               int repyrval=Integer.parseInt(RECIEPTS_YR_VALUE[ii]);
               int issuyrqty=Integer.parseInt(ISSUES_YEAR_QTY[ii]);
               int issval=Integer.parseInt(ISSUES_YR_VALUE[ii]);
          
              String rem=remark[ii];
              int offcod=Integer.parseInt(offcode[ii]);
              String sqlUpdate="";    	
              try 
              {
          
            	  sqlUpdate = " UPDATE FAS_A52_REGISTER SET " +
    				" N_OPEN_BAL_QTY="+openqty+"," +
    				"OPENING_BAL_VALUE="+openvalu+","+
    				"N_RECIEPTS_YEAR_QTY="+receyrqt+","+"" +
    				"RECIEPTS_YR_VALUE="+repyrval+"," + 
    				"N_ISSUES_YEAR_QTY="+issuyrqty+"," +
    		
    				"SYSTEM_REMARKS='"+rem+"'"+"," +
    				"UPDATED_BY_USERID='"+userid+"'," +
    				"UPDATED_DATE=SYSTIMESTAMP  ,"+
    				"PARTICULARS='"+particul+"'"+"," +
    				"ASSET_MINOR_CLASS_CODE= "+assetMin+
                    " WHERE accounting_unit_id ="+unit_id+
                    " AND accounting_unit_office_id ="+offcod+
    				" AND financial_year ='"+financial_year+"'" +
    				" AND asset_code = "+assetno+
    				" and ASSET_MAJOR_CLASS_CODE="+assetmajor;
            System.out.println(sqlUpdate);
  		   	 ps = connection.prepareStatement(sqlUpdate);
  		 
  		        up=ps.executeUpdate();
                 //System.out.println("up ..." +ii+"times  "+up);
                  
				} catch (Exception e) {
					System.out.println("exception......in full update "+e);			
				}
				
             }
     	
       
     	//System.out.println("k>0"+up);
     	 if(up>0){
				System.out.println("k> >>>> "+up);
				xml=xml+"<flag>success</flag>";
				connection.commit();
				sendMessage(response,"Records updated successfully ","ok");
			}
			else
			{xml=xml+"<flag>failure</flag>";
				connection.rollback();	
				sendMessage(response,"Record Not updated  ","ok");
			}
        	}
        	catch (Exception e) {
				// TODO: handle exception
			} 
        	xml=xml+"</response>";
        	  System.out.println("xml is : " + xml);
              pw.write(xml);
              pw.flush();
              pw.close();
        }*/
        
       
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("poooost ");
		Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
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
	   	//String date_transaction = null;
	   	String financial_year = null;
	    	
	   	
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
        	System.out.println("strCommand : post  " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        int up=0;
        try{
       
           if(strCommand.equalsIgnoreCase("updateTotally")){
        	   
        	   try
               {
               	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
               //	System.out.println("accounting_unit_id : " + unit_id);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
               }        
               
               try
               {
               	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
               	//System.out.println("accounting_unit_office_id : " + office_id);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
               } 
               try
               {
            	   assetmajor = Integer.parseInt(request.getParameter("cmbmajorasset"));
               	//System.out.println("assetmajor : " + assetmajor);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'major' parameter ===> " + e);
               } 
 
               try
               {
            	   financial_year =request.getParameter("cmbFinancialYear");
              	System.out.println("financial_year : " + financial_year);
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
               } 
        	//System.out.println("inside update toolllay");
        	
           	System.out.println("update totally POST >>>>>>   ");
         	String[] assetMinorCode=request.getParameterValues("minorcode");
         	String[] assetcode=request.getParameterValues("assetcode");
         	/*String[] PARTICULARS=request.getParameterValues("PARTICULARS");*/
         	String[] OPEN_BAL_QTY=request.getParameterValues("OPEN_BAL_QTY");
         	String[] OPENING_BAL_VALUE=request.getParameterValues("OPENING_BAL_VALUE");
         	String[] RECIEPTS_YEAR_QTY=request.getParameterValues("RECIEPTS_YEAR_QTY");
         	
         	String[] RECIEPTS_YR_VALUE=request.getParameterValues("RECIEPTS_YR_VALUE");
         	String[] ISSUES_YEAR_QTY=request.getParameterValues("ISSUES_YEAR_QTY");
         	String[] ISSUES_YR_VALUE=request.getParameterValues("ISSUES_YR_VALUE");
       
                   
         	String[] remark=request.getParameterValues("remark");
          	String[] offcode=request.getParameterValues("offcode");
        	
        	int ss=assetcode.length;
        	int cc=0;
        //  System.out.println("assetcode.length =="+ss);
   //  xml="<response><command>updateTotally1</command>";
        	for(int ii=0;ii<ss;ii++){
         		
             	
         		int assetno=Integer.parseInt(assetcode[ii]);
         		int assetMin=Integer.parseInt(assetMinorCode[ii]);
         		 //String particul=PARTICULARS[ii];
         		  int openqty=Integer.parseInt(OPEN_BAL_QTY[ii]); 
                   int openvalu=Integer.parseInt(OPENING_BAL_VALUE[ii]);
                   int receyrqt=Integer.parseInt(RECIEPTS_YEAR_QTY[ii]);
                   int repyrval=Integer.parseInt(RECIEPTS_YR_VALUE[ii]);
                   int issuyrqty=Integer.parseInt(ISSUES_YEAR_QTY[ii]);
                   int issval=Integer.parseInt(ISSUES_YR_VALUE[ii]);
              
                  String rem=remark[ii];
                  int offcod=Integer.parseInt(offcode[ii]);
                  String sqlUpdate="";    
                  
                  
                  String remarkedit="";
          		
           	   try{
       			   int obqty=0,recqty=0,issuqty=0;
       			  
       			   String obrem="",recrem="",issrem="";
       			   String selectqry="select  decode(OPEN_BAL_QTY,null,0,OPEN_BAL_QTY) as OPEN_BAL_QTY," +
       			   		"decode(ISSUES_YEAR_QTY,null,0,ISSUES_YEAR_QTY) as ISSUES_YEAR_QTY," +
       			   		"decode(RECIEPTS_YEAR_QTY,null,0,RECIEPTS_YEAR_QTY) as RECIEPTS_YEAR_QTY "+
       				" from  fas_a52_register    where accounting_unit_id =? and accounting_unit_office_id =? "+
       				 " AND financial_year =?  AND asset_code = ? and ASSET_MAJOR_CLASS_CODE=? and ASSET_MINOR_CLASS_CODE=? ";
       			   
       			   PreparedStatement ps1=connection.prepareStatement(selectqry);
       			   ps1.setInt(1,unit_id);                 
                      ps1.setInt(2,offcod);                   
                      ps1.setString(3,financial_year);                    
                      ps1.setInt(4,assetno);
                      ps1.setInt(5,assetmajor);
                      ps1.setInt(6,assetMin); 
                     ResultSet rs1= ps1.executeQuery();
                     while(rs1.next()){
                   	  obqty=rs1.getInt("OPEN_BAL_QTY");
                   	  recqty=rs1.getInt("RECIEPTS_YEAR_QTY");
                   	  issuqty=rs1.getInt("ISSUES_YEAR_QTY");
                   	  if(obqty==openqty){
                   		System.out.println("no changes ");  
                   	  }else{
                   		   
                   		  obrem="OB-valChg-"+obqty+"-to-"+openqty;
                   		  System.out.println(" changes done "+obrem);
                   	  }
                   	  if(recqty==receyrqt){
                   		  System.out.println("no changes ");  
                   	  }else{
                   		  recrem="R-valChg-"+recqty+"-to-"+receyrqt;
                   		  System.out.println(" changes done "+recrem);  
                   	  }if(issuqty==issuyrqty){
                   		  System.out.println("no changes ");  
                   	  }else{
                   		  issrem="I-valChg-"+issuqty+"-to-"+issuyrqty;
                   		  System.out.println(" changes done "+issrem);  
                   	  }
                   	  
                     }
                     remarkedit=obrem+""+recrem+""+issrem;
       			   
       		   }catch(Exception e){
       			   System.out.println("Error in getting remarks "+e);
       		   }
                  try 
                  {
              
                	  sqlUpdate = " UPDATE FAS_A52_REGISTER SET " +
        				" N_OPEN_BAL_QTY="+openqty+"," +
    /*    				"OPENING_BAL_VALUE="+openvalu+","+
    */    				"N_RECIEPTS_YEAR_QTY="+receyrqt+","+"" +
        			/*	"RECIEPTS_YR_VALUE="+repyrval+"," + */
        				"N_ISSUES_YEAR_QTY="+issuyrqty+"," +
        				
        				"SYSTEM_REMARKS=SYSTEM_REMARKS||''||'"+remarkedit+"'"+"," +
        				"UPDATED_BY_USERID='"+userid+"'," +
        				"UPDATED_DATE=SYSTIMESTAMP  ,"+
        				/*"PARTICULARS='"+particul+"'"+"," +*/
        				"ASSET_MINOR_CLASS_CODE= "+assetMin+
                        " WHERE accounting_unit_id ="+unit_id+
                        " AND accounting_unit_office_id ="+offcod+
        				" AND financial_year ='"+financial_year+"'" +
        				" AND asset_code = "+assetno+
        				" and ASSET_MAJOR_CLASS_CODE="+assetmajor;
               System.out.println(sqlUpdate);
      		  PreparedStatement ps = connection.prepareStatement(sqlUpdate);
      		 
      		        up=ps.executeUpdate();
                     System.out.println("up ..." +ii+"times  "+up);
                      cc++;
    				} catch (Exception e) {
    					
 					System.out.println("exception......in update calll send message "+e);
 					//xml=xml+"<flag>failure</flag>";
					connection.rollback();
                     sendMessage(response,"Failed to update Data","ok");  
				}
    				
        	}
        	System.out.println("cc "+cc+"  ss "+ss);
				      if(cc==ss)
						{
				    	 // xml=xml+"<flag>success</flag>";
							connection.commit();
							sendMessage(response,"Records updated successfully ","ok");
						}
						else
						{
						//	xml=xml+"<flag>failure</flag>";
							connection.rollback();	
							sendMessage(response,"Record Not updated  ","ok");
						}
         
        	
  
        	}//----if end
        	
        }catch(Exception ss){
        	System.out.println("SQL exception   "+ss);
        	
        }
       // xml=xml+"</response>";
  	 // System.out.println("xml is : " + xml);
      //  pw.write(xml);
     //   pw.flush();
     //   pw.close();
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
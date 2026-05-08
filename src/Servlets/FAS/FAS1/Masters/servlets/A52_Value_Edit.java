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
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class A52_Value_Edit
 */
public class A52_Value_Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
       
    
    public A52_Value_Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        ResultSet result1=null,rs1=null;
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
        System.out.println("Session id is:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : a52 value edit " + strCommand);
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
        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        	System.out.println("assetmajor : " + assetmajor);
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
             System.out.println("goget");
             result = statement.executeQuery("select OPEN_BAL_QTY,OPENING_BAL_VALUE,RECIEPTS_YEAR_QTY,RECIEPTS_YR_VALUE,\n"+
            		 "ISSUES_YEAR_QTY,ISSUES_YR_VALUE,\n"+
            		 "  DECODE(N_OPEN_BAL_QTY,NULL,0,N_OPEN_BAL_QTY)     AS N_OPEN_BAL_QTY , " +
               		 "  DECODE(N_OPENING_BAL_VALUE,NULL,0,N_OPENING_BAL_VALUE)AS N_OPENING_BAL_VALUE , " +
               		 "  DECODE(N_RECIEPTS_YEAR_QTY,NULL,0,N_RECIEPTS_YEAR_QTY)AS N_RECIEPTS_YEAR_QTY , " +
               		 "  DECODE(N_RECIEPTS_YR_VALUE,NULL,0,N_RECIEPTS_YR_VALUE)AS N_RECIEPTS_YR_VALUE , " +
               		 "  DECODE(N_ISSUES_YEAR_QTY,NULL,0,N_ISSUES_YEAR_QTY)    AS N_ISSUES_YEAR_QTY , " +
               		 "  DECODE(N_ISSUES_YR_VALUE,NULL,0,N_ISSUES_YR_VALUE)    AS N_ISSUES_YR_VALUE ,\n"+
            		 "DEP_PREV_YEAR,DEPRE_REC_AC,DEPRE_ALLOWED_YR,\n"+
            		 "DEPRE_TR_AC,DEPRE_UPTO_DATE,NET_DEPRE_COST,APP_PRE_YR,APP_GRANT_RECIEVED,APPRO_DURING_YR,\n"+
            		 "APP_GRANT_TR,APP_GRANT_UPTODATE,PARTICULARS,REMARKS,accounting_unit_id,\n"+
            		 "accounting_unit_office_id,financial_year,asset_code,ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE,PARTICULARS,\n" + 
            		 " (SELECT m.ASSET_MINOR_CLASS_DESC "+
            					" FROM FAS_ASSET_MINOR_CLASSIFICATION m "+
            					" WHERE m.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND m.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE "+
            					" )"+ 
            					"AS ASSET_MINOR_CLASS_DESC, "+
            					" (select OFFICE_NAME from com_mst_offices where OFFICE_ID=a.accounting_unit_office_id)as offName"+
             " from FAS_A52_REGISTER a " +
             " WHERE a.accounting_unit_id = " + unit_id +
	     " AND a.accounting_unit_office_id = " + office_id +
	     " AND a.financial_year = '"+ financial_year +"'" +
	     " AND a.ASSET_MAJOR_CLASS_CODE="+assetmajor);
       // " AND ASSET_MINOR_CLASS_CODE="+assetminor);
             
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
                     xml=xml+"<RECIEPTS_YEAR_QTY>" + result.getInt("RECIEPTS_YEAR_QTY") + "</RECIEPTS_YEAR_QTY>";
                     xml=xml+"<RECIEPTS_YR_VALUE>" + result.getInt("RECIEPTS_YR_VALUE") +"</RECIEPTS_YR_VALUE>";
                     xml=xml+"<ISSUES_YEAR_QTY>" + result.getInt("ISSUES_YEAR_QTY") + "</ISSUES_YEAR_QTY>";
                     xml=xml+"<ISSUES_YR_VALUE>" + result.getInt("ISSUES_YR_VALUE") + "</ISSUES_YR_VALUE>";
                     xml=xml+"<N_OPEN_BAL_QTY>" + result.getInt("N_OPEN_BAL_QTY") + "</N_OPEN_BAL_QTY>";
                     xml=xml+"<N_OPENING_BAL_VALUE>" + result.getInt("N_OPENING_BAL_VALUE") +"</N_OPENING_BAL_VALUE>";
                     xml=xml+"<N_RECIEPTS_YEAR_QTY>" + result.getInt("N_RECIEPTS_YEAR_QTY") + "</N_RECIEPTS_YEAR_QTY>";
                     xml=xml+"<N_RECIEPTS_YR_VALUE>" + result.getInt("N_RECIEPTS_YR_VALUE") +"</N_RECIEPTS_YR_VALUE>";
                     xml=xml+"<N_ISSUES_YEAR_QTY>" + result.getInt("N_ISSUES_YEAR_QTY") + "</N_ISSUES_YEAR_QTY>";
                     xml=xml+"<N_ISSUES_YR_VALUE>" + result.getInt("N_ISSUES_YR_VALUE") + "</N_ISSUES_YR_VALUE>";
                    /* xml=xml+"<DEP_PREV_YEAR>" + result.getInt("DEP_PREV_YEAR") + "</DEP_PREV_YEAR>";
                     xml=xml+"<DEPRE_REC_AC>" + result.getInt("DEPRE_REC_AC") + "</DEPRE_REC_AC>";
                     xml=xml+"<DEPRE_ALLOWED_YR>" + result.getInt("DEPRE_ALLOWED_YR") + "</DEPRE_ALLOWED_YR>";
                     xml=xml+"<DEPRE_TR_AC>" + result.getInt("DEPRE_TR_AC") + "</DEPRE_TR_AC>";
                     xml=xml+"<DEPRE_UPTO_DATE>" + result.getInt("DEPRE_UPTO_DATE") + "</DEPRE_UPTO_DATE>";
                     xml=xml+"<NET_DEPRE_COST>" + result.getInt("NET_DEPRE_COST") + ".00"+"</NET_DEPRE_COST>";
                     xml=xml+"<APP_PRE_YR>" + result.getString("APP_PRE_YR") + "</APP_PRE_YR>";
                     xml=xml+"<APP_GRANT_RECIEVED>" + result.getInt("APP_GRANT_RECIEVED") + "</APP_GRANT_RECIEVED>";
                     xml=xml+"<APPRO_DURING_YR>" + result.getInt("APPRO_DURING_YR") + "</APPRO_DURING_YR>";
                     xml=xml+"<APP_GRANT_TR>" + result.getInt("APP_GRANT_TR") + "</APP_GRANT_TR>";
                     xml=xml+"<APP_GRANT_UPTODATE>" + result.getInt("APP_GRANT_UPTODATE") + "</APP_GRANT_UPTODATE>"; */
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
                     }*/
                	 
                	 
                	
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
        } 
       
        else if(strCommand.equals("loadMajor"))
        { 
        	//System.out.println("\n*************\nloadMajor\n**************\n");
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
        else if(strCommand.equals("checkVerifyA52"))
        { 
        	//System.out.println("\n*************\n checkVerifyA52 \n**************\n");
            xml="<response><command>checkVerifyA52</command>";
            try 
            {
                /*System.out.println("bef res");
              System.out.println(unit_id);
              System.out.println(office_id);
              System.out.println(financial_year);
              System.out.println(asset_code);*/
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
        }  
 
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        int unit_id = 0;
        int office_id = 0;
        int assetmajor=0;
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
        	System.out.println("strCommand : post " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        int up=0;
        try{
       
           if(strCommand.equalsIgnoreCase("updateTotally")){
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
               
               try
               {
            	   financial_year =request.getParameter("cmbFinancialYear");
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
               } 
        	String[] sno=request.getParameterValues("sno");
        	String[] assetcode=request.getParameterValues("assetcode");
        	String[] minorcode=request.getParameterValues("minorcode");
        	String[] offcode=request.getParameterValues("offcode");
        	String[] OPENING_BAL_VALUE=request.getParameterValues("OPENING_BAL_VALUE");
        	String[] RECIEPTS_YR_VALUE=request.getParameterValues("RECIEPTS_YR_VALUE");
        	String[] ISSUES_YR_VALUE=request.getParameterValues("ISSUES_YR_VALUE");
        	
        	String[] N_OPENING_BAL_VALUE=request.getParameterValues("N_OPENING_BAL_VALUE");
        	String[] N_RECIEPTS_YR_VALUE=request.getParameterValues("N_RECIEPTS_YR_VALUE");
        	String[] N_ISSUES_YR_VALUE=request.getParameterValues("N_ISSUES_YR_VALUE");
        	
        	String[] OPEN_BAL_QTY=request.getParameterValues("OPEN_BAL_QTY");
        	String[] RECIEPTS_YEAR_QTY=request.getParameterValues("RECIEPTS_YEAR_QTY");
        	String[] ISSUES_YEAR_QTY=request.getParameterValues("ISSUES_YEAR_QTY");
        	String[] PARTICULARS1=request.getParameterValues("PARTICULARS");
        	String[] officeN=request.getParameterValues("offName");
        	
        	
        	
        	int ss=assetcode.length;
        	int cc=0;
            System.out.println("assetcode.length =="+ss);
        	for(int ii=0;ii<ss;ii++){
        		//System.out.println("------------------"+ii+"-----------------");
        		int assetno=Integer.parseInt(assetcode[ii]);
        	  /* System.out.println("minorcode "+minorcode[ii]);
        		System.out.println("offcode "+offcode[ii]);
        		System.out.println("OPENING_BAL_VALUE "+OPENING_BAL_VALUE[ii]);
        		System.out.println("RECIEPTS_YR_VALUE "+RECIEPTS_YR_VALUE[ii]);
        		System.out.println("ISSUES_YR_VALUE "+ISSUES_YR_VALUE[ii]);*/
        	
                 

                  int openvalu=Integer.parseInt(N_OPENING_BAL_VALUE[ii]);
                  int repyrval=Integer.parseInt(N_RECIEPTS_YR_VALUE[ii]);
                  int issval=Integer.parseInt(N_ISSUES_YR_VALUE[ii]);
                  
                  
                  int minorcode1=Integer.parseInt(minorcode[ii]);
                  int offco=Integer.parseInt(offcode[ii]);
                  String remarkedit="";
        		
        		                	   try{
        		            			   int obval=0,recval=0,issuval=0;
        		            			  
        		            			   String obrem="",recrem="",issrem="";
        		            			   String selectqry="select  decode(opening_bal_value,null,0,opening_bal_value) as opening_bal_value," +
        		            			   		"decode(ISSUES_YR_VALUE,null,0,ISSUES_YR_VALUE) as ISSUES_YR_VALUE," +
        		            			   		"decode(reciepts_yr_value,null,0,reciepts_yr_value) as reciepts_yr_value "+
        		            				" from  fas_a52_register    where accounting_unit_id =? and accounting_unit_office_id =? "+
        		            				 " AND financial_year =?  AND asset_code = ? and ASSET_MAJOR_CLASS_CODE=? and ASSET_MINOR_CLASS_CODE=? ";
        		            			   
        		            			   PreparedStatement ps1=connection.prepareStatement(selectqry);
        		            			   ps1.setInt(1,unit_id);                 
        		                           ps1.setInt(2,offco);                   
        		                           ps1.setString(3,financial_year);                    
        		                           ps1.setInt(4,assetno);
        		                           ps1.setInt(5,assetmajor);
        		                           ps1.setInt(6,minorcode1); 
        		                          ResultSet rs1= ps1.executeQuery();
        		                          while(rs1.next()){
        		                        	  obval=rs1.getInt("opening_bal_value");
        		                        	  recval=rs1.getInt("reciepts_yr_value");
        		                        	  issuval=rs1.getInt("ISSUES_YR_VALUE");
        		                        	  if(obval==openvalu){
        		                        		System.out.println("no changes ");  
        		                        	  }else{
        		                        		   
        		                        		  obrem="OB-valChg-"+obval+"-to-"+openvalu;
        		                        		  System.out.println(" changes done "+obrem);
        		                        	  }
        		                        	  if(recval==repyrval){
        		                        		  System.out.println("no changes ");  
        		                        	  }else{
        		                        		  recrem="R-valChg-"+recval+"-to-"+repyrval;
        		                        		  System.out.println(" changes done "+recrem);  
        		                        	  }if(issuval==issval){
        		                        		 // System.out.println("no changes ");  
        		                        	  }else{
        		                        		  issrem="I-valChg-"+issuval+"-to-"+issval;
        		                        		  System.out.println(" changes done "+issrem);  
        		                        	  }
        		                        	  
        		                          }
        		                          remarkedit=obrem+""+recrem+""+issrem;
        		            			   
        		            		   }catch(Exception e){
        		            			   System.out.println("Error in getting remarks "+e);
        		            		   }
        		            		   try 
        		                       {
        		            			   //'"+remarkedit+"'
                	 //System.out.println("  remarkedit "+remarkedit);
                  String sqlUpdate = "UPDATE FAS_A52_REGISTER " +
     				"SET N_OPENING_BAL_VALUE=?,N_RECIEPTS_YR_VALUE=?," + 
     				" N_ISSUES_YR_VALUE=?, SYSTEM_REMARKS=SYSTEM_REMARKS||''|| ?,"+
     				" UPDATED_BY_USERID=?,UPDATED_DATE=? "+
                    "WHERE accounting_unit_id = ? AND accounting_unit_office_id = ? " +
     				" AND financial_year = ?  AND asset_code = ? and ASSET_MAJOR_CLASS_CODE=? and ASSET_MINOR_CLASS_CODE=?";
                    //System.out.println(sqlUpdate);
     		   	   PreparedStatement ps = connection.prepareStatement(sqlUpdate); 
                     ps.setInt(1,openvalu);
                     ps.setInt(2,repyrval);
                     ps.setInt(3,issval);
                     ps.setString(4,remarkedit);
                     ps.setString(5,userid);
                     ps.setTimestamp(6,ts);
                     ps.setInt(7,unit_id);                 
                     ps.setInt(8,office_id);                   
                     ps.setString(9,financial_year);                    
                     ps.setInt(10,assetno);
                     ps.setInt(11,assetmajor);
                     ps.setInt(12,minorcode1);
                     up=ps.executeUpdate();
                     //System.out.println("up ..."+up);
                     cc++;
                     
 				} catch (Exception e) {
 					System.out.println("exception......in update calll send message "+e);
					
					/*connection.rollback();
                     sendMessage(response,"Failed to update Data","ok");  */
				}
 				//System.out.println("count of records updated  "+cc);
 				//System.out.println(" cc "+cc+"  ssss "+ss);
				         
                }
        	if(cc==ss)
			{
				connection.commit();
				sendMessage(response,"Records updated successfully ","ok");
			}
			else
			{
				System.out.println(" inside    else  ");
				connection.rollback();	
				sendMessage(response,"Record Not updated  ","ok");
			}
  
        	}
        	
        }catch(Exception ss){
        	System.out.println("SQL exception   "+ss);
        	
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

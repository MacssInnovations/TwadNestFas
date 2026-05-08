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
 * Servlet implementation class A52_Qty_Push_to_Num
 */
public class A52_Qty_Push_to_Num extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public A52_Qty_Push_to_Num() {
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
        System.out.println("Session id is    a52 push...:"+userid);
        
        
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
        	//System.out.println("assetmajor : " + assetmajor);
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
            // "a.accounting_unit_id = "+ unit_id +
	    // " AND " +
	     "a.accounting_unit_office_id = " + office_id +
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
        else if (strCommand.equalsIgnoreCase("checkStatus")) {
        	int c1=0,c2=0,c3=0;
            xml = "<response><command>checkStatus</command>";
            System.out.println("checkStatus in a52 push to num ");
            try {
            	
            	/*String[] divyear=cmbFinancialYear.split("-");
            	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
            	System.out.println("new year  "+newyear);*/
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
               //   System.out.println("cricleID11 office Id "+cricleID+" orignal  "+office_id);
                
                  
                  String searchquery="select 'X' from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR='"+financial_year+"' ";//and finanical_year='"+cmbFinancialYear+"' 
                 PreparedStatement ps11 =  connection.prepareStatement(searchquery);
                  ps11.setInt(1, office_id);
                  ResultSet rss21=ps11.executeQuery();
                 // System.out.println(" searchquery  "+searchquery);
                  if(rss21.next()){
      			
      				String searchquery1="select A52_STATUS_VAL from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR='"+financial_year+"' ";//and finanical_year='"+cmbFinancialYear+"' 
                    ps1 =  connection.prepareStatement(searchquery1);
                    ps1.setInt(1, cricleID);
                   // ps1.setInt(2, cricleID);
                   // ps1.setString(2, newyear);
                    rss2=ps1.executeQuery();
                   // System.out.println(" searchquery  "+searchquery);
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
        
        else if(strCommand.equals("checkVerifyA52"))
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
        }  
 
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
        	String[] offName=request.getParameterValues("offName");
        	String[] aval_qty=request.getParameterValues("aval_qty");
        	String[] crt_aval_qty=request.getParameterValues("crt_aval_qty");
        	String[] PARTICULARS1=request.getParameterValues("PARTICULARS");
        	String[] finyr=financial_year.split("-");
        	int finyr1=(Integer.parseInt(finyr[0])+1);
        	int finyr2=(Integer.parseInt(finyr[1])+1);
        	String finyrfull=finyr1+"-"+finyr2;
        	
        	System.out.println("finyrfull  "+finyrfull);
        	/*String[] N_OPENING_BAL_VALUE=request.getParameterValues("N_OPENING_BAL_VALUE");
        	String[] N_RECIEPTS_YR_VALUE=request.getParameterValues("N_RECIEPTS_YR_VALUE");
        	String[] N_ISSUES_YR_VALUE=request.getParameterValues("N_ISSUES_YR_VALUE");
        	String[] OPEN_BAL_QTY=request.getParameterValues("OPEN_BAL_QTY");
        	String[] RECIEPTS_YEAR_QTY=request.getParameterValues("RECIEPTS_YEAR_QTY");
        	String[] ISSUES_YEAR_QTY=request.getParameterValues("ISSUES_YEAR_QTY");*/
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
          		        	   /*String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
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
          		                  }*/
          		        	   
          		        	/* String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
     		            	  ps1 =  connection.prepareStatement(selectCircleID);
     		                  ps1.setInt(1, office_id);
     		                  rss1=ps1.executeQuery();
     		                  while(rss1.next()){
     		                	  circleoff=rss1.getInt("CIRCLE_OFFICE_ID");	  
     		                  }*/ 
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
          					
          					/*connection.rollback();
                               sendMessage(response,"Failed to update Data","ok");  */
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
       			} 
                	  
                	  
                	  
                	  
                	  
                	  
                  }
            }catch (Exception e) {
				System.out.println("error in checking");
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

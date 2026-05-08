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
 * Servlet implementation class A52_Push_OB
 */
public class A52_Push_OB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public A52_Push_OB() {
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
        	//&cmbheadac="+cmbheadac
        	 //	+"&cmbdepreciat="+cmbdepreciat+"&cmbapport="+cmbapport;
        	//assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        	//assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        	//assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        	//System.out.println("assetmajor : " + assetmajor);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'assetmajor' parameter ===> " + e);
        }     
      
     
       /* if(strCommand.equals("GoGet"))
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
        } */
 
        //check Status of freeze 
        
        
        if(strCommand.equals("checkStatus"))
        { 
        	int count=0;
    	System.out.println("\n*************\ncheckStatus \n**************\n");
        xml="<response><command>checkStatus</command>";
        try 
        {
        	  result = statement.executeQuery("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,A52_STATUS_CIRCLE,A52_STATUS_UNIT from FAS_A52_REGISTER_STATUS where ACCOUNTING_FOR_OFFICE_ID="+office_id+"  and FINANCIAL_YEAR='"+financial_year+"'");
              //ACCOUNTING_UNIT_ID="+unit_id+" and 
             
           try
           {
          	 xml=xml+"<flag>success</flag>";
          	 String valExists = "No";
               while(result.next())
               { 
              	 valExists = "Yes";
              	 xml += "<ACCOUNTING_UNIT_ID>" + result.getInt("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
              	 xml += "<A52_STATUS>" + result.getString("A52_STATUS_UNIT") + "</A52_STATUS>";
               }

               xml += "<exists>"+valExists+"</exists>";
           }catch(Exception e)
           {
          	 System.out.println("Exception in getting values from  a52 freeze status : " + e);
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
        
        
        else  if(strCommand.equals("getValue"))
        { 
        	int count=0;
    	System.out.println("\n*************\ngetValue \n**************\n");
        xml="<response><command>getValue</command>";
        try 
        {
       //  System.out.println("getValue");
       //  String[] divyear=financial_year.split("-");
      	//String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
       /*  String qry="select OPEN_BAL_QTY, "+
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
          "  from FAS_A52_REGISTER_OB a " +
         		
         " WHERE " +
        // "a.accounting_unit_id = "+ unit_id +
    // " AND " +
     "a.accounting_unit_office_id = " + office_id +
     " AND a.financial_year ='"+ financial_year +"'" +
     " AND a.ASSET_MAJOR_CLASS_CODE="+assetmajor ;*/
         

        	  String qry="select OPEN_BAL_QTY, "+
              " OPENING_BAL_VALUE, "+
     		" RECIEPTS_YEAR_DR_QTY,"+
     		" RECIEPTS_YR_DR_VALUE,"+
     		" ISSUES_YEAR_CR_QTY,"+
     		" ISSUES_YR_CR_VALUE,"+
     		" dep_prev_year,"+
     		" depre_rec_ac,"+
     		" DEPRE_ALLOWED_YR_CR,"+
     		" DEPRE_TR_AC,"+
     		" depre_upto_date,"+
     		" net_depre_cost,"+
     		" APP_PRE_YR,"+
     		" APP_REC_AC,"+
     		" APPRO_ALLOWED_YR_CR,"+
     		" APPRO_TR_AC,"+
     		" APPRO_UPTO_DATE,"+
     		" REMARKS,"+
     		//" particulars,"+
     		//" TEST_DEP_COST," +
     		"accounting_unit_id,"+
             		 " accounting_unit_office_id,asset_code,ASSET_MAJOR_CLASS_CODE,ASSET_MINOR_CLASS_CODE,"+
             		  " (SELECT m.ASSET_MINOR_CLASS_DESC "+
             					 " FROM FAS_ASSET_MINOR_CLASSIFICATION m "+
             					 " WHERE m.ASSET_MINOR_CLASS_CODE=a.ASSET_MINOR_CLASS_CODE  AND m.ASSET_MAJOR_CLASS_CODE  =a.ASSET_MAJOR_CLASS_CODE "+
             					" ) "+
             					" AS ASSET_MINOR_CLASS_DESC, " +
             					"  (select m1.ASSET_MAJOR_CLASS_DESC From FAS_MST_ASSETS_CLASS m1 where m1.ASSET_MAJOR_CLASS_CODE=a.ASSET_MAJOR_CLASS_CODE)as asset_major_desc, "+
             					"(select OFFICE_NAME from com_mst_offices where OFFICE_ID=a.accounting_unit_office_id)as offName  "+
               "  from FAS_A52_REGISTER_EDIT a " +
              		
              " WHERE " +
             // "a.accounting_unit_id = "+ unit_id +
         // " AND " +
          "a.accounting_unit_office_id = " + office_id +
          " AND a.financial_year ='"+ financial_year +"'" +
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
                 xml=xml+"<reciepts_year_qty>" + result.getInt("RECIEPTS_YEAR_DR_QTY") + "</reciepts_year_qty>";
                 xml=xml+"<reciepts_yr_value>" + result.getInt("RECIEPTS_YR_DR_VALUE") +"</reciepts_yr_value>";
                 int tot_qty=result.getInt("OPEN_BAL_QTY")+result.getInt("RECIEPTS_YEAR_DR_QTY") ;
                 int tot_value=result.getInt("OPENING_BAL_VALUE")+result.getInt("RECIEPTS_YR_DR_VALUE");

          		 xml=xml+"<tot_qty>" + tot_qty + "</tot_qty>";
                 xml=xml+"<tot_value>" + tot_value +"</tot_value>";

                 xml=xml+"<ISSUES_YEAR_QTY>" + result.getInt("ISSUES_YEAR_CR_QTY") + "</ISSUES_YEAR_QTY>";
                 xml=xml+"<issues_yr_value>" + result.getInt("ISSUES_YR_CR_VALUE") + "</issues_yr_value>";
                 
                
          		int cb_qty=tot_qty- result.getInt("ISSUES_YEAR_CR_QTY");
          		int cb_value=tot_value-result.getInt("ISSUES_YR_CR_VALUE");
          		 xml=xml+"<cb_qty>" + cb_qty + "</cb_qty>";
                 xml=xml+"<cb_value>" + cb_value +"</cb_value>";

                 xml=xml+"<dep_prev_year>" + result.getInt("dep_prev_year") + "</dep_prev_year>";
                 xml=xml+"<depre_rec_ac>" + result.getInt("depre_rec_ac") +"</depre_rec_ac>";
                 xml=xml+"<depre_allowed_yr>" + result.getInt("DEPRE_ALLOWED_YR_CR") + "</depre_allowed_yr>";
                 xml=xml+"<DEPRE_TR_AC>" + result.getInt("DEPRE_TR_AC") + "</DEPRE_TR_AC>";
                 xml=xml+"<depre_upto_date>" + result.getInt("depre_upto_date") + "</depre_upto_date>";
               
                 xml=xml+"<net_depre_cost>" + result.getInt("net_depre_cost") + "</net_depre_cost>";
                 xml=xml+"<APP_PRE_YR>" + result.getInt("APP_PRE_YR") + "</APP_PRE_YR>";
                 xml=xml+"<app_grant_recieved>" + result.getInt("APP_REC_AC") + "</app_grant_recieved>";
                 xml=xml+"<appro_during_yr>" + result.getInt("APPRO_ALLOWED_YR_CR") + "</appro_during_yr>";
                 xml=xml+"<APP_GRANT_TR>" + result.getInt("APPRO_TR_AC") +"</APP_GRANT_TR>";
             
               /*  Upto Previous year Depreciation + 
                 Received through proforma account + 
                 allowed during the year credit –
                 allowed during the year debit*/
                 
                 
                 int tot_dep=result.getInt("dep_prev_year")+result.getInt("depre_rec_ac")+result.getInt("DEPRE_ALLOWED_YR_CR");
                 int tot_app= result.getInt("APP_PRE_YR")+ result.getInt("APP_REC_AC")+result.getInt("APPRO_ALLOWED_YR_CR");
                 xml=xml+"<tot_dep>" + tot_dep + "</tot_dep>";
                 xml=xml+"<tot_app>" + tot_app + "</tot_app>";
                 //11a – 12a 
                 int upto_dep=tot_dep-result.getInt("DEPRE_TR_AC")  ;
                 int upto_app=tot_app-result.getInt("APPRO_TR_AC");
                 xml=xml+"<upto_dep>" + upto_dep +"</upto_dep>";
                 xml=xml+"<upto_app>" + upto_app +"</upto_app>";
                 
                 xml=xml+"<ASSET_MAJOR_CLASS_CODE>"+ result.getInt("ASSET_MAJOR_CLASS_CODE") +"</ASSET_MAJOR_CLASS_CODE>";
					xml=xml+"<ASSETMINORCLASSCODE>"+ result.getInt("ASSET_MINOR_CLASS_CODE") +"</ASSETMINORCLASSCODE>";
					xml=xml+"<ASSET_MINOR_CLASS_DESC>"+ result.getString("ASSET_MINOR_CLASS_DESC") +"</ASSET_MINOR_CLASS_DESC>";
					xml=xml+"<asset_major_desc>"+ result.getString("asset_major_desc") +"</asset_major_desc>";
					PARTICULARS = result.getString("REMARKS").trim();
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
        //loadHeadDesc
        else if(strCommand.equals("loadHeadDesc"))
        { 
        	System.out.println("\n*************\n loadHeadDesc \n**************\n");
            xml="<response><command>loadHeadDesc</command>";
            try 
            {
            // result = statement.executeQuery("select ACCOUNT_HEAD_CODE,ASSET_MAJOR_CLASS_CODE from FAS_ASSET_AC_HEADS where ASSET_MAJOR_CLASS_CODE="+assetmajor+" order by ACCOUNT_HEAD_CODE");
            	String ssqr="select ACCOUNT_HEAD_CODE,ASSET_MAJOR_CLASS_CODE from FAS_MST_ASSETS_CLASS  where ASSET_MAJOR_CLASS_CODE="+assetmajor;
             result = statement.executeQuery(ssqr);
             //System.out.println("ssqry head  "+ssqr);
             //select ACCOUNT_HEAD_CODE from FAS_MST_ASSETS_CLASS  where ASSET_MAJOR_CLASS_CODE=
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_MAJOR_CLASS_CODE>" + result.getInt("ASSET_MAJOR_CLASS_CODE") + "</ASSET_MAJOR_CLASS_CODE>";
                	 xml += "<ACCOUNT_HEAD_CODE>" + result.getString("ACCOUNT_HEAD_CODE") + "</ACCOUNT_HEAD_CODE>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from loadHeadcode: " + e);
             }
             
             result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in loadhead"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        //loadDep
        else if(strCommand.equals("loadDep"))
        { 
        	System.out.println("\n*************\n loadDep \n**************\n");
            xml="<response><command>loadDep</command>";
            try 
            {
          String ssqr="select DEPRECIATION_RATE,DEPRECIATION_CATE_CODE from  FAS_DEPRE_RATES where STATUS='Y' and FINANCIAL_YEAR='"+financial_year+"' and  DEPRECIATION_CATE_CODE="+assetmajor;
             result = statement.executeQuery(ssqr);
           System.out.println("ssqr dep  "+ssqr);

             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<DEPRECIATION_CATE_CODE>" + result.getInt("DEPRECIATION_CATE_CODE") + "</DEPRECIATION_CATE_CODE>";
                	 xml += "<DEPRECIATION_RATE>" + result.getString("DEPRECIATION_RATE") + "</DEPRECIATION_RATE>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from loadDep: " + e);
             }
             
             result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in loaddep"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        //loadapp
        else if(strCommand.equals("loadApp"))
        { 
        	System.out.println("\n*************\n loadApp \n**************\n");
            xml="<response><command>loadApp</command>";
            try 
            {
          String ssqry="select APPORTIONMENT_RATE,APPORTION_GRANT_CATE_CODE from FAS_APPORTIONMENT_RATES where STATUS='L' and FINANCIAL_YEAR='"+ financial_year +"' and APPORTION_GRANT_CATE_CODE="+assetmajor;
             result = statement.executeQuery(ssqry);
            
System.out.println("appload  ssqry "+ssqry);
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<APPORTION_GRANT_CATE_CODE>" + result.getInt("APPORTION_GRANT_CATE_CODE") + "</APPORTION_GRANT_CATE_CODE>";
                	 xml += "<APPORTIONMENT_RATE>" + result.getString("APPORTIONMENT_RATE") + "</APPORTIONMENT_RATE>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from loadApp: " + e);
             }
             
             result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in loaddep"+e1);
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
        else if (strCommand.equalsIgnoreCase("getValue1")) {
        	int c1=0,c2=0,c3=0;
            xml = "<response><command>getValue1</command>";
            System.out.println("getValue1 in a52 push to num ");
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
        
        int asset_code1=0;
        int OB_Qty1=0,OB_Value1=0,Receipts_Dr_Qty1=0,Receipts_Cr_Qty1=0,Receipts_Dr_Value1=0,Receipts_Cr_Value1=0;
        int Total_Qty1=0,Total_Value1=0,Issues_Dr_Qty1=0,Issues_Cr_Qty1=0,Issues_Dr_Value1=0,Issues_Cr_Value1=0,CB_Qty1=0,CB_Value1=0,Upto_Pre_Depr1=0;
        int Upto_Pre_Appr1=0,Rec_Thr_dep1=0,Rec_Thr_app1=0,allow_dur_dep_dr1=0,allow_dur_dep_cr1=0,allow_dur_app_dr1=0,allow_dur_app_cr1=0;
        int tot_Dep1=0,tot_App1=0,trasf_Thr_dep1=0,trasf_Thr_app1=0,Upto_date_Dep1=0,Upto_date_App1=0,dep_Cost1=0;
        String desc1="",headac="";
        int depreciat=0,apport=0;
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
            	 System.out.println("asset_code1:::::::::::"+asset_code1);
            	 
            	  depreciat =Integer.parseInt(request.getParameter("cmbdepreciat"));
            	    apport =Integer.parseInt(request.getParameter("cmbapport"));
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
                 	  dep_Cost1);
          */
                	  
               try 
                 {  
            	   
            	  /* String sqlselect="select 'X' from FAS_A52_REGISTER_EDIT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and FINANCIAL_YEAR=? and ASSET_MAJOR_CLASS_CODE=? and ASSET_CODE=? and HEAD_OF_ACCOUNT=?";        		            			   
					PreparedStatement ps11 = connection.prepareStatement(sqlselect); 
					ps11.setInt(1,unit_id); 
					ps11.setInt(2,office_id);  
					ps11.setString(3,financial_year);
					ps11.setInt(4,majorCoode);
					ps11.setInt(5,asset_code1);
					ps11.setString(6,headac); 
					
            	   ResultSet rs11=ps11.executeQuery();
            	   if(rs11.next()){
            		   System.out.println("inside if");
            		   connection.rollback();	
						sendMessage(response,"Record already Exists  ","ok");
            		   
            	   }else{
            		   System.out.println("inside else ");*/
            	   try{
            		   
            	  
					/*String sqlinsert="insert into FAS_A52_REGISTER_EDIT(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,FINANCIAL_YEAR,ASSET_MAJOR_CLASS_CODE,ASSET_CODE,HEAD_OF_ACCOUNT,DEPRECIATION,APPORTIONMENT,OPEN_BAL_QTY,OPENING_BAL_VALUE,RECIEPTS_YEAR_DR_QTY,RECIEPTS_YEAR_CR_QTY,RECIEPTS_YR_DR_VALUE,RECIEPTS_YR_CR_VALUE,ISSUES_YEAR_DR_QTY,ISSUES_YEAR_CR_QTY,ISSUES_YR_DR_VALUE,ISSUES_YR_CR_VALUE,DEP_PREV_YEAR,APP_PRE_YR,DEPRE_REC_AC,APP_REC_AC,DEPRE_ALLOWED_YR_DR,DEPRE_ALLOWED_YR_CR,APPRO_ALLOWED_YR_DR,APPRO_ALLOWED_YR_CR,DEPRE_TR_AC,APPRO_TR_AC,DEPRE_UPTO_DATE,APPRO_UPTO_DATE,NET_DEPRE_COST,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";        		            			   
					PreparedStatement ps = connection.prepareStatement(sqlinsert);
					
					ps.setInt(1,unit_id); 
					ps.setInt(2,office_id);  
					ps.setString(3,financial_year);
					ps.setInt(4,majorCoode);
					ps.setInt(5,asset_code1);
					ps.setString(6,headac); 
					ps.setInt(7,depreciat);
					ps.setInt(8,apport);
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
					
					
					
					*/
            		   
            		   
            		   
            		   String sqlinsert="update FAS_A52_REGISTER_EDIT set HEAD_OF_ACCOUNT=?,DEPRECIATION=?,APPORTIONMENT=?,OPEN_BAL_QTY=?,OPENING_BAL_VALUE=?,RECIEPTS_YEAR_DR_QTY=?,RECIEPTS_YEAR_CR_QTY=?,RECIEPTS_YR_DR_VALUE=?,RECIEPTS_YR_CR_VALUE=?,ISSUES_YEAR_DR_QTY=?,ISSUES_YEAR_CR_QTY=?,ISSUES_YR_DR_VALUE=?,ISSUES_YR_CR_VALUE=?,DEP_PREV_YEAR=?,APP_PRE_YR=?,DEPRE_REC_AC=?,APP_REC_AC=?,DEPRE_ALLOWED_YR_DR=?,DEPRE_ALLOWED_YR_CR=?,APPRO_ALLOWED_YR_DR=?,APPRO_ALLOWED_YR_CR=?,DEPRE_TR_AC=?,APPRO_TR_AC=?,DEPRE_UPTO_DATE=?,APPRO_UPTO_DATE=?,NET_DEPRE_COST=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_OFFICE_ID=? and FINANCIAL_YEAR=? and ASSET_MAJOR_CLASS_CODE=? and ASSET_CODE=? ";        		            			   
   					PreparedStatement ps = connection.prepareStatement(sqlinsert);
   					
   					
   					ps.setString(1,headac); 
   					ps.setInt(2,depreciat);
   					ps.setInt(3,apport);
   					ps.setInt(4,OB_Qty1);
   					ps.setInt(5,OB_Value1);
   					ps.setInt(6,Receipts_Dr_Qty1);
   					ps.setInt(7,Receipts_Cr_Qty1);
   					ps.setInt(8,Receipts_Dr_Value1);
   					ps.setInt(9,Receipts_Cr_Value1); 
   					ps.setInt(10,Issues_Dr_Qty1);
   					ps.setInt(11,Issues_Cr_Qty1);
   					ps.setInt(12,Issues_Dr_Value1);
   					ps.setInt(13,Issues_Cr_Value1);
   					ps.setInt(14,Upto_Pre_Depr1);
   					ps.setInt(15,Upto_Pre_Appr1);
   					ps.setInt(16,Rec_Thr_dep1);
   					ps.setInt(17,Rec_Thr_app1);
   					ps.setInt(18,allow_dur_dep_dr1);
   					ps.setInt(19,allow_dur_dep_cr1);
   					ps.setInt(20,allow_dur_app_dr1);
   					ps.setInt(21,allow_dur_app_cr1);
   					ps.setInt(22,trasf_Thr_dep1);
   					ps.setInt(23,trasf_Thr_app1);
   					ps.setInt(24,Upto_date_Dep1);
   					ps.setInt(25,Upto_date_App1);
   					ps.setInt(26,dep_Cost1);
   					ps.setString(27,desc1); 
   					ps.setString(28,userid);
   					ps.setTimestamp(29,ts);
   					
//   					ps.setInt(30,unit_id); 
   					ps.setInt(30,office_id);  
   					ps.setString(31,financial_year);
   					ps.setInt(32,majorCoode);
   					ps.setInt(33,asset_code1);
   					
   					up=ps.executeUpdate();
   					System.out.println("up ..."+up);
   					
   					
   					
   					
            		   
            	   }catch (Exception e) {
   					System.out.println("Excep in update  "+e);
   				}
					   
            	  // }
            	   if(up>0)
					{
						connection.commit();
						sendMessage(response,"Records Updated successfully ","ok");
					}
					else
					{
						//System.out.println(" inside    else  ");
						connection.rollback();	
						sendMessage(response,"Record updation failed ","ok");
					}    
            	   
					} catch (Exception e) {
					System.out.println("exception......in update calll send message "+e);
					
					connection.rollback();
					sendMessage(response,"Failed to update Data","ok");  
					}
					System.out.println("records updated  "+up);            	  
							  
					                	  
					                	  
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
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateFromIssue
 */
public class UpdateFromIssue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
    public UpdateFromIssue() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection connection=null;
	        Statement statement=null;
	        ResultSet result=null,result1=null; 
	   	    PreparedStatement ps=null;
	      
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
	        	System.out.println("strCommand : " + strCommand);
	        }
	        catch(Exception e)
	        {
	          e.printStackTrace();
	        }
	     
	        try
	        {
	        	
	
	        }
	        catch(Exception e)
	        { 
	            System.out.println("IGNORABLE Exception getting values from jsp " + e);
	        }         
	      
	      if(strCommand.equals("GoGet"))
	        { 
	    	  unit_id = Integer.parseInt(request.getParameter("unit_id"));
	        	office_id = Integer.parseInt(request.getParameter("office_id"));
	        	financial_year = request.getParameter("financial_year");
	        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
	        	int count=0;
	        	
	            xml="<response><command>GoGet</command>";
	            try 
	            {

	                String selectQuery="select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH, "+
	                " ASSET_CODE,ISSUE_DATE,ISSUED_TO_LEVEL_ID,ISSUED_TO_OFFICE,QTY_ISSUED,REMARKS,"+
	                " TDA_ORIGINATING_JVR_NO,TDA_ORIGINATING_JVR_DATE,VALUE_ISSUED "+ 
	                "  from FAS_ISSUEOF_ASSETS "+
	             " WHERE ACCOUNTING_UNIT_ID = " + unit_id +
			     " AND ACCOUNTING_FOR_OFFICE_ID = " + office_id +
			     " AND FINANCIAL_YEAR = '" + financial_year + "'" +
			     " AND ASSET_MAJOR_CLASS_CODE="+assetmajor+" AND UPDATED_TO_A52 is NULL ";
	             //   System.out.println(selectQuery);          
	         	ps=connection.prepareStatement(selectQuery);
				result=ps.executeQuery();	
	             
	           try
	             {
	            	 xml=xml+"<flag>success</flag>";
	            	 String valExists = "No";   	
	                 while(result.next())
	                 { 
	                	 valExists = "Yes";
	                	 xml += "<asset_code>" + result.getInt("ASSET_CODE") + "</asset_code>";
			               	 xml += "<accounting_unit_id>" + result.getInt("ACCOUNTING_UNIT_ID") + "</accounting_unit_id>"; 
			            	 xml += "<accounting_unit_office_id>" + result.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</accounting_unit_office_id>"; 
	                     xml=xml+"<CASHBOOK_YEAR>" + result.getInt("CASHBOOK_YEAR") + "</CASHBOOK_YEAR>";
	                     xml=xml+"<CASHBOOK_MONTH>" + result.getInt("CASHBOOK_MONTH")  +"</CASHBOOK_MONTH>";
	                   //  xml=xml+"<ISSUE_DATE>" + result.getInt("ISSUE_DATE") + "</RECEIPT_NO>";
	                     Date dateformat1=result.getDate("ISSUE_DATE");
	                	 DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
	                	 String changedDate1 = df1.format(dateformat1);
	                 	 xml += "<ISSUE_DATE>" +changedDate1+"</ISSUE_DATE>"; 
	                 	// xml += "<ISSUE_DATE>" +result.getDate("ISSUE_DATE")+"</ISSUE_DATE>"; 
	                     xml=xml+"<ISSUED_TO_LEVEL_ID><![CDATA[" + result.getString("ISSUED_TO_LEVEL_ID") + "]]></ISSUED_TO_LEVEL_ID>";
	                     xml=xml+"<ISSUED_TO_OFFICE>" + result.getInt("ISSUED_TO_OFFICE") + "</ISSUED_TO_OFFICE>"; 
	                     xml=xml+"<QTY_ISSUED>" + result.getInt("QTY_ISSUED") + "</QTY_ISSUED>";
	                     xml=xml+"<VALUE_ISSUED>" + result.getInt("VALUE_ISSUED") + "</VALUE_ISSUED>";                     
	                     xml=xml+"<REMARKS>" + result.getString("REMARKS") + "</REMARKS>";                    
	                	 count++;
	                 }

	                 xml =xml+ "<exists>"+valExists+"</exists>";
	                 xml =xml+ "<count>"+count+"</count>";
	             }
	           catch(Exception e)
	             {
	            	 System.out.println("Exception in getting values from DB - Go GET: " + e);
	             }

	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in Get"+e1);
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
	            	String selectQuery="select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE";
	             
	             ps=connection.prepareStatement(selectQuery);
	 			result=ps.executeQuery();	
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

	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in Get"+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	        }
	        /*else if(strCommand.equals("updateTotally")) 
	        {
	        	int rowcount=Integer.parseInt(request.getParameter("rowcount"));
	        	System.out.println("rowcount"+rowcount );
	        	int up=0;
	     	System.out.println("update totally");
	   	   xml="<response><command>updateTotally1</command>";
	   	   int cc=0;
	   	   
	   	 String[] grid=request.getParameter("grid").split(",");
			
			int len=grid.length;
			//System.out.println("arraylength"+len);
			
			
			for(int i=0;i<len;i=i+9)
			{
				
				int casbyr=Integer.parseInt(grid[i]);
				int casbmth=Integer.parseInt(grid[i+1]);
				int asscode=Integer.parseInt(grid[i+2]);
				String issdate=grid[i+3];
				String issuelevelid=grid[i+4];
				int issuofice=Integer.parseInt(grid[i+5]);
				int issuedqty=Integer.parseInt(grid[i+6]);
				String remar=grid[i+7];
				int offid=Integer.parseInt(grid[i+8]);	
				String sqlUpdate ="";
				try{
					
				
				sqlUpdate = " UPDATE FAS_A52_REGISTER SET " +
       	  "ISSUES_YEAR_QTY=decode(ISSUES_YEAR_QTY,null,0,ISSUES_YEAR_QTY)+"+issuedqty+
       	  "VALUE_ISSUED=decode(VALUE_ISSUED,null,0,VALUE_ISSUED)+"+issuedqty+
               " WHERE ACCOUNTING_UNIT_ID= "+unit_id +
				" and ACCOUNTING_UNIT_OFFICE_ID="+office_id+
				" and FINANCIAL_YEAR='" + financial_year + "'" +
				" and ASSET_MAJOR_CLASS_CODE="+assetmajor +
				"and ASSET_CODE=" +asscode ;
           // System.out.println(sqlUpdate);
		    	 ps = connection.prepareStatement(sqlUpdate);  		
		         up=ps.executeUpdate();
		         cc++;
				}catch(Exception ee){
					System.out.println("Exception in update query "+ee);
				}
				
			}
	    
	    
	     	 if(rowcount==cc){
					System.out.println("rowcount===cc  "+rowcount+"  "+cc);
					xml+="<flag>success</flag>";
				}else{
					xml+="<flag>failure</flag>";
				}
			
			xml+="</response>";

	     	}*/
	        
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
	        ResultSet result=null,result1=null;
	       // ResultSet result1=null;   
	   	    PreparedStatement ps=null;
	      
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
	        	System.out.println("strCommand : " + strCommand);
	        	
	        	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	        	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
	        	financial_year = request.getParameter("cmbFinancialYear");
	        	assetmajor = Integer.parseInt(request.getParameter("cmbmajorasset"));
	        }
	        catch(Exception e)
	        {
	          e.printStackTrace();
	        }
	        if(strCommand.equals("updateTotally")) 
	        {
  	
	   	    String[] serialcount=request.getParameterValues("serialNo");
	     	String[] CASHBOOK_YEAR=request.getParameterValues("CASHBOOK_YEAR");
	     	String[] CASHBOOK_MONTH=request.getParameterValues("CASHBOOK_MONTH");
	     	String[] asset_code=request.getParameterValues("asset_code");
	     	String[] ISSUE_DATE=request.getParameterValues("ISSUE_DATE");
	     	String[] ISSUED_TO_LEVEL_ID=request.getParameterValues("ISSUED_TO_LEVEL_ID");
	     	String[] ISSUED_TO_OFFICE=request.getParameterValues("ISSUED_TO_OFFICE");
	     	String[] QTY_ISSUED=request.getParameterValues("QTY_ISSUED");	    	
	     	String[] VALUE_ISSUED=request.getParameterValues("VALUE_ISSUED");
	     	String[] REMARKS=request.getParameterValues("REMARKS");
	    	String[] officeid1=request.getParameterValues("officeid1");
	      

	     	int ss=serialcount.length;
	     
	     System.out.println("serialcount leng  =="+ss);
	     int rowsupdated=0;
	     try{
	    	  connection.setAutoCommit(false);
	    int up=0;
	  	
	     	for(int ii=0;ii<ss;ii++){
	     		
	     		String sqlUpdate="";    
	     		try 
	              {	
	     			int CASHBOOK_YEAR1=Integer.parseInt(CASHBOOK_YEAR[ii]);
	     			int CASHBOOK_MONTH1=Integer.parseInt(CASHBOOK_MONTH[ii]);
				int assetno=Integer.parseInt(asset_code[ii]);
				String ISSUE_DATE1=ISSUE_DATE[ii];
				int ISSUED_TO_OFFICE1=Integer.parseInt(ISSUED_TO_OFFICE[ii]); 
				int QTY_ISSUED1=Integer.parseInt(QTY_ISSUED[ii]);
				int oofid=Integer.parseInt(officeid1[ii]);
				String ISSUED_TO_LEVEL_ID1=ISSUED_TO_LEVEL_ID[ii];             
				String rem=REMARKS[ii];
				int VALUE_ISSUED1=Integer.parseInt(VALUE_ISSUED[ii]);
				/*correctqtyremark1=correctqtyremark[ii];
				if((correctqtyremark[ii].equalsIgnoreCase(""))||(correctqtyremark[ii].equalsIgnoreCase("null"))){
	     			correctqtyremark1="";
	     		}*/
				 
				
				/*sqlUpdate = " UPDATE FAS_A52_REGISTER SET " +
		       	  "ISSUES_YEAR_QTY=decode(ISSUES_YEAR_QTY,null,0,ISSUES_YEAR_QTY)+"+QTY_ISSUED1+
		       	  " , ISSUES_YR_VALUE=decode(ISSUES_YR_VALUE,null,0,ISSUES_YR_VALUE)+"+VALUE_ISSUED1+
		               " WHERE" +
		               //" ACCOUNTING_UNIT_ID= "+unit_id + " and "+
						"  ACCOUNTING_UNIT_OFFICE_ID="+office_id+
						" and FINANCIAL_YEAR='" + financial_year + "'" +
						" and ASSET_MAJOR_CLASS_CODE="+assetmajor +
						" and ASSET_CODE=" +assetno ;
*/
				sqlUpdate = " UPDATE FAS_A52_REGISTER_EDIT SET " +
		       	  "ISSUES_YEAR_CR_QTY=decode(ISSUES_YEAR_CR_QTY,null,0,ISSUES_YEAR_CR_QTY)+"+QTY_ISSUED1+
		       	  " , ISSUES_YR_CR_VALUE=decode(ISSUES_YR_CR_VALUE,null,0,ISSUES_YR_CR_VALUE)+"+VALUE_ISSUED1+
		               " WHERE" +
		               //" ACCOUNTING_UNIT_ID= "+unit_id + " and "+
						"  ACCOUNTING_UNIT_OFFICE_ID="+office_id+
						" and FINANCIAL_YEAR='" + financial_year + "'" +
						" and ASSET_MAJOR_CLASS_CODE="+assetmajor +
						" and ASSET_CODE=" +assetno ;

				
				
	                // System.out.println(sqlUpdate);
	  		    	 ps = connection.prepareStatement(sqlUpdate);  		
	  		         up=ps.executeUpdate();
	  		         
	  		         if(up>0){
	  		       rowsupdated++;
	  		         }
	  		       
	  		 	if(up>0){
	  		    	 
		  		     
		  		       String updatedtoRece="update FAS_ISSUEOF_ASSETS set UPDATED_TO_A52=? "+
		             " WHERE "+//ACCOUNTING_UNIT_ID = " + circleid + " AND "+
				     "  ACCOUNTING_FOR_OFFICE_ID = " + office_id +
				     " AND FINANCIAL_YEAR = '" + financial_year + "'" +
				     " AND ASSET_MAJOR_CLASS_CODE="+assetmajor+ " AND ASSET_CODE="+assetno ;
		  		   PreparedStatement  ps1 = connection.prepareStatement(updatedtoRece);  
		  		     ps1.setTimestamp(1,ts);
		  		   int up1=ps1.executeUpdate();
		  		     System.out.println("up1 "+up1);
		  		     
		  		       	}
					} catch (Exception e) {
						System.out.println("exception......in full update "+e);			
					}
					
	             } 
	   //System.out.println(" rowsupdated "+rowsupdated +" ss "+ss);
	     
	     	 if(rowsupdated==ss){
	                 connection.commit();
	                 sendMessage(response,"The Issues of Assets Successfully Updated","ok");
	              }
	              else
	              {
	                 connection.rollback();    
	                 sendMessage(response,"The Issues of Assets Are Not Saved ","ok");
	              }
	     }catch(Exception e){
	    	 System.out.println("Exception  "+e);
	     }
	   	   
       }	     
	       }
	 private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
    	try
    	{
    		//System.out.println("Inside.....................");
    		//String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
    		String url="org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" + bType;
    		response.sendRedirect(url);
    	}
    	catch(Exception e)
    	{
    		System.out.println("error in messenger"+e);
    	}        	
    }

}

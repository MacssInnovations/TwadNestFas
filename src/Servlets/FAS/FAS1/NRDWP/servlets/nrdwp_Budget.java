package Servlets.FAS.FAS1.NRDWP.servlets;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class nrdwp_Budget
 */
public class nrdwp_Budget extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
   
    public nrdwp_Budget() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 Connection connection=null;
	        Statement statement=null;
	        ResultSet result=null,rs1=null,rs2=null,rs3=null; 
	   	    PreparedStatement ps=null,ps1=null,ps2=null,ps3=null;
	      
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
	        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
	        	office_id = Integer.parseInt(request.getParameter("office_id"));
	        	financial_year = request.getParameter("financial_year");
	        	
	        	//System.out.println("accounting_unit_id : " + unit_id);
	        	//System.out.println("accounting_unit_office_id : " + office_id);
	        	//System.out.println("financial_year : " + financial_year);
	        	
	        }
	        catch(Exception e)
	        { 
	            System.out.println("IGNORABLE Exception getting values from jsp " + e);
	        }         
	      
	      if(strCommand.equals("GoGet"))
	        { 
	        	int count=0;
	            xml="<response><command>GoGet</command>";
	            try 
	            {
	            	
	            	String checkFreeze="select 'X' from FAS_NRDWP_BUDGET_FREEZE where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=?";
	            	ps3=connection.prepareStatement(checkFreeze);
	            	 ps3.setInt(1,unit_id);
	 	            ps3.setInt(2, office_id);
	 	            ps3.setString(3, financial_year);
	 	            rs3=ps3.executeQuery();
	            	if(rs3.next()){
	            		xml=xml+"<checkFreeze>freeze</checkFreeze>";		
	            	}
	            	else{
	            		xml=xml+"<checkFreeze>Notfreeze</checkFreeze>";	
	            	//}
	            String checkqry="select 'X' from FAS_NRDWP_BUDGET where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=?";
	            ps2=connection.prepareStatement(checkqry);
	            ps2.setInt(1,unit_id);
	            ps2.setInt(2, office_id);
	            ps2.setString(3, financial_year);
	            rs2=ps2.executeQuery();
	            if(rs2.next()){
	            	xml=xml+"<check>valueinTB</check>";	
	            	xml=xml+"<flag>success</flag>";
	            }else{
	            	xml=xml+"<check>NotvalueinTB</check>";	
	            	
	           try
	             {
	               
	  	         String selectQry="select t2.activity_code as activity_code,decode(t2.ACTIVITY_CODE,'M','Main Activity','Support Activity')as Activty_Desc,t1.component_desc as component_desc,t1.COMPONENT_CODE as COMPONENT_CODE from fas_nrdwp_component t1 inner join fas_nrdwp_cs_map t2 on t1.component_code=t2.component_code";
	  	         //  System.out.println(selectQry);           
	  	         	ps=connection.prepareStatement(selectQry);
	  				result=ps.executeQuery();	
	  	             int ccshare=0;
	            	 xml=xml+"<flag>success</flag>";
	            	 String sQry="select SHARE_CODE,SHARE_DESC  from FAS_NRDWP_SHARE order by SHARE_CODE";
	       			 ps1=connection.prepareStatement(sQry);
	       			 rs1=ps1.executeQuery();
	       			xml += "<SHARE_value>";
	       			 while(rs1.next()){
	       				 xml += "<SHARE_CODE>" + rs1.getInt("SHARE_CODE")+ "</SHARE_CODE>";
				            xml += "<SHARE_DESC>" + rs1.getString("SHARE_DESC") + "</SHARE_DESC>"; 
				            ccshare++;       
	       			 }
	       			xml += "</SHARE_value>";
	       		    xml =xml+ "<sharecount>"+ccshare+"</sharecount>";
	            	 String valExists = "No";
	                 while(result.next())
	                 { 
	                	 valExists = "Yes";
	                	xml += "<activity_code>" + result.getString("activity_code") + "</activity_code>";
			            xml += "<Activty_Desc>" + result.getString("Activty_Desc") + "</Activty_Desc>"; 
			            xml += "<COMPONENT_CODE>" + result.getInt("COMPONENT_CODE") + "</COMPONENT_CODE>"; 
	                    xml += "<component_desc>" + result.getString("component_desc") + "</component_desc>";              
	                	 count++;
	                	 
	                 }

	                 System.out.println("count  "+count);
	                 xml =xml+ "<exists>"+valExists+"</exists>";
	                 xml =xml+ "<count>"+count+"</count>";
	             }
	           catch(Exception e)
	             {
	            	 System.out.println("Exception in getting values from DB - Go GET: " + e);
	             }
	            }
	            }
	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in Get"+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
	           
	        }
	      else if(strCommand.equals("Edit1"))
	        { 
	        	int count=0;
	            xml="<response><command>Edit1</command>";
	            try 
	            {
	            	String checkFreeze="select 'X' from FAS_NRDWP_BUDGET_FREEZE where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=?";
	            	ps3=connection.prepareStatement(checkFreeze);
	            	 ps3.setInt(1,unit_id);
	 	            ps3.setInt(2, office_id);
	 	            ps3.setString(3, financial_year);
	 	            rs3=ps3.executeQuery();
	            	if(rs3.next()){
	            		xml=xml+"<checkFreeze>freeze</checkFreeze>";		
	            	}
	            	else{
	            		xml=xml+"<checkFreeze>Notfreeze</checkFreeze>";	
	            String checkqry=" select 'X' from FAS_NRDWP_BUDGET where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=?";
	            ps2=connection.prepareStatement(checkqry);
	            ps2.setInt(1,unit_id);
	            ps2.setInt(2, office_id);
	            ps2.setString(3, financial_year);
	            rs2=ps2.executeQuery();
	            if(rs2.next()){
	            	xml=xml+"<check>valueinTB</check>";	
	            	 xml=xml+"<flag>success</flag>";
	            	 try
		             {
		        	   String selectQry="select f1.activity ,decode(f1.activity,'M' ,'Main Activity','Support Activity')as actdesc, "+
		        		   " component_code,(select COMPONENT_DESC from FAS_NRDWP_COMPONENT f2 where f2.COMPONENT_CODE=f1.COMPONENT_CODE)as CompDesc,"+
		        		   " SHARE_CODE,(select SHARE_DESC from FAS_NRDWP_SHARE f3 where f3.SHARE_CODE=f1.SHARE_CODE)as ShareDesc, "+
		        		   " allocation "+
		        		   " from fas_nrdwp_budget f1 "+
		        		   " where accounting_unit_id  =? and "+ 
		        		   " accounting_for_office_id=? and "+
		        		   " financial_year          =? ";         
			         	 ps=connection.prepareStatement(selectQry);
			         	 ps.setInt(1,unit_id);
				         ps.setInt(2, office_id);
				         ps.setString(3, financial_year);
						 result=ps.executeQuery();	
	                	while(result.next()){
		                	xml += "<activity>" + result.getString("activity") + "</activity>";
				            xml += "<actdesc>" + result.getString("actdesc") + "</actdesc>"; 
				            xml += "<COMPONENT_CODE>" + result.getInt("component_code") + "</COMPONENT_CODE>"; 
		                    xml += "<CompDesc>" + result.getString("CompDesc") + "</CompDesc>";  
		                    xml += "<SHARE_CODE>" + result.getInt("SHARE_CODE") + "</SHARE_CODE>";
				            xml += "<ShareDesc>" + result.getString("ShareDesc") + "</ShareDesc>"; 
				            xml += "<allocation>" + result.getInt("allocation") + "</allocation>"; 
		                	count++;
	                	} 
		                
		                 xml =xml+ "<count>"+count+"</count>";
		             }
		           catch(Exception e)
		             {
		            	 System.out.println("Exception in getting values from DB - Edit Values: " + e);
		             }
	            }else{
	            	xml=xml+"<check>NotvalueinTB</check>";	
	            }
	            }
	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in Get for Edit "+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
	           
	        }
	      else  if(strCommand.equals("addDetails")) 
	        {	
	    	  xml="<response><command>addDetails</command>";
	        	int rowsinserted=0;
	        	int rowcount=Integer.parseInt(request.getParameter("rowcount"));
	        	  String[] grid=request.getParameter("grid").split(",");
	        	  int len=grid.length;
	  			//System.out.println("arraylength"+len);
	  			int cc=0,ww=0;
	  			try{
	  				connection.setAutoCommit(false);
				for(int i=0;i<len;i=i+5)
				{

					String activity_code11=grid[i];
	    			int COMPONENT_CODE11=Integer.parseInt(grid[i+1]);   
	    			 int shareCode=0;
	     			String NRDWP1=grid[i+2];
	     			String SMS1=grid[i+3];
	     			String tot=grid[i+4];

	    			int NRDWP11=0;
	    			int SMS11=0;
	    			int up=0;
	    			String sqlinsert="";
	    			if(NRDWP1.equalsIgnoreCase("")||NRDWP1.equalsIgnoreCase(" ")||NRDWP1.equalsIgnoreCase(null)||NRDWP1.equalsIgnoreCase("0")){
	    				System.out.println("  NRDWP1   value "+NRDWP1);
	    				ww++;
	    				
	         		}else{
	         			//System.out.println(" else nrdwp  "+NRDWP1);
	         			NRDWP11=Integer.parseInt(NRDWP1);
	         			String sQry="select SHARE_CODE from FAS_NRDWP_SHARE where SHARE_DESC='NRDWP'";
	          			 ps1=connection.prepareStatement(sQry);
	          			 rs1=ps1.executeQuery();
	          			 if(rs1.next()){
	          				 shareCode=rs1.getInt("SHARE_CODE");
	          			 }
	          			
	            			sqlinsert = "insert into FAS_NRDWP_BUDGET(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACTIVITY,COMPONENT_CODE,SHARE_CODE,ALLOCATION,updated_by_user_id,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?) ";
	        		    	 ps = connection.prepareStatement(sqlinsert); 
	        		    	 ps.setInt(1, unit_id);
	        		    	 ps.setInt(2, office_id);
	        		    	 ps.setString(3, financial_year);
	        		    	 ps.setString(4, activity_code11);
	        		    	 ps.setInt(5, COMPONENT_CODE11);
	        		    	 ps.setInt(6, shareCode);
	        		    	 ps.setInt(7, NRDWP11);
	        		    	 ps.setString(8, userid);
	        		    	 ps.setTimestamp(9, ts);
	        		         up=ps.executeUpdate();
	                     // System.out.println("up ...times  "+up);
	        		       rowsinserted++;	
	         		}
	    			if(SMS1.equalsIgnoreCase("")||SMS1.equalsIgnoreCase(" ")||SMS1.equalsIgnoreCase(null)||SMS1.equalsIgnoreCase("0")){
	    				System.out.println("  SMS1   value "+SMS1);
	    				ww++;
	         		}else{
	         			SMS11=Integer.parseInt(SMS1);
	         			String sQry="select SHARE_CODE from FAS_NRDWP_SHARE where SHARE_DESC='SMS'";
	       			 ps1=connection.prepareStatement(sQry);
	       			 rs1=ps1.executeQuery();
	       			 if(rs1.next()){
	       				 shareCode=rs1.getInt("SHARE_CODE");
	       			 }
	         			sqlinsert = "insert into FAS_NRDWP_BUDGET(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACTIVITY,COMPONENT_CODE,SHARE_CODE,ALLOCATION,updated_by_user_id,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?) ";
	     		    	 ps = connection.prepareStatement(sqlinsert); 
	     		    	 ps.setInt(1, unit_id);
	     		    	 ps.setInt(2, office_id);
	     		    	 ps.setString(3, financial_year);
	     		    	 ps.setString(4, activity_code11);
	     		    	 ps.setInt(5, COMPONENT_CODE11);
	     		    	 ps.setInt(6, shareCode);
	     		    	 ps.setInt(7, SMS11);
	     		    	 ps.setString(8, userid);
	     		    	 ps.setTimestamp(9, ts);
	     		         up=ps.executeUpdate();
	                  // System.out.println("up ..." +ii+"times  "+up);
	     		       rowsinserted++;
	         		}
				
					cc++;
				}

	  int ss11=((rowcount-1)*2)-ww;
	   System.out.println(" rowsinserted "+rowsinserted +" ww "+ww +" ss11 "+ss11);
	     	 if(rowsinserted==ss11){
	                 connection.commit();
	                 xml=xml+"<flag>success</flag>";
	                 //sendMessage(response,"Inserted Successfully","ok");
	              }
	              else
	              {
	                 connection.rollback();  
	                 xml=xml+"<flag>failure</flag>";
	                // sendMessage(response,"The values Are Not Saved ","ok");
	              }
	     }catch(Exception e){
	    	 System.out.println("Exception  "+e);
	     }
	   	   
	    }
	      else  if(strCommand.equals("updateDetails")) 
	        {	
	    	  xml="<response><command>updateDetails</command>";
	        	int rowcount=Integer.parseInt(request.getParameter("rowcount"));
       			/*String[] recordcot=request.getParameterValues("serialNo");
       			String[] actcode=request.getParameterValues("activity_code");
       			String[] compcode=request.getParameterValues("COMPONENT_CODE");      			
       			String[] sharcode=request.getParameterValues("SHARE_CODE");
       			String[] allocatn=request.getParameterValues("allocation");*/
       			
	  			String sqlupdate="";
	  			int up=0,rowsupdated=0;

	  			  String[] grid=request.getParameter("grid").split(",");
	        	  int len=grid.length;
	  			System.out.println("arraylength"+len);
	  			try{
	  				connection.setAutoCommit(false);
				for(int i=0;i<len;i=i+4)
				{

				/*for(int i=0;i<rowcount;i=i++)
				{
					*/
					/*int recordcot1=Integer.parseInt(recordcot[i]);
	       			String actcode1=actcode[i];
	       			int compcode1=Integer.parseInt(compcode[i]);      			
	       			int sharcode1=Integer.parseInt(sharcode[i]);
	       			int allocatn1=Integer.parseInt(allocatn[i]);*/
	
					//int recordcot1=Integer.parseInt(grid[i]);
	       			String actcode1=grid[i];
	       			int compcode1=Integer.parseInt(grid[i+1]);      			
	       			int sharcode1=Integer.parseInt(grid[i+2]);
	       			int allocatn1=Integer.parseInt(grid[i+3]);
	            			sqlupdate = "update FAS_NRDWP_BUDGET set ALLOCATION=?,updated_by_user_id=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=? AND ACTIVITY=? AND COMPONENT_CODE=? AND SHARE_CODE=?";
	        		    	 ps = connection.prepareStatement(sqlupdate); 
	        		    	 ps.setInt(1, allocatn1);
	        		    	 ps.setString(2, userid);
	        		    	 ps.setTimestamp(3, ts); 
	        		    	 ps.setInt(4, unit_id);
	        		    	 ps.setInt(5, office_id);
	        		    	 ps.setString(6, financial_year);
	        		    	 ps.setString(7, actcode1);
	        		    	 ps.setInt(8, compcode1);
	        		    	 ps.setInt(9, sharcode1);
	        		         up=ps.executeUpdate();
	                     // System.out.println("up ...times  "+up);
	        		       rowsupdated++;	
					}

	     	 if(rowsupdated==rowcount){
	                 connection.commit();
	                 xml=xml+"<flag>success</flag>";
	              }
	              else
	              {
	                 connection.rollback();  
	                 xml=xml+"<flag>failure</flag>";
	              }
	     }catch(Exception e){
	    	 System.out.println("Exception  "+e);
	     }
	   	   
	    } else  if(strCommand.equals("deleteDetails")) 
        {	
	    	  xml="<response><command>deleteDetails</command>";
	        	int rowcount=Integer.parseInt(request.getParameter("rowcount"));
     			/*String[] recordcot=request.getParameterValues("serialNo");
     			String[] actcode=request.getParameterValues("activity_code");
     			String[] compcode=request.getParameterValues("COMPONENT_CODE");      			
     			String[] sharcode=request.getParameterValues("SHARE_CODE");
     			String[] allocatn=request.getParameterValues("allocation");*/
     			
	  			String sqlupdate="";
	  			int up=0,rowsdelete=0;

	  			  String[] grid=request.getParameter("grid").split(",");
	        	  int len=grid.length;
	  			System.out.println("arraylength"+len);
	  			try{
	  				connection.setAutoCommit(false);
				for(int i=0;i<len;i=i+4)
				{

	       			String actcode1=grid[i];
	       			int compcode1=Integer.parseInt(grid[i+1]);      			
	       			int sharcode1=Integer.parseInt(grid[i+2]);
	       			int allocatn1=Integer.parseInt(grid[i+3]);
	            			sqlupdate = "delete from FAS_NRDWP_BUDGET where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=? AND ACTIVITY=? AND COMPONENT_CODE=? AND SHARE_CODE=?";
	        		    	 ps = connection.prepareStatement(sqlupdate);  
	        		    	 ps.setInt(1, unit_id);
	        		    	 ps.setInt(2, office_id);
	        		    	 ps.setString(3, financial_year);
	        		    	 ps.setString(4, actcode1);
	        		    	 ps.setInt(5, compcode1);
	        		    	 ps.setInt(6, sharcode1);
	        		         up=ps.executeUpdate();
	                     // System.out.println("up ...times  "+up);
	        		       rowsdelete++;	
					}

	     	 if(rowsdelete==rowcount){
	                 connection.commit();
	                 xml=xml+"<flag>success</flag>";
	              }
	              else
	              {
	                 connection.rollback();  
	                 xml=xml+"<flag>failure</flag>";
	              }
	     }catch(Exception e){
	    	 System.out.println("Exception  "+e);
	     }
	   	   
	    }
	     
	      xml=xml+"</response>"; 
	       System.out.println("xml is : " + xml);
	        pw.write(xml);
	        pw.flush();
	        pw.close();
	}

/*	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection=null;
        Statement statement=null;
        ResultSet rs1=null,rs2=null; 
   	    PreparedStatement ps=null,ps1=null,ps2=null;
      
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
              
           ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim();

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
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        if(strCommand.equals("addDetails")) 
        {	
        	String shareval="";
        	 String[] shareval1=null;
        	int sharecod=0,ccshare=0;
        	try{
        	 String sQry1="select SHARE_CODE,SHARE_DESC  from FAS_NRDWP_SHARE order by SHARE_CODE";
   			 ps1=connection.prepareStatement(sQry1);
   			 rs1=ps1.executeQuery();
   			 while(rs1.next()){
   				sharecod=rs1.getInt("SHARE_CODE");
   				shareval=   rs1.getString("SHARE_DESC").trim(); 
		            ccshare++; 
		           // shareval1=request.getParameterValues(shareval);   
		            
   			 }
   		//	System.out.println(" shareval1 "+shareval1);
        	}catch(Exception e){
        		System.out.println(" get values "+e);
        	}
	
   	    String[] serialcount=request.getParameterValues("serialNo");
	    String[] activity_code1=request.getParameterValues("activity_code");
     	String[] COMPONENT_CODE1=request.getParameterValues("COMPONENT_CODE");
     
     	
     	//String[] SMS=request.getParameterValues("SMS");
     //	String[] NRDWP=request.getParameterValues("NRDWP");
        
     	int ss=serialcount.length;
//int getv=ss*ccshare;
int getvv=(ccshare-1);
String[] nrdwp=null;
String[] sms=null;
//System.out.println(" getv "+getv);
    	for(int ii=0,j=0;ii<grid_val.length;ii=ii+ccshare,j++){ 
    		nrdwp[j]=grid_val[ii];
    		sms[j]=grid_val[ii+1];	    		
    	}
     System.out.println("serialcount leng  =="+ss);
     int rowsinserted=0;
    try{
    	  connection.setAutoCommit(false);
    int up=0,ww=0;
    
     	for(int ii=0;ii<ss;ii++){
     		for(int v=0;v<ccshare;v++){
     			String[] grid_val=request.getParameterValues("gridval"+v);
     			
     		}
     		
     		String sqlinsert="";    
     		try 
              {	
     			String activity_code11=activity_code1[ii];
    			int COMPONENT_CODE11=Integer.parseInt(COMPONENT_CODE1[ii]);   
    			 int shareCode=0;
    			 for(int j=0;j<grid_val.length;j=j+ccshare){ 
    		    		nrdwp[j]=grid_val[j];
    		    		sms[j]=grid_val[j+1];	    		
    		    	}
     			String NRDWP1=nrdwp[ii];
    			int NRDWP11=0;
    			if(NRDWP1.equalsIgnoreCase("")||NRDWP1.equalsIgnoreCase(" ")||NRDWP1.equalsIgnoreCase(null)||NRDWP1.equalsIgnoreCase("0")){
    				//System.out.println("  NRDWP1   value "+NRDWP1);
    				ww++;
    				
         		}else{
         			NRDWP11=Integer.parseInt(NRDWP1);
         			String sQry="select SHARE_CODE from FAS_NRDWP_SHARE where SHARE_DESC='NRDWP'";
          			 ps1=connection.prepareStatement(sQry);
          			 rs1=ps1.executeQuery();
          			 if(rs1.next()){
          				 shareCode=rs1.getInt("SHARE_CODE");
          			 }
            			sqlinsert = "insert into FAS_NRDWP_BUDGET(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACTIVITY,COMPONENT_CODE,SHARE_CODE,ALLOCATION,updated_by_user_id,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?) ";
        		    	 ps = connection.prepareStatement(sqlinsert); 
        		    	 ps.setInt(1, unit_id);
        		    	 ps.setInt(2, office_id);
        		    	 ps.setString(3, financial_year);
        		    	 ps.setString(4, activity_code11);
        		    	 ps.setInt(5, COMPONENT_CODE11);
        		    	 ps.setInt(6, shareCode);
        		    	 ps.setInt(7, NRDWP11);
        		    	 ps.setString(8, userid);
        		    	 ps.setTimestamp(9, ts);
        		         up=ps.executeUpdate();
                     // System.out.println("up ..." +ii+"times  "+up);
        		       rowsinserted++;	
         		}
     			
     			//---------------------------------------------
			
			String SMS1=sms[ii];
			int SMS11=0;
			System.out.println(" activity_code11 "+activity_code11);
			System.out.println(" COMPONENT_CODE11  "+COMPONENT_CODE11);
			System.out.println(" SMS11  "+SMS11);
			//System.out.println(" NRDWP11 "+NRDWP11);
				
			if(SMS1.equalsIgnoreCase("")||SMS1.equalsIgnoreCase(" ")||SMS1.equalsIgnoreCase(null)||SMS1.equalsIgnoreCase("0")){
				//System.out.println("  SMS1   value "+SMS1);
				ww++;
     		}else{
     			SMS11=Integer.parseInt(sms[ii]);
     			String sQry="select SHARE_CODE from FAS_NRDWP_SHARE where SHARE_DESC='SMS'";
   			 ps1=connection.prepareStatement(sQry);
   			 rs1=ps1.executeQuery();
   			 if(rs1.next()){
   				 shareCode=rs1.getInt("SHARE_CODE");
   			 }
     			sqlinsert = "insert into FAS_NRDWP_BUDGET(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACTIVITY,COMPONENT_CODE,SHARE_CODE,ALLOCATION,updated_by_user_id,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?) ";
 		    	 ps = connection.prepareStatement(sqlinsert); 
 		    	 ps.setInt(1, unit_id);
 		    	 ps.setInt(2, office_id);
 		    	 ps.setString(3, financial_year);
 		    	 ps.setString(4, activity_code11);
 		    	 ps.setInt(5, COMPONENT_CODE11);
 		    	 ps.setInt(6, shareCode);
 		    	 ps.setInt(7, SMS11);
 		    	 ps.setString(8, userid);
 		    	 ps.setTimestamp(9, ts);
 		         up=ps.executeUpdate();
              // System.out.println("up ..." +ii+"times  "+up);
 		       rowsinserted++;
     		}
				

				} catch (Exception e) {
					System.out.println("exception......in insert "+e);			
				}
				
             } 
  
   int ss11=(ss*2)-ww;
   System.out.println(" rowsinserted "+rowsinserted +" ww "+ww +" ss11 "+ss11);
     	 if(rowsinserted==ss11){
                 connection.commit();
                 sendMessage(response,"Inserted Successfully","ok");
              }
              else
              {
                 connection.rollback();    
                 sendMessage(response,"The values Are Not Saved ","ok");
              }
     }catch(Exception e){
    	 System.out.println("Exception  "+e);
     }
   	   
    }	     
       }*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection=null;
        Statement statement=null;
        ResultSet rs1=null,rs2=null; 
   	    PreparedStatement ps=null,ps1=null,ps2=null;
      
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
       // System.out.println("Session id is:"+userid);
        response.setContentType("text/xml"); 
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : " + strCommand);
        	
        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
        	office_id = Integer.parseInt(request.getParameter("office_id"));
        	financial_year = request.getParameter("financial_year");
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        if(strCommand.equals("addDetails")) 
        {	
        	
        	int ccshare=0,rowsinserted=0;
        	int rowcount=Integer.parseInt(request.getParameter("rowcount"));
        	  String[] grid=request.getParameter("grid").split(",");
        	  int len=grid.length;
  			System.out.println("arraylength"+len);
  			int cc=0,ww=0;
  			try{
  				connection.setAutoCommit(false);
			for(int i=0;i<len;i=i+5)
			{

				String activity_code11=grid[i];
    			int COMPONENT_CODE11=Integer.parseInt(grid[i+1]);   
    			 int shareCode=0;
     			String NRDWP1=grid[i+2];
     			String SMS1=grid[i+3];
     			String tot=grid[i+4];
     			
     			System.out.println(" activity_code11 "+activity_code11);
     			System.out.println(" COMPONENT_CODE11 "+COMPONENT_CODE11);
     			System.out.println(" NRDWP1 "+NRDWP1);
     			System.out.println(" SMS1 "+SMS1);
     			System.out.println(" tot "+tot);
    			int NRDWP11=0;
    			int SMS11=0;
    			int up=0;
    			String sqlinsert="";
    			if(NRDWP1.equalsIgnoreCase("")||NRDWP1.equalsIgnoreCase(" ")||NRDWP1.equalsIgnoreCase(null)||NRDWP1.equalsIgnoreCase("0")){
    				System.out.println("  NRDWP1   value "+NRDWP1);
    				ww++;
    				
         		}else{
         			//System.out.println(" else nrdwp  "+NRDWP1);
         			NRDWP11=Integer.parseInt(NRDWP1);
         			String sQry="select SHARE_CODE from FAS_NRDWP_SHARE where SHARE_DESC='NRDWP'";
          			 ps1=connection.prepareStatement(sQry);
          			 rs1=ps1.executeQuery();
          			 if(rs1.next()){
          				 shareCode=rs1.getInt("SHARE_CODE");
          			 }
          			 System.out.println("befor query 111 ");
            			sqlinsert = "insert into FAS_NRDWP_BUDGET(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACTIVITY,COMPONENT_CODE,SHARE_CODE,ALLOCATION,updated_by_user_id,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?) ";
        		    	 ps = connection.prepareStatement(sqlinsert); 
        		    	 ps.setInt(1, unit_id);
        		    	 ps.setInt(2, office_id);
        		    	 ps.setString(3, financial_year);
        		    	 ps.setString(4, activity_code11);
        		    	 ps.setInt(5, COMPONENT_CODE11);
        		    	 ps.setInt(6, shareCode);
        		    	 ps.setInt(7, NRDWP11);
        		    	 ps.setString(8, userid);
        		    	 ps.setTimestamp(9, ts);
        		         up=ps.executeUpdate();
                     // System.out.println("up ...times  "+up);
        		       rowsinserted++;	
         		}
    			if(SMS1.equalsIgnoreCase("")||SMS1.equalsIgnoreCase(" ")||SMS1.equalsIgnoreCase(null)||SMS1.equalsIgnoreCase("0")){
    				System.out.println("  SMS1   value "+SMS1);
    				ww++;
         		}else{
         			//System.out.println(" else sms   "+SMS1);
         			SMS11=Integer.parseInt(SMS1);
         			String sQry="select SHARE_CODE from FAS_NRDWP_SHARE where SHARE_DESC='SMS'";
       			 ps1=connection.prepareStatement(sQry);
       			 rs1=ps1.executeQuery();
       			 if(rs1.next()){
       				 shareCode=rs1.getInt("SHARE_CODE");
       			 }
         			sqlinsert = "insert into FAS_NRDWP_BUDGET(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACTIVITY,COMPONENT_CODE,SHARE_CODE,ALLOCATION,updated_by_user_id,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?) ";
     		    	 ps = connection.prepareStatement(sqlinsert); 
     		    	 ps.setInt(1, unit_id);
     		    	 ps.setInt(2, office_id);
     		    	 ps.setString(3, financial_year);
     		    	 ps.setString(4, activity_code11);
     		    	 ps.setInt(5, COMPONENT_CODE11);
     		    	 ps.setInt(6, shareCode);
     		    	 ps.setInt(7, SMS11);
     		    	 ps.setString(8, userid);
     		    	 ps.setTimestamp(9, ts);
     		         up=ps.executeUpdate();
                  // System.out.println("up ..." +ii+"times  "+up);
     		       rowsinserted++;
         		}
			
				cc++;
			}
  			/*}catch(Exception e){
  				System.out.println("Exception  "+e);
  			}*/
   	   /* String[] serialcount=request.getParameterValues("serialNo");
	    String[] activity_code1=request.getParameterValues("activity_code");
     	String[] COMPONENT_CODE1=request.getParameterValues("COMPONENT_CODE");
     
     	
     	//String[] SMS=request.getParameterValues("SMS");
     //	String[] NRDWP=request.getParameterValues("NRDWP");
        
     	int ss=serialcount.length;
//int getv=ss*ccshare;
int getvv=(ccshare-1);
String[] nrdwp=null;
String[] sms=null;

     System.out.println("serialcount leng  =="+ss);
     int rowsinserted=0;
    try{
    	  connection.setAutoCommit(false);
    int up=0,ww=0;
    
     	for(int ii=0;ii<ss;ii++){
     		
     		String sqlinsert="";    
     		try 
              {	
     			String activity_code11=activity_code1[ii];
    			int COMPONENT_CODE11=Integer.parseInt(COMPONENT_CODE1[ii]);   
    			 int shareCode=0;
    			 for(int j=0;j<grid_val.length;j=j+ccshare){ 
    		    		nrdwp[j]=grid_val[j];
    		    		sms[j]=grid_val[j+1];	    		
    		    	}
     			String NRDWP1=nrdwp[ii];
    			int NRDWP11=0;
    			if(NRDWP1.equalsIgnoreCase("")||NRDWP1.equalsIgnoreCase(" ")||NRDWP1.equalsIgnoreCase(null)||NRDWP1.equalsIgnoreCase("0")){
    				//System.out.println("  NRDWP1   value "+NRDWP1);
    				ww++;
    				
         		}else{
         			NRDWP11=Integer.parseInt(NRDWP1);
         			String sQry="select SHARE_CODE from FAS_NRDWP_SHARE where SHARE_DESC='NRDWP'";
          			 ps1=connection.prepareStatement(sQry);
          			 rs1=ps1.executeQuery();
          			 if(rs1.next()){
          				 shareCode=rs1.getInt("SHARE_CODE");
          			 }
            			sqlinsert = "insert into FAS_NRDWP_BUDGET(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACTIVITY,COMPONENT_CODE,SHARE_CODE,ALLOCATION,updated_by_user_id,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?) ";
        		    	 ps = connection.prepareStatement(sqlinsert); 
        		    	 ps.setInt(1, unit_id);
        		    	 ps.setInt(2, office_id);
        		    	 ps.setString(3, financial_year);
        		    	 ps.setString(4, activity_code11);
        		    	 ps.setInt(5, COMPONENT_CODE11);
        		    	 ps.setInt(6, shareCode);
        		    	 ps.setInt(7, NRDWP11);
        		    	 ps.setString(8, userid);
        		    	 ps.setTimestamp(9, ts);
        		         up=ps.executeUpdate();
                     // System.out.println("up ..." +ii+"times  "+up);
        		       rowsinserted++;	
         		}
     			
     			//---------------------------------------------
			
			String SMS1=sms[ii];
			int SMS11=0;
			System.out.println(" activity_code11 "+activity_code11);
			System.out.println(" COMPONENT_CODE11  "+COMPONENT_CODE11);
			System.out.println(" SMS11  "+SMS11);
			//System.out.println(" NRDWP11 "+NRDWP11);
				
			if(SMS1.equalsIgnoreCase("")||SMS1.equalsIgnoreCase(" ")||SMS1.equalsIgnoreCase(null)||SMS1.equalsIgnoreCase("0")){
				//System.out.println("  SMS1   value "+SMS1);
				ww++;
     		}else{
     			SMS11=Integer.parseInt(sms[ii]);
     			String sQry="select SHARE_CODE from FAS_NRDWP_SHARE where SHARE_DESC='SMS'";
   			 ps1=connection.prepareStatement(sQry);
   			 rs1=ps1.executeQuery();
   			 if(rs1.next()){
   				 shareCode=rs1.getInt("SHARE_CODE");
   			 }
     			sqlinsert = "insert into FAS_NRDWP_BUDGET(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACTIVITY,COMPONENT_CODE,SHARE_CODE,ALLOCATION,updated_by_user_id,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?) ";
 		    	 ps = connection.prepareStatement(sqlinsert); 
 		    	 ps.setInt(1, unit_id);
 		    	 ps.setInt(2, office_id);
 		    	 ps.setString(3, financial_year);
 		    	 ps.setString(4, activity_code11);
 		    	 ps.setInt(5, COMPONENT_CODE11);
 		    	 ps.setInt(6, shareCode);
 		    	 ps.setInt(7, SMS11);
 		    	 ps.setString(8, userid);
 		    	 ps.setTimestamp(9, ts);
 		         up=ps.executeUpdate();
              // System.out.println("up ..." +ii+"times  "+up);
 		       rowsinserted++;
     		}
				

				} catch (Exception e) {
					System.out.println("exception......in insert "+e);			
				}
				
             } */
  
  int ss11=((rowcount-1)*2)-ww;
   System.out.println(" rowsinserted "+rowsinserted +" ww "+ww +" ss11 "+ss11);
     	 if(rowsinserted==ss11){
                 connection.commit();
                 sendMessage(response,"Inserted Successfully","ok");
              }
              else
              {
                 connection.rollback();    
                 sendMessage(response,"The values Are Not Saved ","ok");
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
 		String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
 		response.sendRedirect(url);
 	}
 	catch(Exception e)
 	{
 		System.out.println("error in messenger"+e);
 	}        	
 }

}

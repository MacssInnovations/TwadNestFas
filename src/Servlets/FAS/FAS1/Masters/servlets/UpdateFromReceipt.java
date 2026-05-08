package Servlets.FAS.FAS1.Masters.servlets;

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
 * Servlet implementation class UpdateFromReceipt
 */
public class UpdateFromReceipt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
   
    public UpdateFromReceipt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	        }
	        catch(Exception e)
	        {
	          e.printStackTrace();
	        }
	     
	        try
	        {
	        	
	        
	        	
	        /*	System.out.println("accounting_unit_id : " + unit_id);
	        	System.out.println("accounting_unit_office_id : " + office_id);
	        	System.out.println("financial_year : " + financial_year);
	        	System.out.println("assetmajor : " + assetmajor);*/
	        	
	        }
	        catch(Exception e)
	        { 
	            System.out.println("IGNORABLE Exception getting values from jsp " + e);
	        }         
	      
	      if(strCommand.equals("GoGet"))
	        { unit_id = Integer.parseInt(request.getParameter("unit_id"));
        	office_id = Integer.parseInt(request.getParameter("office_id"));
        	financial_year = request.getParameter("financial_year");
	    		assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
	        	int count=0;
	        	//System.out.println("\n*************\nGo Get\n**************\n");
	            xml="<response><command>GoGet</command>";
	            try 
	            {
	              //  System.out.println("goget");
	                
	                String selectQuery="select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH, "+
	                " ASSET_CODE,RECEIPT_NO,RECEIPT_DATE,RECEIVED_FROM_LEVEL_ID,RECEIVED_FROM_OFFICE,MBOOK_NO,MBOOK_DATE,RECEIVED_QTY, "+
	                " REMARKS,TDA_ACCEPTING_JVR_NO,TDA_ACCEPTING_JVR_DATE,RECEIVED_VALUE,PARTICULARS  "+ 
	                "  from FAS_ASSETS_RECEIPT "+
	             " WHERE ACCOUNTING_UNIT_ID = " + unit_id +
			     " AND ACCOUNTING_FOR_OFFICE_ID = " + office_id +
			     " AND FINANCIAL_YEAR = '" + financial_year + "'" +
			     " AND ASSET_MAJOR_CLASS_CODE="+assetmajor+" AND UPDATED_TO_A52 is NULL";
	                System.out.println(selectQuery);
	            // result = statement.executeQuery(selectQuery);            
	         	ps=connection.prepareStatement(selectQuery);
				//System.out.println(selectQuery);
				result=ps.executeQuery();	
	             
	           try
	             {
	        	  // System.out.println("inside try");
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
	                     xml=xml+"<RECEIPT_NO>" + result.getInt("RECEIPT_NO") + "</RECEIPT_NO>";
	                     Date dateformat=result.getDate("RECEIPT_DATE");
	                	 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	                	 String changedDate = df.format(dateformat);
	                 	 xml += "<RECEIPT_DATE>" +changedDate+"</RECEIPT_DATE>"; 
	                	// xml += "<RECEIPT_DATE>" +result.getDate("RECEIPT_DATE")+"</RECEIPT_DATE>"; 
	                     xml=xml+"<RECEIVED_FROM_LEVEL_ID><![CDATA[" + result.getString("RECEIVED_FROM_LEVEL_ID") + "]]></RECEIVED_FROM_LEVEL_ID>";
	                     xml=xml+"<RECEIVED_FROM_OFFICE>" + result.getString("RECEIVED_FROM_OFFICE") + "</RECEIVED_FROM_OFFICE>";
	                     xml=xml+"<MBOOK_NO>" + result.getInt("MBOOK_NO") + "</MBOOK_NO>";
	                     Date dateformat1=result.getDate("MBOOK_DATE");
	                     System.out.println("dateformat1 "+dateformat1);
	                    
	                	 DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
	                	 System.out.println("df1 "+df1);
	                	 String changedDate1 = df1.format(dateformat1);
	                	 System.out.println("changedDate1  "+changedDate1);
	                 	 xml += "<MBOOK_DATE>" +changedDate1+"</MBOOK_DATE>"; 
	                	// xml += "<MBOOK_DATE>" +result.getDate("MBOOK_DATE")+"</MBOOK_DATE>"; 
	                     xml=xml+"<RECEIVED_QTY>" + result.getInt("RECEIVED_QTY") + "</RECEIVED_QTY>";
	                 	 xml=xml+"<RECEIVED_VALUE>" + result.getInt("RECEIVED_VALUE") + "</RECEIVED_VALUE>";
	                     xml=xml+"<REMARKS>" + result.getString("REMARKS") + "</REMARKS>";  
	                     xml=xml+"<PARTICULARS><![CDATA[" +result.getString("PARTICULARS") +"]]></PARTICULARS>";  
	                     
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
	        /*else if(strCommand.equals("checkVerifyA52"))
	        { 
	        	System.out.println("\n*************\n checkVerifyA52 \n**************\n");
	            xml="<response><command>checkVerifyA52</command>";
	            try 
	            {
	                System.out.println("bef res");
	              System.out.println(unit_id);
	              System.out.println(office_id);
	              System.out.println(financial_year);
	              System.out.println(asset_code);
	             result = statement.executeQuery("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,NUM_OB_EDIT_STATUS from FAS_ASSETS_NUM_OB_EDIT where ACCOUNTING_UNIT_ID="+unit_id+" and ACCOUNTING_FOR_OFFICE_ID="+office_id+"");
	             
	                //System.out.println("aft res");
	             try
	             {
	            	 xml=xml+"<flag>success</flag>";
	            	 String valExists = "No";
	                 while(result.next())
	                 { 
	                	 valExists = "Yes";
	                	 xml += "<ACCOUNTING_UNIT_ID>" + result.getInt("ACCOUNTING_UNIT_ID") + "</ACCOUNTING_UNIT_ID>";
	                	 xml += "<NUM_OB_EDIT_STATUS>" + result.getString("NUM_OB_EDIT_STATUS") + "</NUM_OB_EDIT_STATUS>";
	                 }

	                 xml += "<exists>"+valExists+"</exists>";
	             }catch(Exception e)
	             {
	            	 System.out.println("Exception in checking frezed: " + e);
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
	        } */
	        /*else if(strCommand.equals("updateTotally")) 
	        {
	        	int rowcount=Integer.parseInt(request.getParameter("rowcount"));
	        	System.out.println("rowcount"+rowcount );
	        	int up=0;
	        	 System.out.println("update tortla iunit "+unit_id);
	              System.out.println("update tortla office "+office_id);
	              System.out.println("update tortla year "+financial_year);
	             
	     	System.out.println("update totally");
	   	   xml="<response><command>updateTotally1</command>";
	   	   int cc=0;
	   	   
	   	 String[] grid=request.getParameter("grid").split(",");
			
			int len=grid.length;
			System.out.println("arraylength"+len);
			
			
			for(int i=0;i<len;i=i+12)
			{
				
				//String sNO=grid[i];
				String casbyr=grid[i+0];
				String casbmth=grid[i+1];
				
				//int sNO=Integer.parseInt(grid[i]);
				int casbyr=Integer.parseInt(grid[i]);
				int casbmth=Integer.parseInt(grid[i+1]);
				int asscode=Integer.parseInt(grid[i+2]);
				int reno=Integer.parseInt(grid[i+3]);
				String recda=grid[i+4];
				String refromoff=grid[i+5];
				int mno=Integer.parseInt(grid[i+6]);
				String mdat=grid[i+7];
				int recqty=Integer.parseInt(grid[i+8]);
				String rema=grid[i+9];
				String refrmid=grid[i+10];
				int office=Integer.parseInt(grid[i+11]);
				
				String asscode=grid[i+2];
				String reno=grid[i+3];
				String recda=grid[i+4];
				String refromoff=grid[i+5];
				String mno=grid[i+6];
				String mdat=grid[i+7];
				String recqty=grid[i+8];
				String rema=grid[i+9];
				String refrmid=grid[i+10];
				String office=grid[i+11];
				
				//System.out.println("i  "+i+"casbyr "+casbyr+"casbmth "+casbmth+"asscode "+asscode+"reno "+reno+"recda "+recda +" refromoff"+refromoff);
				//System.out.println("mno "+mno+"mdat "+mdat+"recqty "+recqty+"rema "+rema+"refrmid "+refrmid +" office "+office);

				String getValuefromtab="select RECIEPTS_YEAR_QTY from FAS_A52_REGISTER where "+
				" ACCOUNTING_UNIT_ID= "+unit_id +
				" and ACCOUNTING_UNIT_OFFICE_ID="+office_id+
				" and FINANCIAL_YEAR='" + financial_year + "'" +
				" and ASSET_MAJOR_CLASS_CODE="+assetmajor +
				"and ASSET_CODE=" +asscode ;
				String sqlUpdate ="";
				try{
					
				
				sqlUpdate = " UPDATE FAS_A52_REGISTER SET " +
          	  "RECIEPTS_YEAR_QTY=decode(RECIEPTS_YEAR_QTY,null,0,RECIEPTS_YEAR_QTY)+"+recqty+
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
					//System.out.println("up<0");
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
		     	String[] MBOOK_DATE=request.getParameterValues("MBOOK_DATE");
		     	String[] MBOOK_NO=request.getParameterValues("MBOOK_NO");
		     	String[] RECEIVED_FROM_OFFICE=request.getParameterValues("RECEIVED_FROM_OFFICE");
		    	String[] RECEIPT_DATE=request.getParameterValues("RECEIPT_DATE");
		    	String[] RECEIPT_NO=request.getParameterValues("RECEIPT_NO");
		     	String[] RECEIVED_QTY=request.getParameterValues("RECEIVED_QTY");	    	
		     	String[] RECEIVED_VALUE=request.getParameterValues("RECEIVED_VALUE");
		     	String[] REMARKS=request.getParameterValues("remarks");
		    	String[] officeid1=request.getParameterValues("officeid1");
		    	String[] unitid1levelid=request.getParameterValues("unitid1");
		    	String[]  PARTICULARS=request.getParameterValues("PARTICULARS");
		     	int ss=serialcount.length;
		     
		     System.out.println("serialcount leng  =="+ss);
		     int rowsupdated=0,yy=0;
		     int circleid=0;
		     try{
		    		String findCircleid="select ACCT_RENDERING_UNIT_ID From FAS_ASSET_VAL_AC_RENDER_UNITS where RENDERING_UNIT_OFFICE_ID="+office_id;
					PreparedStatement ps1 = connection.prepareStatement(findCircleid);  
						ResultSet rs1=ps1.executeQuery();
						if(rs1.next()){
							circleid=rs1.getInt("ACCT_RENDERING_UNIT_ID");
						}
							
		    	  connection.setAutoCommit(false);
		    int up=0;
		  	
		     	for(int ii=0;ii<ss;ii++){
		     		  up=0;
		     	System.out.println("iiiiiiiiiiii >>> "+ii);	
		     		String sqlUpdate="";    
		     		try 
		              {	
		     			int CASHBOOK_YEAR1=Integer.parseInt(CASHBOOK_YEAR[ii]);
		     			int CASHBOOK_MONTH1=Integer.parseInt(CASHBOOK_MONTH[ii]);
					int assetno=Integer.parseInt(asset_code[ii]);
					String MBOOK_DATE1=MBOOK_DATE[ii];
					int MBOOK_NO1=Integer.parseInt(MBOOK_NO[ii]); 
					String RECEIVED_FROM_OFFICE1=RECEIVED_FROM_OFFICE[ii]; 
					String RECEIPT_DATE1=RECEIPT_DATE[ii];
					int RECEIPT_NO1=Integer.parseInt(RECEIPT_NO[ii]); 
					int RECEIVED_QTY1=Integer.parseInt(RECEIVED_QTY[ii]);
					int RECEIVED_VALUE1=Integer.parseInt(RECEIVED_VALUE[ii]);
					int oofid=Integer.parseInt(officeid1[ii]);	
					String rem=REMARKS[ii];
					String part=PARTICULARS[ii];
					String unitid1levelid1=unitid1levelid[ii];
					System.out.println(ii+" unitid1levelid1  "+unitid1levelid1);
					System.out.println(ii+" assetno  "+assetno);
						
						String checkqry="select 'X' from FAS_A52_REGISTER_EDIT where ACCOUNTING_UNIT_ID ="+circleid+" and ACCOUNTING_UNIT_OFFICE_ID ="+office_id+" and FINANCIAL_YEAR= '"+financial_year+"'"+
						" and ASSET_MAJOR_CLASS_CODE= "+assetmajor+" and ASSET_CODE="+assetno;
						System.out.println("checkqry    "+checkqry);
						PreparedStatement pss=connection.prepareStatement(checkqry);
						ResultSet rss=pss.executeQuery();
						if(rss.next()){
							yy=0;
						}else{

						yy=1;
					sqlUpdate="insert into FAS_A52_REGISTER_EDIT(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,FINANCIAL_YEAR, "+
						" ASSET_MAJOR_CLASS_CODE, "+
						" ASSET_MINOR_CLASS_CODE, "+
						" ASSET_CODE, "+
						" RECIEPTS_YEAR_DR_QTY, "+
						" RECIEPTS_YR_DR_VALUE, "+						
						" UPDATED_BY_USERID, "+
						" UPDATED_DATE, "+
						" REMARKS,OFFICE_WING_SINO) values(?,?,?,?,?,?,?,?,?,?,?,0) ";
		  		    	 ps = connection.prepareStatement(sqlUpdate);  
		  		    	  ps.setInt(1, circleid);
		                 ps.setInt(2, office_id);
		                 ps.setString(3,financial_year);
		                 ps.setInt(4,assetmajor);	                 
		                 ps.setInt(5,1);
		                 ps.setInt(6,assetno);
		                ps.setInt(7,RECEIVED_QTY1);
		                 ps.setInt(8,RECEIVED_VALUE1);
		                 ps.setString(9,userid);
		                 ps.setTimestamp(10,ts);
		                 ps.setString(11,part);
		  		         up=ps.executeUpdate();
		  		       rowsupdated++;
		  		       
		  		       	if(up>0){
		  		    	 
		  		     try{
		  		       String updatedtoRece="update FAS_ASSETS_RECEIPT set UPDATED_TO_A52=? "+
		             " WHERE ACCOUNTING_UNIT_ID = " + unit_id +
				     " AND ACCOUNTING_FOR_OFFICE_ID = " + office_id +
				     " AND FINANCIAL_YEAR = '" + financial_year + "'" +
				     " AND ASSET_MAJOR_CLASS_CODE="+assetmajor+ " AND ASSET_CODE="+assetno ;
		  		       System.out.println("updatedtoRece >> "+updatedtoRece);
		  		     ps1 = connection.prepareStatement(updatedtoRece);  
		  		     ps1.setTimestamp(1,ts);
		  		   int up1=ps1.executeUpdate();
		  		     System.out.println("up1 "+up1);
		  		     }catch(Exception e){
		  		    	 System.out.println("Excep Come here.... ");
		  		    	 e.printStackTrace();
		  		     }
		  		       	}
		  		       
						}
						} catch (Exception e) {
							System.out.println("exception......in full update "+e);			
						}
						
		             } 
		   System.out.println(" rowsupdated "+rowsupdated +" ss "+ss);
		     if(yy==1){
		    	 if(rowsupdated==ss){
	                 connection.commit();
	                 sendMessage(response,"The Receipt of Assets Successfully Updated","ok");
	              }
	              else
	              {
	                 connection.rollback();    
	                 sendMessage(response,"The Receipt of Assets Are Not Saved ","ok");
	              }
		     }else{
		    	 if(rowsupdated==0){
	            	  connection.rollback();    
		                 sendMessage(response,"The Receipt of Assets Are already Exists ","ok");  
	              }
	              else
	              {
	                 connection.rollback();    
	                 sendMessage(response,"The Receipt of Assets Are Not Saved ","ok");
	              }
		    	 
		     }
		     	 if(rowsupdated==ss){
		                 connection.commit();
		                 sendMessage(response,"The Receipt of Assets Successfully Updated","ok");
		              }/*else if(rowsupdated==0){
		            	  connection.rollback();    
			                 sendMessage(response,"The Receipt of Assets Are already Exists ","ok");  
		              }*/
		              else
		              {
		                 connection.rollback();    
		                 sendMessage(response,"The Receipt of Assets Are Not Saved ","ok");
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
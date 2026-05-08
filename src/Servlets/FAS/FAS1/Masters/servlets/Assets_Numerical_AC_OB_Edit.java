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
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Assets_Numerical_AC_OB_Edit
 */
public class Assets_Numerical_AC_OB_Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	
    public Assets_Numerical_AC_OB_Edit() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
   
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
	        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
	        	office_id = Integer.parseInt(request.getParameter("office_id"));
	        	financial_year = request.getParameter("financial_year");
	        	//financial_year="2011-12";
	        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
	        	
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
	        { 
	        	int count=0;
	        	//System.out.println("\n*************\nGo Get\n**************\n");
	            xml="<response><command>GoGet</command>";
	            try 
	            {
	              //  System.out.println("goget");
	                
	                String selectQuery="select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,date_of_entry,"+
	                "asset_code,qty_avl_ason_date,physical_location_code as location_desc,"+
	               // "(select OFFICE_NAME from COM_MST_OFFICES where office_id=physical_location_code) as location_desc,"+
	                " status,remarks,alias_code,qty_available,CORRECTQTY,CORRECTQTYREMARK from fas_assets_num_ob "+
	             " WHERE " +
	            // "accounting_unit_id = " + unit_id +
			   //  " AND" +
			     " ACCOUNTING_FOR_OFFICE_ID = " + office_id +
			     " AND financial_year = '" + financial_year + "'" +
			     " AND ASSET_MAJOR_CLASS_CODE="+assetmajor;
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
	           	  try 
		            {
		              
		             result1 = statement.executeQuery("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,NUM_OB_EDIT_STATUS from FAS_ASSETS_NUM_OB_EDIT where ACCOUNTING_UNIT_ID="+unit_id+" and ACCOUNTING_FOR_OFFICE_ID="+office_id+"");

		            	
		            	 String numstvalExists = "No";
		                 while(result1.next())
		                 { 
		                	 numstvalExists = "Yes";
		                	 int unitiddd=result1.getInt("ACCOUNTING_UNIT_ID");
		                	 String numst=result1.getString("NUM_OB_EDIT_STATUS");
		                	 System.out.println(" unitiddd  numst "+unitiddd+"  "+numst);
		                 }

		                 xml=xml+"<numstvalExists>"+numstvalExists+"</numstvalExists>";
		             
		             
		             result1.close();
		             //response.setHeader("cache-control","no-cache");
		             
		            }
		            catch(Exception e1)
		            {
		            	System.out.println("Exception is in Get"+e1);
		            	
		            }
	                 while(result.next())
	                 { 
	                	 valExists = "Yes";
	                	 xml += "<asset_code>" + result.getInt("asset_code") + "</asset_code>";
			               	 xml += "<accounting_unit_id>" + result.getInt("ACCOUNTING_UNIT_ID") + "</accounting_unit_id>"; 
			            	 xml += "<accounting_unit_office_id>" + result.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</accounting_unit_office_id>"; 
	                	 Date dateformat=result.getDate("date_of_entry");
	                	 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	                	 String changedDate = df.format(dateformat);
	                 	 xml += "<date_of_entry>" +changedDate+"</date_of_entry>"; 
	                     xml=xml+"<qty_avl_ason_date>" + result.getInt("qty_avl_ason_date") + "</qty_avl_ason_date>";
	                     xml=xml+"<location_desc>" + result.getString("location_desc")  +"</location_desc>";
	                     xml=xml+"<status>" + result.getString("status") + "</status>";
	                     xml=xml+"<remarks><![CDATA[" + result.getString("remarks") + "]]></remarks>";
	                     xml=xml+"<qty_available>" + result.getInt("qty_available") + "</qty_available>";
	                     xml=xml+"<CORRECTQTY>" + result.getInt("CORRECTQTY") + "</CORRECTQTY>";
	                     xml=xml+"<CORRECTQTYREMARK>" + result.getString("CORRECTQTYREMARK") + "</CORRECTQTYREMARK>";                    
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
	        } 
	        else if(strCommand.equals("updateTotally")) 
	        {
	        	
	        	int up=0;
	        	/* System.out.println("update tortla iunit "+unit_id);
	              System.out.println("update tortla office "+office_id);
	              System.out.println("update tortla year "+financial_year);
	             */
	     	System.out.println("update totally");
	   	   xml="<response><command>updateTotally1</command>";
	   //	   int rowcout=Integer.parseInt(request.getParameter("rowcount"));
	   	  // int ccc=0;
	   	   
	   	   String[] serialcount=request.getParameterValues("serialNo");
	   	   System.out.println("serialCount len:::"+serialcount.length);
	   	   
	   	 String[] grid=request.getParameter("grid").split(",");
	   
			int len=grid.length;
			
			System.out.println("arraylength=== "+len);
			
			for(int i=0;i<len;i++)
			{
				System.out.println("grid["+i+"]  "+grid[i]);
			}
			/*for(int i=0;i<len;i=i+11)
			{
				String dateofentry=grid[i];
				int assetno=Integer.parseInt(grid[i+1]);
				String locationdesc1=grid[i+2];
				int qtyavlasondate1=Integer.parseInt(grid[i+3]);
			//	int qtyavailable1=Integer.parseInt(grid[i+4]);
				String status1=grid[i+4];
				String rem=grid[i+5];
				int qtyavailable1=Integer.parseInt(grid[i+6]);
				int correctqty1=Integer.parseInt(grid[i+7]);
				String correctqtyremark1=grid[i+9];
				int oofid=Integer.parseInt(grid[i+10]);
				int unid=Integer.parseInt(grid[i+11]);
				
				
			}*/
	     	/*String[] dateofentry=request.getParameterValues("dateofentry");
	     	String[] assetcode=request.getParameterValues("assetcode");
	     	String[] qtyavlasondate=request.getParameterValues("qtyavlasondate");
	     	String[] locationdesc=request.getParameterValues("locationdesc");
	     	String[] status=request.getParameterValues("status");
	     	String[] qtyavailable=request.getParameterValues("qtyavailable");
	     	String[] remark=request.getParameterValues("remarks");	    	
	     	String[] officeid=request.getParameterValues("officeid");
	     	String[] correctqty=request.getParameterValues("correctqty");
	    	String[] correctqtyremark=request.getParameterValues("correctqtyremark");
	      
	     	int ss=assetcode.length;
	     
	     System.out.println("assetcode.length =="+ss);*/
	     
	  	int rowsupdated=0;
	     	/*for(int ii=0;ii<ss;ii++){
	     		
	     		System.out.println("------------------"+ii+"-----------------");
	     		
	     		System.out.println("dateofentry "+dateofentry[ii]);
	     		System.out.println("assetcode "+assetcode[ii]);
	     		System.out.println("qtyavlasondate "+qtyavlasondate[ii]);
	     		System.out.println("locationdesc "+locationdesc[ii]);
	     		System.out.println("status "+status[ii]);
	     		System.out.println("qtyavailable "+qtyavailable[ii]);
	     		System.out.println("remark "+remark[ii]);
	     		
	     		String dateofentry1=dateofentry[ii];
	     		int assetno=Integer.parseInt(assetcode[ii]);
	      		 String locationdesc1=locationdesc[ii];
	     		  int qtyavlasondate1=Integer.parseInt(qtyavlasondate[ii]); 
	              int qtyavailable1=Integer.parseInt(qtyavailable[ii]);
	              int oofid=Integer.parseInt(officeid[ii]);
	              String status1=status[ii];             
	              String rem=remark[ii];
	              int correctqty1=Integer.parseInt(correctqty[ii]);
	             String correctqtyremark1=correctqtyremark[ii];*/
	  	/*
	  	for(int i=0;i<len;i=i+12)
		{
			String dateofentry=grid[i];
			int assetno=Integer.parseInt(grid[i+1]);
			int qtyavlasondate1=Integer.parseInt(grid[i+2]);
			String locationdesc1=grid[i+3];
			String locatio=grid[i+4];
			String status1=grid[i+5];
			String rem=grid[i+6];
			int qtyavailable1=Integer.parseInt(grid[i+7]);
			int correctqty1=Integer.parseInt(grid[i+8]);
			String correctqtyremark1=grid[i+9];
			int unid=Integer.parseInt(grid[i+10]);
			int oofid=Integer.parseInt(grid[i+11]);
	  	
	              String sqlUpdate="";    
	              
	              try 
	              {
	           
	            	  sqlUpdate = " UPDATE fas_assets_num_ob SET " +
	            	  "CORRECTQTY= "+correctqty1+","+
	            	  " CORRECTQTYREMARK='"+correctqtyremark1+"'"+
	    				//"qty_available="+qtyavailable1+
	                    " WHERE accounting_unit_id = "+unit_id+
	                    " AND ACCOUNTING_FOR_OFFICE_ID = "+oofid+
	    				" AND financial_year = '"+financial_year+"'" +
	    				" AND asset_code = "+assetno+
	    				" AND ASSET_MAJOR_CLASS_CODE= "+assetmajor+
	    				" AND qty_avl_ason_date= "+qtyavlasondate1+
	    				" AND status= '"+status1+"'";
	                 System.out.println(sqlUpdate);
	  		    	 ps = connection.prepareStatement(sqlUpdate);  		
	  		         up=ps.executeUpdate();
	                // System.out.println("up ..." +ii+"times  "+up);
	  		       rowsupdated++;
					} catch (Exception e) {
						System.out.println("exception......in full update "+e);			
					}
					
	             }   
	     	 if(rowsupdated==rowcout){
					System.out.println("rowsupdated===ss"+rowsupdated+"  "+rowcout);
					xml+="<flag>success</flag>";
				}else{
					//System.out.println("up<0");
					xml+="<flag>failure</flag>";
				}  */
			xml+="</response>";

	     	}
	        
	       System.out.println("xml is : " + xml);
	        pw.write(xml);
	        pw.flush();
	        pw.close();
	}

	
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
	        	
	     	System.out.println("update totally");
   	
	   	    String[] serialcount=request.getParameterValues("serialNo");
 	     	String[] dateofentry=request.getParameterValues("dateofentry");
	     	String[] assetcode=request.getParameterValues("assetcode");
	     	String[] qtyavlasondate=request.getParameterValues("qtyavlasondate");
	     	String[] locationdesc=request.getParameterValues("locationdesc");
	     	String[] status=request.getParameterValues("status");
	     	String[] qtyavailable=request.getParameterValues("qtyavailable");
	     	String[] remark=request.getParameterValues("remarks");	    	
	     	String[] officeid=request.getParameterValues("officeid");
	     	String[] correctqty=request.getParameterValues("correctqty");
	    	String[] correctqtyremark=request.getParameterValues("correctqtyremark");
	      
	     	int ss=serialcount.length;
	     
	     System.out.println("serialcount leng  =="+ss);
	     int rowsupdated=0;
	     try{
	    	  connection.setAutoCommit(false);
	    int up=0;
	  	
	     	for(int ii=0;ii<ss;ii++){
	     		
	     		/*System.out.println("------------------"+ii+"-----------------");
	     		System.out.println("dateofentry "+dateofentry[ii]);
	     		System.out.println("assetcode "+assetcode[ii]);
	     		System.out.println("qtyavlasondate "+qtyavlasondate[ii]);
	     		System.out.println("locationdesc "+locationdesc[ii]);
	     		System.out.println("status "+status[ii]);
	     		System.out.println("qtyavailable "+qtyavailable[ii]);
	     		System.out.println("remark  officeid[ii] "+remark[ii]+" "+officeid[ii] );
	     		System.out.println("correctqty "+correctqty[ii]);
	     		System.out.println("correctremark "+correctqtyremark[ii]);*/
	     		String correctqtyremark1="";
	     		String dateofentry1="";
	     		String sqlUpdate="";    
	     		try 
	              {	
				dateofentry1=dateofentry[ii];
				int assetno=Integer.parseInt(assetcode[ii]);
				String locationdesc1=locationdesc[ii];
				int qtyavlasondate1=Integer.parseInt(qtyavlasondate[ii]); 
				int qtyavailable1=Integer.parseInt(qtyavailable[ii]);
				int oofid=Integer.parseInt(officeid[ii]);
				String status1=status[ii];             
				String rem=remark[ii];
				int correctqty1=Integer.parseInt(correctqty[ii]);
				correctqtyremark1=correctqtyremark[ii];
				if((correctqtyremark[ii].equalsIgnoreCase(""))||(correctqtyremark[ii].equalsIgnoreCase("null"))){
	     			correctqtyremark1="";
	     		}
				 
           
	            	  sqlUpdate = " UPDATE fas_assets_num_ob SET " +
	            	  "CORRECTQTY= "+correctqty1+","+
	            	  " CORRECTQTYREMARK='"+correctqtyremark1+"'"+
	    				//"qty_available="+qtyavailable1+
	                    " WHERE accounting_unit_id = "+unit_id+
	                    " AND ACCOUNTING_FOR_OFFICE_ID = "+oofid+
	    				" AND financial_year = '"+financial_year+"'" +
	    				" AND asset_code = "+assetno+
	    				" AND ASSET_MAJOR_CLASS_CODE= "+assetmajor+
	    				" AND qty_avl_ason_date= "+qtyavlasondate1+
	    				" AND status= '"+status1+"'";
	                // System.out.println(sqlUpdate);
	  		    	 ps = connection.prepareStatement(sqlUpdate);  		
	  		         up=ps.executeUpdate();
	                // System.out.println("up ..." +ii+"times  "+up);
	  		       rowsupdated++;
					} catch (Exception e) {
						System.out.println("exception......in full update "+e);			
					}
					
	             } 
	   //System.out.println(" rowsupdated "+rowsupdated +" ss "+ss);
	     
	     	 if(rowsupdated==ss){
	                 connection.commit();
	                 sendMessage(response,"The Assets Numerical Update Successfully","ok");
	              }
	              else
	              {
	                 connection.rollback();    
	                 sendMessage(response,"The Assets Numerical Are Not Saved ","ok");
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
     		System.out.println("Inside.....................");
     		String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
     		response.sendRedirect(url);
     	}
     	catch(Exception e)
     	{
     		System.out.println("error in messenger"+e);
     	}        	
     }
}

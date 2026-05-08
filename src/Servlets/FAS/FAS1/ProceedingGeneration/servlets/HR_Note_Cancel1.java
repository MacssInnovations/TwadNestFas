package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class HR_Note_Cancel1
 */
public class HR_Note_Cancel1 extends HttpServlet {
	private static final long serialVersionUID1 = 1L;
	private int txtCB_Year;
	private static final long serialVersionUID = 1L;
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HR_Note_Cancel1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException {
    	 String strCommand=null;


    	 PrintWriter out = response.getWriter();
	     response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	    Connection con = null;
	    
	    try {
	            HttpSession session = request.getSession(false);
	            if (session == null) {
	                    System.out.println(request.getContextPath() + "/index.jsp");
	                    response.sendRedirect(request.getContextPath() + "/index.jsp");
	                    return;
	            }
	            System.out.println(session);

	    } catch (Exception e) {
	            System.out.println("Redirect Error :" + e);
	    }
	    try {
	            ResourceBundle rs1 = ResourceBundle
	                            .getBundle("Servlets.Security.servlets.Config");
	            String ConnectionString = "";

	            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
	            String strdsn = rs1.getString("Config.DSN");
	            String strhostname = rs1.getString("Config.HOST_NAME");
	            String strportno = rs1.getString("Config.PORT_NUMBER");
	            String strsid = rs1.getString("Config.SID");
	            String strdbusername = rs1.getString("Config.USER_NAME");
	            String strdbpassword = rs1.getString("Config.PASSWORD");
	            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	            Class.forName(strDriver.trim());
	            con = DriverManager.getConnection(ConnectionString, strdbusername
	                            .trim(), strdbpassword.trim());
	    } catch (Exception e) {
	            System.out.println("Exception in opening connection :" + e);
	            // sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	    } 
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
	    String update_user=(String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
	    PreparedStatement ps2=null,ps=null,ps1=null;
	    ResultSet rs2=null,rs=null,rs1=null;
	    String cmd="",xml=null;
	    int count=0;
	    int cmbAcc_UnitCode=0;
	    try
	    {
	    cmd=request.getParameter("command");
	   
	    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    
	    }catch (Exception e) {
	            e.printStackTrace();
	    }
	    System.out.println("cmd---------"+cmd);
	    
	
	    
	    
	   
		String cmd1 = null;
		if(cmd.equalsIgnoreCase("LoadBankAccountNumber"))
	    {
	         
	                                                                                                
	            String sql=
	            		
	            		"SELECT F.Hr_Note_No " +
	            		"FROM FAS_HR_NOTE_DETAILS f, " +
	            		"  FAS_HR_SANC_PROC_MST f1 " +
	            		"WHERE F.Accounting_Unit_Id    =F1.Accounting_Unit_Id " +
	            		"AND F.Accounting_For_Office_Id=F1.Accounting_For_Office_Id " +
	            		"AND F.Cashbook_Year           =F1.Cashbook_Year " +
	            		"AND F.Cashbook_Month          =F1.Cashbook_Month " +
	            		"AND Status                    ='L' " +
	            		"AND f.accounting_unit_id        = ?";
	            
	     System.out.println("sql:::"+sql);
	    
	        try
	        {
	                  ps2=con.prepareStatement(sql);
	                 ps2.setInt(1, cmbAcc_UnitCode);
	                  rs2=ps2.executeQuery();
	               
	                  System.out.print("haiiiiiiiiiiiiiiiiiiiiiiiiiii");
	                  xml="<response><command>LoadBankAccountNumber</command>";
	                  
	                  /** Count How many Records are available  */
	                 while(rs2.next()) 
	                  {
	                	  System.out.print("mmmmmmmmmm");
	                    xml=xml+ "<Hr_Note_No>"+ rs2.getInt("Hr_Note_No") +"</Hr_Note_No>";  
	                     
	                    /*xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
	                     xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
	                     xml=xml+ "<opr_mode>"+ rs2.getString("AC_OPERATIONAL_MODE_ID") +"</opr_mode>";                 
	                     xml=xml+ "<acc_desc>"+ rs2.getString("acc_no") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
	                     xml=xml+ "<bank_name>"+ rs2.getString("y_bank_name") +"</bank_name>";                                           
	                     xml=xml+ "<branch_name>"+ rs2.getString("z_BRANCH_NAME") +"</branch_name>";*/
	                     count++;
	                  }
	                  
	                  if(count==0) {
	                      xml=xml+"<flag>NoData</flag>";
	                  }
	                  else{                
	                      xml=xml+"<flag>success</flag>";
	                  }              
	       }
	           catch(Exception e) 
	           {
	                  System.out.println("Exception in assigning..."+e);
	                  xml=xml+"<flag>failure</flag>";
	           }
	           xml = xml + "</response>";
	                    System.out.println(xml);
	                    out.println(xml);
	                    
	    
	    }
	                    
	                    
		 String cmd11 = request.getParameter("command");
		
	                   if(cmd11.equalsIgnoreCase("searchByMonth")) {
	                    	
	                	  
	                    	  String CONTENT_TYPE1 = "text/xml; charset=windows-1252";
	                    	  response.setContentType(CONTENT_TYPE1);
	                    	  String xml1 = "";
	                    	  xml1 = "<response><command>" + cmd11 + "</command>";
	                    	  int cmbOffice_code=0;


	                    	 int cmbAcc_UnitCode1   =
	                    	              Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	                    	System.out.print(cmbAcc_UnitCode1);
	                    	 

	                    	 

	                    	  	cmbOffice_code =Integer.parseInt(request.getParameter("cmbOffice_code"));
	                    	  	System.out.print(cmbOffice_code);
	                    	  	int note_no1 = Integer.parseInt(request.getParameter("note_no"));
	                    	  	System.out.print(note_no1);
	                    	  	
	                    	  	int txtCB_Year11 = Integer.parseInt(request.getParameter("txtCB_Year"));
	                    	  	System.out.print(txtCB_Year11);
	                    	  	int txtCB_Month11 = Integer.parseInt(request.getParameter("txtCB_Month"));
	                    	  	System.out.print(txtCB_Month11);
	                    	      try {
	                    	       String qry_Budget= 
	                    	      		 "SELECT f.HR_NOTE_NO, " +
	                    	      				 "  TO_CHAR(f.NOTE_DATE,'dd/mm/yyyy') AS NOTE_DATE, " +
	                    	      				 "  f1.bill_major_type_desc, " +
	                    	      				 "  f2.bill_minor_type_desc, " +
	                    	      				 "  f3.bill_sub_type_desc , " +
	                    	      				 "  f.NOTE_AMOUNT, " +
	                    	      				 "  f.NOTE_PREPARED_BY, " +
	                    	      				 "  f.ACCOUNT_HEAD_CODE " +
	                    	      				 "FROM FAS_HR_NOTE_DETAILS f, " +
	                    	      				 "  FAS_BILL_MAJOR_TYPES f1, " +
	                    	      				 "  FAS_BILL_MINOR_TYPES_MST f2, " +
	                    	      				 "  FAS_BILL_SUB_TYPES f3 " +
	                    	      				 "WHERE f.BILL_MAJOR_TYPE_CODE =f1.BILL_MAJOR_TYPE_CODE " +
	                    	      				 "AND f.bill_minor_type_code   =f2.bill_minor_type_code " +
	                    	      				 "AND f.bill_major_type_code   =f2.bill_major_type_code " +
	                    	      				 "AND f.bill_sub_type_code     =f3.bill_sub_type_code " +
	                    	      				 "AND f.bill_major_type_code   =f3.bill_major_type_code " +
	                    	      				 "AND F.Bill_Minor_Type_Code   =F3.Bill_Minor_Type_Code " +
	                    	      				 "AND F.Status                 ='L' " +
	                    	      				 "AND Accounting_Unit_Id       ="+cmbAcc_UnitCode1+" " +
	                    	      				 "AND Accounting_For_Office_Id ="+cmbOffice_code+" " +
	                    	      				 
	                    	      				 "AND Cashbook_Month           ="+txtCB_Month11+" "+
	                    	      				 "AND CASHBOOK_YEAR           ="+txtCB_Year11+" "+
	                    	      				"AND HR_NOTE_NO               = "+note_no1+" ";
	                    	      				                 		 
	                    	     
	                    	       
	                    	       System.out.println(qry_Budget);
	                    	       
	                    	         
	                    	       
	                    	       
	                    	       PreparedStatement ps_bud=con.prepareStatement(qry_Budget);
	                    	         /*ps_bud.setInt(1,cmbAcc_UnitCode1);
	                    	         ps_bud.setInt(2,cmbOffice_code);
	                    	         ps_bud.setString(3, txtCB_Year11);
	                    	         ps_bud.setString(4,txtCB_Month11);*/
	                    	         System.out.println(qry_Budget);
	                    	         ResultSet rs_bud=ps_bud.executeQuery();
	                    	         int c1=0;
	                    	         while(rs_bud.next())
	                    	         {
	                    	      	   
	                    	          	 System.out.println("haiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
	                    	          	
	                    	          	System.out.println(rs_bud.getInt("HR_NOTE_NO"));
	                    	          	System.out.println(rs_bud.getString("NOTE_DATE"));
	                    	          	System.out.println(rs_bud.getString("bill_major_type_desc"));
	                    	          	System.out.println(rs_bud.getString("bill_minor_type_desc"));
	                    	          	System.out.println(rs_bud.getString("bill_sub_type_desc"));
	                    	          	System.out.println(rs_bud.getFloat("NOTE_AMOUNT"));
	                    	          	System.out.println(rs_bud.getString("NOTE_PREPARED_BY"));
	                    	          	System.out.println(rs_bud.getInt("ACCOUNT_HEAD_CODE"));
	                    	          	//System.out.println(rs_bud.getString("STATUS"));
	                    	          	 xml1 =xml1 + "<flag>success</flag><NOTE_DATE>" +
	                    						   
	                    	          			 
	                    	rs_bud.getString("NOTE_DATE") + "</NOTE_DATE><bill_major_type_desc>" +         					   
	                    	rs_bud.getString("bill_major_type_desc") + "</bill_major_type_desc><bill_minor_type_desc>" +
	                    	rs_bud.getString("bill_minor_type_desc") + "</bill_minor_type_desc><bill_sub_type_desc>" +
	                    	rs_bud.getString("bill_sub_type_desc") + "</bill_sub_type_desc><NOTE_AMOUNT>" +
	                    	rs_bud.getFloat("NOTE_AMOUNT") + "</NOTE_AMOUNT><NOTE_PREPARED_BY>" +
	                    	rs_bud.getString("NOTE_PREPARED_BY") + "</NOTE_PREPARED_BY><ACCOUNT_HEAD_CODE>" +
	                    	rs_bud.getInt("ACCOUNT_HEAD_CODE") + "</ACCOUNT_HEAD_CODE>" ; 						   
	                    			   
	                    				   
	                    	              }
	                    	      }
	                    	              	
	                    	         



	                    	   catch (Exception e) {
	                    	      System.out.println("catch..HERE.in " + e);
	                    	      xml1 = xml1 + "<flag>failure</flag>";
	                    	  }
	                    	  xml1 = xml1 + "</response>";
	                    	  System.out.println(xml1);
	                    	  out.println(xml1);
	                    	
	                    	
	    }
					
	    }
  
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		PreparedStatement ps2=null,ps=null;        
		ResultSet rs2=null;
                
                Calendar c,c1;
                int txtCash_year=0;
              
                
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
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		}
		catch(Exception e)
		{
			System.out.println("Exception in opening connection :"+e);
		}
		int cmbAcc_UnitCode=0,cmbOffice_code=0, no = 0,
		 txtCB_Month = 0,txtCB_Year = 0;
		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
		.getAttribute("UserProfile");
		System.out.println("do post starts");
		int empid = empProfile.getEmployeeId();
		String update_user=(String)session.getAttribute("UserId");
		long l=System.currentTimeMillis();
		Timestamp ts=new Timestamp(l);                      
		 Date ctdate = new java.sql.Date(ts.getTime());  
		
	
		try{
			cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		
		no=Integer.parseInt(request.getParameter("cmbBankAccNo"));
		System.out.println(no);
		txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
		System.out.println(txtCB_Year);
				 txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		System.out.println(txtCB_Month);
		cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
		
		}
		catch(NumberFormatException e){System.out.println("exception"+e );}
	//	System.out.println("cmbOffice_code "+cmbOffice_code);
		
		try{int note_no = Integer.parseInt(request.getParameter("note no"));}
		catch(NumberFormatException e){System.out.println("exception Bills_list:"+e );}
	//	System.out.println("cmbOffice_code "+cmbOffice_code);

		String[] NOTE_DATE1=request.getParameterValues("NOTE_DATE1");
		String[] bill_major_type_desc1=request.getParameterValues("bill_major_type_desc1");
		//System.out.println("voucherno1"+voucherno1);
		String[] bill_minor_type_desc1=request.getParameterValues("bill_minor_type_desc1"); 
		String[] bill_sub_type_desc1=request.getParameterValues("bill_sub_type_desc1");
		String[] NOTE_AMOUNT1=request.getParameterValues("NOTE_AMOUNT1"); 
		String[] NOTE_PREPARED_BY1=request.getParameterValues("NOTE_PREPARED_BY1");
		String[] ACCOUNT_HEAD_CODE1=request.getParameterValues("ACCOUNT_HEAD_CODE1");
		
		//String[] status1=request.getParameterValues("status");
		System.out.println("hdfhsjkfhsuidfhsdihjd");
		
  try{
		
		String query="UPDATE Fas_Hr_Note_Details " +
				"SET Status                  ='C' ,UPDATED_BY_USERID =?,UPDATED_DATE=?" +				
				"WHERE Accounting_Unit_Id      ="+cmbAcc_UnitCode+" " +
				 "AND Accounting_For_Office_Id ="+cmbOffice_code+" " +
				 "AND HR_NOTE_NO               ="+no+" "+
				 "AND CASHBOOK_YEAR           ="+txtCB_Year+" "+
				 "AND Cashbook_Month           ="+txtCB_Month+" ";
				 
				
							
							ps=con.prepareStatement(query);
							System.out.println(query)	;
                        	ps.setString(1, update_user);
              				ps.setTimestamp(2, ts);
              				
							
              				 int up = ps.executeUpdate();
              				System.out.println("naga"+up);
              				
              				if(up>0)
              				{
              				System.out.println("it is updated")	;

							sendMessage(response," Update has been cancelled Successfully ","ok");   
                        con.commit();
              				}
              				else
              				{
              					System.out.println("it is not updated")	;	
              					System.out.println("redirect");                                
              	                sendMessage(response,"Error in Cancellation","ok"); 
              				}
                        	
  }
		
        

                       catch (SQLException e1) {
                    	   try {
                   			con.rollback();
                   		}
                   		catch (SQLException e11) {
                   			e11.printStackTrace();
                   		}
                    	   
               			
               		}	
                   	try{
                		con.commit();
                	}catch(Exception e){System.out.println(e);}
	
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


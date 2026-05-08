package Servlets.HR.HR1.Training.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class Audit_Slip
 */
public class Retirement_Cadre_Wise extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Retirement_Cadre_Wise() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection con = null;
        Statement statement=null;    
        int txtOffId, Audit_para_id, office_id_audited, txtEmployeeid,auto_no = 0,desg_name1;
        ResultSet results3=null,results=null,rs1=null,rss=null;
		 PreparedStatement ps=null, ps1=null,ps2=null,ps3=null,pss=null;
        response.setContentType("text/xml");
        PrintWriter out=response.getWriter();   
        String cmd = "",sql = null; 
        String xml="";
        String office_id="",type_id="",sql_query="";
        ResultSet result = null;
      
 		 String audit_cat, Audit_Name, para_type,desg_name, Slip_No, Para_Gist, Objection_Made, Voucher_Details, Scheme_Details, Remarks, Status_Para, emp_name,Para_No;
        response.setHeader("Cache-Control","no-cache");
        HttpSession session=request.getSession(false);
        System.out.println("aa");
       
        xml=xml+"<response>";

        try
        {            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp?message=sessionout");
                response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
               return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
           System.out.println("Redirect Error :"+e);
        }
        try
        {
          cmd = request.getParameter("command");
          
          System.out.println(" my last command"+cmd);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      
   	try
       {
   		LoadDriver lb;
   		lb = new LoadDriver();
   		con=lb.getConnection();
           ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
              String ConnectionString="";
             
              String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
              String strdsn=rs.getString("Config.DSN");
              String strhostname=rs.getString("Config.HOST_NAME");
              String strportno=rs.getString("Config.PORT_NUMBER");
              String strsid=rs.getString("Config.SID");
              String strdbusername=rs.getString("Config.USER_NAME");
              String strdbpassword=rs.getString("Config.PASSWORD");
                
              //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
               Class.forName(strDriver.trim());
               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              
       }
   	catch (Exception e)
   	{
   		if(cmd.equalsIgnoreCase("Get"))
   		{
   			xml="Database Service not Available";
   		}
   		else
   		{
   			 xml="<response><status>success</status><value>databaseError</value></response>";
   		}
   		out.write(xml);
   		 out.flush();
   	       out.close();	
   	       System.out.println("databse connection error");
		return;
		}
   	
        int count=0;
   

       String userid=(String)session.getAttribute("UserId");
       System.out.println("session id is:"+userid);
     
       String updatedby=(String)session.getAttribute("UserId");
      
       DateFormat dateFormat= new SimpleDateFormat("dd/MMM/yyyy");
       java.util.Date date= new java.util.Date();
       String dateString= dateFormat.format(date);
      java.sql.Timestamp ts=null;

		try {
			ts = new java.sql.Timestamp(dateFormat.parse(dateString).getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try
		{
			if(cmd.equalsIgnoreCase("getEmployeeExistDetails"))
		       {

				try
		    	  {
		    		  int party_id=Integer.parseInt(request.getParameter("party_id"));
		    		  int emp_id=Integer.parseInt(request.getParameter("emp_id"));
		    		  String date_from=request.getParameter("date_from");
		    		  String date_to=request.getParameter("date_to");
				    	 
				    	 
				    	 System.out.println("date_from="+date_from);
				    	 System.out.println("date_to="+date_to);
				    	 
				    	 int c=0;
				    	  
				    	  String sql2="SELECT A.RESPONSIBLE_EMP_ID, " +
				    	  "  DESIGNATION_ID, " +
				    	  "  TO_CHAR(A.DATE_FROM,'dd/mm/yyyy') AS DATE_FROM , " +
				    	  "  TO_CHAR(A.DATE_TO,'dd/mm/yyyy')   AS DATE_TO, " +
				    	  "  NVL(AUDIT_PARA_ID,0)              AS AUDIT_PARA_ID, " +
				    	  "  LINK_WITH_PARA, " +
				    	  "  trim(PARA_STATUS_ID)     AS PARA_STATUS_ID , " +
				    	  "  NVL(MONETARY_LOSS_AMT,0) AS MONETARY_LOSS_AMT, " +
				    	  "  REMARKS " +
				    	  "FROM " +
				    	  "  (SELECT AUDIT_PARTY_ID, " +
				    	  "    RESPONSIBLE_EMP_ID, " +
				    	  "    DESIGNATION_ID, " +
				    	  "    DATE_FROM, " +
				    	  "    DATE_TO, " +
				    	  "    LINK_WITH_PARA " +
				    	  "  FROM Hrm_Audit_Para_Emp_Link_Mst " +
				    	  "  WHERE AUDIT_PARTY_ID  =? " +
				    	  "  AND RESPONSIBLE_EMP_ID=? " +
				    	  "  AND DATE_FROM         =TO_DATE(TRIM(?),'dd/mm/yyyy') " +
				    	  "  AND date_to           =to_date(trim(?),'dd/mm/yyyy') " +
				    	  "  )A " +
				    	  "LEFT OUTER JOIN " +
				    	  "  (SELECT AUDIT_PARTY_ID, " +
				    	  "    AUDIT_PARA_ID, " +
				    	  "    RESPONSIBLE_EMP_ID, " +
				    	  "    PARA_STATUS_ID, " +
				    	  "    MONETARY_LOSS_AMT, " +
				    	  "    REMARKS, " +
				    	  "    FROM_DATE, " +
				    	  "    TO_DATE " +
				    	  "  FROM HRM_AUDIT_PARA_EMP_LINK_TRN " +
				    	  "  )B " +
				    	  "ON B.RESPONSIBLE_EMP_ID=A.RESPONSIBLE_EMP_ID " +
				    	  "AND A.AUDIT_PARTY_ID   =B.AUDIT_PARTY_ID " +
				    	  "AND A.DATE_FROM        =B.FROM_DATE " +
				    	  "AND A.DATE_TO          =B.TO_DATE" ;
		    		  
		    	  PreparedStatement ps_statment=con.prepareStatement(sql2);
		    	  ps_statment.setInt(1, party_id);
		    	  ps_statment.setInt(2, emp_id);
		    	  ps_statment.setString(3, date_from);
		    	  ps_statment.setString(4, date_to);
		  		System.out.println("sql2    sss ==="+sql2);
		  		  ResultSet result1 = ps_statment.executeQuery();
		  		 xml=xml+"<command>getEmployeeExistDetails</command>";
				
				 while(result1.next())
				 {
					 c++;
					 xml=xml+"<DESIGNATION_ID>"+result1.getInt("DESIGNATION_ID")+"</DESIGNATION_ID>" ;	
					 xml=xml+"<AUDIT_SLIP_ID>"+result1.getInt("AUDIT_PARA_ID")+"</AUDIT_SLIP_ID>" ;
					 xml=xml+"<MONETARY_LOSS_AMT>"+result1.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>" ;
					 xml=xml+"<REMARKS>"+result1.getString("REMARKS")+"</REMARKS>" ;
					 
					 xml=xml+"<SLIP_STATUS_ID>"+result1.getString("PARA_STATUS_ID")+"</SLIP_STATUS_ID>" ;
					 xml=xml+"<DATE_FROM>"+result1.getString("DATE_FROM")+"</DATE_FROM>" ;
					 xml=xml+"<DATE_TO>"+result1.getString("DATE_TO")+"</DATE_TO>" ;
					 xml=xml+"<LINK_WITH_SLIP>"+result1.getString("LINK_WITH_PARA")+"</LINK_WITH_SLIP>" ;

				 }
				 
				 if(c!=0)
				 {
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<flag>failure</flag>";
					 System.out.println("norecord");
				 }
				 

				
		  		  
		    	  }
		    	  catch(Exception e)
		    	  {
		    		  xml=xml+"<flag>failure</flag>";
		    		  System.out.println("catch == "+e);
		    	  }
		       
		       }
			if(cmd.equalsIgnoreCase("getEmployeeDetails"))
		       {
				try
		    	  {
		    		  int party_id=Integer.parseInt(request.getParameter("party_id"));
				    	 
				    	 
				    	 System.out.println("party_id="+party_id);
				    	
				    	 
				    	 int c=0;
				    	  
				    	  String sql2="SELECT  AUDIT_PARTY_ID, " +
				    	  "  RESPONSIBLE_EMP_ID, " +
				    	  "  C.EMPLOYEE_ID " +
				    	  "  ||'-' " +
				    	  "  ||C.EMPLOYEE_NAME " +
				    	  "  ||'.' " +
				    	  "  ||C.EMPLOYEE_INITIAL AS EMPLOYEE_NAME, " +
				    	  "  B.DESIGNATION, " +
				    	  "  TO_CHAR(DATE_FROM,'dd/mm/yyyy') AS DATE_FROM , " +
				    	  "  TO_CHAR(DATE_TO,'dd/mm/yyyy')   AS DATE_TO, " +
				    	  "  LINK_WITH_PARA " +
				    	  "FROM HRM_AUDIT_PARA_EMP_LINK_MST A, " +
				    	  "  HRM_MST_DESIGNATIONS B, " +
				    	  "  HRM_MST_EMPLOYEES C " +
				    	  "WHERE AUDIT_PARTY_ID=? " +
				    	  "AND B.DESIGNATION_ID=A.DESIGNATION_ID " +
				    	  "AND C.EMPLOYEE_ID   =A.RESPONSIBLE_EMP_ID ORDER BY RESPONSIBLE_EMP_ID ";
		    		  
		    	  PreparedStatement ps_statment=con.prepareStatement(sql2);
		    	  ps_statment.setInt(1, party_id);
		    	 
		  		System.out.println("sql2    sss ==="+sql2);
		  		  ResultSet result1 = ps_statment.executeQuery();
		  		 xml=xml+"<command>getEmployeeDetails</command>";
				
				 while(result1.next())
				 {
					 c++;
					 xml=xml+"<RESPONSIBLE_EMP_ID>"+result1.getInt("RESPONSIBLE_EMP_ID")+"</RESPONSIBLE_EMP_ID>" ;	
					 xml=xml+"<EMPLOYEE_NAME>"+result1.getString("EMPLOYEE_NAME")+"</EMPLOYEE_NAME>" ;
					 xml=xml+"<AUDIT_PARTY_ID>"+result1.getInt("AUDIT_PARTY_ID")+"</AUDIT_PARTY_ID>" ;
					 xml=xml+"<DESIGNATION>"+result1.getString("DESIGNATION")+"</DESIGNATION>" ;
					 xml=xml+"<DATE_FROM>"+result1.getString("DATE_FROM")+"</DATE_FROM>" ;
					 xml=xml+"<DATE_TO>"+result1.getString("DATE_TO")+"</DATE_TO>" ;
					 xml=xml+"<LINK_WITH_SLIP>"+result1.getString("LINK_WITH_PARA")+"</LINK_WITH_SLIP>" ;

				 }
				 
				 if(c!=0)
				 {
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<flag>failure</flag>";
					 System.out.println("norecord");
				 }
				 
//				 sql="select AUDIT_PARTY_ID from hrm_audit_status_mst where   AUDIT_PARTY_ID="+party_id+" and RESPONSIBLE_EMP_ID="+emp_id;;
//				 ps=con.prepareStatement(sql);
//				 ResultSet rst=ps.executeQuery();
//				 while(rst.next())
//				 {
//					 c++;
//					 xml=xml+"<status_id>"+rst.getString("status_id")+"</status_id>" ;	
//					
//					  
//					 
//				 }
				
		  		  
		    	  }
		    	  catch(Exception e)
		    	  {
		    		  xml=xml+"<flag>failure</flag>";
		    		  System.out.println("catch == "+e);
		    	  }
		       }
			if(cmd.equalsIgnoreCase("getSlipDetails"))
		       {

		    	

				//	    	  "AND a.employee_status_id NOT IN('VRS','DTH','SAN','MEV','CMR')"


					    	  try
					    	  {
					    		  int party_id=Integer.parseInt(request.getParameter("party_id"));
							    	 
							    	 
							    	 System.out.println("party_id="+party_id);
							    	
							    	 
							    	 int c=0;
							    	  
							    	  String sql2="SELECT a.AUDIT_PARA_ID, " +
										 "  a.AUDIT_PARA_ID_String " +
										 "  ||b.classification_desc " +
										 "  ||'_' " +
										 "  ||c.sub_classification_desc AS AUDIT_PARA_DESC " +
										 "FROM " +
										 "  (SELECT AUDIT_PARA_ID, " +
										 "    'para_id:' " +
										 "    || AUDIT_PARA_ID " +
										 "    ||'_para_no:' " +
										 "    || para_no " +
										 "    || '_Office_id:' " +
										 "    || OFFICE_ID " +
										 "    || '_' AS AUDIT_PARA_ID_String, " +
										 "    classification_id, " +
										 "    Sub_Classification_Id " +
										 "  FROM HRM_AUDIT_PARAS_MST " +
										 "  WHERE audit_party_id=?   and PROCESS_FLOW_ID not in('CL')" +
										 "  )a " +
										 "LEFT OUTER JOIN " +
										 "  (SELECT classification_id,classification_desc FROM hrm_audit_class_mst " +
										 "  )b " +
										 "ON a.classification_id=b.classification_id " +
										 "LEFT OUTER JOIN " +
										 "  (SELECT sub_classification_id, " +
										 "    sub_classification_desc, " +
										 "    classification_id " +
										 "  FROM hrm_audit_subclass_mst " +
										 "  )c " +
										 "ON a.sub_classification_id=c.sub_classification_id " +
										 "AND b.classification_id   =c.classification_id order by AUDIT_PARA_ID " ;
					    		  
					    	  PreparedStatement ps_statment=con.prepareStatement(sql2);
					    	  ps_statment.setInt(1, party_id);
					    	 
					  		System.out.println("sql2    sss ==="+sql2);
					  		  ResultSet result1 = ps_statment.executeQuery();
					  		 xml=xml+"<command>getSlipDetails</command>";
							
							 while(result1.next())
							 {
								 c++;
								 xml=xml+"<AUDIT_SLIP_ID>"+result1.getInt("AUDIT_PARA_ID")+"</AUDIT_SLIP_ID>" ;	
								
								 xml=xml+"<AUDIT_SLIP_DESC>"+result1.getString("AUDIT_PARA_DESC")+"</AUDIT_SLIP_DESC>" ;

							 }
							 
							 if(c!=0)
							 {
								 xml=xml+"<flag>success</flag>";
							 }
							 else
							 {
								 xml=xml+"<flag>failure</flag>";
								 System.out.println("norecord");
							 }
							 
							 sql="select trim(status_id) as status_id,status_desc from hrm_audit_status_mst where   applicable_to in('B')";
							 ps=con.prepareStatement(sql);
							 ResultSet rst=ps.executeQuery();
							 while(rst.next())
							 {
								 c++;
								 xml=xml+"<status_id>"+rst.getString("status_id")+"</status_id>" ;	
								
								 xml=xml+"<status_desc>"+rst.getString("status_desc")+"</status_desc>" ;

							 
								 
							 }
							
					  		  
					    	  }
					    	  catch(Exception e)
					    	  {
					    		  xml=xml+"<flag>failure</flag>";
					    		  System.out.println("catch == "+e);
					    	  }
					    	 
					    	  
					       
		       }
			if(cmd.equalsIgnoreCase("DeleteDetails"))
			{

				 xml=xml+"<command>DeleteDetails</command>";
		    	 int c=0;
		    	
				 String [] slipid = null ;
				 String  para_id=null;
				 String [] status = null ;
				 String [] lossamt = null ;
				 String [] remarks = null ;
				 int emp_id=Integer.parseInt(request.getParameter("emp_id"));
		    	// int desg_id=Integer.parseInt(request.getParameter("desg_id"));
		    	 int party_id=Integer.parseInt(request.getParameter("party_id"));
		    	 
		    	 String from_dates=request.getParameter("from_dates");
		    	 String to_dates=request.getParameter("to_dates");
		    	 String linkwithslip=request.getParameter("linkwithslip");
		    	 
		    	 int total=0;
		    	 
		    	 String query="SELECT COUNT(*) AS total, " +
		    	 "  audit_party_id, " +
		    	 "  RESPONSIBLE_EMP_ID " +
		    	 "FROM hrm_audit_para_emp_link_trn " +
		    	 "WHERE audit_party_id  =? " +
		    	 "AND responsible_emp_id=? " +
		    	 "GROUP BY audit_party_id, " +
		    	 "  RESPONSIBLE_EMP_ID" ;
		    	 
		    	 ps=con.prepareStatement(query);
		    	 ps.setInt(1, party_id);
		    	 ps.setInt(2, emp_id);
		    	 rss=ps.executeQuery();
		    	 while(rss.next())
		    	 {
		    		 total=rss.getInt("total");
		    	 }
		    	 
		    	 String[] arr=new String[100];
		    	 
		    	 
		    	 if(linkwithslip.equalsIgnoreCase("Y"))
		    	 {
		    		 
		    	 //slipid=request.getParameterValues("slip_id");
		    		 para_id=request.getParameter("para_id");
		    		 arr=para_id.split(",");
		    		 System.out.println("total is  = "+total);
		    	 //status=request.getParameterValues("status");
		    	 //lossamt=request.getParameterValues("lossamt");
		    	 //remarks=request.getParameterValues("remarks");
		    	 
			    	for(int a=0;a<arr.length;a++)
			    	{
			    		System.out.println("the no  is "+arr[a]);
			    	}
		    	 
			    	 for(int k=0;k<arr.length;k++)
			    	 {
			    	 
			    	 ps=con.prepareStatement("DELETE FROM HRM_AUDIT_PARA_EMP_LINK_TRN WHERE AUDIT_PARTY_ID="+party_id+" AND RESPONSIBLE_EMP_ID="+emp_id+" and AUDIT_PARA_ID="+arr[k]+"  And FROM_DATE  =To_Date(Trim('"+from_dates+"'),'dd/mm/yyyy')    ");
			    	 ps.executeUpdate();
			    	 total--;
			    	 c++;
			    	 
			    	 }
		    	 
			    	 if(total!=0)
			    	 {
			    	
			    	 }
			    	 else
			    	 {
			    		 ps=con.prepareStatement("DELETE FROM HRM_AUDIT_PARA_EMP_LINK_MST WHERE AUDIT_PARTY_ID="+party_id+" AND RESPONSIBLE_EMP_ID="+emp_id+"  And Date_From  =To_Date(Trim('"+from_dates+"'),'dd/mm/yyyy')    ");
				    	 ps.executeUpdate();
			    	 }
			    	 
		    	 }
		    	 
		    	 if(linkwithslip.equalsIgnoreCase("N"))
		    	 {
		    		 ps=con.prepareStatement("DELETE FROM HRM_AUDIT_PARA_EMP_LINK_MST WHERE AUDIT_PARTY_ID="+party_id+" AND RESPONSIBLE_EMP_ID="+emp_id+"  And Date_From  =To_Date(Trim('"+from_dates+"'),'dd/mm/yyyy')    ");
			    	 ps.executeUpdate();
			    	 
			    	 c++;
			    	 
		    	 }
		    	 
		    	
		    	 
		    	
		    	 
		    	
		    	 
		    	 
		    	 
		    	 
				
		    	 
		    	 if(c!=0)
				 {
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<flag>failure</flag>";
					 System.out.println("norecord");
				 }
			
			}
			
			if(cmd.equalsIgnoreCase("UpdateDetails"))
			{

				 String [] slipid = null ;
				 String [] status = null ;
				 String [] lossamt = null ;
				 String [] remarks = null ;
				 int emp_id=Integer.parseInt(request.getParameter("emp_id"));
		    	 int desg_id=Integer.parseInt(request.getParameter("desg_id"));
		    	 int party_id=Integer.parseInt(request.getParameter("party_id"));
		    	 
		    	 String from_dates=request.getParameter("from_dates");
		    	 String to_dates=request.getParameter("to_dates");
		    	 String linkwithslip=request.getParameter("linkwithslip");
		    	 if(linkwithslip.equalsIgnoreCase("Y"))
		    	 {
		    	 slipid=request.getParameterValues("slip_id");
		    	 status=request.getParameterValues("status");
		    	 lossamt=request.getParameterValues("lossamt");
		    	 remarks=request.getParameterValues("remarks");
		    	 }
		    	 xml=xml+"<command>UpdateDetails</command>";
		    	 int c=0;
		    	 
//		    	 ps=con.prepareStatement("DELETE FROM HRM_AUDIT_PARA_EMP_LINK_MST WHERE AUDIT_PARTY_ID="+party_id+" AND RESPONSIBLE_EMP_ID="+emp_id+" And Date_From  =To_Date(Trim('"+from_dates+"'),'dd/mm/yyyy')  AND date_to  =to_date(trim('"+to_dates+"'),'dd/mm/yyyy')  ");
//		    	 ps.executeUpdate();
		    	 
//		    	 ps=con.prepareStatement("DELETE FROM HRM_AUDIT_PARA_EMP_LINK_TRN WHERE AUDIT_PARTY_ID="+party_id+" AND RESPONSIBLE_EMP_ID="+emp_id+" And Date_From  =To_Date(Trim('"+from_dates+"'),'dd/mm/yyyy')  AND date_to  =to_date(trim('"+to_dates+"'),'dd/mm/yyyy')  ");
//		    	 ps.executeUpdate();
		    	 
		    	 
					String sql2="UPDATE HRM_AUDIT_PARA_EMP_LINK_MST " +
					"SET DESIGNATION_ID     = ?, " +
					"  DATE_FROM            = to_date(?,'dd/mm/yyyy'), " +
					"  DATE_TO              = to_date(?,'dd/mm/yyyy'), " +
					"  LINK_WITH_PARA       = ?, " +
					"  UPDATED_BY_USER_ID   = ?, " +
					"  UPDATED_DATE         = systimestamp, " +
					"  PROCESS_FLOW_ID      = 'MD' " +
					"WHERE AUDIT_PARTY_ID   = ? " +
					"AND RESPONSIBLE_EMP_ID = ?" ;
					 ps=con.prepareStatement(sql2);
					 ps.setInt(6, party_id);
		    		 ps.setInt(7, emp_id);
		    		 ps.setInt(1, desg_id);
		    		 ps.setString(2, from_dates);
		    		 ps.setString(3, to_dates);
		    		 ps.setString(4, linkwithslip);
		    		 ps.setString(5, userid);
		    		c= ps.executeUpdate();
		    		 
		    		
		    	 String sql1="UPDATE HRM_AUDIT_PARA_EMP_LINK_TRN " +
		    	 "SET PARA_STATUS_ID     = ?, " +
		    	 "  MONETARY_LOSS_AMT    = ?, " +
		    	 "  REMARKS              = ?, " +
		    	 "  UPDATED_BY_USER_ID   = ?, " +
		    	 "  UPDATED_DATE         = systimestamp, " +
		    	 "  PROCESS_FLOW_ID      = 'MD' " +
		    	 "WHERE AUDIT_PARTY_ID   = ? " +
		    	 "AND AUDIT_PARA_ID      = ? " +
		    	 "AND RESPONSIBLE_EMP_ID = ?"
		    	
 ;
		    	 if(linkwithslip.equalsIgnoreCase("Y"))
		    	 {
		    	 for(int k=0;k<slipid.length;k++)
		    	 {
		    		 ps=con.prepareStatement(sql1);
		    		 ps.setInt(5, party_id);		
		    		 ps.setInt(6, Integer.parseInt(slipid[k]));
		    		 ps.setInt(7, emp_id);		    		
		    		 ps.setString(1, status[k]);
		    		 ps.setInt(2, Integer.parseInt(lossamt[k]));
		    		 ps.setString(3, remarks[k]);
		    		 ps.setString(4, userid);
		    		
		    		 
		    		 
		    		 ps.executeUpdate();
		    		 
		    	 }
		    	 }
		    	 
		    	 if(c!=0)
				 {
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<flag>failure</flag>";
					 System.out.println("norecord");
				 }
			
			}
			
			if(cmd.equalsIgnoreCase("SaveDetails"))
			{
				 String [] slipid = null ;
				 String [] status = null ;
				 String [] lossamt = null ;
				 String [] remarks = null ;
				 int emp_id=Integer.parseInt(request.getParameter("emp_id"));
		    	 int desg_id=Integer.parseInt(request.getParameter("desg_id"));
		    	 int party_id=Integer.parseInt(request.getParameter("party_id"));
		    	 
		    	 String from_dates=request.getParameter("from_dates");
		    	 String to_dates=request.getParameter("to_dates");
		    	 String linkwithslip=request.getParameter("linkwithslip");
		    	 if(linkwithslip.equalsIgnoreCase("Y"))
		    	 {
		    	 slipid=request.getParameterValues("slip_id");
		    	 status=request.getParameterValues("status");
		    	 lossamt=request.getParameterValues("lossamt");
		    	 remarks=request.getParameterValues("remarks");
		    	 }
		    	 xml=xml+"<command>SaveDetails</command>";
		    	 int c=0;
		    	 
//		    	 ps=con.prepareStatement("DELETE FROM HRM_AUDIT_PARA_EMP_LINK_MST WHERE AUDIT_PARTY_ID="+party_id+" AND RESPONSIBLE_EMP_ID="+emp_id+" And Date_From  =To_Date(Trim('"+from_dates+"'),'dd/mm/yyyy')  AND date_to  =to_date(trim('"+to_dates+"'),'dd/mm/yyyy')  ");
//		    	 ps.executeUpdate();
		    	 
//		    	 ps=con.prepareStatement("DELETE FROM HRM_AUDIT_PARA_EMP_LINK_TRN WHERE AUDIT_PARTY_ID="+party_id+" AND RESPONSIBLE_EMP_ID="+emp_id+" And Date_From  =To_Date(Trim('"+from_dates+"'),'dd/mm/yyyy')  AND date_to  =to_date(trim('"+to_dates+"'),'dd/mm/yyyy')  ");
//		    	 ps.executeUpdate();
		    	 
		    	 
					String sql2="INSERT " +
					"INTO HRM_AUDIT_PARA_EMP_LINK_MST " +
					"  ( " +
					"    AUDIT_PARTY_ID, " +
					"    RESPONSIBLE_EMP_ID, " +
					"    DESIGNATION_ID, " +
					"    DATE_FROM, " +
					"    DATE_TO, " +
					"    LINK_WITH_PARA, " +
					"    UPDATED_BY_USER_ID, " +
					"    UPDATED_DATE, " +
					"    PROCESS_FLOW_ID " +
					"  ) " +
					"  VALUES " +
					"  ( " +
					"    ?, " +
					"    ?, " +
					"    ?, " +
					"    to_date(?,'dd/mm/yyyy'), " +
					"    to_date(?,'dd/mm/yyyy'), " +
					"    ?, " +
					"    ?, " +
					"    systimestamp, " +
					"    'CR' " +
					"  )" ;
					 ps=con.prepareStatement(sql2);
					 ps.setInt(1, party_id);
		    		 ps.setInt(2, emp_id);
		    		 ps.setInt(3, desg_id);
		    		 ps.setString(4, from_dates);
		    		 ps.setString(5, to_dates);
		    		 ps.setString(6, linkwithslip);
		    		 ps.setString(7, userid);
		    		c= ps.executeUpdate();
		    		 
		    		
		    	 String sql1="INSERT " +
		    	 "INTO HRM_AUDIT_PARA_EMP_LINK_TRN " +
		    	 "  ( " +
		    	 "    AUDIT_PARTY_ID, " +
		    	 "    AUDIT_PARA_ID, " +
		    	 "    RESPONSIBLE_EMP_ID, " +
		    	 "    PARA_STATUS_ID, " +
		    	 "    MONETARY_LOSS_AMT, " +
		    	 "    REMARKS, " +
		    	 "    UPDATED_BY_USER_ID, " +
		    	 "    UPDATED_DATE, " +
		    	 "    PROCESS_FLOW_ID, " +
		    	 "    FROM_DATE, " +
		    	 "    TO_DATE " +

		    	 "  ) " +
		    	 "  VALUES " +
		    	 "  ( " +
		    	 "    ?, " +
		    	 "    ?, " +
		    	 "    ?, " +
		    	 "    ?, " +
		    	 "    ?, " +
		    	 "    ?, " +
		    	 "    ?, " +
		    	 "    systimestamp, " +
		    	 "    'CR', " +
		    	 "    to_date(?,'dd/mm/yyyy'), " +
				 "    to_date(?,'dd/mm/yyyy') " +
		    	 "  )" ;
		    	 if(linkwithslip.equalsIgnoreCase("Y"))
		    	 {
		    	 for(int k=0;k<slipid.length;k++)
		    	 {
		    		 ps=con.prepareStatement(sql1);
		    		 ps.setInt(1, party_id);		
		    		 ps.setInt(2, Integer.parseInt(slipid[k]));
		    		 ps.setInt(3, emp_id);		    		
		    		 ps.setString(4, status[k]);
		    		 ps.setInt(5, Integer.parseInt(lossamt[k]));
		    		 ps.setString(6, remarks[k]);
		    		 ps.setString(7, userid);
		    		 ps.setString(8, from_dates);
		    		 ps.setString(9, to_dates);
		    		 
		    		 ps.executeUpdate();
		    		 
		    	 }
		    	 }
		    	 
		    	 if(c!=0)
				 {
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<flag>failure</flag>";
					 System.out.println("norecord");
				 }
			}
			if(cmd.equalsIgnoreCase("Employee_Details"))
		       {
		    	

	//	    	  "AND a.employee_status_id NOT IN('VRS','DTH','SAN','MEV','CMR')"


		    	  try
		    	  {
		    		    int emp_id=Integer.parseInt(request.getParameter("emp_id"));
				    	 int office_id1=Integer.parseInt(request.getParameter("office_id"));
				    	 String period_covered_from=request.getParameter("Audit_Period_cover_frm");
				    	 String period_covered_to=request.getParameter("Audit_Period_Cover_To");
				    	 
				    	 System.out.println("empid="+emp_id);
				    	 System.out.println("office_id1="+office_id1);
				    	 System.out.println("period_covered_from="+period_covered_from);
				    	 System.out.println("period_covered_to="+period_covered_to);
				    	 
				    	 int c=0;
				    	  
				    	  String sql2="SELECT DISTINCT a.employee_id, " +
				    	  "  b.employee_initial " +
				    	  "  ||'.' " +
				    	  "  ||b.employee_name as emp_name, " +
				    	  "  a.office_id, " +
				    	  "  a.designation_id, " +
				    	  "  d.designation, " +
				    	  "  a.date_from, " +
				    	  "  a.date_to " +
				    	  "FROM hrm_emp_service_data a " +
				    	  "LEFT OUTER JOIN hrm_mst_employees b " +
				    	  "ON a.employee_id=b.employee_id " +
				    	  "LEFT OUTER JOIN hrm_mst_designations d " +
				    	  "ON a.designation_id =d.designation_id " +
				    	  "WHERE a.employee_id =? " +
				    	  "AND a.office_id     =? " +
				    	  "AND (((a.date_from <=to_date(?,'dd/mm/yyyy') " +
				    	  "AND (a.date_to BETWEEN to_date(?,'dd/mm/yyyy') AND to_date(?,'dd/mm/yyyy') " +
				    	  "OR (a.date_to>=to_date(?,'dd/mm/yyyy'))) " +
				    	  "OR ((a.date_from BETWEEN to_date(?,'dd/mm/yyyy') AND to_date(?,'dd/mm/yyyy')) " +
				    	  "AND (a.date_to BETWEEN to_date(?,'dd/mm/yyyy') AND to_date(?,'dd/mm/yyyy')) " +
				    	  "OR (a.date_to  >=to_date(?,'dd/mm/yyyy'))) " +
				    	  "OR (a.date_from<=to_date(?,'dd/mm/yyyy')) " +
				    	  "AND (a.date_to >=to_date(?,'dd/mm/yyyy')) )))"
				    	  
				    	  ;
		    		  
		    	  PreparedStatement ps_statment=con.prepareStatement(sql2);
		    	  ps_statment.setInt(1, emp_id);
		    	  ps_statment.setInt(2, office_id1);
		    	  
		    	  ps_statment.setString(3, period_covered_from);
		    	  ps_statment.setString(4, period_covered_from);
		    	  ps_statment.setString(5, period_covered_to);
		    	  ps_statment.setString(6, period_covered_to);
		    	  ps_statment.setString(7, period_covered_from);
		    	  ps_statment.setString(8, period_covered_to);
		    	  ps_statment.setString(9, period_covered_from);
		    	  ps_statment.setString(10, period_covered_to);
		    	  ps_statment.setString(11, period_covered_to);
		    	  ps_statment.setString(12, period_covered_from);
		    	  ps_statment.setString(13, period_covered_to);
		  		System.out.println("sql2    sss ==="+sql2);
		  		  ResultSet result1 = ps_statment.executeQuery();
		  		 xml=xml+"<command>Employee_Details</command>";
				
				 while(result1.next())
				 {
					 c++;
					 xml=xml+"<employee_id>"+result1.getInt("employee_id")+"</employee_id>" ;	
					 xml=xml+"<designation_id>"+result1.getInt("designation_id")+"</designation_id>" ;					 
					 xml=xml+"<emp_name>"+result1.getString("emp_name")+"</emp_name>" ;	
					 xml=xml+"<designation>"+result1.getString("designation")+"</designation>" ;
					 xml=xml+"<office_id>"+result1.getString("office_id")+"</office_id>" ;
					 xml=xml+"<date_from>"+result1.getString("date_from")+"</date_from>" ;
					 xml=xml+"<date_to>"+result1.getString("date_to")+"</date_to>" ;

				 }
				 
				 if(c!=0)
				 {
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<flag>failure</flag>";
					 System.out.println("norecord");
				 }
				
		  		  
		    	  }
		    	  catch(Exception e)
		    	  {
		    		  xml=xml+"<flag>failure</flag>";
		    		  System.out.println("catch == "+e);
		    	  }
		    	 
		    	  
		       }
			
			else if(cmd.equalsIgnoreCase("Get_Emp_details"))
			{
				xml=xml+"<command>Get_Emp_details</command>";

		    	 int off_id=Integer.parseInt(request.getParameter("office_id"));
		    	 String date_from=request.getParameter("Audit_Period_cover_frm");
		    	 String date_to=request.getParameter("Audit_Period_Cover_To");
		    	 System.out.println(date_from +" == "+date_to);
		    	 int c=0;
		    	  
		    	  String sql2=
		    		  "(SELECT a.employee_id, " +
		    		  "  TO_CHAR(a.date_from,'dd/mm/yyyy') AS date_from, " +
		    		  "  a.date_from_session, " +
		    		  "  TO_CHAR(a.date_to,'dd/mm/yyyy') AS date_to, " +
		    		  "  a.date_to_session, " +
		    		  "  b.ename, " +
		    		  "  a.designation_id, " +
		    		  "  d.designation " +
		    		  "FROM " +
		    		  "  (SELECT DISTINCT employee_id, " +
		    		  "    date_from, " +
		    		  "    date_from_session, " +
		    		  "    date_to, " +
		    		  "    date_to_session, " +
		    		  "    office_id, " +
		    		  "    DESIGNATION_ID " +
		    		  "  FROM hrm_emp_service_data " +
		    		  "  )a " +
		    		  "LEFT OUTER JOIN " +
		    		  "  (SELECT employee_id, " +
		    		  "    employee_initial " +
		    		  "    ||'.' " +
		    		  "    ||employee_name AS ename " +
		    		  "  FROM hrm_mst_employees " +
		    		  "  )b " +
		    		  "ON a.employee_id=b.employee_id " +
		    		  "LEFT OUTER JOIN " +
		    		  "  (SELECT employee_id,designation_id FROM hrm_emp_current_posting " +
		    		  "  )c " +
		    		  "ON a.employee_id=c.employee_id " +
		    		  "LEFT OUTER JOIN " +
		    		  "  (SELECT designation_id,designation FROM hrm_mst_designations " +
		    		  "  )d " +
		    		  "ON a.Designation_Id=D.Designation_Id " +
		    		  "WHERE A.Office_Id  =? " +
		    		  "AND A.Date_From BETWEEN To_Date(?,'dd/mm/yyyy') AND To_Date(?,'dd/mm/yyyy') " +
		    		  ") " +
		    		  "UNION " +
		    		  "  (SELECT a.employee_id, " +
		    		  "    TO_CHAR(a.date_from,'dd/mm/yyyy') AS date_from, " +
		    		  "    a.date_from_session, " +
		    		  "    TO_CHAR(a.date_to,'dd/mm/yyyy') AS date_to, " +
		    		  "    a.date_to_session, " +
		    		  "    b.ename, " +
		    		  "    a.designation_id, " +
		    		  "    d.designation " +
		    		  "  FROM " +
		    		  "    (SELECT DISTINCT employee_id, " +
		    		  "      date_from, " +
		    		  "      date_from_session, " +
		    		  "      date_to, " +
		    		  "      date_to_session, " +
		    		  "      office_id, " +
		    		  "      designation_id " +
		    		  "    FROM hrm_emp_service_data " +
		    		  "    )a " +
		    		  "  LEFT OUTER JOIN " +
		    		  "    (SELECT employee_id, " +
		    		  "      employee_initial " +
		    		  "      ||'.' " +
		    		  "      ||employee_name AS ename " +
		    		  "    FROM hrm_mst_employees " +
		    		  "    )b " +
		    		  "  ON a.employee_id=b.employee_id " +
		    		  "  LEFT OUTER JOIN " +
		    		  "    (SELECT employee_id,designation_id FROM hrm_emp_current_posting " +
		    		  "    )c " +
		    		  "  ON a.employee_id=c.employee_id " +
		    		  "  LEFT OUTER JOIN " +
		    		  "    (SELECT designation_id,designation FROM hrm_mst_designations " +
		    		  "    )d " +
		    		  "  ON a.Designation_Id=D.Designation_Id " +
		    		  "  WHERE A.Office_Id  =? " +
		    		  "  AND A.Date_To BETWEEN To_Date(?,'dd/mm/yyyy') AND To_Date(?,'dd/mm/yyyy') " +
		    		  "  ) " +
		    		  "UNION " +
		    		  "  (SELECT a.employee_id, " +
		    		  "    TO_CHAR(a.date_from,'dd/mm/yyyy') AS date_from, " +
		    		  "    a.date_from_session, " +
		    		  "    TO_CHAR(a.date_to,'dd/mm/yyyy') AS date_to, " +
		    		  "    a.date_to_session, " +
		    		  "    b.ename, " +
		    		  "    a.designation_id, " +
		    		  "    d.designation " +
		    		  "  FROM " +
		    		  "    (SELECT DISTINCT employee_id, " +
		    		  "      date_from, " +
		    		  "      date_from_session, " +
		    		  "      date_to, " +
		    		  "      date_to_session, " +
		    		  "      office_id , " +
		    		  "      designation_id " +
		    		  "    FROM hrm_emp_service_data " +
		    		  "    )a " +
		    		  "  LEFT OUTER JOIN " +
		    		  "    (SELECT employee_id, " +
		    		  "      employee_initial " +
		    		  "      ||'.' " +
		    		  "      ||employee_name AS ename " +
		    		  "    FROM hrm_mst_employees " +
		    		  "    )b " +
		    		  "  ON a.employee_id=b.employee_id " +
		    		  "  LEFT OUTER JOIN " +
		    		  "    (SELECT employee_id,designation_id FROM hrm_emp_current_posting " +
		    		  "    )c " +
		    		  "  ON a.employee_id=c.employee_id " +
		    		  "  LEFT OUTER JOIN " +
		    		  "    (SELECT designation_id,designation FROM hrm_mst_designations " +
		    		  "    )d " +
		    		  "  ON a.Designation_Id=D.Designation_Id " +
		    		  "  WHERE A.Office_Id  =? " +
		    		  "  AND (A.Date_From  <=To_Date(?,'dd/mm/yyyy') " +
		    		  "  AND A.Date_To     >=To_Date(?,'dd/mm/yyyy')) " +
		    		  "  )"  ;
		    	  
		    	  
		    	  try
		    	  {
		    		  
		    	  
		  		ps=con.prepareStatement(sql2);
		  		ps.setInt(1, off_id);
		  		ps.setString(2, date_from);
		  		ps.setString(3, date_to);
		  		
		  		ps.setInt(4, off_id);
		  		ps.setString(5, date_from);
		  		ps.setString(6, date_to);
		  		
		  		ps.setInt(7, off_id);
		  		ps.setString(8, date_from);
		  		ps.setString(9, date_to);
		  		
		  		System.out.println("SQL data : "+sql2);
		  		ResultSet result1=ps.executeQuery();
		  		System.out.println("SQL data : "+sql2);
				
				 while(result1.next())
				 {
					 c++;
					 xml=xml+"<employee_id>"+result1.getInt("employee_id")+"</employee_id>" ;	
					 xml=xml+"<date_from>"+result1.getString("date_from")+"</date_from>" ;
					 xml=xml+"<date_from_session>"+result1.getString("date_from_session")+"</date_from_session>" ;
					 xml=xml+"<date_to>"+result1.getString("date_to")+"</date_to>" ;
					 xml=xml+"<date_to_session>"+result1.getString("date_to_session")+"</date_to_session>" ;
					 xml=xml+"<designation_id>"+result1.getInt("designation_id")+"</designation_id>" ;					 
					 xml=xml+"<ename>"+result1.getString("ename")+"</ename>" ;	
					 xml=xml+"<designation>"+result1.getString("designation")+"</designation>" ;	

				 }
				 
				 if(c!=0)
				 {
					 xml=xml+"<count>"+c+"</count>";
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<count>"+c+"</count>";
					 xml=xml+"<flag>failure</flag>";
				 }
				
		  		  
		    	  }
		    	  catch(Exception e)
		    	  {
		    		  xml=xml+"<count>"+c+"</count>";
		    		  xml=xml+"<flag>failure</flag>";
		    	  }
		    	 
		    	  
		       
			}
			
			else if(cmd.equalsIgnoreCase("update"))
		{

			xml=xml+"<command>update</command>";
			int cou=0;
			
			int slip_id=Integer.parseInt(request.getParameter("slip_id"));
			int emp_id=Integer.parseInt(request.getParameter("emp_id"));
			int desg_id=Integer.parseInt(request.getParameter("desg_id"));
			int amount=Integer.parseInt(request.getParameter("amount"));
			String status_id=request.getParameter("status_id");
			String remarks=request.getParameter("remarks");
			String from_dates=request.getParameter("from_dates");
			String to_dates=request.getParameter("to_dates");
			 sql_query="UPDATE HRM_AUDIT_SLIP_EMP_LINK " +
			 "SET DESIGNATION_ID     = ?, " +
			 "  SLIP_STATUS_ID       = ?, " +
			 "  MONETARY_LOSS_AMT    = ?, " +
			 "  REMARKS              = ?, " +
			 "  UPDATED_BY_USER_ID   = ?, " +
			 "  UPDATED_DATE         = systimestamp, " +
			 "  PROCESS_FLOW_ID      = 'MD', " +
			 "  DATE_FROM            =?, " +
			 "  DATE_TO              =? " +
			 "WHERE AUDIT_SLIP_ID    = ? " +
			 "AND RESPONSIBLE_EMP_ID = ?"

			 ;
			 
			 try {
				ps=con.prepareStatement(sql_query);
				// ps.setInt(1, audit_party);
				 ps.setInt(8, slip_id);
				 ps.setInt(9, emp_id);
				 ps.setInt(1, desg_id);
				 ps.setString(2, status_id);
				 ps.setInt(3, amount);
				 
				 ps.setString(4, remarks);
				 
				 ps.setString(5, updatedby);
				 ps.setString(6, from_dates);
				 ps.setString(7, to_dates);
				 ps.executeUpdate();
				
				 
				
					 xml=xml+"<flag>success</flag>";
				 
				
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml=xml+"<flag>failure</flag>";
				e.printStackTrace();
			}
			
			
		
		}
		
		
			else if(cmd.equalsIgnoreCase("delete"))
		{
			xml=xml+"<command>delete</command>";
			int cou=0;
			int slip_id=Integer.parseInt(request.getParameter("slip_id"));
			int emp_id=Integer.parseInt(request.getParameter("emp_id"));
			sql_query="UPDATE HRM_AUDIT_SLIP_EMP_LINK " +
			"SET UPDATED_BY_USER_ID = ?, " +
			"  UPDATED_DATE         = systimestamp, " +
			"  PROCESS_FLOW_ID      = 'DL' " +
			"WHERE AUDIT_SLIP_ID    = ? " +
			"AND RESPONSIBLE_EMP_ID = ?"

;
			 try {
					ps=con.prepareStatement(sql_query);
					ps.setString(1, updatedby);
					ps.setInt(2, slip_id);
					ps.setInt(3, emp_id);
   				    ps.executeUpdate();					
					xml=xml+"<flag>success</flag>";
			 }
			 catch(Exception e)
			 {
				 xml=xml+"<flag>failure</flag>";
				 e.printStackTrace();
			 }
		}
		
		
			else if(cmd.equalsIgnoreCase("insert"))
		{
			xml=xml+"<command>insert</command>";
			int cou=0;
			int slip_id=Integer.parseInt(request.getParameter("slip_id"));
			int emp_id=Integer.parseInt(request.getParameter("emp_id"));
			int desg_id=Integer.parseInt(request.getParameter("desg_id"));
			int amount=Integer.parseInt(request.getParameter("amount"));
			String status_id=request.getParameter("status_id");
			String remarks=request.getParameter("remarks");
			String from_dates=request.getParameter("from_dates");
			String to_dates=request.getParameter("to_dates");
			
			
			
			 System.out.println(type_id);
			 sql_query="INSERT " +
			 "INTO HRM_AUDIT_SLIP_EMP_LINK " +
			 "  ( " +
			 "    AUDIT_SLIP_ID, " +
			 "    RESPONSIBLE_EMP_ID, " +
			 "    DESIGNATION_ID, " +
			 "    SLIP_STATUS_ID, " +
			 "    MONETARY_LOSS_AMT, " +
			 "    REMARKS, " +
			 "    UPDATED_BY_USER_ID, " +
			 "    UPDATED_DATE, " +
			 "    PROCESS_FLOW_ID, " +
			 "    DATE_FROM, " +
			 "    DATE_TO " +
			 "  ) " +
			 "  VALUES " +
			 "  ( " +
			 "    ?, " +
			 "    ?, " +
			 "    ?, " +
			 "    ?, " +
			 "    ?, " +
			 "    ?, " +
			 "    ?, " +
			 "    systimestamp, " +
			 "    'CR', " +
			 "    ?, " +
			 "    ? " +
			 "  )"


			 ;
			 
			 try {
				ps=con.prepareStatement(sql_query);
				 ps.setInt(1, slip_id);
				 ps.setInt(2, emp_id);
				 ps.setInt(3, desg_id);
				 ps.setString(4, status_id);
				 ps.setInt(5, amount);
				 
				 ps.setString(6, remarks);
				 
				 ps.setString(7, updatedby);
				 ps.setString(8, from_dates);
				 ps.setString(9, to_dates);
				 
				 ps.executeUpdate();
				
				 
				
					 xml=xml+"<flag>success</flag>";
				 
				
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml=xml+"<flag>failure</flag>";
				e.printStackTrace();
			}
			
			
		}
		
			else if(cmd.equalsIgnoreCase("Load_slip_grid"))
		{
			
			xml=xml+"<command>Load_slip_grid</command>";
			int cou=0;
			int slip_id=Integer.parseInt(request.getParameter("slipid"));
			 System.out.println(type_id);
			 sql_query="SELECT a.AUDIT_SLIP_ID, " +
			 "  a.RESPONSIBLE_EMP_ID, " +
			 "  a.DESIGNATION_ID, " +
			 "  a.SLIP_STATUS_ID, " +
			 "  a.MONETARY_LOSS_AMT, " +
			 "  a.REMARKS, " +
			 "  a.PROCESS_FLOW_ID, " +
			 "  b.employee_initial " +
			 "  || '.' " +
			 "  || b.employee_name AS empname, " +
			 "  c.designation " +
			 "FROM HRM_AUDIT_SLIP_EMP_LINK a   " +
			 "LEFT OUTER JOIN hrm_mst_employees b " +
			 "ON a.responsible_emp_id=b.employee_id " +
			 "LEFT OUTER JOIN hrm_mst_designations c " +
			 "ON a.designation_id          =c.designation_id " +
			 "WHERE a.process_flow_id NOT IN('DL') and a.AUDIT_SLIP_ID="+slip_id+""


			 ;
			 
			 try {
				ps=con.prepareStatement(sql_query);
				
				 rss=ps.executeQuery();
				 while(rss.next())
				 {
					 cou++;
					 xml=xml+"<AUDIT_PARA_ID>"+rss.getInt("AUDIT_SLIP_ID")+"</AUDIT_PARA_ID>";
					 xml=xml+"<RESPONSIBLE_EMP_ID>"+rss.getInt("RESPONSIBLE_EMP_ID")+"</RESPONSIBLE_EMP_ID>";
					 xml=xml+"<DESIGNATION_ID>"+rss.getInt("DESIGNATION_ID")+"</DESIGNATION_ID>";
					 xml=xml+"<EMP_PARA_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</EMP_PARA_STATUS_ID>";
					 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
					 xml=xml+"<PROCESS_FLOW_ID>"+rss.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
					 xml=xml+"<empname>"+rss.getString("empname")+"</empname>";
					 xml=xml+"<designation>"+rss.getString("designation")+"</designation>";
				 }
				 
				 xml=xml+"<count>"+cou+"</count>";
				 if(cou!=0)
				 {
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<flag>failure</flag>";
				 }
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml=xml+"<flag>failure</flag>";
				e.printStackTrace();
			}
			
			
			
			
		}
			
			else if(cmd.equalsIgnoreCase("sub_programs"))
			{




				xml=xml+"<command>sub_programs</command>";
				int cou=0,year=0;
				String program_id="";
				year=Integer.parseInt(request.getParameter("year"));
				program_id=request.getParameter("program_id");
				
				
				 System.out.println(type_id);
//				 sql_query="SELECT venue_trg_centre, " +
//				 "  a.PROG_TITLE_ID, " +
//				 "  b.PROG_TITLE_NAME, " +
//				 "  TOT_NO_PROGRAMMES " +
//				 "FROM " +
//				 "  (SELECT DECODE(VENUE_TRG_CENTRE_ID,NULL,'Other_Centres',VENUE_TRG_CENTRE_ID) AS venue_trg_centre, " +
//				 "    PROG_TITLE_ID, " +
//				 "    COUNT(*) AS TOT_NO_PROGRAMMES " +
//				 "  FROM HRM_TRG_PROG_CUTOFF_MST_NEW " +
//				 "  WHERE EXTRACT(YEAR FROM SCHEDULED_FROM) = ? " +
//				 "  AND VENUE_TRG_CENTRE_ID                 =? " +
//				 "  GROUP BY DECODE(VENUE_TRG_CENTRE_ID,NULL,'Other_Centres',VENUE_TRG_CENTRE_ID), " +
//				 "    PROG_TITLE_ID " +
//				 "  ) a " +
//				 "LEFT OUTER JOIN " +
//				 "  (SELECT PROG_TITLE_ID, " +
//				 "    PROG_TITLE_NAME, " +
//				 "    PROG_TITLE_SHORT_NAME " +
//				 "  FROM HRM_TRG_PROG_TITLE_MST " +
//				 "  )b " +
//				 "ON a.PROG_TITLE_ID=b.PROG_TITLE_ID " +
//				 "ORDER BY 1 ASC, " +
//				 "  2 ASC";
				 
				 sql_query="SELECT VENUE_TRG_CENTRE_ID, " +
				 "  TRG_CENTRE_NAME, " +
				 "  prog_title_id, " +
				 "  prog_title_name, " +
				 "  tot_no_training, " +
				 "  SUM(tot_emp) AS tot_emp " +
				 "FROM " +
				 "  (SELECT VENUE_TRG_CENTRE_ID, " +
				 "    TRG_CENTRE_NAME, " +
				 "    PROG_TITLE_ID, " +
				 "    PROG_TITLE_NAME, " +
				 "    tot_no_training, " +
				 "    tot_emp " +
				 "  FROM " +
				 "    (SELECT COUNT(EMPLOYEE_ID) AS tot_emp, " +
				 "      TRG_PROGRAMME_ID " +
				 "    FROM HRM_TRG_PROG_CUTOFF_TRN_NEW " +
				 "    WHERE TRG_PROGRAMME_ID IN " +
				 "      (SELECT TRG_PROGRAMME_ID " +
				 "      FROM HRM_TRG_PROG_CUTOFF_MST_NEW " +
				 "      WHERE VENUE_TRG_CENTRE_ID             =? " +
				 "      AND extract (YEAR FROM SCHEDULED_FROM)=? " +
				 "      ) " +
				 "    GROUP BY TRG_PROGRAMME_ID " +
				 "    )d " +
				 "  LEFT OUTER JOIN " +
				 "    ( SELECT DISTINCT PROG_TITLE_ID, " +
				 "      TRG_PROGRAMME_ID, " +
				 "      VENUE_TRG_CENTRE_ID " +
				 "    FROM HRM_TRG_PROG_CUTOFF_MST_NEW " +
				 "    WHERE VENUE_TRG_CENTRE_ID             =? " +
				 "    AND extract (YEAR FROM SCHEDULED_FROM)=? " +
				 "    )a " +
				 "  ON d.TRG_PROGRAMME_ID=a.TRG_PROGRAMME_ID " +
				 "  LEFT OUTER JOIN " +
				 "    (SELECT TRG_CENTRE_CODE,TRG_CENTRE_NAME FROM hrm_trg_centre_mst " +
				 "    )xx " +
				 "  ON a.VENUE_TRG_CENTRE_ID=xx.TRG_CENTRE_CODE " +
				 "  LEFT OUTER JOIN " +
				 "    (SELECT PROG_TITLE_ID AS title_id, " +
				 "      PROG_TITLE_NAME " +
				 "    FROM HRM_TRG_PROG_TITLE_MST " +
				 "    )b " +
				 "  ON a.PROG_TITLE_ID=b.title_id " +
				 "  LEFT OUTER JOIN " +
				 "    (SELECT COUNT(PROG_TITLE_ID) AS tot_no_training , " +
				 "      PROG_TITLE_ID              AS prg_tit_id " +
				 "    FROM HRM_TRG_PROG_CUTOFF_MST_NEW " +
				 "    WHERE VENUE_TRG_CENTRE_ID             =? " +
				 "    AND extract (YEAR FROM SCHEDULED_FROM)=? " +
				 "    GROUP BY PROG_TITLE_ID " +
				 "    )c " +
				 "  ON c.prg_tit_id=a.PROG_TITLE_ID " +
				 "  ) " +
				 "WHERE tot_emp   IS NOT NULL " +
				 "AND prog_title_id=prog_title_id " +
				 "GROUP BY VENUE_TRG_CENTRE_ID, " +
				 "  TRG_CENTRE_NAME, " +
				 "  prog_title_id, " +
				 "  prog_title_name, " +
				 "  tot_no_training"
				 ;
				 
				 System.out.println("sql query = "+sql_query+" ");
				 
				 try {
					ps=con.prepareStatement(sql_query);
					
					ps.setString(1, program_id);
					ps.setInt(2, year);
					
					ps.setString(3, program_id);
					ps.setInt(4, year);
					ps.setString(5, program_id);
					ps.setInt(6, year);
					 rss=ps.executeQuery();
					 while(rss.next())
					 {
						 cou++;
						 
						 xml=xml+"<VENUE_TRG_CENTRE_ID>"+rss.getString("VENUE_TRG_CENTRE_ID")+"</VENUE_TRG_CENTRE_ID>";
						 xml=xml+"<TRG_CENTRE_NAME>"+rss.getString("TRG_CENTRE_NAME")+"</TRG_CENTRE_NAME>";
						 xml=xml+"<prog_title_id>"+rss.getInt("prog_title_id")+"</prog_title_id>";						 
						 xml=xml+"<PROG_TITLE_NAME>"+rss.getString("prog_title_name")+"</PROG_TITLE_NAME>";
						 
						 xml=xml+"<tot_no_training>"+rss.getInt("tot_no_training")+"</tot_no_training>";
						 xml=xml+"<tot_emp>"+rss.getInt("tot_emp")+"</tot_emp>";
						 xml=xml+"<Year>"+year+"</Year>";
						 year--;
//						 xml=xml+"<SANCTIONED_NO_OF_POSTS>"+rss.getInt("total_sanc_posts")+"</SANCTIONED_NO_OF_POSTS>";
//						 xml=xml+"<FILLEDUP_POSTS>"+rss.getInt("total_filled_up")+"</FILLEDUP_POSTS>";
//						 xml=xml+"<DIVERSION_TO_OTHER>"+rss.getInt("total_diversion_to")+"</DIVERSION_TO_OTHER>";
//						 
//						 xml=xml+"<DIVERSION_FROM_OTHER>"+rss.getInt("total_diversion_from")+"</DIVERSION_FROM_OTHER>";
//						 xml=xml+"<vacant_posts>"+rss.getString("total_vacant")+"</vacant_posts>";
//						 xml=xml+"<vacancy_percent>"+rss.getString("vacancy_percent")+"</vacancy_percent>";
						 
						 
						 
						 
//						 String sql_querys="SELECT venue_trg_centre, " +
//						 "  i.PROG_TITLE_ID, " +
//						 "  total_emp_trained, " +
//						 "  prog_title_name " +
//						 "FROM " +
//						 "  (SELECT DECODE(VENUE_TRG_CENTRE_ID,NULL,'Other_Centres',VENUE_TRG_CENTRE_ID) AS venue_trg_centre, " +
//						 "    PROG_TITLE_ID, " +
//						 "    total_emp_trained " +
//						 "  FROM " +
//						 "    (SELECT VENUE_TRG_CENTRE_ID, " +
//						 "      PROG_TITLE_ID, " +
//						 "      COUNT(*) AS total_emp_trained " +
//						 "    FROM " +
//						 "      (SELECT TRG_PROGRAMME_ID, " +
//						 "        VENUE_TRG_CENTRE_ID, " +
//						 "        PROG_TITLE_ID, " +
//						 "        SCHEDULED_FROM, " +
//						 "        employee_id " +
//						 "      FROM " +
//						 "        (SELECT TRG_PROGRAMME_ID, " +
//						 "          VENUE_TRG_CENTRE_ID, " +
//						 "          PROG_TITLE_ID, " +
//						 "          SCHEDULED_FROM, " +
//						 "          SCHEDULED_TO " +
//						 "        FROM HRM_TRG_PROG_CUTOFF_MST_NEW " +
//						 "        WHERE EXTRACT(YEAR FROM SCHEDULED_TO) = ? " +
//						 "        AND VENUE_TRG_CENTRE_ID               =? " +
//						 "        ) a " +
//						 "      INNER JOIN " +
//						 "        (SELECT trg_programme_id AS trg_id, " +
//						 "          employee_id " +
//						 "        FROM HRM_TRG_PROG_CUTOFF_TRN_NEW " +
//						 "        ) b " +
//						 "      ON a.TRG_PROGRAMME_ID = b.trg_id " +
//						 "      ) x " +
//						 "    GROUP BY VENUE_TRG_CENTRE_ID, " +
//						 "      PROG_TITLE_ID " +
//						 "    ) y " +
//						 "  )i " +
//						 "LEFT OUTER JOIN " +
//						 "  ( SELECT prog_title_id,prog_title_name FROM HRM_TRG_PROG_TITLE_MST " +
//						 "  )j " +
//						 "ON i.PROG_TITLE_ID=j.prog_title_id " +
//						 "ORDER BY 1"
//						 ;
//						 
//						 ps1=con.prepareStatement(sql_querys);
//						 ps1.setInt(1, year);
//						 ps1.setString(2, program_id);
//						 rs1=ps1.executeQuery();
//						 
//						 while(rs1.next())
//						 {
//							 xml=xml+"<total_sub_emp_trained>"+rss.getInt("total_emp_trained")+"</total_sub_emp_trained>";
//						 }
//						 
						 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<empname>"+rss.getString("empname")+"</empname>";
//						 xml=xml+"<CATEGORY_ID>"+rss.getString("CATEGORY_ID")+"</CATEGORY_ID>";
//						 xml=xml+"<category_desc>"+rss.getString("category_desc")+"</category_desc>";
//						 xml=xml+"<CLASSIFICATION_ID>"+rss.getString("CLASSIFICATION_ID")+"</CLASSIFICATION_ID>";
//						 xml=xml+"<classification_desc>"+rss.getString("classification_desc")+"</classification_desc>";
//						 xml=xml+"<SUB_CLASSIFICATION_ID>"+rss.getString("SUB_CLASSIFICATION_ID")+"</SUB_CLASSIFICATION_ID>";
//						 xml=xml+"<sub_classification_desc>"+rss.getString("sub_classification_desc")+"</sub_classification_desc>";					 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<status_desc>"+rss.getString("status_desc")+"</status_desc>";
//						 
//						 xml=xml+"<AG_FILE_NO>"+rss.getString("AG_FILE_NO")+"</AG_FILE_NO>";
//						 xml=xml+"<GIST>"+rss.getString("GIST")+"</GIST>";
//						 xml=xml+"<SLIP_DATE>"+rss.getString("SLIP_DATE")+"</SLIP_DATE>";
//						 xml=xml+"<RESPONSIBILITY_FIXED>"+rss.getString("RESPONSIBILITY_FIXED")+"</RESPONSIBILITY_FIXED>";
//						 xml=xml+"<MONETARY_LOSS_EXISTS>"+rss.getString("MONETARY_LOSS_EXISTS")+"</MONETARY_LOSS_EXISTS>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<PRIORITY>"+rss.getString("PRIORITY")+"</PRIORITY>";
//						 xml=xml+"<PROCESS_FLOW_ID>"+rss.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
						 
						 
						 
						 
						 
						 
						 
						 
					 }
					 
					 xml=xml+"<count>"+cou+"</count>";
					 if(cou!=0)
					 {
						 xml=xml+"<flag>success</flag>";
					 }
					 else
					 {
						 xml=xml+"<flag>failure</flag>";
					 }
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					xml=xml+"<flag>failure</flag>";
					e.printStackTrace();
				}
				
			
			
			
			
			}
			
			else if(cmd.equalsIgnoreCase("cadre_wise_sub_chart"))
			{




				xml=xml+"<command>cadre_wise_sub_chart</command>";
				int cou=0,year=0,cadre_id=0;
				year=Integer.parseInt(request.getParameter("year"));
				cadre_id=Integer.parseInt(request.getParameter("cadre_id"));
				
				int year15=year+1;
				int year16=year+2;
				int year17=year+3;
				int year18=year+4;
				 System.out.println("2018 ==== "+year18);
//				 sql_query="SELECT DECODE(VENUE_TRG_CENTRE_ID,NULL,'Other_Centres',VENUE_TRG_CENTRE_ID) AS VENUE_TRG_CENTRE_ID, " +
//				 "  TOT_NO_PROGRAMMES, " +
//				 "  DECODE(b.TRG_CENTRE_NAME,NULL,'Other_Centres',b.TRG_CENTRE_NAME) AS TRG_CENTRE_NAME " +
//				 "FROM " +
//				 "  (SELECT VENUE_TRG_CENTRE_ID, " +
//				 "    COUNT(*) AS TOT_NO_PROGRAMMES " +
//				 "  FROM HRM_TRG_PROG_CUTOFF_MST_NEW " +
//				 "  WHERE EXTRACT(YEAR FROM SCHEDULED_FROM) = ? " +
//				 "  GROUP BY VENUE_TRG_CENTRE_ID " +
//				 "  ) a " +
//				 "LEFT OUTER JOIN " +
//				 "  (SELECT TRG_CENTRE_CODE,TRG_CENTRE_NAME FROM hrm_trg_centre_mst " +
//				 "  )b " +
//				 "ON a.VENUE_TRG_CENTRE_ID=b.TRG_CENTRE_CODE " +
//				 "ORDER BY 2 DESC" ;
				 
				 sql_query="SELECT cadre_id, " +
				 "  cadre_name, " +
				 "  YEAR as Year_wise, " +
				 "  year2_retire AS total_retire " +
				 "FROM " +
				 "  (SELECT ?   AS YEAR , " +
				 "    COUNT( *) AS YEAR2_RETIRE, " +
				 "    cadre_id  AS RANK_ID2 " +
				 "  FROM ALLRETIREMENTVIEW " +
				 "  WHERE RETYEAR=? " +
				 "  AND cadre_id =? " +
				 "  GROUP BY ?, " +
				 "    cadre_id " +
				 "  UNION ALL " +
				 "  SELECT ? , " +
				 "    COUNT( *) AS YEAR3_RETIRE, " +
				 "    cadre_id  AS RANK_ID3 " +
				 "  FROM ALLRETIREMENTVIEW " +
				 "  WHERE RETYEAR=? " +
				 "  AND cadre_id =? " +
				 "  GROUP BY cadre_id " +
				 "  UNION ALL " +
				 "  SELECT ?, " +
				 "    COUNT( *) AS YEAR4_RETIRE, " +
				 "    cadre_id  AS RANK_ID4 " +
				 "  FROM ALLRETIREMENTVIEW " +
				 "  WHERE RETYEAR=? " +
				 "  AND cadre_id =? " +
				 "  GROUP BY cadre_id " +
				 "  UNION ALL " +
				 "  SELECT ?, " +
				 "    COUNT( *) AS YEAR5_RETIRE, " +
				 "    cadre_id  AS RANK_ID5 " +
				 "  FROM ALLRETIREMENTVIEW " +
				 "  WHERE RETYEAR=? " +
				 "  AND cadre_id =? " +
				 "  GROUP BY cadre_id " +
				 "  UNION ALL " +
				 "  SELECT ?, " +
				 "    COUNT( *) AS YEAR5_RETIRE, " +
				 "    cadre_id  AS RANK_ID5 " +
				 "  FROM ALLRETIREMENTVIEW " +
				 "  WHERE RETYEAR=? " +
				 "  AND cadre_id =? " +
				 "  GROUP BY cadre_id " +
				 "  )a " +
				 "LEFT OUTER JOIN " +
				 "  ( SELECT cadre_id,cadre_name FROM HRM_mst_cadre WHERE cadre_id=? " +
				 "  )b " +
				 "ON a.RANK_ID2=b.cadre_id " +
				 "ORDER BY YEAR"
				 ;
				 
				 System.out.println("sql query = "+sql_query+" ");
				 
				 try {
					ps=con.prepareStatement(sql_query);
					ps.setInt(1, year);
					ps.setInt(2, year);
					ps.setInt(3, cadre_id);
					ps.setInt(4, year);
					
					ps.setInt(5, year15);
					ps.setInt(6, year15);
					ps.setInt(7, cadre_id);
					
					
					
					ps.setInt(8, year16);
					ps.setInt(9, year16);
					ps.setInt(10, cadre_id);
					
					
					ps.setInt(11, year17);
					ps.setInt(12, year17);
					ps.setInt(13, cadre_id);
					
					
					
					
					ps.setInt(14, year18);
					ps.setInt(15, year18);
					ps.setInt(16, cadre_id);
					
					
					ps.setInt(17, cadre_id);
					
					
					
					
					 rss=ps.executeQuery();
					 while(rss.next())
					 {
						 cou++;
						 xml=xml+"<cadre_name>"+rss.getString("cadre_name")+"</cadre_name>";
//						 xml=xml+"<VENUE_TRG_CENTRE_ID>"+rss.getString("VENUE_TRG_CENTRE_ID")+"</VENUE_TRG_CENTRE_ID>";
						 xml=xml+"<cadre_id>"+rss.getInt("cadre_id")+"</cadre_id>";
						 xml=xml+"<Year>"+rss.getInt("Year_wise")+"</Year>";
						 
						 xml=xml+"<total_retire>"+rss.getInt("total_retire")+"</total_retire>";
//						 xml=xml+"<total_retire_15>"+rss.getInt("year2015")+"</total_retire_15>";
//						 xml=xml+"<total_retire_16>"+rss.getInt("year2016")+"</total_retire_16>";
//						 xml=xml+"<total_retire_17>"+rss.getInt("year2017")+"</total_retire_17>";
//						 xml=xml+"<total_retire_18>"+rss.getInt("year2018")+"</total_retire_18>";
						// year++;
//						 xml=xml+"<SANCTIONED_NO_OF_POSTS>"+rss.getInt("total_sanc_posts")+"</SANCTIONED_NO_OF_POSTS>";
//						 xml=xml+"<FILLEDUP_POSTS>"+rss.getInt("total_filled_up")+"</FILLEDUP_POSTS>";
//						 xml=xml+"<DIVERSION_TO_OTHER>"+rss.getInt("total_diversion_to")+"</DIVERSION_TO_OTHER>";
//						 
//						 xml=xml+"<DIVERSION_FROM_OTHER>"+rss.getInt("total_diversion_from")+"</DIVERSION_FROM_OTHER>";
//						 xml=xml+"<vacant_posts>"+rss.getString("total_vacant")+"</vacant_posts>";
//						 xml=xml+"<vacancy_percent>"+rss.getString("vacancy_percent")+"</vacancy_percent>";
						 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<empname>"+rss.getString("empname")+"</empname>";
//						 xml=xml+"<CATEGORY_ID>"+rss.getString("CATEGORY_ID")+"</CATEGORY_ID>";
//						 xml=xml+"<category_desc>"+rss.getString("category_desc")+"</category_desc>";
//						 xml=xml+"<CLASSIFICATION_ID>"+rss.getString("CLASSIFICATION_ID")+"</CLASSIFICATION_ID>";
//						 xml=xml+"<classification_desc>"+rss.getString("classification_desc")+"</classification_desc>";
//						 xml=xml+"<SUB_CLASSIFICATION_ID>"+rss.getString("SUB_CLASSIFICATION_ID")+"</SUB_CLASSIFICATION_ID>";
//						 xml=xml+"<sub_classification_desc>"+rss.getString("sub_classification_desc")+"</sub_classification_desc>";					 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<status_desc>"+rss.getString("status_desc")+"</status_desc>";
//						 
//						 xml=xml+"<AG_FILE_NO>"+rss.getString("AG_FILE_NO")+"</AG_FILE_NO>";
//						 xml=xml+"<GIST>"+rss.getString("GIST")+"</GIST>";
//						 xml=xml+"<SLIP_DATE>"+rss.getString("SLIP_DATE")+"</SLIP_DATE>";
//						 xml=xml+"<RESPONSIBILITY_FIXED>"+rss.getString("RESPONSIBILITY_FIXED")+"</RESPONSIBILITY_FIXED>";
//						 xml=xml+"<MONETARY_LOSS_EXISTS>"+rss.getString("MONETARY_LOSS_EXISTS")+"</MONETARY_LOSS_EXISTS>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<PRIORITY>"+rss.getString("PRIORITY")+"</PRIORITY>";
//						 xml=xml+"<PROCESS_FLOW_ID>"+rss.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
					 }
					 
					 xml=xml+"<count>"+cou+"</count>";
					 if(cou!=0)
					 {
						 xml=xml+"<flag>success</flag>";
					 }
					 else
					 {
						 xml=xml+"<flag>failure</flag>";
					 }
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					xml=xml+"<flag>failure</flag>";
					e.printStackTrace();
				}
				
			
			
			
			
			}
			
			
			else if(cmd.equalsIgnoreCase("cadre_wise_chart"))
			{



				xml=xml+"<command>cadre_wise_chart</command>";
				int cou=0,year=0,service_group_id=0;
				year=Integer.parseInt(request.getParameter("year"));
				service_group_id=Integer.parseInt(request.getParameter("service_group"));
				
				
				 System.out.println(type_id);
//				 sql_query="SELECT DECODE(VENUE_TRG_CENTRE_ID,NULL,'Other_Centres',VENUE_TRG_CENTRE_ID) AS VENUE_TRG_CENTRE_ID, " +
//				 "  TOT_NO_PROGRAMMES, " +
//				 "  DECODE(b.TRG_CENTRE_NAME,NULL,'Other_Centres',b.TRG_CENTRE_NAME) AS TRG_CENTRE_NAME " +
//				 "FROM " +
//				 "  (SELECT VENUE_TRG_CENTRE_ID, " +
//				 "    COUNT(*) AS TOT_NO_PROGRAMMES " +
//				 "  FROM HRM_TRG_PROG_CUTOFF_MST_NEW " +
//				 "  WHERE EXTRACT(YEAR FROM SCHEDULED_FROM) = ? " +
//				 "  GROUP BY VENUE_TRG_CENTRE_ID " +
//				 "  ) a " +
//				 "LEFT OUTER JOIN " +
//				 "  (SELECT TRG_CENTRE_CODE,TRG_CENTRE_NAME FROM hrm_trg_centre_mst " +
//				 "  )b " +
//				 "ON a.VENUE_TRG_CENTRE_ID=b.TRG_CENTRE_CODE " +
//				 "ORDER BY 2 DESC" ;
				 
				 sql_query="SELECT cadre_id, " +
				 "  cadre_name, " +
				 "  total_retire_knt " +
				 "FROM " +
				 "  (SELECT cadre_id, " +
				 "    COUNT(*) AS total_retire_knt " +
				 "  FROM " +
				 "    (SELECT EMPLOYEE_ID, " +
				 "      designation_id, " +
				 "      cadre_id, " +
				 "      service_group_id, " +
				 "      year_of_retire, " +
				 "      month_of_retire " +
				 "    FROM " +
				 "      (SELECT EMPLOYEE_ID, " +
				 "        designation_id, " +
				 "        year_of_retire, " +
				 "        month_of_retire " +
				 "      FROM " +
				 "        (SELECT EMPLOYEE_ID, " +
				 "          extract(YEAR FROM retiredate)  AS year_of_retire, " +
				 "          extract(MONTH FROM retiredate) AS month_of_retire " +
				 "        FROM HRM_PAY_RETIREMENT_VIEW " +
				 "        WHERE extract(YEAR FROM retiredate) = ? " +
				 "        ) A " +
				 "      LEFT OUTER JOIN " +
				 "        ( SELECT employee_id AS emp_id, designation_id FROM hrm_emp_current_posting " +
				 "        ) b " +
				 "      ON a.employee_id = b.emp_id " +
				 "      ) x " +
				 "    LEFT OUTER JOIN " +
				 "      (SELECT designation_id AS desig_id, " +
				 "        cadre_id, " +
				 "        service_group_id " +
				 "      FROM hrm_mst_designations " +
				 "      ) y " +
				 "    ON x.designation_id   = y.desig_id " +
				 "    WHERE service_group_id= ? " +
				 "    ) m " +
				 "  GROUP BY cadre_id " +
				 "  ) aa " +
				 "LEFT OUTER JOIN " +
				 "  ( SELECT cadre_id AS cadreid, cadre_name FROM hrm_mst_cadre " +
				 "  ) bb " +
				 "ON aa.cadre_id =bb.cadreid " +
				 "ORDER BY cadre_id"
				 ;
				 
				 System.out.println("sql query = "+sql_query+" ");
				 
				 try {
					ps=con.prepareStatement(sql_query);
					ps.setInt(1, year);
					ps.setInt(2, service_group_id);
					 rss=ps.executeQuery();
					 while(rss.next())
					 {
						 cou++;
						 xml=xml+"<cadre_name>"+rss.getString("cadre_name")+"</cadre_name>";
//						 xml=xml+"<VENUE_TRG_CENTRE_ID>"+rss.getString("VENUE_TRG_CENTRE_ID")+"</VENUE_TRG_CENTRE_ID>";
						 xml=xml+"<cadre_id>"+rss.getInt("cadre_id")+"</cadre_id>";
						 xml=xml+"<Year>"+year+"</Year>";
						 xml=xml+"<total_retire>"+rss.getInt("total_retire_knt")+"</total_retire>";
						 year++;
//						 xml=xml+"<SANCTIONED_NO_OF_POSTS>"+rss.getInt("total_sanc_posts")+"</SANCTIONED_NO_OF_POSTS>";
//						 xml=xml+"<FILLEDUP_POSTS>"+rss.getInt("total_filled_up")+"</FILLEDUP_POSTS>";
//						 xml=xml+"<DIVERSION_TO_OTHER>"+rss.getInt("total_diversion_to")+"</DIVERSION_TO_OTHER>";
//						 
//						 xml=xml+"<DIVERSION_FROM_OTHER>"+rss.getInt("total_diversion_from")+"</DIVERSION_FROM_OTHER>";
//						 xml=xml+"<vacant_posts>"+rss.getString("total_vacant")+"</vacant_posts>";
//						 xml=xml+"<vacancy_percent>"+rss.getString("vacancy_percent")+"</vacancy_percent>";
						 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<empname>"+rss.getString("empname")+"</empname>";
//						 xml=xml+"<CATEGORY_ID>"+rss.getString("CATEGORY_ID")+"</CATEGORY_ID>";
//						 xml=xml+"<category_desc>"+rss.getString("category_desc")+"</category_desc>";
//						 xml=xml+"<CLASSIFICATION_ID>"+rss.getString("CLASSIFICATION_ID")+"</CLASSIFICATION_ID>";
//						 xml=xml+"<classification_desc>"+rss.getString("classification_desc")+"</classification_desc>";
//						 xml=xml+"<SUB_CLASSIFICATION_ID>"+rss.getString("SUB_CLASSIFICATION_ID")+"</SUB_CLASSIFICATION_ID>";
//						 xml=xml+"<sub_classification_desc>"+rss.getString("sub_classification_desc")+"</sub_classification_desc>";					 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<status_desc>"+rss.getString("status_desc")+"</status_desc>";
//						 
//						 xml=xml+"<AG_FILE_NO>"+rss.getString("AG_FILE_NO")+"</AG_FILE_NO>";
//						 xml=xml+"<GIST>"+rss.getString("GIST")+"</GIST>";
//						 xml=xml+"<SLIP_DATE>"+rss.getString("SLIP_DATE")+"</SLIP_DATE>";
//						 xml=xml+"<RESPONSIBILITY_FIXED>"+rss.getString("RESPONSIBILITY_FIXED")+"</RESPONSIBILITY_FIXED>";
//						 xml=xml+"<MONETARY_LOSS_EXISTS>"+rss.getString("MONETARY_LOSS_EXISTS")+"</MONETARY_LOSS_EXISTS>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<PRIORITY>"+rss.getString("PRIORITY")+"</PRIORITY>";
//						 xml=xml+"<PROCESS_FLOW_ID>"+rss.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
					 }
					 
					 xml=xml+"<count>"+cou+"</count>";
					 if(cou!=0)
					 {
						 xml=xml+"<flag>success</flag>";
					 }
					 else
					 {
						 xml=xml+"<flag>failure</flag>";
					 }
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					xml=xml+"<flag>failure</flag>";
					e.printStackTrace();
				}
				
			
			
			
			}
			
			else if(cmd.equalsIgnoreCase("vacants_circle_wise"))
			{


				xml=xml+"<command>vacants_circle_wise</command>";
				int cou=0,region_id=0;
				region_id=Integer.parseInt(request.getParameter("region_id"));
				
				
				
				 System.out.println(type_id);
				 sql_query="SELECT office_name, " +
				 "  office_id, " +
				 "  office_level_id, " +
				 "  total_sanc_posts, " +
				 "  total_filled_up, " +
				 "  total_diversion_to, " +
				 "  total_diversion_from, " +
				 "  total_vacant, " +
				 "  ROUND(total_vacant/total_sanc_posts*100,0) AS vacancy_percent " +
				 "FROM " +
				 "  (SELECT region_office_id, " +
				 "    circle_office_id, " +
				 "    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc_posts, " +
				 "    SUM(FILLEDUP_POSTS)         AS total_filled_up, " +
				 "    SUM(DIVERSION_TO_OTHER)     AS total_diversion_to, " +
				 "    SUM(DIVERSION_FROM_OTHER)   AS total_diversion_from, " +
				 "    SUM(vacant_posts)           AS total_vacant " +
				 "  FROM " +
				 "    (SELECT office_id, " +
				 "      region_office_id, " +
				 "      DECODE(circle_office_id,NULL,region_office_id, circle_office_id) AS circle_office_id, " +
				 "      division_office_id, " +
				 "      SANCTIONED_NO_OF_POSTS, " +
				 "      FILLEDUP_POSTS, " +
				 "      DIVERSION_TO_OTHER, " +
				 "      DIVERSION_FROM_OTHER, " +
				 "      TOTAL, " +
				 "      REMAININGPOSTS AS vacant_posts " +
				 "    FROM " +
				 "      (SELECT FIN_YEAR, " +
				 "        OFFICE_ID, " +
				 "        POST_RANK_ID, " +
				 "        SANCTIONED_NO_OF_POSTS, " +
				 "        FILLEDUP_POSTS, " +
				 "        DIVERSION_TO_OTHER, " +
				 "        DIVERSION_FROM_OTHER, " +
				 "        TOTAL, " +
				 "        REMAININGPOSTS, " +
				 "        OFF_ORDER " +
				 "      FROM HRM_SANCTIONED_ALL_VEW " +
				 "      WHERE post_rank_id = 8 " +
				 "      AND office_id NOT IN ( 5000 ) " +
				 "      AND fin_year       = '2013-2014' " +
				 "      ) a " +
				 "    LEFT OUTER JOIN " +
				 "      (SELECT OFFICE_ID AS off_id, " +
				 "        OFFICE_NAME, " +
				 "        OFFICE_LEVEL_ID, " +
				 "        REGION_OFFICE_ID, " +
				 "        CIRCLE_OFFICE_ID, " +
				 "        DIVISION_OFFICE_ID, " +
				 "        SUBDIVISION_OFFICE_ID, " +
				 "        AUDITWING_OFFICE_ID, " +
				 "        LAB_OFFICE_ID, " +
				 "        OFF_ORDER " +
				 "      FROM COM_MST_ALL_OFFICES_VIEW " +
				 "      ) b " +
				 "    ON a.office_id = b.off_id " +
				 "    LEFT OUTER JOIN " +
				 "      ( SELECT post_rank_id AS post_id, post_rank_name FROM hrm_mst_post_ranks " +
				 "      ) c " +
				 "    ON a.post_rank_id = c.post_id " +
				 "    ) x " +
				 "  GROUP BY region_office_id, " +
				 "    circle_office_id " +
				 "  HAVING region_office_id = ? " +
				 "  ) y " +
				 "INNER JOIN " +
				 "  (SELECT office_id , " +
				 "    office_name , " +
				 "    office_level_id " +
				 "  FROM com_mst_offices " +
				 "  WHERE office_status_id = 'CR' " +
				 "  AND office_id         >=5000 " +
				 "  ) z " +
				 "ON y.circle_office_id = z.office_id" ;
				 
				 System.out.println("sql query = "+sql_query+" ");
				 
				 try {
					ps=con.prepareStatement(sql_query);
					ps.setInt(1, region_id);
					 rss=ps.executeQuery();
					 while(rss.next())
					 {
						 cou++;
						 xml=xml+"<office_name>"+rss.getString("office_name")+"</office_name>";
						 xml=xml+"<region_office_id>"+rss.getInt("office_id")+"</region_office_id>";
						 xml=xml+"<SANCTIONED_NO_OF_POSTS>"+rss.getInt("total_sanc_posts")+"</SANCTIONED_NO_OF_POSTS>";
						 xml=xml+"<FILLEDUP_POSTS>"+rss.getInt("total_filled_up")+"</FILLEDUP_POSTS>";
						 xml=xml+"<DIVERSION_TO_OTHER>"+rss.getInt("total_diversion_to")+"</DIVERSION_TO_OTHER>";
						 
						 xml=xml+"<DIVERSION_FROM_OTHER>"+rss.getInt("total_diversion_from")+"</DIVERSION_FROM_OTHER>";
						 xml=xml+"<vacant_posts>"+rss.getString("total_vacant")+"</vacant_posts>";
						 xml=xml+"<vacancy_percent>"+rss.getString("vacancy_percent")+"</vacancy_percent>";
						 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<empname>"+rss.getString("empname")+"</empname>";
//						 xml=xml+"<CATEGORY_ID>"+rss.getString("CATEGORY_ID")+"</CATEGORY_ID>";
//						 xml=xml+"<category_desc>"+rss.getString("category_desc")+"</category_desc>";
//						 xml=xml+"<CLASSIFICATION_ID>"+rss.getString("CLASSIFICATION_ID")+"</CLASSIFICATION_ID>";
//						 xml=xml+"<classification_desc>"+rss.getString("classification_desc")+"</classification_desc>";
//						 xml=xml+"<SUB_CLASSIFICATION_ID>"+rss.getString("SUB_CLASSIFICATION_ID")+"</SUB_CLASSIFICATION_ID>";
//						 xml=xml+"<sub_classification_desc>"+rss.getString("sub_classification_desc")+"</sub_classification_desc>";					 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<status_desc>"+rss.getString("status_desc")+"</status_desc>";
//						 
//						 xml=xml+"<AG_FILE_NO>"+rss.getString("AG_FILE_NO")+"</AG_FILE_NO>";
//						 xml=xml+"<GIST>"+rss.getString("GIST")+"</GIST>";
//						 xml=xml+"<SLIP_DATE>"+rss.getString("SLIP_DATE")+"</SLIP_DATE>";
//						 xml=xml+"<RESPONSIBILITY_FIXED>"+rss.getString("RESPONSIBILITY_FIXED")+"</RESPONSIBILITY_FIXED>";
//						 xml=xml+"<MONETARY_LOSS_EXISTS>"+rss.getString("MONETARY_LOSS_EXISTS")+"</MONETARY_LOSS_EXISTS>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<PRIORITY>"+rss.getString("PRIORITY")+"</PRIORITY>";
//						 xml=xml+"<PROCESS_FLOW_ID>"+rss.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
					 }
					 
					 xml=xml+"<count>"+cou+"</count>";
					 if(cou!=0)
					 {
						 xml=xml+"<flag>success</flag>";
					 }
					 else
					 {
						 xml=xml+"<flag>failure</flag>";
					 }
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					xml=xml+"<flag>failure</flag>";
					e.printStackTrace();
				}
				
			
			
			}
			
			else if(cmd.equalsIgnoreCase("vacants"))
			{

				xml=xml+"<command>vacants</command>";
				int cou=0;
			
				
				
				
				 System.out.println(type_id);
				 sql_query="SELECT office_name, " +
				 "  office_id, " +
				 "  office_level_id, " +
				 "  total_sanc_posts, " +
				 "  total_filled_up, " +
				 "  total_diversion_to, " +
				 "  total_diversion_from, " +
				 "  total_vacant, " +
				 "  ROUND(total_vacant/total_sanc_posts*100,0) AS vacancy_percent " +
				 "FROM " +
				 "  (SELECT region_office_id, " +
				 "    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc_posts, " +
				 "    SUM(FILLEDUP_POSTS)         AS total_filled_up, " +
				 "    SUM(DIVERSION_TO_OTHER)     AS total_diversion_to, " +
				 "    SUM(DIVERSION_FROM_OTHER)   AS total_diversion_from, " +
				 "    SUM(vacant_posts)           AS total_vacant " +
				 "  FROM " +
				 "    (SELECT office_id, " +
				 "      region_office_id, " +
				 "      circle_office_id, " +
				 "      division_office_id, " +
				 "      SANCTIONED_NO_OF_POSTS, " +
				 "      FILLEDUP_POSTS, " +
				 "      DIVERSION_TO_OTHER, " +
				 "      DIVERSION_FROM_OTHER, " +
				 "      TOTAL, " +
				 "      REMAININGPOSTS AS vacant_posts " +
				 "    FROM " +
				 "      (SELECT FIN_YEAR, " +
				 "        OFFICE_ID, " +
				 "        POST_RANK_ID, " +
				 "        SANCTIONED_NO_OF_POSTS, " +
				 "        FILLEDUP_POSTS, " +
				 "        DIVERSION_TO_OTHER, " +
				 "        DIVERSION_FROM_OTHER, " +
				 "        TOTAL, " +
				 "        REMAININGPOSTS, " +
				 "        OFF_ORDER " +
				 "      FROM HRM_SANCTIONED_ALL_VEW " +
				 "      WHERE post_rank_id = 8 " +
				 "      AND office_id NOT IN ( 5000 ) " +
				 "      AND fin_year       = '2013-2014' " +
				 "      ) a " +
				 "    LEFT OUTER JOIN " +
				 "      (SELECT OFFICE_ID AS off_id, " +
				 "        OFFICE_NAME, " +
				 "        OFFICE_LEVEL_ID, " +
				 "        REGION_OFFICE_ID, " +
				 "        CIRCLE_OFFICE_ID, " +
				 "        DIVISION_OFFICE_ID, " +
				 "        SUBDIVISION_OFFICE_ID, " +
				 "        AUDITWING_OFFICE_ID, " +
				 "        LAB_OFFICE_ID, " +
				 "        OFF_ORDER " +
				 "      FROM COM_MST_ALL_OFFICES_VIEW " +
				 "      ) b " +
				 "    ON a.office_id = b.off_id " +
				 "    LEFT OUTER JOIN " +
				 "      ( SELECT post_rank_id AS post_id, post_rank_name FROM hrm_mst_post_ranks " +
				 "      ) c " +
				 "    ON a.post_rank_id = c.post_id " +
				 "    ) x " +
				 "  GROUP BY region_office_id " +
				 "  ) y " +
				 "LEFT OUTER JOIN " +
				 "  (SELECT office_id , " +
				 "    office_name , " +
				 "    office_level_id " +
				 "  FROM com_mst_offices " +
				 "  WHERE office_status_id = 'CR' " +
				 "  AND office_id         >=5000 " +
				 "  ) z " +
				 "ON y.region_office_id = z.office_id"
				 ;
				 
				 System.out.println("sql query = "+sql_query+" ");
				 
				 try {
					ps=con.prepareStatement(sql_query);
					
					 rss=ps.executeQuery();
					 while(rss.next())
					 {
						 cou++;
						 xml=xml+"<office_name>"+rss.getString("office_name")+"</office_name>";
						 xml=xml+"<region_office_id>"+rss.getInt("office_id")+"</region_office_id>";
						 xml=xml+"<SANCTIONED_NO_OF_POSTS>"+rss.getInt("total_sanc_posts")+"</SANCTIONED_NO_OF_POSTS>";
						 xml=xml+"<FILLEDUP_POSTS>"+rss.getInt("total_filled_up")+"</FILLEDUP_POSTS>";
						 xml=xml+"<DIVERSION_TO_OTHER>"+rss.getInt("total_diversion_to")+"</DIVERSION_TO_OTHER>";
						 
						 xml=xml+"<DIVERSION_FROM_OTHER>"+rss.getInt("total_diversion_from")+"</DIVERSION_FROM_OTHER>";
						 xml=xml+"<vacant_posts>"+rss.getString("total_vacant")+"</vacant_posts>";
						 xml=xml+"<vacancy_percent>"+rss.getString("vacancy_percent")+"</vacancy_percent>";
						 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<empname>"+rss.getString("empname")+"</empname>";
//						 xml=xml+"<CATEGORY_ID>"+rss.getString("CATEGORY_ID")+"</CATEGORY_ID>";
//						 xml=xml+"<category_desc>"+rss.getString("category_desc")+"</category_desc>";
//						 xml=xml+"<CLASSIFICATION_ID>"+rss.getString("CLASSIFICATION_ID")+"</CLASSIFICATION_ID>";
//						 xml=xml+"<classification_desc>"+rss.getString("classification_desc")+"</classification_desc>";
//						 xml=xml+"<SUB_CLASSIFICATION_ID>"+rss.getString("SUB_CLASSIFICATION_ID")+"</SUB_CLASSIFICATION_ID>";
//						 xml=xml+"<sub_classification_desc>"+rss.getString("sub_classification_desc")+"</sub_classification_desc>";					 
//						 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//						 xml=xml+"<status_desc>"+rss.getString("status_desc")+"</status_desc>";
//						 
//						 xml=xml+"<AG_FILE_NO>"+rss.getString("AG_FILE_NO")+"</AG_FILE_NO>";
//						 xml=xml+"<GIST>"+rss.getString("GIST")+"</GIST>";
//						 xml=xml+"<SLIP_DATE>"+rss.getString("SLIP_DATE")+"</SLIP_DATE>";
//						 xml=xml+"<RESPONSIBILITY_FIXED>"+rss.getString("RESPONSIBILITY_FIXED")+"</RESPONSIBILITY_FIXED>";
//						 xml=xml+"<MONETARY_LOSS_EXISTS>"+rss.getString("MONETARY_LOSS_EXISTS")+"</MONETARY_LOSS_EXISTS>";
//						 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//						 
//						 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//						 xml=xml+"<PRIORITY>"+rss.getString("PRIORITY")+"</PRIORITY>";
//						 xml=xml+"<PROCESS_FLOW_ID>"+rss.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
					 }
					 
					 xml=xml+"<count>"+cou+"</count>";
					 if(cou!=0)
					 {
						 xml=xml+"<flag>success</flag>";
					 }
					 else
					 {
						 xml=xml+"<flag>failure</flag>";
					 }
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					xml=xml+"<flag>failure</flag>";
					e.printStackTrace();
				}
				
			
			}
		
			else if(cmd.equalsIgnoreCase("retrive_valuess"))
		{
			xml=xml+"<command>retrive_valuess</command>";
			int cou=0;
			int slip_id=Integer.parseInt(request.getParameter("slip_id"));
			int emp_id=Integer.parseInt(request.getParameter("emp_id"));
			
			
			
			 System.out.println(type_id);
			 sql_query="SELECT a.AUDIT_SLIP_ID, " +
			 "  a.RESPONSIBLE_EMP_ID, " +
			 "  a.DESIGNATION_ID, " +
			 "  a.SLIP_STATUS_ID, " +
			 "  a.MONETARY_LOSS_AMT, " +
			 "  a.REMARKS, " +
			 "  a.PROCESS_FLOW_ID, " +
			 "  TO_CHAR(a.date_from,'dd/mm/yyyy') AS date_from, " +
			 "  TO_CHAR(a.date_to,'dd/mm/yyyy')   AS date_to, " +
			 "  b.employee_initial " +
			 "  || '.' " +
			 "  || b.employee_name AS empname, " +
			 "  c.designation " +
			 "FROM HRM_AUDIT_SLIP_EMP_LINK a " +
			 "LEFT OUTER JOIN hrm_mst_employees b " +
			 "ON a.responsible_emp_id=b.employee_id " +
			 "LEFT OUTER JOIN hrm_mst_designations c " +
			 "ON a.designation_id     =c.designation_id " +
			 "WHERE a.audit_slip_id   =? " +
			 "AND a.responsible_emp_id=?"


			 ;
			 
			 try {
				ps=con.prepareStatement(sql_query);
				ps.setInt(1, slip_id);
				ps.setInt(2, emp_id);
				 rss=ps.executeQuery();
				 while(rss.next())
				 {
					 cou++;
					 xml=xml+"<audit_slip_id>"+rss.getInt("AUDIT_SLIP_ID")+"</audit_slip_id>";
					 xml=xml+"<RESPONSIBLE_EMP_ID>"+rss.getInt("RESPONSIBLE_EMP_ID")+"</RESPONSIBLE_EMP_ID>";
					 xml=xml+"<DESIGNATION_ID>"+rss.getInt("DESIGNATION_ID")+"</DESIGNATION_ID>";
					 xml=xml+"<designation>"+rss.getString("designation")+"</designation>";
					 
					 xml=xml+"<date_from>"+rss.getString("date_from")+"</date_from>";
					 xml=xml+"<date_to>"+rss.getString("date_to")+"</date_to>";
					 
					 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
					 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
					 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
					 xml=xml+"<empname>"+rss.getString("empname")+"</empname>";
//					 xml=xml+"<CATEGORY_ID>"+rss.getString("CATEGORY_ID")+"</CATEGORY_ID>";
//					 xml=xml+"<category_desc>"+rss.getString("category_desc")+"</category_desc>";
//					 xml=xml+"<CLASSIFICATION_ID>"+rss.getString("CLASSIFICATION_ID")+"</CLASSIFICATION_ID>";
//					 xml=xml+"<classification_desc>"+rss.getString("classification_desc")+"</classification_desc>";
//					 xml=xml+"<SUB_CLASSIFICATION_ID>"+rss.getString("SUB_CLASSIFICATION_ID")+"</SUB_CLASSIFICATION_ID>";
//					 xml=xml+"<sub_classification_desc>"+rss.getString("sub_classification_desc")+"</sub_classification_desc>";					 
//					 xml=xml+"<SLIP_STATUS_ID>"+rss.getString("SLIP_STATUS_ID")+"</SLIP_STATUS_ID>";
//					 xml=xml+"<status_desc>"+rss.getString("status_desc")+"</status_desc>";
//					 
//					 xml=xml+"<AG_FILE_NO>"+rss.getString("AG_FILE_NO")+"</AG_FILE_NO>";
//					 xml=xml+"<GIST>"+rss.getString("GIST")+"</GIST>";
//					 xml=xml+"<SLIP_DATE>"+rss.getString("SLIP_DATE")+"</SLIP_DATE>";
//					 xml=xml+"<RESPONSIBILITY_FIXED>"+rss.getString("RESPONSIBILITY_FIXED")+"</RESPONSIBILITY_FIXED>";
//					 xml=xml+"<MONETARY_LOSS_EXISTS>"+rss.getString("MONETARY_LOSS_EXISTS")+"</MONETARY_LOSS_EXISTS>";
//					 xml=xml+"<MONETARY_LOSS_AMT>"+rss.getInt("MONETARY_LOSS_AMT")+"</MONETARY_LOSS_AMT>";
//					 
//					 xml=xml+"<REMARKS>"+rss.getString("REMARKS")+"</REMARKS>";
//					 xml=xml+"<PRIORITY>"+rss.getString("PRIORITY")+"</PRIORITY>";
//					 xml=xml+"<PROCESS_FLOW_ID>"+rss.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
				 }
				 
				 xml=xml+"<count>"+cou+"</count>";
				 if(cou!=0)
				 {
					 xml=xml+"<flag>success</flag>";
				 }
				 else
				 {
					 xml=xml+"<flag>failure</flag>";
				 }
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml=xml+"<flag>failure</flag>";
				e.printStackTrace();
			}
			
		}
		
		
		}
		catch(NumberFormatException e)
		{
			System.out.println("Number Format Exeption "+e);
			xml=xml+"<command>Exception</command>";
			xml=xml+"<number_format_exception>NFException</number_format_exception>";
		
		}
	  catch (Exception e)
	  {
		  System.out.println("Exception 0"+e);
	  }
	  xml=xml+"</response>";
		System.out.println("xml is === "+ xml);
		out.write(xml);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

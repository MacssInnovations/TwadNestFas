package Servlets.FAS.FAS1.EBBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EB_Bill_Journalisation
 */
public class Emp_Bill_Journalisation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Emp_Bill_Journalisation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
	   	 
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

		              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		               Class.forName(strDriver.trim());
		               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		    }
		    catch(Exception e)
		    {
		      System.out.println("Exception in connection...."+e);
		    }
			        ResultSet rs=null,rs1=null,rs2=null;
			        CallableStatement cs=null;
			        PreparedStatement ps=null,ps1=null,ps2=null;
			        String xml="";
			   	   
			        response.setContentType(CONTENT_TYPE);
			        PrintWriter out = response.getWriter();
			        response.setHeader("Cache-Control","no-cache");  
			        HttpSession session=request.getSession(false);
			        try
			        {
			                if(session==null)
			                {
			                        xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
			                        out.println(xml);
			                        System.out.println(xml);
			                        out.close();
			                        return;

			                    }
			                    //System.out.println(session);

			        }catch(Exception e)
			        {
			                //System.out.println("Redirect Error :"+e);
			        }
			        System.out.println("java");
			        String command;
			        command = request.getParameter("command");
			        
			        session =request.getSession(false);
			        String updatedby=(String)session.getAttribute("UserId");
			        long l=System.currentTimeMillis();
			        java.sql.Timestamp ts=new java.sql.Timestamp(l);
			            System.out.println("got");
			            System.out.println("command"+command);
			            if(command.equalsIgnoreCase("add")) {
			            	 xml="<response><command>Add</command>";	           
				            int acUnit = Integer.parseInt(request.getParameter("acc_unit").trim());
				            int officeId=Integer.parseInt(request.getParameter("office_id").trim());
				          
//				           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
//				           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
				          
				         int majorType=Integer.parseInt(request.getParameter("majortype").trim());
				         int MinorType=Integer.parseInt(request.getParameter("minortype").trim());
				         int journalType=Integer.parseInt(request.getParameter("journaltype").trim());
				       		           
				            String remarks=request.getParameter("remarks");
				           
				            try{
		               	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("insert into FAS_EMP_BILL_LJV(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,JOURNAL_TYPE_CODE,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?)");
				                ps.setInt(1,acUnit);
				                ps.setInt(2,officeId);
				               
//			                    ps.setInt(3,cashYear);
//			                    ps.setInt(4,cashMonth);
			                    ps.setInt(3,majorType);
			                    ps.setInt(4,MinorType);
			                    ps.setInt(5,journalType);
			                   			                   
			                    ps.setString(6,remarks);
			                    ps.setString(7,"A");
			                    ps.setString(8,updatedby);
				                ps.setTimestamp(9,ts);
				                ps.executeUpdate();
			                           
			                    
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }
			            
			            else if(command.equalsIgnoreCase("update")) {
			            	 xml="<response><command>Update</command>";	           
			            	  int acUnit = Integer.parseInt(request.getParameter("acc_unit").trim());
					            int officeId=Integer.parseInt(request.getParameter("office_id").trim());
					          
//					           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
//					           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
					          
					         int majorType=Integer.parseInt(request.getParameter("majortype").trim());
					         int MinorType=Integer.parseInt(request.getParameter("minortype").trim());
					         int journalType=Integer.parseInt(request.getParameter("journaltype").trim());
					       		           
					            String remarks=request.getParameter("remarks");
				           
				            try{
		              	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("update FAS_EMP_BILL_LJV set REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and JOURNAL_TYPE_CODE=?");
				               
			                    ps.setString(1,remarks);
			                    ps.setString(2,updatedby);
			                    ps.setTimestamp(3,ts);
			                    ps.setInt(4,acUnit);
				                ps.setInt(5,officeId);
				              
//			                    ps.setInt(6,cashYear);
//			                    ps.setInt(7,cashMonth);
			                    ps.setInt(6,majorType);
			                    ps.setInt(7,MinorType);
			                    ps.setInt(8,journalType);
			                 ps.executeUpdate();
			                           
			                    
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }    
			            else if(command.equalsIgnoreCase("Delete")) {
			            	 xml="<response><command>Delete</command>";	           
			            	 int acUnit = Integer.parseInt(request.getParameter("acc_unit").trim());
					            int officeId=Integer.parseInt(request.getParameter("office_id").trim());
					          
//					           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
//					           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
					          
					         int majorType=Integer.parseInt(request.getParameter("majortype").trim());
					         int MinorType=Integer.parseInt(request.getParameter("minortype").trim());
					         int journalType=Integer.parseInt(request.getParameter("journaltype").trim());
				           		           
				            try{
		             	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("delete from FAS_EMP_BILL_LJV where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and JOURNAL_TYPE_CODE=?");
				               
			                    ps.setInt(1,acUnit);
				                ps.setInt(2,officeId);
				               
//			                    ps.setInt(3,cashYear);
//			                    ps.setInt(4,cashMonth);
			                    ps.setInt(3,majorType);
			                    ps.setInt(4,MinorType);
			                    ps.setInt(5,journalType);
				                ps.executeUpdate();
			                           
			                    
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }    
			            else if(command.equalsIgnoreCase("get")) {
			            	 xml="<response><command>get</command>";	           
			            	 int majorType = Integer.parseInt(request.getParameter("majortype").trim());
					           
				           		           
				            try{
		             	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=?");
				               
			                    ps.setInt(1,majorType);
				              
				               rs= ps.executeQuery();
			                           
			                   while(rs.next()) 
			                   {
			                	   xml=xml+"<minorcode>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</minorcode>";
			                	   xml=xml+"<minordesc>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</minordesc>"; 
			                	   
			                   }
				               
				               
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }         
			            
			            else if(command.equalsIgnoreCase("getminor")) {
			            	 xml="<response><command>getminor</command>";	           
			            	 int majorType = Integer.parseInt(request.getParameter("majortype").trim());
					           
				           		           
				            try{
		             	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=?");
				               
			                    ps.setInt(1,majorType);
				              
				               rs= ps.executeQuery();
			                           
			                   while(rs.next()) 
			                   {
			                	   xml=xml+"<minorcode>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</minorcode>";
			                	   xml=xml+"<minordesc>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</minordesc>"; 
			                	   
			                   }
				               
				               
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }        
			            
			            
			            
			            out.println(xml);
				       	       
				        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

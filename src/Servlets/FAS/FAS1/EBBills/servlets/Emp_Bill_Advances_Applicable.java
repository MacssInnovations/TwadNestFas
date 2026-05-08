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
 * Servlet implementation class Emp_Bill_Advances_Applicable
 */
public class Emp_Bill_Advances_Applicable extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Emp_Bill_Advances_Applicable() {
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
			            	 
			            	 String finYear=request.getParameter("finyear");
				           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
				           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
				          
				         int majorType=Integer.parseInt(request.getParameter("majortype").trim());
				         int MinorType=Integer.parseInt(request.getParameter("minortype").trim());
				         String applicable=request.getParameter("applicable");
				       		           
				            String date=request.getParameter("date");
				          
				            System.out.println("Finyear"+finYear);
				            System.out.println("cashMonth"+cashMonth);
				            System.out.println("cashYear"+cashYear);
				            System.out.println("majorType"+majorType);
				            System.out.println("MinorType"+MinorType);
				            System.out.println("applicable"+applicable);
				            System.out.println("date"+date);
				            
				            try{
		               	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("insert into FAS_BILL_ADV_APPL_MST(FINANCIAL_YEAR,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,ADVANCE_APPLY,APPLICABLE_UPTO_DATE,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,to_date(?,'dd-mm-yy'),?,?)");
				                ps.setString(1,finYear);
				                ps.setInt(2,cashYear);
				             
			                    ps.setInt(3,cashMonth);
			                    ps.setInt(4,majorType);
			                    ps.setInt(5,MinorType);
			                   			                   			                   
			                    ps.setString(6,applicable);
			                    ps.setString(7,date);
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
			            	 String finYear=request.getParameter("finyear");
					           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
					           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
					          
					         int majorType=Integer.parseInt(request.getParameter("majortype").trim());
					         int MinorType=Integer.parseInt(request.getParameter("minortype").trim());
					         String applicable=request.getParameter("applicable");
					       		           
					            String date=request.getParameter("date");
					           
					            try{
			               	              		                  		                          
				                    xml=xml+"<flag>success</flag>";
				                    
				                   // to_date(?,'dd-mm-yyyy')
				                    ps=con.prepareStatement("update FAS_BILL_ADV_APPL_MST set ADVANCE_APPLY=?,APPLICABLE_UPTO_DATE=to_date(?,'dd-mm-yyyy'),UPDATED_BY_USERID=?,UPDATED_DATE=? where FINANCIAL_YEAR=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?");
					                
				                    ps.setString(1,applicable);
				                    ps.setString(2,date);
				                    ps.setString(3,updatedby);
					                ps.setTimestamp(4,ts);
				                    
				                    ps.setString(5,finYear);
					                ps.setInt(6,cashYear);
					             
				                    ps.setInt(7,cashMonth);
				                    ps.setInt(8,majorType);
				                    ps.setInt(9,MinorType);
				                   			                   			                   
				                   
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
			            	 String finYear=request.getParameter("finyear");
					           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
					           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
					          
					         int majorType=Integer.parseInt(request.getParameter("majortype").trim());
					         int MinorType=Integer.parseInt(request.getParameter("minortype").trim());
				           		           
				            try{
		             	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("delete from FAS_BILL_ADV_APPL_MST  where FINANCIAL_YEAR=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?");
				               
			                    ps.setString(1,finYear);
				                ps.setInt(2,cashYear);
				             
			                    ps.setInt(3,cashMonth);
			                    ps.setInt(4,majorType);
			                    ps.setInt(5,MinorType);
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

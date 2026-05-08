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
 * Servlet implementation class EB_Energy_Quota_Details
 */
public class EB_Energy_Quota_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EB_Energy_Quota_Details() {
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
				            String serviceNo=request.getParameter("serviceno");
				            String billNo=request.getParameter("billno");
				           String billDate=request.getParameter("billdate");
                            String billMonthYear[]=billDate.split("/");
				            
				            int billMonth=Integer.parseInt(billMonthYear[1]);
				            int billYear=Integer.parseInt(billMonthYear[2]);
				            
				           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
				           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
				           
				          String  unitsTime=request.getParameter("unitstime").trim(); 
				            
				          
				         float unitsQuotaFixed=Float.parseFloat(request.getParameter("unitsquotafixed").trim());
				         
				        
				         float unitsQuotaExcess=Float.parseFloat(request.getParameter("unitsquotaexcess").trim());
				         float epdUnitsFixed=Float.parseFloat(request.getParameter("epdunitsfixed").trim());
				         float epdUnitsExcess=Float.parseFloat(request.getParameter("epdunitsexcess").trim());
				         
				            String KVATime=request.getParameter("kvatime");
				           
				            String remarks=request.getParameter("remarks");
				           
				            try{
		               	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("insert into FAS_EB_ENERGY_QUOTA_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SERVICE_NO,BILL_NO,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_DATE,BILL_FOR_MONTH,BILL_FOR_YEAR,EPK_UNITS_TIME,EPK_UNITS_QUOTA_FIXED,EPK_UNITS_QUOTA_EXCESS,EPD_KVA_TIME,EPD_UNITS_QUOTA_FIXED,EPD_UNITS_QUOTA_EXCESS,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?)");
				                ps.setInt(1,acUnit);
				                ps.setInt(2,officeId);
				                ps.setString(3,serviceNo);
				                ps.setString(4,billNo);
			                    ps.setInt(5,cashYear);
			                    ps.setInt(6,cashMonth);
			                    ps.setString(7,billDate);
			                    ps.setInt(8,billMonth);
			                    ps.setInt(9,billYear);
			                    ps.setString(10,unitsTime);
			                    ps.setFloat(11,unitsQuotaFixed);
			                    ps.setFloat(12,unitsQuotaExcess);
			                    ps.setString(13,KVATime);
			                    ps.setFloat(14,epdUnitsFixed);
			                    ps.setFloat(15,epdUnitsExcess);
			                    ps.setString(16,remarks);
			                    ps.setString(17,updatedby);
				                ps.setTimestamp(18,ts);
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
					            String serviceNo=request.getParameter("serviceno");
					            String billNo=request.getParameter("billno");
					            String billDate=request.getParameter("billdate");
	                            String billMonthYear[]=billDate.split("/");
					            
					            int billMonth=Integer.parseInt(billMonthYear[1]);
					            int billYear=Integer.parseInt(billMonthYear[2]);
					           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
					           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
					           
					          String  unitsTime=request.getParameter("unitstime").trim(); 
					            
					          
					         float unitsQuotaFixed=Float.parseFloat(request.getParameter("unitsquotafixed").trim());
					         
					        
					         float unitsQuotaExcess=Float.parseFloat(request.getParameter("unitsquotaexcess").trim());
					         float epdUnitsFixed=Float.parseFloat(request.getParameter("epdunitsfixed").trim());
					         float epdUnitsExcess=Float.parseFloat(request.getParameter("epdunitsexcess").trim());
					         
					            String KVATime=request.getParameter("kvatime");
					           
					            String remarks=request.getParameter("remarks");
				           
				            try{
		              	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("update FAS_EB_ENERGY_QUOTA_MST set BILL_DATE=?,BILL_FOR_MONTH=?,BILL_FOR_YEAR=?,EPK_UNITS_TIME=?,EPK_UNITS_QUOTA_FIXED=?,EPK_UNITS_QUOTA_EXCESS=?,EPD_KVA_TIME=?,EPD_UNITS_QUOTA_FIXED=?,EPD_UNITS_QUOTA_EXCESS=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SERVICE_NO=? and BILL_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				               
			                   
			                    ps.setString(1,billDate);
			                    ps.setInt(2,billMonth);
			                    ps.setInt(3,billYear);
			                    ps.setString(4,unitsTime);
			                    ps.setFloat(5,unitsQuotaFixed);
			                    ps.setFloat(6,unitsQuotaExcess);
			                    ps.setString(7,KVATime);
			                    ps.setFloat(8,epdUnitsFixed);
			                    ps.setFloat(9,epdUnitsExcess);
			                    ps.setString(10,remarks);
			                    ps.setString(11,updatedby);
				                ps.setTimestamp(12,ts);
				                
				                ps.setInt(13,acUnit);
				                ps.setInt(14,officeId);
				                ps.setString(15,serviceNo);
				                ps.setString(16,billNo);
			                    ps.setInt(17,cashYear);
			                    ps.setInt(18,cashMonth);
				                
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
					            String serviceNo=request.getParameter("serviceno");
					            String billNo=request.getParameter("billno");
					           
					           int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
					           int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
				           		           
				            try{
		             	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("delete from FAS_EB_ENERGY_QUOTA_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SERVICE_NO=? and BILL_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				               
				               
			                    ps.setInt(1,acUnit);
				                ps.setInt(2,officeId);
				                ps.setString(3,serviceNo);
				                ps.setString(4,billNo);
			                    ps.setInt(5,cashYear);
			                    ps.setInt(6,cashMonth);
				                ps.executeUpdate();
			                           
			                    
				                           
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

package Servlets.FAS.FAS1.EBBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EB_Consumption_Worksheet
 */
public class EB_Consumption_Worksheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EB_Consumption_Worksheet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
	   	  System.out.println("welcome to EB Bill Consumption Work Sheet details");
		        try{
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
		        catch(Exception e){
		        	System.out.println("Exception in opening connection :"+e); 
		        }
		        ResultSet rs=null,rs1=null,rs2=null;
		        CallableStatement cs=null;
		        PreparedStatement ps=null,ps1=null,ps2=null;
		        String xml="";
		       
		        
		     //   int ac_month=Integer.parseInt(request.getParameter("acmonth").trim());  
	        //   int ac_year=Integer.parseInt(request.getParameter("acyear").trim());  
	        //   int unit=Integer.parseInt(request.getParameter("unit").trim());
	           
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
		        
		        String command;
		        command = request.getParameter("command");
		        
		        session =request.getSession(false);
		        String updatedby=(String)session.getAttribute("UserId");
		        long l=System.currentTimeMillis();
		        java.sql.Timestamp ts=new java.sql.Timestamp(l);
		            
		           
		             if(command.equalsIgnoreCase("add")) {
		            	 System.out.println("ADD");	 
		            	  int acUnit = Integer.parseInt(request.getParameter("unit").trim());
				            int officeId=Integer.parseInt(request.getParameter("officeid").trim());
				            String serviceNo=request.getParameter("serviceno");
				            String billNo=request.getParameter("billno");
				            String billDate=request.getParameter("billdate"); 
				            String billMonthYear[]=billDate.split("/");
				            
				            int billMonth=Integer.parseInt(billMonthYear[1]);
				            int billYear=Integer.parseInt(billMonthYear[2]);
				        
				            int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
				            int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
				           
				            int circleCode=Integer.parseInt(request.getParameter("circlecode").trim());
				            
				           String powerCut=request.getParameter("powercut");
				           
				          
				            float netNormal=Float.parseFloat(request.getParameter("netnormal").trim());
				            float netPeak=Float.parseFloat(request.getParameter("netpeak").trim());
				            float netOffPeak=Float.parseFloat(request.getParameter("netoffpeak").trim());
				            float netTotal=Float.parseFloat(request.getParameter("nettotal").trim());
				          				            
				            float KVAH=Float.parseFloat(request.getParameter("kvah").trim());
				            float RKAH=Float.parseFloat(request.getParameter("rkah").trim());
				           float powerFactor=Float.parseFloat(request.getParameter("powerfactor").trim()); 
				            String remark=request.getParameter("remark"); 
				            
				            
			            xml="<response><command>add</command>";
			            try{
			            	 xml=xml+"<flag>success</flag>";
			           	    
			            	 ps=con.prepareStatement("insert into FAS_EB_BILL_CONS_WS_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SERVICE_NO,BILL_NO,BILL_DATE,CIRCLE_CODE,POWER_CUT,NORMAL_NET_CONS,PEAK_NET_CONS,OFFPEAK_NET_CONS,TOTAL_NET_CONS,KVAH,RKAH,POWER_FACTOR,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,BILL_YEAR,BILL_MONTH) values(?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				                ps.setInt(1,acUnit);
				                ps.setInt(2,officeId);
				                ps.setInt(3,cashYear);
				                ps.setInt(4,cashMonth);
				                ps.setString(5,serviceNo);
				                ps.setString(6,billNo);
				                ps.setString(7,billDate);
				                ps.setInt(8,circleCode);  
				                ps.setString(9,powerCut);  
				                ps.setFloat(10,netNormal);
				                ps.setFloat(11,netPeak);
				                ps.setFloat(12,netOffPeak);  
				                ps.setFloat(13,netTotal);  
				                ps.setFloat(14,KVAH);  
				                ps.setFloat(15,RKAH);
				                ps.setFloat(16,powerFactor);
				                 ps.setString(17,remark);
				                ps.setString(18,updatedby);
				                ps.setTimestamp(19,ts);
				                ps.setInt(20,billYear);  
				                ps.setInt(21,billMonth);  
				                ps.executeUpdate();
			            
			            } catch(Exception e) {	
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		        }else if(command.equalsIgnoreCase("Addtrn")) {
		        	 System.out.println("TRN");	 
		        	 
		        	  int acUnit = Integer.parseInt(request.getParameter("unit").trim());
			            int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            String serviceNo=request.getParameter("serviceno");
			            String billNo=request.getParameter("billno");
			            int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            String addOrLess=request.getParameter("addorless");
			            if(addOrLess.equalsIgnoreCase("Add"))
			            {
			            	addOrLess="A";	
			            }else if(addOrLess.equalsIgnoreCase("Less"))
			            {
			            	addOrLess="L";		
			            }
			            
			            String remark=request.getParameter("consdesc");
			           
			            float normal=Float.parseFloat(request.getParameter("normal").trim());
			            float peak=Float.parseFloat(request.getParameter("peak").trim());
			            float offPeak=Float.parseFloat(request.getParameter("offpeak").trim());
			            float netConsTot=Float.parseFloat(request.getParameter("netconstot").trim());
			           
			            int serialNo=0;
		        			            
			            
		            try{
		           
		            	
		            	ps=con.prepareStatement("select count(*) as cnt from FAS_EB_BILL_CONS_WS_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SERVICE_NO=? and BILL_NO=?");  
		            	ps.setInt(1,acUnit);
		            	ps.setInt(2,officeId);
		            	ps.setString(3,serviceNo);
		            	ps.setString(4,billNo);
		            	
		            	rs=ps.executeQuery(); 
		            	if(rs.next())
		            	{
		            	serialNo=rs.getInt("cnt");
		            	serialNo++;
		            	}
		            	else{
		            		serialNo++;
		            	}
		            			            	
		            	 ps=con.prepareStatement("insert into FAS_EB_BILL_CONS_WS_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SERVICE_NO,BILL_NO,SL_NO,CONS_TYPE,CONS_DESC,NORMAL_CONS,PEAK_CONS,OFFPEAK_CONS,NET_CONS,CASHBOOK_YEAR,CASHBOOK_MONTH,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		            	 ps.setInt(1,acUnit);
			            ps.setInt(2,officeId);
			            ps.setString(3,serviceNo);
			            ps.setString(4,billNo);
			            ps.setInt(5,serialNo);
			            ps.setString(6,addOrLess);
			            ps.setString(7,remark);
			            ps.setFloat(8,normal);
			            ps.setFloat(9,peak);
			            ps.setFloat(10,offPeak);
			            ps.setFloat(11,netConsTot);
			            ps.setInt(12,cashYear);
			            ps.setInt(13,cashMonth);
			            ps.setString(14,updatedby);
			            ps.setTimestamp(15,ts);
			            ps.executeUpdate();   	
          
		            } catch(Exception e) {
	              //  xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	           // xml=xml+"</response>";
	           // System.out.println(xml);
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

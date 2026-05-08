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
 * Servlet implementation class EB_Bill_Consumption_Details
 */
public class EB_Bill_Consumption_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EB_Bill_Consumption_Details() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
	   	  System.out.println("welcome to EB Bill Consumption  details");
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
		        DecimalFormat df=new DecimalFormat("#0.00");
		        
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
				            String dueDate=request.getParameter("duedate"); 
				            String billReceived=request.getParameter("billreceived"); 
				          
				            float tranLoss=Float.parseFloat(request.getParameter("tranloss").trim());
				            float transCapacity=Float.parseFloat(request.getParameter("transcapacity").trim());
				            float energyCharges=Float.parseFloat(request.getParameter("energycharges").trim());
				            float demandTariff=Float.parseFloat(request.getParameter("demandtariff").trim());
				           System.out.println("Demand unit"+request.getParameter("demandunit").trim());
				            
				            float demandUnit=Float.parseFloat(request.getParameter("demandunit").trim());
				            float totalDemand=Float.parseFloat(request.getParameter("totaldemand").trim());
				            float addOther=Float.parseFloat(request.getParameter("addother").trim());
				            float lessOther=Float.parseFloat(request.getParameter("lessother").trim());
				            float netValue=Float.parseFloat(request.getParameter("netvalue").trim());
				            String remark=request.getParameter("remark"); 
				            
				            
			            xml="<response><command>add</command>";
			            try{
			            	 xml=xml+"<flag>success</flag>";
			           	    
			            	 ps=con.prepareStatement("insert into FAS_EB_BILL_CONS_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SERVICE_NO,BILL_NO,BILL_DATE,BILL_YEAR,BILL_MONTH,DUE_DATE,BILL_RECEIPT_DATE,TRANSFORMER_LOSS,TRANSFORMER_CAPACITY,TOTAL_ENERGY_CHARGES,DEMAND_TARIFF,DEMAND_UNITS,TOTAL_DEMAND_CHARGES,TOTAL_ADD_OTHERS,TOTAL_LESS_OTHERS,NET_VALUE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,to_date(?,'dd-mm-yyyy'),to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?)");
				                ps.setInt(1,acUnit);
				                ps.setInt(2,officeId);
				                ps.setInt(3,cashYear);
				                ps.setInt(4,cashMonth);
				                ps.setString(5,serviceNo);
				                ps.setString(6,billNo);
				                ps.setString(7,billDate);
				                ps.setInt(8,billYear);  
				                ps.setInt(9,billMonth);  
				                ps.setString(10,dueDate);
				                ps.setString(11,billReceived);
				                ps.setFloat(12,tranLoss);  
				                ps.setFloat(13,transCapacity);  
				                ps.setFloat(14,energyCharges);  
				                ps.setFloat(15,demandTariff);
				                ps.setFloat(16,demandUnit);
				                ps.setFloat(17,totalDemand);
				                ps.setFloat(18,addOther);
				                ps.setFloat(19,lessOther);
				                ps.setFloat(20,netValue);
				                ps.setString(21,remark);
				                ps.setString(22,updatedby);
				                ps.setTimestamp(23,ts);

				                ps.executeUpdate();
			            
			            } catch(Exception e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		        }else if(command.equalsIgnoreCase("Addtrnenergy")) {
		        	 System.out.println("TRN");	 
		        	 
		        	  int acUnit = Integer.parseInt(request.getParameter("unit").trim());
			            int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            String serviceNo=request.getParameter("serviceno");
			            String billNo=request.getParameter("billno");
			            int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            String energyType=request.getParameter("energytype");
			            String remark=request.getParameter("description");
			           
			            float energyTariff=Float.parseFloat(request.getParameter("energytariff").trim());
			            float energyUnit=Float.parseFloat(request.getParameter("energyunit").trim());
			            float energyCharge=Float.parseFloat(request.getParameter("energycharge").trim());
			            float rebate=Float.parseFloat(request.getParameter("rebate").trim());
			            float afterRebate=Float.parseFloat(request.getParameter("afterrebate").trim());
			            int serialNo=0;
		        			            
			            
		            try{
		           
		            	
		            	ps=con.prepareStatement("select count(*) as cnt from FAS_EB_BILL_CONS_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SERVICE_NO=? and BILL_NO=?");  
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
		            			            	
		            	 ps=con.prepareStatement("insert into FAS_EB_BILL_CONS_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SERVICE_NO,BILL_NO,SL_NO,ENERGY_TYPE,ENERGY_TARIFF,ENERGY_UNIT,ENERGY_CHARGE,REBATE,ENERGY_CHG_AFTER_REBATE,REMARKS,CASHBOOK_YEAR,CASHBOOK_MONTH,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		            	 ps.setInt(1,acUnit);
			            	ps.setInt(2,officeId);
			            	ps.setString(3,serviceNo);
			            	ps.setString(4,billNo);
			             ps.setInt(5,serialNo);
			             ps.setString(6,energyType);
			             ps.setFloat(7,energyTariff);
			             ps.setFloat(8,energyUnit);
			             ps.setFloat(9,energyCharge);
			             ps.setFloat(10,rebate);
			             ps.setFloat(11,afterRebate);
			             ps.setString(12,remark);
			             ps.setInt(13,cashYear);
			            ps.setInt(14,cashMonth);
			           			            		             
			             ps.setString(15,updatedby);
			             ps.setTimestamp(16,ts);
			             ps.executeUpdate();   	
		            	
		            	
		            	
	           
		            } catch(Exception e) {
	              //  xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	           // xml=xml+"</response>";
	           // System.out.println(xml);
	        }
		        else if(command.equalsIgnoreCase("Addtrnother")) {
		        	 System.out.println("TRN-2");	 
		        	 
		        	  int acUnit = Integer.parseInt(request.getParameter("unit").trim());
			            int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            String serviceNo=request.getParameter("serviceno");
			            String billNo=request.getParameter("billno");
			            int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            String addOrLess=request.getParameter("addorless");
			            String remark=request.getParameter("description");
			            float amount=Float.parseFloat(request.getParameter("amount").trim());
			           
			            int serialNo=0;
		        
			            if(addOrLess.equalsIgnoreCase("Add"))
			            {
			            	addOrLess="A";	
			            }else if(addOrLess.equalsIgnoreCase("Less"))
			            {
			            	addOrLess="L";	
			            }
			            
		            try{
		           
		            	
		            	ps=con.prepareStatement("select count(*) as cnt from FAS_EB_BILL_CONS_TRN2 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SERVICE_NO=? and BILL_NO=?");  
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
		            			            	
		            	 ps=con.prepareStatement("insert into FAS_EB_BILL_CONS_TRN2(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SERVICE_NO,BILL_NO,SL_NO,ADD_OR_LESS,ADD_OR_LESS_DESC,CASHBOOK_YEAR,CASHBOOK_MONTH,AMOUNT,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?)");
		            	 ps.setInt(1,acUnit);
			            	ps.setInt(2,officeId);
			            	ps.setString(3,serviceNo);
			            	ps.setString(4,billNo);
			             ps.setInt(5,serialNo);
			             ps.setString(6,addOrLess);
			             ps.setString(7,remark);
			             ps.setInt(8,cashYear);
			            ps.setInt(9,cashMonth);
			            ps.setFloat(10,amount);
			            		             
			             ps.setString(11,updatedby);
			             ps.setTimestamp(12,ts);
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

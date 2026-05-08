package Servlets.FAS.FAS1.EBBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EB_Bill_WS_Annexure
 */
public class EB_Bill_WS_Annexure extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EB_Bill_WS_Annexure() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
	  	  System.out.println("welcome to EB Bill Work sheet Annexure Details");
	  	  
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
		        String xml="",sql="";
		        DecimalFormat df=new DecimalFormat("#0.00");
		      
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
		                    //    System.out.println(xml);
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
		            if(command.equalsIgnoreCase("getbill")) {
			                   
			          
			            xml="<response><command>getbill</command>";
			            try{
			                   
			            	int unitId=Integer.parseInt(request.getParameter("unitid").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            	String serviceNo=request.getParameter("serviceno");
			            	
			                    ps=con.prepareStatement("SELECT BILL_NO  FROM FAS_EB_BILL_CONS_MST WHERE ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SERVICE_NO=?");
			                    ps.setInt(1,unitId);
			                    ps.setInt(2,officeId);
			                    ps.setInt(3,cashYear);
			                    ps.setInt(4,cashMonth);
			                    ps.setString(5,serviceNo);
			                    rs=ps.executeQuery();
			                    
			                    if(rs.next()){
			                    xml=xml+"<flag>success</flag>";
			                    }else
			                    {
			                    	xml=xml+"<flag>success123</flag>";
			                    }
			                    rs=ps.executeQuery();
			                    while(rs.next()){
			                    	
			                    xml=xml+"<billno>"+rs.getString("BILL_NO")+"</billno>";
			                   		                  
				                    
		                        }
	                    
			                    
			                    ps=con.prepareStatement("SELECT METER_SL_NO  FROM FAS_EB_METER_MASTER WHERE ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SERVICE_NO=?");
			                    ps.setInt(1,unitId);
			                    ps.setInt(2,officeId);
			                   
			                    ps.setString(3,serviceNo);
			                    rs=ps.executeQuery();
			                  
			                    while(rs.next()){
			                    	
			                    xml=xml+"<meterno>"+rs.getString("METER_SL_NO")+"</meterno>";
			                   		                  
				                    
		                        }
			                    
			                    
			                    
			                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		         
		            }  if(command.equalsIgnoreCase("getbilldate")) {
			                   
			          
			            xml="<response><command>getbilldate</command>";
			            try{
			                   
			            	int unitId=Integer.parseInt(request.getParameter("unitid").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            	String serviceNo=request.getParameter("serviceno");
			            	String billNo=request.getParameter("billno");
			            	
			                    ps=con.prepareStatement("SELECT to_char(BILL_DATE) as BILL_DATE,TOTAL_ENERGY_CHARGES,TOTAL_DEMAND_CHARGES  FROM FAS_EB_BILL_CONS_MST WHERE ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SERVICE_NO=? and BILL_NO=?");
			                    ps.setInt(1,unitId);
			                    ps.setInt(2,officeId);
			                    ps.setInt(3,cashYear);
			                    ps.setInt(4,cashMonth);
			                    ps.setString(5,serviceNo);
			                    ps.setString(6,billNo);
			                    rs=ps.executeQuery();
			                    
			                    xml=xml+"<flag>success</flag>";
			                    
			                    while(rs.next()){
			                    
			                    xml=xml+"<billdate>"+rs.getString("BILL_DATE")+"</billdate>";
			                    xml=xml+"<energycharges>"+df.format(rs.getFloat("TOTAL_ENERGY_CHARGES"))+"</energycharges>";	                  
			                    xml=xml+"<demandcharges>"+df.format(rs.getFloat("TOTAL_DEMAND_CHARGES"))+"</demandcharges>";   
			                    
		                        }
	                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		         
		            } if(command.equalsIgnoreCase("Addmst")) {
			                   
			          
			            xml="<response><command>Addmst</command>";
			            try{
			                   
			            	int unitId=Integer.parseInt(request.getParameter("unit").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            	String serviceNo=request.getParameter("serviceno");
			            	String billNo=request.getParameter("billno");
			            	String billDate=request.getParameter("billdate");
			            	String meterNo=request.getParameter("meterno");
			            	String readingDate=request.getParameter("readingdate");
			            	String readTypeCode=request.getParameter("readtypecode");
			            	String meterType=request.getParameter("metertype");
		            	      	float finalReading=Float.parseFloat(request.getParameter("finalreading").trim());
			            	float initialReading=Float.parseFloat(request.getParameter("initialreading").trim());
			            	float differenceReading=Float.parseFloat(request.getParameter("differencereading").trim());
			            	float MF=Float.parseFloat(request.getParameter("mf").trim());
			            	float consumption=Float.parseFloat(request.getParameter("comsumption").trim());
			            	float compCon=Float.parseFloat(request.getParameter("comp_con").trim());
			            	float otherCon=Float.parseFloat(request.getParameter("other_cons").trim());
			            	float avgCons=Float.parseFloat(request.getParameter("avg_cons").trim());
			            	
			            	String remarks=request.getParameter("remark");
			            	String circleName=request.getParameter("circlename");
			            	
			                    ps=con.prepareStatement("insert into FAS_EB_BILL_WS_ANNEX_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SERVICE_NO,BILL_NO,BILL_DATE,CIRCLE_NAME,METER_NO,READING_DATE,READ_TYPE_CODE,METER_UDM_TYPE,FINAL_READING,INITIAL_READING,DIFFERENCE_IN_READING,MF," +
			                    		"CONSUMPTION,COMP_CONS,OTHER_CONS,AVG_CONS,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH) values(?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			                    ps.setInt(1,unitId);
			                    ps.setInt(2,officeId);
			                    ps.setString(3,serviceNo);
			                    ps.setString(4,billNo);
			                    ps.setString(5,billDate);
			                    ps.setString(6,circleName);
			                    ps.setString(7,meterNo);
			                    ps.setString(8,readingDate);
			                    ps.setString(9,readTypeCode);
			                    ps.setString(10,meterType);
			                    ps.setFloat(11,finalReading);
			                    ps.setFloat(12,initialReading);
			                    ps.setFloat(13,differenceReading);
			                    ps.setFloat(14,MF);
			                    ps.setFloat(15,consumption);
			                    ps.setFloat(16,compCon);
			                    ps.setFloat(17,otherCon);
			                    ps.setFloat(18,avgCons);
			                    ps.setString(19,remarks);
			                    ps.setString(20,updatedby);
			                    ps.setTimestamp(21,ts);
			                    ps.setInt(22,cashYear);
			                    ps.setInt(23,cashMonth);
			                    
			                    
			                    
			                    rs=ps.executeQuery();
			                    
			                    xml=xml+"<flag>success</flag>";
			                  
	                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		         
		            } if(command.equalsIgnoreCase("Addtrn2")) {
			                   
			          
			            xml="<response><command>Addtrn2</command>";
			            try{
			                   
			            	int unitId=Integer.parseInt(request.getParameter("unit").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            	String serviceNo=request.getParameter("serviceno");
			            	String billNo=request.getParameter("billno");
			            	
		            	    float pFIncentive=Float.parseFloat(request.getParameter("pfincentive").trim());
			            	float adjAffected=Float.parseFloat(request.getParameter("adjaffected").trim());
			            	float adjNotAffected=Float.parseFloat(request.getParameter("adjnotaffected").trim());
			            				            	
			            	String adjCode=request.getParameter("adjcode");
			            
			            	int sNo=0;
			            	 ps=con.prepareStatement("SELECT count(*) as cnt  FROM FAS_EB_BILL_WS_ANNEX_TRN2 WHERE ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SERVICE_NO=? and BILL_NO=?");
			                    ps.setInt(1,unitId);
			                    ps.setInt(2,officeId);
			                    ps.setInt(3,cashYear);
			                    ps.setInt(4,cashMonth);
			                    ps.setString(5,serviceNo);
			                    ps.setString(6,billNo);
			                    rs=ps.executeQuery();
			                    
			            	if(rs.next())
			            	{
			            		sNo=rs.getInt("cnt");
			            	}else{
			            		sNo=0;
			            	}
			            	
			            	sNo++;
			       
			                    ps=con.prepareStatement("insert into FAS_EB_BILL_WS_ANNEX_TRN2(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SERVICE_NO,BILL_NO,SL_NO,PF_INCENTIVE,ADJ_CODE,ADJ_AMOUNT_AFFECTED,ADJ_AMOUNT_NOT_AFFECTED,UPDATED_BY_USERID,UPDATED_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			                    ps.setInt(1,unitId);
			                    ps.setInt(2,officeId);
			                    ps.setString(3,serviceNo);
			                    ps.setString(4,billNo);
			                    ps.setInt(5,sNo);
			                    ps.setFloat(6,pFIncentive);
			                    ps.setString(7,adjCode);
			                    
			                    ps.setFloat(8,adjAffected);
			                    ps.setFloat(9,adjNotAffected);
			                    
			                    ps.setString(10,updatedby);
			                    ps.setTimestamp(11,ts);
			                    ps.setInt(12,cashYear);
			                    ps.setInt(13,cashMonth);
			                    
			                    
			                    
			                    rs=ps.executeQuery();
			                    
			                    xml=xml+"<flag>success</flag>";
			                  
	                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		         
		            } if(command.equalsIgnoreCase("Addtrn1")) {
			                   
			          
			            xml="<response><command>Addtrn1</command>";
			            try{
			                   
			            	int unitId=Integer.parseInt(request.getParameter("unit").trim());
			            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
			            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
			            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
			            	String serviceNo=request.getParameter("serviceno");
			            	String billNo=request.getParameter("billno");
			            	
		            	    float realizedEnergyCharge=Float.parseFloat(request.getParameter("realizedenergycharge").trim());
			            	float recordedDemandCharge=Float.parseFloat(request.getParameter("regdemandcharge").trim());
			            	float totalAdj=Float.parseFloat(request.getParameter("totaladjustment").trim());
			            	float taxableAmount=Float.parseFloat(request.getParameter("taxableamount").trim());
			            	float taxAmount=Float.parseFloat(request.getParameter("taxamount").trim());
			            	float oldTaxAmount=Float.parseFloat(request.getParameter("oldtaxamount").trim());
			            	float totalTaxAmount=Float.parseFloat(request.getParameter("totaltaxamount").trim());
			            	
			            	
			                    ps=con.prepareStatement("insert into FAS_EB_BILL_WS_ANNEX_TRN1(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SERVICE_NO,BILL_NO,REALISED_ENERGY_CHARGES,RECORDED_DEMAND_CHARGES,TOTAL_ADJUSTMENTS,TAXABLE_AMOUNT,TAX_AMOUNT," +
			                    		"OLD_TAX_AMOUNT,TOTAL_TAX_AMOUNT,UPDATED_BY_USERID,UPDATED_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			                    ps.setInt(1,unitId);
			                    ps.setInt(2,officeId);
			                    ps.setString(3,serviceNo);
			                    ps.setString(4,billNo);
			                   
			                    ps.setFloat(5,realizedEnergyCharge);
			                    ps.setFloat(6,recordedDemandCharge);
			                    ps.setFloat(7,totalAdj);
			                    ps.setFloat(8,taxableAmount);
			                    ps.setFloat(9,taxAmount);
			                    ps.setFloat(10,oldTaxAmount);
			                    ps.setFloat(11,totalTaxAmount);
			                   
			                    ps.setString(12,updatedby);
			                    ps.setTimestamp(13,ts);
			                    ps.setInt(14,cashYear);
			                    ps.setInt(15,cashMonth);
			                    
			                    
			                    
			                    rs=ps.executeQuery();
			                    
			                    xml=xml+"<flag>success</flag>";
			                  
	                    
		                    
		                    }
		           
		            catch(SQLException e) {
		                xml=xml+"<flag>failure</flag>";
		                e.printStackTrace();
		            }
		            xml=xml+"</response>";
		           // System.out.println(xml);
		            //out.print(xml);
		         
		            } 
		            
		            System.out.println(xml);
		            out.print(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

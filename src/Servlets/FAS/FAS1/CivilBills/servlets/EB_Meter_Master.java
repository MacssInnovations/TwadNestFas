package Servlets.FAS.FAS1.CivilBills.servlets;

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
 * Servlet implementation class EB_Meter_Master
 */
public class EB_Meter_Master extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	    private static final String DOC_TYPE = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EB_Meter_Master() {
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
		            String tariff=request.getParameter("tariff");
		            int permittedMd=0;
		           try{
		            if(request.getParameter("permittedmd")!=null ||!(request.getParameter("permittedmd").equalsIgnoreCase(""))){
		             permittedMd=Integer.parseInt(request.getParameter("permittedmd").trim());
		            }
		           }catch(Exception e){System.out.println(e);}
		           
		          String  meterNo=request.getParameter("meterno").trim(); 
		            
		            String supplyDate=request.getParameter("supplydate");
		            System.out.println("supplyDate::::;"+supplyDate);
		            String effectiveFrom=request.getParameter("effectivefrom");
		            String effectiveUpto=request.getParameter("effectiveupto");
		            String ConnectionType=request.getParameter("connectiontype");
		            String remarks=request.getParameter("remarks");
		            String htlt=request.getParameter("htlt");
		            try{
               	              		                  		                          
	                    xml=xml+"<flag>success</flag>";
	                    
	                   // to_date(?,'dd-mm-yyyy')
	                    ps=con.prepareStatement("insert into FAS_EB_METER_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SERVICE_NO,TARIFF_APP,PERMITTED_MD_IN_KVA,METER_SL_NO,DATE_OF_SUPPLY,HT_OR_LT,TYPE_OF_CONNECTION,DATE_EFFECTIVE_FROM,DATE_EFFECT_UPTO,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,to_date(?,'dd/mm/yyyy'),to_date(?,'dd/mm/yyyy'),?,?,?,?)");
		                ps.setInt(1,acUnit);
		                ps.setInt(2,officeId);
		                ps.setString(3,serviceNo);
		                ps.setString(4,tariff);
	                    ps.setInt(5,permittedMd);
	                    ps.setString(6,meterNo);
	                    ps.setString(7,supplyDate);
	                    ps.setString(8,htlt);
	                    ps.setString(9,ConnectionType);
	                    ps.setString(10,effectiveFrom);
	                    ps.setString(11,effectiveUpto);
	                    ps.setString(12,remarks);
	                    ps.setString(13,"L");
		                ps.setString(14,updatedby);
		                ps.setTimestamp(15,ts);
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
		            String tariff=request.getParameter("tariff");
		            int permittedMd=0;
			           
		            try{
			            if(request.getParameter("permittedmd")!=null ||!(request.getParameter("permittedmd").equalsIgnoreCase(""))){
			             permittedMd=Integer.parseInt(request.getParameter("permittedmd").trim());
			            }
			           }catch(Exception e){System.out.println(e);}
		            
		           
		          String  meterNo=request.getParameter("meterno").trim(); 
		            String supplyDate=request.getParameter("supplydate");
		            String effectiveFrom=request.getParameter("effectivefrom");
		            String effectiveUpto=request.getParameter("effectiveupto");
		            String ConnectionType=request.getParameter("connectiontype");
		            String remarks=request.getParameter("remarks");
		            String htlt=request.getParameter("htlt");
		           
		            try{
              	              		                  		                          
	                    xml=xml+"<flag>success</flag>";
	                    
	                   // to_date(?,'dd-mm-yyyy')
	                    ps=con.prepareStatement("update FAS_EB_METER_MASTER set TARIFF_APP=?,PERMITTED_MD_IN_KVA=?,METER_SL_NO=?,DATE_OF_SUPPLY=to_date(?,'dd-mm-yyyy'),HT_OR_LT=?,TYPE_OF_CONNECTION=?,DATE_EFFECTIVE_FROM=to_date(?,'dd-mm-yyyy'),DATE_EFFECT_UPTO=to_date(?,'dd-mm-yyyy'),REMARKS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SERVICE_NO=?");
		               
		                ps.setString(1,tariff);
	                    ps.setInt(2,permittedMd);
	                    ps.setString(3,meterNo);
	                    ps.setString(4,supplyDate);
	                    ps.setString(5,htlt);
	                    ps.setString(6,ConnectionType);
	                    ps.setString(7,effectiveFrom);
	                    ps.setString(8,effectiveUpto);
	                    ps.setString(9,remarks);
	                    ps.setString(10,"L");
		                ps.setString(11,updatedby);
		                ps.setTimestamp(12,ts);
		                ps.setInt(13,acUnit);
		                ps.setInt(14,officeId);
		                ps.setString(15,serviceNo);
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
		           		           
		            try{
             	              		                  		                          
	                    xml=xml+"<flag>success</flag>";
	                    
	                   // to_date(?,'dd-mm-yyyy')
	                    ps=con.prepareStatement("delete from FAS_EB_METER_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SERVICE_NO=?");
		               
		               
		                ps.setInt(1,acUnit);
		                ps.setInt(2,officeId);
		                ps.setString(3,serviceNo);
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

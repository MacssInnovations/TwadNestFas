package Servlets.FAS.FAS1.Masters.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class A52_Report_After_Edit
 */
public class A52_Report_After_Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
	    Connection connection = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public A52_Report_After_Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	PrintWriter out = response.getWriter();
    	   response.setHeader("cache-control","no-cache");
 	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
 	      response.setContentType(CONTENT_TYPE);
 	      
    	PreparedStatement ps = null;
    	ResultSet results;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String selstr = "";
        String selspestr = "";
        String sel = "";
        String opt = "";
        response.setContentType(CONTENT_TYPE);
        try {


            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        String strCommand = "",unitid="";
		String xml = "";

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try {
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("command");
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unitid = request.getParameter("unitid");
			System.out.println("unitid:-" + unitid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		
		if (strCommand.equalsIgnoreCase("loadUnitRendering")) {
			
			int count=0;
			xml = "<response><command>unitLoad</command>";
			
			try {
			
				
				int offid1=0;
				
				int cnt=0;
				
				  String ss="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+unitid;
		             
	               PreparedStatement ps21=connection.prepareStatement(ss);
		           ResultSet rs1=ps21.executeQuery();
		           if(rs1.next()){
		        	   offid1=rs1.getInt("ACCOUNTING_UNIT_OFFICE_ID");
		           }         
		           System.out.println(" offid1  "+offid1);
	            	 //find reployement or not....
	  	          String ssq="select OFFICE_LEVEL_ID,OFFICE_STATUS_ID from COM_MST_OFFICES where OFFICE_ID="+offid1;
	  	     //  System.out.println(ssq);
	  	          String officlevel="";
	  	          String offstatus="";
	  	           PreparedStatement ps20=connection.prepareStatement(ssq);
	  	           ResultSet rs11=ps20.executeQuery();
	  	           if(rs11.next()){
	  	        	 officlevel=rs11.getString("OFFICE_LEVEL_ID");
	  	        	offstatus=rs11.getString("OFFICE_STATUS_ID");
	  	        	System.out.println("offstatus  "+offstatus);
	  	        	//&&(offstatus.equalsIgnoreCase("CR"))
	  	        	 if((officlevel.equalsIgnoreCase("CL")) ){
	  	        		 String ssq2="select a.RENDERING_UNIT_OFFICE_ID,a.ACCT_UNIT_ID_RENDERED_FOR,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=a.rendering_unit_office_id)as officeName from fas_asset_val_ac_render_units a where a.acct_rendering_unit_id="+unitid+" order by a.RENDERING_UNIT_OFFICE_ID";
			  	        	
			  	        	// System.out.println(ssq2);
			  	        	 PreparedStatement ps211=connection.prepareStatement(ssq2);
			  	        	   ResultSet rs112=ps211.executeQuery();
			  	        	   while(rs112.next()){
			  	        		 
			  	        		 xml = xml + "<unit_No>"+ rs112.getInt("ACCT_UNIT_ID_RENDERED_FOR") +"-"+rs112.getInt("RENDERING_UNIT_OFFICE_ID")+"-"+rs112.getInt("RENDERING_UNIT_OFFICE_ID")+"</unit_No>";
									xml=xml+"<desc>"+rs112.getString("officeName").trim()+"("+rs112.getInt("RENDERING_UNIT_OFFICE_ID")+")"+"</desc>";
			  	        		   
			  	        		
			  	          			                count++;
			  	        		   
			  	        	   }
	  	        	 }else if(officlevel.equalsIgnoreCase("DN")){
	  	        		 
	  	        		
	  	        String ss11="select accounting_for_office_id,accounting_unit_id,b.office_name,b.OFFICE_ID from fas_mst_acct_unit_offices a,com_mst_offices b where b.OFFICE_ID in (select office_id from FAS_NEW_VIEW  where division_office_id="+offid1+") and a.ACCOUNTING_UNIT_ID="+unitid;
	  	        		 
                           
			  	        	 PreparedStatement ps11=connection.prepareStatement(ss11);
			  	        	   ResultSet rs112=ps11.executeQuery();
			  	        	   while(rs112.next()){
			  	        		 
			  	        		 xml = xml + "<unit_No>"+ rs112.getInt("accounting_unit_id") +"-"+rs112.getInt("OFFICE_ID")+ "-"+rs112.getInt("OFFICE_ID")+"</unit_No>";
									xml=xml+"<desc>"+rs112.getString("office_name").trim()+"("+rs112.getInt("OFFICE_ID")+")"+"</desc>";
			  	        		   
			  	        		 
									count++;
	  	        		 
	  	        	 }
	  	        	 }else if(officlevel.equalsIgnoreCase("HO")){
	  	        		 
	  	        String ss11="select a.RENDERING_UNIT_OFFICE_ID,a.ACCT_UNIT_ID_RENDERED_FOR,OFFICE_WING_SINO,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=a.rendering_unit_office_id)as officeName from fas_asset_val_ac_render_units a where a.acct_rendering_unit_id="+unitid+" order by a.RENDERING_UNIT_OFFICE_ID";
			  	        	 PreparedStatement ps11=connection.prepareStatement(ss11);
			  	        	   ResultSet rs112=ps11.executeQuery();
			  	        	   while(rs112.next()){	
									 xml = xml + "<unit_No>"+ rs112.getInt("ACCT_UNIT_ID_RENDERED_FOR") +"-"+rs112.getInt("RENDERING_UNIT_OFFICE_ID")+"-"+rs112.getInt("OFFICE_WING_SINO")+ "</unit_No>";
										xml=xml+"<desc>"+rs112.getString("officeName").trim()+"("+rs112.getInt("RENDERING_UNIT_OFFICE_ID")+")"+"("+rs112.getInt("OFFICE_WING_SINO")+")"+"</desc>";
				  	        		   
									count++;
	  	        		 
	  	        	 }
	  	        	 }
	  	        	 else {
	  	        		  String ss1="select ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
  	                      "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID= "+unitid;
  	            //    System.out.println(ss1);
  	                PreparedStatement ps22 =connection.prepareStatement(ss1);
  	               ResultSet rs10 = ps22.executeQuery();
  	               

  	                while (rs10.next()) {
  	                	
  	                  xml = xml + "<unit_No>"+ rs10.getInt("ACCOUNTING_UNIT_ID")+"-"+rs10.getInt("ACCOUNTING_FOR_OFFICE_ID")+"-"+rs10.getInt("ACCOUNTING_FOR_OFFICE_ID")+ "</unit_No>";
						xml=xml+"<desc>"+rs10.getString("OFFICE_NAME").trim()+"("+rs10.getInt("ACCOUNTING_FOR_OFFICE_ID")+")"+"</desc>";
  	                	
  			                 
  			                  count++;
  	                }
	  	        	 }
	  	        	  
	  	        	   
	  	           }else{
	  	        	   
	  	        	   System.out.println(" else ");
	  	           }
	  	         xml = xml + "<count>"+count+"</count>";
					if(count==0){
						xml = xml + "<flag>failure</flag>";
					}else{
						xml = xml + "<flag>success</flag>";
						System.out.println("count"+count);
					}
	  	           
	  	      		
				
								
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
		}
		
        
		xml = xml+"</response>";
		out.write(xml);
		System.out.println(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  	  try {
	            HttpSession session = request.getSession(false);
	            if (session == null) {
	                System.out.println(request.getContextPath() + "/index.jsp");
	                response.sendRedirect(request.getContextPath() + "/index.jsp");

	            }
	            System.out.println(session);

	        } catch (Exception e) {
	            System.out.println("Redirect Error :" + e);
	        }

	        String selstr = "";
	        String selspestr = "";
	        String sel = "";
	        String opt = "";
	        response.setContentType(CONTENT_TYPE);
	        try {


	            ResourceBundle rs =
	                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	            String ConnectionString = "";

	            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
	            String strdsn = rs.getString("Config.DSN");
	            String strhostname = rs.getString("Config.HOST_NAME");
	            String strportno = rs.getString("Config.PORT_NUMBER");
	            String strsid = rs.getString("Config.SID");
	            String strdbusername = rs.getString("Config.USER_NAME");
	            String strdbpassword = rs.getString("Config.PASSWORD");

	            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


	            Class.forName(strDriver.trim());
	            connection =
	                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
	                                                strdbpassword.trim());
	        } catch (Exception ex) {
	            String connectMsg =
	                "Could not create the connection" + ex.getMessage() + " " +
	                ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	       
			HttpSession session = request.getSession(false);

			try {

				if (session == null) {
					System.out.println(request.getContextPath() + "/index.jsp");
					response.sendRedirect(request.getContextPath() + "/index.jsp");

				}

			} catch (Exception e) {
				System.out.println("Redirect Error :" + e);
			}
			String userid = (String) session.getAttribute("UserId");
			System.out.println("User Id is:" + userid);
			try {
				if (session == null) {
					System.out.println(request.getContextPath() + "/index.jsp");
					response.sendRedirect(request.getContextPath() + "/index.jsp");

				}
				System.out.println(session);

			} catch (Exception e) {
				System.out.println("Redirect Error :" + e);
			}

			

			Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
					.getAttribute("UserProfile");
			int empid = empProfile.getEmployeeId();
			String empName = empProfile.getEmployeeName();
			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);
     // doGet(request, response);
  	 JasperDesign jasperDesign = null;
	        File reportFile = null;
	        try {
	            //System.out.println("++++++++calling A52Register_OBEntry servlet*****");
	            int AccUnitCode=0;
		            int asset_majclass_code=0 ;
		            String finyear="";
		            String rtype="" ;
		            String check="";
		          String units="",assetsWise="";
		          String check1=null;
		          int unit_rendered=0;
		          String assetwise="";
		          int officeiddd=0,officewing=0;   
		          String officelevel="",officelevel2="",officelevel1="";
		          int officelevelid=0;
		          String newQry="";
		          String circleQry="",optionView="";
		          int officeidd=0;
		          
	            	int offcid=0,unitiid=0;
	            try{
	            	
	           
	            AccUnitCode =Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	           // int AccOfficeCode =
	             //   Integer.parseInt(request.getParameter("cmbOffice_code"));
	            asset_majclass_code = Integer.parseInt(request.getParameter("cmbasset"));
	            finyear =request.getParameter("cmbFinancialYear");
	           rtype = request.getParameter("txtoption");
	            check=request.getParameter("unit_rendered");
	          check1=request.getParameter("allid");
	          assetwise=request.getParameter("allasset");
	          
	         optionView=request.getParameter("optionView");
	          
	          System.out.println("check "+check);
	              }catch(Exception e){
	            	System.out.println("input get from jsp---"+e);
	            	
	            }try{
	            	
	            	//and c.acct_rendering_unit_id       =368
	            	
	            	String sqry="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+AccUnitCode;
	            	 PreparedStatement ps=connection.prepareStatement(sqry);
			           ResultSet rs=ps.executeQuery();
			           while(rs.next()){
			        	   officeidd=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
			           }
	            	//System.out.println("officeidd "+officeidd); OFFICE_STATUS_ID
	            	String selectQry="select office_level_id from FAS_NEW_VIEW  where office_id="+officeidd;
			           PreparedStatement ps1=connection.prepareStatement(selectQry);
			           ResultSet rs1=ps1.executeQuery();
			           if(rs1.next()){
			        	   officelevel=rs1.getString("office_level_id");
			        	   
			        	   if(officelevel.equalsIgnoreCase("CL")){
					           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+AccUnitCode;
					           }else if(officelevel.equalsIgnoreCase("RN")){
					           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+AccUnitCode;
					           }else if(officelevel.equalsIgnoreCase("HO")){
					           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+AccUnitCode;
					           }
					           else{
					           //System.out.println("else part ");
					        /*   String findcir="select  CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID="+officeidd;
					           
					            PreparedStatement ps2=connection.prepareStatement(findcir);
					           ResultSet rs2=ps2.executeQuery();
					           while(rs2.next()){
					        	   offcid=rs2.getInt("CIRCLE_OFFICE_ID");  
					           } 
					           String findunitid="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID ="+offcid;
					            PreparedStatement ps3=connection.prepareStatement(findunitid);
					           ResultSet rs3=ps3.executeQuery();
					           while(rs3.next()){
					        	   unitiid=rs3.getInt("ACCOUNTING_UNIT_ID");   
					        	   
					           } */
					        	   
					        	   
					        	  
						           String findunitid="select ACCT_RENDERING_UNIT_ID from FAS_ASSET_VAL_AC_RENDER_UNITS where ACCT_UNIT_ID_RENDERED_FOR ="+AccUnitCode;
						            PreparedStatement ps3=connection.prepareStatement(findunitid);
						           ResultSet rs3=ps3.executeQuery();
						           while(rs3.next()){
						        	   unitiid=rs3.getInt("ACCT_RENDERING_UNIT_ID");   
						        	   
						           } 
					           
					           ///finding circle id for unit.....
					            circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+unitiid;
					           // System.out.println("welcome...  circleQry   ==>"+circleQry);

			    				String selectQry2="select office_level_id from FAS_NEW_VIEW  where office_id="+officeidd;
						           PreparedStatement ps12=connection.prepareStatement(selectQry2);
						           ResultSet rs12=ps12.executeQuery();
						           while(rs12.next()){
						        	   officelevel1=rs12.getString("office_level_id");
						           }
						          // System.out.println(" officelevel1 "+officelevel1);
						           if(officelevel1.equalsIgnoreCase("DN")){
						        	  
						        	   units=" and a.accounting_unit_office_id in (select office_id from FAS_NEW_VIEW  where division_office_id="+officeidd+")"  ;
						        	   
						           }else{
						        	 
						        	   units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unitiid+" and a.accounting_unit_office_id="+officeidd;
						        	  // units="";
						           }
						         //  System.out.println("units  "+units);   
					            
					           }   
			        	   
			           }else{
			        	   
			        	   String selectQryss="select office_level_id,OFFICE_STATUS_ID from COM_MST_OFFICES  where office_id="+officeidd;
				           PreparedStatement ps1s=connection.prepareStatement(selectQryss);
				           ResultSet rs1s=ps1s.executeQuery();
				           String offlevel="";
				           String offstatus="";
			        	   if(rs1s.next()){
			        		   offlevel=rs1s.getString("office_level_id");
			        		   offstatus=rs1s.getString("OFFICE_STATUS_ID");
			        		 // if((offlevel.equalsIgnoreCase("CL"))&&((offstatus.equalsIgnoreCase("NC"))||(offstatus.equalsIgnoreCase("CL"))||(offstatus.equalsIgnoreCase("RD"))  ) ){
			        			  
			        			  units="and a.accounting_unit_office_id= "+officeidd;
			        			  
			        		  /*}else{
			        			  units="and a.accounting_unit_office_id= "+officeidd;
			        		  }*/
			        		   
			        		   
			        	   }
			        	   
			           }
			          
		            System.out.println("cmbAcc_UnitCode**"+AccUnitCode+"**asset_majclass_code"+asset_majclass_code+"finyear* "+finyear+"unit_rendered--"+check+"check 1"+check1);
	            	}catch(Exception e){
	            	System.out.println("error in split "+e);
	            }
	    			if(check1.equalsIgnoreCase("All"))
					{	
						
					}
					else
					{	
						 String[] unitsoffice=check.split("-");
				           //unit_rendered=Integer.parseInt(check);      
				           unit_rendered=Integer.parseInt(unitsoffice[0]); 
				           officeiddd=Integer.parseInt(unitsoffice[1]);
				           officewing=Integer.parseInt(unitsoffice[2]);
				           
				        // System.out.println("unit_rendered==="+unit_rendered+"  officeiddd--- "+officeiddd+" officewing "+officewing);
				           
				        // old   String selectQry="select office_level_id from com_mst_all_offices_view  where office_id="+officeiddd;
				           String selectQry="select office_level_id from FAS_NEW_VIEW  where office_id="+officeiddd;
				          
				           PreparedStatement ps=connection.prepareStatement(selectQry);
				           ResultSet rs=ps.executeQuery();
				           while(rs.next()){
				        	   officelevel2=rs.getString("office_level_id");
				           }
				           System.out.println(" officelevel2 "+officelevel2);
				           if(officelevel2.equalsIgnoreCase("DN")){
				        	   //newQry=" and a.accounting_unit_office_id in (select office_id from com_mst_all_offices_view  where division_office_id="+officeiddd+")"  ;
				        	 //old units=" and a.accounting_unit_office_id in (select office_id from com_mst_all_offices_view  where division_office_id="+officeiddd+")"  ;
				        	   units=" and a.accounting_unit_office_id in (select office_id from FAS_NEW_VIEW  where division_office_id="+officeiddd+")"  ;
				        	   
				           } else if(officelevel2.equalsIgnoreCase("HO")) {
				        	  // newQry=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id="+officeiddd;
				        	   units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id="+officeiddd+" and a.office_wing_sino="+officewing;
				           }
				           else{
				        	  // newQry=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id="+officeiddd;
				        	   units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id="+officeiddd;
				           }
						//units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id= "+officeiddd;
						//units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id "+newQry;
						//System.out.println("check...in else "+units+check1);
					}
					if(assetwise.equalsIgnoreCase("All"))
					{	
						assetsWise="";
						//System.out.println("check..in if ."+units+check1);
					}
					else
					{					
						assetsWise="  AND asset_major_class_code ="+asset_majclass_code;
						//System.out.println("check...in else "+units+check1);
					}
					
					
					if(optionView.equalsIgnoreCase("beforeEdit")){
						 reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A52Report_Before_Edit.jasper"));
					}else if (optionView.equalsIgnoreCase("afterEdit"))
					{
						 reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A52Report_After_Edit.jasper"));	
					}
	          
	          
	            if (!reportFile.exists())
	                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	            JasperReport jasperReport =
	                (JasperReport)JRLoader.loadObject(reportFile.getPath());
	           // input get from

	            Map map = new HashMap();
	
				System.out.println("units=="+units);
				System.out.println("assetsWise=="+assetsWise);
				 System.out.println("circleQry  "+circleQry);
				 System.out.println("reportFile  "+reportFile);
				 
				 
	            map.put("financialyear", finyear);
	            map.put("accunitid", AccUnitCode);
	            map.put("assetmajclass", asset_majclass_code);
	          
	            map.put("querp", units);
	            map.put("circleQry",circleQry);	           
	            map.put("queryAsset", assetsWise);
	         
	            JasperPrint jasperPrint =
	                JasperFillManager.fillReport(jasperReport, map, connection);


	            if (rtype.equalsIgnoreCase("HTML")) {
	                response.setContentType("text/html");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"A52Report_After_Edit.html\"");
	                PrintWriter out = response.getWriter();
	                JRHtmlExporter exporter = new JRHtmlExporter();
	                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
	                                      false);
	                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
	                                      jasperPrint);
	                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                exporter.exportReport();
	                out.flush();
	                out.close();
	            } else if (rtype.equalsIgnoreCase("PDF")) {
	            	System.out.println("the option chosen is :::::"+rtype);
	                byte buf[] =
	                    JasperExportManager.exportReportToPdf(jasperPrint);
	                response.setContentType("application/pdf");
	                response.setContentLength(buf.length);
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"A52Report_After_Edit.pdf\"");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
	                System.out.println("testing***"+jasperPrint);
	                out.close();
	            } else if (rtype.equalsIgnoreCase("EXCEL")) {

	                response.setContentType("application/vnd.ms-excel");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"A52Report_After_Edit.xls\"");
	                JRXlsExporter exporterXLS = new JRXlsExporter();
	                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
	                                         jasperPrint);

	                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
	                                         xlsReport);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
	                                         Boolean.FALSE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
	                                         Boolean.TRUE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
	                                         Boolean.FALSE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
	                                         Boolean.TRUE);
	                exporterXLS.exportReport();
	                byte[] bytes;
	                bytes = xlsReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();

	            } else if (rtype.equalsIgnoreCase("TXT")) {

	                response.setContentType("text/plain");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"A52Report_After_Edit.txt\"");

	                JRTextExporter exporter = new JRTextExporter();
	                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
	                                      jasperPrint);
	                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
	                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
	                                      txtReport);
	                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
	                                      new Integer(200));
	                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
	                                      new Integer(50));
	                exporter.exportReport();

	                byte[] bytes;
	                bytes = txtReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();

	            }

	        } catch (Exception ex) {
	            String connectMsg =
	                "Could not create the report " + ex.getMessage() + " " +
	                ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	}

}

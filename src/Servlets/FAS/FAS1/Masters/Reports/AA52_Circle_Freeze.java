package Servlets.FAS.FAS1.Masters.Reports;

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
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class AA52_Circle_Freeze
 */
public class AA52_Circle_Freeze extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
    Connection connection = null;   
    
    public AA52_Circle_Freeze() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 Connection connection = null;
		 	PrintWriter out = response.getWriter();
		 	   response.setHeader("cache-control","no-cache");
			      String CONTENT_TYPE = "text/xml; charset=windows-1252";
			      response.setContentType(CONTENT_TYPE);
	
		 	PreparedStatement preparedStatement1 = null;
		 	PreparedStatement ps2= null,ps22=null;
		 	ResultSet rss=null;
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
		         String connectMsg ="Could not create the connection" + ex.getMessage() + " " +
		             ex.getLocalizedMessage();
		         System.out.println(connectMsg);
		     }
		     String strCommand = "";
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

				
				
				Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
						.getAttribute("UserProfile");
			
				long l = System.currentTimeMillis();
				Timestamp ts = new Timestamp(l);
				Vector<Integer> status=new Vector<Integer>();
				Vector<Integer> statusnot=new Vector<Integer>();
				
				 if(strCommand.equalsIgnoreCase("FreezeAA52_Circle"))
		        {
					
		            xml="<response><command>FreezeAA52_Circle</command>";
					int count = 0;
					
					//try{
						int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
						int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
						String cmbFinancialYear = request.getParameter("cmbFinancialYear");
						String verifya52aa52=request.getParameter("verifya52aa52").trim();
						//String verifyvalueA52="";
						String verifyvalueAA52="";	
						//String A52Date1="",AA52Date1="";
								try {
									ResultSet rs0=null,rs1=null,rs2=null,rs3=null;
									PreparedStatement ps1=null,ps3=null;
									ps2=connection.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from FREEZE_AA52_STATUS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND FINANCIAL_YEAR='"+cmbFinancialYear+"' AND AA52_STATUS_UNIT='Y'  AND AA52_STATUS_CIRCLE='Y' ");
									rs0=ps2.executeQuery();
									if(rs0.next()){
										//System.out.println("Already exists ");
										xml=xml+"<flag>AlreadyExist</flag>";									
									}else{
										String notofficestatusupdate="";
										String officestatusupdate="";
										String queryFind="select RENDERING_UNIT_OFFICE_ID from fas_asset_val_ac_render_units where acct_rendering_unit_id="+cmbAcc_UnitCode;
										ps1=connection.prepareStatement(queryFind);
										rs1=ps1.executeQuery();
										if(rs1.next()){
											ResultSet rs4=ps1.executeQuery();
											while(rs4.next()){

												int unitofficeid=rs4.getInt("RENDERING_UNIT_OFFICE_ID");
												//System.out.println("inside checking with this table..."+unitofficeid);
												String findingintab="select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from FREEZE_AA52_STATUS where ACCOUNTING_FOR_OFFICE_ID="+unitofficeid+" AND FINANCIAL_YEAR='"+cmbFinancialYear+"' AND AA52_STATUS_UNIT='Y'";
												ps3=connection.prepareStatement(findingintab);
												rs2=ps3.executeQuery();
												if(rs2.next()){
													ResultSet rs5=ps3.executeQuery();
													while(rs5.next()){
														//System.out.println("inside inner while looppp ");
														int statusoffice=rs5.getInt("ACCOUNTING_FOR_OFFICE_ID");
														//System.out.println("statusoffice "+statusoffice);
														if(!status.contains(statusoffice)){
															status.add(statusoffice);
														}								
													}
													
												}else{
													//notofficestatusupdate+=unitofficeid;
													if(!statusnot.contains(unitofficeid)){
													statusnot.add(unitofficeid);
													}
												}
												
												//System.out.println("  status  "+status);
												//System.out.println("  statusnot  "+statusnot);	
										   	}
											int sizevect=statusnot.size();
											if(statusnot.isEmpty()){
												//(statusnot.contains(cmbAcc_UnitCode))
												//System.out.println("vector status vector empty");
												

												if(verifya52aa52.equalsIgnoreCase("AA52")){
													//System.out.println("inside a52 ");
													verifyvalueAA52="Y";
													
													java.sql.Date AA52Date=new java.sql.Date(ts.getTime());
													//System.out.println("if aa52 "+AA52Date);
													preparedStatement1 = connection.prepareStatement("update FREEZE_AA52_STATUS set AA52_STATUS_CIRCLE=?,AA52_DATE_CIRCLE=? where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=? AND AA52_STATUS_UNIT='Y'");
													preparedStatement1.setString(1, verifyvalueAA52);
													preparedStatement1.setDate(2, AA52Date);							
													preparedStatement1.setInt(3, cmbAcc_UnitCode);
													preparedStatement1.setInt(4, cmbOffice_code);
													preparedStatement1.setString(5, cmbFinancialYear);
													
							    					count = preparedStatement1.executeUpdate();
													System.out.println("count:" + count);
													 
												}
												 if(count>0) {
								                        xml=xml+"<flag>success</flag>"; 
								                    }	
												
											}else{
												xml=xml+"<flag>someOfficeNot</flag>"; 
											}
											
										}else{
											System.out.println("Else part no rendering");
											xml=xml+"<flag>NotCircle</flag>"; 
											/*

											if(verifya52aa52.equalsIgnoreCase("AA52")){
											//	System.out.println("inside a52 ");		
												verifyvalueAA52="Y";
											
												java.sql.Date AA52Date=new java.sql.Date(ts.getTime());
											//	System.out.println("if a52 "+A52Date);
												preparedStatement1 = connection.prepareStatement("update FREEZE_AA52_STATUS set AA52_STATUS_CIRCLE=?,AA52_DATE_CIRCLE=? where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=?");
												preparedStatement1.setString(1, verifyvalueAA52);
												preparedStatement1.setDate(2, AA52Date);							
												preparedStatement1.setInt(3, cmbAcc_UnitCode);
												preparedStatement1.setInt(4, cmbOffice_code);
												preparedStatement1.setString(5, cmbFinancialYear);
												
												count = preparedStatement1.executeUpdate();
												System.out.println("count:" + count);
												//}
											}
											 if(count>0) {
							                        xml=xml+"<flag>success</flag>"; 
							                    }	
											*/
											System.out.println("No  rendering ---");
										}

									} 
		                }
		            catch(Exception e)
		                {
		                     System.out.println("exception in add is"+e);
		                     xml=xml+"<flag>failure</flag>";
		                }
		           
		        }
				 /*else if(strCommand.equalsIgnoreCase("checkStatusA52AA52Register")){
					// int c1=0;
					 xml="<response><command>checkStatusA52AA52Register</command>";
						//int count = 0;
						System.out.println("inside checkStatusA52AA52Register");
						int cmbAcc_UnitCode=0;
						//int cmbOffice_code =0;
						String cmbFinancialYear="";
						try{
							cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
							//cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
							cmbFinancialYear = request.getParameter("cmbFinancialYear");
						}
						catch(Exception e){
							System.out.println("eeegetng values error");
							
						}
							try {
								int c1=0;
								ps22=connection.prepareStatement("select A52_STATUS_VAL,AA52_STATUS from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' AND FINANCIAL_YEAR='"+cmbFinancialYear+"'");
								rss=ps22.executeQuery();
								System.out.println("inside try");
								System.out.println(cmbAcc_UnitCode+"  "+cmbFinancialYear);
								while(rss.next()){
									System.out.println("record having");
									xml=xml+"<A52_STATUS>"+ rss.getString("A52_STATUS_QTY").trim()+"</A52_STATUS>";
									xml=xml+"<AA52_STATUS>"+ rss.getString("AA52_STATUS").trim()+"</AA52_STATUS>";
									c1++;
								}
								if(c1==0){
									System.out.println("not record having");
									xml = xml + "<flag>failure</flag>";
								}else{
									System.out.println("record having  -----");
									xml = xml + "<count>"+c1+"</count>";
									xml = xml + "<flag>success</flag>";
									System.out.println("c1 in get values "+c1);
								}
								
							}
				            catch(Exception e)
				                {
				                     System.out.println("exception in get values "+e);
				                     xml=xml+"<flag>failure</flag>";
				                }	
								
				 }*/

				
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

		//String cmd="";
		String strCommand = request.getParameter("command");
		int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
		int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		String cmbFinancialYear = request.getParameter("cmbFinancialYear");
		String verifya52aa52=request.getParameter("aa52_option");
		//System.out.println("verifya52aa52  "+verifya52aa52);
		if(strCommand.equalsIgnoreCase("viewAbstract")){
			
		/*if(verifya52aa52.equalsIgnoreCase("A52")){
			 JasperDesign jasperDesign = null;
		        File reportFile = null;
		        try {
		          
		           reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A52RegisterOBDataEntry_Abs.jasper"));
		                  
		            if (!reportFile.exists())
		                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		            JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());


		            Map map = new HashMap();
		            map.put("accunitid", cmbAcc_UnitCode);
		            map.put("financialyear", cmbFinancialYear);
		         
		            JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);
		            byte buf[] =JasperExportManager.exportReportToPdf(jasperPrint);
	                response.setContentType("application/pdf");
	                response.setContentLength(buf.length);
	                response.setHeader("Content-Disposition","attachment;filename=\"A52Abs.pdf\" ");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
		           // System.out.println("testing***"+jasperPrint);
		            out.close(); 
		           
		            String s = request.getContextPath();
		            RequestDispatcher rd = request.getRequestDispatcher(s+"Verify_A52_AA52_Register.jsp");
		            request.setAttribute("msg", "Do u want to contine");
		            rd.forward(request, response);  

		        } catch (Exception ex) {
		            String connectMsg =
		                "Could not create the report " + ex.getMessage() + " " +
		                ex.getLocalizedMessage();
		            System.out.println(connectMsg);
		        }
		        String ss=request.getParameter("btverifyA52AA52");
		        //System.out.println("button   "+ss);
		       
		       // JOptionPane.showConfirmDialog (null, "Yes or No?");

		        int option = JOptionPane.showConfirmDialog (null, "Yes or No?");

		        if (option == JOptionPane.YES_OPTION ) {
		        	System.out.println("yes selcted");
		        	
		        	
		        }
		        else if (option == JOptionPane.NO_OPTION) {
		        	System.out.println("no  selcted");
		        	// JOptionPane.showMessageDialog (null, "This is your answer for NO...'");
		        }
		       
		        else {
		        	System.out.println("cancel selcted");
		        	
		        }
		        
		}else */if(verifya52aa52.equalsIgnoreCase("AA52")){
		//System.out.println("aa52");	
		 JasperDesign jasperDesign = null;
	        File reportFile = null;
	        try {
		 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/AA52OB_DATA_REPORT_Abstract.jasper")); 
        // System.out.println("reportFile"+reportFile);   
       if (!reportFile.exists())
       throw new JRRuntimeException("File J not found. The report design must be compiled first.");
       
       JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
     // System.out.println("jasperReport"+jasperReport);
       Map map=new HashMap();

       map.put("accountingunitid",cmbAcc_UnitCode);
       map.put("accountofficeid",cmbOffice_code);
       map.put("cmbFinancialYear",cmbFinancialYear);        
      
     
       JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
        
       			System.out.println("PDF:::::::::::");
                   byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                   response.setContentType("application/pdf");
                   response.setContentLength(buf.length);
                   response.setHeader ("Content-Disposition", "attachment;filename=\"AA52Abstract.pdf\"");
                   OutputStream out = response.getOutputStream();
                   out.write(buf, 0, buf.length);
                   out.close();
			   } 
			   catch (Exception ex) 
			   {
			       String connectMsg = 
			       "Could not create the report " + ex.getMessage() + " " + 
			       ex.getLocalizedMessage();
			       System.out.println(connectMsg);
			   }
	
		}
		}
	}

}

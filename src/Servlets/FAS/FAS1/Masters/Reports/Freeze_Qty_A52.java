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
 * Servlet implementation class Freeze_Qty_A52
 */
public class Freeze_Qty_A52 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
    Connection connection = null;   
    public Freeze_Qty_A52() {
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

				
				
				Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
						.getAttribute("UserProfile");
			
				long l = System.currentTimeMillis();
				Timestamp ts = new Timestamp(l);
				Vector<Integer> status=new Vector<Integer>();
				Vector<Integer> statusnot=new Vector<Integer>();
				
				 if(strCommand.equalsIgnoreCase("FreezeA52Qty"))
		        {
					
		            xml="<response><command>FreezeA52Qty</command>";
					int count = 0;
					
					//try{
						int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
						int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
						String cmbFinancialYear = request.getParameter("cmbFinancialYear");
						String verifya52aa52=request.getParameter("verifya52aa52").trim();
						String verifyvalueA52="";
						String verifyvalueAA52="";	
						String A52Date1="",AA52Date1="";
								try {
									
									ResultSet rs0=null,rs1=null,rs2=null,rs3=null;
									PreparedStatement ps1=null,ps3=null;
									
									int uuunitid=0;
									
									 String ss1="select a.RENDERING_UNIT_OFFICE_ID,a.ACCT_UNIT_ID_RENDERED_FOR,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=a.rendering_unit_office_id)as officeName from fas_asset_val_ac_render_units a where a.acct_rendering_unit_id="+cmbAcc_UnitCode+" and a.RENDERING_UNIT_OFFICE_ID="+cmbOffice_code+"  order by a.RENDERING_UNIT_OFFICE_ID";
			  	            //    System.out.println(ss1);
			  	                PreparedStatement ps =connection.prepareStatement(ss1);
			  	               ResultSet rs10 = ps.executeQuery();
									if(rs10.next()){
										uuunitid=rs10.getInt("ACCT_UNIT_ID_RENDERED_FOR");
									}else{
										uuunitid=cmbAcc_UnitCode;
									}
									
									System.out.println("uuunitid--->  "+uuunitid);
									
									
									ps2=connection.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID='"+uuunitid+"' AND ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code+"' AND FINANCIAL_YEAR='"+cmbFinancialYear+"' AND A52_STATUS_QTY='Y'");
									rs0=ps2.executeQuery();
									if(rs0.next()){
										//System.out.println("Already exists two ");
										xml=xml+"<flag>AlreadyExist</flag>";									
									}else{
										

										/*String notofficestatusupdate="";
										String officestatusupdate="";
										String queryFind="select RENDERING_UNIT_OFFICE_ID from fas_asset_val_ac_render_units where acct_rendering_unit_id="+cmbAcc_UnitCode;
										ps1=connection.prepareStatement(queryFind);
										rs1=ps1.executeQuery();
										if(rs1.next()){

											while(rs1.next()){

												int unitofficeid=rs1.getInt("RENDERING_UNIT_OFFICE_ID");
												//System.out.println("inside checking with this table..."+unitofficeid);
												String findingintab="select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID="+unitofficeid+" AND FINANCIAL_YEAR='"+cmbFinancialYear+"' AND A52_STATUS_QTY='Y'";
												ps3=connection.prepareStatement(findingintab);
												rs2=ps3.executeQuery();
												if(rs2.next()){
													
													while(rs2.next()){
														//System.out.println("inside inner while looppp ");
														int statusoffice=rs2.getInt("ACCOUNTING_FOR_OFFICE_ID");
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
												System.out.println("vector status vector empty");
												

												if(verifya52aa52.equalsIgnoreCase("A52")){
													System.out.println("inside a52 ");
													verifyvalueA52="Y";
													verifyvalueAA52="N";
													java.sql.Date A52Date=new java.sql.Date(ts.getTime());
													System.out.println("if a52 "+A52Date);
													preparedStatement1 = connection.prepareStatement("insert into FAS_A52_AA52_Verify_status(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,A52_STATUS_VAL,AA52_STATUS,A52_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,AA52_DATE)values(?,?,?,?,?,?,?,SYSTIMESTAMP,?)");
													preparedStatement1.setInt(1, cmbAcc_UnitCode);
													preparedStatement1.setInt(2, cmbOffice_code);
													preparedStatement1.setString(3, cmbFinancialYear);
													preparedStatement1.setString(4, verifyvalueA52);
													preparedStatement1.setString(5, verifyvalueAA52);
													preparedStatement1.setDate(6, A52Date);						
													preparedStatement1.setString(7, userid);
													preparedStatement1.setString(8, AA52Date1);		
							    					count = preparedStatement1.executeUpdate();
													System.out.println("count:" + count);
													 
												}
												 if(count>0) {
								                        xml=xml+"<flag>success</flag>"; 
								                    }	
												
											}else{
												xml=xml+"<flag>someOfficeNot</flag>"; 
											}
											
										}else{*/

											if(verifya52aa52.equalsIgnoreCase("A52")){
											//	System.out.println("inside a52 ");		
												verifyvalueA52="Y";
												//verifyvalueAA52="N";
												java.sql.Date A52Date=new java.sql.Date(ts.getTime());
											//	System.out.println("if a52 "+A52Date);
												preparedStatement1 = connection.prepareStatement("insert into FAS_A52_AA52_Verify_status(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,A52_STATUS_QTY,A52_DATE_QTY,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,SYSTIMESTAMP)");
												preparedStatement1.setInt(1, uuunitid);
												preparedStatement1.setInt(2, cmbOffice_code);
												preparedStatement1.setString(3, cmbFinancialYear);
												preparedStatement1.setString(4, verifyvalueA52);
												preparedStatement1.setDate(5, A52Date);						
												preparedStatement1.setString(6, userid);
	
						    					count = preparedStatement1.executeUpdate();
												System.out.println("count:" + count);
												//}
											}
											 if(count>0) {
							                        xml=xml+"<flag>success</flag>"; 
							                    }	
											
											
										//}
									} 
		                }
		            catch(Exception e)
		                {
		                     System.out.println("exception in add is"+e);
		                     xml=xml+"<flag>failure</flag>";
		                }
								xml = xml+"</response>";
								out.write(xml);
								System.out.println(xml);
		        } 
				 else if (strCommand.equalsIgnoreCase("LoadUnitWise_OfficeCode")) {
			            xml = "<response><command>" + strCommand + "</command>";
			            int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			           
			            try {
			            	//unit_id
			            	int offid1=0;
			            	 int cnt = 0;
			              
			               String ss="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
			             
			               PreparedStatement ps21=connection.prepareStatement(ss);
				           ResultSet rs1=ps21.executeQuery();
				           if(rs1.next()){
				        	   offid1=rs1.getInt("ACCOUNTING_UNIT_OFFICE_ID");
				           }         
				           
			            	 //find reployement or not....
			  	          String ssq="select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID='CL' and OFFICE_ID="+offid1;
			  	     //  System.out.println(ssq);
			  	           PreparedStatement ps20=connection.prepareStatement(ssq);
			  	           ResultSet rs11=ps20.executeQuery();
			  	           if(rs11.next()){
			  	        	   
			  	        	   String ssq2="select a.RENDERING_UNIT_OFFICE_ID,a.ACCT_UNIT_ID_RENDERED_FOR,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=a.rendering_unit_office_id)as officeName from fas_asset_val_ac_render_units a where a.acct_rendering_unit_id="+cmbAcc_UnitCode+" order by a.RENDERING_UNIT_OFFICE_ID";
			  	        	
			  	        	// System.out.println(ssq2);
			  	        	 PreparedStatement ps211=connection.prepareStatement(ssq2);
			  	        	   ResultSet rs112=ps211.executeQuery();
			  	        	   while(rs112.next()){
			  	        		 
			  	        		   xml =
			  	          			 xml + "<offid>" + rs112.getInt("RENDERING_UNIT_OFFICE_ID") + "</offid>";

			  	        		   xml =
			  	          			 xml + "<uuid>" + rs112.getInt("ACCT_UNIT_ID_RENDERED_FOR") + "</uuid>";
			  	          			                    xml =
			  	          			 xml + "<offname>" + rs112.getString("officeName").trim() + "</offname>";
			  	          			                    cnt++;   
			  	        		   
			  	        	   }
			  	        	   
			  	           }else{

			  	                String ss1="select ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b " +
			  	                      "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode;
			  	            //    System.out.println(ss1);
			  	                PreparedStatement ps =connection.prepareStatement(ss1);
			  	               ResultSet rs10 = ps.executeQuery();
			  	               

			  	                while (rs10.next()) {
			  			                    xml = xml + "<offid>" + rs10.getInt("ACCOUNTING_FOR_OFFICE_ID") + "</offid>";
			  			                  xml = xml + "<uuid>" + rs10.getInt("ACCOUNTING_UNIT_ID") + "</uuid>";
			  			                    xml = xml + "<offname>" + rs10.getString("OFFICE_NAME").trim() + "</offname>";
			  			                    cnt++;
			  	                }
			  	               
			  	        	   
			  	           } 
			            	
			                if (cnt != 0)
			                    xml = xml + "<flag>success</flag>";
			                else
			                    xml = xml + "<flag>failure</flag>";
			            } catch (Exception e) {
			                System.out.println("catch..HERE.in load head code." + e);
			                xml = xml + "<flag>failure</flag>";
			            }
			         
			            xml = xml+"</response>";
						out.write(xml);
						System.out.println(xml);  

			        }
				 else  if(strCommand.equalsIgnoreCase("UnFreezeA52Qty"))
		        {
					
		            xml="<response><command>UnFreezeA52Qty</command>";
					int count = 0;
					
					//try{
						int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
						int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
						String cmbFinancialYear = request.getParameter("cmbFinancialYear");
						String verifya52aa52=request.getParameter("verifya52aa52").trim();
						String verifyvalueA52="";
						String officelevel="";
								
									
								if(cmbFinancialYear.equalsIgnoreCase("2011-12"))	
								{	
									xml+="<fin_year>"+cmbFinancialYear+"</fin_year>";
									try {
									ResultSet rs0=null,rs1=null,rs2=null,rs3=null,rss1=null,rss3=null;
									PreparedStatement ps1=null,ps3=null,ps=null;
									
									int uuunitid=0;
									
									 String ss1="select a.RENDERING_UNIT_OFFICE_ID,a.ACCT_UNIT_ID_RENDERED_FOR,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=a.rendering_unit_office_id)as officeName from fas_asset_val_ac_render_units a where a.acct_rendering_unit_id="+cmbAcc_UnitCode+" and a.RENDERING_UNIT_OFFICE_ID="+cmbOffice_code+"  order by a.RENDERING_UNIT_OFFICE_ID";
			  	            //    System.out.println(ss1);
			  	                PreparedStatement ps11 =connection.prepareStatement(ss1);
			  	               ResultSet rs10 = ps11.executeQuery();
									if(rs10.next()){
										uuunitid=rs10.getInt("ACCT_UNIT_ID_RENDERED_FOR");
									}
									else{
										uuunitid=cmbAcc_UnitCode;
									}
									System.out.println("uuunitid--->  "+uuunitid);

									
									
									
									ps2=connection.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID='"+uuunitid+"' AND ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code+"' AND FINANCIAL_YEAR='"+cmbFinancialYear+"' AND A52_STATUS_QTY='Y'");
									rs0=ps2.executeQuery();
									if(rs0.next()){
										
										 String selectQry="select office_level_id from com_mst_all_offices_view  where office_id="+cmbOffice_code;
								           PreparedStatement ps4=connection.prepareStatement(selectQry);
								           ResultSet rs=ps4.executeQuery();
								           while(rs.next()){
								        	   officelevel=rs.getString("office_level_id");
								           }
								          // System.out.println(" officelevel "+officelevel);
								           int cricleID=0;
											int circleunitid=0;
								           if(officelevel.equalsIgnoreCase("RN")){
								        	   cricleID=cmbOffice_code;
								        	  // circleunitid=cmbAcc_UnitCode;
								        	   circleunitid=uuunitid;
								        	   
								        	   //select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?
								           }else {
	
						            	String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
						            	  ps =  connection.prepareStatement(selectCircleID);
						                  ps.setInt(1, cmbOffice_code);
						                  rss1=ps.executeQuery();
						                  while(rss1.next()){
						                	  cricleID=rss1.getInt("CIRCLE_OFFICE_ID");	  
						                  }
						                  String selectCircleID1="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?";
						            	  ps3 =  connection.prepareStatement(selectCircleID1);
						                  ps3.setInt(1, cricleID);
						                  rss3=ps3.executeQuery();
						                  while(rss3.next()){
						                	  circleunitid=rss3.getInt("ACCOUNTING_UNIT_ID");	  
						                  }
						                  
								           }
						                 // System.out.println("check circle freeze cricleID  "+cricleID+"circleunitid  "+circleunitid);
						                  ps1=connection.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID='"+circleunitid+"' AND ACCOUNTING_FOR_OFFICE_ID='"+cricleID+"' AND FINANCIAL_YEAR='"+cmbFinancialYear+"' AND A52_STATUS_VAL='Y'");
										  rs1=ps1.executeQuery();
										  //System.out.println("check circle freeze  ");
										  if(rs1.next()){
											  
											 // System.out.println("check circle freeze ddd  ");
											  xml=xml+"<flag>CircleFreezed</flag>"; 
														
										  }else{
											 // System.out.println("check circle freeze not    ");
											  
												  String delqry="delete from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=?";
											  preparedStatement1 = connection.prepareStatement(delqry);
												preparedStatement1.setInt(1, uuunitid);
												preparedStatement1.setInt(2, cmbOffice_code);
												preparedStatement1.setString(3, cmbFinancialYear);
												
						    					count = preparedStatement1.executeUpdate();
												System.out.println("count:" + count);

											 if(count>0) {
							                        xml=xml+"<flag>success</flag>"; 
							                    }
											  
										  }
										
											
											
										//}
									
								}
									else{
										xml=xml+"<flag>FirstFreeze</flag>";	
									} 
									
		                }
		            catch(Exception e)
		                {
		                     System.out.println("exception in add is"+e);
		                     xml=xml+"<flag>failure</flag>";
		                }  
									
		        }else if(cmbFinancialYear.equalsIgnoreCase("2012-13"))
				{

		        	xml+="<fin_year>"+cmbFinancialYear+"</fin_year>";
					try {
						ResultSet rs0=null,rs1=null,rs2=null,rs3=null;
						PreparedStatement ps1=null,ps3=null;
						
						
						
						
						ps2=connection.prepareStatement("SELECT " +
								"  CASE " +
								"    WHEN A52_STATUS_CIRCLE IS NULL " +
								"    AND A52_STATUS_UNIT     ='Y' " +
								"    THEN 'freeze' " +
								"    WHEN A52_STATUS_CIRCLE IS NOT NULL " +
								"    AND A52_STATUS_UNIT     ='Y' " +
								"    THEN 'Circle_Freezed' " +
								"    WHEN A52_STATUS_CIRCLE IS NOT NULL " +
								"    AND A52_STATUS_UNIT    IS NULL " +
								"    THEN 'UnFreezed' " +
								"  END AS flag, " +
								"  A52_STATUS_CIRCLE, " +
								"  A52_STATUS_UNIT " +
								" FROM FAS_A52_REGISTER_STATUS " +
								" WHERE accounting_unit_id=" +cmbAcc_UnitCode+
								" AND A52_STATUS_CIRCLE  IS NULL " +
								" AND A52_STATUS_UNIT     ='Y'");
						System.out.println("SELECT " +
								"  CASE " +
								"    WHEN A52_STATUS_CIRCLE IS NULL " +
								"    AND A52_STATUS_UNIT     ='Y' " +
								"    THEN 'freeze' " +
								"    WHEN A52_STATUS_CIRCLE IS NOT NULL " +
								"    AND A52_STATUS_UNIT     ='Y' " +
								"    THEN 'Circle_Freezed' " +
								"    WHEN A52_STATUS_CIRCLE IS NOT NULL " +
								"    AND A52_STATUS_UNIT    IS NULL " +
								"    THEN 'UnFreezed' " +
								"  END AS flag, " +
								"  A52_STATUS_CIRCLE, " +
								"  A52_STATUS_UNIT " +
								" FROM FAS_A52_REGISTER_STATUS " +
								" WHERE accounting_unit_id=" +cmbAcc_UnitCode+
								" AND A52_STATUS_CIRCLE  IS NULL " +
								" AND A52_STATUS_UNIT     ='Y'");
						rs0=ps2.executeQuery();
						while(rs0.next()){
							System.out.println(rs0.getString("flag"));
						if(rs0.getString("flag").equalsIgnoreCase("freeze"))
						{
							String queryFind="select RENDERING_UNIT_OFFICE_ID from fas_asset_val_ac_render_units where acct_rendering_unit_id="+cmbAcc_UnitCode;
							ps1=connection.prepareStatement(queryFind);
							rs1=ps1.executeQuery();
							
							if(rs1.next()){

								String delqry="update FAS_A52_REGISTER_STATUS set A52_STATUS_UNIT=? where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND A52_STATUS_CIRCLE is null and A52_STATUS_UNIT='Y'";
								  preparedStatement1 = connection.prepareStatement(delqry);
								  preparedStatement1.setString(1, "");
									preparedStatement1.setInt(2, cmbAcc_UnitCode);
									preparedStatement1.setInt(3, cmbOffice_code);
								
									
			    					count = preparedStatement1.executeUpdate();
									System.out.println("count:" + count);
							}
							if(count>0) {
		                        xml=xml+"<flag>A52success</flag>"; 
		                    }else{
		                    	 xml=xml+"<flag>failure</flag>";
		                    }	
						}else if(rs0.getString("flag").equalsIgnoreCase("Circle_Freezed"))
						{
							 xml=xml+"<flag>Circle_Freezed</flag>"; 
						}else if(rs0.getString("flag").equalsIgnoreCase("UnFreezed"))
						{
							 xml=xml+"<flag>UnFreezed</flag>"; 
						}else {
	                    	 xml=xml+"<flag>failure</flag>";
	                    }	
						/* if(count>0) {
		                        xml=xml+"<flag>success</flag>"; 
		                    }
						  
						
					else{
						xml=xml+"<flag>NotCircle</flag>"; 
					}
				
				}
				
				else{
					xml=xml+"<flag>FreezeFirst</flag>"; 
				}*/
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
						int cmbOffice_code =0;
						String cmbFinancialYear="";
						try{
							cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
							cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
							cmbFinancialYear = request.getParameter("cmbFinancialYear");
						}
						catch(Exception e){
							System.out.println("eeegetng values error");
							
						}
							try {
								int c1=0;
								ps22=connection.prepareStatement("select A52_STATUS_QTY from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' AND ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code+"' AND FINANCIAL_YEAR='"+cmbFinancialYear+"'");
								rss=ps22.executeQuery();
								//System.out.println("inside try");
								System.out.println(cmbAcc_UnitCode+"  "+cmbFinancialYear);
								while(rss.next()){
									System.out.println("record having");
									xml=xml+"<A52_STATUS>"+ rss.getString("A52_STATUS_QTY").trim()+"</A52_STATUS>";
									//xml=xml+"<AA52_STATUS>"+ rss.getString("AA52_STATUS").trim()+"</AA52_STATUS>";
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
		String verifya52aa52=request.getParameter("a52_option");
		System.out.println("verifya52aa52  "+verifya52aa52);
		if(strCommand.equalsIgnoreCase("viewAbstract")){
			
		if(verifya52aa52.equalsIgnoreCase("A52")){
			 JasperDesign jasperDesign = null;
		        File reportFile = null;
		        try {
		           // System.out.println("++++++++A52Abstract ****");
		          //  int AccUnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		           // String finyear =request.getParameter("cmbFinancialYear");
		          //  String rtype = request.getParameter("txtoption");
		           // System.out.println("cmbAcc_UnitCode******"+cmbAcc_UnitCode+"finyear*******"+cmbFinancialYear);
		           
		           reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A52RegisterOBDataEntry_Abs.jasper"));
		                  
		            if (!reportFile.exists())
		                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		            JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());


		            Map map = new HashMap();
		            map.put("accunitid", cmbAcc_UnitCode);
		            map.put("financialyear", cmbFinancialYear);
		            //map.put("assetmajclass", asset_majclass_code);
		         
		            JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);
		            byte buf[] =JasperExportManager.exportReportToPdf(jasperPrint);
	                response.setContentType("application/pdf");
	                response.setContentLength(buf.length);
	                response.setHeader("Content-Disposition","attachment;filename=\"A52Abs.pdf\" ");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
		           // System.out.println("testing***"+jasperPrint);
		            out.close(); 
		           
		           /* String s = request.getContextPath();
		            RequestDispatcher rd = request.getRequestDispatcher(s+"Verify_A52_AA52_Register.jsp");
		            request.setAttribute("msg", "Do u want to contine");
		            rd.forward(request, response);  */

		        } catch (Exception ex) {
		            String connectMsg =
		                "Could not create the report " + ex.getMessage() + " " +
		                ex.getLocalizedMessage();
		            System.out.println(connectMsg);
		        }
		        String ss=request.getParameter("btverifyA52AA52");
		        //System.out.println("button   "+ss);
		       
		       // JOptionPane.showConfirmDialog (null, "Yes or No?");

		       /* int option = JOptionPane.showConfirmDialog (null, "Yes or No?");

		        if (option == JOptionPane.YES_OPTION ) {
		        	System.out.println("yes selcted");
		        	
		        	
		        }
		        else if (option == JOptionPane.NO_OPTION) {
		        	System.out.println("no  selcted");
		        	// JOptionPane.showMessageDialog (null, "This is your answer for NO...'");
		        }
		       
		        else {
		        	System.out.println("cancel selcted");
		        	
		        }*/
		        
		}else if(verifya52aa52.equalsIgnoreCase("AA52")){
		System.out.println("aa52");	
		 JasperDesign jasperDesign = null;
	        File reportFile = null;
	        try {
		 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/AA52OB_DATA_REPORT_Abstract.jasper")); 
         System.out.println("reportFile"+reportFile);   
       if (!reportFile.exists())
       throw new JRRuntimeException("File J not found. The report design must be compiled first.");
       
       JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
      System.out.println("jasperReport"+jasperReport);
       Map map=new HashMap();

       map.put("accountingunitid",cmbAcc_UnitCode);
       map.put("accountofficeid",cmbOffice_code);
       //map.put("cmbassetcode",cmbassetcode);
       map.put("cmbFinancialYear",cmbFinancialYear);        
      
     
       JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
        
       			//System.out.println("PDF:::::::::::");
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

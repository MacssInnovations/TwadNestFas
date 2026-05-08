package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;
import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import Servlets.FAS.FAS1.CommonControls.servlets.Restricted_AccountHead;


import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
public class AccountHead_Verification extends HttpServlet
{
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
    }
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException 
    {
    		System.out.println("AccountHead_Verification doget method testtttttttttttttttttt");
				try {
				  HttpSession session = request.getSession(false);
				  if (session == null) {
				      System.out.println(request.getContextPath() + "/index.jsp");
				      response.sendRedirect(request.getContextPath() + "/index.jsp");
				      return;
				  }
				  System.out.println(session);
				} catch (Exception e) {
				  System.out.println("Redirect Error :" + e);
				}
					int employee_id = 0;
					HttpSession session = request.getSession(false);
					UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
					System.out.println("user id::" + empProfile.getEmployeeId());
					employee_id = empProfile.getEmployeeId();
					int old_id=0;
					
					String fin_year,MajGroup,MinGroup,verified_on,accheaddesc,status = "";
					int accheadcode,verification_by = 0;
					
					
					Connection con = null;
					ResultSet rs = null, rs2 = null, rs3 = null, rsbank = null;
					PreparedStatement ps = null, ps2 = null, ps3 = null, psbank = null;
					Statement stmt = null;
					//String xml="";
					response.setContentType(CONTENT_TYPE);
					response.setHeader("Cache-Control", "no-cache");
					PrintWriter out = response.getWriter();
					String strCommand = "";
					try {
					  ResourceBundle rs1 =
					      ResourceBundle.getBundle("Servlets.Security.servlets.Config");
					  String ConnectionString = "";
					
					  String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
					  String strdsn = rs1.getString("Config.DSN");
					  String strhostname = rs1.getString("Config.HOST_NAME");
					  String strportno = rs1.getString("Config.PORT_NUMBER");
					  String strsid = rs1.getString("Config.SID");
					  String strdbusername = rs1.getString("Config.USER_NAME");
					  String strdbpassword = rs1.getString("Config.PASSWORD");
//					  ConnectionString =
//					          strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//					          ":" + strsid.trim();
					  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
					  Class.forName(strDriver.trim());
					  con =
					DriverManager.getConnection(ConnectionString, strdbusername.trim(),
					                   strdbpassword.trim());
					} catch (Exception e) {
					  System.out.println("Exception in opening connection :" + e);
					  //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
					}

					try {
					  strCommand = request.getParameter("Command");
					  System.out.println("assign..here command..." + strCommand);
					}
					catch (Exception e) {
					  System.out.println("Exception in assigning..." + e);
					}

					 if (strCommand.equalsIgnoreCase("Load_SL_Code") || strCommand.equalsIgnoreCase("Load_MasterSL_Code")) {
					  String CONTENT_TYPE = "text/xml; charset=windows-1252";
					  response.setContentType(CONTENT_TYPE);
					  String xml = "";
					  int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
					  int cmbSL_type = 0;
					  int addtional_field_value = 0;
					  int y = 0;
					  xml = "<response><command>" + strCommand + "</command>";
					 
					  try {
					      cmbSL_type =
					              Integer.parseInt(request.getParameter("cmbSL_type"));
					  } catch (Exception e) {
					      System.out.println("error get SL_type");
					  }
					  
					/*  try {
					      cmbAcc_UnitCode =
					              Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
					  } catch (Exception e) {
					      System.out.println("error get acc unit code");
					  }
					  try {
					      cmbOffice_code =
					              Integer.parseInt(request.getParameter("cmbOffice_code"));
					  } catch (Exception e) {
					      System.out.println("error get office id");
					  }*/
					  System.out.println("SL_TYPE " + cmbSL_type + "  NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
					//  System.out.println(cmbAcc_UnitCode);
					//  System.out.println(cmbOffice_code);
					  xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
					   if (cmbSL_type == 7) {
					      int other_dept_off_alias_id = 0, oid = 0, cmbMas_SL_type = 0;
					      String deptid = "";
					      System.out.println("inside 7 employeees");
					      try {
					
					          try {
					              addtional_field_value =
					                      Integer.parseInt(request.getParameter("addtional_field_value"));
					          } catch (Exception e) {
					              System.out.println("error get addtional_field_value");
					          }
					          try {
					              cmbMas_SL_type =
					                      Integer.parseInt(request.getParameter("cmbMas_SL_type"));
					          } catch (Exception e) {
					              System.out.println("getting master combo sl type failed");
					          }
					          try {
					              other_dept_off_alias_id =
					                      Integer.parseInt(request.getParameter("other_dept_off_alias_id"));
					          } catch (Exception e) {
					              System.out.println("getting master combo slcode failed");
					          }
					          System.out.println("other_dept_off_alias_id.." +
					                             other_dept_off_alias_id);
					          //deptid=request.getParameter("deptid");
					          System.out.println(oid + " " +
					                             deptid); // OTHER_DEPT_ALIAS_ID
					          if (cmbMas_SL_type == 9) {
					              try {
					                  System.out.println("inside other employee");
					                  ps =
					con.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ALIAS_ID=? ");
					                  ps.setInt(1, other_dept_off_alias_id);
					                  rs = ps.executeQuery();
					                  if (rs.next()) {
					                      deptid = rs.getString("OTHER_DEPT_ID").trim();
					                      oid = rs.getInt("OTHER_DEPT_OFFICE_ID");
					                  }
					                  rs.close();
					                  ps.close();
					
					                  ps =
					con.prepareStatement(" select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL ||'-'|| d.DESIGNATION as ENAME" +
					             " from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d " +
					             " where c.DESIGNATION_ID=d.DESIGNATION_ID  and c.EMPLOYEE_ID=e.EMPLOYEE_ID and c.EMPLOYEE_STATUS_ID='DPN' " +
					             " and c.OFFICE_ID=? and c.DEPARTMENT_ID=? and e.EMPLOYEE_ID=?");
					                  ps.setInt(1, oid);
					                  ps.setString(2, deptid);
					                  ps.setInt(3,
					                            addtional_field_value); // later modified
					                  rs = ps.executeQuery();
					
					                  if (rs.next()) {
					                      xml = xml + "<flag>success</flag>";
					                      xml =
					xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
					rs.getString("ENAME") + "</cname>";
					
					                  } else {
					                      xml = xml + "<flag>failure</flag>";
					                  }
					              } catch (Exception e) {
					                  System.out.println("catch..HERE.in load emp code." +
					                                     e);
					                  xml = xml + "<flag>failure</flag>";
					              }
					
					          }
					
					          else {
					              try {
					              System.out.println("::::::addtional_field_value:::::"+addtional_field_value);
					                  // ps=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and e.OFFICE_ID=? and e.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
					                  //ps.setInt(1,cmbOffice_code);
					                  ps =  con.prepareStatement("SELECT e.EMPLOYEE_ID,\n" + 
					                  "  e.EMPLOYEE_NAME\n" + 
					                  "  ||'.'\n" + 
					                  "  ||e.EMPLOYEE_INITIAL AS ENAME,\n" + 
					                  "  'CR'                 AS EMPLOYEE_STATUS_ID\n" + 
					                  " From Hrm_Mst_Employees E\n" + 
					                  " Where E.Employee_Id= ?\n" + 
					                  " ORDER BY e.EMPLOYEE_NAME ");
					/*
					ps =  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c," +
					" HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
					*/
					                  ps.setInt(1, addtional_field_value);
					                  rs = ps.executeQuery();
					
					                  if (rs.next()) {
					                      xml =
					xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
					rs.getString("ENAME") + "</cname>";
					                      xml =
					xml + "<state>" + rs.getString("EMPLOYEE_STATUS_ID") + "</state>";
					                      xml = xml + "<flag>success</flag>";
					                  } else
					                      xml = xml + "<flag>failure</flag>";
					
					                  ps.close();
					                  rs.close();
					              } catch (Exception e) {
					                  System.out.println("catch..HERE.in load emp cod in else part." +
					                                     e);
					                  xml = xml + "<flag>failure</flag>";
					              }
					          }
					      } catch (Exception e) {
					          System.out.println("catch..HERE.in getting request value code." +
					                             e);
					          xml = xml + "<flag>failure</flag>";
					      }
					  }   
					  else {
					      System.out.println("no match found");
					      xml = xml + "<flag>failure</flag>";
					  }
					  System.out.println("match found for Sub-Ledger-Type");
					  xml = xml + "</response>";
					  System.out.println(xml);
					  out.println(xml);
			}
					 else if (strCommand.equalsIgnoreCase("loadlist"))
							 {
								 String CONTENT_TYPE = "text/xml; charset=windows-1252";
								 response.setContentType(CONTENT_TYPE);
								 String xml = "";
								 String financial_year="",MajorGroup="";
								 String MinorGroup ="",mg="";
								 xml = "<response><command>gett</command>";
								 try {
								      financial_year = request.getParameter("fin_year");
								  } catch (Exception e) {
								      System.out.println("error get financial_year");
								  }
								  try {
									  MajorGroup = request.getParameter("MajorGrp");
								  	}
								  catch (Exception e) {
								      System.out.println("error get MajorGroup");
								  }
								  try {
									  MinorGroup = request.getParameter("MinorGrp");
								  }
								  catch (Exception e) {
								      System.out.println("error get MinorGroup");
								  }
								  if(MinorGroup.equals("All"))
								  {
									  mg="";
								  }
								  else
								  {
									  mg=" AND MINOR_GROUP IN(SELECT MINOR_HEAD_DESC FROM COM_MST_MINOR_HEADS WHERE MAJOR_HEAD_CODE ='"+MajorGroup+"' AND minor_head_code='"+MinorGroup+"')";
								  }
								  try
								  {
								  ps3 = con.prepareStatement("select FINANCIAL_YEAR,MAJOR_GROUP,MINOR_GROUP,VERIFICATION_DONE_BY, "+
										  					"to_char(VERIFICATION_DONE_ON,'dd/mm/yyyy')as VERIFICATION_DONE_ON,VERIFIED_STATUS,ACCOUNTHEADCODE,ACCOUNTHEADDESC"+
										  					" from FAS_ACCHEAD_VERIFICATION_LOG where FINANCIAL_YEAR = ? and "+
										  					"MAJOR_GROUP in (select major_head_desc from com_mst_major_heads where major_head_code = ?) "+mg);
//								  ps3 = con.prepareStatement("select * from FAS_ACCHEAD_VERIFICATION_LOG");
								  ps3.setString(1, financial_year);
								  ps3.setString(2, MajorGroup);
							
								  rs3 = ps3.executeQuery();
								  
								  while(rs3.next())
								  {
									  fin_year = rs3.getString("FINANCIAL_YEAR");
									  MajGroup = rs3.getString("MAJOR_GROUP");
									  MinGroup = rs3.getString("MINOR_GROUP");
									  verification_by = rs3.getInt("VERIFICATION_DONE_BY");
					  				  verified_on = rs3.getString("VERIFICATION_DONE_ON");
					  				  status = rs3.getString("VERIFIED_STATUS");
					  				  accheadcode =  rs3.getInt("ACCOUNTHEADCODE");
					  				  accheaddesc = rs3.getString("ACCOUNTHEADDESC");
					  				
					  				xml = xml + "<finyear>"+fin_year+"</finyear>";
					  				xml = xml + "<majgroup>"+MajGroup+"</majgroup>";
					  				xml = xml + "<minrgroup>"+MinGroup+"</minrgroup>";
					  				xml = xml + "<verified_by>"+verification_by+"</verified_by>";
					  				xml = xml + "<verified_on>"+verified_on+"</verified_on>";
					  				xml = xml + "<status>"+status+"</status>";
					  				xml = xml + "<accheadcode>"+accheadcode+"</accheadcode>";
					  				xml = xml + "<accheaddesc>"+accheaddesc+"</accheaddesc>";
					  				xml = xml + "<flag>success</flag>";
								  }
								  xml = xml + "<flag>nodata</flag>";
								  }
								  catch(Exception ee)
								  {
									  System.out.println("Exception in loading list**********"+ee);
									  xml = xml + "<flag>failure</flag>";
								  }
								  xml = xml + "</response>";
								  System.out.println(xml);
								  out.println(xml); 
							 } 
					 //added on Jan2013 by sathya
					 else if (strCommand.equals("MajorMinor")) 
				        {
				            String major = request.getParameter("MajorGroup");
				            String minor = request.getParameter("MinorGroup").trim();
				            String usage = request.getParameter("usagestatus");
				            String sql ="";
				            String xml = "";
				           /* System.out.println("major**********"+major);
				            System.out.println("minor**********"+minor);
				            System.out.println("Usage status *******"+usage);*/
				            if(usage.equalsIgnoreCase("InUse"))
				            {
				                    if (major.equals("All") && minor.equals("All"))  {
				                        System.out.println("from here");
				                        sql =
				        // "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y'  order by ACCOUNT_HEAD_CODE";
				           "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y'   " +
				           " and ACCOUNT_HEAD_CODE not in (select accountheadcode from fas_acchead_verification_log where VERIFIED_STATUS ='Y') order by ACCOUNT_HEAD_CODE";
				                    } else if (!major.equals("All") && minor.equals("All")) {
				                        sql =
//				         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and MAJOR_HEAD_CODE='" +
//				           major + "'  order by ACCOUNT_HEAD_CODE";
				         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and MAJOR_HEAD_CODE='"+major+"' " +
				         " and ACCOUNT_HEAD_CODE not in (select accountheadcode from fas_acchead_verification_log where VERIFIED_STATUS ='Y' and " +
 				         "major_group = (select major_head_desc from com_mst_major_heads where major_head_code = '"+major+"')) order by ACCOUNT_HEAD_CODE";
				                    } else if (major.equals("All") && !minor.equals("All")) {
				                        sql =
//				         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and MINOR_HEAD_CODE='" +
//				           minor + "'  order by ACCOUNT_HEAD_CODE";
				         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and MINOR_HEAD_CODE='"+minor+"' "+
				         "and ACCOUNT_HEAD_CODE not in (select accountheadcode from fas_acchead_verification_log where VERIFIED_STATUS ='Y' and " +
 				         "minor_group = (select MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MINOR_HEAD_CODE = '"+minor+"'))" +
				         "order by ACCOUNT_HEAD_CODE";
				                    } else {
				                        sql =
//				         "select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and MAJOR_HEAD_CODE='" +
//				           major + "' and MINOR_HEAD_CODE='" + minor + "'  order by ACCOUNT_HEAD_CODE";
				                        	"SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE,MINOR_HEAD_CODE,BALANCE_TYPE FROM COM_MST_ACCOUNT_HEADS "+
				                        	" where usage_status ='Y' and major_head_code='"+major+"' and minor_head_code='"+ minor +"' and ACCOUNT_HEAD_CODE not in (select accountheadcode "+
				                        	" from fas_acchead_verification_log where major_group = (select major_head_desc from com_mst_major_heads where major_head_code = '"+major+"') "+
				                        	" and minor_group = (select MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MINOR_HEAD_CODE = '"+ minor +"') and verified_status = 'Y') "+
				                        	" ORDER BY ACCOUNT_HEAD_CODE ";
				                    }
				           }
				            try {
				                int No = 0;
				                stmt = con.createStatement();
				                System.out.println("executing : " + sql);
				               // PreparedStatement ps3;
				                rs = stmt.executeQuery(sql); // Query executed here
				                xml =
				     xml + "<response><command>" + strCommand + "</command><flag>success</flag>";
				                System.out.println("yes error");
				                while (rs.next()) {
				                    xml = xml + "<AHCode_leng>";
				                    xml = xml + "<AHCode>" + rs.getInt("ACCOUNT_HEAD_CODE") + "</AHCode>";
				                    xml = xml + "<AHDesc>" + rs.getString("ACCOUNT_HEAD_DESC") + "</AHDesc>";
				                    //System.out.println("........major.."+rs.getString("MAJOR_HEAD_CODE"));
				                    //System.out.println("........minor.."+rs.getString("MINOR_HEAD_CODE"));
				                    ps2 = con.prepareStatement("select MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
				                    ps2.setString(1, rs.getString("MAJOR_HEAD_CODE"));
				                    ResultSet rs1 = ps2.executeQuery();
				                    if (rs1.next()) {
				                        //
				                        xml = xml + "<Maj_id>" + rs1.getString("MAJOR_HEAD_DESC") + "</Maj_id>";

				                        //System.out.println("hai .. 1"+rs1.getString("MAJOR_HEAD_DESC"));
				                        ps2.close();
				                        rs1.close();
				                    }
				                    ps3 = con.prepareStatement("select MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=? and  MINOR_HEAD_CODE=? ");
				                    ps3.setString(1, rs.getString("MAJOR_HEAD_CODE"));
				                    ps3.setInt(2, rs.getInt("MINOR_HEAD_CODE"));
				                    rs1 = ps3.executeQuery();
				                    if (rs1.next()) {
				                        //
				                        xml = xml + "<Min_id>" + rs1.getString("MINOR_HEAD_DESC") + "</Min_id>";
				                        //System.out.println("hai .. 2"+rs1.getString("MINOR_HEAD_DESC"));
				                        ps3.close();
				                        rs1.close();
				                    }
				                    xml = xml + "<Bal_type>" + rs.getString("BALANCE_TYPE") + "</Bal_type>";
				                    //System.out.println("hai .. 3"+rs.getString("BALANCE_TYPE"));
				                    xml = xml + "</AHCode_leng>";
				                    ++No;
				                   // System.out.println(No);
				                }
				                System.out.println(No);
				                if (No == 0) {
				                    xml = "<response><command>" + strCommand + "</command><flag>failure</flag><Type>strType</Type>";
				                }
				            } catch (SQLException sqle) {
				                System.out.println("error while fetching data " + sqle);
				                xml = "<response><command>" + strCommand + "</command><flag>failure</flag><Type>strType</Type>";
				            }
				            xml = xml + "</response>";
				            out.println(xml);
				            System.out.println(xml);
				         }
                       }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException 
    {
    		System.out.println("AccountHead_Verification Post Method ");
				try {
						HttpSession session = request.getSession(false);
				  if (session == null) {
				      System.out.println(request.getContextPath() + "/index.jsp");
				      response.sendRedirect(request.getContextPath() + "/index.jsp");
				      return;
				  }
				  System.out.println(session);
				} catch (Exception e) {
				  System.out.println("Redirect Error :" + e);
				}
					int employee_id = 0;
					HttpSession session = request.getSession(false);
					UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
					System.out.println("user id::" + empProfile.getEmployeeId());
					employee_id = empProfile.getEmployeeId();
					int old_id=0;
					int flag =0;
					Connection con = null;
					ResultSet rs = null, rs2 = null, rs3 = null, rsbank = null;
					PreparedStatement ps = null, ps2 = null, ps3 = null, psbank = null;
					//String xml="";
					response.setContentType(CONTENT_TYPE);
					response.setHeader("Cache-Control", "no-cache");
					PrintWriter out = response.getWriter();
					String strCommand = "";
					try {
					  ResourceBundle rs1 =
					      ResourceBundle.getBundle("Servlets.Security.servlets.Config");
					  String ConnectionString = "";
					
					  String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
					  String strdsn = rs1.getString("Config.DSN");
					  String strhostname = rs1.getString("Config.HOST_NAME");
					  String strportno = rs1.getString("Config.PORT_NUMBER");
					  String strsid = rs1.getString("Config.SID");
					  String strdbusername = rs1.getString("Config.USER_NAME");
					  String strdbpassword = rs1.getString("Config.PASSWORD");
//					  ConnectionString =
//					          strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//					          ":" + strsid.trim();
					  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
					  Class.forName(strDriver.trim());
					  con =
					DriverManager.getConnection(ConnectionString, strdbusername.trim(),
					                   strdbpassword.trim());
					} catch (Exception e) {
					  System.out.println("Exception in opening connection :" + e);
					  //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
					}

					try {
					  strCommand = request.getParameter("Command");
					  System.out.println("assign..here command..." + strCommand);
					}
					catch (Exception e) {
					  System.out.println("Exception in assigning..." + e);
					}
					if (strCommand.equalsIgnoreCase("Add"))
					{
						String financial_year="",Emp_id = "",verified_date = "";
						int RecordCount = 0;
						try
						{
							financial_year = request.getParameter("cmbAccountHead_FY");
						}
						catch(Exception e)
						{
							System.out.println("Exception in getting finanacial year"+e);
						}
						try
						{
							Emp_id = request.getParameter("txtEmpID_mas");
						}
						catch(Exception e1)
						{
							System.out.println("Exception in getting Emp id"+e1);
						}
						try
						{
							verified_date = request.getParameter("txtverificationDate");
						}
						catch(Exception e2)
						{
							System.out.println("Exception in getting verified_date"+e2);
						}
						try {
							RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
							System.out.println("RecordCountRecordCountRecordCountRecordCount    "+RecordCount);
							} catch (Exception e3) {
							System.out.println("Error Getting Total Number of Records "+e3);
						}
							String userid = (String) session.getAttribute("UserId");
							long l = System.currentTimeMillis();
							Timestamp ts = new Timestamp(l);
							
							String check[] = new String[RecordCount];
							String AccHeadCode[] = new String[RecordCount];
							String AccHeadDesc[] = new String[RecordCount];
							String MajorCode[] = new String[RecordCount];
							String MinorCode[] = new String[RecordCount];
							String BalanceType[] = new String[RecordCount];
							
							int check2 = 0;
							int AccHeadCode2 = 0;
							//int MajorCode2 = 0;
							//int MinorCode2 = 0;
							String Remarks2 = null;String AccHeadDesc2 =null;String BalanceType2 =null;
							String MajorCode2 = null ; String  MinorCode2 = null;
							
							int kk = 0;
							try {
								int k = 0;
								for (k = 0; k < RecordCount; k++) {
									/* Check Box */
									try {
										check[k] = request.getParameter("slno_db1" + k);
										check2 = Integer.parseInt(check[k]);

									} catch (Exception e) {
										System.out.println("Error for getting check -->" + e);
									}
									if (k == check2) {
									try {
										AccHeadCode[k] = request
												.getParameter("AccHeadCode" + k);
										if (AccHeadCode[k] != null) {
											if (AccHeadCode[k].equals("")) {
												AccHeadCode2 = 0;
											} else {
												AccHeadCode2 = Integer
														.parseInt(AccHeadCode[k]);
											}
										} else {
											AccHeadCode2 = 0;
										}
									} catch (Exception e) {
										System.out
												.println("Error for getting Account Head Code  -->"
														+ e);
									}
									try {
										AccHeadDesc[k] = request
												.getParameter("AccHeadDesc" + k);
										if (AccHeadDesc[k] != null) {
											if (AccHeadDesc[k].equals("")) {
												AccHeadDesc2 = null;
											} else {
												AccHeadDesc2 = AccHeadDesc[k];
											}
										} else {
											AccHeadDesc2 = null;
										}
									} catch (Exception e) {
										System.out
												.println("Error for getting Account Head Description  -->"
														+ e);
									}
		
									try {
										MajorCode[k] = request
												.getParameter("MajorCode" + k);
										if (MajorCode[k] != null) {
											if (MajorCode[k].equals("")) {
												MajorCode2 = null;
											} else {
												MajorCode2 = MajorCode[k];
											}
										} else {
											MajorCode2 = null;
										}
									} catch (Exception e) {
										System.out
												.println("Error for getting Major code -->"
														+ e);
									}
									try {
										MinorCode[k] = request
												.getParameter("MinorCode" + k);
										if (MinorCode[k] != null) {
											if (MinorCode[k].equals("")) {
												MinorCode2 = null;
											} else {
												MinorCode2 = MinorCode[k];
											}
										} else {
											MinorCode2 = null;
										}
									} catch (Exception e) {
										System.out
												.println("Error for getting Major code -->"
														+ e);
									}
									try {
										BalanceType[k] = request
												.getParameter("BalanceType" + k);
										if (BalanceType[k] != null) {
											if (BalanceType[k].equals("")) {
												BalanceType2 = null;
											} else {
												BalanceType2 = BalanceType[k];
											}
										} else {
											BalanceType2 = null;
										}
									} catch (Exception e) {
										System.out
												.println("Error for getting Balance Type -->"
														+ e);
									}	
									int Sl_no = 0;
									try
				                    {
				                            String sqlsel="select decode(max(SLNO),null,0,max(SLNO))as SLNO from FAS_ACCHEAD_VERIFICATION_LOG";
				                            ps=con.prepareStatement(sqlsel);
				                            rs=ps.executeQuery();
				                           // System.out.println(sqlsel);
				                            if(rs.next())
				                            {
				                                     Sl_no=rs.getInt("SLNO");
				                            }
				                            Sl_no=Sl_no+1;
				                           // System.out.println("Maximum value of Serial Number is :"+Sl_no);
				                            ps.close();
				                            rs.close();
				                    }
				                    catch(Exception ee) 
				                    {
				                         System.out.println("Exception in getting maximum value of Serial Number :"+ee);    
				                    }
				                    try{
				                    	String sql1= "select * from FAS_ACCHEAD_VERIFICATION_LOG where FINANCIAL_YEAR = ? and MAJOR_GROUP = ?" +
				                    					"and MINOR_GROUP = ? and ACCOUNTHEADCODE = ?" ;
				                    	ps3 = con.prepareStatement(sql1);
				                    	ps3.setString(1, financial_year);
				                    	ps3.setString(2, MajorCode2);
				                    	ps3.setString(3, MinorCode2);
				                    	ps3.setInt(4, AccHeadCode2);
				                    	rs3 = ps3.executeQuery();
				                    	if(rs3.next())
				                    	{
				                    		flag=1;
				                       	}
				                      	System.out.println("flag Value***"+flag);
				                    	if(flag==1)
				                    	{
				                    		sendMessage(response, "The Account Head is Verified already............", "ok");
											con.rollback();
											con.commit();
				                    	}
				                    	else if (flag==0)
				                    	{
				                    		try {
											String sql = "insert into FAS_ACCHEAD_VERIFICATION_LOG(SLNO, FINANCIAL_YEAR, MAJOR_GROUP, MINOR_GROUP, "+ 
														"VERIFICATION_DONE_BY,    VERIFICATION_DONE_ON,    VERIFIED_STATUS,    UPDATED_BY_USERID,	"+
													     "UPDATED_DATE,    ACCOUNTHEADCODE,    ACCOUNTHEADDESC ) "+
													     "values(?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?)";
											
												ps = con.prepareStatement(sql);
												ps.setInt(1, Sl_no);
												ps.setString(2, financial_year);
												ps.setString(3, MajorCode2);
												ps.setString(4, MinorCode2);
												ps.setString(5, Emp_id);
												ps.setString(6, verified_date);
												ps.setString(7, "Y");
												ps.setString(8, userid);
												ps.setTimestamp(9, ts);
												ps.setInt(10, AccHeadCode2);
												ps.setString(11, AccHeadDesc2);
												kk = ps.executeUpdate();
											
				                    			} catch (Exception e) {
																			e.printStackTrace();
																			con.rollback();
																			con.commit();
				                    									}
				                    	}
				                    }
				                    catch(Exception e)
				                    {
				                    	e.printStackTrace();
										con.rollback();
										con.commit();
				                    }

									}

								}
								 System.out.println("Value of kk**********"+kk);
									if (kk <= 0) {
										System.out.println("redirect");
										sendMessage(response, "Account Head Verification Failed..... Try again..... ", "ok");
										con.rollback();
										con.commit();
									} 
							} catch (Exception e) {
								e.printStackTrace();
							}
						    finally
						    {
						        System.out.println("done");
						        sendMessage(response, "Account Head Verification Done Successfully ", "ok");
						        try{con.setAutoCommit(true);  }catch(SQLException sqle){}
						    }
						
					}
    }
    private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (Exception e) {
			System.out.println("error in messenger" + e);
		}
	}
}

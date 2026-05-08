package Servlets.FAS.FAS1.Masters.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import sun.util.calendar.Gregorian;

public class Bank_Account_Details extends HttpServlet {
    private static String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        HttpSession session = request.getSession(false);
        try {

            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        Connection con = null;
        ResultSet rs = null, res = null,rs2=null,rs21=null;
        PreparedStatement ps = null, ps1 = null,ps2=null,ps21=null;
        CallableStatement cs = null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        int cmbBankId = 0, cmbAcc_UnitCode = 0, cmbBranchId = 0;
        String cmbOperation_mode = "", cmbBankAcc_type = "", txtRemarks = "";
        long txtBankAccountNo = 0;
        Date txtOpening_date = null, txtBalance_date = null,txtClosed_date=null;
        double txtDepositAmount = 0, txtBalanceAmount =
            0, txtClosingBal_Amount = 0;
        Calendar c;
        String Account_Status = null;

        //String update_user="x";
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        try {
            cmbBankId = Integer.parseInt(request.getParameter("cmbBankId"));
        } catch (Exception e) {
            System.out.println("Exception to catch bank id ");
        }

        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbAcc_UnitCode ");
        }

        try {
            cmbBranchId =
                    Integer.parseInt(request.getParameter("cmbBranchId"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbBranchId ");
        }


        cmbOperation_mode = request.getParameter("cmbOperation_mode");
        cmbBankAcc_type = request.getParameter("cmbBankAcc_type");

        txtRemarks = request.getParameter("txtRemarks");
        Account_Status = request.getParameter("Account_Status");

        try {
            txtBankAccountNo =
                    Long.parseLong(request.getParameter("txtBankAccountNo"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtBankAccountNo ");
        }

        try {
            txtDepositAmount =
                    Double.parseDouble(request.getParameter("txtDepositAmount"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtDepositAmount ");
        }

        try {
            txtBalanceAmount =
                    Double.parseDouble(request.getParameter("txtBalanceAmount"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtBalanceAmount ");
        }


        try {
            if (!(request.getParameter("txtOpening_date")).equalsIgnoreCase("")) {
                String sd[] =
                    request.getParameter("txtOpening_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtOpening_date = new Date(d.getTime());
                System.out.println("txtOpening_date " + txtOpening_date);
            }
        } catch (Exception e) {
            System.out.println("Exception to catch txtOpening_date ");
        }

        try {
            if (!(request.getParameter("txtBalance_date")).equalsIgnoreCase("")) {
                String sd[] =
                    request.getParameter("txtBalance_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtBalance_date = new Date(d.getTime());
                System.out.println("txtBalance_date " + txtBalance_date);
            }
        } catch (Exception e) {
            System.out.println("Exception to catch txtOpening_date ");
        }
        
        
        
        try {
            if (!(request.getParameter("txtClosed_date")).equalsIgnoreCase("")) {
                String sd[] =
                    request.getParameter("txtClosed_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtClosed_date = new Date(d.getTime());
                System.out.println("txtClosed_date " + txtClosed_date);
            }
        } catch (Exception e) {
            System.out.println("Exception to catch txtOpening_date ");
        }
        

        System.out.println("after getting parameters");

        if (strCommand.equalsIgnoreCase("getBranch")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>getBranch</command>";

            int y = 0;

            try {
                ps =
               //con.prepareStatement("select BRANCH_ID, CITY_TOWN_NAME ||' - '|| BRANCH_NAME as branch_city from FAS_MST_BANK_BRANCHES where BANK_ID=?");
                		con.prepareStatement("select BRANCH_ID, BRANCH_NAME ||','|| coalesce(CITY_TOWN_NAME,'') as branch_city from FAS_MST_BANK_BRANCHES where BANK_ID=?");;

                ps.setInt(1, cmbBankId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
 xml + "<cid>" + rs.getInt("BRANCH_ID") + "</cid><cname>" +
   rs.getString("branch_city") + "</cname>";
                    y++;
                }
                if (y != 0) {
                    xml = xml + "<flag>success</flag>";
                } else
                    xml = xml + "<flag>failure</flag>";

                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("Finding Branch failed due to exception" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } 
        else if(strCommand.equalsIgnoreCase("getType"))
        {
        	String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>getType</command>";
            
            System.out.println("cmbAcc_UnitCode>>>"+cmbAcc_UnitCode);
        	int y=0,x=0;
        	try
            {
        	if(cmbAcc_UnitCode==5)
        	{
        		
                 ps2=con.prepareStatement("select trim(ACCOUNT_TYPE_ID) as acc_type_id, ACCOUNT_TYPE from FAS_MST_BANK_AC_TYPES");
                 rs2=ps2.executeQuery();
                 while(rs2.next())
                 {
                	xml=xml+ "<type>" + rs2.getString("acc_type_id") + "</type>";
                	xml=xml+ "<typedesc>" + rs2.getString("ACCOUNT_TYPE") + "</typedesc>";
                	y++;
                 }
                 ps21=con.prepareStatement("select trim(AC_OPERATIONAL_MODE_ID) as Operational_id, AC_OPERATIONAL_MODE from FAS_MST_AC_OPER_MODES");
                 rs21=ps21.executeQuery();
                 while(rs21.next())
                 {
                	xml=xml+ "<Opr_id>" + rs21.getString("Operational_id") + "</Opr_id>";
                	xml=xml+ "<Mode>" + rs21.getString("AC_OPERATIONAL_MODE") + "</Mode>";
                	y++;
                 }
                   
        	}else
        	{
        		ps2=con.prepareStatement("select trim(ACCOUNT_TYPE_ID) as acc_type_id, ACCOUNT_TYPE from FAS_MST_BANK_AC_TYPES where ACCOUNT_TYPE_ID in ('CA','SB')");
                rs2=ps2.executeQuery();
                while(rs2.next())
                {
               	xml=xml+ "<type>" + rs2.getString("acc_type_id") + "</type>";
               	xml=xml+ "<typedesc>" + rs2.getString("ACCOUNT_TYPE") + "</typedesc>";
               	y++;
                }
                ps21=con.prepareStatement("SELECT trim(AC_OPERATIONAL_MODE_ID) AS Operational_id, " +
		                     " AC_OPERATIONAL_MODE FROM fas_mst_ac_oper_modes " +              		                     
		                     " WHERE AC_OPERATIONAL_MODE_ID IN ('COL','OPR','FDW','OPR-NRDWP-Main','OPR-NRDWP-Support')");
                rs21=ps21.executeQuery();
                while(rs21.next())
                {
                	xml=xml+ "<Opr_id>" + rs21.getString("Operational_id") + "</Opr_id>";
                	xml=xml+ "<Mode>" + rs21.getString("AC_OPERATIONAL_MODE") + "</Mode>";
                	y++;
                }
        	}
        	
        
        	if (y != 0) {
                xml = xml + "<flag>success</flag>";
            } else
                xml = xml + "<flag>failure</flag>";
        	
            } 
            catch(Exception e)
            {
            System.out.println("Exception in Bank Account Type combo..."+e);
            }
        	
        	 xml = xml + "</response>";
             System.out.println(xml);
             out.println(xml);
        	
        }        
        else if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int ac_alias = 0;
            xml = "<response><command>Add</command>";
            System.out.println("add");
            System.out.println(txtRemarks);

            String sql =
                "select  max(BANK_AC_NO_ALIAS_CODE) as max_ac from FAS_MST_BANK_BALANCE";


            try {

                ps1 = con.prepareStatement(sql);
                res = ps1.executeQuery();
                while (res.next()) {
                    ac_alias = res.getInt("max_ac");
                }
                ac_alias++;

                System.out.println("alias code is --->" + ac_alias);

                ps =
  con.prepareStatement("insert into FAS_MST_BANK_BALANCE(ACCOUNTING_UNIT_ID,BANK_ID,BRANCH_ID,BANK_AC_NO,BANK_AC_TYPE_ID," +
                       "AC_OPERATIONAL_MODE_ID,AC_OPENING_DATE,INITIAL_DEPOSIT_AMT,BALANCE_DATE,OPENING_BALANCE,CLOSING_BALANCE,REMARKS,UPDATED_BY_USER_ID ,UPDATED_DATE, BANK_AC_NO_ALIAS_CODE,STATUS,CLOSED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbBankId);
                ps.setInt(3, cmbBranchId);
                ps.setLong(4, txtBankAccountNo);
                ps.setString(5, cmbBankAcc_type);
                ps.setString(6, cmbOperation_mode);
                ps.setDate(7, txtOpening_date);
                ps.setDouble(8, txtDepositAmount);
                ps.setDate(9, txtBalance_date);
                ps.setDouble(10, txtBalanceAmount);
                //ps.setInt(11,sl_number);
                ps.setDouble(11, txtClosingBal_Amount);
                ps.setString(12, txtRemarks);
                ps.setString(13, update_user);
                ps.setTimestamp(14, ts);
                ps.setInt(15, ac_alias);
                ps.setString(16, Account_Status);
                ps.setDate(17, txtClosed_date);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Update")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Update</command>";

            try {
                
                
                cs =
  con.prepareCall("call FAS_CLOSED_BANKS_STATUS_UPDATE(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbBankId);
                cs.setInt(3, cmbBranchId);
                cs.setLong(4, txtBankAccountNo);
                cs.setString(5, cmbBankAcc_type);
                cs.setString(6, cmbOperation_mode);
                cs.setString(7, Account_Status);
                cs.registerOutParameter(8, java.sql.Types.NUMERIC);
                cs.setInt(8, 0);
                cs.execute();
                //int errcode = cs.getInt(8);
                int errcode= cs.getBigDecimal(8).intValue();
System.out.println("Error Code>>>>"+errcode);
                if (errcode == 0) {
//                    ps.executeUpdate();
                	ps =
                			  con.prepareStatement("update FAS_MST_BANK_BALANCE set AC_OPERATIONAL_MODE_ID=?,AC_OPENING_DATE=?,INITIAL_DEPOSIT_AMT=?," +
                			                       "BALANCE_DATE=?,OPENING_BALANCE=?,CLOSING_BALANCE=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?, status=?  " +
                			                   //    ",CLOSED_DATE=?" +
                			                       " where ACCOUNTING_UNIT_ID=? and BANK_ID=? and BRANCH_ID=? and BANK_AC_NO=? and BANK_AC_TYPE_ID=? ");
                			               
                			                
                			                System.out.println("update FAS_MST_BANK_BALANCE set AC_OPERATIONAL_MODE_ID="+cmbOperation_mode+",AC_OPENING_DATE="+txtOpening_date+",INITIAL_DEPOSIT_AMT="+txtDepositAmount+"," +
                			                       "BALANCE_DATE="+txtBalance_date+",OPENING_BALANCE="+txtBalanceAmount+",CLOSING_BALANCE="+txtClosingBal_Amount+",REMARKS="+txtRemarks+",UPDATED_BY_USER_ID="+update_user+",UPDATED_DATE="+ts+", status="+Account_Status+"  " +
                			                       " where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and BANK_ID="+cmbBankId+" and BRANCH_ID="+cmbBranchId+" and BANK_AC_NO="+txtBankAccountNo+" and BANK_AC_TYPE_ID="+cmbBankAcc_type+"");

                			                ps.setString(1, cmbOperation_mode);
                			                ps.setDate(2, txtOpening_date);
                			                ps.setDouble(3, txtDepositAmount);
                			                ps.setDate(4, txtBalance_date);
                			                ps.setDouble(5, txtBalanceAmount);
                			                ps.setDouble(6, txtClosingBal_Amount);
                			                ps.setString(7, txtRemarks);
                			                ps.setString(8, update_user);
                			                ps.setTimestamp(9, ts);
                			                ps.setString(10, Account_Status);
                			             //   ps.setDate(11, txtClosed_date);
                			                ps.setInt(11, cmbAcc_UnitCode);
                			                ps.setInt(12, cmbBankId);
                			                ps.setInt(13, cmbBranchId);
                			                ps.setLong(14, txtBankAccountNo);
                			                ps.setString(15, cmbBankAcc_type);
                	
                	
                	int update=ps.executeUpdate();
                    System.out.println("update::"+update);
                    xml = xml + "<flag>success</flag>";
                } else {
                	con.rollback();
                	xml = xml + "<flag>failure</flag>";
                }
                

                System.out.println(Account_Status);
                System.out.println(errcode);


            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Delete</command>";

            try {
                ps =
  con.prepareStatement("delete from  FAS_MST_BANK_BALANCE " +
                       " where ACCOUNTING_UNIT_ID=? and BANK_ID=? and BRANCH_ID=? and BANK_AC_NO=? and BANK_AC_TYPE_ID=?");


                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbBankId);
                ps.setInt(3, cmbBranchId);
                ps.setLong(4, txtBankAccountNo);
                ps.setString(5, cmbBankAcc_type);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    }
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException {
    	CONTENT_TYPE= "text/html; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);


		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;

		try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

		    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
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
		//System.out.println("User Id is:" + userid);
		try {
			//System.out.println("chk 3");
			if (session == null) {
				//System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("Command");
			//System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		//Connection con=null;
		ResultSet rs=null;
			/* Get Parameters */
			int qcheck1 = 0;
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
		
			String txtStatus="",cmbDocType="",cmbOprMode="",txtoption="",txtUnit="";
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "+ e);
			}
		
			try {
				txtoption= request.getParameter("txtoption");
				txtStatus = request.getParameter("txtStatus");
				
				txtUnit = request.getParameter("txtUnit");
			
				
			} catch (Exception e) {
				System.out.println("Error Not Getting cmbBankAccNo  -->" + e);
			}

			System.out.println("txtStatus "+txtStatus);
String qry="";
System.out.println("txtUnit "+txtUnit);
	
	//	System.out.println("txtoption "+txtoption);
			int count=0;
			  try {

		            File reportFile = null;
		            Map map = null;
	                map = new HashMap();
		            try {
		              //  System.out.println("calling servlet..."+cmbDocType+" qry "+qry+"reportFile"+reportFile);
		               
				if (txtoption.equalsIgnoreCase("office")) {

					if (txtUnit.equalsIgnoreCase("Y")) {
						if (txtStatus.equalsIgnoreCase("All")) {
							qry = "";
						} else {
							qry = "  where STATUS                = '" + txtStatus
									+ "'";
						}
						reportFile = new File(
								getServletContext()
										.getRealPath(
												"/org/FAS/FAS1/Reports/Masters/jasper/UnitBAnkDetails_oFFICEwiseAll.jasper"));
					} else if (txtUnit.equalsIgnoreCase("N")) {
						if (txtStatus.equalsIgnoreCase("All")) {
							qry = "";
						} else {
							qry = "  and STATUS                = '"
									+ txtStatus + "'";
						}
						reportFile = new File(
								getServletContext()
										.getRealPath(
												"/org/FAS/FAS1/Reports/Masters/jasper/UnitBAnkDetails_oFFICEwise.jasper"));
					} else {
						System.out.println("test error office");
					}
					// System.out.println("calling servlet..."+cmbDocType+" qry "+qry+"reportFile"+reportFile+"  txtUnit"+txtUnit);
				}
				if(txtoption.equalsIgnoreCase("module")){
		                	if(txtStatus.equalsIgnoreCase("All")){
                				qry="";
                			}else{
                				qry=" AND STATUS                = '"+txtStatus+"'";
                			}
		                	cmbOprMode = request.getParameter("cmbOprMode");
		                	if(txtUnit.equalsIgnoreCase("Y")){
		                reportFile =
		                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/UnitBAnkDetails_ModulewiseAll.jasper"));
		                	}else if(txtUnit.equalsIgnoreCase("N")){
		                		reportFile =
			                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/UnitBAnkDetails_Modulewise.jasper"));
		                	} else{
		                		 System.out.println("test error Module");
		                	 }
		                map.put("Oper_Mode", cmbOprMode);
		                System.out.println("calling servlet..."+cmbDocType+" qry "+qry+"reportFile"+reportFile+"  txtUnit"+txtUnit);
		                }
		                
		                if (!reportFile.exists())
		                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		                System.out.println("from ...");
		                JasperReport jasperReport =
		                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
		              
		                map.put("unit_id", cmbAcc_UnitCode);
		                map.put("status", txtStatus);
		                map.put("qry", qry);

		                JasperPrint jasperPrint =
		                    JasperFillManager.fillReport(jasperReport, map,
		                                                 connection);
		               // System.out.println("upto");
		                String rtype = "PDF"; // request.getParameter("cmbReportType");
		                System.out.println(rtype);
		                    byte buf[] =
		                        JasperExportManager.exportReportToPdf(jasperPrint);
		                    response.setContentType("application/pdf");
		                    response.setContentLength(buf.length);
		                    //response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
		                    OutputStream out = response.getOutputStream();
		                    out.write(buf, 0, buf.length);
		                    out.close();
		                
			
    }catch (Exception ex) {
        String connectMsg =
            "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
        System.out.println(connectMsg);
        //sendMessage(response,"The Challan Report Creation failed","ok");
   
    }
			  }catch (Exception ex) {
			        String connectMsg =
			            "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
			        System.out.println(connectMsg);
			        //sendMessage(response,"The Challan Report Creation failed","ok");
			   
			    }
    }
}

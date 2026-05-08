package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;


/**
 * Servlet implementation class BRS_ListUnAck
 */
public class BRS_ListUnAck extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_ListUnAck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try 
                {
			ResourceBundle rsb = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
			try 
                        {
				statement = connection.createStatement();
				connection.clearWarnings();
			} 
                        catch (SQLException e) 
                        {
				System.out.println("Exception in creating statement:" + e);
			}
		} 
                catch (Exception e) 
                {
			System.out.println("Exception in openeing connection:" + e);
		}
		System.out.println("chk 2");
		String strCommand = "";
		String xml = "";
		HttpSession session = request.getSession(false);
		try 
                {
			if (session == null) 
                        {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			System.out.println(session);

		} 
                catch (Exception e) 
                {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try 
                {
			System.out.println("chk 3");
			if (session == null) 
                        {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} 
                catch (Exception e) 
                {
			System.out.println("Redirect Error :" + e);
		}
		try 
                {
			strCommand = request.getParameter("command");
			System.out.println(strCommand);
		} catch (Exception e) 
                {
			e.printStackTrace();
		}
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		if (strCommand.equalsIgnoreCase("ListAll")) 
                {
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();

			xml = xml + "<response><command>ListAll</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			System.out.println("cmbAcc_UnitCode------>"+cmbAcc_UnitCode);
                        int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                        System.out.println("cmbOffice_code------>"+cmbOffice_code);
                        int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                        System.out.println("txtCB_Year------>"+txtCB_Year);
                        int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                        System.out.println("txtCB_Month------>"+txtCB_Month);
			long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
                        System.out.println("cmbBankAccNo------>"+cmbBankAccNo);
			
			String RemitanceDate=null;
			String WithdrawlDate=null;
			
			String Stringdate=null;
			String Stringdate1=null;
			Double crAmount = 0.00;
			Double drAmount = 0.00;
			
			try 
                        {
				ps = connection.prepareStatement("select SL_NO,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and Doc_Type!='J' ORDER BY REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO");
				ps.setInt(1,cmbAcc_UnitCode );
				ps.setInt(2,cmbOffice_code );
				ps.setInt(3,txtCB_Year );
				ps.setInt(4,txtCB_Month );
				ps.setLong(5,cmbBankAccNo );				
				results = ps.executeQuery();
				
				while(results.next()) 
                                {
					int slno=results.getInt("SL_NO");
                                        Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
					Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
					//Date EntryDate1=results.getDate("PASSBOOK_DATE");					
                                        try 
                                        {
                                                Stringdate = RemitanceDate1.toString();
                                        } 
                                        catch (Exception e) 
                                        {
                                                Stringdate = "0000-00-00";
                                        }
                                        try 
                                        {
                                                Stringdate1 = WithdrawlDate1.toString();
                                        } 
                                        catch (Exception e) 
                                        {
                                                Stringdate1 = "0000-00-00";
                                        }
                                       

					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					
										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
                                        if(month>=10)
                                        {
                                                        RemitanceDate=(day+"/"+month+"/"+year);
                                        }
                                        else
                                        {
                                                RemitanceDate=(day+"/0"+month+"/"+year);	
                                        }
                                                
                                        if(month1>=10)
                                        {
                                                        WithdrawlDate=(day1+"/"+month1+"/"+year1);
                                        }
                                        else
                                        {
                                                WithdrawlDate=(day1+"/0"+month1+"/"+year1);	
                                        }
                                          
                                                xml = xml + "<SerialNumber>" + slno   + "</SerialNumber>";
                                                xml = xml + "<RemitanceDate>" + RemitanceDate   + "</RemitanceDate>";
						xml = xml + "<WithdrawlDate>" + WithdrawlDate   + "</WithdrawlDate>";
						xml = xml + "<Voucher_or_ChallanNo>"+ results.getInt("VOUCHER_OR_CHALLAN_NO")   + "</Voucher_or_ChallanNo>";
						xml = xml + "<Cheqe_or_DDNo>"+ results.getInt("CHEQUE_DD_NO")   + "</Cheqe_or_DDNo>";
						//xml = xml + "<CRAmount>" + results.getFloat("CR_AMOUNT") + "</CRAmount>";
                                                //xml = xml + "<DRAmount>" + results.getFloat("DR_AMOUNT")+ "</DRAmount>";
						 xml = xml + "<CRAmount>" + results.getString("CR_AMOUNT") + "</CRAmount>";
						xml = xml + "<DRAmount>" + results.getString("DR_AMOUNT")+ "</DRAmount>";
						crAmount += results.getDouble("CR_AMOUNT");
						drAmount += results.getDouble("DR_AMOUNT");
					
                                }
							xml = xml + "<crAmount>"+crAmount+"</crAmount>";
							xml = xml + "<drAmount>"+drAmount+"</drAmount>";
                            xml = xml + "<flag>success</flag>";                            
                        }
			catch (Exception e) 
                        {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		}
		
		if (strCommand.equalsIgnoreCase("ListAll_prev")) 
        {
	response.setContentType(CONTENT_TYPE);
	PrintWriter out = response.getWriter();

	xml = xml + "<response><command>ListAll</command>";
	int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	System.out.println("cmbAcc_UnitCode------>"+cmbAcc_UnitCode);
                int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                System.out.println("cmbOffice_code------>"+cmbOffice_code);
                int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                System.out.println("txtCB_Year------>"+txtCB_Year);
                int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                System.out.println("txtCB_Month------>"+txtCB_Month);
	long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
                System.out.println("cmbBankAccNo------>"+cmbBankAccNo);
	
	String RemitanceDate=null;
	String WithdrawlDate=null;
	
	String Stringdate=null;
	String Stringdate1=null;
	Double crAmount = 0.00;
	Double drAmount = 0.00;
	
	try 
                {
		String ss="select SL_NO,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? And ((Cashbook_Year           <"+txtCB_Year+") Or (Cashbook_Year           =? And Cashbook_Month          <=? )) and ACCOUNT_NO=? and Doc_Type!='J'";
	//	" Union All "+
	//	" Select Sl_No,Remittance_Date,Withdrawal_Date,Voucher_Or_Challan_No,Cheque_Dd_No,Cr_Amount,Dr_Amount From Fas_Brs_Transaction "+
	//	 " Where Accounting_Unit_Id                ="+cmbAcc_UnitCode+" And Accounting_For_Office_Id            ="+cmbOffice_code+" And (Cashbook_Month                     ="+txtCB_Month+
	//	 " And Extract (Month From Passbook_Date )!="+txtCB_Month+") And Cashbook_Year                      <="+txtCB_Year+" And Account_No                          ="+cmbBankAccNo+
	//	 " AND Twad_Or_Non_Twad                    ='T' And Doc_Type!               ='J' ORDER BY REMITTANCE_DATE,VOUCHER_OR_CHALLAN_NO ";
		System.out.println("ss:::"+ss);
		ps = connection.prepareStatement(ss);
		ps.setInt(1,cmbAcc_UnitCode );
		ps.setInt(2,cmbOffice_code );
		ps.setInt(3,txtCB_Year );
		ps.setInt(4,txtCB_Month );
		ps.setLong(5,cmbBankAccNo );				
		results = ps.executeQuery();
		
		while(results.next()) 
                        {
			int slno=results.getInt("SL_NO");
                                Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
			Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
			//Date EntryDate1=results.getDate("PASSBOOK_DATE");					
                                try 
                                {
                                        Stringdate = RemitanceDate1.toString();
                                } 
                                catch (Exception e) 
                                {
                                        Stringdate = "0000-00-00";
                                }
                                try 
                                {
                                        Stringdate1 = WithdrawlDate1.toString();
                                } 
                                catch (Exception e) 
                                {
                                        Stringdate1 = "0000-00-00";
                                }
                               

			String[] ddd = Stringdate.split("-");
			String[] ddd1 = Stringdate1.split("-");
			
								
			int day =Integer.parseInt(ddd[2]);
			int month =Integer.parseInt(ddd[1]);
			int year = Integer.parseInt(ddd[0]);
			
			int day1 =Integer.parseInt(ddd1[2]);
			int month1 =Integer.parseInt(ddd1[1]);
			int year1 = Integer.parseInt(ddd1[0]);
			
                                if(month>=10)
                                {
                                                RemitanceDate=(day+"/"+month+"/"+year);
                                }
                                else
                                {
                                        RemitanceDate=(day+"/0"+month+"/"+year);	
                                }
                                        
                                if(month1>=10)
                                {
                                                WithdrawlDate=(day1+"/"+month1+"/"+year1);
                                }
                                else
                                {
                                        WithdrawlDate=(day1+"/0"+month1+"/"+year1);	
                                }
                                  
                                        xml = xml + "<SerialNumber>" + slno   + "</SerialNumber>";
                                        xml = xml + "<RemitanceDate>" + RemitanceDate   + "</RemitanceDate>";
				xml = xml + "<WithdrawlDate>" + WithdrawlDate   + "</WithdrawlDate>";
				xml = xml + "<Voucher_or_ChallanNo>"+ results.getInt("VOUCHER_OR_CHALLAN_NO")   + "</Voucher_or_ChallanNo>";
				xml = xml + "<Cheqe_or_DDNo>"+ results.getInt("CHEQUE_DD_NO")   + "</Cheqe_or_DDNo>";
				//xml = xml + "<CRAmount>" + results.getFloat("CR_AMOUNT") + "</CRAmount>";
                                        //xml = xml + "<DRAmount>" + results.getFloat("DR_AMOUNT")+ "</DRAmount>";
				 xml = xml + "<CRAmount>" + results.getString("CR_AMOUNT") + "</CRAmount>";
				xml = xml + "<DRAmount>" + results.getString("DR_AMOUNT")+ "</DRAmount>";
				crAmount += results.getDouble("CR_AMOUNT");
				drAmount += results.getDouble("DR_AMOUNT");
			
                        }
					xml = xml + "<crAmount>"+crAmount+"</crAmount>";
					xml = xml + "<drAmount>"+drAmount+"</drAmount>";
                    xml = xml + "<flag>success</flag>";                            
                }
	catch (Exception e) 
                {
		xml = xml + "<flag>failure</flag>";
		e.printStackTrace();
	}
	xml = xml + "</response>";
	out.write(xml);
	System.out.println(xml);
}
                else if (strCommand.equalsIgnoreCase("ListAllPassSheet")) 
                {
                	response.setContentType(CONTENT_TYPE);
            		PrintWriter out = response.getWriter();

			xml = xml + "<response><command>ListAll</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			System.out.println("cmbAcc_UnitCode------>"+cmbAcc_UnitCode);
                        int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                        System.out.println("cmbOffice_code------>"+cmbOffice_code);
                        int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                        System.out.println("txtCB_Year------>"+txtCB_Year);
                        int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                        System.out.println("txtCB_Month------>"+txtCB_Month);
			long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
                        System.out.println("cmbBankAccNo------>"+cmbBankAccNo);
			
			String RemitanceDate=null;
			String WithdrawlDate=null;
			
			String Stringdate=null;
			String Stringdate1=null;
			Double crAmount = 0.00;
			Double drAmount = 0.00;
			
			try 
                        {
				ps = connection.prepareStatement("select SL_NO,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and extract (Year From Passbook_Date) =? and extract (MONTH FROM PASSBOOK_DATE)=? and ACCOUNT_NO=? and Doc_Type!='J' ORDER BY REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO");
				ps.setInt(1,cmbAcc_UnitCode );
				ps.setInt(2,cmbOffice_code );
				ps.setInt(3,txtCB_Year );
				ps.setInt(4,txtCB_Month );
				ps.setLong(5,cmbBankAccNo );				
				results = ps.executeQuery();
				
				while(results.next()) 
                                {
					int slno=results.getInt("SL_NO");
                                        Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
					Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
					//Date EntryDate1=results.getDate("PASSBOOK_DATE");					
                                        try 
                                        {
                                                Stringdate = RemitanceDate1.toString();
                                        } 
                                        catch (Exception e) 
                                        {
                                                Stringdate = "0000-00-00";
                                        }
                                        try 
                                        {
                                                Stringdate1 = WithdrawlDate1.toString();
                                        } 
                                        catch (Exception e) 
                                        {
                                                Stringdate1 = "0000-00-00";
                                        }
                                       

					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					
										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
                                        if(month>=10)
                                        {
                                                        RemitanceDate=(day+"/"+month+"/"+year);
                                        }
                                        else
                                        {
                                                RemitanceDate=(day+"/0"+month+"/"+year);	
                                        }
                                                
                                        if(month1>=10)
                                        {
                                                        WithdrawlDate=(day1+"/"+month1+"/"+year1);
                                        }
                                        else
                                        {
                                                WithdrawlDate=(day1+"/0"+month1+"/"+year1);	
                                        }
                                          
                                                xml = xml + "<SerialNumber>" + slno   + "</SerialNumber>";
                                                xml = xml + "<RemitanceDate>" + RemitanceDate   + "</RemitanceDate>";
						xml = xml + "<WithdrawlDate>" + WithdrawlDate   + "</WithdrawlDate>";
						xml = xml + "<Voucher_or_ChallanNo>"+ results.getInt("VOUCHER_OR_CHALLAN_NO")   + "</Voucher_or_ChallanNo>";
						xml = xml + "<Cheqe_or_DDNo>"+ results.getInt("CHEQUE_DD_NO")   + "</Cheqe_or_DDNo>";
						//xml = xml + "<CRAmount>" + results.getFloat("CR_AMOUNT") + "</CRAmount>";
                                                //xml = xml + "<DRAmount>" + results.getFloat("DR_AMOUNT")+ "</DRAmount>";
						 xml = xml + "<CRAmount>" + results.getString("CR_AMOUNT") + "</CRAmount>";
						xml = xml + "<DRAmount>" + results.getString("DR_AMOUNT")+ "</DRAmount>";
						crAmount += results.getDouble("CR_AMOUNT");
						drAmount += results.getDouble("DR_AMOUNT");
					
                                }
							xml = xml + "<crAmount>"+crAmount+"</crAmount>";
							xml = xml + "<drAmount>"+drAmount+"</drAmount>";
                            xml = xml + "<flag>success</flag>";                            
                        }
			catch (Exception e) 
                        {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.write(xml);
			System.out.println(xml);
		}
	
            else if (strCommand.equalsIgnoreCase("ListAll_acknowledged")) 
            {
            	response.setContentType(CONTENT_TYPE);
        		PrintWriter out = response.getWriter();

                    xml = xml + "<response><command>ListAll</command>";
                    int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                    System.out.println("cmbAcc_UnitCode------>"+cmbAcc_UnitCode);
                    int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
                    System.out.println("cmbOffice_code------>"+cmbOffice_code);
                    int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
                    System.out.println("txtCB_Year------>"+txtCB_Year);
                    int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
                    System.out.println("txtCB_Month------>"+txtCB_Month);
                    long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
                    System.out.println("cmbBankAccNo------>"+cmbBankAccNo);
                    
                    String RemitanceDate=null;
                    String WithdrawlDate=null;
                    
                    String Stringdate=null;
                    String Stringdate1=null;
                    Double crAmount = 0.00;
                    Double drAmount = 0.00;
                    
                    try 
                    {
                            ps = connection.prepareStatement("select SL_NO,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and extract (Year From Passbook_Date) =? and extract (MONTH FROM PASSBOOK_DATE)=? AND CASHBOOK_YEAR <="+txtCB_Year+" AND CASHBOOK_MONTH  <"+txtCB_Month+" and ACCOUNT_NO=? and Doc_Type!='J' ORDER BY REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO");
                            ps.setInt(1,cmbAcc_UnitCode );
                            ps.setInt(2,cmbOffice_code );
                            ps.setInt(3,txtCB_Year );
                            ps.setInt(4,txtCB_Month );
                            ps.setLong(5,cmbBankAccNo );                             
                            results = ps.executeQuery();
                            
                            while(results.next()) 
                            {
                                    int slno=results.getInt("SL_NO");
                                    Date RemitanceDate1=results.getDate("REMITTANCE_DATE");
                                    Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
                                    //Date EntryDate1=results.getDate("PASSBOOK_DATE");                                     
                                    try 
                                    {
                                            Stringdate = RemitanceDate1.toString();
                                    } 
                                    catch (Exception e) 
                                    {
                                            Stringdate = "0000-00-00";
                                    }
                                    try 
                                    {
                                            Stringdate1 = WithdrawlDate1.toString();
                                    } 
                                    catch (Exception e) 
                                    {
                                            Stringdate1 = "0000-00-00";
                                    }
                                   

                                    String[] ddd = Stringdate.split("-");
                                    String[] ddd1 = Stringdate1.split("-");
                                    
                                                                            
                                    int day =Integer.parseInt(ddd[2]);
                                    int month =Integer.parseInt(ddd[1]);
                                    int year = Integer.parseInt(ddd[0]);
                                    
                                    int day1 =Integer.parseInt(ddd1[2]);
                                    int month1 =Integer.parseInt(ddd1[1]);
                                    int year1 = Integer.parseInt(ddd1[0]);
                                    
                                    if(month>=10)
                                    {
                                                    RemitanceDate=(day+"/"+month+"/"+year);
                                    }
                                    else
                                    {
                                            RemitanceDate=(day+"/0"+month+"/"+year);        
                                    }
                                            
                                    if(month1>=10)
                                    {
                                                    WithdrawlDate=(day1+"/"+month1+"/"+year1);
                                    }
                                    else
                                    {
                                            WithdrawlDate=(day1+"/0"+month1+"/"+year1);     
                                    }
                                      
                                            xml = xml + "<SerialNumber>" + slno   + "</SerialNumber>";
                                            xml = xml + "<RemitanceDate>" + RemitanceDate   + "</RemitanceDate>";
                                            xml = xml + "<WithdrawlDate>" + WithdrawlDate   + "</WithdrawlDate>";
                                            xml = xml + "<Voucher_or_ChallanNo>"+ results.getInt("VOUCHER_OR_CHALLAN_NO")   + "</Voucher_or_ChallanNo>";
                                            xml = xml + "<Cheqe_or_DDNo>"+ results.getInt("CHEQUE_DD_NO")   + "</Cheqe_or_DDNo>";
                                            //xml = xml + "<CRAmount>" + results.getFloat("CR_AMOUNT") + "</CRAmount>";
                                            //xml = xml + "<DRAmount>" + results.getFloat("DR_AMOUNT")+ "</DRAmount>";
                                             xml = xml + "<CRAmount>" + results.getString("CR_AMOUNT") + "</CRAmount>";
                                            xml = xml + "<DRAmount>" + results.getString("DR_AMOUNT")+ "</DRAmount>";
                                            crAmount += results.getDouble("CR_AMOUNT");
                                            drAmount += results.getDouble("DR_AMOUNT");
                                    
                            }
                                                    xml = xml + "<crAmount>"+crAmount+"</crAmount>";
                                                    xml = xml + "<drAmount>"+drAmount+"</drAmount>";
                        xml = xml + "<flag>success</flag>";                            
                    }
                    catch (Exception e) 
                    {
                            xml = xml + "<flag>failure</flag>";
                            e.printStackTrace();
                    }
                    xml = xml + "</response>";
            		out.write(xml);
            		System.out.println(xml);
            }else if(strCommand.equalsIgnoreCase("printAll")){
    	    	try{
            		String s = "",myQry="";
            		int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        			long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));        			
            		myQry="SELECT a.SL_NO                                   AS SL_NO, " +
            		"  a.ACCOUNTING_UNIT_ID                                 AS ACCOUNTING_UNIT_ID, " +
            		"  a.ACCOUNTING_FOR_OFFICE_ID                           AS ACCOUNTING_FOR_OFFICE_ID, " +
            		"  DECODE(a.REMITTANCE_DATE,NULL,'-',a.REMITTANCE_DATE) AS REMITTANCE_DATE, " +
            		"  DECODE(a.WITHDRAWAL_DATE,NULL,'-',a.WITHDRAWAL_DATE) AS WITHDRAWAL_DATE, " +
            		"  a.VOUCHER_OR_CHALLAN_NO                              AS VOUCHER_OR_CHALLAN_NO, " +
            		"  a.CHEQUE_DD_NO                                       AS CHEQUE_DD_NO, " +
            		"  a.CR_AMOUNT                                          AS CR_AMOUNT, " +
            		"  a.DR_AMOUNT                                          AS DR_AMOUNT, " +
            		"  b.accounting_unit_name                               AS accounting_unit_name, " +
            		"  c.OFFICE_NAME                                        AS OFFICE_NAME " +
            		"FROM " +
            		"  (SELECT ACCOUNTING_UNIT_ID, " +
            		"    ACCOUNTING_FOR_OFFICE_ID, " +
            		"    SL_NO, " +
            		"    TO_CHAR(REMITTANCE_DATE,'dd/MM/yyyy') AS REMITTANCE_DATE, " +
            		"    TO_CHAR(WITHDRAWAL_DATE,'dd/MM/yyyy') AS WITHDRAWAL_DATE, " +
            		"    VOUCHER_OR_CHALLAN_NO, " +
            		"    CHEQUE_DD_NO, " +
            		"    CR_AMOUNT, " +
            		"    DR_AMOUNT " +
            		"  FROM FAS_BRS_TRANSACTION_NOENTRY " +
            		"  WHERE ACCOUNTING_UNIT_ID    ='"+cmbAcc_UnitCode+"' " +
            		"  AND ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code+"' " +
            		"  AND CASHBOOK_YEAR           ='"+txtCB_Year+"' " +
            		"  AND CASHBOOK_MONTH          ='"+txtCB_Month+"' " +
            		"  AND ACCOUNT_NO              ='"+cmbBankAccNo+"' " +
            		"  AND Doc_Type!               ='J' " +
            		"  )a " +
            		"LEFT OUTER JOIN " +
            		"  (SELECT ACCOUNTING_UNIT_ID, accounting_unit_name FROM fas_mst_acct_units " +
            		"  ) b " +
            		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
            		"LEFT OUTER JOIN " +
            		"  ( SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
            		"  )c " +
            		"ON a.ACCOUNTING_FOR_OFFICE_ID=c.OFFICE_ID " +
            		"ORDER BY a.REMITTANCE_DATE, " +
            		"  a.WITHDRAWAL_DATE, " +
            		"  a.VOUCHER_OR_CHALLAN_NO";
            		s=request.getRealPath("/org/FAS/FAS1/BRS/jaspers/BRSListUnAck.jrxml");
            		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
            		JRDesignQuery query=new JRDesignQuery();
            		query.setText(myQry);
            		jasperDesign.setQuery(query);
            		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
            		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, null, connection);
            		ByteArrayOutputStream bout=new ByteArrayOutputStream();
    		        OutputStream os=response.getOutputStream();
    		        response.setContentType("application/pdf");
    		        response.setHeader ("Content-Disposition", "attachment;filename=\"Brs_UnAck_List.pdf\"");
    		        os.write(JasperManager.printReportToPdf(jasperPrint));
    		        os.close();
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
    	    }
		
        }
}

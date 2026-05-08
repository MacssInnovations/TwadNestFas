package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BRS_FollupActionNotTaken extends HttpServlet 
{
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
    
    public BRS_FollupActionNotTaken() 
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
        {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		PreparedStatement ps = null;
               // PreparedStatement ps1 = null;
                //ResultSet rs1 = null;
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
		
		if (strCommand.equalsIgnoreCase("ListAll")) 
                {

			xml = xml + "<response><command>ListAll</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
			int cmbBankAccNo = Integer.parseInt(request
					.getParameter("cmbBankAccNo"));
			String txtFromDate = request.getParameter("txtFromDate");
			String txtToDate = request.getParameter("txtToDate");

			String FromDate = null;
			String ToDate = null;
			String m = null;
			String m1 = null;
			String[] dd = txtFromDate.split("/");
			String[] dd1 = txtToDate.split("/");

			String dayF = dd[1];
			int monthF = Integer.parseInt(dd[0]);
			String yearF = (dd[2]).substring(2);

			String dayT1 = dd1[1];
			int monthT1 = Integer.parseInt(dd1[0]);
			String yearT1 = (dd1[2]).substring(2);

			if (monthF == 01) {
				m = "JAN";
			} else if (monthF == 02) {
				m = "FEB";
			} else if (monthF == 03) {
				m = "MAR";
			} else if (monthF == 04) {
				m = "APR";
			} else if (monthF == 05) {
				m = "MAY";
			} else if (monthF == 06) {
				m = "JUN";
			} else if (monthF == 07) {
				m = "JUL";
			} else if (monthF == 8) {
				m = "AUG";
			} else if (monthF == 9) {
				m = "SEP";
			} else if (monthF == 10) {
				m = "OCT";
			} else if (monthF == 11) {
				m = "NOV";
			} else if (monthF == 12) {
				m = "DEC";
			}

			if (monthT1 == 01) {
				m1 = "JAN";
			} else if (monthT1 == 02) {
				m1 = "FEB";
			} else if (monthT1 == 03) {
				m1 = "MAR";
			} else if (monthT1 == 04) {
				m1 = "APR";
			} else if (monthT1 == 05) {
				m1 = "MAY";
			} else if (monthT1 == 06) {
				m1 = "JUN";
			} else if (monthT1 == 07) {
				m1 = "JUL";
			} else if (monthT1 == 8) {
				m1 = "AUG";
			} else if (monthT1 == 9) {
				m1 = "SEP";
			} else if (monthT1 == 10) {
				m1 = "OCT";
			} else if (monthT1 == 11) {
				m1 = "NOV";
			} else if (monthT1 == 12) {
				m1 = "DEC";
			}
			FromDate = (dayF + "-" + m + "-" + yearF);
			ToDate = (dayT1 + "-" + m1 + "-" + yearT1);

			String EntryDate=null;
			String RemittanceDate=null;
                        String WithdrawlDate=null;
                        String DocDate=null;
			
                        String Stringdate=null;
			String Stringdate1=null;
                        String Stringdate2=null;
                        String Stringdate3=null;
                        
                        int count=0;
			try 
                        {
				                           
                                ps = connection.prepareStatement("select * from (select * from (select SL_NO,ENTRY_DATE,TWAD_OR_NON_TWAD,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,REMITTANCE_DATE,WITHDRAWAL_DATE,DOC_DATE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and FOLLOW_UP_ACTION_REQUIRED='Y' and TWAD_OR_NON_TWAD='T')X left outer join (select SL_NO as slno,DOC_NO as docno,DOC_TYPE as doctype,ACTION_NO,ACTION_DATE,ACTION_TAKEN from FAS_BRS_FOLLOWUP where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and ACTION_DATE >= '"
								+ FromDate
								+ "' and ACTION_DATE <= '"
								+ ToDate
								+ "' and TWAD_OR_NON_TWAD='T')Y on X.SL_NO = Y.slno union select * from (select SL_NO,ENTRY_DATE,TWAD_OR_NON_TWAD,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,REMITTANCE_DATE,WITHDRAWAL_DATE,DOC_DATE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and FOLLOW_UP_ACTION_REQUIRED='Y' and TWAD_OR_NON_TWAD='NT')X left outer join (select SL_NO as slno,DOC_NO as docno,DOC_TYPE as doctype,ACTION_NO,ACTION_DATE,ACTION_TAKEN from FAS_BRS_FOLLOWUP where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and ACTION_DATE >= '"
								+ FromDate
								+ "' and ACTION_DATE <= '"
								+ ToDate
								+ "' and TWAD_OR_NON_TWAD='NT')Y on X.SL_NO = Y.slno) where ACTION_TAKEN is null");
				ps.setInt(1,cmbAcc_UnitCode );
				ps.setInt(2,cmbOffice_code );
				ps.setInt(3,txtCB_Year );
				ps.setInt(4,txtCB_Month );
				ps.setInt(5, cmbBankAccNo);
				ps.setInt(6, cmbAcc_UnitCode);
				ps.setInt(7, cmbOffice_code);
				ps.setInt(8, txtCB_Year);
				ps.setInt(9, txtCB_Month);
				ps.setInt(10, cmbBankAccNo);
				ps.setInt(11, cmbAcc_UnitCode);
				ps.setInt(12, cmbOffice_code);
				ps.setInt(13, txtCB_Year);
				ps.setInt(14, txtCB_Month);
				ps.setInt(15, cmbBankAccNo);
				ps.setInt(16, cmbAcc_UnitCode);
				ps.setInt(17, cmbOffice_code);
				ps.setInt(18, txtCB_Year);
				ps.setInt(19, txtCB_Month);
				ps.setInt(20, cmbBankAccNo);
				results = ps.executeQuery();
				
				while(results.next()) 
                                {
					int slno=results.getInt("SL_NO");
                                        Date EntryDate1=results.getDate("ENTRY_DATE");
					Date RemittanceDate1=results.getDate("REMITTANCE_DATE");
                                        Date WithdrawlDate1=results.getDate("WITHDRAWAL_DATE");
                                        Date DocDate1=results.getDate("DOC_DATE");
                                        try 
                                        {
                                                Stringdate = EntryDate1.toString();
                                        } 
                                        catch (Exception e) 
                                        {
                                                Stringdate = "0000-00-00";
                                        }
                                        try 
                                        {
                                                Stringdate1 = RemittanceDate1.toString();
                                        } 
                                        catch (Exception e) 
                                        {
                                                Stringdate1 = "0000-00-00";
                                        }
                                        try 
                                        {
                                                Stringdate2= WithdrawlDate1.toString();
                                        } 
                                        catch (Exception e) 
                                        {
                                                Stringdate2 = "0000-00-00";
                                        }
                                        try 
                                        {
                                                Stringdate3 = DocDate1.toString();
                                        } 
                                        catch (Exception e) 
                                        {
                                                Stringdate3 = "0000-00-00";
                                        }
                                        
                                                                              
					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
                                        String[] ddd2 = Stringdate2.split("-");
                                        String[] ddd3 = Stringdate3.split("-");
                                       										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
                                    
                                        int day2 =Integer.parseInt(ddd2[2]);
                                        int month2 =Integer.parseInt(ddd2[1]);
                                        int year2 = Integer.parseInt(ddd2[0]);
                                        
                                        int day3 =Integer.parseInt(ddd3[2]);
                                        int month3 =Integer.parseInt(ddd3[1]);
                                        int year3 = Integer.parseInt(ddd3[0]);
					
                                        if(month>=10)
                                        {
                                                        EntryDate=(day+"/"+month+"/"+year);
                                        }
                                        else
                                        {
                                                EntryDate=(day+"/0"+month+"/"+year);	
                                        }
                                                
                                        if(month1>=10)
                                        {
                                                        RemittanceDate=(day1+"/"+month1+"/"+year1);
                                        }
                                        else
                                        {
                                                RemittanceDate=(day1+"/0"+month1+"/"+year1);	
                                        }
                                        if(month>=10)
                                        {
                                                        WithdrawlDate=(day2+"/"+month2+"/"+year2);
                                        }
                                        else
                                        {
                                                WithdrawlDate=(day2+"/0"+month2+"/"+year2);    
                                        }
                                                
                                        if(month1>=10)
                                        {
                                                        DocDate=(day3+"/"+month3+"/"+year3);
                                        }
                                        else
                                        {
                                                DocDate=(day3+"/0"+month3+"/"+year3);        
                                        }
                                            
                                        
                                        
                                                                                     
                                                xml = xml + "<SerialNumber>" + slno   + "</SerialNumber>";
                                                xml = xml + "<EntryDate>" + EntryDate    + "</EntryDate>";
						xml = xml + "<RemittanceDate>" + RemittanceDate   + "</RemittanceDate>";
                                                xml = xml + "<WithdrawlDate>" +   WithdrawlDate  + "</WithdrawlDate>";
                                                xml = xml + "<DocDate>" +  DocDate  + "</DocDate>";
						xml = xml + "<TwadNonTwad>"+ results.getString("TWAD_OR_NON_TWAD")   + "</TwadNonTwad>";
						xml = xml + "<Doc_No>"+ results.getInt("DOC_NO")   + "</Doc_No>";
						xml = xml + "<DocType>" + results.getString("DOC_TYPE") + "</DocType>";
						xml = xml + "<Cheqe_or_DDNo>"
						+ results.getInt("CHEQUE_DD_NO")
						+ "</Cheqe_or_DDNo>";

				xml = xml + "<CRAmount>" + results.getFloat("CR_AMOUNT")
						+ "</CRAmount>";

				xml = xml + "<DRAmount>" + results.getFloat("DR_AMOUNT")
						+ "</DRAmount>";
                                            count++;	
                                }
                                if(count>0)
                                    xml = xml + "<flag>success</flag>";
                                else
                                    xml = xml + "<flag>failure</flag>";
                        }
			catch (Exception e) 
                        {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}						
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
        }
}

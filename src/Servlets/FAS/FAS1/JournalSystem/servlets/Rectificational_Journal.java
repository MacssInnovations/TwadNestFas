package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Rectificational_Journal
 */
public class Rectificational_Journal extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Rectificational_Journal() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection con = null;
                int txtAcc_HeadCode=0;
		try {

			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in connection...." + e);
		}
		ResultSet rs = null, rs1 = null,rs3=null;

		PreparedStatement ps = null, ps1 = null,ps3=null;
		String xml = "";

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				xml = "<response><command>sessionout</command><flag>sessionout</flag></response>";
				out.println(xml);
				System.out.println(xml);
				out.close();
				return;

			}
			// System.out.println(session);

		} catch (Exception e) {
			// System.out.println("Redirect Error :"+e);
		}
		System.out.println("java");
		String command;
		command = request.getParameter("command");

		session = request.getSession(false);

		System.out.println("got");
		System.out.println("command" + command);
		// String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);
		if (command.equalsIgnoreCase("paymentreceipt")) {

			// String xml="";
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0, adjYear = 0, adjMonth = 0;

			int y = 0;
			xml = "<response><command>" + command + "</command>";

			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("error get acc unit code");
			}
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (Exception e) {
				System.out.println("error get office id");
			}

			try {
				adjYear = Integer.parseInt(request.getParameter("adjyear"));
			} catch (Exception e) {
				System.out.println("error get adjYear");
			}
			try {
				adjMonth = Integer.parseInt(request.getParameter("adjmonth"));
			} catch (Exception e) {
				System.out.println("error get adjMonth");
			}
                        try {
                                txtAcc_HeadCode = Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                        } catch (Exception e) {
                                System.out.println("error get txtAcc_HeadCode");
                        }
			String docType = request.getParameter("doctype");

			try {

				String Sql = "";
				int flag = 0;
				if (docType.equalsIgnoreCase("R"))
					//Sql = "SELECT RECEIPT_NO as recepaymentno,ACCOUNT_HEAD_CODE FROM FAS_RECEIPT_MASTER WHERE accounting_for_office_id=? AND accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and RECEIPT_STATUS='L' and MODE_OF_CREATION='M' and account_head_code="+txtAcc_HeadCode+" order by RECEIPT_NO";
                                        Sql="SELECT RECEIPT_NO AS recepaymentno,0 as slno,\n" + 
                                        "  mas.ACCOUNT_HEAD_CODE\n" + 
                                        "FROM FAS_RECEIPT_MASTER mas\n" + 
                                        "WHERE mas.accounting_unit_id=?\n" + 
                                       // "AND mas.accounting_unit_id        =?\n" + 
                                        "AND mas.cashbook_month            =?\n" + 
                                        "AND mas.cashbook_year             =?\n" + 
                                        "AND mas.RECEIPT_STATUS            ='L'\n" + 
                                        "AND mas.REMITTANCE_STATUS            ='Y'\n" + 
                                        "AND mas.MODE_OF_CREATION          ='M'\n" + 
                                        "and mas.account_head_code="+txtAcc_HeadCode+"\n" + 
                                        "union all\n" + 
                                        "SELECT RECEIPT_NO AS recepaymentno,sl_no as slno,\n" + 
                                        "  trn.ACCOUNT_HEAD_CODE\n" + 
                                        "FROM fas_receipt_transaction trn\n" + 
                                        "WHERE trn.accounting_unit_id="+cmbAcc_UnitCode+"\n" + 
                                       // "AND trn.accounting_unit_id        ="+cmbAcc_UnitCode+"\n" + 
                                        "AND trn.cashbook_month            ="+adjMonth+"\n" + 
                                        "AND trn.cashbook_year             ="+adjYear+"\n" + 
                                        "and trn.account_head_code="+txtAcc_HeadCode;
				else if (docType.equalsIgnoreCase("P"))
					//Sql = "SELECT VOUCHER_NO as recepaymentno,ACCOUNT_HEAD_CODE FROM FAS_PAYMENT_MASTER WHERE ACCOUNTING_FOR_OFFICE_ID=? AND ACCOUNTING_UNIT_ID=? AND CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and PAYMENT_STATUS='L' and MODE_OF_CREATION='M' and account_head_code="+txtAcc_HeadCode+" order by VOUCHER_NO";
                                        Sql="SELECT VOUCHER_NO AS recepaymentno,0 as slno,\n" + 
                                        "  mas.ACCOUNT_HEAD_CODE\n" + 
                                        "FROM FAS_PAYMENT_MASTER mas\n" + 
                                        "WHERE ACCOUNTING_UNIT_ID=?\n" + 
                                        //"AND ACCOUNTING_UNIT_ID        =?\n" + 
                                        "AND CASHBOOK_MONTH            =?\n" + 
                                        "AND CASHBOOK_YEAR             =?\n" + 
                                        "AND PAYMENT_STATUS            ='L'\n" + 
                                        "AND MODE_OF_CREATION          ='M'\n" + 
                                        "AND mas.account_head_code         ="+txtAcc_HeadCode+"\n" + 
                                        "\n" + 
                                        "union all\n" + 
                                        "SELECT VOUCHER_NO AS recepaymentno,sl_no as slno,\n" + 
                                        "  trn.ACCOUNT_HEAD_CODE\n" + 
                                        "FROM FAS_PAYMENT_TRANSACTION trn\n" + 
                                        "WHERE trn.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+"\n" + 
                                    //    "AND trn.ACCOUNTING_UNIT_ID        ="+ cmbAcc_UnitCode+
                                        "AND trn.CASHBOOK_MONTH            ="+adjMonth+"\n" +  
                                        "AND trn.CASHBOOK_YEAR             ="+adjYear+"\n" + 
                                        "and trn.VERIFIED='Y'\n" + 
                                        "AND trn.account_head_code         ="+txtAcc_HeadCode;
				else if (docType.equalsIgnoreCase("J"))
					//Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_JOURNAL_MASTER WHERE accounting_for_office_id=? AND accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and JOURNAL_STATUS='L' and MODE_OF_CREATION='M' and account_head_code="+txtAcc_HeadCode+" order by VOUCHER_NO";
					 Sql=" SELECT trn.VOUCHER_NO AS recepaymentno,trn.SL_NO as slno,  \n" + 
					 "					   trn.ACCOUNT_HEAD_CODE  \n" + 
					 "					 FROM FAS_JOURNAL_TRANSACTION trn , FAS_JOURNAL_MASTER mas\n" + 
					 "					 WHERE trn.ACCOUNTING_UNIT_ID=?\n" + 
					// "					 AND trn.ACCOUNTING_UNIT_ID        = ?\n" + 
					 "					 AND trn.CASHBOOK_MONTH            =?\n" + 
					 "					 AND trn.CASHBOOK_YEAR             =?\n" + 
					 "					 and trn.VERIFIED='Y'  \n" + 
					 "					 AND trn.account_head_code         ="+txtAcc_HeadCode+"\n" + 
					 "           AND mas.JOURNAL_STATUS            ='L'  \n" + 
					 "					 AND mas.MODE_OF_CREATION         in ('M','A')  \n" + 
					 "            and trn.ACCOUNTING_FOR_OFFICE_ID=mas.ACCOUNTING_FOR_OFFICE_ID\n" + 
					 "					 AND trn.ACCOUNTING_UNIT_ID        = mas.ACCOUNTING_UNIT_ID\n" + 
					 "					 AND trn.CASHBOOK_MONTH            =mas.CASHBOOK_MONTH\n" + 
					 "					 AND trn.CASHBOOK_YEAR             =mas.CASHBOOK_YEAR\n" + 
					 "           and trn.VOUCHER_NO             =mas.VOUCHER_NO";
				else if (docType.equalsIgnoreCase("FR")) {
					if (cmbOffice_code == 5000 && cmbAcc_UnitCode==5) {
                                        System.out.println("head office HO");
						Sql = "SELECT RECEIPT_NO as recepaymentno FROM FAS_FUND_RECEIPT_BY_HO WHERE accounting_for_office_id=5000 AND accounting_unit_id=5 AND cashbook_month=? and cashbook_year=? and RECEIPT_STATUS='L' and (dr_account_head_code="+txtAcc_HeadCode+" or cr_account_head_code="+txtAcc_HeadCode+") order by RECEIPT_NO";
						//flag = 1;
					} else
                                        {
                                            System.out.println("else office");
						Sql = "SELECT RECEIPT_NO as recepaymentno FROM FAS_FUND_RECEIPT_BY_OFFICE WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and RECEIPT_STATUS='L' and (dr_account_head_code="+txtAcc_HeadCode+" or cr_account_head_code="+txtAcc_HeadCode+") order by RECEIPT_NO";
                                        }
				} else if (docType.equalsIgnoreCase("FT")) {
					System.out.println("cmbOffice_code ==>  "+cmbOffice_code);
					if (cmbOffice_code == 5000) {
						//Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_FUND_TRF_FROM_HO_MASTER WHERE accounting_for_office_id=5000 AND accounting_unit_id=5 AND cashbook_month=? and cashbook_year=? and TRANSFER_STATUS='L' order by VOUCHER_NO";
						 Sql="SELECT VOUCHER_NO AS recepaymentno,\n" + 
						 "  mas.ACCOUNT_HEAD_CODE\n" + 
						 "FROM FAS_FUND_TRF_FROM_HO_MASTER mas\n" + 
						 "WHERE mas.accounting_unit_id=?\n" + 
						// "AND mas.accounting_unit_id        =?\n" + 
						 "AND mas.cashbook_month            =?\n" + 
						 "AND mas.cashbook_year             =?\n" + 
						 "AND mas.TRANSFER_STATUS            ='L'\n" + 
						
						 "and mas.account_head_code="+txtAcc_HeadCode+"\n" + 
						 "union all\n" + 
						 "SELECT VOUCHER_NO AS recepaymentno,\n" + 
						 "  trn.ACCOUNT_HEAD_CODE\n" + 
						 "FROM FAS_FUND_TRF_FROM_HO_TRN trn\n" + 
						 "WHERE trn.accounting_unit_id="+cmbAcc_UnitCode+"\n" + 
						// "AND trn.accounting_unit_id        ="+cmbAcc_UnitCode+"\n" + 
						 "AND trn.cashbook_month            ="+adjMonth+"\n" + 
						 "AND trn.cashbook_year             ="+adjYear+"\n" + 
                                                 "and trn.AUTO_STATUS ='Y' \n" + 
                                                    "and trn.VERIFY='Y'\n" + 
						 "and trn.account_head_code="+txtAcc_HeadCode;
						//flag = 1;
					} else
						Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_FUND_TRF_FROM_OFFICE WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and TRANSFER_STATUS='L' and VERIFY='Y'  and (dr_account_head_code="+txtAcc_HeadCode+" or cr_account_head_code="+txtAcc_HeadCode+") order by VOUCHER_NO";
				} else if (docType.equalsIgnoreCase("IBT")) {
					flag = 1;
					//Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_INTER_BANK_TRF_AT_HO WHERE  cashbook_month=? and cashbook_year=? and TRANSFER_STATUS='L' and account_head_code="+txtAcc_HeadCode+" order by VOUCHER_NO";
					Sql ="SELECT VOUCHER_NO AS recepaymentno  "+
					"	From Fas_Inter_Bank_Trf_At_Ho "+
					"	Where Cashbook_Month =? "+
					"	AND cashbook_year    =? "+
					"	AND TRANSFER_STATUS  ='L' "+
					"	AND DR_ACCOUNT_HEAD_CODE="+txtAcc_HeadCode+
					"	Union All "+
					"	SELECT VOUCHER_NO AS recepaymentno "+
					"	From Fas_Inter_Bank_Trf_At_Ho "+
					"	Where Cashbook_Month = "+adjMonth+
					"	AND cashbook_year    = "+adjYear+
					"	AND TRANSFER_STATUS  ='L' "+
					"	And CR_ACCOUNT_HEAD_CODE= "+txtAcc_HeadCode;
				}
                System.out.println("Sql::"+Sql);
				ps = con.prepareStatement(Sql);
				if (flag != 1) {
					//ps.setInt(1, cmbOffice_code);
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, adjMonth);
					ps.setInt(3, adjYear);
				} else {
					ps.setInt(1, adjMonth);
					ps.setInt(2, adjYear);
				}

				rs = ps.executeQuery();

				while (rs.next()) {
                                System.out.println("docType::::"+docType);
					
                                        if(docType.equalsIgnoreCase("J") || docType.equalsIgnoreCase("P") || docType.equalsIgnoreCase("R")){
                                            xml = xml + "<receiptno>" + rs.getInt("recepaymentno")+ "</receiptno><commonFlag>yes</commonFlag>";
                                        xml = xml + "<slno>" + rs.getInt("slno")+ "</slno>";
                                        }
                                        else {
                                            xml = xml + "<receiptno>" + rs.getInt("recepaymentno")+ "</receiptno><commonFlag>no</commonFlag>";
                                        }
					y++;
				}
				if (y != 0) {
					xml = xml + "<flag>success</flag>";
				} else
					xml = xml + "<flag>failure</flag>";

				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load supplier." + e);
				xml = xml + "<flag>failure</flag>";
			}

			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		} 
		else if (command.equalsIgnoreCase("paymentreceipt_Rectify")) 
        {
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, adjYear = 0, adjMonth = 0;
            int y = 0;
            xml = "<response><command>" + command + "</command>";
            try {
                    cmbAcc_UnitCode = Integer.parseInt(request
                                    .getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                    System.out.println("error get acc unit code");
            }
        
            try {
                    adjYear = Integer.parseInt(request.getParameter("adjyear"));
            } catch (Exception e) {
                    System.out.println("error get adjYear");
            }
            try {
                    adjMonth = Integer.parseInt(request.getParameter("adjmonth"));
            } catch (Exception e) {
                    System.out.println("error get adjMonth");
            }
            try {
                    txtAcc_HeadCode = Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
                    System.out.println("error get txtAcc_HeadCode");
            }
            String docType = request.getParameter("doctype");

            try {

                    String Sql = "";
                    int flag = 0;
//                   Sql="SELECT RECEIPT_NO AS recepaymentno,0 as slno,\n" + 
//                            "  mas.ACCOUNT_HEAD_CODE\n" + 
//                            "FROM FAS_RECEIPT_MASTER mas\n" + 
//                            "WHERE mas.accounting_unit_id=?\n" + 
//                            "AND mas.cashbook_month            =?\n" + 
//                            "AND mas.cashbook_year             =?\n" + 
//                            "AND mas.RECEIPT_STATUS            ='L'\n" + 
//                            "AND mas.REMITTANCE_STATUS            ='Y'\n" + 
//                            "AND mas.MODE_OF_CREATION          ='D'\n" + 
//                            "and mas.account_head_code="+txtAcc_HeadCode+"\n" + 
//                            "union all\n" + 
//                            "SELECT RECEIPT_NO AS recepaymentno,sl_no as slno,\n" + 
//                            "  trn.ACCOUNT_HEAD_CODE\n" + 
//                            "FROM fas_receipt_transaction trn\n" + 
//                            "WHERE trn.accounting_unit_id="+cmbAcc_UnitCode+"\n" + 
//                            "AND trn.cashbook_month            ="+adjMonth+"\n" + 
//                            "AND trn.cashbook_year             ="+adjYear+"\n" + 
//                            "and trn.account_head_code="+txtAcc_HeadCode;
                    
                    
                    Sql=" SELECT trn.VOUCHER_NO AS recepaymentno,trn.SL_NO as slno,  \n" + 
       					 "					   trn.ACCOUNT_HEAD_CODE  \n" + 
       					 "					 FROM FAS_JOURNAL_TRANSACTION trn , FAS_JOURNAL_MASTER mas\n" + 
       					 "					 WHERE trn.ACCOUNTING_UNIT_ID=?\n" + 
       					 "					 AND trn.CASHBOOK_MONTH            =?\n" + 
       					 "					 AND trn.CASHBOOK_YEAR             =?\n" + 
       					 "					 and trn.VERIFIED='Y'  \n" + 
       					 "					 AND trn.account_head_code         ="+txtAcc_HeadCode+"\n" + 
       					 "           AND mas.JOURNAL_STATUS            ='L'  \n" + 
       					 "					 AND mas.MODE_OF_CREATION          ='D'  \n" + 
       					 "            and trn.ACCOUNTING_FOR_OFFICE_ID=mas.ACCOUNTING_FOR_OFFICE_ID\n" + 
       					 "					 AND trn.ACCOUNTING_UNIT_ID        = mas.ACCOUNTING_UNIT_ID\n" + 
       					 "					 AND trn.CASHBOOK_MONTH            =mas.CASHBOOK_MONTH\n" + 
       					 "					 AND trn.CASHBOOK_YEAR             =mas.CASHBOOK_YEAR\n" + 
       					 "           and trn.VOUCHER_NO             =mas.VOUCHER_NO";
                    
                    
                    
                   
                   
                    System.out.println("Sql::"+Sql);
                    ps = con.prepareStatement(Sql);
                    
                            ps.setInt(1, cmbAcc_UnitCode);
                            ps.setInt(2, adjMonth);
                            ps.setInt(3, adjYear);
                         
                    rs = ps.executeQuery();

                    while (rs.next()) {
                                xml = xml + "<receiptno>" + rs.getInt("recepaymentno")+ "</receiptno><commonFlag>yes</commonFlag>";
                                xml = xml + "<slno>" + rs.getInt("slno")+ "</slno>";
                            y++;
                    }
                    if (y != 0) {
                            xml = xml + "<flag>success</flag>";
                    } else
                            xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
            } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
	    else if (command.equalsIgnoreCase("paymentreceipt_centage")) 
            {
                // String xml="";
                int cmbAcc_UnitCode = 0, cmbOffice_code = 0, adjYear = 0, adjMonth = 0;

                int y = 0;
                xml = "<response><command>" + command + "</command>";

                try {
                        cmbAcc_UnitCode = Integer.parseInt(request
                                        .getParameter("cmbAcc_UnitCode"));
                } catch (Exception e) {
                        System.out.println("error get acc unit code");
                }
              /*  try {
                        cmbOffice_code = Integer.parseInt(request
                                        .getParameter("cmbOffice_code"));
                } catch (Exception e) {
                        System.out.println("error get office id");
                }  */

                try {
                        adjYear = Integer.parseInt(request.getParameter("adjyear"));
                } catch (Exception e) {
                        System.out.println("error get adjYear");
                }
                try {
                        adjMonth = Integer.parseInt(request.getParameter("adjmonth"));
                } catch (Exception e) {
                        System.out.println("error get adjMonth");
                }
                try {
                        txtAcc_HeadCode = Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                } catch (Exception e) {
                        System.out.println("error get txtAcc_HeadCode");
                }
                String docType = request.getParameter("doctype");

                try {

                        String Sql = "";
                        int flag = 0;
                       Sql="SELECT RECEIPT_NO AS recepaymentno,0 as slno,\n" + 
                                "  mas.ACCOUNT_HEAD_CODE\n" + 
                                "FROM FAS_RECEIPT_MASTER mas\n" + 
                                "WHERE mas.accounting_unit_id=?\n" + 
                                "AND mas.cashbook_month            =?\n" + 
                                "AND mas.cashbook_year             =?\n" + 
                                "AND mas.RECEIPT_STATUS            ='L'\n" + 
                                "AND mas.REMITTANCE_STATUS            ='Y'\n" + 
                                "AND mas.MODE_OF_CREATION          ='M'\n" + 
                                "and mas.account_head_code="+txtAcc_HeadCode+"\n" + 
                                "union all\n" + 
                                "SELECT RECEIPT_NO AS recepaymentno,sl_no as slno,\n" + 
                                "  trn.ACCOUNT_HEAD_CODE\n" + 
                                "FROM fas_receipt_transaction trn\n" + 
                                "WHERE trn.accounting_unit_id="+cmbAcc_UnitCode+"\n" + 
                                "AND trn.cashbook_month            ="+adjMonth+"\n" + 
                                "AND trn.cashbook_year             ="+adjYear+"\n" + 
                                "and trn.account_head_code="+txtAcc_HeadCode;
                       
                       
                        System.out.println("Sql::"+Sql);
                        ps = con.prepareStatement(Sql);
                        
                                ps.setInt(1, cmbAcc_UnitCode);
                                ps.setInt(2, adjMonth);
                                ps.setInt(3, adjYear);
                             //   ps.setInt(4, txtAcc_HeadCode);
                               

                        rs = ps.executeQuery();

                        while (rs.next()) {
                                    xml = xml + "<receiptno>" + rs.getInt("recepaymentno")+ "</receiptno><commonFlag>yes</commonFlag>";
                                    xml = xml + "<slno>" + rs.getInt("slno")+ "</slno>";
                                y++;
                        }
                        if (y != 0) {
                                xml = xml + "<flag>success</flag>";
                        } else
                                xml = xml + "<flag>failure</flag>";

                        ps.close();
                        rs.close();
                } catch (Exception e) {
                        System.out.println("catch..HERE.in load supplier." + e);
                        xml = xml + "<flag>failure</flag>";
                }

                xml = xml + "</response>";
                System.out.println(xml);
                out.println(xml);
            }
                else if (command.equalsIgnoreCase("headcheck")) {

			// String xml="";
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0, adjYear = 0, adjMonth = 0, headCode = 0, receiptNo = 0;

			xml = "<response><command>" + command + "</command>";

			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("error get acc unit code");
			}
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (Exception e) {
				System.out.println("error get office id");
			}

			try {
				adjYear = Integer.parseInt(request.getParameter("adjyear"));
			} catch (Exception e) {
				System.out.println("error get adjYear");
			}
			try {
				adjMonth = Integer.parseInt(request.getParameter("adjmonth"));
			} catch (Exception e) {
				System.out.println("error get adjMonth");
			}

			try {
				headCode = Integer.parseInt(request.getParameter("headcode"));
			} catch (Exception e) {
				System.out.println("error get headcode");
			}

			try {
				receiptNo = Integer.parseInt(request.getParameter("receiptno"));
			} catch (Exception e) {
				System.out.println("error get headcode");
			}

			String docType = request.getParameter("doctype");

			try {
				int flag = 0, flagSub = 0;
				String Sql = "";
				if (docType.equalsIgnoreCase("R"))
					Sql = "SELECT RECEIPT_NO AS recepaymentno,  ACCOUNT_HEAD_CODE FROM FAS_RECEIPT_TRANSACTION "
							+ "WHERE accounting_unit_id  =? AND "
							+ "cashbook_month  =? AND cashbook_year  =? AND RECEIPT_NO  =? "
							+ "AND ACCOUNT_HEAD_CODE  =? union SELECT RECEIPT_NO AS recepaymentno,  "
							+ "ACCOUNT_HEAD_CODE FROM FAS_RECEIPT_MASTER WHERE "
							+ "accounting_unit_id =? AND cashbook_month  =? AND cashbook_year  =? AND "
							+ "RECEIPT_NO  =? AND ACCOUNT_HEAD_CODE =?";
				else if (docType.equalsIgnoreCase("P"))
					//Sql = "SELECT VOUCHER_NO as recepaymentno,ACCOUNT_HEAD_CODE FROM FAS_PAYMENT_TRANSACTION WHERE ACCOUNTING_FOR_OFFICE_ID=? AND ACCOUNTING_UNIT_ID=? AND CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and  VOUCHER_NO=? and ACCOUNT_HEAD_CODE=? order by VOUCHER_NO";
					 Sql ="SELECT VOUCHER_NO as recepaymentno,ACCOUNT_HEAD_CODE FROM FAS_PAYMENT_TRANSACTION WHERE ACCOUNTING_UNIT_ID=? AND CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and  VOUCHER_NO=? and ACCOUNT_HEAD_CODE=? \n" + 
					 " union all\n" + 
					 " SELECT VOUCHER_NO as recepaymentno,ACCOUNT_HEAD_CODE FROM FAS_PAYMENT_MASTER WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" AND CASHBOOK_MONTH="+adjMonth+" and CASHBOOK_YEAR="+adjYear+" and  VOUCHER_NO="+receiptNo+" and ACCOUNT_HEAD_CODE= "+headCode;
				else if (docType.equalsIgnoreCase("J"))
					Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_JOURNAL_TRANSACTION WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and  VOUCHER_NO=? and ACCOUNT_HEAD_CODE=?  order by VOUCHER_NO";
				else if (docType.equalsIgnoreCase("FR")) {
					if (cmbOffice_code == 5000) {
						Sql = "SELECT RECEIPT_NO as recepaymentno FROM FAS_FUND_RECEIPT_BY_HO WHERE accounting_for_office_id=5000 AND accounting_unit_id=5 AND cashbook_month=? and cashbook_year=? and RECEIPT_NO=? and DR_ACCOUNT_HEAD_CODE=? order by RECEIPT_NO";
						flag = 1;
					} else
						Sql = "SELECT RECEIPT_NO as recepaymentno FROM FAS_FUND_RECEIPT_BY_OFFICE WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and RECEIPT_STATUS='L' and RECEIPT_NO=? and DR_ACCOUNT_HEAD_CODE=? order by RECEIPT_NO";
				} else if (docType.equalsIgnoreCase("FT")) {
					System.out.println("cmbAcc_UnitCode FT "+cmbAcc_UnitCode);
					if ((cmbOffice_code == 5000)&&(cmbAcc_UnitCode!=6)) {
						System.out.println("if cmbAcc_UnitCode FT "+cmbAcc_UnitCode);
						Sql = "select account_head_code from FAS_FUND_TRF_FROM_HO_MASTER where cashbook_month="
								+ adjMonth
								+ " and cashbook_year="
								+ adjYear
								+ " and VOUCHER_NO="
								+ receiptNo
								+ " and account_head_code="
								+ headCode
								+ " \n"
								+ " union   all \n"
								+ " select account_head_code from FAS_FUND_TRF_FROM_HO_TRN where cashbook_month=? and cashbook_year=? and VOUCHER_NO=? and account_head_code=? ";

						// "SELECT VOUCHER_NO as recepaymentno FROM FAS_FUND_TRF_FROM_HO_TRN WHERE accounting_for_office_id=5000 AND accounting_unit_id=5 AND cashbook_month=? and cashbook_year=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE=? order by VOUCHER_NO";
						flag = 1;
					} else
						Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_FUND_TRF_FROM_OFFICE WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and VOUCHER_NO=? AND (CR_ACCOUNT_HEAD_CODE      =? or DR_ACCOUNT_HEAD_CODE="+headCode+") order by VOUCHER_NO";
				} else if (docType.equalsIgnoreCase("IBT")) {
					flag = 1;
					Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_INTER_BANK_TRF_AT_HO WHERE  cashbook_month=? and cashbook_year=? and  VOUCHER_NO=? and (DR_ACCOUNT_HEAD_CODE=? or CR_ACCOUNT_HEAD_CODE="
							+ headCode + ") order by VOUCHER_NO";
				}
			    System.out.println("Sql" + Sql);
				ps = con.prepareStatement(Sql);

				//System.out.println("Sql" + Sql);				

				if (flag == 0) {		
					if (docType.equalsIgnoreCase("R")) {
						//ps.setInt(1, cmbOffice_code);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, adjMonth);
						ps.setInt(3, adjYear);
						ps.setInt(4, receiptNo);
						ps.setInt(5, headCode);
						//ps.setInt(6, cmbOffice_code);
						ps.setInt(6, cmbAcc_UnitCode);
						ps.setInt(7, adjMonth);
						ps.setInt(8, adjYear);
						ps.setInt(9, receiptNo);
						ps.setInt(10, headCode);
					}else{
					//ps.setInt(1, cmbOffice_code);
//                                        System.out.println("cmbOffice_code:;;"+cmbOffice_code+":::adjMonth::"+adjMonth);
//					    System.out.println("adjYear:;;"+adjYear+":::receiptNo::"+receiptNo);
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, adjMonth);
					ps.setInt(3, adjYear);
					ps.setInt(4, receiptNo);
					ps.setInt(5, headCode);
                                        System.out.println("v:headCode:"+headCode);
					}
				} else {											
						ps.setInt(1, adjMonth);
						ps.setInt(2, adjYear);
						ps.setInt(3, receiptNo);
						ps.setInt(4, headCode);					
				}
				rs = ps.executeQuery();

				if (rs.next()) {
                                System.out.println("result        ::::");

					if (docType.equalsIgnoreCase("R"))
						Sql = "SELECT RECEIPT_NO as recepaymentno,ACCOUNT_HEAD_CODE FROM FAS_RECEIPT_MASTER WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and RECEIVABLE_VOUCHER_TYPE='R' and RECEIPT_NO=?";
					else if (docType.equalsIgnoreCase("P"))
						Sql = "SELECT VOUCHER_NO as recepaymentno,ACCOUNT_HEAD_CODE FROM FAS_PAYMENT_MASTER WHERE ACCOUNTING_UNIT_ID=? AND CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and PAYABLE_VOUCHER_TYPE='R' and VOUCHER_NO=?";
					else if (docType.equalsIgnoreCase("J"))
						Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_JOURNAL_MASTER WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and VOUCHER_NO=?";
                                                //Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_JOURNAL_MASTER WHERE accounting_for_office_id=? AND accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and CB_REF_TYPE='R' and VOUCHER_NO=?";
					else if (docType.equalsIgnoreCase("FR")) {
						if (cmbOffice_code == 5000) {
							Sql = "SELECT RECEIPT_NO as recepaymentno FROM FAS_FUND_RECEIPT_BY_HO WHERE accounting_for_office_id=5000 AND accounting_unit_id=5 AND cashbook_month=? and cashbook_year=? and RJV_CREATED='R' and RECEIPT_NO=?";
							flagSub = 1;
						} else
							Sql = "SELECT RECEIPT_NO as recepaymentno FROM FAS_FUND_RECEIPT_BY_OFFICE WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and RJV_CREATED='R' and RECEIPT_NO=?";
					} else if (docType.equalsIgnoreCase("FT")) {
						if (cmbOffice_code == 5000) {
							Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_FUND_TRF_FROM_HO_MASTER WHERE accounting_for_office_id=5000 AND accounting_unit_id=5 AND cashbook_month=? and cashbook_year=? and RJV_CREATED='R' and VOUCHER_NO=?";
							flagSub = 1;
						} else
							Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_FUND_TRF_FROM_OFFICE WHERE accounting_unit_id=? AND cashbook_month=? and cashbook_year=? and RJV_CREATED='R' and VOUCHER_NO=?";
					} else if (docType.equalsIgnoreCase("IBT")) {
						flagSub = 1;
						Sql = "SELECT VOUCHER_NO as recepaymentno FROM FAS_INTER_BANK_TRF_AT_HO WHERE  cashbook_month=? and cashbook_year=? and VOUCHER_NO=? and RJV_CREATED='R'";
					}
                                        System.out.println("end Sql::::::"+Sql);
					ps1 = con.prepareStatement(Sql);
					System.out.println("flagSub "+flagSub);
					if (flagSub == 0) {
					//	ps1.setInt(1, cmbOffice_code);
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, adjMonth);
						ps1.setInt(3, adjYear);
						ps1.setInt(4, receiptNo);

					} else {
						ps1.setInt(1, adjMonth);
						ps1.setInt(2, adjYear);
						ps1.setInt(3, receiptNo);

					}
					rs1 = ps1.executeQuery();

					if (rs1.next()) {
						xml = xml + "<flag>rjvupdated</flag>";
					} else
						xml = xml + "<flag>success</flag>";

				}

				else
					xml = xml + "<flag>failure</flag>";

				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		}
                    else if (command.equalsIgnoreCase("chooseHead")) {

                                            // String xml="";
                                            int cmbAcc_UnitCode = 0,count_head=0;
                                            xml = "<response><command>" + command + "</command>";

                                            try {
                                                    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                                            } catch (Exception e) {
                                                    System.out.println("error get acc unit code");
                                            }
                                           

                                            try{
                                                ps3=con.prepareStatement("SELECT Accounting_Unit_Id,\n" + 
                                                "  Ac_Head_Code,\n" + 
                                                "  (SELECT h1.account_head_desc\n" + 
                                                "  FROM Com_Mst_Account_Heads h1\n" + 
                                                "  Where H1.Account_Head_Code=Ac_Head_Code\n" + 
                                                "  )AS DESC1\n" + 
                                                " FROM fas_office_bank_ac_current\n" + 
                                                " WHERE AC_OPERATIONAL_MODE_ID='OPR'\n" + 
                                                " AND CR_DR_TYPE              ='CR'\n" + 
                                                " AND Module_Id               ='MF005'\n" + 
                                                " AND accounting_unit_id      =?");
                                                ps3.setInt(1,cmbAcc_UnitCode);
                                                rs3=ps3.executeQuery();
                                                while(rs3.next()) 
                                                {
                                               // System.out.println("whileeeeeeeeeeeee");
                                                     count_head++;
                                                    xml = xml + "<hCode>" + rs3.getInt("AC_HEAD_CODE")+ "</hCode>";
                                                  //  xml = xml + "<hDesc>" + rs3.getString("DESC1")+ "</hDesc>";
                                                    
                                                }
                                                if(count_head>0) {
                                           //     System.out.println("count_head::::"+count_head);
                                                    xml = xml + "<flag>success</flag>";
                                                }
                                                else {
                                                    xml = xml + "<flag>failure</flag>";
                                                }
                                            }
                                             catch (Exception e) {
                                                    e.printStackTrace();
                                                    xml = xml + "<flag>Exception</flag>";
                                            }

                                            xml = xml + "</response>";
                                            System.out.println(xml);
                                            out.println(xml);
                                    }
                    else if (command.equalsIgnoreCase("chooseHead1")) {

                        // String xml="";
                        int cmbAcc_UnitCode = 0,count_head=0;
                        xml = "<response><command>" + command + "</command>";

                        try {
                                cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                        } catch (Exception e) {
                                System.out.println("error get acc unit code");
                        }
                       

                        try{
                            ps3=con.prepareStatement("SELECT Accounting_Unit_Id,\n" + 
                            "  Ac_Head_Code,\n" + 
                            "  (SELECT h1.account_head_desc\n" + 
                            "  FROM Com_Mst_Account_Heads h1\n" + 
                            "  Where H1.Account_Head_Code=Ac_Head_Code\n" + 
                            "  )AS DESC1\n" + 
                            " FROM fas_office_bank_ac_current\n" + 
                            " WHERE AC_OPERATIONAL_MODE_ID='COL'\n" + 
                            " AND CR_DR_TYPE              ='DR'\n" + 
                            " AND Module_Id               ='MF004'\n" + 
                            " AND accounting_unit_id      =?");
                            ps3.setInt(1,cmbAcc_UnitCode);
                            rs3=ps3.executeQuery();
                            while(rs3.next()) 
                            {
                           // System.out.println("whileeeeeeeeeeeee");
                                 count_head++;
                                xml = xml + "<hCode>" + rs3.getInt("AC_HEAD_CODE")+ "</hCode>";
                              //  xml = xml + "<hDesc>" + rs3.getString("DESC1")+ "</hDesc>";
                                
                            }
                            if(count_head>0) {
                       //     System.out.println("count_head::::"+count_head);
                                xml = xml + "<flag>success</flag>";
                            }
                            else {
                                xml = xml + "<flag>failure</flag>";
                            }
                        }
                         catch (Exception e) {
                                e.printStackTrace();
                                xml = xml + "<flag>Exception</flag>";
                        }

                        xml = xml + "</response>";
                        System.out.println(xml);
                        out.println(xml);
                }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String strCommand = "";
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		PreparedStatement ps = null, ps1 = null;
		String xml = "";
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

		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}

		try {

			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);

		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}
		if (strCommand.equalsIgnoreCase("Add")) {
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			xml = "<response><command>Add</command>";
			Calendar c;
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0, txtJournalVou_No = 0;
			// int txtCash_Acc_code=0;
			int Total_TRN_Rec = 0;
			// double txtAmount=0;
			String txtCheque_NO = "", txtCB_REF_TYPE = "";

			Date txtCrea_date = null, txtCheque_date = null;
			String txtRemarks = "";

			int cmbMas_SL_type = 0, cmbMas_SL_Code = 0;
			String txtMode_of_creat = "M", txtCreat_By_Module = "GJV";
			double dep_rate = 0; // changes here
			String update_user = (String) session.getAttribute("UserId");
			long l = System.currentTimeMillis();
			//Timestamp ts = new Timestamp(l);

			// For Banking Purpose

			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbOffice_code " + cmbOffice_code);

			/*
			 * try{txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"
			 * ));} catch(NumberFormatException
			 * e){System.out.println("exception"+e );}
			 * System.out.println("txtCash_Acc_code "+txtCash_Acc_code);
			 */

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			System.out.println("txtCrea_date " + txtCrea_date);

			System.out.println("b4 getting month and year");
			try {
				txtCash_year = Integer.parseInt(sd[2]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtCash_year " + txtCash_year);

			try {
				txtCash_Month_hid = Integer.parseInt(sd[1]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

			/*
			 * try{txtCash_year=Integer.parseInt(request.getParameter("txtCash_year"
			 * ));} catch(Exception e){System.out.println("exception"+e );}
			 * System.out.println("txtCash_year "+txtCash_year);
			 * 
			 * try{txtCash_Month_hid=Integer.parseInt(request.getParameter(
			 * "txtCash_Month_hid"));} catch(Exception
			 * e){System.out.println("exception"+e );}
			 * System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
			 */

			try {
				txtJournalVou_No = Integer.parseInt(request
						.getParameter("txtJournalVou_No"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtJournalVou_No " + txtJournalVou_No);

			/*
			 * try{txtAmount=Double.parseDouble(request.getParameter("txtAmount")
			 * );} catch(Exception e){System.out.println("exception"+e );}
			 * System.out.println("txtAmount "+txtAmount);
			 */

			txtCheque_NO = request.getParameter("txtCheque_NO");
			System.out.println("txtCheque_NO " + txtCheque_NO);

			if (!request.getParameter("txtCheque_date").equalsIgnoreCase("")) {
				sd = request.getParameter("txtCheque_date").split("/");
				c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
						.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
				d = c.getTime();
				txtCheque_date = new Date(d.getTime());
			}
			System.out.println("txtCheque_date " + txtCheque_date);
			// changes here
			try {
				cmbMas_SL_type = Integer.parseInt(request
						.getParameter("cmbMas_SL_type"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			try {
				cmbMas_SL_Code = Integer.parseInt(request
						.getParameter("cmbMas_SL_Code"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			System.out.println("cmbMas_SL and office " + cmbMas_SL_type + " "
					+ cmbMas_SL_Code);// +" "+cmbMas_offid);
			int cashYear = 0;
			int cashMonth = 0;
			if (cmbMas_SL_type == 72) {
				cashYear = Integer.parseInt(request.getParameter("cashyear"));
				cashMonth = Integer.parseInt(request.getParameter("cashmonth"));

			}

			txtRemarks = request.getParameter("txtRemarks");
			System.out.println("txtRemarks " + txtRemarks);

			try {
				con.clearWarnings();
				con.setAutoCommit(false);
				System.out.println("inside proc");
				String No_TRN_Rec[] = request.getParameterValues("H_code");
				SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");  
    			SimpleDateFormat objTs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
                //java.sql.Date txtCrea_date = java.sql.Date.valueOf( "txtCrea_date" );
                //long epoch = objTs.parse("ts").getTime();    
               
    			   c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
    					                         Integer.parseInt(sd[0]));
    					            d = c.getTime();
    					            txtCrea_date = new Date(d.getTime());
    			
    			Timestamp ts=new Timestamp(l); 
    			
				// int NTR=No_TRN_Rec.length;
				// System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
				Total_TRN_Rec = No_TRN_Rec.length;// Integer.parseInt(No_TRN_Rec);
				System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
			//	cs =
//							con.prepareCall("{call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//				cs.setInt(1, cmbAcc_UnitCode);
//				cs.setInt(2, cmbOffice_code);
//				cs.setInt(3, txtCash_year);
//				cs.setInt(4, txtCash_Month_hid);
//				cs.setInt(5, txtJournalVou_No);
//				cs.setDate(6, txtCrea_date);
//				// cs.setString(7,txtReceipt_type);
//				// cs.setInt(8,txtCash_Acc_code);
//				cs.setInt(7, cmbMas_SL_type);
//				cs.setInt(8, cmbMas_SL_Code);
//				cs.setDouble(9, dep_rate);
//				cs.setString(10, txtCheque_NO);
//				cs.setDate(11, txtCheque_date);
//				cs.setString(12, txtCB_REF_TYPE);
//				// cs.setInt(13,txtCB_REF_NO);
//				// cs.setDate(14,txtCB_REF_DATE);
//				// cs.setDouble(19,txtAmount);
//				cs.setInt(13, Total_TRN_Rec);
//				cs.setString(14, txtRemarks);
//				cs.setString(15, txtMode_of_creat);
//				cs.setString(16, txtCreat_By_Module);
//				cs.setString(17, "insert");
//				cs.registerOutParameter(5, java.sql.Types.NUMERIC);
//				cs.registerOutParameter(18, java.sql.Types.NUMERIC);
//				cs.setString(19, update_user);
//				cs.setTimestamp(20, ts);
//				// cs.setInt(21,cashYear);
//				// cs.setInt(22,cashMonth);
//				System.out.println("b4 exe ");
//				cs.execute();
						cs=con.prepareCall("call FAS_JOURNAL_MASTER_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?,?,?::int,?,?)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setInt(3, txtCash_year);
                cs.setInt(4, txtCash_Month_hid);
                cs.setInt(5, txtJournalVou_No);
                cs.setDate(6, txtCrea_date);
                // cs.setString(7,txtReceipt_type);
                //  cs.setInt(8,txtCash_Acc_code);
                cs.setInt(7, cmbMas_SL_type);
                cs.setInt(8, cmbMas_SL_Code);
                cs.setDouble(9, dep_rate);
                cs.setString(10, txtCheque_NO);
                cs.setDate(11, txtCheque_date);
                cs.setString(12, txtCB_REF_TYPE);
                // cs.setInt(13,txtCB_REF_NO);
                // cs.setDate(14,txtCB_REF_DATE);
                // cs.setDouble(19,txtAmount);
                cs.setInt(13, Total_TRN_Rec);
                cs.setString(14, txtRemarks);
                cs.setString(15, txtMode_of_creat);
                cs.setString(16, txtCreat_By_Module);
                cs.setString(17, "insert");
                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                cs.registerOutParameter(18, java.sql.Types.INTEGER);
                cs.setNull(5, java.sql.Types.NUMERIC);
                cs.setNull(18, java.sql.Types.NUMERIC);
                cs.setString(19, update_user);
                cs.setTimestamp(20, ts);
                System.out.println("b4 exe ");
                cs.execute();
                txtJournalVou_No = cs.getBigDecimal(5).intValue();
                int errcode = cs.getInt(18);
               System.out.println("SQLCODE:::" + errcode);
				if (errcode != 0) {
					System.out.println("redirect");
					sendMessage(response,
							"The General Voucher Number Creation Failed ", "ok");
					xml = xml + "<flag>failure</flag>";
				} else {
					String Grid_H_code[] = request.getParameterValues("H_code");
					String Grid_CR_DR_type[] = request
							.getParameterValues("CR_DR_type");
					String Grid_SL_type[] = request
							.getParameterValues("SL_type");
					String Grid_SL_code[] = request
							.getParameterValues("SL_code");
					// String
					// Grid_rec_from[]=request.getParameterValues("rec_from");
					String Grid_Bill_No[] = request
							.getParameterValues("Bill_NO");
					String Grid_Bill_date[] = request
							.getParameterValues("Bill_date");
					String Grid_Bill_type[] = request
							.getParameterValues("Bill_type");

					String Grid_Agree_No[] = request
							.getParameterValues("Agree_No");
					String Grid_Agree_date[] = request
							.getParameterValues("Agree_date");

					String Grid_sl_amt[] = request.getParameterValues("sl_amt");
					String Grid_particular[] = request
							.getParameterValues("particular");

					String Grid_adj_year[] = request
							.getParameterValues("adj_year");
					String Grid_adj_month[] = request
							.getParameterValues("adj_month");

					String Grid_doc_no[] = request.getParameterValues("doc_no");
					String Grid_doc_type[] = request
							.getParameterValues("doc_type");

					String sql = "insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, "
							+ "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, "
							+ "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE,"
							+ "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  "
							+ "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH,ADJ_DOC_TYPE,ADJ_DOC_NO ) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

					int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code = 0, cmbSL_type = 0, txtCB_REF_NO = 0, adjYear = 0, adjMonth = 0, docNo = 0;
					Date txtBill_Date = null, txtAgree_Date = null, txtCheque_DD_date = null, txtCB_REF_DATE = null;
					double txtsub_Amount = 0;
					String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type = "", txtAgree_No = "", txtParticular = "";
					String txtCheque_DD = "", txtCheque_DD_NO = "", docType = "";

					ps = con.prepareStatement(sql);
					for (int k = 0; k < Grid_H_code.length; k++) {
						try {
							txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
						} catch (Exception e) {
							System.out.println("exception in trans " + e);
						}
						rad_sub_CR_DR = Grid_CR_DR_type[k];

						try {
							cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
						} catch (Exception e) {
							System.out.println("exception in trans " + e);
						}
						try {
							cmbSL_Code = Integer.parseInt(Grid_SL_code[k]);
						} catch (Exception e) {
							System.out.println("exception in trans " + e);
						}
						System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
						System.out.println("Grid_CR_DR_type[k] "
								+ Grid_CR_DR_type[k]);
						System.out.println("Grid_SL_type[k]" + Grid_SL_type[k]
								+ "u");
						System.out.println("Grid_SL_code[k]" + Grid_SL_code[k]
								+ "from here" + cmbSL_Code);
						// System.out.println(cmbSL_type.equalsIgnoreCase("7"));
						// txtsub_Recei_from=Grid_rec_from[k];

						txtBill_no = Grid_Bill_No[k];

						txtBill_Type = Grid_Bill_type[k];
						try {
							adjYear = Integer.parseInt(Grid_adj_year[k]);
							adjMonth = Integer.parseInt(Grid_adj_month[k]);
							docNo = Integer.parseInt(Grid_doc_no[k]);
						} catch (Exception e) {
							System.out
									.println("this is not have doc type and voucherno");
						}
						docType = Grid_doc_type[k];
						if (docType.equalsIgnoreCase("")) {
							docType = null;
						}

						if (!Grid_Bill_date[k].equalsIgnoreCase("")) {
							sd = Grid_Bill_date[k].split("/");
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							txtBill_Date = new Date(d.getTime());
						}

						txtAgree_No = Grid_Agree_No[k];
						if (!Grid_Agree_date[k].equalsIgnoreCase("")) {
							sd = Grid_Agree_date[k].split("/");
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							txtAgree_Date = new Date(d.getTime());
						}

						System.out.println("txtBill_no..." + txtBill_no);
						System.out.println("txtBill_Type..." + txtBill_Type);
						System.out.println("txtBill_Date..." + txtBill_Date);
						System.out.println("txtAgree_No..." + txtAgree_No);
						System.out.println("txtAgree_Date..." + txtAgree_Date);

						System.out.println("amount");
						txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
						txtParticular = Grid_particular[k];
						System.out.println("amount");
						System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);
						// System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
						System.out.println("Grid_particular[k] "
								+ Grid_particular[k]);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setInt(3, txtCash_year);
						ps.setInt(4, txtCash_Month_hid);
						ps.setInt(5, txtJournalVou_No);
						ps.setInt(6, SL_NO);
						ps.setInt(7, txtAcc_HeadCode);
						ps.setString(8, rad_sub_CR_DR);
						ps.setInt(9, cmbSL_type);
						ps.setInt(10, cmbSL_Code);
						ps.setString(11, txtBill_no);
						ps.setString(12, txtBill_Type);
						ps.setString(13, txtAgree_No);
						ps.setDate(14, txtAgree_Date);
						ps.setDate(15, txtBill_Date);

						ps.setString(16, txtCheque_DD);
						ps.setString(17, txtCheque_DD_NO);
						ps.setDate(18, txtCheque_DD_date);

						ps.setDouble(19, txtsub_Amount);
						ps.setString(20, txtParticular);
						ps.setInt(21, txtCB_REF_NO);
						ps.setDate(22, txtCB_REF_DATE);
						ps.setString(23, update_user);
						ps.setTimestamp(24, ts);
						ps.setInt(25, adjYear);
						ps.setInt(26, adjMonth);
						ps.setString(27, docType);
						ps.setInt(28, docNo);
						SL_NO++;
						ps.executeUpdate();

						if (docType != null && !docType.equalsIgnoreCase("")) {
							String updateReference = "";
							if (docType.equalsIgnoreCase("J"))
								updateReference = "update FAS_JOURNAL_MASTER set CB_REF_TYPE='R' where ACCOUNTING_UNIT_ID="
										+ cmbAcc_UnitCode
										+ " and ACCOUNTING_FOR_OFFICE_ID="
										+ cmbOffice_code
										+ " and CASHBOOK_YEAR="
										+ adjYear
										+ " and CASHBOOK_MONTH="
										+ adjMonth
										+ " and VOUCHER_NO=" + docNo + "";
							else if (docType.equalsIgnoreCase("P"))
								updateReference = "update FAS_PAYMENT_MASTER set PAYABLE_VOUCHER_TYPE='R' where ACCOUNTING_UNIT_ID="
										+ cmbAcc_UnitCode
										+ " and ACCOUNTING_FOR_OFFICE_ID="
										+ cmbOffice_code
										+ " and CASHBOOK_YEAR="
										+ adjYear
										+ " and CASHBOOK_MONTH="
										+ adjMonth
										+ " and VOUCHER_NO=" + docNo + "";
							else if (docType.equalsIgnoreCase("R"))
								updateReference = "update FAS_RECEIPT_MASTER set RECEIVABLE_VOUCHER_TYPE='R' where ACCOUNTING_UNIT_ID="
										+ cmbAcc_UnitCode
										+ " and ACCOUNTING_FOR_OFFICE_ID="
										+ cmbOffice_code
										+ " and CASHBOOK_YEAR="
										+ adjYear
										+ " and CASHBOOK_MONTH="
										+ adjMonth
										+ " and RECEIPT_NO=" + docNo + "";
							else if (docType.equalsIgnoreCase("FT")) {
								if (cmbOffice_code == 5000)
									updateReference = "update FAS_FUND_TRF_FROM_HO_MASTER set RJV_CREATED='R' where  CASHBOOK_YEAR="
											+ adjYear
											+ " and CASHBOOK_MONTH="
											+ adjMonth
											+ " and VOUCHER_NO="
											+ docNo + "";
								else
									updateReference = "update FAS_FUND_TRF_FROM_OFFICE set RJV_CREATED='R' where ACCOUNTING_UNIT_ID="
											+ cmbAcc_UnitCode
											+ " and ACCOUNTING_FOR_OFFICE_ID="
											+ cmbOffice_code
											+ " and CASHBOOK_YEAR="
											+ adjYear
											+ " and CASHBOOK_MONTH="
											+ adjMonth
											+ " and VOUCHER_NO=" + docNo + "";
							} else if (docType.equalsIgnoreCase("FR")) {
								if (cmbOffice_code == 5000)
									updateReference = "update FAS_FUND_RECEIPT_BY_HO set RJV_CREATED='R' where  CASHBOOK_YEAR="
											+ adjYear
											+ " and CASHBOOK_MONTH="
											+ adjMonth
											+ " and RECEIPT_NO="
											+ docNo + "";
								else
									updateReference = "update FAS_FUND_RECEIPT_BY_OFFICE set RJV_CREATED='R' where ACCOUNTING_UNIT_ID="
											+ cmbAcc_UnitCode
											+ " and ACCOUNTING_FOR_OFFICE_ID="
											+ cmbOffice_code
											+ " and CASHBOOK_YEAR="
											+ adjYear
											+ " and CASHBOOK_MONTH="
											+ adjMonth
											+ " and RECEIPT_NO=" + docNo + "";
							} else if (docType.equalsIgnoreCase("IBT"))
								updateReference = "update FAS_INTER_BANK_TRF_AT_HO set RJV_CREATED='R' where   CASHBOOK_YEAR="
										+ adjYear
										+ " and CASHBOOK_MONTH="
										+ adjMonth
										+ " and VOUCHER_NO="
										+ docNo
										+ "";

							System.out.print("update *** " + updateReference);

							ps1 = con.prepareStatement(updateReference);
							ps1.executeUpdate();
							ps1.close();

						}

						txtAcc_HeadCode = 0;
						rad_sub_CR_DR = "";
						cmbSL_type = 0;
						cmbSL_Code = 0;
						txtCheque_DD = "";
						txtCheque_DD_NO = "";
						txtCheque_DD_date = null;
						txtAgree_No = "";
						txtAgree_Date = null;
						txtsub_Amount = 0;
						txtParticular = "";
						adjYear = 0;
						adjMonth = 0;
						docType = "";
					}
					ps.close();
					System.out.println("b4 commit");

					System.out.println("Type of adjust" + cmbMas_SL_type);

					con.commit();
					sendMessage(response,
							"Rectification Journal Voucher Number '"
									+ txtJournalVou_No
									+ "' has been Created Successfully ", "ok");
				}

			}

			catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException sqle) {
					System.out.println("exception in rollback " + sqle);
				}
				sendMessage(response,
						"The Rectification Voucher Number Creation Failed ",
						"ok");
				System.out.println("Exception occur due to " + e);
			} finally {
				System.out.println("done");
				try {
					con.setAutoCommit(true);
				} catch (SQLException sqle) {
				}
			}

		}
	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
		}
	}

}

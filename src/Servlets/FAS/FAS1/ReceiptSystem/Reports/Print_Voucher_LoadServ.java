package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Print_Voucher_LoadServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
                                                           System.out.println("calling Print_Voucher_LoadServ ");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Print_Voucher_LoadServ</title></head>");
        out.println("<body>");

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

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        String selstr = "";
        String selspestr = "";
        String sel = "";
        String opt = "";
        String xml = "";
        int voucher_no = 0;
        int count = 0;

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
            System.out.println("Here " + ConnectionString);
            String accun = request.getParameter("cbaccunit");
            int accunit = Integer.parseInt(accun);
            System.out.println(accunit);
            String off = request.getParameter("cboffid");
            int offid = Integer.parseInt(off);
            String cbmon = request.getParameter("cbmonth");
            int cbmonth = Integer.parseInt(cbmon);
            System.out.println("Month" + cbmonth);
            String cbye = request.getParameter("cbyear");
            int cbyear = Integer.parseInt(cbye);
            System.out.println("Year" + cbyear);
            xml = xml + "<response>";

            String rec_type = request.getParameter("rectype");
            System.out.println("Type=" + rec_type);
            String cbtype = request.getParameter("cbtype");
            System.out.println("Receipt type" + cbtype);
            String sub_str="";
         
            if (rec_type.equalsIgnoreCase("REC")) {
            	
            	   if(cbtype.equalsIgnoreCase("ALL")){
                   	sub_str=  "  and created_by_module in ('CR','BR')";
                   }else{
                   	sub_str=  "  and created_by_module=?";
                   }
                ps =
  connection.prepareStatement("select receipt_no as vno from fas_Receipt_Master " +
                              " where accounting_unit_id=? and accounting_for_office_id=? and cashbook_month=? and " +
                              " cashbook_year=? " +
                           //   " and created_by_module=?" +
                              sub_str+
                              " and receipt_status!='C' " +
                              " order by receipt_no");

                //ps.setString(1,rec_type);
                ps.setInt(1, accunit);
                ps.setInt(2, offid);
                ps.setInt(3, cbmonth);
                ps.setInt(4, cbyear);
                if(!cbtype.equalsIgnoreCase("ALL")){
                ps.setString(5, cbtype); }
                rs1 = ps.executeQuery();

                while (rs1.next()) {
                    count++;
                    //voucher_no=rs1.getInt("vno");
                    xml = xml + "<voucher>" + rs1.getInt("vno") + "</voucher>";
                }

            } else if (rec_type.equalsIgnoreCase("PAY")) {
         	   if(cbtype.equalsIgnoreCase("ALL")){
                  	sub_str=  "  and created_by_module in('BPF','NP','BPP')";
                  }else{
                  	sub_str=  "  and created_by_module=?";
                  }
                ps =
  connection.prepareStatement("select voucher_no as vno from fas_Payment_Master " +
                              " where accounting_unit_id=? and accounting_for_office_id=? and " +
                              " cashbook_month=? and " +
                              " cashbook_year=? " +
                             // " and created_by_module=? " +
                              sub_str+
                              " and payment_status!='C' " +
                              " order by voucher_no");

                //ps.setString(1,rec_type);
                ps.setInt(1, accunit);
                ps.setInt(2, offid);
                ps.setInt(3, cbmonth);
                ps.setInt(4, cbyear);
                if(!cbtype.equalsIgnoreCase("ALL")){
                ps.setString(5, cbtype);
                }
                rs1 = ps.executeQuery();

                while (rs1.next()) {
                    count++;
                    //voucher_no=rs1.getInt("vno");
                    xml = xml + "<voucher>" + rs1.getInt("vno") + "</voucher>";
                }
			} else if (rec_type.equalsIgnoreCase("JOU")) {

				if (cbtype.equalsIgnoreCase("ALL")) {
					ps = connection
							.prepareStatement("SELECT voucher_no AS vno "
									+ " FROM fas_Journal_Master "
									+ " WHERE accounting_unit_id    =? "
									+ " AND accounting_for_office_id=? "
									+ " AND cashbook_month          =? "
									+ " AND cashbook_year           =? "
									+ " AND created_by_module IN ('LJV','GJV') "
									+ " AND journal_status!    ='C' "
									+ " UNION ALL "
									+ "SELECT voucher_no AS vno "
									+ " FROM fas_Journal_Master "
									+ " WHERE accounting_unit_id    =? "
									+ " AND accounting_for_office_id=? "
									+ " AND cashbook_month          =3 "
									+ " AND cashbook_year           =? "
									+ " AND created_by_module       ='SJV' "
									+ " AND journal_status!         ='C' "
									+ " ORDER BY vno");

					ps.setInt(1, accunit);
					ps.setInt(2, offid);
					ps.setInt(3, cbmonth);
					ps.setInt(4, cbyear);
					ps.setInt(5, accunit);
					ps.setInt(6, offid);
					ps.setInt(7, cbyear);

					rs1 = ps.executeQuery();

					while (rs1.next()) {

						count++;
						// voucher_no=rs1.getInt("vno");
						xml = xml + "<voucher>" + rs1.getInt("vno")
								+ "</voucher>";
					}

				} else {

					ps = connection
							.prepareStatement("select voucher_no as vno from fas_Journal_Master "
									+ " where accounting_unit_id=? and accounting_for_office_id=? and "
									+ " cashbook_month=? and "
									+ " cashbook_year=?"
									+ "  and created_by_module=? "
									+ " and journal_status!='C' "
									+ " order by voucher_no");

					// ps.setString(1,rec_type);
					ps.setInt(1, accunit);
					ps.setInt(2, offid);
					ps.setInt(3, cbmonth);
					ps.setInt(4, cbyear);

					ps.setString(5, cbtype);
					rs1 = ps.executeQuery();

					while (rs1.next()) {
						count++;
						// voucher_no=rs1.getInt("vno");
						xml = xml + "<voucher>" + rs1.getInt("vno")
								+ "</voucher>";
					}

					// else
					// out.println("javascript:alert('Select reciept type')");
					/*
					 * rs1=ps.executeQuery();
					 * 
					 * while(rs1.next()) { count++;
					 * //voucher_no=rs1.getInt("vno");
					 * xml=xml+"<voucher>"+rs1.getInt("vno")+"</voucher>"; }
					 */
				}
			
			}
			else if (rec_type.equalsIgnoreCase("FR")) {
            	
				if(cbtype.equalsIgnoreCase("Office")){
             ps =
connection.prepareStatement("select receipt_no as vno from FAS_FUND_RECEIPT_BY_OFFICE " +
                           " where accounting_unit_id=? and accounting_for_office_id=? and cashbook_month=? and " +
                           " cashbook_year=? " +
                           " and RECEIPT_STATUS!='C' " +
                           " order by receipt_no");

             //ps.setString(1,rec_type);
             ps.setInt(1, accunit);
             ps.setInt(2, offid);
             ps.setInt(3, cbmonth);
             ps.setInt(4, cbyear);
             
             rs1 = ps.executeQuery();

             while (rs1.next()) {
                 count++;
                 //voucher_no=rs1.getInt("vno");
                 xml = xml + "<voucher>" + rs1.getInt("vno") + "</voucher>";
             }

         }
				
				else if(cbtype.equalsIgnoreCase("HO")){
		             ps =
		            		 connection.prepareStatement("select receipt_no as vno from FAS_FUND_RECEIPT_BY_HO " +
		            		                            " where accounting_unit_id=? and accounting_for_office_id=? and cashbook_month=? and " +
		            		                            " cashbook_year=? " +
		            		                            " and RECEIPT_STATUS!='C' " +
		            		                            " order by receipt_no");

		            		              //ps.setString(1,rec_type);
		            		              ps.setInt(1, accunit);
		            		              ps.setInt(2, offid);
		            		              ps.setInt(3, cbmonth);
		            		              ps.setInt(4, cbyear);
		            		              
		            		              rs1 = ps.executeQuery();

		            		              while (rs1.next()) {
		            		                  count++;
		            		                  //voucher_no=rs1.getInt("vno");
		            		                  xml = xml + "<voucher>" + rs1.getInt("vno") + "</voucher>";
		            		              }

		            		          }		
			}
else if (rec_type.equalsIgnoreCase("FT")) {
            	
				if(cbtype.equalsIgnoreCase("From_Office")){
             ps =
connection.prepareStatement("select VOUCHER_NO as vno from FAS_FUND_TRF_FROM_OFFICE " +
                           " where accounting_unit_id=? and accounting_for_office_id=? and cashbook_month=? and " +
                           " cashbook_year=? " +
                           " and TRANSFER_STATUS!='C' " +
                           " order by receipt_no");

             //ps.setString(1,rec_type);
             ps.setInt(1, accunit);
             ps.setInt(2, offid);
             ps.setInt(3, cbmonth);
             ps.setInt(4, cbyear);
             
             rs1 = ps.executeQuery();

             while (rs1.next()) {
                 count++;
                 //voucher_no=rs1.getInt("vno");
                 xml = xml + "<voucher>" + rs1.getInt("vno") + "</voucher>";
             }

         }
				
				else if(cbtype.equalsIgnoreCase("From_HO")){
		             ps =
		            		 connection.prepareStatement("select VOUCHER_NO as vno from TRANSFER_STATUS " +
		            		                            " where accounting_unit_id=? and accounting_for_office_id=? and cashbook_month=? and " +
		            		                            " cashbook_year=? " +
		            		                            " and TRANSFER_STATUS!='C' " +
		            		                            " order by receipt_no");

		            		              //ps.setString(1,rec_type);
		            		              ps.setInt(1, accunit);
		            		              ps.setInt(2, offid);
		            		              ps.setInt(3, cbmonth);
		            		              ps.setInt(4, cbyear);
		            		              
		            		              rs1 = ps.executeQuery();

		            		              while (rs1.next()) {
		            		                  count++;
		            		                  //voucher_no=rs1.getInt("vno");
		            		                  xml = xml + "<voucher>" + rs1.getInt("vno") + "</voucher>";
		            		              }

		            		          }		
			}
        	if (count > 0) {
				xml = xml + "<flag>success</flag>";
			}

			else {
				xml = xml + "<flag>failure</flag>";
			}
        }catch (Exception ex) {
            //xml+="<flag>failure</flag>";
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        xml += "</response>";
        out.println(xml);
        System.out.println(xml);
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
}

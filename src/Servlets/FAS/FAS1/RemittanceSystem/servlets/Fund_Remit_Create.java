package Servlets.FAS.FAS1.RemittanceSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Fund_Remit_Create extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String strCommand = "";
        Connection con = null;

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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

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
            CallableStatement cs = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Calendar c;
            // common to All transaction
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0;
            double txtAmount = 0;
            Date txtCrea_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            ////////

            ////  For the purpose of remittance
            int txtCash_Acc_code = 0;
            int txtChallan_No = 0;
            Date txtChallan_Date = null;
            String txtCR_DB = "", txtRemarks = "", txtVerified =
                "N", Remit_type = "F";
            int txtVerified_Auth = 0;
            Date verify_Date = null;

            int txtVoucher_No = 0;
            int txtBankId = 0, txtBranchId =
                0; //will be Generated b4 calling print challan section
            long txtBankAccountNo =
                0; //will be Generated b4 calling print challan section


            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            /*  System.out.println("b4 getting month and year");
                try{txtCash_year=Integer.parseInt(sd[2]);}
                            catch(Exception e){System.out.println("exception"+e );}
                            System.out.println("txtCash_year "+txtCash_year);

                            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                            catch(Exception e){System.out.println("exception"+e );}
                            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
             */

            txtChallan_Date =
                    txtCrea_date; // Challan date and Remittance date is same date as creation date only


            try {
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);
           /* nk if(txtCash_Acc_code==0)  {///unused and prevent msg box response due to exception
            * hidden by Nandakumar on 03sep2019 
          	  sendMessage(response,
                        "Head Code Not valid " +txtCash_Acc_code, "ok");
          }*/
            txtCR_DB = request.getParameter("txtCR_DB");
            System.out.println("txtCR_DB " + txtCR_DB);

            /*
               try{txtBankId=Integer.parseInt(request.getParameter("txtBankId"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("txtBankId "+txtBankId);

                try{txtBranchId=Integer.parseInt(request.getParameter("txtBranchId"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("txtBranchId "+txtBranchId);
              */
            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);

            /** Find Cashbook Month and Year */

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);

          
            ///////////////////////--------------------- END S HERE------------------
            try {
                System.out.println("start");
                con.clearWarnings();
                con.setAutoCommit(false);
//                cs =
//  con.prepareCall("{call FAS_REMITTANCE_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                cs.setInt(1, cmbAcc_UnitCode);
//                cs.setInt(2, cmbOffice_code);
//                cs.setInt(3, txtCash_Month_hid);
//                cs.setInt(4, txtCash_year);
//                cs.setDate(5, txtCrea_date);
//                cs.setString(6, Remit_type);
//                cs.setInt(7, txtChallan_No);
//                cs.setDate(8, txtChallan_Date);
//                cs.setDouble(9, txtAmount);
//                cs.setString(10, txtVerified);
//                cs.setDate(11, verify_Date);
//                cs.setInt(12, txtVerified_Auth);
//                cs.setString(13, txtRemarks);
//                cs.setString(14, update_user);
//                cs.setTimestamp(15, ts);
//                cs.setLong(16, txtBankAccountNo);
//                cs.setString(17, "insert");
//                cs.registerOutParameter(7, java.sql.Types.NUMERIC);
//                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
//                cs.execute();
//
//                txtChallan_No = cs.getInt(7);
//                int errcode = cs.getInt(18);
                
                cs =
                		  con.prepareCall("call FAS_REMITTANCE_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?,?::numeric,?,?,?::numeric,?,?,?,?::numeric,?,?::numeric)");
                		                cs.setInt(1, cmbAcc_UnitCode);
                		                cs.setInt(2, cmbOffice_code);
                		                cs.setInt(3, txtCash_Month_hid);
                		                cs.setInt(4, txtCash_year);
                		                cs.setDate(5, txtCrea_date);
                		                cs.setString(6, Remit_type);
                		                cs.setInt(7, txtChallan_No);
                		                cs.setDate(8, txtChallan_Date);
                		                cs.setDouble(9, txtAmount);
                		                cs.setString(10, txtVerified);
                		                cs.setDate(11, verify_Date);
                		                cs.setInt(12, txtVerified_Auth);
                		                cs.setString(13, txtRemarks);
                		                cs.setString(14, update_user);
                		                cs.setTimestamp(15, ts);
                		                cs.setLong(16, txtBankAccountNo);
                		                
                		                cs.setString(17, "insert");
                		                cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                		                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
                		                //cs.setNull(7, java.sql.Types.NUMERIC);
                		                cs.setNull(18, java.sql.Types.NUMERIC);
                		                cs.execute();

                		                //txtChallan_No = cs.getInt(7);
                		                //int errcode = cs.getInt(18);
                		                 txtChallan_No = cs.getBigDecimal(18).intValue();
                		                int errcode = cs.getBigDecimal(18).intValue();
                cs.close();
                System.out.println("SQLCODE from remittance:::" + errcode);
                System.out.println("challan number..." + txtChallan_No);

                //int errcode=0;//nk
                if (errcode != 0) {
                    con.rollback();
                    System.out.println("The Fund Remittance failed to INSERT values in remit table");
                    sendMessage(response, "The Fund Remittance failed ", "ok");

                    return;
                }

                else {
                    String sql_update = "";
                    if (cmbAcc_UnitCode == 5)
                        sql_update =
                                "update FAS_FUND_RECEIPT_BY_HO set CHALLAN_NO=?, CHALLAN_DATE=?, REMITTANCE_STATUS='Y' where REMITTANCE_STATUS='N'  and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_NO=? and RECEIPT_DATE=?";
                    else
                        sql_update =
                                "update FAS_FUND_RECEIPT_BY_OFFICE set CHALLAN_NO=?, CHALLAN_DATE=?, REMITTANCE_STATUS='Y' where  REMITTANCE_STATUS='N' and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_NO=? and RECEIPT_DATE=?";
                    String sel[] = request.getParameterValues("sel");
                    //String seq_No[]=request.getParameterValues("seq_No");
                    String Rec_No[] = request.getParameterValues("Rec_No");
                    String Rec_Date[] = request.getParameterValues("Rec_Date");
                    int txtRec_No = 0;
                    Date txtRec_Date = null;
                    ps = con.prepareStatement(sql_update);
                    System.out.println("after update length.." + sel.length);
                    //
                    if (sel.length > 0) {

                        for (int i = 0, k = 0; i < sel.length; k++) {
                            System.out.println("sel[" + i + "],,.." + sel[i]);
                            System.out.println("Rec_No[" + k + "],,.." +
                                               Rec_No[k]);

                            if (Integer.parseInt(sel[i]) ==
                                Integer.parseInt(Rec_No[k])) // It's true ,only when selected checkbox value and Sl.NO hidden field equal
                            {
                                System.out.println("value of i.." + i);
                                System.out.println("sel.." + sel[i]);
                                //System.out.println("seq_No.."+seq_No[k]);
                                System.out.println("Rec_No.." + Rec_No[k]);
                                System.out.println("Rec_Date.." + Rec_Date[k]);


                                try {
                                    txtRec_No = Integer.parseInt(Rec_No[k]);
                                } catch (Exception e) {
                                    System.out.println("exception in trans " +
                                                       e);
                                }
                                if (!Rec_Date[k].equalsIgnoreCase("")) {
                                    sd = Rec_Date[k].split("/");
                                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                                    d = c.getTime();
                                    txtRec_Date = new Date(d.getTime());
                                }
                                ps.setInt(1, txtChallan_No);
                                ps.setDate(2, txtChallan_Date);
                                ps.setInt(3, cmbAcc_UnitCode);
                                ps.setInt(4, cmbOffice_code);
                                ps.setInt(5, txtRec_No);
                                ps.setDate(6, txtRec_Date);
                                ps.executeUpdate();
                                txtRec_No = 0;
                                txtRec_Date = null;
                                i++;
                            } else
                                continue;
                        }
                    } else {
                        con.rollback();
                        System.out.println("The Fund Remittance failed to update FAS_RECEIPT_MASTER values in remit table");
                        sendMessage(response, "The Fund Remittance failed ",
                                    "ok");
                        return;
                    }
                    ps.close();

                    System.out.println("last ");
                    con.commit();
                    System.out.println("here after PDF");
                    sendMessage(response,
                                "The Fund Remittance done successfully with Challan number " +
                                txtChallan_No, "ok");
                    System.out.println("after send message");
                }

            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
                sendMessage(response, "The Fund Remittance failed ", "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done here");
                try {
                 con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
            }
        }

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
            0, txtCash_year = 0;
        Date txtCrea_date = null;

        if (strCommand.equalsIgnoreCase("PendingReceipts")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            long txtBankAccountNo = 0;
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);
            xml = "<response><command>PendingReceipts</command>";

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);


            /** Find Cashbook Month and Year */
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


            /*   try{txtCash_year=Integer.parseInt(sd[2]);}
                            catch(Exception e){System.out.println("exception"+e );}
                            System.out.println("txtCash_year "+txtCash_year);

                            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                            catch(Exception e){System.out.println("exception"+e );}
                            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);

                //////               --------- Finding cash book month HERE -----------
                 String[] sp=request.getParameter("txtCrea_date").split("/");
                 System.out.println(sp[0]+" "+sp[1]+" "+sp[2]);
                 int check_year=Integer.parseInt(sp[2]);                 // to check in while loop
                 int check_day=Integer.parseInt(sp[0]);                    // to check in while loop
                             System.out.println(Integer.parseInt(sp[2]));
                             System.out.println("here"+check_year);

                  String check_date=request.getParameter("txtCrea_date");
                  sp=request.getParameter("txtCrea_date").split("/");
                  check_date=sp[2]+"/"+sp[1]+"/"+sp[0];

                  System.out.println(check_date); // to check in while loop with d/b date it converted to yyyy/mm/dd form
                  try
                  {
                      String sql1="select FINANCIAL_YEAR," +
                      "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ," +
                      "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
                      "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  " +
                      "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";

                   // date is taken as string from database in above format for checking with receipt date variable ( check_date is string type)
                   // checking of dates performed in form of String checking
                   ps=con.prepareStatement(sql1);
                   rs=ps.executeQuery();
                   int Begin_yr,End_yr;
                  while(rs.next())
                  {
                      String[] yr=rs.getString("FINANCIAL_YEAR").split("-");
                       Begin_yr=Integer.parseInt(yr[0]);
                       End_yr=Integer.parseInt(yr[1]);
                                       System.out.println("while");
                                       System.out.println(Begin_yr+ " "+End_yr);
                                       System.out.println(rs.getString("mar_beg")+" "+rs.getString("mar_end"));

                      if(check_year==Begin_yr)          //   to check which financial year it belongs
                      {
                          if(txtCash_Month_hid>=4 && txtCash_Month_hid<=12)
                          {
                                   System.out.println("if 4");
                                   if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                                   {
                                       txtCash_Month_hid=03;
                                   System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                                   }
                                   else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                                   {
                                       txtCash_Month_hid=04;
                                   System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                                   }
                                   else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                                   {
                                       txtCash_Month_hid=txtCash_Month_hid+1;
                                       if(txtCash_Month_hid>12)
                                           {
                                               txtCash_Month_hid=1;
                                               txtCash_year=txtCash_year+1;
                                               System.out.println("hello"+txtCash_year);
                                           }
                                       System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                                   }
                                   else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                                   {
                                      //txtCash_Month_hid=txtCash_Month_hid;
                                      System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                                   }
                          }

                      }
                      else  if(check_year==End_yr)
                      {
                          if(txtCash_Month_hid>=1 && txtCash_Month_hid<=3)
                          {
                              txtCash_year=End_yr;System.out.println("if 3");
                              if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                               {
                                   txtCash_Month_hid=03;
                               System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                               }
                               else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                               {
                                   txtCash_Month_hid=04;
                               System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                               }
                               else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                               {
                                   txtCash_Month_hid=txtCash_Month_hid+1;
                                   if(txtCash_Month_hid>12)            // No chance for this condition
                                   {
                                   txtCash_Month_hid=1;
                                   txtCash_year=txtCash_year+1;
                                   System.out.println("hello"+txtCash_year);
                                   }
                                   System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                               }
                              else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                              {
                                  //txtCash_Month_hid=txtCash_Month_hid;
                                  System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                              }
                          }
                      }
                  }
                  ps.close();
                  rs.close();
                  }
                  catch(Exception e)
                  {
                  sendMessage(response," Finding Cash book month failed","ok");
                  System.out.println("exception"+e);
                  return;
                  }
           */

            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);


            try {
                String sqlps = "";
                if (cmbAcc_UnitCode == 5)
//                    sqlps =
//"select a.RECEIPT_NO,to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date, " +
//" trim(c.ACCOUNTING_UNIT_NAME) || ' -' || d.OFFICE_NAME   as RECEIVED_FROM, a.CHEQUE_DD_NO," +
//" to_char( a.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqDD_date,b.BANK_NAME, " +
//" trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as  AMOUNT from FAS_FUND_RECEIPT_BY_HO a,FAS_MST_BANKS b," +
//" FAS_MST_ACCT_UNITS c,COM_MST_OFFICES d  where  RECEIPT_STATUS!='C' and  REMITTANCE_STATUS='N' and " +
//" a.OFFICE_BANK_ID=b.BANK_ID  and a.RECEIVED_FROM_OFFICE_ID=d.OFFICE_ID " +
//" and a.RECEIVED_FROM_HO_UNIT_ID=c.ACCOUNTING_UNIT_ID " +
//" and a.ACCOUNTING_UNIT_ID=? and  a.ACCOUNTING_FOR_OFFICE_ID=? and  a.RECEIPT_DATE<=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and a.HO_ACCOUNT_NO=? " +
//" order by a.RECEIPT_NO ";
                	
                	sqlps="SELECT  A "
                			+ "	.RECEIPT_NO, "
                			+ "	to_char( A.RECEIPT_DATE, 'DD/MM/YYYY' ) AS rec_date, "
                			+ "  case when d.OFFICE_NAME is not null then d.OFFICE_NAME else c.accounting_unit_name end AS RECEIVED_FROM,	"
                			+ "	A.CHEQUE_DD_NO, "
                			+ "	to_char( A.CHEQUE_DD_DATE, 'DD/MM/YYYY' ) AS cheqDD_date, "
                			+ "	b.BANK_NAME, "
                			+ "	TRIM ( to_char( A.TOTAL_AMOUNT, '99999999999999.99' ) ) AS AMOUNT "
                			+ "FROM "
                			+ "	FAS_FUND_RECEIPT_BY_HO A left join 	FAS_MST_BANKS b on A.OFFICE_BANK_ID = b.BANK_ID "
                			+ "	left join COM_MST_OFFICES d on A.RECEIVED_FROM_OFFICE_ID = d.OFFICE_ID "
                			+ "	left join FAS_MST_ACCT_UNITS C on A.RECEIVED_FROM_HO_UNIT_ID = C.ACCOUNTING_UNIT_ID "
                			+ "WHERE "
                			+ "	RECEIPT_STATUS != 'C' "
                			+ "	AND REMITTANCE_STATUS = 'N' "
                			+ " and a.ACCOUNTING_UNIT_ID=? "
                			+ "	AND A.ACCOUNTING_FOR_OFFICE_ID = ? "
                			+ "	AND A.RECEIPT_DATE <= ? "
                			+ "	AND CASHBOOK_YEAR = ? "
                			+ "	AND CASHBOOK_MONTH = ? "
                			+ "	AND A.HO_ACCOUNT_NO = ? "
                			+ "ORDER BY "
                			+ "	A.RECEIPT_NO ";

                else
                    sqlps =
"select a.RECEIPT_NO,to_char(a.RECEIPT_DATE,'DD/MM/YYYY') as rec_date, " +
" 'Banking Section'   as RECEIVED_FROM, a.CHEQUE_DD_NO," +
" to_char( a.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqDD_date,b.BANK_NAME," +
" trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as  AMOUNT from FAS_FUND_RECEIPT_BY_OFFICE a,FAS_MST_BANKS b" +
" where  RECEIPT_STATUS!='C' and  REMITTANCE_STATUS='N' " +
" and a.HO_BANK_ID=b.BANK_ID  " +
" and a.ACCOUNTING_UNIT_ID=? and  a.ACCOUNTING_FOR_OFFICE_ID=? and  a.RECEIPT_DATE<=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and a.OFFICE_ACCOUNT_NO=? " +
" order by a.RECEIPT_NO ";


                ps = con.prepareStatement(sqlps);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtCash_year);
                ps.setInt(5, txtCash_Month_hid);
                ps.setLong(6, txtBankAccountNo);
                rs = ps.executeQuery();
                int cnt = 0;
                double amt = 0.0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    xml =
 xml + "<Rec_No>" + rs.getInt("RECEIPT_NO") + "</Rec_No>" + "<Rec_Date>" +
   rs.getString("rec_date") + "</Rec_Date>" + "<Rec_From>" +
   rs.getString("RECEIVED_FROM") + "</Rec_From>" + "<cheqDD_No>" +
   rs.getString("CHEQUE_DD_NO") + "</cheqDD_No>" + "<cheqDD_date>" +
   rs.getString("cheqDD_date") + "</cheqDD_date>" + "<Bank_name>" +
   rs.getString("BANK_NAME") + "</Bank_name>" + "<Total_amt>" +
   rs.getString("AMOUNT") + "</Total_amt>";
                    //amt=amt+rs.getDouble("AMOUNT");
                    cnt++;
                }
                //System.out.println("Double.toString(amt).."+Double.toString(amt));
                //xml=xml+"<Remitted_amount>"+amt+"</Remitted_amount>";
                System.out.println("count" + cnt);
                if (cnt == 0)
                    xml =
 "<response><command>PendingReceipts</command>" + "<flag>failure</flag>";
                //else
                // xml=xml+"<Acc_headOf_Receipt>"+rs.getInt("ACCOUNT_HEAD_CODE")+"</Acc_headOf_Receipt>"+"<Remitted_amount>"+amt+"</Remitted_amount>";
                // use of ACCOUNT_HEAD_CODE is , used to insert into payment transaction table as ACCOUNT_HEAD_CODE taken from receipt system,
                // after insert into remittance followed by pay master table..
            } catch (Exception e) {
                System.out.println("catch..HERE.in failure to retrieve." + e);
                xml =
 "<response><command>PendingReceipts</command>" + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }


    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            System.out.println("inside send msg");
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("Excep" + e);
        }
    }
}

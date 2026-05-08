package Servlets.FAS.FAS1.RemittanceSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.ByteArrayOutputStream;
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
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class BankRemit_Create extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

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
                "N", Remit_type = "B";
            int txtVerified_Auth = 0;
            Date verify_Date = null;
int count=0;
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


            txtChallan_Date =
                    txtCrea_date; // Challan date and Remittance date is same date as creation date only


           /* try {
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);
            if(txtCash_Acc_code==0)  {
          	  sendMessage(response,
                        "Head Code Not valid " +txtCash_Acc_code, "ok");
          	  return;
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


            /*
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
              //sendMessage(response," Finding Cash book month failed","ok");
              System.out.println("exception"+e);
              return;
              }

              */


            try {
                System.out.println("start");
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("before procedure call");
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
                 txtChallan_No = cs.getBigDecimal(7).intValue();
                int errcode = cs.getBigDecimal(18).intValue();
                cs.close();
                System.out.println("SQLCODE from remittance:::" + errcode);
                System.out.println("challan number..." + txtChallan_No);

                if (errcode != 0) {
                    con.rollback();
                    System.out.println("The Bank Remittance failed to INSERT values in remit table");
                    sendMessage(response, "The Bank Remittance failed ", "ok");

                    return;
                }

                else {
                    String sql_update =
                        "update FAS_RECEIPT_MASTER set CHALLAN_NO=?, CHALLAN_DATE=?, REMITTANCE_STATUS='Y' where RECEIPT_TYPE='B'  and CREATED_BY_MODULE='BR' and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_NO=? and RECEIPT_DATE=?";
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

                        for (int k = 0; k < sel.length; k++) {
                            System.out.println("sel[" + k + "],,.." + sel[k]);
                            System.out.println("Rec_No[" + k + "],,.." +
                                               Rec_No[k]);

                            if (Integer.parseInt(sel[k]) ==
                                Integer.parseInt(Rec_No[k])) // It's true ,only when selected checkbox value and Sl.NO hidden field equal
                            {

                                System.out.println("value of i.." + k);
                                System.out.println("sel.." + sel[k]);
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
                                count= ps.executeUpdate();
                                txtRec_No = 0;
                                txtRec_Date = null;
                             if(count > 0)count=1;
                             else count=0;
                            } 
                            System.out.println("Count value  ***************** "+count);
                        }
                    } else {
                        con.rollback();
                        System.out.println("The Bank Remittance failed to update FAS_RECEIPT_MASTER values in remit table");
                        sendMessage(response, "The Bank Remittance failed ",
                                    "ok");
                       return;
                    }
                  //  ps.close();


                    //sendMessage(response,"The Bank Remittance done successfully","ok");

                    /////////////////////-------------------------- Print Challan------------------

                    //////////////////---------------------------- End -----------------
                    System.out.println(" Final Count value  >>>>>>>>>>>>>>>>. "+count);
                    System.out.println("last ");
                    if(count > 0)
                    { 
                    	 try { con.commit(); } catch (SQLException sqle) {
                    		 sqle.printStackTrace();
                    	 }
                    sendMessage(response,"The Bank Remittance done successfully","ok");
                    return;
                }
                }

            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                	 System.out.println("Excep 111");
                	sqle.printStackTrace();
                   
                }
                sendMessage(response, "The Bank Remittance failed ", "ok");
            
                System.out.println("Exception occur due to " + e);
                return;
            } finally {
                System.out.println("done here");
                try {
                 con.setAutoCommit(true);
                } catch (SQLException sqle) {
                	 System.out.println("Excep 111");
                	sqle.printStackTrace();
                   
                }
            }
        }

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        PreparedStatement ps = null, ps2 = null;
        ResultSet rs = null, rs2 = null;

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

            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);


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

          
            System.out.println("test");

            try {
               
                String q1 ="                                                    " +
                       "select * from                                                               " +
                       "  (                                                                         " +
                       "    select                                                                  " +
                       "        RECEIPT_DATE  ,                                                     " +
                       "        CASHBOOK_YEAR,                                                      " +
                       "        CASHBOOK_MONTH,                                                     " +
                       "        RECEIPT_NO,                                                         " +
                       "        to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date ,                    " +
                       "        RECEIVED_FROM,                                                      " +
                       "        trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT     " +
                       "    from                                                                    " +
                       "        FAS_RECEIPT_MASTER                                                  " +
                       "    where " + "             RECEIPT_TYPE='B' " +
                       "        and REMITTANCE_STATUS='N'" +
                       "        and CREATED_BY_MODULE='BR' " +
                       "        and RECEIPT_STATUS!='C' " +
                       "        and ACCOUNTING_UNIT_ID=? " +
                       "        and ACCOUNTING_FOR_OFFICE_ID=? " +
                       "        and RECEIPT_DATE<=? " +
                       "        and CASHBOOK_YEAR=? " +
                       "        and CASHBOOK_MONTH=? " +
                       "        and ACCOUNT_NO=? " +
                       "        and REMITTANCE_IN_CURR_MONTH='Y'  " +
                       "   union all " + "  " + "   select  " +
                       "        RECEIPT_DATE, " + "        CASHBOOK_YEAR," +
                       "        CASHBOOK_MONTH," + "        RECEIPT_NO," +
                       "        to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date , " +
                       "        RECEIVED_FROM," +
                       "        trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT" +
                       "  from " + "        FAS_RECEIPT_MASTER" + "  where " +
                       "         RECEIPT_TYPE='B'        " +
                       "     and REMITTANCE_STATUS='N'   " +
                       "     and CREATED_BY_MODULE='BR'  " +
                       "     and RECEIPT_STATUS!='C'     " +
                       "     and ACCOUNTING_UNIT_ID=?           " +
                       "     and ACCOUNTING_FOR_OFFICE_ID=?     " +
                       "     and RECEIPT_DATE<=?                " +
                       "     and ACCOUNT_NO=?                   " +
                       "     and CASHBOOK_MONTH < ?             " +
                       "     and CASHBOOK_YEAR <= ?             " +
                    //   "     and REMITTANCE_IN_CURR_MONTH='Y'   " +
                       "     and REMITTANCE_IN_CURR_MONTH='N'   " +
                       "  )    as ps                                 " +
                       "  order by RECEIPT_DATE                 " + "  ";

                System.out.println("year and month :" + txtCash_year + "  " +
                                   txtCash_Month_hid);
                System.out.println(q1);
                ps =  con.prepareStatement(q1);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtCash_year);
                ps.setInt(5, txtCash_Month_hid);
                ps.setLong(6, txtBankAccountNo);

                ps.setInt(7, cmbAcc_UnitCode);
                ps.setInt(8, cmbOffice_code);
                ps.setDate(9, txtCrea_date);
                ps.setLong(10, txtBankAccountNo);
                ps.setInt(11, txtCash_Month_hid);
                ps.setInt(12, txtCash_year);


                rs = ps.executeQuery();
                int cnt = 0;
                double amt = 0.0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    String cheqNos = "", cheqdates = "", banknames = "";

                    xml =
 xml + "<Rec_No>" + rs.getInt("RECEIPT_NO") + "</Rec_No>" + "<Rec_Date>" +
   rs.getString("rec_date") + "</Rec_Date>" + "<Rec_From><![CDATA[" +
   rs.getString("RECEIVED_FROM") + "]]></Rec_From>" + "<Total_amt>" +
   rs.getString("TOTAL_AMOUNT") + "</Total_amt>";
                    //amt=amt+rs.getDouble("TOTAL_AMOUNT");
                    ps2 =
 con.prepareStatement("select trs.CHEQUE_DD_NO,to_char(trs.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheqDD_date,trs.BANK_NAME" +
                      " from FAS_RECEIPT_MASTER mas,FAS_RECEIPT_TRANSACTION trs " +
                      "where mas.ACCOUNTING_UNIT_ID=trs.ACCOUNTING_UNIT_ID and mas.ACCOUNTING_FOR_OFFICE_ID=trs.ACCOUNTING_FOR_OFFICE_ID " +
                      "and mas.CASHBOOK_YEAR=trs.CASHBOOK_YEAR and  mas.CASHBOOK_MONTH=trs.CASHBOOK_MONTH and  mas.RECEIPT_NO=trs.RECEIPT_NO " +
                      " and  mas.RECEIPT_TYPE='B' and mas.REMITTANCE_STATUS='N' and mas.RECEIPT_STATUS!='C'  and mas.CREATED_BY_MODULE='BR'  " +
                      " and mas.ACCOUNTING_UNIT_ID=? and  mas.ACCOUNTING_FOR_OFFICE_ID=? and  mas.CASHBOOK_YEAR=? and mas.CASHBOOK_MONTH=? and mas.ACCOUNT_NO=? " +
                      " and mas.RECEIPT_NO=? GROUP BY trs.CHEQUE_DD_NO,trs.CHEQUE_DD_DATE,BANK_NAME");
                    ps2.setInt(1, cmbAcc_UnitCode);
                    ps2.setInt(2, cmbOffice_code);
                    ps2.setInt(3, rs.getInt("CASHBOOK_YEAR"));
                    ps2.setInt(4, rs.getInt("CASHBOOK_MONTH"));
                    ps2.setLong(5, txtBankAccountNo);
                    ps2.setInt(6, rs.getInt("RECEIPT_NO"));
                    rs2 = ps2.executeQuery();
                    int r = 0;

                    while (rs2.next()) {
                        if (r !=
                            0) // to get the CHEQUE_DD_NO,cheqDD_date,BANK_NAME with "," (comma) , if more than one available for one voucher
                        {
                            cheqNos =
                                    rs2.getString("CHEQUE_DD_NO") + "," + cheqNos;
                            cheqdates =
                                    rs2.getString("cheqDD_date") + "," + cheqdates;
                            banknames =
                                    rs2.getString("BANK_NAME") + "," + banknames;
                        } else {
                            cheqNos = rs2.getString("CHEQUE_DD_NO");
                            cheqdates = rs2.getString("cheqDD_date");
                            banknames = rs2.getString("BANK_NAME");
                        }
                        r++;
                    }
                    /*
                         while(rs2.next())
                         {

                                 cheqNos=rs2.getString("CHEQUE_DD_NO");
                                 cheqdates=rs2.getString("cheqDD_date");
                                 banknames=rs2.getString("BANK_NAME");

                             r++;
                         }*/
                    rs2.close();
                    ps2.close();
                    xml =
 xml + "<cheqDD_No>" + cheqNos + "</cheqDD_No>" + "<cheqDD_date>" + cheqdates +
   "</cheqDD_date>" + "<Bank_name>" + banknames + "</Bank_name>";

                    cnt++;
                }
                // System.out.println("Double.toString(amt).."+Double.toString(amt));
                // xml=xml+"<Remitted_amount>"+amt+"</Remitted_amount>";
                // System.out.println("count"+cnt);
                if (cnt == 0)
                    xml =
 "<response><command>PendingReceipts</command>" + "<flag>failure</flag>";
                rs.close();
                ps.close();

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
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
			return;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Excep" + e);
		}
		
	}
}

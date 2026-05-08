package Servlets.FAS.FAS1.AuthorizationSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.Security.classes.UserProfile;

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

public class Authorization_JAO_Create extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
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
        CallableStatement cs1 = null;
		PreparedStatement cs2=null;
		ResultSet rs;
        String xml = "";
       
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
            xml = "<response><command>Add</command>";
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            Date txtCrea_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

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


            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);


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
            System.out.println("b4 getting month and year");
            try{txtCash_year=Integer.parseInt(sd[2]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_year "+txtCash_year);

                        try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_Month_hid "+txtCash_Month_hid);


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
             sendMessage(response,"The Authorization has failed to find cash book month","ok");
             System.out.println("exception"+e);
             }
         */

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                String txtReferNO_edit = "", txtRemak_edit =
                    "", cmbSubSystemType = "", txtRefdate =
                    ""; //for cross reference
                Date txtReferDate_edit = null;
                String radAuth_MC = "";
                int txtAuth_By = 0;
                UserProfile empProfile =
                    (UserProfile)session.getAttribute("UserProfile");

                txtAuth_By = empProfile.getEmployeeId();

                cmbSubSystemType = request.getParameter("cmbSubSystemType");
                System.out.println("cmbSubSystemType.. " + cmbSubSystemType);

                if (cmbSubSystemType.equalsIgnoreCase("BR_S"))
                	///    **********************
                { cmbSubSystemType = "BR";}
                else if (cmbSubSystemType.equalsIgnoreCase("CR_S"))
                {  cmbSubSystemType = "CR";}
                
                
                else if (cmbSubSystemType.equalsIgnoreCase("TDAOS"))
                {  cmbSubSystemType = "TDAO";}
                else if (cmbSubSystemType.equalsIgnoreCase("TCAOS"))
                {  cmbSubSystemType = "TCAO";}
                else if (cmbSubSystemType.equalsIgnoreCase("TDAAS"))
                {  cmbSubSystemType = "TDAA";}
                else if (cmbSubSystemType.equalsIgnoreCase("TCAAS"))
                {  cmbSubSystemType = "TCAA";}
                else if (cmbSubSystemType.equalsIgnoreCase("TCAABS"))
                {  cmbSubSystemType = "TCAA";}
                else if (cmbSubSystemType.equalsIgnoreCase("TDAABS"))
                {  cmbSubSystemType = "TDAA";}
                
                
                txtReferNO_edit = request.getParameter("txtReferNO_edit");
                System.out.println("txtReferNO_edit  " + txtReferNO_edit);

                txtRemak_edit = request.getParameter("txtRemak_edit");
                System.out.println("txtRemak_edit  " + txtRemak_edit);

                txtRefdate = request.getParameter("txtReferDate_edit");

                if(cmbSubSystemType.equalsIgnoreCase("DCB"))
                {
                	cs2=con.prepareStatement("select cashbook_year,cashbook_month from fas_receipt_master where accounting_for_office_id =? and to_char(receipt_date,'dd/mm/yyyy')=? and receipt_no=?");
                	cs2.setInt(1, cmbOffice_code);
                	cs2.setString(2, Receipt_Creation_Date);
                	cs2.setInt(3,txtVoucher_No);
                	rs=cs2.executeQuery();
                	while(rs.next()) {
                		txtCash_year=rs.getInt("cashbook_year");
                		txtCash_Month_hid=rs.getInt("cashbook_month");
                		
                	}
                	System.out.println(txtCash_year);
                	System.out.println(txtCash_Month_hid);
                }
                if (!txtRefdate.equalsIgnoreCase("")) {
                    sd = request.getParameter("txtReferDate_edit").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtReferDate_edit = new Date(d.getTime());
                }

                radAuth_MC = request.getParameter("radAuth_MC");
                System.out.println("radAuth_MC.. " + radAuth_MC);

                cs1 =
 con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
                cs1.setInt(1, cmbAcc_UnitCode);
                cs1.setInt(2, txtCash_year);
                cs1.setInt(3, txtCash_Month_hid);
                cs1.setInt(4, txtVoucher_No);
                cs1.setInt(5, cmbOffice_code);
                cs1.setDate(6, txtCrea_date);
                cs1.setString(7, cmbSubSystemType);
                cs1.setString(8, txtReferNO_edit);
                cs1.setDate(9, txtReferDate_edit);
                cs1.setString(10, txtRemak_edit);
                cs1.setInt(11, txtAuth_By);
                cs1.setString(12, "allow_modify");
                cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
                cs1.setInt(13,0);
                cs1.setString(14, update_user);
                cs1.setTimestamp(15, ts);
                cs1.setString(16, radAuth_MC);
                //cs1.setString(16,radAuth_MC);
                cs1.execute(); // insertion into cross reference table
                //int errcode = cs1.getInt(13);
                int errcode = cs1.getBigDecimal(13).intValue();
              System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                System.out.println("cmbOffice_code " + cmbOffice_code);
                System.out.println("txtCrea_date " + txtCrea_date);
                System.out.println("txtCash_year " + txtCash_year);
                System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
                System.out.println("txtVoucher_No" + txtVoucher_No);
                System.out.println("cmbSubSystemType" + cmbSubSystemType);
                System.out.println("txtReferNO_edit" + txtReferNO_edit);
                System.out.println("txtReferDate_edit" + txtReferDate_edit);
                System.out.println("txtRemak_edit" + txtRemak_edit);
                System.out.println("txtAuth_By" + txtAuth_By);
                System.out.println("radAuth_MC" + radAuth_MC);

                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    con.rollback();
                    sendMessage(response, "Authorization has failed ", "ok");
                    xml = xml + "<flag>failure</flag>";
                    return;
                }
                con.commit();
                if (radAuth_MC.equalsIgnoreCase("M"))
                    sendMessage(response,
                                "The Modification has allowed to the voucher number '" +
                                txtVoucher_No + "' ", "ok");
                else
                    sendMessage(response,
                                "The Cancellation has allowed to the voucher number '" +
                                txtVoucher_No + "' ", "ok");
                return;
            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
                sendMessage(response, "The Authorization has failed ", "ok");
               
                System.out.println("Exception occur due to " + e);
                return;
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
            }

        }
    }

    /**
     * GET Method
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public

    void doGet(HttpServletRequest request,
               HttpServletResponse response) throws ServletException,
                                                    IOException {


        /** Session Checking  */
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

        /** Database Connection  */
        Connection con = null;
        ResultSet rs = null,rs5=null;
        PreparedStatement ps = null,ps5=null;
        //String xml="";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        int gcode=0;
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

        /** Receiving Command  */
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        /** Variables Declaration  */
        String xml = "", cmbSubSystemType = "",sub_type="";
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        Date txtCrea_date = null;
        Calendar c;

        /** Get Accounting Unit ID  */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

        /** Get Accounting Office ID  */
        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("cmbOffice_code " + cmbOffice_code);

        /** Get Sub System Type */
        cmbSubSystemType = request.getParameter("cmbSubSystemType");
        System.out.println("cmbSubSystemType.. " + cmbSubSystemType);

//        sub_type = request.getParameter("sub_type");
//        System.out.println("sub_type.. " + sub_type);

        int cashbookMonth=0;
        int cashbookYear=0;

        /** Get Date */
        try {
            String[] sd = request.getParameter("txtCrea_date").split("/");
            cashbookMonth=Integer.parseInt(sd[1]);
            cashbookYear=Integer.parseInt(sd[2]);
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }


        /** Load Voucher Number for particular Date  */
        if (strCommand.equalsIgnoreCase("load_Voucher_No")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            xml = "<response><command>load_Voucher_No</command>";
            String QueryType = "";
            if (cmbSubSystemType.equalsIgnoreCase("CR"))
                QueryType ="select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and sub_ledger_type_code <> 14 and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='M' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null AND (CB_REF_TYPE IS NULL OR CB_REF_TYPE ='')  AND ( REMARKS NOT LIKE 'GPF RJV Created for the req no%' or REMARKS is null ) ";
//    13-03-2020                    "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='M' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null  AND ( REMARKS NOT LIKE 'GPF RJV Created for the req no%' or REMARKS is null ) ";
            // else if(cmbSubSystemType.equalsIgnoreCase("BR"))
            // QueryType="select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='M' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("BR"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='B' and sub_ledger_type_code <> 14 and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='M' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null AND  ( REMARKS NOT LIKE 'GPF RJV Created for the req no%' or REMARKS is null ) ";
            else if (cmbSubSystemType.equalsIgnoreCase("CR_S")) // cash Reclassification
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='S' and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("BR_S")) // Bank Reclassification
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='S' and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("GJV"))
                QueryType =
                        "select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='GJV' and CB_REF_TYPE is null and JOURNAL_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("LJV")) // Once payment done for any of the transaction journal, CB_REF_TYPE is set to 'P'.. so after that you can't do any changes in that journal voucher
                QueryType =
                        "select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=?  and CREATED_BY_MODULE='LJV' and CB_REF_TYPE is null and JOURNAL_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("BPP"))
                // changed on 20Jun17 BS in order to consider the cheq cancel status
            	//QueryType =
                  //      "select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPP' and PAYMENT_STATUS!='C'";
            	QueryType ="SELECT DISTINCT(M.VOUCHER_NO) " +
            			"FROM FAS_PAYMENT_MASTER M, " +
            			"  FAS_PAYMENT_TRANSACTION T " +
            			"WHERE M.ACCOUNTING_UNIT_ID     =T.ACCOUNTING_UNIT_ID " +
            			"AND M.ACCOUNTING_FOR_OFFICE_ID = T.ACCOUNTING_FOR_OFFICE_ID " +
            			"AND M.VOUCHER_NO               = T.VOUCHER_NO " +
            			"AND M.CASHBOOK_MONTH           = T.CASHBOOK_MONTH " +
            			"AND M.CASHBOOK_YEAR            = T.CASHBOOK_YEAR " +
            			"AND M.ACCOUNTING_UNIT_ID       =? " +
            			"AND M.ACCOUNTING_FOR_OFFICE_ID =? " +
            			"AND M.PAYMENT_DATE             =? " +
            			"AND M.PAYMENT_TYPE             ='B' " +
            			"AND M.CREATED_BY_MODULE        ='BPP' " +
            			"AND M.PAYMENT_STATUS!='C' " +
            			"AND (T.CHEQ_CANCEL_STATUS     IS NULL " +
            			"OR T.CHEQ_CANCEL_STATUS!= 'Y') " ;
            			
            else if (cmbSubSystemType.equalsIgnoreCase("BPF"))
                QueryType =
                        "select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPF' and MODE_OF_CREATION <> 'A' and PAYMENT_STATUS!='C'";
            else if(cmbSubSystemType.equalsIgnoreCase("CHQD"))
                QueryType="SELECT CHEQUE_DD_NO as VOUCHER_NO FROM FAS_CHEQUE_DISHONOUR WHERE ACCOUNTING_UNIT_ID  =? AND ACCOUNTING_FOR_OFFICE_ID=? AND Cheq_Dishonour_Date =? AND (REVERT_CHEQDISHONOUR_STATUS!='Y' or REVERT_CHEQDISHONOUR_STATUS is null) ";
            else if(cmbSubSystemType.equalsIgnoreCase("CHQC"))
                QueryType="Select Cheque_Dd_No as VOUCHER_NO From Fas_Cheque_Cancel Where Accounting_Unit_Id         =? And Accounting_For_Office_Id     =? AND CHEQ_CANCEL_DATE          =? And (REVERT_CHEQCANCEL_STATUS!='Y' Or REVERT_CHEQCANCEL_STATUS  Is Null)";
            else if(cmbSubSystemType.equalsIgnoreCase("ADM"))
               // QueryType="select VOUCHER_NO from FAS_ADJUST_MEMO_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and MEMO_STATUS!='C' and ACCEPTANCE_STATUS is null and VOUCHER_NO not in(select VOUCHER_NO from fas_adjust_memo_trn where acceptance_status!=null)";
            	QueryType="SELECT distinct m.VOUCHER_NO,t.ACCEPTANCE_STATUS  "+
							" FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t "+
            		" WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
            		" 	and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
            		" 	and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
            		" 	and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
            		" 	and m.voucher_no=t.voucher_no "+
            		" 	and m.ACCOUNTING_UNIT_ID    =? "+
            		" 	AND m.ACCOUNTING_FOR_OFFICE_ID=? "+
            		" 	AND m.VOUCHER_DATE            =? "+
            		"   and t.account_head_code    in ('900201','610101')" + 
            		"   and t.acceptance_status is null" +
            		" 	AND m.MEMO_STATUS!='C'";
            //unit side
            else if(cmbSubSystemType.equalsIgnoreCase("ADM_A"))
               QueryType="SELECT t.accept_voucher_no as VOUCHER_NO "+                        //accept vr.no added insteaded of vr.no//MK@27-01-2021
						"	FROM FAS_ADJUST_MEMO_MST m, "+
							  " FAS_ADJUST_MEMO_TRN t "+
               " WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID "+
               " 			AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
               " 			AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR "+
               " 			AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
               " 			AND m.voucher_no              =t.voucher_no "+
               " 			AND t.FOR_ACCOUNTING_UNIT_ID  =? "+
               " 			AND t.ACCEPT_VOUCHER_DATE     =? "+
               " 			AND m.MEMO_STATUS<>'C' "+
               " 			and t.ACCEPTANCE_STATUS='Y' "+
              // " 				and t.ADVICE_TRANSFER_FREEZE='N'";
               "			AND (T.ADVICE_TRANSFER_FREEZE  ='N'\r\n" + 
               "			or T.ADVICE_TRANSFER_FREEZE is null)";
           
            
            else if (cmbSubSystemType.equalsIgnoreCase("NP"))
                QueryType =
                        "select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='NP' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("FTH"))
                //QueryType =  "select VOUCHER_NO from FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'";
                QueryType="SELECT distinct m.VOUCHER_NO\n" + 
                " FROM FAS_FUND_TRF_FROM_HO_MASTER m,FAS_FUND_TRF_FROM_HO_TRN t\n" + 
                " WHERE  m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID\n" + 
                " and  m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID\n" + 
                " and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR\n" + 
                " and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH\n" + 
                " and m.VOUCHER_NO=t.VOUCHER_NO\n" + 
                " and m.ACCOUNTING_UNIT_ID    =? \n" + 
                " AND m.ACCOUNTING_FOR_OFFICE_ID=?\n" + 
                " AND m.DATE_OF_TRANSFER        =?\n" + 
                " AND m.TRANSFER_STATUS!='C'\n" + 
                " and t.AUTO_STATUS is null ";
              
            else if (cmbSubSystemType.equalsIgnoreCase("FTO"))
                QueryType =
                        "select offc.VOUCHER_NO from FAS_FUND_TRF_FROM_OFFICE offc where offc.ACCOUNTING_UNIT_ID=?  and offc.ACCOUNTING_FOR_OFFICE_ID=? and offc.DATE_OF_TRANSFER=? and offc.TRANSFER_STATUS!='C'"+
                         "  AND offc.voucher_no NOT IN" + 
                         "  (SELECT ho.trf_voucher_no    " + 
                         "  FROM fas_fund_receipt_by_ho ho" + 
                         "  WHERE ho.received_from_office_id=offc.accounting_for_office_id" + 
                         "  AND ho.cashbook_month           =offc.cashbook_month" + 
                         "  AND ho.cashbook_year            =offc.cashbook_year" + 
                         "  AND ho.trf_voucher_no           =offc.voucher_no" + 
                         "  AND ho.trf_voucher_date         =offc.date_of_transfer" + 
                         "  and ho.receipt_status!='C' " +
                         "  )";
            else if (cmbSubSystemType.equalsIgnoreCase("FRH"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_STATUS!='C' and REMITTANCE_STATUS!='Y' and  CHALLAN_NO=0 and CHALLAN_DATE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("FRO"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_STATUS!='C' and REMITTANCE_STATUS!='Y' and  CHALLAN_NO=0 and CHALLAN_DATE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("IBT"))
                QueryType =
                        "select VOUCHER_NO from FAS_INTER_BANK_TRF_AT_HO where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("SC"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='SC' and RECEIPT_STATUS!='C' and CHALLAN_NO=0 and CHALLAN_DATE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("CRM"))
                QueryType =
                        "select CHALLAN_NO as VOUCHER_NO from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CHALLAN_DATE=? and REMITTANCE_TYPE	='C' and (STATUS is null OR  STATUS='') ";
            else if (cmbSubSystemType.equalsIgnoreCase("BRM"))
                QueryType =
                        "select CHALLAN_NO as VOUCHER_NO from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CHALLAN_DATE=? and REMITTANCE_TYPE ='B'  and (STATUS is null OR  STATUS='') ";
            else if (cmbSubSystemType.equalsIgnoreCase("FRM"))
                QueryType =
                        "select CHALLAN_NO as VOUCHER_NO from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CHALLAN_DATE=? and REMITTANCE_TYPE ='F' and (STATUS is null OR  STATUS='')";
            else if (cmbSubSystemType.equalsIgnoreCase("YC"))
                QueryType =
                        "SELECT VOUCHER_NO as VOUCHER_NO FROM fas_yourself_cheque_mst WHERE accounting_unit_id = ?  AND accounting_for_office_id = ? AND to_char(voucher_date,'DD-MON-YY') = ?  AND VOUCHER_STATUS ='L' ";
            else if (cmbSubSystemType.equalsIgnoreCase("DCB"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='B'  and SUB_LEDGER_TYPE_CODE =14 and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION  in ('S','M') and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("ODR"))
                QueryType =
                        "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='B'  and SUB_LEDGER_TYPE_CODE =9 and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION  in ('S','M') and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("DCC"))                        
                            QueryType =
                                    "select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C'  and SUB_LEDGER_TYPE_CODE =14 and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='M' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null";
                                    
            /*  Temp/Adv Imprest Authorization added on 04/03/2011 */
            else if(cmbSubSystemType.equalsIgnoreCase("IMP"))
                          {
                              QueryType="select a.VOUCHER_NO from\n" + 
                              "(\n" + 
                              "    select \n" + 
                              "      VOUCHER_NO,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PAYMENT_DATE\n" + 
                              "    from \n" + 
                              "      FAS_PAYMENT_MASTER \n" + 
                              "    where \n" + 
                              "      ACCOUNTING_UNIT_ID=? and\n" + 
                              "      ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                              "      PAYMENT_DATE=? and \n" + 
                              "      PAYMENT_TYPE='B' and \n" +
                              "      PAYMENT_STATUS!='C' and \n" + 
                              "      MODE_OF_CREATION='I'\n" + 
                              ")a  \n" + 
                              "where  VOUCHER_NO not in\n" + 
                              "(\n" + 
                              "    select RECEIVABLE_VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID and RECEIVABLE_VOUCHER_DATE=a.PAYMENT_DATE and RECEIPT_STATUS='L' \n" + 
                              ") \n" + 
                              "and VOUCHER_NO not in\n" + 
                              "(\n" + 
                              "    select CB_REF_NO from FAS_JOURNAL_TRANSACTION trn,FAS_JOURNAL_MASTER mst\n" + 
                              "    where trn.ACCOUNTING_UNIT_ID=mst.ACCOUNTING_UNIT_ID and trn.ACCOUNTING_FOR_OFFICE_ID=mst.ACCOUNTING_FOR_OFFICE_ID and trn.CASHBOOK_YEAR=mst.CASHBOOK_YEAR and trn.CASHBOOK_MONTH=mst.CASHBOOK_MONTH and trn.VOUCHER_NO=mst.VOUCHER_NO and trn.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID and trn.ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID and trn.CB_REF_DATE=a.PAYMENT_DATE and mst.JOURNAL_STATUS='L'\n" + 
                              ")";   
                          }
                          else if(cmbSubSystemType.equalsIgnoreCase("TMP"))
                          {   
                              QueryType="select a.VOUCHER_NO from\n" + 
                              "(\n" + 
                              "    select \n" + 
                              "      VOUCHER_NO,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PAYMENT_DATE\n" + 
                              "    from \n" + 
                              "      FAS_PAYMENT_MASTER \n" + 
                              "    where \n" + 
                              "      ACCOUNTING_UNIT_ID=? and\n" + 
                              "      ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                              "      PAYMENT_DATE=? and \n" + 
                              "      PAYMENT_STATUS!='C' and \n" + 
                              "      MODE_OF_CREATION='T'\n" + 
                              ")a  \n" + 
                              "where  VOUCHER_NO not in\n" + 
                              "(\n" + 
                              "    select RECEIVABLE_VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID and RECEIVABLE_VOUCHER_DATE=a.PAYMENT_DATE and RECEIPT_STATUS='L' \n" + 
                              ") \n" + 
                              "and VOUCHER_NO not in\n" + 
                              "(\n" + 
                              "    select CB_REF_NO from FAS_JOURNAL_TRANSACTION trn,FAS_JOURNAL_MASTER mst\n" + 
                              "    where trn.ACCOUNTING_UNIT_ID=mst.ACCOUNTING_UNIT_ID and trn.ACCOUNTING_FOR_OFFICE_ID=mst.ACCOUNTING_FOR_OFFICE_ID and trn.CASHBOOK_YEAR=mst.CASHBOOK_YEAR and trn.CASHBOOK_MONTH=mst.CASHBOOK_MONTH and trn.VOUCHER_NO=mst.VOUCHER_NO and trn.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID and trn.ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID and trn.CB_REF_DATE=a.PAYMENT_DATE and mst.JOURNAL_STATUS='L'\n" + 
                              ")";   
                          }
            else if(cmbSubSystemType.equalsIgnoreCase("IMPR"))
                          { 
                              QueryType="select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and  RECEIPT_STATUS!='C' and MODE_OF_CREATION='I' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null";
                          }
                          else if(cmbSubSystemType.equalsIgnoreCase("TMPR"))
                          { 
                             QueryType="select RECEIPT_NO as VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and  RECEIPT_STATUS!='C' and MODE_OF_CREATION='T' and REMITTANCE_STATUS!='Y' and CHALLAN_NO=0 and CHALLAN_DATE is null and CB_REF_TYPE is null";
                          }
            else if(cmbSubSystemType.equalsIgnoreCase("TDAO")||cmbSubSystemType.equalsIgnoreCase("TCAO")||cmbSubSystemType.equalsIgnoreCase("TDAOS")||cmbSubSystemType.equalsIgnoreCase("TCAOS"))
            {
            if(cmbSubSystemType.equalsIgnoreCase("TDAO")){
                QueryType="select * from\n" + 
                "(\n" + 
                "    select \n" + 
                "        VOUCHER_NO,\n" + 
                "        VOUCHER_DATE,\n" + 
                "        ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
                "        ACCEPTING_SLNO,\n" + 
                "        ACCEPTING_DATE,\n" + 
                "        orginating_jvr_no, " +
                "       ORGINATING_JVR_DATE " +
                "    from\n" + 
                "        FAS_TDA_TCA_RAISED_MST \n" + 
                "    where\n" + 
                "        ACCOUNTING_UNIT_ID=? and\n" + 
                "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                "        VOUCHER_DATE=? AND\n" + 
                "       ( TDA_OR_TCA=? or TDA_OR_TCA='TDACB') and\n" + 
                "        STATUS='L' and \n" +
               //DHANA ADDED FOR TDAO 4TH_MAR_2011
             //   "        ADVICE_TYPE='J' and \n" +
                "        (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='Y')" + 
                ")mst_originat\n" + 
                "where\n" + 
                "accepting_slno is null or(ACCEPTING_SLNO not in \n" + 
                "(\n" + 
                "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and VOUCHER_DATE=mst_originat.ACCEPTING_DATE and STATUS='L'\n" + 
                ") )" +
                 " and orginating_jvr_no in" + 
                 "  (select m.voucher_no" + 
                 "  from fas_journal_master m" + 
                 "  where m.accounting_unit_id              =mst_originat.acct_unit_id" + 
                 "  and m.voucher_no                        =mst_originat.orginating_jvr_no" + 
                 "  and m.voucher_date=mst_originat.ORGINATING_JVR_DATE " + 
                 "  and m.journal_status ='L'  " + 
                 "  and m.cb_ref_type is null" + 
                 "  )";
            }
          
            else if(cmbSubSystemType.equalsIgnoreCase("TCAO")){
                QueryType="select * from\n" + 
                "(\n" + 
                "    select \n" + 
                "        VOUCHER_NO,\n" + 
                "        VOUCHER_DATE,\n" + 
                "        ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
                "        ACCEPTING_SLNO,\n" + 
                "        ACCEPTING_DATE,\n" + 
                "        orginating_jvr_no," +
                "       ORGINATING_JVR_DATE " +
                "    from\n" + 
                "        FAS_TDA_TCA_RAISED_MST \n" + 
                "    where\n" + 
                "        ACCOUNTING_UNIT_ID=? and\n" + 
                "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                "        VOUCHER_DATE=? AND\n" + 
                "       ( TDA_OR_TCA=? or TDA_OR_TCA='TCACB') and\n" + 
                "        STATUS='L' and \n" +
               //DHANA ADDED FOR TDAO 4TH_MAR_2011
             //   "        ADVICE_TYPE='J' and \n" +
                "        (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='Y')" + 
                ")mst_originat\n" + 
                "where\n" + 
                "(accepting_slno is null) or (accepting_slno =0)or (ACCEPTING_SLNO not in \n" + 
                "(\n" + 
                "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and VOUCHER_DATE=mst_originat.ACCEPTING_DATE and STATUS='L'\n" + 
                ") " +
                "  )and orginating_jvr_no in" + 
                "  (select m.voucher_no" + 
                "  from fas_journal_master m" + 
                "  where m.accounting_unit_id              =mst_originat.acct_unit_id" + 
                "  and m.voucher_no                        =mst_originat.orginating_jvr_no" + 
                "  and m.voucher_date=mst_originat.ORGINATING_JVR_DATE " + 
                "  and m.journal_status ='L'  " + 
                "  and m.cb_ref_type is null" + 
                "  )";
            }
            
            else if(cmbSubSystemType.equalsIgnoreCase("TDAOS")){
                QueryType="select * from\n" + 
                "(\n" + 
                "    select \n" + 
                "        VOUCHER_NO,\n" + 
                "        VOUCHER_DATE,\n" + 
                "        ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
                "        ACCEPTING_SLNO,\n" + 
                "        ACCEPTING_DATE,\n" + 
                "        orginating_jvr_no, " +
                "       ORGINATING_JVR_DATE " +
                "    from\n" + 
                "        FAS_TDA_TCA_RAISED_MST \n" + 
                "    where\n" + 
                "        ACCOUNTING_UNIT_ID=? and\n" + 
                "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                "        VOUCHER_DATE=? AND\n" + 
                "       ( TDA_OR_TCA=? or TDA_OR_TCA='TDACB') and\n" + 
                "        STATUS='L' and \n" +
               //DHANA ADDED FOR TDAO 4TH_MAR_2011
             //   "        ADVICE_TYPE='J' and \n" +
                "        (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='Y') and \n" + 
                " SUPPLEMENT_NO IS NOT NULL  and SUPPLEMENT_NO!=0"+
                ")mst_originat\n" + 
                "where\n" + 
                "ACCEPTING_SLNO not in \n" + 
                "(\n" + 
                "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and VOUCHER_DATE=mst_originat.ACCEPTING_DATE and STATUS='L'\n" + 
                ")" +
                " and orginating_jvr_no in" + 
                "  (select m.voucher_no" + 
                "  from fas_journal_master m" + 
                "  where m.accounting_unit_id              =mst_originat.acct_unit_id" + 
                "  and m.voucher_no                        =mst_originat.orginating_jvr_no" + 
                "  and m.voucher_date=mst_originat.ORGINATING_JVR_DATE " + 
                "  and m.journal_status ='L'  " + 
                "  and m.cb_ref_type is null" + 
                "  )";
                ;
            }
            else if(cmbSubSystemType.equalsIgnoreCase("TCAOS")){
                QueryType="select * from\n" + 
                "(\n" + 
                "    select \n" + 
                "        VOUCHER_NO,\n" + 
                "        VOUCHER_DATE,\n" + 
                "        ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
                "        ACCEPTING_SLNO,\n" + 
                "        ACCEPTING_DATE,\n" + 
                "        orginating_jvr_no ," +
                "       ORGINATING_JVR_DATE " +
                "    from\n" + 
                "        FAS_TDA_TCA_RAISED_MST \n" + 
                "    where\n" + 
                "        ACCOUNTING_UNIT_ID=? and\n" + 
                "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                "        VOUCHER_DATE=? AND\n" + 
                "       ( TDA_OR_TCA=? or TDA_OR_TCA='TCACB') and\n" + 
                "        STATUS='L' and \n" +
               //DHANA ADDED FOR TDAO 4TH_MAR_2011
             //   "        ADVICE_TYPE='J' and \n" +
                "        (ACCEPTANCE_STATUS is null or ACCEPTANCE_STATUS='Y') and\n" + 
                " SUPPLEMENT_NO IS NOT NULL and SUPPLEMENT_NO!=0"+

                ")mst_originat\n" + 
                "where\n" + 
                "ACCEPTING_SLNO not in \n" + 
                "(\n" + 
                "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and VOUCHER_DATE=mst_originat.ACCEPTING_DATE,'dd-mm-yyyy') and STATUS='L'\n" + 
                ")" +
                " and orginating_jvr_no in" + 
                "  (select m.voucher_no" + 
                "  from fas_journal_master m" + 
                "  where m.accounting_unit_id              =mst_originat.acct_unit_id" + 
                "  and m.voucher_no                        =mst_originat.orginating_jvr_no" + 
                "  and m.voucher_date=mst_originat.ORGINATING_JVR_DATE " + 
                "  and m.journal_status ='L'  " + 
                "  and m.cb_ref_type is null" + 
                "  )";
                ;
            }
            }
            if(cmbSubSystemType.equalsIgnoreCase("TDAOB")||cmbSubSystemType.equalsIgnoreCase("TCAOB"))
        	{
            	if(cmbSubSystemType.equalsIgnoreCase("TDAOB")){
                    QueryType="select * from\n" + 
                    "(\n" + 
                    "    select \n" + 
                    "        VOUCHER_NO,\n" + 
                    "        VOUCHER_DATE,\n" + 
                    "        ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
                    "        ACCEPTING_SLNO,\n" + 
                    "        ACCEPTING_DATE,\n" + 
                    "        orginating_jvr_no, " +
                    "       ORGINATING_JVR_DATE " +
                    "    from\n" + 
                    "        FAS_TDA_TCA_RAISED_MST \n" + 
                    "    where\n" + 
                    "        ACCOUNTING_UNIT_ID=? and\n" + 
                    "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                    "        VOUCHER_DATE=? AND\n" + 
                    "       ( TDA_OR_TCA=? or TDA_OR_TCA='TDACB') and\n" + 
                    "        STATUS='L' and \n" +
                   //DHANA ADDED FOR TDAO 4TH_MAR_2011
                 //   "        ADVICE_TYPE='J' and \n" +
                    "        (ACCEPTANCE_STATUS is null)" + 
                    ")mst_originat\n" + 
                    "where\n" + 
                    "ACCEPTING_SLNO not in \n" + 
                    "(\n" + 
                    "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and VOUCHER_DATE=mst_originat.ACCEPTING_DATE and STATUS='L'\n" + 
                    ")"+
                    " and orginating_jvr_no is null " +
                    " and ORGINATING_JVR_DATE is null";
                }
            	else if(cmbSubSystemType.equalsIgnoreCase("TCAOB")){
                    QueryType="select * from\n" + 
                    "(\n" + 
                    "    select \n" + 
                    "        VOUCHER_NO,\n" + 
                    "        VOUCHER_DATE,\n" + 
                    "        ACCOUNTING_UNIT_ID as acct_unit_id,\n" + 
                    "        ACCEPTING_SLNO,\n" + 
                    "        ACCEPTING_DATE,\n" + 
                    "        orginating_jvr_no," +
                    "       ORGINATING_JVR_DATE " +
                    "    from\n" + 
                    "        FAS_TDA_TCA_RAISED_MST \n" + 
                    "    where\n" + 
                    "        ACCOUNTING_UNIT_ID=? and\n" + 
                    "        ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                    "        VOUCHER_DATE=? AND\n" + 
                    "       ( TDA_OR_TCA=? or TDA_OR_TCA='TCACB') and\n" + 
                    "        STATUS='L' and \n" +
                   //DHANA ADDED FOR TDAO 4TH_MAR_2011
                 //   "        ADVICE_TYPE='J' and \n" +
                    "        (ACCEPTANCE_STATUS is null)" + 
                    " )mst_originat\n" + 
                    " where\n" + 
                    " ACCEPTING_SLNO not in \n" + 
                    " (\n" + 
                    "    select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=mst_originat.acct_unit_id and VOUCHER_NO=mst_originat.ACCEPTING_SLNO and VOUCHER_DATE=mst_originat.ACCEPTING_DATE and STATUS='L'\n" + 
                    " )" +
                    " and orginating_jvr_no is null " +
                    " and ORGINATING_JVR_DATE is null";
                }
            	
        	}
            
            else if(cmbSubSystemType.equalsIgnoreCase("TDAR")||cmbSubSystemType.equalsIgnoreCase("TCAR")||cmbSubSystemType.equalsIgnoreCase("TDARS")||cmbSubSystemType.equalsIgnoreCase("TCARS"))
            {
                  if(cmbSubSystemType.equalsIgnoreCase("TDAR")){
              QueryType="SELECT VOUCHER_NO,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID AS acct_unit_id,ACCEPTING_SLNO," +
                            "ACCEPTING_DATE FROM FAS_TDA_TCA_RAISED_MST WHERE ACCOUNTING_UNIT_ID =?  AND " +
                            "ACCOUNTING_FOR_OFFICE_ID=? AND Responding_Jvr_Date=? AND (TDA_OR_TCA ='TDAO' or TDA_OR_TCA='TDACB') AND" +
                            " STATUS ='L' AND RESPONDING_JVR_NO >0";
                  }
                  else 
                  if(cmbSubSystemType.equalsIgnoreCase("TCAR")){
              QueryType="SELECT VOUCHER_NO,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID AS acct_unit_id,ACCEPTING_SLNO," +
                            "ACCEPTING_DATE FROM FAS_TDA_TCA_RAISED_MST WHERE ACCOUNTING_UNIT_ID =?  AND " +
                            "ACCOUNTING_FOR_OFFICE_ID=? AND RESPONDING_JVR_DATE=? AND (TDA_OR_TCA ='TCAO' or TDA_OR_TCA='TCACB') AND" +
                            " STATUS ='L' AND RESPONDING_JVR_NO >0";
                  }
                  else if(cmbSubSystemType.equalsIgnoreCase("TDARS")){
                      QueryType="SELECT VOUCHER_NO,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID AS acct_unit_id,ACCEPTING_SLNO," +
                              "ACCEPTING_DATE FROM FAS_TDA_TCA_RAISED_MST WHERE ACCOUNTING_UNIT_ID =?  AND " +
                              "ACCOUNTING_FOR_OFFICE_ID=? AND Responding_Jvr_Date=? AND (TDA_OR_TCA ='TDAO' or TDA_OR_TCA='TDACB') AND" +
                              " STATUS ='L' AND RESPONDING_JVR_NO >0 AND SUPPLEMENT_NO IS NOT NULL and SUPPLEMENT_NO!=0";
                    }
                  else if(cmbSubSystemType.equalsIgnoreCase("TCARS")){
                  QueryType="SELECT VOUCHER_NO,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID AS acct_unit_id,ACCEPTING_SLNO," +
                                "ACCEPTING_DATE FROM FAS_TDA_TCA_RAISED_MST WHERE ACCOUNTING_UNIT_ID =?  AND " +
                                "ACCOUNTING_FOR_OFFICE_ID=? AND RESPONDING_JVR_DATE=? AND (TDA_OR_TCA ='TCAO' or TDA_OR_TCA='TCACB') AND" +
                                " STATUS ='L' AND RESPONDING_JVR_NO >0 AND SUPPLEMENT_NO IS NOT NULL and SUPPLEMENT_NO!=0";
                      }
                	  
                  
            }
            else if(cmbSubSystemType.equalsIgnoreCase("TDAA")||cmbSubSystemType.equalsIgnoreCase("TCAA")||cmbSubSystemType.equalsIgnoreCase("TDAAS")||cmbSubSystemType.equalsIgnoreCase("TCAAS"))
            {
                
            	if(cmbSubSystemType.equalsIgnoreCase("TDAA")||cmbSubSystemType.equalsIgnoreCase("TCAA"))
            	{
            	
            	QueryType="select * from\n" + 
                "(\n" + 
                "    select   \n" +
                "      a.VOUCHER_NO," + 
                "      b.ACCOUNTING_UNIT_ID,\n" + 
                "      b.ACCOUNTING_FOR_OFFICE_ID,      \n" + 
                "      a.RESPONDING_JVR_NO, \n" + 
                "      a.RESPONDING_JVR_DATE \n" + 
                "    from   \n" + 
                "      FAS_TDA_TCA_RAISED_MST a, FAS_TDA_TCA_RAISED_MST b   \n" + 
                "    where  \n" + 
                "      a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and\n" + 
                "      a.VOUCHER_NO=b.ACCEPTING_SLNO and\n" + 
                "      a.VOUCHER_DATE=b.ACCEPTING_DATE and\n" + 
                "      a.ACCOUNTING_UNIT_ID=? and   \n" + 
                "      a.ACCOUNTING_FOR_OFFICE_ID=? and    \n" + 
                "      a.VOUCHER_DATE=? AND   \n" + 
                "      a.TDA_OR_TCA=? and   \n" + 
                "      a.STATUS='L' and a.accepting_jvr_no is not null \n" + 
                ")aa \n" + 
                "where aa.RESPONDING_JVR_NO not in\n" + 
                "(\n" + 
                "    select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID " +
                "and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and VOUCHER_NO=aa.RESPONDING_JVR_NO and VOUCHER_DATE=aa.RESPONDING_JVR_DATE and JOURNAL_STATUS='L'\n" + 
                ")";
            	}
            	
            	else if(cmbSubSystemType.equalsIgnoreCase("TDAAS")||cmbSubSystemType.equalsIgnoreCase("TCAAS"))
            	{
            	
            	QueryType="select * from\n" + 
                "(\n" + 
                "    select   \n" +
                "      a.VOUCHER_NO," + 
                "      b.ACCOUNTING_UNIT_ID,\n" + 
                "      b.ACCOUNTING_FOR_OFFICE_ID,      \n" + 
                "      a.RESPONDING_JVR_NO, \n" + 
                "      a.RESPONDING_JVR_DATE \n" + 
                "    from   \n" + 
                "      FAS_TDA_TCA_RAISED_MST a, FAS_TDA_TCA_RAISED_MST b   \n" + 
                "    where  \n" + 
                "      a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and\n" + 
                "      a.VOUCHER_NO=b.ACCEPTING_SLNO and\n" + 
                "      a.VOUCHER_DATE=b.ACCEPTING_DATE and\n" + 
                "      a.ACCOUNTING_UNIT_ID=? and   \n" + 
                "      a.ACCOUNTING_FOR_OFFICE_ID=? and    \n" + 
                "      a.VOUCHER_DATE=? AND   \n" + 
                "      a.TDA_OR_TCA=? and   \n" + 
                "      a.STATUS='L' and a.accepting_jvr_no is not null  and \n" + 
                "      a.SUPPLEMENT_NO IS NOT NULL  and a.SUPPLEMENT_NO!=0"+
                ")aa \n" + 
                "where aa.RESPONDING_JVR_NO not in\n" + 
                "(\n" + 
                "    select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID " +
                "and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and VOUCHER_NO=aa.RESPONDING_JVR_NO and VOUCHER_DATE=aa.RESPONDING_JVR_DATE and JOURNAL_STATUS='L'\n" + 
                ")";
            	}
            	
            }
            else if(cmbSubSystemType.equalsIgnoreCase("TDAAB")||cmbSubSystemType.equalsIgnoreCase("TCAAB")||cmbSubSystemType.equalsIgnoreCase("TDAABS")||cmbSubSystemType.equalsIgnoreCase("TCAABS"))
            {
                
            	if(cmbSubSystemType.equalsIgnoreCase("TDAAB")||cmbSubSystemType.equalsIgnoreCase("TCAAB"))
            	{
            	QueryType="select * from\n" + 
                "(\n" + 
                "    select   \n" +
                "      a.VOUCHER_NO," + 
                "      b.ACCOUNTING_UNIT_ID,\n" + 
                "      b.ACCOUNTING_FOR_OFFICE_ID,      \n" + 
                "      a.ACCEPTING_JVR_NO, \n" + 
                "      a.ACCEPTING_JVR_DATE \n" + 
                "    from   \n" + 
                "      FAS_TDA_TCA_RAISED_MST a, FAS_TDA_TCA_RAISED_MST b   \n" + 
                "    where  \n" + 
                "      a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and\n" + 
                "      a.VOUCHER_NO=b.ACCEPTING_SLNO and\n" + 
                "      a.VOUCHER_DATE=b.ACCEPTING_DATE and\n" + 
                "      a.ACCOUNTING_UNIT_ID=? and   \n" + 
                "      a.ACCOUNTING_FOR_OFFICE_ID=? and    \n" + 
                "      a.VOUCHER_DATE=? AND   \n" + 
                "      a.TDA_OR_TCA=? and   \n" + 
                "      a.STATUS='L' \n" + 
                ")aa \n" + 
                "where aa.ACCEPTING_JVR_NO IS NULL";
            	}
            	
            	else if(cmbSubSystemType.equalsIgnoreCase("TDAABS")||cmbSubSystemType.equalsIgnoreCase("TCAABS"))
            	{
            	QueryType="select * from\n" + 
                "(\n" + 
                "    select   \n" +
                "      a.VOUCHER_NO," + 
                "      b.ACCOUNTING_UNIT_ID,\n" + 
                "      b.ACCOUNTING_FOR_OFFICE_ID,      \n" + 
                "      a.ACCEPTING_JVR_NO, \n" + 
                "      a.ACCEPTING_JVR_DATE \n" + 
                "    from   \n" + 
                "      FAS_TDA_TCA_RAISED_MST a, FAS_TDA_TCA_RAISED_MST b   \n" + 
                "    where  \n" + 
                "      a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and\n" + 
                "      a.VOUCHER_NO=b.ACCEPTING_SLNO and\n" + 
                "      a.VOUCHER_DATE=b.ACCEPTING_DATE and\n" + 
                "      a.ACCOUNTING_UNIT_ID=? and   \n" + 
                "      a.ACCOUNTING_FOR_OFFICE_ID=? and    \n" + 
                "      a.VOUCHER_DATE=? AND   \n" + 
                "      a.TDA_OR_TCA=? and   \n" + 
                "      a.STATUS='L'  and \n" + 
                "      a.SUPPLEMENT_NO IS NOT NULL  and a.SUPPLEMENT_NO!=0"+
                ")aa \n" + 
                "where aa.ACCEPTING_JVR_NO IS NULL";
            	}
                
            }
            //tpa added on 30march2012
            else if(cmbSubSystemType.equalsIgnoreCase("TPAOD")||cmbSubSystemType.equalsIgnoreCase("TPAOC"))
            {
          	  QueryType="select  VOUCHER_NO from FAS_TPA_MASTER  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and  VOUCHER_DATE=? AND TPA_TYPE=? and STATUS='L' and ACCEPTANCE_STATUS is null and (VERIFY is null or VERIFY='N')";
            }   
            
            else if(cmbSubSystemType.equalsIgnoreCase("TPAODV")||cmbSubSystemType.equalsIgnoreCase("TPAOCV"))
            {
          	  QueryType="select  VOUCHER_NO from FAS_TPA_MASTER  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and  VOUCHER_DATE=? AND TPA_TYPE=? and STATUS='L' and ACCEPTANCE_STATUS is null and VERIFY='Y' and (AUDIT_VERIFY is null or AUDIT_VERIFY='N')";
            }   
            else if(cmbSubSystemType.equalsIgnoreCase("TPAODA")||cmbSubSystemType.equalsIgnoreCase("TPAOCA"))
            {
          	  QueryType="select  VOUCHER_NO from FAS_TPA_MASTER  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and  VOUCHER_DATE=? AND TPA_TYPE=? and STATUS='L' and ACCEPTANCE_STATUS is null and VERIFY='Y' and (AUDIT_VERIFY is not null or AUDIT_VERIFY='Y')";
            }
            else if(cmbSubSystemType.equalsIgnoreCase("TPAAD")||cmbSubSystemType.equalsIgnoreCase("TPAAC"))
            {
          	  QueryType="SELECT tpa.VOUCHER_NO \n"+
							"	FROM FAS_TPA_MASTER tpa,fas_journal_transaction jtr \n"+
							"	WHERE tpa.ACCOUNTING_UNIT_ID    =? \n"+
							"	AND tpa.ACCOUNTING_FOR_OFFICE_ID=? \n"+
							"	AND tpa.VOUCHER_DATE            =? \n"+
							"	AND tpa.TPA_TYPE                =?  \n"+
							"	AND tpa.STATUS                  ='L'   \n"+
							//commanded on 13-08-2018 vasanthi mam said to remove this condition for unit id - 182 yr 2018,apr vr-42
//	                    	"	and jtr.cashbook_month="+cashbookMonth+" and jtr.cashbook_year="+cashbookYear+" and jtr.verified is null and jtr.sl_no=1 \n"+
                            " and jtr.cashbook_month="+cashbookMonth+" and jtr.cashbook_year="+cashbookYear+" and jtr.sl_no=1 " +
							"	and tpa.orgin_voucher_no=jtr.voucher_no and tpa.ORG_ACCOUNTING_UNIT_ID=jtr.ACCOUNTING_UNIT_ID ";
            }
            QueryType =
                    QueryType + " order by 1"; // here order by specified with column number

            System.out.println("QueryType...." + QueryType);

            try {
                //ps=con.prepareStatement("select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPF' and PAYMENT_STATUS!='C'");
                /*
                              ps=con.prepareStatement("select i.VOUCHER_NO from FAS_PAYMENT_MASTER i,FAS_CROSS_REFERENCE c where " +
                             " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.PAYMENT_DATE=? and PAYMENT_TYPE='B' and i.PAYMENT_STATUS!='C'  and CREATED_BY_MODULE='BPF' " +
                             " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                             " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.VOUCHER_NO=c.VOUCHER_NO " +
                             " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='C' and DOC_TYPE='BPF'");
                             */
            	
                ps = con.prepareStatement(QueryType);
                if(!cmbSubSystemType.equalsIgnoreCase("ADM_A")){
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                //tda_tca
                if(cmbSubSystemType.equalsIgnoreCase("TDAO")||cmbSubSystemType.equalsIgnoreCase("TDAOB"))
                    ps.setString(4,"TDAO");
                else if(cmbSubSystemType.equalsIgnoreCase("TDAOS"))
                    ps.setString(4,"TDAO");
                else if(cmbSubSystemType.equalsIgnoreCase("TCAO")||cmbSubSystemType.equalsIgnoreCase("TCAOB"))
                    ps.setString(4,"TCAO");
                
                else if(cmbSubSystemType.equalsIgnoreCase("TCAOS"))
                    ps.setString(4,"TCAO");
                else if(cmbSubSystemType.equalsIgnoreCase("TDAA"))
                {
                    System.out.println("inside TDAA");
                    ps.setString(4,"TDAA");
                }
                else if(cmbSubSystemType.equalsIgnoreCase("TDAAS"))
                {
                    System.out.println("inside TDAA");
                    ps.setString(4,"TDAA");
                }
                else if(cmbSubSystemType.equalsIgnoreCase("TCAA"))
                    ps.setString(4,"TCAA");
                else if(cmbSubSystemType.equalsIgnoreCase("TCAAS"))
                    ps.setString(4,"TCAA");
                else if(cmbSubSystemType.equalsIgnoreCase("TCAAB"))
                    ps.setString(4,"TCAA");
                else if(cmbSubSystemType.equalsIgnoreCase("TCAABS"))
                    ps.setString(4,"TCAA");
                else if(cmbSubSystemType.equalsIgnoreCase("TDAAB"))
                    ps.setString(4,"TDAA");
                else if(cmbSubSystemType.equalsIgnoreCase("TDAABS"))
                    ps.setString(4,"TDAA");
                else if(cmbSubSystemType.equalsIgnoreCase("TPAOC")||cmbSubSystemType.equalsIgnoreCase("TPAOD")||cmbSubSystemType.equalsIgnoreCase("TPAAC")||cmbSubSystemType.equalsIgnoreCase("TPAAD"))
                {
                    ps.setString(4,cmbSubSystemType);
                    System.out.println("cmbSubSystemType ::: "+cmbSubSystemType);
                }
                else if(cmbSubSystemType.equalsIgnoreCase("TPAOCV")||cmbSubSystemType.equalsIgnoreCase("TPAOCA"))
                {
                    ps.setString(4,"TPAOC");
                    System.out.println("cmbSubSystemType ::: "+cmbSubSystemType);
                }
                else if(cmbSubSystemType.equalsIgnoreCase("TPAODV")||cmbSubSystemType.equalsIgnoreCase("TPAODA"))
                {
                    ps.setString(4,"TPAOD");
                    System.out.println("cmbSubSystemType ::: "+cmbSubSystemType);
                }
                
                }
                else
                {
                	  ps.setInt(1, cmbAcc_UnitCode);
                      ps.setDate(2, txtCrea_date);
                }
                    System.out.println("SELECT t.accept_voucher_no"+
    						"	FROM FAS_ADJUST_MEMO_MST m, "+
							  " FAS_ADJUST_MEMO_TRN t "+
             " WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID "+
             " 			AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
             " 			AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR "+
             " 			AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
             " 			AND m.voucher_no              =t.voucher_no "+
             " 			AND t.FOR_ACCOUNTING_UNIT_ID  = "+cmbAcc_UnitCode+
             " 			AND t.ACCEPT_VOUCHER_DATE     = "+txtCrea_date+
             " 			AND m.MEMO_STATUS<>'C' "+
             " 			and t.ACCEPTANCE_STATUS='Y' "+
             "			AND (T.ADVICE_TRANSFER_FREEZE  ='N'\r\n" + 
             "			or T.ADVICE_TRANSFER_FREEZE is null)");
                rs = ps.executeQuery();

                int count = 0,accept_count=0;
                while (rs.next()) {
                	if(cmbSubSystemType.equalsIgnoreCase("ADM"))
                	{
                		System.out.println("gggg"+rs.getString("ACCEPTANCE_STATUS"));
                		
                		if(rs.getString("ACCEPTANCE_STATUS")!=null)
                		{
//                			count=0;
                			accept_count++;
                		}
                		else if(rs.getString("ACCEPTANCE_STATUS")==null)
                		{
                			xml = xml + "<Rec_No>" + rs.getInt("VOUCHER_NO") + "</Rec_No>";
                			  count++;
                		}
                		System.out.println("Count value****"+count);
                		System.out.println("accept_count value****"+accept_count);
                	}
                	else{
                    xml = xml + "<Rec_No>" + rs.getInt("VOUCHER_NO") + "</Rec_No>";
                    count++;
                    accept_count=0;
                	}
                	
                }
                System.out.println("after Count value****"+count);
                System.out.println("after accept_count value****"+accept_count);
                
                if (count == 0)
                {
                    xml = xml + "<flag>failure</flag>";
                }
                else
                {
                	if(accept_count>0)
                	{
                		xml = xml + "<flag>failure</flag>";
                	}
                	else
                	{      
                     xml = xml + "<flag>success</flag>";
                	}
                 	
                }
               
                
                System.out.println("count" + count);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (strCommand.equalsIgnoreCase("load_Voucher_details")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int txtVoucher_No = 0;

            /** Get Voucher Number  */
            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }

            System.out.println("txtVoucher_No " + txtVoucher_No);

            xml = "<response><command>load_Voucher_details</command>";

            String QueryType = "";
            if (cmbSubSystemType.equalsIgnoreCase("CR"))
                QueryType ="select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and CHALLAN_NO=0 and CHALLAN_DATE is null AND ( REMARKS NOT LIKE 'GPF RJV Created for the req no%' or REMARKS is null )";
            else if (cmbSubSystemType.equalsIgnoreCase("BR"))
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and sub_ledger_type_code <> 14 and CHALLAN_NO=0 and CHALLAN_DATE is null AND ( REMARKS NOT LIKE 'GPF RJV Created for the req no%' or REMARKS is null )";
            else if (cmbSubSystemType.equalsIgnoreCase("CR_S")) // CASH Reclassification
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='S'";
            else if (cmbSubSystemType.equalsIgnoreCase("BR_S")) // BANK Reclassification
                QueryType =
                        "select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_TYPE='B' and CREATED_BY_MODULE='BR' and RECEIPT_STATUS!='C' and MODE_OF_CREATION='S'";
            else if (cmbSubSystemType.equalsIgnoreCase("GJV"))
                QueryType =
                        "select e.JOURNAL_TYPE_DESC as com_value,(select  nvl(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                        " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                        " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                        " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='GJV' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";
            else if (cmbSubSystemType.equalsIgnoreCase("LJV"))
                QueryType =
                        "select m.VOUCHER_NO,e.JOURNAL_TYPE_DESC as com_value,(select nvl(to_char(sum(AMOUNT),'99999999999999.99'),'0') from FAS_JOURNAL_TRANSACTION where " +
                        " ACCOUNTING_UNIT_ID=m.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and CASHBOOK_YEAR=m.CASHBOOK_YEAR " +
                        " and CASHBOOK_MONTH=m.CASHBOOK_MONTH and VOUCHER_NO=m.VOUCHER_NO and CR_DR_INDICATOR='DR') as amt from  FAS_JOURNAL_MASTER m,FAS_MST_JOURNAL_TYPE e  " +
                        " where m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.VOUCHER_DATE=? and  m.VOUCHER_NO=? and m.CREATED_BY_MODULE='LJV' and m.JOURNAL_STATUS!='C' and  m.JOURNAL_TYPE_CODE=e.JOURNAL_TYPE_CODE  ";
            else if (cmbSubSystemType.equalsIgnoreCase("BPP"))
                QueryType =
                        "select PAID_TO as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=?  and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPP' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("BPF"))
                QueryType =
                        "select PAID_TO as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='BPF' and PAYMENT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("NP"))
                QueryType =
                        "select PAID_TO as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=? and PAYMENT_TYPE='B' and CREATED_BY_MODULE='NP' and PAYMENT_STATUS!='C'";
            else if(cmbSubSystemType.equalsIgnoreCase("CHQD"))
                QueryType="SELECT ACCOUNTING_UNIT_NAME as com_value,AMOUNT as amt FROM FAS_CHEQUE_DISHONOUR cqd, FAS_MST_ACCT_UNITS acc_mst WHERE cqd.accounting_unit_id=acc_mst.accounting_unit_id and cqd.ACCOUNTING_UNIT_ID  =? AND ACCOUNTING_FOR_OFFICE_ID=? AND Cheq_Dishonour_Date  =? and CHEQUE_DD_NO::numeric=?  ";                
            else if(cmbSubSystemType.equalsIgnoreCase("CHQC"))
            {
            
            	QueryType="SELECT ACCOUNTING_UNIT_NAME AS com_value,AMOUNT AS amt FROM FAS_CHEQUE_CANCEL cqd,FAS_MST_ACCT_UNITS acc_mst "+
						" Where Cqd.Accounting_Unit_Id=Acc_Mst.Accounting_Unit_Id And Cqd.Accounting_Unit_Id  =? And Accounting_For_Office_Id=? "+
            		" AND CHEQ_CANCEL_DATE     =? AND CHEQUE_DD_NO            =?";
            }
            else if (cmbSubSystemType.equalsIgnoreCase("FTH"))
                //QueryType="select VOUCHER_NO,,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_FUND_TRF_FROM_HO_MASTER where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and TRANSFER_STATUS!='C'";
                QueryType =
                        "select (select o.OFFICE_NAME from FAS_FUND_TRF_FROM_HO_TRN t,COM_MST_OFFICES o  " +
                        " where t.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID and t.ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID " +
                        " and t.CASHBOOK_YEAR=a.CASHBOOK_YEAR and t.CASHBOOK_MONTH=a.CASHBOOK_MONTH and t.VOUCHER_NO=a.VOUCHER_NO " +
                        " and t.TRANSFER_TO_OFFICE_ID=o.OFFICE_ID and t.SL_NO=1 and t.auto_status is null) || ' and ..'  as com_value,trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as amt " +
                        " from FAS_FUND_TRF_FROM_HO_MASTER a where a.ACCOUNTING_UNIT_ID=? and a.ACCOUNTING_FOR_OFFICE_ID=? and " +
                        " a.DATE_OF_TRANSFER=? and a.VOUCHER_NO=? and a.TRANSFER_STATUS!='C'  ";
            // In the above query i used SL_NO=1 to get only one office from transaction , else it retrieve more than one row,hence it say error
            else if (cmbSubSystemType.equalsIgnoreCase("FTO"))
                QueryType =
                        "select 'HEAD OFFICE' as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_FUND_TRF_FROM_OFFICE where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and VOUCHER_NO=? and TRANSFER_STATUS!='C' and auto_status is null";
            else if (cmbSubSystemType.equalsIgnoreCase("FRH"))
                //QueryType="select receipt_no as VOUCHER_NO,,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_FUND_RECEIPT_BY_HO where ACCOUNTING_UNIT_ID=?  and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_STATUS!='C'";
                QueryType =
                        "select b.OFFICE_NAME as com_value,trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as amt from FAS_FUND_RECEIPT_BY_HO a,COM_MST_OFFICES b where a.ACCOUNTING_UNIT_ID=?  and a.ACCOUNTING_FOR_OFFICE_ID=? and a.RECEIPT_DATE=? and RECEIPT_NO=? and a.RECEIPT_STATUS!='C' " +
                        " and a.RECEIVED_FROM_OFFICE_ID=b.OFFICE_ID ";
            else if (cmbSubSystemType.equalsIgnoreCase("FRO"))
                QueryType =
                        "select 'HEAD OFFICE' as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_FUND_RECEIPT_BY_OFFICE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("IBT"))
                QueryType =
                        "select 'HEAD OFFICE' as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_INTER_BANK_TRF_AT_HO where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and VOUCHER_NO=? and TRANSFER_STATUS!='C'";
            else if (cmbSubSystemType.equalsIgnoreCase("SC"))
                QueryType =
                        "select  'cheque No: ' || t.CHEQUE_DD_NO || '  --  Date : ' || to_char(t.CHEQUE_DD_DATE,'DD/MM/YYYY') as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_RECEIPT_MASTER m,FAS_RECEIPT_TRANSACTION t" +
                        " where m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH and m.RECEIPT_NO=t.RECEIPT_NO" +
                        " and m.ACCOUNTING_UNIT_ID=? and m.ACCOUNTING_FOR_OFFICE_ID=? and m.RECEIPT_DATE=? and m.RECEIPT_NO=? and m.RECEIPT_TYPE='C' " +
                        " and m.CREATED_BY_MODULE='SC' and m.RECEIPT_STATUS!='C' and m.CHALLAN_NO=0 and m.CHALLAN_DATE is null";
            else if (cmbSubSystemType.equalsIgnoreCase("CRM"))
                QueryType =
                        "   select '-NIL-' as com_value , trim(to_char(AMOUNT_REMITTED,'99999999999999.99')) as amt    \n" +
                        "   from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?              \n" +
                        "   and CHALLAN_DATE=? and CHALLAN_NO=?                                                          ";
            else if (cmbSubSystemType.equalsIgnoreCase("BRM"))
                QueryType =
                        "   select '-NIL-' as com_value , trim(to_char(AMOUNT_REMITTED,'99999999999999.99')) as amt    \n" +
                        "   from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?              \n" +
                        "   and CHALLAN_DATE=? and CHALLAN_NO=?                                                          ";

            else if (cmbSubSystemType.equalsIgnoreCase("FRM"))
                QueryType =
                        "   select '-NIL-' as com_value , trim(AMOUNT_REMITTED::varchar,'99999999999999.99') as amt    \n" +
                        "   from FAS_REMITTANCE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?              \n" +
                        "   and (CHALLAN_DATE)=? and CHALLAN_NO=?                                                          ";
           //Adjustment memo
            else if(cmbSubSystemType.equalsIgnoreCase("ADM"))
                QueryType="SELECT SUB_LEDGER_CODE, "+
						"  offi, "+
                	" (SELECT office_name FROM com_mst_offices WHERE office_id=offi "+
                	"   ) AS com_value, "+
                	"   amt "+
                	" FROM "+
                	"   (SELECT t.SUB_LEDGER_CODE, "+
                	"     (SELECT ACCOUNTING_UNIT_OFFICE_ID "+
                	"     From Fas_Mst_Acct_Units "+
                	"     WHERE accounting_unit_id=t.SUB_LEDGER_CODE "+
                	"     )Offi, "+
                	"     Trim(To_Char(m.Total_Amount,'99999999999999.99')) As Amt "+
                	"   From Fas_Adjust_Memo_Mst M,Fas_Adjust_Memo_Trn T "+
                	"   Where M.Accounting_Unit_Id=T.Accounting_Unit_Id "+
                	"   And M.Accounting_For_Office_Id=T.Accounting_For_Office_Id "+
                	"   And M.CASHBOOK_YEAR=T.CASHBOOK_YEAR "+
                	"   And M.Cashbook_Month=T.Cashbook_Month "+
                	"   And M.Voucher_No=T.Voucher_No "+
                	"   And M.Accounting_Unit_Id    =?"+
                	"   And M.Accounting_For_Office_Id=? "+
                	"   And M.Voucher_Date            =? "+
                	"   And M.Voucher_No              =? "+
                	"   AND (m.ACCEPTANCE_STATUS       ='N' or M.Acceptance_Status is null) "+
                	"   )as ps";
            
            else if(cmbSubSystemType.equalsIgnoreCase("ADM_A"))
                QueryType="SELECT SUB_LEDGER_CODE, "+
						"  offi, "+
                	" (SELECT office_name FROM com_mst_offices WHERE office_id=offi "+
                	"   ) AS com_value, "+
                	"   amt "+
                	" FROM "+
                	"   (SELECT t.SUB_LEDGER_CODE, "+
                	"     (SELECT ACCOUNTING_UNIT_OFFICE_ID "+
                	"     From Fas_Mst_Acct_Units "+
                	"     WHERE accounting_unit_id=t.SUB_LEDGER_CODE "+
                	"     )Offi, "+
                	"     Trim(To_Char(m.Total_Amount,'99999999999999.99')) As Amt "+
                	"   From Fas_Adjust_Memo_Mst M,Fas_Adjust_Memo_Trn T "+
                	"   Where M.Accounting_Unit_Id=T.Accounting_Unit_Id "+
                	"   And M.Accounting_For_Office_Id=T.Accounting_For_Office_Id "+
                	"   And M.CASHBOOK_YEAR=T.CASHBOOK_YEAR "+
                	"   And M.Cashbook_Month=T.Cashbook_Month "+
                	"   And M.Voucher_No=T.Voucher_No "+
                	"   And T.FOR_ACCOUNTING_UNIT_ID   =?"+
                	"   And t.ACCEPT_VOUCHER_DATE     =?"+
                	"   And t.ACCEPT_Voucher_No              =?"+
                	"   AND t.ACCEPTANCE_STATUS       ='Y'"+
                	"   ) as opt1";
                 
            else if (cmbSubSystemType.equalsIgnoreCase("YC"))
                QueryType =
                        "    select                                                           " +
                        "         '-NIL-' as com_value ,                                       " +
                        "            trim(to_char(CHEQUE_AMOUNT,'99999999999999.99')) as amt     " +
                        "    from                                                                  " +
                        "            fas_yourself_cheque_mst                                        " +
                        "        where                                                             " +
                        "            ACCOUNTING_UNIT_ID=?                                         " +
                        "            and ACCOUNTING_FOR_OFFICE_ID=?                              " +
                        "            and to_char(VOUCHER_DATE,'DD-MON-YY')=?                     " +
                        "            and VOUCHER_NO=?                                             " +
                        "            and voucher_status='L'                                           ";
           // Imprest Temp/Adv Load Voucher Details 
            else if(cmbSubSystemType.equalsIgnoreCase("IMP"))
                           QueryType="select PAID_TO as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=?  and PAYMENT_TYPE='B' and MODE_OF_CREATION='I' and PAYMENT_STATUS!='C'";
                       else if(cmbSubSystemType.equalsIgnoreCase("TMP"))
                           QueryType="select PAID_TO as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt  from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=?  and PAYMENT_TYPE='B' and MODE_OF_CREATION='T' and PAYMENT_STATUS!='C'";
                          
                       else if(cmbSubSystemType.equalsIgnoreCase("IMPR"))    
                           QueryType="select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_STATUS!='C' and CHALLAN_NO=0 and CHALLAN_DATE is null"; 
                       else if(cmbSubSystemType.equalsIgnoreCase("TMPR"))    
                           QueryType="select RECEIVED_FROM as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_STATUS!='C' and CHALLAN_NO=0 and CHALLAN_DATE is null"; 
                           
                      //tda_tca
                       else if(cmbSubSystemType.equalsIgnoreCase("TDAO")||cmbSubSystemType.equalsIgnoreCase("TCAO")||cmbSubSystemType.equalsIgnoreCase("TDAA")||cmbSubSystemType.equalsIgnoreCase("TCAA")||
                    		   cmbSubSystemType.equalsIgnoreCase("TCAAB")||cmbSubSystemType.equalsIgnoreCase("TDAAB")||cmbSubSystemType.equalsIgnoreCase("TDAOB")||cmbSubSystemType.equalsIgnoreCase("TCAOB"))    
                           QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_TDA_TCA_RAISED_MST mst,FAS_MST_ACCT_UNITS acc_mst where mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=? and mst.STATUS!='C' ";
                         
                       else if(cmbSubSystemType.equalsIgnoreCase("TDAOS")||cmbSubSystemType.equalsIgnoreCase("TCAOS")||cmbSubSystemType.equalsIgnoreCase("TDAAS")||cmbSubSystemType.equalsIgnoreCase("TCAAS")||cmbSubSystemType.equalsIgnoreCase("TCAABS")||cmbSubSystemType.equalsIgnoreCase("TDAABS"))    
                            QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_TDA_TCA_RAISED_MST mst,FAS_MST_ACCT_UNITS acc_mst where mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_DATE=? and VOUCHER_NO=? and mst.STATUS!='C'and SUPPLEMENT_NO IS NOT NULL  and SUPPLEMENT_NO!=0";
            
            
                       else if(cmbSubSystemType.equalsIgnoreCase("TDAR")||cmbSubSystemType.equalsIgnoreCase("TCAR"))
                           QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_TDA_TCA_RAISED_MST mst,FAS_MST_ACCT_UNITS acc_mst where mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RESPONDING_JVR_DATE=? and VOUCHER_NO=? and mst.STATUS!='C' ";
            
                       else if(cmbSubSystemType.equalsIgnoreCase("TDARS")||cmbSubSystemType.equalsIgnoreCase("TCARS"))
                           QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(TOTAL_AMOUNT,'99999999999999.99'))  as amt from FAS_TDA_TCA_RAISED_MST mst,FAS_MST_ACCT_UNITS acc_mst where mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RESPONDING_JVR_DATE=? and VOUCHER_NO=? and mst.STATUS!='C' and SUPPLEMENT_NO IS NOT NULL  and SUPPLEMENT_NO!=0";
                           
            //DCB receipt
            else if (cmbSubSystemType.equalsIgnoreCase("DCB"))
                QueryType =
                        "SELECT RECEIVED_FROM AS com_value,trim(TO_CHAR(TOTAL_AMOUNT,'99999999999999.99')) AS amt FROM FAS_RECEIPT_MASTER WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND RECEIPT_DATE=? AND RECEIPT_NO=? AND RECEIPT_TYPE='B' AND CREATED_BY_MODULE='BR' AND RECEIPT_STATUS!='C' AND CHALLAN_NO=0 AND SUB_LEDGER_TYPE_CODE=14 AND CHALLAN_DATE IS NULL";
            else if (cmbSubSystemType.equalsIgnoreCase("ODR"))
                QueryType =
                        "SELECT RECEIVED_FROM AS com_value,trim(TO_CHAR(TOTAL_AMOUNT,'99999999999999.99')) AS amt FROM FAS_RECEIPT_MASTER WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND RECEIPT_DATE=? AND RECEIPT_NO=? AND RECEIPT_TYPE='B' AND CREATED_BY_MODULE='BR' AND RECEIPT_STATUS!='C' AND CHALLAN_NO=0 AND SUB_LEDGER_TYPE_CODE=9 AND CHALLAN_DATE IS NULL";
             else if (cmbSubSystemType.equalsIgnoreCase("DCC"))
                            QueryType =
                                    "SELECT RECEIVED_FROM AS com_value,trim(TO_CHAR(TOTAL_AMOUNT,'99999999999999.99')) AS amt FROM FAS_RECEIPT_MASTER WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND RECEIPT_DATE=? AND RECEIPT_NO=? AND RECEIPT_TYPE='C' AND CREATED_BY_MODULE='CR' AND RECEIPT_STATUS!='C' AND CHALLAN_NO=0 AND SUB_LEDGER_TYPE_CODE=14 AND CHALLAN_DATE IS NULL";
               //added on 30mar2012      
            else if(cmbSubSystemType.equalsIgnoreCase("TPAOC"))    
                QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(AMOUNT,'99999999999999.99'))  as amt from FAS_TPA_MASTER mst,FAS_TPA_TRANSACTION trn,FAS_MST_ACCT_UNITS acc_mst where mst.accounting_unit_id= trn.accounting_unit_id and mst.accounting_for_office_id= trn.accounting_for_office_id and mst.cashbook_month= trn.cashbook_month and mst.cashbook_year= trn.cashbook_year and mst.voucher_no= trn.voucher_no and mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and mst.ACCOUNTING_FOR_OFFICE_ID=? and mst.VOUCHER_DATE=? and mst.VOUCHER_NO=? and mst.STATUS!='C' and trn.SL_NO=1 ";
            
            else if(cmbSubSystemType.equalsIgnoreCase("TPAOD")) 
            	QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(AMOUNT,'99999999999999.99'))  as amt from FAS_TPA_MASTER mst,FAS_TPA_TRANSACTION trn,FAS_MST_ACCT_UNITS acc_mst where mst.accounting_unit_id= trn.accounting_unit_id and mst.accounting_for_office_id= trn.accounting_for_office_id and mst.cashbook_month= trn.cashbook_month and mst.cashbook_year= trn.cashbook_year and mst.voucher_no= trn.voucher_no and mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and mst.ACCOUNTING_FOR_OFFICE_ID=? and mst.VOUCHER_DATE=? and mst.VOUCHER_NO=? and mst.STATUS!='C' and trn.SL_NO=1 ";
            
            
            else if(cmbSubSystemType.equalsIgnoreCase("TPAOCV"))    
                QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(AMOUNT,'99999999999999.99'))  as amt from FAS_TPA_MASTER mst,FAS_TPA_TRANSACTION trn,FAS_MST_ACCT_UNITS acc_mst where mst.accounting_unit_id= trn.accounting_unit_id and mst.accounting_for_office_id= trn.accounting_for_office_id and mst.cashbook_month= trn.cashbook_month and mst.cashbook_year= trn.cashbook_year and mst.voucher_no= trn.voucher_no and mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and mst.ACCOUNTING_FOR_OFFICE_ID=? and mst.VOUCHER_DATE=? and mst.VOUCHER_NO=? and mst.STATUS!='C' and trn.SL_NO=1 ";
            
            else if(cmbSubSystemType.equalsIgnoreCase("TPAODV")) 
            	QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(AMOUNT,'99999999999999.99'))  as amt from FAS_TPA_MASTER mst,FAS_TPA_TRANSACTION trn,FAS_MST_ACCT_UNITS acc_mst where mst.accounting_unit_id= trn.accounting_unit_id and mst.accounting_for_office_id= trn.accounting_for_office_id and mst.cashbook_month= trn.cashbook_month and mst.cashbook_year= trn.cashbook_year and mst.voucher_no= trn.voucher_no and mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and mst.ACCOUNTING_FOR_OFFICE_ID=? and mst.VOUCHER_DATE=? and mst.VOUCHER_NO=? and mst.STATUS!='C' and trn.SL_NO=1 ";
            
            else if(cmbSubSystemType.equalsIgnoreCase("TPAOCA"))    
                QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(AMOUNT,'99999999999999.99'))  as amt from FAS_TPA_MASTER mst,FAS_TPA_TRANSACTION trn,FAS_MST_ACCT_UNITS acc_mst where mst.accounting_unit_id= trn.accounting_unit_id and mst.accounting_for_office_id= trn.accounting_for_office_id and mst.cashbook_month= trn.cashbook_month and mst.cashbook_year= trn.cashbook_year and mst.voucher_no= trn.voucher_no and mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and mst.ACCOUNTING_FOR_OFFICE_ID=? and mst.VOUCHER_DATE=? and mst.VOUCHER_NO=? and mst.STATUS!='C' and trn.SL_NO=1 ";
            
            else if(cmbSubSystemType.equalsIgnoreCase("TPAODA")) 
            	QueryType="select ACCOUNTING_UNIT_NAME as com_value,trim(to_char(AMOUNT,'99999999999999.99'))  as amt from FAS_TPA_MASTER mst,FAS_TPA_TRANSACTION trn,FAS_MST_ACCT_UNITS acc_mst where mst.accounting_unit_id= trn.accounting_unit_id and mst.accounting_for_office_id= trn.accounting_for_office_id and mst.cashbook_month= trn.cashbook_month and mst.cashbook_year= trn.cashbook_year and mst.voucher_no= trn.voucher_no and mst.TRF_ACCOUNTING_UNIT_ID=acc_mst.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_UNIT_ID=? and mst.ACCOUNTING_FOR_OFFICE_ID=? and mst.VOUCHER_DATE=? and mst.VOUCHER_NO=? and mst.STATUS!='C' and trn.SL_NO=1 ";
            
            
            
            
            else if(cmbSubSystemType.equalsIgnoreCase("TPAAC"))
//            	QueryType="SELECT ACCOUNTING_UNIT_NAME  AS com_value,trim(TO_CHAR(AMOUNT,'99999999999999.99')) AS amt FROM FAS_TPA_MASTER mst, \n"+
//							"  FAS_TPA_TRANSACTION trn, \n"+
//							"  FAS_MST_ACCT_UNITS acc_mst \n"+
//						"	WHERE mst.accounting_unit_id    =acc_mst.accounting_unit_id \n"+
//							"AND mst.accounting_unit_id      = trn.accounting_unit_id \n"+
//							" AND mst.accounting_for_office_id= trn.accounting_for_office_id \n"+
//							"AND mst.cashbook_month          = trn.cashbook_month \n"+
//						"	AND mst.cashbook_year           = trn.cashbook_year \n"+
//						"	AND mst.voucher_no              = trn.voucher_no \n"+
//						"	AND mst.ACCOUNTING_UNIT_ID      =? \n"+
//						"	AND mst.ACCOUNTING_FOR_OFFICE_ID=? \n"+
//						"	AND mst.VOUCHER_DATE            =? \n"+
//						"	AND mst.VOUCHER_NO              =? \n"+
//						"	AND mst.STATUS!                 ='C' \n"+
//						"	AND trn.SL_NO     in(select max(sl_no) from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" \n"+
//						"	and VOUCHER_NO="+txtVoucher_No+" and cr_dr_indicator='CR' and cashbook_year in (select cashbook_year from FAS_TPA_MASTER \n"+
//						"	where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and VOUCHER_NO="+txtVoucher_No+" )  \n"+
//						"	and cashbook_month in (select cashbook_month from FAS_TPA_MASTER \n"+
//						"	where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and VOUCHER_NO="+txtVoucher_No+")	) ";
            
            	// changed on 18-01-2018 for  issue number 27158 
            	QueryType="SELECT ACCOUNTING_UNIT_NAME  AS com_value,sum(TO_CHAR(AMOUNT,'99999999999999.99')) AS amt FROM FAS_TPA_MASTER mst, \n"+
						"  FAS_TPA_TRANSACTION trn, \n"+
						"  FAS_MST_ACCT_UNITS acc_mst \n"+
					"	WHERE mst.accounting_unit_id    =acc_mst.accounting_unit_id \n"+
						"AND mst.accounting_unit_id      = trn.accounting_unit_id \n"+
						" AND mst.accounting_for_office_id= trn.accounting_for_office_id \n"+
						"AND mst.cashbook_month          = trn.cashbook_month \n"+
					"	AND mst.cashbook_year           = trn.cashbook_year \n"+
					"	AND mst.voucher_no              = trn.voucher_no \n"+
					"	AND mst.ACCOUNTING_UNIT_ID      =? \n"+
					"	AND mst.ACCOUNTING_FOR_OFFICE_ID=? \n"+
					"	AND mst.VOUCHER_DATE            =? \n"+
					"	AND mst.VOUCHER_NO              =? \n"+
					"	AND mst.STATUS!                 ='C' \n"+
					"   AND cr_dr_indicator         ='CR' " +
					"  GROUP BY ACCOUNTING_UNIT_NAME ";
            	
            	
            else if(cmbSubSystemType.equalsIgnoreCase("TPAAD"))
            	QueryType="SELECT ACCOUNTING_UNIT_NAME  AS com_value,trim(TO_CHAR(AMOUNT,'99999999999999.99')) AS amt FROM FAS_TPA_MASTER mst, \n"+
									"  FAS_TPA_TRANSACTION trn, \n"+
									"  FAS_MST_ACCT_UNITS acc_mst \n"+
								"	WHERE mst.accounting_unit_id    =acc_mst.accounting_unit_id \n"+
									"AND mst.accounting_unit_id      = trn.accounting_unit_id \n"+
									" AND mst.accounting_for_office_id= trn.accounting_for_office_id \n"+
									"AND mst.cashbook_month          = trn.cashbook_month \n"+
								"	AND mst.cashbook_year           = trn.cashbook_year \n"+
								"	AND mst.voucher_no              = trn.voucher_no \n"+
								"	AND mst.ACCOUNTING_UNIT_ID      =? \n"+
								"	AND mst.ACCOUNTING_FOR_OFFICE_ID=? \n"+
								"	AND mst.VOUCHER_DATE            =? \n"+
								"	AND mst.VOUCHER_NO              =? \n"+
								"	AND mst.STATUS!                 ='C' \n"+
								"	AND trn.SL_NO     in(select max(sl_no) from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" \n"+
								"	and VOUCHER_NO="+txtVoucher_No+" and cr_dr_indicator='DR' and cashbook_year in (select cashbook_year from FAS_TPA_MASTER \n"+
								"	where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and VOUCHER_NO="+txtVoucher_No+" )  \n"+
								"	and cashbook_month in (select cashbook_month from FAS_TPA_MASTER \n"+
								"	where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and VOUCHER_NO="+txtVoucher_No+")	) ";
            
            System.out.println("QueryType...." + QueryType);
            
            
            
            
            try {
                ps = con.prepareStatement(QueryType);
                
                if(!cmbSubSystemType.equalsIgnoreCase("ADM_A")){
                
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtVoucher_No);
                }
                else
                {
                	ps.setInt(1, cmbAcc_UnitCode);
                    ps.setDate(2, txtCrea_date);
                    ps.setInt(3, txtVoucher_No);
                }
                rs = ps.executeQuery();

                int count1 = 0;
                if (rs.next()) {

                    xml =
 xml + "<com_value><![CDATA[" + rs.getString("com_value") + "]]></com_value>";
                    //System.out.println("...rs.getDouble(amt)"+rs.getDouble("amt"));
                    // If you use getDouble it'll return in terms of 2.2323232232323E23.. Note 'E' in the number
                    // So i used getString to avoid 'E' power

                    xml = xml + "<amt>" + rs.getString("amt") + "</amt>";
                    System.out.println("cmbSubSystemType"+cmbSubSystemType);
                    
                    if (cmbSubSystemType.equalsIgnoreCase("CR"))
                    {
                      QueryType="Select A.RECEIPT_NO  From FAS_RECEIPT_TRANSACTION A join FAS_RECEIPT_MASTER B On  A.RECEIPT_NO=B.RECEIPT_NO Where A.RECEIPT_NO='"+txtVoucher_No+"' And A.Cashbook_Year =extract(YEAR FROM RECEIPT_DATE) AND A.Cashbook_Month =extract(MONTH FROM B.RECEIPT_DATE) AND A.Accounting_Unit_Id=? And A.Accounting_For_Office_Id =? And B.RECEIPT_DATE=? AND A.CB_REF_NO>0";
                    		  System.out.println("QueryType>>>>"+QueryType);
                         
                     	ps = con.prepareStatement(QueryType);
                         ps.setInt(1, cmbAcc_UnitCode);
                         ps.setInt(2, cmbOffice_code);
                         ps.setDate(3, txtCrea_date);

                         rs = ps.executeQuery();

//                          int count = 0;
                          if (rs.next()) {

                              xml = xml + "<Edit>true</Edit>";
                              
                          }
                          else{
                          xml = xml + "<Edit>false</Edit>";
                          }
                       
                    }
                    
                    
                    
                    if(cmbSubSystemType.equalsIgnoreCase("FTH")) 
                    {
                 String sql_one="select t.AUTO_STATUS FROM FAS_FUND_TRF_FROM_HO_TRN t,\n" + 
                        "    FAS_FUND_TRF_FROM_HO_MASTER ag\n" + 
                        "  WHERE t.ACCOUNTING_UNIT_ID    =ag.ACCOUNTING_UNIT_ID\n" + 
                        "  AND t.ACCOUNTING_FOR_OFFICE_ID=ag.ACCOUNTING_FOR_OFFICE_ID\n" + 
                        "  AND t.CASHBOOK_YEAR           =ag.CASHBOOK_YEAR\n" + 
                        "  AND t.CASHBOOK_MONTH          =ag.CASHBOOK_MONTH\n" + 
                        "  AND t.VOUCHER_NO              =ag.VOUCHER_NO\n" + 
                        " and ag.ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+"\n" + 
                        " AND ag.ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+"\n" + 
                         "AND ag.DATE_OF_TRANSFER        = ?\n"+                        
                        " AND ag.VOUCHER_NO               ="+txtVoucher_No+"\n" + 
                        " AND ag.TRANSFER_STATUS!='C' and t.auto_status='Y'";
                        System.out.println("sql_one"+sql_one);
                        ps5 = con.prepareStatement(sql_one);
                       ps5.setDate(1, txtCrea_date);
                        rs5 = ps5.executeQuery();
                        while(rs5.next()) {
                            gcode++;
                        }
                    }
                    count1++;
                }
                if(gcode==0){
                if (count1 == 0)
                    xml = xml + "<flag>failure</flag><autos>failure</autos>";
                else
                    xml = xml + "<flag>success</flag><autos>no</autos>";
                }
                else {
                    if (count1 == 0)
                        xml = xml + "<flag>failure</flag><autos>failure</autos>";
                    else
                        xml = xml + "<flag>success</flag><autos>yes</autos>";
                }
                
                
                
                
                System.out.println("count  " + count1);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
            return;
        } catch (IOException e) {
            System.out.println("Excep" + e);
        }
    }
}
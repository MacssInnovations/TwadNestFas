package Servlets.FAS.FAS1.FundTransferSystem.servlets;

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

public class Fund_Transfer_Create_byHO extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
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

        Connection con = null;
        ResultSet rs = null, rsbank = null;
        CallableStatement cs = null;
        PreparedStatement ps = null, psbank = null;
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

        if (strCommand.equalsIgnoreCase("office_with_bank_betails")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            Calendar c;
            Date txtCrea_date=null;
            xml = "<response><command>office_with_bank_betails</command>";

            try {
                int offid = 0;
                int unit_ID = 0;
                int cmb_HO_acc_unitid = 0;
                offid = Integer.parseInt(request.getParameter("oid"));
                
                try{
                	 String[] sd = request.getParameter("txtCrea_date").split("/");
                     c =
            new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                                  Integer.parseInt(sd[0]));
                     java.util.Date d = c.getTime();
                     txtCrea_date = new Date(d.getTime());
                     System.out.println("txtCrea_date " + txtCrea_date);
                }
                catch(Exception e)
                {
                	
                }
                
                
                try {
                    cmb_HO_acc_unitid =
                            Integer.parseInt(request.getParameter("cmb_HO_acc_unitid"));
                    System.out.println("cmb_HO_acc_unitid11111111"+cmb_HO_acc_unitid);
                } catch (Exception e) {
                    System.out.println("A/c unit id not send");
                }
                String txtModule_Type = request.getParameter("txtModule_Type");
                String cr_dr_indi = request.getParameter("cr_dr_indi");
                System.out.println("txtModule_Type.." + txtModule_Type);
                System.out.println("cr_dr_indi.." + cr_dr_indi);

                xml = xml + "<oid>" + offid + "</oid>";

//                ps =
//                		  con.prepareStatement("select u.ACCOUNTING_UNIT_ID,o.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES u,COM_MST_OFFICES o  where u.ACCOUNTING_FOR_OFFICE_ID=o.OFFICE_ID and u.ACCOUNTING_FOR_OFFICE_ID=? and o.office_status_id not in ('NC','CL','RD')");
                
                ps =
  con.prepareStatement("select u.ACCOUNTING_UNIT_ID,o.OFFICE_NAME,U.DATE_OF_CLOSURE,u.CLOSED from FAS_MST_ACCT_UNIT_OFFICES u,COM_MST_OFFICES o  where u.ACCOUNTING_FOR_OFFICE_ID=o.OFFICE_ID and u.ACCOUNTING_FOR_OFFICE_ID=?");
               
                
                ps.setInt(1, offid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    
                	System.out.println("CLOSED====>"+rs.getString("CLOSED"));
                	
                	if(rs.getString("CLOSED")=="null"||rs.getString("CLOSED")==null)
                	{
                		System.out.println("Closed is null!....");
                		
                		ps =
                      		  con.prepareStatement("select u.ACCOUNTING_UNIT_ID,o.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES u,COM_MST_OFFICES o  where u.ACCOUNTING_FOR_OFFICE_ID=o.OFFICE_ID and u.ACCOUNTING_FOR_OFFICE_ID=? and o.office_status_id not in ('NC','CL','RD')");
                		ps.setInt(1, offid);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                        	if (offid == 5000) {
                                unit_ID = cmb_HO_acc_unitid;
                            } else
                                unit_ID = rs.getInt("ACCOUNTING_UNIT_ID");

                            System.out.println("unit_ID"+unit_ID);
                            xml =
         xml + "<oname>" + rs.getString("OFFICE_NAME") + "</oname>";
                            System.out.println("here xml" + xml);
                            System.out.println("b4 bank fetch");
                            System.out.println(unit_ID);
                            String sql_bank =
                                "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce (br.CITY_TOWN_NAME,'') as bk_br_city " +
                                " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                                " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and  curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.ac_operational_mode_id='OPR' and curr.STATUS='Y'";
                            // here SL_NO=1 means that DEFAULT account number for that unit ..
                            System.out.println(sql_bank);
                            psbank = con.prepareStatement(sql_bank);

                            psbank.setInt(1, unit_ID);
                            psbank.setString(2, txtModule_Type);
                            psbank.setString(3, cr_dr_indi);
                            rsbank = psbank.executeQuery();
                            if (rsbank.next()) {
                                xml = xml + "<flag>success</flag>";
                                System.out.println("inside if rsbank");

                                xml =
         xml + "<AC_HEAD_CODE>" + rsbank.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
                                xml =
         xml + "<BANK_ID>" + rsbank.getInt("BANK_ID") + "</BANK_ID>";
                                xml =
         xml + "<BRANCH_ID>" + rsbank.getInt("BRANCH_ID") + "</BRANCH_ID>";
                                xml =
         xml + "<BANK_AC_NO>" + rsbank.getLong("BANK_AC_NO") + "</BANK_AC_NO>";
                                xml =
         xml + "<bk_br_city>" + rsbank.getString("bk_br_city") + "</bk_br_city>";
                            } else
                                xml = xml + "<flag>failure_bank</flag>";
                            ps.close();
                            rs.close();
                            psbank.close();
                            rsbank.close();
                        }
                	}
                	
                	else if(rs.getString("CLOSED").equalsIgnoreCase("Y"))
    		   		{
    		   			if(rs.getDate("DATE_OF_CLOSURE")!=null)
    		   			{
    		   			System.out.println("DATE_OF_CLOSURE===>"+rs.getDate("DATE_OF_CLOSURE"));
    		   			System.out.println("Create Date===>"+txtCrea_date);
    		   				if(txtCrea_date.compareTo(rs.getDate("DATE_OF_CLOSURE"))<=0)
    		   				{
                	if (offid == 5000) {
                        unit_ID = cmb_HO_acc_unitid;
                    } else
                        unit_ID = rs.getInt("ACCOUNTING_UNIT_ID");

                    System.out.println("unit_ID"+unit_ID);
                    xml =
 xml + "<oname>" + rs.getString("OFFICE_NAME") + "</oname>";
                    System.out.println("here xml" + xml);
                    System.out.println("b4 bank fetch");
                    System.out.println(unit_ID);
                    String sql_bank =
                        "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || COALESCE(br.CITY_TOWN_NAME,'') as bk_br_city " +
                        " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? " +
                        " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and  curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.ac_operational_mode_id='OPR' and curr.STATUS='Y'";
                    // here SL_NO=1 means that DEFAULT account number for that unit ..
                    System.out.println(sql_bank);
                    psbank = con.prepareStatement(sql_bank);

                    psbank.setInt(1, unit_ID);
                    psbank.setString(2, txtModule_Type);
                    psbank.setString(3, cr_dr_indi);
                    rsbank = psbank.executeQuery();
                    if (rsbank.next()) {
                        xml = xml + "<flag>success</flag>";
                        System.out.println("inside if rsbank");

                        xml =
 xml + "<AC_HEAD_CODE>" + rsbank.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
                        xml =
 xml + "<BANK_ID>" + rsbank.getInt("BANK_ID") + "</BANK_ID>";
                        xml =
 xml + "<BRANCH_ID>" + rsbank.getInt("BRANCH_ID") + "</BRANCH_ID>";
                        xml =
 xml + "<BANK_AC_NO>" + rsbank.getLong("BANK_AC_NO") + "</BANK_AC_NO>";
                        xml =
 xml + "<bk_br_city>" + rsbank.getString("bk_br_city") + "</bk_br_city>";
                    } else
                        xml = xml + "<flag>failure_bank</flag>";
                    ps.close();
                    rs.close();
                    psbank.close();
                    rsbank.close();
		   			}
		   			else
		   			{
		   				xml = xml + "<flag>failure_date</flag>";
		   			}
		   			}
                	else
                	{
                		xml=xml+"<flag>failure_load</flag>";
                	}
    		   		}
                	

                } else
                    xml = xml + "<flag>failure_office</flag>";
            } catch (Exception e1) {
                xml = xml + "<flag>failure_office</flag>";
                System.out.println("Exception handling.." + e1);
            }
            xml = xml + "</response>";
            System.out.println("xml.." + xml);
            out.println(xml);
        }
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
        ResultSet rs = null;
        CallableStatement cs = null;
        PreparedStatement ps = null;
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
            //  General details
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId =
                0, Total_TRN_Rec = 0;
            long txtBankAccountNo = 0;
            double txtAmount = 0;
            String txtCR_DB = "";
            String txtRemarks = "";
            Date txtCrea_date = null, txtReferenceDate = null;
            String txtReferenceNo = "";
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            try {
                txtBankId =
                        Integer.parseInt(request.getParameter("txtBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankId " + txtBankId);

            try {
                txtBranchId =
                        Integer.parseInt(request.getParameter("txtBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBranchId " + txtBranchId);

            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);


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

            try {
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);

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

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtCR_DB = request.getParameter("txtCR_DB");
            System.out.println("txtCR_DB " + txtCR_DB);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);

            txtReferenceNo = request.getParameter("txtReferenceNo");
            System.out.println("txtReferenceNo " + txtReferenceNo);

            if (!request.getParameter("txtReferenceDate").equalsIgnoreCase("")) {
                sd = request.getParameter("txtReferenceDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                txtReferenceDate = new Date(d.getTime());
            }
            System.out.println("txtReferenceDate " + txtReferenceDate);


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
             }
             ps.close();
             rs.close();
             }
             catch(Exception e)
             {
             sendMessage(response,"The Cash Book Period Not Found ","ok");
             System.out.println("exception"+e);
             }

          */

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");
                String No_TRN_Rec[] =
                    request.getParameterValues("Sub_Acc_Head_Code");
                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                Total_TRN_Rec =
                        No_TRN_Rec.length; //Integer.parseInt(No_TRN_Rec);
                System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
                cs =
  con.prepareCall("call FAS_FUND_TRF_FRM_HO_MAS_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?,?,?,?::numeric,?,?,?::numeric)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setDate(3, txtCrea_date);
                cs.setInt(4, txtCash_year);
                cs.setInt(5, txtCash_Month_hid);
                cs.setInt(6, txtVoucher_No);
                cs.setString(7, txtCR_DB);
                cs.setInt(8, txtCash_Acc_code);
                cs.setInt(9, txtBankId);
                cs.setInt(10, txtBranchId);
                cs.setLong(11, txtBankAccountNo);
                cs.setString(12, txtRemarks);
                cs.setDouble(13, txtAmount);
                cs.setString(14, txtReferenceNo);
                cs.setDate(15, txtReferenceDate);
                cs.setString(16, "insert");
                cs.registerOutParameter(17, java.sql.Types.NUMERIC);
                cs.registerOutParameter(6, java.sql.Types.NUMERIC);
                cs.setNull(17, java.sql.Types.NUMERIC);
                cs.setNull(6, java.sql.Types.NUMERIC);
                cs.setString(18, update_user);
                cs.setTimestamp(19, ts);
                cs.setInt(20, Total_TRN_Rec);
                System.out.println("b4 exe ");
                cs.execute();
//                txtVoucher_No = cs.getInt(6);
//                int errcode = cs.getInt(17);
                txtVoucher_No = cs.getBigDecimal(6).intValue();
                int errcode = cs.getBigDecimal(17).intValue();
                
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The Fund Transfer Transaction failed ", "ok");
                    return;
                } else {
                    String Grid_Sub_Office_code[] =
                        request.getParameterValues("Sub_Office_code");
                    String Grid_H_code[] =
                        request.getParameterValues("Sub_Acc_Head_Code");
                    String Grid_HO_acc_unitid[] =
                        request.getParameterValues("HO_Unit_ID");
                    String Grid_Sub_Bank_Acc_No[] =
                        request.getParameterValues("Sub_Bank_Acc_No");
                    String Grid_Sub_Bank_Id[] =
                        request.getParameterValues("Sub_Bank_Id");
                    String Grid_Sub_Branch_Id[] =
                        request.getParameterValues("Sub_Branch_Id");
                    String Grid_particular[] =
                        request.getParameterValues("particular");
                    String Grid_sl_amt[] =
                        request.getParameterValues("sl_amt");

                    String Grid_Cheque_DD[] =
                        request.getParameterValues("Cheque_DD");
                    String Grid_Cheque_DD_NO[] =
                        request.getParameterValues("Cheque_DD_NO");
                    String Grid_Cheque_DD_date[] =
                        request.getParameterValues("Cheque_DD_date");

                    String sql =
                        "insert into FAS_FUND_TRF_FROM_HO_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,TRANSFER_TO_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO," +
                        "ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,OFFICE_BANK_ID,OFFICE_BRANCH_ID,OFFICE_ACCOUNT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE," +
                        "AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,TRANSFERED_TO_HO_UNIT_ID,FUND_TYPE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    int SL_NO = 1, txtSubAcc_HeadCode = 0, txtSubOffice_code =
                        0, txtSubBankId = 0, txtSubBranchId = 0;
                    int txtHO_acc_unitid = 0;
                    long txtSubBankAccountNo = 0;
                    Date txtCheque_DD_date = null;
                    double txtsub_Amount = 0;
                    String rad_sub_CR_DR = "", txtParticular = "";
                    String txtCheque_DD = "", txtCheque_DD_NO = "";


                    /**
                         * Get Fund Type eg: C-Civil or W-Work
                         */

                    String fund_type = null;
                    try {
                        fund_type = request.getParameter("fund_type");
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    System.out.println("FUND TYPE ----------->" + fund_type);

                    ps = con.prepareStatement(sql);
                    for (int k = 0; k < Grid_H_code.length; k++) {

                        try {
                            txtSubAcc_HeadCode =
                                    Integer.parseInt(Grid_H_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        rad_sub_CR_DR = "DR";

                        System.out.println("1" + txtSubAcc_HeadCode);

                        try {
                            txtSubOffice_code =
                                    Integer.parseInt(Grid_Sub_Office_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        System.out.println("2" + txtSubOffice_code);

                        try {
                            txtHO_acc_unitid =
                                    Integer.parseInt(Grid_HO_acc_unitid[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        System.out.println("3" + txtHO_acc_unitid);

                        try {
                            txtSubBankAccountNo =
                                    Long.parseLong(Grid_Sub_Bank_Acc_No[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        System.out.println("4" + txtSubBankAccountNo);

                        try {
                            txtSubBankId =
                                    Integer.parseInt(Grid_Sub_Bank_Id[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        System.out.println("5" + txtSubBankId);

                        try {
                            txtSubBranchId =
                                    Integer.parseInt(Grid_Sub_Branch_Id[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        System.out.println("6" + txtSubBranchId);

                        txtParticular = Grid_particular[k];

                        System.out.println("7" + txtParticular);

                        try {
                            txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        System.out.println("8" + txtsub_Amount);

                        try {
                            txtCheque_DD = Grid_Cheque_DD[k];
                        } catch (Exception e) {
                            System.out.println("exception in trnas");
                        }

                        System.out.println("9" + txtCheque_DD);

                        try {
                            txtCheque_DD_NO = Grid_Cheque_DD_NO[k];
                        } catch (Exception e) {
                            System.out.println("exception in trnas");
                        }

                        System.out.println("10" + txtCheque_DD_NO);

                        if (!Grid_Cheque_DD_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Cheque_DD_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtCheque_DD_date = new Date(d.getTime());
                        }

                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setInt(2, cmbOffice_code);
                        ps.setInt(3, txtSubOffice_code);
                        ps.setInt(4, txtCash_year);
                        ps.setInt(5, txtCash_Month_hid);
                        ps.setInt(6, txtVoucher_No);
                        ps.setInt(7, SL_NO);
                        ps.setInt(8, txtSubAcc_HeadCode);
                        ps.setString(9, rad_sub_CR_DR);
                        ps.setInt(10, txtSubBankId);
                        ps.setInt(11, txtSubBranchId);
                        ps.setLong(12, txtSubBankAccountNo);
                        ps.setString(13, txtCheque_DD);
                        ps.setString(14, txtCheque_DD_NO);
                        ps.setDate(15, txtCheque_DD_date);
                        ps.setDouble(16, txtsub_Amount);
                        ps.setString(17, txtParticular);
                        ps.setString(18, update_user);
                        ps.setTimestamp(19, ts);
                        ps.setInt(20, txtHO_acc_unitid);
                        ps.setString(21, fund_type);


                        SL_NO++;
                        ps.executeUpdate();

                        System.out.println("txtSubOffice_code.." +
                                           txtSubOffice_code);
                        System.out.println("txtHO_acc_unitid.." +
                                           txtHO_acc_unitid);

                        txtSubOffice_code = 0;
                        txtSubAcc_HeadCode = 0;
                        txtSubBankId = 0;
                        txtSubBranchId = 0;
                        txtSubBankAccountNo = 0;
                        txtCheque_DD = "";
                        txtCheque_DD_NO = "";
                        txtCheque_DD_date = null;
                        txtsub_Amount = 0;
                        txtParticular = "";
                        txtHO_acc_unitid = 0;

                        System.out.println("txtSubOffice_code.." +
                                           txtSubOffice_code);
                        System.out.println("txtHO_acc_unitid.." +
                                           txtHO_acc_unitid);

                    }
                    ps.close();
                    System.out.println("b4 commit");
                    con.commit();
                    sendMessage(response,
                                "The Fund Transfer Transaction Voucher Number '" +
                                txtVoucher_No +
                                "' has been Created Successfully ", "ok");
                }

            }

            catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                }
                sendMessage(response, "The Fund Transfer TransactionFailed ",
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
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("error in send message");
        }
    }
}

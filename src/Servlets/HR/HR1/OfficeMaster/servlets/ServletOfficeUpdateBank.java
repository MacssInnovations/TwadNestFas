package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;


public class ServletOfficeUpdateBank extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        String strCriteria = "";
        int Office_Id = 0;


        int found = 0;
        String mode = request.getParameter("command");
        System.out.println(mode);


        String xml = "";
        System.out.println("Hai");
        Connection connection = null;
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
            if (mode.equals("Load")) {
                try {
                    System.out.println("hai1");
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));
                    String sql =
                        "select OFFICE_ID,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,OFFICE_PHONE_NO,OFFICE_FAX_NO from com_mst_offices where OFFICE_ID=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Office_Id);
                    connection.clearWarnings();
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            if (results.next()) {
                                xml =
 xml + "<options><id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><District>" +
   results.getInt("DISTRICT_CODE") + "</District><Phone>" +
   results.getString("OFFICE_PHONE_NO") + "</Phone><Fax>" +
   results.getString("OFFICE_FAX_NO") + "</Fax></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in Query" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                } finally {
                    //connection.close();
                }
            }

            else if (mode.equals("Add")) {
                System.out.println("Add Mode");

                //Getting Values from Hidden Field
                int officeid =
                    Integer.parseInt(request.getParameter("txtOffice_Id"));
                System.out.println("Office id" + officeid);
                String slno[] = request.getParameterValues("sno");
                String bankid[] = request.getParameterValues("bankname");
                String branchid[] = request.getParameterValues("branchname");
                String acctype[] = request.getParameterValues("acctype");
                String operationalmode[] =
                    request.getParameterValues("operationalmode");
                String datecreated[] =
                    request.getParameterValues("datecreated");
                String accno[] = request.getParameterValues("accno");
                String headaccno[] = request.getParameterValues("headaccno");
                String initialdeposit[] =
                    request.getParameterValues("initialdeposit");
                //System.out.println(slno);

                //Creation for Integer Array
                int slno1[] = new int[slno.length];
                int bankid1[] = new int[slno.length];
                int branchid1[] = new int[slno.length];
                int accno1[] = new int[slno.length];
                int headaccno1[] = new int[slno.length];
                float initialdeposit1[] = new float[slno.length];

                //Converting the Values into Integer Array
                for (int k = 0; k < slno.length; k++) {
                    slno1[k] = Integer.parseInt(slno[k]);
                    bankid1[k] = Integer.parseInt(bankid[k]);
                    branchid1[k] = Integer.parseInt(branchid[k]);
                    accno1[k] = Integer.parseInt(accno[k]);
                    headaccno1[k] = Integer.parseInt(headaccno[k]);
                    initialdeposit1[k] = Float.parseFloat(initialdeposit[k]);
                    System.out.println("slno is" + slno[k]);
                    System.out.println("Bank is" + bankid1[k]);
                    System.out.println("Branch is" + branchid1[k]);
                    System.out.println("Accno is" + accno1[k]);
                    System.out.println("HeadAccno is" + headaccno1[k]);
                }

                //Convert into Sql Date
                java.sql.Date date1[] = new java.sql.Date[slno.length];
                java.util.Date d1 = null;
                //System.out.println("datecreated length"+datecreated.length);

                for (int i = 0; i < slno.length; i++) {
                    SimpleDateFormat dateFormat1 =
                        new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        //System.out.println("inside date");
                        d1 = dateFormat1.parse(datecreated[i]);
                        dateFormat1.applyPattern("yyyy-MM-dd");
                        //System.out.println("inside date1");
                        datecreated[i] = dateFormat1.format(d1);
                        System.out.println("inside date2:" + datecreated[i]);
                        date1[i] = Date.valueOf(datecreated[i]);
                        System.out.println("date is" + date1[i]);

                    } catch (Exception e) {
                        System.out.println("Exception in Date Converted" + e);
                    }
                }
                try {
                    System.out.println("Add");
                    connection.clearWarnings();
                    connection.setAutoCommit(false);
                    String sql2 =
                        "delete from FAS_OFFICE_BANK_CURRENT where office_id=?";
                    String sql3 =
                        "delete from fas_office_bank_Ac_current where office_id=?";
                    PreparedStatement ps = connection.prepareStatement(sql2);
                    ps.setInt(1, officeid);
                    ps.executeUpdate();
                    ps.close();

                    ps = connection.prepareStatement(sql3);
                    ps.setInt(1, officeid);
                    ps.executeUpdate();
                    ps.close();

                    String sql =
                        "insert into FAS_OFFICE_BANK_CURRENT(OFFICE_ID,SL_NO,BANK_ID,BRANCH_ID) values(?,?,?,?)";
                    String sql1 =
                        "insert into FAS_OFFICE_BANK_AC_CURRENT(office_id,BANK_ID,BRANCH_ID,BANK_AC_NO,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,AC_OPENING_DATE,AC_HEAD_CODE,INITIAL_DEPOSIT_AMT,Sl_No) values(?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);

                    for (int j = 0; j < slno.length; j++) {
                        statement.setInt(1, officeid);
                        statement.setInt(2, slno1[j]);
                        statement.setInt(3, bankid1[j]);
                        statement.setInt(4, branchid1[j]);
                        statement.executeUpdate();

                    }
                    PreparedStatement statement1 =
                        connection.prepareStatement(sql1);

                    for (int l = 0; l < slno.length; l++) {
                        statement1.setInt(1, officeid);
                        statement1.setInt(2, bankid1[l]);
                        statement1.setInt(3, branchid1[l]);
                        statement1.setInt(4, accno1[l]);
                        statement1.setString(5, acctype[l]);
                        statement1.setString(6, operationalmode[l]);
                        statement1.setDate(7, date1[l]);
                        statement1.setInt(8, headaccno1[l]);
                        statement1.setFloat(9, initialdeposit1[l]);
                        statement1.setInt(10, slno1[l]);
                        statement1.executeUpdate();
                    }
                    connection.commit();


                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                    connection.rollback();
                }

                finally {
                    //connection.close();
                }
                String msg =
                    "Update Bank Details Has been Successfully Inserted.<br>Office ID  is  : " +
                    officeid;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");


            } else if (mode.equals("SlNo")) {

                try {
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));
                    String sql =
                        "select MAX(SL_NO) from FAS_OFFICE_BANK_CURRENT where office_id=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Office_Id);
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            if (results.next()) {
                                xml =
 xml + "<options><id>" + results.getInt(1) + "</id></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in QuerySlNo" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }


                } catch (Exception e) {
                    System.out.println("Exception in SlNO" + e);
                }
            }

            else if (mode.equals("BankId")) {

                System.out.println("inside Bank");
                System.out.println("bank is" + request.getParameter("BankId"));
                try {
                    int Bank_Id =
                        Integer.parseInt(request.getParameter("BankId"));
                    System.out.println("bank id is" + Bank_Id);
                    String sql =
                        "select Branch_id,Branch_name from fas_mst_bank_branches where bank_id=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Bank_Id);
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            String Branch = request.getParameter("Branch");
                            if (Branch != null) {
                                xml = xml + "<Branch>" + Branch + "</Branch>";
                            }
                            while (results.next()) {
                                xml =
 xml + "<options><id>" + results.getInt(1) + "</id><name>" +
   results.getString(2) + "</name></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in QueryBranch" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }


                } catch (Exception e) {
                    System.out.println("Exception in Branch" + e);
                }


            }

            else if (mode.equals("Branch")) {

                System.out.println("inside Branch");
                System.out.println("bank is" + request.getParameter("BankId"));
                try {
                    int Bank_Id =
                        Integer.parseInt(request.getParameter("BankId"));
                    int Branch_Id =
                        Integer.parseInt(request.getParameter("BranchId"));

                    String sql =
                        "select BRANCH_ADDRESS1,BRANCH_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,MICR_CODE from fas_mst_bank_branches where bank_id=? and branch_id=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Bank_Id);
                    statement.setInt(2, Branch_Id);
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            while (results.next()) {
                                xml =
 xml + "<options><Address1>" + results.getString(1) + "</Address1><Address2>" +
   results.getString(2) + "</Address2><Address3>" + results.getString(3) +
   "</Address3><District>" + results.getInt(4) + "</District><Micr>" +
   results.getInt(5) + "</Micr></options>";
                                found++;
                            }
                            if (found == 0)
                                xml = "<response><flag>failure</flag>";

                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in QueryBranch" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }


                } catch (Exception e) {
                    System.out.println("Exception in Branch" + e);
                }


            }

            else if (mode.equals("TableView")) {


                System.out.println("inside TableView");
                //System.out.println("bank is"+request.getParameter("BankId"));
                try {
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));

                    String sql =
                        "select fas_mst_banks.BANK_id, bank_name ,fas_mst_bank_branches.BRANCH_ID, branch_name , micr_code, BANK_AC_NO,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,AC_OPENING_DATE,AC_HEAD_CODE,INITIAL_DEPOSIT_AMT,Sl_No from fas_mst_bank_branches,fas_mst_banks,fas_office_bank_ac_current where fas_mst_banks.bank_id=fas_office_bank_ac_current.bank_id and (fas_mst_bank_branches.bank_id=fas_office_bank_ac_current.bank_id  and fas_mst_bank_branches.branch_id=fas_office_bank_ac_current.branch_id ) and office_id=?";

                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Office_Id);

                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println("hai");
                        try {
                            xml = "<response><flag>success</flag>";
                            //xml=xml+"<options>";
                            while (results.next()) {
                                java.sql.Date DateOfFormation =
                                    results.getDate(9);
                                String DateToBeDisplayed = "";
                                if (DateOfFormation == null) {
                                    DateToBeDisplayed = "Not Specified";
                                } else {
                                    try {
                                        java.text.SimpleDateFormat sdf =
                                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                                        DateToBeDisplayed =
                                                sdf.format(DateOfFormation);
                                    } catch (Exception e) {
                                        System.out.println("error while formatting date : " +
                                                           e);
                                        DateToBeDisplayed = "Not Specified";
                                    }
                                }
                                System.out.println("date : " +
                                                   DateToBeDisplayed);
                                xml =
 xml + "<BankId>" + results.getInt(1) + "</BankId><BankName>" +
   results.getString(2) + "</BankName><BranchId>" + results.getInt(3) +
   "</BranchId><BranchName>" + results.getString(4) + "</BranchName><Micr>" +
   results.getInt(5) + "</Micr><AccNo>" + results.getInt(6) +
   "</AccNo><AccType>" + results.getString(7) + "</AccType><OperationalMode>" +
   results.getString(8) + "</OperationalMode><AccDate>" + DateToBeDisplayed +
   "</AccDate><AccHead>" + results.getInt(10) + "</AccHead><InitialDeposit>" +
   results.getInt(11) + "</InitialDeposit><SlNo>" + results.getInt(12) +
   "</SlNo>";
                                found++;
                            }

                            if (found == 0)
                                xml = "<response><flag>failure</flag>";
                            //xml=xml+"</options>";
                            xml = xml + "</response>";
                        } catch (SQLException e) {
                            System.out.println("Exception in QueryBranch" + e);
                        } finally {
                            results.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception in statement:" + e);
                    } finally {
                        statement.close();
                    }


                } catch (Exception e) {
                    System.out.println("Exception in Branch" + e);
                }


            }


        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }

        System.out.println("Xml is : " + xml);
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();

        out.write(xml);
        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}

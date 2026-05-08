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

public class ServletOfficeBank extends HttpServlet {
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
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();
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
                int officeid =
                    Integer.parseInt(request.getParameter("txtOffice_Id"));
                System.out.println("OfficeId is" + officeid);
                int slno = Integer.parseInt(request.getParameter("txtSl_No"));
                System.out.println("slno is:" + slno);
                int bankid = Integer.parseInt(request.getParameter("cmbBank"));
                System.out.println("bankid is:" + bankid);
                int branchid =
                    Integer.parseInt(request.getParameter("cmbBranch"));
                System.out.println("branchid is:" + branchid);
                String DateCreated = request.getParameter("txtDateOfClosure");
                System.out.println("date is:" + DateCreated);
                String Reason = request.getParameter("Reason");

                //Getting Values from The Table

                //String bankidt[]=request.getParameterValues("bankname");
                //String branchidt[]=request.getParameterValues("branchname");
                String acctype[] = request.getParameterValues("acctype");
                String operationalmode[] =
                    request.getParameterValues("operationalmode");
                String datecreated[] =
                    request.getParameterValues("datecreated");
                System.out.println("Date is" + datecreated);
                String accno[] = request.getParameterValues("accno");
                String headaccno[] = request.getParameterValues("headaccno");
                String initialamount[] =
                    request.getParameterValues("initialamount");


                //Creation for Integer Array
                int slno1[] = new int[accno.length];
                int bankid1[] = new int[accno.length];
                int branchid1[] = new int[accno.length];
                int accno1[] = new int[accno.length];
                int headaccno1[] = new int[accno.length];
                int initialamount1[] = new int[accno.length];


                //Converting the Values into Integer Array
                try {
                    for (int k = 0; k < accno.length; k++) {

                        //bankid1[k]=Integer.parseInt(bankidt[k]);
                        //branchid1[k]=Integer.parseInt(branchidt[k]);
                        accno1[k] = Integer.parseInt(accno[k]);
                        headaccno1[k] = Integer.parseInt(headaccno[k]);
                        initialamount1[k] = Integer.parseInt(initialamount[k]);

                        System.out.println("Bank is" + bankid1[k]);
                        System.out.println("Branch is" + branchid1[k]);
                        System.out.println("Accno is" + accno1[k]);
                        System.out.println("HeadAccno is" + headaccno1[k]);
                    }
                } catch (Exception e) {

                    //String msg="Null Pointer Exception ";
                    //msg="<br><br><p><b>" + msg + "</b></p>";
                    //sendMessage(response,msg,"ok");
                    System.out.println("Exception in Null Pointer" + e);
                }

                //Convert into Sql Date
                java.sql.Date date1[] = new java.sql.Date[accno.length];
                java.util.Date d1 = null;
                //System.out.println("datecreated length"+datecreated.length);

                for (int i = 0; i < accno.length; i++) {
                    SimpleDateFormat dateFormat1 =
                        new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        System.out.println("inside date");
                        /*d1=dateFormat1.parse(datecreated[i]);
                                  dateFormat1.applyPattern("yyyy-MM-dd");
                                  System.out.println("inside date1");
                                datecreated[i]=dateFormat1.format(d1);
                                  System.out.println("inside date2:"+datecreated[i]);*/
                        date1[i] = Date.valueOf(datecreated[i]);
                        System.out.println("date is" + date1[i]);

                    } catch (Exception e) {
                        //String msg="Date Converted Exception"  ;
                        //msg="<br><br><p><b>" + msg + "</b></p>";
                        //sendMessage(response,msg,"ok");
                        System.out.println("Exception in Date Converted" + e);
                    }
                }


                SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date d;
                java.sql.Date date;
                try {
                    //System.out.println(Dob);
                    d = dateFormat.parse(DateCreated);
                    dateFormat.applyPattern("yyyy-MM-dd");
                    DateCreated = dateFormat.format(d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                date = java.sql.Date.valueOf(DateCreated);
                System.out.println("Office id" + officeid);

                //System.out.println(slno);


                try {
                    System.out.println("Add");
                    connection.clearWarnings();
                    connection.setAutoCommit(false);

                    String sql =
                        "insert into fas_office_bank_closed(office_id,CLOSED_BANK_ID,CLOSED_BRANCH_ID,CLOSURE_SLNO,CLOSURE_DATE,CLOSURE_REASON) values(?,?,?,?,?,?)";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, officeid);
                    statement.setInt(2, bankid);
                    statement.setInt(3, branchid);
                    statement.setInt(4, slno);
                    statement.setDate(5, date);
                    statement.setString(6, Reason);
                    statement.executeUpdate();
                    statement.close();

                    String sql1 =
                        "insert into FAS_OFFICE_BANK_AC_CLOSED(office_id,bank_id,branch_id,ACCOUNT_NO,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,AC_OPENING_DATE,INITIAL_DEPOSIT_AMT,AC_HEAD_CODE) select office_id,bank_id,branch_id,BANK_AC_NO,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,AC_OPENING_DATE,INITIAL_DEPOSIT_AMT,AC_HEAD_CODE from FAS_OFFICE_BANK_AC_CURRENT where office_id=? and bank_id=? and branch_id=?";
                    PreparedStatement statement1 =
                        connection.prepareStatement(sql1);
                    statement1.setInt(1, officeid);
                    statement1.setInt(2, bankid);
                    statement1.setInt(3, branchid);
                    statement1.executeUpdate();

                    /*for(int j=0;j<bankidt.length;j++)
                                         {
                                               statement1.setInt(1,officeid);
                                               statement1.setInt(2,bankid1[j]);
                                               statement1.setInt(3,branchid1[j]);
                                               statement1.setInt(4,accno1[j]);
                                               statement1.setString(5,acctype[j]);
                                               statement1.setString(6,operationalmode[j]);
                                               statement1.setDate(7,date1[j]);
                                               statement1.setInt(8,initialamount1[j]);
                                               statement1.setInt(9,headaccno1[j]);
                                               statement1.executeUpdate();

                                         }*/
                    statement1.close();
                    String sql2 =
                        "delete from fas_office_bank_current where office_id=? and bank_id=? and branch_id=?";
                    statement1 = connection.prepareStatement(sql2);
                    statement1.setInt(1, officeid);
                    statement1.setInt(2, bankid);
                    statement1.setInt(3, branchid);
                    statement1.executeUpdate();

                    /*for(int j=0;j<bankidt.length;j++)
                                         {
                                             statement1.setInt(1,officeid);
                                             statement1.setInt(2,bankid);
                                             statement1.setInt(3,branchid);
                                             statement1.executeUpdate();
                                         }*/
                    statement1.close();
                    String sql3 =
                        "delete from fas_office_bank_ac_current where office_id=? and bank_id=? and branch_id=?";
                    statement1 = connection.prepareStatement(sql3);
                    statement1.setInt(1, officeid);
                    statement1.setInt(2, bankid);
                    statement1.setInt(3, branchid);
                    statement1.executeUpdate();


                    /*for(int j=0;j<bankidt.length;j++)
                                         {
                                            statement1.setInt(1,officeid);
                                            statement1.setInt(2,bankid1[j]);
                                            statement1.setInt(3,branchid1[j]);
                                            statement1.setInt(4,accno1[j]);
                                            statement1.setString(5,acctype[j]);
                                            statement1.setString(6,operationalmode[j]);
                                            statement1.setDate(7,date1[j]);
                                            statement1.setInt(8,headaccno1[j]);
                                            statement1.setInt(9,initialamount1[j]);
                                            statement1.executeUpdate();

                                         }*/
                    connection.commit();

                } catch (SQLException e) {
                    System.out.println("Exception in connection:" + e);
                    connection.rollback();
                }

                finally {
                    //connection.close();
                }
                String msg =
                    "Bank Closure Details Has been Successfully Inserted.<br>Office ID  is  : " +
                    officeid;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");


            } else if (mode.equals("SlNo")) {

                try {
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));
                    System.out.println("OfficeId is:" + Office_Id);
                    String sql =
                        "select MAX(CLOSURE_SLNO) from fas_office_bank_closed where office_id=?";
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
                    Office_Id =
                            Integer.parseInt(request.getParameter("OfficeId"));

                    String sql =
                        "select Bank_id,Branch_Id,BANK_AC_NO,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,AC_HEAD_CODE,AC_OPENING_DATE,INITIAL_DEPOSIT_AMT from fas_office_bank_ac_current where bank_id=? and branch_id=? and office_id=?";
                    PreparedStatement statement =
                        connection.prepareStatement(sql);
                    statement.setInt(1, Bank_Id);
                    statement.setInt(2, Branch_Id);
                    statement.setInt(3, Office_Id);
                    try {
                        ResultSet results = statement.executeQuery();
                        System.out.println();
                        try {
                            xml = "<response><flag>success</flag>";
                            while (results.next()) {
                                java.sql.Date DateOfFormation =
                                    results.getDate("AC_OPENING_DATE");
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
                                xml =
 xml + "<options><bankid>" + results.getInt(1) + "</bankid><branchid>" +
   results.getString(2) + "</branchid><AccNo>" + results.getInt("BANK_AC_NO") +
   "</AccNo><AccType>" + results.getString("BANK_AC_TYPE_ID") +
   "</AccType><operationalmode>" +
   results.getString("AC_OPERATIONAL_MODE_ID") +
   "</operationalmode><AccHead>" + results.getInt("AC_HEAD_CODE") +
   "</AccHead><AccDate>" + DateToBeDisplayed + "</AccDate><InitialAmount>" +
   results.getInt("INITIAL_DEPOSIT_AMT") + "</InitialAmount></options>";
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


        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }

        System.out.println("Xml is : " + xml);


        out.println(xml);
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

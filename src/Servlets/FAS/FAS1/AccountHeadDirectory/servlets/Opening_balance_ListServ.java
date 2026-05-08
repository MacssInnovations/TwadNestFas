package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Opening_balance_ListServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private ResultSet rs1 = null;
    private PreparedStatement ps1 = null;
    private ResultSet rs2 = null;
    private PreparedStatement ps2 = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        System.out.println("hello");

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

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

        String strCommand = "";
        String strchar = "";
        String strStart = "";
        String xml = "";
        float uptoCR = 0;
        float uptoDB = 0;
        int currCR = 0;
        int currDR = 0;
        int b = 0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


        try {

            strCommand = request.getParameter("cmd");
            System.out.println("assign.command...." + strCommand);
            strchar = request.getParameter("scod");
            System.out.println("assign.scod...." + strchar);
            strchar.equalsIgnoreCase("strchar");
            strStart = strchar.toUpperCase();
            System.out.println("strStart...." + strStart);


        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        if (strCommand.equalsIgnoreCase("list")) {

            System.out.println("Inside List in listing... ");
            String AcId = "";
            String AcName = "";
            xml = "<response><command>list</command>";

            b = 0;
            try {
                ps1 =
 con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_DESC like ('" +
                      strchar + "%') or ACCOUNT_HEAD_DESC like ('" + strStart +
                      "%')");

                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    AcId = rs1.getString("ACCOUNT_HEAD_CODE");
                    AcName = rs1.getString("ACCOUNT_HEAD_DESC");
                }
                rs1.close();
                ps1.close();
                ps =
  con.prepareStatement("SELECT * FROM FAS_OB_GL_HEADS where ACCOUNTING_HEAD_CODE=?");
                ps.setString(1, AcId);

                rs = ps.executeQuery();

                try {

                    while (rs.next()) {
                        uptoCR = rs.getFloat("UPTO_DATE_CR_BALANCE");
                        System.out.println("upto cr%%%%%%%%%%%%%%%%%%%%%" +
                                           uptoCR);
                        uptoDB = rs.getFloat("UPTO_DATE_DR_BALANCE");
                        System.out.println("upto db " + uptoDB);
                        currDR = rs.getInt("CURR_YEAR_DR_BALANCE");
                        System.out.println("curr dr is " + currDR);
                        currCR = rs.getInt("CURR_YEAR_CR_BALANCE");
                        System.out.println("curr cr is " + currCR);

                        xml =
 xml + "<flag>success</flag><AcId>" + AcId + "</AcId><AcName>" + AcName +
   "</AcName><uptoCR>" + uptoCR + "</uptoCR><uptoDB>" + uptoDB +
   "</uptoDB><currDR>" + currDR + "</currDR><currCR>" + currCR + "</currCR>";
                        b++;

                    }
                    rs.close();
                    ps.close();
                } catch (Exception ae) {
                    System.out.println("Exception in while loop..." + ae);
                }

                //////////////////for text box display start.../////////////////////////////

                String majorcode = "";
                String majorDesc = "";
                String minorcode = "";
                String minordesc = "";
                String subcode1 = "";
                String subdesc1 = "";
                String subcode2 = "";
                String subdesc2 = "";


                try {
                    ps =
  con.prepareStatement("select * from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps.setString(1, AcId);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        majorcode = rs.getString("MAJOR_HEAD_CODE");
                        minorcode = rs.getString("MINOR_HEAD_CODE");
                        subcode1 = rs.getString("SUB_HEAD1_CODE");
                        subcode2 = rs.getString("SUB_HEAD2_CODE");
                        try {
                            ps1 =
 con.prepareStatement("select * from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
                            ps1.setString(1, majorcode);
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                majorDesc = rs1.getString("MAJOR_HEAD_DESC");
                                xml =
 xml + "<majorDesc>" + majorDesc + "</majorDesc>";
                            }
                            rs1.close();
                            ps1.close();
                            ps1 =
 con.prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MINOR_HEAD_CODE=?");
                            ps1.setString(1, minorcode);
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                minordesc = rs1.getString("MINOR_HEAD_DESC");
                                xml =
 xml + "<minordesc>" + minordesc + "</minordesc>";
                            }
                            rs1.close();
                            ps1.close();
                            int f1 = 0;
                            ps1 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                            ps1.setString(1, subcode1);
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                subdesc1 = rs1.getString("SUB_HEAD_DESC");
                                xml =
 xml + "<subdesc1>" + subdesc1 + "</subdesc1>";
                                f1 = f1 + 1;
                            }
                            if (f1 == 0) {
                                xml = xml + "<subdesc1>" + f1 + "</subdesc1>";
                            }
                            rs1.close();
                            ps1.close();
                            int f = 0;
                            ps1 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                            ps1.setString(1, subcode2);
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                subdesc2 = rs1.getString("SUB_HEAD_DESC");
                                xml =
 xml + "<subdesc2>" + subdesc2 + "</subdesc2>";
                                f = f + 1;
                            }
                            if (f == 0) {
                                xml = xml + "<subdesc2>" + f + "</subdesc2>";
                            }
                        } catch (Exception que) {
                            System.out.println("Exception in fetching major details..." +
                                               que);
                        }


                    }
                    rs.close();
                    ps.close();
                } catch (Exception e) {
                    System.out.println("catch...." + e);
                    xml = xml + "<flag>failure</flag>";
                }

                ////////////////////////for text box display end....///////////////////////

                if (b == 0) {

                    System.out.println("i...." + b);
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
                System.out.println("catch in load table..." + e);
            }
            xml = xml + "</response>";
        }
        //////////////////end

        ////////start combobox fetch values
        else if (strCommand.equalsIgnoreCase("listcombo")) {

            System.out.println("Inside List in combobox listing... ");
            String AcId = "";
            String AcName = "";
            String Major_grp = request.getParameter("cmbMajor_grp");
            System.out.println("assign....." + Major_grp);

            String Minor_grp = request.getParameter("cmbMinor_grp");
            System.out.println("assign....." + Minor_grp);

            String Sub_grp1 = request.getParameter("cmbSub_grp1");
            System.out.println("assign....." + Sub_grp1);

            String Sub_grp2 = request.getParameter("cmbSub_grp2");
            System.out.println("assign....." + Sub_grp2);


            xml = "<response><command>list</command>";

            b = 0;
            try {
                ps1 =
 con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where MAJOR_HEAD_CODE=? and MINOR_HEAD_CODE=? and SUB_HEAD1_CODE=? and SUB_HEAD2_CODE=?");
                ps1.setString(1, Major_grp);
                ps1.setString(2, Minor_grp);
                ps1.setString(3, Sub_grp1);
                ps1.setString(4, Sub_grp2);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    AcId = rs1.getString("ACCOUNT_HEAD_CODE");
                    System.out.println("AcId" + AcId);
                    AcName = rs1.getString("ACCOUNT_HEAD_DESC");
                }
                rs1.close();
                ps1.close();
                ps =
  con.prepareStatement("SELECT * FROM FAS_OB_GL_HEADS where ACCOUNTING_HEAD_CODE=?");
                ps.setString(1, AcId);

                rs = ps.executeQuery();

                try {

                    while (rs.next()) {
                        uptoCR = rs.getFloat("UPTO_DATE_CR_BALANCE");
                        System.out.println("upto cr%%%%%%%%%%%%%%%%%%%%%" +
                                           uptoCR);
                        uptoDB = rs.getFloat("UPTO_DATE_DR_BALANCE");
                        System.out.println("upto db " + uptoDB);
                        currDR = rs.getInt("CURR_YEAR_DR_BALANCE");
                        System.out.println("curr dr is " + currDR);
                        currCR = rs.getInt("CURR_YEAR_CR_BALANCE");
                        System.out.println("curr cr is " + currCR);

                        xml =
 xml + "<flag>success</flag><AcId>" + AcId + "</AcId><AcName>" + AcName +
   "</AcName><uptoCR>" + uptoCR + "</uptoCR><uptoDB>" + uptoDB +
   "</uptoDB><currDR>" + currDR + "</currDR><currCR>" + currCR + "</currCR>";
                        b++;

                    }
                    rs.close();
                    ps.close();
                } catch (Exception ae) {
                    System.out.println("Exception in while loop..." + ae);
                }

                //////////////////for text box display start.../////////////////////////////

                String majorcode = "";
                String majorDesc = "";
                String minorcode = "";
                String minordesc = "";
                String subcode1 = "";
                String subdesc1 = "";
                String subcode2 = "";
                String subdesc2 = "";


                try {
                    ps =
  con.prepareStatement("select * from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps.setString(1, AcId);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        majorcode = rs.getString("MAJOR_HEAD_CODE");
                        minorcode = rs.getString("MINOR_HEAD_CODE");
                        subcode1 = rs.getString("SUB_HEAD1_CODE");
                        subcode2 = rs.getString("SUB_HEAD2_CODE");
                        try {
                            ps1 =
 con.prepareStatement("select * from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
                            ps1.setString(1, majorcode);
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                majorDesc = rs1.getString("MAJOR_HEAD_DESC");
                                xml =
 xml + "<majorDesc>" + majorDesc + "</majorDesc>";
                            }
                            rs1.close();
                            ps1.close();
                            ps1 =
 con.prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MINOR_HEAD_CODE=?");
                            ps1.setString(1, minorcode);
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                minordesc = rs1.getString("MINOR_HEAD_DESC");
                                xml =
 xml + "<minordesc>" + minordesc + "</minordesc>";
                            }
                            rs1.close();
                            ps1.close();
                            int f1 = 0;
                            ps1 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                            ps1.setString(1, subcode1);
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                subdesc1 = rs1.getString("SUB_HEAD_DESC");
                                xml =
 xml + "<subdesc1>" + subdesc1 + "</subdesc1>";
                                f1 = f1 + 1;
                            }
                            if (f1 == 0) {
                                xml = xml + "<subdesc1>" + f1 + "</subdesc1>";
                            }
                            rs1.close();
                            ps1.close();
                            int f = 0;
                            ps1 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                            ps1.setString(1, subcode2);
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                subdesc2 = rs1.getString("SUB_HEAD_DESC");
                                xml =
 xml + "<subdesc2>" + subdesc2 + "</subdesc2>";
                                f = f + 1;
                            }
                            if (f == 0) {
                                xml = xml + "<subdesc2>" + f + "</subdesc2>";
                            }
                        } catch (Exception que) {
                            System.out.println("Exception in fetching major details..." +
                                               que);
                        }


                    }
                    rs.close();
                    ps.close();
                } catch (Exception e) {
                    System.out.println("catch...." + e);
                    xml = xml + "<flag>failure</flag>";
                }

                ////////////////////////for text box display end....///////////////////////

                if (b == 0) {

                    System.out.println("i...." + b);
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
                System.out.println("catch in load table..." + e);
            }
            xml = xml + "</response>";
        }


        /////end combobox
        else if (strCommand.equalsIgnoreCase("listNO")) {


            int strNO = Integer.parseInt(request.getParameter("scod"));
            System.out.println("assign....." + strNO);

            System.out.println("Inside List number in listing... ");
            String AcId = "";
            String AcName = "";
            xml = "<response><command>list</command>";

            b = 0;
            try {

                ps =
  con.prepareStatement("SELECT * FROM FAS_OB_GL_HEADS where ACCOUNTING_HEAD_CODE like ('" +
                       strNO + "%')");

                rs = ps.executeQuery();

                try {

                    while (rs.next()) {
                        AcId = rs.getString("ACCOUNTING_HEAD_CODE");
                        uptoCR = rs.getFloat("UPTO_DATE_CR_BALANCE");
                        System.out.println("upto cr%%%%%%%%%%%%%%%%%%%%%" +
                                           uptoCR);
                        uptoDB = rs.getFloat("UPTO_DATE_DR_BALANCE");
                        System.out.println("upto db " + uptoDB);
                        currDR = rs.getInt("CURR_YEAR_DR_BALANCE");
                        System.out.println("curr dr is " + currDR);
                        currCR = rs.getInt("CURR_YEAR_CR_BALANCE");
                        System.out.println("curr cr is " + currCR);
                    }

                    rs.close();
                    ps.close();

                    ps1 =
 con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                    ps1.setString(1, AcId);
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        AcId = rs1.getString("ACCOUNT_HEAD_CODE");
                        AcName = rs1.getString("ACCOUNT_HEAD_DESC");
                        xml =
 xml + "<flag>success</flag><AcId>" + AcId + "</AcId><AcName>" + AcName +
   "</AcName><uptoCR>" + uptoCR + "</uptoCR><uptoDB>" + uptoDB +
   "</uptoDB><currDR>" + currDR + "</currDR><currCR>" + currCR + "</currCR>";
                        b++;
                    }
                    rs1.close();
                    ps1.close();
                } catch (Exception ae) {
                    System.out.println("Exception in while loop..." + ae);
                }

                if (b == 0) {

                    System.out.println("i...." + b);
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
                System.out.println("catch in load table..." + e);
            }
            //////////////////////text box display start....//////////////
            String majorcode = "";
            String majorDesc = "";
            String minorcode = "";
            String minordesc = "";
            String subcode1 = "";
            String subdesc1 = "";
            String subcode2 = "";
            String subdesc2 = "";


            try {
                ps =
  con.prepareStatement("select * from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                ps.setString(1, AcId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    majorcode = rs.getString("MAJOR_HEAD_CODE");
                    minorcode = rs.getString("MINOR_HEAD_CODE");
                    subcode1 = rs.getString("SUB_HEAD1_CODE");
                    subcode2 = rs.getString("SUB_HEAD2_CODE");
                    try {
                        ps1 =
 con.prepareStatement("select * from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
                        ps1.setString(1, majorcode);
                        rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            majorDesc = rs1.getString("MAJOR_HEAD_DESC");
                            xml =
 xml + "<majorDesc>" + majorDesc + "</majorDesc>";
                        }
                        rs1.close();
                        ps1.close();
                        ps1 =
 con.prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MINOR_HEAD_CODE=?");
                        ps1.setString(1, minorcode);
                        rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            minordesc = rs1.getString("MINOR_HEAD_DESC");
                            xml =
 xml + "<minordesc>" + minordesc + "</minordesc>";
                        }
                        rs1.close();
                        ps1.close();
                        ps1 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                        ps1.setString(1, subcode1);
                        rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            subdesc1 = rs1.getString("SUB_HEAD_DESC");
                            xml =
 xml + "<subdesc1>" + subdesc1 + "</subdesc1>";
                        }

                        rs1.close();
                        ps1.close();
                        int g = 0;
                        ps1 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
                        ps1.setString(1, subcode2);
                        rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            subdesc2 = rs1.getString("SUB_HEAD_DESC");
                            xml =
 xml + "<subdesc2>" + subdesc2 + "</subdesc2>";
                            g = g + 1;
                        }
                        if (g == 0) {
                            xml = xml + "<subdesc2>" + g + "</subdesc2>";
                        }
                        rs1.close();
                        ps1.close();
                    } catch (Exception que) {
                        System.out.println("Exception in fetching major details..." +
                                           que);
                    }


                }
                rs.close();
                ps.close();
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }


            //////////////////////text box display end...//////////////////////////

            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("listRange")) {


            int from = Integer.parseInt(request.getParameter("txtFrom"));
            System.out.println("assign....." + from);
            int to = Integer.parseInt(request.getParameter("txtTo"));
            System.out.println("assign....." + to);

            System.out.println("Inside List range in listing... ");
            String AcId = "";
            String AcName = "";
            xml = "<response><command>listRange</command>";

            b = 0;
            try {

                ps =
  con.prepareStatement("SELECT * FROM FAS_OB_GL_HEADS where ACCOUNTING_HEAD_CODE between " +
                       from + " and " + to + "");

                rs = ps.executeQuery();

                try {

                    while (rs.next()) {
                        AcId = rs.getString("ACCOUNTING_HEAD_CODE");
                        uptoCR = rs.getFloat("UPTO_DATE_CR_BALANCE");
                        System.out.println("upto cr%%%%%%%%%%%%%%%%%%%%%" +
                                           uptoCR);
                        uptoDB = rs.getFloat("UPTO_DATE_DR_BALANCE");
                        System.out.println("upto db " + uptoDB);
                        currDR = rs.getInt("CURR_YEAR_DR_BALANCE");
                        System.out.println("curr dr is " + currDR);
                        currCR = rs.getInt("CURR_YEAR_CR_BALANCE");
                        System.out.println("curr cr is " + currCR);

                        ps1 =
 con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                        ps1.setString(1, AcId);
                        rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            AcId = rs1.getString("ACCOUNT_HEAD_CODE");
                            AcName = rs1.getString("ACCOUNT_HEAD_DESC");
                            xml = xml + "<AcName>" + AcName + "</AcName>";
                            b++;
                        }
                        rs1.close();
                        ps1.close();
                        xml =
 xml + "<AcId>" + AcId + "</AcId><uptoCR>" + uptoCR + "</uptoCR><uptoDB>" +
   uptoDB + "</uptoDB><currDR>" + currDR + "</currDR><currCR>" + currCR +
   "</currCR>";
                    }

                    rs.close();
                    ps.close();


                } catch (Exception ae) {
                    System.out.println("Exception in while loop..." + ae);
                }

                if (b == 0) {

                    System.out.println("i...." + b);
                    xml = xml + "<flag>failure</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
                }


            } catch (Exception e) {
                System.out.println("catch in load table..." + e);
            }

            /////////////////////text box display start.../////////////////////
            /* String majorcode="";
 String majorDesc="";
 String minorcode="";
 String minordesc="";
 String subcode1="";
 String subdesc1="";
 String subcode2="";
 String subdesc2="";



 try {
     ps=con.prepareStatement("select * from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
     ps.setString(1,AcId);
     rs=ps.executeQuery();
     while(rs.next()) {
         majorcode=rs.getString("MAJOR_HEAD_CODE");
         minorcode=rs.getString("MINOR_HEAD_CODE");
         subcode1=rs.getString("SUB_HEAD1_CODE");
         subcode2=rs.getString("SUB_HEAD2_CODE");
         try {
             ps1=con.prepareStatement("select * from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE=?");
             ps1.setString(1,majorcode);
             rs1=ps1.executeQuery();
             while(rs1.next()) {
                 majorDesc=rs1.getString("MAJOR_HEAD_DESC");
                 xml=xml+"<majorDesc>"+majorDesc+"</majorDesc>";
             }
             rs1.close();
             ps1.close();
             ps1=con.prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MINOR_HEAD_CODE=?");
             ps1.setString(1,minorcode);
             rs1=ps1.executeQuery();
             while(rs1.next()) {
                 minordesc=rs1.getString("MINOR_HEAD_DESC");
                 xml=xml+"<minordesc>"+minordesc+"</minordesc>";
             }
             rs1.close();
             ps1.close();
             ps1=con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
             ps1.setString(1,subcode1);
             rs1=ps1.executeQuery();
             while(rs1.next()) {
                 subdesc1=rs1.getString("SUB_HEAD_DESC");
                 xml=xml+"<subdesc1>"+subdesc1+"</subdesc1>";
             }

             rs1.close();
             ps1.close();
             ps1=con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS where SUB_HEAD_CODE=?");
             ps1.setString(1,subcode2);
             rs1=ps1.executeQuery();
             while(rs1.next()) {
                 subdesc2=rs1.getString("SUB_HEAD_DESC");
                 xml=xml+"<subdesc2>"+subdesc2+"</subdesc2>";
             }
         }
         catch(Exception que) {
             System.out.println("Exception in fetching major details..."+que);
         }


     }
     rs.close();
     ps.close();
 }
 catch(Exception e) {
     System.out.println("catch...."+e);
     xml=xml+"<flag>failure</flag>";
 }*/

            ////////////////////text box display end..////////////////////////
            xml = xml + "</response>";
        }
        ////////////////////fetching minor values while selecting major///////////////
        else if (strCommand.equalsIgnoreCase("loadminor")) {

            String majorid = "";

            majorid = request.getParameter("majorid");
            System.out.println("assign....." + majorid);

            String minorcode = "";
            String minorDesc = "";


            try {
                ps =
  con.prepareStatement("select * from COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=?");
                ps.setString(1, majorid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    minorcode = rs.getString("MINOR_HEAD_CODE");
                    minorDesc = rs.getString("MINOR_HEAD_DESC");
                    xml =
 xml + "<option><minorcode>" + minorcode + "</minorcode><minorDesc>" +
   minorDesc + "</minorDesc></option>";
                }
                xml = "<select>" + xml + "</select>";

                rs.close();
                ps.close();
            }


            catch (Exception e) {
                System.out.println("catch.fetching minor details..." + e);
                xml = xml + "<flag>failure</flag>";
            }
        }
        /////////////////////end
        else if (strCommand.equalsIgnoreCase("loadsub")) {

            String subgrp1 = "";
            String subgrp2 = "";
            String subgrpDesc1 = "";
            String subgrpDesc2 = "";

            String minorid = request.getParameter("minorid");
            System.out.println("assign....." + minorid);


            try {
                ps2 =
 con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS");

                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    subgrpDesc1 = rs2.getString("SUB_HEAD_DESC");
                    subgrp1 = rs2.getString("SUB_HEAD_CODE");

                    xml =
 xml + "<option><subgrp1>" + subgrp1 + "</subgrp1><subgrpDesc1>" +
   subgrpDesc1 + "</subgrpDesc1></option>";
                }

                xml = "<select>" + xml + "</select>";
                rs2.close();
                ps2.close();
            } catch (Exception que) {
                System.out.println("Exception in fetching subgroup1 details..." +
                                   que);
            }
        }
        /////////////////////end

        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}


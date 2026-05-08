package Servlets.FAS.FAS1.MonthEnd.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class GLOpeningBalanceList extends HttpServlet {

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
        ResultSet rs = null;
        ResultSet rs2 = null;

        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
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
            System.out.println("Exception in openeing connection :" + e);
            //               sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        System.out.println("servlet called");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strType = "", xml = "<response>";
        try {
            strType = request.getParameter("Command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode =
            0, cmbOffice_code = 0;
        Date txtFrom_date = null, txtTo_date = null;
        Calendar c;
        String sql = "", txtCreat_By_Module = "", cmbStatus = "";


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
        System.out.println("strtype  " + strType);
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..." + txtCB_Year);
        System.out.println("Month..." + txtCB_Month);
        //  txtCreat_By_Module=request.getParameter("txtCreat_By_Module");
        // after receipt status
        // cmbStatus=request.getParameter("cmbStatus");

        // System.out.println("cmbStatus.."+cmbStatus);

        if (strType.equalsIgnoreCase("searchByMonth")) {
            xml = "<response><command>searchByMonth</command>";
            String AcHeadName = "";


            sql =
 "select ACCOUNT_HEAD_CODE,UPTO_CREDIT_BALANCE,UPTO_DEBIT_BALANCE,CURRENT_YEAR_CREDIT_BALANCE,CURRENT_YEAR_DEBIT_BALANCE,MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND,CURRENT_MONTH_DEBIT,CURRENT_MONTH_CREDIT,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND," +
   "to_char(DR_UPDATE_LAST_DATE,'DD/MM/YYYY') as dr_date,to_char(CR_UPDATE_LAST_DATE,'DD/MM/YYYY') as cr_date from FAS_GENERAL_LEDGER where  ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and year=? and month=? order by ACCOUNT_HEAD_CODE";
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, txtCB_Year);
                ps.setInt(4, txtCB_Month);
                // ps.setString(5,txtCreat_By_Module);
                // ps.setString(6,cmbStatus);
                xml =
 xml + "<flag>success</flag><Ucode>" + cmbAcc_UnitCode + "</Ucode><Offid>" +
   cmbOffice_code + "</Offid><txtCB_Year>" + txtCB_Year +
   "</txtCB_Year><txtCB_Month>" + txtCB_Month + "</txtCB_Month>";
                rs = ps.executeQuery();
                int cnt = 0;
                String AcHeadCode = "";

                while (rs.next()) {
                    try {
                        ps1 =
 con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
                        ps1.setInt(1, rs.getInt("ACCOUNT_HEAD_CODE"));
                        rs2 = ps1.executeQuery();
                        if (rs2.next()) {
                            AcHeadName = rs2.getString("ACCOUNT_HEAD_DESC");
                            // xml=xml+"<AHdesc>"+rs2.getString("ACCOUNT_HEAD_DESC")+"</AHdesc>";
                        }
                        ps1.close();
                    } catch (Exception que) {
                        System.out.println("exception in fetching the account head name....." +
                                           que);
                    }


                    xml = xml + "<leng>";
                    xml =
 xml + "<Hcode>" + rs.getInt("ACCOUNT_HEAD_CODE") + "</Hcode>";
                    xml = xml + "<Hname>" + AcHeadName + "</Hname>";
                    xml =
 xml + "<UptoCRBalance>" + rs.getString("UPTO_CREDIT_BALANCE").trim() +
   "</UptoCRBalance>";
                    xml =
 xml + "<UptoDBBalance>" + rs.getString("UPTO_DEBIT_BALANCE").trim() +
   "</UptoDBBalance>";
                    xml =
 xml + "<currCR>" + rs.getString("CURRENT_YEAR_CREDIT_BALANCE").trim() +
   "</currCR>";
                    xml =
 xml + "<currDB>" + rs.getString("CURRENT_YEAR_DEBIT_BALANCE").trim() +
   "</currDB>";
                    xml =
 xml + "<OB>" + rs.getString("MONTH_OPENING_BALANCE") + "</OB>";
                    xml =
 xml + "<IND>" + rs.getString("MONTH_OPENING_BAL_DR_CR_IND") + "</IND>";
                    xml =
 xml + "<CUR_DEBIT>" + rs.getString("CURRENT_MONTH_DEBIT") + "</CUR_DEBIT>";
                    xml =
 xml + "<CUR_CREDIT>" + rs.getString("CURRENT_MONTH_CREDIT") + "</CUR_CREDIT>";
                    xml =
 xml + "<CLOSING_BALANCE>" + rs.getString("MONTH_CLOSING_BALANCE") +
   "</CLOSING_BALANCE>";
                    xml =
 xml + "<CLOSING_BAL_IND>" + rs.getString("MONTH_CLOSING_BAL_DR_CR_IND") +
   "</CLOSING_BAL_IND>";
                    xml =
 xml + "<DR_DATE>" + rs.getString("dr_date") + "</DR_DATE>";
                    xml =
 xml + "<CR_DATE>" + rs.getString("cr_date") + "</CR_DATE>";

                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>searchByMonth</command><flag>failure</flag>";
            }

        }
        System.out.println("here " + strType.equalsIgnoreCase("searchByDate"));

        /* if(strType.equalsIgnoreCase("searchByDate"))
        {
            xml="<response><command>searchByDate</command>";
            System.out.println("here "+strType.equalsIgnoreCase("searchByDate"));

            String[] sd=request.getParameter("txtFrom_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtFrom_date=new Date(d.getTime());
            System.out.println("from_date "+txtFrom_date);

            sd=request.getParameter("txtTo_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtTo_date=new Date(d.getTime());
            System.out.println("txtTo_date "+txtTo_date);
            sql="select VOUCHER_NO,to_char(PAYMENT_DATE,'DD/MM/YYYY') as rec_date,REMARKS, trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID=? and  " +
            "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and PAYMENT_DATE>=? and PAYMENT_DATE<=? and CREATED_BY_MODULE=? and PAYMENT_STATUS=?";
           try
           {
             int count=0;
            ps=con.prepareStatement(sql);
            ps.setInt(1,cmbAcc_UnitCode);
            ps.setInt(2,cmbOffice_code);
            ps.setInt(3,txtCB_Year);
            ps.setInt(4,txtCB_Month);
            ps.setDate(5,txtFrom_date);
            ps.setDate(6,txtTo_date);
            ps.setString(7,txtCreat_By_Module);
            ps.setString(8,cmbStatus);
             xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
             "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
               rs=ps.executeQuery();
                   while(rs.next())
                   {
                       xml=xml+"<leng>";
                       xml=xml+"<Rec_no>"+rs.getInt("VOUCHER_NO")+"</Rec_no>";
                       xml=xml+"<Rec_Date>"+rs.getString("rec_date")+"</Rec_Date>";
                       xml=xml+"<Remak>"+rs.getString("REMARKS")+"</Remak>";
                       xml=xml+"<Tot_Amt>"+rs.getString("TOTAL_AMOUNT")+"</Tot_Amt>";
                       xml=xml+"</leng>";
                       count++;
                   }
                  if(count==0)
                  {
                     xml="<response><command>searchByDate</command><flag>failure</flag>";
                  }
               }
               catch(SQLException sqle)
               {
                 System.out.println("error while fetching data " + sqle);
                   xml="<response><command>searchByDate</command><flag>failure</flag>";
               }
        } */
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
    }
}
